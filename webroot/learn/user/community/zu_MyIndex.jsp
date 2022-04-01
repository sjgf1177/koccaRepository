<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getStringDefault("p_process","selectmain");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  v_cmuno      = box.getString("p_cmuno");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    //공지사항
    ArrayList listDirectBrd     = (ArrayList)request.getAttribute("listDirectBrd");

    //게시글
    ArrayList listBrd     = (ArrayList)request.getAttribute("listBrd");

    //투표관련정보
    int v_userpollcnt    = Integer.parseInt(String.valueOf(request.getAttribute("userpollcnt")));
    ArrayList listPoll       = (ArrayList)request.getAttribute("listPoll");
    
    System.out.println("listPoll : " + listPoll);
    //System.out.println("listPoll.size() : " + listPoll.size());
    
    ArrayList detailList = null;
    ArrayList cntList   = null;
    
    DataBox masterBox   = null;
    DataBox questionBox = null;
    DataBox detailBox   = null;
    DataBox cntBox      = null;
    if(listPoll !=null && listPoll.size() != 0){
    	masterBox       = (DataBox)listPoll.get(0);  //리스트
    	questionBox     = (DataBox)listPoll.get(1);  //항목
    	detailList      = (ArrayList)listPoll.get(2);  //상세필드
    	cntList         = (ArrayList)listPoll.get(3);  //응답결과
    }

    String  v_title      = "";
    String  v_fdte       = "";
    String  v_tdte       = "";
    String  v_pollno     = "";
    int     v_apply_cnt  = 0;

    if(masterBox != null){
        v_pollno     = masterBox.getString("d_pollno");
        v_title      = masterBox.getString("d_title");
        v_fdte       = masterBox.getString("d_fdte");
        v_tdte       = masterBox.getString("d_tdte");
        v_apply_cnt  = masterBox.getInt("d_apply_cnt");
    }
    
    
    // 질문항목
    String v_questno            = "";
    String v_need_question      = "";
    String v_item_cnt           = "";
    
    if(questionBox != null ){
        v_questno            = questionBox.getString("d_questno");
        v_need_question      = questionBox.getString("d_need_question");
        v_item_cnt           = questionBox.getString("d_item_cnt");
    }
    
%>  


<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//조회
function uf_searchOK() {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "totalsearchPage";
    document.form1.submit();
}

//게시판보기
function uf_viewOK(menuno,brdno) {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet?p_menuno="+menuno+"&p_brdno="+brdno;
    document.form1.p_process.value = "viewPage";
    document.form1.submit();
}
//게시판리스트로이동
function uf_brdlistOK(menuno) {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet?menuno="+menuno;
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//페이지이동
function goPage() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet?p_pageno=1";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}

//설문 답변
function uf_replyData() {
	<% if(userInfo == null) { %>
	alert("회원만의 공간입니다.");
    <% } else { %>
    if(uf_validcheck() == -1) return;
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "replyIndexData";
    document.form1.submit();
    <% } %>
}

//설문답변페이지로이동
function uf_replyOK(pollno,rowseq) {
	<% if(userInfo == null) { %>
    alert("회원만의 공간입니다.");
    <% } else { %>
    document.form1.p_pollno.value = pollno;
    document.form1.p_rowseq.value = rowseq;
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "queryReply";
    document.form1.submit();
    <% } %>
}

//설문결과페이지
function uf_resultviewOK(pollno) {
	<% if(userInfo == null) { %>
	alert("회원만의 공간입니다.");
	<% } else { %>
    document.form1.p_pollno.value = pollno;
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "moveresultviewPage";
    document.form1.submit();
    <% } %>
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
	<input type = "hidden" name = "p_process"     value="">
	<input type = "hidden" name = "p_userid"      value = "">
	<input type = "hidden" name = "p_cmuno"       value = "<%=v_cmuno%>">
	<input type = "hidden" name = "p_rowseq"      value = "">
	<input type = "hidden" name = "p_pollno"      value = "">

<% if(v_tmp_layout_fg.equals("1")) { %>
<%@ include file="/learn/user/community/zu_MyIndex_A.jsp"%>
<%} else if(v_tmp_layout_fg.equals("2")) { %>
<%@ include file="/learn/user/community/zu_MyIndex_B.jsp"%>
<%} %>


	
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->