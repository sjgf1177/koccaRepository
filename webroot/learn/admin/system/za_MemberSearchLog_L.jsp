<%
//**********************************************************
//  1. 제      목: 인사DB검색결과 로그보기
//  2. 프로그램명 : za_MemberSearchLog.jsp
//  3. 개      요: 인사DB검색결과 로그보기
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2005. 02. 05
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int  v_pageno     = box.getInt("p_pageno");
    if (v_pageno == 0) v_pageno = 1;

 
    

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list = (ArrayList)request.getAttribute("logList");

%>
<html>
<head>
<title>▒ 회원 ▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.system.MemberSearchServlet";
         document.form1.p_process.value = "logView";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.system.MemberSearchServlet";
         document.form1.p_process.value = "logView";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
   
<form name="form1" method="post" action="/servlet/controller.system.MemberSearchServlet">
          <input type="hidden" name="p_process"  value="logView">
		  <input type="hidden" name="p_action"  value="go">
          <input type="hidden" name="p_pageno" value="">
<table width="790" height="137" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
            <td align="center" valign='top' bgcolor="#FFFFFF">
			  


      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title23.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
	  <br>
              <table width="770" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="5" class="table_top_line"></td>
                </tr>
                <tr> 
                  <td width="10%" height="26" class="table_title"><b>조회날짜</b></td>
                  <td width="13%" class="table_title"><b>ID</b></td>
                  <td width="14%" class="table_title"><b>성명</b></td>
                  <td width="52%" class="table_title"><b>검색조건</b></td>
                  <td width="11%" class="table_title"><b>결과수</b></td>
                </tr>
                <%
         int i = 0;;

            for(i = 0; i < list.size(); i++) {

                DataBox dbox = (DataBox)list.get(i); 
				v_dispnum   = dbox.getInt("d_Dispnum");
                v_totalpage = dbox.getInt("d_Totalpagecount");

%>
                <tr> 
                  <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_logdate"),"yyyy/MM/dd")%></td>
                  <td class="table_02_1"><%=dbox.getString("d_userid")%></td>
                  <td class="table_02_1"><%=dbox.getString("d_name")%></td>
                  <td class="table_02_1"><%=dbox.getString("d_srchword")%></td>
                  <td class="table_02_1"><%=dbox.getInt("d_rowcnt")%></td>
                </tr>
                <%
          }
%>
                <% if(i == 0){ %>
                <tr> 
                  <td height='40' colspan="5" class="table_02_1">Download된 결과가 없습니다.</td>
                </tr>
                <% } %>
              </table>

            <!----------------- total 시작 ----------------->
            <table width="770" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right" valign="absmiddle">
                  <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
            </table>
            <!----------------- total 끝 ----------------->

            <table width="770" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10"><a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif"  border=0></a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    



<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
