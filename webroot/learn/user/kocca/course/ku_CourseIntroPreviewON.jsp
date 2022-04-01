<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: zu_SubjectPreview.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
//    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String upload_url = conf.getProperty("url.upload");

    String  v_process       = box.getString("p_process");
    String  v_rprocess      = box.getString("p_rprocess");
    String  v_isonoff       = box.getStringDefault("p_isonoff","ON");
    String  v_sphere        = box.getString("p_sphere");
    String  v_middleclass   = box.getString("p_middleclass");
//    String  v_subjtype      = box.getStringDefault("p_subjtype","alway");
    String  v_subjtype      = box.getString("p_subjtype");
    String  v_subjtype_view = "";
    String  v_disposition   = box.getStringDefault("p_disposition","year");  // 성향
    String  v_subj          = box.getString("p_subj");

    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_year      =  "";
    String  v_preurl    =  "";
    String  v_sdesc     =  "";
    String  v_propstart =  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_place     =  "";
    String  v_tutor     =  "";
    String  v_muserid   =  "";
    String  v_mastertel =  "";
    String  v_isgoyong  =  "";
    String  v_isunit    =  "";
    String  v_isordinary=  "";
    String  v_isworkshop=  "";
    String  v_subjtarget=  "";
    String  v_edumans   =  "";
    String  v_intro     =  "";
    String  v_explain   =  "";
    String  v_memo        = "";

    String  v_isonoff_value="";
    String  v_prop_img  =  "";
    String  v_isgoyong_value="";
    String  v_edutype   =  "";
    String  v_env       =  "";
    String  v_owner     =  "";  //소유주코드
    String  v_ownerman  =  "";  //크래듀운영자명
    String  v_ownertel  =  "";  //담당자 전화번호

	int		v_prewidth = 0; //맛보기 창 width
    int	 	v_preheight = 0; //맛보기 창 height


    String  v_introducefilenamereal = "";
    String  v_introducefilenamenew  = "";
    String  v_introducefilenamenew_view  = "";
    String  v_contenttype  = "";

    String v_mname      = "";   // 강사명
    String v_career     = "";   // 강사경력
    String v_book       = "";   // 강사저서

    int     v_studentlimit =0;
    int     v_biyong    =  0;
    int     v_wstep     =  0;
    int     v_wmtest    =  0;
    int     v_wftest    =  0;
    int     v_wreport   =  0;
    int     v_wetc1     =  0;
    int     v_wetc2     =  0;
    int     v_Twetc     =  0;
    int     v_stepcnt   =  0;
    int     v_testcnt   =  0;
    int     v_reportcnt =  0;
    int     v_actcnt    =  0;
    int     v_gradscore =  0;
    int     v_gradstep  =  0;
    int     v_edudays   =  0;
    int     v_edutimes  =  0;
    int     v_ratewbt   =  0;
    int     v_ratevod   =  0;
    int     v_score     =  0;   // 학점배점
    int     v_propcnt   = 0;
    String  v_lesson    =  "";
    String  v_desc      = "";
    String  v_fromdate  = "";
    String  v_todate    = "";
    String  v_fromdate_view  = "";
    String  v_todate_view    = "";

    String  v_biyong_view = "";
    String  v_studentlimit_view  = "";
    String  v_isoutsourcing  ="";
    String  v_conturl        = "";
    int     i           =  0;

