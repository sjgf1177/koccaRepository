<%
//**********************************************************
//  1. 제     목: SUBJECT Update
//  2. 프로그램명: zu_Subject_U.jsp
//  3. 개     요: 과정신청
//  4. 환     경: JDK 1.3
//  5. 버     젼: 1.0
//  6. 작     성: 2004.01.14
//  7. 수     정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox dbox = (DataBox)request.getAttribute("resultbox");
    List<DataBox> schoolList = (List<DataBox>) dbox.getObject("schoolList");
    List<DataBox> licenseList = (List<DataBox>) dbox.getObject("licenseList");
    List<DataBox> careerList = (List<DataBox>) dbox.getObject("careerList");
    List<DataBox> competitionList = (List<DataBox>) dbox.getObject("competitionList");

    DataBox offApplyInfo = (DataBox)request.getAttribute("offApplyInfo");
    String needinput = dbox.get("d_needinput", "10000000000");
    String[]hp = new String[3];
    int index = 0;
    StringTokenizer st = new StringTokenizer(dbox.get("d_handphone"), "-");
    while(st.hasMoreTokens()) {
        hp[index++] = st.nextToken();
    }

    String v_upperclass = box.get("p_upperclass");
    if (box == null) box = RequestManager.getBox(request);
    int i = 0;
    char[] token = needinput.toCharArray();//"11111111111111".toCharArray();//
    String now_sukang_count=dbox.get("d_now_sukang_count");  //현재 수강 신청 한 수
    String syudentwait=dbox.get("d_syudentwait");  // 대기자가 몇명으로 셋팅 되어 있는지 나타내는 수
    String studentlimit=dbox.get("d_studentlimit");  //교육정원이 몇명으로 셋팅 되어 있는지 나타내는 수

    String applySession = offApplyInfo.getString("d_apply_session");
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
<script language="JavaScript">
    var a=Number("<%=now_sukang_count%>");
    var b=Number("<%=syudentwait%>");
    var c=Number("<%=studentlimit%>");
   /*
    $("document").ready(function(){
        if((a+1)>(c+b))
        {
            alert("교육 정원을 초과 하여 수강 신청을 하실수 없습니다.");
            window.close();
        }
        else if(a>=c && (a+1)<=(c+b))
        {
            if(!confirm("정원이 마감 되었습니다.\n 대기자로 등록 하시겠습니까?"))
                window.close();
        }
    });
    */
<!--//
//LIST
function whenList(){
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.off.ProposeOffServlet';
    document.form1.p_process.value = 'SubjectList';
    document.form1.p_rprocess.value = '';
    self.location.href = "/servlet/controller.off.ProposeOffServlet?p_process=SubjectList";
}

