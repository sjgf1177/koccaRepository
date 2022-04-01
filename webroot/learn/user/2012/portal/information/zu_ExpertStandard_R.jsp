<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
    String  v_tem_grcode   = box.getSession("tem_grcode");
    
    String v_content    = "";
    String v_code       = "";
    String v_title      = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
        v_content    = dbox.getString("d_content");
        v_code       = dbox.getString("d_code");
        v_title      = dbox.getString("d_title");
     }
    
%>
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT>

function insertPage(){
    document.form1.p_process.value= "insertPage";
    document.form1.action = "/servlet/controller.tutor.OffExpertHomePageServlet";
    document.form1.submit();
}

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" enctype = "multipart/form-data" method = "post" action=""> 
        <input type = "hidden" name = "p_process"   value = "">
        <input type = "hidden" name = "p_tab"       value = "">

            <h2><img src="/images/portal/information/h2_tit10.gif" alt="전문가등록하기" class="fl_l" /><p class="category">Home > 정보광장 > <strong>전문가등록하기</strong></p></h2>

            <p><img src="/images/portal/information/professional_borimg.gif" alt="콘텐츠진흥원은 전문인력 확보 및 적극적인 활용을 위해 전문가 등록 시스템을 구축하고 있습니다. 탁월한 능력과 역량이 있는 분들의 많은 참여를 바랍니다" /></p>

            <ul class="tabwrap mg_t30">
                <li><a href="javascript:insertPage();" class="tab_information"><span>전문가 등록하기</span></a></li>
                <li><a class="tab_information tab_information_on"><span>전문가 등록기준</span></a></li>
            </ul>
            
<!-- 탭 -->
<%=v_content %>
<p class="board_btn ag_c mg_t30"><a href="javascript:insertPage();" class="btn_violet mg_r7"><span>등록</span></a></p>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->