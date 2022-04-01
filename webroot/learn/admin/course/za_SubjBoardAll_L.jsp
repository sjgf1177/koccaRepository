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
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int v_tabseq   = box.getInt("p_tabseq");
    String v_subj   = box.getString("p_subj");
    String v_year	= box.getString("p_year");
    String v_subjseq= box.getString("p_subjseq");

    int v_seq = 0;
    int v_levels = 0;
    int v_cnt = 0;
    int i     = 0;

    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_title  = "";
    String	v_replyTitle= "";

    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    int v_upfilecnt = 0;

    int v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    String v_class1     = "";
    String v_class2     = "";
    
    ArrayList list = (ArrayList)request.getAttribute("selectStudyBoardList");
    
    SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();

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
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  ss_action    = box.getString("s_action");
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

		if (p_action=='go') {
			if (chkParam() == false) {
                return;
            }
			top.ftop.setPam();
			document.form1.p_process.value = 'selectList';
		}
      //if (p_action=='go') {}

        document.form1.p_pageno.value = "1";
		document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
		document.form1.submit();
    }

// 상세보기
function select(seq, upfilecnt, userid, tabseq) {
    document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
    document.form1.p_process.value = "select";
    document.form1.p_seq.value = seq;
    document.form1.p_tabseq.value = tabseq;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.p_userid.value    = userid;
    document.form1.submit();
}

function insert() {
	  if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	  }
	  else if (document.form1.s_subjcourse.value == 'ALL' || document.form1.s_subjcourse.value == '----') {
			alert("과정을 선택하세요.");
			return ;
	  }
	  else if (document.form1.s_subjseq.value == 'ALL' || document.form1.s_subjseq.value == '----') {
			alert("과정차수를 선택하세요.");
			return ;
	  }
    document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
    document.form1.p_process.value = "insertPage";
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
  document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
  document.form1.p_process.value = "selectList";
  document.form1.submit();
}
//페이지 이동
function goPage(pageNum) {
document.form1.p_action.value = "go";
   document.form1.p_pageno.value = pageNum;
   document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
   document.form1.p_process.value = "selectList";
   document.form1.submit();
}

//검색조건 체크
function chkParam() {
  if (document.form1.s_grcode.value == '' || document.form1.s_grcode.value == '----') {
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

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_subj"    value="">
    <input type="hidden" name="p_year"    value="">
    <input type="hidden" name="p_subjseq" value="">
    <input type="hidden" name="p_hsubjnm" value="">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_action"  value="">

    <input type="hidden" name="p_seq"    value="">
    <input type="hidden" name="p_type"    value="SB">
    <input type="hidden" name="p_upfilecnt"    value="">
    <input type="hidden" name="p_userid"    value="">
    <input type="hidden" name="p_tabseq"    value="">

    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->

    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <!-- 
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/homepage/homepage_title12.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
       -->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
       		 <tr>
              <td><img src="/images/admin/course/tit_c_subboard.gif" ></td>
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
			                    <td align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %><a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
                                
                            </tr>	
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

        <!----------------- 공지사항관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" height="50" class="table_title"><b>번호</b></td>
            <td class="table_title" ><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
            <td class="table_title"><a href="javascript:whenOrder('title')" class="e">제목</a></td>
            <td width="7%" class="table_title"><a href="javascript:whenOrder('name')" class="e">작성자</a></td>
            <td width="7%" class="table_title"><a href="javascript:whenOrder('indate')" class="e">작성일자</a></td>
            <td width="7%" class="table_title"><a href="javascript:whenOrder('upfilecnt')" class="e">첨부</a></td>
            <td width="7%" class="table_title"><a href="javascript:whenOrder('dispnum')" class="e">조회수</a></td>
          </tr>
<%
	if(list != null) {
		for(i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);
			v_userid    = dbox.getString("d_userid");
			v_name      = dbox.getString("d_name");
			v_indate    = dbox.getString("d_indate");
			v_title     = dbox.getString("d_title");
			v_seq       = dbox.getInt("d_seq");
			v_cnt       = dbox.getInt("d_cnt");
			v_levels    = dbox.getInt("d_levels");
			v_upfilecnt = dbox.getInt("d_upfilecnt");
			v_dispnum   = dbox.getInt("d_dispnum");
			v_totalpage = dbox.getInt("d_totalpage");
			v_rowcount  = dbox.getInt("d_rowcount");
		    v_replyTitle= "";
		    
	        if(v_levels > 1){
		        for(int j=1;j < v_levels;j++){
		        	v_replyTitle +="&nbsp;&nbsp;";
		        }
		        v_replyTitle += "<img src=\"/images/portal/ico/ico_re.gif\" align=\"absbottom\">";
	        }

		// 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
		if (!v_searchtext.equals("")) {
			if (v_search.equals("name")) {
				v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			} else if (v_search.equals("title")) {
				v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			}
		}
%>
			<tr>
						<td class="table_02_1"><%= v_dispnum %></td>
						<td class="table_02_2"><%= dbox.getString("d_subjnm") %></td>
						<td class="table_02_2"><%= v_replyTitle %><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>', '<%= dbox.getString("d_tabseq") %>')"><%=v_title%></a>
                      <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){ %>
                        	<img src='/images/portal/ico/ico_board_n.gif' align=absbottom/>
                      <%  } %>
						</td>
						<td class="table_02_1"><%= v_name %></td>
						<td class="table_02_1"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
						<td class="table_02_1">
<%		if( v_upfilecnt>0){ %>
							<img src="/images/portal/ico/ico_file.gif" alt="file" />
<%		}%>
						</td>
						<td class="table_02_1"><%= v_cnt %></td>
					</tr>
<%  }
	} else {   %>    
                    <tr> 
                      <td class="table_02_1" colspan="9">등록된 내용이 없습니다.</td>
                    </tr>    
<%    } %>
        </table>
        <!----------------- 공지사항관리 끝 ----------------->
        <br>
        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->

     </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
