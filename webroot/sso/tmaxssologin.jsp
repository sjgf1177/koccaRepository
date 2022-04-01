<%@ page contentType = "text/html; charset=euc-kr" %>

<%@ page import="java.io.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>

<%@ page import="com.tmax.sso.filter.*" %>
<%@ page import="com.tmax.jw.util.aes.*" %>

<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.homepage.*"%>
<%@ page import="com.credu.common.*"%>
<%@ page import="com.credu.system.*"%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%!

/* 사용자 PW는 체크하지 않음. 아무 값이나 넘어와도 됨.*/
private String doLogin(RequestBox box, String userid, String userpw, String userip){


	//MultipartRequest multi = null;
	//RequestBox box = RequestManager.getBox(request, response);
	//RequestBox box = (RequestBox)request.getBox(request, response);
	String v_process = box.getStringDefault("p_process","login");
	int fileupstatus = 0;

	String returnMsg = "";
	int isOk1 = 0;

	try {
            AlertManager alert = new AlertManager();
            String v_msg = "";
            String v_url = "";
            System.out.println("#12#:userid="+userid+" tem_grcode="+box.getSession("tem_grcode"));

            String tem_grcode      = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
            v_url  = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");
            //v_url  = "/index" + v_grcode + ".jsp";        // 템플릿 적용후 주석 제거

            //String v_userip = request.getRemoteAddr();
            //box.put("p_userip", v_userip);

            LoginBean bean = new LoginBean();

			isOk1 = bean.loginSSO(box, userid, userpw, userip);
            System.out.println("#13#:isOk1="+isOk1);
            if(isOk1 == 1) {

                    String v_code   = "00000000000000000005";                  // 로그인 마일리지코드
                    String s_userid = box.getSession("userid");

					box.setSession("cmu_grtype","KGDI");
					returnMsg  = "OK";

	        }else{
	                if (isOk1 == -1) {  //ID 또는 비밀번호 없음
	                    returnMsg = "\\'" + userid + "\\'는 등록된 아이디가 아닙니다.(edu.kocc.or.kr:7)";
	                } else if (isOk1 == -2) {
	                    returnMsg = "\\'" + userid + "\\'사용자는 서비스를 사용할 수 없습니다.(edu.kocc.or.kr:7)";        //죄송합니다
	                } else if (isOk1 == -3) {
	                    returnMsg = "비밀번호가 맞지 않습니다(edu.kocc.or.kr:7)";       //비밀번호가 맞지 않습니다
	                } else {
	                    returnMsg = "";
	                }

            }
            System.out.println("#15#:returnMsg="+returnMsg);

	}catch (Exception ex) {
            //ErrorManager.getErrorStackTrace(ex, out);
			ex.printStackTrace();
			returnMsg = "#14#:doLogin() Exception!!";
    }

	if(isOk1 == 1) return "1#OK";
	else return "0#" + returnMsg;
}

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

//System.out.println("\n\n\n\n\n\n\n\n--------------------------------------------------");
RequestBox box = (RequestBox)request.getAttribute("requestbox");
if (box == null) box = RequestManager.getBox(request);

int logLevel = 1;
String serviceName = "한국콘텐츠아카데미";

String SERVICE_CODE = "KA";
String GETINFO_URL = "http://sso.kocca.or.kr/getInfo.jsp";
String progLang = "java";
String MEM_ID = null;

response.setContentType("text/html;charset=utf-8");

/* STEP1 - SSO 토큰과 nextURL 을 받아 옮. 포탈로그인 URL을 가져 옮. */
String tokenStr = (String)request.getParameter(Util.tokenKeyName);
String nextPage = (String)request.getParameter(Util.nextURLKeyName);
//String loginURL = (String)application.getInitParameter("loginURL");
String loginURL =  "http://www.wecon.or.kr/sub/mb/MBLogin.srt?tmaxsso_rtrn=edu.kocca.or.kr/sso/runSSO.jsp";

if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : nextPage = '"+ nextPage +"'");

/* NEXT 페이지 처리 */
if(nextPage != null && nextPage.length() != 0) {

	int i = nextPage.indexOf("nextUrl=");

	if(i!=-1) {
		nextPage = nextPage.substring(i+8);
		nextPage = java.net.URLDecoder.decode(nextPage,"utf-8");
	}
	else nextPage = "/";

	if (logLevel<2) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : DestPage = '"+ nextPage +"'");

}
else{
	if (logLevel<2) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : naxtPage is nulll!!");
	nextPage = "/";
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
String passwd=token.getPassword();

if (logLevel<2) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : userid = '"+ userid +"'");
if (logLevel<2) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : passwd = '"+ passwd +"'");
if (logLevel<1) 	out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : userid = '"+ userid +"'<br>");
if (logLevel<1) 	out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : passwd = '"+ passwd +"'<br>");


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

//	out.println("MEM_ID : " + MEM_ID);

	tokenInfo = getUserInfo(MEM_ID, SERVICE_CODE, GETINFO_URL, progLang, true);

//	out.println("tokenInfo : " + tokenInfo);
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

//	out.println("userid : " + strUserId);
//	out.println("passwd : " + strUserPw);

}
else{

	if (logLevel<1) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : getUserInfo() ERR (" + tokenInfo + ")");
	out.println("<html><script>alert('GetUserInfo Fail. [" + arrTokenInfo[1].toString()  + "]');document.location.href='" + nextPage + "';</script></html>");
	return;
}

//if(true)return;


/* STEP4 - 업무단 API 를 사용하여 로그인 처리. */
boolean loginCheck = false;

String strUserIp = (String)request.getRemoteAddr();
String loginReturn = "";

if(!strUserId.equals("") && !strUserPw.equals("")){

	/*TEST*/
	//strUserId = "pcu022";
	//strUserId = "user123";

	/************************ APP Login  API S ************************/
	loginReturn = doLogin(box, strUserId, strUserPw, strUserIp);
	/************************ APP Login  API E ************************/

	if (logLevel<2) 	System.out.println("[SKSSO-tmaxssologin.jsp]  "  + serviceName+ " : loginReturn = " + loginReturn);

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

}
else {
	out.println("<html><script>alert('토큰에 사용자 ID가 존재하지 않습니다.');document.location.href='/';</script></html>");
}

//System.out.println("\n\n\n\n\n\n\n\n--------------------------------------------------");

%>
