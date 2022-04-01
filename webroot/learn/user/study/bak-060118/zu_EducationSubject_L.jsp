<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_EducationSubject_L.jsp
//  3. 개      요: 수강과정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "MyClassBean" class = "com.credu.study.MyClassBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String yeslearn_url = conf.getProperty("yeslearn.url.value");

    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");
    
    //out.println(v_user_id);
    

    String  v_grcode     =  "";
    String  v_gyear      =  "";
    String  v_grseq      =  "";
    String  v_upperclass =  "";
    String  v_isonoff    =  "";
    String  v_course     =  "";
    String  v_cyear      =  "";
    String  v_courseseq  =  "";
    String  v_coursenm   =  "";
    String  v_subj       =  "";
    String  v_subjseqgr  =  "";
    String  v_year       =  "";
    String  v_subjnm     =  "";
    String  v_subjseq    =  "";
    String  v_propend    =  "";
    String  v_edustart   =  "";
    String  v_eduend     =  "";
    String  v_eduurl     =  "";
    int     v_ieduurl    = 0;  
    String  v_isnewcourse= "";
    String  v_chkfirst   =  "";
    String  v_chkfinal   =  "";
    String  v_cancelkind =  "";
    String  v_cpsubj     = "";
    String  v_cpsubjseq  = "";
    String  v_middleclassnm = "";
    String  v_preurl    =  "";
    String v_hasPreviewObj = "";

    String  v_isgraduated  = "";
    String  v_subjtarget   = "";
    String  v_isonoff_value= "";
    String  v_isgraduated_value="";
    String  v_today        =  FormatDate.getDate("yyyyMMdd");
    String  v_propenddate  = "";
    String  v_edustartdate = "";
    String  v_eduenddate   = "";
    String  v_reviewend    = "";
    String  v_cancelcheck  = "";
    String  v_proposetype  = "";
    String  v_isoutsourcing = "";
    String  v_isablereview = "";
    String  v_stuchkfinal  = "";
    String  v_company      = "";
    String  v_edulist_value= "";
    String  v_contenttype   ="";
    String  v_aesseq        = "";
    String  v_aescontentid  = "";
    String  v_kind          = "";
    String  v_gubun         = "";
    String  v_serno         = "";
    String  v_pkey          = "";

    String  v_base64resno = "";
    int     v_total     =  0;
    int     i           =  0;

    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("EducationSubjectList");   //study.MyClassBean.selectEducationSubjectList(box)
    list2 = (ArrayList)request.getAttribute("ProposeSubjectList");     //study.MyClassBean.selectProposeSubjectList(box)
    list3 = (ArrayList)request.getAttribute("GraduationSubjectList");  //study.MyClassBean.selectGraduationSubjectList(box)
    //out.println(list3.size());

    // 학습제약조건
    SubjGongAdminBean control = new SubjGongAdminBean();
    int v_controlstudy = 0;

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topMyClass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

// 학습창 OPEN
function studyOpen(url, iurl) {
<%      if (box.getSession("gadmin").equals("ZZ")) {  %> 
            var s_url = url+"&p_iurl="+iurl;
//    alert(s_url);
           if (url == '') {
               alert("정보가 없습니다");
           } else {
			   if (iurl == 0)
				open_window('openApp',s_url,0,0,0,0,false,false,true,true,true);
			   else 
				open_window('openApp',s_url,0,0,1024,768,false,false,'yes','yes','yes');
           }
<%      } else {                                      %>
            alert ("학습자 권한이 아닙니다.");
<%      }                                             %>
}

// 학습시간제한
function controlstudy() {
    alert("지금은 학습가능 시간이 아닙니다.");
    return; 
}

// 학습현황 OPEN
function whenEdulist(subjnm, iurl, iseduend, url) {
//alert(url);
    var ulink = url + "&p_subjnm=" + subjnm + "&p_iurl=" + iurl + "&p_iseduend=" + iseduend;
    var new_Open = window.open(ulink,"eduList",'scrollbars=no,width=350,height=240,scrollbars=yes,resizable=no');
}



//수료증 출력
function suRoyJeung(subj,year,subjseq,userid, kind){
    window.self.name = "SuryoJeung";
    var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=667,height=600,scrollbars=no,resizable=yes');
    document.form1.target = "openSuryoJeungPrint";
    document.form1.action='/servlet/controller.study.MyClassServlet';
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_userid.value = userid;
    document.form1.p_kind.value = kind;
    document.form1.p_process.value = 'SuryoJeung';
    document.form1.submit();
}

