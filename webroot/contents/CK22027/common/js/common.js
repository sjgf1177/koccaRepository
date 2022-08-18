/* ----------------------------------------------------------------------------------------------------------------------------------*/
var contentsMode = "local";/*var contentsMode = "porting";*/
// var contentsMode = "porting";
var portingMode = false; //true는 진도제어, false는 진도제어해제
var isBrowse = mobileCheck();
var nScale=1;
var agent = navigator.userAgent.toLowerCase();
var isIE = ieBrowser();
var _href = location.href.split("/");
var chapter = parseInt(_href[_href.length-2],10);
//var chapterTitle = getChapter();
// var pageTitle = "["+courseTitle+"] "+transNum(chapter)+". "+chapterTitle;
var pageTitle = "["+courseTitle+"]";
var docu_name = _href[_href.length-1].replace(".html","");
var now_page = parseInt(docu_name,10);
var total_page = Object.keys(dataInfo).length;
var cur_flow = dataInfo[(now_page-1)].flow;
var cur_media = dataInfo[(now_page-1)].media;
var cur_app = dataInfo[(now_page-1)].app;
var cur_sync = dataInfo[(now_page-1)].sync;
var isPlaying = false;
var app_complete = false;
var media_start;
var media_controll;
var playerGoto;
var mediaPATH;
var loadMediaURL;
var subject = new Object();
subject = []
var contents_width = 1280;
var contents_height = 772;
var fullscreenCheck = "none";
var skillPoint = new Object();
skillPoint = []
var chasi = contentsName;
// var quarter = location.href.split('/')[(location.href.split('/').length-3)];
var quarter = now_page;
var learnedChk = false;
var learnChk = getCookie("learnChk"+chasi+quarter);
/* ----------------------------------------------------------------------------------------------------------------------------------*/

function transNum(n){
	return(n<10?("0"+n):(n));
}

String.prototype.replaceAll = function(org, dest){
    return this.split(org).join(dest);
}

String.prototype.capitalize = function(org, dest){
    return this.charAt(0).toUpperCase()+this.slice(1);
}

function mobileCheck(){
	try{document.createEvent("TouchEvent");return "MOBILE";}catch(e){return "PC";}
}

var effectSnd = new Audio();
effectSnd.autoplay = true;
function effect_sound_fn(_type){
	effectSnd.src = "./common/mp3/"+_type+".mp3";
	effectSnd.load(function(){
		effectSnd.play();
	});
}

var completeSnd = new Audio();
completeSnd.autoplay = true;
function complete_sound_fn(){
	completeSnd.src = "./common/mp3/balloon.mp3";
	completeSnd.load(function(){
		completeSnd.play();
	});
}

function ieBrowser(){
	if((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1)){
		return "IE";
	}else{
		return "none";
	}
}

function getChapter(){
	var _title = courseInfo[courseType][chapter];
	_title = _title.replace('|',' ');
	_title = _title.replace('{','');
	_title = _title.replace('}','');
	return _title;
}

