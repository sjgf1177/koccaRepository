<%
//**********************************************************
//  1. 제      목: 수강료결제 관리
//  2. 프로그램명 : za_SchoolfeeAdmin_L.jsp
//  3. 개      요: 행정서비스 - 수강료결제 관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.18
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.library.PageUtil" %>
<%@ page import = "com.credu.propose.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");

	String  v_process  = "selectList";

    String  ss_grcode			= box.getString("s_grcode");		//교육그룹
    String  ss_gyear			= box.getString("s_gyear");			//년도
    String  ss_grseq			= box.getString("s_grseq");			//교육차수
    String  ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_mastercd			= box.getString("s_mastercd");		//과정&코스
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_paystatus		= box.getString("s_paystatus");	    //결제상태
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
    String  ss_startappdate		= box.getString("s_startappdate");	//수강신청일 조회 시작
    String  ss_endappdate		= box.getString("s_endappdate");	//수강신청일 조회 끝
    String  ss_paymethod		= box.getString("s_paymethod");		//결제방법
    String  ss_membergubun		= box.getString("s_membergubun");	//결제방법
	
	String v_grtype = GetCodenm.get_grtype(box,ss_grcode);
	
    String  v_action    = box.getString("p_action");

	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
	String  v_dispnum	= "";
    
    
    int     v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
    int     v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

	int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
	int		v_total_rowcount = 0;

	if(v_pageno == 0)
	{
		v_pageno = 1;
	}

    String  v_ongo     = "whenSelection('go')";
	String  v_finalString = "";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language="JavaScript" src = "/script/hhi_lib.js"></script>
<script language="JavaScript">
$(document).ready(function(){
	$("#s_startappdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#s_endappdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#s_startdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#s_enddate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
	function MM_openBrWindow(theURL,winName,features) { //v2.0
	  window.open(theURL,winName,features);
	}

	//검색
	function whenSelection(p_action) {
	  document.form1.p_action.value = p_action;

	  if (p_action == 'go') {
		if (chkParam() == false) {
		  return;
		}
		top.ftop.setPam();
	  }

      document.form1.p_pageno.value = 1;
	  document.form1.action='/servlet/controller.polity.SchoolfeeAdminServlet';
	  document.form1.p_process.value = 'selectList';    
	  document.form1.submit();
	}

	// 검색조건 체크
	function chkParam() {
	  if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '') {
		alert("교육그룹을 선택하세요.");
		return false;
	  }
	  if (document.form1.s_gyear.value == 'ALL' || document.form1.s_gyear.value == '') {
	     alert("연도를 선택하세요.");
	     return false;
	  }
	  
	}

	// 정렬
	function ordering(column) {
		if (document.form1.p_orderType.value == " asc") {
			document.form1.p_orderType.value = " desc";
		} else {
			document.form1.p_orderType.value = " asc";
		}

		document.form1.p_orderColumn.value = column;
		whenSelection("go");
	}

	// 회원정보
	function whenMemberInfo(userid) {
		window.self.name = "SchoolfeeList";
		open_window("openMember","","100","100","600","260");
		document.form1.target = "openMember";
		document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
		document.form1.p_process.value = 'memberInfo';
		document.form1.submit();
		document.form1.target = window.self.name;
	}

	//-------------------------------
    // excel file 생성.
    //-------------------------------
    function whenExcel()
    {
        f = document.form1;
		
		if (chkParam() == false) {
		  return;
		}

		window.self.name = "SchoolfeeList";
		document.form1.method = 'post';
		document.form1.target = '_self';

		document.form1.action='/servlet/controller.polity.SchoolfeeAdminServlet';
        document.form1.p_process.value = 'ExcelDown'; 
		document.form1.p_action.value = "go";

        document.form1.submit();	                
		document.form1.target = window.self.name;
		
    }

	// 페이지이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.polity.SchoolfeeAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.p_action.value = "go";
         document.form1.submit();
    }

	// 페이지이동
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.polity.SchoolfeeAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.p_action.value = "go";
         document.form1.submit();
    }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.polity.SchoolfeeAdminServlet';
        document.form1.p_process.value = "selectList";
        document.form1.p_action.value = "go";
        document.form1.submit();
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

	//입과인원 조회
	function fnStudentList(p_tid) {
		window.self.name = "SchoolfeeList";
		open_window("openStudent","","100","100","620","600",false,false,false,true,true);
		document.form1.target = "openStudent";
		document.form1.p_tid.value = p_tid;
		document.form1.action='/servlet/controller.polity.SchoolfeeAdminServlet';
		document.form1.p_process.value = 'SearchStudentPage';
		document.form1.submit();
	    document.form1.target = window.self.name;
	}

	//결제세부정보 조회
	function fnSchoolfeeInfo(p_tid) {
		window.self.name = "SchoolfeeList";
		open_window("openSchoolfeeInfo","","100","100","600","360",false,false,false,false,true);
		document.form1.target = "openSchoolfeeInfo";
		document.form1.p_tid.value = p_tid;
		document.form1.action='/servlet/controller.polity.SchoolfeeAdminServlet';
		document.form1.p_process.value = 'SchoolfeeInfo';
		document.form1.submit();
	    document.form1.target = window.self.name;
	}

	//결제세부정보 조회
	function fnPayInfo(p_tid) {
		window.self.name = "SchoolfeeList";
		open_window("openPayInfo","","100","100","620","450",false,false,false,true,true);
		document.form1.target = "openPayInfo";
		document.form1.p_tid.value = p_tid;
		document.form1.action='/servlet/controller.polity.SchoolfeeAdminServlet';
		document.form1.p_process.value = 'paycancelPage';
		document.form1.submit();
	    document.form1.target = window.self.name;
	}

	//계산서 결제세부정보 조회
	function fnTaxBillInfo(p_tid) {
		window.self.name = "SchoolfeeList";
		open_window("openTaxBillInfo","","100","100","600","360",false,false,false,false,true);
		document.form1.target = "openTaxBillInfo";
		document.form1.p_tid.value = p_tid;
		document.form1.action='/servlet/controller.polity.SchoolfeeAdminServlet';
		document.form1.p_process.value = 'SchoolfeeInfo';
		document.form1.submit();
	    document.form1.target = window.self.name;
	}

	 function whenCancel(tid) {
		  var url = "/inicis/INIcancelform.jsp?p_tid="+tid;
		  windec = window.open(url, "INIcancel", "toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=600,height=400,top=0,left=0");
	 }
