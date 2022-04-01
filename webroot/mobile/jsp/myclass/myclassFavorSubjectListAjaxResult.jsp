<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import = "org.json.simple.JSONObject" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList favorSubjectList = (ArrayList)request.getAttribute("favorSubjectList");

    JSONObject jsonObj = new JSONObject();

    try {
        if (favorSubjectList == null && favorSubjectList.size() > 0 ) {
            jsonObj.put("result", "null");
        } else {
            jsonObj.put("favorSubjectList", favorSubjectList);
        }

        // System.out.println( jsonObj.toJSONString().replace("\\\\", "/") );
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e.getMessage());
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>

