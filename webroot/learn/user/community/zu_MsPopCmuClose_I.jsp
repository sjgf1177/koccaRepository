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
    String  v_process      = box.getStringDefault("p_process","gomsnoticePop");
    String  v_static_cmuno = box.getString("p_static_cmuno");
    String  v_cmuno        = box.getString("p_cmuno");

    String  v_searchtext   = box.getString("p_searchtext");
    String  v_select       = box.getString("p_select");

    String  s_userid       = box.getSession("userid");
    String  s_username     = box.getSession("name");

%>  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//저장
function uf_cfmOK() {

    if(document.form1.p_title.value ==''){
        alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
     }
     
     if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
         alert("내용을 입력해 주세요 !!"); 
         CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
         return false; 
     } 
    document.getElementById("p_content").value = CrossEditor.GetBodyValue();
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movecommunitycloseData";
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
<input type = "hidden" name = "p_static_cmuno"      value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">

            <h1><img src="/images/portal/community/tit_11.gif" alt="커뮤니티 폐쇄" /></h1>

            <p class="board_txt"><span>※</span> 커뮤니티 폐쇄 요청 사유를 적어주세요.</p>
            <table class="study_write">
            <tr>
                <th width="10%" class="th"><label for="subject">제목</label></th>
                <td width="90%" class="td"><input name="p_title" type="text" id="subject" class="inbox" style="width:97%;" /></td>
            </tr>
            <tr>
                <td colspan="2" class="td con">
                    <textarea  id="p_content" name="p_content" style="display:none;"></textarea>
                    <script type="text/javascript">
                        var CrossEditor = new NamoSE("contents");
                        CrossEditor.editorStart();
                        CrossEditor.SetUISize("650","400");
                    </script>
                </td>
            </tr>
            </table>

            <p class="board_btn"><a href="javascript:uf_cfmOK();" class="btn_gr"><span>발송</span></a></p>

</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->

