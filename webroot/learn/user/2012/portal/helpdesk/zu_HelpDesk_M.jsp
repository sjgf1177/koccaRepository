<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process  = box.getString("p_process");

    int     v_seq           = 0;            // 일련번호
    int     v_cnt           = 0; 
    String  v_title         = "";           // 제목
    String  v_date          = "";           // 등록일
    String  v_repstatus     = "";           // 답변상태(qna)
    String  v_types         = "";           // QNA 글 타입
    String  v_faqcategory   = "";           // FAQ 카테고리
    String  v_winneryn      = "";           // 이벤트 담청자 여부
    String  v_passyn        = "";           // 워크샵 선정 여부
    String  v_onoffgubun    = "";           // 온/오프구분

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    ArrayList faqCategoryList   = (ArrayList)request.getAttribute("faqCategoryList");
    ArrayList faqList           = (ArrayList)request.getAttribute("faqList");
    ArrayList qnaList           = (ArrayList)request.getAttribute("qnaList");
    ArrayList noticeList        = (ArrayList)request.getAttribute("noticeList");
    ArrayList letterList        = (ArrayList)request.getAttribute("letterList");
    
    // 메인페이지에서 각 메뉴로 이동시 메뉴 표시하기 위한 파라미터 - 추후 동적으로 불러오는 부분으로 변경(?)
    String noticeMenuId         = "07";
    String faqMenuId            = "01";
    String qnaMenuId            = "02";
    String swdownMenuId         = "03";
    String menualMenuId         = "06";
    String letterMenuId         = "08";
    String contactMenuId        = "09";
    String helpMenuId           = "05";
    String bookMenuId           = "10";
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->


<SCRIPT LANGUAGE="JavaScript">
<!--

    // 공지사항 더보기
    function moreNotice() {
        document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.form1.p_process.value = "List";
        document.form1.menuid.value = "<%=noticeMenuId%>";
        document.form1.target = "_self";
        document.form1.submit();
    }

    // 공지사항 상세보기
    function viewNotice(seq) {
       document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
       document.form1.p_process.value = "selectView";
       document.form1.menuid.value = "<%=noticeMenuId%>";
       document.form1.p_seq.value= seq;
       document.form1.target = "_self";
       document.form1.submit();
    }

    // FAQ 더보기
    function moreFaq()
    {
   	   document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
       document.form1.p_process.value    = "selectList";
       document.form1.menuid.value = "<%=faqMenuId%>";
       document.form1.target = "_self";
       document.form1.submit();
    }

    // FAQ 카테고리로 보기
    function changeCategory(aa) {
       document.form1.faqcategory.value=aa;
       document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
       document.form1.p_process.value    = "selectList";
       document.form1.menuid.value = "<%=faqMenuId%>";
       document.form1.target = "_self";
       document.form1.submit();
    }

   // QNA 더보기
    function moreQna() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "";
        document.form1.menuid.value = "<%=qnaMenuId%>";
        document.form1.target = "_self";
        document.form1.submit();
    }

    // QNA 상세보기
    function viewQna(seq, types) {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value   = "selectView";
        document.form1.menuid.value = "<%=qnaMenuId%>";
        document.form1.target = "_self";
        document.form1.p_seq.value       = seq;
        document.form1.p_types.value     = types;
        document.form1.submit();
    }

    // 뉴스레터 더보기
    function moreLetter() {
        document.form1.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.form1.p_process.value = "List";
        document.form1.menuid.value = "<%=letterMenuId%>";
        document.form1.target = "_self";
        document.form1.submit();
    }

    // 뉴스레터 상세보기
    function viewLetter(seq) {
        document.form1.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.form1.p_process.value = "selectView";
        document.form1.menuid.value = "<%=letterMenuId%>";
        document.form1.p_seq.value= seq;
        document.form1.target = "_self";
        document.form1.submit();
    }

    function changeTab(idx) {
        var tab1    = document.getElementById("tab1");
        var tab2    = document.getElementById("tab2");
        var tab3    = document.getElementById("tab3");
        var tab4    = document.getElementById("tab4");

    	switch(idx){
	        case "1" :  
	            tab1.style.display  = "block";
	            tab2.style.display  = "none";
	            tab3.style.display  = "none";
	            tab4.style.display  = "none";    
	            break;
	        case "2" :  
	            tab1.style.display  = "none";
	            tab2.style.display  = "block";
	            tab3.style.display  = "none";
	            tab4.style.display  = "none";    
	            break;
	        case "3" :  
	            tab1.style.display  = "none";
	            tab2.style.display  = "none";
	            tab3.style.display  = "block";
	            tab4.style.display  = "none";    
	            break;
	        case "4" :  
	            tab1.style.display  = "none";
	            tab2.style.display  = "none";
	            tab3.style.display  = "none";
	            tab4.style.display  = "block";    
	            break;
        } 
    }

    function goMenual() {
    	document.form1.action = "/servlet/controller.homepage.HomePageHelpServlet";
        document.form1.p_process.value = "Help";
        document.form1.menuid.value = "<%=menualMenuId%>";
        document.form1.target="_self";
        document.form1.submit();
    }

    function goQuestion() {
        document.form1.action = "/servlet/controller.homepage.HomePageContactServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.menuid.value = "<%=contactMenuId%>";
        document.form1.target="_self";
        document.form1.submit();
    }

    function moreSwDown() {
    	document.form1.action = "/servlet/controller.homepage.HomePageHelpServlet";
        document.form1.p_process.value = "selectHelpSWDown";
        document.form1.menuid.value = "<%=swdownMenuId%>";
        document.form1.target="_self";
        document.form1.submit();
    }

    function moreHelp() {
        document.form1.action = "/servlet/controller.homepage.HomePageHelpServlet";
        document.form1.p_process.value = "selectHelp";
        document.form1.menuid.value = "<%=helpMenuId%>";
        document.form1.target="_self";
        document.form1.submit();
    }

    function moreBook(){
    	document.form1.action = "/servlet/controller.book.BookServlet";
        document.form1.p_process.value = "listPage";
        document.form1.menuid.value = "<%=bookMenuId%>";
        document.form1.target="_self";
        document.form1.submit();
    }
