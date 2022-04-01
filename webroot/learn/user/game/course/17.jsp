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
    <a href="javascript:tabmove(1)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image201','','/images/user/game/course/tab_learn1on_01.gif',1)"><img src="/images/user/game/course/tab_learn1_01.gif" name="Image201" border="0"></a><a href="javascript:tabmove(2)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image202','','/images/user/game/course/tab_learn2on_01.gif',1)"><img src="/images/user/game/course/tab_learn2_01.gif" name="Image202" border="0"></a><img src="/images/user/game/course/tab_learn3on_01.gif"></td>
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
    <a href="/learn/user/game/course/10.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image101','','/images/user/game/course/grp_btn_0102.gif',1)"><img src="/images/user/game/course/grp_btn_0101.gif" name="Image101" border="0"></a>
<a href="/learn/user/game/course/11.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image102','','/images/user/game/course/grp_btn_0202.gif',1)"><img src="/images/user/game/course/grp_btn_0201.gif" name="Image102" border="0"></a>
<a href="/learn/user/game/course/12.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image103','','/images/user/game/course/grp_btn_0302.gif',1)"><img src="/images/user/game/course/grp_btn_0301.gif" name="Image103" border="0"></a>
<a href="/learn/user/game/course/13.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image104','','/images/user/game/course/grp_btn_0402.gif',1)"><img src="/images/user/game/course/grp_btn_0401.gif" name="Image104" border="0"></a>
<a href="/learn/user/game/course/14.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image105','','/images/user/game/course/grp_btn_0502.gif',1)"><img src="/images/user/game/course/grp_btn_0501.gif" name="Image105" border="0"></a>
<a href="/learn/user/game/course/15.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image106','','/images/user/game/course/grp_btn_0602.gif',1)"><img src="/images/user/game/course/grp_btn_0601.gif" name="Image106" border="0"></a>
<a href="/learn/user/game/course/16.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image107','','/images/user/game/course/grp_btn_0702.gif',1)"><img src="/images/user/game/course/grp_btn_0701.gif" name="Image107" border="0"></a>
<img src="/images/user/game/course/grp_btn_0802.gif">
<a href="/learn/user/game/course/18.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image109','','/images/user/game/course/grp_btn_0902.gif',1)"><img src="/images/user/game/course/grp_btn_0901.gif" name="Image109" border="0"></a>
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
								<td width="240" valign="top" style="padding-left:10;padding-top:0"><img src="/images/user/game/course/grp_title08.gif"></td>
								<td width="388">
								<!--리스트-->
									<table border="0" cellpadding="0" cellspacing="0" width="388">
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_01.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000075','기초드로잉','ON','G01','게임아카데미')">기초드로잉</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px" width="220px"><img src="/images/user/game/course/num_02.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000076','색채학1','ON','G01','게임아카데미')">색채학1</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_03.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000035','조형학','ON','G01','게임아카데미')">조형학</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_04.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000036','색채학2','ON','G01','게임아카데미')">색채학2</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_05.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000032','컨셉드로잉','ON','G01','게임아카데미')">컨셉드로잉</td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_06.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000031','컨셉디자인1','ON','G01','게임아카데미')">컨셉디자인1</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_07.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('004300','3D그래픽 제작 실무(new)','ON','G01','게임아카데미')">3D그래픽 제작 실무(new)</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_08.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('001100','아이디어발상(New)','ON','G01','게임아카데미')">아이디어발상(New)</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_09.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('004200','3DMax 이해하기(new)','ON','G01','게임아카데미')">3DMax 이해하기(new)</a></td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<!--
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_10.gif" align="absmiddle"> &nbsp; 3D CG(디)</td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_11.gif" align="absmiddle"> &nbsp; 2D CG(디)</td>
											<td width="168px"><img src="/images/user/game/course/common_icon01.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										
										-->
										
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_10.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000037','영상/조명학','ON','G01','게임아카데미')">영상/조명학</a></td>
											<td width="168"><img src="/images/user/game/course/common_icon02.gif" align="absmiddle"></td>
										</tr>
										<tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
										</tr>
										<tr height="24">
											<td style="padding-left:10px"><img src="/images/user/game/course/num_11.gif" align="absmiddle"> &nbsp; <a href="javascript:whenSubjInfo('000065','게임아키텍처의 이해1','ON','G01','게임아카데미')">게임아키텍쳐의 이해1</a></td>
											<td width="168"><img src="/images/user/game/course/common_icon02.gif" align="absmiddle"></td>
										</tr>
										<tr>
											<td colspan="2"><img src="/images/user/game/course/point_bg.gif"></td>
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
