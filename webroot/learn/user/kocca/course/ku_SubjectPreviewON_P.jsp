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
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
//    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String upload_url = conf.getProperty("url.upload");

    String  v_process       = box.getString("p_process");
    String  v_rprocess      = box.getString("p_rprocess");
    String  v_isonoff       = box.getStringDefault("p_isonoff","ON");
    String  v_sphere   = box.getString("p_sphere");
    String  v_middleclass   = box.getString("p_middleclass");
    String  v_subjtype      = box.getStringDefault("p_subjtype","alway");
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
    String  v_mastermail=  "";
    String  v_mastertel =  "";
    String  v_isgoyong  =  "";
    String  v_subjtarget=  "";
    String  v_edumans   =  "";
    String  v_intro     =  "";
    String  v_explain   =  "";
    String  v_isonoff_value="";
    String  v_prop_img  =  "";
    String  v_isgoyong_value="";
    String  v_edutype   =  "";
    String  v_masteruser=  "";
    String  v_env       =  "";
    String  v_owner     =  "";  //소유주코드
    String  v_ownerman  =  "";  //크래듀운영자명
    String  v_ownertel  =  "";  //담당자 전화번호
    String  v_isessential = ""; // 과정구분
    String  v_memo        = "";


    String  v_introducefilenamereal = "";
    String  v_introducefilenamenew  = "";
    String  v_introducefilenamenew_view  = "";
    String  v_contenttype  = "";
    String  v_aescontentid = "";
    String  v_studentlimitch="";

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
    String  v_isessential_view = "";
    String  v_lesson    =  "";
    String  v_desc   = "";
    String  v_lecture   =  "";
    String  v_lecturedate= "";
    String  v_biyong_view = "";
    String  v_studentlimit_view  = "";
    String  v_isoutsourcing  ="";
    String  v_conturl        = "";
    int     i           =  0;

