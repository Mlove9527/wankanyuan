
$("#select").click(function(){
    if($("#select").val()=='固定'){
        $(".money").css("display","block");
        $(".low").css("display","none");
    }
    if($("#select").val()=='随机'){
        $(".money").css("display","none");
        $(".low").css("display","block");
    }
});
//获取店铺id
var shopId = GetQueryString('shopId');
$("#bat").click(function(){
    //console.log($("#select").val())
    //固定和随机
    var rank ="";
    var num = '';
    if($("#select").val()=="固定"){
        rank=1;
        num= $("#rdate5").val()
    }else if($("#select").val()=="随机"){
        rank=2;
        num = $('#rdate3').val() +"-" + $('#rdate4').val()
    }
    $.ajax({
        type:"post",
        url:path + "Activity/createActivity",
        async:true,
        data:{
            "admin_user":username,
            'token':token,
            'a_name': $("#active").val(),
            'a_type':2,
            'mct_id':shopId,
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),
            'a_redbag_effective':$("#select1").find("option:selected").text(),
            'a_redbag_type':rank,
            'a_redbag_money':num
        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res)
            alert(res.message)
            // if(res.error_code==000){
            //     alert('创建成功')
            // }else{
            //     alert('创建失败')
            // }

        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});


//$("#btn").click(function(){
//    window.location.href=linkPath + 'SelfbuiltActivities/selfBuild.html';
//});