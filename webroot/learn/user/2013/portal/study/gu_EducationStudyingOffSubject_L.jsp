<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList list1 = null;
    list1 = (ArrayList)request.getAttribute("ProposeOffHistoryList");//수강신청
    ArrayList list = null;
    list = (ArrayList)request.getAttribute("StudyHistoryOffList");//교육완료

    String v_process = box.getString("p_process");
    String v_user_id = box.getSession("userid");
    String v_tem_grcode = box.getSession("tem_grcode");
    String v_upperclass = box.getString("v_upperclass");
    String v_tid = "";
    String v_subj = "";
    String v_year = "";
    String v_subjseq = "";
    String v_seq = "";
    String v_subjnm = "";
    String v_edustart = "";
    String v_eduend = "";
    String v_appdate = "";
    String v_classname = "";
    String v_refundabledate= "";
    String v_refundableyn = "";
    String v_refundyn = "";
    String v_canceldate = "";
    String v_paymethod = "";
    String v_chkfirst = "";
    String v_chkfinal = "";
    String v_accept_state = "";
    String v_rank = "";
    String v_cancelableyn = "";
    String v_refunddate = "";
    double v_biyong = 0;
    int v_rowspan = 0;
    int i = 0;
    int l = 0;


    String vv_user_id = box.getSession("userid");
    String vv_process = box.getString("p_process");
    String vv_selectcode = box.getStringDefault("p_selectcode", "ALL");
    String vv_grcode = box.getSession("tem_grcode");

    String vv_upperclass = "";
    String vv_upperclassnm = "";
    String vv_subj = "";
    String vv_year = "";
    String vv_subjnm = "";
    String vv_subjseq = "";
    String vv_edustart = "";
    String vv_eduend = "";
    String vv_kind             = "1";
    String vv_isgraduated = "";
    String vv_isgraduated_txt = "";
    String vv_stustatus = "";
    String vv_stustatusnm = "";
    double vv_score = 0d;
    String vv_isterm = "";
    String vv_appdate = "";

    int vi = 0;

    int vv_dispnum = 0, vv_totalpage = 0, vv_rowcount = 1, vv_upfilecnt = 0, vv_tabseq = 0;

    // 페이징관련
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int vv_pageno = box.getInt("p_pageno");

    box.put("title_str", "현장교육 강의실 - 개인메뉴");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="javascript">
    //검색내용에 맞는 리스트 페이지로 이동
    function selectList() {
        document.form1.action = "/servlet/controller.study.MyClassServlet";
        document.form1.p_process.value = "ProposeOffHistoryPage";
        document.form1.submit();
    }

    //수강신청 결제팝업
    function payApply(p_listgubun, p_subj, p_year, p_subjseq, p_seq){
        window.self.name = "winProposePage";
        farwindow = window.open("", "openWinPayApply", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 780, height = 800, top=0, left=0");
        document.form1.p_listgubun.value = p_listgubun;
        document.form1.p_subj.value = p_subj;
        document.form1.p_year.value = p_year;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.p_seq.value = p_seq;
        document.form1.target = "openWinPayApply"
        document.form1.action = "/inicis/plugin_check_new_off.jsp";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
    }

    //수강취소
    function cancelPropose(p_tid, p_paymethod){

        if(!confirm("수강을 취소하시겠습니까?\n수강신청내용과 결제정보가 취소됩니다.")){
            return;
        }

        document.form1.target = "_self"
        document.form1.p_tid.value = p_tid;
        document.form1.p_paymethod.value = p_paymethod;
        document.form1.p_process.value = 'CancelOffPropose';
        document.form1.action = "/servlet/controller.study.MyClassServlet";
        document.form1.submit();
    }

    //수강신청 취소 팝업
    function cancelApply(p_tid, p_paymethod){
        window.self.name = "winProposePage";
        farwindow = window.open("", "openWinCancelApply", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 550, height = 400, top=0, left=0");
        document.form1.target = "openWinCancelApply"
        document.form1.p_tid.value = p_tid;
        document.form1.p_paymethod.value = p_paymethod;
        document.form1.p_process.value = 'ProposeOffCancelApplyPage';
        document.form1.action = "/servlet/controller.study.MyClassServlet";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
    }

    //수강취소(신청만 하고 승인이나 결재가 없는경우
    function cancelApplyFirst(v_subj,v_year,v_subjseq,v_seq){
        if(!confirm("수강을 취소하시겠습니까?")){
            return;
        }
        document.form1.target = "_self"
        document.form1.p_tid.value = "";
        document.form1.p_paymethod.value = "";
        document.form1.p_listgubun.value = "PROP";
        document.form1.p_subj.value = v_subj;
        document.form1.p_year.value = v_year;
        document.form1.p_subjseq.value = v_subjseq;
        document.form1.p_seq.value = v_seq;
        document.form1.p_process.value = 'ProposeOffCancelFirst';
        document.form1.action = "/servlet/controller.study.MyClassServlet";
        document.form1.submit();
    }

//수강신청정보수정
function updatePropose(subj,year,subjseq, subjnm) {
    if(<%= box.getSession("userid").length()==0 %>) {
        alert("로그인이 필요합니다.");
        return;
    }

    var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
    document.form1.p_process.value = "UpdateSubjectEduProposePage";
    document.form1.target = "proposeWindow";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.action = "/servlet/controller.off.ProposeOffServlet";
    document.form1.submit();
    document.form1.target = "_self";
}

//수료증 출력
function suRoyJeung(subj,year,subjseq,userid){
    window.self.name = "SuryoJeung";
    var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=830,height=800,scrollbars=yes,resizable=NO');

    document.form2.target = "openSuryoJeungPrint";
    document.form2.action = "/servlet/controller.polity.DiplomaAdminServlet";

    document.form2.p_subj.value = subj;
    document.form2.p_year.value = year;
    document.form2.p_scsubjseq.value = subjseq;
    document.form2.p_userid.value = userid;
    document.form2.p_process.value = "DiplomaOffPrint";
    document.form2.submit();
    document.form2.target = window.self.name;
}
//페이지 이동
function goPage(pageNum) {

    document.form2.p_pageno.value = pageNum;
    document.form2.action = "/servlet/controller.study.MyClassServlet";
    document.form2.p_process.value = "EducationStudyingOffSubjectPage";
    document.form2.submit();

}
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 현장 교육 -->
            <ul class="location">
                <li>개인메뉴</li>
                <li>
                    현장교육 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->                        
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" >온라인 강의실</a></li>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage" class="active">현장교육 강의실</a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" >내가작성한 글</a></li>
                        <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                        <!-- 2014-04-23 기존 3D과정이 미래창조부로 이관 완료됨으로 인하여 주석처리
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage" class="active">나의 강의실</a></li>
                        <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=004">과정신청</a></li>
                        <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=004">과정신청</a></li>
                        <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList">일정안내</a></li>
                        //-->
                    </ul>
                </li>
            </ul>

            <!-- 서브메인용 -->
            <!-- 2015-01-12 개편 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage"><strong><u>나의 강의실</u></strong></a></li>
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001">과정신청</a></li>
                    <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList">일정안내</a></li>
                </ul>
            </div>

            //-->
        </div>

        <div id="contents">
            <h2 class="subTit">현장교육 강의실<!--<img src="/images/2013/field_edu/offlineEdu_title.gif" alt="나의 강의실" />//--></h2>

            <div id="subCont" class="myClass">
                <h3 class="subTit_bl">> 다음 과정<!--<img src="/images/2013/online_edu/myclass_title_h3_2.gif" alt="신청중인 과정" />//--></h3>
                <div class="tableWrap2 noclose">
                <form name= "form1" method= "post" action="">
                    <input type='hidden' name='p_process'  >
                    <input type='hidden' name='p_userid'   >
                    <input type='hidden' name='p_subj'     >
                    <input type='hidden' name='p_year'     >
                    <input type='hidden' name='p_subjseq'  >
                    <input type='hidden' name='p_seq'      >
                    <input type='hidden' name='p_subjnm'   >
                    <input type='hidden' name='p_tid'      >
                    <input type='hidden' name='p_paymethod'>
                    <input type='hidden' name='p_listgubun'>
                    <table class="layoutTable3 space3" summary="이표는 나의 강의실 신청중인 과정표로 과정명,교육기간,수강신청일,교육비(원),승인상태,취소하기,정보수정으로 구성되어있습니다.">
                        <caption>신청중인 과정</caption>
                        <colgroup>
                            <col width="284"/>
                            <col width="140"/>
                            <col width="84"/>
                            <col width="80"/>
                            <col width="66"/>
                            <col width="82"/>
                            <col width="*"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">과정명</th>
                            <th scope="col">교육기간</th>
                            <th scope="col">수강신청일</th>
                            <th scope="col">교육비(원)</th>
                            <th scope="col">승인상태</th>
                            <th scope="col">취소하기</th>
                            <th scope="col">정보수정</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                        if(list1 != null) {
                            for(i = 0; i < list1.size(); i++) {
                                DataBox dbox = (DataBox)list1.get(i);

                                v_tid           = dbox.getString("d_tid");
                                v_subj          = dbox.getString("d_subj");
                                v_year          = dbox.getString("d_year");
                                v_subjseq       = dbox.getString("d_subjseq");
                                v_seq           = dbox.getString("d_seq");
                                v_subjnm        = dbox.getString("d_subjnm");
                                v_edustart      = dbox.getString("d_edustart");
                                v_eduend        = dbox.getString("d_eduend");
                                v_appdate       = dbox.getString("d_appdate");
                                v_classname     = dbox.getString("d_classname");
                                v_refundabledate= dbox.getString("d_refundabledate");
                                v_refundableyn  = dbox.getString("d_refundableyn");
                                v_refundyn      = dbox.getString("d_refundyn");
                                v_canceldate    = dbox.getString("d_canceldate");
                                v_paymethod     = dbox.getString("d_paymethod");
                                v_chkfirst      = dbox.getString("d_chkfirst");
                                v_chkfinal      = dbox.getString("d_chkfinal");
                                v_rank          = dbox.getString("d_rank");
                                v_cancelableyn  = dbox.getString("d_cancelableyn");
                                v_refunddate    = dbox.getString("d_refunddate");
                                v_rowspan       = dbox.getInt("d_rowspan");
                                v_biyong        = dbox.getDouble("d_biyong");

                                v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
                                v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
                                v_appdate       = FormatDate.getFormatDate(v_appdate,"yyyy.MM.dd");
                                v_refundabledate= FormatDate.getFormatDate(v_refundabledate,"yyyy.MM.dd");
                                v_canceldate    = FormatDate.getFormatDate(v_canceldate,"yyyy.MM.dd");
                                v_refunddate    = FormatDate.getFormatDate(v_refunddate,"yyyy.MM.dd");


                                if ("U".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
                                    v_accept_state  = "미처리";
                                } else if ("Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
                                    v_accept_state  = "1차승인";
                                } else if ("Y".equals(v_chkfirst) && "N".equals(v_chkfinal)) {
                                    v_accept_state  = "최종불합격";
                                } else if ("Y".equals(v_chkfirst) && "Y".equals(v_chkfinal)) {
                                    v_accept_state  = "최종승인";
                                } else if ("N".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
                                    v_accept_state  = "미승인";
                                }
                        %>
                        <tr>
                            <td class="first"><%= v_subjnm %></td>
                            <td><%= v_edustart %> ~ <%= v_eduend %></td>
                            <td><%= v_appdate %></td>
                            <td><%if(v_biyong == 0){%>무료<%}else{%><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %><%} %></td>
                            <td><%= v_accept_state %></td>
                            <td>
                                <%if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) { %>
                                    <span class="btnPink"><a href="javascript:cancelApplyFirst('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')">취소하기</a></span>
                                <%} %>
                            </td>
                            <td>
                                <%if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) { %>
                                    <span class="btnBlue"><a href="javascript:updatePropose('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_subjnm %>')">정보수정</a></span>
                                <%} %>
                            </td>
                        </tr>
                        <%  }
                        }
                        if(i == 0){%>
                        <tr>
                            <td colspan="7" class="first">
                                수강신청 이력이 없습니다.
                            </td>
                        </tr>
                        <%} %>
                        </tbody>
                    </table>
                </form>
                </div>
                <!-- //tableWrap2 -->

                <h3 class="subTit_bl">> 지난 과정<!--<img src="/images/2013/online_edu/myclass_title_h3_4.gif" alt="종료된 과정" />//--></h3>
                <div class="tableWrap2 noclose">
                <form name= "form2" method= "post">
                    <input type='hidden' name='p_process'>
                    <input type='hidden' name='p_sel'       value="<%=box.getString("p_sel")%>">
                    <input type='hidden' name='p_subj'      value="">
                    <input type='hidden' name='p_subjnm'    value ="">
                    <input type='hidden' name='p_year'      value="">
                    <input type='hidden' name='s_subj'      value="">
                    <input type='hidden' name='p_scsubjseq' value="">
                    <input type='hidden' name='p_subjseq'   value="">
                    <input type='hidden' name='p_userid'    value="">
                    <input type='hidden' name='p_kind'      value="">
                    <input type='hidden' name='p_isonoff'   value ="">
                    <input type="hidden" name="p_pageno"        value="<%= vv_pageno %>">
                    <table class="layoutTable3 space3" summary="이표는 나의 강의실 종료된 과정표로 과정명,교육기간,수강신청일,수료상태,수료증 으로 구성되어있습니다.">
                        <caption>종료된 과정</caption>
                        <colgroup>
                            <col width="284"/>
                            <col width="140"/>
                            <col width="84"/>
                            <col width="80"/>
                            <col width="82"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">과정명</th>
                            <th scope="col">교육기간</th>
                            <th scope="col">수강신청일</th>
                            <th scope="col">수료상태</th>
                            <th scope="col">수료증</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                        for(vi = 0; vi < list.size(); vi++) {
                            DataBox dbox2 = (DataBox)list.get(vi);

                            vv_subj         = dbox2.getString("d_subj");
                            vv_year         = dbox2.getString("d_year");
                            vv_subjseq      = dbox2.getString("d_subjseq");
                            vv_subjnm       = dbox2.getString("d_subjnm");
                            vv_edustart     = dbox2.getString("d_edustart");
                            vv_eduend       = dbox2.getString("d_eduend");
                            vv_isgraduated  = dbox2.getString("d_isgraduated");
                            vv_stustatus    = dbox2.getString("d_stustatus");
                            vv_stustatusnm  = dbox2.getString("d_stustatusnm");
                            vv_upperclassnm = dbox2.getString("d_upperclassnm");
                            vv_score        = dbox2.getDouble("d_score");
                            vv_isterm       = dbox2.getString("d_isterm");
                            vv_appdate      = dbox2.getString("d_appdate");

                            vv_dispnum      = dbox2.getInt("d_dispnum");
                            vv_totalpage    = dbox2.getInt("d_totalpage");

                            vv_edustart     = FormatDate.getFormatDate(vv_edustart,"yyyy.MM.dd");
                            vv_eduend       = FormatDate.getFormatDate(vv_eduend,"yyyy.MM.dd");
                            vv_appdate      = FormatDate.getFormatDate(vv_appdate,"yyyy.MM.dd");

                            if(vv_isgraduated.equals("Y")){vv_isgraduated_txt = "수료";}
                            else{vv_isgraduated_txt = "미수료";}
                        %>
                        <tr>
                            <td class="first"><%= vv_subjnm %></td>
                            <td><%= vv_edustart %> ~ <%= vv_eduend %></td>
                            <td><%=vv_appdate %></td>
                            <td><%=vv_isgraduated_txt %></td>
                            <td>
                                <%if("Y".equals(vv_isgraduated)){%>
                                    <span class="btnPink"><a href="javascript:suRoyJeung('<%=vv_subj%>','<%=vv_year%>','<%=vv_subjseq%>','<%=vv_user_id%>')">출력하기</a></span>
                                <%}else{ %>
                                    -
                                <%} %>
                            </td>
                        </tr>
                        <%} %>
                        <%  if(vi == 0){     %>
                            <tr><td colspan="5" class="first">학습 종료된 과정이 없습니다.</td></tr>
                        <% } %>
                        </tbody>
                    </table>
                </form>
                </div>
                <!-- //tableWrap2 -->

                <div class="paging">
                    <%=PageUtil.re2013_printPageListDiv(vv_totalpage, vv_pageno, 0, 10, vv_rowcount) %>
                </div>
                <!-- //paging -->
            </div>
            <!-- //#subCont -->
        </div>
        <!-- //#contents -->
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>