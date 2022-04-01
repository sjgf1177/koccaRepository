<%
//**********************************************************
//  1. 제     목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개     요: 과정조회
//  4. 환     경: JDK 1.3
//  5. 버     젼: 1.0
//  6. 작     성: 2004.01.14
//  7. 수     정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String type = box.get("type");
    String v_area = box.get("p_area");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");


    String sub_title_img ="";  // 서브이미지
    String sub_title_name =""; // 서브 이미지 설명
    String sub_title_navi =""; // 서브 이미지 설명

    String  v_today        =  FormatDate.getDate("yyyyMMddHH");

     if (type.equals("S01")) {
        sub_title_img = "/images/2012/sub/page3/title/offline_cast.gif";
        sub_title_name = "오프라인과정 방송영상 분야";
        sub_title_navi = "방송영상";
    } else if (type.equals("S02")) {
        sub_title_img = "/images/2012/sub/page3/title/offline_making.gif";
        sub_title_name = "오프라인과정 게임개발 분야";
        sub_title_navi = "게임제작";
    } else if (type.equals("S03")) {
        sub_title_img = "/images/2012/sub/page3/title/offline_plan.gif";
        sub_title_name = "오프라인과정 기획창작 분야";
        sub_title_navi = "기획창작";
    } else {
        sub_title_img = "/images/2012/sub/page3/title/offline_total.gif";
        sub_title_name = "오프라인과정 방송영상 분야";
        sub_title_navi = "전체";
    }

    //box.put("submenu","1");


    String p_specials = box.get("p_specials");
    String p_action = box.get("p_action");

    String p_menuid = box.get("menuid"); //방송 01,게임 02,기획 03
    String p_middleclass = box.get("s_mclassnm");  //산업계직무재교육 001,차세대인재양성교육 002, 수탁교육 003
    if (box == null) box = RequestManager.getBox(request);
    List<DataBox> subjList = (List<DataBox>)request.getAttribute("SubjectList");         // 과정 리스트
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pagesize = box.getInt("p_pagesize");
    int  v_totalrowcount =  0;

    String categoryName = "";
    String sub1_toggle = "off";
    String sub2_toggle = "off";
    String sub3_toggle = "off";
    String sub4_toggle = "off";

    if ("001".equals(v_area)) {
        sub2_toggle = "on";
        categoryName = "현업인 직무 재교육";
    } else if ("002".equals(v_area)) {
        sub3_toggle = "on";
        categoryName = "차세대 인재양성교육";
    } else if ("003".equals(v_area)) {
        sub4_toggle = "on";
        categoryName = "";
    } else if ("004".equals(v_area)) {
        sub1_toggle = "on";
        categoryName = "3D 입체콘텐츠 전문인력양성";
    }

    // box.put("title_str", categoryName + "-과정신청, 현장교육");
    box.put("title_str", categoryName + " - 교육신청");// 2015-03-06 "현업인 직무 재교육" 이름변경
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<!-- 스크립트영역 -->
<script language="JavaScript">
<!--//
// 과정검색 엔터처리
function search_enter(e)  {
    if (e.keyCode =='13'){  subjSearch();  }
}

// 과정검색
function subjSearch() {
    document.form1.p_process.value = "SubjectList";
    document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj, year, subjseq, seq){
    var subjForm = document.form1;
    subjForm.p_subj.value = subj;
    subjForm.p_year.value = year;
    subjForm.p_subjseq.value = subjseq;
    subjForm.p_seq.value = seq;
    subjForm.p_process.value = 'SubjectPreviewPage';
    subjForm.p_rprocess.value = 'SubjectList';
    subjForm.action='/servlet/controller.off.ProposeOffServlet';
    subjForm.method = "get";
    subjForm.target = "_self";
    subjForm.submit();
}

//전체과정보기
function whenSubjectListAll() {
document.form1.p_process.value = 'SubjectListAll';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.off.ProposeOffServlet';
document.form1.submit();
}
//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, seq) {
    if(<%= box.getSession("userid").length()==0 %>) {
        alert("로그인이 필요합니다.");
        var retURL = document.location.href;
        retURL = retURL.substr( retURL.indexOf("//") + 2, retURL.length);
        retURL = retURL.substr( retURL.indexOf("/") , retURL.length);
        location.href = "/learn/user/2013/portal/homepage/zu_MainLogin.jsp?p_frmURL=" + retURL;
        return;
    }
    if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
     return;
    }
    var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
    document.form1.p_process.value = "SubjectEduProposePage";
    document.form1.target = "proposeWindow";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_seq.value = seq;
    document.form1.action = "/servlet/controller.off.ProposeOffServlet";
    document.form1.submit();
    document.form1.target = "_self";
}

