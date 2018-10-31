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
        app_mine();
        app_create();
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
                    <a href="/wankangyuan/application/viewCreate2"><div class="top2Cli top2CliYJ">我创建的</div></a>
                    <a href="/wankangyuan/application/viewPublic2"><div class="top2Cli">公共</div></a>
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
                        <a href="/wankangyuan/application/viewCreate">
                            <div class="listZTli listZT1 active">
                                <img src="<%=request.getContextPath()%>/static/img/listZT1.png"alt="" class="listZT1i" />
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
                        <div class="shaixuanBTiK">
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
                    
                    <div class="pro_menu pro_addK">
                        <div class="pro_addk">
                            <div class="pro_addT">添加至项目</div>
                            <div class="pro_addI"></div>
                        </div>
                    </div>

                    <!-- <div class="pro_menu pro_del" onclick="to_delete()">删除</div>
                    <div class="pro_menu pro_open" onclick="to_public()">公开</div>
                    <div class="pro_menu pro_disopen" onclick="to_private()">取消公开</div> -->
                    <div class="pro_menu pro_del" data-bind="click:to_delete">删除</div>
                    <div class="pro_menu pro_open" data-bind="click:to_public">公开</div>
                    <div class="pro_menu pro_disopen" data-bind="click:to_private" >取消公开</div>
                    <div class="pro_menu pro_createapp">+创建应用</div>
                </div>
                
                <div class="app_typeul" data-bind="foreach:{data:appTypeList, as:'appType'}">
                	<div class="app_typeli" data-bind="text:appType,click:$root.filtrateAppType"></div>
                </div>
                <div class="pro_addul">
                    <c:forEach items="${projectList}" var="project">
                    <div class="pro_addli" data-bind="click:function(){addToProjrct(${project.id})}" >${project.p_name }</div>
                </c:forEach>
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
                <div class="createappK">
                    <div class="inportT">
                        <div class="inportTt">创建应用</div>
                        <div class="inportTx"></div>
                    </div>
                    <form action="/wankangyuan/application/create2" method="post">
                        <div class="adddataM">
                            <div class="adddataMli">
                                <div class="adddataMlit2">应用名称：</div>
                                <input name="appName" type="text" class="adddataMliTT adddataMliT" />
                            </div>
                            <div class="adddataMli">
                                <div class="adddataMlit2">应用描述：</div>
                                <textarea name="appIntro" class="adddataMliTT adddataMliT3" ></textarea>
                            </div>
                        </div>
                        <input type="submit" class="inportB" value="创建" />
                    </form>
                </div>               
                <form id="appList" action="/wankangyuan/application/setStatus2" method="post">
                <div data-bind="foreach:appList">
                    <div class="PJK2li">
                        <div class="PJK2litop">
                            <div class="PJK2litopT2 PJliCli_1" data-bind="text:appName"></div>
                            <div class="fuxuanK3">
                                <input name="ids" type="checkbox" class="input_check" data-bind="value: id,attr:{id:'check'+($index()+1)},event: { change: $root.checkOne}">
                                <label data-bind="attr:{for:'check'+($index()+1)}"></label>
                            </div>
                        </div>
                        <div class="PJK2licre PJliCli_1">
                            <div class="PJK2licreT1">创建人：</div>
                            <div class="PJK2licreT2" data-bind="text:creator" ></div>
                        </div>
                        <div class="PJK2litime PJliCli_1">
                            <div class="PJK2licreT1">状态：</div>
                            <div class="PJK2licreT2">
                            <!-- ko if: 0 == isDisplay -->私有<!-- /ko -->
                        	<!-- ko if: 1 == isDisplay -->公开<!-- /ko -->
                            </div>
                        </div>
                        <div class="PJK2litime PJliCli_1">
                            <div class="PJK2litimeT1" data-bind="text:createTime"></div>
                        </div>
                        <div class="PJK2lidetail PJliCli_1" data-bind="text:appIntro"></div>
                        <a data-bind="attr:{href:'/wankangyuan/application/updateForm2?id='+id}" class="PJK2liex">应用说明</a>
                    </div>
                </div>
                <input id="pub_but" type="submit" name="cmd" value="公开" style="display:none">
                <input id="pri_but" type="submit" name="cmd" value="私有" style="display:none">
                <input id="projectId" name="projectId" type="hidden" disabled="disabled">
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
/* function filtrateAppType(appType){
    $("input[name='appType']").val(appType);
    $("#filterFrom").submit();
} */

