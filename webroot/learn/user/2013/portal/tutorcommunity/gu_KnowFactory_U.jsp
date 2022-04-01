<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명: gu_KnowBoard_U.jsp
//  3. 개      요: 지식공유 최근지식 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
        RequestBox box = (RequestBox)request.getAttribute("requestbox");

        String v_title      = "";
        String v_contents   = "";
        String v_usernm     = "";
        String v_inuserid   = "";
        String v_indate     = "";
        String v_types      = "";
        String v_categorycd = "";
        String v_isopen     = "";
        String content      = "";
        String v_subjnm     = "";
        String v_tgubun     = "";
        String v_tabseq     = "";
        String v_cnt        = "";
        String v_area       = "";
        String v_subj       = "";
        String v_subjseq    = "";
        String v_year       = "";
        String v_lesson     = "";


        int     v_seq        = box.getInt("p_seq");
        String  v_searchtext = box.getString("p_searchtext");
        String  v_select     = box.getString("p_select");
        int     v_pageno     = box.getInt("p_pageno");
        String  v_selCategory = box.getString("p_selCategory");

        String  width        = "650";
        String  height       = "200";

        String  s_name = box.getSession("name");

        DataBox dbox = (DataBox)request.getAttribute("SelectView");
        if(dbox != null) {
            v_usernm       = dbox.getString("d_name");
            v_categorycd = dbox.getString("d_categorycd");

            v_tgubun     = dbox.getString("d_gubun");
            v_tabseq     = dbox.getString("d_tabseq");
            v_seq        = dbox.getInt("d_seq");
            v_types      = dbox.getString("d_types");
            v_title      = dbox.getString("d_title");
            content      = dbox.getString("d_contents");
            v_indate     = dbox.getString("d_indate");
            v_inuserid   = dbox.getString("d_inuserid");
            v_cnt        = dbox.getString("d_cnt");
            v_subjnm     = dbox.getString("d_subjnm");
            v_area       = dbox.getString("d_area");
            v_subj       = dbox.getString("d_subj");
            v_subjseq    = dbox.getString("d_subjseq");
            v_year       = dbox.getString("d_year");
            v_lesson     = dbox.getString("d_lesson");

            String s_userid  = box.getSession("userid");
        }

        String full_toggle = "off";
        String sub1_toggle = "off";
        String sub2_toggle = "off";
        String sub3_toggle = "off";
        String sub4_toggle = "off";
        String sub5_toggle = "off";

        String sub_navi = "";
        String categoryName = "";

        if (v_categorycd.equals("C0")){
            sub1_toggle = "on";
            sub_navi = "문의";
            categoryName = "문의";
        }else if (v_categorycd.equals("B0")){
            sub2_toggle = "on";
            categoryName = "방송영상";
        } else if (v_categorycd.equals("G0")){
            sub3_toggle = "on";
            categoryName = "게임개발";
        } else if (v_categorycd.equals("K0")){
            sub4_toggle = "on";
            categoryName = "만애캐/음악&middot;공연";
        }else if (v_categorycd.equals("MINE")){
            sub5_toggle = "on";
            categoryName = "내가 작성한 글";
        } else {
            full_toggle = "on";
            categoryName = "전체";
        }

        String sFileExt ="";
        String imgExt ="";

    //box.put("title_str", "글수정 - " + categoryName + " - 커뮤니티 - 온라인교육");
    box.put("title_str", "글수정 - " + categoryName + " - 개인메뉴");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script language="JavaScript" type="text/JavaScript">
<!--

//변경된 내용을 수정
function update() {
    var frm = document.form1;



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
/*
    if (frm.p_categorycd.options[frm.p_categorycd.selectedIndex].value == "") {
        alert("분류를 선택하세요!");
        frm.p_categorycd.focus();
        return;
    }
*/
    //document.getElementById("txtfield4").value = CrossEditor.GetBodyValue();

    frm.p_searchtext.value    = "";
    frm.p_select.value        = "";
    frm.p_selCategory.value   = "";
    frm.p_pageno.value        =  1;

    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "update";
    document.form1.p_area.value = "MINE";
    document.form1.submit();

}

//리스트 화면으로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
     document.form1.p_process.value = "ListPage";
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

