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


     //v_owner = getCodenm.get_owner(v_subj);

     if (v_owner.equals("credu")) {                 //크레듀
         v_masteruser        = v_ownerman;
     } else {
         outputData = probean.getMeberInfo(v_muserid);
           v_masteruser = (String)outputData.get("name");
           v_mastermail = (String)outputData.get("email");
     }
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
    // 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();  }
    }

    // 과정검색
    function subjSearch() {
        if (document.form1.p_lsearchtext.value=="") {
            alert("검색어를 입력해주세요");
            return;
        }
        document.form1.action = "/servlet/controller.propose.MainSubjSearchServlet";   // 
        document.form1.p_process.value = "SubjSearch";
        document.form1.submit();
    }

// 맛보기
function whenPreShow(url) {
    open_window("openShow",url,"100","100","800","717","","","",true,true);
}


// 과정리스트
function whenSubjList(){
    document.form1.target = "_self";
    document.form1.p_process.value=document.form1.p_rprocess.value;
    document.form1.action=document.form1.p_actionurl.value;
    //alert(document.form1.p_actionurl.value);
    document.form1.submit();
}


//신청자명단
function proposeName(){
  document.form1.target = "_self";
  document.form1.p_process.value = "ProposeListPage";
  document.form1.action="/servlet/controller.propose.ProposeCourseServlet";
  document.form1.submit();

}

// 차수정보조회
function whenSelSubjInfo(subjseq,year){

        var url = "/servlet/controller.propose.ProposeCourseServlet";

        var new_Open = window.open(url,"seqInfo",'scrollbars=yes,width=680,height=600,resizable=no');
        document.form1.target = "seqInfo";
        document.form1.p_process.value = "SeqPreviewPage";
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        //document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq,subjnm) {

   if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
     return;
   }

   document.form1.target = "_self";
   document.form1.p_subj.value = subj;
   document.form1.p_year.value = year;
   document.form1.p_subjseq.value = subjseq;
   document.form1.p_process.value = "SubjectEduPropose";
   document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
   document.form1.submit();
}

//나의강의실
function goPersonHistory(){
  document.form1.target = "_self";
  document.form1.p_process.value = "EducationSubjectPage";
  document.form1.action="/servlet/controller.study.MyClassServlet";
  document.form1.submit();
}

//강좌리스트
function whenLectureList(){
   window.self.name = "LectureList";
   var url = "/servlet/controller.propose.ProposeCourseServlet";
   var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=747,height=550,resizable=no');
   document.form1.target="LectureList1";
   document.form1.p_process.value="LectureList";
   //document.form1.action="/servlet/controller.propose.ProposeCourseServlet";
   document.form1.submit();

}

// 시설보기
function insOpen(url) {
    open_window("openIns",url,"100","100","748","600","","","","yes",true);
}
//-->
</script>


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
    <input type='hidden' name='p_returnurl' value="/servlet/controller.propose.ProposeCourseServlet">

          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20" align="center" valign="top" class="location">
                  HOME &gt; 수강신청/안내 &gt; 수강신청</td>
              </tr>
              <tr>
                <td align="center" valign="top">
                <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="75"><img src="/images/user/support/search_img.gif" alt="과정검색"></td>
                      <td width="75">
                        <select name="p_lsearch">
                          <option value="isonoff">구분</option>
                          <option value="upperclass">분류</option>
                          <option value="subjnm">과정명</option>
                        </select>
                      </td>
                      <td width="132"><input type="text" class="input" name="p_lsearchtext" onkeypress="search_enter(event)" size="20"></td>
                      <td width="394"><a href="javascript:subjSearch()"><img src="/images/user/button/btn_search.gif" alt="검색"></a></td>
                    </tr>
                    <tr>
                      <td colspan="4"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr>
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top"><img src="/images/user/apply/<%=tem_subimgpath%>/tit_apply.jpg"></td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>

                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="565" valign="top"><img src="/images/user/apply/st_lecinfo.gif"></td>
                      <td width="110" align="right" valign="top"><a href="javascript:whenSubjList()" onfocus="this.blur()"><img src="/images/user/button/btn_c_totalview.gif" alt="전체과정보기" border="0"></a>
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
                            <td height="76" ><table width="439" border="0" cellspacing="0" cellpadding="0">
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
                            <td class="tblfont_gleft"><%=v_insname%> (<%if(v_edutype.equals("01")){out.print("출퇴근");}else{out.print("합숙");}%>)&nbsp;&nbsp;
                            <% if (!v_realpath.equals("")) { %>
                              <a href="javascript:insOpen('<%=v_realpath%>')" onfocus=this.blur()><img src="/images/user/button/btn_view.gif" alt="보기" border="0" ></a>
                            <% }                          %>
                            </td>
                          </tr>
                          <tr> 
                            <td class="tbl_gtit2">신청자명단</td>
                            <td class="tblfont_gleft"><a href="javascript:proposeName()" onfocus=this.blur()><img src="/images/user/button/btn_view.gif" alt="보기" border="0" ></a></td>
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
                      <td height="20" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2"><img src="/images/user/apply/st_edudate.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td colspan="2">
                      <!-- 교육일정 table  -->
                      <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="4" class="lcolor"></td>
                          </tr>
                          <tr> 
                            <td width="200" class="tbl_gtit2">수강신청기간</td>
                            <td width="200" class="tbl_gtit">교육기간</td>
                            <td width="135" class="tbl_gtit2">정원</td>
                            <td width="135"  class="tbl_gtit">신청</td>
                          </tr>
<%
                      ArrayList list = (ArrayList)request.getAttribute("subjseqList");
                      //out.println(list.size());
                      String v_ispropose  = "";
                      String v_proposetype = "";
                      String v_statusString = "";
                      String v_subjseqgr = "";
                      String v_today = "";
                      int    v_iedustart = 0;


                      for(int k = 0; k < list.size(); k++) {
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
                        v_proposetype        = dbox1.getString("d_proposetype");

                        v_today = FormatDate.getDate("yyyyMMddhh");

                        if(v_edustart.equals("")){v_iedustart = 0;}
                        else{v_iedustart = Integer.parseInt(v_edustart);}

                        if(v_iedustart >= Integer.parseInt(v_today)){
                              v_statusString = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"')\" onfocus=this.blur()><img src='/images/user/button/btn_apply.gif' border=0></a>";

                             if(v_ispropose.substring(0,1).equals("Y")){
                               v_statusString = "<a href='javascript:goPersonHistory()'><img src='/images/user/button/btn_apply_ing.gif' border=0></a>";
                             }
                        }else{
                          v_statusString = "-";
                        }

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
<!--
                                                <td class=boardskin1_textn><a href="javascript:whenSelSubjInfo('<%=v_subjseq%>','<%=v_year%>')"><b><%=Integer.parseInt(v_subjseqgr)%></b></a></td>
-->
                            <td class="tbl_grc"><%=v_propstart%> ~ <%=v_propend%></td>
                            <td class="tbl_grc"><%=v_edustart%> ~ <%=v_eduend%></td>
                            <td class="tbl_grc"><%=v_studentlimit%></td>
                            <td class="tbl_grc"><%=v_statusString%></td>
                          </tr>
<%
                    }
%>
                        </table>

                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2">&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
</form>
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