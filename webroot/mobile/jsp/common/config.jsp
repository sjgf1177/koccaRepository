<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ page import="org.json.simple.JSONObject" %><%@ page import="com.credu.mobile.common.DeviceInfoBean" %><%
    request.setCharacterEncoding("utf-8");
    JSONObject obj = new JSONObject();
    JSONObject obj2 = new JSONObject();
    JSONObject obj3 = new JSONObject();
    String deviceToken = request.getParameter("devicetoken");
    String platform = request.getParameter("platform");
    String message = "작업도중 오류가 발생하였습니다. 관리자에게 문의하세요.";
    message = "";
    deviceToken = ( deviceToken == null ) ? "" : deviceToken;
    platform = ( platform == null ) ? "" : platform;
    obj2.put("iosVersion", "2.0.5");
    obj2.put("androidVersion", "4.0.7");
    obj2.put("uiVersion", "1.0");
    obj2.put("appleSetupURL", "https://itunes.apple.com/us/app/kocca/id492151283?l=ko&ls=1&mt=8");
    obj2.put("androidSetupURL", "https://play.google.com/store/apps/details?id=com.newin.kocca");
    obj2.put("uiURL", "/mobile/jsp/common/menu.jsp");
    obj2.put("serviceDomain", "http://m.edu.kocca.kr");
    obj2.put("rootURL", "/servlet/controller.mobile.openclass.OpenClassPopularServlet");
    obj2.put("startURL", "/servlet/controller.mobile.openclass.OpenClassPopularServlet");
    obj2.put("startTitle", "열린강좌");
    obj2.put("startLoginURL", "/mobile/jsp/common/checkUserStudyStatus.jsp");
    obj2.put("startLoginTitle", "나의강의실");
    obj2.put("loginURL", "/mobile/html/user/login.html");
    obj2.put("searchURL", "/mobile/jsp/subj/searchSubject.jsp?keyword=");
    obj2.put("notice", "");
    obj2.put("deviceToken", deviceToken);
    obj2.put("platform", platform);
    obj2.put("userNameCookie", "username");
    obj2.put("passwordCookie", "pwd");
    obj3.put("errCode", "0");
    obj3.put("message", message);
    obj.put("setting", obj2);
    obj.put("error", obj3);
    out.print(obj.toJSONString().replace("\\", ""));
    out.flush();

    session.setAttribute("platform", platform);
    if ( !deviceToken.equals("") && !platform.equals("") ) {
        DeviceInfoBean bean = new DeviceInfoBean();
        int regReulstCnt = bean.registerDeviceInfo(deviceToken, platform);

        System.out.println("regResultCnt in jsp : " + regReulstCnt);
    }
%>
