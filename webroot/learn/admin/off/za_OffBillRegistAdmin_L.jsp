<%
//**********************************************************
//  1. 제      목: 수강료 납부 등록
//  2. 프로그램명 : za_OffBillRegistAdmin_L.jsp
//  3. 개      요: 행정서비스 - 수강료납부등록
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

    String  ss_year			    = box.getString("s_year");			//년도
	String  ss_subj             = box.getString("s_subjcode");	  	//과정
	String  ss_subjseq          = box.getString("s_subjseq");	  	//과정
	String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
	if( ss_year.equals("") ) {
		ss_year = Integer.toString((new java.util.Date().getYear()) + 1900);
	}
    String  ss_mastercd			= box.getString("s_mastercd");		//과정&코스
    String  ss_upperclass		= box.getStringDefault("s_upperclass", "S01");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
	String  s_subjsearchkey     = box.getString("s_subjsearchkey");
	
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
	  document.form1.action='/servlet/controller.off.OffBillRegistAdminServlet';
	  document.form1.p_process.value = 'selectList';    
	  document.form1.submit();
	}

	// 검색조건 체크
	function chkParam() {
	//	if (document.form1.s_subjseq.value == 'ALL' || document.form1.s_subjseq.value == '' || document.form1.s_subjseq.value == ' ') {
	//		alert("차수를 선택하세요.");
	//		return false;
	//	}
	}

	function subjSearch() {
		changes_subjcode($("#s_year").val(),$("#s_upperclass").val(),$("#s_middleclass").val(),$("#s_lowerclass").val());
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
		window.self.name = "OffBillList";
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

		window.self.name = "OffBillList";
		document.form1.method = 'post';
		document.form1.target = '_self';

		document.form1.action='/servlet/controller.off.OffBillRegistAdminServlet';
        document.form1.p_process.value = 'ExcelDown'; 
		document.form1.p_action.value = "go";

        document.form1.submit();
		document.form1.target = window.self.name;	                
		
    }

	// 페이지이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.off.OffBillRegistAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.p_action.value = "go";
         document.form1.submit();
    }

	// 페이지이동
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.off.OffBillRegistAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.p_action.value = "go";
         document.form1.submit();
    }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.off.OffBillRegistAdminServlet';
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

	// 수강료납부 등록
	function whenOffBillInsert() {

		if (document.form1.s_subjseq.value == 'ALL' || document.form1.s_subjseq.value == '' || document.form1.s_subjseq.value == ' ') {
			alert("차수를 선택하세요.");
			return;
		}

		if (document.form1.p_action.value != 'go') {
		     alert("검색버튼을 먼저 클릭해주세요.");
		     return;
		}
		
		window.self.name = "OffBillList";
		open_window("openOffBillInsert","","100","100","620","450",false,false,false,true,true);
		document.form1.target = "openOffBillInsert";
		document.form1.action = "/servlet/controller.off.OffBillRegistAdminServlet";
		document.form1.p_process.value = 'offbillInsertPage';
		document.form1.submit();
		document.form1.target = window.self.name;
	}
	
	//입과인원 조회
	function fnStudentList(p_tid) {
		window.self.name = "OffBillList";
		open_window("openStudent","","100","100","620","600",false,false,false,true,true);
		document.form1.target = "openStudent";
		document.form1.p_tid.value = p_tid;
		document.form1.action='/servlet/controller.off.OffBillRegistAdminServlet';
		document.form1.p_process.value = 'SearchStudentPage';
		document.form1.submit();
		document.form1.target = window.self.name;
	}

	//결제세부정보 수정페이지
	function fnOffBillRegistInfo(p_subj,p_year,p_subjseq,p_seq) {
		window.self.name = "OffBillList";
		open_window("openOffBillInfo","","100","100","600","520",false,false,false,false,true);
		document.form1.target = "openOffBillInfo";
		document.form1.p_subj.value = p_subj;
		document.form1.p_year.value = p_year;
		document.form1.p_subjseq.value = p_subjseq;
		document.form1.p_seq.value = p_seq;
		document.form1.action='/servlet/controller.off.OffBillRegistAdminServlet';
		document.form1.p_process.value = 'offbillUpdatePage';
		document.form1.submit();
		document.form1.target = window.self.name;
	}

	//결제세부정보 조회
	function fnPayInfo(p_tid) {
		window.self.name = "OffBillList";
		open_window("openPayInfo","","100","100","620","450",false,false,false,true,true);
		document.form1.target = "openPayInfo";
		document.form1.p_tid.value = p_tid;
		document.form1.action='/servlet/controller.off.OffBillRegistAdminServlet';
		document.form1.p_process.value = 'paycancelPage';
		document.form1.submit();
		document.form1.target = window.self.name;
	}

	 function whenCancel(tid) {
		  var url = "/inicis/INIcancelform.jsp?p_tid="+tid;
		  windec = window.open(url, "INIcancel", "toolbar=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=600,height=400,top=0,left=0");
	 }

	function actionclear() {
		document.form1.p_action.value  = "";
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
          <td><img src="/images/admin/portal/s.1_32.gif" ></td>
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

    <input type="hidden" name="p_year"          value="<%=ss_year%>">
    <input type="hidden" name="p_subj"          value="<%=ss_subj%>">
    <input type="hidden" name="p_subjseq"       value="<%=ss_subjseq%>">
    <input type="hidden" name="p_uclass"        value="<%=ss_upperclass%>">
    <input type="hidden" name="p_mclass"        value="<%=ss_middleclass%>">
    <input type="hidden" name="p_lclass"        value="<%=ss_lowerclass%>">
    <input type="hidden" name="p_startdate"     value="<%=ss_startdate%>">
    <input type="hidden" name="p_enddate"       value="<%=ss_enddate%>">
    <input type="hidden" name="p_subjsearchkey" value="<%=s_subjsearchkey%>">
    <input type="hidden" name="p_seq"           value="">

    <input type="hidden" name="p_userid" >
    <input type="hidden" name="p_tid" >

    <input type="hidden" name="p_returnurl"       value="/servlet/controller.off.OffBillRegistAdminServlet">
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
					  <td>
						<!------------------- 조건검색 시작 ------------------------->
						 <table cellspacing="0" cellpadding="0" width="99%">
							<tr>
								<td>
									<table border="0" cellspacing="0" cellpadding="0" width="99%">
										<tr>
											<td>
											<script type="text/javascript">
											var isFirst = 0;
											function fnSearchBox() {
												if(isFirst>1) 
												changes_subjcode(
														$("#s_year").val(),
														$("#s_upperclass").val(),
														$("#s_middleclass").val(),
														$("#s_lowerclass").val(),
														$("#s_year").val()
														);
												isFirst++;
											}
											function fnSearchBoxs_middleclass() {
												if(isFirst>1) 
												changes_lowerclass($("#s_upperclass").val(), $("#s_middleclass").val());
												isFirst++;
											}
											</script>
												연도 <kocca_select:select name="s_year" sqlNum="off.year"  param=" "
													onChange="fnSearchBox();" attr=" " selectedValue="<%= ss_year %>" isLoad="true" all="none" />
												대분류 <kocca_select:select name="s_upperclass" sqlNum="off.0002"  param=" "
													onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
												중분류 <kocca_select:select name="s_middleclass" sqlNum="off.0003"  param="<%= ss_upperclass %>"
													onChange="changes_lowerclass(s_upperclass.value, this.value);"
													afterScript="fnSearchBoxs_middleclass"
													attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
												소분류 <kocca_select:select name="s_lowerclass" sqlNum="off.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
													afterScript="fnSearchBox"
													onChange="fnSearchBox()" attr=" " selectedValue="<%=ss_lowerclass %>" isLoad="true" all="true" />
											</td>
											<td rowspan=2 align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') --></td>
										</tr>
										<tr>
											<td>
												<%--과정년도 <kocca_select:select name="s_subjyear" sqlNum="off.year.subj"  param=" "--%>
													<%--onChange="fnSearchBox()" attr=" " selectedValue="<%= ss_subjyear %>" isLoad="true" all="true" />--%>
												과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj.terms"
													param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_year %>"
													onChange="changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
												차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
													onChange="" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
												과정검색 <input type="text" name="s_subjsearchkey" size="17" onkeypress="if(event.keyCode=='13') whenSelection('go');" value="<%=s_subjsearchkey%>">
												&nbsp;결제일&nbsp;<input name="s_startdate" id="s_startdate" value="<%=ss_startdate%>" type="text" class="datepicker_input1" size="10">&nbsp;
		  				  			~&nbsp;<input name="s_enddate" id="s_enddate" value="<%=ss_enddate%>" type="text" class="datepicker_input1" size="10">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<!-------------------- 조건검색 끝 ---------------------------->
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
    	String  v_subj        = "";
        String  v_year        = "";
    	String  v_subjnm      = "";
    	String  v_subjseq     = "";
    	String  v_seq         = "";
    	String  v_billreqnm   = "";
    	double  v_biyong      = 0;
    	double  v_realbiyong  = 0;
    	String  v_billbegindt = "";
    	String  v_billenddt   = "";
    	int	    v_usercnt     = 0;

%>

        <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td class=sub_title>수강료 납부 등록</td>
            <td align="right" height="20" width="58">&nbsp;</td>
            <td align="right" height="20" width="150">
            <a href="javascript:whenOffBillInsert()" class="c"><img src="/images/admin/button/btn_add.gif" border="0"></a>
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
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr>
          	<td class="table_title" width="5%"><b>No</b></td>
			<td class="table_title"><b><a href="javascript:ordering('b.subjnm')" class="e">과정명</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('b.subjseq')" class="e">차수</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.billreqnm')" class="e">결제명</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.biyong')" class="e">수강료</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.realbiyong')" class="e">실결제액</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('a.billbegindt')" class="e">납부기간</a></b></td>
			<td class="table_title"><b><a href="javascript:ordering('usercnt')" class="e">납부대상자수</a></b></td>
          </tr>

