<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.course.*"%>
<%@ page import="com.credu.homepage.LinkSiteBean"%>

<%
	RequestBox footerBox = (RequestBox)request.getAttribute("requestbox");

	//관련부서 사이트
	LinkSiteBean site = new LinkSiteBean();
	ArrayList bolist = site.SelectList(footerBox);
	
	String footerUrl = "/servlet/controller.homepage.HomePageHelpServlet";
%>
	<div id="footer">
		<div class="copyright">
			<div class="copyright_left">
				<h3 style="width: 109px; height: 75px; float: left;">
					<!--하단 카피라이트 빈 로고 -->
					<img src="/images/portal/asp/typeB/main/footer_logo.png" />
				</h3>
				<ul>
					<li><a href="javascript:menuMainForward('99', '<%=footerUrl %>?p_process=Footer&p_code=FOOTER_PRIVACY')" style="color: #000; font-weight: 600;">개인정보처리방침</a></li>
					<li><a href="javascript:menuForward(7,'01');">아카데미 소개</a></li>
					<li><a href="javascript:menuForward(4,'01');">자주묻는 질문</a></li>
					<li style="border: none;"><a href="javascript:menuMainForward('99', '<%=footerUrl %>?p_process=Footer&p_code=FOOTER_EMAIL')">이메일무단수집거부</a></li>
				</ul>
				<p style="float: left; margin-left: 10px; margin-top: 20px; color: #908f8f; line-height: 16px; font-size: 11px;">
					한국콘텐츠아카데미 | 주소 : 서울특별시 동대문구 회기로 66<br />온라인 교육 02-6310-0770
				</p>
			</div>
			<div class="gray_right">
				<p style="margin-top: 40px;">
					<span style="color: #505050; margin-right: 5px;">Family site</span>
					<select name="fsite" style="color: #626262; font-size: 11px; height: 20px;">
						<option value="">관련사이트 바로가기</option>
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
				        <%  }	%>
					</select>
				</p>
			</div>
		</div>
	</div>
</div><%-- wrap end --%>