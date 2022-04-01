function show_calendar(str_target, str_datetime) {
	var arr_months = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
	var week_days = ["일", "월", "화", "수", "목", "금", "토"];
	var n_weekstart = 0; // day week starts from (normally 0 or 1)
	
	//--------------------modifyed  by dklee, 2004.07.24---------------------
	//var dt_datetime = (str_datetime == null || str_datetime =="" ?  new Date() : str2dt(str_datetime));
	var dt_datetime;
	if(str_datetime == null || str_datetime =="") {
		dt_datetime = new Date();
	}else {
		dt_datetime = str2dt(str_datetime);
		if(dt_datetime == null) {
			var obj = eval(str_target);
			obj.value = "";
			return;
		}
	}
	//---------------------------------------
	
	
	var dt_prev_month = new Date(dt_datetime);
	dt_prev_month.setMonth(dt_datetime.getMonth()-1);
	var dt_next_month = new Date(dt_datetime);
	dt_next_month.setMonth(dt_datetime.getMonth()+1);
	var dt_firstday = new Date(dt_datetime);
	dt_firstday.setDate(1);
	dt_firstday.setDate(1-(7+dt_firstday.getDay()-n_weekstart)%7);

	var dt_lastday = new Date(dt_next_month);
	dt_lastday.setDate(0);
	
	// print calendar header
	var str_buffer = new String (
		"<html>\n"+
		"<head>\n"+
		"<title>달력</title>\n"+
		"<STYLE TYPE=\"text/css\">\n" +
        "<\!-- \n "+
        " a:link     { font-size:9pt; text-decoration: none; color:\"#000000\" } \n "+
        " a:visited  { font-size:9pt; text-decoration: none; color:\"#000000\" } \n"+
        " a:hover    { font-size:9pt; text-decoration: none; color:\"red\" } \n"+
        "//-->\n"+
        "</STYLE>\n"+
		
		"</head>\n"+
		"<body bgcolor=\"White\"  leftmargin=\"10\" topmargin=\"10\" marginwidth=\"10\" marginheight=\"10\"    >\n"+
		"<table class=\"clsOTable\" cellspacing=\"0\" border=\"0\" width=\"200\" >\n"+
		"<tr><td bgcolor=\"#DEDEDE\">\n"+
		"<table cellspacing=\"1\" cellpadding=\"3\" border=\"0\" width=\"100%\">\n"+
		"<tr>\n	<td bgcolor=\"#DEDEDE\"><a href=\"javascript:window.opener.show_calendar('"+
		str_target+"', '"+ dt2dtstr(dt_prev_month)+"');\">"+
		"<font size=\"1\">\◀</font></a></td>\n"+
		"	<td bgcolor=\"#DEDEDE\" colspan=\"5\" align=\"center\">"+
		"<font color=\"gray\" face=\"tahoma, verdana\" size=\"1\">"
		+dt_datetime.getFullYear()+"&nbsp;&nbsp;"+arr_months[dt_datetime.getMonth()]+"</font></td>\n"+
		"	<td bgcolor=\"#DEDEDE\" align=\"right\"><a href=\"javascript:window.opener.show_calendar('"
		+str_target+"', '"+dt2dtstr(dt_next_month)+"');\">"+
		
		"<font size=\"1\">\▶</font></a></td>\n</tr>\n"
	);

	var dt_current_day = new Date(dt_firstday);
	// print weekdays titles
	str_buffer += "<tr>\n";
	for (var n=0; n<7; n++)
		str_buffer += "	<td bgcolor=\"#ffffff\">"+
		"<font color=\"black\" face=\"굴림\" size=\"1\">"+
		week_days[(n_weekstart+n)%7]+"</font></td>\n";
	// print calendar table
	str_buffer += "</tr>\n";
	while (dt_current_day.getMonth() == dt_datetime.getMonth() ||
		dt_current_day.getMonth() == dt_firstday.getMonth()) {
		// print row heder
		str_buffer += "<tr>\n";
		for (var n_current_wday=0; n_current_wday<7; n_current_wday++) {
				if (dt_current_day.getDate() == dt_datetime.getDate() &&
					dt_current_day.getMonth() == dt_datetime.getMonth())
					// print current date
					str_buffer += "	<td bgcolor=\"#FFCC00\" align=\"right\">";
				else
					// print working days of current month
					str_buffer += "	<td bgcolor=\"#F2F2F2\" align=\"right\">";

				if (dt_current_day.getMonth() == dt_datetime.getMonth()) {
                    if(dt_current_day.getDay() == 6)
    					str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"'; window.close();\">"+
					"<font color=\"blue\" face=\"tahoma, verdana\" size=\"1\">";
                    else if(dt_current_day.getDay() == 0) 
	    				str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"'; window.close();\">"+
					"<font color=\"red\" face=\"tahoma, verdana\" size=\"1\">";
                    else if(dt_current_day.getDate() == dt_datetime.getDate() &&
					dt_current_day.getMonth() == dt_datetime.getMonth())
		    			str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"'; window.close();\">"+
					"<font color=\"white\" face=\"tahoma, verdana\" size=\"1\">";
                    else 
					// print days of current month
		    			str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"'; window.close();\">"+
					"<font color=\"black\" face=\"tahoma, verdana\" size=\"1\">";
                }else 
					// print days of other months
					str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"'; window.close();\">"+
					"<font color=\"#CDCDCD\" face=\"tahoma, verdana\" size=\"1\">";
				str_buffer += dt_current_day.getDate()+"</font></a></td>\n";
				dt_current_day.setDate(dt_current_day.getDate()+1);
		}
		// print row footer
		str_buffer += "</tr>\n";
	}
	// print calendar footer
	str_buffer +=
		"<form name=\"cal\">\n<tr><td colspan=\"7\" bgcolor=\"#BEBEBE\">"+
		"<input type=\"hidden\" name=\"time\" value=\"\" size=\"8\" maxlength=\"8\"></font></td></tr>\n" +
		"</table>\n" +
		"</tr>\n</td>\n</table>\n" +
		"</body>\n" +
		"</html>\n";

	var vWinCal = window.open("", "Calendar", 
		"width=220,height=177,status=no,resizable=no,top=200,left=400");
	
	
	//--- added by dklee, 2004.07.24
	vWinCal.focus();		
	//-------------------------------
	
	vWinCal.opener = self;
	var calc_doc = vWinCal.document;
	calc_doc.write (str_buffer);
	calc_doc.close();
}

