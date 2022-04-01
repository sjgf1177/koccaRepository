<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_NoticeGrcode_L.jsp
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
    ArrayList grcodelist = (ArrayList)request.getAttribute("grcodenm"); 
    
    String v_grcodecd = box.getString("p_grcodecd");
    String v_loginyn  = box.getString("p_loginyn");
    
    String s_gadmin = box.getSession("gadmin");
    String v_gadmin = "";
    if(!s_gadmin.equals("")){
      v_gadmin      = s_gadmin.substring(0,1);
    }
    
    String gubun = box.getString("gubun");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript">



</script>
</head>
<body>
<form name="grcodeform" method="post" >

<%if(!v_gadmin.equals("K")){%>
<table border = "0">
    <%
    int v_i = 0;
    int v_lineChang = 4;
    int v_grcodeCnt = grcodelist.size();
    
      for(int i=1; i<v_grcodeCnt; i++){
    	DataBox grdbox = (DataBox)grcodelist.get(i);
    	if( ( (i+4)%v_lineChang == 1) ){
    	%>
    	<tr height="18" class="table_02_2"> 
    	<%
    	
    	}
    %>
         <td class="table_02_2" >
    <% 	if( gubun.equals("admin") && grdbox.getString("d_grcode").equals("N000001") ) { %>
    		<input type="checkbox" name="p_grcode" onClick="parent.chooseonoff(this)" value="<%=grdbox.getString("d_grcode")%>"  <%if(v_grcodecd.indexOf(grdbox.getString("d_grcode") ) >-1 ){out.println("checked");}%>> <%=grdbox.getString("d_grcodenm")%>
    <%	} else { %>
         	<input type="checkbox" name="p_grcode" value="<%=grdbox.getString("d_grcode")%>"  <%if(v_grcodecd.indexOf(grdbox.getString("d_grcode") ) >-1 ){out.println("checked");}%>> <%=grdbox.getString("d_grcodenm")%>
    <% 	} %>
         </td>
    <%
      if( ( i%v_lineChang == 0) ){
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

<%if(v_loginyn.equals("Y")){%>
      if(document.grcodeform.all['p_grcode'] == '[object]') {
          if (document.grcodeform.p_grcode.length > 0) {
            for (i=0; i<document.grcodeform.p_grcode.length; i++) {
              document.grcodeform.p_grcode[i].disabled = true;
            }
          } else {
              document.grcodeform.p_grcode.disabled = true;
          }
       }
<%}%>
</script>

<%}else{%>
    <input type="hidden" name="p_grcode" value=''  >
<%}%>
</form>
</body>
</html>