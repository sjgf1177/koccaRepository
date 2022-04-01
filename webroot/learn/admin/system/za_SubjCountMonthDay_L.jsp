<%
//**********************************************************
//  1. 제      목: 접속통계
//  2. 프로그램명 : za_CountMonthDay_L.jsp
//  3. 개      요: 접속통계 - 월일통계 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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

    String v_pre_year   = String.valueOf(StringManager.toInt(v_year)-1);
    String v_next_year  = String.valueOf(StringManager.toInt(v_year)+1);

    String v_date_year  = "";
    String v_date_month = "";
    String v_date_day   = "";
    int    v_maxcnt     = 0;
    int    v_lastday    = 0;
    int    v_cnt        = 0;
    int    v_percent    = 0;

    String v_script_month = "";
    String v_script_day   = "";

    //-----------LastDay 구하기(윤달체크)-----------------------
    int year  = Integer.parseInt(v_year);
    int month = Integer.parseInt(v_month);
    int[] arry_lastDay = {0,31,28,31,30,31,30,31,31,30,31,30,31};
    if(year%4 == 0 && year%100 == 0) arry_lastDay[2] = 29;
    v_lastday = arry_lastDay[month];

    int v_yearcount = Integer.parseInt((String)request.getAttribute("YearCnt"));
    ArrayList list1 = (ArrayList)request.getAttribute("selectList1");
    ArrayList list2 = (ArrayList)request.getAttribute("selectList2");

  //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_grseq     = box.getString("s_grseq");            //교육차수

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
    function whenSelection(p_action) {
        if (p_action=="go"){
            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
                alert("교육그룹을 선택하세요.");
                return ;
            }
            top.ftop.setPam();
        }
        document.form1.target = "_self";
        document.form1.s_action.value = p_action;
        document.form1.action = "/servlet/controller.system.SubjCountServlet";
        document.form1.p_process.value = "selectMonthDay";
        document.form1.submit();
	}
		
		//페이지 이동
    function movePage(process) {
        document.form1.action = "/servlet/controller.system.SubjCountServlet";
        document.form1.p_process.value = process;
        document.form1.submit();
    }
		
		//연간선택
    function selectPage(year) {
        document.form1.action = "/servlet/controller.system.SubjCountServlet";
        document.form1.p_date_year.value   = year;
        document.form1.p_process.value = "selectMonthDay";
        document.form1.submit();
    }

		//월간선택
    function MoveMonthDay(month) {
        document.form1.action = "/servlet/controller.system.SubjCountServlet";
        document.form1.p_date_month.value = month;
        document.form1.p_process.value  = 'selectMonthDay';
        document.form1.submit();
    }
		
		//일간선택
    function moveDayTime(day) {
        document.form1.action = "/servlet/controller.system.SubjCountServlet";
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
    <input type = "hidden" name = "p_date_day"   value = "">
    <input type = "hidden" name = "p_gubun"   value = "">
    <input type = "hidden" name = "s_action"   value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/system/title_bg.gif" class="page_title"><img src="/images/admin/system/system_title6.gif"  border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <table width="50%" cellspacing="0" cellpadding="0" class="table1" border="0">
        <tr>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('HOME')" class="c">접속통계</a></td>
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
                <td rowspan="3" class="<%=v_tab_color1%>_butt_left"></td>
                <td class="<%=v_tab_color1%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_middle">과정별 접속통계</td>
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
					  <td width="97%" colspan="2" align="left" valign="middle">
						<!------------------- 조건검색 시작 ------------------------->
						<font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                        <%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                        <%= SelectSubjBean.getSubjseq(box, false, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
					  </td>
                      <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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
            <td align="center"><font face="굴림" size="3.5">◈<b>접속통계 (월-일 통계) <%=v_year%>년 Total :<%=v_yearcount%></font></b></td>
          </tr>
        </table>
        <br>

        <!----------------- 월-일 통계, 일-시 통계, 월-시 통계, 월-요일 통계 버튼 시작 ----------------->
        <table width="37%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center"><img src="/images/admin/system/statistics1_butt.gif" width="76" height="20" border="0"></td>
            <td align="center"><a href="javascript:movePage('selectDayTime')"><img src="/images/admin/system/statistics2_butt.gif" width="76" height="20" border="0"></a></td>
            <td align="center"><a href="javascript:movePage('selectMonthTime')"><img src="/images/admin/system/statistics3_butt.gif" width="76" height="20" border="0"></a></td>
            <td align="center"><a href="javascript:movePage('selectMonthWeek')"><img src="/images/admin/system/statistics4_butt.gif" width="76" height="20" border="0"></a></td>
          </tr>
          <tr><td height="5"></td></tr>
        </table>
        <!----------------- 월-일 통계, 일-시 통계, 월-시 통계, 월-요일 통계 버튼 끝 ----------------->
        <br>

        <!----------------- 년-월 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="1">
          <tr>
            <td align="right"><a href="javascript:selectPage('<%=v_pre_year%>')"><img src="/images/admin/system/system_left_arrow.gif" width="17" height="17" border="0"></a></td>
            <td align="center"><b><font size="3"><%=v_year%>년</font></b></td>
            <td><a href="javascript:selectPage('<%=v_next_year%>')"><img src="/images/admin/system/system_right_arrow.gif" width="17" height="17" border="0"></a></td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="center"><b><font size="3"><%=v_month%>월</font></b></td>
            <td>&nbsp;</td>
          </tr>
        </table>
       <!-----------------   년-월 끝   ----------------->
		<br>
       <!----------------- 접속자수 시작 ----------------->
        <table width="97%" border="0" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="13" height="3" bgcolor="#0075AD"></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>월</b></td>
<%
        for(int i = 1; i <= 12; i++) {
            if (i < 10) {
                v_script_month = "0" + String.valueOf(i);
            } else {
                v_script_month = String.valueOf(i);
            }
            out.println ("<td class='table_title' width='7%'><b><a href=\"javascript:MoveMonthDay('"+v_script_month+"')\">"+ i +"월</b></td>");
        }
%>          </tr>
          <tr>
            <td class="table_01" height="25" >접속자수</td>
<%
            int j = 0;
            v_date_month = "";
            v_cnt        = 0;

            for(int i = 1; i <= 12; i++) {
              if (j < list1.size()) {
                CountData data1  = (CountData)list1.get(j);

                v_date_month  = data1.getDate_month();
                v_cnt         = data1.getCnt();

                j++;
              } else {
                v_date_month  = String.valueOf(i);
                v_cnt         = 0;
              }

              while (StringManager.toInt(v_date_month) > i) {
                out.println ("<td class='table_02_1'>"+0+"</td>");
                i++;
              }

              out.println ("<td class='table_02_1'>"+v_cnt+"</td>");
            }
%>

          </tr>
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
            <td height="25" class="table_title" width="20%"><b>일</b></td>
            <td class="table_title"><b>[<%=v_year%>년 <%=v_month%>월]  아래 날짜를 클릭 하면 해당 날짜의 [일/시] 통계가 출력 됩니다.</b></td>
          </tr>

<%
            // 최대값 구하기
            for(int i = 0; i < list2.size(); i++) {
                 CountData data2  = (CountData)list2.get(i);
                 v_cnt         = data2.getCnt();
                 if (v_maxcnt < v_cnt)  v_maxcnt = v_cnt;
            }

            j = 0;
            for(int i = 1; i <= v_lastday; i++) {

              if (j < list2.size()) {
                CountData data3  = (CountData)list2.get(j);

                v_date_day    = data3.getDate_day();
                v_cnt         = data3.getCnt();
                v_percent     = (v_cnt*90)/v_maxcnt;
                if (v_percent ==0) v_percent     = 1;       // 0%일경우 그래프가 틀려짐
                j++;
              } else {
                v_date_day    = String.valueOf(i);
                v_cnt         = 0;
                v_percent     = 1;
              }

              while (StringManager.toInt(v_date_day)  > i) {

                  if (i < 10) {
                      v_script_day = "0" + String.valueOf(i);
                  } else {
                      v_script_day = String.valueOf(i);
                  }

%>

          <tr>
            <td height="25" align="center" bgcolor="#EEEEEE"><a href="javascript:moveDayTime('<%=v_script_day%>')"><%=i%>일</a></td>
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

%>
          <tr>
            <td height="25" align="center" bgcolor="#EEEEEE"><a href="javascript:moveDayTime('<%=v_date_day%>')"><%=StringManager.toInt(v_date_day)%>일</a></td>
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
