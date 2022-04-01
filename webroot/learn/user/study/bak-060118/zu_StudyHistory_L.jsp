<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_EducationSubject_L.jsp
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
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "MyClassBean" class = "com.credu.study.MyClassBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_user_id   = box.getSession("userid");
    String  v_process   = box.getString("p_process");
    String  v_selectcode= box.getStringDefault("p_selectcode", "ALL");
    String  v_upperclass=  "";
    String  v_isonoff   =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_subjseqgr   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_place     =  "";
    String  v_edu_org_nm=  "";
    String  v_kind      =  "";
    String  v_isgraduated= "";
    String  v_isnewcourse= "";
    String  v_isonoff_value="";
    String  v_kind_value =  "";
    String  v_serno      = "";
    
    double v_credit      = 0;
    double v_creditexam  = 0;
    
    int     v_rowspan   =  0;
    int     v_rowspan_cnt= 0;
    int     v_total     =  0;
    int     i           =  0;
    ArrayList list      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("StudyHistoryList");
    
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" href="/css/style_myhome.css" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
  function selectMoveTab(sel){
      document.form1.target = "_self";
      document.form1.action = "/servlet/controller.study.MyClassServlet";
      document.form1.p_process.value = 'StudyHistoryList';
      document.form1.p_sel.value = sel;
      document.form1.submit();
  }

  function go_lexamhstUser(){
      document.form1.target = "_self";
      document.form1.p_process.value = 'selectStudyHistoryLang';
      document.form1.action = "/servlet/controller.language.LexamHstUserServlet";
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
    								        <td align="right" class="location"><img src="/images/common/bullet_location.gif"> <a href="http://www.hkhrd.com">HOME</a> > <a href="http://www.hkhrd.com/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage">마이홈</a> > 개인교육이력</td>
    								    </tr>
    								    <tr height="3">
    								        <td></td>
    								    </tr>
    								</table>
                  <!-- 현재 위치 끝 -->
                  <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                  <form name= "form1" method= "post">
                    <input type='hidden' name='p_process'>
                    <input type='hidden' name='p_sel'>
                    <tr> 
                      <td> <TABLE cellpadding="0" cellspacing="0" border="0">
                          <tr> 
                            <td><IMG src="/images/common/title_bullet.gif" border="0"></td>
                            <td><img src="/images/user/myhome/title_study_02_02.gif"></td>
                          </tr>
                        </TABLE></td>
                    </tr>
                    <tr height="1"> 
                      <td bgcolor="DAD8D8"></td>
                    </tr>
                    <tr height="20"> 
                      <td></td>
                    </tr>
                    <tr> 
                      <td> <TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                          <tr> 
                            <td><TABLE cellpadding="0" cellspacing="0" border="0" width="735">
                                <tr height="35"> 
                                  <td bgcolor="F7F3EF" style="padding-left:5"> 
                                    <TABLE cellpadding="0" cellspacing="0" border="0">
                                      <tr> 
                                        <td><A HREF="javascript:selectMoveTab('StudyHistoryTot')" onfocus=this.blur()><IMG src="/images/user/myhome/study_tab01.gif" border="0"></A></td>
                                        <td><A HREF="javascript:selectMoveTab('StudyHistory')" onfocus=this.blur()><IMG src="/images/user/myhome/study_tab02_on.gif" border="0"></a></td>
                                        <td><!--A HREF="javascript:go_lexamhstUser();" onfocus=this.blur()><IMG src="/images/user/myhome/study_tab03.gif" border="0"--></A></td>
                                        <!--td><IMG src="/images/user/myhome/study_tab04.gif" border="0"></A></td-->
                                      </tr>
                                    </TABLE></td>
                                </tr>
                                <tr> 
                                  <td height="6"></td>
                                </tr>
								<tr> 
                                  <td>
                                    <table width="735" height="120" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td width=171><img src="/images/user/myhome/personinfo_head.gif" border="0"></td>
                                        <td width=529 background="/images/user/myhome/personinfo_bg.gif"><table width="529" border="0" cellspacing="1" cellpadding="0">
                                            <tr> 
                                              <td width="15">&nbsp;</td>
                                              <td width="265">&nbsp;</td>
                                              <td width="15">&nbsp;</td>
                                              <td width="235">&nbsp;</td>
                                            </tr>
                                            <%
                                              Hashtable outputData = new Hashtable();
                                              outputData = probean.getMeberInfo(v_user_id);
                                              
                                              String v_comp = (String)outputData.get("comp");
                                              String v_jikup = (String)outputData.get("jikup");
                                              String v_cono = (String)outputData.get("cono");
                                              String v_resno = (String)outputData.get("resno");
                                              String v_jikwi = (String)outputData.get("jikwi");
                                              String v_name = (String)outputData.get("name");
                                              String v_email = (String)outputData.get("email");
                                              String v_enterdt = (String)outputData.get("enterdt");
                                              String v_jikwinm = (String)outputData.get("jikwinm");
                                              String v_divinam = (String)outputData.get("divinam");
                                              String v_deptnam = (String)outputData.get("deptnam");
                                              String v_prmtdat = (String)outputData.get("prmtdat");
                                              String v_work_plc = (String)outputData.get("work_plc");
                                              String v_work_plcnm = (String)outputData.get("work_plcnm");
                                              String v_syear     = (String)outputData.get("syear");
                                              String v_prmtyy     = (String)outputData.get("prmtyy");
                                            %>
                                            <tr> 
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>사&nbsp;&nbsp;&nbsp;번&nbsp;:&nbsp;<%=v_cono%></td>
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름&nbsp;:&nbsp;<%=v_name%></td>
                                            </tr>
                                            <tr> 
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>소&nbsp;&nbsp;&nbsp;속&nbsp;:&nbsp;<%=v_divinam%>&nbsp;<%=v_deptnam%></td>
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>직&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;위&nbsp;:&nbsp;<%=v_jikwinm%></td>
                                            </tr>
                                            <tr> 
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>입사일&nbsp;:&nbsp;<%=FormatDate.getFormatDate(v_enterdt,"yyyy/MM/dd")%></td>
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>승진기준일&nbsp;:&nbsp;<%=FormatDate.getFormatDate(v_prmtdat,"yyyy/MM/dd")%></td>
                                            </tr>
                                            <tr> 
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>년&nbsp;&nbsp;&nbsp;차&nbsp;:
                                              <%
                                              if( ( box.getSession("usergubun").equals("RH") || box.getSession("usergubun").equals("RK") ) && !v_prmtyy.equals("") ){
                                                out.println(StringManager.cutZero(v_prmtyy));
                                              }else if( !(box.getSession("usergubun").equals("RH")||box.getSession("usergubun").equals("RK")) && !v_syear.equals("")){
                                                out.println(StringManager.cutZero(v_syear));
                                              }else{
                                                out.println("-");
                                              }
                                              %>
                                              </td>
                                              <td><img src="/images/user/myhome/bullet_02.gif" border="0"></td>
                                              <td>근&nbsp;&nbsp;&nbsp;무&nbsp;&nbsp;&nbsp;지&nbsp;:&nbsp;<%=v_work_plcnm%></td>
                                            </tr>
                                            <tr> 
                                              <td></td>
                                              <td>&nbsp;</td>
                                              <td>&nbsp;</td>
                                              <td>&nbsp;</td>
                                            </tr>
                                          </table></td>
                                        <td width=35><img src="/images/user/myhome/personinfo_tail.gif" border="0"></td>
                                      </tr>
                                    </table>
									</td>
                                </tr>
								<tr> 
                                  <td height="8"></td>
                                </tr>
                                <tr> 
                                  <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width=130><img src="/images/user/myhome/point_head.gif" ></td>
                                        <td background="/images/user/myhome/point_boxbg.gif"><table width="568" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                            <%
                                            DataBox dbox1 = (DataBox)request.getAttribute("promotionscroe");
                                            double v_sumcredit      = 0;
                                            double v_sumcreditexam  = 0;
                                            //out.print(dbox1);
                                            if(dbox1 != null){
                                              v_sumcredit     = dbox1.getDouble("d_sumcredit");
                                              v_sumcreditexam = dbox1.getDouble("d_sumcreditexam");
                                            }
                                            %>
                                              <td width="60"><img src="/images/user/myhome/point_t01.gif" ></td>
                                              <td width=60><%=v_sumcredit%></td>
                                              <td width="60"><img src="/images/user/myhome/point_t02.gif" ></td>
                                              <td width=60><%=v_sumcreditexam%></td>
                                              <td width="39"><img src="/images/user/myhome/point_t03.gif" ></td>
                                              <td width="79"><%=v_sumcredit+v_sumcreditexam%></td>
                                              <td width="106"><img src="/images/user/myhome/point_s_head.gif" ></td>
                                              <td width="85" class=upoint><%=v_sumcredit+v_sumcreditexam%></td>
                                              <td width="18"><img src="/images/user/myhome/point_s_tail.gif" ></td>
                                            </tr>
                                          </table></td>
                                        <td width=37><img src="/images/user/myhome/point_tail.gif" ></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                                
                                
                                <tr> 
                                  <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                                        <td  class=guidetitle >세부내역</td>
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
                                              <td width="79" class=boardskin1_textb>직위</td>
                                              <td width="221" class=boardskin1_textb>과정명</td>
                                              <td width="192" class=boardskin1_textb>교육기간</td>
                                              <td width="83" class=boardskin1_textb>수료점수</td>
                                              <td width="93" class=boardskin1_textb>평가점수<br>(과정평가점수)</td>
                                              <td width="60" class=boardskin1_textb>계</td>
                                            </tr>
                                            <%    //double v_score = 0;
                                            double v_ftest = 0;
                                            for(i = 0; i < list.size(); i++) {
                                              DataBox dbox = (DataBox)list.get(i);
                                              
                                              v_isonoff     = dbox.getString("d_isonoff");
                                              v_course      = dbox.getString("d_course");
                                              v_cyear       = dbox.getString("d_cyear");
                                              v_subj        = dbox.getString("d_subj");
                                              v_subjseqgr   = dbox.getString("d_subjgr");
                                              v_year        = dbox.getString("d_year");
                                              v_subjseq     = dbox.getString("d_subjseq");
                                              v_subjnm      = dbox.getString("d_subjnm");
                                              v_edustart    = dbox.getString("d_edustart");
                                              v_eduend      = dbox.getString("d_eduend");
                                              v_credit      = dbox.getDouble("d_credit");
                                              v_creditexam  = dbox.getDouble("d_creditexam");
                                              v_isgraduated = dbox.getString("d_isgraduated");
                                              v_jikwinm     = dbox.getString("d_jikwinm");
                                              v_serno       = dbox.getString("d_serno");
                                              v_ftest       = dbox.getDouble("d_ftest");

                                              v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                                              v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

                                                if(v_isonoff.equals("ON")){   v_kind_value =   "사이버";
                                                }
                                                else if(v_isonoff.equals("OFF")){  v_kind_value =   "집합";
                                                }
                                                else{v_kind_value =   "";}

                                                if(v_isgraduated.equals("Y")){      v_isgraduated = "이수";
                                                }else if(v_isgraduated.equals("N")){v_isgraduated = "미이수";       }

                                                //if(v_score == 0){    v_score="-";   }
                                                System.out.println("score="+dbox.getDouble("d_score"));
                                                
                                            %>
                                            <tr> 
                                              <td height="12" class=boardskin1_textn><%=v_jikwinm%></td>
                                              <td class=boardskin1_textn><%=v_subjnm%></td>
                                              <td class=boardskin1_textn><%=v_edustart%>~<%=v_eduend%></td>
                                              <td class=boardskin1_textn><%=v_credit%></td>
                                              <td class=boardskin1_textn><%=v_creditexam%>(<%=v_ftest%>)</td>
                                              <td class=boardskin1_textn><%=(v_credit+v_creditexam)%></td>
                                            </tr>
                                            <%
                                              }if(i==0){
                                            %>
                                            <tr> 
                                              <td height="12" class=boardskin1_textn colspan="8">이수한 교육이력이 존재하지 않습니다.</td>
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
                                
                                <tr> 
                                  <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width=10 style=padding-left:6><img src="/images/user/myhome/bullet_03.gif" ></td>
                                        <td  class=guidetitle >제도안내</td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td height=6><table width="735" border="0" cellspacing="2" cellpadding="0">
                                      <tr> 
                                        <td width="34" align=right><img src="/images/user/myhome/bullet_02.gif" ></td>
                                        <td width="701">교육점수제는 '일반직'에 해당됩니다</td>
                                      </tr>
                                      <tr> 
                                        <td align=right><img src="/images/user/myhome/bullet_02.gif" ></td>
                                        <td>차기 승진시 적용되는 교육점수는 <font color="EA2828">최대 
                                          15점</font>입니다. </td>
                                      </tr>
                                      <tr> 
                                        <td align=right>&nbsp;</td>
                                        <td> <font color="286CEA">(단, 5급->4급 승진대상은 
                                          '승진후보과정'5점만 적용)</font></td>
                                      </tr>
                                      <tr> 
                                        <td align=right><img src="/images/user/myhome/bullet_02.gif" ></td>
                                        <td>교육점수 반영 과정 및 기준 => 현 직급내에서 수강한 3개 
                                          과정에 한하여 인정됨.</td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td height=6></td>
                                </tr>
                                <tr> 
                                  <td><table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td colspan=9 bgcolor=DCDCDB height=1></td>
                                      <tr align="center" bgcolor="F7F3EF"> 
                                        <td width="270" height="27"><font color="A3744F">구분</font></td>
                                        <td width="32" height="27"><font color="A3744F"><img src="/images/user/myhome/table_line.gif" ></font></td>
                                        <td width="87" height="27"><font color="A3744F">수료점수</font></td>
                                        <td width="33" height="27"><font color="A3744F"><img src="/images/user/myhome/table_line.gif" ></font></td>
                                        <td width="86" height="27"><font color="A3744F">평가점수</font></td>
                                        <td width="32" height="27"><font color="A3744F"><img src="/images/user/myhome/table_line.gif" ></font></td>
                                        <td width="80" height="27"><font color="A3744F">계</font></td>
                                        <td width="32" height="27"><font color="A3744F"><img src="/images/user/myhome/table_line.gif" ></font></td>
                                        <td width="83" height="27"><font color="A3744F">비고</font></td>
                                      </tr>
                                      <tr> 
                                        <td colspan=9 bgcolor=DCDCDB height=1></td>
                                    </table>
                                    <table width="735" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td  colspan=9 height="1" bgcolor="#FFFFFF"></td>
                                      </tr>
                                      <tr> 
                                        <td width="272" height="26" style=padding-left:20px>1.신임과정</td>
                                        <td width="1" height="26" ><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="124" height="26" align="center">4점</td>
                                        <td width="1" height="26" ><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="120" height="26" align="center">1점</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="113" height="26" align="center">5점</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="102" height="26" align="center">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td  colspan=9 height="1" bgcolor="#DAD8D8"></td>
                                      </tr>
                                      <tr bgcolor="F9F9F9"> 
                                        <td width="272" height="26" style=padding-left:20px>2.향상과정</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="124" height="26" align="center">4점</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="120" height="26" align="center">1점</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="113" height="26" align="center">5점</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="102" height="26" align="center">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td  colspan=9 height="1" bgcolor="#DAD8D8"></td>
                                      </tr>
                                      <tr> 
                                        <td width="272" height="26" style=padding-left:20px>3.승진후보자과정</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="124" height="26" align="center">5점</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="120" height="26" align="center">-</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="113" height="26" align="center">5점</td>
                                        <td width="1" height="26"><img src="/images/user/myhome/table_line.gif" ></td>
                                        <td width="102" height="26" align="center">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td  colspan=9 height="1" bgcolor="#DAD8D8"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td class=dirtext02>(※ 평가점수는 과정평가 성적 60점이상에 
                                    대해서만 '1점' 부여됩니다.) </td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                                
                              </TABLE></td>
                          </tr>
                        </TABLE></td>
                    </tr>
                  </table> 
                  </form>
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