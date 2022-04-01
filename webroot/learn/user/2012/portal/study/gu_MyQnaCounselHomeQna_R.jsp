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
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","2");
	box.put("submenu","5");
	
	int    v_seq        = box.getInt("p_seq");
	int    v_pageno     = box.getInt("p_pageno");
	String v_searchtext = box.getString("p_searchtext");             
	String v_select     = box.getString("p_select");
	String v_selCategory = box.getString("p_selCategory");
	
    String  v_categorycd  = box.getStringDefault("p_categorycd","00");
	String	v_process	  = box.getString("p_process");
	String  v_subj        = box.getString("p_subj");
	String  v_year        = box.getString("p_year");
	String  v_subjseq     = box.getString("p_subjseq");
	String  v_type        = box.getString("pp_type");
	String  v_repkind     = "";
	int     v_repseq      = 0;
    int		v_pagesize	  = box.getInt("p_pagesize");

    String  v_action      = box.getString("p_action");
    int     v_upfilecnt   = box.getInt("p_upfilecnt");          //  서버에 저장되있는 파일수
	
	String s_userid     = box.getSession("userid");
	
	String v_title      = "";
	String v_contents   = "";
	String v_inuserid   = "";
	String v_indate     = "";
	String v_types      = "";
	String v_name       = "";
	String v_categorynm = "";
	String v_isopen     = "";
	String v_repstatus  = "";
	int    v_cnt        = 0;
	
	DataBox dbox        = (DataBox)request.getAttribute("selectQna");
	
	if(dbox != null) {
	    v_inuserid          = dbox.getString("d_inuserid");
	    v_name              = dbox.getString("d_name");
	    v_types             = dbox.getString("d_types");
	    v_indate            = dbox.getString("d_indate");
	    v_title             = dbox.getString("d_title");
	    v_seq               = dbox.getInt("d_seq");
	    v_cnt               = dbox.getInt("d_cnt");
	    v_contents          = dbox.getString("d_contents");
	    v_categorycd        = dbox.getString("d_categorycd");
	    v_categorynm        = dbox.getString("d_categorynm");
	    v_isopen            = dbox.getString("d_isopen");
	    v_repstatus         = dbox.getString("d_okyn1");
	}
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
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
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet">
    <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
    <input type = "hidden" name = "p_select"      value = "<%= v_select %>">
    <input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
    <input type = "hidden" name = "p_selCategory" value = "<%= v_selCategory %>">
    <input type = "hidden" name = "p_process"     value = "">
    <input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
    <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
    <input type = "hidden" name = "pp_type"       value = "<%=v_type%>">
    
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
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&v_type=ALL" tabindex="171" title="회원님께서 상담하신 전체 목록을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/total_on.png" alt="전체"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&v_type=PQ" tabindex="172" title="사이트 이용 문의 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/site_off.png" alt="사이트 이용 문의"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&v_type=MM" tabindex="173" title="1:1상담 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/to_off.png" alt="1:1상담"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&v_type=SUBJ" tabindex="174" title="과정질문 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/course_off.png" alt="과정질문방"></a></li>
			</ul>
		</div>
		<div class="viewbar"><h6><b><%= v_title %></b></h6></div>
		<div class="viewcontent">
			<h6><%=v_contents%></h6>
			<table class="about" cellpadding="0" cellspacing="0" summary="게시물 정보입니다">
				<colgroup><col width="40"><col width="*"><col width="50"><col width="70"><col width="65"><col width="60"></colgroup>
				<tr>
					<th>분류</th>
					<td><%=v_categorynm %></td>
					<th>작성자</th>
					<td><%=v_name%></td>
					<th>작성일자</th>
					<td><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
				</tr>
			</table>
		</div>
		<%
		 DataBox Ansbox        = (DataBox)request.getAttribute("selectAns");
    
		String v_anstitle      = "";
		String v_anscontents   = "";
		String v_ansinuserid   = "";
		String v_ansindate     = "";
		String v_ansname       = "";
		String v_anscategorynm = "";
		
		
		if(Ansbox != null) {
		    v_ansinuserid          = Ansbox.getString("d_inuserid");
		    v_ansname              = Ansbox.getString("d_name");
		    v_ansindate            = Ansbox.getString("d_indate");
		    v_anstitle             = Ansbox.getString("d_title");
		    v_anscontents          = Ansbox.getString("d_contents");
		    v_anscategorynm        = Ansbox.getString("d_categorynm");
		    
	
		%>
		
		<div class="viewanswer">
			<table class="answer" cellpadding="0" cellspacing="0">
				<colgroup><col width="70"><col width="*"></colgroup>
				<tr>
					<th>
						<h2>Answer</h2>
						<h6><%= FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd") %></h6>
					</th>
					<td>
						<%=v_anscontents%><br>
					</td>
				</tr>
			</table>
		</div>
		
		<% } %>
		<div class="viewfooter"><button type="button" tabindex="200" onclick="javascript:history.go(-1);" class="btn_small_lineblue" title="목록으로 돌아갑니다">목록으로</button></div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
