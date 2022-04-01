<script language="JavaScript" type="text/JavaScript">
//개인터뮤니티 홈으로 이동
function uf_moveMyCommunity() {
    if(document.leftFrm.p_static_cmuno.value=='') return;
    document.leftFrm.action = "/servlet/controller.community.CommunityIndexServlet?p_cmuno="+document.leftFrm.p_static_cmuno.value+"&p_static_cmuno="+document.leftFrm.p_static_cmuno.value;
    document.leftFrm.p_process.value = "selectmyindex";

    document.leftFrm.submit();
}

//메뉴페이지로이동
function uf_moveBrdMenuList(menuno,brd_fg) {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else { %>
    document.leftFrm.menuno.value = menuno;
    document.leftFrm.p_process.value = "selectlist";
    document.leftFrm.action = "/servlet/controller.community.CommunityFrBoardServlet?p_menuno="+menuno+"&p_brd_fg="+brd_fg;
    document.leftFrm.submit();
    <% } %>
}

//설문조사페이지로이동
function uf_moveFrPollListPage() {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else { %>
    document.leftFrm.action = "/servlet/controller.community.CommunityFrPollServlet?p_brd_fg=4";
    document.leftFrm.p_process.value = "movelistPage";
    document.leftFrm.submit();
    <% } %>
}
//메일전송페이지로이동
function uf_comSendMail(userid) {
   open_window('msPopNotice', '/servlet/controller.community.CommunityPopUpServlet?p_process=sendmailPage&p_cmuno=<%=v_cmuno%>&p_parent_userid='+userid, 100, 100, 810, 480, 'no', 'no', 'no', 'no', 'no');
  
}

//메일전송페이지로이동
function uf_moveFrPollListPage() {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else { %>
    document.leftFrm.action = "/servlet/controller.community.CommunityFrPollServlet?p_brd_fg=4";
    document.leftFrm.p_process.value = "movelistPage";
    document.leftFrm.submit();
    <% } %>
}


//일정등록페이지로이동
function uf_moveFrCalendarWritePage() {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else { %>
    document.leftFrm.action = "/servlet/controller.community.CommunityFrCalendarServlet?p_if_flag=MM&p_brd_fg=5";
    document.leftFrm.p_process.value = "movelistPage";
    document.leftFrm.submit();
    <% } %>
}
//개인터뮤니티 홈으로 이동
function uf_moveMsMainPage() {
    document.leftFrm.action = "/servlet/controller.community.CommunityMasterServlet";
    document.leftFrm.p_process.value = "selectmsmainPage";
    document.leftFrm.submit();
}

//회원가입 페이지로 이동
function uf_moveFrJoinPage() {
	<% if(leftCloseFg.equals("1")){ %>
    alert('이미 가입되었습니다.');return;
    <% } else if(leftCloseFg.equals("0")){ %>
    alert('현재 가입 승인 신청중입니다..');return;
    <% } else if(leftCloseFg.equals("2")){ %>
    var bRtn = confirm("현재 탈퇴중입니다. 재가입하시겠습니까?");
    if(bRtn) {
    	document.leftFrm.action = "/servlet/controller.community.CommunityFrJoinServlet";
    	document.leftFrm.p_rejoinFlag.value = "Y";
        document.leftFrm.p_process.value = "movePage";
        document.leftFrm.submit();
    } else {
        return;
    }
    <% } else { %>
    document.leftFrm.action = "/servlet/controller.community.CommunityFrJoinServlet";
    document.leftFrm.p_process.value = "movePage";
    document.leftFrm.submit();
    <% } %>
}


//개인정보변경 페이지로 이동
function uf_moveFrMemChangePage() {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else { %>
    document.leftFrm.action = "/servlet/controller.community.CommunityFrMemberServlet";
    document.leftFrm.p_process.value = "movePage";
    document.leftFrm.submit();
    <% } %>
}


//회원정보조회 페이지로 이동
function uf_moveFrMemListPage() {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else { %>
    //document.leftFrm.action = "/servlet/controller.community.CommunityFrMemberServlet";
    //document.leftFrm.p_process.value = "moveMemInfoData";
    //document.leftFrm.submit();
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movememberactivityPage";
    document.form1.submit();
    <% } %>
}


//회원탈퇴 페이지로 이동
function uf_moveFrOutPage() {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else if("01".equals(userInfo.getString("d_grade"))) {%>
    alert('운영자는 탈퇴할 수 없습니다.');return;
    <% } else { %>
    document.leftFrm.action = "/servlet/controller.community.CommunityIndexServlet";
    document.leftFrm.p_process.value = "movememout";
    document.leftFrm.submit();
    <% } %>
}

//회원초대 페이지로 이동
function uf_moveFrInvitationPage() {
	<% if(!memberPermission){ %>
    alert('회원만의공간입니다');return;
    <% } else { %>
    document.leftFrm.action = "/servlet/controller.community.CommunityFrInvitationServlet?p_cmuno=<%=v_cmuno%>";
    document.leftFrm.p_process.value = "movePage";
    document.leftFrm.submit();
   <% } %>
}


