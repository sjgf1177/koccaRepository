<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if (box == null) box = RequestManager.getBox(request);
    int v_tabseq         = box.getInt("p_tabseq");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_seq = 0,v_readcnt = 0,v_cnt = 0;

    String v_inuserid   = "";
    String v_position   = "";
    String v_indate     = "";
    String v_title      = "";
    String tLink        = "";
    String v_types      = "";
    String v_name       = "";
    String v_categorycd = "";
    String v_categorynm = "";
    String v_isopen     = "";
    String v_repstatus  = "";
    String v_repstatusnm = "";


    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    ArrayList selectList = (ArrayList)request.getAttribute("selectList");

    String  v_searchtext     = box.getString("p_searchtext");
    String  v_search         = box.getString("p_search");
    String  v_selCategory    = box.getString("p_selCategory");

    int v_pageno             = box.getInt("p_pageno");
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

    int  v_totalrowcount =  0;

    String v_classname1      = "";
    String v_classname2      = "";

    box.put("title_str", "묻고 답하기 - 교육문의 - 고객센터");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//입력페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.p_pageno.value = "<%= v_pageno %>";
    document.form1.submit();
}

function search_enter(e)  {
    if (e.keyCode =='13'){  selectList();  }
}


//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "";
    document.form1.submit();
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.submit();
}

//카테고리에 맞는 리스트 검색
function changeCategory() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value    = "";
    document.form1.submit();
}

//등록 페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
     document.form1.p_process.value = "";
     document.form1.submit();
}
//-->
</script>

<!-- container-new 시작 //-->
<div id="container-new">


    <!-- form 시작 -->
    <form name="form1" method = "post" action="">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="p_pageno"      value="<%=v_pageno%>">
    <input type="hidden" name="p_seq"         value="">
    <input type="hidden" name="p_userid"      value="">
    <input type="hidden" name="p_upfilecnt"   value="">
    <input type="hidden" name="p_types"       value="<%=v_types%>">

        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>고객센터</li>
                <li>교육문의 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습지원</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">자주하는 질문</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList" class="active">교육문의</a></li>
