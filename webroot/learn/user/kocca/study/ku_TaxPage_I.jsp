<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: ku_TaxPage_I.jsp
//  3. 개      요: 세금계산서 신청
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2006. 02.03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<html>
<head>
<title>세금계산서 신청</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<SCRIPT src="/script/cresys_lib.js" type=text/javascript></SCRIPT>
<style type="text/css">
<!--
@import url("/css/user_style_k1.css");
-->
</style>
</head>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	
    String v_subj		= box.getString("p_subj");
    String v_subjnm		= box.getString("p_subjnm");
	String v_subjseq	= box.getString("p_subjseq");
	String v_year		= box.getString("p_year");
	
	String v_userid		= box.getSession("userid");
	String v_username	= box.getSession("name");
	String v_grcode		= box.getSession("tem_grcode");

	String v_resno = "";
	String v_post1 = "";
	String v_post2 = "";
	String v_addr1 = "";
	String v_addr2 = "";
	String v_membergubun = "";
	String v_membergubun_value = "";
	String v_pay = "";

	DataBox dbox = (DataBox)request.getAttribute("select");

	if(dbox != null)
	{
		v_resno		= dbox.getString("d_resno");
		v_post1		= dbox.getString("d_post1");
		v_post2		= dbox.getString("d_post2");
		v_addr1		= dbox.getString("d_addr");
		v_addr2		= dbox.getString("d_addr2");
		v_membergubun	= dbox.getString("d_membergubun");
		v_pay		= dbox.getString("d_paymoney");
	}

	if(v_membergubun.equals("C")) v_membergubun_value = "기업";
	else if(v_membergubun.equals("U")) v_membergubun_value = "대학";
	else v_membergubun_value = "개인";
%>
<script>
// 우편번호검색
function searchPost() {
	window.self.name = "PersonalSelect";
	open_window("openPost","","100","100","417","400","","","","yes","");
	document.form1.target = "openPost";
	document.form1.action='/servlet/controller.library.PostSearchServlet';
	document.form1.p_process.value = 'SearchPostOpenPage';
	document.form1.submit();
	document.form1.target = window.self.name;
}
// 주소 받아오기
function receivePost(post1, post2, addr, postgubun){
	document.form1.p_post1.value = post1;
	document.form1.p_post2.value = post2;
	document.form1.p_addr1.value = addr;
	document.form1.p_addr2.focus();
	
}

// 신청
function inputTax()
{
	var frm = document.form1;

	frm.action = "/servlet/controller.polity.TaxAdminServlet";
	frm.p_process.value = "Insert";

	frm.submit();
}

</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="form1">
	<input type="hidden" name="p_subj" value="<%=v_subj%>">
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
	<input type="hidden" name="p_year" value="<%=v_year%>">
	<input type="hidden" name="p_resno" value="<%=v_resno%>">
	<input type="hidden" name="p_membergubun" value="<%=v_membergubun%>">
	<input type="hidden" name="p_pay" value="<%=v_pay%>">
	<input type="hidden" name="p_process" >

<table width="673" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/user/kocca/apply/paywindow_top.gif" width="673" height="12"></td>
  </tr>
  <tr> 
    <td background="/images/user/kocca/apply/paywindow_bg.gif">
	<table width="667" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="6">&nbsp;</td>
          <td width="667" align="right"><img src="/images/user/kocca/apply/pop_tit_bill.gif" width="661" height="42"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td background="/images/user/kocca/apply/paywindow_bg.gif">&nbsp;</td>
  </tr>
  <tr> 
    <td align="center" background="/images/user/kocca/apply/paywindow_bg.gif"> 
      <!-- 신청 -->
      <table width="600" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides"   >
        <tr> 
          <td height="5" class="linecolor_app"></td>
          <td height="5" bgcolor="cfcfcf" ></td>
        </tr>
        <tr> 
          <td width="101" align="center" bgcolor="FFF3EC" class="tbl_gtit2">이름</td>
          <td class="tbl_gleft"><%=v_username%></td>
        </tr>
        <tr> 
          <td height="26" align="center" bgcolor="FFF3EC" class="tbl_gtit2">주민번호</td>
          <td class="tbl_gleft"><%=v_resno.substring(0,6)%> -  <%=v_resno.substring(6,13)%> </td>
        </tr>
        <tr> 
          <td align="center" bgcolor="FFF3EC" class="tbl_gtit2">과정명</td>
          <td class="tbl_gleft"><%=v_subjnm%>  </td>
        </tr>
        <tr> 
          <td align="center" bgcolor="FFF3EC" class="tbl_gtit2">차수</td>
          <td class="tbl_gleft"><%=v_subjseq%> </td>
        </tr>
        <tr> 
          <td align="center" bgcolor="FFF3EC" class="tbl_gtit2">연도</td>
          <td class="tbl_gleft"><%=v_year%> </td>
        </tr>
        <tr> 
          <td align="center" bgcolor="FFF3EC" class="tbl_gtit2">회원구분</td>
          <td class="tbl_gleft"><%=v_membergubun_value%> </td>
        </tr>
		 <tr> 
          <td align="center" bgcolor="FFF3EC" class="tbl_gtit2">신청구분</td>
          <td class="tbl_gleft">
			<select name="p_gubun">
				<option value="C">기업</option>
				<option value="P">개인</option>
			</select>
		  </td>
        </tr>
        <tr> 
          <td align="center" bgcolor="FFF3EC" class="tbl_gtit2">금액</td>
          <td class="tbl_gleft"><%=v_pay%> </td>
        </tr>
        <tr> 
          <td rowspan="2" align="center" bgcolor="FFF3EC" class="tbl_gtit2">배송지 주소</td>
          <td class="tbl_gleft"><input name="p_post1" type="text" class="input3" style="width:75" value="<%=v_post1%>">
            - 
            <input name="p_post2" type=text class="input3" style="width:75" value="<%=v_post2%>">
            <a href="javascript:searchPost()"><img src="/images/user/kocca/button/b_search_num.gif" align="absmiddle"width="87" height="18" border="0"> </a>
          </td>
        </tr>
        <tr> 
          <td class="tbl_gleft"><input name="p_addr1" type=text class="input3" style="width:200" value="<%=v_addr1%>"> 
            <input name="p_addr2" type=text class="input3" style="width:200" value="<%=v_addr2%>"></td>
        </tr>
      </table>
      <table width="673" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="10" colspan="3"></td>
        </tr>
        <tr> 
          <td width="26">&nbsp;</td>
          <td width="623"><div align="right"><a href="javascript:inputTax();"><img src="/images/user/kocca/button/btn_ok.gif" width="42" height="19"></a>&nbsp;&nbsp;<a href="javascript:self.close();"><img src="/images/user/kocca/button/btn_cancel.gif" width="42" height="19"></a></div></td>
          <td width="24">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="bottom" background="/images/user/kocca/apply/paywindow_bg.gif"><img src="/images/user/kocca/apply/paywindow_bottom.gif" width="673" height="19"></td>
  </tr>
</table>
</form>
</body>
</html>
