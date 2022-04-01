<%
//**********************************************************
//  1. 제      목: 회원운영 통계 - 차수별 현황
//  2. 프로그램명 : za_TotalStat_L.jsp
//  3. 개      요: 과정운영 통계 - 차수 New
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.07
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    Date dt = new Date();
    int currYear = Integer.parseInt( sdf.format(dt) );
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/style.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="/css/jqueryui/1.8.2/themes/ui-lightness/jquery-ui.css" />
<style>
    table#cellp td { padding:3px 0px 3px 0px; }
    dl,dt,dd { padding:0; margin:0; list-style:none; }
</style>

<script type="text/javascript" src="/js/jquery/1.8.2/jquery.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.8.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="/js/assets/prettify.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/flash/swfobject.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/amcharts.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/amfallback.js"></script>
<script language="JavaScript">
<!--
// 검색

    $(document).ready(function() {

        // $('#b_year, #b_month, #b_age, #b_vocation, #b_location, #b_gender,#b_location, #b_weekly, #b_hourly, #b_homepage').click(function(e) {
        $('#b_month, #b_age, #b_vocation, #b_location, #b_gender,#b_location, #b_weekly, #b_hourly, #b_homepage').click(function(e) {
            //alert('start');
            var newyear =  $("#ss_year").val();
            var ss_month = $("#ss_month").val();
            var ss_category = $("#ss_category").val();

            /*
            if ($(e.target).is("#b_year")) {  // 회원가입누적
                $.ajax({ type : "POST",
                    url : "/servlet/controller.statistics.UserCountServlet?p_process=selectYearStat",
                    data: "&param="+newyear+"&param2="+ss_category,
                    beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
                    success: function(msg){
                        //alert(msg);
                        if(msg.length > 0) { $('#s_list').html(msg); }
                        $("select").multiselect();
                    }
                });//end ajax
            }
            */

            if ( newyear == null || newyear == "") {
                alert("년도를 선택하세요");
                $("#ss_year").focus();
                return;
            } else if ( ss_month == null || ss_month == "") {
                alert("월을 선택하세요");
                $("#ss_month").focus();
                return;
            } else {

                if ($(e.target).is("#b_month")) { // 월별
                     $.ajax({ type : "POST",
                        url : "/servlet/controller.statistics.UserCountServlet?p_process=selectMonthStat",
                            data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_category,
                            beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
                            success: function(msg){
                                if(msg.length > 0) { $('#s_list').html(msg); }
                                $("select").multiselect();
                            }
                        });//end ajax

                } else if ($(e.target).is("#b_age")) { // 연령별
                    $.ajax({ type : "POST",
                        url : "/servlet/controller.statistics.UserCountServlet?p_process=selectAgeStat",
                        data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_category,
                        beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
                        success: function(msg){
                            if(msg.length > 0) { $('#s_list').html(msg); }
                        }
                    });//end ajax

                } else if ($(e.target).is("#b_vocation")) { // 직업별
                    $.ajax({ type : "POST",
                        url : "/servlet/controller.statistics.UserCountServlet?p_process=selectVocationStat",
                        data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_category,
                        beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
                        success: function(msg){
                            if(msg.length > 0) { $('#s_list').html(msg); }
                        }
                    });//end ajax
                } else if ($(e.target).is("#b_location")) { // 지역별
                    $.ajax({ type : "POST",
                        url : "/servlet/controller.statistics.UserCountServlet?p_process=selectLocationStat",
                        data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_category,
                        beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
                        success: function(msg){
                            if(msg.length > 0) { $('#s_list').html(msg); }
                        }
                    });//end ajax
                } else if ($(e.target).is("#b_weekly")) { // 요일별
                    //alert('요일별');
                    $.ajax({ type : "POST",
                        url : "/servlet/controller.statistics.UserCountServlet?p_process=selectWeekStat",
                        data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_category,
                        beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
                        success: function(msg){
                            if(msg.length > 0) { $('#s_list').html(msg); }
                        }
                    });//end ajax
                } else if ($(e.target).is("#b_hourly")) { // 시간별
                    $.ajax({ type : "POST",
                        url : "/servlet/controller.statistics.UserCountServlet?p_process=selectHourStat",
                        data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_category,
                        beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
                        success: function(msg){
                            if(msg.length > 0) { $('#s_list').html(msg); }
                        }
                    });//end ajax
                } else if ($(e.target).is("#b_homepage")) { // 홈페이지 통게}

                    var ulink = "https://www.google.com/analytics/web/?et=&hl=ko&authuser=#report/visitors-overview/a33104445w60296830p61666125/";
                    var new_Open = window.open(ulink,"google",'scrollbars=yes,width=1024,height=764,scrollbars=yes,resizable=no');
                }
            }

        });

        $("select").multiselect({
            selectedList: 5
        });

    });

