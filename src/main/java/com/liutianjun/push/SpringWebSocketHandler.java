package com.liutianjun.push;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.liutianjun.pojo.ChatMessage;
import com.liutianjun.service.FriendMessageService;

public class SpringWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private FriendMessageService friendMessageService;
	
    private static final Map<String, WebSocketSession> userMap;
    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);
    static {
    	//userMap = new HashMap<String,WebSocketSession>();
    	userMap = new ConcurrentHashMap<String,WebSocketSession>();
    }
    
    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        
    	//users.add(session);
    	userMap.put((String) session.getAttributes().get("WEBSOCKET_USERNAME"), session);
        System.out.println("connect to the websocket success......当前数量:"+userMap.size());
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }
    
    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        String username= (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        System.out.println("用户"+username+"已退出！");
        userMap.remove(session.getAttributes().get("user"));
        System.out.println("剩余在线用户"+userMap.size());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override    
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	String jsonStr = message.getPayload();
    	ObjectMapper objectMapper = new ObjectMapper();
    	ChatMessage chatMessage = objectMapper.readValue(jsonStr, ChatMessage.class);
    	
    	sendMessageToUser(chatMessage.getObjname(), message);
        //super.handleTextMessage(session, message);
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){session.close();}
        logger.debug("websocket connection closed......");
        userMap.remove(session.getAttributes().get("user"));
    }

    public boolean supportsPartialMessages() {
        return false;
    }
    
    
    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
    	WebSocketSession user = userMap.get(userName);
    	String jsonStr = message.getPayload();
    	ObjectMapper objectMapper = new ObjectMapper();
        try {
        	ChatMessage chatMessage = objectMapper.readValue(jsonStr, ChatMessage.class);
            if (null != user && user.isOpen()) {
                user.sendMessage(message);
            }else {
            	if(1 == friendMessageService.sendFriendMessage(chatMessage.getUsername(), chatMessage.getObjname(), chatMessage.getContent())) {
            		chatMessage.setStatus("OFFLINE");
            		chatMessage.setContent("对方不在线，本消息已转为消息提醒");
            		sendMessageToMe(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            	}
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 给自己发送消息
     * @Title: sendMessageToMe 
     * @param message 
     * void
     */
    public void sendMessageToMe(TextMessage message) {
    	try {
			String jsonStr = message.getPayload();
			ObjectMapper objectMapper = new ObjectMapper();
			ChatMessage chatMessage = objectMapper.readValue(jsonStr, ChatMessage.class);
			WebSocketSession me = userMap.get(chatMessage.getUsername());
			me.sendMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
    	Collection<WebSocketSession> users = userMap.values();
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
