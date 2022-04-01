<%
//**********************************************************
// 1. 프로그램명: 아카데미 이야기 수정 화면
// 2. 파  일  명: za_TongBoard_U.jsp
// 3. 개      요: 아카데미 이야기 수정 화면
// 4. 환      경: JDK 1.?
// 5. 저      자: saderaser
// 6. 생  성  일: 2014-08-21
// 7. 변 경 내 역:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>

<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.library.FormatDate" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int seq = box.getInt("seq");
    int pageNo = box.getInt("p_pageno");
    int pageSize = box.getInt("p_pagesize");

    String searchType = box.getString("searchType");
    String searchKeyword = box.getString("searchKeyword");

    String userid = box.getSession("userid");

    DataBox tongboard = (DataBox)request.getAttribute("tongboard");

    String userNm = box.getSession("name");

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

    if (tongboard != null) {
        title = tongboard.getString("d_title");
        cont = tongboard.getString("d_cont");
        viewCount = tongboard.getInt("d_view_count");
        regId = tongboard.getString("d_reg_id");
        regNm = tongboard.getString("d_reg_nm");
        regDt = tongboard.getString("d_reg_dt");
        useYn = tongboard.getString("d_use_yn");
        fileNm = tongboard.getString("d_file_nm");
        saveFileNm = tongboard.getString("d_save_file_nm");
        fileSize = tongboard.getString("d_file_size");
        imgNm = tongboard.getString("d_img_nm");
        saveImgNm = tongboard.getString("d_save_img_nm");

    }
    // 웹에디터 템플릿 설정
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;
    
%>

<html>
<head>
<title>수정화면 | 아카데미 이야기 | 홈페이지 | 관리자 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--

    /**
     *
     */
    function fnUpdateTong() {

        document.form1.cont.value = document.form1.Wec.MIMEValue;
        document.form1.cont.value.replace("&","&amp;");

        if ($("#oTitle").val() == "") {
            alert("제목을 입력하세요.");
            $("#oTitle").focus();
            return false;
        } 
       /*
       else  if ($("#oCont").val() == "") {
            alert("내용을 입력하세요.");
            $("#oCont").focus();
            return false;
        }
        */ 
        //alert(document.form1.Wec.MIMEValue.length);//416
        if (blankCheck(document.form1.cont.value)) {
            alert("내용을 입력하세요");
            document.form1.p_adcontent.focus();
            return;
        }
        
        if ( confirm("수정하시겠습니까?") ) {
            $("#oProcess").val("update");
            $("#oform1").attr("action", "/servlet/controller.homepage.TongBoardAdminServlet");
            $("#oform1").submit();
        } else {
            return false;
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
<form id="oform1" name="form1" method="post" enctype="multipart/form-data" onsubmit="return fnUpdateTong();">
    <input type="hidden" id="oProcess" name="p_process" value="" />
    <input type="hidden" id="oPageNo" name="p_pageno" value="<%= pageNo %>" />
    <input type="hidden" id="oPageSize" name="p_pagesize" value="<%= pageSize %>" />
    <input type="hidden" id="oSearchType" name="searchType" value="<%= searchType %>">
    <input type="hidden" id="oSearchKeyword" name="searchKeyword" value="<%= searchKeyword %>">
    <input type="hidden" id="oSeq" name="seq" value="<%= seq %>">
    <input type="hidden" id="oOrgSaveFileNm" name="orgSaveFileNm" value="<%= saveFileNm %>">
    <input type="hidden" id="oOrgSaveImgNm" name="orgSaveImgNm" value="<%= saveImgNm %>">
    <input type="hidden" id="cont" name="cont" value="<%=StringUtil.convertHtmlchars(cont)%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="padding-top:20px;"><h2>아카데미 이야기</h2></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <!-- 소제목 시작 //-->
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="250" style="font-weight:bold; font-size:11pt;">아카데미 이야기 게시물 수정</td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!-- 소제목 끝 //-->

            <!-- 게시물 입력 폼 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>작성자</strong></td>
                    <td height="25" class="table_02_2"><%= userNm %></td>
                </tr>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>수정일</strong></td>
                    <td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>사용여부</strong></td>
                    <td width="35%" height="25" class="table_02_2">
                        <select id="oUseYn" name="useYn">
                            <option value="Y" <%= useYn.equals("Y") ? "selected='selected'" : "" %>>사용</option>
                            <option value="N" <%= useYn.equals("N") ? "selected='selected'" : "" %>>미사용</option>
                        </select>
                    </td>
                </tr>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>제목</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="text" id="oTitle" name="title" class="input" style="width:680px;" value="<%= title %>" />
                    </td>
                </tr>
                <tr id="divonoff">
                    <td height="25" class="table_title" ><strong>내용</strong></td>
                    <td height="25" class="table_02_2">
                        <!-- 나모 액티브 스퀘어  시작 -->
                        <script language="javascript" src="/script/user_patch.js"></script>
                        <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted("cont");</script>
                        <script language="javascript">object_namoActivepatch('100%','500');</script>
                        <!-- 나모 액티브 스퀘어  종료 -->
                    </td>
                </tr>
                <tr>
                    <td width="16%" class="table_title"><b>첨부파일</b></td>
                    <td id="sTd" width="81%" class="table_02_2" style="line-height:20pt;">
                        <input type="file" id="oAttachFile" name="p_file" class="input" style="width:680px;" />
<%
        if ( !fileNm.equals("") ) {
%>
                        <br/>
                        <a href="javascript:fnDownlaodFile('<%= saveFileNm.replace("\\", "/") %>', '<%= fileNm %>');"><b><%= fileNm %></b></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        (새로운 파일을 등록하면 기존 파일은 삭제됩니다.)
<%
        }
%>

                    </td>
                </tr>
                <tr>
                    <td width="16%" class="table_title"><b>썸네일</b></td>
                    <td id="sTd" width="81%" class="table_02_2" style="line-height:20pt;">
                        <input type="file" id="oImgFile" name="p_imgFile" class="input" style="width:680px;" />
<%
        if ( !imgNm.equals("") ) {
%>
                        <br/>
                        <a href="javascript:fnDownlaodFile('<%= saveImgNm.replace("\\", "/") %>', '<%= imgNm %>');"><b><%= imgNm %></b></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        (새로운 파일을 등록하면 기존 파일은 삭제됩니다.)
<%
        }
%>

                    </td>
                </tr>
            </table>
            <!-- 게시물 입력 폼 끝 //-->

            <br />
            <!-- 저장, 취소 버튼 시작----------------->
            <table width="11%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><input type="image" src="/images/admin/button/btn_save.gif" alt="저장" /></td>
                    <td align="center"><a href="javascript:history.back();"><img src="/images/admin/button/btn_cancel.gif" border="0" alt="취소" /></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
