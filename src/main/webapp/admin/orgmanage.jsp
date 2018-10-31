<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="zhangfn" uri="http://github.com/zhangkaitao/tags/zhang-functions" %>
<!DOCTYPE html>
<!-- saved from url=(0056)http://themifycloud.com/demos/templates/janux/table.html -->
<html lang="en" class=" js inlinesvg">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!-- start: Meta -->

    <title>组织机构审批</title>
    <meta name="description" content="Bootstrap Metro Dashboard">
    <meta name="author" content="Dennis Ji">
    <meta name="keyword" content="Metro, Metro UI, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <!-- end: Meta -->

    <!-- start: Mobile Specific -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- end: Mobile Specific -->

    <!-- start: CSS -->
    <link id="bootstrap-style" href="<%=request.getContextPath()%>/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/admin/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link id="base-style" href="<%=request.getContextPath()%>/admin/css/style.css" rel="stylesheet">
    <link id="base-style-responsive" href="<%=request.getContextPath()%>/admin/css/style-responsive.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
    <!-- end: CSS -->


    <!-- start: Favicon -->
    <link rel="shortcut icon" href="http://themifycloud.com/demos/templates/janux/img/favicon.ico">
    <!-- end: Favicon -->
    <style type="text/css">
        .jqstooltip { 
            position: absolute;
            left: 0px;
            top: 0px;
            visibility: hidden;
            background: rgb(0, 0, 0) transparent;
            background-color: rgba(0,0,0,0.6);
            filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000);-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
            color: white;
            font: 10px arial, san serif;
            text-align: left;
            white-space: nowrap;
            padding: 5px;
            border: 1px solid white;
            z-index: 10000;
        }
        .jqsfield { 
            color: white;
            font: 10px arial, san serif;
            text-align: left;
        }
    </style>
    <style type="text/css">
    input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
    margin: 0;
    }
    </style>
</head>

<body style="cursor: auto;">
<!-- start: Header顶端 -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <!-- <a class="brand" href="index.html"><span>JANUX</span></a> -->
            <a class="brand" href="/wankangyuan/admin/viewUserManage"><img src="<%=request.getContextPath()%>/admin/img/newlogo2.png" height="70" width="218" alt="" /></a>

            <!-- start: Header Menu -->
            <div class="nav-no-collapse header-nav">
                <ul class="nav pull-right">
                    <!-- start: User Dropdown -->
                    <li class="dropdown">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="http://themifycloud.com/demos/templates/janux/table.html#">
                            <i class="halflings-icon white user"></i>用户名
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="dropdown-menu-title">
                                <span>账户设置</span>
                            </li>
                            <li>
                                <a href="/wankangyuan/admin/logout">
                                    <i class="halflings-icon off"></i>退出系统
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- end: User Dropdown -->
                </ul>
            </div>
            <!-- end: Header Menu -->
        </div>
    </div>
</div>
<!-- end: Header -->

<div class="container-fluid-full">
    <div class="row-fluid">

        <!-- start: Main Menu -->
        <div id="sidebar-left" class="span2">
            <div class="nav-collapse sidebar-nav">
                <ul class="nav nav-tabs nav-stacked main-menu">
                    <li>
                        <a href="/wankangyuan/admin/viewUserManage">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 用户管理</span>
                        </a>
                    </li>   
                    <li>
                        <a href="/wankangyuan/admin/viewRoleManage">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 角色管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="/wankangyuan/admin/viewResourceManage">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 菜单管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="/wankangyuan/admin/formatdata">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 格式数据管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="/wankangyuan/admin/SysConfigManage">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 系统配置</span>
                        </a>
                    </li>
                    <li>
                        <a href="/wankangyuan/adminProject/selectAdminProject">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 项目管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="/wankangyuan/projectRole/selectProjectRole">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 项目内角色管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="/wankangyuan/organization/viewOrgList">
                            <!-- <i class="icon-bar-chart"></i> -->
                            <span class="hidden-tablet"> 组织机构审批</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- end: Main Menu -->

        <noscript>
            &lt;div class="alert alert-block span10"&gt;
            &lt;h4 class="alert-heading"&gt;Warning!&lt;/h4&gt;
            &lt;p&gt;You need to have &lt;a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank"&gt;JavaScript&lt;/a&gt; enabled to use this site.&lt;/p&gt;
            &lt;/div&gt;
        </noscript>

        <!-- start: Content -->
        <div id="content" class="span10" style="min-height: 479px;">
            <ul class="breadcrumb">
                <li>
                    <i class="icon-home"></i>
                    <a href="/wankangyuan/admin/viewIndex">首页</a> 
                    <i class="icon-angle-right"></i>
                </li>
                <li>
                    <a href="/wankangyuan/organization/viewOrgList">组织机构审批</a>
                </li>
            </ul>

            <!-- 数据采集表start -->
            <div class="row-fluid sortable ui-sortable">
                <div class="box span12 datacollec">
                    <div class="box-header" data-original-title="">
                        <h2>
                            <i class="halflings-icon white user"></i>
                            <span class="break"></span>组织机构审批
                        </h2>
                    </div>
                    <div class="box-content">
                        <div class="box_xytab">
                            <div class="box_xytabz active">
                                <div class="box_xytabzK">
                                    <!-- <div class="box_xytabzT">Donor配置</div> -->
                                    <div class="tableedit">
                                        <div class="tableeditz2">
                                        <form method="get">
                                            <input name="organizationName" type="text" value="${organizationName }" class="tableeditz2p" placeholder="组织机构" />
                                            <input type="submit" class="tableeditz2b" value="搜索" />
                                        </form>
                                        </div>
                                        <div class="tableeditz tableeditzpass" onclick="dealOrgRequest(1)">通过</div>
                                        <div class="tableeditz tableeditzdeny" onclick="dealOrgRequest(0)">禁用</div>
                                    </div>
                                    <div class="tablebox">
                                        <table class="biaoge table-bordered">
                                            <div class="biaotou">
                                                <tr role="row">
                                                    <th class="biaotouth">
                                                        <input type="checkbox" class="quanxuan">全选
                                                    </th>
                                                    <th class="biaotouth">编号</th>
                                                    <th class="biaotouth">名称</th>
                                                    <th class="biaotouth">真实姓名</th>
                                                    <th class="biaotouth">创建者</th>
                                                    <th class="biaotouth">手机号</th>
                                                    <th class="biaotouth">单位信息</th>
                                                    <th class="biaotouth">创建时间</th>
                                                    <th class="biaotouth">状态</th>
                                                </tr>
                                            </div>
                                            <div class="biaoxiang">
                                            <c:forEach items="${list }" var="org" varStatus="orgList">
                                                <tr role="row" class="trbx">
                                                    <th class="biaoxiangth"><input id="check${orgList.count }" type="checkbox" name="ids" class="xuanze" value="${org.id }"></th>
                                                    <th class="biaoxiangth">${org.id }</th>
                                                    <th class="biaoxiangth">${org.organizationName }</th>
                                                    <th class="biaoxiangth">${org.realName }</th>
                                                    <th class="biaoxiangth">${org.creator }</th>
                                                    <th class="biaoxiangth">${org.phone }</th>
                                                    <th class="biaoxiangth">${org.unitInfo }</th>
                                                    <th class="biaoxiangth"><fmt:formatDate type="both" value="${org.createTime }" /></th>
                                                    <th class="biaoxiangth">
                                                        <c:if test="${'0' eq org.status }">禁用</c:if>
                                                        <c:if test="${'1' eq org.status }">正常</c:if>
                                                    </th>
                                                </tr>
                                            </c:forEach>
                                            </div>
                                        </table>
                                    </div>

                                    <div class="pageK" id="box" ></div>

                                </div>
                            </div>
                        </div>



                    </div>
                </div>
            </div>
            <!-- 数据采集表end -->

        </div><!--/.fluid-container-->
        <!-- end: Content -->
    </div><!--/#content.span10-->
