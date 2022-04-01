<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 Q&A리스트화면
//  2. 프로그램명: zu_CpQ&A_L.jsp
//  3. 개      요: 외주관리 시스템 Q&A리스트화면
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
if (box == null) box = RequestManager.getBox(request);
int v_tabseq   = box.getInt("p_tabseq");
int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
int v_seq = 0,v_readcnt = 0,i = 0;
String v_inuserid = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "", v_types = "" ;
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
ArrayList list = (ArrayList)request.getAttribute("selectList");
String  v_classname1 = "";
String  v_classname2 = "";   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");
String  v_search      = box.getString("p_search");

int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");

String s_usernm = box.getSession("name");
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템 Q &amp; A</title>
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
         document.form1.action = "/servlet/controller.cp.CpQnaServlet";
         document.form1.p_process.value = "";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.cp.CpQnaServlet";
         document.form1.p_process.value = "";
         document.form1.submit();
    }
    function selectList() {
        document.form1.action = "/servlet/controller.cp.CpQnaServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }
    function insertPage() {
        document.form1.action = "/servlet/controller.cp.CpQnaServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    function select(seq, userid, types) {
        document.form1.action = "/servlet/controller.cp.CpQnaServlet";
        document.form1.p_process.value = "selectView";
        document.form1.p_seq.value = seq;
        document.form1.p_userid.value    = userid;
		document.form1.p_types.value    = types;
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

function adminList() {
	document.form1.action = "/servlet/controller.cp.CpAdminServlet";
	document.form1.p_process.value    = "adminiList";
	document.form1.submit();
}     
//-->
</script>
</head>

<body leftmargin=0 topmargin=0 onLoad="MM_preloadImages('/images/gate/O_help_m01_on.gif','/images/gate/O_help_m02_on.gif','/images/gate/O_help_m03_on.gif');">
   <form name = "form1" method = "post">
       
        <input type = "hidden" name = "p_process" value = "">
        <input type = "hidden" name = "p_pageno" value = "">
        <input type = "hidden" name = "p_seq" value = "">
        <input type = "hidden" name = "p_userid" value = "">
		<input type = "hidden" name = "p_types"  value = "<%=v_types%>">

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
          <td width="775"><table width="779" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="465" align="right" valign="middle">

                                        <p><!--검색시작-->
				<select name = "p_select" class = "input">
                                            <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
                                            <option value='contents' <% if (v_search.equals("contents")) out.print("selected"); %>>내용</option>
               </select><!--검색끝--> </p>
</td>
                                    <td width="134" align="right" valign="middle">                                        <p><input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                name = "p_searchtext"  size = "25" maxlength = "30"></p>
</td>
                                    <td width="1%" align="right" valign="middle">&nbsp;</td>
                                    <td width="50" align="right" valign="middle">                                        <p><a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_search.gif" width="50" height="22" border="0" align="middle"></a></p>
</td>
                                    <td width="1%" align="right" valign="middle">&nbsp;</td>
                                    <td width="50" align="right" valign="middle">
                                        <p><a href="javascript:insertPage()" target="_self" onfocus=this.blur()><%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %><img src="/images/gate/btn_regist.gif" width="50" height="22" border="0" align="middle"></a><%}%>                                        </p>
</td>
              </tr>
                                <tr>
                                    <td height="5" align="right" colspan="6" width="779"></td>
              </tr>
            </table> 
            <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="54" class=O_boardbgimg><span class=b_title>번호</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="579" class=O_boardbgimg><span class=b_title>제목</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="140" class=O_boardbgimg><span class=b_title>작성자</span></td>
              </tr>
<%if(list.size() != 0){%>
 <%  for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);  
                   v_dispnum = dbox.getInt("d_dispnum");
                   v_inuserid   = dbox.getString("d_inuserid");
                   //v_name   = dbox.getString("d_name");
                   v_indate    = dbox.getString("d_indate");
                   v_title    = dbox.getString("d_title");
                   v_seq      = dbox.getInt("d_seq");
				   v_types      = dbox.getString("d_types");
                   v_readcnt  = dbox.getInt("d_readcnt");
                   v_realmotion  = dbox.getString("d_realmotion");
                   v_savemotion  = dbox.getString("d_savemotion");
                   v_upfilecnt   = dbox.getInt("d_filecnt");
                   v_totalpage = dbox.getInt("d_totalpage");
                   v_rowcount = dbox.getInt("d_rowcount");  

        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }

        if ((i % 2) == 0) {
            v_classname1 = "b_list_W";
            v_classname2 = "b_list_W";
        } else {
          
			v_classname1 = "b_list_G";
            v_classname2 = "b_list_G";
        }

%>
			  <tr> 
               <!--리스트시작-->
				<td class="<%=v_classname1%>" width="54" align="center"><%=v_dispnum%></td>
                
				<td valign="bottom"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                
				<td  class="<%=v_classname2%>" width="579"  style="padding-left:10">
				
				<%if (Integer.parseInt(v_types) > 0) {%>
				<img src="/images/gate/board_re.gif" width="23" height="11" border="0">
                <% } %>
				
				<a href="javascript:select('<%=v_seq%>','<%=v_inuserid%>','<%=v_types%>')"><%=v_title%></a>
				</td>
               <td valign="bottom"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
               <td class="<%=v_classname1%>" width="140" align="center"><%=v_inuserid%></td>
			  
			  
			  </tr>
			  <tr> 
                <td colspan="16" align="center" class=b_guide></td>
              </tr>
             <%}%>
<%}else{%>
           

                                                    <tr>
                                                        <td class=b_list_W colspan="13" align=center >
															등록 된 내용이 없습니다.		
														</td>
													</tr>

<%}%>             
			  <!--리스트끝-->
              <tr> 
               <td colspan="4" class=b_list_bottom></td><td colspan="4" class=b_list_bottom></td>
              </tr>
              
			  
			  
			  <tr align="center"> 
                <td height=20 colspan="5"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
                  </td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td colspan="5" height=20></td>
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
