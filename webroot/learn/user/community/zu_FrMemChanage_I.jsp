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

    String  v_process      = box.getStringDefault("p_process","movePage");
    String  v_cmuno      = box.getString("p_cmuno");
    

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  content      = box.getString("content");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);
%>  
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//등록
function uf_insertOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrMemberServlet";
    document.form1.p_process.value = "updateData";
    document.form1.submit();
}

//이메일수정
function uf_updateEmailOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrMemberServlet";
    document.form1.p_process.value = "updateEmailData";
    document.form1.submit();
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">

            <h1><img src="/images/portal/community/tit_18.gif" alt="개인정보변경" /></h1>

            <table class="list">
            <colgroup><col width="100px" /><col /></colgroup>
            <thead>
            <tr>
                <th class="ta_l">성명</th>
                <td class="ta_l end"><%=userInfo.getString("d_kor_name")%> </td>
            </tr>
            <tr>
                <th class="ta_l">회사전화번호</th>
                <td class="ta_l in end"><input type="text" class="inbox fl_l mg_r6" style="width:295px;" value="<%=userInfo.getString("d_office_tel")%>" readonly/></td>
            </tr>
            <tr>
                <th class="ta_l">자택전화번호</th>
                <td class="ta_l in end"><input type="text" class="inbox fl_l mg_r6" style="width:295px;" value="<%=userInfo.getString("d_tel")%>" readonly/></td>
            </tr>
            <tr>
                <th class="ta_l">휴대폰</th>
                <td class="ta_l in end"><input type="text" class="inbox fl_l mg_r6" style="width:295px;" value="<%=userInfo.getString("d_mobile")%>" readonly/></td>
            </tr>
            <tr>
                <th class="ta_l">팩스번호</th>
                <td class="ta_l in end"><input type="text" class="inbox fl_l mg_r6" style="width:295px;" value="<%=userInfo.getString("d_fax")%>" readonly/></td>
            </tr>
            <tr>
                <th class="ta_l">담당업무</th>
                <td class="ta_l in end"><input type="text" class="inbox fl_l mg_r6" style="width:295px;" value="<%=userInfo.getString("d_duty")%>" readonly/></td>
            </tr>
            <tr>
                <th class="ta_l">근무지</th>
                <td class="ta_l in end"><input type="text" class="inbox fl_l mg_r6" style="width:295px;" value=""<%=userInfo.getString("d_wk_area")%>" readonly/></td>
            </tr>
            <tr>
                <th class="ta_l">이메일</th>
                <td class="ta_l in end"><input type="text" class="inbox fl_l mg_r6" style="width:295px;" value="<%=userInfo.getString("d_email")%>" readonly/></td>
            </tr>
            </thead>
            </table>
            
            
            <table class="study_write bo_tn">
            <tr>
                <td class="td con"><textarea name="p_content" style="height:170px;" title="내용 입력란"><%=userInfo.getString("d_intro") %></textarea></td>
            </tr>
            </table>
            
            <p class="board_btn"><a href="javascript:uf_insertOK();" class="btn_gr"><span>저장</span></a></p>
            
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->
