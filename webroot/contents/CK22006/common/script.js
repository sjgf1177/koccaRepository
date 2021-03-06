// JavaScript Document
var _this=this;

/////video var///////////////////////////////
var _video=null;
var _playBtn=null;
var _pauseBtn=null;
var _replayBtn=null;
var _volBtn=null;
var _fullscreen=null;

var _timeTxt=null;
var _timeTxtPlayed=null;
var _timeTxtTotal=null;
var _slideBar=null;
var _slideBg=null;
var _slideHandle=null;//slideHandleBg
var _slideHandleBar = null;
var _slideHandle=null;


var _mvPercent=0;
var _handleGap=15;
var _rect=null;
var _dragX=0;
var _dragWidth=0;
var _dragGap=0; //10;

var _dragPerc=0;
var eventChk=1;
var isDraging=false;

var isFullScreen=false;

/////video var///////////////////////////////

var _volHandle=null;
var _volHandleBg=null;
var _muteBtn = null;
var _unMuteBtn=null;
var _volSlideBar=null;

var _muteFlag=false;
var _volRect=null;
var _volDragX=0;
var _volDragY=0;
var _volDragHeight=0;
var _volDragPerc=0;
var _volFlag=true;
var _volDragGap=10;

var _narBtn=null;
var _narFlag=false;
var _narDiv=null;

var _audio=null;
var _bgm=null;

var _presentPageSummary=0;
var _totalPageSummary=0;


function sInit(){
	_this = this;
	_video = document.getElementById("myVideo");
	_source=document.createElement('source');
	var _surl;

	if(currentClau!=movieClau){
			var surlname=menuNaviNameSet[currentChapter][currentPage];
		_surl="http://edu_kocca.ecn.cdn.infralab.net/kocca/mp4/onlineclass/CK22006/mp4/"+itostr(currentChapter)+surlname.toLowerCase()+".mp4";
	}else{
		
		_surl="http://edu_kocca.ecn.cdn.infralab.net/kocca/mp4/onlineclass/CK22006/mp4/"+itostr(currentChapter)+itostr(currentClauPage)+".mp4";
	
	}
	
	if(document.getElementById("myAudio")){
		_video=document.getElementById("myAudio");
			_surl="../common/audio/"+menuNaviNameSet[currentChapter][currentPage]+".mp3";
			/*bgm????*/
			_audio=document.getElementById("myBgm");
			_bgm="../common/audio/Bgm.mp3";
			_sourcebgm=document.createElement('source');
			_sourcebgm.setAttribute('src', _bgm);
			_audio.appendChild(_sourcebgm);
			
		//_surl="../common/audio/"+itostr(currentChapter)+""+itostr(currentPage)+".mp3";
	}
	_source.setAttribute('src', _surl);
	_video.appendChild(_source);
	//alert();
	if(_video){
		try{
			_initVideo();
		}catch(e){}
	}
	_initVolume();
	menuInit();	
	loadSpeed();
	speedControl();
}

function _initVolume(){
	_muteBtn = document.getElementById("mute_btn");
	_unMuteBtn = document.getElementById("un_mute_btn");
	_volHandle = document.getElementById("volHandle");
	_volHandleBg = document.getElementById("volHandleBg");
	_volSlideBar = document.getElementById("volSlideBar");
	_narBtn = document.getElementById("nar_btn");
	_volBtn = document.getElementById("vol_btn");
	
	
	if(_muteBtn){
		_muteBtn.addEventListener("mouseover",_showVolHandle,false);
		_muteBtn.addEventListener("click",_mute,false);	
	}
	
	if(_unMuteBtn){
		_unMuteBtn.addEventListener("mouseover",_showVolHandle,false);
		_unMuteBtn.addEventListener("click",_unMute,false);	
	}
	if(_volHandle){
		//_volHandle.style.display="none";
		_volHandle.addEventListener("mousedown",_volDragStart,false);	
	}
	
	if(_narBtn){
		_narBtn.addEventListener("click",_narHandler,false);	
	}
	
	if(_volBtn){
		_volBtn.addEventListener("click",_volHandler,false);	
	}

	
}


function _volHandler(e){
	_volFlag=!_volFlag;
	if(_volFlag){
		// ??????????????
		//if(_video){
		//	_video.muted=false;
		//}
		_volSlideBar.style.display="none";

		//_volBtn.style.opacity=1;
	}else{
				// ????????????
	//	if(_video){
		//	_video.muted=true;
		//}
		_volSlideBar.style.display="block";
		if(_video.volume==1){
		$("#volHandleBg").css("background","#0058bb");
		//_volHandleBg.style.backgroundColor = "#0058bb";
		}
		//_volBtn.style.opacity=0.3;
	}
}