//    v_preurl               =  EduEtc1Bean.make_previewURL(v_subj);
    String v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);

    String  s_userid = box.getSession("userid");
    String  v_prop_check = "";

    String v_classname  = "";
    String v_eduperiod  = "";
	String v_tname = "";

    int v_discount = 0;                           // 할인율
    v_discount = DiscountBean.returnDiscount(box);

    DataBox dbox = (DataBox)request.getAttribute("subjectPreview");

    if (dbox != null) {
        v_subj           = dbox.getString("d_subj");
        v_subjnm         = dbox.getString("d_subjnm");
        v_subjtarget     = dbox.getString("d_subjtarget");
        v_intro          = dbox.getString("d_intro");
        v_edumans        = dbox.getString("d_edumans");
        v_explain        = dbox.getString("d_explain");
        v_memo          = dbox.getString("d_memo");
        v_owner          = dbox.getString("d_owner");
        v_ownerman       = dbox.getString("d_ownerman");
        v_ownertel       = dbox.getString("d_ownertel");
        v_muserid        = dbox.getString("d_muserid");
        v_preurl         = dbox.getString("d_preurl");
        v_mastertel      = dbox.getString("d_musertel");
        v_isoutsourcing  = dbox.getString("d_isoutsourcing");
        v_conturl        = dbox.getString("d_conturl");
        v_contenttype    = dbox.getString("d_contenttype");
        v_isgoyong       = dbox.getString("d_isgoyong");
        v_isunit         = dbox.getString("d_isunit");
        v_isordinary     = dbox.getString("d_isordinary");
        v_isworkshop     = dbox.getString("d_isworkshop");

        v_biyong    =  dbox.getInt("d_biyong");
        v_wstep     =  dbox.getInt("d_wstep");
        v_wmtest    =  dbox.getInt("d_wmtest");
        v_wftest    =  dbox.getInt("d_wftest");
        v_wreport   =  dbox.getInt("d_wreport");
        v_wetc1     =  dbox.getInt("d_wetc1");
        v_wetc2     =  dbox.getInt("d_wetc2");
        v_gradscore =  dbox.getInt("d_gradscore");
        v_gradstep  =  dbox.getInt("d_gradstep");

        v_mname         = dbox.getString("d_name");     //강사명
        v_career        = dbox.getString("d_career");
        v_book          = dbox.getString("d_book");
        v_classname     = dbox.getString("d_classname");
        v_eduperiod     = dbox.getString("d_eduperiod");
		v_tname			= dbox.getString("d_name");

		v_prewidth		= dbox.getInt("d_prewidth");
    	v_preheight		= dbox.getInt("d_preheight");

    	if (v_prewidth==0) v_prewidth   = 1024;
    	if (v_preheight==0) v_preheight = 768;


        v_isonoff_value  = (v_isonoff.equals("ON"))?"사이버":"집합";
        v_intro          =  StringManager.replace(v_intro,"\r\n","<br>");
        v_explain        =  StringManager.replace(v_explain,"\r\n","<br>");
        v_mastertel      =  StringManager.replace(v_mastertel,"\r\n","<br>");
        v_memo           =  StringManager.replace(v_memo,"\r\n","<br>");

        v_career      =  StringManager.replace(v_career,"\r\n","<br><img src='/images/user/game/course/bl_intro.gif' width='9' height='8'>&nbsp;");
        v_book        =  StringManager.replace(v_book,"\r\n","<br><img src='/images/user/game/course/bl_intro.gif' width='9' height='8'>&nbsp;");


        if (v_isgoyong.equals("Y")) v_isgoyong_value = "적용";
        else                        v_isgoyong_value = "미적용";
        v_introducefilenamereal = dbox.getString("d_introducefilenamereal");
        v_introducefilenamenew  = dbox.getString("d_introducefilenamenew");
        if (v_introducefilenamenew.equals("")) v_introducefilenamenew_view = "/images/user/kocca/course/course_img.gif";
        else                                   v_introducefilenamenew_view = upload_url + "bulletin/" + v_introducefilenamenew;
        //v_owner = getCodenm.get_owner(v_subj);

    }



    if (v_subjtype.equals("")) {
        if (v_isordinary.equals("Y")) v_subjtype = "alway";
        else if (v_isordinary.equals("N")) v_subjtype = "sometime";
    }

    if (v_subjtype.equals("alway")) {
        v_subjtype_view = "상시과정";
    } else if (v_subjtype.equals("sometime")) {
        v_subjtype_view = "수시과정";
    } else if (v_subjtype.equals("prof")) {
        v_subjtype_view = "전문가과정";
    } else if (v_subjtype.equals("workshop")) {
        v_subjtype_view = "워크숍과정";
    } else if (v_subjtype.equals("recognition")) {
        v_subjtype_view = "학점인증과정";
    }

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
var userId = "<%=s_userid%>";
//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, billYn,course,coursenm) {

   if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
     return;
   }

    if(billYn == "Y") {
        document.form1.p_process.value = "SubjectEduBill";
    } else {
        document.form1.p_process.value = "SubjectEduPropose";
    }

    document.form1.target = "_self";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_billYn.value = billYn;
    document.form1.p_course.value = course;
    document.form1.p_process.value = "SubjectEduPropose";
    document.form1.action = "/servlet/controller.propose.KProposeCourseServlet";
    document.form1.submit();
}

