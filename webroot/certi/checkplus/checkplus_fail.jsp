<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.credu.library.DBConnectionManager" %>
<%@ page import="com.credu.library.ErrorManager" %>
<%@ page import="com.credu.library.ListSet" %>
<%@ page import="com.credu.library.Log" %>
<%@ page import="NiceID.Check.CPClient" %>

<%
    CPClient niceCheck = new  CPClient();

    String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");
    String sReserved1  = requestReplace(request.getParameter("param_r1"), "");
    String sReserved2  = requestReplace(request.getParameter("param_r2"), "");
    String sReserved3  = requestReplace(request.getParameter("param_r3"), "");

    String sSiteCode = "G7889";             // NICE로부터 부여받은 사이트 코드
    String sSitePassword = "KCQJDMUIH9ZI";  // NICE로부터 부여받은 사이트 패스워드

    String sCipherTime = "";        // 복호화한 시간
    String sRequestNumber = "";     // 요청 번호
    String sErrorCode = "";         // 인증 결과코드
    String sAuthType = "";          // 인증 수단
    String sMessage = "";
    String sPlainData = "";

    System.out.println("본인인증 결과 실패 페이지 시작<br/>");
    String[] errCodeArr = {"0001", "0011", "0012", "0013", "0014", "0015", "0016", "0017", "0018", "0020", "0021", "0022", "0031", "0032", "0033", "0034", "0050", "9998", "9999", "9097"};
    String[] errMessageArr = {"인증불일치(사용자인증, 인증번호 불일치)", "유효하지 않은 응답 SEQ", "유효하지 않은 인증정보(주민번호, 휴대폰번호, 이통사)", "암호화 데이터 처리오류", "암호화 프로세스 오류", "암호화 데이터 오류", "복호화 프로세스 오류", "복호화 데이터 오류", "이통사 통신오류", "유효하지 않은 제휴사 코드", "중단된 제휴사 코드", "휴대폰인증 사용이 불가한 제휴사 코드", "인증번호 확인 실패(해당 데이터 없음)", "인증번호 확인 실패(주민번호 불일치)", "인증번호 확인 실패(요청SEQ 불일치)", "인증번호 확인 실패(기 처리건)", "명의도용 차단서비스 가입자", "본인인증 결과값 전달 실패", "정의되지 않은 오류", "인증번호 입력 3회 오류"};

    HashMap mapresult = new HashMap();
    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

    if( iReturn == 0 ) {
        sPlainData = niceCheck.getPlainData();
        sCipherTime = niceCheck.getCipherDateTime();

        // 데이타를 추출합니다.
        mapresult = niceCheck.fnParse(sPlainData);

        mapresult.put("CIPHER_TIME", sCipherTime);

        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
        sErrorCode = (String)mapresult.get("ERR_CODE");
        sAuthType = (String)mapresult.get("AUTH_TYPE");

        int index = 0;
        for ( int i = 0 ; i < errCodeArr.length ; i++ ) {
            if ( errCodeArr[i].equals( sErrorCode ) ) {
                index = i;
                break;
            }
        }
        // sMessage = "다음과 같은 오류가 발생하였습니다.\"\n\"" + errMessageArr[index];
        sMessage = errMessageArr[index];

    } else if( iReturn == -1) {
        sMessage = "복호화 시스템 오류";

    } else if( iReturn == -4) {
        sMessage = "복호화 처리 오류";

    } else if( iReturn == -5) {
        sMessage = "복호화 해쉬 오류";

    } else if( iReturn == -6) {
        sMessage = "복호화 데이터 오류";

    } else if( iReturn == -9) {
        sMessage = "입력 데이터 오류";

    } else if( iReturn == -12) {
        sMessage = "사이트 패스워드 오류";

    } else {
        sMessage = "알수 없는 오류";
    }

    if( iReturn != 0 ) {
        mapresult.put("ERR_CODE", String.valueOf(iReturn));
    }
    mapresult.put("RESULT_MSG", sMessage);

    this.insertMobileCertiHistory(mapresult);