/*
function _volHandler(e){
	_volFlag=!_volFlag;
	if(_volFlag){
		if(_video){
			_video.muted=false;
		}
		_volBtn.style.opacity=1;
	}else{
		if(_video){
			_video.muted=true;
		}
		_volBtn.style.opacity=0.3;
	}
}
*/

function _narHandler(e){
	//alert("_narHandler!!");
	if(typeof(isNarrationsEmpty) == 'undefined' || isNarrationsEmpty == true)
		return;
		
	_narDiv = document.getElementById("narration");
	if(_narDiv){
		if(_narFlag){
			_narFlag=false;	
			_narBtn.style.opacity=1;
		}else{
			_narFlag=true;	
			_narBtn.style.opacity=0.7;
		}
		
		if(_narFlag){
			_narDiv.style.display="block";
		}else{
			_narDiv.style.display="none";
		}
	}
	
}

function _showVolHandle(e){
	var mc = e.target;
	if(_volHandle){
		_volHandle.style.display="block";
	}
}

function _volDragStart(e){
	if(_volSlideBar){
		
		//_volRect =	volHandleBg.getBoundingClientRect();
		_volRect = _volSlideBar.getBoundingClientRect();
		_volDragY = _volRect.top;
		_volDragX = _volRect.left;
		_volDragHeight = _volRect.top+_volRect.height;
		_volDragWidth = _volRect.left+_volRect.width; 
	}
	
	console.log("_volRect = "+_volRect+" / _volSlideBar = "+_volSlideBar);
	
	window.addEventListener("mousemove",_volDragEnter,false);	
	e.preventDefault();
}
function _volDragEnter(e){
	var volVisualDiff=0.85; // add
	$("#volHandleBg").css("background","#FFFFFF");
	//_volHandleBg.style.backgroundColor = "#FFFFFF";
	var yy = e.clientY-_volDragY;
	if(e.clientY<_volDragY){
		yy = 0;	
	}
	if(e.clientY>(_volDragHeight)){
		yy = _volRect.height;	
	}
	_volDragPerc = yy / (_volRect.height);
	_volHandle.style.marginTop=yy*volVisualDiff-_volHandle.style.height-11+"px";
	_volHandleBg.style.height = yy*volVisualDiff + "px";
	
	
	
	console.log(e.clientY+"/"+_volDragY+"/"+yy+" / "+_volDragPerc);	
		//_slideHandle.style.marginLeft=(e.clientX-_dragWidth)+"px";
	//_slideBar.style.width = xx+"px";
	
	
	//_slideBar.style.width = _slideHandle.style.left;
	if(_video){
		//_video.muted=false;
		_video.volume = 1-_volDragPerc;
	}
	if(_audio){
		_audio.volume = 1-_volDragPerc;
	}
}

function _mute(){
	_muteFlag=true;
	_muteBtn.style.display = "none";
	_unMuteBtn.style.display = "block";
	if(_video){
		_video.muted=true;
	}
	if(_audio){
		_audio.muted=true;
	}
}

function _unMute(){
	_muteFlag=false;
	_muteBtn.style.display = "block";
	_unMuteBtn.style.display = "none";
	if(_video){
		_video.muted=false;
	}
		if(_audio){
		_audio.muted=false;
	}
}

///////////video control/////////////////////////////////////////////////////////////////
function _initVideo(){
	_playBtn = document.getElementById("play_btn");
	_pauseBtn = document.getElementById("pause_btn");
	_timeTxt = document.getElementById("timer");
	_timeTxtPlayed = document.getElementById("playedTime");
	_timeTxtTotal = document.getElementById("totalTime");
	_slideBar = document.getElementById("slideBar");
	_slideBg = document.getElementById("slideBg");
	_slideHandle = document.getElementById("handle");
	_slideHandleBar =  document.getElementById("handleBar");
	_replayBtn = document.getElementById("replay_btn");
	_slideHandleBg = document.getElementById("slideHandleBg");
	_fullscreen = document.getElementById("full_btn");

	
	if(_slideBg){
		_rect = _slideBg.getBoundingClientRect();
		_dragX = _rect.left;
		_dragWidth = _rect.left+_rect.width-_handleGap;
		//console.log("_dragWidth = " + _dragWidth+",dragX="+_dragX);
	}
	
	
	
	_video.addEventListener('loadedmetadata',_loadMetadata,false);	
	_video.addEventListener('pause',_pause,false);
	_video.addEventListener('playing',_playing,false);
	_video.addEventListener('timeupdate',_timeUpdate,false);
	_video.addEventListener('ended',_ended,false);
	
	_video.controls=false;
	
	window.addEventListener("mouseup",_dragOver,false);
	
	_initBtn();
}