//-->
</SCRIPT>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="form1" method="post" action="">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_seq">
    <input type = "hidden" name = "faqcategory">
    <input type = "hidden" name = "p_types">
    <input type = "hidden" name = "gubun" value = "<%=gubun %>">
    <input type = "hidden" name = "menuid">
    
            <div class="studying_visualbox">
                <dl class="category">
                    <dt><img src="/images/portal/studying/main_tit_category.gif" alt="FAQ CATEGORY" /></dt>
                    <%  if(faqCategoryList != null && faqCategoryList.size() > 0) {
                            for(int i = 0 ; i < faqCategoryList.size(); i ++) {
	                            DataBox dbox = (DataBox)faqCategoryList.get(i);
	                            v_faqcategory = dbox.getString("d_faqcategory");
	                            v_title       = dbox.getString("d_faqcategorynm");
	                            v_cnt         = dbox.getInt("d_cnt");
                    %>
                    <dd><a href="javascript:changeCategory('<%=v_faqcategory %>');"><%=v_title %></a></dd>
                    <%      } 
                        }else { %>
                    <dd>없음</dd>    
                    <%  } %>
                </dl>

                <dl class="faqlist">
                    <dt><img src="/images/portal/studying/main_tit.gif" alt="FAQ BEST5 자주 묻는 질문 목록을 확인하시고  궁금증을 해결하세요." class="fl_l" /> <a href="javascript:moreFaq();" class="fl_r"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></dt>
                    <%
                        if(faqList.size() > 0) {
                            for(int i = 0 ; i < faqList.size(); i ++) {
	                            DataBox dbox = (DataBox)faqList.get(i);
	                            v_seq         = dbox.getInt("d_fnum");
	                            v_faqcategory = dbox.getString("d_faqcategory");
	                            v_title       = dbox.getString("d_title");
                    %>
                    <dd><img src="/images/portal/studying/main_faq_<%=i+1 %>.gif" alt="<%=i+1 %>" /> <a href="javascript:changeCategory('<%=v_faqcategory %>');"><%=v_title %></a> </dd>
                    <%      }
                        } else {%>
                    <dd>FAQ가 존재하지 않습니다.</dd>
                    <%  } %>
                </dl>
            </div>
    
            <div class="main_warp">
                <div class="studying_left">
                    <p class="main_stit"><img src="/images/portal/studying/main_stit1.gif" alt="공지사항" class="fl_l" /><a href="javascript:moreNotice();" class="fl_r"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></p>

                    <table class="main_list">
                    <!--[if ie]><colgroup><col width="315px" /><col width="90" /><![endif]-->
                    <colgroup><col width="325px" /><col width="90" /></colgroup>
                    <thead>
                    <tr class="studying1">
                        <th><img src="/images/portal/studying/main_table_tit1.gif" alt="제목" /></th>
                        <th class="end"><img src="/images/portal/studying/main_table_tit2.gif" alt="날짜" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if(noticeList != null && noticeList.size() > 0) {
	                        for(int i = 0 ; i < noticeList.size(); i ++) {
	                            DataBox dbox = (DataBox)noticeList.get(i);
	                            v_seq         = dbox.getInt("d_seq");
	                            v_title       = dbox.getString("d_adtitle");
	                            v_date        = dbox.getString("d_addate");  
	                            v_onoffgubun  = dbox.getString("d_onoffgubun");
	                            
	                            if( box.getSession("tem_grcode").equals("N000001") ) {
		                            if( v_onoffgubun.equals("C") ) {
		                        		v_onoffgubun = "[ON]";
		                            } else if( v_onoffgubun.equals("C") ) {
		                            	v_onoffgubun = "[OFF]";
		                            } else {
		                            	v_onoffgubun = "";
		                            }
	                            } else {
	                            	v_onoffgubun = "";
	                            }
	                            
	                            v_title       = StringManager.formatTitle(StringManager.removeHTML(v_title), 25);
                    %>                            
                    <tr>
                        <td class="ta_l"><a href="javascript:viewNotice(<%=v_seq %>);"><%=v_onoffgubun %><%=v_title %></a> </td>
                        <td class="num"><%=FormatDate.getFormatDate(v_date, "yyyy.MM.dd")%></td>
                    </tr>
                    <%       }                
                        } else {
                    %>
                    <tr>
                        <td colspan=2>등록된 글이 없습니다.</td>
                    </tr>
                    <%  } %>
                    <tr>
                        
                    </tr>
                    </tbody>
                    </table>
                </div>
                
                <div class="studying_right">
                    <p class="main_stit"><img src="/images/portal/studying/main_stit2.gif" alt="Q&A" class="fl_l" /><a href="javascript:moreQna();" class="fl_r"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></p>

                    <table class="main_list">
                    <thead>
                    <tr class="studying2">
                        <th><img src="/images/portal/studying/main_table_tit3.gif" alt="제목" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if(qnaList != null && qnaList.size() > 0) {
	                        for(int i = 0 ; i < qnaList.size(); i ++) {
	                            DataBox dbox = (DataBox)qnaList.get(i);
	                            v_seq         = dbox.getInt("d_seq");
	                            v_title       = dbox.getString("d_title");
	                            v_repstatus   = dbox.getString("d_okyn1");
	                            v_types       = dbox.getString("d_types");  
	                            
	                            if(v_repstatus.equals("3")){
	                                v_repstatus = "답변완료";
	                            } else if(v_repstatus.equals("2")){
	                                v_repstatus = "답변준비중";
	                            } else {
	                                v_repstatus = "열람전";
	                            }
	                            
	                            v_title       = StringManager.formatTitle(StringManager.removeHTML(v_title), 30);
                    %>                   
                    <tr>
                        <td class="ta_l"><a href="javascript:viewQna(<%=v_seq %>, '<%=v_types %>');"><%=v_title %></a> </td>
                    </tr>
                    <%      }
	                    } else { %>
	                <tr>
	                   <td>등록된 글이 없습니다.</td>
	                </tr>
	                <%  } %>
                    </tbody>
                    </table>
                </div>
            </div>
            
            <ul class="main_banner">
                <li><a href="javascript:moreSwDown();"><img src="/images/portal/studying/main_banner1.gif" alt="S/W 다운로드" /></a></li>
                <li><a href="javascript:moreHelp();"><img src="/images/portal/studying/main_banner2.gif" alt="학습환경 도우미" /></a></li>
<!--                 <li><a href="javascript:goMenual();"><img src="/images/portal/studying/main_banner3.gif" alt="온라인 메뉴얼" /></a></li> -->
                <li><a href="javascript:moreBook();"><img src="/images/portal/studying/main_banner4.gif" alt="교재안내" /></a></li>
                <li class="bgn"><a href="javascript:goQuestion();"><img src="/images/portal/studying/main_banner5.gif" alt="1:1문의" /></a></li>
            </ul>

</form>
<!-- form 끝 -->


<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


