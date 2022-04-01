<%
//**********************************************************
//  1. 제      목: 마이 액티비티 PAGE
//  2. 프로그램 명: ku_Activity_Study.jsp
//  3. 개      요: MY Activity - 학습과정별 접속통계
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 7. 25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","05");

    String  s_subj          = box.getString("subj");
    String  s_subjnm        = "";
    String  s_year          = box.getString("year");
    String  s_subjseq       = box.getString("subjseq");
    String  v_user_id       = box.getSession("userid");

    String  v_subj          = "";
    String  v_year          = "";
    String  v_subjnm        = "";
    String  v_subjseq       = "";
    String  v_eduurl        = "";
    String  v_cpsubj        = "";
    String  v_cpsubjseq     = "";
    String  v_isgraduated   = "";
    String  v_subjtarget    = "";
    String  v_isonoff_value = "";
    String  v_isonoff       = "";
    String  v_isoutsourcing = "";
    String  v_company       = "";

    String  v_total_time    = "";
    String  v_total_minute  = "";
    String  v_total_sec     = "";
    String  v_first_end     = "";
    String  v_lesson_count  = "0";
    String  v_tstep         = "0";

    String  v_menu          = "";
    String  v_menunm        = "";
    int     v_cnt           = 0;

    int     i               = 0;

    int     v_boardcnt      = 0;
    int     v_datacnt       = 0;
    int     v_qnacnt        = 0;
    int     v_examcnt       = 0;
    int     v_sulcnt        = 0;
    String  v_reportindate  = "";

    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    ArrayList list4     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("EducationSubjectList");
    list2 = (ArrayList)request.getAttribute("selectActivity");
    list3 = (ArrayList)request.getAttribute("selectLearningTime");
    list4 = (ArrayList)request.getAttribute("selectBoard");
    DataBox dbox5  = (DataBox)request.getAttribute("memberView");
  //  DataBox dbox5 = (DataBox)list5.get(0);

    // 최근학습일
    String date  = (String)request.getAttribute("studyDate");
    // 강의실 접근정보
    String count  = (String)request.getAttribute("studyCount");
    // 강의실 접근정보
    String step  = (String)request.getAttribute("studyStep");



    if(list3 != null && list3.size() > 0) {
        DataBox dbox3    = (DataBox)list3.get(0);
        v_total_time    = dbox3.getString("d_total_time");
        v_total_minute  = dbox3.getString("d_total_minute");
        v_total_sec     = dbox3.getString("d_total_sec");
        v_first_end     = dbox3.getString("d_first_end");
        v_lesson_count  = dbox3.getString("d_lesson_count");
        v_tstep         = dbox3.getString("d_tstep");
    }


    if(list4 != null && list4.size() > 0) {

        DataBox dbox4   = (DataBox)list4.get(0);
        v_boardcnt          =  dbox4.getInt("d_boardcnt");
        v_datacnt           =  dbox4.getInt("d_datacnt");
        v_qnacnt            =  dbox4.getInt("d_qnacnt");
        v_examcnt           =  dbox4.getInt("d_examcnt");
        v_sulcnt            =  dbox4.getInt("d_sulcnt");
        v_reportindate      =  dbox4.getString("d_reportindate");
    }
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topMyclass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<Script language="javascript">

    function whenSelectSubj(subj,year,subjseq) {
        document.form1.subj.value = subj;
        document.form1.year.value = year;
        document.form1.subjseq.value = subjseq;
        document.form1.action='/servlet/controller.system.KStudyCountServlet';
        document.form1.p_process.value = 'myActivity';
        document.form1.submit();
    }
    function whenSelectSubj2(oTarget) {
        var ff = document.getElementById("select2").value;
        var a_value = ff.split(",");
        document.form1.subj.value = a_value[0];
        document.form1.year.value = a_value[1];
        document.form1.subjseq.value = a_value[2];
        document.form1.action='/servlet/controller.system.KStudyCountServlet';
        document.form1.p_process.value = 'myActivity';
        document.form1.submit();

    }

    function studyOpen(url) {
    <%  if (box.getSession("gadmin").equals("ZZ")) {  %>
        open_window('openApp',url,0,0,0,0,false,false,true,true,true);
    <%  } else {                                      %>
        alert ("학습자 권한이 아닙니다.");
    <%  }                                             %>
    }

    //엑셀 출력
    function whenExcel() {
        window.self.name = "ActivityStudy";
        open_window("openExcel","","10","10","900","660","no","no","no","no","yes");
        document.form1.target = "openExcel";
        document.form1.action='/servlet/controller.system.KStudyCountServlet';
        document.form1.p_process.value = 'myActivityExcel';
        document.form1.submit();
    }
    //나의진도보기 팝업
    function upWin(url){
        window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();

    }
