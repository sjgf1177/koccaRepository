<%
//**********************************************************
//  1. 제	  목: zu_Subject_Bill_Result.jsp
//  2. 프로그램명: zu_Subject_Bill_Result.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%@page import="com.credu.Bill.BillBean"%><jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);
	String p_action = box.get("p_action");
	String type = box.get("type");
	String resultCode = box.get("resultCode");
	int totalPrice = Integer.parseInt(box.get("totalPrice"));
	DataBox payMethodType = BillBean.getPayMethod();
%>
<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script>
	var openwin=window.open("/inicis/childwin.html","childwin","width=300,height=160");
	openwin.close();

	/*------------------------------------------------------------------------------------------------------*
         * 1. $inipay->m_resultCode 										*
         *       가. 결 과 코 드: "00" 인 경우 결제 성공[무통장입금인 경우 - 고객님의 무통장입금 요청이 완료]	*
         *       나. 결 과 코 드: "00"외의 값인 경우 결제 실패  						*
         *------------------------------------------------------------------------------------------------------*/

	function show_receipt() // 영수증 출력
	{
		if("<%=resultCode%>" == "00")
		{
			var receiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=" + "<%=box.get("tid")%>" + "&noMethod=1";
			window.open(receiptUrl,"receipt","width=430,height=700");
		}
		else
		{
			alert("해당하는 결제내역이 없습니다");
		}
	}

	function errhelp() // 상세 에러내역 출력
	{
		var errhelpUrl = "http://www.inicis.com/ErrCode/Error.jsp?result_err_code=" + "<%=box.get("resulterrCode")%>"
						 + "&mid=" + "<%=box.get("mid")%>"
						 + "&tid=<%=box.get("tid")%>" 
						 + "&goodname=" + "<%=box.get("goodname")%>" 
						 + "&price=" + "<%=box.get("price")%>" 
						 + "&paymethod=" + "<%=box.get("payMethod")%>" 
						 + "&buyername=" + "<%=box.get("buyername")%>" 
						 + "&buyertel=" + "<%=box.get("buyertel")%>" 
						 + "&buyeremail=" + "<%=box.get("buyeremail")%>" 
						 + "&codegw=" + "<%=box.get("codegw")%>";
		window.open(errhelpUrl,"errhelp","width=520,height=150, scrollbars=yes,resizable=yes");
	}
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->

  	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	<table>   
      <tr>
      	<td>
      	
      	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/myclass/stitle_06.gif" alt="장바구니" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>장바구니</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <!--타이틀부분//-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/portal/homepage_renewal/myclass/toff_01.jpg" name="Image49" id="Image49" /></td>
    <td><img src="/images/portal/homepage_renewal/myclass/toff_02.jpg" name="Image50"  id="Image50" /></td>
    <td class="h_road" ><img src="/images/portal/homepage_renewal/myclass/ton_03.jpg" name="Image51" id="Image51" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

    <table border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="100" class="org"><div align="center"><img src="/images/portal/homepage_renewal/myclass/img_m_ok.jpg" width="388" height="70" /></div></td>
        </tr>
      <tr>
        <td>* 수강신청하신 과목은 <strong>나의 강의실 &gt; 수강신청확인/취소</strong>에서 확인하실 수 있습니다.</td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/myclass/sstitle_05.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="2" bgcolor="#7692CB"></td>
      </tr>
      <% if( totalPrice > 0) { %>
      <tr>
        <td class="tit_table">결제내용</td>
        <td class="ct_tit_table_left"><a href="javascript:show_receipt()"><%=box.get("goodname")%></a></td>
      </tr>
      <tr>
        <td class="tit_table">결제방법</td>
        <td class="ct_tit_table_left"><a href="javascript:show_receipt()"><%= payMethodType.get(box.get("payMethod")) %></a></td>
      </tr>
      <tr>
        <td class="tit_table">신용카드번호</td>
        <td class="ct_tit_table_left"><a href="javascript:show_receipt()"><%= box.get("cardNumber") %></a></td>
      </tr>
      <tr>
        <td class="tit_table">승인날짜</td>
        <td class="ct_tit_table_left"><%= box.getDate("pgAuthDate") %></td>
      </tr>
      <tr>
        <td class="tit_table">승인시각</td>
        <td class="ct_tit_table_left"><%= box.get("pgAuthTime") %></td>
      </tr>
      <tr>
        <td class="tit_table">승인번호</td>
        <td class="ct_tit_table_left"><a href="javascript:show_receipt()"><%= box.get("authCode") %></a></td>
      </tr>
      
      <tr>
        <td height="1" colspan="2" bgcolor="7692CB"></td>
      </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="2" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="org">&nbsp;</td>
      </tr>
      <tr>
        <td height="25" class="blue_txt"> 신용카드 결제안내</td>
      </tr>
      <tr>
        <td>* 신용카드 청구서에 &quot;이니시스(inicis.com)&quot;으로 표기됩니다.</td>
      </tr>
      <tr>
        <td>* LG카드 및 BC카드의 경우 &quot;이니시스(이용 상점명)&quot;으로 표기되고, 삼성카드의 경우 &quot;이니시스(이용상점 URL)&quot;로 <br />
          　표기됩니다</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="25" class="blue_txt">계좌이체 결제안내</td>
      </tr>
      <tr>
        <td>* 고객님의 통장에는 &quot;이니시스&quot;로 표기됩니다.</td>
      </tr>
      <%}else{ %>
      <tr>
        <td class="tit_table">결제내용</td>
        <td class="ct_tit_table_left"><%=box.get("goodname")%></td>
      </tr>
      <tr>
        <td class="tit_table">결제방법</td>
        <td class="ct_tit_table_left">무료</td>
      </tr>
      <%} %>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><div align="right"><% if( totalPrice > 0) { %><a href="javascript:show_receipt()"><img src="/images/portal/homepage_renewal/myclass/btn_print2.jpg" alt="영수증 출력"  /></a><%} %><a href="javascript:menuForward('3', '17');"><img src="/images/portal/homepage_renewal/myclass/btn_list_ok_cc.jpg" alt="수강신청 확인/취소"  /></a><a href="javascript:menuMainForward('3','/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');"><img src="/images/portal/homepage_renewal/myclass/btn_home.jpg" alt="홈으로"  /></a></div></td>
      </tr>
    </table></td>
    <td width="20">&nbsp;</td>

  </tr>
