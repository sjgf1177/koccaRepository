<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 나의강의실> 학습진단결과창
//  2. 프로그램명 : gu_JindanResult_L.jsp
//  3. 개      요: 학습진단창
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	DataBox dbox = null;
    ArrayList list = (ArrayList)request.getAttribute("JindanUserResult");
	String v_upperclass = (String)request.getAttribute("p_upperclass");
	String v_middleclass = (String)request.getAttribute("p_middleclass");
	String v_lowerclass = (String)request.getAttribute("p_lowerclass");
	String v_classname = (String)request.getAttribute("p_classname");
	String v_jindanDate = (String)request.getAttribute("p_jindanDate");
    
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>진단이력보기 - 결과보기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<link rel="stylesheet" type="text/css" href="/css/user_style1.css"/>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
// 과정 내용보기
function whenSubjInfo(subj,tabnum){

    document.frm3.pp_subj.value     = subj;
    document.frm3.pp_tabnum.value   = tabnum;
    document.frm3.pp_process.value  = 'SubjectPreviewPage';
    document.frm3.pp_rprocess.value = 'SubjectList';
    document.frm3.p_process.value  = 'CourseIntroMove';
    document.frm3.action='/servlet/controller.jindan.JindanUserServlet';
    document.frm3.target = "winListPage";
    document.frm3.submit();
	self.close();

}
//-->
</script>
</head>
<body id="popup">
<form method=post action="" name="frm3">
<input type="hidden" name="pp_subj"/>
<input type="hidden" name="pp_year"/>
<input type="hidden" name="pp_tabnum"/>
<input type="hidden" name="pp_process"/>
<input type="hidden" name="pp_rprocess"/>
<input type="hidden" name="p_process"/>
</form>

<div id="pop_header">
<h1><img src="/images/portal/myclass/pop_h1_tit7.gif" alt="진단이력보기 - 결과보기" /></h1>
</div>
<div id="pop_container">
<div id="contentwrap" class="message_top">
	
	<h3 class="pop_tit_txt"><%= v_classname %> 학습진단<span class="ra_txt">(진단일 : <%= v_jindanDate %>)</span></h3>
	<table class="list">
	<colgroup><col width="24%" /><col width="46%" /><col width="8%" /><col width="10%" /><col width="12%" /></colgroup>
	<thead>
	<tr>
		<th>과정명</th>
		<th>통계</th>
		<th>점수</th>
		<th class="end">추천순위</th>
		<!-- th class="end">과정정보</th-->
	</tr>
	</thead>
	<tbody>
<%
	String lDate="";
	int rate=0;
	String subjgubun="";

	for(int i=0; i < list.size(); i++){
		dbox = (DataBox)list.get(i);
		rate = ( 275 * dbox.getInt("d_score") ) / 100 ;
		lDate = FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd");
		subjgubun = conf.getProperty("game.middleclass."+dbox.getString("d_middleclass"));
%>
	<tr>
		<td class="ta_l"><%= dbox.getString("d_subjnm") %></td>
		<td class="ta_l ta_l10"><span class="progress_bar"><span class="bar_blue"><span style="width:<%= rate %>px;"></span></span></span></td>
		<td><%= dbox.getInt("d_score") %>점</td>
		<td class="end"><%= i + 1 %>위</td>
		<!-- td class="end"><a href="javascript:whenSubjInfo('<%//= dbox.getString("d_subj") %>')" class="board_btn2"><span>과정보기</span></a></td-->
	</tr>

<%
	}
%>
	</tbody>
	</table>
</div>
</div>
<div id="pop_footer">
<p class="f_btn"><a href="javascript:self.close()" class="btn_gr"><span>닫기</span></a></p>
</div>
</body>
</html>