<%
//**********************************************************
//  1. 제      목: 지식공유 꼬릿말
//  2. 프로그램명 : gu_KnowAddList.jsp
//  3. 개      요: 지식공유 꼬릿말 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정: 
//***********************************************************
%>

<%
ArrayList list = (ArrayList)request.getAttribute("selecCommentList");%>

                  <!-- 댓글달기 table -->
				  <form name = "form2" method = "post">
				  <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
				  <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
				  <input type = "hidden" name = "p_select"      value = "<%= v_select %>">
				  <input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
				  <input type = "hidden" name = "p_process"     value = "">
				  <input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
				  <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
				  <input type = "hidden" name = "p_commentseq" value ="">
				  <input type = "hidden" name = "p_categorycd" value ="">

				<table width="720" border="0" cellspacing="0" cellpadding="0">
				    <tr> 
                      <td height="5" colspan="6">
					    <table width="720" border="0" cellspacing="0" cellpadding="0">
						  <tr> 
							<td height="12" colspan="3"></td>
						  </tr>
						  <tr>
                            <td><input name="commentqna" type="text" class="input2" size="105"></td>
							<td width="9">&nbsp;</td>
                            <td width="58" align="center"><a href="javascript:insertcomment()"><img src="/images/user/game/button/btn_re_write.gif"></a></td>
                          </tr>
						  <tr> 
							<td height="6" colspan="3">&nbsp;</td>
						  </tr>
                        </table>

					  </td>
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
                      <td width="65" class="tbl_gleft02"><%=c_name%></td>
                      <td width="1"><img src="/images/user/game/mystudy/bg_dotline_1.gif"></td>
                      <td width="617" class="tbl_gleft03"><%=v_commentqna%></td>
                      <td width="37" class="tbl_gleft">
                      <%
                      	if(s_userid.equals(v_inuserid)){
                      %>
					  						<a href="javascript:deleteComment('<%=v_commentseq%>')"><img src="/images/user/game/mystudy/icon_x.gif"></a></td>
					  					<%
					  						}
					  					%>
                    </tr>
                    <tr>
						<td background="/images/user/game/mystudy/bg_dotline_2.gif" height="1" colspan="4"></td>
					</tr>

                      <%}%>
					<%}else{%>
				    <tr> 
					  <td height="6" colspan="3">&nbsp;</td>
				    </tr>
					<%}%>
                    
                  </table>
				  </form>