// 전체과정보기 (이전화면)
function whenSubjList() {

    document.form1.target = "_self";
    document.form1.p_process.value=document.form1.p_rprocess.value;
    document.form1.action=document.form1.p_actionurl.value;
    //alert(document.form1.p_actionurl.value);
    document.form1.submit();
}

    function calculate_msglen(msg)
    {
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

function whenPropose() {
    if( document.form1.p_comptext.value == "" ) {
        alert("직장(학교)명을 입력하세요.");
        document.form1.p_comptext.focus();
        return;
    }

    var f = document.form1;
    if(f.p_tcareeryear){
        if(f.p_tcareeryear.value == ""){
            alert("[총 경력기간] 년도를 입력해 주시기 바랍니다.");
            $("#p_tcareeryear").focus();
            return;
        }
        if(f.p_tcareermonth.value == ""){
            alert("[총 경력기간] 개월을 입력해 주시기 바랍니다.");
            $("#p_tcareermonth").focus();
            return;
        }
        if(f.p_tcareermonth.value > 12){
            alert("[총 경력기간] 개월 수는 12개월 이상 입력하실 수 없습니다.");
            $("#p_tcareermonth").focus();
            return;
        }
    }


    if(!<%= dbox.get("d_specials").substring(3,4).equals("Y")%>) { // 특강 여부
        var r = calculate_msglen($("#p_selfintro").val());
        if(r>3000){
            alert("현재 글자수는 = "+ r + " 자 입니다.\n자기소개는 3000자를 넘을수 없습니다.");
            $("#p_selfintro").focus();
            return;
        }

        var d=calculate_msglen($("#p_support").val());
        if(d>3000){
            alert("현재 글자수는 = "+ r + " 자 입니다.\n지원동기는 3000자를 넘을수 없습니다.");
            $("#p_support").focus();
            return;
        }
    }

    if ( $("#oApplyNameInput").length > 0 ) {
        var sessionChk = $("input[name=chk_apply_session]:checkbox:checked");
        var temp = "";

        if ( $("#oApplyNameInput").val() == "" ) {
            alert("참가정보의 이름을 입력해 주시기 바랍니다.");
            $("#oApplyNameInput").focus();
            return;
        }
        if ( sessionChk.length == 0 ) {
            alert("참가할 세션을 선택해 주시기 바랍니다.");
            $("input[name=chk_apply_session]:checkbox").eq(0).focus();
            return;
        }
        if ( $("#oApplyBelongTitleInput").val() == "" ) {
            alert("참가정보의 소속 및 직함을 입력해 주시기 바랍니다.");
            $("#oApplyBelongTitleInput").focus();
            return;
        }

        $("input[name=chk_apply_session]:checkbox:checked").each( function() {
            temp += $(this).val() + ",";
        });
        $("#oApplySesson").val( temp.substring(0, temp.length - 1) );

        $("#oApplyName").val( $("#oApplyNameInput").val() );
        $("#oApplyBelongTitle").val( $("#oApplyBelongTitleInput").val() );

    }

    if ( $("#oNicknameInput").length > 0 ) {

        if ( $("#oNicknameInput").val() == "" ) {
            alert("개인 온라인 채널의 이름을 입력해 주시기 바랍니다.");
            $("#oNicknameInput").focus();
            return;
        }

        $("#oNickname").val( $("#oNicknameInput").val() );
        $("#oPrivateSns").val( $("#oPrivateSnsInput").val() );
    }

    if(!confirm("<%= dbox.get("d_subjnm") %>과정 수강신청 정보를 수정하시겠습니까?")) {
         return;
        }
        document.form1.p_process.value = "UpdateSubjectEduPropose";
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.off.ProposeOffServlet";
        document.form1.submit();
}

    function textareaLength(hwnd,target)
    {
        var h=$("[name='"+hwnd+"']").val();
        var r=calculate_msglen(h);
        $("#"+target).text(r);
        if(r>3000)
            $("#"+target).css("color","#FF0000");
        else
            $("#"+target).css("color","#000000");
    }

function whenCancel() {
    window.close();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, billYn) {
    if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")) {
     return;
    }
    document.form1.p_process.value = "SubjectEduPropose";
    document.form1.target = "_self";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.action = "/servlet/controller.off.ProposeOffServlet";
    document.form1.submit();
}
    //페이지 이동
    function go(index) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = index;
        document.form1.submit();
    }
    //페이지 이동
    function goPage(pageNum) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = pageNum;
        document.form1.submit();
    }
    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.submit();
    }
    //삭제
    function whenSimpleDelete(deleteType, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) {
        if(!confirm("정말 삭제하시겠습니까?")) return;
        $.post("/servlet/controller.common.AjaxServlet"
            , {   sqlNum:"propose.delete."+deleteType
                , rerurnFunction:"resultSimpleDelete"
                , p_subj:"<%=box.get("p_subj")%>"
                , p_subjseq:"<%=box.get("p_subjseq")%>"
                , p_userid:"<%= box.getSession("userid")%>"
                , param1:param1
                , param2:param2
                , param3:param3
                , param4:param4
                , param5:param5
                , param6:param6
                , param7:param7
                , param8:param8
                , param9:param9
                , param10:param10
            }
            , function(data) {
                if(data.indexOf('true') != -1)
                    $('#'+deleteType+param1).hide();
                $("#ajaxDiv").html(data);
            });
    }
    function resultSimpleDelete(data) {
        if (data.result=='true') {
             alert("삭제했습니다.");
        }
        else alert("삭제가 실패하였습니다.\n"+data.result);

    }
    //저장
    function whenSimpleSave(saveType, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) {
        if(saveType=='study') {
            if($('#p_schoolname').val() == '') {
                alert("학교명을 입력하여 주십시요");
                return;
            }
            if($('#p_place').val() == '') {
                alert("소재지를 입력하여 주십시요");
                return;
            }
            if($('#p_major').val() == '') {
                alert("학과/전공을 입력하여 주십시요");
                return;
            }
            if($('#p_sstart').val() == '' || $('#p_send').val() == '') {
                alert("재학기간을 입력하여 주십시요");
                return;
            }
        }
        else if(saveType=='license') {
            if($('#p_licensename').val() == '') {
                alert("자격증명을 입력하여 주십시요");
                return;
            }
            if($('#p_getdate').val() == '') {
                alert("취득일을 입력하여 주십시요");
                return;
            }
            if($('#p_publish').val() == '') {
                alert("발행처를 입력하여 주십시요");
                return;
            }
        }
        else if(saveType=='workinfo') {
            param2=$("[name='jikGubun']:checked").val() =="0091" ? $("[name='p_jikup']").val():$("[name='p_jikjong']").val()
            param1=$("[name='jikGubun']:checked").val();

            var jik = $("#p_jikup").val() + $("#p_jikjong").val();
            if(jik=="") {
                alert("종사직업을 선택하여 주십시요");
                return;
            }
            if($('#p_officename').val() == '') {
                alert("회사명을 입력하여 주십시요");
                return;
            }
            if($('#p_workstart').val() == '') {
                alert("재직기간을 시작일을 입력하여 주십시요");
                return;
            }
            if($("#p_workstatus option:selected").text() == "퇴사" || $("#p_workstatus option:selected").text() == "휴직") {
                if ($('#p_workend').val() == '') {
                    alert("재직기간 종료일을 입력하여 주십시요.");
                    return;
                }
            }
        }
        else if(saveType=='competition')
        {
            if($("[name='p_competition_nm']").val()=="")
            {
                alert("대회공모전명은 반드시 입력 하셔야 합니다.");
                $("[name='p_competition_nm']").focus();
                return;
            }
            if($("[name='p_competition']").val()=="")
            {
                alert("일자는 반드시 입력 하셔야 합니다.");
                $("[name='p_competition']").focus();
                return;
            }
            if($("[name='p_competition_detail']").val()=="")
            {
                alert("수상/공모내역은 반드시 입력 하셔야 합니다.");
                $("[name='p_competition_detail']").focus();
                return;
            }
        }
        $.post("/servlet/controller.common.AjaxServlet"
            , {   sqlNum:"propose.insert."+saveType
                , rerurnFunction:"resultSimpleSave"
                , p_subj:"<%=box.get("p_subj")%>"
                , p_subjseq:"<%=box.get("p_subjseq")%>"
                , p_userid:"<%= box.getSession("userid")%>"
                , param1:param1
                , param2:param2
                , param3:param3
                , param4:param4
                , param5:param5
                , param6:param6
                , param7:param7
                , param8:param8
                , param9:param9
                , param10:param10
            }
            , function(data) {
                if(data.indexOf('true') != -1)
                     whenSimpleLoad(saveType);
                $("#ajaxDiv").html(data);
                if(saveType=='workinfo') {
                    $('#p_officename').val('');
                    $('#p_workstart').val('');
                    $('#p_workend').val('');
                }
            });
    }
    function resultSimpleSave(data) {
        if (data.result=='true') {
             alert("등록했습니다.");
        }
        else alert("등록이 실패하였습니다.\n"+data.result);

    }
    //출석상태조회
    function whenSimpleLoad(saveType) {
        $.post("/servlet/controller.common.TagServlet"
                , {   sqlNum:"propose.select."+saveType,rerurnFunction:"readData"+saveType, type:6
                    , p_subj:"<%=box.get("p_subj")%>"
                    , p_subjseq:"<%=box.get("p_subjseq")%>"
                    , p_userid:"<%= box.getSession("userid")%>"
                }
                , function(data) {
                    $("#ajaxDiv").html(data);
                });
    }
    function readDatastudy(data) {
         $("#schoolList").html( $("#schoolList").html()+
                 "<tr><td>"+data.d_schoolname+"</td><td>"
                 +data.d_statusnm+"</td><td>"
                 +data.d_send+"</td><td>"
                 +data.d_major+"</td><td>"
                 +data.d_place+"</td><td><a href=\"javascript:whenSimpleDelete('study', "
                 +data.d_seq+");\"><img src=\"/images/admin/portal/stat_0.gif\"/></a></td></tr>");
         $('#p_schoolname').val('');
         $('#p_place').val('');
         $('#p_major').val('');
         $('#p_sstart').val('');
         $('#p_send').val('');
    }
    function readDatalicense(data) {
         $("#licenseList").html( $("#licenseList").html()+
                 "<tr><td>"+data.d_licensename+"</td><td>"
                 +data.d_getdate+"</td><td>"
                 +data.d_publish+"</td><td><a href=\"javascript:whenSimpleDelete('license', "
                 +data.d_seq+");\"><img src=\"/images/admin/portal/stat_0.gif\"/></a></td></tr>");
         $('#p_licensename').val('');
         $('#p_getdate').val('');
         $('#p_publish').val('');
    }
    function readDataworkinfo(data) {
         $("#workinfoList").html( $("#workinfoList").html()+"<tr><td>"
                 +data.d_jikup+"</td><td>"
                 +data.d_jikjong+"</td><td>"
                 +data.d_officename+"</td><td>"
                 +data.d_position+"</td><td>"
                 +data.d_workstart+" ~ "
                 +data.d_workend+"</td><td>"
                 +data.d_workstatus+"</td><td><a href=\"javascript:whenSimpleDelete('workinfo', "+data.d_seq+");\"><img src=\"/images/admin/portal/stat_0.gif\"/></a></td></tr>");
    }

    function readDatacompetition(data) {
         $("#competition").html( $("#competition").html()+
                 "<tr><td>"+data.d_competition_date+"</td><td>"
                 +data.d_competition_nm+"</td><td>"
                 +data.d_competition_detail+"</td><td><a href=\"javascript:whenSimpleDelete('competition', "
                 +data.d_seq+");\"><img src=\"/images/admin/portal/stat_0.gif\"/></a></td></tr>");
         $('#p_competition').val('');
         $('#p_competition_nm').val('');
         $('#p_competition_detail').val('');
    }

 // 숫자만 들어가게 만든다.
    function onlyNumber()
    {
        if(((event.keyCode<48)||(event.keyCode>57)))
        event.returnValue=false;
    }
