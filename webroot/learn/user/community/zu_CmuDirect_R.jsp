<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_faq_type   = box.getStringDefault("p_faq_type","DIRECT");
    int     v_faqno      = box.getInt("p_faqno");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    int     v_pageno     = box.getInt("p_pageno");
    int     v_rowseq     = box.getInt("p_rowseq");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
	
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    String ems_url = conf.getProperty("ems.url.value"); // 메일


    ArrayList list         = (ArrayList)request.getAttribute("list");
    ArrayList viewfaq      =new ArrayList();
    //ArrayList viewfaqfile  =new ArrayList();
    //ArrayList viewfaqbefore=new ArrayList();
    //ArrayList viewfaqnext  =new ArrayList();
    //ArrayList viewfaqreplay=new ArrayList();
    
    if(list.size() != 0){
       viewfaq       = (ArrayList)list.get(0);  
       //viewfaqfile   = (ArrayList)list.get(1);  
       //viewfaqbefore = (ArrayList)list.get(2);  
       //viewfaqnext   = (ArrayList)list.get(3);  
       //viewfaqreplay = (ArrayList)list.get(4);  

    }
 
    DataBox dbox = (DataBox)viewfaq.get(0);

%>  

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//리스트로 이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityDirectServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
	<input type = "hidden" name = "p_process"      value="">
	<input type = "hidden" name = "p_faq_type"     value="<%=v_faq_type%>">
	<input type = "hidden" name = "p_pageno"       value = "<%=v_pageno%>">
	<input type = "hidden" name = "p_searchtext"   value = "<%=v_searchtext%>">
	<input type = "hidden" name = "p_select"       value = "<%=v_select%>">
	<input type = "hidden" name = "p_userid"       value = "">
	<input type = "hidden" name = "p_rplno"        value = "">
	<input type = "hidden" name = "p_faqno"        value = "<%=v_faqno%>">
	<input type = "hidden" name = "p_lv"           value = "<%=dbox.getInt("d_lv")%>">
	<input type = "hidden" name = "p_position"     value = "<%=dbox.getInt("d_position")%>">
	<input type = "hidden" name = "p_parent"       value = "<%=dbox.getInt("d_parent")%>">
	
	<input type = "hidden" name = "p_root"         value = "<%=dbox.getInt("d_root")%>">
	
	<input type = "hidden" name = "p_checks"       value="<%=dbox.getString("d_register_userid")%>,,,">
	<input type = "hidden" name = "userLoginID"    value="<%=box.getSession("userid")%>">
	
	<input type = "hidden" name = "p_rowseq"       value = "<%=v_rowseq%>">

            <h2><img src="/images/portal/community/h2_tit1.gif" alt="공지사항" class="fl_l" /><p class="category">Home > 커뮤니티 > <strong>공지사항</strong></p></h2>

            <p><img src="/images/portal/community/notic_borimg.gif" alt="공지사항" /></p>

            <table class="study_view mg_t30">
            <!--[if ie]><colgroup><col width="55px" /><col width="250px" /><col width="40px" /><col width="80px" /><col width="40px" /><col width="80px" /></colgroup><![endif]-->
            <colgroup><col width="75px" /><col width="250px" /><col width="60px" /><col width="80px" /><col width="60px" /><col width="80px" /></colgroup>
            <tr>
                <th>제목</th>
                <td colspan="5" class="tit"><%=dbox.getString("d_title") %></td>
            </tr>
            <tr class="bo">
                <th>작성자</th>
                <td><%=dbox.getString("d_name") %></td>
                <th>작성일</th>
                <td class="num"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy.MM.dd") %></td>
                <th>조회</th>
                <td class="num"><%=dbox.getInt("d_read_cnt") %></td>
            </tr>
            <tr>
                <td colspan="6" class="con"><%=dbox.getString("d_content") %></td>
            </tr>
            <!-- 파일첨부 시작 -->
            <%
	            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
	            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
	            String i_boardStyle        = "study_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
            </table>

            <p class="board_btn"><a href="javascript:wf_listOK()" class="btn_gr"><span>목록</span></a></p>
           
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->