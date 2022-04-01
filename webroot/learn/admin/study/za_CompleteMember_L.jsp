<%
//**********************************************************
//  1. 제      목: COMPLETE MEMBER LIST
//  2. 프로그램명: za_CompleteMember_L.jsp
//  3. 개      요: 학습완료자 명단조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    String  ems_url    = conf.getProperty("ems.url.value");

    //2009.10.27 페이지수 추가
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

    //2009.10.27 페이지당 레코드수 추가
    if (v_pagesize == 0)  v_pagesize = 10;

    String  v_grseq     =  "";
    String  v_grseqnm   =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_userid    =  "";
    String  v_name      =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_email     =  "";
    String  v_isnewcourse= "";
    String  v_tstep     =  "";
    String  v_mtest     =  "";
    String  v_ftest     =  "";
    String  v_htest     =  "";
    String  v_report    =  "";
    String  v_etc1      =  "";
    String  v_etc2      =  "";
    String  v_avtstep   =  "";
    String  v_avmtest   =  "";
    String  v_avftest   =  "";
    String  v_avhtest   =  "";
    String  v_avreport  =  "";
    String  v_avetc1    =  "";
    String  v_avetc2    =  "";
    String  v_score     =  "";
    String  v_isgraduated=  "";
	String  v_membergubunnm= "";
    String  v_isonoff   =  "";
    String  v_isonoff_value="";
    String  v_subjseqgr =  "";
    String v_isbelongcourse = "";	// 전문가(코스)과정여부 Y/N
    int v_subjcnt = 0;			// 전문가(코스)과정에 속한 과정수

    int     v_totalpage		=  0;
    int     v_rowcount		=  1;
    int     v_totalrowcount	=  0;
    int     v_rowspan		=  0;
    int     v_total			=  0;
    int		v_dispnum		=  0;
    int     i				=  0;

    String  v_orderColumn  = box.getString("p_orderColumn");           //정렬할 컬럼명
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

    ArrayList list      = null;
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
    String  ss_company   = box.getString("s_company");          //회사
    String  ss_selgubun  = box.getString("s_selgubun");
    String  ss_seltext   = box.getString("s_seltext");
    String  ss_seldept   = box.getString("s_seldept");

    String  ss_edustart  = box.getString("s_start");            //교육시작일
    String  ss_eduend    = box.getString("s_end");              //교육종료일

    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";
   if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("CompleteMemberList");
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
    // 검색
    function whenSelection(p_action) {
        if (p_action=="go"){
        	top.ftop.setPam();
            if (chkParam() == false) {
                return;
            }
        }
        document.form1.s_action.value = p_action;
        document.form1.p_pageno.value = 1;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.p_process.value = 'CompleteMemberList';
        document.form1.submit();
    }

    //검색
    function whenSelection2(p_action, p_type) {
        if (p_action=="go"){
            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
                alert("교육그룹을 선택하세요.");
                return ;
            }

            if (document.form1.s_grseq.value == 'ALL' ) {
                alert("교육차수를 선택하세요.");
                return ;
            }

            top.ftop.setPam();
        }
        if (p_type == 'selgubun')
        document.form1.s_seltext.value = 'ALL';
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.p_process.value = 'CompleteMemberList';
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

    //엑셀 출력
    function whenExcel() {
        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
      }
        //window.self.name = "CompleteMemberList";
        //open_window("openExcel","","10","10","900","660","no","no","no","no","yes");

        //2009.10.27 엑셀출력시 모든 레코드가 나오도록 하기 위해 사이즈, 넘버 임시저장
        var temp_pagesize = document.form1.p_pagesize.value; 
        var temp_pageno   = document.form1.p_pageno.value; 
        
        document.form1.target = "_self";

        //2009.10.27 엑셀출력시 모든 레코드가 나오도록 하기 위해 사이즈, 넘버변경
        document.form1.p_pagesize.value = 10000;
        document.form1.p_pageno.value = 1;

        document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.p_process.value = 'CompleteMemberExcel';
        document.form1.submit();

        //2009.10.27 화면출력시 원래 레코드가 나오도록 하기 위해 사이즈, 넘버 원래대로 수정
        document.form1.p_pagesize.value = temp_pagesize;
        document.form1.p_pageno.value = temp_pageno;
    }

 // 파라미터 체크
    function chkParam() {
      if (document.form1.s_grcode.value == "" || document.form1.s_grcode.value == '----') {
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
/*
        if (document.form1.s_subjcourse.value == "" ) {
            alert("과정을 선택하세요.");
            return false;
        }
*/
    }
    //체크박스 체크
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

    //전체선택
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

    //전체선택 취소
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

    //폼메일 발송
    function whenFormMail() {
      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }
      document.form1.target = "_self";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.p_process.value = 'SendFormMail';
      document.form1.submit();
    }

    //일반메일 발송
    function whenFreeMail() {

      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }

      ff =document.form1;

      ff.p_msubjnm.value=ff.s_subjcourse.options[ff.s_subjcourse.selectedIndex].text;
      ff.p_mseqgrnm.value=ff.s_subjseq.options[ff.s_subjseq.selectedIndex].text;
      ff.p_msubj.value=ff.s_subjcourse.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value=ff.s_subjseq.value;
      ff.p_touch.value = "10";

      window.self.name = "ProposeMemberList";
      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
