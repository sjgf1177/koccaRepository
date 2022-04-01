<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.book.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","5");

    String  v_process = box.getString("p_process");
    int v_pageno         = box.getInt("p_pageno");

    String ss_classify   = box.getStringDefault("s_classify","ALL");
    String v_searchtext  = box.getString("p_searchtext");
    String v_bookcode      = box.getString("p_bookcode");          // 교재코드

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
    String v_isbn          = "";          // ISBN

    String upload_url = conf.getProperty("url.upload");
    DecimalFormat  df = new DecimalFormat("###,##0");

    DataBox dbox = (DataBox)request.getAttribute("selectBook");

    if (dbox != null) {
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
        v_isbn          = dbox.getString("d_isbn");
        

        if (v_newfilename1.equals("")) v_image_view1 = "/images/user/game/book/blank_book1.gif";
        else                           v_image_view1 = v_newfilename1;
        if (v_newfilename2.equals("")) v_image_view2 = "/images/user/game/book/blank_book2.gif";
        else                           v_image_view2 = v_newfilename2;

        v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");
        v_intro   = StringManager.replace(v_intro,"\n","<br>");
        v_writer  = StringManager.replace(v_writer,"\n","<br>");
        v_content = StringManager.replace(v_content,"\n","<br>");
        v_review  = StringManager.replace(v_review,"\n","<br>");
     }

     String s_userid = box.getSession("userid");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--

function listBook() {
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

// 교재신청
function whenBookPropose(bookcode) {
/*
    document.form1.p_bookcode.value = bookcode;

     var new_Open = window.open("","BookBill","scrollbars=auto,width=700,height=430,resizable=no");
     document.form1.target = "BookBill";
     document.form1.p_process.value = "bookBillPage";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.submit();
*/

    farwindow = window.open("", "winInicis", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
    document.fbill.p_bookcode.value = bookcode;
    document.fbill.target = "winInicis";
    document.fbill.submit();

    farwindow.window.focus();

}

	//장바구니 담기
	function whenBookBasket(bookcode, price) {

	   if(!confirm("장바구니에 담으시겠습니까?")){
		 return;
	   }

	   document.fcart.target = "_self";
	   document.fcart.p_price.value = price;
	   document.fcart.p_bookcode.value = bookcode;
	   document.fcart.p_process.value = "InsertBasketBook";
	   document.fcart.action = "/servlet/controller.account.AccountManagerServlet";
	   document.fcart.submit();
	}
	

	function whenSearch() {
	    document.form1.p_pageno.value = 1;
	    document.form1.target = "_self";
	    document.form1.action = "/servlet/controller.book.BookServlet";
	    document.form1.p_process.value = "listPage";
	    document.form1.submit();
	}


//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="">
    <input type = "hidden" name="p_process"    value = "<%=v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="s_classify"   value = "<%=ss_classify %>">
    <%-- <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>"> --%>
    <input type = "hidden" name="p_bookcode"   value = "<%=v_bookcode%>">
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
					<p onclick="typechoiceopen('1')" tabindex="121" onfocus="typechoiceopen('1')"><span id="choicename">교재명</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" id="choicetype" value="subject">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('교재명')" tabindex="122" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">교재명</dd>
						<!-- <dd onclick="searchtype('내용')" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd> -->
					</dl>
				</li>
				<li class="insert"><input type="text" name="p_searchtext" tabindex="123" class="insearch" onkeypress="javascript:fnKeyPressEnter(event, 'whenSearch');" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:whenSearch();" tabindex="124" title="검색어를 입력 후 클릭해주세요"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
		</div>
		<div class="bookshowback"><button type="button" tabindex="125" onclick="javascript:listBook();" class="btn_small_gray" title="교재 목록으로 돌아갑니다">목록으로</button></div>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="126" title="이 서적은?">이 서적은?</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="127" title="이 서적의 저자를 소개합니다">저자를 소개합니다</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="128" title="서적의 구성을 간략하게 소개합니다">이 서적의 구성</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="129" title="이 서적의 목차를 확인합니다">이 서적의 목차</a></dd>
		</dl>
		<table class="linecolor" cellspacing="0" cellpadding="0" summary="4색 구분라인">
			<colgroup><col width="25%"><col width="25%"><col width="25%"><col width="*"></colgroup>
			<tr>
				<td class="color1"></td>
				<td class="color2"></td>
				<td class="color3"></td>
				<td class="color4"></td>
			</tr>
		</table>
		<div class="bookdetail">
			<table class="bookabout" cellspacing="0" cellpadding="0" id="skip01" summary="교재정보입니다">
				<colgroup><col width="130"><col width="*"></colgroup>
				<tr>
					<td><img src="http://edu.kocca.or.kr<%=v_image_view2%>" class="book" alt="<%=v_bookname%>"></td>
					<th>
						<h1><%=v_bookname%></h1>
						<h3>저자 : <%=v_author%></h3>
						<h4>IBSN : <%=v_isbn %></h4>
						<h4>판매처 : <%=v_book_site %>&nbsp;
						<% if( !v_book_url.equals("") ) { %> 
							<a href="<%=v_book_url %>" tabindex="176" target="_blank" title="판매처 바로가기"><img src="/images/2012/sub/page4/btn_golink.gif" alt="바로가기" align="absmiddle"></a>
	          <% } else { %>
	          	&nbsp;
				    <% } %>            	
						</h4>
					</th>
				</tr>
			</table>
			<table class="aboutrable" cellspacing="0" cellpadding="0" id="skip02" summary="저자를 소개합니다">
				<colgroup><col width="80"><col width="*"></colgroup>
				<tr>
					<th><img src="/images/2012/sub/page4/subject_writer_about.gif" alt="저자소개"></th>
					<td>
						<!-- <p>[문혜성]</p> -->
						<%=v_writer %>
					</td>
				</tr>
			</table>
			<table class="aboutrable" cellspacing="0" cellpadding="0" id="skip03" summary="교재를 설명합니다">
				<colgroup><col width="80"><col width="*"></colgroup>
				<tr>
					<th><img src="/images/2012/sub/page4/subject_book_about.gif" alt="책소개"></th>
					<td>
						<%=v_intro%>
					</td>
				</tr>
			</table>
			<table class="aboutrable" cellspacing="0" cellpadding="0" id="skip04" summary="교재 목차입니다">
				<colgroup><col width="80"><col width="*"></colgroup>
				<tr>
					<th><img src="/images/2012/sub/page4/subject_index.gif" alt="목차"></th>
					<td>
						<%=v_content %>
					</td>
				</tr>
			</table>
		</div>
	</div>        
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