function _initBtn(){
	if(_playBtn){
		_playBtn.addEventListener("click",_playMv);	
	}
	if(_pauseBtn){
		_pauseBtn.addEventListener("click",_pauseMv);	
	}
	if(_slideHandle){
		_slideHandle.addEventListener("mousedown",_dragStart,false);	
	}
	
	if(_replayBtn){
		_replayBtn.addEventListener("click",replayMv);	
	}
	//alert("_slideHandleBg = "+_slideHandleBg);
	if(_slideHandleBg){
		_slideHandleBg.addEventListener("click",_slideclick,false);	
	}

	if(_fullscreen){
		_fullscreen.addEventListener("click",_fullscreenMv);	

	}


	//_playing();
}

// ???? ?ε?

function loadSpeed(){
var tmpSpeed = Number(getCookie('speed' + '_' + bmkCode));
    if(tmpSpeed){
		//alert(tmpSpeed);
    	_video.playbackRate = tmpSpeed;
    	if(tmpSpeed == 1){
    		$('#displaySpeed').html('x1.0');
    	}else if(tmpSpeed == 1.2){
    		$('#displaySpeed').html('x1.2');
    	}else if(tmpSpeed == 1.4){
    		$('#displaySpeed').html('x1.4');
		}else if(tmpSpeed == 1.6){
    		$('#displaySpeed').html('x1.6');
		}else if(tmpSpeed == 1.8){
    		$('#displaySpeed').html('x1.8');
    	}else if(tmpSpeed == 2){
    		$('#displaySpeed').html('x2.0');
    	}
    }else{
    	$('#displaySpeed').html('x1.0');
    }

}

// ????????
function speedControl(){
	$('#x10').click(function(){
		$('#displaySpeed').html('x1.0');
		_video.playbackRate = 1.0;
		setCookie('speed' + '_' + bmkCode, 1.0,365);
		speedCtrlBack();

	});
	$('#x12').click(function(){
		$('#displaySpeed').html('x1.2');
		_video.playbackRate = 1.2;
		setCookie('speed' + '_' + bmkCode, 1.2,365);
		speedCtrlBack();
	});
	$('#x14').click(function(){
		$('#displaySpeed').html('x1.4');
		_video.playbackRate = 1.4;
		setCookie('speed' + '_' + bmkCode, 1.4,365);
		speedCtrlBack();
	});
	$('#x16').click(function(){
		$('#displaySpeed').html('x1.6');
		_video.playbackRate = 1.6;
		setCookie('speed' + '_' + bmkCode, 1.6,365);
		speedCtrlBack();
	});
	$('#x18').click(function(){
		$('#displaySpeed').html('x1.8');
		_video.playbackRate = 1.8;
		setCookie('speed' + '_' + bmkCode, 1.8,365);
		speedCtrlBack();
	});
	$('#x20').click(function(){
		$('#displaySpeed').html('x2.0');
		_video.playbackRate = 2.0;
		setCookie('speed' + '_' + bmkCode, 2.0,365);
		speedCtrlBack();
	});
}

function speedCtrlBack(){
			$('#curSpeed').removeClass('on');
			$('#speedBg').css("display","none");
			$("#displaySpeed").css("top","-15px");
			//$("#displaySpeed").css("line-height","20px");
			$("#displaySpeed").css("margin-left","");
			$("#displaySpeed").css("margin-top","");

}


function _dragOver(e){
//	_slideHandle.removeEventListener("mouseup",_dragOver,false);
	window.removeEventListener("mousemove",_dragMove,false);	
	window.removeEventListener("mousemove",_volDragEnter,false);	

	console.log(_video.duration + ", " + _dragPerc);

	if(parent.isPageControl) {
		_gotoTimeMv();
	}

	isDraging=false;

		if(_video.currentTime<_video.duration-0.1){
	hidePopupChapter();
	}

	e.preventDefault();														
}

function _dragStart(e){
	isDraging=true;
	window.addEventListener("mousemove",_dragMove,false);	
	//window.addEventListener("mousemove",_volDragEnter,false);
	e.preventDefault();
}

