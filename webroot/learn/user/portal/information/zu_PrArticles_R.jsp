<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_process    = box.getString("p_process");
    int     v_tabseq     = box.getInt   ("p_tabseq");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_seq      = box.getInt("p_seq");

    String  v_date          = "";
    String  v_title         = "";
    String  v_content       = "";
    String  v_name          = "";
    String  v_flagYn        = "";
    String  v_position      = "";
    String  v_url           = "";
    int     v_cnt           =  0;
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    
    if (dbox != null) {
        v_title     = dbox.getString("d_title"); 
        v_content   = dbox.getString("d_content");   
        v_date      = dbox.getString("d_ldate");      
        v_name      = dbox.getString("d_name");
        v_flagYn    = dbox.getString("d_flagyn");
        v_position  = dbox.getString("d_position");
        v_url       = dbox.getString("d_url");
        v_cnt       = dbox.getInt("d_cnt");
     }  
    
%>
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">

    function listnotice(){
    	document.nform2.p_process.value = "selectList";
        document.nform2.action = "/servlet/controller.infomation.BriefingHomePageServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform2" method="post">
	<input type = "hidden" name = "p_process" >
	<input type = "hidden" name = "p_seq" value="<%=v_seq%>">
	<input type = "hidden" name = "p_tabseq" value="<%=v_tabseq%>">
	<input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
	<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
	<input type = "hidden" name = "p_search" value = "<%=v_search %>">

            <h2><img src="/images/portal/information/h2_tit9.gif" alt="홍보기사" class="fl_l" /><p class="category">Home > 정보광장 > <strong>홍보기사</strong></p></h2>

            <p><img src="/images/portal/information/inforlatter_borimg.gif" alt="과정 수강을 마친 수강생들의 교육후기를 만나보세요 " /></p>
            
            <table class="information_view mg_t30">
            <!--[if ie]><colgroup><col width="25px" /><col width="170px" /><col width="40px" /><col width="80px" /><col width="35px" /><col width="50px" /></colgroup><![endif]-->
            <colgroup><col width="45px" /><col width="170px" /><col width="50px" /><col width="80px" /><col width="55px" /><col width="50px" /></colgroup>
            <tr>
                <th>제목</th>
                <td colspan="5" class="tit"><%=v_title%></td>
            </tr>
            <tr class="bo">
                <th>작성자</th>
                <td><%=v_name%></td>
                <th>작성일자</th>
                <td class="num"><%= FormatDate.getFormatDate(v_date,"yyyy.MM.dd") %></td>
                <th>조회수</th>
                <td class="num"><%=v_cnt%></td>
            </tr>
            <tr>
                <td colspan="8" class="con"><%=v_content%></td>
            </tr>
            </table>
            
            <%
            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
            String i_boardStyle        = "information_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %> 
            
            <p class="board_btn"><a href="javascript:listnotice()" class="board_violet"><span>목록</span></a></p>
            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->
