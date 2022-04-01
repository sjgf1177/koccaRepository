<%
//**********************************************************
//  1. 제	  목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%!
	private void append(StringBuffer list, DataBox dbox) {
		/*list.append("<tr onmouseover=\"className='over'\" onmouseout=\"className=' '\">\n");
		list.append("	<td><input type='hidden' name='p_subj' value='" + dbox.get("d_subj") + "'/>" + dbox.get("d_classname") + "</td>\n");
		list.append("	<td class=\"ta_l\"><a href=\"\"><strong>" + dbox.get("d_subjnm") + "</strong></a></td>\n");
		list.append("	<td class=\"num\">" + dbox.getDate("d_edustart") + "~" + dbox.getDate("d_edustart") + "</span></td>\n");
		list.append("	<td class=\"ta_r num\"><strong class=\"myclass\">" + dbox.getCommaInt("d_biyong") + "원</strong></td>\n");
		list.append("</tr>\n");//*/
		list.append("<tr onmouseover=\"className='over'\" onmouseout=\"className=' '\">\n");
		list.append("	<td><input type='hidden' name='p_subj' value='");
		list.append(dbox.get("d_subj"));
		list.append("'/>");
		list.append("<input type='hidden' name='p_subjseq' value='");
		list.append(dbox.get("d_subjseq"));
		list.append("'/>");
		list.append("<input type='hidden' name='p_year' value='");
		list.append(dbox.get("d_year"));
		list.append("'/>");        
		list.append(dbox.get("d_classname"));
		list.append("</td>\n");
		list.append("	<td class=\"ta_l\"><a href=\"\"><strong>");
		list.append(dbox.get("d_subjnm"));
		list.append("</strong></a></td>\n");
		list.append("	<td class=\"num\">");
		list.append(dbox.getDate("d_edustart"));
		list.append("~");
		list.append(dbox.getDate("d_eduend"));
		list.append("</span></td>\n");
		list.append("	<td class=\"ta_r num\"><strong class=\"myclass\">");
		list.append(dbox.getCommaInt("d_biyong"));
		list.append("원</strong></td>\n");
		list.append("</tr>\n");
	}
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	String type = box.get("type");
	String v_goodname = null;
	if (box == null) box = RequestManager.getBox(request);
	DataBox resultbox = (DataBox) request.getAttribute("resultbox");
	List<DataBox> proposeResultList = (List<DataBox>)resultbox.getObject("proposeResultList");		 // 과정 리스트
	StringBuffer listY = new StringBuffer();
	StringBuffer listN = new StringBuffer();
	if (proposeResultList!=null) {
		for(DataBox dbox : proposeResultList) {
			if(v_goodname == null) {
				v_goodname = dbox.get("d_subjnm");
			}
			if (dbox.get("d_chkfirst").equals("Y")) {
				append(listY, dbox);
			}
			else {
				append(listN, dbox);
			}
		}
		if (proposeResultList.size()>1) v_goodname += "외 " + (proposeResultList.size()-1) + "건";
	}
	String[]phone = resultbox.getStringArray("d_handphone", "-");
	%>
<%@ include file="/learn/user/portal/include/site_info.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미</title>
<link href="/css/portal/homepage_renewal/style.css" rel="stylesheet" type="text/css" />
<link href="/css/portal/homepage_renewal/member.css" rel="stylesheet" type="text/css" />
<script language=javascript src="http://plugin.inicis.com/pay40.js"></script>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script language="javascript" src="/script/mainscript.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>  

<script language=javascript>
StartSmartUpdate();
</script>
<!--
※ 주의 ※
 상단 자바스크립트는 지불페이지를 실제 적용하실때 지불페이지 맨위에 위치시켜
 적용하여야 만일에 발생할수 있는 플러그인 오류를 미연에 방지할 수 있습니다.

  <script language=javascript src="http://plugin.inicis.com/pay40.js"></script>
  <script language=javascript>
  StartSmartUpdate();	// 플러그인 설치(확인)
  </script>
