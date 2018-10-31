
var val ="";
$("#select").click(function(){

    if($("#select").val()=="特价"){
        val=1;
        $(".youhui").css("display","block");
        $(".zhekou").css("display","none");
    }
    if($("#select").val()=="折扣"){
        val=2;
        $(".youhui").css("display","none");
        $(".zhekou").css("display","block");
    }
});


$("#bat").click(function(){
    console.log($("#select").val())
    var num = ""
    if($("#select").val()=="特价"){
        num = $("#rdate3").val();
    }else if($("#select").val()=="折扣"){
        num = $("#rdate4").val();
    }
//获取店铺id
    var shopId = GetQueryString('shopId');
    $.ajax({
        type:"post",
        url:path + "/Activity/createActivity",
        async:true,
        data:{
            'admin_user':username,
            'token':token,
            'mct_id':shopId,
            'a_name': $("#active").val(),
            'a_type':3,
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),

            'a_discount_type':val,

            'a_discount':num
        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res.message)
            alert(res.message)
            // if(res.error_code==000){
            //     alert('创建成功')
            // }else{
            //     alert('创建失败')
            // }

        },
        err:function(){
            //alert('请求失败，请检查网络设置')
        }
    });

window.location.href= linkPath + 'selectgoods.html?shopId='+shopId;

});

$("#btn").click(function(){

});