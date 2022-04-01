<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 수료현황  - 개인별 현황
//  2. 프로그램명 : za_CoursePassPeson_L.jsp
//  3. 개      요: 수료현황 - 개인별  현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.11
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String	v_userid		= "";
    String	v_name			= "";
    String	v_resno			= "";
    String	v_upperclass	= "";
    String	v_middleclassname	= "";
    String	v_lowerclassname	= "";
    String	v_subj			= "";
    String	v_year			= "";
    String	v_subjnm		= "";
    String	v_subjseq		= "";
    String	v_edustart		= "";
    String	v_eduend		= "";
    String	v_grcode		= "";
    String	v_gyear			= "";
    String	v_grseq			= "";
    String	v_isgraduated	= "";
    double	v_score			= 0;
    double	v_tstep			= 0;
    double	v_mtest			= 0;
    double	v_ftest			= 0;
    double	v_htest			= 0;
    double	v_report		= 0;
    double	v_etc1			= 0;
    double	v_etc2			= 0;
    double	v_avtstep		= 0;
    double	v_avmtest		= 0;
    double	v_avftest		= 0;
    double	v_avhtest		= 0;
    double	v_avreport		= 0;
    double	v_avetc1		= 0;
    double	v_avetc2		= 0;
    int		v_exam_cnt		= 0;
    int		v_sul_cnt1		= 0;
    int		v_sul_cnt2		= 0;
    double	v_sul_rate		= 0;
    int		v_qna_cnt		= 0;           
	
    double	v_total_score	= 0;
    double	v_total_tstep	= 0;
    double	v_total_mtest	= 0;
    double	v_total_ftest	= 0;
    double	v_total_htest	= 0;
    double	v_total_report	= 0;
    double	v_total_etc1	= 0;
    double	v_total_etc2	= 0;
    int		v_total_exam_cnt= 0;
    int		v_total_sul_cnt	= 0;
    int		v_total_sul_cnt1= 0;
    int		v_total_sul_cnt2= 0;
    double	v_total_sul_rate= 0;
    int		v_total_qna_cnt	= 0;           
    
    String	v_total_score_rate		= "";
    String	v_total_tstep_rate		= "";
    String	v_total_mtest_rate		= "";
    String	v_total_ftest_rate		= "";
    String	v_total_htest_rate		= "";
    String	v_total_report_rate		= "";
    String	v_total_etc1_rate		= "";
    String	v_total_etc2_rate		= "";
    String	v_total_exam_cnt_rate	= "";
    String	v_total_sul_cnt_rate	= "";
    String	v_total_sul_cnt1_rate	= "";
    String	v_total_sul_cnt2_rate	= "";
    String	v_total_sul_rate_rate	= "";
    String	v_total_qna_cnt_rate	= "";           
	
    int     i           		= 0;
    
    String	ss_grcode			= box.getString("s_grcode");		//교육그룹
    String	ss_gyear			= box.getString("s_gyear");			//년도
    String	ss_grseq			= box.getString("s_grseq");			//교육차수
    String	ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
    String  ss_option1			= box.getString("s_option1");		//통계구분
    String  ss_option2			= box.getString("s_option2");		//통계별 구분2
    String  ss_option3			= box.getString("s_option3");		//통계별 구분3
    
    if (ss_enddate.equals("")) {
    	ss_startdate	= FormatDate.getDateAdd(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd", "month", -1);
    	ss_enddate		= FormatDate.getFormatDate(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd");
    }
    
    String  v_action	= box.getString("p_action");
	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript">
$(document).ready(function(){
	$("#s_startdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#s_enddate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
// 검색
function whenSelection(ss_action) {
	var process = 'selectPassPersonListPre';            //검색전
  
	if (ss_action=="go")  {
		if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return;
		}
		//if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '') {
		//   alert("교육차수를 선택하세요.");
		//   return;
		//}
		top.ftop.setPam();
		process='selectPassPersonList';   //검색후
	}
	document.form1.target = "_self";  
	document.form1.action = "/servlet/controller.course.CourseStateAdminServlet";
	document.form1.p_action.value = ss_action;
	document.form1.p_process.value = process;
	document.form1.submit();
}

// 정렬
function ordering(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

	document.form1.p_order.value = column;
	whenSelection("go");
}

// 엑셀보기
function whenExcel() {
	if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
        alert("교육그룹을 선택하세요.");
        return ;
    }
    //window.self.name = "CourseSale";
    //open_window("openExcel1","","100","100","800","600","yes","yes","yes","yes","yes");
    //document.form1.target = "openExcel1";
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.course.CourseStateAdminServlet';
    document.form1.p_process.value = 'selectPassPersonListExcel';
	document.form1.p_action.value = "go";
    document.form1.submit();
}

// 날짜 삭제(달력)
function whenclick(caldate) {
	caldate.value = "";
}

// 통계변경1
function selectOption1(p) {
	//alert(p);
	document.form1.target = "_self";  
	document.form1.action = "/servlet/controller.course.CourseStateAdminServlet";
	document.form1.p_action.value = "change";
	document.form1.p_process.value = p;
	document.form1.submit();
}

//통계변경2
function selectOption2(p) {
	document.form1.target = "_self";  
	document.form1.action = "/servlet/controller.course.CourseStateAdminServlet";
	document.form1.p_action.value = "change";
	document.form1.p_process.value = p;
	document.form1.submit();
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
          <td><img src="/images/admin/course/co_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!------------------------------------- form 시작 ------------------------------------->
      <form name = "form1" method = "post">
        <input type="hidden" name="p_process">
        <input type="hidden" name="p_action">
        <input type="hidden" name="p_order"     value="">
        <input type="hidden" name="p_orderType" value="">
        <input type="hidden" name="p_grcode" >
        <input type="hidden" name="p_grseq" >
        <input type="hidden" name="p_gyear">
        <input type="hidden" name="p_subj" >
        <input type="hidden" name="p_subjseq" >
        <input type="hidden" name="p_option3" value="">
      
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
                      <td align="left" valign="middle">
                        <!------------------- 조건검색 시작 ------------------------->
                        <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                      </td>
                      <td align="left" valign="middle">
                        <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                      </td>
                      <td align="left" valign="middle">  
                        <font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                      </td>
                    </tr>
                    <tr>
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr>
                      <td align="left" valign="middle">
                        <%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                      </td>
                      <td align="left" valign="middle">
                        <%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                      </td>
                      <td align="left" valign="middle">
                        <%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                      </td>
  		  		      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="5" colspan="4"></td>
                    </tr>
  		  		    <tr>
                      <td align="left" valign="middle">
                                                          조회기간&nbsp;<input name="s_startdate" id="s_startdate" value="<%=ss_startdate%>" type="text" class="datepicker_input1" size="10">&nbsp;
		  				  			~&nbsp;<input name="s_enddate" id="s_enddate" value="<%=ss_enddate%>" type="text" class="datepicker_input1" size="10">&nbsp;
                      </td>
                      <td align="left" valign="middle">
                                                         통계구분
                        <kocca_select:select name="s_option1" sqlNum="coursestatistics1"  param=" " onChange="selectOption1(this.value);" attr="통계구분1" selectedValue="selectPassAllListPre" isLoad="true" all="none" type="1" styleClass=""/>
                      </td>
                      <td align="left" valign="middle">
                                                          구분
		  		        <select name="s_option2" onChange="selectOption2(this.value);">
		  				  <option value="selectPassAllListPre"    <%= "selectPassAllListPre".equals(ss_option2) ? " SELECTED" : "" %>>전체</option>
		  				  <option value="selectPassCourseListPre" <%= "selectPassCourseListPre".equals(ss_option2) ? " SELECTED" : "" %>>과정별</option>
		  				  <option value="selectPassPersonListPre" <%= "selectPassPersonListPre".equals(ss_option2) ? " SELECTED" : "" %>>개인별</option>
				        </select>
                      </td>
                      <td width="20%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></div><!-- whenSelection('go') --></td>
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
      </table><br>
      <!----------------- 엑셀출력 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>개인별 수료현황</b></td>
          <td align="right">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>           
      <!----------------- 통계조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="23" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title"><b>No</b></td>
          <td class="table_title"><b>성명</b></td>
          <td class="table_title"><b>아이디</b></td>
          <td class="table_title"><b>주민번호</b></td>
          <td class="table_title"><b>분야</b></td>
          <td class="table_title"><b>난이도</b></td>
          <td class="table_title"><b>교육기간</b></td>
          <td class="table_title"><b>과정명</b></td>
          <td class="table_title"><b>진도율(%)</b></td>
          <td class="table_title"><b>리포트</b></td>
          <td class="table_title"><b>중간평가</b></td>
          <td class="table_title"><b>최종평가</b></td>
          <td class="table_title"><b>형성평가</b></td>
          <td class="table_title"><b>참여도</b></td>
          <td class="table_title"><b>기타</b></td>
          <td class="table_title"><b>총점</b></td>
          <td class="table_title"><b>평가응시수</b></td>
          <td class="table_title"><b>설문응시수</b></td>
          <td class="table_title"><b>수료여부</b></td>
          <td class="table_title"><b>사전설문</b></td>
          <td class="table_title"><b>고객만족도</b></td>
          <td class="table_title"><b>만족도</b></td>
          <td class="table_title"><b>나의상담내역</b></td>
        </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

				v_userid			= dbox.getString("d_userid");
			    v_name				= dbox.getString("d_name");
			    v_resno				= dbox.getString("d_resno");
			    v_grcode			= dbox.getString("d_grcode");
				v_gyear				= dbox.getString("d_gyear");
				v_grseq				= dbox.getString("d_grseq");
				v_upperclass		= dbox.getString("d_upperclass");
				v_middleclassname	= dbox.getString("d_middleclassname");
				v_lowerclassname	= dbox.getString("d_lowerclassname");
				v_edustart			= dbox.getString("d_edustart");
				v_eduend			= dbox.getString("d_eduend");
				v_subj				= dbox.getString("d_subj");
				v_year				= dbox.getString("d_year");
				v_subjseq			= dbox.getString("d_subjseq");
				v_subjnm			= dbox.getString("d_subjnm");
			    v_isgraduated		= dbox.getString("d_isgraduated");
			    v_score				= dbox.getDouble("d_score");
			    v_tstep				= dbox.getDouble("d_tstep");
			    v_mtest				= dbox.getDouble("d_mtest");
			    v_ftest				= dbox.getDouble("d_ftest");
			    v_htest				= dbox.getDouble("d_htest");
			    v_report			= dbox.getDouble("d_report");
			    v_etc1				= dbox.getDouble("d_etc1");
			    v_etc2				= dbox.getDouble("d_etc2");
			    v_avtstep			= dbox.getDouble("d_avtstep");
			    v_avmtest			= dbox.getDouble("d_avmtest");
			    v_avftest			= dbox.getDouble("d_avftest");
			    v_avhtest			= dbox.getDouble("d_avhtest");
			    v_avreport			= dbox.getDouble("d_avreport");
			    v_avetc1			= dbox.getDouble("d_avetc1");
			    v_avetc2			= dbox.getDouble("d_avetc2");
			    v_exam_cnt			= dbox.getInt("d_exam_cnt");
			    v_sul_cnt1			= dbox.getInt("d_sul_cnt1");
			    v_sul_cnt2			= dbox.getInt("d_sul_cnt2");
			    v_sul_rate			= dbox.getDouble("d_sul_rate");
			    v_qna_cnt			= dbox.getInt("d_qna_cnt");       
			    
			    v_total_score		+= v_score;	 
			    v_total_tstep		+= v_tstep;
			    v_total_mtest		+= v_mtest;
			    v_total_ftest		+= v_ftest;	
			    v_total_htest		+= v_htest;	
			    v_total_report		+= v_report;	
			    v_total_etc1		+= v_etc1;	
			    v_total_etc2		+= v_etc2;	
			    v_total_exam_cnt	+= v_exam_cnt;
			    v_total_sul_cnt		+= v_sul_cnt1 + v_sul_cnt2;
			    v_total_sul_rate	+= v_sul_rate;
			    v_total_qna_cnt		+= v_qna_cnt;	
%>
        <tr class="table_02_1">
          <td class="table_02_1"><%= i + 1 %></td>
          <td class="table_02_1"><%= v_name %></td>
          <td class="table_02_1"><%= v_userid %></td>
          <td class="table_02_1"><%= (v_resno.length()) > 12 ? v_resno.substring(0,6) + "-" + v_resno.substring(6,13) : v_resno %></td>
          <td class="table_02_1"><%= v_middleclassname %></td>
          <td class="table_02_1"><%= v_lowerclassname %></td>
          <td class="table_02_1"><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
          <td class="table_02_2"><%= v_subjnm %></td>
          <td class="table_02_1"><%= v_tstep %>%</td>
          <td class="table_02_1"><%= v_report %></td>
          <td class="table_02_1"><%= v_mtest %></td>
          <td class="table_02_1"><%= v_ftest %></td>
          <td class="table_02_1"><%= v_htest %></td>
          <td class="table_02_1"><%= v_etc1 %></td>
          <td class="table_02_1"><%= v_etc2 %></td>
          <td class="table_02_1"><%= v_score %></td>
          <td class="table_02_1"><%= v_exam_cnt %></td>
          <td class="table_02_1"><%= v_sul_cnt1 + v_sul_cnt2 %></td>
          <td class="table_02_1"><%= v_isgraduated %></td>
          <td class="table_02_1"><%= (v_sul_cnt1 > 0) ? "Y" : "N" %></td>
          <td class="table_02_1"><%= (v_sul_cnt2 > 0) ? "Y" : "N" %></td>
          <td class="table_02_1"><%= v_sul_rate %>%</td>
          <td class="table_02_1"><%= v_qna_cnt %></td>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="23">등록된 내용이 없습니다.</td>
        </tr>
<%			} else { 
				    v_total_score_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_score	 / i );
				    v_total_tstep_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_tstep	 / i );
				    v_total_mtest_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_mtest	 / i );
				    v_total_ftest_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_ftest	 / i );
				    v_total_htest_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_htest	 / i );
				    v_total_report_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_report	 / i );
				    v_total_etc1_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_etc1	 / i );
				    v_total_etc2_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_etc2	 / i );
				    v_total_exam_cnt_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_exam_cnt / i );
				    v_total_sul_cnt_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt / i );
				    v_total_sul_cnt1_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt1 / i );
				    v_total_sul_cnt2_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt2 / i );
				    v_total_sul_rate_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_rate / i );
				    v_total_qna_cnt_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_qna_cnt	 / i );	
