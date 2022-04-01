<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 Q&A 수정화면
//  2. 프로그램명: zu_CpQna_U.jsp
//  3. 개      요: 외주관리 시스템 Q&A 수정화면
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

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_contents = "", v_inuserid = "", v_indate = "", v_types = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";

String width = "650";
String height = "200";	

String s_name = box.getSession("name");


DataBox dbox = (DataBox)request.getAttribute("selectQna");

v_inuserid   = dbox.getString("d_inuserid");
v_types = dbox.getString("d_types");
v_indate    = dbox.getString("d_indate");
v_title    = dbox.getString("d_title");
v_seq      = dbox.getInt("d_seq");
String content = StringManager.replace(dbox.getString("d_contents"),"&amp;","&");

out.println("content::::"+content);
v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");

//------------------------------------------------------------------------------------------------------
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
<script language="JavaScript" type="text/JavaScript">
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

            function selectList() {
                document.form1.action = "/servlet/controller.cp.CpQnaServlet";
                document.form1.p_process.value = "";     
                document.form1.submit();	
            }            
            
            function cancel(seq, userid, types) {
        document.form1.action = "/servlet/controller.cp.CpQnaServlet";
        document.form1.p_process.value = "selectView";
        document.form1.p_seq.value = seq;
        document.form1.p_userid.value    = userid;
		document.form1.p_types.value    = types;
        document.form1.submit();
    }
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
                document.form1.action = "/servlet/controller.cp.CpQnaServlet";
                document.form1.p_process.value = "update";                
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
</script>
</head>

<body leftmargin=0 topmargin=0 onLoad="MM_preloadImages('/images/gate/O_help_m01_on.gif','/images/gate/O_help_m02_on.gif','/images/gate/O_help_m03_on.gif');">
<form name = "form1" enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
	<input type = "hidden" name = "p_types"     value = "<%= v_types %>">
    <input type = "hidden" name = "p_searchtext"  value = "">
    <input type = "hidden" name = "p_select"  value = "">
    <input type = "hidden" name = "p_pageno"  value = "">
	<input type = "hidden" name = "p_userid"  value = "<%=v_inuserid%>">
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
		  <!--입력내용시작-->
         <table width="814" border="0" cellspacing="0" cellpadding="0">
			
				<!-- 제목입력란-->
            
              <tr> 
                <td width="88" class=b_write>                                            <p align="left">질문제목</p></td><td colspan="2" class=b_textarea width="726"><input style="padding:1; border:1 solid #D1D1D1;" type = "text"  onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background = '#ffffff'" 
	                                            name = "p_title" size = "84" maxlength = "100" value = "<%= v_title %>"></td>
				
              </tr>
			  <!--제목입력란끝-->
			  <!--작성자이름값 얻어오기-->

              <tr> 
                
				 <td class=b_write width="88">                                             <p align="left">작성자</p></td>
				
				<!--작성자이름값 얻어오기-->
                <td class = "table-content" width = "710" align = "left">&nbsp;&nbsp; <%= s_name %></td>
                                   
              </tr>
			  <!--이름값끝-->
              
              <tr> 
                                    <td class=b_write width="88">
                                        <p align="left">질문작성일</p></td>
                <td class="table_text3" width="16">&nbsp;&nbsp;<%= FormatDate.getDate("yyyy/MM/dd") %></td>
              </tr>
			  <tr> <!--내용입력란-->
                <td valign="top" class=b_write style="padding-top:10px" width="88">                                            <p align="left">내용</p></td><td colspan="2" class=b_textarea width="726">  
				<!-- DHTML Editor  -->
					<%@ include file="/portal/include/DhtmlEditor.jsp" %>
				<!-- DHTML Editor  --> </td></tr>
			  <!--내용입력란끝-->
			 
              <!--파일업로드-->
			
									
									 <%//  if(box.getBoolean("p_isUpload")) {    //    파일첨부 여부 %>
	                                    <tr>
	                                        <td width="88" class=b_write align = "center"><div align="left">첨부파일</div></td>
	                                        <td class = "table-content"  align = "left">
	                                        <%   for(int i = 0; i < v_realfileVector.size(); i++) {  
	                                                     String v_realfile = (String)v_realfileVector.elementAt(i);
                                                             String v_savefile = (String)v_savefileVector.elementAt(i);
	                                                    if(v_realfile != null) {  %>                                                                                   
                                                    &nbsp;<%= v_realfile%>
                                                    &nbsp;<input type = "checkbox"  onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_savefile" value = "<%= v_savefile%>" > (삭제시 체크)
                                                    <br>                                                    
                                                    <input type = "hidden" name = "p_realfile"  value = "<%= v_realfile%>">
                                                <%        }                                                                
                                                         }   %>
                                                </td>
	                                    </tr>	                          
					<tr>
                                                <td width="88" class=b_write align = "center"><div align="left">파일첨부</div></td>
                                                <td class = "table-content"  align = "left">
                                                    &nbsp;&nbsp;<input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file"><br>
                                                    
                                                </td>
					</tr>
					
					
				<%//  }   %>         			
								
								
			  <!--파일업로드끝-->
			  
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
                <td width="88">&nbsp;</td>
                <td colspan="2" width="726"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
					<!--등록하기-->
                     <td width="83%" align="right"><a href = "javascript:update();"><img src="/images/gate/btn_save.gif" width="50" height="22" border="0"></a></td>
                     <td width="7%"><a href = "javascript:cancel('<%=v_seq%>','<%=v_inuserid%>','<%=v_types%>')"><img src="/images/gate/btn_cancel.gif" width="50" height="22" border="0"></a></td>
                     <td width="10%" align="right"><a href = "javascript:selectList();"><img src="/images/gate/btn_list.gif" width="74" height="22" border="0"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td width="88">&nbsp;</td>
                <td colspan="2" width="726">&nbsp;</td>
              </tr>
            </table>
			
			<!--입력내용끝--></td>
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
