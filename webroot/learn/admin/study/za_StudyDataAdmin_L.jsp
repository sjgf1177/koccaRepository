<%
//**********************************************************
//  1. 제      목: STUDY DATABOARD ADMIN LIST
//  2. 프로그램명: za_StudyDataAdmin_L.jsp
//  3. 개      요: 과정자료실 관리자 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String  v_process   = box.getString("p_process");
	String  v_orderColumn   = box.getString("p_orderColumn");
	
    String  v_subj      = "";
    //String  v_year      = "";
    //String  v_subjseq   = "";
	//String  v_subjseqgr   = "";
    //String  v_isclosed  = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    //String v_isnewcourse ="";
    //String  v_course    = "";
    //String  v_cyear     = "";
    //String  v_courseseq = "";          
    //String  v_coursenm  = "";   
    String  v_onoff_value  = "";  
    int     v_cnt        =  0;     
    int     v_tabseq     =  0;    
    //int     v_rowspan    =  0;    
    int     i            = 0;
    ArrayList list1      = null;
    
    //페이징
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    
    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육주관
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getStringDefault("s_grseq","");            //교육차수
    String  ss_grseqnm   = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";
	//DEFINED in relation to select END
        
    if(ss_action.equals("go")){    //go button 선택시만 list 출력
        list1 = (ArrayList)request.getAttribute("selectAdminList");
    }    
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelection(ss_action) {
        if (document.form1.s_grcode.value == '' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }
        if (document.form1.s_gyear.value == '' || document.form1.s_gyear.value == '----') {
            alert("연도를 선택하세요.");
            return ;
		}
        //if (ss_action=="go")	{
        //      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
        //          alert("교육주관을 선택하세요.");
        //          return ;
        //      }
        //      top.ftop.setPam();
        //}
        document.form1.s_action.value = ss_action;
        document.form1.p_pageno.value = "1";
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";      
        document.form1.p_process.value = "adminList";
        document.form1.submit();
    }
    
    function whenDataSelect(subjnm,tabseq) {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_tabseq.value = tabseq;
        document.form1.p_process.value = "list";
        document.form1.submit();
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

	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
		document.form1.p_process.value = "adminList";
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
		document.form1.p_process.value = "adminList";
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
    //		alert(document.form1.s_grcode.value+ " , " + document.form1.s_upperclass.value+ " , " + document.form1.s_middleclass.value+ " , " + document.form1.s_lowerclass.value+ " , " + document.form1.s_subjseq.value+ " , " +'' );
        }
        startup++;
    }
