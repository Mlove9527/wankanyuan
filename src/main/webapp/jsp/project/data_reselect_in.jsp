<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="/wankangyuan/static/css/project1.css" />
<script type="text/javascript"
    src="/wankangyuan/jsp/project/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        //project0();
        //pro_data();
        //pro_dataclick();
        //data_dataclick2();
        //data_click_guanxi();
        //data_dataclick(); 
    }
</script>
<body>
<div class="Box">
    <div class="box">
	    <div class="prodainm">
			<div class="prodainmL">
			    <div class="PJliBK">
			        <c:forEach items="${formatTypeFolders}" var="formatTypeTemp"
			            varStatus="status">
			            <div class="PJliB1">
			                <div class="PJliB1L">
			                    <div class="fuxuanK4 fuxuanK41">
			                        <input type="checkbox" class="input_check"
			                            name="${formatTypeTemp.ft_id }"
			                            id="check1_${formatTypeTemp.ft_id }"> <label
			                            for="check1_${formatTypeTemp.ft_id }"></label>
			                    </div>
			                    <div class="PJliB1Lt">${formatTypeTemp.ft_name }</div>
			                    <div class="PJliBLi PJliBLi2"></div>
			                </div>
			                <div class="PJliBR">
			                    <c:forEach items="${formatTypeTemp.formatDataNodes}"
			                        var="formatDataNodeTemp" varStatus="status">
			                        <div class="PJliB2">
			                            <div class="PJliB2L">
			                                <div class="fuxuanK4 fuxuanK42">
			                                    <input type="checkbox" class="input_check"
			                                        value="${formatTypeTemp.ft_id }"
			                                        name="${formatDataNodeTemp.key}"
			                                        id="check1_${formatDataNodeTemp.key}"> <label
			                                        for="check1_${formatDataNodeTemp.key}"></label>
			                                </div>
			                                <div class="PJliB2Lt" id="${formatDataNodeTemp.key}">${formatDataNodeTemp.value}
			                                </div>
			
			                            </div>
			                        </div>
			                    </c:forEach>
			                </div>
			            </div>
			        </c:forEach>
			    </div>
			</div>
			<div class="prodaclmR">
		          <div class="prodaclmRz">
                       <div class="pdclmRz_ul"></div>
                       <div class="prodaclmRzT">

                           <div class="prodaclmRzTt prodaclmRzTtmz">名称</div>
                           <div class="prodaclmRzTt prodaclmRzTtnr">内容</div>
                       </div>
                       <div class="prodaclmRzB">
                           <c:forEach items="${metaDatas}" var="metaDataListTemp">
                               <div class="prodaclmRzBz">
                                   <div class="prodaclmRzBzt prodaclmRzTtmz">${metaDataListTemp[1] }</div>
                                   <div class="prodaclmRzBzt prodaclmRzTtmz">
                                       <input class="meta_input" id="${metaDataListTemp[0] }"
                                           value="${metaDataListTemp[2] }" />
                                   </div>
                                   <div class="pdclmRz_li pdclmRz_edit"
                                       onclick="meta_input_submit('${metaDataListTemp[0] }')">保存</div>

                               </div>
                           </c:forEach>
                       </div>
                   </div>
                   <br>
			    <div class="prodaclmRz2">
			        <div class="prodaclmRsxK">
			            <div class="prodaclmRsx">
			                <div class="prodaclmRsxT">筛选</div>
			                <img src="/wankangyuan/static/img/sanjiao_blue.png" alt=""
			                    class="prodaclmRsxI" />
			            </div>
			
			            <div class="search" style="margin-top:0px;">
			                <div class="searchC">
			                    <img src="/wankangyuan/static/img/search.png" alt=""
			                        class="searchCi" /> <input type="text" class="searchCt"
			                        placeholder="搜索数据" value="${searchFirstWordNode}" />
			                </div>
			            </div>
			        </div>
			
			        <div class="shaixuanZK">
			            <div class="shaixuanZKC">
			                <c:forEach items="${data}" var="dataTemp">
			                    <div class="shaixuanZKli">
			                        <div class="shaixuanZKliI active"></div>
			                        <div class="shaixuanZKliT">${dataTemp.ff_name }</div>
			                    </div>
			                </c:forEach>
			            </div>
			        </div>
			
			        <div class="prodaclmRsjK">
			            <table class="PJul" >
			        <tr class="PJList">
			            <td>
			                <div class="quanxuanK fxK1">
			                    <input type="checkbox" class="input_check" id="check4_0">
			                    <label for="check4_0"></label>
			                </div>
			                </td>
			                <td><div class="prodaclmRzTt2" id="54">546</div></td>
			                <td><div class="prodaclmRzTt2" id="542">54236</div></td>
			            </tr>
		                <tr class="PJli">
                              <td>
                                <div class="fuxuanK5 fxK1 fx4">
                                    <input type="checkbox" class="input_check"
                                        name="${dataDataTempTemp}" id="check4_1">
                                    <label for="check4_1"></label>
                                </div>
                              </td>
                                <td><div class="prodaclmRzTt3">
                                    <span>8549684984</span>
                                </div></td>
                                <td><div class="prodaclmRzTt3">
                                    <span>45</span>
                                </div>
                            </td>
		                 </tr>
		                 <tr class="PJli">
                              <td>
                                <div class="fuxuanK5 fxK1 fx4">
                                    <input type="checkbox" class="input_check"
                                        name="${dataDataTempTemp}" id="check4_2">
                                    <label for="check4_2"></label>
                                </div>
                              </td>
                                <td><div class="prodaclmRzTt3">
                                    <span>8549684984</span>
                                </div></td>
                                <td><div class="prodaclmRzTt3">
                                    <span>45</span>
                                </div>
                            </td>
                         </tr>
			            </table>
			                <div class="pageK" id="box"></div>
			                
			            </div>
			            <div class="BTSX2">
			            <div class="BTSXc">
			                <div class="BTSXcli">
			                    <div class="BTSXcliT">排序：</div>
			                    <img src="/wankangyuan/static/img/sort_up.png" alt=""
			                        class="BTSXcliI" /> <img
			                        src="/wankangyuan/static/img/sort_down.png" alt=""
			                        class="BTSXcliI" /> <input type="text" class="BTSXcliIpd"
			                        style="display: none;" />
			                </div>
			                <div class="BTSXcli">
			                    <div class="BTSXcliT">过滤：</div>
			                    <input type="text" class="BTSXcliGLK" />
			                    <button id="guolv">过滤</button>
			                </div>
			                <div class="BTSXcli">
			                    <div class="BTSXcliT">值筛选：</div>
			                </div>
			                <div class="BTSXcli2">
			                    <div class="BTSXcli2li">
			                        <input type="checkbox" class="BTSXcli2liI" />
			                        <div class="BTSXcli2liT">空值</div>
			                    </div>
			                </div>
			                <div class="BTSXcli3">
			                    <div class="BTSXcli3BT BTSXcli3BTent" onclick="shaixuan(1)">筛选</div>
			                    <div class="BTSXcli3BT BTSXcli3BTent" onclick="chongzhi()">重置</div>
			                </div>
			            </div>
			        </div>
			        </div>
			        
			</div>
			
		</div>
    </div>
