/* 
* @Author: Marte
* @Date:   2018-04-23 15:32:03
* @Last Modified by:   Marte
* @Last Modified time: 2018-05-16 10:36:25
*/

//项目应用结果重新运行——单独页面
function pro_appendre2(){
    
    var oproreK=document.querySelectorAll('.proreK2')[0];//格式数据框
    var oproreb1=oproreK.querySelectorAll('.proreb1')[0];//格式数据框提交按钮

// 重新选择格式数据框格式数据显示
    var oproreM=document.querySelectorAll('.proreM')[0];
    var aproreMzc=oproreM.querySelectorAll('.proreMzc');
    var aPJliB=oproreM.querySelectorAll('.PJliB');

    for(var i=0;i<aproreMzc.length;i++){
        (function(index){
            aproreMzc[index].onclick=function(){
                console.log(index)
                for(var j=0;j<aPJliB.length;j++){
                    aPJliB[j].className="PJliB";
                }
                aPJliB[index].className="PJliB active";
            }
        })(i)
    }


//选择格式数据
    var aproreMli=oproreM.querySelectorAll('.proreMli');
    var proreMliPD=[];
    // console.log(aproreMli);

    for(var i=0;i<aproreMli.length;i++){
        proreMliPD[i]=0;
        // console.log(proreMliPD);
        (function(index){
            aproreMli[index].onclick=function(){
                if(proreMliPD[index]==0){
                    // aproreMli.className="proreMli active";
                    aproreMli[index].style.color="#5ca0e5";
                    proreMliPD[index]=1;
                }else{
                    // aproreMli.className="proreMli";
                    aproreMli[index].style.color="#666";
                    proreMliPD[index]=0;
                }
            }
        })(i)
    }


}



//  项目0
function project0(){
	var ologo=document.querySelectorAll('.logo')[0];
    ologo.onclick=function(){
        window.location.href = "/wankangyuan";
    }
	
    var obox=document.querySelectorAll('.box')[0];
    obox.style.minHeight=window.screen.availHeight-200+'px';
    console.log(document.body.clientWidth);
    
    if(document.querySelectorAll('.PJList')[0]){
    	var oPJList=document.querySelectorAll('.PJList')[0];
    }else if(document.querySelectorAll('.shaixuanC')[0]){
    	var oPJList=document.querySelectorAll('.shaixuanC')[0];
    }
    
    
    //console.log(oPJList);
    
    if(document.querySelectorAll('.PJul')[0]){
    	var oPJul=document.querySelectorAll('.PJul')[0];
    }else if(document.querySelectorAll('.PJK2')[0]){
    	var oPJul=document.querySelectorAll('.PJK2')[0];
    }else if(document.querySelectorAll('.prof_lbRmULm')[0]){
    	var oPJul=document.querySelectorAll('.prof_lbRmULm')[0];
    }
    
    //console.log(oPJul);

// 全选框和复选框的动作
    if(document.querySelectorAll('.quanxuanK')[0]){
    	if(oPJList){
    		oquanxuanK=oPJList.querySelectorAll('.quanxuanK')[0];
    	}else if(document.querySelectorAll(".allK")){
    		oallK=document.querySelectorAll('.allK')[0];
    		oquanxuanK=oallK.querySelectorAll('.quanxuanK')[0];
    	}
    	
        
        var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];

        var afuxuan=[];
        var afuxuanK=[];

        if(document.querySelectorAll('.fuxuanK2')[0]){
            afuxuanK=oPJul.querySelectorAll('.fuxuanK2');
            //console.log("k2");
        }else if(document.querySelectorAll('.fuxuanK3')[0]){
            afuxuanK=document.querySelectorAll('.fuxuanK3');
            //console.log("k3");
        }

        if(afuxuanK[0]){
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
            }
        }
        

        oquanxuan.onchange=function(){
            if(oquanxuan.checked){
                for(var i=0;i<afuxuanK.length;i++){
                    afuxuan[i].checked=1;
                }
            }else{
                console.log(2);
                for(var i=0;i<afuxuanK.length;i++){
                    afuxuan[i].checked=0;
                }
            }
        }


        if(afuxuanK[0]){
            for(var i=0;i<afuxuanK.length;i++){
                (function(index){
                    afuxuan[i].onchange=function(){
                        var fuxuanPD=0;
                        for(var j=0;j<afuxuanK.length;j++){
                            if(afuxuan[j].checked){
                                fuxuanPD++;
                            }
                            console.log(afuxuan[j].checked);
                        }
                        console.log(fuxuanPD);
                        if(fuxuanPD==afuxuanK.length){
                            oquanxuan.checked=1;
                        }else if(fuxuanPD!=afuxuanK.length){
                            oquanxuan.checked=0;
                        }
                    }
                })(i)
            }
        }
        
    }else{
        console.log(222);
    }
}

