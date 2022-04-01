<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.dunet.common.util.*" %>

<style type="text/css">
    * {margin:0; padding:0;}
    img {border:0; vertical-align:top;}
    ul {list-style:none;}
    
    #wrap {width:921px; margin:0; auto; }
    #top_gnb {width:921px; height:67px; background:url(/images/asp/type1/gnb_bg.jpg) no-repeat;}
    #gnb_menu {float:left; margin-top:0px; clear:both; width:921px; font-size:12px;}
    #gnb_menu li {display:inline; float:left; height:70px;}
    #main {width:921px; height:313px;}
    #lnb {float:left; width:222px;}
    #visual {float:right; width:699px;}

    #gnb_depth2 {margin:45px 0px 0px 200px;display:none; position:relative; top:-26px; left:-20px;}
    #gnb_depth2 li {padding: 0 5px;}
    #gnb_depth3 {margin:45px 0px 0px 360px;display:none; position:relative; top:-26px; left:-20px;}
    #gnb_depth3 li {padding: 0 5px;}
    #gnb_depth4 {margin:45px 0px 0px 520px;display:none; position:relative; top:-26px; left:-20px;}
    #gnb_depth4 li {padding: 0 5px;}
    #gnb_depth5 {margin:45px 0px 0px 600px;display:none; position:relative; top:-26px; left:-20px;}
    #gnb_depth5 li {padding: 0 5px;}
    #gnb_depth6 {margin:45px 0px 0px 610px;display:none; position:relative; top:-26px; left:-20px;}
    #gnb_depth6 li {padding: 0 5px;}
</style>

<script language="javascript" src="/script/flash_TagScript.js"></script>
<script type="text/javascript">

    function toggleSubMenu(menuId, flag, mainyn) {
        var objId = "gnb_depth" + menuId
        var obj = document.getElementById( objId );

        if (obj != null) {
            obj.style.display = (flag) ? "block" : "none";
        }

        var imgId = "oImg" + menuId;
        var imgObj = document.getElementById( imgId );
        imgObj.src = (flag) ? "/images/asp/type1/gnb_m0" + menuId + "_0.jpg" : "/images/asp/type1/gnb_m0" + menuId + ".jpg";
    }

</script>

