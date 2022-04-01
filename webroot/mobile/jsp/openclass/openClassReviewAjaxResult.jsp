<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int resultCnt = box.getInt("resultCnt");

    StringBuffer result = new StringBuffer();

    try {
        result.append("{\"resultCnt\": ").append(resultCnt).append("}");

    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e.getMessage());
    }

    out.println( result.toString() );
%>
