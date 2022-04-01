<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" href="/css/style_myhome.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
function etestWright(){
      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'SulmunUserPaperListPage';
    document.form2.action = "/servlet/controller.research.SulmunTargetUserServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function IndividualResult(p_etestsubj, p_year, p_etestcode, p_userid, p_etesttext, p_average, p_personcnt) {

  window.self.name = "winIndividualResult";
  farwindow = window.open("", "openIndividualResult", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form2.target = "openIndividualResult";
  document.form2.action = "/servlet/controller.etest.ETestUserServlet";
  document.form2.p_process.value = "ETestUserPaperResult2";
  
  document.form2.p_etestsubj.value    = p_etestsubj; 
  document.form2.p_year.value    = p_year; 
  document.form2.p_etestcode.value = p_etestcode; 
  document.form2.s_etestsubj.value    = p_etestsubj; 
  document.form2.s_gyear.value    = p_year; 
  document.form2.s_etestcode.value = p_etestcode; 
  document.form2.p_etesttext.value  = p_etesttext; 
  document.form2.p_userid.value  = p_userid;

  document.form2.submit();

  farwindow.window.focus();
  document.form2.target = window.self.name;
}
//-->
</script>
</HEAD>

<BODY topmargin="0" leftmargin="0">
<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	<tr class="tableheight1" valign="top">
		<td background="/images/common/bg_myhome.gif">
			<TABLE cellpadding="0" cellspacing="0" border="0" width="950">
				<tr class="tableheight2">
					<td>
						<!-- TOP 시작 -->

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
						
						<!-- 검색 끝 -->
						<!-- 본문 시작 -->
						<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
							<tr>
								
                <td class="bodypd"> 
                  <!-- 현재 위치 시작 -->
                  <%@include file="/incmenu/location.jsp"%>
                  <!-- 현재 위치 끝 -->
                  <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                    <tr> 
                      <td> <TABLE cellpadding="0" cellspacing="0" border="0">
                          <tr> 
                            <td><IMG src="/images/common/title_bullet.gif" border="0"></td>
                            <td><img src="/images/user/myhome/title_study_05.gif"></td>
                          </tr>
                        </TABLE></td>
                    </tr>
                    <tr height="1"> 
                      <td bgcolor="DAD8D8"></td>
                    </tr>
                    <tr> 
                      <td> <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                          <tr> 
                            <td><TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                                <tr height="15"> 
                                  <td></td>
                                </tr>
<%
         ArrayList blist = (ArrayList)request.getAttribute("ETestUserList");
		 DataBox dbox01 = null;
		 if(blist.size() > 0){
		     dbox01 = (DataBox)blist.get(0);
		 }else{
		     dbox01 = new DataBox("resoponsebox");
		 }
%>
 <form name="form1" method="post" action="/servlet/controller.etest.ETestUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_etestsubj"    value="<%=dbox01.getString("d_etestsubj")%>">
        <input type="hidden" name="p_gyear"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="p_etestcode"  value="<%=dbox01.getString("d_etestcode")%>">
        <input type="hidden" name="p_startdt"  value="<%=dbox01.getString("d_startdt")%>">
        <input type="hidden" name="p_enddt"  value="<%=dbox01.getString("d_enddt")%>">
        <input type="hidden" name="p_userid"  value="">
</form>
                                <tr> 
                                  <td height=15><table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                                        <td  class=guidetitle>E-Test 평가</td>
                                      </tr>
                                    </table></td>
                                </tr>
								<tr> 
                                        
                                  <td class=dirtext02 style=padding-left:6>테스트명을 클릭하시면 테스트에 참가 하실 수 있습니다.</td>
                                      </tr>
                                <tr> 
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
                                              <td width="50"  class=boardskin1_textb>No</td>
                                              <td width="192"  class=boardskin1_textb>테스트명</td>
                                              <td width="88"  class=boardskin1_textb>연도</td>
                                              <td width="88"  class=boardskin1_textb>문제수</td>
                                              <td width="132"  class=boardskin1_textb>시작시간</td>
                                              <td width="134"  class=boardskin1_textb>종료시간</td>
                                              <td width="71"  class=boardskin1_textb>시험시간</td>
                                            </tr>

<%
        for (int i=0; i<blist.size(); i++) {
            DataBox dbox = (DataBox)blist.get(i);   

			Random ran = new Random();
			
    
%>
                                            <tr> 
											  <td  class=boardskin1_textn><%=String.valueOf(i+1)%></td>
                                              <td  class=boardskin1_textn><a href="javascript:etestWright('<%=ran.nextInt(dbox.getInt("d_papercnt"))%>')"><%=String.valueOf(dbox.getString("d_etesttext"))%></a></td>
                                              <td  class=boardskin1_textn><%=dbox.getInt("d_year")%></td>
											  <td  class=boardskin1_textn><%=dbox.getInt("d_etestcnt")%></td>
                                              <td  class=boardskin1_textn><%=FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy-MM-dd HH:mm")%></td>
											  <td  class=boardskin1_textn><%=FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy-MM-dd HH:mm")%></td>
											  <td  class=boardskin1_textn><%=dbox.getInt("d_etesttime")%></td>
                                            </tr>
<%    
        } 
%>

                                          </table></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td class="boardskin1_bottom"></td>
                                </tr>
                                <tr> 
                                  <td height=8></td>
                                </tr>
                                <tr> 
                                  <td height=8></td>
                                </tr>
<%
         ArrayList blist1 = (ArrayList)request.getAttribute("ETestUserHistoryList");
		 DataBox dbox0 = null;
		 if(blist1.size() > 0){
		     dbox0 = (DataBox)blist1.get(0);
		 }else{
		     dbox0 = new DataBox("resoponsebox");
		 }
%>
 <form name="form2" method="post" action="/servlet/controller.etest.ETestUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_etestsubj"    value="<%=dbox01.getString("d_etestsubj")%>">
        <input type="hidden" name="p_year"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="p_etestcode"  value="<%=dbox01.getString("d_etestcode")%>">
        <input type="hidden" name="s_etestsubj"    value="<%=dbox01.getString("d_etestsubj")%>">
        <input type="hidden" name="s_gyear"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="s_etestcode"  value="<%=dbox01.getString("d_etestcode")%>">
        <input type="hidden" name="p_startdt"  value="<%=dbox01.getString("d_startdt")%>">
        <input type="hidden" name="p_enddt"  value="<%=dbox01.getString("d_enddt")%>">
        <input type="hidden" name="p_userid"  value="">
        <input type="hidden" name="p_etesttext"  value="">
  	    <input type="hidden" name="p_personcnt"   value="1">
		<input type="hidden" name="p_average"  value="1">
</form>
                                <tr> 
                                  <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                                        <td  class=guidetitle>E-Test 결과</td>
                                      </tr>
                                    </table></td>
                                </tr>
								<tr> 
                                        
                                  <td class=dirtext02 style=padding-left:6>테스트명에 
                                    과목을 클릭하시면 테스트 결과를 조회 하실 수 있습니다.</td>
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
                                              <td width="50"  class=boardskin1_textb>No</td>
                                              <td width="192"  class=boardskin1_textb>테스트명</td>
                                              <td width="88"  class=boardskin1_textb>연도</td>
                                              <td width="115"  class=boardskin1_textb>응시일</td>
                                              <td width="97"  class=boardskin1_textb>점수</td>
                                            </tr>
<%
        for (int i=0; i<blist1.size(); i++) {
            DataBox dbox1 = (DataBox)blist1.get(i);   
    
%>
                                            <tr> 
                                              <td  class=boardskin1_textn><%=String.valueOf(i+1)%></td>
											  <td  class=boardskin1_textn>
											  <a href="javascript:IndividualResult('<%=dbox1.getString("d_etestsubj")%>','<%=dbox1.getString("d_year")%>','<%=dbox1.getString("d_etestcode")%>','<%=dbox1.getString("d_userid")%>','<%=dbox1.getString("d_etesttext")%>','1','1')" class="e"><%=String.valueOf(dbox1.getString("d_etesttext"))%></a></td>
                                              <td  class=boardskin1_textn><%=dbox1.getInt("d_year")%></td>
                                              <td  class=boardskin1_textn><%=FormatDate.getFormatDate(dbox1.getString("d_ended"),"yyyy-MM-dd HH:mm:ss")%></td>
                                              <td  class=boardskin1_textn><%=dbox1.getInt("d_score")%></td>
                                            </tr>
<%    
        } 
%>

                                          </table></td>
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
                        </TABLE></td>
                    </tr>
                  </table> </td>
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

			<!-- FOOTER 끝 -->
		</td>
	</tr>
</TABLE>
</BODY>
</HTML>