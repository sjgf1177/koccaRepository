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
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_03_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_04_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_05_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_06_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_on.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_off.gif" /></a></span>
			</td>
		  </tr>
		  <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
		  <tr><td>
            <div id="tab7" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><p><img src="/images/portal/homepage_renewal/academy/cont_tit06.gif" alt="드라마프로듀서스쿨" /></p>
                    <p class="mgt10"><img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                    <p> 드라마프로듀서스쿨은 콘텐츠산업의 핵심장르인 드라마의 글로벌 경쟁력 강화를 위한 창의적 드라마 프로듀서 양성을 위해 드라마제작사협회와 협력하여 운영하는 교육프로그램입니다.</p>
                    <br>
                    <br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle04.gif" alt="개요" /></p>                    
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;운영목적 : 드라마의 글로벌 경쟁력 강화를 위한 창의적 드라마  프로듀서 양성</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육대상 : 드라마프로듀서 분야에 재능과 관심 있는 자, 관련학과 졸업(예정)자</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육운영 : 드라마프로듀서과정 2개 반 운영(각반 20명)</p>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle05.gif" alt="운영방향" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;글로벌 시장 경쟁력 강화와 방송영상산업계 요구를 반영한 창의적 드라마전문 프로듀서 양성, 공급</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;드라마콘텐츠 기획, 제작관리, 마케팅 등 드라마제작 환경 변화에 대응 가능한  글로벌 역량을 갖춘 드라마프로듀서<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;예비인력 양성</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;드라마분야별 전문성을 고려하여 실무위주의 강사배치 및 단계별 전임강사 편성</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육생 취업지원을 위한 드라마제작사 네트워크 운영</p>
                    <br>
                    <br>                    
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle06.gif" alt="운영방법" /></p>                    
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청(<a href="www.dramapd.kr">www.dramapd.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 서류심사 및 면접심사를 통해 과정별 자격기준에 적합한 교육생 선발</p>
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 현장 실무전문가, 학계 전문가 등</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국드라마프로듀서스쿨 강의실(강서구 등촌동 KGIT 4층)</p>
                    <Br>
                    <Br>
                    <p  id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle07.gif" alt="교육관련 문의" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;담당자 : 김희숙 과장(02-2161-0072, <a href="mailto:hskim@kocca.kr">hskim@kocca.kr</a>)<br />
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
      
 <link rel="stylesheet" href="/css/20110727.css" type="text/css">   
 <table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td>
	<p><img src="/images/portal/common/h4_43.gif" alt="드라마프로듀서스쿨" /></p>
	<p><img src="/images/portal/common/txt_8_3.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_12.gif" alt="사업목적" /></p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;콘텐츠산업의 핵심장르인 드라마의 글로벌 경쟁력 강화를 위한 창의적 드라마 프로듀서 양성</p>
	<br>
	<br>

	<p><img src="/images/portal/common/stit_13.gif" alt="사업개요" /></p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육대상 : 드라마프로듀서 분야에 재능과 관심 있는 자, 관련학과 졸업(예정)자</p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육운영 : 10개월/장기교육 운영</p>

	<table cellspacing="0" summary="드라마 프로듀서 스쿨 교육운영 내용 입니다." class="basicTable tableTh01">
		<caption>드라마 프로듀서 스쿨 교육운영</caption>
		<thead>
			<tr>
				<th class="first" scope="col" width="30%">과정명</th>
				<th scope="col" >교육일정</th>
				<th scope="col">교육기간</th>
				<th scope="col">교육인원</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th scope="row"class="first">기획프로듀서 양성과정</th>
				<td>'11.03~'11.12</td>
				<td>10개월</td>
				<td>20명</td>
			</tr>
			<tr>
				<th scope="row" class="first">제작프로듀서 양성과정</th>
				<td>'11.03~'11.12</td>
				<td>10개월</td>
				<td>20명</td>
			</tr>
		</tbody>
	</table> 

	<Br>
	<Br>
	<p><img src="/images/portal/common/stit_02.gif" alt="운영방향" /></p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;기획프로듀서와 제작프로듀서로 분리, 현장중심의 맞춤형 인재양성을 목표로 운영</p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;현장 실무 중심의 강의, 제작실습, 인턴쉽 프로그램 운영</p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;드라마제작사협회 회원사측의 네트웍를 활용, 제작현장 프로듀서들의 멘토쉽과 노하우 습득</p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;풍부한 현장 경험과 강의 경험을 보유한 국내ㆍ외 전문 강사진을 활용 교육 서비스 제공</p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;국내ㆍ외 유관기관과 네트웍 구축을 통한 고품위 교육서비스 제공</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_03.gif" alt="운영방법" /></p>
	&nbsp;
		<img src="/images/portal/common/20110727add/b_01_process_01_1.gif" alt="협업교육요구조사" />
		<img src="/images/portal/common/20110727add/b_01_process_01_2.gif" alt="교육과정개발" />
		<img src="/images/portal/common/20110727add/b_01_process_01_3.gif" alt="교육신청/선발(1~2월)" />
		<img src="/images/portal/common/20110727add/b_01_process_01_4.gif" alt="교육과정(3~11월)" />
		<img src="/images/portal/common/20110727add/b_01_process_01_5.gif" alt="최종교육평가(12월)" />
		<img src="/images/portal/common/20110727add/b_01_process_01_6.gif" alt="취업 지원" />
	<p>&nbsp;&nbsp;&nbsp;※ 교육과정별 운영방법과 일정은 일부 차이가 있음</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청(<a href="www.dramapd.kr">www.dramapd.kr</a>)</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 서류심사 및 면접심사를 통해 과정별 자격기준에 적합한 교육생 선발</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 현장 실무전문가, 학계 전문가 등</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국드라마프로듀서스쿨 강의실(마포구 상암동 KGIT 7층)</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;담당자 : 제작인력양성팀 김희숙(02-2161-0072, <a href="mailto:hskim@kocca.kr">hskim@kocca.kr</a>)</p>
	</td>
  </tr>
</table>

<%} %>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