//  项目1
function project1(){

// 筛选菜单框显示隐藏
    var oshaixuanBT=document.querySelectorAll('.shaixuanBT')[0];//获取筛选下拉按钮
    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
    var shaixuanPD=0;

    oshaixuanBT.onclick=function(event){
//        if(shaixuanPD==0){
            oshaixuanZK.className="shaixuanZK active";
//            shaixuanPD=1;
//        }else{
//            oshaixuanZK.className="shaixuanZK";
//            shaixuanPD=0;
//        }
         event.stopPropagation();
         oBTSX.style.display="none";
        // console.log(1);
    }
    // document.onclick=function(){
    //     oshaixuanZK.className="shaixuanZK";
    // }
    $(window).click(function(){
    	oshaixuanZK.className="shaixuanZK";
    })
    $(oshaixuanZK).click(function(){
    	event.stopPropagation();
    })

//筛选按钮显示隐藏选项
    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
    var ashaixuanZKliI=document.querySelectorAll('.shaixuanZKliI');//获取所有筛选按钮
    var oPJList=document.querySelectorAll('.PJList')[0];//项目表头栏
    var aPJListli=oPJList.querySelectorAll('.PJListli');//项目表头
    var oPJul=document.querySelectorAll('.PJul')[0];//项目栏
    var aPJli=oPJul.querySelectorAll('.PJli');//每一条项目

    console.log(aPJli);
    

    //初始化筛选按钮判断
    var shaixuanBTPD=new Array();
    for(var i=0;i<ashaixuanZKliI.length;i++){
        shaixuanBTPD[i]=1;
    }



    for(var i=0;i<ashaixuanZKliI.length;i++){
        (function(j){
            ashaixuanZKliI[j].onclick=function(){
                if(shaixuanBTPD[j]==0){
                    ashaixuanZKliI[j].className="shaixuanZKliI active";
                    aPJListli[j].style.display="block";
                    var otd=aPJListli[j].parentNode;
//                  console.log(otd.tagName);
                    if(otd.tagName=="TD"){
                    	otd.style.display="";
                    }
                    for(var o=0;o<aPJli.length;o++){
                        if(aPJli[o].querySelectorAll('.PJliCli2')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli2');//格式数据表项
                            aPJliCli[j].style.display="-webkit-box";
                            var otd=aPJliCli[j].parentNode;
//                            console.log(otd.tagName);
                            if(otd.tagName=="TD"){
                            	otd.style.display="";
                            }
                        }else if(aPJli[o].querySelectorAll('.PJliCli')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli');//项目表项
                            aPJliCli[j].style.display="-webkit-box";
                            var otd=aPJliCli[j].parentNode;
//                          console.log(otd.tagName);
                            if(otd.tagName=="TD"){
                            	otd.style.display="";
                            }
                        }
                    }
                    shaixuanBTPD[j]=1;
                }else{
                    ashaixuanZKliI[j].className="shaixuanZKliI";
                    aPJListli[j].style.display="none";
                    var otd=aPJListli[j].parentNode;
//                  console.log(otd.tagName);
                    if(otd.tagName=="TD"){
                    	otd.style.display="none";
                    }
                    for(var o=0;o<aPJli.length;o++){
                        if(aPJli[o].querySelectorAll('.PJliCli2')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli2');//格式数据表项
                            aPJliCli[j].style.display="none";
                            var otd=aPJliCli[j].parentNode;
//                          console.log(otd.tagName);
                            if(otd.tagName=="TD"){
                            	otd.style.display="none";
                            }
                        }else if(aPJli[o].querySelectorAll('.PJliCli')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli');//项目表项
                            aPJliCli[j].style.display="none";
                            var otd=aPJliCli[j].parentNode;
//                          console.log(otd.tagName);
                            if(otd.tagName=="TD"){
                            	otd.style.display="none";
                            }
                        }
                        
                    }
                    // aPJliCli[j].style.display="none";
                    shaixuanBTPD[j]=0;
                }
                
            }
        })(i)
    }

//项目表头和项目分割线宽度
    var oPJList=document.querySelectorAll('.PJList')[0];//项目表头
    var oPJListline=document.querySelectorAll('.PJListline')[0];//分割线

//    oPJListline.style.width=oPJList.offsetWidth*0.98+"px";
    // console.log(oPJListline.offsetWidth);

//点击表头的排序筛选功能
    var oPJK=document.querySelectorAll('.PJK')[0];//项目框
    var oBTSX=document.querySelectorAll('.BTSX')[0];//项目表头筛选框
    var oPJList=document.querySelectorAll('.PJList')[0];//项目表头栏
    var aPJListli=oPJList.querySelectorAll('.PJListli');//项目表头

    var BTSXpd=-1;//项目表头筛选框判断

    //点击设置排序筛选框
    for(var i=0;i<aPJListli.length;i++){
        (function(j){
            aPJListli[j].onclick=function(){
//                if(BTSXpd==j){
//                    oBTSX.style.display="none";
//                    BTSXpd=-1;
//                }else{
                    oBTSX.style.display="block";
//                    BTSXpd=j;
//                }
                    
                var BTSXleft=aPJListli[j].offsetLeft;
                oBTSX.name=aPJListli[j].innerHTML;
                console.log(oBTSX.name);
                console.log(BTSXleft);
                if(BTSXleft>1118){
                    BTSXleft=1118;
                }
                oBTSX.style.left=BTSXleft-20+'px'; 
                event.stopPropagation();
                oshaixuanZK.className="shaixuanZK";
                
                var oBTSXcliGLK=document.querySelectorAll('.BTSXcliGLK')[0];
                console.log(oBTSXcliGLK);
                if(oBTSXcliGLK){
                	oBTSXcliGLK.value="";
                }
            }
        })(i)
    }
    $(window).click(function(){
    	oBTSX.style.display="none";
    })
    $(oBTSX).click(function(){
    	event.stopPropagation();
    })
}

//项目切换
function project2(){
	// 筛选菜单框显示隐藏
    var oshaixuanBT=document.querySelectorAll('.shaixuanBT')[0];//获取筛选下拉按钮
    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
    oshaixuanZK.style.top="216px";
    var shaixuanPD=0;

    oshaixuanBT.onclick=function(event){
        oshaixuanZK.className="shaixuanZK active";
        event.stopPropagation();
    }
    oshaixuanZK.onclick=function(event){
        event.stopPropagation();
    }
    
    window.onclick=function(){
        oshaixuanZK.className="shaixuanZK";
    }

//筛选按钮显示隐藏选项
    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
    var ashaixuanZKliI=document.querySelectorAll('.shaixuanZKliI');//获取所有筛选按钮
    var oPJul=document.querySelectorAll('.PJK2')[0];//项目栏
    var aPJli=oPJul.querySelectorAll('.PJK2li');//每一条项目

    //console.log(aPJli);
    

    //初始化筛选按钮判断
    var shaixuanBTPD=new Array();
    for(var i=0;i<ashaixuanZKliI.length;i++){
        shaixuanBTPD[i]=1;
    }



    for(var i=0;i<ashaixuanZKliI.length;i++){
        (function(j){
            ashaixuanZKliI[j].onclick=function(){
                if(shaixuanBTPD[j]==0){
                    ashaixuanZKliI[j].className="shaixuanZKliI active";
                    for(var o=0;o<aPJli.length;o++){
                        if(aPJli[o].querySelectorAll('.PJliCli2')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli2');//格式数据表项
                            aPJliCli[j].style.display="-webkit-box";
                        }else if(aPJli[o].querySelectorAll('.PJliCli_1')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli_1');//项目表项
                            aPJliCli[j].style.display="block";
                        }
                    }
                    shaixuanBTPD[j]=1;
                }else{
                    ashaixuanZKliI[j].className="shaixuanZKliI";
                    for(var o=0;o<aPJli.length;o++){
                        if(aPJli[o].querySelectorAll('.PJliCli2')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli2');//格式数据表项
                            aPJliCli[j].style.display="none";
                        }else if(aPJli[o].querySelectorAll('.PJliCli_1')[0]){
                            var aPJliCli=aPJli[o].querySelectorAll('.PJliCli_1');//项目表项
                            aPJliCli[j].style.display="none";
                        }
                        
                    }
                    // aPJliCli[j].style.display="none";
                    shaixuanBTPD[j]=0;
                }
                
            }
        })(i)
    }
}

//  我的项目
function pro_mine(){
    
}

//  创建项目
function pro_create(){
//创建项目
    var opro_create=document.querySelectorAll('.pro_create')[0];//创建项目按钮
    var ocreatePJK=document.querySelectorAll('.createPJK')[0];//创建项目框
    var ocre_PJKtopI=document.querySelectorAll('.cre_PJKtopI')[0];//项目框关闭按钮
    var owidth=document.body.clientWidth;//屏幕宽度
    
    var createPJKpd=0;

    ocre_PJKtopI.onclick=function(){
        if(createPJKpd==1){
            ocreatePJK.style.display="none";
            createPJKpd=0;
        }
    }
    opro_create.onclick=function(){
        if(createPJKpd==0){
            ocreatePJK.style.display="block";
            // alert(ocreatePJK.offsetWidth);
            console.log(owidth/2);
            console.log(ocreatePJK.offsetWidth/2);
            ocreatePJK.style.left=owidth/2-ocreatePJK.offsetWidth/2+"px";//创建框居中
            createPJKpd=1;
        }
    }


}

//创建页面1
function pro_create1(){

//编辑框显示隐藏
    var oPJeditK=document.querySelectorAll('.PJeditK')[0];//编辑框
    var oPJeditX=document.querySelectorAll('.PJeditX')[0];//编辑框关闭按钮
    var aPJedit=document.querySelectorAll('.PJedit');//编辑按钮

    var PJeditpd=0;

    for(var i=0;i<aPJedit.length;i++){
        (function(index){
            aPJedit[index].onclick=function(){
                if(PJeditpd==0){
                    oPJeditK.style.display="block";
                    PJeditpd=1;
                }else{

                }
            }
        })(i)
    }
    oPJeditX.onclick=function(){
        oPJeditK.style.display="none";
        PJeditpd=0;
    }
}

