<%
//**********************************************************
//  1. 제      목: Activity
//  2. 프로그램명 : Activity
//  3. 개      요: Activity
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 2007.4.21 NOH HEE SUNG
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList list = (ArrayList)request.getAttribute("selectList");
    
    String s_userid = box.getSession("userid");
    
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    
    int v_pageno    = box.getInt("p_pageno");
		if (v_pageno == 0) v_pageno = 1;
		
		int row= 20;

%>



<script language="JavaScript" type="text/JavaScript">
<!--
//검색된 페이지로 이동
function selectList() {
    document.ff.action = "/servlet/controller.study.StudyActivityServlet";
    document.ff.p_pageno.value = "1";
    document.ff.p_process.value = "List";
    document.ff.submit();
}

//페이지 이동
function go(index) {
	document.ff.p_pageno.value = index;
	document.ff.action = "/servlet/controller.study.StudyActivityServlet";
	document.ff.p_process.value = "List";
	document.ff.submit();
}

//페이지 이동
function goPage(pageNum) {
	 document.ff.p_pageno.value = pageNum;
	 document.ff.action = "/servlet/controller.study.StudyActivityServlet";
	 document.ff.p_process.value = "List";
	 document.ff.submit();
}

//저장
function whenSubmit() {

	 if (document.ff.p_content.value=="") {
	 		alert("내용을 입력하세요");
	 		document.ff.p_content.focus();
	 		return; 
	 		
	 } else {
		 document.ff.action = "/servlet/controller.study.StudyActivityServlet";
		 document.ff.p_process.value = "insert";
		 document.ff.submit();
	 }
}

function whenDelete(p_seq) {

	if (confirm("삭제하시겠습니까?")) {
		document.ff.p_seq.value = p_seq;
		document.ff.action="/servlet/controller.study.StudyActivityServlet";
		document.ff.p_process.value="delete";
		document.ff.submit();
	}
	
}
//-->
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>Untitled Document</title>
<style type="text/css">
<!--
body, td {
	margin-left: 0px;
	margin-top: 0px;
	font-size:12px;
	font-family:굴림,굴림체; color:"#616161"; font-size:9pt; }





}
-->
</style>
<script type="text/JavaScript">
<!--
function MM_callJS(jsStr) { //v2.0
  return eval(jsStr)
}



//-->
</script>
</head>

<body onload="javascript:document.ff.p_content.focus();">
<table width="66%" height="569" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="90%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/images/user/kocca/activity/arcade/arcade-1.jpg" width="680" height="101" border="0" usemap="#Map" /></td>
      </tr>
      <tr>
        <td background="/images/user/kocca/activity/arcade/arcade-2.jpg" width="680" height="94">
		 
		 
		 </td>
		
		
      </tr>
      <tr>
        <td >
<table id="Table_01" width="680" height="375" border="0" cellpadding="0" cellspacing="0">
			<form name="ff" method="post" action="" onSubmit="whenSubmit();">
				<input type="hidden" name="p_seq" value="">
				<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
				<input type="hidden" name="p_process" value="">

	<tr>
		<td width="35" rowspan="3">
			<img src="/images/user/kocca/activity/arcade/arcade-3_01.jpg" width="35" height="375" alt="" /></td>
		        <td width="91"><img src="/images/user/kocca/activity/arcade/arcade-3_02.jpg" width="81" height="37" alt="" /> 
                </td>
		        <td width="518" valign=middle>
				
				<input type="text" name="p_content" alue="80자 이내로 내용을 입력해주세요." size="60" />&nbsp;&nbsp;&nbsp;
        
				 

				  
				  
				  <a href="javascript:whenSubmit();"><img src="/images/user/kocca/activity/arcade/btn.jpg" border=0 width="34" height="25" /></a></td>
		<td width="36" rowspan="3">
		    
			<img src="/images/user/kocca/activity/arcade/arcade-3_04.jpg" width="36" height="375" alt="" /></td>
	</tr>
	</form>
    	<tr>
		     <td valign=top colspan=2 background="/images/user/kocca/activity/arcade/arcade-3_05.jpg" width="609" height="303" alt="" />
		     <br>
		     <%	
for(int i = 0; i < list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);
				int	   d_seq		 = dbox.getInt("d_seq");
				String d_name    = dbox.getString("d_name");
				String d_content    = dbox.getString("d_input01");
				String d_ldate    = dbox.getString("d_ldate");
				String d_resno    = dbox.getString("d_resno");

			v_dispnum   = dbox.getInt("d_dispnum");
				v_totalpage = dbox.getInt("d_totalpage");
				v_rowcount  = dbox.getInt("d_rowcount");
%>


		     <table>
		       <tr>
		         <td width=90><font color=616161><%=d_name%></font></td>
		         <td width=70%><font color=616161><%=d_content%></font></td>
		         <td width=150><font color=616161><%=FormatDate.getFormatDate(d_ldate,"yyyy-MM-dd")%> <% if (s_userid.equals(d_resno)) { %><a href="javascript:whenDelete('<%=d_seq%>')">(삭제)</a><% } %></font></td>
		       </tr>
		     </table>
<% } %>


<%   if (list.size()==0) {                         %>
                      등록된 내용이 없습니다.
                      
<%   } %>

				<table align=center>
		       <tr align=center>
		         <td align=center><%= StudyActivityBean.printPageList(v_totalpage, v_pageno, row) %></td>
		       </tr>
		     </table>
		     
		     
		     
		     
		     
				</td>
        
        
     </tr>
     
	 </td>
	</tr>
	
	<tr>
		<td colspan="2">
			<img src="/images/user/kocca/activity/arcade/arcade-3_06.jpg" width="609" height="35" alt="" /></td>
	</tr>

	
                           <tr align=center> 
                        <td colspan=2 align='center'><table align=center><tr><td></td></tr></table</td>
                      </tr>



</table>
<!-- End ImageReady Slices -->
        </td>
      </tr>
    </table>
  


<map name="Map" id="Map">
<area shape="rect" coords="613,0,659,20" href="#" onClick="MM_callJS('window:close()')" />
</map>
</body>
</html>
