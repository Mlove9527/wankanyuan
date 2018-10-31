$(function(){

    //获取id
    var shopId = GetQueryString('shopId');

    //$('#target').dargFlex('drag');

    $.ajax({
        type:"post",
        url:path + "Goods/getGoodCateList",
        async:true,
        data:{
            "admin_user": username,
            'token': token,
            'mct_id': shopId


        },
        success:function(res) {
            /* 对返回数据进行处理 */
            console.log(res);
            var data = res.data;
            $.each(data,function(i,item){
                var html = '<li class="drag"  id="'+item.gc_id+'" >\n' +
                    '<span>'+item.gc_name+'</span>\n' +
                    '</li>'
                $("#target").append(html);



            });
            // /* 商品排序 */

            $('#target').dargFlex('drag');

        },
        err:function(){
            alert('请求失败，请检查网络设置')
        }
    });


    
    //商品分类排顺序
    $(".submit").click(function(){

        //    //获取id
        var shopId = GetQueryString('shopId');
        var dragArr = $('#target .drag');
        var dragIdArr = [];
        for (var i = 0; i < dragArr.length; i++) {
        	dragIdArr.push(dragArr[i].id);
        }
        var idSort = '[' + dragIdArr + ']';
        $.ajax({
        	type:"post",
        	url: path + "Goods/sortGoodsCate",
        	async:true,
        	data:{
        		/*
        		 * mobile		是	string	用户名
        		 * token		是	string	商户token
        		 * usertype		是	string	用户类型 （1 用户 2 商户 3 配送员）
        		 * sort_rule	是	string	商品分类排序规则 数字为gc_id
        		 */
               "admin_user": username,
               'token': token,
               'mct_id': shopId,
        		'sort_rule':idSort
        	},
        	success:function(res){
        		if(res.error_code == '0000'){
        			alert('排序成功');
        		} else {
        			checkCode(res.error_code,res.message);
        		}
        	},
        	error:function(){
        		alert('排序失败，请检查网络设置');
        	}
        });
    })
    
});
