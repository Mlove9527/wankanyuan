package com.liutianjun.controller;


import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.liutianjun.service.RoleService;
import com.liutianjun.utils.LoggerUtils;
import com.liutianjun.utils.SendEmailCode;
import com.liutianjun.utils.StringUtils;
import com.liutianjun.utils.VerifyCodeUtils;
import com.liutianjun.utils.vcode.Captcha;
import com.liutianjun.utils.vcode.GifCaptcha;
import com.liutianjun.utils.vcode.SpecCaptcha;


@Controller
@Scope(value="prototype")
@RequestMapping("open")
public class CommonController {
	@Resource
	RoleService roleService;
	@Value("${email.fromMail}")  
    private String fromMail;  
	@Value("${email.user}")  
	private String fromUser;  
	@Value("${email.password}")  
	private String fromPassword;  
	/*@RequestMapping("refreshDB")
	@ResponseBody
	public Map<String,Object> refreshDB(){
		roleService.initData();
		resultMap.put("status", 200);
		return resultMap;
	}*/
	/**
	 * 404错误
	 * @param request
	 * @return
	 */
	@RequestMapping("404")
	public ModelAndView _404(HttpServletRequest request){
		ModelAndView view = new ModelAndView("common/404");
		return view;
	}
	/**
	 * 500错误
	 * @param request
	 * @return
	 */
	@RequestMapping("500")
	public ModelAndView _500(HttpServletRequest request){
		ModelAndView view = new ModelAndView("common/500");
		
		Throwable t = (Throwable)request.getAttribute("javax.servlet.error.exception");
		String defaultMessage = "未知" ;
		if(null == t){
			view.addObject("line", defaultMessage);
			view.addObject("clazz", defaultMessage);
			view.addObject("methodName",defaultMessage);
			return view;
		}
		String message = t.getMessage() ;//错误信息
		StackTraceElement[] stack = t.getStackTrace();
		view.addObject("message", message);
		if(null != stack && stack.length != 0 ){
			StackTraceElement element = stack[0];
			int line = element.getLineNumber();//错误行号
			String clazz = element.getClassName();//错误java类
			String fileName = element.getFileName();
			
			String methodName = element.getMethodName() ;//错误方法
			view.addObject("line", line);
			view.addObject("clazz", clazz);
			view.addObject("methodName",methodName);
			LoggerUtils.fmtError(getClass(), "line:%s,clazz:%s,fileName:%s,methodName:%s()",
					line,clazz,fileName,methodName);
		}
		return view;
	}
	
