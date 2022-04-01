<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
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
function targetWright(){
      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'SulmunUserPaperListPage';
    document.form2.action = "/servlet/controller.research.SulmunTargetUserServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function commonWright(){
      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form1.target = "openWinInsert"
    document.form1.p_process.value = 'SulmunUserPaperListPage';
    document.form1.action = "/servlet/controller.research.SulmunCommonUserServlet";
    document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}
function ReloadPage() {
  document.form1.p_process.value = 'SulmunUserListPage';
  document.form1.submit();
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
                               <TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
                                 <tr>
                                    <td align="right" class="location"><img src="/images/common/bullet_location.gif"><a href="http://www.hkhrd.com"> HOME</a> > <a href="http://www.hkhrd.com/learn/user/study/myhome_main.jsp">마이홈</a> > 일반설문조사</td>
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
                            <td><img src="/images/user/myhome/title_study_08.gif"></td>
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
         ArrayList blist = (ArrayList)request.getAttribute("SulmunCommonUserList");
		 DataBox dbox01 = null;
		 if(blist.size() > 0){
		     dbox01 = (DataBox)blist.get(0);
		 }else{
		     dbox01 = new DataBox("resoponsebox");
		 }
		 int count = 0;
		 int count2=0;
%>
 <form name="form1" method="post" action="/servlet/controller.research.SulmunCommonUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=dbox01.getString("d_subj")%>">
        <input type="hidden" name="p_grcode"  value="<%=dbox01.getString("d_grcode")%>">
        <input type="hidden" name="p_gyear"  value="<%=dbox01.getString("d_year")%>">
        <input type="hidden" name="p_subjseq"  value="<%=dbox01.getString("d_subjseq")%>">
        <input type="hidden" name="p_sulpapernum"  value="<%=dbox01.getString("d_sulpapernum")%>">
        <input type="hidden" name="p_sulpapernm"  value="<%=dbox01.getString("d_sulpapernm")%>">
        <input type="hidden" name="p_sulstart"  value="<%=dbox01.getString("d_sulstart")%>">
        <input type="hidden" name="p_sulend"  value="<%=dbox01.getString("d_sulend")%>">
</form>
                                <tr> 
                                  <td height=15><table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                                        <td  class=guidetitle>설문 리스트</td>
                                      </tr>
                                    </table></td>
                                </tr>
								<tr> 
                                        
                                  <td class=dirtext02 style=padding-left:6>설문명을 클릭하시면 설문에 참가 하실 수 있습니다.</td>
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
                                              <td width="50"  class=boardskin1_textb>설문번호</td>
                                              <td width="435"  class=boardskin1_textb>설문지명</td>
                                              <td width="88"  class=boardskin1_textb>문제수</td>
                                              <td width="162"  class=boardskin1_textb>설문기간</td>
                                            </tr>

<%
        for (int i=0; i<blist.size(); i++) {
			count++;
            DataBox dbox = (DataBox)blist.get(i);   
    
%>
											<tr> 
											  <td  class=boardskin1_textn><%=String.valueOf(dbox.getInt("d_sulpapernum"))%></td>
                                              <td  class=boardskin1_textn><a href="javascript:commonWright()"><%=String.valueOf(dbox.getString("d_sulpapernm"))%></a></td>
                                              <td  class=boardskin1_textn><%=dbox.getInt("d_totcnt")%></td>
                                              <td  class=boardskin1_textn><%=FormatDate.getFormatDate(dbox.getString("d_sulstart"),"yyyy-MM-dd")%> ~ <%=FormatDate.getFormatDate(dbox.getString("d_sulend"),"yyyy-MM-dd")%></td>
											<tr> 
<%    
        } 
%>

<%
         ArrayList blist1 = (ArrayList)request.getAttribute("SulmunTargetUserList");
		 DataBox dbox0 = null;
		 if(blist1.size() > 0){
		     dbox0 = (DataBox)blist1.get(0);
		 }else{
		     dbox0 = new DataBox("resoponsebox");
		 }
%>
 <form name="form2" method="post" action="/servlet/controller.research.SulmunTargetUserServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=dbox0.getString("d_subj")%>">
        <input type="hidden" name="p_grcode"  value="<%=dbox0.getString("d_grcode")%>">
        <input type="hidden" name="p_gyear"  value="<%=dbox0.getString("d_year")%>">
        <input type="hidden" name="p_subjseq"  value="<%=dbox0.getString("d_subjseq")%>">
        <input type="hidden" name="p_sulpapernum"  value="<%=dbox0.getString("d_sulpapernum")%>">
        <input type="hidden" name="p_sulpapernm"  value="<%=dbox0.getString("d_sulpapernm")%>">
        <input type="hidden" name="p_sulstart"  value="<%=dbox0.getString("d_sulstart")%>">
        <input type="hidden" name="p_sulend"  value="<%=dbox0.getString("d_sulend")%>">
</form>
<%
        for (int i=0; i<blist1.size(); i++) {
			count++;
            DataBox dbox1 = (DataBox)blist1.get(i);   
    
%>
											<tr> 
											  <td  class=boardskin1_textn><%=String.valueOf(dbox1.getInt("d_sulpapernum"))%></td>
                                              <td  class=boardskin1_textn><a href="javascript:targetWright()"><%=String.valueOf(dbox1.getString("d_sulpapernm"))%></a></td>
                                              <td  class=boardskin1_textn><%=dbox1.getInt("d_totcnt")%></td>
                                              <td  class=boardskin1_textn><%=FormatDate.getFormatDate(dbox1.getString("d_sulstart"),"yyyy-MM-dd")%> ~ <%=FormatDate.getFormatDate(dbox1.getString("d_sulend"),"yyyy-MM-dd")%></td>
											</tr> 
<%    
        } 
%>
                                                  <%
                                                  if(count == 0){ %>
                                                  <tr>
                                                    <td class=boardskin1_textn colspan="4">해당하는 설문이 없습니다.</td>
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