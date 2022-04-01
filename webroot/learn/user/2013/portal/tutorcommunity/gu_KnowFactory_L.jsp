<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_process = box.getString("p_process");
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0) v_pageno = 1;
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    int v_pagesize = box.getInt("p_pagesize");
    int v_totalrowcount = 0;
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    String v_searchtext = box.getString("p_searchtext");
    String v_search = box.getString("p_search");
    String v_category = box.getString("p_area");

    String v_tgubun = "";
    String v_tabseq = "";
    String v_seq = "";
    String v_types = "";
    String v_title = "";
    String v_indate = "";
    String v_inuserid = "";
    String v_cnt = "";
    String v_name = "";
    String v_subj = "";
    String v_subjseq = "";
    String v_year = "";
    String v_lesson = "";
    String v_subjnm = "";
    String v_area = "";
    String v_upperclass = "";
    String v_middleclass = "";
    String v_lowerclass = "";
    String v_answercnt = "";
    int v_commentcnt = 0;
    String ss_tgubun = box.getString("ss_tgubun");

    ArrayList list = (ArrayList)request.getAttribute("selectLastList");

    String full_toggle = "off";
    String sub1_toggle = "off";
    String sub2_toggle = "off";
    String sub3_toggle = "off";
    String sub4_toggle = "off";
    String sub5_toggle = "off";

    String sub_navi = "";
    String categoryName = "";

    if (v_category.equals("C0")){
        sub1_toggle = "on";
        sub_navi = "문의";
        categoryName = "문의";
    }else if (v_category.equals("B0")){
        sub2_toggle = "on";
        sub_navi = "방송영상";
        categoryName = "방송영상";
    } else if (v_category.equals("G0")){
        sub3_toggle = "on";
        sub_navi = "게임개발";
        categoryName = "개임개발";
    } else if (v_category.equals("K0")){
        sub4_toggle = "on";
        sub_navi = "문화콘텐츠";
        categoryName = "만애캐/음악&middot;공연";
    }else if (v_category.equals("MINE")){
        sub5_toggle = "on";
        sub_navi = "내가작성한글";
        categoryName = "내가 작성한글";
    } else {
        full_toggle = "on";
        sub_navi = "종합";
        categoryName = "전체";
    }

    // box.put("title_str", categoryName + " - 커뮤니티 - 온라인교육");
    box.put("title_str", categoryName + " - 개인메뉴");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script language="JavaScript" type="text/JavaScript">
//입력페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value="InsertPage";
    //document.form1.p_pageno.value="<%= v_pageno %>";
    document.form1.submit();
}

//상세화면 페이지로 이동
function select(tgubun, tabseq, seq, subj, subjseq, year, lesson, types) {

    document.form1.method = "get";
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value  = "selectView";
    document.form1.p_tgubun.value   = tgubun;
    document.form1.p_tabseq.value   = tabseq;
    document.form1.p_seq.value      = seq;
    document.form1.p_types.value    = types;
    document.form1.p_subj.value     = subj;
    document.form1.p_year.value     = year;
    document.form1.p_subjseq.value  = subjseq;
    document.form1.p_lesson.value   = lesson;

    document.form1.submit();
}
function search_enter(e)  {
    if (e.keyCode =='13'){  selectList();  }
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
    /*
    if (form1.p_searchtext.value == ""){
        alert("검색 할 내용을 입력해 주세요");
        return false;
    } else {
        document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
        document.form1.p_process.value="ListPage";
        document.form1.p_pageno.value="1";
        document.form1.submit();
    }
    */

    var searchWord = $("input:text[name=p_searchtext]").val();

    searchWord = fnCheckInvalidChar(searchWord);

    $("input:text[name=p_searchtext]").val(searchWord);

    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.p_pageno.value = "1";
    document.form1.submit();

}

function sstgubun_listpage(){
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value="ListPage";
    document.form1.submit();
}
//페이지 이동
function goPage(pageNum) {

    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value="ListPage";
    document.form1.submit();
}

// Tab 선택시 동작
function showTab(clickTab)
{
   //alert(clickTab);
    g_showTab(clickTab, 2);
}

