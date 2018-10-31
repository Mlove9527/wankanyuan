<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/project1.css" />
<!-- <link href="css/bootstrap.min.css" rel="stylesheet" media="screen"> -->
<!-- <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script> -->
<script  src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.qqFace.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<style type="text/css">
    .qqFace { margin-top: 4px; background: #fff; padding: 2px; border: 1px #dfe6f6 solid; }
    .qqFace table td { padding: 0px; }
    .qqFace table td img { cursor: pointer; border: 1px #fff solid; }
    .qqFace table td img:hover { border: 1px #0066cc solid; }
    #show { width: 770px; margin: 20px auto; background: #fff; padding: 5px; border: 1px solid #DDD; vertical-align: top; }
    .sub_btn { position: absolute; right: 0px; top: 0; display: inline-block; zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  *display: inline;
    vertical-align: baseline; margin: 0 2px; outline: none; cursor: pointer; text-align: center; font: 14px/100% Arial, Helvetica, sans-serif; padding: .5em 2em .55em; text-shadow: 0 1px 1px rgba(0,0,0,.6); -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2); -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2); box-shadow: 0 1px 2px rgba(0,0,0,.2); color: #e8f0de; border: solid 1px #538312; background: #64991e; background: -webkit-gradient(linear, left top, left bottom, from(#7db72f), to(#4e7d0e)); background: -moz-linear-gradient(top, #7db72f, #4e7d0e);  filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#7db72f', endColorstr='#4e7d0e');
    }
    .sub_btn:hover { background: #538018; background: -webkit-gradient(linear, left top, left bottom, from(#6b9d28), to(#436b0c)); background: -moz-linear-gradient(top, #6b9d28, #436b0c);  filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#6b9d28', endColorstr='#436b0c');
    }
</style>
<body>
    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="<%=request.getContextPath()%>/static/img/newlogo2.png" height="70" width="218" alt="" class="logo" /></h1>
                <a href="/wankangyuan/project/selectMyProject">
                    <div class="topT">项目</div>
                </a>
                <a href="/wankangyuan/sourceData/firstIn?type=1">
                    <div class="topT">格式数据</div>
                </a>
                <a href="/wankangyuan/application/viewMine">
                    <div class="topT">应用</div>
                </a>
                <div class="touxiangK">
                    <a href="/wankangyuan/userInfo">
                        <img src="${user.headimg }" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="touxiang" />
                    </a>
                    <div class="userbutK">
                        <a href="/wankangyuan/userInfo">
                            <div class="userbut">用户信息</div>
                        </a>
                        <a href="/wankangyuan/message/viewMessage">
                            <div class="userbut">系统消息
                            <c:if test="${systemMSG }">
                                <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint2" />
                            </c:if>
                            </div>
                        </a>
                        <div class="userbutline"></div>
                        <a href="/wankangyuan/logout">
                            <div class="userbut">退出登录</div>
                        </a>
                    </div>
                </div>
                <div class="nicheng">${user.username }</div>
                <a href="/wankangyuan/friends/viewFriendsManage">
                    <div class="yanjiuquan active">
                        <div class="yanjiuquanT">研究圈</div>
                        <%-- <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint" /> --%>
                    </div>
                </a>
            </div>
            <div class="top2">
                <div class="top2C">
                    <a href="/wankangyuan/friends/viewFriendsManage"><div class="top2Cli">好友管理</div></a>
                    <a href="/wankangyuan/friendMessage/viewFriendMessage">
                        <div class="top2Cli top2CliYJ">好友消息</div>
                    </a>
                    <!-- <div class="top2CrB">
                        <div class="top2Crb">清空所有消息</div>
                    </div> -->
                </div>
            </div>
            <div class="messagesendM">
                <div class="backK">
                    <a href="/wankangyuan/friendMessage/viewFriendMessage">
                        <img src="<%=request.getContextPath()%>/static/img/back.png" alt="" class="back" />
                    </a>
                </div>

                <!-- 左侧即时消息 -->
                <div class="messsendML">
                    <div class="messsendMLT">
                        <span>${obj.username }</span>与你的对话
                    </div>
                    <div class="messsendMLM"></div>

                    <!-- 输入发送栏 -->
                    <div class="messsendMLB">
                        <img src="<%=request.getContextPath()%>/static/img/messsend_1.png" alt="" class="mesendMLB_jia" />
                        <img src="<%=request.getContextPath()%>/static/img/messsend_2.png" alt="" class="mesendMLB_bq" />
                        <textarea name="" id="saytext" class="mesendMLB_kuang" maxlength="200" placeholder="长度限制200个字符"></textarea>
                        <input type="button" class="mesendMLB_send" value="发送" onclick="sendMessage()" />
                    </div>
                </div>

                <!-- 右侧消息栏 -->
                <div class="messsendMR">
                    <div class="messsendMRT">私信列表</div>
                    <div class="messsendMRM" data-bind="foreach: friendMessage">
                        <div class="messsendMRMz">
                            <div class="messsendMRMzik">
                                <img alt="" class="messsendMRMzi" data-bind="attr:{src:senderHeadimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' />
                            </div>
                            <div class="messsendMRMzt">
                                <div class="messsendMRMztT">
                                    <div class="messsendMRMztTl" data-bind="text:senderName"></div>
                                    <div class="messsendMRMztTr">
                                        <span data-bind="text:sendTime"></span>
                                    </div>
                                </div>
                                <div class="messsendMRMztB" data-bind="text:content,click:$root.sendFriendMessage"></div>
                            </div>
                        </div>
                    </div>
                </div>




                <div class="clear"></div>
            </div>

            <div class="bottom">
                <a href="javascript:;">
                    <div class="bot_guanwang">公司官网</div>
                </a>
                <a href="javascript:;">
                    <div class="bot_guanyu">关于</div>
                </a>
                <a href="javascript:;">
                    <div class="bot_jianyi">反馈建议</div>
                </a>
                <div class="botT">Copyright @2018天津万康源科技有限公司</div>
            </div>
        </div>
    </div>
</body>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sockjs.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/knockout-3.4.2.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/moment.min.js"></script>

<script type="text/javascript">
project0();
var centHost = window.location.host;

var websocket = null;
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://"+centHost+"/wankangyuan/websocket/socketServer");
} 
else if ('MozWebSocket' in window) {
    websocket = new MozWebSocket("ws://"+centHost+"/wankangyuan/websocket/socketServer");
} 
else {
    websocket = new SockJS("http://"+centHost+"/wankangyuan/sockjs/socketServer");
}
//连接发生错误的回调方法
websocket.onerror = function () {
    layer.msg("连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function () {
    layer.msg("连接成功");
}

//接收到消息的回调方法
websocket.onmessage = function (event) {
    var msgObj = JSON.parse(event.data);
    if(msgObj.status == "OFFLINE"){
    	timesend(msgObj.content);
    	return ;
    }
    if(msgObj.username == '${obj.username}'){
	    messagesend(2,msgObj.headimg,msgObj.content);
    }else {
        $.post("/wankangyuan/friendMessage/sendFriendMessage/"+msgObj.content,{username:msgObj.username,objname:msgObj.objname},function(result){
            if(result && result.status!= 200){
                return layer.msg("接收消息失败",function(){}),!1;
            }else{
            	vm.showFriendMessage();
            	//timesend("对方正在与其他人聊天，本消息已转为消息提醒");
                
            }
        },"json");
    }
}

//连接关闭的回调方法
websocket.onclose = function () {
    layer.msg("连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
	//关闭WebSocket连接
	websocket.close();
}


$(document).ready(function(){
    $('.mesendMLB_bq').qqFace({

        id : 'facebox', 

        assign:'saytext', 

        path:'<%=request.getContextPath()%>/static/arclist/' //表情存放的路径

    });
    //timesend("2018-4-16 18：00");
    $.get("/wankangyuan/friendMessage/getRecentMessage",{senderId:${obj.id}},function(data){
    	if(null != data && "" != data){
	        var list = JSON.parse(data);
	        var standTime = moment(list[0].sendTime).add(5,"seconds");
            timesend(moment(list[0].sendTime).format('YYYY-MM-DD HH:mm:ss'));
	        for (var i in list){
	        	if(standTime - moment(list[i].sendTime) < 0){
	        		timesend(moment(list[i].sendTime).format('YYYY-MM-DD HH:mm:ss'));
	        		standTime = moment(list[i].sendTime).add(10,"m");
	        	}
	            messagesend(2,list[i].senderHeadimg,list[i].content);
	        }
    	}
    	vm.showFriendMessage();
    });
})
    
function sendMessage(){
	if($("#saytext").val().trim() == "") {
		return layer.msg("内容不能为空!!!",function(){}),!1;
	}
	var content = $("#saytext").val();
	var jsonStr = '{"objname":"${obj.username}","username":"${user.username}","headimg":"${user.headimg}","content":"'+ content +'"}';
    	
	messagesend(1,"${user.headimg}",content);
	websocket.send(jsonStr);
	$("#saytext").val("");
}

//定义ViewModel
function ViewModel() {
    var self = this;
    self.friendMessage = ko.observableArray();
    self.showFriendMessage = function() {
    	$.get("/wankangyuan/friendMessage/getAllMyMessage",{id:${user.id}},function(data){
    		var list = JSON.parse(data);
    		self.friendMessage.removeAll();
            for (var i in list){
            	list[i].sendTime = moment(list[i].sendTime).format('YYYY-MM-DD HH:mm:ss')
            	self.friendMessage.push(list[i]);
            }
    	});
    }
    //self.showFriendMessage();
    
    self.sendFriendMessage = function(message) {
    	window.location.href="/wankangyuan/friendMessage/viewSendMessage?objId=" + message.senderId;
    }
    
}
var vm = new ViewModel();
ko.applyBindings(vm);

</script>
</html>