//-->
$(document).ready(function(){
    $("#p_militarystart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_militaryend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_sstart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_send").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_getdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_workstart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_workend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_competition").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200,yearRange:'-50:+1'});
    $("#p_jikup").show();
    $('#p_jikjong').hide();
    if(document.form1.p_militarystatus) selectMilitary();
});
</script>
</head>
<body id="popup"><!-- popup size : 730* -->
<!-- 스크립트영역 -->
<div id="ajaxDiv"></div>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1" action="/servlet/controller.off.ProposeOffServlet" method="post" enctype="multipart/form-data">
    <input type="hidden" name="p_upperclass"    value="<%=v_upperclass%>"/>
    <input type="hidden" name="p_process"   value="<%=box.get("p_process")%>"/>
    <input type="hidden" name="p_rprocess"  value=""/>
    <input type="hidden" name="p_year"  value="<%=box.get("p_year")%>"/>
    <input type="hidden" name="p_subj"  value="<%=box.get("p_subj")%>"/>
    <input type="hidden" name="p_subjseq"   value="<%=box.get("p_subjseq")%>"/>
    <input type="hidden" name="p_seq"   value="<%=box.get("p_seq")%>"/>
    <input type="hidden" name="p_price" value="<%=dbox.get("d_biyong")%>"/>
    <input type="hidden" name="p_realpayment"   value="<%=dbox.get("d_biyong")%>"/>
    <input type="hidden" name="p_chkfirst"  value="<%=dbox.get("d_autoconfirm")%>"/><%// 자동승인여부 %>
    <input type="hidden" name="p_subjnm"    value=""/>
    <input type="hidden" name="onOff"   value="1"/>
    <input type="hidden" name="p_iscourseYn"    value=""/>

    <input type="hidden" name="p_apply_name" id="oApplyName" value="<%= offApplyInfo.getString("d_apply_name") %>" />
    <input type="hidden" name="p_apply_session" id="oApplySesson" value="<%= applySession %>" />
    <input type="hidden" name="p_apply_belong_title" id="oApplyBelongTitle" value="<%= offApplyInfo.getString("d_apply_belong_title") %>" />

    <input type="hidden" name="p_nickname" id="oNickname" value="<%= offApplyInfo.getString("d_nickname") %>" />
    <input type="hidden" name="p_private_sns" id="oPrivateSns" value="<%= offApplyInfo.getString("d_private_sns") %>" />

    <div id="pop_header">
        <h1><img src="/images/portal/online/pop_h1_tit3.gif" alt="수강신청" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap">
            <dl class="guide_list">
                <dt><img src="/images/portal/popup/tit_guide.gif" alt="GUIDE" /></dt>
                <dd>연락처(핸드폰) 및 이메일은 과정운영에 필수 항목이므로 정확히 입력해 주십시오</dd>
            </dl>