//관심과정 등록
function whenSubjPropose2(subj,subjnm) {

   if(!confirm(subjnm+"과정을 관심과정으로 등록하시겠습니까?")){
     return;
   }

   document.form1.target = "_self";
   document.form1.p_subj.value = subj;
//   document.form1.p_year.value = year;
//   document.form1.p_subjseq.value = subjseq;
   document.form1.p_userid.value = "<%=s_userid%>";

   document.form1.p_process.value = "InterestInsert";
   document.form1.action = "/servlet/controller.study.KInterestServlet";
   document.form1.submit();
}

//나의강의실
function goPersonHistory(){
  document.form1.target = "_self";
  document.form1.p_process.value = "EducationSubjectPage";
  document.form1.action="/servlet/controller.study.KMyClassServlet";
  document.form1.submit();
}

// 성향선택
function whenTabDispo(tabnm) {
   document.form1.p_disposition.value = tabnm;
   document.form1.p_process.value = "SubjectPreviewPage";
   document.form1.action = "/servlet/controller.course.KCourseIntroServlet";
   document.form1.target = "_self";
   document.form1.submit();
}

// 교수소개 팝업
function open_introduceList() {
    window.self.name = "introduceList";     //opener 객체에 name 을 지정한다
    //open_window("openIntroduceList","","100","0","700","590","1");
	open_window("openIntroduceList", "", "100", "0", "720", "590", "no", "no", "no", "yes", "no");

    document.form1.scsubj.value = "<%=v_subj%>";
    document.form1.userid.value = "<%=v_muserid%>";
    document.form1.target = "openIntroduceList";
    document.form1.action = "/servlet/controller.tutor.TutorMessageAdminServlet";
    document.form1.p_process.value = "introduceTutor";
    document.form1.submit();
    document.form1.target = window.self.name;
}

 // 과정 내용보기
    function whenSubjInfo(subj,sphere){
        document.form1.p_subj.value        = subj;
        document.form1.p_sphere.value = sphere;
        document.form1.p_process.value  = 'SubjectPreviewPage';
        document.form1.p_rprocess.value = 'SubjectListAlway';
        document.form1.action='/servlet/controller.course.KCourseIntroServlet';
        document.form1.target = "_self";
        document.form1.submit();
    }

// 맛보기
function whenPreShow(url, subj) {
    // 로그
  //  prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
//    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window("openShow",url,"100","100","<%=v_prewidth%>","<%=v_preheight%>","","","",true,true);
}


// 맛보기
function studyOpen(url,subj) {
    // 로그
    if (userId=="") {
    }else{
        prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
        open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);
    }
    open_window('openApp',url,0,0,<%=v_prewidth%>,<%=v_preheight%>,false,false,true,true,true);
}
//-->
</SCRIPT>
<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="<%=v_process%>" >
    <input type='hidden' name='p_rprocess' value="<%=v_rprocess%>" >
    <input type='hidden' name='p_subj' value="<%=v_subj%>">
    <input type='hidden' name='p_disposition' value="<%=v_disposition%>">
    <input type='hidden' name='p_sphere' value="<%=v_sphere%>">
    <input type='hidden' name='p_middleclass' value="<%=v_middleclass%>">
    <input type='hidden' name='p_subjtype' value="<%=v_subjtype%>">
    <input type='hidden' name='p_userid' value="">
    <input type='hidden' name='p_year' value="">
    <input type='hidden' name='p_subjseq' value="">
    <input type='hidden' name='userid'   value="">
    <input type='hidden' name='scsubj'   value="">
    <input type="hidden" name="p_billYn">
    <input type="hidden" name="p_course">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td  class="location" > HOME > 과정소개 > <%=v_subjtype_view%></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/<%=tem_subimgpath%>/tit_course_<%=v_subjtype%>.gif"></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <!--서브메뉴_앵커 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="90" rowspan="3"><img src="/images/user/kocca/course/i_alway.gif"></td>
                            <td width="10" rowspan="3">&nbsp;</td>
                            <td><img src="/images/user/kocca/course/text_alway_01.gif"></td>
                          </tr>
                          <tr>
                            <td height="12"></td>
                          </tr>
                          <tr>
                            <td><a href="#info"><img src="/images/user/kocca/course/st_alway_info.gif" border="0"></a><a href="#curry"><img src="/images/user/kocca/course/st_alway_colum.gif" border="0"></a><a href="#anali"><img src="/images/user/kocca/course/st_alway_analysis.gif" border="0"></a><a href="#goodcourse"><img src="/images/user/kocca/course/st_alway_recommend.gif" border="0"></a><!--a href="#apply"><img src="/images/user/kocca/course/st_alway_apply.gif" border="0"></a--><!--임시처리--></td>
                          </tr>
                          <tr>
                            <td height="17" colspan="3" ></td>
                          </tr>
                        </table>
                        <!--표시작1 -->
                        <a name="info"></a>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" height="142" class="tb_bg_alway"><img src="/images/user/kocca/course/text_course_name02.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td valign="middle"><table width="439" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td colspan="2" class="tbl_bleft"><strong>[ <%=v_subjnm%> ]</strong></td>
                                </tr>
                                <tr>
                                  <td align="center" >
                                    <table width="137" height="100" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" >
                                      <tr>
                                        <td width="125" align="center"><img src="<%=v_introducefilenamenew_view%>" width="128" height="95"></td>
                                      </tr>
                                    </table></td>
                                  <td width="299" valign="bottom" class="tblfont_titleft">
