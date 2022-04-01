<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.RequestManager" %>
<%
    // RequestBox box = (RequestBox)request.getAttribute("requestbox");
    RequestBox box = RequestManager.getBox(request);

    String[] deviceTypeArr = {"iphone", "ipod", "ipad", "blackberry", "android", "windows ce", "lg", "mot", "samsung", "sonyericsson", "chrome", "safari", "msie", "firefox"};

	String userAgent = request.getHeader("User-Agent");
    String deviceType = "";

    String sUserId = box.getSession("userid");
    String sGrcode = box.getSession("grcode");
    sGrcode = (sGrcode == null || sGrcode.equals("")) ? "N000001" : sGrcode;

    boolean isWeb = false;
    boolean isApp = false;
    boolean isLogin = false;
    boolean isB2C = sGrcode.equals("N000001");

    for ( int i = 0 ; i < deviceTypeArr.length; i++ ) {
        if ( userAgent.toLowerCase().indexOf( deviceTypeArr[i]) > -1) {
            request.setAttribute("device_type", deviceTypeArr[i]);
            deviceType = deviceTypeArr[i];
            isWeb = true;
            // System.out.println("isWeb is true. You're connecting from " + deviceTypeArr[i] + "!!");
            break;
        }
    }

    if ( !isWeb && userAgent.indexOf("KoccaApplication") > -1) {
        isApp = true;
        // System.out.println("isApp is true. You're connecting from mobil app!!");
    }

    if( !box.getSession("userid").equals("") ) {
        isLogin = true;
    }

    String naviType = ( isApp ) ? "1" : "2";

    boolean isCheckApp = false;
    Cookie[] cookies = request.getCookies();

    if(cookies!=null) {
        for(int i = 0 ; i < cookies.length ; i++) {
            // out.println(cookies[i].getName() + " : " + cookies[i].getValue());
            if ( cookies[i].getName().equals("isCheckApp") ) {
                isCheckApp = cookies[i].getValue().equals("true") ;
                break;
            }
        }
    }

    // out.println("접속 기기 혹은 브라우저 정보 : " + userAgent);
%>
