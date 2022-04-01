<script language="javascript">

function goLoginAll() {
	document.getElementById('logM').style.display = "none";
	document.getElementById('logG').style.display = "block";
}
</script>		

		<div id="aside">
			<% if(topWarp_userid.equals("")) { %>
			<div id="logM" class=common_login>
				<a href="javascript:ssoLogin();"><img src="/images/portal/asp/btn_common_login.gif" alt="통합회원 로그인" /></a>
				<p class="search_btn">
					<a href="javascript:ssoJoin();"><img src="/images/portal/asp/btn_comjoin.gif" alt="통합회원가입" /></a>
					<a href="javascript:ssoLosspwd();"><img src="/images/portal/asp/btn_comsearch.gif" alt="통합 ID/PW찾기" /></a>
					<a href="#"><img src="/images/portal/asp/btn_orsearch.gif" alt="기존 ID/PW찾기" /></a>
				</p>
			</div>

			<div id="logG" class="login_box" style="display:none;">
				<dl>
					<dt><img src="/images/portal/asp/login_tit.gif" alt="Member LOGIN" /></dt>
					<dd><input type="text" class="inbox" value="아이디" name="p_userid" id="userid" onChange="$('#p_topuserid').val(this.value);" onkeypress="moveFocus(event,'p_pwd');$('#passwd').val('');" onClick="this.value=''"/><br />
					<input type="password" class="inbox" value="패스워드" name="p_pwd" id="passwd" onChange="$('#p_toppwd').val(this.value);" onkeypress="fnKeyPressEnter(event, 'topLogin');" onClick="this.value=''"/></dd>
					<dd class="btn"><a href="javascript:topLogin();"><img src="/images/portal/asp/btn_login.gif" alt="로그인" /></a></dd>
				</dl>
				<p class="search_btn">
					<a href=""><img src="/images/portal/asp/btn_idpwsearch.gif" alt="아이디 / 패스워드 찾기" /></a>
					<a href=""><img src="/images/portal/asp/btn_joining.gif" alt="회원가입하기" /></a>
				</p>
			</div>
			<% } else {%>
			<div id="logO" class="logout_box">
				<dl>
					<dt><img src="/images/portal/asp/login_tit.gif" alt="Member LOGIN" /></dt>
					<dd class="txt"><%=topWarp_usernm %>님<br />접속하셨습니다</dd>
					<dd class="btn"><a href=""><img src="/images/portal/asp/btn_logout.gif" alt="로그아웃" /></a></dd>
				</dl>
				<p class="search_btn">
					<a href="javascript:topMemberInfoModify();"><img src="/images/portal/asp/btn_indiedit.gif" alt="개인정보변경하기" /></a>
					<a href="javascript:topLogout();"><img src="/images/portal/asp/btn_withdrawal.gif" alt="회원탈퇴하기" /></a>
				</p>
			</div>
			<%  }  %>
			<p class="banner">
				<a href="javascript:menuForward('4','01');"><img src="/images/portal/asp/banner_1.gif" alt="자주묻는질문" /></a>
				<a href="javascript:menuForward('1','11');"><img src="/images/portal/asp/banner_2.gif" alt="원간교육일정" /></a>
				<a href="javascript:menuForward('1','13');"><img src="/images/portal/asp/banner_3.gif" alt="수강신청안내" /></a>
			</p>

			<dl class="menu">
				<dt><img src="/images/portal/asp/menu_tit.gif" alt="자주찾는 메뉴" /></dt>
				<dd class="ty1">
					<a href="javascript:menuForward('1','01');"><img src="/images/portal/asp/menu_1_off.gif" alt="교육과정안내" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
					<a href="javascript:menuForward('3','11');"><img src="/images/portal/asp/menu_2_off.gif" alt="수강중인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
					<a href="javascript:menuForward('5','03');"><img src="/images/portal/asp/menu_3_off.gif" alt="교육자료실" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
				</dd>
				<dd class="ty2">
					<a href="javascript:menuForward('4','06');"><img src="/images/portal/asp/menu_4_off.gif" alt="온라인메뉴얼" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
					<a href="javascript:menuForward('3','17');"><img src="/images/portal/asp/menu_5_off.gif" alt="수강신청확인/취소" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
					<a href="javascript:menuForward('5','06');"><img src="/images/portal/asp/menu_6_off.gif" alt="수강생갤러리" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
				</dd>
				<dd class="ty3">
					<a href="javascript:menuForward('1','12');"><img src="/images/portal/asp/menu_7_off.gif" alt="연간교육일정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
					<a href="javascript:menuForward('3','12');"><img src="/images/portal/asp/menu_8_off.gif" alt="나의상담내역" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
					<a href="javascript:menuForward('5','08');"><img src="/images/portal/asp/menu_9_off.gif" alt="교육후기" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
				</dd>
			</dl>

			<dl class="eduservice">
				<dt><img src="/images/portal/asp/eduservice_tit.gif" alt="교육문의 서비스" /></dt>
				<dd><img src="/images/portal/asp/eduservice.gif" alt="TEL 02-2345-6789
FAX 02-2345-6789 
과정이나 사이트 이용안내 관련하여 친절히 상담해 드립니다.
운영시간: 평일 09:00~18:00" /></dd>
				<!-- <dd>
					<img src="/images/portal/asp/eduservice_num.gif" alt="TEL 02-2345-6789
FAX 02-2345-6789" />
					<img src="/images/portal/asp/eduservice_txt.gif" class="txt" alt="과정이나 사이트 이용안내 관련하여 친절히 상담해 드립니다." />
					<img src="/images/portal/asp/eduservice_time.gif" class="time" alt="운영시간: 평일 09:00~18:00" />
				</dd> -->
			</dl>
		</div>