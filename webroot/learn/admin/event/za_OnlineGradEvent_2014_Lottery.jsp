<%@page contentType="text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.credu.library.DBConnectionManager" %>
<%@page import="com.credu.library.ListSet" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    String adminAuth = (String)session.getAttribute("gadmin");
    if ( adminAuth == null || !adminAuth.equals("A1") ) {
%>
<html>
<head>
<title>2014년 수료왕 이벤트 관리 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<script type="text/javascript">
    alert("권한이 없습니다.");
    window.close();
</script>
<body></body>
</html>

<%
    } else {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());

        Date date1 = sdf.parse("2015-01-05 10:30");
        Date date2 = new Date();

        int lastDrawLevel = this.getLastDrawLevel();
%>
<html>
<head>
<title>2014년 수료왕 이벤트 관리 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/css" href="/css/admin_style.css">
<style type="text/css">
    .divPopupMask   {position: absolute; left:0px; top:0px; width:1024px; height:800px; background-image: url(/images/common/mask.png); z-index:100; display: none; z-index:9999; }
    .btnWinnerPrize {border:1 solid #707070; padding: 4px 0px 2px 0px; font-size:12px; background-color:#ecceae; width:320px; cursor:pointer;}
    .btnDraw {border:1 solid #707070; padding: 6px 0px 4px 0px; font-size:12px; font-weight:bold; color:#ffffff; background-color:#558ff7; width:320px; cursor:pointer;}
    .btnDisabled {border:1 solid #707070; padding: 6px 0px 4px 0px; font-size:12px; font-weight:bold; color:#ababab; background-color:#efefef; width:320px;}
</style>

<script type="text/javascript" src="/js/jquery/1.8.3/jquery.js"></script>
<script type="text/javascript">
<!--
    /**
     * 추첨하기
     */
    function fnDoLuckyDraw (lvl, winnerCnt, winWeightPoint) {

        if ( !confirm("한 번 진행된 추첨 결과는 되돌릴 수 없습니다.\n추첨을 진행하시겠습니까?")) {
            return;
        }

        lvl = Number(lvl);

        winWeightPoint = (arguments[2] == null || arguments[2] == "" ) ? 0 : winWeightPoint;

        var btnStr = ["iPhone 6 16G(1명)", "신세계 상품권 10만원(3명)", "2015년 프랭클린 플래너(10명)", "바나나맛 우유(100명)"];

        var param = "act_type=1&level=" + lvl + "&winner_cnt=" + winnerCnt + "&win_weight_point=" + winWeightPoint;
        $(".divPopupMask").show();

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/learn/admin/event/za_OnlineGradEventAjaxResult.jsp"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {

                    alert("추첨이 완료되었습니다.");
                    $("#oLastDrawLevel").val(lvl);
                    $(".divPopupMask").hide();

                    var currBtn = $("#oDrawBtn" + lvl);
                    var nextBtn = ( lvl < 4) ? $("#oDrawBtn" + (lvl + 1) ) : $("#oDrawBtn4");

                    currBtn.attr("value", btnStr[lvl - 1] +" 당첨자 보기");
                    currBtn.attr("onclick", "fnViewPrizeWinners(" + lvl + ");");
                    currBtn.removeClass("btnDraw").addClass("btnWinnerPrize");

                    if ( lvl < 4 ) {
                        nextBtn.attr("disabled", false);
                        nextBtn.removeClass("btnDisabled").addClass("btnDraw");
                    }

                    fnViewPrizeWinners(lvl);
                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                    $("#oLoadingImg").hide();
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }

        });

    }

    /**
     * 당첨자 목록 보기
     */
    function fnViewPrizeWinners (lvl) {
        // ajax 비동기로 조회 후 화면 우측에 당첨자 목록 표시
        var winnerCnt = 0;

        switch(lvl) {
            case 1 :
                winnerCnt = 1;
                break;
            case 2 :
                winnerCnt = 3;
                break;
            case 3 :
                winnerCnt = 10;
                break;
            case 4 :
                winnerCnt = 100;
                break;
        }
        
        var param = "act_type=2&level=" + lvl + "&winner_cnt=" + winnerCnt + "&win_weight_point=0";
        var listStr = "";
        var num = 1;

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/learn/admin/event/za_OnlineGradEventAjaxResult.jsp"
            ,   dataType : "json"
            ,   data : param
            ,   success : function (data) {
                    if ( data.prizeWinnerList.length > 0 ) {

                        listStr += "<table width=\"99%\" class=\"table_out\" cellspacing=\"1\" cellpadding=\"1\">   \n";
                        listStr += "    <tr>    \n";
                        listStr += "        <th class=\"table_title\">번호</th>   \n";
                        listStr += "        <th class=\"table_title\">아이디</th>   \n";
                        listStr += "        <th class=\"table_title\">이름</th>     \n";
                        listStr += "        <th class=\"table_title\">전화번호</th> \n";
                        listStr += "        <th class=\"table_title\">이메일</th>   \n";
                        listStr += "    </tr>   \n";

                        $.each( data.prizeWinnerList, function(){
                            listStr += "<tr>   \n";
                            listStr += "    <td class=\"table_02_5\">" + (num++) + "</td>     \n";
                            listStr += "    <td class=\"table_02_5\">" + this.d_userid + "</td>     \n";
                            listStr += "    <td class=\"table_02_5\">" + this.d_usernm + "</td>     \n";
                            listStr += "    <td class=\"table_02_5\">" + this.d_handphone + "</td>  \n";
                            listStr += "    <td class=\"table_02_5\">" + this.d_email + "</td> \n";
                            listStr += "</tr>   \n";
                        });
                        listStr += "</table>";
                    }

                    var listArea = $("#oPrizeWinnersLevel" + lvl);
                    listArea.empty();
                    listArea.html( listStr );

                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }

        });
    }

    /**
     * 엑셀 파일로 목록 저장
     */
    function fnSaveExcelFile() {
        var lastDrawLevel = $("#oLastDrawLevel").val();
        if ( lastDrawLevel == 0 ) {
            alert("출력할 목록이 없습니다.");
        } else {
            var docForm = document.form1;
            docForm.action = "./za_OnlineGradEvent_2014_Excel.jsp"
            docForm.submit();
        }
    }

    function fnReset() {
        var param = "act_type=3&level=0&winner_cnt=0&win_weight_point=0";

        $.ajaxSetup({cache:false});
        $.ajax({
                type : "get"
            ,   url : "/learn/admin/event/za_OnlineGradEventAjaxResult.jsp"
            ,   data : param
            ,   success : function (data) {
                    location.reload();
                }
            ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
            ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }

        });
    }
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id="oLoadingImg" style="display:none;">
    <img src="/images/common/loader.gif" id="oLoadImg" />
