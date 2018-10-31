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
<script type="text/javascript">
	window.onload=function(){
		project0();
        project2();
        // pro_mine();
        // app_mine();
        app_public();
        // app_create();
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
                    <a href="/wankangyuan/application/viewMine2"><div class="top2Cli">我的</div></a>
                    <a href="/wankangyuan/application/viewCreate2"><div class="top2Cli">我创建的</div></a>
                    <a href="/wankangyuan/application/viewPublic2"><div class="top2Cli top2CliYJ">公共</div></a>
                    <div class="search">
                        <div class="searchC">
                            <img src="<%=request.getContextPath()%>/static/img/search.png" alt="" class="searchCi" data-bind="click:searchAppList" />
                            <input name="appName" type="text" class="searchCt" data-bind="event: { keyup: judgeSearchAppList}" placeholder="搜索应用" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="shaixuan">
                <div class="shaixuanC">
                    <div class="listZT">
                        <a href="/wankangyuan/application/viewPublic">
                            <div class="listZTli listZT1 active">
                                <img src="<%=request.getContextPath()%>/static/img/listZT1.png" alt="" class="listZT1i" />
                                <img src="<%=request.getContextPath()%>/static/img/listZT1.png" alt="" class="listZT1i" />
                            </div>
                        </a>
                        <a href="javascript:;">
                            <div class="listZTli listZT2">
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
                            <input type="checkbox" class="input_check" id="check0" data-bind="click: checkAll" >
                            <label for="check0"></label>
                        </div>
                        <div class="allT">全选</div>
                    </div>
               
                    <div class="pro_menu app_addtomine" data-bind="click:addToMine" >添加至我的</div>
                    
                </div>
                <div class="app_typeul" data-bind="foreach:{data:appTypeList, as:'appType'}">
                	<div class="app_typeli" data-bind="text:appType,click:$root.filtrateAppType"></div>
                </div>
                <div class="pro_addul">
                    <div class="pro_addli">项目1</div>
                    <div class="pro_addli">项目2</div>
                    <div class="pro_addli">项目3</div>
                    <div class="pro_addli">项目4</div>
                    <div class="pro_addli">项目5</div>
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
                        <div class="shaixuanZKliT">状态</div>
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
                <form id="appList" method="post">
                <div data-bind="foreach:appList">
                    <div class="PJK2li">
	                    <div class="PJK2litop">
	                        <div class="PJK2litopT2 PJliCli_1" data-bind="text:appName"></div>
	                        <!-- <div class="PJK2litopI"></div> -->
	                        <div class="fuxuanK3">
	                            <input name="ids" type="checkbox" class="input_check" data-bind="value: id,attr:{id:'check'+($index()+1)},event: { change: $root.checkOne}">
	                            <label data-bind="attr:{for:'check'+($index()+1)}"></label>
	                        </div>
	                    </div>
	                    <div class="PJK2licre PJliCli_1">
	                        <div class="PJK2licreT1">创建人：</div>
	                        <div class="PJK2licreT2" data-bind="text:creator"></div>
	                    </div>
	                    <div class="PJK2litime PJliCli_1">
                            <div class="PJK2licreT1">状态：</div>
                            <div class="PJK2licreT2">
                                <!-- ko if: "${user.username}" == creator -->我创建的<!-- /ko -->
                            	<!-- ko if: "${user.username}" != creator -->
                                	<!-- ko if: 0 == isDisplay -->私有<!-- /ko -->
                            		<!-- ko if: 1 == isDisplay -->公开<!-- /ko -->
                            	<!-- /ko -->
                            </div>
                        </div>
	                    <div class="PJK2litime PJliCli_1">
	                        <div class="PJK2litimeT1" data-bind="text:createTime"></div>
	                    </div>
	                    <div class="PJK2lidetail PJliCli_1" data-bind="text:appIntro"></div>
	                    <a data-bind="attr:{href:'/wankangyuan/application/explain?id='+id}" class="PJK2liex">应用说明</a>
	                </div>
                </div>
                </form>
            </div>

            <div class="pageK" id="box" ></div>

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
    
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/paging.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/layer/layer.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/knockout-3.4.2.js"></script>
<c:if test="${not empty msg}">
    <script type="text/javascript">
    layer.msg("${msg}");
    </script>
</c:if>
<script type="text/javascript">

//定义ViewModel
function ViewModel() {
	var self = this;
	var page,rows,total,appName,appType,orderName,orderDir,field,option;
	self.appList = ko.observableArray();
	self.appCentAll = ko.observableArray();
    self.appCart = ko.observableArray();
	self.showAppList = function() {
        
		$.getJSON("/wankangyuan/application/getPublic",{
			page:page,
			appName:self.appName,
			appType:self.appType,
			orderName:self.orderName,
			orderDir:self.orderDir,
			},function(data){
			page = data.page;
			rows = data.rows;
			total = data.total;
			self.appList.removeAll();
			for (var i in data.list){
                self.appList.push(data.list[i]);
            }
			project0();
	        project2();
	        app_public();
			$('#box').paging({
			    initPageNo: page, // 初始页码
			    totalPages: Math.ceil(total/rows), //总页数
			    totalCount: '合计&nbsp;' + self.total + '&nbsp;条数据', // 条目总数
			    slideSpeed: 600, // 缓动速度。单位毫秒
			    jump: true, //是否支持跳转
			    callback: function(page_) { // 回调函数
			        if(page_!=page){
			           page = page_;
			           self.showAppList();
			        }
			    }
			});
			$.getJSON("/wankangyuan/application/getPublic",{
				page:1,
                rows:1000,
	            appName:self.appName,
	            appType:self.appType,
	            orderName:self.orderName,
	            orderDir:self.orderDir,
	            },function(data){
	            	self.appCentAll.removeAll();
	                for (var i in data.list){
	                    self.appCentAll.push(data.list[i].id);
	                }
	                $(".input_check").each(function(){
	                    var index = $.inArray(Number($(this).val()),self.appCart());
	                    if(index >= 0){
	                        $(this).attr("checked",true);
	                    }
	                });
	            });
			
		});
	}
	
	//全选
    self.checkAll = function(){
        if($("#check0").attr('checked')){
            self.appCart.removeAll();
            for (var i in self.appCentAll()){
                self.appCart.push(self.appCentAll()[i]);
            }
        }else{
            self.appCart.removeAll();
        }
        return true;
    }
    //复选
    self.checkOne = function(option){
        //console.log(option.id);
        var index = $.inArray(Number(option.id),self.appCart());
        if(index >= 0){
            self.appCart.remove(option.id);
        }else{
            self.appCart.push(option.id);
        }
        
        if(self.appCentAll().length == self.appCart().length){
            $("#check0").attr('checked',true);
        }else{
            $("#check0").attr('checked',false);
        }
    }
    
    self.addToMine = function() {
        if (self.appCart().length == 0) {
            return layer.msg("请至少选中一个",function(){}),!1;
        } else {
            $.ajax({
                type: "POST", 
                url: "/wankangyuan/userAppRelation/addToMine",
                data: {ids:self.appCart().join(",")}, //可选参数
                dataType: "json",
                success: function(result){
                    layer.msg(result.message, {
                        anim: 0,
                        end: function (index) {
                            window.location.reload();
                        }
                    });
                },
                error:function(result){
                    layer.msg(result.message, {
                        anim: 0,
                        end: function (index) {
                            window.location.reload();
                        }
                    });
                }
            });
        }
    }
    
    //边缘弹出
    /* layer.open({
      type: 1
      ,offset: 'lb' //具体配置参考：offset参数项
      ,content: '总计<div style="padding: 20px 80px;" data-bind="text:appCentAll().length"></div>'+
      '购物车<div style="padding: 20px 80px;" data-bind="text:appCart().length"></div>'
      ,btn: '关闭全部'
      ,btnAlign: 'c' //按钮居中
      ,shade: 0 //不显示遮罩
      ,yes: function(){
        layer.closeAll();
      }
    }); */
    
	//初始化列表
	self.showAppList();
	
	//点击搜索
	self.searchAppList = function() {
		page = 1;
		self.appCart.removeAll();
        $("#check0").attr('checked',false);
		self.appName = $("input[name='appName']").val();
		self.showAppList();
	}
	//回车搜索
	self.judgeSearchAppList = function(data, event) {
        if(event.keyCode == "13") {  
        	self.searchAppList();
        }  
    }
	//筛选搜索
	self.filterSearchAppList = function() {
		arr = [];
		$("input[name='option']:checked").each(function(i){  
			arr[i]=$(this).val(); 
		});  
        self.option = arr.join(",");
        self.appName = "";
        page = 1;
        self.showAppList();
	}
	//排序搜索
	self.orderAppList = function(order) {
		self.orderName = $(".BTSXpd").attr("order");
		self.orderDir = order;
		self.searchAppList();
	}
	//显示字段列表
	self.fieldList = ko.observableArray();
	self.searchField = function() {
		self.fieldList.removeAll();
		$.getJSON("/wankangyuan/application/getPublicAppFieldList",{field:$(".BTSXpd").attr("order"),content:$(".BTSXcliGLK").val()},function(data){
            for (var i in data){
            	if($(".BTSXpd").attr("order") == "is_async"){
					if(data[i].isAsync == '0') {
						data[i].isAsync = "同步"
					}
					if(data[i].isAsync == '1') {
						data[i].isAsync = "异步"
					}
				}
            	if($(".BTSXpd").attr("order") == "is_display"){
					if(data[i].isDisplay == '0') {
						data[i].isDisplay = "私有"
					}
					if(data[i].isDisplay == '1') {
						data[i].isDisplay = "公开"
					}
				}
                self.fieldList.push(data[i]);
            }
        });
	}
	$(".PJListli").click(function() {
		self.fieldList.removeAll();
	    $(".BTSXpd").val($(this).attr("name"));
	    $(".BTSXpd").attr("order",$(this).attr("order"));
	});
	
	/* -------------------------------------------- */
	//应用类型列表
	self.appTypeList = ko.observableArray();
	self.filtrateAppType = function(appType) {
        self.appType = appType;
        self.searchAppList();
    }
	self.getAppTypeList = function() {
		$.getJSON("/wankangyuan/application/getPublicAppTypeList",function(data){
            self.appTypeList.removeAll();
            for (var i in data){
                self.appTypeList.push(data[i]);
            }
		});
	}
	self.getAppTypeList();
}
var vm = new ViewModel();
ko.applyBindings(vm);

</script>
</body>
</html>