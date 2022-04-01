<%
		String homeExeSrcFile = request.getRequestURI();

		if(homeExeSrcFile.equals("/portal/user/knowledge/km_view.jsp")) homeExeSrcFile = "/my_list";
		else if(homeExeSrcFile.equals("/portal/user/knowhow/knowhow_view.jsp")) homeExeSrcFile = "/my_knowhow";
		
			
		//url에서 대표가 되는 URL의 일부 Tree 여부
		//String[] menuUrlKey = {"/zu_EducationSubject_L",  "/zu_StudyHistoryTotal_L", "/zu_PersonalInform_U", "/my_mileage", "/zu_ETestPaper_L", "/zu_ProposeMember_L", "/zu_ChangeSeq_L", "/my_list","/my_knowhow", "/my_scrap", "/my_service"};
		String[] menuUrlKey = {"/zu_Subject_L", "/zu_ProposeCancel_L", "/zu_SubjSearch_L", "/zu_SubjResearch_L", "/zu_SubjRecom_L", "/zu_EducationYearlySchedule_L", "/zu_EducationMonthlySchedule_L"};

		// 링크될 url
		//String[] menuSrc = {"/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage", "/servlet/controller.study.MyClassServlet?p_process=ProposeCancelPage", "/servlet/controller.study.MyClassServlet?p_process=StudyHistoryList", "/servlet/controller.study.MyClassServlet?p_process=PersonalInformUpdatePage", "/learn/user/study/my_mileage.jsp", "/servlet/controller.etest.ETestUserServlet?p_process=ETestUserListPage", "/servlet/controller.study.MyClassServlet?p_process=ProposeMemberSearch", "/servlet/controller.study.ChangeSeqServlet?p_process=ListPage", "/learn/user/study/my_list.jsp", "/learn/user/study/my_knowhow_list.jsp", "/learn/user/study/my_scrap_list.jsp", "/learn/user/study/my_service_list.jsp"};		
		String[] menuSrc = {
		"/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList",               //1
		"/servlet/controller.study.MyClassServlet?p_process=ProposeCancelPage",
		"/servlet/controller.propose.MainSubjSearchServlet?p_process=SubjSearchPre",            //2
		"/servlet/controller.research.SulmunSubjResultServlet?p_process=SulmunEachResultPage",  //7
		"/servlet/controller.propose.MainSubjSearchServlet?p_process=SubjRecomList",            //3
	"/servlet/controller.propose.ProposeCourseServlet?p_process=EducationYearlySchedule",   //4
		"/servlet/controller.propose.ProposeCourseServlet?p_process=EducationMonthlySchedule",  //5
		};

		// 메뉴 이미지명
		//String[] menuImgName = {"img101", "img102", "img103", "img104", "img105", "img106", "img107", "img108", "img109", "img110", "img111"};
		String[] menuImgName = {"img101", "img102", "img103", "img107", "img104", "img105", "img106"};
		
		
		// 메뉴 이미지
		//String[] menuImg = {"/images/common/left_myhome_01", "/images/common/left_myhome_02", "/images/common/left_myhome_03", "/images/common/left_myhome_04", "/images/common/left_myhome_05", "/images/common/left_myhome_06", "/images/common/left_myhome_07", "/images/common/left_myhome_08", "/images/common/left_myhome_09", "/images/common/left_myhome_10", "/images/common/left_myhome_11"};		
		
		String[] menuImg = {"/images/common/left_course_01", "/images/common/left_course_10", "/images/common/left_course_02", "/images/common/left_course_07", "/images/common/left_course_03", "/images/common/left_course_04", "/images/common/left_course_05"};
		

		int menuCo = 7;	
		
		
%>					
                           <TABLE cellpadding="0" cellspacing="0" border="0" height="100%">
							<tr height="74">
								<td style="padding-left:10px"><IMG src="/images/common/left_title_course.gif" border="0"></td>
							</tr>
							<tr height="10">
								<td></td>
							</tr>
							<tr valign="top">
								<td style="padding-left:25px">
									<TABLE cellpadding="0" cellspacing="0" border="0">
<%
		int iMenu = homeExeSrcFile.indexOf(menuUrlKey[0]);
		

		for(int loop = 0; loop < menuCo; loop++){
			int iMenuBg = homeExeSrcFile.indexOf(menuUrlKey[loop]);

			//-------------menu의 1,5,6,7에 대하여 처리------ //
			////-- 페이지 안에 서브 탭페이지들에 대하여 동일한 이미지 highlight
			if(loop == 0) {
				if(homeExeSrcFile.endsWith("zu_ContentRearch_L.jsp")) 
				{
				  iMenuBg = 0;
				}
			}else if(loop == 1) {
				if(homeExeSrcFile.endsWith("zu_StudyHistoryOn_L.jsp") || homeExeSrcFile.endsWith("zu_StudyHistoryOff_L.jsp") || homeExeSrcFile.endsWith("zu_StudyHistoryLang_L.jsp")) 
				{
				  iMenuBg = 0;
				}
			}else if(loop == 5) {
				if(homeExeSrcFile.endsWith("zu_EduTargetSearch_L.jsp") || 					homeExeSrcFile.endsWith("zu_CheifApproval_L.jsp") || homeExeSrcFile.endsWith("zu_TeamExamHistory_L.jsp") ||
				homeExeSrcFile.endsWith("zu_TeamScoreSearch_L.jsp")	) {
				iMenuBg = 0;
				}
			}
			else if(loop == 6) {
				if(homeExeSrcFile.endsWith("zu_ChangeSeq_I.jsp") || homeExeSrcFile.endsWith("zu_ChangeSeq_U.jsp") || homeExeSrcFile.endsWith("zu_ChangeSeq_R.jsp")) {
				iMenuBg = 0;
				}
			}
			else if(loop == 7) {
				if(homeExeSrcFile.endsWith("zu_myqna_l.jsp")) {
				iMenuBg = 0;
				}
			}
			
			//-----------------------------------------------------//
			if (iMenuBg > -1) {		

%>
										<tr>
											<td>
											<A HREF="<%=menuSrc[loop]%>">
											<img src="<%=menuImg[loop]%>_r.gif" border="0" name="<%=menuImgName[loop]%>"></A></td>
										</tr>
<%
				iMenuBg = -1;
			 }else{
%>
                                        <tr>
											<td>
											<A HREF="<%=menuSrc[loop]%>">
											<img src="<%=menuImg[loop]%>.gif" border="0" name="<%=menuImgName[loop]%>" onMouseOver="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>_r.gif'" onMouseOut="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>.gif'"></A></td>
										</tr>
<%			 
			 }
			   if(loop == 4 || loop == 6 ){
%>            
			  							<tr height="15">
			  								<td></td>
			  							</tr>
<%            
			  }
			}
%>
									</TABLE>
								</td>
							</tr>
						</TABLE>