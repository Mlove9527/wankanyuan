//获取id
var shopId = GetQueryString('shopId');
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
            var html = '<option value="'+item.gc_name+'" id="'+item.gc_id+'">'+item.gc_name+'</option>'
            $(".kind").append(html)
            // $(".kind").attr('id',item.gc_id);
        })
    },
    error:function(){
        alert('请求失败，请检查网络设置')
    }
});

//添加菜品
$(".que").click(function(){
    //获取gc_id

    var gc_id = $(".kind option:selected").attr('id');
    console.log(gc_id)

    //获取gc_name
    var gc_name = $(".kind").val();

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
    formData.append("gc_name",gc_name);
    formData.append("g_pic",fileObj);
    formData.append("g_name",cainame);
    formData.append("g_spec",describe);
    formData.append("g_lunchbox",boxfee);
    formData.append("g_stock",kucun);
    formData.append("g_standard",guige);
    formData.append("g_price",price);


    $.ajax({
        url: path +'Goods/addGood',
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (res){
            console.log(res);
            if(res.error_code==0000){
                alert("添加成功")
            }

        },
        error: function () {
            alert('保存失败，请检查网络设置');
        }
    });

});

