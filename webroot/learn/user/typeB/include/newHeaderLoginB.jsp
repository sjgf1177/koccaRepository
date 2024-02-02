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
	</div>
</header>