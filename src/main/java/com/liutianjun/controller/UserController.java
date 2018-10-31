package com.liutianjun.controller;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liutianjun.pojo.Friends;
import com.liutianjun.pojo.SysConfig;
import com.liutianjun.pojo.User;
import com.liutianjun.service.FriendsService;
import com.liutianjun.service.RoleService;
import com.liutianjun.service.SysConfigService;
import com.liutianjun.service.UserService;
import com.liutianjun.utils.VerifyCodeUtils;

import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;

/**
 * 用户Controller
 * @Title: UserController.java  
 * @Package com.liutianjun.controller  
 * @Description: TODO
 * @author LiuTianJun  
 * @date 2018年5月16日  
 * @version V1.0
 */
@Controller
public class UserController {
	protected Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private FriendsService friendsService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	
	/**
	 * 登录跳转
	 * @return
	 */
	@RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, 
    		Model model,RedirectAttributes attributes) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if ("kaptchaValidateFailed".equals(exceptionClassName)) {
        	error = "动态验证码错误";
        } else if ("forbidUserLoginFailed".equals(exceptionClassName)) {
        	error = "普通用户不能登录后台";
		} else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
        	error = "账号被锁定";
		} else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
        	error = "登录失败多次，账户被锁定10分钟";
		} else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        if(req.getParameter("forceLogout") != null) {  
        	error = "您已经被管理员强制退出，请重新登录";  
        }   
        String loginType = req.getParameter("loginType");
        if("adminLogin".equals(loginType)) {
        	attributes.addFlashAttribute("msg", error);
        	return "redirect:/admin/login";
        }else {
        	 model.addAttribute("error", error);
		}
        return "user/login.jsp";
    }
	
	/**
	 * 注册跳转
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register(RedirectAttributes attributes) {
		SysConfig sysConfig = sysConfigService.selectByPrimaryKey(1);
		if(0 == sysConfig.getIsRegistrable()) {
			attributes.addFlashAttribute("msg", "注册关闭!");
			return "redirect:/login";
		}
		return "user/register.jsp";
	}
	
	/**
	 * 注册功能
	 * @Title: subRegister 
	 * @return 
	 * String
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> subRegister(String randomCode, String emailCode, User user) {
		SysConfig sysConfig = sysConfigService.selectByPrimaryKey(1);
		resultMap.put("status", 400);
		//检查注册是否开启
		if(0 == sysConfig.getIsRegistrable()) {
			resultMap.put("message", "注册关闭!");
			return resultMap;
		}
		//检查验证码
		if(!VerifyCodeUtils.verifyCode(randomCode,VerifyCodeUtils.V_CODE)) {
			resultMap.put("message", "动态验证码错误!");
			return resultMap;
		}
		//检查用户名
		if(null != userService.selectByUsername(user.getUsername())){
			resultMap.put("message", "用户名已经存在!");
			return resultMap;
		}
		//检查邮箱
		if(null != userService.selectByEmail(user.getEmail())){
			resultMap.put("message", "Email已经存在!");
			return resultMap;
		}
		//检查手机号
		if(null != userService.selectByPhone(user.getPhone())){
			resultMap.put("message", "手机号已经存在!");
			return resultMap;
		}
		//检查邮箱验证码
		if(!VerifyCodeUtils.verifyCode(emailCode, VerifyCodeUtils.V_EMAILCODE)) {
			resultMap.put("message", "邮箱验证码错误!");
			return resultMap;
		}
		user.setRoleIds("3");
		userService.insert(user);
		resultMap.put("message", "注册成功!");
		resultMap.put("status", 200);
		
		return resultMap;
	}
	
	/**
	 * 忘记密码
	 * @Title: forgetPassword 
	 * @return 
	 * String
	 */
	@RequestMapping(value="/forgetPassword",method=RequestMethod.GET)
	public String forgetPassword() {
		return "user/forget_ps.jsp";
	}
	
	/**
	 * 重置密码
	 * @Title: forgetPassword 
	 * @return 
	 * String
	 */
	@RequestMapping(value="/forgetPassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> forgetPassword(String email, String emailCode, String password) {
		resultMap.put("status", 400);
		//检查手机号
		if(null == userService.selectByEmail(email)){
			resultMap.put("message", "邮箱不存在!");
			return resultMap;
		}
		//检查手机验证码
		String vPhoneCode = (String) SecurityUtils.getSubject().getSession().getAttribute(VerifyCodeUtils.V_EMAILCODE);
		if(null ==emailCode || !emailCode.equals(vPhoneCode)) {
			resultMap.put("message", "邮箱验证码错误!");
			return resultMap;
		}
		User user = userService.selectByEmail(email);
		userService.changePassword(user.getId(), password);
		resultMap.put("message", "重置密码成功!");
		resultMap.put("status", 200);
		
		return resultMap;
	}
	
	/**
	 * 用户信息显示
	 * @Title: userInfo 
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/userInfo",method=RequestMethod.GET)
	public String userInfo(Model model,HttpServletRequest request) {
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
	    
	    model.addAttribute("user", user);
	    
	    SysConfig sysConfig = sysConfigService.selectByPrimaryKey(1);
	    
		model.addAttribute("sysConfig", sysConfig);
	    
	    String contentUrl = request.getSession().getServletContext().getRealPath( "/userFiles/" ).replace("\\", "/")+ user.getId()+"/";
	    
	    File file = new File(contentUrl);
	    
	    if (!file.exists()) { // 判断文件父目录是否存在
	    	file.mkdirs();
	    }
	    long size = FileUtils.sizeOfDirectory(file);
	    
	    
	    Map<String,Object> map = new HashMap<>();
	    //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
	    if (size < 1024) {  
	        map.put("size", String.valueOf(size));
	        map.put("unit", "B");
	        model.addAttribute("map", map);
			return "user/user_info.jsp";
	    } else {  
	        size = size / 1024;  
	    }  
	    //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位  
	    //因为还没有到达要使用另一个单位的时候  
	    //接下去以此类推  
	    if (size < 1024) {
	    	map.put("size", String.valueOf(size));
	        map.put("unit", "KB");
	        model.addAttribute("map", map);
			return "user/user_info.jsp";
	    } else {  
	        size = size / 1024;  
	    }  
	    if (size < 1024) {  
	        //因为如果以MB为单位的话，要保留最后1位小数，  
	        //因此，把此数乘以100之后再取余  
	        size = size * 100;
	        map.put("size", String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)));
	        map.put("unit", "MB");
	        model.addAttribute("map", map);
			return "user/user_info.jsp";
	    } else {  
	        //否则如果要以GB为单位的，先除于1024再作同样的处理  
	        size = size * 100 / 1024;
	        map.put("size", String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)));
	        map.put("unit", "GB");
	        model.addAttribute("map", map);
			return "user/user_info.jsp";
	    }  
	}
	
	/**
	 * 更新用户信息
	 * @Title: updateUserInfo 
	 * @param user
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/updateUserInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUserInfo(User user) {
		User oldUser = userService.selectByPrimaryKey(user.getId());
		resultMap.put("status", 400);
		//检查用户名
		if(null != user.getUsername() && null != userService.selectByUsername(user.getUsername())){
			if(!oldUser.getUsername().equals(user.getUsername())) {
				resultMap.put("message", "用户名已经存在!");
				return resultMap;
			}
		}
		//检查邮箱
		if(null != user.getEmail() && null != userService.selectByEmail(user.getEmail())){
			if(!oldUser.getEmail().equals(user.getEmail())) {
				resultMap.put("message", "Email已经存在!");
				return resultMap;
			}
		}
		//检查手机号
		if(null != user.getPhone() && null != userService.selectByPhone(user.getPhone())){
			if(!oldUser.getPhone().equals(user.getPhone())) {
				resultMap.put("message", "手机号已经存在!");
				return resultMap;
			}
		}
		if(0 == userService.updateByPrimaryKey(user)) {
			resultMap.put("message", "修改失败");
			return resultMap;
		}
		resultMap.put("message", "修改成功");
		resultMap.put("status", 200);
		return resultMap;
		
	}
	
	/**
	 * 修改手机号
	 * @Title: updateUserPhone 
	 * @param phoneCode
	 * @param phone
	 * @param newPhoneCode
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/updateUserPhone",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUserPhone(String phone) {
		resultMap.put("status", 400);
		//检查手机号
		if(null != userService.selectByPhone(phone)){
			resultMap.put("message", "手机号已存在!");
			return resultMap;
		}
		
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
	    user.setPhone(phone);
		
		if(0 == userService.updateByPrimaryKey(user)) {
			resultMap.put("message", "修改失败");
			return resultMap;
		}
		resultMap.put("message", "修改成功");
		resultMap.put("status", 200);
		return resultMap;
		
	}
	
	/**
	 * 修改邮箱
	 * @Title: updateUserEmail 
	 * @param user
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/updateUserEmail",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUserEmail(String emailCode,String email,String newEmailCode) {
		resultMap.put("status", 400);
		if(!VerifyCodeUtils.verifyCode(emailCode, VerifyCodeUtils.V_EMAILCODE)) {
			resultMap.put("message", "旧邮箱验证码错误!");
			return resultMap;
		}
		
		if(!VerifyCodeUtils.verifyCode(newEmailCode, VerifyCodeUtils.V_NEWEMAILCODE)) {
			resultMap.put("message", "新邮箱验证码错误!");
			return resultMap;
		}
		//检查邮箱
		if(null != userService.selectByEmail(email)){
			resultMap.put("message", "Email已经存在!");
			return resultMap;
		}
		
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
	    user.setEmail(email);
		if(0 == userService.updateByPrimaryKey(user)) {
			resultMap.put("message", "修改失败");
			return resultMap;
		}
		resultMap.put("message", "修改成功");
		resultMap.put("status", 200);
		return resultMap;
	}
	
	/**
	 * 修改用户密码
	 * @Title: updateUserPassword 
	 * @param phoneCode
	 * @param password
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/updateUserPassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUserPassword(String emailCode,String password) {
		resultMap.put("status", 400);
		
	    //检查邮箱验证码
  		if(!VerifyCodeUtils.verifyCode(emailCode, VerifyCodeUtils.V_EMAILCODE)) {
  			resultMap.put("message", "邮箱验证码错误!");
  			return resultMap;
  		}
  		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    //获取用户
	    User user = userService.selectByUsername(username);
	    userService.changePassword(user.getId(), password);
		resultMap.put("message", "重置密码成功!");
		resultMap.put("status", 200);
		
		return resultMap;
	}
	
	/**
	 * 上传头像
	 * @Title: updateUserPhone 
	 * @param imgBase
	 * @param request
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/upPic",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUserPhone(String imgBase,HttpServletRequest request) {
		resultMap.put("status", 400);
		imgBase = StringUtils.substringAfter(imgBase,",");
		
		//获取用户名
	    String username = (String)SecurityUtils.getSubject().getPrincipal();
	    User user = userService.selectByUsername(username);
	    String upPicUrl = request.getSession().getServletContext().getRealPath( "/userFiles/" ).replace("\\", "/");
		String toImagePath = upPicUrl + user.getId()+"/"+ "headImg.jpg";
		String imageType = "jpg";
		try {
			BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] bytes1 = decoder.decodeBuffer(imgBase);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			RenderedImage bi1 = ImageIO.read(bais);
			File w2 = new File(toImagePath);// 可以是jpg,png,gif格式
			if (!w2.getParentFile().exists()) { // 判断文件父目录是否存在
				w2.getParentFile().mkdirs();
			}
			if (!w2.exists()) {
				w2.createNewFile();
			}
			ImageIO.write(bi1, imageType, w2);// 不管输出什么格式图片，此处不需改动
			
			//压缩图片
			Thumbnails.of(w2).size(200, 200).toFile(w2);
			
		    String urlPic = StringUtils.substringAfter(toImagePath, "webapps");
		    
		    user.setHeadimg(urlPic);
		    if(0 ==userService.updateByPrimaryKey(user)) {
		    	resultMap.put("message", "修改失败");
		    	return resultMap;
		    }
			resultMap.put("message", "修改成功");
			resultMap.put("status", 200);
			return resultMap;
		} catch (IOException e) {
			e.printStackTrace();
			resultMap.put("message", "修改失败");
			return resultMap;
		}
		
	}
	
	/**
	 * 管理员进入用户管理界面
	 * @Title: viewUserManage 
	 * @param page
	 * @param rows
	 * @param username
	 * @param model
	 * @return 
	 * String
	 */
	@RequestMapping(value="/admin/viewUserManage",method=RequestMethod.GET)
	public String viewUserManage(@RequestParam(value="page", defaultValue="1")Integer page, 
            @RequestParam(value="rows", defaultValue="10")Integer rows,
            @RequestParam(value="username", required=false)String username,
            Model model) {
		Map<String, Object> map = userService.findAll(page, rows, username);
		
		Map<String, Object> roleMap = roleService.findAll();
		
		model.addAttribute("roleList", roleMap.get("list"));
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("total", map.get("total"));
		model.addAttribute("page", page);
		model.addAttribute("rows", rows);
		model.addAttribute("username", username);
		
		return "admin/usermanage.jsp";
	}
	
	/**
	 * 管理员注册新用户
	 * @Title: insertUserInfo 
	 * @param user
	 * @param attributes
	 * @return 
	 * String
	 */
	@RequestMapping(value="/admin/insertUserInfo",method=RequestMethod.POST)
	public String insertUserInfo(User user,RedirectAttributes attributes) {
		//检查用户名
		if(null != userService.selectByUsername(user.getUsername())){
			attributes.addFlashAttribute("msg", "用户名已经存在!");
			return "redirect:/admin/viewUserManage";
		}
		//检查邮箱
		if(null != userService.selectByEmail(user.getEmail())){
			attributes.addFlashAttribute("msg", "Email已经存在!");
			return "redirect:/admin/viewUserManage";
		}
		//检查手机号
		if(null != userService.selectByPhone(user.getPhone())){
			attributes.addFlashAttribute("msg", "手机号已经存在!");
			return "redirect:/admin/viewUserManage";
		}
		//设置默认密码为手机号
		user.setPassword(user.getPhone());
		
		if(0 != userService.insert(user)) {
			attributes.addFlashAttribute("msg", "注册成功!");
		}else {
			attributes.addFlashAttribute("msg", "注册失败!");
		}
		return "redirect:/admin/viewUserManage";
	}
	
	/**
	 * 获取用户信息
	 * @Title: getUserInfo 
	 * @param id
	 * @return 
	 * User
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value="/admin/getUserInfo",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserInfo(Integer id) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = userService.selectByPrimaryKey(id);
		String json = mapper.writeValueAsString(user);
		return json;
	}
	
	/**
	 * 管理员禁用账户
	 * @Title: forbidUser 
	 * @param ids
	 * @param cmd
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="admin/forbidUser{cmd}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> forbidUser(Integer[] ids, @PathVariable String cmd) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		if(null != ids && Arrays.asList(ids).contains(1) ) {
			resultMap.put("message", "管理员账号不能禁用!");
			return resultMap;
		}
		
		if(null != ids && ids.length == userService.forbidUserByIds(ids,cmd)) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功!");
		}
		
		return resultMap;
	}
	
	/**
	 * 管理员重置用户密码
	 * @Title: resetPassword 
	 * @param ids
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/admin/resetPassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> resetPassword(Integer[] ids) {
		resultMap.put("status", 400);
		resultMap.put("message", "操作失败!");
		
		if(null != ids && ids.length == userService.resetPasswordByIds(ids)) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功,密码重置已为用户手机号!");
		}
		
		return resultMap;
	}
	
	/**
	 * 批量添加用户到组
	 * @Title: updateUserOrg 
	 * @param organizationId
	 * @param ids
	 * @return 
	 * Map<String,Object>
	 */
	@RequestMapping(value="/user/updateUserOrg",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUserOrg(Integer organizationId,Integer[] ids) {
		resultMap.put("status", 400);
		resultMap.put("message", "添加失败!");
		
		if(null != ids && ids.length == userService.updateUserOrg(organizationId, ids)) {
			resultMap.put("status", 200);
			resultMap.put("message", "添加成功!");
		}
		
		return resultMap;
	}
	
	/**
	 * 根据组ID获取组内成员
	 * @Title: getUserByOrg 
	 * @param organizationId
	 * @return 
	 * String
	 */
	@RequestMapping(value="/user/getUserByOrg",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserByOrg(Integer organizationId) {
		
		try {
			List<User> orgUserList = userService.findAll(organizationId);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOrgUserList = objectMapper.writeValueAsString(orgUserList);
			
			return jsonOrgUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据用户名获取列表
	 * @Title: getOrgUserByName 
	 * @param isOrg
	 * @param username
	 * @return 
	 * String
	 */
	@RequestMapping(value="/user/getOrgUserByName",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getOrgUserByName(Integer isOrg,String username) {
		
		try {
			List<User> orgUserList = userService.findOrgAll(isOrg,username);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOrgUserList = objectMapper.writeValueAsString(orgUserList);
			
			return jsonOrgUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有用户信息
	 * @Title: findAllUser 
	 * @param username
	 * @return 
	 * String
	 */
	@RequestMapping(value="/user/findStrangerList",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findAllUser(String username) {
		
		try {
			Map<String, Object> map = userService.findAll(1, 10, username);
			@SuppressWarnings("unchecked")
			List<User> userList = (List<User>) map.get("list");
			
			//获取我的好友
			String me = (String)SecurityUtils.getSubject().getPrincipal();
			User user = userService.selectByUsername(me);
			List<Friends> myFriendsList = friendsService.findAllMyFriends(user.getId(), null,null);
			
			//排除已加好友
			if(null != userList && userList.size()>0 && null != myFriendsList && myFriendsList.size()>0) {
				Iterator<User> iterator = userList.iterator();
				User user1;
				while(iterator.hasNext()) {
					user1 = iterator.next();
					if(user1.getId() == user.getId()) {
						iterator.remove();
						continue;
					}
					for(int i=0;i<myFriendsList.size();i++) {
						if(user1.getId() == myFriendsList.get(i).getFriendId()) {
							iterator.remove();
							break;
						}
					}
					
				}
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			String userListstr = objectMapper.writeValueAsString(userList);
			
			
			return userListstr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
	/**
	 * 查找所有组内成员
	 * @Title: getOrgAllByName 
	 * @param username
	 * @return 
	 * String
	 */
	@RequestMapping(value="/user/getOrgAllByName",method=RequestMethod.GET, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getOrgAllByName(String username) {
		try {
			List<User> orgUserList = userService.findOrgAll(username);
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOrgUserList = objectMapper.writeValueAsString(orgUserList);
			return jsonOrgUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 批量移除成员组ID
	 * @Title: removeOrgByIds 
	 * @param ids
	 * @return 
	 * Map<String,Object>
	 */
	@RequiresPermissions("organization:delete")
	@RequestMapping(value="/user/removeOrgByIds",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> removeOrgByIds(Integer[] orgerIds) {
		resultMap.put("status", 400);
		resultMap.put("message", "移除失败!");
		
		if(null != orgerIds && orgerIds.length == userService.removeOrgByIds(orgerIds)) {
			resultMap.put("status", 200);
			resultMap.put("message", "移除成功!");
		}
		
		return resultMap;
	}
	
	
}
