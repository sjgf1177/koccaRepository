<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: gu_SubjectPreview.jsp
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
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String upload_url = conf.getProperty("url.upload");

    String  v_process      = box.getString("p_process");
    String  v_rprocess     = box.getString("p_rprocess");
    String  v_subj         = box.getString("p_subj");
    String  v_subjnm       = box.getString("p_subjnm");
    String  v_isonoff      = box.getString("p_isonoff");
    String  v_upperclassnm = box.getString("p_upperclassnm");
    String  v_upperclass   = box.getString("p_upperclass");
    String  v_select       = box.getString("p_select");
    String  v_actionurl    = box.getString("p_actionurl");
    String  v_lsearchsubject = box.getString("p_lsearchsubject");
    String  v_gubun       = box.getString("p_gubun");


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
    String  v_ownertel  =  "";  //담당자 전화번호
    String  v_isessential = ""; // 과정구분
    String  v_purl        = "";
    String  v_introducefilenamereal = "";
    String  v_introducefilenamenew  = "";
    String  v_introducefilenamenew_view  = "";
    String  v_contenttype  = "";
    String  v_ownerman = "";

    int     v_studentlimit =0;
    int     v_biyong    =  0;
    int     v_wstep     =  0;
    int     v_wmtest    =  0;
    int     v_wftest    =  0;
    int     v_wreport   =  0;
    int     v_wact      =  0;
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
    String  v_isessential_view = "";
    String  v_lesson    =  "";
    String  v_lecture   =  "";
    String  v_lecturedate= "";
    String  v_biyong_view = "";
    String  v_studentlimit_view  = "";
    String  v_isoutsourcing  ="";
    String  v_conturl        = "";
	String	v_classname		= "";
    int     i           =  0;

    v_preurl        =  EduEtc1Bean.make_previewURL(v_subj);
    String v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);


    Hashtable outputData = new Hashtable();
    DataBox dbox = (DataBox)request.getAttribute("subjectPreview");

    v_edumans        = dbox.getString("d_edumans");
    v_subj           = dbox.getString("d_subj");
	v_subjnm		 = dbox.getString("d_subjnm");
    v_subjtarget     = dbox.getString("d_subjtarget");
    v_intro          = dbox.getString("d_intro");
    v_explain        = dbox.getString("d_explain");
    v_owner          = dbox.getString("d_owner");
    v_isonoff	     = dbox.getString("d_isonoff");
    v_ownertel       = dbox.getString("d_ownertel");
    v_muserid        = dbox.getString("d_muserid");
    v_preurl         = dbox.getString("d_preurl");
    v_mastertel      = dbox.getString("d_musertel");
    v_isoutsourcing  = dbox.getString("d_isoutsourcing");
    v_conturl        = dbox.getString("d_conturl");
    v_contenttype    = dbox.getString("d_contenttype");
	v_ownerman		 = dbox.getString("d_ownerman");
    v_classname     = dbox.getString("d_classname");

    v_isonoff_value  = (v_isonoff.equals("ON"))?"사이버":"집합";
    v_intro          =  StringManager.replace(v_intro,"\r\n","<br>");
    v_explain        =  StringManager.replace(v_explain,"\r\n","<br>");
    v_mastertel      =  StringManager.replace(v_mastertel,"\r\n","<br>");

    v_isgoyong        = dbox.getString("d_isgoyong");
    if (v_isgoyong.equals("Y")) v_isgoyong_value = "적용";
    else                        v_isgoyong_value = "미적용";
    v_introducefilenamereal = dbox.getString("d_introducefilenamereal");
    v_introducefilenamenew  = dbox.getString("d_introducefilenamenew");
    if (v_introducefilenamenew.equals("")) v_introducefilenamenew_view = "/images/user/apply/le_img.jpg";
    else                                   v_introducefilenamenew_view = upload_url + "bulletin/" + v_introducefilenamenew;
    //v_owner = getCodenm.get_owner(v_subj);

    if (v_owner.equals("credu")) {                 //크레듀
       v_masteruser        = v_ownerman;
    } else {
       outputData = probean.getMeberInfo(v_muserid);
       v_masteruser = (String)outputData.get("name");
       v_mastermail = (String)outputData.get("email");
    }
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--


//콘텐츠 평가보기
function ContentResearchInfo(){
  opener.location = "/servlet/controller.propose.ProposeCourseServlet?p_process=ContentResearch&p_subj=<%=v_subj%>";
  self.close();
}


