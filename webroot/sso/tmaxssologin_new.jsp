<%@ page contentType = "text/html; charset=euc-kr" %>
<%@ page import="com.tmax.sso.filter.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*"%>
<%@ page import="javax.servlet.http.*" %>

<%@ page import="com.tmax.jw.util.aes.*" %>

<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>

<%!
/* HTTP Connection 을 이용하여 사용자 정보를 가져 옮 */
public String getUserInfo(String MEM_ID, String SERVICE_CODE, String GETINFO_URL, String progLang, boolean isDebug){
	
		HttpURLConnection conn;
		OutputStream out;
		InputStream in;
		BufferedReader br;
		
		String data = "";
		String recvBuf = null;
		
		String server_url = GETINFO_URL + "?val1=" + MEM_ID + "&val2=" + SERVICE_CODE + "&val3=" + progLang;
		if(isDebug) System.out.println("[GetUserInfo] server_url : " + server_url);
						
		try {
			URL url = new URL(server_url);

			conn = (HttpURLConnection) url.openConnection();
			
			// request
			conn.setDoOutput(true);	// 출력스트림에 대해서 enable (input은 default가 true)
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			out = (OutputStream) conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			
			// Response
			in = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			br = new BufferedReader(isr);
						
			while (true) {
				String line = br.readLine().trim();
						
				if(line.length()!=0 && !line.equals(" ")){
					recvBuf = line.toString().trim();
					if(isDebug) System.out.println("[GetUserInfo] recvBuf='" + recvBuf + "'");
					break;
				}
			}	
			
			if(isDebug) System.out.println("[GetUserInfo] recv : " + recvBuf);
			br.close();
			
		} catch(Exception e) {
			recvBuf = "FALSE##04";
			e.printStackTrace();
		}
				
		return recvBuf;

}
%>


<%

int logLevel = 0;
String serviceName = "ACD";

String SERVICE_CODE = "AB";
//String GETINFO_URL = "http://login.jwdemo.com:8091/info/getInfo.jsp";
String GETINFO_URL = "http://sso.kocca.or.kr/getInfo.jsp";
String progLang = "java";
String MEM_ID = null;

/* STEP1 - SSO 토큰과 nextURL 을 받아 옮. 포탈로그인 URL을 가져 옮. */
String tokenStr = (String)request.getParameter(Util.tokenKeyName);
String nextPage = (String)request.getParameter(Util.nextURLKeyName);
String loginURL = (String)application.getInitParameter("loginURL");
	
if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : nextPage = '"+ nextPage +"'");

/* NEXT 페이지 처리 */
if(nextPage != null && nextPage.length() != 0) {

	int i = nextPage.indexOf("nextUrl=");
	
	if(i!=-1) {
		nextPage = nextPage.substring(i+8);
		nextPage = java.net.URLDecoder.decode(nextPage,"utf-8");
	}
	//else nextPage = "/";
	else nextPage = "/main.jsp";
	
	if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : DestPage = '"+ nextPage +"'");
	
}
else{
	if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : naxtPage is nulll!!");
	//nextPage = "/";
	nextPage = "/main.jsp";
}

	
/* STEP2 - SSO 토큰이 없을 경우 인증페이지로 보냄. */
if(tokenStr==null || tokenStr.length()==0){
	//response.sendRedirect(SSOConf.selfloginurl);
	response.sendRedirect(loginURL);
	return;
}

/* STEP3 - SSO 토큰에서 사용자 정보 획득.*/
TmaxSSOToken token = new TmaxSSOToken(tokenStr);
String userid=token.getUserid();
String passwd=token.getPassword(); // ex) jjaiwook$jjaiwook$jjaiwook$jjaiwook$jjaiwook$1q2w3e4r$1q2w3e4r$1q2w3e4r$##jjaiwook

if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : userid = '"+ userid +"'");
if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : passwd = '"+ passwd +"'");	

String tokenInfo = "";
String strUserId = "";
String strUserPw= "";

/* token.getPassword() 에서 2차 때 부분만 잘라 냄.*/
String parse1[] = passwd.split("##");

if (parse1.length == 2) {
	MEM_ID = parse1[1].toString().trim();
	/* 사용자 정보를 가져 옮. 
	TRUE##XXXXXXXXXX - 정상.
	FALSE##01 - 허용된 IP가 아님.
	FALSE##02 - 파라미터 이상.
	FALSE##03 - DB에 해당 사용자 정보 없음.
	FALSE##04 - 서버커넥션 실패.
	*/
	tokenInfo = getUserInfo(MEM_ID, SERVICE_CODE, GETINFO_URL, progLang, true);
}
else{
	if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : SSO Token Error!!! passwd = " + passwd);
	out.println("<html><script>alert('TOKEN ERR');document.location.href='" + nextPage + "';</script></html>");
	return;
}

/* 사용자 정보를 이용하여 ID/PW 획득 */
String arrTokenInfo[] = tokenInfo.split("##");
if(arrTokenInfo[0].toString().equals("TRUE")){

	/******************** AES 복호화 ********************/
	AesCipher ac = new AesCipher();
	String strResult = ac.runAesDecode(arrTokenInfo[1].toString(),true);
	
	String result[] = strResult.split("//");
	
	if (result.length == 2) {
		strUserId = result[0].toString().trim();
		strUserPw = result[1].toString().trim();
		if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : getUserInfo() Success!! strUserId='" + strUserId + "'/strUserPw='" + strUserPw + "'");
	}
	else{
		if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : getUserInfo() ERR (" + strResult + ")");
		out.println("<html><script>alert('GetUserInfo Fail.');document.location.href='" + nextPage + "';</script></html>");
		return;
	}

}
else{
		
	if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : getUserInfo() ERR (" + tokenInfo + ")");
	out.println("<html><script>alert('GetUserInfo Fail. [" + arrTokenInfo[1].toString()  + "]');document.location.href='" + nextPage + "';</script></html>");
	return;
}

/* STEP4 - 업무단 API 를 사용하여 로그인 처리. */
boolean loginCheck = false;
	
/************************ APP Login  API S ************************/
//String loginReturn = TmaxSSOLogin(strUserId,strUserPw);
/************** 테스트 **********************/
String loginReturn = "1#OK";
/************************ APP Login  API E ************************/
	
String loginResult[] = loginReturn.split("#");

String isLoginTrue = loginResult[0].toString();
String errMsg = loginResult[1].toString();
	
if(isLoginTrue.equals("1")) loginCheck = true;


/* STEP5 - 로그인 정상처리 여부에 따른 처리로직 */

if(loginCheck) {
	
	/* 인증쿠키 생성 */
	Cookie tmaxsso_cookie = new Cookie(Util.authenticatedCookieName, "yes");
	tmaxsso_cookie.setMaxAge(Integer.parseInt(SSOConf.timeout)*60);
	tmaxsso_cookie.setPath("/");
	response.addCookie(tmaxsso_cookie);

	Cookie tmaxssocookie2 = new Cookie(Util.closingCookieName, "yes");
	tmaxssocookie2.setPath("/");
	response.addCookie(tmaxssocookie2);

	/* 인증세션 생성 */
	session.setAttribute("userid",strUserId);
	session.setAttribute("TMAXSSO","SUCCESS");
	
	// 업무 메인페이지로 보냄.
	response.sendRedirect(nextPage);

} else {
	out.println("<html><script>alert('" + errMsg + "');document.location.href='/';</script></html>");											
}
%>
