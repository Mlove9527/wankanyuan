//获取店铺id
var shopId = GetQueryString('shopId');


$(function() {

    /* 菜单分类数组 */
    var menuTypeDataArr;

    /* 自定义滚动条 */
    $("#boxscroll").niceScroll({
        cursorborder: "",
        cursoropacitymax: 0.7,
        cursorcolor: "#CCC",
        boxzoom: true
    });


    /* 获取商品 */

    $.ajax({
        type: "post",
        url: path + "Goods/getGoodList",
        async: true,
        data: {
            /*
             * 	mobile		是	string	用户名
             token		是	string	商户token
             usertype	是	string	用户类型 （1 用户 2 商户 3 配送员）
             */
            'admin_user':username,
            'token':token,
            'mct_id':1,
            'activity_type':1
        },
        success: function(res) {
            if(res.error_code == '0000') {
                 //console.log(res.data);
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
                getFoodList(menuTypeDataArr,typeName);
                //console.log(getFoodList(menuTypeDataArr,typeName))
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
        getFoodList(menuTypeDataArr,$(this).html());
    });


    /* 获取某一类的所有商品  */

    function getFoodList(dataArr, type) {
        // console.log(dataArr)
        for(var i in dataArr) {
            var foodTypeName = dataArr[i];
            if(foodTypeName.gc_name == type) {
                 console.log(foodTypeName)
                var menuFoodStr = '';
                var menuFoodStr1 = '';
                var menuFoodStr2 = '';
                var foodNameArr = foodTypeName.goods;
                for(var j in foodNameArr){
                    if(foodNameArr[j].g_shelves == 1){
                          if(arid.indexOf(foodNameArr[j].g_id)!=-1){
                              menuFoodStr += 	'<li>'
                                  +		'<div class="choose-box" >'
                                  +'<img  src="images/select1.png" class="pic" id="' + foodNameArr[j].g_id + '" alt="">'
                                      //+			'<input id="' + foodNameArr[j].g_id + '" type="checkbox" class="checkbix" data-shape="circled" data-size="large" data-color="blue" data-text="">'
                                  +		'</div>'
                                  +		'<div class="food-detail">'
                                  +			'<div class="food-name" >'
                                  +				foodNameArr[j].g_name
                                  +			'</div>'
                                  +			'<div class="food-price-num">'
                                  +				'<div class="food-old-price">'
                                  +					foodNameArr[j].g_price
                                  +				'</div>'
                                  +				'<div class="food-new-price">'
                                  +					foodNameArr[j].g_price
                                  +				'</div>'
                                  +				'<div class="food-num">'
                                  +					'活动库存'
                                  +					'<input type="text" placeholder="1-1000" value="' + foodNameArr[j].g_stock + '" />'
                                  +					'份'
                                  +				'</div>'
                                  +			'</div>'
                                  +		'</div>'
                                  +	'</li>';
                          }else if(foodNameArr[j].g_shelves !=-1){
                              menuFoodStr += 	'<li>'
                                  +		'<div class="choose-box" >'
                                  +'<img  src="images/circle.png" class="pic" id="' + foodNameArr[j].g_id + '" alt="">'
                                      //+			'<input id="' + foodNameArr[j].g_id + '" type="checkbox" class="checkbix" data-shape="circled" data-size="large" data-color="blue" data-text="">'
                                  +		'</div>'
                                  +		'<div class="food-detail">'
                                  +			'<div class="food-name" >'
                                  +				foodNameArr[j].g_name
                                  +			'</div>'
                                  +			'<div class="food-price-num">'
                                  +				'<div class="food-old-price">'
                                  +					foodNameArr[j].g_price
                                  +				'</div>'
                                  +				'<div class="food-new-price">'
                                  +					foodNameArr[j].g_price
                                  +				'</div>'
                                  +				'<div class="food-num">'
                                  +					'活动库存'
                                  +					'<input type="text" placeholder="1-1000" value="' + foodNameArr[j].g_stock + '" />'
                                  +					'份'
                                  +				'</div>'
                                  +			'</div>'
                                  +		'</div>'
                                  +	'</li>';
                          }
                    }

                }
                $('.menu-food-box ul').html(menuFoodStr);
            }
        }
    }

});
//点击选择商品
var arid = [];
var arid1 = [];
var arr = [];
var arr1 = [];
var nu  = 0;
var num1 = "";
$("#boxscroll").on('click','.choose-box',function(){
    //alert('hello')
    var num = "";
    var id = "";
    var val = "";
    var id1 = "";
    var val1 = "";

    var num2 = "";
    var num3 = "";
    var nuid="";
    if(($(this).children('img').attr('src'))=="images/circle.png"){
        $(this).children("img").attr('src','images/select1.png');
        id = ($(this).children('img').attr('id'));
        for(var i =0;i<=arid.length;i++){
            if($.inArray(id,arid)==-1){
                arid.push(id);
                console.log(arid)
            }
        }
        //console.log(id);
        val = $(this).siblings('.food-detail').children('.food-price-num').children('.food-num').children('input').val();
        //console.log(val)
        num = id +"-"+val;
        console.log(num)
        for(var i = 0;i<=arr.length;i++){
            if($.inArray(num,arr)==-1){
                arr.push(num);
                nu++;
            }
        }

    }else if(($(this).children('img').attr('src'))=="../image/select1.png"){
        nu--;
        $(this).children("img").attr('src','../image/circle.png');
        id1 = ($(this).children('img').attr('id'));
        arid1.push(id1);
        for(var i =0;i<=arid.length;i++){
            for(var j=0;j<=arid1.length;j++){
                if(arid1[j]==arid[i]){
                    arid.splice(i,1);
                }
            }

        }
        val1 = $(this).siblings('.food-detail').children('.food-price-num').children('.food-num').children('input').val();
        num2 = id1 +"-"+val1;
        arr1.push(num2);
        for(var i = 0;i<=arr.length;i++){
            for(j = 0;j<arr1.length;j++){
                if(arr[i]==arr1[j]){
                    arr.splice(i,1);
                }
            }
        }

    }
    //console.log(arr);
    //console.log(arid);
    num1 = arr.toString();
    //console.log(num1);
    $(".selected").html(nu);
});


//按钮下一步
//ajax发送数据
$("#sign").click(function(){
    //console.log($("#select").val())

    $.ajax({
        type:"post",
        url:path + "Goods/joinActivity",
        async:true,
        data:{
            'admin_user':username,
            'token':token,
            'mct_id':shopId,
            'activity_type': 1,
            'activity_id':140,
            'a_type':3,
            'g_id':num1,
            'a_discount_type':1,
            'a_discount':'12',
            'a_name':"天天特价",
            'a_startdate':"2017-12-12",
            'a_enddate':"2017-12-14"

        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res.message)
        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });

});
