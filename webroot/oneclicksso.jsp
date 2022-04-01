<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="com.credu.library.*" %>
<%@ page import = "java.lang.String" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box        = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.setSession("tem_grcode","N000032");
    box.setSession("tem_sso","Y");

    String id=box.getString("id");
    String bizcd=box.getString("bizcd");
    String pCtype2 = box.getString("bizcd" );
    String pCtype = box.getString("ctype");

    long lnValueC1 = 52845L;
	long lnValueC2 = 28317L;
	long lnMYKEY = 15234L;

	Long lnUserID = new Long( id );

	long lnResult = 0L;

	//decoding SK
	lnResult = ( lnUserID.longValue() - lnValueC2 ) / lnValueC1 - lnMYKEY;
    if( pCtype == null || pCtype.equals("" ) ) {
            pCtype = pCtype2;
        }

//    String strUserId = pCtype + lnResult;
    String strUserId = "one_" + lnResult;

    if(strUserId.equals(""))
    {
%>
<body>
<script type="text/javascript">
    alert("잘못된 접근 입니다.");
    location.href="http://edu.kocca.or.kr";
</script>
<%} else {%>
<jsp:forward page="/servlet/controller.homepage.MainServlet" >
        <jsp:param name="p_id" value="<%=strUserId%>" />
        <jsp:param name="gubun" value="60" />
        <jsp:param name="p_process" value="ASP_Login" />
    </jsp:forward>
<%}%>