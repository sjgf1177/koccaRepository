<%
/**
 * file name : gu_JindanUserList_L.jsp
 * date      : 2005/12/14
 * programmer:	lyh
 * function  : 진단테스트 list 출력
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
	DataBox dbox = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);    
	box.put("leftmenu","04"); 

    ArrayList list = (ArrayList)request.getAttribute("JindanHistoryCheck");

	//응시여부체크부분
	String G01001001 = "N";		//게임디자인 초급
	String G01001002 = "N";
	String G01001003 = "N";
	String G01002001 = "N";		//게임프로그래밍
	String G01002002 = "N";
	String G01002003 = "N";
	String G01003001 = "N";		//게임그래픽
	String G01003002 = "N";
	String G01003003 = "N";
	String G01004 = "N";		//게임경영
	String G01005 = "N";			//게임창작
	String G01006 = "N";			//게임사운드
	String ClassInfo = "N";

	if(list.size() != 0 ){

		for(int i=0; i<list.size(); i++){
			dbox = (DataBox)list.get(i);

				ClassInfo = 
					dbox.getString("d_upperclass").trim()+
					dbox.getString("d_middleclass").trim()+
					dbox.getString("d_lowerclass").trim();

				if(ClassInfo.equals("G01001001")){
						G01001001 = "Y";
				}else if(ClassInfo.equals("G01001002")){
						G01001002 = "Y";
				}else if(ClassInfo.equals("G01001003")){
						G01001003 = "Y";
				}else if(ClassInfo.equals("G01002001")){
						G01002001 = "Y";
				}else if(ClassInfo.equals("G01002002")){
						G01002002 = "Y";
				}else if(ClassInfo.equals("G01002003")){
						G01002003 = "Y";
				}else if(ClassInfo.equals("G01003001")){
						G01003001 = "Y";
				}else if(ClassInfo.equals("G01003002")){
						G01003002 = "Y";
				}else if(ClassInfo.equals("G01003003")){
						G01003003 = "Y";
				}else if(ClassInfo.equals("G01004")){
						G01004 = "Y";
				}else if(ClassInfo.equals("G01005")){
						G01005 = "Y";
				}else if(ClassInfo.equals("G01006")){
						G01006 = "Y";
				}

		}

	}

    
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
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


	//진단문제평가
function testGo(classname, class1, class2, class3, chk){

	if(chk == "Y"){
			var ok = confirm( classname+"은(는) 이미 응시하신 내역이 있습니다. \n재응시할경우 기존의 데이타는 없어집니다. \n다시 응시하시겠습니까?"); 
	}else{
			var ok = confirm( classname+"의 진단테스트를 시작하시겠습니까?"); 
	}

	if(ok){
		window.self.name = "winListPage";
		window.open("", "selfTest", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 720, height = 680, top=0, left=0");
		document.frm1.class1.value = class1;
		document.frm1.class2.value = class2;
		document.frm1.class3.value = class3;
		document.frm1.target = "selfTest"
		document.frm1.p_process.value = "JindanUserPaperListPage";
		document.frm1.action = "/servlet/controller.jindan.JindanUserServlet";
		document.frm1.submit();

	}else{
		return;
	}
	
}
//-->
</SCRIPT>
<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="right"  background="/images/user/game/apply/<%=tem_subimgpath%>/tit_selftest.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 수강신청 > Self 진단</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>

<!-- 공사중 시작 -->
<!--table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="20"></td>
  </tr>
  <tr>
     <td align="center"><img src="/images/user/game/apply/jindan_ready.gif" border="0"></td>
  </tr>
</table-->
<!-- 공사중 끝 -->

<!-- tab -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
	<form name="frm1"  method="post"> 
		<input type="hidden" name="p_process">
		<input type="hidden" name="class1">
		<input type="hidden" name="class2">
		<input type="hidden" name="class3">
  <tr> 
    <td width="102"><img src="/images/user/game/apply/tab_self_on.gif"></td>
    <td width="378" valign="top"><a href="/servlet/controller.jindan.JindanUserServlet?p_process=JindanHistoryList" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image21','','/images/user/game/apply/tab_selfmy_on.gif',1)"><img src="/images/user/game/apply/tab_selfmy.gif" name="Image21" border="0"></a></td>
    <td width="240">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="3"><img src="/images/user/game/apply/self_line.gif"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/user/game/apply/self_text.gif" ></td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr > 
    <td height="1" colspan="2" background="/images/user/game/apply/dotline.gif"></td>
  </tr>
  <tr> 
    <td width="362">
	<% //게임디자인테스트 %>
	<table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="129"><img src="/images/user/game/apply/self_img1.gif"></td>
          <td width="166" valign="top"><table width="164" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="3">&nbsp;</td>
              </tr>
              <tr> 
                <td width="24"> <input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임디자인 초급','G01','001','001','<%=G01001001%>');"> 
                </td>
                <td width="24">초급</td>
                <td width="116" align="right"><a href="javascript:testGo('게임디자인 초급','G01','001','001','<%=G01001001%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image9" border="0"></a></td>
              </tr>
              <tr> 
                <td height="5" colspan="3"></td>
              </tr>
              <tr> 
                <td><input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임디자인 중급','G01','001','002','<%=G01001002%>')"></td>
                <td>중급</td>
                <td align="right"><a href="javascript:testGo('게임디자인 중급','G01','001','002','<%=G01001002%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image10" border="0"></a></td>
              </tr>
              <tr> 
                <td height="5" colspan="3"></td>
              </tr>
              <tr> 
                <td><input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임디자인 고급','G01','001','003','<%=G01001003%>')"></td>
                <td>고급</td>
                <td align="right"><a href="javascript:testGo('게임디자인 고급','G01','001','003','<%=G01001003%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image11','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image11" border="0"></a></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="25" colspan="2"></td>
        </tr>
      </table></td>
    <td width="358" valign="top">
	<%// 게임프로그래밍테스트 %>
	<table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="129"><img src="/images/user/game/apply/self_img2.gif" width="113" height="126"></td>
          <td width="166" valign="top"><table width="164" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="3">&nbsp;</td>
              </tr>
              <tr> 
                <td width="24"> <input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임프로그래밍 초급','G01','002','001','<%=G01002001%>')"> 
                </td>
                <td width="24">초급</td>
                <td width="116" align="right"><a href="javascript:testGo('게임프로그래밍 초급','G01','002','001','<%=G01002001%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image91','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image91" border="0" id="Image91"></a></td>
              </tr>
              <tr> 
                <td height="5" colspan="3"></td>
              </tr>
              <tr> 
                <td><input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임프로그래밍 중급','G01','002','002','<%=G01002002%>')"></td>
                <td>중급</td>
                <td align="right"><a href="javascript:testGo('게임프로그래밍 중급','G01','002','002','<%=G01002002%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image101" border="0" id="Image101"></a></td>
              </tr>
              <tr> 
                <td height="5" colspan="3"></td>
              </tr>
              <tr> 
                <td><input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임프로그래밍 고급','G01','002','003','<%=G01002003%>')"></td>
                <td>고급</td>
                <td align="right"><a href="javascript:testGo('게임프로그래밍 고급','G01','002','003','<%=G01002003%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image111','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image111" border="0" id="Image111"></a></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="25" colspan="2"></td>
        </tr>
      </table></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr > 
    <td height="1" colspan="2" background="/images/user/game/apply/dotline.gif"></td>
  </tr>
  <tr> 
    <td width="362">
	<%// 게임그래픽테스트 %>
	<table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="129"><img src="/images/user/game/apply/self_img3.gif" width="113" height="126"></td>
          <td width="166" valign="top"><table width="164" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="3">&nbsp;</td>
              </tr>
              <tr> 
                <td width="24"> <input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임그래픽 초급','G01','003','001','<%=G01003001%>')"> 
                </td>
                <td width="24">초급</td>
                <td width="116" align="right"><a href="javascript:testGo('게임그래픽 초급','G01','003','001','<%=G01003001%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image92','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image92" border="0" id="Image92"></a></td>
              </tr>
              <tr> 
                <td height="5" colspan="3"></td>
              </tr>
              <tr> 
                <td><input type="radio" name="radiobutton" value="radiobutton" onClick="testGo('게임그래픽 중급','G01','003','002','<%=G01003002%>')"></td>
                <td>중급</td>
                <td align="right"><a href="javascript:testGo('게임그래픽 중급','G01','003','002','<%=G01003002%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image102','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image102" border="0" id="Image102"></a></td>
              </tr>
              <tr> 
                <td height="5" colspan="3"></td>
              </tr>
              <tr> 
                <td><input type="radio" name="radiobutton" value="radiobutton" onClick="javascript:testGo('게임그래픽 고급','G01','003','003','<%=G01003003%>')"></td>
                <td>고급</td>
                <td align="right"><a href="javascript:testGo('게임그래픽 고급','G01','003','003','<%=G01003003%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image112','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image112" border="0" id="Image112"></a></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="25" colspan="2"></td>
        </tr>
      </table></td>
    <td width="358" valign="top">
	<%// 게임경영테스트 %>
	<table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="129"><img src="/images/user/game/apply/self_img5.gif" width="113" height="126"></td>
          <td width="166" valign="middle"><table width="165" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="165">&nbsp;</td>
              </tr>
              <tr> 
                <td><a href="javascript:testGo('게임경영','G01','004','','<%=G01004%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image911','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image911" border="0" id="Image91"></a></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="25" colspan="2"></td>
        </tr>
      </table></td>
  </tr>
</table>


<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr > 
    <td height="1" colspan="2" background="/images/user/game/apply/dotline.gif"></td>
  </tr>
  <tr> 
    <td width="362">
	<%// 게임창작테스트 %>
	<table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="129"><img src="/images/user/game/apply/self_img6.gif" width="113" height="126"></td>
          <td width="166" valign="middle"><table width="164" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td><a href="javascript:testGo('게임창작','G01','005','','<%=G01005%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image91111','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image91111" border="0" id="Image91"></a></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="25" colspan="2"></td>
        </tr>
      </table></td>
    <td width="358" valign="top">
	<%// 게임사운드테스트 %>
	<table width="295" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="129"><img src="/images/user/game/apply/self_img7.gif" width="113" height="126"></td>
          <td width="166" valign="middle"><table width="164" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td><a href="javascript:testGo('게임사운드','G01','006','','<%=G01006%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9111','','/images/user/game/button/btn_jindan_on.gif',1)"><img src="/images/user/game/button/btn_jindan.gif" name="Image9111" border="0" id="Image91"></a></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="25" colspan="2"></td>
        </tr>
      </table></td>
  </tr>
</table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->