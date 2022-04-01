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
    
    CommunityFrBoardBean bean = new CommunityFrBoardBean();
    List replyList = (List)request.getAttribute("selectList");
    
    String v_userid     = "";
    String v_usernm     = "";
    String v_content    = "";
    String v_reg_date   = "";
    
    int    v_rplno      =  0;
   
%>
            <table class="answer bo_bn">
            <colgroup><col width="126px" /><col /></colgroup>
            <%
            if( replyList != null && replyList.size() > 0 ) {
                for(int i = 0 ; i < replyList.size() ; i++) {
                	DataBox dbox = (DataBox)replyList.get(i);
                	v_userid = dbox.getString("d_userid");
                	v_usernm = dbox.getString("d_name");
                	v_content = dbox.getString("d_content");
                	v_reg_date = FormatDate.getFormatDate(dbox.getString("d_modifier_dte"), "yyyy.MM.dd HH:mm:ss");
                	
                	v_rplno    = dbox.getInt("d_rplno");
            %>
            <tr>
                <th class="list"><%=v_usernm %>(<%=v_userid %>)</th>
                <td><%=v_content %><span class="data">(<%=v_reg_date %>)</span> 
                <% if(s_userid.equals(v_userid)) { %>
                <a href="javascript:uf_deleteMemoOK('<%=v_rplno %>');"><img src="/images/portal/btn/btn_s_close.gif" class="va_m" alt="x" /></a>
                <% } %>
                </td>
            </tr>
            <%}} else { %>
            <tr></tr>
            <%} %>
            </table>