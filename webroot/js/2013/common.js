$(function(){
	//셀렉트박스 스타일 변경
	$("#mainMenu .search select,#myInfoMng").selectbox({
		className: "selbox", //클래스명
		animationSpeedDown: 0, //펼쳐질때 스피드
		animationSpeedUp: 0, //접힐때 스피드
		listboxMaxSize: 10, //스크롤바 없이 나오는 option수
		replaceInvisible: false,
		direction: "down", //방향 위로는("up")
		lineHeight: 20, //option 하나당 line-height값
		addWidth: 0 //기본넓이값에서 더해지는 넓이값. 이미지가 들어가면 그 넓이값이 자체적으로 계산이 안되므로 이미지 넓이값 이상 넣어준다
	});
	
	//2015-01-07 본문 셀렉트박스 스타일 변경
	$("#contents .search select,#myInfoMng").selectbox({
		className: "selbox", //클래스명
		animationSpeedDown: 0, //펼쳐질때 스피드
		animationSpeedUp: 0, //접힐때 스피드
		listboxMaxSize: 10, //스크롤바 없이 나오는 option수
		replaceInvisible: false,
		direction: "down", //방향 위로는("up")
		lineHeight: 20, //option 하나당 line-height값
		addWidth: 0 //기본넓이값에서 더해지는 넓이값. 이미지가 들어가면 그 넓이값이 자체적으로 계산이 안되므로 이미지 넓이값 이상 넣어준다
	});
	
	//메뉴, 컨텐츠 바로가기
	$("#shortcutmenu a").focus(function(){
		$(this).addClass("focus");
	})
	.blur(function(){
		$(this).removeClass("focus");
	});
	
	//확대 축소
	zoomInOut.init();
	$("#header .zoom a").eq(0).click(function(e){
		e.preventDefault();
		zoomInOut.plus();
	});
	
	$("#header .zoom a").eq(1).click(function(e){
		e.preventDefault();
		zoomInOut.minus();
	});
	
	//id pw 입력 필드 기본 문구
	$("#header").find(".id, .pw").ipFocus();
	
	//패밀리사이트 마우스 나가면 사라짐
	$("#footer .familysite").mouseleave(function(){
		if($(this).find("dd:first").is(":visible")){
			showsite();
		}
	});
});

//서브 메뉴
$(function(){
	/* css */
	if($(".introCategory").length){
		$("#mainMenu").addClass("fullmenu");
	}
		
	if($("#mainMenu .unfold").length){
		//메뉴 띄우기
		var el_mainmenu = $("#mainMenu .location")
		,el_sub = $("#mainMenu .location ul")
		,timer;
		$("#mainMenu .unfold").each(function(i){
			var opt = {
				btns       : "#mainMenu .unfold:eq("+i+")" //레이어 띄우기 버튼
				,layer     : "#mainMenu .location ul:eq("+i+")" //레이어
				,closeBtn  : ".closeBtn" //레이어 안쪽 닫기 버튼
				,outCloseBtn  : ".lyrClose" //레이어 바깥쪽 닫기 버튼
				,evt       : "click" //이벤트
				,distTop   : 0 //더해질 상단 여백
				,distLeft  : 0 //더해질 왼쪽 여백
				,autoPosition  : false //자동으로 중앙 위치 맞춤 true || false
				,evtPosition  : false //마우스 위치로 맞춤 true || false
				,winResize : false //윈도우 크기 바뀌면 위치 재설정 true || false
				,blurClose : false //초첨 나갈때 닫히기 true || false
				,toggle : true //버튼을 클릭하면 켜졌다꺼졌다로 true || false
				,fn        : function(){
					el_sub.hide();
				} //이벤트 발생시 실행될 함수
			}
			,mainmenu = layerUI(opt);
		});
		el_mainmenu.mouseenter(function(){
			clearTimeout(timer);
		});
		el_mainmenu.mouseleave(function(){
			timer = setTimeout(function(){
				el_sub.hide();
			},300);
		});
	}
});

