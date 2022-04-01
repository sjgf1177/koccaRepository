<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String s_userid = (String)request.getSession().getAttribute("userid");
    
    int    v_pageno = 0;
    
    DataBox fileBox = (DataBox) request.getAttribute("fileBox");
    
    Vector i_realfileVector = (Vector)fileBox.getObject("d_realfileVector");
    Vector i_savefileVector = (Vector)fileBox.getObject("d_savefileVector");
    Vector i_filenoVector   = (Vector)fileBox.getObject("d_filenoVector");
    
%>
            <% {
                int i_fileCnt = i_realfileVector != null ? i_realfileVector.size() : 0 ; %>

            <%  if( i_realfileVector != null && i_realfileVector.size() > 0) { %>

            <%      for(int i_i = 0; i_i < i_realfileVector.size(); i_i++) {      //     Ã·ºÎÆÄÀÏ °¹¼ö¿¡ µû¶ó
            
                        String v_realfile = (String)i_realfileVector.elementAt(i_i);
                        String v_savefile = (String)i_savefileVector.elementAt(i_i);
                        String v_fileno   = (String)i_filenoVector.elementAt(i_i);

                        if(v_realfile != null && !v_realfile.equals("")) {  %>
                    <img src="/images/portal/ico/ico_file.gif" alt="file" />
                    <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><span><%= v_realfile%></span></a>
                    <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>><%=i_realfileVector.size()-1 == i_i ? "" : "<br/>" %>
            <%              }   
                        }  %>
            <input type = "hidden" name="p_upfilecnt"  value = "<%=i_fileCnt%>">
 <%        } }%>