<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8" />
	<title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/project1.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function project1(bol){
	console.log('222')
	// 筛选菜单框显示隐藏
	    var oshaixuanBT=document.querySelectorAll('.shaixuanBT')[0];//获取筛选下拉按钮
	    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
	    var shaixuanPD=0;

	    oshaixuanBT.onclick=function(event){
	        // if(shaixuanPD==0){
	            oshaixuanZK.className="shaixuanZK active";
	            // shaixuanPD=1;
	        // }else{
	        //     oshaixuanZK.className="shaixuanZK";
	        //     shaixuanPD=0;
	        // }
	        event.stopPropagation();
	        oBTSX.style.display="none";
	        // console.log(1);
	    }
	    oshaixuanZK.onclick=function(event){
	        event.stopPropagation();
	    }

	//筛选按钮显示隐藏选项
	    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
	    var ashaixuanZKliI=document.querySelectorAll('.shaixuanZKliI');//获取所有筛选按钮
	    var oPJList=document.querySelectorAll('.PJList')[0];//项目表头栏
	    var aPJListli=oPJList.querySelectorAll('.PJListli');//项目表头
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
		var oRiqi = document.querySelectorAll('.riqi')[0]
	    oPJListline.style.width=oPJList.offsetWidth*0.98+"px";
	    // console.log(oPJListline.offsetWidth);

	  //点击表头的排序筛选功能
	    var oPJK=document.querySelectorAll('.PJK')[0];//项目框
	    var oBTSX=document.querySelectorAll('.BTSX')[0];//项目表头筛选框
	    if (bol) {
	    	oBTSX = document.querySelectorAll('.riqi')[0];//项目表头筛选框
	    }
	    // var oBTSXpd=document.querySelectorAll('.BTSXpd')[0];//项目表头筛选框判断

	    var oPJList=document.querySelectorAll('.PJList')[0];//项目表头栏
	    var aPJListli=oPJList.querySelectorAll('.PJListli');//项目表头

	    // var BTSXpd=-1;//项目表头筛选框判断

	  //点击设置排序筛选框
	    for(var i=0;i<aPJListli.length;i++){
	        (function(j){
	            aPJListli[j].onclick=function(){
	            	oRiqi.style.display="none";
	            	oBTSX.style.display="none";
	            	if($(this).attr('order') == 'create_time') {
	            		oRiqi.style.display="block";
	            	} else {
	            		oBTSX.style.display="block";
	            	}
	            	$("#isnull").css("display","none");
	            	
	                // if(BTSXpd==j){
	                //     oBTSX.style.display="none";
	                //     BTSXpd=-1;
	                // }else{
	                    // BTSXpd=j;
	                // }
	                var BTSXleft=aPJListli[j].offsetLeft;
	                // oBTSX.name=aPJListli[j].innerHTML;
	                if(document.querySelectorAll('.BTSXpd')[0]){
	                    var oBTSXpd=document.querySelectorAll('.BTSXpd')[0];//项目表头筛选框判断
	                    //oBTSXpd.value=aPJListli[j].name;
	                    //console.log(oBTSXpd.value);
	                }
	                
	                
	                //console.log(BTSXleft);
	                if(BTSXleft>1118){
	                    BTSXleft=1118;
	                }
	                oBTSX.style.left=BTSXleft-20+'px'; 
	                oRiqi.style.left=BTSXleft-20+'px'; 
	                event.stopPropagation();
	                oshaixuanZK.className="shaixuanZK";
	                
	                var oBTSXcliGLK=document.querySelectorAll('.BTSXcliGLK')[0];
	                if(oBTSXcliGLK){
	                	oBTSXcliGLK.value="";
	                }
	            }
	        })(i)
	    }
	    // $(document).click(function(){
	    //     console.log(1);
	    // })
	    window.onclick=function(){
	        //console.log(1);
	        oBTSX.style.display="none";
	        oshaixuanZK.className="shaixuanZK";
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
//	                oBTSXcliIpd.value=index+1;
//	                console.log(oBTSXcliIpd.value);
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

	}
	window.onload = function () {
		project0();
		project1();
		// pro_mine();
		app_mine();
	}
</script>

<body>
	<div class="Box">
		<div class="box">
			<div class="top">
				<h1>
					<img src="<%=request.getContextPath()%>/static/img/newlogo2.png" height="70" width="218" alt="" class="logo" />
				</h1>
				<a href="/wankangyuan/project/selectMyProject">
					<div class="topT">项目</div>
				</a> <a href="/wankangyuan/sourceData/firstIn?type=1">
					<div class="topT">格式数据</div>
				</a> <a href="/wankangyuan/application/viewMine">
					<div class="topT active">应用</div>
				</a>
				<div class="touxiangK">
					<a href=" "> <img src="${user.headimg }" onerror='this.src="/wankangyuan/static/img/head.jpg"' class="touxiang"
						/>
					</a>
					<div class="userbutK">
						<a href="/wankangyuan/userInfo">
							<div class="userbut">用户信息</div>
						</a> <a href="/wankangyuan/message/viewMessage">
							<div class="userbut">
								系统消息
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
				<div class="nicheng">
					<shiro:principal />
				</div>
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
					<a href="/wankangyuan/application/viewMine"><div class="top2Cli top2CliYJ">我的</div></a> <a href="/wankangyuan/application/viewCreate"><div
							class="top2Cli">我创建的</div></a> <a href="/wankangyuan/application/viewPublic"><div class="top2Cli">公共</div></a>
					<div class="search">
						<div class="searchC">
							<img src="<%=request.getContextPath()%>/static/img/search.png" alt="" class="searchCi" data-bind="click:searchAppList" />											<input
								name="appName" type="text" class="searchCt" data-bind="event: { keyup: judgeSearchAppList}" placeholder="搜索应用"
							/>
						</div>
					</div>
				</div>
			</div>
			<div class="shaixuan">
				<div class="shaixuanC">
					<div class="listZT">
						<a href="/wankangyuan/application/viewMine2">
							<div class="listZTli listZT1 active">
								<img src="<%=request.getContextPath()%>/static/img/listZT1.png" alt="" class="listZT1i" /> <img src="<%=request.getContextPath()%>/static/img/listZT1.png"
									alt="" class="listZT1i" />
							</div>
						</a> <a href="javascript:;">
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
							<img src="<%=request.getContextPath()%>/static/img/sanjiao_blue.png" alt="" class="shaixuanBTi" />
						</div>
					</div>
					<!-- <div class="pro_menu app_typeK">
		<div class="app_typek">
			<div class="app_typeT">应用类别</div>
			<div class="app_typeI"></div>
		</div>
	</div> -->
					<div class="pro_menu pro_addK">
						<div class="pro_addk">
							<div class="pro_addT">添加至项目</div>
							<div class="pro_addI"></div>
						</div>
					</div>

					<div class="pro_menu pro_rem" data-bind="click:removeFromMine">移除</div>
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
						<div class="shaixuanZKli">
							<div class="shaixuanZKliI active"></div>
							<div class="shaixuanZKliT">应用类别</div>
						</div>
					</div>
				</div>
				<div class="app_typeul" data-bind="foreach:{data:appTypeList, as:'appType'}">
					<div class="app_typeli" data-bind="text:appType,click:$root.filtrateAppType"></div>
				</div>
				<div class="pro_addul">
					<c:forEach items="${projectList}" var="project">
						<div class="pro_addli" data-bind="click:function(){addToProjrct(${project.p_id})}">${project.p_name }</div>
					</c:forEach>
				</div>
			</div>
			<div class="PJK">
				<div class="PJList">
					<div class="allK">
						<div class="quanxuanK">
							<input type="checkbox" class="input_check" id="check0" data-bind="click: checkAll"> <label for="check0"></label>
						</div>
						<div class="allT">全选</div>
					</div>
					<div class="PJListli appname" name="appName" order="app_name">应用名称</div>
					<div class="PJListli appcreater" name="creator" order="creator">创建人</div>
					<div class="PJListli apptime" name="createTime" order="create_time">创建时间</div>
					<div class="PJListli PJyibu" name="isAsync" order="is_async">异步/同步</div>
					<div class="PJListli PJkeyword" name="keywords" order="keywords">关键字</div>
					<div class="PJListli appexplain" name="appOverview" order="app_overview">应用描述</div>
					<div class="PJListli apptype" name="appType" order="app_type">应用类别</div>
				</div>
				<div class="PJListline"></div>
				<div class="PJul">
					<form id="appList" method="post">
						<div data-bind="foreach:appList">
							<div class="PJli">
								<div class="PJliC">
									<div class="fuxuanK2">
										<input name="ids" type="checkbox" class="input_check" data-bind="value: appId,attr:{id:'check'+($index()+1)},event: { change: $root.checkOne}">
										<label data-bind="attr:{for:'check'+($index()+1)}"></label>
									</div>
									<a data-bind="attr:{href:'/wankangyuan/application/explain?id='+appId}">
										<div class="PJliCli appname" data-bind="text:appName"></div>
										<div class="PJliCli appcreater" data-bind="text:creator"></div>
										<div class="PJliCli apptime" data-bind="text:createTime"></div>
										<div class="PJliCli PJyibu">
											<!-- ko if: 0 == isAsync -->
											同步
											<!-- /ko -->
											<!-- ko if: 1 == isAsync -->
											异步
											<!-- /ko -->
										</div>
										<div class="PJliCli PJkeyword" data-bind="text:keywords"></div>
										<div class="PJliCli appexplain" data-bind="text:appOverview"></div>
										<div class="PJliCli apptype" data-bind="text:appType"></div>
									</a>
								</div>
								<div class="PJliline"></div>
							</div>
						</div>


						<input id="projectId" name="projectId" type="hidden" disabled="disabled">
					</form>
				</div>
				<!--上次选中的值  -->
				<input type="hidden" id="current_field_val" />
				<!--上次选中的表头列  -->
				<input type="hidden" id="current_field" />
				<div class="BTSX">
					<div class="BTSXc">
						<input type="hidden" class="BTSXpd" />
						<div class="BTSXcli">
							<div class="BTSXcliT">排序：</div>
							<div class="BTSXcliI" data-bind="click:function(){orderAppList(' ASC')}">↑</div>
							<div class="BTSXcliI" data-bind="click:function(){orderAppList(' DESC')}">↓</div>
						</div>
						<div class="BTSXcli">
							<div class="BTSXcliT">过滤：</div>
							<input type="text" class="BTSXcliGLK" />
							<button style="display: inline-block;" data-bind="click:searchField">过滤</button>
						</div>
						<div class="BTSXcli">
							<div class="BTSXcliT">值筛选：</div>
						</div>
						<div class="BTSXcli2" data-bind="foreach:{data:fieldList,as:'field'}">
							<div class="BTSXcli2li">
								<input name="option" type="checkbox" class="BTSXcli2liC" data-bind="value:eval('field.'+$('.BTSXpd').val())" />
								<div class="BTSXcli2liT" data-bind="text:eval('field.'+$('.BTSXpd').val())"></div>
							</div>
						</div>
						<div class="BTSXcli3">
							<div class="BTSXcli3BT BTSXcli3BTent" data-bind="click:filterSearchAppList">筛选</div>
							<!-- <div class="BTSXcli3BT BTSXcli3BTres" data-bind="click:$root.fieldList.removeAll();" >重置</div> -->
							<div class="BTSXcli3BT BTSXcli3BTres" data-bind="click:resetFilter">重置</div>
						</div>
					</div>
				</div> 
				<!-- 日期 -->
				<div class="BTSX riqi">
					<div class="BTSXc">
						<input type="hidden" class="BTSXpd" />
						<div class="BTSXcli">
							<div class="BTSXcliT">排序：</div>
							<div class="BTSXcliI" data-bind="click:function(){orderAppList(' ASC')}">↑</div>
							<div class="BTSXcliI" data-bind="click:function(){orderAppList(' DESC')}">↓</div>
						</div>
						<div class="BTSXcli" style="height:100px;">
							<div class="BTSXcliT">过滤：
							<div style="width:156px;margin-left:calc(50% - 30px);line-height:30px;padding-bottom:10px;">
								<input class="Wdate" id="ksrq" type="text" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
								<input class="Wdate" id="jsrq" type="text" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							</div>
							</div>
							
						</div>
						<div class="BTSXcli" style="display:none;">
							<div class="BTSXcliT">值筛选：</div>
						</div>
						<div class="BTSXcli2" style="display:none;" data-bind="foreach:{data:fieldList,as:'field'}">
							<div class="BTSXcli2li">
								<input name="option" type="checkbox" class="BTSXcli2liC" data-bind="value:eval('field.'+$('.BTSXpd').val())" />
								<div class="BTSXcli2liT" data-bind="text:eval('field.'+$('.BTSXpd').val())"></div>
							</div>
						</div>
						<div class="BTSXcli3">
							<div class="BTSXcli3BT BTSXcli3BTent" data-bind="click:filterSearchAppList">筛选</div>
							<!-- <div class="BTSXcli3BT BTSXcli3BTres" data-bind="click:$root.fieldList.removeAll();" >重置</div> -->
							<div class="BTSXcli3BT BTSXcli3BTres" data-bind="click:resetFilter">重置</div>
						</div>
					</div>
				</div>

			</div>

			<div class="pageK" id="box"></div>

			<div class="bottom">
				<a href="javascript:;">
					<div class="bot_guanwang">公司官网</div>
				</a> <a href="javascript:;">
					<div class="bot_guanyu">关于</div>
				</a> <a href="javascript:;">
					<div class="bot_jianyi">反馈建议</div>
				</a>
				<div class="botT">Copyright @2018天津万康源科技有限公司</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/paging.js"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/knockout-3.4.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/mouse.js"></script>
	<c:if test="${not empty msg}">
		<script type="text/javascript">
			layer.msg("${msg}");
		</script>
	</c:if>

	<script type="text/javascript">

		//定义ViewModel
		/******************************* 修改开始  *****************************/
		$.cookie('checkAll',false)
		var isRepeat = false // 是否重复
		var changeType = ''
		var changeName = '' // 点击筛选后再次点击过滤调用此值
		var sourceType = '' // 作为查询条件的数据
		var sourceName = '' // 点击筛选后其他点击过滤调用此值
		var isClick = false  // 是否点击了过滤
		var startDate = ''
		var endDate =  ''
		var appNames, appTypes, appNameOptions, creatorOptions, isAsyncOptions, keywordsOptions, appIntroOptions, createTimeOptions, appTypeOptions = ''
		var mouseCounts = 0
		var pageSize = 12
		var pageIndex = 1
		var indexChange = ''
		var countsLz = 0
		/******************************* 修改结束 *****************************/
		$('.BTSXcli2').on('mousewheel',function (event) {
			console.log(indexChange)
			let _this = $(this)
			if (event.deltaY  < 0) {

				mouseCounts ++
				if (mouseCounts == 4) {
					mouseCounts = 0
					if (pageIndex < Math.ceil(countsLz/pageSize)) {
						pageIndex ++
						$.getJSON("/wankangyuan/userAppRelation/getMyAppFieldList", {
							field: indexChange,
							content: $(".BTSXcliGLK").val(),
							appName: self.appName,
							appType: self.appType,
							appTypeOption: appTypeOptions,
							appNameOption: appNameOptions,
							creatorOption: creatorOptions,
							isAsyncOption: isAsyncOptions,
							keywordsOption: keywordsOptions,
							appIntroOption: appIntroOptions,
							createTimeOption: createTimeOptions,
							pageSize:pageSize,
							pageIndex:pageIndex
							
						}, function (res) {
							console.log(res.data)
							$.each(res.data, (i,n)=> {
								if (indexChange == 'creator') {
									_this.append('<div class="BTSXcli2li"><input name="option" type="checkbox" class="BTSXcli2liC" data-bind="value:eval("indexChange."+$(".BTSXpd").val())" value="'+n.creator+'"><div class="BTSXcli2liT" data-bind="text:eval("indexChange."+$(".BTSXpd").val())">'+n.creator+'</div></div>')
								}
								if (indexChange == 'app_name') {
									_this.append('<div class="BTSXcli2li"><input name="option" type="checkbox" class="BTSXcli2liC" data-bind="value:eval("indexChange."+$(".BTSXpd").val())" value="'+n.appName+'"><div class="BTSXcli2liT" data-bind="text:eval("indexChange."+$(".BTSXpd").val())">'+n.appName+'</div></div>')
								}
								if (indexChange == 'keywords') {
									_this.append('<div class="BTSXcli2li"><input name="option" type="checkbox" class="BTSXcli2liC" data-bind="value:eval("indexChange."+$(".BTSXpd").val())" value="'+n.keywords+'"><div class="BTSXcli2liT" data-bind="text:eval("indexChange."+$(".BTSXpd").val())">'+n.keywords+'</div></div>')
								}
								if (indexChange == 'app_overview') {
									_this.append('<div class="BTSXcli2li"><input name="option" type="checkbox" class="BTSXcli2liC" data-bind="value:eval("indexChange."+$(".BTSXpd").val())" value="'+n.appOverview+'"><div class="BTSXcli2liT" data-bind="text:eval("indexChange."+$(".BTSXpd").val())">'+n.appOverview+'</div></div>')
								}
								if (indexChange == 'app_type') {
									_this.append('<div class="BTSXcli2li"><input name="option" type="checkbox" class="BTSXcli2liC" data-bind="value:eval("indexChange."+$(".BTSXpd").val())" value="'+n.appType+'"><div class="BTSXcli2liT" data-bind="text:eval("indexChange."+$(".BTSXpd").val())">'+n.appType+'</div></div>')
								}
								
							})
							
						})
					}
					
					
				}
			} else {
				mouseCounts = 0
			}
		})
		function ViewModel() {
			var lastField = "";
			var lastFieldVal = "";

			var self = this;
			var page, rows, total, appName, appType, orderName, orderDir, field, option;
			var appNameOption, creatorOption, isAsyncOption, keywordsOption, appIntroOption, createTimeOption, appTypeOption;
			self.appList = ko.observableArray();
			self.appCentAll = ko.observableArray();
			self.appCart = ko.observableArray();



			self.showAppList = function () {
				//  self.option  为选中的值，根据选中的值进行数据筛选
				var field = $(".BTSXpd").attr("order");//选中的字段
				console.log($(".BTSpd"))
				console.log(self.option)
				sourceName = self.option
				var field_val = "";
				if (field == "is_async") {
					field_val = self.isAsyncOption;
				} else if (field == "create_time") {
					field_val = self.createTimeOption;
				} else if (field == "keywords") {
					field_val = self.keywordsOption;
				} else if (field == "creator") {
					field_val = self.creatorOption;
				} else if (field == "app_overview") {
					field_val = self.appIntroOption;
				} else if (field == "app_type") {
					field_val = self.appType;
				} else if (field == "app_name") {
					field_val = self.appNameOption;
				}

				/* 
				$("#current_field_val").val(lastFieldVal) */
				/* if($("#current_field").val()!=field){
					$("#current_field").val(field)
				}  */
				/* var objString = JSON.stringify(field_val); //JSON 数据转化成字符串
				$.cookie(field,objString); */
				if (self.field == 'appName') {
					appNameOptions = self.option
				}
				if (self.field == 'appType') {
					appTypeOptions = self.option
				}
				if (self.field == 'creator') {
					creatorOptions = self.option
				}
				if (self.field == 'isAsync') {
					isAsyncOptions = self.option
				}
				if (self.field == 'keywords') {
					keywordsOptions = self.option
				}
				if (self.field == 'createTime') {
					createTimeOptions = $('#ksrq').val() +','+ $('#jsrq').val()
				}
				if (self.field == 'appOverview') {
					appIntroOptions = self.option
				}
				// 查询数据列表
				$.getJSON("/wankangyuan/userAppRelation/getMine", {
					page: page,
					appName: self.appName,
					appType: self.appType,
					appNameOption: appNameOptions,
					creatorOption: creatorOptions,
					isAsyncOption: isAsyncOptions,
					keywordsOption: keywordsOptions,
					appIntroOption: appIntroOptions,
					createTimeOption: createTimeOptions,
					appTypeOption: appTypeOptions
				}, function (data) {
					page = data.page;
					rows = data.rows;
					total = data.total;
					self.appList.removeAll();
					var field_vals = [];
					for (var i in data.list) {
						self.appList.push(data.list[i]);
						// 分页  
						$('#box').paging({
							initPageNo: page, // 初始页码
							totalPages: Math.ceil(total/rows), //总页数
							totalCount: '合计&nbsp;' + self.total + '&nbsp;条数据', // 条目总数
							slideSpeed: 600, // 缓动速度。单位毫秒
							jump: true, //是否支持跳转
							callback: function(page_) { // 回调函数  相当于点击之后执行
								if ($.cookie('checkAll') == 'true') {
										
										$('.input_check').each(function () {
												$(this).prop('checked',true)
											}) 
										}
								if(page_!=page){
									page = page_;
									self.showAppList();
									//是否为全选状态
									$(".input_check").each(function(){
									var index = $.inArray(Number($(this).val()),self.appCart());
									if(index >= 0){
										$(this).attr("checked",true);
									}
								})
									
								}
							}
						});
					}
					project0();
					console.log(field)
					project1();
					app_mine();
					$('#box').paging({
						initPageNo: page, // 初始页码
						totalPages: Math.ceil(total / rows), //总页数
						totalCount: '合计&nbsp;' + self.total + '&nbsp;条数据', // 条目总数
						slideSpeed: 600, // 缓动速度。单位毫秒
						jump: true, //是否支持跳转
						callback: function (page_) { // 回调函数
							if (page_ != page) {
								page = page_;
								self.showAppList();
							}
						}
					});
					// 设置选中
/* 					$.getJSON("/wankangyuan/userAppRelation/getMine", {
						page: 1,
						rows: 1000,
						appName: self.appName,
						appType: self.appType,
						appNameOption: appNameOptions,
						creatorOption: creatorOptions,
						isAsyncOption: isAsyncOptions,
						keywordsOption: keywordsOptions,
						appIntroOption: appIntroOptions,
						createTimeOption: createTimeOptions,
						appTypeOption: appTypeOptions
					}, function (data) {
						self.appCentAll.removeAll();
						for (var i in data.list) {
							self.appCentAll.push(data.list[i].appId);
						}
						var is_all = true;
						$(".input_check:gt(0)").each(function () {
							var index = $.inArray(Number($(this).val()), self.appCart());
							if (index >= 0) {
								$(this).attr("checked", true);
							} else {
								is_all = false;
							}
						})
						if (is_all) {
							$("#check0").attr('checked', true);
						} else {
							$("#check0").attr('checked', false);
						}
					}) */
				})
			}
			//全选
			self.checkAll = function () {
				if ($("#check0").attr('checked')) {
					$.cookie('checkAll',true)
					self.appCart.removeAll();
					for (var i in self.appCentAll()) {
						self.appCart.push(self.appCentAll()[i]);
					}
				} else {
					$.cookie('checkAll',false)
					self.appCart.removeAll();
				}
				return true;
			}
			//复选
			self.checkOne = function (option) {

				var index = $.inArray(Number(option.appId), self.appCart());
				if (index >= 0) {
					self.appCart.remove(option.appId);
				} else {
					self.appCart.push(option.appId);
				}

				if (self.appCentAll().length == self.appCart().length) {
					$("#check0").attr('checked', true);
				} else {
					$("#check0").attr('checked', false);
				}
			}

			//添加到项目
			self.addToProjrct = function (projectId) {
				console.log(projectId+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
				if (self.appCart().length == 0) {
					layer.msg("请至少选中一个");
				} else {
					layer.confirm('请确认是否添加?', {
						btn: ['确认', '取消'], //按钮
						icon: 2
					}, function () {
						$.ajax({
							type: "POST",
							url: "/wankangyuan/ProjectAppRelation/addToProject",
							data: { projectId: projectId, ids: self.appCart().join(",") }, //可选参数
							dataType: "json",
							success: function (result) {
								layer.msg(result.message, {
									anim: 0,
									end: function (index) {
										if (result.status == '200') {
											window.location.href = "/wankangyuan/project/selectMyProject";
										} else {
											window.location.reload();
										}
									}
								});
							},
							error: function (result) {
								layer.msg(result.message, {
									anim: 0,
									end: function (index) {
										window.location.reload();
									}
								});
							}
						});

					}, function () {
						return;
					});
				}
			}

			//移除
			self.removeFromMine = function () {

				if (self.appCart().length == 0) {
					layer.msg("请至少选中一个");
				} else {
					layer.confirm('请确认是否移除?', {
						btn: ['确认', '取消'], //按钮
						icon: 2
					}, function () {
						$.ajax({
							type: "POST",
							url: "/wankangyuan/userAppRelation/removeFromMine",
							data: { ids: self.appCart().join(",") }, //可选参数
							dataType: "json",
							success: function (result) {
								layer.msg(result.message, {
									anim: 0,
									end: function (index) {
										window.location.reload();
									}
								});
							},
							error: function (result) {
								layer.msg(result.message, {
									anim: 0,
									end: function (index) {
										window.location.reload();
									}
								});
							}
						});

					}, function () {
						return;
					});
				}
			}
			//初始化列表
			self.showAppList();

			//点击搜索
			self.searchAppList = function () {
				page = 1;
				self.appCart.removeAll();
				$("#check0").attr('checked', false);
				self.appName = $("input[name='appName']").val();
				self.showAppList();
			}
			//回车搜索
			self.judgeSearchAppList = function (data, event) {
				if (event.keyCode == "13") {
					self.searchAppList();
				}
			}
			//筛选搜索  
			self.filterSearchAppList = function () {
				if(!isClick) {
					alert('请先过滤,谢谢！')
					return false
				}
				arr = [];
				$("input[name='option']:checked").each(function (i) {
					arr[i] = $(this).val();
				});
				if (arr.length == 0) {
					$("input[name='option']").each(function (i) {
						arr[i] = $(this).val();
					});
				}
				self.option = arr.join(",");
				//self.appName = "";
				self.field = $(".BTSXpd").val();
				if (self.field == "appName") {

					if (isRepeat) {
						self.appNameOption = self.option;
					}

				} else if (self.field == "creator") {

					if (isRepeat) {
						self.creatorOption = self.option;
					}

				} else if (self.field == "isAsync") {

					if (isRepeat) {
						self.isAsyncOption = self.option;
					}
				} else if (self.field == "keywords") {

					if (isRepeat) {
						self.keywordsOption = self.option;
					}
				} else if (self.field == "appOverview") {

					if (isRepeat) {
						self.appIntroOption = self.option;
					}
				} else if (self.field == "createTime") {

					if (isRepeat) {
						self.createTimeOption = self.option;
					}
				} else if (self.field == "appType") {

					if (isRepeat) {
						self.appTypeOption = self.option;
					}
				}
				isRepeat = false
				page = 1;
				self.appCart.removeAll();
				$("#check0").attr('checked', false);
				$(".BTSXcliGLK").val("");
				self.showAppList()
				$('.BTSX').hide()
				

			}

			//重置筛选
			self.resetFilter = function () {
				appNameOptions = "";
				creatorOptions = "";
				isAsyncOptions = "";
				keywordsOptions = "";
				appIntroOptions = "";
				createTimeOptions = "";
				appTypeOptions = "";
				changeType = '';
				changeName = '';
				sourceName = '';
				sourceType = '';
				self.option = '';
				page = 1;
				self.appCart.removeAll();
				$("#check0").attr('checked', false);
				self.showAppList();
			}

			//排序搜索
			self.orderAppList = function (order) {
				self.orderName = $(".BTSXpd").attr("order");
				self.orderDir = order;
				self.searchAppList();
			}
			//显示字段列表
			self.fieldList = ko.observableArray();

			//  过滤
			self.searchField = function () {
				
				var field = $(".BTSXpd").attr("order");//选中的字段


				self.fieldList.removeAll();

				/***************************修改开始 *********************/
				/*
				和上一次点击是否相等，记录当前点击的列
				*/
				isClick= true
				if (changeType == field) {

					isRepeat = true
					sourceType = field
				} else {

					isRepeat = false
					changeType = field
				}
				/*******************************修改结束 *************************/
				if (isRepeat) {  // 相同过滤时，显示备选值， 将选中的值赋给sourceName

					if (changeType == "is_async") {
						isAsyncOptions = changeName;

					} else if (changeType == "create_time") {
						createTimeOptions = changeName;

					} else if (changeType == "keywords") {
						keywordsOptions = changeName;

					} else if (changeType == "creator") {
						creatorOptions = changeName

					} else if (changeType == "app_overview") {
						appIntroOptions = changeName;

					} else if (changeType == "app_type") {
						appTypeOptions = changeName;

					} else if (changeType == "app_name") {
						appNameOptions = changeName;

					}
				} else { // 不同过滤时，固定上次过滤条件
					console.log(sourceName,'过滤条件')
					if (sourceType == "is_async") {
						isAsyncOptions = sourceName
					} else if (sourceType == "create_time") {
						createTimeOptions = sourceName;

					} else if (sourceType == "keywords") {
						keywordsOptions = sourceName;

					} else if (sourceType == "creator") {
						creatorOptions = sourceName;

					} else if (sourceType == "app_overview") {
						appIntroOptions = sourceName;

					} else if (sourceType == "app_type") {
						appTypeOptions = sourceName;

					} else if (sourceType == "app_name") {
						appNameOptions = sourceName;

					}
				}
				indexChange = field
				// 筛选条件查询 根据点击筛选记录的条件 进行多条件查询
				$.getJSON("/wankangyuan/userAppRelation/getMyAppFieldList", {
					field: field,
					content: $(".BTSXcliGLK").val(),
					appName: self.appName,
					appType: self.appType,
					appTypeOption: appTypeOptions,
					appNameOption: appNameOptions,
					creatorOption: creatorOptions,
					isAsyncOption: isAsyncOptions,
					keywordsOption: keywordsOptions,
					appIntroOption: appIntroOptions,
					createTimeOption: createTimeOptions,
					pageSize:pageSize,
					pageIndex:pageIndex
					
				}, function (res) {
					/****************      修改开始******************/
					var data= res.data
					countsLz = res.total
					$('.BTSXcli2').html('')
					if (!isRepeat) {
						changeName = '' // 重置过滤查询条件
						for (let a = 0; a < data.length; a++) {
							let json = data[a]
							for (let key in json) {

								changeName += json[key] + ','
							}
							if (JSON.stringify(json) === '{}') {
								changeName += 'null,'
							}
						}
						changeName = changeName.substr(0, changeName.length - 1)

					}
					/********************* 修改结束**********************/
					var field_vals = [];
					var isnull = false;
					for (var i = 0; i < data.length; i++) {
						if ($(".BTSXpd").attr("order") == "is_async") {
							if (data[i] == null) {
								isnull = true;
							} else if (data[i].isAsync == '0') {
								data[i].isAsync = "同步"
								self.fieldList.push(data[i]);
							} else if (data[i].isAsync == '1') {
								data[i].isAsync = "异步"
								self.fieldList.push(data[i]);
							} else {
								isnull = true;
							}
							field_vals.push(data[i].isAsync);
						}
						if ($(".BTSXpd").attr("order") == "create_time") {
							if (data[i] == null) {
								isnull = true;
							} else if (data[i].createTime != "" && data[i].createTime != null) {
								self.fieldList.push(data[i]);
							} else {
								isnull = true;
							}
							field_vals.push(data[i].createTime);
							
						}
						if ($(".BTSXpd").attr("order") == "keywords") {
							if (data[i] == null) {
								isnull = true;
							} else
								if (data[i].keywords != "" && data[i].keywords != null) {
									self.fieldList.push(data[i]);
								} else {
									isnull = true;
								}
							field_vals.push(data[i].keywords);
						}
						if ($(".BTSXpd").attr("order") == "creator") {
							if (data[i] == null) {
								isnull = true;
							} else
								if (data[i].creator != "" && data[i].creator != null) {
									self.fieldList.push(data[i]);
								} else {
									isnull = true;
								}
							field_vals.push(data[i].creator);
						}
						if ($(".BTSXpd").attr("order") == "app_overview") {
							if (data[i] == null) {
								isnull = true;
							} else
								if (data[i].appOverview != "" && data[i].appOverview != null) {
									self.fieldList.push(data[i]);
								} else {
									isnull = true;
								}
							field_vals.push(data[i].appOverview);
						}
						if ($(".BTSXpd").attr("order") == "app_type") {
							if (data[i] == null) {
								isnull = true;
							} else
								if (data[i].appType != "" && data[i].appType != null) {
									self.fieldList.push(data[i]);
								} else {
									isnull = true;
								}
							field_vals.push(data[i].appType);
						}
						if ($(".BTSXpd").attr("order") == "app_name") {
							if (data[i] == null) {
								isnull = true;
							} else
								if (data[i].appName != "" && data[i].appName != null) {
									self.fieldList.push(data[i]);
								} else {
									isnull = true;
								}
							field_vals.push(data[i].appName);
						}
					}
					/* if ($(".BTSXpd").attr("order") == "create_time") {
						startDate = data[0].createTime
						$('#ksrq').val(startDate)
						endDate = data[data.length - 1].createTime
						$('#jsrq').val(endDate)
					} */
					//清空字段
					$("#isnull").remove();
					var filesList = $(".BTSXcli2");
					if (isnull) {
						filesList.prepend('<div class="BTSXcli2li" id="isnull">' +
							' <input name="option" type="checkbox" class="BTSXcli2liC" value="null" />' +
							'<div class="BTSXcli2liT">空值</div>' +
							' </div>');
					}
				});


			}
/* 			$(".PJListli").click(function () {
				isClick= false
				self.fieldList.removeAll();
				$(".BTSXpd").val($(this).attr("name"));
				$(".BTSXpd").attr("order", $(this).attr("order"));
			}); */
			$(".PJListli").each(function () {
				$(this).click(function () {
					$(".PJListli").css({
						'color':'#16579b'
					})
					$(this).css({
						'color':'#ff0033'
					})
					isClick= false
					self.fieldList.removeAll();
					$(".BTSXpd").val($(this).attr("name"));
					$(".BTSXpd").attr("order", $(this).attr("order"));
					pageIndex = 1
					self.searchField()
					
				})
			})

			/* -------------------------------------------- */
			//应用类型列表
			self.appTypeList = ko.observableArray();
			self.filtrateAppType = function (appType) {
				
				self.appType = appType;
				self.searchAppList();
			}
			self.getAppTypeList = function () {
				$.getJSON("/wankangyuan/userAppRelation/getMyAppTypeList", function (data) {
					self.appTypeList.removeAll();
					for (var i in data) {
						self.appTypeList.push(data[i]);
					}
				});
			}
			self.getAppTypeList();
		}
		var vm = new ViewModel();
		ko.applyBindings(vm);

		function lastField(field, self) {
			var field_val = "";//选中的值
			if ($("#current_field").val() != field) {
				/* if(field == "is_async"){
					field_val=self.isAsyncOption;
				}else if(field == "create_time"){
					field_val=self.createTimeOption;
				}else if(field == "keywords"){
					field_val=self.keywordsOption;
				}else if(field == "creator"){
					field_val=self.creatorOption;
				}else if(field == "app_overview"){
					field_val=self.appIntroOption;
				}else if(field == "app_type"){
					field_val=self.appType;
				}else if(field == "app_name"){
					field_val=self.appNameOption;
				} */
				$("#current_field").val(field)
				//if(field_val!=null){
				//$("#current_field_val").val(field_val)
			} else {
				if (field == "is_async") {
					self.isAsyncOption = $("#current_field_val").val();
				} else if (field == "create_time") {
					self.createTimeOption = $("#current_field_val").val();
				} else if (field == "keywords") {
					self.keywordsOption = $("#current_field_val").val();
				} else if (field == "creator") {
					self.creatorOption = $("#current_field_val").val();
				} else if (field == "app_overview") {
					self.appIntroOption = $("#current_field_val").val();
				} else if (field == "app_type") {
					self.appType = $("#current_field_val").val();
				} else if (field == "app_name") {
					self.appNameOption = $("#current_field_val").val();
				}
			}
		}
		function filterCurrentField(field) {
			if ($("#current_field").val() == field) {
				if (field == "is_async") {
					self.isAsyncOption = $("#current_field_val").val();
				} else if (field == "create_time") {
					self.createTimeOption = $("#current_field_val").val();
				} else if (field == "keywords") {
					self.keywordsOption = $("#current_field_val").val();
				} else if (field == "creator") {
					self.creatorOption = $("#current_field_val").val();
				} else if (field == "app_overview") {
					self.appIntroOption = $("#current_field_val").val();
				} else if (field == "app_type") {
					self.appType = $("#current_field_val").val();
				} else if (field == "app_name") {
					self.appNameOption = $("#current_field_val").val();
				}
			}
		}

	</script>
</body>

</html>