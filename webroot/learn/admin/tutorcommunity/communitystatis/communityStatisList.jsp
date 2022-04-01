<%
//**********************************************************
//  1. 제      목: 튜터링커뮤니티 커뮤니티 통계
//  2. 프로그램명 : communityStatisList.jsp
//  3. 개      요: 커뮤니티 통계
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2013.07.17
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
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	
	String	v_sdate = box.getStringDefault("s_sdate",FormatDate.getDate("yyyyMMdd"));
	String	v_edate = box.getStringDefault("s_edate",FormatDate.getDate("yyyyMMdd"));
	
	String  v_process   = box.getString("p_process");
	int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    int		v_dispnum   = 0;
    int		v_totalpage = 0;           // 게시물총페이지수
    int		v_totalrowcount =  0;
    
	String	v_tgubun = "";
	String	v_tabseq = "";
	String	v_seq = "";
	String	v_types = "";
	String	v_title = "";
	String	v_indate = "";
	String	v_inuserid = "";
	String	v_cnt = "";
	String	v_name = "";
	String	v_subj = "";
	String	v_subjseq = "";
	String	v_year = "";
	String	v_lesson = "";
	String	v_subjnm = "";
	String	v_area = "";
	String	v_upperclass = "";
	String	v_middleclass = "";
	String	v_lowerclass = "";
	String	v_answercnt = "";
	String	v_commentcnt = "";
	
	ArrayList list = (ArrayList)request.getAttribute("TutorCommunityList");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>

<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#s_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#s_edate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});

function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}
function pagesize(pageSize) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_pagesize.value = pageSize;
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}
function whenSelection() {
	document.form1.p_pageno.value = 1;
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}
function selectDetailView(tgubun, tabseq, seq, types, subj, subjseq, year, lesson, answercnt, commentcnt){
	document.form1.p_tgubun.value		= tgubun;
	document.form1.p_tabseq.value		= tabseq;
	document.form1.p_seq.value			= seq;
	document.form1.p_types.value		= types;
	document.form1.p_subj.value			= subj;
	document.form1.p_subjseq.value		= subjseq;
	document.form1.p_year.value			= year;
	document.form1.p_lesson.value		= lesson;
	document.form1.p_answercnt.value	= answercnt;
	document.form1.p_commentcnt.value	= commentcnt;
    document.form1.p_process.value = "detailView";
    document.form1.submit();
}
function insert(){
	document.form1.p_process.value = "insertPage";
	document.form1.submit();
}
</script>

</head>

