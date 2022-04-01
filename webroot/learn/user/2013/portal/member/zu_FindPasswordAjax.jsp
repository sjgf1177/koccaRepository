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
<%@ page import="com.credu.library.HashCipher" %>
<%@ page import="com.credu.library.ListSet" %>
<%@ page import="com.credu.library.Log" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.mnwise.lts.client.TcpipClient" %>

<%@ page import="org.json.simple.JSONObject" %>

<%@ page import="java.sql.PreparedStatement" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String certiType = box.getString("checkType");
    String userId = box.getString("p_findpwd_userid");
    String userName = box.getString("p_findpwd_name");
    String mobileNo = box.getStringDefault("p_findpwd_mobile", "");
    String email = box.getStringDefault("p_findpwd_email", "");

    certiType = certiType.equals("1") ? "MOBILE" : "EMAIL";
    String certiStr = certiType.equals("MOBILE") ? mobileNo : email;

    JSONObject jsonObj = new JSONObject();

    int resultCount = this.checkUserPassword( userId, userName, certiType, certiStr );

    if ( resultCount == 0) {
        jsonObj.put("result", "fail");
    } else {
        jsonObj.put("result", "success");
    }

    System.out.println( jsonObj.toJSONString().replace("\\\\", "/") );
    out.println( jsonObj.toJSONString().replace("\\\\", "/") );

%>