// datetime parsing and formatting routimes. modify them if you wish other datetime format
// modified by dklee , 2004.07.24
function str2dt (str_datetime) {
	var re_date = /^(\d+)\.(\d+)\.(\d+)/;
	if (!re_date.exec(str_datetime)) {
		alert("유효한 날짜 형식이 아닙니다. : "+ str_datetime + "\n예)2004.07.24");
		return;
	}
	return (new Date (RegExp.$1, RegExp.$2-1, RegExp.$3));
}
function dt2dtstr (dt_datetime) {
    var m = new String(dt_datetime.getMonth())
    var d = new String(dt_datetime.getDate())
	if(d.length == 1 && m.length == 1 && m!=9){
		return (new String (
			dt_datetime.getFullYear()+"."+"0"+(dt_datetime.getMonth()+1)+"."+"0"+dt_datetime.getDate()));
	} else if(d.length == 1 && m.length == 1){
		return (new String (
			dt_datetime.getFullYear()+"."+(dt_datetime.getMonth()+1)+"."+"0"+dt_datetime.getDate()));
	} else if(d.length == 1 && m.length !=1){
		return (new String (
			dt_datetime.getFullYear()+"."+(dt_datetime.getMonth()+1)+"."+"0"+dt_datetime.getDate()));
	} else if(d.length != 1 && m.length == 1 && m!=9){
		return (new String (
			dt_datetime.getFullYear()+"."+"0"+(dt_datetime.getMonth()+1)+"."+dt_datetime.getDate()));
	} else if(d.length != 1 && m.length == 1 ){
		return (new String (
			dt_datetime.getFullYear()+"."+(dt_datetime.getMonth()+1)+"."+dt_datetime.getDate()));
	}else {
		return (new String (
			dt_datetime.getFullYear()+"."+(dt_datetime.getMonth()+1)+"."+dt_datetime.getDate()));
	}
			
}


