		<% String v_indexURLGLB = "";
		   String s_tem_grcodeGLB = box.getSession("tem_grcode");
		   if (s_tem_grcodeGLB.equals("N000002")) {
		   	v_indexURLGLB = "/indexGame.jsp";
		   } else {
		   	v_indexURLGLB = "/index" + s_tem_grcodeGLB + ".jsp";
		   }
		%>
		<a href="<%=v_indexURLGLB%>"><img src="/images/user/portal/homepage/type1/topmn_home.gif"></a>
<%if(box.getSession("userid").equals("")){%>
<%}else{%><a href="/servlet/controller.homepage.LoginServlet?p_process=gologout"><img src="/images/user/portal/homepage/type1/topmn_logout.gif"></a>
<%}%>
<!--a href="/servlet/controller.homepage.HomePageSiteMapServlet"><img src="/images/user/portal/homepage/type1/topmn_sitemap.gif"></a--><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=insertPage"><img src="/images/user/portal/homepage/type1/topmn_dearadmin.gif"></a><%if (s_tem_grcodeGLB.equals("N000002")) {%><a href="javascript:CommunityOpenPage();"><img src="/images/user/portal/homepage/type1/topmn_community.gif"></a><%}%>
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