//-->
</script>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/portal/s.1_admin02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_action" value="<%= v_action %>">
    <input type="hidden" name="p_orderColumn"  value="">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type="hidden" name="p_pagesize"  value = "<%=v_pagesize%>">

    <input type="hidden" name="p_grcode"     value="<%=ss_grcode%>">
    <input type="hidden" name="p_gyear"      value="<%=ss_gyear%>">
    <input type="hidden" name="p_grseq"      value="<%=ss_grseq%>">
    <input type="hidden" name="p_grseqnm"    value="<%=ss_grseqnm%>">
    <input type="hidden" name="p_uclass"     value="<%=ss_upperclass%>">
    <input type="hidden" name="p_mclass"     value="<%=ss_middleclass%>">
    <input type="hidden" name="p_lclass"     value="<%=ss_lowerclass%>">

    <input type="hidden" name="p_userid" >
    <input type="hidden" name="p_tid" >

    <input type="hidden" name="p_returnurl"       value="/servlet/controller.polity.SchoolfeeAdminServlet">
    <input type="hidden" name="p_returnprocess"   value="selectList">


      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">

            <table cellspacing="2" cellpadding="2" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td colspan="2" align="left" valign="middle">
                      <script type="text/javascript">
						var startup = 0;
						function Main_s_subjseq(){
						    if (startup > 1)
						        changes_grseq(document.all.s_grcode.value, document.all.s_gyear.value);
						}
						function Main_subjcourse(){
						    if(startup > 1)
						        changes_subjcourse(document.all.s_grcode.value, document.all.s_upperclass.value, document.all.s_middleclass.value, document.all.s_lowerclass.value, document.all.s_grseq.value,'' );
						    startup++;
						}
						</script>
                        <!------------------- 조건검색 시작 ------------------------->
                        <font color="red">★</font> 교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
                        onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
                      </td>
                      <td colspan="2">
				                      <font color="red">★</font>  연도 <kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
				                onChange="changes_grseq(s_grcode.value, this.value);" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
						    교육차수 <kocca_select:select name="s_grseq" sqlNum="course.subjseq"
						        param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
						        attr=" style='width:130px;'" onChange="" selectedValue="<%= ss_grseq %>" isLoad="true" all="true" />
						    대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
						        onChange="changes_middleclass(this.value);Main_subjcourse();" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
						    중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
						        onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
						    소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
						        onChange="whenSelection('go')" attr=" " selectedValue="0000" isLoad="true" all="true" />
                      </td>
                    </tr>
					<tr>
                        <td height="5" colspan="4"></td>
                    </tr>
                    <tr>
						<td colspan="4">
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>
                                                          수강신청일&nbsp;<input name="s_startappdate" id="s_startappdate" value="<%=ss_startappdate%>" type="text" class="datepicker_input1" size="10">&nbsp;
		  				  			~&nbsp;<input name="s_endappdate" id="s_endappdate" value="<%=ss_endappdate%>" type="text" class="datepicker_input1" size="10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                          결제일&nbsp;<input name="s_startdate" id="s_startdate" value="<%=ss_startdate%>" type="text" class="datepicker_input1" size="10">&nbsp;
		  				  			~&nbsp;<input name="s_enddate" id="s_enddate" value="<%=ss_enddate%>" type="text" class="datepicker_input1" size="10">
                                </td>
                                <td width="300">
                                                          결제명검색<input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
								<%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
								</td>
							</tr>
						</table>
						</td>
                    </tr>				
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>		
      </table>
	  <br>

