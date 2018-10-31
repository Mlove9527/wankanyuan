<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
<link rel="stylesheet" type="text/css" href="/wankangyuan/static/css/project1.css" />
<script type="text/javascript" src="/wankangyuan/static/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        project0();
        project2();
        pro_mine();
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
                    <a href="javascript:;" onclick="remove()"><div class="top2Cli top2CliYJ">我的</div></a>
                    <a href="/wankangyuan/project/selectCreatedProject?creator=1" onclick="remove()"><div class="top2Cli">我创建的</div></a>
                    <a href="/wankangyuan/project/selectPublicProject" onclick="remove()"><div class="top2Cli">公共</div></a>
                    <div class="search">
                        <div class="searchC">
                            <img src="/wankangyuan/static/img/search.png" alt="" class="searchCi" />
                            <input type="text" class="searchCt"  placeholder="搜索项目" value="${projectSearchWord}"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="shaixuan">
                <div class="shaixuanC">
                    <div class="listZT">
                        <a href="<%=request.getContextPath()%>/jsp/project/project_mine.jsp">
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
                        <div class="shaixuanBTiK" >
                            <img src="<%=request.getContextPath()%>/static/img/sanjiao_blue.png" alt="" class="shaixuanBTi" />
                        </div>
                    </div>
                    <div class="jiangeline"></div>
                    <div class="allK">
                         <div class="quanxuanK">
                            <input type="checkbox" class="input_check" id="check0">
                            <label for="check0"></label>
                        </div>
                        <div class="allT">全选</div>
                        <input type="hidden" id="all_value" value=${allValue}>
                    </div>
                    <div class="pro_menu pro_exit" onclick="exit()">退出</div>
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
                        <div class="shaixuanZKliT">应用描述</div>
                    </div>
                </div>
            </div>
            <div class="PJK2">
                <c:forEach items="${projects}" var="project">
            	
		            <div class="PJK2li">
	                    <div class="PJK2litop">
	                        <a href="/wankangyuan/project/getProjectDetail?id=${project.id}">
	                            <div class="PJK2litopT PJliCli_1">${project.p_name}</div>
	                        </a>
	                        <div class="fuxuanK3">
	                                <input type="checkbox" class="input_check input_checks" name="projectCheck" id="check${project.id}" value="${project.id}">
	                                <label for="check${project.id}"></label>
	                        </div>
	                    </div>
	                    <div class="PJK2licre PJliCli_1">
	                        <div class="PJK2licreT1">创建人：</div>
	                        <div class="PJK2licreT2">${project.creatorName }</div>
	                    </div>
	                    <div class="PJK2litime PJliCli_1">
	                        <div class="PJK2litimeT1">${project.create_datetime }</div>
	                    </div>
						<div class="PJK2lidetail PJliCli_1">${project.introduction }</div>
	                </div>    
	                	
                </c:forEach>
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
   
    	//退出项目
    	function exit(){
    		var searchWord = $(".searchCt").val();//搜索条件
    		var allChangMine2 = "";
    		if (sessionStorage.allChangMine2){
    			allChangMine2 = sessionStorage.allChangMine2;
			 }
    		var noChangId = "";
    		if (sessionStorage.noChangId){
    			noChangId = sessionStorage.noChangId;
			 }
    		
    		var afuxuanK=document.querySelectorAll('.fuxuanK3');
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
            	layer.msg("请勾选项目");
            	return;
            }
            layer.confirm('请确认退出所选项目？',{
            	btn:['确认','取消'],
            	icon:2
            },function(){
            	//退出项目
                $.ajax({
                	url:"/wankangyuan/project/exit2",
                	type:"post",
                	data:{
                		ids:ids.join(","),
                		searchWord : searchWord,
                		allValue : allChangMine2,
                		noChangId : noChangId
                	},
                	dataType:"json",
                	success : function(data){
                		if(data.result == true){
                			window.location.href="/wankangyuan/project/selectMyProject?type=2&user_id=1";
                		}else{
                			layer.msg(data.message);
                			window.location.href="/wankangyuan/project/selectMyProject?type=2&user_id=1";
                		}
                	},
                	error : function(){
                		layer.msg("联网失败");
                	}
                });	
            },function(){
            	return;
            }); 
    	}
    	
    	$('#box').paging({
            initPageNo: ${page}, // 初始页码
            totalPages: Math.ceil(${total}/${rows}), //总页数
            totalCount: '合计&nbsp;' + ${total} + '&nbsp;条数据', // 条目总数
            slideSpeed: 600, // 缓动速度。单位毫秒
            jump: true, //是否支持跳转
            callback: function(page) { // 回调函数
            	var user_id=${user.id};
                if(page!=${page}){
                	var searchWord = $(".searchCt").val();
                    window.location.href="/wankangyuan/project/selectMyProject?user_id="+user_id+"&page="+page+"&strip=${rows}&type=2&searchWord="+searchWord+"&allValue="+$("#all_value").val();
                }
            }
        });
    	
    	$(".searchCt").bind("keypress" , function(event){
    		if(event.keyCode == 13){
    			var user_id=${user.id};
    			window.location.href="/wankangyuan/project/selectMyProject?user_id="
    					+user_id+"&type=2&searchWord="+this.value;
    			
    		}
    	});
    	
    	$("#check0").click(function(){
    		if($("#check0").is(":checked")){
    			$("#all_value").val("all");
    			sessionStorage.setItem("noChangId",'');
    			sessionStorage.setItem("allChangMine2",'true');
    		}else{
    			$("#all_value").val("");
    			sessionStorage.setItem("changId",'');
    			sessionStorage.setItem("allChangMine2",'false');
    		}
    	});
    	
    	 $(function() {
    		 var arrs = []
    		 var narrs = []
    		var allValue = $("#all_value").val();
    		 if(sessionStorage.allChangMine2 == 'true'){
    			 $("#check0").attr("checked",true);
    			 $(".input_checks").each(function () {
    				 $(this).attr('checked',true)    			 
	    			 if (sessionStorage.noChangId){
						 if (sessionStorage.noChangId.indexOf(',') != -1) {
							 narrs = sessionStorage.noChangId.split(',')
							 var _this = $(this)
	    	    			 $.each(narrs,function (i) {
	    	    				 if(_this.val() == narrs[i]) {
	    	    					  _this.attr('checked',false)
	    	    				 }
	    	    			 })
						 } else {
							 if($(this).val() == sessionStorage.noChangId) {
								 $(this).attr('checked',false)
							}
						 }
						 
		    			 
					 }
    			 
    		 })
    		 }
    		 
    		 
    	})
    	// 筛选是否选中
    	$(".input_checks").each(function () {
    		 $(this).click(function () {
    			 var id = $(this).val()
        		 var bol = false
        		 // 当前元素id
        		 var snarr = [] // session 中未选
     			 var narr = [] // 未选中
    			 // 更改选中状态
        			$(".input_checks").each(function () {
        				 if (sessionStorage.noChangId){
        					 snarr = sessionStorage.noChangId.split(',')
        				 }
        				 if (sessionStorage.changId){
                			 sarr = sessionStorage.changId.split(',')
        				 }
        			 if($(this).attr('checked') == 'checked') {
        				// 选中时从未选中列表删除此元素
            			 if (snarr.indexOf(id) != -1) {
            				 snarr.splice(snarr.indexOf(id),1)
            			 }
        			 } else{
        				 narr.push($(this).val())
        			 }
        			})
	       			 //设置未选中
	       			 var snarrs = Array.from(new Set(snarr.concat(narr)))
	       			 sessionStorage.setItem("noChangId",snarrs.toString());
    		 })
    	 })
    	 
    	  function remove(){
    		 sessionStorage.setItem("noChangId",'');
    		 sessionStorage.setItem("allChangMine2",'false');
    	 }
    
    </script>
</body>
</html>