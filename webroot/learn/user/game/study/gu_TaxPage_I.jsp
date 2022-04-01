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

<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
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
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="F6F6F6">
<form name="form1">
	<input type="hidden" name="p_subj" value="<%=v_subj%>">
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
	<input type="hidden" name="p_year" value="<%=v_year%>">
	<input type="hidden" name="p_resno" value="<%=v_resno%>">
	<input type="hidden" name="p_membergubun" value="<%=v_membergubun%>">
	<input type="hidden" name="p_pay" value="<%=v_pay%>">
	<input type="hidden" name="p_process" >
<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/game/apply/pop_tit_bill.gif"></td>
  </tr>
  <tr>
    <td><table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
            <!-- 신청 -->
            <table width="600" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides"   >
              <tr  class="lcolor"> 
                <td height="3" class="linecolor_app"></td>
                <td width="57" height="3" class="linecolor_app2"></td>
                <td width="54" height="3" class="linecolor_app2"></td>
                <td width="102" height="3" class="linecolor_app2"></td>
              </tr>
              <tr> 
                <td width="101" class="tbl_gtit2">이름</td>
                <td colspan="3" class="tbl_gleft"><%=v_username%></td>
              </tr>
              <tr> 
                <td height="26" class="tbl_gtit2">주민번호</td>
                <td colspan="3" class="tbl_gleft"><%=v_resno.substring(0,6)%> -  <%=v_resno.substring(6,13)%> </td>
              </tr>
              <tr> 
                <td class="tbl_gtit2">과정명</td>
                <td colspan="3" class="tbl_gleft"><%=v_subjnm%></td>
              </tr>
              <tr> 
                <td class="tbl_gtit2">차수</td>
                <td colspan="3" class="tbl_gleft"><%=v_subjseq%></td>
              </tr>
              <tr> 
                <td class="tbl_gtit2">연도</td>
                <td colspan="3" class="tbl_gleft"><%=v_year%></td>
              </tr>
              <tr> 
                <td class="tbl_gtit2">회원구분</td>
                <td colspan="3" class="tbl_gleft"><%=v_membergubun_value%></td>
              </tr>
			  <tr> 
				  <td class="tbl_gtit2">신청구분</td>
				  <td class="tbl_gleft" colspan="3">
					<select name="p_gubun">
						<option value="C">기업</option>
						<option value="P">개인</option>
					</select>
				  </td>
				</tr>
              <tr> 
                <td class="tbl_gtit2">금액</td>
                <td colspan="3" class="tbl_gleft"><%=v_pay%> </td>
              </tr>
              <tr> 
				  <td rowspan="2" class="tbl_gtit2">배송지 주소</td>
				  <td class="tbl_gleft" colspan="3"><input name="p_post1" type="text" class="input3" style="width:75" value="<%=v_post1%>">
					- 
					<input name="p_post2" type=text class="input3" style="width:75" value="<%=v_post2%>">
					<a href="javascript:searchPost()"><img src="/images/user/game/button/btn_zipcode.gif" align="absmiddle"width="87" height="18" border="0"> </a>
				  </td>
				</tr>
				<tr> 
				  <td class="tbl_gleft" colspan="3"><input name="p_addr1" type=text class="input3" style="width:200" value="<%=v_addr1%>"> 
					<input name="p_addr2" type=text class="input3" style="width:200" value="<%=v_addr2%>"></td>
				</tr>
            </table>
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="10" colspan="2" align="right"></td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:inputTax();"><img src="/images/user/game/button/btn_confirm.gif" width="48" height="21" border="0"></a> 
                  <a href="javascript:self.close();"><img src="/images/user/game/button/btn_cancel.gif" width="48" height="21" border="0"> 
                  </a></td>
              </tr>
            </table></td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
