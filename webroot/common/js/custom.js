$(function(){
    //����� ������ üũ
    var isMobileSize = false;
    function checkMobileSize() {
	    var x = $(window).width();
	    if (x >= 992) {
	        return false;
	    } else {
	        return true;
	    }
	}
	
	function getScrollbarWidth() {
	    var outer = document.createElement("div");
	    outer.style.visibility = "hidden";
	    outer.style.width = "100px";
	    outer.style.msOverflowStyle = "scrollbar"; // needed for WinJS apps

	    document.body.appendChild(outer);

	    var widthNoScroll = outer.offsetWidth;
	    // force scrollbars
	    outer.style.overflow = "scroll";

	    // add innerdiv
	    var inner = document.createElement("div");
	    inner.style.width = "100%";
	    outer.appendChild(inner);

	    var widthWithScroll = inner.offsetWidth;

	    // remove divs
	    outer.parentNode.removeChild(outer);

	    return widthNoScroll - widthWithScroll;
    }
    isMobileSize = checkMobileSize();
    
    
    //������ â������ ����
    $(window).resize(function(){
		isMobileSize = checkMobileSize();
        if(!isMobileSize){
            if($(".gnb_con").hasClass("active")){
                $(".gnb_con > ul > li > ul").css({"display":"block"});
                var subMenuLength = 0;
                $(".gnb_con > ul > li").each(function(){
                    subMenuLength = subMenuLength < $(this).find("li").length ? $(this).find("li").length : subMenuLength;
                });
                $(".header_after").height((subMenuLength + 1) * 30);
            }else{
                $(".gnb_icon").css({"float":""}); 
            }
            $("section").css({"position":""});
            $(".gnb_con > ul > li").css({"background":""});
        }else{
            if($(".gnb_con").hasClass("active")){
                $(".header_after").height(0);
                $("#search_label , .seperate_line").css({"display":"none"});
                $(".gnb_icon").css({"float":"right"}); 
                $(".gnb_con > ul > li > ul").css({"display":"none"});
                $(".gnb_con > ul > li").each(function(){
                    if($(this).find("ul").length){
                        $(this).css({"background-image":"url(/common/image/gnb_1depth_mobile_arrow_modify.png)"});
                    }
                });
                $("section").css({"position":"fixed"});
            }else if($(".gnb_util_icon").hasClass("active")){
                $(".gnb_util_icon").removeClass("active");
                $(".gnb_menu").css({"display":"inline-block"});
                $(".gnb_close").css({"display":"none"});
            }else{
                $(".gnb_con").css({"display":""});
                $(".gnb_icon").css({"float":"right"});
            }
        }
    });
    
    
    //�˻���ư
    $("#search_label").on("click",function(){
        if(!isMobileSize){
            $(".gnb_util_icon").addClass("active");
            $(".gnb_con").css({"display":"none"});
            $(".gnb_menu").css({"display":"none"});
            $(".gnb_close").css({"display":"inline-block"});
        }
    });
    
    //gnb�޴� ���콺 ȣ��
    $(".gnb_con > ul > li").mouseover(function(){
        if(!isMobileSize){
            if(!$(".gnb_con").hasClass("active")){
                if($(this).find("ul").length){
                    $(".header_after").stop().animate({"height":"20px"},200);
                    $(this).find("ul").show();
                }
            }
        }
    });
    $(".gnb_con > ul > li").mouseleave(function(){
        if(!isMobileSize){
            if(!$(".gnb_con").hasClass("active")){
                $(".header_after").stop().animate({"height":"0"},200);
                $(this).find("ul").hide();
            }
        }
    });
    
    //gnb ��ü�޴�
    $(".gnb_menu").on("click",function(){
        if(!isMobileSize){
            $("#search_label , .seperate_line").css({"display":"none"}); //�˻� ������
            $(".gnb_icon").css({"float":"right"}); //������ ��ġ ����
            $(".gnb_con > ul > li > ul").css({"display":"block"});
            //header ���� �ø���
            var subMenuLength = 0;
            $(".gnb_con > ul > li").each(function(){
                subMenuLength = subMenuLength < $(this).find("li").length ? $(this).find("li").length : subMenuLength;
            });
            $(".header_after").height((subMenuLength + 1) * 30);
        }else{
            $(".gnb_con > ul > li").each(function(){
                if($(this).find("ul").length){
                    $(this).css({"background-image":"url(/common/image/gnb_1depth_mobile_arrow_modify.png)"});
                }
            });
            $("section").css({"position":"fixed"});
        }
        
        $(".gnb_menu").css({"display":"none"}); //gnb ���� ������ ������
        $(".gnb_close").css({"display":"inline-block"}); //gnb �ݱ� ������ ���̱�
        $(".gnb_con").addClass("active");
    });
    
    //gnb �� �˻� �ݱ�
    $(".gnb_close").on("click",function(){
        if(!isMobileSize){
            if($(".gnb_util_icon").hasClass("active")){
                $(".gnb_util_icon").removeClass("active");   
                $(".gnb_con").css({"display":"inline-block"});
            }else if($(".gnb_con").hasClass("active")){
                $("#search_label , .seperate_line").css({"display":""});
                $(".gnb_con").removeClass("active");
                $(".gnb_icon").css({"float":"none"});
                $(".gnb_con > ul > li > ul").css({"display":""});
                $(".header_after").css({"height":"0"});
            }   
        }else{
            $(".gnb_con").removeClass("active");
            $("section").css({"position":""});
        }
        $(".gnb_menu").css({"display":"inline-block"});
        $(".gnb_close").css({"display":"none"});
    });
    
    //����Ͽ��� liŬ���� ����޴�
    $(".gnb_con > ul > li").on("click", function(){
        if(isMobileSize){
            if($(this).find("ul").css("display") == "none"){
                $(this).find("ul").slideDown("fast");
                if($(this).index() != 2){
                    $(this).css({"background-image":"url(/common/image/gnb_1depth_mobile_arrow_on_modify.png)"});
                }
                $(this).siblings("li").find("ul").slideUp("fast");
                $(this).siblings("li:not(:nth-child(3))").css({"background-image":"url(/common/image/gnb_1depth_mobile_arrow_modify.png)"});
            }else{
                $(this).find("ul").slideUp("fast");
                if($(this).index() != 2){
                    $(this).css({"background-image":"url(/common/image/gnb_1depth_mobile_arrow_modify.png)"});
                }
            }
        }
    });
    
    //faq
    $( '.faq_con').each( function(index, container){
        $(container).find( '.faq_tit').each( function( i, li ){
            $(li).attr( "_idx", i ).click( function( event ){
                var $_li = $( event.currentTarget );
                var _index = parseInt( $_li.attr( "_idx" ) );
                $( $( $_li.parentsUntil( ".faq_con")).parent().find( ".faq_tit" )).each( function( j, p ){
                    var el = $( $(p).parent().find( ".faq_reply") );
                    if( _index == j ){
                        if( $(p).hasClass( "action" ) ){
                            $(p).removeClass( "action" );
                            el.animate({height: 0}, 300);
                        }else{
                            $(p).addClass( "action" );
                            var curHeight = el.height();
                            var autoHeight = el.css('height', 'auto').height();
                            el.height(curHeight).animate({height: autoHeight}, 300);
                        }
                    }else{
                        $(p).removeClass( "action" );
                        el.animate({height: 0}, 300);
                    }
                });
            });
        });
    });
    
    //���� �����̵� ���
    if($("#main_banner_slide").length > 0){
    	swiper0 = new Swiper('#main_banner_slide', {
    		pagination: '.swiper-pagination'
    			,paginationType: 'fraction'
    				,loop : true
    				,autoplay: 4000
    				,paginationClickable: true
    				,direction: 'horizontal' // �����̵� ��������� ����(vertical�ϸ� �������� ������)
    					,slidesPerView: 1 // �ѹ��� ���̴� �����̵� ����
    					,spaceBetween: 0 // �����̵� ������ ���� px ����
    					,mousewheelControl: true // ���콺 �ٷ� �����̵� ������
    					,autoplayDisableOnInteraction: false
    					,nextButton: '.swiper-button-next' //������ư
    					,prevButton: '.swiper-button-prev' //������ư
    	});
    }
    
	$(document).on("click",".swiper-autoplay-start",function(){
		swiper0.startAutoplay();
		$(this).removeClass("swiper-autoplay-start").addClass("swiper-autoplay-stop");
	});
	$(document).on("click",".swiper-autoplay-stop",function(){
		swiper0.stopAutoplay();
		$(this).removeClass("swiper-autoplay-stop").addClass("swiper-autoplay-start");
	});
	
	if($(".lnb_wrap").length > 0){
		$(window).scroll(function(){
	        var scrollHeight = $(document).scrollTop();
	        if(scrollHeight >= 145){
	            if(document.body.clientHeight < $(".lnb_wrap").height() + 310){
	               if( scrollHeight > ($(document).height() - document.body.clientHeight - 240) ){
	                    $(".lnb_wrap").css({"top":"auto","bottom": 310 - ($(document).height() - document.body.clientHeight - scrollHeight) });  
	               }else{
	                    $(".lnb_wrap").css({"top":"50px","bottom":"auto"});  
	               }
	            }else{
	                $(".lnb_wrap").css({"top":"50px","bottom":"auto"});
	            }
	        }else{
	            $(".lnb_wrap").css({"top": 195 - scrollHeight +"px","bottom":"auto"});   
	        }
	    });
	}


    //맨 위로 이동 Top 선언
    function TopmoveEvent(){
        // 맨 위로 이동 버튼 생성
        $('section').append('<button type="button" class="topmove bi bi-arrow-up-circle-fill" title="맨 위로 이동"></button>')

        //스크롤 시 버튼 보여짐,숨김
        $( window ).scroll( function() {
            if ( $( this ).scrollTop() > 200 ) {
                $( '.topmove' ).fadeIn();
            } else {
                $( '.topmove' ).fadeOut();
            }
        } );

        //버튼 클릭시 맨 위로 이동
        $( '.topmove' ).click( function() {
            $( 'html, body' ).animate( { scrollTop : 0 }, 400 );
            return false;
        } );
    };

    TopmoveEvent(); // 맨 위로 이동 버튼 이벤트

    //파일 업로드

        var $fileBox = null;

        $(function() {
            init();
        })

        function init() {
            $fileBox = $('.input-file');
            fileLoad();
        }

        function fileLoad() {
            $.each($fileBox, function(idx){
                var $this = $fileBox.eq(idx),
                    $btnUpload = $this.find('[type="file"]'),
                    $label = $this.find('.file-label');

                $btnUpload.on('change', function() {
                    var $target = $(this),
                        fileName = $target.val(),
                        $fileText = $target.siblings('.file-name');
                    $fileText.val(fileName);
                })

                $btnUpload.on('focusin focusout', function(e) {
                    e.type == 'focusin' ?
                        $label.addClass('file-focus') : $label.removeClass('file-focus');
                })

            })
        }


});