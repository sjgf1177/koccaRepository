<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_I.jsp
//  3. 개      요: 공지사항 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 8. 1
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList complist   = (ArrayList)request.getAttribute("compnm"); 
    
    String v_gubun  = box.getString("p_gubun");
    String v_compcd = box.getString("p_compcd");
%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript">



</script>
</head>
<body>
<form name="compform" method="post" >
<table>
    <%
    int v_i = 0;
    int v_lineChang = 5;
    int v_compCnt = complist.size();
    
      for(int i=0; i<v_compCnt; i++){
    	NoticeData compnm = (NoticeData)complist.get(i);
    	if( ( (i+6)%v_lineChang == 1) ){
    	%>
    	<tr height="18" class="table_02_2"> 
    	<%
    	
    	}
    %>
         <td class="table_02_2" width="170" ><input type="checkbox" name="p_comp" value="<%=compnm.getComp()%>" <%if(v_compcd.indexOf(compnm.getComp()) >-1 ){out.println("checked");}%> > <%=compnm.getCompnm()%></td>
    <%
      if( ( (i+1)%v_lineChang == 0) ){
    %>
       </tr>
    <%
      }
      }
    %>
   </td>
 </tr>
</table>
<script language='javascript'>

<%if(v_gubun.equals("Y")){%>
      if(document.compform.all['p_comp'] == '[object]') {
          if (document.compform.p_comp.length > 0) {
            for (i=0; i<document.compform.p_comp.length; i++) {
              document.compform.p_comp[i].checked = true;
              document.compform.p_comp[i].disabled = true;
            }
          } else {
              document.compform.p_comp.checked = true;
              document.compform.p_comp.disabled = true;
          }
       }
<%}%>

</script>
</form>
</body>
</html>