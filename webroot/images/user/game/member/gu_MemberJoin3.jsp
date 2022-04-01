<%@ page contentType="text/html; charset=euc-kr" %>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMember.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


		<!-- title -->
		<div style="width:720px;height:35px;margin-left:27px;margin-bottom:20px;background-image:url('/images/user/game/member/type1/tit_idpw.gif');text-align:right;padding:19px 25px 0px 0px;font-size:11px;color:#000000;">
			<img src="/images/user/game/member/type1/blt01.gif"> Home &gt; 회원가입
		</div>
		<!-- //title -->
		
		<div style="margin-left:27px;">
			<img src="/images/user/game/member/type1/join_i01.gif" align="left"><img src="/images/user/game/member/type1/join_i02.gif"><br>
			※ 소속란의 대학들은 콘텐츠보급 협약 체결된 대학으로 <font color=#cc6925>해당 재학생은 필히 소속을 선택</font><br>
			<font color=white>※ </font>하시기 바랍니다.<br>
			※ 그외 회원은 "일반회원"으로 선택해 주십시요.<br>
			<br>

			<img src="/images/user/game/member/type1/join3_st01.gif" vspace="0"><br>
			<table cellpadding=0 cellspacing=0 width=720>
			<col width=65><col width=65><col width=175><col width=95><col width=290>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 아이디</td>
				<td colspan=3 style=font-size:11px;color:#6d5952;>
					<input type=text class=type1 style=width:175> 
					<a href=#><img src=/images/user/game/member/type1/btn_chkid.gif align=absmiddle></a>
					ID는 4자이상 12자 이내의 영문소문자와 숫자만 가능합니다.
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 비밀번호</td>
				<td colspan=3 style=font-size:11px;color:#6d5952;>
					<input type=text class=type1 style=width:175> 
					비밀번호는 4자이상 12자이내로 만들어 주세요.
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 비밀번호확인</td>
				<td colspan=3>
					<input type=text class=type1 style=width:175> 
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 이름(한글)</td>
				<td style=font-size:11px>
					<input type=text class=type1 style=width:175> 
				</td>
				<td align=center>이름(영문)</td>
				<td style=font-size:11px>
					<input type=text class=type1 style=width:175>
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=4><td colspan=5></td></tr>
			<tr height=26>
				<td style="padding-left:10;border-right:1px solid #dcd8d7" rowspan=4><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 주소</td>
				<td align=center>우편번호</td>
				<td style=font-size:11px;color:#6d5952; colspan=3>
					<input type=text class=type1 style=width:175> 
					<a href=#><img src=/images/user/game/member/type1/btn_zipcode.gif align=absmiddle></a>
					직장주소/집주소 중 하나는 꼭 입력하셔야만 됩니다.
				</td>
			</tr>
			<tr height=26>
				<td align=center>자택주소</td>
				<td colspan=3>
					<input type=text class=type1 style=width:400> 
					<a href=#><img src=/images/user/game/member/type1/btn_deladd.gif></a>
				</td>
			</tr>
			<tr height=26>
				<td align=center>우편번호</td>
				<td colspan=3>
					<input type=text class=type1 style=width:175> 
					<a href=#><img src=/images/user/game/member/type1/btn_zipcode.gif></a>
				</td>
			</tr>
			<tr height=26>
				<td align=center>직장주소</td>
				<td colspan=3>
					<input type=text class=type1 style=width:400> 
					<a href=#><img src=/images/user/game/member/type1/btn_deladd.gif></a>
				</td>
			</tr>
			<tr height=4><td colspan=5></td></tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> E-Mail</td>
				<td colspan=3>
					<input type=text class=type1 style=width:175> 
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 소속</td>
				<td colspan=3>
					<input type=text class=type1 style=width:175> 
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 직업</td>
				<td colspan=3>
					<select name="" style="width:100;">
					<option>학생</option>
					</select>
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 최종학력</td>
				<td colspan=3>
					<select name="" style="width:100;">
					<option>중졸</option>
					</select>
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:10; colspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle>핸드폰</td>
				<td colspan=3>
					<select name="" style="width:80;">
					<option>010</option>
					</select>
					-
					<input type=text class=type1 style=width:100> 
					-
					<input type=text class=type1 style=width:100> 
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=4><td colspan=5></td></tr>
			<tr height=26>
				<td style="padding-left:10;border-right:1px solid #dcd8d7" rowspan=2><img src=/images/user/game/member/type1/blt02.gif align=absmiddle> 전화</td>
				<td align=center>자택</td>
				<td colspan=2>
					<input type=text class=type1 style=width:175> 
				</td>
				<td style=font-size:11px;color:#6d5952; rowspan=2>
					2개중 연락처 1개는 반드시 기재바랍니다.<br>
					예)02-540-0000<br>
				</td>
			</tr>
			<tr height=26>
				<td align=center>직장</td>
				<td colspan=2>
					<input type=text class=type1 style=width:175> 
				</td>
			</tr>
			<tr height=4><td colspan=5></td></tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			</table>

			<table cellpadding=0 cellspacing=0 width=720>
			<col width=130><col width=590>
			<tr height=10><td colspan=2></td></tr>
			<tr height=15><td colspan=2 style="border-top:5px solid #dfd8d2;border-right:5px solid #dfd8d2;border-left:5px solid #dfd8d2;">&nbsp;</td></tr>
			<tr height=26>
				<td style=padding-left:20;>메일 수신여부</td>
				<td>
					<input type=radio name=radio1> 수신 
					<input type=radio name=radio1> 수신거부 
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:20;>뉴스레터 수신여부</td>
				<td>
					<input type=radio name=radio1> 수신 
					<input type=radio name=radio1> 수신거부 
				</td>
			</tr>
			<tr height=1><td class=lineColor colspan=5></td></tr>
			<tr height=26>
				<td style=padding-left:20;>연락처 공개여부</td>
				<td>
					<input type=radio name=radio1> 공개 
					<input type=radio name=radio1> 비공개 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span  style=font-size:11px;color:#6d5952;>(학습자 조회시 동료학생들에게 전화번호 email, 주소를 공개합니다.)</span>
				</td>
			</tr>
			<tr height=15><td colspan=2 style="border-bottom:5px solid #dfd8d2;border-right:5px solid #dfd8d2;border-left:5px solid #dfd8d2;">&nbsp;</td></tr>
			<tr height=15><td colspan=2></td></tr>
			<tr>
				<td colspan=2 align=center>
					<a href=#><img src=/images/user/game/member/type1/btn_join.gif></a>
					<a href=#><img src=/images/user/game/member/type1/btn_joincancel.gif></a>
				</td>
			</tr>
			</table>

		</div>
		<br>
		<br>



<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
