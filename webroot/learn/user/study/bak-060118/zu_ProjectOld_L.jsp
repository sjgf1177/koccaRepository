<%
//**********************************************************
//  1. 제      목: PROJECT LIST
//  2. 프로그램명: zu_ProjectOld_L.jsp
//  3. 개      요: 리포트 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 04
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process     = box.getString("p_process");

    String  v_pkey        = box.getString("p_pkey");
    String  v_userid      = box.getString("p_userid");
    String  v_filenm      = "";
    String  v_title       = "";
    String  v_submitdate    = "";
    String  v_markingdate = "";
    String  v_rscore      = "";
    String  v_redpen      = "";
    int i = 0;

    ArrayList list      = null;
    //DEFINED class&variable END
    list = (ArrayList)request.getAttribute("projectList");

%>
<html>
<head>
<title>::: REPORT :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 레포트 제출
    function whenSubmit(title) {
        form = document.form1;
        form.action='/servlet/controller.study.ProjectServlet';
        form.p_process.value = 'ProjectSelect';
        form.p_title.value = title;
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body topmargin="0" leftmargin="0">
<form name="form1" method="post">
<input type="hidden" name="p_process" value="<%=v_process%>">
<input type="hidden" name="p_pkey"    value="<%=v_pkey%>">
<input type="hidden" name="p_userid"  value="<%=v_userid%>">
<input type="hidden" name="p_title"   value="">


<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr>
    <td>
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr>
          <td align="center" valign="top" class="body_color">
          <br>
            <!----------------- 타이틀 시작 ----------------->
          <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
            <tr>
              <td><img src="/images/user/study/report_title.gif"></td>
              <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
              <td><img src="/images/user/study/gongi_tail.gif"></td>
            </tr>
          </table>
          <BR>
            <!----------------- 리포트 리스트 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr>
                <td class="board_color_line"></td>
              </tr>
            </table>

            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
              <tr>
                <td>
                  <table cellspacing="1" cellpadding="3" class="table2">
                      <tr>
                        <td class="board_title_bg1" width="5%" height="28">번호</td>
                        <td class="board_title_bg1" height="28">과제명</td>
                        <td class="board_title_bg1" width="15%" height="28">제출일</td>
                        <td class="board_title_bg1" width="15%" height="28">채점일</td>
                        <td class="board_title_bg1" width="10%" height="28">채점결과</td>
                        <td class="board_title_bg1" width="10%" height="28">강사지도</td>
                      </tr>
<%
            for(i = 0; i < list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i);
                v_filenm      = dbox.getString("d_filenm");
                v_title       = dbox.getString("d_title");
                v_submitdate    = dbox.getString("d_submitdate");
                v_markingdate = dbox.getString("d_markingdate");
                v_rscore      = dbox.getString("d_rscore");
                v_redpen      = dbox.getString("d_redpen");

                v_submitdate    = FormatDate.getFormatDate(v_submitdate,"yyyy/MM/dd");
                v_markingdate = FormatDate.getFormatDate(v_markingdate,"yyyy/MM/dd");
                if(v_redpen.length() == 0)  v_redpen = "[없음]";
                else                        v_redpen = "[있음]";
%>
                      <tr>
                        <td class="board_text1" height="27"><%= i + 1 %></td>
                        <td class="board_text3" height="27"><%=v_title%></td>
                        <td class="board_text1" height="27"><%=v_submitdate%></td>
                        <td class="board_text1" height="27"><%=v_markingdate%></td>
                        <td class="board_text1" height="27"><%=v_rscore%></td>
                        <td class="board_text1" height="27"> <a href="javascript:whenSubmit('<%=v_title%>')"><%=v_redpen%></a>
                        </td>
                      </tr>
<% 
            } 
%>
                    </table>

                </td>
              </tr>
            </table>
            <!----------------- 리포트  리스트 끝 ---------------->

            <!----------------- 돌아가기, 닫기 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr>
                <td height="11"></td>
              </tr>
              <tr>
                <td align="right">
                <a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 돌아가기, 닫기 버튼 끝 ----------------->

            <br>

          </td>
        </tr>
      </table>


    </td>
  </tr>
</table>



<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
