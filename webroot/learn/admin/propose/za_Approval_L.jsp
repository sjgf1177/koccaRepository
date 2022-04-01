<%
/**
 * file name : za_Approval_L.jsp
 * date   :
 * programmer:
 * function  : 수강신청 승인 리스트 조회화면
 */
%>
<%@ page contentType="text/html;charset=MS949" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_process = "listPage";

    String ss_grcode = box.getString("s_grcode");         //교육그룹
    String ss_gyear = box.getString("s_gyear");          //년도
    String ss_grseq = box.getString("s_grseq");          //교육차수
    String ss_grseqnm = box.getString("s_grseqnm");        //교육차수명
    String ss_mastercd = box.getString("s_mastercd");       //과정&코스
    String ss_upperclass = box.getString("s_upperclass");    //과정대분류
    String ss_middleclass = box.getString("s_middleclass");  //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");    //과정소분류
    String ss_subjcourse = box.getString("s_subjcourse");     //과정&코스
    String ss_subjseq = box.getString("s_subjseq");        //과정 차수
    String ss_superyn = box.getString("s_superyn");      // 회원구분: 우수/일반
    String ss_selgubun = box.getString("s_selgubun");
    String ss_seltext = box.getString("s_seltext");
    String ss_seldept = box.getString("s_seldept");
    String ss_edustart = box.getString("s_start");         //교육시작일
    String ss_eduend = box.getString("s_end");           //교육종료일
    String ss_appstart = box.getString("s_appstart");          //수강신청시작일
    String ss_append = box.getString("s_append");            //수강신청종료일
    String ss_datefield = box.getString("s_datefield");            //수강신청&결제일 선택
    String v_isManagerPropose = box.getString("isManagerPropose");           //
    String s_subjsearchkey = box.getString("s_subjsearchkey");         //과정명
    String ss_area=box.getString("s_area");

    String p_membergubun = box.getString("p_membergubun");
    String pp_chkfinal = box.getString("pp_chkfinal");
    String p_paymethod = box.getString("p_paymethod");
    String p_resultcode = box.getString("p_resultcode");

    int v_biyong = 0;           // 금액
    String v_biyong_view = "";          // 화면출력용 금액(,추가)
    int v_total_biyong = 0;         // 총금액
    String v_total_biyong_view = "";          // 화면출력용 총금액(,추가)
    int v_total_price = 0; // 총 입금금액
    String v_step = box.getString("p_step");
    String v_appstatus = box.getString("p_appstatus");
    String v_searchkind = box.getStringDefault("p_searchkind", "subjseq");

    String v_action = box.getString("p_action");
    String v_gadmin = box.getString("s_gadmin");

    String[] stat_k = {"ALL","B","Y","N"};
    String[] stat_v = {"전체","미처리","승인","반려"};


    String v_finalString = ""; //최종승인 select box 태그

    String v_orderType = box.getStringDefault("p_orderType"," asc"); //정렬순서
    int i=0;
    int l=0;

    int v_usercnt = 0;

    // String v_ongo = "fnSearchList('go')";
    // String v_seq = box.getString("isManagerPropose");

    String vv_biyong = box.getString("p_biyong");
    //out.println("gadmin=>"+box.getSession("gadmin")+", userid=>"+box.getSession("userid"));
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" >
<!--

    $(function(){
        $("#oAppStart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
        $("#oAppEnd").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})

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
        $("#oGyear").append("<option value=\"\">-- 선택 --</option>");
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");

        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
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
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");


        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
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
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");


        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
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
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");
        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
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
        $("#oMiddleclass").append("<option value=\"\">-- 전체 --</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
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
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 목록 조회
     */
    function fnSearchList(p_action) {
        document.form1.p_action.value = p_action;

        if (p_action == 'go') {
            if (fnCheckParam() == false) {
                return;
            }
            // top.ftop.setPam();
        }

        if(document.form1.s_upperclass.value == "----") {
            document.form1.s_upperclass.value = "ALL";
        }

        document.form1.action = "/servlet/controller.propose.ApprovalServlet";
        document.form1.target = "_self";
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }

    /**
     * 검색조건 체크
     */
    function fnCheckParam() {
        if (document.form1.s_grcode.value == "ALL" || document.form1.s_grcode.value == "") {
            alert("교육그룹을 선택하세요.");
            return false;
        }

        if (document.form1.s_appstart.value != "" && document.form1.s_append.value != "") {
        } else {
            if (document.form1.s_grseq.value == "ALL" || document.form1.s_grseq.value == "") {
                alert("교육차수를 선택하세요.");
                return false;
            }
        }
    }

    /**
     * 목록 정렬
     */
    function fnSortOrderList(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_order.value = column;
        fnSearchList("go");
    }

    /**
     * 승인(취소,삭제)처리
     */
    function fnDoApproval(){
        if (fnCheckParam() == false) {
          return;
        }
        document.ff.submit();
    }

    /**
     * 전체 옵션선택 버튼
     */
    function fnChangeAll(idx){
        if(!confirm("변경가능한 승인옵션이 모두 변경됩니다.\n\n계속하시겠습니까?")) return;

        for(var i=0;i<document.ff.length;i++){
            if(document.ff.elements[i].type=="select-one"){
                document.ff.elements[i].selectedIndex=idx;
            }
        }

        alert("[확인]버튼을 누르셔야만 저장됩니다");

    }

    /**
     * 회원정보 조회
     */
    function fnViewMemberInfo(userid, subj, subjseq, year) {
        // window.self.name = "ProposeList";
        open_window("openMember","","100","100","1024","700",'','','','yes');
        document.form1.target = "openMember";
        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet?p_searchtext=" + userid + "&p_subj=" + subj;
        document.form1.p_subjseq.value=subjseq;
        document.form1.p_year.value=year;
        document.form1.p_userid.value=userid;
        // document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = "PersonalSelect";
        // document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        // document.form1.target = window.self.name;
    }

    /**
     * excel file 생성.
     */
    function fnSaveExcel() {
        f = document.form1;

        if (fnCheckParam() == false) {
          return;
        }

        document.form1.method = 'post';
        document.form1.target = 'mainFrame';

        document.form1.action='/servlet/controller.propose.ApprovalServlet';
        document.form1.p_process.value = 'ExcelDown';
        document.form1.p_action.value = "go";

        document.form1.submit();

    }

    function whenCancel(p_tid) {
//      window.self.name = "SchoolfeeList";
        open_window("openPayInfo","","100","100","620","450","no","no","no","yes","yes");
        document.form1.target = "openPayInfo";
        document.form1.p_tid.value = p_tid;
        document.form1.action='/servlet/controller.polity.SchoolfeeAdminServlet';
//      document.form1.p_process.value = 'paycancelPage'; 환불처리 조회 및 취소
        document.form1.p_process.value = 'paycancelPage2';// 환불처리 조회
        document.form1.submit();
//      document.form1.target = window.self.name;
    }

    function whenSimpleLoad(data) {
        $.post("/servlet/controller.common.TagServlet"
                , {   sqlNum:"propost.accept.cancelPopup",rerurnFunction:"readData", type:6
                    , p_subjcode:data.d_subj
                    , p_year:data.d_year
                    , p_subjseq:data.d_subjseq
                    , p_userid:data.d_userid
                }
                , function(data) {
                    $("#ajaxDiv").html(data);
                });
        document.getElementById("detailView").style.pixelTop = event.y*1 + document.body.scrollTop*1-50;
        document.getElementById("detailView").style.pixelLeft =event.x*1-320;
    }
    function readData(data) {
        $('#detailView').fadeIn(350);
        $("#detailView_d_refundbank").html(data.d_refundbank);
        $("#detailView_d_refundaccount").html(data.d_refundaccount);
        $("#detailView_d_refundname").html(data.d_refundname);
        $("#detailView_d_cancelreason").html(data.d_cancelreason);
    }
    function ready() {
        $("#detailView").fadeOut(250);
        return;
    }

    /**
     * ?
     */
    function fnSubjSearchByName() {
        $("#oGrseq").trigger("change");
    }

//-->
</script>
</head>



<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
<div id="ajaxDiv"></div>
<div id="detailView" style="position: absolute; z-index: 99; display:none;">
    <table width="300" border="0" cellspacing="0" class="page_title" cellpadding="0">
        <tr>
            <td align="center" valign="top">
                <b>환불계좌</b><br/>
                <table cellspacing="1" cellpadding="5" class="table_out" border="0">
                  <tr>
                    <td colspan="2" class="table_top_line"></td>
                  </tr>
                  <tr>
                    <td class="table_01">은행명</td>
                    <td class="table_02_2" id="detailView_d_refundbank"></td>
                  </tr>
                  <tr>
                    <td class="table_01">계좌번호</td>
                    <td class="table_02_2"  id="detailView_d_refundaccount"></td>
                  </tr>
                  <tr>
                    <td class="table_01">예금주</td>
                    <td class="table_02_2"  id="detailView_d_refundname"></td>
                  </tr>
                  <tr>
                    <td class="table_01">취소사유</td>
                    <td class="table_02_2"  id="detailView_d_cancelreason"></td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table_top_line"></td>
                  </tr>
                  <tr>
                    <td class="table_01" colspan="2"><a href="javascript:ready()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
                  </tr>
                 </table>
            </td>
        </tr>
    </table>
</div>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/propose/p_title01.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br/>

<!-- form 시작 //-->
<form name = "form1" method = "post" action="/servlet/controller.propose.ApprovalServlet">
    <input type="hidden" name="p_process" value="<%=v_process%>" />
    <input type="hidden" name="p_masternm" value="" />
    <input type="hidden" name="p_mastercd" value="<%=ss_mastercd%>" />
    <input type="hidden" name="p_action" value="go" />
    <input type="hidden" name="p_order" value="name" />
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>" />
    <input type="hidden" name="p_userid" />
    <input type="hidden" name="p_tid" />

    <input type="hidden" name="p_search" value="userid" />

    <input type="hidden" name="p_grcode" value="<%= ss_grcode %>" />
    <input type="hidden" name="p_grseq" value="" />
    <input type="hidden" name="p_year" value="<%= ss_gyear %>" />
    <input type="hidden" name="p_gyear" value="<%= ss_gyear %>" />
    <input type="hidden" name="p_subj" value="<%= ss_subjcourse %>" />
    <input type="hidden" name="p_subjseq" value="<%= ss_grseq %>" />
    <input type="hidden" name="p_gubun" value="3" />
    <input type="hidden" name="p_gubunnm" value="교육개시" />
    <input type="hidden" name="p_ispropose" value="" />
    <input type="hidden" name="p_isapproval" value="" />
    <input type="hidden" name="p_uclass" value="<%= ss_upperclass %>" />
    <input type="hidden" name="p_mclass" value="<%= ss_middleclass %>">
    <input type="hidden" name="p_lclass" value="<%= ss_lowerclass %>">
    <input type="hidden" name="p_searchkind" value="<%=v_searchkind%>">

    <input type="hidden" name="p_returnurl" value="/servlet/controller.propose.ApprovalServlet">
    <input type="hidden" name="p_returnprocess" value="listPage">


      <!-- form 시작 //-->
    <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
            <td bgcolor="#C6C6C6" align="center">

                <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                    <colgroup>
                        <col style="width:8%" />
                        <col style="width:31%" />
                        <col style="width:8%" />
                        <col style="width:10%" />
                        <col style="width:8%" />
                        <col style="width:30%" />
                        <col style="width:5%" />
                    </colgroup>
                    <tr>
                        <td class="_tdT"><font color="red">★</font>교육그룹</td>
                        <td class="_tdS">
                            <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 선택 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                        </td>
                        <td class="_tdT"><font color="red">★</font>연도</td>
                        <td class="_tdS">
<%
    if ( !ss_gyear.equals("") ) {
%>
                            <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="-- 선택 --" type="sqlID" param="<%= ss_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= ss_gyear %>" isLoad="true" />
<%
    } else {
%>
                            <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="-- 선택 --" type="sqlID" isLoad="false" />
<%
    }
%>
                        </td>
                        <td class="_tdT"><font color="red">★</font>교육차수</td>
                        <td class="_tdS">
<%
    if ( !ss_grseq.equals("") ) {
%>
                                <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="-- 선택 --" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear %>" sqlID="selectBox.grSeqList" selectedValue="<%= ss_grseq %>" isLoad="true" />
<%
    } else {
%>
                                <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="-- 선택 --" type="sqlID" isLoad="false" />
<%
    }
%>
                            </td>
                            <td rowspan="5" align="left">
                                <a href="javascript:fnSearchList('go');"><img src="/images/admin/button/b_go.gif" border="0" align="absmiddle"></a>
                            </td>
                        </tr>
                        <tr>
                            <td class="_tdT">대분류</td>
                            <td class="_tdS">
                                <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                            </td>
                            <td class="_tdT">중분류</td>
                            <td class="_tdS">
<%
    if ( !ss_middleclass.equals("") ) {
%>
                                <kocca:selectBox  name="s_middleclass" id="oMiddleclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" />
<%
    } else {
%>
                                <select name="s_middleclass" id="oMiddleclass">
                                    <option value="">-- 전체 --</option>
                                </select>
<%
    }
%>
                            </td>
                            <td class="_tdT">소분류</td>
                            <td class="_tdS">
<%
    if ( !ss_lowerclass.equals("") ) {
%>
                                <kocca:selectBox name="s_lowerclass" id="oLowerclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" />
<%
    } else {
%>
                                <select name="s_lowerclass" id="oLowerclass">
                                    <option value="">-- 전체 --</option>
                                </select>
<%
    }
%>
                            </td>
                        </tr>
                        <tr>
                            <td class="_tdT">과정검색</td>
                            <td class="_tdS">
                                <input type="text" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode=='13') fnSubjSearchByName();" value="<%=s_subjsearchkey%>">
                                <a href="javascript:fnSubjSearchByName();"><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>
                            </td>
                            <td class="_tdT">과정</td>
                            <td colspan="3" class="_tdS">
<%
        if ( !ss_subjcourse.equals("") ) {
            s_subjsearchkey = ( s_subjsearchkey.equals("") ) ? "%" : s_subjsearchkey;
%>
                                <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="-- 전체 --" optionTitleValue="ALL" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_grseq + ',' + s_subjsearchkey %>" sqlID="selectBox.subjList" selectedValue="<%= ss_subjcourse %>" isLoad="true" />
<%
    } else {
%>
                                <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="-- 전체 --" optionTitleValue="ALL" type="sqlID" isLoad="false" />
<%
    }
%>
                            </td>
                        </tr>
                        <tr>
                            <td class="_tdT">과정차수</td>
                            <td class="_tdS">
                                <kocca:selectBox name="s_subjseq" id="oSubjseq" optionTitle="-- 전체 --" optionTitleValue="ALL" type="sqlID" isLoad="false" />
                            </td>
                            <td class="_tdT">승인</td>
                            <td colspan="3" class="_tdS">
                                <select name="p_appstatus">
                                <%
                                    for(int ix=0;ix < 4;ix++){
                                %>
                                    <option value="<%=stat_k[ix]%>" <%=((v_appstatus.equals(stat_k[ix])? "selected":" "))%> ><%=stat_v[ix]%></option>
                                <%
                                    }
                                %>
                                </select>
                                <input type="hidden" name="p_step" value="2" />
                                <input type="hidden" name="s_start" />
                                <input type="hidden" name="s_end" />

                                <select name="s_datefield">
                                    <option value="appdate">수강신청일</option>
                                    <!-- <option value="pgauthdate" <%-- if(ss_datefield.equals("pgauthdate")) out.print("selected"); --%> >결제일</option> //-->
                                </select>
                                <input name="s_appstart" id="oAppStart" value="<%= ss_appstart %>" type="text" class="datepicker_input1" size="10" readonly /> ~
                                <input name="s_append" id="oAppEnd" value="<%= ss_append %>" type="text" class="datepicker_input1" size="10" readonly />
                            </td>
                        </tr>
                        <tr>
                            <td class="_tdT">교육구분</td>
                            <td class="_tdS">
                                <select name="s_area">
                                    <option value="T" <% if(ss_area.equals("T")) out.print("selected"); %> >전체</option>
                                    <option value="G0" <% if(ss_area.equals("G0")) out.print("selected"); %> >게임</option>
                                    <option value="B0" <% if(ss_area.equals("B0")) out.print("selected"); %> >방송</option>
                                    <option value="K0" <% if(ss_area.equals("K0")) out.print("selected"); %> >문콘</option>
                                </select>
                            </td>
                            <td class="_tdT">유/무료</td>
                            <td class="_tdS" colspan="3">
                                <select name="p_biyong">
                                    <option value="ALL">-전체-</option>
                                    <option value="Y" <% if(vv_biyong.equals("Y")){ %>selected<%} %>>유료</option>
                                    <option value="N" <% if(vv_biyong.equals("N")){ %>selected<%} %>>무료</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td height="9"></td>
                        </tr>
                    </table>
                </td>
            </tr>
      </table>
     <!-- 조건검색 끝 //-->


<%
        String v_ispropose = "";
        String v_proposetype = "";
        String v_isedutarget = "";
        String v_educnt = "";
        String v_mastercd = "";
        String v_masternm = "";

        if (!ss_subjcourse.equals("ALL")&&!ss_subjseq.equals("ALL")&&!ss_subjcourse.equals("")&&!ss_subjseq.equals("")&&v_action.equals("go")) {
            DataBox dbox1 = (DataBox)request.getAttribute("isManagerStatus");

            if(dbox1 != null){

                v_ispropose = dbox1.getString("d_ispropose");
                v_mastercd = dbox1.getString("d_mastercd");
                v_masternm = dbox1.getString("d_masternm");
                v_proposetype = dbox1.getString("d_proposetype");
                v_isedutarget = dbox1.getString("d_isedutarget");
                v_educnt = dbox1.getString("d_educnt");

            }
    }
%>

    <!-- 모두승인, 모두승인취소, 확인 버튼 시작 //-->
    <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>과정기준조회</td>
            <td align="right" height="20" width="58">&nbsp;</td>
            <td width="8"></td>
            <td align="right" height="20" width="70"><a href="javascript:fnChangeAll(1)"><img src="/images/admin/button/btn_allok.gif" border="0"></a></td>
            <td width="8"></td>
            <td align="right" height="20" width="90"><a href="javascript:fnChangeAll(0)"><img src="/images/admin/button/btn_allcancel.gif" border="0"></a></td>
            <td width="8"></td>
            <!--
            <td align="right" height="20" width="70"><a href="javascript:fnChangeAll(2)"><img src="/images/admin/button/btn_alldelete.gif" border="0"></a></td>
            <td width="8"></td>
            -->
            <td align="right" height="20" width="40"><a href="javascript:fnDoApproval()"><img src="/images/admin/button/btn_checkok.gif" border="0"></a></td>
            <td width="8"></td>
            <td align="right" height="20" width="70"><a href="javascript:fnSaveExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
        </tr>
        <tr>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
            <td height="3"></td>
        </tr>
    </table>
    <!-- 모두승인, 모두승인취소, 확인 버튼 끝 //-->

    <!-- 과정기준조회 시작 //-->
    <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
            <td colspan="14" class="table_top_line"></td>
        </tr>

        <tr>
            <td class="table_title" width="180"><a href="javascript:fnSortOrderList('scsubj')" class="e">과정</a></b></td>
            <td class="table_title" width="30"><b>차수</b></td>
            <td class="table_title" width="40"><b><a href="javascript:fnSortOrderList('b.userid')" class="e">ID</a></b></td>
            <td class="table_title" width="40"><b><a href="javascript:fnSortOrderList('name')" class="e">성명</a></b></td>
            <td class="table_title" width="30">
                <b><kocca_select:select name="p_membergubun" sqlNum="membergubun"  param=" " onChange="fnSearchList('go');" attr=" 회원<br/>분류" selectedValue="<%= p_membergubun %>" isLoad="true" all="true" type="3" styleClass="table_title"/></b>
            </td>
            <td class="table_title">
                <b><kocca_select:select name="pp_chkfinal" sqlNum="chkfinal"  param=" " onChange="fnSearchList('go');" attr="승인상태" selectedValue="<%= pp_chkfinal %>" isLoad="true" all="true" type="3" styleClass="table_title"/></b>
            </td>
            <td class="table_title"><b><a href="javascript:fnSortOrderList('b.appdate')" class="e">신청일</a></b></td>
            <td class="table_title" width="40">
                <kocca_select:select name="p_resultcode" sqlNum="payresultcode"  param=" " onChange="fnSearchList('go');" attr="결제<br/>결과" selectedValue="<%= p_resultcode %>" isLoad="true" all="true" type="3" styleClass="table_title"/>
            </td>
            <td class="table_title">실결제액<br/>/수강료</td>
            <td class="table_title" width="70">결제일</td>
            <td class="table_title" width="60">
                <kocca_select:select name="p_paymethod" sqlNum="paymethodnm"  param=" " onChange="fnSearchList('go');" attr="결제방법" selectedValue="<%= p_paymethod %>" isLoad="true" all="true" type="3" styleClass="table_title"/>
            </td>
            <td class="table_title" width="50">결제자</td>
            <td class="table_title" align="center">취소요청</td>
        </tr>
        </form>
        <form name="ff" method="post" action="/servlet/controller.propose.ApprovalServlet">
            <input type="hidden" name="p_process" value="approvalProcess" />
            <input type="hidden" name="p_grcode" value="<%=ss_grcode%>" />
            <input type="hidden" name="p_gyear" value="<%=ss_gyear%>" />
            <input type="hidden" name="p_grseq" value="<%=ss_grseq%>" />
            <input type="hidden" name="p_grseqnm" value="<%=ss_grseqnm%>" />
            <input type="hidden" name="p_uclass" value="<%=ss_upperclass%>" />
            <input type="hidden" name="p_mclass" value="<%=ss_middleclass%>" />
            <input type="hidden" name="p_lclass" value="<%=ss_lowerclass%>" />
            <input type="hidden" name="p_subjcourse" value="<%=ss_subjcourse%>" />
            <input type="hidden" name="p_subjseq" value="<%=ss_subjseq%>" />
            <input type="hidden" name="p_superyn" value="<%=ss_superyn%>" />
            <input type="hidden" name="p_searchkind" value="<%=v_searchkind%>" />
            <input type="hidden" name="p_mastercd" value="<%=ss_mastercd%>" />
            <input type="hidden" name="p_masternm" value="" />
            <input type="hidden" name="p_step" value="<%=v_step%>" />
            <input type="hidden" name="p_appstatus" value="<%=v_appstatus%>" />

<%
    if (v_action.equals("go")) {
        ArrayList list = (ArrayList)request.getAttribute("ApprovalList");

        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

        String v_subj = "";
        String v_subjnm = "";
        String v_membergubun= "";
        String v_userid = "";
        String v_uname = "";
        String v_subjseq = "";
        String v_appdate = "";
        String v_chkfinal = "";
        String v_edustart = "";
        String v_isclosed = "";
        String v_scyear = "";
        String v_billstat = "";
        String v_tmp_subj = "";
        String v_iscourseYn = "";
        String v_course = "";
        String v_coursenm = "";

        String v_tid = "";
        String v_resultcode = "";
        String v_resultmsg = "";
        String v_paymethod = "";
        String v_paymethodnm = "";
        String v_pgauthdate = "";
        String v_pgauthtime = "";
        String v_buyername = "";
        String v_cancelyn = "";
        String v_cancelresult = "";
        String v_canceldate = "";
        String v_canceltime = "";
        String v_inputname = "";
        String v_inputdate = "";
        String v_year = "";

        int v_subjcnt = 0;

        for (i=0; i<list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i);

            v_biyong = dbox.getInt("d_biyong");
            v_total_biyong  += v_biyong;
            v_total_price += dbox.getInt("d_price");
            v_finalString = "";

            v_year = dbox.getString("d_year");
            v_subj = dbox.getString("d_scsubj");
            v_subjnm = dbox.getString("d_subjnm");
            v_membergubun = dbox.getString("d_membergubun");
            v_userid = dbox.getString("d_userid");
            v_uname = dbox.getString("d_name");
            v_subjseq = dbox.getString("d_scsubjseq");

            v_appdate = dbox.getString("d_appdate");
            v_chkfinal = dbox.getString("d_chkfinal");
            v_edustart = dbox.getString("d_edustart");
            v_isclosed = dbox.getString("d_isclosed");
            v_scyear = dbox.getString("d_scyear");
            v_billstat = dbox.getString("d_billstat");
            v_course = dbox.getString("d_course");
            v_coursenm = dbox.getString("d_coursenm");
            v_iscourseYn = dbox.getString("d_isbelongcourse");
            v_subjcnt = dbox.getInt("d_subjcnt");
            //v_usercnt = dbox.getInt("d_usercnt");


            v_tid = dbox.getString("d_tid");
            v_resultcode = dbox.getString("d_resultcode");
            v_resultmsg = dbox.getString("d_resultmsg");
            v_paymethod = dbox.getString("d_paymethod");
            v_paymethodnm = dbox.getString("d_paymethodnm");
            v_pgauthdate = dbox.getString("d_pgauthdate");
            v_pgauthtime = dbox.getString("d_pgauthtime");
            v_buyername = dbox.getString("d_buyername");
            v_cancelyn = dbox.getString("d_cancelyn");
            v_cancelresult = dbox.getString("d_cancelresult");
            v_canceldate = dbox.getString("d_canceldate");
            v_canceltime = dbox.getString("d_canceltime");
            v_inputname = dbox.getString("d_inputname");
            v_inputdate = dbox.getString("d_inputdate");

            //회원구분
            if(v_membergubun.equals("C")) {v_membergubun = "기업";}
            else if(v_membergubun.equals("U")) {v_membergubun = "대학";}
            else  {v_membergubun = "개인";}

            //결제 상태
            if(v_billstat.equals("")) {
                v_billstat = "미완료";
            }

            if (v_edustart.equals("")) {
                v_edustart = "0";
            }

            if ( v_isclosed.equals("N")){                       //////수료처리전에만 수정이 가능하다.
                if (v_step.equals("2") && (v_today < Integer.parseInt(v_edustart))){   ///학습시작전 조건
                    v_finalString="<input type='hidden' name='p_params' value='" + v_subj+ "," + v_scyear+ "," + v_subjseq+ "," + v_iscourseYn+ "," + v_course+ "," + v_userid + "'> <select name='p_chkfinal'>";

                 if (v_chkfinal.equals("Y")  ){
                     v_finalString +="<option value='B'>미처리</option><option value='Y' selected>승인</option><option value='D'>삭제</option>";
                 } else {
                     v_finalString +="<option value='B' selected>미처리</option><option value='Y'>승인</option><option value='D'>삭제</option>";
                 }

                 v_finalString += "</select>";

                 } else if (v_step.equals("2") && (v_today > Integer.parseInt(v_edustart)) && (!v_chkfinal.equals("Y")&&!v_chkfinal.equals("N")) ){ //학습시작후 미처리인 경우
                     v_finalString="<input type='hidden' name='p_params' value='" + v_subj+ "," + v_scyear+ "," + v_subjseq+ "," + v_iscourseYn+ "," + v_course+ "," + v_userid +"'><select name='p_chkfinal'>";

                     if (v_chkfinal.equals("B")){
                         v_finalString +="<option value='B' selected>미처리</option><option value='Y'>승인</option><option value='D'>삭제</option>";
                     }
                     v_finalString += "</select>";
                 } else {
                        if (v_chkfinal.equals("Y"))   v_finalString = "승인" ;
                        else if (v_chkfinal.equals("N"))  v_finalString = "반려";
                        else    v_finalString = "미처리";
                 }

            } else {  //수료처리 완료된 경우
                v_finalString = "<font color=red>학습종료</font>";

                if (v_chkfinal.equals("B")) {
                    v_finalString += "(미처리)";
                } else if (v_chkfinal.equals("Y")) {
                    v_finalString += "(승인)";
                } else if (v_chkfinal.equals("N")) {
                    v_finalString += "(반려)";
                }
            }

            if(v_iscourseYn.equals("Y")) {
                if(l == v_subjcnt) {
                    v_tmp_subj = "";
                }

                if (!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000")) {
                    l = 1;
                    v_usercnt++;
%>
        <tr class="table_02_1">
            <td class="table_02_1" ><font class="text_color01"><%=v_coursenm%></font></td>
            <td class="table_02_1" ><%=v_subjseq%></td>
            <td class="table_02_1" ><a href="javascript:fnViewMemberInfo('<%=v_userid%>', '<%= v_subj %>', '<%= v_subjseq %>', '<%= v_year %>')" class="e"><%=v_userid%></a></td>
            <td class="table_02_1" ><%=v_uname%></td>
            <td class="table_02_1" ><font class="text_color04"><%=v_membergubun%></font></td>
            <td class="table_02_1" ><%=v_finalString%></td>
            <td class="table_02_1" ><%=FormatDate.getFormatDate(v_appdate,"yyyy-MM-dd HH:mm:ss")%></td>
            <td class="table_02_1">
<%
                    if (!v_tid.equals("")) {
                        if (v_resultcode.equals("00")) {
%>
                <a href="javascript:whenCancel('<%=v_tid%>');">성공</a>
<%
                        } else {
%>
                실패
<%
                        }
                    }
%>
            </td>
            <td class="table_02_1"><%= dbox.getCommaInt("d_price") %>/<%=dbox.getCommaInt("d_biyong")%></td>
            <td class="table_02_1">
<%
                    if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
                            out.print(FormatDate.getFormatDate(v_pgauthdate,"yyyy-MM-dd"));
                       } else if (v_paymethod.equals("BankBook")) {
                            out.print(FormatDate.getFormatDate(v_inputdate,"yyyy-MM-dd"));
                       }
%>
            </td>
            <td class="table_02_1"><%=v_paymethodnm%></td>
            <td class="table_02_1">
<%
                    if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
                        out.print(v_buyername);
                   } else if (v_paymethod.equals("BankBook")) {
                        out.println(v_inputname);
                   }
%>
            </td>
            <td class="table_02_1"><% if (v_resultcode.equals("00")) { %><a href='javascript:;' onclick='whenSimpleLoad(<%= dbox.getAll() %>)'><%= dbox.getDate("d_needcancel") %></a><% } %></td>
        </tr>
<%
                    v_tmp_subj = v_course;
                } else {
                    l++;
%>
            <!--td class="table_02_1"><%//=v_subjnm%></font></td-->
<%
                }
            } else {
                v_usercnt++;
%>
        <tr class="table_02_1">
            <td class="table_02_1"><%=v_subjnm%></td>
            <td class="table_02_1"><%=v_subjseq%></td>
            <td class="table_02_1" ><a href="javascript:fnViewMemberInfo('<%=v_userid%>', '<%= v_subj %>', '<%= v_subjseq %>', '<%= v_year %>')" class="e"><%=v_userid%></a></td>
            <td class="table_02_1"><%=v_uname%></td>
            <td class="table_02_1"><font class="text_color04"><%=v_membergubun%></font></td>
            <td class="table_02_1"><%=v_finalString%></td>
            <td class="table_02_1"><%=FormatDate.getFormatDate(v_appdate,"yyyy-MM-dd HH:mm:ss")%></td>
            <td class="table_02_1">
<%
                if (!v_tid.equals("")) {
                    if (v_resultcode.equals("00")) {
%>
                <a href="javascript:whenCancel('<%=v_tid%>');">성공</a>
<%
                    } else {
%>
                실패
<%
                    }
                }
%>
            </td>
            <td class="table_02_1"><%= dbox.getCommaInt("d_price") %>/<%=dbox.getCommaInt("d_biyong")%></td>
            <td class="table_02_1">
<%
                if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
                    out.print(FormatDate.getFormatDate(v_pgauthdate,"yyyy-MM-dd"));
                } else if (v_paymethod.equals("BankBook")) {
                    out.print(FormatDate.getFormatDate(v_inputdate,"yyyy-MM-dd"));
                }
