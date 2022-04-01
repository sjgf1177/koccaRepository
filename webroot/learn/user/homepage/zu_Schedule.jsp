<%
//**********************************************************
//  1. 제      목 : 홈페이지 메인
//  2. 프로그램명 : zu_Main.jsp
//  3. 개      요 :
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 정상진 2005.07.8
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_my_subj          = "";
    String v_my_year          = "";
    String v_my_subjnm        = "";
    String v_my_subjseq       = "";
    String v_my_subjseqgr     = "";
    String v_my_edustart      = "";
    String v_my_eduend        = "";
    String v_my_edustart_value = "";
    String v_my_eduend_value   = "";
	String v_start_day = "";
	String v_end_day = "";

	Calendar myCalendar = Calendar.getInstance();

	int year = 0;
	int month = 0;
	int previous_year = 0;
	int previous_month = 0;
	int next_year = 0;
	int next_month = 0;
	int day = 0;

	int t_year = 0;
	int t_month = 0;
	int t_day = 0;
		
	t_year = myCalendar.get(myCalendar.YEAR);
	t_month = myCalendar.get(myCalendar.MONTH)+1;
	t_day = myCalendar.get(myCalendar.DAY_OF_MONTH);

	String strYear = request.getParameter("year");
	String strMonth = request.getParameter("month");

	String r_month = "";
	String thisday = "";

	if (strYear == null) {
		year = myCalendar.get(myCalendar.YEAR);
	} else {
		year = Integer.parseInt(strYear);
	}

	if(strMonth == null) {
		month = myCalendar.get(myCalendar.MONTH);
	} else {
		month = Integer.parseInt(strMonth) -1;
	}

	myCalendar.set(myCalendar.YEAR, year);
	myCalendar.set(myCalendar.MONTH, month);
	month = month + 1;

	previous_year = myCalendar.get(myCalendar.YEAR) - 1;
	previous_month = myCalendar.get(myCalendar.MONTH);
	
	if (previous_month < 1) {
		previous_month = 12;
	} else if(previous_month > 12) {
		previous_month = 1;
	}
	
	next_year = myCalendar.get(myCalendar.YEAR) + 1;
	next_month = myCalendar.get(myCalendar.MONTH) + 2;
	
	if (next_month < 1) {
		next_month = 12;
	} else if (next_month > 12) {
		next_month = 1;
	}

	if(month<10) r_month = "0"+month;

	box.put("edustart",year+""+r_month);
//	box.put("eduend",year+""+r_month);
    MyScheduleBean myclassbean = new MyScheduleBean();
    ArrayList myclasslist = myclassbean.selectEducationSubjectList(box);


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>나의 일정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}


//미리보기 레이어 start
  function js_Msgposit(y){ 
	 var left = event.x + document.body.scrollLeft;
	 if(left > 110) left = 110;
	 var top = event.y + document.day_box.scrollTop;
	 if(top > 250) top = 250;
	  message.style.posTop = top 
	  message.style.posLeft = left;
  }
  function js_Msgset(str,str2){ 
	  var text 
	  text ='<table width="174" height="58" border="2" bgcolor="#EEFAD1" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#336600">'; 
	  text += '<tr><td><font color="006699">' + str + '<br><b>' + str2 + '</b></td></tr></table>';
	  message.innerHTML=text 
  }
  function js_Msgset1(str){ 
	  var text = str
	  message.innerHTML=text 
  }
  function js_Msghide(){ 
	  message.innerHTML='' 
      //message.style.visibility='hidden' 
  }
//미리보기 레이어 end

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
  
<div id="Layer1" style="position:absolute; left:116px; top:127px; width:175px; height:57px; z-index:1; visibility: hidden;" onMouseOut="MM_showHideLayers('Layer1','','hide')" onMouseOver="MM_showHideLayers('Layer1','','show')">
  <table width="174" height="58" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#336600" >
    <tr>
      <td valign="top" bgcolor="#EEFAD1" ><font color="006699">2005/08/11 
        </font><br>
        <b>실천!정보보안심화</b> 과정<br>
        교육시작일입니다.</td>
    </tr>
  </table>
