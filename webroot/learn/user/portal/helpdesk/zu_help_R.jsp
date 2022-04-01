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
    
    String tap			= "";
    
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

function changeTap(process){
    var objDl   = document.getElementsByName("objDl");
    var objDd   = document.getElementsByName("objDd");


    if(idx == '1'){
    	objDd[0].style.display  = "block";
    	objDd[1].style.display  = "none";
    }else if(idx == '2' ){
    	objDd[0].style.display  = "none";
    	objDd[1].style.display  = "block";
    }    
}  


</SCRIPT>
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
      	<% if(v_code.equals("HELP_INTERNET")){ %>
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/stitle_07.gif" alt="학습환경 도우미" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>학습환경 도우미</strong></td>
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
        <td><img src="/images/portal/homepage_renewal/support/bar_02.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/support/tab_on_001.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_on_001.gif" name="Image45" width="168" height="39" border="0" id="Image45" /></a></td>
        <td><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpMove" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/support/tab_on_002.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_002.gif" name="Image46" width="168" height="39" border="0" id="Image46" /></a></td>
        </tr>
    </table>    
    <table width="100%" border="0" cellpadding="15" cellspacing="2" bgcolor="#7692CB">
      <tr>
        <td bgcolor="f7f7f7">동영상 재생시 문제가 있으시다면, 현재 사용 중인 인터넷 회선의 연결속도를 확인해 주세요.
          <br />
          회선 속도 테스트는 하루에 20~30분 간격을 두고 반드시 여러차례 해주셔야 정확한 속도를 알 수 있습니다.</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
        </tr>
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/support/sstitle_02.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">구분</td>
        <td class="tit_table_right">최소 인터넷 회선속도</td>
      </tr>
      <tr>
        <td class="ct_tit_table">일반동영상</td>
        <td class="ct_tit_table_right">최소 300 Kbps ~ 400 Kbps</td>
      </tr>
      <tr>
        <td class="ct_tit_table">한국콘텐츠아카데미 동영상</td>
        <td class="ct_tit_table_right">최소 500 Kbps ~ 700 Kbps</td>
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
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/support/sstitle_03.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="2" colspan="2" bgcolor="#7692CB"></td>
        </tr>
      <tr>
        <td height="10" colspan="2"></td>
        </tr>
      <tr>
        <td width="20">&nbsp;</td>
        <td height="20">1. 한국정보화진흥원 사이트에 접속합니다.<a href="http://speed.nia.or.kr/" target="_blank" ><span class="btxt"> http://speed.nia.or.kr/</span></a></td>
        </tr>
      <tr>
        <td>&nbsp;</td>
        <td height="20">2. 상단 메뉴에서 품질측정테스트 → <span class="style1">인터넷 속도</span> 또는 메인에 <span class="style1">인터넷 속도</span>를 선택합니다. </td>
        </tr>
      <tr>
        <td height="10" colspan="2"></td>
        </tr>
    </table>
    <table width="90%" border="0" align="center" cellpadding="20" cellspacing="1" bgcolor="eeeeee">
      <tr>
        <td bgcolor="#FFFFFF"><div align="center"><img src="/images/portal/homepage_renewal/support/img_help1.gif" width="478" height="270" /></div></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
      <tr>
        <td width="20">&nbsp;</td>
        <td height="20">3. [품질테스트]를 선택하시면 별도의 프로그램을 설치하게 되는데, → 이때 설치를 선택하여 주세요.    </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td height="20">　“설치 안함”을 선택하시면 속도측정을 하실 수 없습니다. </td>
      </tr>
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
    </table>
    <table width="90%" border="0" align="center" cellpadding="20" cellspacing="1" bgcolor="eeeeee">
      <tr>
        <td bgcolor="#FFFFFF"><div align="center"><img src="/images/portal/homepage_renewal/support/img_help2.gif" width="408" height="152" /></div></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
      <tr>
        <td width="20">&nbsp;</td>
        <td height="20">4. 인터넷 속도 품질측정 테스트를 하기 위한 회원님의 시스템 정보를 이용하기 위해서는 약관이 동의를
    하셔야 합니다. </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td height="20">　다음 버튼을 선택해 주세요</td>
      </tr>
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
    </table>
    <table width="90%" border="0" align="center" cellpadding="20" cellspacing="1" bgcolor="eeeeee">
      <tr>
        <td bgcolor="#FFFFFF"><div align="center"><img src="/images/portal/homepage_renewal/support/img_help3.gif" width="488" height="273" /></div></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
      <tr>
        <td width="20">&nbsp;</td>
        <td height="20">5. 서비스 이용 환경에서 본인과 맞는 서비스 환경을 선택하여 주신 후 <span class="style1">테스트 시작</span> 버튼을 선택해 주세요.</td>
      </tr>
      
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
    </table>
    <table width="90%" border="0" align="center" cellpadding="20" cellspacing="1" bgcolor="eeeeee">
      <tr>
        <td bgcolor="#FFFFFF"><div align="center"><img src="/images/portal/homepage_renewal/support/img_help4.gif" width="490" height="377" /></div></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
      <tr>
        <td width="20">&nbsp;</td>
        <td height="20">6. 화면의 모든 정보가 표시되었다면, 인터넷 품질측정이 정상적으로 이루어진 것입니다.    </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td height="20">　 결과 확인 버튼을 누르시면 측정 결과에 대해서 확인하실 수 있습니다. </td>
      </tr>
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
    </table>
    <table width="90%" border="0" align="center" cellpadding="20" cellspacing="1" bgcolor="eeeeee">
      <tr>
        <td bgcolor="#FFFFFF"><div align="center"><img src="/images/portal/homepage_renewal/support/img_help5.gif" width="468" height="597" /></div></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
      <tr>
        <td width="20">&nbsp;</td>
        <td height="20">7. 인터넷 속도 품질측정 테스트 결과를 확인합니다. </td>
      </tr>
      
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
    </table>
    <table width="90%" border="0" align="center" cellpadding="20" cellspacing="1" bgcolor="eeeeee">
      <tr>
        <td bgcolor="#FFFFFF"><div align="center"><img src="/images/portal/homepage_renewal/support/img_help6.gif" width="474" height="697" /></div></td>
      </tr>
    </table>
    
    <%}else if(v_code.equals("HELP_VIDEO")){ %>
    
    	<table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/stitle_07.gif" alt="학습환경 도우미" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>학습환경 도우미</strong></td>
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
        <td><img src="/images/portal/homepage_renewal/support/bar_02.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image45','','/images/portal/homepage_renewal/support/tab_on_001.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_off_001.gif" name="Image45" width="168" height="39" border="0" id="Image45" /></a></td>
        <td><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpMove" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image46','','/images/portal/homepage_renewal/support/tab_on_002.gif',1)"><img src="/images/portal/homepage_renewal/support/tab_on_002.gif" name="Image46" width="168" height="39" border="0" id="Image46" /></a></td>
      </tr>
    </table>
    <table width="100%" border="0" cellpadding="15" cellspacing="2" bgcolor="#7692CB">
      <tr>
        <td bgcolor="f7f7f7">동영상 강좌를 들으실 때 끊김 현상이 생긴다면, 동영상 속도를 측정해 보세요.
          <br />
          인터넷 회선속도 측정과 동영상 속도 측정의 결과값은 다를 수 있으며, 동영상 속도 측정결과 속도가 지나치게 느릴 <br />
          경우
인터넷 회선속도 측정을 통해 회선 속도도 함께 체크해 주시길 바랍니다.</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2"></td>
      </tr>
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/support/sstitle_006.gif"/></td>
      </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="#7692CB"></td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="20">
      <tr>
        <td><div align="center">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20" height="25" class="stxt">ㆍ</td>
              <td class="btxt"><div align="left">300kbps</div></td>
            </tr>
          </table>
          <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
            <tr>
              <td height="200" bgcolor="#FFFFFF">
              	<div align="center">	              
	              <SCRIPT language=javascript type=text/javascript>
	              	object_embed("mms://vod.kbi.re.kr/goldclass/UCC.wmv","300","250");
	              </SCRIPT>
	            </div>
	          </td>
            </tr>
          </table>
        </div></td>
        <td><div align="center">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="20" height="25" class="stxt">ㆍ</td>
              <td class="btxt"><div align="left">500kbps</div></td>
            </tr>
          </table>
          <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="eeeeee">
            <tr>
              <td height="200" bgcolor="#FFFFFF">
              	<div align="center">				
				<SCRIPT language=javascript type=text/javascript>
				object_embed("mms://speedtest.hvod.nefficient.co.kr/speedtest/test2.wmv","300","250");
                </SCRIPT>
			  	</div>
			  </td>
            </tr>
          </table>
        </div></td>
      </tr>
    </table>
    <!--타이틀부분//-->
    <%} %>
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
      	
      	<%}else{ %>


            <h2><img src="/images/portal/studying/h2_tit5.gif" alt="학습환경도우미" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>학습환경도우미</strong></p></h2>

            <p><img src="/images/portal/studying/help_borimg.gif" alt="과정 수강을 위해서는 원할한 인터넷 접속환경이 필요합니다. 본인의 접속환경을 체크해보세요." /></p>
<!-- 콘텐츠 영역 시작 -->
<%=v_content %>
<!-- 콘텐츠 영역 종료 -->
<%} %>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->
