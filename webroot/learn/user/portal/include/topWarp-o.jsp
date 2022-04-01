<%@ page import = "com.credu.homepage.LoginBean" %>

	<%
	    String v_indexURLGLB = "";
        String s_tem_grcodeGLB = box.getSession("tem_grcode");
        if (koccaYn) {
            v_indexURLGLB = "/indexGame.jsp";
        } else {
            v_indexURLGLB = "/index" + s_tem_grcodeGLB + ".jsp";
        }
        
        String topWarp_userid = box.getSession("userid");
        String topWarp_usernm = box.getSession("name");
        String topWarp_gadmin = box.getSession("gadmin");
        if (topWarp_gadmin.equals("")) {
        	box.setSession("gadmin", "ZZ");
        	topWarp_gadmin = box.getSession("gadmin");
        }
        
     %>
	
	<div class="topwarp">
		<h1><a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/common/logo.gif" alt="KOCCA" /></a></h1>
		<!-- 
		<h1><img src="/images/user/LOGO/kuk.jpg" alt="천안함 장병의 숭고한 희생을 추모합니다" /></h1>
		 -->
		<div class="homemenu_wrap <%=!koccaYn || box.getSession("s_gubun").equals("0") ? "fl_r" : "" %>">
            <dl class="homemenu">
                <% if(topWarp_userid.equals("")) { if (box.getSession("tem_grcode").equals("N000001")) {%>
				<dd><a href="javascript:ssoLogin();"><img src="/images/portal/btn/topmenu1_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="로그인" /></a></dd>
				<dd><a href="javascript:ssoJoin();"><img src="/images/portal/btn/topmenu2_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="회원가입" /></a></dd>
				<dd><a href="javascript:ssoLosspwd();"><img src="/images/portal/btn/topmenu6.gif" alt="아이디/비밀번호찾기" /></a></dd>
                <input name="p_userid" id="p_topuserid" type="hidden" class="inbox" onkeypress="moveFocus(event,'p_pwd');"/>
                <input name="p_pwd" id="p_toppwd" type="hidden" class="inbox" onkeypress="javascript:fnKeyPressEnter(event, 'topLogin');"/>
                <%}
                else {
                	%>
 <!--
                <input name="p_userid" id="p_topuserid" type="hidden" class="inbox" onkeypress="moveFocus(event,'p_pwd');"/>
                <input name="p_pwd" id="p_toppwd" type="hidden" class="inbox" onkeypress="javascript:fnKeyPressEnter(event, 'topLogin');"/>
                 <dd class="id"><input name="p_userid" id="p_topuserid" type="text" class="inbox" onkeypress="moveFocus(event,'p_pwd');"/></dd>
                <dd class="pw"><input name="p_pwd" id="p_toppwd" type="password" class="inbox" onkeypress="javascript:fnKeyPressEnter(event, 'topLogin');"/></dd>
                <dd class="login_btn"><a href="javascript:topLogin();"><img src="/images/portal/btn/btn_main_login.gif" alt="LOGIN" /></a></dd>
 		          <dd><a href="javascript:topMemberJoin();"><img src="/images/portal/btn/topmenu5.gif" alt="회원가입" /></a></dd>
                <dd><a href="javascript:topFindIdPasswd();"><img src="/images/portal/btn/topmenu6.gif" alt="아이디/비밀번호찾기" /></a></dd>  -->
                <dd class="id"><input name="p_userid" id="p_topuserid" type="text" class="inbox" onkeypress="moveFocus(event,'p_pwd');"/></dd>
                <dd class="pw"><input name="p_pwd" id="p_toppwd" type="password" class="inbox" onkeypress="javascript:fnKeyPressEnter(event, 'topLogin');"/></dd>
                <dd class="login_btn"><a href="javascript:topLogin();"><img src="/images/portal/btn/btn_main_login.gif" alt="LOGIN" /></a></dd>
                    <%                }
                } else {%>
                <dt><%=topWarp_usernm %>님 반갑습니다
                    <%= box.getSession("tem_grcode").equals("N000001") ? LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") : ""%>
                    <% if (!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals(""))) {                     %>
                    <input type="image" src="/images/portal/btn/btn_top_go.gif" alt="go" class="va_m" onClick='topAdminOpenPage()'/>
                    <% }   %>
                </dt> 
                <dd><a href="javascript:topMemberInfoModify();"><img src="/images/portal/common/topmenu5_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="개인정보변경" /></a></dd>
                <dd><a href="javascript:topLogout();"><img src="/images/portal/common/topmenu4_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="로그아웃" /></a></dd> 
                <%  }  %>
                <dd class="mg_rn"><a href="javascript:menuForward('4', '11');"><img src="/images/portal/btn/topmenu3_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);"  alt="사이트맵" /></a></dd>
            </dl>
        </div>
	
		<%  if(koccaYn && !box.getSession("s_gubun").equals("0")) { %>
		<div class="class_search">
            <a id = "span_active" class="tit" href="javascript:topClassSearchClickHandler(this);"><img src="/images/portal/common/search_tit1_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="오프라인 과정" /><input name = "topWarp_searchClass" type = "hidden" value = 'online'/></a>
            <div id="search_tit" style="display:none;" onclick="topClassSearchClickHandler(this);" style="cursor:pointer;" >
                <img src="/images/portal/common/search_tit2_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="오프라인 과정" />
                <input name = "topWarp_searchClass" type = "hidden" value = 'offline'/>
            </div>
            <input name = "topWarp_searchText" id = "topWarp_searchText" type="text" onkeypress="javascript:fnKeyPressEnter(event, 'totalSubjSearch');"/>
            <a href="javascript:totalSubjSearch();" class="btn"><img src="/images/portal/btn/btn_search.gif" alt="검색" /></a>
        </div>
        <%  } %>

	</div>
	
