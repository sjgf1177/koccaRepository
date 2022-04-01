<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 오프라인 결제 결과
//  2. 프로그램명: gu_OffPayCheck_P.jsp
//  3. 개      요: 오프라인 결제 결과
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.01.19
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

    String v_tid         = "";
    String v_cardnumber  = "";
    String v_pgauthdate  = "";
    String v_pgauthtime  = "";
    String v_paymethod   = "";
    String v_authcode    = "";
    
    ArrayList list = (ArrayList)request.getAttribute("offsecurepayInfo");

    if (list.size() > 0) {
        DataBox dbox   = (DataBox)list.get(0);
        v_tid          = dbox.getString("d_tid");
        v_pgauthdate   = dbox.getString("d_pgauthdate");
        v_pgauthdate   = FormatDate.getFormatDate(v_pgauthdate,"yyyy.MM.dd");
        v_pgauthtime   = dbox.getString("d_pgauthtime");
        v_pgauthtime   = FormatDate.getFormatTime(v_pgauthtime,"HH:mm");
        v_cardnumber   = dbox.getString("d_cardnumber");
        v_paymethod    = dbox.getString("d_paymethod");
        v_authcode     = dbox.getString("d_authcode");
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>결제완료</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
</head>
<body id="popup"><!-- popup size : 752* -->
	<div id="pop_header">
		<h1><img src="/images/portal/popup/myclass_h1_tit2.gif" alt="결제완료" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<ul class="basket_tab">
				<li><img src="/images/portal/myclass/basket_off_tab1_off.gif" alt="결제정보확인" /></li>
				<li><img src="/images/portal/myclass/basket_off_tab2_on.gif" alt="결제완료" /></li>
			</ul>
			
			<div class="message_wrap bo_bn pd_t45 pd_bn">
				<img src="/images/portal/myclass/basket_message2.gif" alt="고객님의 결제요청이 성공되었습니다." />
				<p class="message_comment"><img src="/images/portal/ico/ico_comment_arrow.gif" alt="" />수강신청하신 과목은 <strong>나의 강의실 > 수강신청확인/취소</strong>에서 확인하실 수 있습니다.</p>
			</div>
			
			<h3><img src="/images/portal/myclass/stit_basket7.gif" alt="결제정보" /></h3>
			<table class="view_myclass">
			<colgroup><col width="18%" /><col width="82%" /></colgroup>
			<tr>
				<th>결제방법</th>
				<td class="txt end">신용카드  </td>
			</tr>
			<tr>
				<th>신용카드번호</th>
				<td class="txt end"><%= v_cardnumber %></td>
			</tr>
			<tr>
				<th>승인날짜</th>
				<td class="txt end"><%= v_pgauthdate %></td>
			</tr>
			<tr>
				<th>승인시각</th>
				<td class="txt end"><%= v_pgauthtime %></td>
			</tr>
			<tr>
				<th>승인번호</th>
				<td class="txt end"><%= v_authcode %></td>
			</tr>
			</table>
 
			<div class="message_comment_list">
				<p class="tit">신용카드 결제안내</p>
				<p class="message_comment"><img src="/images/portal/ico/ico_comment_arrow.gif" alt="" />신용카드 청구서에 "이니시스(inicis.com)"으로 표기됩니다.</p>
				<p class="message_comment"><img src="/images/portal/ico/ico_comment_arrow.gif" alt="" />LG카드 및 BC카드의 경우 "이니시스(이용 상점명)"으로 표기되고, 삼성카드의 경우 "이니시스(이용상점 URL)"로 표기됩니다.</p>
			</div>
 
			<div class="message_comment_list">
				<p class="tit">계좌이체 결제안내</p>
				<p class="message_comment"><img src="/images/portal/ico/ico_comment_arrow.gif" alt="" />고객님의 통장에는 "이니시스"로 표기됩니다.</p>
			</div>
		</div>
	</div>
	<div id="pop_footer">
		<p class="f_btn"><a href="javascript:top.window.close()" class="btn_gr"><span>닫기</span></a></p>
	</div>
<form name="form1" method="post">
	<input type="hidden" name="p_process" value="MyOffClassBillListPage"/>
</form>

<script language = "javascript">
<!--
	top.window.opener.location.reload(top.window.opener.location.href);
	//document.form1.target = top.window.opener.name;
	//document.form1.action = "/servlet/controller.study.MyClassBillServlet"; 
	//document.form1.submit();
//-->
</script>
</body>
</html>