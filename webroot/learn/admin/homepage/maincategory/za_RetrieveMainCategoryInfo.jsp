<%
//**********************************************************
// 1. 파일명 : za_RetrieveMainCategoryInfo.jsp
// 2. 프로그램명 : 홈페이지 메인화면 인기/추천 항목 등록 화면
// 3. 설명 : 홈페이지 메인화면 인기/추천 영역에 표시되는 카테고리 항목 정보를 조회하는 페이지이다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : 2015-01-29
// 7. 수정 이력 :
//          2015-01-29 최초 작성
//***********************************************************
%>

<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    DataBox mainCategoryInfo = (DataBox)request.getAttribute("mainCategoryInfo");
    ArrayList<DataBox> mainCategoryDetailList = (ArrayList<DataBox>)request.getAttribute("mainCategoryDetailList");
    DataBox dbox = null;

    int categorySeq = box.getInt("categorySeq");
%>
<!DOCTYPE html>
<html>
<head>
<title>상세보기 - 메인 인기/추천 관리 - 홈페이지 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/js/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
<!--
    /**
     * 수정 화면으로 이동한다.
     */
    function fnGoToUpdatePage(categorySeq) {
        $("#oProcess").val("updatePage");
        $("#oMainCategoryInfoForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryServlet").submit();
    }

    /**
     * 카테고리 항목을 삭제한다.
     * 해당 항목을 삭제하면 하위의 등록된 과정 정보까지 모두 삭제한다.
     */
    function fnDeleteMainCategory(categorySeq) {
        if ( confirm("해당 카테고리를 삭제하면 하위의 등록된 모든 과정도 함께 삭제됩니다.\n삭제하시겠습니까?") ) {
            $("#oProcess").val("delete");
            $("#oMainCategoryInfoForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryServlet").submit();
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
            <h3 style="text-align:left; padding-left:16px">* 메인 카테고리 관리</h3>

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
                        <%= mainCategoryInfo.getString("d_category_nm") %>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">등록 과정</th>
                    <td class="table_02_2">
<%
        if ( mainCategoryDetailList != null && mainCategoryDetailList.size() > 0) {
            for ( int i = 0 ; i < mainCategoryDetailList.size() ; i++ ) {
                dbox = (DataBox)mainCategoryDetailList.get(i);
%>
                <a href="<%= dbox.getString("d_item_url") %>" target="_blank"><%= dbox.getString("d_item_nm") %></a><br/>
<%
            }
        } else {
            out.println("등록된 과정이 없습니다.");
        }
%>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">카테고리 타입</th>
                    <td class="table_02_2">
                        <%= mainCategoryInfo.getString("d_category_type_nm") %>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">레이아웃 타입</th>
                    <td class="table_02_2">
                        <%= mainCategoryInfo.getString("d_layout_type") %> 타입
                        <br/>
                        
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">순서</th>
                    <td class="table_02_2">
                        <%= mainCategoryInfo.getString("d_sort_order") %>
                    </td>
                </tr>
                <tr>
                    <th height="25" class="table_title">사용여부</th>
                    <td class="table_02_2">
                        <%= mainCategoryInfo.getString("d_use_yn").equals("Y") ? "사용" : "미사용" %>
                    </td>
                </tr>
            </table>

            <br/>
            <!-- 저장, 취소 버튼 시작 //-->
            <table width="22%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="javascript:fnGoToUpdatePage();"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:fnDeleteMainCategory();"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:history.back(-1)"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->
        </td>
    </tr>
</table>

<form id="oMainCategoryInfoForm" name="mainCategoryInfoForm" method="get" action="">
    <input type="hidden" id="oProcess" name="process" value="" />
    <input type="hidden" id="oCategorySeq" name="categorySeq" value="<%= categorySeq %>" />
    <input type="hidden" id="oRegItemCnt" name="regItemCnt" value="<%= mainCategoryInfo.getString("d_reg_item_cnt") %>" />
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