</div>
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="300" height="100%">
    <tr>
        <td valign="top">
            <table cellpadding="0" cellspacing="1" bgcolor="#bebebe" class="table2">
                <tr>
                    <td align="center" valign="top" bgcolor="#FFFFFF" class="body_color">
					 <br>
            <!-- 년 월표시 -->
            <table width="254" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center"><a href="zu_Schedule.jsp?year=<%= previous_month == 12 ? previous_year : year %>&month=<%= previous_month %>" alt='<%=previous_month%>월'>◀</a> <strong> <%=year%>/<%=month%> </strong> <a href="zu_Schedule.jsp?year=<%= next_month == 1 ? next_year : year%>&month=<%= next_month %>" alt='<%=next_month%>월'>▶</a></td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
            </table>
			<!-- 날짜들 표시 -->
            <table width="254"  cellspacing="1" cellpadding="4"   class="mytable_out">
              <tr> 
                <td class="myt_tit" width="12"><img src="/images/user/homepage/my_1.gif" width="6" height="8"></td>
                <td class="myt_tit" width="12"><img src="/images/user/homepage/my_2.gif" width="8" height="8"></td>
                <td class="myt_tit" width="12"><img src="/images/user/homepage/my_3.gif" width="6" height="8"></td>
                <td class="myt_tit" width="12"><img src="/images/user/homepage/my_4.gif" width="11" height="8"></td>
                <td class="myt_tit" width="12"><img src="/images/user/homepage/my_3.gif" width="6" height="8"></td>
                <td class="myt_tit" width="12"><img src="/images/user/homepage/my_5.gif" width="5" height="8"></td>
                <td class="myt_tit" width="12"><img src="/images/user/homepage/my_6.gif" width="6" height="8"></td>
              </tr><form name="day_box" method="post">
	<%
		while(true){
			day++;
			
			//날짜를 day 값으로 세팅
			myCalendar.set(myCalendar.DAY_OF_MONTH, day);
			
			//달력의 날짜가 다음달로 넘어가면 day 값이랑 달라짐
			if (day != myCalendar.get(myCalendar.DAY_OF_MONTH)) {
				break;
			}
			
			if (day == 1) {
				out.println("<tr align='right' valign='top'>");
				
				//요일 수만큼 빈칸 만들기
				for (int j = 1; j < myCalendar.get(myCalendar.DAY_OF_WEEK); j++) {
					out.print("<td class=myt_td>&nbsp;</td>");
				}
			}
			
			
			out.println("<td class='myt_td'>");
			out.println("  <table width='20' border='0' cellspacing='0'cellpadding='0'>");                    
			out.println("	<tr>");
			out.println("	  <td align='center'>");


			//요일이 일요일이면 빨간색으로 표시
			if (myCalendar.get(myCalendar.DAY_OF_WEEK) == myCalendar.SUNDAY) {
				out.println("<font color=red>");
				out.println(myCalendar.get(myCalendar.DAY_OF_MONTH));
				out.println("</font>");
				
			} else if (myCalendar.get(myCalendar.DAY_OF_WEEK) == myCalendar.SATURDAY) {
				//요일이 토요일이면 파란색으로 표시					
				out.println("<font color=blue>");
				out.println(myCalendar.get(myCalendar.DAY_OF_MONTH));
				out.println("</font>");
				
			} else if (t_year == year & t_month == month & t_day == day) {
				//오늘일 경우 폰트는 크고 색깔은 검은색으로				
				out.println("<font color=#EE7EAD><b>");
				out.println(myCalendar.get(myCalendar.DAY_OF_MONTH));
				out.println("</b></font>");
			
			} else {
				out.println(myCalendar.get(myCalendar.DAY_OF_MONTH));
			}

			out.println("	</td>");
            out.println("  </tr>");
            out.println("  <tr> ");
            out.println("	  <td align='center' height='8'>");

		
    for(int ss = 0; ss < myclasslist.size(); ss++) {
        DataBox mydbox    = (DataBox)myclasslist.get(ss);

        v_my_subj          =  mydbox.getString("d_subj");
        v_my_year          =  mydbox.getString("d_year");
        v_my_subjnm        =  mydbox.getString("d_subjnm");
        v_my_subjseq       =  mydbox.getString("d_subjseq");
        v_my_edustart      =  mydbox.getString("d_edustart");
        v_my_eduend        =  mydbox.getString("d_eduend");

		v_start_day      =  mydbox.getString("d_edustart").substring(0,8);
        v_end_day        =  mydbox.getString("d_eduend").substring(0,8);

		v_my_edustart_value = "교육시작일: "+FormatDate.getFormatDate(v_my_edustart,"yyyy/MM/dd");
        v_my_eduend_value   = "교육종료일: "+FormatDate.getFormatDate(v_my_eduend,"yyyy/MM/dd");
//out.println(day);
//out.println(v_end_day);
		thisday = year+""+r_month+""+((day < 10) ? "0"+day : ""+day);
		if(thisday.equals(v_start_day)){
            out.println("<a href='#' onmousemove=\"js_Msgposit(100)\"  onMouseOut=\"js_Msghide(); window.status=(''); return true;\" onmouseover=\"js_Msgset('"+v_my_edustart_value+"','"+v_my_subjnm+"'); return true;\">");
			out.println("<img src='/images/user/homepage/bl_myday.gif' width='7' height='8' border='0'></a>");
		}else {
			out.println(" ");
		}

		if(thisday.equals(v_end_day)){
            out.println("<a href='#' onmousemove=\"js_Msgposit(100)\"  onMouseOut=\"js_Msghide(); window.status=(''); return true;\" onmouseover=\"js_Msgset('"+v_my_eduend_value+"','"+v_my_subjnm+"'); return true;\">");
			out.println("<img src='/images/user/homepage/bl_myday.gif' width='7' height='8' border='0'></a>");
		}else {
			out.println(" ");
		}

	}// for end
			out.println("	  </td>");
            out.println("	</tr>");
            out.println("  </table>");
			out.println("</td>");
			
			//요일이 토요일이면 다음줄로 내림
			if (myCalendar.get(myCalendar.DAY_OF_WEEK) == myCalendar.SATURDAY) {
				out.println("</tr>");
				out.println("<tr align='right' valign='top'>");
			}
		}
				//요일 수만큼 빈칸 만들기
				for (int j = 8; j > myCalendar.get(myCalendar.DAY_OF_WEEK); j--) {
					out.print("<td class=myt_td>&nbsp;</td>");
				}		
		
		//마지막이 토요일로 끝나지 않았으면 줄을 닫아줌
		if (myCalendar.get(myCalendar.DAY_OF_WEEK) != myCalendar.SATURDAY){

			out.println("</tr>");
		}
		
	%>
            </table>
            <table width="262" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="6"></td>
              </tr>
            </table>           
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
	<td align="right"><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" border="0"></a></td>
  </tr>
</table>
<div id="message" style="position:absolute; left:116px; top:127px; width:175px; height:57px; z-index:1; border-width:1px; border-style:none;"></div>
</body>
</html>