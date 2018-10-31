

//新增数据源格式数据字段提交按钮，OK
$("#insertFormatTypeSubmit").click(function (){
	$.ajax({
		url:"/wankangyuan/formatType/insertFormatType",
		type:"post",
		dataType:"json",
		data:{
			cs_id:insertFormatTypeForm.format_add_cs_id.value,
			ft_name:insertFormatTypeForm.format_add_ft_name.value,
			is_view:insertFormatTypeForm.format_add_is_view.value,
			floder:insertFormatTypeForm.format_add_floder.value,
			uid:'1'
		},
		success : function(data){
			if(data.result == true){
				alert(data.message);
				//window.location.href="/wankangyuan/admin/formatdata?cs_id="+insertFormatTypeForm.format_add_cs_id.value;
				insertFormatTypeForm.format_add_ft_name.value="";
				insertFormatTypeForm.format_add_floder.value="";
				huoqu();
			}else{
				alert(data.message);
			}
		},
		error : function(){
			alert("网络异常，请稍后重试！");
		}
	});		
});


//编辑数据源格式数据字段提交按钮
$("#updateFormatTypeFormSubmit").click(function (){
	
	$.ajax({
		url:"/wankangyuan/formatType/updateFormatType",
		type:"post",
		dataType:"json",
		data:{
			ft_id:updateFormatTypeForm.edit_ft_id.value,
			ft_name:updateFormatTypeForm.edit_ft_name.value,
			is_view:updateFormatTypeForm.edit_is_view.value,
			floder:updateFormatTypeForm.edit_floder.value,
			uid:'1'
		},
		success:function(data){
			if(data.result == true){
				alert("格式数据类型更新成功！");
				//window.location.href="/wankangyuan/admin/formatdata?ft_id="+updateFormatTypeForm.edit_ft_id.value;
				huoqu();
			}else{
				alert(data.message);
			}
		},
		error:function(){
			alert("网络异常，请稍后重试！");
		}
	});
});

//删除格式化数据类型，NOT OK
$("#deleteFormatTypeSubmit").click(function (){
	
	//此处重新获取一遍ids
	var ids = [];
	var source_field_checkbox=document.querySelectorAll('.format_type_checkbox');
	for(var i = 0 ;  i < source_field_checkbox.length ; i++){
		if(source_field_checkbox[i].checked == true){
			ids.push(source_field_checkbox[i].id);
		}
	}
	//进行ajax请求
	$.ajax({
		url:"/wankangyuan/formatType/deleteFormatType",
		type:"post",
		data:{
			ft_ids:ids.join(",")
		},
		dataType:"json",
		success : function(data){
			if(data.result == true){
				//进行删除成功后的跳页处理
				alert(data.message);
				//window.location.href="/wankangyuan/admin/formatdata?ft_ids="+ft_ids;
				huoqu();

			}else{
				alert(data.message);
			}
		},
		error : function(){
			alert("网络异常，请稍后重试！");
		}	
	});
});




