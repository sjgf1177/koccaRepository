function setScript(){
  this.scriptArr = new Array();
  this.vttFile = null;


  (scriptVersion == "scroll") ? this.fnAjaxToXML() : this.fnAjaxToVtt();
};


setScript.prototype.fnAjaxToXML = function(){
  var _this = this;
  $.ajax({
    url : "./xml/" + strInitial + strChapter + "_" + strPage + ".xml",
    type : "GET",
    success : function(xml){ _this.fnSetScrollScript(xml); }
  });
}


setScript.prototype.fnSetScrollScript = function(_xml){
  var _script = $('.scriptWrap');
  $(_xml).find("part").each(function() {
    var tmpTrack = $('<div class="part" ><div class=teller><p class="text">'+ '</p></div>\
    <div class=track><p class="text">'+ $(this).find("script").text() + '</p></div></div>');
    _script.append(tmpTrack);
  });
  // _this.$content.append(_this.$script);
}

// setScript.prototype.fnSetScrollScript = function(_xml){
//   var _script = $('.scriptWrap');
//   $(_xml).find("part").each(function() {
//     var tmpTrack = $('<div class="part" ><div class=teller><p class="text">'+ $(this).find("teller").text() + ": " + '</p></div>\
//     <div class=track><p class="text">'+ $(this).find("script").text() + '</p></div></div>');
//     _script.append(tmpTrack);
//   });
//   // _this.$content.append(_this.$script);
//}

//ajax로 vtt 파일 읽어 들이기
setScript.prototype.fnAjaxToVtt = function(){
  var _this = this;
  $.ajax({
    url : "./vtt/" + strInitial + strChapter + "_" +  strPage + ".vtt",
    type : "GET",
    success : function(vtt){
      _this.fnVttToArr(vtt);
      _this.vttFile = vtt;
    }  //로드 완료시 vtt배열화 함수 호출
  });
}

//vtt배열화
setScript.prototype.fnVttToArr = function(vtt){
  var _this = this;
  var _tmpArr = vtt.split("\n\r\n"); //자막 단위 분리
  $.each(_tmpArr, function(n, elem) {
    _this.scriptArr[n] = {num:"WEBVTT FILE", time:"", teller:"", fullScript:"", script:"", script1:"", script2:""};
    if(n==0) return;
    var _lines = elem.split("\n");   //라인 단위 분리
    $.each(_lines, function(m, elem) {
        if(m == 0) _this.scriptArr[n].num = elem.replace(/\r/g,'');
        else if(m == 1) _this.scriptArr[n].time = elem.replace(/\r/g,'');
        else if(m == 2) _this.scriptArr[n].teller = elem.replace(/\r/g,'');
        else if(m == 3) _this.scriptArr[n].script1 = elem.replace(/\r/g,'');
        else if(m == 4) _this.scriptArr[n].script2 = elem.replace(/\r/g,'');
    });
    var tmpBol = _this.scriptArr[n].script2 == "" || _this.scriptArr[n].script2 == undefined;
    _this.scriptArr[n].script = (tmpBol) ? _this.scriptArr[n].script = _this.scriptArr[n].script1 : _this.scriptArr[n].script1 + '<br>' +_this.scriptArr[n].script2;
    _this.scriptArr[n].fullScript = _this.scriptArr[n].script1.replace(/ /gi, "")  + _this.scriptArr[n].script2.replace(/ /gi, "");


    (scriptType != "search") ? _this.fnSetFrameJump(n) : _this.fnSetSearchScript(n);
  });
  scriptScroll = new setScroll($('.scriptScroll'), $('.searchListWrap'), $('.searchList'), "vertical");
  _this.fnSetTwoLineScript();

}

