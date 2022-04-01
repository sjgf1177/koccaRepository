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

    String  v_selCategory = box.getString("p_selCategory");
    String  v_selState = box.getString("p_selState");
    String  v_searchtext    = box.getString("p_searchtext");
    
    String  v_process   = box.getString("p_process");
    String  v_year      = box.getString("p_year");
    String  v_hsubjnm   = box.getString("p_hsubjnm");


    //DEFINED in relation to select START
    String  ss_year         = box.getString("s_year");                      //연도
    String  ss_subj         = box.getString("s_subjcode");                  //과정
    String  ss_grseq        = box.getString("s_grseq");                     //차수
    String  ss_upperclass   = box.getStringDefault("s_upperclass", "ALL");  //과정대분류
    String  ss_middleclass  = box.getStringDefault("s_middleclass", "ALL"); //과정중분류
    String  ss_lowerclass   = box.getStringDefault("s_lowerclass", "ALL");  //과정소분류
    String  ss_action       = box.getString("s_action");
    String  s_subjsearchkey = box.getString("s_subjsearchkey");
    String  v_gyear         = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
    //DEFINED in relation to select END

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    
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
    
    String v_compnm       = "";
    String v_depart       = "";
    String v_type        = "";
    String v_employdate    = "";
    
    
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
            <td rowspan="2"><b>NO</b></td>
            <td rowspan="2">과정명</td>
            <td rowspan="2">차수</td>
            <td rowspan="2">성명</td>
            <td rowspan="2">학번</td>
            <td rowspan="2">학적</td>
            <td colspan="4">취업현황</td>
            <td rowspan="2">최종수정일</td>
          </tr>
          <tr>
            <td>날짜</td>
            <td>업체명</td>
            <td>부서명</td>
            <td>형태</td>
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
                v_year         = dbox.getString("d_year");
                
                 
               
                v_compnm        = dbox.getString("d_compnm");    
          		v_depart		= dbox.getString("d_depart"); 
          		v_type			= dbox.getString("d_type");  
          		v_employdate    = dbox.getString("d_employdate");  

                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
        %>
        <tr>
          <td><%= v_dispnum %></td>
          <td><%= v_subjnm %> </td>
          <td><%= v_subjseq.equals("") ? "" : Integer.parseInt(v_subjseq)+"기" %> </td>
          <td><%= v_name %> </td>
          <td><%= v_studentno %> </td>
          <td><%= v_statenm %> </td>
          
          <td><%= v_employdate %> </td>
          <td><%= v_compnm %> </td>
          <td><%= v_depart %> </td>
          <td><%= v_type %> </td>
          
          <td><%= FormatDate.getFormatDate(v_ldate, "yyyy-MM-dd") %> </td>
        </tr>
        <%        
            }
        } else {
        %>
        <tr><td colspan=11>등록된 과정이 없습니다.</td></tr>
        <% }  %>  
      </table>
 </body>
 </html>
