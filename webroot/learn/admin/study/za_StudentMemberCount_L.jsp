<%
//**********************************************************
//  1. 제      목: STUDENT MEMBER COUNT LIST
//  2. 프로그램명: za_StudentMemberCount_L.jsp
//  3. 개      요: 입과인원조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int v_pageno        = box.getInt("p_pageno");

    String  v_selTab        = box.getString("p_selTab");
    String  v_imgColor1      = "black";         //신청과정 이미지색
    String  v_imgColor2      = "black";         //수강과정 이미지색
    String  v_imgColor3      = "black";         //수료과정 이미지색
    String  v_imgColor4      = "black";         //학점이수현황 이미지색

    String  v_grseq         =  "";
    String  v_grseqnm       =  "";
    String  v_course        =  "";
    String  v_cyear         =  "";
    String  v_courseseq     =  "";
    String  v_coursenm      =  "";
    String  v_subj          =  "";
    String  v_year          =  "";
    String  v_subjnm        =  "";
    String  v_subjseq       =  "";
    String  v_subjseqgr     =  "";
    String  v_propstart     =  "";
    String  v_propend       =  "";
    String  v_edustart      =  "";
    String  v_eduend        =  "";
    String  v_isonoff       =  "";
    String  v_isonoff_value = "";
    String  v_isnewcourse   = "";
    String  v_studentcnt    = "";
    String  v_totalcnt      = "";
	String  v_isbelongcourse = "";


    int     v_studentlimit =  0;
    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
    int     v_procnt    =  0;
    int     v_proycnt    =  0;
    int     v_stucnt    =  0;
    int     v_comcnt    =  0;
    int     v_cancnt    =  0;
    int     v_totalstucnt   =  0;
    int     v_totalcomcnt   =  0;
    int     v_totalcancnt   =  0;
    int     v_totalprocnt   =  0;
    int     v_totalproycnt  =  0;
    int     v_totalstulimit =  0;
    int     v_personcnt     =  0;
	int		v_subjcnt		= 0;	//전문가과정안 과정수

    int v_today = 0;
    int v_dday = 0;                     //D데이 저장 변수
    int v_edustart_value = 0;
    int v_eduend_value = 0;
    int v_count = 0;

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_orderColumn  = box.getString("p_orderColumn");           //정렬할 컬럼명

    ArrayList list      = null;
    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode      = box.getString("s_grcode");        //교육그룹
    String  ss_gyear       = box.getString("s_gyear");         //년도
    String  ss_grseq       = box.getString("s_grseq");         //교육차수
    String  ss_upperclass  = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass  = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse  = box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq     = box.getString("s_subjseq");       //과정 차수
    String  ss_edustart    = box.getString("s_start");            //교육시작일
    String  ss_eduend      = box.getString("s_end");              //교육종료일

    if(v_selTab.equals("all") || v_selTab.equals("")){
        v_imgColor1 = "blue";
    } else if(v_selTab.equals("wait")){
        v_imgColor2 = "blue";
    } else if(v_selTab.equals("progress")){
        v_imgColor3 = "blue";
    } else if(v_selTab.equals("finish")){
        v_imgColor4 = "blue";
    }



    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";

    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("StudentMemberCountList");
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
    //탭변경시
    function select_tab(sel,p_action){
      if (p_action=="go"){
            if(document.form1.s_grcode.length == 0) {
                alert("선택된 교육그룹이 없습니다.");
                return;
            }
            top.ftop.setPam();
        }

        document.form1.target = "_self";
        document.form1.p_selTab.value = sel;
        document.form1.s_action.value = p_action;
        document.form1.p_process.value = 'StudentMemberCountList';
        document.form1.action = "/servlet/controller.study.StudentStatusAdminServlet";
        document.form1.submit();
    }

    //검색
    function whenSelection(p_action) {
        if (p_action=="go"){
        	top.ftop.setPam();
            if (chkParam() == false) {
                return;
            }
        }
        //alert(document.form1.p_selTab.value);
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.p_process.value = 'StudentMemberCountList';
        document.form1.submit();
    }

    //검색
    function whenSelection2(p_action, p_type) {
        if (p_action=="go"){
            if(document.form1.s_grcode.length == 0) {
                alert("선택된 교육그룹이 없습니다.");
                return;
            }

     //       var st_date = make_date(form1.s_start.value);
     //       var ed_date = make_date(form1.s_end.value);

     //       if((st_date.length > 0 && ed_date.length > 0) && st_date > ed_date) {
     //           alert("교육시작일이 교육종료일보다 큽니다.");
     //           return;
     //       }
     //       document.form1.s_edustart.value = st_date;
     //       document.form1.s_eduend.value   = ed_date;
            top.ftop.setPam();
        }
        if (p_type == 'selgubun')
        document.form1.s_seltext.value = 'ALL';
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.p_process.value = 'StudentMemberCountList';
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

    // 엑셀출력
    function whenExcel(p_execelprocess) {
        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
      }
        //window.self.name = "StudentMemberCountList";
        //open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
        //document.form1.target = "openExcel";
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
        document.form1.p_excelprocess.value = p_execelprocess;
        document.form1.p_process.value = 'StudentMemberCountExcel';
        //document.form1.p_process.value = p_process;
        document.form1.submit();
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

    // 전체선택 취소
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

    // 메일발송
    function whenSendMail() {
      if (chkSelected() < 1) {
        alert('메일을 발송할 과정을 선택하세요');
        return;
      }

      if(document.form1.p_selTab.value == 'wait'){
          document.form1.p_process.value = 'SendStudyBeforeMail';
      }
      else if(document.form1.p_selTab.value == 'finish'){
          document.form1.p_process.value = 'SendStudyAfterMail';
      }

      //alert(document.form1.p_process.value);
      //return;

      document.form1.target = "_self";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.submit();
    }

    // 페이지이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.study.StudentStatusAdminServlet';
         document.form1.p_process.value = "StudentMemberCountList";
         document.form1.submit();
    }

    // 페이지이동
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.study.StudentStatusAdminServlet';
         document.form1.p_process.value = "StudentMemberCountList";
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
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type = "hidden" name="s_edustart">
    <input type = "hidden" name="s_eduend">
    <input type="hidden" name="p_selTab" value="<%=v_selTab%>">
    <input type = "hidden" name="p_excelprocess">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/study/s_title01.gif" ></td>
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

						<table border="0" cellspacing="0" cellpadding="0" width="99%" >
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

      <!----------------- 탭이동/엑셀/모두선택/모두해지버튼 ----------------->

      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="left">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="110" height="23" align="center" valign="middle">
                    <table cellspacing="0" cellpadding="0" class="s_table">
                      <tr>
                        <td rowspan="3" class="<%=v_imgColor1%>_butt_left"></td>
                        <td class="<%=v_imgColor1%>_butt_top"></td>
                        <td rowspan="3" class="<%=v_imgColor1%>_butt_right"></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor1%>_butt_middle"><a href="javascript:select_tab('all','<%=ss_action%>')" class="c">전체</a></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor1%>_butt_bottom"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="2"></td>
                  <td width="110">
                    <table cellspacing="0" cellpadding="0" class="s_table">
                      <tr>
                        <td rowspan="3" class="<%=v_imgColor2%>_butt_left"></td>
                        <td class="<%=v_imgColor2%>_butt_top"></td>
                        <td rowspan="3" class="<%=v_imgColor2%>_butt_right"></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor2%>_butt_middle"><a href="javascript:select_tab('wait','<%=ss_action%>')" class="c">학습대기과정</a></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor2%>_butt_bottom"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="2"></td>
                  <td width="110">
                    <table cellspacing="0" cellpadding="0" class="s_table">
                      <tr>
                        <td rowspan="3" class="<%=v_imgColor3%>_butt_left"></td>
                        <td class="<%=v_imgColor3%>_butt_top"></td>
                        <td rowspan="3" class="<%=v_imgColor3%>_butt_right"></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor3%>_butt_middle"><a href="javascript:select_tab('progress','<%=ss_action%>')" class="c">학습진행과정</a></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor3%>_butt_bottom"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="2"></td>
                  <td width="110">
                    <table cellspacing="0" cellpadding="0" class="s_table">
                      <tr>
                        <td rowspan="3" class="<%=v_imgColor4%>_butt_left"></td>
                        <td class="<%=v_imgColor4%>_butt_top"></td>
                        <td rowspan="3" class="<%=v_imgColor4%>_butt_right"></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor4%>_butt_middle"><a href="javascript:select_tab('finish','<%=ss_action%>')" class="c">학습완료과정</a></td>
                      </tr>
                      <tr>
                        <td class="<%=v_imgColor4%>_butt_bottom"></td>
                      </tr>
                    </table>
                  </td>
                  <td>&nbsp;</td>
                </tr>
              </table>
            </td>
            <td align="right" height="20">&nbsp;</td>
            <!----------------- 탭선택시 Button 분기----------------->
            <%  if(v_selTab.equals("all") || v_selTab.equals("")){      %>      <!---- 전체선택 ---->
              <td align="right" width="65"><a href="javascript:whenExcel('StuMemberALLExcel')"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
            <%  }else if(v_selTab.equals("wait")){      %>                     <!---- 학습대기과정 ---->
              <td align="right" width="100"><!--a href="javascript:whenSendMail()"><img src="/images/admin/button/btn_previewmail.gif" border="0"></a--></td>
              <td width=4></td>

              <td align="right" width="64"><!--a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif"  border="0"></a--></td>
              <td width=4></td>
          <td align="right" width="87"><!--a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif" border="0"></a--></td>
          <td width=4></td>
          <td align="right" width="65"><a href="javascript:whenExcel('StuMemberWaitExcel')"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
            <%  }else if(v_selTab.equals("progress")){      %>                    <!---- 학습진행과정 ---->
              <td align="right" width="65"><a href="javascript:whenExcel('StuMemberProgExcel')"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
            <%  }else if(v_selTab.equals("finish")){   %>                    <!---- 학습완료과정 ---->
            <td align="right" width="100"><!--a href="javascript:whenSendMail()"><img src="/images/admin/button/btn_aftermail.gif"  border="0"></a--></td>
            <td width=4></td>
              <td align="right" width="64"><!--a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif"  border="0"></a--></td>
              <td width=4></td>
              <td align="right" width="87"><!--a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif" border="0"></a--></td>
              <td width=4></td>
              <td align="right" width="65"><a href="javascript:whenExcel('StuMemberFinishExcel')"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
            <%  }   %>
            <!----------------- 탭선택시 Button 분기----------------->
        </tr>
      </table>
      <!----------------- 탭이동/엑셀/모두선택/모두해지버튼 ----------------->
      <!----------------- 탭선택시 Include 분기----------------->
      <%  if(v_selTab.equals("all") || v_selTab.equals("")){      %>      <!---- 전체선택 ---->
      <%@ include file="/learn/admin/study/za_SelectedSubjAll_L.jsp"    %>
      <%  }else if(v_selTab.equals("wait")){      %>                     <!---- 학습대기과정 ---->
      <%@ include file="/learn/admin/study/za_SelectedSubjWait_L.jsp"  %>
      <%  }else if(v_selTab.equals("progress")){      %>                    <!---- 학습진행과정 ---->
      <%@ include file="/learn/admin/study/za_SelectedSubjPorgress_L.jsp" %>
      <%  }else if(v_selTab.equals("finish")){   %>                    <!---- 학습완료과정 ---->
      <%@ include file="/learn/admin/study/za_SelectedSubjFinish_L.jsp" %>
      <%  }   %>
      <!----------------- 탭선택시 Include 분기 끝----------------->
     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
