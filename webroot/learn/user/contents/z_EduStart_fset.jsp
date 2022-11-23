<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    if("Y".equals(box.getSession("tobeyn"))){
  	   response.setHeader("P3P", "CP='CAO PSA CONi OTR OUR DEM ONL'");
     }

    String  p_process   = box.getString("p_process");

    String  p_subj      = box.getString("p_subj");
    String  p_year      = box.getString("p_year");
    String  p_subjseq   = box.getString("p_subjseq");
    String  c_gadmin    = box.getSession("gadmin");
    String  v_sitegubun = box.getString("p_sitegubun");
    String  v_ispreview = box.getString("p_ispreview"); //스콤 맛보기 여부
    String  v_review    = box.getString("p_review");    // 복습

    box.setSession("s_subj",    p_subj);
    box.setSession("s_year",    p_year);
    box.setSession("s_subjseq", p_subjseq);

    //학습권한 얻기 (Y/P/N)
    String  v_eduauth = "";

    if (v_ispreview.equals("Y")) {          // 맛보기 일경우
        box.setSession("s_eduauth","P");    //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
        v_eduauth = "P";
    } else {
        if (v_review.equals("Y")) {
        box.setSession("s_eduauth","P");    //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
        v_eduauth = "P";
        } else {
            v_eduauth = EduEtc1Bean.get_eduAuth(box);
            box.setSession("s_eduauth",v_eduauth);  //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
        }
    }

%>
    <!--<html><head><title>Sorry</title></head><body></body></html>
    <script>
    alert("[<%=box.getSession("gadmin")%>][<%=box.getSession("s_subj")%>]죄송합니다. 학습권한이 없으므로 학습창을 닫습니다.\n\n문의사항은 운영자에게 연락하여 주십시오");
    //window.close();
    </script>-->