-->
<script language="JavaScript">
<!--//
function pay(frm)
{
	// MakePayMessage()를 호출함으로써 플러그인이 화면에 나타나며, Hidden Field
	// 에 값들이 채워지게 됩니다. 일반적인 경우, 플러그인은 결제처리를 직접하는 것이
	// 아니라, 중요한 정보를 암호화 하여 Hidden Field의 값들을 채우고 종료하며,
	// 다음 페이지인 INIsecurepay.php로 데이터가 포스트 되어 결제 처리됨을 유의하시기 바랍니다.
	if(document.ini.clickcontrol.value == "enable")
	{
		if (document.ini.paygubun(0).checked) {
			document.ini.gopaymethod.value = "onlycard";
		}
		if (document.ini.paygubun(1).checked) {
			document.ini.gopaymethod.value = "onlydbank";
		}

		if(document.ini.gopaymethod.value == "") {
			alert("결제 수단을 선택하세요.");
			return false;
		}

		if(document.ini.goodname.value == "")  // 필수항목 체크 (상품명, 상품가격, 구매자명, 구매자 이메일주소, 구매자 전화번호)
		{
			alert("과정정보가 없습니다.");
			return false;
		}

		if(document.ini.price.value == "")
		{
			alert("가격정보가 없습니다.");
			return false;
		}

		if(document.ini.buyername.value == "")
		{
			alert("구매자 정보가 없습니다.");
			return false;
		}

		if(document.ini.buyeremail.value == "")
		{
			alert("EMAIL 주소를 입력하세요.");
			document.ini.buyeremail.focus();
			return false;
		}

		if(document.ini.buyertel1.value == "" || document.ini.buyertel2.value == "" || document.ini.buyertel3.value == "")
		{
			alert("전화번호를 입력하세요.");
			return false;
		}

		document.ini.buyertel.value = document.ini.buyertel1.value + document.ini.buyertel2.value + document.ini.buyertel3.value;
		document.ini.price.value = document.ini.price.value.replace(",","");
		if(document.INIpay == null || document.INIpay.object == null)  // 플러그인 설치유무 체크
		{
			alert("\n이니페이 플러그인 128이 설치되지 않았습니다. \n\n안전한 결제를 위하여 이니페이 플러그인 128의 설치가 필요합니다. \n\n다시 설치하시려면 Ctrl + F5키를 누르시거나 메뉴의 [보기/새로고침]을 선택하여 주십시오.");
			return false;
		}
		else
		{
			/******
			 * 플러그인이 참조하는 각종 결제옵션을 이곳에서 수행할 수 있습니다.
			 * (자바스크립트를 이용한 동적 옵션처리)
			 */

			/*
			50000원 미만은 할부불가, 일시불만 플러그인에서 선택 제한하기 위한 적용
			*/

        		if(parseInt(frm.price.value) < 50000)
        		{
    				/****  ※ 주의 ※  - 무이자 가맹점만 계약된 경우는 nointerest 값을 "yes"로 수정
    				                     그 외에는 일반적으로 "no"으로 세팅
    				****/
    				frm.nointerest.value = "no";
            			frm.quotabase.value = "일시불";
        		}
        		else
        		{
            			/*
				※ 주의 ※ - 위의 5만원미만 조건에 대해 맞지않을 때 무이자 관련 필드(nointerest, quotabase)의
				             기준조건을 그대로 유지할 수 있도록 아래 소스 중에 nointerest, quotabase 값과 동일하게 적용
				*/

				frm.nointerest.value = "no";
				frm.quotabase.value = "선택:일시불:3개월:4개월:5개월:6개월:7개월:8개월:9개월:10개월:11개월:12개월";
				//frm.quotabase.value = "일시불";

			}


			if (MakePayMessage(frm))
			{
				disable_click();
				//openwin = window.open("/inicis/childwin.html","childwin","width=299,height=149");
				return true;
			}
			else
			{
				alert("결제를 취소하셨습니다.");
				return false;
			}
		}
	}
	else
	{
		return false;
	}
}


function enable_click()
{
	document.ini.clickcontrol.value = "enable"
}

function disable_click()
{
	document.ini.clickcontrol.value = "disable"
}

