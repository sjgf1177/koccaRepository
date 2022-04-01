<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="java.net.InetAddress" %>
<%@ include file="/learn/user/portal/include/site_info.jsp" %>
<%
    InetAddress inet= InetAddress.getLocalHost();
    String ipSplit = inet.getHostAddress();
    String [] ipnum=ipSplit.replace(".","/").split("/");

    String v_indexURLGLB = "";
    String s_tem_grcodeGLB = box.getSession("tem_grcode");
    
    String naviType=box.getSession("tem_menu_type");  //메뉴 네비게이션 타입
    String mainType=box.getSession("tem_main_type");  //메인 화면 타입
    
    boolean newAsp = mainType.equals("A") || mainType.equals("B") ? true : false;

    if (koccaYn) {
        v_indexURLGLB = "/indexGame.jsp";
    } else {
        v_indexURLGLB = "/index" + s_tem_grcodeGLB + ".jsp";
    }

    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");

    if (topWarp_gadmin.equals("")) {
        box.setSession("gadmin", "ZZ");
        topWarp_gadmin = box.getSession("gadmin");
    }

    String referer ="4124124"+ request.getHeader("REFERER");
    if(referer.indexOf("http://localhost")>0 || referer.indexOf("http://lms.kocca.or.kr")>0 || referer.indexOf("http://172.16.80.75")>0 || referer.indexOf("http://edu.kocca.or.kr")>0)
        referer="Y";
    else
        referer="";

    String mainYN=box.get("mainYn");
    String init_margin="";
    String init_margin1="";
    String init_margin2="";
    if(mainYN.equals("")){
        if(s_tem_grcodeGLB.equals("N000001")){
            init_margin="8";
            init_margin1="0";
            init_margin2="34";
        }else{
            init_margin="30";
            init_margin1="10";
            init_margin2="30";
        }
    }else{
        init_margin="20";
        init_margin1="5";
        init_margin2="30";
    }

    //asp 
    String ser_nm=request.getServerName();
    String[] rejectGroupList = {"mcst.kocca.or.kr", "mcst.edukocca.or.kr", "kbs.edukocca.or.kr", "kocuco-hi.edukocca.or.kr", "narasarang.edukocca.or.kr"};

    String rejectResult = "0";

    for ( int asldfkasldkf = 0 ; asldfkasldkf < rejectGroupList.length; asldfkasldkf++ ) {

        if ( ser_nm.indexOf( rejectGroupList[asldfkasldkf]) > -1) {
            rejectResult = "1";
            break;
        }
    }

    boolean isReject = rejectResult.equals("1") ? true : false;
    
	TempletBean templetBeans = new TempletBean();
    
    List mainMenuList = templetBeans.getMainMenuList(box.getSession("tem_grcode"));
    
    int mainListSize = mainMenuList != null ? mainMenuList.size() : 0 ;
    
    String mMenuGrtype = "";
    String mMenuGubun = "";
    String mMenuId = "";
    String mMenuName = "";
    String mMenuUrl = "";
    String mMenuImgPath = "";
    String mMenuImg = "";
    String mMenuOverImg = "";
    String mMenuSubMenuHomeImg = "";
    String mSubImg = "";
    String mSubOverImg = "";
    int mMenuxposition = 0;
    String msgPosition = "";
    List subMenuList = templetBeans.getSubMenuList(box.getSession("tem_grcode"));

    String sMenuGubun = "";
    String sMenuId = "";
    String sMenuName = "";
    String sMenuUrl = "";
    String sMenuImg = "";
    String sMenuOverImg = "";
    String sMenuImgPath = "";
    String sSubImg = "";
    String sSubOverImg = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미</title>
