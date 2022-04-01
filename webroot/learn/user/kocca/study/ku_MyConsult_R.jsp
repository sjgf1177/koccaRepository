<%
//**********************************************************
//  1. 제      목: 나의공부방 > 나의 상담내역
//  2. 프로그램명 : ku_MyConsult_R.jsp
//  3. 개      요: 나의질문방
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","03"); 

    DataBox dbox        = (DataBox)request.getAttribute("MyQnaStudyViewPage");

	String v_inuserid   = dbox.getString("d_inuserid");
    String v_name       = dbox.getString("d_name");
    String v_indate     = dbox.getString("d_indate");
    String v_title      = dbox.getString("d_title");
    String v_contents   = dbox.getString("d_contents");
    //String v_cnt        = dbox.getString("d_cnt");

   	Vector v_realfileVector = null;
	Vector v_savefileVector = null;
	v_realfileVector    = (Vector)dbox.getObject("d_realfile");
	v_savefileVector    = (Vector)dbox.getObject("d_savefile");
	int    v_filecount = v_realfileVector.size();
	
	int v_pageno = box.getInt("p_pageno");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>

<%@ include file="/learn/user/kocca/include/topMyclass.jsp"%>

<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
				
						<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 마이클래스&gt; 나의상담내역</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/myclass/<%=tem_subimgpath%>/tit_myconsult.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/myclass/text_myconsult.gif"></td>
                          </tr>
                          <tr>
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/myclass/st_myconsult.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="9"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td colspan="4"><img src="/images/user/kocca/myclass/bar_myconsult_view_top.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="2" colspan="6"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="linecolor_consult01">제목</td>
                            <td width="605" colspan="5" class="tbl_gleft01"><%=v_title%></td>
                          </tr>
                          <tr> 
                            <td colspan="4" height="1"></td>
                          </tr>
                          <tr> 
                            <td colspan="4" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="4" height="1"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="linecolor_consult01">작성자</td>
                            <td width="110" class="tbl_gleft02"><%=v_name%>(<%= v_inuserid %>)</td>
                            <td width="75" class="linecolor_consult01" >작성일</td>
                            <td width="125" class="tbl_gleft02"><%= FormatDate.getFormatDate(v_indate, "yyyy-MM-dd") %></td>
                          </tr>
                          <tr> 
                            <td colspan="4" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="4" height="2"></td>
                          </tr>
                          <tr> 
                            <td colspan="4" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="4"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="24" colspan="2">&nbsp;</td>
                                </tr>
                                <tr> 
                                  <td width="40">&nbsp;</td>
                                  <td><%=v_contents%></td>
                                </tr>
                                <tr> 
                                  <td height="20" colspan="2">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td colspan="4" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="linecolor_consult01">첨부파일</td>
                            <td colspan="3" class="tbl_gleft02">
							<table width="540" height="20" border="0" cellpadding="0" cellspacing="0" align="center">
		<%		
			if( v_realfileVector != null ) { 
				
				for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
					String v_realfile = (String)v_realfileVector.elementAt(i);
					String v_savefile = (String)v_savefileVector.elementAt(i); 
			
					if(!v_realfile.equals("")) {  
		%>
							<tr bgcolor="#F5F8EF"><td class="tbl_gleft04"><a href ='/servlet/controller.library.DownloadServlet?p_savefile=<%=v_savefile%>&p_realfile=<%=v_realfile%>'> 
				<%=v_realfile%></a>&nbsp;</td>
			<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>> 
		<%			}else{%>
			 <tr bgcolor="#F5F8EF"><td class="tbl_gleft04">첨부된 파일이 없습니다 <td>
		<%			}
				}
			}
		%> 			
			</tr>
		</table>  
							</td>
                          </tr>
                          <tr> 
                            <td height="3" colspan="4" class="tbl_bgcolor_myconsult"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="20"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="27" class="linecolor_notice"><table width="52" border="0" align="right" cellpadding="0" cellspacing="0">
                                      <tr> 
                                        <td><a href="/servlet/controller.study.KMyClassServlet?p_process=MyConsult&p_pageno=<%=v_pageno%>"><img src="/images/user/kocca/button/btn_list.gif" border="0"></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td height="1" bgcolor="#cccccc"></td>
                                </tr>
                              </table>
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td height="20">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->

