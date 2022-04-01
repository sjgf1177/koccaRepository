<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
//**********************************************************
//  1. 제      목: 온라인과정
//  2. 프로그램명: zu_buyBagSubject_I.jsp
//  3. 개      요: 수강신청 화면
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: swchoi 2013.10.26
//  7. 수      정:
//**********************************************************
%>
<%
    String p_needinput = request.getParameter("p_needinput");

    int i = 0;
    char[]token = p_needinput.toCharArray();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>수강신청</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>

<script type="text/javascript" >

    /**
     * 페이지 로딩할 때 datepicker 초기화
     */
    $(document).ready(function(){
        $("#dtpicker1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker2").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker3").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker4").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker5").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker6").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker7").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker8").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
        $("#dtpicker9").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
    });

    /**
     * 자기 소개 및 지원동기 등 textarea 입력시 입력된 byte 수 표시
     */
    function textareaLength(hwnd,target) {
        var h = $("[name='" + hwnd + "']").val();
        var r = calculate_msglen(h);
        $("#" + target).text(r);
        if(r>3000) {
            $("#"+target).css("color","#FF0000");
        } else {
            $("#"+target).css("color","#000000");
        }
    }

    /**
     * 문자열의 byte 수를 계산하여 리턴한다.
     */
    function calculate_msglen(msg) {
        var nbytes = 0;

        for (i=0; i<msg.length; i++) {
            var ch = msg.charAt(i);
            if(escape(ch).length > 4) {
                nbytes += 2;
            } else if (ch == '\n') {
                if (msg.charAt(i-1) != '\r') {
                    nbytes += 1;
                }
            } else if (ch == '<' || ch == '>') {
                nbytes += 4;
            } else {
                nbytes += 1;
            }
        }

        return nbytes;
    }

</script>
</head>
<body id="popup" style="overflow:auto"><!-- popup size : 730* -->

<div id="minical" onclick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<!-- 스크립트영역종료 -->

    <div id="pop_header">
        <h1><img src="/images/portal/online/pop_h1_tit3.gif" alt="수강신청" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap">
            <dl class="guide_list">
                <dt><img src="/images/portal/popup/tit_guide.gif" alt="GUIDE" /></dt>
                <dd>연락처(핸드폰) 및 이메일은 과정운영에 필수 항목이므로 정확히 입력해 주십시오</dd>
            </dl>

            <h3 class="pop_tit"><img src="/images/portal/common/stit_applystep.gif" alt="신청과정" /></h3>
            <table class="view_bl">
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">과정명</th>
                <td colspan="3" class="end"><strong>2009 농수산홈쇼핑 After Effect HD 과정</strong></td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">분야</th>
                <td style="padding-left:10px;padding-right:0">방송</td>
                <th style="padding-left:0;padding-right:0;text-align:center;">차수</th>
                <td class="end">1차</td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">신청자명</th>
                <td style="padding-left:10px;padding-right:0">홍길동</td>
                <th style="padding-left:0;padding-right:0;text-align:center;">교육비</th>
                <td class="end">50,000원</td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">수강신청기간</th>
                <td style="padding-left:10px;padding-right:0">2013.11.28~2013.11.28</td>
                <th style="padding-left:0;padding-right:0;text-align:center;">과정교육기간</th>
                <td class="end">2013.11.28~2013.11.28 </td>
            </tr>
