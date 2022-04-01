<%
//**********************************************************
//  1. 제      목: EDUCATION STUDYING SUBJECT PAGE
//  2. 프로그램명: zu_EducationStudyingSubject_L.jsp
//  3. 개      요: 수강중인 과정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009/11/23
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList list1 = null;
    list1 = (ArrayList)request.getAttribute("EducationStudyingSubjectList");   //수강중인 과정 리스트
    ArrayList list2 = null;
    list2 = (ArrayList)request.getAttribute("EducationProposeSubjectList");   //수강신청 과정 리스트
    ArrayList list3 = null;
    list3 = (ArrayList)request.getAttribute("StudyHistoryList");   //종료 과정 리스트

    ArrayList favorSubjectList = (ArrayList)request.getAttribute("favorSubjectList"); // 찜한 과정 목록
    DataBox dbox = null;

    String userId = box.getSession("userid");
    String grcode = box.getSession("tem_grcode");

    //학습중인과정
    String v_subj = "";
    String v_year = "";
    String v_subjnm = "";
    String v_subjseq = "";
    String v_edustart = "";
    String v_eduend =  "";
    String v_eduurl =  "";
    int v_ieduurl = 0;
    String cpsubj = "";
    String cpsubjseq = "";
    String v_isonoff =  "";
    String v_upperclassnm = "";
    String v_edulist_value= "";
    String isoutsourcing = "";
    String company = "";
    String contenttype  = "";
    String v_tmp_subj = "";
    String v_isalways = "";


    //종료과정
    String subj = "";
    String year = "";
    String subjNm = "";
    String isUse = "";
    String contentType = "";
    String subjSeq = "";
    String eduStart = "";
    String eduTerm = "";
    String eduEnd = "";
    String isGraduated = "";
    String gradStatusTxt = "";
    String eduUrl = "";

    String isAbleReview = "";
    String isPossibleByDate = "";

    int possibleCnt = 0;
    
    double tstep = 0d;

    String upperClassNm = "";
    

    int total = 0;
    int dispNum = 0;
    int totalPage = 0;
    int totalRowCount = 1;

    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int pageNo = box.getInt("p_pageno");

    box.put("title_str", "나의 강의실 - 온라인 강의실 - 개인메뉴");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script type="text/javascript">
    // 학습창 OPEN
    function studyOpen(gubun,url, iurl) {
        var f = "";
        if(gubun == "1") {
            f = document.form1;
        } else if(gubun =="3") {
            f = document.form3;
        }

        f.lessonReurl.value = url;
        f.lessonReiurl.value = iurl;
        var s_url = url+"&p_iurl="+iurl;
        if (url == '') {
            alert("정보가 없습니다");
        } else {
            if (iurl == 0){
                open_window('openApp',s_url,0,0,0,0,false,false,false,true,true);
            }else{
                open_window('openApp',s_url,0,0,1024,768,false,false,false,'yes','yes');
            }
        }
}

    // 학습현황 OPEN
    function whenEdulist(subjNm, iurl, iseduend, url) {
        var ulink = url + "&p_subjnm=" + subjNm + "&p_iurl=" + iurl + "&p_iseduend=" + iseduend;
        var new_Open = window.open(ulink,"eduList",'scrollbars=no,width=350,height=240,scrollbars=yes,resizable=no');
    }

    // 과정상세정보 POPUP
    function whenSubjInfoPopup(gubun,subj,subjNm,isonoff){
        var f = "";

        if(gubun == "1"){
            f = document.form1;
        } else if(gubun =="3") {
            f = document.form3;
        }

        window.self.name = "SubjList";
        window.open("", "openSubjInfo", "scrollbars=no, width=720, height=350, scrollbars=yes, resizable=yes'");
        f.target = "openSubjInfo"

        f.p_subj.value = subj;
        f.p_subjnm.value = subjNm;
        f.p_isonoff.value = isonoff;
        f.p_process.value = 'SubjectPreviewPopup';
        f.action='/servlet/controller.propose.ProposeCourseServlet';
        f.submit();

        f.target = window.self.name;
    }

    /**
     * 과정정보 상세 화면으로 이동
     */
    function fnViewSubjInfo(subj, isUse, possibleCnt, year, subjseq) {

        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "?p_process=previewSubjDetailPage&p_subj=" + subj + "&p_year=" + year + "&p_subjseq=" + subjseq;

        if ( isUse != null && isUse == "N" ) {
            alert("폐지된 과정입니다.");
            return;
        }

        if ( possibleCnt != null && possibleCnt == 0) {
            alert("현재 개설되어 있지 않은 과정입니다.");
            return;
        }

        location.href = url + param;
    }

    //수강취소
    function cancelPropose(tid,p_paymethod){
        if(!confirm("수강을 취소하시겠습니까?")){
            return;
        }
        document.form2.target = "_self"
        document.form2.p_tid.value = tid;
        document.form2.p_paymethod.value = p_paymethod;
        document.form2.p_process.value = 'CancelPropose';
        document.form2.action = "/servlet/controller.study.MyClassServlet";
        document.form2.submit();
    }

    //수료증 출력
    function suRoyJeung(subj,year,subjSeq,userid, kind, subjgrcode){
        window.self.name = "SuryoJeung";
        var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=830,height=800,scrollbars=yes,resizable=NO');

        document.form3.target = "openSuryoJeungPrint";
        document.form3.action = "/servlet/controller.polity.DiplomaAdminServlet";

        document.form3.p_subj.value = subj;
        document.form3.p_year.value = year;
        document.form3.p_scsubjseq.value = subjSeq;
        document.form3.p_userid.value = userid;
        document.form3.p_kind.value = kind;
        document.form3.p_grcode.value = subjgrcode;
        document.form3.p_process.value = "DiplomaPrint";
        document.form3.submit();
        document.form3.target = window.self.name;
    }

    //페이지 이동
    function goPage(pageNum) {

        document.form3.p_pageno.value = pageNum;
        document.form3.action = "/servlet/controller.study.MyClassServlet";
        document.form3.p_process.value = "EducationStudyingSubjectPage";
        document.form3.submit();
    }

    /**
     * 찜한 강의 취소
     */
    function fnCancelFavor(subj, idx) {

        var param = "p_process=manageFavorSubj&classType=01&subj=" + subj + "&jobType=cancel";
        var successMsg = "";

        if ( confirm("찜한 과정 목록에서 삭제하시겠습니까?") ) {
            $.ajax({
                    type : "get"
                ,   url : "/servlet/controller.study.MyClassServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {

                        if ( data.isLogin == true ) {
                            if ( data.resultCnt > 0) {
                                $("#oFavorSubjList" + idx).remove();
                                alert("찜한 과정에서 삭제되었습니다.");

                            } else {
                                alert("오류가 발생하였습니다.\n관리자에게 문의하세요.");
                                return;
                            }
                        } else {
                            alert("로그인이 필요합니다.");
                            return;
                        }
                    }
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1 + " :: " + arg2);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }

            });
        } else {
            return;
        }
    }

 </script>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

        <!-- 온라인 교육 -->
        <div id="mainMenu">
            <ul class="location">
                <li>개인메뉴</li>
                <li>온라인 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" class="active">온라인 강의실</a></li>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육 강의실</a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" >내가작성한 글</a></li>
                        <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                        <!-- 2015-01-09 개편 삭제
                        <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                        <li><a href="#">커뮤니티</a></li>
                        //-->
                    </ul>
                </li>
            </ul>

            <!-- 서브메인용 -->
            <div class="introCategory">
                <ul>
                    <!-- <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><strong><u>나의 강의실</u></strong></a></li> //-->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><strong><u>나의 강의실</u></strong></a></li>
                    <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList" >찜목록</a></li> 
                    <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew" >설문</a></li>
                    <li><a href="/servlet/controller.study.ProposeReviewServlet?p_process=selectReviewList" >과정후기</a></li>
                    <!-- 2015-01-09 개편 변경
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                    //-->
                </ul>
            </div>
            <!-- //서브메인용 -->
            <!-- //온라인 교육 -->
        </div>


    <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
            <h2 class="subTit">나의 강의실<!--<img src="/images/2013/online_edu/myclass_title.gif" alt="나의 강의실" />//--></h2>

            <div id="subCont" class="myClass">
                <!-- 2015-01-12 개편삭제
                <ul class="tabCus">
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/2013/online_edu/myclass_tab_1_on.gif" alt="나의 강의실" /></a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage"><img src="/images/2013/online_edu/myclass_tab_2_off.gif" alt="상담내역" /></a></li>
                    <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew"><img src="/images/2013/online_edu/myclass_tab_3_off.gif" alt="설문" /></a></li>
                    <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList"><img src="/images/2013/online_edu/myclass_tab_4_off.gif" alt="찜한강좌" /></a></li>
                </ul>
                //-->

                


                <h3 class="subTit_bl">&gt; 학습중인  과정<!--<img src="/images/2013/online_edu/myclass_title_h3_4.gif" alt="종료된 과정" />//--></h3>
                <div class="tableWrap2 noclose">
                <form name="form3" method= "post">
                    <input type="hidden" name="p_process">
                    <input type="hidden" name="p_sel"       value="<%=box.getString("p_sel")%>">
                    <input type="hidden" name="p_subj"      value="">
                    <input type="hidden" name="p_subjnm"    value ="">
                    <input type="hidden" name="p_grcode"    value ="">
                    <input type="hidden" name="s_grcode"    value="<%= grcode %>">
                    <input type="hidden" name="p_year"      value="">
                    <input type="hidden" name="s_subj"      value="">
                    <input type="hidden" name="p_scsubjseq" value="">
                    <input type="hidden" name="p_subjseq"   value="">
                    <input type="hidden" name="p_userid"    value="">
                    <input type="hidden" name="p_kind"      value="">
                    <input type="hidden" name="p_isonoff"   value ="">
                    <input type="hidden" name="p_pageno"        value="<%= pageNo %>">
                    <!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
                    <input type="hidden" name="lessonRepopup" value="1">
                    <input type="hidden" name="lessonReurl" value="">
                    <input type="hidden" name="lessonReiurl" value="">

                    <table class="layoutTable3 space3" summary="이표는 나의 강의실 학습중인 과정표로 카테고리,과정명,교육기간,학습현황,학습하기 내용으로 구성되어 있습니다 ">
                        <caption>학습중인 과정</caption>
                        <colgroup>
                            <col width="76"/>
                            <col width="344"/>
                            <col width="150"/>
                            <col width="85"/>
                            <col width="65"/>
                            <col width="80"/>
                            <col width="*"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">카테고리</th>
                            <th scope="col">과정명</th>
                            <th scope="col">학습기간</th>
                            <th scope="col">강의실</th>
                            <th scope="col">진도율</th>
                            <th scope="col">수료증</th>
                        </tr>
                        </thead>
                        <tbody>
