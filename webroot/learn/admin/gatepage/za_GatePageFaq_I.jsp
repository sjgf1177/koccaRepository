<%
//**********************************************************
//  1. 제      목: GatePage FAQ 등록
//  2. 프로그램명 : za_GatePageFaq_I.jsp
//  3. 개      요: GatePage FAQ 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2005. 1. 1
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.gatepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	DataBox dbox = (DataBox)request.getAttribute("GatePageFaqSetting");
	String s_gubun = "";
    String v_faqcategory  = box.getString("p_faqcategory");
	String v_faqcategorynm  = box.getString("p_faqcategorynm");
	int v_fnum  = dbox.getInt("d_fnum");
	s_gubun = box.getString("p_gubun");
	System.out.println("여기는 faq등록화면 이지요s_gubun &*******  "  + s_gubun);
%>

<html>
<head>
<title>GateSystemFAQ</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script src="/script/cafe_select.js" language="javascript"></script>
<SCRIPT language="javascript">

    function insert2Action() {
        if (document.form1.p_title.value == "") {
            alert("FAQ 제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }

        if (document.form1.p_contents.value == "") {
            alert("FAQ 답변을 입력하세요");
            document.form1.p_contents.focus();
            return;
        }

        document.form1.action = "/servlet/controller.gatepage.GatePageFaqCategoryAdminServlet";
        document.form1.p_process.value = "insert2";
        document.form1.submit();
    }

</SCRIPT>

</head>

<body topmargin=0 leftmargin=0>
    <form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "">
	<input type = "hidden" name = "p_gubun" value = "<%=s_gubun%>">
	<input type = "hidden" name = "p_faqcategory" value = "<%=v_faqcategory%>">
	<input type = "hidden" name = "p_fnum" value = "<%=v_fnum%>">
	

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/gatepage/cp_title07.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   <br>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td class="table_title" width="18%">FAQ 카테고리코드</td>
          <td class="table_02_2"  width="82%"><%=v_faqcategory%></td>
        </tr>
        <tr>
          <td class="table_title">FAQ 카테고리명</td>
          <td class="table_02_2"><%=GatePageFaqCategoryAdminBean.getFaqCategoryName(v_faqcategory)%></td>
        </tr>
        <tr>
          <td class="table_title">FAQ 번호</td>
          <td class="table_02_2"><%=v_fnum%></td>
        </tr>
        <tr>
          <td class="table_title">FAQ 제목</td>
          <td class="table_02_2"><input name="p_title" type="text" class="input" style="width:600"></td>
        </tr>
        <tr>
          <td class="table_title">FAQ 답변</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5>
		  
		  <TEXTAREA NAME="p_contents" ROWS="20" COLS="55"></textarea>
		  </td>
        </tr>
      </table>
      
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height=6></td>
          <td height=6></td>
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="14" height=6></td>
          <td align="right" height=6></td>
        </tr>
        <tr> 
          <td width="722" align="right" ></td>
          <td width="150"></td>
          <td width="10">&nbsp;</td>
          <td width="32" align="right">
			  <a href="javascript:insert2Action();" onfocus=this.blur()>
			  <img src="/images/admin/button/btn_save.gif" border = "0"></td>
          <td width="14">&nbsp;</td>
          <td width="32" align="right">
			  <a href="javascript:history.back();" onfocus=this.blur()>
			  <img src="/images/admin/button/btn_cancel.gif" border = "0"></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
