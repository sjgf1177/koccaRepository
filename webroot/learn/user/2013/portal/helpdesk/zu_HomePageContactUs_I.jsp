<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.cp.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "1:1문의-교육문의, 학습지원");

    int    v_tabseq     = box.getInt("p_tabseq");
    String v_process    = box.getString("p_process");
    int    v_pageno     = box.getInt("p_pageno");
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    String v_indate     = "";
    String s_userid     = box.getSession("userid");
    String s_username   = box.getSession("name");
    String s_email      = box.getSession("email");
    String content      = "";
    String width        = "650";
    String height       = "200";

    box.put("title_str", "1:1문의-교육문의, 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//입력된 내용 저장
function insert() {

    if( document.form1.p_title.value == '' ) {
        alert("제목을 입력하세요.");
        document.form1.p_title.focus();
        return;
    }

    if (realsize(document.form1.p_title.value) > 200) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }

    if (realsize(document.form1.p_content.value) < 1) {
        alert("내용을 입력해주세요");
        document.form1.p_content.focus();
        return;
    }

    //document.getElementById("p_content").value = CrossEditor.GetBodyValue();

    document.form1.p_pageno.value = "1";
    document.form1.p_search.value     = "";
    document.form1.p_searchtext.value = "";
    document.form1.action = "/servlet/controller.homepage.HomePageContactServlet";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}

//빈내용 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;

    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);

    return (false);
}

//내용입력을 취소하고 리스트로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.MainServlet";
     //document.form1.p_process.value = "selectList";
     document.form1.submit();
}

//글자수 체크
function realsize( value ) {
 var len = 0;
 if ( value == null ) return 0;
 for(var i=0;i<value.length;i++){
     var c = escape(value.charAt(i));
     if ( c.length == 1 ) len ++;
     else if ( c.indexOf("%u") != -1 ) len += 2;
     else if ( c.indexOf("%") != -1 ) len += c.length/3;
 }
 return len;
}

//-->
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

    <!-- Form 영역 시작 -->
    <form name="form1" enctype = "multipart/form-data" method = "post" action="">
        <input type="hidden" name="p_tabseq"    value="<%=v_tabseq %>">
        <input type="hidden" name="p_pageno"     value="<%=v_pageno %>">
        <input type="hidden" name="p_search"     value="<%=v_search %>">
        <input type="hidden" name="p_searchtext" value="<%=v_searchtext %>">
        <input type="hidden" name="p_process" value="">

        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>고객센터</li>
                <li>교육문의 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습지원</a></li>
<!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
-->
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">자주하는 질문</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList" class="active">교육문의</a></li>
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
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
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
                    <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList"><img src="/images/2013/support/tabstudy1_off.jpg" alt="묻고 답하기" /></a></li>
                    <li><a href="#"><img src="/images/2013/support/tabstudy2_on.jpg" alt="1:1문의" /></a></li>
                </ul>
                <h3 class="blind">1:1문의 현재위치</h3>

                <div class="noticBox">
                    묻고답하기와는 달리 비공개로 진행되는 문의공간입니다. 질문의 공개를 원하지 않을 경우 1:1문의를 통해 자유로운 질문을 남겨주세요.<br />
                    <strong>개인메뉴 > 상담내역</strong>에서 <strong>답변</strong>을 <strong>확인</strong>하실 수 있습니다
                </div>

                <div class="tableWrap space1">
                    <table class="dataTable" summary="이 표는 교육문의 표로 주제,내용  내용으로 구성 되어 있습니다.">
                        <caption>1:1문의 표</caption>
                        <colgroup>
                            <col width="18%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row"><label for="subject">주제</label></th>
                            <td><input type="text" name="p_title" id="subject" class="inputText ipsz1" size="98" /></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="subjectxt">내용</label></th>
                            <td>
                                <textarea name="p_content" id="subjectxt" class="textArea" style="width:94%" rows="19" cols="13"></textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="btnArea">
                    <a href="javascript:insert();"><img src="/images/2013/btn/btn_regist.gif" alt="확인" /></a>
                    <a href="javascript:history.go(-1);"><img src="/images/2013/btn/btn_cancel.gif" alt="취소" /></a>
                </div>
            </div>
            <!-- //#subCont -->

        </div>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>