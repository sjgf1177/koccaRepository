<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개      요: 과정조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","04");                       // 메뉴 아이디 세팅

    String  v_process     = box.getString("p_process");
    String  v_select      = box.getStringDefault("p_select","ON");
    String  v_gubun       = box.getStringDefault("p_gubun","1");
	String  v_grcode      = box.getStringDefault("p_grcode","N000001");
	
	String v_tabnum		= box.getStringDefault("p_tabnum","1");

    String v_code       = "";
    String v_codenm     = "";

	int v_seq = 0;

    String v_subj           = "";
    String v_subjseq        = "";
    String v_subjnm         = "";

	String v_gubunNm	= "";
	String v_tname		= "";
	String v_dday		= "";
	String v_starttime	= "";
	String v_endtime	= "";
	String v_place		= "";

    ArrayList list1      = null;
    ArrayList list2      = null;

	list1 = (ArrayList)KProposeCourseBean.OffLineSelectSubjectList(box);	// 신청 가능한 과목
	list2 = (ArrayList)KProposeCourseBean.OffLineApplySelectSubjectList(box);	// 신청한 과목

%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
	// 오프라인 수강 신청
	function applyOffLine(seq)
	{
		var frm = document.form1;
		if(!confirm("해당 과정을 수강신청하시겠습니까?")){
		 return;
	   }

	   frm.p_seq.value = seq;
	   frm.action = "/servlet/controller.propose.KProposeCourseServlet";
	   frm.p_process.value = "OffLineApply";
	   frm.target = "_self";
	   frm.submit();
	}

	// 오프라인 수강신청 최소
	function applyOffLineCancel( seq)
	{
		var frm = document.form1;
		if(!confirm("수강 신청을 취소하시겠습니까?")){
		 return;
	   }

	   frm.p_seq.value = seq;
	   frm.action = "/servlet/controller.propose.KProposeCourseServlet";
	   frm.p_process.value = "OffLineCancel";
	   frm.target = "_self";
	   frm.submit();
	}

	// 상세보기
	function applyOffLineView(seq)
	{
		var frm = document.form1;

		frm.p_seq.value = seq;
		frm.action = "/servlet/controller.propose.KProposeCourseServlet";
		frm.p_process.value = "OffLineView";
		frm.target = "_self";
		frm.submit();
	}