</div>
</body>
<script type="text/javascript"
        src="/wankangyuan/static/js/jquery.min.js"></script>
<script type="text/javascript">

var oPJliBR=document.querySelectorAll('.PJliBR')[0];
var oPJliB2Lt=oPJliBR.querySelectorAll('.PJliB2Lt')[0];

oPJliB2Lt.style.color="rgb(22,87,155)";



var obox=document.querySelectorAll('.box')[0];
obox.style.minHeight=window.screen.availHeight-200+'px';
console.log(document.body.clientWidth);

//全选框和复选框的动作
if(document.querySelectorAll('.quanxuanK')[0]){
    oquanxuanK=document.querySelectorAll('.quanxuanK')[0];
    var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];

    var afuxuan=[];
    var afuxuanK=[];

    if(document.querySelectorAll('.fuxuanK2')[0]){
        afuxuanK=document.querySelectorAll('.fuxuanK2');
        console.log("k2");
    }else if(document.querySelectorAll('.fuxuanK3')[0]){
        afuxuanK=document.querySelectorAll('.fuxuanK3');
        console.log("k3");
    }else if(document.querySelectorAll('.fuxuanK5')[0]){
        afuxuanK=document.querySelectorAll('.fuxuanK5');
        console.log("k3");
    }

    if(afuxuanK[0]){
        for(var i=0;i<afuxuanK.length;i++){
            afuxuan.push(afuxuanK[i].querySelectorAll('.input_check')[0]);
        }
    }
    
    $(oquanxuanK).on("change",function(){
    	if(oquanxuan.checked){
            console.log(1);
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan[i].checked=1;
            }
        }else{
            console.log(2);
            for(var i=0;i<afuxuanK.length;i++){
                afuxuan[i].checked=0;
            }
        }
    })


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
    
}else{
    console.log(222);
}

//格式数据树展开收起
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


