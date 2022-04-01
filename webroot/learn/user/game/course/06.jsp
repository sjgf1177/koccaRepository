<%
//**********************************************************
//  1. 제      목: 과정 > 학습위계
//  2. 프로그램명 : gu_subjectIntro.jsp
//  3. 개      요: 학습위계
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
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
	box.put("leftmenu","02");

    String  v_process     = box.getString("p_process");
	String  v_gubun		  = box.getStringDefault("p_gubun","1");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT>
var old_menu = ''; var old_cell = '';
function comment( submenu, cellbar) {

	if( old_menu != submenu ) {

		if( old_menu !='' ) {
			old_menu.style.display = 'none';
		}
		submenu.style.display = 'block';
		old_menu = submenu;
		old_cell = cellbar;

	} else {
		submenu.style.display = 'none';
		old_menu = '';
		old_cell = '';
	}
}


function tabmove(tab){
      document.form1.action='/servlet/controller.course.CourseIntroServlet';
	  document.form1.p_gubun.value = tab;
      document.form1.p_process.value = 'SubjectPage';
      document.form1.submit();
}

// 과정 내용보기
/*
function whenSubjInfo(subj){
    document.form1.p_subj.value     = subj;
    document.form1.p_process.value  = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectPage';
    document.form1.action='/servlet/controller.course.CourseIntroServlet';
    document.form1.target = "_self";
    document.form1.submit();
}
*/
// 과정 내용보기
function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm){
    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_iscourseYn.value = courseyn;
    document.form1.p_upperclass.value = upperclass;
    document.form1.p_upperclassnm.value = upperclassnm;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.target = "_self";
    document.form1.submit();
}
</SCRIPT>

<style type="text/css">
<!--
.l-padding {
	padding-left: 12px;
}
-->
</style>

<form name="form1" method="post">
	<input type="hidden" name="p_gubun" value="<%=v_gubun%>">
	<input type="hidden" name="p_subj">
	<input type="hidden" name="p_subjnm">
	<input type="hidden" name="p_iscourseYn">
	<input type="hidden" name="p_upperclass">
	<input type="hidden" name="p_upperclassnm">
	<input type="hidden" name="p_process">
	<input type="hidden" name="p_rprocess">



<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="right"  background="/images/user/game/course/<%=tem_subimgpath%>/tit_learncourse.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
      HOME > 과정안내 > 학습위계</td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
</table>
<!-- 학습위계 -->

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" style="padding-right:17px">
    <a href="javascript:tabmove(1)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image201','','/images/user/game/course/tab_learn1on_01.gif',1)"><img src="/images/user/game/course/tab_learn1_01.gif" name="Image201" border="0"></a><img src="/images/user/game/course/tab_learn2on_01.gif"><a href="javascript:tabmove(3)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image203','','/images/user/game/course/tab_learn3on_01.gif',1)"><img src="/images/user/game/course/tab_learn3_01.gif" name="Image203" border="0"></a></td>
  </tr>
   <tr>
    <td align="center">
    	<table border="0" cellpadding="0" cellspacing="0" width="688">
    		<tr>
    			<td bgcolor="6A93B3" height="2"></td>
    		</tr>
    	</table>
    </td>
  </tr>
  <tr style="padding-top:15px;padding-right:25px">
    <td align="right">
    <img src="/images/user/game/course/dgn_btn_0102.gif">
<a href="/learn/user/game/course/07.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image102','','/images/user/game/course/dgn_btn_0202.gif',1)"><img src="/images/user/game/course/dgn_btn_0201.gif" name="Image102" border="0"></a>
<a href="/learn/user/game/course/08.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image103','','/images/user/game/course/dgn_btn_0302.gif',1)"><img src="/images/user/game/course/dgn_btn_0301.gif" name="Image103" border="0"></a>
<a href="/learn/user/game/course/09.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image104','','/images/user/game/course/dgn_btn_0402.gif',1)"><img src="/images/user/game/course/dgn_btn_0401.gif" name="Image104" border="0"></a>
</td>
  </tr>
