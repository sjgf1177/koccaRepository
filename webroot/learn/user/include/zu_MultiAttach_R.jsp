<%@ page contentType = "text/html;charset=euc-kr" %>
<%
    /**************************************************************************
     * 이 파일을 include할 경우, include하는 파일에서 다음 변수들을 선언/정의해야 한다.
     *Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
     *Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
     *Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
     *String i_boardStyle        = "";                                          // 보드 스타일
     *
     **************************************************************************/
%> 

<%{
    
%>

            <% {
                int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ; %>

            <%  if( i_realfileVector != null && i_realfileVector.size() > 0) { %>
            <%if( box.getSession("tem_grcode").equals("N000001")) { %>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            	<%if(i_realfileVector != null && !i_realfileVector.equals("") ){ %>
            		<tr>
						<td class="tit_table" width="150px">첨부파일</td>
						<td colspan="7" class="ct_tit_table_lright">
							 <%      for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
            
                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
                        String v_savefile = (String)i_savefileVector.elementAt(i_i);      

                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                        
	                    <img src="/images/portal/ico/ico_file.gif" alt="file" class="mg_r6 va_t" />
	                    <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a>
	                    <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
	                    
	                    <%              }   
                        }  %>
						</td>
					  </tr>
					  <tr>
			          	<td height="1" colspan="8" bgcolor="CACACA"></td>
			          </tr>
			          <tr>
			          	<td height="4" colspan="8" bgcolor="F7F7F7"></td>
			          </tr>
					  <%} %>
					  </table>
            <%}else{ %>
            <table class="<%=i_boardStyle %> bo_tn">
            <colgroup><col width="65px" /><col width="625px" /></colgroup>
            <tr class="bgn">
                <th class="th">첨부파일</th>
                <td colspan="7" class="td pd_ln">
            <%      for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
            
                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
                        String v_savefile = (String)i_savefileVector.elementAt(i_i);      

                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                    <p>
                    <img src="/images/portal/ico/ico_file.gif" alt="file" class="mg_r6 va_t" />
                    <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a>
                    <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
                    </p>
            <%              }   
                        }  %>
            
                </td>
            </tr>
            </table>
            <%} %>
            <input type = "hidden" name="p_upfilecnt"  value = "<%=i_fileCnt%>">
 <%        } } }%>
 