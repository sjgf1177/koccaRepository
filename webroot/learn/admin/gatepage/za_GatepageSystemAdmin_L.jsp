<%
//**********************************************************
//  1. 제      목: 게이트시스템 운영자에게 관리 리스트화면
//  2. 프로그램명: zu_GatepageSystemAdmin_L.jsp
//  3. 개      요: 게이트시스템 운영자에게 관리 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.gatepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0, v_dispnum = 0;
int v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
int v_pageno = box.getInt("p_pageno"); 
String v_name = "", v_jikwinm = "", v_post = "", v_cono = "", v_title = "", v_ismail = "", v_gubun, s_gubun="" ,s_selected="";

ArrayList list = (ArrayList)request.getAttribute("selectList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");


String s_userid = box.getSession("userid");
System.out.println(s_userid);
s_gubun = box.getString("p_gubun");
System.out.println("s_gubun ====>" + s_gubun);

System.out.println("s_selected=========" + s_selected);

String s_usernm = box.getSession("name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>GatePage운영자관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script  language="javascript">
<!--
function go(index) {
	 document.form1.p_pageno.value = index;
	 document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
	 document.form1.p_process.value = "selectList";     
	 document.form1.submit();
            }            
function goPage(pageNum) {
	 document.form1.p_pageno.value = pageNum;
	 document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
	 document.form1.p_process.value = "selectList";     
	 document.form1.submit();
}

function selectList() {
	document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
	document.form1.p_process.value = "selectList";   
	document.form1.p_pageno.value = "1";       
	document.form1.submit();
}     

function answer(num,cono) {
	document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
	document.form1.p_process.value = "answer";
	document.form1.p_seq.value = num;
	document.form1.p_cono.value = cono;
	document.form1.submit();
}

function changeCategory() {
        document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
        document.form1.p_process.value    = "";
        document.form1.submit();
}
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post">
       <input type = "hidden" name = "p_process" value = "">
        <input type = "hidden" name = "p_pageno" value = "">
        <input type = "hidden" name = "p_seq" value = "">
        <input type = "hidden" name = "p_cono" value = "">
		
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/gatepage/cp_title08.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
        </tr>
      </table>
	         <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="455" class=dir_txt>답변은 개인 메일로 발송됩니다. <br></td>



          <td width="100"><p>
		  
		
		  <select name="p_gubun" size="1" onChange="javascript:selectList()">
						<option value="00">== 선택 ==</option>                                
						<option value="01" <%if (s_gubun.equals("01")){ %> selected<% }%>>외주관리</option>
						<option value="02"  <%if (s_gubun.equals("02")){ %> selected<% }%>>고용보험관리</option>
						<option value="03"  <%if (s_gubun.equals("03")){ %> selected<% }%>>통합강사관리</option>
						<option value="04"  <%if (s_gubun.equals("04")){ %> selected<% }%>>베타테스트</option>
		 </select></p></td>
          
          <td width="77" class=txt_input>제목검색:</td>
          <td width="166"><input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                name = "p_searchtext" value = "" size = "25" maxlength = "30"> </td>
          <td width="32" align="right"><a href="javascript:selectList()"><img src="/images/admin/button/btn_inquiry.gif" border="0"></a></td>
        </tr>
        <tr> 
          <td height=6></td>
          <td align="right" height=6 colspan="2"></td>
          <td width="77" height=6></td>
          <td align="right" height=6></td>
        </tr>
      </table>
      <table width="971"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td class="table_title" width="5%">NO</td>
          <td class="table_title"   width="20%">소속</td>
          <td class="table_title"   width="10%">직위</td>
          <td class="table_title"   width="9%">사번</td>
          <td class="table_title"   width="12%">성명</td>
          <td class="table_title"   width="644">제목</td>
          <td class="table_title"   width="100">답변</td>          
        </tr>
<%if(list.size() != 0){%>		
		<!--for문 시작하는 곳-->
		<%for(i = 0; i < list.size(); i++) {
					DataBox dbox = (DataBox)list.get(i);  
                    
					v_name   = dbox.getString("d_name");
					v_jikwinm    = dbox.getString("d_jikwinm");
					
                  	v_seq = dbox.getInt("d_seq");
					v_post = dbox.getString("d_post");
					v_cono = dbox.getString("d_cono");
					v_title = dbox.getString("d_quetitle");
					v_ismail = dbox.getString("d_ismail");
					v_totalpage = dbox.getInt("d_totalpage");
					v_rowcount = dbox.getInt("d_rowcount");  
					v_dispnum = dbox.getInt("d_dispnum");
				  
				  if (!v_searchtext.equals("")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                        // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
		}%>
                <tr>
          <td class="table_02_1" width="48"><%=v_dispnum%></td>
          <td class="table_02_1" width="192"><%=v_post%></td>
          <td class="table_02_1" width="96"><%=v_jikwinm%></td>
          <td class="table_02_1" width="86"><a href="javascript:answer('<%=v_seq%>','<%=v_cono%>')"><%=v_cono%></a></td>
          <td class="table_02_1" width="115"><%=v_name%></td>
          <td class="table_02_1" width="644"><%=v_title%></td>
          <td class="table_02_1" width="100"><%=v_ismail%></td>
        </tr>
		<%}%>
<%}else{%>
           

                                                    <tr>
                                                        <td class="table_02_1" colspan="13" align=center >
															등록 된 문의가 없습니다.		
														</td>
													</tr>

<%}%>
     <!--for 문 끝나는 곳-->
      </table>
      
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height=6></td>
          <td height=6></td>
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="12" height=6></td>
          <td align="right" height=6></td>
        </tr>
        <tr> 
		<td height=20 colspan="11"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
          <!--버튼있던 부분-->
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
