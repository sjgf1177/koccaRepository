
<%
//**********************************************************
//  1. 제      목: 홈페이지 오프라인강좌소식통
//  2. 프로그램명 : OffLine_Info.jsp
//  3. 개      요: 홈페이지 오프라인강좌소식통
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>
	   
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	String v_dday	= "";	// 강의일
	String v_subjnm	= "";	// 강의명
	int v_seq		= 0;	

	ArrayList list      = null;

	list = (ArrayList)KProposeCourseBean.OffLineSelectSubjectTopList(box);	// 최근 과목

%>	
<script>
	// 상세보기
	function applyOffLineView(seq)
	{
		var frm = document.frmOffLine;

		frm.p_seq.value = seq;
		frm.action = "/servlet/controller.propose.KProposeCourseServlet";
		frm.p_process.value = "OffLineView";
		frm.target = "_self";
		frm.submit();
	}
</script>

<!-- 팝업창 띄우는 메소드 //-->
<script language="JavaScript" type="text/JavaScript">
	<!--
		function MM_openBrWindow(theURL,winName,features) { //v2.0
		window.open(theURL,winName,features);
	}
//-->
</script>

<form name="frmOffLine">
	<input type="hidden" name="p_seq">
	<input type="hidden" name="p_process">
	<table width="223" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td colspan="3"><img src="/images/user/kocca/homepage/type1/st_offline_info.gif" width="223" height="27" border="0" usemap="#Map4"></td>
	  </tr>
	  <tr> 
		<td colspan="3"><img src="/images/user/kocca/homepage/type1/st_offline_bar01.gif" width="223" height="5"></td>
	  </tr>
	  <tr> 
		<td width="5" height="46" bgcolor="#EEEEEE"></td>
		<td width="213" bgcolor="#EEEEEE">
		<table width="213" border="0" cellspacing="0" cellpadding="0">
<%
	int i = 0;
	for(i = 0; i < list.size(); i++) 
	{
		DataBox dbox = (DataBox)list.get(i);

		v_seq		= dbox.getInt("d_seq");
		v_dday		= dbox.getString("d_dday");
		v_subjnm	= dbox.getString("d_subjnm");

		if(v_subjnm.length() > 10)
		{
			v_subjnm = v_subjnm.substring(0,10) + "...";
		}


		//날짜 처리
		if(v_dday.equals("")){v_dday = "미정";}
		else{v_dday     = FormatDate.getFormatDate(v_dday,"yyyy/MM/dd");}

%>			<tr> 
			  <td width="63" height="23" class="tbl_bleft3"><%=v_dday%></td>
			  <td><img src="/images/user/kocca/homepage/type1/vline_offline.gif" width="1" height="15"></td>
			  <td class="tbl_gleft"><a href="javascript:applyOffLineView('<%=v_seq%>')"><%= v_subjnm %></a></td>
			</tr>
<%		} 

	 if (i == 0) { %>
			<tr> 
			  <td height="1" colspan="3" align="Center">이 달의 오프라인 강좌를 준비중입니다.</td>
			</tr>
<%	}	%>
		  </table>
		</td>
		<td width="5" bgcolor="#EEEEEE"></td>
	</tr>
	<tr> 
		<td colspan="3"><img src="/images/user/kocca/homepage/type1/st_offline_bar02.gif" width="223" height="5"></td>
	</tr>
	<tr> 
		<td height="15" colspan="3"></td>
	</tr>
	<tr>
		<td colspan="3"><a href="/servlet/controller.community.CommunityIndexServlet?p_cmuno=2006000023&p_static_cmuno=2006000023"><img src="/images/user/kocca/homepage/type2/r_bn01.gif"></a></td>
	</tr>
	<tr> 
		<td height="7" colspan="3"></td>
	</tr>
	<tr>
		<td colspan="3"><a href="http://contents.connect.or.kr/servlet/controller.community.CommunityIndexServlet?p_cmuno=2006000024"><img src="/images/user/kocca/homepage/type2/r_bn03.gif"></a></td>
	</tr>
	<tr> 
		<td height="7" colspan="3"></td>
	</tr>
	<tr>
		<td colspan="3"><a href="http://contents.connect.or.kr/servlet/controller.homepage.KHome1vs1Servlet?p_process=movePage"><img src="/images/user/kocca/homepage/type2/r_bn02.gif"></a></td>
	</tr>
	<tr> 
		<td height="7" colspan="3"></td>
	</tr>
	<tr>
		<td colspan="3"><a href="#" onClick="MM_openBrWindow('/learn/user/kocca/homepage/pop_curri.jsp','','scrollbars=yes,width=816,height=600');"><img src="/images/user/kocca/homepage/type2/b_curri.gif"></a></td>
	</tr>
	<tr> 
		<td height="7" colspan="3"></td>
	</tr>
	<tr>
		<td colspan="3"><a href="#" onClick="MM_openBrWindow('/learn/user/kocca/homepage/pop_map.jsp','','scrollbars=no,width=676,height=629');"><img src="/images/user/kocca/homepage/type2/b_map.gif"></a></td>
	</tr>
	<tr> 
		<td height="7" colspan="3"></td>
	</tr>
	<!--tr>
		<td colspan="3"><a href="http://218.232.93.13/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=10"><img src="/images/user/kocca/homepage/type1/event_bn.jpg"></a></td>
	</tr>
	<tr> 
		<td height="15" colspan="3"></td>
	</tr-->
</form>

<map name="Map4">
  <area shape="rect" coords="193,3,221,20" href="/servlet/controller.propose.KProposeCourseServlet?p_process=OffLineSubjPage">
</map>