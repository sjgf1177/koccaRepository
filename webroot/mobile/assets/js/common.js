
/**
 * ���� �м��� ����
 */
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-53999261-1', 'auto');
ga('send', 'pageview');
/**
 * ���� �м��� ����
 */

// modal
(function($){
    $.fn.modal = function(options){
        var defaults = {
            show:false,
            overlayDrop:true
        }
        var options = $.extend(defaults, options);
        var $btnOpen = $('[data-toggle="modal"]');
        var modal = this;
        if(options.overlayDrop == true){
            if ($('body').find('.overlay').length == 0)
            {
                $('body').prepend('<div class="overlay"></div>');
            }
            var overlay = $('.overlay');
        }
        function modalShow(){
            $('body').addClass('modal-open');
            modal.show();
            overlay.show();
        }
        if(options.show == true){
            modalShow();
        }
    }
})(jQuery);
$(function(){
    $('[data-toggle="modal"]').click(function(){
        var target = $(this).attr('data-target');
        if ($('body').find('.overlay').length == 0)
        {
            $('body').prepend('<div class="overlay"></div>');
        }
        $('body').addClass('modal-open');
        $('.overlay').show();
        $(target).show();
        return false;
    });
    $('.modal-close').click(function(){
        $('body').removeClass('modal-open');
        $('.overlay').hide();
        $('.modal').hide();
        return false;
    });
});
// fixed-top
(function($){
    $.fn.fixedTop = function(options){
        var obj = this;
        var windows = $(window);
        var sidebarTop = obj.position().top;
        var sidebarHeight = obj.height();
        var type = "";
        windows.scroll(function(event) {
            scrollTop = windows.scrollTop(),
            topPosition = Math.max(0, sidebarTop - scrollTop),
            topPosition = Math.min(topPosition, scrollTop - sidebarHeight);

            if (scrollTop > sidebarTop)
            {
                obj.addClass('fixed-top');
                obj.css('top', topPosition);
            } else {
                obj.removeClass('fixed-top');
                obj.css({top: 0});
            }
        });
        return this;
    }
})(jQuery);

