


            <table width="640" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="22" class="scolor"><img src="/images/user/homepage/type1/sm_head.gif"></td>
                <td width="596" valign="middle" class="scolor">
<% if (!box.getSession("userid").equals("")) {%>
                    <a href="/"><img src="/images/user/homepage/type1/sm_home.gif" border="0"></a>
<% }                %>
                    <!--<a href="/servlet/controller.community.CommunityIndexServlet"><img src="/images/user/homepage/type1/sm_comm.gif" border="0"></a>-->
                    <%
                    	if (box.getSession("userid").equals("lee1")){
                    %>
                    <a href="/servlet/controller.homepage.MainServlet" onClick='CommunityOpenPage()'><img src="/images/user/homepage/type1/sm_comm.gif" border="0"></a>
                    <%
                    	}
                    %>
                    <a href="/servlet/controller.homepage.HomePageContactServlet"><img src="/images/user/homepage/type1/sm_contact.gif" border="0"></a>
					<a href="/servlet/controller.homepage.HomePageSiteMapServlet?p_process=listPage"><img src="/images/user/homepage/type1/sm_map.gif" border="0"></a>
<% if (!box.getSession("userid").equals("")) {%>
                    <a href="/servlet/controller.homepage.MemberInfoServlet?p_process=memberUpdatePage"><img src="/images/user/homepage/type1/sm_p.gif" border="0"></a>
<% }                %>
<% if (!box.getSession("userid").equals("")) {%>
                    <a href="javascript:goLoginout()"><img src="/images/user/homepage/type1/sm_logout.gif" border="0"></a>
<% }                %>
                </td>
                <td width="22" align="right" class="scolor"><img src="/images/user/homepage/type1/sm_tail.gif"></td>
              </tr>
            </table>



<SCRIPT LANGUAGE="JavaScript">
<!--
    // 로그 아웃 처리
    function goLoginout() {
        if (confirm("로그아웃 하시겠습니까?")) {
           document.location = "/servlet/controller.homepage.LoginServlet?p_process=logout";
        } else {
            return;
        }
    }

    // 커뮤니티 새창
    function CommunityOpenPage() {
        window.self.name = "winSelectViewCommunity";
        farwindow = window.open("", "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
        document.topdefaultForm.target = "openWinCommunity"
        document.topdefaultForm.action = "/servlet/controller.community.CommunityIndexServlet";
        document.topdefaultForm.submit();

        farwindow.window.focus();
        document.topdefaultForm.target = window.self.name;
    }    
//-->
</SCRIPT>





