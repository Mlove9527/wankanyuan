package com.liutianjun.shiro.filter;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.liutianjun.pojo.FriendMessage;
import com.liutianjun.pojo.User;
import com.liutianjun.service.FriendMessageService;
import com.liutianjun.service.MessageService;
import com.liutianjun.service.UserService;


/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
public class UserFilter extends PathMatchingFilter {

    @Autowired
    private UserService userService;
    
    @Autowired
    private FriendMessageService friendMessageService;
    
    @Autowired
    private MessageService messageService;
    
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String)SecurityUtils.getSubject().getPrincipal();
        User user = userService.selectByUsername(username);
        request.setAttribute("user", user);
        
        List<FriendMessage> list = friendMessageService.findAllRecentMessageList(user.getId());
        if(null != list && list.size() > 0) {
        	request.setAttribute("friendMSG", true);
        }
        if(0 < messageService.findUnDealMessage(user.getId())) {
        	request.setAttribute("systemMSG", true);
        }
        /*
        
        Set<String> permissions = userService.findPermissions(username);
        List<Resource> menus = resourceService.findMenus(permissions);
        HttpServletRequest req = (HttpServletRequest) request;
        req.getSession().setAttribute("menus", menus);
        */
        return true;
    }
}