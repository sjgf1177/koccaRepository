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
	
	upperMap   = ClassifySubjectBean.getMenuId(box);
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>


<form name="form1" method="post" action="">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "1">
    <input type = "hidden" name = "menuid">
    <br/>
    <br/>
    <!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/offline/stitle_08.gif" alt="월별교육 일정" /></td>
        <td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>수강신청 안내</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
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
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//offline/info_01.jpg" /> </td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>2. 오프라인 과정</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//offline/info_02.jpg" /></td>
					</tr>
					<tr>
						<td>- 수강신청을 위해 오프라인 과정 > 리스트 페이지로 이동합니다.<br>
						- 과정명을 클릭하면 과정 안내 페이지로 이동합니다.</td>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>3. 오프라인 과정 > 상세보기</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//offline/info_03.jpg" /></td>
					</tr>
					<tr>
				        <td>- 오프라인 과정에 대한 안내 페이지입니다.<br>
						- 수강신청을 하기 위해서 [수강신청]버튼을 클릭하면 수강신청 팝업창이 표시됩니다.</td>
				    </tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>4. 오프라인 과정 > 수강신청</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//offline/info_04.jpg" /></td>
					</tr>
					<tr>
						<td>- 수강신청 화면입니다.<br>
						- 기본 정보 및 자기소개서 부분을 작성한 후 [신청하기] 버튼을 클릭하면 신청이 진행됩니다.</td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>

					<tr>
						<td><strong>5. 나의 강의실 > 교육신청 확인/취소 > 오프라인과정</strong></td>
					</tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//offline/info_05.jpg" /></td>
					</tr>
					<tr>
						<td>- 운영자가 [1차 승인] 처리를 한 경우에 승인상태가 '1차 승인'으로 바뀐 것을 확인할 수 있습니다.<br>
						- [결제하기] 버튼을 클릭하면, 결제 화면이 표시됩니다.</td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>
					<tr>
						<td class="jin_table_Tright"><img src="/images/portal/homepage_renewal//offline/info_06.jpg" /></td>
					</tr>
					<tr>
						<td>- [결제하기] 버튼을 클릭하면, 이니시스 결제 팝업창이 나타납니다.</td>
					</tr>
					<tr>
				        <td height="15"></td>
				    </tr>
					<tr>
						<td align="center"><img src="/images/portal/homepage_renewal//offline/info_07.jpg" /></td>
					</tr>
					<tr>
						<td>- 결제가 완료되면 수강신청이 완료됩니다.</td>
					</tr>
				</table>
			</td>
		</tr>



    </table>
    <!--타이틀부분//-->
  <!--   
  <table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30"><strong>1. 회원 가입: 회원으로 가입하신 후 로그인합니다.</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_img01.png" width="540" height="498" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>2. 오프라인 과정 </strong><br /></td>
  </tr>
  <tr>
    <td class="pd_l15">- 수강신청을 위해 교육생 페이지의 오프라인과정으로 이동합니다.<br />
- 과정명을 클릭하면 과정상세 페이지로 이동합니다.<br />
- 리스트에서 [신청] 버튼을 클릭하면 수강신청 페이지 팝업이 표시됩니다.<br />
&nbsp;&nbsp;(단, 로그인 된 상태에서만 수강신청이 가능합니다.)</td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_img02.png" width="540" height="393" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>3. 과정 상세보기 / 수강신청</strong><br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_img03.png" width="540" height="429" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- [수강신청] 버튼을 클릭하면 아래와 같은 수강신청 팝업창이 표시된다.</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_img04.png" width="540" height="535" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
	<td class="pd_115">- 기본정보 및 자기소개서 부분을 작성한 후 [신청하기] 버튼을 클릭하면 신청이 완료됩니다.<br />
	&nbsp;&nbsp;&nbsp;(단, 화면에 표시되는 항목은 과정차수 등록 시 체크한 정보를 기초로 표시됩니다.)</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><strong>4. 나의강의실 &gt; 수강신청확인/취소 &gt; 오프라인 과정</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_img05.png" width="540" height="246" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15">- 관리자가 [1차승인] 처리를 한 경우에 나의강의실 &gt; 수강신청확인/취소 화면의 승인상태가 &lsquo;1차승인&rsquo;으로 <br />
&nbsp;&nbsp;바뀐 것을 확인할 수 있으며, [결제하기] 버튼이 화면에 표시됩니다.<br />
- [결제하기] 버튼을 클릭하면, 이니시스 결제 화면이 표시됩니다.</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_img06.png" width="540" height="580" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/offline/sub_lecapp_img07.png" width="274" height="260" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
 -->
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->