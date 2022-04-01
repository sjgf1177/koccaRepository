<link rel='stylesheet' type='text/css' href='/css/kms_category_style.css'>
<script src="/script/category_xtree_hmc.js" language="JavaScript"></script>

<%

		String exeSrcFile = request.getRequestURI();
			
		//url에서 대표가 되는 URL의 일부 Tree 여부
		String[] menuUrlKey = {"/hrdknowledge", "/hrdnews", "/hrdbrief", "/seminar", "/e-learning", "soft_down", "/edu"};

		// 링크될 url
		String[] menuSrc = {"/portal/user/gold/hrdknowledge.jsp", "/portal/user/gold/hrdnews.jsp", "/portal/user/gold/hrdbrief.jsp", "/portal/user/gold/seminar_general.jsp", "/portal/user/gold/e-learning.jsp", "/portal/user/gold/soft_down.jsp", "/portal/user/gold/edu_word.jsp"};		

		// 메뉴 이미지명
		String[] menuImgName = {"img101", "img102", "img103", "img104", "img105", "img106", "img107"};
		// 메뉴 이미지
		String[] menuImg = {"/images/common/left_gold_01", "/images/common/left_gold_02", "/images/common/left_gold_03", "/images/common/left_gold_04", "/images/common/left_gold_05", "/images/common/left_gold_06", "/images/common/left_gold_07"};		

		int menuCo = 7;	
		
		com.daumsoft.product.ks.helper.manager.CategoryManager mgr = (com.daumsoft.product.ks.helper.manager.CategoryManager)HelperSystem.getInstance().getManager("CATEGORY");
%>

						
						<TABLE cellpadding="0" cellspacing="0" border="0" height="100%">
							<tr height="74">
								<td style="padding-left:10px"><IMG src="/images/common/left_title_gold.gif" border="0"></td>
							</tr>
							<tr height="10">
								<td></td>
							</tr>
							<tr valign="top">
								<td style="padding-left:25px">
									<TABLE cellpadding="0" cellspacing="0" border="0">


<%
		int iMenu = exeSrcFile.indexOf(menuUrlKey[0]);
		int iMenuBg = -1;
		
		for(int loop=0; loop<menuCo; loop++){

			if(iMenu > -1){
%>

										<tr>
											<td><A HREF="<%=menuSrc[loop]%>"><img src="<%=menuImg[loop]%>_r.gif" border="0" name="<%=menuImgName[loop]%>" ></A></td>
										</tr>
										<tr>
											<td>
												<table cellpadding=0 cellspacing=0 border=0 width=100%>
													<tr><td>&nbsp;</td>
														<td>				
														<!-- 카테고리 트리 출력 -->
														<div style="overflow:auto;width:167;height:100%;">
														<jsp:include page="category_view.jsp" flush="true">
															<jsp:param name="categoryId" value="<%=sCateId%>"/>
														</jsp:include>				
														</div>
														<!-- 카테고리 트리 출력 끝 -->
														</td>
													</tr>
												</table>
											</td>
										</tr>

<%
				iMenu = -1;
			}else {
				iMenuBg = exeSrcFile.indexOf(menuUrlKey[loop]);
				if (iMenuBg > -1) {
%>

										<tr>
											<td>
											<A HREF="<%=menuSrc[loop]%>">
											<img src="<%=menuImg[loop]%>_r.gif" border="0" name="<%=menuImgName[loop]%>"></A></td>
										</tr>

<%
					iMenuBg = -1;
				}else {
%>

										<tr>
											<td>
											<A HREF="<%=menuSrc[loop]%>">
											<img src="<%=menuImg[loop]%>.gif" border="0" name="<%=menuImgName[loop]%>" onMouseOver="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>_r.gif'" onMouseOut="document.all.<%=menuImgName[loop]%>.src='<%=menuImg[loop]%>.gif'"></A></td>
										</tr>

<%
				}
			}
		}
%>

									</TABLE>
								</td>
							</tr>
						</TABLE>