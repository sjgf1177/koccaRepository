<%
//**********************************************************
//  1. 제      목: 마이 액티비티 PAGE
//  2. 프로그램 명: zu_Activity_Study.jsp
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
<%@ include file="/learn/user/include/topMyClass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<Script language="javascript">

    function whenSelectSubj(subj,year,subjseq) {
        document.form1.subj.value = subj;
        document.form1.year.value = year;
        document.form1.subjseq.value = subjseq;
        document.form1.action='/servlet/controller.system.StudyCountServlet';
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
        document.form1.action='/servlet/controller.system.StudyCountServlet';
        document.form1.p_process.value = 'myActivityExcel';
        document.form1.submit();
    }
//-->
</Script>
          <!-- center start -->
<form name="form1" method="post" action="">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="subj" value="<%=s_subj%>">
<input type="hidden" name="year" value="<%=s_year%>">
<input type="hidden" name="subjseq" value="<%=s_subjseq%>">
</form>
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 나의 강의실 &gt; 나의 Activity</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/myclass/type1/tit_activity.jpg" alt="나의강의실"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- 구분탭 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="98" valign="top"><a href="/servlet/controller.system.MenuCountServlet?p_process=myActivity" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image48','','/images/user/myclass/tab_siteact_on.gif',1)"><img src="/images/user/myclass/tab_siteact.gif" alt="사이트활동" name="Image48" width="98" height="22" border="0"></a>
                      </td>
                      <td width="98" valign="top" ><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/myclass/tab_studyact_on.gif',1)"><img src="/images/user/myclass/tab_studyact_on.gif" alt="학습활동" name="Image37" border="0"></a></td>
                      <td width="98" valign="top" >&nbsp;</td>
                      <td width="381" align="right" >&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4" class="font_ex">현재학습중인 과정 활동 통계 입니다.</td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="578" valign="top"><img src="/images/user/myclass/text_course2.gif" > 
<%
    for(i = 0; i < list1.size(); i++) {
        DataBox dbox    = (DataBox)list1.get(i);

        v_subj          =  dbox.getString("d_subj");
        v_year          =  dbox.getString("d_year");
        v_subjnm        =  dbox.getString("d_subjnm");
        v_subjseq       =  dbox.getString("d_subjseq");


        if(s_subj.equals(v_subj) && s_year.equals(v_year) && s_subjseq.equals(v_subjseq)){

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


        }

%>
                        <a href="javascript:whenSelectSubj('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>')"><img src="/images/user/myclass/text_<%=i+1%>.gif" alt="<%=v_subjnm%>" border="0" ></a> 
<%}%>
<%if (i==0){%>수강중인 과정이 없습니다.<%}%>
                      </td>
                      <td width="97" align="right" valign="top"><a href="javascript:whenExcel()"><img src="/images/user/button/btn_print.gif" alt="출력하기" border="0"></a></td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="2"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" height="5" valign="top"> 
                        <!-- 학습활동정보 table -->
                        <table width="675" border="0" cellpadding="0" cellspacing="0" background="/images/user/myclass/pe_box_bg.gif">
                          <tr> 
                            <td><img src="/images/user/myclass/pe_box_top.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
                              <table width="630" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="96" rowspan="3" align="center" valign="top"><img src="/images/user/myclass/activity_img.jpg"></td>
                                  <td width="275" height="18" class="tbl_ble"><strong>학습시간 
                                    : </strong><%=v_total_time.equals("")?"00":v_total_time%> : <%=v_total_minute.equals("")?"00":v_total_minute%> : <%=v_total_sec.equals("")?"00":v_total_sec%></td>
                                  <td>&nbsp;</td>
                                  <td class="tbl_ble"><strong>최근학습일 :</strong> 
                                    <%=date%></td>
                                </tr>
                                <tr> 
                                  <td height="2" background="/images/user/myclass/stu_box_vline2.gif"></td>
                                  <td width="13" height="2"></td>
                                  <td width="246" height="2" background="/images/user/myclass/stu_box_vline2.gif"></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_ble"><strong>강의실접근횟수 :</strong> 
                                    <%=count%></td>
                                  <td>&nbsp;</td>
                                  <td class="tbl_ble"><strong>진도 :</strong> <%=step%> %</td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/myclass/pe_box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="20" valign="top"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center" valign="top"> 
                        <!-- 사이트활동테이블 -->
                        <table width="612" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="95" valign="top"><img src="/images/user/myclass/text_course.gif"></td>
                            <td width="517"><strong><%=s_subjnm%></strong></td>
                          </tr>
                          <tr> 
                            <td height="8" colspan="2" align="center" valign="top"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" align="center" valign="top"><img src="/images/user/myclass/act_text_t3.gif"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" align="center" valign="top"> <table width="600" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="5" colspan="3"></td>
                                </tr>
<%
for(i = 0; i < list2.size(); i++) {
    DataBox dbox2   = (DataBox)list2.get(i);

    v_menu          =  dbox2.getString("d_menu");
    v_menunm        =  dbox2.getString("d_menunm");
    v_cnt           =  dbox2.getInt("d_cnt");
%>
                                <tr> 
                                  <td width="135" bgcolor="#F6F6F6" class="tbl_gleft"><%=v_menunm%></td>
                                  <td width="105" class="tbl_pc"><%=v_cnt%></td>
                                  <td width="360" class="tbl_gleft">
                    <%if(v_menu.equals("91")){%>
                        <%=(v_examcnt==0)?"평가에 참여하지 않으셨습니다.":v_examcnt+""%> 
                        &nbsp; &nbsp; &nbsp; &nbsp      
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:studyOpen('<%=v_eduurl%>')"><img src="/images/user/button/btn_m_myclassgo.gif" alt="강의실가기" border="0"></a><%}else{%><%}%>
                    <%}else if(v_menu.equals("95")){%>  
                        <%=(v_sulcnt==0)?"설문에 참여하지 않으셨습니다.":v_sulcnt+""%> 
                        &nbsp; &nbsp; &nbsp; &nbsp;
                        <% if(v_isonoff.equals("ON")){ %><a href="javascript:studyOpen('<%=v_eduurl%>')"><img src="/images/user/button/btn_m_myclassgo.gif" alt="강의실가기" border="0"></a><%}else{%><%}%>
                    <%}else if(v_menu.equals("34")){%>  
                        제출일 : <%=v_reportindate.equals("")?"":FormatDate.getFormatDate(v_reportindate,"yyyy-MM-dd HH:mm")%>
                    <%}else if(v_menu.equals("21")){%>  
                                    <%=v_datacnt%>                              
                    <%}else if(v_menu.equals("22")){%>  
                                    <%=v_boardcnt%> 
                    <%}else if(v_menu.equals("3")){%>   
                                    <%=v_qnacnt%>   
                    <%}%>
                                  </td>
                                </tr>
                                <tr> 
                                  <td height="1"></td>
                                  <td height="1" colspan="2" bgcolor="#E3DED5"></td>
                                </tr>
<%}%>
                                <tr> 
                                  <td height="1"></td>
                                  <td height="1" colspan="2" bgcolor="#E3DED5"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->