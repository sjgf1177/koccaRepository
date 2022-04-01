<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int    v_seq        = box.getInt("p_seq");
    int    v_pageno     = box.getInt("p_pageno");
    String v_searchtext = box.getString("p_searchtext");
    String v_select     = box.getString("p_select");
    String v_selCategory = box.getString("p_selCategory");

    String s_userid     = box.getSession("userid");

    String v_title      = "";
    String v_contents   = "";
    String v_inuserid   = "";
    String v_indate     = "";
    String v_types      = "";
    String v_name       = "";
    String v_categorycd = "";
    String v_categorynm = "";
    String v_isopen     = "";
    String v_repstatus  = "";
    int    v_cnt        = 0;

    //윗글
    int    u_seq        = box.getInt("p_seq")+1;
    String u_title      = "";
    String u_name       = "";
    String u_indate     = "";
    String u_types      = "";
    String u_inuserid   = "";
    String u_isopen     = "";

    //아래글
    int    d_seq        = box.getInt("p_seq")-1;
    String d_title      = "";
    String d_name       = "";
    String d_indate     = "";
    String d_types      = "";
    String d_inuserid   = "";
    String d_isopen     = "";

    DataBox dbox        = (DataBox)request.getAttribute("selectQna");
    DataBox upbox        = (DataBox)request.getAttribute("selectUpQna");//윗글
    DataBox downbox        = (DataBox)request.getAttribute("selectDownQna");//아래글

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

    if(downbox != null) {
        d_inuserid          = downbox.getString("d_inuserid");
        d_name              = downbox.getString("d_name");
        d_types             = downbox.getString("d_types");
        d_indate            = downbox.getString("d_indate");
        d_title             = downbox.getString("d_title");
        d_seq               = downbox.getInt("d_seq");
        d_isopen            = downbox.getString("d_isopen");
    }

    box.put("title_str", v_title + " - 묻고 답하기 - 교육문의 - 고객센터");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">

    //리스트페이지로 이동
    function selectList() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }

    function searchList() {
        if (form1.p_searchtext.value == ""){
            alert("검색 할 내용을 입력해 주세요");
        }else{
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
        }
    }

    //수정페이지로 이동
    function updatePage() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    //답변 등록 페이지로 이동
    function replyPage() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "replyPage";
        document.form1.submit();
    }

    //꼬릿말 등록
    function insertcomment() {
        if(document.form2.commentqna.value==""){
            alert("꼬릿말을 등록하세요");
            return;
        }
        document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
        document.form2.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form2.p_process.value = "commentInsertPage";
        document.form2.submit();
    }


    //뷰 화면 삭제
    function deleteAction() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }

    //꼬릿말 삭제
    function deleteComment(v_commentseq) {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form2.p_commentseq.value = v_commentseq;
            document.form2.action = "/servlet/controller.homepage.HomePageQNAServlet";
            document.form2.p_process.value = "deleteComment";
            document.form2.submit();
        }
        else {
            return;
        }
    }

    //위,아래 페이지로 이동
    function select(seq, upfilecnt, userid, types) {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value   = "selectView";
        document.form1.p_seq.value       = seq;
        document.form1.p_userid.value    = userid;
        document.form1.p_types.value     = types;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.submit();
    }

</script>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

    <!-- Form 영역 시작 -->
    <form name="form1" method = "post" action="">
        <input type="hidden" name="p_seq"         value="<%= v_seq %>">
        <input type="hidden" name="p_pageno"      value="<%= v_pageno %>">
        <input type="hidden" name="p_selCategory" value="<%= v_selCategory %>">
        <input type="hidden" name="p_process"     value="">
        <input type="hidden" name="p_userid"      value="<%= v_inuserid %>">
        <input type="hidden" name="p_types"       value="<%=v_types%>">
        <input type="hidden" name="p_upfilecnt"   value="">

        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>고객센터</li>
                <li>
                    교육문의 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습지원</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">자주하는 질문</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList" class="active">교육문의</a></li>
<!-- 2015-01-08 개편 수정
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList" class="active">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
-->
                    </ul>
                </li>
            </ul>
            <!-- //학습지원 -->
