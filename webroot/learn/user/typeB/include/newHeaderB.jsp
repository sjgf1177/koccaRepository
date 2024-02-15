<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<script type="text/javascript" >
    //전체 과정검색
    function totalSubjSearch() {
        var grcode="${sessionScope.tem_grcode }";
        var searchText = $("#search_input").val();

		var url     = "/servlet/controller.propose.ProposeCourseServlet?p_process=TotalSubjectList&s_subjnm="+searchText;
		menuMainForward_Total(url);
    }
    
    function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar=no,menubar=no,status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
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
	<header>
        <div class="wrapper">
			<c:set var="gubunId" value="${param.gubun}_${param.menuid}" />

			<c:if test="${not empty param.gubun
							and param.gubun ne '0'
							and param.gubun ne '2'
							and gubunId ne '3_0'
							and gubunId ne '4_0'
							and param.gubun ne '10'
							and param.gubun ne '60'
							and param.gubun ne '80'
							and param.gubun ne '88'}">
            <div class="container mt20">
                <div class="row header-util-wrap">
                    <div class="col-12 d-flex">
						<!-- header 왼쪽 영역(로고, 프로필사진, 나의강의실, 로그아웃)-->
						<ul class="fixed-header-box text-center">
							<li class="logo">
								<a href="javascript:menuForward('9', '02');" class="d-inblock"><img src="/images/newmain_20100625/<c:out value="${sessionScope.tem_grcode }"/>.png" alt="로고" /></a>
								<%--<a href="#" class="d-inblock"><img src="/images/2023/renew_logo.svg" alt="로고" /></a>--%>
							</li>
							<li>
								<span class="circle-box myname">${sessionScope.name}</span>
								<a href="javascript:menuForward('3', '11');" class="d-block btn btn-success btn-md w-75 mt20">나의강의실</a>
								<span class="d-block mt10" class="fc-light"><a href="javascript:mainmenu('3');" class="fc-light">로그아웃</a> ㅣ <a href="javascript:mainmenu('4');" class="fc-light">개인정보변경</a></span>
							</li>
						</ul>
						<div class="header-left-box">
							<!-- 임의로 살려둔곳 추후 제거-->
							<div class="header-util-text" style="position: relative; z-index: 9999;">
<%--								<span class="header_util_item"><a href="javascript:mainmenu('990');">로그인</a></span>
								<hr class="header_util_line">
								<span class="header_util_item"><a href="javascript:mainmenu('1');">회원가입</a></span>
								<hr class="header_util_line">
								<span class="header_util_item"><a href="javascript:mainmenu('2');">아이디/패스워드 찾기</a></span>--%>
							</div>
							<!-- 임의로 살려둔곳 추후 제거-->
						</div>
						<!-- header 오른쪽 영역(Tab)-->
						<div class="header-right-box">
							<c:if test="${param.gubun ne '70'}">
							<ul class="nav nav-tabs nav-justified">
								<li class="nav-item">
									<a href="javascript:menuForward('9', '02');" class="nav-link <c:if test="${param.gubun eq '9'}">active</c:if>">정규과정</a>
								</li>
								<li class="nav-item">
									<a href="javascript:menuForward('5', '01');" class="nav-link <c:if test="${param.gubun eq '5'}">active</c:if>">열린과정</a>
								</li>
								<li class="nav-item">
									<a href="javascript:menuForward('4', '07');" class="nav-link <c:if test="${param.gubun eq '4'}">active</c:if>">공지사항</a>
								</li>
								<!--
								<li class="nav-item">
									<a href="#" class="nav-link">콘텐츠4</a>
								</li>
								<li class="nav-item">
									<a href="#" class="nav-link">콘텐츠5</a>
								</li>
								-->
							</ul>
							</c:if>
						</div>

                    </div>
                </div>

            </div>
			</c:if>
        </div>

    </header>