<%
                                 if (v_hasPreviewObj.equals("Y")) {
%>
                                    <a href="javascript:studyOpen('/servlet/controller.contents.EduStart?p_subj=<%=v_subj%>&p_year=2000&p_subjseq=0001&p_ispreview=Y', '<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/kocca/button/b_course_pre.gif" border="0"></a>
<%
                                 } else {
                                     if(!v_preurl.equals("")){
%>
                                    <a href="javascript:whenPreShow('<%=v_preurl%>','<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/kocca/button/b_course_pre.gif" border="0"></a>
<%
                                     }
                                 }
%>
                                     <a href="javascript:whenSubjPropose2('<%=v_subj%>', '<%=v_subjnm%>')" onfocus=this.blur()><img src='/images/user/kocca/button/b_choise_course.gif' border=0></a>
                                  </td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table>
                        <!--임시처리-->
                        <!--table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_course_fraction02.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft"><%=v_subjtype_view%> </td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table-->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_course_porpose.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft"><%=v_intro%></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_student.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft"><%=v_edumans%></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_reference.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft"><%=v_memo%></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table>
                        <!--임시처리-->
                        <!--table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_pay.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft"><%=v_biyong%></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table-->
                        <!--임시처리-->
                        <!--table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_eduspecial.gif" height="12"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft">강좌 오픈 후 2주차 토요일에 통합특강 실시</td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table-->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_deadline.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft">- 진도율 : <%=v_wstep%>%<br>
                              - 과 제 : <%=v_wreport%>%<br>
                              - 참여도 : <%=v_wetc1%>%<br>
                              <strong>- 총점 <%=v_gradscore%>점 이상 수료 </strong></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="3"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr >
                            <td height="2" colspan="4" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
                          </tr>
                          <tr>
                            <td height="2" colspan="4"></td>
                          </tr>
                          <tr>
                            <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_teacher.gif"></td>
                            <td width="13">&nbsp;</td>
                            <td class="tbl_bleft"><strong><%=v_tname%></strong>
<%                      if (!v_tname.equals("")) {                                    %>
                            <strong>&nbsp;&nbsp;<a href="javascript:open_introduceList()"><img src="/images/user/kocca/button/b_introduce_pro.gif" border="0"></a></strong>
<%                      }                                                                %>
                            </td>
                            <td class="tbl_bleft">&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="2" colspan="4"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="1" colspan="2" class="linecolor_course02"></td>
                          </tr>
                          <tr>
                            <td height="5"></td>
                          </tr>
                          <tr>
                            <td colspan="2" align="right"><a href="#top"><img src="/images/user/kocca/button/b_top.gif" border="0"></a></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"><div align="left"><a name="curry"></a></div></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/course/sst_curry.gif"></td>
                          </tr>
                          <tr>
                            <td height="8"></td>
                          </tr>
                        </table>
                        <table width="680" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#DFDFE0"  frame="hsides">
                          <tr  class="lcolor">
                            <td height="3" colspan="5" class="linecolor_cource"></td>
                          </tr>
                          <tr>
                            <td width="34" class="tbl_ytit">차</td>
                            <td width="110" class="tbl_ytit">시작일</td>
                            <td width="110" class="tbl_ytit">종료일</td>
                            <td width="247" class="tbl_ytit">강의내용<br></td>
                            <td width="104"  class="tbl_ytit">담당교수</td>
                          </tr>
                          <tr>
                            <td height="5" colspan="5" ></td>
                          </tr>
