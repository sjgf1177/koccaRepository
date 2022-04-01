<%
//**********************************************************
//  1. 제      목: 수료처리 리스트
//  2. 프로그램명: za_Finish_L1.java
//  3. 개      요: 수료처리 리스트 및 처리
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 06. 20
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
	String  v_action       = box.getString("p_action");
    String  ss_gubun        = box.getString("s_gubun");
    String  ss_biyong        = box.getString("s_biyong");
    String  ss_pageing        = box.getString("s_pageing");

    String s_gadmin     = box.getSession("gadmin"); // 권한

	ArrayList list = null;

	if (v_action.equals("go")) {
    	list = (ArrayList)request.getAttribute("CompleteList");
    }

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String v_orderColumn   = box.getString("p_orderColumn"); //정렬순서필드

	int is_complete		= 0;    // 수료여부
	String v_subjnm		= "";
	String v_isclosed	= "";
	String v_approval	= "";
	String v_approvalstatus	= "";
	String v_isonoff	= "";
	String v_subj		= "";
	String v_year		= "";
	String v_subjseq	= "";
	String v_stolddate	= ""; // 수료처리일
	String v_isoutsrc	= ""; // 외주업체
	String v_subjseqgr	= "";
	String v_eduend		= "";
	String v_date		= "";
	String v_course		= "";
	String v_coursenm	= "";
	String v_courseseq	= "";
	String v_tmp_subj	= "";
	String v_iscourseYn	= "";
	String v_finalString = "";
	String v_suryo_string = "";
    String v_suryo_string_inja="";
	String v_course_string = "";

	int		v_subjcnt	= 0;
	int		v_usercnt	= 0;
	int		l			= 0;
	int		v_student  = 0;
	int		v_complete_cnt = 0;
	int		v_course_stold_usercnt = 0;

    long sum1=0; //교육인원 총계
    long sum2=0; //수료인원 총계

    //페이징
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

%>
<html>
<head>
<title>수료처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script language="JavaScript">
    $(document).ready(function(){
        $("#allChk").bind("click",function(){
            var chk=$(this).attr("checked");
            $("[name='chkbox']").each(function(){
                var r=$(this).val();
                var r_split=r.split(",")[4];
                if(r!="" && r_split!=0)
                    $(this).attr("checked",chk);
            })
        });
    });

    //수료 일괄처리
    function whenAllProcess(G)
    {
        var sw=0;
        var hwnd=$("#tb1");
        var img_filename="";
        var chk_count=0;
        $("tr",hwnd).each(function(i){
            var tr_hwnd=$("input:checkbox",this);
            var chk=$(tr_hwnd).attr("checked");
            var val=$(tr_hwnd).val();
            if(chk)
            {
                chk_count++;       /*
                if(img_filename=="")
                    img_filename=$("img",this).attr("src");
                alert("i="+i+"    "+img_filename="+img_filename + "    $("img",this).attr("src")="+$("img",this).attr("src"));
                if(img_filename!=$("img",this).attr("src"))
                {
//                    alert("선택한 수료과정중에 수료완료와 수료처리 두개가 존재 합니다.\n 하나의 종류만 선택해주세요(row="+i+")");
                    alert(img_filename)
                    alert($("img",this).attr("src"))
                    sw=1;
                    return false;
                }                    */

                var val_split=val.split(",")[4];
                if(val_split==0)
                {
                    alert("선택한 수료과정중에 교육인원이 0인 과정이 존재 합니다.\n 체크를 삭제 합니다.");
                    $(tr_hwnd).attr("checked",false);
                    sw=1;
                    return false;
                }
            }
        });

        if(sw==1)
            return;

        if(chk_count==0)
        {
            alert("선택한 과정이 존재하지 않습니다.\n 적어도 하나 이상의 과정을 선택하여 주세요.");
            return;
        }

		if(G == 'N'){
        	if (!confirm("수료처리를 하시겠습니까?")) return;
      		document.form1.p_process.value = 'totalSoryo';
      		document.form1.submit();
		}else{
			if (!confirm("점수재계산을 하시겠습니까?")) return;
      		document.form1.p_process.value = 'totalsocrecal';
      		document.form1.submit();
		}
    }