%>
            </td>
            <td class="table_02_1"><%=v_paymethodnm%></td>
            <td class="table_02_1">
<%
                if (v_paymethod.equals("Card") || v_paymethod.equals("VCard") || v_paymethod.equals("DirectBank"))  {
                    out.print(v_buyername);
                } else if (v_paymethod.equals("BankBook")) {
                    out.println(v_inputname);
                }
%>
            </td>
            <td class="table_02_1"><% if (v_resultcode.equals("00")) { %><a href='javascript:;' onclick='whenSimpleLoad(<%= dbox.getAll() %>)'><%= dbox.getDate("d_needcancel") %></a><% } %></td>
        </tr>

<%
            }
        }

        if (i == 0) {
%>
        <tr>
            <td align="center" bgcolor="#ffffff" height="100" colspan="14">등록된 내용이 없습니다.</td>
        </tr>
<%
        }
    }

%>

        </table>
        <!-- 과정기준조회 끝 //-->
        </form>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="left" class="ms">◈<b> 결제총금액 : <%=v_total_price%> 원</b></td>
                <td align="right" class="ms">◈<b> TOTAL : <%=v_usercnt%> 명</b></td>
            </tr>
        </table>

            <br/>

        </td>
    </tr>

</table>
<!-- form 끝 //-->

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