<%!
    /**
     * 회원의 비밀번호 찾기이다.
     * 현재 비밀번호는 복호화가 불가능하기 때문에 입력한 정보 일치 여부를 확인 후
     * 일치할 경우 임시 비밀번호를 생성하고, 인증한 매체(휴대폰/이메일)로 임시 비밀번호를 발송한다.
     * @param userId - 사용자 아이디
     * @param userName - 사용자 이름
     * @param certiType - 인증유형(MOBILE / EMAIL)
     * @param certiStr - 인증유형에 따른 값. MOBILE이면 휴대전화번호, EMAIL이면 이메일 주소
     */
    private int checkUserPassword( String userId, String userName, String certiType, String certiStr) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        int result = 0;
        int sendResult = 0;
        int updateResult = 0;

        try {
            System.out.println("checkUserPassword start ");
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("SELECT  COUNT(USERID) AS RESULTCNT  \n");
            sql.append("  FROM  TZ_MEMBER   \n");
            sql.append(" WHERE  GRCODE = 'N000001'  \n");
            sql.append("   AND  STATE = 'Y'         \n");
            sql.append("   AND  LEAVE_DATE IS NULL  \n");
            sql.append("   AND  USERID = ?          \n");
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
            pstmt.setString( 1, userId );
            pstmt.setString( 2, userName );
            pstmt.setString( 3, certiStr );

            ls = new ListSet(pstmt);

            if ( ls.next() ) {
                result = ls.getInt("RESULTCNT");
                System.out.println("ls.next() result : " + result);
            }

            ls.close();
            ls = null;

            pstmt.close();
            pstmt = null;

            // 인증에 성공한 경우
            if (result > 0) {
                String newPassword = this.getRandomString(8);

                System.out.println("newPassword : " + newPassword);

/**========================================================**/
                if ( certiType.equals("MOBILE") ) {
                    System.out.println("인증 타입은 휴대폰");
                    String msg = "";
                    String[] mobileNoArr = new String[3];

                    sql.setLength(0);
                    sql.append("INSERT  INTO EM_TRAN    (   \n");
                    sql.append("        TRAN_PR         \n");
                    sql.append("    ,   TRAN_PHONE      \n");
                    sql.append("    ,   TRAN_CALLBACK   \n");
                    sql.append("    ,   TRAN_STATUS     \n");
                    sql.append("    ,   TRAN_DATE       \n");
                    sql.append("    ,   TRAN_MSG        \n");
                    sql.append(") VALUES (  \n");
                    sql.append("        EM_TRAN_PR.NEXTVAL  \n");
                    sql.append("    ,   ?   \n");
                    sql.append("    ,   ?   \n");
                    sql.append("    ,   '1' \n");
                    sql.append("    ,   SYSDATE \n");
                    sql.append("    ,   ?   \n");
                    sql.append(")  \n");

                    pstmt = connMgr.prepareStatement(sql.toString());

                    msg += "[EDUKOCCA] 회원님의 임시 비밀번호는 " + newPassword + " 입니다.";

                    if ( certiStr.length() < 11 ) {
                        mobileNoArr[0] = certiStr.substring(0, 3);
                        mobileNoArr[1] = certiStr.substring(3, 6);
                        mobileNoArr[2] = certiStr.substring(6, 10);
                    } else {
                        mobileNoArr[0] = certiStr.substring(0, 3);
                        mobileNoArr[1] = certiStr.substring(3, 7);
                        mobileNoArr[2] = certiStr.substring(7, 11);
                    }

                    pstmt.setString(1, mobileNoArr[0] + "-" + mobileNoArr[1] + "-" + mobileNoArr[2]);
                    pstmt.setString(2, "02-2161-0077");
                    pstmt.setString(3, msg);

                    sendResult = pstmt.executeUpdate();

                    pstmt.close();
                    pstmt = null;

                    /*
                    if ( result > 0 ) {
                        connMgr.commit();
                    } else {
                        connMgr.rollback();
                    }
                    */

                } else {
                    System.out.println("인증 타입은 이메일");

                    StringBuffer contSb = new StringBuffer();

                    contSb.append("<div style=\"font-weight:bold; font-size:13px; \">안녕하세요,").append(userName).append(" 고객님,<br/>임시 비밀번호는 다음과 같습니다.<br/><br/>");
                    contSb.append("<ul>");
                    contSb.append("<li>임시 비밀번호 : <span style=\"color:#5c82e8;\">").append( newPassword ).append( "</span></li><br/>");
                    contSb.append("</ul>");
                    contSb.append("<span style=\"font-weight:bold;\"><a href=\"http://edu.kocca.or.kr/\" target=\"_blank\">EDUKOCCA 바로가기</a></span><br/><br/>");
                    contSb.append("<span style=\"font-weight:bold;\">새로 발급받은 비밀번호로 로그인 후 비밀번호를 변경하시기 바랍니다.</span></div>");

                    TcpipClient tc = new TcpipClient(); // 메일발송 socket connection open
                    tc.open("mail2.kbi.re.kr", 9110);   //9100 추후변경

                    tc.setAID("34");
                    tc.setArg("INAME", "");
                    tc.setArg("IUSERID", "");
                    tc.setArg("IEMAIL", certiStr);
                    tc.setArg("TITLE", "[EDUKOCCA] 임시 비밀번호 발급 안내 메일입니다.");
                    tc.setData("CONTENT", contSb.toString() );

                    // 메일 발송
                    String sendMailResult = tc.commit();
                    // String sendMailResult = "250";

                    if ( sendMailResult.equals("250") ) {

                        System.out.println("이메일 발송 성공");

                        int maxSeq = 0;
                        sql.setLength(0);
                        sql.append("SELECT NVL( MAX ( SEQ ), 0 ) + 1 AS MAX_SEQ FROM TZ_EMAIL_CERTI_HISTORY ");

                        ls = connMgr.executeQuery(sql.toString());
                        if ( ls.next() ) {
                            maxSeq = ls.getInt("MAX_SEQ");
                        }

                        sql.setLength(0);
                        sql.append("/* 이메일 임시비밀번호 발송 기록 저장 */\n");
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
                        sql.append("    ,   ''  \n");
                        sql.append("    ,   SYSDATE \n");
                        sql.append("    ,   'P'     \n");
                        sql.append("    )   \n");

                        pstmt = connMgr.prepareStatement(sql.toString());
                        pstmt.setInt( 1, maxSeq );
                        pstmt.setString( 2, certiStr );
                        pstmt.setString( 3, newPassword );

                        System.out.println("이메일 발송 - maxSeq : " + maxSeq);
                        System.out.println("이메일 발송 - certiStr : " + certiStr);
                        System.out.println("이메일 발송 - newPassword : " + newPassword);

                        sendResult = pstmt.executeUpdate();

                        System.out.println("이메일 로그 기록 결과 : " + sendResult);

                        pstmt.close();
                        pstmt = null;

                        /*
                        if ( result > 0 ) {
                            connMgr.commit();
                        } else {
                            connMgr.rollback();
                        }
                        */
                    }
                }
/**========================================================**/

                if ( sendResult > 0 ) {
                    System.out.println("이메일 로그 기록 성공 후 회원 정보 갱신");
                    sql.setLength(0);
                    sql.append("/* 회원테이블에 임시 비밀번호 저장 */   \n");
                    sql.append("UPDATE  TZ_MEMBER   \n");
                    sql.append("   SET  PWD = ?     \n");
                    sql.append(" WHERE  USERID = ?  \n");

                    pstmt = connMgr.prepareStatement(sql.toString());

                    pstmt.setString(1, HashCipher.createHash(newPassword));
                    pstmt.setString(2, userId);

                    updateResult = pstmt.executeUpdate();

                    pstmt.close();
                    pstmt = null;
                }

                if ( result + sendResult + updateResult > 2 ) {
                    System.out.println("DB commit");
                    connMgr.commit();
                } else {
                    System.out.println("DB rollback");
                    connMgr.rollback();
                    return 0;
                }

            } else {
                return 0;
            }

        } catch (Exception e) {
            Log.err.println(e.getMessage());
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("checkUserPassword()\r\n" + e.getMessage());
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

        return result + sendResult + updateResult;
    }

    /**
     * 난수를 발생시켜 임의의 비밀번호를 생성한 후 리턴한다.
     */
    private String getRandomString(int randomStrLength) throws Exception {
        int index = 0;

        char[] charSet = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'
            , 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'
            , 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };

        StringBuffer sb = new StringBuffer();

        try {

            for( int i = 0 ; i < randomStrLength ; i++ ) {
                index = (int)(charSet.length * Math.random() );
                sb.append(charSet[index]);
            }

            System.out.println("임시 비번 : " + sb.toString() );
        } catch (Exception e) {
            Log.err.println(e.getMessage());
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("getRandomString()\r\n" + e.getMessage());
        }

        return sb.toString();
    }
%>