<%
    ArrayList list2 = (ArrayList)request.getAttribute("lessonList");


    int j = 0;
    for(j = 0; j < list2.size(); j++)
    {
        DataBox dbox2 = (DataBox)list2.get(j);

        v_lesson   = dbox2.getString("d_lesson");
        v_desc     = dbox2.getString("d_sdesc");
        v_fromdate = dbox2.getString("d_fromdate");
        v_todate   = dbox2.getString("d_todate");
        if (v_fromdate.equals("0")) v_fromdate_view  = "기간제한없음";
        else                        v_fromdate_view  = "교육시작일로부터 " + v_fromdate + "일";
        if (v_todate.equals("0"))   v_todate_view  = "기간제한없음";
        else                        v_todate_view  = "교육시작일로부터 " + v_todate + "일";


%>
                          <tr>
                            <td height="34" class="tbl_grc"><%=StringManager.cutZero(v_lesson)%></td>
                            <td class="tbl_grc"><%=v_fromdate_view%></td>
                            <td class="tbl_grc"><%=v_todate_view%></td>
                            <td class="tbl_bleft"><%=v_desc%><br></td>
                            <td class="tbl_grc"><%=v_mname%>(<%=v_muserid%>)</td>
                          </tr>
<%
    }

    if(j == 0) {
%>
    <tr>
      <td class="tbl_grc" align="center" colspan="4">해당 차시가 없습니다.</td>
    </tr>
<%
    }
%>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"></td>
                          </tr>
                          <tr>
                            <td colspan="2" align="right"><a href="#top"><img src="/images/user/kocca/button/b_top.gif" border="0"></a></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"><div align="center"><a name="anali"></a></div></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/course/sst_analysis.gif" width="116" height="14"></td>
                          </tr>
                          <tr>
                            <td height="8"></td>
                          </tr>
                        </table>
                        <!--장르별분야_서브텍스트 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="8" colspan="7"></td>
                          </tr>
                          <tr>
                            <td width="6">&nbsp;</td>
                            <td width="63"><%if (v_disposition.equals("year")) { %><img src="/images/user/kocca/course/tab_year_on.gif"><%} else {%><a href="javascript:whenTabDispo('year')" onMouseOver="MM_swapImage('Image141','','/images/user/kocca/course/tab_year_on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/user/kocca/course/tab_year.gif" name="Image141" width="63" height="24" border="0" id="Image141"></a><%}%></td>
                            <td width="2">&nbsp;</td>
                            <td width="63"><%if (v_disposition.equals("sex")) { %><img src="/images/user/kocca/course/tab_sex_on.gif"><%} else {%><a href="javascript:whenTabDispo('sex')" onMouseOver="MM_swapImage('Image142','','/images/user/kocca/course/tab_sex_on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/user/kocca/course/tab_sex.gif" name="Image142" width="63" height="24" border="0" id="Image42"></a><%}%></td>
                            <td width="2">&nbsp;</td>
                            <td width="481"><%if (v_disposition.equals("job")) { %><img src="/images/user/kocca/course/tab_job_on.gif"><%} else {%><a href="javascript:whenTabDispo('job')" onMouseOver="MM_swapImage('Image143','','/images/user/kocca/course/tab_job_on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/user/kocca/course/tab_job.gif" name="Image143" width="63" height="24" border="0" id="Image43"></a><%}%></td>
                            <td width="63">&nbsp;</td>
                          </tr>
                          <tr>
                            <td colspan="7"><img src="/images/user/kocca/course/bar_analysis_top.gif" width="680" height="11"></td>
                          </tr>
                          <tr >
                            <td colspan="7" background="/images/user/kocca/course/bg_analysis.gif"><table width="680" border="0" cellspacing="0" cellpadding="0" >
                                <tr>
                                  <td width="100" rowspan="3">&nbsp;</td>
                                  <td width="560" height="10"></td>
                                  <td width="20" rowspan="3">&nbsp;</td>
                                </tr>
                                <tr>
                                  <td>


                                    <!--  기존 학습자 성향 분석 -->

