$(function(){
	if ($('#mainBannerLg li').length > 1) {
		$('#mainBannerLg').bxSlider( {
			auto:true,
            autoHover:true,
//			pager:false,
			controls:false,
			pagerCustom: '#mainBanner'
		});
	}

	if ($('#mainBanner li').length > 3) {
		mbSlider = $('#mainBanner').bxSlider({
            auto:false,
            // autoHover:true,
			// pager:false,
            controls:true,
			minSlides:3,
			maxSlides:3,
			moveSlides:1,
			slideWidth:214
		});
	}

/*
	$('.main-banner li a').mouseover(function(){
		var href = $(this).attr('href');
		var img = $(this).find('img').attr('rel');
		$('.main-banner .output').html('<a href="'+href+'"><img src="'+img+'" alt=""></a>');
		return false;
	});
	$('.main-banner li:first-child a').trigger('mouseover');
*/
	$('.lt-tab').each(function(){
		var index = $(this).find('.header .active').index();
		var tabContent = $(this).find('.body');
		tabContent.eq(index).show();
	});

	$('.lt-tab .header a').click(function(){
        if ( $(this).hasClass("more") ) {
            var index = $(".lt-tab .header ul").find(".active").index();
            var url = "";

            if ( index == 1 ) {
                // 공지사항 목록으로 이동
                url = "/servlet/controller.homepage.HomeNoticeServlet?p_process=List";

            } else if ( index == 2 ) {
                // 이벤트 목록으로 이동
                url = "/servlet/controller.homepage.EventHomePageServlet?p_process=selectList";
                // alert("서비스 준비중입니다.");
                // return;
            } else {
                return;
            }

            if ( url != "" ) {
                location.href = url;
            }

        } else {
            var index = $(this).parent().index();
			if ( index == 1 || index == 2) {
				$(".lt-tab .header .more").show();
			} else {
				$(".lt-tab .header .more").hide();
			}
            var tabContent = $(this).parent().parent().parent().parent().find('.body');
            $(this).parent().parent().find('li').removeClass('active');
            $(this).parent().addClass('active');
            tabContent.hide();
            tabContent.eq(index).show();
            return false;
        }
	});

	$(".ro").hover(
		function() { this.src = this.src.replace("-off","-on"); },
		function() { this.src = this.src.replace("-on","-off"); }
	);

	$('.lt-wz-group .header a').click(function(){
		var index = $(this).parent().index();
		var tabContent = $(this).parent().parent().parent().parent().find('.body');
		$(this).parent().parent().find('li').removeClass('active');
		$(this).parent().addClass('active');
		tabContent.hide();
		tabContent.eq(index).show();
		return false;
	});

	/*
    $(".mainSearchInput").focus(function(srcc) {
        if ($(this).val() == $(this)[0].title) {
            $(this).removeClass("mainSearchInputActive");
            $(this).val("");
        }
    });

    $(".mainSearchInput").blur(function() {
        if ($(this).val() == "") {
            $(this).addClass("mainSearchInputActive");
            $(this).val($(this)[0].title);
        }
    });
        
    $(".mainSearchInput").blur();
	*/

});

/**
 * 홈페이지에 노출된 인기 검색어를 클릭하면 해당 검색어를 입력창에 붙여넣고
 * 과정 검색을 시작한다.
 */
function fnSearchSubjByKeyword( searchWord )  {
    $("#topWarp_searchText").val( searchWord );
    totalSubjSearch1();
}

function fnViewBoardDetail( boardType, seq ) {
    var url = "";
    if ( boardType == "NOTICE" ) {
        url = "/servlet/controller.homepage.HomeNoticeServlet?p_process=selectView&p_seq=" + seq;
    } else {
        // alert("서비스 준비중입니다.");
        url = "/servlet/controller.homepage.EventHomePageServlet?p_process=selectView&p_seq=" + seq;
        return;
    }

    location.href = url;
}