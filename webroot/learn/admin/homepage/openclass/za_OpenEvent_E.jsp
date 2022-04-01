<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>
  
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    response.setHeader("Content-Disposition", "attachment; filename=event.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList<DataBox> list = (ArrayList)request.getAttribute("list");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body>

			<!----------------- List 시작 ----------------->
			<table class="table_out" border="1">
                <tr>
                    <td colspan="9" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" width="3%"><b>No</b></td>
                    <td class="table_title" width="5%"><b>아이디</b></td>
                    <td class="table_title" width="5%"><b>이름</b></td>
                    <td class="table_title" width="5%"><b>이메일</b></td>
                    <td class="table_title" width="0%"><b>전화번호</b></td>
                    <td class="table_title" width="5%"><b>강좌코드</b></td>
                    <td class="table_title"><b>강좌명</b></td>
                    <td class="table_title" width="8%"><b>학습일시</b></td>
                    <td class="table_title" width="5%"><b>학습횟수</b></td>
                </tr>
                <%if(list != null && list.size() > 0){%>
                	<%
                		int v_row = 1;
                		int v_row_cnt = 1;
                		String v_userid = "";
                		String v_name 	= "";
                		String v_email	= "";
                		String v_handphone = "";
                		String v_seq	= "";
                		String v_userid_tmp = "";
                		String v_lecnm	= "";
                		for(DataBox dbox : list){
                			v_row_cnt = dbox.getInt("d_row_cnt");
                			v_userid	= dbox.getString("d_userid");
                			v_name		= dbox.getString("d_name");
                			v_email		= dbox.getString("d_email");
                			v_handphone	= dbox.getString("d_handphone");
                			v_seq		= dbox.getString("d_seq");
                			v_lecnm 	= dbox.getString("d_lecnm");
                	%>
               				<tr>
               					<%if(!v_userid_tmp.equals(v_userid)){%>
               					<%v_userid_tmp = v_userid;%>
               					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_row++%></td>
               					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_userid%></td>
               					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_name%></td>
               					<td class="table_02_2" rowspan="<%=v_row_cnt%>"><%=v_email%></td>
               					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_handphone%></td>
               					<%}%>
               					<td class="table_02_1"><%=v_seq%></td>
               					<td class="table_02_2"><%=v_lecnm%></td>
               					<td class="table_02_1"><%=dbox.getString("d_study_date")%></td>
               					<td class="table_02_1"><%=dbox.getString("d_study_count")%></td>
               				</tr>
                	<%
                		}
                	%>
                <%}else{%>
                	<tr>
                		<td class="table_02_1" colspan="9">조회된 데이터가 없습니다.</td>
                	</tr>
                <%}%>
            </table>
			<!----------------- List 종료 ----------------->
      
</body>
</html>

