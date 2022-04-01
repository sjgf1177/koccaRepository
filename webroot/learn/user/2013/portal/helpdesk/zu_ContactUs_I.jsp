<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.cp.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "단체 수강 신청 - 제휴문의 - 아카데미소개");
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
function insert_check() {
    if(val_check()){
        document.form1.action = "/servlet/controller.homepage.HomePageContactServlet";
        document.form1.p_process.value = "insertContactUs";
        document.form1.submit();
    }
}

function val_check(){
    var f = document.form1;

    if(f.company.value == ""){
        alert("단체명을 입력해 주세요.");
        f.company.focus();
        return false;
    }

    if(f.addr.value == ""){
        alert("주소를 입력해 주세요.");
        f.addr.focus();
        return false;
    }

    if(f.com_name.value == ""){
        alert("성명을 입력해 주세요.");
        f.com_name.focus();
        return false;
    }

    if(f.tel1.value == "" || f.tel2.value == "" || f.tel3.value == ""){
        alert("유선전화번호를 입력해 주세요.");
        if(f.tel1.value == ""){ f.tel1.focus();}
        if(f.tel2.value == ""){ f.tel2.focus();}
        if(f.tel3.value == ""){ f.tel3.focus();}
        return false;
    }

    if(f.mobile1.value == "" || f.mobile2.value == "" || f.mobile3.value == ""){
        alert("모바일번호를 입력해 주세요.");
        if(f.mobile1.value == ""){ f.mobile1.focus();}
        if(f.mobile2.value == ""){ f.mobile2.focus();}
        if(f.mobile3.value == ""){ f.mobile3.focus();}
        return false;
    }

    if(f.email1.value == "" || f.email2.value == ""){
        alert("이메일 주소를 입력해 주세요.");
        if(f.email1.value == ""){ f.email1.focus();}
        if(f.email2.value == ""){ f.email2.focus();}
        return false;
    }

    var category = $(':radio[name="category"]:checked').val();
    if(category == "" || category == undefined){
        alert("신청분야를 선택해 주세요.");
        f.category.focus();
        return false;
    }

    if(f.edustart.value == "" || f.eduend.value == ""){
        alert("수강신청기간을 입력해 주세요.");
        f.edustart.focus();
        return false;
    }

    if(f.edustartday.value == ""){
        alert("학습시작일을 입력해 주세요.");
        f.edustartday.focus();
        return false;
    }

    if(f.edustudent.value == ""){
        alert("예상입과인원을 입력해 주세요.");
        f.edustudent.focus();
        return false;
    }

    var c_file = f.p_file.value;
    c_file = c_file.substring(c_file.lastIndexOf(".")+1).toLowerCase();
    if(c_file != "gif" && c_file != "jpg" && c_file != "png"){
        alert("LOGO는 gif, jpg, png 파일만 등록 할 수 있습니다.");
        f.p_file.focus();
        return false;
    }

    if(f.apply_subj.value == ""){
        alert("신청과정을 입력해 주세요.");
        f.apply_subj.focus();
        return false;
    }

    if(f.domain.value == ""){
        alert("도메인을 입력해 주세요.");
        f.domain.focus();
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
<!-- 스크립트영역종료 -->

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
            <h2 class="subTit">제휴문의<!--<img src="/images/2013/introduce/contactus_title.gif" alt="contact us" />//--></h2>

            <div id="subCont">
                <ul class="tabCus">
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus"><img src="/images/2013/introduce/tabcus1_on.jpg" alt="단체수강신청" /></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactetc"><img src="/images/2013/introduce/tabcus2_off.jpg" alt="문의 및 제안" /></a></li>
                </ul>
                <h3 class="blind">단체수강신청 현재위치</h3>

                <div class="noticBox">
                    한국콘텐츠진흥원 사이버아카데미에서는 기관,기업,학교 대상으로 단체수강서비스를 제공하고 있습니다.<br />
                    게임, 문화, 방송 분야의 135개 과정이 무료로 제공됩니다.<br />
                    기업에서는 직무교육의 일환으로 활용되고, 학교에서는 학점 연계 및 부교재의 교육 형태로 이루어지고 있습니다.<br />
                    단체수강서비스는 자체 사이버연수원을 개설해드리며 체계적인 관리를 위한 교육담당자 관리 페이지도 제공됩니다.<br />
                    한국콘텐츠아카데미 소개 자료를 통해 더 자세한 내용 확인 가능하며, 궁금하신 점은 아래 번호로 언제든지 연락주시기 바랍니다.
                    <a href="/upload/contactus/2014_kocca_academy_guide.pdf" target="_blank" class="innerspace" style="vertical-align:top; width:220px;">
                        <img src="/images/fileicon/pdf_file.png" width="21" height="21" alt="" />
                        한국콘텐츠아카데미 소개 바로보기
                    </a>
                    한국콘텐츠아카데미 교육담당 : 02-2161-0077 /  <a href="mailto::edu_kocca@naver.com" alt="edu_kocca@naver.com">edu_kocca@naver.com</a>
                </div>

                <form id="groupinsert" name="form1" method="post" enctype="multipart/form-data" action="/servlet/controller.homepage.HomePageContactServlet">
                <input type="hidden" name="p_process" value="insertContactUs">

                <h4 class="subTit">기본정보입력</h4>
                <p class="sidetxt"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />는 필수 입력 항목입니다.</p>
                <div class="tableWrap space2">
                    <table class="dataTable" summary="이 표는 기본정보입력 표로 단체명,주소,담당자,이메일 내용으로 구성 되어 있습니다.">
                        <caption>기본정보입력 표</caption>
                        <colgroup>
                            <col width="15%"/>
                            <col width="15%"/>
                            <col width="23%"/>
                            <col width="15%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><label for="memberName">단체명</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="4"><input type="text" name="company" id="memberName" class="inputText" size="40" /></td>
                        </tr>
                        <tr>
                            <th><label for="dAddres">주소</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="4"><input type="text" name="addr" id="dAddres" class="inputText" size="50" /></td>
                        </tr>
                        <tr>
                            <th rowspan="2">담장자</th>
                            <td class="tdbg"><label for="dCharge">부서/직급</label></td>
                            <td><input type="text" name="com_dept" id="dCharge" class="inputText" size="20" /></td>
                            <td class="tdbg"><label for="phone">유선전화</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></td>
                            <td>
                                <select name="tel1" id="phone" class="select">
                                    <option value="">지역번호 선택</option>
                                    <option value="070">070</option>
                                    <option value="02">02</option>
                                    <option value="031">031</option>
                                    <option value="032">032</option>
                                    <option value="033">033</option>
                                    <option value="041">041</option>
                                    <option value="042">042</option>
                                    <option value="043">043</option>
                                    <option value="044">044</option>
                                    <option value="051">051</option>
                                    <option value="052">052</option>
                                    <option value="053">053</option>
                                    <option value="054">054</option>
                                    <option value="055">055</option>
                                    <option value="061">061</option>
                                    <option value="062">062</option>
                                    <option value="063">063</option>
                                    <option value="064">064</option>
                                </select>-
                                <input type="text" name="tel2" class="inputText" size="4" maxlength="4" title="유선전화 중간자리 입력" />-
                                <input type="text" name="tel3" class="inputText" size="4" maxlength="4" title="유선전화 끝자리 입력"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdbg"><label for="dName">성명</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></td>
                            <td><input type="text" name="com_name" id="dName" class="inputText" size="20" /></td>
                            <td class="tdbg"><label for="mobile">모바일번호</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></td>
                            <td>
                                <select name="mobile1" id="mobile" class="select">
                                    <option value="">이동번호 선택</option>
                                    <option value="010">010</option>
                                    <option value="011">011</option>
                                    <option value="016">016</option>
                                    <option value="017">017</option>
                                    <option value="018">018</option>
                                    <option value="019">019</option>
                                </select>-
                                <input type="text" name="mobile2" class="inputText" size="4" maxlength="4" title="이동번호 중간자리 입력" />-
                                <input type="text" name="mobile3" class="inputText" size="4" maxlength="4" title="이동번호 끝자리 입력" />
                            </td>
                        </tr>
                        <tr>
                            <th><label for="dEmail">이메일</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="2">
                                <input type="text" name="email1" id="email1" class="inputText" size="12" title="이메일 아이디 입력"/> @
                                <input type="text" name="email2" id="inmeildomain" class="inputText" size="10" title="이메일 주소 입력"/>
                                <select id="selectmailtype" name="email3" onchange="emailChange();" class="select" title="이메일주소 선택">
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
                            <td class="tdbg"><label for="homepage">홈페이지</label></td>
                            <td><input type="text" name="homepage" id="homepage" class="inputText" size="31" value="http://" /></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap -->

                <h4 class="subTit">학습정보입력</h4>
                <p class="sidetxt"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />는 필수 입력 항목입니다.</p>
                <div class="tableWrap space2">
                    <table class="dataTable" summary="이 표는 문의 및 제안 작성 표로 성명,연락처,이메일,분류,주제,내용,관련링크, 파일첨부 내용으로 구성 되어 있습니다.">
                        <caption>문의 및 제안 작성 표</caption>
                        <colgroup>
                            <col width="150"/>
                            <col width="*"/>
                            <col width="150"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row">신청분야<img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="3">
                                <span class="rd2">
                                    <label><input type="radio" name="category" class="inputRadio first" title="방송" value="1" /> 방송</label>
                                    <label><input type="radio" name="category" class="inputRadio" title="게임" value="2" /> 게임</label>
                                    <label><input type="radio" name="category" class="inputRadio" title="문화" value="3" /> 문화</label>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dPeriod">수강신청기간</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td><input type="text" name="edustart" id="startdate" class="inputText" size="10"/> ~ <input type="text" name="eduend" id="enddate" class="inputText" size="10" title="수강시청기간" /></td>
                            <th scope="row"><label for="dPeriod1">학습시작일</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td><input type="text" name="edustartday" id="learningstart" class="inputText" size="10"/></td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dPeriod2">학습기간</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td>
                                <select name="eduperiod" id="dPeriod2" class="select">
                                    <option value="1">1개월</option>
                                    <option value="2">2개월</option>
                                    <option value="3">3개월</option>
                                </select>
                            </td>
                            <th scope="row"><label for="dExpect">예상입과인원</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td>
                                <input type="text" name="edustudent" id="dExpect" class="inputText" size="10"/> 명
                            </td>
                        </tr>
                        <tr>
                            <th><label for="dSort">과정선택제한</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="3">
                                1인 최대
                                <select name="edusubjcnt" id="dSort" class="select">
                                    <option value="1">1개 과정</option>
                                    <option value="2">2개 과정</option>
                                    <option value="3">3개 과정</option>
                                    <option value="4">4개 과정</option>
                                    <option value="5">5개 과정</option>
                                    <option value="6">6개 과정</option>
                                    <option value="7">7개 과정</option>
                                    <option value="8">8개 과정</option>
                                    <option value="9">9개 과정</option>
                                </select> 을 선택할 수 있도록 설정합니다.
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dfile">LOGO업로드</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="3">
                                <input type="file" name="p_file" id="dfile" class="inputFile" size="30" />
                                <span class="tTxt">고객님께서 소속하신 단체(기업,학교,기관)의 로고이미지(gif,jpg,png 등)을 등록해 주세요.</span>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row">신청과정<img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="3" id="requestbox">
                                <span class="rd0"><label><input name="apply_gubun" value="1" id="allcourse" type="checkbox" class="checkBox" /> 전체과정</label></span>
                                <span class="rd1"><label><input name="apply_gubun" value="2" id="boardcastcourse" type="checkbox" class="checkBox" title="신청과정 체크" /> 방송분야 과정</label></span>
                                <span class="rd1"><label><input name="apply_gubun" value="3" id="gamecourse" type="checkbox" class="checkBox" title="신청과정 체크" /> 게임분야 과정</label></span>
                                <span class="rd1"><label><input name="apply_gubun" value="4" id="culturecourse" type="checkbox" class="checkBox" title="신청과정 체크" /> 문화분야 과정</label></span>
                                <textarea name="apply_subj" class="textArea" rows="7" cols="13"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dQues2">기타문의</label></th>
                            <td colspan="3">
                                <textarea name="etc" id="dQues2" class="textArea" rows="7" cols="13"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><label for="dDomain">요청도메인</label><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /></th>
                            <td colspan="3">http:// <input type="text" name="domain" id="dDomain" class="inputText" size="20" /> .edukocca.or.kr</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap -->

                <div class="btnArea">
                    <button type="button" onclick="insert_check();" class="btnText"><span>위 내용으로 신청합니다.</span></button>
                </div>
            </form>
            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

        <script type="text/javascript">
            $(window).load(function() {
                //JqueryUI 달력
                $.datepicker.regional['ko'] = {
                   closeText: '닫기',
                   prevText: '이전',
                   nextText: '다음',
                   currentText: '오늘',
                   monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
                   monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
                   dayNames: ['일','월','화','수','목','금','토'],
                   dayNamesShort: ['일','월','화','수','목','금','토'],
                   dayNamesMin: ['일','월','화','수','목','금','토'],
                   weekHeader: 'Wk',
                   dateFormat: 'yy-mm-dd',
                   firstDay: 0,
                   isRTL:false,
                   showMonthAfterYear:true,
                   yearSuffix: ''};
                $.datepicker.setDefaults( $.datepicker.regional['ko'] );
                $('#startdate').datepicker({ numberOfMonths: 3, showButtonPanel: false });
                $('#enddate').datepicker({ numberOfMonths: 3, showButtonPanel: false });
                $('#learningstart').datepicker({ numberOfMonths: 3, showButtonPanel: false });

                //신청과정 전체선택&해제
                $('#allcourse').click(function() {
                    $(this).attr('checked',$('#allcourse').is(':checked'));
                    $('#requestbox').find('input:checkbox').attr('checked',$('#allcourse').is(':checked'));
                });
            });
        </script>

<%@ include file="/learn/user/2013/portal/include/footer.html"%>