<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    box.put("topmenu","5");
    box.put("submenu","7");
    
    ArrayList list1 = null;
    ArrayList list2 = null;

    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    String  v_selSeminarGubun   = box.getString("p_selSeminarGubun");
    
    int v_pageno    = box.getInt("p_pageno");
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
	int v_pagesize = box.getInt("p_pagesize");
    
    int    v_seq     = 0;              // 일련번호
    int    v_cnt     = 0;              // 조회수

    String v_title     = "";           // 제목
    String v_content   = "";           // 내용
    String v_indate    = "";           // 등록일
    String v_name      = "";           // 등록자
    String v_startdate = "";           // 이벤트 시작일
    String v_enddate   = "";           // 이벤트 시작일
    String v_winneryn  = "";           // 담청자 여부
    String v_isall     = "";
    String v_status    = "";
    String v_bannerimg = "";
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    
    list1 = (ArrayList)request.getAttribute("selectList1");
    list2 = (ArrayList)request.getAttribute("selectList2");
   
%>    
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

    function viewPassContent(seq) {

    	window.open("about:blank", "popUpView", "top=0, left=0, width=400, height=330, status=no, resizable=no, scrollbars=auto");

        document.nform1.action="/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectViewPass";
        document.nform1.p_seq.value = seq;
        document.nform1.target="popUpView";
        document.nform1.submit();
        
        document.nform1.target="_self";
    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();" action="">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
    <input type = "hidden" name = "p_seq"    >
    
   <div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계시는 콘텐츠의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>이벤트</u></td>
			</tr>
		</table>
		<div class="concept">
			<div><img src="/images/2012/sub/page4/title/event.gif" alt="한국콘텐츠아카데미에서 진행하는 공개 이벤트에 참여해보세요"></div>
			<ul class="boardsearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" tabindex="171" onfocus="typechoiceopen('1')"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" id="choicetype" value="subject">
					<input type="hidden" name="p_search" value="title">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<dd onclick="searchtype('내용')" tabindex="173" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd> 
					</dl>
				</li>
				<li class="insert"><input type="text" tabindex="174" name="p_searchtext" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:select();" tabindex="175" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
		</div>
		<div class="event">
			<img src="/images/2012/sub/page4/subject_playevent.gif" class="category" alt="진행중인 이벤트">
			<table class="eventlist" cellpadding="0" cellspacing="0" summary="현재 진행중인 이벤트의 목록입니다">
				<colgroup><col width="210"><col width="280"><col width="210"><col width="280"></colgroup>
				<tr>
				<%
					if(list1.size() != 0){
						for( int i = 0; i < list1.size(); i++) {
						    DataBox dbox     = (DataBox)list1.get(i);
						    
						    v_seq       = dbox.getInt("d_seq");
						    v_cnt       = dbox.getInt("d_cnt");
						    v_title     = dbox.getString("d_title");
						    v_startdate = dbox.getString("d_strdate");
						    v_enddate   = dbox.getString("d_enddate");
						    v_indate    = dbox.getString("d_indate");
						    v_name      = dbox.getString("d_name");
						    v_winneryn  = dbox.getString("d_winneryn");
						    v_isall     = dbox.getString("d_isall");
						
						    v_dispnum        = dbox.getInt("d_dispnum");
						    v_totalpage      = dbox.getInt("d_totalpage");
						    v_rowcount       = dbox.getInt("d_rowcount");
						    v_bannerimg       = dbox.getString("d_bannerimg");
						    
						    if(v_winneryn.equals("C")){
						    	v_winneryn = "미발표";
						    	v_status = "완료";
						    } else if(v_winneryn.equals("Y")){
						        v_winneryn = "<a href='javascript:viewPassContent("+v_seq+")'><img src='/images/portal/homepage_renewal/library/btn_dok.jpg' width='90' height='21' alt='당첨자확인' /></a>";
						        v_status = "완료";
						    } else if(v_winneryn.equals("N")){
						    	v_winneryn = "미발표";
						    	v_status = "진행";
						    } else if(v_winneryn.equals("A")){
						    	v_winneryn = "미발표";
		                        v_status = "진행";
		                    } 
						    
						    
						    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
						    	/*
						    if (!v_searchtext.equals("")) {
						    	v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
						    } 
						    	*/
				%>      
					<th><div><img src="http://edu.kocca.or.kr/upload/event/<%=v_bannerimg%>" alt="<%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%> <%=v_title%>"></div></th>
					<td>
						<h4><%=v_title%></h4>
						<h5><img src="/images/2012/sub/page4/icon_eventdate.gif" alt="이벤트기간"> <%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%></h5>
						<p>
							<a href="javascript:selectView(<%=v_seq%>)" tabindex="<%=i+180%>" title="<%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%> <%=v_title%>에 대한 이벤트 상세내용을 확인합니다"><img src="/images/2012/sub/page4/btn_eventdetail.gif" alt="이벤트상세보기"></a>
							<!-- <a href="#"><img src="/images/2012/sub/page4/btn_notchoice.gif" alt="미발표"></a> -->
						</p>
					</td>
					
				<% }} %>
				
				<% if (list1.size() == 0) { %>					
					<td colspan="4" class="noevent">
						현재 진행중인 이벤트가 없습니다.
					</td>
				<% } %>		
				<% if (list1.size() % 2 == 1) { %>	
					<th></th><td>&nbsp;</td>		
				<% } %>			
				</tr>
			</table>
			
			<img src="/images/2012/sub/page4/subject_stopevent.gif" class="category" alt="종료된 이벤트">
			<table class="eventlist" cellpadding="0" cellspacing="0" summary="현재 진행중인 이벤트의 목록입니다">
				<colgroup><col width="210"><col width="280"><col width="210"><col width="280"></colgroup>
				<tr>
				
				 <%
					if(list2.size() != 0){
						for( int i = 0; i < list2.size(); i++) {
						    DataBox dbox     = (DataBox)list2.get(i);
						    
						    v_seq       = dbox.getInt("d_seq");
						    v_cnt       = dbox.getInt("d_cnt");
						    v_title     = dbox.getString("d_title");
						    v_startdate = dbox.getString("d_strdate");
						    v_enddate   = dbox.getString("d_enddate");
						    v_indate    = dbox.getString("d_indate");
						    v_name      = dbox.getString("d_name");
						    v_winneryn  = dbox.getString("d_winneryn");
						    v_isall     = dbox.getString("d_isall");
						
						    v_dispnum        = dbox.getInt("d_dispnum");
						    v_totalpage      = dbox.getInt("d_totalpage");
						    v_rowcount       = dbox.getInt("d_rowcount");
						    v_bannerimg       = dbox.getString("d_bannerimg");
						    
						    /*
						    if(v_winneryn.equals("C")){
						    	v_winneryn = "미발표";
						    	v_status = "완료";
						    } else if(v_winneryn.equals("Y")){
						        v_winneryn = "<a href='javascript:viewPassContent("+v_seq+")'><img src='/images/portal/homepage_renewal/library/btn_dok.jpg' width='90' height='21' alt='당첨자확인' /></a>";
						        v_status = "완료";
						    } else if(v_winneryn.equals("N")){
						    	v_winneryn = "미발표";
						    	v_status = "진행";
						    } else if(v_winneryn.equals("A")){
						    	v_winneryn = "미발표";
		                        v_status = "진행";
		                    } 
						    */
						    
						    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
						    /*
						    if (!v_searchtext.equals("")) {
						    	v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
						    } 
						    */
					%>      
			
					<th><div><img src="http://edu.kocca.or.kr/upload/event/<%=v_bannerimg%>" alt="<%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")+"~"+FormatDate.getFormatDate(v_enddate, "MM.dd")%> <%=v_title%>"></div></th>
					<td>
						<h4><%=StringManager.formatTitle(v_title, 52)%></h4>
						<h5><img src="/images/2012/sub/page4/icon_eventfinish.gif" alt="이벤트종료"> <%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")+"~"+FormatDate.getFormatDate(v_enddate, "MM.dd")%></h5>
						<p>
							<a href="javascript:selectView(<%=v_seq%>)" tabindex="<%="2"+i+"1"%>" title="[종료된 이벤트] <%=v_title%>에 대한 이벤트 상세내용을 확인합니다"><img src="/images/2012/sub/page4/btn_eventdetail.gif" alt="이벤트상세보기"></a>
							<% if(v_winneryn.equals("Y")){ %>
							<a href="javascript:viewPassContent(<%=v_seq%>)" tabindex="<%="2"+i+"2"%>" title="[종료된 이벤트] <%=v_title%>에 대한 당첨자를 확인합니다 (새창)"><img src="/images/2012/sub/page4/btn_choicepeople.gif" alt="당첨자발표"></a> 
							<% } %>
						</p>
					</td>
					<% if ((i+1)%2 == 0) {%> </tr><tr> <% } %>
					
					<% }} %>
				</tr>
			</table>
			<div class="clearL"></div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
