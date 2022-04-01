<%@ page contentType="text/html; charset=euc-kr" %>

<%
	String gubun = request.getParameter("GUBUN");
	String key = request.getParameter("KEY");
	String key2 = request.getParameter("KEY2");
%>
						
						<TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr height="36">
								<td bgcolor="F5F5E7" style="BORDER-TOP:#EDEDED 1px solid;BORDER-BOTTOM:#EDEDED 1px SOLID">
								<form>
								<TABLE cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td width="152"><IMG src="/images/common/search_knowledge.gif" border="0"></td>
										<td style="padding-left:6"><script>NV_write("<select name='search' style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 80px;height:19px;font-size:9pt;' onchange='changeBoardType(this.value);'><option value='B' selected>지식충전소</option><option value='W' >웹진형</option><option value='I' >앨범형</option><option value='L'  >게시판형</option></select>", 10);</script></td>
										<td style="padding-left:6"><input type="text" style="width:188" class="input"></td>
										<td style="padding-left:6"><A HREF="#"><IMG src="/images/common/bt_p_search.gif" border="0"></A></td>
<%
	if(gubun.equals("2")) { 
%>
										<td style="padding-left:4"><A HREF="/portal/user/knowledge/km_write.jsp?cateId=<%=key%>"><IMG src="/images/common/bt_y_knowledge01.gif" border="0"></A></td>
										<td style="padding-left:4"><A HREF="/portal/user/knowhow/knowhow_write.jsp?cateId=<%=key2%>"><IMG src="/images/common/bt_y_knowledge02.gif" border="0"></A></td>
<%
	}
%>
									</tr>
								</TABLE>
								</td>
								</form>
							</tr>
						</TABLE>