// 과정상세정보 POPUP
function whenSubjInfoPopup(subj,subjnm,isonoff){
    window.self.name = "SubjList";
    window.open("", "openSubjInfo", "scrollbars=no,width=750,height=550,scrollbars=yes,resizable=yes'");
    document.form1.target = "openSubjInfo"

    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_process.value = 'SubjectPreviewPopup';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.submit();

    document.form1.target = window.self.name; 
}

// 맛보기 OPEN
function whenPreShow(url,subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window("openShow",url,"100","100","800","717","","","",true,true);
}

// 맛보기
function studyOpen1(url,subj) {
    // 로그
    prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
    open_window("openShow",prelog_url,"0","0","10","10","","","",true,true);

    open_window('openApp',url,0,0,0,0,false,false,true,true,true);
}
//-->
</SCRIPT>

<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_subj'      value ="">
    <input type='hidden' name='p_year'      value ="">
    <input type='hidden' name='p_subjseq'   value ="">
    <input type='hidden' name='p_subjnm'    value ="">
    <input type='hidden' name='p_userid'    value ="">
    <input type='hidden' name='p_gubun'     value ="">
    <input type='hidden' name='p_rejectedreason'     value ="">
    <input type='hidden' name='p_isonoff'   value ="">
    <input type='hidden' name='p_kind' value="">

          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 나의 강의실 &gt; 나의 강의실</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/myclass/<%=tem_subimgpath%>/tit_myclass.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  
      <table width="675" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="474" valign="top"><img src="/images/user/myclass/st_study1.gif"> 
          </td>
          <td width="201" align="right" valign="top">&nbsp;</td>
        </tr>
        <tr> 
          <td height="5" colspan="2"></td>
        </tr>
        <tr> 
          <td colspan="2" class="font_ex">학습하기 버튼을 클릭하시면 학습창으로 이동합니다. 학습하시고자 하는 과정의 학습하기 버튼을 클릭하세요.</td>
        </tr>
        <tr> 
          <td height="5" colspan="2"></td>
        </tr>
        <tr> 
          <td colspan="2"> 
            <!-- 학습중인과정table  -->
            <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor"> 
                <td height="3" colspan="5" class="linecolor_my"></td>
              </tr>
              <tr> 
                <td width="70" class="tbl_ptit2">중분류</td>
                <td width="280" class="tbl_ptit">과정명</td>
                <td width="139" class="tbl_ptit2">교육기간</td>
                <td width="79" class="tbl_ptit">학습현황</td>
                <td width="63"  class="tbl_ptit2">학습하기</td>
              </tr>
              <%
                        for(i = 0; i < list1.size(); i++) {
                            //MyClassData data1 = (MyClassData)list1.get(i);
                            DataBox dbox    = (DataBox)list1.get(i);

                            v_upperclass    =  dbox.getString("d_upperclass");
                            v_isonoff       =  dbox.getString("d_isonoff");
                            v_course        =  dbox.getString("d_course");
                            v_cyear         =  dbox.getString("d_cyear");
                            v_courseseq     =  dbox.getString("d_courseseq");
                            v_coursenm      =  dbox.getString("d_coursenm");
                            v_subj          =  dbox.getString("d_subj");
                            v_year          =  dbox.getString("d_year");
                            v_subjnm        =  dbox.getString("d_subjnm");
                            v_subjseq       =  dbox.getString("d_subjseq");
                            v_subjseqgr     =  dbox.getString("d_subjseqgr");
                            v_edustart      =  dbox.getString("d_edustart");
                            v_eduend        =  dbox.getString("d_eduend");
                            v_eduurl        =  dbox.getString("d_eduurl");
                            v_subjtarget    =  dbox.getString("d_subjtarget");
                            v_isnewcourse   =  dbox.getString("d_isnewcourse");
                            v_isoutsourcing =  dbox.getString("d_isoutsourcing");
                            v_cpsubj        =  dbox.getString("d_cpsubj");
                            v_cpsubjseq     =  dbox.getString("d_cpsubjseq");
                            v_stuchkfinal   =  dbox.getString("d_stuchkfinal");
                            v_company       =  dbox.getString("d_company");
                            v_chkfinal      =  dbox.getString("d_chkfinal");
                            v_middleclassnm      =  dbox.getString("d_middleclassnm");
                            v_contenttype   =  dbox.getString("d_contenttype");
                            v_aesseq        =  dbox.getString("d_aesseq");

                            v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                            v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

                            if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                            }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }
                            if(v_subjtarget.equals(""))     {   v_subjtarget    =   "전사원";   }

                            // 학습url=외주인 경우..
                            if(v_eduurl.equals("")) { v_ieduurl = 0; }else{ v_ieduurl = 1; }
                            
                            // 학습현황
                            v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id;
                            //학습URL?FIELD1=사번&FIELD2=교육년도&FIELD3=외주업체과정코드&FIELD4=외주업체과정차수
                            //FIELD1="+v_userid+"&FIELD2="+v_gyear+"&FIELD3="+v_cpsubj+"&FIELD4="+v_cpsubjseq;
                            
                            //out.println(v_isoutsourcing.equals("Y"));

                            if (v_isoutsourcing.equals("Y")) {                 //위탁과정일때
                                //v_base64resno = StringManager.BASE64Encode(box.getSession("resno"));
                                //v_eduurl = "http://www.credu.com/pls/cyber/zasp3.smotor_jumpjsp?p_subj=" +v_subj+ "&p_resno=" + v_base64resno;  //외부과정이 아닌경우
//                                v_eduurl = v_eduurl+"?FIELD1="+v_user_id+"&FIELD2=" +v_year +"&FIELD3=" +v_cpsubj+"&FIELD4="+ v_cpsubjseq + "&FIELD99=" + v_company + "&FIELD100=N";

                                v_eduurl = "/servlet/controller.contents.EduStart";
                                v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "&FIELD99=" + v_company+"&FIELD100=N"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
                            } else {
                                // 예스런일때
                                if (v_contenttype.equals("Y")) {
                                v_eduurl = "/servlet/controller.contents.EduStart";
                                v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_subj + "&FIELD4=" + v_subjseq + "&FIELD5=" + v_aesseq + "&FIELD99=" + v_company + "&FIELD100=N"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
                                } else {
                                    v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);  //외부과정이 아닌경우
                                    v_eduurl +=  "&contenttype=" + v_contenttype;
                                }
                            }

                            if(v_isonoff.equals("OFF")){
                                v_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
                            }
                            // 학습시간제한
                            v_controlstudy = control.allowStudy(box, v_subj, v_year, v_subjseq);