<%
    if(i < token.length && token[0] == '1') {
%>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">휴대전화</th>
                <td style="padding-left:10px;padding-right:0"><input type="text" class="inbox" style="width:38px;" value="010" /> <input type="text" class="inbox" style="width:38px;" value="010" /> <input type="text" class="inbox" style="width:38px;" value="010" /></td>
                <th style="padding-left:0;padding-right:0;text-align:center;">이메일</th>
                <td class="end" style="padding-left:10px;padding-right:0"><input type="text" class="inbox" style="width:162px;" value="master@kocca.or.kr" /></td>
            </tr>

            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">현소속</th>
                <td style="padding-left:10px;padding-right:0"><input type="text" id="p_comptext" name="p_comptext" value="직장(학교)명" class="inbox"></td>
                <th style="padding-left:0;padding-right:0;text-align:center;">증명사진<br>업로드</th>
                <td style="padding-left:10px;padding-right:0" class="end">
                    <input type="file" name="p_picfile" class="inbox">
                    *용량은 20Kb 이하로 해주세요.&nbsp;&nbsp;&nbsp;<br><label style="color:red">파일명은 영문으로 올려 주세요.</label>
                </td>
            </tr>

<%
    }

        if(token.length > 8 && token[8] == '1') {
%>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">총 경력기간</th>
                <td style="padding-left:10px;padding-right:0" colspan="3" class="end">
                    <input type="text" maxlength="2" size="4" id="p_tcareeryear"  name="p_tcareeryear"  value="" onkeypress="onlyNumber()" class="inbox" /> 년
                    <input type="text" maxlength="2" size="4" id="p_tcareermonth" name="p_tcareermonth" value="" onkeypress="onlyNumber()" class="inbox"/> 개월
                </td>
            </tr>
<%
        }

        if(token.length > 8 && token[6] == '1') {
%>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">자기소개<br>(경력사항)</th>
                <td style="padding-left:10px;padding-right:0" colspan="3" class="end"><textarea rows="5" cols="80" name="p_intro" id="p_selfintro" onkeyup="textareaLength('p_intro','p_selfintroLbael');"></textarea><br/>
                * 총 3000Byte 넘을수 없습니다.(한글,특수문자 2byte 나머지 1Byte)
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;현재글자 수 : <label id="p_selfintroLbael" style="font-weight:bold;"></label>
                </td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">지원동기</th>
                <td style="padding-left:10px;padding-right:0" colspan="3" class="end"><textarea rows="5" cols="80" name="p_motive" id="p_support" onkeyup="textareaLength('p_motive','p_motiveLbael');"></textarea><br/>
                * 총 3000Byte 넘을수 없습니다.(한글,특수문자 2byte 나머지 1Byte)
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;현재글자 수 : <label id="p_motiveLbael" style="font-weight:bold;"></label>
                </td>
            </tr>

<%
        }
%>
            </table>

