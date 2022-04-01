<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 FAQ리스트화면
//  2. 프로그램명: zu_CpFaq_L.jsp
//  3. 개      요: 외주관리 시스템 FAQ리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
	System.out.println("cpv_process ====>" + v_process);

    String  v_faqcategory        = box.getString("p_faqcategory");
	System.out.println("cpfaqList____v_faqcategory ====>" + v_faqcategory);

    int     v_fnum     = 0;
    String  v_title    = "";
    String  v_contents = "";
    String  v_indate   = "";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
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
function changeCategory() {
        document.form1.action = "/servlet/controller.cp.CpFaqServlet";
        document.form1.p_process.value    = "";
        document.form1.submit();
}
function selectList() {
        document.form1.action = "/servlet/controller.cp.CpQnaServlet";
        document.form1.p_process.value    = "selectList";
        document.form1.submit();
    }
function faqList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else {
	
  }
<% } else { %>
	document.form1.p_process.value   = "selectList";
	document.form1.action            = "/servlet/controller.cp.CpFaqServlet";
	document.form1.submit()
<% } %>
}
function qnaList() {
<% if (session.getAttribute("userid")==null) {%>     
if( getCookie( "userid_cp" ) == "" ) {
alert("로그인을 하셔야 사용 가능합니다");
} else {

}
<% } else { %>
document.form1.p_process.value   = "selectList";
document.form1.action            = "/servlet/controller.cp.CpQnaServlet";
document.form1.submit()
<% } %>
}
function adminList() {
	document.form1.action = "/servlet/controller.cp.CpAdminServlet";
	document.form1.p_process.value    = "adminiList";
	document.form1.submit();
}
//-->
</script>
<script language="JavaScript">
<!--
function Search()
{
	document.form1.p_faqcodecont.value=document.form1.p_faqcode.options[document.form1.p_faqcode.selectedIndex].text;
	document.form1.action="/Controller?cmd=home&act=homefaq";
	document.form1.submit();
}

function clickHandler() {
  var targetId, srcElement, targetElement, srcCss;
  srcElement = window.event.srcElement;

  if (srcElement.className == "Outline") {
     targetId = srcElement.id + "details";
     targetElement = document.all(targetId);
     srcCss = document.all(srcElement.id);

     if (targetElement.style.display == "none") {
        targetElement.style.display = "";
        srcCss.style.color = "#666666";
        srcCss.style.font = "normal 9pt \"돋움\"";
     } else {
        targetElement.style.display = "none";
        srcCss.style.color = "";
        srcCss.style.font = "normal 9pt \"돋움\"";
     }
  }
}

document.onclick = clickHandler;
-->
</script>

</head>

<body leftmargin=0 topmargin=0 onLoad="MM_preloadImages('/images/gate/O_help_m01_on.gif','/images/gate/O_help_m02_on.gif','/images/gate/O_help_m03_on.gif');">
 <form name = "form1" method = "post">
        <input type = "hidden" name = "p_process" value = "">
        <input type = "hidden" name = "p_pageno" value = "">
        <input type = "hidden" name = "p_seq" value = "">
        <input type = "hidden" name = "p_userid" value = "">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="907" bgcolor="EFEFEA"><img src="/images/gate/O_m03_img.gif" width="830" height="120"></td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td><table width="949" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="25">&nbsp;</td>
          <td width="85" valign="top"><table width="85" border="0" cellpadding="0" cellspacing="0" background="/images/gate/O_help_mbg.gif">
              <tr> 
                <td><img src="/images/gate/O_help_top.gif" width="85" height="17"></td>
              </tr>
              <tr> 
                <td><img src="/images/gate/O_help_m01_on.gif" name="sub01" width="85" height="20" border="0"></td>
              </tr>
              <tr> 
                <td height=30><a href="javascript:qnaList()" target="_self" onfocus=this.blur() onMouseOver="MM_swapImage('sub02','','/images/gate/O_help_m02_on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/gate/O_help_m02.gif" name="sub02" width="85" height="20" border="0"></a></td>
              </tr>
              <tr> 
                <td height=30><a href="javascript:adminList()" target="_self" onfocus=this.blur() onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('sub03','','/images/gate/O_help_m03_on.gif',1)"><img src="/images/gate/O_help_m03.gif" name="sub03" width="85" height="20" border="0"></a></td>
              </tr>
              <tr>
                <td><img src="/images/gate/O_help_bottom.gif" width="85" height="40"></td>
              </tr>
            </table></td>
          <td width="25">&nbsp;</td>
          <td width="775"><table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="79%" align="right">카테고리명: </td>
                <td width="13%" align="right"><%=CpFAQBean.getFaqCategorySelecct("p_faqcategory", v_faqcategory, "onChange=\"javascript:changeCategory()\"", 1)%></td>
                <td width="8%" align="right"></td>
              </tr>
              <tr>
                <td height="6" align="right"></td>
                <td height="6" align="right"></td>
                <td height="6" align="right"></td>
              </tr>
            </table> 
            <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/gate/boardbox_c01.gif" width="20" height="20"></td>
                <td width="735" class=b_viewbox_T>&nbsp;</td>
                <td width="20"><img src="/images/gate/boardbox_c02.gif" width="20" height="20"></td>
              </tr>
              <tr> 
                <td class=b_viewbox_L>&nbsp;</td>
                <td align="center"><table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="3" class=FAQ_line></td>
                    </tr>
					<!--내용시작-->
					<%
            int i =0;
            for( i = 0; i < list.size(); i++) {

                DataBox dbox = (DataBox)list.get(i);  
                v_title    = dbox.getString("d_title");
                v_contents = dbox.getString("d_contents");
                v_contents = StringManager.replace(v_contents,"\r\n","<br>");

				
                  

%>
                    <tr> 
                      <td width="34"><img src="/images/gate/FAQ_Q.gif" width="34" height="18"></td>
                      <td width="21"><img src="/images/gate/FAQ_line.gif" width="21" height="18"></td>
                      <td width="720" class=FAQ_q><span id="Out<%=i%>" class="Outline" style="cursor:hand"><%=v_title%></span></td>
                    </tr>
                    <tr> 
                      <td colspan="3" class=FAQ_line></td>
                    </tr>
                    <tr> 
                      <td colspan="3"><div id="Out<%=i%>details" style="display:none; position:relative;padding-top:4px;visibility: visible;" class=FAQ_a>
                          <table width="720" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td width="34" class=FAQ_a><img src="/images/gate/FAQ_A.gif" width="34" height="18"></td>
                              <td width="21" class=FAQ_a><img src="/images/gate/FAQ_line.gif" width="21" height="18"></td>
                              <td width="720" class=FAQ_a><%=v_contents%> </td>
                            </tr>
                            <tr> 
                              <td colspan="4" class=FAQ_line></td>
                            </tr>
                          </table>
                        </div></td>
                    </tr>
						<%}%>   
                    <!--내용끝-->
                  </table></td>
                <td class=b_viewbox_R>&nbsp;</td>
              </tr>
              <tr> 
                <td><img src="/images/gate/boardbox_c03.gif" width="20" height="20"></td>
                <td class=b_viewbox_B>&nbsp;</td>
                <td><img src="/images/gate/boardbox_c04.gif" width="20" height="20"></td>
              </tr>
              <tr> 
                <td height=8></td>
                <td height=8></td>
                <td height=8></td>
              </tr>
            </table></td>
          <td width="10">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
          <td width="25" height="50">&nbsp;</td>
          <td width="775" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3"><img src="/images/gate/O_copyright.gif" width="323" height="41"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td class=O_bgline></td>
  </tr>
</table>
</form>
</body>
</html>
