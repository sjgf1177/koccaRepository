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

<%@ include file="/learn/user/portal/include/top.jsp"%>
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


//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post">
    <input type = "hidden" name="p_process"    value = "<%=v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="s_classify"   value = "<%=ss_classify %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_bookcode"   value = "<%=v_bookcode%>">

            <style>
            table.booknavi { width:100%; border-collapse:collapse; margin-top:30px; }
            table.booknavi td { padding-bottom:10px; }
            div.booktip { margin-bottom:20px; border-top:1px solid #dddddd; border-bottom:1px solid #dddddd; }
            table.bookinfor { width:100%; border-collapse:collapse; }
            table.bookinfor td.rightabout { vertical-align:top; }
            table.bookinfor td img { width:133px; height:152px; border:2px solid #efefef; }
            table.bookinfor td table.inabout { width:100%; border-collapse:collapse; }
            table.bookinfor td table.inabout th { background-color:#ffffff; border:1px solid #dddddd; font-weight:bold; }
            table.bookinfor td table.inabout td { background-color:#ffffff; border:1px solid #dddddd; text-indent:10px; }
            table.bookinfor td table.inabout td.booktitle { font-family:"dotum","sans-serif"; font-size:16px; color:#3473ad; padding:10px 0px 10px 0px; font-weight:bold; text-indent:5px; }
            </style>
            <table class="booknavi" cellpadding="0" cellspacing="0">
            	<colgroup><col width="50%"><col width="*"></col></colgroup>
            	<tr>
            		<td style="text-align:left;"><img src="/images/portal/homepage_renewal/support/stitle_08.gif" alt="교재안내"></td>
            		<td style="text-align:right;">Home > 학습지원센터 > <strong>교재안내</strong></td>
            	</tr>
            </table>
            <div class="booktip"><img src="/images/portal/studying/book_borimg.gif" alt="과정수강시 필요한 교재 및 콘텐츠진흥원 발생 도서를 소개합니다." /></div>
            <table class="bookinfor" cellpadding="0" cellspacing="0">
            	<colgroup><col width="150"><col width="*"></col></colgroup>
            	<tr>
            		<td style="text-align:left;"><img src="<%=v_image_view2%>"></td>
            		<td class="rightabout">
            			<table class="inabout" cellpadding="0" cellspacing="0">
            				<colgroup><col width="100"><col width="*"></col></colgroup>
            				<tr>
            					<td colspan="2" class="booktitle"><%=v_bookname%></td>
            				</tr>
            				<tr>
            					<th>저자</th>
            					<td><%=v_author%></td>
            				</tr>
            				<tr>
            					<th>ISBN</th>
            					<td><%=v_isbn %></td>
            				</tr>
            				<tr>
            					<th>판매처</th>
            					<td>
            						<%=v_book_site %>
				                    <% if( !v_book_url.equals("") ) { %> 
				                    	<a href="<%=v_book_url %>" style="color:#627ABA;" target="_blank">[바로가기]</a>
				                    <% } else { %>
				                    	&nbsp;
				                    <% } %>
            					</td>
            				</tr>
            			</table>
            		</td>
            	</tr>
            </table>
            <style>
             div.subtitleA { margin-top:30px; }
             div.subcontent { margin-top:15px; margin-left:15px; }
             div.inbottom { margin-top:30px; margin-bottom:30px; border-top:1px solid #dddddd; padding-top:10px; text-align:right; }
            </style>
            <div class="subtitleA"><img src="/images/portal/common/stit_writerinfo.gif" alt="저자소개"></div>
            <div class="subcontent"><%=v_writer %></div>
            <div class="subtitleA"><img src="/images/portal/common/stit_bookinfo.gif" alt="책소개"></div>
            <div class="subcontent"><%=v_intro%></div>
            <div class="subtitleA"><img src="/images/portal/common/stit_list.gif" alt="목차"></div>
            <div class="subcontent"><%=v_content %></div>

            <div class="inbottom"><a href="javascript:listBook();"><span>목록</span></a></div>            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->