<%@ page contentType="text/html;charset=MS949" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.credu.propose.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.course.ClassifySubjectBean" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<jsp:useBean id="getCodenm" class="com.credu.common.GetCodenm" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("topmenu","00");

    String p_action = box.get("p_action");
    String p_lsearchtext = box.get("p_lsearchtext");
    String detlSubjFlag = box.get("detlSubjFlag");

    String v_upperclass = "";

    if (box == null) {
        box = RequestManager.getBox(request);
    }

    List<DataBox> onlineSubjList = (List)request.getAttribute("onlineSubjList");        // 온라인 과정 리스트
    List<DataBox> offlineSubjList = (List)request.getAttribute("offlineSubjList");      // 오프라인 과정 리스트
    List<DataBox> openclassSubjList = (List)request.getAttribute("openclassSubjList");  // 열린강좌 과정 리스트

    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>

    String v_titleprn = "";
    String v_contentprn = "";

    int pageNo = box.getInt("pageNo");
    int pageSize = box.getInt("pageSize");
    int totalRowCnt = 0;
    int totalPage = 0;

    box.put("title_str", "통합검색결과");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<script type="text/javascript">

    /**
     * 과정검색
     */
    function subjSearch() {
        $("#oProcess").val( "TotalSubjectList" );
        $("#oSearchForm").submit();
    }

    /**
     * 온라인 과정 내용보기
     */
    function fnViewOnlineSubjInfo(subj, subjnm, courseyn, upperclass, upperclassnm, year, subjseq, menuid) {
        var url = "/servlet/controller.propose.ProposeCourseServlet";
        var param = "";
        param += "?p_process=SubjectPreviewPage";
        param += "&p_rprocess=TotalSubjectList";
        param += "&p_subj=" + subj;
        param += "&p_year=" + year;
        param += "&p_subjseq=" + subjseq;

        location.href = url + param;

        /*
        $("#oSubj").val( subj );
        $("#oSubjnm").val( subjnm );
        $("#oIscourseYn").val( courseyn );
        $("#oUpperclass").val( upperclass );
        $("#oUpperclassnm").val( upperclassnm );
        $("#oYear").val( year );
        $("#oSubjseq").val( subjseq );
        $("#oMenuid").val( menuid );
        $("#oProcess").val( "SubjectPreviewPage" );
        $("#oRprocess").val( "TotalSubjectList" );

        $("#oSearchForm").attr("action", "/servlet/controller.propose.ProposeCourseServlet");
        $("#oSearchForm").attr("target", "_self");
        $("#oSearchForm").submit();
        */
    }

    /**
     * 현장교육 과정 내용 보기
     */
    function fnViewOfflineSubjInfo(subj, year, subjseq, seq){
        $("#oSubj").val( subj );
        $("#oYear").val( year );
        $("#oSubjseq").val( subjseq );
        $("#oSeq").val( seq );
        $("#oProcess").val( "SubjectPreviewPage" );
        $("#oRprocess").val( "SubjectList" );

        $("#oSearchForm").attr("method", "get").attr("action", "/servlet/controller.off.ProposeOffServlet");
        $("#oSearchForm").submit();
    }

    /**
     * 열린 강좌 열기
     */
    function fnViewOpenclass( seq, lectureCls) {
        var url = "/servlet/controller.infomation.GoldClassHomePageServlet";
        var param = "?p_process=selectView&p_seq=" + seq + "&p_lecture_cls=" + lectureCls;

        location.href = url + param;
    }

    /**
     * 수강신청
     */
    function whenSubjPropose(subj,year,subjseq, subjnm, billYn) {

        if(<%= box.getSession("userid").length()==0 %>) {
            alert("로그인이 필요합니다.");
            return;
        }

        if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
         return;
        }

        var new_Open = window.open("","proposeWindow","scrollbars=yes,width=800,height=600,resizable=no");

        $("#oProcess").val("SubjectEduProposePage" );
        $("#oSubj").val( subj );
        $("#oYear").val( year );
        $("#oSubjseq").val( subjseq );

        $("#oSearchForm").attr("action", "/servlet/controller.propose.ProposeCourseServlet").attr("target", "proposeWindow").submit();
    }

    /**
     * 전체 과정검색
     */
    function totalSubjSearch2() {

        var searchText = $("#oInputSearchText").val();

        if ( searchText.replace(/ /gi, "") == "" ) {
            alert("검색어를 입력하세요.");
            $("#oInputSearchText").focus();
            return false;
        }

        $("#oInputSearchText").value = replaceBlank( searchText.trim() );
        $("#oProcess").val( "SubjSearch" );
        $("#oDetlSubjFlag").val("");

        $("#oSearchForm").attr("method", "get");
        $("#oSearchForm").attr("action", "/servlet/controller.propose.MainSubjSearchServlet");
        $("#oSearchForm").attr("target", "_self");
        $("#oSearchForm").submit();
    }

    function fnPreView( previewURL, previewWinHeight, previewWinWidth ) {
        open_window("openShow", previewURL,"100","100", previewWinHeight, previewWinWidth, "", "", "", true, true);
    }

    /**
     * 목록 더보기
     * @param detlSubjFlag 상세 과정 플래그
     * @param searchText 검색어
     */
    function fnViewMoreList( detlSubjFlag, searchText ) {

        $("#oInputSearchText").val( searchText );
        $("#oDetlSubjFlag").val( detlSubjFlag );

        $("#oSearchForm").submit();
    }

    /**
     * 페이지 이동
     */
    function goPage( pageNo ) {
        $("#oPageno").val( pageNo );
        $("#oSearchForm").submit();
    }

