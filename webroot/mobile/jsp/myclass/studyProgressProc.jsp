<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>
<%@ page errorPage="/jsp/library/error.jsp" %>
<%@ page import="com.credu.library.ConfigSet" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="java.io.BufferedWriter" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
    StringBuffer sb = new StringBuffer();

    String customInfo = (String)request.getParameter("customInfo");
    // String[] paramArr = null;
    // String[] paramArr2 = null;
    // String paramStr = "";
    // String process = "";
    // String subj = "";
    // String year = "";
    // String subjseq = "";
    // String lesson = "";

    String contentID = (String)request.getParameter("contentID");
    String totalPageNum = (String)request.getParameter("totalPageNum");
    String curPage = (String)request.getParameter("curPage");

    String userid = (String)session.getAttribute("userid");

    String upDir = "";

    Date now = new Date();
    String platform = (String)session.getAttribute("platform");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    try {

        sb.append("[").append(sdf.format(now)).append("] ");
        sb.append("[" + platform + "] ");
        sb.append("customInfo=" + customInfo + "&");
        sb.append("contentID=" + contentID + "&");
        sb.append("totalPageNum=" + totalPageNum + "&");
        sb.append("curPage=" + curPage + "\n");
        // sb.append("progress URL : http://m.edu.kocca.kr/servlet/controller.mobile.myclass.StudySubjectServlet?" + customInfo + "\n\n");

        System.out.println("return parameter : " + sb.toString());
        System.out.println("progress URL : " + "http://m.edu.kocca.kr/servlet/controller.mobile.myclass.StudySubjectServlet?" + customInfo + "\n\n");

        ConfigSet conf = new ConfigSet();
        upDir = conf.getProperty("dir.home");

        File f = new File(upDir + "upload\\studyProgressResult.txt");

        if ( !f.exists() ) {
            f.createNewFile();
        }

        BufferedWriter fw = new BufferedWriter( new FileWriter(f.getAbsoluteFile(), true));
        fw.write(sb.toString() );
        fw.newLine();
        fw.flush();
        fw.close();

    } catch (IOException ie) {
        ie.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>