</table>
      	
      	
      	<%}else{ %>
<h2><img src="/images/portal/myclass/h2_tit3.gif" alt="장바구니" class="fl_l" /><p class="category">Home &gt; 나의강의실 &gt; <strong>장바구니</strong></p></h2>

<ul class="basket_tab">
	<li><img src="/images/portal/myclass/basket_tab1.gif" alt="1.신청목록 확인" /></li>
	<li><img src="/images/portal/myclass/basket_tab2_2.gif" alt="2.결제정보 확인" /></li>
	<li><img src="/images/portal/myclass/basket_tab3_on.gif" alt="3.결제완료" /></li>
</ul>

<div class="message_wrap mg_t15">
	<img src="/images/portal/myclass/basket_message.gif" alt="수강 신청하신 과목의 결제가 완료되었습니다." />
	<p class="message_comment"><img src="/images/portal/ico/ico_comment_arrow.gif" alt="" />수강신청하신 과목은 <strong>나의 강의실 &gt; 수강신청확인/취소</strong>에서 확인하실 수 있습니다.</p>
</div>
			<h3><img src="/images/portal/myclass/stit_basket7.gif" alt="결제정보" /></h3>
			<table class="view_myclass">
			<colgroup><col width="18%" /><col width="82%" /></colgroup>
		<% if( totalPrice > 0) { %>
			<tr>
				<th>결제내용</th>
				<td class="txt end"><a href="javascript:show_receipt()"><%=box.get("goodname")%></a></td>
			</tr>
			<tr>
				<th>결제방법</th>
				<td class="txt end"><a href="javascript:show_receipt()"><%= payMethodType.get(box.get("payMethod")) %></a></td>
			</tr>
			<tr>
				<th>신용카드번호</th>
				<td class="txt end"><a href="javascript:show_receipt()"><%= box.get("cardNumber") %></a></td>
			</tr>
			<tr>
				<th>승인날짜</th>
				<td class="txt end"><%= box.getDate("pgAuthDate") %></td>
			</tr>
			<tr>
				<th>승인시각</th>
				<td class="txt end"><%= box.get("pgAuthTime") %></td>
			</tr>
			<tr>
				<th>승인번호</th>
				<td class="txt end"><a href="javascript:show_receipt()"><%= box.get("authCode") %></a></td>
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
			<p class="board_btn ag_c mg_t40"><a href="javascript:show_receipt()" class="btn_gr"><span>영수증 출력</span></a><a href="javascript:menuForward('3', '17');" class="btn_gr"><span>수강신청 확인/취소</span></a><a href="javascript:menuMainForward('3','/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');" class="btn_gr"><span> 홈으로</span></a></p>
		<% } else { %>
			<tr>
				<th>결제내용</th>
				<td class="txt end"><%=box.get("goodname")%></td>
			</tr>
			<tr>
				<th>결제방법</th>
				<td class="txt end">무료</td>
			</tr>
			</table>
			<p class="board_btn ag_c mg_t40"><a href="javascript:menuForward('3', '17');" class="btn_gr"><span>수강신청 확인/취소</span></a><a href="javascript:menuMainForward('3','/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');" class="btn_gr"><span> 홈으로</span></a></p>
		<% } %>
		<%} %>
<!-- form 끝 -->
<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
