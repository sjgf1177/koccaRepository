<%
//**********************************************************
//  1. 제      목: 나의상담내역 - 과정 QnA 상세보기
//  2. 프로그램명 : gu_MyQnaCounselSangdam_R.jsp
//  3. 개      요: 나의상담내역
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    box.put("topmenu","2");
    box.put("submenu","5");

    String  v_process   = box.getString("p_process");
    String  v_categorycd  = box.getString("p_categorycd");
    int     v_seq         = box.getInt("p_seq");
    String  v_subj        = box.getString("p_subj");
    String  v_year        = box.getString("p_year");
    String  v_subjseq     = box.getString("p_subjseq");
    String  v_type        = box.getString("p_type");
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_select      = box.getString("p_select");
    String  v_repkind     = "";
    int     v_repseq      = 0;

    int     v_pageno      = box.getInt("p_pageno");
    int     v_pagesize    = box.getInt("p_pagesize");

    DataBox dbox        = (DataBox)request.getAttribute("selectMyQnaCounselQna");
    String v_inuserid   = dbox.getString("d_inuserid");
    String v_lesson     = dbox.getString("d_lesson");
    String v_kind       = dbox.getString("d_kind");
    String v_name       = dbox.getString("d_name");
    String v_indate     = dbox.getString("d_indate");
    String v_title      = dbox.getString("d_title");
    String v_contents   = dbox.getString("d_contents");
    String v_grcodenm   = dbox.getString("d_grcodenm");
    String v_grcode     = dbox.getString("d_grcode");
    String v_okyn1      = dbox.getString("d_okyn1");
    String v_okuserid1  = dbox.getString("d_okuserid1");
    String v_okdate1    = dbox.getString("d_okdate1");
    String v_okyn2      = dbox.getString("d_okyn2");
    String v_okuserid2  = dbox.getString("d_okuserid2");
    String v_okdate2    = dbox.getString("d_okdate2");
    String v_scsubjnm   = dbox.getString("d_scsubjnm");
    String v_subjseqgr  = dbox.getString("d_subjseqgr");
    Vector v_realfileVector = (Vector)dbox.getObject("d_realfile");
    Vector v_savefileVector = (Vector)dbox.getObject("d_savefile");
    String v_realmotion  = dbox.getString("d_realmotion");
    String v_savemotion  = dbox.getString("d_savemotion");
    String v_status_view = "";
    int    v_filecount   = v_realfileVector.size();
    int    v_cnt         = dbox.getInt("d_cnt");

    //vc_status  1 : 미처리, 2 : 처리중, 3 : 완료
    if (v_okyn1.equals("1")) {
        v_status_view = "열람전";
    } else if (v_okyn1.equals("2")) {
        v_status_view = "답변준비중";
    } else if (v_okyn1.equals("3")) {
        v_status_view = "답변완료";
    } else {
        v_status_view = "";
    }

    MyQnaBean myqnabean = new MyQnaBean();

    //Relative Start
    String pp_searchStart   = box.getString("p_searchStart");
    String pp_searchEnd     = box.getString("p_searchEnd");
    String ss_gyear         = box.getString("s_gyear");
    String ss_grseq         = box.getString("s_grseq");
    String ss_upperclass    = box.getString("s_upperclass");
    String ss_middleclass   = box.getString("s_middleclass");
    String ss_lowerclass    = box.getString("s_lowerclass");
    String ss_subjcourse    = box.getString("s_subjcourse");
    String ss_subjseq       = box.getString("s_subjseq");
    String pp_order         = box.getString("p_order");
    String pp_orderType     = box.getString("p_orderType");
    //Relative End

    box.put("title_str", "상담 내역 - 개인메뉴");
    //box.put("title_str", "상담 내역-나의 강의실, 온라인교육");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
    $(function() {
        $(document.body).css("font-family", "돋움,Dotum,AppleGothic,sans-serif").css("font-size", "12px");
    });

    // 목록으로
    function listPage() {
        document.form1.target = "_self";
        document.form1.p_process.value='MyQnaCounselListPage';
        document.form1.action='/servlet/controller.study.MyQnaServlet';
        document.form1.submit();
    }

