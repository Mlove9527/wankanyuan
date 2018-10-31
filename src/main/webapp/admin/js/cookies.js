/*
 * 请求路径
 */
//var path = 'http://118.190.114.198:8035/';
/*
 * 跳转路径
 */
//var linkPath = 'http://118.190.114.198/WM-s/';

function setCookie(cname, cvalue, exdays) //设置cookie
{
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toGMTString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) //读取cookie
{
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i = 0; i < ca.length; i++) {
		var c = ca[i].trim();
		if(c.indexOf(name) == 0) return c.substring(name.length, c.length);
	}
	return "";
}

function checkCode(code, msg) {
	if(code == '0000') {
		
	} else {
		alert(msg);
	}
}

function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
}


/* 用户类型 */
userTypeval = 2