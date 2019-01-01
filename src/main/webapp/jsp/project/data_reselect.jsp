<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
<link rel="stylesheet" type="text/css" href="/wankangyuan/static/css/project1.css" />
<script type="text/javascript" src="/wankangyuan/jsp/project/js/project1.js"></script>
<script type="text/javascript">

    window.onload=function(){
    	pro_appendre2();
    	
    	// 全选框和复选框的动作
        if(document.querySelectorAll('.quanxuanK')[0]){
            oquanxuanK=document.querySelectorAll('.quanxuanK')[0];
            var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];
            var afuxuan=[];
            var afuxuanK=[];
            if(document.querySelectorAll('.fuxuanK2')[0]){
                afuxuanK=document.querySelectorAll('.fuxuanK2');
                console.log("k2");
            }else if(document.querySelectorAll('.fuxuanK3')[0]){
                afuxuanK=document.querySelectorAll('.fuxuanK3');
                console.log("k3");
            }
            if(afuxuanK[0]){
                for(var i=0;i<afuxuanK.length;i++){
                    afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
                }
            }
            oquanxuanK.onchange=function(){
                if(oquanxuan.checked){
                    for(var i=0;i<afuxuanK.length;i++){
                        afuxuan[i].checked=1;
                    }
                }else{
                    console.log(2);
                    for(var i=0;i<afuxuanK.length;i++){
                        afuxuan[i].checked=0;
                    }
                }
            }
            if(afuxuanK[0]){
                for(var i=0;i<afuxuanK.length;i++){
                    (function(index){
                        afuxuanK[i].onchange=function(){
                            var fuxuanPD=0;
                            for(var j=0;j<afuxuanK.length;j++){
                                if(afuxuan[j].checked){
                                    fuxuanPD++;
                                }
                                console.log(afuxuan[j].checked);
                            }
                            console.log(fuxuanPD);
                            if(fuxuanPD==afuxuanK.length){
                                oquanxuan.checked=1;
                            }else if(fuxuanPD!=afuxuanK.length){
                                oquanxuan.checked=0;
                            }
                        }
                    })(i)
                }
            }
        }else{
            console.log(222);
        }
    }
</script>
<body>
    <div class="Box">
        <div class="box2">
            <div class="proreK2">
                <div class="inportT">
                    <div class="inportTt">选择格式数据</div>
                    <select id="source_Select" class="pro_menusel">
						<c:forEach items="${sources}" var="sourceTemp">
							<c:if test="${sourceTemp.cs_id!=source.cs_id}">
								<option value="${sourceTemp.cs_id}">${sourceTemp.cs_name}</option>
							</c:if>
							<c:if test="${sourceTemp.cs_id==source.cs_id}">										
								<option value="${sourceTemp.cs_id}" selected="selected">${sourceTemp.cs_name}</option>
							</c:if>
						</c:forEach>						
					</select>
                </div>
                <div class="proreKC">
                	<!-- 格式数据字段需要根据不同的数据源进行变化 -->
                    <div class="proreT">
                        <div class="allK">
							<div class="quanxuanK">
								<input type="checkbox" class="input_check" name="0" id="check0">
								<label for="check0"></label>
							</div>
							<div class="allT">全选</div>
						</div>
						<c:forEach items="${source.sourceFields}" var="sourceFieldTemp">
							<div class="PJListli">${sourceFieldTemp.csf_name}</div>
						</c:forEach>	
                    </div>
                    <div class="proreM">
                    	
                    	<!-- begin -->
                        <div class="proreMz">
                            <c:forEach items="${sourceDatas}" var="sourceData">
								<div class="PJli">
									<div class="PJliC">
										<c:forEach items="${sourceData}" var="sourceDataField" varStatus="status">
											<c:if test="${status.index==0}">
												<div class="fuxuanK2">
													<input type="checkbox" class="input_check" name="${sourceDataField}" id="check${sourceDataField}">
													<label for="check${sourceDataField}"></label>
												</div>
											</c:if>
											<c:if test="${status.index!=0}">
                                				<td style="cursor:pointer;">
                                				<div class="PJliCli2">
                                 				   <a href="#" onclick="datainHref('${sourceData[0]}')">
													   <c:choose>
														   <c:when test="${source.sourceFields[status.index-1].type=='图片' || source.sourceFields[status.index-1].type=='文件'}">
															   <span>${fn:substringAfter(sourceDataField, "_")}</span>
														   </c:when>
														   <c:otherwise>
															   <span>${sourceDataField}</span>
														   </c:otherwise>
													   </c:choose>
                                    </a>
                                </div>
                                </td>
                             </c:if>
										</c:forEach>
									</div>
								</div>
							</c:forEach>
                        </div>
						<!-- end -->
						
                    </div>
                </div>
                <div class="proreB">
               		<input id="submit_data" type="button" class="proreb" value="提交" />
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="/wankangyuan/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/wankangyuan/static/js/layer/layer.js"></script>
    <script type="text/javascript">

		var p_id = ${p_id};
    //进入详情页
    function datainHref(sourceDataId){
		var cs_id = $("#source_Select option:selected").val();

		window.location.href="/wankangyuan/sourceData/getSourceDataById?type=5&cs_id="+cs_id+"&p_id="+p_id+"&sourceDataId="+sourceDataId;
	}
    	
  	//切换数据源
   $("#source_Select").change(function(){
	var cs_id = $("#source_Select option:selected").val();

	window.location.href="/wankangyuan/projectFormatData/getAllSourceDatas?type=5&cs_id="+cs_id+"&p_id="+p_id;
    });
  	//提交勾选的数据
  	$("#submit_data").click(function (){
  		
  		var afuxuanK=document.querySelectorAll('.fuxuanK2');
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
          	layer.msg("请勾选源数据");
          	return;
          }
          var cs_id = $("#source_Select option:selected").val();
          $.ajax({
          	url:"/wankangyuan/common/selectCondition",
          	type:"post",
          	data:{
          		cs_id:cs_id,
          		sourceDataIds:ids.join(",")
          	},
          	dataType:"json",
          	success : function(data){
          		window.parent.postMessage(data.message, "*");
          	},
          	error : function(){
          		layer.msg("联网失败");
          	}
          });
  	});	
	    
    </script>
</body>
</html>