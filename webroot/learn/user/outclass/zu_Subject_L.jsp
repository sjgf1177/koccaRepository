<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개      요: 과정조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process     = box.getString("p_process");
    String  v_select      = box.getStringDefault("p_select","ON");
    String  v_gubun       = box.getStringDefault("p_gubun",ProposeCourseBean.WORK_CODE);
    String  v_userid			= box.getSession("userid");
    String  v_resno				= box.getString("p_resno");

    String  v_code       = "";
    String  v_codenm     = "";
    String  v_tab_bg     = "";
    String  v_tab_class  = "";



    String v_subj           = "";
    String v_scupperclass   = "";
    String v_scmiddleclass  = "";
    String v_isonoff        = "";
    String v_year           = "";
    String v_subjseq        = "";
    String v_subjnm         = "";
    String v_proposetype    = "";
    String v_usebook        = "";
    String v_isnew          = "";
    String v_ishit          = "";
    String v_isrecom        = "";
    String v_uclassnm       = "";
    String v_mclassnm       = "";

    String v_imgbook        = "";
    String v_imgNew         = "";
    String v_imgHit         = "";
    String v_imgRecom       = "";
    String v_Bscupperclass  = "";
    String v_Bscmiddleclass  = "";

    ArrayList list1      = null;
    ArrayList list2      = null;

    list1 = (ArrayList)request.getAttribute("UpperClassList");      // 직무, 어학 구분 리스트
    list2 = (ArrayList)request.getAttribute("SubjectList");         // 과정 리스트
    
    
%>
<!------- 메뉴시작 ------------>
<%@ include file="/learn/user/include/topOutClass.jsp"%>
<!------- 메뉴끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

// 직무 , 어학 선택
function whenSelect(sel,gubun){
    document.form1.p_select.value = sel;
    document.form1.p_isonoff.value = sel;
    document.form1.p_gubun.value   = gubun;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.outclass.OutClassServlet';
    document.form1.p_process.value = 'SubjectList';
    document.form1.submit();
}

// 과정상세정보
function whenSubjInfo(subj,subjnm,isonoff,upperclass,upperclassnm){
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.outclass.OutClassServlet';
    document.form1.submit();
}

// 전체과정보기
function whenSubjectListAll() {
    document.form1.p_process.value = 'SubjectListAll';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.outclass.OutClassServlet';
    document.form1.submit();
}


//-->
</SCRIPT>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='p_isonoff' value="ON">
    <input type='hidden' name='p_select'  value="<%=v_select%>">
    <input type='hidden' name='p_gubun'   value="<%=v_gubun%>">





    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_proposestart'>
    <input type='hidden' name='p_proposeend'>
    <input type='hidden' name='p_proposeyn'>
    <input type='hidden' name='p_proposetype'>
    <input type='hidden' name='p_purl'>



    <input type='hidden' name='p_subj'>
    <input type='hidden' name='p_subjnm'>
    <input type='hidden' name='p_upperclass' value=''>
  <input type='hidden' name='p_upperclassnm' >
    <input type='hidden' name='p_actionurl' value='/servlet/controller.outclass.OutClassServlet'>
    <input type='hidden' name='p_rprocess' >

          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="center" valign="top">
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/<%=site%>/tit_apply.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="2" valign="top" class="font_ex">수강신청 과목에 대한 
                        정보를 반드시 읽어보신 후 신청하시기 바랍니다.<br>
                        교육후에는 수강취소를 하실 수 없습니다.
                      </td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="2">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td colspan="2"> 

                        <!-- 과정구분탭 -->
                        <table width="675" border="0" cellspacing="0" cellpadding="0" bgcolor="EBF6EE">
                          <tr> 
                            <td height="5" colspan="4"></td>
                          </tr>
                          <tr> 
                            <td width="98" valign="top"><img src="/images/user/<%=site%>/tab_cyber_on.gif"> 
                            </td>
                            <td width="98" valign="top" ><a href="javascript:whenSelect('OFF','')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/<%=site%>/tab_jib_on.gif',1)"><img src="/images/user/<%=site%>/tab_jib.gif" name="Image37" border="0"></a></td>
                            <td width="98" >&nbsp;</td>
                            <td width="377" >&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="20" colspan="4"></td>
                          </tr>
                         
                        </table>

                        <!-- 과정분류탭 -->
                        <table width="675" border="0" cellpadding="0" cellspacing="0" bgcolor="EBF6EE">
                          <tr> 
