<%
/**
 * file name : za_PluralClass_L.jsp
 * date      : 
 * programmer:
 * function  : 복수클래스 구성
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");


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
    String  ss_action    = box.getString("p_action");

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>
<html>
<head>
<title>복수클래스구성</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">
<!--
//검색
function whenSelection(p_action) {
  if (p_action == 'go') {
    if (chkParam() == false) {
      return;
    }
    top.ftop.setPam();
  }

  document.form1.p_action.value = p_action;
  document.form1.submit();
}


// 엑셀보기 
function whenExcel() {
    if (chkParam() == false) {
      return;
    }

						
		document.form1.method = 'post';
		document.form1.target = 'mainFrame';

    document.form1.action='/servlet/controller.study.ClassServlet';
    document.form1.p_process.value = 'ClassListExcel'; 

    document.form1.submit();	       
	
}



// 검색조건 체크
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

//단일클래스로
function goSingleClassList() {
  document.form1.p_process.value= 'listPage';
  document.form1.p_action.value = 'go';
  document.form1.submit();
}

//클랙스 삭제
function whenDelete(p_subj, p_year, p_subjseq) {
  if (confirm("클래스를 삭제하시겠습니까?")) {
    document.form2.p_process.value  = 'deleteClass';
    document.form2.p_subj.value     = p_subj;
    document.form2.p_year.value     = p_year;
    document.form2.p_subjseq01.value= p_subjseq;
    document.form2.submit();
  }
}
//클래스 생성
function whenCreateClass(p_subj, p_year, p_subjseq) {
  var v_elemstring = 'document.form2.p_' + p_subj + p_year + p_subjseq + '.value';
  var v_classcnt =  eval(v_elemstring);
  if (v_classcnt < 1) {
    alert('추가할 CLASS 갯수를 입력하세요');
    return;
  } 
  document.form2.p_process.value  = 'createClass';
  document.form2.p_subj.value     = p_subj;
  document.form2.p_year.value     = p_year;
  document.form2.p_subjseq01.value= p_subjseq;
  document.form2.submit();
}

// 클래스 수정페이지로 이동
function whenUpdatePage(p_subj, p_year, p_subjseq) {
  document.form2.p_process.value  = 'classUpdatePage';
  document.form2.p_subj.value     = p_subj;
  document.form2.p_year.value     = p_year;
  document.form2.p_subjseq01.value= p_subjseq;
  document.form2.submit();
}

//클래스 리스트 조회
function openSubjClass(p_subj, p_year, p_subjseq) {
//  window.self.name = "winSubjClass";
  window.open("", "openSubjClassList", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width=710, height=480, top=0, left=0");
  document.form3.target = "openSubjClassList";
  document.form3.action = "/servlet/controller.study.ClassServlet";
  document.form3.p_process.value = "subjClasslistPage";
  document.form3.p_subj.value    = p_subj;
  document.form3.p_year.value    = p_year;
  document.form3.p_subjseq01.value = p_subjseq;
  document.form3.submit();
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

// 파일로 추가
function insertFileToDB() {
    if(document.form1.p_action.value != "go"){
        alert('먼저 조회해 주십시오');
        return;
    }

//    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 850, height = 670, top=0, left=0");
    document.form1.target = "openWinInsert"
    document.form1.p_process.value = "classFileToDBPage";
    document.form1.action = "/servlet/controller.study.ClassServlet";
    document.form1.submit();

    farwindow.window.focus();
//    document.form1.target = window.self.name;
}


//select box 설정
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
function fnSubjSearchByName() {
 $("#oGrseq").trigger("change");
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
          <td><img src="/images/admin/study/s_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table> 
    <!----------------- title 끝 ----------------->
    <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
      <form name="form1" method="post" action="/servlet/controller.study.ClassServlet">
        <input type="hidden" name="p_process" value="plurallistPage">
        <input type="hidden" name="p_action"  value="<%=ss_action%>">
        <input type="hidden" name="p_orderColumn">
        <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table_bg">
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
          </td>
        </tr>
      </form>
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <br>

      <!----------------- 단일클래스구성, 복수클래스 구성 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
          <td width="125" height="23" align="center" valign="middle">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/images/admin/student/student_black_img1.gif" rowspan="3" width="5" height="23"></td>
                <td background="/images/admin/student/student_black_img3.gif" height="5"></td>
                <td rowspan="3" background="/images/admin/student/student_black_img2.gif" width="5" height="23"></td>
              </tr>
              <tr>
                <td background="/images/admin/student/student_black_img5.gif" align="center" height="16" valign="middle"><b><font color="#FFFFFF"><a href="javascript:goSingleClassList()" class="c">단일클래스구성</a></font></b></td>
              </tr>
              <tr>
                <td background="/images/admin/student/student_black_img4.gif" height="2"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="125">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td background="/images/admin/student/student_blue_img1.gif" rowspan="3" width="5" height="23"></td>
                <td background="/images/admin/student/student_blue_img3.gif" height="5"></td>
                <td rowspan="3" background="/images/admin/student/student_blue_img2.gif" width="5" height="23"></td>
              </tr>
              <tr>
                <td background="/images/admin/student/student_blue_img5.gif" align="center" height="16" valign="middle"><b><font color="#FFFFFF">복수클래스구성</font></b></td>
              </tr>
              <tr>
                <td background="/images/admin/student/student_blue_img4.gif" height="2"></td>
              </tr>
            </table>
          </td>
          <td align="right" valign="top">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>&nbsp;
            <a href="javascript:insertFileToDB()"><img src="/images/admin/button/btn_fileplus.gif" border="0"></a>
          </td>
        </tr>
      </table>

      <!----------------- 단일클래스구성, 복수클래스 구성 끝 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.study.ClassServlet">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="<%=ss_action%>">
        <input type="hidden" name="s_grcode"     value="<%=ss_grcode%>">
        <input type="hidden" name="s_gyear"      value="<%=ss_gyear%>">
        <input type="hidden" name="s_grseq"      value="<%=ss_grseq%>">
        <input type="hidden" name="s_upperclass"     value="<%=ss_upperclass%>">
        <input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>">
        <input type="hidden" name="s_subjseq"    value="<%=ss_subjseq%>">
        <input type="hidden" name="s_comp"       value="<%=ss_company%>">
        <input type="hidden" name="p_subj"       value="">
        <input type="hidden" name="p_year"       value="">
        <input type="hidden" name="p_subjseq01"  value="">
        <tr>
          <td colspan="10" class="table_top_line"></td>
        </tr>
        <!----------------- 복수클래스구성 시작 ----------------->
        <tr>
          <td class="table_title" colspan="2"><a href="javascript:whenOrder('subj')" class="e">과정</a></td>
          <td class="table_title"><a href="javascript:whenOrder('subjseq')" class="e">과정차수</a></td>
          <td class="table_title"><b>교육기간</b></td>
          <td class="table_title"><b>교육생수</b></td>
          <td class="table_title"><b>클래스수</b></td>
          <td class="table_title"><b>미지정 교육생수</b></td>
          <td class="table_title"><b>가용 강사수</b></td>
          <td class="table_title"><b>클래스 추가</b></td>
          <td class="table_title"><b>클래스 수정</b></td>
        </tr>
<%  
	ArrayList  list = (ArrayList)request.getAttribute("ClassList");

    ClassListData data = null;
    Vector  tutor = null;
    TutorSelectData tutordata = null;

if (list.size() != 0 ) {	//내용이 있다면

    for (int i=0; i<list.size(); i++) {
        data  = (ClassListData)list.get(i);

		// 전문가과정여부에 따라 달리 출력
		if(data.getIsbelongcourse().equals("Y")){		//전문가과정일때 - 정렬을 course로 하여 이 안에서 for문으로 속한 과정출력
%>
				<tr>
				  <td class="table_02_2"  rowspan="<%=data.getSubjcnt()%>"><%=data.getCoursenm()%></td>
				  <td class="table_02_2"><a href="javascript:openSubjClass('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')" class='b'><%=data.getSubjnm()%></a></td>
				  <td class="table_02_1"><%=StringManager.cutZero(data.getSubjseqgr())%></td>
				  <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEdustart(),"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(data.getEduend(),"yyyy/MM/dd")%></td>
				  <td class="table_02_1"><%=data.getStudentcnt()%></td>
				  <td class="table_02_1"><%=data.getClasscnt()%></td>
				  <td class="table_02_1"><%=data.getNoassignstudentcnt()%></td>
				  <td class="table_02_1"><%=data.getAvailabletutorcnt()%></td>
		<%          if (data.getClasscnt() > 0) {  %>
				  <td class="table_02_1"><a href="javascript:whenDelete('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')"><img src="/images/admin/button/b_delete.gif" border="0"></a></td>
		<%          } else {  %>
				  <td class="table_02_1">
					<table width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						<td align="center"><input name="p_<%=data.getSubj()+data.getYear()+data.getSubjseq()%>" type="text" class="input" size="3" value="0" maxlength="3"></td>
						<td align="center"><a href="javascript:whenCreateClass('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')"><img src="/images/admin/button/b_modify.gif" border="0"></a></td>
					  </tr>
					</table>
				  </td>
		<%          } %>
				  <td class="table_02_1"><% if (data.getClasscnt() > 0) {  %><a href="javascript:whenUpdatePage('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')" ><img src="/images/admin/button/b_modify.gif" border="0"></a><% } %></td>
				</tr>

<%
			//과정수에 따라서 나머지 속해있는 과정을 출력.
			for( int k = 0;  k < ( data.getSubjcnt() -1 ); k++ ){
				i++;
		        data  = (ClassListData)list.get(i); 
%>

				<tr>
				  <td class="table_02_2"><a href="javascript:openSubjClass('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')" class='b'><%=data.getSubjnm()%></a></td>
				  <td class="table_02_1"><%=StringManager.cutZero(data.getSubjseqgr())%></td>
				  <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEdustart(),"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(data.getEduend(),"yyyy/MM/dd")%></td>
				  <td class="table_02_1"><%=data.getStudentcnt()%></td>
				  <td class="table_02_1"><%=data.getClasscnt()%></td>
				  <td class="table_02_1"><%=data.getNoassignstudentcnt()%></td>
				  <td class="table_02_1"><%=data.getAvailabletutorcnt()%></td>
		<%          if (data.getClasscnt() > 0) {  %>
				  <td class="table_02_1"><a href="javascript:whenDelete('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')"><img src="/images/admin/button/b_delete.gif" border="0"></a></td>
		<%          } else {  %>
				  <td class="table_02_1">
					<table width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						<td align="center"><input name="p_<%=data.getSubj()+data.getYear()+data.getSubjseq()%>" type="text" class="input" size="3" value="0" maxlength="3"></td>
						<td align="center"><a href="javascript:whenCreateClass('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')"><img src="/images/admin/button/b_modify.gif" border="0"></a></td>
					  </tr>
					</table>
				  </td>
		<%          } %>
				  <td class="table_02_1"><% if (data.getClasscnt() > 0) {  %><a href="javascript:whenUpdatePage('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')" ><img src="/images/admin/button/b_modify.gif" border="0"></a><% } %></td>
				</tr>

<%
		}//for문

	}else{	// 일반과정이면
%>

				<tr>
				  <td class="table_02_2" colspan="2"><a href="javascript:openSubjClass('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')" class='b'><%=data.getSubjnm()%></a></td>
				  <td class="table_02_1"><%=StringManager.cutZero(data.getSubjseqgr())%></td>
				  <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEdustart(),"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(data.getEduend(),"yyyy/MM/dd")%></td>
				  <td class="table_02_1"><%=data.getStudentcnt()%></td>
				  <td class="table_02_1"><%=data.getClasscnt()%></td>
				  <td class="table_02_1"><%=data.getNoassignstudentcnt()%></td>
				  <td class="table_02_1"><%=data.getAvailabletutorcnt()%></td>
		<%          if (data.getClasscnt() > 0) {  %>
				  <td class="table_02_1"><a href="javascript:whenDelete('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')"><img src="/images/admin/button/b_delete.gif" border="0"></a></td>
		<%          } else {  %>
				  <td class="table_02_1">
					<table width="100%" height="26" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						<td align="center"><input name="p_<%=data.getSubj()+data.getYear()+data.getSubjseq()%>" type="text" class="input" size="3" value="0" maxlength="3"></td>
						<td align="center"><a href="javascript:whenCreateClass('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')"><img src="/images/admin/button/b_modify.gif" border="0"></a></td>
					  </tr>
					</table>
				  </td>
		<%          } %>
				  <td class="table_02_1"><% if (data.getClasscnt() > 0) {  %><a href="javascript:whenUpdatePage('<%=data.getSubj()%>', '<%=data.getYear()%>', '<%=data.getSubjseq()%>')" ><img src="/images/admin/button/b_modify.gif" border="0"></a><% } %></td>
				</tr>


<%
		}

	}//for문

}else{		//내용이 없다면
%>

		<tr>
           <td align="center" bgcolor="#F7F7F7" height="50" colspan="11">등록된 내용이 없습니다</td>
        </tr>



<%
}
%>
      <!----------------- 복수클래스구성 끝 ----------------->
      </form>
      </table>
      <br>
    </td>
  </tr>
<form name="form3" method="post" action="/servlet/controller.study.ClassServlet">
  <input type="hidden" name="p_process" value="">
  <input type="hidden" name="p_subj"    value="">
  <input type="hidden" name="p_year"    value="">
  <input type="hidden" name="p_subjseq01" value="">
</form>
</table>
  <%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
