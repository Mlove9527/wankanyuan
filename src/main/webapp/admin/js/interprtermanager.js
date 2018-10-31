/*增加新的选项*/

$(".addnew").click(function(){
    $(".meng").css("display","block");
    $(".mengxiang").css("display","block")
});
$(".addlangurage").click(function(){
    $(".meng").css("display","block");
    $(".mengxiang1").css("display","block")
})
/*新国家返回键*/
$(".newfan").click(function(){
    $(".mengxiang").css("display","none");
    $(".meng").css("display","none")
});
/*新建语种等级的返回键*/
$(".rankfan").click(function(){
    $(".mengxiang1").css("display","none");
    $(".meng").css("display","none")
});
/*选中按钮的颜色*/
/*国籍*/
$(".gj").click(function(){
    console.log($(this).css("color"));
    if($(this).css("color")=="rgb(51, 51, 51)"){
        $(this).css("background","#0B90C4");
        $(this).css("color","#ffffff");

    }else if($(this).css("color")=="rgb(255, 255, 255)"){
        $(this).css("background","#ffffff");
        $(this).css("color","#333");
    }
});
/*国家*/
$(".guorank").click(function(){
    if($(this).css("color")=="rgb(51, 51, 51)"){
        $(this).css("background","#0B90C4");
        $(this).css("color","#ffffff");

    }else if($(this).css("color")=="rgb(255, 255, 255)"){
        $(this).css("background","#ffffff");
        $(this).css("color","#333");
    }
})
/*英语等级*/
$(".langurage").click(function(){
    if($(this).html()=="英语"){
        var html="";
        html+='<div class="dengji">\n' +
            '<p class="" style="border:none">英语等级</p>\n' +
            '<p class="deng">一级</p>\n' +
            '<p class="deng">二级</p>\n' +
            '<p class="deng">三级</p>\n' +
            '<p class="deng">四级</p>\n' +
            '</div>'
        $("#rank").append(html)
    }
    if($(this).css("color")=="rgb(51, 51, 51)"){
        $(this).css("background","#0B90C4");
        $(this).css("color","#ffffff");


    }else if($(this).css("color")=="rgb(255, 255, 255)"){

        $(this).css("background","#ffffff");
        $(this).css("color","#333");
        $(".dengji").remove()
    }


});
/*等级*/
$("#rank").on('click','.deng',function(){
    if($(this).css("color")=="rgb(51, 51, 51)"){
        $(this).css("background","#0B90C4");
        $(this).css("color","#ffffff");

    }else if($(this).css("color")=="rgb(255, 255, 255)"){
        $(this).css("background","#ffffff");
        $(this).css("color","#333");
    }
})