//상단 탭메뉴
$(function(){
	var el_edumenu = $("#quickEduMenu")
	, el_nothing = $("____")
	,timer
	,quickEdu = el_edumenu.tabUI({
		tab : ".tab a, .viewAll a" //탭
		,con : ".cont, .totalmenu" //컨텐츠
		,tabSrcOn : "_on" //이미지 replace src on (a_ov.gif)
		,tabSrcOff : "" //이미지 replace src off (a.gif)
		,tabHover : "on" //탭 오버시 클래스
		,eventHandler : "click" //이벤트핸들러
		,imgHover : true //이미지 온오프 true | false
		,href : false //click 이벤트일때 href 실행 true | false
		,fn : function(i,el,opt){
			$('.mainbnr').cycle('pause');
			if(el.currTab.hasClass("active")){
				opt.imgHover = false;
			}else{
				opt.imgHover = true;
			}
		} //탭 활성화될 때 실행할 함수
		,titAttr : false //탭 활성화될 때 title 값 끝에 추가할 문구
		,firstNum : 99
	});
	el_edumenu.mouseenter(function(){
		clearTimeout(timer);
	});
	var el_mainbnr = $(".mainContent .mainbnr .group");
	el_edumenu.mouseleave(function(){
		timer = setTimeout(function(){
			el_mainbnr.show();
			quickEdu.openTab(el_nothing,99);
            try {
                if(!flag_mainbnr_pause){
                    $('.mainbnr').cycle('resume');
                }
            } catch (e) {
            }
		},300);
	});
	el_edumenu.find(".closeBtn").click(function(e){
		e.preventDefault();
		quickEdu.openTab(el_nothing,99);
        $(".viewAll > a").focus();
	});
	
	el_edumenu.find(".active img").imgSwap("","_on");
	
	imgRollover(["#quickEduMenu .tab a, .viewAll a","#quickEduMenu .cont li a"],{hold:"on",hold2:"active"});
	imgRollover(["#sideMenu .eduSup a"]);
});

function showsite() {
	for(i=1; i<=100; i++){
		var sl = document.getElementById("url"+i);
		if(sl) {
			if (sl.style.display == "none" || sl.style.display == "") {
				sl.style.display = "block";
			} else {
				sl.style.display = "none";
			}
		}
	}
}

//보기 페이지 답변 접기
var ansfolding = function(){
	$(".answer, .reply1 .total").each(function(){
		var lyr = $(this).next();
		$(this).find("a").toggle(function(){
			lyr.hide();
		},function(){
			lyr.show();
		});
	});
}

function getInternetExplorerVersion() {
	var rv = -1;
	if (navigator.appName == 'Microsoft Internet Explorer') {
		var ua = navigator.userAgent;
		var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		if (re.exec(ua) != null)
		rv = parseFloat(RegExp.$1);
	}
	return rv;
}

//image src replace
jQuery.fn.imgSwap = function(src_in,src_out){
	if(typeof src_out != "string") src_out="_on";
	$(this).each(function(){
		var that=$(this);
		var imgSrc=that.attr("src");
		if(imgSrc==undefined) return;
		var imgType=imgSrc.match(/\.gif$|\.jpg$|\.png$/i);
		if(imgSrc.indexOf(src_in+imgType) == -1 || src_out != "" && imgSrc.indexOf(src_out+imgType) != -1) return;
		that.attr("src",imgSrc.replace(src_in+imgType,src_out+imgType));
	});
	return this;
}

