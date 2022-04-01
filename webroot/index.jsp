<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.library.*,com.credu.library.RequestBox" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
  RequestBox box = (RequestBox)request.getAttribute("requestbox");
  if (box == null) {
	  box = RequestManager.getBox(request);
	  
  }
  box.setSession("tem_grcode", null);
%>
<html lang="ko">
<head>
<title>한국콘텐츠아카데미</title>
<meta charset="euc-kr">
</head>
<body>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" >
	function goMain() {
        var url = document.location.href;

        var actionURL = "";
        var isMobile = false;

        var mobileKeyWords = new Array("iPhone", "iPod", "iPad", "BlackBerry", "Android", "Windows CE", "LG", "MOT", "SAMSUNG", "SonyEricsson");

        for (var word in mobileKeyWords){
            if (navigator.userAgent.match(mobileKeyWords[word]) != null){
                isMobile = true;
                break;
            }
        }
        alert("url : " + url);
        if (url.indexOf("songpa.kocca.or.kr") > -1) { // 송파구청
            actionURL = "http://songpa.edukocca.or.kr/servlet/controller.homepage.MainServlet";

        } else if (url.indexOf("edum.kocca.or.kr") > -1) { // 구 모바일
            actionURL = "/servlet/controller.mobile.main.MainServlet";
		} else if (url.indexOf("edulms.kocca.kr") > -1) {
			actionURL = "http://edu.kocca.kr";
		} else if (url.indexOf("testbed.kocca.kr") > -1) {
			if ( isMobile )
				actionURL = "https://edu.kocca.kr/edumobile/main/main.do?siteName=testbed";
			else 
				actionURL = "https://edu.kocca.kr/testbed/main/main.do";
			/* if ( isMobile )
				if (  url.indexOf("edumobile") == -1 )
					actionURL = "https://testbed.kocca.kr/edumobile/main/main.do?siteName=testbed&menuNo=600125";
				else
					return;
			else
				actionURL = "https://testbed.kocca.kr/testbed/main/main.do";  */
				
        } else {

            if ( isMobile ) {
                /* actionURL = "http://m.edu.kocca.kr/servlet/controller.mobile.openclass.OpenClassPopularServlet"; */
            	actionURL = "/servlet/controller.homepage.MainServlet";
            } else { 
                actionURL = "/servlet/controller.homepage.MainServlet";
            } 
        }
				if (url.indexOf("edu.kocca.or.kr") > -1) {
				    actionURL = "http://edu.kocca.kr";		
				}
        location.href = actionURL;
	}

	$(document).ready(function() {
		goMain();
	});
</Script>

<form id="form1" name="form1">
</form>
</body>
</html>
