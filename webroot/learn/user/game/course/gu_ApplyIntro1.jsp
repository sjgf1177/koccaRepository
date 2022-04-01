<%
//**********************************************************
//  1. 제      목: 수강신청 > 수강신청안내
//  2. 프로그램명: gu_ApplyIntro1.jsp
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
	String  v_tem_grcode   = box.getSession("tem_grcode");
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
	<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


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

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

 function move(tab){
	document.form1.p_process.value= "SubjectIntro";
	document.form1.p_tab.value = tab;
	document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
	document.form1.submit();
 }

 function fPopup()  {
    NewWindow=window.open('/learn/user/game/course/gu_ApplyIntro1_Pop.html','popup','width=600,height=350,toobar=no,scrollbars=auto,menubar=no,status=no ,directories=no,');
  }


//-->

</script>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_tab'>


	<TABLE border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td>
		<% if(v_tem_grcode.equals("N000010")) {	//교원연수 %>
			<img src= "/images/user/game/course/type1/kyowon/img01.gif">
		<% } else { %>
			<img src= "/images/user/game/apply/img01.gif">
		<% } %>
		</td>
		</tr>
		<% if(!v_tem_grcode.equals("N000002")) {	//게이트페이지 %>
		<tr>
		<td><img src= "/images/user/game/apply/img02_gp.gif"></td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img03_gp.gif"></td>
		</tr>
		<% } else { %>
		<tr>
		<td><img src= "/images/user/game/apply/img02.gif"></td>
		</tr>
		<tr>
		<td>
			<TABLE border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td width="544">&nbsp;</td>
				<td><a href=# onclick="fPopup();" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('/images/user/game/apply','','/images/user/game/apply/btn_.gif',1)"><img name="Image3" border="0" src="/images/user/game/apply/btn.gif"></a></td>
				<td width="28">&nbsp;</td>
				</tr>
			</table>
		</td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img03.gif"></td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img04.gif"></td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img05.gif"></td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img06.gif"></td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img07.gif"></td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img08.gif"></td>
		</tr>
		<tr>
		<td><img src= "/images/user/game/apply/img09.gif"></td>
		</tr>
		<% } %>
	</table>

<!--table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="right"  background="/images/user/game/apply/<%=tem_subimgpath%>/title01.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
      HOME > 수강신청 > 수강신청안내</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
</table>
<//-- 탭 --//>
<table width="719" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="719" align="right"><img src="/images/user/game/apply/tab_g01_on.gif"><a href="javascript:move(2)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','/images/user/game/apply/tab_g02_on.gif',1)"><img src="/images/user/game/apply/tab_g02.gif" name="Image5" border="0"></a></td>
  </tr>
</table>
<table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/apply/gbox_bg.gif">
  <tr>
    <td><img src="/images/user/game/apply/gbox_top.gif"></td>
  </tr>
  <tr>
    <td align="center" valign="top"><table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/user/game/apply/st_applyguide1.gif"></td>
        </tr>
        <tr>
          <td height="9"></td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tbl_menualtext" >1. 수강신청메뉴선택</td>
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
        <tr>
          <td align="center" valign="top"><img src="/images/user/game/apply/guide_img1.gif"></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="690" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="tbl_menualtext" >2. 선택한 과정의 상세정보를 확인한 후에 신청버튼를 클릭하세요.(<strong>무료과정</strong>)</td>
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
          <td class="tbl_menualtext" >3. 선택한 과정의 상세정보를 확인한 후에 신청버튼를 클릭하세요.(<strong>유료과정</strong>)</td>
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
          <td class="tbl_menualtext" >4. 현금결제 클릭시 결제처리 팝업창이 뜹니다. 내용을 적고 확인을 누르면
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
      </table></td>
  </tr>
  <tr>
    <td valign="bottom"><img src="/images/user/game/apply/gbox_bo.gif"></td>
  </tr>
</table-->


</form>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->