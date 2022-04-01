<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "org.json.simple.JSONObject" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList noticeList = (ArrayList)request.getAttribute("noticeList");

    JSONObject jsonObj = new JSONObject();

    StringBuilder result = new StringBuilder();

    int seq = 0;
    int totCnt = 0;
    int rnk = 0;
    String adTitle = "";
    String adContent = "";
    String onOffType = "";

    try {
        if (noticeList == null && noticeList.size() > 0 ) {
            result.append("{result: null}");
        } else {
            jsonObj.put("noticeList", noticeList);

            // System.out.println("jsonObj.toJSONString() : " + jsonObj.toJSONString() + "\n");
        }

    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString() );
%>