<input type="hidden" id="mainListSize" name="mainListSize" value="<%=mainListSize %>" />
<!-- <div id="topMenu" class="menu"> //-->
<div id="topMenu" style="text-align:right; position:absolute; width:961px; height:100px; margin:0px 0px 0; font-size:0;  z-index:2;">
<table style="margin-top:0px;" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td valign="top" align="center">

        <!-- 2014-05-27 메뉴 수정. 기존의 flash로 만들어진 메뉴를 제거하고 이미지와 텍스트로 교체
            하지만 기존 코딩 방식이 table로 구성되어 있어서 문제가 많은 2014년 후반기 개편 작업시에 작업 필요 //-->
        <div id="wrap">
			<div id="top_gnb">
				<div id="gnb_menu">
					<ul>
						<li onmouseover="toggleSubMenu('1', true);" onmouseout="toggleSubMenu('1', false);"><a href="/index.jsp"><img src="/images/asp/type1/gnb_m01.jpg" alt="Home" id="oImg1" /></a></li>
						<li onmouseover="toggleSubMenu('2', true);" onmouseout="toggleSubMenu('2', false);"><a href="javascript:menuForward('8', '01');"><img src="/images/asp/type1/gnb_m02.jpg" alt="과정안내" id="oImg2" /></a></li>
						<li onmouseover="toggleSubMenu('3', true);" onmouseout="toggleSubMenu('3', false);"><a href="javascript:menuForward('9', '01');"><img src="/images/asp/type1/gnb_m03.jpg" alt="수강신청" id="oImg3" /></a></li>
						<li onmouseover="toggleSubMenu('4', true);" onmouseout="toggleSubMenu('4', false);"><a href="javascript:menuForward('5', '01');"><img src="/images/asp/type1/gnb_m04.jpg" alt="열린강좌" id="oImg4" /></a></li>
						<li onmouseover="toggleSubMenu('5', true);" onmouseout="toggleSubMenu('5', false);"><a href="javascript:menuForward('3', '11');"><img src="/images/asp/type1/gnb_m05.jpg" alt="나의강의실" id="oImg5" /></a></li>
						<li onmouseover="toggleSubMenu('6', true);" onmouseout="toggleSubMenu('6', false);"><a href="javascript:menuForward('4', '07');"><img src="/images/asp/type1/gnb_m06.jpg" alt="학습지원센터" id="oImg6" /></a></li>
					</ul>

					<ul id="gnb_depth2" onmouseover="toggleSubMenu('2', true);" onmouseout="toggleSubMenu('2', false);">
						<li><a href="javascript:menuForward('8', '01');">교육과정안내</a></li>
						<li><a href="javascript:menuForward('8', '02');">월별교육일정</a></li>
					</ul>
					<ul id="gnb_depth3" onmouseover="toggleSubMenu('3', true);" onmouseout="toggleSubMenu('3', false);">
						<li><a href="javascript:menuForward('9', '01');">수강신청안내</a></li>
						<li><a href="javascript:menuForward('9', '02');">수강신청</a></li>
						<li><a href="javascript:menuForward('9', '03');">수강신청확인/취소</a></li>
					</ul>
					<ul id="gnb_depth4" onmouseover="toggleSubMenu('4', true);" onmouseout="toggleSubMenu('4', false);">
                        <!--
						<li><a href="javascript:menuForward('5', '03');">교육자료실</a></li>
                        //-->
					</ul>
					<ul id="gnb_depth5" onmouseover="toggleSubMenu('5', true);" onmouseout="toggleSubMenu('5', false);">
						<li><a href="javascript:menuForward('3', '11');">수강중인과정</a></li>
						<li><a href="javascript:menuForward('3', '02');">나의교육자료</a></li>
						<li><a href="javascript:menuForward('3', '12');">나의상담내역</a></li>
					</ul>
					<ul id="gnb_depth6" onmouseover="toggleSubMenu('6', true);" onmouseout="toggleSubMenu('6', false);">
						<li><a href="javascript:menuForward('4', '07');">공지사항</a></li>
						<li><a href="javascript:menuForward('4', '01');">FAQ</a></li>
						<li><a href="javascript:menuForward('4', '02');">Q&A</a></li>
						<li><a href="javascript:menuForward('4', '03');">S/W다운로드</a></li>
						<li><a href="javascript:menuForward('4', '05');">학습환경도우미</a></li>
                        <!-- <li><a href="javascript:menuForward('4', '09');">1:1문의</a></li> //-->
					</ul>
				</div>
			</div>
		</div>

        <% if (box.getSession("tem_grcode").equals("N000001") ) {%>
            <!-- <script type="text/javascript">jsFlash('/flash/topnavi.swf','995','41','','','', '');</script> //-->
        <%} else {%>
            <% if (naviType.equals("1")) {%>
                <!-- <script type="text/javascript">jsFlash('/flash/topnavi-new_a.swf','960','68','','','', '');</script> //-->
            <%} else if(naviType.equals("2")) { %>
                <!-- <script type="text/javascript">jsFlash('/flash/topnavi-new_b.swf','960','68','','','', '');</script> //-->
            <%} else {%>
                <!-- <script type="text/javascript">jsFlash('/flash/topnavi.swf','995','41','','','', '');</script> //-->
            <%}%>
        <%}%>
        </td>
        <td valign="top">&nbsp;</td>
    </tr>
