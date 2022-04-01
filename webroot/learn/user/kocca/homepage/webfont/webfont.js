//<[CDATA[

/**
*컴퓨터에 activeX설치되어있는지 여부를 확인 
*설치되있을시 return true
*/

function isInstalledActiveX() {/*{{{*/
	var isInstall = false;
	try {
		var obj = new ActiveXObject("ClearAdjust.CTAdjust.1");
	
		if(obj)
			isInstall = true;
		else
			isInstall = false;
	} catch(ex) {
		isInstall = false;
	}
	var ua = navigator.userAgent;
	var un = navigator.appName;
	if(un == 'Microsoft Internet Explorer') {
		//IE .
		if(ua.indexOf('MSIE 7.0') > 0) {
			isInstall = true;		
		}
	}
    return isInstall;
}/*}}}*/
/**
*onload 이벤트에 function을 추가하는 function
*/
function addOnload(func) {/*{{{*/
    if(typeof window.onload == 'function') {
        loadfunc = window.onload;
        window.onload = function() {
            loadfunc();
            func();
        }
    } else {
        window.onload = func;    
    }
}/*}}}*/

/**
*onload시에 동작하여 필요한 이밴트 설정
*/
function startUp() {/*{{{*/
    var inst_btn = document.getElementById('inst_active');
    inst_btn.onclick = inst_activex;
    var no_inst = document.getElementById('no_inst');
    //no_inst.onclick = closePanel;
	no_inst.onclick = removeFor;
    var period_rm = document.getElementById('period_rm');
    period_rm.onclick = removeFor;
    var adjust_font = document.getElementById('adjust_font');
    adjust_font.onclick = adjustFontExec;
	//adjust_font.onclick = removeButton1;
	var cancel_font = document.getElementById('cancel_font');
    cancel_font.onclick =  cancelFont;
	//cancel_font.onclick = removeButton2;
	
}/*}}}*/

/**
*activeX설치여부 및 설치의 하느냐 마느냐의 선택에 따라 동작을 다르게 한다
*/
function checkActiveX() {/*{{{*/
    if(isInstalledActiveX()) {
        //이미 activeX설치가 되있을 시에 클리어타입으로 전환
        var obj = new ActiveXObject("ClearAdjust.CTAdjust");
        if(obj.SmoothType != 2) obj.SmoothType = 2;
        adjustFont();
    } else {
        // 안보기 첵크를 안했으면 activeX 설치관련 창을 띄운다
        if(getCookie('period_rm') != 'yes') {
            document.getElementById('quest_panel').style.display = 'block';
        }
        // activeX를 설치한다고 했을시에 리로드시 창을 띄우지 않고 activeX로드
        if(getCookie('inst') == 'yes') {
            document.getElementById("ax_container").innerHTML = 
                '<OBJECT id="CTAdjust" ' + 
                'codeBase="http://download.microsoft.com/download/7/E/6/7E6A8567-DFE4-4624-87C3-163549BE2704/clearadj.cab#version=1,0,0,4" ' +
                'classid="CLSID:DE22A7AB-A739-4C58-AD52-21F9CD6306B7" VIEWASTEXT> ' + 
                '</OBJECT>';
        deleteCookie('inst');
		adjustFontExec();
        }
    }
}/*}}}*/

// activeX설치 클릭시에 activeX를 로드
function inst_activex() {/*{{{*/
    document.getElementById("ax_container").innerHTML = 
        '<OBJECT id="CTAdjust" ' + 
        'codeBase="http://download.microsoft.com/download/7/E/6/7E6A8567-DFE4-4624-87C3-163549BE2704/clearadj.cab#version=1,0,0,4" ' +
        'classid="CLSID:DE22A7AB-A739-4C58-AD52-21F9CD6306B7" VIEWASTEXT> ' + 
        '</OBJECT>';
    setCookie('inst', 'yes', 1);
    CTAdjust.onerror = function() { 
        alert('activex를 설치해주세요');
    }
    return false;
}/*}}}*/

