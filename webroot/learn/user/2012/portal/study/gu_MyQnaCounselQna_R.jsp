<%
//**********************************************************
//  1. 제      목: 나의상담내역 - 과정 QnA 상세보기
//  2. 프로그램명 : gu_MyQnaCounselSangdam_R.jsp
//  3. 개      요: 나의상담내역 
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","2");
	box.put("submenu","5");

	String	v_process	= box.getString("p_process");
	String  v_categorycd  = box.getString("p_categorycd");
	int     v_seq         = box.getInt("p_seq");
	String  v_subj        = box.getString("p_subj");
	String  v_year        = box.getString("p_year");
	String  v_subjseq     = box.getString("p_subjseq");
	String  v_type        = box.getString("p_type");
	String  v_searchtext  = box.getString("p_searchtext");
	String  v_select      = box.getString("p_select");
	String  v_repkind     = "";
	int     v_repseq      = 0;

	int		v_pageno	  = box.getInt("p_pageno");
    int		v_pagesize	  = box.getInt("p_pagesize");
	
    DataBox dbox        = (DataBox)request.getAttribute("selectMyQnaCounselQna");
	String v_inuserid   = dbox.getString("d_inuserid");
	String v_lesson     = dbox.getString("d_lesson");
	String v_kind       = dbox.getString("d_kind");
	String v_name       = dbox.getString("d_name");
	String v_indate     = dbox.getString("d_indate");
	String v_title      = dbox.getString("d_title");
	String v_contents   = dbox.getString("d_contents");
	String v_grcodenm   = dbox.getString("d_grcodenm");
	String v_grcode     = dbox.getString("d_grcode");
	String v_okyn1      = dbox.getString("d_okyn1");
	String v_okuserid1  = dbox.getString("d_okuserid1");
	String v_okdate1    = dbox.getString("d_okdate1");
	String v_okyn2      = dbox.getString("d_okyn2");
	String v_okuserid2  = dbox.getString("d_okuserid2");
	String v_okdate2    = dbox.getString("d_okdate2");
	String v_scsubjnm   = dbox.getString("d_scsubjnm");
	String v_subjseqgr  = dbox.getString("d_subjseqgr");
	Vector v_realfileVector = (Vector)dbox.getObject("d_realfile");
	Vector v_savefileVector = (Vector)dbox.getObject("d_savefile");
	String v_realmotion  = dbox.getString("d_realmotion");
	String v_savemotion  = dbox.getString("d_savemotion");
	String v_status_view = "";
	int    v_filecount   = v_realfileVector.size();
	int    v_cnt         = dbox.getInt("d_cnt");

    //vc_status  1 : 미처리, 2 : 처리중, 3 : 완료
    if (v_okyn1.equals("1")) {
        v_status_view = "열람전";
    } else if (v_okyn1.equals("2")) {
        v_status_view = "답변준비중";
    } else if (v_okyn1.equals("3")) {
        v_status_view = "답변완료";
    } else {
        v_status_view = "";
    }

	MyQnaBean myqnabean = new MyQnaBean();

	//Relative Start
	String pp_searchStart	= box.getString("p_searchStart");
	String pp_searchEnd     = box.getString("p_searchEnd");
	String ss_gyear         = box.getString("s_gyear");
	String ss_grseq         = box.getString("s_grseq");
	String ss_upperclass    = box.getString("s_upperclass");
	String ss_middleclass   = box.getString("s_middleclass");
	String ss_lowerclass    = box.getString("s_lowerclass");
	String ss_subjcourse    = box.getString("s_subjcourse");
	String ss_subjseq       = box.getString("s_subjseq");
	String pp_order       	= box.getString("p_order");
	String pp_orderType     = box.getString("p_orderType");
	//Relative End
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>

<script language="JavaScript" type="text/JavaScript">
<!--
// 목록으로
function listPage() {
    document.form1.target = "_self";
	document.form1.p_process.value='MyQnaCounselListPage';
	document.form1.action='/servlet/controller.study.MyQnaServlet';
	document.form1.submit();
}

//-->
</script>

