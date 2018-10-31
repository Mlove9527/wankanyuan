package com.liutianjun.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liutianjun.pojo.SysConfig;
import com.liutianjun.service.SysConfigService;

/**
 * 系统配置管理
 * @Title: SysConfigController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月25日  
 * @version V1.0
 */
@Controller
@RequestMapping("/admin")
public class SysConfigController {

	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 显示系统配置
	 * @Title: viewSysConfigManage 
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/SysConfigManage",method=RequestMethod.GET)
	public String viewSysConfigManage(Model model) {
		SysConfig sysConfig = sysConfigService.selectByPrimaryKey(1);
		model.addAttribute("sysConfig", sysConfig);
		return "admin/sysmanage.jsp";
	}
	
	/**
	 * 更新系统配置
	 * @Title: updateSysConfigManage 
	 * @param sysConfig
	 * @param attributes
	 * @return 
	 * String
	 */
	@RequestMapping(value="/SysConfigManage",method=RequestMethod.POST)
	public String updateSysConfigManage(SysConfig sysConfig,RedirectAttributes attributes) {
		if(1 == sysConfigService.updateByPrimaryKey(sysConfig)) {
			attributes.addFlashAttribute("msg", "更新成功");
		}else {
			attributes.addFlashAttribute("msg", "更新失败");
		}
		
		return "redirect:/admin/SysConfigManage";
	}
	
	/**
	 * 显示后台首页
	 * @Title: viewIndex 
	 * @return 
	 * String
	 */
	@RequestMapping(value="/viewIndex",method=RequestMethod.GET)
	public String viewIndex() {
		return "admin/index.jsp";
	}
	
	/**
	 * 管理员登录跳转
	 * @return
	 */
	@RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, 
    		Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if ("kaptchaValidateFailed".equals(exceptionClassName)) {
        	error = "验证码错误";
        } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
        	error = "账号被锁定";
		} else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "/admin/login.jsp";
    }
	
	/**
	 * 管理员登出
	 * @Title: adminLogout 
	 * @return 
	 * String
	 */
	@RequestMapping(value = "/logout",method=RequestMethod.GET)
	public String adminLogout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/admin/login";
	}
	
}