//글자 사이즈 체크
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

//파일첨부에 쓰기 방지
function File_CancelKeyInput()
{
    if(event.keyCode == 9){ // Tab key만 허용
        return true;
    }else{
        alert('[찾아보기] 버튼을 이용해 첨부하실 파일을 선택하세요.\n\n경로를 수동으로 잘못 입력하실 경우 업로드가 제대로 이루어지지 않습니다.');
        return false;
    }
}


// 카테고리 선택
function openCategory(){
    window.self.name = "winSelectList";
    open_window("openwinSelectList","","100","100","390","400","","","","yes","");
    document.form1.target = "openwinSelectList";
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "OpenCategoryPage";
    document.form1.submit();
    document.form1.target = window.self.name;

}
//-->
</script>
<!-- center start -->
<!-- container-new 시작 //-->
<div id="container-new">

    <!-- 쓰기 폼 시작 -->
    <form name="form1" enctype = "multipart/form-data" method = "post" action="">
        <input type="hidden" name="p_searchtext"  value="">
        <input type="hidden" name="p_select"      value="">
        <input type="hidden" name="p_selCategory" value="">
        <input type="hidden" name="p_pageno"      value="">
        <input type="hidden" name="p_catecd"      value="">
        <input type="hidden" name="p_process"     value="">
        <input type="hidden" name="p_userid"      value="">
        <input type="hidden" name="p_upfilecnt"   value="">
        <input type="hidden" name="p_tgubun"      value="<%=v_tgubun %>">
        <input type="hidden" name="p_tabseq"      value="<%=v_tabseq %>">
        <input type="hidden" name="p_seq"         value="<%=v_seq %>">
        <input type="hidden" name="p_types"       value="<%=v_types %>">
        <input type="hidden" name="p_subj"        value="<%=v_subj %>">
        <input type="hidden" name="p_subjseq"     value="<%=v_subjseq %>">
        <input type="hidden" name="p_year"        value="<%=v_year %>">
        <input type="hidden" name="p_lesson"      value="<%=v_lesson %>">
        <input type="hidden" name="p_area"        value="">

        <div id="mainMenu">
            <!-- 온라인 교육 -->
            <ul class="location">
                <li>개인메뉴</li>
                <li>정보관리 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a></li>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육</a></li>
                        <li><a href="#" class="active">내가작성한 글</a></li>
                        <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                    </ul>
                </li>
                <!-- 2015-01-12 개편 삭제
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
            <!-- 2015-01-12 개편삭제
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
                if(v_categorycd.equals("MINE")){
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
                <!-- 2015-01-19 개편삭제
                <ul class="tabCus">
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/2013/online_edu/community_tab_1_<%=full_toggle %>.gif" alt="전체" /></a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=B0"><img src="/images/2013/online_edu/community_tab_2_<%=sub2_toggle %>.gif" alt="방송영상" /></a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=G0"><img src="/images/2013/online_edu/community_tab_3_<%=sub3_toggle %>.gif" alt="게임개발" /></a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=K0"><img src="/images/2013/online_edu/community_tab_4_<%=sub4_toggle %>.gif" alt="문화콘텐츠" /></a></li>
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

                <div class="write1">
                    <dl>
                        <dt><label for="wr_curric">과정명</label></dt>
                        <dd>
                            <%=v_subjnm%>
                        </dd>
                        <dt><label for="wr_subject">제목</label></dt>
                        <dd><input type="text" name="p_title" id="wr_subject" style="width:635px;" value="<%=dbox.getString("d_title") %>" /></dd>
                        <dt class="blind"><label for="wr_cont">내용</label></dt>
                        <dd class="cont"><textarea name="content" cols="120" rows="15"><%=StringManager.replace(dbox.getString("d_contents"), "&", "&amp") %></textarea></dd>
                    </dl>
                </div>
                <!--
                <div class="btnArea">
                    <a href="javascript:update();"><img src="/images/2013/btn/btn_retouch.gif" alt="수정" /></a>
                    <a href="javascript:history.go(-1)"><img src="/images/2013/btn/btn_cancel.gif" alt="취소" /></a>
                </div>
                //-->

            </div>
            <!-- //#subCont -->
        </div>

    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>