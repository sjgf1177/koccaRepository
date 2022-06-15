//application

/**
  * startContent		시작 시 호출 함수
 */
function startContent()
{
}
/**
 * endContent		종료 시 호출 함수
 */
function endContent()
{
}
/**
 * endVideo		영상 시작 시 호출 함수
 */
function playVideo()
{
  $('.nextAlert').hide();
  $('.nextAlert').css("right","20px");
}
/**
 * endVideo		영상 종료 시 호출 함수
 */
function endVideo()
{
  setAudio('end_video');
  $('.nextAlert').fadeIn(1500);
  $('.nextAlert').css("right","-0px");
}

function scaleChange(_target){

  var videoWidth = 1080;
  contentScale = $(window).width() / videoWidth;
  if( osType == "iPhone OS" || osType == "iPad" ){
    if(window.orientation==90 || window.orientation==-90){
      contentScale = screen.height  / videoWidth;

      if(screen.width == 414 && screen.height == 896) contentScale = contentScale * 0.89; //iPhone XR, iPhone XS Max
      else if(screen.width == 375 && screen.height == 812) contentScale = contentScale * 0.89; //iPhone XS, iPhone X
    }
    else {
      contentScale = screen.width / videoWidth;
    }
  }

  // contentScale =  (contentScale >= 1)? 1 : contentScale;
  _target.css("transform",'scale('+contentScale+')');
  _target.css("-webkit-transform",'scale('+contentScale+')');
  _target.css("-moz-transform",'scale('+contentScale+')');
  _target.css("-o-transform",'scale('+contentScale+')');
}



// 버튼 생성
// input
//  $target : 해당 버튼
//  text : 타이틀
//  tab : depth
//  func : 버튼 작동 함수
function fnSetSubButton($target, text, tab, func)
{
  $target.attr("title", text);
  $target.attr("tabIndex", tab);
  $target.off().on('click', function() {
      $(this).removeClass("hover");
      try { func(); }
      catch(exception) { console.log(exception); }
  });
}
// 타이틀 생성
// input
//  $target : 해당 버튼
//  text : 타이틀
//  tab : depth
function fnsetSubTitle($target, text, tab){
  $target.attr("title", text);
  $target.attr("tabIndex", tab);
}

/**
 * movePage		페이지 이동
 * @param _page		이동할 페이지
 * @param [_data]		추가로 보낼 내용
 */
 /**
  * movePage		페이지 이동
  * @param _page		이동할 페이지
  * @param [_data]		추가로 보낼 내용
  */
 function movePage(_page, _data)
 {
 	location.href =  itostr(_page) + ".html";
 }

function progressbarLock()
{
	return false;
}