<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%
    pageContext.setAttribute("CR", "\r");
    pageContext.setAttribute("LF", "\n");
    pageContext.setAttribute("CRLF", "\r\n");
    pageContext.setAttribute("SP", "&nbsp;");
    pageContext.setAttribute("BR", "<br/>");
%>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<link type="text/css" href="/css/jplayer/jplayer.css" rel="stylesheet" />
<link type="text/css" href="/css/jplayer/content.css" rel="stylesheet" />
<link type="text/css" href="/css/jplayer/animate.css" rel="stylesheet" />

<script type="text/javascript" src="/js/jplayer/jquery.jplayer.js"></script>
<script type="text/javascript" src="/js/jplayer/transit.js"></script>
<script type="text/javascript" src="/js/jplayer/jquery.textillate.js"></script>
<script type="text/javascript" src="/js/jplayer/jquery.fittext.js"></script>
<script type="text/javascript" src="/js/jplayer/jquery.lettering.js"></script>
<script type="text/javascript" src="/js/jplayer/siteSecurity_1.1.js"></script>
<script type="text/javascript" src="/js/jplayer/jquery.easing.js"></script>
<script type="text/javascript" src="/js/jplayer/common.js"></script>

<script type="text/javascript">

    $(document).ready(function () {
        $(".video").empty();

        var tagObj = "";

        tagObj =  '<div id="jp_container_1" class="jp-video jp-video-360p subVideo" role="application" aria-label="media player" style="width:100%; height:auto;">';
        tagObj += '    <div class="jp-type-single">';
        tagObj += '        <div id="jquery_jplayer_1" class="jp-jplayer"></div>';
        tagObj += '        <div class="jpback"></div>';
        tagObj += '        <div class="jp-gui">';
        tagObj += '            <div class="jp-video-play"></div>';
        tagObj += '            <div class="jp-gradient-box"></div>';
        tagObj += '            <div class="jp-bottom-controls">';
        tagObj += '                <div class="jp-progress">';
        tagObj += '                    <div class="jp-seek-bar">';
        tagObj += '                        <div class="jp-play-bar"></div>';
        tagObj += '                    </div>';
        tagObj += '                    <div class="jp-limit"></div>';
        tagObj += '                </div>';
        tagObj += '                <div class="jp-interface">';
        tagObj += '                    <div class="jp-inbox">';
        tagObj += '                        <div class="jp-controls-holder">';
        tagObj += '                            <div class="jp-controls">';
        tagObj += '                                <button class="jp-cur-rewind tooltip" role="button" tabIndex="0" title="10초이전"><span class="sub_tooltip">10초이전</span></button>';
        tagObj += '                                <button class="jp-play tooltip" role="button" tabIndex="0" title="재생"><span class="sub_tooltip">재생</span></button>';
        tagObj += '                                <button class="jp-cur-forward tooltip" role="button" tabIndex="0" title="10초건너뛰기"><span class="sub_tooltip">10초건너뛰기</span></button>';
        tagObj += '                                <div class="jp-time-display">';
        tagObj += '                                    <div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>';
        tagObj += '                                    <div class="jp-time-line">/</div>';
        tagObj += '                                    <div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>';
        tagObj += '                                </div>';
        tagObj += '                            </div>';
        tagObj += '                        </div>';
        tagObj += '                        <div class="jp-volume-controls">';
        tagObj += '                            <button class="jp-mute tooltip" role="button" tabIndex="0" title="볼륨"><span class="sub_tooltip">볼륨</span></button>';
        tagObj += '                            <div class="jp-volume-bar">';
        tagObj += '                                <div class="jp-volume-bar-value"></div>';
        tagObj += '                            </div>';
        tagObj += '                        </div>';
        tagObj += '                        <div class="jp-util">';
        tagObj += '                            <ul>';
        tagObj += '                                <li><a href="javascript:;" class="u_index tooltip" title="목차"><span class="sub_tooltip">목차</span></a></li>';
        tagObj += '                            </ul>';
        tagObj += '                            <div class="jp-toggles">';
        tagObj += '                                <div class="jp-speed">';
        tagObj += '                                    <button type="button" class="jp-toggles-text tooltip" title="재생 속도" tabIndex="0"><span class="sub_tooltip">재생속도</span></button>';
        tagObj += '                                    <div class="jp-speed-btn-box">';
        tagObj += '                                        <button class="speed sp2" data-speed="0" title="0.5배속도">0.5</button>';
        tagObj += '                                        <button class="speed sp3" data-speed="2" title="1배속도">1.0</button>';
        tagObj += '                                        <button class="speed sp4" data-speed="4" title="1.5배속도">1.5</button>';
        tagObj += '                                        <button class="speed sp5" data-speed="6" title="2배속도">2.0</button>';
        tagObj += '                                        <h4 style="display: none">재생 속도</h4>';
        tagObj += '                                    </div>';
        tagObj += '                                </div>';
        tagObj += '                            </div>';

        if('${selectOffExpert.d_vtt_path}'.indexOf(".vtt") != -1) {
            tagObj += '                        <button class="jp-script tooltip" role="button" tabIndex="0" title="자막"><span class="sub_tooltip">자막</span></button>';
        }

        tagObj += '                            <button class="jp-full-screen tooltip" role="button" tabIndex="0" title="전체화면"><span class="sub_tooltip">전체화면</span></button>';
        tagObj += '                        </div>';
        tagObj += '                    </div>';
        tagObj += '                </div>';
        tagObj += '            </div>';
        tagObj += '            <div id="indexNavi"><div id="navigation"></div></div>';
        tagObj += '            <div id="chapterNavi"><div id="chapnavigation"></div></div>';
        tagObj += '        </div>';
        tagObj += '        <div class="scriptWrap">';
        tagObj += '            <div class="ScriptPart">';
        tagObj += '                    <span class="teller"></span>';
        tagObj += '                    <span class="scriptTxt"></span>';
        tagObj += '            </div>';
        tagObj += '        </div>';
        tagObj += '    </div>';
        tagObj += '    <div class="circle-static-rewind"><p class="">10초이전</p></div>';
        tagObj += '    <button class="jp-play mobile" role="button" tabindex="0" title=""></button>';
        tagObj += '    <div class="circle-static-forward"><p class="">10초건너뛰기</p></div>';
        tagObj += '</div>';

        $(".video").html(tagObj);
        PlayerEvent();

        $('#jquery_jplayer_1').jPlayer({
            ready: function (event) {
                $(this).jPlayer('setMedia', {
                    m4v: '${selectOffExpert.d_vodurl}',
                    poster: '/images/jplayer/cover.png' //썸네일
                });

                if(!navigator.userAgent.match(/Android|Mobile|iP(hone|od|ad)|BlackBerry|IEMobile|Kindle|NetFront|Silk-Accelerated|(hpw|web)OS|Fennec|Minimo|Opera M(obi|ini)|Blazer|Dolfin|Dolphin|Skyfire|Zune/)){
                    $(this).jPlayer('play');
                }

            },
            swfPath:'js',
            supplied:'m4v',
            size: {
                width:'100%',
                height:'auto',
                cssClass:'jp-video-360p'
            },
            useStateClassSkin: true,
            autoBlur: false,
            smoothPlayBar: true,
            keyEnabled: true,
            remainingDuration: false,
            toggleDuration: false,
            volume: 0.5,
            ended : function(){

            }
        });
    });

    //리스트페이지로 이동
    function selectList() {
        var prePage = document.form1.p_prePage.value;

        if(prePage == "Main") {
            document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
            document.form1.target="_self";
            document.form1.p_process.value = "mainPage";
            document.form1.submit();
        } else {
            document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
            document.form1.p_process.value = "selectPreGoldClassList";
            document.form1.target="_self";
            document.form1.submit();
        }
    }

    function openGoldClass(seq,w,h){

        if (w.length>0) {
            window.open("", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");
        }else{
            window.open("", "ViewVod", "top=0, left=0, width=1008, height=570, status=no, resizable=no");
        }
        document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.p_process.value = "popUpVod";
        document.form1.p_seq.value = seq;
        document.form1.target="ViewVod";
        document.form1.submit();
        document.form1.target="_self";
    }
</script>


<form name = "form1" method = "post">
    <input type = "hidden" name = "p_seq"         value = "<c:out value="${param.p_seq }" />">
    <input type = "hidden" name = "p_searchtext"  value = "<c:out value="${param.p_searchtext }" />">
    <input type = "hidden" name = "p_search"      value = "<c:out value="${param.p_search }" />">
    <input type = "hidden" name = "pageNo"      value = "<c:out value="${param.pageNo }" />">
    <input type = "hidden" name = "p_prePage"     value = "<c:out value="${param.p_prePage }" />">
    <input type = "hidden" name = "p_process"     value = "">
    <input type="hidden" name="gubun" value="${param.gubun }" />
    <input type="hidden" name="menuid" value="${param.menuid }" />
</form>
<section class="container d-flex">
    <div class=""></div>
    <div class="subContainer">
        <div class="sub_section">
            <div class="sub_contents_body">
                <div class="sub_board_header">
                    <%--<jsp:include page="/learn/user/typeB/include_left/left_5.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>--%>
                        00 : ${selectOffExpert}
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_body">
                                <div class="sub_info_body">
                                    <%--                                <p class="sub_course_view_title">과정 소개</p>--%>
                                    <div class="sub_board_header col-right">
                                        <a href="javascript:selectList();" class="list_btn btn">목록으로</a>
                                        <%--                                    <div class="list_title">--%>
                                        <%--                                        <span>열린강좌는 무료로 제공되는 공개강좌로 재미와 깊이가 함께 존재합니다.</span>--%>
                                        <%--                                        <span>훌륭한 명사의 강좌를 만나보실 수 있으며, 누구나 참여할 수 있는 열린 교육공간입니다.</span>--%>
                                        <%--                                    </div>--%>
                                    </div>
                                    <div class="sub_course_alert_box">
                                        <p><c:out value="${selectOffExpert.d_lecnm }" /></p>
                                    </div>
                                    <div class="video_watch_w">

                                        <!-- 영상 들어가는 곳-->
                                        <div class="video">
                                            <video src="<c:out value="${selectOffExpert.d_vodurl}" />" autoplay  controls style="width: 100%; height: auto;"></video>
                                        </div>
                                        <!--// 영상 들어가는 곳-->
                                    </div>
                                    <div class="sub_course_view_wrap">

                                        <div class="info_box">
                                            <table class="tutor">
                                                <colgroup>
                                                    <col width="25%">
                                                    <col width="auto">
                                                </colgroup>
                                                <tbody>
                                                <c:if test="${not empty selectOffExpert.d_tutornm or not empty electOffExpert.d_tutorcareer}">
                                                    <tr>
                                                        <th>강사소개</th>
                                                        <td>
                                                                <%--<img src="/servlet/controller.library.DownloadServlet?p_savefile=<c:out value="${selectOffExpert.d_tutorimg }" />" alt="강사">--%>
                                                            <span><i class="icon_chk"></i>이름 : <c:out value="${selectOffExpert.d_tutornm }" /></span><br><br>
                                                            <span><i class="icon_chk"></i>약력<br/>
                                                            <c:set var="tutorcareer" value="${fn:replace(selectOffExpert.d_tutorcareer, CRLF, BR)}" />
                                                            <c:set var="tutorcareer" value="${fn:replace(tutorcareer, LF, BR)}" />
                                                            <c:set var="tutorcareer" value="${fn:replace(tutorcareer, CR, BR)}" />
                                                            <c:out value="${tutorcareer }" escapeXml="false" />
                                                        </span>
                                                            <span></span>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${not empty selectOffExpert.d_intro}">
                                                    <tr>
                                                        <th>강좌소개 (강좌개요)</th>
                                                        <td><i class="icon_chk"></i>
                                                            <c:set var="intro" value="${fn:replace(selectOffExpert.d_intro, CRLF, BR)}" />
                                                            <c:set var="intro" value="${fn:replace(intro, LF, BR)}" />
                                                            <c:set var="intro" value="${fn:replace(intro, CR, BR)}" />
                                                            <c:out value="${intro }" escapeXml="false" />
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${not empty selectOffExpert.d_contents}">
                                                    <tr>
                                                        <th>강좌내용</th>
                                                        <td><i class="icon_chk"></i>목차</br>
                                                            <c:set var="contents" value="${fn:replace(selectOffExpert.d_contents, CRLF, BR)}" />
                                                            <c:set var="contents" value="${fn:replace(contents, LF, BR)}" />
                                                            <c:set var="contents" value="${fn:replace(contents, CR, BR)}" />
                                                            <c:out value="${contents }" escapeXml="false" />
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                </tbody>
                                            </table>
                                            <%--                                        <div class="course_button">--%>
                                            <%--                                            <a href="javascript:openGoldClass('<c:out value="${param.p_seq }" />', '<c:out value="${selectOffExpert.d_width_s }" />','<c:out value="${selectOffExpert.d_height_s }" />');" class="apply_btn btn btn-purple big_btn">강좌보기</a>--%>
                                            <%--                                            <a href="javascript:selectList();" class="list_btn btn btn-outline-secondary">목록으로</a>--%>
                                            <%--                                        </div>--%>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</section>


<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
<style>
    .info_box table tr{
        border-bottom: none;
    }
</style>
</html>