$(function(){
/*
    if ($('.tabs-fixed-top').length > 0)
    {
        $('.tabs-fixed-top').fixedTop({type:'tabs'});
    }
    if ($('.header-fixed-top').length > 0)
    {
        $('.header-fixed-top').fixedTop({type:'header'});
    }
*/

	var topbannerH = $('#topbanner').height();
    var headerObj = $('#header');
	var headerH = $('#header').height();
	var tabsH = $('.tabs-fixed-top').height();

    var isOpenClassPopular = ( $("#oOpenClassPopular").length > 0);

    // 2014-11-21
    // ����� ���� �� ��ʰ� �߰��Ǿ���.
    // ��ʸ� Ŭ���ϰ� �Ǹ�, ���� �̹� ��ġ�� ��쿡�� ���� �����ϰ�
    // �׷��� ���� ��쿡�� ��ġ �ȳ� ȭ������ �̵��ϴ� ����� ������
    if (topbannerH > 0) {
        if (headerH > 0 && tabsH == null) {
            if (topbannerH > 0)
            {
                $('#header').css('top', topbannerH);
                $('.tabs-fixed-top').css('top',headerH + topbannerH);
            }

            if ( isOpenClassPopular ) {
                $('body').css('padding-top',headerH + topbannerH);
            } else {
                $('body').css('padding-top',headerH + topbannerH + 10);
            }
            // $('#sidenav ul').css('padding-top', headerH + topbannerH + 1);
            $('#sidenav ul').css('padding-top', topbannerH + 1);
            $('#header').addClass('header-shadow');
            $('#faq-list').css('top', headerH + topbannerH);

        } else if (headerH > 0 && tabsH > 0) {

            if (topbannerH > 0) {
                $('#header').css('top',topbannerH);
                $('.tabs-fixed-top').css('top',headerH + topbannerH);
            }
            if ( isOpenClassPopular ) {
                $('body').css('padding-top',headerH + tabsH + topbannerH);
            } else {
                $('body').css('padding-top',headerH + tabsH + topbannerH + 10);
            }
            // $('#sidenav ul').css('padding-top',headerH+topbannerH+1);
            $('#sidenav ul').css('padding-top', topbannerH + 1);
            $('#header').removeClass('header-shadow');
            $('#faq-list').css('top', headerH + topbannerH);
        }

    } else {

        if ( headerObj.length > 0) {
            if (headerH > 0 && tabsH == null) {
                $('body').css('padding-top',headerH + 10);
                //$('#sidenav ul').css('padding-top',headerH+1);
                $('#header').addClass('header-shadow');

            } else if (headerH > 0 && tabsH > 0) {
                // $('body').css('padding-top',headerH+tabsH+10);
                // $('#sidenav ul').css('padding-top',headerH+1);
                $('#header').removeClass('header-shadow');
                $('.tabs-fixed-top').css("top", 43);

                $(".category-detail-header").css("top", 83);
                $(".thumb-list").css("padding-top", 83);
                $(".panel-body > .thumb-list").css("padding-top", 0);
            }
        } else {
            if (tabsH == null) {
                $(".container").css("padding-top", 0);
                $(".category-detail-header").css("top", 0);
                $(".thumb-list").css("padding-top", 0);
            } else {
                $(".container").css("padding-top", 43);
                $(".category-detail-header").css("padding-top", 43);
                // $(".thumb-list").css("top", 43);
            }
        }


    }


    // 2014-11-21
    // ����� �� ���� ��� �ݱ� ��ư Ŭ���� ��ʸ� ����,
    // ��Ű���� �����Ͽ� 1���� ���� ������ ����
	$('.topbanner-close').click(function(){
		$('#topbanner').hide();
        $('#header').css('top',0);
        $('.tabs-fixed-top').css('top',headerH);

        if ( isOpenClassPopular ) {
            $('body').css('padding-top',headerH + tabsH);
        } else {
            $('body').css('padding-top',headerH + tabsH + 10);
        }

        // $('#sidenav ul').css('padding-top',headerH+1);
        $('#sidenav ul').css('padding-top', 1);
        $('#faq-list').css('top', 43);

        fnSetCookie( "isCheckApp", "true", 7, true);
	});

    // 2014-11-21
    // ����� �� ���� ��� Ŭ���� ��ʸ� ����,
    // ��Ű���� �����Ͽ� 1���� ���� ������ ����
    // ���� �̹� ��ġ�� ��� ���� �����ϰ�, �׷��� ���� ��� ��ġ �ȳ��� �̵�
	$('#topbanner .body a').click(function(){
		$('#topbanner').hide();
        $('#header').css('top',0);
        $('.tabs-fixed-top').css('top',headerH);

        if ( isOpenClassPopular ) {
            $('body').css('padding-top',headerH + tabsH);
        } else {
            $('body').css('padding-top',headerH + tabsH + 10);
        }

        // $('#sidenav ul').css('padding-top',headerH+1);
        $('#sidenav ul').css('padding-top', 1);
        $('#faq-list').css('top', 43);

        fnExcuteApp();

        fnSetCookie( "isCheckApp", "true", 7, true);
	});


    // header search
    $('.btn-header-search').click(function(){
        if ($('.input-header-search').is(':hidden'))
        {
            $('.input-header-search').show();
            $('.btn-search-close').show();
            return false;
        } else {
            $(this).attr('type','submit');
        }
    });
    $('.btn-search-close').click(function(){
        $('.input-header-search').hide();
        $('.btn-search-close').hide();
        return false;
    });

    // sidenav
    $('.btn-header-menu').click(function(){
        if ($('#sidenav').hasClass('opened'))
        {
            $('.overlay').hide();
            $('#sidenav').stop().animate({right:-260});
            $('#sidenav').removeClass('opened');

            setTimeout( "fnHideSideNav()", 300);

        } else {
            $('.overlay').show();
            $('#sidenav').show();
            $('#sidenav').stop().animate({right:0});
            $('#sidenav').addClass('opened');
        }
        return false;
    });

    // tabs-auto-width
    $('.tabs-auto-width').each(function(index){
        var tabWidth = $(this).width() / ($(this).find('li').last().index()+1);
        $(this).find('li').width(tabWidth);
    });
    $(window).resize(function(){
        $('.tabs-auto-width').each(function(index){
            var tabWidth = $(this).width() / ($(this).find('li').last().index()+1);
            $(this).find('li').width(tabWidth);
        });
    });

    $('.panel-header a[data-toggle="collapse"]').click(function(){
        var panelBody = $(this).parent().parent().children('.panel-body');
        if (panelBody.is(':hidden')) {
            panelBody.show();
            $(this).find("i").removeClass("icon icon-ar-down").addClass("icon icon-ar-up");
        } else {
            panelBody.hide();
            $(this).find("i").removeClass("icon icon-ar-up").addClass("icon icon-ar-down");
        }
        return false;
    });

    /*
    $('.panel-category .panel-header a').click(function(){
        var lectureCls = $(this).attr("data-value");
        var panelBody = $(this).parent().parent().children('.panel-body');


        if (panelBody.is(':hidden')){
            fnSelectOpenClassCategoryList(lectureCls, 0, panelBody.children("ul"));
        } else {
            panelBody.children(".thumb-list").empty();
            panelBody.hide();
        }


        if (panelBody.length > 0)
        {
            if (panelBody.is(':hidden'))
            {
                if ($(this).children('i').hasClass('icon-ar-down'))
                {
                    $('.panel-category .panel-header a i').removeClass('icon-ar-up').addClass('icon-ar-down');
                    $(this).children('i').removeClass('icon-ar-down').addClass('icon-ar-up');
                }
                // $('.panel-body').hide();
                panelBody.show();
            } else {
                if ($(this).children('i').hasClass('icon-ar-down') || $(this).children('i').hasClass('icon-ar-up'))
                {
                    $('.panel-category .panel-header a i').removeClass('icon-ar-up').addClass('icon-ar-down');
                }
                panelBody.hide();
            }
            return false;
        } else {
            return true;
        }
    });
*/
/*
    $('.panel-category').each(function(){
        var untilEnd = $(this).find('.thumb-list li:eq(3)');
        var $btnMore = "";
        var $btnAll = "";
        if (untilEnd.length < 1) {
            $(this).find('.thumb-list li').show();
        } else {
            untilEnd.prevAll().show();
            $(this).find('.panel-body').append('<a href="#" class="btn-more">������ <i class="icon icon-cir-plus"></i></a>');
            $btnMore = $(this).find('.btn-more');
        }
        if( $btnMore) {
            $btnMore.click(function(){
                $(this).parent().find('.thumb-list li').show();
                $(this).parent().append('<a href="#" class="btn-all">��ü����</a>');
                $(this).remove();
                return false;
            });
        }
    });
*/

    /*
    $('.btn-util-menu').click(function(){
        var offset = $(this).offset();
        if ($(this).hasClass('toggle-in'))
        {
            $('.popover-util-menu').hide();
            $('.btn-util-menu').removeClass('toggle-in');
        } else {
            $('.btn-util-menu').removeClass('toggle-in');
            $(this).addClass('toggle-in');
            $('.popover-util-menu').show();
        }
        $('.popover-util-menu').css({top:offset.top+20,left:offset.left-80});
        return false;
    });
    */
});


