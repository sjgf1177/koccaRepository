<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.credu.research.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.homepage.*" %>
<%@ page import="com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_title = "", v_contents = "", v_inuserid = "", v_indate = "" , v_types = "";
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;

    int v_seq = box.getInt("p_seq");
    int v_upfilecnt = box.getInt("p_upfilecnt"); // 서버에 저장되있는 파일수
    int v_pageno = box.getInt("p_pageno");

    String v_searchtext = box.getString("p_searchtext"); // 서버에 저장되있는 파일수
    String v_select = box.getString("p_select");
    String v_tabseq = box.getString("p_tabseq");
    String v_tgubun = box.getString("p_tgubun");
    String v_subj = box.getString("p_subj");
    String v_subjseq = box.getString("p_subjseq");
    String v_year = box.getString("p_year");
    String v_lesson = box.getString("p_lesson");

    String v_name = "";
    String v_repstatus  = "";
    String v_subjnm = "";

    DataBox dbox = (DataBox)request.getAttribute("SelectView");

    int v_commentseq = 0;

    v_inuserid = dbox.getString("d_inuserid");
    v_name = dbox.getString("d_name");
    v_types = dbox.getString("d_types");
    v_indate = dbox.getString("d_indate");
    v_title = dbox.getString("d_title");
    v_subjnm = dbox.getString("d_subjnm");
    v_seq = dbox.getInt("d_seq");

    if (v_tgubun.equals("N")) {
        v_contents = dbox.getString("d_contents");
    } else {
        v_contents = dbox.getString("d_contents").replace("\r", "<BR>");  // 보여주는 부분 엔터
    }
    v_realfileVector = (Vector)dbox.getObject("d_realfile");
    v_savefileVector = (Vector)dbox.getObject("d_savefile");
    String v_categorynm = dbox.getString("d_categorynm");
    int v_recommend = dbox.getInt("d_recommend");
    int v_cnt = dbox.getInt("d_cnt");
    v_repstatus = dbox.getString("d_okyn1");

    String v_categorycd= box.getString("p_area");
    String s_userid = box.getSession("userid");
    String s_gender = box.getSession("gender");

    int v_filecount = v_realfileVector.size();

    String p_categorycd = box.getString("p_categorycd"); // request 카테고리

    String v_process= box.getString("p_process");

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



    String v_tgubunnm = "";
    if(v_tgubun.equals("T")){v_tgubunnm = "<img src=\"/images/2013/common/ico_discussion.gif\" alt=\"토론\" />";}
    else if(v_tgubun.equals("Q")){v_tgubunnm = "<img src=\"/images/2013/common/ico_question.gif\" alt=\"질문\" />";}
    else if(v_tgubun.equals("C")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_c.gif\" alt=\"자유\" />";}
    else if(v_tgubun.equals("D")){v_tgubunnm = "<img src=\"/images/2013/common/ico_pds.gif\" alt=\"자료\" />";}
    else if(v_tgubun.equals("I")){v_tgubunnm = "<img src=\"/images/2013/common/ico_info.gif\" alt=\"정보\" />";}
    else if(v_tgubun.equals("R")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_r.gif\" alt=\"문의\" />";}
    else if(v_tgubun.equals("N")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_n.gif\" alt=\"공지\" />";}

    // box.put("title_str", v_title + " - " + categoryName + " - 커뮤니티 - 온라인교육");
    box.put("title_str", v_title + " - " + categoryName + " - 개인메뉴");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script language="JavaScript" type="text/JavaScript">
//리스트페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_area.value="<%=v_categorycd%>";
    document.form1.p_process.value="ListPage";
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

//수정페이지로 이동
function updatePage() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value="updatePage";
    document.form1.submit();
}

//답변 등록 페이지로 이동
function replyPage() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value="replyPage";
    document.form1.submit();
}

//꼬릿말 등록
function insertcomment() {
    if(document.form2.commentqna.value==""){
        alert("꼬릿말을 등록하세요");
        return;
    }
    var r=calculate_msglen($("#commentqna").val());
    if(r>200){
        alert("현재 댓글은 = "+ r + "Byte 입니다.\n댓글등록은 200Byte 이하로 입력해 주시기 바랍니다.");
        $("#commentqna").focus();
        return;
    }
    document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
    document.form2.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form2.p_process.value="commentInsert";
    document.form2.submit();
}

//뷰 화면 삭제
function deleteAction() {
    if (confirm("정말로 삭제하시겠습니까?")) {
        document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
        document.form1.p_process.value="delete";
        document.form1.submit();
    }
    else {
        return;
    }
}

//꼬릿말 삭제
function deleteComment(v_commentseq) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
        document.form2.p_commentseq.value = v_commentseq;
        document.form2.action = "/servlet/controller.homepage.KnowBoardUserServlet";
        document.form2.p_process.value="deleteComment";
        document.form2.submit();
    }
    else {
        return;
    }
}