//进入到格式字段列表
function enter(id){
	var ft_id = id;
	//此处需要去数据库中查询当前格式类型下面的格式字段列表并对弹出的格式字段列表进行更新展示。
	$.ajax({
		url:"/wankangyuan/formatType/getFormatFields",
		type:"post",
		data:{
			ft_id:ft_id
		},
		dataType:"json",
		success : function(data){
			if(data.result == true){
				
				//获取格式字段列表
				var formatFields = data.formatType.formatFields;
				//获取显示格式字段列表的DIV
				var formatFieldsTable = $("#formatFieldsTable");
				formatFieldsTable.empty();
				var str = '<div class="biaotou">';
				str += '<tr role="row">';
				str += '<th class="biaotouth">';
				str += '选择';
				str += '</th>';
				str += '<th class="biaotouth">metainfo</th>';
				str += '<th class="biaotouth">字段名</th>';
				str += '<th class="biaotouth">类型</th>';
				str += '<th class="biaotouth">校验规则</th>';
				str += '<th class="biaotouth">可枚举</th>';
				str += '<th class="biaotouth">必填</th>';
				str += '<th class="biaotouth">显示</th>';
				str += '<th class="biaotouth">字段描述</th>';
				str += '<th class="biaotouth">错误提示</th>';
				str += '<th class="biaotouth">创建时间</th>';
				str += '<th class="biaotouth">创建人</th>';
				str += '<th class="biaotouth">更新时间</th>';
				str += '<th class="biaotouth">更新人</th>';
				str += '</tr>';
				str += '</div>';
				str += '<div class="biaoxiang">';
				for( var i in formatFields){
					str += '<tr role="row" class="trbx">';
					str += '<th class="biaoxiangth"><input type="checkbox" class="xuanze format_field_checkbox" id="'+formatFields[i].ff_id+'"></th>';
					
					if(formatFields[i].is_meta == false){
						str+='<th class="biaoxiangth" id="ff_is_meta'+formatFields[i].ff_id+'">否</th>';
					}else{
						str+='<th class="biaoxiangth" id="ff_is_meta'+formatFields[i].ff_id+'">是</th>';
					}
					
					
					str += '<th class="biaoxiangth" id="ff_ff_name'+formatFields[i].ff_id+'">'+formatFields[i].ff_name+'</th>';
					str += '<th class="biaoxiangth" id="ff_type'+formatFields[i].ff_id+'">'+formatFields[i].type+'</th>';
					str += '<th class="biaoxiangth" id="ff_check_rule'+formatFields[i].ff_id+'">'+formatFields[i].check_rule+'</th>';
					
					if(formatFields[i].enumerated == false){
						str+='<th class="biaoxiangth" id="ff_enumerated'+formatFields[i].ff_id+'">否</th>';
					}else{
						str+='<th class="biaoxiangth" id="ff_enumerated'+formatFields[i].ff_id+'">是</th>';
					}
					
					if(formatFields[i].not_null == false){
						str+='<th class="biaoxiangth" id="ff_not_null'+formatFields[i].ff_id+'">否</th>';
					}else{
						str+='<th class="biaoxiangth" id="ff_not_null'+formatFields[i].ff_id+'">是</th>';
					}
					
					if(formatFields[i].is_view == false){
						str+='<th class="biaoxiangth" id="ff_is_view'+formatFields[i].ff_id+'">隐藏</th>';
					}else{
						str+='<th class="biaoxiangth" id="ff_is_view'+formatFields[i].ff_id+'">显示</th>';
					}
					
					str += '<th class="biaoxiangth" id="ff_description'+formatFields[i].ff_id+'">'+formatFields[i].description+'</th>';
					str += '<th class="biaoxiangth" id="ff_error_msg'+formatFields[i].ff_id+'">'+formatFields[i].error_msg+'</th>';
										
					str+='<th class="biaoxiangth">'+formatFields[i].create_datetime+'</th>';
					str+='<th class="biaoxiangth">'+formatFields[i].creator+'</th>';
					if(formatFields[i].update_datetime == ""){
						str+='<th class="biaoxiangth">'+formatFields[i].create_datetime+'</th>';
						str+='<th class="biaoxiangth">'+formatFields[i].creator+'</th>';
					}
					str+='<th class="biaoxiangth">'+formatFields[i].update_datetime+'</th>';
					str+='<th class="biaoxiangth">'+formatFields[i].updater+'</th>'
					
					
					

					
					str += '</tr>';
				}
				
				str += '</div>';
				formatFieldsTable.html(str);
				
				var oendconfigK=document.querySelectorAll('.endconfigK')[0];//配置结果类型框
		    	oendconfigK.style.display="block";
		    	$("#ft_id").val(id);//设置进入的格式类型ID
		    	
			}else{
				alert(data.message);
			}
			
			
		},
		error : function(){
			alert("网络异常，请稍后重试！");
		}
	});
}