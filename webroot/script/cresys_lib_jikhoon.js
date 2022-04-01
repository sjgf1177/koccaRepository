
// 달력 스크립트 시작
var target;

function MiniCal(jucke) {
  target=jucke
  x = (document.layers) ? loc.pageX : event.clientX;
  y = (document.layers) ? loc.pageY : event.clientY;
  minical.style.pixelTop  = y-0;
  minical.style.pixelLeft  = x-50;
  minical.style.display = (minical.style.display == "block") ? "none" : "block";
  Show_cal(0,0,0)
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
  window.event.srcElement.style.borderColor = "red";
  if (cal_Day.length > 7) {
    target.value=cal_Day
  }

  if (document.form1.p_action.value=="go"){
	if (!chkParam()) { return; }
  }
  if(document.form1.p_attdate1.value != null){
	document.form1.p_attdate.value=make_date(document.form1.p_attdate1.value);
  }
  document.form1.p_process.value="insertPage";
  document.form1.submit();

}

function doOut() {
  var el = window.event.fromElement;
  cal_Day = el.title;

  if (cal_Day.length > 7) {
    el.style.borderColor = "white";
  }
  stime=window.setTimeout("minical.style.display='none';", 10);
}

// 달력 스크립트 끝

function SendMail(urlstr) {
  var popWnd = urlstr ;
  window.open(popWnd, "sendmail", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=no,copyhistory=yes,width=900,height=600");
}

function userinfo(url){
  window.open(url, "userinfo", "toolbar=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=562,height=285,top=0,left=0");
}

function imageOn(i)
{
  eval('document.img'+i+'.src=images['+i+'][1].src');
}

function imageOff(i)
{
  eval('document.img'+i+'.src=images['+i+'][0].src');
}

function logout()
{
  if (confirm("접속을 종료하시겠습니까?"))
  {
    top.location="logout.jsp";
  }
}

function num_max_chk(data, data1)
{
  for (var i = 0 ;i < data.value.length ;i++ )
  {
    if ((data.value.substring(i,i+1) < "0" || data.value.substring(i,i+1) > "9" ))
    {
      alert("숫자만 입력가능합니다.");
      data.focus();
      return;
    }
  }

  if (parseInt(data.value,10) > parseInt(data1,10))
  {
    alert("만점보다 적은값을 입력하십시요.");
    data.focus();
    return;
  }
}

function quotation_chk()
{
  var data;
  var sum_str = "";

  for ( var k = 0 ; k < document.forms.length ; k++ )
  {
    for ( var j = 0 ; j < document.forms[k].elements.length ;j++)
    {
      if ( document.forms[k].elements[j].type == "text" || document.forms[k].elements[j].type == "textarea" )
      {
        data = document.forms[k].elements[j].value;
        sum_str = "";
      
        for ( var i = 0 ; i < data.length ;i++)
        {
          if ( data.substring(i,i+1) == "'" )
          {
            sum_str = sum_str + "＇";
          }
          else if ( data.substring(i,i+1) == "\"" )
          {
            sum_str = sum_str + "＂";
          }
          else
          {
            sum_str = sum_str + data.substring(i,i+1);
          }
        }  
        document.forms[k].elements[j].value = sum_str;
      }
    }
  }
}


function numeric_chk(data)
{
  for (var i = 0 ;i < data.value.length ;i++ )
  {
    if ((data.value.substring(i,i+1) < "0" || data.value.substring(i,i+1) > "9" ))
    {
      alert("숫자만 입력가능합니다.");
      data.value = "";
      data.focus();
      return false;
    }
  }
  return true;
}

function numeric_chk_noalert(value)
{
  for (var i = 0 ;i < value.length ;i++ )
  {
    if ((value.substring(i,i+1) < "0" || value.substring(i,i+1) > "9" )) {
      return false;
    }
  }
  return true;
}

function number_chk(data)
{
  if (isNaN(data.value))
  {
    alert("숫자만 입력가능합니다.");
    data.value = "0.0";
    data.focus();
    return false;
  }
  return true;
}

function number_chk_noalert(value)
{
  if (isNaN(value)) {
    return false;
  }
  return true;
}


/**
 * 입력값이 사용자가 정의한 포맷 형식인지 체크
 * 자세한 format 형식은 자바스크립트의 'regular expression'을 참조
 */
function isValidFormat(input,format) {
    if (input.value.search(format) != -1) {
        return true; //올바른 포맷 형식
    }
    return false;
}

/**
 * 입력값이 이메일 형식인지 체크
 * ex) if (!isValidEmail(form.email)) {
 *         alert("올바른 이메일 주소가 아닙니다.");
 *     }
 */
function isValidEmail(input) {
    var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    if (blankCheck(input.value)) return true;
    return isValidFormat(input,format);
}

/**
 * 입력값이 전화번호 형식(숫자-숫자-숫자)인지 체크
 */
function isValidPhone(input) {
    var format = /^(\d+)-(\d+)-(\d+)$/;
    if (blankCheck(input.value)) return true;
    return isValidFormat(input,format);
}

/**
 * 선택된 라디오버튼이 있는지 체크
 */
function hasCheckedRadio(input) {
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (input[inx].checked) return true;
        }
    } 
    else {
        if (input.checked) return true;
    }
    return false;
}

