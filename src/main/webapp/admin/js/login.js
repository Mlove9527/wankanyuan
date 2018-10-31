/* 
* @Author: Marte
* @Date:   2018-05-22 16:37:19
* @Last Modified by:   Marte
* @Last Modified time: 2018-05-24 10:40:24
*/

$(document).ready(function(){
    document.querySelectorAll('.container')[0].style.height=document.body.scrollHeight+"px";
    console.log(document.body.scrollHeight);

    var ainput=document.querySelectorAll('input');
    var ainputT=[];
    for(var i=0;i<ainput.length;i++){
        if(ainput[i].type=="text"||ainput[i].type=="password"){
            ainputT.push(ainput[i]);
        }
    }
    var oreset=document.querySelectorAll('.reset')[0];
    oreset.onclick=function(){
        for(var i=0;i<ainputT.length;i++){
            ainputT[i].value="";
        }
    }
});