
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
						<a href="/wankangyuan/sourceData/firstIn?type=${type123}&cs_id=${source.cs_id}"> <img
							src="/wankangyuan/static/img/back.png" height="20" width="20"
							alt="" class="backI" />
						</a>${sourceData[1]} <input id="cs_id" value="${source.cs_id }"
							style="display: none;" /> <input id="sourceDataId"
							value="${sourceData[0]}" style="display: none;" />
					</div>
					<div class="pro_menu pro_addK" style="display: none;">
						<div class="pro_addk">
							<div class="pro_addT">添加结点至项目</div>
							<div class="pro_addI"></div>
						</div>
					</div>

					<div class="app_expexport app_expexport_node"
						style="display: none;">导出结点</div>
					<div class="app_expexport app_expexport_type"
						style="display: none;">导出格式类型</div>
				</div>
			</div>

				<div class="pro_addul">
					<c:forEach items="${projects}" var="projectTemp">
						<div class="pro_addli" id="${projectTemp.p_id }">${projectTemp.p_name}</div>
					</c:forEach>
				</div>
			<div class="prodainm">
				<div class="prodainmL">
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
					<c:forEach items="${source.sourceFields}" var="sourceFieldTemp"
						varStatus="status">
						<div class="prodainmRz1">
							<div class="prodainmRz1L">${sourceFieldTemp.csf_name}</div>
							<div class="prodainmRz1R">${fn:substringAfter(sourceData[status.index+1], "_")}</div>
						</div>
					</c:forEach>
				</div>
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
		function dataNodeClick(formatNodeId, ft_id) {
			var cs_id = $('#cs_id').val();
			var sourceDataId = $("#sourceDataId").val();
			window.location.href = "/wankangyuan/formatNode/getFormatNodeById?cs_id="
					+ cs_id
					+ "&sourceDataId="
					+ sourceDataId
					+ "&type=1&ft_id="
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
									+ "&ft_id="
									+ ft_id
									+ "&formatNodeId=" + formatNodeId;

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
	</script>
</body>
</html>