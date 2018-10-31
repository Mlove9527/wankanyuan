
//新增
/*$(".new-crease").click(function() {
	$(".mengxiang").css("display", "none");
	$(".mengxiang1").css("display", "block");
});*/

//添加
/*$(".add").click(function() {
	var text = "";
	text += '<div class="del">' +
		'<span>xxxxx</span>' +
		'<button class="btn btn-primary btn-fill">删除</button>' +
		'</div>'
	$(".cont").append(text);
	$(".mengxiang1").css("display", "none");
	$(".meng").css("display", "none")
	alert("添加成功")
});*/

//添加权限
$(".tianjia").click(function() {
	$(".mengxiang4").css("display", "block");
	$(".mengxiang2").css("display", "none")
});

/*$(".baocun").click(function() {
	$(".mengxiang2").css("display", "none");
	$(".meng").css("display", "none")
});*/
$(".quxiao").click(function() {
	$(".mengxiang2").css("display", "none");
	$(".meng").css("display", "none")
});

//返回
$(".fanhui").click(function() {
	//alert(111)
	$(".mengxiang").css("display", "none");
	$(".meng").css("display", "none");
});

//新增角色
/*$(".new").click(function() {
	$(".meng").css("display", "block");
	$(".mengxiang2").css("display", "block")
})*/


//返回
$(".fanh").click(function() {
	$(".mengxiang4").css("display", "none");
	$(".meng").css("display", "none");
})
