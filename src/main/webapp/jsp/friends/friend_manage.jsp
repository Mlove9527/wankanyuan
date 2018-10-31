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
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        project0();
        // project1();
        // pro_mine();
        // user_info();
        // system_message();
        friend_manage();
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
                    <div class="topT">应用</div>
                </a>
                <div class="touxiangK">
                    <a href="/wankangyuan/userInfo">
                        <img src="${user.headimg }" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="touxiang" />
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
                <div class="nicheng">${user.username }</div>
                <a href="/wankangyuan/friends/viewFriendsManage">
                    <div class="yanjiuquan active">
                        <div class="yanjiuquanT">研究圈</div>
                        <c:if test="${friendMSG}">
                            <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint" />
                        </c:if>
                    </div>
                </a>
            </div>
            <div class="top2">
                <div class="top2C">
                    <a href="/wankangyuan/friends/viewFriendsManage"><div class="top2Cli top2CliYJ">好友管理</div></a>
                    <a href="/wankangyuan/friendMessage/viewFriendMessage">
                        <div class="top2Cli">好友消息
                        <c:if test="${friendMSG}">
                            <img src="<%=request.getContextPath()%>/static/img/redpoint.png" height="11" width="11" alt="" class="redpoint3" />
                        </c:if>
                        </div>
                    </a>
                </div>
            </div>
            <div class="friendM">
                <div class="friendMT">
                    
                    <!-- 组织结构添加框 -->
                    <div class="zzjgK">
                    <form id="addNewOrgForm">
                        <div class="zzjgM">
                            <div class="zzjgMz">
                                <div class="zzjgMzt">组织结构名称</div>
                                <input name="organizationName" type="text" class="zzjgMzp" />
                            </div>
                            <div class="zzjgMz">
                                <div class="zzjgMzt">真实姓名</div>
                                <input name="realName" type="text" class="zzjgMzp" />
                            </div>
                            <div class="zzjgMz">
                                <div class="zzjgMzt">联系电话</div>
                                <input name="phone" type="text" class="zzjgMzp" />
                            </div>
                            <div class="zzjgMz">
                                <div class="zzjgMzt">单位简介</div>
                                <textarea name="unitInfo" class="zzjgMzta"></textarea>
                            </div>
                        </div>
                        <div class="zzjgB">
                            <input type="button" class="zzjgBb" onclick="addNewOrg()" value="提交审核" />
                        </div>
                    </form>
                    </div>

                    <!-- 组添加框 -->
                    <form id="addNewGroupForm">
                    <div class="zzsyK">
                        <div class="zzsyKT">添加组到组织结构</div>
                        <input id="formParentId" name="parentId" type="text" class="zzsyKpd" style="display:none;" />
                        <div class="zzsyM">
                            <div class="zzsyMz">
                                <div class="zzsyMzt">组名称：</div>
                                <input name="organizationName" type="text" class="zzsyMzp" />
                            </div>
                        </div>
                        <div class="zzsyB">
                            <input type="button" class="zzsyBb" onclick="addNewGroup()" value="确定" />
                        </div>
                    </div>
                    </form>
                    
                    <!-- 组修改框 -->
                    <form id="updateGroupForm">
                    <div class="zzsy_editK">
                        <div class="zzsyKT">修改组名称</div>
                        <input name="id" type="text" class="zzsy_editKpd" style="display:none;"/>
                        <div class="zzsyM">
                            <div class="zzsyMz">
                                <div class="zzsyMzt">组名称：</div>
                                <input id="updateGroupName" name="organizationName" type="text" class="zzsy_editMzp" />
                            </div>
                        </div>
                        <div class="zzsyB">
                            <input type="button" class="zzsyBb" onclick="updateGroup()" value="确定" />
                        </div>
                    </div>
                    </form>

                    <!-- 组删除框 -->
                    <form id="deleteGroupForm">
                    <div class="zzsy_delK">
                        <div class="zzsyKT">确定删除选中组吗？（删除后不可恢复！）</div>
                        <input name="id" type="text" class="zzsy_delKpd" style="display:none;" />
                        <div class="zzsyB">
                            <input type="button" class="zzsyBb2" value="确定" onclick="deleteGroup()" />
                        </div>
                    </div>
                    </form>

                    <!-- 添加成员框 -->
                    
                    <div class="chengyuan_addK">
                        <div class="cyaddKT">添加成员</div>
                        <form id="getUserByNameForm">
                        <div class="cyaddKT2">
                            <input name="isOrg" type="hidden" value="0">
                            <input id="addOrgerInput" name="username" type="text" class="cyaddKT2p" placeholder="输入要搜索的用户名" />
                            <input type="button" class="cyaddKT2b" value="搜索" data-bind="click:getFriendsToOrg" />
                        </div>
                        </form>
                        <form id="addOrgMembersForm">
                        <div class="cyaddKM">
                            <table class="cyaddKMtb">
                                <tr class="biaotou">
                                    <th class="touxiang">头像</th>
                                    <th class="yonghuming">用户名</th>
                                    <th class="youxiang">邮箱</th>
                                    <th class="caozuo">操作</th>
                                </tr>
                                <tbody data-bind="foreach: friends2">
                                <tr class="biaoxiang" >
                                    <th class="touxiang"><img data-bind="attr:{src: friendHeadimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="touxiangi" /></th>
                                    <th class="yonghuming" data-bind="text: friendName"></th>
                                    <th class="youxiang" data-bind="text: friendEmail"></th>
                                    <th class="caozuo">
                                        <input name="userIds" type="checkbox" class="caozuochk" data-bind="value: friendId" />
                                    </th>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="cyaddKB">
                            <!-- <div class="cyaddKBt">添加到</div> -->
                            <input id="groupId" type="hidden" name="groupId">
                            <%-- <select name="organizationId" id="" class="cyaddKBs">
                            <c:forEach items="${orgList }" var="org">
                            <c:if test="${0 ne org.parentId}">
                                <option value="${org.id }">
                                    <c:forEach items="${orgList }" var="par">
                                        <c:if test="${par.id eq org.parentId }">${par.organizationName } - ${org.organizationName }</c:if>
                                    </c:forEach>
                                </option>
                            </c:if>
                            </c:forEach>
                            </select> --%>
                            <input type="button" class="cyaddKBb" value="添加" onclick="addOrgMembers()" />
                        </div>
	                    </form>
                    </div>
                        
                    <!-- 群发消息框 -->
                    <div class="qunfaK">
                        <div class="qunfaT">
                            <div class="qunfaTt">群发消息</div>
                            <img src="<%=request.getContextPath()%>/static/img/close.png" alt="" class="qunfaTi" />
                        </div>
                        <div class="qunfaM">
                            <div class="qunfaMt">内容：</div>
                            <textarea name="content" class="qunfaMta" maxlength="600"></textarea>
                        </div>
                        <div class="qunfaB">
                            <input type="button" class="qunfaBb qunfaBb_cancel" value="取消" />
                            <input type="button" class="qunfaBb qunfaBb_send" value="发送" onclick="sendMassFriendMessage()" />
                        </div>
                    </div>

                    <!-- 从组中移除框 -->
                    <div class="zuyichuK">
                        <div class="zzsyKT">确定从组中移除选中成员吗？</div>
                        <input type="text" class="zzsy_delKpd" style="display:none" />
                        <div class="zzsyB">
                            <input type="button" class="zzsyBb2" value="确定" data-bind="click: removeOrgers" />
                        </div>
                    </div>

                    <!-- 移除好友 -->
                    <div class="yichuhyK">
                        <div class="zzsyKT">确定移除选中的好友吗？</div>
                        <input type="text" class="zzsy_delKpd" style="display:none;" />
                        <div class="zzsyB">
                            <input type="button" class="zzsyBb2" value="确定" data-bind="click:removeMyFriends" />
                        </div>
                    </div>

                    
                    <!-- 按钮栏 -->
                    <div class="friendMTl">
                        <div class="friendMTl_l">
                            <div class="friendMTl_lt">组织结构</div>
                        </div>
                        <div class="friendMTl_r">
                            <div class="friendMTl_rb friend_addzzs">添加组织结构</div>
                            <div class="jiangeline2"></div>
                            <div class="friendMTl_rb friend_addy">添加组</div>
                            <div class="jiangeline2"></div>
                            <div class="friendMTl_rb friend_addmem">添加成员</div>
                            <div class="jiangeline2"></div>
                            <div class="friendMTl_rb friend_edity">修改组</div>
                            <div class="jiangeline2"></div>
                            <div class="friendMTl_rb friend_dely">删除组</div>
                        </div>
                    </div>
                    <div class="friendMTr">
                        <div class="search_2">
                            <div class="searchC">
                                <img src="<%=request.getContextPath()%>/static/img/search.png" alt="" class="searchCi" data-bind="click:getMyFriends" />
                                <input id="myFriendSearch" name="friendName" type="text" class="searchCt"  placeholder="搜索" data-bind="event: { keyup: searchMyFriends}" />
                            </div>
                        </div>
                        <div class="search_3 active">
                            <div class="searchC">
                                <img src="<%=request.getContextPath()%>/static/img/search.png" alt="" class="searchCi" data-bind="click:getOrgers" />
                                <input id="groupMemberSearch" type="text" class="searchCt"  placeholder="组内成员" data-bind="event: { keyup: searchOrgers}" />
                            </div>
                        </div>
                        <!-- <div class="friendMTrs" style="display:none;">
                            <select id="orgRole" name="orgRole" data-bind="event: { change:getOrgers }">
                                <option value="">所有角色</option>
                                <option value="管理员">管理员</option>
                                <option value="成员">成员</option>
                            </select>
                        </div> -->
                        <!-- <div class="friendMTrss" style="display:none;">
                            <select id="sysRoles" name="sysRoles" data-bind="foreach:sysRoles,event: { change:getMyFriends }">
                                <option data-bind="text:description,value:description"></option>
                            </select>
                        </div> -->
                        <div class="friendMTrb friend_qunfa">群发消息</div>
                        <div class="friendMTrb friend_yichuzu">从组中移除</div>
                        <div class="friendMTrb friend_yichuhy">移除好友</div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="friendMM">

                    <!-- 添加好友框 -->
                    <div class="haoyou_addK">
                        <div class="hyaddKT">
                            <div class="hyaddKTt">添加好友</div>
                            <img src="<%=request.getContextPath()%>/static/img/close.png" alt="" class="hyaddKTi" />
                        </div>
                        <div class="cyaddKT2">
                        <form id="findStrangerListForm">
                            <input name="username" type="text" class="cyaddKT2p" placeholder="输入要搜索的用户名" />
                            <input type="button" class="cyaddKT2b" value="搜索" data-bind="click:findStrangerList" />
                        </form>
                        </div>
                        <div class="cyaddKM">
                        <form id="addFriendsForm">
                            <table class="cyaddKMtb">
                                <tr class="biaotou">
                                    <th class="touxiang">头像</th>
                                    <th class="yonghuming">用户名</th>
                                    <th class="youxiang">邮箱</th>
                                    <th class="caozuo">操作</th>
                                </tr>
                                <tbody data-bind="foreach:strangerList">
                                <tr class="biaoxiang">
                                    <th class="touxiang"><img data-bind="attr:{src: headimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="touxiangi" /></th>
                                    <th class="yonghuming" data-bind="text:username"></th>
                                    <th class="youxiang" data-bind="text:email"></th>
                                    <th class="caozuo">
                                        <input name="ids" data-bind="value:id" type="checkbox" class="caozuochk" />
                                    </th>
                                </tr>
                                </tbody>
                            </table>
                        </form>
                        </div>
                        <div class="cyaddKB">
                            <input type="button" class="cyaddKBb" data-bind="click:sendAllFriendRequest" value="添加" />
                            <input type="button" class="cyaddKBb hyaddKBb_close" value="关闭" />
                        </div>
                    </div>

                    <div class="friendMMl">
                        <div class="friendMMlT"><!-- 除添加好友外 -->
                            <div class="friendMMlTT"><!-- 除我的好友外 -->
                              <div data-bind="foreach: orgList">
                                <div class="friendMMlTTz" data-bind="attr:{name: id}" ><!-- 每个组织结构 -->
                                    <div class="friendMMlTTzT">
                                        <span class="fri_name" data-bind="text: organizationName,click:$root.removeAllOrgers"></span>
                                        <div class="friendMMlTTzTi"></div>
                                    </div>
                                    <div class="friendMMlTTzB">
                                        <div data-bind="template: { name: 'orgTmpl', foreach: groupList }"></div>
                                    </div>
                                </div>
                              </div>
                            </div>
                            <div class="friendMMlTB">
                                <a href="javascript:;" class="friendMMlTBa">
                                    <span data-bind="click:getMyFriends">我的好友（</span><span data-bind="text:friends().length"></span><span>）</span>
                                </a>
                            </div>
                        </div>
                        <div class="friendMMlB">
                            <div class="friendMMlb">添加好友</div>
                        </div>
                    </div>
                    <div class="friendMMr">
                        <div class="friendMMrz active">
                            <div class="friMMrtabK">
                            <form id="orgersForm">
                                <table class="friMMrtab">
                                    <tr class="biaotou">
                                        <th class="xuanze">
                                            <div class="quanxuanK">
                                                <input type="checkbox" class="input_check" id="check1_0">
                                                <label for="check1_0"></label>
                                            </div>全选
                                        </th>
                                        <th class="touxiangk">头像</th>
                                        <th class="yonghuming">用户名</th>
                                        <th class="youxiang">邮箱</th>
                                        <th class="juese">
                                        <select id="orgRole" name="orgRole" data-bind="event: { change:getOrgers }" style="font-family: Microsoft YaHei;color: #013f73;font-weight: bold;font-size: 16px;">
                                            <option value="" style="font-family: Microsoft YaHei;color: #013f73;">所有角色</option>
                                            <option value="管理员" style="font-family: Microsoft YaHei;color: #013f73;">管理员</option>
                                            <option value="成员" style="font-family: Microsoft YaHei;color: #013f73;">成员</option>
                                        </select>
                                        </th>
                                        <th class="caozuo">操作</th>
                                    </tr>
                                    
                                    <tbody id="ko_friend" data-bind="foreach: orgers">
                                    <tr class="">
                                        <td class="xuanze">
                                            <div class="fuxuanK2">
                                                <input name="orgIds" type="checkbox" class="input_check" data-bind="value: id,attr:{id:'check1_'+($index()+1)}">
                                                <label data-bind="attr:{for:'check1_'+($index()+1)}"></label>
                                            </div>
                                        </td>
                                        <td class="touxiangk">
                                            <img data-bind="attr:{src:headimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="touxiangi" />
                                        </td>
                                        <td class="yonghuming"><span data-bind="text: username"></span></td>
                                        <td class="youxiang"><span data-bind="text: email"></span></td>
                                        <td class="juese"><span data-bind="text: orgRole"></span></td>
                                        <td>
                                            <span data-bind="if:password"><span class="caozuo bukecaozuo" data-bind="text:password"></span></span>
                                            <span class="caozuo kecaozuo" data-bind="ifnot:password,click: $root.sendFriendRequest">加好友</span>
                                        </td>
                                        
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                            </div>
                        </div>
                        <div class="friendMMrz">
                            <div class="friMMrtabK">
                            <form id="myFriendsForm">
                                <table class="friMMrtab">
                                    <tr class="biaotou">
                                        <th class="xuanze">
                                            <div class="quanxuanK">
                                                <input type="checkbox" class="input_check" id="check2_0">
                                                <label for="check2_0"></label>
                                            </div>全选
                                        </th>
                                        <th class="touxiangk">头像</th>
                                        <th class="yonghuming">用户名</th>
                                        <th class="youxiang">邮箱</th>
                                        <th class="juese">
				                            <select id="sysRoles" name="sysRoles" data-bind="foreach:sysRoles,event: { change:getMyFriends }" style="font-family: Microsoft YaHei;color: #013f73;font-weight: bold;font-size: 16px;">
				                                <option data-bind="text:description,value:description" style="font-family: Microsoft YaHei;color: #013f73;"></option>
				                            </select>
                                        </th>
                                        <th class="caozuo">操作</th>
                                    </tr>
                                    <tbody data-bind="foreach:friends">
                                    <tr class="" >
                                        <td class="xuanze">
                                            <div class="fuxuanK2">
                                                <input name="ids" type="checkbox" class="input_check" data-bind="value: friendId,attr:{id:'check2_'+($index()+1)}">
                                                <label data-bind="attr:{for:'check2_'+($index()+1)}"></label>
                                            </div>
                                        </td>
                                        <td class="touxiangk">
                                            <img data-bind="attr:{src:friendHeadimg}" onerror='this.src="/wankangyuan/static/img/head.jpg"' alt="" class="touxiangi" />
                                        </td>
                                        <td class="yonghuming"><span data-bind="text: friendName"></span></td>
                                        <td class="youxiang"><span data-bind="text: friendEmail"></span></td>
                                        <td class="juese"><span data-bind="text: friendRolename"></span></td>
                                        <td class="caozuo faxiaoxi"><span data-bind="click:$root.sendMessage">发消息</span></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </form>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
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

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/knockout-3.4.2.js"></script>

