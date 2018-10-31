$(function(){
    //获取商圈
    var shangquanArr = getShangquan();
    var shangquanHtmlStr = '';
    for (var i in shangquanArr) {
        shangquanHtmlStr += '<option value="' + shangquanArr[i] + '">' + shangquanArr[i] + '</option>';
    }
    $('#shangq').html(shangquanHtmlStr);

	var name = '';
	var shangquan = '';
	var line = '';
    //获取配送员列表
    function getOrderList() {
        var newDataArr;
        $.ajax({
            type: "post",
            url: path + "Delivery/getDeliveryList",
            async: false,
            data: {
                "admin_user": username,
                'token': token,
                'd_name': name,
                'mct_business_circle':shangquan,
                'online':line
            },
            success: function (res) {
                /* 对返回数据进行处理 */
                console.log(res);
                //当前在线人数
                $(".online").html(res.online_count);
                $(".outline").html(res.out_line);
                var dataArr = res.data;
                console.log(dataArr);
                newDataArr = [];

                for (var i in dataArr) {
                    var obj = {};
                    obj.d_id= dataArr[i].d_id;
                    obj.d_name = dataArr[i].d_name;
                    obj.d_phone = dataArr[i].d_phone;
                    obj.mct_business_circle = dataArr[i].mct_business_circle;
                    obj.d_finish_count=dataArr[i].d_finish_count;
                    obj.d_balance = dataArr[i].d_balance +'元';


                    obj.caozuo = '<button class="btn btn-mini btn-success edit" id="'+dataArr[i].d_id+'">编辑</button>'+
                                  '<button class="btn btn-mini btn-warning dele" style="margin-left:10px" id="'+dataArr[i].d_id+'">删除</button>'+
                                  '<button class="btn btn-mini btn-primary resetpass" style="margin-left:10px" id="'+dataArr[i].d_id+'">重置密码</button>';

                    newDataArr.push(obj);

                }


            },
            error: function () {
                alert('请求失败，请检查网络设置')
            }
        });
        // console.log(newDataArr);
        return newDataArr;
    }

	function showOrderList(){
		var data = getOrderList();
	
	    var table = $("#example").dataTable({
	
	        data:data,
	        autoWidth:false,
	        pagingType:"full_numbers",
	        //列表表头字段
	
	        columns: [
	            {"data": "d_id"},
	            {"data": "d_name"},
	            {"data": "d_phone"},
	            {"data": "mct_business_circle"},
	            {"data": "d_finish_count"},
	            {"data": "d_balance"},
	            {"data": "caozuo"}
	        ]
	
	
	    });
	}
    
	showOrderList();

    //查询
    $(".chaxun").click(function(){
        //骑士名称
        name = $(".d_name").val();
        //商圈
        shangquan =$("#shangq").val();
        //是否在线
        line = $("#line").val();
        
        if($('#example_wrapper')){
			$('#example_wrapper').remove();
			var tableEle = $('<table id="example" class="table table-bordered table-striped table-condensed">'+
					'<thead>'+
						'<tr>'+
							'<th style="width:200px">账号</th>'+
                            '<th style="width:200px">姓名</th>'+
                            '<th style="width:200px">电话号</th>'+
                            '<th style="width:200px">商圈</th>'+
                            '<th style="width:200px">完成订单数</th>'+
                            '<th style="width:200px">余额</th>'+
                            '<th style="width:200px">操作</th>'+
						'</tr>'+
					'</thead>'+
					'<tbody></tbody>'+
				'</table>');
			$('#example-bottom').before(tableEle);
		}

        showOrderList();
    })



    //添加配送员
    $(".addpei").click(function(){
        $(".meng").css("display","block");
        $(".mengxiang").css("display","block")
        //获取商圈列表
        //获取商圈
        var shangquanArr = getShangquan();
        var shangquanHtmlStr = '';
        for (var i in shangquanArr) {
            shangquanHtmlStr += '<option value="' + shangquanArr[i] + '">' + shangquanArr[i] + '</option>';
        }
        $('#mct_business_circle').html(shangquanHtmlStr);

    })


    //保存配送员信息
    $(".baocun").click(function(){


        //商圈
        var mct_business_circle = $("#mct_business_circle").val();
        var d_name = $(".nam").val();
        var id_card = $(".id_card").val();
        var d_sex =""
        if($("#d_sex").val()=="男"){
            d_sex =1
        }else if($("#d_sex").val()=="女"){
            d_sex =2
        }
        var d_age = $(".d_age").val();
        var d_phone = $(".d_phone").val();
        var d_password =$(".d_password").val();

        $.ajax({
            type: "post",
            url: path + "Delivery/addDelivery",
            async: false,
            data: {
                "admin_user": username,
                'token': token,
                'd_name':d_name,
                'mct_business_circle':mct_business_circle,
                'id_card':id_card,
                'd_phone':d_phone,
                'd_sex':d_sex,
                'd_age':d_age,
                'd_password':d_password

            },
            success: function (res) {
                /* 对返回数据进行处理 */
                console.log(res);
                if(res.error_code==000){
                    alert('添加成功');
                    window.location.reload();
                }else{
                    alert('添加失败')
                }

            },
            error: function () {
                alert('请求失败，请检查网络设置')
            }
        });

        $(".meng").css("display","none");
        $(".mengxiang").css("display","none")

    })


    //编辑配送员信息
    $("#example").on('click','.edit',function(){

        $(".meng").css("display","block");
        $(".mengxiang1").css("display","block");

        //获取商圈
        var shangquanArr = getShangquan();
        var shangquanHtmlStr = '';
        for (var i in shangquanArr) {
            shangquanHtmlStr += '<option value="' + shangquanArr[i] + '">' + shangquanArr[i] + '</option>';
        }
        $('#mct_business_quan').html(shangquanHtmlStr);
        var id = $(this).attr('id');
        $.ajax({
            type: "post",
            url: path + "Delivery/getDelivery",
            async: false,
            data: {
                "admin_user": username,
                'token': token,
                'd_id':id

            },
            success: function (res) {
                /* 对返回数据进行处理 */
                console.log(res);
                var data = res.data;
                console.log(data)


                        $(".xing").val(data.d_name);
                        $(".phonenum").val(data.d_phone);
                        $("#mct_business_quan").val(data.mct_business_circle);
                        if(data.d_sexx ==1){
                            $("#sex").val('男')
                        }else if(data.d_sex ==2){
                            $("#sex").val('女')
                        }


                    $(".nianling").val(data.d_age);
                    $(".card").val(data.id_card);

            },
            error: function () {
                alert('请求失败，请检查网络设置')
            }
        });


        //保存按钮
        $(".save").click(function(){
            var mct_business_circle = $("#mct_business_quan").val();
            var d_name = $(".xing").val();
            var id_card = $(".card").val();
            var d_sex =""
            if($("#sexx").val()=="男"){
                d_sex =1
            }else if($("#d_sex").val()=="女"){
                d_sex =2
            }
            var d_age = $(".nianling").val();
            var d_phone = $(".phonenum").val();


            $.ajax({
                type: "post",
                url: path + "Delivery/editDelivery",
                async: false,
                data: {
                    "admin_user": username,
                    'token': token,
                    'd_id':id,
                    'd_name':d_name,
                    'mct_business_circle':mct_business_circle,
                    'id_card':id_card,
                    'd_phone':d_phone,
                    'd_sex':d_sex,
                    'd_age':d_age

                },
                success: function (res) {
                    /* 对返回数据进行处理 */
                    console.log(res);
                    if(res.error_code==000){
                        alert('修改成功');
                        window.location.reload();
                    }else{
                       checkCode(res.error_code,res.message);
                    }

                },
                error: function () {
                    alert('请求失败，请检查网络设置')
                }
            });
            $(".mengxiang1").css("display","none");
            $(".meng").css("display","none")
        })
    })


    //删除配送员信息
    $(".dele").click(function(){
        var did = $(this).attr('id');

        $.ajax({
            type: "post",
            url: path + "Delivery/delDelivery",
            async: false,
            data: {
                "admin_user": username,
                'token': token,
                'd_id': did

            },
            success: function (res) {
                /* 对返回数据进行处理 */
                console.log(res);
                if(res.error_code==000){
                    alert('删除成功')
                    window.location.reload();
                }else{
                    alert('删除失败')
                }

            },
            error: function () {
                alert('请求失败，请检查网络设置')
            }
        });
    })

    //重置密码
    $("#example").on('click','.resetpass',function(){

        var id =$(this).attr('id');


        $(".mengxiang2").css("display","block");
        $(".meng").css("display","block");

        $(".change").click(function(){

            var password = $(".word").val()

            $.ajax({
                type: "post",
                url: path + "Delivery/resetPassword",
                async: false,
                data: {
                    "admin_user": username,
                    'token': token,
                    'd_id':id,
                    'password':password

                },
                success: function (res) {
                    /* 对返回数据进行处理 */
                    console.log(res);
                    if(res.error_code==000){
                        alert('修改成功');
                        window.location.reload();
                    }else{
                        alert('添加失败')
                    }

                },
                error: function () {
                    alert('请求失败，请检查网络设置')
                }
            });
        })

    })
});