<%
//**********************************************************
//  1. 제      목 : 홈페이지 메인
//  2. 프로그램명 : zu_Main.jsp
//  3. 개      요 :
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 정상진 2005.07.8
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.net.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.net.InetAddress" %>
<%@ page import = "java.net.InetSocketAddress" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");
    String v_tem_grcode  = box.getSession("tem_grcode");

	//로그인 체크 변수
    boolean bLoginCheck = false;

    //세션에 사용자 정보가 있는지 검사
    if (session.getAttribute("userid") != null ) {
        bLoginCheck = true;
    }

    box.put("onLoad","onload=\"winonload();\"");

    LoginBean bean = new LoginBean();

    String v_m_gubun        = "";
    String v_m_menuid       = "";
    String v_m_menuname     = "";
    String v_m_menuurl      = "";
    ArrayList mainl_list = (ArrayList)request.getAttribute("mainl_list");
    ArrayList mainr_list = (ArrayList)request.getAttribute("mainr_list");
    ArrayList mainc_list = (ArrayList)request.getAttribute("mainc_list");

    String v_tem_url = "/learn/user/game/include/topMainN000013.jsp";

%>

<%//템플릿 정보%>
<%@ include file="/learn/user/game/include/site_info.jsp" %>

<!------- 글로벌메뉴, 상단 로고,메뉴 시작 ------------>
<%@ //include file="/learn/user/game/include/topMain"+v_tem_grcode+".jsp"%>
<%	//if(v_tem_url.equals("")) {	%>
<%@ include file="/learn/user/game/include/topMainN000013.jsp"%>
<!------- 글로벌메뉴, 상단 로고,메뉴 끝  ------------->
<SCRIPT LANGUAGE="JavaScript">

    function winonload()
    {
    //    if("<%=session.getAttribute("userid")%>" == "" || "<%=session.getAttribute("userid")%>" == "null")
    //    {
    //        document.getElementById("p_userid").focus();
    //    }
    }

</SCRIPT>
<SCRIPT type=text/JavaScript>
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

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
    <table cellpadding="0" cellspacing="0" width="216" height="100%" style="table-layout:fixed" border="0" bgcolor="#FFFFFF">
        <col width="1"><col width="8"><col width="190"><col width="13">
        <tr>
        <td colspan="3" height="15"></td>
        <tr valign="top">
        <td bgcolor="#cccccc"></td>
        <td></td>
        <td align="right">
<%
    for (int i=0; i<mainl_list.size(); i++) {
        DataBox dbox1 = (DataBox)mainl_list.get(i);

        v_m_gubun     = dbox1.getString("d_gubun");
        v_m_menuid    = dbox1.getString("d_menuid");
        v_m_menuname  = dbox1.getString("d_menuname");
        v_m_menuurl   = dbox1.getString("d_menuurl");

%>

		<!--  <%=v_m_menuname%> table start -->
		<jsp:include page="<%=v_m_menuurl%>" flush="true" />
		<!--  <%=v_m_menuname%> table end -->
<%	}
%>
        </td>
        </tr>
        </table>
    </td>
    <TD width="558" align=center bgcolor="#FFFFFF">