function focus_control()
{
	if(document.ini.clickcontrol.value == "disable")
		openwin.focus();
}

function fnCancel() {
	if(confirm('결제를 취소하시겠습니까?')) {
		menuForward('3','18');
	}
}

//과정 내용보기
function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq){
document.form1.p_subj.value = subj;
document.form1.p_subjnm.value = subjnm;
document.form1.p_iscourseYn.value = courseyn;
document.form1.p_upperclass.value = upperclass;
document.form1.p_upperclassnm.value = upperclassnm;
document.form1.p_year.value = year;
document.form1.p_subjseq.value = subjseq;
document.form1.p_process.value = 'SubjectPreviewPage';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
document.form1.target = "_self";
document.form1.submit();
}
//-->
</script>

</head>
<!--
※ 주의 ※
 아래의 body TAG의 내용중에
 onload="javascript:enable_click()" onFocus="javascript:focus_control()" 이 부분은 수정없이 그대로 사용.
 아래의 form TAG내용도 수정없이 그대로 사용.
-->
<body onload="javascript:enable_click()" onFocus="javascript:focus_control()">
<form name="f1" method="post">
	<input type="hidden" name="p_process"/>
</form>
<!-- accessibility_menu -->
<ul id="accessibility_menu">
    <li><a href="#content">본문으로 넘어가기</a></li>
    <li><a href="#header">대 메뉴로 넘어가기</a></li>
    <li><a href="#banner">텍스트베너로 넘어가기</a></li>
    <li><a href="#footer">카피라이터로 넘어가기</a></li>
</ul>
<!--// accessibility_menu -->
<hr />

<!-- header -->
<script language="javascript">
    
</script>

<%@ include file="/learn/user/portal/include/common.jsp" %>

<div id="header">
    <!-- Top Warp 시작 -->
	<%@ include file="/learn/user/portal/include/topWarp.jsp" %>
	<!-- Top Warp 종료 -->
	
	<!-- Top Menu 시작 -->
	<%@ include file="/learn/user/portal/include/topMenu.jsp" %>
    <!-- Top Menu 종료 -->	

    <!-- Top Visual 시작 -->
    <%@ include file="/learn/user/portal/include/topVisual.jsp" %>
    <!-- Top Visual 종료 -->
</div>

<!--// header -->
<hr />
<!-- container -->
<div id="container">
    <div id="contentWrap">
        <!-- 서브메뉴영역 -->
        <%@ include file="/learn/user/portal/include/subMenu.jsp"%>
        <!-- 서브메뉴영역종료 -->

<!-- content 시작 -->
        <div id="content">

<!-- ******************************** 스크립트영역 ******************************** -->
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<!-- <form name=ini method=post action="/inicis/INIsecurepay.jsp" onsubmit="return pay(this)"> -->
<form name=ini method=post action="/servlet/controller.bill.BillServlet" onsubmit="return pay(this)">