<%
        // 직무, 어학 구분
        list1 = (ArrayList)request.getAttribute("UpperClassList");

        for(int k = 0; k < list1.size(); k++) {
            DataBox dbox = (DataBox)list1.get(k);
            v_code   = dbox.getString("d_code");
            v_codenm = dbox.getString("d_codenm");
            if (v_code.equals(v_gubun)) {
                v_tab_bg    = "tab_bg_on.gif";
                v_tab_class = "tbl_gtabon";
            } else {
                v_tab_bg    = "tab_bg.gif";
                v_tab_class = "tbl_gtaboff";
            }
%>

                            <td width="105"> 
                              <table width="105" border="0" cellpadding="0" cellspacing="0" background="/images/user/<%=site%>/<%=v_tab_bg%>">
                                <tr> 
                                  <td width="9" height="25"><img src="/images/user/<%=site%>/tab_head_on.gif"></td>
                                  <td width="87" class="<%=v_tab_class%>"><a href="javascript:whenSelect('<%=v_select%>','<%=v_code%>')"><%=v_codenm%></a></td>
                                  <td width="10" align="right"><img src="/images/user/<%=site%>/tab_tail_on.gif"></td>
                                </tr>
                              </table>
                            </td>
                            <td width="5">&nbsp;</td>
<%  
        }
%>
                            <td width="435" align="right"><a href="javascript:whenSubjectListAll()"><img src="/images/user/<%=site%>/btn_c_applylist2.gif"></a></td>
                          </tr>
                          <tr bgcolor="C8C8C8"> 
                            <td height="2" colspan="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2"></td>
                    </tr>

<%
                  //과정 리스트
                  int v_upclass_seq = 0;
                  int i = 0;
                  for(i = 0; i < list2.size(); i++) {
                      DataBox dbox = (DataBox)list2.get(i);

                      v_subj          = dbox.getString("d_subj");
                      v_scupperclass  = dbox.getString("d_scupperclass");
                      v_scmiddleclass = dbox.getString("d_scmiddleclass");
                      v_isonoff       = dbox.getString("d_isonoff");
                      v_subjnm        = dbox.getString("d_subjnm");
                      v_usebook       = dbox.getString("d_usebook");
                      v_isnew         = dbox.getString("d_isnew");
                      v_ishit         = dbox.getString("d_ishit");
                      v_isrecom       = dbox.getString("d_isrecom");
                      v_uclassnm      = dbox.getString("d_uclassnm");
                      v_mclassnm      = dbox.getString("d_mclassnm");

                      if (v_usebook.equals("Y")) v_imgbook = "&nbsp;<img src='/images/user/"+site+"/btn_book.gif'>";
                      else                       v_imgbook = "";
//v_isnew = "Y";v_ishit="Y";v_isrecom="Y";
                      if(v_isnew.equals("Y"))    v_imgNew = "&nbsp;<img src='/images/user/"+site+"/btn_snew.gif'>";
                      else                       v_imgNew = "";
                      if(v_ishit.equals("Y"))    v_imgHit = "&nbsp;<img src='/images/user/"+site+"/btn_shit.gif'>";
                      else                       v_imgHit = "";
                      if(v_isrecom.equals("Y"))  v_imgRecom = "&nbsp;<img src='/images/user/"+site+"/btn_srecom.gif'>";
                      else                       v_imgRecom = "";


                      // 중분류 코드가 틀린경우
                      if (!v_Bscmiddleclass.equals(v_scmiddleclass)) {
                          // 화면에서 왼쪽 TD
                          if (v_upclass_seq % 2 == 0) {
                              // 처음이 아닐때
                              if (v_upclass_seq != 0) {
%>
                            </td>
                          </tr>
                        </table>

                      </td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="2"></td>
                    </tr>

<%
                              } // END IF 처음이 아닐때
%>
                    <tr> 
                      <td width="337" align="center" valign="top"> 
<%
                          // 화면에서 오른쪽 TD
                          } else {
%>

                            </td>
                          </tr>
                        </table>

                     </td>
                      <td width="338" align="center" valign="top"> 

<%
                          }    // END IF  TD 위치에 따라

%>

                        <!-- 과정리스트 -->
                        <table width="317" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td class="lecturetitle"><img src="/images/user/<%=site%>/bl_lecture.gif" > <%=v_mclassnm%></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/<%=site%>/lecture_line.gif"></td>
                          </tr>
                          <tr> 
                            <td class="lecturelist">
<%
                          v_upclass_seq++;
                      }        // END IF 대분류 코드가 틀린경우
%>
                              <a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_scupperclass%>','<%=v_uclassnm%>')" title="<%=v_subjnm%>"><%=v_subjnm%><%=v_imgbook%><%=v_imgNew%><%=v_imgHit%><%=v_imgRecom%></a><br> 
<%
                    v_Bscmiddleclass = v_scmiddleclass;
                }   // END FOR

                if (i > 0 ) {
                    // 나머지 태그들 결합
                    if (v_upclass_seq % 2 == 0) {
%>
                            </td>
                          </tr>
                        </table>

                      </td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="2"></td>
                    </tr>

<%
                    } else {
%>
                            </td>
                          </tr>
                        </table>

                     </td>
                      <td width="338" align="center" valign="top">&nbsp; 
                      
                      </td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="2"></td>
                    </tr>
<%
                    }
                }
%>

                    <tr> 
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
</form>

              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
            </table>

          </td>
        </tr>
        <tr>
          <td class="mlcolor2">&nbsp;</td>
        </tr>
      </table>
     </td>
  </tr>
</table>
<!-- footer -->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
