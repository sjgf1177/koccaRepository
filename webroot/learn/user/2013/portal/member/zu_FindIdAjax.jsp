<%
//**********************************************************
//  1. 제  목: 회원가입_본인확인
//  2. 파일명: /certi/email/sendCertiEmailAjax.jsp
//  3. 개  요: 회원가입_본인확인
//  4. 환  경: JDK 1.5
//  5. 버  젼: 1.0
//  6. 작  성:
//  7. 수  정:
//***********************************************************
%>

<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>

<%@ page import="com.credu.library.DBConnectionManager" %>
<%@ page import="com.credu.library.ErrorManager" %>
<%@ page import="com.credu.library.ListSet" %>
<%@ page import="com.credu.library.Log" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<%@ page import="org.json.simple.JSONObject" %>

<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.util.ArrayList" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String userName = box.getString("p_findid_name");
    String certiStr = box.getString("p_findid_certi");
    String certiType = ( certiStr.indexOf("@") > -1 ) ? "EMAIL" : "MOBILE";

    JSONObject jsonObj = new JSONObject();

    ArrayList<DataBox> userList = this.findUserId( userName, certiStr, certiType );

    if ( userList == null || userList.size() == 0) {
        jsonObj.put("result", "null");
    } else {
        jsonObj.put("result", "success");
        jsonObj.put("userList", userList);
    }

    System.out.println( jsonObj.toJSONString().replace("\\\\", "/") );
    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>

<%!
    private ArrayList<DataBox> findUserId( String userName, String certiStr, String certiType) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        ArrayList<DataBox> userList = null;
        DataBox dbox = null;
        try {
            connMgr = new DBConnectionManager();

            userList = new ArrayList<DataBox>();
            sql.append("SELECT  RPAD( SUBSTR(USERID, 0 , 4), LENGTH(USERID), '*') AS USERID \n");
            sql.append("    ,   NAME        \n");
            sql.append("  FROM  TZ_MEMBER   \n");
            sql.append(" WHERE  GRCODE = 'N000001'  \n");
            sql.append("   AND  STATE = 'Y' \n");
            sql.append("   AND  LEAVE_DATE IS NULL  \n");
            sql.append("   AND  NAME LIKE '%' || ? || '%'\n");

            if ( certiType.equals("MOBILE") ) {
                sql.append("   AND  REPLACE(CRYPTO.DEC('normal', HANDPHONE), '-', '') = ? \n");

            } else if ( certiType.equals("EMAIL") ) {
                sql.append("   AND  EMAIL =  CRYPTO.ENC('normal', ? )   \n");
            }

            if ( certiType.equals("MOBILE") ) {
                certiStr = certiStr.replaceAll("-", "");
            }

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setString( 1, userName );
            pstmt.setString( 2, certiStr );

            ls = new ListSet(pstmt);


            while ( ls.next() ) {
                dbox = ls.getDataBox();
                userList.add(dbox);                
            }


        } catch (Exception e) {
            Log.err.println(e.getMessage());
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("findUserId()\r\n" + e.getMessage());
        } finally {
            if ( ls != null ) {
                ls.close();
                ls = null;
            }
            if ( pstmt != null ) {
                pstmt.close();
                pstmt = null;
            }

            if ( connMgr != null ) {
                connMgr.freeConnection();
            }
        }

        return userList;
    }

%>