//      document.form1.action='/servlet/controller.study.StudyTotalStatusServlet';
      document.form1.action='<%=ems_url%>/ems/MassMail.do';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();

    }

    // 페이지 이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.study.StudentStatusAdminServlet';
         document.form1.p_process.value = "CompleteMemberList";
         document.form1.submit();
    }

    // 페이지 이동
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.study.StudentStatusAdminServlet';
         document.form1.p_process.value = "CompleteMemberList";
         document.form1.submit();
    }

    // 탭 선택
    function select_tab(sel){
        document.form1.target = "_self";
        if(sel == "1")      { document.form1.p_process.value = "StudentMemberList";    }
        else if(sel == "2") { document.form1.p_process.value = "CompleteMemberList";}
        document.form1.action = '/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.submit();
    }

    // 멤버정보
    function whenMemberInfo(userid) {
        window.self.name = "ProposeMemberList";
        open_window("openMember","","100","100","600","260");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        document.form1.target = window.self.name;
    }

	// 2007.10.27 페이지 사이즈 변경
    function pagesize(pageSize) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = 1;
         document.form1.p_pagesize.value = pageSize;
         document.form1.action = '/servlet/controller.study.StudentStatusAdminServlet';
         document.form1.p_process.value = "CompleteMemberList";
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
    $(document).ready(function(){
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
    function fnSubjSearchByName() {
        $("#oGrseq").trigger("change");
    }
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type="hidden" name="p_pagesize" value="<%=v_pagesize%>">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="p_rprocess" value="CompleteMemberList">
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type = "hidden" name="s_edustart">
    <input type = "hidden" name="s_eduend">

    <!-- tz_humantouc insert parameter start-->
    <input type="hidden" name="p_touch" value=''>
    <input type="hidden" name="p_msubjnm" value=''>
    <input type="hidden" name="p_mseqgrnm" value=''>
    <input type="hidden" name="p_msubj" value=''>
    <input type="hidden" name="p_myear" value=''>
    <input type="hidden" name="p_msubjseq" value=''>
    <!-- tz_humantouc insert parameter end -->

    <input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/study/s_title02.gif" ></td>
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

						<table border="0" cellspacing="0" cellpadding="0" width="99%" class="_srchT">
							<tr>
								<td width="10%" class="_tdT"><font color="red">★</font>교육그룹</td>
								<td width="15%" class="_tdS">
								<%if("H1".equals(box.getSession("gadmin"))){%>
									<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
									<kocca_select:select name="s_grcode" onChange="fnChangeGrcode();" sqlNum="course.0001" selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
									<%if(ss_gyear.equals("")){%>
										<script>$(document).ready(function(){setTimeout(fnChangeGrcode, 300);});</script>
									<%}%>
								<%}else{%>
									<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
									<kocca:selectBox name="s_grcode" id="s_grcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
								<%}%>
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
                               	<td class="_tdS">
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
			                    <% String s_user = box.getString("s_user"); %>                            
								
                               	<td class="_tdT">ID 또는 성명</td>
								<td class="_tdS"><input type="text" name="s_user" size="24" value="<%= s_user %>"></td>
								<td align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td>
                                
                            </tr>			                    							
							<%--
							<tr>
							<% String s_subjsearchkey = box.getString("s_subjsearchkey"); %>
								<td width="10%" class="_tdT">과정검색</td>
								<td colspan="2" class="_tdS"><input type="text" name="s_subjsearchkey" size="24" onChange="Main_subjcourse()" value="<%=s_subjsearchkey%>"></td>
								<td width="10%" class="_tdT">과정</td>
								<td colspan="4" class="_tdS"><kocca:selectBox name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9" 
									param="<%=ss_grcode%>"param4="<%=ss_upperclass%>"param5="<%=ss_middleclass%>"param6="<%=ss_lowerclass%>" 
									param7="<%=ss_grseq%>" param9="<%= s_subjsearchkey %>" 
									onChange="changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value);" 
									attr="" selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
								</td>
							</tr>
							<tr>
							<% String s_user = box.getString("s_user"); %>
								<td width="10%" class="_tdT">과정차수</td>
								<td width="15%" class="_tdS"><kocca:selectBox name="s_subjseq" sqlNum="subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4" 
									param="<%=ss_grcode%>"param2="<%=ss_grseq%>"param3="<%=ss_subjcourse%>"param4="<%=ss_gyear%>" 
									onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
								</td>
								<td width="10%" class="_tdT">ID 또는 성명</td>
								<td width="15%" class="_tdS"><input type="text" name="s_user" size="24" value="<%= s_user %>"></td>
								<td colspan="4" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td>
							</tr> --%>
							<input type = "hidden" name="s_start">
                       		<input type = "hidden" name="s_end">
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

        <table cellspacing="0" cellpadding="0" class="table1">
          <tr>
            <td width="125" height="23" align="center" valign="middle">

              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>
                  <td class="black_butt_middle"><a href="javascript:select_tab('1')" class="c">학습진행자</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>
              </table>

            </td>
            <td width="2"></td>
            <td width="125">

              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="blue_butt_left"></td>
                  <td class="blue_butt_top"></td>
                  <td rowspan="3" class="blue_butt_right"></td>
                </tr>
                <tr>
                  <td class="blue_butt_middle">학습완료자</td>
                </tr>
                <tr>
                  <td class="blue_butt_bottom"></td>
                </tr>
              </table>

            </td>
            <td align="right" valign="top">

      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right" width="65">
            <!--a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif" border="0"></a-->
            </td>
            <td width=4></td>
            <td align="right" width="65">
            <!--a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif" border="0"></a-->
            </td>
            <td width=4></td>
            <td align="right" height="20">
            <a href="javascript:whenFormMail()"><!--img src="/images/admin/button/btn_dogmail.gif" border="0"-->&nbsp;</a>
            </td>
            <td width=4></td>
            <td align="right" width="65">
            <!--a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a-->
            </td>
            <td width=4></td>
            <td align="right" width="65">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
            </td>

        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 끝 ----------------->

            </td>
          </tr>
        </table>
        <table cellspacing="0" cellpadding="0" class="table_out">
          <tr>
            <td bgcolor="#636563">
              <table cellspacing="1" cellpadding="0" class="s_table">
                <tr>
                  <td bgcolor="#FFFFFF" align="center" valign="top">
                    <br>
                  <!----------------- 학습완료자 시작 ----------------->
                    <table border="0" cellspacing="1" cellpadding="5" class="box_table_out">
                      <tr>
                        <td colspan="18" class="table_top_line"></td>
                      </tr>
                      <tr>
                        <td class="table_title"  width="4%">No</td>
                        <td class="table_title"  width="7%"><a href="javascript:whenOrder('grseqnm')" class="e">교육차수<% out.print("grseqnm".equals(v_orderColumn) ? (" asc".equals(v_orderType) ? "▲" : "▼") : ""); %></a></td>
                        <td class="table_title"  colspan="2"><a href="javascript:whenOrder('subjnm')" class="e">과정명<% out.print("subjnm".equals(v_orderColumn) ? (" asc".equals(v_orderType) ? "▲" : "▼") : ""); %></a></td>
                        <td class="table_title"  width="10%"><a href="javascript:whenOrder('membergubunnm')" class="e">회원구분<% out.print("membergugunnm".equals(v_orderColumn) ? (" asc".equals(v_orderType) ? "▲" : "▼") : ""); %></a></td>
                        <td class="table_title"  width="5%"><a href="javascript:whenOrder('userid')" class="e">ID<% out.print("userid".equals(v_orderColumn) ? (" asc".equals(v_orderType) ? "▲" : "▼") : ""); %></a></td>
                        <td class="table_title"  width="7%"><a href="javascript:whenOrder('name')" class="e">성명<% out.print("name".equals(v_orderColumn) ? (" asc".equals(v_orderType) ? "▲" : "▼") : ""); %></a></td>
                        <td class="table_title"  width="5%">과정<br>차수</td>
                        <!-- td class="table_title"  width="7%">구분</td-->
                        <td class="table_title"  width="7%">수료<br>여부</td>
                        <td class="table_title" width="5%">진도율</td>
                        <td class="table_title" width="5%">중간평가</td>
                        <td class="table_title" width="5%">최종평가</td>
                        <td class="table_title" width="5%">형성평가</td>
                        <td class="table_title" width="5%">리포트</td>
                        <td class="table_title" width="5%">참여도</td>
                        <td class="table_title" width="5%">기타</td>
                        <td class="table_title" width="5%">총점</td>
                        <!--td class="table_title"  width="5%"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td-->
                      </tr>
                      <%

                   DataBox dbox = null;


					String preCourse = "";			//이전출력과정 - 000000이면 일반과정이고 이외는 전문가과정
					int preStudentCnt = 1;
					int preSubjcnt = 1;
  					int l = 0;


if(ss_action.equals("go") ){	//go button 선택일때라면

	//내용이 있고 없음여부
	if( list.size() != 0 ){		// 검색된 내용이 있다면
	               v_total = list.size();

		for(i = 0; i < v_total; i++) {
				dbox = (DataBox)list.get(i);

				v_grseqnm       = dbox.getString("d_grseqnm");
				v_subj          = dbox.getString("d_subj");
				v_year          = dbox.getString("d_year");
				v_subjnm        = dbox.getString("d_subjnm");
				v_subjseq       = dbox.getString("d_subjseq");
				v_subjseqgr     = dbox.getString("d_subjseqgr");
				v_membergubunnm     = dbox.getString("d_membergubunnm");
				v_userid        = dbox.getString("d_userid");
				v_name          = dbox.getString("d_name");
				v_edustart      = dbox.getString("d_edustart");
				v_eduend        = dbox.getString("d_eduend");
				v_email         = dbox.getString("d_email");
				v_tstep         = dbox.getString("d_tstep");
				v_mtest         = dbox.getString("d_mtest");
				v_ftest         = dbox.getString("d_ftest");
				v_htest         = dbox.getString("d_htest");
				v_report        = dbox.getString("d_report");
				v_etc1          = dbox.getString("d_etc1");
				v_etc2          = dbox.getString("d_etc2");
				v_avtstep       = dbox.getString("d_avtstep");
				v_avmtest       = dbox.getString("d_avmtest");
				v_avftest       = dbox.getString("d_avftest");
				v_avhtest       = dbox.getString("d_avhtest");
				v_avreport      = dbox.getString("d_avreport");
				v_avetc1        = dbox.getString("d_avetc1");
				v_avetc2        = dbox.getString("d_avetc2");
				v_score         = dbox.getString("d_score");
				v_isonoff       = dbox.getString("d_isonoff");
                v_isgraduated   = dbox.getString("d_isgraduated");
				v_coursenm		= dbox.getString("d_coursenm");
				v_course		= dbox.getString("d_course");
				v_courseseq		= dbox.getString("d_courseseq");
				v_isbelongcourse	= dbox.getString("d_isbelongcourse");
				v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
				v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
				v_subjcnt	 = dbox.getInt("d_subjcnt");			// 전문가과정의 과목수

                //2009.10.27 페이지 나누기
				v_totalpage     = dbox.getInt("d_totalpage");
                v_rowcount      = dbox.getInt("d_rowcount");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
                v_dispnum       = dbox.getInt("d_dispnum");

				//if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
				//else                      {     v_isonoff_value="집합";     }

%>
                 <tr>
<%
			//전문가과정이라면
			if( v_isbelongcourse.equals("Y")){

					if(l == v_subjcnt) preCourse = "";

					if( !preCourse.equals("000000") && !preCourse.equals(v_course) ) {
						l = 1;
%>

                        <td class="table_01"><%=v_totalrowcount - v_pagesize * v_pageno + v_pagesize - i%></td>
                        <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_grseqnm%></td>
                        <td class="table_02_2" rowspan="<%=v_subjcnt%>">
						  <font class="text_color04" align="center">
						  <CENTER><%=v_coursenm%><br><%=StringManager.cutZero(v_courseseq)%>차</CENTER></font></td>
                        <td class="table_02_2">
						  <font class="text_color04" align="center">
						  <CENTER><%=v_subjnm%></CENTER></font></td>
                        <td class="table_02_1"><%=v_membergubunnm%></td>
                        <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%= v_userid %></a></td>
                        <td class="table_02_1"><%= v_name %></td>
                        <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
						<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
                        <td class="table_02_1"><%= v_isgraduated %></td>
                        <td class="table_02_1"><%= v_tstep %><br/>(<%= v_avtstep %>)</td>
                        <td class="table_02_1"><%= v_mtest %><br/>(<%= v_avmtest %>)</td>
                        <td class="table_02_1"><%= v_ftest %><br/>(<%= v_avftest %>)</td>
                        <td class="table_02_1"><%= v_htest %><br/>(<%= v_avhtest %>)</td>
                        <td class="table_02_1"><%= v_report %><br/>(<%= v_avreport %>)</td>
                        <td class="table_02_1"><%= v_etc1 %><br/>(<%= v_avetc1 %>)</td>
                        <td class="table_02_1"><%= v_etc2 %><br/>(<%= v_avetc2 %>)</td>
                        <td class="table_02_1"><%= v_score %></td>
                        <!--td class="table_02_1">
                          <input type="checkbox" name="p_checks" value="<%=v_userid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>">
                        </td-->
                        <input type="hidden" name="p_subj" value="<%=v_subj%>">
                        <input type="hidden" name="p_year" value="<%=v_year%>">
                        <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">

<%
						preCourse = v_course;
					}else{
						l++;
%>

                        <td class="table_01"><%=v_totalrowcount - v_pagesize * v_pageno + v_pagesize - i%></td>
                        <td class="table_02_2">
						  <font class="text_color04" align="center">
						  <CENTER><%=v_subjnm%></CENTER></font></td>
                        <td class="table_02_1"><%=v_membergubunnm%></td>
                        <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%= v_userid %></a></td>
                        <td class="table_02_1"><%= v_name %></td>
                        <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
						<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
                        <td class="table_02_1"><%= v_isgraduated %></td>
                        <td class="table_02_1"><%= v_tstep %><br/>(<%= v_avtstep %>)</td>
                        <td class="table_02_1"><%= v_mtest %><br/>(<%= v_avmtest %>)</td>
                        <td class="table_02_1"><%= v_ftest %><br/>(<%= v_avftest %>)</td>
                        <td class="table_02_1"><%= v_htest %><br/>(<%= v_avhtest %>)</td>
                        <td class="table_02_1"><%= v_report %><br/>(<%= v_avreport %>)</td>
                        <td class="table_02_1"><%= v_etc1 %><br/>(<%= v_avetc1 %>)</td>
                        <td class="table_02_1"><%= v_etc2 %><br/>(<%= v_avetc2 %>)</td>
                        <td class="table_02_1"><%= v_score %></td>
                        <!--td class="table_02_1">
                          <input type="checkbox" name="p_checks" value="<%=v_userid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>">
                        </td-->
                        <input type="hidden" name="p_subj" value="<%=v_subj%>">
                        <input type="hidden" name="p_year" value="<%=v_year%>">
                        <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">

<%
					}


			}else{		//전문가과정이 아니면
%>


                        <td class="table_01"><%=v_totalrowcount - v_pagesize * v_pageno + v_pagesize - i%></td>
                        <td class="table_02_1"><%=v_grseqnm%></td>
                        <td class="table_02_2" colspan="2">
						  <font class="text_color04" align="center">
						  <CENTER><%=v_subjnm%></CENTER></font></td>
                        <td class="table_02_1"><%=v_membergubunnm%></td>
                        <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%= v_userid %></a></td>
                        <td class="table_02_1"><%= v_name %></td>
                        <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
						<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
                        <td class="table_02_1"><%= v_isgraduated %></td>
                        <td class="table_02_1"><%= v_tstep %><br/>(<%= v_avtstep %>)</td>
                        <td class="table_02_1"><%= v_mtest %><br/>(<%= v_avmtest %>)</td>
                        <td class="table_02_1"><%= v_ftest %><br/>(<%= v_avftest %>)</td>
                        <td class="table_02_1"><%= v_htest %><br/>(<%= v_avhtest %>)</td>
                        <td class="table_02_1"><%= v_report %><br/>(<%= v_avreport %>)</td>
                        <td class="table_02_1"><%= v_etc1 %><br/>(<%= v_avetc1 %>)</td>
                        <td class="table_02_1"><%= v_etc2 %><br/>(<%= v_avetc2 %>)</td>
                        <td class="table_02_1"><%= v_score %></td>
                        <!--td class="table_02_1">
                          <input type="checkbox" name="p_checks" value="<%=v_userid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>">
                        </td-->
                        <input type="hidden" name="p_subj" value="<%=v_subj%>">
                        <input type="hidden" name="p_year" value="<%=v_year%>">
                        <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">

<%
			}
%>
			</tr>

<%
		}//for
	}else if( list.size() == 0){		//  내용이 없다면
%>
				<tr>
					<td align="center" bgcolor="#F7F7F7" height="50" colspan="18">등록된 내용이 없습니다</td>
				</tr>
              </table>
<%
	}
}
%>
                  </td>
                </tr>
              </table>

                 <!----------------- 학습완료자 끝 ----------------->

                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <br>
        
        <!----------------- page navi 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, row, v_pagesize, v_totalrowcount) %>
            </td>
          </tr>
        </table>
        <!----------------- page navi 끝 ----------------->
        
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>



</form>
</body>
</html>