//============삭제 불가 2004-02-16[이충헌]========================//
function submitCalendar(str_target, str_datetime) {
	var arr_months = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
	var week_days = ["일", "월", "화", "수", "목", "금", "토"];
	var n_weekstart = 0; // day week starts from (normally 0 or 1)

	var dt_datetime = (str_datetime == null || str_datetime =="" ?  new Date() : str2dt(str_datetime));
	var dt_prev_month = new Date(dt_datetime);
	dt_prev_month.setMonth(dt_datetime.getMonth()-1);
	var dt_next_month = new Date(dt_datetime);
	dt_next_month.setMonth(dt_datetime.getMonth()+1);
	var dt_firstday = new Date(dt_datetime);
	dt_firstday.setDate(1);
	dt_firstday.setDate(1-(7+dt_firstday.getDay()-n_weekstart)%7);

	var dt_lastday = new Date(dt_next_month);
	dt_lastday.setDate(0);
	
	// print calendar header
	var str_buffer = new String (
		"<html>\n"+
		"<head>\n"+
		"<title>달력</title>\n"+
		"<STYLE TYPE=\"text/css\">\n" +
        "<\!-- \n "+
        " a:link     { font-size:9pt; text-decoration: none; color:\"#000000\" } \n "+
        " a:visited  { font-size:9pt; text-decoration: none; color:\"#000000\" } \n"+
        " a:hover    { font-size:9pt; text-decoration: underline; color:\"red\" } \n"+
        "//-->\n"+
        "</STYLE>\n"+
		
		"</head>\n"+
		"<body bgcolor=\"White\">\n"+
		"<table class=\"clsOTable\" cellspacing=\"0\" border=\"0\" width=\"170\">\n"+
		"<tr><td bgcolor=\"#DEDEDE\">\n"+
		"<table cellspacing=\"1\" cellpadding=\"3\" border=\"0\" width=\"100%\">\n"+
		"<tr>\n	<td bgcolor=\"#DEDEDE\"><a href=\"javascript:window.opener.show_calendar('"+
		str_target+"', '"+ dt2dtstr(dt_prev_month)+"');\">"+
		"<font size=\"1\">\◀</font></a></td>\n"+
		"	<td bgcolor=\"#DEDEDE\" colspan=\"5\" align=\"center\">"+
		"<font color=\"gray\" face=\"tahoma, verdana\" size=\"1\">"
		+arr_months[dt_datetime.getMonth()]+"&nbsp;&nbsp;"+dt_datetime.getFullYear()+"</font></td>\n"+
		"	<td bgcolor=\"#DEDEDE\" align=\"right\"><a href=\"javascript:window.opener.show_calendar('"
		+str_target+"', '"+dt2dtstr(dt_next_month)+"');\">"+
		
		"<font size=\"1\">\▶</font></a></td>\n</tr>\n"
	);

	var dt_current_day = new Date(dt_firstday);
	// print weekdays titles
	str_buffer += "<tr>\n";
	for (var n=0; n<7; n++)
		str_buffer += "	<td bgcolor=\"#BEBEBE\">"+
		"<font color=\"white\" face=\"굴림\" size=\"1\">"+
		week_days[(n_weekstart+n)%7]+"</font></td>\n";
	// print calendar table
	str_buffer += "</tr>\n";
	while (dt_current_day.getMonth() == dt_datetime.getMonth() ||
		dt_current_day.getMonth() == dt_firstday.getMonth()) {
		// print row heder
		str_buffer += "<tr>\n";
		for (var n_current_wday=0; n_current_wday<7; n_current_wday++) {
				if (dt_current_day.getDate() == dt_datetime.getDate() &&
					dt_current_day.getMonth() == dt_datetime.getMonth())
					// print current date
					str_buffer += "	<td bgcolor=\"#FFB6C1\" align=\"right\">";
				else if (dt_current_day.getDay() == 0 || dt_current_day.getDay() == 6)
					// weekend days
					str_buffer += "	<td bgcolor=\"#DBEAF5\" align=\"right\">";
				else
					// print working days of current month
					str_buffer += "	<td bgcolor=\"white\" align=\"right\">";

				if (dt_current_day.getMonth() == dt_datetime.getMonth())
					// print days of current month
					str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"' ; window.opener.form.submit(); window.close();\">"+
					"<font color=\"black\" face=\"tahoma, verdana\" size=\"1\">";
				else 
					// print days of other months
					str_buffer += "<a href=\"javascript:window.opener."+str_target+
					".value='"+dt2dtstr(dt_current_day)+"' ; window.opener.form.submit(); window.close();\">"+
					"<font color=\"#CDCDCD\" face=\"tahoma, verdana\" size=\"1\">";
				str_buffer += dt_current_day.getDate()+"</font></a></td>\n";
				dt_current_day.setDate(dt_current_day.getDate()+1);
		}
		// print row footer
		str_buffer += "</tr>\n";
	}
	// print calendar footer
	str_buffer +=
		"<form name=\"cal\">\n<tr><td colspan=\"7\" bgcolor=\"#BEBEBE\">"+
		"<input type=\"hidden\" name=\"time\" value=\"\" size=\"8\" maxlength=\"8\"></font></td></tr>\n" +
		"</table>\n" +
		"</tr>\n</td>\n</table>\n" +
		"</body>\n" +
		"</html>\n";

	var vWinCal = window.open("", "Calendar", 
		"width=520,height=597,status=no,resizable=yes,top=200,left=400");
	vWinCal.opener = self;
	var calc_doc = vWinCal.document;
	calc_doc.write (str_buffer);
	calc_doc.close();
}


function makeDate(bform) {
	var now   = new Date();
	var day   = now.getDate();
	var month = now.getMonth()+1;
	var year  = now.getFullYear();	
	var year2 = now.getFullYear();	
//	alert(bform);
	switch (bform.term.value) {
		case '1주' :
			setToDate(bform, year2, month, day + 7);
			break;
		case '2주' :
			setToDate(bform, year2, month, day + 14);
			break;
		case '3주' :
			setToDate(bform, year2, month, day + 21);
			break;
		case '4주' :
			setToDate(bform, year2, month, day + 28);
			break;
	}

}
	
function setToDate(bform, y,m,d) {
	var tovot = new Date(y, m, d);
	var day = tovot.getDate();
	var month = parseInt(tovot.getMonth());
	var year = parseInt(tovot.getFullYear());
    if(day<10){day= "0"+day;}   
	  
	bform.edate.value = year+"."+month+"."+day;
}



/*function onChange() {
   document.all..style.display = "block";
}*/