<%

    ArrayList list3 = (ArrayList)request.getAttribute("dispositionList");

    String v_age   = "";
    String v_sex   = "";
    String v_jikup = "";
    String [] v_ages = {"50대", "40대", "30대", "20대", "10대"};
    String [] v_sexs = {"남", "여"};
    String [] v_jikups = {"학생", "회사원","공무원,교직,군인", "전문,자유직", "농,수,축산업", "자영업", "주부", "기타"};
    int    [] v_age_cnts   = {0,0,0,0,0};
    int    [] v_sex_cnts   = {0,0};
    int    [] v_jikup_cnts = {0,0,0,0,0,0,0,0};

    String v_w_size = "";
    String []  v_text = null;
    int    []  v_cnts = null;
    int       avg  = 0;
    int       avg_view = 0;
    int v_cnt = 0;
    int total = 0;
    int m = 0;

    if (v_disposition.equals("year")) {                             // 나이별
        for(m = 0; m < list3.size(); m++)  {
            DataBox dbox3 = (DataBox)list3.get(m);

            v_age   = dbox3.getString("d_age");
            v_cnt     = dbox3.getInt("d_cnt");

            if (v_age.equals("10"))      v_age_cnts[4] = v_cnt;
            else if (v_age.equals("20")) v_age_cnts[3] = v_cnt;
            else if (v_age.equals("30")) v_age_cnts[2] = v_cnt;
            else if (v_age.equals("40")) v_age_cnts[1] = v_cnt;
            else if (v_age.equals("50")) v_age_cnts[0] = v_cnt;
            total += v_cnt;
        }
        v_w_size = "38";
        v_text = v_ages;
        v_cnts = v_age_cnts;

    } else if (v_disposition.equals("sex")) {                       // 성별
        for(m = 0; m < list3.size(); m++)  {
            DataBox dbox3 = (DataBox)list3.get(m);

            v_sex   = dbox3.getString("d_sex");
            v_cnt     = dbox3.getInt("d_cnt");

            if (v_sex.equals("1"))      v_sex_cnts[0] = v_cnt;
            else if (v_sex.equals("2")) v_sex_cnts[1] = v_cnt;
            total += v_cnt;
        }
        v_w_size = "38";
        v_text = v_sexs;
        v_cnts = v_sex_cnts;

    } else if (v_disposition.equals("job")) {
        for(m = 0; m < list3.size(); m++)  {
            DataBox dbox3 = (DataBox)list3.get(m);

            v_jikup   = dbox3.getString("d_jikup");
            v_cnt     = dbox3.getInt("d_cnt");

            if (v_jikup.equals("01"))      v_jikup_cnts[0] = v_cnt;
            else if (v_jikup.equals("02")) v_jikup_cnts[1] = v_cnt;
            else if (v_jikup.equals("03")) v_jikup_cnts[2] = v_cnt;
            else if (v_jikup.equals("04")) v_jikup_cnts[3] = v_cnt;
            else if (v_jikup.equals("05")) v_jikup_cnts[4] = v_cnt;
            else if (v_jikup.equals("06")) v_jikup_cnts[5] = v_cnt;
            else if (v_jikup.equals("07")) v_jikup_cnts[6] = v_cnt;
            else if (v_jikup.equals("08")) v_jikup_cnts[7] = v_cnt;
            else                           v_cnt = 0;
            total += v_cnt;
        }
        v_w_size = "100";
        v_text = v_jikups;
        v_cnts = v_jikup_cnts;

    }

    for(m = 0; m < v_text.length; m++)  {
        if (total != 0) avg = (int)((v_cnts[m] * 100) / total);
        else            avg = 0;
        if (avg == 0) avg_view = 1;
        else          avg_view = avg;
%>
                                    <table width="560" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="2" colspan="5"></td>
                                      </tr>
                                      <tr>
                                        <td width="<%=v_w_size%>"><%=v_text[m]%></td>
                                        <td width="5">&nbsp;</td>
                                        <td class="linecolor_course04"><img src="/images/user/kocca/course/bar_gradient.gif" width="<%=avg_view%>%" height="10"></td>
                                        <td width=15>&nbsp;</td>
                                        <td width=53><font color="#663300"><%=avg%>%</font></td>
                                      </tr>
                                      <tr>
                                        <td height="1" colspan="5"></td>
                                      </tr>
                                      <tr>
                                        <td></td>
                                        <td></td>
                                        <td height="1" colspan="3" class="linecolor_course03"></td>
                                      </tr>
                                    </table>

<%
    }
