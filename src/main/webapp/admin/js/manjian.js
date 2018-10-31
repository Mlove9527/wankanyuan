//添加元素
$(".add").click(function(){
    //console.log(111);
    var len=$(".time").height();
    console.log(len);
    var html='';
    html+="<div class='time remove'>"+"<ul style='list-style:none'>"+
        "<li class=''>"+
        "<p>满足金额（元）</p>"+
        "<input type='text' class='form-control satisfy' id='rdate5' placeholder='满足金额'>"+"</li>"+
        "<li class='jianmian'>"+
        "<p>减免金额（元）</p>"+
        "<input type='text' class='form-control reduce' id='rdate6' placeholder='减免金额'>"+
        "</li>"+
        "<li class='fu'>"+"<p></p>"+
        "<p class='jian'>" +
        "<img src='images/jian.png' alt=''>"
        +"</p>"+
        "</li>"+
        "</ul>"+"</div>";

    $("#tt").append(html);

});

$("#tt").on('click','.jian',function(){

    $(this).parents('.remove').remove();

});

//请求数据
var arr = [];
$("#bat").click(function(){
 //console.log($("#rdate3"));
    var num = "";
    var nu = "";
    var nn= "";
    nu = $(".satisfy");
    nn = $(".reduce");
    console.log(nn);
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
//获取店铺id
    var shopId = GetQueryString('shopId');
    $.ajax({
        type:"post",
        url:path + "/Activity/createActivity",
        async:true,
        data:{
            'admin_user':username,
            'token':token,
            'mct_id':shopId,
            'usertype':2,
            'a_name': $("#active").val(),
            'a_type':1,
            'a_startdate':$("#rdate1").val(),
            'a_enddate':$("#rdate2").val(),
            'a_rule':num1

        },
        success:function(res){
            /* 对返回数据进行处理 */
            console.log(res.message);
            alert(res.message)
            // if(res.error_code==000){
            //     alert('创建成功')
            // }else{
            //     alert('创建失败')
            // }

        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });
});

$("#btn").click(function(){
    window.location.href=linkPath + 'SelfbuiltActivities/selfBuild.html';
});

