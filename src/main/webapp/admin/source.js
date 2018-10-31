//获取某个采集源的基本信息配置以及格式数据类型配置
$(".box_xxtabz").click(function(){
	$("input[name='cs_id']").val(this.id);
    $("input[name='format_add_cs_id']").val(this.id);
	huoqu();
    
});

function huoqu(){
    
    var cs_id = $("input[name='cs_id']").val();
   	var sourceFieldTable = $("#sourceFieldTable");
   	var formatTypeTable = $("#formatTypeTable");
   	
   	$.ajax({
   		url:"/wankangyuan/source/getSourceAll",
   		type:"post",
   		data:{
   			cs_id:cs_id
   		},
   		dataType:"json",
   		success : function(data){
   			if(data.result == true){
   				//设置数据源的名称
   				$("#cs_name").text(data.source.cs_name+'-配置');
   				$("#edit_cs_cs_id").val(data.source.cs_id);
   				if(data.source.is_view == 1){
   					$("#edit_cs_is_view").val('true');
   				}else{
   					$("#edit_cs_is_view").val('false');
   				}
   				$("#edit_cs_cs_name").val(data.source.cs_name);
   				//获取基本字段并展示
   				var sourceFields = data.source.sourceFields;
   				sourceFieldTable.empty();
   				
   				var str = '';
   				str+='<div class="biaotou">';
   				str+='<tr role="row">';
   				str+='<th class="biaotouth">';
   				str+='选择';
   				str+='</th>';
   				str+='<th class="biaotouth">字段名</th>';
   				str+='<th class="biaotouth">类型</th>';
   				str+='<th class="biaotouth">校验规则</th>';
   				str+='<th class="biaotouth">是否可枚举</th>';
   				str+='<th class="biaotouth">是否必填</th>';
   				str+='<th class="biaotouth">状态</th>';
   				str+='<th class="biaotouth">字段描述信息</th>';
   				str+='<th class="biaotouth">错误信息提示</th>';
   				str+='<th class="biaotouth">创建时间</th>';
   				str+='<th class="biaotouth">更新时间</th>';
   				str+='<th class="biaotouth">创建人</th>';
   				str+='<th class="biaotouth">更新人</th>';
   				str+='</tr>';
   				str+='</div>';
   				str+='<div class="biaoxiang">';
   				for(var i in sourceFields){
   					str+='<tr role="row" class="trbx" >';
   					str+='<th class="biaoxiangth"><input type="checkbox" class="xuanze source_field_checkbox" id="'+sourceFields[i].csf_id+'"></th>';
   					str+='<th class="biaoxiangth" id="csf_name'+sourceFields[i].csf_id+'">'+sourceFields[i].csf_name+'</th>';
					str+='<th class="biaoxiangth" id="type'+sourceFields[i].csf_id+'">'+sourceFields[i].type+'</th>';
					str+='<th class="biaoxiangth" id="check_rule'+sourceFields[i].csf_id+'">'+sourceFields[i].check_rule+'</th>';
					if(sourceFields[i].enumerated == false){
						str+='<th class="biaoxiangth" id="enumerated'+sourceFields[i].csf_id+'">否</th>';
					}else{
						str+='<th class="biaoxiangth" id="enumerated'+sourceFields[i].csf_id+'">是</th>';
					}
					
					if(sourceFields[i].not_null == false){
						str+='<th class="biaoxiangth" id="not_null'+sourceFields[i].csf_id+'">否</th>';
					}else{
						str+='<th class="biaoxiangth" id="not_null'+sourceFields[i].csf_id+'">是</th>';
					}
					if(sourceFields[i].is_view == false){
						str+='<th class="biaoxiangth" id="is_view'+sourceFields[i].csf_id+'">隐藏</th>';
					}else{
						str+='<th class="biaoxiangth" id="is_view'+sourceFields[i].csf_id+'">显示</th>';
					}
					str+='<th class="biaoxiangth" id="description'+sourceFields[i].csf_id+'">'+sourceFields[i].description+'</th>';
					
					str+='<th class="biaoxiangth" id="error_msg'+sourceFields[i].csf_id+'">'+sourceFields[i].error_msg+'</th>';
					str+='<th class="biaoxiangth">'+sourceFields[i].create_datetime+'</th>';
					
					if(sourceFields[i].update_datetime == null){
						sourceFields[i].update_datetime = "";
					}
					str+='<th class="biaoxiangth">'+sourceFields[i].update_datetime+'</th>';
					str+='<th class="biaoxiangth">'+sourceFields[i].creator+'</th>';
					
					if(sourceFields[i].updater == null){
						sourceFields[i].updater = "";
					}
					str+='<th class="biaoxiangth">'+sourceFields[i].updater+'</th>';

   					str+='</tr>';
   				}
   				str+='</div>';
   				sourceFieldTable.html(str);
   				
   				
   				//获取格式字段并展示
   				var formatTypes = data.source.formatTypes;
   				formatTypeTable.empty();
   				
   				var strFormatTypes = '';
   				strFormatTypes+='<div class="biaotou">';
   				strFormatTypes+='<tr role="row">';
   				strFormatTypes+='<th class="biaotouth">';
   				strFormatTypes+='选择';
   				strFormatTypes+='</th>';
   				strFormatTypes+='<th class="biaotouth">格式化数据类型名</th>';
   				strFormatTypes+='<th class="biaotouth">创建时间</th>';
   				strFormatTypes+='<th class="biaotouth">更新时间</th>';
   				strFormatTypes+='<th class="biaotouth">创建人</th>';
   				strFormatTypes+='<th class="biaotouth">更新人</th>';
   				strFormatTypes+='<th class="biaotouth">状态</th>';
   				strFormatTypes+='<th class="biaotouth">格式化数据类别</th>';
   				strFormatTypes+='<th class="biaotouth">操作</th>';
   				strFormatTypes+='</tr>';
   				strFormatTypes+='</div>';
   				strFormatTypes+='<div class="biaoxiang">';
   				for(var i in formatTypes){
   					strFormatTypes+='<tr role="row" class="trbx" >';
   					strFormatTypes+='<th class="biaoxiangth"><input type="checkbox" class="xuanze format_type_checkbox" id="'+formatTypes[i].ft_id+'"></th>';
   					strFormatTypes+='<th class="biaoxiangth" id="ft_name'+formatTypes[i].ft_id+'">'+formatTypes[i].ft_name+'</th>';
					strFormatTypes+='<th class="biaoxiangth" id="create_datetime'+formatTypes[i].ft_id+'">'+formatTypes[i].create_datetime+'</th>';
					strFormatTypes+='<th class="biaoxiangth" id="check_rule'+formatTypes[i].ft_id+'">'+formatTypes[i].update_datetime+'</th>';
					strFormatTypes+='<th class="biaoxiangth" id="creator'+formatTypes[i].ft_id+'">'+formatTypes[i].creator+'</th>';
					strFormatTypes+='<th class="biaoxiangth" id="updater'+formatTypes[i].ft_id+'">'+formatTypes[i].updater+'</th>';
					if(formatTypes[i].is_view == false){
						strFormatTypes+='<th class="biaoxiangth" id="is_view'+formatTypes[i].ft_id+'">隐藏</th>';
					}else{
						strFormatTypes+='<th class="biaoxiangth" id="is_view'+formatTypes[i].ft_id+'">显示</th>';
					}
					strFormatTypes+='<th class="biaoxiangth" id="floder'+formatTypes[i].ft_id+'">'+formatTypes[i].floder+'</th>';
					strFormatTypes+='<th class="biaoxiangth biaoxiangthin" onclick="enter('+formatTypes[i].ft_id+')">进入</th>';

   					strFormatTypes+='</tr>';
   				}
   				strFormatTypes+='</div>';
   				formatTypeTable.html(strFormatTypes);

   			}else{
   				alert(data.message);
   			}
   		},
   		error : function(){
   			alert("网络异常，请稍后重试！");
   		}
   		
   	});
}

