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
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process        = box.getString("p_process");
    String  v_rprocess       = box.getString("p_rprocess");
    String  v_subj           = box.getString("p_subj");
    String  v_subjnm         = box.getString("p_subjnm");
    String  v_isonoff        = box.getString("p_isonoff");
    String  v_upperclassnm   = box.getString("p_upperclassnm");
    String  v_upperclass     = box.getString("p_upperclass");
    String  v_rupperclass    = box.getString("p_rupperclass");
    String  v_select         = box.getString("p_select");
    String  v_actionurl      = box.getString("p_actionurl");
    String  v_lsearchsubject = box.getString("p_lsearchsubject");
    String  v_gubun          = box.getString("p_gubun");

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
    String  v_purl        = "";
    String  v_bookfilenamereal = "";
    String  v_bookfilenamenew = "";
    String  v_introducefilenamereal = "";
    String  v_introducefilenamenew  = "";
    String  v_introducefilenamenew_view  = "";

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


    if(v_isonoff.equals("ON"))      {
        v_preurl        =  EduEtc1Bean.make_previewURL(v_subj);
    }

    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";



    Hashtable outputData = new Hashtable();
    String v_rndcreditreq     = "";
    String v_rndcreditchoice  = "";

     DataBox dbox = (DataBox)request.getAttribute("subjectPreview");

     v_subj           = dbox.getString("d_subj");
     v_subjtarget     = dbox.getString("d_subjtarget");
     v_edumans        = dbox.getString("d_edumans");
     v_edumans        = dbox.getString("d_edumans");
     v_intro          = dbox.getString("d_intro");
     v_explain        = dbox.getString("d_explain");
     v_owner          = dbox.getString("d_owner");
     v_ownerman       = dbox.getString("d_ownerman");
     v_ownertel       = dbox.getString("d_ownertel");
     v_muserid        = dbox.getString("d_muserid");
     v_preurl         = dbox.getString("d_preurl");
     v_edutimes       = dbox.getInt("d_edutimes");
     v_edudays        = dbox.getInt("d_edudays");
     v_edutype        = dbox.getString("d_edutype");
     v_place          = dbox.getString("d_place");
     v_bookfilenamereal = dbox.getString("d_bookfilenamereal");
     v_bookfilenamenew = dbox.getString("d_bookfilenamenew");
     v_mastertel      = dbox.getString("d_musertel");
     v_isonoff_value  = (v_isonoff.equals("ON"))?"사이버":"집합";
     v_intro          =  StringManager.replace(v_intro,"\r\n","<br>");
     v_explain        =  StringManager.replace(v_explain,"\r\n","<br>");

     v_rndcreditreq    = dbox.getString("d_rndcreditreq");
     v_rndcreditchoice = dbox.getString("d_rndcreditchoice");
     v_isgoyong        = dbox.getString("d_isgoyong");
     if (v_isgoyong.equals("Y")) v_isgoyong_value = "적용";
     else                        v_isgoyong_value = "미적용";
     v_introducefilenamereal = dbox.getString("d_introducefilenamereal");
     v_introducefilenamenew  = dbox.getString("d_introducefilenamenew");
     if (v_introducefilenamenew.equals("")) v_introducefilenamenew_view = "/images/user/apply/le_img.jpg";
     else                                   v_introducefilenamenew_view = "/upload/bulletin/" + v_introducefilenamenew;

     //v_owner = getCodenm.get_owner(v_subj);

     if (v_owner.equals("credu")) {                 //크레듀
         v_masteruser        = v_ownerman;
     } else {
         outputData = probean.getMeberInfo(v_muserid);
           v_masteruser = (String)outputData.get("name");
           v_mastermail = (String)outputData.get("email");
     }

    // 시설정보
    String v_inscode  = "";
    String v_insname  = "";
    String v_insurl   = "";
    String v_realpath = "";
    DataBox dbox2 = (DataBox)request.getAttribute("institution");
    if (dbox2 != null) {
        v_inscode  = dbox2.getString("d_code");
        v_insname  = dbox2.getString("d_insname");
        v_insurl   = dbox2.getString("d_url");
        if (!v_insurl.equals("")) v_realpath = conf.getProperty("dir.institutionrealpath") + v_inscode + "/" + v_insurl;
    }

	String site   = "outclass/"+box.getSession("site");   // 이미지, CSS 경로
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/images/user/<%=site%>/user_style1.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

// 맛보기
function whenPreShow(url) {
    open_window("openShow",url,"100","100","800","717","","","",true,true);
}


//콘텐츠 평가보기
function ContentResearchInfo(){
  //zu_ContentRearch_L.jsp
//  document.form1.target = "_self";
/*
  document.form1.target = opener;
  document.form1.p_process.value = "ContentResearch";
  document.form1.action="/servlet/controller.outclass.OutClassServlet";
  document.form1.submit();
*/
  opener.location = "/servlet/controller.outclass.OutClassServlet?p_process=ContentResearch&p_subj=<%=v_subj%>";
  self.close();
}


//신청자명단
function proposeName(){
  document.form1.target = "_self";
  document.form1.p_process.value = "ProposeListPage";
  document.form1.action="/servlet/controller.outclass.OutClassServlet";
  document.form1.submit();

}