<% if(i < token.length && token[0] == '1') { %>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td width="100px"><h3 class="pop_tit"><img src="/images/portal/common/stit_applystep.gif" alt="신청과정" /></h3></td>
                    <%--<td>--%>
                        <%--<% if(Integer.parseInt(now_sukang_count)>=Integer.parseInt(studentlimit) && (Integer.parseInt(now_sukang_count)+1)<=(Integer.parseInt(studentlimit)+Integer.parseInt(syudentwait))) {%>--%>
                            <%--<h3> * 현재 수강신청시 <label style="color:red;"><font size="5">대기자</font></label>로 분류 됩니다. </h3>--%>
                        <%--<%}else{%>--%>
                            <%--&nbsp;--%>
                        <%--<%}%>--%>
                    <%--</td>--%>
                </tr>
            </table>
            <table class="view_bl">
            <colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
            <tr align="center">
                <th style="padding-left:0;padding-right:0;text-align:center;" >과정명</th>
                <td colspan="3" class="end" style="padding-left:10px;padding-right:0;"><strong><%= dbox.get("d_subjnm") %></strong></td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">분야</th>
                <td style="padding-left:10px;padding-right:0"><%= dbox.get("d_middleclassnm") %></td>
                <th style="padding-left:0;padding-right:0;text-align:center;">차수</th>
                <td style="padding-left:10px;padding-right:0" class="end"><%= dbox.get("d_subjseq") %>차</td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;" >신청자명</th>
                <td style="padding-left:10px;padding-right:0">
                    <input type="hidden" name="p_name" value="<%=dbox.get("d_name")%>"/>
                    <%= dbox.get("d_name") %>
                </td>
                <th style="padding-left:0;padding-right:0;text-align:center;">교육비</th>
                <td style="padding-left:10px;padding-right:0" class="end">
                    <input type="hidden" name="p_biyong" value="<%=dbox.get("d_biyong")%>"/>
                    <%= dbox.getCommaInt("d_biyong") %>원
                </td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">수강신청기간</th>
                <td style="padding-left:10px;padding-right:0"><%= dbox.getDate("d_propstart", "-") %> ~ <%= dbox.getDate("d_propend", "-") %></td>
                <th style="padding-left:0;padding-right:0;text-align:center;">과정교육기간</th>
                <td style="padding-left:10px;padding-right:0" class="end"><%= dbox.getDate("d_edustart", "-") %> ~ <%= dbox.getDate("d_eduend", "-") %></td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">휴대전화</th>
                <td style="padding-left:10px;padding-right:0" class="in">
                    <input type="hidden" id="p_handphone" name="p_handphone" value="<%= dbox.get("d_handphone") %>" />
                    <input type="text" id="hp1" name="hp1" onchange="$('#p_handphone').val($('#hp1').val() + '-' + $('#hp2').val() + '-' + $('#hp3').val());"
                        class="inbox" style="width:38px;" value="<%= hp[0] == null ? "" : hp[0] %>" />-
                    <input type="text" id="hp2" name="hp2" onchange="$('#p_handphone').val($('#hp1').val() + '-' + $('#hp2').val() + '-' + $('#hp3').val());"
                        class="inbox" style="width:38px;" value="<%= hp[1] == null ? "" : hp[1] %>" />-
                    <input type="text" id="hp3" name="hp3" onchange="$('#p_handphone').val($('#hp1').val() + '-' + $('#hp2').val() + '-' + $('#hp3').val());"
                        class="inbox" style="width:38px;" value="<%= hp[2] == null ? "" : hp[2] %>" />
                </td>
                <th style="padding-left:0;padding-right:0;text-align:center;">이메일</th>
                <td style="padding-left:10px;padding-right:0" class="in end"><input type="text" id="p_email" name="p_email" class="inbox" style="width:162px;" value="<%= dbox.get("d_email") %>" /></td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">직장(학교)명</th>
                <td style="padding-left:10px;padding-right:0"><input type="text" id="p_comptext" name="p_comptext" value="<%= dbox.getString("d_comptext") %>" class="inbox"></td>
                <th style="padding-left:0;padding-right:0;text-align:center;">증명사진<br>업로드</th>
                <td style="padding-left:10px;padding-right:0" class="end">
                    <% if(!dbox.getString("d_img_path").equals("")) {%>
                    <label id="pic" style="font-weight:bold;">사진이 존재 합니다. </label>
                    <%}%>
                    <input type="file" name="p_picfile" class="inbox">
                    *용량은 20Kb 이하로 해주세요.&nbsp;&nbsp;&nbsp;<br><label style="color:red">파일명은 영문으로 올려 주세요.</label>
                </td>
            </tr>

<%
        if(token.length > 8 && token[8] == '1') {
%>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">총 경력기간</th>
                <td style="padding-left:10px;padding-right:0" colspan="3" class="end">
                    <input type="text" maxlength="2" size="4" id="p_tcareeryear"  name="p_tcareeryear"  value="<%=dbox.getString("d_tcareeryear") %>" onkeypress="onlyNumber()" class="inbox"/> 년
                    <input type="text" maxlength="2" size="4" id="p_tcareermonth" name="p_tcareermonth" value="<%=dbox.getString("d_tcareermonth") %>" onkeypress="onlyNumber()" class="inbox"/> 개월
                </td>
            </tr>
<%
        }

        if(!dbox.get("d_specials").substring(3,4).equals("Y")) {
        // if(token.length > 8 && token[6] == '1') {
%>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">자기소개<br>(경력사항)</th>
                <td style="padding-left:10px;padding-right:0" colspan="3" class="end"><textarea rows="5" cols="90" name="p_intro" id="p_selfintro" onkeyup="textareaLength('p_intro','p_selfintroLbael');"><%= dbox.get("d_intro") %></textarea><br/>
                * 총 3000Byte 넘을수 없습니다.(한글,특수문자 2byte 나머지 1Byte)
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;현재글자 수 : <label id="p_selfintroLbael" style="font-weight:bold;"></label>
                </td>
            </tr>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">지원동기</th>
                <td style="padding-left:10px;padding-right:0" colspan="3" class="end"><textarea rows="5" cols="90" name="p_motive" id="p_support" onkeyup="textareaLength('p_motive','p_motiveLbael');"><%= dbox.get("d_motive") %></textarea><br/>
                * 총 3000Byte 넘을수 없습니다.(한글,특수문자 2byte 나머지 1Byte)
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;현재글자 수 : <label id="p_motiveLbael" style="font-weight:bold;"></label>
                </td>
            </tr>
<%
        }

        if(box.get("p_subj").equals("SB13033")){ %>
            <tr>
                <th style="padding-left:0;padding-right:0;text-align:center;">첨부파일</th>
                <td style="padding-left:10px;padding-right:0" colspan="3" class="end"><input type="file" name="p_attfile" class="inbox"/>
                 <% if(!dbox.getString("d_savefilenm").equals("")) {%>
                    <a href="/servlet/controller.library.DownloadServlet?p_realfile=<%=dbox.getString("d_savefilenm") %>&p_savefile=<%=dbox.getString("d_realfilenm") %>" target="_blank"><%=dbox.getString("d_savefilenm") %></a>
                 <%}%></td>
            </tr>
            <%} %>
            </table>

<%
    }

    if(i < token.length && token[9] == '1') {
%>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_apply_info.gif" alt="참가정보" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="19%" /><col width="35%" /><col width="16%" /><col width="20%" /></colgroup>
            <tr>
                <th>이름(필명가능)</th>
                <td colspan="3" class="end">
                    <input type="text" class="inbox" style="width:200px;" name="p_apply_name_input" id="oApplyNameInput" value="<%= offApplyInfo.getString("d_apply_name") %>" />
                </td>
            </tr>
            <tr>
                <th>참가세션 (중복체크가능)</th>
                <td colspan="3" class="end">
                    <input type="checkbox" class="inbox" name="chk_apply_session" id="oApplySesson1" value="1" style="vertical-align:middle;" <%= (applySession.indexOf("1") > -1 ) ? "checked='checked'" : "" %> /> <label for="oApplySesson1" style="vertical-align:middle;">세션1</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox" class="inbox" name="chk_apply_session" id="oApplySesson2" value="2" style="vertical-align:middle;" <%= (applySession.indexOf("2") > -1 ) ? "checked='checked'" : "" %> /> <label for="oApplySesson2" style="vertical-align:middle;">세션2</label>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox" class="inbox" name="chk_apply_session" id="oApplySesson3" value="3" style="vertical-align:middle;" <%= (applySession.indexOf("3") > -1 ) ? "checked='checked'" : "" %> /> <label for="oApplySesson3" style="vertical-align:middle;">세션3</label>

                </td>
            </tr>
            <tr>
                <th>소속 및 직함</th>
                <td colspan="3" class="end">
                    <input type="text" name="p_apply_belong_title_input" id="oApplyBelongTitleInput" class="inbox" style="width:200px;" value="<%= offApplyInfo.getString("d_apply_belong_title") %>"/>
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
                    <input type="text" class="inbox" style="width:200px;" name="p_nickname_input" id="oNicknameInput" value="<%= offApplyInfo.getString("d_nickname") %>" />
                </td>
            </tr>
            <tr>
                <th>개인 온라인채널<br/>(SNS, 블로그 등)</th>
                <td colspan="3" class="end">
                    <input type="text" name="p_private_sns_input" id="oPrivateSnsInput" class="inbox" style="width:400px;" value="<%= offApplyInfo.getString("d_private_sns") %>"/>
                </td>
            </tr>
            </table>

<%
    }

    if(i < token.length && token[1] == '1') {
%>
<script>
    function selectMilitary() {
        var s = $(":input:radio[name=p_militarystatus]:checked").val();
        if(!s || s=="") {
            $("#p_militarystart").val('');
            $("#p_militaryend").val('');
            $("#p_militarymemo").val('');
            $("#milliDate").hide();
            $("#milliMemo").hide();
        }
        else if(s.indexOf('1')!=-1) {
            $("#milliDate").show();
            $("#p_militarymemo").val('');
            $("#milliMemo").hide();
        }
        else if(s.indexOf('2')!=-1) {
            $("#p_militarystart").val('');
            $("#p_militaryend").val('');
            $("#p_militarymemo").val('');
            $("#milliDate").hide();
            $("#milliMemo").hide();
        }
        else {
            $("#p_militarystart").val('');
            $("#p_militaryend").val('');
            $("#milliDate").hide();
            $("#milliMemo").show();
        }
    }
</script>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_army.gif" alt="병역구분" class="fl_l" /> <span class="ra_txt"><input type="radio" onclick='selectMilitary()' name="p_militarystatus" value="" <%= dbox.get("d_militarystatus").length()==0 ? "checked" : "" %>/><strong>해당없음</strong> (해당사항이 없을 경우 체크해주세요)</span></h3>
            <table class="write">
            <tr>
                <td class="intxt end">
                    <span><kocca_select:select sqlNum="0001" name="p_militarystatus" styleClass="mg_l10" param="0093" onChange="selectMilitary" selectedValue='<%= dbox.get("d_militarystatus") %>' isLoad="true" type="2.2"/></span>
                    <span class="mg_l20" id="milliDate">
                        복무기간
                            <input name="p_militarystart" id="p_militarystart" type="text" class="inbox" style="width:65px;" value="<%= dbox.get("d_militarystart") %>"/>
                             ~
                            <input name="p_militaryend" id="p_militaryend" type="text" class="inbox" style="width:65px;" value="<%= dbox.get("d_militaryend") %>"/>
                    </span>
                    <span class="mg_l15" id="milliMemo">면제사유 <input type="text" name="p_militarymemo" id="p_militarymemo" class="inbox" style="width:113px;"  value="<%= dbox.get("d_militarymemo") %>" /></span>
                </td>
            </tr>
            </table>
<%  }if(i < token.length && token[2] == '1') {  %>
            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_curri.gif" alt="교육경험" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="19%" /><col width="32%" /><col width="14%" /><col width="35%" /></colgroup>
            <tr>
                <th>학교명</th>
                <td><input type="text" name="p_schoolname" id="p_schoolname" value="" class="inbox" style="width:162px;" /></td>
                <th>소재지</th>
                <td class="end"><input type="text" class="inbox" name="p_place"  id="p_place"  style="width:162px;" /></td>
            </tr>
            <tr>
                <th>학과/전공</th>
                <td><input type="text" class="inbox" name="p_major" id="p_major" style="width:162px;" /></td>
                <th>졸업구분</th>
                <td class="end"><span><kocca_select:select sqlNum="0001" name="p_status" styleClass="mg_l10" param="0094"    selectedValue='<%= dbox.get("d_status") %>' isLoad="true" type="2.2"/></span></td>
            </tr>
            <tr>
                <th>재학기간</th>
                <td colspan="3" class="end">
                    <!-- <input type="text" name="p_sstart" id="p_sstart" class="inbox" style="width:65px;" size="10" maxlength=10  value="" onclick="MiniCal(this)" readonly /><img onclick="p_sstart.click();" src="/images/portal/ico/ico_calendar.gif"  style='CURSOR:hand' class="calendar" /> ~
                    <input type="text" name="p_send" id="p_send" class="inbox" style="width:65px;" size="10" maxlength=10  value="" onclick="MiniCal(this)" readonly /><img onclick="p_send.click();" src="/images/portal/ico/ico_calendar.gif"  style='CURSOR:hand' class="calendar" /> -->
                    <input type="text" name="p_sstart" id="p_sstart" class="inbox" style="width:65px;" value=""/> ~
                    <input type="text" name="p_send" id="p_send" class="inbox" style="width:65px;" value=""/>
                </td>
            </tr>
            </table>
            <p class="btn"><a href="javascript:whenSimpleSave('study',
                                                            $('#p_schoolname').val(),
                                                            $('#p_place').val(),
                                                            $(':input:radio[name=p_status]:checked').val(),
                                                            $('#p_major').val(),
                                                            $('#p_sstart').val(),
                                                            $('#p_send').val()  )" class="btn_gr"><span>등록</span></a></p>
            <table class="ac_list">
            <colgroup><col width="114px" /><col width="101px" /><col width="147px" /><col /><col width="101px" /></colgroup>
            <thead>
            <tr>
                <th>학교명</th>
                <th>졸업구분</th>
                <th>재학기간</th>
                <th>학과/전공</th>
                <th>소재지</th>
                <th>삭제</th>
            </tr>
            </thead>
            <tbody id="schoolList">
