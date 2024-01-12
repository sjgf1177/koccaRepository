<%@ page contentType = "text/html;charset=euc-kr" %>
<jsp:include page="/learn/user/typeB/include/site_info.jsp" />
	<!DOCTYPE html>
	<html lang="ko">
	<head>
		<meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <title>¿¡µàÄÚÄ«-B2B</title>
	
	    <!-- Bootstrap-3.3.7 / Jquery UI -->
	    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet">
	    <!--<link href="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css" rel="stylesheet">-->
	    <!--<link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">-->

	    <script src="/common/js/jquery-3.3.1.min.js"></script>
	    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
	    <!--<script src="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>-->
	    <!--<script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>-->


		<!-- Bootstrap-5.3.1 -->
		<link href="/common/js/bootstrap-5.3.1-dist/css/bootstrap.css" rel="stylesheet">
		<script src="/common/js/bootstrap-5.3.1-dist/js/bootstrap.bundle.js"></script>


		<link href="/common/js/swiper/swiper.min.css" rel="stylesheet">
	    
	    <!-- Custom CSS -->
	    <link href="/common/css/custom.css" rel="stylesheet">
	    <%--<link href="/common/css/header.css" rel="stylesheet">--%>
	    <link href="/common/css/footer.css" rel="stylesheet">
	    
	    <!-- Custom JS -->
	    <script src="/common/js/custom.js"></script>
	    
	    <!-- ±âÁ¸ JS -->
	    <script type="text/javascript" src="/script/portal/common.js"></script>
		<script language="javascript" src="/script/flash_TagScript.js"></script>
		<script language="javascript" src="/script/mainscript.js"></script>

		<!-- Ãªº¿ JS-->
		<script>
			var ht = null;
			(function(id, scriptSrc, callback) {
				var d = document,
						tagName = 'script',
						$script = d.createElement(tagName),
						$element = d.getElementsByTagName(tagName)[0];

				$script.id = id;
				$script.async = true;
				$script.src = scriptSrc;

				if (callback) { $script.addEventListener('load', function (e) { callback(null, e); }, false); }
				$element.parentNode.insertBefore($script, $element);
			})('happytalkSDK', 'https://chat-static.happytalkio.com/sdk/happytalk.chat.v2.min.js', function() {
				ht = new Happytalk({
					siteId: '6000000472',
					siteName: '(ÁÖ)½ºÅè¹Ìµð¾î',
					categoryId: '175412',
					divisionId: '175413'
				});
			});
		</script>
	</head>
	<body>
		<jsp:include page="/learn/user/typeB/include/common.jsp" />
		<jsp:include page="/learn/user/typeB/include/newHeaderB.jsp" />