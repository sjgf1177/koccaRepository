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
<html>
<head>

<link rel="stylesheet" href="/css/admin_style_eudintro.css" type="text/css">
<script language="javascript">

	function selectArea( val ) {
		document.form1.tabid.value = val;
		document.form1.p_process.value = "edu";
		document.form1.action = "/servlet/controller.homepage.HomePageAboutUsServlet";
		document.form1.submit();
	}

</script>
</head>
<body>
<form name="form1" method="post" action="/" >
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
          <td class="h_road">Home &gt; 아카데미 소개  &gt; <strong>교육사업 안내</strong></td>
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
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_03_on.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_04_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_05_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_06_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_off.gif" /></a></span>
			</td>
		  </tr>
		  <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
		  <tr><td>
            <div id="tab3" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><p><img src="/images/portal/homepage_renewal/academy/cont_tit03.gif" alt="사이버콘텐츠 아카데미 운영" /></p>
                    <p class="mgt10"><img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                    <p>온라인교육과정은 방송영상/게임/문화콘텐츠 기획, 제작, 경영, 비즈니스 분야의 우수 교육 콘텐츠를 개발하여 콘텐츠산업 신규 진입자와
                      종사자들에게 열린 교육과 평생교육을 위해 운영되는 교육프로그램입니다.</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle04.gif" alt="개요" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;운영목적 : 문화콘텐츠산업 종사자의 직무능력향상과 인력양성의 저변확대</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육대상 : 콘텐츠 산업 종사자, 콘텐츠관련학과 학생 및 일반인 등</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육운영 : 136개 과정 매월 1회 개강</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle05.gif" alt="운영방향" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;콘텐츠 산업계 교육요구도 조사를 통한 현장 중심의 온라인 교육 콘텐츠개발</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;융합미디어 환경변화에 대응 가능한 온라인교육시스템 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;과정별 전문 온라인 튜터를 활용한 1:1 맞춤식 학습관리 운영</p>                           
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle06.gif" alt="운영방법" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 교육신청 선착순</p>
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 학습자 개인 PC에서 온라인 학습</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 과정운영 : 매월 1회 개강(1일)</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 운영내용 : 방송영상/게임/문화콘텐츠 기획, 제작, 경영비즈니스 분야 등 136개 과정 운영<br>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 학습기간 : 학습 1개월, 복습 3개월</p>              
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교 육 비  : 무료</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 콘텐츠 산업 분야 전문가, 학계전문가</p>
                    <Br>
                    <Br>
                    <p  id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle07.gif" alt="교육관련 문의" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;박경은 주임 (02-3219-6541, <a href="mailto:pke329@kocca.kr">pke329@kocca.kr</a>)<br />
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;조나연 대리 (02-2161-0077, <a href="mailto:edu_kocca@naver.com">edu_kocca@naver.com</a>)<br />
                      <br />
                    </p></td>
                </tr>
              </table>
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
	<td>

	<p><img src="/images/portal/common/h4_41.gif" alt="사이버콘텐츠 아카데미 운영" /></p>
	<p><img src="/images/portal/common/txt_8_6.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
	<Br>
	<Br>

	<p>온라인교육과정은 방송영상/게임/문화콘텐츠 기획, 제작, 경영, 비즈니스 분야의 우수 교육 콘텐츠를 개발하여 콘텐츠산업 신규 진입자와
	종사자들에게 열린 교육과 평생교육을 위해 운영되는 교육프로그램입니다.</p>

	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_01.gif" alt="개요" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;운영목적 : 콘텐츠산업의 국가 경쟁력 제고와 콘텐츠산업 인력양성의 저변확대</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육대상 : 콘텐츠 산업 종사자, 콘텐츠관련학과 학생, 초,중등 교원 및 일반인 등</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육운영 : 136개 과정 매월 1회 개강</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_02.gif" alt="운영방향" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;콘텐츠 산업계 교육요구도 조사를 통한 현장 중심의 온라인 교육 콘텐츠개발</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;융합미디어 환경변화에 대응 가능한 온라인교육시스템 운영</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;과정별 전문 온라인 튜터를 활용한 1:1 맞춤식 학습관리 운영</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_03.gif" alt="운영방법" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 교육신청 선착순</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 인터넷을 통한 동영상 강의 및 이론 교육 등 과정별 커리큘럼에 의한 현장 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 운영내용<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. 방송영상/게임/문화콘텐츠 기획, 제작, 경영, 비즈니스 분야 등 136여개 과정 운영<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. 국내 유일의 방송제작 및 미디어교육분야 원격교육연수원 운영 및 교원 승진학점 인정과정 운영<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(교육과학기술부 인가번호 제 04-02호)</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 콘텐츠 산업 분야 전문가, 학계전문가</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육시스템 및 장비 : 학습자별 개인 PC에서 온라인 학습</p>
	<Br>
	<Br>


	<p><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;박경은 주임 (02-3219-6541, <a href="mailto:pke329@kocca.kr">pke329@kocca.kr</a>)</p>

	</td>
  </tr>
</table>
<%} %>
</form>
</body>
</html>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


