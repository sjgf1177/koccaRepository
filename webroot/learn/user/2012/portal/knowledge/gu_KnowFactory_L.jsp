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
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","8");

    String v_process= box.getString("p_process");
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_select      = box.getString("p_select");

    int v_seq = 0, v_readcnt = 0,  i = 0,  v_cnt = 0;
    String  v_inuserid    = "", v_position = "", v_indate = "",  v_title = "", tLink = "",  v_types = "" ,v_name="";
    String  v_categorycd     = box.getString("p_categorycd");
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String v_classname1      = "";
    String v_classname2      = "";
    String v_repstatus  	 = "";
    String v_subjnm		  	 = "";
    String v_tabseq		  	 = "";
    String v_cate		  	 = "";  // 'A -자료실  'B - 지식팩토리 C - 과정질문방
    String v_subjyear 		 = "";  
    
    String tabimg1 ="tab01.gif";
    String tabimg2 ="tab02.gif";
    String tabimg3 ="tab03.gif";
    String tabimg4 ="tab04.gif";
    String tabimg5 ="tab05.gif";

    ArrayList list = (ArrayList)request.getAttribute("selectLastList");
    
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
	
%>

<script language="JavaScript" type="text/JavaScript">
<!--
//입력페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "InsertPage";
    //document.form1.p_pageno.value = "<%= v_pageno %>";
    document.form1.submit();
}

//상세화면 페이지로 이동
function select(seq, tabseq, userid, indate, subjyear,types) {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_indate.value     = indate;
    document.form1.p_subjyear.value     = subjyear;
    document.form1.p_tabseq.value = tabseq;
    document.form1.submit();
}


//검색내용에 맞는 리스트 페이지로 이동
function selectList(tab) {
    if (form1.p_searchtext.value == ""){
        alert("검색 할 내용을 입력해 주세요");
    }else{
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.p_pageno.value = "1";
    document.form1.submit();
    showTab(tab);
    }
}

//페이지 이동
function goPage(pageNum) {

    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.submit();
}

// Tab 선택시 동작
function showTab(clickTab)
{
   //alert(clickTab);
    g_showTab(clickTab, 2);
}