/**
 * 선택된 체크박스가 있는지 체크
 */
function hasCheckedBox(input) {
    return hasCheckedRadio(input);
}

function Insa_OnClick(urlstr)
{
  var popWnd = urlstr ;
  var popmnu = window.open(popWnd, "popmnu", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=yes,width=500,height=350");
}

function Excel(urlstr)
{
   excel_popup = window.open("/excel/" + urlstr + ".csv", "popup1","toolbar=no,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes,copyhistory=yes, width=800, height=530");
}

function PullWin(urlstr)
{
  var popWnd = urlstr ;
  var popup = window.open(popWnd, "popup", "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,copyhistory=yes");
}

function select_YYYYMMDD_LeapYear(year)
{
  var flag = new Boolean(false);
  
  if (((year % 4 == 0) && !(year % 100 == 0))||(year % 400 ==0)) return (true);
  /*
  if ((parseInt(year,10) % 4) == 0)      // 4년마다 윤년이고
  {
    flag = true;
    if ((parseInt(year,10) % 100) == 0)    // 그중 100년마다 윤년이 아니고,
    {
      flag = false;
      if ((parseInt(year,10) % 400) == 0)  // 그중 400년마다 윤년이다
      {
        flag = true;
      }
    }
  }
  */
  return (false);
}

function select_YYYYMMDD_Change(syear,smon,sday)
{
  myform = document.form1;    // FORM 이름변경(script이동시)
  myform_s = "document.form1.";
  var end_day = new Array();
  end_day[0] = 31;
  end_day[1] = 28;
  end_day[2] = 31;
  end_day[3] = 30;
  end_day[4] = 31;
  end_day[5] = 30;
  end_day[6] = 31;
  end_day[7] = 31;
  end_day[8] = 30;
  end_day[9] = 31;
  end_day[10] = 30;
  end_day[11] = 31;
  var year = parseInt(this.eval(myform_s + syear + ".value"),10);
  var mon = parseInt(this.eval(myform_s + smon + ".value"),10) - 1;

  if (select_YYYYMMDD_LeapYear(year))  // true이면 윤년이므로 2월달은 28일이된다
  {
    end_day[1] = 29;
  }
  else
  {
    end_day[1] = 28;
  }

  var s_sday = myform_s + sday + ".options.length = end_day[mon]";
  this.eval(s_sday);

  for (var i=0; i<end_day[mon]; i++)
  {
    this.eval(myform_s + sday + ".options[i].value = i + 1");
    this.eval(myform_s + sday + ".options[i].text = i + 1");
  }
  if (this.eval(myform_s + sday + ".options.selectedIndex") == -1)
  {
    this.eval(myform_s + sday + ".options.selectedIndex = end_day[mon] - 1");
  }
}

function select_YYYYMMDD(syear,year,smon,mon,sday,day)
{  // s를 붙인 것은 html input name
  var select_check;
  var today = new Date();
  var now_year = today.getYear();
  
  if (now_year < 100)      // 2000년 이전에는 두자리 2000년이후는 4자리로 나온다..document참조
  {
    now_year = 1900 + now_year;
  }
  
  document.write("<select name=" + syear + " onChange=select_YYYYMMDD_Change('" + syear + "','" + smon + "','" + sday + "')>");
  for (var loop_year=now_year-1; loop_year<=now_year+10; loop_year++)
  {
    if (loop_year == year)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_year + select_check + ">" + loop_year + "</option>");
  }
  document.write("</select>/ ");
  
  document.write("<select name=" + smon + " onChange=select_YYYYMMDD_Change('" + syear + "','" + smon + "','" + sday + "')>");
  
  for (var loop_month=1; loop_month<=12; loop_month++)
  {
    if (loop_month == mon)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_month + select_check + ">" + loop_month + "</option>");
  }
  document.write("</select>/ ");
  
  document.write("<select name=" + sday + " onChange=select_YYYYMMDD_Change('" + syear + "','" + smon + "','" + sday + "')>");
  for (var loop_day=1; loop_day<=31; loop_day++)
  {
    if (loop_day == day)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_day + select_check + ">" + loop_day + "</option>");
  }
  document.write("</select>");
}

