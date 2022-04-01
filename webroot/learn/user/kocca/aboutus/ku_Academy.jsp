<%
//**********************************************************
//  1. 제      목: 아카데미소개 > 문화콘텐츠 인력양성체계도
//  2. 프로그램명 : ku_Academy.jsp
//  3. 개      요: 문화콘텐츠 인력양성체계도
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    box.put("leftmenu","03"); 
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topAbout.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

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

</SCRIPT>

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 아카데미소개 > 문화콘텐츠 인력양성전체 
                              체계</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/aboutus/<%=tem_subimgpath%>/tit_academy.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="88" rowspan="3"><img src="/images/user/kocca/aboutus/text_academy.gif" width="680" height="33"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr > 
                            <td height="1" colspan="2" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr > 
                            <td height="7" colspan="2" ></td>
                          </tr>
                        </table>
                        <!--체계도목록 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="20">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td><span style="CURSOR: hand" 
                              onClick=comment(menu1);><img src="/images/user/kocca/aboutus/list_01.gif"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><SPAN id=menu1 
                              style="DISPLAY: none"> 
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      문화콘텐츠산업의 글로벌화를 대비하는 전략적 리더 및 핵심 경영자 그룹 양성</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>대상</strong> 
                                      : 교수, CEO, CT기술자, 인문학 등 다분야 전문가 </TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>기간 
                                      </strong>: 6개월, 연 1회 </TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> - 국내교육 
                                      (5개월) : 야간 (18:30～10:00), 주 2회 (1일 3hr) 
                                      <br>
                                      - 해외교육 (2주) : 현지 대학원 수업 (Certificate 제공) 
                                      <br>
                                      - 수료후 : 팀프로젝트 수행결과물 제출 (사업계획서 강의교안 등)</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>인원</strong> 
                                      : 30명 내외</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      2 ~ 4 : 수강생 모집 및 선정</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      5 ~ 10 : 교육운영 (해외연수 : 8～9월중)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      박병호 대리(02-2016-4123 / <a href="mailto:jackone@kocca.or.kr">jackone@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </SPAN></td>
                          </tr>
                          <tr> 
                            <td valign="top"><span style="CURSOR: hand" 
                              onClick=comment(menu2);><img src="/images/user/kocca/aboutus/list_02.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu2" style="DISPLAY: none"> 
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      공무원, 교사 등 공공부문 전문인력에 대한 문화콘텐츠산업의 중요성 교육</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      전문인력 유입 및 문화콘텐츠산업 종사자에 대한 재교육 추진 </TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">공공부문 
                                      전문인력 연수과정 (주간)</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con">- 공무원/교사/교수/사법연수생/변호사 
                                      : 연 1~2회, 1~2주, 과정별 30명 내외</TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">문화콘텐츠 
                                      연수과정 (야간)</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> - 집중과정(舊 
                                      전문과정) : 연 2회, 1개월, 정원 25명 <br>
                                      - 심화과정 : 연 2회, 2개 과정(기획력,비즈니스 등), 2개월, 정원 
                                      25명 </TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">문화콘텐츠산업 
                                      실무워크숍 (주간, 합숙)</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con">- 연 5회, 1박 2일, 정원 50명</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">지역특화 
                                      전문인력 양성과정(주간 또는 야간)</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con">- 5개 지역, 지역별 25명 내외</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle">(세부일정 
                                      추후 공고)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4 ~ 12 : 교육 운영</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      지현승 대리 (02-2016-4124 / <a href="mailto:hsji@kocca.or.kr">hsji@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td valign="top"><span style="CURSOR: hand" 
                              onClick=comment(menu3);><img src="/images/user/kocca/aboutus/list_03.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu3" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      문화콘텐츠 전문인력의 글로벌 안목과 비전 함양을 위한 교육프로그램 운영</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>연수지역</strong> 
                                      : 미국, 영국, 일본, 중국</TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>연수인원</strong> 
                                      : 총 120명 내외 (지역별 각 30명)</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>연수기간</strong> 
                                      : 미국/영국 2주, 일본/중국 1주</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>연수비 
                                      지원</strong> : 교육비 전액 및 숙박비 50% (총 교육비의 60% 
                                      이내)</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>연수내용</strong> 
                                      : 각 해외시장별 문화콘텐츠 산업의 이해</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con">※ 공통 : 해외업체와의 Biz Matching 
                                      및 Global Networking 활동 지원 </TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle">(세부일정 
                                      추후 공고)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      3 ~ 4 : 현지 연수처 확정</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4 ~ 5 : 연수생 선정, 연수 준비</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      6 ~ 9 : 현지 연수 진행</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      서희선 과장(02-2016-4121 /<a href="mailto:dionia@kocca.or.kr"> 
                                      dionia@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td valign="top" ><span style="CURSOR: hand" 
                              onClick=comment(menu4);><img src="/images/user/kocca/aboutus/list_04.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu4" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      산업 전반의 생산성 및 경쟁력 강화를 위해 산업계 수요가 큰 기획(Planning)·창작(Story-Telling) 
                                      <br>
                                      부문 전문가 양성 </TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>교육기간</strong> 
                                      : 6개월 (2006. 7 ～ 2007. 2)</TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>교육시간</strong> 
                                      : 주간 (09:00～17:00) 원칙 (1일 7시간)</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>교육대상</strong> 
                                      : 관련분야 전공자 및 경력자</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>교육정원</strong> 
                                      : 총 20명 내외 (학생보다 프리랜서/업계 종사자 우선)</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>교육내용</strong></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"> - 국내/해외 연수 : 문화콘텐츠 장르별 
                                      기획/창작사례, 해외성공사례 <br>
                                      - 업체공동 프로젝트 실습, 파견근무, 창작결과물 발표회 등 </TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4 ~ 5 : 수강생 모집 및 선정</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      7 ~ 2007. 2 : 교육 운영</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      박병호 대리(02-2016-4123 /<a href="mailto:jackone@kocca.or.kr"> 
                                      jackone@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td valign="top" ><span style="CURSOR: hand" 
                              onClick=comment(menu5);><img src="/images/user/kocca/aboutus/list_05.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu5" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      문화콘텐츠 전공학생의 창의성 발굴 및 청년기업가 육성 기회 제공</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">지원대상 
                                      : 문화콘텐츠 관련 동아리 (전문대 이상) / 30개 내외</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con">- 해당학교(과)에 
                                      정식으로 등록된 동아리에 한함 / 동일학교 복수 신청 가능 <br>
                                      - 신청 필수요건 : 산학협력단/지도교수 필참, 산학협력업체 참여 유도 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>지원규모</strong> 
                                      : (추후 공지)</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>지원내역</strong> 
                                      : 창작활동에 필요한 재료비, 조사비, 활동비 등</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">성과관리 
                                      및 활성화 지원</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con">- 학교(교수)차원 인센티브 : 학점혜택, 
                                      장학금 지급 등 <br>
                                      - 사업종료시 “대학창작물 발표회” 개최 </TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle">(세부일정 
                                      추후 공고)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      3 ~ 4 : 사업공고 및 선정 심사</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      5 ~11 : 사업 진행 (동아리 창작활동, 중간 점검 포함)</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      12 : 결과 평가</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      우성배 과장(02-2016-4122 / <a href="mailto:wsb21@kocca.or.kr">wsb21@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td valign="top" ><span style="CURSOR: hand" 
                              onClick=comment(menu6);><img src="/images/user/kocca/aboutus/list_06.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu6" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      인력양성 정책개발을 위한 기초조사 및 우수 사례 벤치마킹 실시</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">교육기관 
                                      현황조사 및 인력수급예측 연구</TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">문화콘텐츠산업 
                                      인력유형 및 직종분석 종합연구</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">문화콘텐츠 
                                      선진기관 인력양성 벤치마킹 연구용역</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      3 : 연구기관 선정</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4 ~10 : 연구 진행</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      10 : 연구결과보고</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      박병호 대리(02-2016-4123 / <a href="mailto:jackone@kocca.or.kr">jackone@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td valign="top" ><span style="CURSOR: hand" 
                              onClick=comment(menu7);><img src="/images/user/kocca/aboutus/list_07.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu7" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      청소년 대상 문화콘텐츠산업의 중요성 홍보</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>지원대상 
                                      </strong>: 초/중/고교 등 정규학교 및 청소년 시설 단체</TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">내용</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> - 문화콘텐츠 
                                      앰배서더 강연활동 (150개) <br>
                                      - 문화콘텐츠 창작페스티벌 개최 <br>
                                      - 문화콘텐츠 진로 가이드북 제작 <br>
                                      - 문화콘텐츠 나눔운동 추진 등 </TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      3 : 강연신청</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4～12 : 강연진행 및 나눔운동 추진</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4～12 : 문화콘텐츠 진로 가이드북 제작</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">기획혁신팀 
                                      윤여원(02-2016-4036 / <a href="mailto:nurimy@kocca.or.kr">nurimy@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td valign="top" ><span style="CURSOR: hand" 
                              onClick=comment(menu8);><img src="/images/user/kocca/aboutus/list_08.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu8" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      산학협력 기반의 문화콘텐츠 관련학과 특성화로 산업계 필요인력 공급시스템 
                                      구축 지원</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>지원대상</strong> 
                                      : 문화콘텐츠관련 정규교육기관 (전문대 이상) 10개교 내외</TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>지원규모</strong> 
                                      : 학교당 3.4억 내외</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>지원내용</strong> 
                                      : 기자재 구입 지원, 프로젝트 실습 지원, 커리큘럼 개발, 산학연계 등</TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong>기대효과</strong> 
                                      : 문화콘텐츠 취업률 제고, 교과과정 개편, 우수학생 유치 등</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      3 : 사업공고(예정)</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4 : 신청서 접수 및 선정 심사</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      5 ~ 12 : 사업수행</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      우성배 과장(02-2016-4122 / <a href="mailto:wsb21@kocca.or.kr">wsb21@kocca.or.kr</a>)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td valign="top" ><span style="CURSOR: hand" 
                              onClick=comment(menu9);><img src="/images/user/kocca/aboutus/list_09.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr>
                            <td valign="top"style="PADDING-LEFT: 35px" ><span id="menu9" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      문화콘텐츠산업 종사자의 업무수행능력 향상지원</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">교육운영</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con">- 개별과목(무료) 
                                      : 매월 20개 과목 내외 <br>
                                      - 전문과목(유료) : 분기 6개과목 내외 <br>
                                      - 워 크 숍(유료) : 분기 6개과목 내외 <br>
                                      - 학점인정(유료) : 1학기(5과목) 2학기(14과목) </TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><font color="#336699">콘텐츠 
                                      개발 (총 11개과목)</font></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> - 전문 6개과목, 
                                      워크숍 1개과목, 학점 4개과목 <br>
                                      - 기 개발(2003년)과목 업데이트 개발 (20개과목) </TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      1 ~ 12 : 아카데미 운영</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      2 ~ 3 : 2006년 개발계획 수립</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>2006. 
                                      4 ~12 : 사이버아카데미 운영 및 콘텐츠 개발</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      서희선 과장(02-2016-4121 / <a href="mailto:dionia@kocca.or.kr">dionia@kocca.or.kr)</a></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr>
                            <td valign="top" ><span style="CURSOR: hand" 
                              onClick=comment(menu10);><img src="/images/user/kocca/aboutus/list_10.gif" width="680" height="37"></span></td>
                          </tr>
                          <tr>
                            <td valign="top" style="PADDING-LEFT: 35px"><span id="menu10" style="DISPLAY: none">
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=10></TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_purpose.gif"> 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10" valign="top"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"> 
                                      문화콘텐츠 구직자와 기업을 연결해주는 취업지원 시스템 운영</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_con.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"> <img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">온/오프 
                                      채용지원 행사를 통해 실질적인 Work-Net 구축</TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top" class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">문화콘텐츠 
                                      직업 및 인력관련 고급정보제공</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_day.gif" align="absmiddle">(세부일정 
                                      추후 공고)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>채용지원 
                                      교육(구직자 대상) : 수시</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>채용박람회 
                                      (온라인) : 연 3회</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>작품전시회 
                                      (온/오프 연계) : 연 2회</TD>
                                  </TR>
                                  <TR>
                                    <TD class="font_con"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8"><strong></strong>수시 
                                      이벤트 (우수 이력서 시상, 채용수기 공모전 등)</TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/aboutus/st_busi_tel.gif" width="220" height="14"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height="10"><img src="/images/user/kocca/aboutus/bl_nemo.gif" height="8">인력양성팀 
                                      우성배 과장(02-2016-4122 / <a href="mailto:wsb21@kocca.or.kr">wsb21@kocca.or.kr</a>) 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD height="20">&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr>
                            <td valign="top" >&nbsp;</td>
                          </tr>
                        </table> 
      <!-- footer -->
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  --------------------> 
      <!-- footer end -->
    </td>
  </tr>
</table>

</body>
</html>
