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
    String  v_process    = box.getStringDefault("p_process","gomsnoticePop");
    String  v_static_cmuno = box.getString("p_static_cmuno");
    String  v_cmuno = box.getString("p_cmuno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

%>  
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//회원찾기
function uf_msPopMember() {
   if(document.form1.p_allselect.checked == true){
       alert("전체회원이 선택되었습니다.");return;
   }
   open_window('msPopMember', '/servlet/controller.community.CommunityPopUpServlet?p_process=gomsmemberPop&p_cmuno='+document.form1.p_cmuno.value, 100, 100, 750, 480, 'no', 'no', 'no', 'yes', 'no');
  
}

//저장
function uf_cfmOK() {
   if(document.form1.p_allselect.checked == false){
        if(document.form1.p_kor_name.value ==''){
           alert('받을사람을 선택하여야합니다.');return;
        }
   }
        if(document.form1.p_title.value ==''){
           alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
        }

    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "insertnoticeData";
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
<input type = "hidden" name = "p_static_cmuno"      value = "<%=v_static_cmuno%>">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">

<input type = "hidden" name = "p_pgm"      value = "popnotice">
<input type = "hidden" name = "p_tmp_userid"      value = "">
<input type = "hidden" name = "p_email"      value = "">

            <h1><img src="/images/portal/community/tit_14.gif" alt="단체알림" /></h1>

            <table class="study_write">
            <!--[if ie]><colgroup><col width="60px" /><col /></colgroup><![endif]-->
            <colgroup><col width="80px" /><col /></colgroup>
            <tr>
                <th class="th"><label for="subject">제목</label></th>
                <td class="td"><input name="p_title" type="text" id="subject" class="inbox" style="width:97%;" /></td>
            </tr>
            <tr>
                <th class="th">받는사람</th>
                <td class="td"><input name="p_kor_name" type="text" id="file" class="inbox va_up2 mg_r6" style="width:320px;" onkeypress="javascript:fnKeyPressEnter(event, 'uf_msPopMember');"/> <a href="javascript:uf_msPopMember();" class="board_btn2 mg_r6"><span class="arrow">찾아보기</span></a> <input type=checkbox name=p_allselect value='Y' style="vertical-align:-1px;" class="va_up1 mg_l10" /> 전체회원 발송</td>
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
