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
    <title>首页</title>
</head>
<link rel="stylesheet" type="text/css" href="/wankangyuan/static/css/index.css" />
<script type="text/javascript" src="/wankangyuan/static/js/project1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>

<script type="text/javascript">
    window.onload=function(){
        index();
        
        
        var osearchI2=document.querySelectorAll('.searchI2')[0];//外置搜索按钮
        var osearchK=document.querySelectorAll('.searchK')[0];//搜索框
        var osearchI=osearchK.querySelectorAll('.searchI')[0];//搜索按钮
        var osearchX=osearchK.querySelectorAll('.searchX')[0];//搜索关闭
        var osearchP=osearchK.querySelectorAll('.searchP')[0];//搜索输入栏

    	//搜索框
        osearchI2.onclick=function(){
            osearchK.style.display="block";
            osearchI2.style.display="none";
            //osearchP.value="";
        }
        osearchX.onclick=function(){
            osearchK.style.display="none";
            osearchI2.style.display="block";
        }
        
      	//banner
        var oxiangmuRMzKC=document.querySelectorAll('.xiangmuRMzKC')[0];//长图
        var axiangmuRMi=oxiangmuRMzKC.querySelectorAll('.xiangmuRMi');//图
        if(axiangmuRMi.length > 0){
        	var DWlength=oxiangmuRMzKC.querySelectorAll('.xiangmuRMz')[0].offsetWidth;//取单位图宽度

            var oxiangmuRMB=document.querySelectorAll('.xiangmuRMB')[0];//按钮框
            var axiangmuRMb=oxiangmuRMB.querySelectorAll('.xiangmuRMb');//按钮

            var oxiangmuRMt=document.querySelectorAll('.xiangmuRMt')[0];//按钮框上面的文字
            var xiangmuRMtCC=axiangmuRMi[0].name;//按钮框文字存储

            oxiangmuRMt.innerHTML=axiangmuRMi[0].name;

            var yiruPD=0;//移入判断

            for(var i=0;i<axiangmuRMb.length;i++){
                (function(index){
                    axiangmuRMb[index].onmouseenter=function(){
                        xiangmuRMtCC=oxiangmuRMt.innerHTML;
                        oxiangmuRMt.innerHTML=axiangmuRMi[index].name;
                        yiruPD=1;
                        
                        axiangmuRMb[index].onclick=function(){
                            for(var j=0;j<axiangmuRMb.length;j++){
                                axiangmuRMb[j].className="xiangmuRMb";
                            }
                            axiangmuRMb[index].className="xiangmuRMb active";
                            oxiangmuRMt.innerHTML=axiangmuRMi[index].name;

                            oxiangmuRMzKC.style.left=-DWlength*index+"px";

                            xiangmuRMtCC=axiangmuRMi[index].name;
                        }

                        axiangmuRMb[index].onmouseout=function(){
                            if(yiruPD==1){
                                oxiangmuRMt.innerHTML=xiangmuRMtCC;
                                yiruPD=0;
                            }
                        }
                    }
                })(i)
            }
        }

    }
