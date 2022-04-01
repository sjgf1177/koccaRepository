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
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.account.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.book.*" %>

<%@ page import = "com.credu.propose.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	String v_usernm = box.getSession("name");
	String v_email  = box.getSession("email");

	String  v_tem_grcode   = box.getSession("tem_grcode");

	String v_process= box.getString("p_process");

	String  v_isevent   = box.getSession("isevent");	//과정당 50% 할인회원 구분

	int v_billprice = 0;

	ArrayList list1 = null;

	String v_gubun = box.getString("p_gubun");

	AccountManagerBean bean = new AccountManagerBean();

	ProposeCourseBean bean2  = new ProposeCourseBean();

	if (v_gubun.equals("N")) { //일반과정
		list1 = bean.selectBasketSubj(box);
	} else if (v_gubun.equals("P")) { //패키지과정
		list1 = bean.selectBasketPackage(box);
	} else if (v_gubun.equals("B")) { //도서
		list1 = bean.selectBasketBook(box);
	}

	String v_goodname = "";

	//도서
	String v_name    = "";
    String v_post1   = "";
    String v_post2   = "";
    String v_addr    = "";
    String v_addr2   = "";
    String v_hometel = "";



	// 이벤트 부분 start
	String  s_userid = box.getSession("userid");
	String currentDate = new java.text.SimpleDateFormat("yyyyMMdd", new java.util.Locale("en", "US")).format(new java.util.Date());
	int iCurrentDate = Integer.parseInt(currentDate);
	// 이벤트 부분 end
%>

<html>
<head>
<title>결제페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:10pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
table, img {border:none}

