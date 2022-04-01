<%
//**********************************************************
// 1. 파일명 : za_RetrieveMainCategoryList.jsp
// 2. 프로그램명 : 홈페이지 메인화면 인기/추천 항목 목록 조회
// 3. 설명 : 홈페이지 메인화면 인기/추천 영역에 표시되는 카테고리 목록을 조회한다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : 2015-01-27
// 7. 수정 이력 :
//          2015-01-27 최초 작성
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.library.PageUtil" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    ArrayList<DataBox> mainCategoryList = (ArrayList<DataBox>)request.getAttribute("mainCategoryList");

    String process = box.getString("process");
    String searchCategoryNm = box.getString("searchCategoryNm");
    String searchUseYn = box.getString("searchUseYn");

    int pageNo = box.getInt("pageNo");
    int pageSize = box.getInt("pageSize");
    int totalPage = 0;
    int totalRowCount = 0;
    int dispNum = 0;

    pageNo = pageNo == 0 ? 1 : pageNo;
    pageSize = pageSize == 0 ? 10 : pageSize;
%>
<!DOCTYPE html>
<html>
<head>
<title>목록 - 메인 인기/추천 관리 - 홈페이지 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/js/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
<!--
    /**
     * 등록화면으로 이동
     */
    function fnGoToRegisterPage() {
        location.href = "/learn/admin/homepage/maincategory/za_RegisterMainCategoryPage.jsp";
    }

    function fnViewMainCategoryPage( categorySeq ) {
        $("#oCategorySeq").val(categorySeq);
        $("#oSearchProcess").val("retrieveInfo");
        $("#oMainCategorySearchForm").attr("action","/servlet/controller.admin.homepage.MainCategoryServlet").submit();
    }

    /**
     * 페이지 이동
     * @param pageNo 이동할 페이지 번호
     */
    function goPage( pageNo ) {
        $("#oPageNo").val(pageNo);
        $("#oSearchProcess").val("retrieveList");
        $("#oMainCategorySearchForm").attr("action","/servlet/controller.admin.homepage.MainCategoryServlet").submit();
    }

    /**
     * 한 화면에 보여줄 목록의 크기를 설정한다.
     * @param pSize 페이지 크기
     */
    function pagesize( pSize ) {
        $("#oPageSize").val(pSize);
        goPage(1);
    }

    /**
     * 검색조건으로 조회
     */
    function fnSearchMainCategoryList() {
        $("#oMainCategorySearchForm").attr("action","/servlet/controller.admin.homepage.MainCategoryServlet").submit();
    }

    /**
     * 카테고리별 하위 등록 관리 화면으로 이동한다.
     * @pararm categorySeq 카테고리 일련번호
     */
    function fnManageMainCategoryPage(categorySeq, categoryNm, categoryType, categoryTypeNm, layoutType) {
        $("#oManageProcess").val("retrieveList");
        $("#oManageCategorySeq").val(categorySeq);
        $("#oManageCategoryNm").val(categoryNm);
        $("#oManageCategoryType").val(categoryType);
        $("#oManageCategoryTypeNm").val(categoryTypeNm);
        $("#oManageLayoutType").val(layoutType);
        $("#oManageForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryDetailServlet").submit();
    }

    /**
     * 사용여부를 일괄 적용한다. 최대 가능한 사용 건수는 6개이다.
     */
    function fnApplyUseYn() {
        var len = $("select[name=categoryUseYn]").length;
        var applyCnt = 0;

        for ( var i = 0 ; i < len ; i++ ) {
            if ( $("select[name=categoryUseYn]").eq(i).val().indexOf("Y") > -1 ) {
                applyCnt++;
            }
        }

        if ( applyCnt > 6 ) {
            alert("사용 가능한 최대 개수는 6개입니다.");
            return;
        } else if ( confirm("사용 여부를 적용하시겠습니까?") ) {
            $("#oCategoryListProcess").val("updateUseYn");
            $("#oCategoryListForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryServlet");
            $("#oCategoryListForm").submit();
        } else {
            return;
        }
    }
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

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
            <!-- form 시작 //-->
            <form id="oMainCategorySearchForm" name="mainCategorySearchForm" method="post">
                <input type="hidden" id="oSearchProcess" name="process" value="<%= process %>" />
                <input type="hidden" id="oPageNo" name="pageNo" value="<%= pageNo %>" />
                <input type="hidden" id="oPageSize" name="pageSize" value="<%= pageSize %>" />
                <input type="hidden" id="oCategorySeq" name="categorySeq" value="" />
            <table border="0" cellpadding="0" cellspacing="0" class="searchTable">
                <colgroup>
                    <col style="width:12%;" />
                    <col style="width:*;" />
                    <col style="width:20%;" />
                </colgroup>
                <tr>
                    <th scope="cols">카테고리명 : </th>
                    <td style="padding:4px;"><input type="text" id="oSearchCategoryNm" name="searchCategoryNm" style="width:520px;" value="<%= searchCategoryNm %>" /></td>
                    <td rowspan="2" style="text-align:center;"><a href="javascript:fnSearchMainCategoryList();"><img src="/images/admin/button/btn_inquiry.gif" border="0"></a></td>
                </tr>
                <tr>
                    <th scope="cols">사용여부 : </th>
                    <td style="padding:4px;">
                        <label><input type="radio" id="oSearchUseYnAll" name="searchUseYn" value="A" <%= searchUseYn.equals("A") ? "checked=\"checked\"" : ""%> />전체</label>
                        <label><input type="radio" id="oSearchUseYnY" name="searchUseYn" value="Y" <%= searchUseYn.equals("Y") ? "checked=\"checked\"" : ""%> />사용</label>
                        <label><input type="radio" id="oSearchUseYnN" name="searchUseYn" value="N" <%= searchUseYn.equals("N") ? "checked=\"checked\"" : ""%> />미사용</label>
                    </td>
                </tr>
            </table>
            </form>
            <!-- form 끝 //-->
            <br/>
            <!-- 버튼 시작 //-->
            <table class="table1" cellspacing="1" cellpadding="5">
                <tr>
                    <td style="width:100%; text-align:right;">
                        <a class="board_btn" onclick="fnApplyUseYn();"><span style="font-size:12px;">사용여부 변경</span></a>
                        <a class="board_btn2" onclick="fnGoToRegisterPage();"><span style="font-size:12px;">카테고리 등록</span></a>
                    </td>
                </tr>
            </table>
            <!-- 버튼 끝 //-->

            <!-- 목록  시작 //-->
            <form id="oCategoryListForm" name="categoryListForm" method="post">
                <input type="hidden" id="oCategoryListProcess" name="process" value="" />
            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col style="width:6%;" />
                    <col style="width:*;" />
                    <col style="width:16%;" />
                    <col style="width:12%;" />
                    <col style="width:7%;" />
                    <col style="width:9%;" />
                    <col style="width:7%;" />
                    <col style="width:12%;" />
                </colgroup>

                <tr>
                    <td colspan="8" class="table_top_line" style="height:2px;"></td>
                </tr>
                <tr>
                    <th class="table_title" height="25">NO</th>
                    <th class="table_title">카테고리명</th>
                    <th class="table_title">카테고리 타입</th>
                    <th class="table_title">레이아웃 타입</th>
                    <th class="table_title">순서</th>
                    <th class="table_title">등록과정 수</th>
                    <th class="table_title">사용여부</th>
                    <th class="table_title">과정등록</th>
                </tr>