//筛选菜单框显示隐藏
var oprodaclmRsx=document.querySelectorAll('.prodaclmRsx')[0];// 获取筛选下拉按钮
var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];// 获取筛选菜单
oshaixuanZK.style.top="30px";
var shaixuanPD=0;


if(oprodaclmRsx){
    oprodaclmRsx.onclick=function(event){
    	oshaixuanZK.className="shaixuanZK active";
    	oBTSX.style.display="none";
        event.stopPropagation();
    }
    oshaixuanZK.onclick=function(event){
        event.stopPropagation();
    }
}

//项目表头和项目分割线宽度
var oPJList=document.querySelectorAll('.PJList')[0];// 项目表头
var oPJListline=document.querySelectorAll('.PJListline')[0];// 分割线

//oPJListline.style.width=oPJList.offsetWidth*0.98+"px";
// console.log(oPJListline.offsetWidth);



//筛选按钮显示隐藏选项
var oshaixuanZK=document.querySelectorAll('.shaixuanZK')[0];// 获取筛选菜单
var ashaixuanZKliI=document.querySelectorAll('.shaixuanZKliI');// 获取所有筛选按钮
var oprodaclmRzT=document.querySelectorAll('.prodaclmRzT')[1];// 项目表头栏
var oPJList=document.querySelectorAll('.PJList')[0];// 项目表头栏
console.log(oPJList);
if(oprodaclmRzT){
    var aprodaclmRzTt2=oprodaclmRzT.querySelectorAll('.prodaclmRzTt2');// 项目表头
}else if(oPJList){
    var aprodaclmRzTt2=oPJList.querySelectorAll('.prodaclmRzTt2');// 项目表头
}
//var oprodaclmRsjK=document.querySelectorAll('.prodaclmRsjK')[0];
//if(oprodaclmRsjK){
//  var oprodaclmRzB=oprodaclmRsjK.querySelectorAll('.prodaclmRzB')[0];// 项目栏
//}
//if(oprodaclmRzB){
//  var aprodaclmRzBz=oprodaclmRzB.querySelectorAll('.prodaclmRzBz');// 每一条项目
//}

var oprodaclmRsjK=document.querySelectorAll('.prodaclmRsjK')[0];
if(oprodaclmRsjK){
    if(oprodaclmRsjK.querySelectorAll('.prodaclmRzB')[0]){
        var oprodaclmRzB=oprodaclmRsjK.querySelectorAll('.prodaclmRzB')[0];// 项目栏
    }else if(oprodaclmRsjK.querySelectorAll('.PJul')[0]){
        var oprodaclmRzB=oprodaclmRsjK.querySelectorAll('.PJul')[0];// 项目栏
    }
}
if(oprodaclmRzB){
    if(oprodaclmRzB.querySelectorAll('.prodaclmRzBz')[0]){
        var aprodaclmRzBz=oprodaclmRzB.querySelectorAll('.prodaclmRzBz');// 每一条项目
    }else if(oprodaclmRzB.querySelectorAll('.PJli')[0]){
        var aprodaclmRzBz=oprodaclmRzB.querySelectorAll('.PJli');// 每一条项目
    }
    
}

console.log(aprodaclmRzBz);

// 初始化筛选按钮判断
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
                if(aprodaclmRzBz){
                    for(var o=0;o<aprodaclmRzBz.length;o++){
                        var aprodaclmRzTt3=aprodaclmRzBz[o].querySelectorAll('.prodaclmRzTt3');// 项目表项
                        aprodaclmRzTt3[j].style.display="block";
                    }
                    
                }
                shaixuanBTPD[j]=1;
            }else{
                ashaixuanZKliI[j].className="shaixuanZKliI";
                aprodaclmRzTt2[j].style.display="none";
                if(aprodaclmRzBz){
                    for(var o=0;o<aprodaclmRzBz.length;o++){
                        var aprodaclmRzTt3=aprodaclmRzBz[o].querySelectorAll('.prodaclmRzTt3');// 项目表项
                        aprodaclmRzTt3[j].style.display="none";
                    }
                    
                }
                shaixuanBTPD[j]=0;
                
            }
            
        }
    })(i)
}

//点击表头的排序筛选功能
var oPJK=document.querySelectorAll('.PJK')[0];// 项目框
if(document.querySelectorAll('.BTSX2')[0]){
    var oBTSX=document.querySelectorAll('.BTSX2')[0];// 项目表头筛选框
}

var oPJList=document.querySelectorAll('.PJList')[0];// 项目表头栏
var aPJListli=oPJList.querySelectorAll('.prodaclmRzTt2');// 项目表头

