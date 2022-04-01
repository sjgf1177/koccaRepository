<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_process   = box.getString("p_process");
    int     v_pageno    = box.getInt   ("p_pageno");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    int     v_tabseq    = box.getInt("p_tabseq");
    int     v_seq       = box.getInt("p_seq");


    int    v_cnt        = 0;            // 조회수
    String v_title      = "";           // 제목
    String v_content    = "";           // 내용
    String v_date       = "";           // 등록일
    String v_name       = "";           // 등록자
    String v_userid     = "";           // userid
    String v_comptext   = "";           // 소속
    String v_target     = "";
    String v_targetnm   = "";

    String v_useyn      = "";           // 사용유무
    String v_month      = "";           // 해당월
    
    String v_realfile   = "";
    String v_savefile   = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    
    if (dbox != null) {    
        v_cnt           = dbox.getInt("d_cnt");
        v_title         = dbox.getString("d_title"); 
        v_comptext      = dbox.getString("d_comptext"); 
        v_content       = dbox.getString("d_contents");
        v_date          = dbox.getString("d_indate");   
        v_name          = dbox.getString("d_lname");
        v_useyn         = dbox.getString("d_useyn");    
        v_realfile      = dbox.getString("d_realfile");
        v_savefile      = dbox.getString("d_savefile");
        v_target        = dbox.getString("d_target");
        v_targetnm      = dbox.getString("d_targetnm");
        
        v_date     = FormatDate.getFormatDate(v_date, "yyyy.MM.dd");
     }  

%>
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">

    function listnotice(){
        document.nform2.p_process.value = "selectList";
        document.nform2.action = "/servlet/controller.homepage.KOpenPowerHomePageServlet";
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

            <h2><img src="/images/portal/information/h2_tit7.gif" alt="인터뷰" class="fl_l" /><p class="category">Home > 정보광장 > <strong>인터뷰</strong></p></h2>

            <p><img src="/images/portal/information/interview_borimg.gif" alt="수강생 및 강사들의 생생한 인터뷰를 만나보세요" /></p>
            
            <table class="information_view mg_t30">
            <!--[if ie]><colgroup><col width="25px" /><col width="200px" /><col width="50px" /><col width="70px" /><col width="25px" /><col width="30px" /></colgroup><![endif]-->
            <colgroup><col width="45px" /><col width="200px" /><col width="60px" /><col width="70px" /><col width="45px" /><col width="30px" /></colgroup>
            <tr>
                <th>제목</th>
                <td class="tit"><%=v_title%></td>
                <th>인터뷰대상</th>
                <td colspan="3"><%=v_targetnm%></td>
            </tr>
            <tr class="bo">
                <th>소속</th>
                <td><%=v_comptext%></td>
                <th>작성일</th>
                <td class="num"><%=v_date%></td>
                <th>조회</th>
                <td class="num"><%=v_cnt %></td>
            </tr>
            <tr>
                <td colspan="6" class="con">
                    <%=v_content %>
                </td>
            </tr>
            </table>
            
            <table class="information_view bo_tn">
            <colgroup><col width="65px" /><col width="625px" /></colgroup>
            <tr class="bgn">
                <th class="th">첨부파일</th>
                <td colspan="7" class="td pd_ln">
            		<p>
                    <img src="/images/portal/ico/ico_file.gif" alt="file" class="mg_r6 va_t" />
                    <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a>
                    <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
                    </p>
                </td>
            </tr>
            </table>
            
            <p class="board_btn"><a href="javascript:listnotice();" class="board_violet"><span>목록</span></a></p>            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>