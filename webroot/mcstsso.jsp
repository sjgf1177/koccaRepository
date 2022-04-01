<%@page import="java.sql.PreparedStatement"%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.homepage.*" %>

<%@ page import="com.sds.acube.ep.security.*" %>
<%@ page import="com.sds.acube.ep.sso.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
DBConnectionManager connMgr = null;
PreparedStatement pstmt = null;
String query = "";
int pstmtIndex = 1;
try {
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    box.setSession("tem_grcode","N000022");
    box.setSession("tem_sso","Y");

    HashMap ssoMap = new HashMap();
    String encodedD1 = request.getParameter("D1");
    String encodingAlgorithm = request.getParameter("D3");
    SecurityFactory fac = SecurityFactory.getInstance();
    SecurityDecoder decoder = fac.getDecoder(encodingAlgorithm);
    String decodedData = decoder.decode(encodedD1);
    String key;
    String value;


    for(StringTokenizer keyValueToken = new StringTokenizer(decodedData, ";"); keyValueToken.hasMoreTokens(); ) {
        String keyValue = keyValueToken.nextToken();
        int index = keyValue.indexOf("=");
        key = keyValue.substring(0, index);
        value = keyValue.substring(index + 1);
        ssoMap.put(key, value);
    }
	
    
    
    String id = "mc_"+(String)ssoMap.get("F1");
    String pw = decoder.decode( (String)ssoMap.get("F12") );
    String name = (String)ssoMap.get("F2");
    String compnm = (String)ssoMap.get("F4");
    String deptnm = (String)ssoMap.get("F7");
    String levelnm = (String)ssoMap.get("F9");
    String email = (String)ssoMap.get("F13");
    String tel = (String)ssoMap.get("F5");
    String mobile = (String)ssoMap.get("F56");
    String fax = (String)ssoMap.get("F57");

    // System.out.println("N000022 id : " + id);
    // System.out.println("N000022 pw : " + pw);
    // System.out.println("N000022 name : " + name);
    // System.out.println("N000022 compnm : " + compnm);
    // System.out.println("N000022 deptnm : " + deptnm);
    // System.out.println("N000022 levelnm : " + levelnm);
    // System.out.println("N000022 email : " + email);
    // System.out.println("N000022 tel : " + tel);
    // System.out.println("N000022 mobile : " + mobile);
    // System.out.println("N000022 fax : " + fax);
    
    if("".equals(id) || "".equals(pw)) {
%>
<script type="text/javascript">
    alert("잘못된 접근 입니다.");
    location.href="http://edu.kocca.or.kr";
</script>
<%
    } else {
    	
    	connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);
        
        query += "INSERT INTO TZ_MCST_LOG(	";
        query += "    SEQ,					";
        query += "    ID,					";
        query += "    PW,					";
        query += "    NAME,					";
        query += "    COMPNM,				";
        query += "    DEPTNM,				";
        query += "    LEVELNM,				";
        query += "    EMAIL,				";
        query += "    TEL,					";
        query += "    MOBILE,				";
        query += "    FAX,					";
        query += "    PARAMS,				";
        query += "    ALGORITHM,			";
        query += "    DECODE_DATA,			";
        query += "    REG_DT,				";
        query += "    INDATE				";
        query += ")							";
        query += "VALUES(					";
        query += "    MCST_LOG_SEQ.NEXTVAL,	";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    ?,					";
        query += "    TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),	";
        query += "    SYSDATE				";
        query += ")							";
        
        pstmt = connMgr.prepareStatement(query);
        pstmt.setString(pstmtIndex++, id);
        pstmt.setString(pstmtIndex++, pw);
        pstmt.setString(pstmtIndex++, name);
        pstmt.setString(pstmtIndex++, compnm);
        pstmt.setString(pstmtIndex++, deptnm);
        pstmt.setString(pstmtIndex++, levelnm);
        pstmt.setString(pstmtIndex++, email);
        pstmt.setString(pstmtIndex++, tel);
        pstmt.setString(pstmtIndex++, mobile);
        pstmt.setString(pstmtIndex++, fax);
        pstmt.setString(pstmtIndex++, encodedD1);
        pstmt.setString(pstmtIndex++, encodingAlgorithm);
        pstmt.setString(pstmtIndex++, decodedData);
        
        pstmt.executeUpdate();
        
        connMgr.commit();
%>
    <jsp:forward page="/servlet/controller.homepage.MainServlet" >
        <jsp:param name="p_id" value="<%=id%>" />
        <jsp:param name="p_pw" value="<%=pw%>" />
        <jsp:param name="gubun" value="60" />
        <jsp:param name="p_name" value="<%=name%>" />
        <jsp:param name="p_compnm" value="<%=compnm%>" />
        <jsp:param name="p_deptnm" value="<%=deptnm%>" />
        <jsp:param name="p_levelnm" value="<%=levelnm%>" />
        <jsp:param name="p_email" value="<%=email%>" />
        <jsp:param name="p_tel" value="<%=tel%>" />
        <jsp:param name="p_mobile" value="<%=mobile%>" />
        <jsp:param name="p_fax" value="<%=fax%>" />
        <jsp:param name="p_process" value="ASP_Login" />
    </jsp:forward>
<%
    }
