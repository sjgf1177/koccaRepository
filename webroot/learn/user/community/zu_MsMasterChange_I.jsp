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

    String  v_process      = box.getStringDefault("p_process","gomsmemberPop");
    String  v_cmuno      = box.getString("p_cmuno");//마스터 관련페이지는 무조건 들어가야함.
     String  v_static_cmuno      = box.getString("p_static_cmuno");//마스터 관련페이지는 무조건 들어가야함.   

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    ArrayList list       = (ArrayList)request.getAttribute("list");
    
    DataBox dbox = (DataBox)list.get(0);

%>  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//리스트
function uf_listOK() {
    document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "selectmsmainPage";
    document.form1.submit();
}
//저장
function uf_saveOK() {
   if(document.form1.p_kor_name.value ==''){
      alert('변경할 마스타를 입력하여야합니다.');document.form1.p_kor_name.focus();return;
   }

   document.form1.action          = "/servlet/controller.community.CommunityMasterServlet";
   document.form1.p_process.value = "updatemasterchangeData";
   document.form1.submit();
}
//회원선택
function uf_msPopMember() {
   open_window('msPopMember', '/servlet/controller.community.CommunityPopUpServlet?p_process=gomsmembernonePop&p_static_cmuno=<%=v_static_cmuno%>&p_cmuno=<%=v_cmuno%>&p_cbxflag=R', 100, 100, 750, 530, 'no', 'no', 'no', 'no', 'no');
  
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
	<input type = "hidden" name = "p_tmp_userid">
	<input type = "hidden" name = "p_email">

            <h1><img src="/images/portal/community/tit_10.gif" alt="마스터변경" /></h1>

            <table class="list">
            <colgroup><col width="120px" /><col /></colgroup>
            <thead>
            <tr>
                <th class="ta_l">현재마스터</th>
                <td class="ta_l end"><%=dbox.getString("d_kor_name")%>(마스터) </td>
            </tr>
            <tr>
                <th class="ta_l">변경할마스터</th>
                <td class="ta_l in end"><input name="p_kor_name" type="text" class="inbox fl_l mg_r6" style="width:295px;" readonly/> <p class="mg_t1"><a href="javascript:uf_msPopMember();" class="board_btn"><span>회원검색</span></a></p></td>
            </tr>
            </thead>
            </table>

            <p class="board_btn"><a href="javascript:uf_saveOK();" class="btn_gr"><span>수정</span></a></p>

</form>
<!-- Form 영역 종료-->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료-->