<%
                        if(list3.size() != 0){
                            dbox = null;
                            for(int i = 0; i < list3.size(); i++) {
                                dbox = (DataBox)list3.get(i);

                                subj = dbox.getString("d_subj");
                                subjNm = dbox.getString("d_subjnm");
                                isUse = dbox.getString("d_isuse");
                                contentType = dbox.getString("d_contenttype");
                                eduUrl = dbox.getString("d_eduurl");
                                year = dbox.getString("d_year");
                                subjSeq = dbox.getString("d_subjseq");
                                v_isalways = dbox.getString("d_isalways");
                                
                                if(v_isalways.equals("Y")){
	                                eduStart = dbox.getString("d_edustart");
	                                eduEnd = dbox.getString("d_eduend");
                                }else{
	                                eduStart = dbox.getString("d_courseedustart");
	                                eduEnd = dbox.getString("d_courseeduend");
                                }
                                
                                isGraduated = dbox.getString("d_isgraduated");
                                upperClassNm = dbox.getString("d_upperclassnm");
                                tstep = dbox.getDouble("d_tstep");
                                isAbleReview = dbox.getString("d_isablereview");
                                isPossibleByDate = dbox.getString("d_ispossible_by_date");

                                cpsubj = dbox.getString("d_cpsubj");
                                cpsubjseq = dbox.getString("d_cpsubjseq");
                                company = dbox.getString("d_company");
                                contenttype = dbox.getString("d_contenttype");
                                
                                isoutsourcing = dbox.getString("d_isoutsourcing");
                                possibleCnt = dbox.getInt("d_possible_cnt");
                                dispNum = dbox.getInt("d_dispnum");
                                totalPage = dbox.getInt("d_totalpage");

                                eduStart = FormatDate.getFormatDate(eduStart,"yyyy.MM.dd");
                                eduEnd = FormatDate.getFormatDate(eduEnd,"yyyy.MM.dd");

                                
                                if(eduEnd.equals(""))
                                	eduTerm = eduStart;
                                else
                                	eduTerm = eduStart + " ~ " + eduEnd; 
                                
                                
                             	// 학습url=외주인 경우..
                                if(eduUrl.equals("")) { v_ieduurl = 0; }else{ v_ieduurl = 1; }

                                if (isoutsourcing.equals("Y")) {                 //위탁과정일때

                                	eduUrl = "/servlet/controller.contents.EduStart";
                                	eduUrl += "?FIELD1=" + userId + "&FIELD2=" + year + "&FIELD3=" + cpsubj + "&FIELD4=" + cpsubjseq + "&FIELD99=" + company+"&FIELD100=N"+ "&contenttype=" + contenttype+ "&p_subj=" + subj +"&p_year="+ year +"&p_subjseq="+subjSeq;
                                } else {//외부과정이 아닌경우
                                     //직접 로직 흡수.. eduUrl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);
                                    if (eduUrl.equals("")){
                                    	eduUrl = "/servlet/controller.contents.EduStart?p_subj="+subj;
                                    }

                                    if (!year.equals(""))
                                    	eduUrl = eduUrl+"&p_year="+year+"&p_subjseq=";
                                    else
                                    	eduUrl = eduUrl+"&p_year=2000&p_subjseq=";

                                    if(!subjSeq.equals(""))
                                    	eduUrl = eduUrl+subjSeq;
                                    else eduUrl = eduUrl+"0001";
                                    eduUrl +=  "&contenttype=" + contenttype;
                                }
%>
                        <tr>
                            <td class="first"><%= upperClassNm %></td>
                            <td><a href="javascript:fnViewSubjInfo('<%= subj %>', '<%= isUse %>', <%= possibleCnt %>, '<%= year %>', '<%= subjSeq %>');"><%= subjNm %></a></td>
                            <td><%= eduTerm %></td>
                            <td>
								<span class="btnPink"><a href="javascript:studyOpen('3','<%=eduUrl%>','<%=v_ieduurl%>');">강의실</a></td>
                            </td>
                            <td><%= tstep %>%</td>
                            <td>
<%
                                if(!subj.equals("") && isGraduated.equals("Y")) {
%>
                                <span class="btnPink"><a href="javascript:suRoyJeung('<%= subj %>','<%= year %>','<%= subjSeq %>','<%= userId %>','1','<%= grcode %>');">출력</a></span>
<%
                                } else {
%>
                                    -
<%
                                }
%>
                            </td>
                        </tr>
<%
                            }
                        } else {
%>
                        <tr>
                            <td class="first" colspan="7">종료된 과정이 없습니다.</td>
                        </tr>
<%
                        }
%>
                        </tbody>
                    </table>
                    </form>
                </div>
                <!-- //tableWrap2 -->

                <div class="paging">
                    <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 0, 30, totalRowCount) %>
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


