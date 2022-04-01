<%
//**********************************************************
//  1. 제      목: 나의공부방 > 나의상담내역
//  2. 프로그램명 : gu_MyQnaCounsel_L.jsp
//  3. 개      요: 나의 상담내역
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","2");
	box.put("submenu","5");

    if (box == null) box = RequestManager.getBox(request);
    
    String  ss_userid     = box.getSession("userid");    
    int		v_no = 0;
    String  v_sdate       = "";
    String  v_types       = "";
    String  v_type        = "";
    String  v_typenm      = ""; 
    String  v_title       = "";
    String  v_status      = "";
    String  v_status_view = "";
    String  v_status_class = "";
    String  v_searchtext  = box.getString("p_searchtext");   
    String  v_select      = box.getString("p_select");   
    String  p_type        = box.getString("p_type");   
    String  v_edustart    = "";
    String  v_eduend      = "";
    String  v_subj      	= "";
    String  v_subjseq   	= "";
    String  v_year      	= "";
    String  v_userid      	= "";
    String	sScriptString	= "";

     
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;
    
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
        
    ArrayList blist2 = (ArrayList)request.getAttribute("MyQnaCounselListPage");  // 학습관련
      
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
function press_enter(e) { 
	if (e.keyCode =='13'){  selectList();  }
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	//if (form1.p_searchtext.value == ""){
	//	alert("검색 할 내용을 입력해 주세요");
	//}else{
    	document.form1.action = "/servlet/controller.study.MyQnaServlet";
    	document.form1.p_process.value = "MyQnaCounselListPage";
        document.form1.p_pageno.value = "1";
    	document.form1.submit();
	//}
}

//상세화면 페이지로 이동
function select(tabseq, seq,  userid, types) {
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value   = "MyQnaSiteViewPage";
	document.form1.p_tabseq.value    = tabseq;
	document.form1.p_seq.value       = seq;
	document.form1.p_userid.value    = userid;
	document.form1.p_types.value     = types;
    //document.form1.p_upfilecnt.value = upfilecnt;
	document.form1.submit();
}


//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value = "MyQnaCounselListPage";
	document.form1.submit();

}


//Q&A상세화면 페이지로 이동
function viewHomeqna(seq, upfilecnt, userid, types, type) {
	document.form1.action				= "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value		= "MyQnaCounselHomeQnaViewPage";
	document.form1.p_seq.value			= seq;
	document.form1.p_userid.value		= userid;
	document.form1.p_types.value		= types;
	document.form1.pp_type.value		= type;
	document.form1.p_upfilecnt.value	= upfilecnt;
	document.form1.submit();


}

//과정질문 상세화면 페이지로 이동
function viewQna(seq,subj,year,subjseq) {
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value = "MyQnaCounselQnaViewPage";
	document.form1.p_seq.value     = seq;
	document.form1.p_subj.value    = subj;
	document.form1.p_year.value    = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.submit();
}

//-->
</script>
<!-- 스크립트영역종료 -->

<%
	String full_toggle = "off";
	String sub1_toggle = "off";
	String sub2_toggle = "off";
	String sub3_toggle = "off";
	String sub4_toggle = "off";
	
	if ("PQ".equals(p_type))        { sub1_toggle = "on"; 
	} else if ("MM".equals(p_type)) { sub2_toggle = "on"; 				
	} else if ("SUBJ".equals(p_type)) { sub3_toggle = "on"; 	
	} else { full_toggle = "on"; 				
	}
