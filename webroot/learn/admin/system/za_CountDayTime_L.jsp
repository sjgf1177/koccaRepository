<%
//**********************************************************
//  1. 제      목: 접속통계
//  2. 프로그램명 : za_CountDayTime_L.jsp
//  3. 개      요: 접속통계 - 일시통계 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_process = box.getString("p_process");
    String v_year    = box.getStringDefault("p_date_year",  FormatDate.getDate("yyyy"));
    String v_month   = box.getStringDefault("p_date_month", FormatDate.getDate("MM"));
    String v_day     = box.getStringDefault("p_date_day",   FormatDate.getDate("dd"));
    String s_gadmin	 = box.getSession("gadmin");

    String v_pre_date   = FormatDate.getDayAdd(v_year+v_month+v_day+"0000", "yyyyMM", "month", -1);
    String v_pre_year   = v_pre_date.substring(0,4);
    String v_pre_month  = v_pre_date.substring(4,6);
    String v_next_date  = FormatDate.getDayAdd(v_year+v_month+v_day+"0000", "yyyyMM", "month", 1);
    String v_next_year  = v_next_date.substring(0,4);
    String v_next_month = v_next_date.substring(4,6);

    String v_date_year  = "";
    String v_date_month = "";
    String v_date_day   = "";
    String v_date_time  = "";
    String v_time_dsp   = "";
    int    v_maxcnt     = 0;
    int    v_first      = 0;
    int    v_last       = 0;
    int    v_halfday    = 0;
    int    v_lastday    = 0;

    int    v_cnt        = 0;
    int    v_percent    = 0;

    String v_script_day   = "";

    //-----------LastDay 구하기(윤달체크)-----------------------
    int year  = Integer.parseInt(v_year);
    int month = Integer.parseInt(v_month);
    int[] arry_lastDay = {0,31,28,31,30,31,30,31,31,30,31,30,31};
    if(year%4 == 0 && year%100 == 0) arry_lastDay[2] = 29;
    v_lastday = arry_lastDay[month];
    v_halfday = v_lastday/2 + v_lastday%2;

    int v_yearcount = Integer.parseInt((String)request.getAttribute("YearCnt"));
    ArrayList list1 = (ArrayList)request.getAttribute("selectList1");
    ArrayList list2 = (ArrayList)request.getAttribute("selectList2");

    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
	String v_tab_color5 = "black";

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//탭이동
	function changeTabpage(p_gubun) {
	    document.form1.p_process.value = "selectMonthDay";

		if (p_gubun == "HOME") {
		  document.form1.action = "/servlet/controller.system.CountServlet";

		} else if (p_gubun == "SUBJ") {
		  document.form1.action = "/servlet/controller.system.SubjCountServlet";
		
		} else if (p_gubun == "MENU") {
		  document.form1.action = "/servlet/controller.system.MenuCountServlet";
		} else if(p_gubun == "PREVIEW"){
		  document.form1.action = "/servlet/controller.system.MenuCountServlet";		
		  document.form1.p_process.value = "selectPreviewCount";
		}
		  
		  document.form1.p_gubun.value = p_gubun;
		  document.form1.submit();     
	}
	//select시 조회
	function whenSelection(){
        document.form1.action = "/servlet/controller.system.CountServlet";
        document.form1.p_process.value = "selectDayTime";
        document.form1.submit();
	}
	
	//페이지 이동
    function movePage(process) {
        document.form1.action = "/servlet/controller.system.CountServlet";
        document.form1.p_process.value = process;
        document.form1.submit();
    }

	//연간,월간 선택
    function selectPage(year, month) {
        document.form1.action = "/servlet/controller.system.CountServlet";
        document.form1.p_date_year.value   = year;
        document.form1.p_date_month.value  = month;
        document.form1.p_process.value = "selectDayTime";
        document.form1.submit();
    }
		
		//일간선택
    function moveDayTime(day) {
        document.form1.action = "/servlet/controller.system.CountServlet";
        document.form1.p_date_day.value = day;
        document.form1.p_process.value  = 'selectDayTime';
        document.form1.submit();
    }
-->
</SCRIPT>

</head>




