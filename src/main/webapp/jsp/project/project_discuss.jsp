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
<script type="text/javascript" src="/wankangyuan/jsp/project/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        project0();
        // project1();
        // pro_mine();
        pro_discuss();
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
                    	  <a href="/wankangyuan/projectTopic/selectProjectTopic"><div class="top2Ctr active">讨论版</div></a>
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
                    	<a href="/wankangyuan//sourceData/getSourceDatas?type=4&p_id=${project.id}"><div class="top2Ctr">格式数据</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['27'] == true }">
                    	<a href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId"><div class="top2Ctr">文件</div></a>
                    </c:if>
                    
                    <c:if test="${authoritys['11'] == true }">
                    	<a href="/wankangyuan/project/getProjectDetail"><div class="top2Ctr ">基本信息</div></a>
                    </c:if>
                    
                    
                </div>
            </div>
            <div class="paixu">
                <div class="sortZ">
                    <div class="sortZc">
                        <div class="sortZt">时间:</div>
                       	<input type="radio" <c:if test="${timeRadio == 'desc' }">checked</c:if> value="desc" name="time" id="timeDesc"/>&nbsp;降&nbsp;&nbsp;
                       	<input type="radio" <c:if test="${timeRadio == 'asc' }">checked</c:if> value="asc" name="time" id="timeAsc"/>&nbsp;升
                    </div>
                </div>
                <div class="sortZ">
                    <div class="sortZc">
                        <div class="sortZt">回复:</div>
                       	<input type="radio" <c:if test="${followRadio == 'desc' }">checked</c:if> value="desc" name="follow" id="followDesc"/>&nbsp;降&nbsp;&nbsp;
                       	<input type="radio" <c:if test="${followRadio == 'asc' }">checked</c:if> value="asc" name="follow" id="followAsc"/>&nbsp;升
                    </div>
                </div>
                <div class="sortZ">
                    <div class="sortZc">
                        <div class="sortZt">查看:</div>
                       	<input type="radio" <c:if test="${lookRadio == 'desc' }">checked</c:if> value="desc" name="look" id="lookDesc"/>&nbsp;降&nbsp;&nbsp;
                       	<input type="radio" <c:if test="${lookRadio == 'asc' }">checked</c:if> value="asc" name="look" id="lookAsc"/>&nbsp;升
                    </div>
                </div>
                <a href="#"><div class="sortt">排序</div></a>
                
                <c:if test="${authoritys['70'] == true }">
                	<div class="addtheme">+创建主题</div>
                </c:if>
                <c:if test="${authoritys['70'] != true }">
                	<div class="addtheme" style="display:none;">+创建主题</div>
                </c:if>
                
            </div>
            <div class="themeK">
                <div class="themeT">
                    <div class="themeTt themeall">全部主题</div>
                    <div class="themeTt themeaut">作者</div>
                    <div class="themeTt themerep">回复/查看</div>
                    <div class="themeTt themelatime">最后发表时间</div>
                </div>
                <div class="themeM">
                
                	<c:forEach items="${projectTopics}" var="projectTopicTemp">
                		<div class="themeMz">
	                        <a href="/wankangyuan/projectTopic/selectProjectTopicFollow?project_topic_id=${projectTopicTemp.id}">
	                            <div class="themeMzt1 themeall">${projectTopicTemp.content}</div>
	                        </a>
	                        <div class="themeMzt2 themeaut">
	                            <div class="themeauthor">${projectTopicTemp.username}</div>
	                            <div class="themetime">${projectTopicTemp.create_datetime}</div>
	                        </div>
	                        <div class="themeMzt2 themerep">
	                            <span class="themehf">${projectTopicTemp.follow_up_num}</span>
	                            /
	                            <span class="themeck">${projectTopicTemp.look_num}</span>
	                        </div>
	                        <div class="themeMzt2 themelatime">${projectTopicTemp.last_datetime}</div>
	                    </div>
                	</c:forEach>

                </div>
                <div class="themeadd">
                    <div class="themeaddX"></div>
                    <div class="themeaddT">创建主题</div>
                    <div class="themeaddM">
                        <div class="themeaddMt">内容：</div>
                        <textarea name="content" id="content" class="themeaddMk" maxlength="600"></textarea>
                        <div class="themeaddMt2">600字以内</div>
                    </div>
                    <div class="themeaddB">
                        <div class="themeaddBc">取消</div>
                        <input type="button" class="themeaddBs" value="发送" />
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
	            if(page!=${page}){
	            	var timeRadio = $('input:radio[name="time"]:checked').val();
	    	    	var followRadio = $('input:radio[name="follow"]:checked').val();
	    	    	var lookRadio = $('input:radio[name="look"]:checked').val();
	    	    	
	                window.location.href="/wankangyuan/projectTopic/selectProjectTopic?page="
	                		+page+"&timeRadio="+timeRadio+"&followRadio="+followRadio+"&lookRadio="+lookRadio;
	            }
	        }
	    });
	    
	    $(".sortt").click(function (){
	    	var timeRadio = $('input:radio[name="time"]:checked').val();
	    	var followRadio = $('input:radio[name="follow"]:checked').val();
	    	var lookRadio = $('input:radio[name="look"]:checked').val();
            window.location.href="/wankangyuan/projectTopic/selectProjectTopic?timeRadio="+timeRadio+"&followRadio="+followRadio+"&lookRadio="+lookRadio;
	    });
    	
	    $(".themeaddBs").click(function (){
	    	
	    	var content = $("#content").val();
	    	var project_id = ${project.id};
	    	if(content == ""){
	    		layer.msg("内容不能为空");
	    		return;
	    	}
	    	$.ajax({
	    		url:"/wankangyuan/projectTopic/insertProjectTopic",
	    		type:"post",
	    		data:{
	    			content:content,
	    			project_id:project_id
	    		},
	    		dataType:"json",
	    		success : function(data){
	    			if(data.result == true){
	    				layer.msg("主题创建成功");
	    				window.location.href="/wankangyuan/projectTopic/selectProjectTopic";
	    			}else{
	    				layer.msg(data.message);
	    			}
	    		},
	    		error : function(){
	    			layer.msg("联网失败");
	    		}
	    	});
	    });
    	
    
    </script>
</body>
</html>