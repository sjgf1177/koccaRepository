<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 자료실 리스트화면
//  2. 프로그램명: zu_CpMateria_L.jsp
//  3. 개      요: 외주관리 시스템 자료실 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
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

int v_seq = 0,v_readcnt = 0,i = 0;
String  v_classname = "";
String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

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
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
<script language = "javascript">
            function go(index) {
                 document.form1.p_pageno.value = index;
                 document.form1.action = "/servlet/controller.cp.CpMateriaServlet";
				 document.form1.p_process.value = "";     
                 document.form1.submit();
            }            
            function goPage(pageNum) {
                 document.form1.p_pageno.value = pageNum;
                 document.form1.action = "/servlet/controller.cp.CpMateriaServlet";
				 document.form1.p_process.value = "";     
                 document.form1.submit();
            }                
            function selectList() {
            	document.form1.action = "/servlet/controller.cp.CpMateriaServlet";
				document.form1.p_process.value = "";   
            	document.form1.p_pageno.value = "1";       
            	document.form1.submit();
            }            
            function insertPage() {
            	document.form1.action = "/servlet/controller.cp.CpMateriaServlet";
				document.form1.p_process.value = "insertPage";  
            	document.form1.p_pageno.value = "<%= v_pageno %>";    
            	document.form1.submit();
            }
            function select(num) {
            	document.form1.action = "/servlet/controller.cp.CpMateriaServlet";
				document.form1.p_process.value = "select";
            	document.form1.p_seq.value = num;
            	document.form1.p_userid.value = "<%=v_userid%>";
				document.form1.p_pageno.value = "<%= v_pageno %>"; 
            	document.form1.submit();
            }
            function updateOpenPage(num) {
                window.self.name = "winSelectList";     //      opener 객체에 name 을 지정한다

                window.open("", "openWinUpdate", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 700, height = 500, top=0, left=0");      
                document.form1.target = "openWinUpdate"
                document.form1.action = "/servlet/controller.cp.CpMateriaServlet";
				document.form1.p_process.value = "updateOpenPage";
                document.form1.p_seq.value = num;
                document.form1.submit();
            }    
            function formMailing() {
            	document.form1.action = "/servlet/controller.cp.CpMateriaServlet";
				document.form1.p_process.value = "formMail";
            	document.form1.submit();
            }
        </script>
</head>

<body leftmargin=0 topmargin=0>
 <form name = "form1" method = "post">
	<input type = "hidden" name = "p_process" value = "">
    <input type = "hidden" name = "p_pageno" value = "">
    <input type = "hidden" name = "p_seq" value = "">
    <input type = "hidden" name = "p_userid" value = "">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="907" bgcolor="EFEFEA">
	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="830" height="120">
          <param name="movie" value="/images/gate/O_sub01.swf">
          <param name=wmode value=transparent>
          <param name="quality" value="high">
          <embed src="/images/gate/O_sub01.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="830" height="120"></embed></object></td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td><table width="949" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="25">&nbsp;</td>
          <td width="85" valign="top"><img src="/images/gate/O_title02.gif" width="85" height="80"></td>
          <td width="25">&nbsp;</td>
          <td width="775"><table width="775" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="100%" align="right" valign="middle">
								
                                        <p><select name = "p_select" class = "input">
                                            <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
                                            <option value = "name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
                                            <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
                 </select>&nbsp;</p>
</td>
                                    <td width="193" align="right" valign="middle">                                        <p><input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                name = "p_searchtext"  size = "25" maxlength = "30"></p>
</td>
                                    <td width="1%" align="right" valign="middle">&nbsp;</td>
                                    <td width="50" align="right" valign="middle">                                        <p><a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_search.gif" width="50" height="22"border="0" align="middle"></a></p>
</td>
                                    <td width="1%" align="right">&nbsp;</td>
                                    <td width="50" align="right" valign="middle">                                        <p><a href="javascript:insertPage()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_regist.gif" width="50" height="22" border="0" align="middle"></a></p>
</td>
              </tr>
                                <tr>
                                    <td height="8" align="right" width="100%" valign="middle"></td>
                                    <td width="193" height="8" align="right" valign="middle">&nbsp;</td>
                                    <td width="1%" height="8" align="right" valign="middle">&nbsp;</td>
                                    <td width="50" height="8" align="right" valign="middle">&nbsp;</td>
                                    <td width="1%" height="8" align="right">&nbsp;</td>
                                    <td width="50" height="8" align="right" valign="middle">&nbsp;</td>
              </tr>
            </table>
            <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
			  
                <td width="54" class=O_boardbgimg><span class=b_title>번호</span></td>
                <td class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="349" class=O_boardbgimg><span class=b_title>제목</span></td>
                <td class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="99" class=O_boardbgimg><span class=b_title>작성자</span></td>
                <td class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="139" class=O_boardbgimg><span class=b_title>작성일</span></td>
                <td class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="69" class=O_boardbgimg><span class=b_title>첨부파일</span></td>
                <td class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="60" class=O_boardbgimg><span class=b_title>조회</span></td>
              </tr>
<%if(list.size() != 0){%>
			   <%  for(i = 0; i < list.size(); i++) {
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
                   v_rowcount = dbox.getInt("d_rowcount");  
                                                                            
                   //------------------------------------------------------------------------------------------------------
                                                                    
                   if (!v_searchtext.equals("")) {
                   v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                   // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                   }
				   if ((i % 2) == 0) {
						v_classname = "b_list_W";
					 } else {
						v_classname = "b_list_G";
					}

				   %>
              
              <tr> 
			  
                <td width="54" align="center" class="<%=v_classname%>"><%= v_dispnum %></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="349" class="<%=v_classname%>" style="padding-left:10"><a href = "javascript:select('<%=v_seq%>', '<%=v_upfilecnt%>', '<%=v_userid%>')">&nbsp;&nbsp;<%= v_title %></a></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="99" align="center" class="<%=v_classname%>"><%= v_name %></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="139" align="center" class="<%=v_classname%>"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="69" align="center" class="<%=v_classname%>"><%if (v_upfilecnt != 0){
					%><img src="/images/gate/icon_file.gif" width="13" height="12"><%
				}else{%>
					
				<%}%></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="60" align="center" class="<%=v_classname%>"><%= v_readcnt %></td>
              </tr>
			  <tr> 
                <td colspan="16" align="center" class=b_guide></td>
              </tr>
			  <%}%>
<%}else{%>
           

                                                    <tr>
                                                        <td class=b_list_W colspan="13" align=center >
															등록 된 내용이 없습니다.		
														</td>
													</tr>

<%}%>              
              
              <tr> 
                <td colspan="11" class=b_list_bottom></td>
              </tr>
              <tr> 
                <td colspan="11" height=8></td>
              </tr>
              <tr align="center"> 
                <td height=20 colspan="11"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height = "30" valign = "bottom" align = "right" colspan = "3"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td colspan="11" height=20></td>
              </tr>
            </table></td>
          <td width="10">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
          <td width="25" height="50">&nbsp;</td>
          <td width="775" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3"><img src="/images/gate/O_copyright.gif" width="370" height="41"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td class=O_bgline></td>
  </tr>
</table>
</form>
</body>
</html>