//댓글 수정 화면 전환
function updateComment(v_commentseq,v_commentqna,v_indate){
    var str = "";
        str += "<input id=\"p_commentqna\" style=\"width:98%;\" class=\"write\" value=\""+v_commentqna+"\">";
    $("#commentseq_"+v_commentseq).empty();
    $("#commentseq_"+v_commentseq).append(str);
    $("[id^='up_del_btn_']").hide();
    $("#up_can_btn_"+v_commentseq).show();
}

//댓글 수정 화면 취소
function cancelComment(v_commentseq,v_commentqna,v_indate){
    var str = "";
        str += v_commentqna;
    $("#commentseq_"+v_commentseq).empty();
    $("#commentseq_"+v_commentseq).append(str);
    $("[id^='up_del_btn_']").show();
    $("#up_can_btn_"+v_commentseq).hide();
}

//댓글 수정 적용
function appupdateComment(v_commentseq){
    if (confirm("정말로 수정하시겠습니까?")){
        document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
        document.form1.p_commentseq.value = v_commentseq;
        document.form1.p_commentqna.value = $("#p_commentqna").val();
        document.form1.p_process.value="updateComment";
        document.form1.submit();
    }else{
        return;
    }
}


// 추천하기
function InsertRecommend(seq) {
//alert(document.form1.p_categorycd.value);
    if (confirm("추천하시겠습니까?")) {
        document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
        document.form1.p_process.value="insertRecommend";
        document.form1.submit();
    }
    else {
        return;
    }
}

function textareaLength(hwnd,target){
    var h=$("[name='"+hwnd+"']").val();
    var r=calculate_msglen(h);
    $("#"+target).text(r);
    if(r>200)
        $("#"+target).css("color","#FF0000");
    else
        $("#"+target).css("color","#000000");
}

function calculate_msglen(msg){
    var nbytes = 0;

    for (i=0; i<msg.length; i++) {
        var ch = msg.charAt(i);
        if(escape(ch).length > 4) {
        nbytes += 2;
        } else if (ch == '\n') {
        if (msg.charAt(i-1) != '\r') {
        nbytes += 1;
        }
        } else if (ch == '<' || ch == '>') {
        nbytes += 4;
        } else {
        nbytes += 1;
        }
    }
 return nbytes;
}
</script>
<!-- container-new 시작 //-->
<div id="container-new">

    <form name="form1" method = "post" action="">
            <input type="hidden" name="p_searchtext"  value="<%=v_searchtext %>">
            <input type="hidden" name="p_select"      value="<%=v_select %>">
            <input type="hidden" name="p_pageno"      value="<%=v_pageno %>">
            <input type="hidden" name="p_process"     value="">
            <input type="hidden" name="p_userid"      value="<%=v_inuserid %>">
            <input type="hidden" name="p_categorycd"  value="<%=p_categorycd%>">
            <input type="hidden" name="p_upfilecnt"   value="<%=v_upfilecnt %>">
            <input type="hidden" name="p_area"        value="<%=v_categorycd%>"/>
            <input type="hidden" name="p_commentseq"  value="" />
            <input type="hidden" name="p_commentqna"  value="" />

            <input type="hidden" name="p_tgubun"    value="<%=v_tgubun %>"/>
            <input type="hidden" name="p_tabseq"    value="<%=v_tabseq %>"/>
            <input type="hidden" name="p_seq"       value="<%=v_seq %>"/>
            <input type="hidden" name="p_types"     value="<%=v_types %>"/>
            <input type="hidden" name="p_subj"      value="<%=v_subj %>"/>
            <input type="hidden" name="p_subjseq"   value="<%=v_subjseq %>"/>
            <input type="hidden" name="p_year"      value="<%=v_year %>"/>
            <input type="hidden" name="p_lesson"    value="<%=v_lesson %>"/>
    </form>
    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>개인메뉴</li>
            <li>내가 작성한 글 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a></li>
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" class="active">내가 작성한 글</a></li>
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
                <!-- 2015-01-19 개편삭제
                <ul class="tabCus">
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/2013/online_edu/community_tab_1_<%=full_toggle %>.gif" alt="전체" /></a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=B0"><img src="/images/2013/online_edu/community_tab_2_<%=sub2_toggle %>.gif" alt="방송영상" /></a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=G0"><img src="/images/2013/online_edu/community_tab_3_<%=sub3_toggle %>.gif" alt="게임개발" /></a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=K0"><img src="/images/2013/online_edu/community_tab_4_<%=sub4_toggle %>.gif" alt="문화콘텐츠" /></a></li>
                    <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list"><img src="/images/2013/online_edu/community_tab_6_off.gif" alt="cmu 국비지원" /></a></li>
