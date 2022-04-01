
<%
		String homeExeSrcFile = request.getRequestURI();

		//url에서 대표가 되는 URL의 일부 Tree 여부
		//String[] menuUrlKey = {"/zu_EducationSubject_L",  "/zu_StudyHistoryTotal_L", "/zu_PersonalInform_U", "/zu_EducationSubject_L", "/zu_ETestPaper_L", "/zu_ProposeMember_L", "/zu_ChangeSeq_L", "/my_list","/my_knowhow", "/my_scrap", "/my_service"};
		String[] menuUrlKey = {"/zu_Subject_L", "/zu_ProposeCancel_L", "/zu_EducationSubject_L", "/zu_StudyHistoryTotal_L", "/zu_ETestPaper_L2"};

		// 링크될 url
		String[] menuSrc = {
		"/servlet/controller.study.SalesClassServlet?p_process=SubjectList",              //0
		"/servlet/controller.study.SalesClassServlet?p_process=ProposeCancelPage",             //1
		"/servlet/controller.study.SalesClassServlet?p_process=EducationSubjectPage",           //2
		"/servlet/controller.study.SalesClassServlet?p_process=StudyHistoryList",              //4
		"/servlet/controller.study.SalesClassServlet?p_process=ETestUserListPage",              //5
		};

		// 메뉴 이미지명
		String[] menuImgName = {"img11", "img22", "img33", "img44", "img55"};
		
		
		// 메뉴 이미지
		String[] menuImg = {"/images/sales/m01", "/images/sales/m02", "/images/sales/m03", "/images/sales/m04", "/images/sales/m05"};
		
		int menuCo = 5;	
		
		
%>					

<table width="163" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/sales/menu_top.gif" border="0"></td>
  </tr>
  <tr>
    <td height=12></td>
  </tr>
  
  <%
		int iMenu = homeExeSrcFile.indexOf(menuUrlKey[0]);
		

		for(int loop = 0; loop < menuCo; loop++){
			int iMenuBg = homeExeSrcFile.indexOf(menuUrlKey[loop]);

			//-------------menu의 1,5,6,7에 대하여 처리------ //
			////-- 페이지 안에 서브 탭페이지들에 대하여 동일한 이미지 highlight
			if(loop == 0) {
				if(homeExeSrcFile.endsWith("zu_Subject_L.jsp") || homeExeSrcFile.endsWith("zu_SubjectOFF_L.jsp")) 
				{
				  iMenuBg = 0;
				}
			}
			else if(loop == 3) {
				if(homeExeSrcFile.endsWith("zu_StudyHistoryTotal_L.jsp") || homeExeSrcFile.endsWith("zu_StudyHistory_L.jsp")) 
				{
				  iMenuBg = 0;
				}
			}
			//else if(loop == 6) {
			//	if(homeExeSrcFile.endsWith("zu_familyapp_i.jsp") || homeExeSrcFile.endsWith("zu_familyapp_r.jsp") || homeExeSrcFile.endsWith("zu_familyapp_u.jsp")|| homeExeSrcFile.endsWith("zu_familyclass_l.jsp") || homeExeSrcFile.endsWith("zu_familyclass_r.jsp")) 
			//	{
			//	iMenuBg = 0; 
			//	}
			//}
			//else if(loop == 7) {
			//	if(homeExeSrcFile.endsWith("zu_myqna_l.jsp")) {
			//	iMenuBg = 0;
			//	}
			//}
			
			//-----------------------------------------------------//
			if (iMenuBg > -1) {		

%>

  <tr>
    <td><a href="<%=menuSrc[loop]%>"><IMG src="<%=menuImg[loop]%>_on.gif" border="0" name="<%=menuImgName[loop]%>"></a></td>
  </tr>
<%
				iMenuBg = -1;
			 }else{
%>
  <tr>
    <td>
      <a href="<%=menuSrc[loop]%>">
      <IMG src="<%=menuImg[loop]%>.gif" border="0" name="<%=menuImgName[loop]%>" onMouseOver="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>_on.gif'" onMouseOut="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>.gif'"></a></td>
  </tr>
<%			 
			 }
			   
			}
%>
  
  
</table>



