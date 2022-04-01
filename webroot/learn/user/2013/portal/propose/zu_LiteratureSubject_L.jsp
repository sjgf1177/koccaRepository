<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.course.*" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet"  scope="page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    //페이징관련

    String v_process = box.getString("p_process");

    // 검색
    String v_searchtext = box.getString("p_searchtext");
    String v_search = box.getString("p_search");

    int pageNo = box.getInt("p_pageno");
    int pageSize = box.getInt("p_pagesize");
    int totalPage = 0;
    int totalRowCount = 0;

    int dispNum = 0;

    pageNo = pageNo == 0 ? 1 : pageNo;
    pageSize = pageSize == 0 ? 10 : pageSize;

    ArrayList literatureSubjectList = (ArrayList)request.getAttribute("literatureSubjectList");  // 인문학 강좌 리스트
    DataBox dbox = null;

    dbox = (DataBox)literatureSubjectList.get(0);

    box.put("title_str", "휴넷 인문학 - 온라인교육");
    
  	//학습창 오픈관련 변수
    int ieduurl = 0;
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- 스크립트영역 -->
<script type="text/javascript">

    $(function(){
        var len = $(".movList2 .btns").length;
        var lyrAll = $(".mvLayer");
        $(".movList2 .btns").each(function(i){
            var that = $(this);
            //ie7 zindex 버그
            $(this).css("zIndex",len-i);

            //차시보기 레이어
            $(this).find(".chart1").click(function(e){
                e.preventDefault();
                e.stopPropagation();
                var lyr = that.find(".mvLayer");
                var id = that.find(".mvLayer").attr('id');
                if(lyr.is(":hidden")){
                    $.post("/servlet/controller.common.TagServlet",{sqlNum:"subjchasi", type:10, p_subj:id}
                    , function(data) {
                        $("#"+id).empty();
                        $("#"+id).append(data);
                    });
                    lyrAll.hide();
                    lyr.show();
                }else{
                    lyrAll.hide();
                    lyr.hide();
                }
            });
        });
        $(document).click(function(){
            lyrAll.hide();
        });
        $(lyrAll).click(function(e){
            e.stopPropagation();
        });
    });

    function lyr_close(){
        $(".mvLayer").hide();
    }

    // 과정검색 엔터처리
    function search_enter(e) {
        if (e.keyCode == "13") {
            subjSearch("", "", "");
        }
    }

    // 과정검색
    function subjSearch(arg1, arg2, arg3) {
        document.form1.p_pageno.value = "1";
        document.form1.p_process.value = "LiteratureSubjectList";
        document.form1.submit();
    }

    /**
     * 과정 상세 정보 화면으로 이동한다.
     */
    function fnViewSubjectInfo(subj, subjnm, courseyn, upperclass, upperclassnm, year, subjseq){
        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "?p_process=SubjectPreviewPage&p_rprocess=LiteratureSubjectList&p_subj=" + subj + "&p_year=" + year + "&p_subjseq=" + subjseq;

        location.href = url + param;
    }

    //맛보기
    function whenPreShow(url, subj, d_preheight, d_prewidth) {
        // 로그
        prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
        open_window("openShow",url,"100","100",d_preheight,d_prewidth,"","","",true,true);
    }

    /**
     * 수강신청
     */
    function fnApplySubjPropose(subj, year, subjseq, subjnm) {

    	if(<%= box.getSession("userid").length()==0 %>) {
            var frmURL = location.href;
            frmURL = frmURL.replace("http://", "");
            var idx = frmURL.indexOf("/");

            frmURL = frmURL.substr(idx, frmURL.length);

            alert("로그인 후  이용해주세요.");
            location.href = "/learn/user/2013/portal/homepage/zu_MainLogin.jsp?p_frmURL=" + encodeURIComponent( frmURL );
            return;
        }
        
        if(!confirm(subjnm+"과정을 학습 하시겠습니까?")){
         return;
        } else {
        	var new_Open = window.open("", "proposeWindow", "scrollbars=yes,width=10,height=10,resizable=no");
            document.form1.p_process.value = "SubjectEduPropose";
            document.form1.target = "proposeWindow";
            document.form1.p_subj.value = subj;
            document.form1.p_year.value = year;
            document.form1.p_subjseq.value = subjseq;
            document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
            document.form1.submit();
            document.form1.target = "_self";
        }
    }

    function go(index) {
        document.form1.p_pageno.value = index;
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.p_process.value = "LiteratureSubjectList";
        document.form1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.p_process.value = "LiteratureSubjectList";
        document.form1.submit();
    }

 	// 학습창 OPEN
    function studyOpen(url, iurl) {
        
        var f = document.form1;

        f.lessonReurl.value = url;
        f.lessonReiurl.value = iurl;
        var s_url = url+"&p_iurl="+iurl;
        
        if (url == '') {
            alert("정보가 없습니다");
        } else {
            if (iurl == 0){
                open_window('openApp',s_url,0,0,0,0,false,false,false,true,true);
            }else{
                open_window('openApp',s_url,0,0,1024,768,false,false,false,'yes','yes');
            }
        }
 	}
