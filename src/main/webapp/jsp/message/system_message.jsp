<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
        system_message();
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
                    <a href="user_info.html">
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
                            <div class="systemMLtabi1"></div>
                            <div class="userMLtabt">系统通知</div>
                        </div>
                    </div>
                </div>
                <div class="userMR">
                    <div class="userMRM">
                        <div class="userMRMtabK">
                            <div class="sysMRMtab active">
                                <div class="messageT">
                                    <div class="messageTK1">
                                        <div id="allMessage" class="messageT1z active">全部通知</div>
                                        <div id="systemMessage" class="messageT1z">系统通知</div>
                                        <div id="friendRequest" class="messageT1z">好友申请</div>
                                        <div id="topicReply" class="messageT1z">话题回复</div>
                                    </div>
                                    <div class="messageTK2">
                                        <div class="messageT2z" onclick="clearAllMessage()">清空所有消息</div>
                                    </div>
                                </div>
                                <div class="messageM">
                                    <div class="messageMzK active">
                                    <c:forEach items="${list }" var="message">
                                        <div name="${message.id }" class="messageMz">
                                            <div class="messageMzik">
                                                <img src="${message.headimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="messageMzi" />
                                            </div>
                                            <div class="messageMztk">
                                                <div class="messageMztk_1">
                                                    <div class="messageMztk_1t1">
                                                    <c:choose>
                                                        <c:when test="${message.type eq 0 }">系统通知</c:when>
                                                        <c:when test="${message.type eq 1 }">好友申请</c:when>
                                                        <c:when test="${message.type eq 2 }">话题回复</c:when>
                                                    </c:choose> 
                                                    </div>
                                                    <div class="messageMztk_1t2">
                                                        <span><fmt:formatDate type="both" value="${message.createTime}" /></span>
                                                    </div>
                                                </div>
                                                <div class="messageMztk_2">
                                                    <div class="messageMztk_2t">${message.content}</div>
                                                </div>
                                            </div>
                                            <div class="messageMzbk">
                                                <c:if test="${0 ne message.status }">
                                                    <div class="messageMzb messageMzdel" onclick="deleteMessageById(${message.id})">删除</div>
                                                </c:if>
                                                <c:choose>
                                                    <c:when test="${message.type eq 0 }">
                                                        <c:choose>
                                                        <c:when test="${0 eq message.status}">
                                                            <div class="messageMzb messageMzagree" onclick="dealAddNewOrgRequest(${message.id},1)">通过</div>
                                                            <div class="messageMzb messageMzrefuse" onclick="dealAddNewOrgRequest(${message.id},0)">拒绝</div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="messageMzbt">${message.result }</div>
                                                        </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:when test="${message.type eq 1 }">
                                                        <c:choose>
			                                            <c:when test="${0 eq message.status}">
			                                                <div class="messageMzb messageMzagree" onclick="dealFriendRequest(${message.id},1)">通过</div>
			                                                <div class="messageMzb messageMzrefuse" onclick="dealFriendRequest(${message.id},0)">拒绝</div>
			                                            </c:when>
			                                            <c:otherwise>
			                                                <div class="messageMzbt">${message.result }</div>
			                                            </c:otherwise>
			                                            </c:choose>
                                                    </c:when>
                                                    <c:when test="${message.type eq 2 }">
                                                        <div class="messageMzb messageMzsee" onclick="location='/wankangyuan/projectTopic/selectProjectTopicFollow?project_topic_id=${message.objId }'">查看</div>
                                                    </c:when>
                                                </c:choose> 
                                            </div>
                                        </div>
                                    </c:forEach>
                                    </div>

                                    <div class="messageMzK">
                                    <c:forEach items="${list }" var="message">
                                    <c:if test="${0 eq message.type }">
                                        <div name="${message.id }" class="messageMz">
                                            <div class="messageMzik">
                                                <img src="${message.headimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="messageMzi" />
                                            </div>
                                            <div class="messageMztk">
                                                <div class="messageMztk_1">
                                                    <div class="messageMztk_1t1">系统通知</div>
                                                    <div class="messageMztk_1t2">
                                                        <span><fmt:formatDate type="both" value="${message.createTime}" /></span>
                                                    </div>
                                                </div>
                                                <div class="messageMztk_2">
                                                    <div class="messageMztk_2t">${message.content}</div>
                                                </div>
                                            </div>
                                            <div class="messageMzbk">
                                            <c:if test="${0 ne message.status }">
                                                <div class="messageMzb messageMzdel" onclick="deleteMessageById(${message.id})" >删除</div>
                                            </c:if>
                                            <c:choose>
                                            <c:when test="${message.type eq 0 }">
                                                <c:choose>
                                                <c:when test="${0 eq message.status}">
                                                    <div class="messageMzb messageMzagree" onclick="dealAddNewOrgRequest(${message.id},1)">通过</div>
                                                    <div class="messageMzb messageMzrefuse" onclick="dealAddNewOrgRequest(${message.id},0)">拒绝</div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="messageMzbt">${message.result }</div>
                                                </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            </c:choose>
                                            </div>
                                        </div>
                                    </c:if>
                                    </c:forEach>
                                    </div>
                                
                                    <div class="messageMzK">
                                    <c:forEach items="${list }" var="message">
                                    <c:if test="${1 eq message.type }">
                                        <div name="${message.id }" class="messageMz">
                                            <div class="messageMzik">
                                                <img src="${message.headimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="messageMzi" />
                                            </div>
                                            <div class="messageMztk">
                                                <div class="messageMztk_1">
                                                    <div class="messageMztk_1t1">好友申请</div>
                                                    <div class="messageMztk_1t2">
                                                        <span><fmt:formatDate type="both" value="${message.createTime}" /></span>
                                                    </div>
                                                </div>
                                                <div class="messageMztk_2">
                                                    <div class="messageMztk_2t">${message.content}</div>
                                                </div>
                                            </div>
                                            <div class="messageMzbk">
                                            <c:choose>
                                            <c:when test="${0 eq message.status}">
                                                <div class="messageMzb messageMzagree" onclick="dealFriendRequest(${message.id},1)">通过</div>
                                                <div class="messageMzb messageMzrefuse" onclick="dealFriendRequest(${message.id},0)" >拒绝</div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="messageMzb messageMzdel" onclick="deleteMessageById(${message.id})" >删除</div>
                                                <div class="messageMzbt">${message.result }</div>
                                            </c:otherwise>
                                            </c:choose>
                                            </div>
                                        </div>
                                    </c:if>
                                    </c:forEach>
                                    </div>

                                    <div class="messageMzK">
                                    <c:forEach items="${list }" var="message">
                                    <c:if test="${2 eq message.type }">
                                        <div name="${message.id }" class="messageMz">
                                            <div class="messageMzik">
                                                <img src="${message.headimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="messageMzi" />
                                            </div>
                                            <div class="messageMztk">
                                                <div class="messageMztk_1">
                                                    <div class="messageMztk_1t1">话题回复</div>
                                                    <div class="messageMztk_1t2">
                                                        <span><fmt:formatDate type="both" value="${message.createTime}" /></span>
                                                    </div>
                                                </div>
                                                <div class="messageMztk_2">
                                                    <div class="messageMztk_2t">${message.content}</div>
                                                </div>
                                            </div>
                                            <div class="messageMzbk">
                                                <div class="messageMzb messageMzdel" onclick="deleteMessageById(${message.id})" >删除</div>
                                                <div class="messageMzb messageMzsee">查看</div>
                                            </div>
                                        </div>
                                    </c:if>
                                    </c:forEach>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
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

