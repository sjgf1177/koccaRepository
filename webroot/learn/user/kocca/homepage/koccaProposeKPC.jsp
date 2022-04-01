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
				<td>과정코드</td>
				<td>과정명</td>
				<td>차수</td>
				<td>회원구분</td>
				<td>ID</td>
				<td>성명</td>
				<td>교육시작일</td>
				<td>교육종료일</td>
				<td>최종승인여부</td>
				<td>이메일</td>
				<td>소속</td>
				<td>마지막수정일</td>
				<td>과정</td>
				<td>직군코드</td>
				<td>직군명</td>


			</tr>
<%
		DBConnectionManager dbconnectionmanager = null;
        ListSet listset = null;
        String s = "";
        try
        {
            dbconnectionmanager = new DBConnectionManager();
            s = " Select C.subj,C.subjnm,C.subjseq,";
						s += " B.membergubun, B.userid,B.name,C.edustart,C.eduend,A.chkfinal,";
						s += " B.email, b.comptext, TO_CHAR(b.ldate, 'YYYYMMDD' ) AS ldate,";
						s += " (select grseqnm from tz_grseq where grcode=c.grcode and gyear=c.gyear and grseq = c.grseq) grseqnm, ";
						s += " b.jikup, ( select codenm from ( select rownum rnum,  codenm from tz_code d where d.gubun = '0070' and d.levels=1 and d.code = b.jikup ) where rnum < 2) ";
						s += " from TZ_PROPOSE A,TZ_MEMBER B,VZ_SCSUBJSEQ C where 1 = 1  ";
            s += " and C.grcode = "+SQLString.Format("N000001");
            s += " and C.scupperclass = "+SQLString.Format("K01");

						//교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
            s += " and C.gyear >= "+SQLString.Format("2007");
						//s += " and C.grseq = "+SQLString.Format("0006");
						s += " and isnull(A.cancelkind,' ') not in ('P','F') and A.appdate is not null ";
						//s += " and A.appdate >= '20070101' ";
						s += " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";

						listset = dbconnectionmanager.executeQuery(s);
						if(listset != null){
								while(listset.next()) {
%>
						<tr>
							<td><%=listset.getString(1)%></td>
							<td><%=listset.getString(2)%></td>
							<td><%=listset.getString(3)%></td>
							<td><%=listset.getString(4)%></td>
							<td><%=listset.getString(5)%></td>
							<td><%=listset.getString(6)%></td>
							<td><%=listset.getString(7)%></td>
							<td><%=listset.getString(8)%></td>
							<td><%=listset.getString(9)%></td>
							<td><%=listset.getString(10)%></td>
							<td><%=listset.getString(11)%></td>
							<td><%=listset.getString(12)%></td>
							<td><%=listset.getString(13)%></td>
							<td><%=listset.getString(14)%></td>
							<td><%=listset.getString(15)%></td>

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

