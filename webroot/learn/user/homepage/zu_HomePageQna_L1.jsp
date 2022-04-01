<%
//**********************************************************
//  1. 제      목: 홈페이지 Q&A 리스트화면
//  2. 프로그램명: zu_HomePageQna_L.jsp
//  3. 개      요: 홈페이지 Q&A 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 6. 29
//  7. 수      정: 이연정 2005. 6. 29
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");
if (box == null) box = RequestManager.getBox(request);
int v_tabseq   = box.getInt("p_tabseq");

int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
int v_seq = 0,v_readcnt = 0,i = 0,v_cnt = 0;

String v_inuserid = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "", v_types = "" ,v_name="",v_upfile="";

int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
ArrayList list = (ArrayList)request.getAttribute("selectList");
  
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");
String  v_search      = box.getString("p_search");

int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");

String s_usernm = box.getSession("name");
String v_classname1 = "";
String v_classname2 = "";
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../homepage/inc/style01.css" rel="stylesheet" type="text/css">
<link href="../../../css/user_style1.css" rel="stylesheet" type="text/css">
<SCRIPT src="/script/cafe_select.js" language="JavaScript"></SCRIPT>
<script language="JavaScript" type="text/JavaScript">
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

function insertPage() {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "insertPage";  
	document.form1.p_pageno.value = "<%= v_pageno %>";    
	document.form1.submit();
}
 
function go(index) {
	document.form1.p_pageno.value = index;
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "";
	document.form1.submit();
}
function goPage(pageNum) {
	 document.form1.p_pageno.value = pageNum;
	 document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	 document.form1.p_process.value = "";
	 document.form1.submit();
}
function selectList() {
	if (form1.p_searchtext.value == ""){
		alert("검색 할 내용을 입력해 주세요");
	}else{
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "";
	document.form1.submit();
	}
}

function select(seq, userid, types) {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
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
	document.form1.action            = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.submit()
<% } %>
}

function adminList() {
	document.form1.action = "/servlet/controller.homepage.HomePageAdminServlet";
	document.form1.p_process.value    = "adminiList";
	document.form1.submit();
}     

function helpdeskList(){
	document.form1.p_process.value = "selectList";
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.submit();
}
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
</table>
<!-- center -->
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
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; Q&amp;A</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="../../../images/user/support/type1/tit_qna.jpg" ></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>

				  <!-- 게시판 시작 -->
				  <form name = "form1" method = "post">
				  <input type = "hidden" name = "p_process" value = "">
				  <input type = "hidden" name = "p_pageno" value = "">
				  <input type = "hidden" name = "p_seq" value = "">
				  <input type = "hidden" name = "p_userid" value = "">
				  <input type = "hidden" name = "p_types"  value = "<%=v_types%>">


                  <!-- qna table -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr valign="top"> 
                      <td colspan="6"> <table width="675" border="0" cellpadding="0" cellspacing="0" background="../../../images/user/support/sup_tb_bg.gif">
                          <tr> 
                            <td width="6"><img src="../../../images/user/support/sup_tb_head.gif" width="5" height="28"></td>
                            <td width="39" align="center" valign="bottom"><img src="../../../images/user/support/sup_tb_num.gif" width="19" height="20"></td>
                            <td width="327" align="center" valign="bottom"><img src="../../../images/user/support/sup_tb_title.gif" width="19" height="20"></td>
                            <td width="99" align="center" valign="bottom"><img src="../../../images/user/support/sup_tb_pe.gif" width="29" height="20"></td>
                            <td width="98" align="center" valign="bottom"><img src="../../../images/user/support/sup_tb_day.gif" width="27" height="20"></td>
                            <td width="56" align="center" valign="bottom"><img src="../../../images/user/support/sup_tb_addfile.gif" width="38" height="20"></td>
                            <td width="40" align="center" valign="bottom"><img src="../../../images/user/support/sup_tb_view.gif" width="18" height="20"></td>
                            <td width="10" align="right"><img src="../../../images/user/support/sup_tb_tail.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="../../../images/user/support/sup_tb_tline.gif"></td>
                    </tr>

<%if(list.size() != 0){%>
<% 
			for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);  
                   v_dispnum = dbox.getInt("d_dispnum");
                   v_inuserid   = dbox.getString("d_inuserid");
                   v_name   = dbox.getString("d_name");
				   v_cnt = dbox.getInt("d_cnt");
                   v_indate    = dbox.getString("d_indate");
				   v_upfile = dbox.getString("d_upfile");
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
            v_classname1 = "board_text1";
            v_classname2 = "board_text3";
        } else {
            v_classname1 = "board_text2";
            v_classname2 = "board_text4";
        }

%>
                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>
                    <tr> 
                      <td width="45" class="tbl_br"><%=v_dispnum%></td>
                      <td width="327" class="<%=v_classname2%>">
					  <%if (Integer.parseInt(v_types) > 0) {%>
								&nbsp;&nbsp;&nbsp;&nbsp;<IMG src="/images/board/re.gif" border="0"> 
               				<% } %>
				
							<a href="javascript:select('<%=v_seq%>','<%=v_inuserid%>','<%=v_types%>')"><%=v_title%></a>

 							<%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
								<IMG src="/images/user/support/ico_new.gif" border="0">
							<% } %>
                      <td width="100" class="tbl_br"><%=v_name%>(<%=v_inuserid%>)</td>
                      <td width="97" class="tbl_br"><%=FormatDate.getFormatDate(v_indate, "yyyy/MM/dd")%></td>
                      <td width="56" align="center">
					  <%if(v_upfile.equals("") || v_upfile == ""){%>
					  <%}else{%>
							<img src="/images/user/support/ico_file.gif" width="13" height="12">
					  <%}%>
					  </td>
                      <td width="50" class="tbl_br"><%=v_cnt%></td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="../../../images/user/support/search_line.gif"></td>
                    </tr>
                      <%}%>
					<%}else{%>

					<tr>
					  <td colspan="13" align=center valign=top>
							등록 된 질문이 없습니다.		
					  </td>
					</tr>
					<%}%>
                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>
                    <tr bgcolor="B8D5E5"> 
                      <td height="5" colspan="6"><img src="../../../images/user/support/sup_tb_bo.gif" width="675" height="5"></td>
                    </tr>
                  </table>

                  <!-- 게시판 끝 -->
                  <!-- 페이징&검색 시작 -->
				  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td width="418" align="right">
					  <table width="130" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="19">
							<%= HomePageQnaBean.printPageList(v_totalpage, v_pageno, row) %>
							</td>
                          </tr>
                        </table></td>
                      <td width="257" align="right">
					  
					  <a href="javascript:insertPage()" target="_self" onfocus=this.blur()>
					<%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) { %>
					  <img src="../../../images/user/button/btn_register.gif" width="45" height="21" border="0"></a><%}%></td>

                    </tr>
                  </table>
				<!--검색시작-->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="227">&nbsp;</td>
                      <td width="56"> 
						<select name = "p_select" class = "input">
                			<option value="title" <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
							<option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
              			 </select>						
						</td>
                      <td width="132"><input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                name = "p_searchtext" value = "<%=v_searchtext %>" size = "25" maxlength = "30"></td>
                      <td width="260"><A HREF="javascript:selectList()"><IMG src="/images/common/bt_y_search.gif" border="0"></A></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table>
				<!--검색끝-->	
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
