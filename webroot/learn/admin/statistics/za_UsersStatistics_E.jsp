<%
	//**********************************************************
//  1. 제      목: 종합 통계(회원)엑셀
//  2. 프로그램명: za_UsersStatistics_E.jsp
//  3. 개      요: 회원 통계 조회 엑셀
//  4. 환      경: JDK 1.7
//  5. 버      젼: 1.0
//  6. 작      성: 2022.10.21
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_UsersStatistics_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

	int v_total	= 0;
	int i = 0;

	ArrayList list = null;
	list = (ArrayList)request.getAttribute("selectUsersStatisticListExcel");
%>
<html>
<head>
	<title></title>
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="aaa();">
	<table cellspacing="1" cellpadding="5" class="table_out" border="1">
		<tr>
			<td colspan="16" class="table_top_line"></td>
		</tr>
		<tr style="text-align: center;">
			<td style="text-align: center;" width="4%">No</td>
			<td style="text-align: center;" width="6%">회원구분</td>
			<td style="text-align: center;" width="3%">교육<br>구분</td>
			<td style="text-align: center;" width="20%">소속</td>
			<td style="text-align: center;" width="8%">아이디</td>
			<td style="text-align: center;" width="5%">성명</td>
			<td style="text-align: center;" width="4%">성별</td>
			<td style="text-align: center;" width="7%">생년월일</td>
			<td style="text-align: center;" width="4%">지역</td>
			<td style="text-align: center;" width="6%">직업군</td>
			<td style="text-align: center;" width="3%">연령</td>
			<td style="text-align: center;" width="7%">가입일</td>
			<td style="text-align: center;" width="11%">최근접속일</td>
			<td style="text-align: center;" width="4%">신청<br>과정(수)</td>
			<td style="text-align: center;" width="4%">학습<br>과정(수)</td>
			<td style="text-align: center;" width="4%">수료<br>과정(수)</td>
		</tr>
		<%
			DataBox dbox = null;

			if( list.size() != 0 ){		// 검색된 내용이 있다면
				v_total = list.size();

				for(i = 0; i < v_total; i++) {
					dbox = (DataBox)list.get(i);
		%>
		<tr>
			<td style="text-align: center;"><%= list.size() - i %></td>
			<td style="text-align: center;"><%= dbox.getString("d_mber_nm") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_edu_type") %></td>
			<td style="text-align: left;"><%= dbox.getString("d_grcodenm") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_user_id") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_user_nm") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_sex_nm") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_brthdy") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_region") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_job_nm") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_age2") %>대</td>
			<td style="text-align: center;"><%= dbox.getString("d_indt") %></td>
			<td style="text-align: center;"><%= dbox.getString("d_lslgdt") %></td>
			<td style="text-align: center;"><%= dbox.getInt("d_pro_cnt") %></td>
			<td style="text-align: center;"><%= dbox.getInt("d_edu_cnt") %></td>
			<td style="text-align: center;"><%= dbox.getInt("d_gra_cnt") %></td>
		</tr>
		<%
			}
		}
		%>
	</table>
</body>
</html>