%>
	    <tr class="table_02_3">
          <td class="table_02_3" colspan="7">총계</td>
          <td class="table_02_3"><%= i %></td>
          <td class="table_02_3"><%= v_total_tstep_rate		%></td>
          <td class="table_02_3"><%= v_total_report_rate	%></td>
          <td class="table_02_3"><%= v_total_mtest_rate		%></td>
          <td class="table_02_3"><%= v_total_ftest_rate		%></td>
          <td class="table_02_3"><%= v_total_htest_rate		%></td>
          <td class="table_02_3"><%= v_total_etc1_rate		%></td>
          <td class="table_02_3"><%= v_total_etc2_rate		%></td>
          <td class="table_02_3"><%= v_total_score_rate		%></td>
          <td class="table_02_3"><%= v_total_exam_cnt_rate	%></td>
          <td class="table_02_3"><%= v_total_sul_cnt_rate	%></td>
          <td class="table_02_3"></td>
          <td class="table_02_3"></td>
          <td class="table_02_3"></td>
          <td class="table_02_3"><%= v_total_sul_rate_rate	%></td>
          <td class="table_02_3"><%= v_total_qna_cnt_rate	%></td>
        </tr>
<%			}
		}
%>
      </form>
      </table>
    <!----------------- 통계조회 끝 ----------------->
    <br>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>