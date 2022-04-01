<%--  
	제  목 : HelpDesk 페이지의 좌측 메뉴(사용자)
	파일명 : left_helpdesk.jsp
	작성일 : 2004.12.
	회  사 :(주)크레듀
	작성자 : 이연정
	설  명 : HelpDesk 좌측 메뉴 Include
		   
	관련 클래스      : 
	Input Parameter  : 
	분기 Page        : 없음
	Output Parameter : 없음
--%> 


<%
		String homeExeSrcFile = request.getRequestURI();

		//url에서 대표가 되는 URL의 일부 Tree 여부
		String[] menuUrlKey = {"/zu_HomePageFaq_L.jsp", "/zu_HomePageQna_L.jsp","/zu_HomePageSoftWare_L.jsp","/zu_HomePageBoard_L.jsp","/zu_HomePageMileage_L","/zu_HomePageHelp_L","/zu_HomePageWay_L"};

		// 링크될 url                                    
		String[] menuSrc = {"/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList", "/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList","/servlet/controller.homepage.HomePageSoftWareServlet?p_process=selectList","/servlet/controller.homepage.HomePageBoardServlet?p_process=selectList","/servlet/controller.homepage.HomePageMileageServlet?p_process=selectList","/servlet/controller.homepage.HomePageHelpServlet?p_process=selectList","/servlet/controller.homepage.HomePageWayServlet?p_process=selectList"};
		
		// 메뉴 이미지명
		String[] menuImgName = {"Image23", "Image24","Image25","Image26","Image27","Image32","Image33"};

		
		// 메뉴 이미지          
		String[] menuImg = {"/images/user/support/lm_01", "/images/user/support/lm_02","/images/user/support/lm_03","/images/user/support/lm_04","/images/user/support/lm_05","/images/user/support/lm_06","/images/user/support/lm_07"};
		

		int menuCo = 7;
%>

		<img src="/images/user/support/type1/lm_tit.gif" width="152" height="63">
<%
		int iMenu = homeExeSrcFile.indexOf(menuUrlKey[0]);


		for(int loop = 0; loop < menuCo; loop++){
			int iMenuBg = homeExeSrcFile.indexOf(menuUrlKey[loop]);

			//-----------------------------------------------------//
			
%>
        <a href="<%=menuSrc[loop]%>" target="_self">
		  <IMG src="<%=menuImg[loop]%>.gif"  name="<%=menuImgName[loop]%>" onMouseOver="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>_on.gif'" onMouseOut="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>.gif'" border="0"></a>
<%
			
}
%>
      
	







