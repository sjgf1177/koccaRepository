<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<%@ page import com.credu.homepage.* %>
<%@ page import com.credu.common.* %>
<%@ page import com.credu.system.* %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = RequestManager.getBox(request);
	
	String strColumnCount = box.getString("pColumnCount");
	String strDataQuery = box.getString("pDataQuery");

	DBConnectionManager dbconnectionmanager = null;
	ListSet listset = null;
    String s = "";

%>

<html>
<head>
<title>통계결과보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<script language="JavaScript">
<!--

-->
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="30" topmargin="30" rightmargin="0" marginwidth="11" marginheight="30">

<table border="0" cellspacing="0" cellpadding="0">		
	<tr>
		<td>	
			<!-----타이틀----->
   	        <table border=0 cellspacing=0 cellpadding=0 class="TABLE70B">
   	        	<tr height=2>
   	            	<td colspan=7 class="TLINE"></td>
   	            </tr>
   	            <tr height=23>
   	            	<td width=1 class="TLINE"></td>
   	                <td width=100 class="TITNEW"></td>
   	                <td width=5 bgcolor=#FFFFFF></td>
   	                <td align=center bgcolor=#FFFFFF>
						<font color="#003366"><b>결과보기></b></font></td>
   	                <td width=5 bgcolor=#FFFFFF></td>
   	                <td width=100 class="TITNEW"></td>
   	                <td width=1 class="TLINE"></td>
   	            </tr>
   	            <tr height=4>
   	                <td colspan=7 class="TLINE"></td>
   	            </tr>
   	        </table><br><br>
			<!-----타이틀 끝----->
           	
<form name=frmList method=post action="staDataprintSave.jsp">
<%
	try
        {
            dbconnectionmanager = new DBConnectionManager();
            s = strDataQuery;

			listset = dbconnectionmanager.executeQuery(s);

%>	
			<table border="0" cellspacing="1" cellpadding="0" class="TABLE70B">
				<tr>
					<td colspan="<%= strColumnCount %>"></td>
				</tr>
				<tr height="25" class="LISTNEW">
					<td width="30">번호</td>
				<% for(int i = 0; i < Integer.parseInt(strColumnCount); i++) { %>
					<td><%= i+1 %></td>
				<% } %>
				</tr>

				<% 
					if(listset != null ) { 
						int j = 0;

						while(listset.next()) {
				%>
				<tr height="25" class="LIST1"> 
						<td><%= j+1 %></td>
				<%
							for(int i = 0; i < Integer.parseInt(strColumnCount); i++) { 
				%>
						<td><%= listset.getString(i+1) %></td>
				<% 
							}

							j++;
				%>
				</tr> 
				<%
						}
					} 
				%>

				<tr>
					<td colspan="<%= strColumnCount %>"></td>
				</tr>				
			</table>
           	<!--------본문끝-------->
            <br><br>
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
			

            <!----- 버튼 ----->
			<table border="0" cellspacing="0" cellpadding="0" class="TABLE70F">
				<tr height=25>
					<td align="right">
						<!--<a href ="javascrip:frmList.submit();">목록</a>
			            <a href ="javascrip:frmExcel.submit();">엑셀</a>-->
			    	</td>
				</tr>
			</table>    	
          	<!-----버튼 끝 ----->
            
		</td>
	</tr>
</table>

</form>

</body>
</html>

<form name="frmExcel" method="post" action="staDataprintListExcel.jsp">
	<input type="hidden" name="pColumnCount" value="<%=strColumnCount%>">
	<input type="hidden" name="pDataQuery" value="<%=strDataQuery%>">
</form>