var BTSXpd=-1;// 项目表头筛选框判断
console.log(aPJListli);
// 点击设置排序筛选框
for(var i=0;i<aPJListli.length;i++){
    (function(j){
        aPJListli[j].onclick=function(){
            oBTSX.style.display="block";
            var BTSXleft=aPJListli[j].offsetLeft;
            oBTSX.name=aPJListli[j].innerHTML;
            console.log(oBTSX.name);
            console.log(BTSXleft);
            if(BTSXleft>1118){
                BTSXleft=1118;
            }
            oBTSX.style.left=BTSXleft-20+'px'; 
            oBTSX.style.top="124px";
            oshaixuanZK.className="shaixuanZK";
            event.stopPropagation();
            
            var oBTSXcliGLK=document.querySelectorAll('.BTSXcliGLK')[0];
            console.log(oBTSXcliGLK);
            if(oBTSXcliGLK){
                oBTSXcliGLK.value="";
            }
        }
    })(i)
}
if(oBTSX){
    oBTSX.onclick=function(){
        event.stopPropagation();
    }
}
window.onclick=function(){
    // console.log(1);
    if(oBTSX){
        oBTSX.style.display="none";
    }
    if(oshaixuanZK){
        oshaixuanZK.className="shaixuanZK";
    }
}
document.onclick=function(){
    // console.log(1);
    
    if(oBTSX){
        oBTSX.style.display="none";
    }
    if(oshaixuanZK){
        oshaixuanZK.className="shaixuanZK";
    }
}


//树状图选中关联
//获取当前页的所属一级和二级
var aPJliB2L=document.querySelectorAll('.PJliB2L');
var xzYIJI=null;
var xzERJI=null;
for(var i=0;i<aPJliB2L.length;i++){
    //console.log(aPJliB2L[i]);
    var oPJliB2Lt=aPJliB2L[i].querySelectorAll('.PJliB2Lt')[0];
    //console.log(oPJliB2Lt.style.color);
    if(oPJliB2Lt.style.color){
        xzERJI=aPJliB2L[i].parentNode;
    }
}
console.log(xzERJI);
var xzERJIfuxuank=xzERJI.querySelectorAll('.fuxuanK4')[0];
xzYIJI=xzERJI.parentNode.parentNode;
//console.log(xzYIJI);

//右侧点击关联
var oprodaclmRsjK=document.querySelectorAll('.prodaclmRsjK')[0];
//console.log(oprodaclmRsjK);
var afuxuanK5=oprodaclmRsjK.querySelectorAll('.fuxuanK5');
//console.log(afuxuanK5);
var oquanxuanK=oprodaclmRsjK.querySelectorAll('.quanxuanK')[0];
//console.log(oquanxuanK);
var erjiPD=null;

//右侧复选的点击
for(var i=0;i<afuxuanK5.length;i++){
    $(afuxuanK5[i]).on("change",function(){
        var erjiPD=false;
        for(var j=0;j<afuxuanK5.length;j++){
            var oinput=afuxuanK5[j].querySelectorAll('input')[0];
            //console.log(oinput.checked);
            if(oinput.checked){
                erjiPD=true;
            }
        }
        if(erjiPD){
            var oxzERJI=xzERJI.querySelectorAll('.input_check')[0];
            //console.log(oxzERJI);
            oxzERJI.checked="checked";
            yijipanduan();
        }else{
            var oxzERJI=xzERJI.querySelectorAll('.input_check')[0];
            //console.log(oxzERJI);
            oxzERJI.checked="";
            yijipanduan();
        }
        
    })
}

//右侧全选的点击
$(oquanxuanK).on("change",function(){
    var erjiPD=false;
    for(var j=0;j<afuxuanK5.length;j++){
        var oinput=afuxuanK5[j].querySelectorAll('input')[0];
        if(oinput.checked){
            erjiPD=true;
        } 
        //console.log(oinput.checked);
    }
    if(erjiPD){
        var oxzERJI=xzERJI.querySelectorAll('.input_check')[0];
        //console.log(oxzERJI);
        oxzERJI.checked="checked";
        yijipanduan();
    }else{
        var oxzERJI=xzERJI.querySelectorAll('.input_check')[0];
        //console.log(oxzERJI);
        oxzERJI.checked="";
        yijipanduan();
    } 
})