function imgRollover(selector,options){
	var opt = {
		srcOn  : "_on" //img src replace on 기본값 "on"
		, srcOff : "" //img src replace off 기본값 ""
		, hold   : "ignoreRollover" //롤오버 적용 안시킬 클래스명 - 해당 이미지 태그에 있어야 함
		, hold2   : "ignoreRollover" //롤오버 적용 안시킬 클래스명 - 해당 이미지 태그에 있어야 함
	}
	opt = $.extend(opt, options || {});
	function setBind(el){
		var img = el;
		if(el.parents("a").length){
			el = el.parents("a");
			img = el.find("img");
		}
		if(el.hasClass(opt.hold) || img.hasClass(opt.hold) || el.hasClass(opt.hold2) || img.hasClass(opt.hold2)) return;
		el.bind("mouseenter focus",function(){
			if(img.hasClass("rollover_on") || el.hasClass(opt.hold)) return;
			img
			.addClass("rollover_on")
			.imgSwap(opt.srcOff,opt.srcOn);
		})
		el.bind("mouseleave blur",function(){
			if(el.hasClass(opt.hold)) return;
			img
			.removeClass("rollover_on")
			.imgSwap(opt.srcOn,opt.srcOff);
		});
	}
	for(var s in selector){
		o = $(selector[s]);
		if(o.is("[src]")){
			o.each(function(){
				setBind($(this));
			});
		}else{
			o.find("[src]").each(function(){
				setBind($(this));
			});
		}
	}
}

//화면 확대/축소
var zoomInOut = (function(){
	var ratio = 100, o, bgsize;
	function init(){
		o = document.getElementById("wrap");
		bg = document.getElementsByTagName("BODY")[0];
		bgsize = 4; //bg 이미지 넓이
	}
	function plus()  {
		if (ratio<300){
		  ratio += 10;
		  o.style.zoom = ratio+'%';
		  bg.style.backgroundSize = bgsize*ratio/100 +'px';
		}
	}
	//글자 축소
	function minus()  {
		if (ratio>100){
		  ratio -= 10;
		  o.style.zoom = ratio+'%';
		  bg.style.backgroundSize = bgsize*ratio/100 +'px';
		}
	}
	return {
		init : init
		,plus : plus
		,minus : minus
	}
})();

//탭
jQuery.fn.tabUI = function(options){
	var opt = {
		tab : ".tabUI_tab" //탭
		,con : ".tabUI_con" //컨텐츠
		,tabSrcOn : "_ov" //이미지 replace src on (a_ov.gif)
		,tabSrcOff : "" //이미지 replace src off (a.gif)
		,tabHover : "on" //탭 오버시 클래스
		,eventHandler : "mouseenter focus" //이벤트핸들러
		,imgHover : true //이미지 온오프 true | false
		,href : false //click 이벤트일때 href 실행 true | false
		,fn : "" //탭 활성화될 때 실행할 함수
		,titAttr : "선택됨" //탭 활성화될 때 title 값 끝에 추가할 문구
		,firstNum : 0 
	};
	var opt = $.extend(opt, options || {});
	var output = {};
	function init(wrap){
		var
		el = {
			tab : wrap.find(opt.tab)
			,con : wrap.find(opt.con)
			,currTab : null
		}
		,att = {
			title : []
		}
		,prevIdx = 1
		,currTit = "";
		output.openTab = function(o,i){
			el.currTab = el.tab.filter("."+opt.tabHover);
			if(opt.fn != ""){
				opt.fn(i,el,opt);
			}
			if(opt.imgHover){
				el.currTab.find("img").imgSwap(opt.tabSrcOn,opt.tabSrcOff);
				if(o.find("img").length){
					o.find("img").imgSwap(opt.tabSrcOff,opt.tabSrcOn);
				}
			}
			if(att.title[prevIdx] === ""){
				el.currTab.removeAttr("title");
			}else{
				el.currTab.attr("title",att.title[prevIdx]);
			}
			if(!att.title[i] && opt.titAttr){
				currTit = opt.titAttr;
			}else if(opt.titAttr){
				currTit = att.title[i] + " - " + opt.titAttr;
			}
			o.attr("title",currTit);
			el.tab.removeClass(opt.tabHover);
			if(o.length){
				o.addClass(opt.tabHover);
			}
			el.con.hide();
			el.con.eq(i).show();
			prevIdx = i;
			if(!opt.href){
				return false;
			}
		}
		el.tab.each(function(i){
			att.title.push($(this).attr("title"));
			$(this).bind(opt.eventHandler,function(e){
				if(/click/.test(opt.eventHandler)){
					e.preventDefault();
				}
				output.openTab($(this),i)
			});
		});

		el.tab.eq(opt.firstNum).trigger(opt.eventHandler.split(" ")[0]);
	}
	$(this).each(function(){
		init($(this));
	});
	return output;
}