/* Padding ******/
.pl_01 {padding:1 10 0 10; line-height:19px;}
.pl_03 {font-size:20pt; font-family:굴림,verdana; color:#FFFFFF; line-height:29px;}

/* Link ******/
.a:link  {font-size:9pt; color:#333333; text-decoration:none}
.a:visited { font-size:9pt; color:#333333; text-decoration:none}
.a:hover  {font-size:9pt; color:#0174CD; text-decoration:underline}

.txt_03a:link  {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:visited {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:hover  {font-size: 8pt;line-height:18px;color:#EC5900; text-decoration:underline}
</style>

<script language=javascript src="http://plugin.inicis.com/pay40.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>

<script language=javascript>
StartSmartUpdate();
</script>

<!----------------------------------------------------------------------------------
※ 주의 ※
 상단 자바스크립트는 지불페이지를 실제 적용하실때 지불페이지 맨위에 위치시켜
 적용하여야 만일에 발생할수 있는 플러그인 오류를 미연에 방지할 수 있습니다.

  <script language=javascript src="http://plugin.inicis.com/pay40.js"></script>
  <script language=javascript>
  StartSmartUpdate();	// 플러그인 설치(확인)
  </script>
----------------------------------------------------------------------------------->


<script language=javascript>

var openwin;

function isnum(data)
{
  for (var i = 0 ;i < data.value.length ;i++ )
  {
    if ((data.value.substring(i,i+1) < "0" || data.value.substring(i,i+1) > "9" )) {
      	return false;
    } else {
    	return true;
    }
  }
}


function cal_price() {
	ff = document.ini;

    if (isnum(ff.point) == false) {
    	return;
    }


	ff.price.value =  parseInt(ff.subjprice.value) - parseInt(ff.point.value);

	//ff.price.value = fnMoneyType(ff.price.value);
}

function fnMoneyType(v) {
        v = v.toString();
        if (v.length > 3) {
                var mod = v.length % 3;
                var retval = (mod > 0 ? (v.substring(0,mod)) : "");
                for (i=0 ; i < Math.floor(v.length / 3); i++) {
                        if ((mod == 0) && (i == 0)) {
                                retval += v.substring(mod+ 3 * i, mod + 3 * i + 3);
                        } else {
                                retval+= "," + v.substring(mod + 3 * i, mod + 3 * i + 3);
                        }
                }
                return retval;
        } else {
                return v;
        }
}

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

		if (document.ini.paygubun(2).checked) {
			document.ini.gopaymethod.value = "VBank";
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
/*
		if (document.ini.paygubun(2).checked) {


			if(document.ini.p_inputname.value.length < 1)
			{
				alert("입금자를 입력해 주세요.");
				document.ini.p_inputname.focus();
				return false;
			}
			if(document.ini.p_inputdate.value.length != 8 )
			{
				alert("입금일을 입력해 주세요.");
				document.ini.p_inputdate.focus();
				return false;
			}

			document.ini.gopaymethod.value = "BankBook";
			document.ini.action="/inicis/INIsecurepayBankBook.jsp";
			document.ini.submit();
			return true;
		}
*/
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

				frm.nointerest.value = "yes";
				frm.quotabase.value = "선택:일시불:3개월:4개월:5개월:6개월:7개월:8개월:9개월:10개월:11개월:12개월";
				//frm.quotabase.value = "일시불";

			}


			if (MakePayMessage(frm))
			{
				disable_click();
				openwin = window.open("childwin.html","childwin","width=299,height=149");
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

//숫자만 입력 가능
function numcheck(oTarget)
{
	if(((event.keyCode < 48) || (event.keyCode > 57)) && ((event.keyCode < 96) || (event.keyCode > 105)))
		if((event.keyCode == 9) || (event.keyCode == 8) || (event.keyCode == 46))
			event.returnValue = true;
		else
			event.returnValue = false;
}

function chkPayMethod() {
	//if (document.ini.paygubun(2).checked) {
	//	IDBank.style.display = "";
	//} else {
		IDBank.style.display = "none";
	//}
}

window.resizeTo(620,720);

</script>

<script language="javascript">

// 우편번호검색
function searchPost() {
    window.self.name = "PersonalSelect";
    open_window("openPost","","100","100","417","400","","","","yes","");
    document.f1.target = "openPost";
    document.f1.action='/servlet/controller.library.PostSearchServlet';
    document.f1.p_process.value = 'SearchPostOpenPage';
    document.f1.submit();
    document.f1.target = window.self.name;
}
// 주소 받아오기
function receivePost(post1, post2, addr, postgubun){
    document.ini.p_post1.value = post1;
    document.ini.p_post2.value = post2;
    document.ini.p_addr1.value = addr;
    document.ini.p_addr2.focus();

}
</script>

<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
//-->
</script>
</head>

<!-----------------------------------------------------------------------------------------------------
※ 주의 ※
 아래의 body TAG의 내용중에
 onload="javascript:enable_click()" onFocus="javascript:focus_control()" 이 부분은 수정없이 그대로 사용.
 아래의 form TAG내용도 수정없이 그대로 사용.
------------------------------------------------------------------------------------------------------->

<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=0 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0 onload="javascript:enable_click()" onFocus="javascript:focus_control()"><center>
<form name="f1" method="post">
	<input type="hidden" name="p_process">
</form>
<form name=ini method=post action="/inicis/INIsecurepay.jsp" onSubmit="return pay(this)">

<table width="590" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" bgcolor="f3f3eb">
    <br>
    <table width="508" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center"><img src="/images/user/game/account/ti_set.gif" width="532" height="35"></td>
      </tr>
    </table>

    <table width="508" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <br>
                 "결제" 버튼을 누르면 결제 정보를 안전하게 암호화하기 위한 플러그인 창이 출력됩니다.<br>
                  플러그인에서 제시하는 단계에 따라 정보를 입력한 후 <b>[결제 정보 확인]</b> 단계에서 <br>"확인" 버튼을 누르면
                  결제처리가 시작됩니다.<br>
              통신환경에 따라 다소 시간이 걸릴수도 있으니 결제결과가 표시될때까지 "중지" 버튼을 누르거나 브라우저를 종료하지 마시고
              잠시만 기다려 주십시오.<br></td>
              </tr>
        </table>
           <br>
      <table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td><img src="/images/user/game/account/ti_set_01.gif" width="192" height="31"></td>
        </tr>
    </table>

		<!-- 상품 리스트 시작 -->
	<% if (v_gubun.equals("N")) { //일반과정 %>
			<!-- 일반과정 시작  -->
	<table width="508" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="5">일반과정
	  </td>
      </tr>
    </table>

	<table width="508" border="0" cellpadding="0" cellspacing="1" bgcolor="c1bba5">
      <tr>
        <td height="25" width="50" align="center" bgcolor="ddd9cd">NO</td>
        <td height="25" align="center" bgcolor="ddd9cd">과정명</td>
        <td width="50" align="center" bgcolor="ddd9cd">차수</td>
        <td width="100" align="center" bgcolor="ddd9cd">수강료</td>
      </tr>
	  <% int v_sumprice = 0;
	  	 int v_dcrate = 0;
	  	 String v_firstSubjnm = "";

	  	 int v_pricecnt = 0;

	  	 for (int i=0; i<list1.size(); i++) {
	  		DataBox dbox = (DataBox)list1.get(i);

	  		if (i==0) { v_firstSubjnm = dbox.getString("d_subjnm"); }

	  		String v_subjnm   = dbox.getString("d_subjnm");
	  		String v_subj	  = dbox.getString("d_subj");
	  		String v_year	  = dbox.getString("d_year");
	  		String v_subjseq  = dbox.getString("d_subjseq");
	  		String v_edustart = dbox.getString("d_edustart");
	  		String v_eduend   = dbox.getString("d_eduend");
	  		int    v_price	  = dbox.getInt("d_price");

	  		if (v_price>0) v_pricecnt++;

			if(v_tem_grcode.equals("N000002") && v_isevent.equals("Y") && (s_userid.equals("gameacademy") || (iCurrentDate >= 20090605 && iCurrentDate <= 20090731))) {
	  			v_price = 10000;
	  		}

	  		v_sumprice += v_price;
	  %>
	  <tr>
        <td height="25" align="center" bgcolor="#FFFFFF"><%=i+1%></td>
        <td bgcolor="#FFFFFF" class="tbl_gleft" style="padding-left:10px;"><%=v_subjnm%></td>
        <td height="25" align="center" bgcolor="#FFFFFF"><%=v_subjseq%></td>
        <td height="25" colspan="2" align="center" bgcolor="#FFFFFF"><%=new java.text.DecimalFormat("###,###,##0").format(v_price)%></td>
      </tr>
	<% } %>

	<% if (list1.size()==0) { %>
	<tr>
	  <td colspan=4 align=center>등록된 내용이 없습니다.</td>
	</tr>
	<% } %>

	<tr>
        <td height="25" colspan="5" align="center" bgcolor="#FFFFFF">&nbsp;</td>
        </tr>

      <tr>
        <td height="25" colspan="3" align="left" bgcolor="ddd9cd" style="padding-left:10px;">합&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;계</td>
        <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="24" height="8">
        <%=new java.text.DecimalFormat("###,###,##0").format(v_sumprice)%>
        </td>
      </tr>
      <tr>
        <td height="25" colspan="3" align="left" bgcolor="ddd9cd" style="padding-left:10px;">할&nbsp;인&nbsp;율</td>
        <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="24" height="8">
	<%
	if(v_tem_grcode.equals("N000002") && v_isevent.equals("Y") && (s_userid.equals("gameacademy") || (iCurrentDate >= 20090605 && iCurrentDate <= 20090731))) {
		out.print("0%"); v_dcrate = 0;
	} else {
		/*	50%할인 이벤트
         if (v_pricecnt==1) 	 { out.print("0%"); v_dcrate = 0; }
	  	 else if (v_pricecnt==2) { out.print("20%"); v_dcrate = 20; }
	  	 else if (v_pricecnt==3) { out.print("30%"); v_dcrate = 30; }
	  	 else if (v_pricecnt==4) { out.print("40%"); v_dcrate = 40; }
	  	 else if (v_pricecnt==5) { out.print("50%"); v_dcrate = 50; }
	  	 else { out.print("0%"); v_dcrate = 0; }
	  	 */
	  	 out.print("0%"); v_dcrate = 0;
	}
	  %>
	   </td>
      </tr>
      <tr>
        <td height="25" colspan="3" align="left" bgcolor="ddd9cd" style="padding-left:10px;">결제금액</td>
        <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="24" height="8">
	  	<%
        v_billprice = v_sumprice - (v_sumprice * v_dcrate / 100);
	  	out.print(new java.text.DecimalFormat("###,###,##0").format(v_billprice));
	  	%>
	  	</td>
      </tr>

	<tr>
        <td height="25" colspan="5" align="left" bgcolor="#FFFFFF" style="padding-left:10px;">결&nbsp;제&nbsp;명 :
         <%
         if (list1.size()>0) {
	  		if (list1.size()==1) {
	  			out.print(v_firstSubjnm);
	  			v_goodname = v_firstSubjnm;
	  		} else {
	  			v_goodname = v_firstSubjnm + " 외 " + (list1.size() - 1) + "건";
	  			out.print(v_goodname);
	  		}
	     }
	  %>
        </td>
        </tr>


	</table>
	<!-- 일반과정 끝 -->

	<% } else if (v_gubun.equals("P")) { //패키지과정 %>
	<!-- 전문가과정 시작 -->
	<table width="508" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="5">전문가과정</td>
          </tr>
        </table>
	<table width="508" border="0" cellpadding="0" cellspacing="1" bgcolor="c1bba5">
      <tr>
        <td height="25" width="50" align="center" bgcolor="ddd9cd">NO</td>
        <td height="25" align="center" bgcolor="ddd9cd" colspan=2>과정명</td>
        <td width="50" align="center" bgcolor="ddd9cd">차수</td>
        <td width="100" align="center" bgcolor="ddd9cd">수강료</td>
      </tr>
	  <% int v_sumprice = 0;
		 String v_tmp_course   = "";
		 String v_course       = "";
		 String v_firstSubjnm = "";
		 int    v_coutsecnt   = 0;

	  	 for (int i=0; i<list1.size(); i++) {
	  		DataBox dbox = (DataBox)list1.get(i);

	  		if (i==0) { v_firstSubjnm = dbox.getString("d_scsubjnm"); }

	  		String v_scsubjnm   = dbox.getString("d_scsubjnm");
	  		String v_scsubj	  = dbox.getString("d_scsubj");
	  		String v_scyear	  = dbox.getString("d_scyear");
	  		String v_scsubjseq  = dbox.getString("d_scsubjseq");
	  		int    v_scbiyong  = dbox.getInt("d_scbiyong");
	  		int    v_subjcnt  = dbox.getInt("d_subjcnt");

	  		v_course        = dbox.getString("d_course");

	  		String v_subjnm   = dbox.getString("d_subjnm");
	  		String v_subj	  = dbox.getString("d_subj");
	  		String v_year	  = dbox.getString("d_year");
	  		String v_subjseq  = dbox.getString("d_subjseq");
	  		String v_edustart = dbox.getString("d_edustart");
	  		String v_eduend   = dbox.getString("d_eduend");
	  		int    v_price	  = dbox.getInt("d_price");



	  		if(!v_tmp_course.equals(v_course)) {
                v_tmp_course = v_course;
                v_sumprice += v_scbiyong;
                v_coutsecnt++;
                %>
		  <tr>
	        <td height="25" align="center" bgcolor="#FFFFFF" rowspan=<%=v_subjcnt%>><%=i+1%></td>
	        <td bgcolor="#FFFFFF" class="tbl_gleft" rowspan=<%=v_subjcnt%> style="padding-left:10px;"><%=v_scsubjnm%></td>
	        <td height="25" align="center" bgcolor="#FFFFFF" style="padding-left:10px;"><%=v_subjnm%></td>
	        <td height="25" align="center" bgcolor="#FFFFFF"><%=v_subjseq%></td>
	        <td height="25" align="center" bgcolor="#FFFFFF" rowspan=<%=v_subjcnt%>><%=new java.text.DecimalFormat("###,###,##0").format(v_scbiyong)%></td>
	      </tr>
	  	<% } else { %>
          <tr >
		     <td height="25" align="center" bgcolor="#FFFFFF" style="padding-left:10px;"><%=v_subjnm%></td>
		     <td height="25" align="center" bgcolor="#FFFFFF" style="padding-left:10px;"><%=v_subjseq%></td>
          </tr>
	  	<% }  %>
      <% } %>

		  <% if (list1.size()==0) { %>
			<tr>
			  <td colspan=5 align=center>등록된 내용이 없습니다.</td>
			</tr>
		  <% } %>

	<tr>
        <td height="25" colspan="4" align="left" bgcolor="ddd9cd" style="padding-left:10px;">결제금액</td>
        <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="24" height="8">
        <%v_billprice = v_sumprice;
	  	out.print(new java.text.DecimalFormat("###,###,##0").format(v_billprice));
	  %></td>
      </tr>

      <tr>
        <td height="25" colspan="5" align="center" bgcolor="#FFFFFF">&nbsp;</td>
        </tr>

      <tr>
        <td height="25" colspan="5" align="left" bgcolor="ddd9cd" style="padding-left:10px;">결제명 :
        <% if (list1.size()>0) {
	  		if (v_coutsecnt==1) {
	  			out.print(v_firstSubjnm);
	  			v_goodname = v_firstSubjnm;
	  		} else {
	  			v_goodname = v_firstSubjnm + " 외 " + (v_coutsecnt - 1) + "건";
	  			out.print(v_goodname);
	  		}
	     }
	  %>
	  </td>
      </tr>
    </table>
	<!-- 전문가과정 끝 -->

	<% } else if (v_gubun.equals("B")) { //도서

	  	 String v_firstSubjnm = "";
	  	 int    v_coutsecnt   = 0;

    MemberInfoBean memberbean = new MemberInfoBean();
    DataBox dbox2 =  memberbean.getMemberInfo(box);
    if (dbox2 != null) {
		v_name    = dbox2.getString("d_name");
        v_post1   = dbox2.getString("d_post1");
        v_post2   = dbox2.getString("d_post2");
        v_addr    = dbox2.getString("d_addr");
        v_addr2   = dbox2.getString("d_addr2");
        v_hometel = dbox2.getString("d_hometel");
    }

	%>

	<!-- 도서 시작 -->
	<table width="508" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="5">도서</td>
      </tr>
    </table>

    <table width="508" border="0" cellpadding="0" cellspacing="1" bgcolor="c1bba5">
      <tr>
        <td height="25" align="center" bgcolor="ddd9cd">NO</td>
        <td height="25" align="center" bgcolor="ddd9cd">도서명</td>
        <td align="center" bgcolor="ddd9cd">수량</td>
        <td align="center" bgcolor="ddd9cd">가격</td>
        <td align="center" bgcolor="ddd9cd">합계</td>
      </tr>
<%
			  int v_booksumprice = 0;
			  v_coutsecnt=list1.size();
			  for (int i=0; i<list1.size(); i++) {
		  		DataBox dbox = (DataBox)list1.get(i);

		  		String v_bookname = dbox.getString("d_bookname");
		  		String v_bookcode = dbox.getString("d_bookcode");
		  		int    v_seq	  = dbox.getInt("d_seq");
		  		int    v_price    = dbox.getInt("d_price");
		  		int    v_unit     = dbox.getInt("d_unit");
		  		int    v_amount   = dbox.getInt("d_amount");
		  		v_booksumprice    = v_booksumprice + v_amount;

		  		if (i==0) { v_firstSubjnm = dbox.getString("d_bookname"); }
	  		%>
	  <tr>
        <td width="50" height="25" align="center" bgcolor="#FFFFFF"><%=i+1%></td>
        <td bgcolor="#FFFFFF" class="tbl_gleft" style="padding-left:10px;"><%=v_bookname%></td>
        <td height="25" align="center" bgcolor="#FFFFFF"><%=new java.text.DecimalFormat("##,###,##0").format(v_unit)%></td>
        <td height="25" align="center" bgcolor="#FFFFFF"><%=new java.text.DecimalFormat("##,###,##0").format(v_price)%></td>
        <td height="25" align="center" bgcolor="#FFFFFF"><%=new java.text.DecimalFormat("##,###,##0").format(v_amount)%></td>
      </tr>
      <% }
      v_billprice = v_booksumprice;
       %>
      <% if (list1.size()!=0) { %>
        <tr>
            <td bgcolor="#FFFFFF" colspan=4 align=center>합계</td>
            <td bgcolor="#FFFFFF" align=center><%=new java.text.DecimalFormat("##,###,##0").format(v_booksumprice)%></td>
        </tr>
      <% } %>

       <% if (list1.size()>0) {
	  		if (v_coutsecnt==1) {
	  			out.print(v_firstSubjnm);
	  			v_goodname = v_firstSubjnm;
	  		} else {
	  			v_goodname = v_firstSubjnm + " 외 " + (v_coutsecnt - 1) + "건";
	  			out.print(v_goodname);
	  		}
	     }
	  %>
    </table>
	<% } %>
	<!-- 도서 끝 -->
	<!-- 상품 리스트 끝 -->

	<br>
        <table width="508" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="/images/user/game/account/ti_set_02.gif" width="192" height="31"></td>
          </tr>
        </table>
        <table width="508" border="0" cellpadding="0" cellspacing="1" bgcolor="c1bba5">
          <tr>
            <td width="118" height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">성명</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="24" height="8">
              <input type=hidden name=buyername size=20 value="<%=v_usernm%>">
              <%=v_usernm%></td>
          </tr>
          <tr>
            <td width="118" height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">전자우편</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="25" height="8">
              <input type=text name=buyeremail class="input" size=20 value="<%=v_email%>">
              <input type=hidden name=parentemail value=""></td>
          </tr>
          <tr>
            <td width="118" height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">이동전화</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="20" height="8">
            	&nbsp;<input type=text name="buyertel1" size=3 class="input" maxlength="3">-
            	<input type=text name="buyertel2" size=4 class="input" maxlength="4">-
            	<input type=text name="buyertel3" size=4 class="input" maxlength="4">
              	<input type=hidden name="buyertel"></td>
          </tr>

          <!-- 수취인 정보 시작 -->
          <tr>
            <td width="118" height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">수취인 성명</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="20" height="8">&nbsp;<input type="text" class="input" name="p_receive" maxlength="10" value="<%=v_name%>"></td>
          </tr>
          <tr>
            <td width="118" height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">수취인 전화번호</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="20" height="8">&nbsp;<input type="text" class="input" name="p_phone" maxlength="14" value="<%=v_hometel%>"></td>
          </tr>
          <tr>
            <td width="118" height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">수취인 우편번호</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="20" height="8">&nbsp;<input type="text" class="input" name="p_post1" maxlength="3" size="4" value="<%=v_post1%>" readOnly> -
                    <input type="text" class="input" name="p_post2" maxlength="3" size="4" value="<%=v_post2%>" readOnly>
                    <a href="javascript:searchPost()"><img src="/images/user/game/button/btn_zipcode.gif" align="absmiddle"width="87" height="18" border="0"> </a></td>
          </tr>
          <tr>
            <td width="118" height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">수취인 주소</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="20" height="8">&nbsp;<input type="text" class="input" name="p_addr1" maxlength="100" size= "60" value="<%=v_addr%>"><br>
                    <img src="/images/user/game/account/spacer.gif" width="20" height="8">&nbsp;<input type="text" class="input" name="p_addr2" maxlength="100" size= "60" value="<%=v_addr2%>"></td>
          </tr>
          <!-- 수취인 정보 끝 -->
        </table>
		 <table width="512" height="54" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" background="/images/user/game/account/bg_mo.gif"><table width="480" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>* 전자우편과 이동전화번호를 입력받는 것은 고객님의 결제성공 내역을 <strong><font color="ff9c00">E-MAIL</font></strong> 또는
                <strong><font color="ff9c00">SMS</font></strong>로 알려드리기 위함이오니
                반드시 기입하시기 바랍니다. </td>
            </tr>
          </table></td>
        </tr>
      </table>


      <br>
        <table width="508" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="/images/user/game/account/ti_set_03.gif" width="192" height="31"></td>
          </tr>
        </table>
        <table width="508" border="0" cellpadding="0" cellspacing="1" bgcolor="c1bba5">
          <tr>
            <td width="118" height="25" align="center" bgcolor="ddd9cd">결제방법</td>
            <td height="25" bgcolor="#FFFFFF"> <img src="/images/user/game/account/spacer.gif" width="15" height="8">
              <input type="radio" name="paygubun" value="CARD" onClick="chkPayMethod();">
              신용카드 결제<br> <img src="/images/user/game/account/spacer.gif" width="15" height="8">
              <input type="radio" name="paygubun" value="DirectBANK" onClick="chkPayMethod();">
              실시간 계좌이체<br> <img src="/images/user/game/account/spacer.gif" width="15" height="8">
              <input type="radio" name="paygubun" value="VBank" onClick="chkPayMethod();">
              무통장(가상계좌)<br>
              <input type="hidden" name="gopaymethod" value="">
            </td>
          </tr>

          <tr id="IDBank" style="display:none">
          	<td width="118" height="25" align="center" bgcolor="ddd9cd">무통장 입금</td>
          	<td height="25" bgcolor="#FFFFFF">
          		<table border=0>
          			<tr>
          				<td width=80>입금계좌</td>
          				<td>361437-04-001935 국민은행</td>
          			</tr>
          			<tr>
         				<td width=80>입금자명</td>
          				<td><input type="text" class="input" name="p_inputname" value="<%=v_usernm%>"></td>
					</tr>
          			<tr>
         				<td width=80>입금일자</td>
          				<td><input type="text" class="input" name="p_inputdate" maxlength="8" onkeydown="javascript:return numcheck(this)"> "-" 없이(ex.20060130)</td>
					</tr>
          		</table>
          	</td>
          </tr>

			<input type=hidden name=goodname size=20 value="<%=v_goodname%>"> <!-- 상품명 -->
          	<input type="hidden" name="ispointuse" value="N">
          	<input type="hidden" name="point" value="0">
          	<input type="hidden" name="price" value="<%=v_billprice%>">
          	<input type="hidden" name="tem_grcode" value="<%=box.getSession("tem_grcode")%>">
           <tr>
            <td height="25" align="center" background="/images/user/game/account/td_bg.gif" bgcolor="ddd9cd">결제금액</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/images/user/game/account/spacer.gif" width="24" height="8">
              <%=new java.text.DecimalFormat("###,###,###").format(v_billprice)%>
            </td>
          </tr>
        </table>
        <table width="508" height="1" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td bgcolor="c1bba5"></td>
        </tr>
      </table>
            <br>


     <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td></td>
      </tr>
    </table>
      <table width="100%" height="62" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" ><table width="120" border="0" cellspacing="0" cellpadding="0">
            <tr align="center">
              <td><input type=image src="/images/user/game/account/b_setok.gif" width="54" height="21" border="0"></td>
              <td><a href="javascript:window.close();"><img src="/images/user/button/btn_cancel.gif" width="45" height="21" border="0"></a></td>
              </tr>
          </table></td>
        </tr>
      </table>      </td>
  </tr>
</table>


<!--
상점아이디.
테스트를 마친 후, 발급받은 아이디로 바꾸어 주십시오.
-->
<% if (request.getServerName().indexOf("210.118.65.146")==0) { %>
<input type=hidden name=mid value="INIpayTest">
<% } else { %>
<input type=hidden name=mid value="kocca00001">
<% } %>


<!--
화폐단위
WON 또는 CENT
주의 : 미화승인은 별도 계약이 필요합니다.
-->
<input type=hidden name=currency value="WON">


<!--
무이자 할부
무이자로 할부를 제공 : yes
무이자할부는 별도 계약이 필요합니다.
카드사별,할부개월수별 무이자할부 적용은 아래의 카드할부기간을 참조 하십시오.
무이자할부 옵션 적용은 반드시 매뉴얼을 참조하여 주십시오.
-->
<input type=hidden name=nointerest value="no">


<!--
카드할부기간
각 카드사별로 지원하는 개월수가 다르므로 유의하시기 바랍니다.

value의 마지막 부분에 카드사코드와 할부기간을 입력하면 해당 카드사의 해당
할부개월만 무이자할부로 처리됩니다 (매뉴얼 참조).
-->
<input type=hidden name=quotabase value="선택:일시불"> <!--:3개월:4개월:5개월:6개월:7개월:8개월:9개월:10개월:11개월:12개월-->


<!-- 기타설정 -->
<!--
SKIN : 플러그인 스킨 칼라 변경 기능 - 6가지 칼라(ORIGINAL, GREEN, ORANGE, BLUE, KAKKI, GRAY)
HPP : 콘텐츠 또는 실물 결제 여부에 따라 HPP(1)과 HPP(2)중 선택 적용(HPP(1):콘텐츠, HPP(2):실물).
Card(0): 신용카드 지불시에 이니시스 대표 가맹점인 경우에 필수적으로 세팅 필요 ( 자체 가맹점인 경우에는 카드사의 계약에 따라 설정) - 자세한 내용은 메뉴얼  참조.
OCB : OK CASH BAG 가맹점으로 신용카드 결제시에 OK CASH BAG 적립을 적용하시기 원하시면 "OCB" 세팅 필요 그 외에 경우에는 삭제해야 정상적인 결제 이루어짐.
RECEIPT : 현금영수증 발행을 원하시는 경우 추가하시면 플러그인에서 현금영수증 발급을 위한 인터페이스를 보실수 있습니다
-->
<input type=hidden name=acceptmethod value="SKIN(ORIGINAL):HPP(1)"> <!--:OCB-->

<!--
상점 주문번호 : 무통장입금 예약(가상계좌 이체),전화결재(1588 Bill) 관련 필수필드로 반드시 상점의 주문번호를 페이지에 추가해야 합니다.
결제수단 중에 실시간 계좌이체 이용 시에는 주문 번호가 결제결과를 조회하는 기준 필드가 됩니다.
상점 주문번호는 최대 40 BYTE 길이입니다.
-->
<input type=hidden name=oid size=40 value="mall_use_order_id">

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
<input type=hidden name=quotainterest value="">
<input type=hidden name=paymethod value="">
<input type=hidden name=cardcode value="">
<input type=hidden name=cardquota value="">
<input type=hidden name=rbankcode value="">
<input type=hidden name=reqsign value="DONE">
<input type=hidden name=encrypted value="">
<input type=hidden name=sessionkey value="">
<input type=hidden name=uid value="">
<input type=hidden name=sid value="">
<input type=hidden name=version value=4000>
<input type=hidden name=clickcontrol value="">
<input type=hidden name=p_TID value="<%=request.getParameter("p_subjcd")%>">
<input type=hidden name=p_gubun value="<%=request.getParameter("p_gubun")%>">
<input type=hidden name=p_process value="<%=v_process%>">

</form>
</body>



