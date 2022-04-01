<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_EducationSubject_L.jsp
//  3. 개      요: 나의강의실
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: Kim.jin.suk 2005 12. 19
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
    String server_url = conf.getProperty("kocca.url.value");

    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");
    String  v_user_nm   = box.getSession("name");
    String  v_grcode    = box.getSession("tem_grcode");
    
    //out.println(v_user_id);

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
    String  v_hasPreviewObj = "";

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
    int	    v_reviewdays   = 0;
    String  v_reviewtype   = "";
    String  v_stuchkfinal  = "";
    String  v_company      = "";
    String  v_edulist_value= "";
    String  v_contenttype   ="";
    String  v_kind          = "";
    String  v_gubun         = "";
    String  v_serno         = "";
    String  v_pkey          = "";
    
    String  v_tmp_subj  = "";
    String  v_iscourseYn = "";
    String  v_taxString     = "";
    String  v_biyong        = "";
    String  v_isuseYn       = "";
    String  v_class_value   = "";
    String  v_class_nm      = "";

    String  v_base64resno = "";
    int     v_total     =  0;
    int     i           =  0;
    int     l           =  0;
    int     v_subjcnt   = 0;

    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("EducationSubjectList");   //study.MyClassBean.selectEducationSubjectList(box)
    list2 = (ArrayList)request.getAttribute("ProposeSubjectList");     //study.MyClassBean.selectProposeSubjectList(box)
    list3 = (ArrayList)request.getAttribute("GraduationSubjectList");  //study.MyClassBean.selectGraduationSubjectList(box)
    //out.println(list3.size());

    // 학습제약조건
    //SubjGongAdminBean control = new SubjGongAdminBean();
    int v_controlstudy = 0;

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topMyclass.jsp"%>
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
    //document.form1.action='/servlet/controller.study.MyClassServlet';
    document.form1.action = "/servlet/controller.polity.DiplomaAdminServlet";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_scsubjseq.value = subjseq;
    document.form1.p_userid.value = userid;
    document.form1.p_kind.value = kind;
    //document.form1.p_process.value = 'SuryoJeung';;
    document.form1.p_process.value = "DiplomaPrint";
    document.form1.submit();
}