</table>
</div>

	<%--<!-- Top Menu 시작 -->--%>
	<%--<ul class="gnb">--%>
	<%--<% if(mainMenuList != null || mainMenuList.size() > 0 ) {--%>
		   <%--for(int top_i = 0 ; top_i <  mainMenuList.size() ; top_i++){--%>
			   <%--DataBox mainBbox = (DataBox)mainMenuList.get(top_i);--%>
		   
			   <%--mMenuGrtype  = mainBbox.getString("d_grtype");--%>
			   <%--mMenuGubun   = mainBbox.getString("d_gubun");--%>
			   <%--mMenuId      = mainBbox.getString("d_menuid");--%>
			   <%--mMenuName    = mainBbox.getString("d_menuname");--%>
			   <%--mMenuUrl     = mainBbox.getString("d_menuurl");--%>
			   <%--mMenuImg     = mainBbox.getString("d_menuimg");--%>
			   <%--mMenuOverImg = mainBbox.getString("d_menuoverimg");--%>
			   <%--mMenuxposition = mainBbox.getInt("d_menuxposition");--%>
			   <%--if (mMenuxposition>0) msgPosition = " style='margin-left:"+mMenuxposition+"px;'"; --%>
			   <%--else msgPosition = "";--%>
			<%--// KOCCA 가 아닌 사이트는 7번 아카데미 소개 생략--%>
			   <%--if( !koccaYn && mMenuGubun.equals("7")) { --%>
				   <%--break;--%>
			   <%--}--%>
			   
		<%--%>--%>
		<%--<li>--%>
			<%--<a href="javascript:menuMainForward('<%=mMenuGubun%>','<%=mMenuUrl %>');">--%>
			<%--<img id="menuImg<%=top_i+1 %>" src="/images/portal/menu/<%=mMenuGubun %>/<%=mMenuImg %>" alt="<%=mMenuName %>"  onmouseover="mainOver(this, <%=top_i+1 %>);" onmouseout="mainOut(this, <%=top_i+1 %>);" onblur="mainOut(this, <%=top_i+1 %>);"/>--%>
			<%--</a>--%>
		<%--<%  // KOCCA 의 메인화면일때만 서브메뉴가 안보임.--%>
		    <%--//if( !(koccaYn && box.getSession("s_gubun").equals("0"))) {--%>
		    	<%--if( !box.getString("mainYn").equals("Y")) {%>--%>
			<%--<div id="gnb_sub" name = "gnb_sub" class="gnb_sub" style="display:none;">--%>
				<%--<!--ul class="sub<%=top_i+1 %>"-->--%>
				<%--<ul class="sub<%=mMenuGubun %>" <%= msgPosition %>>--%>
	    <%--<%      --%>
	            <%--for(int top_j = 0 ; top_j < subMenuList.size(); top_j++) {--%>
	                <%--DataBox subBox = (DataBox)subMenuList.get(top_j);--%>
		            
	                <%--sMenuGubun   = subBox.getString("d_gubun");--%>
		            <%--sMenuId      = subBox.getString("d_menuid");--%>
		            <%--sMenuName    = subBox.getString("d_menuname");--%>
		            <%--sMenuUrl     = subBox.getString("d_menuurl");--%>
		            <%--sMenuImg     = subBox.getString("d_menuimg");--%>
		            <%--sMenuOverImg = subBox.getString("d_menuoverimg");--%>
		            <%--sSubImg       = subBox.getString("d_subimg");--%>
		            <%--sSubOverImg   = subBox.getString("d_suboverimg");--%>
		            <%--sMenuImgPath  = StringUtil.replaceAll(subBox.getString("d_imgpath"), '\\', '/');--%>
		            
	    	       <%--if(sMenuGubun.equals(mMenuGubun)) {--%>
	    <%--%>--%>
					<%--<li><a href="javascript:menuForward('<%=sMenuGubun %>', '<%=sMenuId %>');"><img src="<%=sMenuImgPath+sSubImg %>" alt="<%=sMenuName %>" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>--%>
	    <%--<%        --%>
	               <%--}--%>
	    	   <%--}%>--%>
				<%--</ul>--%>
			<%--</div>--%>
	    <%--<%  } %>--%>
		<%--</li>--%>
	    <%--<% --%>
		   <%--}--%>
	   <%--}    %>--%>

		<%--<li class="allmenu"><a href="javascript:openAllMenu();"><img src="/images/portal/<%=koccaYn ? "common" : "asp" %>/allmenu.gif" alt="전체메뉴" /></a></li>--%>
	<%--</ul>--%>
    <%--<!-- Top Menu 종료 -->	--%>

    
