<%
//**********************************************************
//  1. 제      목: PROJECT QUESTION ADMIN LIST
//  2. 프로그램명: za_ProjectQuestionsAdmin_L.jsp
//  3. 개      요: 리포트 출제 관리
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
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr   = "";
    String  v_isclosed  = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    String  v_isnewcourse ="";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_closed_value = "";
    String  v_onoff_value  = "";
    String v_edustart   =   "";
    String v_eduend     =   "";

    String  v_tmp_subj  = "";
    String  v_iscourseYn = "";

    int     v_projseqcnt= 0;
    int     v_ordseqcnt = 0;
    int     v_rowspan   =  0;
    int     i           = 0;
    int     l           = 0;
    int     v_subjcnt   = 0;
    ArrayList list1     = null;

    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm   = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";

    String  ss_area=box.getString("s_area");					  // 교육구분 [게임/방송/문콘]
    String ss_biyong=box.getString("s_biyong");					  // 유/무료
    //DEFINED in relation to select END

    if(ss_action.equals("go")){    //go button 선택시만 list 출력
        list1 = (ArrayList)request.getAttribute("projectQuestionsAList");
    }

    //페이징
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

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
       if (ss_action=="go") {
    	   if (chkParam() == false) {
               return;
           }
           top.ftop.setPam();
      }
      document.form1.s_action.value = ss_action;
      document.form1.p_pageno.value = "1";
      document.form1.action='/servlet/controller.study.ProjectAdminServlet';
      document.form1.p_process.value = 'ProjectQuestionsAdmin';
      document.form1.submit();
    }

    // 리포트 리스트 보기
    function projectlist_select(subj,subjnm,year,subjseq,subjseqgr,eduend) {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectQuestionsList';
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_subjseqgr.value = subjseqgr;
        document.form1.p_eduend.value = eduend;
        document.form1.submit();
    }

    function whenOrder(column) {
	    if (document.form1.p_orderType.value == " asc") {
	        document.form1.p_orderType.value = " desc";
	    } else {
	        document.form1.p_orderType.value = " asc";
	    }
	
	    document.form1.s_action.value = "go";
	
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
		document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
		document.form1.p_process.value = "ProjectQuestionsAdmin";
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
		document.form1.p_process.value = "ProjectQuestionsAdmin";
		document.form1.submit();
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
//-->
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
    <input type="hidden" name="p_subjseqgr">
    <input type="hidden" name="p_eduend">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_action"  value="">  

    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/course/co_title07.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        <br>
            <!-- 교육그룹, 년도 시작 -->
        <table class="form_table_out" cellspacing="0" cellpadding="1">
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
                                                <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
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
      <!-- 교육그룹, 년도 끝 -->
        <br>
        <br>

        <!----------------- Activity 제출관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" class="table_title""><b>NO</b></td>
            <!--td class="table_title" width='7%'><a href="javascript:whenOrder('isonoff')" class="e">구분</a></td-->
            <td class="table_title" colspan="2"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
            <td class="table_title" width="5%"><a href="javascript:whenOrder('subjseqgr')" class="e">차수</a></td>
            <td class="table_title" width="15%"><a href="javascript:whenOrder('edustart')" class="e">교육기간</a></td>
            <td class="table_title" width="12%"><b>리포트 list 조회</b></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrder('projseqcnt')" class="e">리포트<br>
              셋트 갯수</a></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrder('ordseqcnt')" class="e">리포트<br>
              문제 갯수</a></td>
            <td class="table_title" width="10%"><a href="javascript:whenOrder('isclosed')" class="e">교육상태</a></td>
          </tr>
          <%
            if(ss_action.equals("go")){    //go button 선택시만 list 출력
                for(i = 0; i < list1.size(); i++) {

                    DataBox dbox = (DataBox)list1.get(i);

                    if( dbox != null)
                    {
                        v_subj          = dbox.getString("d_subj");
                        v_course        = dbox.getString("d_course");
                        v_cyear         = dbox.getString("d_cyear");
                        v_courseseq     = dbox.getString("d_courseseq");
                        v_coursenm      = dbox.getString("d_coursenm");
                        v_year          = dbox.getString("d_year");
                        v_subjseq       = dbox.getString("d_subjseq");
                        v_subjseqgr     = dbox.getString("d_subjseqgr");
                        v_isclosed      = dbox.getString("d_isclosed");
                        v_subjnm        = dbox.getString("d_subjnm");
                        v_isonoff       = dbox.getString("d_isonoff");
                        v_edustart      = dbox.getString("d_edustart");
                        v_eduend        = dbox.getString("d_eduend");
                        v_iscourseYn    = dbox.getString("d_isbelongcourse");

                        v_projseqcnt    = dbox.getInt("d_projseqcnt");
                        v_ordseqcnt     = dbox.getInt("d_ordseqcnt");
                        v_subjcnt       = dbox.getInt("d_subjcnt");

                        //페이징
						v_dispnum       = dbox.getInt("d_dispnum");
		                v_totalpage     = dbox.getInt("d_totalpage");
		                v_rowcount      = dbox.getInt("d_rowcount");

                        //날짜 처리
                        if(v_edustart.equals("")){v_edustart = "미정";}
                        else{v_edustart     = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");}

                        if(v_eduend.equals("")){v_eduend = "미정";}
                        else{v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");}

                        if(v_isclosed.equals("Y"))      {  v_closed_value = "완료";   }
                        else                            {  v_closed_value = "진행중"; }
                        //if(v_isonoff.equals("ON"))      {  v_onoff_value  = "사이버"; }
                        //else                            {  v_onoff_value  = "집합";   }

                    }
               %>
          <tr>
            <td class="table_01"><%= v_dispnum %></td>
            <!--td class="table_02_1"><%//=v_onoff_value%></td-->
<% if(v_iscourseYn.equals("Y"))
    {
        if(l == v_subjcnt) v_tmp_subj = "";
        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
        {
             l = 1;
%>
            <td class="table_02_2" rowspan="<%=v_subjcnt%>" ><%= v_coursenm %></td>
            <td class="table_02_2" ><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"  class="b"><%=v_subjnm%></a></td>
<%
        }else{
             l++;
%>
            <td class="table_02_2" ><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"  class="b"><%=v_subjnm%></a></td>
<%      }
        v_tmp_subj = v_course;
    } else { 
%>
            <td class="table_02_2" colspan="2"><a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"  class="b"><%=v_subjnm%></a></td>
<% } %>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1">
            <%=v_edustart %>~<%=v_eduend %>
            </td>
            <td class="table_03_1"> <a href="javascript:projectlist_select('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_subjseqgr%>', '<%=v_eduend%>')"><img src="/images/admin/button/b_reportlist.gif" border="0"></a>
            </td>
            <td class="table_02_1"><%=v_projseqcnt%></td>
            <td class="table_02_1"><%=v_ordseqcnt%></td>
            <td class="table_02_1"><%=v_closed_value%></td>
          </tr>
<%
              }
          }
          if(ss_action.equals("go") && i == 0){ 
%>
          <tr>
            <td class="table_02_1" colspan="9">등록된 내용이 없습니다</td>
          </tr>
<%        } %>
        </table>
        <!----------------- Activity 제출관리 끝 ----------------->
        <br>
        <!----------------- 페이징 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
            </td>
          </tr>
        </table>
        <!----------------- 페이징 끝 ----------------->
        
     </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