/**************************************************
 * ��� �Լ� ����
 *************************************************/

/**
 * url �̵�
 * @param type : app / web ����
 * @param targetUrl : �̵��� url
 * @param retUrl : �ǵ��� �� url
 * @param parameter : �Ķ���� ��
 */
function fnMoveNavi(type, targetUrl, retUrl, modalYn) {

    var paramConnector = ( targetUrl.indexOf("?") > -1) ? "&" : "?";

    if (type === 1) {

        targetUrl = (targetUrl.indexOf("m.edu.kocca.kr") > -1 ) ? targetUrl : "http://m.edu.kocca.kr" + targetUrl;
        targetUrl = ( retUrl == "" || retUrl == null ) ? targetUrl : targetUrl + paramConnector + "retUrl=" + encodeURIComponent(retUrl);

        var animation = (modalYn == "Y") ? "SlideBottomToTop" : "SlideRightToLeft";
        var appUrl = {
            "showView" : {
                "url": targetUrl,
                "animation" : animation
            }
        };

        targetUrl = "newin:@" + JSON.stringify(appUrl);

        document.location = targetUrl;
    } else {
        targetUrl = ( retUrl == "" || retUrl == null ) ? targetUrl : targetUrl + paramConnector + "retUrl=" + encodeURIComponent(retUrl);
        
        location.href = targetUrl;
    }
}

function naviPopup() {
    var j = {
        "hideView" : {
            "animation" : "SlideLeftToRight",
            "reload" : "yes"
        }
    };
}

/**
 * SNS�� �����ϱ� ���̾� �˾� ����
 */
function fnOpenSharePopup() {
    $(".overlay").show();
    $("#oSNSShareModal").modal({show:true});
    // $(".modal").show;
}

function fnCloseSharePopup() {
    $(".overlay").hide();
    $("#oSNSShareModal").hide();
    $(".modal").hide();
}

