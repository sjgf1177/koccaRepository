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
    String  v_cmuno      = box.getString("p_cmuno");
    String  v_menuno     = box.getString("p_menuno");    
    String  v_brdno      = box.getString("p_brdno");     
    String  v_board_fg   = box.getString("p_brd_fg");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    
    int     v_pageno     = box.getInt("p_pageno");
    int     v_rowseq     = box.getInt("p_rowseq");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);
	
    String ems_url = conf.getProperty("ems.url.value"); // 메일

    DataBox dbox       = (DataBox)request.getAttribute("selectView");
    
%>  
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//리스트로 이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//다음,이전글로이동
function uf_viewOK(faqno,rowseq) {

    document.form1.p_brdno.value = faqno;
    document.form1.p_rowseq.value = rowseq;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "viewPage";
    document.form1.submit();
}

//댓글등록하기
function uf_insertMemoOK() {
    if(document.form1.p_rep_content.value ==''){
       alert('댓글 내용을 입력하여야합니다.');document.form1.p_rep_content.focus();return;
    }

    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "insertMemoData";
    document.form1.submit();
}

//댓글삭제하기
function uf_deleteMemoOK(rplno) {
    document.form1.p_rplno.value = rplno;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "deleteMemoData";
    document.form1.submit();
}


//글삭제하기
function uf_deleteOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "deleteData";
    document.form1.submit();
}


//수정페이지로이동하기
function uf_updateOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "updatePage";
    document.form1.submit();
}

//답변등록페이지로이동하기
function uf_replyOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "replyPage";
    document.form1.submit();
}

	//일반메일 발송
    function MailSend() {
      window.self.name = "ProposeMemberList";
      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
      document.form1.action='<%=ems_url%>/ems/MassMail.do';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
      //document.form2.target = window.self.name;      
    }

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_faq_type"    value="<%=v_faq_type%>">
<input type = "hidden" name = "p_cmuno"       value="<%=v_cmuno%>">
<input type = "hidden" name = "p_menuno"      value = "<%=v_menuno%>">
<input type = "hidden" name = "p_brdno"       value = "<%=v_brdno%>">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_upfilecnt"   value = "">
<input type = "hidden" name = "p_rplno"       value = "">

<input type = "hidden" name = "p_lv"          value = "<%=dbox.getInt("d_lv")%>">
<input type = "hidden" name = "p_position"    value = "<%=dbox.getInt("d_position")%>">
<input type = "hidden" name = "p_parent"      value = "<%=dbox.getInt("d_parent")%>">
<input type = "hidden" name = "p_root"        value = "<%=dbox.getInt("d_root")%>">
<input type = "hidden" name = "p_rowseq"      value = "<%=v_rowseq%>">

<input type="hidden" name="p_checks" value="<%=dbox.getString("d_register_userid")%>,,,">
<input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">
<input type = "hidden" name = "p_brd_fg"       value = "<%=box.getString("p_brd_fg")%>">

            <h1>
            <%if(v_board_fg.equals("0")){%>
            <img src="/images/portal/community/tit_01.gif" alt="공지사항" class="fl_l" />
            <%}else if(v_board_fg.equals("2")){%>
            <span class="fl_l" style="font-size:12px;"><img src="/images/portal/community/tit_02.gif" alt="게시판" class="va_up2" /> (<%=(new CommunityMsMenuBean()).getSingleColumn2(v_cmuno,v_menuno,"title",v_board_fg)%>)</span>
            <%}else if(v_board_fg.equals("1")){%>
            <span class="fl_l" style="font-size:12px;"><img src="/images/portal/community/tit_03.gif" alt="자료실" class="va_up2" /> (<%=(new CommunityMsMenuBean()).getSingleColumn2(v_cmuno,v_menuno,"title",v_board_fg)%>)</span>
            <%}%>
            </h1>
            
            <table class="study_view">
            <!--[if ie]><colgroup><col width="50px" /><col /><col width="50px" /><col width="80px" /><col width="25px" /><col width="80px" /></colgroup><![endif]-->
            <colgroup><col width="70px" /><col /><col width="70px" /><col width="80px" /><col width="45px" /><col width="80px" /></colgroup>
            <tr>
                <th>제목</th>
                <td colspan="5" class="tit"><%=dbox.getString("d_title")%></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%=dbox.getString("d_name")%></td>
                <th>작성일</th>
                <td class="num"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy/MM/dd")%></td>
                <th>조회</th>
                <td class="num"><%=dbox.getInt("d_read_cnt")%></td>
            </tr>
            <tr>
                <td colspan="6" class="con"><%=dbox.getString("d_content")%></td>
            </tr>
            </table>
            
            <!-- 파일첨부 시작 -->
            <%
                Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                String i_boardStyle        = "study_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
            
            <p class="board_btn">
            <%if(s_userid.equals(dbox.getString("d_userid"))){%>
            <a href="javascript:uf_deleteOK();" class="btn_gr"><span>삭제</span></a>
            <a href="javascript:uf_updateOK();" class="btn_gr"><span>수정</span></a>
            <%} %>
            <% if("2".equals(v_board_fg)){%>
            <a href="javascript:uf_replyOK();" class="btn_gr"><span >답변</span></a>
            <%}%>
            <a href="javascript:wf_listOK();" class="btn_gr"><span>목록</span></a>
            </p>
            
            

            <!-- 댓글 시작 -->
            <%@ include file="/learn/user/community/include/cm_reply.jsp" %>
            <!-- 댓글 종료 -->
            
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->