<%
    String btnClassName = "";
    String btnTitle = "";

    if ( mainCategoryList != null && mainCategoryList.size() > 0 ) {
        DataBox dbox = null;
        for( int i = 0 ; i < mainCategoryList.size() ; i++ ){
            dbox = (DataBox)mainCategoryList.get(i);

            if ( dbox.getString("d_category_type").indexOf("B_") > -1 ) {
                btnClassName = dbox.getInt("d_reg_item_cnt") == 0 ? "btn_violet" : "board_green";
                btnTitle = dbox.getInt("d_reg_item_cnt") == 0 ? "&nbsp;글 지정&nbsp;" : "&nbsp;글 관리&nbsp;";
            } else {
                btnClassName = dbox.getInt("d_reg_item_cnt") == 0 ? "btn_violet" : "board_green";
                btnTitle = dbox.getInt("d_reg_item_cnt") == 0 ? "과정 등록" : "과정 관리";
            }

%>
                <tr>
                    <td class="table_01"><%= dbox.getInt("d_dispNum") %></td>
                    <td class="table_02_2"><a href="javascript:fnViewMainCategoryPage(<%= dbox.getInt("d_category_seq") %>);"><%= dbox.getString("d_category_nm") %></a></td>
                    <td class="table_02_1"><%= dbox.getString("d_category_type_nm") %></td>
                    <td class="table_02_1"><%= dbox.getString("d_layout_type") %></td>
                    <td class="table_02_1"><%= dbox.getString("d_sort_order") %></td>
                    <td class="table_02_1"><%= dbox.getInt("d_reg_item_cnt") %></td>
                    <td class="table_02_1">
                        <select id="oCategoryUseYn_<%= dbox.getInt("d_category_seq") %>" name="categoryUseYn">
                            <option value="<%= dbox.getInt("d_category_seq") %>|Y" <%= dbox.getString("d_use_yn").equals("Y") ? "selected=\"selected\"" : "" %>>Y</option>
                            <option value="<%= dbox.getInt("d_category_seq") %>|N" <%= dbox.getString("d_use_yn").equals("N") ? "selected=\"selected\"" : "" %>>N</option>
                        </select>
                    </td>
                    <td class="table_02_1"><a class="<%= btnClassName %>" onclick="fnManageMainCategoryPage(<%= dbox.getInt("d_category_seq") %>, '<%= dbox.getString("d_category_nm") %>', '<%= dbox.getString("d_category_type") %>', '<%= dbox.getString("d_category_type_nm") %>', '<%= dbox.getString("d_layout_type") %>');"><span><%= btnTitle %></span></a></td>
                </tr>
<%
            totalPage = dbox.getInt("d_totalPage");
            totalRowCount = dbox.getInt("d_totalRowCount");
        }
    } else {
%>
                <tr>
                    <td class="table_01" colspan="8" align="center">조회된 목록이 없습니다.</td>
                    <!-- <td class="table_02_1"></td> //-->
                </tr>
<%
    }
%>
            </table>
            </form>
            <!-- 목록 끝 //-->

            <!-- paging 시작 //-->
            <br/>
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" valign="absmiddle">
                        <%= PageUtil.printPageSizeList(totalPage, pageNo, 0, pageSize, totalRowCount) %>
                    </td>
                </tr>
            </table>
            <!-- paging 끝 //-->
        </td>
    </tr>
</table>

<form id="oManageForm" name="manageForm" method="post">
    <input type="hidden" id="oManageProcess" name="process" value="" />
    <input type="hidden" id="oManageCategorySeq" name="categorySeq" value="" />
    <input type="hidden" id="oManageCategoryNm" name="categoryNm" value="" />
    <input type="hidden" id="oManageCategoryType" name="categoryType" value="" />
    <input type="hidden" id="oManageCategoryTypeNm" name="categoryTypeNm" value="" />
    <input type="hidden" id="oManageLayoutType" name="layoutType" value="" />
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
