<%
//**********************************************************
//  1. 제      목: TUTOR LIST
//  2. 프로그램명: za_TutorStudent_LE.jsp
//  3. 개      요: 강사체점 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
 
<%
	response.setHeader("Content-Disposition", "inline; filename=TutorStudentEXE1.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_subj			= "";
    String v_subjnm         = "";
    String v_year           = "";
    String v_subjseq		= "";
    String v_class          = "";
    String v_tuserid		= "";
    String v_tusernm        = "";
    String v_stucnt			= "";
    String v_tucnt			= "";
    String v_fmon		    = "";
    String v_tmon		    = "";
	String temtuserid		= "";

    int     i               =  0;

	ArrayList list = (ArrayList)request.getAttribute("TutorSubjList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
	        <table cellspacing="1" cellpadding="5" border="1">
          <tr>
            <td width="5%" class="table_title"><b>No</b></td>
            <td width="10%" class="table_title"><b>강사명</b></td>
            <td class="table_title"><b>과정</b></td>
            <td width="10%" class="table_title"><b>년도</b></td>
            <td width="10%" class="table_title"><b>과정차수</b></td>
            <td width="10%" class="table_title"><b>클래스</b></td>
            <td width="10%" class="table_title"><b>수강인원</b></td>
			<td width="5%" class="table_title"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
          </tr>
<% 

            for(i = 0; i < list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);
				
                v_subj			= dbox.getString("d_subj");
                v_subjnm	    = dbox.getString("d_subjnm");
                v_year          = dbox.getString("d_year");
                v_subjseq		= dbox.getString("d_subjseq");
                v_class			= dbox.getString("d_class");
                v_tuserid       = dbox.getString("d_tuserid");
                v_tusernm		= dbox.getString("d_tusernm");
                v_stucnt        = dbox.getString("d_stucnt");
%>
          <tr>
            <td class="table_01"><%= list.size() - i %></td>
            <td class="table_02_1"><%=v_tusernm%></td>
            <td class="table_02_2"><%=v_subjnm%></td>
            <td class="table_02_1"><%=v_year%></a></td>
            <td class="table_02_1"><%=v_subjseq%></td>
            <td class="table_02_1"><%=v_class%></td>
            <td class="table_02_1"><%=v_stucnt%></td>
            <td class="table_02_1">
<% if(!v_tuserid.equals(temtuserid)){%>
				<input type="checkbox" name="p_checks" value="<%=v_tuserid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>">
<%}%>
			</td>
          </tr>
<%
			if(!temtuserid.equals(v_tuserid)) temtuserid	= v_tuserid;
		}
%>
		
		</table>
        

      </td>
  </tr>
</table>

</body>
</html>