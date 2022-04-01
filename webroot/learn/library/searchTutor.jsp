<%
//**********************************************************
//  1. 제      목: 교수/강사 검색 리스트
//  2. 프로그램명 : searchTutor.jsp
//  3. 개      요: 교수/강사 검색 리스트(팝업)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2004. 11. 10
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

    String  v_search     = box.getString("p_gubun");
    String  v_searchtext = box.getString("p_key1");
    String  v_key2       = box.getString("p_key2");

    String v_userid  = "";
    String v_name    = "";
    String v_resno   = "";
    String v_email   = "";
    String v_phone   = "";

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>
<html>
<head>
<title>▒ 교수/튜터 ▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function init() {
        this.moveTo(0,0);
        this.resizeTo(575,485);
    }

    function selectTutor(userid, name, resno, email, phone, tmp1, tmp2, tmp3, tmp4, tmp5) {
        opener.receiveTutor(userid, name, resno, email, phone, tmp1, tmp2, tmp3, tmp4, tmp5);
        self.close();
    }

    function enter(e) {
        if (e.keyCode =='13'){
            search();
        }
    }

    function search() {
        if (document.form1.p_key1.value == "") {
            alert("검색어를 입력해주세요");
            return;
        }

         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "tutor";
         document.form1.submit();
    }

    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "tutor";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "tutor";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onLoad='init();'>

      
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
    <input type = "hidden" name = "p_userid"  value = "">
    <input type = "hidden" name = "p_key2"  value = "<%=v_key2%>">


<table width="532" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">


  <tr>
    <td><table width="530" height="435" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
          <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            
          <td><img src="../../images/admin/course/tit_memsearch.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <br>
            <table width="510" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
              <tr>
                <td height="10"></td>
              </tr>
                <td align="right">
                  <select name="p_gubun">
                    <option value='name' <% if (v_search.equals("name")) out.println("selected"); %> >이름</option>
                    <option value='userid' <% if (v_search.equals("userid")) out.println("selected"); %> >ID</option>
                  </select>
                  <input name="p_key1" type="text" class="input" value='<%=v_searchtext%>' onkeypress=enter(event)>
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
                <td colspan="7" class="table_top_line"></td>
              </tr>
              <tr>
                <td width="5%" height="26" class="table_title">&nbsp;</td>
                <td width="5%" class="table_title"><b>NO</b></td>
                <td width="18%" class="table_title"><b>ID</b></td>
                <td width="18%" class="table_title"><b>성명</b></td>
                <td width="31%" class="table_title"><b>email</b></td>
                <td width="23%" class="table_title"><b>전화번호</b></td>
              </tr>
<%
         int i = 0;;

            for(i = 0; i < list.size(); i++) {

                SearchData data  = (SearchData)list.get(i);

                v_userid        = data.getCode();
                v_name          = data.getCodenm();
                v_resno         = data.getTmp1();;
                v_email         = data.getTmp2();
                v_phone         = data.getTmp3();
                v_dispnum       = data.getDispnum();
                v_totalpage     = data.getTotalpagecount();

%>
              <tr>
                <td height="26" class="table_01"> <input type="radio" name="no" value="<%=v_userid%>" onClick="javascipt:selectTutor('<%=v_userid%>' ,'<%=v_name%>' ,'<%=v_resno%>' ,'<%=v_email%>' ,'<%=v_phone%>' ,'' , '', '', '', '')"></td>
                <td class="table_02_1"><%=(i+1)%></td>
                <td class="table_02_1"><%=v_userid%></td>
                <td class="table_02_1"><p><%=v_name%></p></td>
                <td class="table_02_1"><%=v_email%></td>
                <td class="table_02_1"><%=v_phone%></td>
              </tr>
<%
          }
%>
            <% if(i == 0){ %>
              <tr>
                <td height='40' colspan="7" class="table_02_1">검색된 튜터/강사가 없습니다</td>
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
