<%
//**********************************************************
//  1. 제      목: Loadmap
//  2. 프로그램명 : pop_Loadmap.jsp
//  3. 개      요: Loadmap 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 06.01.16
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

%>

<SCRIPT LANGUAGE="JavaScript">
<!--

function move(tab){
	document.form1.p_process.value= "select";
	document.form1.p_tab.value = tab;
	document.form1.action = "/servlet/controller.course.SubjLoadMapServlet";
	document.form1.submit();
}

//-->
</SCRIPT>


<html>
<head>
<title> ::: 과정로드맵 ::: </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
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
</script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}
//-->
</script>
</head>

<form name = "form1" method="post">
	<input type="hidden" name ="p_process">
	<input type="hidden" name ="p_tab">

<body bgcolor="F6F6F6" onLoad="MM_preloadImages('/images/user/kocca/course/tab1_02on.gif','/images/user/kocca/course/tab1_03on.gif','/images/user/kocca/course/tab2_02on.gif','/images/user/kocca/course/tab2_03on.gif','/images/user/kocca/course/tab2_04on.gif','/images/user/kocca/course/tab2_05on.gif','/images/user/kocca/course/tab2_06on.gif')">
<div id="Layer2" style="position:absolute; left:37px; top:495px; width:674px; height:182px; z-index:2; visibility: hidden;">
  <table width="675" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td valign="top"><img src="/images/user/kocca/course/box_r_top.gif"></td>
    </tr>
    <tr> 
      <td align="center" background="/images/user/kocca/course/box_r_bg.gif"><table width="640" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="tbl_bleft"><img src="/images/user/kocca/course/bl_road.gif" align="absmiddle"><font color="#000000"><strong>음반장르 
              기획부분</strong></font> </td>
          </tr>
          <tr> 
            <td height="6"></td>
          </tr>
          <tr> 
            <td><img src="/images/user/kocca/course/road1-2.gif" width="640" height="125"></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><img src="/images/user/kocca/course/box_r_bo.gif"></td>
    </tr>
  </table>
</div>
<div id="Layer3" style="position:absolute; left:37px; top:495px; width:675px; height:182px; z-index:3; visibility: hidden;">
  <table width="675" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td valign="top"><img src="/images/user/kocca/course/box_r_top.gif"></td>
    </tr>
    <tr> 
      <td align="center" background="/images/user/kocca/course/box_r_bg.gif"><table width="640" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="tbl_bleft"><img src="/images/user/kocca/course/bl_road.gif" align="absmiddle"><font color="#000000"><strong>출판장르 
              기획부분</strong></font> </td>
          </tr>
          <tr> 
            <td height="6"></td>
          </tr>
          <tr> 
            <td><img src="/images/user/kocca/course/road1-3.gif" width="640" height="124"></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><img src="/images/user/kocca/course/box_r_bo.gif"></td>
    </tr>
  </table>
</div>
<div id="Layer4" style="position:absolute; left:37px; top:495px; width:675px; height:182px; z-index:4; visibility: hidden;">
  <table width="675" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td valign="top"><img src="/images/user/kocca/course/box_r_top.gif"></td>
    </tr>
    <tr> 
      <td align="center" background="/images/user/kocca/course/box_r_bg.gif"><table width="640" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="tbl_bleft"><img src="/images/user/kocca/course/bl_road.gif" align="absmiddle"><font color="#000000"><strong>캐릭터장르 
              기획부분</strong></font> </td>
          </tr>
          <tr> 
            <td height="6"></td>
          </tr>
          <tr> 
            <td><img src="/images/user/kocca/course/road1-4.gif"></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><img src="/images/user/kocca/course/box_r_bo.gif"></td>
    </tr>
  </table>
</div>
<div id="Layer5" style="position:absolute; left:37px; top:495px; width:675px; height:182px; z-index:5; visibility: hidden;">
  <table width="675" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td valign="top"><img src="/images/user/kocca/course/box_r_top.gif"></td>
    </tr>
    <tr> 
      <td align="center" background="/images/user/kocca/course/box_r_bg.gif"><table width="640" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="tbl_bleft"><img src="/images/user/kocca/course/bl_road.gif" align="absmiddle"><font color="#000000"><strong>모바일장르 
              기획부분</strong></font> </td>
          </tr>
          <tr> 
            <td height="6"></td>
          </tr>
          <tr> 
            <td><img src="/images/user/kocca/course/road1-5.gif" width="640" height="124"></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><img src="/images/user/kocca/course/box_r_bo.gif"></td>
    </tr>
  </table>
