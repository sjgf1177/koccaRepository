<%
//**********************************************************
// 1. 프로그램명: CMU 게시판 상세보기 화면
// 2. 파  일  명: za_CMUBoard_R.jsp
// 3. 개      요: CMU 게시판 상세보기 화면
// 4. 환      경: JDK 1.?
// 5. 저      자: saderaser
// 6. 생  성  일: 2014-08-21
// 7. 변 경 내 역:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>

<%@ page import="com.credu.library.*" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int seq = box.getInt("seq");
    int pageNo = box.getInt("p_pageno");
    int pageSize = box.getInt("p_pagesize");

    String searchType = box.getString("searchType");
    String searchKeyword = box.getString("searchKeyword");

    String userid = box.getSession("userid");

    DataBox cmuBoard = (DataBox)request.getAttribute("cmuBoard");

    int viewCount = 0;
    String title = "";
    String cont = "";
    String regId = "";
    String regNm = "";
    String regDt = "";
    String useYn = "";
    String fileNm = "";
    String saveFileNm = "";
    String fileSize = "";
    String imgNm = "";
    String saveImgNm = "";

    if (cmuBoard != null) {
        title = cmuBoard.getString("d_title");
        cont = cmuBoard.getString("d_cont");
        viewCount = cmuBoard.getInt("d_view_count");
        regId = cmuBoard.getString("d_reg_id");
        regNm = cmuBoard.getString("d_reg_nm");
        regDt = cmuBoard.getString("d_reg_dt");
        useYn = cmuBoard.getString("d_use_yn");
        fileNm = cmuBoard.getString("d_file_nm");
        saveFileNm = cmuBoard.getString("d_save_file_nm");
        fileSize = cmuBoard.getString("d_file_size");
        imgNm = cmuBoard.getString("d_img_nm");
        saveImgNm = cmuBoard.getString("d_save_img_nm");
        regDt   = FormatDate.getFormatDate(regDt,"yyyy/MM/dd");
    }
%>

<html>
<head>
<title>상세화면 | CMU 게시판 | 홈페이지 | 관리자 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--

    /**
     * 수정 화면으로 이동
     */
    function fnGoToModifyPage() {
        $("#oProcess").val("updatePage");
        $("#oCMUBoardForm").attr("action", "/servlet/controller.homepage.CMUBoardAdminServlet");
        $("#oCMUBoardForm").submit();
    }

    /**
     * 게시물 삭제
     */
    function fnDeleteArticle() {
        if ( confirm("삭제된 자료는 복구할 수 없습니다.\n정말로 삭제하시겠습니까?") ) {
            $("#oProcess").val("delete");
            $("#oCMUBoardForm").attr("action", "/servlet/controller.homepage.CMUBoardAdminServlet");
            $("#oCMUBoardForm").submit();
        } else {
            return;

        }
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
<form id="oCMUBoardForm" name="cmuBoardForm" method="post">
    <input type="hidden" id="oProcess" name="p_process" value="" />
    <input type="hidden" id="oPageNo" name="p_pageno" value="<%= pageNo %>" />
    <input type="hidden" id="oPageSize" name="p_pagesize" value="<%= pageSize %>" />
    <input type="hidden" id="oSearchType" name="searchType" value="<%= searchType %>">
    <input type="hidden" id="oSearchKeyword" name="searchKeyword" value="<%= searchKeyword %>">
    <input type="hidden" id="oSeq" name="seq" value="<%= seq %>">
    <input type="hidden" id="oSaveFileNm" name="saveFileNm" value="<%= saveFileNm %>">
    <input type="hidden" id="oSaveImgNm" name="saveImgNm" value="<%= saveImgNm %>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="padding-top:20px;"><h2>CMU 국비지원게시판</h2></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <!-- 소제목 시작 //-->
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="250" style="font-weight:bold; font-size:11pt;">CMU 게시물 상세</td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!-- 소제목 끝 //-->

            <!-- 게시물 내용 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>제 목</strong></td>
                    <td height="25" class="table_02_2" colspan="3"><%= title %></td>
                </tr>
                <tr class="table_02_2">
                    <td width="15%" class="table_title"><strong>작성자</strong></td>
                    <td width="35%" height="25" class="table_02_2"><%= regNm %></td>
                    <td height="25" class="table_title" ><strong>작성일</strong></td>
                    <td height="25" class="table_02_2"><%= regDt %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>조회수</strong></td>
                    <td width="35%" height="25" class="table_02_2"><%= viewCount %></td>
                    <td width="15%" class="table_title"><strong>사용여부</strong></td>
                    <td height="25" class="table_02_2"><%= useYn %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>내용</strong></td>
                    <td height="25" class="table_02_2" colspan="3" style="line-height:18px;"><%= cont %></td>
                </tr>
                <tr>
                    <td class="table_title" align="center">첨부파일</td>
                    <td class="table_02_2" align="left" colspan="4">
<%
        if ( !fileNm.equals("") ) {
%>
                        <a href="javascript:fnDownlaodFile('<%= saveFileNm.replace("\\", "/") %>', '<%= fileNm %>');"><%= fileNm %></a>
<%
        }
%>
                    </td>
                </tr>
                <tr>
                    <td class="table_title" align="center">썸네일</td>
                    <td class="table_02_2" align="left" colspan="4">
<%
        if ( !imgNm.equals("") ) {
%>
                        <a href="javascript:fnDownlaodFile('<%= saveImgNm.replace("\\", "/") %>', '<%= imgNm %>');"><%= imgNm %></a>
<%
        }
%>
                    </td>
                </tr>

            </table>
            <!-- 게시물 내용 끝 //-->

            <br />

            <!-- 수정, 삭제, 리스트 버튼 시작 //-->
            <table width="16%" border="0" cellspacing="0" cellpadding="0">
                <tr>
<%
        String adminAuth = box.getSession("gadmin");
        if ( userid.equals(regId) || !adminAuth.equals("ZZ") ) {
%>
                    <td align="center"><a href="javascript:fnGoToModifyPage()"><img src="/images/admin/button/btn_modify.gif" border="0" alt="수정" /></a>&nbsp;</td>
                    <td align="center"><a href="javascript:fnDeleteArticle()"><img src="/images/admin/button/btn_del.gif" border="0" alt="삭제" /></a>&nbsp;</td>
<%
        }
%>
                    <td align="center"><a href="javascript:history.back();"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
                </tr>
            </table>
            <!-- 수정, 삭제, 리스트 버튼 끝 //-->
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
