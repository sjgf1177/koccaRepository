<%
//**********************************************************
//  1. 제     목: PROPOSE MEMBER LIST
//  2. 프로그램명: za_StudentMember_L.jsp
//  3. 개     요: 신청명단조회
//  4. 환     경: JDK 1.3
//  5. 버     젼: 1.0
//  6. 작     성: 2004-02-04
//  7. 수     정:
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
<!docTYpe html>
<html>
<head>
<title>외부교육 일괄입과 - 신청입과 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--

    // top.document.title = document.title;

    $(function() {
        $("#oGrcode").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.grYearList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrYear
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oGyear").bind("change", function(){
            var grcode = $("#oGrcode").val();
            var param = "type=sqlID&sqlID=selectBox.grSeqList&param=" + grcode + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrSeq
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

    });

    /**
     * 교육 그룹별 연도 selectbox 지정
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oGyear").append("<option value=\"\">-- 선택 --</option>");
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGyear").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 교육 그룹 연도별 교육차수 selectbox 지정
     */
    function fnSetGrSeq( result ) {
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGrseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

    function fnRegisterExternalEdu() {
        if( $("#oGrcode").val() == "" ) {
            alert("교육그룹을 선택하세요.");
            $("#oGrcode").focus();
            return;
        } else if( $("#oGyear").val() == "" ) {
            alert("연도를 선택하세요.");
            $("#oGyear").focus();
            return;
        } else if( $("#oGrseq").val() == "" ) {
            alert("교육차수를 선택하세요.");
            $("#oGrseq").focus();
            return;
        } else if( $("#oEduFile").val() == "" ) {
            alert("엑셀 파일을 등록해주세요.");
            $("#oEduFile").focus();
            return;
        }

        var fileExt = "";
        var idx = $("#oEduFile").val().lastIndexOf(".");

        fileExt = $("#oEduFile").val().substring(idx + 1, $("#oEduFile").val().length);

        if (fileExt.toLowerCase() != "xls" ) {
            alert("확장자가 xls인 엑셀 파일을 등록해 주세요.");
            $("#oEduFile").focus();
            return;
        }

        if ( confirm("선택한 내용으로 등록하시겠습니까?") ) {
            $("#oProcess").val("register");
            $("#oExternalEduForm").attr("action", "/servlet/controller.propose.ExternalEduServlet").submit();
        } else {
            return;
        }
    }
//-->
</script>
</head>
<body bgcolor="#ffffff" text="#000000" style="margin-left:0px; margin-top:0px;">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="font-size:20px; font-weight:bold; padding-top:12px;">외부교육 일괄입과</td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br/>

            <form id="oExternalEduForm" name="externalEduForm" method="post" enctype="multipart/form-data">
                <input type="hidden" id="oProcess" name="process" value="" />
            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col style="width:12%" />
                    <col style="width:32%" />
                    <col style="width:12%" />
                    <col style="width:44%" />
                </colgroup>
                <tr>
                    <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                    <th class="table_title">교육그룹</th>
                    <td class="table_02_2" colspan="3">
                        <kocca:selectBox name="grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" isLoad="true" />
                    </td>
                </tr>
                <tr>
                    <th class="table_title">연도</th>
                    <td class="table_02_2">
                        <select name="gyear" id="oGyear">
                            <option value="">-- 선택 --</option>
                        </select>
                    </td>
                    <th class="table_title">교육차수</th>
                    <td class="table_02_2">
                        <select name="grseq" id="oGrseq">
                            <option value="">-- 선택 --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th class="table_title">엑셀 파일</th>
                    <td class="table_02_2" colspan="3">
                        <input type="file" id="oEduFile" name="p_eduFile" style="width:570px; border:1px solid;"/>
                        <a href="/servlet/controller.library.DownloadServlet?p_savefile=\upload\bulletin\b2b_edu_list_sample.xls&p_realfile=b2b_edu_list_sample.xls" style="font-weight:bold; color:#D02D9C;">샘플 파일 다운로드</a>
                    </td>
                </tr>
            </table>
            </form>
            <br/>

            <!-- 저장, 취소 버튼 시작 //-->
            <table width="11%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="javascript:fnRegisterExternalEdu();"><img src="/images/admin/button/btn_register.gif" border="0"></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->

            <!-- 주의사항 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="dir_txt"><b><font size="3">(주의사항 - 필독)</font></b></td>
                </tr>
                <tr>
                    <td height="8"></td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">1. <font color="#e60873"><a href="/servlet/controller.library.DownloadServlet?p_savefile=\upload\bulletin\b2b_edu_list_sample.xls&p_realfile=b2b_edu_list_sample.xls" style="font-weight:bold; color:#D02D9C;">샘플 파일 다운로드</a>를 클릭하여 아래의 그림과 같은 양식의 파일을 다운로드 한다.</font></td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">2. <font color="#E60873">다운로드 된 양식을 기준으로 자료를 작성한 후 Excel 문서로 저장한다.</font></td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">3. 등록 자료 작성 시 <font color="#A661D7">[<b>성별, 과정코드, 수료여부</b>]</font>의 순서를 반드시 지켜야 한다.</td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">4. 자료 작성 완료 후, 상단의 등록 폼에서 교육그룹, 연도, 교육차수를 반드시 선택하고, 엑셀 파일을 선택한다.</td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">5. 내용을 확인 후 등록 버튼을 클릭하여 일괄입과 처리를 진행한다.</td>
                </tr>
            </table>
            <!-- 주의사항 끝 //-->

            <br/>
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="left">
                        샘플 => <a href="/servlet/controller.library.DownloadServlet?p_savefile=\upload\bulletin\b2b_edu_list_sample.xls&p_realfile=b2b_edu_list_sample.xls" style="font-weight:bold; color:#f00;">다운로드</a>
                    </td>
                </tr>
                <tr>
                    <td><img src="/images/admin/propose/b2b_edu_list_sample.jpg" border="0"></td>
                </tr>
            </table>
            <br/><br/>

        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
