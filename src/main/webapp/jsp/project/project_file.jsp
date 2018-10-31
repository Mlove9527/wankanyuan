<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
</head>
<script type="text/javascript" src="/wankangyuan/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>
<link rel="stylesheet" type="text/css" href="/wankangyuan/static/css/project1.css" />
<script type="text/javascript" src="/wankangyuan/jsp/project/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
    	
    	//点击项目标题栏
      	 $(".prof_lbRmULtli").click(function (){
      		document.querySelectorAll(".BTSX")[0].id=this.id;//更新筛选框的筛选字段为当前点击的字段名
      		$("#BTSXcli2").html("");
     			//filter();//然后自动执行过滤，筛选出十个数值
      	});
    	
        project0();
      //点击表头的排序筛选功能
        var oPJK=document.querySelectorAll('.prof_lbRmUL')[0];//项目框
        var oBTSX=document.querySelectorAll('.BTSX')[0];//项目表头筛选框
        var oPJList=document.querySelectorAll('.prof_lbRmULt')[0];//项目表头栏
        var aPJListli_1=oPJList.querySelectorAll('.prof_lbRmULtli');//项目表头
        var aPJListli = [];
        
        for(var i=0;i<aPJListli_1.length;i++){
        	if(aPJListli_1[i].innerHTML=="下载"){
        		
        	}else{
        		aPJListli.push(aPJListli_1[i]);
        	}
        	
        }

        //点击设置排序筛选框
        for(var i=0;i<aPJListli.length;i++){
            (function(j){
                aPJListli[j].onclick=function(){
                    oBTSX.style.display="block";
                    
                    var BTSXleft=aPJListli[j].offsetLeft;
                    oBTSX.name=aPJListli[j].innerHTML;
                   
                    //console.log(BTSXleft);
                    if(BTSXleft>1118){
                        BTSXleft=1118;
                    }
                    oBTSX.style.left=BTSXleft-110+'px';
                    if(oBTSX.offsetLeft<=0){
                    	oBTSX.style.left='0px';
                    }else if(oBTSX.offsetLeft>=600){
                    	oBTSX.style.left='600px';
                    }
                    oBTSX.style.top="85px";
                    event.stopPropagation();
                }
            })(i)
        }
        $(window).click(function(){
            oBTSX.style.display="none";
        })
        $(oBTSX).click(function(){
            event.stopPropagation();
        })
        

        //以下是文件的相关操作
        
        var oprof_lbLtRtup=document.querySelectorAll('.prof_lbLtRtup')[0];//文件上传按钮
        var ofileaddK=document.querySelectorAll('.fileaddK')[0]//文件上传框
        var ofileaddTr=document.querySelectorAll('.fileaddTr')[0];//文件上传框关闭按钮
        var owidth=document.body.clientWidth;//屏幕宽度

        var ofileKpd=0;//文件上传框状态判断

        ofileaddTr.onclick=function(){
            if(ofileKpd==1){
                ofileaddK.style.display="none";
                ofileKpd=0;
            }
        }
        oprof_lbLtRtup.onclick=function(){
            if(ofileKpd==0){
            	if(floder_id != 0){
                    ofileaddK.style.display="block";
                    //console.log(owidth/2);
                    //console.log(ofileaddK.offsetWidth/2);
                    ofileaddK.style.left=owidth/2-ofileaddK.offsetWidth/2+"px";//创建框居中
                    ofileKpd=1;
            	}else{
            		layer.msg("请选择文件夹");
            	}

            }
        }

    	//文件树
        var oprof_lbLmT=document.querySelectorAll('.prof_lbLmT')[0];
        var aPJliB1L=oprof_lbLmT.querySelectorAll('.PJliB1L');
        var aPJliB2L=oprof_lbLmT.querySelectorAll('.PJliB2L');

        var aPJliBL=[];

        for(var i=0;i<aPJliB1L.length;i++){
            aPJliBL.push(aPJliB1L[i]);
        }
        for(var i=0;i<aPJliB2L.length;i++){
            aPJliBL.push(aPJliB2L[i]);
        }
        
        
        oprof_lbLmT.onclick=function(){
            for(var j=0;j<aPJliBL.length;j++){
                aPJliBL[j].style.color="#666";
            }
        }

        /* var aPJliB1Lt=document.querySelectorAll('.PJliB1Lt');
        var aPJliB2Lt=document.querySelectorAll('.PJliB2Lt');
        var awenjian=[];
        for(var i=0;i<aPJliB1Lt.length;i++){
            awenjian.push(aPJliB1Lt[i]);
        }
        for(var i=0;i<aPJliB2Lt.length;i++){
            awenjian.push(aPJliB2Lt[i]);
        } */
        
        
        /* console.log(awenjian);
        
        var oprof_editK=document.querySelectorAll('.prof_editK')[0];
        if(oprof_editK){
            var oprof_aeMzp=oprof_editK.querySelectorAll('.prof_aeMzp')[0];
            console.log(oprof_aeMzp);
        } */
        
        //console.log(aPJliBL);

        oprof_lbLmT.onclick=function(){
            for(var j=0;j<aPJliBL.length;j++){
                aPJliBL[j].style.color="#666";
            }
        }

        

    	//文件树添加根/叶、修改、删除
        var oprof_lbLtRaddg=document.querySelectorAll('.prof_lbLtRaddg')[0];//添加根按钮
        var oprof_lbLtRaddy=document.querySelectorAll('.prof_lbLtRaddy')[0];//添加叶按钮
        var oprof_lbLtRtch=document.querySelectorAll('.prof_lbLtRtch')[0];//修改按钮
        var oprof_lbLtRtde=document.querySelectorAll('.prof_lbLtRtde')[0];//删除按钮

        //添加
        var oprof_addK=document.querySelectorAll('.prof_addK')[0];//添加根、叶框
        var oprof_aeTx=oprof_addK.querySelectorAll('.prof_aeTx')[0];//添加根、叶框关闭按钮
        var oprof_aeb=oprof_addK.querySelectorAll('.prof_aeb')[0];//添加根、叶框提交按钮

        oprof_lbLtRaddg.onclick=function(){
        	g_y = 1;
            oprof_addK.style.display="block";
        }

        oprof_lbLtRaddy.onclick=function(){
        	g_y = 0;
            if(floder_id == 0){
            	layer.msg("请选中父文件夹");
            	return;
            }else{
            	oprof_addK.style.display="block";
            }
        }

        oprof_aeTx.onclick=function(){
            oprof_addK.style.display="none";
        }
        oprof_aeb.onclick=function(){
            oprof_addK.style.display="none";
            var floder_name  = $("#leafFloderName").val();
            $.ajax({
            	url:"/wankangyuan/projectFloderFile/addProjectFloder",
            	type:"post",
            	data:{
            		g_y:g_y,
            		parent_id:floder_id,
            		floder_name:floder_name
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
            		}else{
            			layer.msg("叶目录添加失败");
            		}
            	},
            	error : function(){
            		layer.msg("联网失败");
            	}
            });

        }


        //修改
        var oprof_editK=document.querySelectorAll('.prof_editK')[0];//添加根、叶框
        var oprof_aeTx=oprof_editK.querySelectorAll('.prof_aeTx')[0];//添加根、叶框关闭按钮
        var oprof_aeb=oprof_editK.querySelectorAll('.prof_aeb')[0];//添加根、叶框提交按钮

        oprof_lbLtRtch.onclick=function(){
        	if(floder_id == 0){
        		layer.msg("请选择待修改名称的文件夹");
        	}else{
        		oprof_editK.style.display="block";
        	}     
        }
        oprof_aeTx.onclick=function(){
            oprof_editK.style.display="none";
        }
        oprof_aeb.onclick=function(){
            oprof_editK.style.display="none";
            var floder_name  = $("#floderNameEdit").val();
            $.ajax({
            	url:"/wankangyuan/projectFloderFile/updateProjectFloder",
            	type:"post",
            	data:{
            		id:floder_id,
            		floder_name:floder_name
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
            		}else{
            			layer.msg("文件夹名称修改失败");
            		}
            	},
            	error : function(){
            		layer.msg("联网失败");
            	}
            });
            
        }


        //删除
        var oprof_delK=document.querySelectorAll('.prof_delK')[0];//添加根、叶框
        var oprof_aeTx=oprof_delK.querySelectorAll('.prof_aeTx')[0];//添加根、叶框关闭按钮
        var oprof_aeb=oprof_delK.querySelectorAll('.prof_aeb')[0];//添加根、叶框提交按钮

        oprof_lbLtRtde.onclick=function(){
        	if(floder_id == 0){
        		layer.msg("请选择待删除的文件夹");
        	}else{
        		oprof_delK.style.display="block";
        	}    
        }
        oprof_aeTx.onclick=function(){
            oprof_delK.style.display="none";
        }
        oprof_aeb.onclick=function(){
        	$.ajax({
        		url:"/wankangyuan/projectFloderFile/deleteProjectFloder",
        		type:"post",
        		data:{
        			floder_id:floder_id
        		},
        		dataType:"json",
        		success : function(data){
        			if(data.result == true){
        				layer.msg("文件夹删除成功");
        				window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
        			}else{
        				layer.msg("文件夹删除失败");
        			}
        		},
        		error : function(){
        			layer.msg("联网失败");
        		}
        		
        	});
            oprof_delK.style.display="none";
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
                    	 <a href="/wankangyuan/projectAppEnd/selectProjectAppEnd"><div class="top2Ctr">应用结果</div></a>
                    </c:if>
                   
                    <c:if test="${authoritys['42'] == true }">
                    	<a href="/wankangyuan/projectApp/selectProjectApp?p_id=${project.id}"><div class="top2Ctr">应用</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['31'] == true }">
                    	<a href="/wankangyuan/sourceData/getSourceDatas?type=4&p_id=${project.id}"><div class="top2Ctr">格式数据</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['27'] == true }">
                    	<a href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId"><div class="top2Ctr active">文件</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['11'] == true }">
                    	<a href="/wankangyuan/project/getProjectDetail"><div class="top2Ctr ">基本信息</div></a>
                    </c:if>
                    
                    
                </div>
            </div>
            <div class="prof_lb">
                <div class="prof_lbL">
                    <div class="prof_lbLt">
                        <div class="prof_lbLtL">文件树</div>
                        <div class="prof_lbLtR">
                        
	                        <c:if test="${authoritys['20'] == true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRaddg" value="添加根" />
                            	<div class="prof_lbLtRline"></div>
		                    </c:if>
		                    <c:if test="${authoritys['20'] != true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRaddg" value="添加根" style="display:none;"/>
                            	<div class="prof_lbLtRline" style="display:none;"></div>
		                    </c:if>
		                    
		                    <c:if test="${authoritys['21'] == true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRaddy" value="添加叶" />
                            	<div class="prof_lbLtRline"></div>
		                    </c:if>
		                    <c:if test="${authoritys['21'] != true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRaddy" value="添加叶" style="display:none;"/>
                            	<div class="prof_lbLtRline" style="display:none;"></div>
		                    </c:if>
		                    
		                    <c:if test="${authoritys['22'] == true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRtup" value="上传" />
                            	<div class="prof_lbLtRline"></div>
		                    </c:if>
		                    <c:if test="${authoritys['22'] != true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRtup" value="上传" style="display:none;"/>
                            	<div class="prof_lbLtRline" style="display:none;"></div>
		                    </c:if>
		                    
		                    <c:if test="${authoritys['23'] == true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRtch" value="修改" />
                            	<div class="prof_lbLtRline"></div>
		                    </c:if>
		                    <c:if test="${authoritys['23'] != true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRtch" value="修改" style="display:none;"/>
                            	<div class="prof_lbLtRline" style="display:none;"></div>
		                    </c:if>
		                    
		                    <c:if test="${authoritys['24'] == true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRtde" value="删除" />
		                    </c:if> 
		                    <c:if test="${authoritys['24'] != true }">
		                    	<input type="button" class="prof_lbLtRt prof_lbLtRtde" value="删除" style="display:none;"/>
		                    </c:if> 
                        </div>
                    </div>
                    <div class="prof_lbLm">
                        <div class="prof_lbLmT">
                            <div class="prodainmL2">
                               	<c:forEach items="${projectFloders}" var="projectFloder">
                               		<div class="PJliB1" >
	                                	<div class="PJliB1L">
	                                        <div class="PJliB2Lic"></div>
	                                        <div class="PJliB1Lt" id="floder${projectFloder.id}" onclick="clickFloder(${projectFloder.id})">${projectFloder.floder_name}</div>
	                                        <div class="PJliBLi PJliBLi1" id="show${projectFloder.id}" onclick="clickShow(${projectFloder.id})"></div>
	                                    </div>
	                                    <div class="PJliBR"></div>
	
                                    </div>
                               	</c:forEach>
                            </div>
                        </div>
                        <div class="prof_lbLmB">
                            <div class="prof_lbLmBt">
                                <div class="prof_lbLmBt1">总文件数：</div>
                                <div class="prof_lbLmBt2">${project.fileNum}</div>
                            </div>
                            <div class="prof_lbLmBt">
                                <div class="prof_lbLmBt1">我的文件数：</div>
                                <div class="prof_lbLmBt2">${myFileNum}</div>
                            </div>
                        </div>
                        <div class="prof_addK">
                            <div class="prof_aeT">
                                <div class="prof_aeTt">添加文件夹</div>
                                <div class="prof_aeTx"></div>
                            </div>
                            <div class="prof_aeM">
                                <div class="prof_aeMz">
                                    <div class="prof_aeMzt">文件夹名：</div>
                                    <input type="text" class="prof_aeMzp" id="leafFloderName"/>
                                </div>
                            </div>
                            <div class="prof_aeB">
                                <input type="button" class="prof_aeb" value="提交" id="submitLeaf"/>
                            </div>
                        </div>
                        <div class="prof_editK">
                            <div class="prof_aeT">
                                <div class="prof_aeTt">修改文件夹</div>
                                <div class="prof_aeTx"></div>
                            </div>
                            <div class="prof_aeM">
                                <div class="prof_aeMz">
                                    <div class="prof_aeMzt">文件夹名：</div>
                                    <input type="text" class="prof_aeMzp" id="floderNameEdit"/>
                                </div>
                            </div>
                            <div class="prof_aeB">
                                <input type="button" class="prof_aeb" value="提交" />
                            </div>
                        </div>
                        <div class="prof_delK">
                            <div class="prof_aeT">
                                <div class="prof_aeTt">删除文件夹</div>
                                <div class="prof_aeTx"></div>
                            </div>
                            <div class="prof_aeM">
                                <div class="prof_aeMt">确定删除选中的文件夹吗？</div>
                            </div>
                            <div class="prof_aeB">
                                <input type="button" class="prof_aeb" value="确定" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="prof_lbR">
                    <div class="prof_lbRt">
                        <div class="prof_lbRtL">文件列表</div>
                        <div class="search2">
                            <div class="search2C">
                                <img src="/wankangyuan/static/img/search.png" alt="" class="search2Ci" onclick="filesearch();"/>
                                <input type="text" class="search2Ct"  placeholder="搜索文件" id="filersercher"/>
                            </div>
                        </div>
                    </div>
                    <div class="prof_lbRm">
                        <div class="prof_lbRmT">
                            <!-- <input type="button" class="prof_lbRmTl prof_lbRmTbt prof_lbRmTse" value="筛选" /> -->
                                                  
                            <c:if test="${authoritys['25'] == true }">
		                    	<input type="button" class="prof_lbRmTr prof_lbRmTbt prof_lbRmTde " value="删除" onclick="deleteFiles()"/>
		                    </c:if>
                        </div>
                        <div class="prof_lbRmUL">
                            <div class="prof_lbRmULt">
			                            
						        <div class="allK">
			                        <div class="quanxuanK">
			                            <input type="checkbox" class="input_check" id="check0">
			                            <label for="check0"></label>
			                        </div>
			                        <div class="allT">选择</div>
			                    </div>

                                <div class="prof_lbRmULtli prof_lbRmULt2" id="file_name">文件名称</div>
                                <div class="prof_lbRmULtli prof_lbRmULt3" id="file_type">类型</div>
                                <div class="prof_lbRmULtli prof_lbRmULt4" id="file_size">大小（KB）</div>
                                <div class="prof_lbRmULtli prof_lbRmULt5" id="create_datetime">创建时间</div>
                                <div class="prof_lbRmULtli prof_lbRmULt6" id="creator">创建人</div>
                                <c:if test="${authoritys['26'] == true }">
			                    	<div class="prof_lbRmULtli prof_lbRmULt7">下载</div>
			                    </c:if> 
                                
                            </div>
                            <div class="prof_lbRmULm" id="filesList"></div>
                            <!-- <div class="pageK" id="box"></div> -->
                        </div>
                        <!-- 筛选框 -->
		                <div class="BTSX" id="${projectQueryCondition.columnName}">
		                    <input type="hidden" id="fileName" value="${fileName}"/>
		                    <input type="hidden" id="fileType" value="${fileType}"/>
		                    <input type="hidden" id="fileSize" value="${fileSize}"/>
		                    <input type="hidden" id="createDatetime" value="${createDatetime}"/>
		                    <input type="hidden" id="creator" value="${creator}"/>
		                    
		                    
		                    <input type="hidden" id="fileNameGl" value=""/>
		                    <input type="hidden" id="fileTypeGl" value=""/>
		                    <input type="hidden" id="fileSizeGl" value=""/>
		                    <input type="hidden" id="createDatetimeGl" value=""/>
		                    <input type="hidden" id="creatorGl" value=""/>
		                   
		                    
		                    
		                    <input id="isFilter" value="${projectQueryCondition.isFilter}" style="display:none;"/>
		                    <div class="BTSXc">
		                        <div class="BTSXcli">
		                            <input type="text" id="sort" style="display:none;" value="${projectQueryCondition.order}"/>
		                            <div class="BTSXcliT">排序：</div>
		                            <div class="BTSXcliI" id="sort_asc" <c:if test="${projectQueryCondition.order != null && projectQueryCondition.order == 'asc' }">style="color:#5ca0e5;"</c:if>>↑</div>
		                            <div class="BTSXcliI" id="sort_desc" <c:if test="${projectQueryCondition.order != null && projectQueryCondition.order == 'desc' }">style="color:#5ca0e5;"</c:if>>↓</div>
		                        </div>
		                        <div class="BTSXcli">
		                            <div class="BTSXcliT">过滤：</div>
		                            <input type="text" class="BTSXcliGLK" value="<%-- ${projectQueryCondition.filter} --%>"/>
		                            <button id="guolv">过滤</button>
		                        </div>
		                        <div class="BTSXcli">
		                            <div class="BTSXcliT">值筛选：</div>
		                            <input id="values" style="display:none;" value="${projectQueryCondition.values}"/>
		                        </div>
		                        <div class="BTSXcli2" id="BTSXcli2">
		                        <!-- 数据 -->
		                            <c:forEach items="${projectQueryCondition.strings}" var="projectQueryConditionTemp">
		                                <div class="BTSXcli2li">
		                                    <input type="checkbox" class="BTSXcli2liC"  name="${projectQueryConditionTemp}"/>
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
                </div>
            </div>

            <div class="fileaddK">
                <div class="fileaddT">
                    <div class="fileaddTl">上传文件</div>
                    <div class="fileaddTr"></div>
                </div>
                <div class="fileaddT2">
                    <div class="fileaddT2l" onclick="clickFile()">+ 添加文件</div>
                    <div class="fileaddT2r">（请上传txt、jpg等格式，大小不超过10兆）</div>
                </div>
                <div class="fileaddM">
                    <div class="fileaddMt">
                        <div class="fileaddMz addMtzname">名称</div>
                        <div class="fileaddMz addMtzsize">大小（KB）</div>
                        <div class="fileaddMz addMtzopea">操作</div>
                    </div>
                    <div class="fileaddMmK" id="fileUploadList"></div>
                </div>
                <button class="fileaddbtn" onclick="upFiles()">上传</button>
            </div>
            

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
    
    <!-- 文件上传form，但是实际的文件上传是通过ajax实现的 -->
	<form id="uploadForm" style="display:none;">
	    <input type="file" name="file" onchange="upFile()" id="uploadFile"/>
	</form>
	
	<a id="downFile" target="_black">下载</a>  
	
    <script type="text/javascript" src="/wankangyuan/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>
    <!-- <script type="text/javascript" src="/wankangyuan/static/js/paging.js"></script> -->
    <script type="text/javascript" src="/wankangyuan/jsp/project/js/project1.js"></script>
    <script type="text/javascript">
    
	   /* $('#box').paging({
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
	                window.location.href="/wankangyuan/projectApp/selectProjectApp?type=1&p_id="+p_id+"&page="+page+"&strip=${rows}&searchWord="+searchWord;
	            }
	        }
	    });*/
    
    	var ids = [];
    	var floder_id = 0; 
    	var g_y;
    	
		//点击上传附加按钮
		function clickFile(){
			document.getElementById("uploadFile").click();
		}
    
	  	//ajax方式上传附件
		function upFile(){
			//获取附件内容
			var formData = new FormData($("#uploadForm")[0]);
			
			//上传附件
			$.ajax({
	            url: '/wankangyuan/projectFloderFile/upload' ,
	            type: 'POST',
	            data: formData,
	            dataType:'json',
	            cache: false,
	            contentType: false,
	            processData: false,
	            success: function (data) {
	                if(data.result == true){
	                	var fileUploadList = $("#fileUploadList");
	                	$("#uploadFile").val("");
	                	fileUploadList.append(
	                		'<div class="fileaddMm" id=\"'+data.id+'\">'+
	                            '<img src="/wankangyuan/static/img/file.png" height="20" width="16" alt="" class="fileaddMi" />'+
	                            '<div class="fileaddMz addMtzname">'+data.originalFilename+'</div>'+
	                            '<div class="fileaddMz addMtzsize">'+Math.ceil(data.size)+' KB</div>'+
	                            '<div class="fileaddMz2 addMtzopea" onclick="removeFiv(\''+data.id+'\')">取消</div>'+
	                        '</div>'
	                	);
	                	ids.push(data.id);
	                }else{
	                	layer.msg(data.message);
	                }
	            },
	            error: function () {
	            	layer.msg("联网失败");
	            }
	        });
		}
	  	
		/* Array.prototype.removeByValue = function(val) {
			for(var i=0; i<this.length; i++) {
			  if(this[i] == val) {
			    this.splice(i, 1);
			    break;
			  }
			}
		} */
	  	
	  	//取消文件上传，移除掉文件数据
	  	function removeFiv(id){
	  		var box = document.getElementById(id);
	  		box.parentNode.removeChild(box);
	  		ids.removeByValue(id);
	  	}
	  	
	  	//提交一组文件，并将文件关系绑定到文件夹-文件关系表中
	  	function upFiles(){	  		
	  		var files = ids.join(",");
	  		if(files == ""){
	  			layer.msg("请添加文件");
	  			return;
	  		}
	  		//进行ajax请求
	  		$.ajax({
	  			url:"/wankangyuan/projectFloderFile/upFiles",
	  			type:"post",
	  			data:{
	  				ids:files,
	  				floder_id:floder_id
	  			},
	  			dataType:"json",
	  			success : function(data){
	  				if(data.result == true){
	  					window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
	  				}else{
	  					layer.msg(data.message);
	  				}
	  			},
	  			error : function(){
	  				layer.msg("联网失败");
	  			}
	  		});	  	
	  	}
	  	
    	//删除一组文件
    	function deleteFiles(){
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
            	layer.msg("请勾选文件");
            	return;
            }
            //网络请求删除一组文件
            $.ajax({
            	url:"/wankangyuan/projectFloderFile/deleteFiles",
            	type:"post",
            	data:{
            		ids:ids.join(",")
            	},
            	dataType:"json",
            	success : function(data){
            		if(data.result == true){
            			layer.msg("文件删除成功");
            			window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
            		}else{
            			layer.msg(data.message);
            			window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
            		}
            	},
            	error : function(){
            		layer.msg("联网失败");
            	}
            	
            });
    	}
    	function filesearch(){
    		//进行网络请求显示右面的文件夹
       		$.ajax({
       			url:"/wankangyuan/projectFloderFile/selectFiles",
       			type:"post",
       			data:{
       				searchWord:$("#filersercher").val()
       			},
       			dataType:"json",
       			success : function(data){
       				consule.log(data)
       				if(data.result == true){
       					//这个地方直接更新右面的文件列表
       					var filesList = $("#filesList");
       					filesList.empty();
       					for(var index in data.projectFiles){
       						if(data.projectFiles[index].id != null){
           						filesList.append(
           							'<div class="prof_lbRmULmLI">'+
       	                                '<div class="fuxuanK2">'+
       	                                	'<input type="checkbox" class="input_check" id="check'+data.projectFiles[index].id
       	                                		+'" name="'+data.projectFiles[index].file_location+'" value="'+data.projectFiles[index].id+'">'+
       	                                	'<label for="check'+data.projectFiles[index].id+'"></label>'+
   	    	                           	'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt2">'+data.projectFiles[index].file_name+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt3">'+data.projectFiles[index].file_type+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt4">'+data.projectFiles[index].file_size+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt5">'+data.projectFiles[index].create_datetime+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt6">'+data.projectFiles[index].username+'</div>'+
   	                                 <c:if test="${authoritys['26'] == true }">
   	                                    '<a style="color:#33B7FF" href="'+data.fileDownloadLocation+data.projectFiles[index].file_location+'" download="'+data.projectFiles[index].file_name+'">下载</a>'+
   	                                 </c:if>
           							'</div>');
       						}
       					}
       					project0();
       				}else{
       					layer.msg(data.message);
       				}
       			},
       			error : function(){
       				layer.msg("联网失败");
       			}
       		});  
    	}
   	   $(".search2Ct").bind("keypress" , function(event){
      		if(event.keyCode == 13){
      			
      		//进行网络请求显示右面的文件夹
       		$.ajax({
       			url:"/wankangyuan/projectFloderFile/selectFiles",
       			type:"post",
       			data:{
       				searchWord:this.value
       			},
       			dataType:"json",
       			success : function(data){
       				if(data.result == true){
       					//这个地方直接更新右面的文件列表
       					var filesList = $("#filesList");
       					filesList.empty();
       					for(var index in data.projectFiles){
       						if(data.projectFiles[index].id != null){
       							
       						
           						filesList.append(
           							'<div class="prof_lbRmULmLI">'+
       	                                '<div class="fuxuanK2">'+
       	                                	'<input type="checkbox" class="input_check" id="check'+data.projectFiles[index].id
       	                                		+'" name="'+data.projectFiles[index].file_location+'" value="'+data.projectFiles[index].id+'">'+
       	                                	'<label for="check'+data.projectFiles[index].id+'"></label>'+
   	    	                           	'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt2">'+data.projectFiles[index].file_name+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt3">'+data.projectFiles[index].file_type+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt4">'+data.projectFiles[index].file_size+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt5">'+data.projectFiles[index].create_datetime+'</div>'+
   	                                    '<div class="prof_lbRmULmli prof_lbRmULt6">'+data.projectFiles[index].username+'</div>'+
   	                                 <c:if test="${authoritys['26'] == true }">
   	                                    '<a style="color:#33B7FF" href="'+data.fileDownloadLocation+data.projectFiles[index].file_location+'" download="'+data.projectFiles[index].file_name+'">下载</a>'+
   	                                 </c:if>
           							'</div>');
       						}
       					}
       					project0();
       				}else{
       					layer.msg(data.message);
       				}
       			},
       			error : function(){
       				layer.msg("联网失败");
       			}
       		});  
	
      		}
       	});
   	   
   	   	//单击文件夹，改变文件夹的颜色，并网络请求文件夹的中所包含的文件
		function clickFloder(id){
			resetVal();
			var aPJliB1Lt=document.querySelectorAll('.PJliB1Lt');
			var awenjian=[];
			for(var i=0;i<aPJliB1Lt.length;i++){
			    awenjian.push(aPJliB1Lt[i]);
			}
			var aPJliB2Lt=document.querySelectorAll('.PJliB2Lt');
			for(var i=0;i<aPJliB2Lt.length;i++){
			    awenjian.push(aPJliB2Lt[i]);
			}
			
			var oprof_editK=document.querySelectorAll('.prof_editK')[0];
            if(oprof_editK){
                var oprof_aeMzp=oprof_editK.querySelectorAll('.prof_aeMzp')[0];
                //console.log(oprof_aeMzp);
            }
			for(var j=0;j<awenjian.length;j++){
			    awenjian[j].style.color="#666";
			}
			document.getElementById("floder"+id).style.color="#5ca0e5";
			if(oprof_aeMzp){
                oprof_aeMzp.value=document.getElementById("floder"+id).innerHTML;
                //console.log(documentnt.getElementById("floder"+id).innerHTML);
            }

			//console.log(awenjian);
			
			
			
			floder_id = id;
			console.log(floder_id+":111111");
    		//进行网络请求显示右面的文件夹
    		$.ajax({
    			url:"/wankangyuan/projectFloderFile/selectFilesByFloderId",
    			type:"post",
    			data:{
    				floder_id:id
    			},
    			dataType:"json",
    			success : function(data){
    				if(data.result == true){
    					//这个地方直接更新右面的文件列表
    					var filesList = $("#filesList");
    					filesList.empty();
    					for(var index in data.projectFiles){
    						if(data.projectFiles[index].id != null){
    							
        						filesList.append(
        							'<div class="prof_lbRmULmLI">'+
    	                                '<div class="fuxuanK2">'+
    	                                	'<input type="checkbox" class="input_check" id="check'+data.projectFiles[index].id
    	                                		+'" name="'+data.projectFiles[index].file_location+'" value="'+data.projectFiles[index].id+'">'+
    	                                	'<label for="check'+data.projectFiles[index].id+'"></label>'+
	    	                           	'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt2">'+data.projectFiles[index].file_name+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt3">'+data.projectFiles[index].file_type+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt4">'+data.projectFiles[index].file_size+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt5">'+data.projectFiles[index].create_datetime+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt6">'+data.projectFiles[index].username+'</div>'+
	                                    <c:if test="${authoritys['26'] == true }">
	                                    '<a style="color:#33B7FF" href="'+data.fileDownloadLocation+data.projectFiles[index].file_location+'" download="'+data.projectFiles[index].file_name+'">下载</a>'+
	                                    </c:if>
        							'</div>');
    						}
    					}
       					project0();
    				}else{
    					layer.msg(data.message);
    				}
    			},
    			error : function(){
    				layer.msg("联网失败");
    			}
    		});
		}
   	   	
   	   	//点击文件夹加减号
   	   	function clickShow(id){
   	   		var temp = document.getElementById("show"+id);
	   	   	if(temp.className.indexOf("PJliBLi2")!=-1){
	   	   		temp.className="PJliBLi PJliBLi1";
	            var ofufu=temp.parentNode.parentNode;
	            var ozizi=ofufu.querySelectorAll('.PJliBR')[0];
	            ozizi.style.display="none";
 	            
	        }else if(temp.className.indexOf("PJliBLi1")){
	        	//网络请求子文件夹并填充子文件夹块
	        	$.ajax({
	        		url:"/wankangyuan/projectFloderFile/getChildFloderByParentFloderId",
	        		type:"post",
	        		data:{
	        			parent_id:id
	        		},
	        		dataType:"json",
	        		success : function(data){
	        			if(data.result == true){
	        				
	        				var content = "";
	        				for(var index in data.msg){
	        					
	        					if(data.msg[index].id != null){
	        						var PJliB2Content = '<div class="PJliB2">';
	            					PJliB2Content += '<div class="PJliB2L" >';
	                					PJliB2Content += '<div class="PJliB2Lic" ></div>';
	                					PJliB2Content += '<div class="PJliB2Lt" id="floder'+data.msg[index].id+'" onclick="clickFloder('+data.msg[index].id+')">'+data.msg[index].floder_name+'</div>';
	                					PJliB2Content += '<div class="PJliBLi PJliBLi1" id="show'+data.msg[index].id+'" onclick="clickShow('+data.msg[index].id+')"></div>';
	            					PJliB2Content += '</div>';
	            					PJliB2Content += '<div class="PJliBR"></div>';
	            					PJliB2Content += '</div>';
	            					content += PJliB2Content;
	        					}
	        				}
	
	        				temp.className="PJliBLi PJliBLi2";
	                        var ofufu=temp.parentNode.parentNode;
	                        var ozizi=ofufu.querySelectorAll('.PJliBR')[0];
	                        ozizi.innerHTML=content;
	                        ozizi.style.display="inline-block";
	        			}
	        		},
	        		error : function(){
	        			layer.msg("联网失败");
	        		}
	        	});
	        }
   	   	}
   	   	
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
    		
    		if(columnName == "file_name"){
    			$("#fileNameGl").val(filter);
            }else if(columnName == "file_type"){
            	$("#fileTypeGl").val(filter);
            }else if(columnName == "file_size"){
            	$("#fileSizeGl").val(filter);
            }else if(columnName == "create_datetime"){
            	$("#createDatetimeGl").val(filter);
            }else if(columnName == "creator"){
            	$("#creatorGl").val(filter);
            }
    		
    		var fileName = $("#fileName").val();
            var fileType = $("#fileType").val();
            var fileSize = $("#fileSize").val();
            var createDatetime = $("#createDatetime").val();
            var creator = $("#creator").val();
            
            var fileNameGl=$("#fileNameGl").val();
            var fileTypeGl = $("#fileTypeGl").val() || '${fileTypeGl}';
            var fileSizeGl = $("#fileSizeGl").val() || '${fileSizeGl}';
            var createDatetimeGl = $("#createDatetimeGl").val() || '${createDatetimeGl}';
            var creatorGl = $("#creatorGl").val() || '${creatorGl}';
            
            console.log(floder_id+":1222222");
    		$.ajax({
    			url:"/wankangyuan/projectFloderFileFilter/getColumns",
    			type:"post",
    			data:{
    				columnName : columnName,
    				filter : filter,
    				floderId : floder_id,
    				fileName : fileName,
    				fileType : fileType,
    				fileSize : fileSize,
    				createDatetime : createDatetime,
    				creator : creator
    			},
    			dataType:"json",
    			success : function(data){
    				if(data.result == true){
    					var BTSXcli2 = document.getElementById("BTSXcli2");//填充筛选值
    					var BTSXcli2_html = "";
    					var isnull=false;
    					for(var index in data.message){
    						if(data.message[index] != ''){
    							BTSXcli2_html+='<div class="BTSXcli2li">';
    							if(vals.indexOf(data.message[index]) != -1){//如果筛选值在之前的筛选中被勾选了，再次选中它
    								BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" checked="checked" name="'+data.message[index]+'"/>';
    							}else{
    								BTSXcli2_html+='<input type="checkbox" class="BTSXcli2liC" name="'+data.message[index]+'"/>';
    							}
        						
        						BTSXcli2_html+='<div class="BTSXcli2liT">'+data.message[index]+'</div>';
        						BTSXcli2_html+='</div>';
    						}else{
    							isnull=true;
    						}
    					}
    					BTSXcli2.innerHTML=BTSXcli2_html;
    					var filesList = $(".BTSXcli2");
    					if(isnull){
    						filesList.prepend('<div class="BTSXcli2li" id="isnull">'+
    			                       ' <input name="空值" type="checkbox" class="BTSXcli2liC" value="空值" />'+
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
    		$(".BTSXcliGLK").val("")
    	}
    	
    	//点击筛选按钮，请求筛选接口
    	function shaixuan(){
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
           
            if(columnName == "file_name"){
            	$("#fileName").val(values);
            }else if(columnName == "file_type"){
            	$("#fileType").val(values);
            }else if(columnName == "file_size"){
            	$("#fileSize").val(values);
            }else if(columnName == "create_datetime"){
            	$("#createDatetime").val(values);
            }else if(columnName == "creator"){
            	$("#creator").val(values);
            }
           
            var fileName = $("#fileName").val();
            var fileType = $("#fileType").val();
            var fileSize = $("#fileSize").val();
            var createDatetime = $("#createDatetime").val();
            var creator = $("#creator").val();
           
            var fileNameGl=$("#fileNameGl").val();
            var fileTypeGl = $("#fileTypeGl").val() || '${fileTypeGl}';
            var fileSizeGl = $("#fileSizeGl").val() || '${fileSizeGl}';
            var createDatetimeGl = $("#createDatetimeGl").val() || '${createDatetimeGl}';
            var creatorGl = $("#creatorGl").val() || '${creatorGl}';
         	
            $("#isFilter").val("true");//设置当前为筛选状态
            //进行网络请求显示右面的文件夹
       		$.ajax({
       			url:"/wankangyuan/projectFloderFileFilter/selectFilesByFilterCondition",
       			type:"post",
       			data:{
       				floderId : floder_id,
       				searchWord : searchWord,
       				columnName : columnName,
       				order : order,
       				filter : filter,
       				values : values,
       				isFilter : 'true',
       				fileName : fileName,
       				fileType : fileType,
       				fileSize : fileSize, 
       				createDatetime : createDatetime,
       				creator : creator
       			},
       			dataType:"json",
       			success : function(data){
       				console.log(data)
       				if(data.result == true){
    					//这个地方直接更新右面的文件列表
    					var filesList = $("#filesList");
    					filesList.empty();
    					for(var index in data.projectFiles){
    						if(data.projectFiles[index].id != null){
        						filesList.append(
        							'<div class="prof_lbRmULmLI">'+
    	                                '<div class="fuxuanK2">'+
    	                                	'<input type="checkbox" class="input_check" id="check'+data.projectFiles[index].id
    	                                		+'" name="'+data.projectFiles[index].file_location+'" value="'+data.projectFiles[index].id+'">'+
    	                                	'<label for="check'+data.projectFiles[index].id+'"></label>'+
	    	                           	'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt2">'+data.projectFiles[index].file_name+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt3">'+data.projectFiles[index].file_type+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt4">'+data.projectFiles[index].file_size+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt5">'+data.projectFiles[index].create_datetime+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt6">'+data.projectFiles[index].username+'</div>'+
	                                    <c:if test="${authoritys['26'] == true }">
	                                    '<a style="color:#33B7FF" href="'+data.fileDownloadLocation+data.projectFiles[index].file_location+'" download="'+data.projectFiles[index].file_name+'">下载</a>'+
	                                    </c:if>
        							'</div>');
    						}
    					}
       					project0();
    				}else{
    					layer.msg(data.message);
    				} 
       			},
       			error : function(data){
       				console.log(data)
       				layer.msg("联网失败");
       			}
       		}); 
    	}
    	//点击筛选按钮
    	$(".BTSXcli3BTent").click(function(){
    		shaixuan();//1代表第一页
    	}); 
    	
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
    		
    		
    		 var fileName = $("#fileName").val();
             var fileType = $("#fileType").val();
             var fileSize = $("#fileSize").val();
             var createDatetime = $("#createDatetime").val();
             var creator = $("#creator").val();
             var fileSize = $("#fileSize").val();
             resetVal();
           	
           	clickFloder(floder_id)
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
    	function resetVal(){
    		$("#fileName").val("");
           	$("#fileType").val("");
           	$("#fileSize").val("");
           	$("#createDatetime").val("");
           	$("#creator").val("");
    	}
    	
    </script>
    
</body>
</html>