//-->
</Script>


<form name="form1" method="post" action="">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="subj" value="<%=s_subj%>">
<input type="hidden" name="year" value="<%=s_year%>">
<input type="hidden" name="subjseq" value="<%=s_subjseq%>">
</form>


                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td  class="location" > HOME &gt; 마이클래스 &gt; 나의 학습활동</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/myclass/<%=tem_subimgpath%>/tit_myeduact.gif" ></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/myclass/text_myeduact.gif" width="680" height="33"></td>
                          </tr>
                          <tr>
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td colspan="3"><img src="/images/user/kocca/myclass/bar_classroom_top.gif" width="680" height="19"></td>
                          </tr>
                          <tr>
                            <td width="26" rowspan="4" valign="top"><img src="/images/user/kocca/myclass/bar_classroom_l.gif" width="26" height="80"></td>
                            <td width="627" height="4" valign="top"><img src="/images/user/kocca/myclass/bar_classhistory_top.gif" width="627" height="4"></td>
                            <td width="27" rowspan="4" valign="top"><img src="/images/user/kocca/myclass/bar_classroom_tail.gif" width="27" height="80"></td>
                          </tr>
                          <tr>
                            <td width="627" valign="top" bgcolor="#D9E9D9">
                            <table width="627" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="84" rowspan="3"><img src="/images/user/kocca/myclass/ico_meeduact.gif" ></td>
                                  <td height="8" colspan="2"></td>
                                </tr>
                                <tr>
                                  <td >
                                  <table width="409" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="3" colspan="9"></td>
                                      </tr>
                                      <tr>
                                        <td width="10">&nbsp;</td>
                                        <td width="63" height="18" background="/images/user/kocca/myclass/box_bg.gif"><div align="center" class="tbl_myclass_gleft_1">최초로그인</div></td>
                                        <td width="17">&nbsp;</td>
                                        <td width="11"><img src="/images/user/kocca/myclass/vline_w.gif" width="1" height="13"></td>
                                        <td width="137"><%=FormatDate.getFormatDate(dbox5.getString("d_lgfirst"),"yyyy-MM-dd HH:mm")%></td>
                                        <td width="63" background="/images/user/kocca/myclass/box_bg.gif"><div align="center" class="tbl_myclass_gleft_1">총로그인수</div></td>
                                        <td width="17">&nbsp;</td>
                                        <td width="11"><img src="/images/user/kocca/myclass/vline_w.gif" width="1" height="13"></td>
                                        <td width="80"><%=dbox5.getString("d_lgcnt")%>회</td>
                                      </tr>
                                      <tr>
                                        <td height="3" colspan="9"></td>
                                      </tr>
                                    </table>
                                    <table width="409" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="1" bgcolor="#FFFFFF"></td>
                                      </tr>
                                    </table>
                                    <table width="409" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="3" colspan="9"></td>
                                      </tr>
                                      <tr>
                                        <td width="10">&nbsp;</td>
                                        <td width="63" height="18" background="/images/user/kocca/myclass/box_bg.gif"><div align="center" class="tbl_myclass_gleft_1">최근로그인</div></td>
                                        <td width="17">&nbsp;</td>
                                        <td width="11"><img src="/images/user/kocca/myclass/vline_w.gif" width="1" height="13"></td>
                                        <td colspan="4"><%=FormatDate.getFormatDate(dbox5.getString("d_lglast"),"yyyy-MM-dd HH:mm")%></td>
                                      </tr>
                                      <tr>
                                        <td height="3" colspan="9"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                  <td height="9" colspan="5"></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td height="4" valign="top"><img src="/images/user/kocca/myclass/bar_classhistory_bottom.gif" width="627" height="4"></td>
                          </tr>
                          <tr>
                            <td valign="top">&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="10" colspan="3"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="22" colspan="3">&nbsp;</td>
                          </tr>

 <%
    String s_subj_name = "수강중인 과정이 없습니다.";
    String s_selectString = "";
    String s_selectDefaule = "";
    for(i = 0; i < list1.size(); i++) {
        DataBox dbox    = (DataBox)list1.get(i);

        v_subj          =  dbox.getString("d_subj");
        v_year          =  dbox.getString("d_year");
        v_subjnm        =  dbox.getString("d_subjnm");
        v_subjseq       =  dbox.getString("d_subjseq");

        s_selectDefaule = "";

        if(s_subj.equals(v_subj) && s_year.equals(v_year) && s_subjseq.equals(v_subjseq)){

            s_selectDefaule = "selected";
            s_subj_name = v_subjnm;
        }
            v_eduurl        =  dbox.getString("d_eduurl");
            v_cpsubj        =  dbox.getString("d_cpsubj");
            v_cpsubjseq     =  dbox.getString("d_cpsubjseq");
            v_company       =  dbox.getString("d_company");
            v_isonoff       =  dbox.getString("d_isonoff");
            v_isoutsourcing =  dbox.getString("d_isoutsourcing");
            s_subjnm        =   v_subjnm;

            if (v_isoutsourcing.equals("Y")) {                 //위탁과정일때
                v_eduurl = v_eduurl+"?FIELD1="+v_user_id+"&FIELD2="+v_year+"&FIELD3="+v_cpsubj+"&FIELD4="+v_cpsubjseq+"&FIELD99="+v_company+"&FIELD100=N";


            } else {
                v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);  //외부과정이 아닌경우
            }

            if(v_isonoff.equals("OFF")){
                v_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
            }


             s_selectString += "<option value='"+v_subj+","+v_year+","+v_subjseq+",' "+s_selectDefaule+" >"+v_subjnm+"</option>";




