/******************************************
	Mini Calendar (2002. 06. 15)
*******************************************/

var target;
var s1,s2,s3;
var week;

/**
 *	달력 display
 */
function MiniCal(juche, toDate, sWeek)
{
	target=juche;
	week = sWeek;
	

	x = (document.layers) ? loc.pageX : event.clientX+ document.body.scrollLeft-100;
	y = (document.layers) ? loc.pageY : event.clientY+ document.body.scrollTop-100;
	if(document.all.minical.length > 1){
		minical[0].style.pixelTop	= y+10;
		minical[0].style.pixelLeft	= x-10;
		minical[0].style.display = (minical[0].style.display == "block") ? "none" : "block";
	}else{
		minical.style.pixelTop	= y+10;
		minical.style.pixelLeft	= x-10;
		minical.style.display = (minical.style.display == "block") ? "none" : "block";
	}
	if(toDate != "") {
		var arr = toDate.split(".").join("");
				
		Show_cal(arr.substring(0,4),arr.substring(4,6),arr.substring(6,8));
	}else {
		var toDay = new Date();
		var year = toDay.getYear();
		var month = toDay.getMonth() + 1;
		var day	  = toDay.getDate();
		
		if(month.length == 1) month = "0" + month;
		if(day.length == 1) day = "0" + day;
		
		Show_cal(year, month, day);
	}
	
	
	//Show_cal(toDate.substring(0,4),toDate.substring(4,6),toDate.substring(6,8));
	//Show_cal(toDate.substring(0,4),toDate.substring(4,6),toDate.substring(6,8));
}





var stime
function doOver() {
	var el = window.event.srcElement;
	cal_Day = el.title;

	if (cal_Day.length > 7) {
		el.style.borderTopColor = el.style.borderLeftColor = "buttonhighlight";
		el.style.borderRightColor = el.style.borderBottomColor = "buttonshadow";
	}
	window.clearTimeout(stime);
}

function doClick() {
	cal_Day = window.event.srcElement.title;
	window.event.srcElement.style.borderColor = "#990000";
	if (cal_Day.length > 7) {
		getFixed(cal_Day);
		target.value = s1 + "." + s2 + "." + s3;		
		
		//added by dklee 2004.12.20 요일 정보를 닫기 위해.
		try {
			var temp = "";
			if(week != null) {
				
				var date = target.value.split(".").join("");
				var day = new Date(  parseInt(date.substring(0,4)), parseInt(date.substring(4,6))-1, parseInt(date.substring(6,8))).getDay();
				if (day == 0) temp = "일";
				if (day == 1) temp = "월";
				if (day == 2) temp = "화";
				if (day == 3) temp = "수";
				if (day == 4) temp = "목";
				if (day == 5) temp = "금";
				if (day == 6) temp = "토";
				week.value =temp;
			}
		} catch(e) {
		}
	}
}

function doOut() {
	var el = window.event.fromElement;
	cal_Day = el.title;

	if (cal_Day.length > 7) {
		el.style.borderColor = "white";
	}
	if(document.all.minical.length > 1){
		stime=window.setTimeout("minical[0].style.display='none';", 200);
	}else{
		stime=window.setTimeout("minical.style.display='none';", 200);
	}
}

function getFixed(sDate){
	var s;
	var arr;

	s = new String(sDate);
	arr = s.split("-");
	if(arr.length == 3){
		s = arr[0] + "-";
		if(arr[1].length == 1) arr[1] = "0" + arr[1];
		s1 = arr[0];
		s = s + arr[1] + "-";
		s2 = arr[1];
		if(arr[2].length == 1) arr[2] = "0" + arr[2];
		s3 = arr[2];
		s = s + arr[2];
	}else{
		s = sDate;
	}
	return s;
}