<%if( box.getSession("tem_grcode").equals("N000001")) { %>
<link href="/css/portal/homepage_renewal/style.css" rel="stylesheet" type="text/css" />
<%}else{ %>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<%} %>
<%if(mainType.equals("A")){%>
	<link rel="stylesheet" href="/css/portal/asp/newaspSub_A.css?20161124" type="text/css" />
<%}else if(mainType.equals("B")){%>
	<link rel="stylesheet" href="/css/portal/asp/newaspSub_B.css" type="text/css" />
<%}%>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script language="javascript" src="/script/mainscript.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<!-- 구글 Analytics 로그분석 시작 -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  //_gaq.push(['_setAccount', 'UA-24782208-1']);
  _gaq.push(['_setAccount', 'UA-33104445-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<!-- 구글 Analytics 로그분석 시작 -->
</head>
<body <%=!koccaYn ? "id=\"asp\" class=\"asp_sub\"": "" %> 
      onload="MM_preloadImages('/images/newmain_20100625/top_m1_o.gif','/images/newmain_20100625/top_m2_o.gif','/images/newmain_20100625/top_m3_o.gif','/images/newmain_20100625/top_m4_o.gif','/images/newmain_20100625/top_m5_o.gif','/images/newmain_20100625/center_faq_o.gif','/images/newmain_20100625/center_down_o.gif','/images/newmain_20100625/center_book_o.gif','/images/newmain_20100625/center_qna_o.gif','/images/newmain_20100625/search_btn_o.gif','/images/newmain_20100625/cont_theme_o.gif','/images/newmain_20100625/cont_colum_o.gif','/images/newmain_20100625/btn_login_o.gif','/images/newmain_20100625/btn_myroom_o.gif','/images/newmain_20100625/btn_apply_o.gif','/images/newmain_20100625/quickm1_o.gif','/images/newmain_20100625/quickm2_o.gif','/images/newmain_20100625/quickm3_o.gif','/images/newmain_20100625/quickm4_o.gif')">
<!-- accessibility_menu -->
<ul id="accessibility_menu">
    <li><a href="#content">본문으로 넘어가기</a></li>
    <li><a href="#header">대 메뉴로 넘어가기</a></li>
    <li><a href="#banner">텍스트베너로 넘어가기</a></li>
    <li><a href="#footer">카피라이터로 넘어가기</a></li>
</ul>
<!--// accessibility_menu -->
<hr />

    <%box.put("mainYn", "N"); %>

<!-- header -->
<script language="javascript">
    window.name = "kocca_parent";
</script>

<%@ include file="/learn/user/portal/include/common.jsp" %>		
<div <%=newAsp ? mainType.equals("A") ? "id=\"wrap\"" : "id=\"wrap_sub\"" : "" %>><%-- wrap start --%>

	<div id="header<%=newAsp ? "_sub" : ""%>" <%=!koccaYn && !newAsp ? "class=\"asp_main\"" : "" %>>
	    <%if(newAsp){%>
	
		    <!-- header -->
		    <%if(mainType.equals("A")){%>
				<%@ include file="/learn/user/portal/include/newSubHeaderA.jsp" %>
		    <%}else{%>
				<%@ include file="/learn/user/portal/include/newSubHeaderB.jsp" %>
		    <%}%>
		    <!-- header -->
			    
		<%}else{%>
		    <!-- Top Warp 시작 -->
			<%@ include file="/learn/user/portal/include/topWarp.jsp" %>
			<!-- Top Warp 종료 -->
			
			<!-- Top Menu 시작 -->
			<%@ include file="/learn/user/portal/include/topMenu.jsp" %>
		    <!-- Top Menu 종료 -->	
		    
		    <!-- Top Visual 시작 -->
		    <%@ include file="/learn/user/portal/include/topVisual.jsp" %>
		    <!-- Top Visual 종료 -->
		<%}%>
	</div>
	
	<%if(newAsp){%>
	    <!-- Top Visual 시작 -->
	    <%@ include file="/learn/user/portal/include/topVisualNew.jsp" %>
	    <!-- Top Visual 종료 -->
	    
	    <style>
	    .asp_sub #container #contentWrap {padding: 220px 0 135px !important;}
	    #content {position:relative; width:860px !important; padding:0 30px !important; font-family:dotum !important; font-size:12px; line-height:21px;}
	
		h2 {height:50px; margin-right:-7px; font-size:0;}
		h2 .category {float:right; font-size:11px; font-weight:normal; color:#999;}
		
		h3 {width:100%; margin:35px 0 8px; overflow:hidden;}
		h3 img.tit {float:left; margin-top:6px;}
		h3 img.tit1 {float:left; margin-top:3px;}
		h3 span.fl_l {float:left; margin-top:6px;}
		h3 span.stit {float:left; padding-left:6px; font-weight:normal; font-size:12px;}
		h3 p.btn {float:right;}
		h3 p.btn a {margin-left:5px; margin-bottom:2px;}
		h3 p.btn a.board_btn2 {margin-top:5px;}
	
		h3 span.ra_txt {float:right; color:#888; font-size:11px; font-weight:normal;}
		h3 span.ra_txt input {vertical-align:-1px; margin-right:2px;}
		:root h3 span.ra_txt input {vertical-align:-2px; margin-right:3px;}
		h3 span.ra_txt strong {color:#6175C4;}
		
		p.f_btn {float:right; height:25px; margin-top:10px;}
		p.f_btn a {margin-left:5px;}
		
		#content p.txt {clear:both; line-height:19px;}
		#content p.txt span.tit {font-size:14px; font-weight:bold;}
		
		#content p.box {margin-top:17px; padding:15px 16px 12px; line-height:19px; border:1px solid #DEDEDE; background:#F8F8F8;}
		
		#content ul.privacy {width:856px; overflow:hidden; margin:25px 0 27px; padding:15px 0 12px; border:2px solid #DEDEDE; background:#F8F8F8;}
		#content ul.privacy li {float:left; width:342px; padding-left:55px; line-height:24px; color:#888; font-weight:bold;}
		

	    </style>
	    
	    
	<%}%>
    
	

    

<!--// header -->
<hr />
<!-- container -->
<div id="container" >
    <div id="contentWrap">