//-->
</SCRIPT>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process' >	
    <input type='hidden' name='p_seq' >	
    <input type='hidden' name='p_userid'>
    <input type='hidden' name='p_grcode' value='N000001'>

          <!-- center start -->  
		  <!-- title -->
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td  class="location" > HOME > 수강신청 &gt; 오프라인강좌 신청/확인</td>
		  </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td><img src="/images/user/kocca/apply/<%=tem_subimgpath%>/tit_offline.gif" ></td>
		  </tr>
		  <tr> 
			<td height="20"></td>
		  </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td height="12" valign="top"><img src="/images/user/kocca/apply/text_offline.gif"></td>
		  </tr>
		  <tr > 
			<td height="1" background="/images/user/kocca/apply/dot_bg_9.gif"></td>
		  </tr>
		  <tr > 
			<td height="16" ></td>
		  </tr>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
			<td><img src="/images/user/kocca/apply/sst_off_apply.gif"></td>
		  </tr>
		  <tr> 
			<td height="15"></td>
		  </tr>
		</table>
		<!-- 신청한 오프라인강좌 -->
		<table width="680" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		  <tr> 
			<td height="3" colspan="8" class="linecolor_app"></td>
		  </tr>
		  <tr> 
			<td width="16" class="tbl_otit">No</td>
			<td width="52" class="tbl_otit2">구분</td>
			<td width="141" class="tbl_otit">강의명</td>
			<td width="55" class="tbl_otit2">담당교수</td>
			<td width="78"  class="tbl_otit">일시</td>
			<td width="69"  class="tbl_otit2">시간</td>
			<td width="130"  class="tbl_otit">장소</td>
			<td width="71" class="tbl_otit2">상태</td>
		  </tr>
		  <tr> 
			<td height="5" colspan="8" ></td>
		  </tr>
		  <%
	int i = 0;
	String v_status	= "";
	String v_statusString = "";
	for(i = 0; i < list2.size(); i++) 
	{
		DataBox dbox2 = (DataBox)list2.get(i);

		v_gubunNm	= dbox2.getString("d_gubunnm");
		v_subjnm	= dbox2.getString("d_subjnm");
		v_tname		= dbox2.getString("d_tname");
		v_dday		= dbox2.getString("d_dday");
		v_starttime	= dbox2.getString("d_starttime");
		v_endtime	= dbox2.getString("d_endtime");
		v_subjnm	= dbox2.getString("d_subjnm");
		v_place		= dbox2.getString("d_place");
		v_seq		= dbox2.getInt("d_seq");
		v_status	= dbox2.getString("d_status");

		//날짜 처리
		if(v_dday.equals("")){v_dday = "미정";}
		else{v_dday     = FormatDate.getFormatDate(v_dday,"yyyy/MM/dd");}


%>
		  <tr> 
			<td class="tbl_grc"><%= String.valueOf(i+1) %></td>
			<td class="tbl_grc"><%= v_gubunNm %></td>
			<td class="tbl_bleft"><a href="javascript:applyOffLineView('<%=v_seq%>')"><%= v_subjnm %></a></td>
			<td class="tbl_grc"><%= v_tname %></td></td>
			<td class="tbl_grc"><%= v_dday %><br></td>
			<td class="tbl_grc"><%= v_starttime %> ~ <%= v_endtime %> </td>
			<td class="tbl_grc"><%= v_place %></td>
			<td class="tbl_grc"><%
			if(v_status.equals("W"))
			{
				%>
					<img src="/images/user/kocca/button/b_cancel.gif" style="cursor:hand" onClick="applyOffLineCancel('<%=v_seq%>')">
				<%
			}
			else
			{
				%>승 인	<%
			}
				%>
				</td>
		  </tr>
<% } 
		if(i == 0)
		{%>
			<tr>
				<td colspan="8" class="tbl_grc">수강 신청 하신 강좌가 없습니다.</td>
			</tr>
<%		} %>
		</table>
		<table width="680" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="20"></td>
		  </tr>
		  <tr> 
			<td><img src="/images/user/kocca/apply/sst_off_apply2.gif"></td>
		  </tr>
		  <tr> 
			<td height="15"></td>
		  </tr>
		</table>
		<!-- 신청가능한 오프라인강좌 -->
		<table width="680" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		  <tr> 
			<td height="3" colspan="8" class="linecolor_app"></td>
		  </tr>
		  <tr> 
			<td width="16" class="tbl_otit">No</td>
			<td width="52" class="tbl_otit2">구분</td>
			<td width="141" class="tbl_otit">강의명</td>
			<td width="55" class="tbl_otit2">담당교수</td>
			<td width="78"  class="tbl_otit">일시</td>
			<td width="69"  class="tbl_otit2">시간</td>
			<td width="130"  class="tbl_otit">장소</td>
			<td width="71" class="tbl_otit2">상태</td>
		  </tr>
		  <tr> 
			<td height="5" colspan="8" ></td>
		  </tr>

<%
	i = 0;
	for(i = 0; i < list1.size(); i++) 
	{
		  DataBox dbox = (DataBox)list1.get(i);

		  v_gubunNm		= dbox.getString("d_gubunnm");
		  v_subjnm		= dbox.getString("d_subjnm");
		  v_tname		= dbox.getString("d_tname");
		  v_dday		= dbox.getString("d_dday");
		  v_starttime	= dbox.getString("d_starttime");
		  v_endtime		= dbox.getString("d_endtime");
		  v_subjnm		= dbox.getString("d_subjnm");
		  v_place		= dbox.getString("d_place");
		  v_seq			= dbox.getInt("d_seq");

		  //날짜 처리
		if(v_dday.equals("")){v_dday = "미정";}
		else{v_dday     = FormatDate.getFormatDate(v_dday,"yyyy/MM/dd");}

%>
		  <tr> 
			<td class="tbl_grc"><%= String.valueOf(i+1) %></td>
			<td class="tbl_grc"><%= v_gubunNm %></td>
			<td class="tbl_bleft"><a href="javascript:applyOffLineView('<%=v_seq%>')"><%= v_subjnm %></a></td>
			<td class="tbl_grc"><%= v_tname %></td>
			<td class="tbl_grc"><%= v_dday %><br></td>
			<td class="tbl_grc"><%= v_starttime %> ~ <%= v_endtime %> </td>
			<td class="tbl_grc"><%= v_place %></td>
			<td class="tbl_grc"><img src="/images/user/kocca/button/b_apply.gif" style="cursor:hand" onClick="applyOffLine('<%=v_seq%>')"></td>
		  </tr>
<% } 
		if(i == 0)
		{%>
			<tr>
				<td colspan="8" class="tbl_grc">수강 신청 하실 수 있는 강좌가 없습니다.</td>
			</tr>
<%		} %>
		</table>
		</form> 
		<br>
		<br>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->