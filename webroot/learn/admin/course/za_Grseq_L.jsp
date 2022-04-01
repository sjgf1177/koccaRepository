<%
//**********************************************************
//  1. 제      목: 교육차수 리스트 조회화면
//  2. 프로그램명: za_Grseq_L.jsp
//  3. 개      요: 교육차수 리스트 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.course.*" %>
<%@ page import="com.credu.common.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String ss_grcode = box.getString("s_grcode");               //교육그룹
    String ss_gyear = box.getString("s_gyear");                //년도
    String ss_upperclass = box.getString("s_upperclass");          //과정대분류
    String ss_middleclass = box.getString("s_middleclass");        //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");          //과정소분류
    String ss_subjcourse= box.getString("s_subjcourse");           //과정&코스
    String ss_grseq = box.getString("s_grseq");                   //과정 차수
    String s_subjsearchkey = box.getString("s_subjsearchkey");   //과정명
    // String s_biyong = box.getString("s_biyong");                  //교육구분
    // String s_area = box.getString("s_area");                     //유/무료

    String v_area = box.getString("v_area");
    String v_biyong = box.getString("v_biyong");

    String v_gyear ="", v_grseq="", v_course="", v_cyear="", v_courseseq="", v_courseStr="";
    String v_subj="",v_year="",v_subjseq="";
    String v_subjnmlink = "";
    String v_precourse = "";
    String s_gadmin = box.getSession("gadmin");
    String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

    String isExcel = "";

