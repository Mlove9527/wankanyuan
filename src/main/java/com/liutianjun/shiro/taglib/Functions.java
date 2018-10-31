package com.liutianjun.shiro.taglib;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.util.StringUtils;

import com.liutianjun.pojo.Resource;
import com.liutianjun.pojo.Role;
import com.liutianjun.service.ResourceService;
import com.liutianjun.service.RoleService;
import com.liutianjun.shiro.spring.SpringUtils;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
public class Functions {

    public static boolean in(String str, Object element) {
        if(str == null) {
            return false;
        }
        return str.indexOf(element.toString()) != -1;
    }
    
    public static String principal(Session session) {
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if(null != principalCollection) {
        	return (String)principalCollection.getPrimaryPrincipal();
        }

        return null;
    }
    public static boolean isForceLogout(Session session) {
        return session.getAttribute("session.force.logout") != null;
    }

    public static String roleName(Integer roleId) {
        Role sysRole = getRoleService().selectByPrimaryKey(roleId);
        if(sysRole == null) {
            return "";
        }
        return sysRole.getDescription();
    }

    public static String roleNames(String roleIds) {
        if(StringUtils.isEmpty(roleIds)) {
            return "";
        }

        String[] roleIdStrs = roleIds.split(",");
        List<Integer> list = new ArrayList<>();
        for(String roleIdStr : roleIdStrs) {
            if(StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            list.add(Integer.valueOf(roleIdStr));
        }
        
        StringBuilder s = new StringBuilder();
        for(Integer roleId : list) {
            Role sysRole = getRoleService().selectByPrimaryKey(roleId);
            if(sysRole == null) {
                return "";
            }
            s.append(sysRole.getDescription());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }
    public static String resourceName(Integer resourceId) {
        Resource sysResource = getResourceService().selectByPrimaryKey(resourceId);
        if(sysResource == null) {
            return "";
        }
        return sysResource.getName();
    }
    public static String resourceNames(String resourceIds) {
        if(StringUtils.isEmpty(resourceIds)) {
            return "";
        }
        
        String[] resourceIdStrs = resourceIds.split(",");
        List<Integer> list = new ArrayList<>();
        for(String roleIdStr : resourceIdStrs) {
            if(StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            list.add(Integer.valueOf(roleIdStr));
        }

        StringBuilder s = new StringBuilder();
        for(Integer resourceId : list) {
            Resource sysResource = getResourceService().selectByPrimaryKey(resourceId);
            if(sysResource == null) {
                return "";
            }
            s.append(sysResource.getName());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    private static RoleService roleService;
    private static ResourceService resourceService;


    public static RoleService getRoleService() {
        if(roleService == null) {
            roleService = SpringUtils.getBean(RoleService.class);
        }
        return roleService;
    }

    public static ResourceService getResourceService() {
        if(resourceService == null) {
            resourceService = SpringUtils.getBean(ResourceService.class);
        }
        return resourceService;
    }
}

