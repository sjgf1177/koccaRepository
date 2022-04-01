<%
//**********************************************************
//  1. 제     목: PROPOSE MEMBER LIST
//  2. 프로그램명: za_StudentMember_L.jsp
//  3. 개     요: 신청명단조회
//  4. 환     경: JDK 1.3
//  5. 버     젼: 1.0
//  6. 작     성: 2004-02-04
//  7. 수     정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=MS949" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.StringManager" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process = box.getString("p_process");
    int v_pageno = box.getInt("p_pageno");

    String v_searchtext= box.getString("p_searchtext");
    String v_grseq = "";
    String v_grseqnm = "";
    String v_course = "";
    String v_cyear = "";
    String v_courseseq = "";
    String v_coursenm = "";
    String v_subj = "";
    String v_year = "";
    String v_subjnm = "";
    String v_subjseq = "";
    String v_companynm = "";
    String v_userid = "";
    String v_name = "";
    String v_appdate = "";
    String v_edustart = "";
    String v_eduend = "";
    String v_chkfirst = "";
    String v_chkfinal = "";
    String v_email = "";
    String v_ismailing = "";
    String v_isnewcourse= "";
    String v_chkfirst_value = "";
    String v_chkfinal_value = "";
    String v_isonoff = "";
    String v_isonoff_value="";
    String v_subjseqgr = "";
    String v_isclosed = "";
    String v_eduterm = "";
    String v_isgoyong = "";
    String v_tmp_subj = "";
    String v_iscourseYn = "";
    String v_score = "";

    int v_totalpage = 0;
    int v_rowcount = 0;
    int v_rowspan = 0;
    int v_total = 0;
    int i = 0;
    int l = 0;
    int v_subjcnt = 0;
    int v_usercnt = 0;

    String v_membergubun = "";

    ArrayList list    = null;
    //DEFINED class&variable END

    //DEFINED in relation to select START
    String ss_grcode = box.getStringDefault("s_grcode", "");           //교육그룹
    String ss_gyear = box.getString("s_gyear");            //년도
    String ss_grseq = box.getString("s_grseq");            //교육차수
    String ss_grseqnm = box.getString("s_grseqnm");        //교육차수명
    String ss_upperclass = box.getString("s_upperclass");    //과정대분류
    String ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");    //과정소분류
    String ss_subjcourse = box.getString("s_subjcourse");      //과정&코스
    String ss_subjseq = box.getString("s_subjseq");        //과정 차수
    String ss_selgubun = box.getString("s_selgubun");
    String ss_seltext = box.getString("s_seltext");
    String ss_seldept = box.getString("s_seldept");
    String ss_edustart = box.getString("s_start");            //교육시작일
    String ss_eduend = box.getString("s_end");             //교육종료일
    String s_subjsearchkey = box.getString("s_subjsearchkey");
    String ss_action = box.getString("s_action");
    String v_ongo = "whenSelection('go')";
    String ss_scoremin = box.getString("p_scoremin");
    String ss_scoremax = box.getString("p_scoremax");
    String ss_area = box.getString("s_area");

    String v_isaddpossible = box.getString("p_isaddpossible");  //차수선택시만

    String v_isclosedseq = "";  //차수선택시만 이력생성여부
    int v_studentlimit = 0;
    int v_propcnt = 0;

    DataBox dbox1 = null;
    if (ss_action.equals("go")) {   //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("StudentMemberList");
        dbox1 = (DataBox)request.getAttribute("subjinfo");
        System.out.println(dbox1);

        if(dbox1 != null){
            v_isclosedseq = dbox1.getString("d_isclosed");
            v_studentlimit = dbox1.getInt("d_studentlimit");
            v_propcnt = dbox1.getInt("d_propcnt");
            v_subjseqgr = dbox1.getString("d_subjseqgr");
            v_eduterm = dbox1.getString("d_eduterm");
            v_isonoff = dbox1.getString("d_isonoff");
        }
    }

    String v_orderType = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String v_order = box.getString("p_order");                  //정렬컬럼

    String p_membergubun = box.getString("p_membergubun");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script language="VBScript" src="/script/cresys_lib.vbs"></script>
