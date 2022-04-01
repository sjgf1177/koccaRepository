<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<%@ page import com.credu.homepage.* %>
<%@ page import com.credu.common.* %>
<%@ page import com.credu.system.* %>

<% 
	//response.setHeader("Content-Disposition", "attachment; filename=memberExcel_01.xls"); 
	//response.setHeader("Content-Description", "JSP Generated Data"); 

	RequestBox box = RequestManager.getBox(request);

    String pUserID = box.getString("id");

	if((pUserID==null) || pUserID.equals("")) {
		out.println("<script language='javascript'>location.href='http://contents.connect.or.kr';</script>");
		return;
	}
%> 

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<html>
<head>
</head>
<body>
		<table width=1000 border="1" cellspacing="1" cellpadding="0" >
			<tr height="25" >
				<td>
					교육그룹
				</td>
				<td>
					년도
				</td>
				<td>
					차수
				</td>
				<td>
					과정
				</td>
				<td>
					교육기간
				</td>
				<td>
					진도율(점수)
				</td>
				<td>
					총점
				</td>
				<td>
					수료가능
				</td>
			</tr>
<%
		DBConnectionManager dbconnectionmanager = null;
        ListSet listset = null;
        String s = "";
        try
        {
            dbconnectionmanager = new DBConnectionManager();
            s = "select A.gyear,A.grseq, ";
            s+= "A.subjnm,A.subjseq,A.isonoff,A.edustart,A.eduend,B.tstep,B.avtstep,B.score,A.point,";
            s+= "(select grcodenm from TZ_GRCODE where grcode=A.grcode) as grcodenm,A.isonoff,  ";
            s+= "(select grseqnm from TZ_GRSEQ where grcode = A.grcode and gyear=A.gyear and grseq=A.grseq) as grseqnm, ";
            s+= " B.isgraduated                                                                                         ";
            s+= "from VZ_SCSUBJSEQ A,TZ_STUDENT B ";
            s+= "where B.userid = "+SQLString.Format(pUserID)+" and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
//            s+= "and B.isgraduated='N' ";
            s+= "order by A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq ";

			listset = dbconnectionmanager.executeQuery(s);
			if(listset != null){
					while(listset.next()) {
%>
						<tr>
							<td>
								<%=listset.getString(12)%>
							</td>
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
								<%=listset.getString(6)%>~<%=listset.getString(7)%>
							</td>
							<td>
								<%=listset.getString(8)%>(<%=listset.getString(9)%>)
							</td>
							<td>
								<%=listset.getString(10)%>
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