/**
 * SNS�� �����ϱ�
 */
function fnSendSns(sns, url, txt) {
    var o;
    var _url = encodeURIComponent(url);
    var _txt = encodeURIComponent(txt);
    var _br  = encodeURIComponent('\r\n');

    switch(sns)
    {
        case 'facebook':
            o = {
                method:'popup',
                url:'http://www.facebook.com/sharer/sharer.php?u=' + _url
            };
            break;

        case 'twitter':
            o = {
                method:'popup',
                url:'http://twitter.com/intent/tweet?text=' + _txt + '&url=' + _url
            };
            break;

        case 'me2day':
            o = {
                method:'popup',
                url:'http://me2day.net/posts/new?new_post[body]=' + _txt + _br + _url + '&new_post[tags]=epiloum'
            };
            break;

        case 'kakaotalk':
            o = {
                method:'web2app',
                param:'sendurl?msg=' + _txt + '&url=' + _url + '&type=link&apiver=2.0.1&appver=2.0&appid=dev.epiloum.net&appname=' + encodeURIComponent('�ѱ���������ī����'),
                a_store:'itms-apps://itunes.apple.com/app/id362057947?mt=8',
                g_store:'market://details?id=com.kakao.talk',
                a_proto:'kakaolink://',
                g_proto:'scheme=kakaolink;package=com.kakao.talk'
            };
            break;

        case 'kakaostory':
            o = {
                method:'web2app',
                param:'posting?post=' + _txt + _br + _url + '&apiver=1.0&appver=2.0&appid=dev.epiloum.net&appname=' + encodeURIComponent('�ѱ���������ī����'),
                a_store:'itms-apps://itunes.apple.com/app/id486244601?mt=8',
                g_store:'market://details?id=com.kakao.story',
                a_proto:'storylink://',
                g_proto:'scheme=kakaolink;package=com.kakao.story'
            };
            break;

        case 'band':
            o = {
                method:'web2app',
                param:'create/post?text=' + _txt + _br + _url,
                a_store:'itms-apps://itunes.apple.com/app/id542613198?mt=8',
                g_store:'market://details?id=com.nhn.android.band',
                a_proto:'bandapp://',
                g_proto:'scheme=bandapp;package=com.nhn.android.band'
            };
            break;

        default:
            alert('�������� �ʴ� SNS�Դϴ�.');
            return false;
    }

    alert(o.param);
    switch(o.method) {
        case 'popup':
            if(navigator.userAgent.match(/koccaapplication/i)) {
                fnShowBrowser( o.url );
            } else {
                window.open(o.url);
            }
            break;

        case 'web2app':
            if(navigator.userAgent.match(/android/i)) {
                // Android
                setTimeout(function() { location.href = 'intent://' + o.param + '#Intent;' + o.g_proto + ';end'}, 100);

            } else if(navigator.userAgent.match(/(iphone)|(ipod)|(ipad)/i)) {
                // Apple
                setTimeout(function(){ location.href = o.a_store; }, 200);
                setTimeout(function(){ location.href = o.a_proto + o.param }, 100);

            } else if(navigator.userAgent.match(/koccaapplication/i)) {
                // kocca app
                // setTimeout(function(){ location.href = o.a_proto + o.param }, 100);
                setTimeout(function(){ fnShowBrowser(o.a_store ) }, 200);
                setTimeout(function(){ fnShowBrowser( o.a_proto + o.param ) }, 100);

            } else {
                alert('�� ����� ����Ͽ����� ����� �� �ֽ��ϴ�.');
            }
            break;
    }

    fnCloseSharePopup();
}

function fnShowBrowser( url ) {
    if(navigator.userAgent == "KoccaApplication") {
        var j = {
            "showView":{
            "url": url,
            "animation":"Browser"
        }
    }
        document.location = "newin:@" + JSON.stringify(j);
    } else {
        document.location = url;
    }
}

/**
 * ����� ������ �޴��� ����.
 * �޴� �ִϸ��̼� ���� �ð��� ���� �� setTimeout �Լ��� �̿��Ͽ� ȣ��ȴ�.
 * �ִϸ��̼��� ���� �� �޴��� ���� ������ �޴��� ������ ���� ��
 * ��Ű�� �̿��Ͽ� �̵��� �� �޴��� �ǳʶٱ� �����̴�.
 */
function fnHideSideNav() {
    $('#sidenav').hide();
}


/**
 *
 */
