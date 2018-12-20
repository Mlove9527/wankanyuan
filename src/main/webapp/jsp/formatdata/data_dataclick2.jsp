
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
							href="/wankangyuan/sourceData/getSourceDataById?cs_id=${formatTypeFolders[0].cs_id}&sourceDataId=${sourceDataId}&type=2">
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


				<div class="prodaclmR">
					<div class="prodaclmRz">
						<div class="pdclmRz_ul"></div>
						<div class="prodaclmRzT">

							<div class="prodaclmRzTt prodaclmRzTtmz">名称</div>
							<div class="prodaclmRzTt prodaclmRzTtnr">内容</div>
						</div>
						<div class="prodaclmRzB">
							<c:forEach items="${metaDatas}" var="metaDataListTemp"><!-- 判断是否是必填，接着判断是否枚举 -->
								<c:if test="${metaDataListTemp.not_null==true }">
									<div class="prodaclmRzBz">
										<div class="prodaclmRzBzt prodaclmRzTtmz" title="${metaDataListTemp.description }">${metaDataListTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
											<c:if test="${metaDataListTemp.type=='字符' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="text" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="text" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='数值' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="number" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="number" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='日期' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="datetime-local" step="01" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="datetime-local" step="01" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='图片' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="file" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="file" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='文件' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="file" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="file" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
									</div>
								
								</c:if>
								<c:if test="${metaDataListTemp.not_null==false }">
									<div class="prodaclmRzBz">
										<div class="prodaclmRzBzt prodaclmRzTtmz" title="${metaDataListTemp.description }">${metaDataListTemp.ff_name }</div>
											<c:if test="${metaDataListTemp.type=='字符' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="text" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="text" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='数值' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="number" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="number" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='日期' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="datetime-local" step="01" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="datetime-local" step="01" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='图片' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="file" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="file" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
											<c:if test="${metaDataListTemp.type=='文件' }">
												<div class="prodaclmRzBzt prodaclmRzTtmz">
													<div class="ff_name" style="display:none;">${metaDataListTemp.ff_name}</div>
													<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
													<c:if test="${metaDataListTemp.enumerated==true }">
														<c:if test="${metaDataListTemp.emvalue !=null}">
															<select class="meta_input" id="${metaDataListTemp.ff_id }">
																<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
																<option  value="${emvalue}">${emvalue}</option>
																</c:forEach>
															</select>
														</c:if>
														<c:if test="${metaDataListTemp.emvalue ==null}">
															<input class="meta_input" type="file" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
														</c:if>
													</c:if>
													<c:if test="${metaDataListTemp.enumerated==false }">
														<input class="meta_input" id="${metaDataListTemp.ff_id }" type="file" value="${metaDataListTemp.mete }" />
													</c:if>
													<div class="pdclmRz_li pdclmRz_edit"
													onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
												</div>
											</c:if>
									</div>
									<%-- <div class="prodaclmRzBz">
										<div class="prodaclmRzBzt prodaclmRzTtmz" title="${metaDataListTemp.description }">${metaDataListTemp.ff_name }</div>
										<div class="prodaclmRzBzt prodaclmRzTtmz">
											<div class="error_msg" style="display:none;">${metaDataListTemp.error_msg}</div>
											<c:if test="${metaDataListTemp.enumerated==true }">
												<c:if test="${metaDataListTemp.emvalue !=null}">
													<select class="meta_input" id="${metaDataListTemp.ff_id }">
														<c:forEach items="${metaDataListTemp.emvalue }" var="emvalue">
														<option  value="${emvalue}">${emvalue}</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${metaDataListTemp.emvalue ==null}">
													<input class="meta_input" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
												</c:if>
											</c:if>
											<c:if test="${metaDataListTemp.enumerated==false }">
												<input class="meta_input" id="${metaDataListTemp.ff_id }" value="${metaDataListTemp.mete }" />
											</c:if>
											<div class="pdclmRz_li pdclmRz_edit"
											onclick="meta_input_submit('${metaDataListTemp.ff_id }')">保存</div>
										</div>
									</div> --%>
								</c:if>
								<%-- <c:if test="${metaDataListTemp[7]=='是' }">
									<div class="prodaclmRzBz">
										<div class="prodaclmRzBzt prodaclmRzTtmz" title="${metaDataListTemp[3] }">${metaDataListTemp[1] }<span style="color: red;">*</span></div>
										<div class="prodaclmRzBzt prodaclmRzTtmz">
										<c:if test="${metaDataListTemp[6] =='是'}"><!-- 判断是否枚举，是的话则将枚举值放到option里 -->
										<input type="hidden" class="meta_input" id="meta${metaDataListTemp[0] +2}"
												value="${metaDataListTemp[4] }" />
												<select onclick="strToarr('#'meta${metaDataListTemp[0] +2})">
													<option>请选择</option>
												</select>
										
											
										</c:if>
										<c:if test="${metaDataListTemp[6] =='否'}">
										
											<input class="meta_input" id="${metaDataListTemp[0] }"
												value="${metaDataListTemp[2] }" />
										</c:if>
										</div>
										<input type="hidden" id="${metaDataListTemp[0] +1}" value="${metaDataListTemp[5] }"><!-- 错误提示信息 -->
										<div class="pdclmRz_li pdclmRz_edit"
											onclick="meta_input_submit('${metaDataListTemp[0] }')">保存</div>
	
									</div>
								</c:if>
								<c:if test="${metaDataListTemp[7]=='否' }">
									<div class="prodaclmRzBz">
										<div class="prodaclmRzBzt prodaclmRzTtmz" title="${metaDataListTemp[3] }">${metaDataListTemp[1] }</div>
										<div class="prodaclmRzBzt prodaclmRzTtmz">
										<c:if test="${metaDataListTemp[6] =='是'}"><!-- 判断是否枚举，是的话则将枚举值放到option里 -->
											<input class="meta_input" id="${metaDataListTemp[0] }"
												value="${metaDataListTemp[4] }" />
										</c:if>
										<c:if test="${metaDataListTemp[6] =='否'}">
										
											<input class="meta_input" id="${metaDataListTemp[0] }"
												value="${metaDataListTemp[2] }" />
										</c:if>
										</div>
										<input type="hidden" id="${metaDataListTemp[0] +1}" value="${metaDataListTemp[5] }"><!-- 错误提示信息 -->
										<div class="pdclmRz_li pdclmRz_edit"
											onclick="meta_input_submit('${metaDataListTemp[0] }')">保存</div>
	
									</div>
								</c:if> --%>
							</c:forEach>
						</div>
					</div>
					<br>
					<div class="prodaclmRz2">
						<div class="prodaclmRsxK">
							<div class="prodaclmRsx">
								<div class="prodaclmRsxT">筛选</div>
								<img src="/wankangyuan/static/img/sanjiao_blue.png" alt=""
									class="prodaclmRsxI" />
							</div>

							<!-- <div class="prodaclmRss" style="display: none;">
								<div class="prodaclmRssC">
									<img src="/wankangyuan/static/img/search.png" alt=""
										class="searchCi" /> <input type="text" class="searchCt"
										placeholder="搜索项目" />
								</div>
							</div> -->

							<div class="prodaclmRsB">
								<div class="prodaclmRsb clmRsb_inport">导入</div>
								<div class="prodaclmRsb clmRsb_remove">移除</div>
								<div class="prodaclmRsb clmRsb_modify">修改</div>
								<div class="prodaclmRsb clmRsb_add">添加</div>
							</div>

							<div class="search" style="margin-top:0px;">
								<div class="searchC">
									<img src="/wankangyuan/static/img/search.png" alt=""
										class="searchCi" /> <input type="text" class="searchCt"
										placeholder="搜索数据" value="${searchFirstWordNode}" />
								</div>
							</div>
						</div>

						<div class="shaixuanZK">
							<div class="shaixuanZKC">
								<c:forEach items="${data}" var="dataTemp">
									<div class="shaixuanZKli">
										<div class="shaixuanZKliI active"></div>
										<div class="shaixuanZKliT">${dataTemp.ff_name }</div>
									</div>
								</c:forEach>
							</div>
						</div>

						<div class="prodaclmRsjK">
						
						<input type="hidden" id="ids" value="${ids}" />
					<input type="hidden" id="isAll" value="${isAll}" />
				<input type="hidden" id="ids2" value="${allids}" />
				<input type="hidden" id="isAll2" value="${isAll1 }" />
							<table class="PJul" >
                        <tr class="PJList">
                            <td>
                                <div class="quanxuanK fxK1">
                                    <input type="checkbox" class="input_check" id="check4_0">
                                    <label for="check4_0"></label>
                                </div>
                                </td>
                                <c:forEach items="${data}" var="dataTemp">
                                    <td><div class="prodaclmRzTt2" id="${dataTemp.ff_id}" title="${dataTemp.description}">${dataTemp.ff_name }</div></td>
                                </c:forEach>
                            </tr>

                                <c:forEach items="${dataDatas}" var="dataDataTemp">
                                    <tr class="PJli">
                                        <c:forEach items="${dataDataTemp}" var="dataDataTempTemp"
                                            varStatus="status">
                                            <c:if test="${status.index == 0 }">
                                            <td>
                                                <div class="fuxuanK5 fxK1 fx4">
                                                    <input type="checkbox" class="input_check"
                                                        name="${dataDataTempTemp}" id="check4_${dataDataTempTemp}">
                                                    <label for="check4_${dataDataTempTemp}"></label>
                                                </div>
                                              </td>
                                            </c:if>
                                            <c:if test="${status.index != 0 }">
                                                <%-- <div class="prodaclmRzTt3 prodaclmRzTtsj3">${dataDataTempTemp}</div>
                                             --%>
                                                <td><div class="prodaclmRzTt3">
                                                    <span>${dataDataTempTemp}</span>
                                                </div></td>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </table>
								<div class="pageK" id="box"></div>
								
							</div>
							<div class="BTSX2">
                            <div class="BTSXc">
                                <div class="BTSXcli">
                                    <div class="BTSXcliT">排序：</div>
                                    <img src="/wankangyuan/static/img/sort_up.png" alt=""
                                        class="BTSXcliI" /> <img
                                        src="/wankangyuan/static/img/sort_down.png" alt=""
                                        class="BTSXcliI" /> <input type="text" class="BTSXcliIpd"
                                        style="display: none;" />
                                </div>
                                <div class="BTSXcli">
                                    <div class="BTSXcliT">过滤：</div>
                                    <input type="text" class="BTSXcliGLK" />
                                    <button id="guolv">过滤</button>
                                </div>
                                <div class="BTSXcli">
                                    <div class="BTSXcliT">值筛选：</div>
                                </div>
                                <div class="BTSXcli2">
                                    <div class="BTSXcli2li">
                                        <input type="checkbox" class="BTSXcli2liI" />
                                        <div class="BTSXcli2liT">空值</div>
                                    </div>
                                </div>
                                <div class="BTSXcli3">
                                    <div class="BTSXcli3BT BTSXcli3BTent" onclick="shaixuan(1)">筛选</div>
                                    <div class="BTSXcli3BT BTSXcli3BTent" onclick="chongzhi()">重置</div>
                                </div>
                            </div>
                        </div>
						</div>
						
					</div>
					<div class="clmReditK">
						<div class="clmReditT">
							<div class="clmReditTt">添加/修改单个数据</div>
							<div class="clmReditTx"></div>
						</div>
						<div class="clmReditM">
							<c:forEach items="${data}" var="dataTemp">
								<c:if test="${dataTemp.not_null==true }"><!-- 是否是必填 -->
									<c:if test="${dataTemp.type=='字符' }">
										<c:if test="${dataTemp.enumerated==true }"><!-- 是否枚举 -->
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<c:if test="${dataTemp.emvalue !=null}">
													<select class="clmReditMzp" id="${dataTemp.ff_id}">
														<c:forEach items="${dataTemp.emvalue }" var="emvaluess">
															<option value="${emvaluess }">${emvaluess }</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${dataTemp.emvalue ==null}">
													<input type="text" class="clmReditMzp" id="${dataTemp.ff_id }" />
												</c:if>
											</div>
											<input type="hidden" value="${ dataTemp.emvalue}"><!-- 枚举值的保存地方 -->
										</c:if>
										<c:if test="${dataTemp.enumerated==false }">
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<input type="text" class="clmReditMzp" id="${dataTemp.ff_id }" />
											</div>
										</c:if>
									</c:if>
									<c:if test="${dataTemp.type=='数值' }">
										<c:if test="${dataTemp.enumerated==true }"><!-- 是否枚举 -->
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<c:if test="${dataTemp.emvalue !=null}">
													<select class="clmReditMzp" id="${dataTemp.ff_id}">
														<c:forEach items="${dataTemp.emvalue }" var="emvaluess">
															<option value="${emvaluess }">${emvaluess }</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${dataTemp.emvalue ==null}">
													<input type="number" class="clmReditMzp" id="${dataTemp.ff_id }" />
												</c:if>
											</div>
											<input type="hidden" value="${ dataTemp.emvalue}"><!-- 枚举值的保存地方 -->
										</c:if>
										<c:if test="${dataTemp.enumerated==false }">
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<input type="number" class="clmReditMzp" id="${dataTemp.ff_id }" />
											</div>
										</c:if>
									</c:if>
									<c:if test="${dataTemp.type=='日期' }">
										<c:if test="${dataTemp.enumerated==true }"><!-- 是否枚举 -->
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<c:if test="${dataTemp.emvalue !=null}">
													<select class="clmReditMzp" id="${dataTemp.ff_id}">
														<c:forEach items="${dataTemp.emvalue }" var="emvaluess">
															<option value="${emvaluess }">${emvaluess }</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${dataTemp.emvalue ==null}">
													<input type="datetime-local" step="01" class="clmReditMzp" id="${dataTemp.ff_id }" />
												</c:if>
											</div>
											<input type="hidden" value="${ dataTemp.emvalue}"><!-- 枚举值的保存地方 -->
										</c:if>
										<c:if test="${dataTemp.enumerated==false }">
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<input type="number" class="clmReditMzp" id="${dataTemp.ff_id }" />
											</div>
										</c:if>
									</c:if>
									<c:if test="${dataTemp.type=='文件' }">
										<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<input type="file" class="clmReditMzp" id="${dataTemp.ff_id }" />
										</div>
									</c:if>
									<c:if test="${dataTemp.type=='图片'}">
										<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }<span class="redstar" style="color: red;">*</span></div>
												<%-- <input type="file" class="clmReditMzp" id="${dataTemp.ff_id }" />
													<img alt="" class="prodainmRi" 
														src="<%=path%>/export/getThumbnailImage"> --%>
										</div>
									</c:if>
								</c:if>
								<c:if test="${dataTemp.not_null==false }"><!-- 是否是必填 -->
									<c:if test="${dataTemp.type=='字符' }">
										<c:if test="${dataTemp.enumerated==true }"><!-- 是否枚举 -->
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<c:if test="${dataTemp.emvalue !=null}">
													<select class="clmReditMzp" id="${dataTemp.ff_id}">
														<c:forEach items="${dataTemp.emvalue }" var="emvaluess">
															<option value="${emvaluess }">${emvaluess }</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${dataTemp.emvalue ==null}">
													<input type="text" class="clmReditMzp" id="${dataTemp.ff_id }" />
												</c:if>
											</div>
											<input type="hidden" value="${ dataTemp.emvalue}"><!-- 枚举值的保存地方 -->
										</c:if>
										<c:if test="${dataTemp.enumerated==false }">
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<input type="text" class="clmReditMzp" id="${dataTemp.ff_id }" />
											</div>
										</c:if>
									</c:if>
									<c:if test="${dataTemp.type=='数值' }">
										<c:if test="${dataTemp.enumerated==true }"><!-- 是否枚举 -->
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<c:if test="${dataTemp.emvalue !=null}">
													<select class="clmReditMzp" id="${dataTemp.ff_id}">
														<c:forEach items="${dataTemp.emvalue }" var="emvaluess">
															<option value="${emvaluess }">${emvaluess }</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${dataTemp.emvalue ==null}">
													<input type="number" class="clmReditMzp" id="${dataTemp.ff_id }" />
												</c:if>
											</div>
											<input type="hidden" value="${ dataTemp.emvalue}"><!-- 枚举值的保存地方 -->
										</c:if>
										<c:if test="${dataTemp.enumerated==false }">
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<input type="number" class="clmReditMzp" id="${dataTemp.ff_id }" />
											</div>
										</c:if>
									</c:if>
									<c:if test="${dataTemp.type=='日期' }">
										<c:if test="${dataTemp.enumerated==true }"><!-- 是否枚举 -->
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<c:if test="${dataTemp.emvalue !=null}">
													<select class="clmReditMzp" id="${dataTemp.ff_id}">
														<c:forEach items="${dataTemp.emvalue }" var="emvaluess">
															<option value="${emvaluess }">${emvaluess }</option>
														</c:forEach>
													</select>
												</c:if>
												<c:if test="${dataTemp.emvalue ==null}">
													<input type="datetime-local" step="01" class="clmReditMzp" id="${dataTemp.ff_id }" />
												</c:if>
											</div>
											<input type="hidden" value="${ dataTemp.emvalue}"><!-- 枚举值的保存地方 -->
										</c:if>
										<c:if test="${dataTemp.enumerated==false }">
											<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<input type="datetime-local" step="01" class="clmReditMzp" id="${dataTemp.ff_id }" />
											</div>
										</c:if>
									</c:if>
									<c:if test="${dataTemp.type=='文件' }">
										<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<input type="file" class="clmReditMzp" id="${dataTemp.ff_id }" />
										</div>
									</c:if>
									<c:if test="${dataTemp.type=='图片'}">
										<div class="clmReditMz">
												<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
												<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
												<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
												<%-- <input type="file" class="clmReditMzp" id="${dataTemp.ff_id }" />
													<img alt="" class="prodainmRi" 
														src="<%=path%>/export/getThumbnailImage"> --%>
										</div>
									</c:if>
									<%-- <c:if test="${dataTemp.enumerated==true }"><!-- 是否枚举 -->
										<div class="clmReditMz">
											<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
											<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
											<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
											<c:if test="${dataTemp.emvalue !=null}">
												<select  class="clmReditMzp" id="${dataTemp.ff_id}">
													<c:forEach items="${dataTemp.emvalue }" var="emvaluess">
														<option value="${emvaluess }">${emvaluess }</option>
													</c:forEach>
												</select>
											</c:if>
											<c:if test="${dataTemp.emvalue ==null}">
												<input type="text" class="clmReditMzp" id="${dataTemp.ff_id }" />
											</c:if>
										</div>
										<input type="hidden" value="${ dataTemp.emvalue}"><!-- 枚举值的保存地方 -->
									</c:if>
									<c:if test="${dataTemp.enumerated==false }">
										<div class="clmReditMz">
											<div class="ff_name" style="display:none;">${dataTemp.ff_name}</div>
											<div class="error_msg" style="display:none;">${dataTemp.error_msg}</div>
											<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
											<input type="text" class="clmReditMzp" id="${dataTemp.ff_id }" />
										</div>
									</c:if> --%>
								</c:if>
							
							<%-- 
								<div class="clmReditMz">
									<div class="clmReditMzt" title="${dataTemp.description}">${dataTemp.ff_name }</div>
									<input type="text" class="clmReditMzp" id="${dataTemp.ff_id }" />
								</div> --%>
							</c:forEach>
						</div>
						<div>
							<input type="button" class="dataeditb" id="addFormatDataSubmit"
								value="提交" />
						</div>
					</div>

					<div class="clmRinportK">
						<div class="inportT">
							<div class="inportTt">导入数据</div>
							<div class="inportTx"></div>
						</div>
						<div class="inportM">
							<div class="inportMt">请把相关数据按照分类准确输入到EXCEL表格模板中，上传数据后，表格会自动配置相关内容。</div>

							<a href="#" class="inportMz inportMd">下载EXCEL模板</a>
							<div class="inportMz inportMu" >上传数据</div>
							<input type="file" class="inportMf" id="inportMf"
								onchange="upFile()" />
						</div>
						<input type="button" class="inportB" value="提交" style="display: none;"/>
					</div>

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
		var afuxuanK2=document.querySelectorAll('.fuxuanK5');//获取页面中复选框的外部div，注意获取的class，本页面是fuxuanK2，有的页面为fuxuanK3或其他
		
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
	

	var cs_id="${cs_id}";//采集源id
	var searchId="${searchId}";//操作字段id
	var searchWord="";//搜索词
	var desc_asc="${desc_asc}";//排序
	var oldCondition=$("#oldCondition").html();//累加筛选条件
	var page="${page}";//页码
	var ft_id = $('#ft_id').val();//类型id
	var nodeName = $("#dataNodeTextArea").val();//结点名
	var sourceDataId = $("#sourceDataId").val();//源数据id
	var formatNodeId = $("#formatNodeId").val();//结点id
	var fanhui='check1_'+formatNodeId;
    var fanhuiID=document.getElementById(fanhui);
    var IDparent1=fanhuiID.parentNode;
    var IDparent2=IDparent1.parentNode;
    var owenben=IDparent2.querySelectorAll('.PJliB2Lt')[0];
	    owenben.style.color="#16579b";
	//更换采集源，刷新页面
	//选择待操作字段
	$('.prodaclmRzTt2').click(function(){
		searchId = $(this).attr('id');
	});
	//全搜索
   $(".searchCt").bind("keypress" , function(event){
		if(event.keyCode == 13){
			reset();
			window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="+cs_id
					+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
					+"&searchFirstWord="+this.value+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
		}
	});

    //点击过滤按钮
  	$("#guolv").click(function (){
  		//过滤
  		filter();
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
			url:"/wankangyuan/formatData/getFieldDatas",
			async:true,
			data:{
				type:2,
				cs_id:$("#cs_id").val(),
				ft_id:ft_id,
				formatNodeId:formatNodeId,
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
					var data = res.ffDatas;
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
  	};
	//排序
	var oBTSXcliI1=document.querySelectorAll('.BTSXcliI')[0];
	var oBTSXcliI2=document.querySelectorAll('.BTSXcliI')[1];
	oBTSXcliI1.onclick=function(){
		desc_asc="ASC";
	    window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
			+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
			+"&searchId="+searchId+"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
	}

	oBTSXcliI2.onclick=function(){
		desc_asc="DESC";
	    window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
			+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
			+"&searchId="+searchId+"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
	}
	function updown(sc){
		desc_asc=sc;
	    window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
			+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
			+"&searchId="+searchId+"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
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
	//数据筛选，支持模糊查询
	function shaixuan(likeSearch){
		var afuxuanK=document.querySelectorAll('.BTSXcli2li');
        var chooseDatasArr = [];
        for(var i=0;i<afuxuanK.length;i++){
        	if(afuxuanK[i].querySelectorAll('.BTSXcli2liI')[0].checked){
        		chooseDatasArr.push(afuxuanK[i].querySelectorAll('.BTSXcli2liT')[0].innerHTML);
        	}
        }
        var chooseDatas=chooseDatasArr.join(",");
    	window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
			+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
			+"&searchId="+searchId+ "&desc_asc="+desc_asc+"&likeSearch="+likeSearch
			+"&searchWord="+searchWord+"&oldCondition="+oldCondition
    		+"&chooseDatas="+chooseDatas+"&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val();
	}
	
	
	
	//分页
    $('#box').paging({
            initPageNo: ${page}, // 初始页码
            totalPages: Math.ceil(${total}/${rows}), //总页数
            totalCount: '合计&nbsp;' + ${total} + '&nbsp;条数据', // 条目总数
            slideSpeed: 600, // 缓动速度。单位毫秒
            jump: true, //是否支持跳转
            callback: function(page) { // 回调函数
                
            	var user_id=${user.id};
        		var cs_id = $("#cs_id").val();
        		var ft_id = $("#ft_id").val();
        		var formatNodeId = $("#formatNodeId").val();
        		var sourceDataId = $("#sourceDataId").val();
        		//之前选中的id
        		var ids_str = distinct_ids($("#ids").val());
        		//之前选中的id+当页选中的id
        		var ids=getids()+","+ids_str;
        		//是否全选
        		var is_all = isAll();
    		
    		if(page!=${page}){
    			window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
    				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
    				+"&page="+page+"&strip=${rows}&ids="+$("#ids2").val()+"&isAll="+$("#isAll2").val()
    				+"&searchId="+searchId+"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;
    			
    		}
    	}
    }); 
		//新增节点
		$("#addDataNodeSubmit").click(function (){
			var cs_id = $('#cs_id').val();
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
	    					window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="+cs_id
	    	    				+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId;
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
	    					window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
	    	    				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId;
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
    	//新增数据
    	$("#addFormatDataSubmit").click(function (){
    		var oclmReditM=document.querySelectorAll('.clmReditM')[0];
    		console.log(oclmReditM);
    		var aclmReditMz=oclmReditM.querySelectorAll('.clmReditMz');
    		console.log(aclmReditMz);
    		var aff_name=oclmReditM.querySelectorAll('.ff_name');
			var aerror_msg=oclmReditM.querySelectorAll('.error_msg');
    		var err_arr=[];
    		var err_pd=true;
    		
    		for(var i=0;i<aclmReditMz.length;i++){
    			if(aclmReditMz[i].querySelectorAll('.redstar')[0]){
    				console.log(i);
    				if(aclmReditMz[i].querySelectorAll('.clmReditMzp')[0].value.replace(/\s+/g,"")==''){
    					err_pd=false;
    					err_arr.push(i);
    				}
    			}
    		}
    		
    		if(err_pd){
    			var type = $("#type").val();
        		if(type == "add"){
            		var formatDatas = document.querySelectorAll(".clmReditMzp");
            		var cs_id = $("#cs_id").val();
            		var ft_id = $("#ft_id").val();
            		var formatNodeId = $("#formatNodeId").val();
            		var sourceDataId = $("#sourceDataId").val();
            		var formatFieldDatas = {};
            		for(var i = 0 ; i < formatDatas.length ; i++){
            			formatFieldDatas[formatDatas[i].id] = formatDatas[i].value;
            		}
        			$.ajax({
                	url:"/wankangyuan/formatData/insertFormatData",
                	type:"post",
                	data:{
                		cs_id:cs_id,
                		ft_id:ft_id,
                		sourceDataId:sourceDataId,
                		formatNodeId:formatNodeId,
                		formatFieldDatas:JSON.stringify(formatFieldDatas)
                	},
                	dataType:"json",
                	success : function(data){
                		if(data.result == true){
                			alert(data.message);
                			//刷新页面
                			window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
                				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
                				+"&page="+page+"&strip=${rows}"
                				+"&searchId="+searchId+"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;
                		}else{
                			alert(data.message);
                		}
                	},
                	error : function(){
                		alert("网络异常，请稍后重试！");
                	}
                });
        		}else if(type == "edit"){ 
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
    	            	alert("请勾选待修改的结点！");
    	            	return;
    	            }else if(formatDataIds.length > 1){
    	            	alert("最多选择一个结点！")
    	            }else{
    	    	        var formatDatas = document.querySelectorAll(".clmReditMzp");
    	    			var cs_id = $("#cs_id").val();
    	    			var ft_id = $("#ft_id").val();
    	    			var formatNodeId = $("#formatNodeId").val();
    	    			var sourceDataId = $("#sourceDataId").val();
    	    			var formatFieldDatas = {};
    	    			for(var i = 0 ; i < formatDatas.length ; i++){
    	    				formatFieldDatas[formatDatas[i].id] = formatDatas[i].value;
    	    			}
    	    			$.ajax({
    	    	        	url:"/wankangyuan/formatData/updateFormatData",
    	    	        	type:"post",
    	    	        	data:{
    	    	        		cs_id:cs_id,
    	    	        		ft_id:ft_id,
    	    	        		sourceDataId:sourceDataId,
    	    	        		formatNodeId:formatNodeId,
    	    	        		formatDataId:formatDataIds.join(","),
    	    	        		formatFieldDatas:JSON.stringify(formatFieldDatas)
    	    	        	},
    	    	        	dataType:"json",
    	    	        	success : function(data){
    	    	        		if(data.result == true){
    	    	        			alert(data.message);
    	    	        			// 刷新页面
    	    	        			window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
    	    	        				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
    	    	        				+"&page="+page+"&strip=${rows}"
    	    	        				+"&searchId="+searchId+"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;
    	    	        		}else{
    	    	        			alert(data.message);
    	    	        		}
    	    	        	},
    	    	        	error : function(){
    	    	        		alert("网络异常，请稍后重试！");
    	    	        	}
    	    	        });  
    	            }
    	       }
    		}else{
    			var j=err_arr[0];
    			alert(aff_name[j].innerHTML+'项错误信息：'+aerror_msg[j].innerHTML);
    		}
    		
    		
    		
    	    		
    		
    	});
    	//移除数据
    	$(".clmRsb_remove").click(function (){
    		
    		var con1=confirm("确定删除选中项吗？");
            if(con1){
                //确认删除的动作            
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
            var cs_id = $("#cs_id").val();
    		var ft_id = $("#ft_id").val();
    		var formatNodeId = $("#formatNodeId").val();
    		var sourceDataId = $("#sourceDataId").val();

    		$.ajax({
            	url:"/wankangyuan/formatData/deleteFormatDatas",
            	type:"post",
            	data:{
            		cs_id:cs_id,
            		ft_id:ft_id,
            		formatDataIds:formatDataIds.join(",")
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			alert(data.message);
            			//刷新页面
            			window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
            				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
            				+"&page="+page+"&strip=${rows}"
            				+"&searchId="+searchId+
            	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;
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
        					window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
                				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
                				+"&page="+page+"&strip=${rows}"
                				+"&searchId="+searchId+
                	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;
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
    	//查看结点数据
    	function dataNodeClick(formatNodeId , ft_id){
    		var cs_id = $('#cs_id').val();
    		var sourceDataId = $("#sourceDataId").val();
    		window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
    				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId;
    	}
    	//meta数据修改
    	function meta_input_submit(formatDataId){
    		console.log(formatDataId);
    		var odata=document.getElementById(formatDataId);
    		console.log(odata);
    		var odata_fa2=odata.parentNode.parentNode;
    		//console.log(odata_fa2);
    		//console.log(odata_fa2.querySelectorAll('.redstar')[0]);
    		var ff_name=odata_fa2.querySelectorAll('.ff_name')[0];
    		var oerror_msg=odata_fa2.querySelectorAll('.error_msg')[0];
    		if(odata_fa2.querySelectorAll('.redstar')[0]){
    			//console.log(odata.value.replace(/\s+/g,""));
    			if(odata.value.replace(/\s+/g,"")==''){
    				alert(ff_name.innerHTML+"项错误信息："+oerror_msg.innerHTML);
    			}else{
    				var cs_id = $("#cs_id").val();
    	    		var ft_id = $("#ft_id").val();
    	    		var formatNodeId = $("#formatNodeId").val();
    	    		var sourceDataId = $("#sourceDataId").val();
    	    		var formatFieldDatas = {};
    	    		formatFieldDatas[formatDataId] = $("#"+formatDataId).val();
    	    		
    	    		$.ajax({
    	            	url:"/wankangyuan/formatData/updateFormatData",
    	            	type:"post",
    	            	data:{
    	            		cs_id:cs_id,
    	            		ft_id:ft_id,
    	            		formatNodeId:formatNodeId,
    	            		formatFieldDatas:JSON.stringify(formatFieldDatas)
    	            	},
    	            	dataType:"json",
    	            	success : function(data){
    	            		if(data.result == true){
    	            			alert(data.message);
    	            			//刷新页面
    	            			window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
    	            				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
    	            				+"&page="+page+"&strip=${rows}"
    	            				+"&searchId="+searchId+
    	            	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;
    	            		}else{
    	            			alert(data.message);
    	            		}
    	            	},
    	            	error : function(){
    	            		alert("网络异常，请稍后重试！");
    	            	}
    	            });
    			}
    		}else{
    			var cs_id = $("#cs_id").val();
        		var ft_id = $("#ft_id").val();
        		var formatNodeId = $("#formatNodeId").val();
        		var sourceDataId = $("#sourceDataId").val();
        		var formatFieldDatas = {};
        		formatFieldDatas[formatDataId] = $("#"+formatDataId).val();
        		
        		$.ajax({
                	url:"/wankangyuan/formatData/updateFormatData",
                	type:"post",
                	data:{
                		cs_id:cs_id,
                		ft_id:ft_id,
                		formatNodeId:formatNodeId,
                		formatFieldDatas:JSON.stringify(formatFieldDatas)
                	},
                	dataType:"json",
                	success : function(data){
                		if(data.result == true){
                			alert(data.message);
                			//刷新页面
                			window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
                				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
                				+"&page="+page+"&strip=${rows}"
                				+"&searchId="+searchId+
                	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;
                		}else{
                			alert(data.message);
                		}
                	},
                	error : function(){
                		alert("网络异常，请稍后重试！");
                	}
                });
			}
    		
    		
    	}
    	//导出数据
    	$(".app_expexport_data").click(function (){

            var cs_id = $("#cs_id").val();
    		var ft_id = $("#ft_id").val();
    		var formatNodeId = $("#formatNodeId").val();
    		var sourceDataId = $("#sourceDataId").val();
    		
   		    var idQuanXuan= $("#isAll2").val();
            var ids3=$(" #ids2").val();
           
            if(idQuanXuan =="false"){
            	 if(ids3 == ""){
            		alert("请勾选待导出数据！");
                 	return;
                 }
            }
    		
            /* var afuxuanK=document.querySelectorAll('.fx4');
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
             } */
              //刷新页面
            window.location.href="/wankangyuan/export/formatData?cs_id="
            				+cs_id+"&ft_id="+ft_id+"&sourceDataId="+sourceDataId+"&formatNodeId="+formatNodeId+"&type=2"
            				+"&searchId="+searchId+"&searchWord="+searchWord+"&oldCondition="+oldCondition+"&ids="+ids3+"&isAll="+idQuanXuan;
           	
    	});
    	//导出结点
    	$(".app_expexport_node").click(function (){
    		var afuxuanK=document.querySelectorAll('.fuxuanK42');
            var afuxuan=[];
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
            }
            var ft_ids = [];
            var formatNodeIds = [];
            for(var i=0;i<afuxuanK.length;i++){
            	if(afuxuan[i].checked){
            		formatNodeIds.push(afuxuan[i].name);
            		ft_ids.push(afuxuan[i].value);
            	}
            }
           /*  if(ft_ids.length > 1 || formatNodeIds.length > 1){
            	alert("最多选择一个结点！");
            	return;
            } */
            
            if(ft_ids == "" || formatNodeIds == ""){
         	   alert("请选择待导出数据的结点！");
         	   return;
            }
            if(ft_ids.length != formatNodeIds.length ){
            	alert("最多选择一个类型！");
            	return;
            } 
            var cs_id = $('#cs_id').val();
    		var ft_id = ft_ids.join(",");
    	//	var formatNodeId = formatNodeIds.join(",");
           	window.location.href="/wankangyuan/export/formatNode?cs_id="+cs_id+"&ft_ids="+ft_id+"&formatNodeIds="+formatNodeIds.join(",");
           	
    	});
    	//导出类型
    	$(".app_expexport_type").click(function (){
    		
    		var afuxuanK=document.querySelectorAll('.fuxuanK41');
            var afuxuan=[];
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
            }
            var ft_ids = [];
            for(var i=0;i<afuxuanK.length;i++){
            	if(afuxuan[i].checked){
            		ft_ids.push(afuxuan[i].name);
            	}
            }
            if(ft_ids.length > 1){
            	alert("最多选择一种格式数据类型！");
            	return;
            }
           	if(ft_ids == ""){
        	   alert("请选择待导出数据的格式类型！");
        	   return;
           	}
            var cs_id = $('#cs_id').val();
    		var sourceDataId = $("#sourceDataId").val();
    		var ft_id = ft_ids.join(",");
           	window.location.href="/wankangyuan/export/formatType?cs_id="+cs_id+"&sourceDataId="+sourceDataId+"&ft_id="+ft_id
			+"&page="+page+"&strip=${rows}"
			+"&searchId="+searchId+"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition;;
    	
    	});
    	
    	$(".inportMd").click(function (){
    		var ft_id = $("#ft_id").val();
    		window.location.href="/wankangyuan/export/formatDataModel?ft_id="+ft_id;
    	});
    	
    	$(".inportMu").click(function (){
    		$("#inportMf").click();
    	});
    	
    	//String cs_id, String ft_id, String formatNodeId)
    	
    	function upFile(){
    		var cs_id = $("#cs_id").val();
    		var ft_id = $("#ft_id").val();
    		var formatNodeId = $("#formatNodeId").val();
    		var sourceDataId = $("#sourceDataId").val();
    		var file = document.getElementById("inportMf").files[0];
            var formdata = new FormData();
            formdata.append('file', file);
            
    		$.ajax({
	            url: '/wankangyuan/import/formatData?cs_id='+cs_id+"&ft_id="+ft_id+"&formatNodeId="+formatNodeId,
	            type: 'POST',
	            data: formdata,
				async:true,
	            cache: false,
	            contentType: false,
	            processData: false,
	            success: function (data) {
	            	if(data.result == false){
	            		alert(data.message)
	            	/* }else{
	            		
	            		window.location.href="/wankangyuan/formatNode/getFormatNodeById?cs_id="
	            				+cs_id+"&sourceDataId="+sourceDataId+"&type=2&ft_id="+ft_id+"&formatNodeId="+formatNodeId
	            				+"&page="+page+"&strip=${rows}"
	            				+"&searchId="+searchId+
	            	    		"&desc_asc="+desc_asc+"&searchWord="+searchWord+"&oldCondition="+oldCondition; */
	            	}
	            },
	            error: function () {
	            	alert("网络异常，请稍后重试！");
	            }
	        });
    		alert("数据导入中，您可先进行其他操作！");
    	}

    	var aprodaclmRzTt2=document.querySelectorAll('.prodaclmRzTt2');
        var oPJK=document.querySelectorAll('.PJK')[0];//项目框
        var oBTSX=document.querySelectorAll('.BTSX2')[0];//项目表头筛选框

        //点击设置排序筛选框
        for(var i=0;i<aprodaclmRzTt2.length;i++){
            (function(j){
                aprodaclmRzTt2[j].onclick=function(){
                    // if(BTSXpd==j){
                    //     oBTSX.style.display="none";
                    //     BTSXpd=-1;
                    // }else{
                        oBTSX.style.display="block";
                        // BTSXpd=j;
                    // }
                    var BTSXleft=aprodaclmRzTt2[j].offsetLeft;
                    // oBTSX.name=aprodaclmRzTt2[j].innerHTML;
                    if(document.querySelectorAll('.BTSXpd')[0]){
                        var oBTSXpd=document.querySelectorAll('.BTSXpd')[0];//项目表头筛选框判断
                        oBTSXpd.value=aprodaclmRzTt2[j].title;
                        console.log(oBTSXpd.value);
                    }
                    
                    
                    console.log(BTSXleft);
                    if(BTSXleft>1118){
                        BTSXleft=1118;
                    }
                    oBTSX.style.left=BTSXleft-130+'px'; 
                    //oBTSX.style.top="100px";
                    event.stopPropagation();
                }
            })(i)
        }
        window.onclick=function(){
        //   console.log(1);
            oBTSX.style.display="none";
        }
        oBTSX.onclick=function(){
            event.stopPropagation();
        }
      /*   var aBTSXcliI=oBTSX.querySelectorAll('.BTSXcliI');//筛选框排序箭头
        var oBTSXcliIpd=document.querySelectorAll('.BTSXcliIpd')[0];//筛选框选择判断
        for(var i=0;i<aBTSXcliI.length;i++){
            (function(index){
                aBTSXcliI[index].onclick=function(){
                    for(var j=0;j<aBTSXcliI.length;j++){
                        aBTSXcliI[j].style.color="#666";
                    }
                    aBTSXcliI[index].style.color="#5ca0e5";
                    oBTSXcliIpd.value=index+1;
                }
            })(i)
        } */

        var oBTSXcli3BTres=document.querySelectorAll('.BTSXcli3BTres')[0];//重置按钮
        var aBTSXcli2liC=document.querySelectorAll('.BTSXcli2liC');//复选框
        var oBTSXcliGLK=document.querySelectorAll('.BTSXcliGLK')[0];//过滤框

       /*  oBTSXcli3BTres.onclick=function(){
            for(var j=0;j<aBTSXcliI.length;j++){
                aBTSXcliI[j].style.color="#666";
            }
            oBTSXcliGLK.value="";
            for(var i=0;i<aBTSXcli2liC.length;i++){
                aBTSXcli2liC[i].checked=false;
            }
        }	 */
        
        
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