<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import = "com.credu.homepage.LinkSiteBean" %>
<%@ page import = "com.credu.homepage.LoginBean" %>
<head>
<title>한국콘텐츠진흥원 아카데미</title>
<style type="text/css" media="screen">@import "/css/2012/common.css";</style>
<style type="text/css" media="screen">@import "/css/2012/main.css";</style>
<script type="text/javascript" src="/js/jquery/1.4/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="/js/2012/lib.js"></script>
<script language="javascript" type="text/javascript" src="/js/2012/jquery.banner.js"></script>
<script language="javascript" src="/script/mainscript.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Content-Language" content="euc-kr">
<meta http-equiv="imagetoolbar" content="false">
<meta name="robots" content="all">
<meta name="MSSmartTagsPreventParsing" content="true">
<meta name="description" content="">
</head>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    ArrayList<DataBox> noticeListTop = (ArrayList) request.getAttribute("noticeListTop"); // 공지사항    
    ArrayList<DataBox> noticeListPopup = (ArrayList) request.getAttribute("noticePopup");// 팝업 공지사항
    
    ArrayList<DataBox> onLineSubjList  = (ArrayList)request.getAttribute("onLineSubjList");// 온라인 인기과정    
    ArrayList<DataBox> onLineGameSubjList  = (ArrayList)request.getAttribute("onLineGameSubjList");// 게임
    ArrayList<DataBox> onLineBroadSubjList  = (ArrayList)request.getAttribute("onLineBroadSubjList");// 방송
    ArrayList<DataBox> onLineCultureSubjList  = (ArrayList)request.getAttribute("onLineCultureSubjList");// 문화
    
    ArrayList<DataBox> DimensionOffSubjectList  = (ArrayList)request.getAttribute("DimensionOffSubjectList");// 3D 입체콘텐츠
    ArrayList<DataBox> IndustryOffSubjectList  = (ArrayList)request.getAttribute("IndustryOffSubjectList");// 산업계
    ArrayList<DataBox> NextOffSubjectList  = (ArrayList)request.getAttribute("NextOffSubjectList");// 차세대
    ArrayList<DataBox> SuTakOffSubjectList  = (ArrayList)request.getAttribute("SuTakOffSubjectList");// 수탁교육
    
    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");
    
    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>

    upperMap   = ClassifySubjectBean.getMenuId(box);
   
%>    
<script type="text/javascript">
/* 패스워드 엔터키 체크 */
   function password_enter(e) {
        if (e.keyCode =='13'){  login_main();  }
    }

    // 로그인
    function login_main() {    	
        if (input_chk()){       
        	//alert('test');
            document.login.p_process.value   = "login";
            //form1.p_topuserid.value = form1.p_userid.value;
            //form1.p_toppwd.value =  form1.p_pwd.value;

            document.login.action            = "/servlet/controller.homepage.LoginServlet";
            document.login.submit();
        } else {
        	return;
        }
    }
    
    function input_chk()  {    	    	
        if (document.getElementById('p_userid').value == "" || document.getElementById('p_userid').value == null )
        {
            document.login.p_userid.value="";  //폼창에 NULL값 리턴
            alert( "아이디을 입력하십시요.");
            document.login.p_userid.focus();
            return(false);
        }
        if (document.getElementById('p_pwd').value == "" || document.getElementById('p_pwd').value == null )
        {
            document.login.p_pwd.value="";  //폼창에 NULL값 리턴
            alert( "비밀번호 입력하십시요.");
            document.login.p_pwd.focus();
            return(false);
        }
        return(true);
    }
    
    function logout() {
	    document.form1.p_process.value = "gologout";
	    document.form1.action          = "/servlet/controller.homepage.LoginServlet";
	    document.form1.submit();
    }
    
 // 공지사항 상세보기
    function viewNotice(seq) {
       document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
       document.form1.p_process.value = "selectView";
       document.form1.menuid.value = "07";
       document.form1.gubun.value = "4";
       document.form1.p_seq.value= seq;
       document.form1.target = "_self";
       document.form1.submit();
    }
    
    function view_On_Subj(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, menuid){
        var p_process="";
        var action="";
        var gubun="";
        if(courseyn=="ON")
        {
            p_process="SubjectPreviewPage";
            action="/servlet/controller.propose.ProposeCourseServlet";
            gubun="1";
        }
        else
        {
            p_process="SubjectPreviewPage";
            action="/servlet/controller.off.ProposeOffServlet";
            gubun="2";
        }

	    document.form1.p_subj.value = subj;
	    document.form1.p_subjnm.value = subjnm;
	    document.form1.p_iscourseYn.value = courseyn;
	    document.form1.p_upperclass.value = upperclass;
	    document.form1.p_upperclassnm.value = upperclassnm;
	    document.form1.p_year.value = year;
	    document.form1.p_subjseq.value = subjseq;
        $("[name='p_seq']").val(upperclassnm);
	    document.form1.menuid.value = menuid;
	    document.form1.gubun.value = gubun;
	    document.form1.p_process.value = "SubjectPreviewPage";
	    document.form1.p_rprocess.value = 'SubjectList';
	    document.form1.action=action;
	    document.form1.target = "_self";
	    document.form1.submit();
    }
    
    
  //전체 과정검색
    function totalSubjSearch() {
        var searchText = $("#topWarp_searchText").val();
         var gubun   = "1";
         var url     = "/servlet/controller.propose.MainSubjSearchServlet?p_process=SubjSearch&p_lsearchtext="+searchText;
         menuMainForward(gubun, url);
       
 
    }
  
 // 관리페이지
    function topAdminOpenPage() {
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinAdmin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1024, height = 768, top=0, left=0");
        document.topdefaultForm.target = "openWinAdmin"
        document.topdefaultForm.action = "/learn/admin/system/mScreenFset.jsp";
        document.topdefaultForm.submit();

        farwindow.window.focus();
        document.topdefaultForm.target = window.self.name;
    }

    // 권한변경
    function topAuthChange() {
        var sAuth   = document.getElementById("p_topAuth").value;

        document.topdefaultForm.p_auth.value    = sAuth;
        document.topdefaultForm.p_process.value = "authChange";
        document.topdefaultForm.action          = "/servlet/controller.homepage.MainServlet";
        document.topdefaultForm.submit();
    }

    
    
