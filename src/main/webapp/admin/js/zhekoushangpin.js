//获取id
var id = GetQueryString('id');
city_code =""

/* 获取商圈列表   */
var shangquanArr = getShangquan();
var shangquanHtmlStr = '';
for (var i in shangquanArr) {
    shangquanHtmlStr += '<option value="' + shangquanArr[i] + '">' + shangquanArr[i] + '</option>';
}
$('#business_circle').html(shangquanHtmlStr)
//创建人
$(".username").val(username);

if(id){
    $.ajax({
        type:"post",
        url:path + "Activity/getActivityInfo",
        async:false,
        data:{
            'admin_user':username,
            'token':token,
            'activity_id':id
        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res)
            var data = res.data;
            //是否显示修改
            if(data.status==1){
                $(".save").prop("hidden",false)
            }else {
                $(".save").prop("hidden",true)
            }
            //活动名称
            $(".activityname").val(data.title)
            //活动描述
            $(".desc").val(data.desc);
            //活动开始日期
            $(".startdate").val(data.startdate);
            //活动结束日期
            $(".enddate").val(data.enddate);
            // console.log(data.city)
            //城市
            city_code = data.city_code;
            console.log(city_code);
            //商圈
            $("#business_circle").val(data.business_circle);
            // //起送价格不低于
            // $(".price_limit").val(data.price_limit);
            //全场折扣
            $(".all").val(data.rule);
            //热度承担
            $(".plat_pay").val(data.plat_pay);
            //商家承担
            $(".mct_pay").val(data.mct_pay)
        },
        err:function(){
            //alert('请求失败，请检查网络设置')
        }
    });
}
//保存按钮
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
        var city = $("#city").val()||$("#province").val();
        var city_code = $("#city").find("option:selected").attr("data-code")||$("#province").find("option:selected").attr("data-code")
        // //配送方式
        // var delivery_type=$("#peisong").val()
        //商圈
        var business_circle =$("#business_circle").val();
        // //起送价格不低于
        // var price_limit = $(".price_limit").val()
        //商家承担
        var mct_pay = $(".mct_pay").val();
        //热度承担
        var plat_pay = $(".plat_pay").val();
        //活动2 全场多少折
        var rule = $(".all").val();

        //时间限制
        var startdate = $(".startdate").val();
        var endate = $(".enddate").val();
        if(startdate>endate){
            alert("请输入正确时间")
        }else{
            var date = new Date();
            var year = date.getFullYear();

            var month = date.getMonth()+1
            if(month<10){
                month= "0"+month;
            }
            var day=date.getDate();
            if(day<10){
                day = "0"+day
            }
            console.log(day)
            console.log(month)
            console.log(year)
            date = year+"-"+month+"-"+day
            console.log(date)


            if(endate<date){
                alert('请输入正确时间')

            }else{
                if(id){
                    $.ajax({
                        type:"post",
                        url:path + "Activity/editPlatActivity",
                        async:true,
                        data:{
                            "admin_user": username,
                            'token': token,
                            'type':1,
                            'activity_id':id,
                            'title':activityname,
                            'desc':desc,
                            'startdate':startdate,
                            'enddate':enddate,
                            'city':city,
                            'city_code':city_code,
                            // 'delivery_type':delivery_type,
                            'business_circle':business_circle,
                            // 'price_limit':price_limit,
                            'mct_pay':mct_pay,
                            'plat_pay':plat_pay,
                            'rule':rule

                        },
                        success:function(res){
                            /* 对返回数据进行处理 */
                            console.log(res)
                            if(res.error_code==000){
                                alert('修改成功')
                            }

                        },
                        err:function(){
                            alert('请求失败，请检查网络设置')
                        }
                    });
                }else{
                    $.ajax({
                        type:"post",
                        url:path + "Activity/addPlatActivity",
                        async:true,
                        data:{
                            "admin_user": username,
                            'token': token,
                            'type':1,
                            'title':activityname,
                            'desc':desc,
                            'startdate':startdate,
                            'enddate':enddate,
                            'city':city,
                            'city_code':city_code,
                            // 'delivery_type':delivery_type,
                            'business_circle':business_circle,
                            // 'price_limit':price_limit,
                            'mct_pay':mct_pay,
                            'plat_pay':plat_pay,
                            'rule':rule

                        },
                        success:function(res){
                            /* 对返回数据进行处理 */
                            console.log(res)
                            if(res.error_code==000){
                                alert('添加成功')
                            }

                        },
                        err:function(){
                            alert('请求失败，请检查网络设置')
                        }
                    });
                }
            }
        }



    });


