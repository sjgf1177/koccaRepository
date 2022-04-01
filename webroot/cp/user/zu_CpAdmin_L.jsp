<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 운영자에게 리스트화면
//  2. 프로그램명: zu_CpAdmin_L.jsp
//  3. 개      요: 외주관리 시스템 운영자에게 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0;
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
int v_pageno = box.getInt("p_pageno"); 
String  v_classname = "";
String v_name = "", v_jikwinm = "", v_jikmunm = "", v_orga_ename = "", v_title = "", v_comptel = "", v_handphon = "", v_userid = "";


ArrayList list = (ArrayList)request.getAttribute("selectList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");


String s_userid = box.getSession("userid");


String s_usernm = box.getSession("name");
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


function go(index) {
document.form1.p_pageno.value = index;
document.form1.action = "/servlet/controller.cp.CpAdminServlet";
document.form1.p_process.value = "adminiList";     
document.form1.submit();
}            
function goPage(pageNum) {
document.form1.p_pageno.value = pageNum;
document.form1.action = "/servlet/controller.cp.CpAdminServlet";
document.form1.p_process.value = "adminiList";     
document.form1.submit();
}                
function selectList() {
document.form1.action = "/servlet/controller.cp.CpAdminServlet";
document.form1.p_process.value = "adminiList";   

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

function whenSelection() {
document.form1.action = "/servlet/controller.cp.CpAdminServlet";
document.form1.p_process.value    = "adminiList";
document.form1.submit();
}

function kiaList(){
document.form1.action = "/servlet/controller.cp.CpAdminServlet";
document.form1.p_process.value    = "adminiListkia";
document.form1.submit();
}

function selectAction2(userid) {
	window.self.name= "languageAppWin";
	openBrWindow('','apppop','width=520,height=600');
	document.form1.action = "/servlet/controller.cp.CpAdminServlet";
	document.form1.p_process.value = "select2";
	document.form1.p_touserid.value = userid;
	
	document.form1.target = "apppop";
	document.form1.submit();
}

function openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

//-->
</script>
</head>

<body leftmargin=0 topmargin=0 onLoad="MM_preloadImages('/images/gate/O_help_m01_on.gif','/images/gate/O_help_m02_on.gif','/images/gate/O_help_m03_on.gif');">
<form name = "form1" method = "post">
        <input type = "hidden" name = "p_process" value = "">
        <input type = "hidden" name = "p_pageno" value = "">
        <input type = "hidden" name = "p_seq" value = "">
		<input type = "hidden" name = "p_formuserid" value = "<%=s_userid%>">
		<input type = "hidden" name = "p_touserid" value = "">
       
		
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="907" bgcolor="EFEFEA"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="830" height="120">
          <param name="movie" value="/images/gate/O_sub03.swf">
          <param name=wmode value=transparent>
          <param name="quality" value="high">
          <embed src="/images/gate/O_sub03.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="830" height="120"></embed></object></td>
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
                <td height=30><a href="javascript:faqList()" target="_self" onfocus=this.blur() onMouseOver="MM_swapImage('sub01','','/images/gate/O_help_m01_on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/gate/O_help_m01.gif" name="sub01" width="85" height="20" border="0"></a></td>
              </tr>
              <tr> 
                <td height=30><a href="javascript:qnaList()" target="_self" onfocus=this.blur() onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('sub02','','/images/gate/O_help_m02_on.gif',1)"><img src="/images/gate/O_help_m02.gif" name="sub02" width="85" height="20" border="0"></a></td>
              </tr>
              <tr> 
                <td height=30><img src="/images/gate/O_help_m03_on.gif"  width="85" height="20" border="0"></td>
              </tr>
              <tr>
                <td><img src="/images/gate/O_help_bottom.gif" width="85" height="40"></td>
              </tr>
            </table></td>
          <td width="25">&nbsp;</td>
          <td width="775"> <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                      <td width="40" valign="middle"> 성명 :</td>
                      <td width="190" valign="middle"><input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                name = "p_searchtext" value = "<%=v_searchtext %>" size = "25" maxlength = "30">                      </td>
                      <td width="1%" valign="middle">&nbsp;</td>
                                                <td width="50" valign="middle">                                                    <p><a href = "javascript:selectList()"><img src="/images/gate/btn_search.gif" width="50" height="22"border="0"></a></p>
</td>
                                                <td align="right" valign="middle">
                                                    
                                                    <p align="right"><%= SelectEduBean.getGrcode(box, true, false)%></p>
</td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height=6></td>
              </tr>
              <tr> 
                <td class=dot_line></td>
              </tr>
              <tr> 
                <td height=12></td>
              </tr>
              <tr> 
                <td height=6><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="13%"><img src="/images/gate/hyndai_tab_on.gif" width="103" height="25"></td>
                      <td width="2%">&nbsp;</td>
                      <td width="85%"><a href="javascript:kiaList()"><img src="/images/gate/kia_tab.gif" width="123" height="25" border="0"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height=6></td>
              </tr>
            </table>
            <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="136" class=O_boardbgimg><span class=b_title>팀명</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="92" class=O_boardbgimg><span class=b_title>성명</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="66" class=O_boardbgimg><span class=b_title>직급</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
               
                                <td width="128" class=O_boardbgimg>
                                    <p align="center"><span class=b_title>전화번호</span></p>
</td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                                <td width="145" class=O_boardbgimg>
                                    <p align="center"><span class=b_title>이동전화</span></p>
</td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="70" class=O_boardbgimg><span class=b_title>문의하기</span></td>
              </tr>
			  <!--가져온값화면에 출력하는 부분-->
<%if(list.size() != 0){%>
			  <%for(i = 0; i < list.size(); i++) {
					DataBox dbox = (DataBox)list.get(i);  
                    
					v_name   = dbox.getString("d_name");
					v_jikwinm    = dbox.getString("d_jikwinm");
					v_jikmunm      = dbox.getString("d_jikmunm");
					v_orga_ename  = dbox.getString("d_orga_ename");
					v_comptel = dbox.getString("d_comptel");
					v_handphon = dbox.getString("d_handphone");
					v_totalpage = dbox.getInt("d_totalpage");
                    v_rowcount = dbox.getInt("d_rowcount");  
					v_userid = dbox.getString("d_userid");
                  if (!v_searchtext.equals("")) {
						v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                        // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
				  }
				  
				  if ((i % 2) == 0) {
					v_classname = "b_list_W";
				  } else {
					v_classname = "b_list_G";
				}
				 if ((i % 2) == 0) {
            v_classname = "b_list_W";
         } else {
            v_classname = "b_list_G";
        }
				  %>
					
              <tr> 
                <td width="136" align="center" class="<%=v_classname%>"><%=v_orga_ename%><br></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="92" align="center" class="<%=v_classname%>"><%=v_name%></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="66" align="center" class="<%=v_classname%>"><%=v_jikwinm%></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
               
                <td width="128" align="center" class="<%=v_classname%>"><%=v_comptel%></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="145" align="center" class="<%=v_classname%>"><%=v_handphon%></td>
                <td valign="bottom" class="<%=v_classname%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="70" align="center" class="<%=v_classname%>"><a href="javascript:selectAction2('<%=v_userid%>')"><img src="/images/gate/btn_question.gif" width="58" height="18" border="0"></a></td>
				
					
              </tr>
              <tr> 
                <td colspan="13" align="center" class=b_guide></td>
              </tr>
              <%}%>
<%}else{%>
           

                                                    <tr>
                                                        <td class=b_list_W colspan="13" align=center >
															검색 된 데이터가 없습니다.			
														</td>
													</tr>

<%}%>
              <tr> 
                <td colspan="13" class=b_list_bottom></td>
              </tr>
			  <!--출력부분끝-->
              <tr> 
                <td colspan="13" height=8></td>
              </tr>
              <tr> 
                                    <td colspan="13" height=20>
                                        <p align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></p>
</td>
              </tr>
            </table></td>
          <td width="10">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
          <td width="25" height="50">&nbsp;</td>
                        <td width="775" height="50">
                            <p align="center">&nbsp;</p>
</td>
          <td height="50">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3"><img src="/images/gate/O_copyright.gif" width="370" height="41"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td class=O_bgline></td>
  </tr>
</table>
</form>
</body>
</html>
