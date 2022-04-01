<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%@ include file="/mobile/jsp/include/init.jsp" %>

<%
    String userId = box.getSession("userid") ;

    if ( userId.equals("") ) {
        out.write("{\"isLogin\":false}");
    } else {
        out.write("{\"isLogin\":true}");
    }

    out.flush();
%>
