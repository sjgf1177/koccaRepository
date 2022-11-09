<%
	//**********************************************************
//  1. ��      ��: ���� ���(ȸ��)����
//  2. ���α׷���: za_UsersStatistics_E.jsp
//  3. ��      ��: ȸ�� ��� ��ȸ ����
//  4. ȯ      ��: JDK 1.7
//  5. ��      ��: 1.0
//  6. ��      ��: 2022.10.21
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
			<td style="text-align: center;" width="6%">ȸ������</td>
			<td style="text-align: center;" width="3%">����<br>����</td>
			<td style="text-align: center;" width="20%">�Ҽ�</td>
			<td style="text-align: center;" width="8%">���̵�</td>
			<td style="text-align: center;" width="5%">����</td>
			<td style="text-align: center;" width="4%">����</td>
			<td style="text-align: center;" width="7%">�������</td>
			<td style="text-align: center;" width="4%">����</td>
			<td style="text-align: center;" width="6%">������</td>
			<td style="text-align: center;" width="3%">����</td>
			<td style="text-align: center;" width="7%">������</td>
			<td style="text-align: center;" width="11%">�ֱ�������</td>
			<td style="text-align: center;" width="4%">��û<br>����(��)</td>
			<td style="text-align: center;" width="4%">�н�<br>����(��)</td>
			<td style="text-align: center;" width="4%">����<br>����(��)</td>
		</tr>
		<%
			DataBox dbox = null;

			if( list.size() != 0 ){		// �˻��� ������ �ִٸ�
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
			<td style="text-align: center;"><%= dbox.getString("d_age2") %>��</td>
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
