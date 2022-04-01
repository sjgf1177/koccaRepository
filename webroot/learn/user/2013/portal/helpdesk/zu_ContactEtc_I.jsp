<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.cp.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "문의 및 제안 - 제휴문의 - 아카데미소개");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script>

function insert_check() {
    if(val_check()){
        document.form1.action = "/servlet/controller.homepage.HomePageContactServlet";
        document.form1.p_process.value = "insertContactEtc";
        document.form1.submit();
    }
}

function val_check(){
    var f = document.form1;

    if(f.username.value == ""){
        alert("성명을 입력해 주세요.");
        f.username.focus();
        return false;
    }

    if(f.tel1.value == "" || f.tel2.value == "" || f.tel3.value == ""){
        alert("연락처를 입력해 주세요.");
        if(f.tel1.value == ""){ f.tel1.focus();}
        if(f.tel2.value == ""){ f.tel2.focus();}
        if(f.tel3.value == ""){ f.tel3.focus();}
        return false;
    }

    if(f.email1.value == "" || f.email2.value == ""){
        alert("이메일 주소를 입력해 주세요.");
        if(f.email1.value == ""){ f.email1.focus();}
        if(f.email2.value == ""){ f.email2.focus();}
        return false;
    }

    if(f.title.value == ""){
        alert("주제를 입력해 주세요.");
        f.title.focus();
        return false;
    }

    if(f.content.value == ""){
        alert("내용을 입력해 주세요.");
        f.content.focus();
        return false;
    }
    return true;
}


function emailChange() {
    var r=$("[name='email3']").val();
    $("[name='email2']").val(r);
    if(r == ""){
        $("[name='email2']").attr("readonly",false);
    }else{
        $("[name='email2']").attr("readonly",true);
    }
}
</script>
<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
                <!-- 아카데미 소개 -->
            <ul class="location">
                <li>홈</li>
                <li>아카데미소개</li>
                <li>
                    제휴문의 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">인사말</a></li>
                        <!-- 2015-03-18 가림
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        //-->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus" class="active">제휴문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
<!-- 2015-01-08 개편삭제
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->

                    </ul>
                </li>
            </ul>
            <!-- 2015-01-08 개편 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus"><strong><u>제휴문의</u></strong></a></li>
                    <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
                </ul>
            </div>
            //-->
        </div>

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
        <form name="form1" method="post" enctype="multipart/form-data" action="/servlet/controller.homepage.HomePageContactServlet">
        <input type = "hidden" name="p_process"    value = "insertContactEtc">
            <h2 class="subTit">제휴문의<!--<img src="/images/2013/introduce/contactus_title.gif" alt="contact us" />//--></h2>
            <div id="subCont">
                <ul class="tabCus">
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus"><img src="/images/2013/introduce/tabcus1_off.jpg" alt="단체수강신청" /></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactetc"><img src="/images/2013/introduce/tabcus2_on.jpg" alt="문의 및 제안" /></a></li>
                </ul>
                <h3 class="blind">문의 및 제안 현재위치</h3>

                <div class="noticBox nbox1">
                    한국콘텐츠아카데미는 고객 여러분의 모든 의견을 겸허하게 듣도록 하겠습니다.
                </div>

                <h4 class="subTit">문의 및 제안 작성</h4>
                <p class="sidetxt"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />는 필수 입력 항목입니다.</p>
                <div class="tableWrap space2">
                    <table class="dataTable" summary="이 표는 문의 및 제안 작성 표로 성명,연락처,이메일,분류,주제,내용,관련링크, 파일첨부 내용으로 구성 되어 있습니다.">
                        <caption>문의 및 제안 작성 표</caption>
                        <colgroup>
                            <col width="18%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row"><label for="dName">성명</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td><input type="text" name="username" id="dName" class="inputText" size="40" /></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="tel">연락처</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td><input type="text" name="tel1" maxlength="3" id="tel" class="inputText" size="3" title="앞 3자리 입력"/>-<input type="text" name="tel2" maxlength="4" class="inputText" size="4" title="가운데 자리 입력" />-<input type="text" name="tel3" maxlength="4" class="inputText" size="4" title="뒤 4자리입력" /></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dEmail">이메일</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td>
                                <input type="text" name="email1" id="dEmail" class="inputText" size="17" title="이메일 아이디 입력"/> @
                                <input type="text" name="email2" class="inputText" size="10" title="이메일 주소 입력"/>
                                <select class="select" name="email3" title="이메일주소 선택" onclick="emailChange()">
                                    <option value="">직접입력</option>
                                    <option value="naver.com" >naver.com</option>
                                    <option value="chol.com" >chol.com</option>
                                    <option value="empal.com" >empal.com</option>
                                    <option value="gmail.com" >gmail.com</option>
                                    <option value="hanmail.net" >hanmail.net</option>
                                    <option value="hanmir.com" >hanmir.com</option>
                                    <option value="hitel.net" >hitel.net</option>
                                    <option value="hotmail.com" >hotmail.com</option>
                                    <option value="korea.com" >korea.com</option>
                                    <option value="lycos.co.kr" >lycos.co.kr</option>
                                    <option value="nate.com" >nate.com</option>
                                    <option value="netian.com" >netian.com</option>
                                    <option value="paran.com" >paran.com</option>
                                    <option value="yahoo.com" >yahoo.com</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dSort">분류</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td>
                                <select name="category" id="dSort" class="select">
                                    <option value="1">일반문의</option>
                                    <option value="2">사업문의</option>
                                    <option value="3">고객제안</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dSubject">주제</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td><input type="text" name="title" id="dSubject" class="inputText" size="100" /></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dsubs">내용</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td>
                                <textarea name="content" id="dsubs" class="textArea" rows="18" cols="10"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dlinks">관련링크</label></th>
                            <td><input type="text" name="link" id="dlinks" class="inputText"  value="http://" /></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dfile">파일첨부</label></th>
                            <td><input type="file" name ="p_file" id="dfile" class="inputFile" size="30" /></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap -->

                <div class="btnArea">
                    <button type="submit" class="btnText" onclick=""><span>위 내용으로 등록합니다.</span></button>
                </div>

            </div>
            <!-- //#subCont -->
        </form>
        </div>

</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