<!---------------------////////////////////////////// 과정/차수정보 ////////////////////////////////-------------------------->
        <%
        String  v_grcode       = "";
        String  v_tid          = "";
        String  v_userid       = "";
        String  v_usernm       = "";
        String  v_membergubun = "";
        String  v_codenm       = "";
        String  v_goodname     = "";
        String  v_inputdate    = "";
        double  v_price        = 0;
        String  v_resultcode   = "";
        String  v_paymethod    = "";
        String  v_paystatus    = "";
        String  v_buyername    = "";
        String  v_pgauthdate   = "";
        String  v_cancelyn     = "";
        double	v_total_price  = 0;
        String  v_cancel_btn   = "";
        String  v_usepoint	   = "";
%>

        <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td class=sub_title>수강료 결제 관리</td>
            <td align="right" height="20" width="58">&nbsp;</td>
            <td align="right" height="20" width="150">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
          </tr>
          <tr>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">		
          <tr>
            <td colspan="12" class="table_top_line"></td>
          </tr>
          <tr>
			<td class="table_title" width="5%"><b>No</b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.usernm')" class="e">성명</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.userid')" class="e">아이디</a></b></td>
			<td class="table_title"><b><kocca_select:select name="s_membergubun" sqlNum="membergubuncode"  param=" "
	onChange="whenSelection('go');" attr="회원구분" selectedValue="<%= ss_membergubun %>" isLoad="true" all="none" type="3" styleClass="table_title"/></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.goodname')" class="e">결제명</a></b></td>
			<td class="table_title" width="10%"><b><a href="javascript:ordering('inputdate')" class="e">수강신청일</a></b></td>
			<td class="table_title"><b><kocca_select:select name="s_paystatus" sqlNum="paystatus"  param=" "
	onChange="whenSelection('go');" attr="결제상태" selectedValue="<%= ss_paystatus %>" isLoad="true" all="none" type="3" styleClass="table_title"/></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.price')" class="e">결제금액</a>/사용포인트</b></td>
			<td class="table_title"><b><kocca_select:select name="s_paymethod" sqlNum="paymethod"  param=" "
	onChange="whenSelection('go');" attr="결제방법" selectedValue="<%= ss_paymethod %>" isLoad="true" all="none" type="3" styleClass="table_title"/></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.buyername')" class="e">결제자</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.pgauthdate')" class="e">결제일</a></b></td>
			<td class="table_title"><b>결제취소</b></td>
          </tr>

