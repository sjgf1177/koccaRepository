<%
//**********************************************************
//  1. 제      목: 나의공부방 > 나의상담내역
//  2. 프로그램명 : gu_MyQnaCounsel_L.jsp
//  3. 개      요: 나의 상담내역
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if (box == null) box = RequestManager.getBox(request);

    String  ss_userid     = box.getSession("userid");
    int     v_no = 0;
    String  v_sdate       = "";
    String  v_types       = "";
    String  v_type        = "";
    String  v_typenm      = "";
    String  v_title       = "";
    String  v_status      = "";
    String  v_status_view = "";
    String  v_status_class = "";
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_select      = box.getString("p_select");
    String  p_type        = box.getString("p_type");
    String  v_edustart    = "";
    String  v_eduend      = "";
    String  v_subj          = "";
    String  v_subjseq       = "";
    String  v_year          = "";
    String  v_userid        = "";
    String  sScriptString   = "";


    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;

    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");

    ArrayList blist2 = (ArrayList)request.getAttribute("MyQnaCounselListPage");  // 학습관련

    box.put("title_str", "상담 내역 - 개인메뉴");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
    $(function() {
        $(document.body).css("font-family", "돋움,Dotum,AppleGothic,sans-serif").css("font-size", "12px");
    });

    function press_enter(e) {
        if (e.keyCode =='13'){  selectList();  }
    }

    //검색내용에 맞는 리스트 페이지로 이동
    function selectList() {
        //if (form1.p_searchtext.value == ""){
        //  alert("검색 할 내용을 입력해 주세요");
        //}else{
            document.form1.action = "/servlet/controller.study.MyQnaServlet";
            document.form1.p_process.value = "MyQnaCounselListPage";
            document.form1.p_pageno.value = "1";
            document.form1.submit();
        //}
    }

    //상세화면 페이지로 이동
    function select(tabseq, seq,  userid, types) {
        document.form1.action = "/servlet/controller.study.MyQnaServlet";
        document.form1.p_process.value   = "MyQnaSiteViewPage";
        document.form1.p_tabseq.value    = tabseq;
        document.form1.p_seq.value       = seq;
        document.form1.p_userid.value    = userid;
        document.form1.p_types.value     = types;
        //document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.submit();
    }


    //페이지 이동
    function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.study.MyQnaServlet";
        document.form1.p_process.value = "MyQnaCounselListPage";
        document.form1.submit();

    }


    //Q&A상세화면 페이지로 이동
    function viewHomeqna(seq, upfilecnt, userid, types, type) {
        document.form1.action               = "/servlet/controller.study.MyQnaServlet";
        document.form1.p_process.value      = "MyQnaCounselHomeQnaViewPage";
        document.form1.p_seq.value          = seq;
        document.form1.p_userid.value       = userid;
        document.form1.p_types.value        = types;
        document.form1.pp_type.value        = type;
        document.form1.p_upfilecnt.value    = upfilecnt;
        document.form1.submit();
    }

    //과정질문 상세화면 페이지로 이동
    function viewQna(seq,subj,year,subjseq) {
        document.form1.action = "/servlet/controller.study.MyQnaServlet";
        document.form1.p_process.value = "MyQnaCounselQnaViewPage";
        document.form1.p_seq.value     = seq;
        document.form1.p_subj.value    = subj;
        document.form1.p_year.value    = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.submit();
    }

