
function setCookieClear(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") +
    ((secure) ? "; secure" : "");
}


function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.togmtstring() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") +
    ((secure) ? "; secure" : "");
}


function getCookieval(offset) {
  var endstr = document.cookie.indexOf (";", offset);
  if (endstr == -1)
    endstr = document.cookie.length;
  return unescape(document.cookie.substring(offset, endstr));
}


function getCookie(name) {
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen) {
	    var j = i + alen;
	    if (document.cookie.substring(i, j) == arg)
	      return getCookieval (j);
	    i = document.cookie.indexOf(" ", i) + 1;
	    if (i == 0) break; 
	}
	return null;
}

function Reset_Cookie( name ) {
	var name = name
	setCookie( name, null, null, "/")

}


/*
  쿠키값 초기화
*/

function InitCookie() {
    setCookie("jindo", "000 000 000 000 000 000 000 000 000 000 000", null, "/");            
                              
}


/*
   해당하는 쿠키값에서 3자리의 문자를 가지고 와서 점수로 변환 출력
*/

function getCookieNum(name, num) {
  var space = " ";	
	var strvalue = getCookie( name )
	var chknum = num - 1;
	
	
   	var stext = strvalue.split(space);
   	
	var rtnstr = stext[ chknum ];
	
   return rtnstr;
	
}


/*
  점수별 쿠키값 입력

*/

function setCookieValue(name, num, jum) {
  var space = " ";	
   var name = name
   var chkStr = jum     
   var strvalue = getCookie( name );
   var chknum = num - 1;
   var setText = ""
  
   var stext = strvalue.split(space);
   
   stext[ chknum ] = jum;
  
  for( i=0; i < 11; i++ ){
  	
  	
   setText = setText + stext[ i ] + " "
  
  
  }
  
  
   setCookie(name, setText, null, "/");
   
}