<script type="text/javascript">


    /**
     * 검색조건에 따라 수강생 목록을 조회한다.
     * @param p_action (별로 의미 없어 보임)
     */
    function whenSelection(p_action) {

        if (p_action == "go" || p_action == "goText") {

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

            // top.ftop.setPam();
        }

        document.form1.s_action.value = p_action;
        //document.form1.p_searchtext.value = "";
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.propose.StudentManagerServlet";
        document.form1.p_process.value = "StudentMemberList";
        document.form1.submit();
    }

    // 이름으로 검색
    function whenTextSearch(p_action){
      if(blankCheck(document.form1.p_searchtext.value)){
        alert("검색어를 입력하세요");
        return ;
      }

      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
      }
       top.ftop.setPam();

       document.form1.s_action.value = p_action;
       document.form1.target = "_self";
       document.form1.action='/servlet/controller.propose.StudentManagerServlet';
       document.form1.p_process.value = 'StudentMemberList';
       document.form1.submit();

    }
    // 정렬
    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_order.value = column;
        whenSelection("go");
    }

    // 엑셀출력
    function whenExcel() {

       if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
       }

       if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '----') {
            if(confirm("교육차수를 제외할 경우 2분이상 소요됩니다\n\n그래도 엑셀출력 하시겠습니까?")) {
            } else {
                return;
            }
       }

       top.ftop.setPam();

        //window.self.name = "StudentMemberList";
        //open_window("openExcel","","100","100","800","400","yes","yes","yes","yes","yes");
        document.form1.s_action.value = "go";
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.propose.StudentManagerServlet';
        document.form1.p_process.value = 'StudentMemberExcel'
        document.form1.p_action.value = "go";;
        document.form1.submit();
    }


    // 조건채크
    function chkSelected() {
      var selectedcnt = 0;
      /* if(document.form1.all['p_checks'] == '[object]') { */
      if(typeof document.getElementsByName("p_checks") == 'object') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            if (document.form1.p_checks[i].checked == true) {
              selectedcnt++;

              //교육반려자 userid list
              if(document.form1.p_chkfinal[i].value == "N"){
                document.form1.p_rejectidlist.value += document.form1.p_seluserid[i].value+", ";
              }

              //최종승인이 아닌자 userid List - 반려시 제약
              if(document.form1.p_chkfinal[i].value != "Y"){
                document.form1.p_chkfinalNlist.value +=  document.form1.p_seluserid[i].value+", ";
              }

              //승인불가능 userid List
              if(document.form1.p_isclosed[i].value == "Y" || document.form1.p_chkfinal[i].value != "N"){
                document.form1.p_reapprovallist.value +=  document.form1.p_seluserid[i].value+", ";
              }

              //고용보험미적용자변경불가능 userid List
              if(document.form1.p_isclosed[i].value == "Y" || document.form1.p_chkfinal[i].value != "Y"){
                document.form1.p_nogoyonglist.value +=  document.form1.p_seluserid[i].value+", ";
              }

              //수료결재완료 userid List
              if(document.form1.p_isclosed[i].value == "Y"){
                document.form1.p_closeidlist.value += document.form1.p_seluserid[i].value+", ";
              }

              //온라인교육이면서 교육시작일이후인것 userid List
              if(!(document.form1.p_isonoff[i].value == "ON" || parseInt(document.form1.p_eduterm[i].value)>3)){
                document.form1.p_onoffchkpointlist.value += document.form1.p_seluserid[i].value+", ";
              }
            }
          }
        } else {
          if (document.form1.p_checks.checked == true) {
            selectedcnt++;

            //교육반려자 userid
              if(document.form1.p_chkfinal.value == "N"){
                document.form1.p_rejectidlist.value += document.form1.p_seluserid.value+", ";
              }

              //최종승인이 아닌자 userid List - 반려시 제약
              if(document.form1.p_chkfinal.value != "Y"){
                document.form1.p_chkfinalNlist.value +=  document.form1.p_seluserid.value+", ";
              }

              //승인불가능 userid
              if(document.form1.p_isclosed.value == "Y" ||document.form1.p_chkfinal.value != "N"){
                document.form1.p_reapprovallist.value +=  document.form1.p_seluserid.value+", ";
              }

              //고용보험미적용자변경불가능 userid List
              if(document.form1.p_isclosed.value == "Y" || document.form1.p_chkfinal.value != "Y"){
                document.form1.p_nogoyonglist.value +=  document.form1.p_seluserid.value+", ";
              }

              //반려 불가능 userid
              if(document.form1.p_rejectpossible.value == "N"){
                document.form1.p_disrejlist.value += document.form1.p_seluserid.value+", ";
              }


              //수료결재완료 userid
              if(document.form1.p_isclosed.value == "Y"){
                document.form1.p_closeidlist.value += document.form1.p_seluserid.value+", ";
              }

              //온라인교육이면서 교육시작일이후인것 userid List
              if(!(document.form1.p_isonoff.value == "ON" || parseInt(document.form1.p_eduterm.value)>3)){
                document.form1.p_onoffchkpointlist.value += document.form1.p_seluserid.value+", ";
              }
          }
        }
      }

      return selectedcnt;
    }

    // 초기화
    function clearlistcode(){
          document.form1.p_rejectidlist.value = "";
          document.form1.p_chkfinalNlist.value = "";
          document.form1.p_reapprovallist.value = "";
          document.form1.p_nogoyonglist.value = "";
          document.form1.p_disrejlist.value = "";
          document.form1.p_closeidlist.value = "";
          document.form1.p_userlist.value   = "";
          document.form1.p_grduserllist.value = "";
          document.form1.p_onoffchkpointlist.value = "";

    }

    //전체선택
    function whenAllSelect() {
     /*  if(document.form1.all['p_checks'] == '[object]') { */
    	if(typeof document.getElementsByName("p_checks") == 'object') {
    		if (document.form1.p_checks.length > 0) {
          		for (i=0; i<document.form1.p_checks.length; i++) {
            		document.form1.p_checks[i].checked = true;
        		}
        	} else {
          		document.form1.p_checks.checked = true;
       		}
    	}
    }

    //전체선택취소
    function whenAllSelectCancel() {
      /* if(document.form1.all['p_checks'] == '[object]') { */ 
      if(typeof document.getElementsByName("p_checks") == 'object') {
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


//      window.self.name = "StudentMemberList";
      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
      document.form1.action='/servlet/controller.study.StudyTotalStatusServlet';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
    }

    // 페이지이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.propose.StudentManagerServlet';
         document.form1.p_process.value = "StudentMemberList";
         document.form1.submit();
    }

    // 페이지이동
    function goPage(pageNum) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.propose.StudentManagerServlet';
         document.form1.p_process.value = "StudentMemberList";
         document.form1.submit();
    }

    // 회원정보
    function whenMemberInfo(userid, subj, subjseq, year) {
//        window.self.name = "ProposeList";
        open_window("openMember","","100","100","1024","700",'','','','yes');
        document.form1.target = "openMember";
        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet?p_searchtext="+userid+"&p_subj="+subj;
        document.form1.p_subjseq.value=subjseq;
        document.form1.p_year.value=year;
        document.form1.p_userid.value=userid;
//      document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = "PersonalSelect";
//      document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
//        document.form1.target = window.self.name;
    }

    //수강생삭제
    function whenStudentDelete() {
       if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }
