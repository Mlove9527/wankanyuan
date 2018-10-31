/* 
* @Author: Marte
* @Date:   2018-05-24 10:44:02
* @Last Modified by:   Marte
* @Last Modified time: 2018-05-24 14:14:23
*/

$(document).ready(function(){
    var ohuanying=document.querySelectorAll('.huanying')[0];
    var ocontent=document.querySelectorAll('#content')[0];
    console.log(ocontent.offsetHeight);

    ohuanying.style.height=ocontent.offsetHeight+"px";
    ohuanying.style.lineHeight=ocontent.offsetHeight+"px";
});