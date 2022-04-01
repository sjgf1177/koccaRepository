<%
/**
 * file name : za_ExamResult_L.jsp
 * date      : 
 * programmer:
 * function  : 평가문제지 평가점수 조회
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.exam.*" %>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode      = box.getStringDefault("s_grcode", "ALL");           //교육그룹
    String  ss_gyear       = box.getStringDefault("s_gyear", "ALL");            //년도
    String  ss_grseq       = box.getStringDefault("s_grseq", "ALL");            //교육차수
  
    String  ss_subjnm      = box.getStringDefault("s_subjnm","");      
    String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");    //과정&코스
    String  ss_subjseq     = box.getStringDefault("s_subjseq","ALL");

    String ss_lesson       = box.getString("s_lesson");        // 평가일차 
    String ss_examtype     = box.getString("s_examtype");      // 평가분류     
    int ss_papernum        = box.getInt("s_papernum");         // 시험지번호 
    String paperNumStr = String.valueOf(ss_papernum);
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    
    ArrayList  list = (ArrayList)request.getAttribute("ExamResultList");
    ExamResultData  data  = null;  
%>
<html>
<head>
<title>평가점수 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">
<!--
function whenSelection2(p_action, p_type) {
  if (p_type == 'selgubun') {
    document.form1.p_seltext.value = 'ALL';
  }
  whenSelection(p_action);
}  

// 조회 검색
function whenSelection(p_action) {
    v_grcode = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    v_year = document.form1.s_gyear.value;
    v_subj = document.form1.s_subjcourse.value;
    v_subjseq = document.form1.s_subjseq.value;
    v_papernum = document.form1.s_papernum.value;

    if (document.form1.s_grcode.value == '') {
            alert("교육그룹을 선택하세요.");
            return ;
    }
    if (p_action=="go") {
    
        //if (document.form1.s_grseq.value=="----"){
        //    alert("교육차수을 선택하세요");
        //    return;
        //}    
        if (v_year==""){
            alert("연도을 선택하세요");
            return;
        }
        if (v_subj==""){
            alert("과정을 선택하세요");
            return;
        }
        if (v_subjseq==""){
            alert("차수를 선택하세요");
            return;
        }
        if (v_papernum== ""){
            alert("평가를 선택하세요");
            return;
        }
        top.ftop.setPam();
    }
  document.form1.p_process.value = 'ExamResultListPage';
  document.form1.p_action.value = p_action;
  document.form1.submit();
}

// 엑셀보기
function whenExcel() {  
    v_grcode = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
    v_year = document.form1.s_gyear.value;
    v_subj = document.form1.s_subjcourse.value;
    v_subjseq = document.form1.s_subjseq.value;
    v_papernum = document.form1.s_papernum.value;

    if (document.form1.s_grcode.value == "") {
            alert("교육그룹을 선택하세요.");
            return ;
    }
    if (v_year==""){
        alert("연도을 선택하세요");
        return;
    }

    if (v_subj==""){
        alert("과정을 선택하세요");
        return;
    }
    if (v_subjseq==""){
        alert("차수를 선택하세요");
        return;
    }
    if (v_papernum== ""){
        alert("평가를 선택하세요");
        return;
    }      
    //window.self.name = "ResultListExcel";
    //open_window("openExcel","","100","100","800","600","yes","yes","yes","yes","yes");
    //document.form1.target = "openExcel";             
    document.form1.target = "_self";
    document.form1.p_action.value = "go";
    document.form1.p_process.value = 'ResultListExcel';
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

// 재채점 링크
function IndividualResult(p_subj, p_year, p_subjseq, p_lesson, p_examtype, p_branch, p_papernum, p_userid, p_subjnm, p_average, p_personcnt) {
  window.self.name = "winIndividualResult";
  farwindow = window.open("", "openIndividualResult", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form3.target = "openIndividualResult";
  document.form3.action = "/servlet/controller.exam.ExamUserServlet";
  //document.form3.p_process.value = "ExamUserPaperResult";
  document.form3.p_process.value = "ExamUserReRatingSelect";
  
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_gyear.value    = p_year; 
  document.form3.p_subjseq.value = p_subjseq; 
  document.form3.p_lesson.value  = p_lesson; 
  document.form3.p_examtype.value   = p_examtype; 
  document.form3.p_papernum.value= p_papernum; 
  document.form3.p_userid.value  = p_userid;

  document.form3.p_subjnm.value  = p_subjnm; 
  document.form3.p_average.value  = p_average; 
  document.form3.p_personcnt.value  = p_personcnt; 
 
  document.form3.submit();

  farwindow.window.focus();
  document.form3.target = window.self.name;
}


// 재응시 횟수 수정
function UpdateRetry(p_subj, p_year, p_subjseq, p_lesson, p_examtype, p_branch, p_papernum, p_userid, p_subjnm, p_userretry, p_retry) {
  window.self.name = "winUpdateRetry";
  farwindow = window.open("", "openUpdateRetry", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=520, height=300, top=0, left=0");
  document.form3.target = "openUpdateRetry";
  document.form3.action = "/servlet/controller.exam.ExamUserServlet";
  document.form3.p_process.value = "ExamUserRetrySetPage";
  
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_gyear.value    = p_year; 
  document.form3.p_subjseq.value = p_subjseq; 
  document.form3.p_lesson.value  = p_lesson; 
  document.form3.p_examtype.value   = p_examtype; 
  document.form3.p_papernum.value= p_papernum; 
  document.form3.p_userid.value  = p_userid;

  document.form3.p_subjnm.value   = p_subjnm; 
  document.form3.p_userretry.value= p_userretry; 
  document.form3.p_retry.value    = p_retry; 
 
  document.form3.submit();

  farwindow.window.focus();
  document.form3.target = window.self.name;
}


    
// 화면 리로드
function ReloadPage(p_action) {
  document.form1.p_process.value = 'ExamResultListPage';
  document.form1.p_action.value  = p_action;
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
            var param = "type=sqlID&sqlID=selectBox.grSubjList&param=" + grcode;
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
            var grcode = $("#oGrcode").val();
            var gyear = $("#oGyear").val();
            var param = "type=sqlID&sqlID=selectBox.subjSeqList2&param=" + grcode + "," + gyear + "," + $(this).val();
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
        });
        $("#oSubjseq").bind("change", function(){
            var grcode = $("#oGrcode").val();
            var gyear = $("#oGyear").val();
            var subjcourse = $("#oSubjcourse").val();
            var param = "type=sqlID&sqlID=selectBox.paperList&param=" + subjcourse + "," + gyear + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetPaperList
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
        $("#oSubjcourse").append("<option value=\"\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGyear").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }
    /**
     * 교육차수별 과정 selectbox 지정
     */
    function fnSetSubjList( result ) {
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oSubjcourse").append("<option value=\"\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"\">== 전체 ==</option>");


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
        $("#oSubjseq").append("<option value=\"\">== 전체 ==</option>");
        
        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSubjseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }
    /**
     * 조건별 평가 selectbox 지정
     */
    function fnSetPaperList( result ) {
        $("#oPapernum").empty();
        $("#oPapernum").append("<option value=\"\">== 선택 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oPapernum").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/exam/e_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
      <form name="form1" method="post" action="/servlet/controller.exam.ExamResultServlet">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
        <input type="hidden" name="p_orderColumn">
        <input type="hidden" name="p_orderType" value="<%=v_orderType%>">        
        <tr> 
          <td bgcolor="#C6C6C6" align="center"> 
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center"> 
				  <table border="0" cellspacing="0" cellpadding="2" width="99%" class="form_table">
					<tr>
					  <td width="35%" align="left" valign="middle">
                         <font color="red">★</font>교육그룹
                         <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
					  </td>
					  <td width="73%" colspan="2">
                         <font color="red">★</font>연도
<%
    if ( !ss_gyear.equals("") || !ss_grcode.equals("") ) {
%>
                            <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== 선택 ==" type="sqlID" param="<%= ss_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= ss_gyear %>" isLoad="true" />
<%
    } else {
%>
                            <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== 선택 ==" type="sqlID" isLoad="false" />
<%
    }
%>

						<!--<font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, false)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
						<!--input type= "hidden" name="s_grseq" value="0001"-->
						<%//= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
						<%//= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
						<%//= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
					  </td>
					</tr>
					<tr>
					  <td align="left" colspan="2">
                        <font color="red">★</font>과  정
