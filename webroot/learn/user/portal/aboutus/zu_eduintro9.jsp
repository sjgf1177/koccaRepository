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
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_on.gif" /></a></span>
			</td>
		  </tr>
		  <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
		  <tr><td>
            <div id="tab8" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><p><img src="/images/portal/homepage_renewal/academy/cont_tit07.gif" alt="게임국가기술자격검정" /></p>
                    <p class="mgt10"><img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                    <p>게임산업발전에 따른 환경변화와 산업계의 인력수요에 부응하는 우수한 게임인력을 배출하고자 게임기획전문가, 게임그래픽전문가, 게임프로그래밍전문가 국가기술자격검정을 실시하고 있습니다.</p>
                    <br>
                    <br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle13.gif" alt="자격 검정 시행 종목" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;게임기획전문가, 게임그래픽전문가, 게임프로그래밍전문가</p>
                    <br>
                    <br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle14.gif" alt="자격 검정 시행" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;전국 7개 지역 검정장에서 연 4회(필기 2회, 실기 2회) 검정 시행</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 검정지역 : 서울, 경기, 대전, 대구, 부산, 전주, 제주<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(지역별 응시인원에 따라 변경, 취소 가능)</p>
                    <table width="100%" cellspacing="0" id="tab_form" summary="게임국가기술 자격 검정 시행 내용입니다.">
                      <thead>
                        <tr>
                          <td class="tit_table" scope="col">회차</td>
                          <td class="tit_table" scope="col">필기시험<br/>원서접수</td>
                          <td class="tit_table" scope="col">필기시험</td>
                          <td class="tit_table" scope="col">필기시험<br/>합격발표</td>
						  <td class="tit_table" scope="col">실기시험<br/>원서접수</td>
						  <td class="tit_table" scope="col">실기시험</td>
						  <td class="tit_table" scope="col">실기시험<br/>합격발표</td>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>1회</td>
                          <td>4. 16<br />
                            ~ 4. 20</td>
                          <td>5. 13</td>
                          <td>6. 4</td>
                          <td>6. 4<br />
                            ~ 6. 8</td>
                          <td>7. 1</td>
                          <td>8. 6</td>
                        </tr>
                        <tr>
                          <td>2회</td>
                          <td>8. 6<br />
                            ~ 8. 10</td>
                          <td>9. 2</td>
                          <td>9. 24</td>
                          <td>9. 24<br />
                            ~ 9. 28</td>
                          <td>10.28</td>
                          <td>11.26</td>
                        </tr>
                      </tbody>
                    </table>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle15.gif" alt="전국지역검정장" /></p>
                    <p>
                    <table width="100%" cellspacing="0" id="tab_form" summary="게임국가기술자격검정 전국 지역 검정장 내용입니다.">
                      <colgroup>
                      <col width="20%" />
                      <col width="" />
                      <col width="" />
                      </colgroup>
                      <thead>
                       	<tr>
                          <td class="tit_table" scope="col" class="first" width="14%">지역</td>
                          <td class="tit_table" scope="col">필기시험</td>
                          <td class="tit_table" scope="col">실기시험</td>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td class="first">서울</td>
                          <td>백석중학교(1회), 한양공고(1, 2회),<br/>서울디자인고교(2회)</td>
                          <td>한남직업전문학교, 호서전문학교</td>
                        </tr>
                        <tr>
                          <td class="first">경기</td>
                          <td>유한대학(부천)</td>
                          <td>유한대학(부천)</td>
                        </tr>
                        <tr>
                          <td class="first">대전</td>
                          <td>우송대학교</td>
                          <td>우송대학교</td>
                        </tr>
                        <tr>
                          <td class="first">대구</td>
                          <td>대구디지털산업진흥원</td>
                          <td>대구디지털산업진흥원</td>
                        </tr>
                        <tr>
                          <td class="first">부산</td>
                          <td>부산정보산업진흥원</td>
                          <td>부산정보산업진흥원</td>
                        </tr>
                        <tr>
                          <td class="first">전주</td>
                          <td>전북게임아카데미</td>
                          <td>전북게임아카데미</td>
                        </tr>
                        <tr>
                          <td class="first">제주</td>
                          <td>제주관광대학</td>
                          <td>제주관광대학</td>
                        </tr>
                      </tbody>
                    </table>
                    <br/>
                    <p>&nbsp;&nbsp;&nbsp;<b>※ 종목별 검정 현황(2011년 기준)</b></p>
                    <p align="right">(단위: 명)</p>
                    <table width="100%" border="0" cellpadding="3" cellspacing="0" id="tab_form" summary=" 종목별 검정 현황 " >
                      <colgroup>                      
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      <col width="7%" />
                      </colgroup>
                      <thead>
                        <tr>
                          <td class="tit_table" colspan="2" rowspan="2">구 분</td>
                          <td class="tit_table" colspan="3">게임기획</td>
                          <td class="tit_table" colspan="3">게임그래픽</td>
                          <td class="tit_table" colspan="3">게임프로그래밍</td>
                          <td class="tit_table" colspan="3">합계</td>
                        </tr>
                        <tr>
                        	<td>응시</td>
                        	<td>합격</td>
                        	<td>합격률</td>
                        	<td>응시</td>
                        	<td>합격</td>
                        	<td>합격률</td>
                        	<td>응시</td>
                        	<td>합격</td>
                        	<td>합격률</td>
                        	<td>응시</td>
                        	<td>합격</td>
                        	<td>합격률</td>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th scope="row" rowspan="2">필기</th>
                          <td>1회</td>
                          <td>477</td>
                          <td>366</td>
                          <td>76.7%</td>
                          <td>263</td>
                          <td>125</td>
                          <td>47.5%</td>
                          <td>169</td>
                          <td>51</td>
                          <td>30.2%</td>
                          <td>909</td>
                          <td>542</td>
                          <td>59.6%</td>                          
                        </tr>   
                        <tr>
                          <td>2회</td>
                          <td>167</td>
                          <td>115</td>
                          <td>68.9%</td>
                          <td>239</td>
                          <td>72</td>
                          <td>30.1%</td>
                          <td>121</td>
                          <td>19</td>
                          <td>15.7%</td>
                          <td>527</td>
                          <td>206</td>
                          <td>39.1%</td>                          
                        </tr> 
                        <tr>
                          <th scope="row" rowspan="2">실기</th>
                          <td>1회</td>
                          <td>272</td>
                          <td>76</td>
                          <td>27.9%</td>
                          <td>174</td>
                          <td>60</td>
                          <td>34.5%</td>
                          <td>58</td>
                          <td>15</td>
                          <td>25.9%</td>
                          <td>504</td>
                          <td>151</td>
                          <td>30.0%</td>                         
                        </tr> 
                        <tr>
                          <td>2회</td>
                          <td>196</td>
                          <td>58</td>
                          <td>29.6%</td>
                          <td>145</td>
                          <td>40</td>
                          <td>27.4%</td>
                          <td>32</td>
                          <td>7</td>
                          <td>21.9%</td>
                          <td>373</td>
                          <td>105</td>
                          <td>28.1%</td>                          
                        </tr>            
                      </tbody>
                    </table>
                    </p>                    
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle16.gif" alt="시험신청" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;온라인 : <a href="http://www.kgq.or.kr" target="_blank">http://www.kgq.or.kr</a></p>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle07.gif" alt="관련문의" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp; 유윤옥 과장 (02-3219-6536, <a href="mailto:inami@kocca.kr">inami@kocca.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp; 주성호 과장 (02-3219-6534, <a href="mailto:thinkju@kocca.kr">thinkju@kocca.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp; 이은정 사원 (02-3219-6542, <a href="mailto:ejred49@kocca.kr">ejred49@kocca.kr</a>)</p>
                    </td>
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
	<p><img src="/images/portal/common/h4_44.gif" alt="게임국가기술자격검정" /></p>
	<p><img src="/images/portal/common/txt_8_4.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
	<Br>
	<Br>
	<p>게임산업발전에 따른 환경변화와 산업계의 인력수요에 부응하는 우수한 게임인력을 배출하고자 게임기획전문가, 게임그래픽전문가,<br>
	게임프로그래밍전문가 기술자격검정을 실시하고 있습니다.</p>
	<br>
	<br>

	<p><img src="/images/portal/common/stit_14.gif" alt="자격 검정 시행 종목" /></p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;게임기획전문가, 게임그래픽전문가, 게임프로그래밍전문가</p>
	<br>
	<br>

	<p><img src="/images/portal/common/stit_15.gif" alt="자격 검정 시행" /></p>
		<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;전국 6개 지역 검정장에서 연 4회(필기 2회, 실기 2회) 검정 시행</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 검정지역 : 서울, 경기, 대전, 대구, 부산, 전주</p>

	<table cellspacing="0" summary="게임국가기술 자격 검정 시행 내용입니다." class="basicTable tableTh01 ">
		<caption>게임국가기술 자격 검정 시행</caption>
		<thead>
			<tr>
				<th rowspan="3" class="first" scope="col" width="6%">회차</th>
				<th scope="col" colspan="3">필 기</th>
				<th scope="col" colspan="4" width="52%">실 기</th>
			</tr>
			<tr class="depth01">
				<th rowspan="2" scope="col">접수</th>
				<th rowspan="2" scope="col">시험</th>
				<th rowspan="2" scope="col">합격<br />발표</th>
				<th colspan="2" scope="col">접 수</th>
				<th rowspan="2" scope="col">시험</th>
				<th rowspan="2" scope="col">합격<br />발표</th>
			</tr>
			<tr class="depth01">
				<th scope="col">이전 필기 <br />합격자</th>
				<th scope="col">당회 합격자</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="first">1회</td>
				<td>4. 18(월)<br />~ 4. 22(금)</td>
				<td>5.15(일)</td>
				<td> 6. 6(월)</td>
				<td>3. 7(월)<br />~ 11(금)</td>
				<td>6. 6(월)<br />~ 10(금)</td>
				<td>7.3(일)<br />~ 7.10(일)</td>
				<td>8.1(월)</td>
			</tr>
			<tr>
				<td class="first">2회</td>
				<td>8. 1(월)<br />~ 8. 5(금)</td>
				<td>8.28(일)</td>
				<td>9. 19(월)</td>
				<td>8. 8(월)<br />~ 8. 12(금)</td>
				<td>9. 19(월)<br />~ 23(금)</td>
				<td>10.16(일)<br />~ 10.23(일)</td>
				<td>11.14(월)</td>
			</tr>
		</tbody>
	</table>

	<Br>
	<Br>
	<p><img src="/images/portal/common/stit_16.gif" alt="전국지역검정장" /></p>
	<p><table cellspacing="0" summary="게임국가기술자격검정 전국 지역 검정장 내용입니다." class="basicTable tableTh01" >
		<caption>게임국가기술자격검정 전국 지역 검정장</caption>
		<thead>
			<tr>
			  	<th scope="col" class="first" width="14%">지역</th>
				<th scope="col">필기시험</th>
				<th scope="col">실기시험</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="first">서울</td>
				<td>선린인터넷고교(1,2회), 한양고등학교(1회),<br />용산공업고등학교(2회)</td>
				<td>한국IT직업전문학교, 호서전문학교</td>
			</tr>
			<tr>
				<td class="first">부산</td>
				<td>부산정보산업진흥원</td>
				<td>부산정보산업진흥원</td>
			</tr>
			<tr>
				<td class="first">대전</td>
				<td>우송대학교</td>
				<td>우송대학교</td>
			</tr>
			<tr>
				<td class="first">대구</td>
				<td>대구디지털산업진흥원</td>
				<td>대구디지털산업진흥원</td>
			</tr>
			<tr>
				<td class="first">전주</td>
				<td>전북게임아카데미</td>
				<td>전북게임아카데미</td>
			</tr>
			<tr>
				<td class="first">경기</td>
				<td>유한대학</td>
				<td>유한대학</td>
			</tr>									
		</tbody>
	</table></p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_17.gif" alt="시험신청" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;온라인 / <a href="http://www.kgq.or.kr" target="_blank">http://www.kgq.or.kr</a></p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_18.gif" alt="관련문의" /></p>
	test
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;창의인력양성팀 유윤옥 과장 (02-3219-6536, <a href="mailto:inami@kocca.kr">inami@kocca.kr</a>)</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;창의인력양성팀 주성호 과장 (02-3219-6534, <a href="mailto:thinkju@kocca.kr">thinkju@kocca.kr</a>)</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;창의인력양성팀 이은정 사원 (02-3219-6542, <a href="mailto:ejred49@kocca.kr">ejred49@kocca.kr</a>)</p>
	</td>
  </tr>
</table>
<%} %>
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


