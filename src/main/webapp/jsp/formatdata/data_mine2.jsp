
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<link rel="stylesheet" type="text/css"
	href="/wankangyuan/static/css/project1.css" />
<script type="text/javascript"
	src="/wankangyuan/jsp/formatdata/js/project1.js"></script>

<script type="text/javascript">
    window.onload=function(){
        project0();
        //project1();
        project2();
        //pro_dataLB();
        pro_data();
        data_mine()
    }
</script>
<body>
    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="/wankangyuan/static/img/newlogo2.png" height="70" width="218" alt="" class="logo" /></h1>
                <a href="/wankangyuan/project/selectMyProject">
					<div class="topT">项目</div>
				</a> <a href="/wankangyuan/sourceData/getSourceDatas?type=1">
					<div class="topT active">格式数据</div>
				</a> <a href="/wankangyuan/application/viewMine">
					<div class="topT">应用</div>
					</a>
                <div class="touxiangK">
                    <a href="/wankangyuan/userInfo">
                        <img src="${user.headimg }" onerror='this.src="/wankangyuan/static/img/head.jpg"'  }" alt="" class="touxiang" />
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
                    <a href="/wankangyuan/sourceData/firstIn?type=1&cs_id=${source.cs_id }"><div class="top2Cli top2CliYJ">我的</div></a>
                    <a href="/wankangyuan/sourceData/firstIn?type=2&cs_id=${source.cs_id }"><div class="top2Cli">我创建的</div></a>
                    <a href="/wankangyuan/sourceData/firstIn?type=3&cs_id=${source.cs_id }"><div class="top2Cli">公共</div></a>
                    <div class="search">
						<div class="searchC">
							<img src="/wankangyuan/static/img/search.png" alt=""
								class="searchCi"  onclick="searchFirst()"/> <input type="text" class="searchCt"
								placeholder="搜索数据" value="${searchFirstWord}" />
						</div>
					</div>
                </div>
            </div>
            <div class="shaixuan">
                <div class="shaixuanC">
                    <div class="listZT">
                        <a href="javascript:;">
                        </a>
                        <a href="<%=request.getContextPath()%>/jsp/formatdata/data_mine.jsp">
                            <div class="listZTli listZT2 active">
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
							<img src="/wankangyuan/static/img/sanjiao_blue.png" alt=""
								class="shaixuanBTi" />
						</div>
					</div>
                    <div class="jiangeline"></div>
                    <div class="allK">
                        <div class="quanxuanK">
                            <input type="checkbox" class="input_check" id="check0">
                            <label for="check0"></label>
                        </div>
                        <div class="allT">全选</div>
                        
                    </div>
                    <!-- <div class="pro_menu pro_exit">退出</div> -->
                    <div class="pro_menu pro_addK">
                        <div class="pro_addk">
                            <div class="pro_addT">添加至项目</div>
                            <div class="pro_addI"></div>
                        </div>
                    </div>
                    <div class="pro_menu pro_rem" id="removeSourceDatas">移出</div>
					<div class="pro_menu pro_export" style="display: none;">导出</div>
                    <!-- 展示数据源列表 ， 需要为select 的option 的onclick设置事件监听-->
                    <select id="source_Select" class="pro_menusel">
						<c:forEach items="${sources}" var="sourcel">
							<c:if test="${sourcel.cs_id!=source.cs_id}">
								<option value="${sourcel.cs_id}">${sourcel.cs_name}</option>
							</c:if>
							<c:if test="${sourcel.cs_id==source.cs_id}">
								<option value="${sourcel.cs_id}" selected="selected">${sourcel.cs_name}</option>
							</c:if>
						</c:forEach>
					</select>
                </div>
                <div class="pro_addul">
                    <c:forEach items="${projects}" var="projectTemp">
						<div class="pro_addli" id="${projectTemp.p_id }">${projectTemp.p_name}</div>
					</c:forEach>
                </div>
                <div class="shaixuanZK">
					<div class="shaixuanZKC">
						<c:forEach items="${source.sourceFields}" var="sourceFieldTemp">
							<div class="shaixuanZKli">
								<div class="shaixuanZKliI active"></div>
								<div class="shaixuanZKliT">${sourceFieldTemp.csf_name}</div>
							</div>
						</c:forEach>
					</div>
				</div>
            </div>
		<div class="PJK">
			<input type="hidden" id="ids" value="${ids}" />
			<input type="hidden" id="isAll" value="${isAll}" />
			<input type="hidden" id="ids2" value="${allids}" />
			<input type="hidden" id="isAll2" value="${isAll1 }" />
			<c:forEach items="${sourceDatas}" var="sourceData">
				<div class="PJ2list">
					<div class="PJK2li">
						
							<c:forEach items="${sourceData}" var="sourceDataField" varStatus="status">
								<c:if test="${status.index==0}">
									<td>
										<div class="fuxuanK3">
											<input type="checkbox" class="input_check" name="${sourceDataField}" value="${sourceDataField}"  id="check${sourceDataField}">
											<label for="check${sourceDataField}"></label>
										</div>
									</td>
								</c:if>
							<c:if test="${status.index!=0}">
								<div onclick="datainHref('${sourceData[0]}')" style="cursor:pointer;">
								<div class="PJK2litop">
									<a href="#" >
										<div class="PJK2litopT PJliCli_1">
										${source.sourceFields[status.index-1].csf_name} :
											<c:choose>
												<c:when test="${source.sourceFields[status.index-1].type=='图片' || source.sourceFields[status.index-1].type=='文件'}">
													${fn:substringAfter(sourceDataField, "_")}
												</c:when>
												<c:otherwise>
													${sourceDataField}
												</c:otherwise>
											</c:choose>
										</div>
									</a>
								</div>
							</div>
							</c:if>
							</c:forEach>
						
					</div>
				</div>
			</c:forEach>
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
			<div id="oldCondition" style="display: none;">${oldCondition}</div>
			 </div>
		</div>

	<script type="text/javascript"
		src="/wankangyuan/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="/wankangyuan/static/js/paging.js"></script>
	<script type="text/javascript">
	
	$(function(){
		var oquanxuanK=document.querySelectorAll('.quanxuanK')[0];//获取页面中全选框的外部div
		var oinput_check=oquanxuanK.querySelectorAll('.input_check')[0];//获取上方div内的全选按钮checkbox
		var oisAll=document.querySelectorAll('#isAll2')[0];//获取隐藏域_存储是否全选的
		var oquanxuanPD=oinput_check.checked;//定义一个变量，存储当前是否全选
		var oids=document.querySelectorAll('#ids2')[0];//获取隐藏域_存储正选或者反选id的
		var afuxuanK2=document.querySelectorAll('.fuxuanK3');//获取页面中复选框的外部div，注意获取的class，本页面是fuxuanK2，有的页面为fuxuanK3或其他
		
		var ainput_check=[];//定义一个数组用来存储页面中的复选框
		for(var i=0;i<afuxuanK2.length;i++){
			var oinput=afuxuanK2[i].querySelectorAll('.input_check')[0];
			ainput_check.push(oinput);//获取页面中的复选框填充到刚才的数组中
		}
		console.log(ainput_check);
		console.log(oisAll.value);
		console.log(oids.value);
		var timer=null;
		if(oisAll.value=='true'){
			console.log(111);
			oinput_check.checked="checked";
			timer=setTimeout(function(){
				oinput_check.checked="checked";
			},100);
			for(var i=0;i<afuxuanK2.length;i++){
				var oinput=afuxuanK2[i].querySelectorAll('.input_check')[0];
				oinput.checked='checked';
			}
			var oidsarr1=oids.value.split(',');
			for (var i=0;i<oidsarr1.length;i++){
				for(var j=0;j<afuxuanK2.length;j++){
					var oinputy=afuxuanK2[j].querySelectorAll('.input_check')[0];
					if(oinputy.id==oidsarr1[i]){
						oinput_check.checked="";
						oinputy.checked="";
					}
				}
			}
		}else{
			console.log(222);
			oinput_check.checked="";
			timer=setTimeout(function(){
				oinput_check.checked="";
			},100);
			for(var i=0;i<afuxuanK2.length;i++){
				var oinput=afuxuanK2[i].querySelectorAll('.input_check')[0];
				oinput.checked='';
			}
			var oidsarr1=oids.value.split(',');
			for (var i=0;i<oidsarr1.length;i++){
				for(var j=0;j<afuxuanK2.length;j++){
					var oinputy=afuxuanK2[j].querySelectorAll('.input_check')[0];
					if(oinputy.id==oidsarr1[i]){
						oinput_check.checked="";
						oinputy.checked="checked";
					}
				}
			}
		}
		$(oinput_check).change(function(){//页面中全选按钮状态变换的时候
			/* console.log(oinput_check.checked); */
			oisAll.value=oinput_check.checked;//全选隐藏域状态跟随变换
			oids.value="";//id隐藏域清空
			oquanxuanPD=oinput_check.checked;//存储全选的变量的值跟随变换
			console.log(oisAll.value);
		})
		for(var i=0;i<ainput_check.length;i++){
			(function(index){
				$(ainput_check[index]).change(function(){//页面中复选框的变换
					if(oisAll.value=='true'){//判断刚才存储的是否全选的变量，以此判断，点击复选框的时候记录的是选中还是反选，如果全选框是true，则复选框的点击是记录反选
						if(!this.checked){//因为是记录反选，所以判断如果点的这个复选框在点击后是非选中状态，说明这个id需要记录
							var oidsarr=oids.value.split(',');
							oidsarr.push(this.id);
							oids.value=oidsarr.join(',');
						}else{//因为是记录反选，所以判断如果点的这个复选框在点击后是选中状态，说明这个id需要从id隐藏域中删除
							var oidsarr=oids.value.split(',');
							for(var j=0;j<oidsarr.length;j++){
								if(this.id==oidsarr[j]){
									oidsarr.splice(j,1);
								}
							}
							oids.value=oidsarr.join(',');
						}
						console.log(oids.value);
					}else{//如果全选框是false，则复选框的点击是记录正选
						if(this.checked){//因为是记录正选，所以判断如果点的这个复选框在点击后是选中状态，说明这个id需要记录
							var oidsarr=oids.value.split(',');
							oidsarr.push(this.id);
							oids.value=oidsarr.join(',');
						}else{//因为是记录正选，所以判断如果点的这个复选框在点击后是选中状态，说明这个id需要从id隐藏域中删除
							var oidsarr=oids.value.split(',');
							for(var j=0;j<oidsarr.length;j++){
								if(this.id==oidsarr[j]){
									oidsarr.splice(j,1);
								}
							}
							oids.value=oidsarr.join(',');
						}
						console.log(oids.value);
					}
					console.log(oisAll.value);
				})
			})(i)
		}
	})


	var cs_id=$('#source_Select').val();
	var searchId="${searchId}";
	var searchWord="";
	var desc_asc="${desc_asc}";
	var oldCondition=$("#oldCondition").html();
	var page="${page}";
	var searchFirstWord=$(".searchCt").val();
	$("#source_Select").change(function(){
		cs_id = $("#source_Select").val();
			window.location.href="/wankangyuan/sourceData/getSourceDatas?block=2&type=1&cs_id="+cs_id+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
	
	});

	$('.PJListli').click(function(){
		searchId = $(this).attr('id');
	});
	//全搜索
	function searchFirst(){
		reset();
		searchFirstWord=$(".searchCt").val();
		window.location.href="/wankangyuan/sourceData/getSourceDatas?block=2&type=1&cs_id="
				+cs_id+"&searchFirstWord="+searchFirstWord+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
	}
    $(".searchCt").bind("keypress" , function(event){
    	searchFirstWord=this.value;
		if(event.keyCode == 13){
			reset();
			window.location.href="/wankangyuan/sourceData/getSourceDatas?block=2&type=1&cs_id="
					+cs_id+"&searchFirstWord="+this.value+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
			
		}
	});
  //点击过滤按钮
	$("#guolv").click(function (){
		//过滤
		filter();
	});
  
	$(".PJListli").click(function (){
		document.querySelectorAll(".BTSX")[0].id=this.id;//更新筛选框的筛选字段为当前点击的字段名
		$("#BTSXcli2").html("");
			//filter();//然后自动执行过滤，筛选出十个数值
	}); 

	$('.BTSXcliGLK').keypress(function(e){		
		if (e.keyCode == 13) {
			filter();
		}
	})
	function filter(){
		searchWord=$(".BTSXcliGLK").val();//过滤条件
		$.ajax({
			type:"post",
			url:"/wankangyuan/sourceData/getSourceFieldDatas",
			async:true,
			data:{
				type:1,
				cs_id:$('#source_Select').val(),
				searchId:searchId,
				searchWord:searchWord,
        		oldCondition:oldCondition
			},
			success:function(res){
				if (res.result) {
					var htmlStr =  '<div class="BTSXcli2li">'
									+'<input type="checkbox" class="BTSXcli2liI" />'
									+'<div class="BTSXcli2liT">空值</div>'
								+'</div>'
								+'<div class="BTSXcli2li">'
								+	'<input type="checkbox" class="BTSXcli2liI"  style="display: none;"/>'
								+'</div>';
					var data = res.csfDatas;
					for (var i in data) {
						htmlStr += '<div class="BTSXcli2li">'
								+		'<input type="checkbox" class="BTSXcli2liI" />'
								+		'<div class="BTSXcli2liT">' + data[i] + '</div>'
								+	'</div>';
					}
					$('.BTSXcli2').html(htmlStr);
				}
			}
		});
		$(".BTSXcliGLK").val("")
	};
	
	var oBTSXcliI1=document.querySelectorAll('.BTSXcliI')[0];
	var oBTSXcliI2=document.querySelectorAll('.BTSXcliI')[1];
	
	
	function updown(sc){
		desc_asc=sc;
	    window.location.href="/wankangyuan/sourceData/getSourceDatas?block=2&type=1&cs_id="+cs_id+"&searchId="+searchId+
	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
	}

	//重置，清空累加筛选条件
	function chongzhi(){
		reset();
		shaixuan(0);
	}		
	function reset(){
		$('#oldCondition').html('');
		oldCondition="";
		$.ajax({
			type:"post",
			url:"/wankangyuan/sourceData/reset",
			async:true,
			data:{					
			},
			success:function(data){
				/* if (data.result) {
					alert(data.message);
				} */
			}
		});
		
	}

	function shaixuan(likeSearch){
		var afuxuanK=document.querySelectorAll('.BTSXcli2li');
        var chooseDatasArr = [];
        for(var i=0;i<afuxuanK.length;i++){
        	if(afuxuanK[i].querySelectorAll('.BTSXcli2liI')[0].checked){
        		chooseDatasArr.push(afuxuanK[i].querySelectorAll('.BTSXcli2liT')[0].innerHTML);
        	}
        }
        var chooseDatas=chooseDatasArr.join(",");
    	window.location.href="/wankangyuan/sourceData/getSourceDatas?block=2&type=1&cs_id="+cs_id+"&searchId="+
		searchId+"&desc_asc="+desc_asc+"&likeSearch="+likeSearch
		+"&searchWord="+searchWord+"&chooseDatas="+chooseDatas+"&oldCondition="+oldCondition+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
	}
	
	$("#removeSourceDatas").click(function (){
		
		var idQuanXuan= $(" #isAll2").val();
        var ids3=$(" #ids2").val();
        
        if(idQuanXuan =="false"){
        	 if(ids3 == ""){
        		alert("请勾选待移出的选项！");
             	return;
             }
        }
       
       var cs_id=$('#source_Select').val();//采集源id
    	var searchId="${searchId}";//操作字段id
    	var searchWord=$(".BTSXcliGLK").val();//过滤条件
    	var desc_asc="${desc_asc}";//排序
    	var oldCondition=$("#oldCondition").html();//累加筛选条件
    	var searchFirstWord = $(".searchCt").val();
    	var chooseDatas = "${chooseDatas}";
    	var likeSearch = "${likeSearch}";
          
        var result = confirm("确认移出选中的数据源数据吗？");
		if(result == true){
			var cs_id = $("#source_Select").val();
            $.ajax({
            	url:"/wankangyuan/sourceData/removeSourceDatas",
            	type:"post",
            	data:{
            		type:1,
            		cs_id:cs_id,
            		ids:ids3,
            		isAll:idQuanXuan,
            		searchId:searchId,
            		searchWord:searchWord,
            		desc_asc:desc_asc,
            		oldCondition:oldCondition,
            		searchFirstWord:searchFirstWord,
            		chooseDatas:chooseDatas,
            		likeSearch:likeSearch
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			alert(data.message);
            			 window.location.href="/wankangyuan/sourceData/getSourceDatas?block=2&type=1&cs_id="+cs_id+"&searchId="+searchId+
           	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition+"&page="+page+"&strip=${rows}"+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();

            		}else{
            			alert(data.message);
            		}
            	},
            	error : function(){
            		alert("网络异常，请稍后重试！");
            	}
            });
		}else{
			return;
		}
});
	
	
	
	
	
	
	  	//将格式数据添加到项目
		$(".pro_addli").click(function (){
			
			var p_id = this.id;
			//var cs_id = $("#source_Select").val();
			var afuxuanK=document.querySelectorAll('.fuxuanK2');
	        var afuxuan=[];
	        for(var i=0;i<afuxuanK.length;i++){
	            afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
	        }
	        var sourceDataIds = [];
	        for(var i=0;i<afuxuanK.length;i++){
	        	if(afuxuan[i].checked){
	        		sourceDataIds.push(afuxuan[i].name);
	        	}
	        }
	        if(sourceDataIds == ""){
	        	alert("请勾选源数据！");
	        	return;
	        }
	        $.ajax({
	        	url:"/wankangyuan/projectFormatData/insert",
	        	type:"post",
				async:true,
	        	data:{
	        		p_id:p_id,
	        		sourceDataIds:sourceDataIds.join(","),
            		cs_id:cs_id
	        	},
	        	dataType:"json",
	        	success : function(data){
	        		if(data.result == true){
	        			alert(data.message);
	        		}else{
	        			alert(data.message);
	        		}
	        	},
	        	error : function(){
	        		alert("网络异常，请稍后重试！");
	        	}
	        });
	        alert("数据添加中，您可先进行其他操作！");
		});
	  	
	
    	//进入到详情页
    	function datainHref(sourceDataId){
    		var cs_id = $("#source_Select").val();
    		reset();
    		window.location.href="/wankangyuan/sourceData/getSourceDataById?cs_id="+cs_id+"&sourceDataId="+sourceDataId+"&type=1";
    	}
    	$('#box').paging({
            initPageNo: ${page}, // 初始页码
            totalPages: Math.ceil(${total}/${rows}), //总页数
            totalCount: '合计&nbsp;' + ${total} + '&nbsp;条数据', // 条目总数
            slideSpeed: 600, // 缓动速度。单位毫秒
            jump: true, //是否支持跳转
            callback: function(page) { // 回调函数
                
            	var user_id=${user.id};
        		var cs_id = $("#source_Select").val();
        		//之前选中的id
        		var ids_str = distinct_ids($("#ids").val());
        		//之前选中的id+当页选中的id
        		var ids=getids()+","+ids_str;
        		//是否全选
        		var is_all = isAll();
        		
                 if(page!=${page}){ 
                	window.location.href="/wankangyuan/sourceData/getSourceDatas1?type=1&cs_id="+cs_id+"&searchId="+searchId+
    	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition+"&page="+page+"&strip=${rows}&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
                } 
            }
        });  
    	
    	//去掉选过的id
    	function distinct_ids(ids_str){
    		var ids_arr=ids_str.split(",");
    		 var sourceDataIds = new Array();
    		var afuxuanK=document.querySelectorAll('.input_check');
    		for(var i = 1; i < afuxuanK.length; i++){
    			var index = ids_arr.indexOf(afuxuanK[i].value);
    			if (index > -1) {
    				ids_arr.splice(index, 1);
    			}
            }
    		return ids_arr.join(",")
    	}
    	//获取当前页选中的id
            function getids(){
            	/* var p_id = this.id;
    			var afuxuanK=document.querySelectorAll('.input_check');
    	        var afuxuan=[];
    	        var sourceDataIds = new Array();
    	        var ids="";
                for(var i = 0; i < afuxuanK.length; i++){
	                 if(afuxuanK[i].checked &&(afuxuanK[i].value!="on")){
	                	 sourceDataIds.push(afuxuanK[i].value);
	                 	//ids+=afuxuanK[i].value+",";
	                 }
                }
                ids=sourceDataIds.join(",");
                return ids; */
        	}
            $("#check0").click(function (){
            	/* var ids=getids();
            	if($("#check0").attr('checked')){
            		$("#isAll").val(true);	 
            	}else{
            		$("#isAll").val(false);
            	} */
            });
            
            function isAll(){
            	/* var isAll=true;
            	var afuxuanK=document.querySelectorAll('.input_check');
            	for(var i = 0; i < afuxuanK.length; i++){
	                 if(!afuxuanK[i].checked){
	                	 isAll=false;
	                 }
            	}
            	return isAll; */
            }
    </script>

</body>
</html>