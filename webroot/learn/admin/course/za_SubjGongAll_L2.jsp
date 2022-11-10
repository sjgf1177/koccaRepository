<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGongAll_L2.jsp
//  3. 개      요: 과정차수별공지사항전체 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String  v_grcode    = "";
    String  v_grcodenm  = "";
    String  v_gyear     = "";
    String  v_grseq     = "";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    String  v_edustart  = "";
    String  v_eduend    = "";
    String  v_isgoyong  = "";

	String  v_tmp_subj = "";
	String  v_iscourseYn = "";

    String  v_subjtypenm  = "";
    String  v_isnewcourse = "";

    int     v_rowspan     =  0;
	int		v_subjcnt	= 0;
	int     i = 0;
	int     l = 0;

    //DEFINED in relation to select START
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
    String  ss_isclosed     = box.getString("s_isclosed");        //진행여부
    String  ss_notpageing     = box.getString("s_notpageing");        //페이징처리 안함
    String  ss_area=box.getString("s_area");					  // 교육구분 [게임/방송/문콘]
    String ss_biyong=box.getString("s_biyong");					  // 유/무료

    //페이징
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    
//    String  v_ongo      = "whenSelection('selectAll')";
    //DEFINED in relation to select END

    int    v_codecnt   = StringManager.toInt(box.getString("p_typescnt"));       // 공지구분갯수

    String  ss_action    = box.getString("s_action");

    ArrayList list1      = null;
    ArrayList list2      = null;
    //if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list1    = (ArrayList)request.getAttribute("selectList1");         // 공지구분리스트
        list2    = (ArrayList)request.getAttribute("selectList2");         // 과정차수별 리스트
    //}

    String   v_codenm       = "";  // 공지구분명
    String   v_typescnt_org = "";
    String[] v_typescnt     = new String[v_codecnt];

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelection(p_action)
	{
		document.form1.p_process.value = 'selectPre';

        if (chkParam() == false) {
            return;
        }
        top.ftop.setPam();

        document.form1.p_action.value = p_action;
        document.form1.p_pageno.value = "1";
		document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
		document.form1.submit();
    }

    //선택전체공지수정
    function allEdit()
    {
        if(document.form1.all['p_checks'] == '[object]') {
            var v_chkcnt = 0;

            if (document.form1.p_checks.checked) {
                v_chkcnt=1;
            }

            if (document.form1.p_checks.length > 0) {
                for (i=0; i<document.form1.p_checks.length; i++) {
                    if (document.form1.p_checks[i].checked == true)
                        v_chkcnt+=1;
                }
            }
              if (v_chkcnt==0) {
                alert('수정할 과정이 없습니다.');
                return;
              }

        } else {
            alert('수정할 과정이 없습니다.');
            return;
        }

        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "allEditPageAll";
        document.form1.submit();
    }

    // 선택공지등록
    function insert() {
        if(document.form1.all['p_checks'] == '[object]') {
            var v_chkcnt = 0;

            if (document.form1.p_checks.checked) {
                v_chkcnt=1;
            }

            if (document.form1.p_checks.length > 0) {
                for (i=0; i<document.form1.p_checks.length; i++) {
                    if (document.form1.p_checks[i].checked == true)
                        v_chkcnt+=1;
                }
            }
                if (v_chkcnt==0) {
                    alert('공지할 과정이 없습니다.');
                    return;
                }

        } else {
            alert('공지할 과정이 없습니다.');
            return;
        }

        //if (document.form1.p_process.value == '') {
        //  alert("공지를 등록할 과정을 검색해주세요");
        //  return;
        //}

        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "insertPageAll";
        document.form1.submit();
    }

    // 선택공지삭제
    function deleteAll() {
        if(document.form1.all['p_checks'] == '[object]') {
            var v_chkcnt = 0;
            if (document.form1.p_checks.checked) {
                v_chkcnt=1;
            }
            if (document.form1.p_checks.length > 0) {
              for (i=0; i<document.form1.p_checks.length; i++) {
                if (document.form1.p_checks[i].checked == true)
                    v_chkcnt+=1;
              }
            }
            if (v_chkcnt==0) {
                alert('삭제할 과정을 선택하세요');
                return;
            }
        } else {
            alert('삭제할 과정을 선택하세요');
            return;
        }

		if(confirm('선택한 과정에 공지사항을 모두 삭제 하시겠습니까?')) {
        	document.form1.action = '/servlet/controller.course.SubjGongAdminServlet';
        	document.form1.p_process.value = 'deletePageAll';
        	document.form1.submit();
        }
    }

    // 선택차수별 공지사항리스트
    function listSub(subj,year,subjseq,subjnm) {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "select";

        document.form1.p_subj.value    = subj;
        //document.form1.s_subjcourse.value    = subj;
        document.form1.p_year.value    = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_hsubjnm.value = subjnm;

        document.form1.submit();
    }

    function whenAllSelect() {
        if(document.form1.all['p_checks'] == '[object]') {
            if (document.form1.p_checks.length > 0) {
                for (i=0; i<document.form1.p_checks.length; i++) {
                    document.form1.p_checks[i].checked = true;
                }
            } else {
                document.form1.p_checks.checked = true;
            }
        }
    }

    function whenAllSelectCancel() {
        if(document.form1.all['p_checks'] == '[object]') {
            if (document.form1.p_checks.length > 0) {
                for (i=0; i<document.form1.p_checks.length; i++) {
                    document.form1.p_checks[i].checked = false;
                }
            } else {
                document.form1.p_checks.checked = false;
            }
        }
    }

    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_orderColumn.value = column;

        whenSelection("go");

    }

	//검색조건 체크
	function chkParam() {
	  if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
	    alert("교육그룹을 선택하세요.");
	    return false;
	  }
	  if (document.form1.s_gyear.value == "" || document.form1.s_gyear.value == '----') {
	      alert("연도를 선택하세요.");
	      return false;
	    }
	  if (document.form1.s_grseq.value == "" || document.form1.s_grseq.value == '----') {
	    alert("교육차수를 선택하세요.");
	    return false;
	  }
	}
  
    //페이지 이동
    function go(index) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = index;
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "selectAll";
        document.form1.submit();
    }
    //페이지 이동
    function goPage(pageNum) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "selectAll";
        document.form1.submit();
    }
    
    function fnChangeGrcode(){
		var param = "type=sqlID&sqlID=selectBox.grYearList&param=" + $('#s_grcode').val();
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
	}

    // select box 설정
    $(function() {
        $("#s_grcode").bind("change", fnChangeGrcode);

        $("#oGyear").bind("change", function(){
            var grcode = $("#s_grcode").val();
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
            var grcode = $("#s_grcode").val();
            var gyear = $("#oGyear").val();
            var subjSearchKey = $("#oSubjSearchKey").val();
            var param;
            subjSearchKey = (subjSearchKey == "") ? "" : subjSearchKey;
            if(subjSearchKey == "")
            	param = "type=sqlID&sqlID=selectBox.subjListAll&param=" + grcode + "," + gyear + "," + $(this).val();
            else 
            	param = "type=sqlID&sqlID=selectBox.subjList&param=" + grcode + "," + gyear + "," + $(this).val() + "," + subjSearchKey;
            
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "post"
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
                var grcode = $("#s_grcode").val();
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
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");


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
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");


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
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");
        
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
        $("#oMiddleclass").append("<option value=\"\">== 전체 ==</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

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
        $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

        if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
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
<form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_subj"    value="">
    <input type="hidden" name="p_year"    value="">
    <input type="hidden" name="p_subjseq" value="">
    <input type="hidden" name="p_hsubjnm" value="">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_action"  value="">

    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->

    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/course/co_title01.gif" ></td>
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

                                    <!------------------- 조건검색 시작 ------------------------->
                                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                                        <colgroup>
                                                    <col style="width:8%" />
                                                    <col style="width:20%" />
                                                    <col style="width:8%" />
                                                    <col style="width:10%" />
                                                    <col style="width:10%" />
                                                    <col style="width:20%" />
                                        </colgroup>
                                        <tr>
                                            <td align="left" valign="middle">
                                                <font color="red">★</font>교육그룹 
                                            </td>
                                            <td>
                                            	<%if("H1".equals(box.getSession("gadmin")) || "H102".equals(box.getSession("gadmin"))){%>
													<kocca_select:select name="s_grcode" onChange="fnChangeGrcode();" sqlNum="course.0001" selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
													<%if(ss_gyear.equals("")){%>
														<script>$(document).ready(function(){setTimeout(fnChangeGrcode, 300);});</script>
													<%}%>
												<%}else{%>
													<kocca:selectBox name="s_grcode" id="s_grcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
												<%}%>
                                            </td>
                                            <td>
                                                <font color="red">★</font>연도 </td>
                                            <td>
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
                                            <td>교육차수</td> <td>
<%
    if ( !ss_grseq.equals("") || !ss_gyear.equals("")) {
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
                                            <td> 대분류 </td>
                                            <td>
                                                <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                            </td>
                                            <td>중분류 </td>
                                            <td>
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
                                            <td>소분류 </td>
                                            <td>
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
                                            <td align="left"><% String s_subjsearchkey = box.getString("s_subjsearchkey");  %>과정검색 </td>
                                            <td>
                                                <input type="text" id="oSubjSearchKey" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode=='13') fnSubjSearchByName();" value="<%=s_subjsearchkey%>">
                                                <a href="javascript:fnSubjSearchByName()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>                                                        </td>
                                            </td>
                                            <td> 과정 </td>
                                            <td colspan='3'>
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
                                        </tr>
                                        <tr>

                                             <td>과정차수 </td>
                                             <td>
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
                                            <td align="left">교육구분
                                                <select name="s_area">
                                                    <option value="T" <% if(ss_area.equals("T")) out.print("selected"); %> >전체</option>
                                                    <option value="G0" <% if(ss_area.equals("G0")) out.print("selected"); %> >게임</option>
                                                    <option value="B0" <% if(ss_area.equals("B0")) out.print("selected"); %> >방송</option>
                                                    <option value="K0" <% if(ss_area.equals("K0")) out.print("selected"); %> >문콘</option>
                                                </select>
                                            </td>
                                            <td >유무료
                                                <select name="s_biyong">
                                                    <option value="T" <% if(ss_biyong.equals("T")) out.print("selected"); %> >전체</option>
                                                    <option value="Z" <% if(ss_biyong.equals("Z")) out.print("selected"); %> >무료</option>
                                                    <option value="P" <% if(ss_biyong.equals("P")) out.print("selected"); %> >유료</option>
                                                </select>
                                            </td>
                                            <td colspan="1">
                                                <input type="checkbox" name="s_notpageing" <% if(!ss_notpageing.equals(""))  out.print("checked"); %> >페이징처리안함                    
                                            </td>
                                            <td align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
                                        </tr>
                                    </table>
                                    <!-------------------- 조건검색 끝 ---------------------------->
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

            <!----------------- 선택전체공지  버튼 시작 ----------------->
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" >
                        <a href="javascript:deleteAll()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;
                        <a href="javascript:insert()"><img src="/images/admin/button/btn_saveall.gif" border="0"></a>&nbsp;
                        <a href="javascript:allEdit()"><img src="/images/admin/button/btn_modifyall.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!----------------- 선택전체공지 버튼 끝 ----------------->

            <!----------------- 공지사항관리 시작 ----------------->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="<%=list1.size() + 7%>" class="table_top_line"></td>
                </tr>
                <tr>
                    <td width="5%" height="50" rowspan="2" class="table_title"><b>NO</b></td>
                    <td width="26%" rowspan="2" class="table_title" colspan="2"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
                    <td width="5%" rowspan="2" class="table_title"><a href="javascript:whenOrder('subjseqgr')" class="e">차수</a></td>
                    <!--td width="6%" rowspan="2" class="table_title"><a href="javascript:whenOrder('isonoff')" class="e">구분</a></td-->
                    <td width="17%" rowspan="2" class="table_title"><a href="javascript:whenOrder('edustart')" class="e">교육기간</a></td>
                    <td height="22" colspan="<%=list1.size()%>" class="table_title"><b>공지구분</b></td>
                    <td width="4%" rowspan="2" class="table_title"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
                </tr>
	    		<tr>
<%
      // 권한 리스트
//if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        for(i = 0; i < list1.size(); i++) {

                CodeData data1  = (CodeData)list1.get(i);
                v_codenm    = data1.getCodenm();
%>
                    <td class="table_title" height="25"><b><%=v_codenm%></b></td>
<%
        }
%>
                </tr>
<%
            for(i = 0; i < list2.size(); i++) {

				DataBox dbox = (DataBox)list2.get(i);

				if( dbox != null) {

					v_grcode    = dbox.getString("d_grcode");
					v_grcodenm  = dbox.getString("d_grcodenm");
					v_gyear     = dbox.getString("d_gyear");
					v_grseq     = dbox.getString("d_grseq");
					v_course    = dbox.getString("d_course");
					v_cyear     = dbox.getString("d_cyear");
					v_courseseq = dbox.getString("d_courseseq");
					v_coursenm  = dbox.getString("d_coursenm");
					v_subj      = dbox.getString("d_subj");
					v_year      = dbox.getString("d_year");
					v_subjseq   = dbox.getString("d_subjseq");
					v_subjseqgr = dbox.getString("d_subjseqgr");
					v_subjnm    = dbox.getString("d_subjnm");
					v_isonoff   = dbox.getString("d_isonoff");
					v_edustart  = dbox.getString("d_edustart");
					v_eduend    = dbox.getString("d_eduend");

					v_iscourseYn	= dbox.getString("d_isbelongcourse");
					v_subjcnt		= dbox.getInt("d_subjcnt");

					//페이징
					v_dispnum       = dbox.getInt("d_dispnum");
	                v_totalpage     = dbox.getInt("d_totalpage");
	                v_rowcount      = dbox.getInt("d_rowcount");

					if(v_isonoff.equals("ON")){
						v_subjtypenm = "사이버";
					} else {
						v_subjtypenm = "집합";
					}
				}
%>
                <tr>
                    <td class="table_01"><%= v_dispnum %></td>

<% 
            if(v_iscourseYn.equals("Y")){

                if(l == v_subjcnt) v_tmp_subj = "";

		        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000")) {
        			 l = 1;
%>
                    <td class="table_02_2" rowspan="<%=v_subjcnt%>" ><%= v_coursenm %></td>
		        	<td class="table_02_2" ><a href="javascript:listSub('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%=v_subjnm%>')"  class="b"><%=v_subjnm%></a></td>
<%
		        }else{
        			l++;
%>
        			<td class="table_02_2" ><a href="javascript:listSub('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%=v_subjnm%>')"  class="b"><%=v_subjnm%></a></td>
<%		
                }
        		v_tmp_subj = v_course;
	        } else { 
%>

                    <td class="table_02_2" colspan="2"><a href="javascript:listSub('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%=v_subjnm%>')"  class="b"><%=v_subjnm%></a></td>
<% 
    } 
%>

                    <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
                    <!--td class="table_02_1"><%//=v_subjtypenm%></td-->
                    <td class="table_02_1"><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>

<%
           // 권한 리스트
           if(v_codecnt > 0) {
                //v_typescnt_org  = data2.getTypescnt();
				v_typescnt_org	= dbox.getString("d_typescnt");


               StringTokenizer st = new StringTokenizer(v_typescnt_org,"/");
               int j = 0;

               // 과정차수별에 대한 각각의 구분별 공지 갯수 배열에 저장
               while (st.hasMoreElements()) {
                   v_typescnt[j] = StringManager.chkNull((String)st.nextToken());
				   System.out.println(v_typescnt[j]);
                   j++;
               }

               // 해당 권한 체크박스에 세팅
               for (int k=0; k < v_codecnt ; k++) {
                   v_typescnt[k] = StringManager.chkNull(v_typescnt[k]);
                   if (v_typescnt[k].equals("")) v_typescnt[k] = "0";
%>
                    <td class="table_02_1"><%=v_typescnt[k]%></td>
<%
                }
            }
%>                  <td class="table_02_1">
                        <input name="p_checks" type="checkbox" value="<%=v_subj + "/" + v_gyear + "/" + v_subjseq%>">
                    </td>
                </tr>
          <%
        }
//}//go
//if (ss_action.equals("go")) {    //go button 선택시만 list 출력
          if(v_process.equals("selectAll") && i == 0){ %>
                <tr>
                    <td class="table_02_1" colspan="<%= (v_codecnt + 10) %>">등록된 내용이 없습니다</td>
                </tr>
<%  
          }
//} //go
%>
            </table>
            <!----------------- 공지사항관리 끝 ----------------->
            <br>
            <!----------------- total 시작 ----------------->
            <br>
<% 
        if(ss_notpageing.equals("")) { 
%>
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td align="right" valign="absmiddle">
                    <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
                </td>
            </tr>
            </table>
<%
        }
%>
            <!----------------- total 끝 ----------------->

        </td>
    </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