<%
    if ( !ss_grcode.equals("") ) {
%>
                        <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="-- 선택 --" param="<%= ss_grcode%>" type="sqlID" sqlID="selectBox.grSubjList" selectedValue="<%= ss_subjcourse %>" isLoad="true" />
<%
    } else {
%>
                        <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="-- 선택 --" optionTitleValue="ALL" type="sqlID" isLoad="false" />
<%
    }
%>

                        <%//= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				         <font color="red">★</font> 차수
<%
    if ( !ss_subjseq.equals("")) {
%>
                           <kocca:selectBox name="s_subjseq" id="oSubjseq" optionTitle="== 전체 ==" optionTitleValue="" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_subjcourse %>" sqlID="selectBox.subjSeqList2" selectedValue="<%= ss_subjseq %>" isLoad="true" />
<%
    } else {
%>
                           <select name="s_subjseq" id="oSubjseq">
                             <option value="">== 전체 ==</option>
                           </select>
<%
    }
%>

<%   
        int classdepth = conf.getInt("subj.class.depth");
        SelectSubjBean selectSubj = new SelectSubjBean();                    
%>
                <!--        평가일차
                        <select name = "s_lesson"  class="inputpsearch"  onChange = "javascript:whenSelection('change')">-->
                        <%//    ArrayList lessonList = selectSubj.getSubjLesson2(box);    %>
                    <!--        <option value='----' <%// if (box.getString("s_lesson").equals("ALL")) out.println(" selected"); %> >==선택==</option> -->
                        <%      
                            //for(int k = 0; k < lessonList.size(); k++) {
                            //  DataBox dboxlesson = (DataBox)lessonList.get(k);  
                        %>
                        <!--    <option value = "<%//= dboxlesson.getString("d_lesson")%>" <%// if ((box.getString("s_lesson").equals(dboxlesson.getString("d_lesson")))) out.print("selected"); %>><%//= dboxlesson.getString("d_sdesc")%></option>-->
                        <%//  } %>
                <!--        </select>-->
                          <font color="red">★</font> 평가
