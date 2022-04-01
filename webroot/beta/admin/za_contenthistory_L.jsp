<%
//**********************************************************
//  1. 제      목: 과정모의학습-콘텐츠 수정 히스토리 리스트
//  2. 프로그램명: za_SubjectStudy_L.jsp
//  3. 개      요: 과정모의학습-콘텐츠 수정 히스토리 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: S.W.Kang
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box  = (RequestBox)request.getAttribute("requestbox");
    String  v_userid    = "";
    String  v_name      = "";
    
    String  v_indate    = "";
    String  v_title     = "";
    int     v_seq       = 0;
    int     v_levels    = 0;
    int     v_cnt       = 0;
    int     i           = 0;
    int     v_dispnum   = 0;
    int     v_totalpage = 0;
    int     v_rowcount  = 0;
    int     v_upfilecnt = 0;
    int     v_filecnt   = 0;
    int     v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String s_userid     = box.getSession("userid");
    String s_username   = box.getSession("name");
    String v_class1     = "";
    String v_class2     = "";
    
    ArrayList list = (ArrayList)request.getAttribute("selectStudyDocList");

    String  v_subj       = box.getString("p_subj");       //과정&코스
    String  v_subjnm     = box.getString("p_subjnm");
    String  v_comp       = box.getString("p_comp");
    String  ss_action    = box.getString("s_action");   
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function listPage() {
        document.form1.action = "/servlet/controller.beta.SubjectSimulationServlet";
        document.form1.p_process.value = "listPage";
        document.form1.p_pageno.value = "1";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
   <form name="form1" method="post" action="">
    <input type= "hidden" name="p_process">
    <input type= "hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
	<input type= "hidden" name="p_subj"      value="<%=v_subj%>"> <!--in relation to select-->
    <input type= "hidden" name="p_subjnm"    value="<%=v_subjnm %>">
    <input type  "hidden" name="p_comp"      value="<%=v_comp%>">
    <input type= "hidden" name="p_pageno"    value="<%=v_pageno %>">
    <input type= "hidden" name="p_search"    value="<%=v_search%>">
    <input type= "hidden" name="p_seq"       value="">
    <input type= "hidden" name="p_upfilecnt" value="">
    <input type= "hidden" name="p_userid"    value="">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>   
            <td background="/images/admin/community/title_bg.gif"><img src="/images/admin/study/study_title26.gif" width="259" height="36" border="0"></td>
        </tr>
      </table>        
      <!----------------- title 끝 ----------------->
        <br>
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">과정명 : <%=v_subjnm%></td>
            <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">콘텐츠회사 : <%=v_comp%></td>            
          </tr>
        </table>
        <br>
        <!----------------- 과정모의학습_콘텐츠수정 히스토리 리스트 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" height="25" class="table_title"><b>NO</b></td>
            <td width="30%" class="table_title"><b>Lesson</b></td>
            <td width="15%" class="table_title"><b>ObjectID</b></td>
            <td width="15%" class="table_title"><b>Object명</b></td>
            <td width="20%" class="table_title"><b>설명</b></td>
            <td width="15%" class="table_title"><b>수정일</b></td>
          </tr>

<%  if(i == 0){   %>    
                    <tr> 
                      <td class="table_02_1" colspan="6">등록된 내용이 없습니다.</td>
                    </tr>    
<%    } %>
        </table>
        <!----------------- 과정 자료실  리스트 끝 ----------------->
        <br>
        <br>
      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
