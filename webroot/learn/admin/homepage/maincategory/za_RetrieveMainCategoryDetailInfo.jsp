<%
//**********************************************************
// 1. 파일명 : za_RetrieveMainCategoryDetailInfo.jsp
// 2. 프로그램명 : 하위등록 과정 상세 화면
// 3. 설명 : 홈페이지 메인화면 인기/추천 영역에 표시되는 카테고리 항목 정보를 조회하는 페이지이다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : 2015-02-23
// 7. 수정 이력 :
//          2015-02-23 최초 작성
//***********************************************************
%>

<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    DataBox mainCategoryDetailInfo = (DataBox)request.getAttribute("mainCategoryDetailInfo");

    DataBox dbox = null;

    int seq = box.getInt("seq");
    int categorySeq = box.getInt("categorySeq");
    int positionNum = box.getInt("positionNum");
    String categoryNm = box.getString("categoryNm");
    String categoryType = box.getString("categoryType");
    String categoryTypeNm = box.getString("categoryTypeNm");
    String layoutType = box.getString("layoutType");
%>
<!DOCTYPE html>
<html>
<head>
<title>상세보기 - 메인 인기/추천 관리 - 홈페이지 - 관리자 - 한국콘텐츠아카데미</title>
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
     * 수정 화면으로 이동한다.
     */
    function fnGoToUpdatePage(categorySeq) {
        $("#oProcess").val("updatePage");
        $("#oMainCategoryDetailForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryDetailServlet").submit();
    }

    /**
     * 카테고리 항목을 삭제한다.
     * 해당 항목을 삭제하면 하위의 등록된 과정 정보까지 모두 삭제한다.
     */
    function fnDeleteMainCategoryDetail(categorySeq) {
        if ( confirm("해당 항목을 삭제하시겠습니까?") ) {
            $("#oProcess").val("delete");
            $("#oMainCategoryDetailForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryDetailServlet").submit();
        }
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
            <h3 style="text-align:left; padding-left:16px">* 하위 과정 상세</h3>

            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col style="width:15%;" />
                    <col style="width:*;" />
                </colgroup>
                <tr>
                    <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr>
                    <th height="25" class="table_title">카테고리명</th>
                    <td class="table_02_2">
                        <%= categoryNm %>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">과정명</th>
                    <td class="table_02_2">
                        <%= mainCategoryDetailInfo.getString("d_item_nm") %>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">순서</th>
                    <td class="table_02_2">
<%
    String imgWidth = "";
    String imgHeight = "";
    if ( layoutType.equals("A") ) {
        imgWidth = "188";
        imgHeight = (mainCategoryDetailInfo.getInt("d_position_num") == 5) ? "338" : "103";
%>
                        <table id="oLayoutTable" cellspacing="12" cellpadding="4">
                            <colgroup>
                                <col style="width:120px;"/>
                                <col style="width:120px;"/>
                                <col style="width:120px;"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 1 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>1</td>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 2 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>2</td>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 5 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%> rowspan="2">5</td>
                                </tr>
                                <tr>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 3 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>3</td>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 4 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>4</td>
                                </tr>
                            </tbody>
                        </table>
<%
    } else {
%>
                        <table id="oLayoutTable" cellspacing="12" cellpadding="4">
                            <colgroup>
                                <col style="width:120px;"/>
                                <col style="width:120px;"/>
                                <col style="width:120px;"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td colspan="3">
                                        <table width="50%">
                                            <tr>
                                                <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 1 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>1</td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 2 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>2</td>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 3 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>3</td>
                                    <td <%= mainCategoryDetailInfo.getInt("d_position_num") == 4 ? "style=\"background-color:#cdcdcd; color:#000;\"" : ""%>>4</td>
                                </tr>
                            </tbody>
                        </table>
<%
    }
%>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">썸네일</th>
                    <td class="table_02_2">
                        <img src="<%= mainCategoryDetailInfo.getString("d_thumb_file_nm") %>" width="<%= imgWidth %>" height="<%= imgHeight %>" alt="" />
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">사용여부</th>
                    <td class="table_02_2">
                        <%= mainCategoryDetailInfo.getString("d_use_yn").equals("Y") ? "사용" : "미사용" %>
                    </td>
                </tr>
            </table>

            <br/>
            <!-- 저장, 취소 버튼 시작 //-->
            <table width="22%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="javascript:fnGoToUpdatePage();"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:fnDeleteMainCategoryDetail();"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:history.back(-1)"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->
        </td>
    </tr>
</table>

<form id="oMainCategoryDetailForm" name="mainCategoryDetailForm" method="post" action="">
    <input type="hidden" id="oProcess" name="process" value="" />
    <input type="hidden" id="oSeq" name="seq" value="<%= seq %>" />
    <input type="hidden" id="oCategorySeq" name="categorySeq" value="<%= categorySeq %>" />
    <input type="hidden" id="oCategoryNm" name="categoryNm" value="<%= categoryNm %>" />
    <input type="hidden" id="oCategoryType" name="categoryType" value="<%= categoryType %>" />
    <input type="hidden" id="oCategoryTypeNm" name="categoryTypeNm" value="<%= categoryTypeNm %>" />
    <input type="hidden" id="oLayoutType" name="layoutType" value="<%= layoutType %>" />
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
