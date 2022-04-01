<%
//**********************************************************
//  1. 제      목: 홈페이지 FAQ 리스트화면
//  2. 프로그램명: zu_HomePageFaq_L.jsp
//  3. 개      요: 홈페이지 FAQ 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
	
    String  v_faqcategory   = "";
	v_faqcategory = box.getString("p_faqcategory");
	if(v_faqcategory.equals("")){
		
		v_faqcategory = "0031";

		box.put("p_faqcategory", v_faqcategory);
	}
	
	

	//int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int     v_fnum     = 0, v_totalpage = 0, v_rowcount = 1;
	int v_pageno = box.getInt("p_pageno"); 
    String  v_title    = "";
    String  v_contents = "";
    String  v_indate   = "";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<LINK rel="stylesheet" href="/css/style_main.css" type="text/css">
<SCRIPT src="/script/cafe_select.js" language="JavaScript"></SCRIPT>

<script language="JavaScript">
<!--
function Search()
{
	document.form1.p_faqcodecont.value=document.form1.p_faqcode.options[document.form1.p_faqcode.selectedIndex].text;
	document.form1.action="/Controller?cmd=home&act=homefaq";
	document.form1.submit();
}

function clickHandler() {
  var targetId, srcElement, targetElement, srcCss;
  srcElement = window.event.srcElement;

  if (srcElement.className == "Outline") {
     targetId = srcElement.id + "details";
     targetElement = document.all(targetId);
     srcCss = document.all(srcElement.id);

     if (targetElement.style.display == "none") {
        targetElement.style.display = "";
        srcCss.style.color = "#666666";
        srcCss.style.font = "normal 9pt \"돋움\"";
     } else {
        targetElement.style.display = "none";
        srcCss.style.color = "";
        srcCss.style.font = "normal 9pt \"돋움\"";
     }
  }
}

document.onclick = clickHandler;

function changeCategory() {
        document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
        document.form1.p_process.value    = "selectList";
        document.form1.submit();
}
function go(index) {
	document.form1.p_pageno.value = index;
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.p_process.value = "selectList";
	document.form1.submit();
}
function goPage(pageNum) {
	 document.form1.p_pageno.value = pageNum;
	 document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	 document.form1.p_process.value = "selectList";
	 document.form1.submit();
}
function helpdeskList(){
	document.form1.p_process.value = "selectList";
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.submit();
}
-->
</script>
</HEAD>