%>
              <tr> 
                <td class="tbl_grc"><%=v_middleclassnm%></td>
                <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
                <td class="tbl_grc"><%=v_edustart%>~<%=v_eduend%></td>
                <td class="tbl_grc"><% if(v_isonoff.equals("ON")){ %>
                  <a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','','<%=v_edulist_value%>')"><img src="/images/user/button/btn_m_studyview.gif" border="0"></a>
                  <%}else{%>
                  -
                  <%}%></td>
                <td class="tbl_grc">
                <% if(v_isonoff.equals("ON")){ %>
                    <% if (v_controlstudy >0) {         %>
                          <a href="javascript:controlstudy()"><img src="/images/user/button/btn_m_studydo.gif" width="60" height="17" border="0"></a>
                    <% } else {                         %>
                          <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')"><img src="/images/user/button/btn_m_studydo.gif" width="60" height="17" border="0"></a>
                    <% }                                %>
                  <%}else{%>
                  -
                  <%}%>
                  </td>
              </tr>
              <%
                        }
                        if(i == 0){ 
%>
              <tr> 
                <td class="tbl_grc" colspan="7">학습중인 과정이 없습니다.</td>
              </tr>
              <%
                        }
%>
              <input  type='hidden' name='p_totCount1' value='<%=(i-1)%>'>
            </table></td>
        </tr>
        <tr> 
          <td height="20" colspan="2"></td>
        </tr>
        <tr> 
          <td colspan="2"><img src="/images/user/myclass/st_study2.gif"></td>
        </tr>
        <tr> 
          <td height="5" colspan="2"></td>
        </tr>
        <tr> 
          <td colspan="2" class="font_ex">수강신청/안내 &gt; 수강신청 메뉴에서 수강신청을 하시면 원하시는 
            과정의 수강이 가능합니다.<br>
            수강신청과 승인이 마쳐졌으나 아직 학습기간이 아닌 과정들입니다. 학습기간이 되면 자동적으로 학습중인 과정으로 <br>
            옮겨지게 됩니다.</td>
        </tr>
        <tr> 
          <td height="5" colspan="2"></td>
        </tr>
        <tr> 
          <td colspan="2"> 
            <!-- 학습예정과정 -->
            <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor"> 
                <td height="3" colspan="4" class="linecolor_my"></td>
              </tr>
              <tr> 
                <td width="70" class="tbl_ptit2">중분류</td>
                <td width="330" class="tbl_ptit">과정명</td>
                <td width="139" class="tbl_ptit2">교육기간</td>
                <td width="100" class="tbl_ptit">맛보기</td>
              </tr>
              <%

                        for(i = 0; i < list2.size(); i++) {
                            DataBox dbox    = (DataBox)list2.get(i);
                            v_upperclass         =  dbox.getString("d_upperclass");
                            v_isonoff            =  dbox.getString("d_isonoff");
                            v_course             =  dbox.getString("d_course");
                            v_cyear              =  dbox.getString("d_cyear");
                            v_courseseq          =  dbox.getString("d_courseseq");
                            v_coursenm           =  dbox.getString("d_coursenm");
                            v_subj               =  dbox.getString("d_subj");
                            v_subjseq            =  dbox.getString("d_subjseq");
                            v_subjseqgr          =  dbox.getString("d_subjseqgr");
                            v_year               =  dbox.getString("d_year");
                            v_subjnm             =  dbox.getString("d_subjnm");
                            v_subjseq            =  dbox.getString("d_subjseq");
                            v_propend            =  dbox.getString("d_propend");
                            v_edustart           =  dbox.getString("d_edustart");
                            v_eduend             =  dbox.getString("d_eduend");
                            v_eduurl             =  dbox.getString("d_eduurl");
                            v_isnewcourse        =  dbox.getString("d_isnewcourse");
                            v_subjtarget         =  dbox.getString("d_subjtarget");
                            v_chkfinal           =  dbox.getString("d_chkfinal");
                            v_middleclassnm      =  dbox.getString("d_middleclassnm");
                            v_contenttype   =  dbox.getString("d_contenttype");
                            v_aesseq        =  dbox.getString("d_aesseq");
                            v_aescontentid  =  dbox.getString("d_aescontentid");

                            v_propenddate   =  FormatDate.getFormatDate(v_propend,"yyyyMMdd");
                            v_edustartdate  =  FormatDate.getFormatDate(v_edustart,"yyyyMMdd");

                            v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                            v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

                            // 맛보기
                            v_preurl        =  EduEtc1Bean.make_previewURL(v_subj);
                            v_preurl         = dbox.getString("d_preurl");
                            v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);
                            if (v_contenttype.equals("Y")) {
                                v_preurl = yeslearn_url + "/anytime/contents/"+v_aescontentid+"/guest/default.htm";
                            }

                            if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                            }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }

                            if(v_subjtarget.equals(""))     {   v_subjtarget    =   "전사원";   }

                            if(StringManager.toInt(v_today) > StringManager.toInt(v_propenddate) && StringManager.toInt(v_today) < StringManager.toInt(v_edustartdate)) v_cancelcheck = "N";
                            else                                                    v_cancelcheck = "Y";


