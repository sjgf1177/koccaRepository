function setMenu(target, wrap, list, type){
  this.$scroll = target;
  this.$bar = null;
  this.$wrap = wrap;
  this.$list = list;
  this.$type = type;

  this.lastIndex = 30;

  this.fnAjaxToXml();


}


setMenu.prototype.fnAjaxToXml = function(){
  var _this = this;
  $.ajax({
    url : "./xml/list.xml",
    type : "GET",
    success : function(xml){
      (menuType != "time") ? _this.fnSetIndexPage(xml) : _this.fnSetIndexTime(xml);
    }
  });
}

//메뉴 페이지이동
setMenu.prototype.fnSetIndexPage = function(_xml){
  var _this = this;
  var mainPage_1 = 0;
  var mainPage_2 = 0;
  $(_xml).find("index").each(function() {

    //main주제 설정
    $(this).children().each(function(index) {
      var mainPage = parseInt($(this).attr('page'));
      var curMainPage = $(this).attr("page");
      var nextMainPage = $(this).next().attr("page");
      var mainTitle = $(this).attr('title');
      if(index == 1) mainPage_1 = mainPage;
      else if(index == 2) mainPage_2 = mainPage;
      var mainBtn = $('<button class="mainTitle">'+$(this).attr('title')+'</button>');

      $(".indexList").append(mainBtn);
      $(".indexList").append($('</br>'));

      if($(this).next().attr("page") == undefined) nextMainPage = totalPage + 1;
      if(curMainPage <=curPage && nextMainPage > curPage ) mainBtn.addClass("toggle");

      fnSetSubButton(mainBtn,mainTitle,_this.lastIndex++,function(){ movePage(mainPage, "menu"); });
      //sub주제 설정
      $(this).children().each(function() {
        var subPage = parseInt($(this).attr('page'));
        var curSubPage = $(this).attr("page");
        var nextSubPage = $(this).next().attr("page");
        var subTitle = $(this).text();
        var subBtn = $('<button class="subTitle">'+subTitle+'</button>');

        $(".indexList").append(subBtn);
        $(".indexList").append($('</br>'));

        if($(this).next().attr("page")==undefined) nextSubPage = nextMainPage;
        if(curSubPage <=curPage && nextSubPage > curPage ) subBtn.addClass("toggle");

        fnSetSubButton(subBtn,subTitle,_this.lastIndex++,function(){ movePage(subPage, "menu"); });
      });
    });

  });
  
  scaleChange($('.chapterMenuImg'));
  scaleChange($('.subMenuImg'));
  scaleChange($('.mainMenuImg'));
}



//메뉴 시간이동
setMenu.prototype.fnSetIndexTime = function(_xml){
var _this = this;
$(_xml).find("topic").each(function() {
    $(this).children().each(function() {
      if($(this).attr("title")=="main")
      {
        var mainTitle = $('<div class="mainTitle">'+$(this).text()+'</div></br>');
        var mainPage = $(this).attr('time');
        $(".indexList").append(mainTitle);
      }
      else if($(this).attr("title")=="line") $(".indexList").append($('<div class="menuLine"></div>'));
      else
      {
        var subTime = $(this).attr('time');
        var subTitle = $('<button class="subTitle" id="'+subTime+'">'+$(this).text()+'</button></br>');
        _this.fnSetTimeChecker(subTime)
        $(".indexList").append(subTitle);
        fnSetSubButton(subTitle,$(this).text(),lastIndex++,function(){
            mainVideo.videoDOM.currentTime = subTime;
            mainVideo.videoDOM.play();
        });
      }
    });
  });
}


// 시간에 따른 메뉴 버튼 toggle
setMenu.prototype.fnSetTimeChecker = function(time){
  var _this = this;
  var _time = time * 1;
  mainVideo.$video.on('timeupdate', function() {
    if( mainVideo.videoDOM.currentTime >= _time ){
      $('.indexList').find("#"+time).addClass("toggle")
      $('.indexList').find("#"+time).closest(".indexList").find('button').not($('.indexList').find("#"+time)).removeClass("toggle");
    }
  });

}
