<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 Sw자료실 리스트화면
//  2. 프로그램명: zu_CpFree_I.jsp
//  3. 개      요: 외주관리 시스템 Sw자료실 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 19
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0,v_upfilecnt = 0;;
String  v_classname = "";
String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1;

ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");
String s_usernm = box.getSession("name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템 자료실</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/cp_style.css">
<script language = "javascript">

function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.cp.CpSwServlet";
	document.form1.p_process.value = "";     
    document.form1.submit();
}            
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.cp.CpSwServlet";
	document.form1.p_process.value = "";     
    document.form1.submit();
}                
//조회리스트
function selectList() {
    document.form1.action = "/servlet/controller.cp.CpSwServlet";
	document.form1.p_process.value = "";   
    document.form1.p_pageno.value = "1";       
    document.form1.submit();
}           
//자료 이력 화면 
function insertPage() {
    document.form1.action = "/servlet/controller.cp.CpSwServlet";
	document.form1.p_process.value = "insertPage";  
    document.form1.p_pageno.value = "<%= v_pageno %>";    
    document.form1.submit();
}

//조회
function select(num,upfilecnt) {
    document.form1.action = "/servlet/controller.cp.CpSwServlet";
	document.form1.p_upfilecnt.value = upfilecnt;
	document.form1.p_process.value = "select";
    document.form1.p_seq.value = num;
    document.form1.p_userid.value = "<%=v_userid%>";
	document.form1.p_pageno.value = "<%= v_pageno %>"; 
    document.form1.submit();
}

//폼메일 보내기
function formMailing() {
    document.form1.action = "/servlet/controller.cp.CpSwServlet";
	document.form1.p_process.value = "formMail";
    document.form1.submit();
    }
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/images/cp/m_board_on.gif','/images/cp/m_data_on.gif','/images/cp/m_company_on.gif','/images/cp/m_sdata_on.gif','/images/cp/m_swdata_on.gif','/images/user/button/btn_idpw_on.gif','/images/user/button/btn_login_on.gif')">
<!-- top -->
 <form name = "form1" method = "post">

        <input type = "hidden" name = "p_process"   value = "">
        <input type = "hidden" name = "p_pageno"    value = "">
        <input type = "hidden" name = "p_seq"       value = "">
        <input type = "hidden" name = "p_userid"    value = "">
		<input type = "hidden" name = "p_upfilecnt" value = "">

<%@include file="zu_Include.jsp"%>
<!-- center -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_subtotal.gif">
  <tr> 
    <td valign="top">
	<!-- topimg -->
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="12" valign="top"><img src="/images/cp/img_sub_head.gif"></td>
          <td width="888" valign="top"><img src="/images/cp/dataimg.jpg"></td>
        </tr>
        <tr valign="top"> 
          <td colspan="2">
		
		  <table width="900" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20" align="center" valign="top"></td>
              </tr>
              <tr> 
                <td align="center" valign="top">
				<table width="835" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/cp/tit_swdata.gif"></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center">
					  <!-- 리스트테이블 -->
					  <table width="800" border="2" cellspacing="0" 
              cellpadding="3" style="border-collapse:collapse;" bordercolor="#D5D5D5" >
                          <tr> 
                            <td width="40" class="tbl_grtit" > <font color="#FF6600" size="-2">▶</font>번호</td>
                            <td  width="472" class="tbl_grtit"><font color="#FF6600" size="-2">▶</font>제목</td>
                            <td  width="62" class="tbl_grtit"><font color="#FF6600" size="-2">▶</font>작성자</td>
                            <td  width="59" align=center class="tbl_grtit"><font color="#FF6600" size="-2">▶</font>작성일</td>
                            <td  width="65" align=center class="tbl_grtit"><font color="#FF6600" size="-2">▶</font>첨부파일</td>
                            <td  width="50" align=center class="tbl_grtit"><font color="#FF6600" size="-2">▶</font>조회수 
                            </td>
                          </tr>

<%		for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);  
                   v_dispnum = dbox.getInt("d_dispnum");
                   v_userid   = dbox.getString("d_userid");
                   v_name   = dbox.getString("d_name");
                   v_indate    = dbox.getString("d_indate");
                   v_title    = dbox.getString("d_title");
                   v_seq      = dbox.getInt("d_seq");
                   v_readcnt  = dbox.getInt("d_cnt");
                   v_realmotion  = dbox.getString("d_realmotion");
                   v_savemotion  = dbox.getString("d_savemotion");
                   v_upfilecnt   = dbox.getInt("d_filecnt");
                   v_totalpage = dbox.getInt("d_totalpage");
                   v_rowcount = dbox.getInt("d_rowcount");   //---------------------------------------------------------------------------------------------------
                                                                    
                   if (!v_searchtext.equals("")&&v_select.equals("title")) {
                   v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                   // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
					} else if(!v_searchtext.equals("")&&v_select.equals("name")){
						v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					}
				   if ((i % 2) == 0) {
					 v_classname = "b_list_W";
				   } else {
					 v_classname = "b_list_G";
				   }
				   %>

                          <tr> 
                            <td width="37" class="tbl_grc"><%= v_dispnum %></td>
                            <td class="tbl_bleft"><a href = "javascript:select('<%=v_seq%>', '<%=v_upfilecnt%>')">&nbsp;&nbsp;<%= v_title %></a></td>
                            <td class="tbl_grc"><%= v_name %></td>
                            <td  width="59" class="tbl_grc" ><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                            <td  width="65" class="tbl_grc" ><%if (v_upfilecnt != 0){
					%><img src="/images/cp/ico_file.gif" width="14" height="14"><%
				}else{%>
					
				<%}%></td>
                            <td  width="50" class="tbl_grc" ><%=v_readcnt%></td>
                          </tr>

			 
		<%}if (i == 0 ) {                         %>


                          <tr>
                              <td class=b_list_W colspan="13" align=center >
									등록 된 내용이 없습니다.		
								</td>
							</tr>

<%}%>             
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="center">
					    <table width="800" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="10" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td width = "700" align="right"> 
								<%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %>
							</td>
							<% if (box.getSession("gadmin").equals("A1")) { %>
                            <td align="right"><a href="javascript:insertPage()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_register.gif" width="45" height="21" border="0"></a></td>
                            <% } %>
                          </tr>
                        </table>
					  </td>
                    </tr>
                  </table>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="269">&nbsp;</td>
                      <td width="59"> <select name = "p_select" class = "input">
                          <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
                          <option value = "name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
						  <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
                        </select> </td>
                      <td width="133"><input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                name = "p_searchtext"  size = "25" maxlength = "30"></td>
                      <td width="339"><a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/cp/btn_search.gif" ></a></td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="4"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>


<%@ include file = "/include/cp_footer.jsp"%>


<%@ include file = "/learn/library/getJspName.jsp" %> 

</body>
</html>