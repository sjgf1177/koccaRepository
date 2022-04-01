<%
//**********************************************************
//  1. 제      목: 동명이인 검색
//  2. 프로그램명 : za_NamesakeList.jsp
//  3. 개      요: 검색 리스트(팝업)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    String v_userid  = "";  //아이디
    String v_name    = "";  //이름
    String v_membergubunnm = "";    // 회원구분명
    String v_grcode="";

    int i = 0;
    ArrayList list          =  null;
    list = (ArrayList)request.getAttribute("PersonalNameSelectList");
    System.out.println("이름검색 list사이즈:"+list.size());
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function selectMember(userid, grcode) {
        // opener.document.form1.p_search[0].checked = true;
        // opener.document.form1.p_searchtext.value = grcode+userid;
        // opener.document.form1.p_process.value = "PersonalSelect";
        // opener.document.form1.target = opener.name;
        // opener.document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
        // opener.document.form1.submit();

        opener.fnSearchUserFromPopup(grcode, userid, "PersonalSelect");
        self.close();
    }
//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
<input type="hidden" name="p_process">
<input type="hidden" name="p_search">
<input type="hidden" name="p_searchtext">

<table width="522" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="520" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">



   <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
            <td><img src="/images/admin/study/tit_personal.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <br>
            <table width="500" border="0" cellspacing="1" cellpadding="0">
              <tr>
                <td colspan="5" height="3" bgcolor="#0075AD"></td>
              </tr>
              <tr>
                <td width="8%" height="26" align="center" bgcolor="#DDDDDD"><b>선택</b></td>
                <td width="8%" align="center" bgcolor="#DDDDDD" width="7%"><b>NO</b></td>
                <td width="40%" align="center" bgcolor="#DDDDDD"><b>회원구분</b></td>
                <td width="12%" align="center" bgcolor="#DDDDDD" width="15%"><b>ID</b></td>
                <td width="12%" align="center" bgcolor="#DDDDDD" width="15%" ><b>성명</b></td>
              </tr>
<%
            for(i=0; i < list.size(); i++) {
                MemberData data  = (MemberData)list.get(i);
                v_userid    = data.getUserid();
                v_membergubunnm      = data.getMembergubunnm();
                v_name      = data.getName();
                v_grcode      = data.getGrcode();
%>
              <tr>
                <td height="26" align="center" bgcolor="#EEEEEE">
                <input type="radio" name="no" value="<%=v_userid%>" onClick="javascipt:selectMember('<%=v_userid%>','<%=v_grcode%>')">
                </td>
                <td align="center" height="25" bgcolor="#F7F7F7"><%= list.size() - i %></td>
                <td align="center" bgcolor="#F7F7F7"><%=v_membergubunnm%></td>
                <td align="center" bgcolor="#F7F7F7"><%=v_userid%></td>
                <td align="center" bgcolor="#F7F7F7"><%=v_name%></td>
              </tr>
<%
        }
if(i == 0){ %>
              <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="5">등록된 내용이 없습니다</td>
              </tr>
         <%  } %>
            </table>
            <table width="496" border="0" cellspacing="0" cellpadding="0">
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
