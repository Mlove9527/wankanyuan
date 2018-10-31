/* 
* @Author: Marte
* @Date:   2018-05-22 09:15:35
* @Last Modified by:   Marte
* @Last Modified time: 2018-05-24 09:29:57
*/

$(document).ready(function(){
//全选效果
    var abox_xytabzK=document.querySelectorAll('.box_xytabzK');//表
    console.log(abox_xytabzK);

    for(var a=0;a<abox_xytabzK.length;a++){
        (function(i){
            var oquanxuan=abox_xytabzK[i].querySelectorAll('.quanxuan')[0];//每个表的全选
            // console.log(oquanxuan);
            var axuanze=abox_xytabzK[i].querySelectorAll('.xuanze');//每个表的选择

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

    }

    var obox_content=document.querySelectorAll('.box-content')[0];//居中所用父元素
    console.log(obox_content);

    var otableeditzadd=document.querySelectorAll('.tableeditzadd')[0];//添加按钮
    var ouser_addboxK=document.querySelectorAll('.user_addboxK')[0];//添加框
    var oaddboxTx=ouser_addboxK.querySelectorAll('.addbiaoxTx')[0];//添加框关闭按钮
    
    var otableeditzedit=document.querySelectorAll('.tableeditzedit')[0];//修改按钮
    var ouser_editboxK=document.querySelectorAll('.user_editboxK')[0];//修改框
    var oeditboxTx=ouser_editboxK.querySelectorAll('.addbiaoxTx')[0];//修改框关闭按钮

    var otableeditzdis=document.querySelectorAll('.tableeditzdis')[0];//删除按钮
    var ouser_disboxK=document.querySelectorAll('.user_disboxK')[0];//删除框
    var odisboxTx=ouser_disboxK.querySelectorAll('.addbiaoxTx')[0];//删除框关闭按钮
    var odisboxTx2=ouser_disboxK.querySelectorAll('.addbiaoxBb2')[0];//删除框关闭按钮2

    // var otableeditzen=document.querySelectorAll('.tableeditzen')[0];//启用按钮
    // var ouser_enboxK=document.querySelectorAll('.user_enboxK')[0];//启用框
    // var oenboxTx=ouser_enboxK.querySelectorAll('.addbiaoxTx')[0];//启用框关闭按钮
    // var oenboxTx2=ouser_enboxK.querySelectorAll('.addbiaoxBb2')[0];//启用框关闭按钮2

    // var otableeditzpsre=document.querySelectorAll('.tableeditzpsre')[0];//重置按钮
    // var ouser_psreboxK=document.querySelectorAll('.user_psreboxK')[0];//重置框
    // var opsreboxTx=ouser_psreboxK.querySelectorAll('.addbiaoxTx')[0];//重置框关闭按钮
    // var opsreboxTx2=ouser_psreboxK.querySelectorAll('.addbiaoxBb2')[0];//重置框关闭按钮2
    

//增加
    otableeditzadd.onclick=function(){
        ouser_addboxK.style.display="block";
        ouser_addboxK.style.left=obox_content.offsetWidth/2-ouser_addboxK.offsetWidth/2+"px";//表居中
    }
    oaddboxTx.onclick=function(){
        ouser_addboxK.style.display="none";
    }


//编辑
    otableeditzedit.onclick=function(){
        // ouser_editboxK.style.display="block";
        ouser_editboxK.style.left=obox_content.offsetWidth/2-ouser_editboxK.offsetWidth/2+"px";//表居中
    }
    oeditboxTx.onclick=function(){
        ouser_editboxK.style.display="none";
    }


//删除
    otableeditzdis.onclick=function(){
        // ouser_disboxK.style.display="block";
        ouser_disboxK.style.left=obox_content.offsetWidth/2-ouser_disboxK.offsetWidth/2+"px";//表居中
    }
    odisboxTx.onclick=function(){
        ouser_disboxK.style.display="none";
    }
    odisboxTx2.onclick=function(){
        ouser_disboxK.style.display="none";
    }


//启用
    // otableeditzen.onclick=function(){
    //     // ouser_enboxK.style.display="block";
    //     ouser_enboxK.style.left=obox_content.offsetWidth/2-ouser_enboxK.offsetWidth/2+"px";//表居中
    // }
    // oenboxTx.onclick=function(){
    //     ouser_enboxK.style.display="none";
    // }
    // oenboxTx2.onclick=function(){
    //     ouser_enboxK.style.display="none";
    // }

    // otableeditzpsre.onclick=function(){
    //     ouser_psreboxK.style.display="block";
    //     ouser_psreboxK.style.left=obox_content.offsetWidth/2-ouser_psreboxK.offsetWidth/2+"px";//表居中
    // }
    // opsreboxTx.onclick=function(){
    //     ouser_psreboxK.style.display="none";
    // }
    // opsreboxTx2.onclick=function(){
    //     ouser_psreboxK.style.display="none";
    // }


});