<%
    if(i < token.length && token[9] == '1') {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_apply_info.gif" alt="참가정보" class="fl_l" />

            <table class="write">
                <colgroup>
                    <col width="16%" />
                    <col width="38%" />
                    <col width="16%" />
                    <col width="20%" />
                </colgroup>
                <tr>
                    <th>이름(필명가능)</th>
                    <td colspan="3" class="end">
                        <input type="text" class="inbox" style="width:162px;" />
                    </td>
                </tr>
                <tr>
                    <th>참가세션<br/>(중복체크가능)</th>
                    <td colspan="3" class="end">
                        <input type="checkbox" id="" name="" value="1" />세션1
                        <input type="checkbox" id="" name="" value="2" />세션2
                        <input type="checkbox" id="" name="" value="3" />세션3
                    </td>
                </tr>
                <tr>
                    <th>소속 및 직함</th>
                    <td colspan="3" class="end">
                        <input type="text" class="inbox" style="width:162px;" />
                    </td>
                </tr>
            </table>
<%
    }

    if(i < token.length && token[10] == '1') {
%>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_channel.gif" alt="개인 온라인 채널" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="19%" /><col width="35%" /><col width="16%" /><col width="20%" /></colgroup>
            <tr>
                <th>이름(닉네임)</th>
                <td colspan="3" class="end">
                    <input type="text" class="inbox" style="width:200px;" name="p_nickname_input" id="oNicknameInput" />
                </td>
            </tr>
            <tr>
                <th>개인 온라인채널<br/>(SNS, 블로그 등)</th>
                <td colspan="3" class="end">
                    <input type="text" name="p_private_sns_input" id="oPrivateSnsInput" class="inbox" style="width:400px;" value=""/>
                </td>
            </tr>
            </table>

<%
    }

    if(i < token.length && token[1] == '1') {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_army.gif" alt="병역구분" class="fl_l" /> <span class="ra_txt"><input type="radio"><strong>해당없음</strong> (해당사항이 없을 경우 체크해주세요)</span></h3>
            <table class="write">
            <tr>
                <td class="intxt end">
                    <span><input type="radio" />필 <input type="radio" class="mg_l10" />미필 <input type="radio" class="mg_l10" />면제</span>
                    <!-- span class="mg_l20">복무기간 <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" />~ <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" /></span-->
                    <span class="mg_l20">복무기간 <input type="text" name="dtpicker1" id="dtpicker1" class="datepicker_input1" style="width:65px;" value="">~ <input type="text" name="dtpicker2" id="dtpicker2" class="datepicker_input1" style="width:65px;" value="" /></span>
                    <span class="mg_l15">면제사유 <input type="text" class="inbox" style="width:113px;" /></span>
                </td>
            </tr>
            </table>
<%
    }

    if(i < token.length && token[2] == '1') {
%>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_curri.gif" alt="교육경험" class="fl_l"/>&nbsp;<label style="color:red; vertical-align: top;"> * 최종학력 및 교육경험은 최소 1개는 입력바랍니다. </label></h3>
            <table class="write">
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr>
                <th>학교명</th>
                <td><input type="text" class="inbox" style="width:162px;" /></td>
                <th>소재지</th>
                <td class="end"><input type="text" class="inbox" style="width:162px;" /></td>
            </tr>
            <tr>
                <th>학과/전공</th>
                <td><input type="text" class="inbox" style="width:162px;" /></td>
                <th>졸업구분</th>
                <td class="end"><select><option>선택</option></select></td>
            </tr>
            <tr>
                <th>재학기간</th>
                <td colspan="3" class="end">
                <!-- <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" />~ <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" /> -->
                <input type="text" name="dtpicker3" id="dtpicker3" class="datepicker_input1" style="width:65px;" value="">~ <input type="text" name="dtpicker4" id="dtpicker4" class="datepicker_input1" style="width:65px;" value="">
                </td>
            </tr>
            </table>
            <p class="btn"><a href="" class="btn_gr"><span>등록</span></a><a href="" class="btn_gr"><span>추가</span></a></p>

            <table class="ac_list">
            <colgroup><col width="114px" /><col width="101px" /><col width="107px" /><col /><col width="101px" /></colgroup>
            <thead>
            <tr>
                <th>학교명</th>
                <th>졸업구분</th>
                <th>졸업(예정)일</th>
                <th>학과/전공</th>
                <th>소재지</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>홍길동대학교</td>
                <td>졸업</td>
                <td>2013.12.31</td>
                <td>정보통신학과/프로그래밍</td>
                <td>서울</td>
            </tr>
            </tbody>
            </table>
<%
    }

    if(i < token.length && token[3] == '1') {
%>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_certificate2.gif" alt="자격증/외국어" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr>
                <th>자격증명/</br> 외국어점수</th>
                <td><input type="text" class="inbox" style="width:162px;" /></td>
                <th>취득일</th>
                <td class="end">
                <!-- <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" /> -->
                <input type="text" name="dtpicker5" id="dtpicker5" class="datepicker_input1" style="width:65px;" value="">
                </td>
            </tr>
            <tr>
                <th>발행처</th>
                <td colspan="3" class="end"><input type="text" class="inbox" style="width:162px;" /></td>
            </tr>
            </table>
            <p class="btn"><a href="javascript:void(0);" class="btn_gr"><span>등록</span></a></p>

            <table class="ac_list">
            <colgroup><col width="221px" /><col width="175px" /><col /></colgroup>
            <thead>
            <tr>
                <th>자격증명/외국어점수</th>
                <th>취득일</th>
                <th>발행처</th>
                <th>삭제</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>정보통신학과/프로그래밍</td>
                <td>2013.12.31</td>
                <td>한국방송콘텐츠 진흥원</td>
                <td><a href="javascript:void(0);"><img src="/images/admin/portal/stat_0.gif"/></a></td>
            </tr>
            <tr>
                <td>정보통신학과/프로그래밍</td>
                <td>2013.12.31</td>
                <td>한국방송콘텐츠 진흥원</td>
                <td><a href="javascript:void(0);"><img src="/images/admin/portal/stat_0.gif"/></a></td>
            </tr>
            </tbody>
            </table>
<%
    }

    if(i < token.length && token[4] == '1') {
%>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_trainnum.gif" alt="연수지명번호" class="fl_l" /> <span class="ra_txt"><input type="radio"><strong>해당없음</strong> (해당사항이 없을 경우 체크해주세요)</span></h3>
            <table class="write">
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr>
                <th>연수지명번호</th>
                <td><input type="text" class="inbox" style="width:162px;" /></td>
                <th>소속교육청</th>
                <td class="end">
                <!-- <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" /> -->
                <input type="text" name="dtpicker6" id="dtpicker6" class="datepicker_input1" style="width:65px;" value="">
                </td>
            </tr>
            </table>
<%
    }

    if(i < token.length && token[5] == '1') {
%>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_career.gif" alt="자격증" class="fl_l" /> <span class="ra_txt"><input type="radio"><strong>해당없음</strong> (해당사항이 없을 경우 체크해주세요)</span></h3>
            <table class="write">
            <colgroup><col width="16%" /><col width="38%" /><col width="16%" /><col width="20%" /></colgroup>
            <tr>
                <th>직업</th>
                <td colspan="3" class="end"><select><option>선택</option></select><select class="mg_l10"><option>선택</option></select></td>
            </tr>
            <tr>
                <th>회사명</th>
                <td><input type="text" class="inbox" style="width:162px;" /></td>
                <th>직무/직급</th>
                <td class="end"><input type="text" class="inbox" style="width:162px;" /></td>
            </tr>
            <tr>
                <th>재직기간</th>
                <td>
                <!-- <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" />~ <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" /> -->
                <input type="text" name="dtpicker7" id="dtpicker7" class="datepicker_input1" style="width:65px;" value="">
                ~ <input type="text" name="dtpicker8" id="dtpicker8" class="datepicker_input1" style="width:65px;" value="">
                </td>
                <th>재직구분</th>
                <td class="end"><select><option>선택</option></select></td>
            </tr>
            </table>
            <p class="btn"><a href="" class="btn_gr"><span>등록</span></a><a href="" class="btn_gr"><span>추가</span></a></p>

            <table class="ac_list">
            <colgroup><col width="67px" /><col width="108px" /><col width="135px" /><col /><col width="150px" /><col width="80px" /></colgroup>
            <thead>
            <tr>
                <th>직업</th>
                <th>직종</th>
                <th>회사명</th>
                <th>직무/직급</th>
                <th>재직기간</th>
                <th>재직구분 </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>회사원</td>
                <td>컴퓨터/인터넷</td>
                <td>방송콘텐츠진흥원</td>
                <td>어학원 웹마스터</td>
                <td>2004.11.29~2013.12.30</td>
                <td>재직</td>
            </tr>
            </tbody>
            </table>
<%
    }

    if(i < token.length && token[6] == '1') {
%>

<%--
            <h3 class="pop_tit"><img src="/images/portal/common/stit_resume.gif" alt="자기소개서" /></h3>
            <table class="write">
            <colgroup><col width="18%" /><col /></colgroup>
            <tr>
                <th>본인소개</th>
                <td><textarea class="inbox" style="width:97%; height:30px;"></textarea></td>
            </tr>
            <tr>
                <th>지원동기</th>
                <td><textarea class="inbox" style="width:97%; height:30px;"></textarea></td>
            </tr>
            <tr>
                <th>향후계획</th>
                <td><textarea class="inbox" style="width:97%; height:30px;"></textarea></td>
            </tr>
            <tr>
                <th>기타</th>
                <td><textarea class="inbox" style="width:97%; height:30px;"></textarea></td>
            </tr>
            </table>
--%>

<%
    }

    if(i < token.length && token[7] == '1') {
%>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_competition.gif" alt="공모및대회 수상이력" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr>
                <th>대회공모전명</th>
                <td><input type="text" class="inbox" style="width:162px;" /></td>
                <th>일자</th>
                <td class="end">
                <!-- <input type="text" class="inbox" style="width:65px;" value="2013.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" /> -->
                <input type="text" name="dtpicker9" id="dtpicker9" class="datepicker_input1" style="width:65px;" value="">
                </td>
            </tr>
            <tr>
                <th>수상/공모내역</th>
                <td colspan="3" class="end"><input type="text" class="inbox" style="width:162px;" /></td>
            </tr>
            </table>
            <p class="btn"><a href="" class="btn_gr"><span>등록</span></a><a href="" class="btn_gr"><span>추가</span></a></p>

            <table class="ac_list">
            <colgroup><col width="221px" /><col width="175px" /><col /></colgroup>
            <thead>
            <tr>
                <th>일자</th>
                <th>대회/공모전명</th>
                <th>수상/공모내역</th>
                <th>삭제</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>2010.05.05</td>
                <td>전국대회(디자인부문)</td>
                <td>금상</td>
                <td><a href="javascript:void(0);"><img src="/images/admin/portal/stat_0.gif"/></a></td>
            </tr>
            </tbody>
            </table>
<%  } %>

        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="" class="btn_red"><span>신청하기</span></a><a href="javascript:window.close();" class="btn_gr"><span>취소</span></a></p>
    </div>
</body>
</html>
