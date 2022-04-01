<%
//**********************************************************
//  1. 제      목: 홈페이지 FAQ 리스트화면
//  2. 프로그램명: zu_HomePageFaq_L.jsp
//  3. 개      요: 홈페이지 FAQ 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 1
//  7. 수      정: 이연정 2005. 7. 1
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
	
    String  v_faqcategory   = "";
	v_faqcategory = box.getString("p_faqcategory");
	if(v_faqcategory.equals("")){
		
		v_faqcategory = "0031";

		box.put("p_faqcategory", v_faqcategory);
	}
	
	

	//int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int     v_fnum     = 0, v_totalpage = 0, v_rowcount = 1;
	int v_pageno = box.getInt("p_pageno"); 
    String  v_title    = "";
    String  v_contents = "";
    String  v_indate   = "";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../homepage/inc/style01.css" rel="stylesheet" type="text/css">
<link href="../../../css/user_style1.css" rel="stylesheet" type="text/css">
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

function changeCategory() {
        document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
        document.form1.p_process.value    = "selectList";
        document.form1.submit();
}
function go(index) {
	document.form1.p_pageno.value = index;
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.p_process.value = "selectList";
	document.form1.submit();
}

function searchTxt(index) {
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.p_process.value = "selectList";
	document.form1.submit();
}


function goPage(pageNum) {
	 document.form1.p_pageno.value = pageNum;
	 document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	 document.form1.p_process.value = "selectList";
	 document.form1.submit();
}
function helpdeskList(){
	document.form1.p_process.value = "selectList";
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.submit();
}

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

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
function clickHandler() {
  var targetId, srcElement, targetElement, srcCss;
  srcElement = window.event.srcElement;

  if (srcElement.className == "tbl_bfaq") {
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
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('../../../images/user/support/lm_01_on.gif','../../../images/user/support/lm_02_on.gif','../../../images/user/support/lm_03_on.gif','../../../images/user/support/lm_04_on.gif','../../../images/user/support/lm_05_on.gif')">
<!-- top -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../../../images/user/homepage/type1/bg_top.gif">
  <tr>
    <td>
	  <table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="170">&nbsp;</td>
          <td align="right">
			<!-- 탑 첫번째 메뉴 시작 -->
			<%@include file="/include/top_menu1.jsp"%>
			<!-- 탑 첫번째 메뉴 끝 -->
		  </td>
        </tr>
			<!-- 탑 두번째 메뉴 시작 -->
			<%@include file="/include/top_menu2.jsp"%>
			<!-- 두번째 메뉴 끝 -->		  
        <tr>
          <td height="21" valign="top"><img src="../../../images/user/homepage/type1/m_head.gif"></td>
        </tr>
      </table>
	 </td>
  </tr>
</table><!-- center -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td>
	  <table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="18" height="30" class="mlcolor1">&nbsp;</td>
          <td width="152" rowspan="2" valign="top" >
			<!-- 왼쪽 메뉴 시작 -->
			<%@include file="/incmenu/left_helpdesk.jsp"%>
			<!-- 왼쪽 메뉴 끝 -->
		  </td>
          <td rowspan="2" valign="top" background="../../../images/user/homepage/type1/bg_center.gif">
		  <!-- center start -->
		  
		  
		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; FAQ</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> <img src="../../../images/user/support/type1/tit_faq.jpg" width="675" height="85" > 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10"></td>
                    </tr>
                  </table>

				<form name = "form1" method = "post">
				<input type = "hidden" name = "p_process" value = "">
				<input type = "hidden" name = "p_pageno" value = "">
				<input type = "hidden" name = "p_seq" value = "">
				<input type = "hidden" name = "p_userid" value = "">
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="116" valign="top" background="../../../images/user/support/faq_img.jpg" style="padding-top:58px">
					  <table width="200" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="74"><img src="../../../images/user/support/faq_ca.gif"></td>
                             <td width="126">
							<%=HomePageFAQBean.homepageGetFaqCategorySelecct("p_faqcategory", v_faqcategory, "onChange=\"javascript:changeCategory()\"", 1)%>
                             </td>
							 <td width="105"><input name="p_searchtxt" type="text" class="input" value="" size="15"></td>
                            <td width="87"><a href="javascript:searchTxt()"><img src="../../../images/user/button/btn_search.gif"></a></td>
                          </tr>
                        </table>

						</td>
                    </tr>
                    <tr>
                      <td><table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="540" valign="top">
							<table width="550" border="0" cellspacing="0" cellpadding="0">
							<!--for문 돌기-->
							<%if(list.size() != 0){%>
							<%
										int i =0;
										for( i = 0; i < list.size(); i++) {

											DataBox dbox = (DataBox)list.get(i);  
											
											v_title    = dbox.getString("d_title");

											v_contents = dbox.getString("d_contents");
											//v_contents = StringManager.replace(v_contents,"\r\n","<br>");
											v_totalpage = dbox.getInt("d_totalpage");
											v_rowcount = dbox.getInt("d_rowcount");
							%>
                                <tr> 
                                  <td width="22"><img src="../../../images/user/support/bl_q.gif"></td>
                                  <td width="528" id="Out<%=i%>" class="tbl_bfaq" style="cursor:hand"><%=v_title%></td>
                                </tr>
                                
                                <tr> 
                                  <td colspan="2" ><div id="Out<%=i%>details" style="display:none; position:relative; padding-top:4px; visibility: visible;" class=FAQ_a> 
                                      <TABLE cellpadding="0" cellspacing="0" border="0" width="550">
                                        <tr valign="top"> 
                                          <td width="30" style="padding-top:2px" class=tbl_bfaqt><img src="../../../images/user/support/bl_a.gif" border="0" align="absmiddle"></td>
                                          <td style="padding-left:5" class=tbl_bfaqt><%=v_contents %></td>
                                        </tr>
                                      </TABLE>
                                    </div></td>
                                </tr>
                                <tr> 
                                  <td colspan="2" ><img src="../../../images/user/support/faq_line.gif"></td>
                                </tr>

								<%}%>
								<%}else{%>
                                <tr>
                                    <td colspan="13" align=center valign=top>
										등록 된 FAQ가 없습니다.		
									</td>
								</tr>
								<tr> 
								    <td colspan="2" ><img src="../../../images/user/support/faq_line.gif"></td>
								</tr>
								<%}%>
                              </table>
                            </td>
                            <td width="135" valign="bottom"><img src="../../../images/user/support/faq_q.gif" width="111" height="160"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table> 
				  </form>
				</td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td class="mlcolor2">&nbsp;</td>
        </tr>
      </table>
	 </td>
  </tr>
</table>
<!-- footer -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../../../images/user/homepage/type1/bg_footer.gif">
  <tr>
    <td valign="top"><table width="900" height="64" border="0" cellpadding="0" cellspacing="0">
        <tr valign="top"> 
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr> 
          <td width="131" rowspan="2" align="right" valign="top">&nbsp;</td>
          <td width="634" height="17" align="center" class="bottom_menu"><a href="#">통신훈련유의사항</a> 
            ㅣ <a href="#">개인정보보호정책</a> <strong>교육문의: TEL</strong> 080-224-9697~7 
            <strong>E-MAIL</strong> <a href="mailto:elearning@autoeversystems.com">elearning@autoeversystems.com</a> 
          </td>
        </tr>
        <tr> 
          <td height="35" align="center" valign="top"><img src="../../../images/user/homepage/type1/footer_copy.gif" ></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
