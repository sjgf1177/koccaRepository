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
</head>
<body>
		<table width=3000 border="0" cellspacing="1" cellpadding="0" class="TABLEB">
			<tr>
				<td colspan="32"></td>
			</tr>
			<tr height="25" class="LISTNEW">
				<td>
					성명
				</td>
				<td>
					메일
				</td>
				<td>
					회사
				</td>
				<td>
					부서
				</td>
				<td>
					직위
				</td>
				<td>
					핸드폰
				</td>
				<td>
					주소
				</td>
			</tr>
<%
	DBConnectionManager dbconnectionmanager = null;
        ListSet listset = null;
        String s = "";
        try
        {
            dbconnectionmanager = new DBConnectionManager();
            s = " select name, email, comptext, '.', '.', handphone, addr + ' ' + addr2 ";
            s = s + " from TZ_MEMBER  " ;
            s = s + " where state = 'Y'" ;
	    s = s + "    and registgubun  in('KOCCA')" ;
	    s = s + "    and ismailing = 'Y'";
	    s = s + "    and islettering = 'Y'";
	    s = s + " order by name";

			listset = dbconnectionmanager.executeQuery(s);
			if(listset != null){
					while(listset.next()) {
%>
						<tr>
							<td>
								<%=listset.getString(1)%>
							</td>
							<td>
								<%=listset.getString(2)%>
							</td>
							<td>
								<%=listset.getString(3)%>
							</td>
							<td>
								<%=listset.getString(4)%>
							</td>
							<td>
								<%=listset.getString(5)%>
							</td>
							<td>
								<%=listset.getString(6)%>
							</td>
							<td>
								<%=listset.getString(7)%>
							</td>
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

