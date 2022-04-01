<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int resultCnt = Integer.parseInt( String.valueOf( request.getAttribute("resultCnt") ) );

    StringBuffer result = new StringBuffer();

    try {

        result.append("{\"resultCnt\": ").append(resultCnt).append("}");
        // System.out.println( result.toString() );
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e.getMessage());
    }

    out.println( result.toString() );
%>