%>

<%--HashMap => <%=ssoMap.toString()%>                   <br>--%>
<%--아이디 => <%=(String)ssoMap.get("F1")%>                     <br>--%>
<%--비밀번호 => <%=decoder.decode((String)ssoMap.get("F12"))%>      <br>--%>
<%--이름 => <%=(String)ssoMap.get("F2")%>                   <br>--%>
<%--이메일 => <%=(String)ssoMap.get("F21")%>                <br>--%>
<%--연락처 => <%=(String)ssoMap.get("F5")%>                     <br>--%>
<%--메뉴아이디 => <%=request.getParameter("menuid")%>           <br>--%>
<%--<%--%>
<%--out.print("ssoMap=> "+ssoMap.toString());--%>
<%--String sParmMenu = "101SBC1";--%>
<%--out.println("topmenu(gubun)=>"+sParmMenu.substring(0,1)+"<br>");--%>
<%--out.println("leftmenu(menuid)=>"+sParmMenu.substring(1,3)+"<br>");--%>
<%--out.println("orders=>"+sParmMenu.substring(6,7)+"<br>");--%>
<%--%>--%>

<%
/*
SessionUser user = new SessionUser();
user.setUserId((String)ssoMap.get("F18")==null?"":(String)ssoMap.get("F18")); // 로그인ID
user.setUserName((String)ssoMap.get("F2")==null?"":(String)ssoMap.get("F2"));    // 사용자명
user.setUserUid((String)ssoMap.get("F1")==null?"":(String)ssoMap.get("F1"));     // 사용자ID
user.setUserCompId((String)ssoMap.get("F3")==null?"":(String)ssoMap.get("F3"));   // 회사ID
user.setUserCompName((String)ssoMap.get("F4")==null?"":(String)ssoMap.get("F4"));    // 회사명
user.setUserDeptId((String)ssoMap.get("F6")==null?"":(String)ssoMap.get("F6"));   // 부서ID
user.setUserDeptName((String)ssoMap.get("F7")==null?"":(String)ssoMap.get("F7"));    // 부서명
user.setUserGradeName((String)ssoMap.get("F9")==null?"":(String)ssoMap.get("F9"));   // 직급명
user.setUserSysMail((String)ssoMap.get("F13")==null?"":(String)ssoMap.get("F13"));   // 내부메일
user.setUserEmail((String)ssoMap.get("F21")==null?"":(String)ssoMap.get("F21"));     // 외부메일
user.setCompId("compid");   // 회사코드
user.setTsId("tsid");       // 가상협업공간ID
user.setMenuId("menuid");   // 메뉴ID
user.setUserDptPosition((String)ssoMap.get("F45")==null?"":(String)ssoMap.get("F45"));  // 직위
user.setUserMemGrade((String)ssoMap.get("F20")==null?"":(String)ssoMap.get("F20"));   // 회원등급
user.setUserPhone((String)ssoMap.get("F5")==null?"":(String)ssoMap.get("F5"));      // 전화번호
user.setUserMobile((String)ssoMap.get("F56")==null?"":(String)ssoMap.get("F56"));     // 휴대폰번호
user.setUserFax((String)ssoMap.get("F57")==null?"":(String)ssoMap.get("F57"));        // 팩스번호
*/

} catch (Exception e) {
	try{
		if(connMgr != null){
			connMgr.rollback();
		}
		e.printStackTrace();
	    System.out.println("Exception Occured ins mcstsso.jsp : " + e.getMessage());
	    throw new Exception("Exception Occured ins mcstsso.jsp \r\n" + e.getMessage());
	}catch(Exception exc){
		if(pstmt != null){
			try{
				pstmt.close();
			}catch(Exception exec){}
		}
		if(connMgr != null){
			try{
				connMgr.setAutoCommit(true);
				connMgr.freeConnection();
			}catch(Exception exec){}
		}
		exc.printStackTrace();
	}
	
} finally {
	if(pstmt != null){
		try{
			pstmt.close();
		}catch(Exception e){}
	}
	if(connMgr != null){
		try{
			connMgr.setAutoCommit(true);
			connMgr.freeConnection();
		}catch(Exception e){}
	}
}

%>