<%  
        if (v_action.equals("go")) {
        	
        ArrayList  list = (ArrayList)request.getAttribute("selectList");
        
        for (i=0; i<list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

            v_finalString	= "";

            v_subj        = dbox.getString("d_subj");
            v_year        = dbox.getString("d_year");
            v_subjnm      = dbox.getString("d_subjnm");
            v_subjseq     = dbox.getString("d_subjseq");
            v_seq         = dbox.getString("d_seq");
            v_billreqnm   = dbox.getString("d_billreqnm");
            v_biyong      = dbox.getDouble("d_biyong");
            v_realbiyong  = dbox.getDouble("d_realbiyong");
            v_billbegindt = dbox.getString("d_billbegindt");
            v_billenddt   = dbox.getString("d_billenddt");
            v_usercnt     = dbox.getInt("d_usercnt");

			v_dispnum		= dbox.getString("d_dispnum");
			v_totalpage     = dbox.getInt("d_totalpage");
			v_rowcount      = dbox.getInt("d_rowcount");
			v_total_rowcount= dbox.getInt("d_totalrowcount");

%>
            <tr class="table_02_1">
				<td class="table_02_1"><%= v_dispnum %></td>
                <td class="table_02_2"><%= v_subjnm %></td>
                <td class="table_02_1"><%= StringManager.cutZero(v_subjseq) %>기</td>
                <td class="table_02_2"><a href="javascript:fnOffBillRegistInfo('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')"><%= v_billreqnm %></a></td>
                <td class="table_02_1"><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %></td>
                <td class="table_02_1"><%= new java.text.DecimalFormat("###,###,##0").format(v_realbiyong) %></td>
                <td class="table_02_1"><%= FormatDate.getFormatDate(v_billbegindt,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_billenddt,"yyyy/MM/dd") %></td>
                <td class="table_02_1"><%= v_usercnt %></td>
            </tr>
<%          //}
        }
		if (i == 0)
			{ %>
			<tr>
				<td align="center" bgcolor="#FFFFFF" height="100" colspan="8">등록된 내용이 없습니다.</td>
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
        <% } %>

        <br>

      </td>
  </tr>

</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>