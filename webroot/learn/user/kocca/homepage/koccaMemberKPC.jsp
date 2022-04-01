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
					ID
				</td>
				<td>
					성명
				</td>
				<td>
					주민등록번호
				</td>
				<td>
					비밀번호
				</td>
				<td>
					메일주소
				</td>
				<td>
					우편번호1
				</td>
				<td>
					우편번호2
				</td>
				<td>
					주소1
				</td>
				<td>
					주소2
				</td>
				<td>
					집전화
				</td>
				<td>
					핸드폰
				</td>
				<td>
					회사전화
				</td>
				<td>
					회사명
				</td>
				<td>
					메일수신여부
				</td>
				<td>
					마지막수정일
				</td>
			</tr>
<%
		DBConnectionManager dbconnectionmanager = null;
        ListSet listset = null;
        StringBuffer sb = null;
        
        try
        {
            dbconnectionmanager = new DBConnectionManager();

						sb.append(" select userid, name, resno, pwd, email, post1, post2, addr, addr2, hometel, handphone, comptel, comptext, ismailing, TO_CHAR(ldate, 'YYYYMMDD') AS ldate ");
						sb.append(" from TZ_MEMBER  ");
						sb.append(" where state = 'Y'");
						sb.append("   and registgubun  in('KOCCA')");

						listset = dbconnectionmanager.executeQuery(sb.toString());
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
							<td>
								<%=listset.getString(8)%>
							</td>
							<td>
								<%=listset.getString(9)%>
							</td>
							<td>
								<%=listset.getString(10)%>
							</td>
							<td>
								<%=listset.getString(11)%>
							</td>
							<td>
								<%=listset.getString(12)%>
							</td>
							<td>
								<%=listset.getString(13)%>
							</td>
							<td>
								<%=listset.getString(14)%>
							</td>
							<td>
								<%=listset.getString(15)%>
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