//  公开项目
function pro_public(){

}

// 项目详情
function pro_detail(){
//项目详情右侧banner
    var oprodexqRbK=document.querySelectorAll('.prodexqRbK')[0];//banner图-容器
    var aprodexqRb=oprodexqRbK.querySelectorAll('.prodexqRb');//banner图

    var oprodexqRaK=document.querySelectorAll('.prodexqRaK')[0];//banner按钮-容器
    var aprodexqRa=oprodexqRaK.querySelectorAll('.prodexqRa');//banner按钮

    for(var i=0;i<aprodexqRa.length;i++){
        (function(j){
            aprodexqRa[j].onclick=function(){
                for(var k=0;k<aprodexqRa.length;k++){
                    aprodexqRa[k].className="prodexqRa";
                    aprodexqRb[k].className="prodexqRb";
                }
                aprodexqRa[j].className="prodexqRa active";
                aprodexqRb[j].className="prodexqRb active";
            }
        })(i)
    }




}


// 项目应用结果
function pro_deappendxq(){
// 左侧显示和隐藏
    var osanjiao_left=document.querySelectorAll('.sanjiao_left')[0];//左箭头
    var osanjiao_right=document.querySelectorAll('.sanjiao_right')[0];//右箭头
    var aprodexq2=document.querySelectorAll('.prodexq2');//两种显示方式

    osanjiao_left.onclick=function(){
        aprodexq2[0].className="prodexq2";
        aprodexq2[1].className="prodexq2 active";
    }
    osanjiao_right.onclick=function(){
        aprodexq2[0].className="prodexq2 active";
        aprodexq2[1].className="prodexq2";
    }


}

//// 格式数据列表
//function pro_dataLB(){
//
////格式数据列表点击显示隐藏
//    var oPJul=document.querySelectorAll('.PJul')[0];//数据列表
//    var aPJliC=oPJul.querySelectorAll('.PJliC');//数据列表内数据行
//    var aPJliB=oPJul.querySelectorAll('.PJliB');//数据行隐藏栏
//
//    for(var i=0;i<aPJliC.length;i++){
//        (function(j){
//            aPJliC[j].onclick=function(){
//                for(var k=0;k<aPJliB.length;k++){
//                    aPJliB[k].className="PJliB";
//                }
//                aPJliB[j].className="PJliB active";
//            }
//        })(i)
//    }
//}



//格式数据
function pro_data(){
// 格式数据树展开收起
    var aPJliBLi=document.querySelectorAll('.PJliBLi');
    for(var i=0;i<aPJliBLi.length;i++){
        (function(j){
            aPJliBLi[j].onclick=function(){
                console.log(this.className);
                if(this.className.indexOf("PJliBLi2")!=-1){
                    this.className="PJliBLi PJliBLi1";
                    var ofufu=this.parentNode.parentNode;
                    var ozizi=ofufu.querySelectorAll('.PJliBR')[0];
                    ozizi.style.display="none";
                }else if(this.className.indexOf("PJliBLi1")){
                    this.className="PJliBLi PJliBLi2";
                    var ofufu=this.parentNode.parentNode;
                    var ozizi=ofufu.querySelectorAll('.PJliBR')[0];
                    ozizi.style.display="inline-block";
                }
            }
        })(i)
    }
}

//点击格式数据
function pro_dataclick(){
// 筛选菜单框显示隐藏
    var oprodaclmRsx=document.querySelectorAll('.prodaclmRsx')[0];//获取筛选下拉按钮
    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
    var shaixuanPD=0;

    oprodaclmRsx.onclick=function(){
        if(shaixuanPD==0){
            oshaixuanZK.className="shaixuanZK active";
            shaixuanPD=1;
        }else{
            oshaixuanZK.className="shaixuanZK";
            shaixuanPD=0;
        }
        
    }

//

//筛选按钮显示隐藏选项
    var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];//获取筛选菜单
    var ashaixuanZKliI=document.querySelectorAll('.shaixuanZKliI');//获取所有筛选按钮
    var oprodaclmRzT=document.querySelectorAll('.prodaclmRzT')[1];//项目表头栏
    var aprodaclmRzTt2=oprodaclmRzT.querySelectorAll('.prodaclmRzTt2');//项目表头
    var oprodaclmRsjK=document.querySelectorAll('.prodaclmRsjK')[0];
    var oprodaclmRzB=oprodaclmRsjK.querySelectorAll('.prodaclmRzB')[0];//项目栏
    if(oprodaclmRzB){
    	var aprodaclmRzBz=oprodaclmRzB.querySelectorAll('.prodaclmRzBz');//每一条项目
    }
    
    
    console.log(aprodaclmRzTt2);

    //初始化筛选按钮判断
    var shaixuanBTPD=new Array();
    for(var i=0;i<ashaixuanZKliI.length;i++){
        shaixuanBTPD[i]=1;
    }



    for(var i=0;i<ashaixuanZKliI.length;i++){
        (function(j){
            ashaixuanZKliI[j].onclick=function(){
                if(shaixuanBTPD[j]==0){
                    ashaixuanZKliI[j].className="shaixuanZKliI active";
                    aprodaclmRzTt2[j].style.display="block";
                    for(var o=0;o<aprodaclmRzBz.length;o++){
                        var aprodaclmRzTt3=aprodaclmRzBz[o].querySelectorAll('.prodaclmRzTt3');//项目表项
                        aprodaclmRzTt3[j].style.display="block";
                    }
                    shaixuanBTPD[j]=1;
                }else{
                    ashaixuanZKliI[j].className="shaixuanZKliI";
                    aprodaclmRzTt2[j].style.display="none";
                    for(var o=0;o<aprodaclmRzBz.length;o++){
                        var aprodaclmRzTt3=aprodaclmRzBz[o].querySelectorAll('.prodaclmRzTt3');//项目表项
                        aprodaclmRzTt3[j].style.display="none";
                    }
                    shaixuanBTPD[j]=0;
                }
                
            }
        })(i)
    }
}


