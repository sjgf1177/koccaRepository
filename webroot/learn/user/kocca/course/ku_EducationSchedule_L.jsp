<%
//**********************************************************
//  1. 제      목: 과정일정
//  2. 프로그램명:  ku_EducationSchedule_L.jsp
//  3. 개      요: 과정일정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","05");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");
    String  v_subjtype     = box.getStringDefault("p_subjtype", "alway");

    String  ss_year    = box.getStringDefault("s_year",FormatDate.getDate("yyyy"));

    String  v_upperclass      =  "";
    String  v_middleclass     =  "";
    String  v_lowerclass      =  "";
    String  v_isonoff         =  "";
    String  v_course          =  "";
    String  v_coursenm        =  "";
    String  v_subj            =  "";
    String  v_year            =  "";
    String  v_subjseq         =  "";
    String  v_subjseqgr       =  "";
    String  v_subjnm          =  "";
    String  v_classnm         =  "";
    String  v_propstart       =  "";
    String  v_scpropend       =  "";
    String  v_scpropstart     =  "";
    String  v_propend         =  "";
    int     v_biyong          =  0;
    String  v_eduperiod       =  "";
    int     v_subjcnt         = 0;

    String  v_isonoff_value   =  "";
    String  v_propstart_value =  "";
    String  v_propend_value   =  "";
    int     v_total           =  0;
    int     i                 =  0;
    String  v_be_course       = "";
    DecimalFormat  df = new DecimalFormat("###,##0");

    ArrayList list   = (ArrayList)request.getAttribute("EducationList");


    SelectEduBean selectEdu = new SelectEduBean();
    ArrayList yearList = selectEdu.getGyear(box);
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenTabSelect(subjtype){
      document.form1.p_subjtype.value = subjtype;
      document.form1.p_process.value  = 'EducationSchedule';
      document.form1.action='/servlet/controller.propose.KMainSubjSearchServlet';
      document.form1.target = "_self";
      document.form1.submit();
    }

    function whenSelect(year){
      document.form1.p_process.value  = 'EducationSchedule';
      document.form1.action='/servlet/controller.propose.KMainSubjSearchServlet';
      document.form1.target = "_self";
      document.form1.submit();
    }


    // 과정 내용보기
    function whenSubjInfo(subj,middleclass){
        document.form1.p_subj.value        = subj;
        document.form1.p_middleclass.value = middleclass;
        document.form1.p_process.value  = 'SubjectPreviewPage';
        document.form1.p_rprocess.value = 'SubjectListAlway';
        document.form1.action='/servlet/controller.course.KCourseIntroServlet';
        document.form1.target = "_self";
        document.form1.submit();
    }
//-->
</SCRIPT>