</div>
<div id="Layer6" style="position:absolute; left:37px; top:495px; width:675px; height:181px; z-index:6; visibility: hidden;">
  <table width="675" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td valign="top"><img src="/images/user/kocca/course/box_r_top.gif"></td>
    </tr>
    <tr> 
      <td align="center" background="/images/user/kocca/course/box_r_bg.gif"><table width="640" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="tbl_bleft"><img src="/images/user/kocca/course/bl_road.gif" align="absmiddle"><font color="#000000"><strong>애니메이션장르 
              기획부분</strong></font> </td>
          </tr>
          <tr> 
            <td height="6"></td>
          </tr>
          <tr> 
            <td><img src="/images/user/kocca/course/road1-6.gif"></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><img src="/images/user/kocca/course/box_r_bo.gif"></td>
    </tr>
  </table>
</div>
<table width="746" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/kocca/course/pop_tit_roadmap.gif" width="679" height="49"></td>
  </tr>
  <tr>
    <td><table width="745" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="23" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="701" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
		  <!-- 전체분류탭부분 -->
		  <table width="675" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right"><img src="/images/user/kocca/course/tab1_01on.gif"><a href="javascript:move(2)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','/images/user/kocca/course/tab1_02on.gif',1)"><img src="/images/user/kocca/course/tab1_02.gif" name="Image8" border="0"></a><a href="javascript:move(3)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','/images/user/kocca/course/tab1_03on.gif',1)"><img src="/images/user/kocca/course/tab1_03.gif" name="Image9" border="0"></a></td>
              </tr>
			    <tr> 
                <td height="1" ></td>
              </tr>
              <tr> 
                <td height="1" bgcolor="#CCCCCC"></td>
              </tr>
            </table> 
			<!-- 서브타이틀 -->
            <table width="676" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="676">&nbsp;</td>
              </tr>
              <tr> 
                <td><img src="/images/user/kocca/course/st_map1-1.gif"></td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>
            <table width="675" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td background="/images/user/kocca/course/pop_road_bg.jpg"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="73">&nbsp;</td>
                      <td width="602">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td valign="top"><table width="50" height="331" border="1" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td width="46" height="329" bordercolor="CFCFC4" bgcolor="#EFEFD1" class="tbl_croad"><strong>직무별<br>
                              러닝맵<br>
                              [기획] </strong></td>
                          </tr>
                        </table></td>
                      <td valign="top"> 
                        <!-- 탭부분 -->
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><a href="#"><img src="/images/user/kocca/course/tab2_01.gif" name="Image11" border="0" onClick="MM_showHideLayers('Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer1','','show')"></a><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image12','','/images/user/kocca/course/tab2_02on.gif',1)"><img src="/images/user/kocca/course/tab2_02.gif" name="Image12" border="0" onClick="MM_showHideLayers('Layer2','','show','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer1','','hide')"></a><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image13','','/images/user/kocca/course/tab2_03on.gif',1)"><img src="/images/user/kocca/course/tab2_03.gif" name="Image13" border="0" onClick="MM_showHideLayers('Layer2','','hide','Layer3','','show','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer1','','hide')"></a><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image14','','/images/user/kocca/course/tab2_04on.gif',1)"><img src="/images/user/kocca/course/tab2_04.gif" name="Image14" border="0" onClick="MM_showHideLayers('Layer2','','hide','Layer3','','hide','Layer4','','show','Layer5','','hide','Layer6','','hide','Layer1','','hide')"></a><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image15','','/images/user/kocca/course/tab2_05on.gif',1)"><img src="/images/user/kocca/course/tab2_05.gif" name="Image15" border="0" onClick="MM_showHideLayers('Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','show','Layer6','','hide','Layer1','','hide')"></a><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image16','','/images/user/kocca/course/tab2_06on.gif',1)"><img src="/images/user/kocca/course/tab2_06.gif" name="Image16" border="0" onClick="MM_showHideLayers('Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','show','Layer1','','hide')"></a></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="104" valign="top">&nbsp;</td>
                            <td width="103"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">미디어변화와<br>
                                    음악서비스 </td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top">&nbsp;</td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">각국의 
                                    문화와 <br>
                                    캐릭터</td>
                                </tr>
                              </table></td>
                            <td width="104" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">에듀테인머트<br>
                                    성공사례</td>
                                </tr>
                              </table></td>
                            <td width="101" valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="104" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">영화감독워크숍</td>
                                </tr>
                              </table></td>
                            <td width="103">&nbsp;</td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">만화가워크숍</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top">&nbsp;</td>
                            <td width="104" valign="top">&nbsp;</td>
                            <td width="101" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">애니감독워크숍</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="5" background="/images/user/kocca/course/5line.gif"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="104" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">워크숍</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">워크숍</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">워크숍</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">워크숍</td>
                                </tr>
                              </table></td>
                            <td width="104" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">워크숍</td>
                                </tr>
                              </table></td>
                            <td width="101" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">워크숍</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="104" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">산업사례연구</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">산업사례연구</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">산업사례연구</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">산업사례연구</td>
                                </tr>
                              </table></td>
                            <td width="104" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">산업사례연구</td>
                                </tr>
                              </table></td>
                            <td width="101" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">산업사례연구</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="104" valign="top">&nbsp;</td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">대중음반기획</td>
                                </tr>
                              </table></td>
                            <td width="103" valign="top">&nbsp;</td>
                            <td width="103" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">캐릭터기획</td>
                                </tr>
                              </table></td>
                            <td width="104" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">모바일기획</td>
                                </tr>
                              </table></td>
                            <td width="101" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">애니메이션기획</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="121" valign="top">&nbsp;</td>
                            <td width="132" align="center" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">시나리오작법</td>
                                </tr>
                              </table></td>
                            <td width="11" valign="top">&nbsp;</td>
                            <td width="126" valign="top"><table width="95" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="91" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">OSMU창조적발상</td>
                                </tr>
                              </table></td>
                            <td width="127" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="EEEEDE" class="tbl_croad">콘텐츠유통배급론</td>
                                </tr>
                              </table></td>
                            <td width="101" valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="183" valign="top">&nbsp;</td>
                            <td width="119" align="right" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="#FFFFFF" class="tbl_croad">문화콘텐츠론</td>
                                </tr>
                              </table></td>
                            <td width="24" valign="top">&nbsp;</td>
                            <td width="101" valign="top"><table width="90" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="108" bordercolor="CFCFC4" bgcolor="#FFFFFF" class="tbl_croad">프리젠테이션</td>
                                </tr>
                              </table></td>
                            <td width="90" valign="top">&nbsp;</td>
                            <td width="101" valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="6"></td>
                          </tr>
                        </table>
                        <table width="618" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="201" valign="top">&nbsp;</td>
                            <td width="226" align="center" valign="top"><table width="131" border="1" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td  bordercolor="CFCFC4" bgcolor="#DDD9AE" class="tbl_croad"><strong>직무기초</strong></td>
                                </tr>
                              </table></td>
                            <td width="90" valign="top">&nbsp;</td>
                            <td width="101" valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="4"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="180" align="center" valign="middle" bgcolor="#FFFFEE">
					  <div id="Layer1" style="position:absolute; left:37px; top:495px; width:674px; height:182px; z-index:1; visibility: hidden;"> 
                          <table width="675" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td valign="top"><img src="/images/user/kocca/course/box_r_top.gif"></td>
                            </tr>
                            <tr> 
                              <td align="center" background="/images/user/kocca/course/box_r_bg.gif"><table width="640" border="0" cellspacing="0" cellpadding="0">
                                  <tr> 
                                    <td class="tbl_bleft"><img src="/images/user/kocca/course/bl_road.gif" align="absmiddle"><font color="#000000"><strong>영화장르 
                                      기획부분</strong></font> </td>
                                  </tr>
                                  <tr> 
                                    <td height="6"></td>
                                  </tr>
                                  <tr> 
                                    <td><img src="/images/user/kocca/course/road1-1.gif"></td>
                                  </tr>
                                </table></td>
                            </tr>
                            <tr> 
                              <td><img src="/images/user/kocca/course/box_r_bo.gif"></td>
                            </tr>
                          </table>
                        </div> <img src="/images/user/kocca/course/road_text.gif" width="497" height="46"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table width="675" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:self.close()"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table></td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