</div>
<div id="oContents">
<form name="form1" method="get">
    <input type="hidden" id="oLastDrawLevel" name="lastDrawLevel" value="<%= lastDrawLevel %>" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><h2>2014년 수료왕 이벤트 추첨</h2></td>
                    <td align="right"><!-- <img src="/images/admin/common/sub_title_tail.gif" /> //--></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br/>

            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="450">
                    </td>
                    <td width="470" align="right" style="padding-right:20px;">
<%
        if ( date1.after(date2) ) {
%>
                        <input type="button" onclick="fnReset();" value="초기화 (2015년 1월 5일 10:30분부터 사용불가)" style="border: 1 solid; font-size:12px; "/>
<%
        }
%>
                    </td>
                    <td width="50" align="right" valign="middle">
                        <a href="javascript:fnSaveExcelFile();"><img src="/images/admin/button/btn_excelsave.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>

            <!-- //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <colgroup>
                    <col width="40%" />
                    <col width="60%" />
                </colgroup>
                <tr>
                    <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" height="25"><b>추첨</b></td>
                    <td class="table_title"><b>당첨자 목록</b></td>
                </tr>
                <tr>
                    <td class="table_01">
                        <table width="99%">
                            <tr>
                                <td style="text-align:center;" id="oBtnArea1">
