<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: gu_SubjectPreviewOn.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
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
<title>결제처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
</head>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	
	int v_realpay			= box.getInt("p_realpay");
	int v_biyong			= box.getInt("p_biyong");
    String v_rprocess		= box.getString("p_rprocess");
    String v_subj			= box.getString("p_subj");
    String v_subjnm			= box.getString("p_subjnm");
	
    String v_iscourseYn		= box.getString("p_iscourseYn");
    String v_course			= box.getString("p_course");
    String v_coursenm		= box.getString("p_coursenm");

    String v_isonoff		= box.getString("p_isonoff");
    String v_upperclassnm	= box.getString("p_upperclassnm");
    String v_upperclass		= box.getString("p_upperclass");
    String v_select			= box.getString("p_select");
    String v_actionurl		= box.getString("p_actionurl");
    String v_lsearchsubject = box.getString("p_lsearchsubject");
    String v_gubun			= box.getString("p_gubun");
    String v_subjseq		= box.getString("p_subjseq");
	String v_year			= box.getString("p_year");

	String v_username		= box.getSession("name");
	String v_userid			= box.getSession("userid");
	String v_bookyn			= box.getString("rbo_1");
	String v_bookprice		= box.getString("p_bookprice");
	String v_usemileage		= box.getString("p_usemileage");
	
	String v_classname		= "";

	if(v_iscourseYn.equals("Y"))
	{
		v_classname = v_coursenm;
	}else{
		v_classname = v_subjnm;
	}

    int v_discount = 0;

	v_discount = DiscountBean.returnDiscount(box);

	if(v_discount == 0)
	{
		v_realpay = v_biyong ;
	}
	else
	{
		v_realpay = v_biyong - (v_biyong* v_discount / 100);
	}
%>
<script>
	//숫자만 입력 가능
	function numcheck(oTarget)
	{
		if(((event.keyCode < 48) || (event.keyCode > 57)) && ((event.keyCode < 96) || (event.keyCode > 105)))
			if((event.keyCode == 9) || (event.keyCode == 8) || (event.keyCode == 46))
				event.returnValue = true;
			else
				event.returnValue = false;
	}

	//입력
	function input()
	{
		var frm = document.form1;

		if(frm.p_inputname.value.length < 1)
		{
			alert("입금자를 입력해 주세요.");
			frm.p_inputname.focus();
			frm.p_inputname.select();
			return;
		}
		if(frm.p_inputdate.value.length < 1 )
		{
			alert("입금일을 입력해 주세요.");
			frm.p_inputdate.focus();
			frm.p_inputdate.select();
			return;
		}

		frm.action = "/servlet/controller.propose.ProposeCourseServlet";
		frm.p_process.value = "BillCheck";
		frm.target = "_self";

		frm.submit();
	}
</script>
<body bgcolor="#F6F6F6">
<form name="form1">
    <input type='hidden' name='p_process'	>
    <input type='hidden' name='p_select'	value="<%=v_select%>">
    <input type='hidden' name='p_gubun'		value="<%=v_gubun%>">
    <input type='hidden' name='p_biyong'	value="<%=v_biyong%>">
    <input type='hidden' name='p_bookprice' value="<%=v_bookprice%>">
    <input type='hidden' name='p_discount'	value="<%=v_discount%>">
    <input type='hidden' name='p_realpay'	value="<%=v_realpay%>">
    <input type='hidden' name='p_subj'		value="<%=v_subj%>">
    <input type='hidden' name='p_year'		value="<%=v_year%>">
    <input type='hidden' name='userid'		value="<%=v_userid%>">
    <input type='hidden' name='p_subjseq'	value="<%=v_subjseq%>">
    <input type='hidden' name='p_subjnm'	value="<%=v_subjnm%>">
    <input type='hidden' name='p_rprocess'	value="<%=v_rprocess%>">
    <input type='hidden' name='p_actionurl' value="<%=v_actionurl%>">
    <input type='hidden' name='p_isonoff'	value="<%=v_isonoff%>">
    <input type='hidden' name='p_upperclass'	value="<%=v_upperclass%>">
    <input type='hidden' name='p_upperclassnm'	value="<%=v_upperclassnm%>">
    <input type='hidden' name='p_lsearchsubject' value="<%=v_lsearchsubject%>">
    <input type='hidden' name='p_returnurl' value="/servlet/controller.propose.ProposeCourseServlet">
    <input type='hidden' name='p_bookyn'	value="<%=v_bookyn%>">
    <input type='hidden' name='p_usemileage'	value="<%=v_usemileage%>">

    <input type='hidden' name='p_course' value="<%=v_course %>">
    <input type='hidden' name='p_coursenm' value="<%=v_coursenm %>">
    <input type='hidden' name='p_iscourseYn' value="<%=v_iscourseYn%>">
	
<table width="700" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/user/game/apply/pop_tit_offlinemoney.gif" ></td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
            <!-- 수강안내 -->
            <table width="620" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor"> 
                <td height="3" class="linecolor_app"></td>
                <td width="239" height="3" class="linecolor_app2"></td>
                <td width="90" height="3" class="linecolor_app"></td>
                <td width="152" height="3" class="linecolor_app2"></td>
              </tr>
              <tr> 
                <td width="103" class="tbl_gtit2">과정명</td>
                <td colspan="3" class="tbl_gleft"><%=v_classname%></td>
              </tr>
			  <tr> 
                <td class="tbl_gtit2">교육비</td>
                <td colspan="3" class="tbl_gleft"><strong><%=v_realpay%></strong></td>
              </tr>
              <tr> 
                <td class="tbl_gtit2">무통자 입금계좌</td>
                <td colspan="3" class="tbl_gleft"><strong>하나은행</strong> 001-02-121313-12-111 
                  <strong>예금주 :</strong> 사이버게임아카데미</td>
              </tr> 
              <tr> 
                <td class="tbl_gtit2">입금자</td>
                <td colspan="3" class="tbl_gleft">
					<input type="text" class="input" name="p_inputname" value="<%= v_username%>">
				</td>
              </tr> 
              <tr> 
                <td class="tbl_gtit2">입금 예정일</td>
                <td colspan="3" class="tbl_gleft">
					<input type="text" class="input" name="p_inputdate" maxlength="8" onkeydown="javascript:return numcheck(this)"> "-"없이 입력해주세요 (ex.20060130)
				</td>
              </tr>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:input()"><img src="/images/user/game/button/btn_confirm.gif" width="48" height="21" border="0"></a> <a href="javascript:self.close();"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
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
</form>
</body>
</html>