<%  for(DataBox schoolListBox : schoolList) { %>
            <tr id="study<%= schoolListBox.get("d_seq") %>">
                <td><%= schoolListBox.get("d_schoolname") %></td>
                <td><%= schoolListBox.get("d_statusnm") %></td>
                <td><%= schoolListBox.get("d_sstart") %>~<%= schoolListBox.get("d_send") %></td>
                <td><%= schoolListBox.get("d_major") %></td>
                <td><%= schoolListBox.get("d_place") %></td>
                <td><a href="javascript:whenSimpleDelete('study', '<%= schoolListBox.get("d_seq") %>');"><img src="/images/admin/portal/stat_0.gif"/></a></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  }if(i < token.length && token[3] == '1') {  %>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_certificate.gif" alt="자격증/외국어" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="19%" /><col width="31%" /><col width="19%" /><col width="31%" /></colgroup>
            <tr>
                <th>자격증명/</br> 외국어점수</th>
                <td><input type="text" name="p_licensename" id="p_licensename" class="inbox" style="width:162px;" /></td>
                <th>취득일</th>
                <td class="end">
                <!-- <input type="text" name="p_getdate" id="p_getdate" class="inbox" style="width:65px;" value="" onclick="MiniCal(this)" readonly/><img src="/images/portal/ico/ico_calendar.gif"  onclick="p_getdate.click();" style='CURSOR:hand' class="calendar" /> -->
                <input type="text" name="p_getdate" id="p_getdate" class="inbox" style="width:65px;" value="">
                </td>
            </tr>
            <tr>
                <th>발행처</th>
                <td colspan="3" class="end"><input type="text" name="p_publish" id="p_publish" class="inbox" style="width:162px;" /></td>
            </tr>
            </table>
            <p class="btn"><a href="javascript:whenSimpleSave('license',
                                                                $('#p_licensename').val(),
                                                                $('#p_getdate').val(),
                                                                $('#p_publish').val()   )" class="btn_gr"><span>등록</span></a></p>
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
            <tbody id="licenseList">
