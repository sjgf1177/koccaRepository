$(function(){

/*  // 다운로드 창
  $('.u_download').bind('click',function(){
    if ($(this).is('.on')){
      $('#tgDown').fadeOut(300);
      $(this).removeClass('on');
      if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('play');}
    } else {
      $('#tgDown').fadeIn(300);
      $(this).addClass('on');
      if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('pause');}
    }
  });*/

  // 인덱스 창
  $('.u_index').bind('click',function(){
    var h = $('#navigation').outerHeight();
    if ($(this).is('.on')){
      $('#indexNavi').stop().animate({'height':'0'},400);
      $(this).removeClass('on');
    //  if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('play');}
    } else {
      $('#indexNavi').stop().animate({'height':h},400);
      $(this).addClass('on');
     // if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('pause');}
    }
  });
  


  // 스크립트 창
  /*
  $('.jp-script').bind('click',function(){

    
    var h = $('.scHead').height()+$('.scConfBox').outerHeight();
    if ($(this).is('.on')){
      $('#scriptBox').stop().animate({'height':'0'},400);
      $(this).removeClass('on');
    //  if ($('.quiz').length > 0){}else{$('#jquery_jplayer_1').jPlayer('play');}
    } else {
      $('#scriptBox').stop().animate({'height':h},400);
      $(this).addClass('on');
    //  if ($('.quiz').length > 0){}else{$('#jquery_jplayer_1').jPlayer('pause');}
    }
  });
  $('.scClose').bind('click',function(){
    $('.jp-script').click();
    
  });
  */

  // 창숨기기
  $(document).mouseup(function(e){
    if ($('.movieView').hasClass('on')){
      return false;
    } else {


      if (!$('#tgDown, .u_download').is(e.target) && $('#tgDown, .u_download').has(e.target).length === 0){
        if($('.u_download').is('.on')){
          $('#tgDown').fadeOut(300);
          $('.u_download').removeClass('on');
          if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('play');}
        }
      }

      if (!$('#tgMovie, .u_playmovie').is(e.target) && $('#tgMovie, .u_playmovie').has(e.target).length === 0){
        if($('.u_playmovie').is('.on')){
          $('#tgMovie').fadeOut(300);
          $('.u_playmovie').removeClass('on');
          if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('play');}
        }
      }
      /*
      if (!$('#scriptBox, .jp-script').is(e.target) && $('#scriptBox, .jp-script').has(e.target).length === 0){
        if($('.jp-script').is('.on')){
          var h = $('.scHead').height()+$('.scConfBox').outerHeight();
          $('#scriptBox').stop().animate({'height':'0'},400);
          $('.jp-script').removeClass('on');
          if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('play');}
        }
      }
      */
      if (!$('#navigation, .u_index').is(e.target) && $('#navigation, .u_index').has(e.target).length === 0){
        if($('.u_index').is('.on')){
          var h = $('#navigation').outerHeight();
          $('#indexNavi').stop().animate({'height':'0'},400);
          $('.u_index').removeClass('on');
          if ($('.quiz').length > 0 || $('.jp-limit').length > 0){}else{$('#jquery_jplayer_1').jPlayer('play');}
        }
      }

      if($(".jp-speed").has(e.target).length === 0){
        $(".jp-speed-btn-box, .jp-toggles-text").removeClass("on"); //speed 영역
      }

      if($(".u_chapter").has(e.target).length === 0){
        $(".u_chapter").removeClass("on");
        $('#chapterNavi').stop().animate({'left':'-999px'},400); //chasinavi 영역
      }

    }
  });

});