<!--菜单项模板-->
<script id="orgTmpl" type="text/html">
<!-- ko if:groupList.length -->
<div class="friendMMlTTzBz2" data-bind="attr:{name: id}" data-bind="text: organizationName">
    <div class="friendMMlTTzBz2Tk" data-bind="click:$root.removeAllOrgers">
        <img src="<%=request.getContextPath()%>/static/img/folder.png" alt="" class="friendMMlTTzBzi" />
        <div class="friendMMlTTzBzt" data-bind="text: organizationName"></div>
        <div class="friendMMlTTzTi"></div>
    </div>
    <div class="friendMMlTTzBz2Mk" data-bind="template: { name: 'orgTmpl', foreach: groupList }">
        <div class="friendMMlTTzBz" data-bind="attr:{name: id}">
            <img src="<%=request.getContextPath()%>/static/img/folder.png" alt="" class="friendMMlTTzBzi" />
            <div class="friendMMlTTzBzt" data-bind="text: organizationName"></div>
        </div>
    </div>
</div>
<!-- /ko -->

<!-- ko ifnot:groupList.length -->
    <div class="friendMMlTTzBz" data-bind="attr:{name: id},click:function(data, event){$root.showOrgers(id) }">
        <img src="<%=request.getContextPath()%>/static/img/folder.png" alt="" class="friendMMlTTzBzi" />
        <div class="friendMMlTTzBzt" data-bind="text: organizationName"></div>
    </div>
