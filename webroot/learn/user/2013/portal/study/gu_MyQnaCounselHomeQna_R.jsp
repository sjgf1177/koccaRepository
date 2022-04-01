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
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int    v_seq        = box.getInt("p_seq");
    int    v_pageno     = box.getInt("p_pageno");
    String v_searchtext = box.getString("p_searchtext");
    String v_select     = box.getString("p_select");
    String v_selCategory = box.getString("p_selCategory");

    String  v_categorycd  = box.getStringDefault("p_categorycd","00");
    String  v_process     = box.getString("p_process");
    String  v_subj        = box.getString("p_subj");
    String  v_year        = box.getString("p_year");
    String  v_subjseq     = box.getString("p_subjseq");
    String  v_type        = box.getString("pp_type");
    String  v_repkind     = "";
    int     v_repseq      = 0;
    int     v_pagesize    = box.getInt("p_pagesize");

    String  v_action      = box.getString("p_action");
    int     v_upfilecnt   = box.getInt("p_upfilecnt");          //  서버에 저장되있는 파일수

    String s_userid     = box.getSession("userid");

    String v_title      = "";
    String v_contents   = "";
    String v_inuserid   = "";
    String v_indate     = "";
    String v_types      = "";
    String v_name       = "";
    String v_categorynm = "";
    String v_isopen     = "";
    String v_repstatus  = "";
    int    v_cnt        = 0;

    DataBox dbox        = (DataBox)request.getAttribute("selectQna");

    if(dbox != null) {
        v_inuserid          = dbox.getString("d_inuserid");
        v_name              = dbox.getString("d_name");
        v_types             = dbox.getString("d_types");
        v_indate            = dbox.getString("d_indate");
        v_title             = dbox.getString("d_title");
        v_seq               = dbox.getInt("d_seq");
        v_cnt               = dbox.getInt("d_cnt");
        v_contents          = dbox.getString("d_contents");
        v_categorycd        = dbox.getString("d_categorycd");
        v_categorynm        = dbox.getString("d_categorynm");
        v_isopen            = dbox.getString("d_isopen");
        v_repstatus         = dbox.getString("d_okyn1");
    }

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
        <input type="hidden" name="p_seq"         value="<%= v_seq %>">
        <input type="hidden" name="p_searchtext"  value="<%= v_searchtext %>">
        <input type="hidden" name="p_select"      value="<%= v_select %>">
        <input type="hidden" name="p_pageno"      value="<%= v_pageno %>">
        <input type="hidden" name="p_selCategory" value="<%= v_selCategory %>">
        <input type="hidden" name="p_process"     value="">
        <input type="hidden" name="p_userid"      value="<%= v_inuserid %>">
        <input type="hidden" name="p_types"       value="<%=v_types%>">
        <input type="hidden" name="pp_type"       value="<%=v_type%>">

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
                <!--2015-01-09 개편 변경
                <li>
                    나의 강의실 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <!--
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
            <h2 class="subTit">상담내역<!--<img src="/images/2013/online_edu/counsel_title.gif" alt="상담내역" />//--></h2>

            <div id="subCont" class="myClass2">

                <!-- 2015-01-09 개편 삭제
                <ul class="tabCus">
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/2013/online_edu/myclass_tab_1_off.gif" alt="나의 강의실" /></a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage"><img src="/images/2013/online_edu/myclass_tab_2_on.gif" alt="상담내역" /></a></li>
                    <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew"><img src="/images/2013/online_edu/myclass_tab_3_off.gif" alt="설문" /></a></li>
                </ul>

                //-->
                <h3 class="blind">상담내역 현재위치</h3>

                <div class="article">
                        <div class="info">
                            <p class="title"><%= v_title %></p>
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
                    ArrayList list = (ArrayList)request.getAttribute("selectAns");

                    String v_anstitle      = "";
                    String v_anscontents   = "";
                    String v_ansinuserid   = "";
                    String v_ansindate     = "";
                    String v_ansname       = "";
                    String v_anscategorynm = "";
                    String v_savefile      = "";
                    String v_realfile      = "";

                    if(list != null && list.size() != 0 ) {
                        for(int i = 0; i < list.size(); i++) {
                            DataBox Ansbox         = (DataBox)list.get(i);

                            v_ansinuserid = Ansbox.getString("d_inuserid");
                            v_ansname = Ansbox.getString("d_name");
                            v_ansindate = Ansbox.getString("d_indate");
                            v_anstitle = Ansbox.getString("d_title");
                            v_anscontents = Ansbox.getString("d_contents");
                            v_anscategorynm = Ansbox.getString("d_categorynm");
                            v_savefile = Ansbox.getString("d_savefile");
                            v_realfile = Ansbox.getString("d_realfile");
    %>
                        <div class="info3">
                            <p class="name"><%=v_ansname %></p>
                            <p class="date"><%= FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd HH:mm") %></p>
                        </div>
                        <div class="inner2">
                            <%=StringManager.removeHTMLHead(v_anscontents).replaceAll("\r\n","<br/>") %>
                        </div>
    <%
                            if(!v_savefile.equals("")) { 
    %>
                        <div class="info2">
                            <p class="file">
                                <a href="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_savefile%>&p_realfile=<%=v_realfile%>"><%=v_realfile %></a>

                            </p>
                        </div>
    <%
                            } 
    %>
    <% 
                        }
                    } else {
    %>
                        <div class="inner2">
                            등록된 답변이 없습니다.
                        </div>

    <%
                    }
    %>
                    </div>
                    <!-- //article -->

                    <table class="bbsNavi" style="display: none;">
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
                    </table>

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