$(document).ready(function(){


  var videoDOM = $("#jp_video_0");
  var nPage = "";

  /* 자막 */
  setTimeout(function() {
    $(videoDOM).append("<track default kind='subtitles' srclang='ko' label='켜짐' src='../vtt/" + chapter + "_" + page + ".vtt' />");
    //track[0].track.mode = 'showing';
  },500);

  setTimeout(function() {
    if (setNumberChk(page) < 10){
      nPage = "0" + (setNumberChk(page) + 1) + ".html";
    }else{
      nPage = (setNumberChk(page) + 1) + ".html";
    }

    if(setNumberChk(page) == setNumberChk(tot_page[setNumberChk(chapter)])) {
      nPage = "01.html";
    }

    $.ajax({
      type : "post"
      ,   dataType: "json"
      ,   url : "/servlet/controller.contents.EduStart"
      ,   data : {
        ct : videoDOM[0].currentTime,
        tt : videoDOM[0].duration,
        cc : setNumberChk(chapter),
        cp : setNumberChk(page),
        tp : setNumberChk(tot_page[setNumberChk(chapter)]),
        np : nPage,
        p_process : "subjseqPageSearchInput"
      }
      ,   success : function(ajaxData) {
        console.log("000 : " + ajaxData.cTime);
        $('#jquery_jplayer_1').jPlayer('play', setNumberChk(ajaxData.cTime));
      }
      ,   error :  function(arg1, arg2) {
        alert("오류가 발생하여 플레이 할 수 없습니다.");
      }
    });

  },1000);

  $('.jp-script').bind('click',function(){

    if(!$(this).is('.on')){
      //자막이 꺼져있을때
      $(".jp-jplayer").addClass('script_active');
      $(this).addClass('on');
      console.log('자막켜짐');


    } else {

      //자막이 켜져있을때
      $(this).removeClass('on');
      $(".jp-jplayer").removeClass('script_active');
      console.log('자막꺼짐');
      return false;

    }
  });

  function setNumberChk(v){
    if(v == ""){
      return 0;
    }else{
      return Number(v);
    }
  }

  videoDOM.on('ended', function() {
    $.ajax({
      type : "post"
      ,   url : "/servlet/controller.contents.EduStart"
      ,   data : {
        ct : videoDOM[0].currentTime,
        tt : videoDOM[0].duration,
        cc : setNumberChk(chapter),
        cp : setNumberChk(page),
        tp : setNumberChk(tot_page[setNumberChk(chapter)]),
        np : "",
        p_process : "subjseqPageUpdate"
      }
      ,   success : function(ajaxData) {

      }
      ,   error :  function(arg1, arg2) {
        alert("오류가 발생하여 플레이 할 수 없습니다.");
      }
    });
  });

  window.onbeforeunload = function () {
    $.ajax({
      type : "post"
      ,   url : "/servlet/controller.contents.EduStart"
      ,   data : {
        ct : videoDOM[0].currentTime,
        tt : videoDOM[0].duration,
        cc : setNumberChk(chapter),
        cp : setNumberChk(page),
        tp : setNumberChk(tot_page[setNumberChk(chapter)]),
        np : "",
        p_process : "subjseqPageUpdate"
      }
      ,   success : function(ajaxData) {

      }
      ,   error :  function(arg1, arg2) {
        alert("오류가 발생하여 플레이 할 수 없습니다.");
      }
    });
  }




});