<table border="0" cellspacing="0" cellpadding="0" width="539">
<tr>
<td height="15"></td>
<td valign="top" rowspan="2"><img src="/images/user/game/homepage/type1/con_bg.gif"></td>
</tr>
<tr>
<td>
<%
    for (int i=0; i<mainc_list.size(); i++) {
        DataBox dbox1 = (DataBox)mainc_list.get(i);

        v_m_gubun     = dbox1.getString("d_gubun");
        v_m_menuid    = dbox1.getString("d_menuid");
        v_m_menuname  = dbox1.getString("d_menuname");
        v_m_menuurl   = dbox1.getString("d_menuurl");

%>
<!--<%=v_m_menuurl%>-->
		<%if( v_m_menuid.equals("07") ){
			if( (s_userid) != "" ){%>
		<!--  <%=v_m_menuname%> table start -->
		<jsp:include page="<%=v_m_menuurl%>" flush="true" />
		<!--  <%=v_m_menuname%> table end -->
			<%}else{}%>
		<%}else{%>
		<!--  <%=v_m_menuname%> table start -->
		<jsp:include page="<%=v_m_menuurl%>" flush="true" />
		<!--  <%=v_m_menuname%> table end -->
		<%}%>
<%	}
%>
<!--
  <table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><img src="/images/user/game/homepage/type1/title.gif"></td>
	</tr>
	<tr>
		<td align="center">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><A  href="http://game.wsu.ac.kr/" target="_blank"><IMG onmouseover="MM_swapImage('Image1','','/images/user/game/homepage/type1/bottom_01u.gif',1)"
                  onmouseout=MM_swapImgRestore() src="/images/user/game/homepage/type1/bottom_01.gif"  border=0 name=Image1></A></td>
					<td><A  href="http://gamenuri.wsu.ac.kr/" target="_blank"><IMG onmouseover="MM_swapImage('Image2','','/images/user/game/homepage/type1/bottom_02u.gif',1)"
                  onmouseout=MM_swapImgRestore() src="/images/user/game/homepage/type1/bottom_02.gif"  border=0 name=Image2></A></td>
					<td><A  href="http://www.metro.daejeon.kr/" target="_blank"><IMG onmouseover="MM_swapImage('Image3','','/images/user/game/homepage/type1/bottom_03u.gif',1)"
                  onmouseout=MM_swapImgRestore() src="/images/user/game/homepage/type1/bottom_03.gif"  border=0 name=Image3></A></td>
					<td><A  href="http://www.djacts.kr/" target="_blank"><IMG onmouseover="MM_swapImage('Image4','','/images/user/game/homepage/type1/bottom_04u.gif',1)"
                  onmouseout=MM_swapImgRestore() src="/images/user/game/homepage/type1/bottom_04.gif"  border=0 name=Image4></A></td>
					<td><A  href="http://www.dif.or.kr/index.php" target="_blank"><IMG onmouseover="MM_swapImage('Image5','','/images/user/game/homepage/type1/bottom_05u.gif',1)"
                  onmouseout=MM_swapImgRestore() src="/images/user/game/homepage/type1/bottom_05.gif"  border=0 name=Image5></A></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
-->
</td>
</tr>
</table>
    </td>
    <td valign="top">
        <table cellpadding="0" cellspacing="0" width="224" height="100%" style="table-layout:fixed" border="0">
        <col width="223"><col width="1">
        <tr><td colspan="2" height="15"><img src="/images/user/game/homepage/type1/right_top.gif"></td></tr>
        <tr>
        <TD vAlign=top align=center width=223 bgcolor="#FFFFFF" >
            <table cellpadding="0" cellspacing="0" width="190" border="0">

<%
    for (int i=0; i<mainr_list.size(); i++) {
        DataBox dbox2 = (DataBox)mainr_list.get(i);

        v_m_gubun     = dbox2.getString("d_gubun");
        v_m_menuid    = dbox2.getString("d_menuid");
        v_m_menuname  = dbox2.getString("d_menuname");
        v_m_menuurl   = dbox2.getString("d_menuurl");

%>

				<tr>
					<td>
					<!--<%=v_m_menuurl%>-->
				<!--  <%=v_m_menuname%> table start -->
				<% %>
				<jsp:include page="<%=v_m_menuurl%>" flush="true" />
				<!--  <%=v_m_menuname%> table end -->
					</td>
				</tr>
				<tr height="5"><td></td></tr>
<%	}
%>
				<tr>
				<td>
<table border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><img src="/images/user/game/homepage/type1/N000012_banner_top.gif"></td>
	</tr>
	<tr>
		<td><a href="http://ct.cheju.ac.kr/" target="_blank"><img src="/images/user/game/homepage/type1/N000013_banner_01.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://scap.scout.co.kr/cheju/login.asp" target="_blank"><img src="/images/user/game/homepage/type1/N000013_banner_03.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://www.cheju.ac.kr/" target="_blank"><img src="/images/user/game/homepage/type1/N000013_banner_05.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://cpim.cheju.ac.kr/" target="_blank"><img src="/images/user/game/homepage/type1/N000013_banner_02.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://www.halla-c.ac.kr/main.html" target="_blank"><img src="/images/user/game/homepage/type1/N000013_banner_06.gif" border="0"></a></td>
	</tr>
	<tr>
		<td height=200>&nbsp;</td>
	</tr>
	<!--
	<tr>
		<td><a href="http://fox.wemade.com/main/main.asp" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_06.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://www.haansoft.com/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_07.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://www.hmst.co.kr/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_08.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://dcon.etri.re.kr/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_09.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://djacts.kr/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_10.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://www.adobe.com/kr/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_11.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://www.rainbus.com/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_12.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://fo.paran.com/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_13.gif" border="0"></a></td>
	</tr>
	<tr>
		<td><a href="http://www.zepetto.com/" target="_blank"><img src="/images/user/game/homepage/type1/N000012_banner_14.gif" border="0"></a></td>
	</tr>
	-->
</table>
				</td>
				</tr>
            </table>
        </td>
        <td bgcolor="#FFFFFF"></td></tr>
        </table>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottomA.jsp"%>
<!---------- copyright end  -------------------->