function select_YYYYMMDD2(syear,year,smon,mon,sday,day,shh,hh,smm,mm)
{  // s를 붙인 것은 html input name 시간,분까지가져옴
  var select_check;
  var today = new Date();
  var now_year = today.getYear();
  
  if (now_year < 100)                // 2000년 이전에는 두자리 2000년이후는 4자리로 나온다..document참조
  {
    now_year = 1900 + now_year;
  }
  
  document.write("<select name=" + syear + ">");
  for (var loop_year=now_year-1; loop_year<=now_year+10; loop_year++)
  {
    if (loop_year == year)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_year + select_check + ">" + loop_year + "</option>");
  }
  document.write("</select>/ ");
  
  document.write("<select name=" + smon + " onChange=select_YYYYMMDD_Change('" + syear + "','" + smon + "','" + sday + "')>");
  
  for (var loop_month=1; loop_month<=12; loop_month++)
  {
    if (loop_month == mon)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_month + select_check + ">" + loop_month + "</option>");
  }
  document.write("</select>/ ");
  
  document.write("<select name=" + sday + ">");
  for (var loop_day=1; loop_day<=31; loop_day++)
  {
    if (loop_day == day)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_day + select_check + ">" + loop_day + "</option>");
  }
  document.write("</select>&nbsp;&nbsp;");
  
  //시간
  document.write("<select name=" + shh + ">");
  for (var loop_hh=0; loop_hh<=23; loop_hh++)
  {
    if (loop_hh == hh)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_hh + select_check + ">" + loop_hh + "</option>");
  }
  document.write("</select>:");
  //분
  document.write("<select name=" + smm + ">");
  for (var loop_mm=0; loop_mm<=59; loop_mm++)
  {
    if (loop_mm == mm)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_mm + select_check + ">" + loop_mm + "</option>");
  }
  document.write("</select>");

}


function select_YYYYMMDD3(syear,year,smon,mon,sday,day)
{  // s를 붙인 것은 html input name
  var select_check;
  var today = new Date();
  var now_year = today.getYear();
  
  if (now_year < 100)                // 2000년 이전에는 두자리 2000년이후는 4자리로 나온다..document참조
  {
    now_year = 1900 + now_year;
  }
  
  document.write("<select name=" + syear + ">");
  for (var loop_year=1950; loop_year<=now_year+10; loop_year++)
  {
    if (loop_year == year)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_year + select_check + ">" + loop_year + "</option>");
  }
  document.write("</select>/ ");
  
  document.write("<select name=" + smon + " onChange=select_YYYYMMDD_Change('" + syear + "','" + smon + "','" + sday + "')>");
  
  for (var loop_month=1; loop_month<=12; loop_month++)
  {
    if (loop_month == mon)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_month + select_check + ">" + loop_month + "</option>");
  }
  document.write("</select>/ ");
  
  document.write("<select name=" + sday + ">");
  for (var loop_day=1; loop_day<=31; loop_day++)
  {
    if (loop_day == day)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    document.write("<option value=" + loop_day + select_check + ">" + loop_day + "</option>");
  }
  document.write("</select>");
}


