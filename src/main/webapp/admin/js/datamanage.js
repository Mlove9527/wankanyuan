/* 
* @Author: Marte
* @Date:   2018-05-09 14:46:55
* @Last Modified by:   Marte
* @Last Modified time: 2018-05-16 00:02:19
*/

$(document).ready(function(){

    // 数据采集源选项卡切换
	
    var odatacollec=document.querySelectorAll('.datacollec')[0];//数据采集表

    var obox_xxtab=odatacollec.querySelectorAll('.box_xxtab')[0];
    var abox_xxtabz=obox_xxtab.querySelectorAll('.box_xxtabz');
    var obox_addxxtabz=obox_xxtab.querySelectorAll('.box_addxxtabz')[0];

    var obox_xytab=odatacollec.querySelectorAll('.box_xytab')[0];
    var abox_xytabz=obox_xytab.querySelectorAll('.box_xytabz');
    
    for(var i=0;i<abox_xxtabz.length;i++){
        (function(index){
            /*abox_xxtabz[index].onclick=function(){
                for(var j=0;j<abox_xytabz.length;j++){
                	abox_xxtabz[j].className="box_xxtabz";
                }
                abox_xxtabz[index].className="box_xxtabz active";
            }*/
            $(abox_xxtabz[index]).click(function(){
            	for(var j=0;j<abox_xxtabz.length;j++){
                	abox_xxtabz[j].className="box_xxtabz";
                }
                abox_xxtabz[index].className="box_xxtabz active";
            })
        })(i)
    }

    // 新增数据采集源
    var obox_addxxtabz=odatacollec.querySelectorAll('.box_addxxtabz')[0];//新增数据采集源按钮
    var oaddboxK=odatacollec.querySelectorAll('.addboxK')[0];//新增数据采集源框
    var oaddboxTx=oaddboxK.querySelectorAll('.addboxTx')[0];//新增数据采集源框关闭按钮
    var oaddboxBb=oaddboxK.querySelectorAll('.addboxBb')[0];//新增数据采集源框提交按钮
    var oaddboxlik=oaddboxK.querySelectorAll('.addboxlik')[0];//新增数据采集源框名称框

    var oaddboxPD=0;

    obox_addxxtabz.onclick=function(){
        if(oaddboxPD==0){
            oaddboxK.style.display="block";
            oaddboxPD=1;
            oaddboxlik.value="";
        }
    }
    oaddboxTx.onclick=function(){
            oaddboxK.style.display="none";
            oaddboxPD=0;
    }
    oaddboxBb.onclick=function(){
            // alert("提交成功")
            oaddboxK.style.display="none";
            oaddboxPD=0;
    }
    
    
    // 编辑数据采集源
    var obox_editxxtabz=odatacollec.querySelectorAll('.box_editxxtabz')[0];//编辑数据采集源按钮
    var oeditboxK=odatacollec.querySelectorAll('.editboxK')[0];//编辑数据采集源框
    var oaddboxTx=oeditboxK.querySelectorAll('.addboxTx')[0];//编辑数据采集源框关闭按钮
    var oaddboxBb=oeditboxK.querySelectorAll('.addboxBb')[0];//编辑数据采集源框提交按钮
    var oaddboxlik=oeditboxK.querySelectorAll('.addboxlik')[0];//编辑数据采集源框名称框

    var oeditboxPD=0;

    obox_editxxtabz.onclick=function(){
        console.log(1)
        if(oeditboxPD==0){
        	if($("input[name='cs_id']").val() == ''){
        		alert("请选择数据源！");
        	}else{
        		oeditboxK.style.display="block";
                oeditboxPD=1;
        	}
            
        }
    }
    oaddboxTx.onclick=function(){
            oeditboxK.style.display="none";
            oeditboxPD=0;
    }

    // 删除数据采集源
    var obox_delxxtabz=odatacollec.querySelectorAll('.box_delxxtabz')[0];//删除数据采集源按钮
    var odelboxK=odatacollec.querySelectorAll('.delboxK')[0];//删除数据采集源框
    var oaddboxTx=odelboxK.querySelectorAll('.addboxTx')[0];//删除数据采集源框关闭按钮
    var oaddboxBb=odelboxK.querySelectorAll('.addboxBb')[0];//删除数据采集源框提交按钮
    // var oaddboxlik=odelboxK.querySelectorAll('.addboxlik')[0];//删除数据采集源框名称框

    var odelboxPD=0;

    obox_delxxtabz.onclick=function(){
        console.log(1)
        if(odelboxPD==0){
        	
        	
        	if($("input[name='cs_id']").val() == ''){
        		alert("请选择数据源！");
        	}else{
        		odelboxK.style.display="block";
                odelboxPD=1;
                oaddboxlik.value="";
        	}

            
        }
    }
    oaddboxTx.onclick=function(){
            odelboxK.style.display="none";
            odelboxPD=0;
    }
    
    


   
    var abox_xytabzK=document.querySelectorAll('.box_xytabzK');//metainfo配置表
    // console.log(abox_xytabzK);
 // 新增metainfo
    var atableeditzadd=[];
    for(var i=0;i<abox_xytabzK.length;i++){
        var otableeditzadd=abox_xytabzK[i].querySelectorAll('.tableeditzadd')[0];
        // console.log(otableeditzadd)
        atableeditzadd.push(otableeditzadd);//新增metainfo按钮
    }
    // console.log(atableeditzadd);
    // var atableeditzadd=obox_xytabzK.querySelectorAll('.tableeditzadd');//新增metainfo按钮
    var oaddbiaoxK=document.querySelectorAll('.addbiaoxK')[0];//新增metainfo框
    var oaddbiaoxTx=oaddbiaoxK.querySelectorAll('.addbiaoxTx')[0];//新增metainfo框关闭按钮
    var oaddbiaoxBb=oaddbiaoxK.querySelectorAll('.addbiaoxBb')[0];//新增metainfo框提交按钮
    var oaddbiaoxBb2=oaddbiaoxK.querySelectorAll('.addbiaoxBb2')[0];//新增metainfo框关闭按钮2
    var aaddbiaoxlik=oaddbiaoxK.querySelectorAll('.addbiaoxlik');//新增metainfo框名称框

    var oaddbiaoxPD=0;

    // console.log(oaddbiaoxK);


    for(var i=0;i<atableeditzadd.length;i++){
        (function(index){
            atableeditzadd[index].onclick=function(){
                if(oaddbiaoxPD==0){
                    oaddbiaoxK.style.display="block";
                    oaddbiaoxPD=1;

                    var aaddbiaoxlik=oaddbiaoxK.querySelectorAll('.addbiaoxlik');
                    var aselect=oaddbiaoxK.querySelectorAll('select');

                    
                    for(var j=0;j<aaddbiaoxlik.length;j++){
                        aaddbiaoxlik[j].value="";
                    }
                    for(var j=0;j<aselect.length;j++){
                        aselect[j].options[0].selected = true;
                    }
                }
            }
        })(i)
    }
    
    oaddbiaoxTx.onclick=function(){
            oaddbiaoxK.style.display="none";
            oaddbiaoxPD=0;
    }
    oaddbiaoxBb2.onclick=function(){
            oaddbiaoxK.style.display="none";
            oaddbiaoxPD=0;
    }
    oaddbiaoxBb.onclick=function(){
            // alert("提交成功")
            oaddbiaoxK.style.display="none";
            oaddbiaoxPD=0;
    }


    // 更新metainfo
    var atableeditzedit=[];
    for(var i=0;i<abox_xytabzK.length;i++){
        var otableeditzedit=abox_xytabzK[i].querySelectorAll('.tableeditzedit')[0];
        // console.log(otableeditzedit);
        atableeditzedit.push(otableeditzedit);//新增metainfo按钮
    }
    // console.log(atableeditzedit);


    // var atableeditzedit=obox_xytabzK.querySelectorAll('.tableeditzedit');//更新metainfo按钮
    var oaddbiaoxK2=document.querySelectorAll('.addbiaoxK2')[0];//更新metainfo框
    var oaddbiaoxTx=oaddbiaoxK2.querySelectorAll('.addbiaoxTx')[0];//更新metainfo框关闭按钮
    var oaddbiaoxBb=oaddbiaoxK2.querySelectorAll('.addbiaoxBb')[0];//更新metainfo框提交按钮
    var oaddbiaoxBb2=oaddbiaoxK2.querySelectorAll('.addbiaoxBb2')[0];//更新metainfo框关闭按钮2
    var aaddbiaoxlik=oaddbiaoxK2.querySelectorAll('.addbiaoxlik');//更新metainfo框名称框

    var oaddbiaoxPD2=0;


    for(var i=0;i<atableeditzedit.length;i++){
        (function(index){
            atableeditzedit[index].onclick=function(){
                if(oaddbiaoxPD2==0){
                	//在此处查询出选中的复选框
                	/*
            		var afuxuanK=document.querySelectorAll('.trbx');
                    var afuxuan=[];
                    for(var i=0;i<afuxuanK.length;i++){
                        afuxuan.push(afuxuanK[i].querySelectorAll('.xuanze')[0]);
                    }
                    var ids = [];
                    for(var i=0;i<afuxuanK.length;i++){
                    	if(afuxuan[i].checked){
                    		ids.push(afuxuan[i].id);
                    	}
                    }*/
                	var checkboxs=document.querySelectorAll('.source_field_checkbox');
                    var ids = [];
                    for(var i=0;i<checkboxs.length;i++){
                    	if(checkboxs[i].checked){
                    		ids.push(checkboxs[i].id);
                    	}
                    }
                    if(ids.length == 0){
                    	alert("请勾选数据源字段！");
                    }else if(ids.length > 1){
                    	alert("最多只能编辑一条记录！");
                    }else{
                    	
                    	//先对数据进行填充
                    	$("#edit_csf_id").val(ids[0]);
                    	//字段名
                    	$("#edit_csf_name").val($('#csf_name'+ids[0]).text());
                    	//类型
                    	$("#edit_type").val($('#type'+ids[0]).text());
                    	//检测规则
                    	$("#edit_check_rule").val($('#check_rule'+ids[0]).text());
                    	//是否可枚举
                    	if($('#enumerated'+ids[0]).text() == '是'){
                    		$("#edit_enumerated").val('true');
                    	}else{
                    		$("#edit_enumerated").val('false');
                    	}
                    	//是否必填
                    	if($('#not_null'+ids[0]).text() == '是'){
                    		$("#edit_not_null").val('true');
                    	}else{
                    		$("#edit_not_null").val('false');
                    	}
                    	//字段描述信息
                    	$("#edit_description").val($('#description'+ids[0]).text());
                    	//错误提示信息
                    	$("#edit_error_msg").val($('#error_msg'+ids[0]).text());

                    	oaddbiaoxK2.style.display="block";
                        oaddbiaoxPD2=1;
                        
                    }
                	
                    /*oaddbiaoxK2.style.display="block";
                    oaddbiaoxPD2=1;
                    for(var j=0;j<aaddbiaoxlik.length;j++){
                        aaddbiaoxlik[j].value="";
                    }*/
                }
            }
        })(i)
    }
    
    oaddbiaoxTx.onclick=function(){
            oaddbiaoxK2.style.display="none";
            oaddbiaoxPD2=0;
    }
    oaddbiaoxBb2.onclick=function(){
            oaddbiaoxK2.style.display="none";
            oaddbiaoxPD2=0;
    }
    oaddbiaoxBb.onclick=function(){
            // alert("提交成功")
            oaddbiaoxK2.style.display="none";
            oaddbiaoxPD2=0;
    }


    // 删除metainfo
    var atableeditzdel=[];
    for(var i=0;i<abox_xytabzK.length;i++){
        var otableeditzdel=abox_xytabzK[i].querySelectorAll('.tableeditzdel')[0];
        // console.log(otableeditzdel);
        atableeditzdel.push(otableeditzdel);//新增metainfo按钮
    }


    // var atableeditzdel=obox_xytabzK.querySelectorAll('.tableeditzdel');//删除metainfo按钮
    var odelbiaoxK=document.querySelectorAll('.delbiaoxK')[0];//删除metainfo框
    // console.log(odelbiaoxK);
    var oaddbiaoxTx=odelbiaoxK.querySelectorAll('.addbiaoxTx')[0];//删除metainfo框关闭按钮
    var oaddbiaoxBb2=odelbiaoxK.querySelectorAll('.addbiaoxBb2')[0];//删除metainfo框关闭按钮2
    var oaddbiaoxBb=odelbiaoxK.querySelectorAll('.addbiaoxBb')[0];//删除metainfo框提交按钮

    var oaddbiaoxPD3=0;

    for(var i=0;i<atableeditzdel.length;i++){
        (function(index){
            atableeditzdel[index].onclick=function(){
                if(oaddbiaoxPD3==0){
                	
                	
                	//首先是获取所有选中的基本信息字段
                	var ids = [];
                	var source_field_checkbox=document.querySelectorAll('.source_field_checkbox');
                	for(var i = 0 ;  i < source_field_checkbox.length ; i++){
                		if(source_field_checkbox[i].checked == true){
                			ids.push(source_field_checkbox[i].id);
                		}
                	}
                	if(ids.length < 1){
                		alert("请勾选的待删除行！");
                		return;
                	}else{
                		odelbiaoxK.style.display="block";
                        oaddbiaoxPD3=1;
                	}

                }
            }
        })(i)
    }

    oaddbiaoxTx.onclick=function(){
            odelbiaoxK.style.display="none";
            oaddbiaoxPD3=0;
    }
    oaddbiaoxBb2.onclick=function(){
            odelbiaoxK.style.display="none";
            oaddbiaoxPD3=0;
    }
    oaddbiaoxBb.onclick=function(){
            // alert("提交成功")
            odelbiaoxK.style.display="none";
            oaddbiaoxPD3=0;
    }



  


    //格式化数据类型配置表

    var abox_xytabzK2=document.querySelectorAll('.box_xytabzK2');//格式化数据类型配置表
    // console.log(abox_xytabzK2);


    // 新增格式化数据类型
    var atableeditzadd2=[];
    for(var i=0;i<abox_xytabzK2.length;i++){
        var otableeditzadd=abox_xytabzK2[i].querySelectorAll('.tableeditzadd')[0];
        atableeditzadd2.push(otableeditzadd);
    }
    // console.log(atableeditzadd2);

    
    // var otableeditzadd=obox_xytabzK2.querySelectorAll('.tableeditzadd')[0];//新增格式化数据类型按钮
    var oaddbiaoxK_=document.querySelectorAll('.addbiaoxK_2')[0];//新增格式化数据类型框
    var oaddbiaoxTx=oaddbiaoxK_.querySelectorAll('.addbiaoxTx')[0];//新增格式化数据类型框关闭按钮
    var oaddbiaoxBb=oaddbiaoxK_.querySelectorAll('.addbiaoxBb')[0];//新增格式化数据类型框提交按钮
    var oaddbiaoxBb2=oaddbiaoxK_.querySelectorAll('.addbiaoxBb2')[0];//新增格式化数据类型框关闭按钮2
    var aaddbiaoxlik=oaddbiaoxK_.querySelectorAll('.addbiaoxlik');//新增格式化数据类型框名称框

    var oaddbiaoxPD_=0;


    for(var i=0;i<atableeditzadd2.length;i++){
        (function(index){
            atableeditzadd2[index].onclick=function(){
                if(oaddbiaoxPD_==0){
                    oaddbiaoxK_.style.display="block";
                    oaddbiaoxPD_=1;

                    var aaddbiaoxlik=oaddbiaoxK_.querySelectorAll('.addbiaoxlik');
                    var aselect=oaddbiaoxK_.querySelectorAll('select');

                    
                    for(var j=0;j<aaddbiaoxlik.length;j++){
                        aaddbiaoxlik[j].value="";
                    }
                    for(var j=0;j<aselect.length;j++){
                        aselect[j].options[0].selected = true;
                    }
                }
            }
        })(i)
    }

    
    oaddbiaoxTx.onclick=function(){
            oaddbiaoxK_.style.display="none";
            oaddbiaoxPD_=0;
    }
    oaddbiaoxBb2.onclick=function(){
            oaddbiaoxK_.style.display="none";
            oaddbiaoxPD_=0;
    }
    oaddbiaoxBb.onclick=function(){
            // alert("提交成功")
            oaddbiaoxK_.style.display="none";
            oaddbiaoxPD_=0;
    }


    // 更新格式化数据类型
    var atableeditzedit2=[];
    for(var i=0;i<abox_xytabzK2.length;i++){
        var otableeditzedit=abox_xytabzK2[i].querySelectorAll('.tableeditzedit')[0];
        // console.log(otableeditzadd)
        atableeditzedit2.push(otableeditzedit);//新增metainfo按钮
    }
    // console.log(atableeditzadd2);

    // var otableeditzedit=obox_xytabzK2.querySelectorAll('.tableeditzedit')[0];//更新格式化数据类型按钮
    var oaddbiaoxK_2=document.querySelectorAll('.addbiaoxK2_2')[0];//更新格式化数据类型框
    // console.log(oaddbiaoxK2);
    var oaddbiaoxTx=oaddbiaoxK_2.querySelectorAll('.addbiaoxTx')[0];//更新格式化数据类型框关闭按钮
    var oaddbiaoxBb=oaddbiaoxK_2.querySelectorAll('.addbiaoxBb')[0];//更新格式化数据类型框提交按钮
    var oaddbiaoxBb2=oaddbiaoxK_2.querySelectorAll('.addbiaoxBb2')[0];//更新格式化数据类型框关闭按钮2
    var aaddbiaoxlik=oaddbiaoxK_2.querySelectorAll('.addbiaoxlik');//更新格式化数据类型框名称框

    var oaddbiaoxPD_2=0;


    for(var i=0;i<atableeditzedit2.length;i++){
        (function(index){
            atableeditzedit2[index].onclick=function(){
                if(oaddbiaoxPD_2==0){
                	
                	var checkboxs=document.querySelectorAll('.format_type_checkbox');
                    var ids = [];
                    for(var i=0;i<checkboxs.length;i++){
                    	if(checkboxs[i].checked){
                    		ids.push(checkboxs[i].id);
                    	}
                    }
                    if(ids.length == 0){
                    	alert("请勾选数据源字段！");
                    }else if(ids.length > 1){
                    	alert("最多只能编辑一条记录！");
                    }else{
                    	
                    	//先对数据进行填充
                    	$("#edit_ft_id").val(ids[0]);
                    	//
                    	$("#edit_ft_name").val($('#ft_name'+ids[0]).text());
                    	
                    	if($('#is_view'+ids[0]).text() == '显示'){
                    		$("#edit_is_view").val("true");
                    	}else{
                    		$("#edit_is_view").val("false");
                    	}
                    	
                    	
                    	$("#edit_floder").val($('#floder'+ids[0]).text());
                    	
                    	oaddbiaoxK_2.style.display="block";
                        oaddbiaoxPD_2=1;
                        
                    }
                	
 
                }
            }
        })(i)
    }

    
    oaddbiaoxTx.onclick=function(){
            oaddbiaoxK_2.style.display="none";
            oaddbiaoxPD_2=0;
    }
    oaddbiaoxBb2.onclick=function(){
            oaddbiaoxK_2.style.display="none";
            oaddbiaoxPD_2=0;
    }
    oaddbiaoxBb.onclick=function(){
            // alert("提交成功")
            oaddbiaoxK_2.style.display="none";
            oaddbiaoxPD_2=0;
    }


    // 删除格式化数据类型
    var atableeditzdel2=[];
    for(var i=0;i<abox_xytabzK2.length;i++){
        var otableeditzdel=abox_xytabzK2[i].querySelectorAll('.tableeditzdel')[0];
        // console.log(otableeditzadd)
        atableeditzdel2.push(otableeditzdel);//新增metainfo按钮
    }
    // console.log(atableeditzdel2);
    
    
    // var otableeditzdel=obox_xytabzK2.querySelectorAll('.tableeditzdel')[0];//删除格式化数据类型按钮
    var odelbiaoxK_=document.querySelectorAll('.delbiaoxK_2')[0];//删除格式化数据类型框
    var oaddbiaoxTx=odelbiaoxK_.querySelectorAll('.addbiaoxTx')[0];//删除格式化数据类型框关闭按钮
    var oaddbiaoxBb2=odelbiaoxK_.querySelectorAll('.addbiaoxBb2')[0];//删除格式化数据类型框关闭按钮2
    var oaddbiaoxBb=odelbiaoxK_.querySelectorAll('.addbiaoxBb')[0];//删除格式化数据类型框提交按钮

    var oaddbiaoxPD_3=0;

    for(var i=0;i<atableeditzdel2.length;i++){
        (function(index){
            atableeditzdel2[index].onclick=function(){
                if(oaddbiaoxPD_3==0){
                	
                	
                	//首先是获取所有选中的基本信息字段
                	var ids = [];
                	var source_field_checkbox=document.querySelectorAll('.format_type_checkbox');
                	for(var i = 0 ;  i < source_field_checkbox.length ; i++){
                		if(source_field_checkbox[i].checked == true){
                			ids.push(source_field_checkbox[i].id);
                		}
                	}
                	if(ids.length < 1){
                		alert("请勾选的待删除行！");
                		return;
                	}else{
                		odelbiaoxK_.style.display="block";
                        oaddbiaoxPD_3=1;
                	}
                	
                }
            }
        })(i)
    }


    oaddbiaoxTx.onclick=function(){
            odelbiaoxK_.style.display="none";
            oaddbiaoxPD_3=0;
    }
    oaddbiaoxBb2.onclick=function(){
            odelbiaoxK_.style.display="none";
            oaddbiaoxPD_3=0;
    }
    oaddbiaoxBb.onclick=function(){
            // alert("提交成功")
            odelbiaoxK_.style.display="none";
            oaddbiaoxPD_3=0;
    }


    // // 格式化数据类型配置表全选效果
    // var odatatypeconfig=document.querySelectorAll('.datatypeconfig')[0];//格式化数据类型配置表
    // var obox_xytabz=odatatypeconfig.querySelectorAll('.box_xytabz')[0];//表格
    
    /*var abox_xytabzK2=document.querySelectorAll('.box_xytabzK2');//三个数据采集表
    console.log(abox_xytabzK2);



    for(var a=0;a<abox_xytabzK2.length;a++){
        (function(i){
            var oquanxuan=abox_xytabzK2[i].querySelectorAll('.quanxuan')[0];//每个表的全选
            // console.log(oquanxuan);
            var axuanze=abox_xytabzK2[i].querySelectorAll('.xuanze');//每个表的选择

            var afuxuan=[];
            for(var i=0;i<axuanze.length;i++){
                afuxuan.push(axuanze[i]);
            }

            oquanxuan.onchange=function(){
                if(oquanxuan.checked){
                    for(var i=0;i<axuanze.length;i++){
                        afuxuan[i].checked=1;
                    }
                }else{
                    // console.log(2);
                    for(var i=0;i<axuanze.length;i++){
                        afuxuan[i].checked=0;
                    }
                }
            }

            for(var i=0;i<axuanze.length;i++){
                (function(index){
                    axuanze[i].onchange=function(){
                        var fuxuanPD=0;
                        for(var j=0;j<axuanze.length;j++){
                            if(afuxuan[j].checked){
                                fuxuanPD++;
                            }
                            console.log(afuxuan[j].checked);
                        }
                        console.log(fuxuanPD);
                        if(fuxuanPD==axuanze.length){
                            oquanxuan.checked=1;
                        }else if(fuxuanPD!=axuanze.length){
                            oquanxuan.checked=0;
                        }
                    }
                })(i)
            }
        })(a)

    }*/
    //初始化的时候，没有数据，无法响应
    /*
    var abiaoxiangthin=document.querySelectorAll('.biaoxiangthin');//进入按钮
    var oendconfigK=document.querySelectorAll('.endconfigK')[0];//配置结果类型框
    var oendconfigTx=oendconfigK.querySelectorAll('.endconfigTx')[0];//配置结果类型框关闭按钮
    for(var i=0;i<abiaoxiangthin.length;i++){
        (function(index){
            abiaoxiangthin[index].onclick=function(){
            	
                oendconfigK.style.display="block";
            }
        })(i)
    }
    oendconfigTx.onclick=function(){
        oendconfigK.style.display="none";
    }*/


    //metainfo表
  /*
    var oendconfigz1=document.querySelectorAll('.endconfigz1')[0];
    var oencozadd=oendconfigz1.querySelectorAll('.encozadd')[0];//新增metainfo按钮
    var oenco1aK=oendconfigz1.querySelectorAll('.enco1aK')[0];//新增metainfo框
    var oendconfigTx=oenco1aK.querySelectorAll('.endconfigTx')[0];//新增metainfo框关闭按钮
    var oencob=oenco1aK.querySelectorAll('.encob')[0];//新增metainfo框提交按钮

    oencozadd.onclick=function(){
        oenco1aK.style.display="block";
    }
    oendconfigTx.onclick=function(){
        oenco1aK.style.display="none";
    }


    var oencozedit=oendconfigz1.querySelectorAll('.encozedit')[0];//更新metainfo按钮
    var oenco1eK=oendconfigz1.querySelectorAll('.enco1eK')[0];//更新metainfo框
    var oendconfigTx=oenco1eK.querySelectorAll('.endconfigTx')[0];//更新metainfo框关闭按钮
    var oencob=oenco1eK.querySelectorAll('.encob')[0];//更新metainfo框提交按钮

    oencozedit.onclick=function(){
        oenco1eK.style.display="block";
    }
    oendconfigTx.onclick=function(){
        oenco1eK.style.display="none";
    }

    var oencozdel=oendconfigz1.querySelectorAll('.encozdel')[0];//删除metainfo按钮
    var oenco1dK=oendconfigz1.querySelectorAll('.enco1dK')[0];//删除metainfo框
    var oendconfigTx=oenco1dK.querySelectorAll('.endconfigTx')[0];//删除metainfo框关闭按钮
    var oencob=oenco1dK.querySelectorAll('.encob')[0];//删除metainfo框提交按钮
    var oencob2=oenco1dK.querySelectorAll('.encob2')[0];//删除metainfo框关闭按钮2

    oencozdel.onclick=function(){
        oenco1dK.style.display="block";
    }
    oendconfigTx.onclick=function(){
        oenco1dK.style.display="none";
    }
    oencob2.onclick=function(){
        oenco1dK.style.display="none";
    }

    //metainfo表全选
    
    var oquanxuan3=oendconfigz1.querySelectorAll('.quanxuan')[0];//全选
    var axuanze3=oendconfigz1.querySelectorAll('.xuanze');//复选
    console.log(oquanxuan3);
    console.log(axuanze3);

    oquanxuan3.onchange=function(){
        if(oquanxuan3.checked){
            for(var i=0;i<axuanze3.length;i++){
                axuanze3[i].checked=1;
            }
        }else{
            // console.log(2);
            for(var i=0;i<axuanze3.length;i++){
                axuanze3[i].checked=0;
            }
        }
    }

    for(var i=0;i<axuanze3.length;i++){
        (function(index){
            axuanze3[i].onchange=function(){
                var fuxuanPD=0;
                for(var j=0;j<axuanze3.length;j++){
                    if(axuanze3[j].checked){
                        fuxuanPD++;
                    }
                    console.log(axuanze3[j].checked);
                }
                console.log(fuxuanPD);
                if(fuxuanPD==axuanze3.length){
                    oquanxuan3.checked=1;
                }else if(fuxuanPD!=axuanze3.length){
                    oquanxuan3.checked=0;
                }
            }
        })(i)
    }




*/


//data表
    
    var oendconfigz2=document.querySelectorAll('.endconfigz2')[0];
    var oencozadd=oendconfigz2.querySelectorAll('.encozadd')[0];//新增metainfo按钮
    var oenco2aK=oendconfigz2.querySelectorAll('.enco2aK')[0];//新增metainfo框
    var oendconfigTx=oenco2aK.querySelectorAll('.endconfigTx')[0];//新增metainfo框关闭按钮
    var oencob=oenco2aK.querySelectorAll('.encob')[0];//新增metainfo框提交按钮

    oencozadd.onclick=function(){
        oenco2aK.style.display="block";
    }
    oendconfigTx.onclick=function(){
        oenco2aK.style.display="none";
    }


    var oencozedit=oendconfigz2.querySelectorAll('.encozedit')[0];//更新metainfo按钮
    var oenco2eK=oendconfigz2.querySelectorAll('.enco2eK')[0];//更新metainfo框
    var oendconfigTx=oenco2eK.querySelectorAll('.endconfigTx')[0];//更新metainfo框关闭按钮
    var oencob=oenco2eK.querySelectorAll('.encob')[0];//更新metainfo框提交按钮

    oencozedit.onclick=function(){
    	
    	var checkboxs=document.querySelectorAll('.format_field_checkbox');
        var ids = [];
        for(var i=0;i<checkboxs.length;i++){
        	if(checkboxs[i].checked){
        		ids.push(checkboxs[i].id);
        	}
        }
        if(ids.length == 0){
        	alert("请勾选记录！");
        }else if(ids.length > 1){
        	alert("最多只能编辑一条记录！");
        }else{
        	
        	//先对数据进行填充
        	$("#edit_ff_ff_id").val(ids[0]);
        	
        	if($("#ff_is_meta"+ids[0]).text() == '是'){
        		$("#edit_ff_is_meta").val('true');
        	}else{
        		$("#edit_ff_is_meta").val('false');
        	}
        	$("#edit_ff_ff_name").val($('#ff_ff_name'+ids[0]).text());
        	$("#edit_ff_type").val($("#ff_type"+ids[0]).text());
        	$("#edit_ff_check_rule").val($("#ff_check_rule"+ids[0]).text());
        	
        	if($("#ff_enumerated"+ids[0]).text() == '是'){
        		$("#edit_ff_enumerated").val('true');
        	}else{
        		$("#edit_ff_enumerated").val('false');
        	}
        	
        	if($("#ff_not_null"+ids[0]).text() == '是'){
        		$("#edit_ff_not_null").val('true');
        	}else{
        		$("#edit_ff_not_null").val('false');
        	}
        	
        	if($("#ff_is_view"+ids[0]).text() == '是'){
        		$("#edit_ff_is_view").val('true');
        	}else{
        		$("#edit_ff_is_view").val('false');
        	}
        	
        	$("#edit_ff_description").val($("#ff_description"+ids[0]).text());
        	$("#edit_ff_error_msg").val($("#ff_error_msg"+ids[0]).text());  	

        	oenco2eK.style.display="block";
        	
        }
    	
    	
    	
        
    }
    oendconfigTx.onclick=function(){
        oenco2eK.style.display="none";
    }

    var oencozdel=oendconfigz2.querySelectorAll('.encozdel')[0];//删除metainfo按钮
    var oenco2dK=oendconfigz2.querySelectorAll('.enco2dK')[0];//删除metainfo框
    var oendconfigTx=oenco2dK.querySelectorAll('.endconfigTx')[0];//删除metainfo框关闭按钮
    var oencob=oenco2dK.querySelectorAll('.encob')[0];//删除metainfo框提交按钮
    var oencob2=oenco2dK.querySelectorAll('.encob2')[0];//删除metainfo框关闭按钮2

    oencozdel.onclick=function(){
        
    }
    oendconfigTx.onclick=function(){
        oenco2dK.style.display="none";
    }
    oencob2.onclick=function(){
        oenco2dK.style.display="none";
    }
      
//data表全选
    
    var oquanxuan4=oendconfigz2.querySelectorAll('.quanxuan')[0];//全选
    var axuanz4=oendconfigz2.querySelectorAll('.xuanze');//复选
    console.log(oquanxuan4);
    console.log(axuanz4);

    if(oquanxuan4){
	    oquanxuan4.onchange=function(){
	        if(oquanxuan4.checked){
	            for(var i=0;i<axuanz4.length;i++){
	                axuanz4[i].checked=1;
	            }
	        }else{
	            // console.log(2);
	            for(var i=0;i<axuanz4.length;i++){
	                axuanz4[i].checked=0;
	            }
	        }
	    }
    }

    for(var i=0;i<axuanz4.length;i++){
        (function(index){
            axuanz4[i].onchange=function(){
                var fuxuanPD=0;
                for(var j=0;j<axuanz4.length;j++){
                    if(axuanz4[j].checked){
                        fuxuanPD++;
                    }
                    console.log(axuanz4[j].checked);
                }
                console.log(fuxuanPD);
                if(fuxuanPD==axuanz4.length){
                    oquanxuan4.checked=1;
                }else if(fuxuanPD!=axuanz4.length){
                    oquanxuan4.checked=0;
                }
            }
        })(i)
    }


});