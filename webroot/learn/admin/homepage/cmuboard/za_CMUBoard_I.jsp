<%
//**********************************************************
// 1. 프로그램명: CMU 게시판 등록 화면
// 2. 파  일  명: za_CMUBoard_I.jsp
// 3. 개      요: CMU 게시판 등록 화면
// 4. 환      경: JDK 1.?
// 5. 저      자: saderaser
// 6. 생  성  일: 2014-08-21
// 7. 변 경 내 역:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage="/learn/library/error.jsp" %>

<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String process = box.getString("p_process");

    int seq = box.getInt("seq");

    int pageNo = box.getInt("p_pageno");
    int pageSize = box.getInt("p_pagesize");

    String userNm = box.getSession("name");
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    
    String v_tem_url_name = v_server + v_tem_url + v_tem_name; 
%>

<html>
<head>
<title>등록화면 | CMU 게시판 | 홈페이지 | 관리자 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" >
<!--
    /**
     * 게시물 등록
     */
    function fnInsertCMU() {

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

        if (document.form1.Wec.Value.length <= 300) {
            alert("내용을 입력하세요");
            return false;
        }

        if($("#oImgFile").val() == "") {
            alert("썸네일을 추가하십시요.");
            $("#oImgFile").focus();
            return false;
        }

        if ( confirm("등록하시겠습니까?") ) {
            $("#oProcess").val("insert");
            $("#oform1").attr("action", "/servlet/controller.homepage.CMUBoardAdminServlet");
            $("#oform1").submit();
        } else {
            return false;
        }

        return;
    }

    /**
     * 등록 취소 후 목록으로 이동
     */
    function fnCancel() {
        history.back();
        /*
        $("#oform1").attr("action", "/servlet/controller.homepage.CMUBoardAdminServlet");
        $("#oProcess").val("list");
        $("#oform1").submit();
        */
    }


//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form id="oform1" name="form1" method="post" enctype="multipart/form-data" onsubmit="return fnInsertCMU();">
    <input type="hidden" id="oProcess"  name="p_process" value="<%= process %>" />
    <input type="hidden" id="oPageNo"   name="p_pageno" value="<%= pageNo %>" />
    <input type="hidden" id="oPageSize" name="p_pagesize" value="<%= pageSize %>" />
    <input type="hidden" id="cont"      name="cont" value="" />

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
                    <td width="250" style="font-weight:bold; font-size:11pt;">CMU 게시물 등록</td>
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
                    <td height="25" class="table_title" ><strong>등록일</strong></td>
                    <td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>사용여부</strong></td>
                    <td width="35%" height="25" class="table_02_2">
                        <select id="oUseYn" name="useYn">
                            <option value="Y">사용</option>
                            <option value="N">미사용</option>
                        </select>
                    </td>
                </tr>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>제목</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="text" id="oTitle" name="title" class="input" style="width:680px;" />
                    </td>
                </tr>
                <tr id="divonoff">
                    <td height="25" class="table_title" ><strong>내용</strong></td>
                    <td height="25" class="table_02_2">
                        <!-- 나모 액티브 스퀘어  시작 -->
                        <script language="javascript" src="/script/user_patch.js"></script>
                        <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";</script>
                        <script language="javascript">object_namoActivepatch('100%','500');</script>
                        <!-- 나모 액티브 스퀘어  종료 -->
                    </td>
                </tr>
                <tr>
                    <td width="16%" class="table_title"><b>첨부파일</b></td>
                    <td id="sTd" width="81%" class="table_02_2">
                        <input type="file" id="oAttachFile" name="p_file" class="input" style="width:680px;" />
                    </td>
                </tr>
                <tr>
                    <td width="16%" class="table_title"><b>썸네일</b></td>
                    <td id="sTd" width="81%" class="table_02_2">
                        <input type="file" id="oImgFile" name="p_imgFile" class="input" style="width:680px;" />
                    </td>
                </tr>
          </table>
            <!-- 게시물 입력 폼 끝 //-->

            <br />
            <!-- 저장, 취소 버튼 시작----------------->
            <table width="11%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><input type="image" src="/images/admin/button/btn_save.gif" /></td>
                    <td align="center"><a href="javascript:fnCancel();"><img src="/images/admin/button/btn_cancel.gif" border="0" alt="취소" /></a></td>
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
