<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_selCategory = box.getString("p_selCategory");
    String  v_selState = box.getString("p_selState");
    String  v_searchtext    = box.getString("p_searchtext");
    
    String  v_process   = box.getString("p_process");
    String  v_year      = box.getString("p_year");
    String  v_hsubjnm   = box.getString("p_hsubjnm");


    //DEFINED in relation to select START
    String  ss_year         = box.getString("s_year");                      //연도
    String  ss_subj         = box.getString("s_subjcode");                  //과정
    String  ss_grseq        = box.getString("s_grseq");                     //차수
	String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
	if( ss_year.equals("") ) {
		ss_year = Integer.toString((new java.util.Date().getYear()) + 1900);
	}
    String  ss_upperclass   = box.getStringDefault("s_upperclass", "S01");  //과정대분류
    String  ss_middleclass  = box.getStringDefault("s_middleclass", "ALL"); //과정중분류
    String  ss_lowerclass   = box.getStringDefault("s_lowerclass", "ALL");  //과정소분류
    String  ss_action       = box.getString("s_action");
    String  s_subjsearchkey = box.getString("s_subjsearchkey");
    String  v_gyear         = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
    //DEFINED in relation to select END

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    
    ArrayList list    = (ArrayList)request.getAttribute("selectList");       // 리스트
    
    String v_subj         = "";
    String v_subjnm       = "";
    String v_subjseq      = "";
    String v_userid       = "";
    String v_name         = "";
    String v_studentno    = "";
    String v_state        = "";
    String v_statenm      = "";
    String v_ldate        = "";
    String v_confirmdate  = "";
    
    String v_compnm       = "";
    String v_depart       = "";
    String v_type        = "";
    
    String v_email         = "";
    String v_employdate    = "";
    String v_employgubun   = "";
    String v_employgubunnm = "";

    
    
    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    
    int     v_totalrowcount =  0;
    int     v_dispnum   = 0;           // 총게시물수
    int     v_totalpage = 0;           // 게시물총페이지수

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/cresys_lib.js'></script>
<script type='text/javascript' src='/script/portal/common.js'></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

	function go(index) {
	    document.form1.p_pageno.value = index;
	    document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
	    document.form1.p_process.value = "selectList";
	    document.form1.submit();
	}
	function goPage(pageNum) {
	    document.form1.p_pageno.value = pageNum;
	    document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
	    document.form1.p_process.value = "selectList";
	    document.form1.submit();
	}
    function whenSelection(p_action) {
        document.form1.p_pageno.value = "1";
        document.form1.p_action.value = "go";
        document.form1.p_process.value = "selectList";
        document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
        document.form1.submit();
    }

    // 선택공지등록
    function insert() {
        document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
        document.form1.p_seq.value      = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }
    
        document.form1.p_orderColumn.value = column;
        whenSelection("go");
    }

function goSubjectListPage() {
  
  document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
  document.form1.p_process.value = "selectAll";
  document.form1.p_action.value = "go";
  document.form1.submit();
}

function excelDown() {
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.off.OffStudentJobAdminServlet';
    document.form1.p_process.value = 'excelDown';
    document.form1.submit();
}
function pagesize(pageSize) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_pagesize.value = pageSize;
    document.form1.action = '/servlet/controller.off.OffStudentJobAdminServlet';
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}
function viewStuStatus(subj, year, subjseq, userid){
    
    window.open("about:blank", "listPagePerPerson", "top=0, left=0, width=600, height=470, status=no, scrollbars=yes");
    document.form1.target = "listPagePerPerson";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_userid.value = userid;
    document.form1.action = '/servlet/controller.off.OffStudentJobAdminServlet';
    document.form1.p_process.value = "selectListPerPerson";
    document.form1.submit();

    document.form1.target="_self";   
}

