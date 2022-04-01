<%
//**********************************************************
//  1. 제      목: PROJECT LIST
//  2. 프로그램명: zu_Project_L.jsp
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
    String  v_process   = box.getString("p_process");    
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");    
    String  v_lesson    = "";
    String  v_title     = "";
    String  v_reptype   = "";
    String  v_projgrp   = "";    
    String  v_rep_value = "";
    String  v_jud_value = "";
    int     v_ordseq    =  0;    
    int     v_projseq   =  0;
    int     v_score     =  0;
    int     v_score2    =  0;
    int     v_cnt       =  0;
    int     i           =  0;
    ArrayList list      = null;
    //DEFINED class&variable END       
   
    list = (ArrayList)request.getAttribute("projectList");     
/*
    SubjseqData dataseq = new SubjseqData();
    GrseqBean bean   = new GrseqBean();
    
    dataseq = bean.SelectSubjseqData(box);
*/
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
    function whenRrojectSubmit(projseq, ordseq,lesson,reptype,projgrp,getcnt) {
        form = document.form1;
        form.action='/servlet/controller.study.ProjectServlet';
        form.p_process.value = 'ProjectHandlingPage';        
        form.p_projseq.value = projseq;        
        form.p_ordseq.value = ordseq;
        form.p_lesson.value = lesson;
        form.p_reptype.value = reptype;
        form.p_projgrp.value = projgrp;
        form.p_getcnt.value = getcnt;        
        document.form1.submit();
    }    
    function whenCoworkerJudge(ordseq,lesson,reptype,projgrp) {
        form = document.form1;
        form.action='/servlet/controller.study.ProjectServlet';
        form.p_process.value = 'ProjectJudgePage';        
        form.p_ordseq.value = ordseq;
        form.p_lesson.value = lesson;
        form.p_reptype.value = reptype;
        form.p_projgrp.value = projgrp;
        document.form1.submit();
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
<input type="hidden" name="p_projseq">
<input type="hidden" name="p_ordseq">
<input type="hidden" name="p_lesson">
<input type="hidden" name="p_reptype">
<input type="hidden" name="p_projgrp">
<input type="hidden" name="p_getcnt"><!--제출갯수-->


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
                        <td class="board_title_bg1" height="28">제 목</td>
                        <td class="board_title_bg1" width="12%" height="28">제출여부</td>
                        <td class="board_title_bg1" width="15%" height="28">취득점수/만점</td>
                        <td class="board_title_bg1" width="11%" height="28">기타</td>
                      </tr>
                      <% 
            for(i = 0; i < list.size(); i++) {        
                ProjectData data  = (ProjectData)list.get(i);                             
                v_ordseq        = data.getOrdseq();     
                v_projseq       = data.getProjseq();
                v_lesson        = data.getLesson();     
                v_title         = data.getTitle();      
                v_reptype       = data.getReptype();    
                v_projgrp       = data.getProjgrp();        
                v_score         = data.getScore();      //만점
                v_score2        = data.getScore2();     //취득점수
                v_cnt           = data.getCnt();
                if(v_cnt == 0){ 
                    v_rep_value = "미제출";    
                    v_jud_value = "미평가";
                }else{                  
                    v_rep_value = "제출";  
                    v_jud_value = v_score2 + "";
                 }
                    
              if(v_reptype.equals("C")){
          %>
                      <% }else{ %>
                      <tr> 
                        <td class="board_text1" height="27"><%= i + 1 %></td>
                        <td class="board_text3" height="27"><a href="javascript:whenRrojectSubmit('<%=v_projseq%>', '<%=v_ordseq%>','<%=v_lesson%>','<%=v_reptype%>','<%=v_projgrp%>', '<%=v_cnt%>')"><%=v_title%></a></td>
                        <td class="board_text1" height="27"><font color="#EC0000"><%=v_rep_value%></font></td>
                        <td class="board_text1" height="27"><font color="#EC0000"><%=v_jud_value%></font> / <%=v_score%></td>
                        <td class="board_text1" height="27"> <a href="javascript:whenRrojectSubmit('<%=v_projseq%>', '<%=v_ordseq%>','<%=v_lesson%>','<%=v_reptype%>','<%=v_projgrp%>', '<%=v_cnt%>')"><img src="/images/user/button/report_send_butt1.gif"  border="0"></a> 
                        </td>
                      </tr>
                      <% }
            } %>
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
