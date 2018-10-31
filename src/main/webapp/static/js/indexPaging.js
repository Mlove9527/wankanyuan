(function($, window, document) {
    // 定义构造函数
    function Paging(el, options) {
        this.el = el;
        this.options = {
            pageNo: options.initPageNo || 1, // 初始页码
            totalPages: options.totalPages || 1, //总页数
            totalCount: options.totalCount || '', // 条目总数
            slideSpeed: options.slideSpeed || 0, // 缓动速度
            jump: options.jump || false, // 支持跳转
            callback: options.callback || function() {} // 回调函数
        };
        this.init();
    }
    // 给实例对象添加公共属性和方法
    Paging.prototype = {
        constructor: Paging,
        init: function() {
            this.createDom();
            this.bindEvents();
        },
        createDom: function() {
            var that = this,
                ulDom = '',
                jumpDom = '',
                content = '',
                liWidth = 60, // li的宽度
                totalPages = that.options.totalPages, // 总页数
                wrapLength = 0;
            totalPages > 5 ? wrapLength = 5 * liWidth : wrapLength = totalPages * liWidth;
            for (var i = 1; i <= that.options.totalPages; i++) {
                i != 1 ? ulDom += '<li class="pageNUM ">' + i + '</li>' : ulDom += '<li class="pageNUM active">' + i + '</li>';
            }
            that.options.jump ? jumpDom = '<input type="text" placeholder="1" class="jump-text" id="jumpText"><button type="button" class="jump-button" id="jumpBtn">跳转</button>' : jumpDom = '';
            content = 
            	'<button class="pageLR" id="prePage"><img src="/wankangyuan/static/img/pageL.png" class="pageLRi" alt="" /></button>' +
            	//'<div  style="height: 40px; float: left; overflow: hidden;width:' + wrapLength + 'px">' +
                '<ul id="pageSelect" style="transition:all; display:inline;' + that.options.slideSpeed + 'ms">' +
                ulDom +
                '</ul>' +
                //'</div>' +
                '<button class="pageLR" id="nextPage"><img src="/wankangyuan/static/img/pageR.png" class="pageLRi" alt="" /></button>' ;
            that.el.html(content);
        },
        bindEvents: function() {
            var that = this,
                pageSelect = $('#pageSelect'), // ul
                lis = pageSelect.children(), // li的集合
                liWidth = lis[0].offsetWidth, // li的宽度
                totalPages = that.options.totalPages, // 总页数
                pageIndex = that.options.pageNo, // 当前选择的页码
                distance = 0, // ul移动距离
                prePage = $('#prePage'),
                nextPage = $('#nextPage'),
                firstPage = $('#firstPage'),
                lastPage = $('#lastPage'),
                jumpBtn = $('#jumpBtn'),
                jumpText = $('#jumpText');

            prePage.on('click', function() {
                pageIndex--;
                if (pageIndex < 1) pageIndex = 1;
                handles(pageIndex);
            })

            nextPage.on('click', function() {
                pageIndex++;
                if (pageIndex > lis.length) pageIndex = lis.length;
                handles(pageIndex);
            })

            firstPage.on('click', function() {
                pageIndex = 1;
                handles(pageIndex);
            })

            lastPage.on('click', function() {
                pageIndex = totalPages;
                handles(pageIndex);
            })

            jumpBtn.on('click', function() {
                var jumpNum = parseInt(jumpText.val().replace(/\D/g, ''));
                if (jumpNum && jumpNum >= 1 && jumpNum <= totalPages) {
                    pageIndex = jumpNum;
                    handles(pageIndex);
                    jumpText.val(jumpNum);
                }
            })

            lis.on('click', function() {
                pageIndex = $(this).index() + 1;
                handles(pageIndex);
            })

            function handles(pageIndex) {
                lis.removeClass('active').eq(pageIndex - 1).addClass('active');
                
                if(totalPages > 10) {
                	lis.eq(10-1).nextAll().hide();
                }
                
                if(totalPages > 10 && pageIndex >= 5) {
                	lis.eq(pageIndex-6).nextUntil(lis.eq(pageIndex+5)).show();
                	lis.eq(pageIndex-5).prevAll().hide();
                }
                
                if(totalPages > 10 && pageIndex >= 5 && pageIndex >= totalPages - 5){
                	lis.eq(totalPages-11).nextAll().show();
                }
                
                if (totalPages <= 5) {
                    that.options.callback(pageIndex);
                    return false;
                }
                if (pageIndex >= 3 && pageIndex <= totalPages - 2) {
                	distance = (pageIndex - 3) * liWidth;
                	
                }
                if (pageIndex == 2 || pageIndex == 1) distance = 0;
                if (pageIndex > totalPages - 2) distance = (totalPages - 5) * liWidth;
                pageSelect.css('transform', 'translateX(' + (-distance) + 'px)');
                pageIndex == 1 ? firstPage.attr('disabled', true) : firstPage.attr('disabled', false);
                pageIndex == 1 ? prePage.attr('disabled', true) : prePage.attr('disabled', false);
                pageIndex == totalPages ? lastPage.attr('disabled', true) : lastPage.attr('disabled', false);
                pageIndex == totalPages ? nextPage.attr('disabled', true) : nextPage.attr('disabled', false);
                that.options.callback(pageIndex);
            }

            handles(that.options.pageNo); // 初始化页码位置
        }
    }
    $.fn.paging = function(options) {
        return new Paging($(this), options);
    }
})(jQuery, window, document);