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
                      <td height="35" width="720" align="right"  background="/images/user/game/aboutus/<%=tem_subimgpath%>/tit_07.gif" class="location"><img src="/images/user/game/common/location_bl.gif"> HOME > aboutus > 위치/연락처</td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>

                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/game/aboutus/st_b7-1.gif" width="352" height="15"></td>
                    </tr>
                    <tr>
                      <td height="5"></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="94" style="padding-left:150px;padding-top:40px" background="/images/user/game/aboutus/about07.jpg" valign="top" >[158-715] 서울시 양천구 목동동로 203-1 방송회관 8층 <br>
                       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; (구주소) 서울시 양천구 목1동 923-5 방송회관 8층 </td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td><img src="/images/user/game/aboutus/st_b7-2.gif" width="352" height="15"></td>
                    </tr>
                    <tr>
                      <td height="5"></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/game/aboutus/map.gif"></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height=30>&nbsp;</td>
                    </tr>
                  </table>
                  <!--table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td><img src="/images/user/game/aboutus/st_b7-3.gif" width="352" height="15"></td>
                    </tr>
                    <tr>
                      <td height="5"></td>
                    </tr>
                  </table>
                  <table width="720" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" rules="rows" frame="hsides">
                    <tr>
                      <td width="176" align="center" bgcolor="#F5FBE3"><strong>구분</strong></td>
                      <td width="112" align="center" bgcolor="#E4F4B5"><strong>담당자</strong></td>
                      <td width="136" align="center" bgcolor="#F5FBE3"><strong>전화번호</strong></td>
                      <td width="260" align="center" bgcolor="#E4F4B5"><strong>E-mail</strong></td>
                    </tr>
                    <tr>
                      <td class="tbl_gleft">시스템, 학사운영 관련 문의</td>
                      <td class="tbl_grc">박희은</td>
                      <td class="tbl_grc">02-540-8193</td>
                      <td class="tbl_grc"><a href="mailto:hhinny@gameacademy.or.kr">hhinny@gameacademy.or.kr</a></td>
                    </tr>
                    <tr>
                      <td class="tbl_gleft">과정, 과목 관련 문의</td>
                      <td class="tbl_grc">김민희</td>
                      <td class="tbl_grc">02-540-8195</td>
                      <td class="tbl_grc"><a href="mailto:minimanim@gameacademy.or.kr">minimanim@gameacademy.or.kr</a></td>
                    </tr>
                  </table--><!--/td>
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