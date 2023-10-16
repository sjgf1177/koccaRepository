<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<script type="text/javascript" >
    //��ü �����˻�
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
 	// ����������
    function topAdminOpenPage() {
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinAdmin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1024, height = 768, top=0, left=0");
        document.topdefaultForm.target = "openWinAdmin"
        document.topdefaultForm.action = "/learn/admin/system/mScreenFset.jsp";
        document.topdefaultForm.submit();

        farwindow.window.focus();
        document.topdefaultForm.target = window.self.name;
    }
 	
 	// ���Ѻ���
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
            <div class="container mt20">
                <div class="row header-util-wrap">
                    <div class="col-12 d-flex">
						<!-- header ���� ����(�ΰ�, �����ʻ���, ���ǰ��ǽ�, �α׾ƿ�)-->
						<ul class="fixed-header-box text-center">
							<li class="logo">
								<a href="#" class="d-inblock"><img src="/images/newmain_20100625/<c:out value="${sessionScope.tem_grcode }"/>.png" alt="�ΰ�" /></a>
							</li>
							<li>
								<span class="circle-box myname">�̸�</span>
								<a href="javascript:menuForward('3', '11');" class="d-block btn btn-success btn-md w-50 mt20">���ǰ��ǽ�</a>
								<span class="d-block mt10" class="fc-light"><a href="javascript:mainmenu('3');" class="fc-light">�α׾ƿ�</a> �� <a href="javascript:mainmenu('4');" class="fc-light">������������</a></span>
							</li>
						</ul>
						<div class="header-left-box"></div>
						<!-- header ������ ����(Tab)-->
						<div class="header-right-box">

							<ul class="nav nav-tabs nav-justified">
								<li class="nav-item">
									<a href="javascript:menuForward('9', '02');" class="nav-link">���԰���</a>
								</li>
								<li class="nav-item">
									<a href="javascript:menuForward('5', '01');" class="nav-link active">��������</a>
								</li>
								<li class="nav-item">
									<a href="javascript:menuForward('4', '07');" class="nav-link">��������</a>
								</li>
								<!--
								<li class="nav-item">
									<a href="#" class="nav-link">������4</a>
								</li>
								<li class="nav-item">
									<a href="#" class="nav-link">������5</a>
								</li>
								-->
							</ul>

						</div>

                    </div>
                </div>

            </div>    
        </div>

    </header>