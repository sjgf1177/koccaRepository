<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.off.OffClassifySubjectBean" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "일정안내 - 현장교육");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
function whenSubjInfo(subj, year, subjseq, seq, area){
    location.href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectPreviewPage&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq+"&p_seq="+seq+"&p_area="+area;
}
</script>
<!-- container-new 시작 //-->
<div id="container-new">

    <div id="mainMenu">
        <!-- 현장 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>현장교육</li>
            <li>
                일정안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001" >교육신청</a></li>
                    <!-- 2015-02-23 삭제
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=002" >차세대 인재 양성교육</a></li>
                    //-->
                    <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList" class="active">일정안내</a></li>
<!-- 2015-01-07 개편삭제
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">나의 강의실</a></li>
-->
                    <!-- 2014-04-23 기존 3D과정이 미래창조부로 이관 완료됨으로 인하여 주석처리
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=004" class="active">과정신청</a></li>
                    //-->
                </ul>
            </li>
        </ul>
        <!-- 서브메인용 -->
<!-- 2015-01-07 개편삭제
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001">과정신청</a></li>
                <!-- 2014-04-23 기존 3D과정이 미래창조부로 이관 완료됨으로 인하여 주석처리
                <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=004">과정신청</a></li>
                //-->
<!--2015-01-07 개편삭제
                <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList"><strong><u>일정안내</u></strong></a></li>
            </ul>
        </div>
//-->
    </div>


    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        
        <div id="subCont">

            <div class="noticBox">
                <strong>※ 해당과정의 세부내용은 4월 중으로 공지예정입니다.</strong><br />
                <strong>■ 교육문의</strong> : 한국콘텐츠진흥원 창의인재양성팀 (02-3219-6534, thinkju@kocca.kr) <br />
                
            </div>


        </div>
        <!-- //#subCont -->
    </div>
    <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>