//-->
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

    <!-- Form 영역 시작 -->
    <form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet" onsubmit="javascript:selectList();">
        <input type="hidden" name="p_process"     value="">
        <input type="hidden" name="p_pageno"      value="<%= v_pageno %>">
        <input type="hidden" name="p_seq"         value="">
        <input type="hidden" name="p_userid"      value="">
        <input type="hidden" name="p_upfilecnt"   value="">
        <input type="hidden" name="p_types"       value="">
        <input type="hidden" name="pp_type"       value="">
        <input type="hidden" name="p_tabseq"      value="">
        <input type="hidden" name="p_subj">
        <input type="hidden" name="p_year">
        <input type="hidden" name="p_subjseq">

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

            <!-- 2015-01-09 개편변경
            <li>나의 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
            //-->
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
            <!-- 2015-01-09 개편변경
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" class="active">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                    <li><a href="#">커뮤니티</a></li>
                </ul>
            </li>
            //-->
            </li>
        </ul>

        <!-- 서브메인용 -->
        <!-- 2015-01-09 개편 삭제
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
        <h2 class="subTit">상담내역<!--<img src="../images/2013/online_edu/counsel_title.gif" alt="상담내역" />//--></h2>

        <div id="subCont" class="myClass2">

            <!-- 2015-01-09 개편삭제
            <ul class="tabCus">
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/2013/online_edu/myclass_tab_1_off.gif" alt="나의 강의실" /></a></li>
                <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage"><img src="/images/2013/online_edu/myclass_tab_2_on.gif" alt="상담내역" /></a></li>
                <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew"><img src="/images/2013/online_edu/myclass_tab_3_off.gif" alt="설문" /></a></li>
                <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList"><img src="/images/2013/online_edu/myclass_tab_4_off.gif" alt="찜한강좌" /></a></li>
            </ul>
            //-->
            <h3 class="blind">상담내역 현재위치</h3>

            <div class="board2">
                <table summary="글번호, 분류, 제목, 과정, 작성자, 등록일 조회로 구성되어있습니다.">
                    <colgroup>
                        <col width="5%"/>
                        <col width="12%"/>
                        <col width="*"/>
                        <col width="10%"/>
                        <col width="12%"/>
                    </colgroup>
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">
                            <select title="분류" name="p_type" onChange="selectList()">
                                <option value="ALL" <%=p_type.equals("ALL") ? "selected" : "" %>>전체</option>
                                <option value="MM" <%=p_type.equals("MM") ? "selected" : "" %>>1vs1상담</option>
                                <option value="SUBJ" <%=p_type.equals("SUBJ") ? "selected" : "" %>>과정질문방</option>
                                <option value="PQ" <%=p_type.equals("PQ") ? "selected" : "" %>>사이트 이용 문의</option>
                            </select>
                        </th>
                        <th scope="col">제목</th>
                        <th scope="col">작성일</th>
                        <th scope="col">비고</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                    if(blist2.size() != 0){
                        for (int i=0; i<blist2.size(); i++) {
                            DataBox dbox = (DataBox)blist2.get(i);
                            v_dispnum   = dbox.getInt("d_dispnum");
                            v_no        = dbox.getInt("d_no");
                            v_sdate     = dbox.getString("d_sdate");
                            v_types     = dbox.getString("d_types");
                            v_totalpage = dbox.getInt("d_totalpage");
                            v_title     = dbox.getString("d_title");
                            v_type      = dbox.getString("d_type");
                            v_status    = dbox.getString("d_status");
                            v_userid    = dbox.getString("d_userid");
                            v_subj      = dbox.getString("d_subj");
                            v_year      = dbox.getString("d_year");
                            v_subjseq   = dbox.getString("d_subjseq");

                            int i_upfilecnt = dbox.getInt("d_upfilecnt");

                            if (!v_searchtext.equals("")&&v_select.equals("title")) {
                                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                            }

                            if (v_type.equals("PQ")) {
                                v_typenm = "홈페이지";
                            } else if (v_type.equals("BU") || v_type.equals("CU") || v_type.equals("OO") || v_type.equals("MM")) {
                                v_typenm = "1vs1상담";
                            } else if (v_type.equals("OFF")) {
                                v_typenm = "유선상담";
                            } else if (v_type.equals("SUBJ")) {
                                v_typenm = "과정질문방";
                            } else {
                                v_typenm = "";
                            }

                            //vc_status  1 : 미처리, 2 : 처리중, 3 : 완료
                            if (v_status.equals("1")) {
                                v_status_view   = "미처리";
                                v_status_class  = "pre";
                            } else if (v_status.equals("2")) {
                                v_status_view   = "답변중";
                                v_status_class  = "ing";
                            } else if (v_status.equals("3")) {
                                v_status_view   = "<img src=\"/images/2013/btn/btn_answer_done2.gif\" alt=\"답변완료\" />";
                                v_status_class  = "finish";
                            } else {
                                v_status_view   = "미처리";
                                v_status_class  = "pre";
                            }

                            if ("PQ".equals(v_type) || "BU".equals(v_type) || "CU".equals(v_type) || "OO".equals(v_type) || "MM".equals(v_type)) {
                                sScriptString   = "viewHomeqna('" + v_no + "', '" + v_upfilecnt + "', '" + v_userid + "', '" + v_types + "', '" + v_type + "');";
                            } else if ("SUBJ".equals(v_type)) {
                                sScriptString   = "viewQna('" + v_no + "', '" + v_subj + "', '" + v_year + "', '" + v_subjseq + "');";
                            }
                    %>
                    <tr>
                        <td class="first"><%= v_dispnum %></td>
                        <td class="fs"><%= v_typenm %></td>
                        <td class="left"><a href="javascript:<%=sScriptString %>;"><%= v_title %></a></td>
                        <td><%=FormatDate.getFormatDate(v_sdate, "yyyy.MM.dd")%></td>
                        <td><%=v_status_view %></td>
                    </tr>
                    <%
                        }
                    }else{
                    %>
                    <tr>
                        <td class="first" colspan="5">등록된 내역이 없습니다.</td>
                    </tr>
                    <%} %>
                    </tbody>
                </table>
            </div>
            <!-- //board2 -->

            <div class="paging">
                <%= PageUtil.re2013_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
            </div>
            <!-- //paging -->
        </div>
        <!-- //#subCont -->
    </div>

    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>