/*
        if (document.form1.s_subjcourse.value == 'ALL') {
          alert("과정을 선택하세요.");
          return ;
        }
*/
        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
        }

        if(document.form1.p_isclosedseq.value == "Y"){
           alert("수료처리 되어 대상자를 삭제할수 없습니다.");
           return;
        }

        if (chkSelected() <1) {
            alert("삭제대상을 선택하세요.");
            return;
        }

        if (document.form1.p_closeidlist.value != "") {
            alert("수료처리 완료된 대상은 삭제할수 없습니다!!!!\n\n<삭제 할수 없는대상>\n"+document.form1.p_closeidlist.value);
            clearlistcode();
            return;
        }

        clearlistcode();

       if(confirm("모든교육정보가 삭제처리됩니다.\n\n 교육생삭제를 실시하시겠습니까?")){
         document.form1.target = "_self";
         document.form1.action = '/servlet/controller.propose.StudentManagerServlet';
         document.form1.p_process.value = "StudentMemberDelete";
         document.form1.submit();
       }else{
         alert("삭제처리를 취소하셨습니다.");
         return;
       }
    }

    /**
     * 현재 선택된 과정에 대상자를 추가한다.
     */
    function fnAddStudent() {

        if ($("#oGrcode").val() == "") {
            alert("교육그룹을 선택하세요.");
            $("#oGrcode").focus();
            return ;
        }

        if ($("#oSubjcourse").val() == "ALL") {
          alert("과정을 선택하세요.");
          $("#oSubjcourse").focus();
          return ;
        }

        if(document.form1.p_isclosedseq.value == "Y"){
           alert("수료처리 되어 대상자를 추가할수 없는 상태입니다.");
           return;
        }

        if ( $("#oSubjseqgr").val() == "" ) {
            alert("조회를 먼저 실행해 주세요.");
            return;
        }
        if( $("#oOrgSearchSubj").val() != $("#oSubjcourse").val() ) {
            alert("검색조건에서 과정이 변경되었습니다. 조회를 먼저 실행해 주세요.");
            return;
        }

        subjnm = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].text;
        grcodenm = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].text;
        document.form1.p_targetsubjseq.value = $("#oSubjseq").val();

        open_window("openAddStudent","","100","100","700","600","","","","yes","no");
        document.form1.target = "openAddStudent";
        document.form1.action="/servlet/controller.propose.StudentManagerServlet";
        document.form1.p_process.value = "addtargetSearch";
        document.form1.s_subjnm.value = subjnm;
        document.form1.s_grcodenm.value = grcodenm;
        document.form1.submit();
        // document.form1.target = window.self.name;
    }

    //반려처리
    function whenStudentRej(){
      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return;
        }

        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
        }

        if(document.form1.p_isclosedseq.value == "Y"){
           alert("수료처리 되어 대상자를 반려할수 없습니다.");
           return;
        }

        if(parseInt(document.form1.p_edutermseq.value)<4 && document.form1.p_isonoffseq.value == "ON"){
           alert("교육시작전입니다. 삭제하여 주십시오!!");
           return;
        }

        if (chkSelected() <1) {
            alert("반려대상을 선택하세요.");
            return;
        }

        if (document.form1.p_chkfinalNlist.value != "") {
            alert("승인되지 않은대상은 반려처리할수 없습니다.!!!!\n\n<반려 할수 없는대상>\n"+document.form1.p_chkfinalNlist.value);
            clearlistcode();
            return;
        }

        if (document.form1.p_closeidlist.value != "") {
            alert("수료처리 완료된 대상은 반려할수 없습니다!!!!\n\n<반려 할수 없는대상>\n"+document.form1.p_closeidlist.value);
            clearlistcode();
            return;
        }

        if (document.form1.p_onoffchkpointlist.value != "") {
            alert("온라인교육이면서 교육시작일 이후에만 반려가 가능합니다.!!!!\n\n<반려 할수 없는대상>\n"+document.form1.p_onoffchkpointlist.value);
            clearlistcode();
            return;
        }

        if (document.form1.p_rejectidlist.value != "") {
            alert("이미 반려처리되어 있는 대상이 있습니다!!!!\n\n<반려 할수 없는대상>\n"+document.form1.p_rejectidlist.value);
            clearlistcode();
            return;
        }

        clearlistcode();

        open_window("rejectedReason","","100","100","550","400","","","","yes","no");
        document.form1.target = "rejectedReason";
        document.form1.action='/servlet/controller.propose.StudentManagerServlet';
        document.form1.p_process.value = "rejectedReason";
        document.form1.submit();

    }

    //반려자 재승인처리
    function whenStudentApp(){
        if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }

        if (document.form1.s_subjcourse.value == 'ALL') {
          alert("과정을 선택하세요.");
          return ;
        }

        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
        }

        if(document.form1.p_isclosedseq.value == "Y"){
           alert("수료처리 되어 대상자를 승인할수 없습니다.");
           return;
        }

        if (chkSelected() <1) {
            alert("승인대상을 선택하세요.");
            return;
        }

        if (document.form1.p_reapprovallist.value != "") {
            alert("승인처리할수 없는 대상이 있습니다.!!!!\n\n<승인할수 없는대상>\n"+document.form1.p_reapprovallist.value);
            alert("<승인대상조건>\n\n 현재 반려처리되있는 수강생중 수료처리 이전상태  ");
            clearlistcode();
            return;
        }

        clearlistcode();
          document.form1.target = "_self";
          document.form1.action='/servlet/controller.propose.StudentManagerServlet';
          document.form1.p_process.value = "studentapproval";
          document.form1.submit();
    }

    //고용보험미적용자변경처리
    function whenNogoyong(){
        if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }

        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
        }

        if(document.form1.p_isclosedseq.value == "Y"){
           alert("수료처리 되어 대상자를 변경할수 없습니다.");
           return;
        }

        if (chkSelected() <1) {
            alert("변경대상을 선택하세요.");
            return;
        }

        if (document.form1.p_nogoyonglist.value != "") {
            alert("변경할수 없는 대상이 있습니다.!!!!\n\n<승인할수 없는대상>\n"+document.form1.p_nogoyonglist.value);
            alert("<변경대상조건>\n\n 현재 승인처리되있는 수강생중 수료처리 전상태  ");
            clearlistcode();
            return;
        }

        clearlistcode();
          document.form1.target = "_self";
          document.form1.action='/servlet/controller.propose.StudentManagerServlet';
          document.form1.p_process.value = "studentnogoyong";
          document.form1.submit();
    }


    //고용보험적용자변경처리
    function whenGoyong(){
        if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }

        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
        }

        if(document.form1.p_isclosedseq.value == "Y"){
           alert("수료처리 되어 대상자를 변경할수 없습니다.");
           return;
        }

        if (chkSelected() <1) {
            alert("변경대상을 선택하세요.");
            return;
        }

        if (document.form1.p_nogoyonglist.value != "") {
            alert("변경할수 없는 대상이 있습니다.!!!!\n\n<승인할수 없는대상>\n"+document.form1.p_nogoyonglist.value);
            alert("<변경대상조건>\n\n 현재 승인처리되있는 수강생중 수료처리 전상태  ");
            clearlistcode();
            return;
        }

        clearlistcode();
          document.form1.target = "_self";
          document.form1.action='/servlet/controller.propose.StudentManagerServlet';
          document.form1.p_process.value = "studentgoyong";
          document.form1.submit();
    }

        //enter key 사용했을때
    function searchIDKeyEvent(e){
     if (e.keyCode =='13'){  whenSelection('go');  }
    }


        // SMS 전송
    function whenSMS() {
      if (chkSelected() < 1) {
        alert('SMS를 발송할 학습자를 선택하세요');
        return;
      }

      ff =document.form1;

      ff.p_msubjnm.value=ff.s_subjcourse.options[ff.s_subjcourse.selectedIndex].text;
      ff.p_mseqgrnm.value=ff.s_subjseq.options[ff.s_subjseq.selectedIndex].text;
      ff.p_msubj.value=ff.s_subjcourse.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value=ff.s_subjseq.value;
      ff.p_touch.value = "09";

//      window.self.name = "StudentMemberList";
      open_window("openSMS","","100","100","620","354");
      document.form1.target = "openSMS";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.p_process.value = 'SendSMS';
      document.form1.submit();
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
    });

    /**
     * 교육 그룹별 연도 selectbox 지정
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oGyear").append("<option value=\"\">== 선택 ==</option>");
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");

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
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");

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
     * ?
     */
    function fnSubjSearchByName() {
        $("#oGrseq").trigger("change");
    }