function _dragMove(e) {
	var xx = e.clientX-_dragX-_dragGap;
	if(e.clientX<_dragX-_dragGap){
		xx = 0;	
	}
	if(e.clientX>(_dragWidth + _handleGap-_dragGap)){
		xx = _rect.width-_dragGap;	
	}
	_dragPerc = xx/(_rect.width-_dragGap);

	if(parent.isPageControl) {
		_slideHandleBar.style.width = xx + "px";
		_slideHandle.style.marginLeft = xx - _handleGap + "px";
		console.log("_dragMove : " + xx + " / " + _dragPerc + " / " + e.clientX + "/" + _rect.width);

		_gotoTimeMv();

		if(isDraging && _video){
			_video.pause();
		}
	}

	if(_video.currentTime<_video.duration-0.1){
		hidePopupChapter();
	}
	
	e.preventDefault();														
}

function _slideclick(e){
	//isDraging=true;
	var xx = e.clientX-_dragX-_dragGap;
	if(e.clientX<_dragX){
		xx = 0;	
	}
	if(e.clientX>(_dragWidth + _handleGap-_dragGap)){
		xx = _rect.width-_dragGap;	
	}
	_dragPerc = xx/(_rect.width-_dragGap);
//	if(_dragPerc>0.95){
//		_dragPerc	=0.95;
//	}

	if(parent.isPageControl){
		_video.currentTime = _video.duration*_dragPerc;
	}

	//_slideHandle.style.marginLeft=xx+"px";
	//isDraging=false;
	//alert("_slideclick")
	//_playing();
}

function audioJump(ct) {
	_video.currentTime = ct;
	if(_video.currentTime<_video.duration-0.1){
	_video.play();
	}
	//_video.playbackRate=spdArray[spdNum-1];
}

function audioEnd() {
	_video.currentTime = _video.duration;
	_video.pause();
}

function _timeUpdate(e){
	var cTime = _video.currentTime;
	var tTime = _video.duration;
	_mvPercent = cTime / tTime;
	_mvcurTime= cTime;  // add
	if(!cTime){
		cTime=0;	
	}
	if(!tTime){
		tTime=0;	
	}
	
	if(_timeTxt){
//		_timeTxt.innerHTML = rectime(cTime)+" / "+rectime(tTime);
		_timeTxtPlayed.innerHTML = rectime(cTime);
		_timeTxtTotal.innerHTML = " / " + rectime(tTime);
	}

	if(!isDraging){
		if(_slideBar && _slideBg){
			//_slideBar.style.width = _slideBg.clientWidth*_mvPercent+"px";
			var xW = _slideBg.clientWidth*_mvPercent;
			var isHandleX = xW;
			if(isHandleX<0){
				isHandleX=0;	
			}
			if(_slideHandle){
				//_slideHandle.style.marginLeft = 	isHandleX+"px";
				_slideHandleBar.style.width = isHandleX + "px";
				_slideHandle.style.marginLeft = isHandleX - _handleGap + "px";
			}
		}
	}
	
	if(cTime > tTime - 0.1) {
		if(typeof(dontShowPopupChapterByVideoEnd) == 'undefined' || dontShowPopupChapterByVideoEnd == false)
			showPopupChapter();
	}

	var nextPage = "";
	if(cTime == tTime) {
		if(currentPage != totalPage) {
			nextPage = pages[currentPage].url;
		} else {
			nextPage = pages[0].url;
		}

		parent.playPageSave(cTime, tTime, currentChapter, currentPage, totalPage, nextPage);
	}
	
	if(typeof(isStopAudioTime) == "function") {
		if(isStopAudioTime(cTime) == true)
			_video.pause();
	}
	
	// ???????????? ???? ?׼??? ?̷??????? ?Ҷ? ȣ???ϴ? ?Լ?
	if(typeof(buttonControlByVideo) == "function") {
		buttonControlByVideo();
	}

//	 console.log("controlhandle:"+_slideHandle.style.marginLeft+"/ control bar width : " + _slideHandleBar.clientWidth+" /  barbg width:"+_slideBg.clientWidth);
//	console.log("_slideHandleBar.style.width = "+_slideHandleBar.style.width);
//	console.log("cTime = " + cTime + ", tTime : " + tTime);
//	console.log("_mvPercent = " + _mvPercent);
}

function _ended(){
//	if(typeof(dontShowPopupChapterByVideoEnd) == 'undefined' || dontShowPopupChapterByVideoEnd == false)
//		showPopupChapter();
}