<!-- <form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post"> -->
	<input type="hidden" name="gopaymethod" value=""/>
	<input type="hidden" name=goodname size=20 value="<%=v_goodname%>"/> <!-- 상품명 -->
	<input type="hidden" name="p_dis"	value="<%=box.get("p_dis")%>"/>
	<input type="hidden" name="ispointuse" value="N"/>
	<input type="hidden" name="point" value="0"/>
	<input type="hidden" name="p_gubun" value="N"/><!-- 구분(N:일반,P:패키지,B:도서)  -->
	<input type="hidden" name="price" value="<%= box.getCommaInt("totalPrice") %>"/>
	<input type="hidden" name="tem_grcode" value="<%=box.getSession("tem_grcode")%>"/>

	<input type="hidden" name="type"	value="<%=type%>"/>
	<input type="hidden" name="p_process"	value="securepay"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	<input type="hidden" name="totalPrice" value="<%= box.get("totalPrice") %>"/>
	<input type="hidden" name="usePoint" value="<%= box.get("usePoint") %>"/>
	
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
        <td><img src="/images/portal/homepage_renewal//myclass/stitle_06.gif" alt="장바구니" /></td>
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
    <td><img src="/images/portal/homepage_renewal//myclass/toff_01.jpg" name="Image49" id="Image49" /></td>
    <td><img src="/images/portal/homepage_renewal//myclass/ton_02.jpg" name="Image50"  id="Image50" /></td>
    <td class="h_road" ><img src="/images/portal/homepage_renewal//myclass/toff_03.jpg" name="Image51" id="Image51" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="org">* 결제가 완료되야 수강신청이 완료됩니다.</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="org">* 오프라인 과정은 운영진 1차 승인 후, “수강료결제 조회/납부”에서 결제를 진행하여 주시기 바랍니다.</td>
        <!-- <td class="h_road"><img src="/images/portal/homepage_renewal//myclass/btn_offline_ok.jpg" /></td>  -->
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/myclass/sstitle_02.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
          <td height="1" colspan="5" bgcolor="#7692CB"></td>
        </tr>
      <tr>
        <td class="tit_table">분류</td>
        <td class="tit_table"> 과정명 </td>
        <td class="tit_table">교육기간</td>
        <td class="tit_table_right">수강료</td>
      </tr>
      <%if (proposeResultList!=null) {
  		for(DataBox dbox : proposeResultList) { %>  		
      <tr>      	
        <td class="ct_tit_table"><%=dbox.getString("d_classname") %></td>
        <td class="ct_tit_table_left"><%=dbox.getString("d_subjnm") %></td>
        <td class="ct_tit_table"><%=dbox.getString("d_edustart") %>~<%=dbox.getString("d_eduend") %></td>
        <td class="ct_tit_table_right"><%=dbox.getString("d_biyong") %>원</td>
      </tr>
      <tr>
      	<td><input type="hidden" name="p_subj"	value="<%=dbox.getString("d_subj") %>"/></td>
      	<td><input type="hidden" name="p_subjseq"	value="<%=dbox.getString("d_subjseq") %>"/></td>
      	<td><input type="hidden" name="p_year"	value="<%=dbox.getString("d_year") %>"/></td>
      </tr>
      <%} } %>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" bgcolor="CACACA"></td>
      </tr>
	  <tr>
        <td height="10" bgcolor="ffffff"></td>
      </tr>
    </table>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal//common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal//myclass/sstitle_02_2.gif"/></td>
      </tr>
    </table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
          <td height="1" colspan="2" bgcolor="#7692CB"></td>
        </tr>
      <tr>
        <td class="tit_table">성명</td>
        <td class="ct_tit_table_lright"><input type=hidden name=buyername size=20 value="<%=resultbox.get("d_name")%>"/><%= resultbox.get("d_name") %></td>
      </tr>
      <tr>
        <td class="tit_table">이메일</td>
        <td class="ct_tit_table_lright"><input type=hidden name=parentemail value=""/>
					<input type="text" class="inbox" name="buyeremail" id="buyeremail" style="width:162px;" value="<%= resultbox.get("d_email") %>" /></td>
      </tr>
      <tr>
        <td class="tit_table">휴대전화</td>
        <td class="ct_tit_table_lright"><input type="hidden" name="buyertel" id="buyertel" value="<%= resultbox.get("d_handphone") %>" />
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel1" id="buyertel1" style="width:38px;" maxlength="3" value="<%= phone[0] %>" />-
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel2" id="buyertel2" style="width:38px;" maxlength="4" value="<%= phone[1] %>" />-
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel3" id="buyertel3" style="width:38px;" maxlength="4" value="<%= phone[2] %>" /></td>
      </tr>
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="3" bgcolor="ffffff"></td>
      </tr>

	  <tr>
		<td class="txt">* 오프라인 과정은 운영진 1차 승인 후, “수강료결제 조회/납부”에서 결제를 진행하여 주시기 바랍니다.</td>
	  </tr>
	  <tr>
        <td height="10" bgcolor="ffffff"></td>
      </tr>
    </table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal//common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal//myclass/sstitle_02_3.gif"/></td>
      </tr>
    </table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
          <td height="1" colspan="2" bgcolor="#7692CB"></td>
        </tr>
        <% if( Integer.parseInt(box.getString("totalPrice")) > 0 ) { %>	
      <tr>
        <td class="tit_table">결제방법</td>
        <td class="ct_tit_table_lright"><input name="paygubun" type="radio" value="CARD" value="radiobutton" id="paygubun" checked/>신용카드
					<input name="paygubun" value="DirectBANK" type="radio" value="radiobutton" id="paygubun" class="mg_l35" />실시간 계좌이체
				<!--	<input name="radiobutton" type="radio" value="radiobutton" class="mg_l35" />무통장 --></td>
      </tr>
      <tr>
        <td class="tit_table">결제금액</td>
        <td class="ct_tit_table_lright"><span class="rtxt"><strong><%= box.getCommaInt("totalPrice") %></strong></span></td>
      </tr>
      <%}else{ %>
      <tr>
        <td class="tit_table">결제방법</td>
        <td class="ct_tit_table_lright">무료</td>
      </tr>
      <tr>
        <td class="tit_table">결제금액</td>
        <td class="ct_tit_table_lright"><span class="rtxt"><strong><%= box.getCommaInt("totalPrice") %></strong></span></td>
      </tr>
      <%} %>
    </table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" bgcolor="F7F7F7"></td>
      </tr>
	</table>



    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
      <% if( Integer.parseInt(box.getString("totalPrice")) > 0 ) { %>	
        <td><div align="center"><a href="javascript:if(pay(document.ini)) document.ini.submit();"><img src="/images/portal/homepage_renewal//myclass/btn_pok.jpg" alt="결제하기"  /></a></div></td>
        <%}else{ %>
        <td><div align="center"><a href="javascript:document.ini.submit();"><img src="/images/portal/homepage_renewal//myclass/btn_pok.jpg" alt="결제하기"  /></a></div></td>
        <%} %>
      </tr>
    </table></td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
      	
      	<%}else{ %>
			<h2><img src="/images/portal/myclass/h2_tit3.gif" alt="장바구니" class="fl_l" /><p class="category">Home &gt; 나의강의실 &gt; <strong>장바구니</strong></p></h2>
			<ul class="basket_tab">
				<li><img src="/images/portal/myclass/basket_tab1_2.gif" alt="1.신청목록 확인" /></li>
				<li><img src="/images/portal/myclass/basket_tab2_on.gif" alt="2.결제정보 확인" /></li>
				<li><img src="/images/portal/myclass/basket_tab3.gif" alt="3.결제완료" /></li>
			</ul>

	
			<ul class="basket_info">
				<li>결제버튼을 누르면 결제 정보를 안전하게 암호화하기 위한 플러그인 창이 출력됩니다.</li> 
				<li>플러그인에서 제시하는 단계에 따라 정보를 입력한 후 [결제 정보 확인] 단계에서 확인버튼을 누르면 결제처리가 됩니다. </li>
				<li>통신환경에 따라 다소 시간이 걸릴수도 있으니<br />
				결제결과가 표시될때까지 중지버튼을 누르거나 브라우저를 종료하지 마시고 잠시만 기다려 주십시오.</li>
			</ul>
	<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket1.gif" alt="신청과정" /></h3>

<div id="listY">
	<table class="board_list">
	<colgroup><col width="110px" /><col width="270px" /><col width="150px" /><col width="90px" /><col width="70px" /></colgroup>
	<thead>
		<tr class="myclass">
			<th><p>분류</p></th>
			<th><p>과정명</p></th>
			<th><p>교육기간</p></th>
			<th class="end"><p>수강료</p></th>
		</tr>
	</thead>
	<tbody>
		<%= listY.toString() %>
	</tbody>
	</table>
</div>
<!-- form 끝 -->
	
	<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket3.gif" alt="총 결제금액" /></h3>
	<table class="box_list">
	<colgroup><col width="175px" /><col width="175px" /><col width="175px" /><col width="175px" /></colgroup>
	<thead>
	<tr>
		<th>수강료</th>
		<th>할인적용</th>
		<th>포인트사용</th>
		<th>실결제 금액</th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><%= box.getCommaInt("sumPrice") %> 원</td>
		<td>- <%= box.getCommaInt("discountPrice") %> 원</td>
		<td><input type='hidden' name='usePoint' value='<%= box.get("usePoint") %>'/>- <%= box.getCommaInt("usePoint") %> 원</td>
		<td><strong><em><input type='hidden' name='totalPrice' value='<%= box.get("totalPrice") %>'/><%= box.getCommaInt("totalPrice") %></em> 원</strong></td>
	</tr>
	</tbody>
	</table>
	
			<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket5.gif" alt="결제자 정보" /></h3>
			<table class="view_myclass">
			<colgroup><col width="15%" /><col width="85%" /></colgroup>
			<tr>
				<th>성명</th>
				<td class="end"><input type=hidden name=buyername size=20 value="<%=resultbox.get("d_name")%>"/><%= resultbox.get("d_name") %></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td class="end">
					<input type=hidden name=parentemail value=""/>
					<input type="text" class="inbox" name="buyeremail" id="buyeremail" style="width:162px;" value="<%= resultbox.get("d_email") %>" />
				</td>
			</tr>
			<tr>
				<th>휴대전화</th>
				<td class="end">
					<input type="hidden" name="buyertel" id="buyertel" value="<%= resultbox.get("d_handphone") %>" />
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel1" id="buyertel1" style="width:38px;" maxlength="3" value="<%= phone[0] %>" />-
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel2" id="buyertel2" style="width:38px;" maxlength="4" value="<%= phone[1] %>" />-
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel3" id="buyertel3" style="width:38px;" maxlength="4" value="<%= phone[2] %>" />
				</td>
			</tr>
			</table>
			<p class="board_basket_comment">전자우편과 이동전화번호 입력은 고객님의 결제성공 내역을 E-MAIL 또는 SMS로 알려드리기 위함이오니 반드시 기입하시기 바랍니다.</p>
			
		<% if( Integer.parseInt(box.getString("totalPrice")) > 0 ) { %>	
			<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket6.gif" alt="결제수단" /></h3>
			<table class="view_myclass">
			<colgroup><col width="15%" /><col width="85%" /></colgroup>
			<tr>
				<th>결제방법</th>
				<td class="end">
					<input name="paygubun" type="radio" value="CARD" value="radiobutton" id="paygubun" checked/>신용카드
					<input name="paygubun" value="DirectBANK" type="radio" value="radiobutton" id="paygubun" class="mg_l35" />실시간 계좌이체
				<!--	<input name="radiobutton" type="radio" value="radiobutton" class="mg_l35" />무통장 -->
				</td>
			</tr>
			<tr>
				<th>결제금액</th>
				<td class="end"><strong><em><%= box.getCommaInt("totalPrice") %></em> 원</strong></td>
			</tr>
			</table>
			<p class="board_btn ag_c mg_t20"><a href="javascript:if(pay(document.ini)) document.ini.submit();" class="btn_account"><span>결제하기</span></a><a href="javascript:fnCancel();" class="btn_account_gr"><span>취소</span></a></p>
		<% } else { %>
			<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket6.gif" alt="결제수단" /></h3>
			<table class="view_myclass">
			<colgroup><col width="15%" /><col width="85%" /></colgroup>
			<tr>
				<th>결제방법</th>
				<td class="end">
					무료
				</td>
			</tr>
			<tr>
				<th>결제금액</th>
				<td class="end"><strong><em><%= box.getCommaInt("totalPrice") %></em> 원</strong></td>
			</tr>
			</table>
			<p class="board_btn ag_c mg_t20"><a href="javascript:document.ini.submit();" class="btn_account"><span>결제하기</span></a><a href="javascript:fnCancel();" class="btn_account_gr"><span>취소</span></a></p>
		<% } %>
		
		<%} %>

