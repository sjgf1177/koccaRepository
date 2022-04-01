<%
//**********************************************************
// 1. 파일명 : za_RetrieveMainCategoryDetailListB.jsp
// 2. 프로그램명 : 홈페이지 메인화면 인기/추천 항목의 하위 등록 과정 목록 조회. 
// 3. 설명 : 홈페이지 메인화면 인기/추천 영역에 표시되는 카테고리의 하위 등록 과정 목록을 조회한다. 게시판 유형(아카데미 이야기, CMU)만 조회 및 관리한다.
// 4. 환경 : JDK 1.5
// 5. 버젼 : 1.0
// 6. 작성 : 2015-02-24
// 7. 수정 이력 :
//      7.1. 2015-02-24 최초 작성
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

    ArrayList<DataBox> mainCategoryDetailList = (ArrayList<DataBox>)request.getAttribute("mainCategoryDetailList");

    String process = box.getString("process");
    int categorySeq = box.getInt("categorySeq");
    String categoryNm = box.getString("categoryNm");
    String categoryType = box.getString("categoryType");
    String categoryTypeNm = box.getString("categoryTypeNm");
    String layoutType = box.getString("layoutType");
    String searchType = box.getString("searchType");
    String searchNm = box.getString("searchNm");
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
<title>목록 - 메인 인기/추천 하위 과정 관리 - 홈페이지 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<style>
    .contentsPopup { display:none; width:966px; height:585px; background:#fff; color:#000; position:fixed; top:102px; left:15px; text-align:center; border:2px solid #000; }
    #oClosePopup { position:absolute; top:6px; left:927px; width:24px; cursor:pointer; border:1px solid #000; font-weight:bold;}
    #oContents { position:absolute; top:34px; left:14px; width:97%; height:92%; border:1px solid #000; overflow:hidden; overflow-y:scroll;}
</style>

<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/js/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
<!--
    $(function() {
        $("#oClosePopup").click( function() {
            $("#oContentsPopup").hide();
        });
    });

    /**
     * 게시물 내용을 조회한다.
     * @param seq - 게시물 일련번호
     */
    function fnViewContents( categoryType, seq ) {
        var param = "process=viewBoardContents&categoryType=" + categoryType + "&seq=" + seq;

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "post"
            ,   url : "/servlet/controller.admin.homepage.MainCategoryDetailServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {

                    $("#oTitle").html(data.contents.d_title);
                    $("#oContents").html(data.contents.d_cont);
                    $("#oContentsPopup").show();
                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(XMLHttpRequest, textStatus, errorThrown) {
                    // alert("error : " + errorThrown);
                    alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
                }

        });
    }

    /**
     * 페이지 이동
     */
    function goPage( pageNo ) {
        $("#oPageNo").val(pageNo);
        $("#oProcess").val("retrieveList");
        $("#oSearchForm").attr("action","/servlet/controller.admin.homepage.MainCategoryDetailServlet").submit();
    }

    /**
     *
     */
    function pagesize( pSize ) {
        $("#oPageSize").val(pSize);
        goPage(1);
    }

    /**
     *
     */
    function fnSearchMainCategoryDetailList() {
        $("#oSearchForm").attr("action","/servlet/controller.admin.homepage.MainCategoryDetailServlet").submit();
    }

    /**
     * 메인카테고리 목록 화면으로 이동한다.
     */
    function fnGoMainCategoryList() {
        $("#oManageProcess").val("retrieveList");
        $("#oManageForm").attr("action","/servlet/controller.admin.homepage.MainCategoryServlet").submit();
    }

    /**
     * 메인글 지정 여부를 설정한다.
     * @param flag - Y/N
     */
    function fnSetMainCategoryItem() {
        var applyCnt = $("input:checkbox[name=mainYn]:checked").length;
        var maxRegCnt = <%= layoutType.equals("A") ? 5 : 4 %>;

        
        if(applyCnt == 0) {
            alert("메인글로 지정할 게시물을 선택하십시오.");
            return;
        } else if ( applyCnt > maxRegCnt ) {
            alert("사용 가능한 최대 개수는 " + maxRegCnt + "개입니다.");
            return;
        } else if ( confirm("선택된 게시물을 메인글로 지정하시겠습니까?") ) {

        	var console = window.console || {log:function(){}}; 
        	
            $("input:checkbox[name=mainYn]:checked").each( function() {
                console.log("checked value : " + $(this).val());
            });

            $("#oItemListProcess").val("updateMainItem");
            $("#oItemListForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryDetailServlet");
            $("#oItemListForm").submit();
        } else {
            return;
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
            <h3 style="text-align:left; padding-left:16px">* 하위 과정 목록 - 게시판 유형</h3>

            <!-- 검색 form 시작 //-->
            <form id="oSearchForm" name="searchForm" method="post">
                <input type="hidden" id="oProcess" name="process" value="retrieveList" />
                <input type="hidden" id="oPageNo" name="pageNo" value="<%= pageNo %>" />
                <input type="hidden" id="oPageSize" name="pageSize" value="<%= pageSize %>" />
                <input type="hidden" id="oCategorySeq" name="categorySeq" value="<%= categorySeq %>" />
                <input type="hidden" id="oCategoryNm" name="categoryNm" value="<%= categoryNm %>" />
                <input type="hidden" id="oCategoryType" name="categoryType" value="<%= categoryType %>" />
                <input type="hidden" id="oCategoryTypeNm" name="categoryTypeNm" value="<%= categoryTypeNm %>" />
                <input type="hidden" id="oLayoutType" name="layoutType" value="<%= layoutType %>" />

                <!-- 뒤로가기 버튼 시작 //-->
                <table class="table1" cellspacing="1" cellpadding="5">
                    <tr>
                        <td style="width:100%; text-align:right;"><a class="board_btn" onclick="fnGoMainCategoryList();"><span style="font-size:12px;">&lt;&lt; 뒤로가기</span></a></td>
                    </tr>
                </table>
                <!-- 뒤로가기 버튼 끝 //-->

                <table border="0" cellpadding="0" cellspacing="0" class="searchTable">
                    <colgroup>
                        <col style="width:12%;" />
                        <col style="width:32%;" />
                        <col style="width:12%;" />
                        <col style="width:32%;" />
                        <col style="width:12%;" />
                    </colgroup>
                    <tr>
                        <th scope="cols">카테고리명 </th>
                        <td style="padding:4px;"><%= categoryNm %></td>
                        <th scope="cols">레이아웃 타입 </th>
                        <td style="padding:4px;"><%= layoutType %> 타입</td>
                        <td rowspan="4" style="text-align:center;"><a href="javascript:fnSearchMainCategoryDetailList();"><img src="/images/admin/button/btn_inquiry.gif" border="0"></a></td>
                    </tr>
                    <tr>
                        <th scope="cols">카테고리 타입</th>
                        <td colspan="3" style="padding:4px;"><%= categoryTypeNm %></td>
                    </tr>
                    <tr>
                        <th scope="cols">사용여부 </th>
                        <td colspan="3" style="padding:4px;">
                            <label><input type="radio" id="oSearchUseYnAll" name="searchUseYn" value="A" <%= searchUseYn.equals("A") ? "checked=\"checked\"" : ""%> />전체</label>
                            <label><input type="radio" id="oSearchUseYnY" name="searchUseYn" value="Y" <%= searchUseYn.equals("Y") ? "checked=\"checked\"" : ""%> />사용</label>
                            <label><input type="radio" id="oSearchUseYnN" name="searchUseYn" value="N" <%= searchUseYn.equals("N") ? "checked=\"checked\"" : ""%> />미사용</label>
                        </td>
                    </tr>
                    <tr>
                        <th scope="cols">
                            <select id="oSearchType" name="searchType">
                                <option value="title" <%= searchType.equals("title") ? "selected=\"selected\"" : "" %>>제목</option>
                                <option value="cont" <%= searchType.equals("cont") ? "selected=\"selected\"" : "" %>>내용</option>
                            </select>
                        </th>
                        <td colspan="3" style="padding:4px;"><input type="text" id="oSearchNm" name="searchNm" style="width:520px;" value="<%= searchNm %>" /></td>
                    </tr>
                </table>
            </form>
            <!-- 검색 form 끝 //-->
            <br/>
            <!-- 과정추가 버튼 시작 //-->
            <table class="table1" cellspacing="1" cellpadding="5">
                <tr>
                    <td style="width:100%; text-align:right;">
                        <a class="board_btn" onclick="fnSetMainCategoryItem();"><span style="font-size:12px;">메인글 지정</span></a>
                        <!-- <a class="board_btn" onclick="fnSetMainCategoryItem('N');"><span style="font-size:12px;">메인글 취소</span></a> //-->
                    </td>
                </tr>
            </table>
            <!-- 과정추가 버튼 끝 //-->

            <!-- 하위 등록된 과정 목록  시작 //-->
            <form id="oItemListForm" name="itemListForm" method="post">
                <input type="hidden" id="oItemListProcess" name="process" value="" />
                <input type="hidden" id="oItemListPageNo" name="pageNo" value="<%= pageNo %>" />
                <input type="hidden" id="oItemListPageSize" name="pageSize" value="<%= pageSize %>" />
                <input type="hidden" id="oItemListCategorySeq" name="categorySeq" value="<%= categorySeq %>" />
                <input type="hidden" id="oItemListCategoryNm" name="categoryNm" value="<%= categoryNm %>" />
                <input type="hidden" id="oItemListCategoryType" name="categoryType" value="<%= categoryType %>" />
                <input type="hidden" id="oItemListCategoryTypeNm" name="categoryTypeNm" value="<%= categoryTypeNm %>" />
                <input type="hidden" id="oItemListLayoutType" name="layoutType" value="<%= layoutType %>" />
            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col style="width:8%;" />
                    <col style="width:*;" />
                    <col style="width:10%;" />
                    <col style="width:10%;" />
                    <col style="width:10%;" />
                    <col style="width:10%;" />
                </colgroup>

                <tr>
                    <td colspan="6" class="table_top_line" style="height:2px;"></td>
                </tr>
                <tr>
                    <th class="table_title" height="25">NO</th>
                    <th class="table_title">제목</th>
                    <th class="table_title">작성자</th>
                    <th class="table_title">등록일</th>
                    <th class="table_title">사용여부</th>
                    <th class="table_title">선택</th>
                </tr>

<%
    if ( mainCategoryDetailList != null && mainCategoryDetailList.size() > 0 ) {
        DataBox dbox = null;
        for( int i = 0 ; i < mainCategoryDetailList.size() ; i++ ){
            dbox = (DataBox)mainCategoryDetailList.get(i);
%>
                <tr>
                    <td class="table_01"><%= dbox.getInt("d_dispNum") %></td>
                    <td class="table_02_2"><a href="javascript:fnViewContents('<%= categoryType %>', <%= dbox.getInt("d_seq") %>);"><%= dbox.getString("d_title") %></a></td>
                    <td class="table_02_1"><%= dbox.getString("d_reg_id") %></td>
                    <td class="table_02_1"><%= dbox.getString("d_reg_dt") %></td>
                    <td class="table_02_1"><%= dbox.getString("d_use_yn") %></td>
                    <td class="table_02_1"><input type="checkbox" id="oMainYn<%= i %>" name="mainYn" <%= dbox.getString("d_main_yn").equals("Y") ? "checked=\"checked\"" : "" %> value="<%= dbox.getString("d_seq")%>" /></td>
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
            <!-- 하위 등록된 과정 목록 끝 //-->

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

<div id="oContentsPopup" class="contentsPopup">
    <div id="oTitle" style="padding: 8px 0 0 15px; font-weight:bold; text-align:left;">내용보기</div>
    <div id="oClosePopup">X</div>
    <div id="oContents">여기는 내용이 나옵니다.</div>
</div>

<form id="oManageForm" name="manageForm" method="post">
    <input type="hidden" id="oManageProcess" name="process" value="" />
    <input type="hidden" id="oManageSeq" name="seq" value="" />
    <input type="hidden" id="oManageCategorySeq" name="categorySeq" value="<%= categorySeq %>" />
    <input type="hidden" id="oManageCategoryNm" name="categoryNm" value="<%= categoryNm %>" />
    <input type="hidden" id="oManageCategoryType" name="categoryType" value="<%= categoryType %>" />
    <input type="hidden" id="oManageCategoryTypeNm" name="categoryTypeNm" value="<%= categoryTypeNm %>" />
    <input type="hidden" id="oManageLayoutType" name="layoutType" value="<%= layoutType %>" />
    <input type="hidden" id="oManagePositionNum" name="positionNum" value="" />
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