$(document).ready(function(){
	$("html > head > title").text(pageTitle);
	initLoading();
	layoutSet();
	if(window.addEventListener){
		window.addEventListener("message",onMessage,false);        
	}else if(window.attachEvent){
		window.attachEvent("onmessage",onMessage,false);
	}
	buildHeader();
	if(contentsMode != "capture") buildBalloon();
	if(chapter == 99) buildTest();
	if(cur_app != "none"){
		var script = document.createElement('script');
		script.src = "./common/js/build"+cur_app.capitalize()+".js"
		script.type = 'text/javascript';
		script.language = 'javascript';
		var done = false;
		script.onload = script.onreadystatechange = function(){
			if (!done && (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete')){
				done = true;
				// Handle memory leak in IE
				script.onload = script.onreadystatechange = null;
				script.parentNode.removeChild(script);
			}
		}
		document.getElementsByTagName("head")[0].appendChild(script);
	}

	if(cur_media == "mp4"){
		if(contentsMode == "porting"){
			mediaPATH = cdnUrl;
		}else{
			if(contentsMode == "capture"){
				mediaPATH = captureUrl;
			}else{
				mediaPATH = localUrl;
			}
		}
		loadMediaURL =mediaPATH+docu_name+".mp4";
		var full_obj = '<button class="fullscreen-close-btn fullscreen-close-btn-'+isBrowse.toLowerCase()+'"><div class="fullscreen-close-icon-'+isBrowse.toLowerCase()+'"></div></button>';
		$("#mediaUI").append(full_obj);
		$(".fullscreen-close-btn").hide();

		$(".fullscreen-close-btn").on({
			mouseover : function(){
				$(this).css({"transform":"rotate(180deg)","transition":"all ease 0.5s"}); 
				$(".fullscreen-close-icon").css({"background-color":"#FDA947"});
			},
			mouseout : function(){
				$(this).css({"transform":"rotate(0deg)","transition":"all ease 0.5s"});
				$(".fullscreen-close-icon").css({"background-color":"rgba(0,0,0,0.5)"});
			},
			click : function(){
				toggle_fullScreen();
			},
		});
	}else{
		loadMediaURL = "./common/mp3/"+cur_app+"."+cur_media;
	}
	mobileBtnSet();
	$("#learningVideo").videoPlayer();
	console.log(
		"pageTitle : "+pageTitle+"\n"+
		"isIE : "+isIE+"\n"+
		"contentsMode : "+contentsMode+"\n"+
		"course_total : "+course_total+"\n"+
		"chapter : "+chapter+"\n"+
		"now_page : "+now_page+"\n"+
		"total_page : "+total_page+"\n"+
		"cur_flow : "+cur_flow+"\n"+
		"cur_app : "+cur_app+"\n"+
		"cur_sync : "+cur_sync+"\n"+
		"loadMediaURL : "+loadMediaURL+"\n"+
		"quarter : "+quarter+"\n"+
		"chasi : "+chasi+"\n"+
		"------------------------------"
	);

	checkCookie();
});

function onMessage(event){
    var data = event.data;
    if(typeof(window[data.func]) == "function"){
		window[data.func].call(null, data.message);
    }
}

function initLoading(){
	var html = '';
	html+= '<div id="loading-center">';
	html+= '<div id="loading-center-absolute">';
	html+= '<div class="object" id="object_four"></div>';
	html+= '<div class="object" id="object_three"></div>';
	html+= '<div class="object" id="object_two"></div>';
	html+= '<div class="object" id="object_one"></div>';
	html+= '</div>';
	html+= '</div>';
	$("#loadingUI").html(html);
}

function layoutSet(){
	if(isBrowse != "PC"){
		mobileSet();
	}
	if(contentsMode == "capture") captureSet();
}

function captureSet(){
	$("#wrap").css({"height":"782px"});
}

function mobileSet(){
	var nScale=1;
	$(window).resize(function(){resizeScale();});
	function resizeScale(){
		nScale = 1;
		if(window.innerWidth > window.innerHeight){
			nScale = window.innerHeight / contents_height;
			if(nScale > 1){
				nScale = 1;
			}
		}else{
			nScale = window.innerWidth / contents_width;
			if(nScale > 1){
				nScale = 1;
			}
		}
		$("body").css({ transformOrigin: "50% 50%", transform: "scale("+nScale+")" });
	}
	resizeScale();
}

function toggle_fullScreen(){
	if(cur_app != "none"){
		fullscreenCheck = "fullscreen";
		alert("전체화면을 지원하지 않습니다.");
	}else{
		fullscreenCheck == "none"
		if(fullscreenCheck == "none"){
			fullscreenCheck = "fullscreen";
			$("#fullscreen").css({"background-position-y":"-"+$("#fullscreen").height()+"px"});
			$('#mediaUI').css({"position":"absolute","top":"0px","left":"0px","width":"100%","height":"100%"});
			$('#learningVideo')[0].setAttribute('controls','');
			$(".fullscreen-close-btn").show();
			openFullScreen();
		}else{
			fullscreenCheck = "none";
			$("#fullscreen").css({"background-position-y":"0px"});
			$('#mediaUI').css({"position":"absolute","top":"0px","left":"0px","width":contents_width+"px","height":(contents_height-$("#navigationUI").height())+"px"});
			$('#learningVideo')[0].removeAttribute('controls');
			$(".fullscreen-close-btn").hide();
			closeFullScreen();
		}
	}
}

function openFullScreen(){
	var divObj = $('#mediaUI')[0];
	if(divObj.requestFullscreen){
		divObj.requestFullscreen();
	}else if(divObj.mozRequestFullScreen){
		divObj.mozRequestFullScreen();
	}else if(divObj.webkitRequestFullscreen){
		divObj.webkitRequestFullscreen();
	}else if(divObj.msRequestFullscreen){
		divObj.msRequestFullscreen();
	}
}

function closeFullScreen(){
	if(document.exitFullscreen){
		document.exitFullscreen();
	}else if(document.mozCancelFullScreen){
		document.mozCancelFullScreen();
	}else if(document.webkitExitFullscreen){
		document.webkitExitFullscreen();
	}else if(document.msExitFullscreen){
		document.msExitFullscreen();
	}
}

// function buildHeader(){
// 	var html = '';
// 	html += '<div class="chapter-wrap">';
// 	html += '<div class="chapter-bullet">!</div>';
// 	var _title = courseInfo[courseType][chapter];
// 	_title = _title.replaceAll('|','<br/>');
// 	_title = _title.replaceAll('{','<span class="chapter-impact">');
// 	_title = _title.replaceAll('}','</span>');
// 	html += '<div class="chapter-title">'+_title+'</div>';
// 	html += '</div>';
// 	// html += '<div class="course-title" id="course-'+courseType+'"></div>';
// 	if(now_page > 1 && now_page < total_page && cur_flow != "lesson") $("#headerUI").html(html);
// 	if(contentsMode == "capture"){
// 		var _capture = '';
// 		_capture += '<div class="capture">';
// 		_capture += '<div class="capture-wrap">';
// 		_capture += '<span class="capture-guide-impact">PC환경</span>에서 지원되는 페이지 입니다.';
// 		_capture += '</div>';
// 		_capture += '</div>';
// 		if(cur_app != "none") $("#headerUI").append(_capture);
// 	}
// }
function buildHeader(){
	var html = '';
	html += '<div class="chapter-wrap">';
	var _title = courseInfo[courseType][0];
	_title = _title.replaceAll('|','<br/>');
	_title = _title.replaceAll('{','<span class="chapter-impact">');
	_title = _title.replaceAll('}','</span>');
	html += '<div class="chapter-title">'+'</div>';
	html += '<div class="course-title">'+'</div>';
	html += '</div>';
	// html += '<div class="course-title" id="course-'+courseType+'"></div>';
	if(now_page == 1){
		$("#headerUI").html("");
	}else{
		$("#headerUI").html(html);
	}
	// if(curFlow == "review") $("#headerUI").html(html);
	if(contentsMode == "capture"){
		var _capture = '';
		_capture += '<div class="capture">';
		_capture += '<div class="capture-wrap">';
		_capture += '<span class="capture-guide-impact">PC환경</span>에서 지원되는 페이지 입니다.';
		_capture += '</div>';
		_capture += '</div>';
		if(cur_app != "none") $("#headerUI").append(_capture);
	}
}

function buildBalloon(){
	if(now_page < total_page){
		$("#balloonUI").html('<button class="balloon balloon-next"></button>');
		$(".balloon").on({
			click : function(){
				prev_next("next");
			}
		});
	}else{
		$("#balloonUI").html('<div class="balloon balloon-end"></div>');
	}
}

// function buildSlipsheet(){
// 	var sub = subject[(now_page-1)];
// 	sub = sub.split("&");
// 	var subNum = parseInt(sub[0],10);
// 	var subTitle = sub[1];
// 	subTitle = subTitle.replaceAll('|','<br/>');
// 	subTitle = subTitle.replaceAll('{','<span class="slipsheet-impact">');
// 	subTitle = subTitle.replaceAll('}','</span>');
// 	var html = '';
// 	html += '<button class="contents-stage" id="slipsheet">';
// 	html += '<div class="slipsheet-wrap">';
// 	html += '<div class="slipsheet-sub-num"><div class="slipsheet-sub-num-txt">'+transNum(subNum)+'</div></div>';
// 	html += '<div class="slipsheet-sub-txt">'+subTitle+'</div>';
// 	var guide = "화면을 ()해 학습을 시작하세요.";
// 	if(isBrowse == "PC"){
// 		guide = guide.replaceAll('()','<span class="slipsheet-guide-impact">클릭</span>');
// 	}else{
// 		guide = guide.replaceAll('()','<span class="slipsheet-guide-impact">터치</span>');
// 	}
// 	html += '<div class="slipsheet-guide-wrap">';
// 	html += '<div class="slipsheet-guide-txt">'+guide+'</div>';
// 	html += '</div>';
// 	html += '</div>';
// 	html += '</button>';
// 	$("#contentUI").html(html);
// 	$("#slipsheet").on({
// 		click : function(){
// 			$(this).fadeOut(function(){
// 				media_controll();
// 			});
// 		}
// 	});
// }
function buildSlipsheet(){
	var sub = courseInfo["type0"];
	var subTitle = sub[now_page-1];
	var subNum = parseInt(courseInfo["type0"]);
	var html = '';
	html += '<button class="contents-stage" id="slipsheet">';
	html += '<div class="slipsheet-wrap">';
	// html += '<div class="slipsheet-sub-num"><div class="slipsheet-sub-num-txt">'+subNum+'</div></div>';
	html += '<div class="slipsheet-sub-txt">'+subTitle+'</div>';
	var guide = "화면을 ()해 학습을 시작하세요.";
	if(isBrowse == "PC"){
		guide = guide.replaceAll('()','<span class="slipsheet-guide-impact">클릭</span>');
	}else{
		guide = guide.replaceAll('()','<span class="slipsheet-guide-impact">터치</span>');
	}
	html += '<div class="slipsheet-guide-wrap">';
	html += '<div class="slipsheet-guide-txt">'+guide+'</div>';
	html += '</div>';
	html += '</div>';
	html += '</button>';
	$("#contentUI").html(html);
	$("#slipsheet").on({
		click : function(){
			$(this).fadeOut(function(){
				media_controll();
			});
		}
	});
}

function play_conf(){
	$("#slipsheet").fadeOut();
}

function prev_next(_dir){
	var pageUrl;
	switch(_dir){
		case 'prev':
			if(now_page == 1){
				chapter_move(_dir);
			}else{
				pageUrl = transNum(now_page-1)+".html";
				document.location.href = pageUrl;
			}
			break;
		case 'next':
			if(now_page == total_page){
				chapter_move(_dir);
			}else{
				pageUrl = transNum(now_page+1)+".html";
				document.location.href = pageUrl;
			}
			break;
	}
}

function movePage(n){
	var pageUrl = transNum(n)+".html";
	document.location.href = pageUrl;
}

function balloon_toggle(_state){
	switch(_state){
		case 'show':
			if(isPlaying == false){
				$("#balloonUI").fadeIn();
				complete_sound_fn();
			}
			break;
		case 'hide':
			$("#balloonUI").fadeOut();
			break;
	}
}

function mobileBtnSet(){
	if(isBrowse != "PC") $("button").off("mouseover").off("mouseout");
}

function buildTest(){
	console.log("역량진단 테스트 빌드업!!!!");
	var html = '';
	html += '<div class="skill-test">';
	html += '<div class="skill-test-stage">';
	html += '<div class="skill-test-wrap" id="skill-test-start">';
	html += '<div class="skill-test-guide1">';
	html += '본 과정에 앞서 진행되는 진단은|{학습자의 역량 수준을 확인}하고|{자신의 직무역량에 맞는 보완학습을 위한 진단}입니다.'
	html += '</div>';
	html += '<div style="width:100%;height:40px;"></div>';
	html += '<div class="skill-test-guide2">';
	html += '총 (b)'+skillInfo.length+'(/b)문항으로 구성된 역량진단은 학습자 수준을 초급/중급/고급으로 구분하여|(b)학습자 수준에 맞는 보완학습자료를 제공(/b)하고 있습니다.'
	html += '</div>';
	html += '<div style="width:100%;height:100px;"></div>';
	html += '<button class="skill-test-btn" id="skill-test-btn-start">';
	html += '<div class="skill-test-btn-name" id="skill-test-btn-name-start">역량진단 바로가기</div>';
	html += '<div class="skill-test-btn-icon" id="skill-test-btn-icon-start"></div>';
	html += '</button>';
	html += '</div>';
	html += '<div class="skill-test-wrap" id="skill-test-quiz">';
	html += '<div class="skill-test-title"><div class="skill-test-title-txt">직무역량진단</div></div>';
	html += '<div class="skill-test-guide3">직무역량진단은 과정수료 결과에 반영되지 않습니다.</div>';
	html += '<div style="width:100%;height:40px;"></div>';
	html += '<div class="skill-test-quiz-select-header-wrap">';
	html += '<div class="skill-test-quiz-select-header-title-wrap">';
	html += '<div class="skill-test-quiz-question"></div>';
	html += '<div class="skill-test-quiz-select skill-test-quiz-select-header">전혀 그렇지 않다</div>';
	html += '<div class="skill-test-quiz-select skill-test-quiz-select-header">그렇지 않다</div>';
	html += '<div class="skill-test-quiz-select skill-test-quiz-select-header">보통이다</div>';
	html += '<div class="skill-test-quiz-select skill-test-quiz-select-header">그렇다</div>';
	html += '<div class="skill-test-quiz-select skill-test-quiz-select-header">매우 그렇다</div>';
	html += '</div>';
	html += '</div>';
	html += '<div class="skill-test-quiz-scroll">';
	html += '<div class="skill-test-quiz-wrap">';
	for(i = 0; i < skillInfo.length; i++){
		if(i == (skillInfo.length-1)){
			html += '<div class="skill-test-quiz-select-wrap">';
		}else{
			html += '<div class="skill-test-quiz-select-wrap skill-test-quiz-select-wrap-line">';
		}
		html += '<div class="skill-test-quiz-question">'+skillInfo[i]+'</div>';
		html += '<div class="skill-test-quiz-select" ><div class="radio circle-shape" id="skill-test-quiz-select-'+i+'-1"><div class="radio-on circle-shape radio'+i+'"></div></div></div>';
		html += '<div class="skill-test-quiz-select" ><div class="radio circle-shape" id="skill-test-quiz-select-'+i+'-2"><div class="radio-on circle-shape radio'+i+'"></div></div></div>';
		html += '<div class="skill-test-quiz-select" ><div class="radio circle-shape" id="skill-test-quiz-select-'+i+'-3"><div class="radio-on circle-shape radio'+i+'"></div></div></div>';
		html += '<div class="skill-test-quiz-select" ><div class="radio circle-shape" id="skill-test-quiz-select-'+i+'-4"><div class="radio-on circle-shape radio'+i+'"></div></div></div>';
		html += '<div class="skill-test-quiz-select" ><div class="radio circle-shape" id="skill-test-quiz-select-'+i+'-5"><div class="radio-on circle-shape radio'+i+'"></div></div></div>';
		html += '</div>';
		skillPoint.push(0);
	}
	html += '</div>';
	html += '</div>';
	html += '<div class="skill-test-submit-wrap">';
	html += '<button class="skill-test-btn" id="skill-test-btn-submit">';
	html += '<div class="skill-test-btn-name">결과보기</div>';
	html += '<div class="skill-test-btn-icon" id="skill-test-btn-icon-submit"></div>';
	html += '</button>';
	html += '</div>';
	html += '</div>';
	html += '<div class="skill-test-wrap" id="skill-test-result">';
	html += '<div class="skill-test-title"><div class="skill-test-title-txt">직무역량진단 결과</div></div>';
	html += '<div class="skill-test-result-guide"><div class="skill-test-result-guide-txt"></div></div>';
	html += '<div class="skill-test-guide3">역량진단 결과에 따라 보충자료를 학습하세요.</div>';
	html += '<div class="skill-test-result-wrap">';
	html += '<div class="skill-test-result-box" id="skill-test-result1">';
	html += '<div class="skill-test-result-box-tag">초급자</div>';
	html += '<div class="skill-test-result-box-guide">평균 3점 미만</div>';
	html += '<button class="skill-test-btn skill-test-btn-down" id="skill-test-btn-down-1">';
	html += '<div class="skill-test-btn-name">보충자료 다운로드</div>';
	html += '<div class="skill-test-btn-icon" id="skill-test-btn-icon-down"></div>';
	html += '</button>';
	html += '</div>';
	html += '<div class="skill-test-result-box" id="skill-test-result2">';
	html += '<div class="skill-test-result-box-tag">중급자</div>';
	html += '<div class="skill-test-result-box-guide">평균 3 ~ 4점</div>';
	html += '<button class="skill-test-btn skill-test-btn-down" id="skill-test-btn-down-2">';
	html += '<div class="skill-test-btn-name">보충자료 다운로드</div>';
	html += '<div class="skill-test-btn-icon" id="skill-test-btn-icon-down"></div>';
	html += '</button>';
	html += '</div>';
	html += '<div class="skill-test-result-box" id="skill-test-result3">';
	html += '<div class="skill-test-result-box-tag">고급자</div>';
	html += '<div class="skill-test-result-box-guide">평균 4점 초과</div>';
	html += '<button class="skill-test-btn skill-test-btn-down" id="skill-test-btn-down-3">';
	html += '<div class="skill-test-btn-name">보충자료 다운로드</div>';
	html += '<div class="skill-test-btn-icon" id="skill-test-btn-icon-down"></div>';
	html += '</button>';
	html += '</div>';
	html += '</div>';
	html += '<div class="skill-test-retry-wrap">';
	html += '<button class="skill-test-btn" id="skill-test-btn-retry">';
	html += '<div class="skill-test-btn-name">다시하기</div>';
	html += '<div class="skill-test-btn-icon" id="skill-test-btn-icon-retry"></div>';
	html += '</button>';
	html += '</div>';
	html += '</div>';
	html += '<div class="skill-alert">';
	html += '<div class="skill-alert-align">';
	html += '<div class="skill-alert-wrap">';
	html += '<div class="skill-alert-icon"></div>';
	html += '<div class="skill-alert-txt"></div>';
	html += '<div class="skill-alert-ex-txt"></div>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	html += '<button class="close-skill"><div class="close-skill-icon" id="close-skill-icon"></div></button>';
	html += '</div>';
	$("#wrap").append(skill_replace_fn(html));
	$(".skill-alert").hide();
	$(".skill-test-quiz-scroll").mCustomScrollbar({theme:"dark-3"});
	$(".close-skill").on({
		mouseover : function(){
			$(this).css({"transform":"rotate(180deg)","transition":"all ease 0.5s"}); 
			$(".close-skill-icon").css({"background-position-x":"-"+$(this).width()+"px"});
		},
		mouseout : function(){
			$(this).css({"transform":"rotate(0deg)","transition":"all ease 0.5s"}); 
			$(".close-skill-icon").css({"background-position-x":"0px"});
		},
		click : function(){
			$(".skill-test").fadeOut(function(){
				media_controll();
			});
		}
	});
	$(".skill-test-btn").on({
		mouseover : function(){
			var _id = $(this).attr('id').split("-");
			$(this).children(".skill-test-btn-name").css({"color":"#ffd600"});
			if($(this).hasClass('skill-test-btn-down')){
				$(this).children(".skill-test-btn-icon").css({"background-position-x":"-"+$("#skill-test-btn-icon-"+_id[_id.length-2]).width()+"px"});
			}else{
				$(this).children(".skill-test-btn-icon").css({"background-position-x":"-"+$("#skill-test-btn-icon-"+_id[_id.length-1]).width()+"px"});
			}
		},
		mouseout : function(){
			$(this).children(".skill-test-btn-name").css({"color":"#fff"});
			$(this).children(".skill-test-btn-icon").css({"background-position-x":"0px"});
		},
		click : function(){
			var _id = $(this).attr('id').split("-");
			console.log("hasClass : "+$(this).hasClass('skill-test-btn-down'));
			if($(this).hasClass('skill-test-btn-down')){
				var down_url = "./common/down/reference"+_id[_id.length-1]+".pdf";
				window.open(down_url);
			}else{
				skill_test_fn(_id[_id.length-1]);
			}
		}
	});
	$(".radio").on({
		click : function(){
			var _id = $(this).attr('id').split("-");
			var qn = parseInt(_id[_id.length-2],10);
			var sn = parseInt(_id[_id.length-1],10);
			skill_select_fn(qn,sn);
		}
	});
	mobileBtnSet();
}

function skill_replace_fn(_txt){
	var _replace = _txt;
	_replace = _replace.replaceAll('|','<br/>');
	_replace = _replace.replaceAll('{','<span class="skill-guide-impact">');
	_replace = _replace.replaceAll('}','</span>');
	_replace = _replace.replaceAll('(b)','<span class="skill-guide-bold">');
	_replace = _replace.replaceAll('(/b)','</span>');
	return _replace;
}

function initTest(){
	console.log("역량진단 테스트 시작!!!!");
	media_controll();
	$(".skill-test-wrap").hide();
	$("#skill-test-start").show();
	$(".skill-test").fadeIn(function(){
		index_view();
	});
}

function reset_skill(){
	skillLevel = 0;
	for(i = 0; i < skillInfo.length; i++){
		skillPoint[i] = 0;
	}
	$(".radio-on").hide();
}

function skill_select_fn(q,s){
	console.log(q+"번 문항 : "+s+" 선택!!!!");
	skillPoint[q] = s;
	$(".radio"+q).hide();
	$("#skill-test-quiz-select-"+q+"-"+s).children(".radio-on").show();
}

function skill_test_fn(_order){
	switch(_order){
		case 'start':
			console.log("역량진단 시작!!!!");
			$("#skill-test-start").fadeOut(function(){
				reset_skill();
				$("#skill-test-quiz").fadeIn();
			});
			break;
		case 'submit':
			skill_result_fn();
			break;
		case 'result':
			$("#skill-test-quiz").fadeOut(function(){
				$("#skill-test-result").fadeIn();
			});
			break;
		case 'retry':
			$("#skill-test-result").fadeOut(function(){
				reset_skill();
				$("#skill-test-quiz").fadeIn();
			});
			break;
	}
}

function skill_result_fn(){
	console.log("skillPoint : "+skillPoint);
	var _check = 0;
	for(i = 0; i < skillInfo.length; i++){
		if(skillPoint[i] == 0) _check++;
	}
	if(_check > 0){
		skill_alert_fn("fail");
	}else{
		skill_alert_fn("success");
	}
}

function setLevel(){
	var _total = 0;
	var _guide = '';
	for(i = 0; i < skillInfo.length; i++){
		_total = _total+skillPoint[i];
	}
	_total = _total/skillInfo.length;
	if(_total  > 4){
		skillLevel = 3;
		_guide = "당신의 역량은 {고급}입니다.";
	}else if(_total  >= 3 && _total  <= 4){
		skillLevel = 2;
		_guide = "당신의 역량은 {중급}입니다.";
	}else{
		skillLevel = 1;
		_guide = "당신의 역량은 {초급}입니다.";
	}
	$(".skill-test-result-guide-txt").html(skill_replace_fn(_guide));
	$(".skill-test-result-box").css({"border":"1px solid #ccc"});
	$(".skill-test-result-box-tag").css({"color":"#000","border-bottom":"1px solid #ccc"});
	$(".skill-test-btn-down").css({"background-color":"#000"});
	$("#skill-test-result"+skillLevel).css({
		"border":"1px solid #FDA947"
	}).children(".skill-test-result-box-tag").css({
		"color":"#FDA947",
		"border-bottom":"1px solid #FDA947"
	});
	$("#skill-test-result"+skillLevel).children(".skill-test-btn-down").css({"background-color":"#FDA947"});
	console.log("skillLevel : "+skillLevel);
}

function skill_alert_fn(_status){
	console.log("skill_alert_fn : "+_status);
	effect_sound_fn("skill");
	$(".skill-alert-icon").css({"background":"url(./common/img/skill/"+_status+".png) no-repeat"});
	var _alert = skillMSG[_status];
	_alert = _alert.replaceAll("|","<br/>");
	_alert = _alert.split("-");
	$(".skill-alert-txt").html(_alert[0]);
	$(".skill-alert-ex-txt").html(_alert[1]);
	$(".skill-alert").fadeIn().delay(1000).fadeOut(function(){
		if(_status == "success"){
			setLevel();
			skill_test_fn("result");
		}
	});
}
function learnPage(){
	if(portingMode == true){
		//setCookie("learnChk"+quarter,true,365);
	}
}

function setCookie(name, value, exp) {
	var date = new Date();
	date.setTime(date.getTime() + exp*24*60*60*1000);
	document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
}
  

function getCookie(name) {
	var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	return value? value[2] : null;
}

function checkCookie() {
	if (learnChk == "true") {
		learnedChk = true;
	}

	parent.pageChk(1, now_page);
} 