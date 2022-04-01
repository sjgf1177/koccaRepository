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
    response.setHeader("Content-Disposition", "attachment; filename=za_EventReg_E.xls");
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
    
    int     v_selEvent     = box.getInt("p_selEvent");
    String  v_selWinYn     = box.getString("p_selWinYn");
    String  v_selStrDate   = box.getString("p_startdate");
    String  v_selEndDate   = box.getString("p_enddate");
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    
    ArrayList selectRegList    = (ArrayList)request.getAttribute("selectRegList");       // 이벤트 신청자 리스트

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>
    <table border=1>
          <tr>
            <td><b>NO</b></td>
            <td><b>이벤트명</b></td>
            <td><b>이벤트 기간</b></td>
            <td><b>입력내용</b></td>
            <td><b>ID</b></td>
            <td><b>성명</b></td>
            <td><b>핸드폰</b></td>
            <td><b>E-Mail</b></td>
            <td><b>주소</b></td>
            <td><b>당첨여부</b></td>
            <td><b>참여일시</b></td>
          </tr>

          <%
            for(int i = 0; i < selectRegList.size(); i++) {

                DataBox dbox = (DataBox)selectRegList.get(i);

                v_seq       = dbox.getInt("d_seq");
                v_title     = dbox.getString("d_title");
                v_strdate   = dbox.getString("d_strdate");
                v_enddate   = dbox.getString("d_enddate");
                v_answer    = dbox.getString("d_answer");
                v_indate    = dbox.getString("d_ldate");
                v_userid    = dbox.getString("d_userid");
                v_name      = dbox.getString("d_name");
                v_handphone = dbox.getString("d_handphone");
                v_email     = dbox.getString("d_email");
                v_addr      = dbox.getString("d_addr");
                v_addr2     = dbox.getString("d_addr2");
                v_win_yn    = dbox.getString("d_win_yn");

                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
                
                if(v_win_yn.equals("Y")){
                	v_win_yn = "당첨";
                } else if(v_win_yn.equals("N")){
                    v_win_yn = "탈락";
                }

%>
          <tr>
            <td><%=v_dispnum %></td>
            <td><%=v_title%></td>
            <td><%=FormatDate.getFormatDate(v_strdate,"yyyy/MM/dd") + " ~ " + FormatDate.getFormatDate(v_enddate,"yyyy/MM/dd")%></td>
            <!-- td ><//%=StringUtil.getByteString(v_answer, 20)%></td -->
            <td ><%=v_answer%></td>
            <td><%=v_userid%></td>
            <td><%=v_name%></td>
            <td><%=v_handphone%></td>
            <td><%=v_email%></td>
            <td><%=v_addr + v_addr2%></td>
            <td><%=v_win_yn%></td>
            <td><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd HH:mm ss")%></td>
          </tr>
<%
        }
%>
        </table>
      
</body>
</html>

