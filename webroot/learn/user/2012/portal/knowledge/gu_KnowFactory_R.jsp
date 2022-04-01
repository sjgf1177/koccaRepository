<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명 : gu_KnowBoard_L.jsp
//  3. 개      요: 지식공유 최근지식 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");
box.put("topmenu","8");

String v_title = "", v_contents = "", v_inuserid = "", v_indate = "" , v_types = "";
Vector v_realfileVector = null;
Vector v_savefileVector = null;
int    v_seq        = box.getInt("p_seq");
int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
int    v_pageno     = box.getInt("p_pageno");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select     = box.getString("p_select"); 
String v_tabseq 	= box.getString("p_tabseq");
String v_cate 		= box.getString("p_types");     // 질문방 C 지식팩토리 B 자료방 A
String v_name = "";
String v_repstatus  = "";
String v_subjnm = "";

String tabimg1 ="tab01.gif";
String tabimg2 ="tab02.gif";
String tabimg3 ="tab03.gif";
String tabimg4 ="tab04.gif";
String tabimg5 ="tab05.gif";

DataBox dbox        = (DataBox)request.getAttribute("SelectView");

int v_commentseq    = 0;
 

v_inuserid          = dbox.getString("d_inuserid");
v_name              = dbox.getString("d_name");
v_types             = dbox.getString("d_types");
v_indate            = dbox.getString("d_indate");
v_title             = dbox.getString("d_title");
v_subjnm             = dbox.getString("d_subjnm");
v_seq               = dbox.getInt("d_seq");
if (v_cate.equals("C")) {
	v_contents          = dbox.getString("d_content").replace("\r", "<BR>");  // 보여주는 부분 엔터
} else {
	v_contents          = dbox.getString("d_content");
}
v_realfileVector    = (Vector)dbox.getObject("d_realfile");
v_savefileVector    = (Vector)dbox.getObject("d_savefile");
String v_categorynm = dbox.getString("d_categorynm");
int v_recommend = dbox.getInt("d_recommend");
int v_cnt       = dbox.getInt("d_cnt");
v_repstatus         = dbox.getString("d_okyn1");

String  v_categorycd= dbox.getString("d_categorycd");
String s_userid     = box.getSession("userid");
String s_gender  	= box.getSession("gender");

int    v_filecount = v_realfileVector.size();

String p_categorycd = box.getString("p_categorycd");            // request 카테고리


/*
if(p_categorycd.equals("")){
    p_categorycd=v_categorycd;
}
*/
String v_process= box.getString("p_process");

String full_toggle = "off";
String sub1_toggle = "off";
String sub2_toggle = "off";
String sub3_toggle = "off";
String sub4_toggle = "off";
String sub_navi = "";

if (v_categorycd.equals("B0")){
	sub1_toggle = "on"; 
	sub_navi = "방송영상";
} else if (v_categorycd.equals("G0")){
	sub2_toggle = "on"; 
	sub_navi = "게임개발";
} else if (v_categorycd.equals("K0")){
	sub3_toggle = "on"; 
	sub_navi = "문화콘텐츠";
}else if (v_categorycd.equals("MINE")){
	sub4_toggle = "on"; 
	sub_navi = "내가작성한글";
} else {
	full_toggle = "on"; 
	sub_navi = "종합";
}

String sFileExt ="";
String imgExt ="";
	
%>


<script language="JavaScript" type="text/JavaScript">
<!--

//리스트페이지로 이동
function selectList() {
   	document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form1.p_process.value = "ListPage";     
	document.form1.submit();	
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.submit();
}

//수정페이지로 이동
function updatePage() {
	document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}

//답변 등록 페이지로 이동
function replyPage() {
	document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form1.p_process.value = "replyPage";
	document.form1.submit();
}   

//꼬릿말 등록
function insertcomment() {
	if(document.form2.commentqna.value==""){
		alert("꼬릿말을 등록하세요");
		return;
	}
	document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
	document.form2.action = "/servlet/controller.homepage.KnowBoardUserServlet";
	document.form2.p_process.value = "commentInsert";
	document.form2.submit();
}

//뷰 화면 삭제
function deleteAction() {
	if (confirm("정말로 삭제하시겠습니까?")) {
		
		document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
		document.form1.p_process.value = "delete";
		document.form1.submit();
	}
	else {
		return;
	}
}

//꼬릿말 삭제
function deleteComment(v_commentseq) {
	if (confirm("정말로 삭제하시겠습니까?")) {
		document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
		document.form2.p_commentseq.value = v_commentseq;
		document.form2.action = "/servlet/controller.homepage.KnowBoardUserServlet";
		document.form2.p_process.value = "deleteComment";
		document.form2.submit();
	}
	else {
		return;
	}
}

