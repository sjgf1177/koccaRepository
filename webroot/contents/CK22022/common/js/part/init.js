var mobileClickBol = false;

$(document).ready(function() {
  var _this = this;
  _this.$contents = $('.contents');
  controller = new setController($('.controller'));
  mainVideo = new setVideo($('.video1'));
  script = new setScript();
  menu = new setMenu();





  if( mobileOS )
  {
    $('.down').hide();
    $('.mobile_play_btn').show();
    $('.sound').hide();
    $('.soundLineWrap').hide();
    $('.fullscreen').hide();
    $('.lock').hide();
  }

  $(window).resize(function() {
    scaleChange($('.chapterMenuImg'));
    scaleChange($('.subMenuImg'));
    scaleChange($('.mainMenuImg'));
  });
  if(osType == "iPhone OS"){
    window.onorientationchange = function() {
      scaleChange($('.chapterMenuImg'));
      scaleChange($('.subMenuImg'));
      scaleChange($('.mainMenuImg'));
    }
  }

  $(document).on('MSFullscreenChange webkitfullscreenchange mozfullscreenchange fullscreenchange', function(e) { 
    fullBol = !fullBol; 
    ($('.fullscreen').attr("class").indexOf("toggle") != -1) ? $('.fullscreen').removeClass("toggle") : $('.fullscreen').addClass("toggle");
  });

  if(mobileOS)
  {
    setInterval(function(){ mobileUiHideCheck(); }, 2000 ); 
    $(document).on('mousedown mousemove',function(){
      mobileClickBol = false;
      if(totalUiHide)
      {
        $('.controller').css("bottom", "0px" );
        $('.chapterMenuImg').css("top", "0px" );
        $('.subMenuImg').css("top", "0px" );
        $('.mainMenuImg').css("top", "0px" );
      }
    });
  }
  else
  {
    $(document).on('mouseleave',function(){
      if(totalUiHide)
      {
        $('.controller').css("bottom", "-120px" );
        $('.chapterMenuImg').css("top", "-150px" );
        $('.subMenuImg').css("top", "-150px" );
        $('.mainMenuImg').css("top", "-150px" );
      }
    });
    $(document).on('mouseenter',function(){
      if(totalUiHide)
      {
        $('.controller').css("bottom", "0px" );
        $('.chapterMenuImg').css("top", "0px" );
        $('.subMenuImg').css("top", "0px" );
        $('.mainMenuImg').css("top", "0px" );
      }
    });
  }

  

});


// $("body").contextmenu( function() {return false;});

$(document).keydown(function(keyEvent) {
      timeValue = 10;
      getKey = keyEvent.keyCode;
      switch (getKey) {
        case 37: //left
          if(Number(mainVideo.videoDOM.currentTime) - timeValue <= 0) mainVideo.videoDOM.currentTime = 0;
          else mainVideo.videoDOM.currentTime = Number(mainVideo.videoDOM.currentTime) - timeValue;
          break;
        case 39: //right
          if(Number(mainVideo.videoDOM.currentTime) + timeValue >= Number(mainVideo.videoDOM.duration)) mainVideo.videoDOM.currentTime = Number(mainVideo.videoDOM.duration);
          else mainVideo.videoDOM.currentTime = Number(mainVideo.videoDOM.currentTime) + timeValue;
          break;
      }
});




function mobileUiHideCheck()
{
  if(mobileClickBol)
  {
    if(totalUiHide)
    {
      $('.controller').css("bottom", "-110px" );
      $('.chapterMenuImg').css("top", "-150px" );
      $('.subMenuImg').css("top", "-150px" );
      $('.mainMenuImg').css("top", "-150px" );
    }
  }
  else{
    mobileClickBol = true;
  }

}