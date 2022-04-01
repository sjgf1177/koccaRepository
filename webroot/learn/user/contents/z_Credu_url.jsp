<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="java.util.ArrayList" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    JSONObject jsonObj = new JSONObject();

    String videoURL = (String)request.getAttribute("videoURL");

    try {
        if (videoURL == null || videoURL.equals("")) {
            jsonObj.put("result", "null");
        } else {
            jsonObj.put("videoURL", videoURL);

        }

         //System.out.println(jsonObj.toJSONString().replace("\\\\", "/"));
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>
