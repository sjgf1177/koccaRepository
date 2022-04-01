<%
//**********************************************************
//  1. 제      목: 수강신청 > 수강신청안내
//  2. 프로그램명: gu_ApplyIntro2.jsp
//  3. 개      요: 수강신청안내
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 06.01.23
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process		= box.getString("p_process");

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<script language="JavaScript" type="text/JavaScript">
<!--
 
 function move(tab){
	document.form1.p_process.value= "SubjectIntro";
	document.form1.p_tab.value = tab;
	document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
	document.form1.submit();
 }

//-->

</script>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_tab'>


<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="right"  background="/images/user/game/apply/<%=tem_subimgpath%>/title01.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 수강신청 > 수강신청안내</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<!-- 탭 -->
<table width="719" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="719" align="right"><a href="javascript:move(1)"><img src="/images/user/game/apply/tab_g01.gif" name="Image11" border="0"></a><img src="/images/user/game/apply/tab_g02_on.gif" width="109" height="32"></td>
  </tr>
</table>
<table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/apply/gbox_bg.gif">
  <tr>
    <td><img src="/images/user/game/apply/gbox_top.gif"></td>
  </tr>
  <tr>
    <td align="center" valign="top"><table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><img src="/images/user/game/apply/st_applyguide2.gif" width="352" height="15"></td>
        </tr>
        <tr> 
          <td height="9"></td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class="tbl_menualtext" >1. 수강신청 확인/취소 메뉴를 클릭하여 수강신청한 리스트를 확인합니다.</td>
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
          <td class="tbl_gleft"><strong>무료과정</strong>인 경우는 ①신청취소를 누르면 바로 신청취소가 
            이루어집니다.(수강신청기간내에)</td>
        </tr>
        <tr>
          <td class="tbl_gleft"><strong>유료과정</strong>인 경우라도 입금 확인중일 경우는 바로 ①신청취소를 
            하실 수 있습니다.</td>
        </tr>
        <tr> 
          <td height="5"></td>
        </tr>
        <tr> 
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img6.gif"></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class="tbl_menualtext" >2. 입금확인 되었을 경우의 신청취소<strong>(유료과정일경우)</strong></td>
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
          <td class="tbl_gleft">②수강신청취소 기간에 해당하면 ③신청취소를 하실 수 있습니다.</td>
        </tr>
        <tr> 
          <td height="5"></td>
        </tr>
        <tr> 
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img7.gif"></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class="tbl_menualtext" >3. 신청취소를 누르면 <strong>환불신청 화면이 뜹니다</strong>. 
          </td>
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
          <td class="tbl_gleft"><strong>④</strong>정보를 입력하고 확인하면 입금구분은 환불 처리중이 
            되며 관리자 승인후 환불 완료와 신청취소가 이루어집니다.</td>
        </tr>
        <tr> 
          <td height="5"></td>
        </tr>
        <tr> 
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img8.gif"></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
        </tr>
      </table>
      
    </td>
  </tr>
  <tr>
    <td valign="bottom"><img src="/images/user/game/apply/gbox_bo.gif"></td>
  </tr>
</table>

          
</form>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->