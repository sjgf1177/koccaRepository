<%
//**********************************************************
//  1. 제      목: PROPOSE CANCEL MEMBER LIST
//  2. 프로그램명: za_ProposeCancelMember_L.jsp
//  3. 개      요: 신청명단조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process = box.getString("p_process");
    int v_pageno = box.getInt("p_pageno");
    if(v_pageno == 0) v_pageno = 1;
    int row = 0;

    ConfigSet config1 = new ConfigSet();
    row = Integer.parseInt(config1.getProperty("page.manage.row"));  //이 모듈의 페이지당 row 수를 셋팅한다

    String v_grseq = "";
    String v_grseqnm = "";
    String v_cyear = "";
    String v_courseseq =  "";
    String v_subj = "";
    String v_year = "";
    String v_subjnm = "";
    String v_subjseq = "";
    String v_subjseqgr =  "";
    String v_userid = "";
    String v_name = "";
    String v_canceldate=  "";
    String v_cancelkind=  "";
    String v_reason = "";
    String v_email = "";
    String v_ismailing =  "";
    String v_isnewcourse= "";
    String v_cancelkind_value = "";
    String v_isonoff = "";
    String v_isonoff_value="";
    String v_course = "";
    String v_coursenm   = "";
    String v_tmp_course ="";
    String v_isbelongcourse = "";
    int v_totalpage =  0;
    int v_rowcount =  0;
    int v_rowspan =  0;
    int v_total =  0;
    int i =  0;
    int k =  0;
    int     v_subjcnt   = 0;

    String v_membergubun = "";
    String v_dispnum    = "";
    String v_paymoney = "";     // 신청금액
    String v_paystat    = "";       // 환불 상태

    ArrayList list = null;
    //DEFINED class&variable END

    String v_orderType = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String v_orderColumn = box.getString("p_orderColumn");           //정렬할 컬럼명

    //DEFINED in relation to select START
    String ss_grcode = box.getString("s_grcode");        //교육그룹
    String ss_gyear = box.getString("s_gyear");         //년도
    String ss_grseq = box.getString("s_grseq");         //교육차수
    String ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String ss_subjseq = box.getString("s_subjseq");       //과정 차수
    String ss_selgubun = box.getString("s_selgubun");
    String ss_seltext = box.getString("s_seltext");
    String ss_seldept = box.getString("s_seldept");
    String ss_edustart = box.getString("s_start");            //교육시작일
    String ss_eduend = box.getString("s_end");              //교육종료일

    String ss_action = box.getString("s_action");
    String v_ongo = "whenSelection('go')";
    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("ProposeCancelMember");

    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    //검색
    function whenSelection(p_action) {
        if (p_action=="go"){

            if (document.form1.s_grcode.value == "" || document.form1.s_grcode.value == "----") {
                alert("교육그룹을 선택하세요.");
                return ;

            } else if (document.form1.s_gyear.value == "" || document.form1.s_gyear.value == "----") {
                alert("연도를 선택하세요.");
                return ;

            } else if (document.form1.s_grseq.value == "" || document.form1.s_grseq.value == "----") {
                alert("교육차수를 선택하세요.");
                return ;
            }
            var st_date = make_date(form1.s_start.value);
            var ed_date = make_date(form1.s_end.value);

            if((st_date.length > 0 && ed_date.length > 0) && st_date > ed_date) {
                alert("교육시작일이 교육종료일보다 큽니다.");
                return;
            }
//            document.form1.s_edustart.value = st_date;
//            document.form1.s_eduend.value = ed_date;
//            top.ftop.setPam();
        }
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.propose.ProposeStatusAdminServlet';
        document.form1.p_process.value = 'ProposeCancelMemberList';
        document.form1.submit();
    }

    //검색
    function whenSelection2(p_action, p_type) {
        if (p_action=="go"){
            if (document.form1.s_grcodenm.value == 'ALL' || document.form1.s_grcode.value == '----') {
                alert("교육그룹을 선택하세요.");
                return ;
            }
            var st_date = make_date(form1.s_start.value);
            var ed_date = make_date(form1.s_end.value);

            if((st_date.length > 0 && ed_date.length > 0) && st_date > ed_date) {
                alert("교육시작일이 교육종료일보다 큽니다.");
                return;
            }
            document.form1.s_edustart.value = st_date;
            document.form1.s_eduend.value = ed_date;
            top.ftop.setPam();
        }
        if (p_type == 'selgubun')
        document.form1.s_seltext.value = 'ALL';
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.propose.ProposeStatusAdminServlet';
        document.form1.p_process.value = 'ProposeCancelMemberList';
        document.form1.submit();
    }

    // 정렬
    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_orderColumn.value = column;
        whenSelection("go");
    }

    // 엑셀 출력
    function whenExcel() {
        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
      }
