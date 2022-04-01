<%
/**
 * file name : mScreenFbody.jsp
 * date      : 2003/7/11
 * programmer: LeeSuMin
 * function  : manager screen Initial Body
 */
%>
<%@ page contentType="text/html;charset=MS949" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    String ss_grcode = box.getStringDefault("s_grcode","N000001");        //교육그룹
    String ss_gyear = box.getString("s_gyear");            //년도
    String ss_grseq = box.getString("s_grseq");            //교육차수

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    Date dt = new Date();
    int currYear = Integer.parseInt( sdf.format(dt) );
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/style.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="/css/jqueryui/1.9.2/themes/redmond/jquery-ui.css" />
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.9.2/jquery-ui.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="/js/assets/prettify.js"></script>
<script type="text/javascript">
<!--
    function winopen() {
        popwin = window.open("mMenutempLeft.jsp", "openAdminpop", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=200,height=700,top=0,left=0");
    }

    function pop_SetStat(){
        popwin = window.open("/learn/admin/statistics/main/za_SettingStat.jsp", "openSetStat", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=600,height=700,top=0,left=0");
    }

    $(document).ready(function() {

        $('#b_status1_hide, #b_status1_show, #b_status1').click(function(e) {
            if ($(e.target).is("#b_status1")) {  // 정규강좌
                var s2_grcode = $("#oGrcode").val();
                var s2_gyear = $("#oGyear").val();
                var s2_grseq = $("#oGrseq").val();

                if(s2_grcode == "") {
                    alert("교육그룹을 선택하세요.");
                    return false;
                }
                if(s2_gyear == "") {
                    alert("교육년도을 선택하세요.");
                    return false;
                }

                $.ajax({ type : "POST",
                    url : "/servlet/controller.statistics.MainStatusticServlet?p_process=selectStat",
                    data: "&param1="+s2_grcode+"&param2="+s2_gyear+"&param3="+s2_grseq,
                    beforeSend: function(){
                        $("#status1").show();
                        $('#status1').html('<img src="/learn/user/contents/loading.gif" border="0"/>');
                    },
                    success: function(msg){
                        if(msg.length > 0) {
                             //$('#goldclass_stat').html(msg);
                             $("#status1").children().remove();
                             $("#status1").append(msg);
                        }
                    }
                });//end ajax
            }
            if ($(e.target).is("#b_status1_hide")) {  // 정규강좌 접기
                $("#status1").hide();
            }
            if ($(e.target).is("#b_status1_show")) {  // 정규강좌 펼치기
                $("#status1").show();
            }
            return false;
        });

        $('#b_status2, #b_status2_hide, #b_status2_show').click(function(e) {
            if ($(e.target).is("#b_status2")) {  // 열린강좌
                var search2_year = $("#status2_year").val();
                $.ajax({ type : "POST",
                    url : "/servlet/controller.statistics.MainStatusticServlet?p_process=selectStat2",
                    data: "&param1="+search2_year,
                    beforeSend: function(){
                            $("#status2").show();
                            $("#status2").html('<img src="/learn/user/contents/loading.gif" border="0"/>');
                    },
                    success: function(msg){
                        if(msg.length > 0) {
                             //$('#goldclass_stat').html(msg);
                             $("#status2").children().remove();
                             $("#status2").append(msg);
                        }
                    }
                });//end ajax
            }
            if ($(e.target).is("#b_status2_hide")) {  // 열린강좌
                $("#status2").hide();
            }
            if ($(e.target).is("#b_status2_show")) {  // 열린강좌
                $("#status2").show();
            }
            return false;
        });

        $('#b_status3, #b_status3_hide, #b_status3_show').click(function(e) {
            if ($(e.target).is("#b_status3")) {  // 튜터
                var search3_year = $("#status3_year").val();
                var status3_gubun = $("#status3_gubun").val();
                $.ajax({ type : "POST",
                    url : "/servlet/controller.statistics.MainStatusticServlet?p_process=selectStat3",
                    data: "&param1="+search3_year+"&param2="+status3_gubun,
                    beforeSend: function(){
                            $("#status3").show();
                            $('#status3').html('<img src="/learn/user/contents/loading.gif" border="0"/>');
                    },
                    success: function(msg){
                        if(msg.length > 0) {
                             //$('#goldclass_stat').html(msg);
                             $("#status3").children().remove();
                             $("#status3").append(msg);
                        }
                    }
                });//end ajax
            }
            if ($(e.target).is("#b_status3_hide")) {  // 튜터
                $("#status3").hide();
            }
            if ($(e.target).is("#b_status3_show")) {  // 튜터
                $("#status3").show();
            }
            return false;
        });

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

    }

    );



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
//-->
</script>
</head>

<body>
<!-- table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td valign="top" align="center" style="background-color: #dfdfdf;">
        <b>메뉴가 안보일경우 <a href="javascript:winopen();">여기</a>를 클릭하시기 바랍니다.(임시메뉴)</b>
    </td>
  </tr>
</table-->


<form name = "form1" method="post">

<!-- 과정 통계 시작 -->

<div style="text-align: left;">
    <strong style="font-size:15px;">과정운영 통계</strong><br/>
    <b>교육그룹</b> : <kocca:selectBox name="grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" isLoad="true" />
    &nbsp;&nbsp;
    <b>교육연도</b> :
        <select name="gyear" id="oGyear">
            <option value="">-- 선택 --</option>
        </select>
    &nbsp;&nbsp;
    <b>교육차수</b> :
        <select name="grseq" id="oGrseq">
            <option value="">-- 선택 --</option>
        </select>
    &nbsp;&nbsp;
    <button id="b_status1">검색</button>&nbsp;<button id="b_status1_hide">접기</button>&nbsp;<button id="b_status1_show">펼치기</button>&nbsp;&nbsp;&nbsp;<button onclick="javascript:pop_SetStat();">통계사용설정</button>
</div>
<br/>

<div id="status1" style="text-align: center;">
    <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
            <td colspan="9" class="table_top_line"></td>
        </tr>
        <tr>
            <td class="table_title" width="10%" height="25"><b>년도</b></td>
            <td class="table_title"><b>교육차수명</b></td>
            <td class="table_title" width="8%"><b>과정수</b></td>
            <td class="table_title" width="8%"><b>신청수</b></td>
            <td class="table_title" width="8%"><b>승인수</b></td>
            <td class="table_title" width="8%"><b>반려수</b></td>
            <td class="table_title" width="8%"><b>수료</b></td>
            <td class="table_title" width="8%"><b>미수료</b></td>
            <td class="table_title" width="8%"><b>수료률</b></td>

        </tr>
        <tr>
            <td colspan="9" class="table_02_1">
            조건 선택 후 검색버튼을 클릭해주세요
            </td>
        </tr>
    </table>
</div>
<!-- 과정 통계 끝 -->
<hr></hr>
<br/>

<!-- 열린강좌 통계 시작 -->
<div style="text-align: left;">
    <strong style="font-size:15px;">열린강좌 통계</strong><br/>
    <b>검색연도</b> : <select name="status2_year" id="status2_year">
<%
    for( int i = currYear; i > 2011  ; i-- ) {
%>
        <option value="<%= i %>" ><%= i %></option>
<%
    }
%>
    </select>&nbsp;&nbsp;
    <button id="b_status2">검색</button>&nbsp;<button id="b_status2_hide">접기</button>&nbsp;<button id="b_status2_show">펼치기</button>
</div><br/>
<div id="status2" style="text-align: center;">
    <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
            <td colspan="14" class="table_top_line"></td>
        </tr>
        <tr>
            <td class="table_title" width="9%" height="25"><b>분류</b></td>
            <td class="table_title" width="7%"><b>1월</b></td>
            <td class="table_title" width="7%"><b>2월</b></td>
            <td class="table_title" width="7%"><b>3월</b></td>
            <td class="table_title" width="7%"><b>4월</b></td>
            <td class="table_title" width="7%"><b>5월</b></td>
            <td class="table_title" width="7%"><b>6월</b></td>
            <td class="table_title" width="7%"><b>7월</b></td>
            <td class="table_title" width="7%"><b>8월</b></td>
            <td class="table_title" width="7%"><b>9월</b></td>
            <td class="table_title" width="7%"><b>10월</b></td>
            <td class="table_title" width="7%"><b>11월</b></td>
            <td class="table_title" width="7%"><b>12월</b></td>
            <td class="table_title" width="7%"><b>합계</b></td>

        </tr>
        <tr>
            <td colspan="14" class="table_02_1">
            조회하실 년도를 선택 후 검색버튼을 클릭해주세요
            </td>
        </tr>
    </table>
</div>
<!-- 열린강좌 통계 끝 -->
<hr></hr>
<br/>

<!-- 튜터 통계 시작 -->
<div style="text-align: left;">
    <strong style="font-size:15px;">튜터활동 통계</strong><br/>
    <b>검색구분</b> : <select name="status3_gubun" id="status3_gubun">
        <option value="I">정보게시</option>
        <option value="Q">과정질문</option>
    </select>&nbsp;&nbsp;
    <b>검색연도</b> : <select name="status3_year" id="status3_year">
<%
    for( int i = currYear; i > 2011  ; i-- ) {
%>
        <option value="<%= i %>" ><%= i %></option>
<%
    }
%>
    </select>&nbsp;&nbsp;
    <button id="b_status3">검색</button>&nbsp;<button id="b_status3_hide">접기</button>&nbsp;<button id="b_status3_show">펼치기</button>
</div><br/>
<div id="status3" style="text-align: center;">
    <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
            <td colspan="14" class="table_top_line"></td>
        </tr>
        <tr>
            <td class="table_title" width="9%" height="25"><b>분류</b></td>
            <td class="table_title" width="7%"><b>1월</b></td>
            <td class="table_title" width="7%"><b>2월</b></td>
            <td class="table_title" width="7%"><b>3월</b></td>
            <td class="table_title" width="7%"><b>4월</b></td>
            <td class="table_title" width="7%"><b>5월</b></td>
            <td class="table_title" width="7%"><b>6월</b></td>
            <td class="table_title" width="7%"><b>7월</b></td>
            <td class="table_title" width="7%"><b>8월</b></td>
            <td class="table_title" width="7%"><b>9월</b></td>
            <td class="table_title" width="7%"><b>10월</b></td>
            <td class="table_title" width="7%"><b>11월</b></td>
            <td class="table_title" width="7%"><b>12월</b></td>
            <td class="table_title" width="7%"><b>합계</b></td>

        </tr>
        <tr>
            <td colspan="14" class="table_02_1">
            조회하실 년도를 선택 후 검색버튼을 클릭해주세요
            </td>
        </tr>
    </table>
</div>
<!-- 튜터 통계 끝 -->
<hr></hr>

</form>
</body>
</html>