//두줄 자막 세팅
setScript.prototype.fnSetTwoLineScript = function(){
  var _this = this;
  mainVideo.videoDOM.addEventListener('loadeddata', function() { // 영상 로드 완료시 배치
    mainVideo.$video.on('timeupdate', function() { // 영상 실시간
      var _bolTrackOn = false;  // 자막이 있는 부분인지 체크
      _this.scriptArr.forEach(function(arr) {
        if(arr.num=="WEBVTT FILE") return;
        var _startTime = secClock(arr.time.split("-->")[0]);
        var _endTime = secClock(arr.time.split("-->")[1]);
        if( _startTime <= mainVideo.curTime && mainVideo.curTime <= _endTime ){
          $('.teller').html(arr.teller + " : ");
          $('.scriptTxt').html(arr.script);
          _bolTrackOn = true;
        }
        else if(!_bolTrackOn && arr == _this.scriptArr[_this.scriptArr.length-1]){
          $('.teller').html("");
          $('.scriptTxt').html("");
        }
      });
    });
  });
}


//화면이동
setScript.prototype.fnSetFrameJump = function(n){
  var _this = this;
  $('.searchScript_top').hide();

  var tmpScript = "";
  tmpScript = _this.scriptArr[n].script1 + "\r"+ _this.scriptArr[n].script2+"&nbsp&nbsp";

  var $scriptPart = $('<span class="searchScript" id="'+ trackToDgi(_this.scriptArr[n].time) +'">'+ tmpScript +'</span>');
  if(_this.scriptArr[n].teller != _this.scriptArr[n-1].teller){
    $trackPart = $('<div class="part" id="'+ trackToDgi(_this.scriptArr[n].time) +'"><span class="searchTeller">'+ _this.scriptArr[n].teller +' : </span></div>');
    $scriptPart = $('<span class="searchScript" id="'+ trackToDgi(_this.scriptArr[n].time) +'">'+ tmpScript +'</span>');
    $trackPart.append($scriptPart);
    $('.searchList').append($trackPart);
    if(n!=0) $('.searchList').append($('<div class="searchLine"></div>'));
  }
  else{
    $trackPart.append($scriptPart);
    $('.searchList').append($trackPart);
  }

  $scriptPart.on('click',function(){
    mainVideo.videoDOM.currentTime = secClock($(this).attr("id"));
    mainVideo.videoDOM.play();
  });
}
//자막검색
setScript.prototype.fnSetSearchScript = function(n){
  var _this = this;
  var $trackPart = $('<div class="part part_'+n+'" id="'+ trackToDgi(_this.scriptArr[n].time) +'">\
                              <span class="searchTime">'+ trackToDgi(_this.scriptArr[n].time).split(":")[1] + ":" + trackToDgi(_this.scriptArr[n].time).split(":")[2].split(".")[0]  +' &nbsp&nbsp&nbsp&nbsp</span>\
                              <span class="searchTeller">'+ _this.scriptArr[n].teller +' &nbsp:&nbsp</span>\
                              <div class="searchScript">'+ _this.scriptArr[n].script  +'</div>\
                              </div>');
  $('.searchList').append($trackPart);
  if(n!=0) $('.searchList').append($('<div class="searchLine searchLine_'+n+'"></div>'));


  $trackPart.on('click',function(){
    mainVideo.videoDOM.currentTime = secClock($(this).attr("id"));
  });

  searchBtn = new setButton($('.searchBtn'), "검색", _this.tabIndex++, false);
  searchBtn.fnButtonOnClick(function(){
    var count = 1;
    $.each(_this.scriptArr, function(i, elem) {
      if(i==0) return;
      $(".part_"+i).hide();
      $('.searchLine_'+i).hide();
      if(elem.script.indexOf($(".searchInput").val()) != -1 ){
        $(".part_"+i).show();
        $('.searchLine_'+i).show();
      }
      else if(elem.fullScript.indexOf($(".searchInput").val()) != -1 ){
        $(".part_"+i).show();
        $('.searchLine_'+i).show();
      }
      else{
        count++;
        if(count==_this.scriptArr.length) alert("검색어에 일치하는 자막이 없습니다.");
      }
    });
    ( !mobileOS ) ? scriptScroll.fnScrollInit() : scriptScroll.$wrap.css('overflow-y','auto');
  });
}
