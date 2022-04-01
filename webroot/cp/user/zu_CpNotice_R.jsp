<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 공지사항 상세화면
//  2. 프로그램명: zu_CpNotice_R.jsp
//  3. 개      요: 외주관리 시스템 공지사항 상세화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 19 
//  7. 수      정: 이연정 2005. 07. 19
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "", v_types = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select = box.getString("p_select"); 
int v_pageno = box.getInt("p_pageno");

int    v_upfilecnt      = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";
String s_cpseq      = box.getSession("cpseq");
String s_gadmin     = box.getSession("gadmin");

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid   = dbox.getString("d_userid");
v_name   = dbox.getString("d_adname");
v_indate    = dbox.getString("d_addate");
v_title    = dbox.getString("d_adtitle");
v_seq      = dbox.getInt("d_seq");
v_content = dbox.getString("d_adcontent");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_realmotion     = dbox.getString("d_realmotion");
v_savemotion     = dbox.getString("d_savemotion");



v_types = dbox.getString("d_types");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../../css/cp_style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

//리스트 페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.cp.CpNoticeServlet";
	document.form1.p_process.value = "selectList";     
	document.form1.submit();	
}            


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
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('../../../images/cp/m_board_on.gif','../../../images/cp/m_company_on.gif','../../../images/cp/m_sdata_on.gif','../../../images/cp/m_swdata_on.gif','../../../images/user/button/btn_idpw_on.gif','../../../images/user/button/btn_login_on.gif','../../../images/cp/m_data_on.gif')">
<form name = "form1" method = "post">
<input type = "hidden" name = "p_process"   value = "">
<input type = "hidden" name = "p_pageno"    value = "">
<input type = "hidden" name = "p_seq"       value = "">
<input type = "hidden" name = "p_userid"    value = "">
<input type = "hidden" name = "p_upfilecnt" value = "<%= v_upfilecnt %>">
<%@include file="zu_Include.jsp"%>
<!-- center -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../../images/cp/bg_subtotal.gif">
  <tr> 
    <td valign="top">
	<!-- topimg -->
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="12" valign="top"><img src="../../../images/cp/img_sub_head.gif"></td>
          <td width="888" valign="top"><img src="../../../images/cp/noticeimg.jpg"></td>
        </tr>
        <tr valign="top"> 
          <td colspan="2">
		
		  <table width="900" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20" align="center" valign="top"></td>
              </tr>
              <tr> 
                <td align="center" valign="top">
				<table width="835" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="../../../images/cp/tit_notice.gif"></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center">
					  <!-- sw자료실 보기 --> 
					  <table width="800" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#D5D5D5" >
                          <tr> 
                            <td width="120" class="tbl_grtit_l"><font color="#339999" size="-2"><img src="/images/cp/bl_board.gif"></font>제목</td>
                            <td colspan="3" class="tbl_btit">&nbsp;&nbsp;<%= v_title %></td>
                          </tr>
                          <tr> 
                            <td width="120" class="tbl_grtit_l"><font color="#339999" size="-2"><img src="/images/cp/bl_board.gif"></font>작성자</td>
                            <td width="384" class="tbl_bleft">&nbsp;&nbsp;<%= v_name %></td>
                            <td width="112" class="tbl_grtit_l"><font color="#339999" size="-2"><img src="/images/cp/bl_board.gif"></font>작성일</td>
                            <td width="148" class="tbl_bleft">&nbsp;&nbsp;<%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                          </tr>
                          <tr> 
                            <td class="tbl_grtit_l" ><font color="#339999" size="-2"><img src="/images/cp/bl_board.gif"></font>첨부파일</td>
                            <td colspan="3" class="tbl_bdata">
			<%		
				if( v_realfileVector != null ) { 
					for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

						String v_realfile = (String)v_realfileVector.elementAt(i);
						String v_savefile = (String)v_savefileVector.elementAt(i);  	

						if(v_realfile != null && !v_realfile.equals("")) {  %>
							&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
					
							<%= v_realfile%></a><br>
							<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
				<%		}   %>
		<%			}
				}
		%>

							</td>
                          </tr>
                          <tr> 
                            <td colspan="4" valign="top" class="tbl_grtit" ><font color="#339999" size="-2"><img src="/images/cp/bl_board.gif"></font>내용</td>
                          </tr>
                          <tr> 
                            <td colspan="4" valign="top" class="tbl_contents" ><%= StringManager.replace(v_content,"&amp;","&") %></p></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="center"><table width="800" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="10" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td width="418" align="right">&nbsp; </td>
                            <td align="right"><a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="../../../images/user/button/btn_list.gif" border="0"></a></td>
                          </tr>
                          <tr>
                            <td align="right">&nbsp;</td>
                            <td align="right">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  
                </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
<%@ include file = "/include/cp_footer.jsp"%>


<%@ include file = "/learn/library/getJspName.jsp" %> 

</body>
</html>