-->    	
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->    
    <input type="hidden" name="p_subj">
    <input type="hidden" name="p_subjnm">
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subjseq">
    <input type="hidden" name="p_tabseq">
    
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_action"  value="">  

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top"> 

            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr> 
                    <td><img src="/images/admin/course/co_title09.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>      
            <!----------------- title 끝 ----------------->
            <br>
            <!----------------- form 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="1">
                <tr> 
                    <td bgcolor="#C6C6C6" align="center"> 
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table_bg">
                            <tr> 
                                <td height="7"></td>
                            </tr>
                            <tr> 
                                <td align="center" valign="middle"> 
                                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                                        <colgroup>
                                                    <col style="width:8%" />
                                                    <col style="width:20%" />
                                                    <col style="width:8%" />
                                                    <col style="width:10%" />
                                                    <col style="width:8%" />
                                                    <col style="width:20%" />
                                        </colgroup>
                                        <tr> 
                                            <td> 
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
                                                <font color="red">★</font>연도
                                            </td>
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
                                            <td>교육차수
                                            </td>
                                            <td>
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
                                            <td width="150px"> 대분류
                                            </td>
                                            <td>
                                                <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                            </td>
                                            <td>중분류 
                                            </td>
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
                                            <td>소분류 
                                            </td>
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
                                            <td align="left"><%     String s_subjsearchkey = box.getString("s_subjsearchkey");  %>
                                                과정검색
                                            </td>
                                            <td>
                                                <input type="text" id="oSubjSearchKey" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode=='13') fnSubjSearchByName();" value="<%=s_subjsearchkey%>">
                                                <a href="javascript:fnSubjSearchByName()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>                                                        </td>

                                            </td>
                                            <td>과정
                                            </td>
                                            <td colspan="3" >
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

                                            <td>
                                                과정차수
                                            </td>
                                            <td colspan="4" >
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
                                            <td align="right">
                                                <a href="javascript:<%=v_ongo%>"><img src="/images/admin/button/b_go.gif" border="0"></a>
                                            </td>
                                        </tr>
                                    </table>
                                    <!-------------------- 조건검색 끝 ---------------------------->
                                </td>
                            </tr>
                            <tr> 
                                <td height="7"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <!----------------- form 끝 ----------------->
            <br>
            <br>
            <!----------------- 과정/코스별 자료실 리스트 시작 ----------------->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr> 
                    <td colspan="6" class="table_top_line"></td>
                </tr>
                <tr> 
                    <td width="5%" height="25" class="table_title"><b>NO</b></td>
                    <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
                    <!--td class="table_title" width="9%"><b>차수</b></td-->
                    <!--td class="table_title"><a href="javascript:whenOrder('isonoff')" class="e">구분</a></td-->
                    <td class="table_title"><a href="javascript:whenOrder('cnt')" class="e">자료수</a></td>
                    <td class="table_title" width="15%"><b>자료실 list 조회</b></td>
                </tr>
          <% 
            if(ss_action.equals("go")){    //go button 선택시만 list 출력
                for(i = 0; i < list1.size(); i++) {
                    /*
                	ProjectData data= (ProjectData)list1.get(i);
                    v_subj          = data.getSubj();     
                    //v_course        = data.getCourse();
                    //v_cyear         = data.getCyear();
                    //v_courseseq     = data.getCourseseq();
                    //v_coursenm      = data.getCoursenm();                                        
                    //v_year          = data.getYear();     
                    //v_subjseq       = data.getSubjseq(); 
					//v_subjseqgr     = data.getSubjseqgr();  
                    //v_isclosed      = data.getIsclosed(); 
                    v_subjnm        = data.getSubjnm();   
                    //v_isonoff       = data.getIsonoff();  
                    //v_rowspan       = data.getRowspan();
                    //v_isnewcourse   = data.getIsnewcourse();
                    v_cnt           = data.getCnt();
                    v_tabseq        = data.getTabseq();
                    //if(v_isonoff.equals("ON"))      {  v_onoff_value  = "사이버"; }
                    //else                            {  v_onoff_value  = "집합";   }
                    */
                    
					DataBox dbox = (DataBox)list1.get(i);
					if( dbox != null)
					{
	                    v_subj          = dbox.getString("d_subj");   
	                    v_subjnm        = dbox.getString("d_subjnm");   
	                    v_cnt           = dbox.getInt("d_cnt");
	                    v_tabseq        = dbox.getInt("d_tabseq");

						//페이징
						v_dispnum       = dbox.getInt("d_dispnum");
		                v_totalpage     = dbox.getInt("d_totalpage");
		                v_rowcount      = dbox.getInt("d_rowcount");
					}
               %>
                <tr> 
                    <td class="table_01"><%= v_dispnum %></td>
                    <td class="table_02_2"><%=v_subjnm%></td>
                    <!--td class="table_02_1"><%//=StringManager.cutZero(v_subjseqgr)%></td-->
                    <!--td class="table_02_1"><%//=v_onoff_value%></td-->
                    <td class="table_02_1"><%=v_cnt%></td>
                    <td class="table_03_1"> <a href="javascript:whenDataSelect('<%=v_subjnm%>','<%=v_tabseq%>')"><img src="/images/admin/button/b_datalist.gif" border="0"></a> 
                    </td>
                </tr>
          <%             
              } 
          }  
          if(ss_action.equals("go") && i == 0){ %>
                <tr> 
                    <td class="table_02_1" colspan="6">등록된 내용이 없습니다</td>
                </tr>
<%  
          } 
%>
            </table>
            <!----------------- 과정/코스별 자료실 리스트 끝 ----------------->
            <br>
            <!----------------- page navi 시작 ----------------->
            <br>
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" valign="absmiddle">
                        <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
                    </td>
                </tr>
            </table>
            <!----------------- page navi 끝 ----------------->
        </td>
    </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
