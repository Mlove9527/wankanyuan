//天天特价编辑

//获取id
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
            $.each(data,function(i,item){
            //优惠类型
                if(item.a_type==3){
            if(item.a_discount_type==1){
                $("#select").val("特价");
                $(".youhui").css("display","block");
                $("#rdate3").val(item.a_discount);
                $(".zhekou").css("display","none");

            }else if(item.a_discount_type==2){
                $("#select").val("折扣");
                $(".youhui").css("display","none");
                $(".zhekou").css("display","block");
                $("#rdate4").val(item.a_discount);
            }
            $("#active").val(item.a_name);
            $("#rdate1").val(item.a_startdate);
            $("#rdate2").val(item.a_enddate);
            $(".name").attr("id",item.id);
            //$("#rdate4").val(data[0].a_discount);
                }
            })
        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});


var val ="";
$("#select").click(function(){
 //alert(111)
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

//下一步
$("#bat").click(function(){
    console.log($(".name").attr("id"));
    var num = ""
    if($("#select").val()=="特价"){
        num = $("#rdate3").val();
    }else if($("#select").val()=="折扣"){
        num = $("#rdate4").val();
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
            'a_name': $("#active").val(),
            'a_type':3,
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),
            'a_discount_type':val,
            'a_discount':num
        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res);
            if(res.message=="数据新增失败"){
               alert("请修改数据");
            }
        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
    //window.location.href=linkPath + 'SelfbuiltActivities/addshop.html';
});

//$.ajax({
//    type:"post",
//    url:path + "api/Pay/AliPayPage",
//    async:true,
//    data:{
//        'mobile':getCookie('id'),
//        'token':getCookie('token'),
//        'usertype':2
//
//
//    },
//    success:function(res){
//        /* 对返回数据进行处理 */
//        console.log(res.data)
//        window.location.href=res.data;
//
//    },
//    err:function(){
//        alert('请求失败，请检查网络设置')
//    }
//});