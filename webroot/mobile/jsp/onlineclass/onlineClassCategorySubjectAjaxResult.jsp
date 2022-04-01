<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.json.simple.JSONObject" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList categorySubjectList = (ArrayList)request.getAttribute("categorySubjectList");

    JSONObject jsonObj = new JSONObject();

    try {
        if (categorySubjectList == null || categorySubjectList.size() == 0 ) {
            jsonObj.put("result", "null");
        } else {
            jsonObj.put("categorySubjectList", categorySubjectList);
        }

        // System.out.println( jsonObj.toJSONString().replace("\\\\", "/") );
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e.getMessage());
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
    System.out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>

