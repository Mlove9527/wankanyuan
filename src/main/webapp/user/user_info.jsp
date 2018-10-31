<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/project1.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        project0();
        // project1();
        // pro_mine();
        user_info();
    }
</script>
</head>
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
                <div class="yanjiuquan">
                    <div class="yanjiuquanT">研究圈</div>
                    <c:if test="${friendMSG}">
                        <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint" />
                    </c:if>
                </div>
                </a>
            </div>
            <div class="updateinfoK update_passwK">
            <form id="passwordForm" method="post">
                <input name="id" type="hidden" value="${user.id }">
                <div class="updateinfoRc">
                    <div class="updateinfoRcz">
                        <input type="text" class="updateinfoRct1 udPSphone" disabled="disabled" value="${user.email }" />
                    </div>
                    <div class="updateinfoRcz">
                        <input id="emailCode_ps" name="emailCode" type="text" class="updateinfoRct2 updateQLk" placeholder="请输入验证码" />
                        <div id="sendEmailCode1" class="updateinfoRcb udPSyzfs">发送</div>
                        <div class="updateinfoRcb2 udPSyzfs2"></div>
                    </div>
                    <div class="updateinfoRcz">
                        <input id="password" name="password" type="password" class="updateinfoRct1 updateQLk"  placeholder="请输入密码" />
                    </div>
                    <div class="updateinfoRcz">
                        <input id="re_password"  type="password" class="updateinfoRct1 updateQLk" placeholder="请再次输入密码" />
                    </div>
                    <div class="updateinfoRB">
                        <div class="udinfoRbca">取消</div>
                        <div class="udinfoRben" onclick="updateUserPassword()" >确定修改</div>
                    </div>
                </div>
            </form>
            </div>
            <div class="updateinfoK update_phoneK">
            <form id="phoneForm" method="post">
                <div class="updateinfoRc">
                    <div class="updateinfoRcz">
                        <input id="oldPhone" type="text" class="updateinfoRct1 udPHphone" disabled="disabled" value="${user.phone }" />
                    </div>
                    <div class="updateinfoRcz">
                        <input id="newPhone" name="phone" type="number" class="updateinfoRct1 udPHphone2 updateQLk" placeholder="请输入新手机号"  />
                    </div>
                    <div class="updateinfoRB">
                        <div id="phoneClose" class="udinfoRbca">取消</div>
                        <div class="udinfoRben" onclick="updateUserPhone()">确定修改</div>
                    </div>
                </div>
            </form>
            </div>
            <div class="updateinfoK update_emailK">
            <form id="emailForm" method="post">
                <input name="id" type="hidden" value="${user.id }">
                <div class="updateinfoRc">
                    <div class="updateinfoRcz">
                        <input type="text" class="updateinfoRct1 udEMemail" disabled="disabled" value="${user.email }"   />
                    </div>
                    
                    <div class="updateinfoRcz">
                        <input id="emailCode_em" name="emailCode" type="text" class="updateinfoRct2 updateQLk" placeholder="请输入验证码" />
                        <div id="sendEmailCode" class="updateinfoRcb udPHyzfs">发送</div>
                        <div class="updateinfoRcb2 udPHyzfs2"></div>
                    </div>
                    
                    <div class="updateinfoRcz">
                        <input id="newEmail" name="email" type="text" class="updateinfoRct1 udEMemail" placeholder="请输入新邮箱地址"  />
                    </div>
                    
                    <div class="updateinfoRcz">
                        <input id="newEmailCode" name="newEmailCode" type="text" class="updateinfoRct2 updateQLk" placeholder="请输入验证码" />
                        <div id="sendNewEmailCode" class="updateinfoRcb udPHyzfs3">发送</div>
                        <div class="updateinfoRcb2 udPHyzfs4"></div>
                    </div>
                    
                    <div class="updateinfoRB">
                        <div class="udinfoRbca">取消</div>
                        <div class="udinfoRben" onclick="updateUserEmail()">确定修改</div>
                    </div>
                </div>
            </form>
            </div>
            <div class="userM">
                <div class="userML">
                    <div class="userMLi">
                        <img src="${user.headimg }" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="userMLi" />
                    </div>
                    <div class="userMLtK">
                        <div class="userMLt">${user.username }</div>
                    </div>
                    <div class="userMLtabK">
                        <div class="userMLtab active">
                            <div class="userMLtabi1"></div>
                            <div class="userMLtabt">基本信息</div>
                        </div>
                        <div class="userMLtab">
                            <div class="userMLtabi2"></div>
                            <div class="userMLtabt">账号设置</div>
                        </div>
                    </div>
                </div>
                <div class="userMR">
                    <div class="touxiangupK">
                        <div class="touxiangupT">
                            <div class="touxiangupTt">上传头像</div>
                            <div class="touxiangupTx"></div>
                        </div>
                        <div class="touxiangupM">
                            <div class="touxiangupMz touxiangupLJ">&nbsp;</div>
                            <input id="headImg" type="file" class="touxiangupp updateQLk" style="display:none;" />
                        </div>
                        <div class="touxiangupB">
                            <div class="touxiangupb touxiang_sele">选择图片</div>
                            <div class="touxiangupb touxiang_up" onclick="updateHeadImg()">上传图片</div>
                        </div>
                    </div>
                    <div class="userMRT">
                        <img src="${user.headimg }" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="userMRTi" />
                        <div class="userMRTt">
                            <span class="userMRTts">更改头像</span>
                        </div>
                    </div>
                    
                    <div class="userMRM">
                        <div class="userMRMtabK">
                            <div class="userMRMtab active">
                            <form id="userForm" method="post">
                                <input name="id" type="hidden" value="${user.id }">
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt">性别：</div>
                                    <div class="userMRMtabzrK">
                                        <input type="radio" name="gender" class="userMRMtabzr" value="0" <c:if test="${'0' eq user.gender }">checked="checked"</c:if> /><span>女</span>
                                        <input type="radio" name="gender" class="userMRMtabzr" value="1" <c:if test="${'1' eq user.gender }">checked="checked"</c:if> /><span>男</span>
                                        
                                    </div>
                                </div>
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt">研究方向：</div>
                                    <input name="researchDirection" type="text" class="userMRMtabzp" value="${user.researchDirection }" />
                                </div>
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt" style="vertical-align:top;">个人简介：</div>
                                    <textarea name="personalProfile" id="" class="userMRMtabzta">${user.personalProfile }</textarea>
                                </div>
                                <div class="userMRMtabz">
                                    <div class="user_kongjianT"><span>${map.size }</span>${map.unit}/<span></span>GB</div>
                                </div>
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt">个人存储空间：</div>
                                    <div class="user_kongjianK">
                                        <div class="user_kongjianZ"></div>
                                    </div>
                                </div>
                                <div class="userMRMtabB">
                                    <div class="userMRMtabb" onclick="updateUserInfo()">保存信息</div>
                                </div>
                            </form>
                            </div>
                            <div class="userMRMtab">
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt2">用户名：</div>
                                    <div id="user_username" class="userMRMtabzt3">
                                        <div class="userMRMtabzt3s">${user.username }</div>
                                    </div>
                                    <!-- <div id="" class="user_mo_name">修改</div> -->
                                </div>
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt2">手机号：</div>
                                    <div id="" class="userMRMtabzt3">
                                        <div id="phone_div" class="userMRMtabzt3s">${user.phone }</div>
                                    </div>
                                    <div id="" class="user_userb user_mo_phone">修改</div>
                                </div>
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt2">邮箱：</div>
                                    <div id="" class="userMRMtabzt3">
                                        <div class="userMRMtabzt3s">${user.email }</div>
                                    </div>
                                    <div id="" class="user_userb user_mo_email">修改</div>
                                </div>
                                <div class="userMRMtabz">
                                    <div class="userMRMtabzt2">密码：</div>
                                    <div id="" class="userMRMtabzt3">
                                        <div class="userMRMtabzt3s">密码至少8位</div>
                                    </div>
                                    <div id="" class="user_userb user_mo_passw">修改</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="userMR2"></div>
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
    
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

