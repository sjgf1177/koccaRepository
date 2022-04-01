<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import="com.credu.common.KoccaTagBean" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.json.simple.JSONObject" %>

<%
    // ajax를 이용하여 select box와 관련된 값을 조회할 때 사용하는 페이지이다.

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if ( box == null ) {
        box = RequestManager.getBox(request);
    }
    JSONObject jsonObj = new JSONObject();

    try {
        String type = box.getString("type");
        String sqlID = box.getStringDefault("sqlID", "");
        String param = box.getStringDefault("param", "");
        String codeValue = box.getStringDefault("codeValue", "");

        ArrayList<DataBox> selectBoxList = new ArrayList<DataBox>();

        if ( type != null && type.equals("sqlID")) {
            if ( param != null ) {
                selectBoxList = KoccaTagBean.getSelectBoxListBySqlID(sqlID, param);
            } else {
                selectBoxList = KoccaTagBean.getSelectBoxListBySqlID(sqlID);
            }
            
        } else {
            selectBoxList = KoccaTagBean.getSelectBoxListByCode(codeValue);
        }

        jsonObj.put("selectBoxList", selectBoxList);

    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( jsonObj.toJSONString().replace("\\\\", "/") );
%>
