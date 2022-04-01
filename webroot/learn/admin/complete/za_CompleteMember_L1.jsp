<%
//**********************************************************
//  1. 제      목: COMPLETE MEMBER LIST
//  2. 프로그램명: za_CompleteMember_L1.jsp
//  3. 개      요: 수료명단조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 26
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int v_pageno        = box.getInt("p_pageno");

    String  v_grseq     =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_subjseqgr =  "";
    String  v_isonoff   =  "";
    String  v_compnm    =  "";
    String  v_companynm =  "";
    String  v_jikwinm   =  "";
    String  v_jikupnm   =  "";
    String  v_userid    =  "";
    String  v_cono      =  "";
    String  v_name      =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_isgraduated= "";
    String  v_email     =  "";
    String  v_ismailing =  "";
    String  v_place     =  "";
    String  v_isnewcourse= "";
    String  v_isonoff_value="";
	String	v_membergubun = "";

	String v_tmp_subj	= "";
	String v_iscourseYn	= "";
	int v_dispnum	= 0;

    int     v_tstep     =  0;
    int     v_avtstep   =  0;
    int     v_mtest     =  0;
    int     v_ftest     =  0;
    int     v_htest     =  0;
    int     v_report    =  0;
    int     v_act       =  0;
    int     v_etc1      =  0;
    int     v_etc2      =  0;
    int     v_score     =  0;
    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     v_subjcnt	=  0;
    int     i           =  0;
	int		l			= 0;
	int		v_usercnt	= 0;

    ArrayList list      = null;
    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //교육차수
    String  ss_upperclass  = box.getString("s_upperclass");     //과정분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정분류
    String  ss_lowerclass  = box.getString("s_lowerclass");     //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    String  ss_company   = box.getString("s_company");          //회사
    String  ss_selgubun  = box.getString("s_selgubun");
    String  ss_seltext   = box.getString("s_seltext");
    String  ss_seldept   = box.getString("s_seldept");

    String  ss_edustart  = box.getString("s_start");            //교육시작일
    String  ss_eduend    = box.getString("s_end");              //교육종료일

    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";
    //DEFINED in relation to select END

    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("CompleteMemberList");
    }

    String  v_orderColumn  = box.getStringDefault("p_orderColumn", "");           //정렬할 컬럼명
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelection(p_action) {
        if (p_action=="go"){
        	if (chkParam() == false) {
                return;
            }
            top.ftop.setPam();
        }
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.complete.CompleteStatusAdminServlet';
        document.form1.p_process.value = 'CompleteMemberList';
        document.form1.submit();
    }

    function whenSelection2(p_action, p_type) {
        if (p_action=="go"){
            top.ftop.setPam();
        }
        if (p_type == 'selgubun')
        document.form1.s_seltext.value = 'ALL';
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.complete.CompleteStatusAdminServlet';
        document.form1.p_process.value = 'CompleteMemberList';
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

    // 엑셀보기
    function whenExcel() {
        if(document.form1.s_action.value != "go"){
            alert('조회를 먼저 하셔야 합니다');
            return;
      }
      //엑셀출력시 모든 레코드가 나오도록 하기 위해 사이즈, 넘버 임시저장
      var temp_pagesize = document.form1.p_pagesize.value; 
      var temp_pageno   = document.form1.p_pageno.value; 
      
      //엑셀출력시 모든 레코드가 나오도록 하기 위해 사이즈, 넘버변경
      document.form1.p_pagesize.value = 60000;
      document.form1.p_pageno.value = 1;

      document.form1.target = "_self";
      document.form1.action='/servlet/controller.complete.CompleteStatusAdminServlet';
      document.form1.p_process.value = 'CompleteMemberExcel';
      document.form1.p_orderColumn.value= "";
      document.form1.submit();

      //화면출력시 원래 레코드가 나오도록 하기 위해 사이즈, 넘버 원래대로 수정
      document.form1.p_pagesize.value = temp_pagesize;
      document.form1.p_pageno.value = temp_pageno;

    }

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

    function whenFormMail() {
      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }
      document.form1.target = "_self";
      document.form1.action='/servlet/controller.complete.CompleteStatusAdminServlet';
      document.form1.p_process.value = 'SendFormMail';
      document.form1.submit();
    }

    function whenFreeMail() {
      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }
      window.self.name = "ProposeMemberList";
      open_window("openFreeMail","","100","100","640","550");
      document.form1.target = "openFreeMail";
      document.form1.action='/servlet/controller.complete.CompleteStatusAdminServlet';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
    }

    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.complete.CompleteStatusAdminServlet';
         document.form1.p_process.value = "CompleteMemberList";
         document.form1.submit();
    }
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.complete.CompleteStatusAdminServlet';
         document.form1.p_process.value = "CompleteMemberList";
         document.form1.submit();
    }
    function whenMemberInfo(userid) {
        window.self.name = "ProposeMemberList";
        open_window("openMember","","100","100","600","260");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        document.form1.target = window.self.name;
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
-->
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="10">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="p_rprocess" value="CompleteMemberList">
    <input type="hidden" name="p_pagesize"  value="10">

    <input type="hidden" name="p_orderColumn"  value="<%=v_orderColumn%>"><!--검색시-->
    <input type="hidden" name="p_orderType"    value="<%=v_orderType%>"><!--검색시-->

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="left" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="1000" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/course/co_title13.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

<!----------------- form 시작 ----------------->
      <table  width="1000" border=0 cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="left">

            <table border=0 cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="left">
                  <!------------------- 조건검색 시작 ------------------------->
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td colspan="2" align="left" valign="middle" rowspan='2'>
                        <!------------------- 조건검색 시작 ------------------------->
					<script type="text/javascript">
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
					</script>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

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
                                <td class="_tdT">대분류</td>
                                <td class="_tdS">
                                    <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                </td>
                                <td class="_tdT">중분류</td>
                                <td class="_tdS">
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
                                <td class="_tdT">소분류</td>
                                <td class="_tdS" colspan="2">
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
                                <td class="_tdT">과정검색</td>
                                <td class="_tdS">
                                    <input type="text" id="oSubjSearchKey" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode=='13') fnSubjSearchByName();" value="<%=s_subjsearchkey%>">
                                    <a href="javascript:fnSubjSearchByName()" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>                                                        </td>
                                <td class="_tdT">과정</td>
                                <td colspan="4" class="_tdS">
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
                            	<td class="_tdT">과정차수</td>
                               	<td colspan = "3" class="_tdS">
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
			                    <td align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td>
                                
                            </tr>	
						</table>
                      
                      </td>
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
      <br>

      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right" height="20">&nbsp;</td>
            <td align="right" width="65">
            <%//<!--a href="javascript:whenFormMail()"><img src="/images/admin/button/btn_dogmail.gif" border="0"></a-->%>
            </td>
            <td width=4></td>
            <td align="right" width="65">
            <%//<!--a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a-->%>
            </td>
            <td width=4></td>
            <td align="right" width="65">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
            </td>
            <!--td width=4></td>
            <td align="right" width="65">
            <a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif" border="0"></a>
            </td>
            <td width=4></td>
            <td align="right" width="65">
            <a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif" border="0"></a>
            </td-->
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
        </tr>
      </table>
      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 끝 ----------------->
      <!----------------- 수료명단조회 시작 ----------------->
        <table width='1000' border=0 cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td colspan="19" class="table_top_line"></td>
          </tr>
          <tr>
            <td rowspan="2" class="table_title">NO</td>
            <td rowspan="2" class="table_title" colspan="2"> <a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
            <td rowspan="2" class="table_title"> <a href="javascript:whenOrder('compnm')" class="e">회원분류</a></td>
            <td rowspan="2" class="table_title"> <a href="javascript:whenOrder('userid')" class="e">ID</a></td>
            <td rowspan="2" class="table_title"> <a href="javascript:whenOrder('name')" class="e">성명</a></td>
            <td rowspan="2" class="table_title"> <a href="javascript:whenOrder('subjseqgr')" class="e">차수</a></td>
            <!--td rowspan="2" class="table_title"> <a href="javascript:whenOrder('isonoff')" class="e">구분</a></td-->
            <td rowspan="2" class="table_title"> <a href="javascript:whenOrder('edustart')" class="e">교육기간</a></td>
            <td colspan="7" class="table_title">점 수</td>
            <td rowspan="2" class="table_title"><a href="javascript:whenOrder('score')" class="e">취득<br>점수</a></td>
            <td rowspan="2" class="table_title"><a href="javascript:whenOrder('isgraduated')" class="e">수료<br>여부</a></td>
          </tr>
          <tr>
            <td class="table_title"><a href="javascript:whenOrder('tstep')" class="e">진도율<br>(점수)</a></td>
            <td class="table_title"><a href="javascript:whenOrder('mtest')" class="e">중간<br>평가</a></td>
            <td class="table_title"><a href="javascript:whenOrder('ftest')" class="e">최종<br>평가</a></td>
            <td class="table_title"><a href="javascript:whenOrder('htest')" class="e">형성<br>평가</a></td>
            <td class="table_title"><a href="javascript:whenOrder('report')" class="e">리포트</a></td>
            <td class="table_title"><a href="javascript:whenOrder('etc1')" class="e">참여도</a></td>
            <td class="table_title"><a href="javascript:whenOrder('etc2')" class="e">기타</a></td>
          </tr>
          <%
            if (ss_action.equals("go")) {    //go button 선택시만 list 출력
                v_total = list.size();
                for(i = 0; i < v_total; i++) {

					DataBox dbox = (DataBox)list.get(i);

					v_grseq         = dbox.getString("d_grseq");
                    v_course        = dbox.getString("d_course");
                    v_cyear         = dbox.getString("d_cyear");
                    v_courseseq     = dbox.getString("d_courseseq");
                    v_coursenm      = dbox.getString("d_coursenm");
                    v_subj          = dbox.getString("d_subj");
                    v_year          = dbox.getString("d_year");
                    v_subjnm        = dbox.getString("d_subjnm");
                    v_subjseq       = dbox.getString("d_subjseq");
                    v_subjseqgr     = dbox.getString("d_subjseqgr");
                    v_isonoff       = dbox.getString("d_isonoff");
                    v_compnm        = dbox.getString("d_compnm");
                    v_companynm     = dbox.getString("d_companynm");
                    v_jikwinm       = dbox.getString("d_jikwinm");
                    v_jikupnm       = dbox.getString("d_jikupnm");
                    v_userid        = dbox.getString("d_userid");
                    v_cono          = dbox.getString("d_cono");
                    v_name          = dbox.getString("d_name");
                    v_edustart      = dbox.getString("d_edustart");
                    v_eduend        = dbox.getString("d_eduend");
                    v_isgraduated   = dbox.getString("d_isgraduated");
                    v_email         = dbox.getString("d_email");
                    v_ismailing     = dbox.getString("d_ismailing");
                    v_place         = dbox.getString("d_place");
                    v_isnewcourse   = dbox.getString("d_isnewcourse");
					v_membergubun	= dbox.getString("d_membergubun");
					v_iscourseYn	= dbox.getString("d_isbelongcourse");
					v_dispnum		= dbox.getInt("d_dispnum");

                    v_tstep         = dbox.getInt("d_tstep");
                    v_avtstep       = dbox.getInt("d_avtstep");
                    v_mtest         = dbox.getInt("d_mtest");
                    v_ftest         = dbox.getInt("d_ftest");
                    v_htest         = dbox.getInt("d_htest");
                    v_report        = dbox.getInt("d_report");
                    v_act           = dbox.getInt("d_act");
                    v_etc1          = dbox.getInt("d_etc1");
                    v_etc2          = dbox.getInt("d_etc2");
                    v_score         = dbox.getInt("d_score");
                    v_subjcnt       = dbox.getInt("d_subjcnt");
                    v_totalpage     = dbox.getInt("d_totalpage");
                    v_rowcount      = dbox.getInt("d_rowcount");

                    v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                    v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

                    //if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
                    //else                      {     v_isonoff_value="집합";     }
                    if(v_place == null) v_place = "";

					if(v_membergubun.equals("C"))
					{
						v_membergubun = "기업";
					}
					else if(v_membergubun.equals("U"))
					{
						v_membergubun = "대학";
					}
					else
					{
						v_membergubun = "개인";
					}
               %>
          <tr>
            <td class="table_01"><%= v_dispnum %></td>
<% if(v_iscourseYn.equals("Y"))
	{
		if(l == v_subjcnt) v_tmp_subj = "";

		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			l = 1;
			v_usercnt++;
			%>
            <!-- 코스인 경우 -->
            <td class="table_02_2" rowspan="<%=v_subjcnt%>"><%=v_coursenm%></td>
            <td class="table_02_2" ><%=v_subjnm %></td>
<%
			v_tmp_subj = v_course;
		}else{
			l++;
		%>
            <td class="table_02_2" ><%=v_subjnm %></td>
<%		}
	} else {
			v_usercnt++;%>
            <!-- 과정인 경우 -->
            <td class="table_02_2" colspan="2"><%=v_subjnm %></td>
<% } %>
            <td class="table_02_2"><%=v_membergubun%></td>
            <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%= v_userid %></a></td>
            <td class="table_02_1"><%= v_name %></td>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <!--td class="table_02_1"><%//=v_isonoff_value%></td-->
            <td class="table_02_1"><%=v_edustart %>~<br> <%= v_eduend %></td>
            <td class="table_02_1"><%=v_tstep%>(<%=v_avtstep%>)</td>
            <td class="table_02_1"><%=v_mtest%></td><!--중간-->
            <td class="table_02_1"><%=v_ftest%></td><!--최종-->
            <td class="table_02_1"><%=v_htest%></td><!--형성-->
            <td class="table_02_1"><%=v_report%></td>
            <td class="table_02_1"><%=v_etc1%></td>
            <td class="table_02_1"><%=v_etc2%></td>
            <td class="table_02_1"><%=v_score%></td>
            <td class="table_02_1"><%=v_isgraduated%></td>
          </tr>
          <%
               }
            }
         if(i == 0 && ss_action.equals("go")){ %>
          <tr>
            <td align="center" bgcolor="#FFFFFF" height="50" colspan="19">수료명단이 없습니다.</td>
          </tr>
        </table>
        <% } else{ %>   </table> <% } %>


       <%  if(i > 0){ %>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
          </tr>
        </table>
        <br>
        <!--table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right">◈<b> TOTAL : <%=v_usercnt%>명</b></td>
          </tr>
        </table-->
        <% } %>
      <!----------------- 수료명단 조회 끝 ----------------->
     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