//-->
</script>
<!-- container-new 시작 //-->
<div id="container-new">

<!-- form 시작 -->
<form id="oSearchForm" name="form1" action="/servlet/controller.propose.MainSubjSearchServlet" method="post">
    <input type="hidden"    id="oProcess"       name="p_process"        value="<%=box.get("p_process")%>" />
    <input type="hidden"    id="oRprocess"      name="p_rprocess"       value="" />
    <input type="hidden"    id="oYear"          name="p_year"           value="" />
    <input type="hidden"    id="oSubj"          name="p_subj"           value="" />
    <input type="hidden"    id="oSubjseq"       name="p_subjseq"        value="" />
    <input type="hidden"    id="oSubjnm"        name="p_subjnm"         value="" />
    <input type="hidden"    id="oIscourseYn"    name="p_iscourseYn"     value="" />
    <input type="hidden"    id="oUpperclass"    name="p_upperclass"     value="" />
    <input type="hidden"    id="oUpperclassnm"  name="p_upperclassnm"   value="" />
    <input type="hidden"    id="oAction"        name="p_action"         value="<%= p_action %>" />
    <input type="hidden"    id="oPageno"        name="pageNo"           value="" />
    <input type="hidden"    id="oPagesize"      name="pageSize"         value="<%= pageSize %>" />
    <input type="hidden"    id="oMenuid"        name="menuid"           value="" />
    <input type="hidden"    id="oOrder"         name="p_order"          value="" />
    <input type="hidden"    id="oSeq"           name="p_seq"            value="" />
    <input type="hidden"    id="oDetlSubjFlag"  name="detlSubjFlag"     value="<%= detlSubjFlag %>" />
    <input type="hidden"    id="oOrgSearchText" name="orgSearchText"    value="<%= p_lsearchtext %>" />

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>통합검색</li>
        </ul>
    </div>

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">통합검색</h2>

        <div id="subCont">
            <!-- searchContent -->
            <div class="searchContent">
                <fieldset class="searchForm">
                    <legend>통합검색</legend>
                    <p><label for="search_keyword">통합검색</label></p>
                    <input type="text" id="oInputSearchText" name="p_lsearchtext" value="<%= p_lsearchtext %>" title="검색어 입력" />
                    <button title="검색" onclick="totalSubjSearch2();"></button>
                </fieldset>

