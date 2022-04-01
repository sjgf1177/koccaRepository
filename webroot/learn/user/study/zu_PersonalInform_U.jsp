<%
//**********************************************************
//  1. 제      목: PERSONAL INFORM UPDATE PAGE
//  2. 프로 그램명: zu_PersonalInform_U.jsp
//  3. 개      요: 개인교육이력
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_userid        = box.getSession("userid"); //ID
    String  v_cono          = "";               // 사번
    String  v_name          = "";               // 이름
    String  v_pwd           = "";               // 비밀번호
    String  v_comp          = "";               // 회사ID
    String  v_companynm     = "";               // 회사명
    String  v_gpmnm         = "";               // 사업부명
    String  v_deptnm        = "";               // 부서명
    String  v_partnm        = "";               // 팀명
    String  v_jikwi         = "";               // 직위
    String  v_jikwinm       = "";               // 직위명
    String  v_hometel       = "";               // 집전화번호
    String  v_hometel1      = "";
    String  v_hometel2      = "";
    String  v_hometel3      = "";
    String  v_handphone     = "";               // 휴대전화번호
    String  v_handphone1    = "";
    String  v_handphone2    = "";
    String  v_handphone3    = "";
    String  v_comptel       = "";               // 회사전화번호
    String  v_tel_line       = "";               //회사내선번호
    String  v_ismailing     = "";               // 기본메일설정
    String  v_email         = "";               // email
    String  v_post1         = "";               // 우편1
    String  v_post2         = "";               // 우편2
    String  v_addr          = "";               // 주소
    String  v_emailkind     = "";
    String  v_comtel        = "";
    
    int     idx             =  0;
    MyClassData  data       = null;
    data = (MyClassData)request.getAttribute("PersonalInform");
    v_cono                  = data.getCono();
    v_name                  = data.getName();
    v_pwd                   = data.getPwd();
    v_comp                  = data.getComp();
    v_companynm             = data.getCompanynm();
    v_gpmnm                 = data.getGpmnm();
    v_deptnm                = data.getDeptnm();
    v_partnm                = data.getPartnm();
    v_jikwi                 = data.getJikwi();
    v_jikwinm               = data.getJikwinm();
    v_hometel               = data.getHometel();
    v_handphone             = data.getHandphone();
    v_comptel               = data.getComptel();
    v_ismailing             = data.getIsmailing();
    v_email                 = data.getEmail();
    v_post1                 = data.getPost1();
    v_post2                 = data.getPost2();
    v_addr                  = data.getAddr();
	v_tel_line				= data.getTel_Line();
	v_comtel                = data.getComptel();

    //int v_hlen =0;
    //v_hlen = v_hometel.length();
    //if(v_hlen >0){
    //    if( v_hlen >4) {    v_hometel3 = StringManager.rightstring(v_hometel,4) ;   }
    //    idx =2;
    //    if( v_hlen> idx) { 
    //        if ((StringManager.substring(v_hometel,0,idx)).equals("02")){
    //            idx =2 ;
    //            v_hometel1= v_hometel.substring(0,idx);
    //        }else {
    //            idx = 3;
    //            if ( v_hlen > idx) { 
    //                v_hometel1= v_hometel.substring(0,idx);
    //            }
    //        }
    //    }
    //    if ( (v_hlen > idx) && ((v_hlen-4) >0) ) {
    //        v_hometel2 = v_hometel.substring(idx, (v_hlen-4 ));
    //    }
/*
        idx = v_hometel.indexOf("-");
        v_hometel1      = v_hometel.substring(0, idx);
        v_hometel       = v_hometel.substring(idx+1);
        idx = v_hometel.indexOf("-");
        v_hometel2      = v_hometel.substring(0, idx);
        v_hometel3      = v_hometel.substring(idx+1);
*/
    //}

    //idx =0;
    //int v_hdlen = v_handphone.length();
    //if(v_hdlen > 0){
    //    //국번 :3자리, 마지막번호 : 4자리
    //    idx = 3;
    //    if ( v_hdlen > idx) { 
    //        v_handphone1 = StringManager.substring(v_handphone,0,idx);
    //    }
    //    if( v_hdlen >4) {    v_handphone3 = StringManager.rightstring(v_handphone,4) ;   }
	//	if ( (v_hdlen >3) && ((v_hdlen-4) > 0) ) {
	//		v_handphone2 = v_handphone.substring(idx,(v_hdlen-4));
	//	}

/*
        idx = v_handphone.indexOf("-");
        v_handphone1    = v_handphone.substring(0, idx);
        v_handphone     = v_handphone.substring(idx+1);
        idx = v_handphone.indexOf("-");
        v_handphone2    = v_handphone.substring(0, idx);
        v_handphone3    = v_handphone.substring(idx+1);
*/
    //}


%>
<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" href="/css/style_myhome.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//우편번호 검색
function searchPost() {
    window.self.name = "personalInform";
    open_window("openPost","","100","100","367","500","","","","yes","");
    document.form1.target = "openPost";
	document.form1.action='/servlet/controller.library.PostSearchServlet';
    document.form1.p_process.value = 'SearchPostOpenPage';
    document.form1.submit();
}