<!-- 2015-01-08 개편 삭제
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

                <div class="article">
                    <div class="info">
                        <p class="title"><%= v_title %></p>
                        <p class="date"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></p>
                    </div>

                    <div class="inner">
                        <%=v_contents.replace("\n","<BR>")%>
                    </div>
                </div>

                <%
                if(v_repstatus.equals("3")){
                    ArrayList list = (ArrayList)request.getAttribute("selectAns");
                    String v_anstitle      = "";
                    String v_anscontents   = "";
                    String v_ansinuserid   = "";
                    String v_ansindate     = "";
                    String v_ansname       = "";
                    String v_anscategorynm = "";

                    if(list.size() != 0) {

                        for(int i = 0; i < list.size(); i++) {
                        DataBox Ansbox = (DataBox)list.get(i);
                        v_ansinuserid          = Ansbox.getString("d_inuserid");
                        v_ansname              = Ansbox.getString("d_name");
                        v_ansindate            = Ansbox.getString("d_indate");
                        v_anstitle             = Ansbox.getString("d_title");
                        v_anscontents          = Ansbox.getString("d_contents");
                        v_anscategorynm        = Ansbox.getString("d_categorynm");

                %>
                <p class="answer"><a href="#">답변</a></p>

                <div class="article">
                    <div class="info3">
                        <p class="name">작성자</p>
                        <p class="date"><%=FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd HH:mm") %></p>
                    </div>
                    <div class="inner2">
                        <%=StringManager.removeHTMLHead(v_anscontents)%>
                    </div>
                </div>
                <%
                }}}
                %>

                <table class="bbsNavi">
                    <colgroup>
                        <col width="70">
                        <col>
                        <col width="70">
                        <col width="70">
                    </colgroup>
                    <%
                    if(upbox != null) {
                        u_inuserid          = upbox.getString("d_inuserid");
                        u_name              = upbox.getString("d_name");
                        u_types             = upbox.getString("d_types");
                        u_indate            = upbox.getString("d_indate");
                        u_title             = upbox.getString("d_title");
                        u_seq               = upbox.getInt("d_seq");
                        u_isopen            = upbox.getString("d_isopen");
                    %>
                    <tr>
                        <td class="pre">위글</td>
                        <td>
                        <%if (u_isopen.equals("N")){%>
                            <img src="/images/2013/common/ico_rock.gif" alt="잠금 표시" />
                        <% } %>
                        <%if (u_isopen.equals("N") && !s_userid.equals(u_inuserid)){%>
                            <a href="javascript:alert('비밀글은 작성자만 볼 수 있습니다.');"><%=StringManager.removeHTML(u_title) %></a>
                        <%}else{ %>
                            <a href="javascript:select('<%=u_seq%>','0','<%=u_inuserid%>','<%=u_types%>');"><%=StringManager.removeHTML(u_title) %></a>
                        <%} %>
                        </td>
                        <td><%=u_name %></td>
                        <td><%=FormatDate.getFormatDate(u_indate, "yyyy.MM.dd") %></td>
                    </tr>
                    <%} %>

                    <%
                    if(downbox != null) {
                        d_inuserid          = downbox.getString("d_inuserid");
                        d_name              = downbox.getString("d_name");
                        d_types             = downbox.getString("d_types");
                        d_indate            = downbox.getString("d_indate");
                        d_title             = downbox.getString("d_title");
                        d_seq               = downbox.getInt("d_seq");
                        d_isopen            = downbox.getString("d_isopen");
                    %>
                    <tr>
                        <td class="next">아래글</td>
                        <td>
                        <%if (d_isopen.equals("N")){%>
                                    <img src="/images/2013/common/ico_rock.gif" alt="잠금 표시" />
                        <% } %>
                        <%if (d_isopen.equals("N") && !s_userid.equals(d_inuserid)){%>
                            <a href="javascript:alert('비밀글은 작성자만 볼 수 있습니다.');"><%=StringManager.removeHTML(d_title) %></a>
                        <%}else{ %>
                            <a href="javascript:select('<%=d_seq%>','0','<%=d_inuserid%>','<%=d_types%>');"><%=StringManager.removeHTML(d_title) %></a>
                        <%} %>
                        </td>
                        <td><%=d_name %></td>
                        <td><%=FormatDate.getFormatDate(d_indate, "yyyy.MM.dd") %></td>
                    </tr>
                    <%} %>
                </table>

                <div class="btnArea right">
                <%if(v_inuserid.equals(box.getSession("userid")) && !v_repstatus.equals("3")){%>
                    <a href="javascript:updatePage();"><img src="/images/2013/btn/btn_retouch.gif" alt="수정" /></a>
                    <a href="javascript:deleteAction();"><img src="/images/2013/btn/btn_delete.gif" alt="삭제" /></a>
                <% } %>
                    <a href="javascript:selectList();"><img src="/images/2013/btn/btn_list.gif" alt="목록" /></a>
                </div>

            </div>
            <!-- //#subCont -->


        </div>
    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>