function g_showTab(tabNo, count)
{

    var clickTabNo = new Number(tabNo);
    var tabCount   = new Number(count);

    if( clickTabNo == 0 || tabCount == 0 ) return;

    for(i=1; i<=tabCount; i++)
    {
        div = document.all["tab_"+i];

        if(!div) return;

        if( clickTabNo == i ) {
            // 활성화된 tab 클릭시 return
            if(div.style.display == "") return;
            div.style.display = ""
        }
        else {
            // 선택되지 않는 탭들은 깜빡임 방지.
            if(div.style.display != "none") {
                div.style.display = "none";
            }
        }
    }
}
//-->
</script>
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<form name = "form1" method = "post" action="">
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td></td>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 지식팩토리 > <u><%=sub_navi %></u></td>
			</tr>
		</table>
		<div class="concept e01">
			<div><img src="/images/2012/sub/page6/title/common.gif" alt="여러분이 궁금한 걸 물어보세요! 분야별 전문가님들이 답변을 달아드립니다."></div>
			<ul class="factorysearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" onfocus="typechoiceopen('1')" tabindex="171"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" id="choicetype" value="subject">
					<input type="hidden" name="p_select" value="title">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<!-- <dd onclick="searchtype('과정')" tabindex="173" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">과정</dd> -->
					</dl>
				</li>
				<li class="insert"><input type="text" name="p_searchtext" tabindex="175" class="insearch" onfocus="this.style.background='#ffffff'"  value = "<%=v_searchtext%>"></li>
				<li class="output"><a href="javascript:selectList();" tabindex="176" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></li>
			</ul>
			  <input type = "hidden" name = "p_process"     value = "<%=v_process%>">
		      <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
		      <input type = "hidden" name = "p_seq"         value = "">
		      <input type = "hidden" name = "p_userid"      value = "">
		      <input type = "hidden" name = "p_tabseq"   	value = "">
		      <input type = "hidden" name = "p_types"       value = "">
		      <input type = "hidden" name = "p_categorycd"  value = "<%=v_categorycd%>">
		      <input type = "hidden" name = "p_indate"  value = "">
		      <input type = "hidden" name = "p_subjyear"  value = "">
			<ul class="pagetab">
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage" tabindex="177" title="지식팩토리의 전체 목록을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/total_<%=full_toggle %>.png" alt="종합"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=B0" tabindex="178" title="방송영상 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/cast_<%=sub1_toggle%>.png" alt="방송영상"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=G0" tabindex="179" title="게임개발 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/game_<%=sub2_toggle%>.png" alt="게임개발"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=K0" tabindex="180" title="문화콘텐츠 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/culture_<%=sub3_toggle%>.png" alt="문화콘텐츠"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=MINE" tabindex="181" title="회원님이 작성한 글을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/mywrite_<%=sub4_toggle%>.png" alt="내가 작성한 글"></a></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0" summary="지식팩토리의 목록입니다">
					<colgroup><col width="50"><col width="50"><col width="*"><col width="300"><col width="80"><col width="50"><col width="50"></colgroup>
					<tr class="listhead">
						<th id="w_num">번호</th>
						<th id="w_subject" colspan="2">주제</th>
						<th id="w_course">관련과정</th>
						<th id="w_date">등록일</th>
						<th id="w_count">조회</th>						
					</tr>
					<%
	        if(list.size() != 0){
            for(i = 0; i < list.size(); i++) {
							DataBox dbox = (DataBox)list.get(i);
							v_dispnum    = dbox.getInt   ("d_dispnum");
							v_cate    	 = dbox.getString("d_cate");
							v_inuserid   = dbox.getString("d_userid");
							v_name       = dbox.getString("d_name");
							v_cnt        = dbox.getInt   ("d_cnt");
							v_indate     = dbox.getString("d_indate");
							v_subjyear   = dbox.getString("d_subjyear");
							v_title      = dbox.getString("d_title");
							v_seq        = dbox.getInt   ("d_seq");
							v_types      = dbox.getString("d_types");
							v_tabseq      = dbox.getString("d_tabseq");
							v_totalpage  = dbox.getInt   ("d_totalpage");
							v_rowcount   = dbox.getInt   ("d_rowcount");
							v_repstatus  = dbox.getString("d_okyn");
							v_subjnm  	= dbox.getString("d_subjnm");
							
							// 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
							if (!v_searchtext.equals("")&&v_select.equals("title")) {
								v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
							} else if(!v_searchtext.equals("")&&v_select.equals("name")){
								v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
							}
					%>
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td name="w_num" class="linefirst"><%=v_dispnum%></td>
						<td class="icontype">
						
						
						<%if(v_cate.equals("B") && v_repstatus.equals("4") ){ %>
						<img src="/images/2012/sub/page6/icon/icon_post.gif" alt="게시">
						<%} else if(v_cate.equals("C")) { 
							if (v_repstatus.equals("2") || v_repstatus.equals("3")) {%> 
							<img src="/images/2012/sub/page6/icon/icon_answer.gif" alt="답변완료">
							<% }  else { %>
							<img src="/images/2012/sub/page6/icon/icon_wait.gif" alt="대기">	
						<% }} %>
						
						<%if(v_cate.equals("A")){ %>
							<img src="/images/2012/sub/page6/icon/icon_data.gif" alt="자료방">
						<% } %>						
						</td>
						<td name="w_subject" class="title courselist" id="bboardfocus<%=i%>"><a href="javascript:select('<%=v_seq%>','<%=v_tabseq%>','<%=v_inuserid%>','<%=v_indate%>','<%=v_subjyear%>','<%=v_cate%>')" tabindex="<%=i+200%>"  onfocus="boardfocus('b',<%=i%>)"  onblur="boardfocus('b',<%=i%>)" title="[<%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%>] <%=v_title%>"><%=v_title%></a></td>
						<td name="w_course" class="coursename"><%=StringManager.formatTitle(v_subjnm, 32)%></td>
						<td name="w_date"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%></td>
						<td name="w_count"><%=v_cnt%></td>
					</tr>
					<%} }else{ %>					
					<tr>
						<td colspan="6" class="nodata linefirst">등록된 게시물이 없습니다.</td>
					</tr>
					<%}%>
				</table>
			</div>
			<div class="boardoption"><button type="button" tabindex="221" onclick="javascript:insertPage();" class="btn_small_lineblue" title="질문글 또는 지식글을 작성할 수 있습니다">글 작성</button></div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
</form>	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
	
