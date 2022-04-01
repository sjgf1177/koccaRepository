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
                      <td height="35" width="720" align="right"  background="/images/user/game/aboutus/<%=tem_subimgpath%>/tit_04.gif" class="location"><img src="/images/user/game/common/location_bl.gif"> HOME > aboutus > 협정체결기관</td>
                    </tr>
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>
             
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2002f.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr>
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr>
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 
                                    (5개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>세종사이버대학교, 전남과학대학, 혜천대학, 호서대학교, 사이버게임대학</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2002b.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 (9개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="657" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="657">극동정보대학, 대불대학교, 동서대학교, 성덕대학, 
                                    세종사이버대학교, 영남대학교, 호서대학교, 전남과학대학, 탐라대학교 </td>
                                </tr>
                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">게임개발사 
                                    (9개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="657" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="657">㈜디지털드림플러스, ㈜미지프로덕션, ㈜베틀겜, ㈜버츄얼웨어, 
                                    ㈜소프트웨이브, ㈜에펙스디지털, ㈜엑사엔터테인먼트,<br>
                                    ㈜오락스, ㈜코디넷 </td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2003f.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 
                                    (37개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td>경남대학교, 광주보건대학, 광주여자대학교, 김천과학대학, 나사렛대학교, 
                                    남부대학교, 남서울대학교, 대경대학, 대구미래대학, 대구보건대학, 대불대학교, 
                                    동명대학, 동명정보대학교, 동의공업대학, 백제예술대학, 부산경상대학, 부산외국어대학교, 
                                    성덕대학, 세경대학, 세종사이버대학교, 영산대학교, 영진사이버대학, 예원대학교, 
                                    울산과학대학, 원광대학교, 원광디지털대학교, 전남과학대학, 전주공업대학, 진주국제대학교, 
                                    청주교육대학교, 추계예술대학교, 춘천정보대학, 탐라대학교, 한국외국어대학교, 
                                    한성대학교, 혜전대학, 호서대학교 </td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2003b.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 
                                    (8개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td>군산대학교, 동서대학교, 한국산업기술대학교, 홍익대학교, 거창전문대학교, 
                                    계원조형예술대학, 담양대학, 동남보건대학 </td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2004f.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 
                                    (39개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td>경북대학교, 경동정보대학, 공주대학교, 광주여자대학교, 김천과학대학, 나사렛대학교, 
                                    남서울대학교, 대경대학, 대구미래대학, 대구사이버대학교, 대구예술대학교, 대구한의대학교, 
                                    대불대학교, 동명대학, 동의공업대학, 배재대학교, 서라벌대학, 서울보건대학, 
                                    성신여자대학교, 송호대학, 숭실대학교, 영산대학교, 예원예술대학교, 우송대학교, 
                                    우송정보대학, 울산과학대학, 원광대학교, 전남과학대학, 전주공업대학, 탐라대학교, 
                                    창신대학, 천안대학교, 청강문화산업대학, 한국외국어대학교, 한성대학교, 한세대학교, 
                                    혜전대학, 호남대학교, 호서대학교</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2004b.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 
                                    (8개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td>경기대학교, 경주대학교, 광주보건대학, 세경대학, 송원대학, 우석대학교, 
                                    한국기술교육대학교, 한국싸이버대학교, 한림대학교, 전주대학교</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2005f.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 
                                    (27개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td>경북대학교, 경운대학교, 김천과학대학, 대구미래대학, 대구한의대학교, 동의과학대학, 
                                    명지전문대학, 배재대학교, 상지영서대학, 세종사이버대학교, 영진사이버대학, 
                                    예원예술대학교, 우석대학교, 우송대학교, 원광대학교, 전주대학교, 주성대학, 
                                    창신대학, 청강문화산업대학, 청양대학, 한국싸이버대학교, 한림대학교, 한림성심대학, 
                                    혜전대학, 호남대학교, 호서대학교, 호원대학교</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top">
					  <table width="720" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/game/aboutus/st_2005b.gif" width="352" height="15"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/game/aboutus/box_t.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top">
							<table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/game/aboutus/bl_01.gif" align="absmiddle"><strong><font color="#669933">대학교 
                                    (17개)</font></strong></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/game/aboutus/line_bg.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td>가톨릭대학교, 강원대학교, 광주대학교, 광주보건대학, 남부대학교, 동서대학교, 
                                    부산정보대학, 세경대학, 용인송담대학, 원광디지털대학교, 장안대학, 제주한라대학, 
                                    춘해대학, 한국산업기술대학교, 한국외국어대학교, 한국재활복지대학, 한라대학교</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/game/aboutus/box_bo.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/aboutus/box1_bg.gif">
                    <tr> 
                      <td valign="top"><img src="/images/user/game/aboutus/box1_t.gif" width="720" height="18"></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top">
						<table width="650" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td>게임아카데미와 원격교육 공동 추진 협정 체결 대학교에는 대학 회원에게 제공하는 학사 
                              운영 기준에 따라 서비스를<br>
                              제공하며, 비용은 일반인 회원과 마찬가지로 무료입니다.<br>
                              세부 문의는 담당자 ( 박희은 ☎ 02-512-4873 <a href="mailto:hhinny@gameacademy.or.kr"><strong>hhinny@gameacademy.or.kr 
                              </strong></a>)에게 연락하시기 바랍니다. </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="bottom"><img src="/images/user/game/aboutus/box1_bo.gif" width="720" height="18"></td>
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