//    v_preurl               =  EduEtc1Bean.make_previewURL(v_subj);
    String v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);

    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";

    String v_classname  = "";
    String v_eduperiod  = "";


    Hashtable outputData = new Hashtable();
    DataBox dbox = (DataBox)request.getAttribute("subjectPreview");

    if (dbox != null) {
        v_subj           = dbox.getString("d_subj");
        v_subjnm         = dbox.getString("d_subjnm");
        v_subjtarget     = dbox.getString("d_subjtarget");
        v_intro          = dbox.getString("d_intro");
        v_edumans        = dbox.getString("d_edumans");
        v_explain        = dbox.getString("d_explain");
        v_owner          = dbox.getString("d_owner");
        v_ownerman       = dbox.getString("d_ownerman");
        v_ownertel       = dbox.getString("d_ownertel");
        v_muserid        = dbox.getString("d_muserid");
        v_preurl         = dbox.getString("d_preurl");
        v_mastertel      = dbox.getString("d_musertel");
        v_isoutsourcing  = dbox.getString("d_isoutsourcing");
        v_conturl        = dbox.getString("d_conturl");
        v_contenttype    = dbox.getString("d_contenttype");
        v_aescontentid   = dbox.getString("d_aescontentid");
        v_isgoyong       = dbox.getString("d_isgoyong");

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
        v_memo          = dbox.getString("d_memo");

        v_isonoff_value  = (v_isonoff.equals("ON"))?"사이버":"집합";
        v_intro          =  StringManager.replace(v_intro,"\r\n","<br>");
        v_explain        =  StringManager.replace(v_explain,"\r\n","<br>");
        v_mastertel      =  StringManager.replace(v_mastertel,"\r\n","<br>");
        v_memo           =  StringManager.replace(v_memo,"\r\n","<br>");
        
        v_career      =  StringManager.replace(v_career,"\r\n","<br><img src='/images/user/game/course/bl_intro.gif' width='9' height='8'>&nbsp;");
        v_book        =  StringManager.replace(v_book,"\r\n","<br><img src='/images/user/game/course/bl_intro.gif' width='9' height='8'>&nbsp;");

        v_isgoyong        = dbox.getString("d_isgoyong");
        if (v_isgoyong.equals("Y")) v_isgoyong_value = "적용";
        else                        v_isgoyong_value = "미적용";
        v_introducefilenamereal = dbox.getString("d_introducefilenamereal");
        v_introducefilenamenew  = dbox.getString("d_introducefilenamenew");
        if (v_introducefilenamenew.equals("")) v_introducefilenamenew_view = "/images/user/kocca/course/course_img.gif";
        else                                   v_introducefilenamenew_view = upload_url + "bulletin/" + v_introducefilenamenew;
        //v_owner = getCodenm.get_owner(v_subj);

        if (v_owner.equals("credu")) {                 //크레듀
           v_masteruser        = v_ownerman;
        } else {
           outputData = probean.getMeberInfo(v_muserid);
           v_masteruser = (String)outputData.get("name");
           v_mastermail = (String)outputData.get("email");
           //v_masteruser        = memberBean.getUserInfo(v_muserid);
        }
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

<html>

<head>
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
<script>
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
// 교수소개 팝업
function open_introduceList() {
    window.self.name = "introduceList";     //opener 객체에 name 을 지정한다
    open_window("openIntroduceList","","100","0","700","590","1");

    document.form1.scsubj.value = "<%=v_subj%>";
    document.form1.userid.value = "<%=v_muserid%>";
    document.form1.target = "openIntroduceList";
    document.form1.action = "/servlet/controller.tutor.TutorMessageAdminServlet";
    document.form1.p_process.value = "introduceList";
    document.form1.submit();
    document.form1.target = window.self.name;
}
// 맛보기
function whenPreShow(url, subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window("openShow",url,"100","100","800","717","","","",true,true);
}

// 맛보기
function studyOpen(url,subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window('openApp',url,0,0,0,0,false,false,true,true,true);
}
</script>
</head>
<body topmargin="0" leftmargin="0">
<form name="form1" method="post" bgcolor="#FFFFFF">
    <input type='hidden' name='p_process' value="<%=v_process%>" >
    <input type='hidden' name='p_subj' value="<%=v_subj%>">
    <input type='hidden' name='p_disposition' value="<%=v_disposition%>">
    <input type='hidden' name='p_sphere' value="<%=v_sphere%>">
    <input type='hidden' name='p_middleclass' value="<%=v_middleclass%>">
    <input type='hidden' name='p_subjtype' value="<%=v_subjtype%>">
    <input type='hidden' name='p_userid' value="">
    <input type='hidden' name='userid'   value="">
    <input type='hidden' name='scsubj'   value="">

<table width="673" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/user/kocca/apply/paywindow_top.gif" width="673" height="12"></td>
  </tr>
  <tr>
    <td background="/images/user/kocca/apply/paywindow_bg.gif">
	<table width="667" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="6">&nbsp;</td>
          <td width="667" align="right"><img src="/images/user/kocca/course/pop_tit_course.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="25" background="/images/user/kocca/apply/paywindow_bg.gif">&nbsp;</td>
  </tr>
</table>
<table width="673" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td colspan="2" background="/images/user/kocca/apply/paywindow_bg.gif">
	<table width="673" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="26">&nbsp;</td>
          <td width="623">
            <!--표시작1 -->
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr > 
                <td height="2" colspan="3" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
              </tr>
              <tr> 
                <td height="2" colspan="3"></td>
              </tr>
              <tr> 
                <td width="85" height="142" class="tb_bg_alway"><img src="/images/user/kocca/course/text_course_name02.gif"></td>
                <td width="13">&nbsp;</td>
                <td valign="middle">
				<table width="439" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="2" class="tbl_bleft"><strong>[ <%=v_subjnm%> ]</strong></td>
                    </tr>
                    <tr> 
                      <td align="center" >
					  <table width="137" height="100" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" >
                          <tr> 
                            <td width="125" align="center">
                            <img src="<%= v_introducefilenamenew_view%>">
                            <!--  <img src="/images/user/kocca/course/course_img.gif"> -->
                            </td>
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
            <!--table width="620" border="0" cellspacing="0" cellpadding="0">
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
            <table width="620" border="0" cellspacing="0" cellpadding="0">
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
            <table width="620" border="0" cellspacing="0" cellpadding="0">
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
            <table width="620" border="0" cellspacing="0" cellpadding="0">
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
            <!--table width="620" border="0" cellspacing="0" cellpadding="0">
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
            <!--table width="620" border="0" cellspacing="0" cellpadding="0">
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
            <table width="620" border="0" cellspacing="0" cellpadding="0">
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
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr > 
                <td height="2" colspan="4" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
              </tr>
              <tr> 
                <td height="2" colspan="4"></td>
              </tr>
              <tr> 
                <td width="85" class="tb_bg_alway"><img src="/images/user/kocca/course/text_teacher.gif"></td>
                <td width="13">&nbsp;</td>
                <td class="tbl_bleft"><strong><%=v_mname%></strong><strong>&nbsp;&nbsp;<a href="javascript:open_introduceList()"><img src="/images/user/kocca/button/b_introduce_pro.gif" border="0"></a></strong></td>
                <td class="tbl_bleft">&nbsp;</td>
              </tr>
              <tr> 
                <td height="2" colspan="4"></td>
              </tr>
			   <tr > 
                <td height="2" colspan="4" background="/images/user/kocca/course/bg_dot_line04.gif"></td>
              </tr>
            </table></td>
          <td width="24">&nbsp;</td>
        </tr>
        <tr> 
          <td height="10" colspan="3"></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td><div align="right">&nbsp;&nbsp;<a href="javascript:self.close()"><img src="/images/user/kocca/button/btn_close.gif" width="42" height="19" border="0"></a></div></td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="3">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td colspan="2"><img src="/images/user/kocca/apply/paywindow_bottom.gif" width="673" height="19"></td>
  </tr>
</table>
</form>
</body>
</html>