//관심버튼 클릭
function fnInterestInsert(subj,year,subjseq,courseyn,subjnm) {

    if (confirm(subjnm + " 과정을 관심과정으로 등록하시곘습니까?")) {
            $.post("/servlet/controller.common.AjaxServlet"
                    , {checkSqlNum:"interest.exist", sqlNum:"interest.insert", p_subj:subj,rerurnFunction:"resultInterest"}
                    , function(data) {
                        $("#ajaxDiv").html(data);
                    });
    }
}
function resultInterest(data) {
    if (data.result=='true') alert("관심과정으로 등록했습니다.");
    else alert("관심과정 등록이 실패하였습니다.\n"+data.result);
}
    //페이지 이동
    function go(index) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = index;
        document.form1.p_process.value = "SubjectList";
        document.form1.submit();
    }
    //페이지 이동
    function selectArea(area,mid) {
        //document.form1.type.value = area;
        document.form1.p_area.value = area;
        if( mid != '' ) {
            document.form1.menuid.value = mid;
        }
        //document.form1.s_mclassnm.value = "";
        document.form1.p_action.value = "go";
        document.form1.p_process.value = "SubjectList";
        document.form1.p_pageno.value = 0;
        document.form1.submit();
    }
    //페이지 이동
    function goPage(pageNum) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = pageNum;
        document.form1.p_process.value = "SubjectList";
        document.form1.submit();
    }
    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.p_process.value = "SubjectList";
        document.form1.submit();
    }
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

    <!-- form 시작 -->

    <form name="form1" action="/servlet/controller.off.ProposeOffServlet" method="post">
    <input type="hidden" name="type"    value="<%=type%>"/>
    <input type="hidden" name="p_upperclass"    value="<%=type%>"/>
    <input type="hidden" name="p_process"   value="<%=box.get("p_process")%>"/>
    <input type="hidden" name="p_rprocess"  value=""/>
    <input type="hidden" name="p_year"  value=""/>
    <input type="hidden" name="p_subj"  value=""/>
    <input type="hidden" name="p_subjseq"   value=""/>
    <input type="hidden" name="p_seq"   value=""/>
    <input type="hidden" name="gubun" value="2">
    <input type="hidden" name="p_area" id="p_area"  value="<%= box.get("p_area") %>"/>
    <input type="hidden" name="p_action"    value="<%=p_action%>"/>
    <input type="hidden" name="p_pageno"    value="<%=v_pageno%>"/>
    <input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
    <input type="hidden" name="menuid" value='<%= box.get("menuid") %>'>
    <input type="hidden" name="p_topuserid" value=""/>
    <input type="hidden" name="p_toppwd"    value=""/>

    <div id="mainMenu">
        <!-- 현장 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>현장교육</li>
            <li><%= v_area.equals("001") ? "교육신청" : "차세대 인재양성교육"%> <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <!-- 2015-03-06현업인 직무 재교육 이름 변경 /-->
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001" <%= v_area.equals("001") ? "class=\"active\"" : ""%>>교육신청</a></li>
                    <!-- 2015-02-23 삭제
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=002" <%= v_area.equals("002") ? "class=\"active\"" : ""%>>차세대 인재양성교육</a></li>
                    //-->
                    <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList">일정안내</a></li>
<!-- 2015-01-07 개편삭제
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001" class="active">과정신청</a></li>
-->
                    <!-- 2014-04-23 기존 3D과정이 미래창조부로 이관 완료됨으로 인하여 주석처리
                    <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=004" class="active">과정신청</a></li>
                    //-->
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
<!-- 2015-01-07 개편삭제
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001"><strong><u>과정신청</u></strong></a></li>
                <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList">일정안내</a></li>
            </ul>
        </div>
-->
    </div>


    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit"><%= v_area.equals("001") ? "교육신청" : "차세대 인재양성교육"%>
        <!--<img src="/images/2013/field_edu/annual_title<%= v_area.equals("001") ? "01" : "02"%>.gif" alt="<%= v_area.equals("001") ? "현업인 직무 재교육" : "차세대 인재양성교육"%>" />//-->
        </h2>

        <div id="subCont">
<!-- 2015-01-07 개편삭제
            <ul class="tabCus">
                <!--
                <li><a href="javascript:selectArea('004','');"><img src="/images/2013/field_edu/annual_tab_1_<%=sub1_toggle%>.gif" alt="3D 입체콘텐츠 전문인력양성" /></a></li>
                //-->
<!--
                <li><a href="javascript:selectArea('001','');"><img src="/images/2013/field_edu/annual_tab_2_<%=sub2_toggle%>.gif" alt="협업인 직무 재교육" /></a></li>
                <li><a href="javascript:selectArea('002','');"><img src="/images/2013/field_edu/annual_tab_3_<%=sub3_toggle%>.gif" alt="차세대 인재양성교육" /></a></li>
            </ul>

