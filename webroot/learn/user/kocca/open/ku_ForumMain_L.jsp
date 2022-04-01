<%
//**********************************************************
//  1. 제      목: 회원포럼
//  2. 프로그램명: zu_ForumMain_L.jsp
//  3. 개      요: 회원포럼메인
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: kim.js
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01"); 
    
	// 이달구하기
			Calendar cal 	= Calendar.getInstance();
			//int	year		= cal.get(Calendar.Yaer);
			int month 		= cal.get(Calendar.MONTH);
			String v_month	= String.valueOf(month);
			System.out.println("v_month "+v_month);
			box.put("p_tmpmonth",v_month);

    //최다조회수 주제글글 가져오기
    ArrayList listSubject = (ArrayList)request.getAttribute("SubjectMax");     
    //최다조회수 자유포럼글 가져오기
    ArrayList listFree = (ArrayList)request.getAttribute("SubjectFreeMax");         
    //추천글 가져오기
    ArrayList listRec = (ArrayList)request.getAttribute("SubjectReviewMax");     
	
	Vector  v_realfileVector = null;
    Vector  v_savefileVector = null;
    Vector  v_fileseqVector  = null;
      
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topOpenkocca.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
<SCRIPT LANGUAGE="JavaScript">

	//포럼 상세보기
    function selectForumView(seq, types) {

		v_url = "/servlet/controller.homepage.OpenForumUserServlet?p_process=selectView";
		document.Forumform1.p_seq.value = seq;
		document.Forumform1.p_types.value = types;
        document.Forumform1.action = v_url;
        document.Forumform1.submit();
    }

</SCRIPT>



			
						<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 열린마당 > 회원포럼</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/openkocca/<%=tem_subimgpath%>/tit_forum.gif"  ></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="88" rowspan="3"><img src="/images/user/kocca/openkocca/text_forum.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr > 
                            <td height="1" colspan="2" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr > 
                            <td height="7" colspan="2" ></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="16"></td>
                          </tr> 
                        </table>
        
						<table width="680" border="0" cellspacing="0" cellpadding="0">
			
							
							  <!-- 공지사항 -->
                               <jsp:include page="/learn/user/kocca/open/Notice.jsp" flush="true" />
                              <!-- //공지사항 -->
							