//文件
function pro_file(){
    var oprof_lbLtRtup=document.querySelectorAll('.prof_lbLtRtup')[0];//文件上传按钮
    var ofileaddK=document.querySelectorAll('.fileaddK')[0]//文件上传框
    var ofileaddTr=document.querySelectorAll('.fileaddTr')[0];//文件上传框关闭按钮
    var owidth=document.body.clientWidth;//屏幕宽度

    var ofileKpd=0;//文件上传框状态判断

    ofileaddTr.onclick=function(){
        if(ofileKpd==1){
            ofileaddK.style.display="none";
            ofileKpd=0;
        }
    }
    oprof_lbLtRtup.onclick=function(){
        if(ofileKpd==0){
        	if(floder_id != 0){
                ofileaddK.style.display="block";
                console.log(owidth/2);
                console.log(ofileaddK.offsetWidth/2);
                ofileaddK.style.left=owidth/2-ofileaddK.offsetWidth/2+"px";//创建框居中
                ofileKpd=1;
        	}else{
        		layer.msg("请选择文件夹");
        	}

        }
    }

//文件树
    var oprof_lbLmT=document.querySelectorAll('.prof_lbLmT')[0];
    var aPJliB1L=oprof_lbLmT.querySelectorAll('.PJliB1L');
    var aPJliB2L=oprof_lbLmT.querySelectorAll('.PJliB2L');

    var aPJliBL=[];

    for(var i=0;i<aPJliB1L.length;i++){
        aPJliBL.push(aPJliB1L[i]);
    }
    for(var i=0;i<aPJliB2L.length;i++){
        aPJliBL.push(aPJliB2L[i]);
    }

    console.log(aPJliBL);
    /*
    for(var i=0;i<aPJliBL.length;i++){
        (function(index){
            aPJliBL[index].onclick=function(event){
                for(var j=0;j<aPJliBL.length;j++){
                    aPJliBL[j].style.color="#666";
                }
                aPJliBL[index].style.color="#5ca0e5";
                event.stopPropagation();
            }
        })(i)
    }*/

    oprof_lbLmT.onclick=function(){
        for(var j=0;j<aPJliBL.length;j++){
            aPJliBL[j].style.color="#666";
        }
    }

    var aPJliB1Lt=document.querySelectorAll('.PJliB1Lt');
    var aPJliB2Lt=document.querySelectorAll('.PJliB2Lt');
    var awenjian=[];
    for(var i=0;i<aPJliB1Lt.length;i++){
        awenjian.push(aPJliB1Lt[i]);
    }
    for(var i=0;i<aPJliB2Lt.length;i++){
        awenjian.push(aPJliB2Lt[i]);
    }
    
//    console.log(awenjian);
//
//    var oprof_editK=document.querySelectorAll('.prof_editK')[0];
//    if(oprof_editK){
//        var oprof_aeMzp=oprof_editK.querySelectorAll('.prof_aeMzp')[0];
//        console.log(oprof_aeMzp);
//    }

    //文件树文字点击变蓝
    for(var i=0;i<awenjian.length;i++){
        (function(index){
            awenjian[index].onclick=function(){
                for(var j=0;j<awenjian.length;j++){
                    awenjian[j].style.color="#666";
                }
                awenjian[index].style.color="#5ca0e5";
                //将floderID更新一下并进行网络请求
                floder_id = awenjian[index].id;
                var id = awenjian[index].id;
        		//进行网络请求显示右面的文件夹
        		$.ajax({
        			url:"/wankangyuan/projectFloderFile/selectFilesByFloderId",
        			type:"post",
        			data:{
        				floder_id:id
        			},
        			dataType:"json",
        			success : function(data){
        				if(data.result == true){
        					//这个地方直接更新右面的文件列表
        					var filesList = $("#filesList");
        					filesList.empty();
        					for(var index in data.projectFiles){
        						filesList.append(
        							'<div class="prof_lbRmULmLI">'+
    	                                '<div class="fuxuanK2">'+
    	                                	'<input type="checkbox" class="input_check" id="check'+data.projectFiles[index].id
    	                                		+'" name="'+data.projectFiles[index].file_location+'" value="'+data.projectFiles[index].id+'">'+
    	                                	'<label for="check'+data.projectFiles[index].id+'"></label>'+
	    	                           	'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt2">'+data.projectFiles[index].file_name+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt3">'+data.projectFiles[index].file_type+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt4">'+data.projectFiles[index].file_size+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt5">'+data.projectFiles[index].create_datetime+'</div>'+
	                                    '<div class="prof_lbRmULmli prof_lbRmULt6">'+data.projectFiles[index].username+'</div>'+
	                                    '<a style="color:#33B7FF" href="http://localhost:8098/download/'+data.projectFiles[index].file_location+'" download="'+data.projectFiles[index].file_name+'">下载</a>'+
        							'</div>');
        					}
        				}else{
        					layer.msg(data.message);
        				}
        			},
        			error : function(){
        				alert("联网失败");
        			}
        			
        		});
                
                
            }
        })(i)
    }

//文件树添加根/叶、修改、删除
    var oprof_lbLtRaddg=document.querySelectorAll('.prof_lbLtRaddg')[0];//添加根按钮
    var oprof_lbLtRaddy=document.querySelectorAll('.prof_lbLtRaddy')[0];//添加叶按钮
    var oprof_lbLtRtch=document.querySelectorAll('.prof_lbLtRtch')[0];//修改按钮
    var oprof_lbLtRtde=document.querySelectorAll('.prof_lbLtRtde')[0];//删除按钮

    //添加
    var oprof_addK=document.querySelectorAll('.prof_addK')[0];//添加根、叶框
    var oprof_aeTx=oprof_addK.querySelectorAll('.prof_aeTx')[0];//添加根、叶框关闭按钮
    var oprof_aeb=oprof_addK.querySelectorAll('.prof_aeb')[0];//添加根、叶框提交按钮

    oprof_lbLtRaddg.onclick=function(){
    	g_y = 1;
        oprof_addK.style.display="block";
    }

    oprof_lbLtRaddy.onclick=function(){
    	g_y = 0;
        if(floder_id == 0){
        	layer.msg("请选中父文件夹");
        	return;
        }else{
        	 oprof_addK.style.display="block";
        }
    }

    oprof_aeTx.onclick=function(){
        oprof_addK.style.display="none";
    }
    oprof_aeb.onclick=function(){
        oprof_addK.style.display="none";
        var floder_name  = $("#leafFloderName").val();
        $.ajax({
        	url:"/wankangyuan/projectFloderFile/addProjectFloder",
        	type:"post",
        	data:{
        		g_y:g_y,
        		parent_id:floder_id,
        		floder_name:floder_name
        	},
        	dataType:"json",
        	success : function(data){
        		if(data.result == true){
        			window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
        		}else{
        			layer.msg("叶目录添加失败");
        		}
        	},
        	error : function(){
        		layer.msg("联网失败");
        	}
        });

    }


    //修改
    var oprof_editK=document.querySelectorAll('.prof_editK')[0];//添加根、叶框
    var oprof_aeTx=oprof_editK.querySelectorAll('.prof_aeTx')[0];//添加根、叶框关闭按钮
    var oprof_aeb=oprof_editK.querySelectorAll('.prof_aeb')[0];//添加根、叶框提交按钮

    oprof_lbLtRtch.onclick=function(){
    	if(floder_id == 0){
    		alert("请选中待修改文件夹");
    	}else{
    		oprof_editK.style.display="block";
    	}     
    }
    oprof_aeTx.onclick=function(){
        oprof_editK.style.display="none";
    }
    oprof_aeb.onclick=function(){
        oprof_editK.style.display="none";
        var floder_name  = $("#floderNameEdit").val();
        $.ajax({
        	url:"/wankangyuan/projectFloderFile/updateProjectFloder",
        	type:"post",
        	data:{
        		id:floder_id,
        		floder_name:floder_name
        	},
        	dataType:"json",
        	success : function(data){
        		if(data.result == true){
        			window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
        		}else{
        			alert("文件名称修改失败！");
        		}
        	},
        	error : function(){
        		alert("联网失败");
        	}
        });
        
    }


    //删除
    var oprof_delK=document.querySelectorAll('.prof_delK')[0];//添加根、叶框
    var oprof_aeTx=oprof_delK.querySelectorAll('.prof_aeTx')[0];//添加根、叶框关闭按钮
    var oprof_aeb=oprof_delK.querySelectorAll('.prof_aeb')[0];//添加根、叶框提交按钮

    oprof_lbLtRtde.onclick=function(){
    	if(floder_id == 0){
    		alert("请选中待删除文件夹！");
    	}else{
    		oprof_delK.style.display="block";
    	}    
    }
    oprof_aeTx.onclick=function(){
        oprof_delK.style.display="none";
    }
    oprof_aeb.onclick=function(){
    	$.ajax({
    		url:"/wankangyuan/projectFloderFile/deleteProjectFloder",
    		type:"post",
    		data:{
    			floder_id:floder_id
    		},
    		dataType:"json",
    		success : function(data){
    			if(data.result == true){
    				alert("删除成功！");
    				window.location.href="/wankangyuan/projectFloderFile/selectProjectFloderByProjectId";
    			}else{
    				alert("删除失败！");
    			}
    		},
    		error : function(){
    			alert("联网失败");
    		}
    		
    	});
        oprof_delK.style.display="none";
    }

}

