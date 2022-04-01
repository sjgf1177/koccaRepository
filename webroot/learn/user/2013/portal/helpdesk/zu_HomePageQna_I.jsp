<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_title     = "";
    String v_content   = "";
    String v_usernm    = "";
    String v_userid    = "";
    String v_regdt     = "";

    int v_seq = box.getInt("p_seq");
    String v_searchtext = box.getString("p_searchtext");
    String v_select     = box.getString("p_select");
    String v_selCategory = box.getString("p_selCategory");
    int    v_pageno     = box.getInt("p_pageno");
    String s_usernm     = box.getSession("name");
    String content      = "";
    String width        = "650";
    String height       = "200";

    String s_userid     = box.getSession("userid");

    box.put("title_str", "글 작성 - 묻고 답하기 - 교육문의 - 고객센터");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
    //리스트화면으로 이동
    function selectList() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }

    //데이터를 입력한다.
    function insert() {

        var frm = document.form1;

        if (frm.p_categorycd.options[frm.p_categorycd.selectedIndex].value == "") {
            alert("분류를 선택하세요!");
            frm.p_categorycd.focus();
            return;
        }

        if (blankCheck(frm.p_title.value)) {
            alert("제목을 입력하세요!");
            frm.p_title.focus();
            return;
        }

        if (realsize(frm.p_title.value) > 100) {
            alert("제목은 한글기준 50자를 초과하지 못합니다.");
            frmp_title.focus();
            return;
        }

        if (blankCheck(frm.p_content.value)) {
            alert("내용을 입력하세요!");
            frm.p_content.focus();
            return;
        }

        frm.p_searchtext.value    = "";
        frm.p_select.value        = "";
        frm.p_selCategory.value   = "";
        frm.p_pageno.value        =  1;

        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

    //입력 내용의 공백을 체크
    function blankCheck( msg ) {
        var mleng = msg.length;
        chk=0;

        for (i=0; i<mleng; i++) {
            if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
        }
        if ( chk == 0 ) return (true);

        return (false);
    }
    function realsize( value ) {//글자수를 체크
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


    function checkYN()
    {
        var r=$("[name='p_isopen']").val();
        if(r=="Y")
            $("[name='p_isopen']").val("N");
        else
            $("[name='p_isopen']").val("Y");

        //alert(document.form1.p_isopen.value);
    }


</script>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

    <!-- Form 영역 시작 -->
    <form name="form1" enctype = "multipart/form-data" method = "post" action="">
        <input type="hidden" name="p_seq"           value="<%= v_seq %>">
        <input type="hidden" name="p_searchtext"    value="<%=v_searchtext %>">
        <input type="hidden" name="p_select"        value="<%=v_select %>">
        <input type="hidden" name="p_selCategory"   value="<%=v_selCategory %>">
        <input type="hidden" name="p_pageno"        value="<%=v_pageno %>">
        <input type="hidden" name="p_process"       value="">

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
<!-- 2015-01-08 개편삭제
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


                <div class="tableWrap5 space1">
                    <table class="dataTable5" summary="이 표는 교육문의 표로 주제,내용  내용으로 구성 되어 있습니다.">
                        <caption>묻고답하기 표</caption>
                        <colgroup>
                            <col width="18%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row"><label for="sel">분야</label></th>
                            <td>
                                <select id="txtfield1" tabindex="176" name="p_categorycd" class="select">
                                    <option value="">분야선택</option>
                                    <option value="A1">게임개발</option>
                                    <option value="B1">방송영상</option>
                                    <option value="C1">문화콘텐츠</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">옵션</th>
                            <td>
                                <span class="rd0"><input type="checkbox" name="p_isopen" id="ch1" value="Y" onclick="checkYN();" /> <label for="ch1">이 글을 공개하지 않습니다.</label></span>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="subject">주제</label></th>
                            <td><input type="text" name="p_title" id="subject" class="inputText ipsz1" size="98" /></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="subjectxt">내용</label></th>
                            <td>
                                <textarea id="subjectxt" name="p_content" class="textArea" style="width:94%" rows="19" cols="13"></textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="btnArea">
                    <a href="javascript:insert();"><img src="/images/2013/btn/btn_regist.gif" alt="등록" /></a>
                    <a href="javascript:history.go(-1);"><img src="/images/2013/btn/btn_cancel.gif" alt="취소" /></a>
                </div>
            </div>
            <!-- //#subCont -->

        </div>
    </form>
    <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>