
$(function(){
    //获取id
    var shopId = GetQueryString('shopId');

    $.ajax({
        type:"post",
        url:path + "Merchant/getMctInfo",
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
            //商店locol
            $("#imghead").attr('src',data.mct_headimgurl)
            //电话
            $(".phone").html(data.mct_orderphone);
            //营业时间
           if(data.mct_opentime==''){
               $(".startime").css("display","none");
               $(".endtime").css("display","none");
           }else{
               $(".all").css("display","none");
               $(".startime").html(data.mct_opentime[0]);
               $(".endtime").html(data.mct_opentime[1]);
           }
           //配送范围
            var arr = data.mct_distribution_rule;

                var Arr1 = (arr[0].split('/'));
                $(".startprice1").html(Arr1[0]);
                $(".fee1").html(Arr1[1]);
                var Arr2 =(arr[1].split('/'))
                $(".startprice2").html(Arr2[0]);
                $(".fee2").html(Arr2[1]);
                var Arr3 =(arr[2].split('/'))
                $(".startprice3").html(Arr3[0]);
                $(".fee3").html(Arr3[1]);
                var Arr4 =(arr[3].split('/'))
                $(".startprice4").html(Arr4[0]);
                $(".fee4").html(Arr4[1]);
            //商家公告
            $("#gonggao").val(data.mct_notice)
            //是否支持投票

            if(data.mct_is_invoice==1){

                $("#avoid").val("支持")
            }else if(data.mct_is_invoice==9){
                $("#avoid").val("不支持");

            }

        },
        error:function(){
            alert('请求失败，请检查网络设置')
        }
    });

  //修改外卖信息
    $(".tijiao").click(function(){
        if($("#avoid").val()=="支持"){
            avoid=1
        }else{
            avoid=9
        }
        console.log(avoid)
        $.ajax({
            type:"post",
            url:path + "Merchant/setMct",
            async:true,
            data:{
                "admin_user": username,
                'token': token,
                'mct_id': shopId,
                'mct_is_invoice':avoid
            },
            success:function(res) {
                /* 对返回数据进行处理 */
                console.log(res);

            },
            error:function(){
                alert('请求失败，请检查网络设置')
            }
        });
    })
});