<!--
상점아이디.
테스트를 마친 후, 발급받은 아이디로 바꾸어 주십시오.
-->
<input type=hidden name=mid value="<%= conf.getProperty("inipay.mid.real") %>"/>


<!--
화폐단위
WON 또는 CENT
주의 : 미화승인은 별도 계약이 필요합니다.
-->
<input type=hidden name=currency value="WON"/>


<!--
무이자 할부
무이자로 할부를 제공 : yes
무이자할부는 별도 계약이 필요합니다.
카드사별,할부개월수별 무이자할부 적용은 아래의 카드할부기간을 참조 하십시오.
무이자할부 옵션 적용은 반드시 매뉴얼을 참조하여 주십시오.
-->
<input type=hidden name=nointerest value="no"/>


<!--
카드할부기간
각 카드사별로 지원하는 개월수가 다르므로 유의하시기 바랍니다.

value의 마지막 부분에 카드사코드와 할부기간을 입력하면 해당 카드사의 해당
할부개월만 무이자할부로 처리됩니다 (매뉴얼 참조).
-->
<input type=hidden name=quotabase value="선택:일시불"/> <!--:3개월:4개월:5개월:6개월:7개월:8개월:9개월:10개월:11개월:12개월-->


<!-- 기타설정 -->
<!--
SKIN : 플러그인 스킨 칼라 변경 기능 - 6가지 칼라(ORIGINAL, GREEN, ORANGE, BLUE, KAKKI, GRAY)
HPP : 콘텐츠 또는 실물 결제 여부에 따라 HPP(1)과 HPP(2)중 선택 적용(HPP(1):콘텐츠, HPP(2):실물).
Card(0): 신용카드 지불시에 이니시스 대표 가맹점인 경우에 필수적으로 세팅 필요 ( 자체 가맹점인 경우에는 카드사의 계약에 따라 설정) - 자세한 내용은 메뉴얼  참조.
OCB : OK CASH BAG 가맹점으로 신용카드 결제시에 OK CASH BAG 적립을 적용하시기 원하시면 "OCB" 세팅 필요 그 외에 경우에는 삭제해야 정상적인 결제 이루어짐.
RECEIPT : 현금영수증 발행을 원하시는 경우 추가하시면 플러그인에서 현금영수증 발급을 위한 인터페이스를 보실수 있습니다
-->
<input type=hidden name=acceptmethod value="SKIN(ORIGINAL):HPP(1)"/> <!--:OCB-->

