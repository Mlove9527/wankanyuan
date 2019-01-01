<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
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
	window.onload = function() {
		project0();
		// project1();
		// pro_mine();
		// pro_dataLB();
		pro_data();
		pro_dataclick();
        data_dataclick2();
        //data_click_guanxi();
        data_dataclick();
	}
</script>
<body>
	<div class="Box">
		<div class="box">
			<div class="top">
				<h1>
					<img src="/wankangyuan/static/img/newlogo2.png" height="70"
						width="218" alt="" class="logo" />
				</h1>
				<a href="/wankangyuan/project/selectMyProject">
					<div class="topT">项目</div>
				</a> <a href="/wankangyuan/sourceData/firstIn?type=1">
					<div class="topT active">格式数据</div>
				</a> <a href="/wankangyuan/application/viewMine">
					<div class="topT">应用</div>
				</a>
				<div class="touxiangK">
					<a href="/wankangyuan/userInfo"> <img src="${user.headimg }"
						onerror='this.src="/wankangyuan/static/img/head.jpg"' }" alt=""
						class="touxiang" />
					</a>
					<div class="userbutK">
						<a href="/wankangyuan/userInfo">
							<div class="userbut">用户信息</div>
						</a> <a href="/wankangyuan/message/viewMessage">
							<div class="userbut">
								系统消息
								<c:if test="${systemMSG }">
									<img
										src="<%=request.getContextPath()%>/static/img/redpoint.png"
										height="11" width="11" alt="" class="redpoint2" />
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
							<img src="<%=request.getContextPath()%>/static/img/redpoint.png"
								height="11" width="11" alt="" class="redpoint" />
						</c:if>
					</div>
				</a>
			</div>
			<div class="top2">
				<div class="top2C">
					<div class="top2Ctl active">
						<a
							href="/wankangyuan/sourceData/firstIn?type=2&cs_id=${cs_id }">
							<img src="/wankangyuan/static/img/back.png" height="20"
							width="20" alt="" class="backI" />
						</a>${sourceData[1]} <input id="cs_id"
							value="${formatTypeFolders[0].cs_id }" style="display: none;" />
						<input id="ft_id" value="${data[0].ft_id }" style="display: none;" />
						<input id="formatNodeId" value="${formatNodeId}"
							style="display: none;" /> <input id="sourceDataId"
							value="${sourceDataId}" style="display: none;" />
					</div>
					<div class="app_expexport app_expexport_data">导出数据</div>
					<div class="app_expexport app_expexport_node">导出结点</div>
					<div class="app_expexport app_expexport_type">导出格式类型</div>
				</div>
			</div>
			<div class="prodainm">
				<div class="prodainmL">
					<div class="dataclLbk">
						<div class="dataclLb daclLb_add">添加数据节点</div>
						<div class="dataclLb daclLb_del">删除</div>
						<div class="dataclLb daclLb_mod">修改</div>
					</div>
					<div class="dataeditK">
						<div class="dataeditT">
							<div class="dataeditTx"></div>
						</div>
						<input id="type" class="type" value="" style="display: none;" />
						<div class="dataeditM">
							<div class="dataeditMt">名称</div>
							<textarea name="" id="dataNodeTextArea" class="dataeditTta"></textarea>
						</div>
						<div class="dataeditB">
							<input type="button" class="dataeditb" id="addDataNodeSubmit"
								value="提交" />
						</div>
					</div>

					<div class="PJliBK">
						<c:forEach items="${formatTypeFolders}" var="formatTypeTemp"
							varStatus="status">
							<div class="PJliB1">
								<div class="PJliB1L">
									<div class="fuxuanK4 fuxuanK41">
										<input type="checkbox" class="input_check"
											name="${formatTypeTemp.ft_id }"
											id="check1_${formatTypeTemp.ft_id }"> <label
											for="check1_${formatTypeTemp.ft_id }"></label>
									</div>
									<div class="PJliB1Lt">${formatTypeTemp.ft_name }</div>
									<div class="PJliBLi PJliBLi2"></div>
								</div>
								<div class="PJliBR">
									<c:forEach items="${formatTypeTemp.formatDataNodes}"
										var="formatDataNodeTemp" varStatus="status">
										<div class="PJliB2">
											<div class="PJliB2L">
												<div class="fuxuanK4 fuxuanK42">
													<input type="checkbox" class="input_check"
														value="${formatTypeTemp.ft_id }"
														name="${formatDataNodeTemp.key}"
														id="check1_${formatDataNodeTemp.key}"> <label
														for="check1_${formatDataNodeTemp.key}"></label>
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
					<c:forEach items="${source.sourceFields1}" var="sourceFieldTemp"
						varStatus="status">
						<div class="prodainmRz1">
							<div class="prodainmRz1L"  title="${sourceFieldTemp.description}">${sourceFieldTemp.csf_name}</div>
							<c:if test="${sourceFieldTemp.not_null==true}"><span class="redspan" style="color:red;float:left;">*</span></c:if>
							<div class="errname" style="display:none;">${sourceFieldTemp.csf_name}</div>
							<div class="errmsg" style="display:none;">${sourceFieldTemp.error_msg}</div>
							<%-- <c:if test="sourceFieldTemp.type=='文件'"></c:if> --%>
							<c:if test="${sourceFieldTemp.type=='文件' }">
								<div class="prodainmRfilek">
										<div class="prodainmRfile" id="${sourceFieldTemp.csf_id }">${fn:substringAfter(sourceData[status.index+1], "_")}</div>
										<input type="file" class="prodainmRip" id=${sourceFieldTemp.csf_id } value="${sourceData[status.index+1] }" style="display: none;">
										<a class="prodainmRib2" href="<%=path%>/export/downloadFile?sourceDataId=${sourceData[0]}&cs_id=${sourceFieldTemp.cs_id }&csf_id=${sourceFieldTemp.csf_id}">下载</a>
										<div class="prodainmRib" id="">修改</div>
									</div>
							</c:if>
							<c:if test="${sourceFieldTemp.type=='图片'}">
								<div class="prodainmRik">
											<img alt="" class="prodainmRi" 
												src="<%=path%>/export/getThumbnailImage?sourceDataId=${sourceData[0]}&cs_id=${sourceFieldTemp.cs_id}&csf_id=${sourceFieldTemp.csf_id}">
											<input type="file" class="prodainmRip" id=${sourceFieldTemp.csf_id } value="${sourceData[status.index+1] }" style="display: none;" >
											<a class="prodainmRib2" href="<%=path%>/export/downloadFile?sourceDataId=${sourceData[0]}&cs_id=${sourceFieldTemp.cs_id}&csf_id=${sourceFieldTemp.csf_id}">下载</a>
											<div class="prodainmRib" id="">修改</div>
										</div>
							</c:if>
							<c:if test="${sourceFieldTemp.type=='字符'}">
								<c:if test="${sourceFieldTemp.enumerated==true }">
										<c:if test="${sourceFieldTemp.emvalue!=null }">
											<select value="${sourceData[status.index+1] }">
												<c:forEach items="${sourceFieldTemp.emvalue}" var="emvalue">
													<option value="${emvalue}">${emvalue}</option>
												</c:forEach>
											</select>
											<%-- <input class="prodainmRz1R" type="text" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" /> --%>
										</c:if>
										<c:if test="${sourceFieldTemp.emvalue==null }">
												<input class="prodainmRz1R" type="text" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										</c:if>
									</c:if>
									<c:if test="${sourceFieldTemp.enumerated==false }">
										<%-- <c:if test="${sourceFieldTemp.emvalue==null }"> --%>
												<input class="prodainmRz1R" type="text" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										<%-- </c:if> --%>
									</c:if>
							</c:if>
							<c:if test="${sourceFieldTemp.type=='数值'}">
								<c:if test="${sourceFieldTemp.enumerated==true }">
											<c:if test="${sourceFieldTemp.emvalue!=null }">
												<select value="${sourceData[status.index+1] }">
													<c:forEach items="${sourceFieldTemp.emvalue}" var="emvalue">
														<option value="${emvalue}">${emvalue}</option>
													</c:forEach>
												</select>
												<%-- <input class="prodainmRz1R" type="number" id=${sourceFieldTemp.csf_id }
														value="${sourceData[status.index+1] }" /> --%>
											</c:if>
											<c:if test="${sourceFieldTemp.emvalue==null }">
													<input class="prodainmRz1R" type="number" id=${sourceFieldTemp.csf_id }
														value="${sourceData[status.index+1] }" />
											
											</c:if>
										</c:if>
										<c:if test="${sourceFieldTemp.enumerated==false }">
										<%-- <c:if test="${sourceFieldTemp.emvalue==null }"> --%>
												<input class="prodainmRz1R" type="number" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										<%-- </c:if> --%>
									</c:if>
							</c:if>
							<c:if test="${sourceFieldTemp.type=='日期'}">
								<input type="hidden" id="hidden_${sourceFieldTemp.csf_id }" value="${sourceData[status.index+1] }">
								<c:if test="${sourceFieldTemp.enumerated==true }">
											<c:if test="${sourceFieldTemp.emvalue!=null }">
												<select value="${sourceData[status.index+1] }">
													<c:forEach items="${sourceFieldTemp.emvalue}" var="emvalue">
														<option value="${emvalue}">${emvalue}</option>
													</c:forEach>
												</select>
												<%-- <input class="prodainmRz1R" type="date" id=${sourceFieldTemp.csf_id }
														value="${sourceData[status.index+1] }" /> --%>
											</c:if>
											<c:if test="${sourceFieldTemp.emvalue==null }">
													<input class="prodainmRz1R" type="datetime-local" step="01" id=${sourceFieldTemp.csf_id }
														value="${sourceData[status.index+1] }" />
											</c:if>
										</c:if>
										<c:if test="${sourceFieldTemp.enumerated==false }">
										<%-- <c:if test="${sourceFieldTemp.emvalue==null }"> --%>
												<input class="prodainmRz1R" type="datetime-local" step="01" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										<%-- </c:if> --%>
									</c:if>
							</c:if>
							<%-- <c:choose>
							
								<c:when test="${sourceFieldTemp.type=='文件' }">
									<div class="prodainmRfilek">
										<div class="prodainmRfile">${sourceData[status.index+1]}</div>
										<input type="file" class="prodainmRip" id=${sourceFieldTemp.csf_id } value="${sourceData[status.index+1] }" style="display: none;">
										<a class="prodainmRib2" href="<%=path%>/export/downloadFile?sourceDataId=${sourceData[0]}&cs_id=${sourceFieldTemp.cs_id }&csf_id=${sourceFieldTemp.csf_id}">下载</a>
										<div class="prodainmRib" id="">修改</div>
									</div>
								</c:when>
								<c:when test="${sourceFieldTemp.type=='图片'}">
									<div class="prodainmRik">
										<img alt="" class="prodainmRi" 
											src="<%=path%>/export/getThumbnailImage?sourceDataId=${sourceData[0]}&cs_id=${sourceFieldTemp.cs_id}&csf_id=${sourceFieldTemp.csf_id}">
										<input type="file" class="prodainmRip" id=${sourceFieldTemp.csf_id } value="${sourceData[status.index+1] }" style="display: none;" >
										<a class="prodainmRib2" href="<%=path%>/export/downloadFile?sourceDataId=${sourceData[0]}&cs_id=${sourceFieldTemp.cs_id}&csf_id=${sourceFieldTemp.csf_id}">下载</a>
										<div class="prodainmRib" id="">修改</div>
									</div>
								</c:when>
								<c:when test="${sourceFieldTemp.type=='字符'}">
									<c:if test="${sourceFieldTemp.enumerated==true }">
										<c:if test="${sourceFieldTemp.emvalue!=null }">
											<select>
												<c:forEach items="${sourceFieldTemp.emvalue}" var="emvalue">
													<option value="${emvalue}">${emvalue}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${sourceFieldTemp.emvalue==null }">
												<input class="prodainmRz1R" type="text" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										</c:if>
									</c:if>
									<c:if test="${sourceFieldTemp.enumerated==false }">
										<c:if test="${sourceFieldTemp.emvalue==null }">
												<input class="prodainmRz1R" type="text" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										</c:if>
									</c:if>
								</c:when>
								<c:when test="${sourceFieldTemp.type=='数值'}">
									<c:if test="${sourceFieldTemp.enumerated==true }">
											<c:if test="${sourceFieldTemp.emvalue!=null }">
												<select>
													<c:forEach items="${sourceFieldTemp.emvalue}" var="emvalue">
														<option value="${emvalue}">${emvalue}</option>
													</c:forEach>
												</select>
											</c:if>
											<c:if test="${sourceFieldTemp.emvalue==null }">
													<input class="prodainmRz1R" type="number" id=${sourceFieldTemp.csf_id }
														value="${sourceData[status.index+1] }" />
											
											</c:if>
										</c:if>
										<c:if test="${sourceFieldTemp.enumerated==false }">
										<c:if test="${sourceFieldTemp.emvalue==null }">
												<input class="prodainmRz1R" type="number" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										</c:if>
									</c:if>
									<input class="prodainmRz1R" type="number" id=${sourceFieldTemp.csf_id }
										value="${sourceData[status.index+1] }" />
								</c:when>
								<c:when test="${sourceFieldTemp.type=='日期'}">
									<c:if test="${sourceFieldTemp.enumerated==true }">
											<c:if test="${sourceFieldTemp.emvalue!=null }">
												<select>
													<c:forEach items="${sourceFieldTemp.emvalue}" var="emvalue">
														<option value="${emvalue}">${emvalue}</option>
													</c:forEach>
												</select>
											</c:if>
											<c:if test="${sourceFieldTemp.emvalue==null }">
													<input class="prodainmRz1R" type="date" id=${sourceFieldTemp.csf_id }
														value="${sourceData[status.index+1] }" />
											
											</c:if>
										</c:if>
										<c:if test="${sourceFieldTemp.enumerated==false }">
										<c:if test="${sourceFieldTemp.emvalue==null }">
												<input class="prodainmRz1R" type="date" id=${sourceFieldTemp.csf_id }
													value="${sourceData[status.index+1] }" />
										
										</c:if>
									</c:if>
									<input class="prodainmRz1R" type="date" id=${sourceFieldTemp.csf_id }
										value="${sourceData[status.index+1] }" />
								</c:when>
							</c:choose> --%>
						</div>
					</c:forEach>
					
					<div class="prodainmRb">保存</div>
				</div>
				<script type="text/javascript">
					var oprodainmR=document.querySelectorAll('.prodainmR')[0];
					console.log(oprodainmR);
					var ainput=oprodainmR.querySelectorAll('input');
					var ainput_datetime=[];
					//console.log(ainput);
					for(var i=0;i<ainput.length;i++){
						//console.log(ainput[i].type);
						if(ainput[i].type=='datetime-local'){
							ainput_datetime.push(ainput[i]);
						}
					}
					console.log(ainput_datetime);
					for(var i=0;i<ainput_datetime.length;i++){
						var oid='hidden_'+ainput_datetime[i].id;
						var ohidden=document.getElementById(oid);
						console.log(ohidden);
						console.log(ohidden.value);
						var ovalue=ohidden.value;
						if(ovalue.length==10){
							ovalue=ovalue+'T'+'00:00:00';
							console.log(ovalue);
							ainput_datetime[i].value=ovalue;
						}else if(ovalue.length==19){
							ovalue=ovalue.replace(' ','T');
							console.log(ovalue);
							ainput_datetime[i].value=ovalue;
						}
					}
					
				</script>
			</div>

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

	<script type="text/javascript"
		src="/wankangyuan/static/js/jquery.min.js"></script>

	<script type="text/javascript">
	$(function(){
		});
	//文件和图片上传input的accept设置
	var aprodainmRip=document.querySelectorAll('prodainmRip');
	for(var i=0;i<aprodainmRip.length;i++){
		console.log(aprodainmRip[i]);
		aprodainmRip[i].accept=varaccept;
	}
	
		function dataNodeClick(formatNodeId, ft_id) {
			var cs_id = $('#cs_id').val();
			var sourceDataId = $("#sourceDataId").val();
			window.location.href = "/wankangyuan/formatNode/getFormatNodeById?cs_id="
					+ cs_id
					+ "&sourceDataId="
					+ sourceDataId
					+ "&type=2&ft_id="
					+ ft_id
					+ "&formatNodeId="
					+ formatNodeId;
		}
		$(".app_expexport_node")
				.click(
						function() {
							var afuxuanK = document
									.querySelectorAll('.fuxuanK42');
							var afuxuan = [];
							for (var i = 0; i < afuxuanK.length; i++) {
								afuxuan.push(afuxuanK[i]
										.querySelectorAll('.input_check')[0]);
							}
							var ft_ids = [];
							var formatNodeIds = [];
							for (var i = 0; i < afuxuanK.length; i++) {
								if (afuxuan[i].checked) {
									formatNodeIds.push(afuxuan[i].name);
									ft_ids.push(afuxuan[i].value);
								}
							}
							if (ft_ids.length > 1 || formatNodeIds.length > 1) {
								alert("最多选择一个结点！");
								return;
							}
							if (ft_ids == "" || formatNodeIds == "") {
								alert("请选择待导出数据的结点！");
								return;
							}
							var cs_id = $('#cs_id').val();
							var ft_id = ft_ids.join(",");
							var formatNodeId = formatNodeIds.join(",");
							window.location.href = "/wankangyuan/export/formatNode?cs_id="
									+ cs_id
									+ "&ft_ids="
									+ ft_id
									+ "&formatNodeIds=" + formatNodeId;
						});
		$(".app_expexport_type")
				.click(
						function() {
							var afuxuanK = document
									.querySelectorAll('.fuxuanK41');
							var afuxuan = [];
							for (var i = 0; i < afuxuanK.length; i++) {
								afuxuan.push(afuxuanK[i]
										.querySelectorAll('.input_check')[0]);
							}
							var ft_ids = [];
							for (var i = 0; i < afuxuanK.length; i++) {
								if (afuxuan[i].checked) {
									ft_ids.push(afuxuan[i].name);
								}
							}
							if (ft_ids.length > 1) {
								alert("最多选择一种格式数据类型！");
								return;
							}
							if (ft_ids == "") {
								alert("请选择待导出数据的格式类型！");
								return;
							}
							var cs_id = $('#cs_id').val();
							var sourceDataId = $("#sourceDataId").val();
							var ft_id = ft_ids.join(",");
							window.location.href = "/wankangyuan/export/formatType?cs_id="
									+ cs_id
									+ "&sourceDataId="
									+ sourceDataId
									+ "&ft_id=" + ft_id;
						});

		//图片修改
		$('.prodainmRib').each(function(index) {
			$('.prodainmRip')[index].addEventListener("change",function () {
				for(var i=0; i<$('.prodainmRip').length; i++) {
					if($('.prodainmRip')[index].id==$('.prodainmRfile')[i].id)
					{
						$('.prodainmRfile').eq(i).text($('.prodainmRip')[index].value+"");
					}
				}
			});

			$(this).click(function() {
				$('.prodainmRip')[index].click();
			})
		});
		
		/* //保存
		$(".prodainmRb")
		.click(function() {
			var fileadd = document.querySelectorAll('.prodainmRip');
			var otheradd = document.querySelectorAll('.prodainmRz1R');
	        var form_data = new FormData();
	        var cs_id = $('#cs_id').val();
			var sourceDataId = $("#sourceDataId").val();
	    	form_data.append("cs_id",cs_id);
	    	form_data.append("sourceDataId",sourceDataId);
	    	for(var i=0;i<fileadd.length;i++){
	        	if(fileadd[i].type=="file"){
	        		//文件
	        		var fileObj = fileadd[i].files[0]; // 获取文件对象
	        		form_data.append(fileadd[i].id, fileObj);
	        	}else{
	        		form_data.append(fileadd[i].id, otheradd[i].value);
	        	}
	        }
	    	for(var i=0;i<otheradd.length;i++){
	        	if(otheradd[i].type=="file"){
	        		//文件
	        		var fileObj = otheradd[i].files[0]; // 获取文件对象
	        		form_data.append(otheradd[i].id, fileObj);
	        	}else{
	        		form_data.append(otheradd[i].id, otheradd[i].value);
	        	}
	        }
	        $.ajax({
	        	url:"/wankangyuan/sourceData/updateSourceAndFile",
	        	type:"post",
	        	data:form_data,
	        	processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
	            contentType : false,  //必须false才会自动加上正确的Content-Type 
	        	success : function(data){
	        		if(data.result == true){
	        			alert(data.message);
	        			// 刷新页面
	        			//window.location.href="/wankangyuan/sourceData/getSourceDatas?type=2&cs_id="+cs_id;       			                    
	        		}else{
	        			alert(data.message);
	        		}
	        	},
	        	error : function(){
	        		alert("联网失败");
	        	}
	        });
    		
		}); */
        //保存
        $(".prodainmRb")
        .click(function() {
            var aerrname=document.querySelectorAll('.errname');
            var aerrmsg=document.querySelectorAll('.errmsg');
            //console.log(aerrname[0].innerHTML);
            var btpd=true;
            var btpd_id=[];
            var aprodainmRz1=document.querySelectorAll('.prodainmRz1');
            for(var i=0;i<aprodainmRz1.length;i++){
                var ospan=aprodainmRz1[i].querySelectorAll('.redspan')[0];
                if(ospan){
                    var oprodainmRip=aprodainmRz1[i].querySelectorAll('.prodainmRip')[0];
                    var oprodainmRz1R=aprodainmRz1[i].querySelectorAll('.prodainmRz1R')[0];
                    if(oprodainmRip){
                        if(oprodainmRip.value==''&&oprodainmRip.defaultValue==''){
                            btpd=false;
                            btpd_id.push(i);
                        }
                    }else if(oprodainmRz1R){
                        if(oprodainmRz1R.value==''){
                            btpd=false;
                            btpd_id.push(i);
                        }
                    }
                    
                }
            }
            if(btpd){
                var fileadd = document.querySelectorAll('.prodainmRip');
                var otheradd = document.querySelectorAll('.prodainmRz1R');
                var form_data = new FormData();
                var cs_id = $('#cs_id').val();
                var sourceDataId = $("#sourceDataId").val();
                form_data.append("cs_id",cs_id);
                form_data.append("sourceDataId",sourceDataId);
                for(var i=0;i<fileadd.length;i++){
                    if(fileadd[i].type=="file"){
                        //文件
                        var fileObj = fileadd[i].files[0]; // 获取文件对象
                        form_data.append(fileadd[i].id, fileObj);
                    }else{
                        form_data.append(fileadd[i].id, otheradd[i].value);
                    }
                }
                for(var i=0;i<otheradd.length;i++){
                    if(otheradd[i].type=="file"){
                        //文件
                        var fileObj = otheradd[i].files[0]; // 获取文件对象
                        form_data.append(otheradd[i].id, fileObj);
                    }else{
                        form_data.append(otheradd[i].id, otheradd[i].value);
                    }
                }
                $.ajax({
                    url:"/wankangyuan/sourceData/updateSourceAndFile",
                    type:"post",
                    data:form_data,
                    processData : false,  //必须false才会避开jQuery对 formdata 的默认处理   
                    contentType : false,  //必须false才会自动加上正确的Content-Type 
                    success : function(data){
                        if(data.result == true){
                            alert(data.message);
                            // 刷新页面
                            //window.location.href="/wankangyuan/sourceData/getSourceDatas?type=2&cs_id="+cs_id;                                    
                        }else{
                            alert(data.message);
                        }
                    },
                    error : function(){
                        alert("联网失败");
                    }
                });
            }else{
                console.log(btpd_id);
                //for(var i=0;i<btpd_id.length;i++){
                    var j=btpd_id[0];
                    alert(aerrname[j].innerHTML+'项错误信息：'+aerrmsg[j].innerHTML);
                //}
            }
        });
		//移除结点
    	$(".daclLb_del").click(function (){
    		
    		var afuxuanK=document.querySelectorAll('.fuxuanK42');
            var afuxuan=[];
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
            }
            var ids = [];
            for(var i=0;i<afuxuanK.length;i++){
            	if(afuxuan[i].checked){
            		ids.push(afuxuan[i].name);
            	}
            }
            if(ids == ""){
            	alert("请勾选待删除的结点！");
            	return;
            }else if(ids.length > 1){
            	alert("最多选择一个结点！")
            }else{
            	var con1=confirm("确定删除选中项吗？");
                if(con1){
              	var cs_id = $("#cs_id").val();
        		var ft_id = $("#ft_id").val();
        		var formatNodeId = $("#formatNodeId").val();
        		var sourceDataId = $("#sourceDataId").val();
            	$.ajax({
        			url:"/wankangyuan/formatNode/deleteFormatNode",
        			type:"post",
        			data:{
        				cs_id:cs_id,
        				formatNodeId:ids.join(",")
        			},
        			dataType:"json",
        			success : function(data){
        				if(data.result == true){
        					alert(data.message);
        					window.location.href="/wankangyuan/sourceData/getSourceDataById?cs_id="+cs_id+"&sourceDataId="+sourceDataId+"&type=2"
        				}else{
        					alert(data.message);
        				}
        			},
        			error : function(){
        				alert("网络异常，请稍后重试！");
        			}
        		});	
                }else{
                    //点击取消的动作
                }
            }
    	});
    	//新增节点
		$("#addDataNodeSubmit").click(function (){
			var cs_id = $('#cs_id').val();
			console.log(cs_id);
			var ft_id = $('#ft_id').val();
			var nodeName = $("#dataNodeTextArea").val();
			var sourceDataId = $("#sourceDataId").val();
			var formatNodeId = $("#formatNodeId").val();
			var type = $("#type").val();
			if(type == "edit"){
	    		var afuxuanK=document.querySelectorAll('.fuxuanK42');
	    		
	            var afuxuan=[];
	            for(var i=0;i<afuxuanK.length;i++){
	                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
	            }
	            var formatNodeIds = [];
	            var ft_ids = [];
	            for(var i=0;i<afuxuanK.length;i++){
	            	if(afuxuan[i].checked){
	            		formatNodeIds.push(afuxuan[i].name);
	            		ft_ids.push(afuxuan[i].value);
	            	}
	            }
	            $.ajax({
	    			url:"/wankangyuan/formatNode/updateFormatNode",
	    			type:"post",
	    			data:{
	    				cs_id:cs_id,
	    				nodeName:nodeName,
	    				ft_id:ft_ids.join(","),
	    				sourceDataId:sourceDataId,
	    				formatNodeId:formatNodeIds.join(",")
	    			},
	    			dataType:"json",
	    			success : function(data){
	    				if(data.result == true){
	    					alert(data.message);
	    					window.location.href="/wankangyuan/sourceData/getSourceDataById?cs_id="+cs_id+"&sourceDataId="+sourceDataId+"&type=2"
	    				}else{
	    					alert(data.message);
	    				}
	    			},
	    			error : function(){
	    				alert("网络异常，请稍后重试！");
	    			}	
	    		});   
			}else if(type == "add"){
	    		var afuxuanK=document.querySelectorAll('.fuxuanK41');
	            var afuxuan=[];
	            for(var i=0;i<afuxuanK.length;i++){
	                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
	            }
	            var ids = [];
	            for(var i=0;i<afuxuanK.length;i++){
	            	if(afuxuan[i].checked){
	            		ids.push(afuxuan[i].name);
	            	}
	            }
	    		$.ajax({
	    			url:"/wankangyuan/formatNode/insertFormatNode",
	    			type:"post",
	    			data:{
	    				cs_id:cs_id,
	    				nodeName:nodeName,
	    				sourceDataId:sourceDataId,
	    				ft_id:ids.join(",")
	    			},
	    			dataType:"json",
	    			success : function(data){
	    				if(data.result == true){
	    					alert(data.message);
	    					/* alert(ft_id); */
	    					/* window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
	    	    				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId; */
	    					window.location.href="/wankangyuan/sourceData/getSourceDataById?cs_id="+cs_id+"&sourceDataId="+sourceDataId+"&type=2"
	    				}else{
	    					alert(data.message);
	    				}
	    			},
	    			error : function(){
	    				alert("网络异常，请稍后重试！");
	    			}
	    		});  
			}
		});
		//导出数据
    	$(".app_expexport_data").click(function (){

            var cs_id = $("#cs_id").val();
    		var ft_id = $("#ft_id").val();
    		var formatNodeId = $("#formatNodeId").val();
    		var sourceDataId = $("#sourceDataId").val();
    		var afuxuanK=document.querySelectorAll('.fx4');
            var afuxuan=[];
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
            }
            var formatDataIds = [];
            for(var i=0;i<afuxuanK.length;i++){
            	if(afuxuan[i].checked){
            		formatDataIds.push(afuxuan[i].name);
            	}
            }
            if(formatDataIds == ""){
          	   alert("请选择待导出数据！");
          	   return;
             }
              //刷新页面
            window.location.href="/wankangyuan/export/formatData?cs_id="
            				+cs_id+"&ft_id="+ft_id+"&formatDataIds="+formatDataIds;
           	
    	});
	</script>
</body>
</html>