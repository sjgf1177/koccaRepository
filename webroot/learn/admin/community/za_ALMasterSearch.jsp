<%
//**********************************************************
//  1. 제      목: 액션러닝 시샵 검색 리스트
//  2. 프로그램명: za_ALMasterSearch.jsp
//  3. 개      요: 검색 리스트(팝업)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 21
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int     v_pageno    = box.getInt("p_pageno");
    if (v_pageno == 0){     v_pageno = 1;   }
    
    String v_subj       = box.getString("p_subj");
    String v_year       = box.getString("p_year");
    String v_subjseq    = box.getString("p_subjseq");
    
    String v_compnm   ="";  //소속
    String v_companynm="";  //회사
    String v_deptnm  = "";  //부서
    String v_jikwinm = "";  //직위
    String v_id      = "";  //아이디
    String v_cono    = "";  //사번
    String v_name    = "";  //이름

    int v_dispnum   = 0;    //총게시물수
    int v_totalpage = 0;    //게시물총페이지수
    ArrayList list          =  null;
    
    list = (ArrayList)request.getAttribute("searchMaster");

%>
<html>
<head>
<title>::: 쌍용자동차 사이버 연수원 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/scriptcresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function selectMaster(id,name) {
      opener.document.form1.p_masterID.value =     id;
      opener.document.form1.p_masterName.value =      name;
      self.close();
    }

    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
         document.form1.p_process.value = "SearchMasterOpenPage";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
         document.form1.p_process.value = "SearchMasterOpenPage";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
    <input type = "hidden" name = "p_id"      value = "">
    <input type = "hidden" name = "p_subj"      value = "<%= v_subj %>">  
    <input type = "hidden" name = "p_year"      value = "<%= v_year %>">  
    <input type = "hidden" name = "p_subjseq"   value = "<%= v_subjseq %>">      

<table width="522" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="520" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
            <table width="500" border="0" cellspacing="1" cellpadding="0">
              <tr> 
                <td colspan="6" height="3" bgcolor="#0075AD"></td>
              </tr>
              <tr> 
                <td width="8%"  class="table_title">&nbsp;</td>
                <td width="8%"  class="table_title"><b>NO</b></td>
                <td width="52%" class="table_title"><b>소속</b></td>
                <td width="8%"  class="table_title"><b>직위</b></td>
                <td width="12%" class="table_title"><b>ID</b></td>
                <td width="12%" class="table_title"><b>성명</b></td>
              </tr>
<%
            for(int i = 0; i < list.size(); i++) {

                DataBox dbox  = (DataBox)list.get(i);
                v_compnm      = dbox.getString("d_compnm");
                v_companynm   = dbox.getString("d_companynm");
                v_deptnm      = dbox.getString("d_deptnm");   
                v_jikwinm     = dbox.getString("d_jikwinm");  
                v_id          = dbox.getString("d_userid");       
                v_name        = dbox.getString("d_name");     
                v_dispnum     = dbox.getInt("d_dispnum");
                v_totalpage   = dbox.getInt("d_totalpage");

%>
              <tr> 
                <td height="26" class="table_01"> 
                <input type="radio" name="no" value="<%=v_id%>" 
                onClick="javascipt:selectMaster('<%=v_id%>','<%=v_name%>')">
                </td>
                <td align="center" class="table_02_1"><%=(i+1)%></td>
                <td align="center" class="table_02_1"><%=v_compnm%></td>
                <td align="center" class="table_02_1"><p><%=v_jikwinm%></p></td>
                <td align="center" class="table_02_1"><%=v_id%></td>
                <td align="center" class="table_02_1"><%=v_name%></td>
              </tr>
<%
        }
%>
            </table>

            <!----------------- total 시작 ----------------->
            <table width="496" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right" valign="absmiddle"> 
                  <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
            </table>
            <!----------------- total 끝 ----------------->

            <table width="496" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="right" style="padding-top=10"><a href='javascript:self.close()'><img src="/images/admin/tutor/close1_butt.gif" width="37" height="18" border=0></a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%@ include file = "/jsp/library/getJspName.jsp" %> 
</form>
</body>
</html>