<!--
// 오픈 창
function open_window(name, url, left, top, width, height, scrollbar, toolbar, menubar, statusbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }


    // 조회(GO버튼) 실행
    function whenSelection(p_action) {
        if (p_action=="go"){
            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
                alert("교육그룹을 선택하세요.");
                return;
            }
            top.ftop.setPam();
        }
        document.form1.target = "_self";
        document.form1.p_pageno.value = "1";
        document.form1.p_process.value = 'listPage';
        document.form1.p_action.value = p_action;
        document.form1.submit();
    }

    //페이지 이동
    function go(index) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = index;
        document.form1.action = "/servlet/controller.complete.FinishServlet";
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }
    //페이지 이동
    function goPage(pageNum) {
        document.form1.p_action.value = "go";
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.complete.FinishServlet";
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }


    // 엑셀보기
    function whenExcel() {
        if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return;
        }

        document.form1.method = 'post';
        document.form1.target = 'mainFrame';

        //엑셀출력시 모든 레코드가 나오도록 하기 위해 사이즈, 넘버 임시저장
        var temp_pagesize = document.form1.p_pagesize.value;
        var temp_pageno   = document.form1.p_pageno.value;

        //엑셀출력시 모든 레코드가 나오도록 하기 위해 사이즈, 넘버변경
        document.form1.p_pagesize.value = 60000;
        document.form1.p_pageno.value = 1;

        //window.self.name = "FinishList";
        //open_window("openExcel","","100","0","600","400","yes","yes","yes","yes","yes");
        //document.form1.target = "openExcel";
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.complete.FinishServlet';
        document.form1.p_process.value = 'ListExcel';
        document.form1.submit();

        //화면출력시 원래 레코드가 나오도록 하기 위해 사이즈, 넘버 원래대로 수정
        document.form1.p_pagesize.value = temp_pagesize;
        document.form1.p_pageno.value = temp_pageno;
    }



    // 수료처리 신청
    function whenComplete(p_subj, p_year, p_subjseq, p_isonoff, p_student, p_isoutsrc) {
        if (p_student==0) {
            alert("수강생이 없습니다.");
            return;
        }
        if (confirm("수료처리를 하시겠습니까?")) {
            document.form1.target = "_self";
            if(p_isoutsrc == "N"){
                if(p_isonoff == "ON"){
                    document.form1.p_process.value = 'subjectComplete';  // 사이버
                }else{
                    document.form1.p_process.value = 'offSubjectCompleteNew';  // 집합
                }
            }else{
                document.form1.p_process.value = 'outSubjIsClosed';  // 위탁
                document.form1.p_rprocess.value = 'listPage';  // 위탁
            }

            document.form1.p_subj.value = p_subj;
            document.form1.p_year.value = p_year;
            document.form1.p_subjseq.value = p_subjseq;
            document.form1.submit();
        }
    }


    // 과정 점수재계산
    function ReRating(p_subj, p_year, p_subjseq, p_isonoff, p_student) {
        if (p_student==0) {
            alert("수강생이 없습니다.");
            return;
        }
        if (confirm("점수재계산 하시겠습니까?")) {
            document.form1.target = "_self";

            if(p_isonoff == "ON"){
                document.form1.p_process.value = 'subjectCompleteRerating';        // 사이버
            }else{
                document.form1.p_process.value = 'offSubjectCompleteRerating';     // 집합
            }

            document.form1.p_subj.value = p_subj;
            document.form1.p_year.value = p_year;
            document.form1.p_subjseq.value = p_subjseq;
            document.form1.submit();
        }
    }

    // 과정 점수재계산
    function CourseReRating(p_course, p_cyear, p_courseseq) {

        if (confirm("점수재계산 하시겠습니까?")) {
            document.form1.target = "_self";

            /*if(p_isonoff == "ON"){
            document.form1.p_process.value = 'subjectCompleteRerating';        // 사이버
            }else{
            document.form1.p_process.value = 'offSubjectCompleteRerating';     // 집합
            }*/

            document.form1.p_process.value = 'CourseCompleteRerating';

            document.form1.p_course.value    = p_course;
            document.form1.p_cyear.value     = p_cyear;
            document.form1.p_courseseq.value = p_courseseq;
            document.form1.submit();
        }
    }


    // 수료처리 취소 신청
    function whenCancel(p_subj, p_year, p_subjseq, p_process, p_student, p_coursestold_usernct) {
        if (p_student==0) {
            alert("수강생이 없습니다.");
            return;
        }
        if(p_coursestold_usernct != 0){
            alert("전문가 과정 수료 취소를 먼저 해주세요.");
            return;
        }
        if(!document.form1.p_padmin.value == 'A1'){
            alert("수료처리를 취소할 권한이 없습니다.");
            return;
        }
        if (confirm("수료처리를 취소 하시겠습니까?")) {
            document.form1.target = "_self";
            document.form1.p_process.value = p_process;

            document.form1.p_subj.value = p_subj;
            document.form1.p_year.value = p_year;
            document.form1.p_subjseq.value = p_subjseq;
            document.form1.submit();
        }
    }

    // 과정 수료처리
    function whenScoreCompute(p_subj, p_year, p_subjseq) {
        if (confirm("수료처리를 하시겠습니까?")) {
            document.form1.target = "_self";
            document.form1.p_process.value = 'scoreCompute';
            document.form1.p_subj.value = p_subj;
            document.form1.p_year.value = p_year;
            document.form1.p_subjseq.value = p_subjseq;
            //document.form1.submit();
        }
    }

    // 코스과정 수료처리
    function whenCourseCompute(p_course, p_cyear, p_courseseq) {
        if (confirm("수료처리를 하시겠습니까?")) {
            document.form1.target = "_self";
            document.form1.p_process.value   = 'courseScoreCompute';
            document.form1.p_course.value    = p_course;
            document.form1.p_cyear.value     = p_cyear;
            document.form1.p_courseseq.value = p_courseseq;
            document.form1.submit();
        }
    }

    // 코스과정 수료처리 취소
    function whenCourseCancel(p_course, p_cyear, p_courseseq) {
        if (confirm("수료처리 취소를 하시겠습니까?")) {
            document.form1.target = "_self";
            document.form1.p_process.value   = 'courseScoreCancel';
            document.form1.p_course.value    = p_course;
            document.form1.p_cyear.value     = p_cyear;
            document.form1.p_courseseq.value = p_courseseq;
            document.form1.submit();
        }
    }

    // 수료처리 상세화면으로 이동
    function selectSubj(subj, year, subjseq, isclosed, subjnm, isonoff, isapproval, subjseqgr, stolddate) {
        document.form1.target = "_self";
        document.form1.p_process.value = "subjectSelect";
        document.form1.p_subj.value    = subj;
        document.form1.p_year.value    = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_isclosed.value = isclosed;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_isonoff.value = isonoff;
        document.form1.p_isapproval.value = isapproval;
        document.form1.p_subjseqgr.value = subjseqgr;
        document.form1.p_stolddate.value = stolddate;
        document.form1.p_orderColumn.value = "";
        document.form1.submit();
    }

    function whenApproval(subj,year,subjseq) {
        document.form1.target = "_self";
        document.form1.p_subj.value = subj;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_process.value = "requestApprovalPage";

        window.self.name = "winChiefApproval";
        var url = "/servlet/controller.complete.FinishServlet";
        var new_Open = window.open(url,"proposeChief",'scrollbars=yes,width=550,height=200,scrollbars=no,resizable=no');

        document.form1.target = "proposeChief";
        document.form1.submit();
    }

    // sorting 조회
    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_orderColumn.value = column;
        whenSelection("go");
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
            if ( $(this).val() == "") {
                $("#oGrseq").empty();
                $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");
                return;
            } else {
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
            }
        });

    });

    /**
     * 교육 그룹별 연도 selectbox 지정
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oGyear").append("<option value=\"\">== 선택 ==</option>");
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");

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
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGrseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.complete.FinishServlet">
	<input type="hidden" name="p_process"    value="">
	<input type="hidden" name="p_action"   value="">
    <input type="hidden" name="p_subj"    value="">
    <input type="hidden" name="p_year"    value="">
    <input type="hidden" name="p_subjseq" value="">
    <input type="hidden" name="p_subjseqgr" value="">
    <input type="hidden" name="p_isapproval" value="">
    <input type="hidden" name="p_subjnm"    value="">
    <input type="hidden" name="p_isclosed" value="">
    <input type="hidden" name="p_isonoff" value="">
    <input type="hidden" name="p_stolddate" value="">
    <input type="hidden" name="p_returnurl" value="/servlet/controller.complete.FinishServlet">
    <input type="hidden" name="p_rprocess" value="listPage">

    <input type="hidden" name="p_course" value="">
    <input type="hidden" name="p_courseseq" value="">
    <input type="hidden" name="p_cyear" value="">

	<input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_pagesize"  value="10">

    <input type="hidden" name="p_padmin"  value="<%=s_gadmin %>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/course/co_title10.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!----------------- title 끝 ----------------->
            <br>
            <!-- 교육그룹, 년도 시작 -->
            <table class="form_table_out form_table_bg" cellspacing="0" cellpadding="1" style="width:97%">
                <colgroup>
                    <col style="width:10%;"/>
                    <col style="width:20%;"/>
                    <col style="width:10%;"/>
                    <col style="width:10%;"/>
                    <col style="width:10%;"/>
                    <col style="width:10%;"/>
                    <col style="width:10%;"/>
                </colgroup>
                <tr>
                    <th><font color="red">★</font> 교육그룹</th>
                    <td>
                         <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                    </td>
                    <th>연도</th>
                    <td>
<%
    if ( !ss_gyear.equals("") ||  !ss_grcode.equals("")) {
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
                    <th>교육차수</th>
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
                    <td rowspan="2" align="right">
                         <%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
                    </td>
                </tr>
                <tr>
                    <th>교육구분</th>
                    <td>
                        <select name="s_gubun">
                            <option value="T" <% if(ss_gubun.equals("T")) out.print("selected"); %>>전체</option>
                            <option value="G0" <% if(ss_gubun.equals("G0")) out.print("selected"); %>>게임</option>
                            <option value="B0" <% if(ss_gubun.equals("B0")) out.print("selected"); %>>방송</option>
                            <option value="K0" <% if(ss_gubun.equals("K0")) out.print("selected"); %>>문콘</option>
                        </select>
                    </td>
                    <td colspan="4">
                        페이징처리안함 <input name="s_pageing" checked="checked" type="checkbox">
                    </td>
                </tr>
            </table>
            <!-- 교육그룹, 년도 끝 -->
            <br>
            <table width="97%" border=0  cellspacing="0" cellpadding="0" >
                <tr>
                    <td align=right>
                        <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0" alt="엑셀출력"></a>&nbsp;
                        <span id="manysuryo"><a href="javascript:whenAllProcess('N')"><img src="/images/admin/button/btn_suryocancle.jpg"  border="0" alt="일괄수료처리"></a>&nbsp;
                        <a href="javascript:whenAllProcess('Y')"><img src="/images/admin/button/btn_rerating.gif" border=0 alt="점수재계산"></a>&nbsp;</span>
                    </td>
                </tr>
            </table>

            <!----------------- 수료처리 시작 ----------------->
            <table cellspacing="1" cellpadding="5" class="table_out" id="tb1">
                <tr>
                    <td colspan="13" class="table_top_line"></td>
                </tr>
                <tr>
                    <td rowspan="2" width="5%" class="table_title">NO</td>
                    <!--td rowspan="2" class="table_title" width="40"><a href="javascript:whenOrder('a.isonoff')" class="e">구분</a></td-->
                    <td rowspan="2" colspan="3" class="table_title"><a href="javascript:whenOrder('a.subjnm')" class="e">과정(전문가)</a></td>
                    <td rowspan="2" class="table_title"><a href="javascript:whenOrder('a.subjseqgr')" class="e">차수</a></td>
                    <td rowspan="2" class="table_title" width="90"><a href="javascript:whenOrder('edustart')" class="e">교육기간</a></td>
                    <td colspan="3" class="table_title">인원</td>
                    <td rowspan="2" class="table_title"><a href="javascript:whenOrder('a.isclosed')" class="e">수료처리</a></td>
                    <td rowspan="2" class="table_title" style="border-right: 1px solid #76abe0">전문가과정<br>수료처리</td>
                    <td rowspan="2" class="table_title" style="border-right: 1px solid #76abe0"><input type="checkbox" id="allChk"></td>
                </tr>
                <tr>
                    <td class="table_title"><a href="javascript:whenOrder('studentcnt')" class="e">교육</a></td>
                    <td class="table_title"><a href="javascript:whenOrder('stoldycnt')" class="e">수료</a></td>
                    <td class="table_title"><a href="javascript:whenOrder('stoldncnt')" class="e">미수료</a></td>
                </tr>
<%		if (v_action.equals("go")) {

			if (list.size()==0) { %>
			    <tr>
                    <td colspan=13 class="table_02_1">검색된 과정이 없습니다.</td></tr>
<%			} else {


			for (int i=0; i<list.size(); i++) {
            	DataBox dbox = (DataBox)list.get(i);

            	is_complete = 0;    // 수료여부

            	v_subjnm	= dbox.getString("subjnm");
            	v_isclosed	= dbox.getString("isclosed");
            	v_approval	= dbox.getString("d_approvalstatus");
            	v_student	= dbox.getInt("d_studentcnt");
                sum1+=v_student;
                sum2+=dbox.getInt("d_stoldycnt");
            	v_isonoff	= dbox.getString("d_isonoff");
            	v_subj		= dbox.getString("d_subj");
            	v_year		= dbox.getString("d_year");
            	v_subjseq	= dbox.getString("d_subjseq");
            	v_stolddate = dbox.getString("d_stolddate");		// 수료처리일
            	v_isoutsrc	= dbox.getString("d_isoutsourcing");	// 외주업체
            	v_course	= dbox.getString("d_course");
				v_coursenm	= dbox.getString("d_coursenm");
            	v_courseseq	= dbox.getString("d_courseseq");
				v_iscourseYn= dbox.getString("d_isbelongcourse");
				v_subjseqgr = dbox.getString("d_subjseqgr");
				v_subjcnt	= dbox.getInt("d_subjcnt");
				v_complete_cnt = dbox.getInt("d_coursestoldncnt");
				v_course_stold_usercnt = dbox.getInt("d_coursestoldusercnt");

            	v_eduend  = FormatDate.getFormatDate(dbox.getString("d_eduend"),"yyyyMMdd");
            	v_date    = FormatDate.getDate("yyyyMMdd");

				//페이징
				v_dispnum       = dbox.getInt("d_dispnum");
                v_totalpage     = dbox.getInt("d_totalpage");
                v_rowcount      = dbox.getInt("d_rowcount");

            	if(!v_stolddate.equals(""))  v_stolddate = FormatDate.getRelativeDate(FormatDate.getFormatDate(v_stolddate,"yyyyMMdd"),1); // 수료처리일+1

                // 수료여부
                if (!v_eduend.equals(""))
                    if (Integer.parseInt(v_date) > Integer.parseInt(v_eduend)) is_complete = 1;

				if (v_student==0) {
					v_finalString = "<a href=\"javascript:alert('수강생이 없습니다.');\" class='b'>"+ v_subjnm + "</a>";
				}
				else
				{
					v_finalString = "<a href=\"javascript:selectSubj('" + v_subj + "','" + v_year + "','" + v_subjseq + "','" + v_isclosed + "','" + v_subjnm + "','" + v_isonoff + "','" + v_approvalstatus + "','" + v_subjseqgr + "','" + v_stolddate + "');\" class='b'>"+ v_subjnm + "</a>";

					v_finalString = "<a href=\"javascript:selectSubj('" + dbox.getString("subj") + "','" + dbox.getString("year") + "','" + dbox.getString("subjseq") + "','" + dbox.getString("isclosed") + "','" + v_subjnm +"','" + dbox.getString("isonoff") +"','" + dbox.getString("approvalstatus") +"','" + dbox.getString("subjseqgr") +"','" + v_stolddate +"');\" class='b'>" + dbox.getString("subjnm") + "</a>";
				}
 				
				if (is_complete == 1) {
					// 수료
					if (v_isclosed.equals("Y")){
						//수료처리일+1 까지 수료처리취소 버튼 확인
						if ( !v_stolddate.equals("") && Integer.parseInt(v_stolddate) >= Integer.parseInt(v_date)){
							if(v_isoutsrc.equals("N")){
         						if(v_isonoff.equals("ON")){
                                     v_suryo_string_inja=v_subj + "','" + v_year + "','" + v_subjseq + "','subjectCompleteCancel', " + v_student +"," + v_course_stold_usercnt;
									v_suryo_string = "<a href=\"javascript:whenCancel('" + v_subj + "','" + v_year + "','" + v_subjseq + "','subjectCompleteCancel', " + v_student +"," + v_course_stold_usercnt + ");\"><img src='/images/admin/button/b_surucancel.gif' border=0></a>&nbsp;";
								}else{
                                     v_suryo_string_inja=v_subj + "','" + v_year + "','" + v_subjseq +"','offSubjectCompleteCancel', "+ v_student + "," + v_course_stold_usercnt;
									v_suryo_string = "<a href=\"javascript:whenCancel('" + v_subj + "','" + v_year + "','" + v_subjseq +"','offSubjectCompleteCancel', "+ v_student + "," + v_course_stold_usercnt + ");\"><img src='/images/admin/button/b_surucancel.gif' border=0></a>&nbsp;";
								}
							}else{
                                v_suryo_string_inja=v_subj + "','" + v_year +"','" + v_subjseq + "','subjectCompleteCancel', "+ v_student +"," + v_course_stold_usercnt;
								v_suryo_string = "<a href=\"javascript:whenCancel('" + v_subj + "','" + v_year +"','" + v_subjseq + "','subjectCompleteCancel', "+ v_student +"," + v_course_stold_usercnt + ");\"><img src='/images/admin/button/b_surucancel.gif' border=0></a>&nbsp;";
							}
						}else{
                            v_suryo_string_inja="";
							v_suryo_string = "<img src='/images/admin/button/b_surucomplete.gif' border=0 alt='수료완료'>";
						}

					}else{	// 미수료
						if(v_isoutsrc.equals("N")){
							if(v_isonoff.equals("ON")){
                                v_suryo_string_inja= v_subj + "','" + v_year +"','"+ v_subjseq +"', '"+ v_isonoff +"', "+ v_student + ", '"+ v_isoutsrc;
								v_suryo_string = "<a href=\"javascript:whenComplete('"+ v_subj + "','" + v_year +"','"+ v_subjseq +"', '"+ v_isonoff +"', "+ v_student + ", '"+ v_isoutsrc +"')\"><img src='/images/admin/button/b_suruchri.gif' border=0></a>&nbsp;";
							}else{
                                v_suryo_string_inja=v_subj + "','" + v_year +"','" + v_subjseq +"', '" + v_isonoff +"', " + v_student +", '" + v_isoutsrc;
								v_suryo_string = "<a href=\"javascript:whenComplete('"+ v_subj + "','" + v_year +"','" + v_subjseq +"', '" + v_isonoff +"', " + v_student +", '" + v_isoutsrc +"')\"><img src='/images/admin/button/b_suruchri.gif' border=0></a>&nbsp;";
							}
						}else{
                            v_suryo_string_inja=v_subj +"','" + v_year + "','" + v_subjseq + "', '" + v_isonoff + "', " + v_student +", '" + v_isoutsrc;
							v_suryo_string = "<a href=\"javascript:whenComplete('" + v_subj +"','" + v_year + "','" + v_subjseq + "', '" + v_isonoff + "', " + v_student +", '" + v_isoutsrc +"')\"><img src='/images/admin/button/b_suruchri.gif' border=0></a>&nbsp;";
						}
					}

				}else{
					if (v_isclosed.equals("N")){
						if(v_isoutsrc.equals("N")){
                            v_suryo_string_inja=v_subj + "','" + v_year +"','" + v_subjseq +"', '" + v_isonoff +"', " + v_student +", '" + v_isoutsrc;
							v_suryo_string = "<a href=\"javascript:ReRating('" + v_subj + "','" + v_year +"','" + v_subjseq +"', '" + v_isonoff + "', " + v_student + ")\"><img src='/images/admin/button/b_rerating.gif' border=0 alt='점수재계산'></a>&nbsp;";
						}else{
							v_suryo_string = "-";
						}
					}else{
                        v_suryo_string_inja="";
						v_suryo_string = "<img src='/images/admin/button/b_surucomplete.gif' border=0 alt='수료완료'>";
					}
				}

				// 코스 과정일 경우
				if(v_iscourseYn.equals("Y"))
				{
					if (is_complete == 1)
					{
						out.println("<!-- v_complete_cnt = " + v_complete_cnt + " -->");
						out.println("<!-- v_subjcnt = " + v_subjcnt + " -->");
						out.println("<!-- v_course_stold_usercnt = " + v_course_stold_usercnt + " -->");



						if(v_course_stold_usercnt < 1) //v_complete_cnt == v_subjcnt &&
						{
							v_course_string = "<a href=\"javascript:whenCourseCompute('" + v_course + "','" + v_year + "','" + v_courseseq + "')\"><img src='/images/admin/button/b_suruchri.gif' border=0></a>";
						}
						else if(v_course_stold_usercnt >= 1) //v_complete_cnt == v_subjcnt &&
						{
							v_course_string = "<a href=\"javascript:whenCourseCancel('" + v_course + "','" + v_year + "','" + v_courseseq + "')\"><img src='/images/admin/button/b_surucancel.gif' border=0></a>";
						}
						else
						{
							v_course_string = "<img src='/images/admin/button/b_suruchri.gif' border=0>";
						}
					}
					else
					{
						/*
						if (v_complete_cnt == v_subjcnt && v_course_stold_usercnt < 1)
						{
							if(v_isoutsrc.equals("N")){
								v_course_string = "<a href=\"javascript:CourseReRating('" + v_course + "','" + v_year +"','" + v_courseseq +"')\"><img src='/images/admin/button/b_rerating.gif' border=0 alt='점수재계산'></a>&nbsp;";
							}else{
								v_course_string = "-";
							}
						}else{
							v_course_string = "<img src='/images/admin/button/b_surucomplete.gif' border=0 alt='수료완료'>";
						}
						*/
						v_course_string = "-";
					}
				}
				else
				{
					v_course_string = "-";
				}
