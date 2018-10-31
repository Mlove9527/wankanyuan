package com.liutianjun.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-12
 * <p>Version: 1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {
	
	protected Map<String, Object> resultMap = new HashMap<String, Object>();
    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String processUnauthenticatedException(HttpServletRequest request, HttpServletResponse response) {
    	if(isAjaxRequest(request)) {
    		resultMap.put("status", 400);
    		resultMap.put("message", "对不起您没有权限!");
    		writeJson(resultMap, response);
    		return null;
    	}else {
    		return "unauthorized.jsp";
		}
    }
    
    /**
     * 是否ajax异常
     * @Title: isAjaxRequest 
     * @param request
     * @return 
     * boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }
    
    private void writeJson(Map<String,Object> map, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            out.write(objectMapper.writeValueAsString(map));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
}
