console.log($("#select1"));
$("#select1").click(function(){
    //alert("hello");
    //console.log($("#select1"));
    if($("#select1").val()=='固定金额'){
        $(".money").css("display","block");
        $(".low").css("display","none");
    }if($("#select1").val()=='随机金额') {
        $(".money").css("display","none");
        $(".low").css("display","block");
    }
});

//获取店铺id
var shopId = GetQueryString('shopId');
$("#bat").click(function(){
    console.log($("#select").val());
    var num = "";
    if($("#select1").val()=="固定金额"){
        num = $("#rdate5").val();
    }else if($("#select1").val()=="随机金额"){
        num = $("#rdate3").val()+"-"+$("#rdate4").val();
    }
    console.log(num)
    //红包类型
    var type="";
    if($("#select1").val()=="固定金额"){
        type = 1;
    }else if($("#select1").val()=="随机金额"){
        type = 2;
    }
    //红包数量

    var limit=""
    if($("#select2").val()=="固定数量"){
        limit=1
    }else if($("#select2").val()=="每天限量"){
        limit=2
    }
    console.log(limit)
    $.ajax({
        type:"post",
        url:path + "/Activity/createActivity",
        async:true,
        data:{
            'admin_user':username,
            'token':token,
            'mct_id':shopId,
            'a_name': $("#active1").val(),
            'a_type':4,
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),
            'a_redbag_effective':$("#select").find("option:selected").text(),
            'a_redbag_type':type,
            'a_redbag_money':num,
            'a_redbag_limit':limit,
            'a_redbag_num':$("#rdate6").val(),
            'a_rule':$("#active2").val(),
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
            alert('请求失败，请检查网络设置')
        }
    });
});

$("#btn").click(function(){
    //window.location.href=linkPath + 'SelfbuiltActivities/selfBuild.html';
});