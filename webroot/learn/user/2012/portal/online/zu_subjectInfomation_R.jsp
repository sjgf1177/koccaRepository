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

    String grcode=box.getSession("tem_grcode");
	upperMap   = ClassifySubjectBean.getMenuId(box);
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<% if(grcode.equals("N000001")) {%>
<form name="form1" method="post">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "1">
    <input type = "hidden" name = "menuid">
    <input type="hidden" name="p_topuserid"	value=""/>
    <input type="hidden" name="p_toppwd"	value=""/>
    
    <!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal//online/stitle_06.gif" alt="전체교육과정" /></td>
        <td class="h_road">Home &gt; 온라인 과정  &gt; <strong>수강신청 안내</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
	  <tr>
        <td height="10" colspan="2"></td>
      </tr>

		<tr>
			<td colspan="2">
				<table>
					<tr>
						<td height="15"></td>
					</tr>
					<tr>
						<td><strong>1. 회원 가입 : 회원으로 가입하신 후 로그인합니다.</strong></td>
					</tr>
					<Tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_01.jpg" /> </td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>2. 온라인 과정</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_02.jpg" /></td>
					</tr>
					<tr>
						<td>- 수강신청을 위해 온라인 과정 > 리스트 페이지로 이동합니다.<br>
						- 과정명을 클릭하면 과정 안내 페이지로 이동합니다.</td>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>3. 온라인 과정 > 상세보기</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_03.jpg" /></td>
					</tr>
					<tr>
				        <td>- 온라인 과정에 대한 안내 페이지입니다.<br>
						- 수강신청을 하기 위해서 [수강신청]버튼을 클릭합니다.</td>
				    </tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<!--<tr>
						<td><strong>4. 온라인 과정 > 수강신청</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_04.jpg" /></td>
					</tr>
					<tr>
						<td>- 수강신청 화면입니다.<br>
						- 과정명 및 정보를 확인 하신 후 [장바구니 담기] 버튼을 클릭하면 과정 정보가 장바구니에 추가됩니다.<br>
						* <u>무료과정일 경우 [신청하기] 버튼을 클릭하시면 별도 설정 없이 수강신청이 완료됩니다.</u></td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>5. 나의 강의실 > 장바구니 > 신청목록 확인</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_05.jpg" /></td>
					</tr>
					<tr>
						<td>- 신청과정 정보 및 결제금액을 확인 후 [결제하기] 버튼을 클릭하시면 결제정보 확인 화면으로 이동됩니다.</td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>6. 나의 강의실 > 장바구니 > 결제정보 확인</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_06.jpg" /></td>
					</tr>
					<tr>
						<td>- 결제를 위한 화면입니다.<br>
						- 결제수단은 신용카드와 실시간계좌이체를 제공하고 있습니다.<br>
						- 결제수단을 선택하신 후 [결제하기] 버튼을 클릭하면 이니시스 결제 화면 창이 표시됩니다.</td>
					</tr>
					<tr>
						<td align="center"><img src="/images/portal/homepage_renewal//online/info_07.jpg" /></td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>7. 나의 강의실 > 장바구니 > 결제완료</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_08.jpg" /></td>
					</tr>
					<tr>
						<td>- 결제완료 화면입니다.<br>
						- [영수증 출력] 버튼을 클릭하면 영수증이 출력 팝업창이 표시됩니다.<br>
						- [수강신청 확인/취소] 버튼을 클릭하면 해당 메뉴로 이동합니다.</td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					--><tr>
						<td><strong>4. 나의 강의실 > 교육신청 확인/취소</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//online/info_09.jpg" /></td>
					</tr>
					<tr>
						<td>- 나의 강의실 - 교육신청 확인/취소 화면입니다.<br><!--
						- 현재 수강신청 승인상태 및 결제상태를 확인할 수 있습니다.<br>
						- 신청 및 결제만 완료된 경우에는 '승인대기'로 표시됩니다.<br>
						- 신청 및 결제, 운영자의 승인까지 완료된 경우에는 '승인'으로 표시됩니다.<br>
						&nbsp;&nbsp;&nbsp;&nbsp;(승인처리 된 경우만 수강중인 과정에 신청과정이 표시됩니다.)<br>
						- --><u>수강승인이 완료된 경우에 나의 강의실 - 수강중인 과정에 승인된 과정이 목록에 추가됩니다.</u><br>
						&nbsp;&nbsp;&nbsp;&nbsp;(<u>단, 학습은 교육시작일 이후에만 가능합니다.</u>)</td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>


				</table>
			</td>
		</tr>

    </table>
    </td>
    <td width="20">&nbsp;</td>
    <td valign="top">
    <!--타이틀부분//-->
    
    <!-- 
    
  <table width="690"  border="0" cellpadding="0" cellspacing="0">
  
  <tr>
    <td height="30"><strong>1. 회원 가입: 회원으로 가입하신 후 로그인합니다.</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img01.png" width="540" height="498" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>2. 온라인 과정</strong><br /></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img02.png" width="540" height="302" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 수강신청을 위해 온라인 과정 &gt; 리스트 페이지로 이동합니다.<br />
    - 과정명을 클릭하면 과정 안내 페이지로 이동합니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>3. 온라인 과정 &gt; 상세보기</strong><br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img03.png" width="540" height="322" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 온라인 과정에 대한 안내 페이지입니다.<br />
    - 수강신청을 하기 위해서 [수강신청] 버튼을 클릭합니다.</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>4. 온라인 과정 &gt; 수강신청</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img04.png" width="540" height="525" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 수강신청 화면입니다.<br />
      - 휴대전화 및 이메일을 입력한 후 [신청하기] 버튼을 클릭하면 수강신청이 완료됩니다. <br />
&nbsp;수강 신청한 과정정보는 장바구니에 추가됩니다.<br />
&nbsp;무료과정일 경우 별다른 설정 없이 과정수강신청이 완료됩니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>5. (무료과정일경우) 온라인 과정 &gt; 장바구니</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img05.png" width="540" height="273" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 무료과정일 경우에는 별다른 설정 없이 과정수강신청이 완료됩니다.<br />
    - 온라인 &gt; 나의강의실 홈에서 과정을 확인 하실 수 있습니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>6. (유료과정일 경우) 온라인 과정 &gt; 장바구니</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img06.png" width="540" height="399" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 하단에는 할인율이 적용된 실제 결제금액이 표시됩니다.<br />
- [결제하기] 버튼을 클릭하시면 결제정보 확인 화면으로 이동됩니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>7. 온라인 과정 &gt; 결제 확인
      <br />
    </strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img071.png" width="540" height="597" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 결제를 위한 화면입니다.<br />
      - 결제수단은 신용카드와 계좌이체를 제공하고 있습니다.<br />
    - 결제수단을 선택한 후 [결제하기] 버튼을 클릭하면 이니시스 결제 화면 창이 표시됩니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_online_img072.gif" width="364" height="350" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>8. 온라인 과정 &gt; 결제완료<br />
    </strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_online_img08.gif" width="551" height="332" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15"> - 결제 완료 화면입니다.<br />
      - [영수증 출력] 버튼을 클릭하면 영수증이 출력 팝업창이 표시됩니다.<br />
    - [수강신청확인/취소] 버튼을 클릭하면 해당 메뉴로 이동합니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>9. 나의 강의실 &gt; 수강신청확인/취소<br />
    </strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_online_img09.gif" width="551" height="364" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 나의강의실 &gt; 수강신청확인/취소 화면입니다.<br />
      - 현재 수강신청 승인상태 및 결제상태를 확인할 수 있습니다.<br />
      - 신청 및 결제만 완료된 경우에는 &lsquo;승인대기&rsquo;로 표시됩니다.<br />
      - 신청 및 결제, 관리자의 승인까지 완료된 경우에는 &lsquo;승인&rsquo;으로 표시됩니다.<br />
    &nbsp;&nbsp;(승인처리 된 경우에만 수강중인과정에 신청과정이 표시됩니다.)</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>10. 교육생 &gt; 나의강의실 &gt; 수강중인과정
      <br />
    </strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_online_img10.gif" width="543" height="363" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 수강승인이 완료된 경우에 나의강의실 &gt; 수강중인 과정에 승인된 과정이 목록에 추가됩니다.<br />
    &nbsp;&nbsp;(단, 학습은 교육시작일 이후에만 가능합니다.)</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
 -->

</form>
<!-- form 끝 -->
<%} else {%>


<form name="form1" method="post">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "1">
    <input type = "hidden" name = "menuid">

<table width="690"  border="0" cellpadding="0" cellspacing="0">

  <tr>
    <td height="30"><strong>1. 로그인후, 수강신청 메뉴로 이동 합니다.<br>&nbsp;&nbsp;&nbsp;과정명을 클릭하면 과정안내 페이지로 이동이 됩니다.</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img01.png" width="540" height="498"  /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>2. 과정에 대해 상세 안내 페이지 입니다.<br>&nbsp;&nbsp;&nbsp;[수강신청] 버튼을 클릭하여 수강신청을 합니다.</strong><br /></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img101.gif"  /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>3. 수강신청 화면 입니다.<br>&nbsp;&nbsp;&nbsp;휴대전화 및 이메일 주소를 확인후,[수강신청] 버튼을 클릭하면 수강신청이 완료 됩니다.</strong><br /></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img103.gif" width="551"  /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>4. 수강신청확인/취소 화면 입니다.<br>&nbsp;&nbsp;&nbsp;현재 수강신청 중인 과정을 확인할 수 있으며, 신청기간 중 수강취소를 할 수 있습니다.</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img104.gif"  /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>5. 학습기간이 시작되면 [나의 강의실] &gt; [수강중인과정]에서 수강신청 하신 과정을 볼 수 있습니다.<br>&nbsp;&nbsp;&nbsp;[학습하기]를 클릭하여 학습을 시작하실 수 있습니다.(단, 학습은 학습시작일 이후에만 가능합니다.)</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/online/sub_lecapp_online_img105.gif"  /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

</form>


<%}%>
<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


