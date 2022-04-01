<%
    // 메뉴 관련 파라미터 정의 - 각 메뉴 이동시 레프트 메뉴 상태(on/off) 결정
    String gubun     = box.getString("gubun");  // 탑 메뉴
	String menuid    = box.getString("menuid"); // 레프트 메뉴
	String orders    = box.getString("orders"); // 레프트 메뉴 순서
	
	// 현재 메뉴 상태(메뉴 이동시 세션 저장)
	if(!gubun.equals("") && gubun != null)  box.setSession("s_gubun", gubun);
	if(!menuid.equals("") && menuid != null) box.setSession("s_menuid", menuid);
	
	String v_return_url = request.getRequestURI();
	String v_return_process = box.getString("p_process");
	
%>

<script language="JavaScript">
function ssoLogin() {
	/*
    var frm = document.frmLogin;
    frm.action = "http://www.wecon.kr/sub/mb/MBLogin.srt";
    frm.method = "post";
    frm.submit();
    */
    location.href = "http://www.wecon.kr/sub/mb/MBLogin.srt?rtnURL=http://edu.kocca.or.kr";
}

function ssoJoin() {
    location.href = "http://www.wecon.kr/sub/kocca/member/ssnNameCk.jsp";
//    var frm = document.frmLogin;
//    frm.action = "http://www.wecon.kr/sub/kocca/member/ssnNameCk.jsp";
//    frm.method = "post";
//    frm.submit();
}
//회원정보수정
function ssoUpdate() {
	location.href = "http://www.wecon.kr/sub/kocca/member/userUpd.jsp?rtnURL=http://edu.kocca.or.kr"
}

function ssoLosspwd() {
    location.href = "http://www.wecon.kr/sub/kocca/mb/MBFindIDPassword.srt";
//    var frm = document.frmLogin;
//    frm.action = "http://www.wecon.kr/sub/kocca/mb/MBFindIDPassword.srt";
//    frm.method = "post";
//    frm.submit();
}

function ssoMemberOut() {
	 location.href = "http://www.wecon.kr/sub/mb/MBOut.srt?rtnURL=http://edu.kocca.or.kr";
}
function menuForward(gubun, menuid){
//	if( (gubun == '1' &&( menuid == '01' || menuid == '13')) || ( gubun == "2" && (menuid == '04' || menuid == '07'))) {
//		alert("준비중입니다.");
//	}
//	else {

    // 취업정보 선택시 컬쳐리스트 페이지 오픈
    if(gubun == '5' && menuid == '09') {
        window.open('http://www.culturist.co.kr/front/recruit/recruit_new_list.asp');
        return;
    }
    document.topdefaultForm.gubun.value    = gubun;
    document.topdefaultForm.menuid.value   = menuid;
    document.topdefaultForm.p_process.value  = "writeLog";
    document.topdefaultForm.action = "/servlet/controller.system.MenuCountServlet";
    document.topdefaultForm.submit();
//	}
}

function menuMainForward(gubun, url){
	    document.topdefaultForm.gubun.value     = gubun;
	    document.topdefaultForm.menuid.value    = "00";
	    document.topdefaultForm.url.value       = url;
	    document.topdefaultForm.p_process.value = "mainForward";
	    document.topdefaultForm.action = "/servlet/controller.system.MenuDataServlet";
	    document.topdefaultForm.submit();
}

//전체 과정검색
function totalSubjSearch() {
    var searchText = document.getElementById("topWarp_searchText").value;
    var searchClass = document.getElementsByName("topWarp_searchClass")[0].value
    if(searchClass == "online") {
    	var gubun   = "1";
        var url     = "/servlet/controller.propose.ProposeCourseServlet?p_process=TotalSubjectList&s_subjnm="+searchText;
    } else {
    	var gubun   = "2";
        var url     = "/servlet/controller.off.ProposeOffServlet?p_process=TotalSubjectList&s_subjnm="+searchText;
    }
    
    menuMainForward(gubun, url);
}

//관련 사이트 링크 셀렉트박스
function topGoSiteSelect() {
    var link1 = document.getElementById("footer_relatedlink").value;
    var arr = link1.split(':');

    if(arr[2] == "Y"){

        alert("로그인 후 이용해주세요");
        return;

    }
    
    if($("#footer_relatedlink").val()!="관련부서 사이트") {
    	window.open("/learn/user/portal/include/linkSite_Action.jsp?p_grcode="+arr[0]+"&p_seq="+arr[1]);
    }
    else alert("이동하실 사이트를 선택하여 주십시요.");
}

// 전체메뉴 펼침/닫기
function openAllMenu() {
    var allmenu = document.getElementById("allmenu");
    var submenus = document.getElementsByName("gnb_sub");
    var submenulen = submenus.length;

    for(var i = 0 ; i < submenulen ; i++) {
            submenus[i].style.display = "none";
    }

    var tempHtml = "<table width='100%'><tr><td><center><img src='/images/portal/common/ajax-loader1.gif'></center></td></tr></tablt>";

    if ( allmenu.style.display == "none") { 
        $('#allmenu').html(tempHtml);
        $('#allmenu').load('/learn/user/portal/include/siteMapPop.jsp'); 
        allmenu.style.display = "block"; 
    } else { 
        allmenu.style.display = "none"; 
    }
}

function mainOver(imgs, idx) {
    var imgObj;
    var subObj = document.getElementsByName("gnb_sub");
    
    var subLength   = document.getElementById("mainListSize").value;
    
    for(var i = 0 ; i < subLength  ; i++) {
        if(i==(idx-1)) {
            imgObj = document.getElementById("menuImg"+(i+1));
            
            if(subObj != null && subObj.length > 0 ) subObj[i].style.display = "block";
            imgover(imgObj);
            
        } else {
            imgObj = document.getElementById("menuImg"+(i+1));
            
            if(subObj != null && subObj.length > 0 ) subObj[i].style.display = "none";
            imgout(imgObj);
        }
    }
    imgover(imgs);
}



function mainOut(imgs, idx) {
	<%  // KOCCA 의 메인화면일때만 서브메뉴가 안보임.
	if( (koccaYn && box.getSession("s_gubun").equals("0"))) { %>
	imgout(imgs);
	<% } %>
    
}
</script>

<form name="topdefaultForm" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'   value=''>
  <input type='hidden' name='gubun'       value='0'>
  <input type='hidden' name='menuid'      value=''>
  <input type='hidden' name='url'         value=''>
  <input type='hidden' name='p_cmuno'     value=''>
  <input type='hidden' name='searchClass' value=''>
  <input type='hidden' name='p_auth'      value=''>
  <input type='hidden' name='p_topuserid' value=''>
  <input type='hidden' name='p_toppwd'    value=''>
</form>