function setButton(target, text, tab, toggle){
  this.$target = target;
  this.$text = text;
  this.$tab = tab;
  this.$toggle = toggle;
  this.fnButtonInit();
}


//버튼 초기화
//  선택자에 타이틀 및 탭인덱스 부여
setButton.prototype.fnButtonInit = function(){
  this.$target.attr("title", this.$text);
  this.$target.attr("tabIndex", this.$tab);
}

//버튼 클릭 지정 함수
setButton.prototype.fnButtonOnClick = function(func){
  var _this = this;
  var _target = this.$target;
  _target.off().on('click', function() {
    try { func(); }
    catch(exception) { console.log(exception); }
    if(_this.$toggle)  (_target.attr("class").indexOf("toggle") != -1) ? _target.removeClass("toggle") : _target.addClass("toggle");
  });
  // console.log(_target.children(".tooltips"))
  if(!mobileOS){
    _target.on('mouseenter', function() {
      $(this).addClass("hover");
      $(this).closest("div").children(".tooltips").fadeIn(100);
    });
    _target.on('mouseleave focusout', function() {
      $(this).removeClass("hover");
      $(this).closest("div").children(".tooltips").fadeOut(1);
    });
  }

}

//스크랩 버튼 클릭 함수
setButton.prototype.fnScrapOnClick = function(){
  var _this = this;
  var _target = this.$target;
  var _page = _target.attr("class").split("box_")[1]

  _target.off().on('click', function() {
    try {
      if(controller.scriptToggle){
        scrapCookieList =  scrapCookieList.replace(_page,"");
        _target.hide();
      }
      else{
        location.href = itostr(parseInt(_page)) + ".html";
      }
    }
    catch(exception) { console.log("The scrapButton is not registered."); }
  });
}