<%
    if(onlineSubjList.size() > 0 || offlineSubjList.size() > 0 || openclassSubjList.size() > 0 ) {
%>
                <p class="result_msg"><span>"<%= p_lsearchtext %>"</span> 의 검색결과가 있습니다.</p>
<%
    } else {
%>
                <p class="result_msg"><span>"<%= p_lsearchtext %>"</span> 의 검색결과가 없습니다.</p>
<%
    }

    if ( detlSubjFlag.equals("") || detlSubjFlag.equals("ONLINE") ) {
%>
                <p class="sch_title"><span>온라인교육</span> 검색 결과</p>
<%
    }

    int maxCount = 0;
    int contMaxLength = 190;

    // 온라인 과정
    if(onlineSubjList.size() > 0 || detlSubjFlag.equals("ONLINE") ) {
        maxCount = detlSubjFlag.equals("ONLINE") ? 10 : 5;
        maxCount = ( onlineSubjList.size() > maxCount ) ? maxCount : onlineSubjList.size();

        for(int i = 0 ; i < maxCount ; i++) {
            DataBox dbox =  onlineSubjList.get(i);

            v_titleprn = this.getConvertedTitle( dbox.get("d_subjnm"), p_lsearchtext );

            v_contentprn = this.getConvertedContents(dbox.get("d_intro"), p_lsearchtext, contMaxLength);
%>
                <div class="result">
                    <div style="width:98%; height:115px; background-color:#DEDEDE; padding:10px;">
                        <div style="float:left;">
                            <img src="<%= dbox.get("d_subjfilenamenew") %>" alt="검색결과이미지" width="165" height="114" />
                        </div>
                        <div style="float:left; margin-left:20px; padding:10px;">
                            <div>
                                <p class="tx" style="over-flow:auto;"><a href="javascript:fnViewOnlineSubjInfo('<%= dbox.get("d_subj") %>', '<%=dbox.get("d_subjnm")%>', '<%=dbox.get("d_isonoff")%>', '<%=dbox.get("d_scupperclass")%>', '<%=dbox.get("d_scupperclassnm")%>', '<%=dbox.get("d_year")%>', '<%=dbox.get("d_subjseq")%>');" title="<%= dbox.get("d_subjnm") %>"><%= v_titleprn %></a></p>
                                <p class="subtx" style="width: 670px; height: 42px; word-break: keep-all;"><%= v_contentprn %></p>
                            </div>
                            <div>
                                <div style="width:300px; float:left;" >
                                    <font color="gray"><p>&nbsp;<%=dbox.get("d_areanm")%></p></font>
                                </div>
                                <div style="float:right;">
                                    <a href="javascript:fnPreView('<%= dbox.get("d_preurl") %>', '<%= dbox.get("d_width") %>', '<%= dbox.get("d_height") %>');" title="<%= dbox.get("d_subjnm") %> 맛보기"><img src="/images/2013/search/btn_preview.png" alt="<%= dbox.get("d_subjnm") %> 맛보기" /></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
<%
            totalRowCnt = dbox.getInt("totalRowCnt");
            totalPage = dbox.getInt("totalPage");
        }

        if ( maxCount < onlineSubjList.size() ) {
%>
                <div style="font-size:14px; text-align:right;">
                    <a href="javascript:fnViewMoreList('ONLINE', '<%= p_lsearchtext %>');"><b>온라인 과정 검색 결과 더보기 &gt;&gt;</b></a>
                </div>
<%
        }
    }

    if ( onlineSubjList.size() == 0 && ( detlSubjFlag.equals("") || detlSubjFlag.equals("ONLINE") )) {
%>
                <p class="tx">온라인 과정 검색 결과가 없습니다.</p>
<%
    }

    if ( detlSubjFlag.equals("") || detlSubjFlag.equals("OFFLINE") ) {
%>

                <div style="height:40px; ">&nbsp;</div>
                <p class="sch_title"><span>현장교육</span> 검색 결과</p>

<%
    }

    // 오프라인 과정 (현장교육)
    if(offlineSubjList.size() > 0 || detlSubjFlag.equals("OFFLINE")) {
        maxCount = detlSubjFlag.equals("OFFLINE") ? 10 : 5;
        maxCount = ( offlineSubjList.size() > maxCount ) ? maxCount : offlineSubjList.size();
        String temp = "";

        for(int i = 0 ; i < maxCount ; i++) {
            DataBox dbox =  offlineSubjList.get(i);

            v_titleprn = this.getConvertedTitle( dbox.get("d_subjnm"), p_lsearchtext );

            temp = StringManager.getContentsHTMLRemoved( dbox.get("d_intro") );

            v_contentprn = this.getConvertedContents(temp, p_lsearchtext, contMaxLength);

%>
                <div class="result">
                    <div style="width:98%; height:65px; background-color:#DEDEDE; padding:10px;">
                        <div style="float:left; margin-left:20px; padding:10px;">
                            <div>
                                <p class="tx"><a href="javascript:fnViewOfflineSubjInfo('<%= dbox.get("d_subj") %>', '<%= dbox.get("d_year") %>', '<%= dbox.get("d_subjseq") %>', '<%= dbox.getStringDefault("d_seq", "1") %>');" title="<%= dbox.get("d_subjnm") %>"><%= v_titleprn %></a></p>
                                <p class="subtx" style="word-break: keep-all;"><%= v_contentprn %></p>
                            </div>
                            <!--
                            <div>
                                <div style="width:300px; float:left;" >
                                    <font color="gray"><p>&nbsp;<%=dbox.get("d_areanm")%></p></font>
                                </div>
                            </div>
                            //-->
                        </div>
                    </div>
                </div>
<%
            totalRowCnt = dbox.getInt("totalRowCnt");
            totalPage = dbox.getInt("totalPage");
        }

        if ( maxCount < offlineSubjList.size() ) {
%>
                <div style="font-size:14px; text-align:right;">
                    <a href="javascript:fnViewMoreList('OFFLINE', '<%= p_lsearchtext %>');"><b>현장교육 검색 결과 더보기 &gt;&gt;</b></a>
                </div>
<%
        }
    }

    if ( offlineSubjList.size() == 0 && ( detlSubjFlag.equals("") || detlSubjFlag.equals("OFFLINE") )) {
%>
                <p class="tx">현장교육 검색 결과가 없습니다.</p>
<%
    }

    if ( detlSubjFlag.equals("") || detlSubjFlag.equals("OPENCLASS") ) {
%>

                <div style="height:40px; ">&nbsp;</div>
                <p class="sch_title"><span>열린강좌</span> 검색 결과</p>
<%
    }

    // 열린강좌 과정
    if(openclassSubjList.size() > 0 || detlSubjFlag.equals("OPENCLASS")) {
        maxCount = detlSubjFlag.equals("OPENCLASS") ? 10 : 5;
        maxCount = ( openclassSubjList.size() > maxCount ) ? maxCount : openclassSubjList.size();

        for(int i = 0 ; i < maxCount ; i++) {
            DataBox dbox =  openclassSubjList.get(i);

            v_titleprn = this.getConvertedTitle( dbox.get("d_subjnm"), p_lsearchtext );

            v_contentprn = this.getConvertedContents(dbox.get("d_explain"), p_lsearchtext, contMaxLength);

%>
                <div class="result">
                    <div style="width:98%; height:115px; background-color:#DEDEDE; padding:10px;">
                        <div style="float:left;">
                            <img src="<%= dbox.get("d_subjfilenamenew") %>" alt="검색결과이미지" width="165" height="114" />
                        </div>
                        <div style="float:left; margin-left:20px; padding:10px;">
                            <div>
                                <p class="tx" style="over-flow:auto;"><a href="javascript:fnViewOpenclass('<%= dbox.get("d_subjseq") %>', '<%=dbox.get("d_lecture_cls")%>');" title="<%= dbox.get("d_subjnm") %> 보기"><%= v_titleprn %></a></p>
                                <p class="subtx" style="width:670px; height: 42px; word-break: keep-all;"><%= v_contentprn %></p>
                            </div>
                            <div>
                                <div style="width:180px; float:left;" >
                                    <font color="gray"><p>&nbsp;<%= dbox.get("d_areanm") %></p></font>
                                </div>
                                <div style="width:240px; float:left;" >
                                    <font color="gray"><p>&nbsp;<%= this.getConvertedTitle( dbox.get("d_tutor_nm"), p_lsearchtext ) %></p></font>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
<%
            totalRowCnt = dbox.getInt("totalRowCnt");
            totalPage = dbox.getInt("totalPage");
        }

        if ( maxCount < openclassSubjList.size() ) {
%>
                <div style="font-size:14px; text-align:right;">
                    <a href="javascript:fnViewMoreList('OPENCLASS', '<%= p_lsearchtext %>');"><b>열린강좌 검색 결과 더보기 &gt;&gt;</b></a>
                </div>
<%
        }
    }

    if ( openclassSubjList.size() == 0 && ( detlSubjFlag.equals("") || detlSubjFlag.equals("OPENCLASS") )) {
%>
                    <p class="tx">열린강좌 검색 결과가 없습니다.</p>
<%
    }