<%
    if ( !ss_subjseq.equals("") || !ss_subjcourse.equals("----")) {
%>
                           <kocca:selectBox name="s_papernum" id="oPapernum" optionTitle="== 선택 ==" optionTitleValue="" type="sqlID" param="<%= ss_subjcourse + ',' + ss_gyear + ',' + ss_subjseq %>" sqlID="selectBox.paperList" selectedValue="<%= paperNumStr %>" isLoad="true" />
<%
    } else {
%>
                           <select name="s_papernum" id="oPapernum">
                             <option value="">== 선택 ==</option>
                           </select>
<%
    }
%>
                          <%//= ExamResultBean.getPaperSelect (ss_subjcourse, ss_gyear, ss_subjseq, ss_lesson, "s_papernum", ss_papernum, "")%>
					  </td>
					  <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
					  </td>
					</tr>
				  </table>
                </td>
              </tr>
              <tr> 
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </form>  
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <br>
<%
    Vector  v_average = (Vector)request.getAttribute("ExamResultAverage");     
    if(v_average.size() > 0){
%>
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="10%" class="table_title">문제수</td>
          <td class="table_title">총점</td>
          <td class="table_title">대상자수</td>
          <td class="table_title">응시자수</td>
          <td class="table_title">미응시자수</td>
          <td class="table_title">전체평균</td>
        </tr>

        <tr> 
          <td class="table_02_1"><%=(String)v_average.get(0)%></td>
          <td class="table_02_1">100점</td>
          <td class="table_02_1"><%=list.size()%></td>
          <td class="table_02_1"><%=(String)v_average.get(1)%></td>
          <td class="table_02_1"><%=list.size()-Integer.parseInt((String)v_average.get(1))%></td>
          <td class="table_02_1"><%=(String)v_average.get(2)%></td>
        </tr>
      </table>
      <br>
      <br>
<%
    }   
%>
<%
    Vector  v_average1 = (Vector)request.getAttribute("ExamResultAverage");     
    int start = 0;
    int end = 10;
    int rate = 0;
    if(v_average1.size() > 0){
%>      
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="10%" class="table_title">점수분포</td>
          <td colspan="2" class="table_title">인원</td>
        </tr>
<%
        for (int i = 5; i < v_average1.size(); i++){
            rate = (Integer.parseInt((String)v_average1.get(i))/Integer.parseInt((String)v_average.get(1))) * 100;

%>
        <tr> 
          <td class="table_02_1" width = "10%"><%=start%>점 ~ <%=end%>점</td>
          <td class="table_02_1" width="10%"><%=(String)v_average1.get(i)%>명</td>
          <td class="table_02_2" align="left"><img src="/images/admin/research/statistics_line.gif" height="14" width="<%=rate*5%>"><%=rate%>%</td>
        </tr>
<%
            if(i==5){
              start += 11;
            }else{
              start += 10; 
            } 
            end += 10;
        }
%>
      </table><br>

<%
    }   
%>     

      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right" height="20">&nbsp;</td>
            <td align="right">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
            </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
           
<%
    if(list.size()>0){
%>     
        <!--재채점-->  
      <table width="97%" cellspacing="0" cellpadding="5" >        
        <tr> 
          <td align=right valign=middle >
          <a href="javascript:IndividualResult('<%=ss_subjcourse%>','<%=ss_gyear%>','<%=ss_subjseq%>','<%=ss_lesson%>','','','<%=ss_papernum%>','','<%=ss_subjnm%>','0','0')" class="b"><img src="/images/admin/button/btn_exrerating.gif" border="0" align=absmiddle></a>
          (※조회된 학습자들이 재채점됩니다.)
          </td>
        </tr>
      </table>        
<%
    }
