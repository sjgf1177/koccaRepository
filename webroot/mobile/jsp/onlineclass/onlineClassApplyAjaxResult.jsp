<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    JSONObject jsonObj = new JSONObject();

    String resultMsg = (String)request.getAttribute("resultMsg");

    try {
        jsonObj.put("resultMsg", resultMsg);

        System.out.println( jsonObj.toJSONString().replace("\\\\", "/") );
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>

