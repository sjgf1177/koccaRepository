<%@ page contentType="text/html;charset=MS949"%>
<%@page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.credu.study.*"%>
<%@ page import="com.credu.common.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.system.*"%>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca"%>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select"%>


<%
    //DEFINED class&variable START
			RequestBox box = (RequestBox) request.getAttribute("requestbox");

			String startDate = FormatDate.getRelativeDate(-30);
			String endDate = FormatDate.getDate("yyyyMMdd");

			String ss_grcode = "N000001"; //box.getString("s_grcode");           //교육그룹
			String ss_gyear = "2015"; //box.getString("s_gyear");            //년도
			String ss_grseq = "0017"; //box.getString("s_grseq");            //교육차수
			String ss_upperclass = box.getString("s_upperclass"); //과정대분류
			String ss_middleclass = box.getString("s_middleclass"); //과정중분류
			String ss_lowerclass = box.getString("s_lowerclass"); //과정소분류
			String ss_subjcourse = "0017";//box.getString("s_subjcourse");       //과정&코스
			String ss_subjseq = box.getString("s_subjseq"); //과정 차수

			String v_searchStart = "";
			String v_searchEnd = "";

			v_searchStart = FormatDate.getFormatDate(startDate, "yyyy-MM-dd");
			v_searchEnd = FormatDate.getFormatDate(endDate, "yyyy-MM-dd");

			String v_action = box.getString("s_action");

			ArrayList list = (ArrayList) request.getAttribute("completeList");

			int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
			int v_pageno = box.getInt("p_pageno");
			int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
			String v_subjnm = "";
			String v_membergubun = "";
			String v_userid = "";
			String v_name = "";
			String v_edustart = "";
			String v_edulatest = "";
			String v_eduend = "";
			String v_tstep = "";
			String v_isgraduated = "";

			//DEFINED in relation to select END
			if (v_action.equals("go")) { //go button 선택시만 list 출력
				list = (ArrayList) request.getAttribute("completeList");
			}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="javascript" src="/script/cresys_lib.js"></script>
