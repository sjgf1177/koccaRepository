<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date dt = new Date();
    String logTime = sdf.format(dt);

    out.println("date : " + dt + "<br/><br/>");
    out.println("logTime : " + logTime);

/*
    String p_cpCheck = (String)request.getParameter("p_cpCheck");
    String p_cpcomp = (String)request.getParameter("p_cpcomp");

    String[] cpEduResultData = request.getParameterValues("p_cpEduResultData");

    System.out.println("p_cpCheck : " + p_cpCheck);
    System.out.println("p_cpcomp : " + p_cpcomp);
    System.out.println("cpEduResultData length : " + cpEduResultData.length);
    int i = 0;

    for ( i = 0 ; i < cpEduResultData.length; i++ ) {
        System.out.println("cpEduResultData[" + i + "] : " + cpEduResultData[i]);
    }

    StringBuffer sb = new StringBuffer();
    sb.append("{\n");
    sb.append("\"message\": \"¼º°ø\",\n");
    sb.append("\"result\": \"S\",\n");
    sb.append("\"rcnt\": \"").append(i).append("\"");
    sb.append("}\n");

    System.out.println( sb.toString() + "\n");
    out.println( sb.toString() );
*/
%>