</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<form name="form1" method="post">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">   <!--in relation to select-->
    <input type="hidden" name="p_rprocess" value="StudentMemberList">
    <input type="hidden" name="p_isMailing" value='1'>
    <input type="hidden" name="p_action" value=''>

    <input type="hidden" name="s_grcodenm"  value="">
    <input type="hidden" name="s_subjnm"  value="">

    <input type="hidden" name="p_userid">
    <input type="hidden" name="p_subjseq">
    <input type="hidden" name="p_year">

    <input type="hidden" name="p_order" value="<%=v_order%>">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="s_edustart">
    <input type="hidden" name="s_eduend">
    <input type="hidden" name="p_subjseqgr" id="oSubjseqgr" value="<%= v_subjseqgr %>">
    <input type="hidden" name="p_isclosedseq" value="<%= v_isclosedseq %>">
    <input type="hidden" name="p_stulimit" value="<%=v_studentlimit%>">
    <input type="hidden" name="p_isaddpossible" value="<%=v_isaddpossible%>">
    <input type="hidden" name="p_propcnt" value="<%=v_propcnt%>">
    <input type="hidden" name="p_targetsubjseq" value="">

    <input type="hidden" name="p_userlist">
    <input type="hidden" name="p_rejectidlist">   <!-- 교육반려자   Userid List --->
    <input type="hidden" name="p_chkfinalNlist">     <!-- 최종승인이 아닌자 Userid List --->
    <input type="hidden" name="p_reapprovallist">   <!-- 승인불가능   Userid List-->
    <input type="hidden" name="p_nogoyonglist">   <!-- 고용보험미적용자변경불가능   Userid List-->
    <input type="hidden" name="p_disrejlist">       <!-- 반려불가능   Userid List-->
    <input type="hidden" name="p_grduserllist">   <!-- 수료처리자 Userid List-->
    <input type="hidden" name="p_closeidlist">     <!-- 수료결재완료 Userid List-->

    <!--input type="hidden" name="p_isclosed"--> <!-- 수료결재상태 완료리스트 -->
    <input type="hidden" name="p_onoffchkpointlist"> <!-- 온라인교육이면서 교육시작일이후 -->

    <input type = "hidden" name="p_edutermseq" value="<%=v_eduterm%>">
    <input type = "hidden" name="p_isonoffseq" value="<%=v_isonoff%>">

    <!-- tz_humantouc insert parameter start-->
    <input type="hidden" name="p_touch" value='03'>
    <input type="hidden" name="p_msubjnm" value=''>
    <input type="hidden" name="p_mseqgrnm" value=''>
    <input type="hidden" name="p_msubj" value=''>
    <input type="hidden" name="p_myear" value=''>
    <input type="hidden" name="p_msubjseq" value=''>
    <!-- tz_humantouc insert parameter end -->

    <input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">
    <input type="hidden" name="emsName" value="<%=box.getSession("name")%>">
    <input type="hidden" name="emsEmail" value="<%= box.getSession("email")%>">
    <input type="hidden" name="s_subjseq" value="<%= box.getString("s_subjseq")%>" />
    <input type="hidden" name="orgSearchSubj" id="oOrgSearchSubj" value="<%= ss_subjcourse %>" />

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/propose/p_title15.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

      <!----------------- 조건검색 시작 ----------------->
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
                      <td  align="left" valign="middle">
                    <table border="0" cellspacing="0" cellpadding="0" width="99%" >
                        <colgroup>
                            <col style="width:8%" />
                            <col style="width:27%" />
                            <col style="width:8%" />
                            <col style="width:10%" />
                            <col style="width:8%" />
                            <col style="width:34%" />
                            <col style="width:5%" />
                        </colgroup>
                        <tr>
                            <td class="_tdT"><font color="red">★</font>교육그룹</td>
                            <td class="_tdS">
                                <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="== 교육그룹 ==" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                            </td>
                            <td class="_tdT"><font color="red">★</font>연도</td>
                            <td class="_tdS">
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
                                <!--
                                <select name="s_gyear" id="oGyear" style="width:88px;">
                                    <option value="">== 선택 ==</option>
                                </select>
                                //-->
                            </td>
                            <td class="_tdT"><font color="red">★</font>교육차수</td>
                            <td class="_tdS">
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
                            <td rowspan="3">
                                <a href="javascript:whenSelection('go');"><img src="/images/admin/button/b_go.gif" border="0" align="absmiddle" alt="검색" /></a>
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <td class="_tdT">대분류</td>
                            <td class="_tdS"><%-- kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
                                onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" / --%>
                            </td>
                            <td class="_tdT">중분류</td>
                            <td class="_tdS"><%-- kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
                                onChange="changes_lowerclass(s_upperclass.value, this.value);" afterScript="Main_s_grseq" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" / --%>
                            </td>
                            <td class="_tdT">소분류</td>
                            <td><%-- kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
                                onChange="" afterScript="Main_s_grseq" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" / --%>
                            </td>
                        </tr>
                        //-->
                        <tr>
                            <td class="_tdT">과정검색</td>
                            <td class="_tdS">
                                <input type="text" name="s_subjsearchkey" id="oSubjSearchKey" size="17" onkeypress="if(event.keyCode == '13') fnSubjSearchByName();" value="<%= s_subjsearchkey %>">
                                <a href="javascript:fnSubjSearchByName();" ><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>
                            </td>
                            <td class="_tdT">과정</td>
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
                            <td class="_tdT">교육구분</td>
                            <td class="_tdS">
                                <select name="s_area">
                                    <option value="T" <% if(ss_area.equals("T")) out.print("selected"); %>>전체</option>
                                    <option value="G0" <% if(ss_area.equals("G0")) out.print("selected"); %>>게임</option>
                                    <option value="B0" <% if(ss_area.equals("B0")) out.print("selected"); %>>방송</option>
                                    <option value="K0" <% if(ss_area.equals("K0")) out.print("selected"); %>>문콘</option>
                                </select>
                            </td>
                            <td class="_tdT">총점</td>
                            <td colspan="3" class="_tdS"><input type="text" name="p_scoremin" size="4" value="<%= ss_scoremin %>" /> ~ <input type="text" name="p_scoremax" size="4" value="<%=ss_scoremax %>"/></td>
                        </tr>
                    </table>
                    </td></tr>
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
      <!----------------- 조건검색 끝 ----------------->

      <br>
      <br>

      <!---------------------////////////////////////////// 과정/차수정보 ////////////////////////////////-------------------------->