<script language="JavaScript">
    // 과정검색
    var openBoolean = false;
	
    function topClassSearchClickHandler(obj) {
        var span_active     = document.getElementById("span_active");
        var span_inactive   = document.getElementById("search_tit");

        var temp_active     = span_active.innerHTML;
        var temp_inactive   = span_inactive.innerHTML;

    	var divSearchTit    = document.getElementById("search_tit");

    	if(obj == null) { 
        	divSearchTit.style.display = "block";
	    	openBoolean = true;
	        return;
    	}

    	if(!openBoolean) {
    		divSearchTit.style.display = "block";
    		openBoolean = true;
    		return;
    	} else {
    		if(obj != null && obj == span_active) {
    			span_active.innerHTML    = temp_active;
    			span_inactive.innerHTML  = temp_inactive;
    		} else if(obj != null && obj == span_inactive) {
    			span_active.innerHTML    = temp_inactive;
                span_inactive.innerHTML  = temp_active;
    		}
    		
    		divSearchTit.style.display = "none";

    		openBoolean = false;
    	}
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
 
    // 로그아웃
    function topLogout(){
<%
String p_originalLoginUserid = box.getSession("p_originalLoginUserid");
if (p_originalLoginUserid == null || p_originalLoginUserid.length()==0) {	%>
    	this.location.href = "http://sso.kocca.or.kr/allLogout.jsp?svrCd=KA";
<%	}	else {		%>
        document.topdefaultForm.p_process.value = "gologout";
        document.topdefaultForm.action          = "/servlet/controller.homepage.LoginServlet";
        document.topdefaultForm.submit();
<%	}	%>
    }

    //로그인 페이지
    function topGoLogin(){
        document.topdefaultForm.p_process.value = "";
        document.topdefaultForm.action          = "/servlet/controller.homepage.MainServlet";
        document.topdefaultForm.submit();
    }

    // 개인정보변경
    function topMemberInfoModify(){
        var url = "/servlet/controller.homepage.MemberInfoServlet?p_process=memberUpdatePageNew";
        document.topdefaultForm.action          = url;
        document.topdefaultForm.gubun.value     = "99";
        document.topdefaultForm.submit();
    }

    // 패스워드 찾기
    function topFindIdPasswd() {
        var frm = document.topdefaultForm;
        //topdefaultForm.p_process.value   = "golosspwd";
        frm.action            = "/servlet/controller.homepage.LoginServlet?p_process=golosspwd";
        frm.submit();
    }

    // 회원가입
    function topMemberJoin() {
        var frm = document.topdefaultForm;
        frm.action            = "/servlet/controller.homepage.MemberJoinServlet?p_process=join";
        frm.submit();
    }

    // 입력 체크
    function topInput_chk()  {
        var userid = document.getElementsByName("p_userid")[0];
        var pwd = document.getElementsByName("p_pwd")[0];

        if(isNull(userid, "아이디") || isNull(pwd, "패스워드")) return true;
        
        return false;
    }

    // 로그인
    function topLogin() {

    	var frm = document.topdefaultForm;

    	var userid = document.getElementsByName("p_userid")[0];
        var pwd = document.getElementsByName("p_pwd")[0];
        
        if (topInput_chk()){
            frm.p_topuserid.value = userid.value;
            frm.p_toppwd.value = pwd.value;
        	frm.p_process.value   = "login";
        	frm.action            = "/servlet/controller.homepage.LoginServlet";
        	frm.submit();
        }
    }

    function moveFocus(event, p) {
        var e = event || window.event;
        var code = e.charCode || e.keyCode;
        if (code == 13) {
        	$('#p_pwd').focus();
            code.keyCode = 0
            return false;
        }
    }
</script>


