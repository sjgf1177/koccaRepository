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
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>
  
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    response.setHeader("Content-Disposition", "attachment; filename=za_regEvent_E.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;

    int     v_seq       = 0;
    String  v_title     = "";
    String  v_strdate   = "";
    String  v_enddate   = "";
    String  v_answer    = "";
    String  v_userid    = "";
    String  v_name      = "";
    String  v_handphone = "";
    String  v_email     = "";
    String  v_addr      = "";
    String  v_addr2     = "";
    String  v_win_yn    = "";
    String  v_indate    = "";
    String	v_job		= "";
    String 	v_ldate		= "";
    String 	v_jobdate	= "";
        
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    
    ArrayList selectRegList    = (ArrayList)request.getAttribute("selectList");       // 이벤트 신청자 리스트

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
    <table border=1>
          <tr>            
          	<td><b>NO</b></td>
          	<td><b>성명</b></td>
          	<td><b>아이디</b></td>
            <td><b>회사</b></td>
            <td><b>등록일</b></td>
            <td><b>취업일</b></td>
            <td><b>핸드폰</b></td>
            <td><b>E-Mail</b></td>
            <td><b>주소</b></td>
          </tr>

          <%
            for(int i = 0; i < selectRegList.size(); i++) {

                DataBox dbox = (DataBox)selectRegList.get(i);
                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_userid    = dbox.getString("d_userid");
                v_name      = dbox.getString("d_name");
                v_handphone = dbox.getString("d_handphone");
                v_email     = dbox.getString("d_email");
                v_addr      = dbox.getString("d_addr");
                v_addr2     = dbox.getString("d_addr2");
                v_job		= dbox.getString("d_job");
                v_ldate		= dbox.getString("d_ldate");
                v_jobdate	= dbox.getString("d_jobdate");
                
%>
          <tr>                    
          	<td><%=v_dispnum%></td>          	
            <td><%=v_name%></td>  
            <td><%=v_userid%></td>
            <td><%=v_job%></td>
            <td><%=FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd")%></td>
            <td><%=FormatDate.getFormatDate(v_jobdate,"yyyy/MM/dd")%></td>
            <td><%=v_handphone%></td>
            <td><%=v_email%></td>
            <td><%=v_addr + v_addr2%></td>
          </tr>
<%
        }
%>
        </table>
      
</body>
</html>

