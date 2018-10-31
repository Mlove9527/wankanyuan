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
        
        
     	// 筛选菜单框显示隐藏
        var oshaixuanBT=document.querySelectorAll('.shaixuanBT')[0];//获取筛选下拉按钮
        var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
        var shaixuanPD=0;

        oshaixuanBT.onclick=function(event){
            if(shaixuanPD==0){
                oshaixuanZK.className="shaixuanZK active";
                shaixuanPD=1;
            }else{
                oshaixuanZK.className="shaixuanZK";
                shaixuanPD=0;
            }
            // event.stopPropagation();
            // console.log(1);
        }
        // document.onclick=function(){
        //     oshaixuanZK.className="shaixuanZK";
        // }

    //筛选按钮显示隐藏选项
        var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
        var ashaixuanZKliI=document.querySelectorAll('.shaixuanZKliI');//获取所有筛选按钮
        var oPJList=document.querySelectorAll('.PJList')[0];//项目表头栏
        var aPJListli=oPJList.querySelectorAll('.PJListli1');//项目表头
        var oPJul=document.querySelectorAll('.PJul')[0];//项目栏
        var aPJli=oPJul.querySelectorAll('.PJli');//每一条项目

        //console.log(aPJli);
        

        //初始化筛选按钮判断
        var shaixuanBTPD=new Array();
        for(var i=0;i<ashaixuanZKliI.length;i++){
            shaixuanBTPD[i]=1;
        }



        for(var i=0;i<ashaixuanZKliI.length;i++){
            (function(j){
                ashaixuanZKliI[j].onclick=function(){
                    if(shaixuanBTPD[j]==0){
                        ashaixuanZKliI[j].className="shaixuanZKliI active";
                        aPJListli[j].style.display="block";
                        for(var o=0;o<aPJli.length;o++){
                            if(aPJli[o].querySelectorAll('.PJliCli2')[0]){
                                var aPJliCli=aPJli[o].querySelectorAll('.PJliCli2');//格式数据表项
                                aPJliCli[j].style.display="-webkit-box";
                            }else if(aPJli[o].querySelectorAll('.PJliCli')[0]){
                                var aPJliCli=aPJli[o].querySelectorAll('.PJliCli');//项目表项
                                aPJliCli[j].style.display="-webkit-box";
                            }
                        }
                        shaixuanBTPD[j]=1;
                    }else{
                        ashaixuanZKliI[j].className="shaixuanZKliI";
                        aPJListli[j].style.display="none";
                        for(var o=0;o<aPJli.length;o++){
                            if(aPJli[o].querySelectorAll('.PJliCli2')[0]){
                                var aPJliCli=aPJli[o].querySelectorAll('.PJliCli2');//格式数据表项
                                aPJliCli[j].style.display="none";
                            }else if(aPJli[o].querySelectorAll('.PJliCli')[0]){
                                var aPJliCli=aPJli[o].querySelectorAll('.PJliCli');//项目表项
                                aPJliCli[j].style.display="none";
                            }
                            
                        }
                        // aPJliCli[j].style.display="none";
                        shaixuanBTPD[j]=0;
                    }
                    
                }
            })(i)
        }

    //项目表头和项目分割线宽度
        var oPJList=document.querySelectorAll('.PJList')[0];//项目表头
        var oPJListline=document.querySelectorAll('.PJListline')[0];//分割线

        oPJListline.style.width=oPJList.offsetWidth*0.98+"px";
        // console.log(oPJListline.offsetWidth);

      //点击表头的排序筛选功能
        var oPJK=document.querySelectorAll('.PJK')[0];//项目框
        var oBTSX=document.querySelectorAll('.BTSX')[0];//项目表头筛选框
        // var oBTSXpd=document.querySelectorAll('.BTSXpd')[0];//项目表头筛选框判断

        var oPJList=document.querySelectorAll('.PJList')[0];//项目表头栏
        var aPJListli=oPJList.querySelectorAll('.PJListli1');//项目表头

        // var BTSXpd=-1;//项目表头筛选框判断

        //点击设置排序筛选框
        for(var i=0;i<aPJListli.length;i++){
            (function(j){
                aPJListli[j].onclick=function(){
                    // if(BTSXpd==j){
                    //     oBTSX.style.display="none";
                    //     BTSXpd=-1;
                    // }else{
                        oBTSX.style.display="block";
                        // BTSXpd=j;
                    // }
                    var BTSXleft=aPJListli[j].offsetLeft;
                    // oBTSX.name=aPJListli[j].innerHTML;
                    if(document.querySelectorAll('.BTSXpd')[0]){
                        var oBTSXpd=document.querySelectorAll('.BTSXpd')[0];//项目表头筛选框判断
                        oBTSXpd.value=$(aPJListli[j]).attr('name');
                        console.log($(aPJListli[j]).attr('name'));
                        console.log(oBTSXpd.value);
                    }
                    
                    
                    console.log(BTSXleft);
                    if(BTSXleft>1118){
                        BTSXleft=1118;
                    }
                    oBTSX.style.left=BTSXleft-20+'px'; 
                    event.stopPropagation();
                }
            })(i)
        }
        // $(document).click(function(){
        //     console.log(1);
        // })
        window.onclick=function(){
            //console.log(1);
            oBTSX.style.display="none";
        }
        document.onclick=function(){
            //console.log(1);
            oBTSX.style.display="none";
        }
        oBTSX.onclick=function(){
            event.stopPropagation();
        }
        
        var aBTSXcliI=oBTSX.querySelectorAll('.BTSXcliI');//筛选框排序箭头
        var oBTSXcliIpd=document.querySelectorAll('.BTSXcliIpd')[0];//筛选框选择判断
        for(var i=0;i<aBTSXcliI.length;i++){
            (function(index){
                aBTSXcliI[index].onclick=function(){
                    for(var j=0;j<aBTSXcliI.length;j++){
                        aBTSXcliI[j].style.color="#666";
                    }
                    aBTSXcliI[index].style.color="#5ca0e5";
//                    oBTSXcliIpd.value=index+1;
//                    console.log(oBTSXcliIpd.value);
                }
            })(i)
        }

        var oBTSXcli3BTres=document.querySelectorAll('.BTSXcli3BTres')[0];//重置按钮
        var aBTSXcli2liC=document.querySelectorAll('.BTSXcli2liC');//复选框
        var oBTSXcliGLK=document.querySelectorAll('.BTSXcliGLK')[0];//过滤框

        oBTSXcli3BTres.onclick=function(){
            for(var j=0;j<aBTSXcliI.length;j++){
                aBTSXcliI[j].style.color="#666";
            }
            oBTSXcliGLK.value="";
            for(var i=0;i<aBTSXcli2liC.length;i++){
                aBTSXcli2liC[i].checked=false;
            }
        }

        
        
        
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
                    	 <a href="/wankangyuan/projectAppEnd/selectProjectAppEnd"><div class="top2Ctr active">应用结果</div></a>
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
                    	<a href="/wankangyuan/project/getProjectDetail"><div class="top2Ctr ">基本信息</div></a>
                    </c:if>
                    
                    
                </div>
            </div>
            <div class="shaixuan">
                <div class="shaixuanC">
                    <div class="listZT">
                        <a href="/wankangyuan/jsp/project/project_append2.jsp">
                            <div class="listZTli listZT1 active">
                                <img src="/wankangyuan/static/img/listZT1.png"alt="" class="listZT1i" />
                                <img src="/wankangyuan/static/img/listZT1.png" alt="" class="listZT1i" />
                            </div>
                        </a>
                        <a href="javascript:;">
                            <div class="listZTli listZT2">
                                <div class="listZT2d"></div>
                                <div class="listZT2d"></div>
                                <div class="listZT2d"></div>
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
                    
                    <c:if test="${authoritys['53'] == true }">
                    	<div class="pro_menu pro_rem">移除</div>
                    </c:if>
                    <c:if test="${authoritys['53'] != true }">
                    	<div class="pro_menu pro_rem" style="display:none;">移除</div>
                    </c:if>
                    
                    <c:if test="${authoritys['52'] == true }">
                    	<div class="pro_menu pro_canfabu">取消发布</div>
                    </c:if>
                    <c:if test="${authoritys['52'] != true }">
                    	<div class="pro_menu pro_canfabu" style="display:none;">取消发布</div>
                    </c:if>
                    
                    <c:if test="${authoritys['51'] == true }">
                    	<div class="pro_menu pro_fabu">发布</div>
                    </c:if>
                    <c:if test="${authoritys['51'] != true }">
                    	<div class="pro_menu pro_fabu" style="display:none;">发布</div>
                    </c:if>
                    
                    <c:if test="${authoritys['50'] == true }">
                    	<div class="pro_menu pro_rerun">重新运行</div>
                    </c:if>
                    <c:if test="${authoritys['50'] != true }">
                    	<div class="pro_menu pro_rerun" style="display:none;">重新运行</div>
                    </c:if>
                    
                    <div class="search2">
                        <div class="search2C">
                            <img src="/wankangyuan/static/img/search.png" alt="" class="search2Ci" />
                            <input type="text" class="search2Ct"  placeholder="搜索应用结果" value="${projectAppTaskSearchWord }"/>
                        </div>
                    </div>
                </div>
                <div class="shaixuanZK">
                <div class="shaixuanZKC">
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">结果名称</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">应用名称</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">创建者</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">运行时间</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">运行使用参数</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">运行结果描述</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">进度</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">发布状态</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">异步/即时</div>
                    </div>
                    <div class="shaixuanZKli">
                        <div class="shaixuanZKliI active"></div>
                        <div class="shaixuanZKliT">结果文件</div>
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
                    <div class="PJListli PJListli1 appendname" id="taskName">结果名称</div>
                    <div class="PJListli PJListli1 appname" id="app_name">应用名称</div>
                    <div class="PJListli PJListli1 appcreater" id="username">创建人</div>
                    <div class="PJListli PJListli1 apptime" id="create_datetime">创建时间</div>
                    <div class="PJListli apptime">参数</div>
                    <div class="PJListli PJListli1 apptime" id="taskDescription">运行结果描述</div>
                    <div class="PJListli appjindu">进度</div>
                    <div class="PJListli PJListli1 appfabuZT" id="isRelease">发布状态</div>
                    <div class="PJListli PJListli1 appYBorJS" id="is_async">异步/即时</div>
                    <div class="PJListli ">结果文件</div>

                </div>
                <div class="PJListline"></div>
                <div class="PJul">
                	
                	<c:forEach items="${projectAppTasks }" var="projectAppTaskTemp">
                		<div class="PJli">
	                        <div class="PJliC">
	                            <div class="fuxuanK2">
	                                <input name="ids" type="checkbox" class="input_check" id="check${projectAppTaskTemp.id }" value="${projectAppTaskTemp.id }">
	                                <label for="check${projectAppTaskTemp.id }"></label>
	                            </div>
	                            <a href="#">
	                            	<c:if test="${authoritys['54'] == true }">
				                    	<div class="PJliCli appendname" onclick="taskEndLocation(${projectAppTaskTemp.id})">${projectAppTaskTemp.taskName }</div>
				                    </c:if>
				                    <c:if test="${authoritys['54'] != true }">
				                    	<div class="PJliCli appendname">${projectAppTaskTemp.taskName }（无打开权限）</div>
				                    </c:if> 
	                                <div class="PJliCli appname" style="color:#000000;">${projectAppTaskTemp.app_name }</div>
	                                <div class="PJliCli appcreater" style="color:#000000;">${projectAppTaskTemp.username }</div>
	                                <div class="PJliCli apptime" style="color:#000000;">${projectAppTaskTemp.create_datetime }</div>
	                                <c:if test="${authoritys['55'] == true }">
				                    	<div class="PJliCli apptime param" onclick="showParam(${projectAppTaskTemp.id})">查看</div>
				                    </c:if>
				                    <c:if test="${authoritys['55'] != true }">
				                    	<div class="PJliCli apptime param">（无查看权限）</div>
				                    </c:if> 
	                                
	                                <div class="PJliCli apptime" style="color:#000000;">${projectAppTaskTemp.taskDescription }</div>
	                                
	                                <c:if test="${projectAppTaskTemp.progress == null}">
	                                	<div class="PJliCli appjindu" style="color:#000000;">0%</div>
	                                </c:if>
	                                <c:if test="${projectAppTaskTemp.progress != null}">
	                                	<div class="PJliCli appjindu" style="color:#000000;">${projectAppTaskTemp.progress }</div>
	                                </c:if>
	                                
	                                
	                                
	                                <c:if test="${projectAppTaskTemp.isRelease == 0 }">
	                                	<div class="PJliCli appfabuZT" style="color:#000000;">未发布</div>
	                                </c:if>
	                                <c:if test="${projectAppTaskTemp.isRelease == 1 }">
	                                	<div class="PJliCli appfabuZT" style="color:#000000;">已发布</div>
	                                </c:if>
	                                
	                                <c:if test="${projectAppTaskTemp.is_async == 0 }">
	                                	<div class="PJliCli appYBorJS" style="color:#000000;">即时</div>
	                                </c:if>
	                                <c:if test="${projectAppTaskTemp.is_async == 1 }">
	                                	<div class="PJliCli appYBorJS" style="color:#000000;">异步</div>
	                                </c:if>
	                                
	                                <c:if test="${authoritys['56'] == true }">
				                    	<div class="PJliCli " onclick="showResultFile(${projectAppTaskTemp.id})">查看</div>
				                    </c:if>
				                    <c:if test="${authoritys['56'] != true }">
				                    	<div class="PJliCli " >（无查看权限）</div>
				                    </c:if> 
	                                
	                            </a> 
	                        </div>
	                        <div class="PJliline"></div>
	                    </div>
                	</c:forEach>
                </div>

                <!-- 筛选框 -->
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
                        	<c:forEach items="${queryCondition.strings}" var="queryConditionTemp">
                        		<div class="BTSXcli2li">
	                                <input type="checkbox" class="BTSXcli2liC" name="${queryConditionTemp}"/>
	                                <div class="BTSXcli2liT">${queryConditionTemp}</div>
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
	            if(page!=${page}){
	            	var searchWord = $(".search2Ct").val();
	            	
	            	if($("#isFilter").val() == "true"){
                		shaixuan(page);
                	}else{
                		window.location.href="/wankangyuan/projectAppEnd/selectProjectAppEnd?type=1&page="+page+"&strip=12&searchWord="+searchWord;
                	}
	                
	            }
	        }
	    });
	    
	    $(".search2Ct").bind("keypress" , function (event){
	    	if(event.keyCode == 13){
	    		var searchWord = this.value;
	    		window.location.href="/wankangyuan/projectAppEnd/selectProjectAppEnd?type=1&page=1&strip=12&searchWord="+searchWord;
	    	}
	    });
	    
	    $(".pro_rerun").click(function (){
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
            
            var task_id = ids.join(",");
            var operType = 'update';
            
            $.ajax({
            	url:"/wankangyuan/projectAppEnd/projectAppReRun",
            	type:"post",
            	data:{
            		task_id:task_id,
            		operType:operType
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			window.open(data.message
                        		,'_blank'
                        		,'width=1200,height=600,menubar=no,toolbar=no,status=no,scrollbars=yes')
            		}else{
            			layer.msg("应用参数地址解析失败");
            		}
            	},
            	error : function(){
            		layer.msg("联网失败");
            	}
            });  
	    });
	    //查看运行参数
	    function showParam(task_id){
	    	var operType = 'query';
	    	$.ajax({
            	url:"/wankangyuan/projectAppEnd/projectAppReRun",
            	type:"post",
            	data:{
            		task_id:task_id,
            		operType:operType
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			window.open(data.message
                        		,'_blank'
                        		,'width=1200,height=600,menubar=no,toolbar=no,status=no,scrollbars=yes')
            		}else{
            			layer.msg("应用参数地址解析失败");
            		}
            	},
            	error : function(){
            		layer.msg("联网失败");
            	}
            });
	    }
	    //打开应用结果地址
	    function taskEndLocation(task_id){
	    	$.ajax({
            	url:"/wankangyuan/projectAppEnd/projectAppTaskResultEnd",
            	type:"post",
            	data:{
            		task_id:task_id,
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			window.open(data.message
                        		,'_blank'
                        		,'width=1200,height=600,menubar=no,toolbar=no,status=no,scrollbars=yes')
            		}else{
            			layer.msg("应用结果地址解析失败");
            		}
            	},
            	error : function(){
            		layer.msg("联网失败");
            	}
            });
	    }
	    //查看运行结果文件
	    function showResultFile(task_id){
	    	$.ajax({
            	url:"/wankangyuan/projectAppEnd/projectAppTaskResultFile",
            	type:"post",
            	data:{
            		task_id:task_id,
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			window.open(data.message
                        		,'_blank'
                        		,'width=1200,height=600,menubar=no,toolbar=no,status=no,scrollbars=yes')
            		}else{
            			layer.msg("应用结果文件地址解析失败");
            		}
            	},
            	error : function(){
            		layer.msg("联网失败");
            	}
            });
	    }
	    //发布应用结果到门户
	    $(".pro_fabu").click(function (){
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
            layer.confirm('请确认发布应用结果？',{
            	btn:['确认','取消'],
            	icon:2
            },function(){
            	var taskIds = ids.join(",");
                $.ajax({
                	url:"/wankangyuan/projectAppEnd/projectAppTaskRelease",
                	type:"post",
                	data:{
                		taskIds:taskIds
                	},
                	dataType:"json",
                	success : function(data){
                		layer.msg(data.message);
                		var searchWord = $(".search2Ct").val();
                		window.location.href="/wankangyuan/projectAppEnd/selectProjectAppEnd?type=1&page=1&strip=12&searchWord="+searchWord;
                	},
                	error : function(){
                		layer.msg("联网失败");
                	}
                });
            },function(){
            	return;
            });
            
	    });
	    
	    //取消发布应用结果到门户
	    $(".pro_canfabu").click(function (){
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
            layer.confirm('请确认取消发布应用结果？',{
            	btn:['确认','取消'],
            	icon:2
            },function(){
            	var taskIds = ids.join(",");
                $.ajax({
                	url:"/wankangyuan/projectAppEnd/projectAppTaskUnRelease",
                	type:"post",
                	data:{
                		taskIds:taskIds
                	},
                	dataType:"json",
                	success : function(data){
                		layer.msg(data.message);
                		var searchWord = $(".search2Ct").val();
                		window.location.href="/wankangyuan/projectAppEnd/selectProjectAppEnd?type=1&page=1&strip=12&searchWord="+searchWord;
                	},
                	error : function(){
                		layer.msg("联网失败");
                	}
                });
            },function(){
            	return;
            });
            
	    });
	    
	    //移除应用结果
	    $(".pro_rem").click(function (){
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
            layer.confirm('请确认移除应用结果？',{
            	btn:['确认','取消'],
            	icon:2
            },function(){
            	var taskIds = ids.join(",");
                $.ajax({
                	url:"/wankangyuan/projectAppEnd/projectAppTaskDelete",
                	type:"post",
                	data:{
                		taskIds:taskIds
                	},
                	dataType:"json",
                	success : function(data){
                		layer.msg(data.message);
                		var searchWord = $(".search2Ct").val();
                		window.location.href="/wankangyuan/projectAppEnd/selectProjectAppEnd?type=1&page=1&strip=12&searchWord="+searchWord;
                	},
                	error : function(){
                		layer.msg("联网失败");
                	}
                });
            },function(){
            	return;
            });
	    });
	    
	    
	    
    	//点击项目标题栏
    	$(".PJListli1").click(function (){
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
    		$.ajax({
    			url:"/wankangyuan/projectAppTaskFilter/selectAppTaskDistinctColumnValue",
    			type:"post",
    			data:{
    				columnName:columnName,
    				filter:filter
    			},
    			dataType:"json",
    			success : function(data){
    				if(data.result == true){
    					var BTSXcli2 = document.getElementById("BTSXcli2");//填充筛选值
    					var BTSXcli2_html = "";
    					for(var index in data.message){
    						//if(index < 10){
    							BTSXcli2_html+='<div class="BTSXcli2li">';
    							if(vals.indexOf(data.message[index]) != -1){//如果筛选值在之前的筛选中被勾选了，再次选中它
    								BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" name="'+data.message[index]+'" checked/>';
    							}else{
    								BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" name="'+data.message[index]+'"/>';
    							}
        						
        						BTSXcli2_html+='<div class="BTSXcli2liT">'+data.message[index]+'</div>';
        						BTSXcli2_html+='</div>';
    						//}
    					}
    					BTSXcli2.innerHTML=BTSXcli2_html;
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
    		window.location.href="/wankangyuan/projectAppEnd/selectProjectAppEnd";
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
    		var searchWord = $(".search2Ct").val();//搜索条件
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
    		
    		window.location.href="/wankangyuan/projectAppTaskFilter/selectAppTaskByFilterCondition?page="+page+"&searchWord="
				+searchWord+"&columnName="+columnName+"&order="+order+"&filter="+filter+"&values="+values+"&isFilter=true";
    		
    	}
    
    </script>
</body>
</html>