// 과정상세정보 POPUP
function whenSubjInfoPopup(subj,subjnm,isonoff){
    window.self.name = "SubjList";
    window.open("", "openSubjInfo", "scrollbars=no,width=700,height=550,scrollbars=yes,resizable=yes'");
    document.form1.target = "openSubjInfo"

    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_process.value = 'SubjectPreviewPopup';
    document.form1.action='/servlet/controller.propose.KProposeCourseServlet';
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

function inputTax(subj, subjseq, year, subjnm)
{
    var isOk = 0;

    window.self.name = "TaxBill";
    window.open("","TaxBillPage",'scrollbars=no,width=673,height=400,scrollbars=no,resizable=yes');

    document.form1.target = "TaxBillPage";
    document.form1.action='/servlet/controller.polity.TaxAdminServlet';
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_process.value = 'InsertPage';
    document.form1.submit();
}
//-->
</SCRIPT>

<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_subj'      value ="">
    <input type='hidden' name='p_year'      value ="">
    <input type='hidden' name='p_subjseq'   value ="">
    <input type='hidden' name='p_scsubjseq'   value ="">
    <input type='hidden' name='p_subjnm'    value ="">
    <input type='hidden' name='p_userid'    value ="">
    <input type='hidden' name='p_gubun'     value ="">
    <input type='hidden' name='p_rejectedreason'     value ="">
    <input type='hidden' name='p_isonoff'   value ="">
    <input type='hidden' name='p_kind' value="">
    <input type='hidden' name='p_grcode' value="<%=v_grcode %>">
    <input type='hidden' name='s_grcode' value="<%=v_grcode %>">
    
    
             <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td  class="location" > HOME > 마이클래스&gt; 강의실</td>
              </tr>
            </table>
            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/user/kocca/myclass/<%=tem_subimgpath%>/tit_classroom.gif"></td>
              </tr>
              <tr> 
                <td height="20"></td>
              </tr>
            </table>
            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="5"><img src="/images/user/kocca/myclass/bar_classroom.gif" width="680" height="24"></td>
              </tr>
              <tr> 
                <td width="45" valign="top"><img src="/images/user/kocca/myclass/bar_classroom_left.gif" width="45" height="96"></td>
                <td width="287" valign="top">
                <table width="287" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="3" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td width="70" height="20" background="/images/user/kocca/myclass/name_bg.gif"><div align="center"><strong><font color="#000000"><%=v_user_nm%></font></strong></div></td>
                      <td width="217"><img src="/images/user/kocca/myclass/text_classroom.gif" width="149" height="20"></td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="2"></td>
                    </tr>
                  </table>
                  <table width="287" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="20" height="33">&nbsp;</td>
                      <td colspan="2"> 변명 중에서도 가장 어리석고 못난 변명은 <br> 
                        &nbsp;&nbsp;&nbsp;&nbsp;"시간이 없어서"라는 변명이다. - 에디슨 </td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="3"></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                      <td width="245"><div align="right">[<%=FormatDate.getDate("yyyy-MM-dd") %>]</div></td>
                      <td width="22">&nbsp;</td>
                    </tr>
                  </table></td>
                <td width="16" valign="top"><img src="/images/user/kocca/myclass/vline.gif" width="16" height="96"></td>
                <td width="305"><img src="/images/user/kocca/myclass/im_clasroom.jpg"></td>
                <td width="27" valign="top"><img src="/images/user/kocca/myclass/bar_classroom_right.gif" width="27" height="96"></td>
              </tr>
              <tr> 
                <td height="10" colspan="5"></td>
              </tr>
            </table>
<!--학습중인과정 시작 -->
            <table width="680" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="/images/user/kocca/myclass/st_eduing_course.gif"></td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              <tr> 
                <td class="font_ex">학습하시고자 하는 과정의 학습하기 버튼을 클릭하시면 학습창으로 이동합니다. </td>
              </tr>
              <tr>
                <td height="9"></td>
                                          </tr>
            </table>
            <table width="680" border="2" cellspacing="0" 
             cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor"> 
                <td height="3" colspan="6" class="linecolor_my"></td>
              </tr>
              <tr> 
                <td width="82" class="tbl_ptit2">분류</td>
                <td width="257" class="tbl_ptit" colspan="2">과정명</td>
                <td width="146" class="tbl_ptit2">교육기간</td>
                <td width="86" class="tbl_ptit">학습현황</td>
                <td width="65"  class="tbl_ptit2">학습하기</td>
                <!--임시처리-->
                <!--td width="80" class="tbl_ptit">세금계산서</td-->
              </tr>
              <tr> 
                <td height="5" colspan="6"></td>
              </tr>
              
               <%
               v_tmp_subj = "";
               l = 0;
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
                v_middleclassnm =  dbox.getString("d_middleclassnm");
                v_contenttype   =  dbox.getString("d_contenttype");
                v_biyong        =  dbox.getString("d_biyong");
                v_isuseYn       =  dbox.getString("d_isuseyn");
                
                v_iscourseYn    = dbox.getString("d_isbelongcourse");
                v_subjcnt       = dbox.getInt("d_subjcnt");

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

                if (v_isoutsourcing.equals("Y")) { 

                    v_eduurl = "/servlet/controller.contents.EduStart";
                    v_eduurl += "?FIELD1=" + v_user_id + "&FIELD2=" + v_year + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "&FIELD99=" + v_company+"&FIELD100=N"+ "&contenttype=" + v_contenttype+ "&p_subj=" + v_subj +"&p_year="+ v_year +"&p_subjseq="+v_subjseq;
                } else {
                        v_eduurl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);  //외부과정이 아닌경우
                        v_eduurl +=  "&contenttype=" + v_contenttype;
                }

                if(v_isonoff.equals("OFF")){
                    v_eduurl = "/servlet/controller.study.OffEduStartServlet?p_process=selectFrame&p_subj="+v_subj+"&p_year="+v_year+"&p_subjseq="+v_subjseq;
                }
                // 학습시간제한
               // v_controlstudy = control.allowStudy(box, v_subj, v_year, v_subjseq);
                
                if(v_iscourseYn.equals("Y"))
                {
                    v_class_value   = v_course;
                    v_class_nm      = v_coursenm;
                }
                else
                {
                    v_class_value   = v_subj;
                    v_class_nm      = v_subjnm;
                }

                // 유료 과정일 경우
                if(!v_biyong.equals("0") && v_isuseYn.equals("N"))
                {
                   v_taxString = "<a href=\"javascript:inputTax('" + v_class_value+"','" + v_subjseq+"','" + v_year+"','" + v_class_nm + "')\"><img src='/images/user/kocca/button/b_bill.gif' border='0'></a>";
                }
                else
                {
                    v_taxString = "-";
                }