//강좌리스트
function whenLectureList(){
   window.self.name = "LectureList";
   var url = "/servlet/controller.outclass.OutClassServlet";
   var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=747,height=550,resizable=no');
   document.form1.target="LectureList1";
   document.form1.p_process.value="LectureList";
   //document.form1.action="/servlet/controller.outclass.OutClassServlet";
   document.form1.submit();

}

// 시설보기
function insOpen(url) {
    open_window("openIns",url,"100","100","748","600","","","","yes",true);
}
//-->
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<form name="form1" method="post" action="">
    <input type='hidden' name='p_subj' value="<%=v_subj%>">
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_useproposeapproval'>
    <input type='hidden' name='p_subjnm' value="<%=v_subjnm%>">
    <input type='hidden' name='p_process' value="<%=v_process%>" >
    <input type='hidden' name='p_rprocess' value="<%=v_rprocess%>">
    <input type='hidden' name='p_actionurl' value="<%=v_actionurl%>">
    <input type='hidden' name='p_purl' value="<%=v_purl%>">
    <input type='hidden' name='p_course' value="000000">
    <input type='hidden' name='p_isonoff' value="<%=v_isonoff%>">
    <input type='hidden' name='p_upperclass' value="<%=v_upperclass%>">
    <input type='hidden' name='p_rupperclass' value="<%=v_rupperclass%>">
    <input type='hidden' name='p_upperclassnm' value="<%=v_upperclassnm%>">
    <input type='hidden' name='p_select' value="<%=v_select%>">
    <input type='hidden' name='p_lsearchsubject' value="<%=v_lsearchsubject%>">
    <input name="gyear" type="hidden" value="<%=FormatDate.getDate("yyyy")%>">
   
    <input type='hidden' name='p_gubun' value="<%=v_gubun%>">
    <input type='hidden' name='p_returnurl' value="/servlet/controller.outclass.OutClassServlet">


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
                <td width="128" valign="top"><img src="/images/user/<%=site%>/st_lecinfo.gif" ></td>
                <td width="547" align="right" valign="top"> 

                 <table width="400" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="167">&nbsp;</td>
                      <td width="54">&nbsp;</td>
                      <td width="95">&nbsp;</td>
                      <td width="84">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="5" colspan="2"></td>
              </tr>
              <tr valign="top"> 
                <td height="20" colspan="2"> 
                  <!-- 과정소개 table -->
                  <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                    <tr  class="lcolor"> 
                      <td height="3" colspan="2" class="lcolor"></td>
                    </tr>
                    <tr> 
                      <td width="135" class="tbl_gtit2">과정명</td>
                      <td height="76" >
                        <table width="439" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="439" class="tblfont_titleft">
                              <table width="130" height="100" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" >
                                <tr>
                                  <td align="center"><img src="<%=v_introducefilenamenew_view%>"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td class="tblfont_titleft"><%=v_subjnm%></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
<!--
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">교육형태</td>
                            <td class="tblfont_gleft"><%=v_isonoff_value%></td>
                          </tr>
-->
                          <tr> 
                            <td height="26" class="tbl_gtit2">교육대상</td>
                            <td class="tblfont_gleft"><%=v_edumans%></td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">교육목표</td>
                            <td class="tblfont_gleft"><%=v_intro%></td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">교육내용</td>
                            <td class="tblfont_gleft"><%=v_explain%></td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">교육기간</td>
                            <td class="tblfont_gleft"><%=v_edudays%> 일</td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">교육시간</td>
                            <td class="tblfont_gleft"><%=v_edutimes%>시간</td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">교육장소</td>
                            <td class="tblfont_gleft"><%=v_insname%> 
                            (<%if(v_edutype.equals("01")){out.print("출퇴근");}else{out.print("합숙");}%>)
                            <% if (!v_realpath.equals("")) { %>
                              <a href="javascript:insOpen('<%=v_realpath%>')" onfocus=this.blur()><img src="/images/user/<%=site%>/btn_view.gif" border="0" ></a>
                            <% }                          %>
                            </td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">신청자명단</td>
                            <td class="tblfont_gleft"><a href="javascript:proposeName()" onfocus=this.blur()><img src="/images/user/<%=site%>/btn_view.gif" border="0" ></a></td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">과정운영자</td>
                            <td class="tblfont_gleft"><%=v_masteruser%>&nbsp;<%=v_mastertel%><!--a href='mailto:<%=v_mastermail%>'><%=v_masteruser%></a>  (☎ : <%=v_mastertel%>)--></td>
                          </tr>
                          <tr>
                            <td class="tbl_gtit2">교재/첨부파일</td>
                            <td class="tblfont_gleft"><% if (!v_bookfilenamereal.equals("")) { %><a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%=v_bookfilenamenew%>&p_realfile=<%=v_bookfilenamereal%>'><%=v_bookfilenamereal%></a><% }else{ %>-<%}%></td>
                          </tr>
<!--
                          <tr> 
                            <td class="tbl_gtit2">차수정보조회</td>
                            <td class="tblfont_gleft">자세한 차수정보조회를 원하시면 차수로 등록된 숫자를 클릭하여 주십시오.</td>
                          </tr>
-->
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="5" colspan="2"></td>
              </tr>
              <tr align="right">
                <td height="20" colspan="2"><a href="javascript:self.close()"><img src="/images/user/<%=site%>/btn_close.gif" border="0"></a></td>
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
<form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>