%>
                      <!-- <a href="javascript:whenSelectSubj('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>')">
                        <img src="/images/user/game/mystudy/num_studyact<%=(i<8)? "0": ""%><%=i+1%>.gif" alt="<%=v_subjnm%>" border="0" ></a>  -->
<%}%>



                          <tr>
                            <td width="350"><img src="/images/user/kocca/myclass/st_coursename.gif"><b><%=s_subj_name%></b></td>
                            <td width="150" align="right"><img src="/images/user/kocca/myclass/text_coursechoise.gif">&nbsp;&nbsp;</td>
                            <td width="150" align="left"><select name="select2" id="select2" onchange="javascript:whenSelectSubj2(this);" >
                                          <%= s_selectString%>
                                          </select></td>
                          </tr>
                          <tr>
                            <td height="9" colspan="3"></td>
                          </tr>
                          <tr>
                            <td height="1" background="/images/user/kocca/myclass/dot_bgline.gif" colspan="3"></td>
                          </tr>
                          <tr>
                            <td height="9" colspan="3"></td>
                          </tr>
                        </table>


                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center">
                                <table width="627" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td height="4" valign="top"><img src="/images/user/kocca/myclass/bar_myeduact_top.gif" width="627" height="4"></td>
                                  </tr>
                                  <tr>
                                    <td width="627" valign="top" bgcolor="#F1F1F1">
                                    <table width="627" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td width="37" rowspan="3" bgcolor="#F1F1F1"></td>
                                          <td width="51" rowspan="3"><img src="/images/user/kocca/myclass/ico_basicinfo.gif" ></td>
                                          <td width="60" rowspan="3" bgcolor="#F1F1F1"></td>
                                          <td colspan="2"></td>
                                        </tr>
                                        <tr>
                                            <td ></td>
                                          <td >
                                          <table width="409" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td height="9" colspan="3"></td>
                                              </tr>
                                              <tr>
                                                <td width="10">&nbsp;</td>
                                                <td width="86" height="18" background="/images/user/kocca/myclass/box_bg_lone.gif"><div align="center" class="tbl_myclass_gleft_1">학습시간</div></td>
                                                <td width="17">&nbsp;</td>
                                                <td width="11"><img src="/images/user/kocca/myclass/vline_gr.gif" width="1" height="13"></td>
                                                <td width="80"><%=v_total_time.equals("")?"00":v_total_time%> : <%=v_total_minute.equals("")?"00":v_total_minute%> : <%=v_total_sec.equals("")?"00":v_total_sec%></td>
                                                <td width="63" background="/images/user/kocca/myclass/box_bg.gif"><div align="center" class="tbl_myclass_gleft_1">최근학습일</div></td>
                                                <td width="17">&nbsp;</td>
                                                <td width="11"><img src="/images/user/kocca/myclass/vline_gr.gif" width="1" height="13"></td>
                                                <td width="114"><%=date%></td>
                                              </tr>
                                              <tr>
                                                <td height="3" colspan="9"></td>
                                              </tr>
                                            </table>
                                            <table width="409" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td height="1" background="/images/user/kocca/myclass/vline_gr.gif"></td>
                                              </tr>
                                            </table>
                                            <table width="409" border="0" cellspacing="0" cellpadding="0">
                                              <tr>
                                                <td height="3" colspan="9"></td>
                                              </tr>
                                              <tr>
                                                <td width="10">&nbsp;</td>
                                                <td width="86" height="18" background="/images/user/kocca/myclass/box_bg_lone.gif"><div align="center" class="tbl_myclass_gleft_1">강의실접근횟수</div></td>
                                                <td width="17">&nbsp;</td>
                                                <td width="11"><img src="/images/user/kocca/myclass/vline_gr.gif" width="1" height="13"></td>
                                                <td width="80"><%=count%></td>
                                                <td width="63" height="18" background="/images/user/kocca/myclass/box_bg.gif"><div align="center" class="tbl_myclass_gleft_1">진도(%)</div></td>
                                                <td width="17">&nbsp;</td>
                                                <td width="11"><img src="/images/user/kocca/myclass/vline_gr.gif" width="1" height="13"></td>
                                                <td width="114"><%=step%>%</td>
                                              </tr>
                                              <tr>
                                                <td height="3" colspan="9"></td>
                                              </tr>
                                            </table></td>
                                        </tr>
                                        <tr>
                                          <td height="9" colspan="6"></td>
                                        </tr>
                                      </table></td>
                                  </tr>
                                  <tr>
                                    <td height="4" valign="top"><img src="/images/user/kocca/myclass/bar_myeduact_bottom.gif" width="627" height="4"></td>
                                  </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                        <table width="627" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5" colspan="5"></td>
                          </tr>
                          <tr>
                            <td width="127"><img src="/images/user/kocca/myclass/tab_title_1.gif"></td>
                            <td width="1"><img src="/images/user/kocca/myclass/tab_title_vline.gif"></td>
                            <td width="92"><img src="/images/user/kocca/myclass/tab_title_2.gif"></td>
                            <td width="1"><img src="/images/user/kocca/myclass/tab_title_vline.gif"></td>
                            <td width="406" ><img src="/images/user/kocca/myclass/tab_title_3.gif"></td>
                          </tr>
                          <tr>
                            <td height="1" colspan="5" background="/images/user/kocca/myclass/dot_bgline.gif" ></td>
                          </tr>