function layerUI(options){
	var ly,el_base,timer,focusBtn;
	var opt = {
		btns       : ".lyrOpen" //레이어 띄우기 버튼
		,layer     : ".layer" //레이어
		,closeBtn  : ".closeBtn" //레이어 안쪽 닫기 버튼
		,outCloseBtn  : ".lyrClose" //레이어 바깥쪽 닫기 버튼
		,evt       : "click" //이벤트
		,distTop   : 0 //더해질 상단 여백
		,distLeft  : 0 //더해질 왼쪽 여백
		,autoPosition  : true //자동으로 중앙 위치 맞춤 true || false
		,evtPosition  : false //마우스 위치로 맞춤 true || false
		,winResize : false //윈도우 크기 바뀌면 위치 재설정 true || false
		,blurClose : false //초첨 나갈때 닫히기 true || false
		,toggle : false //버튼을 클릭하면 켜졌다꺼졌다로 true || false
		,fn        : null //이벤트 발생시 실행될 함수
		,fn_close        : null //닫힐때 실행될 함수
	}
	for(s in options){opt[s] = options[s];}
	ly = $(opt.layer);
	if(!ly.length) return;
	el_base = ly.parent();
	while(!/relative|absolute/.test(el_base.css("position"))){
		if(/BODY|HTML/.test(el_base[0].tagName)) break;
		el_base = el_base.parent();
	}
	var setPos = function(){
		var top, left;
		top = $(window).height() < ly.height() ? "0" : ($(window).height() - ly.height()) / 2;
		top += $(window).scrollTop() + opt.distTop;
		top = top > 0 ? top : 0;
		left = $(window).scrollLeft() + ((el_base.width() - ly.width()) /2) + opt.distLeft;
		ly.css({
			top: top + "px",
			left: left + "px"
		});
	}
	var setEvtPos = function(e){
		var top,left,top_limit,left_limit;
		top = $(window).scrollTop() + e.pageY + opt.distTop;
		left = $(window).scrollLeft() + e.pageX + opt.distLeft;
		top_limit = $(window).scrollTop() + el_base.height() - ly.height() - 20;
		left_limit = $(window).scrollLeft() + el_base.width() - ly.width() - 20;
		if(left_limit < left && left_limit > 0){
			left = left_limit;
		}
		if(top_limit < top){
			if(top_limit > 0){
				top = top_limit;
			}else{
				top = 0;
			}
		}
		ly.css({
			top: top + "px",
			left: left + "px"
		});
	}
	$(opt.btns).each(function(i){
		$(this).bind(opt.evt,function(e){
			clearTimeout(timer);
			focusBtn = $(this);
			if(opt.toggle && ly.is(":hidden") || !opt.toggle || !/click|mousedown/.test(opt.evt)){
				if(typeof opt.fn === "function"){
					opt.fn(opt,i,e);
				}
				if(opt.autoPosition){
					setPos();
				}else if(opt.evtPosition){
					setEvtPos(e);
				}
				ly.show()
				if(/click/.test(opt.evt)){
					ly.find(">*:first").attr("tabIndex",-1).focus();
					return false;
				}
			}else{
				lyrClose();
			}
		});
	});
	if(/mouseover|mouseenter/.test(opt.evt)){
		$(opt.btns).mouseleave(function(){
			timer = setTimeout(function(){
				ly.hide();
			},300);
		});
		ly.mouseover(function(){
			clearTimeout(timer);
		})
		.mouseleave(function(){
			ly.hide();
		});
	}
	if(opt.winResize){
		$(window).resize(setPos);
	}
	var lyrClose = function(e){
		ly.hide();
		if(focusBtn){
			focusBtn.focus();
		}
		if(typeof opt.fn_close === "function"){
			opt.fn_close(opt,e);
		}
		return false;
	}
	ly.find(opt.closeBtn).click(lyrClose);;
	$(opt.outCloseBtn).click(lyrClose);;
	if(opt.blurClose){
		$(opt.btns).blur(function(){
			ly.hide();
		});
	}
}

