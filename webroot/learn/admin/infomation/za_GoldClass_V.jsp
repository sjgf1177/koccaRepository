<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>  
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    
    String  v_vodurl          = "";   // 내용
    String  v_width_s           = "";   // 창넓이
    String  v_height_s          = "";   // 창높이
    
    DataBox dbox = (DataBox)request.getAttribute("selectOffExpert");
    if (dbox != null) {
        v_vodurl            = dbox.getString("d_vodurl");     
        v_width_s           = dbox.getString("d_width_s");    
        v_height_s          = dbox.getString("d_height_s");   
    }
    
    String v_server   = conf.getProperty("kocca.url.value");
    
    v_server = v_server.replace("http://", "mms://");
    
    System.out.println("url ===============> "+v_vodurl);
    
%>

<SCRIPT LANGUAGE="JavaScript">
window.location = "<%=v_vodurl%>";
</SCRIPT>