//강의목차보기
function whenLectureList(isoutsourcing, conturl){
   window.self.name = "LectureList";
   if(isoutsourcing == "N"){
     var url = "/servlet/controller.propose.ProposeCourseServlet";
     var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=746,height=550,resizable=no');
     document.form1.target="LectureList1";
     document.form1.p_process.value="LectureList";
     document.form1.submit();
   }else{
     url = conturl;
     var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=800,height=600,resizable=no');
   }
}

// 맛보기
function whenPreShow(url, subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"100","100","800","717","","","",true,true);

    open_window("openShow",url,"100","100","800","717","","","",true,true);
}

// 맛보기
function studyOpen(url, subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window('openApp',url,0,0,0,0,false,false,true,true,true);
}
//-->
</SCRIPT>

<body bgcolor="F6F6F6">
<form name="form1" method="post" action="">
    <input type='hidden' name='p_process' value="<%=v_process%>" >
    <input type='hidden' name='p_select' value="<%=v_select%>">
    <input type='hidden' name='p_gubun' value="<%=v_gubun%>">

    <input type='hidden' name='p_subj' value="<%=v_subj%>">
    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_subjnm' value="<%=v_subjnm%>">
    <input type='hidden' name='p_rprocess' value="<%=v_rprocess%>">
    <input type='hidden' name='p_actionurl' value="<%=v_actionurl%>">
    <input type='hidden' name='p_purl' value="<%=v_purl%>">
    <input type='hidden' name='p_course' value="000000">
    <input type='hidden' name='p_isonoff' value="<%=v_isonoff%>">
    <input type='hidden' name='p_upperclass' value="<%=v_upperclass%>">
    <input type='hidden' name='p_upperclassnm' value="<%=v_upperclassnm%>">
    <input type='hidden' name='p_lsearchsubject' value="<%=v_lsearchsubject%>">

    <input type='hidden' name='p_returnurl' value="/servlet/controller.propose.ProposeCourseServlet">
<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/game/mystudy/tit_pop_course.gif"></td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF"> 
            <!-- 과정소개 -->
            <table cellpadding=0 cellspacing=0 width=620>
              <col width=87>
              <col width=3>
              <col width=630>
              <tr height=30 valign=bottom> 
                <td style="padding:0 0 4 10;color:#000000;">과정명</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td style="padding:0 0 4 10;color:#37669a;font-weight:bold;"><%=v_subjnm%> 
                  <!--img src="/images/user/game/apply/icon01.gif align=absmiddle"> 
                  <img src="/images/user/game/apply/icon02.gif align=absmiddle"--></td>
              </tr>
              <tr height=1> 
                <td colspan=3 background="/images/user/game/apply/tbl_dot.gif"></td>
              </tr>
              <tr height=30 valign=bottom> 
                <td style="padding:0 0 4 10;color:#000000;">과정 분류명</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td style="padding:0 0 4 10;"><%=v_classname%></td>
              </tr>
              <tr height=1> 
                <td colspan=3 background="/images/user/game/apply/tbl_dot.gif"></td>
              </tr>
              <tr height=30 valign=bottom> 
                <td style="padding:0 0 4 10;color:#000000;">교육구분</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td style="padding:0 0 4 10;"><%=v_isonoff_value%></td>
              </tr>
              <tr height=1> 
                <td colspan=3 background="/images/user/game/apply/tbl_dot.gif"></td>
              </tr>
              <tr height=30 valign=bottom> 
                <td style="padding:0 0 4 10;color:#000000;">교육목표</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td style="padding:8 0 4 10;"><%=v_intro%></td>
              </tr>
              <tr height=1> 
                <td colspan=3 background="/images/user/game/apply/tbl_dot.gif"></td>
              </tr>
              <tr height=30 valign=bottom> 
                <td style="padding:0 0 4 10;color:#000000;">교육내용</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td style="padding:8 0 4 10;"><%=v_explain%></td>
              </tr>
              <tr height=1> 
                <td colspan=3 background="/images/user/game/apply/tbl_dot.gif"></td>
              </tr>
              <tr height=30 valign=bottom> 
                <td style="padding:0 0 4 10;color:#000000;">교육담당자</td>
                <td><img src="/images/user/game/apply/tbl_bar.gif"></td>
                <td style="padding:0 0 4 10;"><%=v_mastertel%></td>
              </tr>
              <tr height=4> 
                <td colspan=3 bgcolor=#d9e5f3></td>
              </tr>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:self.close()"> <img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table></td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
