<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="org.json.simple.JSONObject" %>

<%
    // ajax 처리후 ArrayList로 결과 값을 받는 기능에서 공통으로 사용하고자 함.
    // 이 페이지를 사용하기 위해서는 ArrayList로 넘겨줄 때 반드시 이름을 "resultList"로 해야하며
    // 결과 자료를 받는 페이지에서 사용하고자 하는 이름이 있을 경우에는 ArrayList로 넘겨주기 전에
    // request.setAttribute("returnName", "listName");처럼 원하는 이름을 지정해주어야 한다.

    JSONObject jsonObj = new JSONObject();

    ArrayList<DataBox> resultList = (ArrayList<DataBox>)request.getAttribute("resultList");
    String returnName = (String) request.getAttribute("returnName");

    returnName = ( returnName == null || returnName.equals("") ) ? "resultList" : returnName;

    try {
        if (resultList == null || resultList.size() == 0 ) {
            jsonObj.put(returnName, "null");
        } else {
            jsonObj.put(returnName, resultList);
        }

        // System.out.println(jsonObj.toJSONString().replace("\\\\", "/"));
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>
