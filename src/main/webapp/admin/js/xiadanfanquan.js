$(function(){
    /* 获取商圈列表   */
    var shangquanArr = getShangquan();
    var shangquanHtmlStr = '';
    for (var i in shangquanArr) {
        shangquanHtmlStr += '<option value="' + shangquanArr[i] + '">' + shangquanArr[i] + '</option>';
    }
    $('#business_circle').html(shangquanHtmlStr)
    //创建人
    console.log($(".username"));
    $(".username").val(username);

    //规则
    var rule_type=""
    $(".check").click(function(){
        $(this).parent().attr("class","checked");
        //console.log($(this).attr('id'))
        if($(this).attr('id')==1){
            rule_type=1
        }else if($(this).attr('id')==2){
            rule_type=2
        }
        console.log(rule_type);
    });



    $(".baocun").click(function(){
        //活动名称
        var activityname = $(".activityname").val()
        //活动描述
        var desc = $(".desc").val();
      //活动开始日期
        var startdate = $(".startdate").val();
        //活动结束日期
        var enddate = $(".enddate").val();
       //活动城市
        var city = $("#province").val()+"-"+$("#city").val();

       //配送方式
        var delivery_type=$("#peisong").val()
        //商圈
        var business_circle =$("#business_circle").val();
       //起送价格不低于
        var price_limit = $(".price_limit").val()
        //商家承担
        var mct_pay = $(".mct_pay").val();
        //热度承担
        var plat_pay = $(".plat_pay").val();
        //活动2 满多少元放券
        var rule = $(".fanquan").val();
       //优惠券设定
        var coupon_rule = "";
        var full = $(".full").val();
        var reduce = $(".reduce").val();
        coupon_rule = full+"-"+reduce;


         $.ajax({
             type:"post",
             url:path + "Activity/addPlatActivity",
             async:true,
             data:{
                 "admin_user": username,
                 'token': token,
                 'type':2,
                 'title':activityname,
                 'desc':desc,
                 'startdate':startdate,
                 'enddate':enddate,
                 'city':city,
                 'delivery_type':delivery_type,
                 'business_circle':business_circle,
                 'price_limit':price_limit,
                 'mct_pay':mct_pay,
                 'plat_pay':plat_pay,
                 'rule_type':rule_type,
                  'rule':rule,
                  'coupon_rule':coupon_rule




             },
             success:function(res){
                 /* 对返回数据进行处理 */
                 console.log(res)

             },
             err:function(){
                 alert('请求失败，请检查网络设置')
             }
         });
    })


})