<%
    /**************************************************************************
     * 이 파일을 include할 경우, include하는 파일에서 다음 변수들을 선언/정의해야 한다.
     *Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
     *Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
     *Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
     *
     **************************************************************************/
%> 

<%{ int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ; %>

            <%      
                    if( i_realfileVector != null ) { 
                        for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     첨부파일 갯수에 따라
            
                            String v_realfile = (String)i_realfileVector.elementAt(i_i);
                            String v_savefile = (String)i_savefileVector.elementAt(i_i);      

                            if(v_realfile != null && !v_realfile.equals("")) {  %>
                                &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a><br>
                                <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
            <%              }   
                        }
                    }
            %>


 <input type = "hidden" name="p_upfilecnt"  value = "<%=i_fileCnt%>">
 
 <% } %>