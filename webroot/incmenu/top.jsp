
<%
	// Portal 초기화
	//KnowledgeSystem.getInstance().initSystem();
	//HelperSystem.getInstance().initSystem();

    String RootPath = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf(":")); 
      //String ext      = RootPath.substring(RootPath.lastIndexOf(".")+1, RootPath.length())
    String path1    = "";
    String path2    = "";

     // out.println(RootPath +"<br>");

     if("cafe".equals(request.getRequestURL().substring(7, request.getRequestURL().indexOf(".")))){
          path1 = RootPath.replaceAll("cafe", "www");
          path2 = RootPath;
     } else {
          path1 = RootPath;
          //path2 = RootPath.replaceAll("www", "cafe");
     }
     
     path1 = "http://10.40.37.102:9999";
	

	String sUserId		= null;
	sUserId = (String)session.getAttribute("userid");
	String sCono		= "";
	String sUserGubun	= "";
	String sGAdmin		= "";
	String sLoginCheck	= "0";
	String sAdminGubun	= "0";
	

	if (sUserId != null) {
		sCono		= (String)session.getAttribute("cono");
		sUserGubun	= (String)session.getAttribute("usergubun");
		sGAdmin		= (String)session.getAttribute("gadmin");
		
		if (sGAdmin.startsWith("A") || sGAdmin.startsWith("H"))  {
			sAdminGubun = "1";
		}

		sLoginCheck = "1";
	}
%>
<script>
var path1 = "<%=path1%>";

</script>

<script src="/script/gomenu.js" language="JavaScript"></SCRIPT>
<script language="JavaScript">

	function goSearchMain(){
<%
		if (session.getAttribute("userid") != null || session.getAttribute("usergubun") != null) {
%>	
			setCookie();
			location.href="/search/search_main?group=<%=sUserGubun%>&logChk=<%=sLoginCheck%>&empNo=<%=sCono%>&admin=<%=sAdminGubun%>";

			/*var fm = document.SEARCHFORM;		
			fm.action = "/search/search_main";
			fm.submit();*/
<%		}
		else {
%>
			alert('로그인 후 이용해 주세요');
<%		}
%>
	}


	function setCookie() {
		var search = "userid=";
		var cookie_value;
		var todayDate = new Date();
		todayDate.setTime(todayDate.getTime() + (1000 * 10 * 180));
		offset = document.cookie.indexOf(search);
		document.cookie = search + "'<%=sCono%>'; path=/search; expires="+ todayDate.toGMTString();
	}


	function changeBoardType() {

	}

	function usmail() {

<%if(session.getAttribute("userid") != null){%>

		document.formcon.action = "/servlet/controller.homepage.HomePageContactServlet";
		document.formcon.p_process.value = "insertPage";
		document.formcon.submit();
<%	}else{%>
		document.formcon.action = "/servlet/controller.homepage.HomePageContactServlet";
		document.formcon.p_process.value = "userid";
		document.formcon.submit();
<%}%>
	}

	function whenLogout() {
		if (confirm("로그아웃 하시겠습니까?")) {
			document.formcon.action = "/community/home/member/LoginAct.jsp?LOGIN_TYPE=LOGOUT";
			document.formcon.submit();
		}
		
	}
</SCRIPT>
						<FORM METHOD=POST name="formcon">
													<input type = "hidden" name = "p_process"  value = "">
										
						</form>

						<TABLE cellpadding="0" cellspacing="0" border="0" width="940">
							<tr>
								<td style="padding-top:3px;padding-left:10px"><A HREF="<%=path1%>/" onFocus="this.blur();"><IMG src="/images/common/hkmotor.gif" border="0"></A></td>
								<td align="right" valign="top">
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td><IMG src="/images/common/top_menu_left.gif" border="0"></td>
											<td><A HREF="<%=path1%>/"><IMG src="/images/common/top_menu_01.gif" border="0"></A></td>
											<td><IMG src="/images/common/top_menu_md.gif" border="0"></td>
											<td><A HREF="javascript:usmail()"><IMG src="/images/common/top_menu_03.gif" border="0"></A></td>
											<td><IMG src="/images/common/top_menu_md.gif" border="0"></td>
											<td><A HREF="<%=path1%>/portal/user/sitemap/sitemap.jsp"><IMG src="/images/common/top_menu_04.gif" border="0"></A></td>
											<!-- 로그인 안되었을땐 안보임 td2개 -->
<%
											if (session.getAttribute("userid") != null || session.getAttribute("usergubun") != null) {
%>											
											<td><IMG src="/images/common/top_menu_md.gif" border="0"></td>
											<td><A HREF="javascript:whenLogout();"><IMG src="/images/common/top_menu_05.gif" border="0"></A></td>
<%
											}
%>											
											<!-- ------------------------------- -->

											<!--td><A HREF="javascript:goSearchMain()"><IMG src="/images/common/top_menu_06.gif" border="0" name="img71" onMouseOver="document.all.img71.src='/images/common/top_menu_06_r.gif'" onMouseOut="document.all.img71.src='/images/common/top_menu_06.gif'"></A></td>

											<td><A HREF="<%=path1%>/portal/user/hrd/hrd_01_01.jsp"><IMG src="/images/common/top_menu_07.gif" border="0" name="img72" onMouseOver="document.all.img72.src='/images/common/top_menu_07_r.gif'" onMouseOut="document.all.img72.src='/images/common/top_menu_07.gif'"></A></td>
											<td><IMG src="/images/common/top_menu_right.gif" border="0"></td-->
										</tr>
									</TABLE>
								</td>
							</tr>
						</TABLE>
