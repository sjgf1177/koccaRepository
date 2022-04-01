<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");
    int  v_pageno     = box.getInt("p_pageno");
    if (v_pageno == 0) v_pageno = 1;

    String  v_search     = box.getString("p_gubun");
    String  v_searchtext = box.getString("p_searchtext");

    String v_comp     = ""; 
    String v_compnm   = ""; 
    String v_name     = ""; 
    String v_telno    = ""; 
    String v_faxno    = ""; 
    String v_email    = ""; 

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list = (ArrayList)request.getAttribute("selectList");
%>
<html>
<head>
<title>▒ 회사 ▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function init() {
        this.moveTo(0,0);
        this.resizeTo(575,515);
    }

    function selectComp(comp , compnm, name, telno, faxno, email) {
        opener.receiveComp(comp , compnm, name, telno, faxno, email);
        self.close();
    }
    function search() {
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "searchComp";
         document.form1.submit();
    }

    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "searchComp";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "searchComp";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onLoad='init();'>
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">


<table width="532" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="530" height="420" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
		  <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            
          <td><img src="../../images/admin/course/tit_companyname.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <br>
            <table width="510" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
              <tr>
                <td height="10"></td>
              </tr>
                <td align="right"> 회사명 :
                  <input name="p_searchtext" type="text" class="input" value='<%=v_searchtext%>' onkeypress="javascript:fnKeyPressEnter(event, 'search');">
                </td>
                <td width="50" align="right" valign="bottom">
                <a href="javascript:search()"><img src="/images/admin/button/search3_butt.gif" border="0"></a>
                </td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </table>
              <table width="510" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr> 
                  <td width="12%" height="26" class="table_title">&nbsp;</td>
                  <td width="88%" class="table_title"><b>회사명</b></td>
                </tr>
                <%
                if(list !=null && list.size() >0){
                    for(int i = 0; i < list.size(); i++) {

		                DataBox dbox  = (DataBox)list.get(i);
		                v_comp     = dbox.getString("d_comp");  
		                v_compnm   = dbox.getString("d_compnm");
		                v_name     = dbox.getString("d_name");  
		                v_telno    = dbox.getString("d_telno"); 
		                v_faxno    = dbox.getString("d_faxno"); 
		                v_email    = dbox.getString("d_email"); 

		                v_dispnum   = dbox.getInt("d_dispnum");
		                v_totalpage = dbox.getInt("d_totalpage");
%>
                <tr> 
                  <td height="26" class="table_01"> 
                  <input type="radio" name="no" value="<%=v_comp%>" 
                  onClick="javascipt:selectComp('<%=v_comp%>', '<%=v_compnm%>', '<%=v_name%>', '<%=v_telno%>', '<%=v_faxno%>', '<%=v_email%>')">
                  </td>
                  <td class="table_02_1"><p><%=v_compnm%></p></td>
                </tr>
                <%
                     }
                    } else{ %>
                <tr> 
                  <td height='40' colspan="2" class="table_02_1">검색된 회사가 없습니다</td>
                </tr>
                <% } %>
              </table>

            <!----------------- total 시작 ----------------->
            <table width="510" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right" valign="absmiddle">
                  <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
            </table>
            <!----------------- total 끝 ----------------->

            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10"><a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif"  border=0></a></td>
              </tr>
            </table>
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
