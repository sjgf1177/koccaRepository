<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%  
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","1");

    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_tabseq   = box.getInt("p_tabseq");
    int v_seq      = box.getInt("p_seq");

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
    String v_onoffgubun = "";			// 온라인/오프라인 구분
    int v_popwidth   = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치

    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용

    DataBox dbox = (DataBox)request.getAttribute("selectNotice");

    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");
        v_startdate = dbox.getString("d_startdate");
        v_enddate   = dbox.getString("d_enddate");
        v_adtitle   = dbox.getString("d_adtitle");
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");
        v_adname    = dbox.getString("d_adname");
        v_luserid   = dbox.getString("d_luserid");
        v_cnt       = dbox.getInt("d_cnt");
        v_loginyn   = dbox.getString("d_loginyn");
        v_useyn     = dbox.getString("d_useyn");
        v_grcodecd  = dbox.getString("d_grcodecd");
        v_popwidth  = dbox.getInt("d_popwidth");
        v_popheight = dbox.getInt("d_popheight");
        v_popxpos   = dbox.getInt("d_popxpos");
        v_popypos   = dbox.getInt("d_popypos");
        v_popup     = dbox.getString("d_popup");
        v_uselist   = dbox.getString("d_uselist");
        v_useframe  = dbox.getString("d_useframe");
        if(dbox.getStringDefault("d_onoffgubun","C").equals("C")){
        	v_onoffgubun = "온라인";	
        }else{
        	v_onoffgubun = "오프라인";
        }
        

        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");

     }

    if (v_gubun.equals("Y")) v_gubun_view = "전체";
    else if (v_gubun.equals("N")) v_gubun_view = "일반";
    else if (v_gubun.equals("P")) v_gubun_view = "팝업";

    if (v_uselist.equals("")) v_uselist = "N";
    if (v_useframe.equals("")) v_useframe = "N";

    if(v_loginyn.equals("Y")) v_loginyn = "로그인후";
    else if(v_loginyn.equals("N")) v_loginyn = "로그인전";
    else v_loginyn = "전체";

    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";

%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<SCRIPT type="text/javascript">

    function listnotice(){
    	document.nform2.p_process.value = "List";
        document.nform2.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="nform2" method="post" action="/">
<input type = "hidden" name = "p_process" >
<input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
<input type = "hidden" name = "p_search" value = "<%=v_search %>">
<input type = "hidden" id="choicetype" value="subject">
	
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
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제','title')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<dd onclick="searchtype('내용','content')" tabindex="173" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd>
					</dl>
				</li>
				
				<li class="insert"><input type="text" tabindex="174" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><input type="image" src="/images/2012/sub/page2/btn_course_search.gif" tabindex="175" title="검색어를 입력 후 클릭해주세요" alt="과정검색"></li>
			</ul>
		</div>
		<div class="viewbar"><h6><b><%=v_adtitle%></b></h6></div>
		<div class="viewcontent">
			<%=StringManager.removeHTMLHead(v_adcontent)%>
			<%
			Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
			Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
			String i_boardStyle        = "board_view";                                // 보드 스타일
			%>
			<%@ include file="/learn/user/include/zu_MultiAttach_2012_R.jsp" %>
			<table class="about" cellpadding="0" cellspacing="0">
				<colgroup><col width="34"><col width="*"><col width="65"><col width="70"><col width="36"><col width="30"></colgroup>
				<tr>
					<th>분류</th>
					<td><%=v_onoffgubun %></td>
					<th>작성일자</th>
					<td><%=v_addate%></td>
					<th>조회</th>
					<td><%=v_cnt%></td>
				</tr>
			</table>
		</div>
		<div class="viewfooter">
			<button type="button" onclick="javascript:listnotice()" tabindex="200" class="btn_small_lineblue" title="목록으로 돌아갑니다">목록으로</button>
		</div>
	</div>
	<input type="submit" class="close" title="실행">
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>