//好友管理页面
function friend_manage(){
    var ofriendMMlTT=document.querySelectorAll('.friendMMlTT')[0];//除我的好友外
    var afriendMMlTTz=ofriendMMlTT.querySelectorAll('.friendMMlTTz');//每个组织结构
    var afriendMMlTTzBz=document.querySelectorAll('.friendMMlTTzBz');//每个组织结构内的组

    var afriendMMrz=document.querySelectorAll('.friendMMrz');//右侧成员框和好友框

    var ofriend_qunfa=document.querySelectorAll('.friend_qunfa')[0];//群发消息按钮
    var ofriend_yichuzu=document.querySelectorAll('.friend_yichuzu')[0];//从组中移除按钮
    var ofriend_yichuhy=document.querySelectorAll('.friend_yichuhy')[0];//移除好友按钮

    var osearch_2=document.querySelectorAll('.search_2')[0];//上方按钮栏搜索栏
    var osearch_3=document.querySelectorAll('.search_3')[0];//上方按钮栏搜索栏


    var ozhizhen=document.createElement('img');//指针图片
    ozhizhen.className="zhizhen";
    ozhizhen.src="../static/img/zhizhen2.png";

    var panduanjicunqi=0;
    
    var afriendMMlTTzBz2=document.querySelectorAll('.friendMMlTTzBz2');
    var afriendMMlTTzBz2Tk=document.querySelectorAll('.friendMMlTTzBz2Tk');
    var afriendMMlTTzBz2Mk=document.querySelectorAll('.friendMMlTTzBz2Mk');

    var zuzhi_PD=[];
    for(var i=0;i<afriendMMlTTz.length;i++){
        zuzhi_PD.push(0);
    }

//console.log(afriendMMlTTz.length);
//组织结构点击效果
    for(var i=0;i<afriendMMlTTz.length;i++){
        (function(index){
            var ofriendMMlTTzT=afriendMMlTTz[index].querySelectorAll('.friendMMlTTzT')[0];
            ofriendMMlTTzT.onclick=function(){

                //console.log("添加组预存框"+ozzsyKpd.value);

                // console.log(zuzhi_PD[index]);
                if(zuzhi_PD[index]==0){
                    panduanjicunqi=0;
                }else if(zuzhi_PD[index]==1){
                    panduanjicunqi=1;
                }
                // console.log(panduanjicunqi);

                for(var j=0;j<afriendMMlTTz.length;j++){
                    afriendMMlTTz[j].className="friendMMlTTz";
                    zuzhi_PD[j]=0;
                }

                if(panduanjicunqi==0){
                    afriendMMlTTz[index].className="friendMMlTTz active";
                    zuzhi_PD[index]=1;
                }else if(panduanjicunqi==1){
                    afriendMMlTTz[index].className="friendMMlTTz active2";
                    zuzhi_PD[index]=0;
                }

            }
        })(i)
    }



//特殊组的点击效果（该组内有包含组的时候）
    var teshuzuPD=[];
    for(var i=0;i<afriendMMlTTzBz2.length;i++){
        teshuzuPD.push(0);
    }
    // console.log(teshuzuPD);
    
    for(var i=0;i<afriendMMlTTzBz2.length;i++){
        (function(index){
            afriendMMlTTzBz2Tk[index].onclick=function(){
                if(teshuzuPD[index]==0){
                    afriendMMlTTzBz2[index].className="friendMMlTTzBz2 active";
                    afriendMMlTTzBz2Mk[index].style.display="block";
                    teshuzuPD[index]=1;

                }else if(teshuzuPD[index]==1){
                    afriendMMlTTzBz2[index].className="friendMMlTTzBz2";
                    afriendMMlTTzBz2Mk[index].style.display="none";
                    teshuzuPD[index]=0;
                }
                
            }
        })(i)
    }
    
    


  
    // var ozzjgKpd=0;//添加组织结构框判断
  //右侧多选
    for(var i=0;i<afriendMMrz.length;i++){
        (function(index){
            var oquanxuanK=afriendMMrz[index].querySelectorAll('.quanxuanK')[0];
            var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];

            var afuxuan=[];
            var afuxuanK=[];

            if(afriendMMrz[index].querySelectorAll('.fuxuanK2')[0]){
                afuxuanK=afriendMMrz[index].querySelectorAll('.fuxuanK2');
                //console.log("k2");
            }else if(afriendMMrz[index].querySelectorAll('.fuxuanK3')[0]){
                afuxuanK=afriendMMrz[index].querySelectorAll('.fuxuanK3');
                //console.log("k3");
            }

            if(afuxuanK[0]){
                for(var i=0;i<afuxuanK.length;i++){
                    afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
                }
            }
            

            oquanxuanK.onchange=function(){
                if(oquanxuan.checked){
                    for(var i=0;i<afuxuanK.length;i++){
                        afuxuan[i].checked=1;
                    }
                }else{
                    console.log(2);
                    for(var i=0;i<afuxuanK.length;i++){
                        afuxuan[i].checked=0;
                    }
                }
            }


            if(afuxuanK[0]){
                for(var i=0;i<afuxuanK.length;i++){
                    (function(index){
                        afuxuanK[i].onchange=function(){
                            var fuxuanPD=0;
                            for(var j=0;j<afuxuanK.length;j++){
                                if(afuxuan[j].checked){
                                    fuxuanPD++;
                                }
                                console.log(afuxuan[j].checked);
                            }
                            console.log(fuxuanPD);
                            if(fuxuanPD==afuxuanK.length){
                                oquanxuan.checked=1;
                            }else if(fuxuanPD!=afuxuanK.length){
                                oquanxuan.checked=0;
                            }
                        }
                    })(i)
                }
            }
        })(i)
    }
}

function friendmanage_quanxuan(){
//右侧多选
    var afriendMMrz=document.querySelectorAll('.friendMMrz');//右侧成员框和好友框
    for(var i=0;i<afriendMMrz.length;i++){
        (function(index){
            var oquanxuanK=afriendMMrz[index].querySelectorAll('.quanxuanK')[0];
            var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];

            var afuxuan=[];
            var afuxuanK=[];

            if(afriendMMrz[index].querySelectorAll('.fuxuanK2')[0]){
                afuxuanK=afriendMMrz[index].querySelectorAll('.fuxuanK2');
                console.log("k2");
            }else if(afriendMMrz[index].querySelectorAll('.fuxuanK3')[0]){
                afuxuanK=afriendMMrz[index].querySelectorAll('.fuxuanK3');
                console.log("k3");
            }else{
                console.log("无");
            }

            if(afuxuanK[0]){
                for(var i=0;i<afuxuanK.length;i++){
                    afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
                }
            }
            

            oquanxuanK.onchange=function(){
                if(oquanxuan.checked){
                    for(var i=0;i<afuxuanK.length;i++){
                        afuxuan[i].checked=1;
                    }
                }else{
                    console.log(2);
                    for(var i=0;i<afuxuanK.length;i++){
                        afuxuan[i].checked=0;
                    }
                }
            }


            if(afuxuanK[0]){
                for(var i=0;i<afuxuanK.length;i++){
                    (function(index){
                        afuxuanK[i].onchange=function(){
                            var fuxuanPD=0;
                            for(var j=0;j<afuxuanK.length;j++){
                                if(afuxuan[j].checked){
                                    fuxuanPD++;
                                }
                                console.log(afuxuan[j].checked);
                            }
                            console.log(fuxuanPD);
                            if(fuxuanPD==afuxuanK.length){
                                oquanxuan.checked=1;
                            }else if(fuxuanPD!=afuxuanK.length){
                                oquanxuan.checked=0;
                            }
                        }
                    })(i)
                }
            }
        })(i)
    }
}

