<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/project1.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        project0();
        // project1();
        // pro_mine();
        // user_info();
        // system_message();
        // friend_manage();
        message_list();
    }
</script>
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
                    <div class="yanjiuquan active">
                        <div class="yanjiuquanT">研究圈</div>
                        <c:if test="${friendMSG}">
                            <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint" />
                        </c:if>
                    </div>
                </a>
            </div>
            <div class="top2">
                <div class="top2C">
                    <a href="/wankangyuan/friends/viewFriendsManage"><div class="top2Cli">好友管理</div></a>
                    <a href="/wankangyuan/friendMessage/viewFriendMessage">
                        <div class="top2Cli top2CliYJ">好友消息
                        <c:if test="${friendMSG}">
                            <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint3" />
                        </c:if>
                        </div>
                    </a>
                    <div class="top2CrB">
                        <div class="top2Crb" onclick="clearAllMessage()" >清空所有消息</div>
                    </div>
                </div>
            </div>
            <div class="messagelistM">
                <div class="messMK">
                <c:forEach items="${list }" var="friendMessage">
                    <div class="messMKz">
                        <img src="${friendMessage.senderHeadimg }" alt="" class="messMKzi" />
                        <div class="messMKzt">
                            <div class="messMKztT">
                                <div class="messMKztTid">${friendMessage.senderName }</div>
                                <div class="messMKztTtime">
                                    <span><fmt:formatDate type="both" value="${friendMessage.sendTime}" /></span>
                                </div>
                            </div>
                            <div class="messMKztB">${friendMessage.content }</div>
                        </div>
                        <a href="/wankangyuan/friendMessage/viewSendMessage?objId=${friendMessage.senderId }">
                            <div class="messMKzb">回复</div>
                        </a>
                    </div>
                </c:forEach>
                </div>
                
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
<c:if test="${not empty msg}">
    <script type="text/javascript">
    layer.msg("${msg}");
    </script>
</c:if>
<script type="text/javascript">
function clearAllMessage() {
	layer.confirm('清空后不能撤销，请确认是否清空?',{
        btn: ['确认','取消'], //按钮
        icon: 2
      }, function(){
          var load = layer.load();
            $.post("/wankangyuan/friendMessage/clearAllMessage",{},function(result){
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
        
      }, function(){
          return;
      });
}
</script>

</body>
</html>