	/**
	 * 获取验证码
	 * @param response
	 */
	@RequestMapping(value="getVCode",method=RequestMethod.GET)
	public void getVCode(HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("image/jpg");  
	        
	        //生成随机字串  
	        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
	        //存入Shiro会话session  
	        SecurityUtils.getSubject().getSession().setAttribute(VerifyCodeUtils.V_CODE, verifyCode.toLowerCase());
	        //生成图片  
	        int w = 146, h = 33;  
	        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(),e, "获取验证码异常：%s",e.getMessage());
		}
	}
	
	/**
	 * 获取手机验证码
	 * @Title: getVCode 
	 * @param response
	 * @param request 
	 * void
	 * @throws Exception 
	 */
	@RequestMapping(value="getPhoneCode",method=RequestMethod.GET)
	@ResponseBody
	public Integer getVPhoneCode(String phone) throws Exception{
		
		//生成验证码
		String verifyPhoneCode = VerifyCodeUtils.generateVerifyPhoneCode(6);
		//存入Shiro会话session
		SecurityUtils.getSubject().getSession().setAttribute(VerifyCodeUtils.V_PHONECODE, verifyPhoneCode);
		//发送手机验证码
		//int i = SendViaAspx.sendPhoneCode(phone, verifyPhoneCode);
		
		System.out.println(phone+"-----"+verifyPhoneCode);
		return 1;
		
	}
	
	
	/**
	 * 获取新手机验证码
	 * @Title: getVCode 
	 * @param response
	 * @param request 
	 * void
	 * @throws Exception 
	 */
	@RequestMapping(value="getNewPhoneCode",method=RequestMethod.GET)
	@ResponseBody
	public Integer getVNewPhoneCode(String phone) throws Exception{
		
		//生成验证码
		String verifyPhoneCode = VerifyCodeUtils.generateVerifyPhoneCode(6);
		//存入Shiro会话session
		SecurityUtils.getSubject().getSession().setAttribute(VerifyCodeUtils.V_NEWPHONECODE, verifyPhoneCode);
		//发送手机验证码
		//int i = SendViaAspx.sendPhoneCode(phone, verifyPhoneCode);
		System.out.println(phone+"-----"+verifyPhoneCode);
		return 1;
		
	}
	
	/**
	 * 生成邮箱验证码
	 * @Title: getVEmailCode 
	 * @param email
	 * @return
	 * @throws Exception 
	 * Integer
	 */
	@RequestMapping(value="getEmailCode",method=RequestMethod.GET)
	@ResponseBody
	public Integer getVEmailCode(String email) throws Exception{
		
		//生成验证码
		String verifyCode = VerifyCodeUtils.generateVerifyPhoneCode(6);
		//存入Shiro会话session
		SecurityUtils.getSubject().getSession().setAttribute(VerifyCodeUtils.V_EMAILCODE, verifyCode);
		//发送邮箱验证码
		Integer i = SendEmailCode.sendMail(fromMail, fromUser, fromPassword,
				email,  
				"【万康源】",  
				"【万康源】：您的验证码为："+ verifyCode);
		
		System.out.println(email+"-----"+verifyCode);
		return i;
		
	}
	
	/**
	 * 获取新邮箱验证码
	 * @Title: getVNewEmailCode 
	 * @param email
	 * @return
	 * @throws Exception 
	 * Integer
	 */
	@RequestMapping(value="getNewEmailCode",method=RequestMethod.GET)
	@ResponseBody
	public Integer getVNewEmailCode(String email) throws Exception{
		
		//生成验证码
		String verifyCode = VerifyCodeUtils.generateVerifyPhoneCode(6);
		//存入Shiro会话session
		SecurityUtils.getSubject().getSession().setAttribute(VerifyCodeUtils.V_NEWEMAILCODE, verifyCode);
		//发送邮箱验证码
		SendEmailCode.sendMail(fromMail, fromUser, fromPassword,
				email,  
				"【万康源】",  
				"【万康源】：您的验证码为："+ verifyCode);
		System.out.println(email+"-----"+verifyCode);
		return 1;
		
	}
	
	
	/**
	 * 获取验证码（Gif版本）
	 * @param response
	 */
	@RequestMapping(value="getGifCode",method=RequestMethod.GET)
	public void getGifCode(HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("image/gif");  
	        /**
	         * gif格式动画验证码
	         * 宽，高，位数。
	         */
	        Captcha captcha = new GifCaptcha(150,41,4);
	        //输出
	        ServletOutputStream out = response.getOutputStream();
	        captcha.out(out);
	        out.flush();
	       //存入Shiro会话session  
	        System.out.println( captcha.text().toLowerCase());
	        request.getSession().setAttribute(VerifyCodeUtils.V_CODE, captcha.text().toLowerCase());
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(),e, "获取验证码异常：%s",e.getMessage());
		}
	}
	/**
	 * 获取验证码（jpg版本）
	 * @param response
	 */
	@RequestMapping(value="getJPGCode",method=RequestMethod.GET)
	public void getJPGCode(HttpServletResponse response,HttpServletRequest request){
		try {
			response.setHeader("Pragma", "No-cache");  
			response.setHeader("Cache-Control", "no-cache");  
			response.setDateHeader("Expires", 0);  
			response.setContentType("image/jpg");  
			/**
			 * jgp格式验证码
			 * 宽，高，位数。
			 */
			Captcha captcha = new SpecCaptcha(146,33,4);
			//输出
			captcha.out(response.getOutputStream());
			HttpSession session = request.getSession(true);  
			//存入Session
			session.setAttribute(VerifyCodeUtils.V_CODE,captcha.text().toLowerCase());  
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(),e, "获取验证码异常：%s",e.getMessage());
		}
	}
	/**
	 * 跳转到其他网站
	 * @param url
	 * @return
	 */
	@RequestMapping(value="www/open/goto",method=RequestMethod.GET)
	public ModelAndView _goto(String url){
		
		return new ModelAndView("www/go_to","url",url);
	}
	/**
	 * 踢出页面
	 * @return
	 */
	@RequestMapping(value="kickedOut",method=RequestMethod.GET)
	public ModelAndView kickedOut(HttpServletRequest request,UrlPathHelper pp){
		//如果是踢出后，来源地址是：http://shiro.itboy.net/u/login.shtml;JSESSIONID=4f1538d9-df19-48c8-b4b1-aadacadde23a
		//如果来源是null，那么就重定向到首页。这个时候，如果首页是要登录，那就会跳转到登录页
		if(StringUtils.isBlank(request.getHeader("Referer"))){
			return new ModelAndView("/");
		}
		return new ModelAndView("common/kicked_out");
	}
	/**
	 * 没有权限提示页面
	 * @return
	 */
	@RequestMapping(value="unauthorized",method=RequestMethod.GET)
	public ModelAndView unauthorized(){
		return new ModelAndView("common/unauthorized");
	}
	@RequestMapping(value="shiro",method=RequestMethod.GET)
	public ModelAndView shiro(){
		return new ModelAndView("shiro");
	}
}
