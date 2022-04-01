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

    String  v_process     = box.getString("p_process");
    String  v_tem_grcode   = box.getSession("tem_grcode");
    
    String v_content    = "";
    String v_code       = "";
    String v_title      = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_content    = dbox.getString("d_content");
        v_code       = dbox.getString("d_code");
        v_title      = dbox.getString("d_title");
     }
    
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->

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
	document.form1.p_code.value = tab;
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
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_process"   value = "">
		<input type = "hidden" name = "p_tab"		value = "">
		<input type = "hidden" name = "p_code"      value = "">
		
		<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	<table>   
      <tr>
      	<td>
      	
      	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/stitle_06.gif" alt="S/W다운로드" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>S/W다운로드</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/bar_01.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="2" colspan="3" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="10" cellspacing="1" bgcolor="eeeeee">
              <tr>
                <td bgcolor="#FFFFFF"><img src="/images/portal/homepage_renewal/support/thumb_sw_flash.jpg" width="99" height="99" /></td>
              </tr>
            </table>
        </div></td>
        <td width="68%">
            <table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
              <tr>
                <td width="4%" height="30"><strong class="org">ㆍ</strong></td>
                <td height="30"><div align="left" class="btxt">Adobe Flash CS5.5 (무료 시험버전)</div></td>
              </tr>
              <tr>
                <td height="1" colspan="2" bgcolor="eeeeee"></td>
              </tr>
              <tr>
                <td><strong class="org">ㆍ</strong></td>
                <td width="96%" height="25">파일을 다운받으셔서 setup 시키시고 사용하시면 무료 시험 버전을 사용하실 </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td height="23" valign="top">수 있습니다.</td>
              </tr>
              <tr>
                <td height="1" colspan="2" bgcolor="eeeeee"></td>
              </tr>
              <tr>
                <td height="30">&nbsp;</td>
                <td height="30" class="h_road"><a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=flash&loc=ko" target=_blank><img src="/images/portal/homepage_renewal/support/btn_down.jpg" /></a></td>
                </tr>
              <tr>
                <td height="1" colspan="2" bgcolor="eeeeee"></td>
              </tr>
            </table>
        </td>
        <td width="0%"><div align="center"></div></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="3" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="10" cellspacing="1" bgcolor="eeeeee">
              <tr>
                <td bgcolor="#FFFFFF"><img src="/images/portal/homepage_renewal/support/thumb_sw_fView.gif" width="99" height="99" /></td>
              </tr>
            </table>
        </div></td>
        <td width="68%"><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="4%" height="30"><strong class="org">ㆍ</strong></td>
              <td height="30" class="btxt">Flash View </td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td><strong class="org">ㆍ</strong></td>
              <td width="96%" height="25">당사에서 준비한 대부분 과정은 플래쉬를 포함하고 있는 경우가 대부분입니다. </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="23" valign="top">설치가 안되는 수강생분들게서는 미리 설치하시기 바랍니다.</td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30">&nbsp;</td>
              <td height="30" class="h_road"><a href="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" target=_blank><img src="/images/portal/homepage_renewal/support/btn_down.jpg" /></a></td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
        </table></td>
        <td width="0%"><div align="center"></div></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="3" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="10" cellspacing="1" bgcolor="eeeeee">
              <tr>
                <td bgcolor="#FFFFFF"><img src="/images/portal/homepage_renewal/support/thumb_sw_shockwave.gif" width="99" height="99" /></td>
              </tr>
            </table>
        </div></td>
        <td width="68%"><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="4%" height="30"><strong class="org">ㆍ</strong></td>
              <td height="30" class="btxt">Macromedia ShockWave Player </td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td><strong class="org">ㆍ</strong></td>
              <td width="96%" height="25">ShockWave는 홈페이지에 사용된 플래쉬가 보이지 않을 경우 플래쉬를 보실 </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="23" valign="top">수있도록 해주는 프로그램입니다</td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30">&nbsp;</td>
              <td height="30" class="h_road"><a href="http://www.macromedia.com/shockwave/download/download.cgi?Lang=Korean&amp;P5_Language=Korea" target=_blank><img src="/images/portal/homepage_renewal/support/btn_down.jpg" /></a></td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
        </table></td>
        <td width="0%"><div align="center"></div></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="3" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="10" cellspacing="1" bgcolor="eeeeee">
              <tr>
                <td bgcolor="#FFFFFF"><img src="/images/portal/homepage_renewal/support/thumb_sw_real.gif" width="99" height="99" /></td>
              </tr>
            </table>
        </div></td>
        <td width="68%"><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="4%" height="30"><strong class="org">ㆍ</strong></td>
              <td height="30" class="btxt">Real Player 한글판</td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td><strong class="org">ㆍ</strong></td>
              <td width="96%" height="25">ra, rm 등으로 이루어진 강의를 볼 수 있는 프로그램입니다. </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="23">설치하시면 부가적인 기능도 활용할 수 있습니다.  </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="23" valign="top">최신버전 및 플러그 인은www.real.com 으로 가시면 이용하실 수 있습니다</td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30">&nbsp;</td>
              <td height="30" class="h_road"><a href="http://www.real.com" target=_blank><img src="/images/portal/homepage_renewal/support/btn_down.jpg" /></a></td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
        </table></td>
        <td width="0%"><div align="center"></div></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="3" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="32%"><div align="center">
            <table border="0" cellpadding="10" cellspacing="1" bgcolor="eeeeee">
              <tr>
                <td bgcolor="#FFFFFF"><img src="/images/portal/homepage_renewal/support/thumb_sw_java.gif" width="99" height="99" /></td>
              </tr>
            </table>
        </div></td>
        <td width="68%"><table width="100%" border="0" align="right" cellpadding="0" cellspacing="0">
            <tr>
              <td width="4%" height="30"><strong class="org">ㆍ</strong></td>
              <td height="30" class="btxt">자바 가상머신(VM) </td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td><strong class="org">ㆍ</strong></td>
              <td width="96%" height="25">XP 초기버전 사용자는 자바 가상머신이 XP에 설치되어 있지 않습니다. </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td height="23" valign="top">학습 또는 사이트 운영을 하시기 위해서는 이 소프트웨어가 필요합니다.</td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
            <tr>
              <td height="30">&nbsp;</td>
              <td height="30" class="h_road"><a href="http://java.com/ko/download/windows_automatic.jsp" target=_blank><img src="/images/portal/homepage_renewal/support/btn_down.jpg" /></a></td>
            </tr>
            <tr>
              <td height="1" colspan="2" bgcolor="eeeeee"></td>
            </tr>
        </table></td>
        <td width="0%"><div align="center"></div></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <!--타이틀부분//-->
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
      	<%}else{ %>


            <h2><img src="/images/portal/studying/h2_tit4.gif" alt="S/W다운로드" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>S/W다운로드</strong></p></h2>

            <p><img src="/images/portal/studying/sw_borimg.gif" alt="콘텐츠진흥원 사이트 이용시 필요한 Software입니다. 필요시 다운받아서 설치하여 이용하시기 바랍니다." /></p>
            
<!-- 콘텐츠 영역 시작 -->
<%=v_content %>
<!-- 콘텐츠 영역 종료 -->

		<%} %>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->