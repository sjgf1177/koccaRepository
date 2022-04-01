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
					userid
				</td>
				<td>
					resno
				</td>
				<td>
					name
				</td>
				<td>
					pwd
				</td>
				<td>
					email
				</td>
				<td>
					post1
				</td>
				<td>
					post2
				</td>
				<td>
					addr
				</td>
				<td>
					addr2
				</td>
				<td>
					hometel
				</td>
				<td>
					handphone
				</td>
				<td>
					comtel
				</td>
				<td>
					comtext
				</td>
			</tr>
<%
		DBConnectionManager dbconnectionmanager = null;
        ListSet listset = null;
        String s = "";
        try
        {
            dbconnectionmanager = new DBConnectionManager();
            s = " select * from ( select rownum rnum,  userid, resno, name, pwd, email, post1, post2, addr, addr2, hometel, handphone, comptel, comptext";
            s = s + " from TZ_MEMBER  " ;
            s = s + " where state = 'Y'" ;
			s = s + "    and registgubun  in('KOCCA')" ;
			s = s + "    and name in ( '장성일', '김기현', '정경호', '박주선', '이정아', '임수현', '뱍대훈', '강성호', '노희송' )) where rnum < 101" ;
//			s = s + "    and ismailing = 'Y'" ;

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

