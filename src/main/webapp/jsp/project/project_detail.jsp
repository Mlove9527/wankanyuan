<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
        pro_detail();
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
               
                    <div class="top2Ctl active"> <c:if test="${url==1 }"><a href="/wankangyuan/project/selectMyProject?user_id=1"></c:if>
                    <c:if test="${url==2 }"><a href="/wankangyuan/project/selectCreatedProject?creator=1"></c:if>
                    <c:if test="${url==3 }"><a href="/wankangyuan/project/selectPublicProject"></c:if>
                     <img src="/wankangyuan/static/img/back.png" height="20" width="20" alt="" class="backI">
						</a>${project.p_name }</div>
                    <c:if test="${authoritys['74'] == true }">
                    	  <a href="/wankangyuan/projectTopic/selectProjectTopic"><div class="top2Ctr">讨论版</div></a>
                    </c:if>
                   
                    <c:if test="${authoritys['63'] == true }">
                    	 <a href="/wankangyuan/projectMember/selectProjectMember"><div class="top2Ctr">成员</div></a>
                    </c:if>
                    
					<c:if test="${authoritys['57'] == true }">
                    	 <a href="/wankangyuan/projectAppEnd/selectProjectAppEnd"><div class="top2Ctr">应用结果</div></a>
                    </c:if>
                   
                    <c:if test="${authoritys['42'] == true }">
                    	<a href="/wankangyuan/projectApp/selectProjectApp?p_id=${project.id}"><div class="top2Ctr">应用</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['31'] == true }">
                    	<a href="/wankangyuan/sourceData/getSourceDatas?type=4&p_id=${project.id}"><div class="top2Ctr">格式数据</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['27'] == true }">
                    	<a href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId"><div class="top2Ctr">文件</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['11'] == true }">
                    	<a href="/wankangyuan/project/getProjectDetail"><div class="top2Ctr active">基本信息</div></a>
                    </c:if>
                    
                    
                </div>
            </div>
            <div class="prodexq">
                <div class="prodexqL">
                    <div class="prodexqLt">项目说明</div>
                    <div class="prodexqLk">
                        <div class="prodexLbk">
                            <!-- <div class="prodexLb">保存</div> -->
                            <c:if test="${authoritys['10'] == true }">
		                    	 <input type="button" class="prodexLb" value="保存" onclick="saveProjectIntroduction()"/>
		                    </c:if>
		                    <c:if test="${authoritys['10'] != true }">
		                    	 <input type="button" class="prodexLb" value="保存" onclick="saveProjectIntroduction()" style="display:none;"/>
		                    </c:if>
                            
                        </div>
                        <textarea name="" id="" class="prodexLt" >${project.introduction}</textarea>

                        <div class="prodexLtj">
                            <div class="prodexLtjt">
                                <div class="prodexLtjtL">项目统计信息</div>
                            </div>
                            <div class="prodexLtjt">
                                <div class="prodexLtjtL">文件：</div>
                                <div class="prodexLtjtR">${project.fileNum }</div>
                            </div>
                            <div class="prodexLtjt">
                                <div class="prodexLtjtL">应用：</div>
                                <div class="prodexLtjtR">${project.appNum }</div>
                            </div>
                            <div class="prodexLtjt">
                                <div class="prodexLtjtL">应用结果：</div>
                                <div class="prodexLtjtR">${project.appResultNum }</div>
                            </div>
                            <div class="prodexLtjt">
                                <div class="prodexLtjtL">成员：</div>
                                <div class="prodexLtjtR">${project.memberNum }</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="prodexqR">
				    <div class="prodexqRt">${project.p_name }</div>
				    <div class="xiangmuR">
				        <div class="xiangmuRM">
				            <div class="xiangmuRMzK">
				                <div class="xiangmuRMzKC">
				                	<c:forEach items="${pAppTasks}" var="pAppTask" varStatus="status">
				                		<c:if test="${status.index<=2}">
				                			<div class="xiangmuRMz">
						                        <iframe src="${pAppTask.result_address }" name="${pAppTask.taskName }" class="xiangmuRMi" ></iframe>
						                    </div>
				                		</c:if>
				                	</c:forEach>
				                </div>
				            </div>
				        </div>
				        <div class="xiangmuRMt"></div>
				        <div class="xiangmuRMB">
				        	<c:forEach items="${pAppTasks}" var="pAppTask" varStatus="status">
		                		<c:if test="${status.index==0}">
		                			<div class="xiangmuRMb active"></div>
		                		</c:if>
		                		<c:if test="${status.index==1}">
		                			<div class="xiangmuRMb"></div>
		                		</c:if>
		                		<c:if test="${status.index==2}">
		                			<div class="xiangmuRMb"></div>
		                		</c:if>
		                	</c:forEach>
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
    
    <script type="text/javascript" src="/wankangyuan/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>
    <script type="text/javascript">
    	
    	//提交编辑后的结果
    	function saveProjectIntroduction(){
    		var introduction = $(".prodexLt").val();
    		$.ajax({
    			url:"/wankangyuan/project/updatePorjectIntroduction",
    			type:"post",
    			dataType:"json",
    			data:{
    				introduction:introduction
    			},
    			success : function(data){
    				if(data.result == true){
    					layer.msg("保存成功");
    					//不需要刷新页面
    					//window.location.href="/wankangyuan/project/getProjectDetail";
    				}else{
    					layer.msg(data.message);
    				}
    			},
    			error : function(){
    				layer.msg("联网失败");
    			}
    		});
    	}

    </script>
</body>
</html>