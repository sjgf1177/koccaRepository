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
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_email      = box.getSession("email");
    String  v_type_l     = box.getString("p_type_l");

    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

%>  

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//신고등록
function uf_insertOK() {

    if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
        alert("신고내용을 입력하여야합니다 !!"); 
        CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
        return false; 
    } 

    document.getElementById("p_content").value = CrossEditor.GetBodyValue();

    if(document.form1.p_cmu_nm.value ==''){
       alert('커뮤니티를 선택하여야합니다.');return;
    }
    document.form1.action = "/servlet/controller.community.CommunityFrPoliceServlet";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}
//커뮤니티 찾기
function uf_findCummunity() {
   open_window('cmuDupCheck', '/servlet/controller.community.CommunityPopUpServlet?p_process=menucmufindPage', 100, 100, 750, 500, 'no', 'no', 'no', 'no', 'no');
  
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"   enctype = "multipart/form-data" method = "post">
<input type = "hidden" name = "p_process" value="">
<input type = "hidden" name = "p_cmuno" value="">
<input type = "hidden" name = "p_email" value="<%=s_email%>">
<input type = "hidden" name = "p_dupchk" value="<%=box.getStringDefault("p_dupchk","N")%>">

            <h2><img src="/images/portal/community/h2_tit3.gif" alt="불건전커뮤니티신고" class="fl_l" /><p class="category">Home > 커뮤니티 > <strong>불건전커뮤니티신고</strong></p></h2>

            <p><img src="/images/portal/community/statement_borimg.gif" alt="불건전커뮤니티신고" /></p>

            <table class="study_write mg_t30">
            <!--[if ie]><colgroup><col width="55px" /><col width="500" /><col width="55px" /><col width="120" /></colgroup><![endif]-->
            <colgroup><col width="75px" /><col width="400" /><col width="75px" /><col width="120" /></colgroup>
            <tr>
                <th class="th"><label for="subject">보내는 이</label></th>
                <td colspan="7" class="td txt"><%=s_username%>(<%=s_email%>)</td>
            </tr>
            <tr>
                <th class="th"><label for="file">커뮤니티명</label></th>
                <td class="td"><input type="text" name="p_cmu_nm" id="file" class="inbox fl_l mg_r6" style="width:300px;" readonly disabled/><p class="mg_t1"><a href="javascript:uf_findCummunity();" class="board_btn"><span>검색</span></a></p></td>
            </tr>
            
            <tr>
                <td colspan="8" class="td con">
                    <textarea  id="p_content" name="p_content" style="display:none;"></textarea>
                    <script type="text/javascript">
                        var CrossEditor = new NamoSE("contents");
                        CrossEditor.editorStart();
                    </script>
                </td>
            </tr>
            </table>

            <p class="board_btn"><a href="javascript:uf_insertOK();" class="btn_gr"><span>신고하기</span></a><!--a href="javascript:alert('메인페이지로');" class="btn_gr"><span>취소</span></a></p-->
            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->