<%
	String full_toggle = "off";
	String sub1_toggle = "off";
	String sub2_toggle = "off";
	String sub3_toggle = "off";
	String sub4_toggle = "off";
	
	if ("PQ".equals(v_type))        { sub1_toggle = "on"; 
	} else if ("MM".equals(v_type)) { sub2_toggle = "on"; 				
	} else if ("SUBJ".equals(v_type)) { sub3_toggle = "on"; 	
	} else { full_toggle = "on"; 				
	}
%>

<!-- title -->
<form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet">
  <input type = "hidden" name = "p_process"		value = "<%= v_process %>">
  <input type = "hidden" name = "p_pageno"		value = "<%= v_pageno %>">
  <input type = "hidden" name = "p_pagesize"	value = "<%= v_pagesize %>">
  <input type = "hidden" name = "p_type"		value = "<%= v_type %>">
  <input type = "hidden" name = "p_subj"		value = "<%= v_subj %>">
  <input type = "hidden" name = "p_year"		value = "<%= v_year %>">
  <input type = "hidden" name = "p_subjseq"		value = "<%= v_subjseq %>">
  <input type = "hidden" name = "p_select"		value = "<%= v_select %>">
  <input type = "hidden" name = "p_searchtext"	value = "<%= v_searchtext %>">

	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 ><u>나의 상담실</u></td>
			</tr>
		</table>
		<div class="concept a05">
			<div class="titlemsg"><img src="/images/2012/sub/page1/title/my_counsel.gif" alt="나의 상담실"></div>
			<ul class="pagetab">
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=ALL" tabindex="171" title="회원님께서 상담하신 전체 목록을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/total_<%=full_toggle%>.png" alt="전체"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=PQ" tabindex="172" title="사이트 이용 문의 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/site_<%=sub1_toggle%>.png" alt="사이트 이용 문의"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=MM" tabindex="173" title="1:1상담 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/to_<%=sub2_toggle%>.png" alt="1:1상담"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=SUBJ" tabindex="174" title="과정질문 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/course_<%=sub3_toggle%>.png" alt="과정질문방"></a></li>
			</ul>
		</div>
		<div class="viewbar"><h6><b><%= v_scsubjnm %></b></h6></div>
		<div class="viewcontent">
			<h6><%=v_contents%></h6>
			<table class="about" cellpadding="0" cellspacing="0">
				<colgroup><col width="40"><col width="*"><col width="50"><col width="70"><col width="65"><col width="60"></colgroup>
				<tr>
					<th>분류</th>
					<td>홈페이지 이용불편 신고</td>
					<th>작성자</th>
					<td><%=v_name%></td>
					<th>작성일자</th>
					<td><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
				</tr>
			</table>
		</div>
		
		<%
			ArrayList list = (ArrayList)request.getAttribute("selectMyQnaCounselQnaListA");
			ArrayList list1 = new ArrayList();
			int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
			//int v_cnt = 0;
			int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1;
			if(list.size() != 0) {
				for(int i = 0; i < list.size(); i++) {
					DataBox dboxA = (DataBox)list.get(i);
					v_dispnum	= dboxA.getInt("d_dispnum");
					v_inuserid	= dboxA.getString("d_inuserid");
					v_name		= dboxA.getString("d_name");
					v_indate	= dboxA.getString("d_indate");
					v_title		= dboxA.getString("d_title");
					v_seq		= dboxA.getInt("d_seq");
		            v_totalpage	= dboxA.getInt("d_totalpage");
		            v_rowcount	= dboxA.getInt("d_rowcount");
		            v_contents	= dboxA.getString("d_contents");
					v_scsubjnm	= dboxA.getString("d_scsubjnm");
					v_subjseqgr = dboxA.getString("d_subjseqgr");
					v_repseq	= dboxA.getInt("d_seq");
					v_repkind	= dboxA.getString("d_kind");
			
		%>      


		<div class="viewanswer">
			<table class="answer" cellpadding="0" cellspacing="0">
				<colgroup><col width="70"><col width="*"></colgroup>
				<tr>
					<th>
						<h2>Answer</h2>
						<h6><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></h6>
					</th>
					<td>
						<%= v_title %>
						<br><%= v_contents %><br>
					</td>
				</tr>
			</table>
		</div>
		<% }} %>
		<div class="viewfooter"><button type="button" tabindex="200" onclick="javascript:listPage();" class="btn_small_lineblue" title="목록으로 돌아갑니다">목록으로</button></div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
