<%
//**********************************************************
//  1. 제      목: 홈페이지 Q&A 꼬릿말 리스트
//  2. 프로그램명: zu_HomePageQna_C.jsp
//  3. 개      요: 홈페이지 Q&A 꼬릿말 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 6. 29
//  7. 수      정: 이연정 2005. 6. 29
//***********************************************************
%>
<%
ArrayList list = (ArrayList)request.getAttribute("selecCommentList");%>

                  <!-- 댓글달기table -->
				  <form name = "form2" method = "post">
                  <table width="675" border="0" cellspacing="0" cellpadding="0">

				  <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
				  <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
				  <input type = "hidden" name = "p_select"      value = "<%= v_select %>">
				  <input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
				  <input type = "hidden" name = "p_process"     value = "">
				  <input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
				  <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
				  <input type = "hidden" name = "p_commentseq" value ="">
				  <input type = "hidden" name = "p_categorycd" value ="">
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>

<%if(list.size() != 0){%>
<% 
			for(int i = 0; i < list.size(); i++) {
                   dbox = (DataBox)list.get(i);  
                   v_inuserid      = dbox.getString("d_inuserid");
                   String c_name   = dbox.getString("d_name");
                   v_indate        = dbox.getString("d_cdate");
                   String v_commentqna    = dbox.getString("d_commentqna");
				   v_commentseq    = dbox.getInt("d_commentseq");
%>

                    <tr> 
                      <td width="90" class="tbl_br"><%=v_inuserid%></td>
                      <td width="8" class="tbl_bc"><img src="/images/user/support/c_vline.gif"></td>
                      <td width="469" class="tbl_gleft"><%=v_commentqna%></td>
                      <td width="20" class="tbl_gleft">
                      <%
                      	if(s_userid.equals(v_inuserid)){
                      %>
					  						<a href="javascript:deleteComment('<%=v_commentseq%>')"><img src="/images/user/button/btn_del_s.gif"></a></td>
					  					<%
					  						}
					  					%>
                      <td width="6"><img src="/images/user/support/c_vline.gif"></td>
                      <td width="83" class="tbl_gleft"><%=FormatDate.getFormatDate(v_indate, "yyyy/MM/dd")%></td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>
                      <%}%>
					<%}else{%>

                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>
					<%}%>
                    <tr> 
                      <td height="5" colspan="6">
					    <table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="588"><input name="commentqna" type="text" class="input2" size="96" maxlength="90"></td>
                            <td width="87" align="center"><a href="javascript:insertcomment()"><img src="/images/user/button/btn_wr.gif" width="65" height="21"></a></td>
                          </tr>
                        </table>
					  </td>
                    </tr>
                  </table>
				  </form>