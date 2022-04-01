<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr" %>

<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
//<![CDATA[

function fnExcuteAppTest() {
    var openAt = new Date,
        uagentLow = navigator.userAgent.toLocaleLowerCase(),
        chrome25,
        kitkatWebview;

    // $("body").append("<iframe id='____koccalink____'></iframe>");
    $('#applinkDiv').html("<iframe id='____koccalink____'></iframe>");
    $("#____koccalink____").hide();

    setTimeout( function() {

        if (new Date - openAt < 1500) {
            if (uagentLow.search("android") > -1) {

                $("#____koccalink____").attr("src","https://play.google.com/store/apps/details?id=com.newin.kocca");

                // $("#____koccalink____").attr("src","market://details?id=com.newin.kocca");
            } else if (uagentLow.search("iphone") > -1) {
                location.replace("https://itunes.apple.com/us/app/kocca/id492151283?l=ko&ls=1&mt=8");
            }
        }
    }, 1000);

    if(uagentLow.search("android") > -1){

        chrome25 = uagentLow.search("chrome") > -1 && navigator.appVersion.match(/Chrome\/\d+.\d+/)[0].split("/")[1] > 25;
        kitkatWebview = uagentLow.indexOf("naver") != -1 || uagentLow.indexOf("daum") != -1;

        if (chrome25 && !kitkatWebview){

            document.location.href = "intent://applink?param=value#Intent;scheme=kocca-app;package=com.newin.kocca;end";
        } else{

            $("#____koccalink____").attr("src", 'kocca-app://');
        }

    } else if(uagentLow.search("iphone") || uagentLow.search("ipad") > -1){
        $("#____koccalink____").attr("src", 'kocca-app://?');
    }
}

//]]>
</script>
</head>
<body>

<a href="javascript:fnExcuteAppTest();">¾Û ½ÇÇà Test </a><br /><br />

<div id="applinkDiv"></div>

</body>
</html>