function _gotoTimeMv(){
	if(isDraging && _video){
		_video.currentTime = _video.duration*_dragPerc;
		if(_video.paused){
			_video.play();
		}
	}
}


function _loadMetadata(e){
	_ended();
	_timeUpdate();
	_playing();
}

// edit(2019.10.11)
var isPause=false;

function _pause(e){
	
	isPause=true;

	if(_playBtn){
		//_playBtn.style.display="block";	
		_playBtn.style.opacity=1;
	}
	
	if(_pauseBtn){
		//_pauseBtn.style.display="none";	
		_pauseBtn.style.opacity=1;
	}
}

function _playing(e){
	if(isPause){

		var tmpSpeed = Number(getCookie('speed' + '_' + bmkCode));
			if(tmpSpeed==0){
			tmpSpeed=1;
		}
		_video.playbackRate=tmpSpeed;
		isPause=false;
	}



	if(_playBtn){
		//_playBtn.style.display="none";	

		_playBtn.style.opacity=1;
	}
	
	if(_pauseBtn){
		//_pauseBtn.style.display="block";	
		_pauseBtn.style.opacity=1;
	}
}

function _playMv(e){
		var tmpSpeed = Number(getCookie('speed' + '_' + bmkCode));
		if(tmpSpeed==0){
			tmpSpeed=1;
		}
		_video.playbackRate=tmpSpeed;
	if(_video){
		_video.play();	
	}
		if(_audio.paused){
		_audio.play();
	}
}
function _pauseMv(e){
	if(_video){
		_video.pause();	
	}
}


function replayMv(e){
	//alert("replayMv");
	if(_video){
		_video.currentTime = 0;
		if(_video.paused){
			_video.play();
			_video.playbackRate=curSpeed;
		}
		if(_audio.paused){
		_audio.play();
	}
	}
	
	hidePopupChapter();
}

// fullscreen
var fullwidth=screen.width;
var fullheight=screen.height;
var fullscale=screen.width/1280;

function _fullscreenMv(e){

	if(!isFullScreen){

		if(_video){	
		/*
			 if (_video.requestFullscreen) {
			 _video.requestFullscreen();
			} else if (_video.mozRequestFullScreen) {
				_video.mozRequestFullScreen();
			} else if (_video.webkitRequestFullscreen) {
				_video.webkitRequestFullscreen();
			} else if(_video.msRequestFullscreen){
			 _video.msRequestFullscreen();
			 }
			 */
			  if (videoContainer.requestFullscreen) {
				videoContainer.requestFullscreen();
			} else if (videoContainer.mozRequestFullScreen) {
				videoContainer.mozRequestFullScreen();
			} else if (videoContainer.webkitRequestFullscreen) {
				videoContainer.webkitRequestFullscreen();
			} else if(videoContainer.msRequestFullscreen){
				videoContainer.msRequestFullscreen();
			 }

			  videoContainer.style.display = "block";
			videoContainer.style.width = "100%";
			videoContainer.style.height = "100%";

			//_handleGap=(fullwidth-11-312)/2+11;
			//_dragGap=(fullwidth-240)/2;
			//alert(_dragGap);
			
			$('#myVideo').css("width",fullwidth);
			$('#myVideo').css("height",720*fullscale);

			$('#control').css("top",720*fullscale-30);
			$('#control').css("width",fullwidth);
			
			$('.indexbtn').css("display","none");
			$('.mapbtn').css("display","none");
			$('.btns1').css("display","none");
			$('.bmkbtn').css("display","none");
			$('.btns3').css("display","none");
			$('.lecbtn').css("display","none");

		//	$('#control').css("text-align","center");
		//	$("#controlset").css("display","inline-block");
			//$("#controlset").css("width","1000px");
			//$("#lmsDiv").css("margin-right","300px");
			//$("#lmsDiv").css("width","700px");
			//$('.slidegap').css("margin-left","400px"); // ??Ʈ?Ѱ???
			//$("#slideBg").css("margin-left","700px");
			//$("#slideBar").css("left","600px");
			$('#paging').css("display","none");
		}
		isFullScreen=true;	
	}else{
		if(document.exitFullscreen) {
			document.exitFullscreen();
		} else if(document.webkitExitFullscreen) {
		   document.webkitExitFullscreen();
		} else if(document.mozCancelFullScreen) {
			 document.mozCancelFullScreen();
		} else if(document.msExitFullscreen) {
		   document.msExitFullscreen();
		}

		 offFullscreen();
		
	}

}