//단체알림 페이지로 이동
function uf_msPopNotice() {
	document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "gomsnoticePop";
    document.form1.submit();
    
   //open_window('msPopNotice', '/servlet/controller.community.CommunityPopUpServlet?p_process=gomsnoticePop&p_static_cmuno=<%=box.getString("p_static_cmuno")%>&p_cmuno=<%=v_cmuno%>', 100, 100, 810, 480, 'no', 'no', 'no', 'no', 'no');
  
}
function uf_msPopMember1() {
   open_window('msPopMember1', '/servlet/controller.community.CommunityPopUpServlet?p_process=gomsmemberPop&p_static_cmuno=<%=box.getString("p_static_cmuno")%>&p_cmuno=<%=v_cmuno%>&p_cbxflag=Y', 100, 100, 610, 480, 'no', 'no', 'no', 'no', 'no');
  
}

//기본정보변경 페이지로 이동
function wf_moveRoomOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "moveroomPage";
    document.form1.submit();
}

//회원가입 페이지로 이동
function uf_moveMsMemberJoinOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movemsmemberjoinPage";
    document.form1.submit();
}

//회원등급변경 페이지로 이동
function uf_moveMsMemberGradeOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movemsmembergradePage";
    document.form1.submit();
}

//커뮤니티 등급명변경 페이지로 이동
function uf_moveMsCmuGradeNmOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movemsmembergradenmPage";
    document.form1.submit();
}

//커뮤니티 마스타변경 페이지로 이동
function uf_moveMsMasterChagneNmOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movemasterchagnePage";
    document.form1.submit();
}

//사이트폐쇄 페이지로 이동
function uf_msPopCloseCommunity() {
   document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
   document.form1.p_process.value = "movecommunityclosePage";
   document.form1.submit();
}

//커뮤니티 메뉴등록 페이지로 이동
function uf_moveMsMenuOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movemenuPage";
    document.form1.submit();
}

//커뮤니티 회원활동현황페이지로 이동한다.
function uf_moveMsMemberActivityOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "movememberactivityPage";
    document.form1.submit();
}



//커뮤니티 홍보등록 페이지로 이동한다.
function uf_moveMsPrOK() {
    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "moveprPage";
    document.form1.submit();
}

// 설문 작성 페이지로 이동한다
function uf_moveMsPoll()
{
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "movewirtePage";
    //document.form1.p_manager.value = "manager";
    document.form1.submit();
}
//-->
</script>

            <div id="member_div" class="menu_warp" <%=(s_tabMenu).equals("member") || (s_tabMenu).equals("") ? "" : "style=\"display:none\"" %>>
                <ul class="menu">
                    <li><a href="javascript:uf_moveBrdMenuList(1,0);" <%="1".equals(menuno) ? "style='fontWeight:bold;'" : ""%>>공지사항</a></li>
                    <li>게시판
                    <% if(listMenuType1.size() > 0 ) { %>
                        <ul class="sub">
                    <%                        
                        for(int i = 0; i < listMenuType1.size(); i++) {
                            DataBox dboxMenuType1 = (DataBox)listMenuType1.get(i);
                            if("file".equals(dboxMenuType1.getString("d_directory_fg"))){
                    %>
                            <li>
                                <a href="javascript:uf_moveBrdMenuList(<%=dboxMenuType1.getString("d_menuno")%>,<%=dboxMenuType1.getString("d_brd_fg")%>);" <%=dboxMenuType1.getString("d_menuno").equals(box.getSession("s_menuno")) ? "style='font-weight:bold;'" : ""%>>
                                <%=dboxMenuType1.getString("d_title")%>
                                </a>
                            </li>
                    <%      }
                        }%>     
                        </ul>
                    <% } %>
                    </li>
                    <!--<li>자료실
                    <% if(listMenuType2.size() > 0 ) { %>
                        <ul class="sub">
                    <%                        
                        for(int i = 0; i < listMenuType2.size(); i++) {
                            DataBox dboxMenuType2 = (DataBox)listMenuType2.get(i);
                            if("file".equals(dboxMenuType2.getString("d_directory_fg"))){
                    %>
                            <li>
                                <a href="javascript:uf_moveBrdMenuList(<%=dboxMenuType2.getString("d_menuno")%>,<%=dboxMenuType2.getString("d_brd_fg")%>);" <%=dboxMenuType2.getString("d_menuno").equals(box.getSession("s_menuno")) ? "style='font-weight:bold;'" : ""%>>
                                <%=dboxMenuType2.getString("d_title")%>
                                </a>
                            </li>
                    <%      }
                        }%>     
                        </ul>
                    <% } %>
                    </li>
                    --><!-- <li><a href="javascript:uf_moveBrdMenuList(2,3);" >앨범</a></li>
                    <li><a href="javascript:uf_moveFrPollListPage();">설문조사</a></li>  -->
                </ul>

                <ul class="list">
                    <!-- <li><a href="javascript:uf_moveFrOutPage();">커뮤니티 탈퇴</a></li>  
                    <li><a href="javascript:uf_moveFrInvitationPage();">친구 초대하기</a></li>
                    <li><a href="javascript:uf_moveFrMemListPage();">커뮤니티 회원목록</a></li>
                    <li><a href="javascript:uf_moveFrMemChangePage();">개인정보변경</a></li> -->
                </ul>
            </div>
