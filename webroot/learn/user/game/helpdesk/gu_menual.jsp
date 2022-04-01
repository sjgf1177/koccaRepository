<%
//**********************************************************
//  1. 제      목: HelpDesk > 온라인 메뉴얼
//  2. 프로그램명 : gu_menual.jsp
//  3. 개      요: 온라인 메뉴얼
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정:
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
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","06");

    String  v_process     = box.getString("p_process");
    String  v_tem_grcode   = box.getSession("tem_grcode");
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT>
var old_menu = ''; var old_cell = '';
function comment( submenu, cellbar) {

	if( old_menu != submenu ) {

		if( old_menu !='' ) {
			old_menu.style.display = 'none';
		}
		submenu.style.display = 'block';
		old_menu = submenu;
		old_cell = cellbar;

	} else {
		submenu.style.display = 'none';
		old_menu = '';
		old_cell = '';
	}
}

function move(tab){
	document.form1.p_process.value= "Help";
	document.form1.p_tab.value = tab;
	document.form1.action = "/servlet/controller.homepage.HomePageHelpServlet";
	document.form1.submit();
}

</SCRIPT>

<style type="text/css">
<!--
.l-padding {
	padding-left: 12px;
}
-->
</style>

<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_process"   value = "">
		<input type = "hidden" name = "p_tab"		value = "">


<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="720" height="35" align="right"  background="/images/user/game/helpdesk/<%=tem_subimgpath%>/tit_menual.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
      HOME > Help Desk > 온라인메뉴얼</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
</table>
<!-- 탭 -->
<table width="719" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="719" align="right"><img src="/images/user/game/helpdesk/tab1_01on.gif"><a href="javascript:move(2)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image11','','/images/user/game/helpdesk/tab1_02on.gif',1)"><img src="/images/user/game/helpdesk/tab1_02.gif" name="Image11" border="0"></a><a href="javascript:move(3)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image12','','/images/user/game/helpdesk/tab1_03on.gif',1)"><img src="/images/user/game/helpdesk/tab1_03.gif" name="Image12" border="0"></a><a href="javascript:move(4)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image13','','/images/user/game/helpdesk/tab1_04on.gif',1)"><img src="/images/user/game/helpdesk/tab1_04.gif" name="Image13" border="0"></a></td>
  </tr>
</table>
<table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/apply/gbox_bg.gif">
  <tr>
    <td><img src="/images/user/game/apply/gbox_top.gif"></td>
  </tr>
  <tr>
    <td align="center" valign="top">
    <table width="690" border="0" cellspacing="0" cellpadding="0">
<% if(!v_tem_grcode.equals("N000002")) {	//게임아카데미 외 %>
        <tr>
          <td><img src="/images/user/game/apply/gp_img1.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/gp_img2.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/gp_img3.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
<% } else { %>
        <tr>
          <td><img src="/images/user/game/apply/guide_img01.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/guide_img02.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/guide_img03.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/guide_img04.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/guide_img05.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/guide_img06.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/guide_img07.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        <tr>
          <td><img src="/images/user/game/apply/guide_img08.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
        <tr>
          <td><img src="/images/user/game/apply/guide_img09.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
<% } %>
      </table>
<!--
    <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/user/game/helpdesk/st_menual1.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tbl_menualtext2" >1. 수강신청메뉴선택</td>
        </tr>
        <tr>
          <td height="2"></td>
        </tr>
        <tr>
          <td height="5" background="/images/user/game/apply/guide_linebg.gif"></td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10"></td>
        </tr>
        <tr>
          <td class="tbl_gleft">수강신청하고자 하는 강좌유형을 선택하고 원하는 강좌를 확인한 후 ①상세보기 버튼을
            클릭합니다.</td>
        </tr>
        <tr>
          <td height="5"></td>
        </tr>
<% if(v_tem_grcode.equals("N000010")) {	//교원연수 %>
        <tr>
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img_kyowon.gif"></td>
        </tr>
<% } else { %>
        <tr>
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img1.gif"></td>
        </tr>
<% } %>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tbl_menualtext2" >2. 선택한 과정의 상세정보를 확인한 후에 신청버튼를 클릭하세요.(<strong>무료과정</strong>)</td>
        </tr>
        <tr>
          <td height="2"></td>
        </tr>
        <tr>
          <td height="5" background="/images/user/game/apply/guide_linebg.gif"></td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10"></td>
        </tr>
        <tr>
          <td class="tbl_gleft"><strong>무료과정</strong>인 경우는 ②신청을 누르면 바로 과정신청확인
            질문이 나타나고 확인하면 신청이 즉시 이루어 집니다.</td>
        </tr>
        <tr>
          <td height="5"></td>
        </tr>
        <tr>
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img2.gif"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tbl_menualtext2" >3. 선택한 과정의 상세정보를 확인한 후에 신청버튼를 클릭하세요.(<strong>유료과정</strong>)</td>
        </tr>
        <tr>
          <td height="2"></td>
        </tr>
        <tr>
          <td height="5" background="/images/user/game/apply/guide_linebg.gif"></td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tbl_gleft"><strong>유료과정</strong>은 ③신청을 누르면 신청하는 과정의 상세 정보가
            나오고 하단에는 결제화면이 뜸니다. </td>
        </tr>
        <tr>
          <td height="5"></td>
        </tr>
        <tr>
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img3.gif"></td>
        </tr>
        <tr>
          <td class="tbl_gleft">④ 마일리지를 보유하고 있으면 해당한도 내에서 마일리지로 수강료를 일부 결제 하실
            수 있습니다.<br>
            ⑤원하는 결제 상태를 클릭합니다.</td>
        </tr>
        <tr>
          <td height="5"></td>
        </tr>
        <tr>
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img4.gif"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tbl_menualtext2" >4. 현금결제 클릭시 결제처리 팝업창이 뜹니다. 내용을 적고 확인을 누르면
            수강신청이 완료됩니다.</td>
        </tr>
        <tr>
          <td height="2"></td>
        </tr>
        <tr>
          <td height="5" background="/images/user/game/apply/guide_linebg.gif"></td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10"></td>
        </tr>
        <tr>
          <td class="tbl_gleft">입금자란에 입금자 성명을 쓰고 <strong>입금할 예정일</strong>을 정확하게
            씁니다.<br>
            확인을 누르면 신청이 완료되었다는 메세지가 뜨면서 수강신청이 완료됩니다. </td>
        </tr>
        <tr>
          <td height="5"></td>
        </tr>
        <tr>
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img5.gif"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
-->
      </td>
  </tr>
  <tr>
    <td valign="bottom"><img src="/images/user/game/apply/gbox_bo.gif"></td>
  </tr>
</table>




<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
