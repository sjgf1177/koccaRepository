<%
//**********************************************************
//  1. 제      목: 교수 소개
//  2. 프로그램명: za_TutorIntroduce_L.jsp
//  3. 개      요: 교수 소개
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: msc 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.tutor.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_process   = box.getString("p_process");    

	DataBox dbox = null;

	dbox = (DataBox)request.getAttribute("introduceTutor");
	ArrayList list = (ArrayList)request.getAttribute("tutorSubjList");

	Vector v_photoVector = null; 
    Vector v_newphotoVector = null;          //      서버에 저장되있는 파일명 배열

    String v_userid   = "";
    String v_name = "";
    String v_content = "";
	String v_subj = "";
	String v_subjnm = "";
	String v_academic = "";
	String v_career = "";
	String v_book = "";
    String  v_intro     =  "";

	int i = 0;

	v_userid	= dbox.getString("d_userid");
	v_name		= dbox.getString("d_name");
	v_academic  = dbox.getString("d_academic");
	v_career	= dbox.getString("d_career");
	v_book		= dbox.getString("d_book");
	v_content	= dbox.getString("d_content");
	v_intro		= dbox.getString("d_intro");

	v_academic     =  StringManager.replace(v_academic,"\r\n","<br><img src='/images/user/kocca/course/bl_message.gif' width='4' height='6'>&nbsp;") ;
	v_career      =  StringManager.replace(v_career,"\r\n","<br><img src='/images/user/kocca/course/bl_message.gif' width='4' height='6'>&nbsp;");
    v_book        =  StringManager.replace(v_book,"\r\n","<br><img src='/images/user/kocca/course/bl_message.gif' width='4' height='6'>&nbsp;");

	v_content	=  StringManager.replace(v_content,"\r\n","<br>");
	v_intro		=  StringManager.replace(v_intro,"\r\n","<br>");

	v_academic	=  StringManager.replace(v_academic,"\r\n","<br>");
	v_career	=  StringManager.replace(v_career,"\r\n","<br>");

	v_photoVector = (Vector)dbox.getObject("d_photo");
	v_newphotoVector = (Vector)dbox.getObject("d_newphoto");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>    
</head>
<body bgcolor="#FCFAF0" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name = "form1" method = "post" action="/servlet/controller.tutor.TutorMessageAdminServlet">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_seq" value="">
    <input type="hidden" name="e_subj" value="">
    <input type="hidden" name="s_action" value="">
<body bgcolor="#FCFAF0" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<!--div id="Layer1" style="position:absolute; left:282px; top:189px; width:347; height:97; z-index:1; overflow-x:hidden;overflow-y:scroll ">
  <table width="319" border="0" cellspacing="0" cellpadding="0">
    
	  <tr><td class="tbl_gleft"><b><%=v_content%></b></td></tr>
		  <tr><td height="1" background="/images/user/kocca/course/dotline_b.gif"></td></tr>
    
  </table>
