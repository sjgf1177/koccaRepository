<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ page import="org.json.simple.JSONObject" %><%@ page import="java.util.HashMap" %><%@ page import="java.util.Map" %><%@ page import="java.util.ArrayList" %><%@ page import="com.credu.library.DataBox" %><%@ page import="com.credu.complete.FinishBean" %>
<%@ page import="com.credu.library.*" %>
<%
	JSONObject jsonObj = new JSONObject();
    JSONObject menuJsonObj = new JSONObject();
    JSONObject erroJosnObj = new JSONObject();

    ArrayList resultList = new ArrayList();
    ArrayList jsonList = new ArrayList();
    DataBox dbox = null;
    Map map = null;

    FinishBean bean = new FinishBean();

    try {
    	
    	RequestBox box = (RequestBox)request.getAttribute("requestbox");
        if (box == null) {
            box = RequestManager.getBox(request);
        }
        
        box.put("startDate",request.getParameter("startDate"));
    	box.put("endDate",  request.getParameter("endDate"));
        
    	resultList = bean.getKentResultList(box);

        if ( resultList.size() > 0 ) {
            for( int i = 0; i < resultList.size() ; i++ ) {
                dbox = (DataBox)resultList.get(i);

                map = new HashMap();
                map.put("ID", dbox.getString("d_userid") );
                map.put("MOKCHA", dbox.getString("d_subj").equals("CK15031") ? "1" : "2");
                map.put("APLY_DT", dbox.getString("d_edustart") );
                map.put("PROGRESS", dbox.getString("d_tstep") );
                map.put("ISU_DT", dbox.getString("d_eduend") );
                map.put("ISU", dbox.getString("d_isgraduated") );
                jsonList.add(map);
            }
        }

        erroJosnObj.put("errCode", "0");
        erroJosnObj.put("message", "");

        menuJsonObj.put("positon", "right");
        menuJsonObj.put("items", jsonList);
        // menuJsonObj.put("error", erroJosnObj);

        jsonObj.put("menu", menuJsonObj);
        jsonObj.put("error", erroJosnObj);
    } catch (Exception e) {
        throw new Exception("[대중문화예술 결과 조회 오류]");
    }

    out.print(jsonObj.toJSONString().replace("\\", ""));
    out.flush();
%>