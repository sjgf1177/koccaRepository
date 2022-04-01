<%
	String menuno    = box.getString("menuno");
    if(!menuno.equals("")) box.setSession("s_menuno", menuno);
	
    String brd_fg    = box.getString("brd_fg");
	
	String tabMenu   = box.getString("tabMenu");
	String s_tabMenu = "";
	if(!tabMenu.equals("")) {
		box.setSession("s_tabMenu", tabMenu);
		s_tabMenu = tabMenu;
	} else {
		s_tabMenu = box.getSession("s_tabMenu");
	}
	
	
  //자료실
  ArrayList listMenuType1   = CommunityMsMenuBean.selectleftCbxList(box.getString("p_cmuno"),"2");

  //게시판
  ArrayList listMenuType2   = CommunityMsMenuBean.selectleftCbxList(box.getString("p_cmuno"),"1");

  //가입한 회원 사용자정보
  DataBox userInfo          = CommunityMsMangeBean.selectMemSingleData(box);
  
  String leftCloseFg = userInfo != null ? userInfo.getString("d_close_fg") : "";
  
  boolean memberPermission = leftCloseFg.equals("1") ? true : false;
  
  
%>
<script language="JavaScript" type="text/JavaScript">
//탭 메뉴 변경
	function change_tab(tabmenu){
		document.topFrm.tabMenu.value = tabmenu;
		document.topFrm.actionflag.value = "changeTab";
		moveMyCommunity()
	}

//전체글 보기
	function totalSearch(){
	    <% if(!memberPermission){ %>
	    alert('회원만의공간입니다');return;
	    <% } else { %>
	    document.leftFrm.action = "/servlet/controller.community.CommunityFrBoardServlet";
	    document.leftFrm.p_select.value = "total";
	    document.leftFrm.p_process.value = "totalsearchPage";
	    document.leftFrm.submit();
	    <% } %>
	}
</script>
<form name="leftFrm" method="post">
<input type = "hidden" name = "p_cmuno"       value = "<%=v_tmp_cmuno%>">
<input type = "hidden" name = "menuno"        value = "<%=box.getSession("s_menuno")%>">
<input type = "hidden" name = "brd_fg"        value = "<%=brd_fg%>">
<input type = "hidden" name = "p_process"     >
<input type = "hidden" name = "p_select"     >
<input type = "hidden" name = "tabmenu"       value = "<%=tabMenu %>"/>
<input type = "hidden" name = "p_rejoinFlag"     >
<input type = "hidden" name = "p_masterno" value = ""/>
        <div id="aside">
            <ul class="info">
                <li><span class="tit">시샵</span> <span class="con"><%=v_cafe_master_nm%></span></li>
                <li><span class="tit">회원수</span> <span class="con"><%=v_tmp_memcnt%>명</span></li>
                <li><span class="tit">생성일자</span> <span class="con"><%=FormatDate.getFormatDate(v_tmp_accetp_dte, "yyyy.MM.dd")%></span></li>
            </ul>
        
            <% if(userInfo != null) { %>
            <p class="btn"><a href="javascript:totalSearch();"><span>커뮤니티 <strong>전체글보기</strong> <img src="/images/portal/ico/ico_btn_arrow.gif" class="ico" /></span></a></p>
            <% } else { %>
            <p class="btn"><a href="javascript:uf_moveFrJoinPage();"><span>커뮤니티 <strong>가입하기</strong> <img src="/images/portal/ico/ico_btn_arrow.gif" class="ico" /></span></a></p>
            <% } %>

			<% if(userInfo != null && "01".equals(userInfo.getString("d_grade"))){ %>
            <ul class="master_tab">
                <li> <a <%=(s_tabMenu).equals("member") || (s_tabMenu).equals("") ? "class=\"on\"" : "" %> href="javascript:change_tab('member');">기본메뉴</a></li>
                <li> <a <%=(s_tabMenu).equals("master") ? "class=\"on\"" : "" %> href="javascript:change_tab('master');">마스터관리</a></span></li>
            </ul>
            <% } %>

            <!-- Member Left Menu Start ! -->
            <%@ include file="/learn/user/community/include/cm_leftmenu_member.jsp"%>
            <!-- Member Left Menu End !  -->
            
            <!-- Master Left Menu Start ! -->
            <% if(userInfo != null && "01".equals(userInfo.getString("d_grade"))){ %>
            <%@ include file="/learn/user/community/include/cm_leftmenu_master.jsp"%>
            <% } %>
            <!-- Master Left Menu End ! -->

            <!--<p class="search_area"><input type="text" name="p_searchtext" class="inbox va_m" style="width:114px;" onkeypress="javascript:fnKeyPressEnter(event, 'totalSearch');"/> <a href="javascript:totalSearch();"><img src="/images/portal/community/btn_search.gif" alt="검색" class="va_m" /></a></p>
--></form>
        </div>