$(window).load(function(){
  ///////////////////////// 기능 설정 ///////////////////////////////////////////////////////////////////////
  var jindoControl = false; // false 진도제어기능 해제, true 진도제어기능 적용

    //speed 설정
    $(".jp-toggles-text").click(function(){
        var toggleclasschk = $(".jp-speed-btn-box").hasClass("on");
        if(!toggleclasschk){
            $(".jp-speed-btn-box, .jp-toggles-text").addClass("on");
        } else {
            $(".jp-speed-btn-box, .jp-toggles-text").removeClass("on");
        }

    });

	//마우스 움직임 없을 때 이벤트
    var test = $('#jp_container_1');
	var moveTimer; //머무른시간을 가짐
    /*test.on("mouseout",function(){

        $(".jp-gui").fadeOut();
        //$(".jp-gradient-box").fadeOut();

        console.log('영상영역에서 벗어남');
        clearTimeout(moveTimer); //머무른시간 초기화

    });*/

    test.on("mousemove",function(){
        //마우스 움직일때마다 실행
        $(".jp-gui").show();
        $('.scriptWrap').css('bottom','55px');
        //$(".jp-gradient-box").show();
        $(".jp-play.mobile").show();

        clearTimeout(moveTimer); //머무른시간 초기화
        moveTimer = setTimeout(function(){ //setTimeout으로 3초뒤 이벤트 실행
            $(".jp-gui").hide();
            $('.scriptWrap').css('bottom','19px');
            //$(".jp-gradient-box").fadeOut();
            $(".jp-play.mobile").hide();
            //console.log('3초동안 마우스 움직임없음');
        },1000)
    });

    $(".jp-bottom-controls").on("mouseout",function(){
        clearTimeout(moveTimer); //머무른시간 초기화
        $(".jp-gui").hide();
        $(".jp-play.mobile").hide();
        $('.scriptWrap').css('bottom','19px');

        //console.log('컨트롤러에서 벗어남!');
    });


    //재생,일시정지 버튼 눌렀을 때
    $('.jp-play.mobile, .jp-controls .jp-play').bind('click',function(){
        $(".jp-gui").hide();
        $('.jp-play.mobile').hide();
        $(".jp-gradient-box").hide();
        $('.scriptWrap').css('bottom','19px');
        //console.log('버튼 누르면 숨김');
    });

	//function mediaResize() {
	var windowWidth = $( window ).width(); // iframe 플레이어 가로너비 기준

        if (windowWidth < 984) {
            //984px 이하일 때
            console.log(' iframe 플레이어 가로너비 984px 이하');
            $(".jp-gui").hide();
            //마우스올렸을때 컨트롤러 보임
            $("#jp_video_0").mouseenter(function(){
                $(".jp-gui").show();
                //$(".jp-gradient-box").show();
                $(".jp-play.mobile").show();
                //console.log('모바일 123');
                $('.scriptWrap').css('bottom','43px');
            });

        } else{
            //984px 초과일 때
            console.log(' iframe 플레이어 가로너비 984px 초과');
            //$('.jp-play.mobile').hide();
            //마우스올렸을때 컨트롤러 보임
            $("#jp_container_1").mouseenter(function(){
                $(".jp-gui").show();
                $(".jp-play.mobile").show();
                //$(".jp-gradient-box").show();
                $('.scriptWrap').css('bottom','55px');
                //console.log('마우스 영역안에 들어옴');
            });
        }
	//}

    var videoDOM = $("#jp_video_0");

    //영상클릭시 재생,일시정지
    $(videoDOM).on({

        'click': function () {
            var video = $('#jp_container_1').hasClass('jp-state-playing');
            if(video){
                //console.log('재생중');
                $('#jquery_jplayer_1').jPlayer('pause');
            } else{
                //console.log('일시정지');
                $('#jquery_jplayer_1').jPlayer('play');
            }

        }

    });

    //영상 10초 전,후 이동
    $("[class^='jp-cur-']").on({
        'click': function(){
            var className = $(this).hasClass('jp-cur-rewind');
            var currentTime = videoDOM[0].currentTime;

            if (className) {
                //10초전 이동
                $('#jquery_jplayer_1').jPlayer('play',currentTime - 10);
                $('.circle-static-rewind').fadeIn(500, function (){
                    $(this).fadeOut();
                })
                //console.log('10초전');
            } else {
                //10초후 이동
                if(videoDOM[0].duration == currentTime){
                    return;
                } else{
                    $('#jquery_jplayer_1').jPlayer('play',currentTime + 10);
                    $('.circle-static-forward').fadeIn(500, function (){
                        $(this).fadeOut();
                    })
                    //console.log('1 : ',videoDOM[0].duration);
                    //console.log('2 : ',currentTime);
                }
            }
        }
    });

    //키보드 제어
    $(document).keydown(function (event){
        var key = event.keyCode;
        var currentTime = videoDOM[0].currentTime;
        if(key==37){
            //왼쪽
            $('#jquery_jplayer_1').jPlayer('play',currentTime - 10);
            $('.circle-static-rewind').fadeIn(500, function (){
                $(this).fadeOut();
            })
        }else if(key==38){
            //위
        }else if(key==39){
            //오른쪽
            $('#jquery_jplayer_1').jPlayer('play',currentTime + 10);
            $('.circle-static-forward').fadeIn(500, function (){
                $(this).fadeOut();
            })
        }else if(key==40){
            //아래
        }
    });

    /* 자막 */
    setTimeout(function() {
        //$(videoDOM).append("<track default kind='subtitles' srclang='ko' label='켜짐' src='../vtt/" + chapter + "_" + page + ".vtt' />");
        $(videoDOM).append("<track default kind='subtitles' srclang='ko' label='켜짐' src='../vtt/" + "01_03" + ".vtt' />"); //테스트용


        ///vtt 텍스트 ///
        videoDOM.on('timeupdate', function () {
            const tracks = document.querySelector("video").textTracks[0]; //vtt 자막을 배열화??
            //console.log(tracks.text);

            tracks.mode = "hidden"; // 기존 VTT 자막 숨김

            var activeCue;
            try {
                activeCue = tracks.activeCues[0];
                //console.log(track.activeCues[0].text);
                $('.scriptTxt').html(activeCue.text.replace("\n", "<br/>")); //vtt 텍스트 출력
            } catch (e) {
                $('.scriptTxt').html('자막이 제공되지 않는 콘텐츠 입니다.');
            }

        });

        //track[0].track.mode = 'showing';


    },500);




});

