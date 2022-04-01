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

    String v_pass_content = "";
    String v_title  = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_title            = dbox.getString("d_title");
    	v_pass_content     = dbox.getString("d_winners");
    }

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>��÷�ڹ�ǥ</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
</head>
<body id="popup" onload="self.focus();"><!-- popup size : 400* -->
    <div id="pop_header">
        <h1><img src="/images/portal/information/pop_h1_tit4.gif" alt="��÷�ڹ�ǥ" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap" class="message_top">
            <p class="message">[<%=v_title %>] �����ǥ</p>
            
            <p class="info"><%=v_pass_content%></p>

        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:self.close();" class="btn_gr"><span>�ݱ�</span></a></p>
    </div>
</body>
</html>