function Show_cal(sYear,sMonth,sDay)
{
	if(sYear<1997) return;

	if(document.all.minical.length > 1){
		document.all.minical[0].innerHTML="";
	}else{
		document.all.minical.innerHTML="";
	}
	Cal_HTML = "";

	//var datToday=new Date().toLocaleString();
	var datToday=new Date();

	intThisYear = sYear;
	intThisMonth = sMonth;
	intThisDay = sDay;

	if (intThisDay==0) intThisDay = datToday.getDay();
	if (intThisMonth==0) intThisMonth = datToday.getMonth();
	if (intThisYear==0) intThisYear = datToday.getYear();

	if (intThisMonth == 1)
	{
		intPrevYear=intThisYear-1;
		intPrevMonth=12;
		intNextYear=intThisYear;
		intNextMonth=2;
	}
	else if (intThisMonth==12)
	{
		intPrevYear=intThisYear;
		intPrevMonth=11;
		intNextYear=(parseInt(intThisYear) + 1);
		intNextMonth=1;
	}
	else
	{
		intPrevYear=intThisYear;
		intPrevMonth=intThisMonth -1;
		intNextYear=intThisYear;
		intNextMonth=Math.ceil(intThisMonth) + 1;
	}

	NowThisYear = sYear;
	NowThisMonth = sMonth;
	NowThisDay = sDay;

	if (NowThisDay==0) NowThisDay = datToday.getDay();
	if (NowThisMonth==0) NowThisMonth = datToday.getMonth();
	if (NowThisYear==0) NowThisYear = datToday.getYear();


	var first_date=new Date(intThisYear,intThisMonth-1,1)
	intFirstWeekday=first_date.getDay();
	intFirstWeekday++

	intSecondWeekDay=intFirstWeekday
	intThirdWeekDay=intFirstWeekday

	datThisDay= intThisYear.toString() +  "-" + intThisMonth.toString() + "-" + intThisDay.toString();
	intThisWeekday=first_date.getDay();
	intThisWeekday++

	if (intThisWeekday == 1) varThisWeekday = "일";
	if (intThisWeekday == 2) varThisWeekday = "월";
	if (intThisWeekday == 3) varThisWeekday = "화";
	if (intThisWeekday == 4) varThisWeekday = "수";
	if (intThisWeekday == 5) varThisWeekday = "목";
	if (intThisWeekday == 6) varThisWeekday = "금";
	if (intThisWeekday == 7) varThisWeekday = "토";

	intPrintDay=1;
	secondPrintDay=1;
	thirdPrintDay=1;

	Stop_Flag=0;

	if (intThisMonth == 4 || intThisMonth==6 || intThisMonth==9 || intThisMonth==11)
	{
		intLastDay=30;
	}
	else if (intThisMonth==2 && !(intThisYear % 4 == 0))
	{
		intLastDay=28;
	}
	else if (intThisMonth==2 && intThisYear % 4 == 0)
	{
		if (intThisYear % 100 == 0)
		{
			if (intThisYear % 400 == 0)
				intLastDay=29;
			else
				intLastDay=28;
		}
		else
		{
			intLastDay=29;
		}
	}
	else
	{
		intLastDay=31;
	}


	if (intPrevMonth==4 || intPrevMonth==6 || intPrevMonth==9 || intPrevMonth==11)
		intPrevLastDay=30;
	else if (intPrevMonth==2 &&  !(intPrevYear % 4 == 0))
		intPrevLastDay=28;
	else if (intPrevMonth==2 && intPrevYear % 4 == 0)
	{
		if (intPrevYear % 100 == 0)
		{
			if (intPrevYear % 400 == 0)
				intPrevLastDay=29;
			else
				intPrevLastDay=28;
		}
		else
		{
			intPrevLastDay=29;
		}
	}
	else
	{
		intPrevLastDay=31;
	}

	Stop_Flag=0;
	Cal_HTML=Cal_HTML + "<table border='0' bgcolor='#DCDCDC' cellpadding=1 cellspacing=1  onmouseover='doOver()' onmouseout='doOut()' onclick='doClick()' style='font-size : 12;font-family:굴림;'>";
	Cal_HTML=Cal_HTML + "<tr align=center>";
	Cal_HTML=Cal_HTML + "<td align=left  title='이전달' style='cursor:hand;' OnClick='JAVAScript:Show_cal(" + intPrevYear.toString()  + "," + intPrevMonth.toString() + ",1)'><font color=006699 size=2>◀</font></td>";
	Cal_HTML=Cal_HTML + "<td colspan=5><font color=#990000><b>";
	Cal_HTML=Cal_HTML + intThisYear.toString() + "년 " + parseInt(intThisMonth).toString() + "월";
	Cal_HTML=Cal_HTML + "</font></b></td>";
	Cal_HTML=Cal_HTML + "<td align=right title='다음달' style='cursor:hand;' OnClick='JAVAScript:Show_cal(" + intNextYear.toString() + "," + intNextMonth.toString() + ",1)'><font color=006699 size=2>▶</font></a></td>";
	Cal_HTML=Cal_HTML + "</tr>";
	Cal_HTML=Cal_HTML + "<tr align=center bgcolor='8DCFF4' style='color:000000;'>";
	Cal_HTML=Cal_HTML + "<td>일</td><td>월</td><td>화</td><td>수</td><td>목</td><td>금</td><td>토</td>";
	Cal_HTML=Cal_HTML + "</tr>";

	for (intLoopWeek=1;intLoopWeek<=6;intLoopWeek++)
	{
		Cal_HTML=Cal_HTML + "<tr align=right valign=top bgcolor='#F6F9F3'>";
		for (intLoopDay=1;intLoopDay<=7;intLoopDay++)
		{
			if (intThirdWeekDay > 1)
			{
				Cal_HTML=Cal_HTML + "<td>&nbsp;</td>";
				intThirdWeekDay=intThirdWeekDay-1;
			}
			else
			{
				if (thirdPrintDay > intLastDay)
				{
					Cal_HTML=Cal_HTML + "<td>&nbsp;</td>";
				}
				else
				{
					Cal_HTML=Cal_HTML + "<td title='" + intThisYear.toString() + "-" + intThisMonth.toString() + "-" + thirdPrintDay.toString() + "' style='cursor: hand;border: 1px solid white;width:18; height:18;";
					if (intThisYear-NowThisYear==0 && intThisMonth-NowThisMonth==0 && thirdPrintDay-intThisDay==0)
					{
						Cal_HTML=Cal_HTML + "background-color:#FACD8A;";
					}

					if  (intLoopDay==1)
					{
						Cal_HTML=Cal_HTML + "color:#990000;";
					}
					else
					{
						Cal_HTML=Cal_HTML + "color:black;";
					}

					Cal_HTML=Cal_HTML+ "'>" + thirdPrintDay.toString();
				}
				thirdPrintDay++;

				if (thirdPrintDay > intLastDay)	Stop_Flag=1;

			}
			Cal_HTML=Cal_HTML + "</td>";
		}
		Cal_HTML=Cal_HTML + "</tr>";
		if (Stop_Flag==1) break;
	}

	Cal_HTML=Cal_HTML+ "</table>";
	if(document.all.minical.length > 1){
		document.all.minical[0].innerHTML=Cal_HTML;
	}else{
		document.all.minical.innerHTML=Cal_HTML;
	}
}


