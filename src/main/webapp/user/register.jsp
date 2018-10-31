<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>注册</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/index.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
    	project0();
        // index();
        forget_ps();
    }
</script>
<body>
    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="<%=request.getContextPath()%>/static/img/newlogo2.png" class="logo" height="70" width="218" alt="" /></h1>
            </div>
            <div class="xiangmu">
                <div class="loginK">
                    <div class="loginT">注册</div>
                    <a href="/wankangyuan/login">
                        <div class="loginBB1">已有账号</div>
                    </a>
                    
                    <form id="_form" method="post">
                    <div class="loginM_2">
                        <div class="loginMz">
                            <div class="loginMzL">账户名称：</div>
                            <div class="loginMzR">
                                <input id="username" name="username" type="text" class="loginMzRp" placeholder="" />
                            </div>
                            <div class="loginMzt">*账户名称由汉字、英文字母、 数字和下划线组成，3-20位！</div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzL">密码：</div>
                            <div class="loginMzR">
                                <input id="password" name="password" type="password" class="loginMzRp" placeholder="" />
                            </div>
                            <div class="loginMzt">*不少于8个字符</div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzL">再次输入：</div>
                            <div class="loginMzR">
                                <input id="re_password" type="password" class="loginMzRp" placeholder="" />
                            </div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzL">手机号：</div>
                            <div class="loginMzR">
                                <input id="phone" name="phone" type="text" class="loginMzRp for_zh" placeholder="" />
                            </div>
                            <div class="loginMzt">*请填写常用手机号</div>
                        </div>
                        
                        <div class="loginMz">
                            <div class="loginMzL">邮箱：</div>
                            <div class="loginMzR">
                                <input id="email" name="email" type="email" class="loginMzRp for_zh" placeholder="" />
                            </div>
                            <div class="loginMzt">*请务必正确填写常用邮箱</div>
                        </div>
                        
                        <div class="loginMz">
                            <div class="loginMzL">验证码：</div>
                            <div class="loginMzR">
                                <input id="emailCode" name="emailCode" type="text" class="loginMzRp3" placeholder="" />
                                <div class="loginMzRbK">
                                    <input id="sendPhoneCode" type="button" class="loginMzRb" value="发送验证码" />
                                    <div class="loginMzRb2"></div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="loginMz">
                            <div class="loginMzL">动态验证码：</div>
                            <div class="loginMzR">
                                <input name="randomCode" type="text" class="loginMzRp2" />
                                <div class="loginMzRiK">
                                    <img src="/wankangyuan/open/getGifCode" class="loginMzRi" onclick="random(this)" title="点击更换"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- <div class="loginM3">
                        <div class="loginMt">*手机号不能接受验证码，请联系客服</div>
                    </div> -->
                    <div class="loginB">
                        <input type="button" class="loginb pro_enter" value="注册" onclick="register()" />
                    </div>
                    </form>
                </div>
            </div>
            <div class="bottom">
                <a href="javascript:;">
                    <div class="bottomL">关于我们</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">用户协议</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">企业服务</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">帮助中心</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">联系我们</div>
                </a>
                <a href="javascript:;">
                    <img src="<%=request.getContextPath()%>/static/img/weixin.png" alt="" class="bottomR" />
                </a>
                <a href="javascript:;">
                    <img src="<%=request.getContextPath()%>/static/img/weibo.png" alt="" class="bottomR" />
                </a>
            </div>
            <div class="bottom2">
                <div class="bottom2z">京ICP备09083200号 合字B2-20160007 人才服务许可证:120116174002号 京公网安备 11010502035189号</div>
                <div class="bottom2z">Copyright © 2006-2018 xxxxxx.com All Rights Reserved</div>
            </div>
        </div>
    </div>
    
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

<c:if test="${not empty error}">
    <script type="text/javascript">
    layer.msg("${error}");
    </script>
</c:if>
<script type="text/javascript">
$("#sendPhoneCode").click(function(){
	var anniu=document.querySelectorAll('.loginMzRb')[0];//发送验证码
    var anniu2=document.querySelectorAll('.loginMzRb2')[0];//禁用发送验证码
	
	var email = $("#email").val();
	var emailReg =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!emailReg.test(email)) {  
		$(".loginMzRi").click();
        return layer.msg('请输入有效的邮箱！',function(){}),!1;
    }else{
        anniu.style.display="none";
        anniu2.style.display="block";

        var time= 60;
        var YZMjishi=0;
        anniu2.innerHTML=time+"s后重试";
    
        YZMjishi=setInterval(function(){
            time--;
            anniu2.innerHTML=time+"s后重试";
            if(time<=0){
                clearInterval(YZMjishi);
                anniu.style.display="block";
                anniu2.style.display="none";
            }
        },1000);
    }
	
  $.get("/wankangyuan/open/getEmailCode?email="+email, function(result){
    if(result != 0){
    	layer.msg("已发送验证码，请注意查收!");
    }else{
    	layer.msg("验证码发送失败，请联系管理员!");
    }
  });
});

function register(){
	
	if(!$("#username").val().match(/^[\u4E00-\u9FA5a-zA-Z0-9_]{3,20}$/)){
		$(".loginMzRi").click();
		return layer.msg('账户名称不规范！',function(){}),!1;
	}
	
	if($("#password").val().length < 8){
		$(".loginMzRi").click();
		return layer.msg('密码不少于8个字符！',function(){}),!1;
	}
	
	if($("#phone").val().trim() != ''){
		var phoneReg = /(^1[3|4|5|6|7|8]\d{9}$)|(^09\d{8}$)/;  
	    var phone = $("#phone").val();  
	    if (!phoneReg.test(phone)) {  
	    	$(".loginMzRi").click();
	        return layer.msg('请输入有效的手机号码！',function(){}),!1;
	    }
	}
	
	if($("#password").val() != $("#re_password").val()){
		$(".loginMzRi").click();
        return layer.msg('2次密码输出不一样！',function(){}),!1;
    }
	
	if($('#emailCode').val().length != 6){
		$(".loginMzRi").click();
        return layer.msg('邮箱验证码的长度为6位！',function(){}),!1;
    }
	
	//$("#_form").submit();
	var load = layer.load();
    $.post("/wankangyuan/register",$("#_form").serialize() ,function(result){
        layer.close(load);
        if(result && result.status!= 200){
        	$(".loginMzRi").click();
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg('注册成功!', {
                anim: 0,
                end: function (index) {
	            	window.location.href= "/wankangyuan/login";
                }
            });
            
        }
    },"json");
	
}

function random(tmp){  
     tmp.src = '/wankangyuan/open/getGifCode?'  + Math.random();
}
   
</script>  
</body>
</html>