// full -> origin

document.addEventListener("webkitfullscreenchange", function(){
    if(!document.webkitIsFullScreen){
  offFullscreen();
    }
});
 

// Firefox
document.addEventListener("mozfullscreenchange", function(){
    if(!document.mozIsFullScreen){
       offFullscreen();
    }
});
 
// Explorer
document.addEventListener("MSFullscreenChange", function(){
    if(!document.msFullscreenElement){
        offFullscreen();
    }
});


document.addEventListener("fullscreenchange", function(){
    if(!document.fullscreenElement){
        offFullscreen();
    }
});

function offFullscreen(){
	    videoContainer.style.width = "1280px";
        videoContainer.style.height = "754px";
		myVideo.style.width="1280px";
		myVideo.style.height="720px";
		myVideo.style.top="0px";
		myVideo.style.left="0px";
		control.style.top="720px";
		control.style.width="1280px";

			$('.indexbtn').css("display","inline");
			$('.mapbtn').css("display","inline");
			$('.btns1').css("display","inline");
			$('.bmkbtn').css("display","inline");
			$('.btns3').css("display","inline");
			$('.lecbtn').css("display","inline");

			//$('#control').css("text-align","");
			//$("#controlset").css("display","");

			/*$('#control').css("text-align","left");*/
			//$("#lmsDiv").css("margin-right","0px");
			//$("#controlset").css("width","800px");
			//$("#controlset").css("left","20px");
			//$("#lmsDiv").css("width","220px");
			//$('.slidegap').css("margin-left","10px"); // ??Ʈ?Ѱ???
			//$("#slideBg").css("margin-left","5px");
		//	$("#slideBar").css("left","205px");
			$('#paging').css("display","block");
			//_handleGap=11;
		//	_dragGap=0;
		isFullScreen=false;
}


function rectime(secs){
	var hr = Math.floor(secs / 3600);
	var min = Math.floor((secs - (hr*3600))/60);
	var sec = Math.floor(secs - (hr*3600) - (min*60));

	min=hr*60+min;
	
	if(hr < 10) {hr = '0'+hr;}	
	if(min < 10) {min = '0'+min;}
	if(sec < 10) {sec = '0'+sec;}
	if(hr){hr='00';}
	return min+':'+sec;
}
///////////video control/////////////////////////////////////////////////////////////////


if(!console){
	var console=this;
	function log(arg){}
}

if(window.addEventListener){
	eventChk=1;
	window.addEventListener("load",sInit,false);
	
}else{
}

////// browser check

 function getBrowserType(){
     
     var _ua = navigator.userAgent;
     var rv = -1;
      
     //IE 11,10,9,8
     var trident = _ua.match(/Trident\/(\d.\d)/i);
     if( trident != null )
     {
         if( trident[1] == "7.0" ) return rv = "IE" + 11;
         if( trident[1] == "6.0" ) return rv = "IE" + 10;
         if( trident[1] == "5.0" ) return rv = "IE" + 9;
         if( trident[1] == "4.0" ) return rv = "IE" + 8;
     }
      
     //IE 7...
     if( navigator.appName == 'Microsoft Internet Explorer' ) return rv = "IE" + 7;
      
     /*
     var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
     if(re.exec(_ua) != null) rv = parseFloat(RegExp.$1);
     if( rv == 7 ) return rv = "IE" + 7; 
     */
      
     //other
     var agt = _ua.toLowerCase();
     if (agt.indexOf("chrome") != -1) return 'Chrome';
     if (agt.indexOf("opera") != -1) return 'Opera'; 
     if (agt.indexOf("staroffice") != -1) return 'Star Office'; 
     if (agt.indexOf("webtv") != -1) return 'WebTV'; 
     if (agt.indexOf("beonex") != -1) return 'Beonex'; 
     if (agt.indexOf("chimera") != -1) return 'Chimera'; 
     if (agt.indexOf("netpositive") != -1) return 'NetPositive'; 
     if (agt.indexOf("phoenix") != -1) return 'Phoenix'; 
     if (agt.indexOf("firefox") != -1) return 'Firefox'; 
     if (agt.indexOf("safari") != -1) return 'Safari'; 
     if (agt.indexOf("skipstone") != -1) return 'SkipStone'; 
     if (agt.indexOf("netscape") != -1) return 'Netscape'; 
     if (agt.indexOf("mozilla/5.0") != -1) return 'Mozilla';
 }