%>      
      <!----------------- 평가점수 조회 시작 ----------------->
      <table border=0 cellspacing="1" cellpadding="5" class="table_out">
      <form name="form3" method="post" action="/servlet/controller.exam.ExamUserServlet">
        <input type="hidden" name="p_process"  value="">
        <input type="hidden" name="p_action"   value="">
            <input type="hidden" name="p_subj"     value="">
              <input type="hidden" name="p_gyear"     value="">
              <input type="hidden" name="p_subjseq"  value="">
              <input type="hidden" name="p_lesson"   value="">
              <input type="hidden" name="p_examtype"    value="">
              <input type="hidden" name="p_papernum" value="">
              <input type="hidden" name="p_userid"   value="">
              <input type="hidden" name="p_subjnm"   value="">
              <input type="hidden" name="p_average"   value="">
              <input type="hidden" name="p_personcnt"   value="">
              <input type="hidden" name="p_examtypenm"  value="">
              <input type="hidden" name="p_flag"  value="1">

            <input type="hidden" name="p_userretry"  value="">
            <input type="hidden" name="p_retry"  value="">
            
            </form>  
        <tr> 
          <td colspan="12" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="6%" class="table_title">NO</td>
          <td class="table_title"><a href="javascript:whenOrder('membergubun')" class="e">회원구분</a></td>
          <td class="table_title"><a href="javascript:whenOrder('d.userid')" class="e">ID(재채점)</a></td>
          <td class="table_title"><a href="javascript:whenOrder('d.name')" class="e">성명</a></td>
   <!--   <td class="table_title">과정연도</td>
          <td class="table_title">과정차수</td>-->   				          
          <td class="table_title"><a href="javascript:whenOrder('c.answer')" class="e">상태</a></td>
          <td class="table_title">평가일차</td>  
          <td width="120" class="table_title"><a href="javascript:whenOrder('a.eduend')" class="e">완료일시</a></td>
          <td width="50" class="table_title"><a href="javascript:whenOrder('c.score')" class="e">점수</a></td>
          <td width="60" align=center class="table_title"><a href="javascript:whenOrder('c.userretry')" class="e">사용자<br>응시횟수<br>(재응시)</a></td>          
        </tr>
                
<%  for (int i=0; i<list.size(); i++) {
        data  = (ExamResultData)list.get(i); %>
        <tr> 
          <td class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=data.getMembergubunnm()%></td>
           <td class="table_02_1">
          <% if (data.getStatus().equals("완료")) { 
                    if(v_average.size() > 0){
          %>
          <a href="javascript:IndividualResult('<%=data.getSubj()%>','<%=data.getYear()%>','<%=data.getSubjseq()%>','<%=data.getLesson()%>','<%=data.getExamtype()%>','','<%=data.getPapernum()%>','<%=data.getUserid()%>','<%=data.getSubjnm()%>','<%=(String)v_average.get(2)%>','<%=(String)v_average.get(1)%>')" class="b">
          <%    }  else { %>
          <a href="javascript:IndividualResult('<%=data.getSubj()%>','<%=data.getYear()%>','<%=data.getSubjseq()%>','<%=data.getLesson()%>','<%=data.getExamtype()%>','','<%=data.getPapernum()%>','<%=data.getUserid()%>','<%=data.getSubjnm()%>','0','0')" class="b">
          <% } }%>
          <%=data.getUserid()%>
          <% if (data.getStatus().equals("완료")) { %>
          </a>   
          <% } %>
          </td>
          <td class="table_02_1"><%=data.getName()%></td>
   <!--   <td class="table_02_1"><%//=data.getYear()%></td>
          <td class="table_02_1"><%//=StringManager.cutZero(data.getSubjseqgr())%>차</td> -->
          <td class="table_02_1"><%=data.getStatus()%></td>
		  <td class="table_02_1"><%=data.getLesson()%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEnded(),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=data.getScore()%></td>
          <td class="table_02_1">
          <% if (data.getStatus().equals("완료")) { %>
          <a href="javascript:UpdateRetry('<%=data.getSubj()%>','<%=data.getYear()%>','<%=data.getSubjseq()%>','<%=data.getLesson()%>','<%=data.getExamtype()%>','','<%=data.getPapernum()%>','<%=data.getUserid()%>','<%=data.getSubjnm()%>','<%=data.getUserretry()%>','<%=data.getRetrycnt()%>');" class="b">
          <%=data.getUserretry()%>
          </a>
          <%}else{%>
          <%=data.getRetrycnt()%>
          <%}%>
          (<%=data.getRetrycnt()%>)</td>
        </tr>
             
<%  } %>
      </table>
      <!----------------- 평가점수 조회 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