%>
                <tr>
                    <td class="table_01" ><%=v_dispnum%></td>
                    <!--td class="table_02_1" >&nbsp;</td-->
                    <!--td class="table_02_1"><%//=dbox.getString("isonoffnm")%></td-->
<% if(v_iscourseYn.equals("Y"))
	{
		if(l == v_subjcnt) v_tmp_subj = "";

		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			l = 1;
			v_usercnt++;
			%>
                    <td class="table_02_2" rowspan="<%=v_subjcnt%>"><%=v_coursenm %></td>
                    <td class="table_02_2" colspan="2"><%=v_finalString %></td><td class="table_02_1"><%=StringManager.cutZero(dbox.getString("subjseqgr"))%></td>
                    <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("edustart"),"yyyy/MM/dd")%>~<br> <%=FormatDate.getFormatDate(dbox.getString("eduend"),"yyyy/MM/dd")%></td>
                    <td class="table_02_1"><%=v_student%></td>		<!--교육-->
                    <td class="table_02_1"><%=dbox.getInt("d_stoldycnt")%></td>			<!--수료-->
                    <td class="table_02_1"><%=dbox.getInt("d_stoldncnt")%></td>			<!--미수료-->
                    <td class="table_03_1"><!-- 1수료처리 --><%=v_suryo_string %></td>
                    <td class="table_02_1" rowspan="<%=v_subjcnt%>" style="border-right: 1px solid #76abe0"><%= v_course_string %></td>
                    <td class="table_02_1" rowspan="<%=v_subjcnt%>" style="border-right: 1px solid #76abe0"><input type="checkbox" name="chkbox" value="<%=v_suryo_string_inja%>"></td>

