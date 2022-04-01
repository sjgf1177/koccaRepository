<%
//**********************************************************
//  1. 제      목: 홈페이지 Q&A 상세화면
//  2. 프로그램명: zu_HomePageQna_R.jsp
//  3. 개      요: 홈페이지 Q&A 상세화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_contents = "", v_inuserid = "", v_indate = "" , v_types = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select = box.getString("p_select"); 
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";
String v_name = "";
DataBox dbox = (DataBox)request.getAttribute("selectQna");

v_inuserid   = dbox.getString("d_inuserid");
v_name   = dbox.getString("d_name");
v_types = dbox.getString("d_types");
v_indate    = dbox.getString("d_indate");
v_title    = dbox.getString("d_title");
v_seq      = dbox.getInt("d_seq");
v_contents = dbox.getString("d_contents");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<LINK rel="stylesheet" href="/css/style_main.css" type="text/css">
<SCRIPT src="/script/cafe_select.js" language="JavaScript"></SCRIPT>
<script language="JavaScript" type="text/JavaScript">
<!--
function selectList() {
   	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "";     
	document.form1.submit();	
}            
function updatePage() {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}
function replyPage() {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "replyPage";
	document.form1.submit();
}      
function deleteAction() {
	if (confirm("정말로 삭제하시겠습니까?")) {
		document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
		document.form1.p_process.value = "delete";
		document.form1.submit();
	}
	else {
		return;
	}
}

function helpdeskList(){
	document.form1.p_process.value = "selectList";
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.submit();
}
//-->
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
									
									<% //@include file="/incmenu/location.jsp"%>
									<!-- 현재 위치 끝 -->
									<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
										<tr>
											<td><IMG src="/images/common/title_helpdesk_02.gif" border="0"></td>
										</tr>
										<tr height="1">
											<td bgcolor="E5E5E5"></td>
										</tr>
										<tr height="2">
											<td></td>
										</tr>
										<tr>
											<td>
												<!-- 게시판 시작 -->
												<form name = "form1" method = "post">
													<input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
												     <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
												     <input type = "hidden" name = "p_select"  value = "<%= v_select %>">
												     <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
												     <input type = "hidden" name = "p_process"  value = "">
												     <input type = "hidden" name = "p_userid" value = "<%= v_inuserid %>">
													 <input type = "hidden" name = "p_types"  value = "<%=v_types%>">
												<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
													<tr height="25">
														<td class="boardbgcolor" width="90" style="padding-left:18px"><IMG src="/images/board/mt_main_title.gif" border="0"></td>
														<td style="padding-left:15"><B><font color="FF8608"><%= v_title %></B></font></td>
													</tr>
													<tr height="25">
														<td class="boardbgcolor" style="padding-left:18px"><IMG src="/images/board/mt_main_name.gif" border="0"></td>
														<td style="BORDER-TOP:#E5E5E5 1px solid;padding-left:15px;padding-right:15px">
															<TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
																<tr>
																	<td style="padding-top:5px"><%=v_name%>(<%= v_inuserid %>)</td>
																	<td align="right" style="padding-top:5px">
																		<TABLE cellpadding="0" cellspacing="0" border="0">
																			<tr height="15">
																				<td align="center" width="43" background="/images/board/mt_main_mbg.gif" style="background-repeat: no-repeat;padding-bottom:2px"><IMG src="/images/board/mt_main_date.gif" border="0"></td>
																				<td width="2"></td>
																				<td style="padding-top:1px"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
																			</tr>
																		</TABLE>
																	</td>
																</tr>
															</TABLE>
														</td>
													</tr>
													<tr height="25">
														<td class="boardbgcolor" style="padding-left:18px"><span class="boardtitle">첨부파일</span></td>
														<td style="BORDER-TOP:#E5E5E5 1px solid;padding-left:19px;padding-right:15px;padding-top:5px">
														<%   for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
                                   String v_realfile = (String)v_realfileVector.elementAt(i);
                                   String v_savefile = (String)v_savefileVector.elementAt(i);
                                     if(v_realfile != null) {  %>
                                 &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a><br>
                                 <input type = "hidden" name = "p_savefile"  value = "<%= v_savefile%>">
                        <%        }    
                                }   %>
														</td>
													</tr>
												</TABLE>
												<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
													<tr height="2">
														<td height="4"></td>
													</tr>
													<tr height="1">
														<td bgcolor="E5E5E5"></td>
													</tr>
													<tr>
														<td style="padding:17">
														<%= StringManager.replace(v_contents,"\n","<br>") %>
														</td>
													</tr>
													<tr height="1">
														<td bgcolor="E5E5E5"></td>
													</tr>
												</TABLE>
												</form>
												<!-- 게시판 끝 -->
												<!-- 버튼 시작 -->
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right" valign="middle"><a href="javascript:replyPage()" target="_self" onfocus=this.blur()><IMG src="/images/board/b_reply.gif" border="0"></a></td>
                      <td width="1%">&nbsp;</td>
                      <td width="53" align="right" valign="middle"><a href="javascript:updatePage()" target="_self" onfocus=this.blur()><IMG src="/images/board/b_edit.gif" border="0"></a></td>
                      <td width="1%">&nbsp;</td>
                      <td width="53" align="right" valign="middle"><a href="javascript:deleteAction()" target="_self" onfocus=this.blur()><IMG src="/images/board/b_del.gif" border="0"></a></td>
                      <td width="1%">&nbsp;</td>
                      <td width="53" align="right" valign="middle"><a href="javascript:selectList()" target="_self" onfocus=this.blur()><IMG src="/images/board/b_list.gif" border="0"></a></td>
                    </tr>
                  </table>
												<!-- 버튼 끝 -->
											</td>
										</tr>
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