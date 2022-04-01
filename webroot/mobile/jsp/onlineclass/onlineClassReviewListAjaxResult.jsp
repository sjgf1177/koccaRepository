<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    JSONObject jsonObj = new JSONObject();

    ArrayList onlineClassReviewList = (ArrayList)request.getAttribute("onlineClassReviewList");

    try {
        if (onlineClassReviewList == null && onlineClassReviewList.size() > 0 ) {
            jsonObj.put("result", "null");
        } else {
            jsonObj.put("onlineClassReviewList", onlineClassReviewList);
        }

        // System.out.println( jsonObj.toJSONString().replace("\\\\", "/") );
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>