<!--
상점 주문번호 : 무통장입금 예약(가상계좌 이체),전화결재(1588 Bill) 관련 필수필드로 반드시 상점의 주문번호를 페이지에 추가해야 합니다.
결제수단 중에 실시간 계좌이체 이용 시에는 주문 번호가 결제결과를 조회하는 기준 필드가 됩니다.
상점 주문번호는 최대 40 BYTE 길이입니다.
-->
<input type=hidden name=oid size=40 value="mall_use_order_id"/>

<!--
플러그인 좌측 상단 상점 로고 이미지 사용
플러그인 좌측 상단에 상점 로고 이미지를 사용하실 수 있으며,
주석을 풀고 이미지가 있는 URL을 입력하시면 플러그인 상단 부분에 상점 이미지를 삽입할수 있습니다.
-->
<!--input type=hidden name=ini_logoimage_url  value="http://[사용할 이미지주소]"-->

<!--
좌측 결제메뉴 위치에 이미지 추가
좌측 결제메뉴 위치에 미미지를 추가하시 위해서는 담당 영업대표에게 사용여부 계약을 하신 후
주석을 풀고 이미지가 있는 URL을 입력하시면 플러그인 좌측 결제메뉴 부분에 이미지를 삽입할수 있습니다.
-->
<!--input type=hidden name=ini_menuarea_url value="http://[사용할 이미지주소]"-->

<!--
플러그인에 의해서 값이 채워지거나, 플러그인이 참조하는 필드들
삭제/수정 불가
-->
<input type=hidden name=quotainterest value=""/>
<input type=hidden name=paymethod value=""/>
<input type=hidden name=cardcode value=""/>
<input type=hidden name=cardquota value=""/>
<input type=hidden name=rbankcode value=""/>
<input type=hidden name=reqsign value="DONE"/>
<input type=hidden name=encrypted value=""/>
<input type=hidden name=sessionkey value=""/>
<input type=hidden name=uid value=""/>
<input type=hidden name=sid value=""/>
<input type=hidden name=version value=4000/>
<input type=hidden name=clickcontrol value=""/>

</form>

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