%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<style>
th {font-size:12px;}
</style>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">

    function MM_jumpMenu(targ,selObj,restore){ //v3.0
      eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
      if (restore) selObj.selectedIndex=0;
    }

    /**
     * 목록 조회
     * @param ss_action
     */
    function fnSearchSubjList( actType ) {
        if (actType == "go")    {
            if ( $("#oGrcode").val() == "") {
                alert("교육그룹을 선택하세요.");
                $("#oGrcode").focus();
                return ;
            } else if ( $("#oGyear").val() == "") {
                alert("연도를 선택하세요.");
                $("#oGyear").focus();
                return ;
            } else if ( $("#oGrseq").val() == "") {
                alert("교유차수를 선택하세요.");
                $("#oGrseq").focus();
                return ;
            }
            // top.ftop.setPam();
        }
        $("input[name=p_process]").val("listPage");
        $("input[name=p_action]").val(actType);
        $("form[name=form1]").submit();;
    }

    function elementForm() {
      var form = document.form1;
      var s_url = "";
      s_url += "&s_upperclass="+form.s_upperclass.value;
      s_url += "&s_middleclass="+form.s_middleclass.value;
      s_url += "&s_lowerclass="+form.s_lowerclass.value;
      s_url += "&s_subjsearchkey="+form.s_subjsearchkey.value;
      s_url += "&s_subjcourse="+form.s_subjcourse.value;
      return s_url;
    }

    //교육차수 추가
    function addGrseq(){
    <%  if (ss_grcode.equals("") || ss_grcode.equals("ALL")) {            %>
        alert ("교육그룹 선택후 go버튼을 먼저 누르신후 차수를 추가하십시요")
        return
    <%  } else {                             %>
        var url=document.form1.action+'?p_process=insertPage&p_grcode=<%=ss_grcode%>';
        window.open(url,'insert','width=620,height=600, scrollbars=yes');
    <%  }                                    %>
    }

    //교육차수 상세화면
    function whenGrseq(gyear,grseq){
        var url=document.form1.action+'?p_process=updatePage&p_grcode=<%=ss_grcode%>&p_gyear='+gyear+'&p_grseq='+grseq+elementForm();
        window.open(url,'insert','width=600,height=580');
    }

    //과정지정화면으로 이동
    function assignSubjCourse(gyear,grseq){
        var url=document.form1.action+'?p_process=assignPage&p_grcode=<%=ss_grcode%>&p_gyear='+gyear+'&p_grseq='+grseq+elementForm();
        window.open(url,'insert','toolbar=no, location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=900,height=600');
    }

    //과정일괄 수정화면으로 이동
    function updateSubjCourse(gyear,grseq){
        var url=document.form1.action+'?p_process=updatePageSubjCourse&p_grcode=<%=ss_grcode%>&p_gyear='+gyear+'&p_grseq='+grseq;
        window.open(url,'insert','toolbar=no, location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=900,height=600');
    }

    //수료점수일괄 수정화면으로 이동
    function updateSubjScore(gyear,grseq){
        var url=document.form1.action+'?p_process=updatePageSubjScore&p_grcode=<%=ss_grcode%>&p_gyear='+gyear+'&p_grseq='+grseq;
        window.open(url,'insert','toolbar=no, location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=900,height=600');
    }



    function delCourse(gyear,grseq,course,cyear,courseq){
        if(!confirm("모든 학습데이터까지 삭제되어 복원할 수 없습니다.\n\n삭제하시겠습니까?")) return;
        setHiddenVals("delCourse",gyear,grseq,course,cyear,courseq);
        document.form1.submit();
    }
    function addCourse(coursenm,gyear,grseq,course){
        if(!confirm("["+coursenm+"] 코스의 차수를 추가합니다.\n\n추가하시겠습니까?")) return;
        setHiddenVals("addCourse",gyear,grseq,course,"","");
        document.form1.submit();
    }
    function delSubj(gyear,grseq,subj,year,subjseq){
        if(!confirm("모든 학습데이터까지 삭제되어 복원할 수 없습니다.\n\n삭제하시겠습니까?")) return;
        setHiddenVals("delSubj",gyear,grseq,subj,year,subjseq);
        document.form1.submit();
    }
    function addSubj(subjnm,gyear,grseq,subj){
        if(!confirm("["+subjnm+"] 과정의 차수를 추가합니다.\n\n추가하시겠습니까?")) return;
        setHiddenVals("addSubj",gyear,grseq,subj,"","");
        document.form1.submit();
    }

    function setHiddenVals(process, gyear,grseq,subjcourse,year,seq){
        document.form1.p_action.value = "go";
        document.form1.p_process.value= process;
        document.form1.p_mgyear.value=gyear;
        document.form1.p_mgrseq.value=grseq;
        document.form1.p_msubjcourse.value=subjcourse;
        document.form1.p_myear.value=year;
        document.form1.p_mseq.value=seq;
    }

    function whenSubjseq(grcode,gyear,grseq,subj,year,subjseq,isonoff){
        var url=document.form1.action+'?p_process=updateSubjseqPage&p_grcode='+grcode+'&p_gyear='+gyear+'&p_grseq='+grseq+'&p_subj='+subj+'&p_year='+year+'&p_subjseq='+subjseq+'&p_isonoff='+isonoff;
        window.open(url,'insert','resizable=yes,scrollbars=yes,status=yes,width=800,height=700');
    }
    function whenCourseseq(grcode,gyear,grseq,course,cyear,courseseq){
        var url='/servlet/controller.course.CourseServlet?p_process=updateCourseseqPage&p_grcode='+grcode+'&p_gyear='+gyear+'&p_grseq='+grseq+'&p_course='+course+'&p_cyear='+cyear+'&p_courseseq='+courseseq;
        window.open(url,'insert','resizable=yes,scrollbars=yes,status=yes,width=800,height=700');
    }

    //엑셀다운
    function whenExcel(ss_action) {
        /*alert("준비 중 입니다.");*/

        if(ss_action != "go"){
            alert('먼저 조회해 주십시오');
            return;
      }

        document.form1.action='/servlet/controller.course.GrseqServlet';
        document.form1.p_process.value = 'listPage';
        document.form1.p_action.value = ss_action;
        document.form1.isExcel.value = "ok";
        document.form1.submit();

    }

    // 미입과학습자 삭제
    function delGrseq() {
    	var check = chkSelected();
      if (check == 1) {
        alert('미입과 과정을 선택하세요');
        return;
      }
      if(!confirm("모든 학습데이터까지 삭제되어 복원할 수 없습니다.\n\n삭제하시겠습니까?")) return;

      document.form1.p_mgyear.value=document.form1.s_gyear.value;
      document.form1.p_mgrseq.value=document.form1.s_grseq.value;
      document.form1.p_action.value = "go";
      document.form1.p_process.value= "delSubjArr";
      document.form1.submit();
    }
    
    function chkSelected(){
    	var result = 0;
    	if($('input:checkbox[name=p_checks]:checked').size() == 0){
    		result = 1; 
    	}
    	
    	$('input:checkbox[name=p_checks]:checked').each(function(){
    		
    	});
    	
    	
    	return result;
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

        /*
        $("#oSubjcourse").bind("change", function(){

            if ( $(this).val() == "ALL" ) {
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
        */

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

        $("#oCheckAll").bind("click", function() {
        	var checked = $(this).is(":checked");
        	$("input:checkbox[name=p_checks]").each(function(){
        		if(!$(this).is(':disabled')){
        			$(this).attr("checked", checked);
        		}
        	});
        });

        $("input:checkbox[name=p_checks]").bind("click", function() {
            var allCnt = $("input:checkbox[name=p_checks]").length;
            var checkedCnt = $("input:checkbox[name=p_checks]:checked").length;

            if ( allCnt == checkedCnt ) {
                $("#oCheckAll").attr("checked", true);
            } else {
                $("#oCheckAll").attr("checked", false);
            }
        });

    });

    /**
     * 교육 그룹별 연도 selectbox 지정
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oGyear").append("<option value=\"\">-- 선택 --</option>");
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");

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
        $("#oGrseq").append("<option value=\"\">-- 선택 --</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");

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
        $("#oSubjcourse").append("<option value=\"ALL\">-- 전체 --</option>");

        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSubjcourse").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

    /**
     * 교육차수별 과정 selectbox 지정
     */
    /*
    function fnSetSubjSeqList( result ) {
        if ( result.selectBoxList.length > 0 ) {
            $("#oSubjseq").val( result.selectBoxList[0].d_code );
        }
    }
    */

    /**
     * ?
     */
    function fnSubjSearchByName() {
        if ( $("#oGrseq").val() == "" ) {
            alert("교육 차수를 먼저 선택하세요.");
            $("#oGrseq").focus();
            return;
        } else {
            $("#oGrseq").trigger("change");
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
    
    // 차수개설 엑셀업로드
    function fnExcelUpload(){
    	$("input[name=p_process]").val("excelUpload");
        $("form[name=form1]").submit();;
    }

</script>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.course.GrseqServlet">
    <input type="hidden" name="p_process" value="listPage" />
    <input type="hidden" name="p_action" value="" />
    <input type="hidden" name="p_grcode" value="<%=ss_grcode%>" />
    <input type="hidden" name="p_mgyear" value="" />
    <input type="hidden" name="p_mgrseq" value="" />
    <input type="hidden" name="p_msubjcourse" value="" />
    <input type="hidden" name="p_myear" value="" />
    <input type="hidden" name="p_mseq" value="" />
    <input type="hidden" name="p_subj" value="" />
    <input type="hidden" name="p_subjnm" value="" />
    <input type="hidden" name="isExcel" value="" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/course/c_title02.gif" alt="asdf"></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br/>
            <!-- form 시작 //-->
            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">

                        <table cellspacing="0" cellpadding="4" class="form_table_bg" >
                            <colgroup>
                                <col style="width:8%;" />
                                <col style="width:27%;" />
                                <col style="width:8%" />
                                <col style="width:10%;" />
                                <col style="width:8%;" />
                                <col style="width:33%;" />
                                <col style="width:6%;" />
                            </colgroup>
                            <tr>
                                <th class="_tdT"><font color="red">★</font>교육그룹</th>
                                <td class="_tdS">
                                    <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 선택 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                                </td>
                                <th class="_tdT">연도</th>
                                <td class="_tdS">
<%
    if ( !ss_gyear.equals("") ) {
%>
                                    <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="-- 선택 --" type="sqlID" param="<%= ss_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= ss_gyear %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_gyear" id="oGyear">
                                        <option value="">-- 선택 --</option>
                                    </select>
<%
    }
%>
                                </td>
                                <th class="_tdT">교육차수</th>
                                <td class="_tdS">
<%
    if ( !ss_grseq.equals("") ) {
%>
                                    <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="-- 선택 --" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear %>" sqlID="selectBox.grSeqList" selectedValue="<%= ss_grseq %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_grseq" id="oGrseq">
                                        <option value="">-- 선택 --</option>
                                    </select>
<%
    }
%>

                                </td>
                                <td rowspan="3" style="text-align:left;">
                                    <a href="javascript:fnSearchSubjList('go');"><img src = "/images/admin/button/b_go.gif" border = "0" align="absmiddle"></a>
                                </td>
                            </tr>
                            <tr>
                                <th class="_tdT">대분류</th>
                                <td class="_tdS">
                                    <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                </td>
                                <th class="_tdT">중분류</th>
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
                                <th class="_tdT">소분류</th>
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
                                <th class="_tdT">과정검색</th>
                                <td class="_tdS">
                                    <input type="hidden" name="p_returnurlkey" value="<%=request.getRequestURI()%>" />
                                    <input type="text" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode == '13') fnSubjSearchByName();" value="<%= s_subjsearchkey %>" />
                                    <input type="text" name="s_temp" id="oTemp" style="display:none;" />
                                    <a href="javascript:fnSubjSearchByName();"><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle" /></a>
                                </td>
                                <th class="_tdT">과정</th>
                                <td class="_tdS" colspan="3">
<%
        if ( !ss_grseq.equals("") ) {
            s_subjsearchkey = ( s_subjsearchkey.equals("") ) ? "%" : s_subjsearchkey;
%>
                                    <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="-- 전체 --" optionTitleValue="ALL" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_grseq + ',' + s_subjsearchkey %>" sqlID="selectBox.subjList" selectedValue="<%= ss_subjcourse %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_subjcourse" id="oSubjcourse">
                                        <option value="">-- 전체 --</option>
                                    </select>
<%
    }
%>
                                </td>
                            </tr>
                            <tr>
                                <th class="_tdT">교육구분</th>
                                <td class="_tdS">
                                    <select name="p_area">
                                        <option value="">-전체-</option>
                                        <option value="G0" <% if(v_area.equals("G0")){ %>selected<% } %> >게임</option>
                                        <option value="K0" <% if(v_area.equals("B0")){ %>selected<% } %> >방송</option>
                                        <option value="B0" <% if(v_area.equals("K0")){ %>selected<% } %> >문화</option>
                                    </select>
                                </td>
                                <th class="_tdT">유/무료</th>
                                <td class="_tdS" colspan="2">
                                    <select name="pp_biyong">
                                        <option value="">-전체-</option>
                                        <option value="Y"  <% if(v_biyong.equals("Y")){ %>selected<% } %> >유료</option>
                                        <option value="N"  <% if(v_biyong.equals("N")){ %>selected<% } %> >무료</option>
                                    </select>
                                </td>
                                <td class="_tdS" colspan="2" align="right">
                                	<input type="button" onclick="javascript:fnExcelUpload();" value="차수개설 엑셀업로드"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <!-- form 끝 //-->
            <br/>

<%
    if (!ss_grcode.equals("")) {
%>
            <!-- 교육차수추가 버튼 시작 //-->
            <table width="970" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" height="20">
                        <a href="javascript:delGrseq()"><img src="/images/admin/button/mdel_btn.gif"  border=0 alt="미입과 삭제"></a>&nbsp;
                        <a href="javascript:addGrseq()"><img src="/images/admin/button/btn_eduplus.gif"  border=0 alt="교육차수추가"></a>&nbsp;
                        <a href="javascript:whenExcel('go')"><img src="/images/admin/button/btn_excelprint.gif" border="0" alt="엑셀다운"></a></td>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!-- 교육차수추가 버튼 끝 //-->
<%
    }
%>
            <!-- 교육차수 관리 시작 //-->
            <table width="970" class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="14" class="table_top_line"></td>
                </tr>
                <tr height="25">
                    <td width="9%" class="table_title"><b>교육차수</b></td>
                    <td width="23%" class="table_title"><b>과정</b></td>
                    <!--td width="5%" class="table_title"><b>구분</b></td-->
                    <td width="9%" class="table_title"><b>교육그룹내<br/>과정차수</b></td>
                    <td width="10%" class="table_title"><b>신청기간</b></td>
                    <td width="10%" class="table_title"><b>교육기간</b></td>
                    <td width="4%" class="table_title"><b>정원</b></td>
                    <td width="4%" class="table_title"><b>신청인원</b></td>
                    <td width="4%" class="table_title"><b>승인인원</b></td>
                    <td width="4%" class="table_title"><b>수료인원</b></td>
                    <td width="4%" class="table_title"><b>수강료</b></td>
                    <td width="4%" class="table_title"><b>자동승인여부</b></td>
                    <td width="4%" class="table_title"><b>복습기간</b></td>
                    <td width="5%" class="table_title">
                        <input type="checkbox" name="p_chkeckall" id="oCheckAll" />
                    </td>
                    <td width="13%" class="table_title"><b>기능</b></td>
                </tr>
<%
    if (box.getString("p_action").equals("go")) {
        ArrayList  list = (ArrayList)request.getAttribute("GrseqList");

        for (int i = 0; i < list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i);

            int subjcnt = dbox.getInt("d_subjcnt");

            if( subjcnt >= 4){
                subjcnt = 3;
            }

            v_subjnmlink = "<a href=\"javascript:whenSubjseq('"+ ss_grcode +"','"+ dbox.getString("d_gyear") +"','"+ dbox.getString("d_grseq") +"','"+ dbox.getString("d_subj") +"','"+ dbox.getString("d_year") +"','"+ dbox.getString("d_subjseq") +"','"+ dbox.getString("d_isonoff") +"')\" class='b'>"+ dbox.getString("d_subjnm") +"</a>";
%>
                <tr height="25">
<%
            if (!v_grseq.equals(dbox.getString("d_grseq")) || !v_gyear.equals(dbox.getString("d_gyear")) ){
                v_grseq = dbox.getString("d_grseq");
                v_gyear = dbox.getString("d_gyear");

%>
                    <td align="center" bgcolor="#eeeeee" rowspan="<%=dbox.getInt("d_rowspan_grseq")%>">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center"><b><%=v_gyear%>-<%=v_grseq%></b>
                                    <br/>
                                    <a href="javascript:whenGrseq('<%=v_gyear%>','<%=v_grseq%>')"><%=dbox.getString("d_grseqnm")%></a>
                                </td>
                            </tr>
                            <tr>
                                <td height="5"></td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <a href="javascript:assignSubjCourse('<%=v_gyear%>','<%=v_grseq%>')"><img src="/images/admin/course/appointment1_butt.gif" width="58" height="18" border=0></a>
                                </td>
                            </tr>
<%
                if(!dbox.getString("d_subj").equals("")) {
%>
                            <tr>
                                <td align="center">
                                    <a href="javascript:updateSubjCourse('<%=v_gyear%>','<%=v_grseq%>')"><img src="/images/admin/course/modify1_butt.gif" border=0></a>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <a href="javascript:updateSubjScore('<%=v_gyear%>','<%=v_grseq%>')"><img src="/images/admin/course/editscore_butt.gif" border=0></a>
                                </td>
                             </tr>
<%
                }
%>
                        </table>
                    </td>
<%
            }

            //if (dbox.getString("d_subj") == null || (dbox.getString("d_subj").equals(""))){
            if (dbox.getString("d_grseqnm") == null || (dbox.getString("d_grseqnm").equals(""))){
%>
                    <td class="table_02_1" colspan="14" align="center">등록된 과정이 없습니다.</td>
<%
            } else {
                v_courseStr = dbox.getString("d_course") + dbox.getString("d_cyear") + dbox.getString("d_courseseq");
                if (!dbox.getString("d_course").equals("") && !dbox.getString("d_course").equals("000000") && !v_courseStr.equals(v_course+v_cyear+v_courseseq)){
                    v_course    = dbox.getString("d_course");
                    v_cyear     = dbox.getString("d_cyear");
                    v_courseseq = dbox.getString("d_courseseq");
%>
                    <!--<td class="table_02_2" align="center">-</td>-->
                    <td class="table_02_1" align="center" rowspan="  <%=subjcnt %> ">
                        <font class="text_color03">[전문가] <%=v_courseseq%><br/>
                        <a href="javascript:whenCourseseq('<%=ss_grcode%>','<%=dbox.getString("d_gyear")%>','<%=dbox.getString("d_grseq")%>','<%=v_course%>','<%=v_cyear%>','<%=v_courseseq%>')"><%=dbox.getString("d_coursenm")%></a></font>
                    </td>
                    <td class="table_02_2"><font class="text_color04"><%=v_subjnmlink%></font></td>
 <%
                } else if (dbox.getString("d_course").equals("000000")) {
                    v_course    = dbox.getString("d_course");
                    v_cyear     = dbox.getString("d_cyear");
                    v_courseseq = dbox.getString("d_courseseq");
%>
                    <!--<td class="table_02_2" align="center">-</td>-->
                    <td class="table_02_2"><font class="text_color04"><%=v_subjnmlink%></font></td>
<%
                } else {
%>
                    <!--<td class="table_02_2" align="center">-</td>-->
                    <td class="table_02_2"><font class="text_color04"><%=v_subjnmlink%></font></td>
<%
                }
%>
                    <!--td class="table_02_1" align="center"><%=dbox.getString("d_isonoffnm")%></td-->
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? StringManager.cutZero(dbox.getString("d_subjseqgr")) : "&nbsp;"%></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? FormatDate.getFormatDate(dbox.getString("d_propstart"), "yyyy/MM/dd") + " ~ " + FormatDate.getFormatDate(dbox.getString("d_propend"), "yyyy/MM/dd") : "&nbsp;"%></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? FormatDate.getFormatDate(dbox.getString("d_edustart"),"yyyy/MM/dd")   + " ~ " + FormatDate.getFormatDate(dbox.getString("d_eduend")  ,"yyyy/MM/dd") : "&nbsp;"%></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? dbox.getInt("d_studentlimit") : "&nbsp;"%></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? dbox.getInt("d_cnt_propose")  : "&nbsp;"%><!--d_proposecnt--></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? dbox.getInt("d_cnt_student")  : "&nbsp;"%><!--d_studentcnt--></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? dbox.getInt("d_cnt_stold")    : "&nbsp;"%><!--d_stoldcnt--></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? dbox.getInt("d_biyong")       : "&nbsp;"%><!--d_biyong--></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? dbox.getString("d_autoconfirm")  : "&nbsp;"%><!--d_autoconfirm--></td>
                    <td class="table_02_1" align="center"><%=!dbox.getString("d_subj").equals("") ? dbox.getInt("d_reviewdays")  : "&nbsp;"%><!--d_reviewdays--></td>
                    <td class="table_02_1" align="center">
<%
                //int i4Cnt = dbox.getInt("d_studentlimit") + dbox.getInt("d_cnt_propose") + dbox.getInt("d_cnt_student") + dbox.getInt("d_cnt_stold");
				int i4Cnt = dbox.getInt("d_cnt_propose") + dbox.getInt("d_cnt_student") + dbox.getInt("d_cnt_stold");
                if (!dbox.getString("d_subj").equals("")) {
%>
                        <input type="checkbox" name="p_checks" id="oChecks<%= i %>" value="<%=ss_grcode%>,<%=dbox.getString("d_gyear")%>,<%=dbox.getString("d_grseq")%>,<%=dbox.getString("d_subj")%>,<%=dbox.getString("d_year")%>,<%=dbox.getString("d_subjseq")%>,<%=dbox.getString("d_isonoff")%>" <% if(i4Cnt == 0) { out.print("checked"); }else{ out.print("disabled"); } %>>
<%
                }
%>
                    </td>
 <%
                if (!dbox.getString("d_subj").equals("") && v_course.equals("000000")){
                    v_subj = dbox.getString("d_subj");
                    v_year = dbox.getString("d_year");
                    v_subjseq = dbox.getString("d_subjseq");
%>
                    <td class="table_02_1" align="center">
                        <table width="96%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center">
                                    <a href="javascript:delSubj('<%=v_gyear%>','<%=v_grseq%>','<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>')"><img src="/images/admin/button/btn_del.gif"  border="0"></a>
                                </td>
                                <td align="center">
                                    <a href="javascript:addSubj('<%=dbox.getString("d_subjnm")%>','<%=v_gyear%>','<%=v_grseq%>','<%=v_subj%>')"><img src="/images/admin/button/btn_add.gif"  border="0"></a>
                                </td>
                            </tr>
                        </table>
                    </td>
<%
                } else if (!dbox.getString("d_subj").equals("") && !dbox.getString("d_course").equals("000000") && !v_courseStr.equals(v_precourse)){
                    v_precourse = v_courseStr;
%>
                    <td class="table_02_1" align="center" rowspan="<%=subjcnt%>">
                        <table width="96%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center">
                                    <a href="javascript:delCourse('<%=v_gyear%>','<%=v_grseq%>','<%=v_course%>','<%=v_cyear%>','<%=v_courseseq%>')"><img src="/images/admin/button/btn_del.gif"  border="0"></a>
                                </td>
                                <td align="center">
                                    <a href="javascript:addCourse('<%=dbox.getString("d_coursenm")%>','<%=v_gyear%>','<%=v_grseq%>','<%=v_course%>')"><img src="/images/admin/button/btn_add.gif"  border="0"></a>
                                </td>
                            </tr>
                        </table>
                    </td>
<%
                } else {
%>
                    <td class="table_02_1">&nbsp;</td>
<%
                }
%>
                </tr>
<%

            }    // end if
        }       // end for

        if (list.size()==0) {
%>
                <tr><td colspan="14" class="table_02_1">등록된 교육차수가 없습니다.</td></tr>
<%
        }
    }  // end if : if (box.getString("p_action").equals("go")) {
%>
            </table>
            <br/>
        </td>
    </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
