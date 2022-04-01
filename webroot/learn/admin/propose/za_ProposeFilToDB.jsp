<%
//**********************************************************
//  1. 제      목: 입과 FileToDB
//  2. 프로그램명 : za_ProposeFileToDB.jsp
//  3. 개      요: 입과 FileToDB
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.propose.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet"  scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String ss_grcode = box.getString("s_grcode");           //교육그룹
    String ss_gyear = box.getString("s_gyear");            //년도
    String ss_grseq = box.getString("s_grseq");            //교육차수
    String ss_grseqnm = box.getString("s_grseqnm");          //교육차수명
    String ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String ss_subjcourse = box.getString("s_subjcourse");       //과정&코스
    String ss_subjseq = box.getString("s_subjseq");          //과정 차수
    String s_subjsearchkey = box.getString("s_subjsearchkey");
    String v_stuoverlap = box.getStringDefault("p_stuoverlap", "Y");
    String v_isgraduated = box.getStringDefault("p_isgraduated", "Y");

    String v_file1 = box.getString("p_file");
%>
<html>
<head>
<title>입과FileToDB11</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script language="VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    /**
     * 일괄 입과처리 (등록)
     */
    function fnRegGroupPropose() {
        action("insertFileToDB");
    }

    /**
     * 일괄 입과처리 미리보기 (등록)
     */
    function fnPreview() {
        action("previewFileToDB");
    }


    function action(p_process) {
        var docForm = document.form3;

        if(blankCheck(docForm.p_file.value)){
            alert("업로드할 엑셀 파일을 선택해 주시기 바랍니다.");
            return;
        }

        if (docForm.p_file.value.length > 0 ){
            var data = docForm.p_file.value;
            data = data.toUpperCase(data);
            if (data.indexOf(".XLS") < 0) {
                alert("파일종류는 XLS 파일만 가능합니다.");
                return;
            }
        }

        if (document.form1.s_grcode.value == "ALL") {
            alert("교육그룹을 선택하세요.");
            return;
        }

        if (document.form1.s_grseq.value == "ALL") {
            alert("교육차수를 선택하세요.");
            return;
        }

        top.ftop.setPam();

        /*if (document.form1.grcodeText.value == '') {
        alert("회사코드를 입력 하세요");
        document.form1.grcodeText.focus();
        return;
        }*/
        /*
        if(docForm.p_stuoverlap.checked == true) {
        docForm.p_stuoverlap.value = "Y";
        }else{
        docForm.p_stuoverlap.value = "N";
        }*/


        if(docForm.p_isgraduated.checked == true) {
            docForm.p_isgraduated.value = "Y";
        }else{
            docForm.p_isgraduated.value = "N";
        }

        docForm.s_subjnm.value = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].text;
        docForm.s_grcodenm.value = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].text;
        docForm.s_subjseqgrnm.value = document.form1.s_subjseq.options[document.form1.s_subjseq.selectedIndex].text;

        docForm.s_grcode.value = document.form1.s_grcode.value;
        docForm.s_gyear.value = document.form1.s_gyear.value;
        docForm.s_grseq.value = document.form1.s_grseq.value;
        docForm.s_upperclass.value = document.form1.s_upperclass.value;
        docForm.s_middleclass.value = document.form1.s_middleclass.value;
        docForm.s_subjcourse.value = document.form1.s_subjcourse.value;
        docForm.s_subjseq.value = document.form1.s_subjseq.value;
        docForm.s_usergrcode.value =  document.form1.grcodeText.value;

        docForm.action = "/servlet/controller.propose.AcceptServlet";
        docForm.p_process.value = p_process;                              //insertFileToDB,previewFileToDB
        docForm.submit();
    }

    function whenSelection(p_action) {
        document.form1.p_process.value = "proposeFileToDB";
        document.form1.p_action.value = p_action;
        document.form1.submit();
    }


    // 검색조건 체크
    function chkParam() {
        if (document.form1.s_grcode.value == "" || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return false;
        }

        if (document.form1.s_grseq.value == "" || document.form1.s_grseq.value == '----') {
            alert("교육차수를 선택하세요.");
            return false;
        }

        if (document.form1.s_subjcourse.value != "" && document.form1.s_subjcourse.value != '----') {
            if (document.form1.s_subjseq.value == "" || document.form1.s_subjseq.value == '----') {
                alert("과정차수를 선택하세요.");
                return false;
            }
        }
    }

    // 과정코드 엑셀파일
    function whenExcel() {
        if (chkParam() == false) {
            return;
        }

//        window.self.name = "proposeExcel";
        open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
        document.form1.target = "openExcel";
        document.form1.action='/servlet/controller.propose.AcceptServlet';
        document.form1.p_process.value = 'proposeListExcel';
        document.form1.submit();

//        document.form1.target = window.self.name;
    }

    var startup = 0;
    function Main_s_grseq(){
        if (startup > 0) {
            changes_grseq(document.form1.s_grcode.value, document.form1.s_gyear.value);
        }
    }

    function Main_subjcourse(){
        var subjSearchKey = '';
        if(startup > 2) {
            if (document.form1.s_subjsearchkey == "undefine") {
                subjSearchKey = "";
            } else {
                subjSearchKey = document.form1.s_subjsearchkey.value;
            }

            changes_subjcourse(document.form1.s_grcode.value, document.form1.s_upperclass.value, document.form1.s_middleclass.value, document.form1.s_lowerclass.value, document.form1.s_grseq.value , subjSearchKey );
            // alert(document.form1.s_grcode.value+ " , " + document.form1.s_upperclass.value+ " , " + document.form1.s_middleclass.value+ " , " + document.form1.s_lowerclass.value+ " , " + document.form1.s_subjseq.value+ " , " +'' );
        }
        startup++;
    }

    function searchTaxBill() {
        var url = "/servlet/controller.library.SearchServlet?p_process=taxBill&s_grcode=<%=ss_grcode%>&s_gyear=<%=ss_gyear%>&s_grseq=<%=ss_grseq%>&p_searchtext=";
        open_window("",url,"100","70","595","455",'','','','true');
    }

    function returnTaxBill(data) {
        $("#tid").val(data.d_tid);
        $("#taxBillName").html("<b>계산서명</b> : " + data.d_goodname);
    }

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
        $("#oGyear").append("<option value=\"\">-- 선택 --</option>");
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");

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
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");


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
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");


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
        $("#oSubjseq").append("<option value=\"ALL\">-- 전체 --</option>");
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
        $("#oMiddleclass").append("<option value=\"\">-- 전체 --</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

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
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

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

//-->
</script>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
        <!-- title 시작 //-->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
            <tr>
                <td><img src="/images/admin/propose/p_title06.gif" ></td>
                <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
        </table>
        <!-- title 끝 //-->

        <br/>
        <!-- form 시작 //-->
        <form name="form1" method="post" action="/servlet/controller.propose.AcceptServlet">
            <input type="hidden" name="p_process" value="">
            <input type="hidden" name="p_action"  value="">
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
                               <td width="12%" class="_tdT">
                                   <font color="red">★</font>교육그룹
                               </td>
                               <td width="22%" class="_tdS">
                                   <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                               </td>
                               <td width="12%" class="_tdT">
                                   <font color="red">★</font>연도
                               </td>
                               <td width="14%" class="_tdS">
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
                               <td width="12%" class="_tdT">
                                   <font color="red">★</font>교육차수
                               </td>
                               <td width="28%" class="_tdS">
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
                                   <a href="javascript:fnSubjSearchByName()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>
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
                               <td class="_tdT"><!--<font color="red">★</font>-->회사코드</td>
                               <td class="_tdS"><input type="text" id="grcodeText" name="grcodeText" ></td>
                               <td colspan="2" align="right"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_course_numexcel.gif" border="0"></a></td>
                           </tr>

                                </td>
                            </tr>
                            <tr>
                                <td height="9"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            </form>
            <!-- form 끝 //-->

            <br/>
            <!-- 찾아보기 시작 //-->
            <form name="form3" method="post" action="/servlet/controller.propose.AcceptServlet" enctype="multipart/form-data">
                <input type="hidden" name="p_process" value="">
                <input type="hidden" name="p_action" value="">
                <input type="hidden" name="s_grcode" value="">
                <input type="hidden" name="s_gyear" value="">
                <input type="hidden" name="s_grseq" value="">
                <input type="hidden" name="s_upperclass" value="">
                <input type="hidden" name="s_middleclass" value="">
                <input type="hidden" name="s_subjcourse" value="">
                <input type="hidden" name="s_subjseq" value="">
                <input type="hidden" name="s_subjnm" value="">
                <input type="hidden" name="s_grcodenm" value="">
                <input type="hidden" name="s_subjseqgrnm" value="">
                <input type="hidden" name="s_usergrcode" value="">
                <input type="hidden" name="p_inputlevel" value="student">

            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center">
                        <table width="21%" height="26" border="0" cellpadding="0" cellspacing="0">
                            <tr id="billneedbutton" style="display:none">
                                <td>
                                    <input type="hidden" name="tid" id="tid" disabled/><font id="taxBillName"><b>계산서명</b> : </font>
                                </td>
                                <td align="right">
                                    <a href="javascript:searchTaxBill()"><img src="/images/admin/button/search3_butt.gif" border=0/></a>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" colspan="2">
                                    <input name="p_file" type="FILE" class="input" size="145">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <!-- 찾아보기 끝 //-->
            <br/>
            <!-- 미리보기, 확인, 취소 버튼 시작 //-->
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td colspan="2" height="20">
                        <b>계산서발급</b>
                        <input type="radio" name="billYn" value="true" onclick="$('#billneedbutton').fadeIn(300);$('#tid').attr('disabled','')"> Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" name="billYn" value="false" onclick="$('#billneedbutton').fadeOut(200);$('#tid').attr('disabled','true');" checked> No
                    </td>
                </tr>
                <tr>
                    <td width=10></td>
                    <td height="20" align="right"><a href="javascript:fnRegGroupPropose()"><img src="/images/admin/button/btn_add.gif"  border="0"></a></td>
                    <td width=10></td>
                    <td align="right" width="32"><a href="javascript:fnPreview()"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
                </tr>
            </table>

            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="928" height="20" align="left">데이터체크 유무선택</td>
                </tr>
                <tr>
                    <td width="928" height="20" align="left"><input type="checkbox" disabled checked>개설차수 비교(기본 : 개설된 차수가 없으면 에러처리)</td>
                </tr>
                <tr>
                    <td width="928" height="20" align="left"><input type="checkbox" disabled checked>회원 DB 조회(기본 : 회원 DB에 존재하지 않으면 에러처리)</td>
                </tr>
                <tr>
                    <td width="928" height="20" align="left"><input type="checkbox" disabled checked>수강신청중/학습중체크(기본 : 해당 과정차수에 대상자가 수강신청을 한기록이 있으면 에러처리)</td>
                </tr>
                <tr>
                    <td width="928" height="20" align="left"><input type="checkbox" name="p_isgraduated" <%if(v_isgraduated.equals("Y")){%>checked<%}%>>이수(수료)자체크 (해당과정 이수자 에러처리)</td>
                </tr>
            </table>
            </form>
            <!-- 미리보기, 확인, 취소 버튼 끝 //-->
            <br/><br/><br/>

            <!-- 주의사항 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="dir_txt"><b><font size="3">(주의사항 - 필독)</font></b></td>
                </tr>
                <tr>
                    <td height="8"></td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">1. <font color="#E60873">교육그룹과 연도, 교육차수를 선택한 후 "과정/차수코드 엑셀다운로드"를 눌러서 양식을 다운로드</font></td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">2. <font color="#E60873">다운로드 된 문서를 기준으로 사용자ID를 추가하여 반드시 "Excel" 문서로 저장한다.</font></td>
                </tr>
                <tr>
                    <td height="20" class="dir_txt">3. 엑셀파일 작성시 <font color="#A661D7">[<b>학습자ID,( 차수 ), ( 과정 )</b>]</font>의 순서로 만들 것.</td>
                </tr>
            </table>
            <!-- 주의사항 끝 //-->

            <br/>
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="left">샘플</td>
                </tr>
                <tr>
                    <td><img src="/images/admin/propose/PropExcel3.gif" border="0"></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td><%@ include file = "/learn/library/getJspName.jsp" %></td>
    </tr>
</table>
</body>
</html>