</table>
<!-- 1 -->
<table border="0" cellpadding="0" cellspacing="0" width=720">
	<tr>
		<td align="center" style="padding-top:15px;padding-bottom:20px">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><img src="/images/user/game/course/bg_top.gif"></td>
				</tr>
				<tr>
					<td background="/images/user/game/course/bg.gif" height="100%">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="240" valign="top" style="padding-left:10;padding-top:0"><img src="/images/user/game/course/dgn_title01.gif"></td>
								<td width="388">
								<!--리스트-->
									<table border="0" cellpadding="0" cellspacing="0" width="388">
										<!--공통과정-->
										<tr height="30">
											<td colspan="2"><img src="/images/user/game/course/common_icon03.gif" align="absmiddle"> <b>공통 과정</b></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px" width="230px"><img src="/images/user/game/course/num_01.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000019','게임문화론','ON','G01','게임아카데미')">게임문화론</a></td>
											<td><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_02.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000074','게임산업연구1','ON','G01','게임아카데미')">게임산업연구 1</a></td>
											<td><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_03.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000092','게임산업연구2','ON','G01','게임아카데미')">게임산업연구 2</a></td>
											<td><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_04.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000094','게임경영학','ON','G01','게임아카데미')">게임경영학</a></td>
											<td><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_05.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000021','게임심리학','ON','G01','게임아카데미')">게임심리학</a></td>
											<td><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>

										<!--공통과정-->
										<tr height="30" style="padding-top:20px;padding-bottom:5px">
											<td colspan="2"><img src="/images/user/game/course/common_icon03.gif" align="absmiddle"> <b>게임분석</b></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_01.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('001200','게임분석론1(New)','ON','G01','게임아카데미')">게임분석론1(New)</a></td>
											<td><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_02.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('003203','게임분석론2(new)','ON','G01','게임아카데미')">게임분석론2(new)</a></td>
											<td><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>


										<!--1-->
										<tr height="30" style="padding-top:20px;padding-bottom:5px">
											<td colspan="2"><img src="/images/user/game/course/common_icon03.gif" align="absmiddle"> <b>게임기획자를 위한 게임프로그램</b></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_01.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000065','게임아키텍처의 이해1','ON','G01','게임아카데미')">게임아키텍처의 이해 1</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_02.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000095','게임아키텍처의 이해2','ON','G01','게임아카데미')">게임아키텍처의 이해 2</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_03.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000093','게임인공지능연구','ON','G01','게임아카데미')">게임인공지능연구</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_04.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000023','윈도우 프로그래밍','ON','G01','게임아카데미')">윈도우 프로그래밍</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_05.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000070','게임기술개론','ON','G01','게임아카데미')">게임기술개론</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>

										<!--2-->
										<tr height="30" style="padding-top:20px;padding-bottom:5px">
											<td colspan="2"><img src="/images/user/game/course/common_icon03.gif" align="absmiddle"> <b>게임기획자를 위한 게임그래픽</b></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px" colspan="2"><img src="/images/user/game/course/num_01.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('003103','기획자가 알아야 할 2D 게임그래픽(new)','ON','G01','게임아카데미')">기획자가 알아야 할 2D 게임그래픽(new)</a></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px" colspan="2"><img src="/images/user/game/course/num_02.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('003207','기획자가 알아야 할 3D 게임그래픽(new)','ON','G01','게임아카데미')">기획자가 알아야 할 3D 게임그래픽(new)</a></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>

										<!--3-->
										<tr height="30" style="padding-top:20px;padding-bottom:5px">
											<td colspan="2"><img src="/images/user/game/course/common_icon03.gif" align="absmiddle"> <b>레벨디자인</b></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_01.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000105','레벨디자인','ON','G01','게임아카데미')">레벨디자인</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_02.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000015','저작툴을 이용한 게임제작','ON','G01','게임아카데미')">저작툴을 이용한 게임제작</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>

										<!--4-->
										<tr height="30" style="padding-top:20px;padding-bottom:5px">
											<td colspan="2"><img src="/images/user/game/course/common_icon03.gif" align="absmiddle"> <b>게임설계</b></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_01.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('003200','게임디자인1(New)','ON','G01','게임아카데미')">게임디자인1(New)</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_02.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('003204','게임디자인2(new)','ON','G01','게임아카데미')">게임디자인2(New)</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_03.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000065','게임아키텍처의 이해1','ON','G01','게임아카데미')">게임아키텍처의 이해 1</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:30px"><img src="/images/user/game/course/num_04.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000095','게임아키텍처의 이해2','ON','G01','게임아카데미')">게임아키텍처의 이해 2</a></td>
											<td></td>
										</tr>
										<tr>
											<td align="right" colspan="2"><img src="/images/user/game/course/point_bg01.gif"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><img src="/images/user/game/course/bg_bottom.gif"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</form>



<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
