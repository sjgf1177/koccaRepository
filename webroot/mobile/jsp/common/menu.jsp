<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ page import="org.json.simple.JSONObject" %><%@ page import="java.util.HashMap" %><%@ page import="java.util.Map" %><%@ page import="java.util.ArrayList" %><%@ page import="com.credu.library.DataBox" %><%@ page import="com.credu.mobile.common.MobileMenuBean" %><%
    request.setCharacterEncoding("utf-8");
    JSONObject jsonObj = new JSONObject();
    JSONObject menuJsonObj = new JSONObject();
    JSONObject erroJosnObj = new JSONObject();

    ArrayList menuList = new ArrayList();
    ArrayList jsonList = new ArrayList();
    DataBox dbox = null;
    Map map = null;

    MobileMenuBean bean = new MobileMenuBean();

    try {
        menuList = bean.getMobileMenuList();

        if ( menuList.size() > 0 ) {
            for( int i = 0; i < menuList.size() ; i++ ) {
                dbox = (DataBox)menuList.get(i);

                map = new HashMap();
                map.put("name", dbox.getString("d_menu_name") );
                map.put("url", dbox.getString("d_menu_url") );
                map.put("icon", dbox.getString("d_menu_icon") );
                map.put("login", dbox.getString("d_login_need_yn") );
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
        throw new Exception("[모바일 앱 메뉴 조회 오류]");
    }

    out.print(jsonObj.toJSONString().replace("\\", ""));
    out.flush();
%>