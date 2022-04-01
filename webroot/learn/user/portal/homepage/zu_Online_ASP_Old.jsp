<%@ page contentType = "text/html;charset=euc-kr" %>
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	            <td >
	                <table  width="100%" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                        <td height="68" valign="top" background="/images/newmain_20100625/topnavi_bgbg.gif" style="background-position:bottom;background-repeat:no-repeat;" align="center"><!--탑네비게이션 영역-->
	
	        <!-- 2014-05-27 메뉴 수정. 기존의 flash로 만들어진 메뉴를 제거하고 이미지와 텍스트로 교체
	            하지만 기존 코딩 방식이 table로 구성되어 있어서 문제가 많은 2014년 후반기 개편 작업시에 작업 필요 //-->
	        <div id="wrap">
				<div id="top_gnb">
					<div id="gnb_menu">
						<ul>
							<li onmouseover="toggleSubMenu('1', true);" onmouseout="toggleSubMenu('1', false);"><a href="/index.jsp"><img src="/images/asp/type1/gnb_m01.jpg" alt="Home" id="oImg1" /></a></li>
							<li onmouseover="toggleSubMenu('2', true);" onmouseout="toggleSubMenu('2', false);"><a href="javascript:menuForward('8', '01');"><img src="/images/asp/type1/gnb_m02.jpg" alt="과정안내" id="oImg2" /></a></li>
							<li onmouseover="toggleSubMenu('3', true);" onmouseout="toggleSubMenu('3', false);"><a href="javascript:menuForward('9', '01');"><img src="/images/asp/type1/gnb_m03.jpg" alt="수강신청" id="oImg3" /></a></li>
							<li onmouseover="toggleSubMenu('4', true);" onmouseout="toggleSubMenu('4', false);"><a href="javascript:menuForward('5', '01');"><img src="/images/asp/type1/gnb_m04.jpg" alt="정보광장" id="oImg4" /></a></li>
							<li onmouseover="toggleSubMenu('5', true);" onmouseout="toggleSubMenu('5', false);"><a href="javascript:menuForward('3', '11');"><img src="/images/asp/type1/gnb_m05.jpg" alt="나의강의실" id="oImg5" /></a></li>
							<li onmouseover="toggleSubMenu('6', true);" onmouseout="toggleSubMenu('6', false);"><a href="javascript:menuForward('4', '07');"><img src="/images/asp/type1/gnb_m06.jpg" alt="학습지원센터" id="oImg6" /></a></li>
						</ul>
	
						<ul id="gnb_depth2" onmouseover="toggleSubMenu('2', true);" onmouseout="toggleSubMenu('2', false);">
							<li><a href="javascript:menuForward('8', '01');">교육과정안내</a></li>
							<li><a href="javascript:menuForward('8', '02');">월별교육일정</a></li>
						</ul>
						<ul id="gnb_depth3" onmouseover="toggleSubMenu('3', true);" onmouseout="toggleSubMenu('3', false);">
							<li><a href="javascript:menuForward('9', '01');">수강신청안내</a></li>
							<li><a href="javascript:menuForward('9', '02');">수강신청</a></li>
							<li><a href="javascript:menuForward('9', '03');">수강신청확인/취소</a></li>
						</ul>
						<ul id="gnb_depth4" onmouseover="toggleSubMenu('4', true);" onmouseout="toggleSubMenu('4', false);">
	                        <!--
							<li><a href="javascript:menuForward('5', '03');">교육자료실</a></li>
	                        //-->
						</ul>
						<ul id="gnb_depth5" onmouseover="toggleSubMenu('5', true);" onmouseout="toggleSubMenu('5', false);">
							<li><a href="javascript:menuForward('3', '11');">수강중인과정</a></li>
							<li><a href="javascript:menuForward('3', '02');">나의교육자료</a></li>
							<li><a href="javascript:menuForward('3', '12');">나의상담내역</a></li>
						</ul>
						<ul id="gnb_depth6" onmouseover="toggleSubMenu('6', true);" onmouseout="toggleSubMenu('6', false);">
							<li><a href="javascript:menuForward('4', '07');">공지사항</a></li>
							<li><a href="javascript:menuForward('4', '01');">FAQ</a></li>
							<li><a href="javascript:menuForward('4', '02');">Q&A</a></li>
							<li><a href="javascript:menuForward('4', '03');">S/W다운로드</a></li>
							<li><a href="javascript:menuForward('4', '05');">학습환경도우미</a></li>
	                        <!-- <li><a href="javascript:menuForward('4', '09');">1:1문의</a></li> //-->
						</ul>
					</div>
				</div>
			</div>
	
	
	                        </td>
	                        <td valign="top">&nbsp;</td>
	                    </tr>
	                    <tr>
	                        <td align="center">
	                            <% if(box.getString("gubun").equals("1")) {%>
	                                <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login.jsp"%>' />
	                            <% } else if(box.getString("gubun").equals("2")) {%>
	                                <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login1.jsp"%>' />
	                            <% } else if(box.getString("gubun").equals("3")) {%>
	                                <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login2.jsp"%>' />
	                            <% } else if(box.getString("gubun").equals("4")) {%>
	                                <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_login3.jsp"%>' />
	                            <% } else if(box.getString("gubun").equals("10")) {%>
	                                <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_IdPw.jsp"%>' />
	                            <% } else if(box.getString("gubun").equals("70")) {%>
	                                <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_Edit_Login.jsp"%>' />
	                            <%} else { %>
	                                <jsp:include page='<%="/learn/user/portal/homepage/zu_Online_ASP_type"+mainType+".jsp"%>' flush="true">
	                                    <jsp:param name="ser_nm" value="<%= ser_nm %>" />
	                                    <jsp:param name="rejectResult" value="<%= rejectResult %>" />
	                                </jsp:include>
	                            <%}%>
	                        </td>
	                    </tr>
	                </table>
	            </td>
	        </tr>
	        <tr>
	            <td align="center">
	                <table width="960px" border="0" cellspacing="0" cellpadding="0">
	                    <tr>
	                        <td height="75" align="left">
	                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                                <tr>
	                                    <td width="100%" height="75" valign="top" style="border-top: #d5d5d5 1px solid;">
	                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td height="12" colspan="2" align="center"></td>
	                                            </tr>
	                                            <tr>
	                                                <td width="168" rowspan="2" align="center"><a href="#" onclick="window.open('http://edu.kocca.or.kr'); return false;"><img src="/images/asp/type1/footer_logo.gif" alt="KOCCA"  width="93" height="34" /></a></td>
	                                                <td height="25" valign="top">
	                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                                                        <tr>
	                                                            <td width="80"><a href="javascript:menuMainForward('99', '/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_PRIVACY')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image82','','/images/asp/type1/footer_menu3_on.gif',1)"><img src="/images/asp/type1/footer_menu3_off.gif" alt="개인정보 처리방침" name="Image82" width="75" height="11" border="0" id="Image82" /></a></td>
	                                                            <td width="60"><a href="javascript:menuForward(7,'01');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image80','','/images/asp/type1/footer_menu1_on.gif',1)"><img src="/images/asp/type1/footer_menu1_off.gif" alt="아카데미소개" name="Image80" width="57" height="11" border="0" id="Image80" /></a></td>
	                                                            <td width="60"><a href="javascript:menuForward(4,'01');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image81','','/images/asp/type1/footer_menu2_on.gif',1)"><img src="/images/asp/type1/footer_menu2_off.gif" alt="자주묻는질문" name="Image81" width="57" height="11" border="0" id="Image81" /></a></td>
	                                                            <td width="90"><a href="javascript:menuMainForward('99', '/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image84','','/images/asp/type1/footer_menu5_on.gif',1)"><img src="/images/asp/type1/footer_menu5_off.gif" alt="이메일무단수집거부" name="Image84" width="85" height="11" border="0" id="Image84" /></a></td>
	                                                            <td width="40"><a href="javascript:menuForward(4,'09');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image85','','/images/asp/type1/footer_menu6_on.gif',1)"><img src="/images/asp/type1/footer_menu6_off.gif" alt="1:1문의" name="Image85" width="34" height="11" border="0" id="Image85" /></a></td>
	                                                            <td width="120" align="right"><img src="/images/asp/type1/footer_menu_familysite.gif" alt="Family Site"  width="68" height="12" /></td>
	                                                            <td style="padding-left:8px">
	                                                                <select name="relatedlink" onChange="goSiteSelect()">
	                                                                <option>:: 관련사이트바로가기</option>
	                                                                <%
	                                                                    for( int ii = 0 ; ii < bolist.size() ; ii++ ){
	                                                                        DataBox ddbox = (DataBox)bolist.get(ii);
	
	                                                                        int vv_seq      = ddbox.getInt("d_seq");
	                                                                        int vv_sort     = ddbox.getInt("d_sort");
	                                                                        String vv_sitenm   = ddbox.getString("d_sitenm");
	                                                                        String vv_url      = ddbox.getString("d_url");
	                                                                        String vv_isuse    = ddbox.getString("d_isuse");
	                                                                        String vv_islogin  = ddbox.getString("d_islogin");
	                                                                        String vv_ldate    = ddbox.getString("d_ldate");
	                                                                        String vv_grcode   = ddbox.getString("d_grcode");
	                                                                        String vv_banner   = ddbox.getString("d_banner");
	                                                                %>
	                                                                            <option value='<%=vv_grcode%>:<%=vv_seq%>:<%=vv_islogin%>'><%=vv_sitenm%></option>
	                                                                <%  }
	                                                                %>
	                                                                </select>
	                                                            </td>
	                                                            <td width="50" align="right"><a href="#"><img src="/images/asp/type1/btn_familysite_go.gif" alt="바로가기" width="42" height="18" /></a></td>
	                                                            <td width="35">&nbsp;</td>
	                                                        </tr>
	                                                    </table>
	                                                </td>
	                                            </tr>
	                                            <tr>
	                                                <td align="left">
	                                                    서울시 종로구 대학로 57 
	현장교육문의 02-6441-3255 / 온라인교육 02-6441-3258 
	                                                </td>
	                                                <!-- td align="left"><img src="/images/asp/type1/footer_add.gif" alt="서울시 양천구 목동동로 203-1 방송회관 8층 교육담당자:제작인력양성팀 장광진대리 대표전화:02-2161-0077(온라인교육)" width="780" height="11" /></td -->
	                                            </tr>
	                                            <!-- tr>
	                                                <td align="center">&nbsp;</td>
	                                                <td align="left"><img src="/images/asp/type1/footer_copyright.gif" width="360" height="11" /></td>
	                                            </tr -->
	                                        </table>
	                                    </td>
	                                    <td>&nbsp;</td>
	                                </tr>
	                            </table>
	                        </td>
	                    </tr>
	                </table>
	            </td>
	        </tr>
	    </table>