/*
* activeX설치에 관한 창 닫기, 지금은 단순히 보이지 않게 할 뿐
* 입맛대로 처리하면 됨 :P
*/
function closePanel() {/*{{{*/
    document.getElementById('quest_panel').style.display = 'none';
    return false;
}/*}}}*/

/*
* X일간 안보기에 관한 쿠키 설정
*/
function removeFor() {/*{{{*/
    var length = document.getElementById('length');
    var period = length.getAttribute('value');
    setCookie('period_rm', 'yes', period);
    document.getElementById('quest_panel').style.display = 'none';
	//setCookie('fontRm', 'yes', period);
	document.getElementById('adjust_font').style.display = 'none';

}/*}}}*/

/*
* activeX 를 사용하여 랜더링 형식 바꾸기
* SmoothType1 = grayscale
* SmoothType2 = cleartype
*/
function check_ctadjust() {/*{{{*/
    var CTAdjust = document.getElementById('CTAdjust');
    var ua = navigator.userAgent;
    var un = navigator.appName;

    if(CTAdjust && (CTAdjust.SmoothType == 0 || CTAdjust.SmoothType == 1 || CTAdjust.SmoothType ==2)) {
        //$("#msg").html('Microsoft에서 제공되는 Cleartype Tuner 프로그램이 설치되었습니다.');
        if(ua.indexOf('Windows NT 5.1') > 0) {
            //XP인 경우.
            if(CTAdjust.SmoothType == 2) {
               adjustFont();
            } else {
               CTAdjust.SmoothType = 2; 
               adjustFont();
            }
        } else {
               CTAdjust.SmoothType = 1; 
               adjustFont();
        }
        closePanel();
    } else {
        timer = setTimeout("check_ctadjust()", 500);
    }
}/*}}}*/

/*
* onload시에 동작, 상황에 따라 동작을 바꾸기 가능 :P
*/
function init() {/*{{{*/
    var ua = navigator.userAgent;
    var un = navigator.appName;

    if(un == 'Microsoft Internet Explorer') {
        //IE인 경우.
        if(ua.indexOf('MSIE 7.0') > 0) {
            //IE7.0인 경우-AX 설치할필요없음. 
            /* 0319 
            *document.getElementById('info').innerHTML = document.getElementById('msie7').innerHTML;  
            *예전에 ie7일때 메시지 띄울때 쓰던 것, 지금은 해당 엘리먼트가 없어서 사용시 에러발생때문에 지웠음
            */
            /*0319 ie7 일시에 웹폰을 바로 적용시킨다.*/
            adjustFont();
        } else {
            //IE7.0이하버젼.

            if(ua.indexOf('Windows 98') > 0) {
                //98
                document.location.href = 'index_win98.html';
                return false;
            } else if(ua.indexOf('Windows NT 5.1') > 0) {
                checkActiveX();
            } else if(ua.indexOf('Windows NT 5.0')) {
                checkActiveX();
            } else {
                //other os.
            }


        }

    } else {
        //document.getElementById('quest_panel').style.display = 'block';
        //IE가 아닌경우.
        //$('#info').html($('#not_ie').html());
        //document.location.href = 'index_non_ie.html';
        //return false;
    }

    check_ctadjust();

}/*}}}*/

/*
* 웹폰트 적용 클릭시 웹폰을 적용시킨다, 현재는
* 특정 엘리먼트에 class를 붙혀서 CSS로 폰트를 적용시키는 방식 사용중
*/
function adjustFont() {/*{{{*/

	if(getCookie('fontRm') == 'no'){
		//nd추가 폰트 해제쿠키 체크..
		 document.getElementById('cancel_font').style.display = 'none';
		 document.getElementById('adjust_font').style.display = 'block';
	} else if(getCookie('fontRm') == 'yes'){
		adjustFontExec();	
		document.getElementById('adjust_font').style.display = 'none';
		document.getElementById('cancel_font').style.display = 'block';
	}else{
		setCookie('fontRm', 'yes', 1);
		adjustFontExec();
		document.getElementById('cancel_font').style.display = 'block';
		document.getElementById('adjust_font').style.display = 'none';
	}
}
/*}}}*/

