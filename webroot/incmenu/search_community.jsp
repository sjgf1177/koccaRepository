<%
/**
 * @@제 목 : ClubSearch.jsp - 커뮤니티검색
 * @@설 명 : 커뮤니티 검색 (카테고리별, 커뮤니티명 및 커뮤니티 검색어로 검색) - 검색결과 리스트 출력 
 * @@Copyright: Copyright (c) 2003
 * @@Company:(주)다음소프트 
 * @@작성자: 이상원
 **/
%>

<%@ page contentType="text/html; charset=euc-kr" %>						
                        
                        
<script language="JavaScript" src="<%= Path.JsURL %>cafe_lib.js"></script>   
<FORM name="frmClubSearch" method="post" action="/community/home/clubhome/ClubSearch.dsh">
<INPUT type="hidden" name="sNum" value="">
                        <TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr height="36">
								<td bgcolor="F5F5E7" style="BORDER-TOP:#EDEDED 1px solid;BORDER-BOTTOM:#EDEDED 1px SOLID">
								<form>
								<TABLE cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td width="152"><IMG src="/images/common/search_community.gif" border="0"></td>
										<td style="padding-left:6">
										<select style="font-size:9pt;color:666666">
										<option>대카테고리</option>
										</select>
										<select style="font-size:9pt;color:666666">
										<option>소카테고리</option>
										</select>
										</td>
										<td style="padding-left:6"><input type="text" style="width:188" class="input"></td>
										<td style="padding-left:6"><A HREF="#"><IMG src="/images/common/bt_p_search.gif" border="0"></A></td>
									</tr>
								</TABLE>
								</form>
                                </td>
							</tr>
						</TABLE>
<%@ include file="/community/home/include/DBConnection.jsp" %>

<%
	Category cate = new Category("01");
%>


                  <!--검색창 input 영역 시작 -->
                  <table cellpadding=0 cellspacing=0 border=0>
                    <form>
                      <tr> 
                        <td height=28 align=right><img src="/img/main/search_commu.gif" border=0 align=absmiddle></td>
                        <td style="padding-left:5px">
							<%=cate.getCodeCombo(conn,"frmClubSearch",90)%>
						</td>
                        <td align="left"  style="padding-left:5px">
                            <input type=text class="ece9e7" name="CName" size=25 maxlength=100 style="width:300;ime-mode:active;">
                        </td>
                        <td  style="padding-left:5px"><a href="JavaScript:frmClubSearch.submit();">
                        <img src="/img/main/search_icon.gif" alt="커뮤니티 검색" border=0 align=absmiddle></a></td>
                      </tr>
                    </form>
                  </table>
				  </form>
				  <!--검색창 input 영역 끝 -->

<%@ include file="/community/home/include/DBtail.jsp" %>

