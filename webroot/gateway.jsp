<%@ page import="com.credu.library.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />
<%!
  public String doPost(String s, String s1) {
    Object obj = null;
    StringBuffer stringbuffer = new StringBuffer();
    try
    {
        URL url = new URL(s);
        HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
        httpurlconnection.setDoOutput(true);
        PrintWriter printwriter = new PrintWriter(httpurlconnection.getOutputStream());
        printwriter.write(s1);
        printwriter.flush();
        printwriter.close();
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), "euc-kr"));
        int i;
        while((i = bufferedreader.read()) != -1)
            stringbuffer.append((char)i);
        httpurlconnection.disconnect();
    }
    catch(Exception exception)
    {
        exception.printStackTrace();
    }
    return stringbuffer.toString();
  }
%>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

String sRst = "";
sRst = doPost("http://www.gameacademy.or.kr/gms/gatewayCmd.svr","&mode=userCheck&Jumin=8308011676816");
if(sRst.trim().equals("1")) {
	out.println("°á°ú=>"+ sRst);
}

String resno   = box.getSession("resno");
out.println("resno=>"+resno);


%>

<%
String s_userid   = box.getSession("userid");
String currentDate = new java.text.SimpleDateFormat("yyyyMMdd", new java.util.Locale("en", "US")).format(new java.util.Date());
int iCurrentDate = Integer.parseInt(currentDate);
if(s_userid.equals("gameacademy") || (iCurrentDate >= 20080701 && iCurrentDate <= 20080930)) {
out.println("#######");
}


String isevent   = box.getSession("isevent");
out.println("isevent=>"+isevent);
%> 