<%

        String v_usemanagerapproval ="";
        String v_ispropose        ="";
        String v_isapproval      ="";
        String v_educnt          ="";
        String v_mastercd          ="";
        String v_masternm          ="";
        String v_proposetype        ="";
        String v_isedutarget        ="";

        if (!ss_subjcourse.equals("ALL")&&!ss_subjseq.equals("ALL")&&!ss_subjcourse.equals("")&&!ss_subjseq.equals("")&&ss_action.equals("go")) {
        DataBox dbox2 = (DataBox)request.getAttribute("isManagerStatus");
             if(dbox2 != null){
                  v_mastercd           = dbox2.getString("d_mastercd");
                  v_masternm           = dbox2.getString("d_masternm");
                  v_isedutarget     = dbox2.getString("d_isedutarget");

                  v_educnt       = dbox2.getString("d_educnt");
             }

%>
        <%}%>

        <!---------------------////////////////////////////// 과정/차수정보끝 ////////////////////////////////-------------------------->
<!--
      <br>
      <br>
-->
      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="2" cellspacing="0">
        <tr>
            <td height="20">
              <table  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="txt_input"> ID 또는 이름 :</td>
                <td><input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>" onkeypress="searchIDKeyEvent(event)"></td>
                <td width=8></td>
                  <td><a href="javascript:whenSelection('go')"><img src="/images/admin/button/search3_butt.gif"  border="0"></a></td>
                </tr>
              </table>
            </td>
            <td align="right" width="30">
            <a href="javascript:whenSMS()"><img src="/images/admin/button/btn_sms.gif" border="0"></a>
            </td>
            <td width="50" align="right">
            <a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a>
            </td>
            <td width="32" align="right">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
            </td>
            <!--td width="50" align="right">
            <a href="javascript:whenNogoyong()"><img src="/images/admin/button/btn_goyong.gif" border="0" alt="고용보험비대상자로지정"></a>
            </td>
            <td width="50" align="right">
            <a href="javascript:whenGoyong()"><img src="/images/admin/button/btn_goyong2.gif" border="0" alt="고용보험대상자로지정"></a>
            </td-->
            <td width="32" align="right" >
            <a href="javascript:whenStudentDelete()"><img src="/images/admin/button/btn_del.gif" border="0"></a>
            </td>
            <td width="70"  align="right" >
            <a href="javascript:fnAddStudent();"><img src="/images/admin/button/btn_peopleplus.gif" border="0"></a>
            </td>
            <td width="45"  align="right" >
            <a href="javascript:whenStudentRej()"><img src="/images/admin/button/btn_reject.gif" border="0"></a>
            </td>
            <!--
            <td width="45"  align="right" >
            <a href="javascript:whenStudentApp()"><img src="/images/admin/button/btn_approve.gif" border="0"></a>
            </td>
            -->
        </tr>
        <tr>
          <td height="3" colspan="10"></td>
        </tr>
      </table>
      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 끝 ----------------->
      <!----------------- 신청명단조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out" board="0">
          <tr>
            <td colspan="17" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title">No</td>
            <td class="table_title" width="20%" colspan="2"><a href="javascript:whenOrder('subj')" class="e">과정</a></td>
            <td class="table_title" width="30">
                <kocca_select:select name="p_membergubun" sqlNum="membergubun"  param=" "
        onChange="whenSelection('go');" attr=" 회원구분" selectedValue="<%= p_membergubun %>" isLoad="true" all="true" type="3" styleClass="table_title"/>
            </td>
            <td class="table_title" width="5%"><a href="javascript:whenOrder('userid')" class="e">ID</a></td>
            <td class="table_title" width="7%"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
            <td class="table_title" width="5%">과정<br>차수</td>
            <td class="table_title" width="7%">구분</td>
            <td class="table_title">교육기간</td>
            <td class="table_title">신청일</td>
            <td class="table_title"><kocca_select:select name="p_chkfinal_l" sqlNum="finalconfirmgubun"  param=" "
    onChange="whenSelection('go');" attr=" 최종<br>승인" selectedValue='<%= box.get("p_chkfinal_l") %>' isLoad="true" all="true" type="3" styleClass="table_title"/></td>
            <td class="table_title"><kocca_select:select name="p_ejectpossible" sqlNum="ejectpossible"  param=" "
    onChange="whenSelection('go');" attr=" 이수<br>여부" selectedValue='<%= box.get("p_ejectpossible") %>' isLoad="true" all="true" type="3" styleClass="table_title"/></td>
            <td class="table_title">총점</td>
            <td class="table_title"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
          </tr>
        <%
            int is_complete = 0;            //수료여부
            String v_grdvalue = "";
            String v_grd_value = "";
            String v_rejectpossible = "";   //반려가능여부

            if (ss_action.equals("go")) {   //go button 선택시만 list 출력
                v_total = list.size();
                for(i = 0; i < v_total; i++) {
                    //ProposeStatusData data  = (ProposeStatusData)list.get(i);
                    DataBox dbox  = (DataBox)list.get(i);

                    if(dbox != null){
                        v_subj            = dbox.getString("d_subj");
                        v_year            = dbox.getString("d_year");
                        v_subjnm            = dbox.getString("d_subjnm");
                        v_subjseq          = dbox.getString("d_subjseq");
                        v_subjseqgr      = dbox.getString("d_subjseqgr");
                        v_membergubun       = dbox.getString("d_membergubun");
                        v_userid            = dbox.getString("d_userid");
                        v_name            = dbox.getString("d_name");
                        v_appdate          = dbox.getString("d_appdate");
                        v_edustart        = dbox.getString("d_edustart");
                        v_eduend            = dbox.getString("d_eduend");
                        v_chkfirst        = dbox.getString("d_chkfirst");
                        v_chkfinal        = dbox.getString("d_chkfinal");
                        v_email          = dbox.getString("d_email");
                        v_ismailing      = dbox.getString("d_ismailing");
                        v_isnewcourse      = dbox.getString("d_isnewcourse");
                        v_isonoff          = dbox.getString("d_isonoff");
                        v_isclosed        = dbox.getString("d_isclosed");
                        v_grdvalue        = dbox.getString("d_grdvalue");
                        v_rejectpossible    = dbox.getString("d_rejectpossible");
                        v_eduterm          = dbox.getString("d_eduterm");
                        v_isgoyong        = dbox.getString("d_isgoyong");
                        v_course            = dbox.getString("d_course");
                        v_courseseq         = dbox.getString("d_courseseq");
                        v_coursenm          = dbox.getString("d_coursenm");
                        v_iscourseYn        = dbox.getString("d_isbelongcourse");
                        v_subjcnt           = dbox.getInt("d_subjcnt");
                        v_score             = dbox.getString("d_score");
                        //v_usercnt     = dbox.getInt("d_usercnt");

                        v_appdate          = FormatDate.getFormatDate(v_appdate,"yyyy/MM/dd HH:mm:ss");
                        v_edustart        = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                        v_eduend            = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

                        if(v_chkfirst.equals("Y"))             {  v_chkfirst_value = "승인";    }
                        else if(v_chkfirst.equals("N"))       {  v_chkfirst_value = "미승인";  }

                        if(v_chkfinal.equals("Y"))             {  v_chkfinal_value = "승인";    }
                        else if(v_chkfinal.equals("B"))       {  v_chkfinal_value = "미처리";  }
                        else if(v_chkfinal.equals("M"))       {  v_chkfinal_value = "-";  }
                        else if(v_chkfinal.equals("N"))       {  v_chkfinal_value = "<font color=red>반려</font>";  }

                        //String v_eduendDate  = FormatDate.getFormatDate(dbox.getString("d_eduend"),"yyyyMMdd");
                        //String v_date = FormatDate.getDate("yyyyMMdd");

                        //if (!v_eduendDate.equals(""))
                        //if (Integer.parseInt(v_date) > Integer.parseInt(v_eduendDate)) is_complete = 1;

                        //if (!v_isclosed.equals("Y")) is_complete = 1;

                        //if(v_grdvalue.equals("Y"))               {  v_grd_value = "수료"; }
                        //else if(v_grdvalue.equals("M"))         {  v_grd_value = "미처리";  }
                        //else       {  v_grd_value = "미수료";  }
                        //if(is_complete > 0 && v_rejectpossible.equals("Y")) {
                        //  v_grd_value = "수료";
                        //} else {
                        //  v_grd_value = "미수료";
                        //}
                        if(v_rejectpossible.equals("Y")) {
                            v_grd_value = "수료";
                        } else {
                            v_grd_value = "미수료";
                        }

                        if(v_isonoff.equals("ON"))             {     v_isonoff_value="사이버";   }
                        else                                     {   v_isonoff_value="집합";     }

                        //회원구분
                        if(v_membergubun.equals("C")) {v_membergubun = "기업";}
                        else if(v_membergubun.equals("U")) {v_membergubun = "대학";}
                        else  {v_membergubun = "개인";}
                    }
               %>
        <tr>

            <td class="table_01"><%=(i+1) %></td>
<% if(v_iscourseYn.equals("Y"))
    {
        if(l == v_subjcnt) v_tmp_subj = "";

        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
        {
            l = 1;
            v_usercnt++;
            %>

            <td class="table_02_2" rowspan="<%=v_subjcnt%>">
                <font class="text_color01"><%=v_coursenm%></font>
                <input type="hidden" name="p_seluserid" value="<%=v_userid%>">
                <input type="hidden" name="p_isonoff" value="<%=v_isonoff%>">
                <input type="hidden" name="p_eduterm" value="<%=v_eduterm%>">
                <input type="hidden" name="p_isclosed" value="<%=v_isclosed%>">
                <input type="hidden" name="p_rejectpossible" value="<%=v_rejectpossible%>">
                <input type="hidden" name="p_chkfinal" value="<%=v_chkfinal%>">
            </td>
            <td class="table_02_2" ><font class="text_color04"><%=v_subjnm %></font></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_membergubun%></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><a href="javascript:whenMemberInfo('<%=v_userid%>', '<%= v_subj %>', '<%= v_subjseq %>', '<%= v_year %>')" class="e"><%= v_userid %></a></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_name%></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_isonoff_value%></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_edustart %>~ <%= v_eduend %></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_appdate %></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_chkfinal_value %></td>
            <td class="table_02_1" ><%=v_grd_value%></td>
            <td class="table_02_1"><%=v_score %></td>
            <td class="table_02_1" rowspan="<%=v_subjcnt%>">
              <input type="checkbox" name="p_checks" value="<%=v_userid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>,<%=v_course%>,<%=v_iscourseYn%>,<%=v_courseseq%>">
            </td>
    <%
            v_tmp_subj = v_course;
        }else{
            l++;
        %>
            <td class="table_02_2"><font class="text_color04"><%=v_subjnm%></font></td>
            <td class="table_02_1"><%=v_grd_value %></td>
<%      }
    } else {
            v_usercnt++;%>

            <td class="table_02_2" colspan="2">
                <font class="text_color04"><%=v_subjnm %></font>
                <input type="hidden" name="p_seluserid" value="<%=v_userid%>">
                <input type="hidden" name="p_isonoff" value="<%=v_isonoff%>">
                <input type="hidden" name="p_eduterm" value="<%=v_eduterm%>">
                <input type="hidden" name="p_isclosed" value="<%=v_isclosed%>">
                <input type="hidden" name="p_rejectpossible" value="<%=v_rejectpossible%>">
                <input type="hidden" name="p_chkfinal" value="<%=v_chkfinal%>">
            </td>
            <td class="table_02_1" ><%=v_membergubun%></td>
            <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>', '<%= v_subj %>', '<%= v_subjseq %>', '<%= v_year %>')" class="e"><%= v_userid %></a></td>
            <td class="table_02_1"><%=v_name%></td>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1"><%=v_isonoff_value%></td>
            <td class="table_02_1"><%=v_edustart %>~ <%= v_eduend %></td>
            <td class="table_02_1"><%=v_appdate %></td>
            <td class="table_02_1"><%=v_chkfinal_value %></td>
            <td class="table_02_1"><%=v_grd_value%></td>
            <td class="table_02_1"><%=v_score %></td>
            <td class="table_02_1">
              <input type="checkbox" name="p_checks" value="<%=v_userid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>,<%=v_course%>,<%=v_iscourseYn%>,<%=v_courseseq%>">
            </td>

<% } %>


        </tr>
         <%
               }
            }
         if(i == 0 && ss_action.equals("go")){ %>
              <tr>
                <td class="table_02_1" colspan="16">등록된 내용이 없습니다</td>
              </tr>
        <% } else{ %>   </table> <% } %>


       <%  if(i > 0){ %>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><!--페이지카운트--></td>
          </tr>
        </table>
        <br>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right">◈<b> TOTAL : <%=v_usercnt%>명</b></td>
          </tr>
        </table>
        <% } %>
      </table>
      <!----------------- 신청명단 조회 끝 ----------------->
     <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
