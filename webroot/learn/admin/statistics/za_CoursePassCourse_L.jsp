<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 수료현황  - 과정별 현황
//  2. 프로그램명 : za_CoursePassCourse_L.jsp
//  3. 개      요: 수료현황 - 과정별  현황
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

	String	v_grcode			= "";
	String	v_gyear				= "";
	String	v_grseq				= "";
	String	v_upperclass		= "";
	String	v_middleclassname	= "";
	String	v_lowerclassname	= "";
	String	v_edustart			= "";
	String	v_eduend			= "";
	String	v_subj				= "";
	String	v_year				= "";
	String	v_subjseq			= "";
	String	v_subjnm			= "";
	int		v_user_cnt			= 0;
	int		v_grad_cnt			= 0;
	int		v_ngrad_cnt			= 0;
	double	v_grad_rate			= 0;
	int		v_sul_cnt2			= 0;
	double	v_sul_rate			= 0;
	int		v_total_user_cnt	= 0;
	int		v_total_grad_cnt	= 0;
	int		v_total_ngrad_cnt	= 0;
	int		v_total_sul_cnt2	= 0;
	
	double	v_total_user_cnt_d	= 0;
	double	v_total_grad_cnt_d	= 0;
	double	v_total_sul_cnt2_d	= 0;
	String	v_total_grad_rate	= "";
	String	v_total_sul_rate	= "";
	
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
	var process = 'selectPassCourseListPre';            //검색전
  
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
		process='selectPassCourseList';   //검색후
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
    document.form1.p_process.value = 'selectPassCourseListExcel';
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
          <td align="left" height="20">&nbsp;<b>과정별 수료현황</b></td>
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
          <td colspan="12" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="5%"><b>No</b></td>
          <td class="table_title"><b>분야</b></td>
          <td class="table_title"><b>난이도</b></td>
          <td class="table_title"><b>과정명</b></td>
          <td class="table_title"><b>차수</b></td>
          <td class="table_title"><b>교육기간</b></td>
          <td class="table_title"><b>교육인원</b></td>
          <td class="table_title"><b>수료인원</b></td>
          <td class="table_title"><b>미수료인원</b></td>
          <td class="table_title"><b>만족도 응시인원</b></td>
          <td class="table_title"><b>수료율</b></td>
          <td class="table_title"><b>만족도 응시율</b></td>
        </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

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
				v_user_cnt			= dbox.getInt("d_user_cnt");
				v_grad_cnt			= dbox.getInt("d_grad_cnt");
				v_ngrad_cnt			= dbox.getInt("d_ngrad_cnt");
				v_grad_rate			= dbox.getDouble("d_grad_rate");
				v_sul_cnt2			= dbox.getInt("d_sul_cnt2");
				v_sul_rate			= dbox.getDouble("d_sul_rate");

				v_total_user_cnt	+= v_user_cnt;
				v_total_grad_cnt	+= v_grad_cnt;
				v_total_ngrad_cnt	+= v_ngrad_cnt;
				v_total_sul_cnt2	+= v_sul_cnt2;

				v_total_user_cnt_d	+= v_user_cnt;
				v_total_grad_cnt_d	+= v_grad_cnt;
				v_total_sul_cnt2_d	+= v_sul_cnt2;
				
%>
        <tr class="table_02_1">
          <td class="table_02_1"><%= i + 1 %></td>
          <td class="table_02_1"><%= v_middleclassname %></td>
          <td class="table_02_1"><%= v_lowerclassname %></td>
          <td class="table_02_2"><%= v_subjnm %></td>
          <td class="table_02_1"><%= StringManager.cutZero(v_subjseq) %></td>
          <td class="table_02_1"><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
          <td class="table_02_1"><%= v_user_cnt %></td>
          <td class="table_02_1"><%= v_grad_cnt %></td>
          <td class="table_02_1"><%= v_ngrad_cnt %></td>
          <td class="table_02_1"><%= v_sul_cnt2 %></td>
          <td class="table_02_1"><%= v_grad_rate %>%</td>
          <td class="table_02_1"><%= v_sul_rate %>%</td>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="12">등록된 내용이 없습니다.</td>
        </tr>
<%			} else { 
				if (v_total_user_cnt != 0) {
					v_total_grad_rate = new java.text.DecimalFormat("###,###,###.##").format(v_total_grad_cnt_d / v_total_user_cnt_d * 100);
					v_total_sul_rate = new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt2_d / v_total_user_cnt_d * 100);
				} else {
					v_total_grad_rate = "0";					
					v_total_sul_rate = "0";
				}
%>
	    <tr class="table_02_3">
          <td class="table_02_3" colspan="6">합계</td>
          <td class="table_02_3"><%= v_total_user_cnt %></td>
          <td class="table_02_3"><%= v_total_grad_cnt %></td>
          <td class="table_02_3"><%= v_total_ngrad_cnt %></td>
          <td class="table_02_3"><%= v_total_sul_cnt2 %></td>
          <td class="table_02_3"><%= v_total_grad_rate %>%</td>
          <td class="table_02_3"><%= v_total_sul_rate %>%</td>
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