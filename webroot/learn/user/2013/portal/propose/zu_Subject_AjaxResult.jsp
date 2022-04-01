<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="org.json.simple.JSONObject" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    JSONObject jsonObj = new JSONObject();

    ArrayList reviewList = (ArrayList)request.getAttribute("reviewList");

    try {
        if (reviewList == null || reviewList.size() == 0 ) {
            jsonObj.put("result", "null");
        } else {
            jsonObj.put("reviewList", reviewList);

        }

        System.out.println(jsonObj.toJSONString().replace("\\\\", "/"));
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>
