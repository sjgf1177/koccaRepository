<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>  

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");

    int     v_seq          = box.getInt("p_seq");

    String v_pass_content   = "";
    String v_seminarnm      = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_pass_content     = dbox.getString("d_pass_content");
    	v_seminarnm        = dbox.getString("d_seminarnm");
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>선정자발표</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
</head>
<body id="popup" onload="self.focus();"><!-- popup size : 400* -->
    <div id="pop_header">
        <h1><img src="/images/portal/information/pop_h1_tit2.gif" alt="선정자발표" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap" class="message_top">
            <p class="message">[<%=v_seminarnm %>] 결과발표</p>
            
            <p class="info"><%=v_pass_content%></p>

        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:self.close();" class="btn_gr"><span>닫기</span></a></p>
    </div>
</body>
</html>