%>
<%!
    /**
     *
     */
    public static String requestReplace (String paramValue, String gubun) {
        String result = "";

        if (paramValue != null) {

            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");

            if(gubun != "encodeData"){
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
            }

            result = paramValue;

        }
        return result;
    }

    /**
     * 인증 기록 저장
     */
    private void insertMobileCertiHistory( HashMap certiMap ) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        int maxSeq = 0;
        int resultCount = 0;
        int index = 1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql.append("SELECT NVL( MAX ( SEQ ), 0 ) + 1 AS MAX_SEQ FROM TZ_MOBILE_CERTI_HISTORY ");

            ls = connMgr.executeQuery(sql.toString());
            if ( ls.next() ) {
                maxSeq = ls.getInt("MAX_SEQ");
            }

            sql.setLength(0);
            sql.append("/* 휴대전화 인증 기록 저장 (실패) */\n");
            sql.append("INSERT  INTO    TZ_MOBILE_CERTI_HISTORY (   \n");
            sql.append("        SEQ                                 \n");
            sql.append("    ,   DEC_DT                              \n");
            sql.append("    ,   REQ_NO                              \n");
            sql.append("    ,   RESULT_CD                           \n");
            sql.append("    ,   CERTI_SATUS                         \n");
            sql.append("    ,   RESULT_MSG                          \n");
            sql.append("    ,   CERTI_DT                            \n");
            sql.append(") VALUES (                                  \n");
            sql.append("        ?                                   \n");
            sql.append("    ,   TO_DATE(?, 'YYMMDDHH24MISS')        \n");
            sql.append("    ,   ?                                   \n");
            sql.append("    ,   ?                                   \n");
            sql.append("    ,   'F'                                 \n");
            sql.append("    ,   ?                                   \n");
            sql.append("    ,   SYSDATE                             \n");
            sql.append(")                                           \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt( index++, maxSeq );
            pstmt.setString( index++, (String)certiMap.get("CIPHER_TIME") );
            pstmt.setString( index++, (String)certiMap.get("REQ_SEQ") );
            pstmt.setString( index++, (String)certiMap.get("ERR_CODE") );
            pstmt.setString( index++, (String)certiMap.get("RESULT_MSG") );

            resultCount = pstmt.executeUpdate();

            if ( resultCount > 0 ) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            Log.err.println(e.getMessage());
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("insertMobileCertiHistory()\r\n" + e.getMessage());
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

    }

%>
<!doctype html>
<html>
<head>
<title>안심본인인증 결과 페이지 | 한국콘텐츠아카데미</title>

<script type="text/javascript">
    window.onload = function() {
        alert("다음과 같은 오류가 발생하였습니다.\n[<%= sMessage %>]");
        self.close();
    };
</script>

</head>
<body>
<!--
    <center>
    <p><p><p><p>
    본인인증이 실패하였습니다.<br>
    <table border=1>
        <tr>
            <td>복호화한 시간</td>
            <td><%= sCipherTime %> (YYMMDDHHMMSS)</td>
        </tr>
        <tr>
            <td>요청 번호</td>
            <td><%= sRequestNumber %></td>
        </tr>
        <tr>
            <td>본인인증 실패 코드</td>
            <td><%= sErrorCode %></td>
        </tr>
        <tr>
            <td>인증수단</td>
            <td><%= sAuthType %></td>
        </tr>
        <tr>
            <td>RESERVED1</td>
            <td><%= sReserved1 %></td>
        </tr>
        <tr>
            <td>RESERVED2</td>
            <td><%= sReserved2 %></td>
        </tr>
        <tr>
            <td>RESERVED3</td>
            <td><%= sReserved3 %></td>
        </tr>
    </table><br><br>
    <%= sMessage %><br>
    </center>
//-->
</body>
</html>