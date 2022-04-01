<%@ page import = "com.credu.homepage.LoginBean" %>


    <div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="top영역">
        <%if( box.getSession("tem_grcode").equals("N000001")) { %>
            <tr>
            <%if(referer.equals("Y")){%>
                <td width="25px" >
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <%= box.getSession("tem_grcode").equals("N000001") ? LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") : ""%>
                            </td>
                            <%if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
                            <td >
                                <input type="image" src="/images/portal/btn/btn_top_go.gif" alt="go" class="va_m" onClick='topAdminOpenPage()'/>
                            </td>
                            <%} %>
                        </tr>
                        <% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
                        <tr>
                            <td colspan="2">
                                <select name="p_project" id="p_project" style="height:20px">
                                    <option value="">선택</option>
                                    <option value="2011000011">2010</option>
                                    <option value="2011000006">2011</option>
                                    <option value="2011000010">2012</option>
                                </select>
                                <input type="image" src="/images/portal/homepage_renewal/common/btn_top_go2.gif" alt="프로젝트게시판 go" class="va_m" onClick="javascript:goSubIndex()"/>
                            </td>
                        </tr>
                        <%} %>
                    </table>
                </td>
            <%} %>
                <td colspan="2">
                    <div align="center">
                        <a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/homepage_renewal/common/btn_logo.gif" alt="KOCCA" /></a>
                    </div>
                </td>
                <td align="right" style="vertical-align: bottom;">
                    <a href="http://twitter.com/edukocca" target="_blank"><img src="/images/portal/homepage_renewal/common/t_icon.jpg" alt="트위터" /></a>
                </td>
            </tr>

        <%}else{ %>
            <tr>
                <td width="245px" align="left" style="padding-top:11px"><a href="/servlet/controller.homepage.MainServlet"><img src="/images/newmain_20100625/<%=s_tem_grcodeGLB%>.gif" alt="logo"/></a></td>
                <td width="250px" valign="middle" >
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                        <tr>
                            <td width="64"><img src="/images/newmain_20100625/search_img_01.gif" width="64" height="27" alt="과정검색"/></td>
                            <td align="left" valign="top" background="/images/newmain_20100625/search_bgbg.gif" alt="" style="padding-top:5px">&nbsp;
                                <input name="topWarp_searchText" id="topWarp_searchText" type="text" title="과정검색" size="33" style="height:14px; margin:0 0 0 0; border:1px solid #fff;font-size:12px;padding:1px;" onkeypress="javascript:fnKeyPressEnter(event, 'totalSubjSearch');" />
                            </td>
                            <td width="7"><img src="/images/newmain_20100625/search_img_02.gif" width="7" height="27" alt=""/></td>
                            <td width="45"><img src="/images/newmain_20100625/search_bn_a.gif" alt="검색" name="Image76" width="45" height="27" border="0" id="Image76" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image76','','/images/newmain_20100625/search_bn_o.gif',1)" style="cursor:pointer" onclick="totalSubjSearch();"/></td>
                            <td width="4"><img src="/images/newmain_20100625/search_img_03.gif" width="4" height="27" alt=""/></td>
                        </tr>
                    </table>
                </td>
                <td width="285px" align="right" style="padding-top:45px" >
                    <table border="0" cellspacing="0" cellpadding="0" width="100%" summary="글로벌메뉴영역">
                        <tr>
                            <td>&nbsp;</td>
<%
                // 로그인이 안되었는 경우
                if(topWarp_userid.equals("")) {
                    if(referer.equals("Y")) {
%>
                                    <td><input name="p_topuserid" id="p_topuserid" type="text" class="inbox" onkeypress="moveFocus(event,'p_pwd');" value="" size="10"/>
                                    <input name="p_toppwd" id="p_toppwd" type="password"  onkeypress="fnKeyPressEnter(event, 'topLogin');" value="" size="10"/>
                                    <a href="javascript:topLogin();">확인</a></td>
<%
                    }

                    if (box.getSession("tem_grcode").equals("N000001")) {
%>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" alt="" /></td>
                                    <td width="29px"><a href="javascript:mainmenu('990');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','/images/newmain_20100625/top_m2_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image8','','/images/newmain_20100625/top_m2_o.gif',1)">
                                        <img src="/images/newmain_20100625/top_m2_a.gif" name="Image8" width="29" height="11" border="0" id="Image8"  alt="로그인" /></a></td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="37px"><a href="javascript:mainmenu('991');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','/images/newmain_20100625/top_m3_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image9','','/images/newmain_20100625/top_m3_o.gif',1)">
                                        <img src="/images/newmain_20100625/top_m3_a.gif" name="Image9" width="37" height="11" border="0" id="Image9"  alt="회원가입" /></a></td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="90px"><a href="javascript:mainmenu('992');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image10','','/images/newmain_20100625/btn_search_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image10','','/images/newmain_20100625/btn_search_o.gif',1)">
                                        <img src="/images/newmain_20100625/btn_search_a.gif" name="Image10"  height="11" border="0" id="Image10"  alt="아이디/패스워드찾기" /></a></td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="38px"><a href="javascript:menuForward('4', '11');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image11','','/images/newmain_20100625/top_m4_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image11','','/images/newmain_20100625/top_m4_o.gif',1)">
                                        <img src="/images/newmain_20100625/top_m4_a.gif" name="Image11" width="38" height="11" border="0" id="Image11"  alt="사이트맵" /></a></td>
<%
                    } else {
                        if ( isReject ) {
                            if (ser_nm.equals("mcst.kocca.or.kr") || ser_nm.equals("mcst.edukocca.or.kr") ) {
%>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="37px">
                                        <a href="javascript:alert('아이나루를 통하여 접속해 주세요');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','/images/newmain_20100625/top_m3_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image9','','/images/newmain_20100625/top_m3_o.gif',1)">
                                        <img src="/images/newmain_20100625/top_m3_a.gif" name="Image9" width="37" height="11" border="0" id="Image9"  alt="" /></a>
                                    </td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="90px"><a href="javascript:mainmenu('2');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image10','','/images/newmain_20100625/btn_search_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image10','','/images/newmain_20100625/btn_search_o.gif',1)">
                                        <img src="/images/newmain_20100625/btn_search_a.gif" name="Image10"  height="11" border="0" id="Image10"  alt="아이디/패스워드찾기"/></a></td>

<%
                            }
                        } else {
%>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="37px">
                                        <a href="javascript:mainmenu('1');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','/images/newmain_20100625/top_m3_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image9','','/images/newmain_20100625/top_m3_o.gif',1)">
                                        <img src="/images/newmain_20100625/top_m3_a.gif" name="Image9" width="37" height="11" border="0" id="Image9"  alt="" /></a>
                                    </td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="90px"><a href="javascript:mainmenu('2');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image10','','/images/newmain_20100625/btn_search_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image10','','/images/newmain_20100625/btn_search_o.gif',1)">
                                        <img src="/images/newmain_20100625/btn_search_a.gif" name="Image10"  height="11" border="0" id="Image10"  alt="아이디/패스워드찾기"/></a></td>
<%
                        }

                    }

                } else {    // 로그인이 되었는 경우
%>
                                <% if (box.getSession("tem_grcode").equals("N000001")) {%>
                                <%if(referer.equals("Y"))%> <td width="60px">&nbsp;</td>
                                    <td width="25px"><%= box.getSession("tem_grcode").equals("N000001") ? LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") : ""%>
                                        <% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
                                            <input type="image" src="/images/portal/btn/btn_top_go.gif" alt="go" class="va_m" onClick='topAdminOpenPage()'/>
                                        <% }   %>
                                    </td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif"  alt=""/></td>
                                    <td width="39px"><a href="javascript:mainmenu('998');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image13','','/images/newmain_20100625/btn_logout_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image13','','/images/newmain_20100625/btn_logout_o.gif',1)">
                                        <img src="/images/newmain_20100625/btn_logout_a.gif" name="Image13" id="Image13" alt="로그아웃" /></a></td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="39px"><a href="javascript:mainmenu('993');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image12','','/images/newmain_20100625/btn_info_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image12','','/images/newmain_20100625/btn_info_o.gif',1)">
                                        <img src="/images/newmain_20100625/btn_info_a.gif" name="Image12" id="Image12" alt="회원정보수정" /></a></td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="38px"><a href="javascript:menuForward('4', '11');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image11','','/images/newmain_20100625/top_m4_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image11','','/images/newmain_20100625/top_m4_o.gif',1)">
                                        <img src="/images/newmain_20100625/top_m4_a.gif" name="Image11" width="38" height="11" border="0" id="Image11"  alt="사이트맵"/></a></td>
                                <% } else {%>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="39px"><a href="javascript:mainmenu('3');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image13','','/images/newmain_20100625/btn_logout_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image13','','/images/newmain_20100625/btn_logout_o.gif',1)">
                                        <img src="/images/newmain_20100625/btn_logout_a.gif" name="Image13" id="Image13" alt="로그아웃" /></a></td>
                                    <td width="10px"><img src="/images/newmain_20100625/top_ico.gif" /></td>
                                    <td width="39px"><a href="javascript:mainmenu('4');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image12','','/images/newmain_20100625/btn_info_o.gif',1)" onblur="MM_swapImgRestore()" onfocus="MM_swapImage('Image12','','/images/newmain_20100625/btn_info_o.gif',1)">
                                        <img src="/images/newmain_20100625/btn_info_a.gif" name="Image12" id="Image12" alt="회원정보수정" /></a></td>
                                <%}%>
                            <%}%>

                        </tr>
                    </table>
                </td>
                <td>&nbsp;</td>
            </tr>
           <% } %>
        </table>
       </div>
<script language="JavaScript">
    // 과정검색
    var openBoolean = false;

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


