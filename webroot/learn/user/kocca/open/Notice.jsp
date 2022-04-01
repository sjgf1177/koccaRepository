
<%
//**********************************************************
//  1. 제      목: 회원포럼 공지사항 
//  2. 프로그램명 : Notice.jsp
//  3. 개      요: 회원포럼 공지사항 
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.18
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>


<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","01");  

    //int v_seqNotice = 0;
    box.put("p_process","moreNotice");
    box.put("p_type", "FN");
    box.put("p_grcode", "0000000");
    box.put("p_subj", "0000000000");
    box.put("p_year", "0000");
    box.put("p_subjseq", "0000");
                
	OpenForumNoticeBean beanNotice= new OpenForumNoticeBean();
 	 int tabseq =beanNotice.selectTableseq(box);
      if (tabseq == 0) {
         tabseq = 13;
      }
      box.put("p_tabseq", String.valueOf(tabseq));
	  box.put("p_process","main");
   	  ArrayList noticeList = beanNotice.selectDirectList(box); 
%>

<SCRIPT LANGUAGE="JavaScript">

	//상세보기
    function selectView(seq) {

		v_url = "/servlet/controller.homepage.OpenForumNoticeServlet?p_process=selectView&p_seq="+seq;
		//		alert(v_url);
        document.nform1.action = v_url;
        document.nform1.submit();
    }



</SCRIPT>
	

<form name="nform1" method="post">
	<input type="hidden" name="p_process">

								
							<tr>
							<td  colspan="3">
							<img src="/images/user/kocca/openkocca/tit_forim_notice.gif" border=0 usemap="#Map0" border="0"><map name="Map0"><area shape="rect" coords="605,2,632,18" href="/servlet/controller.homepage.OpenForumNoticeServlet?p_process=List"></map> 
							
										
							</td>
						  </tr>
                          <tr> 
                            <td height="10"  colspan="3"></td>
                          </tr>
						  <tr>
							<td colspan="3">
							
							

                       <table width="680" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="95"><img src="/images/user/kocca/openkocca/forum_notice.gif" border="0">
										</td>
										<td>
											<table width="540" border="0" cellspacing="0" cellpadding="0">
											
											<% 
												int nlsize = (noticeList.size());
													for(int i = 0; i < nlsize; i++) {
													  DataBox dbox = (DataBox)noticeList.get(i);
													  int v_seqNotice  = dbox.getInt("d_seq");
											%>
	
												<tr>
													<td width="9"><img src="/images/user/kocca/openkocca/bllet.gif" ></td>
                                                    <td width="450" class="notice"> <a href="javascript:selectView('<%=v_seqNotice%>')"><%=dbox.getString("d_adtitle")%></td>
													<td width="81"class="notice_day">[<%=FormatDate.getFormatDate(dbox.getString("d_addate"),"yyyy-MM-dd")%>]</td>
												<tr>
												<tr> 
													<td height="1" colspan="3" class="linecolor_main01"></td>
												  </tr>
											<%		}
												for(int j = 0; j < 4 - nlsize; j++){%>			
												<tr>
													<td width="9"><img src="/images/user/kocca/openkocca/bllet.gif" ></td>
                                                    <td width="450" class="notice">&nbsp;</td>
													<td width="81"class="notice_day">&nbsp;</td>
												<tr>
												<tr> 
													<td height="1" colspan="3" class="linecolor_main01"></td>
												  </tr>
         										<%}%>
	
											</table>
										</td>
									</tr>
								</table>
								
								
								
							
							</td>
						  </tr>
						  <tr>
							<td height="21" colspan="3"></td>
						  </tr>

</form>
		