<%  
        if (v_action.equals("go")) {
        	
        ArrayList  list = (ArrayList)request.getAttribute("selectList");
        
        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

        for (i=0; i<list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

            v_finalString	= "";

            v_grcode      = dbox.getString("d_grcode");
            v_tid         = dbox.getString("d_tid");
            v_userid      = dbox.getString("d_userid");
            v_usernm      = dbox.getString("d_usernm");
            v_membergubun = dbox.getString("d_membergubun");
            v_codenm      = dbox.getString("d_codenm");
            v_goodname    = dbox.getString("d_goodname");
            v_inputdate   = dbox.getString("d_inputdate");
            v_price       = dbox.getDouble("d_price");
            v_resultcode  = dbox.getString("d_resultcode");
            v_paymethod   = dbox.getString("d_paymethod");
            v_paystatus   = dbox.getString("d_paystatus");
            v_buyername   = dbox.getString("d_buyername");
            v_pgauthdate  = dbox.getString("d_pgauthdate");
            v_cancelyn    = dbox.getString("d_cancelyn");
            v_usepoint    = dbox.getStringDefault("d_usepoint" ,"0");

			v_dispnum		= dbox.getString("d_dispnum");
			v_totalpage     = dbox.getInt("d_totalpage");
			v_rowcount      = dbox.getInt("d_rowcount");
			v_total_rowcount= dbox.getInt("d_totalrowcount");

			if ("Y".equals(v_paystatus) && (!"TaxBill".equals(v_paymethod) && (!"BankBook".equals(v_paymethod)))) {
				v_cancel_btn = "<a href=\"javascript:fnPayInfo('" + v_tid + "')\">[결제취소]</a>";
			} else {
				v_cancel_btn = "";
			}
			
			if ("Y".equals(v_paystatus)) {
				v_paystatus = "결제완료";
			} else if ("N".equals(v_paystatus)) {
				v_paystatus = "미결제";
			} else if ("R".equals(v_paystatus)) {
				v_paystatus = "환불완료";
			}
			
			if ("BankBook".equals(v_paymethod)) {
				v_paymethod = "무통장";
			} else if ("Card".equals(v_paymethod)) {
				v_paymethod = "신용카드(ISP)";
			} else if ("DirectBank".equals(v_paymethod)) {
				v_paymethod = "실시간계좌이체";
			} else if ("TaxBill".equals(v_paymethod)) {
				v_paymethod = "계산서";
			} else if ("VBank".equals(v_paymethod)) {
				v_paymethod = "무통장(가상계좌)";
			} else if ("VCard".equals(v_paymethod)) {
				v_paymethod = "신용카드(안심클릭)";
			} else if ("FreePay".equals(v_paymethod)) {
				v_paymethod = "무료결제";
			}
			
			v_total_price += v_price;
%>
            <tr class="table_02_1">
				<td class="table_02_1"><%= v_dispnum %></td>
                <td class="table_02_1"><%= v_usernm %></td>
                <td class="table_02_1"><%= v_userid %></td>
                <td class="table_02_1"><%= v_codenm %></td>
                <td class="table_02_2"><%= v_goodname %></td>
                <td class="table_02_1"><%= FormatDate.getFormatDate(v_inputdate,"yyyy/MM/dd") %></td>
<%
			if ("계산서".equals(v_paymethod)) {
%>
                <td class="table_02_1"><a href="javascript:fnTaxBillInfo('<%= v_tid %>')"><%= v_paystatus %></a></td>
<%
			} else {
%>
                <td class="table_02_1"><%= v_paystatus %></td>
<%
			}
%>
                <td class="table_02_1"><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %>/<%=v_usepoint %></td>
                <td class="table_02_1"><%= v_paymethod %></td>
                <td class="table_02_1"><%= v_buyername %></td>
                <td class="table_02_1"><%= FormatDate.getFormatDate(v_pgauthdate,"yyyy/MM/dd") %></td>
                <td class="table_02_1"><%= v_cancel_btn %></td>
            </tr>
<%          //}
        }
		if (i == 0)
			{ %>
			<tr>
				<td align="center" bgcolor="#FFFFFF" height="100" colspan="12">등록된 내용이 없습니다.</td>
			  </tr>
			<%}
    }

%>
            </FORM>
        </table>
        <!----------------- 과정기준조회 끝 ----------------->

        <%  if(i > 0){ %>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_total_rowcount, true) %></td>
          </tr>
        </table>
        <br>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right">◈<b> 총합 : <%= new java.text.DecimalFormat("###,###,##0").format(v_total_price) %>원</b></td>
          </tr>
        </table>
        <% } %>

        <br>

      </td>
  </tr>

</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>