<form name="Forumform1" method="post">
          <input type=hidden name=p_seq value="">
          <input type=hidden name=p_types value="">
						  <tr>
							<td width="330"  valign="top" >
								<table width="330" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_forum_01.gif" usemap="#Map01" border="0"><map name="Map01"><area shape="rect" coords="290,7,324,26" href="/servlet/controller.homepage.OpenForumUserServlet?p_process=SubjectList"></map> </td>
									</tr>
									<tr>
										<td  background="/images/user/kocca/openkocca/bg_st_forum.gif" align="center">
										<table width="310"  height="80" border="0" cellspacing="0" cellpadding="0">											
											<tr>
												<td height="10" colspan="8"></td>
											</tr>											
											
											 <% //조회수 많은 주제포럼
											  if (listSubject != null) {
												  
												  for(int i = 0; i <listSubject.size(); i++) {
													        DataBox dbox    = (DataBox)listSubject.get(i);
													
   													        String v_seq        =  dbox.getString("d_seq");
                                                            String v_types      =  dbox.getString("d_types");   			
															String v_title      =  dbox.getString("d_title");
															String v_contents   =  dbox.getString("d_contents");			
													        String v_indate     =  dbox.getString("d_indate");
													        String v_upfile     =  dbox.getString("d_upfile");				
													        String v_cnt        =  dbox.getString("d_cnt");
   													        String v_subject    =  dbox.getString("d_subject");

															v_realfileVector    = (Vector)dbox.getObject("d_realfile");
															v_savefileVector    = (Vector)dbox.getObject("d_savefile");
															v_fileseqVector		= (Vector)dbox.getObject("d_fileseq");
													 %>       
															<tr>
															<td width="74" rowspan="5" valign="top" style="padding-top:5px;">
															
																<table width="74" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
																	<tr> 
																	  <td>
<% 		if( !v_upfile.equals("") ) { 
			for( i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
				String v_realfile = (String)v_realfileVector.elementAt(0);
				String v_savefile = (String)v_savefileVector.elementAt(0);  
				String v_fileseq  = (String)v_fileseqVector.elementAt(0); 
				if(i == 1) break;
				
				if(v_realfile != null && !v_realfile.equals("")) {  
					v_savefile = conf.getProperty("url.upload") + "bulletin/" + v_savefile;%>
																		<img src="<%=v_savefile%>" width="74" height="56"></td>
<%				}
			}
		}else{%>
																		<img src="/images/user/kocca/homepage/type1/blank_photo1.gif" width="74" height="56"></td>
<%		}
%>
																	  
																	</tr>
																  </table>
															   </td>
																<td width="10" rowspan="5"></td>
																<td class="tbl_gleft08"><%=v_subject%></td>
																<td width="1" ><img src="/images/user/kocca/openkocca/vline.gif" ></td>
																<td class="tbl_grc"><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd")%></td>
																<td width="1" ><img src="/images/user/kocca/openkocca/vline.gif" ></td>
																<td class="tbl_grc">조회 : <%=v_cnt%></td>
															</tr>
															<tr > 
																<td height="1" colspan="5" background="/images/user/kocca/common/dot_bg_9.gif"></td>
															  </tr>
															<tr>
																<td class="tbl_gleft07" colspan="5"><a href="javascript:selectForumView('<%=v_seq%>','<%=v_types%>');" ><%=StringManager.formatTitle(v_title,25)%></a></td>
															</tr>
															
														
											
                                                    <%}
                                                    }%>											
										</table>
										</td>
									</tr>									
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_forum_bo.gif" ></td>
									</tr>
								</table>						
							</td>
							<td width="20"></td>
							<td width="330" valign="top"  >
								<table width="330" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_forum_02.gif"  usemap="#Map02" border="0"><map name="Map02"><area shape="rect" coords="290,7,324,26" href="/servlet/controller.homepage.OpenForumUserServlet?p_process=SubjectFreeList"></map></td>
									</tr>
									<tr>
										<td background="/images/user/kocca/openkocca/bg_st_forum.gif" align="center">
										<table width="310"  height="80" border="0" cellspacing="0" cellpadding="0">											
											<tr>
												<td height="10" colspan="8"></td>
											</tr>											
											
										 <% //조회수 많은 자유포럼
										   if (listFree != null) {
												  for(int i = 0; i <listFree.size(); i++) {
													        DataBox dbox    = (DataBox)listFree.get(i);
													
													        String v_seq        =   dbox.getString("d_seq");
                                                            String v_types      =   dbox.getString("d_types");   		
															String v_title      =   dbox.getString("d_title");
															String v_contents   =   dbox.getString("d_contents");			
													        String v_indate     =  dbox.getString("d_indate");
													        String v_upfile     =   dbox.getString("d_upfile");			
													        String v_cnt        =  dbox.getString("d_cnt");
   													        String v_subject    =  dbox.getString("d_subject");

															v_realfileVector    = (Vector)dbox.getObject("d_realfile");
															v_savefileVector    = (Vector)dbox.getObject("d_savefile");
															v_fileseqVector		= (Vector)dbox.getObject("d_fileseq");
													        
													 %>       
															<tr>
															<td width="74" rowspan="5" valign="top" style="padding-top:5px;">
															
																<table width="74" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
																	<tr> 
 																	  <td>
<% 		if( !v_upfile.equals("") ) { 
			for( i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
				String v_realfile = (String)v_realfileVector.elementAt(0);
				String v_savefile = (String)v_savefileVector.elementAt(0);  
				String v_fileseq  = (String)v_fileseqVector.elementAt(0); 
				if(i == 1) break;
				
				if(v_realfile != null && !v_realfile.equals("")) {  
					v_savefile = conf.getProperty("url.upload") + "bulletin/" + v_savefile;%>
																		<img src="<%=v_savefile%>" width="74" height="56"></td>
<%				}
			}
		}else{%>
																		<img src="/images/user/kocca/homepage/type1/blank_photo1.gif" width="74" height="56"></td>
<%		}
%>
																	</tr>
																  </table>
															   </td>
																<td width="10" rowspan="5"></td>
																<td class="tbl_gleft08"><%=v_subject%></td>
																<td width="1" ><img src="/images/user/kocca/openkocca/vline.gif" ></td>
																<td class="tbl_grc"><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd")%></td>
																<td width="1" ><img src="/images/user/kocca/openkocca/vline.gif" ></td>
																<td class="tbl_grc">조회 : <%=v_cnt%></td>
															</tr>
															<tr > 
																<td height="1" colspan="5" background="/images/user/kocca/common/dot_bg_9.gif"></td>
															  </tr>
															<tr>
																<td class="tbl_gleft07" colspan="5"><a href="javascript:selectForumView('<%=v_seq%>','<%=v_types%>');" ><%=StringManager.formatTitle(v_title,25)%></a></td>
															</tr>
														
														
											
                                                    <%}
                                                    }%>											
										</table>
										</td>
									</tr>									
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_forum_bo.gif" ></td>
									</tr>
								</table>						
							</td>
						  </tr>
						  <tr>
							<Td height="21"></td>
						  </tr>
						  <tr>
							<td width="330" valign="top" >
								<table width="330" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_forum_03.gif"   usemap="#Map03" border="0"><map name="Map03"><area shape="rect" coords="290,7,324,26" href="/servlet/controller.homepage.OpenForumUserServlet?p_process=SubjectReview">
										</map></td>
									</tr>
									<tr>
										<td background="/images/user/kocca/openkocca/bg_st_forum.gif" align="center">
										<table width="310"  height="80" border="0" cellspacing="0" cellpadding="0">											
											<tr>
												<td height="10" colspan="8"></td>
											</tr>											
											
										 <% //추천포럼
												 
										   if (listRec != null) {
												  for(int i = 0; i <listRec.size(); i++) {
													        DataBox dbox    = (DataBox)listRec.get(i);
        
        												    String v_seq       =   dbox.getString("d_seq");
                                                            String v_types     =   dbox.getString("d_types");   		
															String v_title     =   dbox.getString("d_title");
															String v_contents  =   dbox.getString("d_contents");			
													        String v_indate    =  dbox.getString("d_indate");
													        String v_upfile    =   dbox.getString("d_upfile");			
													        String v_cnt       =  dbox.getString("d_cnt");
   													        String v_subject   =  dbox.getString("d_subject");
													       
															v_realfileVector    = (Vector)dbox.getObject("d_realfile");
															v_savefileVector    = (Vector)dbox.getObject("d_savefile");
															v_fileseqVector		= (Vector)dbox.getObject("d_fileseq"); 
													 %>       
															<tr>
															<td width="74" rowspan="5" valign="top" style="padding-top:5px;">
															
																<table width="74" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
																	<tr> 
																  <td>
<% 		if( !v_upfile.equals("") ) { 
			for( i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
				String v_realfile = (String)v_realfileVector.elementAt(0);
				String v_savefile = (String)v_savefileVector.elementAt(0);  
				String v_fileseq  = (String)v_fileseqVector.elementAt(0); 
				if(i == 1) break;
				
				if(v_realfile != null && !v_realfile.equals("")) {  
					v_savefile = conf.getProperty("url.upload") + "bulletin/" + v_savefile;%>
																		<img src="<%=v_savefile%>" width="74" height="56"></td>
<%				}
			}
		}else{%>
																		<img src="/images/user/kocca/homepage/type1/blank_photo1.gif" width="74" height="56"></td>
<%		}
%>
																	</tr>
																  </table>
															   </td>
																<td width="10" rowspan="5"></td>
																<td class="tbl_gleft08"><%=v_subject%></td>
																<td width="1" ><img src="/images/user/kocca/openkocca/vline.gif" ></td>
																<td class="tbl_grc"><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd")%></td>
																<td width="1" ><img src="/images/user/kocca/openkocca/vline.gif" ></td>
																<td class="tbl_grc">조회 : <%=v_cnt%></td>
															</tr>
															<tr > 
																<td height="1" colspan="5" background="/images/user/kocca/common/dot_bg_9.gif"></td>
															  </tr>
															<tr>
																<td class="tbl_gleft07" colspan="5"><a href="javascript:selectForumView('<%=v_seq%>','<%=v_types%>');" ><%=StringManager.formatTitle(v_title,25)%></a></td>
															</tr>
															
														
                                                    <%}
                                                    }%>											
										</table>
										</td>
										
									</tr>									
									<tr>
										<td colspan="5"><img src="/images/user/kocca/openkocca/st_forum_bo.gif" ></td>
									</tr>
								</table>						
							</td>
						  </table>      
</form>




<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->