</div-->
<table width="701" border="0" cellspacing="14" cellpadding="0">
  <tr>
    <td><table width="673" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/user/kocca/course/bar_popup_top.gif" width="673" height="12"></td>
        </tr>
        <tr>
          <td background="/images/user/kocca/course/bar_popup_bg.gif">
            <!--책임교수소개_타이틀 -->
            <table width="673" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="6">&nbsp;</td>
                <td width="661"><img src="/images/user/kocca/course/type1/tit_popup.gif"></td>
                <td width="6">&nbsp;</td>
              </tr>
              <tr> 
                <td height="22" colspan="3">&nbsp;</td>
              </tr>
            </table>
            <!--교수님메세지 -->
            <table width="673" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="23" rowspan="5">&nbsp;</td>
                <td width="622"><img src="/images/user/kocca/course/bar_message_top.jpg" width="622" height="49"></td>
                <td width="28" rowspan="5">&nbsp;</td>
              </tr>
              <tr> 
                <td bgcolor="#EAE5D5"><table width="622" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="19">&nbsp;</td>
                      <td width="189"><table width="189" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="4" colspan="2"> </td>
                          </tr>
                          <tr> 
                            <td width="175" height="135"><table width="175" height="135" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
                              <tr> 
                                  <td bgcolor="#FFFFFF"><table width="173" border="0" cellspacing="1" cellpadding="0">
                                      <tr> 

									  <%	
										if( v_photoVector != null ) { 

											for(i = 0; i < v_photoVector.size(); i++) {      //     첨부파일 갯수에 따라
												String v_photo = (String)v_photoVector.elementAt(i);
												String v_newphoto = (String)v_newphotoVector.elementAt(i);  

												if(v_newphoto != null && !v_newphoto.equals("")) {
													 v_newphoto = conf.getProperty("url.upload") +  "bulletin/" + v_newphoto; 

									 %>
													<td><img src ="<%=v_newphoto%>"width = "171" height="131" ></td>	
											  <%}else{%>
													<td><img src="/images/user/kocca/course/photo_message.gif"></td>
											 <%}%>	
										<%	}   %>
									<%			} %>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td width="14" valign="bottom"><img src="/images/user/kocca/course/photo_shadow.jpg" width="14" height="29"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                            <td rowspan="3">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="1" background="/images/user/kocca/course/dotline.gif"></td>
                          </tr>
                          <tr> 
                            <td class="popup_message01"><div align="center">[ <%=v_name %> ]</div></td>
                          </tr>
                        </table></td>
                      <td width="7">&nbsp;</td>
                      <td width="393" valign="top">
					    <table width="393" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/course/stit_message.jpg"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/kocca/course/bar_sub_messsge.gif"></td>
                          </tr>
                          <tr>
                            <td height="100"  background="/images/user/kocca/course/bar_sub_messsge_bg.gif" style="padding-left:30px;">
                            <div style='width:347px;height:97px;overflow:auto;scrollbar-3dlight-color:#B5B5B5;scrollbar-arrow-color:#B5B5B5;scrollbar-darkshadow-color:#FFFFFF;scrollbar-face-color:#F2F7FE;scrollbar-highlight-color:#FFFFFFc;scrollbar-shadow-color:#B5B5B5;'>
                            <%=v_intro%>
                            </div>
                            </td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/course/bar_sub_messsge_bottom.gif"></td>
                          </tr>
                        </table></td>
                      <td width="14">&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td><img src="/images/user/kocca/course/bar_message_mid.gif"></td>
              </tr>
              <tr> 
                <td background="/images/user/kocca/course/bar_message_bg.gif"><table width="622" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="3">&nbsp;</td>
                      <td width="201" valign="top">
					  <table width="201" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="8" rowspan="3">&nbsp;</td>
                            <td><img src="/images/user/kocca/course/stit_brief.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr> 
                            <td><table width="193" border="0" cellspacing="0" cellpadding="0">
							     <tr>
                                  <td class="popup_message02"><img src="/images/user/kocca/course/bl_message.gif">
                                  <%=v_academic%></td>
                                </tr>
                              </table>
							  </td>
                          </tr>
                        </table></td>
                      <td width="2" valign="top"><img src="/images/user/kocca/course/vline02.gif" ></td>
                      <td width="201" valign="top"><table width="201" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="8" rowspan="3">&nbsp;</td>
                            <td><img src="/images/user/kocca/course/stit_reference.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr> 
                            <td><table width="193" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="popup_message02"><img src="/images/user/kocca/course/bl_message.gif" > <%=v_book%></td>
                                </tr>
                              </table>
                             
                             
                          </tr>
                        </table></td>
                      <td width="2"><img src="/images/user/kocca/course/vline02.gif" ></td>
                      <td width="201" valign="top"><table width="201" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="8" rowspan="3">&nbsp;</td>
                            <td><img src="/images/user/kocca/course/stit_list.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr> 
                            <td><table width="193" border="0" cellspacing="0" cellpadding="0">
							                      <%
                        for(i = 0; i < list.size(); i++) {
                            DataBox dbox2  = (DataBox)list.get(i);
                            v_subj        = dbox2.getString("d_subj");
                            v_subjnm      = dbox2.getString("d_subjnm");

                       %>
							<tr> <td width="6"><div align="right"><img src="/images/user/kocca/course/bl_message.gif" ></div></td> 
							<td class="popup_message02">&nbsp;<%=v_subjnm%></td></tr>

		
						  <%		
							}
						%> 
                               
                              </table>
                              
                          </tr>
                        </table></td>
                      <td width="12">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="622" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="7"></td>
                    </tr>
                    <tr> 
                      <td><div align="right"><a href="javascript:javascript:self.close()"><img src="/images/user/kocca/button/btn_close.gif" border="0"></a></div></td>
                    </tr>
                    <tr>
                      <td height="4"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td><img src="/images/user/kocca/course/bar_message_bottom.gif" ></td>
              </tr>
            </table> </td>
        </tr>
        <tr>
          <td><img src="/images/user/kocca/course/bar_popup_botton.gif" ></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