</script>
<form name="topdefaultForm" method="post" action="javascript:return;" id="topdefaultForm">
  <input type='hidden' name='p_process'   value=''>
  <input type='hidden' name='gubun'       value='0'>
  <input type='hidden' name='menuid'      value=''>
  <input type='hidden' name='url'         value=''>
  <input type='hidden' name='p_cmuno'     value=''>
  <input type='hidden' name='searchClass' value=''>
  <input type='hidden' name='p_auth'      value=''>
  <input type='hidden' name='p_topuserid' value=''>
  <input type='hidden' name='p_toppwd'    value=''>
  <input type='hidden' name='p_area'    value=''>
</form>	
<form name="form1" method="post" id="form1" style="margin-top: -206px;">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="p_rprocess" value="" />
    <input type="hidden" name="menuid" value="" />
    <input type="hidden" name="gubun" value="" />
    <input type="hidden" name="p_seq" value="" />
    <input type="hidden" name="p_subj" value="" />
    <input type="hidden" name="p_subjnm" value="" />
    <input type="hidden" name="p_iscourseYn" value="" />
    <input type="hidden" name="p_upperclass" value="" />
    <input type="hidden" name="p_upperclassnm" value="" />
    <input type="hidden" name="p_year" value="" />
    <input type="hidden" name="p_subjseq" value="" />
    <input type="hidden" name="p_topuserid"	value=""/>
    <input type="hidden" name="p_toppwd"	value=""/>
    <input type="hidden" name="p_edusulUserid" values="" />
    <input type = "hidden" name = "p_prePage"     value = "">
</form> 
<body>
<div id="header">
	<div class="topheader">
		<div class="logo"><img src="/images/2012/common/logo.png" alt="한국콘텐츠아카데미"></div>
		
		<ul class="navi">
			<li><a href="javascript:menuMainForward('3', '/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');" title="나의강의실로 이동합니다. 이용하시기 전에 로그인을 해주세요."><img src="/images/2012/common/menu/menu1.png" onmouseover="slideCateScroll('on','1');" id="navi1" alt="나의강의실"></a></li>
			<li><a href="javascript:menuMainForward('1', '/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList');" title="온라인 교육과정을 확인 및 신청할 수 있습니다."><img src="/images/2012/common/menu/menu2.png" onmouseover="slideCateScroll('on','2');" id="navi2" alt="온라인과정"></a></li>
			<li><a href="javascript:menuMainForward('2', '/servlet/controller.off.ProposeOffServlet?p_process=SubjectList');" title="오프라인 교육과정을 확인 및 신청할 수 있습니다."><img src="/images/2012/common/menu/menu3.png" onmouseover="slideCateScroll('on','3');" id="navi3" alt="오프라인과정"></a></li>
			<li><a href="javascript:menuMainForward('4', '/servlet/controller.homepage.HomeNoticeServlet?p_process=List&menuid=07');" title="학습 전/중/후 필요한 정보와 자료를 제공합니다."><img src="/images/2012/common/menu/menu4.png" onmouseover="slideCateScroll('on','4');" id="navi4" alt="학습지원"></a></li>
			<li><a href="javascript:menuMainForward('5', '/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage');" title="유명인사들의 공개강의를 열람할 수 있습니다."><img src="/images/2012/common/menu/menu5.png" onmouseover="slideCateScroll('on','5');" id="navi5" alt="열린강의"></a></li>
			<li><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage');" title="교육 중 궁금한 것이 있다면 무엇이든 물어보세요. 이용하시기 전에 로그인을 해주세요."><img src="/images/2012/common/menu/menu6.png" onmouseover="slideCateScroll('on','6');" id="navi6" alt="지식팩토리"></a></li>
			<li><a href="javascript:menuForward('7','01');" title="한국콘텐츠진흥원 아카데미를 소개합니다."><img src="/images/2012/common/menu/menu7.png" onmouseover="slideCateScroll('on','7');" id="navi7" alt="아카데미소개"></a></li>
		</ul>
		
		
		<ul class="memberoption">
			<li class="leftbg"></li>
			<li class="centerbg">
				<form method="post" action="">
					<dl class="search">
						<dd class="intext"><input type="text" name="topWarp_searchText" id="topWarp_searchText" onfocus="this.style.background='#000000';" title="검색어를 입력해주세요."></dd>
						<dd class="outsearch"><a href="javascript:totalSubjSearch();"><img src="/images/2012/common/btn_search.png" alt="검색" title="검색을 시작합니다."></a></dd>
					</dl>
					<dl class="option">
					<% if(topWarp_userid.equals("")) {%>     <%--로그인이 안되었는 경우--%>	
						<dd><a href="javascript:menuMainForward('90','/servlet/controller.homepage.MainServlet?p_process=MainLogin');" title="로그인화면으로 이동합니다"><img src="/images/2012/common/option_btn_login.png" alt="로그인"></a></dd>
						<dd><a href="javascript:menuForward('90','01');" title="지금 회원가입을 해보세요"><img src="/images/2012/common/option_btn_join.png" alt="회원가입"></a></dd>
					<% } else { %>	
						<!-- 로그인 후 -->
						<dd class="membername"><b><%=topWarp_usernm%></b>님 어서오세요.</dd>
						<dd><a href="javascript:logout();" title="로그아웃합니다"><img src="/images/2012/common/option_btn_logout.png" alt="로그아웃"></a></dd>
						<dd><a href="javascript:menuForward('90','03');" title="회원님의 정보를 수정합니다"><img src="/images/2012/common/option_btn_modify.png" alt="회원정보변경"></a></dd>
						<!-- 관리자 모드 -->
						<dd class="select">
							<%= box.getSession("tem_grcode").equals("N000001") ? LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") : ""%>
						</dd>
						<dd>
						<% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
						<a href="javascript:topAdminOpenPage()" title="LMS로 이동합니다"><img src="/images/2012/common/option_btn_lms.png" alt="LMS"></a>
						<% } %>
						</dd>
						<% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
						<dd class="select">
							<select name="p_project" title="년도를 선택해주세요">
								<option>선택</option>
								<option value="2011000011">2010</option>
								<option value="2011000011">2011</option>
								<option value="2011000011">2012</option>
							</select>
						</dd>
						<dd><a href="javascript:goSubIndex()" title="프로젝트게시판으로 이동합니다"><img src="/images/2012/common/option_btn_project.png" alt="프로젝트게시판"></a></dd>
						<% } %>
					<% } %>
					</dl>
				</form>
			</li>
			<li class="rightbg"></li>
		</ul>
	</div>
	
