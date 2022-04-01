<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%
    String[] deviceTypeArr = {"iphone", "ipod", "ipad", "blackberry", "android", "windows ce", "lg", "mot", "samsung", "sonyericsson", "chrome", "safari", "msie", "firefox"};

	String userAgent = request.getHeader("User-Agent").toLowerCase();

    String appURL = "";

    if ( userAgent.indexOf("android") > -1 ) {
        appURL = "https://play.google.com/store/apps/details?id=com.newin.kocca";
    } else {
        appURL = "https://itunes.apple.com/us/app/kocca/id492151283?l=ko&ls=1&mt=8";
    }

    response.sendRedirect( appURL );
%>
