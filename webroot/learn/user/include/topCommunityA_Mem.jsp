<%
    ArrayList toplistUser       = (ArrayList)request.getAttribute("listUser");
System.out.println("...................  ::::"+box.getString("p_cmuno"));

%>
               <!-- 정보창 테이블 -->
               <table width="162" border="0" cellspacing="0" cellpadding="0">
<% if(toplistUser.size()<1){%>
                <tr><td height="50" align="center" valign="top" background="/images/community/info_bg.jpg" class="tbl_bc"><%=s_username%>  안녕하세요. </td></tr>
                <tr><td height="10">&nbsp;</td></tr>
<% } else {
 
 
 %>
                <tr><td height="50" align="center" valign="top" background="/images/community/info_bg.jpg" class="tbl_bc"><%=s_username%>  안녕하세요. </td></tr>
                <tr><td height="10">&nbsp;</td></tr>
<%}%>
               </table>
               <!-- 나의커뮤니티 -->
               <table width="162" border="0" cellspacing="0" cellpadding="0">
                <tr><td><img src="/images/community/my_text.gif"></td></tr>
                <tr>
                 <td><select name="p_static_cmuno" onchange="javascript:uf_moveMyCommunity();">
                      <option >:내가 가입한 커뮤니티: </option>
                      <%=(new com.credu.community.CommunityIndexBean()).selectMyCuminity(s_userid,box.getString("p_static_cmuno"), s_grtype))%>
                    </select>
<script language="JavaScript" type="text/JavaScript">
//개인터뮤니티 홈으로 이동
function uf_moveMyCommunity() {

    document.form1.action = "/servlet/controller.community.CommunityIndexServlet?p_cmuno="+document.form1.p_static_cmuno.value+"&p_static_cmuno="+document.form1.p_static_cmuno.value;
    document.form1.p_process.value = "selectmyindex";

    document.form1.submit();
}

//-->
</script>
                 </td>
                </tr>
                <tr><td height="5"><img src="/images/community/my_line.gif"></td></tr>
                <tr><td height="10"></td></tr>
               </table>
               <!-- 버튼테이블 -->
               <table width="162" border="0" cellspacing="0" cellpadding="0">
                <tr><td><a href="/servlet/controller.community.CommunityCreateServlet?p_process=insertPage"><img src="/images/community/ban_make.gif" border="0"></a></td></tr>
                <tr><td height="5"></td></tr>
                <tr><td><a href="/servlet/controller.community.CommunityQnAServlet?p_process=selectlist&p_faq_type=CMUQNA"><img src="/images/community/ban_qna.gif" border="0"></a></td></tr>
                <tr><td height="5"></td></tr>
                <!--tr><td><a href="#"><img src="/images/community/ban_help.gif" border="0"></a></td></tr>
                <tr><td height="5"></td></tr-->
                <tr><td><a href="/servlet/controller.community.CommunityFrPoliceServlet?p_process=movePage"><img src="/images/community/ban_singo.gif" border="0"></a></td></tr>
               </table>