<!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
-->
<!--
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
-->
                    </ul>
                </li>
            </ul>

            <!-- //학습지원 -->
            <!--
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList"><strong><u>교육문의</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
                </ul>
            </div>
            -->
            <!-- //서브메인용 -->
            
        </div>
        <!-- //#mainMenu -->

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">


            <h2 class="subTit">교육문의<!--<img src="/images/2013/support/studyask_title.gif" alt="교육문의" />//--></h2>

            <div id="subCont">
                <ul class="tabCus">
                    <li><a href="#"><img src="/images/2013/support/tabstudy1_on.jpg" alt="묻고 답하기" /></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=insertPage;"><img src="/images/2013/support/tabstudy2_off.jpg" alt="1:1문의" /></a></li>
                </ul>
                <h3 class="blind">묻고답하기 현재위치</h3>

                <div class="noticBox">
                    질문 후 운영진의 답변이 완료되면 회원님의 모바일폰으로 답변완료 안내 SMS가 발송됩니다.
                </div>

                <div class="noticBox">
                    <strong>[3D 입체콘텐츠 과정]</strong><br />
                    본 사업은 2014년 4월부로 한국콘텐츠진흥원에서 정보통신산업진흥원(<a href="http://uhd3d.nipa.kr" target="_blank" style="color:#0000ff;">uhd3d.nipa.kr</a>)으로 사업이 이관되었음을 알려드립니다. <br />
                    <%-- 문의 : 070-7551-6738 --%>
                </div>
            <!--  페이지 내 검색 -->
            <div class="search">
                <fieldset>
                    <legend>통합 검색</legend>
                    <select name="p_search" title="검색 분류">
                        <option value="TITL" <%=v_search.equals("TITL")?"selected":"" %>>제목</option>
                        <option value="CONT" <%=v_search.equals("CONT")?"selected":"" %>>내용</option>
                        <option value="NAME" <%=v_search.equals("NAME")?"selected":"" %>>작성자</option>
                    </select>
                    <input type="text" name="p_searchtext" value="<%=v_searchtext %>" title="검색어" class="keyword" onkeypress="search_enter(event);" />
                    <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" onclick="javascript:selectList();" />
                </fieldset>
            </div>

                <div class="tableWrap2 space5">
                    <table class="board" summary="이표는 나의 강의실 종료된 과정표로 카테고리,과정명,교육기간,복습,성적,수료여부,수료증 으로 구성되어있습니다.">
                        <caption>종료된 과정</caption>
                        <colgroup>
                            <col width="10%"/>
                            <col width="10%"/>
                            <col width="*"/>
                            <col width="12%"/>
                            <col width="11%"/>
                            <col width="13%"/>
                            <col width="7%"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col">
                                <select class="" name="p_selCategory" onchange="selectList()">
                                    <option value="" <%=box.getString("p_selCategory").equals("ALL")? "selected":"" %>>전체</option>
                                    <option value="B1" <%=box.getString("p_selCategory").equals("B1")? "selected":"" %>>방송영상</option>
                                    <option value="A1" <%=box.getString("p_selCategory").equals("A1")? "selected":"" %>>게임개발</option>
                                    <option value="C1" <%=box.getString("p_selCategory").equals("C1")? "selected":"" %>>문화콘텐츠</option>
                                </select>
                            </th>
                            <th scope="col">제목</th>
                            <th scope="col">작성자</th>
                            <th scope="col">등록일</th>
                            <th scope="col">답변여부</th>
                            <th scope="col">조회</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if(selectList.size() != 0){
                                for(int i = 0; i < selectList.size(); i++) {
                                    DataBox dbox = (DataBox)selectList.get(i);
                                    v_dispnum    = dbox.getInt("d_dispnum");
                                    v_inuserid   = dbox.getString("d_inuserid");
                                    v_name       = dbox.getString("d_name");
                                    v_cnt        = dbox.getInt("d_cnt");
                                    v_indate     = dbox.getString("d_indate");
                                    v_title      = dbox.getString("d_title");
                                    v_seq        = dbox.getInt("d_seq");
                                    v_types      = dbox.getString("d_types");
                                    v_readcnt    = dbox.getInt("d_readcnt");
                                    v_upfilecnt  = dbox.getInt("d_filecnt");
                                    v_totalpage  = dbox.getInt("d_totalpage");
                                    v_rowcount   = dbox.getInt("d_rowcount");
                                    v_categorycd = dbox.getString("d_categorycd");
                                    v_categorynm = dbox.getString("d_categorynm");
                                    v_isopen     = dbox.getString("d_isopen");
                                    v_repstatus  = dbox.getString("d_okyn1");
                                    v_totalrowcount = dbox.getInt("d_totalrowcount");

                                    // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                                    if (!v_searchtext.equals("")&&v_search.equals("title")) {
                                        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                                    } else if(!v_searchtext.equals("")&&v_search.equals("name")){
                                        v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                                    }

                                    if(v_categorynm.equals("")){
                                        v_categorynm = "기타";
                                    }

                                    if(v_repstatus.equals("3")){
                                        v_repstatusnm = "<img src=\"/images/2013/btn/btn_answer_done.gif\" alt=\"답변완료\" />";
                                    }else if(v_repstatus.equals("2")){
                                        v_repstatusnm = "준비중";
                                    }else{
                                        v_repstatusnm = "미처리중";
                                    }
                        %>
                        <tr>
                            <td class="first"><%=v_dispnum%></td>
                            <td><%=v_categorynm%></td>
                            <td class="left">
                                <%if (v_isopen.equals("N")){%>
                                    <img src="/images/2013/common/ico_rock.gif" alt="잠금 표시" />
                                <% } %>
                                <%if (v_isopen.equals("N") && !s_userid.equals(v_inuserid)){%>
                                    <a href="javascript:alert('비밀글은 작성자만 볼 수 있습니다.');"><%=StringManager.formatTitle(v_title, 30)%></a>
                                <%}else{ %>
                                    <a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_inuserid%>','<%=v_types%>');"><%=StringManager.formatTitle(v_title, 30)%></a>
                                <%}%>
                                <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
                                    <img src="/images/2013/common/ico_new2.gif" alt="새로올라온 글 표시" />
                                <%} %>
                            </td>
                            <td><%=v_name%></td>
                            <td><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%></td>
                            <td><%=v_repstatusnm %></td>
                            <td><%=v_cnt%></td>
                        </tr>
                        <%
                                }
                            }else{
                        %>
                        <tr>
                            <td class="first" colspan="7">등록된 내용이 없습니다.</td>
                        </tr>
                        <%} %>
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap2 -->

                <div class="btnArea right">
                    <a href="javascript:insertPage();"><img src="/images/2013/btn/bnt_write.gif" alt="글작성" /></a>
                </div>

                <div class="paging pgSpace">
                    <%= PageUtil.re2013_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
                </div>
                <!-- //paging -->
            </div>
            <!-- //#subCont -->


        </div>
    </form>
    <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>