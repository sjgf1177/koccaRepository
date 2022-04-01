<%
/**
 * file name : za_HuryunExe_E.jsp
 * date      : 2004/12/29
 * programmer: Lee Chang Hun
 * function  : 훈련실시신고 인쇄
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.scorm.*" %>
<%@ page import = "com.credu.goyong.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
 
<%
	response.setHeader("Content-Disposition", "inline; filename=StudentHuryunEXE1.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String  v_action    = box.getString("p_action");
	String  v_subj      =box.getString("p_subj");
	ArrayList list = null;
	int listsize = 0;
	    list = (ArrayList)request.getAttribute("StudentListView");
    	listsize = list.size();

		//out.println("11111====>"+listsize);
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
            <td class="table_title" width="5%">No</td>
            <td class="table_title" width="15%">회원구분</td>
            <td class="table_title" width="20%">주민등록번호</td>
            <td class="table_title" width="25%">ID</td>
            <td class="table_title" width="25%">성명</td>
          </tr>


<%       
        String v_resno ="";
        
		for ( int i=0; i<list.size(); i++ ) {
			DataBox dbox = (DataBox)list.get(i);
			
			v_resno = dbox.getString("d_resno");
			
			if(v_resno.length()  == 13){
			  v_resno = dbox.getString("d_resno").substring(0,6) + "-*******";
			}else{
			  v_resno = v_resno +"(<font color=red>주민번호 오류</font>)";
			}
%>

           <tr class="table_02_1">
                <td class="table_02_1"><%= (i+1) %></td>
                <td class="table_02_1"><%= dbox.getString("d_membergubunnm") %></td>
                <td class="table_02_1"><%= v_resno %></td>
                <td class="table_02_1"><%= dbox.getString("d_userid") %></td>
                <td class="table_02_1"><%= dbox.getString("d_name") %></td>
            </tr>

<%
		}
%>
		
		</table>
        

      </td>
  </tr>
</table>

</body>
</html>
