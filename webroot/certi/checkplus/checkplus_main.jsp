<%@ page language="java" contentType="text/html;charset=euc-kr" %>
<%@ page import="com.mnwise.lts.client.TcpipClient" %>
<%@ page import="NiceID.Check.CPClient" %>
<%
    CPClient niceCheck = new CPClient();

    String sSiteCode = "G7889";             // NICE로부터 부여받은 사이트 코드
    String sSitePassword = "KCQJDMUIH9ZI";  // NICE로부터 부여받은 사이트 패스워드

    String sRequestNumber = "REQ0000000001";            // 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로
                                                        // 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
    sRequestNumber = niceCheck.getRequestNO(sSiteCode);
    session.setAttribute("REQ_SEQ" , sRequestNumber);   // 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
    out.println("sRequestNumber (요청번호) : " + sRequestNumber + "<br/>");

    String sAuthType = "M";          // 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서

    String popgubun = "Y";      //Y : 취소버튼 있음 / N : 취소버튼 없음
    String customize = "";      //없으면 기본 웹페이지 / Mobile : 모바일페이지

    // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
    String sReturnUrl = "http://edu.kocca.or.kr/certi/checkplus/checkplus_success.jsp";      // 성공시 이동될 URL
    String sErrorUrl = "http://edu.kocca.or.kr/certi/checkplus/checkplus_fail.jsp";          // 실패시 이동될 URL

    // 입력될 plain 데이타를 만든다.
    String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                        "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;

    out.println("입력될 plain data : " + sPlainData + "<br/>");
    String sMessage = "";
    String sEncData = "";

    int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
    if( iReturn == 0 ) {
        sEncData = niceCheck.getCipherData();
        out.println("sEncData : " + sEncData + "<br/>");

    } else if( iReturn == -1) {
        sMessage = "암호화 시스템 에러입니다.";

    } else if( iReturn == -2) {
        sMessage = "암호화 처리오류입니다.";

    } else if( iReturn == -3) {
        sMessage = "암호화 데이터 오류입니다.";

    } else if( iReturn == -9) {
        sMessage = "입력 데이터 오류입니다.";

    } else {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }
%>

<html>
<head>
    <title>NICE평가정보 - CheckPlus 안심본인인증 테스트</title>

    <script language='javascript'>
    window.name ="Parent_window";

    function fnPopup(){
        window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
        document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
        document.form_chk.target = "popupChk";
        document.form_chk.submit();
    }
    </script>
</head>
<body>
    <%= sMessage %><br><br>
    업체정보 암호화 데이타 : [<%= sEncData %>]<br><br>

    <!-- 본인인증 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. //-->
    <form name="form_chk" method="post">
        <input type="hidden" name="m" value="checkplusSerivce">         <!-- 필수 데이타로, 누락하시면 안됩니다. //-->
        <input type="hidden" name="EncodeData" value="<%= sEncData %>"> <!-- 위에서 업체정보를 암호화 한 데이타입니다. //-->

        <!-- 업체에서 응답받기 원하는 데이타를 설정하기 위해 사용할 수 있으며, 인증결과 응답시 해당 값을 그대로 송신합니다. 해당 파라미터는 추가하실 수 없습니다. //-->
        <input type="hidden" name="param_r1" value="">
        <input type="hidden" name="param_r2" value="">
        <input type="hidden" name="param_r3" value="">

        <a href="javascript:fnPopup();"> CheckPlus 안심본인인증 Click</a>
    </form>
</body>
</html>


<%

    int index = 0;

    char[] charSet = new char[] {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'
        , 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'
        , 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    StringBuffer sb = new StringBuffer();
    for( int i = 0 ; i < 8 ; i++ ) {
        index = (int)(charSet.length * Math.random() );
        sb.append(charSet[index]);
    }
%>
