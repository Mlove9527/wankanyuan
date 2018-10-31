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
        // project1();
        // pro_detail();
        pro_deappendxq();
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
                        <img src="${user.headimg }" alt="" class="touxiang" />
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
            <div class="backbtnK">
                <a href="javascript:history.go(-1);">
                    <div class="backbtn"></div>
                </a>
            </div>
            <div class="prodeappendxqT">13例结直肠癌病人的基因表达模式研究</div>
            <div class="prodexq2 active">
                <div class="prodexqL">
                    <div class="prodexqLk">
                        <div class="prodexLbk">
                            <input type="button" class="prodexLb" value="保存" />
                        </div>
                        <textarea name="" id=""class="prodexLt">病例对照研究是以现在确诊的患有某特定疾病的病人作为病例，以不患有该病但具有可比性的个体作为对照，通过询问，实验室检查或复查病史，搜集既往各种可能的危险因素的暴露史，测量并比较病例组与对照组中各因素的暴露比例，经统计学检验，若两组差别有意义，则可认为因素与疾病之间存在着统计学上的关联。</textarea>
                        <div class="sanjiao_left"></div>
                    </div>
                </div>
                <div class="prodexqR">
                    <div class="prodexqRk">
                        <!-- <div class="prodexqRbK">
                            <div class="prodexqRb active">
                                <img src="/wankangyuan/static/img/prodetail1_1.png" alt="" class="prodexqRbI" />
                                <div class="prodexqRbT">25例糖尿病患者的基因表达模式研究1</div>
                            </div>
                            <div class="prodexqRb">
                                <img src="/wankangyuan/static/img/prodetail1_2.png" alt="" class="prodexqRbI" />
                                <div class="prodexqRbT">25例糖尿病患者的基因表达模式研究2</div>
                            </div>
                            <div class="prodexqRb">
                                <img src="/wankangyuan/static/img/prodetail1_3.png" alt="" class="prodexqRbI" />
                                <div class="prodexqRbT">25例糖尿病患者的基因表达模式研究3</div>
                            </div>
                        </div>
                        <div class="prodexqRaK">
                            <div class="prodexqRa active"></div>
                            <div class="prodexqRa"></div>
                            <div class="prodexqRa"></div>
                        </div> -->
                        <img src="/wankangyuan/static/img/prodetail1.png" alt="" class="prodexqRbI" />
                    </div>
                </div>
            </div>
            <div class="prodexq2">
                <div class="sanjiao_right"></div>
                <div class="prodexqK">
                    <img src="/wankangyuan/static/img/prodetail2.png" alt="" class="prodexqKi" />
                </div>
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
</body>
</html>