<body id="tutor" bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" action="/servlet/controller.tutorcommunity.TutorCommunityStatisServlet">
	<input type = "hidden" name = "p_process"    value = "<%=v_process %>"/>
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>"/>
    <input type = "hidden" name = "p_pagesize"   value = "<%=v_pagesize%>"/>
    <input type = "hidden" name = "p_tgubun"	 value = ""/>
    <input type = "hidden" name = "p_tabseq"	 value = ""/>
    <input type = "hidden" name = "p_seq"		 value = ""/>
    <input type = "hidden" name = "p_types"		 value = ""/>
    <input type = "hidden" name = "p_subj"		 value = ""/>
    <input type = "hidden" name = "p_subjseq"	 value = ""/>
    <input type = "hidden" name = "p_year"		 value = ""/>
    <input type = "hidden" name = "p_lesson"	 value = ""/>
    <input type = "hidden" name = "p_answercnt"	 value = ""/>
    <input type = "hidden" name = "p_commentcnt" value = ""/>
    
	<table width="1000" border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td align="center" valign="top">
			<!----------------- title 시작 ----------------->
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class=page_title>
					<tr> 
						<td align="left"><img src="/images/admin/tutorcommunity/lms_title_commstat.gif"></td>
					</tr>
				</table>
			<!----------------- title 끝 ----------------->
			</td>
		</tr>
		
		<tr>
			<td align="center" valign="top">
				<!------------------------------------- 검색 조건 시작 ------------------------------------->
				<table cellspacing="0" cellpadding="1" class="form_table_out">
					<tr>
						<td bgcolor="#C6C6C6" align="center">
							<table cellspacing="0" cellpadding="0" class="form_table_bg" >
								<tr>
									<td height="7"></td>
								</tr>
								<tr>
									<td align="left">
										<table id="cellp" border="0" cellspacing="1" cellpadding="2" width="100%"  class="form_table">
											<tr>
												<td>&nbsp;
													말머리
													<select name="s_tgubun">
														<option value="ALL">전체</option>
														<option value="T" <%if(box.getString("s_tgubun").equals("T")) out.println("selected");%>>토론</option>
														<option value="Q" <%if(box.getString("s_tgubun").equals("Q")) out.println("selected");%>>질문</option>
														<option value="C" <%if(box.getString("s_tgubun").equals("C")) out.println("selected");%>>일반</option>
														<option value="D" <%if(box.getString("s_tgubun").equals("D")) out.println("selected");%>>자료</option>
														<option value="I" <%if(box.getString("s_tgubun").equals("I")) out.println("selected");%>>정보</option>
													</select>
													&nbsp;&nbsp;&nbsp;
													분야
													<select name="s_area">
														<option value="ALL">전체</option>
														<option value="C0" <%if(box.getString("s_area").equals("C0")) out.println("selected");%>>공통</option>
														<option value="B0" <%if(box.getString("s_area").equals("B0")) out.println("selected");%>>방송영상</option>
														<option value="G0" <%if(box.getString("s_area").equals("G0")) out.println("selected");%>>게임개발</option>
														<option value="K0" <%if(box.getString("s_area").equals("K0")) out.println("selected");%>>문화콘텐츠</option>
														<option value="W0" <%if(box.getString("s_area").equals("W0")) out.println("selected");%>>교양</option>
													</select>
													&nbsp;&nbsp;&nbsp;
													과정명
													<kocca_select:select name="s_subj" sqlNum="tutorcummunity.subjselect"  isLoad="true"/>
													&nbsp;&nbsp;&nbsp;
													기간별&nbsp; 
													<input name="s_sdate" id="s_sdate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_sdate, "yyyy-MM-dd") %>'> ~ 
													<input name="s_edate" id="s_edate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_edate, "yyyy-MM-dd") %>'>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<!------------------------------------- 검색 조건 끝 ------------------------------------->
				<br/>
				<table width="97%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td  class=sub_title>
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td align="right">
										<img src="/images/admin/button/b_go.gif" alt="검색" style="CURSOR:POINTER" onclick="whenSelection()">&nbsp;
										<img src="/images/admin/button/btn_excelprint.gif"/>
										<img src="/images/admin/button/btn_write.gif" alt="쓰기" style="CURSOR:POINTER" onclick="insert()">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="3"></td>
					</tr>
				</table>
			
				<div id="s_list">
					<!----------------- 조회 시작 ----------------->
					<table class="table_out" cellspacing="0" cellpadding="3" border="0" summary="튜터 커뮤니티">
						<caption></caption>
						<colgroup>
							<col width="5%"/>
							<col width="5%"/>
							<col width=""/>
							<col width="30%"/>
							<col width="5%"/>
							<col width="5%"/>
							<col width="8%"/>
						</colgroup>
						<thead>
						<tr>
							<th scope="col" class="table_title">No</th>
							<th scope="col" class="table_title">말머리</th>
							<th scope="col" class="table_title">제목</th>
							<th scope="col" class="table_title">관련과정</th>
							<th scope="col" class="table_title">작성자</th>
							<th scope="col" class="table_title">조회수</th>
							<th scope="col" class="table_title">작성일</th>
						</tr>
						</thead>
						<tbody>
						<%
						if(list.size() > 0 ){
							for(int i = 0; i < list.size(); i++) {
								DataBox dbox = (DataBox)list.get(i);
								
								v_tgubun		= dbox.getString("d_tgubun");
								v_tabseq		= dbox.getString("d_tabseq");
								v_seq			= dbox.getString("d_seq");
								v_types			= dbox.getString("d_types");
								v_title			= dbox.getString("d_title");
								v_inuserid		= dbox.getString("d_inuserid");
								v_cnt			= dbox.getString("d_cnt");
								v_name			= dbox.getString("d_name");
								v_indate		= FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy-MM-dd");
								v_subj			= dbox.getString("d_subj");
								v_subjseq		= dbox.getString("d_subjseq");
								v_year			= dbox.getString("d_year");
								v_lesson		= dbox.getString("d_lesson");
								v_subjnm		= dbox.getString("d_subjnm");
								v_area			= dbox.getString("d_area");
								v_upperclass	= dbox.getString("d_upperclass");
								v_middleclass	= dbox.getString("d_middleclass");
								v_lowerclass	= dbox.getString("d_loserclass");
								v_answercnt		= dbox.getString("d_answercnt");
								v_commentcnt	= dbox.getString("d_commentcnt");
								
								
								v_dispnum   = dbox.getInt("d_dispnum");
				                v_totalpage = dbox.getInt("d_totalpage");
				                v_totalrowcount = dbox.getInt("d_totalrowcount");
								
				                String v_tgubunnm = "";
								if(v_tgubun.equals("T")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_t.gif\" alt=\"토론\" />";}
								else if(v_tgubun.equals("Q")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_q.gif\" alt=\"질문\" />";}
								else if(v_tgubun.equals("C")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_c.gif\" alt=\"일반\" />";}
								else if(v_tgubun.equals("D")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_d.gif\" alt=\"자료\" />";}
								else if(v_tgubun.equals("I")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_i.gif\" alt=\"정보\" />";}
								
								String v_areanm = "";
								if(v_area.equals("B0")){v_areanm = "방송영상";}
								else if(v_area.equals("G0")){v_areanm = "게임개발";}
								else if(v_area.equals("K0")){v_areanm = "문화콘텐츠";}
								else if(v_area.equals("W0")){v_areanm = "교양";}
								else if(v_area.equals("C0")){v_areanm = "공통";}
						%>
						
						<tr>
							<td class="table_01_under_c9"><%=v_dispnum %></td>
							<td class="table_02_1_under_c9"><%=v_tgubunnm %></td>
							<td class="table_02_2_under_c9"><%if(!v_answercnt.equals("0"))out.print("<strong>[답변완료]</strong> "); %><a href="javascript:selectDetailView('<%=v_tgubun %>','<%=v_tabseq %>','<%=v_seq %>','<%=v_types %>','<%=v_subj %>','<%=v_subjseq %>','<%=v_year %>','<%=v_lesson %>','<%=v_answercnt %>','<%=v_commentcnt %>');"><%=v_title %> (+<%=v_commentcnt %>)</a></td>
							<td class="table_02_2_under_c9">(<%=v_areanm %>) <%=v_subjnm %></td>
							<td class="table_02_1_under_c9"><%=v_name %></td>
							<td class="table_02_1_under_c9"><%=v_cnt %></td>
							<td class="table_02_1_under_c9"><%=v_indate %></td>
						</tr>
						
						<%
							}
						}else{
						%>
						<tr>
							<td align="center" bgcolor="#FFFFFF" height="100" colspan="7">등록된 내용이 없습니다.</td>
						</tr>
						<%
						}
						%>
						</tbody>
					</table>
					<!----------------- 조회 끝 ----------------->
					
					<!----------------- total 시작 ----------------->
			        <br>
					<table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right" valign="absmiddle">
								<%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
							</td>
						</tr>
					</table>
			        <!----------------- total 끝 ----------------->
				</div>
			</td>
		</tr>
	</table>
	</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>