<form name="form1" method="post" >
    <input type='hidden' name='p_process'     value="<%=v_process%>">
    <input type='hidden' name='p_rprocess'    value="">
    <input type='hidden' name='p_subjtype'    value="<%=v_subjtype%>">
    <input type='hidden' name='p_subj'        value="">
    <input type='hidden' name='p_middleclass' value="">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 수강신청 &gt; 교육일정</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/apply/<%=tem_subimgpath%>/tit_dayplan.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td colspan="3">
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="117"><img src="/images/user/kocca/apply/day_ba_f.gif"></td>
                                  <td align="right"><img src="/images/user/kocca/apply/tab_apply_fir.gif" width="5" height="42"></td>
                                  <td width="81">
                                  <% if (v_subjtype.equals("alway")) {       %>
                                      <img src="/images/user/kocca/apply/tab_<%=v_subjtype%>_on.gif"><br>
                                  <% } else {                                %>
                                      <a href="javascript:whenTabSelect('alway')"><img src="/images/user/kocca/apply/tab_alway.gif" name="Image6" border="0" id="Image6" onMouseOver="MM_swapImage('Image6','','/images/user/kocca/apply/tab_alway_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a><br>
                                  <% }                                       %>
                                  </td>
                                  <td width="83">
                                  <% if (v_subjtype.equals("sometime")) {       %>
                                      <img src="/images/user/kocca/apply/tab_<%=v_subjtype%>_on.gif"><br>
                                  <% } else {                                %>
                                      <a href="javascript:whenTabSelect('sometime')"><img src="/images/user/kocca/apply/tab_sometime.gif" name="Image7" border="0" id="Image7" onMouseOver="MM_swapImage('Image7','','/images/user/kocca/apply/tab_sometime_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a><br>
                                  <% }                                       %>
                                  </td>
                                  <td width="95">
                                  <% if (v_subjtype.equals("prof")) {       %>
                                      <img src="/images/user/kocca/apply/tab_<%=v_subjtype%>_on.gif"><br>
                                  <% } else {                                %>
                                      <a href="javascript:whenTabSelect('prof')"><img src="/images/user/kocca/apply/tab_prof.gif" name="Image8" border="0" id="Image8" onMouseOver="MM_swapImage('Image8','','/images/user/kocca/apply/tab_prof_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a><br>
                                  <% }                                       %>
                                  </td>
                                  <td width="94">
                                  <% if (v_subjtype.equals("workshop")) {       %>
                                      <img src="/images/user/kocca/apply/tab_<%=v_subjtype%>_on.gif"><br>
                                  <% } else {                                %>
                                      <a href="javascript:whenTabSelect('workshop')"><img src="/images/user/kocca/apply/tab_workshop.gif" name="Image9" border="0" id="Image9" onMouseOver="MM_swapImage('Image9','','/images/user/kocca/apply/tab_workshop_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a><br>
                                  <% }                                       %>
                                  </td>
                                  <td width="99">
                                  <% if (v_subjtype.equals("recognition")) {       %>
                                      <img src="/images/user/kocca/apply/tab_<%=v_subjtype%>_on.gif"><br>
                                  <% } else {                                %>
                                      <a href="javascript:whenTabSelect('recognition')"><img src="/images/user/kocca/apply/tab_recognition.gif" name="Image10" border="0" id="Image10" onMouseOver="MM_swapImage('Image10','','/images/user/kocca/apply/tab_recognition_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a><br>
                                  <% }                                       %>
                                  </td>
                                  <td width="5"><img src="/images/user/kocca/apply/tab_tail.gif" width="5" height="42"></td>
                                  <td width="10">&nbsp;</td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr> 
                            <td colspan="3"><img src="/images/user/kocca/apply/i_apply_top01.gif" width="680" height="24"></td>
                          </tr>
                          <tr> 
                            <td width="22" valign="top"><img src="/images/user/kocca/apply/i_apply_left.gif"></td>
                            <td width="631" valign="top"><table width="631" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/kocca/apply/sst_<%=v_subjtype%>.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr> 
                                  <td><img src="/images/user/kocca/apply/edu_plan_timg.gif"></td>
                                </tr>
                              </table></td>
                            <td width="27" valign="top"><img src="/images/user/kocca/apply/i_apply_right.gif"></td>
                          </tr>
                          <tr> 
                            <td height="1" colspan="3" valign="top">&nbsp;</td>
                          </tr>
                        </table>
                        <!--교육년도 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="35">
                              <select name="s_year" onChange="whenSelect('v_select')" >
                        <%
                            String yearStr = "";
                            for(int k = 0; k < yearList.size(); k++) {
                                DataBox dbox = (DataBox)yearList.get(k);
                                yearStr += "<option value = '"+ dbox.getString("d_gyear")+"' ";
                                if (ss_year.equals(dbox.getString("d_gyear"))) {
                                  yearStr += " selected";
                                }
                                yearStr += " >";
                                yearStr += dbox.getString("d_gyear");
                                yearStr += "</option>\n";
                            }
                            out.print(yearStr);
                        %>
                              </select>
                            </td>
                          </tr>
                          <tr> 
                            <td height="2"></td>
                          </tr>
                        </table>
                        <!--교육일정 -->
                        <table width="680" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr> 
                            <td height="3" colspan="6" class="linecolor_app"></td>
                          </tr>
                          <tr> 
                            <td class="tbl_otit2" colspan="2">과정명</td>
                            <td width="30" class="tbl_otit">차수<br></td>
                            <td width="60"  class="tbl_otit2">교육비</td>
                            <td width="150"  class="tbl_otit">수강신청기간</td>
                            <td width="50"  class="tbl_otit2">교육기간</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6" ></td>
                          </tr>
