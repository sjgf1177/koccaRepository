<%
//**********************************************************
//  1. 제      목: 한국신용평가정보 - CheckPlus 본인인증 실패
//  2. 프로그램명 : checkplus_fail.jsp
//  3. 개      요: 한국신용평가정보 - CheckPlus 본인인증 실패
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2011-03-17
//  7. 수      정:
//***********************************************************
%>


<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    Kisinfo.Check.CPClient kisCrypt = new  Kisinfo.Check.CPClient();

    String sEncodeData = request.getParameter("EncodeData");
    String sPlainData = "";
    String sSiteCode = "G1091";				// 한신평정보로부터 부여받은 사이트 코드
    String sSitePassword = "OGUNHRYMMD3M";		// 한신평정보로부터 부여받은 사이트 패스워드
    String sCipherTime = "";						// 복호화한 시간
    String sRequestNumber = "";						// 요청 번호
    String sErrorCode = "";							// 인증 결과코드
    String sAuthType = "";							// 인증 수단
    String sMessage = "";
    
    int iReturn = kisCrypt.fnDecode(sSiteCode, sSitePassword, sEncodeData);

    if( iReturn == 0 )
    {
        sPlainData = kisCrypt.getPlainData();
        sCipherTime = kisCrypt.getCipherDateTime();
        
        // 데이타를 추출합니다.
        java.util.HashMap mapresult = kisCrypt.fnParse(sPlainData);
        
        sRequestNumber = (String)mapresult.get("REQ_SEQ");
        sErrorCode = (String)mapresult.get("ERR_CODE");
        sAuthType = (String)mapresult.get("AUTH_TYPE");
    }
    else if( iReturn == -1)
    {
        sMessage = "복호화 시스템 에러입니다.";
    }    
    else if( iReturn == -4)
    {
        sMessage = "복호화 처리오류입니다.";
    }    
    else if( iReturn == -5)
    {
        sMessage = "복호화 해쉬 오류입니다.";
    }    
    else if( iReturn == -6)
    {
        sMessage = "복호화 데이터 오류입니다.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "입력 데이터 오류입니다.";
    }    
    else if( iReturn == -12)
    {
        sMessage = "사이트 패스워드 오류입니다.";
    }    
    else
    {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }
%>

<html>
<head>
    <title>한국신용평가정보 - CheckPlus 본인인증 테스트</title>
</head>
<body>
    <center>
    <p><p><p><p>
    본인인증이 실패하였습니다.<br>

    <%--필요에 따라 사용할 것
    <table width=450 border=1>
        <tr>
            <td>복호화한 시간</td>
            <td><%= sCipherTime %> (YYMMDDHHMMSSSSS)</td>
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
    </table><br><br>        
    <%= sMessage %><br>
    --%>
    </center>

    <table width=350  cellspacing="0" cellpadding="0">
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
        <td align="center">            
            <a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" width="45" height="20"  border="0" alt="닫기"></a>
        </td>
      </tr>
    </table>
</body>
</html>