</div>
<div id="submenuline" class="submenubox">
	<div class="subnavibox">
		<table id="submenu1" class="subnavi smenu1">
		
			<tr>
				<td><a href="javascript:menuForward('3','11');" title="현재 학습중인 과정을 확인 또는 학습할 수 있습니다.">수강중인 과정</a></td>
				<td><a href="javascript:menuForward('3','02');" title="회원가입 후 현재까지 학습한 이력을 확인할 수 있습니다.">나의교육이력</a></td>
				<td><a href="javascript:menuForward('3','17');" title="신청한 과정을 확인하거나 취소할 수 있습니다.">교육신청 확인/취소</a></td>
				<td><a href="javascript:menuForward('3','19');" title="결제 조회 또는 취소를 할 수 있습니다.">수강료 결제관리</a></td>
				<td><a href="javascript:menuForward('3','12');" title="상담하신 내역을 확인할 수 있습니다.">나의 상담내역</a></td>
				<td><a href="javascript:menuForward('3','07');" title="회원님이 참여하신 설문을 확인할 수 있습니다.">나의 설문</a></td>
				<td><a href="javascript:menuForward('3','17');" title="과정을 수료하신 경우 수료증을 출력할 수 있습니다.">수료증 출력</a></td>
			</tr>
		</table>
		<table id="submenu2" class="subnavi smenu2">
			<tr>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')" title="방송영상 분야 온라인과정을 안내합니다.">전체</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=B0')" title="방송영상 분야 온라인과정을 안내합니다.">방송영상</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=G0')" title="게임개발 분야 온라인과정을 안내합니다.">게임개발</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=K0')" title="문화콘텐츠 분야 온라인과정을 안내합니다.">문화콘텐츠</a></td>
			</tr>
		</table>
		<table id="submenu3" class="subnavi smenu3">
			<tr>
				<td><a href="javascript:menuForward('2','01');" title="방송영상 분야 오프라인과정을 안내합니다.">방송영상</a></td>
				<td><a href="javascript:menuForward('2','02');" title="게임개발 분야 오프라인과정을 안내합니다.">게임제작</a></td>
				<td><a href="javascript:menuForward('2','03');" title="기획창작 분야 오프라인과정을 안내합니다.">기획창작</a></td>
				<td><a href="javascript:menuForward('2','04');" title="학습 중 필요한 자료를 제공합니다.">교육자료실</a></td>
				<td><a href="javascript:menuForward('2','05');" title="오프라인 교육의 연간 및 월간 교육일정을 확인할 수 있습니다.">교육일정</a></td>
				<!-- <td><a href="#" title="교육실, 교육장비, 휴식공간 등 학습환경을 소개합니다.">교육환경소개</a></td> -->
			</tr>
		</table>
		<table id="submenu4" class="subnavi smenu4">
			<tr>
				<td><a href="javascript:menuForward('4','07');"  title="한국콘텐츠진흥원 아카데미의 새소식을 알려드립니다.">공지사항</a></td>
				<td><a href="javascript:menuForward('4','01');" title="자주 묻는 질문을 모아두었습니다.">FAQ</a></td>
				<td><a href="javascript:menuForward('4','02');" title="궁금한 점이 있다면 물어보세요.">묻고답하기</a></td>
				<td><a href="javascript:menuForward('4','05');" title="온라인 학습 전 정상적인 학습을 위해 학습환경을 설정합니다.">학습환경도우미</a></td>
				<td><a href="javascript:menuForward('4','09');" title="비공개 질문을 원하신다면 이곳 1:1문의를 이용해주세요.">1:1문의</a></td>
				<td><a href="javascript:menuForward('4','10');" title="오프라인 교육 시 사용되는 교재를 소개합니다.">교재소개</a></td>
				<td><a href="javascript:menuForward('4','12');" title="아카데미에서 진행하는 다양한 이벤트를 확인할 수 있습니다.">이벤트</a></td>
				<!-- <td><a href="javascript:menuForward('4','07');" title="수강신청이 어려우실 경우 가이드를 통해 따라해보세요.">수강신청방법</a></td> -->
				<!-- <td><a href="javascript:menuForward('4','07');" title="온라인/오프라인 교육 시작 전 궁금한 점이 있다면 안내를 받아보세요.">교육가이드</a></td> -->
			</tr>
		</table>
		<table id="submenu5" class="subnavi smenu5">
			<tr>
				<td><a href="javascript:menuForward('5','12');" title="방송영상 관련 열린강의를 열람할 수 있습니다.">방송영상</a></td>
				<td><a href="javascript:menuForward('5','13');" title="영화 관련 열린강의를 열람할 수 있습니다.">영화</a></td>
				<td><a href="javascript:menuForward('5','14');" title="다큐멘터리 관련 열린강의를 열람할 수 있습니다.">다큐멘터리</a></td>
				<td><a href="javascript:menuForward('5','15');" title="문화예술 관련 열린강의를 열람할 수 있습니다.">문화예술</a></td>
				<td><a href="javascript:menuForward('5','16');" title="게임 관련 열린강의를 열람할 수 있습니다.">게임</a></td>
				<!-- <td><a href="javascript:menuForward('5','17');" title="기획 관련 열린강의를 열람할 수 있습니다.">기획</a></td>
				<td><a href="javascript:menuForward('5','17');" title="기타 관련 열린강의를 열람할 수 있습니다.">기타</a></td> -->
			</tr>
		</table>
		<table id="submenu6" class="subnavi smenu6">
			<tr>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage')" title="지식팩토리의 전체 지식을 종합해보았습니다.">종합</a></td>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CB')" title="방송영상 분야 지식 공간입니다.">방송영상</a></td>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CG')" title="방송영상 분야 지식 공간입니다.">게임개발</a></td>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CK')" title="방송영상 분야 지식 공간입니다.">문화콘텐츠</a></td>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=MINE')" title="회원님께서 질문하신 내용을 이용할 수 있습니다.">내가 작성한 글</a></td>
			</tr>
		</table>
		<table id="submenu7" class="subnavi smenu7">
			<tr>
				<td><a href="javascript:menuForward('7','01');" title="한국콘텐츠진흥원장님께서 회원님들께 드리는 인사말입니다.">진흥원장 인사말</a></td>
				<td><a href="javascript:menuForward('7','02');" title="아카데미의 설립목적과 비전에 대해 소개합니다.">설립목적 및 비전</a></td>
				<td><a href="javascript:menuForward('7','05');" title="아카데미 교육사업을 안내합니다.">교육사업</a></td>
				<!-- <td><a href="javascript:menuForward('7','03');" title="아카데미 교육시설을 안내합니다.">교육시설</a></td> -->
				<td><a href="javascript:menuForward('7','06');" title="아카데미와 협력관계인 기관을 소개합니다.">교육협력기관</a></td>
				<td><a href="javascript:menuForward('7','04');" title="한국콘텐츠진흥원 아카데미에 오시는길을 안내합니다.">오시는길</a></td>
			</tr>
		</table>
	</div>
	<div onmouseover="slideCateScroll('off','0');" id="blockbox" style="margin-top:7px; height:10px; display:none;"></div>
