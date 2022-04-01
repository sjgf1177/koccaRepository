<%
//**********************************************************
//  1. 제      목: 게이트시스템 공지사항 관리 리스트화면
//  2. 프로그램명: zu_GatepageNotice_L.jsp
//  3. 개      요: 게이트시스템 공지사항 관리 리스트화면
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

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0;

String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");

String s_usernm = box.getSession("name");
String s_gubun = "";

s_gubun = box.getString("p_gubun");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>GateSystem공지사항</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
<!--
function go(index) {
     document.form1.p_pageno.value = index;
     document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
	 document.form1.p_process.value = "";     
     document.form1.submit();
}            
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
	 document.form1.p_process.value = "";     
     document.form1.submit();
}                
function selectList() {
	document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
	document.form1.p_process.value = "selectList";   
	document.form1.p_pageno.value = "1";       
	document.form1.submit();
}            
function insertPage() {
	document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
	document.form1.p_process.value = "insertPage";  
	document.form1.p_pageno.value = "<%= v_pageno %>";    
	document.form1.submit();
}
function select(num) {
	document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
	document.form1.p_process.value = "select";
	document.form1.p_seq.value = num;
	document.form1.p_userid.value = "<%=v_userid%>";
	document.form1.p_pageno.value = "<%= v_pageno %>"; 
	document.form1.submit();
}
function changeCategory() {
        document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
        document.form1.p_process.value    = "selectList";
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
    <input type = "hidden" name = "p_userid" value = "">


	
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/gatepage/cp_title06.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="98">
		  <!--시스템구분시작-->
		  	<select name="p_gubun" size="1" onChange="javascript:changeCategory()">
                                 
                <option value="AE"	<%if (s_gubun.equals("AE")){ %> selected<% }%>>외주관리</option>
                <option value="BG"  <%if (s_gubun.equals("BG")){ %> selected<% }%>>고용보험관리</option>
                <option value="DF"  <%if (s_gubun.equals("DF")){ %> selected<% }%>>통합강사관리</option>
                <option value="CF"  <%if (s_gubun.equals("CF")){ %> selected<% }%>>베타테스트</option>
			</select>
		  
		  <!--시스템구분끝--> 
		  </td>
          <td width="667" align="right" valign="middle">
		  
		  <select name = "p_select" class = "input">
                <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
          		<option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
          </select>
				 
				 </td>
          <td width="124" align="right" valign="middle"> 
		  <input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                                                 name = "p_searchtext"  size = "15" maxlength = "15"></td>
          <td width=9></td>
          <td width="32" align="right" valign="middle"><a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/admin/button/btn_search.gif" border="0"></a></td>
          <td width=8></td>
          <td width="32" align="right" valign="middle"><a href="javascript:insertPage()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height=8 width="98"></td>
          <td height=8 width="667"></td>
          <td height=8 width="124"></td>
          <td height=8 width="9"></td>
          <td height=8 width="32"></td>
          <td height=8 width="8"></td>
          <td height=8 width="32"></td>
        </tr>
      </table>
		  <table width="970" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			  <td colspan="7" class="table_top_line"></td>
			</tr>

			<tr> 
			  <td width="81"  class="table_title">번호</td>
			  <td width="1" class="table_title"><img src="/images/gate/L_board_Tg.gif"></td>
			  <td width="638" class="table_title">제목</td>
			  <td width="1" class="table_title"><img src="/images/gate/L_board_Tg.gif"></td>
			  <td width="70"  class="table_title">파일</td>
			  <td width="1" class="table_title"><img src="/images/gate/L_board_Tg.gif"></td>
			  <td width="178" class="table_title">날짜</td>
			</tr>
			<tr> 
			   <td colspan="17" align="center" class=b_guide></td>
			</tr>
				  
<%if(list.size() != 0){%>				  
			<!--내용 시작하는 곳-->
			<%  for(i = 0; i < list.size(); i++) {
					   DataBox dbox = (DataBox)list.get(i);  
					   v_dispnum = dbox.getInt("d_dispnum");
					   //v_userid   = dbox.getString("d_userid");
					   v_name   = dbox.getString("d_adname");
					   v_indate    = dbox.getString("d_addate");
					   v_title    = dbox.getString("d_adtitle");
					   v_seq      = dbox.getInt("d_seq");
					   v_readcnt  = dbox.getInt("d_adreadcnt");
					   v_realmotion  = dbox.getString("d_realmotion");
					   v_savemotion  = dbox.getString("d_savemotion");
					   v_upfilecnt   = dbox.getInt("d_filecnt");
					   v_totalpage = dbox.getInt("d_totalpage");
					   v_rowcount = dbox.getInt("d_rowcount");  
																				
					   //------------------------------------------------------------------------------------------------------
																		
					   if (!v_searchtext.equals("")) {
					   v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					   // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
					   }%>
			
			<tr> 


			
			  <td align="center" class=b_list_W><%= v_dispnum %></td>
			  <td width="1" valign="bottom" class=b_list_W><img src="/images/gate/L_board_Lg.gif"></td>
			  <td class=b_list_W style="padding-left:10"><a href = "javascript:select('<%=v_seq%>', '<%=v_upfilecnt%>', '<%=v_userid%>')"><%= v_title %></a></td>
			  <td width="1" valign="bottom" class=b_list_W><img src="/images/gate/L_board_Lg.gif"></td>
			  <td  align="center" class=b_list_W>
			  
			  <%if (v_upfilecnt != 0){
						%><img src="/images/gate/icon_file.gif" width="13" height="12"><%
					}else{%>
						
			  <%}%></td>
			  
			  <td width="1" valign="bottom" class=b_list_W><img src="/images/gate/L_board_Lg.gif"></td>
			  <td align="center" class=b_list_W><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
				
			</tr>
		
			<tr> 
					<td colspan="17" align="center" class=b_guide></td>
			</tr>

			<%}%>	
<%}else{%>
           

                                                    <tr>
                                                        <td class=b_list_W colspan="13" align=center >
															등록 된 공지사항이 없습니다.		
														</td>
													</tr>

<%}%>			
			<!--내용 끝나는곳-->
			<tr> 
				<td colspan="7" class=b_list_bottom></td>
			 </tr> 	
		  </table>
    
 <table width="970" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height = "30" valign = "bottom" align = "right" colspan = "3"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
                    </tr>
                  </table>
      
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
