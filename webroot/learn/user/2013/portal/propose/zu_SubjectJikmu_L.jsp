<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "java.util.ArrayList" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    //페이징관련

    String v_process = box.getString("p_process");

    // 검색
    String v_searchtext = box.getString("p_searchtext");
    String v_search = box.getString("p_search");

    int v_pageno = box.getInt("p_pageno");
    int v_dispnum = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount = 0;
    String v_position = "";

    String v_gubun_1 = box.getStringDefault("p_gubun_1","A");
    String v_gubun_2 = box.getStringDefault("p_gubun_2","A");
    String v_gubun_3 = box.getStringDefault("p_gubun_3","A");
    String p_action = box.get("p_action");

    String v_gubunnm_1 = "";
    String v_gubunnm_2 = "";

    if(v_gubun_1.equals("B0")){
        v_gubunnm_1 = "방송영상";
    } else if(v_gubun_1.equals("G0")) {
        v_gubunnm_1 = "게임";
    } else if(v_gubun_1.equals("K0")){
        v_gubunnm_1 = "만화애니캐릭터";
    } else if(v_gubun_1.equals("M0")) {
        v_gubunnm_1 = "음악공연";
    } else {
        v_gubunnm_1="전체";
    }

    ArrayList subjList = (ArrayList)request.getAttribute("SubjectList");  // 과정 리스트
    ArrayList gubun_1 = (ArrayList)request.getAttribute("Subjhomegubun_1");  //필터 구분 1
    ArrayList gubun_2 = (ArrayList)request.getAttribute("Subjhomegubun_2");  //필터 구분 2
    ArrayList gubun_3 = (ArrayList)request.getAttribute("Subjhomegubun_3");  //필터 구분 2

    box.put("title_str", "직무별분류 - 과정안내 - 온라인교육");
%>