function g_showTab(tabNo, count)
{

    var clickTabNo = new Number(tabNo);
    var tabCount   = new Number(count);

    if( clickTabNo == 0 || tabCount == 0 ) return;

    for(i=1; i<=tabCount; i++)
    {
        div = document.all["tab_"+i];

        if(!div) return;

        if( clickTabNo == i ) {
            // 활성화된 tab 클릭시 return
            if(div.style.display == "") return;
            div.style.display = ""
        }
        else {
            // 선택되지 않는 탭들은 깜빡임 방지.
            if(div.style.display != "none") {
                div.style.display = "none";
            }
        }
    }
}
</script>
<!-- container-new 시작 //-->
<div id="container-new">

    <form name="form1" method="post" action="" onsubmit="return selectList();">
        <input type="hidden" name="p_process" value="<%=v_process%>" />
        <input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
        <input type="hidden" name="p_tgubun" value="" />
        <input type="hidden" name="p_tabseq" value="" />
        <input type="hidden" name="p_seq" value="" />
        <input type="hidden" name="p_types" value="" />
        <input type="hidden" name="p_area" value="<%=v_category%>" />
        <input type="hidden" name="p_subj" value="" />
        <input type="hidden" name="p_year" value="" />
        <input type="hidden" name="p_subjseq" value="" />
        <input type="hidden" name="p_lesson" value="" />

        <div id="mainMenu">
            <!-- 온라인 교육 -->
            <ul class="location">
                <li>개인메뉴</li>
                <li>내가 작성한 글 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a></li>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육</a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" class="active">내가작성한 글</a></li>
                        <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                    </ul>
                </li>

                <!-- 2015-01-09 개편 변경
                <li>홈</li>
                <li>온라인 교육</li>
                <li>
                    커뮤니티 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                        <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                    </ul>
                </li>
                //-->
            </ul>

            <!-- 서브메인용 -->
            <!-- 2015-01-09 개편 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><strong><u>커뮤니티</u></strong></a></li>
                </ul>
            </div>
            //-->
            <!-- //서브메인용 -->
        </div>

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
                <h2 class="subTit">
    <%
                if(v_category.equals("MINE")){
    %>
                    내가 작성한글<!--<img src="/images/2013/online_edu/myCommunity_title.gif" alt="내가 작성한글" />//--></h2>
    <%
                }else {
    %>
                    커뮤니티<!--<img src="/images/2013/online_edu/community_title.gif" alt="커뮤니티" />//--></h2>
    <%
                }
    %>
                <div id="subCont">
                <!--  페이지 내 검색 -->
                <div class="search">
                    <fieldset>
                        <legend>통합 검색</legend>
                        <select name="p_search" title="검색 분류">
                            <option value="TITL" <%=v_search.equals("TITL")?"selected":"" %>>제목</option>
                            <option value="SUBJ" <%=v_search.equals("SUBJ")?"selected":"" %>>과정</option>
                        </select>
                        <input type="text" name="p_searchtext" value="<%=v_searchtext %>" title="검색어" class="keyword" />
                        <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" />
                    </fieldset>
                </div>
                <!-- 2015-01-09 개편 삭제
                    <ul class="tabCus">
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/2013/online_edu/community_tab_1_<%=full_toggle %>.gif" alt="전체" /></a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=B0"><img src="/images/2013/online_edu/community_tab_2_<%=sub2_toggle %>.gif" alt="방송영상" /></a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=G0"><img src="/images/2013/online_edu/community_tab_3_<%=sub3_toggle %>.gif" alt="게임개발" /></a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=K0"><img src="/images/2013/online_edu/community_tab_4_<%=sub4_toggle %>.gif" alt="만애캐/음악공연" /></a></li>
                        <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list"><img src="/images/2013/online_edu/community_tab_6_off.gif" alt="cmu 국비지원" /></a></li>
    <%
            if ( !topWarp_userid.equals("") ) {
    %>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE"><img src="/images/2013/online_edu/community_tab_5_<%=sub5_toggle %>.gif" alt="내가 작성한 글" /></a></li>
    <%
            }
    %>
                    </ul>

                //-->

                    <h3 class="blind">전체 현재위치</h3>

                    <ul class="bbsType">
    <!--                    <li><a href="community_list_webzine.html"><img src="/images/2013/common/ico_webzine.gif" alt="" /> 웹진형</a></li>-->
    <!--                    <li class="on"><a href="javascript:void(0);"><img src="/images/2013/common/ico_bbs.gif" alt="" /> 게시판형</a></li>-->
                    </ul>

                    <div class="board2">
                        <table summary="글번호, 분류, 제목, 과정, 작성자, 등록일 조회로 구성되어있습니다.">
                            <colgroup>
                                <col width="5%"/>
                                <col width="*"/>
                                <col width="22%"/>
                                <col width="8%"/>
                                <col width="9%"/>
                                <col width="4%"/>
                            </colgroup>
                            <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col">
                                    <div class="divide">
                                        <select class="category" title="분류" name="ss_tgubun" onchange="javascript:sstgubun_listpage();">
                                            <option value="ALL">전체</option>
                                            <option value="D" <%if(ss_tgubun.equals("D")) out.println("selected"); %>>자료</option>
                                            <option value="I" <%if(ss_tgubun.equals("I")) out.println("selected"); %>>정보</option>
                                            <option value="Q" <%if(ss_tgubun.equals("Q")) out.println("selected"); %>>질문</option>
                                            <option value="T" <%if(ss_tgubun.equals("T")) out.println("selected"); %>>토론</option>
                                        </select>
                                        제목
                                    </div>
                                </th>
                                <th scope="col">과정</th>
                                <th scope="col">작성자</th>
                                <th scope="col">등록일</th>
                                <th scope="col">조회</th>
                            </tr>
                            </thead>
                            <tbody>
                            <!-- 아이콘
                            <img src="/images/2013/common/ico_pds.gif" alt="자료" />
                            <img src="/images/2013/common/ico_info.gif" alt="정보" />
                            <img src="/images/2013/common/ico_question.gif" alt="질문" />
                            <img src="/images/2013/common/ico_discussion.gif" alt="토론" />
                            -->
                            <%
                            if(list.size() > 0 ){
                                for(int i = 0; i < list.size(); i++) {
                                    DataBox dbox = (DataBox)list.get(i);

                                    v_tgubun        = dbox.getString("d_tgubun");
                                    v_tabseq        = dbox.getString("d_tabseq");
                                    v_seq           = dbox.getString("d_seq");
                                    v_types         = dbox.getString("d_types");
                                    v_title         = dbox.getString("d_title");
                                    v_inuserid      = dbox.getString("d_inuserid");
                                    v_cnt           = dbox.getString("d_cnt");
                                    v_name          = dbox.getString("d_name");
                                    v_indate        = dbox.getString("d_indate");
                                    v_subj          = dbox.getString("d_subj");
                                    v_subjseq       = dbox.getString("d_subjseq");
                                    v_year          = dbox.getString("d_year");
                                    v_lesson        = dbox.getString("d_lesson");
                                    v_subjnm        = dbox.getString("d_subjnm");
                                    v_area          = dbox.getString("d_area");
                                    v_upperclass    = dbox.getString("d_upperclass");
                                    v_middleclass   = dbox.getString("d_middleclass");
                                    v_lowerclass    = dbox.getString("d_loserclass");
                                    v_answercnt     = dbox.getString("d_answercnt");
                                    v_commentcnt    = dbox.getInt("d_commentcnt");


                                    v_dispnum   = dbox.getInt("d_dispnum");
                                    v_totalpage = dbox.getInt("d_totalpage");
                                    v_totalrowcount = dbox.getInt("d_totalrowcount");

                                    String t_addate = v_indate.substring(0,8);
                                    String t_agodate = dbox.getString("d_agodate").substring(0,8);
                                    int c_addate = Integer.parseInt(t_addate);
                                    int c_agodate = Integer.parseInt(t_agodate);
                                    boolean new_che = false;
                                    String new_tag = "";
                                    if(c_addate-c_agodate > 0){
                                        new_che = true;
                                        new_tag = "<img src=\"/images/2013/common/ico_new3.gif\" alt=\"new\">";
                                    }

                                    String v_tgubunnm = "";
                                    if(v_tgubun.equals("T")){v_tgubunnm = "<img src=\"/images/2013/common/ico_discussion.gif\" alt=\"토론\" />";}
                                    else if(v_tgubun.equals("Q")){v_tgubunnm = "<img src=\"/images/2013/common/ico_question.gif\" alt=\"질문\" />";}
                                    else if(v_tgubun.equals("C")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_c.gif\" alt=\"자유\" />";}
                                    else if(v_tgubun.equals("D")){v_tgubunnm = "<img src=\"/images/2013/common/ico_pds.gif\" alt=\"자료\" />";}
                                    else if(v_tgubun.equals("I")){v_tgubunnm = "<img src=\"/images/2013/common/ico_info.gif\" alt=\"정보\" />";}
                                    else if(v_tgubun.equals("R")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_r.gif\" alt=\"문의\" />";}
                                    else if(v_tgubun.equals("N")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_n.gif\" alt=\"공지\" />";}

                                    // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                                    //if (!v_searchtext.equals("")) {
                                    //  v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">"+v_searchtext+"</font>");
                                    //}
                            %>
                            <tr>
                                <td class="first"><%=v_dispnum%></td>
                                <td class="left bldnone">
                                    <%=v_tgubunnm %>
                                    <a href="javascript:select('<%=v_tgubun%>','<%=v_tabseq%>','<%=v_seq%>','<%=v_subj%>','<%=v_subjseq%>','<%=v_year%>','<%=v_lesson%>','<%=v_types%>')"><%=StringManager.formatTitle(v_title,32)%></a>
                                    <%if(new_che)%> <%=new_tag %>
                                    <%if(v_commentcnt > 0)out.print("<span class=\"repl\"> [<em>"+v_commentcnt+"</em>]</span>"); %><%if(!v_answercnt.equals("0") && (v_tgubun.equals("Q")||v_tgubun.equals("R")))out.print("<span class=\"repl\"> 답변 <em>"+v_answercnt+"</em></span>"); %>
                                </td>
                                <td class="left"><%=v_subjnm %></td>
                                <td><%=v_name %></td>
                                <td><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%></td>
                                <td><%=v_cnt%></td>
                            </tr>
                            <%} }else{ %>
                            <tr>
                                <td colspan="6" class="first">등록된 게시물이 없습니다.</td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
    <%
            if ( !topWarp_userid.equals("") ) {
    %>
                        <!--
                        <div class="btnArea right">
                            <a href="javascript:insertPage();"><img src="/images/2013/btn/bnt_write.gif" alt="글작성" /></a>
                        </div>
                        //-->
    <%
            }
    %>

                    </div>
                    <!-- //tableWrap2 -->


                    <div class="paging pgSpace">
                        <%= PageUtil.re2013_printPageListDiv(v_totalpage, v_pageno, 0, 15, v_rowcount) %>
                    </div>
                    <!-- //paging -->

                </div>
                <!-- //#subCont -->
        </div>
    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>
