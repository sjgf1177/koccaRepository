<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("topmenu","5");
	box.put("submenu","3");

    if (box == null) box = RequestManager.getBox(request);
    int v_tabseq         = box.getInt("p_tabseq");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_seq = 0,v_readcnt = 0,v_cnt = 0;

    String v_inuserid   = "";
    String v_position   = "";
    String v_indate     = "";
    String v_title      = "";
    String tLink        = "";
    String v_types      = "";
    String v_name       = "";
    String v_categorycd = "";
    String v_categorynm = "";
    String v_isopen     = "";
    String v_repstatus  = "";
    
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    ArrayList selectList = (ArrayList)request.getAttribute("selectList");
      
    String  v_searchtext     = box.getString("p_searchtext");   
    String  v_select         = box.getString("p_select");
    String  v_selCategory    = box.getString("p_selCategory");

    int v_pageno             = box.getInt("p_pageno"); 
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

	int	 v_totalrowcount =  0;

    String v_classname1      = "";
    String v_classname2      = "";
                  
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//입력페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "insertPage";  
    document.form1.p_pageno.value = "<%= v_pageno %>";    
    document.form1.submit();
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
    if (form1.p_searchtext.value == ""){
        alert("검색 할 내용을 입력해 주세요");
    }else{
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "";
    document.form1.submit();
    }
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.submit();
}

//카테고리에 맞는 리스트 검색
function changeCategory() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value    = "";
    document.form1.submit();
}

//등록 페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
     document.form1.p_process.value = "";
     document.form1.submit();
}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name = "form1" method = "post" action="">
<input type = "hidden" name = "p_process"     value = "">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_seq"         value = "">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_upfilecnt"   value = "">
<input type = "hidden" name = "p_types"       value = "<%=v_types%>">
  <div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고 계시는 화면의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>묻고답하기</u></td>
			</tr>
		</table>
		<div class="concept">
			<div><img src="/images/2012/sub/page4/title/qna.gif" alt="학습에 대해 궁금한 것이 있다면 무엇이든 물어보세요"></div>
			<ul class="boardsearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" tabindex="171" onfocus="typechoiceopen('1')"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" name="p_select" id="choicetype" value="title">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제','title')" onkeypress="searchtype('주제','title')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<dd onclick="searchtype('내용','content')" onkeypress="searchtype('내용','content')" tabindex="173" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd>
					</dl>
				</li>
				<li class="insert"><input type="text" name="p_searchtext" tabindex="174" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:selectList();" tabindex="175" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0" summary="묻고답하기의 목록을 확인합니다">
					<colgroup><col width="50"><col width="140"><col width="*"><col width="80"><col width="80"><col width="100"><col width="50"></colgroup>
					<tr class="listhead">
						<th id="w_num">번호</th>
						<th id="w_category">분류</th>
						<th id="w_subject">제목</th>
						<th id="w_writer">작성자</th>
						<th id="w_writingdate">작성일</th>
						<th id="w_answer">답변여부</th>
						<th id="w_count">조회</th>
					</tr>
				  <%
				  if(selectList.size() != 0){
				    for(int i = 0; i < selectList.size(); i++) {
		           DataBox dbox = (DataBox)selectList.get(i);  
		           v_dispnum    = dbox.getInt("d_dispnum");
		           v_inuserid   = dbox.getString("d_inuserid");
		           v_name       = dbox.getString("d_name");
		           v_cnt        = dbox.getInt("d_cnt");
		           v_indate     = dbox.getString("d_indate");
		           v_title      = dbox.getString("d_title");
		           v_seq        = dbox.getInt("d_seq");
		           v_types      = dbox.getString("d_types");
		           v_readcnt    = dbox.getInt("d_readcnt");
		           v_upfilecnt  = dbox.getInt("d_filecnt");
		           v_totalpage  = dbox.getInt("d_totalpage");
		           v_rowcount   = dbox.getInt("d_rowcount");  
		           v_categorycd = dbox.getString("d_categorycd");
		           v_categorynm = dbox.getString("d_categorynm");
		           v_isopen     = dbox.getString("d_isopen");
		           v_repstatus  = dbox.getString("d_okyn1");
		           v_totalrowcount = dbox.getInt("d_totalrowcount");
		
		            // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
		
		            if (!v_searchtext.equals("")&&v_select.equals("title")) {
		                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
		            } else if(!v_searchtext.equals("")&&v_select.equals("name")){
		                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
		            }
		            
		            if(v_categorynm.equals("")){
		            	v_categorynm = "기타";
		            }
		            /*
		            if(v_repstatus.equals("3")){
		            	v_repstatus = "답변완료";
		            } else if(v_repstatus.equals("2")){
		                v_repstatus = "답변준비중";
		            } else {
		            	v_repstatus = "열람전";
		            }
		            */

					%>
			
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td name="w_num" class="linefirst"><%=v_dispnum%></td>
						<td name="w_category"><%=v_categorynm%></td>
						<td name="w_subject" class="title" id="bboardfocus<%=i%>">
							<%if (v_isopen.equals("N")){%>
              	<img src="/images/2012/sub/page4/icon_lock.gif" class="lock" alt="잠금" align="absmiddle">
              <% } %>
							<%if (v_isopen.equals("N") && !s_userid.equals(v_inuserid)){%>
              	<a href="javascript:alert('비밀글은 작성자만 볼 수 있습니다.');" onfocus="boardfocus('b',<%=i%>)" onblur="boardfocus('b',<%=i%>)" tabindex="<%="2"+i+"1"%>" title="<%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%> <%=v_title%>"><%=StringManager.formatTitle(v_title, 48)%></a>
              <% } else { %>
              	<a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_inuserid%>','<%=v_types%>')" onfocus="boardfocus('b',<%=i%>)" onblur="boardfocus('b',<%=i%>)" tabindex="<%="2"+i+"1"%>" title="<%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%> <%=v_title%>"><%=StringManager.formatTitle(v_title, 48)%></a>
              <% } %>
		                    
              <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
              	<img src="/images/portal/homepage_renewal/support/icon_n.gif" alt="n" />
              <% } %>
						</td>
						<td name="w_writer"><%=v_name%></td>
						<td name="w_writingdate"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%></td>
						<td name="w_answer">
							<%if(v_repstatus.equals("3")){ %>
		        		<button type="button" class="btn_nomal_darkmint" tabindex="<%="2"+i+"2"%>" title="답변이 완료 되었습니다. 확인해주세요">답변완료</button>
		        	<%}else if(v_repstatus.equals("2")){ %>
		        		<h6>준비중</h6>
		        	<%}else{ %>
		        		<h6>미처리중</h6>
		        	<%} %>
						</td>
						<td name="w_count"><%=v_cnt%></td>
					</tr>
					<%
						} 
					} else {
					%>
					 <tr><td colspan="7" class="nodata linefirst">등록된 게시물이 없습니다.</td></tr>
     			<% } %>
				</table>
			</div>
			<div class="boardoption"><button type="button" onclick="javascript:insertPage();" tabindex="301" class="btn_small_lineblue" title="질문글 또는 지식글을 작성할 수 있습니다">글 작성</button></div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