<%
			v_tmp_subj = v_course;
		}else{
			l++;
		%>
                    <td class="table_02_2" colspan="2"><%=v_finalString %></td><td class="table_02_1"><%=StringManager.cutZero(dbox.getString("subjseqgr"))%></td>
                    <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("edustart"),"yyyy/MM/dd")%>~<br> <%=FormatDate.getFormatDate(dbox.getString("eduend"),"yyyy/MM/dd")%></td>
                    <td class="table_02_1"><%=v_student%></td>		<!--교육-->
                    <td class="table_02_1"><%=dbox.getInt("d_stoldycnt")%></td>			<!--수료-->
                    <td class="table_02_1"><%=dbox.getInt("d_stoldncnt")%></td>			<!--미수료-->
                    <td class="table_02_1"><!-- 1수료처리 --><%=v_suryo_string %></td>
                    <td class="table_03_1" rowspan="<%=v_subjcnt%>" style="border-right: 1px solid #76abe0"><input type="checkbox" name="chkbox" value="<%=v_suryo_string_inja%>"></td>
<%		}
	} else {
			v_usercnt++;%>
                    <td class="table_02_2" colspan="3"><%=v_finalString %></td><td class="table_02_1"><%=StringManager.cutZero(dbox.getString("subjseqgr"))%></td>
                    <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("edustart"),"yyyy/MM/dd")%>~<br> <%=FormatDate.getFormatDate(dbox.getString("eduend"),"yyyy/MM/dd")%></td>
                    <td class="table_02_1"><%=v_student%></td>		<!--교육-->
                    <td class="table_02_1"><%=dbox.getInt("d_stoldycnt")%></td>			<!--수료-->
                    <td class="table_02_1"><%=dbox.getInt("d_stoldncnt")%></td>			<!--미수료-->
                    <td class="table_03_1"><!-- 1수료처리 --><%=v_suryo_string %></td>
                    <td class="table_02_1" style="border-right: 1px solid #76abe0"><%= v_course_string %></td>
                    <td class="table_02_1" rowspan="<%=v_subjcnt%>" style="border-right: 1px solid #76abe0"><input type="checkbox" name="chkbox" value="<%=v_suryo_string_inja%>"></td>
<% } %>

                </tr>
<% 			} // for end
        out.print("<tr>");
    out.print("<td class='table_02_1' colspan='6'>합계</td>");
    out.print("<td class='table_02_1'>"+sum1+"</td>");
    out.print("<td class='table_02_1'>"+sum2+"</td>");
    out.print("<td class='table_02_1'>"+(sum1-sum2)+"</td>");
    out.print("<td class='table_02_1' colspan='3'>&nbsp;</td>");
    out.print("<tr>");
		}
	} // go end
%>
            </table>
<script type="text/javascript">
    if("<%=v_suryo_string_inja%>"=="")
        $("#manysuryo").show();//rsg 20170711
</script>
      <% if(ss_pageing.equals("")) {%>
            <!----------------- 수료처리 끝 ----------------->
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
    <%}%>
        </td>
    </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
