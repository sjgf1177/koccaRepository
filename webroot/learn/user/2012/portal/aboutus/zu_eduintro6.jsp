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
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_03_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_04_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_05_on.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_06_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_off.gif" /></a></span>
			</td>
		  </tr>
		  <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
		  <tr><td>
            <div id="tab5" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><p><img src="/images/portal/homepage_renewal/academy/cont_tit04.gif" alt="3D 입체콘텐츠 전문인력양성" /></p>
                    <p class="mgt10"> <img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                    <p>3D 입체콘텐츠 전문인력양성 교육은 3D 입체콘텐츠 선진 제작기술의 조기 습득을 통해 해외 시장 진출을 선도할 리더급 제작 핵심인재를 양성하기 위한 교육프로그램입니다.</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle04.gif" alt="개요" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;운영목적 : 3D 입체영상 제작 분야별 전문 인력 양성을 통한 국내 3D 콘텐츠 산업의 조기 정착 유도</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육대상</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- S3D 프로그램 제작과정 : 콘텐츠 산업 종사자</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- S3D 입체 제작기술 인재양성 : 콘텐츠 산업 종사자</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- S3D 엘리트 인재양성과정 : 예비인력(졸업예정자, 미취업자, 프리랜서 등)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- S3D 프로그램 제작과정 : 3D입체작품‘맞춤형’제작 교육 및 실습</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- S3D 입체 제작기술 인재양성 : 3D기획/촬영/편집/그래픽/스테레오그래퍼</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- S3D 엘리트 인재양성과정 : 팀 베이스의 프로젝트 수행을 통한 현장 제작 실습과 일자리 창출을 위한 Job-matching</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육과정</p>
                    <table cellspacing="0" id="tab_form" summary="3D 입체콘텐츠 전문인력양성 교육과정 및 기간 상세 내역입니다.">
                      <colgroup>
                      <col width="13%" />
                      <col width="" />
                      <col width="16%" />
                      <col width="10%"/>
                      <col width="10%" />
                      </colgroup>
                      <thead>
                        <tr>
                          <td class="tit_table">분야</td>
                          <td class="tit_table" width="30%">과정명</td>
                          <td class="tit_table">일자</td>
                          <td class="tit_table">기간</td>
                          <td class="tit_table">인원</td>
                        </tr>
                      </thead>
                      <tfoot>
                        <tr>
                          <td colspan="4">합 계</td>
                          <td>1,025명</td>
                        </tr>
                      </tfoot>
                      <tbody>
                      	<tr>
                          <td rowspan="1" bgcolor="#f6f6f6">S3D 프로그램<br/>
								제작과정<br />
								(10개 과정)</td>
                          <td>S3D 프로그램 제작과정</td>
                          <td>12년 03월 ~ 13년 02월</td>
                          <td>-</td>
                          <td>100명</td>
                        </tr>
                        
                        <tr>
                          <td bgcolor="#f6f6f6" scope="row" rowspan="38">3D 입체<br/>
                          		제작기술<br/>
                            	인재양성 과정<br/>
                            	(38과정)</td>
                          <td>3ALITY  리그 테크니션 과정 1차</td>
                          <td>04.04  ~ 04.06</td>
                          <td>3일</td>
                          <td>10명</td>
                        </tr>
                      <tr>
                      	<td>Smoke를 활용한 3D입체 제작 과정 1차</td>
                        <td>04.16  ~ 04.20</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Avid를 활용한 3D입체 제작 과정 1차</td>
                        <td>04.23  ~ 04.27</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>스테레오그래퍼 과정 1차</td>
                        <td>04.23  ~ 04.27</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 특수촬영 과정</td>
                        <td>05.14  ~ 05.18</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Avid를 활용한 3D입체 제작 과정 2차</td>
                        <td>05.21  ~ 05.25</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>P+S Technik 리그 테크니션 과정 1차</td>
                        <td>05.21  ~ 05.25</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 Quasar 리그 테크니션 1차</td>
                        <td>05.29  ~ 05.31</td>
                        <td>3일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Cinema4D를 활용한 3D입체 제작 과정 1차</td>
                        <td>06.11  ~ 06.15</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 수중촬영 과정</td>
                        <td>06.18  ~ 06.22</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Avid를 활용한 3D입체 제작 과정 3차</td>
                        <td>06.25  ~ 06.29</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 교수워크숍</td>
                        <td>06.28  ~ 07.06</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3ALITY 리그 테크니션 과정 2차</td>
                        <td>07.04  ~ 07.06</td>
                        <td>3일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Smoke를 활용한 3D입체 제작 과정 2차</td>
                        <td>07.09  ~ 07.13</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 Quasar 리그 테크니션 2차</td>
                        <td>07.16  ~ 07.20</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Nuke를 활용한 3D입체 컴포지팅 과정 1차</td>
                        <td>07.16  ~ 07.20</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Avid를 활용한 3D입체 제작 과정 4차</td>
                        <td>07.23  ~ 07.27</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Nuke를 활용한 3D입체 컴포지팅 과정 2차</td>
                        <td>07.30  ~ 08.03</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>DSLR을 활용한 3D입체 촬영 과정</td>
                        <td>08.06  ~ 08.10</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Avid를 활용한 3D입체 제작 과정 5차</td>
                        <td>08.20  ~ 08.24</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>After Effect를 활용한 3D입체 모션그래픽 1차</td>
                        <td>08.27  ~ 08.31</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Nuke를 활용한 3D입체 컴포지팅 과정 3차</td>
                        <td>09.03  ~ 09.07</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>스테레오그래퍼 과정 2차</td>
                        <td>09.10  ~ 09.14</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>스테디캠 촬영 과정</td>
                        <td>09.17  ~ 09.21</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 드라마 제작 과정</td>
                        <td>09.24  ~ 09.28</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 영화 제작 과정 3차</td>
                        <td>09.26  ~ 09.28</td>
                        <td>3일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 다큐멘터리 제작 과정</td>
                        <td>10.08  ~ 10.12</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Nuke를 활용한 3D입체 컴포지팅 과정 4차</td>
                        <td>10.08  ~ 10.12</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>해외 워크샵 - 뉴질랜드 WETA(반지의제왕3D)</td>
                        <td>10.07  ~ 10.14</td>
                        <td>8일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Smoke를 활용한 3D입체 제작 과정 3차</td>
                        <td>10.15  ~ 10.19</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 항공 촬영 과정</td>
                        <td>10.22  ~ 10.26</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>P+S Technik리그 테크니션 2차</td>
                        <td>10.29  ~ 10.31</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>After Effect를 활용한 3D입체 모션그래픽 2차</td>
                        <td>10.29  ~ 10.31</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>3D입체 촬영 과정</td>
                        <td>11.07  ~ 11.9</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Nuke를 활용한 3D입체 컴포지팅 과정 5차</td>
                        <td>11.12  ~ 11.16</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Avid를 활용한 3D입체 제작 과정 6차</td>
                        <td>11.19  ~ 11.23</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Frame Forge를 활용한 3D입체 제작 과정</td>
                        <td>12.03  ~ 12.07</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>Avid를 활용한 3D입체 제작 과정 7차</td>
                        <td>12.17  ~ 12.21</td>
                        <td>5일</td>
                        <td>10명</td>
                      </tr>                      
                      
                      <tr>
                        <th scope="row" rowspan="3">S3D 엘리트<br />
                          	인재양성 과정<br />
                          	(3개 과정)</th>
                        <td>S3D Pre-Production 과정</td>
                        <td>03월 ~ 12월</td>
                        <td>10개월</td>
                        <td>10명</td>
                      </tr>
                      <tr>
                        <td>S3D Production 과정</td>
                        <td>03월 ~ 12월</td>
                        <td>10개월</td>
                        <td>15명</td>
                      </tr> 
                      <tr>
                        <td>S3D Post-Production 과정</td>
                        <td>03월 ~ 12월</td>
                        <td>10개월</td>
                        <td>20명</td>
                      </tr>
                      
                      <tr>
                        <th scope="row" rowspan="3">해외초청 워크숍<br />
                          	(3회)</th>
                        <td>서울국제3D페어입체가이드라인세미나/비즈매칭</td>
                        <td>05.15  ~ 05.18</td>
                        <td>4일</td>
                        <td>100명</td>
                      </tr>
                      <tr>
                        <td>VFX슈퍼바이저초청워크숍</td>
                        <td>09.10  ~ 09.14</td>
                        <td>5일</td>
                        <td>100명</td>
                      </tr> 
                      <tr>
                        <td>3D DIRECTOR워크숍</td>
                        <td>11.05  ~ 11.09</td>
                        <td>5일</td>
                        <td>100명</td>
                      </tr>
                      
                      <tr>
                        <th scope="row" rowspan="1">지역연계 교육과정<br />
                          	(10회)</th>
                        <td>지역 대학 및 진흥원 연계 교육(10회)</td>
                        <td>12년 07월 ~ 12년 09월<br/>
							12년 12월 ~ 13년 02월</td>
                        <td>-</td>
                        <td>200명</td>
                      </tr>
                      </tbody>
                      
                    </table>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle05.gif" alt="운영방향" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;3D 입체영상 제작 분야별 단기 실무 실습 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;국내 3D 프로젝트 중심으로 ‘맞춤형’컨설팅 제작 교육진행</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;풍부한 현장 경험과 강의 경험을 보유한 국내·외 전문 강사진을 활용 교육 서비스 제공</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle06.gif" alt="운영방법" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 중기교육과정은 수시로 맞춤형 교육으로 추후 별도 신청/선발기준 수립</p>
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습 교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 국 내외 현장실무전문가, 학계전문가 등</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</p>
                    <Br>
                    <Br>
                    <p  id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle07.gif" alt="교육관련 문의" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;주봉현 차장 (02-3219-6528, <a href="mailto:bhjoo@kocca.kr">bhjoo@kocca.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;임규복 과장 (02-3219-6530, <a href="mailto:bok@kocca.kr">bok@kocca.kr</a>)<br />
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;정성민 주임 (02-3219-6531, <a href="mailto:shurike@kocca.kr">shurike@kocca.kr</a>)<br />
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
		<p><img src="/images/portal/common/h4_40.gif" alt="3D 입체콘텐츠 전문인력양성" /></p>
	<p><img src="/images/portal/common/txt_8.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
	<Br>
	<Br>

	<p>3D 입체콘텐츠 전문인력양성 교육은 3D 입체콘텐츠 선진 제작기술의 조기 습득을 통해 해외 시장 진출을 선도할 리더급 제작<br />
	 핵심인재를 양성하기 위한 교육프로그램입니다.</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_01.gif" alt="개요" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;운영목적 : 3D 입체영상 제작 분야별 전문 인력 양성을 통한 국내 3D 콘텐츠 산업의 조기 정착 유도</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육대상</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 3D 입체 제작기술 인재양성 : 영상 분야 현업 종사자</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 프로젝트 연계 3D 입체 리더 인재양성 : 3D 입체 작품 참여 제작 인력</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 3D 입체 제작기술 인재양성 : 3D기획/촬영/편집/그래픽/스테레오그래퍼</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 프로젝트 연계 3D 입체 리더 인재양성 : 작품 컨설팅을 통한 '맞춤형' 제작 교육</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육과정</p>


	<table cellspacing="0" summary="3D 입체콘텐츠 전문인력양성 교육과정 및 기간 상세 내역입니다." class="basicTable tableTh01">
		<caption>3D 입체콘텐츠 전문인력양성 교육과정 및 기간</caption>
		<thead>
			<tr>
				<th>분야</th>
				<th width="30%">과정명</th>
				<th>일자</th>
				<th>기간</th>
				<th>인원</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th scope="row" >합계</th>
				<td>31개 과정</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>350명</td>
			</tr>
		</tfoot>
		<tbody>
			<tr>
				<th scope="row" rowspan="27">3D 입체<br />
				제작기술<br />
				인재양성<br />
				(27과정)</th>
				<td class="alignL">3D입체프리비즈과정-1차</td>
				<td>3.21~4.25</td>
				<td>5일</td>
				<td>12명</td>
			</tr>
			<td class="alignL">3D입체프리비즈과정-2차</td>
				<td>10.10~10.14</td>
				<td>3일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체프리비즈과정-3차</td>
				<td>12.5~12.9</td>
				<td>5일</td>
				<td>12명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체촬영과정-1차</td>
				<td>3.28~4.1</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체촬영과정-2차</td>
				<td>4.25~4.29</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체촬영과정-3차</td>
				<td>5.23~5.27</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체촬영과정-4차</td>
				<td>9.19~9.23</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체촬영과정-5차</td>
				<td>&nbsp;</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체촬영과정-6차</td>
				<td>10.31~11.4</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체촬영과정-7차</td>
				<td>11.14~11.18</td>
				<td>5일</td>
				<td>10명</td>
			</tr>                                   
			<tr>
				<td class="alignL">3D입체촬영과정-8차</td>
				<td>12.12~12.16</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-1차</td>
				<td>4.4~4.8</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-2차</td>
				<td>5.16~5.20</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-3차</td>
				<td>5.30~6.3</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-4차</td>
				<td>6.20~6.24</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-5차</td>
				<td>7.4~7.8</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-6차</td>
				<td>9.26~9.30</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-7차</td>
				<td>11.7~11.11</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체편집과정-8차</td>
				<td>11.28~12.2</td>
				<td>5일</td>
				<td>11명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체VFX과정-1차</td>
				<td>4.11~4.15</td>
				<td>5일</td>
				<td>12명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체VFX과정-2차</td>
				<td>10.24~10.28</td>
				<td>5일</td>
				<td>12명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체VFX과정-3차</td>
				<td>11.21~11.25</td>
				<td>5일</td>
				<td>12명</td>
			</tr>
			<tr>
				<td class="alignL">스테레오그래퍼과정-1차</td>
				<td>4.18~4.22</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">스테레오그래퍼과정-2차</td>
				<td>6.27~7.1</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">스테레오그래퍼과정-3차</td>
				<td>10.17~10.21</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체게임과정-1차</td>
				<td>&nbsp;</td>
				<td>5일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">3D입체게임과정-2차</td>
				<td>&nbsp;</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<th scope="row">프로젝트 연계<br />
				3D 입체 리더<br />
				인재양성(4과정)</th>
				<td class="alignL">프로젝트 베이스<br />
				연계교육(5회)</td>
				<td>장기교육<br />(3월~12월)</td>
				<td>&nbsp;</td>
				<td>50명</td>
			</tr>
		</tbody>
	</table>

	<Br>
	<Br>
	<p><img src="/images/portal/common/stit_02.gif" alt="운영방향" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;3D 입체영상 제작 분야별 단기 실무 실습 과정 운영</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;국내 3D 프로젝트 중심으로 ‘맞춤형’컨설팅 제작 교육진행</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;풍부한 현장 경험과 강의 경험을 보유한 국내·외 전문 강사진을 활용 교육 서비스 제공</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_03.gif" alt="운영방향" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 중기교육과정은 수시로 맞춤형 교육으로 추후 별도 신청/선발기준 수립</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습 교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 국 내외 현장실무전문가, 학계전문가 등</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</p>
	<Br>
	<Br>


	<p><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;제작인력양성팀 주봉현 차장 (02-3219-6528, <a href="mailto:bhjoo@kocca.kr">bhjoo@kocca.kr</a>)</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;제작인력양성팀 임규복 과장 (02-3219-6530, <a href="mailto:bok@kocca.kr">bok@kocca.kr</a>)</p>
	</td>
  </tr>
</table>
<%} %>
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


