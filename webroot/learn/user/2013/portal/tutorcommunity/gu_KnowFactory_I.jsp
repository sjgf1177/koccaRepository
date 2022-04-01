<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명 : gu_KnowBoard_I.jsp
//  3. 개      요: 지식공유 최근지식 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼:
//  6. 작      성: 이나연 06.01.03
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    box.put("topmenu","99");

    String v_process= box.getString("p_process");
    int    v_pageno     = box.getInt("p_pageno");

    String v_categorycd = box.getString("p_area");            // request 카테고리
    int v_seq = box.getInt("p_seq");
    String v_searchtext = box.getString("p_searchtext");
    int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    String content      = "";
    String width        = "650";
    String height       = "200";

    String s_userid     = box.getSession("userid");
    String s_name       = box.getSession("name");

    String full_toggle = "off";
    String sub1_toggle = "off";
    String sub2_toggle = "off";
    String sub3_toggle = "off";
    String sub4_toggle = "off";
    String sub5_toggle = "off";

    String sub_navi = "";

    if (v_categorycd.equals("C0")){
        sub1_toggle = "on";
        sub_navi = "문의";
    }else if (v_categorycd.equals("B0")){
        sub2_toggle = "on";
        sub_navi = "방송영상";
    } else if (v_categorycd.equals("G0")){
        sub3_toggle = "on";
        sub_navi = "게임개발";
    } else if (v_categorycd.equals("K0")){
        sub4_toggle = "on";
        sub_navi = "문화콘텐츠";
    }else if (v_categorycd.equals("MINE")){
        sub5_toggle = "on";
        sub_navi = "내가작성한글";
    } else {
        full_toggle = "on";
        sub_navi = "종합";
    }

    //    box.put("title_str", "글작성 - 커뮤니티 - 온라인교육");
    box.put("title_str", "글작성 - "+sub_navi+" - 개인메뉴");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script language="JavaScript" type="text/JavaScript">
<!--
 //리스트화면으로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.submit();
}

/* 2015-01-09 개편
*  내가 작성한 글 리스트화면으로 이동
*/
function fnselectMyList() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.p_area.value = "MINE";
    document.form1.submit();
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.submit();
}

//선택 조회
function whenSelection(p_action) {

    //var v_grcode, v_subj, v_upperclass, v_middleclass, v_lowerclass;
    //v_grcode      = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    //v_subj        = document.form1.s_subjcourse.value;
    document.form1.p_catecd.value  = document.form1.s_subjcourse.value;
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

 //데이터를 입력한다.
function insert() {
    var frm = document.form1;

    if (frm.p_tgubun.value == "SEL"){
        alert("말머리를 선택하세요.");
        frm.p_tgubun.focus();
        return;
    }else if(frm.p_tgubun.value == "C"){
        if(frm.p_category.value == "SEL"){
            alert("분야를 선택하세요.");
            frm.p_category.focus();
            return;
        }
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

    //document.getElementById("txtfield4").value = CrossEditor.GetBodyValue();

    frm.p_searchtext.value    = "";
    frm.p_pageno.value        =  1;

    var subjinfo = frm.p_subjinfo.value;

    frm.p_subj.value = subjinfo.substr(0,7);
    frm.p_subjseq.value = subjinfo.substr(7,4);
    frm.p_year.value = subjinfo.substr(11,4);
    frm.p_lesson.value = "000";

    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "insert";
    document.form1.p_area.value = "MINE";
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

//글자수를 체크
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
function display_showhide(el_id, showhide){
    if(showhide == "S"){
        $("#"+el_id).show();
    }else{
        $("#"+el_id).hide();
    }
}
function change_tgubun(val){
    if(val == "R"){
        display_showhide("tr_category","S");
        display_showhide("tr_course","H");
    }else{
        display_showhide("tr_course","S");
        display_showhide("tr_category","H");
    }
}
//-->
</script>
<!-- container-new 시작 //-->
<div id="container-new">

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>개인메뉴</li>
            <li>내가작성한 글 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a></li>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" class="active">내가작성한 글</a></li>
                    <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                </ul>
            </li>
            
            <!-- 2015-01-09 개편삭제
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
        -->
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
    <form name="form1" enctype = "multipart/form-data" method = "post" action="">
        <input type="hidden" name="p_seq"           value="<%=v_seq %>">
        <input type="hidden" name="p_searchtext"    value="<%=v_searchtext %>">
        <input type="hidden" name="p_pageno"        value="<%=v_pageno %>">
        <input type="hidden" name="p_process"       value="">
        <input type="hidden" name="p_subj"          value="">
        <input type="hidden" name="p_subjseq"       value="">
        <input type="hidden" name="p_year"          value="">
        <input type="hidden" name="p_lesson"        value="">
        <input type="hidden" name="p_area"        value="">

            <h2 class="subTit">
<%
            if(v_categorycd.equals("MINE")){
%>
                내가 작성한 글<!--<img src="/images/2013/online_edu/myCommunity_title.gif" alt="내가 작성한글" />//--></h2>
<%
            }else {
%>            
                커뮤니티<!--<img src="/images/2013/online_edu/community_title.gif" alt="커뮤니티" />//--></h2>
<%
            }
%>
            <div id="subCont">

                <!-- 2015-01-09 개편삭제
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
                        <dt><label for="wr_header">말머리</label></dt>
                        <dd>
                            <select name="p_tgubun" onchange="javacript:change_tgubun(this.value);" id="wr_header">
                                <option value="SEL">선택</option>
                                <option value="Q">질문</option>
                                <option value="I">정보</option>
                                <option value="T">토론</option>
                                <!--option value="R">문의</option>
                                <option value="C">자유</option>
                                <option value="D">자료</option-->
                            </select>
                        </dd>
                        <dt><label for="wr_curric">과정명</label></dt>
                        <dd>
                            <%= SelectSubjBean.getSubjInfo(box,v_categorycd)%>
                        </dd>
                        <dt><label for="wr_subject">제목</label></dt>
                        <dd><input type="text" name="p_title" id="wr_subject" style="width:635px;" /></dd>
                        <dt class="blind"><label for="wr_cont">내용</label></dt>
                        <dd class="cont"><textarea name="content" cols="120" rows="15"></textarea></dd>
<!--                        첨부파일 추후 개발-->
                        <!--dt><label for="wr_file">파일첨부</label></dt>
                        <dd><input type="file" name="" id="wr_file" style="width:455px;" /></dd>
                        <dt><label for="wr_thumb">썸네일등록</label></dt>
                        <dd>
                            <input type="file" name="" id="wr_thumb" style="width:455px;" />
                            <span class="memo">300kbyte 이하 (가로200, 세로130 pixel)</span>
                        </dd-->
                    </dl>
                </div>
                <!-- //write1 -->
                <!--
                <div class="btnArea">
                    <a href="javascript:insert();"><img src="/images/2013/btn/btn_regist.gif" alt="등록" /></a>
                    <a href="javascript:fnselectMyList();"><img src="/images/2013/btn/btn_cancel.gif" alt="취소" /></a>
                </div>
                //-->

            </div>
            <!-- //#subCont -->

    </form>
    </div>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>