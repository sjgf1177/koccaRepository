<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: gu_SubjectPreviewOn.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String upload_url = conf.getProperty("url.upload");

    String  v_process      = box.getString("p_process");
    String  v_rprocess     = box.getString("p_rprocess");
    String  v_subj         = box.getString("p_subj");
    String  v_subjnm       = box.getString("p_subjnm");
    String  v_isonoff      = box.getString("p_isonoff");
    String  v_upperclassnm = box.getString("p_upperclassnm");
    String  v_upperclass   = box.getString("p_upperclass");
    String  v_select       = box.getString("p_select");
    String  v_actionurl    = box.getString("p_actionurl");
    String  v_lsearchsubject = box.getString("p_lsearchsubject");
    String  v_gubun       = box.getString("p_gubun");






%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--
//-->
</SCRIPT>


<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="<%=v_process%>" >

	<!-- title -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td width="720" height="35" align="right"  background="/images/user/game/mystudy/type1/tit_interest.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
		  HOME > 수강신청 > 장바구니</td>
	  </tr>
	  <tr> 
		<td height="20"></td>
	  </tr>
	</table>
	<!-- 관심과정table  -->
	<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		<form name="frm3" method="post" >
			<INPUT TYPE="hidden" NAME="p_process">
		

	  <tr  class="lcolor"> 
		<td height="3" colspan="8" class="linecolor_my"></td>
	  </tr>
		  <tr> 
			<td class="tbl_ptit2">구분</td>
			<td class="tbl_ptit">과정명</td>
			<td class="tbl_ptit2">차수</td>
			<td class="tbl_ptit">담당교수</td>
			<td class="tbl_ptit2">교육기간</td>
			<td class="tbl_ptit">수강료</td>
			<td class="tbl_ptit2">[삭제]</td>
		  </tr>
	  </tr>

	  <tr> 
		<td class="tbl_grc">과정</td>
		<td class="tbl_gleft"><a href="javascript:whenSubjInfo('000019','ALL')">게임문화론</a><br>
		</td>
		<td class="tbl_grc">003</td>
		<td class="tbl_grc">홍길동</td>
		<td class="tbl_grc">2006-07-28 ~ 2006-09-21</td>
		<td class="tbl_grc">25,000원</td>
		<td class="tbl_grc">삭제</td>
	</tr>

	<tr>
	  <td colspan=7 height=30></td>
	</tr>
	
	<tr>
	  <td colspan=5 class="tbl_grc">합계</td>
	  <td class="tbl_grc">25,000원</td>
	  <td>&nbsp;</td>
	</tr>
	<tr>
	  <td colspan=5 class="tbl_grc">할인율</td>
	  <td class="tbl_grc">10%</td>
	  <td>&nbsp;</td>
	</tr>
	<tr>
	  <td colspan=5 class="tbl_grc">결제금액</td>
	  <td class="tbl_grc">22,500원</td>
	  <td>&nbsp;</td>
	</tr>
	

	  </form>
	</table>
	
	<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		<tr>
			<td> 결제 진행하기
			</td>	
		</tr>
	</table>
	
	
        <br>
        <br>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->