-->

            <h3 class="blind">교육신청 현재위치</h3>

            <!--2015-03-06 삭제
            <div class="noticBox">
                <strong>[3D 입체콘텐츠 과정]</strong><br />
                본 사업은 2014년 4월부로 한국콘텐츠진흥원에서 정보통신산업진흥원(<a href="http://uhd3d.nipa.kr" target="_blank" style="color:#0000ff;">uhd3d.nipa.kr</a>)으로 사업이 이관되었음을 알려드립니다. <br />
            </div>

            -->
        <!--  페이지 내 검색 -->
        <div class="search">
            <fieldset>
                <legend>통합 검색</legend>
                <select name="p_search" title="검색 분류">
                    <option value="NAME" <%=v_search.equals("NAME")?"selected":"" %>>과정명</option>
                </select>
                <input type="text" name="p_searchtext" value="<%=v_searchtext %>" title="검색어" class="keyword" onkeypress="search_enter(event);" />
                <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" onclick="javascript:subjSearch();" />
            </fieldset>
        </div>


            <div class="board2 space2">
                <table class="" summary="이 표는 현장교육 과정안내 표로 분야, 과정명, 교육비, 신청기간, 교육기간,상태 로 구성 되어 있습니다.">
                    <caption>과정안내 표</caption>
                    <colgroup>
                        <col width="*"/>
                        <col width="35%"/>
                        <col width="*"/>
                        <col width="*"/>
                        <col width="*"/>
                        <col width="*"/>
                    </colgroup>
                    <thead>
                        <tr>
                            <th>분야</th>
                            <th>과정명</th>
                            <th>교육비</th>
                            <th>신청기간</th>
                            <th>교육기간</th>
                            <th>상태</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                    int i = 0;
                    for(DataBox dbox : subjList) {
                    String biyong = "";
                    String slimit = "";
                    String propyn = dbox.getString("d_propyn");
                    if( dbox.getInt("d_biyong") == 0 ) {
                        biyong = "무료";
                    } else {
                        biyong = dbox.getCommaInt("d_biyong") + "원";
                    }
                    if( dbox.getInt("d_studentlimit") == 0 ) {
                        slimit = "제한없음";
                    } else {
                        slimit = dbox.getCommaInt("d_studentlimit") + "명";
                    }
                    %>
                    <tr>
                        <td class="first"><%=dbox.get("d_areaname") %></td>
                        <td class="subject"><a href="javascript:whenSubjInfo(
                                    '<%=dbox.get("d_subj")%>',
                                    '<%=dbox.get("d_year")%>',
                                    '<%=dbox.get("d_subjseq")%>',
                                    '<%=dbox.get("d_seq")%>')"><%=dbox.get("d_subjnm")%></a></td>
                        <td><%= biyong %></td>
                        <td><%=dbox.getDate("d_propstart", ".").substring(0,10)%>~<%=dbox.getDate("d_propend", ".").substring(0,10)%></td>
                        <td><%=dbox.getDate("d_edustart", ".").substring(0,10)%>~<%=dbox.getDate("d_eduend", ".").substring(0,10)%></td>
                        <td>
                        <%if(dbox.get("d_me_sukang_yn").equals("Y")) {%>신청완료<%  } else if( dbox.get("d_isprop").equals("Y") ) { %>
                            <a href="javascript:whenSubjPropose(
                                    '<%=dbox.get("d_subj")%>',
                                    '<%=dbox.get("d_year")%>',
                                    '<%=dbox.get("d_subjseq")%>',
                                    '<%=dbox.get("d_subjnm")%>',
                                    '<%=dbox.get("d_seq")%>')"><img src="/images/2013/field_edu/btn_apply.gif" alt="신청" /></a>
                        <% } else if (StringManager.toInt(dbox.get("d_propstart")) >= StringManager.toInt(v_today)){ %>
                            <a href="javascript:alert('수강신청 기간이 아닙니다.');"><img src="/images/2013/field_edu/btn_standby.gif" alt="대기" /></a>
                        <% } else { %>
                            <img src="/images/2013/field_edu/btn_ending.gif" alt="마감" />
                        <% } i++; %>
                        </td>
                    </tr>
                    <%
                        v_totalpage  = dbox.getInt("d_totalpage");// for 루프 안에 삽입
                        v_totalrowcount = dbox.getInt("d_totalrowcount");
                        v_rowcount    = dbox.getInt("d_rowcount");
                    }
                    if( subjList.size() < 1) {
                    %>
                        <tr><td colspan="6" class="first">등록된 과정이 없습니다.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <!-- //tableWrap -->

            <div class="paging">
                <%= PageUtil.re2013_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
            </div>
            <!-- //paging -->

        </div>
        <!-- //#subCont -->
    </div>
    <!-- 컨텐츠 영역 끝 -->
    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>