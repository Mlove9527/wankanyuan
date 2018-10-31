<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>找回密码</title>
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
                    <div class="loginT">找回密码</div>
                    <a href="/wankangyuan/login">
                        <div class="loginBB1">马上登录</div>
                    </a>
                    <form id="_form" method="post">
                    <div class="loginM">
                        <div class="loginMz">
                            <!-- <div class="loginMzL">账户名称：</div> -->
                            <div class="loginMzR">
                                <input id="email" name="email" type="text" class="loginMzRp for_zh" placeholder="请输入邮箱" />
                            </div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzR">
                                <input id="emailCode" name="emailCode" type="text" class="loginMzRp3" placeholder="验证码" />
                                <div class="loginMzRbK">
                                    <input id="sendEmailCode" type="button" class="loginMzRb" value="发送验证码" />
                                    <div class="loginMzRb2"></div>
                                </div>
                            </div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzR">
                                <input id="password" name="password" type="password" class="loginMzRp for_ps" placeholder="请输入新密码" />
                            </div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzR">
                                <input id="re_password" type="password" class="loginMzRp for_ps2" placeholder="请再次输入新密码" />
                            </div>
                        </div>
                        
                    </div>
                    </form>
                    <div class="loginM3">
                        <div class="loginMt">*邮箱不能接受验证码，请联系客服</div>
                    </div>
                    <div class="loginB">
                        <input type="button" class="loginb pro_enter" value="确认" onclick="resetPassword()" />
                    </div>
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
$("#sendEmailCode").click(function(){
	var anniu=document.querySelectorAll('.loginMzRb')[0];//发送验证码
    var anniu2=document.querySelectorAll('.loginMzRb2')[0];//禁用发送验证码
    
    var email = $("#email").val();
    var emailReg =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!emailReg.test(email)) {  
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

function resetPassword(){
	if($("#email").val().trim() == ''){
        return layer.msg('请输入手机号',function(){}),!1;
    }
	
	if($('#emailCode').val().length != 6){
        return layer.msg('手机验证码的长度为6位！',function(){}),!1;
    }
	
	if($("#password").val().length < 8){
        return layer.msg('密码不少于8个字符！',function(){}),!1;
    }
	
	if($("#password").val() != $("#re_password").val()){
        return layer.msg('2次密码输出不一样！',function(){}),!1;
    }
	
	var load = layer.load();
    $.post("/wankangyuan/forgetPassword",$("#_form").serialize() ,function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg('密码重置成功,请重新登录!', {
                anim: 0,
                end: function (index) {
                    window.location.href= "/wankangyuan/login";
                }
            });
            
        }
    },"json");
	
}
</script>
</body>
</html>