%>
              <tr> 
                <td class="tbl_grc"><%=v_middleclassnm%></td>
                <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
                <td class="tbl_grc"><%=v_edustart %>~<%=v_eduend %></td>
                <td class="tbl_grc"><% if (v_hasPreviewObj.equals("Y")) { %>
                  <a href="javascript:studyOpen1('/servlet/controller.contents.EduStart?p_subj=<%=v_subj%>&p_year=2000&p_subjseq=0001&p_ispreview=Y', '<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/button/btn_m_preview.gif" border="0"></a>
                  <% } else { %>
                  <% if(!v_preurl.equals("")){ %>
                  <a href="javascript:whenPreShow('<%=v_preurl%>', '<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/button/btn_m_preview.gif" border="0"></a>
                  <%}%>
                  <% } %></td>
              </tr>
              <%
                        }
                        if(i == 0){ 
%>
              <tr> 
                <td class="tbl_grc" colspan="7">학습예정중인 과정이 없습니다.</td>
              </tr>
              <%
                        }
%>
              <input  type='hidden' name='p_totCount' value='<%=(i-1)%>'>
            </table></td>
        </tr>
        <tr> 
          <td height="20" colspan="2"></td>
        </tr>
        <tr> 
          <td colspan="2"><img src="/images/user/myclass/st_study3.gif"></td>
        </tr>
        <tr> 
          <td height="5" colspan="2"></td>
        </tr>
        <tr> 
          <td colspan="2" class="font_ex">학습종료한 과정의 목록입니다. 학습종료후 1년간 확인이가능하며 복습가능여부에 
            따라 복습이가능합니다.</td>
        </tr>
        <tr>
          <td colspan="2" class="font_ex"><img src="/images/user/myclass/class_text.gif"></td>
        </tr>
        <tr> 
          <td height="3" colspan="2"></td>
        </tr>
        <tr valign="top"> 
          <td colspan="2"> 
            <!-- 학습을 마친과정 -->
            <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor"> 
                <td height="3" colspan="7" class="linecolor_my"></td>
              </tr>
              <tr> 
                <td width="70" class="tbl_ptit2">중분류</td>
                <td width="134" class="tbl_ptit">과정명</td>
                <td width="129" class="tbl_ptit2">교육기간</td>
                <td width="129" class="tbl_ptit">복습기간</td>
                <td width="48" class="tbl_ptit2">성적보기</td>
                <td width="48" class="tbl_ptit">복습하기</td>
                <td width="48" class="tbl_ptit2">수료증</td>
              </tr>
              <%
                    String v_score = "";
                    String v_iscareer ="";
                        for(i = 0; i < list3.size(); i++) {
                            DataBox dbox = (DataBox)list3.get(i);
                            v_upperclass    = dbox.getString("d_upperclass");
                            v_isonoff       = dbox.getString("d_isonoff");
                            v_course        = dbox.getString("d_course");
                            v_cyear         = dbox.getString("d_cyear");
                            v_courseseq     = dbox.getString("d_courseseq");
                            v_coursenm      = dbox.getString("d_coursenm");
                            v_subj          = dbox.getString("d_subj");
                            v_year          = dbox.getString("d_year");
                            v_subjnm        = dbox.getString("d_subjnm");
                            v_subjseq       = dbox.getString("d_subjseq");
                            v_edustart      = dbox.getString("d_edustart");
                            v_eduend        = dbox.getString("d_eduend");
                            v_eduurl        = dbox.getString("d_eduurl");
                            v_isgraduated   = dbox.getString("d_isgraduated");
                            v_isnewcourse   = dbox.getString("d_isnewcourse");
                            v_subjtarget    = dbox.getString("d_subjtarget");
                            v_isoutsourcing = dbox.getString("d_isoutsourcing");
                            v_isablereview  = dbox.getString("d_isablereview");
                            v_score         = dbox.getString("d_score");
                            v_iscareer      = dbox.getString("d_iscareer");
                            v_cpsubj        = dbox.getString("d_cpsubj");
                            v_cpsubjseq     = dbox.getString("d_cpsubjseq");
                            v_company       = dbox.getString("d_company");
                            v_middleclassnm      =  dbox.getString("d_middleclassnm");
                            v_contenttype   =  dbox.getString("d_contenttype");
                            v_aesseq        =  dbox.getString("d_aesseq");
                            v_aescontentid  =  dbox.getString("d_aescontentid");
                            v_kind          = dbox.getString("d_kind");
                            v_gubun         = dbox.getString("d_gubun");
                            v_serno         = dbox.getString("d_serno");
                            v_pkey          = dbox.getString("d_pkey");
                            //out.println("out="+v_isoutsourcing);

                            v_eduenddate = FormatDate.getFormatDate(v_eduend,"yyyyMMdd");


                            v_reviewend = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "year", 1), "yyyy/MM/dd");

                            v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                            v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

                            if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                            }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }

                            if(v_isgraduated.equals("N")){ v_isgraduated_value = "미수료";
                            }else if(v_isgraduated.equals("Y")){ v_isgraduated_value = "수료";  }

                            if(v_subjtarget.equals(""))     {   v_subjtarget    =   "전사원";   }


                            // 학습url 있으면 외주인 경우..
                            //if(v_eduurl.equals("")) { v_ieduurl = 0; }else{ v_ieduurl = 1; }

                            // 학습현황
                            v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id;

                            // 과거 자료이면
                            if (v_kind.equals("2")) {
                                if (v_gubun.equals("ji")) { // 직무이면 
                                    v_eduurl = yeslearn_url + "/selfupcampus/cybercampus/classroom/enter_student_room.asp?GroupStatus=99&txtGroupId="+v_aescontentid+"&txtUserId="+v_user_id;
									v_ieduurl = 1;
                                } else if (v_gubun.equals("la")){  // 어학이면
                                    v_eduurl = v_eduurl;
									v_ieduurl = 1;
                                }
                                v_edulist_value = "/servlet/controller.contents.EduStart?p_process=eduList&p_subj="+ v_pkey +"&p_year="+ v_year +"&p_subjseq="+v_subjseq+"&p_userid="+v_user_id+"&p_kind=2";
                            } else {
                                if (v_isoutsourcing.equals("Y")) {                 //크레듀
                                    v_eduurl = v_eduurl+"?FIELD1="+v_user_id+"&FIELD2="+ v_year+"&FIELD3="+v_cpsubj+"&FIELD4=" +v_cpsubjseq+ "&FIELD99="+ v_company+ "&FIELD100=Y";
                                    //v_eduurl = "/servlet/controller.contents.EduStart";
                                    //v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "&FIELD99=" + v_company+"&FIELD100=Y"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
									v_ieduurl = 1;
                                } else {
                                    // 예스런일때
                                    if (v_contenttype.equals("Y")) {
    //                                    v_eduurl = "/servlet/controller.contents.EduStart";
    //                                    v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "&FIELD5=" + v_aesseq + "&FIELD99=" + v_company + "&FIELD100=Y"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
                                        v_eduurl = yeslearn_url + "/selfupcampus/cybercampus/classroom/enter_student_room.asp?GroupStatus=99&txtGroupId="+v_aescontentid+"&txtUserId="+v_user_id;
										v_ieduurl = 1;
                                    } else {
                                      //v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);  //외부과정이 아닌경우
                                      v_eduurl = EduEtc1Bean.make_eduURL(v_subj,"2000","0001");  //외부과정이 아닌경우
                                      v_eduurl +=  "&contenttype=" + v_contenttype+"&p_review=Y";
									  v_ieduurl = 0;
                                    }
                                }
                            }

