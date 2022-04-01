<%
//**********************************************************
//  1. 제      목: PROJECT COP LIST
//  2. 프로그램명: zu_ProjectCOP_U.jsp
//  3. 개      요: 리포트 COP그룹 동료평가 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 05
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_lesson    = box.getString("p_lesson");
    String  v_reptype   = box.getString("p_reptype");
    String  v_projgrp   = box.getString("p_projgrp");
    int     v_ordseq    = box.getInt("p_ordseq");
    String  v_status    = "";
    String  v_name      = "";
    String  v_title     = "";
    String  v_indate    = "";
    int     v_score     =  0;
    int     v_score2    =  0;
    int     i           =  0;
    ArrayList list      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("projectJudgePage");
%>
<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenRrojectSubmit(projgrp,status) {
        form = document.form1;
        form.action='/servlet/controller.study.ProjectServlet';
        form.p_process.value = 'ProjectHandlingPage';
        form.p_projgrp.value = projgrp;
        form.p_status.value = status;
        document.form1.submit();
    }
    function whenProjectCopJudge() {
        form = document.form1;
          if (chkSelected() > 0) {
            alert('평가 점수를 정확히 입력하세요');
            return;
          }
        form.action='/servlet/controller.study.ProjectServlet';
        form.p_process.value = 'ProjectJudge';
        document.form1.submit();
    }

    function chkSelected() {
      var selectedcnt = 0;
      if(document.form1.all['p_score'] == '[object]') {
        if (document.form1.p_score.length > 0) {
          for (i=0; i<document.form1.p_score.length; i++) {
            if (document.form1.p_score[i].value == '0') {
    	      selectedcnt++;
    	    }
          }
        } else {
          if (document.form1.p_score.checked == '0') {
    	    selectedcnt++;
          }
        }
      }
      return selectedcnt;
    }
-->
</SCRIPT>
</head>


<body topmargin="0" leftmargin="0">
<form name="form1" method="post">
<input type="hidden" name="p_process">
<input type="hidden" name="p_subj" value="<%=v_subj%>">
<input type="hidden" name="p_year" value="<%=v_year%>">
<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
<input type="hidden" name="p_ordseq" value="<%=v_ordseq%>">
<input type="hidden" name="p_lesson" value="<%=v_lesson%>">
<input type="hidden" name="p_reptype" value="<%=v_reptype%>">
<input type="hidden" name="p_projgrp">
<input type="hidden" name="p_status">

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr>
    <td>
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr>
            <td align="center" valign="top" class="body_color"> <br> 
              <!----------------- 타이틀 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
                <tr> 
                  <td><img src="/images/user/study/report_title.gif" alt="레포트"></td>
                  <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
                  <td><img src="/images/user/study/gongi_tail.gif"></td>
                </tr>
              </table>
              <!----------------- 타이틀 끝 ---------------->
            <br>

            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr>
                <td width="13" valign="top"><img src="/images/user/study/icon2.gif" width="13" height="11" border="0"></td>
                <td><b>동일 class 학습자 제출물 평가하기</b></td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>

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
                      <td class="board_title_bg1" height="28" width="10%">제출자</td>
                      <td class="board_title_bg1" height="28">제 목</td>
                      <td class="board_title_bg1" height="28" width="13%">제출일</td>
                      <td class="board_title_bg1" height="28" width="13%">평가점수</td>
                      <td class="board_title_bg1" height="28" width="15%">평가</td>
                    </tr>
        <%
            for(i = 0; i < list.size(); i++) {
                ProjectData data  = (ProjectData)list.get(i);
                v_name      = data.getName();
                v_projgrp   = data.getProjgrp();
                v_title     = data.getTitle();
                v_indate    = data.getIndate();
                v_score     = data.getScore();
                v_score2    = data.getScore2();
                if(v_indate != null){ v_indate = FormatDate.getFormatDate(v_indate,"yyyy/MM/dd");
                }else{                v_indate = "";    }
                if(v_user_id.equals(v_projgrp)){    v_status = "Y";
                }else{                              v_status = "N";   }

         %>
                    <tr>
                      <td class="board_text1" height="27"><%= list.size() - i %></td>
                      <td class="board_text1" height="27"><%= v_name %></td>
                      <td class="board_text3" height="27">
                      <a href="javascript:whenRrojectSubmit('<%=v_projgrp%>','<%=v_status%>')"><%=v_title%></a></td>
                      <td class="board_text1" height="27"><%= v_indate %></td>
                      <td class="board_text1" height="27"><%= v_score %></td>
                      <td class="board_text1" height="27">
                        <input type="text" name="p_score" maxlength="30" size="12" class="input" value="<%= v_score2 %>">
                        <input type="hidden" name="p_chk" value="<%=v_projgrp%>">
                      </td>
                    </tr>
            <% } %>
                  </table>

                </td>
              </tr>
            </table>
            <!----------------- 리포트  리스트 끝 ---------------->

            <!----------------- 내목록으로, 저장, 닫기 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr>
                <td height="11"></td>
              </tr>
              <tr>
                  <td align="right"> <a href="javascript:history.back()"><img src="/images/user/study/my_list_butt1.gif"  border="0"></a></td>
                <td align="right" width="65">
                <a href="javascript:whenProjectCopJudge()"><img src="/images/user/button/btn_save.gif"  alt="저장" border="0"></a>
                </td>
                <td align="right" width="65">
                <a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" alt="닫기" border="0"></a></td>
              </tr>
            </table>
            <!----------------- 내목록으로, 저장, 닫기 버튼 끝 ---------------->
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