%>
              <tr> 
<% if(v_iscourseYn.equals("Y"))
    { 
        if(l == v_subjcnt) v_tmp_subj = "";
        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
        {
             l = 1;
            %>
            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_middleclassnm%></td>
            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_coursenm%><br><%=v_subjseq%>차</td>
            <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
            <td class="tbl_grc"><%=v_edustart%>~<%=v_eduend%></td>
                <td class="tbl_grc"><% if(v_isonoff.equals("ON")){ %>
                  <a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','','<%=v_edulist_value%>')"><img src="/images/user/kocca/button/b_view_edu.gif" border="0"></a>
                  <%}else{%>
                  -
                  <%}%></td>
                <td class="tbl_grc">
                <% if(v_isonoff.equals("ON")){ %>
                    <% if (v_controlstudy >0) {         %>
                          <a href="javascript:controlstudy()"><img src="/images/user/kocca/button/b_goedu.gif" border="0"></a>
                    <% } else {                         %>
                          <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')"><img src="/images/user/kocca/button/b_goedu.gif" border="0"></a>
                    <% }                                %>
                  <%}else{%>
                  -
                  <%}%>
                  </td>
                  <!--임시처리-->
                  <!--td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_taxString%></td-->
    <%  
        }else{ 
            l++;
        %>
            <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
            <td class="tbl_grc"><%=v_edustart%>~<%=v_eduend%></td>
                <td class="tbl_grc"><% if(v_isonoff.equals("ON")){ %>
                  <a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','','<%=v_edulist_value%>')"><img src="/images/user/kocca/button/b_view_edu.gif" border="0"></a>
                  <%}else{%>
                  -
                  <%}%></td>
                <td class="tbl_grc">
                <% if(v_isonoff.equals("ON")){ %>
                    <% if (v_controlstudy >0) {         %>
                          <a href="javascript:controlstudy()"><img src="/images/user/kocca/button/b_goedu.gif" border="0"></a>
                    <% } else {                         %>
                          <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')"><img src="/images/user/kocca/button/b_goedu.gif" border="0"></a>
                    <% }                                %>
                  <%}else{%>
                  -
                  <%}%>
                  </td>
<%      }v_tmp_subj = v_course;
    } else { %>

            <td class="tbl_grc"><%=v_middleclassnm%></td>
            <td class="tbl_bleft" colspan="2"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td><td class="tbl_grc"><%=v_edustart%>~<%=v_eduend%></td>
                <td class="tbl_grc"><% if(v_isonoff.equals("ON")){ %>
                  <a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','','<%=v_edulist_value%>')"><img src="/images/user/kocca/button/b_view_edu.gif" border="0"></a>
                  <%}else{%>
                  -
                  <%}%></td>
                <td class="tbl_grc">
                <% if(v_isonoff.equals("ON")){ %>
                    <% if (v_controlstudy >0) {         %>
                          <a href="javascript:controlstudy()"><img src="/images/user/kocca/button/b_goedu.gif" border="0"></a>
                    <% } else {                         %>
                          <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')"><img src="/images/user/kocca/button/b_goedu.gif" border="0"></a>
                    <% }                                %>
                  <%}else{%>
                  -
                  <%}%>
                  </td>
                  <!--임시처리-->
                  <!--td class="tbl_grc"><%=v_taxString%></td-->
<% } %>


                
              </tr>
              <%
                        }
                        if(i == 0){ 
%>
              <tr> 
                <td class="tbl_grc" colspan="6">학습중인 과정이 없습니다.</td>
              </tr>
              <%
                        }
%>
                        </table>
                        
