<%
//**********************************************************
//  1. 제      목: 사이트맵
//  2. 프로그램명 : ku_SiteMap.jsp
//  3. 개      요: 사이트맵
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>
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
    String  v_faqcategory = "";
    v_faqcategory = box.getString("faqcategory");

    int     v_fnum       = 0, v_totalpage = 0, v_rowcount = 1;
    int     v_pageno     = box.getInt("p_pageno");
    String  v_title      = "";
    String  v_contents   = "";
    String  v_indate     = "";
    String  v_searchtext = box.getString("p_searchtxt");
    ArrayList list       = (ArrayList)request.getAttribute("selectList");
    String faqcategory   ="";

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topSiteMap.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--

//-->
</SCRIPT>

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td  class="location" > HOME > 사이트맵</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/common/<%=tem_subimgpath%>/tit_sitemap.gif"></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/common/text_sitemap.gif"></td>
                          </tr>
                          <tr >
                            <td height="1" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr >
                            <td height="35" ></td>
                          </tr>
                        </table>
                        <!--사이트맵
-->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="182" valign="top">
                              <!--타이틀 -->
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_course.gif"></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td background="/images/user/kocca/common/sitemap_bg_course.jpg"><table width="182" height="10" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_01.gif"></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('1', '01', '1')">
                                            과정검색</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_01.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('1', '02', '2')">
                                            상시과정</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_01.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('1', '03', '3')">수시과정</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_01.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('1', '04', '4')">
                                            전문가과정</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_01.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('1', '05', '5')">
                                            워크숍과정</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_01.gif"></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('1', '06', '6')">
                                            학점인증과정</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"></div></td>
                                        <td class="tbl_bleft_none">&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td width="42">&nbsp;</td>
                            <td width="182" valign="top"><table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><!--img src="/images/user/kocca/common/sm_st_intro.gif"----></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top" background=""><table width="182" height="10" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <!--td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_02.gif"></div></td--->
                                        <!---td class="tbl_bleft_none"><a href="javascript:menuForward('4', '01', '1')">아카데미소개</td----->
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <!---td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_02.gif"></div></td--->
                                        <!---td class="tbl_bleft_none"><a href="javascript:menuForward('4', '02', '2')">원장인사말</td---->
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <!----td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_02.gif"></div></td>
                                        <td class="tbl_bleft_none"><a href="javascript:menuForward('4', '03', '3')">문화콘덴츠 인력양성제도</a></td----->
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td width="42">&nbsp;</td>
                            <td width="182" valign="top"><table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_culture.gif" ></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top" background="/images/user/kocca/common/sitemap_bg_contents.jpg"><table width="182" height="10" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                                    <!--table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_03.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('5', '01', '1')">회원포럼</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table-->
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_03.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('5', '03', '3')">
                                            파워 인터뷰</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_03.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('5', '02', '2')">오프라인 강좌</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>

                                    </td>
                                </tr>
                              </table></td>
                            <td width="50">&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="3" colspan="6"></td>
                          </tr>
                          <tr>
                            <td rowspan="3" valign="top"><table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_myclass.gif" ></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top" background="/images/user/kocca/common/sitemap_bg_myclass.jpg"><table width="182" height="10" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '01', '1')">
                                            강의실 </a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '02', '2')">
                                            수강이력</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '03', '3')">
                                            나의상담내역</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif"></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '04', '4')">
                                            나의설문</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '05', '5')">
                                            나의학습활동</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '06', '6')">
                                            e-test</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '07', '7')">
                                            맞춤추천강좌</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '08', '8')">
                                            관심강좌</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <!--table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_04.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('3', '09', '9')">
                                            마일리지</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table--></td>
                                </tr>
                              </table></td>
                            <td height="260">&nbsp;</td>
                            <td valign="top"><table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_apply.gif" ></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top" background="/images/user/kocca/common/sitemap_bg_apply.jpg"><table width="182" height="10" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_05.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('2', '01', '1')">수강신청/ 취소 안내</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_05.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('2', '02', '2')">수강신청</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_05.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('2', '03', '3')">
                                            수강신청 확인/취소</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_05.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('2', '04', '4')">
                                            오프라인강좌 신청/확인</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_05.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('2', '05', '5')">
                                            교육일정</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"></div></td>
                                        <td class="tbl_bleft_none">&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"></div></td>
                                        <td class="tbl_bleft_none">&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td>&nbsp;</td>
                            <td valign="top"><table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_customer.gif" ></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top" background="/images/user/kocca/common/sitemap_bg_customer.jpg"><table width="182" height="10" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('6', '01', '1')">
                                            F A Q</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('6', '02', '2')">
                                            1 : 1 상담</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('6', '03', '3')">
                                            온라인학습이용안내</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('6', '04', '4')">
                                            학습자료실</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('6', '05', '5')">
                                            프로그램다운로드</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="javascript:menuForward('6', '06', '6')">
                                            학습환경시스템</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"></div></td>
                                        <td class="tbl_bleft_none">&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td rowspan="2">&nbsp;</td>
                            <td height="3" colspan="3"></td>
                            <td rowspan="2">&nbsp;</td>
                          </tr>
                          <tr>
                            <td valign="top"><table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_member.gif" ></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td valign="top" background="/images/user/kocca/common/sitemap_bg_customer.jpg"><table width="182" height="10" border="0" cellpadding="0" cellspacing="0">
                                      <tr>
                                        <td></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="/servlet/controller.homepage.KLoginServlet?p_process=golosspwd">
                                            아이디/비밀번호찾기</a>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">
                                            <a href="/servlet/controller.homepage.KMemberJoinServlet?p_process=join">
                                            회원가입</a></td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">개인정보보호정책</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"><img src="/images/user/kocca/common/site_bl_06.gif" ></div></td>
                                        <td class="tbl_bleft_none">이용약관</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table>
                                    <table width="182" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="24"><div align="right"></div></td>
                                        <td class="tbl_bleft_none">&nbsp;</td>
                                      </tr>
                                      <tr>
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td>&nbsp;</td>
                            <td valign="top"><table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_notice.gif" ></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sitemap_bo.gif" ></td>
                                </tr>
                                <tr>
                                  <td height="18"></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sm_st_sitemap.gif"></td>
                                </tr>
                              </table>
                              <table width="182" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/common/sitemap_bo.gif" ></td>
                                </tr>
                                <tr>
                                  <td height="18"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table> 
    </form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->