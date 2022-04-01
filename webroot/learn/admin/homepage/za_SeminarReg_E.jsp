<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGong_L.jsp
//  3. 개      요: 선택과정차수별 공지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>  
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	response.setHeader("Content-Disposition", "attachment; filename=za_SeminarReg_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    
    String  v_propstart = box.getString("p_propstart");
    String  v_propend   = box.getString("p_propend");
    
    int     v_totalrowcount =  0;

    int     v_seq            = 0;
    String  v_seminargubun   = "";
    String  v_seminargubunnm = "";
    String  v_seminarnm      = "";
    String  v_strdate        = "";
    String  v_enddate        = "";
    String  v_propstart1     = "";
    String  v_propend2       = "";
    String  v_answer         = "";
    String  v_userid         = "";
    String  v_name           = "";
    String  v_pass_yn        = "";
    String  v_indate         = "";
    
    String  v_selGubun       = box.getString("p_selGubun");
    int     v_selSeminar     = box.getInt("p_selSeminar");
    String  v_selSeminarNm   = box.getString("p_selSeminarNm");
    String  v_selPassYn      = box.getString("p_selPassYn");
    //String  v_propstart      = box.getString("p_sdate");
    //String  v_propend        = box.getString("p_edate");
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    
    ArrayList selectRegList    = (ArrayList)request.getAttribute("selectRegList");       // 워크샵 신청자 리스트
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
    <table border=1>
          <tr>
            <td><b>NO</b></td>
            <td><b>워크샵 분류</b></td>
            <td><b>워크샵명</b></td>
            <td><b>워크샵 신청기간</b></td>
            <td><b>워크샵 일시</b></td>
            <td><b>ID</b></td>
            <td><b>성명</b></td>
            <td><b>승인상태</b></td>
            <td><b>신청일시</b></td>
          </tr>

          <%
            for(int i = 0; i < selectRegList.size(); i++) {

                DataBox dbox = (DataBox)selectRegList.get(i);

                v_seq            = dbox.getInt("d_seq");
                v_seminargubun   = dbox.getString("d_seminargubun");
                v_seminargubunnm = dbox.getString("d_seminargubunnm");
                v_seminarnm      = dbox.getString("d_seminarnm");
                v_strdate        = dbox.getString("d_startdate");
                v_enddate        = dbox.getString("d_enddate");
                v_propstart1     = dbox.getString("d_propstart");
                v_propend2       = dbox.getString("d_propend");
                v_userid         = dbox.getString("d_userid");
                v_name           = dbox.getString("d_name");
                v_pass_yn        = dbox.getString("d_pass_yn");
                v_indate         = dbox.getString("d_indate");

                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");

%>

          <tr>
            <td><%=v_dispnum %></td>
            <td><%=v_seminargubunnm%></td>
            <td><%=v_seminarnm%></td>
            <td><%=FormatDate.getFormatDate(v_propstart1,"yyyy/MM/dd") + " ~ " + FormatDate.getFormatDate(v_propend2,"yyyy/MM/dd")%></td>
            <td><%=FormatDate.getFormatDate(v_strdate,"yyyy/MM/dd") + " ~ " + FormatDate.getFormatDate(v_enddate,"yyyy/MM/dd")%></td>
            <td><%=v_userid%></td>
            <td><%=v_name%></td>
            <td><%=v_pass_yn.equals("Y") ? "승인" : "미승인" %></td>
            <td><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd HH:mm ss")%></td>
          </tr>
<%
        }
%>
        </table>

</body>
</html>