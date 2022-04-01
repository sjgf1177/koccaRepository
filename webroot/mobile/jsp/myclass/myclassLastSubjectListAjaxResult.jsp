<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList lastSubjectList = (ArrayList)request.getAttribute("lastSubjectList");

    JSONObject jsonObj = new JSONObject();

    StringBuilder result = new StringBuilder();

    int seq = 0;
    int totCnt = 0;
    int rnk = 0;
    String adTitle = "";
    String adContent = "";
    String onOffType = "";

    try {
        if (lastSubjectList == null && lastSubjectList.size() > 0 ) {
            jsonObj.put("result", "null");
        } else {
            jsonObj.put("lastSubjectList", lastSubjectList);

            System.out.println("jsonObj.toJSONString() : " + jsonObj.toJSONString() + "\n");
        }

    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString() );
%>