<%
        if ( !topWarp_userid.equals("") ) {
%>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE"><img src="/images/2013/online_edu/community_tab_5_<%=sub5_toggle %>.gif" alt="내가 작성한 글" /></a></li>
<%
        }
%>
                </ul>
                -->
                <h3 class="blind">전체 현재위치</h3>

                <div class="article">
                    <div class="info">
                        <p class="title"><%=v_tgubunnm %> <%=v_title%></p>
                        <p class="date"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></p>
                        <p class="curriculum"><%=v_subjnm%></p>
                    </div>
                    <div class="info2">
                        <p class="name"><%=v_name%></p>
<%
        if( v_realfileVector != null ) {
            for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

                String v_realfile = (String)v_realfileVector.elementAt(i);
                String v_savefile = (String)v_savefileVector.elementAt(i);

                if(v_realfile != null && !v_realfile.equals("")) {


                    if(v_realfile != null && !v_realfile.equals("")) {
                        sFileExt =  v_realfile.substring(v_realfile.lastIndexOf(".")+1);
                        if (sFileExt.equals("hwp")) {
                            imgExt = "<img src='/images/2012/common/icon/icon_hwp.gif' id='exticon' align='absmiddle' alt='한글문서'>";
                        } else if (sFileExt.equals("doc") || sFileExt.equals("docx")) {
                            imgExt = "<img src='/images/2012/common/icon/icon_doc.gif' id='exticon' align='absmiddle' alt='워드문서'>";
                        } else if (sFileExt.equals("ppt")||sFileExt.equals("pptx")) {
                            imgExt = "<img src='/images/2012/common/icon/icon_ppt.gif' id='exticon' align='absmiddle' alt='프레젠테이션'>";
                        } else if (sFileExt.equals("xls") ||sFileExt.equals("xlsx") ) {
                            imgExt = "<img src='/images/2012/common/icon/icon_xls.gif' id='exticon' align='absmiddle' alt='엑셀'>";
                        } else if (sFileExt.equals("pdf")) {
                            imgExt = "<img src='/images/2012/common/icon/icon_pdf.gif' id='exticon' align='absmiddle' alt='PDF'>";
                        } else {
                            imgExt = "<img src='/images/2012/common/icon/icon_idk.gif' id='exticon' align='absmiddle' alt='알 수 없는 파일형식'>";
                        }
%>
                        <p class="file"><a href="/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>"><%= v_realfile%></a><input type="hidden" name="p_savefile"  value=<%=v_savefile%> /></p>
<% 
                    }
                }
            }
        }
%>
                    </div>

                    <div class="inner">
                        <%=StringManager.removeHTMLHead(v_contents)%>
                    </div>

                    <div class="reply1">
<% 
                        ArrayList list = (ArrayList)request.getAttribute("selectCommentList"); %>
                        <p class="total"><a href="#">덧글 <%=list.size() %>개</a> | 조회수 <%=v_cnt %></p>
                        <ul>
                            <!-- 댓글 반본 구간 시작 -->
                            <%
                            //if(list.size() != 0){
                            String s_gadmin = box.getSession("gadmin");
                            String commentInuserid = "";
                            for(int i = 0; i < list.size(); i++) {
                                dbox = (DataBox)list.get(i);
                                // v_inuserId      = dbox.getString("d_inuserid");
                                commentInuserid = dbox.getString("d_inuserid");
                                String c_name = dbox.getString("d_name");
                                v_indate = dbox.getString("d_cdate");
                                String v_commentqna = dbox.getString("d_commentqna");
                                v_commentseq = dbox.getInt("d_commentseq");
                            %>
                            <li>
                                <div class="replInfo">
                                    <p class="name"><%=c_name %></p>
                                    <p class="date"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd hh:mm") %></p>
<!--                                    <p class="replBtn"><a href="#">답글</a></p>-->
                                </div>
                                <p class="con" id="commentseq_<%=v_commentseq%>">
                                    <%=v_commentqna%>
                                </p>
<%
        if(s_userid.equals(commentInuserid)) {
%>
                                <ul class="func" id="up_del_btn_<%=v_commentseq%>">
                                    <li><a href="javascript:updateComment(<%=v_commentseq%>,'<%=v_commentqna%>','<%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%>')">수정</a></li>
                                    <li><a href="javascript:deleteComment(<%=v_commentseq%>)">삭제</a></li>
                                </ul>
                                <ul class="func" id="up_can_btn_<%=v_commentseq%>" style="display: none;">
                                    <li><a href="javascript:appupdateComment(<%=v_commentseq%>);">적용</a></li>
                                    <li><a href="javascript:cancelComment(<%=v_commentseq%>,'<%=v_commentqna%>','<%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%>');">취소</a></li>
                                </ul>
<%
        }
%>
                            </li>
<%
    }
