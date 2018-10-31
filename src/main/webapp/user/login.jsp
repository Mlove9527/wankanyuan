<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>登录</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/index.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
    	project0();
        // index();
        login();
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
                    <div class="loginT">登录</div>
                    <form action="/wankangyuan/login" method="post">
                    <div class="loginM">
                        <div class="loginMz">
                            <input name="loginType" type="hidden" value="userLogin">
                            <div class="loginMzL">账户名称：</div>
                            <div class="loginMzR">
                                <input id="username" class="loginMzRp" type="text" name="username" value="<shiro:principal/>" placeholder="用户名/邮箱/手机" required="required" >
                            </div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzL">密码：</div>
                            <div class="loginMzR">
                                <input id="password" type="password" class="loginMzRp" name="password" required="required">
                            </div>
                        </div>
                        <div class="loginMz">
                            <div class="loginMzL">动态验证码：</div>
                            <div class="loginMzR">
                                <input id="randomcode" type="text" class="loginMzRp2" name="randomcode" required="required" />
                                <div class="loginMzRiK">
                                    <img src="/wankangyuan/open/getGifCode" class="loginMzRi" onclick="random(this)" title="点击更换"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="loginM2">
                        <a href="/wankangyuan/forgetPassword">
                            <div class="loginM2z">忘记密码？</div>
                        </a>
                        <a href="/wankangyuan/register">
                            <div class="loginM2z">还没有账号？</div>
                        </a>
                    </div>
                    <div class="login_b">
                        <input type="submit" class="loginb" value="登录" >
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
</body>
<body>

<%-- <div class="error">${error}</div> --%>
<%-- <div style="width:100%;text-align:center;">
<form action="" method="post">
    用户名：<input id="username" type="text" name="username" value="<shiro:principal/>" required="required" ><br/>
    密码：<input id="password" type="password" name="password" required="required"><br/>
    <!-- 自动登录：<input type="checkbox" name="rememberMe" value="true"><br/> -->
    验证码：<input id="randomcode" type="text" name="randomcode" required="required" /> 
    <img src="/wankangyuan/open/getGifCode" onclick="random(this)"/><br/>
    <input type="submit" value="登录" onclick="to_verify()">
</form>
</div> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

<shiro:authenticated>
    <script type="text/javascript">
    layer.msg("您已经登录");
    layer.confirm('您已经登录，请问是否重新登录？', {
    	  btn: ['重新登录','跳回项目页'] //按钮
    	}, function(){
    	    window.location.href='/wankangyuan/logout';
    	}, function(){
    		window.location.href='/wankangyuan/project/selectMyProject';
    	});
    </script>
</shiro:authenticated>
<c:if test="${not empty error}">
    <script type="text/javascript">
    layer.msg("${error}");
    </script>
</c:if>
<c:if test="${not empty msg}">
    <script type="text/javascript">
    layer.msg("${msg}");
    </script>
</c:if>
<script type="text/javascript">
   function random(tmp){  
        tmp.src = '/wankangyuan/open/getGifCode?'  + Math.random();
   }
   
</script>  
</body>
</html>