</script>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

<!-- form 시작 -->
<form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="get">
    <input type="hidden" id="oProcess" name="p_process" value="" />
    <input type="hidden" id="oPageNo" name="p_pageno" value="<%= pageNo %>" />
    <input type="hidden" id="oPageSize" name="p_pagesize" value="<%= pageSize %>" />
    <input type="hidden" id="oYear" name="p_year" value="" />
    <input type="hidden" id="oSubj" name="p_subj" value="" />
    <input type="hidden" id="oSubjseq" name="p_subjseq" value="" />
    <input type="hidden" id="oSubjnm" name="p_subjnm" value="" />
    <input type="hidden" id="oUpperclass" name="p_upperclass" value="" />
    <input type="hidden" id="oRprocess" name="p_rprocess" value="" />
    <input type="hidden" id="oUpperclassnm" name="p_upperclassnm" value="" />
    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>온라인 교육</li>
<%
        if ( currDate < 20150304 ) {
%>

            <li>
                휴넷인문학 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" >분야별</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup" >직업별</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem" >교육체계도</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList" class="active">휴넷인문학</a></li>

                    <!-- 2015-01-08 개편 변경
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                    -->
                </ul>
            </li>
<%
        }
%>
        </ul>
        <!--  페이지 내 검색 -->
        <div class="search">
            <fieldset>
                <legend>통합 검색</legend>
                <select name="p_search" title="검색 분류">
                    <option value="NAME" <%= v_search.equals("NAME") ? "selected" : "" %>>과정명</option>
<!--                    <option value="CONT" <%=v_search.equals("CONT")?"selected":"" %>>과정내용</option>-->
                </select>
                <input type="text" name="p_searchtext" value="<%= v_searchtext %>" title="검색어" class="keyword" onkeypress="search_enter(event);" />
                <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" onclick="javascript:subjSearch('', '', '');" />
            </fieldset>
        </div>

        <!-- 서브메인용 -->
        <!-- 2015-01-08 개편 삭제
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem"><strong><u>과정안내</u></strong></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
            </ul>
        </div>
        -->
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">휴넷인문학<!-- <img src="/images/2013/field_edu/courseguide_title.gif" alt="휴넷인문학" /> //--></h2>
            <div id="subCont">
                <!-- 2015-01-19 2015-01-19 개편 삭제
                <ul class="tabCus">
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><img src="/images/2013/online_edu/tabcurs4_off.jpg" alt="과정별 소개" /></a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup"><img src="/images/2013/online_edu/tabcurs3_off.jpg" alt="작업별 분류" /></a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikmu"><img src="/images/2013/online_edu/tabcurs2_off.jpg" alt="직무별 분류" /></a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem"><img src="/images/2013/online_edu/tabcurs1_off.jpg" alt="교육체계도" /></a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList"><img src="/images/2013/online_edu/tabcurs5_on.jpg" alt="휴넷인문학" /></a></li>
                </ul>
                //-->

                <div class="noticBox">
                    창의인재양성에 앞장서는 한국콘텐츠진흥원의 특별판 인문학 이제는 쉽게 시작해보세요!
                </div>

                <p class="search_title">총 <em><%= dbox.getInt("d_totalRowCount") %></em>개의 검색결과가 있습니다.</p>
                <div class="movList2">