<!-- /ko -->
                                        
</script>

<script type="text/javascript">
//从组里移除
$(".friend_yichuzu").click(function() {
	var ids = $("input[name='orgIds']");
    var checkNum = 0;
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked) {
            checkNum++;
        }
    }
    if (checkNum == 0) {
        layer.msg("请至少选中一个");
    } else {
    	$(".zuyichuK").show();
    }
});
//从好友移除
$(".friend_yichuhy").click(function() {
	var ids = $(".fuxuanK2 input[name='ids']");
    var checkNum = 0;
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked) {
            checkNum++;
        }
    }
    if (checkNum == 0) {
        layer.msg("请至少选中一个");
    } else {
    	$(".yichuhyK").show();
    }
});

//群发消息
$('.friend_qunfa').click(function(){
	var ids = $(".fuxuanK2 input[name='ids']");
    var checkNum = 0;
    for (var i = 0; i < ids.length; i++) {
        if (ids[i].checked) {
            checkNum++;
        }
    }
    if (checkNum == 0) {
        layer.msg("请至少选中一个");
    } else {
    	$(".qunfaMta").val("");
        $(".qunfaK").show();
    }
});

function sendMassFriendMessage() {
	var content = $(".qunfaMta").val();
	var load = layer.load();
    $.post("/wankangyuan/friendMessage/sendMassFriendMessage/"+content,$('#myFriendsForm').serialize() ,function(result){
        layer.close(load);
        if(result && result.status!= 200){
            return layer.msg(result.message,function(){}),!1;
        }else{
            layer.msg(result.message, {
                anim: 0,
                end: function (index) {
                	$(".qunfaMta").val("");
                    $(".qunfaK").hide();
                }
            });
            
        }
    },"json");
}

