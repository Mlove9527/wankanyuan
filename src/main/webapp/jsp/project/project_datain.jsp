<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="/wankangyuan/static/css/project1.css" />
<script type="text/javascript" src="/wankangyuan/static/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        project0();
        // project1();
        // pro_mine();
        // pro_dataLB();
        pro_data();
    }
</script>
<body>
    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="/wankangyuan/static/img/newlogo2.png" height="70" width="218" alt="" class="logo" /></h1>
                <a href="/wankangyuan/project/selectMyProject?user_id=1">
                	<div class="topT active">项目</div>
                </a>
                <a href="/wankangyuan/sourceData/firstIn?type=1">
                    <div class="topT">格式数据</div>
                </a>
                <a href="/wankangyuan/application/viewMine">
                    <div class="topT ">应用</div>
                </a>
                <div class="touxiangK">
                   <a href="/wankangyuan/userInfo">
                        <img src="${user.headimg }" onerror='this.src="/wankangyuan/static/img/head.jpg"' class="touxiang" />
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
                <div class="nicheng"><shiro:principal/></div>
                <a href="/wankangyuan/friends/viewFriendsManage">
                <div class="yanjiuquan">
                    <div class="yanjiuquanT">研究圈</div>
                    <c:if test="${friendMSG}">
                        <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint" />
                    </c:if>
                </div>
                </a>
            </div>
            <div class="top2">
                <div class="top2C">
                    <div class="top2Ctl active">
                        <a href="/wankangyuan/sourceData/getSourceDatas?type=4&p_id=${project.id}">
                            <img src="/wankangyuan/static/img/back.png" height="20" width="20" alt="" class="backI" />
                        </a>${sourceData[1]}
                        <input id="cs_id" value="${source.cs_id }" style="display:none;"/>
                        <input id="sourceDataId" value="${sourceData[0]}" style="display:none;"/>
                    </div>
                </div>
            </div>
            <div class="prodainm">
                <div class="prodainmL">
                    <div class="PJliBK">
						<c:forEach items="${formatTypeFolders}" var="formatTypeTemp" varStatus="status">
							<div class="PJliB1">
	                            <div class="PJliB1L">
	                                <div class="fuxuanK4 fuxuanK41">
	                                    <input type="checkbox" class="input_check" name="${formatTypeTemp.ft_id }" id="check1_${formatTypeTemp.ft_id }">
	                                    <label for="check1_${formatTypeTemp.ft_id }"></label>
	                                </div>
	                                <div class="PJliB1Lt">${formatTypeTemp.ft_name }</div>
	                                <div class="PJliBLi PJliBLi2"></div>
	                            </div>
	                            <div class="PJliBR">
	                            	<c:forEach items="${formatTypeTemp.formatDataNodes}" var="formatDataNodeTemp" varStatus="status">
										<div class="PJliB2">
		                                    <div class="PJliB2L">
		                                        <div class="fuxuanK4 fuxuanK42">
		                                            <input type="checkbox" class="input_check" value="${formatTypeTemp.ft_id }" name="${formatDataNodeTemp.key}" id="check1_${formatDataNodeTemp.key}">
		                                            <label for="check1_${formatDataNodeTemp.key}"></label>
		                                        </div>
		                                        <div class="PJliB2Lt" id="${formatDataNodeTemp.key}"
		                                        	onclick="dataNodeClick('${formatDataNodeTemp.key}','${formatTypeTemp.ft_id }')">${formatDataNodeTemp.value}
		                                        </div>
		                                    </div>
		                                </div>
									</c:forEach>
	                            </div>
	                        </div>
						</c:forEach>
                    </div>
                </div>
                <div class="prodainmR">
             		<c:forEach items="${source.sourceFields}" var="sourceFieldTemp" varStatus="status">
						<div class="prodainmRz1">
	                        <div class="prodainmRz1L">${sourceFieldTemp.csf_name}</div>
	                        <div class="prodainmRz1R">${sourceData[status.index+1] }</div>
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
    
    <script type="text/javascript" src="/wankangyuan/static/js/jquery.min.js"></script>
	<script type="text/javascript">
	
		function dataNodeClick(formatNodeId , ft_id){
			var cs_id = $('#cs_id').val();
			var sourceDataId = $("#sourceDataId").val();
			window.location.href="/wankangyuan/formatNode/getFormatNodeById?type=4&cs_id="
					+cs_id+"&sourceDataId="+sourceDataId+"&ft_id="+ft_id+"&formatNodeId="+formatNodeId;
		}
		
	</script>
</body>
</html>