<%@ include file="/learn/user/2013/portal/include/top_sub.jsp"%>
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
    function search_enter(e)  {
        if (e.keyCode =='13') {
            fnSubjectSearch("<%=v_gubun_1 %>","<%=v_gubun_2 %>","<%=v_gubun_3 %>");
        }
    }


    /**
     * 과정 카테고리 선택을 이용하여 과정을 검색한다.
     */
    function fnSubjectSearch(p_gubun_1, p_gubun_2, p_gubun_3) {
        // document.form1.p_pageno.value = "1";
        // document.form1.p_gubun_1.value = p_gubun_1;
        // document.form1.p_gubun_2.value = p_gubun_2;
        // document.form1.p_gubun_3.value = p_gubun_3;
        // document.form1.p_process.value = "SubjectListJikmu";
        // document.form1.submit();

        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "?p_process=SubjectListJikmu&p_pageno=1&p_gubun_1=" + p_gubun_1 + "&p_gubun_2=" + p_gubun_2 + "&p_gubun_3=" + p_gubun_3;

        location.href = url + param;
    }

    /**
     * 직무별분류에서 1번째 분야, 2번째 직업 카테고리 선택을 이용하여 과정을 검색한다.
     */
    function fnSubjectSearch2(change_gubun, p_gubun_1, p_gubun_2, p_gubun_3) {
        if(change_gubun == "1"){
            p_gubun_2 = "A";
            p_gubun_3 = "A";
            fnSubjectSearch(p_gubun_1, p_gubun_2, p_gubun_3);
        }
        if(change_gubun == "2"){
            p_gubun_3 = "A";
            fnSubjectSearch(p_gubun_1, p_gubun_2, p_gubun_3);
        }
    }

    /**
     * 과정 상세 정보 화면으로 이동한다.
     */
    function fnViewSubjectInfo(subj, subjnm, courseyn, upperclass, upperclassnm, year, subjseq){
        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "?p_process=SubjectPreviewPage&p_rprocess=SubjectList&p_subj=" + subj + "&p_year=" + year + "&p_subjseq=" + subjseq;

        location.href = url + param;

        /* 2014-12-22 주석 처리
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_iscourseYn.value = courseyn;
        //document.form1.p_upperclass.value = upperclass;
        document.form1.p_upperclassnm.value = upperclassnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_process.value = 'SubjectPreviewPage';
        document.form1.p_rprocess.value = 'SubjectListJikmu';
        document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
        document.form1.target = "_self";
        document.form1.submit();
        */
    }
    //맛보기
    function whenPreShow(url, subj, d_preheight, d_prewidth) {
        // 로그
        prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
        open_window("openShow",url,"100","100",d_preheight,d_prewidth,"","","",true,true);
    }

    //수강신청
    function whenSubjPropose(subj,year,subjseq, subjnm, billYn) {

        if(<%= box.getSession("userid").length()==0 %>) {
            alert("로그인이 필요합니다.");
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
        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.submit();
        document.form1.target = "_self";
    }

    function go(index) {
        document.form1.p_gubun_1.value = "<%=v_gubun_1%>";
        document.form1.p_gubun_2.value = "<%=v_gubun_2%>";
        document.form1.p_gubun_3.value = "<%=v_gubun_3%>";
        document.form1.p_pageno.value = index;
        document.form1.target  =   "_self";
        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.p_process.value = "SubjectListJikmu";
        document.form1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.form1.p_gubun_1.value = "<%=v_gubun_1%>";
        document.form1.p_gubun_2.value = "<%=v_gubun_2%>";
        document.form1.p_gubun_3.value = "<%=v_gubun_3%>";
        document.form1.p_pageno.value = pageNum;
        document.form1.target  =   "_self";
        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
        document.form1.p_process.value = "SubjectListJikmu";
        document.form1.submit();
    }
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
    <input type="hidden" name="p_process" value="<%=box.get("p_process")%>">
    <input type="hidden" name="p_year" value="">
    <input type="hidden" name="p_subj" value="">
    <input type="hidden" name="p_subjseq" value="">
    <input type="hidden" name="p_subjnm" value="">
    <input type="hidden" name="p_iscourseYn" value="">
    <input type="hidden" name="p_upperclassnm" value="">
    <input type="hidden" name="p_action" value="<%=p_action%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type="hidden" name="p_rprocess" value="">
    <input type="hidden" name="p_upperclassnm" value="">
    <input type="hidden" name="p_topuserid" value="">
    <input type="hidden" name="p_toppwd" value="">
    <input type="hidden" name="p_gubun_1" value="<%=v_gubun_1%>"/>
    <input type="hidden" name="p_gubun_2" value="<%=v_gubun_2%>"/>
    <input type="hidden" name="p_gubun_3" value="<%=v_gubun_3%>"/>
    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>온라인 교육</li>
            <li>
                과정안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                </ul>
            </li>
        </ul>

        <!--  페이지 내 검색 -->
        <div class="search">
            <fieldset>
                <legend>통합 검색</legend>
                <select name="p_search" title="검색 분류">
                    <option value="NAME" <%=v_search.equals("NAME")?"selected":"" %>>과정명</option>
<!--                    <option value="CONT" <%=v_search.equals("CONT")?"selected":"" %>>과정내용</option>-->
                </select>
                <input type="text" name="p_searchtext" value="<%=v_searchtext %>" title="검색어" class="keyword" onkeypress="search_enter(event);" />
                <input type="image" src="/images/2013/btn/btn_src2.gif" alt="검색" class="btn" onclick="javascript:fnSubjectSearch('<%=v_gubun_1 %>','<%=v_gubun_2 %>','<%=v_gubun_3 %>');" />
            </fieldset>
        </div>

        <!-- 서브메인용 -->
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem"><strong><u>과정안내</u></strong></a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
            </ul>
        </div>
        <!-- //서브메인용 -->
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit"><img src="/images/2013/field_edu/courseguide_title.gif" alt="과정안내" /></h2>
            <div id="subCont">
                <ul class="tabCus">
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><img src="/images/2013/online_edu/tabcurs4_off.jpg" alt="과정별 소개" /></a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup"><img src="/images/2013/online_edu/tabcurs3_off.jpg" alt="작업별 분류" /></a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikmu"><img src="/images/2013/online_edu/tabcurs2_on.jpg" alt="직무별 분류" /></a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem"><img src="/images/2013/online_edu/tabcurs1_off.jpg" alt="교육체계도" /></a></li>
<%
        if ( box.getSession("isLiteratureSubjYn").equals("Y") ) {
%>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList"><img src="/images/2013/online_edu/tabcurs5_off.jpg" alt="휴넷인문학" /></a></li>
<%
        }
%>
                </ul>

                <div class="noticBox">
                    온라인과정의 직무별 분류에 따라 교육과정을 확인 하실 수 있습니다.<br/>
                    과정명 우측에 <img src="/images/2013/online_edu/detail_mbile.gif" alt="모바일" style="vertical-align: middle;"/> 표시가 있는 과정은 <strong>모바일 앱</strong>을 이용하여 학습 하실 수 있습니다.
                </div>


                <div class="selectBox">
                    <div class="mtstepWrap">
                        <p class="steptit"><img src="/images/2013/online_edu/tit_step1.gif" alt="분야를 선택해 주세요" /></p>
                        <div class="scroll">
                            <ul>
                                <%
                                    if(gubun_1.size() != 0){
                                        for (int i=0; i<gubun_1.size(); i++) {
                                            DataBox dbox_1 = (DataBox)gubun_1.get(i);
                                            String code_1 = dbox_1.getString("d_code");
                                            String codenm_1 = dbox_1.getString("d_codenm");

                                %>
                                            <li><a href="javascript:fnSubjectSearch2('1','<%=code_1 %>','<%=v_gubun_2 %>','<%=v_gubun_3 %>');" <%=v_gubun_1.equals(code_1)?"class='active'":"" %>><%=codenm_1 %></a></li>
                                <%      }
                                    }
                                %>
                            </ul>
                        </div>
                    </div>

                    <div class="mtstepWrap">
                        <p class="steptit"><img src="/images/2013/online_edu/tit_step2_jm.gif" alt="직무을 선택해 주세요" /></p>
                        <div class="scroll" tabindex="0">
                            <ul>
                                <%
                                    if(gubun_2.size() != 0){
                                %>
                                        <li><a href="javascript:fnSubjectSearch('<%=v_gubun_1 %>','A','A');" <%=v_gubun_2.equals("A")?"class='active'":"" %>>전체</a></li>
                                <%
                                        for (int i=0; i<gubun_2.size(); i++) {
                                            DataBox dbox_2 = (DataBox)gubun_2.get(i);
                                            String code_2 = dbox_2.getString("d_code");
                                            String codenm_2 = dbox_2.getString("d_codenm");

                                            if(code_2.equals(v_gubun_2)){
                                                v_gubunnm_2 = codenm_2;
                                            }
                                %>
                                            <li><a href="javascript:fnSubjectSearch2('2','<%=v_gubun_1 %>','<%=code_2 %>','<%=v_gubun_3 %>');" <%=v_gubun_2.equals(code_2)?"class='active'":"" %>><%=codenm_2 %></a></li>
                                <%      }
                                    }else{
                                %>
                                        <li>&nbsp;</li>
                                        <li>&nbsp;</li>
                                        <li>&nbsp;</li>
                                        <li style="text-align: center;"><a><strong>분야</strong>를 선택해 주세요.</a></li>
                                <%
                                    }
                                %>
                            </ul>
                        </div>
                    </div>

                    <div class="mtstepWrap">
                        <p class="steptit"><img src="/images/2013/online_edu/tit_step3.gif" alt="과정을 선택해 주세요" /></p>
                        <div class="scroll" tabindex="0">
                            <ul>
                                <%
                                    if(gubun_3.size() != 0){
                                %>
                                        <li><a href="javascript:fnSubjectSearch('<%=v_gubun_1 %>','<%=v_gubun_2 %>','A');" <%=v_gubun_3.equals("A")?"class='active'":"" %>>전체</a></li>
                                <%
                                        for (int i=0; i<gubun_3.size(); i++) {
                                            DataBox dbox_3 = (DataBox)gubun_3.get(i);
                                            String code_3 = dbox_3.getString("d_code");
                                            String codenm_3 = dbox_3.getString("d_codenm");
                                %>
                                            <li><a href="javascript:fnSubjectSearch('<%=v_gubun_1 %>','<%=v_gubun_2 %>','<%=code_3 %>');" <%=v_gubun_3.equals(code_3)?"class='active'":"" %>><%=codenm_3 %></a></li>
                                <%      }
                                    }else{
                                %>
                                        <li>&nbsp;</li>
                                        <li>&nbsp;</li>
                                        <li>&nbsp;</li>
                                        <li style="text-align: center;"><a><strong>직무</strong>를 선택해 주세요.</a></li>
                                <%
                                    }
                                %>
                            </ul>
                        </div>
                    </div>
                </div>
                <%if(!v_gubun_2.equals("A")){ %>
                    <img src="/images/2013/online_edu/subjjikmu_script/<%=v_gubun_1 %><%=v_gubun_2 %>.gif" alt="<%=v_gubunnm_1 %> <%=v_gubunnm_2.replaceAll("_","에 ") %>의 역량소개" style="margin-bottom: 30px;"/>
                <%} %>

                <p class="search_title"><strong><%=v_gubunnm_1 %></strong>에 대한 총 <em><%=box.getStringDefault("d_totalcount","0") %></em>개의 검색결과가 있습니다.</p>
                <div class="movList2">
                    <%
                        if(subjList.size() != 0){
                            for (int i=0; i<subjList.size(); i++) {
                                DataBox dbox = (DataBox)subjList.get(i);
                                String propyn = dbox.getString("d_propyn");
                                String imgurl = dbox.getString("d_introducefilenamenew");
                                String mobileUseYn = dbox.getString("d_mobile_use_yn");
                                String isnewYn = dbox.getString("d_isnew");
                                String ishitYn = dbox.getString("d_ishit");

                                imgurl = imgurl.replaceAll("\\\\", "/");

                                if (imgurl.equals("")) {
                                    imgurl = "/images/2012/common/not_image.gif";
                                } else {
                                    imgurl = "http://edu.kocca.or.kr/"+imgurl;
                                }

                                v_dispnum        = dbox.getInt("d_dispnum");
                                v_totalpage      = dbox.getInt("d_totalpage");
                                v_rowcount       = dbox.getInt("d_rowcount");
                                v_position       = dbox.getString("d_position");
                    %>
                    <dl>
                        <dt>
                            <img src="<%=imgurl%>" width="126" height="80" alt="<%=dbox.get("d_subjnm")%>" />
<!--                            <span class="btn_threeWrap">-->
<!--                                <span class="btn_three1"><a href="#"> 과정 만족도 : <%=dbox.get("d_sul_avg") %></a></span>-->
<!--                            </span>-->
                        </dt>
                        <dd class="title"><a href="javascript:fnViewSubjectInfo('<%=dbox.get("d_subj")%>',
                                                                    '<%=dbox.get("d_subjnm")%>',
                                                                    '<%=dbox.get("d_isonoff")%>',
                                                                    '<%=dbox.get("d_scupperclass")%>',
                                                                    '<%=dbox.get("d_uclassnm")%>',
                                                                    '<%=dbox.get("d_scyear")%>',
                                                                    '<%=dbox.get("d_subjseq")%>')"><%=dbox.get("d_subjnm")%></a>
                                            <span class="img_mobile" style="float:right; margin-top:-6px;">
<%
                                if(mobileUseYn.equals("Y")){
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
                        <dd class="txt"><%=StringManager.formatTitle(dbox.get("d_intro"),180) %></dd>
                        <dd class="period">신청기간 <%=dbox.get("d_propstart").substring(0,10)%> ~ <%=dbox.get("d_propend").substring(0,10)%>   교육기간 <%=dbox.get("d_edustart").substring(0,10)%> ~ <%=dbox.get("d_eduend").substring(0,10)%></dd>
                        <dd class="btns">
                            <ul>
                                <li><a href="#" class="chart1">차시보기</a></li>
                                <li><a href="javascript:whenPreShow('<%= dbox.get("d_preurl")%>','<%= dbox.get("d_subj")%>', '<%= dbox.get("d_prewidth", "800") %>','<%= dbox.get("d_preheight", "600") %>' );" class="chart2">강의맛보기</a></li>
                                <li><%if(dbox.getBoolean("d_existpropose")) {%>
                                        신청완료
                                    <%}else{
                                        if( propyn.equals("Y") ) {%>
                                            <a href="javascript:whenSubjPropose('<%=dbox.get("d_subj")%>','<%=dbox.get("d_scyear")%>','<%=dbox.get("d_subjseq")%>','<%=dbox.get("d_subjnm")%>');" class="chart3">수강신청</a></li>
                                    <%  }
                                    } %>
                            </ul>
                            <!-- 레이어 -->
                            <div class="mvLayer" id="<%=dbox.get("d_subj")%>">
                            </div>
                            <!--// 레이어 -->
                        </dd>
                    </dl>
                    <%  }
                        }else{%>
                    <dl><dd>검색된 과정이 없습니다.</dd></dl>
                    <%} %>
                </div>
                <!--// movList2 -->

                <!-- //board2 -->

                <div class="paging">
                    <%=PageUtil.re2013_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
                </div>
                <!-- //paging -->

            </div>
            <!-- //#subCont -->
    </div>
    </form>
<%@ include file="/learn/user/2013/portal/include/footer.jsp"%>