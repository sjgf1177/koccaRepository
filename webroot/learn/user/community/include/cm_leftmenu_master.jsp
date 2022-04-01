<%
	String v_maserno = box.getString("p_masterno");
	String s_masterno = "";
    if(!box.getString("p_masterno").equals("")){
    	box.setSession("s_masterno", v_maserno); 
    	s_masterno = v_maserno;
    } else {
    	s_masterno = v_maserno;
    }
%>


<script language="JavaScript" type="text/JavaScript">

//단체알림 페이지로 이동
function uf_msPopNotice(p) {
	document.leftFrm.p_masterno.value = p;
	document.leftFrm.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.leftFrm.p_process.value = "gomsnoticePop";
    document.leftFrm.submit();
    
   //open_window('msPopNotice', '/servlet/controller.community.CommunityPopUpServlet?p_process=gomsnoticePop&p_static_cmuno=<%=box.getString("p_static_cmuno")%>&p_cmuno=<%=v_cmuno%>', 100, 100, 810, 480, 'no', 'no', 'no', 'no', 'no');
  
}
function uf_msPopMember1() {
   open_window('msPopMember1', '/servlet/controller.community.CommunityPopUpServlet?p_process=gomsmemberPop&p_static_cmuno=<%=box.getString("p_static_cmuno")%>&p_cmuno=<%=v_cmuno%>&p_cbxflag=Y', 100, 100, 610, 480, 'no', 'no', 'no', 'no', 'no');
  
}

//기본정보변경 페이지로 이동
function wf_moveRoomOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "moveroomPage";
    document.leftFrm.submit();
}

//회원가입 페이지로 이동
function uf_moveMsMemberJoinOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "movemsmemberjoinPage";
    document.leftFrm.submit();
}

//회원등급변경 페이지로 이동
function uf_moveMsMemberGradeOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "movemsmembergradePage";
    document.leftFrm.submit();
}

//커뮤니티 등급명변경 페이지로 이동
function uf_moveMsCmuGradeNmOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "movemsmembergradenmPage";
    document.leftFrm.submit();
}

//커뮤니티 마스타변경 페이지로 이동
function uf_moveMsMasterChagneNmOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "movemasterchagnePage";
    document.leftFrm.submit();
}

//사이트폐쇄 페이지로 이동
function uf_msPopCloseCommunity(p) {
	document.leftFrm.p_masterno.value = p;
   document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
   document.leftFrm.p_process.value = "movecommunityclosePage";
   document.leftFrm.submit();
}

//커뮤니티 메뉴등록 페이지로 이동
function uf_moveMsMenuOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "movemenuPage";
    document.leftFrm.submit();
}

//커뮤니티 회원활동현황페이지로 이동한다.
function uf_moveMsMemberActivityOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "movememberactivityPage";
    document.leftFrm.submit();
}



//커뮤니티 홍보등록 페이지로 이동한다.
function uf_moveMsPrOK(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "moveprPage";
    document.leftFrm.submit();
}

// 설문 작성 페이지로 이동한다
function uf_moveMsPoll(p) {
	document.leftFrm.p_masterno.value = p;
    document.leftFrm.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.leftFrm.p_process.value = "movewirtePage";
    //document.leftFrm.p_manager.value = "manager";
    document.leftFrm.submit();
}
//-->
</script>
            <div id="master_div" class="menu_warp" <%=(s_tabMenu).equals("master") ? "" : "style=\"display:none\"" %>>
                <ul class="master_list">
                    <li><a href="javascript:uf_msPopNotice(1);" <%=s_masterno.equals("1") ? "style='font-weight:bold;'" : "" %>>단체알림</a></li>
                    <li><a href="javascript:wf_moveRoomOK(2);" <%=s_masterno.equals("2") ? "style='font-weight:bold;'" : "" %>>기본정보변경</a></li>
                    <li><a href="javascript:uf_moveMsMenuOK(3);" <%=s_masterno.equals("3") ? "style='font-weight:bold;'" : "" %>>메뉴관리</a></li>
                    <li><a href="javascript:uf_moveMsMemberJoinOK(4);" <%=s_masterno.equals("4") ? "style='font-weight:bold;'" : "" %>>회원가입관리</a></li>
                    <li><a href="javascript:uf_moveMsMemberGradeOK(5);" <%=s_masterno.equals("5") ? "style='font-weight:bold;'" : "" %>>회원등급관리</a></li>
                    <li><a href="javascript:uf_moveMsMasterChagneNmOK(6);" <%=s_masterno.equals("6") ? "style='font-weight:bold;'" : "" %>>마스터변경</a></li>
                    <li><a href="javascript:uf_msPopCloseCommunity(7);" <%=s_masterno.equals("7") ? "style='font-weight:bold;'" : "" %>>커뮤니티폐쇄</a></li>
                    <li><a href="javascript:uf_moveMsMemberActivityOK(8);" <%=s_masterno.equals("8") ? "style='font-weight:bold;'" : "" %>>회원활동현황</a></li>
                    <li><a href="javascript:uf_moveMsPrOK(9);" <%=s_masterno.equals("9") ? "style='font-weight:bold;'" : "" %>>홍보요청</a></li>
                    <li><a href="javascript:uf_moveMsPoll(10);" <%=s_masterno.equals("10") ? "style='font-weight:bold;'" : "" %>>설문작성</a></li>
                </ul>
            </div> 
