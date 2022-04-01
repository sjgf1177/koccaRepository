<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 오프라인 결제시신청
//  2. 프로그램명: gu_OffPayCheck_P.jsp
//  3. 개      요: 오프라인 결제시신청
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.01.18
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

	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	String type = box.get("type");

	String p_listgubun = box.get("p_listgubun");
	String p_subj      = box.get("p_subj");
	String p_year      = box.get("p_year");
	String p_subjseq   = box.get("p_subjseq");
	String p_seq       = box.get("p_seq");

    String v_classname    = "";
    String v_area		  = "";
    String v_tid          = "";
    String v_goodname     = "";
    String v_buyername    = "";
    String v_buyertel     = "";
    String v_buyertel1    = "";
    String v_buyertel2    = "";
    String v_buyertel3    = "";
    String v_buyeremail   = "";
    String v_pgauthdate   = "";
    String v_edustart     = "";
    String v_eduend       = "";
    double v_price        = 0;
    double v_biyong       = 0;
    double v_total_biyong = 0;
    String v_subjnm       = "";
    
    ArrayList list = (ArrayList)request.getAttribute("offpayInfo");

    if (list.size() > 0) {
        DataBox dbox   = (DataBox)list.get(0);
        v_classname    = dbox.getString("d_classname");
        v_area		   = dbox.getString("d_area");
        v_tid          = dbox.getString("d_tid");
        v_goodname     = dbox.getString("d_subjnm");
        v_buyername    = dbox.getString("d_buyername");
        v_buyertel     = dbox.getString("d_buyertel");
        v_buyeremail   = dbox.getString("d_buyeremail");
        v_pgauthdate   = dbox.getString("d_pgauthdate");
        v_pgauthdate   = FormatDate.getFormatDate(v_pgauthdate,"yyyy.MM.dd");
        v_edustart     = dbox.getString("d_edustart");
        v_eduend       = dbox.getString("d_eduend");
        v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
        v_eduend       = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
        v_total_biyong = dbox.getDouble("d_biyong");
        v_price        = dbox.getDouble("d_price");
        
        String[]	arrBuyertel	= v_buyertel.split("-");

    	//휴대전화번호 처리
    	v_buyertel		= v_buyertel.replace("-","");
    	if (v_buyertel.length() != 0) {
    		if (arrBuyertel.length == 3) {
    			v_buyertel1	= arrBuyertel[0];
    			v_buyertel2	= arrBuyertel[1];
    			v_buyertel3	= arrBuyertel[2];
    		} else {
    			if (v_buyertel.length() >= 3) v_buyertel1	= v_buyertel.substring(0,3);
    			if (v_buyertel.length() >= 7) {
    				v_buyertel2	= v_buyertel.substring(3,7);
    			} else if (v_buyertel.length() > 3 && v_buyertel.length() <= 7) {
    				v_buyertel2	= v_buyertel.substring(3,v_buyertel.length());
    			}
    			if (v_buyertel.length() > 7) v_buyertel3	= v_buyertel.substring(7);
    		}
    	}
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>결제정보확인</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script language=javascript src="http://plugin.inicis.com/pay40.js"></script>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
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
		document.ini.price.value = document.ini.price.value.replace(/\,/gi,"");
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
		top.window.focus();
}