<%
        if(literatureSubjectList.size() != 0){
            for (int i = 0; i < literatureSubjectList.size(); i++) {
                dbox = (DataBox)literatureSubjectList.get(i);
                String propyn = dbox.getString("d_propyn");
                String introducefilenamenew = dbox.getString("d_introducefilenamenew");
                String mobileUseYn = dbox.getString("d_mobile_use_yn");
				String isnewYn = dbox.getString("d_isnew");
				String ishitYn = dbox.getString("d_ishit");

                introducefilenamenew = introducefilenamenew.replaceAll("\\\\", "/");

                if (introducefilenamenew.equals("")) {
                    introducefilenamenew = "/images/2012/common/not_image.gif";
                }

                dispNum = dbox.getInt("d_dispNum");
                totalPage = dbox.getInt("d_totalPage");
                totalRowCount = dbox.getInt("d_totalRowCount");
                
                String eduUrl = dbox.getString("d_eduurl");
                String isoutsourcing = dbox.getString("d_ISOUTSOURCING");
                String cpsubj = dbox.getString("d_CPSUBJ");
                String cpsubjseq = dbox.getString("d_CPSUBJSEQ");
                String contenttype = dbox.getString("d_CPSUBJSEQ");
                String year = dbox.getString("d_scyear");
                String company = dbox.getString("d_company");
                String subj = dbox.getString("d_subj");
                String subjSeq = dbox.getString("d_subjseq");
                
             	// 학습url=외주인 경우..
                if(eduUrl.equals("")) { ieduurl = 0; }else{ ieduurl = 1; }
             	
                if (isoutsourcing.equals("Y")) {                 //위탁과정일때

                	eduUrl = "/servlet/controller.contents.EduStart";
                	eduUrl += "?FIELD1=" + box.getSession("userid") + "&FIELD2=" + year + "&FIELD3=" + cpsubj + "&FIELD4=" + cpsubjseq + "&FIELD99=" + company+"&FIELD100=N"+ "&contenttype=" + contenttype+ "&p_subj=" + subj +"&p_year="+ year +"&p_subjseq="+subjSeq;
                } else {//외부과정이 아닌경우
                     //직접 로직 흡수.. eduUrl = EduEtc1Bean.make_eduURL(v_subj,v_year,v_subjseq);
                    if (eduUrl.equals("")){
                    	eduUrl = "/servlet/controller.contents.EduStart?p_subj="+subj;
                    }

                    if (!year.equals(""))
                    	eduUrl = eduUrl+"&p_year="+year+"&p_subjseq=";
                    else
                    	eduUrl = eduUrl+"&p_year=2000&p_subjseq=";

                    if(!subjSeq.equals(""))
                    	eduUrl = eduUrl+subjSeq;
                    else eduUrl = eduUrl+"0001";
                    eduUrl +=  "&contenttype=" + contenttype;
                }
%>
                    <dl>
                        <dt>
                            <img src="<%=introducefilenamenew%>" width="126" height="80" alt="<%= dbox.get("d_subjnm")%>" />
                        </dt>
                        <dd class="title">
                            <a href="javascript:fnViewSubjectInfo('<%= dbox.get("d_subj") %>', '<%= dbox.get("d_subjnm") %>', '<%= dbox.get("d_isonoff") %>', '<%= dbox.get("d_upperclass") %>', '<%= dbox.get("d_upperclassnm") %>', '<%= dbox.get("d_year") %>', '<%= dbox.get("d_subjseq") %>')"><%= dbox.get("d_subjnm") %></a>
                            <span class="img_mobile" style="float:right; margin-top:-6px;">
<%
                                if(mobileUseYn.equals("Y")) {
%>
											<img src="/images/2013/online_edu/detail_mbile.png" style="float:right; margin-right:5px;" alt="모바일"/>
<%
                                }
%>
<%
                                if(ishitYn.equals("Y")) {
%>
											<img src="/images/2013/online_edu/detail_popular.png" style="float:right; margin-right:5px;" alt="인기" />
<%
                                }
%>
<%
                                if(isnewYn.equals("Y")) {
%>
											<img src="/images/2013/online_edu/detail_new.png" style="float:right; margin-right:5px;" alt="신규" />
<%
                                }
%>
											</span>

                        </dd>
                        <dd class="txt"><%= StringManager.formatTitle(dbox.get("d_intro"),180) %></dd>
                        <dd class="period">신청기간 <%= dbox.getString("d_propstart") %> ~ <%= dbox.getString("d_propend") %> 교육기간 <%= dbox.getString("d_edustart") %> ~ <%= dbox.getString("d_eduend") %></dd>
                        <dd class="btns">
                            <ul>
                                <li><a href="#" class="chart1">차시보기</a></li>

                                <li>
<%
                                if(dbox.getString("d_exist_prop").equals("Y")) {
%>
                                        <a href="javascript:studyOpen( '<%= eduUrl %>', '<%= ieduurl %>');" title="이 과정에 대해 학습을 진행 합니다.">학습중</a>
<%
                                } else {
                                    if( propyn.equals("Y") ) {
%>
                                            <a href="javascript:fnApplySubjPropose('<%= dbox.get("d_subj")%>','<%= dbox.get("d_year")%>','<%= dbox.get("d_subjseq")%>','<%= dbox.get("d_subjnm")%>');" class="chart3">학습하기</a></li>
<%
                                    }
                                }
%>
                            </ul>
                            <!-- 레이어 -->
                            <div class="mvLayer" id="<%= dbox.get("d_subj")%>">
                            </div>
                            <!--// 레이어 -->
                        </dd>
                    </dl>
<%                          }
                        }else{
%>
                    <dl><dd>검색된 과정이 없습니다.</dd></dl>
<%
                        } 
%>
                </div>
                <!--// movList2 -->

                <!-- //board2 -->

                <div class="paging">
                    <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 0, pageSize, totalRowCount) %>
                    <%-- PageUtil.printPageSizeList(totalPage, pageNo, 0, pageSize, totalRowCount) --%>
                </div>
                <!-- //paging -->

            </div>
            <!-- //#subCont -->
    </div>
</form>

</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
