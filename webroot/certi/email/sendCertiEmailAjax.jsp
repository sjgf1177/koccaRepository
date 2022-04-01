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
<%@ page import="com.mnwise.lts.client.TcpipClient" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    StringBuffer sb = new StringBuffer();
    StringBuffer contSb = new StringBuffer();
    String certiNum = "";
    String sendMailResult = "";

    String email = box.getString("email");

    // 인증번호 생성
    int index = 0;
    char[] charSet = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    for( int i = 0 ; i < 6 ; i++ ) {
        index = (int)(charSet.length * Math.random() );
        certiNum += charSet[index];
    }

    System.out.println( "\n인증번호: " + certiNum );

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
    Date dt = new Date();

    // 메일 내용
    contSb.append("<div style=\"font-weight:bold; font-size:13px; \">안녕하세요, EDUKOCCA 고객님,<br/>요청하신 회원가입 인증번호는 다음과 같습니다.<br/><br/>");
    contSb.append("<ul>");
    contSb.append("<li>인증번호 : <span style=\"color:#5c82e8;\">").append( certiNum ).append( "</span></li><br/>");
    contSb.append("<li>발급시간 : <span style=\"color:#5c82e8;\">").append( sdf.format(dt) ).append( "</span></li><br/>");
    contSb.append("</ul>");
    contSb.append("<span style=\"font-weight:bold;\">인증번호는 메일이 발송된 시점부터 10분간만 유효 합니다.</span></div>");

    TcpipClient tc = new TcpipClient(); // 메일발송 socket connection open
    tc.open("mail2.kocca.kr", 9110);   //9100 추후변경

    tc.setAID("34");
    tc.setArg("INAME", "");
    tc.setArg("IUSERID", "");
    tc.setArg("IEMAIL", email);
    tc.setArg("TITLE", "[EDUKOCCA] 회원가입 인증번호 안내 메일입니다.");
    tc.setData("CONTENT", contSb.toString() );

    // 메일 발송
    sendMailResult = tc.commit();
    // sendMailResult = "250";

    System.out.println("메일발송결과 : " + sendMailResult);

    // 메일 발송 성공이면 DB에 인증 정보 입력
    if ( sendMailResult.equals("250") ) {
        String insertResult = this.insertEmailCertiHistory( email, certiNum );
        String[] temp = insertResult.split("\\|");

        if ( temp[0].equals("1") ) {
            sb.append("{    \n");
            sb.append("    \"result\": \"S\", \n");
            sb.append("    \"seq\": \"").append(temp[1]).append("\",     \n");
            sb.append("    \"certiNum\": \"").append(certiNum).append("\"   \n");
            sb.append("}    \n");
        } else {
            sb.append("{    \n");
            sb.append("    \"result\": \"EI\" \n");
            sb.append("}    \n");
        }

    } else {
        sb.append("{    \n");
        sb.append("    \"result\": \"EE\" \n");
        sb.append("}    \n");
    }

    System.out.println( sb.toString() );
    out.println( sb.toString() );
%>

<%!
    private String insertEmailCertiHistory(String email, String certiNum) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        int maxSeq = 0;
        int resultCount = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql.append("SELECT NVL( MAX ( SEQ ), 0 ) + 1 AS MAX_SEQ FROM TZ_EMAIL_CERTI_HISTORY ");

            ls = connMgr.executeQuery(sql.toString());
            if ( ls.next() ) {
                maxSeq = ls.getInt("MAX_SEQ");
            }

            sql.setLength(0);
            sql.append("/* 이메일 인증번호 발송 기록 저장 */\n");
            sql.append("INSERT  INTO    TZ_EMAIL_CERTI_HISTORY  \n");
            sql.append("    (                   \n");
            sql.append("        SEQ             \n");
            sql.append("    ,   EMAIL           \n");
            sql.append("    ,   CERTI_NUM       \n");
            sql.append("    ,   CERTI_STATUS    \n");
            sql.append("    ,   REG_DT          \n");
            sql.append("    ,   ACT_TYPE        \n");
            sql.append("    ) VALUES (          \n");
            sql.append("        ?   \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?) \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   'N' \n");
            sql.append("    ,   SYSDATE \n");
            sql.append("    ,   'C'     \n");
            sql.append("    )   \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt( 1, maxSeq );
            pstmt.setString( 2, email );
            pstmt.setString( 3, certiNum );

            resultCount = pstmt.executeUpdate();

            if ( resultCount > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            Log.err.println(e.getMessage());
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("insertEmailCertiHistory()\r\n" + e.getMessage());
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

        return resultCount + "|" + maxSeq;
    }
%>