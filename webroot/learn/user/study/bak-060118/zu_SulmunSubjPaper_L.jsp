<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_SulmunSubjPaper_L.jsp
//  3. 개      요: 수강과정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 27
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
 
    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("EducationSubjectList1");  
    list2 = (ArrayList)request.getAttribute("EducationSubjectList2");    
    list3 = (ArrayList)request.getAttribute("GraduationSubjectList2");  
    //out.println(list3.size());
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" href="/css/style_myhome.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function subjWright(p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend){
        document.form1.p_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_listok.value = '0';

        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 816, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

function contentsWright(p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend){
        document.form1.p_subj.value = 'CONTENTS';
        document.form1.s_subj.value = p_subj;
        document.form1.p_grcode.value = p_grcode;
        document.form1.p_gyear.value = p_gyear;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_sulpapernm.value = p_sulpapernm;
		document.form1.p_sulstart.value = p_sulstart;
        document.form1.p_sulend.value = p_sulend;
        document.form1.p_listok.value = '0';

        window.self.name = "winSelectView";
        farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 816, height = 538, top=0, left=0");
        document.form1.target = "openSubjChange";
        document.form1.action = "/servlet/controller.research.SulmunContentsUserServlet";
        document.form1.p_process.value = "SulmunUserPaperListPage";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}
function ReloadPage(p_action) {
  document.form1.p_process.value = 'SulmunUserListPage';
  document.form1.p_action.value  = p_action;
  document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
  document.form1.submit();
}

//-->
</SCRIPT>
</HEAD>

<BODY topmargin="0" leftmargin="0">
<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr class="tableheight1" valign="top">
		<td background="/images/common/bg_myhome.gif">
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
						<%@include file="/incmenu/myhome.jsp"%>
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
					 	<%@include file="/incmenu/left_myhome.jsp"%>
					 	<!-- 왼쪽 메뉴 끝 -->
					 </td>
					 <td>
						<!-- 검색 시작 -->
						<!--%@include file="/incmenu/search_myhome.jsp"%-->
						<jsp:include page="/incmenu/search_main.jsp" flush="true">
							<jsp:param name="GUBUN" value="EDUCATION"/>
						</jsp:include>
						<!-- 검색 끝 -->
						<!-- 본문 시작 -->
						<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
							<tr>
                            <td class="bodypd">
                              <!-- 현재 위치 시작 -->
                               <TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
                                 <tr>
                                    <td align="right" class="location"><img src="/images/common/bullet_location.gif"><a href="http://www.hkhrd.com"> HOME</a> > <a href="http://www.hkhrd.com/learn/user/study/myhome_main.jsp">마이홈</a> > 과정설문조사</td>
                                 </tr>
                                 <tr height="3">
                                    <td></td>
                                 </tr>
                               </table>
                              <!-- 현재 위치 끝 -->
                              <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                                <tr>
                                  <td> <TABLE cellpadding="0" cellspacing="0" border="0">
                                      <tr>
                                        <td><IMG src="/images/common/title_bullet.gif" border="0"></td>
                                        <td><img src="/images/user/myhome/title_myhome_14.gif"></td>
                                      </tr>
                                    </TABLE></td>
                                </tr>
                                <tr height="1">
                                  <td bgcolor="E5E5E5"></td>
                                </tr>
                                <tr height="20">
                                  <td></td>
                                </tr>
								<tr> 
                                  <td class=dirtext02 style=padding-left:6>설문명을 클릭하시면 설문에 참가 하실 수 있습니다.</td>
                                </tr>
                                <tr>
                                  <td>
								     <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
<%
		 int count = 0;
		 int count2=0;
%>
 <form name="form1" method="post" action="/servlet/controller.research.SulmunCommonUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="">
        <input type="hidden" name="s_subj"    value="">
        <input type="hidden" name="p_grcode"  value="">
        <input type="hidden" name="p_gyear"  value="">
        <input type="hidden" name="p_subjseq"  value="">
        <input type="hidden" name="p_sulpapernum"  value="">
        <input type="hidden" name="p_sulpapernm"  value="">
        <input type="hidden" name="p_sulstart"  value="">
        <input type="hidden" name="p_sulend"  value="">
        <input type="hidden" name="p_listok"  value="">
</form>

                                      <tr>
                                        <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                                              <td  class=guidetitle >학습중인과정</td>
                                            </tr>
                                          </table></td>
                                      </tr>

                                      <tr>
                                        <td><table border="0" cellpadding="0" cellspacing="0" width="735">
                                            <tr>
                                              <td class="boardskin1_color" height="3"></td>
                                            </tr>
                                            <tr>
                                              <td height="2"></td>
                                            </tr>
                                            <tr>
                                              <td>
                                                <table width="735" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out >
                                                  <tr valign="middle">
                                                    <td valign="middle" width="30" class=boardskin1_textb>No</td>
                                                    <td valign="middle" width="78" class=boardskin1_textb>교육구분</td>
                                                    <td valign="middle" width="293" class=boardskin1_textb>과정</td>
                                                    <td valign="middle" width="78" class=boardskin1_textb>설문유형</td>
                                                    <td valign="middle" width="142" class=boardskin1_textb>설문명</td>
                                                    <td valign="middle" width="70" class=boardskin1_textb>상태</td>
                                                    <td valign="middle" width="100" class=boardskin1_textb>진도율비교</td>
                                                  </tr>
                                                  <%
                                                    for(int i = 0; i < list1.size(); i++) {
				                                        //count ++; 
                                                        
														DataBox dbox = (DataBox)list1.get(i);
                                                        DataBox dbox2 = (DataBox)list2.get(i);

                                                        String v_isonoff       =  dbox.getString("d_isonoff");
                                                        String v_subjnm        =  dbox.getString("d_subjnm");
														String v_subj          =  dbox.getString("d_subj");
														String v_grcode          =  dbox.getString("d_grcode");
                                                        String v_year          =  dbox.getString("d_year");
                                                        String v_subjseq       =  dbox.getString("d_subjseq");
                                                        int v_sulpapernum     =  dbox.getInt("d_sulpapernum");
                                                        String v_sulpapernm      =  dbox.getString("d_sulpapernm");
                                                        String v_sulstart        =  dbox.getString("d_sulstart");
                                                        String v_sulend        =  dbox.getString("d_sulend");

														String v_subj2          =  dbox2.getString("p_subj");
														String v_grcode2          =  dbox2.getString("d_grcode");
                                                        String v_year2          =  dbox2.getString("p_year");
                                                        String v_subjseq2       =  dbox2.getString("p_subjseq");
                                                        int v_sulpapernum2     =  dbox2.getInt("d_sulpapernum");
                                                        String v_sulpapernm2      =  dbox2.getString("d_sulpapernm");
                                                        String v_sulstart2        =  dbox2.getString("d_sulstart");
                                                        String v_sulend2        =  dbox2.getString("d_sulend");
    
	                                                    String v_promotion  = dbox.getString("p_promotion");      //권장진도율
                                                        String v_progress   = dbox.getString("p_progress");       // 자기진도율

														String v_isonoff_value = "";

                                                        if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                                                        }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }

													%>

                                                    <%if(dbox.getInt("d_sulpapernum")>0){
														count++;%>
												  <tr>
													<td class=boardskin1_textn rowspan="<%if(v_isonoff.equals("ON")) {%>2<%}else {%>1<%}%>"><%=count%></td>
													<td class=boardskin1_textn rowspan="<%if(v_isonoff.equals("ON")) {%>2<%}else {%>1<%}%>"><%=v_isonoff_value%></td>
                                                    <td class=boardskin1_textn rowspan="<%if(v_isonoff.equals("ON")) {%>2<%}else {%>1<%}%>"><%=v_subjnm%></td>
                                                    <td class=boardskin1_textn>과정설문</td>
                                                    <td class=boardskin1_textn>
					                                <%if(dbox.getString("d_suldata").equals("0") && (Double.parseDouble(v_progress)>=(dbox.getInt("d_progress")))){%>
					                                <a href="javascript:subjWright('<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '<%=v_sulstart%>', '<%=v_sulend%>')"><%=String.valueOf(dbox.getString("d_sulpapernm"))%></a>
					                                <%}else{%>
					                                <%=String.valueOf(dbox.getString("d_sulpapernm"))%>
					                                <%}%>													
													</td>
					                                <%if(dbox.getString("d_suldata").equals("0") && (Double.parseDouble(v_progress)>=(dbox.getInt("d_progress")))){%>
                                                    <td  class=boardskin1_textn><font color="red">응시가능</font></td>
					                                <%}else if(dbox.getString("d_suldata").equals("0") && (Double.parseDouble(v_progress)<(dbox.getInt("d_progress")))){%>
                                                    <td  class=boardskin1_textn><font color="red">진도율부족</font></td>
					                                <%}else if(dbox.getString("d_suldata").equals("1")){%>
                                                    <td  class=boardskin1_textn><font color="red">응시완료</font></td>
					                                <%}%>
				                                    <td  class=boardskin1_textn><%=v_progress%> / <%=dbox.getInt("d_progress")%></td>
												  </tr>
											<!--	  <tr>-->
													<%}else if(v_isonoff.equals("ON")) {
														count++;%>
                                                  <tr> 
													<td class=boardskin1_textn><%=count%></td>
													<td class=boardskin1_textn><%=v_isonoff_value%></td>
                                                    <td class=boardskin1_textn><%=v_subjnm%></td>
													<%}%>

													<%if(v_isonoff.equals("ON")) {%>
                                                    <td class=boardskin1_textn>콘텐츠평가</td>
                                                    <td class=boardskin1_textn>
					                                <%if(dbox2.getString("d_contentsdata").equals("0")){%>
					                                <a href="javascript:contentsWright('<%=v_subj2%>', 'ALL', '<%=v_year2%>', '<%=v_subjseq2%>', '<%=v_sulpapernum2%>', '<%=v_sulpapernm2%>', '<%=v_sulstart2%>', '<%=v_sulend2%>')"><%=String.valueOf(dbox2.getString("d_sulpapernm"))%></a>
					                                <%}else{%>
					                                <%=String.valueOf(dbox2.getString("d_sulpapernm"))%>
					                                <%}%>													
													</td>
					                                <%if(dbox2.getString("d_contentsdata").equals("0")){%>
                                                    <td  class=boardskin1_textn><font color="red">응시가능</font></td>
					                                <%}else if(dbox2.getString("d_contentsdata").equals("1")){%>
                                                    <td  class=boardskin1_textn><font color="red">응시완료</font></td>
					                                <%}%>
				                                    <td  class=boardskin1_textn>-</td>
													<%}%>
												  </tr>

                                                  <%}
                                                  if(count == 0){ %>
                                                  <tr>
                                                    <td class=boardskin1_textn colspan="7">학습중인 과정이 없습니다.</td>
                                                  </tr>
                                                  <%
                                                  }
                                                  %>
                                                </table>
                                              </td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr>
                                        <td class="boardskin1_bottom"></td>
                                      </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                      </tr>

                                      <tr>
                                        <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                                              <td  class=guidetitle >학습을마친과정</td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr>
                                        <td><table border="0" cellpadding="0" cellspacing="0" width="735">
                                            <tr>
                                              <td class="boardskin1_color" height="3"></td>
                                            </tr>
                                            <tr>
                                              <td height="2"></td>
                                            </tr>
                                            <tr> 
                                              <td><table width="735" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out >
                                                  <tr> 
                                                    <td width="30" class=boardskin1_textb>No</td>
                                                    <td width="78" class=boardskin1_textb>교육구분</td>
                                                    <td width="293" class=boardskin1_textb>과정</td>
                                                    <td width="142" class=boardskin1_textb>교육기간</td>
                                                    <td width="78" class=boardskin1_textb>설문유형</td>
                                                    <td width="142" class=boardskin1_textb>설문명</td>
                                                    <td width="70" class=boardskin1_textb>상태</td>
                                                  </tr>
                                                  <%
                                                  for(int i = 0; i < list3.size(); i++) {
				                                        

                                                        DataBox dbox2 = (DataBox)list3.get(i);

                                                        String v_isonoff       =  dbox2.getString("d_isonoff");
                                                        String v_subjnm        =  dbox2.getString("d_subjnm");
														String v_subj          =  dbox2.getString("p_subj");
														String v_grcode          =  dbox2.getString("d_grcode");
                                                        String v_year          =  dbox2.getString("p_year");
                                                        String v_subjseq       =  dbox2.getString("p_subjseq");
                                                        int v_sulpapernum     =  dbox2.getInt("d_sulpapernum");
                                                        String v_sulpapernm      =  dbox2.getString("d_sulpapernm");
                                                        String v_sulstart        =  dbox2.getString("d_sulstart");
                                                        String v_sulend        =  dbox2.getString("d_sulend");

                                                        String v_edustart      =  dbox2.getString("d_edustart");
                                                        String v_eduend        =  dbox2.getString("d_eduend");
                                                        v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                                                        v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

														String v_isonoff_value = "";
                                                      
                                                      if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                                                      }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }

                                                  %>
												  <%if(v_isonoff.equals("ON")) {
													  count2 ++; %>
                                                  <tr>
                                                    <td class=boardskin1_textn><%=count2%></td>
													<td class=boardskin1_textn><%=v_isonoff_value%></td>
                                                    <td class=boardskin1_textn><%=v_subjnm%></td>
                                                    <td class=boardskin1_textn><%=v_edustart%>~<%=v_eduend%></td>
													<td class=boardskin1_textn>콘텐츠평가</td>
                                                    <td class=boardskin1_textn>
					                                <%if(dbox2.getString("d_contentsdata").equals("0")){%>
					                                <a href="javascript:contentsWright('<%=v_subj%>', 'ALL', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>', '<%=v_sulstart%>', '<%=v_sulend%>')"><%=String.valueOf(dbox2.getString("d_sulpapernm"))%></a>
					                                <%}else{%>
					                                <%=String.valueOf(dbox2.getString("d_sulpapernm"))%>
					                                <%}%>													
													</td>
					                                <%if(dbox2.getString("d_contentsdata").equals("0")){%>
                                                    <td  class=boardskin1_textn><font color="red">응시가능</font></td>
					                                <%}else if(dbox2.getString("d_contentsdata").equals("1")){%>
                                                    <td  class=boardskin1_textn><font color="red">응시완료</font></td>
					                                <%}%>
                                                  </tr>
                                                  <%}
													}
                                                  if(count2 == 0){
                                                  %>
                                                  <tr>
                                                    <td height="12" class=boardskin1_textn colspan="7">학습을 마친과정이 없습니다.</td>
                                                  </tr>
                                                  <%}%>
                                                </table>
                                              </td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr>
                                        <td class="boardskin1_bottom"></td>
                                      </tr>
                                      <tr>
                                        <td>&nbsp;</td>
                                      </tr>
                                    </TABLE></td>
                                </tr>
                              </table>
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