jQuery.fn.textPlaceholder = function(att) {
	$(this).each(function(){
		var that = this;

		var placeholder = that.getAttribute(att || 'defval');
		if(placeholder === null) return;
		var input = jQuery(that);
		if (that.value === '' || that.value == placeholder) {
			input.addClass('text-placeholder');
			that.value = placeholder;
		}

		input.focus(function(){
			if (input.hasClass('text-placeholder')) {
				this.value = '';
				input.removeClass('text-placeholder')
			}
		});

		input.blur(function(){
			if (this.value === '') {
				input.addClass('text-placeholder');
				this.value = placeholder;
			} else {
				input.removeClass('text-placeholder');
			}
		});

		that.form && jQuery(that.form).submit(function(){
			if (input.hasClass('text-placeholder')) {
				that.value = '';
			}
		});
	});
}

jQuery.fn.ipFocus = function(){
	$(this).each(function(){
		if($(this).val() != ""){
			$(this).css({backgroundPosition : "-999px -999px"});
		}
		$(this).focus(function(){
			$(this).css({backgroundPosition : "-999px -999px"});
		});
		$(this).blur(function(){
			if($(this).val() == ""){
				$(this).css({backgroundPosition : "0 0"});
			}
		});
	});
}

function currGuideUI(){
	var all_uf = $(".all_unfold")
	,all_f = $(".all_fold")
	,btn_f = $(".broadWrap2").find(".btn_folder")
	,tit = $(".broadWrap2").find(".title")
	,con = $(".bdSec,.bdnewSec")
    ,img = ""; 
	btn_f.each(function(i){
		var ly = con.eq(i);
		img = btn_f.eq(i).find("img");
		var img2 = tit.eq(i).find("img");
		$(this).click(function(e){
			e.preventDefault();
			if(ly.hasClass("active")){
				ly.removeClass("active");
				img.imgSwap("_on","");
				img2.imgSwap("_on","");
				img.attr("alt","펼침");
			}else{
				ly.addClass("active");
				img.imgSwap("","_on");
				img2.imgSwap("","_on");
				img.attr("alt","닫기");
			}
		});
	});
	all_f.click(function(e){
		e.preventDefault();
		con.removeClass("active");
		btn_f.find("img").imgSwap("_on","");
		tit.find("img").imgSwap("_on","");
		img.attr("alt","펼침");
	});
	all_uf.click(function(e){
		e.preventDefault();
		con.addClass("active");
		btn_f.find("img").imgSwap("","_on");
		tit.find("img").imgSwap("","_on");
		img.attr("alt","닫기");
	});
}

	/**
	 * 관련 사이트로 이동한다.
	 */
	function fnMoveFamilySite(url) {
		var docForm = document.familySiteform;
		docForm.action = url;
		docForm.submit();
	}



    function fnCheckInvalidChar( inStr ) {
        inStr = inStr.replace(/\</g,"");
        inStr = inStr.replace(/\>/g,"");
        inStr = inStr.replace(/\"/g,"");
        inStr = inStr.replace(/\'/g,"");
        inStr = inStr.replace(/\%/g,"");
        inStr = inStr.replace(/\;/g,"");
        inStr = inStr.replace(/\(/g,"");
        inStr = inStr.replace(/\)/g,"");
        inStr = inStr.replace(/\&/g,"");
        inStr = inStr.replace(/\+/g,"");
        return inStr;
    }