</script>
<body>
	
	<c:if test="${showProjects==null}">
		<script type="text/javascript">
			window.location.href="/wankangyuan/homepage/selectShowProject";
		</script>
	</c:if>

    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="/wankangyuan/static/img/newlogo2.png" class="logo" height="70" width="218" alt="" /></h1>
                <a href="/wankangyuan/admin/login" target="_blank"><div class="link">链接</div></a>
                <shiro:guest>
                <a href="/wankangyuan/register"><div class="register">注册</div></a>
                <a href="/wankangyuan/login"><div class="login">登录</div></a>
                </shiro:guest>
                <shiro:authenticated>
                <div class="touxiangK">
                    <a href=" ">
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
                <a href="/wankangyuan/project/selectMyProject">
                <div class="nicheng"><shiro:principal/></div>
                </a>
                </shiro:authenticated>
                
                <img src="/wankangyuan/static/img/search.png" alt="" class="searchI2" />
                <div class="searchK">
                    <img src="/wankangyuan/static/img/search.png" alt="" class="searchI" />
                    <img src="/wankangyuan/static/img/close2.png" alt="" class="searchX" />
                    <input type="text" class="searchP" placeholder="搜索平台公开项目" value="${homepageSearchWord }" />
                </div>
            </div>
            
            <div class="xiangmu">
                <div class="xiangmuL">
                    <div class="xiangmuLT">
                        <div class="xiangmuLC">平台项目</div>
                    </div>
                    <div class="xiangmuLM">
                    	<c:forEach items="${showProjects }" var="showProjectTemp" varStatus="status">
                    		<div class="xiangmuLMz <c:if test="${status.index == 0}">active</c:if>" >
	                            <div class="xiangmuLMzT" id="${showProjectTemp.id}">${showProjectTemp.p_name}</div>
	                            <div class="xiangmuLMzM">${showProjectTemp.introduction}</div>
	                        </div>
                    	</c:forEach>
                    </div>
	                <div class="xiangmuLB">
	                    <div class="pageK" id="box" ></div>
	                </div>
                </div>
                <div class="xiangmuR">
                    <div class="xiangmuRT" id="show_p_name">${showProjects[0].p_name}</div>
                    <div class="xiangmuRM">
                        <div class="xiangmuRMzK">
                            <div class="xiangmuRMzKC" id="xiangmuRMzKC">
                            	<c:forEach items="${showProjects[0].projectAppTasks}" var="pAppTask" varStatus="status">
		                			<div class="xiangmuRMz">
				                        <iframe src="${pAppTask.result_address }" name="${pAppTask.taskName }" class="xiangmuRMi" ></iframe>
				                    </div>
			                	</c:forEach>
                            </div>
                        </div>
                        
                        <c:if test="${showProjects[0] != null && showProjects[0].projectAppTasks[0] != null}">
                        	<div class="xiangmuRMt" id="xiangmuRMt">${showProjects[0].projectAppTasks[0].taskName}</div>
                        </c:if>
                        <c:if test="${showProjects[0] == null|| showProjects[0].projectAppTasks[0] == null}">
                        	<div class="xiangmuRMt" id="xiangmuRMt"></div>
                        </c:if>
                        
                        <div class="xiangmuRMB" id="xiangmuRMB">
                        	<c:forEach items="${showProjects[0].projectAppTasks}" var="pAppTask" varStatus="status">
		                		<c:if test="${status.index == 0}">
		                			<div class="xiangmuRMb active"></div>
		                		</c:if>
		                		<c:if test="${status.index != 0}">
		                			<div class="xiangmuRMb"></div>
		                		</c:if>
		                	</c:forEach>
                            
                        </div>
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
                    <img src="/wankangyuan/static/img/weixin.png" alt="" class="bottomR" />
                </a>
                <a href="javascript:;">
                    <img src="/wankangyuan/static/img/weibo.png" alt="" class="bottomR" />
                </a>
            </div>
            <div class="bottom2">
                <div class="bottom2z">京ICP备09083200号 合字B2-20160007 人才服务许可证:120116174002号 京公网安备 11010502035189号</div>
                <div class="bottom2z">Copyright © 2006-2018 xxxxxx.com All Rights Reserved</div>
            </div>
        </div>
    </div>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/indexPaging.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>
<script type="text/javascript" src="/wankangyuan/static/js/project1.js"></script>