function viewPropose(uid, subj, subjseq, year) {
	window.self.name = "ProposeList";
	open_window("openMember","","100","100","1024","700",'','','','yes');
	document.form2.target = "openMember";
	
	document.form2.p_userid.value = uid;
	document.form2.p_subj.value = subj;
	document.form2.p_subjseq.value = subjseq;
	document.form2.p_year.value = year;

	document.form2.submit();
}
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" >
    <input type="hidden" name="p_action">
    <input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>">
    <input type="hidden" name="p_pageno"    value="<%=v_pageno %>">
    <input type="hidden" name="p_subj"      id="p_subj"         value="">
    <input type="hidden" name="p_subjnm"    value="">
    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_year"      id="p_year"         value="">
    <input type="hidden" name="p_subjseq"   id="p_subjseq"      value="">
    <input type="hidden" name="p_userid"    id="p_userid"      value="">
    
    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
         <td><img src="/images/admin/portal/s.1_off09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">

          
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
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
											<td rowspan=2 width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') --></td>
										</tr>
										<tr>
											<td>
												<%--과정년도 <kocca_select:select name="s_subjyear" sqlNum="off.year.subj"  param=" "--%>
													<%--onChange="fnSearchBox()" attr=" " selectedValue="<%= ss_subjyear %>" isLoad="true" all="true" />--%>
												과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj"
													param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_year %>"
													onChange="subjSelected();changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
												차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
													onChange="" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
												
                                            </td>
                                        </tr>
                                        
                                        <tr>
											<td>
												
												과정검색 <input type="text" name="s_subjsearchkey" size="17" onkeypress="javascript:fnKeyPressEnter(event, 'whenSelection');" value="<%=s_subjsearchkey%>">
                                                ID 또는 이름&nbsp;<input name="p_searchtext" type="text" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'whenSelection');">
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
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <!----------------- 추가 버튼 시작 ----------------->
      <table id="insertButton" cellpadding="0" cellspacing="0" class="table1" >
        <tr>
          <td align="right" height="20">
            <a href="javascript:excelDown()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 추가 버튼 끝 ----------------->
      <!----------------- 과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
            <td rowspan="2" class="table_title" width="5%" height="25"><b>NO</b></td>
            <td rowspan="2" class="table_title" width="25%"><a href="javascript:whenOrder('B.SUBJNM')" class="e">과정명</a></td>
            <td rowspan="2" class="table_title" width="5%">차수</td>
            <td rowspan="2" class="table_title" width="8%"><a href="javascript:whenOrder('B.SUBJNM')" class="e">성명</a></td>
            <td rowspan="2" class="table_title" width="10%">학번</td>
            <%-- <td rowspan="2" class="table_title" width="7%">
                    <kocca_select:select                                       
                    name="p_selState"                                         
                    sqlNum="0001"                                    
                    param="0089"             
                    onChange="whenSelection('go');"                         
                    attr= "학적"                                        
                    selectedValue="<%=v_selState%>"                         
                    isLoad="true"                                           
                    type="3"                                                
                    styleClass=""                                
                    all="true" />   
            </td> --%>
            <td colspan="4" class="table_title" width="15%">취업현황</td>
            <td rowspan="2" class="table_title" width="10%">최종수정일</td>
            <td rowspan="2" class="table_title" width="5%">등록</td>
        </tr>
        <tr>
            <td class="table_title" width="5%">날짜</td>
            <td class="table_title" width="8%">업체명</td>
            <td class="table_title" width="8%">부서명</td>
            <td class="table_title" width="5%">형태</td>
          </tr>
        <%
        if(list !=null && list.size() > 0){
            for(int i = 0 ; i < list.size() ; i++) {
                DataBox dbox   = (DataBox)list.get(i);
                v_subj         = dbox.getString("d_subj");       
                v_subjnm       = dbox.getString("d_subjnm");     
                v_subjseq      = dbox.getString("d_subjseq");
                v_userid       = dbox.getString("d_userid");
                v_name         = dbox.getString("d_name");       
                v_studentno    = dbox.getString("d_studentno");  
                v_state        = dbox.getString("d_stustatus");      
                v_statenm      = dbox.getString("d_statenm");    
                v_ldate        = dbox.getString("d_lastdate");      
                v_confirmdate  = dbox.getString("d_confirmdate");
                v_year         = dbox.getString("d_year");
               
          		v_compnm        = dbox.getString("d_compnm");    
          		v_depart		= dbox.getString("d_depart"); 
          		v_type			= dbox.getString("d_type");  
          		
          		v_email         = dbox.getString("d_email");       
          		v_employdate    = dbox.getString("d_employdate");  
                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
        %>
        <tr>
          <td class="table_01" height="25"><%= v_dispnum %></td>
          <td class="table_02_1"><%= v_subjnm %> </td>
          <td class="table_02_1"><%= v_subjseq.equals("") ? "" : Integer.parseInt(v_subjseq)+"기" %> </td>
          <td class="table_02_1">
          <a href="javascript:viewPropose('<%=v_userid%>','<%=v_subj %>','<%=v_subjseq%>','<%=v_year%>');"><%= v_name %></a>
           </td>
          <td class="table_02_1"><%= v_studentno %> </td>
         <%--  <td class="table_02_1"><%= v_statenm %></td> --%>
          <td class="table_02_1"><%= v_employdate %> </td>
          <td class="table_02_1"><%= v_compnm %> </td> 
          <td class="table_02_1"><%= v_depart %> </td> 
          <td class="table_02_1"><%= v_type %> </td> 
          <td class="table_02_1"><%=FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd")%> </td>  
          <td class="table_02_1"><a href="javascript:viewStuStatus('<%=v_subj %>','<%=v_year %>','<%=v_subjseq %>','<%=v_userid %>');">수정</a></td>       
        </tr>
        <%        
            }
        } else {
        %>
        <tr><td colspan=11 class="table_02_1">등록된 과정이 없습니다.</td></tr>
        <% }  %>  
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
      <!----------------- 저장, 삭제, 리스트 버튼 시작 ----------------->
      <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" valign="absmiddle">
            <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 10, v_pagesize, v_totalrowcount) %>
          </td>
        </tr>
      </table>
      <!----------------- 저장, 삭제, 리스트 버튼 끝 ----------------->
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
<form name="form2" method="post" action="/servlet/controller.off.OffApprovalAdminServlet">
	<input type="hidden" name="p_userid" value=""/>
	<input type="hidden" name="p_subj" value=""/>
	<input type="hidden" name="p_subjseq" value=""/>
	<input type="hidden" name="p_year" value=""/>
	<input type="hidden" name="p_process" value="viewPropose"/>
</form>
</body>
</html>