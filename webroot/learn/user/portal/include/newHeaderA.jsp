<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "com.credu.homepage.LoginBean" %>
<style>
 @import url(https://fonts.googleapis.com/earlyaccess/nanumgothic.css); 
 * {margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;font-family: NanumGothic, Nanum Gothic,dotum,Helvetica,verdana,sans-serif;font-size: 12px;font-weight: normal;letter-spacing:-0.5px;font-color:#333;} 
</style>
<script language="JavaScript">
    // 과정검색
    var openBoolean = false;

    $(document).ready(function(){
    	$('.mainmenu > ul').mouseover(function(){
    		showSubMenu('IN');
    	});
    	
    	$('#container, #cnt').mouseover(function(){
    		showSubMenu('OUT');
    	});
    });
    
    function showSubMenu(type){
    	if(type == "IN" ){
    		$('.overmenu').show();
    	}else{
    		$('.overmenu').hide();
    	}
    }
    
    function onoffselect()
    {
        var imgFilename=$("#mainSearch").attr("src");
        var filename="/images/newmain_20100625/search_";
        var fileClick="/images/newmain_20100625/search_";
        var tmp="";

        if(imgFilename.indexOf("search_online.gif")>0)
            tmp="offline";
        else
            tmp="online";

        $("#mainSearch").attr("src",filename+tmp+".gif");
        $("#mainSearchClick").attr("src",fileClick+tmp+".gif");
        $("#topWarp_searchClass").val(tmp+"line");
    }

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
if (p_originalLoginUserid == null || p_originalLoginUserid.length()==0) {   %>
        this.location.href = "http://sso.kocca.or.kr/allLogout.jsp?svrCd=KA";
<%  }   else {      %>
        document.topdefaultForm.p_process.value = "gologout";
        document.topdefaultForm.action          = "/servlet/controller.homepage.LoginServlet";
        document.topdefaultForm.submit();
<%  }   %>
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

    // 로그인
    function topLogin() {

        var frm = document.topdefaultForm;

        var userid = $("[name='p_topuserid']").val();
        var pwd = $("[name='p_toppwd']").val();

        if(userid=="" ||  pwd=="")
            return;

        frm.p_topuserid.value = userid;
        frm.p_toppwd.value = pwd;
        frm.p_process.value   = "login";
        frm.action            = "/servlet/controller.homepage.LoginServlet";
        frm.submit();
    }

    function moveFocus(event, p) {
        var e = event || window.event;
        var code = e.charCode || e.keyCode;
        if (code == 13) {
            $('#p_pwd').focus();
            code.keyCode = 0;
            return false;
        }
    }

    //전체 과정검색
    function totalSubjSearch() {
        var grcode="<%=s_tem_grcodeGLB%>";
        var searchText = $("#topWarp_searchText").val();
        var searchClass = $("#mainSearch").attr("src");

        if(grcode=="N000001")
        {
            if(searchClass.indexOf("online")>=0) {
                var gubun   = "1";
                var url     = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&s_subjnm="+searchText;
            } else {
                var gubun   = "2";
                var url     = "/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&s_subjnm="+searchText;
            }
            menuMainForward(gubun, url);
        }
        else
        {
            var url     = "/servlet/controller.propose.ProposeCourseServlet?p_process=TotalSubjectList&s_subjnm="+searchText;
            menuMainForward_Total(url);
        }

    }

    //즐겨찾기 추가
    function addFavoriteMark(title,url){
        if(window.sidebar){
            //firefox
            window.sidebar.addPanel(title,url,"");
        }else if(window.opera && window.print){
            //opera
            var elem = document.createElement('a');
            elem.setAttribue('href',url);
            elem.setAttribue('title',title);
            elem.setAttribute('rel','sidebar');
            elem.click();
        }else if(document.all){
            //ie
            window.external.AddFavorite(url,title);
        }
    }
    //시작페이지 추가
    function addStartPage(){
    document.body.style.behavior='url(#default#homepage)';
    document.body.setHomePage('http://edu.kocca.or.kr');
    }

  //커뮤니티 새창
    function goSubIndex() {

        if(document.getElementById('p_project').value == ""){
            alert("년도를 선택해주세요");
            return;
        }

        window.open("/servlet/controller.community.CommunityIndexServlet?p_process=selectmyindex&p_cmuno="+document.getElementById('p_project').value, "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,resizable=no, width = 920, height = 660, top=0, left=0");
    }
</script>
	    	<!--오버시 나타나는 메뉴 -->
	        <div class="overmenu" style="display:none;">
	        	<div class="overmenu_cnt">
	            	<div class="overmenu_margin">
	                    <ul style="border-left:1px solid #e1e1e1">
	                        <li><a href="javascript:menuForward('8', '01');">교육과정안내</a></li>
	                        <li><a href="javascript:menuForward('8', '02');">월별교육일정</a></li>
	                    </ul>
	                    <ul>
	                        <li><a href="javascript:menuForward('9', '01');">수강신청안내</a></li>
	                        <li><a href="javascript:menuForward('9', '02');">수강신청</a></li>
	                        <li><a href="javascript:menuForward('9', '03');">수강신청확인/취소</a></li>
	                    </ul>
	                    <ul>
	                    	<li></li>
	                    </ul>
	                    <ul>
	                        <li><a href="javascript:menuForward('3', '11');">수강중인과정</a></li>
	                        <li><a href="javascript:menuForward('3', '02');">나의교육자료</a></li>
	                        <li><a href="javascript:menuForward('3', '12');">나의상담내역</a></li>
	                    </ul>
	                    <ul>
	                        <li><a href="javascript:menuForward('4', '07');">공지사항</a></li>
	                        <li><a href="javascript:menuForward('4', '01');">FAQ</a></li>
	                        <li><a href="javascript:menuForward('4', '02');">Q&amp;A</a></li>
	                        <li><a href="javascript:menuForward('4', '03');">S/W다운로드</a></li>
	                        <li><a href="javascript:menuForward('4', '05');">학습환경도우미</a></li>
	                    </ul>
	                </div>
	            </div>
	        </div>
	        <!--//오버시 나타나는 메뉴 -->
	        
	    	<div class="header_topmenu">
	        	<div class="header_topmenu_mid">
	            	<p style="float:right;">
	            		<%if(box.getSession("userid").equals("")){%>
		            		<%
			               	if ( isReject ) {
			            		if (ser_nm.equals("mcst.kocca.or.kr") || ser_nm.equals("mcst.edukocca.or.kr") ) {
							%>
				                	<span><!--회원가입 --><a href="javascript:alert('아이나루를 통하여 접속해 주세요');" ><img src="/images/portal/asp/typeA/main/topmenu_01.jpg" /></a></span>
				                    <span><!--아이디패스워드찾기 --><a href="javascript:mainmenu('2');" ><img src="/images/portal/asp/typeA/main/topmenu_02.jpg" /></a></span>
							<%
			           			}
			        		} else {
							%>
				                	<span><!--회원가입 --><a href="javascript:mainmenu('1');" ><img src="/images/portal/asp/typeA/main/topmenu_01.jpg" /></a></span>
				                    <span><!--아이디패스워드찾기 --><a href="javascript:mainmenu('2');" ><img src="/images/portal/asp/typeA/main/topmenu_02.jpg" /></a></span>
							<%
			        		}
							%>
						<%}else{%>
		                	<span><!--로그아웃 --><a href="javascript:mainmenu('3');" ><img src="/images/portal/asp/typeA/main/topmenu_03.jpg" /></a></span>
		                    <span><!--개인정보변경 --><a href="javascript:mainmenu('4');" ><img src="/images/portal/asp/typeA/main/topmenu_04.jpg" /></a></span>
						<%}%>
	                </p>
	                <div class="mainmenu">
	                	<h1>
	                		<a href="/servlet/controller.homepage.MainServlet">
	                		<%if(box.getString("p_hostname").equals("localhost:8080")) {%>
	                			<span><img src="/images/newmain_20100625/logo.png" alt="logo"/></span>
	                		<%} else { %>
	                			<img src="/images/newmain_20100625/<%=s_tem_grcodeGLB%>.gif" alt="logo"/>
	                		<%} %>
	                		</a>
	                	</h1>
	                    <!--상단메뉴 -->
	                	<ul>
	                    	<li style="color:#353333"><a href="javascript:menuForward('8', '01');">과정안내</a></li>
	                        <li style="color:#353333"><a href="javascript:menuForward('9', '01');">수강신청</a></li>
	                        <li style="color:#353333"><a href="javascript:menuForward('5', '01');">열린강좌</a></li>
	                        <li><a href="javascript:menuForward('3', '11');" style="color:#00aebc">나의강의실</a></li>
	                        <li><a href="javascript:menuForward('4', '07');" style="color:#00aebc">학습지원센터</a></li>
	                    </ul>
	                    <!--//상단메뉴 -->
	                    
	                </div>
	                <div class="searchzone">
	                	<ul>
	                    	<li style="float:left;width:60px;font-size:14px;border-right:1px solid #717070;font-weight:600;line-height:14px;margin-top:6px;">과정검색</li>
	                        <li style="float:left;width:100px;"><input type="text" id="topWarp_searchText" name="topWarp_searchText" title="과정검색" style="width:97px;height:21px;border:none;margin-left:3px;margin-top:4px;" onkeypress="javascript:fnKeyPressEnter(event, 'totalSubjSearch');" /></li>
	                        <li style="width:25px;float:left;margin-top:3px;"><img src="/images/portal/asp/typeA/main/icon_search.jpg" style="cursor:pointer" onclick="totalSubjSearch();"/></li>
	                    </ul>
	                </div>
	            </div>
	        </div>