// 개인정보 수정
function whenPersonalUpdate(){
    form = document.form1;
    if (blankCheck(form.p_pwd1.value)) {
        alert("비밀번호를 입력하세요!");
        form.p_pwd1.focus();
        return;
    }
    if (blankCheck(form.p_pwd2.value)) {
        alert("비밀번호를 입력하세요!");
        form.p_pwd2.focus();
        return;
    }
    if (!(form.p_pwd1.value == form.p_pwd2.value)){
        alert("비밀번호 확인이 일치하지 않습니다");
        form.p_pwd2.value = "";
        form.p_pwd2.focus();
        return;
    }
    if (document.form1.p_pwd1.value.length < 4 || document.form1.p_pwd1.value.length > 8) {
        alert("비빌번호는 4 ~ 8자리 사이입니다.");
        document.form1.p_pwd1.focus();
        return;
    }
    
    if (blankCheck(form.p_comtel.value)) {
        alert("사내전화를 등록하여주십시요");
        form.p_comtel.focus();
        return;
    }
    
    if (blankCheck(form.p_handphone.value)) {
        alert("핸드폰번호를 등록하여 주십시요.");
        document.form1.p_handphone.focus();
        return;
    }
    <%if(v_email.equals("")){%>
    if (blankCheck(form.p_email.value)) {
        alert("email ID를 입력하여 주십시요.");
        document.form1.p_email.focus();
        return;
    }
    if (blankCheck(form.p_emailkind.value)) {
        alert("사용할Email종류를 선택하여 주십시요");
        document.form1.p_email.focus();
        return;
    }
    <%}%>
    form.target = "_self";
    form.action = "/servlet/controller.study.MyClassServlet";
    form.p_process.value = "PersonalInformUpdate";
    form.submit();
}

// CANCEL
function cancel(process) {
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.study.MyClassServlet";
    document.form1.p_process.value = process;
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
						
						<!-- 검색 끝 -->
						<!-- 본문 시작 -->
						<TABLE cellpadding="0" cellspacing="0" border="0" width="735">
							<tr>
								
                <td class="bodypd"> 
                  <!-- 현재 위치 시작 -->
                  <TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
    								    <tr>
    								        <td align="right" class="location"><img src="/images/common/bullet_location.gif"> <a href="http://www.hkhrd.com">HOME</a> > <a href="http://www.hkhrd.com/learn/user/study/myhome_main.jsp">마이홈</a> > 개인정보</td>
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
                            <td><img src="/images/user/myhome/title_study_03.gif"></td>
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
                      <td> <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                          <tr> 
                            <td><table border="0" cellpadding="0" cellspacing="0" width="735">
                            <form name= "form1" method= "post">
                              <input type='hidden' name='p_process'>
                                <tr> 
                                  <td class="boardskin1_color" height="3"></td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td><table width="735" border="0" cellspacing="1" cellpadding="0" class=boardskin1_out >
                                      <tr> 
                                        <td width="112" class=boardskin1_textb_r>사번</td>
                                        <td width="620" class=boardskin1_textn_l><%=v_userid%></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>이름(한글)</td>
                                        <td class=boardskin1_textn_l><%=v_name%></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>*비밀번호</td>
                                        <td class=boardskin1_textn_l><input name="p_pwd1" type="password" class="input" style="width:150" value="<%=v_pwd%>"></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>*비밀번호확인</td>
                                        <td class=boardskin1_textn_l><input name="p_pwd2" type="password" class="input" style="width:150" value="<%=v_pwd%>"></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>회사</td>
                                        <td class=boardskin1_textn_l><%=v_companynm%></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>부서</td>
                                        <td class=boardskin1_textn_l><%=v_deptnm%></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>직위</td>
                                        <td class=boardskin1_textn_l><%=v_jikwinm %></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>*사내전화</td>
                                        <td class=boardskin1_textn_l><input name="p_comtel" type="text" class="input" style="width:150" maxlength="25" value="<%=v_comtel%>"></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>핸드폰</td>
                                        <td class=boardskin1_textn_l><input name="p_handphone" type="text" class="input" style="width:150"  maxlength="25" value='<%=v_handphone%>'></td>
                                      </tr>
                                      <tr> 
                                        <td class=boardskin1_textb_r>현재입력된  e-mail</td>
                                        <td class=boardskin1_textn_l><%=v_email%> 
                                        </td>
                                      </tr>
                                      <%
                                      //if(v_email.equals("")){//이메일이 없을 경우에만 수정가능하도록
                                      %>
                                      <tr> 
                                        <td class=boardskin1_textb_r>e-mail입력</td>
                                        <td class=boardskin1_textn_l><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="20%"><input name="p_email" type="text" class="input" style="width:100"> @</td>
                                              <td width="80%">
                                              <%
                                                String emailStr = "";
                                                emailStr  = " <SELECT name='p_emailkind' style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 120px;height:19px;font-size:9pt;'>";
                                                emailStr += " <option value=''>선택하세요!!</option>";
                                                emailStr += " <option value='hyundai-motor.com'>hyundai-motor.com</option>";
                                                emailStr += " <option value='kia.co.kr'>kia.co.kr</option>";
                                                emailStr += " <option value='hmc.co.kr'>hmc.co.kr</option>";
                                                emailStr += " </SELECT> ";
                                              %>
                                              <script>NV_write("<%=emailStr%>", 10);</script></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <%//}%>
                                    </table></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td class="boardskin1_bottom"></td>
                          </tr>
		<tr> 
			<tr> 
                                  <td height="6"></td>
                                </tr>
                      <td align="right"><table  border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><a href="javascript:whenPersonalUpdate()" onfocus=this.blur()><IMG src="/images/user/course/btn_save.gif" alt="저장" border="0"></a></td>
                            <td>&nbsp;</td>
                            <td><!--a href="#" onfocus=this.blur()><IMG src="/images/board/b_cancel.gif" border="0"></a--></td>
                          </tr>
                        </table>
                        </form>
                        </td>
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
			<jsp:include page="/incmenu/footer.jsp" flush="true">
				<jsp:param name="sUrl" value="<%=request.getServletPath().toString()%>"/>
			</jsp:include>
			<!-- FOOTER 끝 -->
		</td>
	</tr>
</TABLE>
</BODY>
</HTML>