<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage= "/learn/library/error.jsp" %>
<%@ page import= "java.util.*" %>
<%@ page import= "com.credu.homepage.*" %>
<%@ page import= "com.credu.library.*" %>
<%@ page import= "com.credu.common.*" %>
<%@ page import= "com.credu.course.*" %>
<%@ page import= "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process = box.getString("p_process");

    int v_seq = box.getInt("p_seq");

    int seq = 0;
    String onoff_flag= "";
    String title= "";
    String img_nm = "";
    String save_img_nm= "";
    String imgSize = "";
    String explain= "";
    String url= "";
    String url_target= "";
    int sort_order= 0;
    String start_dt= "";
    String end_dt = "";
    String reg_dt = "";
    String reg_id = "";
    String mod_dt = "";
    String mod_id = "";


    DataBox dbox = (DataBox)request.getAttribute("bannerData");

    if (dbox != null) {
        seq         = dbox.getInt("d_seq");
        onoff_flag  = dbox.getString("d_onoff_flag");
        title       = dbox.getString("d_title");
        img_nm      = dbox.getString("d_img_nm");
        save_img_nm = dbox.getString("d_save_img_nm");
        imgSize     = dbox.getString("d_img_size");
        explain     = dbox.getString("d_explain");
        url         = dbox.getString("d_url");
        url_target  = dbox.getString("d_url_target");
        sort_order  = dbox.getInt("d_sort_order");
        start_dt    = dbox.getString("d_start_dt").substring(0,4)+"/"+dbox.getString("d_start_dt").substring(4,6)+"/"+dbox.getString("d_start_dt").substring(6,8);
        end_dt      = dbox.getString("d_end_dt").substring(0,4)+"/"+dbox.getString("d_end_dt").substring(4,6)+"/"+dbox.getString("d_end_dt").substring(6,8);
        reg_dt      = dbox.getString("d_reg_dt");
        reg_id      = dbox.getString("d_reg_id");
        mod_dt      = dbox.getString("d_mod_dt");
        mod_id      = dbox.getString("d_mod_id");

    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/css" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/script/jquery-ui-1.10.4.js"></script>

<script type="text/javascript">
<!--
    $(document).ready( function() {

        //$(document).title = "검색어 통계 | 통계 | 한국콘텐츠아카데미 관리자";

        // 달력 초기화
        $("#oPostStartDate").datepicker({
            showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
            buttonImage: "/images/portal/ico/ico_calendar.gif", // 버튼 이미지
            buttonText: "게시 시작일자",
            buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
            changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
            changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
            // minDate: "-7d", // 현재날짜로부터 100년이전까지 년을 표시한다.
            // maxDate: "30d", // 현재날짜로부터 100년이전까지 년을 표시한다.
            nextText: "다음 달", // next 아이콘의 툴팁.
            prevText: "이전 달", // prev 아이콘의 툴팁.
            numberOfMonths: [1,1], // 한번에 얼마나 많은 월을 표시할것인가. [2,3] 일 경우, 2(행) x 3(열) = 6개의 월을 표시한다.
            stepMonths: 1, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가.
            // yearRange: "-15:", // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
            showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다.
            currentText: "오늘" , // 오늘 날짜로 이동하는 버튼 패널
            closeText: "닫기",  // 닫기 버튼 패널
            dateFormat: "yy/mm/dd", // 텍스트 필드에 입력되는 날짜 형식.
            showAnim: "slideDown", //애니메이션을 적용한다.
            showMonthAfterYear: true , // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"], // 요일의 한글 형식.
            monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"], // 월의 한글 형식.

            onClose: function( selectedDate ) {
                $("#oPostEndDate" ).datepicker( "option", "minDate", selectedDate );
            }
        });

        $("#oPostEndDate").datepicker({
            showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
            buttonImage: "/images/portal/ico/ico_calendar.gif", // 버튼 이미지
            buttonText: "게시 종료일자",
            buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
            changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
            changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
            // minDate: "0d", // 현재날짜로부터 100년이전까지 년을 표시한다.
            // maxDate: "30d",
            nextText: "다음 달", // next 아이콘의 툴팁.
            prevText: "이전 달", // prev 아이콘의 툴팁.
            numberOfMonths: [1,1], // 한번에 얼마나 많은 월을 표시할것인가. [2,3] 일 경우, 2(행) x 3(열) = 6개의 월을 표시한다.
            stepMonths: 1, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가.
            //yearRange: "-15:", // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
            showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다.
            currentText: "오늘" , // 오늘 날짜로 이동하는 버튼 패널
            closeText: "닫기",  // 닫기 버튼 패널
            dateFormat: "yy/mm/dd", // 텍스트 필드에 입력되는 날짜 형식.
            showAnim: "slideDown", //애니메이션을 적용한다.
            showMonthAfterYear: true , // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"], // 요일의 한글 형식.
            monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"], // 월의 한글 형식.

            onClose: function( selectedDate ) {
                $("#oPostStartDate" ).datepicker( "option", "maxDate", selectedDate );
            }
        });

        $("#oPostStartDate" ).datepicker( "option", "maxDate", "<%=end_dt%>" );

    });

    /**
     * 배너 수정/update
     */
    function fnSaveBanner(){
        if ($("#oTitle").val() == "") {
            alert("제목을 입력하세요.");
            $("#oTitle").focus();
            return ;
        }
        if ($("#oOnoffFlag").val() == "") {
            alert("온/오프 라인을 선택하십시오.");
            $("#oOnoffFlag").focus();
            return ;
        }
        if ($("#oPostStartDate").val() == "" || $("#oPostEndDate").val() == "") {
            alert("게시기간을 입력하십시오.");
            if ($("#oPostStartDate").val() == ""){
                $("#oPostStartDate").focus();
                return ;
            }else{
                $("#oPostEndDate").focus();
                return ;
            }            
        }
        if ($("#oExplain").val() == "") {
            alert("배너 설명을 입력하십시오.");
            $("#oExplain").focus();
            return ;
        }
        if ($("#oLinkUrl").val() == "") {
            alert("배너의 링크 Url을 입력하십시오.");
            $("#oExplain").focus();
            return ;
        }
        if ($("#oLinkUrl").val() == "") {
            alert("배너의 링크 Url을 입력하십시오.");
            $("#oExplain").focus();
            return ;
        }
        if ($("#oTarget").val() == "") {
            alert("연결될 타겟 창을 선택하십시오.");
            $("#oTarget").focus();
            return ;
        }
        if (confirm('정말 수정 하시겠습니까?')) {
            document.form1.action = "/servlet/controller.homepage.BannerAdminServlet";
            document.form1.submit();
        }
    }

    /**
     * 취소/리스트 바로가기
     */
    function fnList(){
        document.form1.action = "/servlet/controller.homepage.BannerAdminServlet";
        document.form1.p_process.value ="selectList"
        document.form1.submit();
    }

    /**
     * 첨부파일 다운로드
     */
    function fnDownlaodFile( saveFileNm, fileNm ) {
        var url = "/servlet/controller.library.DownloadServlet";
        var param = "?p_savefile=" + saveFileNm + "&p_realfile=" + fileNm;

        location.href = url + param;
    }

//-->
</script>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form ID="oSaveForm" name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "update">
    <input type = "hidden" name="p_seq"    value = "<%=v_seq%>">
    <input type = "hidden" id="oOrgSaveImgNm" name="orgSaveImgNm" value="<%= save_img_nm %>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="padding-top:20px;"><h2>메인배너 수정</h2></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <br />
            <!-- 메인배너 관리 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col width="120px;" />
                    <col width="200px;" />
                    <col width="120px;" />
                    <col width="*;" />
                </colgroup>
                <tr>
                    <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                    <td height="25" class="table_title"><strong> 제 목 </strong></td>
                    <td class="table_02_2" colspan="3">
                          <input type="text" id="oTitle" name="title" value="<%=title%>"size="100" class="input" maxlength="100">
                    </td>
                </tr>
                <tr>
                    <td height="25" class="table_title"><strong>온 / 오프 구분</strong></td>
                    <td class="table_02_2" colspan="3">
                        <select id="oOnoffFlag" name="onoffFlag">
                            <option value="">-- 선택 --</option>
                            <option value="ON" <%= onoff_flag.equals("ON") ? "selected='selected'" : "" %>>온라인</option>
                            <option value="OFF" <%= onoff_flag.equals("OFF") ? "selected='selected'" : "" %>>오프라인</option>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td height="25" class="table_title"><strong>게시기간</strong></td>
                    <td class="table_02_2" colspan="3">
                        <input type="text" id="oPostStartDate" name="postStartDate" value="<%=start_dt%>" class="input" maxlength="10" size="10" /> ~
                        <input type="text" id="oPostEndDate" name="postEndDate" value="<%=end_dt%>" class="input" maxlength="10" size="10" />
                    </td>
                </tr>
                <tr>
                    <td height="25" class="table_title"><strong>배너 이미지</strong></td>
                    <td class="table_02_2" colspan="3">
                          <input type="file" id="oImgFile" name="imgFile" size="84" class="input" />
<%
        if ( !img_nm.equals("") ) {
%>
                        <br/><br/>
                        <a href="javascript:fnDownlaodFile('<%= save_img_nm.replace("\\", "/") %>', '<%= img_nm %>');"><b><%= img_nm %></b></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        (새로운 파일을 등록하면 기존 파일은 삭제됩니다.)
<%
        }
%>
                    </td>
                </tr>
<!--
                <tr id="oVodPathTr">
                    <td height="25" class="table_title"><strong>배너크기 </strong></td>
                    <td class="table_02_2" colspan="3">
                        <input type="radio" name="imgSize" value='W3' <%= imgSize.equals("W3") ? "checked" : "" %>>860* 276&nbsp;
                        <input type="radio" name="imgSize" value='W2' <%= imgSize.equals("W2") ? "checked" : "" %>>570*276&nbsp;
                        <input type="radio" name="imgSize" value='W1' <%= imgSize.equals("W1") ? "checked" : "" %>>280*276&nbsp;
                    </td>
                </tr>
-->
                <tr>
                    <td height="25" class="table_title"><strong>배너설명(alt tag)</strong></td>
                    <td class="table_02_2" colspan="3">
                        <textarea id="oExplain" name="explain" cols="80" rows="5" style="width: 70%; height: 150;" ><%=explain%></textarea>
                    </td>
                </tr>
                <tr id="oVodUrlTr">
                    <td height="25" class="table_title"><strong>URL(링크)</strong></td>
                    <td class="table_02_2" colspan="3">
                          <input type="text" id="oLinkUrl" name="linkUrl" size="100" class="input" maxlength="400" value="<%=url%>" />
                    </td>
                </tr>
                <tr>
                    <td height="25" class="table_title"><strong>URL 타겟</strong></td>
                    <td class="table_02_2" colspan="3">
                        <select id="oTarget" name="target">
                            <option value="">-- 선택 --</option>
                            <option value="_blank" <%= url_target.equals("_blank") ? "selected='selected'" : "" %>>새창</option>
                            <option value="_self" <%= url_target.equals("_self") ? "selected='selected'" : "" %>>현재창</option>
                        </select>
                    </td>
                </tr>
            </table>
            <!-- 메인배너 입력 양식 끝 //-->

    <br />
    <!-- 버튼 시작 //-->
    <table width="11%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td align="center"><a href="javascript:fnSaveBanner();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td align="center"><a href="javascript:fnList()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
    </table>
    <!-- 버튼 끝 //-->


      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