<%  for(DataBox licenseListBox : licenseList) { %>
            <tr id="license<%= licenseListBox.get("d_seq") %>">
                <td><%= licenseListBox.get("d_licensename") %></td>
                <td><%= licenseListBox.get("d_getdate") %></td>
                <td><%= licenseListBox.get("d_publish") %></td>
                <td><a href="javascript:whenSimpleDelete('license', '<%= licenseListBox.get("d_seq") %>');"><img src="/images/admin/portal/stat_0.gif"/></a></td>
            </tr>
<%  } %>
            </tbody>
            </table>
<%  }if(i < token.length && token[4] == '1') {  %>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_trainnum.gif" alt="연수지명번호" class="fl_l" />
            <span class="ra_txt">
                <input type="checkbox" name="p_trainingnum" id="p_trainingnum" value="true" <% if(dbox.get("d_yeunsuno").length()==0) out.println("checked='checked')");%>
                         onclick="if(!this.checked) $('#trainTable').fadeIn(500); else $('#trainTable').fadeOut(500);"/>
                <strong>해당없음</strong> (해당사항이 없을 경우 체크해주세요)
            </span>
            </h3>
            <table class="write" id="trainTable" <% if(dbox.get("d_yeunsuno").length()==0) out.println("style='display:none'");%>>
            <colgroup><col width="19%" /><col width="31%" /><col width="19%" /><col width="31%" /></colgroup>
            <tr>
                <th>연수지명번호</th>
                <td><input type="text" class="inbox" style="width:162px;" onkeyup="getObjectLength(this.name)" onchange="getObjectLength(this.name, 30)" name="p_yeunsuno" id="p_yeunsuno" value="<%=dbox.get("d_yeunsuno")%>"/> <font id="p_yeunsuno_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>
                <th>소속교육청</th>
                <td class="end"><input type="text" name="p_yeonsunm" id="p_yeonsunm" onkeyup="getObjectLength(this.name)" onchange="getObjectLength(this.name, 50)" class="inbox" style="width:65px;" value="<%=dbox.get("d_yeonsunm")%>" /> <font id="p_yeonsunm_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>
            </tr>
            </table>

<%  }if(i < token.length && token[5] == '1') {  %>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_career.gif" alt="경력" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="19%" /><col width="35%" /><col width="16%" /><col width="20%" /></colgroup>
            <tr>
                <th>직업</th>
                <td colspan="3" class="end">
                    <input type="radio" name="jikGubun" onclick="changep_jikup('0091');$('#p_jikup').show();$('#p_jikjong').hide();" checked value="0091"/>문화산업종사자&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" name="jikGubun" onclick="changep_jikjong('0092');$('#p_jikjong').show();$('#p_jikup').hide();" value="0092"/>비문화산업종사자&nbsp;&nbsp;
                    <kocca_select:select sqlNum="0001" name="p_jikup"  styleClass="mg_l10" param="0091" selectedValue='' isLoad="true" type="1" all="no"/>
                    <kocca_select:select sqlNum="0001" name="p_jikjong"  styleClass="mg_l10" param="0092" selectedValue='' isLoad="true" type="1" all="no"/>
                </td>
            </tr>
            <tr>
                <th>회사명</th>
                <td><input type="text" class="inbox" style="width:162px;" name="p_officename" id="p_officename" /></td>
                <th>직무/직급</th>
                <td class="end"><input type="text" class="inbox" style="width:162px;" name="p_position" id="p_position" /></td>
            </tr>
            <tr>
                <th>재직기간</th>
                <td>
                     <input type="text" name="p_workstart" id="p_workstart" class="inbox" style="width:65px;" value=""/>
                     ~ <input type="text" name="p_workend" id="p_workend" class="inbox" style="width:65px;" value=""/>
                     </td>
                <th>재직구분</th>
                <td class="end"><kocca_select:select sqlNum="0001" name="p_workstatus" styleClass="mg_l10" param="0095" selectedValue='' isLoad="true" type="1" all="none"/></td>
            </tr>
            </table>
            <p class="btn"><a href="javascript:whenSimpleSave('workinfo',
                                                                1,
                                                                2,
                                                                $('#p_officename').val(),
                                                                $('#p_position').val(),
                                                                $('#p_workstart').val(),
                                                                $('#p_workend').val(),
                                                                $('#p_workstatus').val()
                                                                )" class="btn_gr"><span>등록</span></a></p>

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
                <th>삭제</th>
            </tr>
            </thead>
            <tbody id="workinfoList">