function select_hhmm(shh,hh,smm,mm){
    var select_check;
    //시간
  document.write("<select name=" + shh + ">");
  for (var loop_hh=0; loop_hh<=23; loop_hh++)
  {
    if (loop_hh == hh)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    
    if ( loop_hh < 10 )
    {
      loop_hh = "0" + loop_hh;
    }
    document.write("<option value=" + loop_hh + select_check + ">" + loop_hh + "</option>");
  }
  document.write("</select>:");
  //분
  document.write("<select name=" + smm + ">");
  for (var loop_mm=0; loop_mm<=59; loop_mm++)
  {
    if (loop_mm == mm)
    {
      select_check=" SELECTED ";
    }
    else
    {
      select_check=" ";
    }
    if ( loop_mm < 10 )
    {
      loop_mm = "0" + loop_mm;
    }    
    document.write("<option value=" + loop_mm + select_check + ">" + loop_mm + "</option>");
  }
  document.write("</select>");
}

// 2001-06-22에서 숫자(20010622)가져오는 함수.
function make_date(str){
    var yyyy = str.substring(0,4);
    var mm   = str.substring(5,7);
    var dd   = str.substring(8,10);
    
    var date_val = yyyy+mm+dd;
    return date_val;
}

/*
function make_date2(data1, data2, data3){
	var zero_val = "00";
	var date_val = "";
	date_val = data1 + zero_val.substring(0,2-data2.length) + data2;
	date_val = date_val + zero_val.substring(0,2-data3.length) + data3;

	return date_val;
}
*/
function blankCheck( msg )
{
	var mleng = msg.length;
	chk=0;
	
	for (i=0; i<mleng; i++)
	{
		if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
	}
	if ( chk == 0 ) return (true);
	
	return (false);
}

function blankFind( msg )
{
	var mleng = msg.length;
	chk=0;
	
	for (i=0; i<mleng; i++)
	{
		if ( msg.substring(i,i+1)==' ' || msg.substring(i,i+1)=='\n' || msg.substring(i,i+1)=='\r') return (false);
	}
	
	return (true);
}

function realsize( value ) 
{
	var len = 0;
	if ( value == null ) return 0;
	for(var i=0;i<value.length;i++){
	   var c = escape(value.charAt(i));
	   if ( c.length == 1 ) len ++;
	   else if ( c.indexOf("%u") != -1 ) len += 2;
	   else if ( c.indexOf("%") != -1 ) len += c.length/3;
	}
	return len;
}

function compnumChk( code )
{
	var sum = 0;
	var getlist =new Array(10);
	var chkvalue =new Array("1","3","7","1","3","7","1","3","5");
	
	for (var i=0; i<10; i++) { 
		getlist[i] = code.substring(i,i+1);
	}
	
	for (var i=0; i<9; i++) { 
		sum += getlist[i]*chkvalue[i];
	}
	sum = sum +parseInt((getlist[8]*5)/10) ;
	sidliy = sum%10;
	sidchk = 0;
	
	if ( sidliy != 0 ) { 
		sidchk = 10 - sidliy; 
	} 
	else { 
		sidchk = 0; 
	}
	
	if ( sidchk != getlist[9] ) {
		return (false);
	}
	
	return (true);
}

