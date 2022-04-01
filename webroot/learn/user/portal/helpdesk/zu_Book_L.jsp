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

<%@ include file="/learn/user/portal/include/top.jsp"%>
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
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process"  value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"   value = "<%=v_pageno %>">
    <input type = "hidden" name = "s_classify" value = "<%=ss_classify%>">
    <input type = "hidden" name = "p_bookcode" value = "">
    
    	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	<table>   
      <tr>
      	<td>
      	
      	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/stitle_08.gif" alt="교재안내" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>교재안내</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/bar_03.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <table width="672" border="0" cellpadding="0" cellspacing="1" bgcolor="ADBBD8">
      <tr>
        <td height="34" bgcolor="F0F0F0"><table border="0" align="center" cellpadding="3" cellspacing="0">
            <tr>
              <td><span class="tit_table">
                <kocca_select:select                                       
                    name="ss_classify"                                         
                    sqlNum="0001"                                    
                    param="0073"             
                    onChange="whenSelect(this.value);"                         
                    attr= ""                                        
                    selectedValue="<%=ss_classify%>"                         
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass="mg_r12"                                
                    all="true" />
              </span></td>
                <td><img src="/images/portal/homepage_renewal/common/txt_search_in.gif" width="69" height="16" /></td>
              <td><input name="p_searchtext" type="text" class="inbox" id="p_searchtext" size="50" value="<%=v_searchtext%>" onkeypress="javascript:fnKeyPressEnter(event, 'whenSearch');"/></td>
              <td><a href="javascript:whenSearch();"><img src="/images/portal/homepage_renewal/common/btn_search_in.jpg" alt="검색" width="46" height="24" /></a></td>
            </tr>
        </table></td></tr>
    </table>
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
                else                           v_image_view2 = v_newfilename2;
                v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");

                if(v_author.equals("")){
                	v_author = "미입력";
                }
%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="2" colspan="2" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="10" cellspacing="1" bgcolor="eeeeee">
              <tr>
                <td width="130" height="130" bgcolor="#FFFFFF"><div align="center"><img src="<%=v_image_view2%>" alt="" width="133" height="152"/></div></td>
              </tr>
            </table>
        </div></td>
        <td><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="4%" height="30"><strong class="org">ㆍ</strong></td>
              <td height="30" class="btxt"><a href="javascript:view(<%=v_bookcode%>)"><%=v_bookname%></a></td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td><strong class="org">ㆍ</strong></td>
              <td width="96%" height="25">저자 : <%=v_author%></td>
            </tr>
            
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td><strong class="org">ㆍ</strong></td>
              <td height="25">판매처 : <%=v_book_site%></td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="50"><%=StringManager.formatTitle(v_intro, 100)%></td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
        </table></td>
        </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
    </table>
    <%}  
    if(i == 0 ) {
%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="2" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td>
        	<table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
              <tr>
                <td>검색된 교재가 없습니다.</td>
              </tr>
            </table>
        </td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>
    </table>
    <%} %>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">

      <tr>
        <td height="1" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" bgcolor="F7F7F7"></td>
      </tr>
    </table>
     <%= PageUtil.re_printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalpage) %>    
    
    </td>
    <td width="20">&nbsp;</td>
   
  </tr>
</table>
      	
      	<%}else{ %>
        
            <h2><img src="/images/portal/studying/h2_tit9.gif" alt="교재안내" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>교재안내</strong></p></h2>

            <p><img src="/images/portal/studying/book_borimg.gif" alt="과정수강시 필요한 교재 및 콘텐츠진흥원 발생 도서를 소개합니다." /></p>

            <p class="searchbox mg_t30">
                    <kocca_select:select                                       
                    name="ss_classify"                                         
                    sqlNum="0001"                                    
                    param="0073"             
                    onChange="whenSelect(this.value);"                         
                    attr= ""                                        
                    selectedValue="<%=ss_classify%>"                         
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass="mg_r12"                                
                    all="true" />
                <img src="/images/portal/board/serbox_classtit.gif" alt="과정검색" />
                <input name="p_searchtext" type="text" class="inbox" style="width:285px;" value="<%=v_searchtext%>" onkeypress="javascript:fnKeyPressEnter(event, 'whenSearch');">
                <input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" />
            </p>

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
                else                           v_image_view2 = v_newfilename2;
                v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");

                if(v_author.equals("")){
                	v_author = "미입력";
                }
%>
            <div class="common_photo_list">
                <dl class="board_photo_list">
                    <dt><a href="javascript:view('<%=v_bookcode%>')"><img src="<%=v_image_view2%>" alt="" width="133" height="152"/></a></dt>
                    <dd class="tit"><a href="javascript:view('<%=v_bookcode%>')" style="color:#627ABA;"><%=v_bookname%></a></dd>
                    <dd class="list">저자 : <%=v_author%></dd>
                    <dd class="list">판매처 : <%=v_book_site%></dd>
                    <dd class="con"><%=StringManager.formatTitle(v_intro, 100)%></dd>
                </dl>
            </div>
<%
            }

        if(i == 0 ) {
%>
            <div class="common_photo_list">
                <dl class="board_photo_list">
                    <dd class="con">검색된 교재가 없습니다.</dd>
                </dl>
            </div>
<%
        }
%>
            
        <!-- 페이징 영역 시작 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
        
        <%} %>
        
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->