<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	response.setHeader("Content-Disposition", "attachment; filename=za_off_SudentJob_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    ArrayList list    = (ArrayList)request.getAttribute("selectList");       // 리스트
    
    String v_subj         = "";
    String v_subjnm       = "";
    String v_subjseq      = "";
    String v_userid       = "";
    String v_name         = "";
    String v_studentno    = "";
    String v_state        = "";
    String v_statenm      = "";
    String v_ldate        = "";
    String v_confirmdate  = "";
    String v_isterm       = "";
    String v_isgraduated  = "";
    
    
    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    
    int     v_totalrowcount =  0;
    int     v_dispnum   = 0;           // 총게시물수
    int     v_totalpage = 0;           // 게시물총페이지수

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
    <table border=1>
          <tr>
            <td><b>NO</b></td>
            <td>과정명</td>
            <td>차수</td>
            <td>ID</td>
            <td>성명</td>
            <td>학번</td>
            <td>학적</td>
            <td>학적변경일자</td>
            <td>입학일자</td>
          </tr>
          <%
        if(list !=null && list.size() > 0){
            for(int i = 0 ; i < list.size() ; i++) {
                DataBox dbox   = (DataBox)list.get(i);
                v_subj         = dbox.getString("d_subj");       
                v_subjnm       = dbox.getString("d_subjnm");     
                v_subjseq      = dbox.getString("d_subjseq");
                v_userid       = dbox.getString("d_userid");
                v_name         = dbox.getString("d_name");       
                v_studentno    = dbox.getString("d_studentno");  
                v_state        = dbox.getString("d_stustatus");      
                v_statenm      = dbox.getString("d_statenm");    
                v_ldate        = dbox.getString("d_ldate");      
                v_confirmdate  = dbox.getString("d_confirmdate");
                v_isterm       = dbox.getString("d_isterm");
                v_isgraduated  = dbox.getString("d_isgraduated");
                
                if(v_isterm.equals("N") && v_isgraduated.equals("Y")){
                    v_statenm = "수료";
                } else if(v_isterm.equals("N") && !v_isgraduated.equals("Y")){
                    v_statenm = "미수료";
                } else if(v_isterm.equals("N") && v_isgraduated.equals("")){
                    v_statenm = "미처리";
                }
                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
        %>
        <tr>
          <td><%= v_dispnum %></td>
          <td><%= v_subjnm %> </td>
          <td><%= v_subjseq.equals("") ? "" : Integer.parseInt(v_subjseq)+"기" %> </td>
          <td><%= v_userid %> </td>
          <td><%= v_name %> </td>
          <td><%= v_studentno %> </td>
          <td><%= v_statenm %> </td>
          <td><%= FormatDate.getFormatDate(v_ldate, "yyyy-MM-dd") %> </td>
          <td><%= FormatDate.getFormatDate(v_confirmdate, "yyyy-MM-dd") %> </td>
        </tr>
        <%        
            }
        } else {
        %>
        <tr><td colspan=11 class="table_02_1">등록된 과정이 없습니다.</td></tr>
        <% }  %>  
        </table>
</body>
</html>
