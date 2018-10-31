//获取id
var shopId = GetQueryString('shopId')
//console.log(shopId )
$(function(){
    function getOrderList() {
        var newDataArr;
        $.ajax({
            type: "post",
            url: path + "/Activity/getActivityList",
            async: false,
            data: {
                "admin_user": username,
                'token': token,
                'mct_id': shopId,
            },
            success: function (res) {
                /* 对返回数据进行处理 */
                console.log(res);
                var dataArr = res.data;
                console.log(dataArr);
                newDataArr = [];

                for (var i in dataArr) {
                    var obj = {};
                    obj.bianhao= dataArr[i].id;
                    obj.a_name = dataArr[i].a_name;
                    obj.a_rule = dataArr[i].a_rule;
                    obj.date = dataArr[i].a_startdate+'- '+dataArr[i].a_enddate;
                    if(dataArr[i].a_type==1){
                        obj.type='店铺满减'
                    }else if(dataArr[i].a_type==2){
                        obj.type='新用户红包'
                    }else if(dataArr[i].a_type==3){
                        obj.type='天天特价'
                    }else if(dataArr[i].a_type==4){
                        obj.type='进店领红包'
                    }
                //状态
                    if (dataArr[i].is_doing == 1) {
                        obj.status = '<div style="color:#0e90d2">未进行</div>'
                        obj.manage = '<span class="btn btn-small btn-info change">修改</span>'
                            +'<span class="btn btn-small btn-primary shanchuthis" id="'+dataArr[i].id+'" style="margin-left:10px">删除</span>';
                    } else {
                        obj.status = '<div style="color:#e54a64">进行中</div>'
                        obj.manage = '<span class="btn btn-small btn-info " disabled="true">修改</span>'
                            +'<span class="btn btn-small btn-primary shanchuthis" id="'+dataArr[i].id+'" style="margin-left:10px">删除</span>';
                    }



                    // obj.select = '<input type="checkbox" class="sel" id="'+dataArr[i].id+'">';

                    newDataArr.push(obj);

                }


                //修改按钮

                $("#example").on('click','.change',function(){
                  var val =($(this).parent().prev().prev().html())
                  if(val=='店铺满减'){
                    window.location.href=linkPath+'editmanjian.html?shopId='+shopId;
                  }else if(val=='天天特价'){
                      window.location.href=linkPath+'editTiantianteprice.html?shopId='+shopId;
                  }else if(val=='进店领红包'){
                      window.location.href=linkPath+'editenterhongbao.html?shopId='+shopId;
                  }else if(val=='新用户红包'){
                      window.location.href=linkPath+'editnewhongbao.html?shopId='+shopId;
                  }
                });

            },
            error: function () {
                alert('请求失败，请检查网络设置')
            }
        });
        // console.log(newDataArr);
        return newDataArr;
    }

    var data = getOrderList();

    var table = $("#example").dataTable({

        data:data,

        pagingType:"full_numbers",
        //列表表头字段

        columns: [
            // {"data": "select"},
            {"data": "bianhao"},
            {"data": "a_name"},
            {"data": "a_rule"},
            {"data": "date"},
            {"data": "type"},
            {"data": "status"},
            {"data": "manage"}

        ]


    });


 //删除当前的活动
    $(".body").on('click','.shanchuthis',function(){
        var id=$(this).attr('id');
        console.log(id);
        console.log($(this).parent().prev().children().html())
        if($(this).parent().prev().children().html()=='未进行'){
            $.ajax({
                type: "post",
                url: path + "/Activity/deleteActivity",
                async: false,
                data: {
                    "admin_user": username,
                    'token': token,
                    'mct_id': shopId,
                    'activity_id':id
                },
                success: function (res) {
                    /* 对返回数据进行处理 */
                    console.log(res);
                    // var data = res.data;
                    // console.log(data);



                },
                error: function () {
                    alert('请求失败，请检查网络设置')
                }
            });
        }else{
            alert('活动正在进行，不可删除')
        }

    })
});

//全选
$("#chose-shop-all").click(function(){
    if($(this).is(":checked")){
        $(".sel").prop("checked",true)
    }else{
        $(".sel").prop("checked",false)
    }



})

//创建活动
//进店领红包
$(".enterhongbao").click(function(){
  window.location.href = linkPath + "enterhongbao.html?shopId="+shopId;


});
//新用户红包
$(".newhongbao").click(function(){
    window.location.href = linkPath + "newhongbao.html?shopId="+shopId;

});
//天天特价
$(".teprice").click(function(){
    window.location.href = linkPath + "tiantianteprice.html?shopId="+shopId;

});
//满减
$(".manjian").click(function(){
    window.location.href = linkPath + "manjian.html?shopId="+shopId;

});
