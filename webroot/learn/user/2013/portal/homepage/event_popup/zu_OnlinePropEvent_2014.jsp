<%@page contentType="text/html;charset=euc-kr" %>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.credu.library.DBConnectionManager" %>
<%@page import="com.credu.library.ListSet" %>
<%
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
<title>수강신청자 이벤트 || 한국콘텐츠아카데미</title>
<meta property="og:title" content="한국콘텐츠아카데미 수강신청자 이벤트">
<meta property="og:url" content="http://edu.kocca.or.kr/learn/user/2013/portal/homepage/event_popup/zu_OnlinePropEvent_2014.jsp">
<meta property="og:description" content="모일수록 선물이 쏟아진다. 한국콘텐츠아카데미 수강신청 선물폭타 이벤트. 수강신청자 수가 많으면 많을 수록 더 많은 선물이 쏟아집니다! 무료로 강의도 듣고 iPad mini2도 받으세요!">
<meta property="og:image" content="http://edu.kocca.or.kr/images/event/2014/2014_online_prop_event.jpg">

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<style>
    html { overflow-x: hidden; overflow-y: scroll; } 
    body{width:700px;}
    .bg {position:absolute; left:0px; top:0px; width:700px; height:2100px; z-index:1;}
    div.box {position:absolute; left:33px; top:58px; }
    div.box input.number_input {position:absolute; z-index:9; left:32; top:60; width:25; background:transparent; border:0; font-size:37px; font-weight:bold; color:#fff; }
    div.box input.number_input2 {position:absolute; z-index:9; left:313; top:1190; width:25; background:transparent; border:0; font-size:37px; font-weight:bold; color:#fff; }

    div.box2 {position:absolute; left:315px; top:1189px; }
    div.box2 input.number_input {position:absolute; z-index:9; left:32; top:60; width:25; background:transparent; border:0; font-size:37px; font-weight:bold; color:#fff; }
    div.box2 input.number_input2 {position:absolute; z-index:9; left:313; top:1190; width:25; background:transparent; border:0; font-size:37px; font-weight:bold; color:#fff; }
</style>
<script src="/js/jquery/1.8.3/jquery.js"></script>
<script src="/script/mainscript.js"></script>
<script>
    var end = new Date("2014/12/01 00:00:00");

    var _second = 1000;
    var _minute = _second * 60;
    var _hour = _minute * 60;
    var _day = _hour * 24;
    var timer;

    var now = new Date("<%= sdf.format(dt) %>");
    var temp;

    var days = 0, hours = 0, minutes = 0, seconds = 0;
    var strDays = "", strHours = "", strMinutes = "", strSeconds = "";

    function fnShowRemaining() {
        temp = now.getTime();
        var distance = end - now;
        if (distance < 0) {

            clearInterval(timer);
            // document.getElementById('countdown').innerHTML = 'EXPIRED!';
            alert("이벤트가 종료되었습니다.");
            self.close();

            return;
        }
        days = Math.floor(distance / _day);
        hours = Math.floor((distance % _day) / _hour);
        minutes = Math.floor((distance % _hour) / _minute);
        seconds = Math.floor((distance % _minute) / _second);

        strDays = ( days > 9 ) ? "" + days : "0" + days;
        strHours = ( hours > 9 ) ? "" + hours : "0" + hours;
        strMinutes = ( minutes > 9 ) ? "" + minutes : "0" + minutes;
        strSeconds = ( seconds > 9 ) ? "" + seconds : "0" + seconds;

        $("#oDays1").val( strDays.substring(0, 1) );
        $("#oDays2").val( strDays.substring(1, 2) );

        $("#oHours1").val( strHours.substring(0, 1) );
        $("#oHours2").val( strHours.substring(1, 2) );

        $("#oMinutes1").val( strMinutes.substring(0, 1) );
        $("#oMinutes2").val( strMinutes.substring(1, 2) );

        $("#oSeconds1").val( strSeconds.substring(0, 1) );
        $("#oSeconds2").val( strSeconds.substring(1, 2) );

        now.setTime(temp + 1000);
    }

    timer = setInterval(fnShowRemaining, 1000);

    function fnSetPropCnt() {
        var propCnt = "<%= this.getPropCount() %>";
        $("#oPropCnt1").val( propCnt.substring(0, 1) );
        $("#oPropCnt2").val( propCnt.substring(1, 2) );
        $("#oPropCnt3").val( propCnt.substring(2, 3) );
        $("#oPropCnt4").val( propCnt.substring(3, 4) );
        $("#oPropCnt5").val( propCnt.substring(4, 5) );
    }

    $(function() {
        fnShowRemaining();
        fnSetPropCnt();
    });

    /**
     * SNS 공유하기
     */
    function fnShareOnSNS( snsName ) {
        var url = location.href;

        fnSendSns( snsName, url, "한국콘텐츠아카데미 수강신청자 이벤트");
    }


</script>

</head>

<body>

    <div class="bg">
        <img src="/images/event/2014/2014_online_prop_event.jpg" width="700" usemap="#Map">
    </div>

    <!--일-->
    <div class="box">
        <input type="text" value="0" class="number_input" id="oDays1" readonly />
    </div>
    <div class="box">
        <input type="text" value="0" class="number_input" id="oDays2" style="margin-left:52px;" readonly />
    </div>

    <!--시-->
    <div class="box">
        <input type="text" value="0" class="number_input" id="oHours1" style="margin-left:190px;" readonly />
    </div>
    <div class="box">
        <input type="text" value="0" class="number_input" id="oHours2" style="margin-left:242px;" readonly />
    </div>

    <!--분-->
    <div class="box">
        <input type="text" value="0" class="number_input" id="oMinutes1" style="margin-left:318px;" readonly />
    </div>
    <div class="box">
        <input type="text" value="0" class="number_input" id="oMinutes2" style="margin-left:370px;" readonly />
    </div>

    <!--초-->
    <div class="box">
        <input type="text" value="0" class="number_input" id="oSeconds1" style="margin-left:443px;" readonly />
    </div>
    <div class="box">
        <input type="text" value="0" class="number_input" id="oSeconds2" style="margin-left:495px;" readonly />
    </div>


    <!--현재인원-->
    <div class="box2">
        <input type="text" value="0" class="number_input2" id="oPropCnt1" readonly />
    </div>
    <div class="box2">
        <input type="text" value="0" class="number_input2" id="oPropCnt2" style="margin-left:56px;" readonly />
    </div>
    <div class="box2">
        <input type="text" value="0" class="number_input2" id="oPropCnt3" style="margin-left:109px;" readonly />
    </div>
    <div class="box2">
        <input type="text" value="0" class="number_input2" id="oPropCnt4" style="margin-left:162px;" readonly />
    </div>
    <div class="box2">
        <input type="text" value="0" class="number_input2" id="oPropCnt5" style="margin-left:215px;" readonly />
    </div>



    <map name="Map">
        <!--페이스북-->
        <area shape="rect" coords="503,1739,549,1783" href="javascript:fnShareOnSNS('facebook');">
        <!--트위터-->
        <area shape="rect" coords="563,1739,609,1783" href="javascript:fnShareOnSNS('twitter');">
        <!--네이버-->
        <!-- <area shape="rect" coords="581,1739,619,1777" href="#" target="_blank"> //-->
    </map>

</body>

</html>
<%!
    private String getPropCount() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sql = new StringBuilder();

        String propCnt = "00000";

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  LPAD(COUNT(B.USERID), 5, '0') AS CNT                  \n");
            sql.append("  FROM  TZ_SUBJSEQ A                                          \n");
            sql.append("    ,   TZ_PROPOSE B                                          \n");
            sql.append(" WHERE  A.GRCODE = 'N000001'                                  \n");
            sql.append("   AND  A.GYEAR = '2014'                                      \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH') BETWEEN A.PROPSTART    \n");
            sql.append("                                           AND A.PROPEND      \n");
            sql.append("   AND  A.YEAR = B.YEAR                                       \n");
            sql.append("   AND  A.SUBJ = B.SUBJ                                       \n");
            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ                                 \n");

            ls = connMgr.executeQuery(sql.toString());

            if ( ls.next() ) {
                propCnt = ls.getString("cnt");
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

        return propCnt;
    }
%>
