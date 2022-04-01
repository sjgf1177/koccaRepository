<%
//**********************************************************
//  1. 제      목: OffLine 과정 조회화면
//  2. 프로그램명: za_OffLineSubject_L.jsp
//  3. 개      요: OffLine과정 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 하경태 2005.12.17
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %> 
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    //DEFINED in relation to select START
    String  ss_grcode		= box.getString("s_grcode");			//교육그룹
    String  ss_gyear		= box.getString("s_gyear");				//년도
    String  ss_upperclass	= box.getString("s_upperclass");		//과정대분류
    String  ss_middleclass	= box.getString("s_middleclass");		//과정중분류
    String  ss_lowerclass	= box.getString("s_lowerclass");		//과정소분류
    String  ss_subjcourse	= box.getString("s_subjcourse");		//과정&코스
    String  ss_subjseq		= box.getString("s_subjseq");			//과정 차수
    String  ss_action		= box.getString("s_action");			//과정 차수
    String  ss_subjgubun    = box.getString("s_subjgubun");
    //DEFINED in relation to select END
   	
	
    String  v_process = box.getString("p_process");
    int  v_pageno         = box.getInt("p_pageno");
    if (v_pageno == 0) {
        v_pageno = 1;
    }
    
	ArrayList list      = null;
    if(ss_action.equals("go")){    //go button 선택시만 list 출력 -> 막아놓음
        list = (ArrayList)request.getAttribute("OffLineSubjectList");
    } 

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

	String v_grcode = box.getStringDefault("p_grcode","G01"); 

%>
<html>
<head>
<title>과정관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<SCRIPT LANGUAGE="JavaScript">
<!--


//임시 등록 페이지로
function InsertSubjPage()
{
	document.form1.p_process.value = 'insertPage';
    document.form1.submit();
}

//과정정보 수정
function UpdateSubjPage(subj, isonoff) {
	document.form1.p_process.value = 'updatePage';
	document.form1.p_seq.value  = subj;
	document.form1.p_subj.value  = subj;
	document.form1.submit();
}

// 셀렉트 선택후 검색
function whenSelection(ss_action) 
{
	document.form1.s_action.value = ss_action;
	document.form1.p_process.value = 'listPage';
	document.form1.submit();

}

//정렬
function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.s_action.value = "go";
    document.form1.p_process.value = 'listPage';   
    document.form1.p_orderColumn.value = column;
    
    document.form1.submit();
}

-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/tit_offlineopen.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <form name="form1" method="post" action="/servlet/controller.course.OffLineSubjectServlet">
			<input type="hidden" name="p_process"	value="<%=v_process%>">		  
			<input type='hidden' name='p_pageno'	value="<%=v_pageno%>">
			<input type="hidden" name="p_grcode"    value="<%=v_grcode%>">
			<input type="hidden" name="s_action"	value="<%=ss_action%>">
			<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
			<input type="hidden" name="p_subjnm"	value="">
			<input type="hidden" name="p_subj"		value="">
			<input type="hidden" name="p_orderColumn">
			<input type="hidden" name="p_seq">          
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
                         <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
							<tr>
								<td colspan="2">
								<font color="red">★</font>과정 분류
									<select name="s_subjgubun">
									<% 
										int j = 0;
										String v_gubuncode = "";
										String v_gubuncodenm = "";
										
										ArrayList list2 = (ArrayList)OffLineSubjectBean.getOffGubun(box);
										%>
										<!--option value="ALL">== 선택 ==</option-->
		<%
										for ( j=0; j < list2.size(); j++) 
										{
											DataBox dbox2 = (DataBox)list2.get(j);

											v_gubuncode		= dbox2.getString("d_code");
											v_gubuncodenm	= dbox2.getString("d_codenm");									
										%>
											<option value="<%=v_gubuncode%>" <% if(v_gubuncode.equals(ss_subjgubun)) { %> selected <%}%>><%=v_gubuncodenm%></option>
										<%
										}
									%>
								</select >
								</td>
								<td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="right" height="20"><a href="javascript:InsertSubjPage()"><img src="/images/admin/button/btn_addplus.gif" border="0"></a> </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 추가 버튼 끝 ----------------->
      <!----------------- 과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="5%"><b>NO</b></td>
          <td class="table_title" width="15%"><a href="javascript:whenOrder('subjgubun')" class="e">분류</a></td>
          <td class="table_title" width="15%"><a href="javascript:whenOrder('subjnm')" class="e">강의명</a></td>
          <td class="table_title" width="10%"><a href="javascript:whenOrder('tname')" class="e">담당교수</a></td>
          <td class="table_title" width="10%"><a href="javascript:whenOrder('dday')" class="e">일시</a></td>
          <td class="table_title" width="10%">시간</td>
          <td class="table_title" width="30%">장소</td>
          <% //} %>
        </tr>
<%  if(ss_action.equals("go")){    //go button 선택시만 list 출력 ->잠시 막아놓음

	int v_seq			= 0;
	int i				= 0;
	String v_subjgubun	= "";
	String v_subjnm		= "";
	String v_subjseq	= "";
	String v_tname		= "";
	String v_dday		= "";
	String v_starttime	= "";
	String v_endtime	= "";
	String v_place		= "";	

    SubjectData data = null;
    for ( i=0; i<list.size(); i++) 
	{
        DataBox dbox = (DataBox)list.get(i);

		v_seq		= dbox.getInt("d_seq");
		v_subjgubun = dbox.getString("d_gubunnm");
		v_subjnm	= dbox.getString("d_subjnm");
		v_subjseq	= dbox.getString("d_subjseq");
		v_tname		= dbox.getString("d_tname");
		v_dday		= dbox.getString("d_dday");
		v_starttime = dbox.getString("d_starttime");
		v_endtime	= dbox.getString("d_endtime");
		v_place		= dbox.getString("d_place");

		//날짜 처리
		if(v_dday.equals("")){v_dday = "미정";}
		else{v_dday     = FormatDate.getFormatDate(v_dday,"yyyy/MM/dd");}

%>
        <tr>
          <td class="table_01"><%=String.valueOf(i+1)%></td>
          <td class="table_02_1"><%=v_subjgubun%></td>
          <td class="table_02_1"><a href="javascript:UpdateSubjPage('<%=v_seq%>')" class='b'><%=v_subjnm%></a></td>
          <td class="table_02_1"><%=v_tname%></a></td>
          <td class="table_02_1"><%=v_dday%></td>
          <td class="table_02_1"><%=v_starttime%> ~ <%=v_endtime%></td>
          <td class="table_02_1"><%=v_place%></td>
        </tr> 
<%  } %>

      <% if (i==0) { %>
           <tr><td colspan="8"class="table_02_1">등록된 과정이 없습니다.</td></tr>
      <%  } %>
<%} %>
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