<%
        if ( lastDrawLevel > 0 ) {
%>
                                    <input type="button" id="oDrawBtn1" value="iPhone 6 16G(1명) 당첨자 보기" onclick="fnViewPrizeWinners(1);" class="btnWinnerPrize" />
<%
        } else {
%>
                                    <input type="button" id="oDrawBtn1" value="iPhone 6 16G(1명) 추첨하기" onclick="fnDoLuckyDraw(1, 1, 10);" class="btnDraw" />
<%
        }
%>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="table_02_6" style="text-align:center;" id="oPrizeWinnersLevel1">
                    </td>
                </tr>

                <tr>
                    <td class="table_01">
                        <table width="99%">
                            <tr>
                                <td style="text-align:center;" id="oBtnArea2">
<%
        if ( lastDrawLevel > 1 ) {
%>
                                    <input type="button" id="oDrawBtn2" value="신세계 상품권(3명) 당첨자 보기" onclick="fnViewPrizeWinners(2);" class="btnWinnerPrize" />
<%
        } else {
%>
                                    <input type="button" id="oDrawBtn2" value="신세계 상품권(3명) 추첨하기" onclick="fnDoLuckyDraw(2, 3, 8);" <%= (lastDrawLevel < 1) ? "class=\"btnDisabled\" disabled=\"disabled\"" : "class=\"btnDraw\"" %> />
<%
        }
%>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="table_02_6" style="text-align:center;" id="oPrizeWinnersLevel2">
                    </td>
                </tr>

                <tr>
                    <td class="table_01">
                        <table width="99%">
                            <tr>
                                <td style="text-align:center;" id="oBtnArea3">
<%
        if ( lastDrawLevel > 2 ) {
%>
                                    <input type="button" id="oDrawBtn3" value="2015년 프랭클린 플래너(10명) 당첨자 보기" onclick="fnViewPrizeWinners(3);" class="btnWinnerPrize" />
<%
        } else {
%>
                                    <input type="button" id="oDrawBtn3" value="2015년 프랭클린 플래너(10명) 추첨하기" onclick="fnDoLuckyDraw(3, 10, 6);" <%= (lastDrawLevel < 2) ? "class=\"btnDisabled\" disabled=\"disabled\"" : "class=\"btnDraw\"" %> />
<%
        }
%>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="table_02_6" style="text-align:center;" id="oPrizeWinnersLevel3">
                    </td>
                </tr>

                <tr>
                    <td class="table_01">
                        <table width="99%">
                            <tr>
                                <td style="text-align:center;" id="oBtnArea4">
<%
        if ( lastDrawLevel > 3 ) {
%>
                                    <input type="button" id="oDrawBtn4" value="바나나맛 우유(100명) 당첨자 보기" onclick="fnViewPrizeWinners(4);" class="btnWinnerPrize" />
<%
        } else {
%>
                                    <input type="button" id="oDrawBtn4" value="바나나맛 우유(100명) 추첨하기" onclick="fnDoLuckyDraw(4, 100);"  <%= (lastDrawLevel < 3) ? "class=\"btnDisabled\" disabled=\"disabled\"" : "class=\"btnDraw\"" %> />
<%
        }
%>

                                </td>
                            </tr>
                        </table>
                    </td>
                    <td class="table_02_6" style="text-align:center;" id="oPrizeWinnersLevel4">
                    </td>
                </tr>

            </table>
            <!-- //-->

            <br/>
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</div>
<div class="divPopupMask"></div>

</body>
</html>

<%
    }
%>

<%!
    private int getLastDrawLevel() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sql = new StringBuilder();

        int lastLevel = 0;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  NVL( MAX(WIN_LEVEL), 0 )  AS LAST_LEVEL FROM  TZ_EVENT_APPLICANT WHERE SEQ = 2 \n");

            ls = connMgr.executeQuery(sql.toString());

            if ( ls.next() ) {
                lastLevel = ls.getInt("last_level");
            }

        } catch(Exception e) {
        } finally {
            if ( ls != null ) {
                ls.close();
                ls = null;
            }
            if ( connMgr != null ) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }

        return lastLevel;
    }
%>