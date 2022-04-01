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
    String  v_purl        = "";
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
    int     i           =  0;

    v_preurl        =  EduEtc1Bean.make_previewURL(v_subj);

    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function whenPreShow(url) {
    open_window("openShow",url,"100","100","800","717","","","",true,true);
}

function whenSubjList(){

    document.form1.target = "_self";
    document.form1.p_process.value=document.form1.p_rprocess.value;
    document.form1.action=document.form1.p_actionurl.value;
    //alert(document.form1.p_actionurl.value);
    document.form1.submit();
}

function whenSelect(){
    document.form1.target = "_self";
    document.form1.action="/servlet/controller.propose.ProposeCourseServlet?leftmenu=1";
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.submit();
}

function whenSelSubjInfo(subjseq){
        //ff =document.form1;

        var url = "/servlet/controller.propose.ProposeCourseServlet";
        
        var new_Open = window.open(url,"seqInfo",'scrollbars=yes,width=680,height=600,resizable=no');
        document.form1.target = "seqInfo";
        document.form1.p_process.value = "SeqPreviewPage";
        document.form1.p_subjseq.value = subjseq;
        document.form1.submit();
}


//수강신청
function whenSubjPropose(subj,year,subjseq,useproposeapproval,gubun) {
   //alert("hi");

   document.form1.target = "_self";
   document.form1.p_subj.value = subj;
   document.form1.p_year.value = year;
   document.form1.p_subjseq.value = subjseq;
   document.form1.p_useproposeapproval.value = useproposeapproval;
   //document.form1.p_gubun.value = gubun;

   if(useproposeapproval == "N"){
     document.form1.p_process.value = "SubjectEduPropose";
   }
   else{
     window.self.name = "winChiefApproval";
     var url = "/servlet/controller.propose.ProposeCourseServlet";
     var new_Open = window.open(url,"proposeChief",'scrollbars=yes,width=550,height=400,scrollbars=no,resizable=no');

     document.form1.target = "proposeChief";
     document.form1.p_process.value = "SubjectEduProposeChief";
   }

   document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
   document.form1.submit();
}

function ContentResearchInfo(){
  //zu_ContentRearch_L.jsp
  document.form1.target = "_self";
  document.form1.p_process.value = "ContentResearch";
  document.form1.action="/servlet/controller.propose.ProposeCourseServlet";
  document.form1.submit();
}

function goPersonHistory(){
  document.form1.target = "_self";
  document.form1.p_process.value = "EducationSubjectPage";
  document.form1.action="/servlet/controller.study.MyClassServlet";
  document.form1.submit();
}


function whenLectureList(){
   window.self.name = "LectureList";
   var url = "/servlet/controller.propose.ProposeCourseServlet";
   var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=800,height=717,scrollbars=no,resizable=no');
   document.form1.target="LectureList1";
   document.form1.p_process.value="LectureList";
   //document.form1.action="/servlet/controller.propose.ProposeCourseServlet";
   document.form1.submit();
   
}

//-->
</script>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
  <table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="730" height="100%">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#bebebe" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" bgcolor="#FFFFFF" class="body_color">
                    <br>
            <!-- 과정소개 table  -->                     
            <table width="675" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
              <tr> 
                <td width="323" valign="top"><img src="/images/user/apply/st_leclist.gif"></td>
                <td width="352" align="right" valign="top">&nbsp; </td>
              </tr>
              <tr> 
                <td height="5" colspan="2"></td>
              </tr>
              <tr valign="top"> 
                <td height="20" colspan="2"> 
                  <!-- 강의목차 table -->
                  <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                    <tr  class="lcolor"> 
                      <td height="3" colspan="2" class="lcolor"></td>
                    </tr>
                    <tr> 
                      <td width="147" class="tbl_gtit2">일차</td>
                      <td width="508" class="tbl_gtit">소개</td>
                    </tr>

<%
                ArrayList list1 = (ArrayList)request.getAttribute("lessonList");
                if(list1 != null){
                for(i = 0; i < list1.size(); i++) {
                  DataBox dbox2  = (DataBox)list1.get(i);
                  v_lesson    =   dbox2.getString("d_lesson");
                  v_sdesc     =   dbox2.getString("d_sdesc");
%>
                    <tr> 
                      <td class="tbl_grc"><%=v_lesson%></td>
                      <td class="tbl_grc"><%=v_sdesc%></td>
                    </tr>
<%}
              }
              if(i == 0){
%>
                    <tr>
                      <td class="tbl_grc" colspan="2">등록된 강좌가 없습니다.</td>
                    </tr>
<%
              }
%>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="5" colspan="2"></td>
              </tr>
              <tr align="right">
                <td height="20" colspan="2"><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" border="0"></a></td>
              </tr>
              <tr> 
                <td height="20" colspan="2"></td>
              </tr>
            </table>
        <!-- 과정소개 table 끝 -->
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
