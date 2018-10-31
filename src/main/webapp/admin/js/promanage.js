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

    
});