function uf_cal(input) {
	var YY = input.value.substring(0,4);
	var MM = input.value.substring(5,7);
	var DD = input.value.substring(8,10);
	var Date = YY + MM + DD;
	MiniCal(input, Date );
}



/*
function uf_ShowCalendar(strFormName, strInputName, strFormat){

     var w_width =  200;
     var w_height = 190;

     var xpos = ( screen.width -  w_width  ) / 2;
     var ypos = ( screen.height - w_height ) / 2;

     window.open ('Calendar.jsp?inputName='+strInputName +'&formName='+strFormName +'&returnFormat=' + strFormat,'target','top='+ ypos +',left='+ xpos +',width=' + w_width + ',height=' + w_height +',directories=no,scrollbars=no,location=no,resizable=no,border=0,menubar=no');
}
*/



function uf_ShowCalendar(strFormName, strInputName, strFormat){

	url = "Calendar.jsp?inputName="+strInputName;
	url += "&formName=" + strFormName;
	url += "&returnFormat=" + strFormat;


	win = jsOpenWindow(url,'Cal','200','190','','1');
	return false;
}




/*-------------------------------------------------
 	Spec      : 달력(년/월/일 독립 input)
 	Parameter : form	-> form name
	Parameter : year	-> 년 입력창 input name
	Parameter : month	-> 월 입력창 input name
	Parameter : day		-> 일 입력창 input name
	Parameter : inspect	-> 영업일 검증 유무(1: 검증, 0: 무검증)
	Example   : a href="javascript:jsGoCalendar('f','year1','month1','day1', '0');return false;"
	Create	  : 2004-01-12
---------------------------------------------------*/
function jsGoCalendar(form, year, month, day, inspect) {
	url = "/com/common/calendar_pop.jsp?form="+form;
	url += "&year=" + year;
	url += "&month=" + month;
	url += "&day=" + day;
	if (inspect != null) {
		url += "&inspect=" + inspect;
	}

	win = jsOpenWindow(url,'Cal','200','190','','1');
	return false;
}

/*-------------------------------------------------
 	Spec      : 달력(년/월/일 통합)
 	Parameter : form		-> form name
	Parameter : inputName	-> 년/월/일 입력창 input name
	Parameter : format		-> 날짜 format
	Parameter : inspect		-> 영업일 검증 유무(1: 검증, 0: 무검증)
	Example   : a href="javascript:uf_ShowCalendar('f', 'date', 'yyyy.MM.dd', '0');return false;"
	Create	  : 2004-01-12
---------------------------------------------------*/
function uf_ShowCalendara(form, inputName, format, inspect) {
	var sep = format.charAt(4);

	url = "/com/common/calendar_pop.jsp?form="+form;
	url += "&inputName=" + inputName;
	url += "&format=" + sep;
	if (inspect != null) {
		url += "&inspect=" + inspect;
	}

	win = jsOpenWindow(url,'Cal','200','190','','1');
	return false;
}

