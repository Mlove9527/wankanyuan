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
        project1();
        pro_mine();
        
        var columnName = document.querySelectorAll(".BTSX")[0].id;
        if(columnName!=null && columnName!=""){
        	//需要点击对应的头部
        	document.getElementById(columnName).click();
        }
    }
</script>
<body>
    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="/wankangyuan/static/img/newlogo2.png" height="70" width="218" alt="" class="logo" /></h1>
                <a href="/wankangyuan/project/selectMyProject">
                    <div class="topT active">项目</div>
                </a>
                <a href="/wankangyuan/sourceData/firstIn?type=1">
                    <div class="topT">格式数据</div>
                </a>
                <a href="/wankangyuan/application/viewMine">
                    <div class="topT">应用</div>
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
                    	  <a href="/wankangyuan/projectTopic/selectProjectTopic" onclick="remove()"><div class="top2Ctr">讨论版</div></a>
                    </c:if>
                   
                    <c:if test="${authoritys['63'] == true }">
                    	 <a href="/wankangyuan/projectMember/selectProjectMember" onclick="remove()"><div class="top2Ctr">成员</div></a>
                    </c:if>
                    
					<c:if test="${authoritys['57'] == true }">
                    	 <a href="/wankangyuan/projectAppEnd/selectProjectAppEnd" onclick="remove()"><div class="top2Ctr">应用结果</div></a>
                    </c:if>
                   
                    <c:if test="${authoritys['42'] == true }">
                    	<a href="/wankangyuan/projectApp/selectProjectApp?p_id=${project.id}" onclick="remove()"><div class="top2Ctr active">应用</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['31'] == true }">
                    	<a href="/wankangyuan/sourceData/getSourceDatas?type=4&p_id=${project.id}" onclick="remove()"><div class="top2Ctr">格式数据</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['27'] == true }">
                    	<a href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId" onclick="remove()"><div class="top2Ctr">文件</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['11'] == true }">
                    	<a href="/wankangyuan/project/getProjectDetail"><div class="top2Ctr" onclick="remove()">基本信息</div></a>
                    </c:if>
                    
                    
                </div>
            </div>
            <div class="shaixuan">
                <div class="shaixuanC">
                    <div class="listZT">
                        <a href="/wankangyuan/jsp/project/project_app2.jsp">
                            <div class="listZTli listZT1 active">
                                <img src="/wankangyuan/static/img/listZT1.png"alt="" class="listZT1i" />
                                <img src="/wankangyuan/static/img/listZT1.png" alt="" class="listZT1i" />
                            </div>
                        </a>
                    </div>
                    <div class="jiangeline"></div>
                    <div class="shaixuanBT">
                        <div class="shaixuanBTt">筛选</div>
                        <div class="shaixuanBTiK">
                            <img src="/wankangyuan/static/img/sanjiao_blue.png" alt="" class="shaixuanBTi" />
                        </div>
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
                <div class="shaixuanZK">
                <div class="shaixuanZKC">
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">应用名称</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">创建人</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">创建时间</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">是否公开</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">异步/同步</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">关键字</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">应用描述</div>
                    </div>
                    </div>
                </div>
            </div>
            <div class="PJK">
                <div class="PJList">
                    <div class="allK">
                        <div class="quanxuanK">
                            <input type="checkbox" class="input_check" id="check0">
                            <label for="check0"></label>
                        </div>
                        <div class="allT">全选</div>
                    </div>
                    <div class="PJListli appname" id=appName>应用名称</div>
                    <div class="PJListli appcreater" id="creator">创建人</div>
                    <div class="PJListli apptime" id="createTime">创建时间</div>
                    <div class="PJListli appopen" id="isDisplay">是否公开</div>
                    <div class="PJListli PJyibu" id="isAsync">异步/同步</div>
                    <div class="PJListli PJkeyword" id="keywords">关键字</div>
                    <div class="PJListli appexplain" id="appIntro">应用描述</div>
                </div>
                <div class="PJListline"></div>
                
                <div class="PJul">
                <c:forEach items="${projectApplications}" var="app" varStatus="appList">
                <a href='/wankangyuan/application/explain?id=${app.id }'>
                    <div class="PJli">
                        <div class="PJliC">
                            <div class="fuxuanK2">
                                <input name="ids" type="checkbox" class="input_check" id="check${appList.count }" value="${app.id }">
                                <label for="check${appList.count }"></label>
                            </div>
                            
                            <div class="PJliCli appname">${app.appName }</div>
                            <div class="PJliCli appcreater">${app.creator }</div>
                            <div class="PJliCli apptime">
                                <fmt:formatDate type="date" value="${app.createTime }" />
                            </div>
                            <div class="PJliCli appopen">
                            	<c:if test="${app.isDisplay eq 0}">私有</c:if>
	                            <c:if test="${app.isDisplay eq 1}">公开</c:if>
	                        </div>
                            <div class="PJliCli PJyibu">
	                            <c:if test="${app.isAsync eq 0}">同步</c:if>
	                            <c:if test="${app.isAsync eq 1}">异步</c:if>
                            </div>
                            <div class="PJliCli PJkeyword">${app.keywords }</div>
                            <div class="PJliCli appexplain">${app.appIntro }</div>
                            
                        </div>
                        <div class="PJliline"></div>
                    </div>
                </a>
                </c:forEach>
                </div>

                    <!-- 筛选框 -->
                    <input type="hidden" id="h_appName" value="${projectAppFilterRequest.appName}"/>
                	<input type="hidden" id="h_creator" value="${projectAppFilterRequest.creator}"/>
                	<input type="hidden" id="h_createTime" value="${projectAppFilterRequest.createTime}"/>
                	<input type="hidden" id="h_isDisplay" value="${projectAppFilterRequest.isDisplay}"/>
                	<input type="hidden" id="h_isAsync" value="${projectAppFilterRequest.isAsync}"/>
                	<input type="hidden" id="h_keywords" value="${projectAppFilterRequest.keywords}"/>
                	<input type="hidden" id="h_appIntro" value="${projectAppFilterRequest.appIntro}"/>
                	
                	
                	<input type="hidden" id="appNameGl" value=""/>
                	<input type="hidden" id="creatorGl" value=""/>
                	<input type="hidden" id="createTimeGl" value=""/>
                	<input type="hidden" id="isDisplayGl" value=""/>
                	<input type="hidden" id="isAsyncGl" value=""/>
                	<input type="hidden" id="keywordsGl" value=""/>
                	<input type="hidden" id="appIntroGl" value=""/>
                                
                <div class="BTSX" id="${queryCondition.columnName}">
                	<input id="isFilter" value="${queryCondition.isFilter}" style="display:none;"/>
                    <div class="BTSXc">
                        <div class="BTSXcli">
                        	<input type="text" id="sort" style="display:none;" value="${queryCondition.order}"/>
                            <div class="BTSXcliT">排序：</div>
                            <div class="BTSXcliI" id="sort_asc" <c:if test="${queryCondition.order != null && queryCondition.order == 'asc' }">style="color:#5ca0e5;"</c:if>>↑</div>
                            <div class="BTSXcliI" id="sort_desc" <c:if test="${queryCondition.order != null && queryCondition.order == 'desc' }">style="color:#5ca0e5;"</c:if>>↓</div>
                        </div>
                        <div class="BTSXcli">
                            <div class="BTSXcliT">过滤：</div>
                            <input type="text" class="BTSXcliGLK" value="${queryCondition.filter}"/>
                            <button id="guolv">过滤</button>
                        </div>
                        <div class="BTSXcli">
                            <div class="BTSXcliT">值筛选：</div>
                            <input id="values" style="display:none;" value="${queryCondition.values}"/>
                        </div>
                        <div class="BTSXcli2" id="BTSXcli2">
                        	<c:forEach items="${queryCondition.strings}" var="projectQueryConditionTemp">
                        		<div class="BTSXcli2li">
	                                <input type="checkbox" class="BTSXcli2liC" name="${projectQueryConditionTemp}"/>
	                                <div class="BTSXcli2liT">${projectQueryConditionTemp}</div>
	                            </div>
                        	 </c:forEach>
                        </div>
                        <div class="BTSXcli3">
                            <div class="BTSXcli3BT BTSXcli3BTent">筛选</div>
                            <div class="BTSXcli3BT BTSXcli3BTres">重置</div> 
                        </div>
                    </div>
                </div>
                
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
	            	if($("#isFilter").val() == "true"){
                		shaixuan(page);
                	}else{
                		window.location.href="/wankangyuan/projectApp/selectProjectApp?type=1&p_id="+p_id+"&page="+page+"&strip=${rows}&searchWord="+searchWord;
                	}   
	            }
	        }
	    });
		
		$(".searchCt").bind("keypress" , function(event){
			if(event.keyCode == 13){
				var p_id = ${project.id};
				window.location.href="/wankangyuan/projectApp/selectProjectApp?type=1&p_id="
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
        				window.location.href="/wankangyuan/projectApp/selectProjectApp?type=1&p_id="+p_id;
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
            //打开一个不被拦截的新窗口
            var newWindow = window.open(null,'_blank','width=1200,height=600,menubar=no,toolbar=no,status=no,scrollbars=yes');
            $.ajax({
            	url:"/wankangyuan/projectApp/projectAppRun",
            	type:"post",
            	data:{
            		app_id:app_id
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
/*             			window.open(data.message
                        		,'_blank'
                        		,'width=1200,height=600,menubar=no,toolbar=no,status=no,scrollbars=yes') */
            			//修改新窗口的url
            			var newUrl = data.message;
                        newWindow.location.href = newUrl;
            		}else{
            			layer.msg("应用运行地址解失败");
            		}
            	},
            	error : function(){
            		layer.msg('联网失败');
            	}
            });    
		});
		
      	//点击项目标题栏
    	$(".PJListli").click(function (){
    		document.querySelectorAll(".BTSX")[0].id=this.id;//更新筛选框的筛选字段为当前点击的字段名
   			filter();//然后自动执行过滤，筛选出十个数值
    	});

    	//过滤框绑定enter事件
    	$(".BTSXcliGLK").bind("keypress" , function(event){
    		if(event.keyCode == 13){
    			//过滤
    			filter();
    		}
    	});
    	//点击过滤按钮
    	$("#guolv").click(function (){
    		//过滤
    		filter();
    	});
    	//过滤指定的字段，根据过滤条件获取指定字段的前十个值
    	function filter(){
    		var filter = $(".BTSXcliGLK").val();//过滤条件
    		var values = $("#values").val();//上次筛选操作中选中的值
    		var vals = values.split(",");//数组形式
    		var columnName = document.querySelectorAll(".BTSX")[0].id;//筛选字段名
    		console.log("filter:"+filter+",values:"+values+",vals:"+vals+",columnName:"+columnName)
    		
    		
    		if(columnName == "appName"){
    			vals = $("#h_appName").val().split(",");//数组形式
    			$("#appNameGl").val(filter);
            }else if(columnName == "creator"){
            	vals = $("#h_creator").val().split(",");//数组形式
            	$("#creatorGl").val(filter);
            }else if(columnName == "createTime"){
            	vals = $("#h_createTime").val().split(",");//数组形式
            	$("#createTimeGl").val(filter);
            }else if(columnName == "isDisplay"){
            	vals = $("#h_isDisplay").val().split(",");//数组形式
            	$("#isDisplayGl").val(filter);
            }else if(columnName == "isAsync"){
            	vals = $("#h_isAsync").val().split(",");//数组形式
            	$("#isAsyncGl").val(filter);
            }else if(columnName == "keywords"){
            	vals = $("#h_keywords").val().split(",");//数组形式
            	$("#keywordsGl").val(filter);
            }else if(columnName == "appIntro"){
            	vals = $("#h_appIntro").val().split(",");//数组形式
            	$("#appIntroGl").val(filter);
            }
    		
    		var appName =  $("#h_appName").val();
            var creator = $("#h_creator").val();
            var createTime = $("#h_createTime").val();
            var isDisplay = $("#h_isDisplay").val();
            var isAsync = $("#h_isAsync").val();
            var keywords = $("#h_keywords").val();
            var appIntro=$("#h_appIntro").val();
    		
            var appNameGl=$("#appNameGl").val();
            var creatorGl = $("#creatorGl").val() || '${creatorGl}';
            var createTimeGl = $("#createTimeGl").val() || '${createTimeGl}';
            var isDisplayGl = $("#isDisplayGl").val() || '${isDisplayGl}';
            var isAsyncGl = $("#isAsyncGl").val() || '${keyWordsGl}';
            var keywordsGl = $("#keywordsGl").val() || '${isOpenGl}';
            var appIntroGl=$("#appIntroGl").val() || '${isOpenGl}';
            
    		$.ajax({
    			url:"/wankangyuan/projectAppFilter/selectAppDistinctColumnValue",
    			type:"post",
    			data:{
    				columnName:columnName,
    				filter:filter,
    				values: values,
    				appName :  appName,
                	creator :  creator,
               		createTime :  createTime,
                	isDisplay :  isDisplay,
                	isAsync : isAsync,
                	keywords : keywords,
               		appIntro: appIntro,
               		appNameGl: appNameGl,
                   	creatorGl :  creatorGl,
                    createTimeGl : createTimeGl,
                    isDisplayGl : isDisplayGl,
                    isAsyncGl : isAsyncGl,
                    keywordsGl : keywordsGl,
                    appIntroGl :appIntroGl
    			},
    			dataType:"json",
    			success : function(data){
    				if(data.result == true){
    					var BTSXcli2 = document.getElementById("BTSXcli2");//填充筛选值
    					var BTSXcli2_html = "";
    					var isnull=false;
    					for(var index in data.message){
    						console.log(data)
    						if(null!=data.message[index]&&data.message[index] != ''){
    						//if(index < 10){
    							BTSXcli2_html+='<div class="BTSXcli2li">';
    							if(vals.indexOf(data.message[index]) != -1){//如果筛选值在之前的筛选中被勾选了，再次选中它
    								//BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" name="'+data.message[index]+'" checked/>';
    								if(data.type==columnName){
    									BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" checked="checked" name="'+data.message[index]+'"/>';
    								}else{
    									BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" checked="checked" name="'+data.message[index]+'"/>';
    								}
    								
    							}else{
    								BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" name="'+data.message[index]+'"/>';
    							}
        						BTSXcli2_html+='<div class="BTSXcli2liT">'+data.message[index]+'</div>';
        						BTSXcli2_html+='</div>';
    						//}
    						}else{
    							isnull=true;
    						}
    					}
    					BTSXcli2.innerHTML=BTSXcli2_html;
    					var filesList = $(".BTSXcli2");
    					if(isnull){
    						filesList.prepend('<div class="BTSXcli2li" id="isnull">'+
    			                       ' <input name="null" type="checkbox" class="BTSXcli2liC" value="null" />'+
    			                            '<div class="BTSXcli2liT">空值</div>'+
    			                       ' </div>');
    					}
    				}else{
    					layer.msg(data.message);
    				}
    			},
    			error : function(){
    				layer.msg("联网失败");
    			}
    		});
    	}
    	
    	//重置按钮
    	$(".BTSXcli3BTres").click(function(){
    		//重置排序栏
    		document.getElementById("sort").value="";
    		document.getElementById("sort_desc").style.color="#666";
    		document.getElementById("sort_asc").style.color="#666"
    		//重新过滤条件
    		$(".BTSXcliGLK").val("");
    		//重置值筛选
    		document.getElementById("BTSXcli2").innerHTML="";
    		//设置重置后退出筛选状态，执行一般的筛选操作
    		$("#isFilter").val("false");
    		window.location.href="/wankangyuan/projectApp/selectProjectApp?p_id=${project.id}";
    	});
    	
    	//点击筛选按钮
    	$(".BTSXcli3BTent").click(function(){
    		shaixuan(1);//1代表第一页
    	});
    	//降序排列
    	$("#sort_desc").click(function(){
    		document.getElementById("sort").value="desc";
    		shaixuan(1);//1代表第一页
    	});
    	//升序排列
    	$("#sort_asc").click(function(){
    		document.getElementById("sort").value="asc";
    		shaixuan(1);//1代表第一页
    	});
    	//点击筛选按钮，请求筛选接口
    	function shaixuan(page){
    		var filter = $(".BTSXcliGLK").val();//过滤条件
    		var searchWord = $(".searchCt").val();//搜索条件
    		var columnName = document.querySelectorAll(".BTSX")[0].id;//字段名
    		var order = document.getElementById("sort").value;//排序方式

    		var BTSXcli2liC=document.querySelectorAll('.BTSXcli2liC');
            var values = [];
            for(var i=0;i<BTSXcli2liC.length;i++){
            	if(BTSXcli2liC[i].checked){
            		values.push(BTSXcli2liC[i].name);
            	}
            }
            values = values.join(",");//勾选的值
            $("#isFilter").val("true");//设置当前为筛选状态
            
            if(columnName == "appName"){
               	$("#h_appName").val(values);
               }else if(columnName == "creator"){
               	$("#h_creator").val(values);
               }else if(columnName == "isDisplay"){
               	$("#h_isDisplay").val(values);
               }else if(columnName == "createTime"){
               	$("#h_createTime").val(values);
               }else if(columnName == "keyWords"){
               	$("#h_keywords").val(values);
               }else if(columnName == "isAsync"){
               	$("#h_isAsync").val(values);
               }else if(columnName == "appIntro"){
               	$("#h_appIntro").val(values);
               }
                
                var appName = $("#h_appName").val();
                var creator = $("#h_creator").val();
                var isDisplay = $("#h_isDisplay").val();
                var createTime = $("#h_createTime").val();
                var keyWords = $("#h_keywords").val();
                var isAsync = $("#h_isAsync").val();
                var appIntro = $("#h_appIntro").val();
              
                var appNameGl=$("#appNameGl").val();
                var creatorGl = $("#creatorGl").val() || '${creatorGl}';
                var createTimeGl = $("#createTimeGl").val() || '${createTimeGl}';
                var isDisplayGl = $("#isDisplayGl").val() || '${isDisplayGl}';
                var isAsyncGl = $("#isAsyncGl").val() || '${keyWordsGl}';
                var keywordsGl = $("#keywordsGl").val() || '${isOpenGl}';
                var appIntroGl=$("#appIntroGl").val() || '${isOpenGl}';
            
            window.location.href="/wankangyuan/projectAppFilter/selectAppByFilterCondition?page="+page+"&searchWord="
			+searchWord+"&columnName="+columnName+"&order="+order+"&filter="+filter+"&values="+values+"&isFilter=true"
			+"&appName="+encodeURI(encodeURI(appName))+
			"&creator="+encodeURI(encodeURI(creator))+
			"&isDisplay="+encodeURI(encodeURI(isDisplay))+
			"&createTime="+createTime+
			"&keyWords="+encodeURI(encodeURI(keyWords))+
			"&isAsync="+encodeURI(encodeURI(isAsync))+
			"&appIntro="+encodeURI(encodeURI(appIntro))
			+"&appNameGl="+encodeURI(encodeURI(appNameGl))+
			"&creatorGl="+encodeURI(encodeURI(creatorGl))+
			"&createTimeGl="+encodeURI(encodeURI(createTimeGl))+
			"&isDisplayGl="+encodeURI(encodeURI(isDisplayGl))+
			"&isAsyncGl="+encodeURI(encodeURI(isAsyncGl))+
			"&keywordsGl="+encodeURI(encodeURI(keywordsGl))
			"&appIntroGl="+encodeURI(encodeURI(appIntroGl));
    		
    	}
		
    	 function remove(){
    		 sessionStorage.setItem("noChangId",'');
    		 sessionStorage.setItem("allChangMine",'false');
    	 }
    	 
    	$(function(){
    		//点击表头改变颜色
   		 $(".PJListli").each(function () {
				$(this).click(function () {
					$(".PJListli").css({
						'color':'#16579b'
					})
					$(this).css({
						'color':'#ff0033'
					})
				})
			})
    	});
    </script>
</body>
</html>