
// 获取菜品列表
//获取id
var shopId = GetQueryString('shopId');
//获取gc_id
var gc_id = GetQueryString('gc_id');
//获取g_id
var g_id = GetQueryString('g_id');

$(function(){
    //获取商品分类列表
    $.ajax({
        type:"post",
        url:path + "Goods/getGoodCateList",
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
                var html = '<option value="'+item.gc_name+'">'+item.gc_name+'</option>'
                $(".kind").append(html)
                $(".kind").attr('id',item.gc_id);
            })
        },
        error:function(){
            alert('请求失败，请检查网络设置')
        }
    });


    //获取菜品
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
                $.each(item['goods'],function(j,itemgoods){
                    // console.log(itemgoods);
                   if(itemgoods.g_id == g_id){
                       $("#imghead").attr('src',itemgoods.g_pic);
                       $(".cainame").val(itemgoods.g_name);
                       $(".caiprice").val(itemgoods.g_price);
                       $(".kucun").val(itemgoods.g_stock);
                       $(".boxfee").val(itemgoods.g_lunchbox);
                       $(".gui").val(itemgoods.g_standard);
                       $(".describe").val(itemgoods.g_spec);

                   }
                })
            })



        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });



    //编辑菜品
    $(".que").click(function(){
        //获取gc_id
        var gc_id = $(".kind").attr('id');

        //获取gc_name
        var gc_name = $(".kind").val();
        //获取g_id
        var g_id = GetQueryString('g_id');

        //获取菜品图片
        var fileM=document.querySelector("#previewImg");
        var fileObj=fileM.files[0];
        //获取菜品名称
        var cainame = $(".cainame").val();
        //菜品价格
        var price = $(".caiprice").val();
        //菜品库存、
        var kucun = $(".kucun").val();
        //餐盒费
        var boxfee = $(".boxfee").val();
        //规格
        var guige =$(".gui").val();
        //菜品描述
        var describe = $(".describe").val();

        var formData=new FormData();
        formData.append("admin_user",username);
        formData.append("token",token);
        formData.append("mct_id",shopId);
        formData.append("gc_id",gc_id);
        formData.append("g_id",g_id);
        formData.append("gc_name",gc_name);
        formData.append("g_pic",fileObj);
        formData.append("g_name",cainame);
        formData.append("g_spec",describe);
        formData.append("g_lunchbox",boxfee);
        formData.append("g_stock",kucun);
        formData.append("g_standard",guige);
        formData.append("g_price",price);


        $.ajax({
            url: path +'Goods/editGood',
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (res){
                console.log(res);
                if(res.error_code==000){
                    alert('修改成功')
                }else{
                    alert('修改失败')
                }

            },
            error: function () {
                alert('保存失败，请检查网络设置');
            }
        });

    })


    //返回按钮
    $(".fan").click(function(){
        window.location.href=linkPath +'caidan.html?shopId='+shopId;
    })
});




