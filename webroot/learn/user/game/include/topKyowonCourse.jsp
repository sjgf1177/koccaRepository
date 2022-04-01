<%//템플릿 정보%>
<%@ include file="/learn/user/game/include/site_info.jsp" %>
<%
    String topmenu  = "1";
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="Pragma" content="no-cache" />
<TITLE> :::: 게임아카데미 교원연수원 :::: </TITLE>
<link rel="stylesheet" type="text/css" href="/css/user_style1.css" />
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</SCRIPT>
</HEAD>

<BODY style="background-image:url(/images/user/game/course/type1/kysub_bg.gif);background-repeat:repeat-x;" <%=onLoad%>>

<!----------------- 공통폼 / 메뉴이동스크립트 시작 ----------------->
<%@ include file="/learn/user/game/include/common.jsp"%>
<!----------------- 공통폼 / 메뉴이동스크립트 끝 ------------------>

<table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0">
<tr valign="top" height="97">
<td>
	<!-- TOP -->
	<table cellpadding="0" cellspacing="0" width="100%" height="97" background="<%=tem_imgpath%><%=tem_topbg%>" style="table-layout:fixed;">
	<col width="1"><col width="999"><col width=""><!--070703수정-->
	<tr>
	<td rowspan="2" valign="top"><!-- Logo -->
		<% String v_indexURL = "";
		   String s_tem_grcode = box.getSession("tem_grcode");
		   if (s_tem_grcode.equals("N000002")) {
		   	v_indexURL = "/indexGame.jsp";
		   } else {
		   	v_indexURL = "/index" + s_tem_grcode + ".jsp";
		   }
		%>
		<a href="<%=v_indexURL%>"><img src="<%=tem_imgpath%><%=tem_toplogo%>" border="0"></a><br>
	</td><!--padding-right:95px;-->
	<td width="77%" height=31 valign="top" style="text-align:right;padding-right:5px;"><!-- top menu -->
    <!----------------- global menu 시작 ----------------->
    <%@ include file="/learn/user/game/include/globalmenuA.jsp"%>
    <!-----------------  global menu 끝 ------------------>
	</td>
	<td rowspan="2"></td>
	</tr>
	<tr>
	<td><!-- menuflash.swf -->
		<!----------------- menu flash 시작 ----------------->
		<%@ include file="/learn/user/game/include/menuflashA.jsp"%>
		<!----------------- menu flash 끝 ------------------>
	</td>
	</tr>
	</table>
	<!-- // TOP -->
</td>
</tr>

<tr valign="top">
<td>

	<!-- CONTENTS -->
	<table border=0 cellpadding="0" cellspacing="0" width="1000" height="100%">
	<col width="223"><col width="777">
	<tr valign="top">
	<td align="right">
      <!-----------------  left menu 시작 ----------------->
        <!-- Left Menu -->
        <table cellpadding="0" cellspacing="0" width="223" height="100%">
        <tr height="114">
            <td align="right"  valign="bottom" >
                <img src="/images/user/game/course/type1/kylm_tit.gif" ><br></td>
        </tr>
        <tr bgcolor=white>
            <td align="right" valign="top" background="/images/user/game/course/type1/kylm_bg.gif">
<%

    ArrayList lmenu_list = TempletBean.getMenuList(tem_grcode, topmenu, "C");
    String lmenu_gubun       = "";
    String lmenu_menuid      = "";
    String lmenu_kind        = "";
    String lmenu_position    = "";
    String lmenu_orders      = "";
    String lmenu_menuname    = "";
    String lmenu_menuurl     = "";
    String lmenu_menuimg     = "";
	String lmenu_menuoverimg = "";
	String lmenu_selmenuimg  = "";

    for(int m = 0; m < lmenu_list.size(); m++) {
        DataBox lmenu_dbox = (DataBox)lmenu_list.get(m);

        lmenu_gubun       = lmenu_dbox.getString("d_gubun");
        lmenu_menuid      = lmenu_dbox.getString("d_menuid");
        lmenu_kind        = lmenu_dbox.getString("d_kind");
        lmenu_position    = lmenu_dbox.getString("d_position");
        lmenu_orders      = lmenu_dbox.getString("d_orders");
        lmenu_menuname    = lmenu_dbox.getString("d_menuname");
        lmenu_menuurl     = lmenu_dbox.getString("d_menuurl");
        lmenu_menuimg     = lmenu_dbox.getString("d_menuimg");
		lmenu_menuoverimg = lmenu_dbox.getString("d_menuoverimg");
		if (leftmenu.equals(lmenu_menuid)) lmenu_selmenuimg = lmenu_menuoverimg;
		else                               lmenu_selmenuimg = lmenu_menuimg;

%>
				<!--  <%=lmenu_menuname%> table start -->
                <a href="javascript:menuForward('<%=topmenu%>', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2<%=m%>','','/images/user/game/course/type1/<%=lmenu_menuoverimg%>',1)"><img src="/images/user/game/course/type1/<%=lmenu_selmenuimg%>" name="Image2<%=m%>" border="0"></a><br>
				<!--  <%=lmenu_menuname%> table end -->
<%	}
%>
                <img src="/images/user/game/course/type1/kylm_bo.gif" ><br>
          <img src="/images/user/game/course/type1/kylm_bot.gif" width="223" height="1"><br></td>
        </tr>
        </table>
        <!-- //Left Menu -->
      <!-----------------  left menu 끝 ------------------>
	</td>
	<td background="/images/user/game/course/type1/conbg.gif" align="center">
		<img src="/images/user/game/course/type1/kysubimg.jpg"><br>