</div><!--/fluid-row-->

<div class="modal hide fade" id="myModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Settings</h3>
    </div>
    <div class="modal-body">
        <p>Here settings can be configured...</p>
    </div>
    <div class="modal-footer">
        <a href="http://themifycloud.com/demos/templates/janux/table.html#" class="btn" data-dismiss="modal">Close</a>
        <a href="http://themifycloud.com/demos/templates/janux/table.html#" class="btn btn-primary">Save changes</a>
    </div>
</div>

<div class="clearfix"></div>

<footer>
    <p>
        <span style="text-align:left;float:left">© 2013 <a href="http://themifycloud.com/downloads/janux-free-responsive-admin-dashboard-template/" alt="Bootstrap_Metro_Dashboard">JANUX Responsive Dashboard</a></span>

    </p>
</footer>

<!-- start: JavaScript-->

<script src="<%=request.getContextPath()%>/admin/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/jquery-migrate-1.0.0.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery-ui-1.10.0.custom.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.ui.touch-punch.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/modernizr.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/bootstrap.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.cookie.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/fullcalendar.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.dataTables.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/excanvas.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/jquery.flot.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/jquery.flot.pie.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/jquery.flot.stack.js"></script>
<script src="<%=request.getContextPath()%>/admin/js/jquery.flot.resize.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.chosen.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.uniform.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.cleditor.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.noty.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.elfinder.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.raty.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.iphone.toggle.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.uploadify-3.1.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.gritter.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.imagesloaded.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.masonry.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.knob.modified.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/jquery.sparkline.min.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/counter.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/retina.js"></script>

<script src="<%=request.getContextPath()%>/admin/js/custom.js"></script>

<!-- end: JavaScript-->

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/paging.js"></script>

<c:if test="${not empty msg}">
 <script type="text/javascript">
 layer.msg("${msg}");
 </script>
</c:if>
<script type="text/javascript">


function dealOrgRequest(cmd){
    var obj = $("input[name='ids']");
    var checkNum = 0;
    var id;
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].checked) {
            checkNum++;
        }
    }
    arr = [];
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].checked) {
            arr.push(Number(obj[i].value));
        }
    }
    
    if (checkNum == 0) {
        layer.msg("请至少选中一个",function(){});
    } else {
    	
        $.post("/wankangyuan/organization/dealOrgRequest",{
            ids:arr.join(","),
            cmd:Number(cmd)
        },function(result){
        	if(result && result.status!= 200){
                return layer.msg(result.message,function(){}),!1;
            }else{
                layer.msg(result.message, {
                    anim: 0,
                    end: function (index) {
                        window.location.reload();
                    }
                });
            }
        },"json");
 
    }
}


$('#box').paging({
    initPageNo: ${page}, // 初始页码
    totalPages: Math.ceil(${total}/${rows}), //总页数
    totalCount: '合计&nbsp;' + ${total} + '&nbsp;条数据', // 条目总数
    slideSpeed: 600, // 缓动速度。单位毫秒
    jump: true, //是否支持跳转
    callback: function(page) { // 回调函数
        console.log(page);
        if(page!=${page}){
            window.location.href="/wankangyuan/organization/viewOrgList?page="+page+"&rows=${rows}&organizationName=${organizationName}";
           
        }
    }
});

</script>
</body>
</html>