<%
/**
 * file name : za_SulmunSubjDetailResult_E.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 설문결과 Excel상세보기
 */
%>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<%@ page errorPage = "/learn/library/error.jsp" %>
<% 
	response.setHeader("Content-Disposition", "inline; filename=za_SulmunSubjResult.xls"); 
	response.setHeader("Content-Description", "JSP Generated Data"); 
%> 
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

	String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  v_sulpapernum       = box.getInt("p_sulpapernum");
	String  ss_subjseq    = box.getString("s_subjseq");

	String  ss_mailgubun       = box.getString("s_mailgubun");

    ArrayList list = (ArrayList)request.getAttribute("SulmunDetailResultExcelPage");System.out.println("ArrayList" + list);
    DataBox dbox0 = (DataBox)request.getAttribute("SulmunPaperData");

    String  v_action    = box.getString("p_action");
 
System.out.println("d_totcnt" + dbox0.getInt("d_totcnt"));
%>
<html>
<head>
<title>설문결과 Excel상세보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.multiestimate.SulmunSubjMailServlet">

<table width="1000" border="1" cellspacing="1" cellpadding="0" height="663">
        <tr>
          <td class="table_title" rowspan="2">사번</td>
          <td class="table_title" rowspan="2">성명</td>
		  <td class="table_title" rowspan="2">사업부</td>
          <td class="table_title" rowspan="2">소속</td>
          <td class="table_title" rowspan="2">직위</td>
		  <td class="table_title" colspan="<%=dbox0.getInt("d_totcnt")%>">설문</td>
		</tr>
		<tr>

<%  
        for (int k=0; k<dbox0.getInt("d_totcnt"); k++) {	%>
			<td class="table_title">문제<%=k+1%></td>
<%    } %>
        </tr>
<%
        for (int i=0; i<list.size(); i++) {
            DataBox dbox2  = (DataBox)list.get(i);  	
%>
        <tr>
		  <td class="table_02_1">&nbsp;<%=dbox2.getString("d_userid")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_subjusernm")%></td>
		  <td class="table_02_1"><%=dbox2.getString("d_divinam")%></td>
		  <td class="table_02_1"><%=dbox2.getString("d_subjasgmnm")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_jikwinm")%></td>
<%    
		String point = dbox2.getString("d_selpoint");//System.out.println("fpoint" + fpoint);
		
		StringTokenizer st = new StringTokenizer(point, ",");
		while(st.hasMoreElements()) {                            
			String token = st.nextToken();
			if(token.equals("")) break;
%>
			<td class="table_02_1"><%=token%></td>
<%    } 
%>
        </tr>
<%  
	 }
%>
        </table>
      <br>
    </td>
  </tr>
</table>

</form>
</body>
</html>