%>

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet" onsubmit="javascript:selectList();">
  <input type = "hidden" name = "p_process"		value = "">
  <input type = "hidden" name = "p_pageno"		value = "<%= v_pageno %>">
  <input type = "hidden" name = "p_seq"			value = "">
  <input type = "hidden" name = "p_userid"		value = "">
  <input type = "hidden" name = "p_upfilecnt"	value = "">
  <input type = "hidden" name = "p_types"		value = "">
  <input type = "hidden" name = "pp_type"		value = "">
  <input type = "hidden" name = "p_tabseq"		value = "">
  <input type = "hidden" name = "p_subj">
  <input type = "hidden" name = "p_year">
  <input type = "hidden" name = "p_subjseq">
  
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 ><u>나의 상담실</u></td>
			</tr>
		</table>
		<div class="concept a05">
			<div class="titlemsg"><img src="/images/2012/sub/page1/title/my_counsel.gif" alt="나의 상담내역"></div>
			<ul class="pagetab">
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=ALL" tabindex="171" title="회원님께서 상담하신 전체 목록을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/total_<%=full_toggle%>.png" alt="전체"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=PQ" tabindex="172" title="사이트 이용 문의 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/site_<%=sub1_toggle%>.png" alt="사이트 이용 문의"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=MM" tabindex="173" title="1:1상담 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/to_<%=sub2_toggle%>.png" alt="1:1상담"></a></li>
				<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage&p_type=SUBJ" tabindex="174" title="과정질문 목록만을 확인합니다"><img src="/images/2012/sub/page1/tabmenu/course_<%=sub3_toggle%>.png" alt="과정질문방"></a></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="50"><col width="140"><col width="*"><col width="100"><col width="120"></colgroup>
					<tr class="listhead">
						<th>순차</th>
						<th>분류</th>
						<th>제목</th>
						<th>작성일자</th>
						<th>답변여부</th>
					</tr>
					
					<%
	        for (int i=0; i<blist2.size(); i++) {
            DataBox dbox = (DataBox)blist2.get(i);  
            v_dispnum	= dbox.getInt("d_dispnum"); 
            v_no		= dbox.getInt("d_no");
            v_sdate		= dbox.getString("d_sdate");
            v_types		= dbox.getString("d_types");
            v_totalpage	= dbox.getInt("d_totalpage");
            v_title		= dbox.getString("d_title");
            v_type		= dbox.getString("d_type");
            v_status	= dbox.getString("d_status");
            v_userid	= dbox.getString("d_userid");
            v_subj		= dbox.getString("d_subj");
            v_year		= dbox.getString("d_year");
            v_subjseq	= dbox.getString("d_subjseq");
            
            int i_upfilecnt = dbox.getInt("d_upfilecnt");
			            
						if (!v_searchtext.equals("")&&v_select.equals("title")) {
							v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
						}
						
				        if (v_type.equals("PQ")) {
				        	v_typenm = "홈페이지";
				        } else if (v_type.equals("BU") || v_type.equals("CU") || v_type.equals("OO") || v_type.equals("MM")) {
				        	v_typenm = "1vs1상담";
				        } else if (v_type.equals("OFF")) {
				        	v_typenm = "유선상담";
				        } else if (v_type.equals("SUBJ")) {
				        	v_typenm = "과정질문방";
				        } else {
				        	v_typenm = "";
				        }
			
				        //vc_status  1 : 미처리, 2 : 처리중, 3 : 완료
				        if (v_status.equals("1")) {
				            v_status_view	= "미처리";
				            v_status_class	= "pre";
				        } else if (v_status.equals("2")) {
				            v_status_view	= "답변중";
				            v_status_class	= "ing";
				        } else if (v_status.equals("3")) {
				            v_status_view	= "답변완료";
				            v_status_class	= "finish";
				        } else {
				            v_status_view	= "미처리";
				            v_status_class	= "pre";
				        }
				        
				        //상세보기 링크 만들기
				        /*
				        if ("OFF".equals(v_type)) {
				        	sScriptString	= "viewCounsel('" + v_no + "');";
				        } else
				        */
				        if ("PQ".equals(v_type) || "BU".equals(v_type) || "CU".equals(v_type) || "OO".equals(v_type) || "MM".equals(v_type)) {
				        	sScriptString	= "viewHomeqna('" + v_no + "', '" + v_upfilecnt + "', '" + v_userid + "', '" + v_types + "', '" + v_type + "');";	
				        } else if ("SUBJ".equals(v_type)) {
				        	sScriptString	= "viewQna('" + v_no + "', '" + v_subj + "', '" + v_year + "', '" + v_subjseq + "');";	
				        }
					%>
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%= v_dispnum %></td>
						<td><%= v_typenm %></td>
						<td class="title" id="bboardfocus<%=i+1%>"><a href="javascript:<%= sScriptString %>" onfocus="boardfocus('b',<%=i+1%>)" onblur="boardfocus('b',<%=i+1%>)" tabindex="<%="2"+i+"1"%>" title="<%=FormatDate.getFormatDate(v_sdate, "yyyy.MM.dd")%> <%= v_title %>"><%= v_title %></a></td>
						<td><%=FormatDate.getFormatDate(v_sdate, "yyyy.MM.dd")%></td>
						<td><button type="button" tabindex="<%="2"+i+"2"%>" onclick="javascript:<%= sScriptString %>" class="btn_nomal_whitegray" title="질문하신 내용에 대해 답변을 준비중입니다"><%= v_status_view %></button></td>
					</tr>
					<%} %>
					<%	if(blist2.size() == 0){	 %>
				  <tr><td colspan="5" align="center" class="linefrist">해당하는 데이타가  없습니다.</td></tr>
				  <% } %>	
				</table>
			</div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
