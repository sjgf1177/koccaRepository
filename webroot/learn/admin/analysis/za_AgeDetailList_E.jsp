<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%
    response.setHeader("Content-Disposition", "attachment; filename=AgeDetailListEXE1.xls");   
    response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_subjseq     = "";
    String v_membergubun = "";
    String v_userid      = "";
    String v_name        = "";

    int i = 0;
    ArrayList list = (ArrayList)request.getAttribute("AgeDetailList");
    DataBox dbox = null;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<body>
    <table cellspacing="1" cellpadding="5" border="1">
      <tr>
        <td  width="5%">NO</td>
        <td  width="10%">회원구분</td>
        <td  width="5%">차수</td>
        <td  width="10%">ID</td>
        <td  width="10%">성명</td>
      </tr>

<%
        for (i=0; i<list.size(); i++ ) {
            dbox = (DataBox)list.get(i);
            v_subjseq     = dbox.getString("d_subjseq");
            v_membergubun = dbox.getString("d_membergubun");
            v_userid      = dbox.getString("d_userid");
            v_name        = dbox.getString("d_name");

            if(v_membergubun.equals("P")){
                v_membergubun = "개인";
            } else if(v_membergubun.equals("C")){
                v_membergubun = "기업";
            } else if(v_membergubun.equals("U")){
                v_membergubun = "대학";
            }
%>
          <tr >
            <td ><%=list.size()-i%></td>
            <td ><%= v_membergubun %></td>
            <td ><%= StringManager.cutZero(v_subjseq) %>차</td>
            <td ><%= v_userid %></td>
            <td ><%= v_name %></td>
          </tr>
<%
        }
%>
        </table>

</body>
</html>