<link rel="stylesheet" type="text/css"
	href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--

	$(document).ready(function(){
	    $("#p_searchStart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	    $("#p_searchEnd").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	});

	//검색
	function whenSelection(ss_action) {
	    if (ss_action=="go"){
	        top.ftop.setPam();
	        //if (chkParam() == false) {
	        //    return;
	        // }
	    }
	    var st_date = make_date(document.form1.p_searchStart.value);
        var ed_date = make_date(document.form1.p_searchEnd.value);

        if(st_date > ed_date){
	        alert("시작일이 종료일보다 큽니다.");
	        return;
        }
        document.form1.p_stdt.value   = st_date;
        document.form1.p_edt.value   = ed_date;
	    document.form1.s_action.value = ss_action;
	    document.form1.target = "_self";
	    document.form1.action='/servlet/controller.study.SpotStudySubjectListServlet';
	    document.form1.p_process.value = 'SpotStudyListByUser';
	    document.form1.submit();
	}

	function select_tab(){
	    document.form1.target = "_self";
	    document.form1.s_action.value = "";
	    document.form1.p_process.value = "SpotStudyList";
	    document.form1.action = '/servlet/controller.study.SpotStudySubjectListServlet';
	    document.form1.submit();
	}

    function fnSubjSearchByName() {
        $("#oLowerclass").trigger("change");
    }

    var upperClass;
    var middleClass;
    var lowerClass;
    
    $(function() {

        $("#oUpperclass").bind("change", function(){
        	$("#oMiddleclass").val("ALL");
        	
        	$("#oLowerclass").val("ALL");
        	upperClass = $(this).val()
        	var param = "type=sqlID&sqlID=selectBox.subjAttMiddleList&param=" + upperClass;
        	
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
        	middleClass = $(this).val();
            var param = "type=sqlID&sqlID=selectBox.subjAttLowerList&param=" + $("#oUpperclass").val() + "," + middleClass;
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

        $("#oLowerclass").bind("change", function(){

        	upperClass = $("#oUpperclass").val();
        	middleClass = $("#oMiddleclass").val();
        	lowerClass = $("#oLowerclass").val();

        	var subjSearchKey = $("#oSubjSearchKey").val();
            subjSearchKey = (subjSearchKey == "") ? "%" : subjSearchKey;
            var param;
            if(upperClass == 'ALL' && middleClass == 'ALL' && lowerClass == 'ALL'){
            	param = "type=sqlID&sqlID=selectBox.subjList&param=N000001,2015,0017," + subjSearchKey;
            } else if(upperClass != 'ALL' && middleClass == 'ALL' && lowerClass == 'ALL'){
				param = "type=sqlID&sqlID=selectBox.subjUpperList&param=N000001,2015,0017,"+ upperClass + "," + subjSearchKey;
			} else if(upperClass != 'ALL' && middleClass != 'ALL' && lowerClass == 'ALL') {
				param = "type=sqlID&sqlID=selectBox.subjMiddleList&param=N000001,2015,0017,"+ upperClass + "," + middleClass + "," + subjSearchKey;
			} else if(upperClass != 'ALL' && middleClass != 'ALL' && lowerClass != 'ALL'){
				param = "type=sqlID&sqlID=selectBox.subjLowerList&param=N000001,2015,0017,"+ upperClass + "," + middleClass + "," + lowerClass + "," + subjSearchKey;
			} 
			
            
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
    });

    /**
     * 검색조건에서 대분류 선택시 callback function
     */
    function fnSetMiddleClass( result ) {
        $("#oMiddleclass").empty();
        $("#oMiddleclass").append("<option value=\"ALL\">-- 전체 --</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"ALL\">-- 전체 --</option>");

        
        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
		
        var subjSearchKey = $("#oSubjSearchKey").val();
        subjSearchKey = (subjSearchKey == "") ? "%" : subjSearchKey;
        if($("#oUpperclass option:selected").val() == 'ALL'){
        	var param = "type=sqlID&sqlID=selectBox.subjList&param=N000001,2015,0017," + subjSearchKey;
		} else {
			var param = "type=sqlID&sqlID=selectBox.subjUpperList&param=N000001,2015,0017,"+ upperClass + "," + subjSearchKey;
		}

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
    
    }

    /**
     * 검색조건에서 중분류 선택시 callback function
     */
    function fnSetLowerClass( result ) {
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"ALL\">-- 전체 --</option>");
		
    	if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }

        var subjSearchKey = $("#oSubjSearchKey").val();
        subjSearchKey = (subjSearchKey == "") ? "%" : subjSearchKey;

		if($("#oMiddleclass option:selected").val() == 'ALL'){
			var param = "type=sqlID&sqlID=selectBox.subjUpperList&param=N000001,2015,0017,"+ upperClass + "," + subjSearchKey;
		} else {
			var param = "type=sqlID&sqlID=selectBox.subjMiddleList&param=N000001,2015,0017,"+ upperClass + "," + middleClass + "," + subjSearchKey;
		}
        
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
    }

    /**
     * 교육차수별 과정 selectbox 지정
     */
    function fnSetSubjList( result ) {
        $("#oSubjcourse").empty();
        //$("#oSubjseq").empty();
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");
        //$("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oSubjcourse").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

  	//엑셀 출력
    function whenExcel() {

    	var st_date = make_date(document.form1.p_searchStart.value);
        var ed_date = make_date(document.form1.p_searchEnd.value);
        
        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
        }

        document.form1.p_stdt.value   = st_date;
        document.form1.p_edt.value   = ed_date;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.study.SpotStudySubjectListServlet';
        document.form1.p_process.value = 'SpotStudyListByUserExcel';
        document.form1.submit();
    }

    
//-->

</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';"
	oncontextmenu='return false' ondragstart='return false'
	onselectstart='return false'
	style="background: buttonface; margin: 5; margin-top: 2; border-top: 1 solid buttonhighlight; border-left: 1 solid buttonhighlight; border-right: 1 solid buttonshadow; border-bottom: 1 solid buttonshadow; width: 155; display: none; position: absolute; z-index: 99">
</div>
<form name="form1" method="post"><input type="hidden"
	name="p_process" value=""> <input type="hidden" name="s_action"
	value="<%=v_action%>"> <input type="hidden" name="p_stdt"
	value=""> <input type="hidden" name="p_edt" value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0"
	height="663">
	<tr>
		<td align="center" valign="top"><!----------------- title 시작 ----------------->
		<table width="97%" border="0" cellspacing="0" cellpadding="0"
			class=page_title>
			<tr>
				<td><img src="/images/admin/study/s_title02.gif"></td>
				<td align="right"><img
					src="/images/admin/common/sub_title_tail.gif"></td>
			</tr>
		</table>
		<!----------------- title 끝 -----------------> <br>
		<!----------------- form 시작 ----------------->
		<table cellspacing="0" cellpadding="1" class="form_table_out">
			<tr>
				<td bgcolor="#C6C6C6" align="center">

				<table cellspacing="0" cellpadding="0" class="form_table_bg">
					<tr>
						<td height="7"></td>
					</tr>
					<tr>
						<td align="center">
						<table border="0" cellspacing="0" cellpadding="0" width="99%"
							class="form_table">
							<tr>
								<td colspan="2" align="left" valign="middle" rowspan='2'><!------------------- 조건검색 시작 ------------------------->
								<table border="0" cellspacing="0" cellpadding="0" width="99%"
									class="_srchT">
									<colgroup>
										<col style="width: 10%" />
										<col style="width: 25%" />
										<col style="width: 10%" />
										<col style="width: 10%" />
										<col style="width: 10%" />
										<col style="width: 20%" />
										<col style="width: 10%" />
									</colgroup>

									<tr>
										<td class="_tdT">대분류</td>
										<td class="_tdS"><kocca:selectBox name="s_upperclass"
											id="oUpperclass" optionTitleValue="ALL"
											optionTitle="== 전체 ==" type="sqlID"
											sqlID="selectBox.subjAttUpperList"
											selectedValue="<%= ss_upperclass %>" isLoad="true" /></td>
										<td class="_tdT">중분류</td>
										<td class="_tdS">
										<%
										    if (!ss_middleclass.equals("")) {
										%> <kocca:selectBox name="s_middleclass" id="oMiddleclass"
											optionTitleValue="ALL" optionTitle="== 전체 ==" type="sqlID"
											sqlID="selectBox.subjAttMiddleList"
											param="<%= ss_upperclass %>"
											selectedValue="<%= ss_middleclass %>" isLoad="true" /> <%
     } else {
 %> <select name="s_middleclass" id="oMiddleclass">
											<option value="ALL">== 전체 ==</option>
										</select> <%
     }
 %>
										</td>
										<td class="_tdT">소분류</td>
										<td class="_tdS" colspan="2">
										<%
										    if (!ss_lowerclass.equals("")) {
										%> <kocca:selectBox name="s_lowerclass" id="oLowerclass"
											optionTitleValue="ALL" optionTitle="== 전체 ==" type="sqlID"
											sqlID="selectBox.subjAttLowerList"
											param="<%= ss_upperclass + ',' + ss_middleclass %>"
											selectedValue="<%= ss_lowerclass %>" isLoad="true" /> <%
     } else {
 %> <select name="s_lowerclass" id="oLowerclass">
											<option value="ALL">== 전체 ==</option>
										</select> <%
     }
 %>
										</td>
									</tr>
									<tr>
										<%
										    String s_subjsearchkey = box.getString("s_subjsearchkey");
										%>
										<td class="_tdT">과정검색</td>
										<td class="_tdS"><input type="text" id="oSubjSearchKey"
											name="s_subjsearchkey" id="oSubjSearchKey" size="17"
											onkeypress="if(event.keyCode=='13') fnSubjSearchByName();"
											value="<%=s_subjsearchkey%>"> <a
											href="javascript:fnSubjSearchByName()"><img
											src="/images/admin/button/search3_butt.gif" border="0"
											align="absmiddle"></a></td>
										<td class="_tdT">과정</td>
										<td colspan="4" class="_tdS">
										<%
										    if (!ss_subjcourse.equals("")) {
														s_subjsearchkey = (s_subjsearchkey.equals(""))
																? "%"
																: s_subjsearchkey;
										%> <kocca:selectBox name="s_subjcourse" id="oSubjcourse"
											optionTitle="== 전체 ==" optionTitleValue="ALL" type="sqlID"
											param="<%= ss_grcode + ',' + ss_gyear + ',' + ss_grseq + ',' + s_subjsearchkey %>"
											sqlID="selectBox.subjList"
											selectedValue="<%= ss_subjcourse %>" isLoad="true" /> <%
     } else {
 %> <kocca:selectBox name="s_subjcourse" id="oSubjcourse"
											optionTitle="== 전체 ==" optionTitleValue="ALL" type="sqlID"
											isLoad="false" /> <%
     }
 %>
										</td>
									</tr>
									<tr>
										<td width="10%" class="_tdT">기간기준</td>
										<td colspan="4"><select name="p_gubun1">
											<option value="1">학습시작일</option>
											<option value="2">학습종료일</option>
										</select> <input name="p_searchStart" id="p_searchStart" type="text"
											class="datepicker_input1" size="10"
											value="<%=v_searchStart%>"> 일 ~ <input
											name="p_searchEnd" id="p_searchEnd" type="text"
											class="datepicker_input1" size="10" value="<%=v_searchEnd%>">
										일</td>
									</tr>
									<tr>
										<td width="10%" class="_tdT">수료여부</td>
										<td colspan="4" style='width: 10%'><select
											name="p_gubun2">
											<option value="ALL">전체</option>
											<option value="1">수료</option>
											<option value="2">미수료</option>
										</select> &nbsp;&nbsp;&nbsp; 검색구분 <select name="p_gubun3">
											<option value="1">아이디</option>
											<option value="2">성명</option>
										</select> <input name="p_searchTxt" id="p_searchTxt" type="text"
											style="width: 200"></td>
										<td align="right"><%@ include
											file="/learn/admin/include/za_GoButton.jsp"%></td>
									</tr>
								</table>

								</td>
							</tr>
							<tr>
								<td height="5"></td>
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
		<!----------------- form 끝 -----------------> <br>
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
						<td class="black_butt_middle"><a
							href="javascript:select_tab()" class="c">과정별</td>
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
				<td align="right" valign="top"><!----------------- 엑셀출력 버튼 시작 ----------------->
				<table width="97%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width=4></td>
						<td align="right" width="700">
						 <a	href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
					</tr>
					<tr>
						<td height="3"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<!----------------- 과정기준조회 시작 ----------------->
		<table class="table_out" cellspacing="1" cellpadding="5">
			<tr>
				<td colspan="10" class="table_top_line"></td>
			</tr>
			<tr>
				<td class="table_title" width="5%"><b>NO</b></td>
				<td class="table_title" width="17%"><b>과정</b></td>
				<td class="table_title" width="6%"><b>회원구분</b></td>
				<td class="table_title" width="8%"><b>ID</b></td>
				<td class="table_title" width="5%"><b>성명</b></td>
				<td class="table_title" width="9%"><b>학습시작일</b></td>
				<td class="table_title" width="9%"><b>최근학습일</b></td>
				<td class="table_title" width="7%"><b>진도율</b></td>
				<td class="table_title" width="9%"><b>학습종료일</b></td>
				<td class="table_title" width="6%"><b>수료여부</b></td>
			</tr>


			<%
			    if (box.getString("s_action").equals("go")) {
							for (int i = 0; i < list.size(); i++) {
								DataBox dbox = (DataBox) list.get(i);
								v_subjnm = dbox.getString("d_subjnm");
								v_membergubun = dbox.getString("d_membergubun");
								v_userid = dbox.getString("d_userid");
								v_name = dbox.getString("d_name");
								v_edustart = dbox.getString("d_edustart");
								v_eduend = dbox.getString("d_eduend");
								v_tstep = dbox.getString("d_tstep");
								v_edulatest = dbox.getString("d_edulatest");
								v_isgraduated = dbox.getString("d_isgraduated_txt");
								v_edustart = FormatDate.getFormatDate(v_edustart,
										"yyyy.MM.dd");
								v_eduend = FormatDate.getFormatDate(v_eduend, "yyyy.MM.dd");
								v_edulatest = FormatDate.getFormatDate(v_edulatest,
										"yyyy.MM.dd");
			%>
			<tr>
				<td class="table_02_1" align="center"><%=(i + 1)%></td>
				<td class="table_02_1" align="center"><%=v_subjnm%></td>
				<td class="table_02_1" align="center"><%=v_membergubun%></td>
				<td class="table_02_1" align="center"><%=v_userid%></td>
				<td class="table_02_1" align="center"><%=v_name%></td>
				<td class="table_02_1" align="center"><%=v_edustart%></td>
				<td class="table_02_1" align="center"><%=v_edulatest%></td>
				<td class="table_02_1" align="center"><%=v_tstep%></td>
				<td class="table_02_1" align="center"><%=v_eduend%></td>
				<td class="table_02_1" align="center"><%=v_isgraduated%></td>
			</tr>
			<%
			    }
						} else {
			%>
			<tr>
				<td colspan='15' class="table_02_1">등록된 데이터가 없습니다.</td>
			</tr>
			<%
			    }
			%>
		</table>

		<!----------------- total 시작 -----------------> <br>
		<table width="97%" height="26" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right" valign="absmiddle"><%=PageUtil.printPageList(v_totalpage, v_pageno, row)%>
				</td>
			</tr>
		</table>
		<!----------------- total 끝 -----------------></td>
	</tr>

</table>
</form>
<%@ include file="/learn/library/getJspName.jsp"%>
</body>
</html>