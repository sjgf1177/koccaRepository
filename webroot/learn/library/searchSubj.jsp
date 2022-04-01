<%
//**********************************************************
//  1. 제      목: 과목ㄴ 검색 리스트
//  2. 프로그램명 : searchSubj.jsp
//  3. 개      요: 과목 검색 리스트(팝업)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 16
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
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

    String  v_search     = box.getString("p_gubun");
    String  v_searchtext = box.getString("p_key1");
    String  v_key2       = box.getString("p_key2");

    String v_subj           = "";   
    String v_subjnm         = "";
    String v_isonoff        = "";
    String v_isonoff_value  = "";

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list = (ArrayList)request.getAttribute("selectList");
%>
<html>
<head>
<title>▒ 과정 ▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function init() {
        this.moveTo(0,0);
        this.resizeTo(560,470);
    }

    function selectSubj(subj , subjnm, tmp1, tmp2, tmp3) {
        opener.receiveSubj(subj , subjnm, tmp1, tmp2, tmp3);
        self.close();
    }

    function enter(e) {
        if (e.keyCode =='13'){
            search();
        }
    }

    function search() {
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "subj";
         document.form1.submit();
    }

    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "subj";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "subj";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onLoad='init();'>
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
    <input type = "hidden" name = "p_gubun"   value = "<%= v_search %>">
    <input type = "hidden" name = "p_key2"  value = "<%=v_key2%>">


<table width="532" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="530" height="420" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
            <table width="510" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="3"></td>
              </tr>
              <tr>
                <td align="right"> 과목명 : 
                  <input name="p_key1" type="text" class="input" value='<%=v_searchtext%>' onkeypress=enter(event)> &nbsp;
                </td>
                <td width="45" align="right" valign="top"> 
                <a href="javascript:search()"><img src="/images/admin/button/btn_inquiry.gif" border="0"></a>
                </td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </table>
            <table width="510" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td colspan="7" class="table_top_line"></td>
              </tr>
              <tr>
                <td width="5%" height="26" class="table_title">&nbsp;</td>
                <td width="5%" class="table_title"><b>NO</b></td>
                <td width="20%" class="table_title"><b>구분</b></td>
                <td width="30%" class="table_title"><b>과목코드</b></td>
                <td width="40%" class="table_title"><b>과목명</b></td>
              </tr>
<%
            int i;
            for(i = 0; i < list.size(); i++) {

                SearchData data  = (SearchData)list.get(i);
                v_subj      = data.getCode();
                v_subjnm    = data.getCodenm();
                v_isonoff   = data.getIsonoff();
                if (v_isonoff.equals("ON")) { v_isonoff_value = "사이버" ; }
                else if (v_isonoff.equals("OFF")) { v_isonoff_value = "집합" ; }
                v_dispnum   = data.getDispnum();
                v_totalpage = data.getTotalpagecount();

%>
              <tr>
                <td height="26" class="table_01"> <input type="radio" name="no" value="<%=v_subj%>" onClick="javascipt:selectSubj('<%=v_subj%>' ,'<%=v_subjnm%>','<%=v_key2%>','','')"></td>
                <td class="table_02_1"><%=(i+1)%></td>
                <td class="table_02_1"><%=v_isonoff_value%></td>
                <td class="table_02_1"><%=v_subj%></td>
                <td class="table_02_1"><%=v_subjnm%></td>
              </tr>
<%
          }
%>
            <% if(i == 0){ %>
              <tr>
                <td  height='40' colspan="7" class="table_02_1">검색된 과정이 없습니다</td>
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