<!--학습예정과정 시작 -->
                    <table width="680" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                          <td height=22>&nbsp;</td>
                        </tr>
                        <tr> 
                          <td><img src="/images/user/kocca/myclass/st_educome_course.gif"></td>
                        </tr>
                        <tr> 
                          <td height="15"></td>
                        </tr>
                        <tr> 
                          <td class="font_ex">수강신청 메뉴에서 수강신청을 하시면 원하시는 과정의 수강이 가능합니다.<br>
                            수강신청과 승인이 마쳐졌으나 아직 학습기간이 아닌 과정들입니다. 학습기간이 
                            되면 자동적으로 학습중인 과정으로 <br>
                            옮겨지게 됩니다.. </td>
                        </tr>
                        <tr> 
                          <td height="9"></td>
                        </tr>
                      </table> 
                      <table width="680" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                        <tr  class="lcolor"> 
                          <td height="3" colspan="5" class="linecolor_my"></td>
                        </tr>
                        <tr> 
                          <td width="82" class="tbl_ptit2">분류</td>
                          <td width="315" class="tbl_ptit" colspan="2">과정명</td>
                          <td width="139" class="tbl_ptit2">교육기간</td>
                          <td width="103" class="tbl_ptit">맛보기</td>
                        </tr>
 <%
            v_tmp_subj = "";
            l = 0;
            for(i = 0; i < list2.size(); i++) {
                DataBox dbox    = (DataBox)list2.get(i);
                v_upperclass    = dbox.getString("d_upperclass");
                v_isonoff       = dbox.getString("d_isonoff");
                v_course        = dbox.getString("d_course");
                v_coursenm      = dbox.getString("d_coursenm");
                v_cyear         = dbox.getString("d_cyear");
                v_courseseq     = dbox.getString("d_courseseq");
                v_subj          = dbox.getString("d_subj");
                v_subjseq       = dbox.getString("d_subjseq");
                v_subjseqgr     = dbox.getString("d_subjseqgr");
                v_year          = dbox.getString("d_year");
                v_subjnm        = dbox.getString("d_subjnm");
                v_subjseq       = dbox.getString("d_subjseq");
                v_propend       = dbox.getString("d_propend");
                v_edustart      = dbox.getString("d_edustart");
                v_eduend        = dbox.getString("d_eduend");
                v_eduurl        = dbox.getString("d_eduurl");
                v_isnewcourse   = dbox.getString("d_isnewcourse");
                v_subjtarget    = dbox.getString("d_subjtarget");
                v_chkfinal      = dbox.getString("d_chkfinal");
                v_middleclassnm = dbox.getString("d_middleclassnm");
                v_contenttype   = dbox.getString("d_contenttype");
                v_iscourseYn    = dbox.getString("d_isbelongcourse");
                v_subjcnt       = dbox.getInt("d_subjcnt");

                v_propenddate   =  FormatDate.getFormatDate(v_propend,"yyyyMMdd");
                v_edustartdate  =  FormatDate.getFormatDate(v_edustart,"yyyyMMdd");

                v_edustart  = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                v_eduend    = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

                // 맛보기
                v_preurl    =  EduEtc1Bean.make_previewURL(v_subj);
                v_preurl    = dbox.getString("d_preurl");
                v_hasPreviewObj = EduEtc1Bean.hasPreviewObj(v_subj);

                if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }

                if(v_subjtarget.equals(""))     {   v_subjtarget    =   "전사원";   }

                if(StringManager.toInt(v_today) > StringManager.toInt(v_propenddate) && StringManager.toInt(v_today) < StringManager.toInt(v_edustartdate)) v_cancelcheck = "N";
                else                                                    v_cancelcheck = "Y";


%>
              <tr> 
    <% if(v_iscourseYn.equals("Y"))
    { 
        if(l == v_subjcnt) v_tmp_subj = "";
        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
        {
             l = 1;
            %>
            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_middleclassnm %></td>
            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_coursenm%><br><%=v_subjseq%>차</td>
            <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
    <%  
        }else{ l++;
        %>
            <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
<%      }v_tmp_subj = v_course;
    } else { 
            %>

            <td class="tbl_bleft" colspan="2"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
<% } %>
                
                <td class="tbl_grc"><%=v_middleclassnm %></td>                
                <td class="tbl_grc"><%=v_edustart %>~<%=v_eduend %></td>
                <td class="tbl_grc"><% if (v_hasPreviewObj.equals("Y")) { %>
                  <a href="javascript:studyOpen1('/servlet/controller.contents.EduStart?p_subj=<%=v_subj%>&p_year=2000&p_subjseq=0001&p_ispreview=Y', '<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/kocca/button/b_course_pre.gif" border="0"></a>
                  <% } else { %>
                  <% if(!v_preurl.equals("")){ %>
                  <a href="javascript:whenPreShow('<%=v_preurl%>', '<%=v_subj%>')" onfocus=this.blur()><img src="/images/user/kocca/button/b_course_pre.gif" border="0"></a>
                  <%}%>
        <% } %></td>
              </tr>
