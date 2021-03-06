<%
//**********************************************************
// 1. 파일명 : za_RegisterMainCategoryPage.jsp
// 2. 프로그램명 : 홈페이지 메인화면 인기/추천 항목 등록 화면
// 3. 설명 : 홈페이지 메인화면 인기/추천 영역에 표시되는 카테고리 항목을 등록할 수 있는 페이지이다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : 2015-01-27
// 7. 수정 이력 :
//          2015-01-27 최초 작성
//***********************************************************
%>

<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<!DOCTYPE html>
<html>
<head>
<title>등록 - 메인 인기/추천 관리 - 홈페이지 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<style>
    #oLayoutTable {width: 360px; }
    #oLayoutTable tr td {text-align:center; font-weight:bold; font-size:32px;  border:1px solid; height:56px; color:#808080;}
</style>

<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/js/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
<!--
    /**
     * 메인 화면 인기/추천과정 카테고리 항목을 등록한다.
     */
    function fnRegisterMainCategory() {
        if( $("#oCategoryNm").val() === "" ) {
            alert("카테고리 명을 입력하세요.");
            $("#oCategoryNm").focus();
            return;

        } else if( $("#oCategoryType").val() === "" ) {
            alert("카테고리 타입을 선택하세요.");
            $("#oCategoryType").focus();
            return;

        } else if( $("input:radio[name=layoutType]:checked").length === 0 ) {
            alert("레이아웃 타입을 선택하세요.");
            $("input:radio[name=layoutType]").eq(0).focus();
            return;

        } else if( $("#oSortOrder").val() === "" ) {
            alert("순서를 선택하세요.");
            $("#oSortOrder").focus();
            return;

        } else if( $("input:radio[name=useYn]:checked").length === 0 ) {
            alert("사용여부를 선택하세요.");
            $("input:radio[name=useYn]").eq(0).focus();
            return;

        } else if (confirm("등록하시겠습니까?")) {
            $("#oMainCategoryRegForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryServlet").submit();

        } else {
            return;
        }
    }

    /**
     * 목록 화면으로 이동한다.
     */
    function fnGoToList() {
        $("#oProcess").val("retrieveList");
        $("#oMainCategoryRegForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryServlet").submit();
    }

//-->
</script>

</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="background-color:#fff; width:240px; font-size:20px; font-weight:bold;">메인 인기/추천 관리</td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br/>

            <h3 style="text-align:left; padding-left:16px">* 메인 카테고리 관리</h3>
            <table width="97%" cellspacing="1" cellpadding="5">
                <tr>
                    <td style="background-color:#fff; text-align:right;">* 모든 입력 항목은 필수입니다.</td>
                </tr>
            </table>
            <!-- 입력 form 시작 //-->

            <form id="oMainCategoryRegForm" name="mainCategoryRegForm" method="post" action="">
                <input type="hidden" id="oProcess" name="process" value="register" />
            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col style="width:15%;" />
                    <col style="width:*;" />
                </colgroup>
                <tr>
                    <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr>
                    <th height="25" class="table_title">카테고리 명</th>
                    <td class="table_02_2">
                        <input type="text" id="oCategoryNm" name="categoryNm" placeholder="카테고리 명을 입력하세요." style="width:80%;" />
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">카테고리 타입</th>
                    <td class="table_02_2">
                        <!-- 아래 항목에 대해 코드로 관리할 방안을 강구해야 함 //-->
                        <kocca:selectBox name="categoryType" id="oCategoryType" optionTitle="카테고리" type="code" codeValue="0120" isLoad="true"  />
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">레이아웃 타입</th>
                    <td class="table_02_2">
                        <table width="98%">
                            <colgroup>
                                <col style="40%" />
                                <col style="10%" />
                                <col style="50%" />
                            <colgroup>
                            <tr>
                                <td>
                                    <input type="radio" id="oLayoutTypeA" name="layoutType" value="A" /> <label for="oLayoutTypeA">A타입</label>
                                </td>
                                <td>&nbsp;</td>
                                <td>
                                    <input type="radio" id="oLayoutTypeB" name="layoutType" value="B" /> <label for="oLayoutTypeB">B타입</label>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="oLayoutTypeA"><img src="/images/admin/maincategory/main_category_template_A.png" style="width:320px; height:181px; border:1px solid;" /></label></td>
                                <td>&nbsp;</td>
                                <td><label for="oLayoutTypeB"><img src="/images/admin/maincategory/main_category_template_B.png" style="width:320px; height:181px; border:1px solid;" /></label></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">순서</th>
                    <td class="table_02_2">
                        <select id="oSortOrder" name="sortOrder" style="font-family:돋움; font-size:13px;">
                            <option value="">선택</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">사용여부</th>
                    <td class="table_02_2">
                        <label><input type="radio" id="oUseYnY" name="useYn" value="Y" checked="checked" /> 사용</label>
                        <label><input type="radio" id="oUseYnN" name="useYn" value="N" /> 미사용</label>
                    </td>
                </tr>
            </table>
            </form>
            <!-- 입력 form 끝 //-->

            <br/>
            <!-- 저장, 취소 버튼 시작 //-->
            <table width="11%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="javascript:fnRegisterMainCategory();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:fnGoToList()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