<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_date_year"  value = "<%= v_year %>">
    <input type = "hidden" name = "p_date_month" value = "<%= v_month %>">
    <input type = "hidden" name = "p_date_day"   value = "<%=v_day%>">
    <input type = "hidden" name = "p_gubun"   value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">


      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/system/title_bg.gif"><img src="/images/admin/system/system_title6.gif"  border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <table width="50%" cellspacing="0" cellpadding="0" class="table1" border="0">
        <tr>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_left"></td>
                <td class="<%=v_tab_color1%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_middle">접속통계</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('SUBJ')" class="c">과정별 접속통계</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_left"></td>
                <td class="<%=v_tab_color2%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_middle"><a href="javascript:changeTabpage('MENU')" class="c">메뉴별 접속통계</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_left"></td>
                <td class="<%=v_tab_color2%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_middle"><a href="javascript:changeTabpage('PREVIEW')" class="c">맛보기카운트</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="55%"></td>
		</table>
		<br>

      <!----------------- 검색조건 form 끝 ----------------->
	  <table cellspacing="0" cellpadding="1" class="form_table_out">
		<tr>
		  <td bgcolor="#C6C6C6" align="center">
			<table cellspacing="0" cellpadding="0" class="form_table_bg" >
			  <tr>
				<td height="7"></td>
			  </tr>
			  <tr>
				<td align="center">
				  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
					<tr>
					  <td width="27%" colspan="2" align="left" valign="middle">
						<!------------------- 조건검색 시작 ------------------------->
						<%
							if(s_gadmin.equals("A1")){
						%>
						&nbsp; <%= SelectEduBean.getGrcode(box, true, true)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
						<%
							} else {
						%>
						&nbsp; <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
						<%	}	%>
					  </td>
					</tr>
				  </table>
				</td>
			  </tr>
			  <tr>
				<td height="9"></td>
			  </tr>
			</table>
		  </td>
		</tr>
	  </table>
      <!----------------- 검색조건 form 끝 ----------------->
		<br>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><font face="굴림" size="3.5">◈<b>접속통계 (일-시 통계) <%=v_year%>년 Total :<%=v_yearcount%></font></b></td>
          </tr>
        </table>
        <br>

        <!----------------- 월-일 통계, 일-시 통계, 월-시 통계, 월-요일 통계 버튼 시작 ----------------->
        <table width="37%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center"><a href="javascript:movePage('selectMonthDay')"><img src="/images/admin/system/statistics1_butt.gif" width="76" height="20" border="0"></a></td>
            <td align="center"><img src="/images/admin/system/statistics2_butt.gif" width="76" height="20" border="0"></td>
            <td align="center"><a href="javascript:movePage('selectMonthTime')"><img src="/images/admin/system/statistics3_butt.gif" width="76" height="20" border="0"></a></td>
            <td align="center"><a href="javascript:movePage('selectMonthWeek')"><img src="/images/admin/system/statistics4_butt.gif" width="76" height="20" border="0"></a></td>
          </tr>
          <tr><td height="5"></td></tr>
        </table>
        <!----------------- 월-일 통계, 일-시 통계, 월-시 통계, 월-요일 통계 버튼 끝 ----------------->
        <br>

        <!----------------- 월-일 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="1">
          <tr>
            <td align="right"><a href="javascript:selectPage('<%=v_pre_year%>','<%=v_pre_month%>')"><img src="/images/admin/system/system_left_arrow.gif" width="17" height="17" border="0"></a></td>
            <td align="center"><b><font size="3"><%=v_year%>년 <%=v_month%>월</font></b></td>
            <td><a href="javascript:selectPage('<%=v_next_year%>','<%=v_next_month%>')"><img src="/images/admin/system/system_right_arrow.gif" width="17" height="17" border="0"></a></td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="center"><b><font size="3"><%=v_day%>일</font></b></td>
            <td>&nbsp;</td>
          </tr>
        </table>
       <!----------------- 월-일 끝 ----------------->
	   <br>
        <!----------------- 접속자수 시작 ----------------->
        <table width="97%" border="0" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="<%=v_halfday+1%>" height="3" bgcolor="#0075AD"></td>
          </tr>
