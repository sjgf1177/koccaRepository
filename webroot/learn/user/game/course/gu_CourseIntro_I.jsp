<%
//**********************************************************
//  1. 제      목: 과정안내
//  2. 프로그램명: gu_CourseIntro_L.jsp
//  3. 개      요: 과정안내 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.12.14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process     = box.getString("p_process");
    String  v_iscourseYn  = box.getStringDefault("p_iscourseYn","N");
    String v_tabnum       = box.getStringDefault("p_tabnum","design");

    String  v_code       = "";
    String  v_codenm     = "";
    String  v_tab_bg     = "";
    String  v_tab_class  = "";

    String v_subj           = "";
    String v_subjnm         = "";
    String v_upperclass     = "";
    String v_middleclass    = "";
    String v_sclowerclass   = "";
    String v_isonoff        = "";
    String v_usebook        = "";
    String v_isnew          = "";
    String v_ishit          = "";
    String v_isrecom        = "";
    String v_uclassnm       = "";
    String v_mclassnm       = "";

    String v_imgbook        = "";
    String v_imgNew         = "";
    String v_imgHit         = "";
    String v_imgRecom       = "";
    double v_sul_avg = 0; // 과정 만족도 

    double tmp_sul_avg = 0;
    String v_imgStart = "";

    ArrayList list1      = null;
    list1 = (ArrayList)request.getAttribute("SubjectList");         // 과정 리스트

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
// 일반/전문가, 대분류코드 선택
function whenSelect(sel,gubun){
    document.form1.p_iscourseYn.value = sel;
    document.form1.p_tabnum.value   = gubun;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.course.CourseIntroServlet';
    document.form1.p_process.value = 'SubjectList';

    document.form1.submit();
}

// 과정 내용보기
function whenSubjInfo(subj,tabnum){
    document.form1.p_subj.value     = subj;
    document.form1.p_tabnum.value   = tabnum;
    document.form1.p_process.value  = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.course.CourseIntroServlet';
    document.form1.target = "_self";
    document.form1.submit();
}
//-->
</SCRIPT>

<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_iscourseYn' value="<%=v_iscourseYn%>">
    <input type='hidden' name='p_tabnum'  value="<%=v_tabnum%>">
    <input type='hidden' name='p_subj' value="">




<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="right"  background="/images/user/game/course/<%=tem_subimgpath%>/tit_eduintro.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> HOME > 과정안내 > 교육과정소개 </td>
  </tr>
  <tr style="padding-top:30px"> 
    <td align="center"><img src="/images/user/game/course/intro_img.gif"></td>
  </tr>
  <tr>
  	<td align="center">
  		<table border="0" cellpadding="0" cellspacing="0" background="/images/user/game/course/intro_bg01.gif" width="665" height="122">
  			<tr>
  				<td align="right" valign="bottom" style="padding: 0 10 13 0">
  					<table border="0" cellpadding="0" cellspacing="0" width="490" height="65">
  						<tr>
  							<td valign="top">학습위계를 통해 원하는 커리큘럼을 찾아볼 수 있습니다.<br>각 분야마다 순차적인 학습을 통해 학습의 효율성을 높일 수 있습니다.</td>
  						</tr>
  						<tr>
  							<td align="right" height="16"><a href="/servlet/controller.course.CourseIntroServlet?p_process=SubjectPage"><img src="/images/user/game/course/go.gif" border="0"></a></td>
  						</tr>
  					</table>
  				</td>
  			</tr>
  		</table>
  	</td>
  </tr>
  <tr style="padding: 10 0 0 0">
  	<td align="center">
  		<table border="0" cellpadding="0" cellspacing="0" background="/images/user/game/course/intro_bg02.gif" width="665" height="122">
  			<tr>
  				<td align="right" valign="bottom" style="padding: 0 10 13 0">
  					<table border="0" cellpadding="0" cellspacing="0" width="490" height="65">
  						<tr>
  							<td valign="top">사이버게임아카데미의 전체 과정을 한번에 볼 수 있습니다.<br>과정의 전체 리스트를 통해 원하는 과정을 선택하세요.</td>
  						</tr>
  						<tr>
  							<td align="right" height="16"><a href="/servlet/controller.course.CourseIntroServlet?p_process=SubjectList"><img src="/images/user/game/course/go.gif" border="0"></a></td>
  						</tr>
  					</table>
  				</td>
  			</tr>
  		</table>
  	</td>
  </tr>
</table>

</form>
<map name="Map" id="Map20">
    <area shape="rect" coords="3,2,120,25"   href="#" onclick="whenSelect('N','design');" />
    <area shape="rect" coords="122,2,232,27" href="#" onclick="whenSelect('N','program');" />
    <area shape="rect" coords="234,2,340,28" href="#" onclick="whenSelect('N','graphic');" />
    <area shape="rect" coords="345,2,450,27" href="#" onclick="whenSelect('N','common');" />
</map>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
