<%@ page language="java" contentType="text/html; charset=euc-kr"    pageEncoding="euc-kr"%>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.text.DecimalFormat" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

	String url = "jdbc:microsoft:sqlserver://218.232.93.17:1433;DatabaseName=CONTENTS;CHARSET=euc-kr";
	String id = "sa";
	String passwd = "kgdi0303";
	
	StringBuffer queryBuf = new StringBuffer();
	String subj = "";
	String year = "";
	String subjseq = "";
	String userid = "";
	String name = "";
	String grcode = "N000001";
	String orgleccode = "";
	int score = 0;
	String isGraduated = "";
	String eduStart = "";
	String eduEnd = "";
	String serNo = "";
	String subjnm = "";
	


	queryBuf.append("	select a.usrcode , a.leccode, a.totscre, a.ckisgrad, b.lecsdat, b.lecedat,");
	queryBuf.append(" '20' + substring(b.leccode, (len(b.leccode)-3) ,2) year,");
	queryBuf.append(" '00' + right(b.leccode,2) subjseq,");
	queryBuf.append("	(select c.name from tz_member c where c.userid = a.usrcode) name ");
	queryBuf.append("	from LEC_REQ a,  LEC_SCL b ");
	queryBuf.append("	where a.reqstat = 'LP' and a.LECCODE LIKE 'PE%' ");
	queryBuf.append("	and a.LECCODE = substring(b.LECCODE,1,len(a.leccode)) ");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl00106%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00406%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma01006%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma01206%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00306%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00506%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma01106%'");
	queryBuf.append("	and a.LECCODE not LIKE '%coclacla03606%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00606%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl01406%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl02606%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocprcpr01306%'");
	queryBuf.append("	and a.LECCODE not LIKE '%coclacal01506%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma00906%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl00306%'");
	queryBuf.append("	and a.LECCODE not LIKE '%coculcul01006%'");
	out.println(queryBuf.toString());	
	out.println("<br><br>");	
	/*
	queryBuf.append("	select a.usrcode , a.leccode, a.totscre, a.ckisgrad, b.lecsdat, b.lecedat,");
	queryBuf.append("	(select c.name from tz_member c where c.userid = a.usrcode) name ");
	queryBuf.append("	from LEC_REQ a,  LEC_SCL b ");
	queryBuf.append("	where a.reqstat = 'LP' and a.LECCODE LIKE 'RE%' ");
	queryBuf.append("	and a.LECCODE = b.LECCODE");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl00106%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00406%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma01006%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma01206%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00306%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00506%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma01106%'");
	queryBuf.append("	and a.LECCODE not LIKE '%coclacla03606%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsplspl00606%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl01406%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl02606%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocprcpr01306%'");
	queryBuf.append("	and a.LECCODE not LIKE '%coclacal01506%'");
	queryBuf.append("	and a.LECCODE not LIKE '%spsmasma00906%'");
	queryBuf.append("	and a.LECCODE not LIKE '%cocplcpl00306%'");
	queryBuf.append("	and a.LECCODE not LIKE '%coculcul01006%'");
	*/
	Connection connection = DriverManager.getConnection(url,id,passwd);


	Statement stmt = connection.createStatement();
	Statement insertStmt = connection.createStatement();
	Statement rsStmt = connection.createStatement();
	
	ResultSet rs = stmt.executeQuery(queryBuf.toString());
	ResultSet tempRs = null;
	boolean isExist = false;

	int i = 0;
	out.println("<table border=1>");
	while(rs.next()){
		i++;
		//subj = rs.getString("leccode").substring(9,15);
		subj = rs.getString("leccode");
		year = rs.getString("year");
		subjseq = rs.getString("subjseq");

		//year = "20" + rs.getString("leccode").substring(13,15);
		//subjseq = "00" + rs.getString("leccode").substring(15,17);
		userid = rs.getString("usrcode");
		name = rs.getString("name");
		orgleccode = rs.getString("leccode");
		score = rs.getInt("totscre");
		isGraduated = rs.getString("ckisgrad");
		eduStart = rs.getString("lecsdat");
		eduEnd = rs.getString("lecedat");

		serNo = "";
	
		if(eduStart.length() == 8) eduStart += "0000";
		if(eduEnd.length() == 8) eduEnd += "0000";		
		
		/*
		out.println("<tr>");		
		out.println("<td>");		out.println(subj);		out.println("</td>");
		out.println("<td>");		out.println(year);		out.println("</td>");
		out.println("<td>");		out.println(subjseq);	out.println("</td>");		
		out.println("<td>");		out.println(userid);	out.println("</td>");		
		out.println("<td>");		out.println(name);		out.println("</td>");		
		out.println("<td>");		out.println(orgleccode);out.println("</td>");		
		out.println("<td>");		out.println(score);		out.println("</td>");		
		out.println("<td>");		out.println(isGraduated);out.println("</td>");		
		out.println("<td>");		out.println(eduStart);	out.println("</td>");		
		out.println("<td>");		out.println(eduEnd);	out.println("</td>");		
		out.println("<td>");		out.println(serNo);		out.println("</td>");		
		out.println("</tr>");		
		*/
//		out.println("no -> "+i);
		queryBuf.setLength(0);
		queryBuf.append(" select count(*) from TZ_STOLDHST ");
		queryBuf.append(" where SUBJ = '").append(subj).append("'");
		queryBuf.append(" and YEAR = '").append(year).append("'");
		queryBuf.append(" and SUBJSEQ = '").append(subjseq).append("'");
		queryBuf.append(" and USERID = '").append(userid).append("'");

		tempRs = rsStmt.executeQuery(queryBuf.toString());
		if(tempRs.next()){
			if(tempRs.getInt(1) > 0){
				isExist = true;
			}else{
				isExist = false;
			}
		}

		if(tempRs != null){		tempRs.close();	}

		if(isExist == false){
			queryBuf.setLength(0);
			queryBuf.append("insert into TZ_STOLDHST ( SUBJ,YEAR,SUBJSEQ,USERID,NAME,");
			queryBuf.append("GRCODE,ORGLECCODE,SCORE,ISGRADUATED,EDUSTART,EDUEND ) ");
			queryBuf.append("values('").append(subj).append("',");
			queryBuf.append("'").append(year).append("',");		
			queryBuf.append("'").append(subjseq).append("',");		
			queryBuf.append("'").append(userid).append("',");		
			queryBuf.append("'").append(name).append("',");				
			queryBuf.append("'").append(grcode).append("',");		
			queryBuf.append("'").append(orgleccode).append("',");		
			queryBuf.append(score).append(",");		
			queryBuf.append("'").append(isGraduated).append("',");				
			queryBuf.append("'").append(eduStart).append("',");				
			queryBuf.append("'").append(eduEnd).append("')");				


			out.println(queryBuf.toString());
			out.println("<br><br>");
			insertStmt.execute(queryBuf.toString());
		}
		
		
		
	}
	
	out.println("</table>");	
	if(stmt != null){		stmt.close();	}
	if(insertStmt != null){		insertStmt.close();	}	
	if(rsStmt != null){		rsStmt.close();	}	
	
	if(rs != null){		rs.close();	}
	if(connection != null){		connection.close();	}


	
	/*
	
	과정코드 [SUBJ] [varchar] (10) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	년도 [YEAR] [char] (4) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	차수 [SUBJSEQ] [varchar] (4) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	사용자ID [USERID] [varchar] (20) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	이름 [NAME] [varchar] (20) COLLATE Korean_Wansung_CI_AS NULL ,
	교육주관코드(N000001)로 입력 [GRCODE] [varchar] (7) COLLATE Korean_Wansung_CI_AS NULL ,
	기존 과정코드 [ORGLECCODE] [varchar] (17) COLLATE Korean_Wansung_CI_AS NULL ,
	점수 -> 어느테이블에 있는지 확인 필요 [SCORE] [decimal](5, 2) NULL ,
	이수여부 -> 어느테이블에 있는지 확인 필요 [ISGRADUATED] [char] (1) COLLATE Korean_Wansung_CI_AS NULL ,
	교육시작일 [EDUSTART] [varchar] (10) COLLATE Korean_Wansung_CI_AS NULL ,
	교육종료일 [EDUEND] [varchar] (10) COLLATE Korean_Wansung_CI_AS NULL ,
	수증증번호 -> 어느테이블에 있는지 확인 필요(찾아보구 없으면 제외) [SERNO] [varchar] (30) COLLATE Korean_Wansung_CI_AS NULL ,

 	*/
 	
 	
	
	
 	
 	
 	
%>
 	
 	
 	
 	
 	
 	
 	
 	
%>