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
    
    String  v_rejoinFlag = box.getString("p_rejoinFlag");
    

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    //ArrayList list       = (ArrayList)request.getAttribute("list");
 
    //DataBox dbox = (DataBox)list.get(0);

%>  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//등록
function uf_insertOK() {
    if(document.form1.p_content.value ==''){
       alert('내용을 입력하여야합니다.');document.form1.content.focus();return;
    }
    document.form1.action = "/servlet/controller.community.CommunityFrJoinServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value = "">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_cmuno"       value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_rejoinFlag"  value = "<%=v_rejoinFlag %>">

            <h1><img src="/images/portal/community/tit_21.gif" alt="커뮤니티 가입" /></h1>

            <table class="study_write">
            <!--[if ie]><colgroup><col width="40px" /><col /></colgroup><![endif]-->
            <colgroup><col width="60px" /><col /></colgroup>
            <tr>
                <th class="th"><label for="subject">성명</label></th>
                <td class="td txt"><%=s_username%></td>
            </tr>
            <tr>
                <td colspan="2" class="td con"><textarea name="p_content" rows="25" cols="90" title="내용 입력란"></textarea></td>
            </tr>
            </table>

            <p class="board_btn"><a href="javascript:uf_insertOK();" class="btn_gr"><span>회원가입</span></a></p>
            
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->