function fnLogout( type ) {
    fnMoveNavi(type, "/mobile/jsp/user/logout.jsp", "");

    // fnMoveNavi(2, "/servlet/controller.mobile.user.LoginServlet?process=logoutProc", "");
}

/**
 * �α��� ���� ����
 */
function fnCheckLoginStatus() {
    var isLogin = false;
    $.ajax({

            type : "get"
        ,   url : "/mobile/jsp/common/checkLoginStatus.jsp"
        ,   dataType : "json"
        ,   success : function (data) {
                isLogin = eval(data.isLogin);
            }
        ,   complete : function(arg1, arg2) {
                // alert("complete : " + arg1);
            }
        ,   error :  function(arg1, arg2) {
                // alert("error : " + arg1);
            }
    });

    return isLogin;
}


/**
 * �޴� Ŭ���� ������ �̵�
 * �α��� �ʿ� ����(isNeedLogin)�� true�̸� �α��� ���� Ȯ�� ���Ŀ� ����
 */
function fnMoveMenu(type, url, isNeedLogin) {
    if ( isNeedLogin ) {

        $.ajax({

                type : "get"
            ,   url : "/mobile/jsp/common/checkLoginStatus.jsp"
            ,   dataType : "json"
            ,   success : function (data) {
                    isLogin = data.isLogin;

                    if ( isLogin ) {
                        fnMoveNavi(type, url, "");
                    } else {
                        alert("�ش� �޴��� ����Ϸ��� �α����� �ؾ� �մϴ�.");
                        fnMoveNavi(type, "/servlet/controller.mobile.user.LoginServlet?process=loginPage", url);
                    }
                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }

        });

    } else {
        fnMoveNavi(type, url, "");
    }
}

/**
 * ��Ű ���� �����Ѵ�.
 */
function fnSetCookie( name, value, expiredays, escapeYn ) {
    var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );

    /*if ( escapeYn ) {
	    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
    } else {
	    document.cookie = name + "=" + value + "; path=/; expires=" + todayDate.toGMTString() + ";"
    }*/
}


/**
 * ��Ű�� ����� ���� �����´�.
 */
function fnGetCookie( cookieName, escapeYn ) {
    var flag = document.cookie.indexOf(cookieName + '=');
    var retValue = "";

	if (flag != -1) {
        flag += cookieName.length + 1;
		end = document.cookie.indexOf(';', flag);

		if (end == -1) {
            end = document.cookie.length;
        }

        if ( escapeYn ) {
            retValue = unescape( document.cookie.substring(flag, end) );
        } else {
            retValue = document.cookie.substring(flag, end) ;
        }

        return retValue;
    }
}

/**
 * ����� ���� �����Ѵ�.
 * ��ġ�Ǿ� ���� ���� ��� ��ġ �ȳ� ȭ������ �̵��Ѵ�.
 */
function fnExcuteApp() {
    var openAt = new Date,
        uagentLow = navigator.userAgent.toLocaleLowerCase(),
        chrome25,
        kitkatWebview;

    $("body").append("<iframe id='____koccalink____'></iframe>");
    // $('#applinkDiv').html("<iframe id='____koccalink____'></iframe>");
    $("#____koccalink____").hide();

    setTimeout( function() {

        if (new Date - openAt < 1500) {
            if (uagentLow.search("android") > -1) {

                $("#____koccalink____").attr("src","https://play.google.com/store/apps/details?id=com.newin.kocca");

                // $("#____koccalink____").attr("src","market://details?id=com.newin.kocca");
            } else if (uagentLow.search("iphone") > -1) {
                location.replace("https://itunes.apple.com/us/app/kocca/id492151283?l=ko&ls=1&mt=8");
            }
        }
    }, 1000);

    if(uagentLow.search("android") > -1){

        chrome25 = uagentLow.search("chrome") > -1 && navigator.appVersion.match(/Chrome\/\d+.\d+/)[0].split("/")[1] > 25;
        kitkatWebview = uagentLow.indexOf("naver") != -1 || uagentLow.indexOf("daum") != -1;

        if (chrome25 && !kitkatWebview){

            document.location.href = "intent://applink?param=value#Intent;scheme=kocca-app;package=com.newin.kocca;end";
        } else{

            $("#____koccalink____").attr("src", 'kocca-app://');
        }

    } else if(uagentLow.search("iphone") || uagentLow.search("ipad") > -1){
        $("#____koccalink____").attr("src", 'kocca-app://?');
    }
}