%>
                            <!-- form -->
                            <li>
                            <div>
                                <form name="form2" method = "post" action="">
                                    <input type="hidden" name="p_tabseq" value="<%= v_tabseq %>">
                                    <input type="hidden" name="p_seq" value="<%= v_seq %>">
                                    <input type="hidden" name="p_searchtext" value="<%= v_searchtext %>">
                                    <input type="hidden" name="p_select" value="<%= v_select %>">
                                    <input type="hidden" name="p_pageno" value="<%= v_pageno %>">
                                    <input type="hidden" name="p_process" value="">
                                    <input type="hidden" name="p_userid" value="<%= commentInuserid %>">
                                    <input type="hidden" name="p_types" value="<%=v_types%>">
                                    <input type="hidden" name="p_commentseq" value ="">
                                    <input type="hidden" name="p_categorycd" value ="">
                                    <input type="hidden" name="p_gender" value="<%=s_gender %>">
                                    <input type="hidden" name="p_tgubun" value="<%=v_tgubun %>"/>

                                    <input type="hidden" name="p_types" value="<%=v_types %>"/>
                                    <input type="hidden" name="p_subj" value="<%=v_subj %>"/>
                                    <input type="hidden" name="p_subjseq" value="<%=v_subjseq %>"/>
                                    <input type="hidden" name="p_year" value="<%=v_year %>"/>
                                    <input type="hidden" name="p_lesson" value="<%=v_lesson %>"/>
                                <fieldset>
                                    <legend>덧글입력</legend>
                                    <textarea id="commentqna" name="commentqna" cols="102" rows="5" title="최대 200Byte까지 작성 가능합니다."></textarea>
                                    <a href="javascript:insertcomment();"><img src="/images/2013/btn/btn_comment.gif" alt="덧글입력" /></a>
                                </fieldset>
                                </form>
                            </div>
                            </li>
                            <!-- //form -->
                        </ul>
                    </div>
                    <!-- //reply1 -->
                </div>
                <!-- //article -->



                <% ArrayList alist = (ArrayList)request.getAttribute("selectAns"); %>
                <p class="answer"><a style="font-size: 12px;">답변 <%=alist.size() %>개</a></p>

                <div class="article">
<%
                    if(alist.size() > 0){
                        for(int i = 0; i < alist.size(); i++) {
                            DataBox Ansbox = (DataBox)alist.get(i);

                            String v_anstitle      = "";
                            String v_anscontents   = "";
                            String v_ansinuserid   = "";
                            String v_ansindate     = "";
                            String v_ansname       = "";
                            String v_anscategorynm = "";

                            v_ansinuserid = Ansbox.getString("d_inuserid");
                            v_ansname = Ansbox.getString("d_name");
                            v_ansindate = Ansbox.getString("d_indate");
                            v_anstitle = Ansbox.getString("d_title");
                            v_anscontents = Ansbox.getString("d_contents");
                            v_anscategorynm = Ansbox.getString("d_categorynm");
%>
                    <div class="info3">
                        <p class="name"><%=v_ansname %></p>
                        <p class="date"><%=FormatDate.getFormatDate(v_ansindate, "yyyy.MM.dd HH:mm")%></p>
                    </div>
                    <div class="inner2">
                        <%=StringManager.removeHTMLHead(v_anscontents)%>
                    </div>
<%
                        }
                    }
%>
                </div>
                <!-- //article -->


                <!--table class="bbsNavi">
                    <colgroup>
                        <col width="70">
                        <col>
                        <col width="70">
                        <col width="70">
                    </colgroup>
                    <tr>
                        <td class="pre">이전글</td>
                        <td><img src="/images/2013/common/ico_pds.gif" alt="자료" /> <a href="community_view.html">제로웹, 사용자 중심 홈페이지 리뉴얼</a></td>
                        <td>작성자</td>
                        <td>2013.06.18</td>
                    </tr>
                    <tr>
                        <td class="next">다음글</td>
                        <td><img src="/images/2013/common/ico_pds.gif" alt="자료" /> <a href="community_view.html">안산교육형, ‘재미있는 수업이야기’ 초등교사들 수업역량 강화... ‘호응’</a></td>
                        <td>홍길동</td>
                        <td>2013.06.18</td>
                    </tr>
                </table-->

                <div class="btnArea right">
<%
    if(s_userid.equals(v_inuserid)) {
%>
                    <!--
                    <a href="javascript:updatePage()"><img src="/images/2013/btn/btn_retouch.gif" alt="수정" /></a>
                    <a href="javascript:deleteAction()"><img src="/images/2013/btn/btn_delete.gif" alt="삭제" /></a>
                    //-->
<%
    }
%>

                    <a href="javascript:selectList();"><img src="/images/2013/btn/btn_list.gif" alt="목록" /></a>
                </div>
            </div>
            <!-- //#subCont -->

    </div>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>