%>

                <!-- 열린강좌 검색 결과 끝 -->

            </div>
<%
    if ( !detlSubjFlag.equals("") ) {
%>
            <div class="paging pgSpace">
                <%= PageUtil.re2013_printPageListDiv(totalPage, pageNo, 0, pageSize, totalRowCnt) %>
            </div>
<%
    }

%>

            <!--// searchContent -->
        </div>
        <!-- //#subCont -->
    </div>
    <!-- //#contents -->
    <!-- 컨텐츠 영역 끝 -->
</form>

</div>
<!--  //container-new 끝 //-->

<%!

    private String makeBold(String src, String chg) {
        String temp = src;
        int idx = src.toUpperCase().indexOf(chg.toUpperCase());

        if( idx == -1 ) {
            return src;
        } else {
            if ( idx == 0 ) {
                temp = "<b>" + src.substring(idx, chg.length()) + "</b>";
            } else {
                temp = src.substring(0, idx) + "<b>" + src.substring(idx, idx + chg.length()) + "</b>";
            }

            src = src.substring(idx + chg.length(), src.length());
        }

        return temp + makeBold( src, chg );
    }

    public String subStringBytes(String str, int byteLength) {
        // String 을 byte 길이 만큼 자르기.

        int retLength = 0;
        int tempSize = 0;
        int asc;

        if(str == null || "".equals(str) || "null".equals(str)){
            str = "";
        }

        int length = str.length();

        for (int i = 1; i <= length; i++) {
            asc = (int) str.charAt(i - 1);
            if (asc > 127) {
                if (byteLength >= tempSize + 2) {
                    tempSize += 2;
                    retLength++;
                } else {
                    return str.substring(0, retLength) + "...";
                }
            } else {
                if (byteLength > tempSize) {
                    tempSize++;
                    retLength++;
                }
            }
        }

        return str.substring(0, retLength);
    }

    private String getConvertedTitle( String srcStr, String chgStr ) throws Exception {
        String[] chgStrArr = chgStr.split("\\s");
        String[] srcStrArr = srcStr.split("\\s");

        String retStr = "";
        String temp = "";
        int idx = 0;

        for ( int i = 0 ; i < chgStrArr.length; i++ ) {

            temp = "";
            // srcStr = srcStr.replaceAll(chgStrArr[i], "<b>" + chgStrArr[i] + "</b>");

            for(int k = 0 ; k < srcStrArr.length; k++) {
                // idx = srcStrArr[k].toUpperCase().indexOf( chgStrArr[i].toUpperCase() );
                // if( idx > -1 ) {
                    // srcStrArr[k] = srcStrArr[k].substring(0, idx) + "<b>" + srcStrArr[k].substring( idx, idx + chgStrArr[i].length() ) + "</b>" + srcStrArr[k].substring(idx + chgStrArr[i].length(), srcStrArr[k].length());
                // }
                temp += makeBold( srcStrArr[k], chgStrArr[i] ) + " ";
            }
            retStr = temp;
            srcStrArr = retStr.split("\\s");
        }
        return retStr;
    }

    private String getConvertedContents( String srcStr, String chgStr, int maxLength ) throws Exception {
        int idx = 0;
        int minPos = 0;
        boolean bChecked = false;

        String[] chgStrArr = chgStr.split("\\s");
        String retStr = "";
        String temp = "";

        for ( int i = 0 ; i < chgStrArr.length; i++ ) {

            if (srcStr.length() > maxLength ) {

                idx = srcStr.indexOf( chgStrArr[i] );

                if ( idx != -1 ) {
                    if ( idx > maxLength && !bChecked ) {
                        minPos = (idx - 40 < 0 ) ? 0 : idx - 40;
                        srcStr = "..." + srcStr.substring( minPos, ( srcStr.length() > (minPos + maxLength ) ) ? (minPos + maxLength) : srcStr.length() ) + "...";
                        bChecked = true;
                    } else {
                        srcStr = srcStr.substring( 0, ( srcStr.length() > maxLength ) ? maxLength : srcStr.length() ) + "...";
                    }
                }
            }
        }

        String[] srcStrArr = srcStr.split("\\s");
        for ( int i = 0 ; i < chgStrArr.length; i++ ) {
            temp = "";
            // srcStr = srcStr.replaceAll(chgStrArr[i], "<b>" + chgStrArr[i] + "</b>");

            for(int k = 0 ; k < srcStrArr.length; k++) {
                // idx = srcStrArr[k].toUpperCase().indexOf( chgStrArr[i].toUpperCase() );
                // if( idx > -1 ) {
                    // srcStrArr[k] = srcStrArr[k].substring(0, idx) + "<b>" + srcStrArr[k].substring( idx, idx + chgStrArr[i].length() ) + "</b>" + srcStrArr[k].substring(idx + chgStrArr[i].length(), srcStrArr[k].length());
                // }
                // temp += (srcStrArr[k] + " ");
                temp += makeBold( srcStrArr[k], chgStrArr[i] ) + " ";
            }
            retStr = temp;
            srcStrArr = retStr.split("\\s");
        }
        return retStr;
    }

%>
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
