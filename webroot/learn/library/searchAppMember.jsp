<%
//**********************************************************
//  1. 제      목: 회원 검색 리스트
//  2. 프로그램명 : searchMember.jsp
//  3. 개      요: 회원 검색 리스트(팝업)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
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

    //String  v_search     = box.getString("p_search");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_seq        = box.getString("p_seq");
    String  v_gubun      = box.getStringDefault("p_gubun","name");

    String v_userid  = "";
    String v_name    = "";
    String v_resno   = "";
    String v_email   = "";
    String v_cono    = "";
    String v_jikwi   = "";
    String v_jikwinm = "";
    String v_comp    = "";
    String v_compnm  = "";
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>
<html>
<head>
<title>▒ 회원 ▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function init() {
        this.moveTo(0,0);
        this.resizeTo(560,470);
    }

    function selectMember(userid, name, email, cono) {
        opener.document.form1.p_appname<%=v_seq%>.value = name;
        opener.document.form1.p_appid<%=v_seq%>.value = userid;
        opener.document.form1.p_appmail<%=v_seq%>.value = email;
        opener.document.form1.p_appcono<%=v_seq%>.value = cono;
        
        self.close();
    }

    function enter(e) {
        if (e.keyCode =='13'){
            search();
        }
    }

    function search() {
        if (document.form1.p_searchtext.value == "") {
            alert("검색어를 입력해주세요");
            return;
        }

         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "appmember";
         document.form1.submit();
    }

    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "appmember";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.library.SearchServlet";
         document.form1.p_process.value = "appmember";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onLoad='init();'>
<form name = "form1" method = "post">
    <input type= "hidden" name= "p_process" value= "<%= v_process %>">
    <input type= "hidden" name= "p_pageno"  value= "<%= v_pageno %>">

<table width="532" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="530" height="420" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
            <table width="510" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
              <tr>
                <td height="10"></td>
              </tr>
                <td align="right">
                  <select name="p_gubun">
                    <option value='name' <% if (v_gubun.equals("name")) out.println("selected"); %> >이름</option>
                    <option value='cono' <% if (v_gubun.equals("cono")) out.println("selected"); %> >사번</option>
                  </select>
                  <input name="p_searchtext" type="text" class="input" value='<%=v_searchtext%>' onkeypress=enter(event)>
                </td>
                <td width="45" align="right" valign="bottom">
                <a href="javascript:search()"><img src="/images/admin/system/search1_butt.gif" border="0"></a>
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
                <td width="46%" class="table_title"><b>소속</b></td>
                <td width="8%" class="table_title"><b>직위</b></td>
                <td width="12%" class="table_title"><b>ID</b></td>
                <td width="12%" class="table_title"><b>사번</b></td>
                <td width="12%" class="table_title"><b>성명</b></td>
              </tr>
<%
         int i = 0;;

            for(i = 0; i < list.size(); i++) {

                SearchData data  = (SearchData)list.get(i);

                v_userid  = data.getCode();
                v_name    = data.getCodenm();
                v_resno   = data.getTmp1();;
                v_email   = data.getTmp2();
                v_cono    = data.getTmp3();
                v_jikwi   = data.getTmp4();
                v_jikwinm = data.getTmp5();
                v_comp    = data.getTmp6();
                v_compnm  = data.getTmp7();         // 회사/사업부/부서
                v_dispnum   = data.getDispnum();
                v_totalpage = data.getTotalpagecount();

%>
              <tr>
                <td height="26" class="table_01"> <input type="radio" name="no" value="<%=v_userid%>" onClick="javascipt:selectMember('<%=v_userid%>' ,'<%=v_name%>','<%=v_email%>' ,'<%=v_cono%>')"></td>
                <td class="table_02_1"><%=(i+1)%></td>
                <td class="table_02_1"><%=v_compnm%></td>
                <td class="table_02_1"><p><%=v_jikwinm%></p></td>
                <td class="table_02_1"><%=v_userid%></td>
                <td class="table_02_1"><%=v_cono%></td>
                <td class="table_02_1"><%=v_name%></td>
              </tr>
<%
          }
%>
            <% if(i == 0){ %>
              <tr>
                <td height='40' colspan="7" class="table_02_1">검색된 회원이 없습니다</td>
              </tr>
            <% } %>
            </table>

            <!----------------- total 시작 ----------------->
            <table width="510" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right" valign="absmiddle">
                  <%= PageUtil.printPageList1(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
            </table>
            <!----------------- total 끝 ----------------->

            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10"><a href='javascript:self.close()'><img src="/images/admin/system/close1_butt.gif"  border=0></a></td>
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
