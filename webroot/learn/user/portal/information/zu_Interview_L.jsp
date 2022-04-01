<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list2 = null;

    String  v_tabseq   = box.getString("p_tabseq");
    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    int v_pageno    = box.getInt("p_pageno");
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
    
    int i = 0;
    int    v_seq     = 0;           // 일련번호
    int    v_cnt     = 0;           // 조회수  

    String v_title   = "";          // 제목
    String v_content = "";          // 내용
    String v_indate   = "";          // 등록일
    String v_name    = "";          // 등록자
    String v_useyn   = "";          // 메인사용여부
    String v_savefile = "";         // 저장파일명
    String v_realfile = "";         // 실제파일명
    String v_select_seq = "";       // 메인 게시선택된 seq
    String v_month   = "";          // 개월
    String v_singleword   = "";     // 한마디

    box.put("leftmenu","02");    

    list2 = (ArrayList)request.getAttribute("selectList");
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.KOpenPowerHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.KOpenPowerHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.KOpenPowerHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.KOpenPowerHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
    <input type = "hidden" name = "p_seq"    >
    <input type = "hidden" name = "p_tabseq"  value = "<%= v_tabseq%>">

            <h2><img src="/images/portal/information/h2_tit7.gif" alt="인터뷰" class="fl_l" /><p class="category">Home > 정보광장 > <strong>인터뷰</strong></p></h2>

            <p><img src="/images/portal/information/interview_borimg.gif" alt="수강생 및 강사들의 생생한 인터뷰를 만나보세요" /></p>

            <p class="board_serach">
                <select name="p_search">
                    <option value="title"   <%if("title".equals(v_search)) out.print(" selected");%>>제목</option>
                    <option value="all" <%if("all".equals(v_search)) out.print(" selected");%>>제목+내용</option>
                </select>
                <input name="p_searchtext" type="text" class="inbox" style="width:135px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/>
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="select();" />
            </p>

            <table class="board_list">
            <colgroup><col width="63px" /><col width="440px" /><col width="90px" /><col width="62px" /></colgroup>
            <thead>
            <tr class="information">
                <th><p>번호</p></th>
                <th><p>제목</p></th>
                <th><p>작성일자</p></th>
                <th class="end"><p>조회수</p></th>
            </tr>
            </thead>
            <tbody>
			<%
			if(list2.size() != 0){
				for( i = 0; i < list2.size(); i++) {
				    DataBox dbox     = (DataBox)list2.get(i);
				    
				    v_seq   = dbox.getInt("d_seq");
				    v_cnt   = dbox.getInt("d_cnt");
				    v_title = dbox.getString("d_title");
				    v_indate = dbox.getString("d_indate");
				    v_name  = dbox.getString("d_lname");
				    v_useyn = dbox.getString("d_useyn");
				    v_month = dbox.getString("d_lmonth");
				    v_singleword = dbox.getString("d_singleword");
				
				    
				    v_dispnum   = dbox.getInt("d_dispnum");
				    v_totalpage = dbox.getInt("d_totalpage");
				
				    v_indate   = FormatDate.getFormatDate(v_indate,"yyyy.MM.dd");
				    
				    
				    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
				    if (!v_searchtext.equals("")) {
				    	v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				    } 
			%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="num"><%=v_dispnum%></td>
                <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>)"><%=v_title%></a></td>
                <td class="num"><%=v_indate%></td>
                <td class="num"><%=v_cnt%></td>
            </tr>
			<%      }
			    }else{%>
			
			      <tr> 
			        <td colspan ='4'>등록된 내용이 없습니다.</td>
			      </tr>
			<%  }   %>
            </tbody>
            </table>
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->