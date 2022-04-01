<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 자유게시판 수정화면
//  2. 프로그램명: zu_CpFree_U.jsp
//  3. 개      요: 외주관리 시스템 자유게시판 수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 20
//  7. 수      정: 이연정 2005. 07. 20
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

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "";

int v_seq = box.getInt("p_seq");
//int v_upfilecnt = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수 
String v_searchtext = box.getString("p_searchtext");
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";
String content = "";
String width = "650";
String height = "200";

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid   = dbox.getString("d_userid");
v_name   = dbox.getString("d_name");
v_indate    = dbox.getString("d_indate");
v_title    = dbox.getString("d_title");
v_seq      = dbox.getInt("d_seq");
content = StringManager.replace(dbox.getString("d_content"),"&amp;","&");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");

//------------------------------------------------------------------------------------------------------
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../../css/cp_style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

//리스트로 이동하기
function cancel() {
    document.form1.action = "/servlet/controller.cp.CpFreeServlet";
    document.form1.p_process.value = "select";
    document.form1.submit();
}

//수정하여 저장하기
function update() { 
	if(document.all.use_editor[0].checked) {        
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
	}else {
		form1.content.value = document.all.txtDetail.value;
    }
    if (blankCheck(document.form1.p_title.value)) {
        alert("제목을 입력하세요!");
        document.form1.p_title.focus();
        return;
    }                
    if (realsize(document.form1.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }		              
    if (blankCheck(document.form1.content.value)) {
        alert("내용을 입력하세요!");
        document.form1.content.focus();
        return;
    }	    
    document.form1.action = "/servlet/controller.cp.CpFreeServlet";
    document.form1.p_process.value = "update";                
    document.form1.submit();
} 

//빈공간 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}

//글자사이즈 체크
function realsize( value ) {
    var len = 0;
    if ( value == null ) return 0;
    for(var i=0;i<value.length;i++){
        var c = escape(value.charAt(i));
        if ( c.length == 1 ) len ++;
        else if ( c.indexOf("%u") != -1 ) len += 2;
        else if ( c.indexOf("%") != -1 ) len += c.length/3;
    }
    return len;
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

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('../../../images/cp/m_board_on.gif','../../../images/cp/m_data_on.gif','../../../images/cp/m_company_on.gif','../../../images/cp/m_sdata_on.gif','../../../images/cp/m_swdata_on.gif','../../../images/user/button/btn_idpw_on.gif','../../../images/user/button/btn_login_on.gif')">
<form name = "form1" enctype = "multipart/form-data" method = "POST">
<input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
<input type = "hidden" name = "p_select"  value = "<%= v_select %>">
<input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
<input type = "hidden" name = "p_process"  value = "">
<input type = "hidden" name = "p_userid" value = "<%= v_userid %>">

<%@include file="zu_Include.jsp"%>
<!-- center -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../../images/cp/bg_subtotal.gif">
  <tr> 
    <td valign="top">
	<!-- topimg -->
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="12" valign="top"><img src="../../../images/cp/img_sub_head.gif"></td>
          <td width="888" valign="top"><img src="../../../images/cp/boardimg.jpg"></td>
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
                      <td><img src="../../../images/cp/tit_board.gif"></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center"><table width="800" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#D5D5D5" >
                          <tr> 
                            <td width="101" class="tbl_gtit_l"><font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>제목</td>
                            <td class="tbl_btit"><input type="text" size="70"  onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background = '#ffffff'" 
	                                            name = "p_title"  maxlength = "100" value = "<%= v_title %>"> 
                            </td>
                          </tr>
                          <tr> 
                            <td width="101" class="tbl_gtit_l"><font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>작성자</td>
                            <td class="tbl_bleft"><%= v_name %></td>
                          </tr>
                          <tr> 
                            <td width="101" valign="top" class="tbl_gtit_l" ><font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>내용</td>
                            <td  class="tbl_contents">
							<!-- DHTML Editor  -->
							<%@ include file="/portal/include/DhtmlEditor.jsp" %>
							<!-- DHTML Editor  -->
							</td>
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
                            <td align="right"><a href = "javascript:update();">
							<img src="../../../images/cp/btn_register.gif" border="0"></a>
							<a href = "javascript:cancel();">
							<img src="../../../images/user/button/btn_list.gif"  border="0"></a></td>
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
<!-- footer -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td background="../../../images/cp/bg_footer.gif"><table width="900" height="64" border="0" cellpadding="0" cellspacing="0">
        <tr valign="top"> 
          <td colspan="2"></td>
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
