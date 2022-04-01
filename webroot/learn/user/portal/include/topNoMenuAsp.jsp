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
    
  //관련부서 사이트
    LinkSiteBean site = new LinkSiteBean();
    ArrayList bolist = site.SelectList(box);

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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미</title>
<link rel="stylesheet" href="/css/newmenu_20100625.css" type="text/css" />
<%if(mainType.equals("A")){%>
	<link rel="stylesheet" href="/css/portal/asp/newaspSub_A.css?20161124" type="text/css" />
<%}else if(mainType.equals("B")){%>
	<link rel="stylesheet" href="/css/portal/asp/newaspSub_B.css" type="text/css" />
<%}%>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="javascript" src="/script/flash_TagScript.js"></script>
<script language="javascript" src="/script/jquery-1.3.2.min.js"></script>
<script language="javascript" src="/script/jquery.timers.js"></script>
<script language="javascript" src="/script/jquery.form.js"></script>
<script language="javascript" src="/script/mainscript.js"></script>
</head>
<body >
<%@ include file="/learn/user/portal/include/common.jsp" %>

<div <%=newAsp ? mainType.equals("A") ? "id=\"wrap\"" : "id=\"wrap_sub\"" : "" %>><%-- wrap start --%>	
	<div id="header<%=newAsp ? "_sub" : ""%>" <%=!koccaYn && !newAsp ? "class=\"asp_main\"" : "" %>>
		<%if(newAsp){%>
		    <!-- header -->
		    <%if(mainType.equals("A")){%>
				<%@ include file="/learn/user/portal/include/newSubHeaderA.jsp" %>
		    <%}else if(mainType.equals("B")){%>
				<%@ include file="/learn/user/portal/include/newSubHeaderB.jsp" %>
		    <%}%>
		    <!-- header -->
			    
		<%}else{%>
			<div style="position:relative; width:1000px; margin:0 auto;">
			<%@ include file="/learn/user/portal/include/topWarp.jsp" %>
			</div>
		<%}%>
	</div>
	<%box.put("mainYn", "Y"); %>
	<%--<%@ include file="/learn/user/portal/include/topMenu.jsp" %>--%>