//成员
function pro_member(){

	//权限管理框的显示隐藏以及位置
	
    var opro_manGL=document.querySelectorAll('.pro_manGL')[0]//权限管理按钮
    var oQXGLk=document.querySelectorAll('.QXGLk')[0];//权限管理框
    var oQXGLkTr=document.querySelectorAll('.QXGLkTr')[0]//权限管理框关闭按钮

    var QXGLkpd=0;

    oQXGLk.style.left=document.body.clientWidth/2-oQXGLk.offsetWidth/2+'px';

    opro_manGL.onclick=function(){
        if(QXGLkpd){
            oQXGLk.style.display='none';
            QXGLkpd=0;
        }else{
                                	
            oQXGLk.style.display="block";
            QXGLkpd=1;
            oQXGLk.style.left=document.body.clientWidth/2-oQXGLk.offsetWidth/2+'px';
        }
    }
    oQXGLkTr.onclick=function(){
        oQXGLk.style.display='none';
        QXGLkpd=0;
    }

    // 添加成员框的显示隐藏

    var opro_manadd=document.querySelectorAll('.pro_manadd')[0];//添加成员按钮
    var omemaddK=document.querySelectorAll('.memaddK')[0];//添加成员框
    var omemaddKx=document.querySelectorAll('.memaddKx')[0];//添加成员框关闭按钮

    var memaddKpd=0;

    opro_manadd.onclick=function(){
        if(memaddKpd){
            omemaddK.style.display='none';
            memaddKpd=0;
        }else{
            omemaddK.style.display="block";
            memaddKpd=1;
        }
    }
    omemaddKx.onclick=function(){
        omemaddK.style.display='none';
        memaddKpd=0;
    }


}

//讨论版
function pro_discuss(){
//    var opaixu=document.querySelectorAll('.paixu')[0];//排序框依据下拉按钮
//    //var asortZc=opaixu.querySelectorAll('.sortZc');//排序依据下拉按钮
//    var asortulK=opaixu.querySelectorAll('.sortulK');//排序下拉框
//
//    var paixuKpd=[];
//    for(var i=0;i<asortZc.length;i++){
//        paixuKpd[i]=0;
//    }
//
//    for(var i=0;i<asortZc.length;i++){
//        (function(index){
//            asortZc[index].onclick=function(){
//                if(paixuKpd[index]==0){
//                    for(var j=0;j<asortulK.length;j++){
//                        asortulK[j].className="sortulK";
//                        paixuKpd[j]=0;
//                    }
//                    asortulK[index].className='sortulK active';
//                    paixuKpd[index]=1;
//                }else{
//                    asortulK[index].className='sortulK';
//                    paixuKpd[index]=0;
//                }
//
//            }
//
//        })(i)
//    }

//创建框的位置以及显示隐藏效果
    var oaddtheme=document.querySelectorAll('.addtheme')[0];//创建主题按钮
    var othemeadd=document.querySelectorAll('.themeadd')[0];//主题创建框
    var othemeaddX=document.querySelectorAll('.themeaddX')[0];//主题创建框关闭按钮
    var othemeaddMk=document.querySelectorAll('.themeaddMk')[0];//创建框内容
    var othemeaddBc=document.querySelectorAll('.themeaddBc')[0];//创建框取消按钮

    var themeaddPD=0;

    // othemeadd.style.left=document.body.clientWidth/2-othemeadd.offsetWidth/2+"px";

    oaddtheme.onclick=function(){
        if(themeaddPD==0){
            othemeadd.style.display="block";
            // othemeadd.style.left=document.body.clientWidth/2-othemeadd.offsetWidth/2+"px";
            themeaddPD=1;
        }else{
            othemeadd.style.display="none";
            themeaddPD=0;
            othemeaddMk.value="";
        }
    }
    othemeaddX.onclick=function(){
        othemeadd.style.display="none";
        themeaddPD=0;
        othemeaddMk.value="";
    }

    othemeaddBc.onclick=function(){
        othemeadd.style.display="none";
        themeaddPD=0;
        othemeaddMk.value="";
    }

}

//格式数据——我的
function data_mine(){

//格式数据添加至项目框显示隐藏
    var opro_addK=document.querySelectorAll('.pro_addK')[0];//格式数据添加至项目
    var opro_addk=opro_addK.querySelectorAll('.pro_addk')[0];//添加按钮
    var opro_addul=document.querySelectorAll('.pro_addul')[0];//添加项目列表
    var apro_addli=opro_addul.querySelectorAll('.pro_addli');//添加各个项目

    // pro_addkPD=0;

    opro_addk.onclick=function(event){
        // if(pro_addkPD==0){
            opro_addul.style.display="block";
        //     pro_addkPD=1;
        // }else{
        //     opro_addul.style.display="none";
        //     pro_addkPD=0;
        // }
        event.stopPropagation();
    }
    document.onclick=function(){
        opro_addul.style.display="none";
    }
    for(var i=0;i<apro_addli.length;i++){
        (function(index){
            apro_addli[index].onclick=function(){
                opro_addul.style.display="none";
                pro_addkPD=0;
            }
        })(i)
    }


    var opro_addul=document.querySelectorAll('.pro_addul')[0];

    opro_addul.style.right="0";
}


//应用——我的
function app_mine(){

//应用添加至项目框显示隐藏
    var opro_addK=document.querySelectorAll('.pro_addK')[0];//应用添加至项目
    var opro_addk=opro_addK.querySelectorAll('.pro_addk')[0];//添加按钮
    var opro_addul=document.querySelectorAll('.pro_addul')[0];//添加项目列表
    var apro_addli=opro_addul.querySelectorAll('.pro_addli');//添加各个项目

    var oapp_typeK=document.querySelectorAll('.app_typeK')[0];//应用添加至项目
    var oapp_typek=oapp_typeK.querySelectorAll('.app_typek')[0];//添加按钮
    var oapp_typeul=document.querySelectorAll('.app_typeul')[0];//添加项目列表
    var aapp_typeli=oapp_typeul.querySelectorAll('.app_typeli');//添加各个项目

    // pro_addkPD=0;

    opro_addk.onclick=function(event){
        // if(pro_addkPD==0){
            opro_addul.style.display="block";
            opro_addul.style.zIndex+=1;
            oapp_typeul.style.display="none";
        //     pro_addkPD=1;
        // }else{
        //     opro_addul.style.display="none";
        //     pro_addkPD=0;
        // }
        event.stopPropagation();
    }
    for(var i=0;i<apro_addli.length;i++){
        (function(index){
            apro_addli[index].onclick=function(){
                opro_addul.style.display="none";
                // pro_addkPD=0;
            }
        })(i)
    }

//应用类别筛选框显示隐藏
    

    // app_typekPD=0;

    oapp_typek.onclick=function(event){
        // if(app_typekPD==0){
            oapp_typeul.style.display="block";
            oapp_typeul.style.zIndex+=1;
            opro_addul.style.display="none";
        //     app_typekPD=1;
        // }else{
        //     oapp_typeul.style.display="none";
        //     app_typekPD=0;
        // }
        event.stopPropagation();
    }
    for(var i=0;i<aapp_typeli.length;i++){
        (function(index){
            aapp_typeli[index].onclick=function(){
                oapp_typeul.style.display="none";
                // app_typekPD=0;
                console.log(index)
            }
        })(i)
    }

    document.onclick=function(){
        opro_addul.style.display="none";
        // pro_addkPD=0;
        oapp_typeul.style.display="none";
        // app_typekPD=0;
    }
}