<BODY topmargin="0" leftmargin="0">

		
<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr class="tableheight1" valign="top">
		<td background="/images/common/bg_sub.gif">
			<TABLE cellpadding="0" cellspacing="0" border="0" width="950">
				<tr class="tableheight2">
					<td>
						<!-- TOP 시작 -->
						<%@include file="/incmenu/top.jsp"%>
						<!-- TOP 끝 -->
					</td>
				</tr>
				<tr class="tableheight3">
					<td style="BORDER-LEFT:#FFFFFF 10px solid">
						<!-- 메인 플래시 시작 -->
						<%@include file="/incmenu/sub.jsp"%>
						<!-- 메인 플래시 끝 -->
					</td>
				</tr>
				<tr class="tableheight4">
					<td style="BORDER-LEFT:#FFFFFF 10px solid">
						<!-- 메뉴바 시작 -->
						<%@include file="/incmenu/menu.jsp"%>
						<!-- 메뉴바 끝 -->
					</td>
				</tr>
			</TABLE>
		</td>
	</tr>
	<tr valign="top">
		<td>
			<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
				<tr valign="top">
					<td width="200" background="/images/common/left_bg.gif">
						<!-- 왼쪽 메뉴 시작 -->
						<%@include file="/incmenu/left_helpdesk.jsp"%>
						<!-- 왼쪽 메뉴 끝 -->
					</td>
					<td>
						<!-- 검색 시작 -->
						<jsp:include page="/incmenu/search_main.jsp" flush="true">
							<jsp:param name="GUBUN" value="PORTAL"/>
						</jsp:include>
						<!-- 검색 끝 -->
						<!-- 본문 시작 -->
						<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
							<tr>
								<td class="bodypd">
									<!-- 현재 위치 시작 -->
									<jsp:include page="/incmenu/helpdesk_location.jsp" flush="true">
									<jsp:param name="URL" value="<%=request.getServletPath().toString()%>"/>
									</jsp:include>

									<!-- 현재 위치 끝 -->
									<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
										<tr>
											<td><IMG src="/images/common/title_helpdesk_01.gif" border="0"></td>
										</tr>
										<tr height="1">
											<td bgcolor="E5E5E5"></td>
										</tr>
										<tr height="20">
											<td></td>
										</tr>
										<tr>
											<td>
											<form name = "form1" method = "post">
												<input type = "hidden" name = "p_process" value = "">
												<input type = "hidden" name = "p_pageno" value = "">
												<input type = "hidden" name = "p_seq" value = "">
												<input type = "hidden" name = "p_userid" value = "">
												<TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
													<tr>
														<td align="right"><IMG src="/images/user/helpdesk/helpdesk_img03.gif" border="0">
															<%=HomePageFAQBean.homepageGetFaqCategorySelecct("p_faqcategory", v_faqcategory, "onChange=\"javascript:changeCategory()\"", 1)%>
															</td>
													</tr>
													<tr height="3">
														<td></td>
													</tr>
												</TABLE>
												<!--내용시작-->
												<TABLE cellpadding="2" cellspacing="1" border="0" width="100%">
													<tr height="1">
														<td background="/images/common/line_bg.gif"></td>
													</tr>
													<!--for문 돌기-->
												<%if(list.size() != 0){%>
												<%
															int i =0;
															for( i = 0; i < list.size(); i++) {

																DataBox dbox = (DataBox)list.get(i);  
																
																v_title    = dbox.getString("d_title");

																v_contents = dbox.getString("d_contents");
																v_contents = StringManager.replace(v_contents,"\r\n","<br>");
																v_totalpage = dbox.getInt("d_totalpage");
																v_rowcount = dbox.getInt("d_rowcount");

																
																  

												%>
													<tr height="28">
														<td  class=FAQ_q onMouseOver="this.style.backgroundColor='#EDF3F8';" onMouseOut="this.style.backgroundColor='#FFFFFF';"><span id="Out<%=i%>" class="Outline" style="cursor:hand"><IMG src="/images/user/helpdesk/helpdesk_img01.gif" border="0" align="absmiddle"> <%=v_title%></span></td>
													</tr>
													
													<tr>
														<td style="padding:7px"><div id="Out<%=i%>details" style="display:none; position:relative;padding-top:4px;visibility: visible;" class=FAQ_a>
															<TABLE cellpadding="0" cellspacing="0" border="0">
																<tr valign="top">
																	<td style="padding-top:2px" class=FAQ_a><IMG src="/images/user/helpdesk/helpdesk_img02.gif" border="0" align="absmiddle"></td>
																	<td style="padding-left:5" class=FAQ_a><%=v_contents%></td>
																</tr>
															</TABLE>
															</div>
														</td>
														<tr height="1">
														<td background="/images/common/line_bg.gif"></td>
													</tr>
													</tr>
													<%}%>
												<%}else{%>
           

                                                    <tr>
                                                        <td colspan="13" align=center valign=top>
															등록 된 FAQ가 없습니다.		
														</td>
													</tr>

												<%}%>
													</form>
													<!--for문 끝-->
													<!--내용끝-->													
													
												</TABLE>
											</td>
										</tr>
										<tr height="40">
											 <td height = "30" valign = "bottom" align = "right" colspan = "3"></td>
										</tr>
										<TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
										  <tr>
											<td width="1206">
												
												
											</td>
										  </tr>
									 </table>
									</TABLE>
								</td>
							</tr>
						</TABLE>
						<!-- 본문 끝 -->
					</td>
				</tr>
			</TABLE>
		</td>
	</tr>
	<tr height="51">
		<td valign="top">
			<!-- FOOTER 시작 -->
			<jsp:include page="/incmenu/footer.jsp" flush="true">
				<jsp:param name="sUrl" value="<%=request.getServletPath().toString()%>"/>
			</jsp:include>
			<!-- FOOTER 끝 -->
		</td>
	</tr>
</TABLE>

</BODY>
</HTML>