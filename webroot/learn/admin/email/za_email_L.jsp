<%
/**
 * file name : za_email_L.jsp
 * date      : 2008/10/21
 * programmer: Lee
 *
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.email.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }


  
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
  
//-->
</SCRIPT>
<%
//   }
   // else {
%>
	<html>
	<head>
	<title>Manager Screen</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
	</head>

  		
		<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
		<table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.email.EmailServlet">
         <%--<input type="hidden" name="s_grseq"      value="<%=ss_grseq%>">--%>
        <%--<input type="hidden" name="s_upperclass"     value="<%=ss_upperclass%>">--%>
        <%--<input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>">--%>
        <%--<input type="hidden" name="s_subjseq"    value="<%=ss_subjseq%>">--%>
        <%--<input type="hidden" name="s_comp"       value="<%=ss_company%>">--%>
        <!----------------- 단일클래스구성 시작 ----------------->
        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title">과정명</td>
          <td class="table_title"><b>ID</b></td>
          <td class="table_title"><b>신청일</b></td>
        </tr>

<%
    ArrayList  list = (ArrayList)request.getAttribute("EmailList");


    EmailData data = null;
//    Vector  tutor = null;
//    TutorSelectData tutordata = null;

    if (list.size() != 0 ) {    //내용이 있다면

        for (int i=0; i<list.size(); i++) {
        data  = (EmailData)list.get(i);
//        v_subjcnt = data.getSubjcnt();

        // 전문가과정여부에 따라 달리 출력

%>
            <tr>
              <td class="table_02_2"  rowspan="<%=data.getClassname()%>"><%=data.getClassname()%></td>
              <td class="table_02_1"><%=data.getUserId()%></td>
              <td class="table_02_1"><%=data.getRequestDate()%></td>              
            </tr>
			
<%
        }
		

    }else{

%>
        <tr>
           <td align="center" bgcolor="#F7F7F7" height="50" colspan="11">등록된 내용이 없습니다</td>
        </tr>
<%
}
%>


    </form>
  </table>

</body>
</html>
<%
 //  }
%>