function app_public(){
//应用类别筛选框显示隐藏
    var oapp_typeK=document.querySelectorAll('.app_typeK')[0];//应用添加至项目
    var oapp_typek=oapp_typeK.querySelectorAll('.app_typek')[0];//添加按钮
    var oapp_typeul=document.querySelectorAll('.app_typeul')[0];//添加项目列表
    var aapp_typeli=oapp_typeul.querySelectorAll('.app_typeli');//添加各个项目

    // app_typekPD=0;

    oapp_typek.onclick=function(event){
        // if(app_typekPD==0){
            oapp_typeul.style.display="block";
            oapp_typeul.style.zIndex+=1;
        //     app_typekPD=1;
        // }else{
        //     oapp_typeul.style.display="none";
        //     app_typekPD=0;
        // }
        event.stopPropagation();
    }
    for(var i=0;i<aapp_typeli.length;i++){
        (function(index){
            aapp_typeli[index].onclick=function(){
                oapp_typeul.style.display="none";
                // app_typekPD=0;
                console.log(index)
            }
        })(i)
    }

    document.onclick=function(){
        // opro_addul.style.display="none";
        // pro_addkPD=0;
        oapp_typeul.style.display="none";
        // app_typekPD=0;
    }
}

//格式数据——我创建的
function data_create(){
//导入数据框显示隐藏
    var opro_inport=document.querySelectorAll('.pro_inport')[0];//导入数据按钮
    var oinportK=document.querySelectorAll('.inportK')[0];//导入数据框
    var oinportTx=document.querySelectorAll('.inportTx')[0];//导入数据框关闭按钮

    var inportPD=0;

    opro_inport.onclick=function(){
        if(inportPD==0){
            oinportK.style.display="block";
            inportPD=1;
        }else{
            oinportK.style.display="none";
            inportPD=0;
        }
    }
    oinportTx.onclick=function(){
        oinportK.style.display="none";
        inportPD=0;
    }

//导入数据框上传文件
    var oinportMu=document.querySelectorAll('.inportMu')[0];//上传文件按钮
    var oinportMf=document.querySelectorAll('.inportMf')[0];//上传文件input

    oinportMu.onclick=function(){
        oinportMf.click();
    }


//添加源数据
    var opro_adddata=document.querySelectorAll('.pro_adddata')[0];//添加源数据按钮
    var oadddataK=document.querySelectorAll('.adddataK')[0];//添加源数据框
    var oadddataTx=oadddataK.querySelectorAll('.adddataTx')[0];//添加源数据框关闭按钮
    var aadddataMliTT=oadddataK.querySelectorAll('.adddataMliTT');//添加源数据框文本框


    var adddataPD=0;//添加源数据框状态判断

    opro_adddata.onclick=function(){
        if(adddataPD==0){
            oadddataK.style.display="block";
            adddataPD=1;
            for(var i=0;i<aadddataMliTT.length;i++){
                aadddataMliTT[i].value="";//打开添加源数据框的时候清空所有文本框和文本域
            }
        }
    }

    oadddataTx.onclick=function(){
        oadddataK.style.display="none";
        adddataPD=0;
    }


}


//应用-我创建的应用页面
function app_create(){

//应用创建框的显示隐藏
    var opro_createapp=document.querySelectorAll('.pro_createapp')[0];//应用创建按钮
    var ocreateappK=document.querySelectorAll('.createappK')[0];//应用创建框
    var oinportTx=ocreateappK.querySelectorAll('.inportTx')[0];//应用创建框关闭按钮

    var appcreatePD=0;

    opro_createapp.onclick=function(){
        // if(appcreatePD==0){
            ocreateappK.style.display="block";
            appcreatePD=1;
        // }else{
        //     ocreateappK.style.display="none";
        //     appcreatePD=0;
        // }
    }
    oinportTx.onclick=function(){
        ocreateappK.style.display="none";
        appcreatePD=0;
    }



}

//项目应用结果重新运行
function pro_appendre(){
//选择格式数据框显示隐藏
    var aproappendrep=document.querySelectorAll('.proappendrep');//参数框
    var oproreK=document.querySelectorAll('.proreK')[0];//格式数据框
    var oinportTx=oproreK.querySelectorAll('.inportTx')[0];//格式数据框关闭按钮
    var oproreb2=oproreK.querySelectorAll('.proreb2')[0];//格式数据框关闭按钮2
    var oproreb1=oproreK.querySelectorAll('.proreb1')[0];//格式数据框提交按钮

    var proappendreRT = document.querySelectorAll('.proappendreRT');//选择格式数据对话框
    proappendreRT[0].onclick=function(){
    	oproreK.style.display="block";
    }
    
    oinportTx.onclick=function(){
        oproreK.style.display="none";
    }
    oproreb2.onclick=function(){
        oproreK.style.display="none";
    }

// 重新选择格式数据框格式数据显示
    var oproreM=document.querySelectorAll('.proreM')[0];
    var aproreMzc=oproreM.querySelectorAll('.proreMzc');
    var aPJliB=oproreM.querySelectorAll('.PJliB');

    for(var i=0;i<aproreMzc.length;i++){
        (function(index){
            aproreMzc[index].onclick=function(){
                console.log(index)
                for(var j=0;j<aPJliB.length;j++){
                    aPJliB[j].className="PJliB";
                }
                aPJliB[index].className="PJliB active";
            }
        })(i)
    }


//选择格式数据
    var aproreMli=oproreM.querySelectorAll('.proreMli');
    var proreMliPD=[];
    // console.log(aproreMli);

    for(var i=0;i<aproreMli.length;i++){
        proreMliPD[i]=0;
        // console.log(proreMliPD);
        (function(index){
            aproreMli[index].onclick=function(){
                if(proreMliPD[index]==0){
                    // aproreMli.className="proreMli active";
                    aproreMli[index].style.color="#5ca0e5";
                    proreMliPD[index]=1;
                }else{
                    // aproreMli.className="proreMli";
                    aproreMli[index].style.color="#666";
                    proreMliPD[index]=0;
                }
            }
        })(i)
    }


}