function addNewOrg() {
	var load = layer.load();
    $.post("/wankangyuan/organization/addNewOrg",$('#addNewOrgForm').serialize() ,function(result){
        layer.close(load);
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
function addNewGroup() {
	if(null == $('#formParentId').val() || '' == $('#formParentId').val()) {
		return layer.msg("请选择组织结构!",function(){}),!1;
	}
	var load = layer.load();
    $.post("/wankangyuan/organization/addNewGroup",$('#addNewGroupForm').serialize() ,function(result){
        layer.close(load);
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


function updateGroup() {
	var load = layer.load();
    $.post("/wankangyuan/organization/updateGroup",$('#updateGroupForm').serialize() ,function(result){
        layer.close(load);
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

function deleteGroup() {
	var load = layer.load();
    $.post("/wankangyuan/organization/deleteGroup",$('#deleteGroupForm').serialize() ,function(result){
        layer.close(load);
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

function addOrgMembers() {
	if(null == $('#groupId').val() || '' == $('#groupId').val()) {
        return layer.msg("请选择组!",function(){}),!1;
    }
	var load = layer.load();
    $.post("/wankangyuan/orgMember/addOrgMembers",$('#addOrgMembersForm').serialize() ,function(result){
        layer.close(load);
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

	
//定义ViewModel
function ViewModel() {
    var self = this;
    //当前组织ID
    var centOrgId;
    
    var centUser = '${user.username}';
    
    
    //组内成员
    self.orgers = ko.observableArray(); //添加动态监视数组对象
    //显示组内成员
    self.showOrgers = function(id){
    	$("#groupId").val(id);
    	centOrgId=id;
    	self.orgers.removeAll();
    	$.get("/wankangyuan/orgMember/findOrgMembers",{groupId:id},function(data){
            //alert(data);
            var list = JSON.parse(data);
            for (var i in list){
            	//根据好友列表判断状态
            	list[i].password = null;
            	for(var k in myfriends) {
                    if(list[i].userId == myfriends[k].friendId){
                        list[i].password = "已添加";
                    }
                    
                }
                if(list[i].username == centUser) {
                	list[i].password = "当前用户";
                }
            	
            	self.orgers.push(list[i]);
            	
            }
            friendmanage_quanxuan();
        });
    }
    
    self.getOrgers = function() {
    	if(null == $('#groupId').val() || '' == $('#groupId').val()) {
            return layer.msg("请选择组!",function(){}),!1;
        }
    	self.orgers.removeAll();
    	$.get("/wankangyuan/orgMember/findGroupMembersByname",{
    		username:$("#groupMemberSearch").val(),
    		groupId:$('#groupId').val(),
    		orgRole:$('#orgRole').val()
    		},function(data){
    		var list = JSON.parse(data);
            for (var i in list){
                //根据好友列表判断状态
                list[i].password = null;
                for(var k in myfriends) {
                    if(list[i].userId == myfriends[k].friendId){
                        list[i].password = "已添加";
                    }
                }
                if(list[i].username == centUser) {
                    list[i].password = "当前用户";
                }
                self.orgers.push(list[i]);
            }
            friendmanage_quanxuan();
    	});
    }
    self.removeAllOrgers = function(){
    	$('#groupId').val("");
    	
    	self.orgers.removeAll();
    }
    
    self.searchOrgers = function(data, event) {
        if(event.keyCode == "13") {  
            self.getOrgers();
        }  
    }
    
    //移除组内成员
    self.removeOrgers = function() {
    	var load = layer.load();
        $.post("/wankangyuan/orgMember/removeOrgMembers",$('#orgersForm').serialize() ,function(result){
            layer.close(load);
            if(result && result.status!= 200){
                return layer.msg(result.message,function(){}),!1;
            }else{
                layer.msg(result.message, {
                    anim: 0,
                    end: function (index) {
                    	self.showOrgers(centOrgId);
                    }
                });
                
            }
            $('.zuyichuK').hide();
        },"json");
    }
    //成员列表
    /* self.members = ko.observableArray(); 
    self.showMembers = function() {
    	self.members.removeAll();
        $.get("/wankangyuan/user/getOrgUserByName",$('#getUserByNameForm').serialize(),function(data){
            var list = JSON.parse(data);
            for (var i in list){
                self.members.push(list[i]);
                
            }
        });
    } */
    //所有陌生人
    self.strangerList = ko.observableArray(); 
    self.findStrangerList = function() {
        self.strangerList.removeAll();
        $.get("/wankangyuan/user/findStrangerList",$('#findStrangerListForm').serialize(),function(data){
            var list = JSON.parse(data);
            for (var i in list){
                self.strangerList.push(list[i]);
            }
            
        });
    }
    
    //初始化组能成员
    //self.showOrgMember();
    
    //发送好友申请
    self.sendFriendRequest = function(org) {
    	var load = layer.load();
        $.post("/wankangyuan/message/sendFriendRequest",{userId:org.userId} ,function(result){
            layer.close(load);
            if(result && result.status!= 200){
                return layer.msg(result.message,function(){}),!1;
            }else{
                layer.msg(result.message, {
                    anim: 0,
                    end: function (index) {
                        self.showOrgers(centOrgId);
                    }
                });
            }
        },"json");
    }
    
    //批量发送好友申请
    self.sendAllFriendRequest = function(user) {
    	var load = layer.load();
        $.post("/wankangyuan/message/sendAllFriendRequest",$('#addFriendsForm').serialize() ,function(result){
            layer.close(load);
            if(result && result.status!= 200){
                return layer.msg(result.message,function(){}),!1;
            }else{
                layer.msg(result.message, {
                    anim: 0,
                });
            }
        },"json");
    }
    
    //-----------------------------------------------------
    
    //角色列表
    var roles;
    self.sysRoles = ko.observableArray();
    self.getRoles = function() {
    	self.sysRoles.removeAll();
        $.get("/wankangyuan/role/getRoleList",{},function(data){
            roles = JSON.parse(data);
            roles[0].description = "所有角色";
            self.sysRoles.push(roles[0]);
            roles = JSON.parse(data);
            for (var i in roles){
                self.sysRoles.push(roles[i]);
                
            }
            friendmanage_quanxuan();
        });
    }
    //初始化角色信息
    self.getRoles();
    
    //-----------------------------------------------------
    
    //我的好友列表
    self.friends = ko.observableArray();
    var myfriends;
    self.getMyFriends = function() {
    	$('#groupId').val("");
    	self.friends.removeAll();
    	$.get("/wankangyuan/friends/getMyFriends",{
    		friendName:$("#myFriendSearch").val(),
    		sysRoles:$('#sysRoles').val()
    		},function(data){
    		myfriends = JSON.parse(data);
    		for (var i in myfriends){
                self.friends.push(myfriends[i]);
                
            }
	    	friendmanage_quanxuan();
        });
    }
    self.getMyFriends();
    
    //组织成员候选
    self.friends2 = ko.observableArray();
    var myfriends2;
    self.getFriendsToOrg = function() {
        self.friends2.removeAll();
        $.get("/wankangyuan/friends/getMyFriends",{friendName:$("#addOrgerInput").val()},function(data){
            myfriends2 = JSON.parse(data);
            for (var i in myfriends2){
                self.friends2.push(myfriends2[i]);
                
            }
            friendmanage_quanxuan();
        });
    }
    
    self.searchFriendsToOrg = function(data, event) {
    	if(event.keyCode == "13") {  
            self.getFriendsToOrg();
        }  
    }
    
    //移除好友
    self.removeMyFriends = function() {
    	var load = layer.load();
    	$.post("/wankangyuan/friends/removeFriends",$('#myFriendsForm').serialize() ,function(result){
            layer.close(load);
            if(result && result.status!= 200){
                return layer.msg(result.message,function(){}),!1;
            }else{
                layer.msg(result.message, {
                    anim: 0,
                    end:function() {
                    	$(".yichuhyK").hide();
                    	self.getMyFriends();	
                    }
                });
            }
        },"json");
    }
    
    self.searchMyFriends = function(data, event) {
    	if(event.keyCode == "13") {  
    		self.getMyFriends();
        }  
    }
    
    self.sendMessage = function(friend) {
    	window.location.href="/wankangyuan/friendMessage/viewSendMessage?objId="+ friend.friendId;
    }
    
    //-----------------------------------------------------
    
    var organization = function(id,organizationName, groupList) {
    this.id = ko.observable(id);
    this.organizationName = ko.observable(organizationName);
    this.groupList = ko.observableArray(groupList || []); //下级子菜单
};
    
    //组织结构
    self.orgList = ko.observableArray();
    self.getOrgList = function() {
        self.orgList.removeAll();
        $.get("/wankangyuan/organization/findOrgList",{},function(data){
        	var orgListJson = JSON.parse(data);
            for (var i in orgListJson){
                self.orgList.push(new organization(orgListJson[i].id,orgListJson[i].organizationName,orgListJson[i].groupList));
                
            }
            friend_manage();
        });
    }
    
    self.getOrgList();
}
var vm = new ViewModel();
ko.applyBindings(vm);

</script>
</body>
</html>