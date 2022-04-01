<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.StringManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.mobile.myclass.MyClassBean" %>

<%@ include file="/mobile/jsp/include/init.jsp" %>

<%
    MyClassBean bean = new MyClassBean();
    ArrayList<DataBox> studySubjectList = bean.selectStudySubjectList(box);

    StringBuffer sb = new StringBuffer();
    String redirectURL = "";

    if ( studySubjectList.size() > 0 ) {
        redirectURL = "/servlet/controller.mobile.myclass.StudySubjectServlet";
    } else {
        redirectURL = "/servlet/controller.mobile.openclass.OpenClassPopularServlet";
    }

    sb.append("<!DOCTYPE html>                                                          \n");
    sb.append("<html>                                                                   \n");
    sb.append("<head>                                                                   \n");
    sb.append("<meta charset=\"euc-kr\">                                                \n");
    sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> \n");
    sb.append("<meta name=\"format-detection\" content=\"telephone=no\">                \n");
    sb.append("<title>·Î±×ÀÎ</title>                                                    \n");
    sb.append("<script>                                                                 \n");
    sb.append("    window.onload = function () {                                        \n");
    sb.append("        location.href = \"").append(redirectURL).append("\";             \n");
    sb.append("    };                                                                   \n");
    sb.append("</script>                                                                \n");
    sb.append("<body></body>                                                            \n");
    sb.append("</html>                                                                  \n");

    out.println(sb.toString());
    out.flush();
%>