<c:if test="${not empty error}">
    <script type="text/javascript">
    layer.msg("${error}");
    </script>
</c:if>
<script type="text/javascript">
var fileSize = ${map.size};
var fileUnit = '${map.unit}';
var realSize
if(fileUnit == 'B'){
    realSize = fileSize/1024/1024/1024;
}
if(fileUnit == 'KB'){
    realSize = fileSize/1024/1024;
}
if(fileUnit == 'MB'){
    realSize = fileSize/1024;
}
if(fileUnit == 'GB'){
    realSize = fileSize;
}
kongjian(${sysConfig.size },realSize);
$("#sendEmailCode,#sendEmailCode1").click(function(){
	var anniu=document.querySelectorAll('.udPSyzfs')[0];//发送验证码
    var anniu2=document.querySelectorAll('.udPSyzfs2')[0];//禁用发送验证码
	var anniud=document.querySelectorAll('.udPHyzfs')[0];//发送验证码
    var anniud2=document.querySelectorAll('.udPHyzfs2')[0];//禁用发送验证码
    
    
    var email = "${user.email}";
    anniu.style.display="none";
    anniu2.style.display="block";
    anniud.style.display="none";
    anniud2.style.display="block";

    var time= 60;
    var YZMjishi=0;
    anniu2.innerHTML=time+"s后重试";
    anniud2.innerHTML=time+"s后重试";

    YZMjishi=setInterval(function(){
        time--;
        anniu2.innerHTML=time+"s后重试";
        anniud2.innerHTML=time+"s后重试";
        if(time<=0){
            clearInterval(YZMjishi);
            anniu.style.display="block";
            anniu2.style.display="none";
            anniud.style.display="block";
            anniud2.style.display="none";
        }
    },1000);
	
  $.get("/wankangyuan/open/getEmailCode?email="+email, function(result){
    if(result != 0){
        layer.msg("已发送验证码，请注意接收!");
    }else{
        layer.msg("验证码发送失败，请联系管理员!");
    }
  });
});
$("#sendNewEmailCode").click(function(){
	var anniud3=document.querySelectorAll('.udPHyzfs3')[0];//发送验证码
    var anniud4=document.querySelectorAll('.udPHyzfs4')[0];//禁用发送验证码
    anniud3.style.display="none";
    anniud4.style.display="block";
    var email = $("#newEmail").val();
    var time= 60;
    var YZMjishi=0;
    anniud4.innerHTML=time+"s后重试";
    YZMjishi=setInterval(function(){
        time--;
        anniud4.innerHTML=time+"s后重试";
        if(time<=0){
            clearInterval(YZMjishi);
            anniud3.style.display="block";
            anniud4.style.display="none";
        }
    },1000);
    
  $.get("/wankangyuan/open/getNewEmailCode?email="+email, function(result){
    if(result != 0){
        layer.msg("已发送验证码，请注意接收!");
    }else{
        layer.msg("验证码发送失败，请联系管理员!");
    }
  });
});
function updateUserInfo() {
    var load = layer.load();
    $.post("/wankangyuan/updateUserInfo",$("#userForm").serialize() ,function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg(result.message, {
                anim: 0,
                end: function (index) {
                    window.location.reload();
                }
            });
        }
    },"json");
    
    $("#sendPhoneCode").click(function(){
        var phone = $("#phone").val();
      $.get("/wankangyuan/open/getPhoneCode?phone="+phone, function(result){
        if(result != 0){
            layer.msg("已发送验证码，请注意接收!");
        }else{
            layer.msg("验证码发送失败，请联系管理员!");
        }
      });
    });
}
function updateUserPhone(){
	
	var phoneReg = /(^1[3|4|5|6|7|8]\d{9}$)|(^09\d{8}$)/;  
    var phone = $('#newPhone').val();  
    if (!phoneReg.test(phone)) {  
        return layer.msg('请输入有效的手机号码！',function(){}),!1;
    }else{
		var load = layer.load();
		$.post("/wankangyuan/updateUserPhone",$("#phoneForm").serialize() ,function(result){
		    layer.close(load);
		    if(result && result.status!= 200){
		        return layer.msg(result.message,function(){}),!1;
		    }else{
		        layer.msg(result.message, {
		            anim: 0,
		            end: function (index) {
		                window.location.reload();
		            }
		        });
		    }
		},"json");
    }
}
function updateUserEmail() {
	if($('#emailCode_em,#newEmailCode').val().length != 6){
        return layer.msg('验证码的长度为6位！',function(){}),!1;
    }
	
	
    var load = layer.load();
    $.post("/wankangyuan/updateUserEmail",$("#emailForm").serialize() ,function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg(result.message, {
                anim: 0,
                end: function (index) {
                    window.location.reload();
                }
            });
        }
    },"json");
}
function updateUserPassword() {
    if($("#password").val().length < 8){
        return layer.msg('密码不少于8个字符！',function(){}),!1;
    }
    
    if($("#password").val() != $("#re_password").val()){
        return layer.msg('2次密码输出不一样！',function(){}),!1;
    }
    
    if($('#emailCode_ps').val().length != 6){
        return layer.msg('验证码的长度为6位！',function(){}),!1;
    }
    var load = layer.load();
    $.post("/wankangyuan/updateUserPassword",$("#passwordForm").serialize() ,function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg(result.message, {
                anim: 0,
                end: function (index) {
                    window.location.reload();
                }
            });
        }
    },"json");
}
function updateHeadImg() {
    var file = $("#headImg")[0].files[0];
    if(!/image\/\w+/.test(file.type)) {
        return layer.msg('请确保文件为图像文件',function(){}),!1;
    }
    
    if(file.size >= 1024*1024*2){
        return layer.msg('头像大小超过2M,请更换图片!',function(){}),!1;
    }
    
    var reader=new FileReader();
    reader.readAsDataURL(file);
    reader.onload=function(e) {
        var load = layer.load();
        $.post("/wankangyuan/upPic",{
            imgBase : this.result
        },function(result){
            layer.close(load);
            if(result && result.status!= 200){
                return layer.msg(result.message,function(){}),!1;
            }else{
                layer.msg(result.message, {
                    anim: 0,
                    end: function (index) {
                        window.location.reload();
                    }
                });
            }
        },"json");
        
    }
}
</script>

</body>

</html>