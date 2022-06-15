
function getURL(_lastIndex)
{
  var url = document.location.href.split("/");
  return url[url.length - _lastIndex - 1].split(".")[0];
}

// 디지털 시간 변환
// input
//  time : 미디어 시간
// ouput
//  digital: 디지털시간 00 : 00
function clock(_time)
{
  if(_time == "undefined" || _time == undefined || _time == "NaN" || _time == NaN) return "00:00";
  var min = Math.floor(_time / 60);
  var sec = Math.floor(_time % 60);
  var digital = itostr(min) + ":" + itostr(sec);
  return digital;
}

// 디지털을 초로 변환
// input
//  time : 미디어 시간
// ouput
//  secTime : 초 시간
function secClock(_time)
{
  try {
    var hour = _time.split(":")[0]
    var min =_time.split(":")[1]
    var sec =_time.split(":")[2]
    var secTime = (hour * 3600) + (min * 60) + (sec * 1);
    return secTime;
  }
  catch(exception) { console.log("secClock error"); }
}

// 트랙시간 00:00로 변환
// input
//  time : 트랙 시간
// ouput
//  secTime : 디지털 시간
function trackToDgi(_trackTime)
{

  var _time = _trackTime.split("-->")[0]
  try {
    var hour = _time.split(":")[0]
    var min =_time.split(":")[1]
    var sec =_time.split(":")[2]
    var secTime = hour + ":" + min + ":" + itostr(sec * 1);
    return secTime;
  }
  catch(exception) { console.log("trackToDgi error"); }
}



// i to str
function itostr(_num)
{
  return _num < 10 ? "0" + _num : _num;
}

// os 탐지
function getOSType()
{
  var ua = navigator.userAgent;

  if (ua.indexOf("Windows NT 5.1") != -1) return "windows_xp";
  if (ua.indexOf("Windows NT 6.1") != -1) return "windows_7";
  if (ua.indexOf("Windows NT 6.2") != -1) return "windows_8";
  if (ua.indexOf("Windows NT 6.3") != -1) return "windows_8.1";
  if (ua.indexOf("Windows NT 10.0") != -1) return "windows_10";
  if (ua.indexOf("Windows") != -1) return "windows";

  if (ua.indexOf("Android") != -1) return "Android OS";
  if (ua.indexOf("iPhone") != -1) return "iPhone OS";
  if (ua.indexOf("iPad") != -1) return "iPad";
}
// Browser 탐지
function getBrowserType()
{
  var _ua = navigator.userAgent;
  /* IE7,8,9,10,11 */
  if (navigator.appName == 'Microsoft Internet Explorer' || _ua.indexOf("rv:11.0") != -1) {
      var rv = -1;
      var trident = _ua.match(/Trident\/(\d.\d)/i);

      //ie11에서는 MSIE토큰이 제거되고 rv:11 토큰으로 수정됨 (Trident표기는 유지)
      if(trident != null && trident[1]  == "7.0") return rv = "IE" + 11;
      if(trident != null && trident[1]  == "6.0") return rv = "IE" + 10;
      if(trident != null && trident[1]  == "5.0") return rv = "IE" + 9;
      if(trident != null && trident[1]  == "4.0") return rv = "IE" + 8;
      if(trident == null) return rv = "IE" + 7;

      var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
      if (re.exec(_ua) != null) rv = parseFloat(RegExp.$1)
      return rv;
  }

  var agt = _ua.toLowerCase();
  if (agt.indexOf("chrome") != -1) return 'Chrome';
  if (agt.indexOf("opera") != -1) return 'Opera';
  if (agt.indexOf("staroffice") != -1) return 'Star Office';
  if (agt.indexOf("webtv") != -1) return 'WebTV';
  if (agt.indexOf("beonex") != -1) return 'Beonex';
  if (agt.indexOf("chimera") != -1) return 'Chimera';
  if (agt.indexOf("netpositive") != -1) return 'NetPositive';
  if (agt.indexOf("phoenix") != -1) return 'Phoenix';
  if (agt.indexOf("firefox") != -1) return 'Firefox';
  if (agt.indexOf("safari") != -1) return 'Safari';
  if (agt.indexOf("skipstone") != -1) return 'SkipStone';
  if (agt.indexOf("netscape") != -1) return 'Netscape';
  if (agt.indexOf("mozilla/5.0") != -1) return 'Mozilla';
}




function getCookie( cookieName ){

  var search = cookieName + "=";
  var cookie = document.cookie;
  if( cookie.length > 0 )		{
    startIndex = cookie.indexOf( cookieName );
    if( startIndex != -1 )
    {
      startIndex += cookieName.length;
      endIndex = cookie.indexOf( ";", startIndex );
      if( endIndex == -1) endIndex = cookie.length;
      return unescape( cookie.substring( startIndex + 1, endIndex ) );
  }
  else{
      return false;
    }
  }else{
      return false;
  }
}

function setCookie( cookieName, cookieValue, expireDate )
{
  var today = new Date();
  today.setDate( today.getDate() + parseInt( expireDate ) );
  document.cookie = cookieName + "=" + escape( cookieValue ) + "; path=/; expires=" + today.toGMTString() + ";"
}

