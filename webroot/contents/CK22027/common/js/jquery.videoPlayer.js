;(function($){
	$.fn.videoPlayer = function(options){

		var isRePlay = false;
		var curSync = 0;
		var rateInfo = new Object();
		rateInfo = [0.5,1,1.5,2];
		var curRate = 1;

		return this.each(function() {
			var $vp = $(this);
			var $vp_controls = $("#navigationUI");
			var controls_html = '';
			controls_html += '<div class="navigation-wrap">';

			controls_html += '<button id="index-open">';
			controls_html += '<div id="index-open-icon"></div>';
			// controls_html += '<div id="index-open-txt">INDEX</div>';
			controls_html += '</button>';
			controls_html += '<div class="seek"></div>';
			controls_html += '<div class="timer">00:00 / 00:00</div>';
			controls_html += '<button class="control-btn" id="caption"></button>';
			controls_html += '<button class="control-btn" id="play"></button>';
			controls_html += '<button class="control-btn" id="replay"></button>';
			controls_html += '<button class="control-btn" id="volume"></button>';
			// controls_html += '<button class="control-btn" id="fullscreen"></button>';
			// controls_html += '<div class="rate-tag">배속 :</div>';
			// for(i = 0; i < rateInfo.length; i++){
			// 	controls_html += '<button class="rate-btn rate-txt" id="rate-'+i+'">'+rateInfo[i].toFixed(1)+'</button>';
			// 	if(i < (rateInfo.length-1)) controls_html += '<div class="rate-split"></div>';
			// }
			controls_html += '<div class="page-wrap">';
			controls_html += '<button class="control-btn" id="prev"></button>';
			controls_html += '<div class="page-txt" id="current-page">'+transNum(now_page)+'</div>';
			controls_html += '<div class="page-txt page-txt-slash">/</div>';
			controls_html += '<div class="page-txt" id="total-page">'+transNum(total_page)+'</div>';
			controls_html += '<button class="control-btn" id="next"></button>';
			controls_html += '</div>';
			controls_html += '</div>';

			$vp_controls.html(controls_html);

			var $index_btn = $vp_controls.find('#index-open'),
			$seek = $vp_controls.find('.seek'),
			$control_btn = $vp_controls.find('.control-btn'),
			$play_btn = $vp_controls.find('#play'),
			$caption_btn = $vp_controls.find('#caption'),
			$volume_btn = $vp_controls.find('#volume'),
			$rate_btn = $vp_controls.find('.rate-btn'),
			$timer = $vp_controls.find('.timer'),
			$prev = $vp_controls.find('#prev'),
			$next = $vp_controls.find('#next'),
				
			vp = $vp[0],
			seek_sliding;
			if(portingMode == true && learnChk == null){
				$(".seek").css("pointer-events","none");
			}else{
				$(".seek").css("pointer-events","auto");
			}

			$vp.bind('timeupdate', seekUpdate);

			createSeek();

			// if(cur_app != "none") {
			// 	$("#fullscreen").click(function(){
			// 		// attr("disabled",true).css({"opacity":0.5,"cursor":"default"});
			// 		fullscreenCheck = "fullscreen";
			// 		alert("전체화면을 지원하지 않습니다.")
			// 	})
			// }
			
			if(captionInfo[(now_page-1)].caption.length == 0) $caption_btn.attr("disabled",true).css({"opacity":0.2,"cursor":"default"});

			if(now_page == 1){
				$prev.attr("disabled",true).css({"opacity":0.2,"cursor":"default"});
			}else if(now_page == total_page){
				$next.attr("disabled",true).css({"opacity":0.2,"cursor":"default"});
			}

			$control_btn.on({
				mouseover : function(){
					var _id = $(this).attr('id');
					if(isBrowse == "PC") mouseOver_fn(_id);
				},
				mouseout : function(){
					var _id = $(this).attr('id');
					if(isBrowse == "PC") mouseOut_fn(_id);
				},
				click : function(){
					var _id = $(this).attr('id');
					control_fn(_id);
				}
			});
			$rate_btn.on({
				mouseover : function(){
					if(isBrowse == "PC") $(this).css({"color":"#FDA947","font-family":"scd6"});
				},
				mouseout : function(){
					var _rate = parseInt($(this).attr('id').replace("rate-",""),10);
					if(_rate != curRate){
						if(isBrowse == "PC") $(this).css({"color":"#fff","font-family":"scd2"});
					}
				},
				click : function(){
					var _rate = parseInt($(this).attr('id').replace("rate-",""),10);
					select_rate(_rate);
				}
			});
			$index_btn.on({
				mouseover : function(){
					if(isBrowse == "PC"){
						var _id = $(this).attr('id');
						$("#index-open-icon").css({"background":"url(./common/img/navigationUI/btn_indexOver.png) no-repeat"});
						$("#"+_id+'-txt').css({"color":"#FDA947"});
					}
				},
				mouseout : function(){
					var _id = $(this).attr('id');
					if(indexToggle == "close" && isBrowse == "PC"){
						$("#index-open-icon").css({"background":"url(./common/img/navigationUI/btn_index.png) no-repeat"});
						$("#"+_id+'-txt').css({"color":"#fff"});
					}
				},
				click : function(){
					index_view();
				}
			});

			function mouseOver_fn(_id){
				if(_id == "volume"){

					if(vp.muted) $("#"+_id).css({"background":"url(./common/img/navigationUI/mute.png) no-repeat 3px 2px"});
				}
				if(_id == "replay"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/replayOver.png) no-repeat 3px 2px"});
				}
				if(_id == "caption"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/caption.png) no-repeat 3px 2px"});
				}

				if(_id == "next"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/btn_nextOver.png) no-repeat"});
				}
				if(_id == "prev"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/btn_prevOver.png) no-repeat"});
				}
			}

			function mouseOut_fn(_id){
				// if(_id == "caption"){
				// 	if(captionToggle == "close") {
				// 		$("#"+_id).css({"background-position-x":"0px"});
				// 	}
				// }
				if(_id == "volume"){

					if(vp.muted) $("#"+_id).css({"background":"url(./common/img/navigationUI/mute.png) no-repeat 3px 2px"});
				}
				if(_id == "caption"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/caption.png) no-repeat 3px 2px"});
				}
				if(_id == "replay"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/replay.png) no-repeat 3px 2px"});
				}
				if(_id == "next"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/btn_next.png) no-repeat"});
				}
				if(_id == "prev"){
					$("#"+_id).css({"background":"url(./common/img/navigationUI/btn_prev.png) no-repeat no-repeat"});
				}
			}

			function control_fn(_id){
				switch(_id){
					case 'play':
						playPause();
						break;
					case 'replay':
						rePlay();
						break;
					case 'caption':
						caption_view();
						break;
					case 'volume':
						muteVolume();
						break;
					case 'fullscreen':
						toggle_fullScreen();
						break;
					case 'prev':
						prev_next(_id);
						break;
					case 'next':
						if(!parent.isPageControl){
							alert('학습을 완료한 후 클릭 가능합니다.');
						}else{
							prev_next(_id);
							break;
						}
				}
			}

			function select_rate(n){
				curRate = n;
				vp.playbackRate = rateInfo[curRate];
				$rate_btn.css({"color":"#fff","font-family":"scd2"});
				$("#rate-"+curRate).css({"color":"#FDA947","font-family":"scd6"});
			}

			vp.controls = false;

			/* = 영상재생
			---------------------------------------------------------------- */

			$vp.attr("src",loadMediaURL);
			$vp.get(0).load();
			vp.addEventListener('error',function(){
				console.log("영상로드 실패!!!");
				$("#loadingUI").fadeOut();
			});
			$vp.get(0).onloadeddata = function(){
				isRePlay=false;
				seekUpdate();
				setVolume();
				select_rate(curRate);
				$("#loadingUI").fadeOut();
				// if(cur_flow == "lesson" && subject[(now_page-1)] != "disable"){
				// 	vp.currentTime = 0;
				// 	vp.pause();
				// 	buildSlipsheet();
				// }
				// if(cur_flow == "lesson"){
				// 	vp.currentTime = 0;
				// 	vp.pause();
				// 	buildSlipsheet();
				// }

				var playPromise = document.querySelector('video').play();

				if(playPromise !== undefined){
					playPromise.then(function() {
						media_start();
					}).catch(function(error) {
						startMedia();
					});
					$("#play").focus();
				}
			}

			function playToggle(){
				if(!vp.paused){
					$play_btn.css({"background":"url(./common/img/navigationUI/btn_pause.png) no-repeat 3px 2px"});
					if(vp.ended){
						$play_btn.css({"background":"url(./common/img/navigationUI/btn_play.png) no-repeat 3px 2px"});
					}
				}else{
					$play_btn.css({"background":"url(./common/img/navigationUI/btn_play.png) no-repeat 3px 2px"});
				}
			}


			/* = 이벤트 핸들링
			---------------------------------------------------------------- */
			function media_start(){
				vp.play();
				$("#mobileUI").fadeOut();
			}

			function startMedia(){
				var guide = "웹 보안 정책에 따라 자동재생이 제한되었습니다.|[화면을 ()해 주세요.]";
				guide = guide.replaceAll('|','<br/>');
				guide = guide.replaceAll('[','<span class="play-txt-guide">');
				guide = guide.replaceAll(']','</span>');
				if(isBrowse == "PC"){
					guide = guide.replaceAll('()','<span class="play-txt-guide-impact">클릭</span>');
				}else{
					guide = guide.replaceAll('()','<span class="play-txt-guide-impact">터치</span>');
				}
				var playHtml = "<button id='mediaPlay'>";
				playHtml += '<div class="play-icon"></div>';
				playHtml += '<div class="play-txt">'+guide+'</div>';
				playHtml += "</button>";
				$("#mobileUI").html(playHtml);
				$("#mobileUI").show();
				$("#mediaPlay").on({
					click : function(){
						media_start();
					}
				});
			}

			function playPause(){
				if(isRePlay){
					vp.pause();
					rePlaySeek();
				}else{
					if(vp.ended){
						isRePlay = true;
						rePlaySeek();
					}else{
						if(!vp.paused){
							vp.pause();
						}else{
							vp.play();
						}
					}
				}
				$("#mobileUI").fadeOut();
				play_conf();
			}

			function rePlay(){
				isRePlay = true;
				playPause();
			}

			function rePlaySeek(){
				isRePlay = false;
				seek_sliding = false;
				vp.currentTime = 0;
				vp.play();
			}

			media_controll=function controll_fn(){
				if(!vp.ended){
					if(!vp.paused){
						vp.pause();
					}else{
						vp.play();
					}
				}
			}

			function createSeek(){
				if(vp.readyState){
					var dur = vp.duration;
					$seek.slider({
						step: 0.01,
						range:'min', 
						max:dur,
						animate:true,
						slide: function(){
							if(parent.isPageControl){
								seek_sliding = true;
								vp.pause();
							}
						},
						stop: function(e, ui){
							if(parent.isPageControl){
								seek_sliding = false;
								vp.currentTime = ui.value;
								vp.play();
								isRePlay = false;
								play_conf();
							}
						}
					});
				}else{
					setTimeout(createSeek, 150);
				}
			}

			playerGoto=function gotoPlay(time){
				if(time == "end"){
					vp.currentTime = vp.duration;
					vp.pause();
				}else{
					vp.currentTime = time;
					vp.play();
				}
			}

			function seekUpdate(){
				var current_time = vp.currentTime;
				var total_time = vp.duration;

				if(!seek_sliding) $seek.slider({'value':current_time});
				$timer.text(timeFormat(current_time));
				playToggle();
				page_complete_fn();

				if(cur_sync != "none"){
					syncTotal = dataInfo[(now_page-1)].sync.length;
					sync_fn();
				}
			}

			function sync_fn(){
				var current_time = vp.currentTime;
				var _syncTime
				for(i = 0; i < syncTotal; i++){
					if(i == (syncTotal-1)){
						if(current_time >= dataInfo[(now_page-1)].sync[i].startTime){
							if(curSync != i){
								curSync = i;
								sync_effect();
							}
						}
					}else{
						if(current_time >= dataInfo[(now_page-1)].sync[i].startTime && current_time < dataInfo[(now_page-1)].sync[(i+1)].startTime){
							if(curSync != i){
								curSync = i;
								sync_effect();
							}
						}
					}
				}
				
			}

			function sync_effect(){
				var _effect = dataInfo[(now_page-1)].sync[curSync].effect;
				var _obj = dataInfo[(now_page-1)].sync[curSync].syncObj;
				var _func = dataInfo[(now_page-1)].sync[curSync].func;
				if(curSync == 0){
					$(".syncObj").hide();
				}else{
					$(".syncObj").hide();
					for(i = 0; i < syncTotal; i++){
						if(i < curSync){
							$("#"+dataInfo[(now_page-1)].sync[i].syncObj).show();
						}
					}
					switch(_effect){
						case 'none':
							$("#"+_obj).show();
							break;
						case 'show':
							$("#"+_obj).fadeIn();
							break;
						case 'show-top':
							$("#"+_obj).show().css({"margin-top":"-20px","display":"block","opacity":0}).stop().animate({"margin-top":"0px","opacity":1},300);
							break;
						case 'show-bottom':
							$("#"+_obj).show().css({"margin-top":"20px","display":"block","opacity":0}).stop().animate({"margin-top":"0px","opacity":1},300);
							break;
						case 'show-left':
							$("#"+_obj).show().css({"margin-left":"20px","display":"block","opacity":0}).stop().animate({"margin-left":"0px","opacity":1},300);
							break;
					}
				}
				sync_ex_fn();
			}

			function sync_ex_fn(){
				var _func = dataInfo[(now_page-1)].sync[curSync].func;
				if(_func != "none"){
					var func_options = _func.split("|");
					var _parameter = null;
					if(func_options.length > 1) _parameter = func_options[1];
					window.postMessage({
						'func':func_options[0],
						'message':_parameter
					},"*");
				}
			}

			function page_complete_fn(){
				if(vp.ended){
					isPlaying = false;
					learnedChk = true;
					$(".seek").css("pointer-events","auto");
					learnPage();

					if(now_page < total_page) {
						parent.playPageSave(vp.currentTime, vp.duration, 1, now_page, total_page, transNum(now_page + 1) + ".html");
					}

					if(cur_app != "none"){
						if(app_complete == "complete"){
							balloon_toggle("show");
						}
					}else{
						balloon_toggle("show");
					}
				}else{
					if(now_page == total_page) {
						if((vp.currentTime > 90 && vp.currentTime < 91) && !isPlaying) {
							parent.playPageSave(vp.duration, vp.duration, 1, now_page, total_page, transNum(total_page) + ".html");
							isPlaying = true;
							balloon_toggle("hide");
						}
					}
				}
			}

			function timeFormat(sec){
				var duration_m = Math.floor(vp.duration/60)<10 ? '0'+Math.floor(vp.duration/60) : Math.floor(vp.duration/60),
					duration_s = Math.floor(vp.duration-(duration_m*60))<10? '0'+Math.floor(vp.duration-(duration_m*60)) : Math.floor(vp.duration-(duration_m*60));
				var current_m = Math.floor(sec/60)<10 ? '0'+Math.floor(sec/60) : Math.floor(sec/60),
					current_s = Math.floor(sec-(current_m*60))<10? '0'+Math.floor(sec-(current_m*60)) : Math.floor(sec-(current_m*60));
				return current_m+':'+current_s+" / "+duration_m+':'+duration_s;
			}

			function setVolume(){
				vp.muted = false;
				effectSnd.muted = false;
				completeSnd.muted = false;
				$volume_btn.css({"background":"url(./common/img/navigationUI/volume.png) no-repeat 3px 2px"});
			}

			function muteVolume(){
				if(vp.muted){
					vp.muted = false;
					effectSnd.muted = false;
					completeSnd.muted = false;
					$volume_btn.css({"background":"url(./common/img/navigationUI/volume.png) no-repeat 3px 2px"});
				}else{
					vp.muted = true;
					effectSnd.muted = true;
					completeSnd.muted = true;
					$volume_btn.css({"background":"url(./common/img/navigationUI/mute.png) no-repeat 3px 2px"});
				}
			}

			function prevPage(){
				prev_next("prev");
			}

			function nextPage(){
				prev_next("next");
			}

		});
	};
})(window.jQuery);