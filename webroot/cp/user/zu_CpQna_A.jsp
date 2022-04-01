<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 Q&A답변등록화면
//  2. 프로그램명: zu_CpQna_A.jsp
//  3. 개      요: 외주관리 시스템 Q&A답변등록화면
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

    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    String  v_types      = box.getString("p_types");
    String  v_title      = "";
    String  v_contents   = "";
    String  v_indate     = "";
    String  v_inuserid   = "";
    String  v_inusername = "";
	Vector  v_realfileVector = null;
	Vector  v_savefileVector = null;
	String  v_realmotion  = "";
	String  v_savemotion = "";
	
	String content = "";
	String width = "650";
	String height = "200";

    DataBox dbox = (DataBox)request.getAttribute("selectQna");

	v_inuserid   = dbox.getString("d_inuserid");
	v_types = dbox.getString("d_types");
	v_indate    = dbox.getString("d_indate");
	v_title    = dbox.getString("d_title");
	v_inuserid   = dbox.getString("d_inuserid");
	v_types = dbox.getString("d_types");
	v_indate    = dbox.getString("d_indate");
	v_title    = dbox.getString("d_title");
	v_contents = StringManager.replace(dbox.getString("d_contents"),"&amp;","&");
	v_realfileVector = (Vector)dbox.getObject("d_realfile");
	v_savefileVector = (Vector)dbox.getObject("d_savefile");
	v_realmotion  = dbox.getString("d_realmotion");
	v_savemotion  = dbox.getString("d_savemotion");
        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }  else if (v_search.equals("contents")) {
                v_contents = StringManager.replace(v_contents, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
    

    String s_userid    = box.getSession("userid");
    String s_name      = box.getSession("name");

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
function selectList() {
	document.form1.action = "/servlet/controller.cp.CpQnaServlet";
    document.form1.p_process.value = "";
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


function cancel() {
        document.form1.action = "/servlet/controller.cp.CpQnaServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }
function insert() {
	if(document.all.use_editor[0].checked) {        
		form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
		}else {
		form1.content.value = document.all.txtDetail.value;
		}
        if (blankCheck(document.all.p_title.value)) {
			alert("제목을 입력하세요!");
	        document.all.p_title.focus();
		    return;
        }                
        if (realsize(document.all.p_title.value) > 100) {
			alert("제목은 한글기준 50자를 초과하지 못합니다.");
	        document.all.p_title.focus();
		    return;
        }		              
        if (blankCheck(document.all.content.value)) {
			alert("내용을 입력하세요!");
	        document.all.content.focus();
	        return;
        }	    
		document.form1.action = "/servlet/controller.cp.CpQnaServlet";
		document.form1.p_process.value = "reply";              
        document.form1.submit();
}
            function blankCheck( msg ) {
                var mleng = msg.length;
                chk=0;
                
                for (i=0; i<mleng; i++) {
                    if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
                }
                if ( chk == 0 ) return (true);
                
                return (false);
            }
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

function adminList() {
	document.form1.action = "/servlet/controller.cp.CpAdminServlet";
	document.form1.p_process.value    = "adminiList";
	document.form1.submit();
}
//-->
</script>
</head>

<body leftmargin=0 topmargin=0 onLoad="MM_preloadImages('/images/gate/O_help_m01_on.gif','/images/gate/O_help_m02_on.gif','/images/gate/O_help_m03_on.gif');">
<form name = "form1" enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"  value = "">
    <input type = "hidden" name = "p_select"  value = "">
    <input type = "hidden" name = "p_pageno"  value = "">
	<input type = "hidden" name = "p_process">
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
                <td height=30><a href="javascript:selectList()"><img src="/images/gate/O_help_m02_on.gif" name="sub02" width="85" height="20" border="0"></a></td>
              </tr>
              <tr> 
                <td height=30><a href="javascript:adminList()" target="_self" onfocus=this.blur() onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('sub03','','/images/gate/O_help_m03_on.gif',1)"><img src="/images/gate/O_help_m03.gif" name="sub03" width="85" height="20" border="0"></a></td>
              </tr>
              <tr>
                <td><img src="/images/gate/O_help_bottom.gif" width="85" height="40"></td>
              </tr>
            </table></td>
          <td width="25">&nbsp;</td>
          <td width="775"> 
            <table width="775" border="0" cellspacing="0" cellpadding="0">
			<tr> 
                <td width="98" class=O_boardwrite_top></td>
                <td width="677" class=O_boardwrite_top></td>
              </tr>
              <tr> 
                <td width="98" class=b_write>질문제목</td>
                <td width="677" class=b_textarea><%=v_title%></td>
              </tr>
              <tr> 
                <td class=b_write>작성자</td>
                <td class=b_textarea><%=v_inuserid%></td>
              </tr>
              <tr> 
                <td class=b_write>질문작성일</td>
                <td class=b_textarea><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd")%></td>
              </tr>
              <tr> 
                <td valign="top" class=b_write style="padding-top:10px">질문내용</td>
                <td class=b_textarea> <%= v_contents%><br> </td>
              </tr>
              <tr> 
			  <tr> 
                <td height=4 width="88"></td>
                <td height=4 colspan="2" width="726"></td>
              </tr>
              <tr> 
                <td colspan="3" class=b_list_bottom width="814"></td>
              </tr>
              <tr> 
                <td height=8 width="88"></td>
                <td height=8 colspan="2" width="726"></td>
              </tr>
              <tr> 
                <td class=b_REguide></td>
                <td class=b_REguide></td>
              </tr>
<td width="98" class=b_write>답변제목</td>
              <td width="677" class=b_textarea><input style="padding:1; border:1 solid #D1D1D1;" size="84" maxlength="200" name="p_title" class="inputsub"></td>
              <tr> 
                <td width="98" class=b_write>작성자</td>
                <td width="677" class=b_textarea><%=s_name%></td>
              </tr>
              <tr> 
                <td width="98" class=b_write>답변작성일</td>
                <td width="677" class=b_textarea><%= FormatDate.getDate("yyyy/MM/dd") %></td>
              </tr>
              <tr> 
                <td valign="top" class=b_write>답변내용</td>
                <td class=b_textarea>  
				<%content = StringManager.replace(v_contents,"<P>","<P>>");%>
				<!-- DHTML Editor  -->
				
					<%@ include file="/portal/include/DhtmlEditor.jsp" %>
				<!-- DHTML Editor  -->
				</td>
              </tr>
			  <tr>
										<td class = "b_write" width = "11%" align = "center"><div align="justify">                                            <p align="left">파일첨부</p>
</div></td>
										
										<td class = "table-content" width = "87%" align = "left">
											&nbsp;&nbsp; <input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file">																																	
										</td>
									
									</tr>
              <tr> 
                <td height=4></td>
                <td height=4></td>
              </tr>
              <tr> 
                <td colspan="2" class=b_list_bottom></td>
              </tr>
              <tr> 
                <td height=8></td>
                <td height=8></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="92%" align="right"><a href="javascript:insert()"><img src="/images/gate/btn_save.gif" width="50" height="22" border=0></a></td>
                      <td width="1%">&nbsp;</td>
                      <td width="7%" align="right"><a href="javascript:cancel()"><img src="/images/gate/btn_cancel.gif" width="50" height="22" border="0"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td>&nbsp;</td>
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
