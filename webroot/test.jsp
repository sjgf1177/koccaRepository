<%@ page contentType="text/html; charset=euc-kr" import="java.util.*, javax.mail.*, javax.mail.internet.*, com.credu.library.*" %>
<%!
    public String replace(String as_Data, String as_String, String as_Change)
    {
        String s_Data = "";
        String s_Tmp = as_Data;
        int i = s_Tmp.indexOf(as_String);

        while (i!= -1)
        {
            if(!s_Tmp.substring(0,i+1).equals("")) {
                s_Data = s_Data + s_Tmp.substring(0,i) + as_Change;
            }
            s_Tmp = s_Tmp.substring(i+as_String.length());
            i = s_Tmp.indexOf(as_String);
        }
        s_Data = s_Data + s_Tmp;
        return s_Data;
    }
%>
<%
        String as_Data = "abcabdabab";

        String s_Data = "";
        String s_Tmp = as_Data;
        int i = s_Tmp.indexOf("ab");

        while (i!= -1)
        {
            if(!s_Tmp.substring(0,i).equals("")) {
                s_Data = s_Data + s_Tmp.substring(0,i) + "X";
            }
            s_Tmp = s_Tmp.substring(i+"ab".length());
            i = s_Tmp.indexOf("ab");
        }
        s_Data = s_Data + s_Tmp;
        out.print("s_Data=>"+s_Data);

        String[] mobileArr = {"iphone", "ipod", "ipad", "blackberry", "android", "windows ce", "lg", "mot", "samsung", "sonyericsson"};

        String currentURL = request.getRequestURL().toString();
        String userAgent = request.getHeader("User-Agent");

        boolean isMobile = false;
        out.println("현재 URL : " + currentURL + "<br/>");
        out.println("요청 agent 정보 : " + userAgent + "<br/>");
        out.println("모바일 여부 : " + isMobile + "<br/>");

        for ( int j = 0 ; j < mobileArr.length; j++ ) {
            if ( userAgent.toLowerCase().indexOf( mobileArr[j]) > -1) {
                isMobile = true;
                System.out.println("isMobile is true. You're connecting from " + mobileArr[j] + "!!!");
                break;
            }
        }
        out.println("모바일 여부 어게인 : " + isMobile + "<br/>");

        if (currentURL.indexOf("songpa.kocca.or.kr") > -1) {
            out.println("<a href='http://songpa.edukocca.or.kr/servlet/controller.homepage.MainServlet'>송파구청으로 이동하기</a><br/>");
        } else {
            if ( isMobile ) {
                out.println("<a href='/servlet/controller.mobile.openclass.OpenClassPopularServlet'>한국콘텐츠아카데미 모바일 화면으로 이동하기</a><br/>");
            } else {
                out.println("<a href='/servlet/controller.homepage.MainServlet'>한국콘텐츠아카데미 PC Web 화면으로 이동하기</a><br/>");
            }
        }
%>


<html lang="ko">
<head>
<title>한국콘텐츠아카데미 TEST</title>
</head>
<body>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--
    function goMain() {
        var url = document.location.href;

        var actionURL = "";
        var isMobile = false;

        var mobileKeyWords = new Array("iPhone", "iPod", "iPad", "BlackBerry", "Android", "Windows CE", "LG", "MOT", "SAMSUNG", "SonyEricsson");

        if (url.indexOf("songpa.kocca.or.kr") > -1) {
            actionURL = "http://songpa.edukocca.or.kr/servlet/controller.homepage.MainServlet";
        } else {

            for (var word in mobileKeyWords){
                if (navigator.userAgent.match(mobileKeyWords[word]) != null){
                    isMobile = true;
                    break;
                }
            }

            if ( isMobile ) {
                actionURL = "http://m.edu.kocca.kr/servlet/controller.mobile.openclass.OpenClassPopularServlet";
            } else {
                actionURL = "/servlet/controller.homepage.MainServlet";
            }
        }

        // document.form1.action = actionURL;
        // document.form1.submit();
        location.href = actionURL;
    }

    $(document).ready(function() {
        goMain();
    });
//-->
</Script>

<form id='form1' name="form1">
</form>

<!--
<kocca_select:select name="p_membergubun" sqlNum="membergubun"  param=" "
    onChange="test1(selectedValue);" attr=" 회원구분" selectedValue=" " isLoad="true" all="true" type="3" styleClass="table_title"/>
 -->

</body>
</html>
