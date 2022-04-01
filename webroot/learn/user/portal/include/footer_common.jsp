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

<%@ include file="/learn/user/portal/include/topNoLeftMenu.jsp"%>

        <!-- <div id="content" class="footer_content"> -->
        <div id="content">
<!-- Common 영역종료 -->

<!-- 콘텐츠 영역 시작 -->
<%=v_content %>
<!-- 콘텐츠 영역 종료 -->

        </div>

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footerNoLeftMenu.jsp"%>
<!-- footer 영역 종료 -->