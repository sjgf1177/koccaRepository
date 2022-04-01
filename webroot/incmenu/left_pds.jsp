<%--  
	제  목 : HelpDesk 페이지의 좌측 메뉴(사용자)
	파일명 : left_helpdesk.jsp
	작성일 : 2004.12.
	회  사 :(주)다음소프트 
	작성자 : 이도경
	설  명 : HelpDesk 좌측 메뉴 Include
		   
	관련 클래스      : 
	Input Parameter  : 
	분기 Page        : 없음
	Output Parameter : 없음
--%> 

<%
	String sPageUri = request.getRequestURI();
System.out.println("helpdesk==> " + sPageUri);	
	int iIdx		= -1;
	String sPrefix	= "pds";
	String imgSrc	= "";
	
	//FAQ = zu_HomePageFaq_XX
	//QNA = zu_HomePageQna_XX
	//Admin = zu_HomePageAdmin_XX
	//Manual = 온라인 매뉴얼 시작 및 교육과정 = manual_01
	//			지식충전소 = manual_02
	//			골드자료실 = manual_03
	//			커뮤니티	= manual_04_XX
	//			포인트제도	= manual_05
	
%>



 					
						<TABLE cellpadding="0" cellspacing="0" border="0" height="100%">
							<tr height="74">
								<td style="padding-left:10px"><IMG src="/images/common/left_title_pds.gif" border="0"></td>
							</tr>
							<tr height="10">
								<td></td>
							</tr>
							<tr valign="top">
								<td style="padding-left:25px">
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td><A HREF="/servlet/controller.tutor.TutorFormServlet"><img src="/images/common/left_<%= (imgSrc=sPageUri.indexOf("zu_TutorForm_L") > 0  ? sPrefix+"_01_r.gif" : sPrefix+"_01.gif")%>" border="0" name="img101" onMouseOver="document.all.img101.src='/images/common/left_pds_01_r.gif'" onMouseOut="javascript:this.src='/images/common/left_<%=imgSrc%>'"></A></td>
										</tr>
									 	<tr>
											<td><A HREF="/servlet/controller.tutor.TutorSwServlet"><img src="/images/common/left_<%= (imgSrc=sPageUri.indexOf("zu_TutorSw_L") > 0  ? sPrefix+"_02_r.gif" : sPrefix+"_02.gif")%>" border="0" name="img102" onMouseOver="document.all.img102.src='/images/common/left_pds_02_r.gif'" onMouseOut="javascript:this.src='/images/common/left_<%=imgSrc%>'"></A></td>
										</tr>
									 	<tr>
											<td><A HREF="/servlet/controller.study.DicSubjStudyServlet?p_process=selectTotalListPre"><img src="/images/common/left_<%= (imgSrc=sPageUri.indexOf("edu_word") > 0  ? sPrefix+"_03_r.gif" : sPrefix+"_03.gif")%>" border="0" name="img103" onMouseOver="document.all.img103.src='/images/common/left_pds_03_r.gif'" onMouseOut="javascript:this.src='/images/common/left_<%=imgSrc%>'"></A></td>
										</tr>
									</TABLE>
								</td>
							</tr>
						</TABLE>