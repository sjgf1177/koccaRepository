<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<%@ page import com.credu.homepage.* %>
<%@ page import com.credu.common.* %>
<%@ page import com.credu.system.* %>

<%
	response.setHeader("Content-Disposition", "attachment; filename=memberExcel_01.xls");
	response.setHeader("Content-Description", "JSP Generated Data");
%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
</head>
<body>
		<table width=3000 border="0" cellspacing="1" cellpadding="0" class="TABLEB">
			<tr>
				<td colspan="32"></td>
			</tr>
			<tr height="25" class="LISTNEW">
				<td>code</td>
				<td>codenm</td>
				<td>gubun</td>
				<td>levels</td>

			</tr>
<%
		DBConnectionManager dbconnectionmanager = null;
        ListSet listset = null;
        String s = "";
        try
        {
            dbconnectionmanager = new DBConnectionManager();
            s = " Select code, codenm, gubun, levels ";
	    s += " from tz_code ";
                s += " where gubun = "+SQLString.Format("0070");
                s += " and levels = "+SQLString.Format("1");

			listset = dbconnectionmanager.executeQuery(s);
			if(listset != null){
					while(listset.next()) {
%>
						<tr>
							<td><%=listset.getString(1)%></td>
							<td><%=listset.getString(2)%></td>
							<td><%=listset.getString(3)%></td>
							<td><%=listset.getString(4)%></td>

						</tr>
			<%
						}	// while END
					}	// if END
			%>
			</table>
<%
        }
        catch(Exception exception1)
        {
            //ErrorManager.getErrorStackTrace(exception1, requestbox, s);
            throw new Exception("sql = " + s + "\r\n" + exception1.getMessage());
        }
        finally
        {
            if(listset != null)
                try
                {
                    listset.close();
                }
                catch(Exception _ex) { }
            if(dbconnectionmanager != null)
                try
                {
                    dbconnectionmanager.freeConnection();
                }
                catch(Exception _ex) { }
        }

%>
</body>
</html>