%>

                                  </td>
                                </tr>
                                <tr>
                                  <td height="10"></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td colspan="7"><img src="/images/user/kocca/course/bar_analysis_bottom.gif" width="680" height="11"></td>
                          </tr>
                        </table>

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"></td>
                          </tr>
                          <tr>
                            <td colspan="2" align="right"><a href="#top"><img src="/images/user/kocca/button/b_top.gif" border="0"></a></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"><div align="center"><a name="goodcourse"></a></div></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/course/sst_good_course.gif" width="57" height="15"></td>
                          </tr>
                          <tr>
                            <td height="8"></td>
                          </tr>
                          <tr>
                            <td height="8"><img src="/images/user/kocca/course/text_recom.gif"></td>
                          </tr>
                          <tr>
                            <td height="8"></td>
                          </tr>
                        </table>

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/bar_analysis_top.gif" width="680" height="11"></td>
                          </tr>
                          <tr >
                            <td background="/images/user/kocca/course/bg_analysis.gif"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="22" rowspan="2">&nbsp;</td>
                                  <td width="625"><table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="22" colspan="3">&nbsp;</td>
                                      </tr>
                                      <tr>

<%
    ArrayList list4 = (ArrayList)request.getAttribute("recomList");
    if (list4 == null) {
        MainSubjSearchBean subjbean = new MainSubjSearchBean();
        list4 = subjbean.selectSubjRecomList(box);
    }

    String v_re_subj       = "";
    String v_re_subjnm     = "";
    String v_re_muserid    = "";
    String v_re_mname      = "";
    String v_re_sphere     = "";
    String v_re_savefile   = "";
    String v_re_realfile   = "";
    int n = 0;

        for(n = 0; n < list4.size(); n++)  {
            DataBox dbox4 = (DataBox)list4.get(n);

            v_re_subj       = dbox4.getString("d_subj");
            v_re_subjnm     = dbox4.getString("d_subjnm");
            v_re_muserid    = dbox4.getString("d_muserid");
            v_re_mname      = dbox4.getString("d_mname");
            v_re_sphere      = dbox4.getString("d_sphere");
            v_re_savefile   = dbox4.getString("d_introducefilenamenew");
            v_re_realfile   = dbox4.getString("d_introducefilenamereal");

%>
                                        <td width="208">
                                          <!--과목소개 _컨덴츠-->
                                          <table width="208" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width="90" rowspan="4" valign="top" >
                                                <table width="90" border="0" cellpadding="0" cellspacing="3" bgcolor="#e7e7e7">
                                                  <tr>
                                                    <td>
                                                      <table width="84" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
                                                        <tr>
                                                          <td>
                                                            <%if( !v_re_savefile.equals("") ){
                                                                v_re_savefile = conf.getProperty("url.upload") + "bulletin/" + v_re_savefile;%>
                                                            <img src="<%=v_re_savefile%>" width="80" height="70"><br></td>
                                                            <%}else{
                                                                v_re_savefile = "/images/user/kocca/course/photo_blank.gif"; %>
                                                            <img src="<%=v_re_savefile%>" ><br></td>
                                                            <%}%>
                                                          </td>
                                                        </tr>
                                                      </table>
                                                    </td>
                                                  </tr>
                                                </table>
                                              </td>
                                              <td width="107" class="tbl_bleft_black"  height="35"><%=v_re_subjnm%></td>
                                              <td width="13" rowspan="4">&nbsp;</td>
                                            </tr>
                                            <tr>
                                              <td class="linecolor_course01" height="1"></td>
                                            </tr>
                                            <tr>
                                              <td height="29" valign="top" class="tbl_bleft">책임교수:<%=v_re_mname%></td>
                                            </tr>
                                            <tr>
                                              <td height="15" class="tbl_bleft"><a href="javascript:whenSubjInfo('<%=v_re_subj%>','<%=v_re_sphere%>')"><img src="/images/user/kocca/button/btn_more.gif"  border="0"></a></td>
                                            </tr>
                                          </table>
                                        </td>
<%
        }

        for (int o = 0; n+o < 3; o ++) {
%>
                                        <td width="208">&nbsp;</td>
<%
        }
