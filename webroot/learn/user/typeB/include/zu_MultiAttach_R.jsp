<%@ page contentType = "text/html;charset=euc-kr" %>

<%
int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ;

if( i_realfileVector != null && i_realfileVector.size() > 0) {
%>
											<tr>
                                                <th>Ã·ºÎÆÄÀÏ</th>
                                                <td colspan="3">
<%
	for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     Ã·ºÎÆÄÀÏ °¹¼ö¿¡ µû¶ó
		String v_realfile = (String)i_realfileVector.elementAt(i_i);
		String v_savefile = (String)i_savefileVector.elementAt(i_i);      
		
		if(v_realfile != null && !v_realfile.equals("")) {
%>
													<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a>
													<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%> /><br/>
													
<%
		}   
	}
%>
												</td>
											</tr>
<%
}
%>
<input type = "hidden" name="p_upfilecnt"  value = "<%=i_fileCnt%>" />