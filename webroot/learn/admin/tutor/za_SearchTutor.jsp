<%
//**********************************************************
//  1. 제      목: 회원 정보 보기
//  2. 프로그램명: za_SearchTutor.jsp
//  3. 개      요: 회원 정보(팝업)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 23
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_searchtext= box.getString("p_mode2");
    int     v_pageno    = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");

    String v_membergubun = "";
    String v_resno       = "";  //주민등록번호
    String v_name        = "";  //이름
    String v_email       = "";  //이메일
    String v_hometel     = "";
    String v_comptel     = "";
    String v_handphone   = "";
    String v_comp        = "";
    String v_compnm      = "";
    String v_userid      = "";
    String v_post1       = "";
    String v_post2       = "";
    String v_addr        = "";
    String v_addr2       = "";
    String v_resno1      = "";

    String v_membergubunnm2 = "";
    String v_grcode = "";
    String v_grcodenm = "";

    int    v_totalpage  = 0;
    int    i            = 0;
    String v_membergubun_view = "";

    ArrayList list = (ArrayList)request.getAttribute("searchTutor");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function selectMember(grcode, comp,compnm,userid,name,email,comptel,handphone,post1,post2,addr,addr2,resno) {
      opener.document.form1.p_compcd.value    = comp;
      opener.document.form1.p_comp.value      = compnm;
      opener.document.form1.p_email.value     = email;
      opener.document.form1.p_userid.value    = userid;
      opener.document.form1.p_name.value      = name;
      opener.document.form1.p_phone.value     = comptel;
      opener.document.form1.p_handphone.value = handphone;
      opener.document.form1.p_post1.value     = post1;
      opener.document.form1.p_post2.value     = post2;
      opener.document.form1.p_addr.value      = addr;
      opener.document.form1.p_addr2.value     = addr2;
      opener.document.form1.p_resno.value     = resno;

      opener.document.form1.p_grcode.value     = grcode;
      self.close();
    }

    function go(index) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
         document.form1.p_process.value = "SearchTutorOpenPage";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
         document.form1.p_process.value = "SearchTutorOpenPage";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_mode1"   value = "name">
    <input type = "hidden" name = "p_mode2"   value = "<%=v_searchtext%>">
    <input type = "hidden" name = "p_process" value = "<%=v_process%>">
    <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_userid"  value = "">
    <input type = "hidden" name = "p_grcode"  value = "">

<table width="522" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="520" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
          <!----------------- title 시작 ----------------->
          <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
            <tr>
              <td><img src="/images/admin/tutor/tit_compeople.gif"></td>
              <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
          </table>
          <br>
            <table width="515" class="table_out" cellspacing="1" cellpadding="0">
                <tr>
                  <td colspan="5" class="table_top_line"></td>
                </tr>
                <tr>
                  <td width="30px"  class="table_title">&nbsp;</td>
                  <td width="60px" class="table_title"><b>회원구분</b></td>
                  <td width="110px" class="table_title"><b>교육그룹명</b></td>
                  <td width="80px" class="table_title"><b>성명</b></td>
                  <td width="80px" class="table_title"><b>아이디</b></td>
                  <td width="70px" class="table_title"><b>생년월일</b></td>
                  <td class="table_title"><b>핸드폰</b></td>
                </tr>
              <!--회원구분 ID 성명 -->
<%
            for(i = 0; i < list.size(); i++) {

                DataBox dbox  = (DataBox)list.get(i);
                v_membergubun = dbox.getString("d_membergubun");
                v_comp        = dbox.getString("d_comp");
                v_compnm      = dbox.getString("d_comptext");
                v_userid      = dbox.getString("d_userid");
                v_name        = dbox.getString("d_name");
                v_email       = dbox.getString("d_email");
                v_comptel     = dbox.getString("d_comptel");
                v_handphone   = dbox.getString("d_handphone");
                v_hometel     = dbox.getString("d_hometel");
                v_post1       = dbox.getString("d_post1");
                v_post2       = dbox.getString("d_post2");
                v_addr        = dbox.getString("d_addr");
                v_addr2       = dbox.getString("d_addr2");
                v_resno       = dbox.getString("d_resno");

                v_membergubunnm2  = dbox.getString("d_membergubunnm2");
                v_grcode      = dbox.getString("d_grcode");
                v_grcodenm    = dbox.getString("d_grcodenm");

                if(!v_resno.equals(""))
                {
                    v_resno1       = dbox.getString("d_resno").substring(0,6);
                    v_resno1       = v_resno1.substring(0,2)+"/"+v_resno1.substring(2,4)+"/"+v_resno1.substring(4,6);
                }
                v_totalpage   = dbox.getInt("d_totalpage");

                v_membergubun_view = dbox.getString("d_membergubunnm");

%>
                <tr>
                  <td class="table_01"> <input type="radio" name="no" value="<%=v_userid%>" onClick="javascipt:selectMember('<%=v_grcode%>', '<%=v_comp%>','<%=v_compnm%>','<%=v_userid%>','<%=v_name%>','<%=v_email%>','<%=v_comptel%>','<%=v_handphone%>','<%=v_post1%>','<%=v_post2%>','<%=v_addr%>','<%=v_addr2%>','<%=v_resno%>')">
                  </td>
                  <td class="table_02_1"><%=v_membergubunnm2%></td>
                  <td class="table_02_1"><%=v_grcodenm%></td>
                  <td class="table_02_1"><%=v_name%></td>
                  <td class="table_02_1"><%=v_userid%></td>
                  <td class="table_02_1"><%=v_resno1%></td>
                  <td class="table_02_1"><%=v_handphone%></td>
                </tr>
                <%
        }
        if(i == 0){
%>
                <tr>
                  <td class="table_02_1" colspan="5">검색된 내용이 없습니다.</td>
                </tr>
                <% } %>
              </table>

            <!----------------- total 시작 ----------------->
            <table width="496" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right" valign="absmiddle">
                <% System.out.println("v_totalpage : " + v_totalpage);System.out.println("v_pageno : " + v_pageno); %>
                  <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
            </table>
            <!----------------- total 끝 ----------------->

            <table width="496" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top:10"><a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif" border=0></a></td>
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
