<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="java.util.ArrayList" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    JSONObject jsonObj = new JSONObject();

    ArrayList openClassCategoryList = (ArrayList)request.getAttribute("openClassCategoryList");

    try {
        if (openClassCategoryList == null && openClassCategoryList.size() > 0 ) {
            jsonObj.put("result", "null");
        } else {
            jsonObj.put("openClassCategoryList", openClassCategoryList);

        }

         //System.out.println(jsonObj.toJSONString().replace("\\\\", "/"));
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>