/* function addToProjrct(projectId){
    var ids = $("input[name='ids']");
    var checkNum = 0;
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked) {
            checkNum++;
        }
    }
    if (checkNum == 0) {
        layer.msg("请至少选中一个");
    } else {
        layer.confirm('请确认是否添加?',{
          btn: ['确认','取消'], //按钮
          icon: 2
        }, function(){
            $("#appList").attr('action',"/wankangyuan/ProjectAppRelation/addToProject");
            $("#projectId").removeAttr("disabled");
            $("#projectId").val(projectId);
            $("#appList").submit();
          
        }, function(){
            return;
        });
    }
}

function to_public(){
    
    var ids = $("input[name='ids']");
    var checkNum = 0;
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked) {
            checkNum++;
        }
    }
    if (checkNum == 0) {
        layer.msg("请至少选中一个");
    } else {
        $("#pub_but").click();
    }
}

function to_private(){
    var ids = $("input[name='ids']");
    var checkNum = 0;
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked) {
            checkNum++;
        }
    }
    if (checkNum == 0) {
        layer.msg("请至少选中一个");
    } else {
        $("#pri_but").click();
    }
}

function to_delete(){
    var ids = $("input[name='ids']");
    var checkNum = 0;
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked) {
            checkNum++;
        }
    }
    if (checkNum == 0) {
        layer.msg("请至少选中一个");
    } else {
        layer.confirm('删除不能撤销，请确认是否删除?',{
          btn: ['确认','取消'], //按钮
          icon: 2
        }, function(){
            $("#appList").attr('action',"/wankangyuan/application/delete2");
            $("#appList").submit();
          
        }, function(){
            return;
        });
    }
} */

//定义ViewModel
function ViewModel() {
	var self = this;
	var page,rows,total,appName,appType,orderName,orderDir,field,option;
	self.appList = ko.observableArray();
	self.appCentAll = ko.observableArray();
    self.appCart = ko.observableArray();
	self.showAppList = function() {
		$.getJSON("/wankangyuan/application/getCreate",{
			page:page,
			appName:self.appName,
			appType:self.appType,
			orderName:self.orderName,
			orderDir:self.orderDir
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
	        app_mine();
	        app_create();
			$('#box').paging({
			    initPageNo: page, // 初始页码
			    totalPages: Math.ceil(total/rows), //总页数
			    totalCount: '合计&nbsp;' + self.total + '&nbsp;条数据', // 条目总数
			    slideSpeed: 600, // 缓动速度。单位毫秒
			    jump: true, //是否支持跳转
			    callback: function(page_) { // 回调函数
			        //console.log(page_);
			        if(page_!=page){
			           page = page_;
			           self.showAppList();
			        }
			    }
			});
			
			$.getJSON("/wankangyuan/application/getCreate",{
	            page:1,
	            rows:1000,
	            appName:self.appName,
	            appType:self.appType
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
	            })
			
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
    
    //添加到项目
    self.addToProjrct = function(projectId){
        if (self.appCart().length == 0) {
            layer.msg("请至少选中一个");
        } else {
            layer.confirm('请确认是否添加?',{
              btn: ['确认','取消'], //按钮
              icon: 2
            }, function(){
                $.ajax({
                    type: "POST", 
                    url: "/wankangyuan/ProjectAppRelation/addToProject",
                    data: {projectId:projectId, ids:self.appCart().join(",")}, //可选参数
                    dataType: "json",
                    success: function(result){
                        layer.msg(result.message, {
                            anim: 0,
                            end: function (index) {
                                window.location.href="/wankangyuan/project/selectMyProject";
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
                
            }, function(){
                return;
            });
        }
    }
    self.to_public = function(){
        if (self.appCart().length == 0) {
            layer.msg("请至少选中一个");
        } else {
            //$("#pub_but").click();
            $.ajax({
                type: "POST", 
                url: "/wankangyuan/application/setStatus",
                data: {cmd:1, ids:self.appCart().join(",")}, //可选参数
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
    
    self.to_private = function(){
        if (self.appCart().length == 0) {
            layer.msg("请至少选中一个");
        } else {
            //$("#pub_but").click();
            $.ajax({
                type: "POST", 
                url: "/wankangyuan/application/setStatus",
                data: {cmd:0, ids:self.appCart().join(",")}, //可选参数
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
    
    self.to_delete = function(){
        if (self.appCart().length == 0) {
            layer.msg("请至少选中一个");
        } else {
            //$("#pub_but").click();
            $.ajax({
                type: "POST", 
                url: "/wankangyuan/application/delete",
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
    });  */
		
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
	
	/* -------------------------------------------- */
	//应用类型列表
	self.appTypeList = ko.observableArray();
	self.filtrateAppType = function(appType) {
        self.appType = appType;
        self.searchAppList();
    }
	self.getAppTypeList = function() {
		$.getJSON("/wankangyuan/application/getMyAppTypeList",function(data){
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