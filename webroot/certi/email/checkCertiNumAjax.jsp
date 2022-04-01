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
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="java.sql.PreparedStatement" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    int seq = box.getInt("seq");
    String userCertiNum = box.getString("user_certi_num");
    String email = box.getString("email");

    StringBuffer sb = new StringBuffer();
    StringBuffer contSb = new StringBuffer();

    String certiResult = this.checkCertiInfo( seq, userCertiNum );
    String[] temp = certiResult.split("\\|");
    int timeGap = Integer.parseInt(temp[1]);

    System.out.println("certi_num_yn : " + temp[0]);
    System.out.println("timeGap : " + timeGap);

    if ( timeGap > 10 ) {
        sb.append("{    \n");
        sb.append("    \"result\": \"ET\" \n");
        sb.append("}    \n");
    } else {
        if ( temp[0].equals("Y") ) {
            sb.append("{    \n");
            sb.append("    \"result\": \"S\" \n");
            sb.append("}    \n");
        } else {
            sb.append("{    \n");
            sb.append("    \"result\": \"EN\" \n");
            sb.append("}    \n");
        }
    }

    System.out.println( sb.toString() );
    out.println( sb.toString() );
%>

<%!
    private String checkCertiInfo(int seq, String userCertiNum) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int resultCount = 0;
        String certiNumYn = "";
        int timeGap = 0;
        String certiStatus = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("SELECT  DECODE(CERTI_NUM, ?, 'Y', 'N') AS CERTI_NUM_YN      \n");
            sql.append("    ,   TRUNC( (SYSDATE - REG_DT ) * 24 * 60 ) AS TIME_GAP  \n");
            sql.append("  FROM  TZ_EMAIL_CERTI_HISTORY  \n");
            sql.append(" WHERE  SEQ = ? \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setString( 1, userCertiNum );
            pstmt.setInt( 2, seq);

            ls = new ListSet(pstmt);

            if ( ls.next() ) {
                certiNumYn = ls.getString("CERTI_NUM_YN");
                timeGap = ls.getInt("TIME_GAP");
            }

            pstmt = null;

            if ( timeGap > 20 ) {
                // 제한 시간 20분 초과
                certiStatus = "T";

            } else {
                if ( certiNumYn.equals("Y") ) {
                    // 인증 성공
                    certiStatus = "S";

                } else {
                    // 인증 실패
                    certiStatus = "F";
                }
            }

            sql.setLength(0);
            sql.append("UPDATE  TZ_EMAIL_CERTI_HISTORY  \n");
            sql.append("   SET  CERTI_STATUS = ?    \n");
            sql.append("    ,   CERTI_DT = SYSDATE  \n");
            sql.append(" WHERE  SEQ = ? \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setString( 1, certiStatus );
            pstmt.setInt( 2, seq);

            resultCount = pstmt.executeUpdate();

            if ( resultCount > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            Log.err.println(e.getMessage());
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("insertSendMailHistory()\r\n" + e.getMessage());
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

        return certiNumYn + "|" + timeGap;
    }

%>