<script type="text/javascript">

	$('#box').paging({
	    initPageNo: ${page}, // 初始页码
	    totalPages: Math.ceil(${total}/${rows}), //总页数
	    totalCount: '合计&nbsp;' + ${total} + '&nbsp;条数据', // 条目总数
	    slideSpeed: 600, // 缓动速度。单位毫秒
	    jump: true, //是否支持跳转
	    callback: function(page) { // 回调函数
	        console.log(page);
	        if(page!=${page}){
	        	var searchWord = $(".searchP").val();
	            window.location.href="/wankangyuan/homepage/selectShowProject?page="+page+"&searchWord="+searchWord;
	        }
	    }
	});
	
	//单击左侧的项目
	$(".xiangmuLMzT").click(function (){
		var p_name = this.innerHTML;
		$.ajax({
			url:"/wankangyuan/homepage/selectOpenAppTasksByProjectId",
			type:"post",
			data:{
				p_id:this.id
			},
			dataType:"json",
			success : function(data){
				var show_p_name = document.getElementById("show_p_name");
				show_p_name.innerHTML=p_name;
				
				//更新应用运行结果名称
				document.getElementById("xiangmuRMt").innerHTML = "";
				
				//应用结果链接显示div
				var xiangmuRMzKC = document.getElementById("xiangmuRMzKC");
				var xiangmuRMzKC_html = "";
				
			   	//应用链接切换小logo         
			    var xiangmuRMB = document.getElementById("xiangmuRMB");
               	var xiangmuRMB_html = "";
               	
               	
				
				if(data.result == true){

					//切换右侧的展示窗口
					for(var index in data.message){
						if(index == 0){
							
							document.getElementById("xiangmuRMt").innerHTML = data.message[index].taskName;
							
							xiangmuRMzKC_html += '<div class="xiangmuRMz">';
							xiangmuRMzKC_html += '<iframe src="'+data.message[index].result_address
													+'" name="'+data.message[index].taskName+'" class="xiangmuRMi" ></iframe>';
							xiangmuRMzKC_html += '</div>';
							
							xiangmuRMB_html += '<div class="xiangmuRMb active"></div>';
						}else{
							xiangmuRMzKC_html += '<div class="xiangmuRMz">';
							xiangmuRMzKC_html += '<iframe src="'+data.message[index].result_address
													+'" name="'+data.message[index].taskName+'" class="xiangmuRMi" ></iframe>';
							xiangmuRMzKC_html += '</div>';
							
							xiangmuRMB_html += '<div class="xiangmuRMb"></div>';
						}
					}
					
					//显示
					xiangmuRMzKC.innerHTML=xiangmuRMzKC_html;
					xiangmuRMB.innerHTML=xiangmuRMB_html;
					
					//banner
				    var oxiangmuRMzKC=document.querySelectorAll('.xiangmuRMzKC')[0];//长图
				    var axiangmuRMi=oxiangmuRMzKC.querySelectorAll('.xiangmuRMi');//图
				    var DWlength=oxiangmuRMzKC.querySelectorAll('.xiangmuRMz')[0].offsetWidth;//取单位图宽度

				    var oxiangmuRMB=document.querySelectorAll('.xiangmuRMB')[0];//按钮框
				    var axiangmuRMb=oxiangmuRMB.querySelectorAll('.xiangmuRMb');//按钮

				    var oxiangmuRMt=document.querySelectorAll('.xiangmuRMt')[0];//按钮框上面的文字
				    var xiangmuRMtCC=axiangmuRMi[0].name;//按钮框文字存储

				    oxiangmuRMt.innerHTML=axiangmuRMi[0].name;

				    var yiruPD=0;//移入判断

				    for(var i=0;i<axiangmuRMb.length;i++){
				        (function(index){
				            axiangmuRMb[index].onmouseenter=function(){
				                xiangmuRMtCC=oxiangmuRMt.innerHTML;
				                oxiangmuRMt.innerHTML=axiangmuRMi[index].name;
				                yiruPD=1;
				                
				                axiangmuRMb[index].onclick=function(){
				                    for(var j=0;j<axiangmuRMb.length;j++){
				                        axiangmuRMb[j].className="xiangmuRMb";
				                    }
				                    axiangmuRMb[index].className="xiangmuRMb active";
				                    oxiangmuRMt.innerHTML=axiangmuRMi[index].name;

				                    oxiangmuRMzKC.style.left=-DWlength*index+"px";

				                    xiangmuRMtCC=axiangmuRMi[index].name;
				                }

				                axiangmuRMb[index].onmouseout=function(){
				                    if(yiruPD==1){
				                        oxiangmuRMt.innerHTML=xiangmuRMtCC;
				                        yiruPD=0;
				                    }
				                }
				            }
				        })(i)
				    }

				}else{
					layer.msg(data.message);
					//显示
					xiangmuRMzKC.innerHTML=xiangmuRMzKC_html;
					xiangmuRMB.innerHTML=xiangmuRMB_html;
				}
			},
			error : function(){
				layer.msg("联网失败");
			}
		});
	});
	
	$(".searchP").bind("keypress" , function(event){
		if(event.keyCode == 13){
			window.location.href="/wankangyuan/homepage/selectShowProject?searchWord="+this.value;	
		}
	});
	
	
</script>

</body>
</html>