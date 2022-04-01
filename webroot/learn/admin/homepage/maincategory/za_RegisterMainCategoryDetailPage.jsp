<%
//**********************************************************
// 1. 파일명 : za_RegisterMainCategoryDetailPage.jsp
// 2. 프로그램명 : 홈페이지 메인화면 인기/추천 항목 등록 화면
// 3. 설명 : 홈페이지 메인화면 인기/추천 영역에 표시되는 카테고리 항목을 등록할 수 있는 페이지이다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : 2015-02-13
// 7. 수정 이력 :
//          2015-02-13 최초 작성
//***********************************************************
%>

<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    int categorySeq = box.getInt("categorySeq");
    String categoryNm = box.getString("categoryNm");
    String categoryType = box.getString("categoryType");
    String categoryTypeNm = box.getString("categoryTypeNm");
    String layoutType = box.getString("layoutType");

    int maxSortOrder = layoutType.equals("A") ? 5 : 4;
%>
<!DOCTYPE html>
<html>
<head>
<title>등록 - 메인 인기/추천 하위 과정 관리 - 홈페이지 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<style>
    .coursePopup { display:none; width:840px; height:500px; background:#fff; color:#000; position:fixed; top:100px; left:80px; text-align:center; border:2px solid #000; }
    #oClosePopup { position:absolute; top:6px; left:795px; width:24px; cursor:pointer; border:1px solid #000; font-weight:bold;}
    #oCourseSearch { position:absolute; top:34px; left:14px; width:96%; height:100px; overflow:hidden;}
    #oCourseList { position:absolute; top:88px; left:14px; width:96%; height:80%; border:1px solid #000; overflow:hidden; overflow-y:scroll;}
    #oCourseList table { width:100%; border-collapse:collapse; }
    #oCourseList table th { font-size:12px; text-align:center; border:1px gray solid; background-color: #eef1f2; padding:12px;}
    #oCourseList table td { font-size:12px; border:1px gray solid; background-color: #ffffff; padding:4px;}
    .d_center { text-align:center;}
    .d_left { text-align:left; }

    #oLayoutTable {width: 360px; }
    #oLayoutTable tr td {text-align:center; font-weight:bold; font-size:32px;  border:1px solid; height:56px; color:#808080;}
</style>

<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/js/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
<!--
    $(function() {
        $(".board_btn2").click( function() {
            $("#oCourseSearchPopup").show();
        });

        $("#oClosePopup").click( function() {
            $("#oCourseSearchPopup").hide();
        });
    });

    /**
     * 메인 화면 인기/추천 하위 과정 카테고리 항목을 등록한다.
     */
    function fnRegisterMainCategoryDetail() {
        if( $("#oItemUrl").val() === "" ) {
            alert("과정을 선택하세요.");
            $("#oItemNm").focus();
            return;

        } else if( $("#oPositionNum").val() === "" ) {
            alert("순서를 선택하세요.");
            $("#oPositionNum").focus();
            return;

        } else if( $("input:radio[name=useYn]:checked").length === 0 ) {
            alert("사용여부를 선택하세요.");
            $("input:radio[name=useYn]").eq(0).focus();
            return;

        } else if( $("#oThumbFileNm").val() === "") {
            alert("썸네일 파일을 선택하세요.");
            $("#oThumbFileNm").focus();
            return;

        } else if (confirm("등록하시겠습니까?")) {
            $("#oMainCategoryDetailRegForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryDetailServlet").submit();
        } else {
            return;
        }
    }

    /**
     * 목록 화면으로 이동한다.
     */
    function fnGoToList() {
        $("#oProcess").val("retrieveList");
        $("#oMainCategoryDetailRegForm").attr("action", "/servlet/controller.admin.homepage.MainCategoryDetailServlet").submit();
    }

    /**
     * 과정 검색 팝업에서 검색어 입력 후 엔터키를 눌렀을 때 검색
     * @param e - key event
     */
    function fnSearchSubjByKey(e) {
        if (e.keyCode == "13") {
            fnSearchSubj();
        }
    }

    /**
     * 과정을 검색한다.
     * @param categoryType - 강좌유형. 정규과정(C_ONL) /열린강좌(C_OPN)
     */
    function fnSearchSubj() {


        $("#oSearchProcess").val("searchSubj");
        var resultStr = "";

        var param = $("#oSearchSubjForm").serialize();

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "post"
            ,   url : "/servlet/controller.admin.homepage.MainCategoryDetailServlet"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {

                    var currCnt = data.subjList.length;

                    $("#oCourseList").empty();

                    if ( currCnt > 0 ) {
                        resultStr += "<table>\n";
                        resultStr += "  <colgroup>\n";
                        resultStr += "      <cols style=\"width:40px;\">\n";
                        resultStr += "      <cols style=\"width:90px\">\n";
                        resultStr += "      <cols style=\"width:*;\">\n";
                        resultStr += "      <cols style=\"width:80px;\">\n";
                        resultStr += "  </colgroup>\n";
                        resultStr += "  <tr>\n";
                        resultStr += "      <th>No.</th>\n";
                        resultStr += "      <th>과정분류</th>\n";
                        resultStr += "      <th>과정명</th>\n";
                        resultStr += "      <th>선택</th>\n";
                        resultStr += "  </tr>\n";
                        $.each( data.subjList, function() {

                            resultStr += "  <tr>";
                            resultStr += "      <td class=\"d_center\">" + (currCnt--) + "</td>";
                            resultStr += "      <td class=\"d_center\">" + this.d_areanm + "</td>";
                            resultStr += "      <td class=\"d_left\">" + this.d_subjnm + "</td>";
                            resultStr += "      <td class=\"d_center\"><a class=\"board_btn\" href=\"javascript:fnSetSubj('" + this.d_subj + "', '" + this.d_subjnm + "');\"><span>선택</span></a></td>";
                            resultStr += "  </tr>";
                        });
                        resultStr += "</table>\n";
                    } else {
                        resultStr += "<table><tr><td>조회된 과정이 없습니다.</td></tr></table>"
                    }

                    $("#oCourseList").append(resultStr);
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
     * 과정 정보를 선택한다.
     * @param subj - 과정코드 혹은 열린강좌 일련번호
     * @param subjNm - 과정명 혹은 열린강좌명
     */
    function fnSetSubj(subj, subjNm) {
        var categoryType = $("#oSearchCategoryType").val();
        var url = "";

        if ( categoryType == "C_ONL" ) {
            url = "/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=" + subj;
        } else {
            url = "/servlet/controller.infomation.GoldClassHomePageServlet?p_process=selectView&p_seq=" + subj;
        }

        $("#oItemNm").val(subjNm);
        $("#oItemUrl").val(url);

        $("#oClosePopup").trigger("click");
    }

    /**
     * 레이아웃 타입과 순서에 따라 썸네일 이미지 크기가 다르기 때문에 선택 항목에 해당하는 이미지 정보를 텍스트로 출력한다.
     * @param len - selectbox의 길이
     * @param val - selectbox 선택된 값
     */
    function fnShowThumbInfo( len, val ) {
        var layoutType = (len === 6) ? "A" : "B";
        var msg = "";

        if ( val !== "" ) {
            if ( layoutType === "A" ) {
                if ( val == 5) {
                    msg = "썸네일 크기는 <b>188 * 338</b> 입니다.";
                } else {
                    msg = "썸네일 크기는 <b>188 * 103</b> 입니다.";
                }
            } else {
                if ( val == 1) {
                    msg = "썸네일 크기는 <b>288 * 158</b> 입니다.";
                } else {
                    msg = "썸네일 크기는 <b>188 * 103</b> 입니다.";
                }
            }

            msg += " 규격에 맞지 않을 경우 홈페이지에서 이미지가 정상적으로 보이지 않을 수 있습니다.";
        }

        $("#oThumbInfo").html( msg );

        $("#oLayoutTable tr td").css("background-color", "#fff");
        $("#oLayoutTable tr td").css("color", "#808080");

        $("#oPosition" + val).css("background-color", "#cdcdcd");
        $("#oPosition" + val).css("color", "#000");

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

            <h3 style="text-align:left; padding-left:16px">* 하위 과정 등록</h3>
            <table width="97%" cellspacing="1" cellpadding="5">
                <tr>
                    <td style="background-color:#fff; text-align:right;">* 모든 입력 항목은 필수입니다.</td>
                </tr>
            </table>
            <!-- 입력 form 시작 //-->

            <form id="oMainCategoryDetailRegForm" name="mainCategoryRegForm" method="post" action="" enctype="multipart/form-data">
                <input type="hidden" id="oProcess" name="process" value="register" />
                <input type="hidden" id="oCategorySeq" name="categorySeq" value="<%= categorySeq %>" />
                <input type="hidden" id="oCategoryNm" name="categoryNm" value="<%= categoryNm %>" />
                <input type="hidden" id="oCategoryType" name="categoryType" value="<%= categoryType %>" />
                <input type="hidden" id="oCategoryTypeNm" name="categoryTypeNm" value="<%= categoryTypeNm %>" />
                <input type="hidden" id="oLayoutType" name="layoutType" value="<%= layoutType %>" />
            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col style="width:16%;" />
                    <col style="width:34%;" />
                    <col style="width:16%;" />
                    <col style="width:34%;" />
                </colgroup>
                <tr>
                    <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                    <th class="table_title">카테고리 명</th>
                    <td class="table_02_2">
                        <%= categoryNm %>
                    </td>
                    <th class="table_title">카테고리 타입</th>
                    <td class="table_02_2">
                        <%= categoryTypeNm %>
                    </td>
                </tr>
                <tr>
                    <th class="table_title">레이아웃 타입</th>
                    <td class="table_02_2" colspan="3">
                        <%= layoutType %> 타입
                        <br/>
<%
        if ( layoutType.equals("A") ) {
%>
                        <table id="oLayoutTable" cellspacing="12" cellpadding="4">
                            <colgroup>
                                <col style="width:120px;"/>
                                <col style="width:120px;"/>
                                <col style="width:120px;"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <td id="oPosition1">1</td>
                                    <td id="oPosition2">2</td>
                                    <td id="oPosition5" rowspan="2">5</td>
                                </tr>
                                <tr>
                                    <td id="oPosition3">3</td>
                                    <td id="oPosition4">4</td>
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
                                                <td id="oPosition1">1</td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="oPosition2">2</td>
                                    <td id="oPosition3">3</td>
                                    <td id="oPosition4">4</td>
                                </tr>
                            </tbody>
                        </table>
<%
        }
%>
                    </td>
                </tr>
                <tr>
                    <th class="table_title">과정 선택</th>
                    <td class="table_02_2" colspan="3">
                        <input type="text" id="oItemNm" name="itemNm" style="width:474px;" onkeypress="return false;"/>
                        <a href="#" class="board_btn2"><span>찾아보기</span></a><br/>
                        <input type="hidden" id="oItemUrl" name="itemUrl" />
                    </td>
                </tr>
                <tr>
                    <th class="table_title">순서</th>
                    <td class="table_02_2">
                        <select id="oPositionNum" name="positionNum" style="font-family:돋움; font-size:13px;" onchange="fnShowThumbInfo(this.length, this.value);">
                            <option value="">선택</option>
<%
        for( int i = 1; i <= maxSortOrder ; i++ ) {
%>
                            <option value="<%= i %>"><%= i %></option>
<%
        }
%>
                        </select>
                    </td>
                    <th class="table_title">사용여부</th>
                    <td class="table_02_2">
                        <label><input type="radio" id="oUseYnY" name="useYn" value="Y" checked="checked" /> 사용</label>
                        <label><input type="radio" id="oUseYnN" name="useYn" value="N" /> 미사용</label>
                    </td>
                </tr>
                <tr>
                    <th class="table_title">썸네일</th>
                    <td class="table_02_2" colspan="3">
                        <input type="file" id="oThumbFileNm" name="p_thumbFileNm" style="width:474px;" /><br/>
                        <div id="oThumbInfo" style="color:#f00; padding-top:4px;"></div>
                    </td>
                </tr>
            </table>
            </form>
            <!-- 입력 form 끝 //-->

            <br/>
            <!-- 저장, 취소 버튼 시작 //-->
            <table width="11%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="javascript:fnRegisterMainCategoryDetail();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:history.back()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->
        </td>
    </tr>
</table>

<div id="oCourseSearchPopup" class="coursePopup">
    <div style="padding: 8px 0 0 15px; font-weight:bold; text-align:left;">과정선택 - <%= categoryTypeNm %></div>
    <div id="oClosePopup">X</div>
    <div id="oCourseSearch">
        <form id="oSearchSubjForm" name="searchSubjForm" method="post" onsubmit="return false;">
            <input type="hidden" id="oSearchProcess" name="process" value="" />
            <input type="hidden" id="oSearchCategoryType" name="categoryType" value="<%= categoryType %>" />
        <table border="0" cellpadding="0" cellspacing="0" class="searchTable" style="width:100%;">
            <colgroup>
                <col style="width:12%;" />
                <col style="width:18%;" />
                <col style="width:12%;" />
                <col style="width:*;" />
                <col style="width:12%;" />
            </colgroup>
            <tr>
<%
        if  ( categoryType.equals("C_ONL") ) {
%>
                <th scope="cols">과정분류</th>
                <td style="text-align:left; padding:8px;">
                    <kocca:selectBox id="oSearchSubjArea" name="searchSubjArea" type="code" isLoad="true" codeValue="0101" optionTitle="- 선택 -" />
                </td>
                <th scope="cols">과정명</th>
                <td style="text-align:left; padding:8px;">
                    <input type="text" id="oSearchSubjNm" name="searchSubjNm" style="width:320px;" value="" onkeypress="fnSearchSubjByKey(event);" />
                </td>
<%
        } else if ( categoryType.equals("C_OPN") ) {
%>
                <th scope="cols">강좌분류</th>
                <td style="text-align:left; padding:8px;">
                    <kocca:selectBox id="oSearchSubjArea" name="searchSubjArea" type="code" isLoad="true" codeValue="0118" optionTitle="- 선택 -" />
                </td>
                <th scope="cols">강좌명</th>
                <td style="text-align:left; padding:8px;">
                    <input type="text" id="oSearchSubjNm" name="searchSubjNm" style="width:320px;" value="" onkeypress="fnSearchSubjByKey(event);" />
                </td>
<%
        }
%>

                <td style="text-align:center;"><a href="javascript:fnSearchSubj();"><img src="/images/admin/button/btn_inquiry.gif" border="0" /></a></td>
            </tr>
        </table>
        </form>
    </div>
    <div id="oCourseList"></div>
</div>


<%@ include file="/learn/library/getJspName.jsp" %>
</body>
</html>