</div>
<div id="container">
	<div class="topcontent">
		<ul class="contentA">
			<li class="leftspace">
				
				<% if(topWarp_userid.equals("")) {%>     <%--로그인이 안되었는 경우--%>	
				<div class="loginput">
				<form method="post" name="login" action=""> 
						<input type='hidden' name='p_process'   value=''>			
						<input name="p_userid" id="p_userid" type="text" class="id" onfocus="this.style.background='#ffffff';" title="아이디를 입력해주세요.">
						<input name="p_pwd" id="p_pwd" type="password" onkeypress="password_enter(event)" class="pass" onfocus="this.style.background='#ffffff';" title="비밀번호를 입력해주세요.">
						<input type="image"  onClick="return login_main();" src="/images/2012/main/btn_login.png" class="login" alt="로그인" title="회원 로그인을 실행합니다.">
				</form> 
				</div>
				<% } else  { %>
					<dl class="logafter">
					<dd><a href="javascript:menuForward('90','03');"><img src="/images/2012/main/member_modify.png"></a></dd>
					<dd><a href="javascript:logout();"><img src="/images/2012/main/logout.png"></a></dd>
					<dd><b><%=topWarp_usernm%></b>님 &nbsp;</dd>
					</dl>
				<%  }  %>
				
				<a href="javascript:menuForward('90','02');" title="아이디 또는 비밀번호를 잊었을 때 클릭하세요."><img src="/images/2012/main/btn_idpass.png" class="idpass" alt="아이디/비밀번호 찾기"></a>
				<a href="javascript:mainmenu('991');" title="신규회원가입을 진행합니다."><img src="/images/2012/main/btn_join.png" class="join" alt="회원가입"></a>
				<dl class="titlenewspost">
					<dt><img src="/images/2012/main/subject_news.png" alt="아카데미 새소식"></dt>
					<dt class="more"><a href="javascript:menuForward('4','07');" title="공지사항으로 이동합니다."><img src="/images/2012/main/btn_more.png" alt="전체보기"></a></dt>
				</dl>
				<table class="newspost" cellpadding="0" cellspacing="0" summary="아카데미 새소식">
					<colgroup><col width="8"><col width="*"></colgroup>
					<tbody>
					
					 <%  // 공지사항
                        if(noticeListTop.size()!=0)
                        {
                            for(int i=0;i<noticeListTop.size();i++)
                            {   
                            	if (i > 5) break;
                                DataBox dbox =  noticeListTop.get(i);
                     %>
                     			<tr>
									<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
									<td><a href="javascript:viewNotice('<%=dbox.getString("d_seq")%>');" title="<%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 35)%>"><%=StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 30)%></a></td>
									<td class="date"><%=dbox.getString("d_addate").substring(4,6)%>-<%=dbox.getString("d_addate").substring(6,8)%></td>
								</tr>
                     <%
                            }
                        }
                        else
                        {
                            out.print("<tr><td><img src='/images/2012/main/icon_dot.png' alt='구분점'></td>");
                            out.print("<td colspan=2>새 소식이 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
                                     
					</tbody>
				</table>
			</li>
			<li class="rightspace">
				<!-- 배너 영역 -->
				<script type="text/javascript">
				<!--
				$(function() {
					$("#cyberbanner").jQBanner({nWidth:589,nHeight:245,nCount:5,isActType:"left",nOrderNo:1,isStartAct:"N",isStartDelay:"Y",nDelay:4000,isBtnType:"img"});
				});
				//-->
				</script>
				<div id="cyberbanner">
					<div class="clsBannerScreen">
						<div class="images"><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectPreviewPage&gubun=2&menuid=03&p_subj=SK12017&p_year=2012&p_subjseq=0001&p_seq=1"><img src="/images/2012/main/banner/showimage/opencast.png" alt="<20대 대표멘트>박신영작가와 청춘의 한계를 논하다! 언제? 201년 10월 9일(화) 19:00 ~ 20:30 어디에서? 목동 한국방송회관 3층 회견장"></a></div>
						<div class="images"><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=selectView&menuid=07&gubun=4&p_seq=1570&tem_grcode=N000001"><img src="/images/2012/main/banner/showimage/kpop.png" alt="k-pop,중화권 진출을 위한 해외 비즈니스 연수 : 접수 9.25(화)~10.15(월) (17:00 마감)"></a></div>
						<div class="images"><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectPreviewPage&gubun=2&menuid=03&p_subj=SK12018&p_year=2012&p_subjseq=0001&p_seq=1"><img src="/images/2012/main/banner/showimage/jung.png" alt="가필드2, 박물관이 살아있다의 3D입체영상 슈퍼바이저 정 미 보그트 초청 강연 : 2012. 10. 15 (월) 목동 방송회관 3층 회견장에서. 강연 참여 신청은 2012.9.24(월) ~ 10.12(금)"></a></div>
						<div class="images"><img src="/images/2012/main/banner/showimage/thanks.png" alt="풍성한 한가위 - 다가오는 한가위도 즐겁게 풍요롭게 보내세요. 한국콘텐츠아카데미 일동"></div>
						<div class="images"><a href="javascript:viewNotice('1568');"><img src="/images/2012/main/banner/showimage/renewal.png" alt="한국콘텐츠아카데미 홈페이지 새단장, 무엇때문에? 브라우저 제약이 없는 학습환경 지원, 누구나 이용할 수 있는 웹접근성 준수"></a></div>
					</div>
					<ul class="clsBannerButton" id="label_3">
						<li><img src="/images/2012/main/banner/number/num01on.png" oversrc="/images/2012/main/banner/number/num01on.png" outsrc="/images/2012/main/banner/number/num01.png" alt="1"></li>
						<li><img src="/images/2012/main/banner/number/num02.png" oversrc="/images/2012/main/banner/number/num02on.png" outsrc="/images/2012/main/banner/number/num02.png" alt="2"></li>
						<li><img src="/images/2012/main/banner/number/num03.png" oversrc="/images/2012/main/banner/number/num03on.png" outsrc="/images/2012/main/banner/number/num03.png" alt="3"></li>
						<li><img src="/images/2012/main/banner/number/num04.png" oversrc="/images/2012/main/banner/number/num04on.png" outsrc="/images/2012/main/banner/number/num04.png" alt="4"></li>
						<li><img src="/images/2012/main/banner/number/num05.png" oversrc="/images/2012/main/banner/number/num05on.png" outsrc="/images/2012/main/banner/number/num05.png" alt="5"></li>
					</ul>
				</div>
				<!-- 배너 영역 끝 -->
			</li>
		</ul>
		<ul class="contentB">
			<li class="online">
				<dl class="titleonline">
					<dd><img src="/images/2012/main/subject_onlinecourse.png" alt="온라인 과정"></dd>
					<dd class="more"><a href="javascript:menuMainForward('1', '/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList');" title="전체보기"><img src="/images/2012/main/btn_more.png" alt="온라인 과정 전체보기"></a></dd>
				</dl>
				<dl class="onlinetab">
					<dd><button type="button" onmouseover="onlinetab('1')" onclick="document.location='#'" class="over" id="online1" title="온라인 추천과정">온라인 추천과정</button></dd>
					<dd><button type="button" onmouseover="onlinetab('2')" onclick="document.location='#'" class="out" id="online2" title="게임제작">게임제작</button></dd>
					<dd><button type="button" onmouseover="onlinetab('3')" onclick="document.location='#'" class="out" id="online3" title="방송영상">방송영상</button></dd>
					<dd><button type="button" onmouseover="onlinetab('4')" onclick="document.location='#'" class="out" id="online4" title="문화콘텐츠">문화콘텐츠</button></dd>
				</dl>
				<table id="onlinelist1" class="courselist" cellpadding="0" cellspacing="0" summary="온라인 추천과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
					
					 <%  // 온라인 추천과정
                        if(onLineSubjList.size()!=0)
                        {
                            for(int i=0;i<onLineSubjList.size();i++)
                            {                               
                                DataBox dbox =  onLineSubjList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");

                                String tmp=upperMap !=null && upperMap.get(dbox.get("d_scupperclass")) != null ?  upperMap.get(dbox.get("d_scupperclass")).toString() : "00";
                                aHref_SubjValues="'"+dbox.get("d_subj")+"','"+dbox.get("d_subjnm")+"','"+dbox.get("d_isonoff")+"','"+dbox.get("d_scupperclass")+"','"+dbox.get("d_uclassnm")+"','"+dbox.get("d_scyear")+"','"+dbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=tmp+"'";
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=dbox.getString("d_EDUSTART")%> ~ <%=dbox.getString("d_EDUEND")%>] <%=StringManager.formatTitle(dbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(dbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=dbox.getString("d_EDUSTART")%> ~ <%=dbox.getString("d_EDUEND")%></td>
						</tr>
                     <%
                            }
                        }
                        else
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
						<!-- <tr>
							<td><img src="/images/2012/main/icon/icon_bl.gif" alt="BL심화과정"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 11가지 법칙으로 끝내는 스토리텔링">11가지 법칙으로 끝내는 스토리텔링</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D Data Exporting Techniques">3D Data Exporting Techniques</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						 -->
					</tbody>
				</table>
				<table id="onlinelist2" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="게임제작분야 과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
					<%  // 게임제작분야
                       for(int i=0;i<onLineGameSubjList.size();i++)
                            {                               
                                DataBox gamedbox =  onLineGameSubjList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");

                                String tmp=upperMap !=null && upperMap.get(gamedbox.get("d_scupperclass")) != null ?  upperMap.get(gamedbox.get("d_scupperclass")).toString() : "00";
                                aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_uclassnm")+"','"+gamedbox.get("d_scyear")+"','"+gamedbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=tmp+"'";
                                
                                String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
                                String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
                                
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
						</tr>
                     <%
                       }
                       
                        if (onLineGameSubjList.size() == 0)
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
						
					</tbody>
				</table>
				<table id="onlinelist3" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="방송영상분야 과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
					<%  // 방송영상
                       for(int i=0;i<onLineBroadSubjList.size();i++)
                            {                               
                                DataBox gamedbox =  onLineBroadSubjList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");

                                String tmp=upperMap !=null && upperMap.get(gamedbox.get("d_scupperclass")) != null ?  upperMap.get(gamedbox.get("d_scupperclass")).toString() : "00";
                                aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_uclassnm")+"','"+gamedbox.get("d_scyear")+"','"+gamedbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=tmp+"'";
                                
                                String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
                                String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
						</tr>
                     <%
                       }
                       
                        if (onLineBroadSubjList.size() == 0)
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
					</tbody>
				</table>
				<table id="onlinelist4" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="문화콘텐츠분야 과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
					<%  // 문화콘텐츠분야
                       for(int i=0;i<onLineCultureSubjList.size();i++)
                            {                               
                                DataBox gamedbox =  onLineCultureSubjList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");

                                String tmp=upperMap !=null && upperMap.get(gamedbox.get("d_scupperclass")) != null ?  upperMap.get(gamedbox.get("d_scupperclass")).toString() : "00";
                                aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_uclassnm")+"','"+gamedbox.get("d_scyear")+"','"+gamedbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=tmp+"'";
                                
                                String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
                                String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
						</tr>
                     <%
                       }
                       
                        if (onLineCultureSubjList.size() == 0)
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
					</tbody>
				</table>
			</li>
			<li class="offline">
				<dl class="titleonline">
					<dd><img src="/images/2012/main/subject_offlinecourse.png" alt="오프라인 과정"></dd>
					<dd class="more"><a href="javascript:menuMainForward('2', '/servlet/controller.off.ProposeOffServlet?p_process=SubjectList');" title="전체보기"><img src="/images/2012/main/btn_more.png" alt="오프라인 과정 전체보기"></a></dd>
				</dl>
				<dl class="onlinetab">
					<dd><button type="button" onmouseover="offlinetab('1')" onclick="document.location='#'" class="over" id="offline1" title="3D 입체콘텐츠">3D 입체콘텐츠</button></dd>
					<dd><button type="button" onmouseover="offlinetab('2')" onclick="document.location='#'" class="out" id="offline2" title="산업계 직무재교육">산업계 직무재교육</button></dd>
					<dd><button type="button" onmouseover="offlinetab('3')" onclick="document.location='#'" class="out" id="offline3" title="차세대 인재양성">차세대 인재양성</button></dd>
					<dd><button type="button" onmouseover="offlinetab('4')" onclick="document.location='#'" class="out" id="offline4" title="수탁교육">수탁교육</button></dd>
				</dl>
				<table id="offlinelist1" class="courselist" cellpadding="0" cellspacing="0" summary="오프라인 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
					<%  // 3D 입체콘텐츠
                       for(int i=0;i<DimensionOffSubjectList.size();i++)
                            {                               
                                DataBox gamedbox =  DimensionOffSubjectList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");                               

                                aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
                                
                                String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
                                String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
						</tr>
                     <%
                       }
                       
                        if (DimensionOffSubjectList.size() == 0)
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
					
					</tbody>
				</table>
				<table id="offlinelist2" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="온라인과정 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
					<%  // 산업계
                       for(int i=0;i<IndustryOffSubjectList.size();i++)
                            {                               
                                DataBox gamedbox =  IndustryOffSubjectList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");                               

                                aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
                                
                                String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
                                String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
						</tr>
                     <%
                       }
                       
                        if (IndustryOffSubjectList.size() == 0)
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
						<!-- <tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">1Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3ALITY리그 테크니션 과정">3ALITY리그 테크니션 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr> -->
					</tbody>
				</table>
				<table id="offlinelist3" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="온라인과정 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
					<%  // 차세대
                       for(int i=0;i<NextOffSubjectList.size();i++)
                            {                               
                                DataBox gamedbox =  NextOffSubjectList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");                               

                                aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
                                
                                String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
                                String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
						</tr>
                     <%
                       }
                       
                        if (NextOffSubjectList.size() == 0)
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
						<!-- <tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">1Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 디지털 영상편집 과정">디지털 영상편집 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr> -->
					</tbody>
				</table>
				<table id="offlinelist4" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="온라인과정 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
					<%  // 차세대
                       for(int i=0;i<SuTakOffSubjectList.size();i++)
                            {                               
                                DataBox gamedbox =  SuTakOffSubjectList.get(i);
                                String biyong="";
                                String aHref_SubjValues="";
                                DecimalFormat df = new DecimalFormat("#,###");                               

                                aHref_SubjValues="'"+gamedbox.get("d_subj")+"','"+gamedbox.get("d_subjnm")+"','"+gamedbox.get("d_isonoff")+"','"+gamedbox.get("d_scupperclass")+"','"+gamedbox.get("d_seq")+"','"+gamedbox.get("d_year")+"','"+gamedbox.get("d_subjseq")+"','";
                                aHref_SubjValues+=gamedbox.get("d_scupperclass")+"'";
                                
                                String v_edustart = FormatDate.getFormatDate(gamedbox.getString("d_EDUSTART"),"yyyy.MM.dd");
                                String v_eduend = FormatDate.getFormatDate(gamedbox.getString("d_EDUEND"),"yyyy.MM.dd");
                     %>
                     	<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="javascript:view_On_Subj(<%=aHref_SubjValues%>);" title="[<%=v_edustart%> ~ <%=v_eduend%>] <%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%>"><%=StringManager.formatTitle(gamedbox.get("d_subjnm"), 26)%></a></td>
							<td class="date"><%=v_edustart%> ~ <%=v_eduend%></td>
						</tr>
                     <%
                       }
                       
                        if (SuTakOffSubjectList.size() == 0)
                        {
                            out.print("<tr>");
                            out.print("<td colspan=3>현재 신청 가능한 강좌가 없습니다.</td>");                           
                            out.print("</tr>");
                        }                                      
             		%>
             		
						<!-- <tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">1Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Smoke를 활용한 3D입체 제작 과정">Smoke를 활용한 3D입체 제작 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3ALITY리그 테크니션 과정">3ALITY리그 테크니션 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr> -->
					</tbody>
				</table>
			</li>
		</ul>
		<div style="clear:left;"></div>
	</div>
	<div class="bottomcontent">
		<div class="contentbox">
			<ul class="content">
				<li class="leftspace">
					<div class="detail">
						<a href="javascript:menuForward('5','12');" title="성공을 말하다! <열린강의> 바로가기"><img src="/images/2012/main/subject_opencast.png" alt="성공을 말하다! 열린강의"></a>
						<div class="opencastment">방송, 영상, 영화, 다큐멘터리, 문화, 예술, 게임, 유통 등 다양한 분야의 유명인사들이 한자리에 모여 말하다.</div>
						<div class="banner"><a href="javascript:menuForward('4','05');" title="온라인과정 수업 전 교육환경 설정하러 바로가기"><img src="/images/2012/main/banner_config.gif" alt="온라인과정 수업 전 교육환경 설정하기"></a></div>
						<div class="talent"><img src="/images/2012/main/subject_talent.png" alt="차세대 인재를 위한 전문화된 교육시설"></div>
						<script type="text/javascript">
						<!--
						$(function() {
							$("#learningroom").jQBanner({	nWidth:370, nHeight:76, nCount:5,	isActType:"up", nOrderNo:1, nDelay:5000 });
						});
						//-->
						</script>
						<div id="learningroom">
							<div class="clsBannerScreen">
								<div class="images" style="display:block"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
							</div>
						</div>
					</div>
				</li>
				<li class="rightspace">
					<dl class="steprable">
						<dd class="msg"><img src="/images/2012/main/step_msg.png" alt="한국콘텐츠아카데미의 처음오셨나요? 전문교육을 무료로 받아보세요."></dd>
						<dd class="step1"><a href="javascript:menuForward('90','01');" title="회원가입 바로가기"><img src="/images/2012/main/step1.png" alt="1단계 회원가입"></a></dd>
						<dd class="step2"><a href="javascript:menuMainForward('90','/servlet/controller.homepage.MainServlet?p_process=MainLogin');" title="로그인 바로가기"><img src="/images/2012/main/step2.png" alt="2단계 로그인"></a></dd>
						<dd class="step3"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')" title="과정선택 바로가기"><img src="/images/2012/main/step3.png" alt="3단계 과정선택"></a></dd>
						<dd class="step4"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')" title="수강신청 바로가기"><img src="/images/2012/main/step4.png" alt="4단계 수강신청"></a></dd>
					</dl>
					<dl class="support">
						<dd><a href="http://kocca.anyhelp.net" target="remote" title="원격지원 서비스로 이동합니다."><img src="/images/2012/main/banner_support.gif" alt="학습 중 PC문제가 발생하셨나요? 원격지원서비스 이용하기"></a></dd>
						<dd><a href="javascript:menuForward('4','02');" title="묻고답하기 게시판으로 이동합니다."><img src="/images/2012/main/banner_sms.gif" alt="묻고답하기 답변 알림 SMS : 질문 후 답변 안내 문자가 발송됩니다."></a></dd>
					</dl>
					<dl class="business">
						<dd class="learning">
							<table class="businessfield" cellpadding="0" cellspacing="0" summary="한국콘텐츠아카데미 교육사업 분야 안내">
								<thead>
									<tr>
										<th colspan="2"><img src="/images/2012/main/subject_business.png" alt="한국콘텐츠아카데미 교육사업"></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" title="차세대 인재 양성교육사업 안내로 이동합니다.">차세대 인재 양성교육</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" title="3D입체 콘텐츠 전문인력 양성사업 안내로 이동합니다.">3D입체 콘텐츠 전문인력 양성</a></td>
									</tr>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" title="산업계 직무 재교육사업 안내로 이동합니다.">산업계 직무 재교육</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" title="창의인재 양성사업 안내로 이동합니다.">창의인재 양성사업</a></td>
									</tr>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" title="온라인 교육사업 안내로 이동합니다.">온라인 교육</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" title="드라마 프로듀서 스쿨사업 안내로 이동합니다.">드라마 프로듀서 스쿨</a></td>
									</tr>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" title="국제 인증과정사업 안내로 이동합니다.">국제 인증과정</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" title="게임 국가기술 자격 검정사업 안내로 이동합니다.">게임 국가기술 자격 검정</a></td>
									</tr>
								</tbody>
							</table>
						</dd>
						<dd class="another">
							<a href="#" title="온라인 전체 교육과정을 확인할 수 있습니다"><img src="/images/2012/main/banner_curriculum.gif" alt="온라인과정 커리큘럼"></a><br>
							<a href="#" title="드림멘토 창의인재 동반사업 바로가기"><img src="/images/2012/main/sample_banner1.gif" alt="드림멘토 창의인재 동반사업"></a>
						</dd>
					</dl>
				</li>
			</ul>
			<div class="clearscript"></div>
		</div>
		<div class="smarticon">
		<table class="icon" cellspacing="0" cellpadding="0" summary="자주이용하는 기능들을 모았습니다.">
			<tbody>
				<tr>
					<!-- <td class="icon1"><a href="#" title="온라인/오프라인 교육 시작 전 궁금한 점이 있다면 안내를 받아보세요."><img src="/images/2012/common/smarticon/icon_guide.png" alt="교육가이드"></a></td> -->
					<td class="icon2"><a href="javascript:menuForward('3','19');" title="선택하신 과정에 결제가 필요하신 경우 결제를 진행할 수 있습니다."><img src="/images/2012/common/smarticon/icon_payment.png" alt="결제하기"></a></td>
					<!-- <td class="icon3"><a href="#" title="과정선택이 어렵다면? 역량진단을 통해 알맞는 과정을 선택해보세요."><img src="/images/2012/common/smarticon/icon_level.png" alt="자기역량진단"></a></td> -->
					<td class="icon4"><a href="javascript:menuForward('4','05');" title="온라인 학습 전 정상적인 학습을 위해 학습환경을 설정합니다."><img src="/images/2012/common/smarticon/icon_config.png" alt="학습환경도우미"></a></td>
					<td class="icon5"><a href="javascript:menuForward('4','09');" title="비공개 질문을 원하신다면 이곳 1:1문의를 이용해주세요."><img src="/images/2012/common/smarticon/icon_1n1.png" alt="1:1문의"></a></td>
					<td class="icon6"><a href="javascript:menuMainForward('4','/servlet/controller.study.MyClassServlet?p_process=StudyHistoryListSyuro');" title="과정을 수료하신 경우 수료증을 출력할 수 있습니다."><img src="/images/2012/common/smarticon/icon_cert.png" alt="수료증 출력"></a></td>
					<td class="icon7"><a href="javascript:menuForward('4','10');" title="오프라인 교육 시 사용되는 교재를 소개합니다."><img src="/images/2012/common/smarticon/icon_book.png" alt="교재안내"></a></td>
					<td class="icon8"><a href="javascript:menuForward('4','01');" title="자주 묻는 질문을 모아두었습니다."><img src="/images/2012/common/smarticon/icon_faq.png" alt="빠른답변"></a></td>
					<td class="icon9"><a href="javascript:menuForward('4','02');" title="궁금한 점이 있다면 물어보세요."><img src="/images/2012/common/smarticon/icon_qna.png" alt="묻고답하기"></a></td>
					<td class="icon10"><a href="javascript:menuForward('7','04');" title="한국콘텐츠진흥원 아카데미에 오시는길을 안내합니다."><img src="/images/2012/common/smarticon/icon_location.png" alt="찾아오시는길"></a></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div id="footer">
	<div class="supportbox">
		<div class="support">
			<div class="supportcontent">
				<ul class="supportmenu">
					<li class="startline"><a href="javascript:menuMainForward('90','/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL');" title="개인정보처리방침으로 이동합니다."><img src="/images/2012/common/btn_member_terms.png" alt="개인정보처리방침"></a></li>
					<li><a href="javascript:menuForward('90','05');" title="이용약관으로 이동합니다."><img src="/images/2012/common/btn_use_terms.png" alt="이용약관"></a></li>
					<li><a href="javascript:menuMainForward('90','/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL');" title="이메일무단수집거부로 이동합니다."><img src="/images/2012/common/btn_email_denial.png" alt="이메일부단수집거부"></a></li>
					<li><a href="javascript:menuForward('90','09');" title="사이트맵으로 이동합니다."><img src="/images/2012/common/btn_sitemap.png" alt="사이트맵"></a></li>
				</ul>
				<ul class="certification">
					<li><img src="/images/2012/common/icon_inicis.png" alt="이니시스 결제 시스템" Onclick="javascript:window.open('https://mark.inicis.com/mark/popup_v1.php?no=19844&st=1322725008','mark','scrollbars=no,resizable=no,width=530,height=670');"></li>
					<li><img src="/images/2012/common/icon_goodcontent.png" alt="콘텐츠제공서비스 품질인증" Onclick="javascript:window.open('http://edu.kocca.or.kr/contents/temp/gcs.html','mark','scrollbars=no,resizable=no,width=715,height=900');"></li>
					<li><img src="/images/2012/common/icon_eprivacy.png" alt="개인정보보호우수사이트 인증" Onclick="javascript:window.open('http://edu.kocca.or.kr/contents/temp/eprivacy.html','mark','scrollbars=no,resizable=no,width=700,height=965');"></li>
					<li><img src="/images/2012/common/icon_isafe.png" alt="개인정보보호안전 인증" Onclick="javascript:window.open('http://edu.kocca.or.kr/contents/temp/i_safe.html','mark','scrollbars=no,resizable=no,width=700,height=965');"></li>
				</ul>
			</div>
		</div>
		<div class="about">
			<ul class="information">
				<li><img src="/images/2012/common/bottom_logo.png" alt="한국콘텐츠진흥원 아카데미"></li>
				<li class="copy">
					<dl class="copyright">
						<dd class="address">서울특별시 양천구 목동동로 203-1 방송회관 8층<br>사업자등록번호 : 117-82-04412      통신판매업신고번호 : 양천 00471</dd>
						<dd class="corp">한국콘텐츠진흥원(목동) 원장 홍상표</dd>
						<dd class="maker">COPYRIGHTⓒ 2012 KOCCA ALL RIGHTS RESERVED.</dd>
					</dl>
				</li>
				<li class="servicecenter">
					<img src="/images/2012/common/bottom_servicecenter.png" alt="온라인 교육문의 02-2161-0077번, 오프라인 교육문의 02-2161-0072">
					<dl class="familysite">
						<dt onclick="showsite();">관련사이트 바로가기</dt>
						<dd id="url1" style="top:-20px;"><a href="http://www.mcst.go.kr/main.jsp" target="_blank">문화체육관광부</a></dd>
						<dd id="url2" style="top:-40px;"><a href="http://www.kocca.kr/main2.html" target="_blank">한국콘텐츠진흥원</a></dd>
						<dd id="url3" style="top:-60px;"><a href="http://library.kocca.or.kr/index.jsp" target="_blank">콘텐츠도서관</a></dd>
						<dd id="url4" style="top:-80px;"><a href="http://www.gamehub.or.kr/main/main.jsp" target="_blank">글로벌게임허브센터</a></dd>
						<dd id="url5" style="top:-100px;"><a href="http://www.culturecontent.com/main.do" target="_blank">문화콘텐츠닷컴</a></dd>
						<dd id="url6" style="top:-120px;"><a href="http://www.dccenter.kr/main_ft.jsp?syscd=FT" target="_blank">콘텐츠이용보호센터</a></dd>
						<dd id="url7" style="top:-140px;"><a href="http://www.contentright.or.kr/index.jsp" target="_blank">저작권위탁관리시스템</a></dd>
						<dd id="url8" style="top:-160px;"><a href="http://www.uci.or.kr/kor/file/main/main.jsp" target="_blank">콘텐츠식별체계(UCI)</a></dd>
						<dd id="url9" style="top:-180px;"><a href="http://www.kgq.or.kr/service/pub/IntroServlet" target="_blank">게임국가자격기술검정</a></dd>
						<dd id="url10" style="top:-200px;"><a href="http://www.culturist.or.kr/" target="_blank">종합인력정보시스템</a></dd>
						<dd id="url11" style="top:-220px;"><a href="http://www.koccamusic.or.kr" target="_blank">음악아카이브</a></dd>
						<!-- <dd id="url12" style="top:-240px;"><a href="http://card.mct.go.kr/waf/welcome?q=AC1A0F34189E00B963E3DC34C9231C1B2F543B08D1668A')">보조사업비카드관리시스템</a></dd> -->
					</dl>
				</li>
			</ul>
		</div>
	</div>
</div>

</body>
</html>