/*
* 웹폰트 실행
*/
function adjustFontExec(){

	setCookie('fontRm', 'yes', 1);	
		
	 if(isInstalledActiveX()) {
		//var font_panel = document.getElementById('font_panel');
	   // font_panel.setAttribute('className', 'webfont');
		/*제목*/var font_title	   = document.getElementById('font_title');
		font_title.setAttribute('className', 'webfont1');
		/*부제목*/var font_subtitle  = document.getElementById('font_subtitle');
		font_subtitle.setAttribute('className', 'webfont2');
		/*날짜*/var font_date	   = document.getElementById('font_date');
		font_date.setAttribute('className', 'webfont3');
		/*이메일*/var font_email	   = document.getElementById('font_email');
		font_email.setAttribute('className', 'webfont4');
		/*보기*/var articleBody	   = document.getElementById('articleBody');
		articleBody.setAttribute('className', 'webfont5');
		/*기자*/var font_reporter	   = document.getElementById('font_reporter');
		font_reporter.setAttribute('className', 'webfont6');
		/*카피*/var font_copy	   = document.getElementById('font_copy');
		font_copy.setAttribute('className', 'webfont7');

		if( document.getElementById('font_imgdown') ){
			 /*이미지밑에글*/var font_imgdown   = document.getElementById('font_imgdown');
			font_imgdown.setAttribute('className', 'webfont8');
		}
		 document.getElementById('adjust_font').style.display = 'none';
		 document.getElementById('cancel_font').style.display = 'block';
	
	} else {
		document.getElementById('quest_panel').style.display = 'block';
	}
		
		return false;
}
/*
*웹폰트 적용 해제, 현재는 웹폰트를 적용하는 클래스를 삭제하는것으로서 동작
*/
function cancelFont() {/*{{{*/
  
	/*제목*/var font_title	   = document.getElementById('font_title');
	font_title.setAttribute('className', 'webfont10');
	/*부제목*/var font_subtitle  = document.getElementById('font_subtitle');
	font_subtitle.setAttribute('className', 'webfont11');
	/*날짜*/var font_date	   = document.getElementById('font_date');
	font_date.setAttribute('className', 'webfont12');
	/*이메일*/var font_email	   = document.getElementById('font_email');
	font_email.setAttribute('className', 'webfont13');
	/*보기*/var articleBody	   = document.getElementById('articleBody');
	articleBody.setAttribute('className', 'webfont14');
	/*기자*/var font_reporter	   = document.getElementById('font_reporter');
	font_reporter.setAttribute('className', 'webfont15');
	/*카피*/var font_copy	   = document.getElementById('font_copy');
	font_copy.setAttribute('className', 'webfont16');
   /*이미지밑에글*/
   if( document.getElementById('font_imgdown') ){
	   var font_imgdown   = document.getElementById('font_imgdown');
		font_imgdown.setAttribute('className', 'webfont17');
   }

	document.getElementById('adjust_font').style.display = 'block';
	document.getElementById('cancel_font').style.display = 'none';
	setCookie('fontRm', 'no', 1);

    return false;
}/*}}}*/

/*
function buttonInsFun(){
	if(getCookie('fontRm') == 'no'){
		
	var cancel_font = document.getElementById('adjust_font').style.display = 'block';

		//document.getElementById("buttonINS").innerHTML = '<td><a href=\"#\" id=\"adjust_font\" >
		//<img src=\"/webfont/images/font_ok.gif\" width=\"65\" height=\"16\" border=\"0\"></a></td>
	//</td>';

	} else {
	
	var adjust_font = document.getElementById('cancel_font').style.display = 'block';

		//buttonINS.InnerHTML = "<td><a href=+"#"+ id=+"cancel_font"+ >
		//<img src=\"/webfont/images/font_no.gif\" width=\"65\" height=\"16\" border=\"0\"></a></td>
	//</td>";
	}
}
*/
//if(getCookie('inst') != 'yes'){
	
	//addOnload(buttonInsFun);
	//addOnload(startUp);
	//addOnload(init);
//}

//]]>
