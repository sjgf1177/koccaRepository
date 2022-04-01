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
    // 인증 화면을 호출한 구분값을 가지고 있다.
    // regForm: 회원가입 / findPwd: 비밀번호 찾기
    String sReserved1 = requestReplace(request.getParameter("param_r1"), "");
    String sReserved2 = requestReplace(request.getParameter("param_r2"), "");
    String sReserved3 = requestReplace(request.getParameter("param_r3"), "");

    String resultPage = "";
    if (sReserved1.equals("regMember") ) {
        // resultPage = "/learn/user/2013/portal/member/zu_MemberInfo_I_new.jsp";
        resultPage = "/servlet/controller.member.MemberJoinRegServlet";
    } else if (sReserved1.equals("findPwd") ) {
        resultPage = "/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd";
    }

    String sSiteCode = "G7889";             // NICE로부터 부여받은 사이트 코드
    String sSitePassword = "KCQJDMUIH9ZI";  // NICE로부터 부여받은 사이트 패스워드

    String sCipherTime = "";        // 복호화한 시간
    String sRequestNumber = "";     // 요청 번호
    String sResponseNumber = "";    // 인증 고유번호
    String sAuthType = "";          // 인증 수단
    String sName = "";              // 성명
    String sDupInfo = "";           // 중복가입 확인값 (DI_64 byte)
    String sConnInfo = "";          // 연계정보 확인값 (CI_88 byte)
    String sBirthDate = "";         // 생일
    String sGender = "";            // 성별
    String sNationalInfo = "";      // 내/외국인정보 (개발가이드 참조)
    String sMessage = "";
    String sPlainData = "";

    String sMobileNo = "";
    // String sCarrier = "";

    boolean isSessionDifferent = false;

    System.out.println("본인인증 결과 성공 페이지 시작<br/>");

    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);
    System.out.println("iReturn : " + iReturn + "<br/>");

    if( iReturn == 0 ) {
        sPlainData = niceCheck.getPlainData();
        sCipherTime = niceCheck.getCipherDateTime();

        // 데이타를 추출합니다.
        HashMap mapresult = niceCheck.fnParse(sPlainData);

        mapresult.put("CIPHER_TIME", sCipherTime);

        sRequestNumber = (String)mapresult.get("REQ_SEQ");
        sResponseNumber = (String)mapresult.get("RES_SEQ");
        sAuthType = (String)mapresult.get("AUTH_TYPE");
        sName = (String)mapresult.get("NAME");
        sBirthDate = (String)mapresult.get("BIRTHDATE");
        sGender = (String)mapresult.get("GENDER");
        sNationalInfo = (String)mapresult.get("NATIONALINFO");
        sDupInfo = (String)mapresult.get("DI");
        sConnInfo = (String)mapresult.get("CI");
        sMobileNo = (String)mapresult.get("MOBILE_NO");
        // sCarrier = (String)mapresult.get("MOBILE_CO");

        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");

        if(!sRequestNumber.equals(session_sRequestNumber)) {
            sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
            sResponseNumber = "";
            sAuthType = "";
            isSessionDifferent = true;
        } else {
            // 인증 로그 기록
            this.insertMobileCertiHistory( mapresult );
        }

    } else if( iReturn == -1) {
        sMessage = "복호화 시스템 에러입니다.";

    } else if( iReturn == -4) {
        sMessage = "복호화 처리오류입니다.";

    } else if( iReturn == -5) {
        sMessage = "복호화 해쉬 오류입니다.";

    } else if( iReturn == -6) {
        sMessage = "복호화 데이터 오류입니다.";

    } else if( iReturn == -9) {
        sMessage = "입력 데이터 오류입니다.";

    } else if( iReturn == -12) {
        sMessage = "사이트 패스워드 오류입니다.";

    } else {
        sMessage = "알수 없는 에러 입니다.";
    }

%>
<%!
    /**
     * 인증 기록 저장
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
            sql.append("/* 휴대전화 인증 기록 저장 (성공) */\n");
            sql.append("INSERT  INTO    TZ_MOBILE_CERTI_HISTORY (   \n");
            sql.append("        SEQ                                 \n");
            sql.append("    ,   DEC_DT                              \n");
            sql.append("    ,   REQ_NO                              \n");
            sql.append("    ,   RES_NO                              \n");
            sql.append("    ,   MOBILE_NO                           \n");
            sql.append("    ,   RESULT_CD                           \n");
            sql.append("    ,   CERTI_SATUS                         \n");
            sql.append("    ,   NAME                                \n");
            sql.append("    ,   RESULT_MSG                          \n");
            sql.append("    ,   CERTI_DT                            \n");
            sql.append(") VALUES (                                  \n");
            sql.append("        ?                                   \n");
            sql.append("    ,   TO_DATE(?, 'YYMMDDHH24MISS')        \n");
            sql.append("    ,   ?                                   \n");
            sql.append("    ,   ?                                   \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?)             \n");
            sql.append("    ,   ?                                   \n");
            sql.append("    ,   'S'                                 \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?)             \n");
            sql.append("    ,   ?                                   \n");
            sql.append("    ,   SYSDATE                             \n");
            sql.append(")                                           \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt( index++, maxSeq );
            pstmt.setString( index++, (String)certiMap.get("CIPHER_TIME") );
            pstmt.setString( index++, (String)certiMap.get("REQ_SEQ") );
            pstmt.setString( index++, (String)certiMap.get("RES_SEQ") );
            pstmt.setString( index++, (String)certiMap.get("MOBILE_NO") );
            pstmt.setString( index++, "0000" );
            pstmt.setString( index++, (String)certiMap.get("NAME") );
            pstmt.setString( index++, "인증 성공");

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

    }
%>
<!doctype html>
<html>
<head>
<title>안심본인인증 결과 페이지 | 한국콘텐츠아카데미</title>
<script src="/js/jquery/1.8.3/jquery.js"></script>
<script type="text/javascript">
    $(function() {
        // var isSessionDifferent = <%= isSessionDifferent %>;
        var iReturn = <%= iReturn %>;
        var resultMessage = "<%= sMessage %>";

        if ( iReturn != 0 && resultMessage != "" ) {
            alert(resultMessage);
            self.close();

        } else {
            if ( parent.opener.name == "Parent_window" ) {
                $("#oCertiResultForm").attr("target", parent.opener.name)
                $("#oCertiResultForm").submit();

            } else {
                alert("부모 창이 변경되어 회원 가입을 진행할 수 없습니다.");
            }
            self.close();
        }

    });
</script>

</head>
<body>
<!-- <form id="oCertiResultForm" name="certiResultForm" action="/servlet/controller.homepage.MainMemberJoinServlet" method="post"> //-->
<form id="oCertiResultForm" name="certiResultForm" action="<%= resultPage %>" method="post">
    <input type="hidden" id="oCertiType" name="certiType" value="MOBILE" />
    <input type="hidden" id="oUserName" name="userName" value="<%= sName %>" />
    <input type="hidden" id="oMobileNo" name="mobileNo" value="<%= sMobileNo %>" />
    <input type="hidden" id="oDupInfo" name="dupInfo" value="<%= sDupInfo %>" />
    <input type="hidden" id="oBirthDate" name="birthDate" value="<%= sBirthDate %>" />
    <input type="hidden" id="oGender" name="gender" value="<%= sGender %>" />
    <input type="hidden" id="oNationalInfo" name="nationalInfo" value="<%= sNationalInfo %>" />
</form>

</body>
</html>