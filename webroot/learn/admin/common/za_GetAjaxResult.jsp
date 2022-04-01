<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="org.json.simple.JSONObject" %>

<%
    // ajax 처리후 단순 결과 값을 받는 기능에서 공통으로 사용하고자 함. 결과 값은 주로 String 사용.
    // 이 페이지를 사용하기 위해서는 결과 값을 넘겨줄 때 반드시 이름을 "result"로 해야하며
    // 결과 자료를 받는 페이지에서 사용하고자 하는 이름이 있을 경우에는
    // request.setAttribute("returnName", "dataName");처럼 원하는 지정해주어야 한다.

    JSONObject jsonObj = new JSONObject();

    Object result = (Object)request.getAttribute("result");
    String returnName = (String)request.getAttribute("returnName");

    returnName = ( returnName == null || returnName.equals("") ) ? "result" : returnName;

    try {
        jsonObj.put(returnName, result);

        // System.out.println(jsonObj.toJSONString().replace("\\\\", "/"));
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>
