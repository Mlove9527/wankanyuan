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
<script type="text/javascript" src="/wankangyuan/jsp/project/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        project0();
        project2();
        pro_mine();
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
                    <div class="top2Ctl active">${project.p_name }</div>
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
                    	<a href="/wankangyuan/projectApp/selectProjectApp?p_id=${project.id}"><div class="top2Ctr active">应用</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['31'] == true }">
                    	<a href="/wankangyuan/sourceData/getSourceDatas?type=4&p_id=${project.id}"><div class="top2Ctr">格式数据</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['27'] == true }">
                    	<a href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId"><div class="top2Ctr">文件</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['11'] == true }">
                    	<a href="/wankangyuan/project/getProjectDetail"><div class="top2Ctr">基本信息</div></a>
                    </c:if>
                    
                    
                </div>
            </div>
            <div class="shaixuan">
                <div class="shaixuanC">
                    <div class="listZT">
                        <a href="/wankangyuan/jsp/project/project_app.jsp">
                            <div class="listZTli listZT2 active">
                                <div class="listZT2d"></div>
                                <div class="listZT2d"></div>
                                <div class="listZT2d"></div>
                            </div>
                        </a>
                    </div>
                    
                    <div class="jiangeline"></div>
                    <div class="allK">
                        <div class="quanxuanK">
                            <input type="checkbox" class="input_check" id="check0">
                            <label for="check0"></label>
                        </div>
                        <div class="allT">全选</div>
                    </div>
                    <c:if test="${authoritys['41'] == true }">
                    	<div class="pro_menu pro_rem">移除</div>
                    </c:if>
                    <c:if test="${authoritys['41'] != true }">
                    	<div class="pro_menu pro_rem" style="display:none;">移除</div>
                    </c:if>
                    <c:if test="${authoritys['40'] == true }">
                    	<div class="pro_menu pro_run">运行</div>
                    </c:if>
                    <c:if test="${authoritys['40'] != true }">
                    	<div class="pro_menu pro_run" style="display:none;">运行</div>
                    </c:if>
                    <div class="search2">
                        <div class="search2C">
                            <img src="/wankangyuan/static/img/search.png" alt="" class="search2Ci" />
                            <input type="text" class="searchCt"  placeholder="搜索应用" value="${projectSearchWord }"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="PJK2">
            
            	<c:forEach items="${projectApplications}" var="app" varStatus="appList">
	             	<div class="PJK2li">
	                    <div class="PJK2litop">
	                        <div class="PJK2litopT2">${app.appName }</div>
	                        <div class="fuxuanK2">
                                <input name="ids" type="checkbox" class="input_check" id="check${appList.count }" value="${app.id }">
                                <label for="check${appList.count }"></label>
                            </div>
	                    </div>
	                    <div class="PJK2licre">
	                        <div class="PJK2licreT1">创建人：</div>
	                        <div class="PJK2licreT2">${app.creator }</div>
	                    </div>
	                    <div class="PJK2litime">
	                    	<div class="PJK2litimeT1"><fmt:formatDate type="date" value="${app.createTime }" /></div>
	                    </div>
	                    <div class="PJK2lidetail">${app.appIntro }</div>
	                    <a href='/wankangyuan/application/explain?id=${app.id }'>
	                    <div class="PJK2liex">应用说明</div>
	                    </a>
	                </div>
                </c:forEach>
            </div>

            <div class="pageK" id="box"></div>

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
    <script type="text/javascript" src="/wankangyuan/static/js/paging.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>
    <script type="text/javascript">
    
	    $('#box').paging({
	        initPageNo: ${page}, // 初始页码
	        totalPages: Math.ceil(${total}/${rows}), //总页数
	        totalCount: '合计&nbsp;' + ${total} + '&nbsp;条数据', // 条目总数
	        slideSpeed: 600, // 缓动速度。单位毫秒
	        jump: true, //是否支持跳转
	        callback: function(page) { // 回调函数
	            console.log(page);
	        	var user_id=${user.id};
	            if(page!=${page}){
	            	var searchWord = $(".searchCt").val();
	            	var p_id = ${project.id};
	                window.location.href="/wankangyuan/projectApp/selectProjectApp?type=2&p_id="+p_id+"&page="+page+"&strip=${rows}&searchWord="+searchWord;
	            }
	        }
	    });
		
		$(".searchCt").bind("keypress" , function(event){
			if(event.keyCode == 13){
				var p_id = ${project.id};
				window.location.href="/wankangyuan/projectApp/selectProjectApp?type=2&p_id="
						+p_id+"&searchWord="+this.value;
				
			}
		});
		
		//移除项目绑定关系
		$(".pro_rem").click(function (){
			var p_id = ${project.id};
			var afuxuanK=document.querySelectorAll('.fuxuanK2');
            var afuxuan=[];
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
            }
            var ids = [];
            for(var i=0;i<afuxuanK.length;i++){
            	if(afuxuan[i].checked){
            		ids.push(afuxuan[i].value);
            	}
            }
            if(ids == ""){
            	layer.msg("请勾选应用");
            	return;
            }
            
            layer.confirm('请确认移除应用？',{
            	btn:['确认','取消'],
            	icon:2
            },function(){
            	$.ajax({
                	url:"/wankangyuan/projectApp/deleteProjectAppRelation",
                	type:"post",
                	data:{
                		p_id:p_id,
                		ids:ids.join(",")
                	},
                	dataType:"json",
                	success : function(data){
                		layer.msg(data.message);
        				window.location.href="/wankangyuan/projectApp/selectProjectApp?type=2&p_id="+p_id;
                	},
                	error : function(){
                		layer.msg('联网失败');
                	}
                });
            },function(){
            	return;
            });
            
		});
		
		//点击项目运行
		$(".pro_run").click(function(){
			var afuxuanK=document.querySelectorAll('.fuxuanK2');
            var afuxuan=[];
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
            }
            var ids = [];
            for(var i=0;i<afuxuanK.length;i++){
            	if(afuxuan[i].checked){
            		ids.push(afuxuan[i].value);
            	}
            }
            if(ids == ""){
            	layer.msg("请勾选应用");
            	return;
            }
            if(ids.length >1){
            	layer.msg("一次最多运行一个应用");
            	return;
            }
            var app_id = ids.join(",");
            $.ajax({
            	url:"/wankangyuan/projectApp/projectAppRun",
            	type:"post",
            	data:{
            		app_id:app_id
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			window.open(data.message
                        		,'_blank'
                        		,'width=1200,height=600,menubar=no,toolbar=no,status=no,scrollbars=yes')
            		}else{
            			layer.msg("应用运行地址解失败");
            		}
            	},
            	error : function(){
            		layer.msg('联网失败');
            	}
            });    
		});
		
    </script>
</body>
</html>