%>
              <tr> 
                <td class="tbl_grc"><%=v_middleclassnm%></td>
                <td class="tbl_gleft">
                <%if (!v_subj.equals("")) {  %>
                  <a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a>
                <%}else{%>
                  <%=v_subjnm%>
                <%}%>
                </td>
                <td class="tbl_grc"><%=v_edustart %>~<%=v_eduend %></td>
                <%if(v_isonoff.equals("ON")&&v_isablereview.equals("Y")){%>
                <td class="tbl_grc"><%=v_eduend%>~<%=v_reviewend%></td>
                <%}else{%>
                <td class="tbl_grc">-</td>
                <%}%>
                <td class="tbl_grc">
				  <a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','Y','<%=v_edulist_value%>')"><img src="/images/user/button/btn_m_sungview.gif" border="0"></a>
				</td>
                <td class="tbl_grc">
				<%if(v_isablereview.equals("Y")){%> 
				  <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')"><img src="/images/user/button/btn_m_exdo.gif" border="0"></a> 
                  <%}%> </td>
                <td class="tbl_grc">
<%            if (v_isgraduated.equals("Y")) {    %>
<%              if (v_kind.equals("2")) {    %>
                <a href="javascript:suRoyJeung('<%=v_pkey%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>','2')"><img src="/images/user/button/btn_m_print.gif" border="0"></a>
<%              } else {                     %>
                <a href="javascript:suRoyJeung('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>','1')"><img src="/images/user/button/btn_m_print.gif" border="0"></a>
<%              }                            %>
<%            }                            %>
                </td>
              </tr>
              <%
                        }
                        if(i == 0){
%>
              <tr> 
                <td height="12" class="tbl_grc" colspan="6">학습을 마친과정이 없습니다.</td>
              </tr>
              <%
                        }
%>
            </table></td>
        </tr>
        <tr> 
          <td colspan="2">&nbsp;</td>
        </tr>
      </table>
                </td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
</form>

              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->