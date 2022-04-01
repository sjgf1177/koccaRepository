<%@ page import = "com.credu.homepage.LinkSiteBean" %>

<%  String footerUrl = "/servlet/controller.homepage.HomePageHelpServlet"; %>
<%
    String footerImg = "";
    if (s_tem_grcodeGLB.equals("N000001")) {
        footerImg=  "/images/newmain_20100625/footer_add.gif";
    } else {
        footerImg = "/images/portal/common/footer_add.gif";
    }   
%>

<%if( box.getSession("tem_grcode").equals("N000001")) { %>
<script type="text/javascript">
function goSiteSelect() {
    	var link1 = document.getElementById("select2").value;
    	var arr = link1.split(':');

    	if(arr[2] == "Y"){
    	
    		alert("로그인 후 이용해주세요"); 
    		return;
    	
    	}
    	window.open("/learn/user/homepage/zu_LinkSite_Action.jsp?p_grcode="+arr[0]+"&p_seq="+arr[1], "SiteLink", "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=700,height=480,left=80,top=100");
    }
</script>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td background="/images/portal/homepage_renewal/offline/footer_bg.jpg"><table width="995" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="22">&nbsp;</td>
      </tr>
      <tr>
        <td height="22" background="/images/portal/homepage_renewal/common/bg_btm.gif"><table width="995" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="112"><a href="javascript:menuForward('90','06')"><img src="/images/portal/homepage_renewal/common/btn_btm_01.gif" alt="개인정보처리방침" /></a></td>
            <td width="66"><a href="javascript:menuForward('90', '05')"><img src="/images/portal/homepage_renewal/common/btn_btm_02.gif" alt="이용약관" /></a></td>
            <td width="111"><a href="javascript:menuMainForward('99', '<%=footerUrl %>?p_process=Footer&p_code=FOOTER_EMAIL')"><img src="/images/portal/homepage_renewal/common/btn_btm_03.gif" alt="이메일 무단수집 거부" /></a></td>
            <td width="65"><a href="javascript:mainmenu('999');"><img src="/images/portal/homepage_renewal/common/btn_btm_04.gif" alt="사이트맵" /></a></td>
            <td><div align="right"><img src="/images/portal/homepage_renewal/common/txt_familysite.gif" alt="Family Site" /></div></td>
            <td width="150" style="padding-right:10px;"><div align="right">
              <select name="select2" id="select2" onChange="goSiteSelect()">
                <option>관련사이트바로가기</option> 
                <%
                    if(1==1) {
                        LinkSiteBean site = new LinkSiteBean();
	                    ArrayList<DataBox> bolist = site.SelectList(box);
	                    if(bolist != null && bolist.size() > 0) {
	                    for( int foot_i = 0 ; foot_i < bolist.size() ; foot_i++ ){
	                        DataBox footerbox = bolist.get(foot_i); %>
	                <option value='<%=footerbox.getString("d_grcode")%>:<%=footerbox.getInt("d_seq")%>:<%=footerbox.getString("d_islogin")%>'><%=footerbox.getString("d_sitenm")%></option>
	                <%  }} 
	                }%>
              </select>
            </div></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        			<td>
			        <table width="995" border="0" cellspacing="0" cellpadding="0">
        		<tr>
        			<td>
			        	<a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/portal/homepage_renewal/common/copyright.gif" alt="서울 양천구 목동동로 203-1 방송회관 8층 | 대표전화: 02-2161-0077(온라인 교육), 02-2161-0072(오프라인 교육) 팩스: 02-3219-5723 | 사업자등록번호: 117-82-04412 | 통신판매업신고번호: 양천 00471 한국콘텐츠진흥원(목동) 원장 이재웅 copyright (c) 2011  kocca  all rights reserved" /></a>
					</td>
					<td>
						<img src="http://edu.kocca.or.kr/contents/temp/i_safe_mark.gif" border="0" alt="I_Safe" style="cursor:hand" Onclick="javascript:window.open('http://edu.kocca.or.kr/contents/temp/i_safe.html','mark','scrollbars=no,resizable=no,width=700,height=965');">
					</td>
					<td>
						<img src="http://edu.kocca.or.kr/contents/temp/eprivacy_mark.gif" border="0" alt="ePrivacy" style="cursor:hand" Onclick="javascript:window.open('http://edu.kocca.or.kr/contents/temp/eprivacy.html','mark','scrollbars=no,resizable=no,width=700,height=965');">
					</td>
					<td>
						<img src="http://edu.kocca.or.kr/contents/temp/gsc_mark.jpg" border="0" alt="Good Content Service" style="cursor:hand" Onclick="javascript:window.open('http://edu.kocca.or.kr/contents/temp/gcs.html','mark','scrollbars=no,resizable=no,width=715,height=900');">
					</td>
					<td>
						<img src="https://mark.inicis.com/img/mark_new/inipoint/mark_circle_blue_s.gif" border="0" alt="클릭하시면 이니시스 결제시스템의 유효성을 확인하실 수 있습니다." style="cursor:hand" Onclick='javascript:window.open("https://mark.inicis.com/mark/popup_v1.php?no=19844&amp;st=1322725008","mark","scrollbars=no,resizable=no,width=530,height=670");'>
					</td>
				</tr>
			</table>
					</td>
				</tr>
    </table></td>
  </tr>
</table>

<%}else{ %>

<div id="footer">
	<div class="footer">
		<p class="logo"><a href="#" onclick="window.open('http://edu.kocca.or.kr'); return false;"><img src="/images/portal/common/footer_logo.gif" alt="kocca" /></a></p>

		<div class="copywarp">
			<ul>
				<li class="fir"><a href="javascript:menuMainForward('99', '<%=footerUrl %>?p_process=Footer&p_code=FOOTER_PRIVACY')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image82','','/images/asp/type1/footer_menu3_on.gif',1)"><img src="/images/asp/type1/footer_menu3_off.gif" alt="개인정보 처리방침" name="Image82" width="75" height="11" border="0" id="Image82" /></a></li>
				<li><a href="javascript:menuForward(7,'01');"><img src="/images/portal/common/footer_menu1_<%=gubun.equals("7") && menuid.equals("01") ? "on" : "off" %>.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="아카데미소개 " /></a></li>
				<li><a href="javascript:menuForward(4,'01');"><img src="/images/portal/common/footer_menu2_<%=gubun.equals("4") && menuid.equals("01") ? "on" : "off" %>.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="자주묻는질문 " /></a></li>
				<!--<li><a href="javascript:menuMainForward('99', '<%=footerUrl %>?p_process=Footer&p_code=FOOTER_PRIVACY')"><img src="/images/portal/common/footer_menu3_<%=box.getString("p_code").equals("FOOTER_PRIVACY") ? "on" : "off" %>.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="개인정보보호방침 " /></a></li>
				-->
				<!--<li><a href="javascript:menuMainForward('99', '<%=footerUrl %>?p_process=Footer&p_code=FOOTER_SERVICE')"><img src="/images/portal/common/footer_menu4_<%=box.getString("p_code").equals("FOOTER_SERVICE") ? "on" : "off" %>.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="이용약관 " /></a></li>
				--><li><a href="javascript:menuMainForward('99', '<%=footerUrl %>?p_process=Footer&p_code=FOOTER_EMAIL')">  <img src="/images/portal/common/footer_menu5_<%=box.getString("p_code").equals("FOOTER_EMAIL") ? "on" : "off" %>.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="이메일무단수집거부 " /></a></li>
				<li class="end"><a href="javascript:menuForward(4,'09');"><img src="/images/portal/common/footer_menu6_<%=gubun.equals("4") && menuid.equals("09") ? "on" : "off" %>.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="1:1문의 " /></a></li>
			</ul>
			<div class="familysite">
                <img src="/images/portal/common/footer_menu_familysite.gif" alt="Family Site" style="vertical-align:middle;"/>
                <select name="footer_relatedlink" id="footer_relatedlink" title="관련부서 사이트">
                    <option>관련부서 사이트</option>   
                    <%
                    if(1==1) {
                        LinkSiteBean site = new LinkSiteBean();
	                    ArrayList<DataBox> bolist = site.SelectList(box);
	                    if(bolist != null && bolist.size() > 0) {
	                    for( int foot_i = 0 ; foot_i < bolist.size() ; foot_i++ ){
	                        DataBox footerbox = bolist.get(foot_i); %>
	                <option value='<%=footerbox.getString("d_grcode")%>:<%=footerbox.getInt("d_seq")%>:<%=footerbox.getString("d_islogin")%>'><%=footerbox.getString("d_sitenm")%></option>
	                <%  }} 
	                }%>
                </select>
                <a href="javascript:topGoSiteSelect()"><img src="/images/portal/btn/btn_familysite_go.gif" alt="바로가기" /></a>
            </div>
			<p>
				<span class="copyright">
					<img src="<%=footerImg%>" alt="서울시 종로구 연견동 대학로 57 홍익대 아트센터 건물 1층 대표전화 : 02-2161-0077 팩스 : 02-2161-0078 | 사업자등록번호: 117-82-04412" class="mg_b4" /><br />
					<!-- <img src="/images/portal/common/footer_copyright.gif" alt="copyright &copy; 2001~2010  한국콘텐츠아카데미 (목동) 원장 이재웅 All Right Reserved" /> -->
				</span>
			</p>
		</div>
	</div>
</div>

<%} %>