//点击右侧时检测左侧二级是否有选中，以此判断一级是否选中
function yijipanduan(){
    var oxzYIJI=xzYIJI.querySelectorAll('.input_check')[0];
    var yijiPD=false;
    for(var i=0;i<aPJliB2L.length;i++){
        //console.log(aPJliB2L[i]);
        var ofuxuanK4=aPJliB2L[i].querySelectorAll('.input_check')[0];
        if(ofuxuanK4.checked){
            yijiPD=true;
        }
    }
    if(yijiPD){
        oxzYIJI.checked="checked";
    }else{
        oxzYIJI.checked="";
    }
}

//左侧二级点击调用,res传复选框
function yijipanduan2(res){
    var erjipanduan=false;
    var yiji=res.parentNode.parentNode.parentNode.parentNode;//左侧一级
    var yijixuanzek=yiji.querySelectorAll('.PJliB1L')[0];
    var yijixuanze=yijixuanzek.querySelectorAll('.input_check')[0];
//  console.log(yijixuanze);
    
    var oerjixuanzek=yiji.querySelectorAll('.PJliBR')[0];
    
    var aerjixuanze=oerjixuanzek.querySelectorAll('.input_check');
    for(var i=0;i<aerjixuanze.length;i++){
        if(aerjixuanze[i].checked){
            erjipanduan=true;
        }
    }
    if(erjipanduan){
        yijixuanze.checked="checked";
    }else{
        yijixuanze.checked="";
    }
    
}

//左侧二级的点击
for(var i=0;i<aPJliB2L.length;i++){
    var oerji=aPJliB2L[i].querySelectorAll('.fuxuanK4')[0];
    $(oerji).on("change",function(res){
        if(res.currentTarget==xzERJIfuxuank){
            var xzERJIfuxuan=xzERJIfuxuank.querySelectorAll('.input_check')[0];
            
            var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];
//          console.log(xzERJIinput);
            
            if(xzERJIfuxuan.checked){
                oquanxuan.checked="checked";
                for(var i=0;i<afuxuanK5.length;i++){
                    var ofuxuan=afuxuanK5[i].querySelectorAll('.input_check')[0];
                    ofuxuan.checked="checked";
                }
            }else{
                oquanxuan.checked="";
                for(var i=0;i<afuxuanK5.length;i++){
                    var ofuxuan=afuxuanK5[i].querySelectorAll('.input_check')[0];
                    ofuxuan.checked="";
                }
            }
        }else{
            
        }
        yijipanduan2(res.currentTarget);
    })
}

//左侧一级的点击
var ayiji=document.querySelectorAll('.PJliB1');
var ayijiL=document.querySelectorAll('.PJliB1L');
console.log(ayijiL);
for(var i=0;i<ayijiL.length;i++){
    (function(index){
        var yijifuxuank=ayijiL[index].querySelectorAll('.fuxuanK4')[0];
        $(yijifuxuank).on("change",function(res){
            console.log(res.currentTarget);
            console.log(index);
            
            var oPJliBR=ayiji[index].querySelectorAll('.PJliBR')[0];//左侧二级框
            var aerjifuxuank=oPJliBR.querySelectorAll('.fuxuanK4');//左侧二级复选框
            var youcePD=false;//判断右侧需不需要全改变
            var aerjifuxuan=oPJliBR.querySelectorAll('.input_check');//二级复选
            var oquanxuan=oquanxuanK.querySelectorAll('.input_check')[0];//右侧全选
            console.log(oquanxuan);
            console.log(afuxuanK5);
            var resinput=res.currentTarget.querySelectorAll('.input_check')[0];
            if(resinput.checked){
                for(var j=0;j<aerjifuxuan.length;j++){
                    aerjifuxuan[j].checked="checked";
                }
            }else{
                for(var j=0;j<aerjifuxuan.length;j++){
                    aerjifuxuan[j].checked="";
                }
            }
            
            for(var j=0;j<aerjifuxuan.length;j++){
                if(aerjifuxuank[j]==xzERJIfuxuank){
                    console.log(aerjifuxuan[j].checked);
                    if(aerjifuxuan[j].checked){
                        youcePD=true;
                    }else{
                        
                    }
                }
            }
            console.log(youcePD);
            if(youcePD){
                oquanxuan.checked="checked";
                for(var i=0;i<afuxuanK5.length;i++){
                    var ofuxuan=afuxuanK5[i].querySelectorAll('.input_check')[0];
                    ofuxuan.checked="checked";
                }
            }else{
                oquanxuan.checked="";
                for(var i=0;i<afuxuanK5.length;i++){
                    var ofuxuan=afuxuanK5[i].querySelectorAll('.input_check')[0];
                    ofuxuan.checked="";
                }
            }
        })
    })(i)
    
}

</script>
</html>