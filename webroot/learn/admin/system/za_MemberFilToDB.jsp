<%
//**********************************************************
//  1. 제      목: 회원 FileToDB
//  2. 프로그램명 : za_MemberFileToDB.jsp
//  3. 개      요: 회원 FileToDB
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성:
//  7. 수      정: 정경진 2005.07.19
//***********************************************************
%>
<%@ page contentType="text/html;charset=MS949" %>
<%@ page errorPage="/learn/library/error.jsp" %>

<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if ( box == null ) {
        box = RequestManager.getBox(request);
    }
%>
<html>
<head>
<title>회원 엑셀등록 | 한국콘텐츠아카데미 관리자</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type = "text/javascript" src = "/script/cresys_lib.js"></script>
<script type = "text/javascript" src = "/script/jquery-1.3.2.min.js"></script>
<script type = "text/javascript" >

    /**
     * 회원 일괄 등록
     */
    function fnRegisterMember() {
        var checkedVal = Number( $("input:radio[name='actType']:checked").val() );
        var process = ( checkedVal == 1) ? "insertMember" : "updateMemberPassword";

        fnDoAction(process);
    }

    /**
     * 회원 등록 미리보기
     */
    function fnPreview() {
        var checkedVal = Number( $("input:radio[name='actType']:checked").val() );
        if ( checkedVal == 2) {
            alert("미리보기는 회원등록만 사용 가능합니다.");
            return;
        }
        fnDoAction("previewFileToDB");
    }

    function fnDoAction(p_process) {

        var r = $.trim($("[name='p_grcode']").val());

        if(r == "") {
            alert("교육그룹을 선택 하세요.");
            return;
        }

        if( blankCheck( document.form3.p_file.value) ) {
            alert("등록할 엑셀 파일을 선택해 주세요");
            return;
        }

        if ( document.form3.p_file.value.length > 0 ) {
            var data = document.form3.p_file.value;
            data = data.toUpperCase(data);
            if (data.indexOf(".XLS") < 0) {
                alert("등록 파일종류는 엑셀 파일(확장자 xls)만 가능합니다.");
                return;
            }
        }

        var confirmMsg = "";
        if (p_process == "insertMember"){
            confirmMsg ="회원 일괄 등록을 진행하시겠습니까?";
        }else if(p_process=="previewFileToDB"){
            confirmMsg = "미리보기를 진행하시겠습니까?";
        }else{
            confirmMsg ="회원 비밀번호 일괄 변경을 진행하시겠습니까?";

        }

        if ( confirm( confirmMsg ) ) {
            document.form3.action = "/servlet/controller.system.MemberAdminServlet";
            document.form3.p_process.value = p_process;
            document.form3.submit();
        }

    }

    $(document).ready( function() {
        $("input:radio[name='actType']").bind("click", function() {
            var checkVal = Number( $(this).val() );

            if (checkVal == 1) {
                $("#oWarnTable1").show();
                $("#oWarnTable2").hide();
            } else if (checkVal == 2) {
                $("#oWarnTable2").show();
                $("#oWarnTable1").hide();
            }
        });

        $("#oGrcode").bind("click", function() {
            $("[name='p_grcode']").val( $(this).val() );
        });
    });
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!--title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/system/unite_title18.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <!-- 찾아보기 시작 //-->
            <form name="form3" method="post" enctype="multipart/form-data">
                <input type="hidden" name="p_process" value="" />
                <input type="hidden" name="p_action" value="" />
                <input type="text" name="p_grcode" value="" />

            <br />

            <table width="97%" border="0" cellspacing="0" cellpadding="8" style="border:1px solid;">
                <colgroup>
                    <col style="width:80px;" />
                    <col style="width:120px;" />
                    <col style="width:80px;" />
                    <col style="width:*;" />
                    <col style="width:172px;" />
                </colgroup>
                <tr>
                    <th style="font-family:돋움; font-size:13px;">작업유형</th>
                    <td width="220">
                        <input type="radio" id="oActType1" name="actType" value="1" checked="checked" /><label for="oActType1">회원등록</label>
                        <input type="radio" id="oActType2" name="actType" value="2" /><label for="oActType2">비밀번호갱신</label>
                    </td>
                    <th style="font-family:돋움; font-size:13px;">교육그룹</th>
                    <td style="vertical-align:middle;">
                        <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 선택 --" type="sqlID" sqlID="selectBox.grcodeList" isLoad="true" />
                    </td>
                    <td align="center" rowspan="2">
                        <span id="oPreviewBtn"><a href="#" onclick="javascript:fnPreview()"><img src="/images/admin/button/btn_preview.gif" alt="미리보기" border="0"></a><span>
                        <a href="javascript:fnRegisterMember()"><img src="/images/admin/button/btn_apply.gif" alt="저장" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td align="left" colspan="4"><input name="p_file" type="file" class="input" style="width:636px; height:24px; border:1px solid;" /></td>
                </tr>
            </table>
            <!-- 찾아보기 끝 //-->
            <br />

            </form>

            <div id="oWarnTable1">
                <!-- 주의사항1 시작 //-->
                <table width="97%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td class="dir_txt"><b><font size="3">(주의사항 - 필독)</font></b></td>
                    </tr>
                    <tr>
                        <td height="8"></td>
                    </tr>

                    <tr>
                        <td height="20" class="dir_txt">1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것.</td>
                    </tr>
                    <tr>
                        <td height="20" class="dir_txt">
                            2. 엑셀파일 작성시 샘플 파일을 참고할 것.
                            ( <a href = "/servlet/controller.library.DownloadServlet?p_savefile=\upload\bulletin\member_sample.xls&p_realfile=member_sample.xls"><b>샘플파일</b></a> )
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <td height="20" class="dir_txt">3. 코드참조 (직업 : 0104, 종사분야 : 0103, 가입경로 : 0105)</td>
                    </tr>
                    //-->
                </table>
                <!-- 주의사항1 끝 //-->
                <br />

                <table width="98%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="left">샘플파일</td>
                    </tr>
                    <tr>
                        <td><img src="/images/admin/system/MemberExcel.jpg" border="0"></td>
                    </tr>
                </table>
            </div>

            <div id="oWarnTable2" style="display:none;">
            <!-- 주의사항2 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="dir_txt"><b><font size="3">(주의사항 - 필독)</font></b></td>
                </tr>
                <tr>
                    <td height="8"></td>
                </tr>

                <tr>
                    <td height="20" class="dir_txt">1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것.</td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">
                        2. 엑셀파일 작성시 샘플 파일을 참고할 것.
                        ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=\upload\bulletin\member_password_sample.xls&p_realfile=member_password_sample.xls'><b>샘플파일</b></a> )
                    </td>
                </tr>
            </table>
            <!-- 주의사항1 끝 //-->
            <br />

            <table width="98%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align='left'>샘플파일</td>
                </tr>
                <tr>
                    <td><img src="/images/admin/system/member_password_excel.jpg" border="0"></td>
                </tr>
            </table>
            </div>

        </td>
    </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
