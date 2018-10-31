$(function(){
	var admin_id;
	
	/**************************** 获取管理员列表   ****************************/
    function getOrderList(){
        var newDataArr;
        $.ajax({
            type:"post",
            url:path + "AdminUser/getAdminList",
            async:false,
            data:{
                "admin_user":username,
                "token":token
            },
            success:function(res){
                if(res.error_code == '0000'){
                    //console.log(res.data)
                    var dataArr = res.data;
                    newDataArr = [];
                    for (var i in dataArr) {
                        var obj = {};
                        obj.realname = dataArr[i].realname;
                        obj.admin_user = dataArr[i].admin_user;
                        obj.admin_mobile = dataArr[i].admin_mobile;
                        obj.role_name = dataArr[i].role_name;
                        obj.m_date = dataArr[i].m_date;
                        if(dataArr[i].status == 1){
                            obj.status = '<span class="label user-status">正常</span>';
                        }else{
                            obj.status = '<span class="label user-status">冻结</span>';
                        }
                        obj.manage = '<span class="btn btn-small btn-info btn-change">修改</span>'
                        		   + '<input type="hidden" value="' + dataArr[i].admin_id + '">';
                        newDataArr.push(obj);
                    }
                } else {
                    checkCode(res.code,res.message);
                }
            },
            error:function(){
                errorFn();
            }
        });
        return newDataArr;
    }
    
    var data = getOrderList();

    var table = $("#example").dataTable({
        data:data,
        pagingType:"full_numbers",
        //列表表头字段
        columns: [
            {"data": "realname"},
            {"data": "admin_user"},
            {"data": "admin_mobile"},
            {"data": "status"},
            {"data": "role_name"},
            {"data": "m_date"},
            {"data": "manage"}
        ]
    });
    
    /******************************* 获取角色列表   在common.js中  *******************************/
    getJueseList();
    
    /******************************* 编辑管理员  *******************************/
   	$('#example').on('click','.btn-change',function(){
   		admin_id = $(this).siblings('input').val();
   		$(".meng").css("display", "block");
		$(".mengxiang").css("display", "block");
		/* 获取管理员信息  */
		$.ajax({
			type:"post",
			url:path + "AdminUser/getAdminInfo",
			async:true,
			data:{
				'admin_user':username,
				'token':token,
				'admin_id':admin_id
			},
			success:function(res){
				if (res.error_code == '0000') {
					var info = res.data;
					$('#xm').val(info.realname);
					$('#yhm').val(info.admin_user);
					$('#dh').val(info.admin_mobile);
					$('#zt option[value="' + info.status + '"]').prop('selected',true);
					$('#selectError1 option[value="' + info.role_id + '"]').prop('selected',true);
				} else{
					checkCode(res.error_code,res.message);
				}
			},
			error:function(){
				errorFn();
			}
		});
   	})
   	/******************************* 更新管理员  *******************************/
   	$('.que').click(function(){
   		$.ajax({
   			type:"post",
   			url: path + "AdminUser/modifyAdmin",
   			async:true,
   			data:{
				'admin_user':username,
				'token':token,
				'admin_id':admin_id,
				'realname':$('#xm').val(),
				'admin_mobile':$('#dh').val(),
				'role_id':$('#selectError1').val(),
				'status':$('#zt').val()
			},
			success:function(res){
				if (res.error_code == '0000') {
					$(".meng").css("display", "none");
					$(".mengxiang").css("display", "none");
					alert(res.message);
				} else{
					checkCode(res.error_code,res.message);
				}
			},
			error:function(){
				errorFn();
			}
   		});
   	})
})