<%

                    v_total = list.size();
                    for(i = 0; i < v_total; i++) {
                        DataBox dbox = (DataBox)list.get(i);
                        v_upperclass      = dbox.getString("d_scupperclass"); 
                        v_middleclass     = dbox.getString("d_scmiddleclass");
                        v_lowerclass      = dbox.getString("d_sclowerclass");
                        v_isonoff         = dbox.getString("d_isonoff"); 
                        v_course          = dbox.getString("d_course"); 
                        v_coursenm        = dbox.getString("d_scsubjnm");
                        v_subj            = dbox.getString("d_scsubj");
                        v_year            = dbox.getString("d_scyear");
                        v_subjseq         = dbox.getString("d_scsubjseq");
                        v_subjseqgr       = dbox.getString("d_subjseqgr");
                        v_subjnm          = dbox.getString("d_subjnm");
                        v_classnm         = dbox.getString("d_classname");
                        v_propstart       = dbox.getString("d_propstart");
                        v_propend         = dbox.getString("d_propend");
                        v_scpropstart     = dbox.getString("d_scpropstart");
                        v_scpropend       = dbox.getString("d_scpropend");
                        v_biyong          = dbox.getInt   ("d_scbiyong");
                        v_eduperiod       = dbox.getString("d_eduperiod");
                        v_subjcnt         = dbox.getInt   ("d_subjcnt");

                        if(v_isonoff.equals("ON")) v_isonoff_value = "사이버";
                        else                       v_isonoff_value = "집합";

                        //날짜 처리
                        if(v_propstart.equals("")) {v_propstart = " - ";}
                        else                       {v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");}

                        if(v_propend.equals(""))   {v_propend = " - ";}
                        else                       {v_propend = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");}
%>
                          <tr> 
<%
                        if (!v_course.equals("000000")) {
                            if (v_be_course.equals(v_course)) {
%>
                            <td class="tbl_bleft" ><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_middleclass%>')"><%=v_subjnm%></a></td>
                            <td class="tbl_grc"><%=v_eduperiod%>주</td>
<%
                            } else {
                                v_be_course = v_course;
%>
                            <td class="tbl_bleft" rowspan="<%=v_subjcnt%>"><%=v_coursenm%></td>
                            <td class="tbl_bleft" ><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_middleclass%>')"><%=v_subjnm%></a></td>
                            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=StringManager.toInt(v_subjseq)%></td>
                            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=df.format(v_biyong)%></td>
                            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_scpropstart%> ~ <%=v_scpropend%></td>
                            <td class="tbl_grc"><%=v_eduperiod%>주</td>
<%
                            }
                        } else {
%>
                            <td class="tbl_bleft" colspan="2"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_middleclass%>')"><%=v_subjnm%></a></td>
                            <td class="tbl_grc"><%=StringManager.toInt(v_subjseqgr)%></td>
                            <td class="tbl_grc"><%=df.format(v_biyong)%></td>
                            <td class="tbl_grc"><%=v_propstart%> ~ <%=v_propend%></td>
                            <td class="tbl_grc"><%=v_eduperiod%>주</td>
<%
                        }
%>
                          </tr>
<%
                    }

                    if(v_total == 0) {
%>
                          <tr>
                            <td class="tbl_grc" colspan="5">개설 과정이 없습니다.</td>
                          </tr>
<%          
                    }
%>
                        </table>

</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->