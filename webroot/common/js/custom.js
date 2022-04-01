$(function(){
    //모바일 사이즈 체크
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
    
    
    //윈도우 창사이즈 변경
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
    
    
    //검색버튼
    $("#search_label").on("click",function(){
        if(!isMobileSize){
            $(".gnb_util_icon").addClass("active");
            $(".gnb_con").css({"display":"none"});
            $(".gnb_menu").css({"display":"none"});
            $(".gnb_close").css({"display":"inline-block"});
        }
    });
    
    //gnb메뉴 마우스 호버
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
    
    //gnb 전체메뉴
    $(".gnb_menu").on("click",function(){
        if(!isMobileSize){
            $("#search_label , .seperate_line").css({"display":"none"}); //검색 가리기
            $(".gnb_icon").css({"float":"right"}); //아이콘 위치 조정
            $(".gnb_con > ul > li > ul").css({"display":"block"});
            //header 높이 늘리기
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
        
        $(".gnb_menu").css({"display":"none"}); //gnb 열기 아이콘 가리기
        $(".gnb_close").css({"display":"inline-block"}); //gnb 닫기 아이콘 보이기
        $(".gnb_con").addClass("active");
    });
    
    //gnb 및 검색 닫기
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
    
    //모바일에서 li클릭시 서브메뉴
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
    
    //메인 슬라이드 배너
    if($("#main_banner_slide").length > 0){
    	swiper0 = new Swiper('#main_banner_slide', {
    		pagination: '.swiper-pagination'
    			,paginationType: 'fraction'
    				,loop : true
    				,autoplay: 4000
    				,paginationClickable: true
    				,direction: 'horizontal' // 슬라이드 진행방향은 수평(vertical하면 수직으로 움직임)
    					,slidesPerView: 1 // 한번에 보이는 슬라이드 갯수
    					,spaceBetween: 0 // 슬라이드 사이의 간격 px 단위
    					,mousewheelControl: true // 마우스 휠로 슬라이드 움직임
    					,autoplayDisableOnInteraction: false
    					,nextButton: '.swiper-button-next' //다음버튼
    					,prevButton: '.swiper-button-prev' //이전버튼
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

});