<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 자유게시판 리스트화면
//  2. 프로그램명: zu_CpFree_L.jsp
//  3. 개      요: 외주관리 시스템 자유게시판 리스트화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 18  
//  7. 수      정: 이연정 2005. 07. 18  
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

int v_seq = 0,v_readcnt = 0,i = 0;
String  v_classname = "";
String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");

String s_usernm = box.getSession("name");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../../css/cp_style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

//페이지이동
function go(index) {
     document.form1.p_pageno.value = index;
     document.form1.action = "/servlet/controller.cp.CpFreeServlet";
     document.form1.p_process.value = "";     
     document.form1.submit();
}

//페이지이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.cp.CpFreeServlet";
     document.form1.p_process.value = "";     
     document.form1.submit();
}

//건색조건을 이용한 리스트 리로딩
function selectList() {
	document.form1.action = "/servlet/controller.cp.CpFreeServlet";
	document.form1.p_process.value = "";   
	document.form1.p_pageno.value = "1";       
	document.form1.submit();
}

//입력화면으로 이동
function insertPage() {
	document.form1.action = "/servlet/controller.cp.CpFreeServlet";
	document.form1.p_process.value = "insertPage";  
	document.form1.p_pageno.value = "<%= v_pageno %>";    
	document.form1.submit();
}

//상세화면으로 이동
function select(num) {
	document.form1.action = "/servlet/controller.cp.CpFreeServlet";
	document.form1.p_process.value = "select";
	document.form1.p_seq.value = num;
	document.form1.p_userid.value = "<%=v_userid%>";
	document.form1.p_pageno.value = "<%= v_pageno %>"; 
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

function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('../../../images/cp/m_board_on.gif','../../../images/cp/m_data_on.gif','../../../images/cp/m_company_on.gif','../../../images/cp/m_sdata_on.gif','../../../images/cp/m_swdata_on.gif','../../../images/user/button/btn_idpw_on.gif','../../../images/user/button/btn_login_on.gif')">
<form name = "form1" method = "post">
<input type = "hidden" name = "p_process" value = "">
<input type = "hidden" name = "p_pageno" value = "">
<input type = "hidden" name = "p_seq" value = "">
<input type = "hidden" name = "p_userid" value = "">

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
                      <td align="center">
					  <!-- 리스트테이블 -->
					  <table width="800" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#D5D5D5" >
                          <tr> 
                            <td width="37" class="tbl_gtit"><font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>번호</td>
                            <td  width="492" class="tbl_gtit"><font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>제목</td>
                            <td  width="73" align=center class="tbl_gtit"><font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>작성자</td>
                            <td  width="86" align=center class="tbl_gtit"> <font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>작성일</td>
                            <td  width="68" align=center class="tbl_gtit"><font color="#6A9659" size="-2"><img src="/images/cp/bl_data.gif"></font>조회수 
                            </td>
                          </tr>


<%if(list.size() != 0){%>
				<%for(i = 0; i < list.size(); i++) {
					DataBox dbox = (DataBox)list.get(i);  
                    v_dispnum = dbox.getInt("d_dispnum");
					v_userid   = dbox.getString("d_userid");
					v_name   = dbox.getString("d_name");
					
					v_indate    = dbox.getString("d_indate");
					v_title    = dbox.getString("d_title");
					v_seq      = dbox.getInt("d_seq");
					v_readcnt  = dbox.getInt("d_cnt");
					v_realmotion  = dbox.getString("d_realmotion");
					v_savemotion  = dbox.getString("d_savemotion");
					v_upfilecnt   = dbox.getInt("d_filecnt");
					v_totalpage = dbox.getInt("d_totalpage");
					v_rowcount = dbox.getInt("d_rowcount");  
					
                  if (!v_searchtext.equals("")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                        // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                        }
						if ((i % 2) == 0) {
							v_classname = "b_list_W";
						 } else {
							v_classname = "b_list_G";
						}

						%>
                          <tr> 
                            <td width="37" class="tbl_grc"><%=v_dispnum%></td>
                            <td  width="492" class="tbl_bleft"><a href="javascript:select(<%=v_seq%>)" target="_self"><%=v_title%></a></td>
                            <td  width="73" class="tbl_grc" ><%=v_name%></td>
                            <td  width="86" class="tbl_grc" ><%= FormatDate.getFormatDate(v_indate, "yyyy-MM-dd") %></td>
                            <td  width="68" class="tbl_grc" ><%=v_readcnt%></td>
                          </tr>

					<%}%>	
				<%}else{%>
           

                         <tr>
                             <td class=b_list_W colspan="13" align=center >
								등록된 내용이 없습니다.		
							</td>
						</tr>
			<%}%>		
                        </table>
					  </td>
                    </tr>
                    <tr> 
                      <td align="center">
					    <table width="800" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="10" colspan = "2"></td>
                          </tr>
                          <tr> 
                            <td align="center" width = "760"> 
							<%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %>
							</td>
                            <td align="right">
							 <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
							<a href = "javascript:insertPage()"><img src="../../../images/user/button/btn_register.gif" width="45" height="21" border="0"></a>
							 <%  }   %>
							</td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="269">&nbsp;</td>
                      <td width="59"> 
					    <select name="p_select" >
                          <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
						  <option value = "name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
                          <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
                        </select> 
					  </td>
                      <td width="133"><input name="p_searchtext" type="text" maxlength = "30" class="input" value="" size="20" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'"></td>
                      <td width="339"><a href = "javascript:selectList()"><img src="../../../images/cp/btn_search.gif"></a></td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="4"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
	</td>
  </tr>
</table>
</form>
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
