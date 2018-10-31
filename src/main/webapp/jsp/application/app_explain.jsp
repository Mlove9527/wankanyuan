<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/project1.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<link href="<%=request.getContextPath()%>/static/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath()%>/static/umeditor/third-party/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/static/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/static/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/umeditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
    window.onload=function(){
        project0();
        // project1();
        // pro_mine();
        // app_mine();
    }
</script>
<body>
    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="<%=request.getContextPath()%>/static/img/newlogo2.png" height="70" width="218" alt="" class="logo" /></h1>
                <a href="/wankangyuan/project/selectMyProject">
                    <div class="topT">项目</div>
                </a>
                <a href="/wankangyuan/sourceData/firstIn?type=1">
                    <div class="topT">格式数据</div>
                </a>
                <a href="/wankangyuan/application/viewMine">
                    <div class="topT active">应用</div>
                </a>
                <div class="touxiangK">
                    <a href=" ">
                        <img src="${user.headimg }"  onerror='this.src="/wankangyuan/static/img/head.jpg"' class="touxiang" />
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
                    <div class="top2Cli top2CliYJ">
                        <!-- <div class="backbtnK1"> -->
                            <a href="javascript:history.go(-1);">
                                <div class="backbtn1"></div>
                            </a>
                        <!-- </div> -->
                        <div class="top2Ct">${application.appName }</div>
                    </div>
                </div>
            </div>
            <div class="appexpM">
                <div class="appexpML">
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">应用编号：</div>
                        <div class="appexpMLzt2">${application.id }</div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">应用名称：</div>
                        <div class="appexpMLzt2">${application.appName }</div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">应用类别：</div>
                        <div class="appexpMLzt2">${application.appType }</div>
                    </div>
                    <%-- <div class="appexpMLz">
                        <div class="appexpMLzt1">创建人：</div>
                        <div class="appexpMLzt2">${application.creator }</div>
                    </div> --%>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">关键字：</div>
                        <div class="appexpMLzt2">${application.keywords }</div>
                        <!-- <div class="appexpMLzt4">RNA</div> -->
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">版本：</div>
                        <div class="appexpMLzt2">${application.versions }</div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">是否存储系统：</div>
                        <div class="appexpMLzt2">
                            <c:if test="${0 eq application.isSaveSystem }">否</c:if>
                            <c:if test="${1 eq application.isSaveSystem }">是</c:if>
                        </div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">是否异步应用：</div>
                        <div class="appexpMLzt2">
                            <c:if test="${0 eq application.isAsync }">否</c:if>
                            <c:if test="${1 eq application.isAsync }">是</c:if>
                        </div>
                    </div>
                    <%-- <div class="appexpMLz">
                        <div class="appexpMLzt1">创建时间：</div>
                        <div class="appexpMLzt2">
                            <fmt:formatDate type="date" value="${application.createTime }" />
                        </div>
                    </div> --%>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">是否公开：</div>
                        <div class="appexpMLzt2">
                            <c:if test="${0 eq application.isDisplay }">否</c:if>
                            <c:if test="${1 eq application.isDisplay }">是</c:if>
                        </div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">计算节点名称：</div>
                        <div class="appexpMLzt2">${application.computeNodeName }</div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">参数地址：</div>
                        <div class="appexpMLzt3">${application.paraAddress }</div>
                    </div><div class="appexpMLz">
                        <div class="appexpMLzt1">结果地址：</div>
                        <div class="appexpMLzt3">${application.resultAddress }</div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">应用简介：</div>
                        <div class="appexpMLzt3">${application.appIntro }</div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">文件结果：</div>
                        <div class="appexpMLzt3">${application.fileResult }</div>
                    </div>
                    <div class="appexpMLz">
                        <div class="appexpMLzt1">文件结果地址：</div>
                        <div class="appexpMLzt3">${application.fileResultAddress }</div>
                    </div>
                </div>
                <div class="appexpMR" style="text-align:left;">
                <input id="description" name="description" type="hidden">
                    <!--style给定宽度可以影响编辑器的最终宽度-->
                    <script type="text/plain" id="myEditor" style="width:820px;height:700px;">
                        ${application.description}
                    </script>
                    
                    <script type="text/javascript">
                        //实例化编辑器
                        var um = UM.getEditor('myEditor');
                        um.setDisabled('fullscreen');
                    </script>
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
    
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>
<c:if test="${not empty msg}">
    <script type="text/javascript">
    layer.msg("${msg}");
    </script>
</c:if>
</body>
</html>