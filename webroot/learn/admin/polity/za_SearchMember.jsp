<%
//**********************************************************
//  1. 제      목: 발급대상자 검색
//  2. 프로그램명: za_searchMember.jsp
//  3. 개      요: 발급대상자 검색
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.19
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
    String  v_process = box.getString("p_process");
    String  v_mode1   = box.getString("p_mode1");
    String  v_mode2   = box.getString("p_mode2");
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

    int    v_totalpage  = 0;
    int    v_dispnum    = 0;
    int    i            = 0;
    String v_membergubun_view = "";

    ArrayList list = (ArrayList)request.getAttribute("searchMember");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<script type="text/javascript" src="/script/portal/common.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function search() {
	    document.form1.action = "/servlet/controller.polity.TaxbillAdminServlet";
        document.form1.p_pageno.value = "1";
	    document.form1.p_process.value = "SearchMemberOpenPage";
	    document.form1.submit();
	}

    function selectMember(userid,name) {
      opener.document.form1.p_userid.value    = userid;
      opener.document.form1.p_name.value      = name;
      self.close();
    }

    function go(index) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.polity.TaxbillAdminServlet";
         document.form1.p_process.value = "SearchMemberOpenPage";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.polity.TaxbillAdminServlet";
         document.form1.p_process.value = "SearchMemberOpenPage";
         document.form1.submit();
    }

//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" onsubmit="javascript:search();">
    <input type = "hidden" name = "p_process" value = "<%=v_process%>">
    <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_userid"  value = "">

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
          <br/>
	      <table width="97%" cellspacing="0" cellpadding="1" class="form_table_out">
	        <tr>
	          <td bgcolor="#C6C6C6" align="center">
				<table cellspacing="2" cellpadding="2" class="form_table_bg" >
				  <tr>
				    <td align="center">
				      <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
				        <tr>
				          <td colspan="2" align="left" valign="middle">
        				    <select name="p_mode1">
        						<option value="name" <%if (v_mode1.equals("name")) out.print("selected"); %>>성명</option>
        						<option value="userid" <%if (v_mode1.equals("userid")) out.print("selected"); %>>ID</option>
        					</select>
                            <input name="p_mode2" type="text" class="input" value="<%=v_mode2%>"  onkeypress="javascript:fnKeyPressEnter(event, 'search');">
                            <a href="javascript:search()"><img src="/images/admin/system/search1_butt.gif" border="0" align="absmiddle"></a>
                          </td>
				        </tr>				
				      </table>
				    </td>
				  </tr>
				</table>
			  </td>
			</tr>
	      </table>
          <br>
            <table width="515" class="table_out" cellspacing="1" cellpadding="0">
                <tr>
                  <td colspan="7" class="table_top_line"></td>
                </tr>
                <tr>
                  <td class="table_title"><b>선택</b></td>
                  <td class="table_title"><b>번호</b></td>
                  <td class="table_title"><b>회원구분</b></td>
                  <td class="table_title"><b>ID</b></td>
                  <td class="table_title"><b>성명</b></td>
                  <td class="table_title"><b>소속</b></td>
                  <td class="table_title"><b>전화번호</b></td>

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
                v_totalpage   = dbox.getInt("d_totalpage");
                v_dispnum     = dbox.getInt("d_dispnum");

                v_membergubun_view = GetCodenm.get_codenm("0029", v_membergubun);

%>
                <tr>
                  <td class="table_01"> <input type="radio" name="no" value="<%=v_userid%>" onClick="javascipt:selectMember('<%=v_userid%>','<%=v_name%>')">
                  </td>
                  <td class="table_02_1"><%=v_dispnum%></td>
                  <td class="table_02_1"><%=v_membergubun_view%></td>
                  <td class="table_02_1"><%=v_userid%></td>
                  <td class="table_02_1"><%=v_name%></td>
                  <td class="table_02_1"><%=v_compnm%></td>
                  <td class="table_02_1"><%=v_handphone%></td>
                </tr>
                <%
        }
        if(i == 0){
%>
                <tr>
                  <td class="table_02_1" colspan="7">검색된 내용이 없습니다.</td>
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
                <td align="right" style="padding-top=10"><a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif" border=0></a></td>
              </tr>
              <tr>
                <td height="5"></td>
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