<%
    int j = 0;
	int sw = 0;
	int sw2 = 0;
    v_first = 1;
    v_last  = v_halfday;
    for(int k = 1; k <= 2;k++) {
%>
          <tr>
            <td class="table_title" height="25"><b>일</b></td>
<%
        for(int i = v_first; i <= v_last; i++) {
            if (i < 10) {
                v_script_day = "0" + String.valueOf(i);
            } else {
                v_script_day = String.valueOf(i);
            }

            if (i <= v_lastday) {
              out.println ("<td class='table_title' ><b><a href=\"javascript:moveDayTime('"+v_script_day+"')\">"+ i +"일</b></td>");
            } else {
              out.println ("<td class='table_title' ></td>");
            }

        }
%>          </tr>
          <tr>
            <td class="table_01" height="25" >접속자수</td>
<%

        for(int i = v_first; i <= v_last; i++) {
            if (j < list1.size()) {
                CountData data1  = (CountData)list1.get(j);

                v_date_day  = data1.getDate_day();
                v_cnt         = data1.getCnt();
			    sw = 1;
                j++;
            } else {
                v_date_day  = String.valueOf(i);
                v_cnt         = 0;
			    sw = 0;
            }

            while (StringManager.toInt(v_date_day) > i && i <= v_last) {
                out.println ("<td class='table_02_1'>"+0+"</td>");
				sw2 = 0;
                i++;
            }
             if (i <= v_last) {
              out.println ("<td class='table_02_1'>"+v_cnt+"</td>");
			  sw2 = 1;
            } else {
              out.println ("<td class='table_02_1'></td>");
            }
        }

		if (j != 0 && sw == 1 && sw2 == 0) j--;

%>

          </tr>
<%
        v_first += v_halfday;
        v_last  += v_halfday;
    }
%>
        </table>
        <!----------------- 접속자수 끝 ----------------->

        <br>
        <br>
        <!----------------- 시간대별 접속자수 시작 ----------------->
        <table width="97%" border="0" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="2" height="3" bgcolor="#0075AD"></td>
          </tr>
          <tr>
            <td height="25" class="table_title" width="20%"><b>시</b></td>
            <td class="table_title"><b>[<%=v_year%>년 <%=v_month%>월 <%=v_day%>일]  시간대별 접속자수입니다.</b></td>
          </tr>

<%
            // 최대값 구하기
            for(int i = 0; i < list2.size(); i++) {
                 CountData data2  = (CountData)list2.get(i);
                 v_cnt         = data2.getCnt();
                 if (v_maxcnt < v_cnt)  v_maxcnt = v_cnt;
            }

            j = 0;
            for(int i = 1; i <= 24; i++) {

              if (j < list2.size()) {
                CountData data3  = (CountData)list2.get(j);

                v_date_month  = data3.getDate_month();
                v_date_day    = data3.getDate_day();
                v_date_time   = data3.getDate_time();
                v_cnt         = data3.getCnt();
                v_percent     = (v_cnt*90)/v_maxcnt;
                if (v_percent ==0) v_percent     = 1;       // 0%일경우 그래프가 틀려짐
                j++;
              } else {
                v_date_month  = "";
                v_date_day    = "";
                v_date_time   = "";
                v_cnt         = 0;
                v_percent     = 1;
              }

              while (StringManager.toInt(v_date_time)  > (i-1)) {
                  v_time_dsp = (i-1) + ":00 ~ " + (i) +":00";

%>
          <tr>
            <td height="25" align="center" bgcolor="#EEEEEE"><%=v_time_dsp%></td>
            <td bgcolor="#F7F7F7">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="14" background="/images/admin/system/statistics_line.gif" width="1%">&nbsp;</td>
                  <td style="padding-left:5px">0회</td>
                </tr>
              </table>
            </td>
          </tr>

<%
                    i++;
                }

          v_time_dsp = (i-1) + ":00 ~ " + (i) +":00";
%>
          <tr>
            <td height="25" align="center" bgcolor="#EEEEEE"><%=v_time_dsp%></td>
            <td bgcolor="#F7F7F7">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="14" background="/images/admin/system/statistics_line.gif" width="<%=v_percent%>%">&nbsp;</td>
                  <td style="padding-left:5px"><%=v_cnt%>회</td>
                </tr>
              </table>
            </td>
          </tr>
<%
            }
%>

        </table>
        <!----------------- 시간대별 접속자수 끝 ----------------->


       <br>

      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
