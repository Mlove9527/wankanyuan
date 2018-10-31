//获取id
var shopId = GetQueryString('shopId');

// 添加菜品分类名称
console.log($(".tianjia"))
$(".tianjia").click(function(){
    $(".xinjian").css("display","block");
    $(".meng").css("display","block");
});

//添加菜品返回按钮
$(".fanset").click(function(){
    $(".xinjian").css("display","none");
    $(".meng").css("display","none");

})
//从菜品库添加菜品返回按钮
$(".back").click(function(){
    $(".mengxiang").css("display","none");
    $(".meng").css("display","none");
})

//商品分类排序
$(".shangpinfenlei").click(function(){
    window.location.href=linkPath+'shangpinfenleipaixu.html?shopId='+shopId;
})
//商品排序
$(".shangpinpaixu").click(function(){
    window.location.href= linkPath+'shangpinpaixu.html?shopId='+shopId;
})





$(".delset").click(function(){
    $(".dele").css("display","none");
    $(".meng").css("display","none");

})
$(".bacset").click(function(){
    $(".dele").css("display","none");
    $(".meng").css("display","none");
});
//****************************************************************************************************************************************************
//获取商品分类
$(function(){
    $.ajax({
        type:"post",
        url:path + "Goods/getGoodList",
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
            console.log(data)

            $.each(data,function(i,item){
                var html1= $('<div class="cont" style="overflow:hidden" id="'+item.gc_id+'"></div>')
                var html2=$('<div class="cailist">'+
                    '<label for="inputSuccess" class="control-label liangcai" style="font-size:18px">'+item.gc_name+'</label>'+
                    '<div class="ico">'+
                    '<div class="halflings-icon plus"></div>'+
                    '<div class="halflings-icon remove"></div>'+
                    '<div class="halflings-icon edit"></div>'+
                    '</div>'+
                    '<div class="clear"></div>'+
                    '</div>')
                html1.append(html2);
                $.each(item['goods'],function(g,itemGood){
                    /* if(item.gc_name==){}*/
                    //var html1= $('<div class="cont"></div>')
                   var html3=$('<ul class="list" style="float:left" id="'+itemGood.g_id+'">' +
                        ' <li>' +
                        ' <img src="'+itemGood.g_pic+'" alt="">' +
                        ' <p>'+itemGood.g_name+'</p>' +
                        '  </li>' +
                        ' </ul>')
                    html1.append(html3);

                });


                $(".caikind").append(html1);

            })

            //添加菜品
            $(".plus").click(function(){
                var gc_id= $(this).parents(".cont").attr('id');
                window.location.href=linkPath+'lurucai.html?shopId='+shopId+'&gc_id='+gc_id;
            })

            //添加菜品
            $(".tianjiacai").click(function(){
                window.location.href=linkPath+'lurucaicai.html?shopId='+shopId
            });
        //编辑菜品
            var gc_id="";
            var g_id=""

            $(".caikind").on('click','.list',function(){
                $(".sure").css("display","block");
                $(".meng").css("display","block");
                 gc_id= $(this).parents(".cont").attr('id');
                 g_id=$(this).attr('id');
                // window.location.href = linkPath+'editcaipin.html?shopId='+shopId+'&gc_id='+gc_id+'&g_id='+g_id;

            });
            //编辑菜品返回
            $(".fanback").click(function(){
                $(".sure").css("display","none");
                $(".meng").css("display","none");
            })
            //编辑
            $(".bian").click(function(){
                window.location.href = linkPath+'editcaipin.html?shopId='+shopId+'&gc_id='+gc_id+'&g_id='+g_id;

            })
            $(".shan").click(function(){
                $.ajax({
                    type:"post",
                    url:path + "Goods/deleteGood",
                    async:true,
                    data:{
                        "admin_user": username,
                        'token': token,
                        'mct_id': shopId,
                        'g_id':g_id

                    },
                    success:function(res) {
                        /* 对返回数据进行处理 */
                        console.log(res);

                    },
                    err:function(){
                        alert('请求失败，请检查网络设置')
                    }
                });
                $(".sure").css("display","none");
                $(".meng").css("display","none");
                window.location.reload()
            })

        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
})
//******************************************************************************************************************************************************
//获取菜单名
$(".newset").click(function(){
    //获取菜单名
    var name = $(".menucai").val();
    $.ajax({
        type:"post",
        url:path + "Goods/addGoodCate",
        async:true,
        data:{
            "admin_user": username,
            'token': token,
            'mct_id': shopId,
            'gc_name':name

        },
        success:function(res) {
            /* 对返回数据进行处理 */
            console.log(res);
            if(res.error_code==0000){
                alert('添加成功')
            }
        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });


    $(".xinjian").css("display","none");
    $(".meng").css("display","none");

})



//****************************************************************************************************************************************************
//删除菜品种类

$(".caikind").on('click','.remove',function(){
    //获取商品id
    var gId=$(this).parents(".cont").attr('id');
    console.log(gId)
        $.ajax({
            type:"post",
            url:path + "Goods/deleteGoodCate",
            async:true,
            data:{
                "admin_user": username,
                'token': token,
                'mct_id': shopId,
                'gc_id':gId

            },
            success:function(res) {
                /* 对返回数据进行处理 */
                console.log(res);
            },
            err:function(){
                alert('请求失败，请检查网络设置')
            }
        });

})
//***************************************************************************************************************************************************
//编辑商品分类
$(".caikind").on('click','.edit',function(){
    var gcId = $(this).parents(".cont").attr('id');

    $(".meng").css("display","block");
    $(".mengxiang1").css("display","block")


    $(".certain").click(function(){
        //console.log($(".cai"))
        var gc_name=$(".cai").val();
        //console.log(gc_name)
        $.ajax({
            type:"post",
            url:path + "Goods/editGoodCate",
            async:true,
            data:{
                "admin_user": username,
                'token': token,
                'mct_id': shopId,
                'gc_id':gcId,
                'gc_name':gc_name

            },
            success:function(res) {
                /* 对返回数据进行处理 */
                console.log(res);

            },
            err:function(){
                alert('请求失败，请检查网络设置')
            }
        });

        $(".meng").css("display","none");
        $(".mengxiang1").css("display","none");
    })

});
//编辑商品分类的返回
$(".fan").click(function(){
    $(".mengxiang1").css("display","none");
    $(".meng").css("display","none");
})

//******************************************************************************************************************************************
//商品分类排序


//*******************************************************************************************************************************************************
//
// $(".caikind").on('click','.plus',function(){
//     // $(".mengxiang").css("display","block");
//     // $(".meng").css("display","block");
//     window.location.href=linkPath+'lurucai.html?shopId='+shopId;
// });



//添加菜品
