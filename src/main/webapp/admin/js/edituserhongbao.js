//编辑新用户红包
//获取商铺id
var shopId = GetQueryString('shopId')
$(function(){
    $.ajax({
        type:"post",
        url:path + "Activity/getActivityList",
        async:true,
        data:{
            "admin_user": username,
            'token': token,
            'mct_id': shopId,
        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res);
            var data= res.data;
            console.log(data);
            $.each(data,function(i,item){

           if(item.a_type==2){
            //红包有效期
            var date="";
            if(item.a_redbag_effective==1){
                date = "1";
            }else if(item.a_redbag_effective==2){
                date = "2";
            }else if(item.a_redbag_effective==3){
                date = "3";
            }
            //红包类型
            var typeval1 = "";
            var money1 = "";
            var arr1 = [];
            if(item.a_redbag_type==1){
                $("#select").val("固定");
                money1 = item.a_redbag_money;
                $(".low").css("display","none");
                $(".money").css("display","block");
                $("#rdate5").val( money1);

            }else if(item.a_redbag_type==2){
                $("#select").val("随机");
                $(".low").css("display","block");
                $(".money").css("display","none");
                money1 = item.a_redbag_money;
                arr1 = money1.split("-")
                console.log(arr1);
                $("#rdate3").val(arr1[0]);
                $("#rdate4").val(arr1[1]);
            }

            $("#active").val(item.a_name);
            $("#rdate1").val(item.a_startdate);
            $("#rdate2").val(item.a_enddate);
            $("#select1").val(date);





            $(".name").attr("id",item.id);
           }

            })
        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});

var rank ="";
var num = "";
console.log($("#select"));
$("#select").click(function(){

    if($("#select").val()=="固定"){
        rank=1;
        num = $("#rdate5").val();
    }else if($("#select").val()=="随机"){
        $(".low").css("display","block");
        $(".money").css("display","none");
    }
})
      

$("#bat").click(function(){
    if($("#select").val()=="固定"){
        rank=1;
        num = $("#rdate5").val();
        console.log(num)
    }else if($("#select").val()=="随机"){
        rank=2;
        num = $('#rdate3').val() +"-" + $('#rdate4').val();
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
            'a_name':$(".name input").val(),
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),
            'a_redbag_effective':$("#select1").find("option:selected").text(),
            'a_redbag_type':rank,
            'a_redbag_money':num
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