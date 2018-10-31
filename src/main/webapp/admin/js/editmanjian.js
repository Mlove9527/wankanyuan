//获取商铺id
var shopId = GetQueryString('shopId')


//添加元素
$(".tianjia").on('click','.add',function(){
   var html="";
    html+='<ul style="list-style: none;overflow:hidden ">'+
        '<li >'+
        '<p>满足金额（元）</p>'+
        '<input type="text" class="form-control satisfy" id="rdate3" placeholder="请输入金额">'+
        '</li>'+
        '<li class="jianmian">'+
        '<p>减免金额（元）</p>'+
        '<input type="text" class="form-control reduce" id="rdate4" placeholder="请输入金额">'+
        '</li>'+
        '<li class="fu">'+
        '<p></p>'+
        '<p class="jian">'+
        '<img src="images/jian.png" alt="">'+
        '</p>'+
        '</li>'+
        '</ul>'
    $(".tianjia").append(html);

});
//删除元素
$(".tianjia").on('click','.jian',function(){

    $(this).parents('ul').remove();
    //$(this).parents('.cha').remove();


});




//店铺满减编辑页
$(function(){
    $.ajax({
        type:"post",
        url:path + "Activity/getActivityList",
        async:true,
        data:{
            "admin_user": username,
            'token': token,
            'mct_id': shopId
        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res);
            var data= res.data;
            console.log(data);
            $.each(data,function(i,item){
            if(item.a_type==1){
            var reducemoney = item.a_rule;
            console.log( reducemoney);
            var moneyarry =reducemoney.split(",");

                var arraynum1 = moneyarry[0].split("-");
                console.log(arraynum1);
                var html1='';
                var html2='';
                html1+='<ul style="list-style: none;overflow:hidden">'+
                    '<li >'+
                    '<p>满足金额（元）</p>'+
                    '<input type="text" class="form-control satisfy" id="rdate3" value="' +arraynum1[0]+'">'+
                    '</li>'+
                    '<li class="jianmian">'+
                    '<p>减免金额（元）</p>'+
                    '<input type="text" class="form-control reduce" id="rdate4" value="' +arraynum1[1]+'">'+
                    '</li>'+
                    '<li class="fu">'+
                    '<p></p>'+
                    '<p class="add">'+
                    '<img src="images/add.png" alt="">'+
                    '</p>'+
                    '</li>'+
                    '</ul>'
                $(".tianjia").append(html1);

            for(var i = 1;i<moneyarry.length;i++){
                console.log(moneyarry[i])
                var arraynum = moneyarry[i].split("-");
                //console.log(arraynum)
                html2+='<ul style="list-style: none;overflow:hidden">'+
                    '<li >'+
                    '<p>满足金额（元）</p>'+
                    '<input type="text" class="form-control satisfy" id="rdate3" value="' +arraynum[0]+'">'+
                    '</li>'+
                    '<li class="jianmian">'+
                    '<p>减免金额（元）</p>'+
                    '<input type="text" class="form-control reduce" id="rdate4" value="' +arraynum[1]+'">'+
                    '</li>'+
                    '<li class="fu">'+
                    '<p></p>'+
                    '<p class="jian">'+
                    '<img src="images/jian.png" alt="">'+
                    '</p>'+
                    '</li>'+
                    '</ul>'
            }
            $(".tianjia").append(html2);
            $("#active").val(item.a_name);
            $("#rdate1").val(item.a_startdate);
            $("#rdate2").val(item.a_enddate);
            $(".name").attr("id",item.id);
            }
            })
        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});


$("#tt").on('click','.jian',function(){
    $(this).parents('.cha').siblings().remove();
    $(this).parents('.cha').remove();
});
$("#bat").click(function(){
    var num = "";
    var nu = "";
    var nn= "";
    var arr=[];
    nu = $(".satisfy");
    nn = $(".reduce");
    //console.log(nn);
    console.log(nu);
    for(var i = 0;i<nu.length;i++){
        console.log(nu[i].value);
        for(var j= 0;i<nn.length;i++){
            console.log(nn[i].value);
            num = nu[i].value+"-"+ nn[i].value;
            arr.push(num);
        }
    }
    num1 = arr.toString();
    console.log(num1);
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
            'a_type':1,
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),
            'a_rule':num1


        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res)

        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});


