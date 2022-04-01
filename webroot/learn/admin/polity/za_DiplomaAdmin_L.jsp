<%
/**
 * file name : za_DiplomaAdmin_L.jsp
 * date      :
 * programmer:
 * function  : 수료증 관리 리스트 조회화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
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
    String ems_url = conf.getProperty("ems.url.value");

    String  ss_grcode			= box.getString("s_grcode");		//교육그룹
    String  ss_gyear			= box.getString("s_gyear");			//년도
    String  ss_grseq			= box.getString("s_grseq");			//교육차수
    String  ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_mastercd			= box.getString("s_mastercd");		//과정&코스
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_subjcourse		= box.getString("s_subjcourse");	//과정&코스
    String  ss_subjseq			= box.getString("s_subjseq");		//과정 차수
    String  ss_accountstart		= box.getString("s_accountstart");//환불시작일
    String  ss_accountend		= box.getString("s_accountend");	//환불종료일
    String  ss_accountstat		= box.getString("s_accountstat");	//환불상태

	
	String v_grtype = GetCodenm.get_grtype(box,ss_grcode);
	
    String  v_action    = box.getString("p_action");

	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
	String v_dispnum	= "";
    
    
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
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src= "/script/cresys_lib.js"></script>
<script language = "VBScript"   src= "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
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

	  document.form1.target = "_self";
	  document.form1.action='/servlet/controller.polity.DiplomaAdminServlet';
	  document.form1.p_process.value = 'selectList';    
	  document.form1.submit();
	}

	// 검색조건 체크
	function chkParam() {
	  if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '') {
		alert("교육그룹을 선택하세요.");
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

		document.form1.p_order.value = column;
		whenSelection("go");
	}

	// 회원정보
	function whenMemberInfo(userid) {
		window.self.name = "ProposeList";
		open_window("openMember","","100","100","600","260");
		document.form1.target = "openMember";
		document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
		document.form1.p_process.value = 'memberInfo';
		document.form1.submit();
		document.form1.target = window.self.name;
	}

	//수료증 출력
	function suRoyJeung(subj,year,subjseq,userid, kind){
		window.self.name = "SuryoJeung";
		//var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=601,height=850,scrollbars=no,resizable=NO');
		var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=yes,width=830,height=600,scrollbars=yes,resizable=NO');
		document.form1.target = "openSuryoJeungPrint";
		document.form1.action = "/servlet/controller.polity.DiplomaAdminServlet";
		document.form1.p_subj.value = subj;
		document.form1.p_grcode.value = document.form1.s_grcode.value;
		document.form1.p_year.value = year;
		document.form1.p_scsubjseq.value = subjseq;
		document.form1.p_userid.value = userid;
		document.form1.p_kind.value = kind;
		document.form1.p_process.value = "DiplomaPrint";
		document.form1.submit();

		
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
		
		document.form1.method = 'post';
		document.form1.target = 'mainFrame';

		document.form1.action='/servlet/controller.polity.DiplomaAdminServlet';
        document.form1.p_process.value = 'ExcelDown'; 
		document.form1.p_action.value = "go";

        document.form1.submit();	                
		
    }

	// 페이지이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.polity.DiplomaAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.p_action.value = "go";
         document.form1.submit();
    }

	// 페이지이동
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.polity.DiplomaAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.p_action.value = "go";
         document.form1.submit();
    }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.polity.DiplomaAdminServlet';
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
          <td><img src="/images/admin/polity/tit_service_01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_masternm"  value="">
    <input type="hidden" name="p_mastercd"  value="<%=ss_mastercd%>">
    <input type="hidden" name="p_action">
    <input type="hidden" name="p_order"  value="name">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type = "hidden" name = "p_pagesize"  value = "<%=v_pagesize%>">

    <input type="hidden" name="p_grcode" >
    <input type="hidden" name="p_seq" >
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subj" >
    <input type="hidden" name="p_scsubjseq" >
    <input type="hidden" name="p_kind" >
    <input type="hidden" name="p_userid" >
	
    <input type="hidden" name="p_chknum" >
    <input type="hidden" name="p_chkvalue" >

    <input type="hidden" name="p_returnurl"       value="/servlet/controller.polity.DiplomaAdminServlet">
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
                      <td align="left" valign="middle">
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
                        <font color="red">★</font>
                                                                 교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
                        onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
				                        연도 <kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
				                onChange="changes_grseq(s_grcode.value, this.value);" afterScript="Main_subjcourse" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
						    교육차수 <kocca_select:select name="s_grseq" sqlNum="course.subjseq"
						        param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
						        attr="" onChange="" afterScript="Main_subjcourse" selectedValue="<%= ss_grseq %>" isLoad="true" all="true" />
                      </td>
                    </tr>
					<tr>
                        <td height="5"></td>
                    </tr>
                    <tr>
                      <td >
						    대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
						        onChange="changes_middleclass(this.value);Main_subjcourse();" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
						    중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
						        onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
						    소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
						        onChange="whenSelection('go')" attr=" " selectedValue="0000" isLoad="true" all="true" />
								과정 <kocca_select:select name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9"
						                param="<%=ss_grcode%>" param4="<%=ss_upperclass%>" param5="<%=ss_middleclass%>" param6="<%=ss_lowerclass%>"
						                param7="<%=ss_grseq%>" param9=""
						                onChange="changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value)" attr=" " selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
						                차수 <kocca_select:select name="s_subjseq" sqlNum="subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4"
						                param="<%=ss_grcode%>" param2="<%=ss_grseq %>" param3="<%=ss_subjcourse%>" param4="<%=ss_gyear%>"
						                onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
                      </td>
                    </tr>
					<tr>
                        <td height="5" ></td>
                    </tr>
                    <tr>
						<td >
						<table cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td>
								<!-------------------- 조건검색 끝 ---------------------------->
                                
								<!--input type = "hidden" name="s_start">
								<input type = "hidden" name="s_end">
								<input type="hidden" name="p_step"  value="2"--></td>
								<td>
								<select name="p_search" >
                <option value='userid'     <% if (v_search.equals("userid")) out.println("selected"); %>>ID</option>
                <option value='name' <% if (v_search.equals("name")) out.println("selected"); %>>이름</option>
              </select>
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
								<%@ include file="/learn/admin/include/za_GoButton.jsp" %></div><!-- whenSelection('go') -->
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
        String v_ispropose          ="";
        String v_proposetype        ="";
        String v_isedutarget        ="";
        String v_educnt             ="";
        String v_mastercd           ="";
        String v_masternm           ="";
        
        if (!ss_subjcourse.equals("ALL")&&!ss_subjseq.equals("ALL")&&!ss_subjcourse.equals("")&&!ss_subjseq.equals("")&&v_action.equals("go")) {
        	System.out.println("==========================2========================");
        	//DataBox dbox = (DataBox)request.getAttribute("selectList");
			
        %>
        <%}%>

        <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td class=sub_title>수료증 관리</td>
            <td align="right" height="20" width="58">&nbsp;</td>
            <td align="right" height="20" width="70"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
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
          <!--FORM name="ff" method="post" action="/servlet/controller.polity.RefundmentAdminServlet"-->
            <input type="hidden" name="p_grcode"     value="<%=ss_grcode%>">
            <input type="hidden" name="p_gyear"      value="<%=ss_gyear%>">
            <input type="hidden" name="p_grseq"      value="<%=ss_grseq%>">
            <input type="hidden" name="p_grseqnm"    value="<%=ss_grseqnm%>">
            <input type="hidden" name="p_uclass"     value="<%=ss_upperclass%>">
            <input type="hidden" name="p_mclass"     value="<%=ss_middleclass%>">
            <input type="hidden" name="p_lclass"     value="<%=ss_lowerclass%>">
            <input type="hidden" name="p_subjcourse" value="<%=ss_subjcourse%>">
            <input type="hidden" name="p_subjseq"    value="<%=ss_subjseq%>">
            <input type="hidden" name="p_mastercd"   value="<%=ss_mastercd%>">
            <input type="hidden" name="p_masternm"   value="">
			<input type="hidden" name="emsName" value="<%=box.getSession("name")%>">
			<input type="hidden" name="emsEmail" value="<%=box.getSession("email")%>">
          <tr>
            <td colspan="10" class="table_top_line"></td>
          </tr>
          <tr>
			<td class="table_title"><b>No</b></td>
			<td class="table_title" width="10%"><b><a href="javascript:ordering('C.subjnm')" class="e">과목</a></b></td>
			<td class="table_title"><b>회원구분</b></td>
			<td class="table_title" width="10%"><b><a href="javascript:ordering('B.userid')" class="e">ID</a></b></td>
			<td class="table_title" width="5%"><b><a href="javascript:ordering('B.name')" class="e">성명</a></b></td>
			<td class="table_title" width="10%"><b><a href="javascript:ordering('C.subjseq')" class="e">차수</a></b></td>
			<td class="table_title"><b>구분</b></td>
			<td class="table_title"><b>교육기간</b></td>
			<td class="table_title"><b>수료여부</a></td>
			<td class="table_title"><b>수료증</a></td>
          </tr>

<%  
        if (v_action.equals("go")) {
        	
        ArrayList  list = (ArrayList)request.getAttribute("selectList");
        
        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

		String v_subj		= "";
		String v_subjnm		= "";
		String v_membergubun= "";
		String v_userid		= "";
		String v_uname		= "";
		String v_subjseq	= "";
		String v_scyear		= "";
		String v_edustart	= "";
		String v_eduend 	= "";
		String v_isonoff	= "";
		String v_isgradu	= "";
		
        for (i=0; i<list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

            v_finalString	= "";

			v_subj			= dbox.getString("d_subj");
			v_subjnm		= dbox.getString("d_subjnm");
			v_userid		= dbox.getString("d_userid");
			v_uname			= dbox.getString("d_name");
			v_subjseq		= dbox.getString("d_subjseq");
			v_scyear		= dbox.getString("d_year");
			v_edustart		= dbox.getString("d_edustart");
			v_eduend		= dbox.getString("d_eduend");
			v_isonoff		= dbox.getString("d_isonoff");
			v_isgradu		= dbox.getString("d_isgraduated");
			v_membergubun	= dbox.getString("d_membergubun");

			v_dispnum		= dbox.getString("d_dispnum");
			v_totalpage     = dbox.getInt("d_totalpage");
			v_rowcount      = dbox.getInt("d_rowcount");
			v_total_rowcount= dbox.getInt("d_totalrowcount");

			if(v_isonoff.equals("ON")){     v_isonoff="사이버";   }
            else                      {     v_isonoff="집합";     }

			if(v_isgradu.equals("Y")) 
			{
				v_isgradu = "수료";
				v_finalString = "<a href=\"javascript:suRoyJeung('" + v_subj + "','"+ v_scyear + "','" + v_subjseq + "','" + v_userid + "','1');\"><img src='/images/admin/button/btn_suryoprint.gif' border='0'></a>";
			}
			else 
			{
				v_isgradu = "미수료";
				v_finalString = "-";
			}

			if(v_membergubun.equals("C")) {v_membergubun = "기업";}
			if(v_membergubun.equals("U")) {v_membergubun = "대학";}
			else {v_membergubun = "개인";}


%>
            <tr class="table_02_1">
				<td class="table_02_1"><%=v_dispnum %></td>
                <td class="table_02_1"><%=v_subjnm %></td>
                <td class="table_02_1"><%=v_membergubun %></td>
                <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%=v_userid%></a></td>
                <td class="table_02_1"><%=v_uname  %></td>
                <td class="table_02_1"><%=v_subjseq %></td>
                <td class="table_02_1"><%=v_isonoff %></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_edustart,"yyyy-MM-dd")%> ~<br><%=FormatDate.getFormatDate(v_eduend,"yyyy-MM-dd")%></td>
                <td class="table_02_1"><%=v_isgradu %></td>
                <td class="table_02_1"><%=v_finalString %></td>
                
            </tr>
<%          //}
        }
		if (i == 0)
			{ %>
			<tr>
				<td align="center" bgcolor="#FFFFFF" height="100" colspan="10">등록된 내용이 없습니다.</td>
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
            <td align="center"><%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_total_rowcount) %></td>
          </tr>
        </table>
        <br>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right">◈<b> TOTAL : <%=v_total_rowcount %>명</b></td>
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