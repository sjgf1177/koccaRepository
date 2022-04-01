<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	
	String  v_process  = box.getString("p_process");
	
	String s_userid   = box.getSession("userid");
	String s_username = box.getSession("name");
	
	HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>
	
	upperMap   = ClassifySubjectBean.getMenuId(box);
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<script language="javascript">

	function selectArea( val ) {
		document.form1.tabid.value = val;
		document.form1.p_process.value = "edu";
		document.form1.action = "/servlet/controller.homepage.HomePageAboutUsServlet";
		document.form1.submit();
	}

</script>

<form name="form1" method="post">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "7">
    <input type = "hidden" name = "menuid">
    <input type = "hidden" name = "tabid" value="<%= box.get("tabid") %>">
    
            	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	
	<table>
		<tr>
			<td>
			<table width="672" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="30" colspan="2" class="h_road">&nbsp;</td>
        </tr>
        <tr>
          <td><img src="/images/portal/homepage_renewal//academy/stitle_03.gif" alt="교육사업 안내" /></td>
          <td class="h_road">Home &gt; 아카데미 소개 &gt; <strong>교육사업 안내</strong></td>
        </tr>
        <tr>
          <td height="12" colspan="2"></td>
        </tr>
        <tr>
          <td height="1" colspan="2" bgcolor="E5E5E5"></td>
        </tr>
      </table>
			 <!--타이틀부분//-->
      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td height="15">
          		<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_01_off.gif" /></a></span>              	
           		<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_02_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_03_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_04_on.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_05_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_06_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_off.gif" /></a></span>
			</td>
		  </tr>
		  <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
		  <tr><td>
            <div id="tab4" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <p><img src="/images/portal/homepage_renewal/academy/cont_tit08.gif" alt="국제 인증과정" /></p>
                    <p class="mgt10"> <img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                <tr>
                  <td>국제자격인증센터는 미국의 Apple社, Avid社, Digidesign社, 영국 Quantel 社 등 해외 유관기관과의 파트너쉽을 구축하여 디지털 Video/Audio 분야, HD촬영 등 5개 분야의 국제 자격인증센터를 운영하며 국제 규격과 교육 체계에 맞는 과정운영을 통해 글로벌 경쟁력을 갖춘 디지털 방송전문 인력을 양성하고 있습니다.</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td><table border="0" cellpadding="3" cellspacing="0"  id="tab_form2">
                      <colgroup>
                      <col width="15%" />
                      <col width="" />
                      <col width="35%" />
                      </colgroup>
                      <thead>
                        <tr>
                          <td class="tit_table">국제인증센터</td>
                          <td class="tit_table">운영현황</td>
                          <td class="tit_table">비고</td>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td class="data" rowspan="2">Avid Authorized Training<br />
                            Partner(AATP)</td>
                          <td class="ta_l fs_s lp_1">- Avid 국제공인 교재, Instructor에 의한 국제 규격의 <br />
                            &nbsp;            영상편집 교육<br />
                            - 과정 수료 후 희망자에 한해 테스트를 거쳐 국제 <br />
                            &nbsp;            Cerificate 취득 가능<br />
                            - 운영과정: Media Composer 101, 201, 202, 203, 205, 220, 324</td>
                          <td rowspan="2" class="end"><strong>Avid(美)</strong><br />
                            Avid 공인 국제인증센터 개소(2008년)<br />
                            Pro school 개소(2003)</td>
                        </tr>
                        <tr>
                          <td class="ta_l fs_s lp_1">- Avid 제공인 교재, Instructor에 의한 국제 규격의 <br />
                            &nbsp;            오디오 교육<br />
                            - 과정 수료 테스트를 거쳐 국제 Certificate취득 가능<br />
                            - 운영과정: Pro Tools 101, 110, 201, 210M, 210P</td>
                        </tr>
                        <tr>
                          <td class="data">Apple Authorized Training<br />
                            Center(AATC)</td>
                          <td class="ta_l fs_s lp_1">- Apple 국제공인 교재, Instructor에 의한 국제 규격의 <br />
                            &nbsp;            Final Cut Pro 교육<br />
                            - 과정 수료 후 희망자에 한해 테스트를 거쳐 국제 <br />
                            &nbsp;            Certificate취득가능<br />
                            - 운영과정: Final Cut Pro LevelⅠ,Ⅱ</td>
                          <td class="end"><strong>Apple(美)</strong><br />
                            Apple공인 국제인증센터 개소(2007년)</td>
                        </tr>
                      </tbody>
                    </table>
                    <br /></td>
                </tr>
              </table>
              <br />
            </div>
            </td>
        </tr>
      </table></td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
			
			<%}else{ %>
    
<% if      ("1".equals(box.get("tabid")) || "".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>차세대인재양성교육</strong></p></h2><% }
   else if ("2".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>산업계직무재교육</strong></p></h2><% }
   else if ("3".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>온라인교육</strong></p></h2><% }
   else if ("4".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내" 	  class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>수탁교육</strong></p></h2><% }
   else if ("5".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>국제인증과정</strong></p></h2><% } 
   else if ("6".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>3D입체콘텐츠전문인력양성</strong></p></h2><% }
   else if ("7".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>기업참여형교육</strong></p></h2><% }
   else if ("8".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내" 	  class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>드라마프로듀서스쿨</strong></p></h2><% }
   else if ("9".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>게임국가기술자격검정</strong></p></h2><% } 
%>
    
    <ul class="tabwrap">
		<li><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><span>차세대인재양성교육</span></a></li>
		<li><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><span>산업계직무재교육</span></a></li>
		<li><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><span>온라인교육</span></a></li>
		<li><a href="javascript:selectArea('4');" class="tab_online<%= box.get("tabid").equals("4") ? " tab_online_on" : "" %>"><span>수탁교육</span></a></li>
		<li><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><span>국제인증과정</span></a></li>
		<li><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><span>3D입체콘텐츠전문인력양성</span></a></li>
		<li><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><span>기업참여형교육</span></a></li>
		<li><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><span>드라마프로듀서스쿨</span></a></li>
		<li><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><span>게임국가기술자격검정</span></a></li>
	</ul>
	
	<table class="row_list">
        <tbody>
          <tr></tr>
        </tbody>
        <colgroup>
        <col width="100%" />
        </colgroup>
      </table>
    
 <table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20">&nbsp;</td>
  </tr>
  <tr>
    <td>국제자격인증센터는 미국의 Apple社, Avid社, Digidesign社, 영국 Quantel 社 등 해외 유관기관과의 파트너쉽을 구축하여 디지털 Video/Audio 분야, HD촬영 등 5개 분야의 국제 자격인증센터를 운영하며 국제 규격과 교육 체계에 맞는 과정운영을 통해 글로벌 경쟁력을 갖춘 디지털 방송전문 인력을 양성하고 있습니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30">&nbsp;</td>
  </tr>
  <tr>
    <td><table class="row_list">
      <colgroup>
        <col width="23%" />
        <col width="" />
        <col width="32%" />
        </colgroup>
      <thead>
        <tr>
          <th>국제인증센터</th>
          <th>운영현황</th>
          <th>비고</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="data" rowspan="2">Avid Authorized Training<br />
            Partner(AATP)</td>
          <td class="ta_l fs_s lp_1">- Avid 국제공인 교재, Instructor에 의한 국제 규격의 <br />
&nbsp;            영상편집 교육<br />
            - 과정 수료 후 희망자에 한해 테스트를 거쳐 국제 <br />
&nbsp;            Cerificate 취득 가능<br />
            - 운영과정: Media Composer 101, 201, 202, 203, 205, 220, 324</td>
          <td rowspan="2" class="end"><strong>Avid(美)</strong><br />
            Avid 공인 국제인증센터 개소(2008년)<br />
            Pro school 개소(2003)</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">- Avid 제공인 교재, Instructor에 의한 국제 규격의 <br />
  &nbsp;            오디오 교육<br />
            - 과정 수료 테스트를 거쳐 국제 Certificate취득 가능<br />
            - 운영과정: Pro Tools 101, 110, 201, 210M, 210P</td>
          </tr>
        <tr>
          <td class="data">Apple Authorized Training<br />
            Center(AATC)</td>
          <td class="ta_l fs_s lp_1">- Apple 국제공인 교재, Instructor에 의한 국제 규격의 <br />
&nbsp;            Final Cut Pro 교육<br />
            - 과정 수료 후 희망자에 한해 테스트를 거쳐 국제 <br />
&nbsp;            Certificate취득가능<br />
            - 운영과정: Final Cut Pro LevelⅠ,Ⅱ</td>
          <td class="end"><strong>Apple(美)</strong><br />
            Apple공인 국제인증센터 개소(2007년)</td>
        </tr>
      </tbody>
    </table>      <br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<%} %>
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


