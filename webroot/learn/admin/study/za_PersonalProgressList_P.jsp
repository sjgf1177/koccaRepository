 <%
//**********************************************************
//  1. 제      목: 학습창 접속 로그 리스트
//  2. 프로그램명: za_PersonalCounsel_L
//  3. 개      요: 학습창 접속 로그 보기(팝업)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 7. 5
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
	String v_lgip     = "";
	String v_ldate    = "";
    String v_day      = "";  // 접속날짜
    String v_time     = "";  // 접속시간
	int    i          = 0;

    ArrayList list = (ArrayList)request.getAttribute("PersonalProgressList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">



<table width="800" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td align="left">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr>
            <td align="center" bgcolor="#FFFFFF">
			 <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/study/tit_log.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
            <br>

<form name="form1" method = "post">
  <input type="hidden" name="p_process" value="<%=v_process%>">

        <table class="box_table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" ><b>NO</b></td>
            <td class="table_title" ><b>시작시간</b></td>
            <td class="table_title" ><b>종료시간</b></td>
            <td class="table_title" ><b>차시명</b></td>
            <td class="table_title" ><b>학습시간</b></td>
            <td class="table_title" ><b>접속 IP</b></td>
          </tr>
<%
    String v_first_edu="",  v_first_end="", v_sdesc="", v_session_time="", v_remote_ip="";

    for (i=0; i<list.size();i++) {
        DataBox dbox1   = (DataBox)list.get(i);
        v_first_edu   = dbox1.getString("d_first_edu");
        v_first_end   = dbox1.getString("d_first_end");
        v_sdesc   = dbox1.getString("d_sdesc");
        v_session_time   = dbox1.getString("d_session_time");
        v_remote_ip   = dbox1.getString("d_remote_ip");

        v_first_edu = FormatDate.getFormatDate(v_first_edu, "yyyy-MM-dd HH:mm ss");
        v_first_end = FormatDate.getFormatDate(v_first_end, "yyyy-MM-dd HH:mm ss");
%>
          <tr>
            <td class="table_01"><%=i+1%></td>
            <td class="table_02_1"><%=v_first_edu%></td>
            <td class="table_02_1"><%=v_first_end%></td>
            <td class="table_02_1"><%=v_sdesc%></td>
            <td class="table_02_1"><%=v_session_time%></td>
            <td class="table_02_1"><%=v_remote_ip%></td>
          </tr>
<%
    }
    if (i==0){
%>
          <tr>
            <td class="table_01" colspan="6">접속로그가 없습니다.</td>
          </tr>
<%
    }
%>
        </table>

            <!----------------- 닫기 버튼 시작 ----------------->
            <table cellspacing="0" cellpadding="0" class="open_table1">
              <tr>
                <td align="right" style="padding-top=10">
                <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 닫기 버튼 끝 ----------------->
              <br>




          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