<%
for(i = 0; i < list2.size(); i++) {
    DataBox dbox2   = (DataBox)list2.get(i);

    v_menu          =  dbox2.getString("d_menu");
    v_menunm        =  dbox2.getString("d_menunm");
    v_cnt           =  dbox2.getInt("d_cnt");
%>
                          <tr <% if  ( i%2 == 0 ) { out.println("bgcolor='F5F5F5'");   }; %> height="23">
                            <td colspan="2" class="tbl_bleft_1"><%=v_menunm%></td>
                            <td colspan="2" class="tbl_bleft_2"><%=v_cnt%></td>
                            <td width="406"  class="tbl_bleft_1">  <%if(v_menu.equals("91")){%>
                        <%=(v_examcnt==0)?"평가에 참여하지 않으셨습니다.":v_examcnt+""%>
                        &nbsp;
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:upWin('/servlet/controller.study.StudyExamServlet?p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>')"><img align="absmiddle" src="/images/user/kocca/button/b_join.gif" border="0"></a><%}else{%><%}%>
                    <%}else if(v_menu.equals("95")){%>
                        <%=(v_sulcnt==0)?"설문에 참여하지 않으셨습니다.":v_sulcnt+""%>
                        &nbsp;
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:upWin('/servlet/controller.study.StudySulmunServlet?p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>')"><img align="absmiddle" src="/images/user/kocca/button/b_join.gif" border="0"></a><%}else{%><%}%>
                    <%}else if(v_menu.equals("34")){%>
                        제출일 : <%=v_reportindate.equals("")?"":FormatDate.getFormatDate(v_reportindate,"yyyy-MM-dd HH:mm")%>
                    <%}else if(v_menu.equals("21")){%>
                                    <%=v_datacnt%>
                    <%}else if(v_menu.equals("22")){%>
                                    <%=v_boardcnt%>
                    <%}else if(v_menu.equals("3")){%>
                                    <%=v_qnacnt%>
                    <%}%></td>

                          </tr>

<%}//for%>

                        </table>

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="20">&nbsp;</td>
                          </tr>
                        </table>





<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->