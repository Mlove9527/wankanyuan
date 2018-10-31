//获取店铺id
var shopId = GetQueryString('shopId');

//进店领红包编辑页面
$(function(){
    $.ajax({
        type:"post",
        url:path + "Activity/getActivityList",
        async:true,
        data:{
            "admin_user": username,
            'token': token,
            'mct_id': shopId

        },
        success:function(res) {
            /* 对返回数据进行处理 */
            console.log(res);
            var data = res.data;
            console.log(data);
            $.each(data,function(i,item) {

               if(item.a_type==4){

            //红包有效期
            var date = "";
            if (item.a_redbag_effective == 1) {
                date = "1";
            } else if (item.a_redbag_effective == 2) {
                date = "2";
            } else if (item.a_redbag_effective == 3) {
                date = "3";
            }
            //红包类型
            var typeval1 = "";
            var money1 = "";
            var arr1 = [];
            if (item.a_redbag_type == 1) {
                $("#select1").val("固定金额");
                money1 = item.a_redbag_money;
                $(".money").css("display", "block");
                $(".low").css("display", 'none');
                $("#rdate5").val(money1);
            } else if (item.a_redbag_type == 2) {
                $("#select1").val("随机金额");
                $(".money").css("display", "none");
                $(".low").css("display", 'block');
                money1 = item.a_redbag_money;
                arr1 = money1.split("-")
                console.log(arr1);
                $("#rdate3").val(arr1[0]);
                $("#rdate4").val(arr1[1]);
            }
            //固定数量和每天限量
            if (item.a_redbag_limit == 1) {
                $("#select2").val("固定数量")
            } else if (item.a_redbag_limit == 2) {
                $("#select2").val("每天限量");
            }
            $("#active1").val(item.a_name);
            $("#active2").val(item.a_rule);
            $("#rdate1").val(item.a_startdate);
            $("#rdate2").val(item.a_enddate);
            $("#select").val(date);
            $("#fafang").val(item.a_redbag_num);


            $(".name").attr("id", item.id);
               }
        })

        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});
//固定金额与随机金额
$("#select1").click(function(){

    if ( $("#select1").val()=="固定金额") {
        $(".money").css("display", "block");
        $(".low").css("display", 'none');
    } else if($("#select1").val()=="随机金额") {

        $(".money").css("display", "none");
        $(".low").css("display", 'block');
    }
})
//保存
$("#bat").click(function(){
    console.log($(".name").attr("id"));

    var num = "";
    if($("#select1").val()=='固定金额'){
        num = $("#rdate5").val();
    }else if($("#select1").val()=='随机金额'){
        num = $("#rdate3").val()+"-"+$("#rdate4").val();
    }

    //红包类型
    var type="";
    if($("#select1").val()=="固定金额"){
        type = 1;
    }else if($("#select1").val()=="随机金额"){
        type = 2;
    }

    //固定数量和每天限量
    var limit ="";
    if($("#select2").val()=='固定数量'){
        limit=1
    }else if($("#select2").val()=='每天限量'){
        limit=2
    }
    $.ajax({
        type:"post",
        url:path + "Activity/editActivity",
        async:true,
        data:{
            "admin_user": username,
            'token': token,
            'mct_id': shopId,
            'activity_id':$(".name").attr("id"),
            'a_name': $("#active1").val(),
            'a_type':4,
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),
            'a_redbag_effective':$("#select").find("option:selected").text(),
            'a_redbag_type':type,
            'a_redbag_money':num,
            'a_redbag_limit':limit,
            'a_redbag_num':$("#fafang").val(),
            'a_rule':$("#active2").val()
        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res)

        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});