<%
    }
    if(i == 0){ 
%>
              <tr> 
                <td class="tbl_grc" colspan="5">학습예정중인 과정이 없습니다.</td>
              </tr>
<%  }   %>
              <input  type='hidden' name='p_totCount' value='<%=(i-1)%>'>
           
          </table> 
          
<!--학습완료과정 시작 -->
          <table width="680" border="0" cellspacing="0" cellpadding="0">
            <tr> 
              <td height=22>&nbsp;</td>
            </tr>
            <tr> 
              <td><img src="/images/user/kocca/myclass/st_end_edu.gif"></td>
            </tr>
            <tr> 
              <td height="15"></td>
            </tr>
            <tr> 
              <td class="font_ex">학습종료한 과정의 목록입니다. 학습종료후 1년간 확인이가능하며 복습가능여부에 
                따라 복습이가능합니다.</td>
            </tr>
            <tr> 
              <td height="9"></td>
            </tr>
          </table>
        
           <table width="680" border="2" cellspacing="0" 
     cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
            <tr  class="lcolor"> 
              <td height="3" colspan="7" class="linecolor_my"></td>
            </tr>
            <tr> 
              <td width="60" class="tbl_ptit2">분류</td>
              <td width="154" class="tbl_ptit" colspan="2">과정명</td>
              <td width="130" class="tbl_ptit2">교육기간</td>
              <td width="130" class="tbl_ptit">복습기간</td>
              <td width="48" class="tbl_ptit2">성적보기</td>
              <td width="48" class="tbl_ptit">복습하기</td>
              <!--td width="45" class="tbl_ptit2">수료증</td-->
            </tr>
              <%
                String v_score = "";
                String v_iscareer ="";
                v_tmp_subj = "";
                l = 0;

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
                    v_reviewdays    = dbox.getInt("d_reviewdays");
                    v_reviewtype    = dbox.getString("d_reviewtype");
                    v_score         = dbox.getString("d_score");
                    v_iscareer      = dbox.getString("d_iscareer");
                    v_cpsubj        = dbox.getString("d_cpsubj");
                    v_cpsubjseq     = dbox.getString("d_cpsubjseq");
                    v_company       = dbox.getString("d_company");
                    v_middleclassnm      =  dbox.getString("d_middleclassnm");
                    v_contenttype   =  dbox.getString("d_contenttype");
                    v_kind          = dbox.getString("d_kind");
                    v_gubun         = dbox.getString("d_gubun");
                    v_serno         = dbox.getString("d_serno");
                    v_pkey          = dbox.getString("d_pkey");
                    v_iscourseYn    = dbox.getString("d_isbelongcourse");
                    v_subjcnt       = dbox.getInt("d_subjcnt");
                    //out.println("out="+v_isoutsourcing);

                    v_eduenddate = FormatDate.getFormatDate(v_eduend,"yyyyMMdd");


                    //v_reviewend = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "year", 1), "yyyy/MM/dd");
                    
                    if (v_reviewtype.equals("D")) {
						v_reviewend = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "date", v_reviewdays - 1), "yyyy/MM/dd");
					} else if (v_reviewtype.equals("W")) {
						v_reviewend = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "date", v_reviewdays * 7 - 1), "yyyy/MM/dd");
					} else if (v_reviewtype.equals("M")) {
						v_reviewend = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "month", v_reviewdays), "yyyy/MM/dd");
					} else if (v_reviewtype.equals("Y")) {
						v_reviewend = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "year", v_reviewdays), "yyyy/MM/dd");
					}

					if (v_reviewtype.equals("D")) {
						if (StringManager.toInt(v_today) > StringManager.toInt(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "date", v_reviewdays - 1)) ) {
							v_isablereview = "N";
						}
					} else if (v_reviewtype.equals("W")) {
						if (StringManager.toInt(v_today) > StringManager.toInt(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "date", v_reviewdays * 7 - 1)) ) {
							v_isablereview = "N";
						}
					} else if (v_reviewtype.equals("M")) {
						if (StringManager.toInt(v_today) > StringManager.toInt(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "month", v_reviewdays)) ) {
							v_isablereview = "N";
						}
					} else if (v_reviewtype.equals("Y")) {
						if (StringManager.toInt(v_today) > StringManager.toInt(FormatDate.getDateAdd(FormatDate.getFormatDate(v_eduend,"yyyyMMdd"),"yyyyMMdd", "year", v_reviewdays)) ) {
							v_isablereview = "N";
						}
					}
					
					if (v_reviewend.equals("")) {
						v_isablereview = "N";
					}
					
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

                    } else {
                        if (v_isoutsourcing.equals("Y")) {                 //크레듀
                            v_eduurl = v_eduurl+"?FIELD1="+v_user_id+"&FIELD2="+ v_year+"&FIELD3="+v_cpsubj+"&FIELD4=" +v_cpsubjseq+ "&FIELD99="+ v_company+ "&FIELD100=Y";
                            v_ieduurl = 1;
                        } else {
                              v_eduurl = EduEtc1Bean.make_eduURL(v_subj,"2000","0001");  //외부과정이 아닌경우
                              v_eduurl +=  "&contenttype=" + v_contenttype+"&p_review=Y";
                              v_ieduurl = 0;
                        }
                    }

