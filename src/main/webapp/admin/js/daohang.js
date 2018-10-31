

$("#sidebar-left").load("../daohang.html");



$("#sidebar-left").on("click",".dropmenu",function(e){
    e.preventDefault();

    $(this).parent().find('ul').slideToggle();

})
// $('.dropmenu').click(function(e){
//
//     e.preventDefault();
//
//     $(this).parent().find('ul').slideToggle();
//
// });