function useridCheck( userid )
{
  //id 영문 check
	var check = "y";
	var newid = userid;

	for(i=0; i<newid.length; i++){
		if (newid.charAt(i) == " "){
			check = "n";
		}
		else if (((newid.charAt(i) < "A") || (newid.charAt(i) > "Z"))
		 		 && ((newid.charAt(i) < "a") || (newid.charAt(i) > "z"))
		     && ((newid.charAt(i) < "0") || (newid.charAt(i) > "9"))){  

			check = "n";
		}
	}
	
	if (check =="n") {
		return (false);
	}
	
	return (true);
}

function passwordCheck( passwd )
{
  //비밀번호 영문,숫자 혼용 check
	var cpasschk=0;
	var npasschk=0;
	var check = "y";
	var newpasswd = passwd;

	for(i=0; i<newpasswd.length; i++) 
	{
		if (newpasswd.charAt(i) == " ") {
			check = "n";
		}
		else if (((newpasswd.charAt(i) < "A") || (newpasswd.charAt(i) > "Z"))
				 && ((newpasswd.charAt(i) < "a") || (newpasswd.charAt(i) > "z"))
		     && ((newpasswd.charAt(i) < "0") || (newpasswd.charAt(i) > "9"))){  

			check = "n";
		}
		if ( "A" <= newpasswd.charAt(i) && newpasswd.charAt(i) <= "Z" ) cpasschk++;
		if ( "a" <= newpasswd.charAt(i) && newpasswd.charAt(i) <= "z" ) cpasschk++;
		if ( "0" <= newpasswd.charAt(i) && newpasswd.charAt(i) <= "9" ) npasschk++;
	}
	
	if (check == "n" || cpasschk == 0 || npasschk == 0) {
		return (false);
	}
	
	return (true);
  //check end	  	
}

//입력폼에서 숫자만 입력
function currency(obj)
{
  if (event.keyCode >= 48 && event.keyCode <= 57) {
  } else {
  	event.returnValue = false
  }
}


// 전체 선택
function allcheck(theform)
{
    for( i=0; i<theform.elements.length; i++) {
        ele = theform.elements[i];
            ele.checked = true;
    }
    return;
}

// 전체해제
function discheck(theform)
{
    for( i=0; i<theform.elements.length; i++) {
        ele = theform.elements[i];
            ele.checked = false;
    }
    return;
}

// 윈도우 팝업
function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
    toolbar_str = toolbar ? 'yes' : 'no';
    menubar_str = menubar ? 'yes' : 'no';
    statusbar_str = statusbar ? 'yes' : 'no';
    scrollbar_str = scrollbar ? 'yes' : 'no';
    resizable_str = resizable ? 'yes' : 'no';
    window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
}


// null,자릿수 체크 ( 필드명,메세지,길이 ) /*완료
function checkLength ( theField, s, Length)
{
    if (theField.value == "" || theField.value == null )
    {
        theField.value="";  //폼창에 NULL값 리턴
        alert( s + "를 입력하십시요.");
        return(false);
    }

    if (theField.value.length < Length)
    {
        theField.value="";  //폼창에 NULL값 리턴
        alert( Length+"글자 이상 이어야 합니다.");
        return(false);
    }

    return(true);
}

// null 값 체크 /*완료
function isNull ( theField, s )
{
    if (theField.value == "" || theField.value == null )
    {
        theField.value="";  //폼창에 NULL값 리턴
        //theField.focus();
        //theField.select();
        alert( s + "을(를) 입력하십시요.");
        return(false);
    }

    return(true);
}

// 사업자 등록 번호 체크
function rnocheck(input) 
{
    var inputStr;
    var i = 0;
    var j = 0;
    var sum =0;
    var weight = new Array(1,3,7,1,3,7,1,3);
    inputStr= input.toString();

    for (i=0;i<inputStr.length-2;i++) {
        sum = sum + parseInt(inputStr.charAt(i)) * weight[i];
    }

    j = parseInt(inputStr.charAt(8)) * 5;

    sum = sum + ((j - (j % 10)) / 10) + ( j % 10 );
    sum = sum % 10;
    if (sum!=0) 
        sum = 10 - sum;
    if (sum != inputStr.charAt(9) )
        return false;
     else
        return true;
}