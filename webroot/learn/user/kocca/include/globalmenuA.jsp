                  <!-- global? -->
                  <table width="225" border="0" cellspacing="0" cellpadding="0" >
                    <tr align="right">
				<%if( !box.getSession("userid").equals("")){%>
                      <td width="88" align="right"><a href="/servlet/controller.homepage.KLoginServlet?p_process=gologout"><img src="/images/user/kocca/homepage/type1/sm_01.gif" border="0"></a></td>
				<%}%>
                      <td width="21" align="right"><a href="/servlet/controller.homepage.KHomePageFAQServlet?p_process=selectList"><img src="/images/user/kocca/homepage/type1/sm_02.gif" border="0"></a></td>
                      <td width="54" align="right"><a href="#"  onClick='CommunityOpenPage()'><img src="/images/user/kocca/homepage/type1/sm_03.gif" border="0"></a></td>
                      <td width="62" align="right"><a href="/servlet/controller.homepage.KHomeSiteMapServlet"><img src="/images/user/kocca/homepage/type1/sm_04.gif" border="0"></a></td>
                    </tr>
                  </table> 
<SCRIPT LANGUAGE="JavaScript">
<!--
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