<%  for(DataBox careerListbox : careerList) { %>
            <tr id="workinfo<%= careerListbox.get("d_seq") %>">
                <td><%= careerListbox.get("d_jikup") %></td>
                <td><%= careerListbox.get("d_jikjong") %></td>
                <td><%= careerListbox.get("d_officename") %></td>
                <td><%= careerListbox.get("d_position") %></td>
                <td><%= careerListbox.get("d_workstart") %>~<%= careerListbox.getStringDefault("d_workend","현재") %></td>
                <td><%= careerListbox.get("d_workstatus") %></td>
                <td><a href="javascript:whenSimpleDelete('workinfo', '<%=careerListbox.get("d_seq")%>');"><img src="/images/admin/portal/stat_0.gif"/></a></td>
            </tr>
<%  } %>
            </tbody>
            </table>


<%  }if(i < token.length && token[6] == '1') {  %>

            <%--<h3 class="pop_tit"><img src="/images/portal/common/stit_resume.gif" alt="자기소개서" /></h3>--%>
            <%--<table class="write">--%>
            <%--<colgroup><col width="18%" /><col /></colgroup>--%>
            <%--<tr>--%>
                <%--<th>본인소개</th>--%>
                <%--<td><textarea class="inbox" name="p_intro" id="p_intro" onkeyup="getObjectLength('p_intro')" onchange="getObjectLength('p_intro', 4000)" style="width:87%; height:30px;"><%= dbox.get("d_intro") %></textarea> <font id="p_intro_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th>지원동기</th>--%>
                <%--<td><textarea class="inbox" name="p_motive" id="p_motive" onkeyup="getObjectLength('p_motive')" onchange="getObjectLength('p_motive', 4000)" style="width:87%; height:30px;"><%= dbox.get("d_motive") %></textarea> <font id="p_motive_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th>향후계획</th>--%>
                <%--<td><textarea class="inbox" name="p_vision" id="p_vision" onkeyup="getObjectLength('p_vision')" onchange="getObjectLength('p_vision', 4000)" style="width:87%; height:30px;"><%= dbox.get("d_vision") %></textarea> <font id="p_vision_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th>기타</th>--%>
                <%--<td><textarea class="inbox" name="p_etc" id="p_etc" onkeyup="getObjectLength('p_etc')" onchange="getObjectLength('p_etc', 4000)" style="width:87%; height:30px;"><%= dbox.get("d_etc") %></textarea> <font id="p_etc_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font></td>--%>
            <%--</tr>--%>
            <%--</table>--%>

<%  } if(i < token.length && token[7] == '1') { %>

            <h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_competition.gif" alt="공모및대회 수상이력" class="fl_l" /></h3>
            <table class="write">
            <colgroup><col width="19%" /><col width="31%" /><col width="19%" /><col width="31%" /></colgroup>
            <tr>
                <th>대회공모전명</th>
                <td><input type="text" class="inbox" style="width:162px;" name="p_competition_nm" id="p_competition_nm"/></td>
                <th>일자</th>
                <td class="end">
                <!-- <input type="text" class="inbox" style="width:65px;" value="2009.11.28" /><input type="image" src="/images/portal/ico/ico_calendar.gif" class="calendar" /> -->
                <input type="text" name="p_competition" id="p_competition" class="datepicker_input1" style="width:65px;" value="">
                </td>
            </tr>                                           `
            <tr>
                <th>수상/공모내역</th>
                <td colspan="3" class="end"><input type="text" class="inbox" style="width:500px;" name="p_competition_detail" id="p_competition_detail"/></td>
            </tr>
            </table>
            <p class="btn"><a href="javascript:whenSimpleSave('competition',
                                                                $('#p_competition_nm').val(),
                                                                $('#p_competition_detail').val()    ,
                                                                $('#p_competition').val())" class="btn_gr"><span>등록</span></a></p>

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
            <tbody id="competition">
        <%  for(DataBox competitionBox : competitionList) { %>
            <tr id="competition<%= competitionBox.get("d_seq") %>">
                <td><%= competitionBox.get("d_competition_date") %></td>
                <td><%= competitionBox.get("d_competition_nm") %></td>
                <td><%= competitionBox.get("d_competition_detail") %></td>
                <td><a href="javascript:whenSimpleDelete('competition', '<%= competitionBox.get("d_seq") %>');"><img src="/images/admin/portal/stat_0.gif"/></a></td>
            </tr>
        <%  } %>
            </tbody>
            </table>
    <%}%>
        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:whenPropose();" class="btn_red"><span>수정</span></a><a href="javascript:whenCancel();" class="btn_gr"><span>닫기</span></a></p>
    </div>
</form>
</body>
</html>
