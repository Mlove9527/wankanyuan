<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>登录</title>
</head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/index.css" />
<!-- <script type="text/javascript" src="js/project1.js"></script> -->

<body>
    <div class="Box">
        <div class="box">
            <div class="top">
                <h1><img src="<%=request.getContextPath()%>/static/img/newlogo2.png" class="logo" height="70" width="218" alt="" /></h1>
                <div class="link">链接</div>
            </div>
            <div class="xiangmu">
                <div class="unauthorized">
                    <div class="unauthorizedC">
                        <div class="unauthorizedCC">
                            <div class="errorT1">对不起，您没有权限</div>
                            <div class="errorT2">您将在5s内跳转,若未跳转请</div>
                            <a href="javascript:;" onClick="javascript :history.go(-1);" class="errorA">点击这里</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="bottom">
                <a href="javascript:;">
                    <div class="bottomL">关于我们</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">用户协议</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">企业服务</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">帮助中心</div>
                </a>
                <a href="javascript:;">
                    <div class="bottomL">联系我们</div>
                </a>
                <a href="javascript:;">
                    <img src="img/weixin.png" alt="" class="bottomR" />
                </a>
                <a href="javascript:;">
                    <img src="img/weibo.png" alt="" class="bottomR" />
                </a>
            </div>
            <div class="bottom2">
                <div class="bottom2z">京ICP备09083200号 合字B2-20160007 人才服务许可证:120116174002号 京公网安备 11010502035189号</div>
                <div class="bottom2z">Copyright © 2006-2018 xxxxxx.com All Rights Reserved</div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    var timer=null;
    timer=setTimeout(function(){
        history.go(-1);
    },5000)
</script>
</html>