// 외환 > 환전을 위한 추가 ( 김영일 )
function uf_FxShowCalendar(form, inputName, format, inspect) {
	var sep = format.charAt(4);

	url = "/pib/bank/fx/wpfxd041_02p.jsp?form="+form;
	url += "&inputName=" + inputName;
	url += "&format=" + sep;
	if (inspect != null) {
		url += "&inspect=" + inspect;
	}

	win = jsOpenWindow(url,'Cal','200','190','','1');
	return false;
}


// 예약이체일 지정을 위해 수정.(변영식)
function uf_ShowCalendarForReservedDay(form, inputName, format, inspect) {
	var sep = format.charAt(4);

	url = "/pib/bank/fndt/wpdep011_14p.jsp?form="+form;
	url += "&inputName=" + inputName;
	url += "&format=" + sep;
	if (inspect != null) {
		url += "&inspect=" + inspect;
	}

	//win = jsOpenWindow(url,'Cal','340','430','','1');
	win = jsOpenWindow(url,'Cal','350','510','','1');
	return false;
}
// 이체결과 조회일 한달 이상체크를 위해 추가(이보영)
function uf_ShowCalendarChk1Month(form, inputName, format, inspect) {
	var sep = format.charAt(4);

	url = "/pib/bank/fndt/wpdep012_04p.jsp?form="+form;
	url += "&inputName=" + inputName;
	url += "&format=" + sep;
	if (inspect != null) {
		url += "&inspect=" + inspect;
	}

	win = jsOpenWindow(url,'Cal','200','190','','1');
	return false;
}


/*-------------------------------------------------------------------------
 	Spec      : Open Window의 스크린 중앙 위치 처리
 	Parameter : url    -> 해당 페이지
	Parameter : name   -> 해당 Window Name
	Parameter : width  -> Window Width Size
	Parameter : height -> Window Heigth Size
	Parameter : scroll -> Window Scroll
	Parameter : loc    -> Window Location(null이 아니면 Center)
	Example   : a href="jsOpenWindow()";
-------------------------------------------------------------------------*/
function jsOpenWindow(url, name, width, height, scroll, loc){
	var top, left;
	if(scroll == null || scroll == '')	scroll='0';
	if(loc != null) {
		top	 = screen.height/2 - height/2 - 50;
		left = screen.width/2 - width/2 ;
	} else {
		top  = 10;
		left = 10;
	}
	var	option = 'width='+width+',height='+height+',top='+top+',left='+left+',resizable=no,status=no,toolbar=no,menubar=no,scrollbars=' + scroll;
   	var win = window.open(url, name, option);
   	win.focus();
   	return win;
}                     



// string에 대한 trim 함수 - 우측의 공백을 없앤 string을 반환
    function trim1(str){
        var len = str.length;
        for ( i=len; i > 0; i--){
            if (str.charAt(i-1) == ' ')
                str = str.substring(0, i-1);
                else return str;
        }
        return str;
    }
    
    //-- 날짜 검사
    function checkDateFormat (x) {
    	
    	if(x.value.length == 8) {
	    	var sYear = x.value.substring(0, 4);
	    	var sMonth =  x.value.substring(4, 6);
	    	var sDate	= x.value.substring(6, 8);
	    	
			var check = new Date(sYear, sMonth,sDate);
			if((sYear +""+ sMonth +""+ sDate) != (check.getYear() + "" + padZeros1(check.getMonth(),2) + "" + padZeros1(check.getDate(),2))) {
				alert("유효한 날짜 형식이 아닙니다." + "\n예)2005.03.01 or 20050301");
				return;
			}
		    	
    		x.value =  sYear + "." + sMonth + "." +  sDate;
    	
    	}
        var str_datetime = x.value;
        if(trim1(str_datetime) == "") {
            x.value = "";
            return;
        }
		var re_date = /^(\d+)\.(\d+)\.(\d+)/;
		if (!re_date.exec(str_datetime)) {
			alert("유효한 날짜 형식이 아닙니다. : "+ str_datetime + "\n예)2005.03.01 or 20050301");
			x.value = "";
			return;
		}
	}
	
	// --------------------------------------------------------------------
	// 숫자를 "0"으로 패팅처리(1 -> 01)
	// --------------------------------------------------------------------	
	function padZeros1(num, size) {
		var str = num.toString();
		var numZeros = size - str.length;
		for (var i=0; i<numZeros; i++) str="0"+str;
		return str;
	}