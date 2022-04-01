<%@ page contentType = "text/html;charset=euc-kr" %>

<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.EncryptUtil" %>

<%
    EncryptUtil encryptUtil = new EncryptUtil("anszhstkdlxm", "wkqkroqkf");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title> 한국콘텐츠아카데미 </title>
  <meta name="Generator" content="EditPlus">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">

<style>
    .menu a {cursor:pointer; font-weight:bold;}
    .menu ul li a{cursor:pointer; font-weight:bold; font-size:10pt;}
    .menu .hide{display:none;}
    .ulSubHide {display:none;}
</style>

<script type="text/javascript" src="/js/2013/jquery-1.8.3.min.js"></script>

<script>
    $(document).ready(function(){
        $(".menu > a").click(function(){
            var firstMenu = $(this).next("ul");
 
            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
            if( firstMenu.is(":visible") ){
                firstMenu.slideUp();
            } else {
                firstMenu.slideDown();
            }
        });

        $(".menu > .hide > li > a").click(function(){
            var submenu = $(this).next("ul");

            var subj = $(this).attr("data-value");

            if( submenu.is(":visible") ){
                submenu.slideUp();
            } else {
                if (subj == "CK06086") {
                    fnViewLecture("http://edu.kocca.or.kr/servlet/controller.contents.EduStart?p_subj=CK06086&p_year=2014&p_subjseq=0027", 1040, 780);
                } else {
                    fnSearchList($(this).next("ul"), subj);
                    submenu.slideDown();
                }
            }
        });

        $(".menu:eq(0)>a").click();
    });

    function fnViewLecture(url, width, height) {
        window.open(url, "previewWin", "top=0, left=0, width="+width+", height="+height+", status=no, resizable=no");
    }

    function fnSearchList( obj, subj ) {
        var list = "";
        $.ajax({
                type : "get"
            ,   url : "/servlet/controller.temp.TempServlet"
            ,   dataType : "xml"
            ,   data : "subj=" + subj
            ,   success : function (xml) {
                    var xmlDoc = $(xml).find("ROOT");

                    var subjObj = $(xmlDoc).find("d_subj");
                    var subjNmObj = $(xmlDoc).find("d_subjnm");
                    var widthObj = $(xmlDoc).find("d_width");
                    var heightObj = $(xmlDoc).find("d_height");
                    var lessonObj = $(xmlDoc).find("d_lesson");
                    var sdescObj = $(xmlDoc).find("d_sdesc");
                    var startingObj = $(xmlDoc).find("d_starting");


                    list = "";
                    for( var i = 0 ; i < subjObj.length; i++) {
                        list += "<li><a href=\"javascript:fnViewLecture('http://edu.kocca.or.kr" + startingObj.eq(i).text() + "', " + widthObj.eq(i).text() + ", " + heightObj.eq(i).text() + ");\">[" + lessonObj.eq(i).text() + "] " + sdescObj.eq(i).text() + "</a></li>";
                    }

                    $(obj).html( list );

                    obj.slideDown();

                }
            ,   complete: function() {
                    // alert("complete");
                }
            ,   error: function() {
                    // alert("error");
                }
        });
    }
</script>
 </head>

 <body>

<%= "asdf : " + encryptUtil.encrypt("") %>
<div>
    <ul>
        <li class="menu">
            <a>정규강좌 목록</a>
            <ul class="hide">
                <li>
                    <a data-value="CG06100" style="text-decoration:none;">아시아 영화</a>
                    <ul class="ulSubHide" id="oList">
                    </ul>
                </li>
                <li>
                    <a data-value="CK08146" style="text-decoration:none;">영화산업의 사례연구</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK07108" style="text-decoration:none;">미국 영화</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK06055" style="text-decoration:none;">문화콘텐츠 성공사례</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CB09143" style="text-decoration:none;">방송콘텐츠 유통과 마케팅</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CG06100" style="text-decoration:none;">3D Data Exporting Techniques</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CG07161" style="text-decoration:none;">MOD 게임제작 및 활용</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CG09192" style="text-decoration:none;">사례를 통한 게임마케팅 연구</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CG09188" style="text-decoration:none;">네트워크 이론</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CG08181" style="text-decoration:none;">게임산업연구</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK06077" style="text-decoration:none;">시간 속의 만화와 캐릭터</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK06079" style="text-decoration:none;">세계의 애니메이션 역사와 사례</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK08149" style="text-decoration:none;">첨단기술 애니메이션</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CB09138" style="text-decoration:none;">미디어속의 양성평등</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK07144" style="text-decoration:none;">[입문] 유비쿼터스사회와 문화콘텐츠</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK06034" style="text-decoration:none;">해외진출전략</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CB09155" style="text-decoration:none;">콘텐츠 비즈니스와 투자전략의 이론과 실제</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK06086" style="text-decoration:none;">문화콘텐츠 스토리텔링 사례</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK10001" style="text-decoration:none;">디자인과 상표(저작권심화)</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK10003" style="text-decoration:none;">문화콘텐츠론</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK06091" style="text-decoration:none;">대중문화분석</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

                <li>
                    <a data-value="CK10002" style="text-decoration:none;">간단하게 배우는 문화콘텐츠 법률</a>
                    <ul class="ulSubHide">
                    </ul>
                </li>

            </ul>
        </li>

        <!--
        <li class="menu">
            <a>열린강좌</a>
            <ul class="hide">
                <li>메뉴2-1</li>
            </ul>
        </li>
        //-->
    </ul>
</div>

  
 </body>
</html>
