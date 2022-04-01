<%
//**********************************************************
//  1. 제      목: 나의 강의실 > 찜한강좌(열린강좌) 목록 화면
//  2. 프로그램명: gu_FavorOpenClass_L.jsp
//  3. 개      요: 수강중인 과정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2014-10-21
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet"  scope="page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    ArrayList favorSubjectList = (ArrayList)request.getAttribute("favorSubjectList"); // 찜한 과정 목록

    ArrayList favorOpenClassList = (ArrayList)request.getAttribute("favorOpenClassList"); // 찜한 과정 목록

    // 페이징관련
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int vv_pageno = box.getInt("p_pageno");

    String grcode = box.getSession("tem_grcode");

    box.put("title_str", "찜목록 - 온라인교육");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script type="text/javascript">

    /**
     * 찜한 온라인교육 취소
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
    /**
     * 찜한 강좌 취소
     */
    function fnCancelFavorOpenclass(seq, idx) {

        var param = "p_process=manageFavorSubj&classType=02&subj=" + seq + "&jobType=cancel";
        var successMsg = "";

        if ( confirm("찜한 강좌 목록에서 삭제하시겠습니까?") ) {
            $.ajax({
                    type : "get"
                ,   url : "/servlet/controller.study.MyClassServlet"
                ,   dataType : "json"
                ,   data : param
                ,   success : function (data) {

                        if ( data.isLogin == true ) {
                            if ( data.resultCnt > 0) {
                                $("#oFavorOpenClassList" + idx).remove();
                                alert("찜한 강좌에서 삭제되었습니다.");

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
    /**
     * 과정정보 상세 화면으로 이동
     */
    function fnViewSubjInfo(subj) {

        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "?p_process=previewSubjDetailPage&p_subj=" + subj;

        location.href = url + param;
    }

    /**
     * 열린강좌 상세 화면으로 이동
     */
    function fnViewOpenClass(seq, lectureCls) {
        var url = "/servlet/controller.infomation.GoldClassHomePageServlet";
        var param ="?p_process=selectView&p_seq=" + seq + "&p_lecture_cls=" + lectureCls;

        location.href = url + param;
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
                    </ul>
                </li>
                <!-- 2015-01-12 개편 변경
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
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList" ><strong><u>찜목록</u></strong></a></li>
                    <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew" >설문</a></li>
                    <li><a href="/servlet/controller.study.ProposeReviewServlet?p_process=selectReviewList" >과정후기</a></li>

                    <!-- 2015-01-12 개편수정
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><strong><u>나의 강의실</u></strong></a></li>
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
            <h2 class="subTit">찜목록<!--<img src="/images/2013/online_edu/favor_title.gif" alt="찜목록" />//--></h2>

            <div id="subCont" class="myClass">

                <!-- 2015-01-12 개편삭제
                <ul class="tabCus">
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/2013/online_edu/myclass_tab_1_off.gif" alt="나의 강의실" /></a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage"><img src="/images/2013/online_edu/myclass_tab_2_off.gif" alt="상담내역" /></a></li>
                    <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew"><img src="/images/2013/online_edu/myclass_tab_3_off.gif" alt="설문" /></a></li>
                    <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList"><img src="/images/2013/online_edu/myclass_tab_4_on.gif" alt="찜한강좌" /></a></li>
                </ul>

                //-->
                <div class="noticBox">
                    열린강좌에서 내가 찜한강좌 목록을 확인 할 수 있습니다.<br/>
                    찜한강좌는 최대 30개까지 보관되며 30개 초과시 오래된 순서대로 자동삭제됩니다.
                </div>


                <!-- 2015-01-12 개편 수정 찜한강좌와 통합  -->

                <h3 class="subTit_bl">> 온라인 교육</h3>
                <div class="tableWrap2 noclose">
                    <table class="layoutTable3 space3" summary="이표는 나의 강의실 찜한 과정표로 카테고리, 과정명, 교육기간, 신청기간, 취소하기로 구성되어있습니다.">
                        <caption>찜한 과정</caption>
                        <colgroup>
                            <col width="80"/>
                            <col width="284"/>
                            <col width="140"/>
                            <col width="106"/>
                            <col width="106"/>
                            <col width="*"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">카테고리</th>
                            <th scope="col">과정명</th>
                            <th scope="col">교육기간</th>
                            <th scope="col">신청기간</th>
                            <th scope="col">취소하기</th>
                        </tr>
                        </thead>
                        <tbody>
<%
                        DataBox favorSuject = null;
                        if( favorSubjectList.size() != 0 ) {
                            for ( int i = 0; i < favorSubjectList.size(); i++) {
                                favorSuject = (DataBox)favorSubjectList.get(i);
%>
                        <tr id="oFavorSubjList<%= i %>">
                            <td class="first"><%= favorSuject.getString("d_classname") %></td>
                            <td><a href="javascript:fnViewSubjInfo('<%= favorSuject.getString("d_subj") %>');"><%= favorSuject.getString("d_subjnm") %></a></td>
                            <td><%= favorSuject.getString("d_edustart") %>~<%= favorSuject.getString("d_eduend") %></td>
                            <td><%= favorSuject.getString("d_propstart") %>~<%= favorSuject.getString("d_propend") %></td>
                            <td><span class="btnPink"><a href="javascript:fnCancelFavor('<%= favorSuject.getString("d_subj") %>', <%= i %>);">찜취소</a></span></td>
                        </tr>
<%
                            }
                        } else {
%>
                        <tr>
                            <td class="first" colspan="5">찜한과정이 없습니다.</td>
                        </tr>
<%
                        }
%>
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap2 -->

                <h3 class="subTit_bl">> 열린강좌</h3>
                <div class="tableWrap2 noclose">
                    <table class="layoutTable3 space3" summary="이표는 나의 강의실 열린 강좌의 찜한 목록 표로 카테고리명, 강좌명, 취소하기 버튼을 포함하고 있습니다.">
                        <caption>신청중인 과정</caption>
                        <colgroup>
                            <col width="70"/>
                            <col width="70"/>
                            <col width="284"/>
                            <col width="80"/>
                            <col width="*"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col">분류별</th>
                            <th scope="col">테마별</th>
                            <th scope="col">과정명</th>
                            <th scope="col">취소하기</th>
                        </tr>
                        </thead>
                        <tbody>
<%
                        DataBox favorOpenclass = null;
                        if( favorOpenClassList.size() != 0 ) {
                            for ( int i = 0; i < favorOpenClassList.size(); i++) {
                                favorOpenclass = (DataBox)favorOpenClassList.get(i);
%>

                        <tr id="oFavorOpenClassList<%= i %>">
                            <td class="first"><%= favorOpenclass.getString("d_area_nm") %></td>
                            <td><%= favorOpenclass.getString("d_lecture_cls_nm") %></td>
                            <td style="text-align:left; padding:0px 8px 0px 8px;">
                                <a href="javascript:fnViewOpenClass(<%= favorOpenclass.getString("d_seq") %>, '<%= favorOpenclass.getString("d_lecture_cls") %>');"><%= favorOpenclass.getString("d_lecnm") %></a>
                            </td>

                            <td><span class="btnPink"><a href="javascript:fnCancelFavorOpenclass(<%= favorOpenclass.getString("d_seq") %>,'<%= i %>');">찜취소</a></span></td>
                        </tr>
<%
                            }
                        } else {
%>
                        <tr>
                            <td class="first" colspan="5">찜한 강좌가 없습니다.</td>
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
                    <%-- PageUtil.re2013_printPageListDiv(vv_totalpage, vv_pageno, 0, 10, vv_rowcount) --%>
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