//应用说明2
function app_exp2(){
//关键字
    var oappexpGJZK=document.querySelectorAll('.appexpGJZK')[0];//关键字框
    var oappexpGJZadp=oappexpGJZK.querySelectorAll('.appexpGJZadp')[0];//关键字增加框
    var oappexpGJZadb=oappexpGJZK.querySelectorAll('.appexpGJZadb')[0];//关键字增加按钮
    var oappexpGJZKC=oappexpGJZK.querySelectorAll('.appexpGJZKC')[0];//关键字包含框


    add_deleteX();
    function add_deleteX(){
        var aappexpGJZ=oappexpGJZKC.querySelectorAll('.appexpGJZ');
        for(var i=0;i<aappexpGJZ.length;i++){
            (function(index){
                var oappexpGJZx=aappexpGJZ[index].querySelectorAll('.appexpGJZx')[0];
                oappexpGJZx.onclick=function(){
                    oappexpGJZKC.removeChild(oappexpGJZKC.children[index]);
                    add_deleteX();
                }
            })(i)
        }
    }
    
    oappexpGJZadb.onclick=function(){
        if(oappexpGJZadp.value!=" "&&oappexpGJZadp.value!=""){
            var oappexpGJZ=document.createElement("div");
            oappexpGJZ.className="appexpGJZ";

            var oappexpGJZt=document.createElement("div");
            oappexpGJZt.className="appexpGJZt";
            oappexpGJZt.innerHTML=oappexpGJZadp.value;
            // console.log(oappexpGJZadp.value);

            var oappexpGJZx=document.createElement("div");
            oappexpGJZx.className="appexpGJZx";

            oappexpGJZ.appendChild(oappexpGJZt);
            oappexpGJZ.appendChild(oappexpGJZx);
            oappexpGJZKC.appendChild(oappexpGJZ);

            oappexpGJZadp.value="";

            add_deleteX();
        }
    }


}

function data_dataclick2(){
    var oPJliBK=document.querySelectorAll('.PJliBK')[0];

    var aPJliBLt=[];
    var aPJliB1Lt=oPJliBK.querySelectorAll('.PJliB1Lt');
    var aPJliB2Lt=oPJliBK.querySelectorAll('.PJliB2Lt');

    for(var i=0;i<aPJliB1Lt.length;i++){
        aPJliBLt.push(aPJliB1Lt[i]);
    }
    for(var i=0;i<aPJliB2Lt.length;i++){
        aPJliBLt.push(aPJliB2Lt[i]);
    }
    // console.log(aPJliBLt);


    // for(var i=0;i<aPJliBLt.length;i++){
    //     (function(index){
    //         aPJliBLt[index].onclick=function(event){
    //             for(var j=0;j<aPJliBLt.length;j++){
    //                 aPJliBLt[j].style.color="#666";
    //             }
    //             aPJliBLt[index].style.color="#5ca0e5";
    //             // event.stopPropagation();
    //         }
    //     })(i)
    // }


    // document.onclick=function(){
    //     for(var j=0;j<aPJliBLt.length;j++){
    //         aPJliBLt[j].style.color="#666";
    //     }
    // }

//添加修改节点框
    var odaclLb_add=document.querySelectorAll('.daclLb_add')[0];//添加按钮
    var odaclLb_mod=document.querySelectorAll('.daclLb_mod')[0];//修改按钮
    var odaclLb_addroot=document.querySelectorAll('.daclLb_addroot')[0];//添加根按钮
    var odataeditK=document.querySelectorAll('.dataeditK')[0];//框
    var odataeditTx=odataeditK.querySelectorAll('.dataeditTx')[0];//关闭按钮

    odaclLb_add.onclick=function(){
        odataeditK.style.display="block";
    }
    odaclLb_mod.onclick=function(){
        odataeditK.style.display="block";
    }
    odaclLb_addroot.onclick=function(){
        odataeditK.style.display="block";
    }
    odataeditTx.onclick=function(){
        odataeditK.style.display="none";
    }
}

function data_dataclick(){
    var oprodaclmRz=document.querySelectorAll('.prodaclmRz')[0];
    var oquanxuanK=oprodaclmRz.querySelectorAll('.quanxuanK')[0];
    var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];
    var afuxuanK=oprodaclmRz.querySelectorAll('.fuxuanK5');

    var afuxuan=[];
    // var afuxuanK=[];

    if(afuxuanK[0]){
        for(var i=0;i<afuxuanK.length;i++){
            afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
        }
    }
    console.log(afuxuanK)
    
        
    oquanxuanK.onchange=function(){
        if(oquanxuan.checked){
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan[i].checked=1;
            }
        }else{
            // console.log(2);
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan[i].checked=0;
            }
        }
    }
    


    if(afuxuanK[0]){
        for(var i=0;i<afuxuanK.length;i++){
            (function(index){
                afuxuanK[i].onchange=function(){
                    var fuxuanPD=0;
                    for(var j=0;j<afuxuanK.length;j++){
                        if(afuxuan[j].checked){
                            fuxuanPD++;
                        }
                        console.log(afuxuan[j].checked);
                    }
                    console.log(fuxuanPD);
                    if(fuxuanPD==afuxuanK.length){
                        oquanxuan.checked=1;
                    }else if(fuxuanPD!=afuxuanK.length){
                        oquanxuan.checked=0;
                    }
                }
            })(i)
        }
    }




    var oprodaclmRz2=document.querySelectorAll('.prodaclmRz2')[0];
    var oquanxuanK2=oprodaclmRz2.querySelectorAll('.quanxuanK')[0];
    var oquanxuan2=oquanxuanK2.querySelectorAll('.input_check')[0];
    var afuxuanK2=oprodaclmRz2.querySelectorAll('.fuxuanK5');


    var afuxuan2=[];
    // var afuxuanK2=[];

    if(afuxuanK2[0]){
        for(var i=0;i<afuxuanK2.length;i++){
            afuxuan2.push(afuxuanK2[i].querySelectorAll('.input_check')[0]);
        }
    }

    oquanxuanK2.onchange=function(){
        if(oquanxuan2.checked){
            for(var i=0;i<afuxuanK2.length;i++){
                afuxuan2[i].checked=1;
            }
        }else{
            // console.log(2);
            for(var i=0;i<afuxuanK2.length;i++){
                afuxuan2[i].checked=0;
            }
        }
    }

    if(afuxuanK2[0]){
        for(var i=0;i<afuxuanK2.length;i++){
            (function(index){
                afuxuanK2[i].onchange=function(){
                    var fuxuanPD2=0;
                    for(var j=0;j<afuxuanK2.length;j++){
                        if(afuxuan2[j].checked){
                            fuxuanPD2++;
                        }
                        console.log(afuxuan2[j].checked);
                    }
                    console.log(fuxuanPD2);
                    if(fuxuanPD2==afuxuanK2.length){
                        oquanxuan2.checked=1;
                    }else if(fuxuanPD2!=afuxuanK2.length){
                        oquanxuan2.checked=0;
                    }
                }
            })(i)
        }
    }


//添加修改单个数据
    var oclmRsb_add=document.querySelectorAll('.clmRsb_add')[0];//添加数据按钮
    var oclmRsb_modify=document.querySelectorAll('.clmRsb_modify')[0];//修改数据按钮
    var oclmRsb_inport=document.querySelectorAll('.clmRsb_inport')[0];//导入按钮
    var oclmReditK=document.querySelectorAll('.clmReditK')[0];//添加修改数据框
    var oclmReditTx=oclmReditK.querySelectorAll('.clmReditTx')[0];//添加修改数据框关闭按钮
    var oclmReditb=oclmReditK.querySelectorAll('.clmReditb')[0];//添加修改数据框提交按钮

    var oclmRinportK=document.querySelectorAll('.clmRinportK')[0];//导入框
    var oinportTx=oclmRinportK.querySelectorAll('.inportTx')[0];//导入框


    oclmRsb_add.onclick=function(){
        oclmReditK.style.display="block";
    }
    oclmRsb_modify.onclick=function(){
        oclmReditK.style.display="block";
    }

    oclmReditTx.onclick=function(){
        oclmReditK.style.display="none";
    }


    oclmRsb_inport.onclick=function(){
        oclmRinportK.style.display="block";
    }
    oinportTx.onclick=function(){
        oclmRinportK.style.display="none";
    }


}