//-->
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

    <!-- Form 영역 시작 -->
    <form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet">
        <input type="hidden" name="p_process"     value="<%= v_process %>">
        <input type="hidden" name="p_pageno"      value="<%= v_pageno %>">
        <input type="hidden" name="p_pagesize"    value="<%= v_pagesize %>">
        <input type="hidden" name="p_type"        value="<%= v_type %>">
        <input type="hidden" name="p_subj"        value="<%= v_subj %>">
        <input type="hidden" name="p_year"        value="<%= v_year %>">
        <input type="hidden" name="p_subjseq"     value="<%= v_subjseq %>">
        <input type="hidden" name="p_select"      value="<%= v_select %>">
        <input type="hidden" name="p_searchtext"  value="<%= v_searchtext %>">

    <div id="mainMenu">
        <ul class="location">
            <li>개인메뉴</li>
            <li>상담내역 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" >온라인 강의실</a></li>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육 강의실</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" >내가작성한 글</a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage" class="active" >상담내역</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                </ul>
            </li>

            <!-- 2015-01-20 개편 변경 
            <li>홈</li>
            <li>온라인 교육</li>
            <li>
                나의 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" class="active">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                    <li><a href="#">커뮤니티</a></li>
                </ul>
            </li>
            //-->
        </ul>

        <!-- 서브메인용 -->
        <!-- 2015-01-20 개편삭제 
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><strong><u>나의 강의실</u></strong></a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
            </ul>
        </div>
        //-->
        <!-- //서브메인용 -->
        <!-- //온라인 교육 -->
    </div>


   <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">상담내역<!-- <img src="/images/2013/online_edu/myclass_title.gif" alt="나의 강의실" /> //--></h2>

        <div id="subCont" class="myClass2">
            <!-- 2015-01-20 개편 삭제
            <ul class="tabCus">
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/2013/online_edu/myclass_tab_1_off.gif" alt="나의 강의실" /></a></li>
                <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage"><img src="/images/2013/online_edu/myclass_tab_2_on.gif" alt="상담내역" /></a></li>
                <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew"><img src="/images/2013/online_edu/myclass_tab_3_off.gif" alt="설문" /></a></li>
            </ul>
            //-->
            <h3 class="blind">상담내역 현재위치</h3>

            <div class="article">
                    <div class="info">
                        <p class="title"><%=v_scsubjnm %></p>
                        <p class="date"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></p>
                    </div>

                    <div class="inner">
                        <%=v_contents%>
                    </div>
                </div>
                <!-- //article -->

                <p class="answer"><a href="#">답변</a></p>

                <div class="article">
                <%
                ArrayList list = (ArrayList)request.getAttribute("selectMyQnaCounselQnaListA");
                ArrayList list1 = new ArrayList();
                int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
                //int v_cnt = 0;
                int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1;
                if(list.size() != 0) {
                    for(int i = 0; i < list.size(); i++) {
                        DataBox dboxA = (DataBox)list.get(i);
                        v_dispnum   = dboxA.getInt("d_dispnum");
                        v_inuserid  = dboxA.getString("d_inuserid");
                        v_name      = dboxA.getString("d_name");
                        v_indate    = dboxA.getString("d_indate");
                        v_title     = dboxA.getString("d_title");
                        v_seq       = dboxA.getInt("d_seq");
                        v_totalpage = dboxA.getInt("d_totalpage");
                        v_rowcount  = dboxA.getInt("d_rowcount");
                        v_contents  = dboxA.getString("d_contents");
                        v_scsubjnm  = dboxA.getString("d_scsubjnm");
                        v_subjseqgr = dboxA.getString("d_subjseqgr");
                        v_repseq    = dboxA.getInt("d_seq");
                        v_repkind   = dboxA.getString("d_kind");
                %>
                    <div class="info3">
                        <p class="name"><%=v_name %></p>
                        <p class="date"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd HH:mm") %></p>
                    </div>
                    <div class="inner2">
                        <%=StringManager.removeHTMLHead(v_contents) %>
                    </div>
                <% }} %>
                </div>
                <!-- //article -->

                <!--table class="bbsNavi" style="display: none;">
                    <colgroup>
                        <col width="70">
                        <col>
                        <col width="70">
                        <col width="70">
                    </colgroup>
                    <tr>
                        <td class="pre">이전글</td>
                        <td><img src="/images/2013/common/ico_pds.gif" alt="자료" /> <a href="my_class_2.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                        <td>작성자</td>
                        <td>2013.06.18</td>
                    </tr>
                    <tr>
                        <td class="next">다음글</td>
                        <td><img src="/images/2013/common/ico_pds.gif" alt="자료" /> <a href="my_class_2.html">안산교육형, ‘재미있는 수업이야기’ 초등교사들 수업역량 강화... ‘호응’</a></td>
                        <td>홍길동</td>
                        <td>2013.06.18</td>
                    </tr>
                </table-->

                <div class="btnArea right">
                    <a href="javascript:history.go(-1);"><img src="/images/2013/btn/btn_list.gif" alt="목록" /></a>
                </div>
            </div>
            <!-- //#subCont -->
        </div>
        <!-- //#contents -->
        <!-- 컨텐츠 영역 끝 -->


    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>