$("#editSourceSubmit").click(function (){
	var cs_id = $("#edit_cs_cs_id").val();
	var is_view = $("#edit_cs_is_view").val();
	var cs_name = $("#edit_cs_cs_name").val();
	$.ajax({
		url:"/wankangyuan/source/updateSource",
		type:"post",
		data:{
			cs_id:cs_id,
			cs_name:cs_name,
			is_view:is_view
		},
		dataType:"json",
		success : function(data){
			if(data.result == true){
				alert(data.message);
				//window.location.href="/wankangyuan/admin/formatdata?cs_id="+cs_id;
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

$("#deleteSourceSubmit").click(function (){
	var cs_id = $("input[name='cs_id']").val();
	$.ajax({
		url:"/wankangyuan/source/deleteSource",
		type:"post",
		data:{
			cs_id:cs_id
		},
		dataType:"json",
		success : function(data){
			if(data.result == true){
				alert(data.message);
				//window.location.href="/wankangyuan/admin/formatdata?cs_id"+cs_id;
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



//新增数据源基本信息字段提交按钮，OK
$("#insertSourceFieldSubmit").click(function (){
	$.ajax({
		url:"/wankangyuan/sourceField/insertSourceField",
		type:"post",
		dataType:"json",
		data:{
			cs_id:insertSourceFieldForm.cs_id.value,
			csf_name:insertSourceFieldForm.csf_name.value,
			type:insertSourceFieldForm.type.value,
			check_rule:insertSourceFieldForm.check_rule.value,
			enumerated:insertSourceFieldForm.enumerated.value,
			not_null:insertSourceFieldForm.not_null.value,
			description:insertSourceFieldForm.description.value,
			error_msg:insertSourceFieldForm.error_msg.value,
			is_view:insertSourceFieldForm.csfield_add_is_view.value,
			uid:'1'
		},
		success : function(data){
			if(data.result == true){
				//window.location.href="/wankangyuan/admin/formatdata?cs_id="+insertSourceFieldForm.cs_id.value;
				insertSourceFieldForm.csf_name.value="";
				insertSourceFieldForm.check_rule.value="";
				insertSourceFieldForm.description.value="";
				insertSourceFieldForm.error_msg.value="";
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

//更新数据源基本信息字段提交按钮，OK
$("#updateSourceFieldSubmit").click(function (){

	$.ajax({
		url:"/wankangyuan/sourceField/updateSourceField",
		type:"post",
		dataType:"json",
		data:{
			csf_id:updateSourceFieldForm.edit_csf_id.value,
			csf_name:updateSourceFieldForm.edit_csf_name.value,
			type:updateSourceFieldForm.edit_type.value,
			check_rule:updateSourceFieldForm.edit_check_rule.value,
			enumerated:updateSourceFieldForm.edit_enumerated.value,
			not_null:updateSourceFieldForm.edit_not_null.value,
			description:updateSourceFieldForm.edit_description.value,
			error_msg:updateSourceFieldForm.edit_error_msg.value,
			is_view:updateSourceFieldForm.csfield_edit_is_view.value,
			uid:'1'
		},
		success : function(data){
			if(data.result == true){
				alert("数据源字段更新成功！");
				//window.location.href="/wankangyuan/admin/formatdata?csf_id="+updateSourceFieldForm.edit_csf_id.value;
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

//删除采集源基本信息字段，OK
$("#deleteSourceFieldSubmit").click(function (){
	
	//此处重新获取一遍ids
	var ids = [];
	var source_field_checkbox=document.querySelectorAll('.source_field_checkbox');
	for(var i = 0 ;  i < source_field_checkbox.length ; i++){
		if(source_field_checkbox[i].checked == true){
			ids.push(source_field_checkbox[i].id);
		}
	}
	//进行ajax请求
	$.ajax({
		url:"/wankangyuan/sourceField/deleteSourceField",
		type:"post",
		data:{
			csf_ids:ids.join(",")
		},
		dataType:"json",
		success : function(data){
			if(data.result == true){
				//进行删除成功后的跳页处理
				alert(data.message);
				//window.location.href="/wankangyuan/admin/formatdata?csf_ids="+csf_ids;
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