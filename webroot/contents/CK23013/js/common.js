$(function(){

  
  // 다운로드 창
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
  });


  
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
        $('#chapterNavi').stop().animate({'height':'0'},400); //chasinavi 영역
      }

    }
  });

});

$(window).load(function(){
  ///////////////////////// 기능 설정 ///////////////////////////////////////////////////////////////////////
  var jindoControl = false; // false 진도제어기능 해제, true 진도제어기능 적용



  
  var windowWidth = $( window ).width();
  //speed 설정
	$(".jp-toggles-text").click(function(){
		var toggleclasschk = $(".jp-speed-btn-box").hasClass("on");
		if(!toggleclasschk){
			$(".jp-speed-btn-box, .jp-toggles-text").addClass("on");
		} else {
			$(".jp-speed-btn-box, .jp-toggles-text").removeClass("on");
		}
		
	});


   //마우스올렸을때 컨트롤러 보임
	$("#allWrap").mouseover(function(){ 

		//영상이 일시정지일때
		$(".jp-gui").show();
		$(".jp-gradient-box").show();
		$(".jp-play.mobile").show();

  });

  //재생,일시정지 버튼
	$('.jp-play.mobile, .jp-controls .jp-play').bind('click',function(){

		$('.jp-play.mobile').hide();
		$(".jp-gui").hide();
		$(".jp-gradient-box").hide();

	});
  
  //차시명
  
  var chapNameSet=new Array();

    // 포팅 시 차시명 입력
    chapNameSet[1]="글로벌 음악시장의 현황과 흐름"; 
    chapNameSet[2]="음악 콘텐츠 기획이란?"; 
    chapNameSet[3]="음악 콘텐츠 유통 구조 및 프로세스의 이해Ⅰ"; 
    chapNameSet[4]="음악 콘텐츠 유통 구조 및 프로세스의 이해Ⅱ"; 
    chapNameSet[5]="음악 콘텐츠 마케팅 전략의 이해"; 
 
  
    //차시목록 생성
    function chapternameSet(){
	
      var lecnt=Math.round((chapNameSet.length-1)/2);
      var chapContent = $("#chapnavigation");
      
      if(chapContent){
        
        chapContent.empty();
        var learningString ="";
        //learningString ="<div id='learningUnit'>"
        //alert(chapNameSet.length);
        for ( var i = 1; i < chapNameSet.length; i++) {
          if(i==1||  i==(lecnt+1)){
            learningString  += "<ul id='learningSection'>";
            
          }
          learningString  += "<li id='learningUnit'>";
        
          if(i!=chapter){
            learningString  += "<div><span>"+i+"</span><a id='ltxt' href=../"+ chapter++ +">"+chapNameSet[i]+"</a></div>";
          }else{
            learningString  += "<div><span class='ltxton'>"+i+"</span><a class='ltxton' href=../"+ chapter++ +">"+chapNameSet[i]+"</a></div>";
          }		
          learningString +="</li>";
  
          if(i==lecnt|| i==chapNameSet.length){
            learningString  += "</ul>";
          }
        }
        //learningString +="</div>"
        //alert(learningString);
        
        chapContent.append(learningString);
        
      }
  
    }


    // 차시목록 창
    $('.u_chapter').bind('click',function(){

      var h = $('#chapnavigation').outerHeight();
      chapternameSet();
      if ($('.u_chapter').is('on')){
        $('#chapterNavi').stop().animate({'height':'0'},400);
        $(this).removeClass('on');
      
      } else {
        $('#chapterNavi').stop().animate({'height':'94.5%'},400);
        $(this).addClass('on');
        
      }
      
    });

});