// 추천하기
function InsertRecommend(seq) {
//alert(document.form1.p_categorycd.value);
	if (confirm("추천하시겠습니까?")) {
		document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
		document.form1.p_process.value = "insertRecommend";
		document.form1.submit();
	}
	else {
		return;
	}
}
//-->
</script>
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 지식팩토리 > <u><%=sub_navi%></u></td>
			</tr>
		</table>
		<div class="concept e01">
			<div><img src="/images/2012/sub/page6/title/common.gif" alt="여러분이 궁금한 걸 물어보세요! 분야별 전문가님들이 답변을 달아드립니다."></div>
			<ul class="factorysearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" onfocus="typechoiceopen('1')" tabindex="171"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" id="choicetype" value="subject">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<!-- <dd onclick="searchtype('내용')" tabindex="173" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd>
						<dd onclick="searchtype('작성자')" tabindex="174" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">작성자</dd> -->
					</dl>
				</li>
				<li class="insert"><input type="text" class="insearch" tabindex="175" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><input type="image" src="/images/2012/sub/page2/btn_course_search.gif" tabindex="176" title="검색어를 입력 후 클릭해주세요" alt="과정검색"></li>
			</ul>
			<ul class="pagetab">
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage" tabindex="177" title="지식팩토리의 전체 목록을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/total_<%=full_toggle %>.png" alt="종합"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=B0" tabindex="178" title="방송영상 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/cast_<%=sub1_toggle%>.png" alt="방송영상"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=G0" tabindex="179" title="게임개발 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/game_<%=sub2_toggle%>.png" alt="게임개발"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=K0" tabindex="180" title="문화콘텐츠 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/culture_<%=sub3_toggle%>.png" alt="문화콘텐츠"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=MINE" tabindex="181" title="회원님이 작성한 글을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/mywrite_<%=sub4_toggle%>.png" alt="내가 작성한 글"></a></li>
			</ul>
		</div>
		<div class="viewbar"><h6><b><%=v_title%> </b></h6></div>
		<form name = "form1" method = "post" action="">
		<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
		<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
		<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
		<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
		<input type = "hidden" name = "p_process"     value = "">
		<input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
		<input type = "hidden" name = "p_types"       value = "<%=v_types%>">
		<input type = "hidden" name = "p_categorycd"  value = "<%=p_categorycd%>">				
		<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
		<div class="viewcontent">
			<h6><%=v_contents%></h6>
		 	<%
	    if( v_realfileVector != null ) {
	      for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
	
	        String v_realfile = (String)v_realfileVector.elementAt(i);
	        String v_savefile = (String)v_savefileVector.elementAt(i);
	
	        if(v_realfile != null && !v_realfile.equals("")) {  
	      	  
	       
	      	  if(v_realfile != null && !v_realfile.equals("")) { 
	            	sFileExt = 	v_realfile.substring(v_realfile.lastIndexOf(".")+1);
	            	if (sFileExt.equals("hwp")) {
	            		imgExt = "<img src='/images/2012/common/icon/icon_hwp.gif' id='exticon' align='absmiddle' alt='한글문서'>";
	            	} else if (sFileExt.equals("doc") || sFileExt.equals("docx")) {
	            		imgExt = "<img src='/images/2012/common/icon/icon_doc.gif' id='exticon' align='absmiddle' alt='워드문서'>";
	            	} else if (sFileExt.equals("ppt")||sFileExt.equals("pptx")) {
	            		imgExt = "<img src='/images/2012/common/icon/icon_ppt.gif' id='exticon' align='absmiddle' alt='프레젠테이션'>";
	            	} else if (sFileExt.equals("xls") ||sFileExt.equals("xlsx") ) {
	            		imgExt = "<img src='/images/2012/common/icon/icon_xls.gif' id='exticon' align='absmiddle' alt='엑셀'>";
	            	} else if (sFileExt.equals("pdf")) {
	            		imgExt = "<img src='/images/2012/common/icon/icon_pdf.gif' id='exticon' align='absmiddle' alt='PDF'>";
	            	} else {
	            		imgExt = "<img src='/images/2012/common/icon/icon_idk.gif' id='exticon' align='absmiddle' alt='알 수 없는 파일형식'>";
	            	}
	      	 
	        
	    %>                   
			<ul class="fileload">
				<li><%=imgExt%> 
				<a href="/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>" tabindex="21-<%=i%>"> <%= v_realfile%></a></li>
				<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
			</ul>
			<% }}}} %>
			
			<table class="about" cellpadding="0" cellspacing="0" summary="선택하신 게시물 정보입니다">
				<colgroup><col width="50"><col width="*"><col width="50"><col width="70"><col width="65"><col width="60"></colgroup>
				<tr>
					<th id="w_course">과정명</th>
					<td name="w_course"><%=v_subjnm%></td>
					<th id="w_writer">작성자</th>
					<td name="w_writer"><%=v_name%></td>
					<th id="w_date">작성일자</th>
					<td name="w_date"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
				</tr>
			</table>
		</div>
		<%
		if(v_repstatus.equals("3")||v_repstatus.equals("2")){ 
		
		  DataBox Ansbox = (DataBox)request.getAttribute("selectAns");
		    
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
			    
			}
		%>		
		<div class="viewanswer" summary="이 게시물에 대한 답변입니다">
			<table class="answer" cellpadding="0" cellspacing="0">
				<colgroup><col width="70"><col width="*"></colgroup>
				<tr>
					<th id="w_answer">
						<h2>Answer</h2>
						<h6><%=FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd")%></h6>
					</th>
					<td name="w_answer">
						<B><%=v_anstitle %></B><BR><BR>
						<%=v_anscontents%>
					</td>
				</tr>
			</table>
		</div>
		<% } %>
		<div class="viewfooter">
		<%if(s_userid.equals(v_inuserid)){	%>			
			<!-- <button type="button" tabindex="200" onclick="javascript:updatePage()" class="btn_small_linegray" title="이 글을 수정합니다">수정하기</button> -->
			<button type="button" tabindex="201" onclick="javascript:deleteAction()" class="btn_small_linegray" title="이 글을 삭제합니다">삭제하기</button>
		<% } %>
			<button type="button" tabindex="202" onclick="javascript:selectList();" class="btn_small_lineblue" title="목록으로 돌아갑니다">목록으로</button>
		</div>
		</form>
	
		<div class="comment">
			<table class="commentlist" cellpadding="0" cellspacing="0" summary="이 게이물에 대한 댓글 목록입니다">
				<colgroup><col width="80"><col width="*"><col width="40"></colgroup>
				<% ArrayList list = (ArrayList)request.getAttribute("selectCommentList"); %>
				<!-- 댓글 반본 구간 시작 -->                             
				<%
				//if(list.size() != 0){
 
				for(int i = 0; i < list.size(); i++) {
					dbox = (DataBox)list.get(i);  
					v_inuserid      = dbox.getString("d_inuserid");
					String c_name   = dbox.getString("d_name");
					v_indate        = dbox.getString("d_cdate");
					String v_commentqna    = dbox.getString("d_commentqna");
					v_commentseq    = dbox.getInt("d_commentseq");
				%>
				<tr>
					<th>
					<% if (dbox.getString("d_gender").equals("2")) { %>
					<img src="/images/2012/common/icon/icon_sex_woman.gif" align="absmiddle" alt="여성">
					<% } else { %>
					<img src="/images/2012/common/icon/icon_sex_man.gif" align="absmiddle" alt="남성">
					<% } %>
					<span class="man"><%=v_inuserid%></span></th>
					<td><%=v_commentqna%><span><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></span></td>
					<td>
					<%if(s_userid.equals(v_inuserid)){	%>
						<!-- <a href="#" tabindex="<%=i+203%>" title="회원님의 댓글을 수정합니다"><img src="/images/2012/common/icon/btn_comment_modify.gif" align="absmiddle" alt="댓글수정"></a> -->
						<a href="javascript:deleteComment(<%=v_commentseq%>)" tabindex="<%=i+243%>" title="회원님의 댓글을 삭제합니다"><img src="/images/2012/common/icon/btn_comment_delete.gif" align="absmiddle" alt="댓글삭제"></a>
					<% } %>
					</td>
				</tr>
				<%}
				//}
				%>
			</table>
			
			<% if (v_cate.equals("B")) { %>
			<div class="commentwritebox">
			 <form name = "form2" method = "post" action="">
				  <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
				  <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
				  <input type = "hidden" name = "p_select"      value = "<%= v_select %>">
				  <input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
				  <input type = "hidden" name = "p_process"     value = "">
				  <input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
				  <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
				  <input type = "hidden" name = "p_commentseq" value ="">
				  <input type = "hidden" name = "p_categorycd" value ="">
				  <input type = "hidden" name = "p_gender" value = "<%=s_gender %>">
				<table class="commentwrite" cellpadding="0" cellspacing="0">
					<colgroup><col width="80"><col width="*"><col width="112"></colgroup>
					<tr>
						<td>
						<% if (s_gender.equals("2")) { %>
						<img src="/images/2012/common/icon/icon_sex_woman.gif" align="absmiddle" alt="여성">
						<% } else { %>
						<img src="/images/2012/common/icon/icon_sex_man.gif" align="absmiddle" alt="남성">
						<% } %>
							<span class="man"><%=s_userid %></span>
						</td>
						<td><textarea name="commentqna" tabindex="270" class="write" onfocus="this.style.background='#ffffff'"></textarea></td>
						<td><button type="button" tabindex="271" onClick="javascript:insertcomment()" class="btn_large_linewhiteblue" title="댓글을 등록합니다">댓글달기</button></td>
					</tr>
				</table>
				</form>
			</div>
			<% } %>
		</div>
	</div>
	<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>