function fnCancel() {
	if(confirm('결제를 취소하시겠습니까?')) {
		top.window.close();
	  }
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
<body onload="javascript:enable_click()" onFocus="javascript:focus_control()" id="popup"><!-- popup size : 400*277 -->
<form name="f1" method="post">
	<input type="hidden" name="p_process"/>
</form>
<!-- ******************************** 스크립트영역 ******************************** -->
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<!-- <form name=ini method=post action="/inicis/INIsecurepay.jsp" onsubmit="return pay(this)"> -->
<form name=ini method=post action="/servlet/controller.bill.OffBillServlet" onsubmit="return pay(this)">
	<input type="hidden" name="gopaymethod" value=""/>
	<input type=hidden name=goodname size=20 value="<%=v_goodname%>"/> <!-- 상품명 -->
	<input type="hidden" name="ispointuse" value="N"/>
	<input type="hidden" name="point" value="0"/>
	<input type="hidden" name="p_gubun" value="N"/><!-- 구분(N:일반,P:패키지,B:도서)  -->
	<input type="hidden" name="price" value="<%= new java.text.DecimalFormat("###,###,##0").format(v_price) %>"/>
	<input type="hidden" name="tem_grcode" value="<%=box.getSession("tem_grcode")%>"/>

	<input type="hidden" name="type"	value="<%=type%>"/>
	<input type="hidden" name="p_process"	value="securepay"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	
	<input type="hidden" name="p_listgubun" value="<%= p_listgubun %>"/>
	<input type="hidden" name="p_subj"      value="<%= p_subj %>"/>
	<input type="hidden" name="p_year"      value="<%= p_year %>"/>
	<input type="hidden" name="p_subjseq"   value="<%= p_subjseq %>"/>
	<input type="hidden" name="p_seq"       value="<%= p_seq %>"/>
	
	<input type="hidden" name="p_area"       value="<%= v_area %>"/>

	<div id="pop_header">
		<h1><img src="/images/portal/popup/myclass_h1_tit1.gif" alt="결제정보확인" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<ul class="basket_tab">
				<li><img src="/images/portal/myclass/basket_off_tab1_on.gif" alt="결제정보확인" /></li>
				<li><img src="/images/portal/myclass/basket_off_tab2_off.gif" alt="결제완료" /></li>
			</ul>
			
			<p class="myclass_basket_txt">결제버튼을 누르면 결제 정보를 안전하게 암호화하기 위한 플러그인 창이 출력됩니다. 플러그인에서 제시하는 단계에 따라 정보를<br /> 입력한 후 [결제 정보 확인] 단계에서 확인버튼을 누르면 결제처리가 됩니다. 통신환경에 따라 다소 시간이 걸릴수도 있으니 결제결과가<br /> 표시될때까지 중지버튼을 누르거나 브라우저를 종료하지 마시고 잠시만 기다려 주십시오.</p>
			
			<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket1.gif" alt="신청과정" /></h3>
			<table class="board_list">
			<colgroup><col width="110px" /><col width="340px" /><col width="150px" /><col width="90px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>분류</p></th>
				<th><p>과정명</p></th>
				<th><p>교육기간</p></th>
				<th class="end"><p>수강료</p></th>
			</tr>
			</thead>
			<tbody>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td><%= v_classname %></td>
				<td class="ta_l"><a href=""><strong><%= v_goodname %></strong></a></td>
				<td class="num"><%= v_edustart %> ~ <%= v_eduend %></td>
				<td class="ta_r num"><strong><%= new java.text.DecimalFormat("###,###,##0").format(v_total_biyong) %>원</strong></td>
			</tr>
			</tbody>
			</table>
			
			<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket3.gif" alt="총 결제금액" /></h3>
			<table class="view_myclass">
			<colgroup><col width="15%" /><col width="85%" /></colgroup>
			<tr>
				<th>총 결제금액</th>
				<td colspan="3" class="end"><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %>원</td>
			</tr>
			</table>
			
			<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket5.gif" alt="결제자 정보" /></h3>
			<table class="view_myclass">
			<colgroup><col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" /></colgroup>
			<tr>
				<th>성명</th>
				<td colspan="3" class="end"><input type=hidden name="buyername" value="<%= v_buyername %>"/><%= v_buyername %></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="buyeremail" class="inbox" style="width:162px;" value="<%= v_buyeremail %>" /></td>
				<th>휴대전화</th>
				<td class="end">
					<input type="hidden" name="buyertel" id="buyertel" value="<%= v_buyertel %>" />
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel1" id="buyertel1" style="width:38px;" maxlength="3" value="<%= v_buyertel1 %>" />-
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel2" id="buyertel2" style="width:38px;" maxlength="4" value="<%= v_buyertel2 %>" />-
					<input type="text" onchange="$('#buyertel').val($('#buyertel1').val()+'-'+$('#buyertel2').val()+'-'+$('#buyertel3').val());" class="inbox" name="buyertel3" id="buyertel3" style="width:38px;" maxlength="4" value="<%= v_buyertel3 %>" />
				</td>
			</tr>
			</table>
			<p class="board_basket_comment">전자우편과 이동전화번호 입력은 고객님의 결제성공 내역을 E-MAIL 또는 SMS로 알려드리기 위함이오니 반드시 기입하시기 바랍니다.</p>
			
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
				<td class="end"><strong><em><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %></em> 원</strong></td>
			</tr>
			</tr>
			</table>
		</div>
	</div>
	<div id="pop_footer" style="height:57px;">
		<p class="f_btn"><a href="javascript:if(pay(document.ini)) document.ini.submit();" class="btn_account"><span>결제하기</span></a><a href="javascript:fnCancel();" class="btn_account_gr"><span>취소</span></a></p>
	</div>

<!--
상점아이디.
테스트를 마친 후, 발급받은 아이디로 바꾸어 주십시오.
-->
<%
		String area			= box.get("p_area");
		String mid = null;
		if ( area.equals("G0") ) {			// 게임 mallId kocca00000
			mid	=	conf.getProperty("inipay.mid.realgame");
		} else if( area.equals("K0") ) {	// 문콘 mallId kocca00002
			mid	=	conf.getProperty("inipay.mid.realcontent");
		} else if( area.equals("B0") ) {	// 방송 mallId kocca00004
			mid	=	conf.getProperty("inipay.mid.realbroad");
		} else {
			mid	=	conf.getProperty("inipay.mid.realgame");
		}
%>

<input type=hidden name=mid value="<%= mid %>"/>

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
</body>
</html>