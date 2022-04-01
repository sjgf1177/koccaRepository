<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.book.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","5");

    String  v_process = box.getString("p_process");
    int v_pageno         = box.getInt("p_pageno");
    int row              = 10 ;
    if (v_pageno == 0)  v_pageno = 1;
    
    
	int v_pagesize = box.getInt("p_pagesize");	
	

    String  ss_classify   = box.getStringDefault("s_classify","ALL");
    String  v_searchtext  = box.getString("p_searchtext");

    int    i = 0;
    String v_bookcode      = "";          // 교재코드
    String v_classify      = "";          // 분류
    String v_codenm        = "";          // 분류명
    String v_bookname      = "";          // 교재명
    int    v_price         = 0;           // 정가
    int    v_dis_price     = 0;           // 판매가
    String v_intro         = "";          // 교재소개
    String v_writer        = "";          // 저자소개
    String v_content       = "";          // 목차
    String v_review        = "";          // 리뷰
    String v_recom         = "";          // 추천교재여부
    String v_luserid       = "";          // 등록자
    String v_ldate         = "";          // 등록일
    String v_realfilename1 = "";          // 큰이미지   실제파일명
    String v_newfilename1  = "";          // 큰이미지   저장파일명
    String v_realfilename2 = "";          // 작은이미지 실제파일명
    String v_newfilename2  = "";          // 작은이미지 저장파일명
    String v_image_view1   = "";
    String v_image_view2   = "";
    String v_book_site     = "";          // 판매처
    String v_book_url      = "";          // 판매 주소(URL)
    String v_author        = "";          // 저자명

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount = 0;

    ArrayList list = (ArrayList)request.getAttribute("selectUserBookList");

    String upload_url = conf.getProperty("url.upload");
    DecimalFormat  df = new DecimalFormat("###,##0");


    /*------------------------ 텝 색깔 이미지 --------------------------*/
    String classify_img1 = "tab_01.gif";
    String classify_img2 = "tab_02.gif";
    String classify_img3 = "tab_03.gif";
    String classify_img4 = "tab_04.gif";
    String classify_img5 = "tab_05.gif";

    if (ss_classify.equals("ALL"))      classify_img1 = "tab_01on.gif";
    else if (ss_classify.equals("G01")) classify_img2 = "tab_02on.gif";
    else if (ss_classify.equals("G02")) classify_img3 = "tab_03on.gif";
    else if (ss_classify.equals("G03")) classify_img4 = "tab_04on.gif";
    else if (ss_classify.equals("G04")) classify_img5 = "tab_05on.gif";
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->

<SCRIPT LANGUAGE="JavaScript">
<!--
function go(index) {
     document.form1.p_pageno.value = index;
    document.form1.target = "_self";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.p_process.value = "listPage";
     document.form1.submit();
}
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
    document.form1.target = "_self";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.p_process.value = "listPage";
     document.form1.submit();
}

//상세보기
function view(bookcode) {
    document.form1.p_bookcode.value = bookcode;
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.target = "_self";
    document.form1.p_process.value = "selectPage";
    document.form1.submit();
}

// 일반/전문가, 대분류코드 선택
function whenSelect(classify){
    document.form1.s_classify.value   = classify;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.book.BookServlet';
    document.form1.p_process.value = 'listPage';

    document.form1.submit();
}

function whenSearch() {
    document.form1.p_pageno.value = 1;
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

// 교재신청
function whenBookPropose(bookcode) {
    document.form1.p_bookcode.value = bookcode;

     var new_Open = window.open("","BookBill","scrollbars=auto,width=700,height=430,resizable=no");
     document.form1.target = "BookBill";
     document.form1.p_process.value = "bookBillPage";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.submit();
}

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" method = "post" action="/">
    <input type = "hidden" name = "p_process"  value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"   value = "<%=v_pageno %>">
    <input type = "hidden" name = "s_classify" value = "<%=ss_classify%>">
    <input type = "hidden" name = "p_bookcode" value = "">
    
    	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>교재소개</u></td>
			</tr>
		</table>
		<div class="concept inspace">
			<div><img src="/images/2012/sub/page4/title/book.gif" alt="과정 수강시 필요한 교재 및 콘텐츠 진흥원 발간도서를 소개합니다. 교재를 클릭하여 판매처를 클릭하시면 도서구매 사이트로 이동합니다."></div>
			<ul class="booksearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" tabindex="171" onfocus="typechoiceopen('1')"><span id="choicename">교재명</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" id="choicetype" value="subject">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('교재명')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">교재명</dd>
						<!-- <dd onclick="searchtype('내용')" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd> -->
					</dl>
				</li>
				<li class="insert"><input type="text" name="p_searchtext" tabindex="173" class="insearch" onkeypress="javascript:fnKeyPressEnter(event, 'whenSearch');" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:whenSearch();" tabindex="174" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
		</div>
		
		<div class="booktable">
		<%
    for( i = 0; i < list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);

        v_bookcode      = dbox.getString("d_bookcode");
        v_classify      = dbox.getString("d_classify");
        v_codenm        = dbox.getString("d_codenm");
        v_bookname      = dbox.getString("d_bookname");
        v_price         = dbox.getInt   ("d_price");
        v_dis_price     = dbox.getInt   ("d_dis_price");
        v_intro         = dbox.getString("d_intro");
        v_writer        = dbox.getString("d_writer");
        v_content       = dbox.getString("d_content");
        v_review        = dbox.getString("d_review");
        v_recom         = dbox.getString("d_recom");
        v_luserid       = dbox.getString("d_luserid");
        v_ldate         = dbox.getString("d_ldate");
        v_realfilename1 = dbox.getString("d_realfilename1");
        v_newfilename1  = dbox.getString("d_newfilename1");
        v_realfilename2 = dbox.getString("d_realfilename2");
        v_newfilename2  = dbox.getString("d_newfilename2");
        v_book_site     = dbox.getString("d_book_site");
        v_book_url      = dbox.getString("d_book_url");
        v_author        = dbox.getString("d_author");

        v_dispnum   = dbox.getInt("d_dispnum");
        v_totalpage = dbox.getInt("d_totalpagecount");
        v_rowcount   = dbox.getInt("d_rowcount");  

        if (v_newfilename1.equals("")) v_image_view1 = "/images/user/game/book/blank_book1.gif";
        else                           v_image_view1 = v_newfilename1;
        if (v_newfilename2.equals("")) v_image_view2 = "/images/user/game/book/blank_book2.gif";
        else                           v_image_view2 = StringManager.replace(v_newfilename2,"\\","/");
        v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");

        if(v_author.equals("")){
        	v_author = "미입력";
        }
		%>
			<dl class="book">
				<dt><%=v_bookname%></dt>
				<dd><a href="javascript:view(<%=v_bookcode%>);" onfocus="imgfocus('b',<%=i%>)" onblur="imgfocus('b',<%=i%>)" tabindex="<%=i+180%>" title="<%=v_bookname%>에 대한 상세화면으로 이동합니다"><img src="http://edu.kocca.or.kr<%=v_image_view2%>" id="bimgfocus<%=i%>" alt="<%=v_bookname%>"></a></dd>
			</dl>
			
		<% if (((i+1)%4 == 0) && (i != 11)) %></div><div class="booktable"><% } %>
		</div>
		
		<div class="pageset inpageset">
			<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 12, v_rowcount) %>
		</div>
	</div>
	<input type="submit" class="close" title="실행">
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