//        window.self.name = "ProposeCancelMemberList";
        open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
        document.form1.target = "openExcel";
        document.form1.action='/servlet/controller.propose.ProposeStatusAdminServlet';
        document.form1.p_process.value = 'ProposeCancelMemberExcel';
        document.form1.s_action.value = 'go';
        document.form1.submit();
    }

    // 체크박스 체크
    function chkSelected() {
        var selectedcnt = 0;
        if(document.form1.all['p_checks'] == '[object]') {
            if (document.form1.p_checks.length > 0) {
                for (i=0; i<document.form1.p_checks.length; i++) {
                    if (document.form1.p_checks[i].checked == true) {
                        selectedcnt++;
                    }
                }
            } else {
                if (document.form1.p_checks.checked == true) {
                    selectedcnt++;
                }
            }
        }
        return selectedcnt;
    }

    // 메일전송
    function whenFreeMail() {
        if (chkParam() == false) {
            return;
        }
        if (chkSelected() < 1) {
            alert('메일을 발송할 학습자를 선택하세요');
            return;
        }
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.propose.ProposeStatusAdminServlet';
        document.form1.p_process.value = 'SendFreeMail';
        document.form1.submit();
    }

    //페이지 이동
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.propose.ProposeStatusAdminServlet';
         document.form1.p_process.value = "ProposeCancelMemberList";
         document.form1.submit();
    }

    //페이지 이동
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.propose.ProposeStatusAdminServlet';
         document.form1.p_process.value = "ProposeCancelMemberList";
         document.form1.submit();
    }

    // 멤버정보
    function whenMemberInfo(userid) {
//        window.self.name = "ProposeMemberList";
        open_window("openMember","","100","100","600","260");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
//        document.form1.target = window.self.name;
    }
    
    var startup = 0;
    function Main_s_grseq(){
        if (startup > 0)
        changes_grseq(document.form1.s_grcode.value, document.form1.s_gyear.value);
    }
    
    function Main_subjcourse(){
        var subjSearchKey = '';
        if (document.form1.s_subjsearchkey == 'undefine')  subjSearchKey = '';
        if(startup > 2) {
            changes_subjcourse(document.form1.s_grcode.value
                            , document.form1.s_upperclass.value
                            , document.form1.s_middleclass.value
                            , document.form1.s_lowerclass.value
                            , document.form1.s_grseq.value
                            , subjSearchKey );
//      alert(document.form1.s_grcode.value+ " , " + document.form1.s_upperclass.value+ " , " + document.form1.s_middleclass.value+ " , " + document.form1.s_lowerclass.value+ " , " + document.form1.s_subjseq.value+ " , " +'' );
        }
        startup++;
    }

    // select box 설정
    $(function() {
        $("#oGrcode").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.grYearList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrYear
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oGyear").bind("change", function(){
            var grcode = $("#oGrcode").val();
            var param = "type=sqlID&sqlID=selectBox.grSeqList&param=" + grcode + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrSeq
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oGrseq").bind("change", function(){
            var grcode = $("#oGrcode").val();
            var gyear = $("#oGyear").val();
            var subjSearchKey = $("#oSubjSearchKey").val();
            subjSearchKey = (subjSearchKey == "") ? "%" : subjSearchKey;
            var param = "type=sqlID&sqlID=selectBox.subjList&param=" + grcode + "," + gyear + "," + $(this).val() + "," + subjSearchKey;
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetSubjList
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oSubjcourse").bind("change", function(){

            if ( $(this).val() == "" ) {
                $("#oSubjseq").val("");
            } else {
                var grcode = $("#oGrcode").val();
                var gyear = $("#oGyear").val();
                var grseq = $("#oGrseq").val();
                var param = "type=sqlID&sqlID=selectBox.subjSeqList&param=" + grcode + "," + gyear + "," + grseq + "," + $(this).val();

                $.ajaxSetup({cache:false});
                $.ajax({
                        type : "get"
                    ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                    ,   dataType : "json"
                    ,   data : param
                    ,   success : fnSetSubjSeqList
                    ,   complete : function(arg1, arg2) {
                            // alert("complete : " + arg1);
                        }
                    ,   error :  function(arg1, arg2) {
                            // alert("error : " + arg1);
                        }
                });
            }
        });

        $("#oUpperclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttMiddleList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetMiddleClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oMiddleclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttLowerList&param=" + $("#oUpperclass").val() + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetLowerClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

    });

    /**
     * 교육 그룹별 연도 selectbox 지정
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oGyear").append("<option value=\"\">== 선택 ==</option>");
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGyear").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 교육 그룹 연도별 교육차수 selectbox 지정
     */
    function fnSetGrSeq( result ) {
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");


        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGrseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

    /**
     * 교육차수별 과정 selectbox 지정
     */
    function fnSetSubjList( result ) {
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");


        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSubjcourse").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

    /**
     * 과정별 과정차수 selectbox 지정
     */
    function fnSetSubjSeqList( result ) {

        $("#oSubjseq").empty();
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");
        
        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSubjseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 검색조건에서 대분류 선택시 callback function
     */
    function fnSetMiddleClass( result ) {
        $("#oMiddleclass").empty();
        $("#oMiddleclass").append("<option value=\"\">== 전체 ==</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 검색조건에서 중분류 선택시 callback function
     */
    function fnSetLowerClass( result ) {
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }
    /**
     * ?
     */
    function fnSubjSearchByName() {
        $("#oGrseq").trigger("change");
    }

-->
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'ondragstart='return false' 
onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

    <input type = "hidden" name="s_edustart">
    <input type = "hidden" name="s_eduend">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr> 
                    <td><img src="/images/admin/propose/p_title10.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!----------------- title 끝 ----------------->
            <br>

            <!----------------- form 시작 ----------------->
            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">

                        <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                            <tr>
                                <td height="7"></td>
                            </tr>
                            <tr>
                                <td align="center">

                                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                                        <tr>
                                            <td colspan="2" align="left" valign="middle" rowspan='2'>
                                            
                                            <!------------------- 조건검색 시작 ------------------------->


                                                <table border="0" cellspacing="0" cellpadding="0" width="99%" >
                                                    <tr>
                                                        <td width="10%" class="_tdT"><font color="red">★</font>교육그룹</td>
                                                        <td width="15%" class="_tdS">
                                                            <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                                                        </td>
                                                        <td width="10%" class="_tdT"><font color="red">★</font>연도</td>
                                                        <td width="15%" class="_tdS">
<%
    if ( !ss_gyear.equals("") ) {
%>
                                                            <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== 선택 ==" type="sqlID" param="<%= ss_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= ss_gyear %>" isLoad="true" />
<%
    } else {
%>
                                                            <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== 선택 ==" type="sqlID" isLoad="false" />
<%
    }
%>
                                                        </td>
                                                        <td width="10%" class="_tdT"><font color="red">★</font>교육차수</td>
                                                        <td width="15%" class="_tdS">
<%
    if ( !ss_grseq.equals("") ) {
%>
                                                            <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="== 선택 ==" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear %>" sqlID="selectBox.grSeqList" selectedValue="<%= ss_grseq %>" isLoad="true" />
<%
    } else {
%>
                                                            <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="== 선택 ==" type="sqlID" isLoad="false" />
<%
    }
%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="10%" class="_tdT">대분류</td>
                                                        <td width="15%" class="_tdS">
                                                            <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                                        </td>
                                                        <td width="10%" class="_tdT">중분류</td>
                                                        <td width="15%" class="_tdS">
<%
    if ( !ss_middleclass.equals("") ) {
%>
                                                            <kocca:selectBox  name="s_middleclass" id="oMiddleclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" />
<%
    } else {
%>
                                                            <select name="s_middleclass" id="oMiddleclass">
                                                                <option value="">== 전체 ==</option>
                                                            </select>
<%
    }
%>
                                                        </td>
                                                        <td width="10%" class="_tdT">소분류</td>
                                                        <td width="15%" class="_tdS">
<%
    if ( !ss_lowerclass.equals("") ) {
%>
                                                            <kocca:selectBox name="s_lowerclass" id="oLowerclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" />
<%
    } else {
%>
                                                            <select name="s_lowerclass" id="oLowerclass">
                                                                <option value="">== 전체 ==</option>
                                                            </select>
<%
    }
%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                    <% String s_subjsearchkey = box.getString("s_subjsearchkey"); %>
                                                        <td width="10%" class="_tdT">과정검색</td>
                                                        <td class="_tdS">
                                                            <input type="text" id="oSubjSearchKey" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode=='13') fnSubjSearchByName();" value="<%=s_subjsearchkey%>">
                                                            <a href="javascript:fnSubjSearchByName()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>
                                                        </td>
                                                        <td width="10%" class="_tdT">과정</td>
                                                        <td colspan="3" class="_tdS">
<%
    if ( !ss_subjcourse.equals("") ) {
        s_subjsearchkey = ( s_subjsearchkey.equals("") ) ? "%" : s_subjsearchkey;
%>
                                                            <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="== 전체 ==" optionTitleValue="ALL" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_grseq + ',' + s_subjsearchkey %>" sqlID="selectBox.subjList" selectedValue="<%= ss_subjcourse %>" isLoad="true" />
<%
    } else {
%>
                                                            <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="== 전체 ==" optionTitleValue="ALL" type="sqlID" isLoad="false" />
<%
    }
%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="10%" class="_tdT">과정차수</td>
                                                        <td width="15%" class="_tdS">
<%
    if ( !ss_subjseq.equals("") ) {
%>
                                                            <kocca:selectBox name="s_subjseq" id="oSubjseq" optionTitle="== 전체 ==" optionTitleValue="ALL" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_grseq + ',' + ss_subjcourse %>" sqlID="selectBox.subjSeqList" selectedValue="<%= ss_subjseq %>" isLoad="true" />
<%
    } else {
%>
                                                            <select name="s_subjseq" id="oSubjseq">
                                                                <option value="">== 전체 ==</option>
                                                            </select>
<%
    }
%>
                                                        </td>
                                                        <input type = "hidden" name="s_start">
                                                        <input type = "hidden" name="s_end">
                                                        <td width="10%" class="_tdT">사용자ID</td>
                                                        <td class="_tdS"><input type="text" name="s_userid" size="15"></td>
                                                        <td width="10%" class="_tdT">사용자명</td>
                                                        <td class="_tdS"><input type="text" name="s_username" size="15"> &nbsp
                                                            <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="8%" class="_tdT">교육구분</td>
                                                        <td width="17%" class="_tdS">
                                                            <select name="p_area" id="p_area">
                                                              <option value="">-전체-</option>
                                                              <option value="G0"  >게임</option>
                                                              <option value="K0"  >방송</option>
                                                              <option value="B0"  >문화</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>

                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="9"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <!----------------- form 끝 ----------------->

            <br>
            <br>

            <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 시작 ----------------->
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" height="20">&nbsp;</td>
                    <td align="right" width="65">&nbsp;</td>
                    <td align="right" width="65">
                        <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 끝 ----------------->
            <!----------------- 신청취소명단조회 시작 ----------------->
            <table cellspacing="1" cellpadding="5" class="table_out">
                <tr>
                    <td colspan="14" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" width="5%">No</td>
                    <td class="table_title" width="14%"><a href="javascript:whenOrder('subj')" class="e">과정</a></td>
                    <td class="table_title" width="10%"><a href="javascript:whenOrder('grseq')" class="e">차수</a></td>
                    <td class="table_title" width="5%"><a href="javascript:whenOrder('userid')" class="e">ID</a></td>
                    <td class="table_title" width="7%"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
                    <td class="table_title" width="13%">회원구분</td>
                    <td class="table_title" width="5%">결제<br>상태</td>
                    <td class="table_title" width="8%">환불액</td>
                    <td class="table_title" width="7%">취소일</td>
                    <td class="table_title" width="7%">취소구분</td>
                    <td class="table_title" width="7%">취소사유</td>
                </tr>
<%
            int j = 0;
            if (ss_action.equals("go")) {    //go button 선택시만 list 출력
            
                Log.info.println("list.size() = " + list.size());
                for(i = 0; i < list.size(); i++) 
                {
                    Log.info.println("i = " + i);
                    
                    DataBox dbox = (DataBox)list.get(i);

                    v_grseq = dbox.getString("d_grseq");
                    v_grseqnm = dbox.getString("d_grseqnm");
                    v_course = dbox.getString("d_course");
                    v_cyear = dbox.getString("d_cyear");
                    v_courseseq = dbox.getString("d_courseseq");
                    v_coursenm = dbox.getString("d_coursenm");
                    v_subj = dbox.getString("d_subj");
                    v_year = dbox.getString("d_year");
                    v_subjnm = dbox.getString("d_subjnm");
                    v_subjseq = dbox.getString("d_subjseq");
                    v_subjseqgr = dbox.getString("d_subjseqgr");
                    v_userid = dbox.getString("d_userid");
                    v_name = dbox.getString("d_name");
                    v_email = dbox.getString("d_email");
                    v_ismailing = dbox.getString("d_ismailing");
                    v_isnewcourse = dbox.getString("d_isnewcourse");
                    v_isonoff = dbox.getString("d_isonoff");
                    v_membergubun   = dbox.getString("d_membergubun");

                    v_course        = dbox.getString("d_course");
                    v_subjcnt       = dbox.getInt("d_subjcnt");
                    v_coursenm      = dbox.getString("d_coursenm");

                    v_dispnum       = dbox.getString("d_dispnum");
                    v_totalpage = dbox.getInt("d_totalpage");
                    v_rowcount = dbox.getInt("d_rowcount");

                    v_paymoney   = dbox.getString("d_paymoney");
                    v_paystat    = dbox.getString("d_paystat    ");
                    
                    v_canceldate = dbox.getString("d_canceldate");
                    v_cancelkind = dbox.getString("d_cancelkind");
                    v_reason = dbox.getString("d_reason");
                    v_rowcount      = dbox.getInt("d_totalrow");
                    v_isbelongcourse = dbox.getString("d_isbelongcourse");

                    if(v_isonoff.equals("ON")){
                        v_isonoff_value="사이버";   
                    } else {     v_isonoff_value="집합";     
                    }
                    v_canceldate = FormatDate.getFormatDate(v_canceldate,"yyyy/MM/dd");
                    
                    if(v_cancelkind.equals("P")) {
                        v_cancelkind_value = "본인취소";    
                    } else if(v_cancelkind.equals("F")) {
                        v_cancelkind_value = "운영자취소";  
                    } else if(v_cancelkind.equals("D")) {  
                        v_cancelkind_value = "운영자삭제";  
                    }

                    //회원구분
                    if(v_membergubun.equals("C")) {
                        v_membergubun = "기업";
                    } else if(v_membergubun.equals("U")) {
                        v_membergubun = "대학";
                    } else {
                        v_membergubun = "개인";
                    }

                    //환불 상태
                    if(v_paystat.equals("CW")) {
                        v_paystat = "환불중";
                    } else if(v_paystat.equals("CY")) {
                        v_paystat = "완료";
                    } else {
                        v_paystat = "미완료";
                    }
                    
%>
<% 
                    if(v_isbelongcourse.equals("Y")) {    
                        if(k == v_subjcnt) v_tmp_course = "";
       
                        if(!v_tmp_course.equals(v_course) && !v_tmp_course.equals("000000")) { 
                            k = 1;
                            v_tmp_course = v_course;
                            j++;
%>
                <tr>
                    <td class="table_01"><%= j %></td>
                    <td class="table_02_2"><font class="text_color04"><%=v_coursenm%></font></td>
                    <td class="table_02_1"><%= v_grseqnm %>[<%=StringManager.cutZero(v_subjseqgr)%>]</td>
                    <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%= v_userid %></a></td>
                    <td class="table_02_1"><%= v_name %></td>
                    <td class="table_02_1"><%= v_membergubun %></td>
                    <td class="table_02_1"><%= v_paystat %></td>               
                    <td class="table_02_1"><%= v_paymoney %></td>         
                    <td class="table_02_1"><%= v_canceldate %></td>
                    <td class="table_02_1"><%= v_cancelkind_value %></td>
                    <td class="table_02_1"><%= v_reason %></td>
<%
                        } else {
                            k++; 
                        }
                    } else { 
                        j++;
                        k++;
            
%>
                <tr>
                    <td class="table_01"><%= v_dispnum %></td>
                    <td class="table_02_2"><font class="text_color04"><%=v_subjnm%></font></td>
                    <td class="table_02_1"><%= v_grseqnm %>[<%=StringManager.cutZero(v_subjseqgr)%>]</td>           
                    <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%= v_userid %></a></td>
                    <td class="table_02_1"><%= v_name %></td>
                    <td class="table_02_1"><%= v_membergubun %></td>
                    <td class="table_02_1"><%= v_paystat %></td>               
                    <td class="table_02_1"><%= v_paymoney %></td>         
                    <td class="table_02_1"><%= v_canceldate %></td>
                    <td class="table_02_1"><%= v_cancelkind_value %></td>
                    <td class="table_02_1"><%= v_reason %></td>
<%
                    } 
%>
            
                </tr>
<%
                }
            }
            if(i == 0 && ss_action.equals("go")){ %>
                <tr>
                    <td align="center" bgcolor="#F7F7F7" height="50" colspan="14">등록된 내용이 없습니다</td>
                </tr>
<%  
            } 
%>
            </table>


<%  
            if(i > 0){ 
%>
            <table class="table1" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
                </tr>
            </table>
            <br>
            <table class="table1" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">◈<b> TOTAL : <%= j%>명</b></td>
                </tr>
            </table>
<% 
            } 
%>
            </table>
      <!----------------- 신청취소명단 조회 끝 ----------------->
            <br>
        </td>
    </tr>
    <tr>
        <td><%@ include file = "/learn/library/getJspName.jsp" %></td>
    </tr>
</table>
</form>
</body>
</html>
