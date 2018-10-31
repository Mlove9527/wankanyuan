<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<!-- saved from url=(0056)http://themifycloud.com/demos/templates/janux/table.html -->
<html lang="en" class=" js inlinesvg">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!-- start: Meta -->

    <title>系统配置</title>
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
                        <a href="/wankangyuan/admin/SysConfigManage">系统配置</a>
                    </li>
                </ul>

                <div class="row-fluid sortable ui-sortable">
                    <div class="box span12 datacollec">
                        <div class="box-header" data-original-title="">
                            <h2>
                                <i class="halflings-icon white user"></i>
                                <span class="break"></span>系统配置
                            </h2>
                        </div>
                        <div class="box-content">
                            <div class="sysconfig">
                            <form method="post">
                                <input name="id" type="hidden" value="${sysConfig.id }">
                                <div class="sysconfigZ">
                                    <div class="sysconfigZt">配置是否允许平台用户自己注册？</div>
                                    <div class="radiogroup">
                                        <input type="radio" class="sysconfigZr" name="isRegistrable" value="1" <c:if test="${'1' eq sysConfig.isRegistrable }">checked="checked"</c:if>  />是
                                        <input type="radio" class="sysconfigZr" name="isRegistrable" value="0" <c:if test="${'0' eq sysConfig.isRegistrable }">checked="checked"</c:if>  />否
                                    </div>
                                </div>
                                <div class="sysconfigZ">
                                    <div class="sysconfigZt">配置用户空间大小</div>
                                    <input name="size" type="text" class="sysconfigZk" value="${sysConfig.size }" />GB
                                </div>
                                <div class="sysconfigB">
                                    <input type="submit" class="sysconfigb" value="保存配置" />
                                </div>
                            </form>
                            </div>
                        </div>
                    </div>
                </div>

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

    

    <!-- <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script> -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/admin/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

	<c:if test="${not empty msg}">
	    <script type="text/javascript">
	    layer.msg("${msg}");
	    </script>
	</c:if>
    <!-- end: JavaScript-->

</body>
</html>