//-->
</script>

</head>

<body id="test" onload="prettyPrint();" bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<table width="1000" border="0" cellspacing="0" cellpadding="0" >
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td align="left"><img src="/images/admin/statistics/tit_sum_d2.gif"></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
        </td>
    </tr>
    <tr>
        <td align="center" valign="top">

            <br/>
            <!-- form 시작 //-->

            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                            <tr>
                                <td height="7"></td>
                            </tr>
                            <tr>
                                <td align="left">
                                    <table id="cellp" border="0" cellspacing="0" cellpadding="0" width="100%"  class="form_table">
                                        <tr>
                                            <td align="left" valign="middle">&nbsp;</td>
                                            <td align="left" valign="middle"> 년도별</td>
                                            <td align="left" valign="middle" width="200">
                                                <select name="ss_year" id="ss_year" multiple="multiple" >
<%
            for ( int i = currYear ; i >= 2002 ; i-- ) {
%>
                                                    <option value="<%= i %>"><%= i %></option>
<%
            }
%>
                                                </select>
                                            </td>

                                            <td align="left" valign="middle">월   별</td>
                                            <td align="left" valign="middle">
                                                <select title="월선택" multiple="multiple" name="ss_month" id="ss_month" size="5" >
                                                    <option value="01">1월</option>
                                                    <option value="02">2월</option>
                                                    <option value="03">3월</option>
                                                    <option value="04">4월</option>
                                                    <option value="05">5월</option>
                                                    <option value="06">6월</option>
                                                    <option value="07">7월</option>
                                                    <option value="08">8월</option>
                                                    <option value="09">9월</option>
                                                    <option value="10">10월</option>
                                                    <option value="11">11월</option>
                                                    <option value="12">12월</option>
                                                </select>
                                            </td>
                                            <td align="left" valign="middle">분류</td>
                                            <td align="left" valign="middle">
                                                <select name="ss_category" multiple="multiple" id="ss_category" size="8">
                                                    <optgroup label="B2C" >
                                                    <option value="01">일반</option>
                                                    </optgroup>
                                                    <optgroup label="B2B">
                                                    <option value="02">공공</option>
                                                    <option value="03">기업</option>
                                                    <option value="04">대학</option>
                                                    <option value="05">특화고</option>
                                                    </optgroup>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left" valign="middle">&nbsp;</td>
                                            <td align="left" valign="middle" colspan="6" >
                                                <dl>
                                                    <!-- <dd style="float:left;"><button id="b_year">회원가입</button></dd> //-->
                                                    <dd style="float:left; margin-left:5px;"><button id="b_month">회원가입</button> </dd>
                                                    <dd style="float:left; margin-left:5px;"><button id="b_age">연령별 통계</button></dd>
                                                    <!--
                                                    <dd style="float:left; margin-left:5px;"><button id="b_vocation">직업별 통계</button></dd>
                                                    <dd style="float:left; margin-left:5px;"><button id="b_location">지역별 통계</button></dd>
                                                    //-->
                                                    <!-- <dd style="float:left; margin-left:5px;"><button id="b_weekly">요일별 통계</button></dd> //-->
                                                    <!-- <dd style="float:left; margin-left:5px;"><button id="b_hourly">시간별  통계</button></dd> //-->
                                                    <dd style="float:left; margin-left:5px;"><button id="b_homepage">홈페이지 통계</button></dd>
                                                </dl>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <br/>

            <!-- 엑셀출력 버튼 시작 //-->
            <div id="s_list">
                <table width="97%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="left" height="20">&nbsp;<b>종합현황</b></td>
                        <td align="right">
                            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
                            <!-- <button id="exCel">엑셀출력</button> -->
                        </td>
                    </tr>
                    <tr>
                        <td height="3"></td>
                    </tr>
                </table>
                <!-- 조회 시작 //-->

                <table class="table_out" cellspacing="1" cellpadding="5">
                    <tr>
                        <td colspan="8" class="table_top_line"></td>
                    </tr>
                    <tr>
                        <td class="table_title"><b>No</b></td>
                        <td class="table_title"><b>연도</b></td>
                        <td class="table_title"><b>남</b></td>
                        <td class="table_title"><b>여</b></td>
                        <td class="table_title"><b>미확인</b></td>
                        <td class="table_title"><b>총인원</b></td>
                    </tr>
                    <tr>
                        <td align="center" bgcolor="#FFFFFF" height="100" colspan="6">등록된 내용이 없습니다.</td>
                    </tr>
                </table>
            </div>
            <!-- 과정기준조회 끝 //-->
            <br/>
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>