%>
                                      </tr>
                                      <tr>
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr >
                                        <td height="1" colspan="3" background="/images/user/kocca/course/bg_dot_line03.gif"></td>
                                      </tr>
                                    </table></td>
                                  <td width="33" rowspan="2">&nbsp;</td>
                                </tr>
                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/course/bar_analysis_bottom.gif" width="680" height="11"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"></td>
                          </tr>
                          <tr>
                            <td colspan="2" align="right"><a href="#top"><img src="/images/user/kocca/button/b_top.gif" border="0"></a></td>
                          </tr>
                        </table>

                        <!--임시처리-->
                        <!-- 수강신청 시작 -->
                        <!--table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"><div align="center"><a name="apply"></a></div></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/course/sst_course_apply.gif" width="57" height="15"></td>
                          </tr>
                          <tr>
                            <td height="8"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><strong><%=box.getSession("name")%> 님</strong>의 할인율은 <%=v_discount%>% 입니다.</td>
                          </tr>
                          <tr>
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="680" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="linecolor_course">
                            <td height="3" colspan="9" class="linecolor_cource"></td>
                          </tr>
                          <tr>
                            <td width="33" class="tbl_ytit">차수</td>
                            <td width="80" class="tbl_ytit">책임교수 </td>
                            <td width="135" class="tbl_ytit2">수강신청기간 </td>
                            <td width="78" class="tbl_ytit">개강일</td>
                            <td width="52" class="tbl_ytit2">기간</td>
                            <td width="73" class="tbl_ytit2">수강료</td>
                            <td width="100"  class="tbl_ytit">신청</td>
                          </tr>
                          <tr>
                            <td height="5" colspan="9" ></td>
                          </tr>
<%
            ArrayList list = (ArrayList)request.getAttribute("subjseqList");

            //out.println(list.size());
            String v_ispropose  = "";
            String v_statusString = "";
            String v_subjseqgr = "";
            String v_today = "";
            int    v_seq_biyong = 0;
            int    v_iedustart = 0;
            String v_billYn  = "";

            int k = 0;

            for(k = 0; k < list.size(); k++) {
                DataBox dbox1 = (DataBox)list.get(k);

                v_studentlimit       = dbox1.getInt("d_studentlimit");
                v_subjseq            = dbox1.getString("d_subjseq");
                v_year               = dbox1.getString("d_year");
                v_subjseqgr          = dbox1.getString("d_subjseqgr");
                v_propstart          = dbox1.getString("d_propstart");
                v_propend            = dbox1.getString("d_propend");
                v_edustart           = dbox1.getString("d_edustart");
                v_eduend             = dbox1.getString("d_eduend");
                v_ispropose          = dbox1.getString("d_ispropose");
                v_propcnt            = dbox1.getInt("d_propcnt");
                v_seq_biyong         = dbox1.getInt("d_biyong");
                v_today = FormatDate.getDate("yyyyMMddhh");

                if( v_seq_biyong == 0) v_billYn = "N";
                else                   v_billYn = "Y";

                if(v_edustart.equals("")){v_iedustart = 0;}
                else{v_iedustart = Integer.parseInt(v_edustart);}

//                if(v_iedustart >= Integer.parseInt(v_today)){
                    v_statusString = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','"+v_billYn+"','000000' ,'')\" onfocus=this.blur()><img src='/images/user/kocca/button/b_aplly.gif' border=0></a>";

                    //out.println("v_ispropose"+v_ispropose);
                    if(v_propcnt > 0){
                        v_statusString = "";
                    }
//                } else{
//                    v_statusString = "<a href=\"javascript:whenSubjPropose2('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"')\" onfocus=this.blur()><img src='/images/user/button/b_interest.gif' border=0></a>";
//                }

                //날짜 처리
                if(v_propstart.equals("")){v_propstart = "미정";}
                else{v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");}

                if(v_propend.equals("")){v_propend = "미정";}
                else{v_propend = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");}

                if(v_edustart.equals("")){v_edustart = "미정";}
                else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}

                if(v_eduend.equals("")){v_eduend = "미정";}
                else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}

%>
                          <tr>
                            <td width="23" bgcolor="#ECF2E3" class="tbl_grc"><%=v_subjseq%></td>
                            <td class="tbl_grc"><%=v_mname%></td>
                            <td class="tbl_grc"><%=v_propstart%> ~ <%=v_propend%></td>
                            <td class="tbl_grc"><%=v_edustart%></td>
                            <td class="tbl_grc"><%=v_eduperiod%>주</td>
                            <td class="tbl_grc"><%=v_seq_biyong%></td>
                            <td class="tbl_grc"><%=v_statusString%></td>
                          </tr>
<%      }
            if(k == 0)
            { %>
                <tr>
                    <td class="tbl_grc" align="center" colspan="8">해당 차수가 없습니다.</td>
                </tr>
<%          }
        %>
                        </table-->
                        <!-- 차수 끝 -->
                        <!--table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5"></td>
                          </tr>
                          <tr>
                            <td colspan="2" align="right"><a href="#top"><img src="/images/user/kocca/button/b_top.gif" border="0"></a></td>
                          </tr>
                        </table-->

                        <!-- 수강신청 끝 -->

</form>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->