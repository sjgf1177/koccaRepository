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

    ArrayList list1 = null;
    ArrayList list2 = null;

    int v_tabseq    = box.getInt("p_tabseq");
    
    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    String  v_selClsfCd   = box.getString("p_selClsfCd");
    String  v_selDtlCd   = box.getString("p_selDtlCd");
    
    int v_pageno    = box.getInt("p_pageno");
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
    
    int    v_seq     = 0;              // 일련번호
    int    v_cnt     = 0;              // 조회수

    String  v_typesnm   = "";
    String  v_title     = "";
    String  v_indate     = "";
    String  v_codenm    = "";
    String  v_userid    = "";
    String  v_usernm    = "";
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    
    list2 = (ArrayList)request.getAttribute("selectList");
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {
    	document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

    //글등록
    function insertPage() {
    	document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
        document.nform1.p_process.value = "insertPage";
        document.nform1.submit();
    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();" action="">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
    <input type = "hidden" name = "p_seq"    >
    <input type = "hidden" name = "p_tabseq" value="<%=v_tabseq %>" >

            <h2><img src="/images/portal/information/h2_tit8.gif" alt="교육후기" class="fl_l" /><p class="category">Home > 정보광장 > <strong>교육후기</strong></p></h2>

            <p><img src="/images/portal/information/edulatter_borimg.gif" alt="과정 수강을 마친 수강생들의 교육후기를 만나보세요 " /></p>

            <p class="board_serach">
                <select name="p_search">
                    <option value="title"   <%if("title".equals(v_search)) out.print(" selected");%>>제목</option>
                    <option value="all" <%if("all".equals(v_search)) out.print(" selected");%>>제목+내용</option>
                </select>
                <input name="p_searchtext" type="text" class="inbox" style="width:135px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');" />
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="select();"/>
            </p>

            <table class="board_list">
            <colgroup><col width="63px" /><col width="350px" /><col width="62px" /><col width="90px" /><col width="62px" /></colgroup>
            <thead>
            <tr class="information">
                <th><p>번호</p></th>
                <th><p>제목</p></th>
                <th><p>작성자</p></th>
                <th><p>작성일자</p></th>
                <th class="end"><p>조회수</p></th>
            </tr>
            </thead>
            <tbody>
			<%
			if(list2.size() != 0){
			    for( int i = 0; i < list2.size(); i++) {
				    DataBox dbox     = (DataBox)list2.get(i);
				    
				    v_seq           = dbox.getInt("d_seq");
				    v_cnt           = dbox.getInt("d_cnt");
				    v_title         = dbox.getString("d_title");
				    v_indate         = dbox.getString("d_indate");
				    v_userid        = dbox.getString("d_userid");
				    v_usernm        = dbox.getString("d_name");
				    
				    v_dispnum        = dbox.getInt("d_dispnum");
				    v_totalpage      = dbox.getInt("d_totalpage");
				    v_rowcount       = dbox.getInt("d_rowcount");
				    
				    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
				    if (!v_searchtext.equals("")) {
				        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				    } 
			%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="num"><%=v_dispnum%></td>
                <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>)"><%=v_title%></a></td>
                <td><%=v_usernm%></td>
                <td class="num"><%= FormatDate.getFormatDate(v_indate,"yyyy/MM/dd") %></td>
                <td class="num"><%=v_cnt%></td>
            </tr>
			<%      }
			    }else{%>
			      <tr> 
			        <td colspan ='5'>등록된 내용이 없습니다.</td>
			      </tr>
			<%  }   %>
            </tbody>
            </table>
            <% if(!s_userid.equals("")) {%>
            <p class="list_btn"><a href="javascript:insertPage()" class="board_blue"><span>등록</span></a></p>
            <%}%>        
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->