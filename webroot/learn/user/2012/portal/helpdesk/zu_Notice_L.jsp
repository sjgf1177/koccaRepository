<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.dunet.common.util.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","1");

    ArrayList list1 = null;
    ArrayList list2 = null;

    int     v_tabseq   = box.getInt("p_tabseq");
    int     v_seq      = box.getInt("p_seq");     
    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
    String v_luserid   = "";
    String v_gubun_view = "";
    String v_loginyn    = "";           // 로그인유무
    String v_useyn      = "";           // 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
    String v_isall      = "";           // 전체 공지 유무
    String v_onoffgubun = "";           // 온오프구분
    int v_popwidth   = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    
    String v_upfile    ="";
    //String v_realfile  ="";
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");
	int v_pagesize = box.getInt("p_pagesize");

	int	 v_totalrowcount =  0;

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    list2 = (ArrayList)request.getAttribute("selectNoticeList");
    
    String left_menu = "helpdesk";
   
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT type="text/javascript" LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

	function pagesize(pageSize) {
		document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
		document.nform1.p_pageno.value = 1;
		document.nform1.p_pagesize.value = pageSize;
		document.nform1.p_process.value = "List";
		document.nform1.submit();
	}

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

//-->
</SCRIPT>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" action="/" onsubmit="javascript:select();">
  <input type = "hidden" name = "p_process">
  <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
  <input type = "hidden" name = "p_seq"    >
  <input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>">
  
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>공지사항</u></td>
			</tr>
		</table>
		<div class="concept">
			<div><img src="/images/2012/sub/page4/title/notice.gif" alt="한국콘텐츠아카데미에서 전하는 회원님들을 위한 다양한 서비스"></div>
			<ul class="boardsearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" tabindex="171" onfocus="typechoiceopen('1')"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" name="p_search" id="choicetype" value="adtitle">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제','adtitle')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<dd onclick="searchtype('내용','adcontent')" tabindex="173" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd>
					</dl>
				</li>
				<li class="insert"><input type="text" tabindex="174" name="p_searchtext" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:select();" tabindex="175" title="검색을 시작합니다"><img  src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0" summary="한국콘텐츠아카데미 새소식 목록입니다">
					<colgroup><col width="50"><col width="90"><col width="*"><col width="100"><col width="50"></colgroup>
					<tr class="listhead">
						<th>번호</th>
						<th>분류</th>
						<th>제목</th>
						<th>작성일</th>
						<th>조회</th>
					</tr>
					<%
					if(list2.size() != 0){
						for( int i = 0; i < list2.size(); i++) {
							DataBox dbox = (DataBox)list2.get(i);
							v_seq      = dbox.getInt("d_seq");
							v_cnt      = dbox.getInt("d_cnt");
							v_adtitle  = dbox.getString("d_adtitle");
							v_addate   = dbox.getString("d_addate");
							v_adname   = dbox.getString("d_adname");
							v_upfilecnt = dbox.getInt("d_filecnt");
							v_dispnum   = dbox.getInt("d_dispnum");
							v_totalpage = dbox.getInt("d_totalpage");
							v_totalrowcount = dbox.getInt("d_totalrowcount");
							v_rowcount  = dbox.getInt("d_rowcount");
							v_isall     = dbox.getString("d_isall");
							v_onoffgubun = dbox.getString("d_onoffgubun");
								if( v_onoffgubun.equals("C") ) {
								v_onoffgubun = "<img src=\"/images/2012/sub/page4/icon/icon_online.gif\" alt=\"온라인\">";
								/* } else if( v_onoffgubun.equals("C") ) {
									v_onoffgubun = "<img src=\"/images/2012/sub/page4/icon/icon_offline.gif\" alt=\"오프라인\">";*/
								} else {
									v_onoffgubun = "<img src=\"/images/2012/sub/page4/icon/icon_offline.gif\" alt=\"오프라인\">";
								}
								
								v_addate = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");
								v_adtitle = StringUtil.getByteString(v_adtitle, 100);
					%>
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%=v_dispnum%></td>
						<td><%=v_onoffgubun %></td>
						<td class="title" id="bboardfocus<%=i%>">
							<a href="javascript:selectView(<%=v_seq%>)" onfocus="boardfocus('b',<%=i%>)" onblur="boardfocus('b',<%=i%>)" tabindex="<%=i+176%>" title="[<%=v_addate%>] <%=v_adtitle%>"><%=v_adtitle%></a>
						</td>
						<td><%=v_addate%></td>
						<td><%=v_cnt%></td>
					</tr>
					<% } }else{%>
						<tr><td colspan="7" class="nodata linefirst">등록된 게시물이 없습니다.</td></tr>
					<% }%>
				</table>
			</div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
	<input type="submit" class="close" title="실행">
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>

