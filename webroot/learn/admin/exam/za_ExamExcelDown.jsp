<%@ page language="java" contentType="text/html;" pageEncoding="euc-kr"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="java.util.Date" %>
<%
 ArrayList list1 = (ArrayList)request.getAttribute("ExamExcelDown");
 DataBox dbox1 = (DataBox)list1.get(0);

 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HHmmss");
 String regdateStr = sdf.format(new Date());
 
 String subjnm=dbox1.getString("d_subjnm");
 dbox1=null;
 list1=null;

 response.setContentType("application/vnd.ms-excel; charset=euc-kr");
 response.setHeader("Content-Disposition", "attachment;filename="+regdateStr+"..xls");
 response.setHeader("Content-Description", "JSP Generated Data");
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
</HEAD>
<BODY>
    <%=subjnm%>
    <table cellspacing="0" cellpadding="0" width="100%" border="1px solid #cdcdcd">
        <tr>
            <td width="60px" style="background-color:#E7F3FE" align="center">문제번호</td>
            <td width="60px" style="background-color:#E7F3FE" align="center">문제형식</td>
            <td width="50px" style="background-color:#E7F3FE" align="center">난이도</td>
            <td style="background-color:#E7F3FE" align="center">문제</td>
            <td width="30px" style="background-color:#E7F3FE" align="center">설명</td>
            <td width="60px" style="background-color:#E7F3FE" align="center">보기번호</td>
            <td width="60px" style="background-color:#E7F3FE" align="center">보기내용</td>
            <td width="30px" style="background-color:#E7F3FE" align="center">정답</td>
        </tr>
        <%
        ArrayList list = (ArrayList)request.getAttribute("ExamExcelDown");
        DataBox dbox = null;
        String tmp1="";
        int count=1,basic=1;

        for (int i=0; i<list.size(); i++) {
            dbox  = (DataBox)list.get(i);
            out.println("<tr>");
                if(!tmp1.equals(dbox.getString("d_examnum")))
                {
                    out.println("<td align='center' rowspan='1' name='tb_merger"+basic+"' >");
                    out.println(dbox.getString("d_examnum"));
                    out.println("</td>");
                    out.println("<td align='center' rowspan='1' name='tb_merger"+basic+"' >");
                    out.println(dbox.getString("d_examtypenm"));
                    out.println("</td>");
                    out.println("<td align='center' rowspan='1' name='tb_merger"+basic+"' >");
                    out.println(dbox.getString("d_levelsnm"));
                    out.println("</td>");
                    out.println("<td  rowspan='1' name='tb_merger"+basic+"' >");
                    out.println(dbox.getString("d_examtext"));
                    out.println("</td>");
                    out.println("<td  rowspan='1' name='tb_merger"+basic+"' >");
                    out.println(dbox.getString("d_exptext"));
                    out.println("</td>");

                    if(i!=0)
                    {
                    %>
                    <script>
                        $("[name='tb_merger<%=basic-1%>']").attr("rowspan",<%=count%>);
                        <%--$("[name='tb_merger<%=i%>']").echo(function(){--%>
                            <%--$(this).attr("rowspan",<%=count%>);--%>
                        <%--});--%>
                    </script>
            <%      }
                    count=1;
                    basic++;
                }
                else
            {      //화면에 뿌릴려면 아래 td 5개를 주석 처리 해야 됨
                    out.println("<td></td>");
                    out.println("<td></td>");
                    out.println("<td></td>");
                    out.println("<td></td>");
                    out.println("<td></td>");
                    count++;
            }

                out.println("<td align='center'>");
                out.println(dbox.getString("d_selnum"));
                out.println("</td>");
                out.println("<td >");
                out.println(dbox.getString("d_seltext"));
                out.println("</td>");
                out.println("<td >");
                out.println(dbox.getString("d_isanswer"));
                out.println("</td>");
            out.println("</tr>");
            tmp1=dbox.getString("d_examnum");
        }
        %>
    </table>
<script>
    $("[name='tb_merger<%=basic-1%>']").attr("rowspan",<%=count%>);
</script>
</body>
</html>
