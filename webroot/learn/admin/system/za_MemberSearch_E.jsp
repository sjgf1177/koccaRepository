<%
//**********************************************************
//  1. 제      목: 과정 조회화면
//  2. 프로그램명: za_Subject_L.jsp
//  3. 개      요: 과정 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 12
//  7. 수      정: 
//**********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<%response.setHeader("Content-Disposition", "inline; filename=MemberInfo.xls");
  response.setHeader("Content-Description", "JSP Generated Data");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<title>회원DB검색</title>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <table border="1" cellspacing="0" cellpadding="0">
        <tr> 
          <td class="table_title"><b>회원구분</b></td>
          <td class="table_title"><b>ID</b></td>
          <td class="table_title"><b>성명(한글)</b></td>
          <td class="table_title"><b>성명(영문)</b></td>
          <td class="table_title"><b>E-mail</b></td>
          <td class="table_title"><b>직위</b></td>
          <td class="table_title"><b>상태</b></td>
          <td class="table_title"><b>우편번호(자택)</b></td>
          <td class="table_title"><b>주소(자택)</b></td>
          <td class="table_title"><b>전화번호(자택)</b></td>
          <td class="table_title"><b>전화번호(회사)</b></td>
          <td class="table_title"><b>전화번호(핸드폰)</b></td>
          <td class="table_title"><b>회사명</b></td>
          <td class="table_title"><b>우편번호(회사)</b></td>
          <td class="table_title"><b>주소(회사)</b></td>
          <td class="table_title"><b>직업</b></td>
          <td class="table_title"><b>최종학력</b></td>
          <td class="table_title"><b>메일수신여부</b></td>
          <td class="table_title"><b>연락처공개여부</b></td>
          <td class="table_title"><b>뉴스레터수신여부</b></td>
          <td class="table_title"><b>가입경로</b></td>
          <td class="table_title"><b>최초가입일</b></td>
          <td class="table_title"><b>최종수정일</b></td>
        </tr>

        <%  ArrayList  list = (ArrayList)request.getAttribute("memberList");

            for (int i=0; i<list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i); 
                String d_ldate = dbox.getString("d_ldate");
                if ( d_ldate.length() <= 8  ) d_ldate = "20"+d_ldate.replaceAll("/","");
        %>
        <tr> 
          <td class="table_02_1"><%=dbox.getString("d_membergubunnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_userid")%></td>
          <td class="table_02_1"><%=dbox.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox.getString("d_eng_name")%></td>
          <td class="table_02_1"><%=dbox.getString("d_email")%></td>    
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_jikwi")%> </td>
          <td class="table_02_1"><%=dbox.getString("d_state")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_post1")%>-<%=dbox.getString("d_post2")%></td>
          <td class="table_02_1"><%=dbox.getString("d_addr")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_hometel")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_comptel")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_handphone")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_comptext")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_comp_post1")%>-<%=dbox.getString("d_comp_post2")%></td>
          <td class="table_02_1"><%=dbox.getString("d_comp_addr1")%> <%=dbox.getString("d_comp_addr2")%></td>
          <td class="table_02_1"><%=dbox.getString("d_jikupnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_degreenm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_ismailing")%></td>
          <td class="table_02_1"><%=dbox.getString("d_isopening")%></td>
          <td class="table_02_1"><%=dbox.getString("d_islettering")%></td>
          <td class="table_02_1"><%=dbox.getString("d_registgubun")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(d_ldate,"yyyy/MM/dd")%></td>
        </tr>
        <%  } %>
      </table>
      <!----------------- 회원DB검색 끝 ----------------->
</body>
</html>
