$(document).ready(function(){


  var videoDOM = $("#jp_video_0");
  var nPage = "";

  /* 자막 */
  setTimeout(function() {


    if (setNumberChk(page) < 10){
      nPage = "0" + (setNumberChk(page) + 1) + ".html";
    }else{
      nPage = (setNumberChk(page) + 1) + ".html";
    }

    if(setNumberChk(page) == setNumberChk(tot_page[setNumberChk(chapter)])) {
      nPage = "01.html";
    }
	
	console.log("ct : " + videoDOM[0].currentTime);
	console.log("tt : " + videoDOM[0].duration);
	console.log("cc : " + setNumberChk(chapter));
	console.log("cp : " + setNumberChk(page));
	console.log("tp : " + setNumberChk(tot_page[setNumberChk(chapter)]));
	console.log("np : " + nPage);

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
        //alert("오류가 발생하여 플레이 할 수 없습니다....");
      }		
	  /*
	, error:function(request,status,error){
        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       }	
*/	   
    });

  },500);

  $('.jp-script').bind('click',function(){

    if(!$(this).is('.on')){
      //자막이 꺼져있을때
      $(".jp-jplayer").addClass('script_active');
      $(this).addClass('on');
      $('.scriptWrap').show();
      //console.log('자막켜짐');


    } else {

      //자막이 켜져있을때
      $(this).removeClass('on');
      $(".jp-jplayer").removeClass('script_active');
      $('.scriptWrap').hide();
      //console.log('자막꺼짐');
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
        alert("오류가 발생하여 플레이 할 수 없습니다..");
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
        alert("오류가 발생하여 플레이 할 수 없습니다...");
      }
    });
  }




});