<script type="text/javascript">
function deleteMessageById(id) {
	var load = layer.load();
    $.post("/wankangyuan/message/deleteMessage",{id},function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg(result.message, {
                anim: 0,
                end: function (index) {
                	$("div[name="+id+"]").remove();
                }
            });
        }
    },"json");
}

function dealFriendRequest(id,cmd) {
	var load = layer.load();
    $.post("/wankangyuan/message/dealFriendRequest",{id:id,cmd:cmd},function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg(result.message, {
                anim: 0,
                end: function (index) {
                    $("div[name="+id+"]").remove();
                }
            });
        }
    },"json");
}

//处理添加新组织请求
function dealAddNewOrgRequest(id,cmd) {
	var load = layer.load();
    $.post("/wankangyuan/message/dealAddNewOrgRequest",{id:id,cmd:cmd},function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg(result.message, {
                anim: 0,
                end: function (index) {
                    $("div[name="+id+"]").remove();
                }
            });
        }
    },"json");
}

function clearAllMessage(type) {
	var type;
	if($('#allMessage').is('.active')) {
		type = -1;
	}else if($('#systemMessage').is('.active')) {
		type = 0;
	}else if($('#friendRequest').is('.active')) {
		type = 1;
	}else if($('#topicReply').is('.active')) {
		type = 2;
	}
	
	layer.confirm('清空后不能撤销，请确认是否清空?',{
        btn: ['确认','取消'], //按钮
        icon: 2
      }, function(){
    	  var load = layer.load();
    	    $.post("/wankangyuan/message/clearAllMessage",{type},function(result){
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