%>
              <tr> 
<% 
if(!v_subj.equals("")) { 
    if(v_iscourseYn.equals("Y"))
    { 
        if(l == v_subjcnt) v_tmp_subj = "";
        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
        {
             l = 1;
            %>
            <td class="tbl_grc"rowspan="<%=v_subjcnt%>"><%=v_middleclassnm%></td>
            <td class="tbl_grc" rowspan="<%=v_subjcnt%>"><%=v_coursenm%><br><%=v_subjseq%>차</td>
            <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
    <%  
        }else{ l++;
        %>
            <td class="tbl_bleft"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
<%      }v_tmp_subj = v_course;
    } else { %>

            <td class="tbl_grc"><%=v_middleclassnm%></td>
            <td class="tbl_bleft" colspan="2"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')"><%=v_subjnm%></a></td>
<% }
} else { %><td class="tbl_bleft" colspan="2"><%=v_subjnm%></td>
    <% } %>
                <td class="tbl_grc"><%=v_edustart %>~<%=v_eduend %></td>
                <%//if(v_isonoff.equals("ON")&&v_isablereview.equals("Y")){%>
                <td class="tbl_grc"><%=v_eduend%>~<%=v_reviewend%></td>
                <%//}else{%>
                <!--td class="tbl_grc">-</td-->
                <%//}%>
                <td class="tbl_grc">
                  <a href="javascript:whenEdulist('<%=v_subjnm%>','<%=v_ieduurl%>','Y','<%=v_edulist_value%>')"><img src="/images/user/kocca/button/b_result.gif" border="0"></a>
                </td>
                <td class="tbl_grc">
                <%if(v_isablereview.equals("Y")){%> 
                  <a href="javascript:studyOpen('<%=v_eduurl%>','<%=v_ieduurl%>')"><img src="/images/user/kocca/button/b_review.gif" border="0"></a> 
                  <%}%> </td>
                <!--td class="tbl_grc">
<%            if (v_isgraduated.equals("Y")) {    %>
<%              if (v_kind.equals("2")) {    %>
                <a href="javascript:suRoyJeung('<%=v_pkey%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>','2')"><img src="/images/user/kocca/button/b_print.gif" border="0"></a>
<%              } else {                     %>
                <a href="javascript:suRoyJeung('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>','1')"><img src="/images/user/kocca/button/b_print.gif" border="0"></a>
<%              }                            %>
<%            }                            %>
                </td-->
              </tr>
              <%
                        }
                        if(i == 0){
%>
              <tr> 
                <td height="12" class="tbl_grc" colspan="7">학습을 마친과정이 없습니다.</td>
              </tr>
              <%
                        }
%>
                             
             </table>
  
                          
<table width="680" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="20">&nbsp;</td>
  </tr>
</table>
                     
                        
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->