<%

    MasterFormData  da  =(MasterFormData)request.getAttribute("MasterFormData");

    String  v_contenttype   = da.getContenttype();
    String  v_mftype        = da.getMftype();       //Frame Structure
    String  v_iscentered    = da.getIscentered();
    String  v_ismfbranch    = da.getIsmfbranch();
    int     v_width         = da.getWidth();
    int     v_height        = da.getHeight();

    box.setSession("s_contenttype", v_contenttype);
    box.setSession("s_mftype",  v_mftype);
    box.setSession("s_ismfbranch",  v_ismfbranch);
    box.setSession("s_width",   v_width);
    box.setSession("s_height",  v_height);

    String  v_base_url      = "/servlet/controller.contents.EduStart?p_process=";
    String  v_url_up        = v_base_url+"fup";
    String  v_url_menu      = v_base_url+"fmenu";
    String  v_url_gong      = "/servlet/controller.study.SubjGongStudyServlet?p_sitegubun="+v_sitegubun+"&p_ispreview="+v_ispreview+"&p_process=select&p_subj="
                            + p_subj+"&p_year="+p_year+"&p_subjseq="+p_subjseq+"&p_mftype="+v_mftype;

    String  v_spaceUrl      = "";                   //중앙정렬시 여백HTML 위치.

    if(v_iscentered.equals("Y"))    v_spaceUrl = EduEtc1Bean.make_eduURL(p_subj,"DOC")+"/docs/menuimg/space.html";

    if(v_contenttype.equals("M") && v_mftype.equals("ND")){
        v_height = 685;
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<title>학습창</title>
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.9.2/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Content-Language" content="euc-kr">
<meta http-equiv="imagetoolbar" content="false">
<meta name="robots" content="all">
<meta name="MSSmartTagsPreventParsing" content="true">
<meta name="description" content="">
<script type="text/javascript" src="/script/portal/backspaceN.js"></script>
<script language="javascript">
    $(document).ready(function(){
        $('#dragpointer').draggable({ revert:false, containment:'#framebox' });
    });
</script>
<script language="javascript">
    function resizeWindow() {
        $(window).load(function(){
            var htop = $('#ietop').outerHeight();
            var hbody = $('#iebody').outerHeight();
            var hmenu = $('#iemenu').outerHeight();
            var wtotal = eval("<%=v_width%>");
            var vmheight = eval("<%=v_height%>");
            var htotal = eval(htop+hbody+hmenu);

            if(htotal == "0") {
                htotal = vmheight;
            }
            //alert(htotal);
            window.resizeTo(wtotal, htotal);

            //alert(hbody);
    //      var docw = eval(document.documentElement.clientWidth);
    //      var doch = eval(document.documentElement.clientHeight);

            var userAgent = navigator.userAgent.toLowerCase();
            var isChrome = (userAgent.indexOf("chrome") > -1);
            var isFireFox = (userAgent.indexOf("firefox") > -1);
            var isSafari = (userAgent.indexOf("safari") > -1);
            var isOpera = (userAgent.indexOf("opera") > -1);
            var isIE8 = (userAgent.indexOf("msie 8.0") > -1);
            var isIE9 = (userAgent.indexOf("msie 9.0") > -1);
            var isIE10 = (userAgent.indexOf("msie 10.0") > -1);
            var isIE11 = (userAgent.indexOf("trident") > -1 && userAgent.indexOf("rv:11") > -1);

            if( isChrome ){
                //alert('구글 크롬 브라우저에서는 원활한 학습이 되지 않을 수 있습니다. 학습창이 나오지 않는 경우에는 F5키를 눌러 새로고침을 해주세요.');
                var docw = eval($(window).width());
                var doch = eval($(window).height());
                var oriwidth = eval(wtotal - docw);
                var oriheight = eval(htotal - doch);
                var resetwidth = eval(wtotal + oriwidth - 17);
                var resetheight = eval(htotal + oriheight - 17);

            } else if($.browser.safari){
                var resetwidth = eval(wtotal + 16);
                var resetheight = eval(htotal + 38);

            } else if( isFireFox ){
                alert('파이어폭스 브라우저에서는 원활한 학습이 되지 않을 수 있습니다. 학습이 올바르게 지원되지 않을 경우 다른 인터넷 익스플로러 또는 사파리, 크롬 브라우저를 통해 학습을 진행해주세요.');
                var docw = eval($('html').width());
                var doch = eval($(window).height());
                var oriwidth = eval(wtotal - docw);
                var oriheight = eval(htotal - doch);
                var resetwidth = eval(wtotal + oriwidth);
                var resetheight = eval(htotal + oriheight);

            } else if ( isIE11 ) {
                //alert('Internet Explorer 11 버전에서는 원활한 학습이 되지 않을 수 있습니다. 학습이 올바르게 지원되지 않을 경우 인터넷 익스플로러 하위 버전을 통해 학습을 진행해주세요.');
                var docw = eval($('html').width());
                var doch = eval($('html').height());
                var oriwidth = eval(wtotal - docw);
                var oriheight = eval(htotal - doch);
                var resetwidth = eval(wtotal + oriwidth);
                var resetheight = eval(htotal + oriheight + 40);

            } else {
                var docw = eval($('html').width());
                var doch = eval($('html').height());
                var oriwidth = eval(wtotal - docw);
                var oriheight = eval(htotal - doch);
                var resetwidth = eval(wtotal + oriwidth);
                var resetheight = eval(htotal + oriheight);
            }

            window.resizeTo(resetwidth, resetheight);

            alert("original w:"+wtotal+"  h:"+htotal+"\n"+"document w:"+docw+"  h:"+doch+"\n"+"o-d w:"+oriwidth+"  h:"+oriheight+"\n"+"o+o w:"+resetwidth+"  h:"+resetheight);

        });
    }
    
    $(document).ready(function(){
    	$("#framebox").css({
    		"position": "relative",
    		"padding-bottom": "60%",
    		"height": "0",
    	});

        if('<%=v_contenttype%>' == 'M' && '<%=v_mftype%>' == 'ND'){
            $("#iebody").css({
                position: "absolute",
                top: "0",
                left: "0",
                width: "100%"
            });
        }else{
            $("#iebody").css({
                position: "absolute",
                top: "0",
                left: "0",
                width: "100%",
                height: "100%"
            });
        }

    	
    });
//     resizeWindow();

    var ebodyLoc = "<%=v_url_gong%>";

</script>
<style>
    html { padding:0px; margin:0px; }
    body { height:100%; width:100%; margin:0px; padding:0px; overflow: hidden; }
    iframe@ebody .body { margin:0; padding:0; }
    frame { overflow:none; margin:0px; padding:0px; }
    iframe { margin:0px; padding:0px; vertical-align:bottom; }
    div { padding:0px; margin:0px; }
    img { border:0px; }

    table#dragpointer { border:0px; position:absolute; z-index:5000; top:0px; left:0px; cursor:pointer; }
    table#dragpointer td.tl { width:10px; height:10px; background-image:url('/images/2012/sub/content/movecon_01.png'); background-repeat:no-repeat; }
    table#dragpointer td.tc { height:10px; background-image:url('/images/2012/sub/content/movecon_02.png'); background-repeat:no-repeat; }
    table#dragpointer td.tr { width:10px; height:10px; background-image:url('/images/2012/sub/content/movecon_03.png'); background-repeat:no-repeat; }
    table#dragpointer td.cl { width:10px; background-image:url('/images/2012/sub/content/movecon_04.png'); background-repeat:no-repeat; }
    table#dragpointer td.cc { background-image:url('/images/2012/sub/content/movecon_05.png'); }
    table#dragpointer td.cr { width:10px; background-image:url('/images/2012/sub/content/movecon_06.png'); background-repeat:no-repeat; }
    table#dragpointer td.bl { width:10px; height:10px; background-image:url('/images/2012/sub/content/movecon_07.png'); background-repeat:no-repeat; }
    table#dragpointer td.bc { height:10px; background-image:url('/images/2012/sub/content/movecon_08.png'); background-repeat:no-repeat; }
    table#dragpointer td.br { width:10px; height:10px; background-image:url('/images/2012/sub/content/movecon_09.png'); background-repeat:no-repeat; }
</style>
</head>
<body>
<%
	if (v_contenttype.equals("M")){
        if (v_mftype.equals("ND")){ %>
            <table id="dragpointer" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="tl"></td>
                    <td class="tc"></td>
                    <td class="tr"></td>
                </tr>
                <tr>
                    <td class="cl"></td>
                    <td class="cc">
                        <a href="javascript:parent.close();" tabindex="1" title="학습을 종료합니다."><img src="/images/2012/sub/content/icon_exit.png" align="middle" alt="학습창닫기"></a>
                        <a title="마우스 왼쪽 버튼을 누른 상태로 드래그하면 위치를 이용할 수 있습니다."><img src="/images/2012/sub/content/move.png" align="middle" alt="네비게이션 이동"></a>
                    </td>
                    <td class="cr"></td>
                </tr>
                <tr>
                    <td class="bl"></td>
                    <td class="bc"></td>
                    <td class="br"></td>
                </tr>
            </table>
            <div id="framebox">
	            <iframe name="ememo" src="" title="빈 프레임입니다." width="0" height="0" marginwidth="0" marginheight="0" frameborder="0" style="position:absolute;"></iframe>
	            <iframe name="echk" src="" title="빈 프레임입니다." width="0" height="0" frameborder="0" marginwidth="0" marginheight="0" style="position:absolute;"></iframe>
	            <iframe id="ietop" name="etop" title="내용 없는 프레임입니다." src="<%=v_url_up%>" frameborder="0" marginwidth="0" marginheight="0" width="<%=v_width%>" height="0" scrolling="no" style="position:absolute;"></iframe>
	            
	            <iframe id="iebody" name="ebody" title="과정 정보 프레임입니다." src="" frameborder="0" marginwidth="0" marginheight="0" framespacing="0" style="overflow-x:hidden; overflow-y:auto;" width="<%=v_width%>" height="<%=v_height%>" allowfullscreen="true"></iframe>

	            <iframe id="iemenu" name="emenu" title="내용 없는 프레임입니다." src="<%=v_url_menu%>" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" width="<%=v_width%>" height="0" style="position:absolute;"></iframe>
	            <iframe src="" name="configs" title="빈 프레임입니다." width="0" height="0" marginwidth="0" marginheight="0" frameborder="0" style="position:absolute;"></iframe>
            </div>
<%      }else{  %>
<!--             <table id="dragpointer" cellspacing="0" cellpadding="0"> -->
<!--                 <tr> -->
<!--                     <td class="tl"></td> -->
<!--                     <td class="tc"></td> -->
<!--                     <td class="tr"></td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td class="cl"></td> -->
<!--                     <td class="cc"> -->
<!--                         <a href="javascript:parent.close();" tabindex="1" title="학습을 종료합니다."><img src="/images/2012/sub/content/icon_exit.png" align="middle" alt="학습창닫기"></a> -->
<!--                         <a title="마우스 왼쪽 버튼을 누른 상태로 드래그하면 위치를 이용할 수 있습니다."><img src="/images/2012/sub/content/move.png" align="middle" alt="네비게이션 이동"></a> -->
<!--                     </td> -->
<!--                     <td class="cr"></td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td class="bl"></td> -->
<!--                     <td class="bc"></td> -->
<!--                     <td class="br"></td> -->
<!--                 </tr> -->
<!--             </table> -->
            <div id="framebox">
            	<iframe name="ememo" src="" title="빈 프레임입니다." width="0" height="0" marginwidth="0" marginheight="0" frameborder="0" style="position:absolute;"></iframe>
            	<iframe name="echk" src="" title="빈 프레임입니다." width="0" height="0" marginwidth="0" marginheight="0" frameborder="0" style="position:absolute;"></iframe>
            	<iframe id="ietop" name="etop" title="내용 없는 프레임입니다." src="<%=v_url_up%>" frameborder="0" marginwidth="0" marginheight="0" width="<%=v_width%>" height="0" scrolling="no" style="position:absolute;"></iframe>
            
            	<iframe id="iebody" name="ebody" title="빈 프레임입니다." src="" frameborder="0" marginwidth="0" marginheight="0" framespacing="0" style="overflow-x:hidden; overflow-y:auto;" width="<%=v_width%>" height="<%=v_height%>" allowfullscreen="true"></iframe>

<%--             <iframe id="iemenu" name="emenu" src="<%=v_url_menu%>" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" width="<%=v_width%>" height="0" style="position:absolute;"></iframe> --%>       
            	<iframe src="" name="configs" title="빈 프레임입니다." width="0" height="0" marginwidth="0" marginheight="0" frameborder="0" style="position:absolute;"></iframe>
            </div>
<%      }
    }else{  %>
<%      if (v_mftype.equals("ND")){ %>
            <table id="dragpointer" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="tl"></td>
                    <td class="tc"></td>
                    <td class="tr"></td>
                </tr>
                <tr>
                    <td class="cl"></td>
                    <td class="cc">
                        <a href="javascript:parent.close();" tabindex="1" title="학습을 종료합니다."><img src="/images/2012/sub/content/icon_exit.png" align="middle" alt="학습창닫기"></a>
                        <a title="마우스 왼쪽 버튼을 누른 상태로 드래그하면 위치를 이용할 수 있습니다."><img src="/images/2012/sub/content/move.png" align="middle" alt="네비게이션 이동"></a>
                    </td>
                    <td class="cr"></td>
                </tr>
                <tr>
                    <td class="bl"></td>
                    <td class="bc"></td>
                    <td class="br"></td>
                </tr>
            </table>
            <div id="framebox">
            <iframe name="ememo" src="" title="빈 프레임입니다." width="0" height="0" frameborder="0" style="position:absolute;"></iframe>
            <iframe name="echk" src="" title="빈 프레임입니다." width="0" height="0" frameborder="0" style="position:absolute;"></iframe>
            <iframe id="ietop" name="etop" src="<%=v_url_up%>" title="내용 없는 프레임입니다." frameborder="0" marginwidth="0" marginheight="0" width="<%=v_width%>" height="60" scrolling="no"></iframe>
            
            <iframe id="iebody" name="ebody" src="" title="빈 프레임입니다." frameborder="0" marginwidth="0" marginheight="0" framespacing="0" style="overflow-x:hidden; overflow-y:auto;" width="<%=v_width%>" height="<%=v_height%>" allowfullscreen="true"></iframe>
            
            <iframe id="iemenu" name="emenu" src="<%=v_url_menu%>" title="내용 없는 프레임입니다." marginwidth="0" marginheight="0" frameborder="0" scrolling="no" width="<%=v_width%>" height="44"></iframe>
            <iframe src="" name="configs" title="빈 프레임입니다." width="0" height="0" frameborder="0" style="position:absolute;"></iframe>
            </div>
<%      }else if (v_mftype.equals("ED")){ %>
            <frameset rows="0,0,0,1*,45" cols="*" frameborder="NO" border="0" framespacing="0">
                <frame src="" title="빈 프레임입니다." name="ememo" scrolling="NO" frameborder="NO">
            <frame src="" title="빈 프레임입니다." name="echk" scrolling="NO" frameborder="NO">
            <frame src="<%=v_url_up%>" title="내용 없는 프레임입니다." name="etop" scrolling="NO" frameborder="NO">
            <frameset cols="*,<%=v_width%>" scrolling="NO" frameborder="NO" border="0" framespacing="0">
                <frame src="/learn/user/contents/overconfig.jsp" title="내용 없는 프레임입니다." name="lefts" scrolling="NO" frameborder="NO">
                <frame src="<%=v_url_gong%>" title="과정 정보 프레임입니다." name="ebody" scrolling="AUTO" frameborder="NO" allowfullscreen="true">
            </frameset>
            <frame src="<%=v_url_menu%>" title="내용 없는 프레임입니다." name="emenu" scrolling="NO" frameborder="NO">
            </frameset>
<%      }else if (v_mftype.equals("NU")){       %>
            <frameset rows="0,1,29,45,1*" cols="*" frameborder="NO" border="0" framespacing="0">
                <frame src="" title="빈 프레임입니다." name="ememo" scrolling="NO" frameborder="NO">
            <frame src="" title="빈 프레임입니다." name="echk" scrolling="NO" frameborder="NO">
            <frame src="<%=v_url_up%>" title="내용 없는 프레임입니다." name="etop" scrolling="NO" frameborder="NO">
            <frame src="<%=v_url_menu%>" title="내용 없는 프레임입니다." name="emenu" scrolling="NO" frameborder="NO">
            <frameset cols="*,<%=v_width%>" scrolling="NO" frameborder="NO" border="0" framespacing="0">
                <frame src="/learn/user/contents/overconfig.jsp" title="내용 없는 프레임입니다." name="lefts" scrolling="NO" frameborder="NO">
                <frame src="<%=v_url_gong%>" title="과정 정보 프레임입니다." name="ebody" scrolling="AUTO" frameborder="NO" allowfullscreen="true">
            </frameset>
            </frameset>
<%      }else if (v_mftype.equals("WL")){       %>
            <frameset rows="0,0,0,1*" cols="*" frameborder="NO" border="0" framespacing="0">
                <frame src="" title="빈 프레임입니다." name="ememo" scrolling="NO" frameborder="NO">
            <frame src="" title="빈 프레임입니다." name="echk" scrolling="NO" frameborder="NO">
            <frame src="<%=v_url_up%>" title="내용 없는 프레임입니다." name="etop" scrolling="NO" frameborder="NO">
            <frameset rows="*" cols="100,1*" frameborder="NO" border="0" framespacing="0">
                <frame src="<%=v_url_menu%>" title="내용 없는 프레임입니다." name="emenu" scrolling="NO" frameborder="NO">
                <frame src="<%=v_url_gong%>" title="과정 정보 프레임입니다." name="ebody" scrolling="AUTO" frameborder="NO" allowfullscreen="true">
            </frameset>
            </frameset>
<%      }else if (v_mftype.equals("NR")){       %>
            <frameset rows="0,0,29,1*" cols="*" frameborder="NO" border="0" framespacing="0">
            <frame src="" title="빈 프레임입니다." name="ememo" scrolling="NO" frameborder="NO">
            <frame src="" title="빈 프레임입니다." name="echk" scrolling="NO" frameborder="NO">
            <frame src="<%=v_url_up%>" title="내용 없는 프레임입니다." name="etop" scrolling="NO" frameborder="NO">
            <frameset rows="*" cols="1*,100" frameborder="NO" border="0" framespacing="0">
                <frame src="<%=v_url_gong%>" title="과정 정보 프레임입니다." name="ebody" scrolling="AUTO" frameborder="NO">
                <frame src="<%=v_url_menu%>" title="내용 없는 프레임입니다." name="emenu" scrolling="NO" frameborder="NO" allowfullscreen="true">
            </frameset>
            </frameset>
<%      }else if (v_mftype.equals("NL")){   %>
            <frameset rows="0,0,29,1*" cols="*" frameborder="NO" border="0" framespacing="0">
                <frame src="" title="빈 프레임입니다." name="ememo" scrolling="NO" frameborder="NO">
            <frame src="" title="빈 프레임입니다." name="echk" scrolling="NO" frameborder="NO">
            <frame src="<%=v_url_up%>" title="내용 없는 프레임입니다." name="etop" scrolling="NO" frameborder="NO">
            <frameset rows="*" cols="100,1*" frameborder="NO" border="0" framespacing="0">
                <frame src="<%=v_url_menu%>" title="내용 없는 프레임입니다."  name="emenu" scrolling="NO" frameborder="NO">
                <frame src="<%=v_url_gong%>" title="과정 정보 프레임입니다." name="ebody" scrolling="AUTO" frameborder="NO" allowfullscreen="true">
            </frameset>
            </frameset>
<%      }
    }   %>
</body>
</html>
