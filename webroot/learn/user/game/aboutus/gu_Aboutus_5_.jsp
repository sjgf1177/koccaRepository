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

	box.put("leftmenu","01"); 
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topAboutus.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
                  <!-- 타이틀 -->
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="35" width="720" align="right"  background="/images/user/game/aboutus/<%=tem_subimgpath%>/tit_05.gif" class="location"><img src="/images/user/game/common/location_bl.gif"> HOME > aboutus > 발전단계</td>
                    </tr>
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>
             
                  <table width="720" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed" rules="rows" frame="hsides">
                    <tr> 
                      <td width="106"><img src="/images/user/game/aboutus/about05-1.gif" width="106" height="26"></td>
                      <td width="5">&nbsp;</td>
                      <td width="106"><img src="/images/user/game/aboutus/about05-2.gif" width="106" height="26"></td>
                      <td width="5">&nbsp;</td>
                      <td width="503"><img src="/images/user/game/aboutus/about05-3.gif"></td>
                    </tr>
                    <tr> 
                      <td align="center">
					  <table width="77" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="77" align="center" background="/images/user/game/aboutus/c_bg.gif"><strong>1단계</strong><br>
                              (2001)</td>
                          </tr>
                        </table></td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td align="center">준비단계</td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td class="tbl_about5">- 게임관련 산 · 학 · 관 위원회 구성<br>
                        - 교재개발, 교육콘텐츠 및 내외부 운영시스템 구성<br>
                        - 콘텐츠 개발 인력 및 원격교육 운영 인력 확보</td>
                    </tr>
                    <tr> 
                      <td align="center">
					  <table width="77" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="77" align="center" background="/images/user/game/aboutus/c_bg.gif"><strong>2단계</strong><br>
                              (2002)</td>
                          </tr>
                        </table></td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td align="center">시범 운영</td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td class="tbl_about5">- LMS 도입 · 콘텐츠 개발 (외주제작 관리)<br>
                        - 교육시스템 구축 및 내외부 시범 운영 서비스<br>
                        - 1차년도 학생 선발 및 원격교육사이트 오픈<br>
                        - 1 · 2차 학년도 콘텐츠 제작 및 보급<br>
                        - 전국 게임관련대학과 연계 시범 서비스</td>
                    </tr>
                    <tr> 
                      <td align="center">
					  <table width="77" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="77" align="center" background="/images/user/game/aboutus/c_bg.gif"><strong>3단계</strong><br>
                              (2003)</td>
                          </tr>
                        </table></td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td align="center">기반 조성</td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td class="tbl_about5">- 원격대학 교원 및 2차년도 학생 선발<br>
                        - 1 · 2차 학년도 콘텐츠 수정 보완<br>
                        - 2 · 3차 학년도 콘텐츠 제작<br>
                        - 전국 대학(기관)과 협력 체계 구축<br>
                        - 연간 5,000명 게임개발과정 교육</td>
                    </tr>
                    <tr> 
                      <td align="center">
					  <table width="77" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="77" align="center" background="/images/user/game/aboutus/c_bg.gif"><strong>4단계</strong><br>
                              (2004)</td>
                          </tr>
                        </table></td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td align="center">기반 조성</td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td class="tbl_about5">- 산 · 학 · 관 전문위원회 운영<br>
                        - 국내외 교육기관과 협력체계 구축<br>
                        - 2 · 3차 학년도 콘텐츠 수정 보완<br>
                        - 3 · 4차 학년도 콘텐츠 제작</td>
                    </tr>
                    <tr>
                      <td align="center">
					  <table width="77" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="77" align="center" background="/images/user/game/aboutus/c_bg.gif"><strong>5단계</strong><br>
                              (2005)</td>
                          </tr>
                        </table></td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td align="center">원격대학<br>
                        설립준비</td>
                      <td valign="bottom"><img src="/images/user/game/aboutus/vline.gif" width="5" height="76"></td>
                      <td class="tbl_about5">- 원격대학 설립을 위한 설비 확충<br>
                        - 3 · 4차 학년도 콘텐츠 수정 보완<br>
                        - 전자도서관 설치 · 운영<br>
                        - 해외교육기관과 협력 교육</td>
                    </tr>
                  </table><!--/td>
              </tr>
              <tr>
                <td height="30"  valign="top" class="submargin"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table-->


	
<!-- foote -->
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>