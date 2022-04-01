<a href="/indexN000004.jsp"><img src="/images/user/game/homepage/type2/topmn_home.gif" width="38" height="37"></a><%if(box.getSession("userid").equals("")){%><%}else{%><a href="/servlet/controller.homepage.LoginServlet?p_process=gologout"><img src="/images/user/game/homepage/type2/topmn_logout.gif" width="75" height="37"></a><%}%><a href="/servlet/controller.homepage.HomePageSiteMapServlet"><img src="/images/user/game/homepage/type2/topmn_sitemap.gif" width="72" height="37"></a><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=insertPage"><img src="/images/user/game/homepage/type2/topmn_dearadmin.gif" width="85" height="37"></a><a href="javascript:CommunityOpenPage();"><img src="/images/user/game/homepage/type2/topmn_community.gif"></a><br>

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