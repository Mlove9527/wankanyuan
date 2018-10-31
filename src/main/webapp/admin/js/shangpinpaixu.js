
$(function() {
    //获取id
    var shopId = GetQueryString('shopId');

    /* 菜单分类数组 */
    var menuTypeDataArr;

    $.ajax({
        type: "post",
        url: path + "Goods/getGoodList",
        async: true,
        data: {
            "admin_user": username,
            'token': token,
            'mct_id': shopId,
        },
        success: function(res) {
            if(res.error_code == '0000') {
                console.log(res.data);
                menuTypeDataArr = res.data;
                /* 获取菜单分类 */
                var menuTypeStr = '';
                for(var i in menuTypeDataArr) {
                    menuTypeStr += '<li id="' + menuTypeDataArr[i].gc_id + '">' + menuTypeDataArr[i].gc_name + '</li>'
                }
                $('.menu-type-box').html(menuTypeStr);
                $('.menu-type-box li').eq(0).addClass('menu-type-active');

                /* 获取相应分类下的菜 */
                var typeName = $('.menu-type-box li').eq(0).html();
                // getFoodList(menuTypeDataArr,typeName);
            } else {
                checkCode(res.error_code, res.message);
            }
        },
        error: function() {
            alert('请求失败，请检查网络设置');
        }
    });
    $('.menu-type-box').on('click', 'li', function() {
        // console.log(menuTypeDataArr)
        $(this).addClass('menu-type-active').siblings().removeClass('menu-type-active');
        // getFoodList(menuTypeDataArr,$(this).html());
    });


    /* 商品排序   */

        $.ajax({
            type: "post",
            url: path + "Goods/getGoodList",
            async: false,
            data: {
                /*
                 * 	mobile		是	string	用户名
                    token		是	string	商户token
                    usertype	是	string	用户类型 （1 用户 2 商户 3 配送员）
                 */
                'admin_user': username,
                'token': token,
                'mct_id': shopId
            },
            success: function(res) {
                if(res.error_code == '0000') {
                    // console.log(res.data);
                    menuTypeDataArr = res.data;
                    /* 获取菜单分类 */
                    var menuTypeStr = '';
                    for(var i in menuTypeDataArr) {
                        menuTypeStr += '<li id="' + menuTypeDataArr[i].gc_id + '">' + menuTypeDataArr[i].gc_name + '</li>'
                    }
                    $('.menu-type-box').html(menuTypeStr);
                    $('.menu-type-box li').eq(0).addClass('menu-type-active');

                    /* 获取相应分类下的菜 */
                    var typeName = $('.menu-type-box li').eq(0).html();
                    getSortFoodList(menuTypeDataArr,typeName);

                    $('#target').dargFlex('drag');

                } else {
                    checkCode(res.error_code, res.message);
                }
            },
            error: function() {
                alert('请求失败，请检查网络设置');
            }
        });

        $('.menu-type-box').off().on('click', 'li', function() {
            // console.log(menuTypeDataArr)
            $(this).addClass('menu-type-active').siblings().removeClass('menu-type-active');
            getSortFoodList(menuTypeDataArr,$(this).html());
        });

        /* 获取某一类的所有商品  */
        function getSortFoodList(dataArr, type) {
            for(var i in dataArr) {
                var foodTypeName = dataArr[i];
                if(foodTypeName.gc_name == type) {
                    // console.log(foodTypeName)
                    var menuFoodStr = '';
                    var foodNameArr = foodTypeName.goods;
                    // console.log(foodNameArr)
                    for(var j in foodNameArr){
                        menuFoodStr += 	'<li id="' + foodNameArr[j].g_id + '" class="drag">'
                            +		'<div class="goods-goodname">'
                            +			foodNameArr[j].g_name
                            +		'</div>'
                            +	'</li>';
                    }
                    $('.menu-food-box ul').html(menuFoodStr);
                    /* 商品排序 */
                }
            }

        }


        //提交排序
    $(".submit").click(function(){

        var dragArr = $('#target .drag');
        var dragIdArr = [];
        for (var i = 0; i < dragArr.length; i++) {
            dragIdArr.push(dragArr[i].id);
        }
        var idSort = '[' + dragIdArr + ']';
        $.ajax({
            type:"post",
            url: path + "Goods/sortGoods",
            async:true,
            data:{
                /*
                 * mobile		是	string	用户名
                 * token		是	string	商户token
                 * usertype		是	string	用户类型 （1 用户 2 商户 3 配送员）
                 * sort_rule	是	string	商品分类排序规则 数字为gc_id
                 */
                'admin_user': username,
                'token': token,
                'mct_id': shopId,
                'sort_rule':idSort,
                'gc_id':$('.menu-type-active').prop('id')
            },
            success:function(res){
                if(res.error_code == '0000'){
                    alert('排序成功');
                } else {
                    checkCode(res.error_code,res.message);
                }
            },
            error:function(){
                alert('排序失败，请检查网络设置');
            }
        });

    }); 




    /* 自定义滚动条 */
    $("#boxscroll").niceScroll({
       cursorborder: "",
       cursoropacitymax: 0.7,
       cursorcolor: "#CCC",
       boxzoom: true
    });
});







