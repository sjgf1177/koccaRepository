<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 과정수강생리스트
//  2. 프로그램명 : za_Permission_L.jsp
//  3. 개      요: 과정수강생 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 20
//  7. 수      정:
//***********************************************************
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
    String  v_process   = box.getString("p_process");
    String  v_usernm    = box.getString("p_usernm");
    String  v_subj      = box.getString("p_subj");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_year      = box.getString("p_year");

    int v_pageno    = box.getInt("p_pageno");
    if (v_pageno == 0) v_pageno = 1;
    int v_totalpage = 0; int v_dispnum = 0;
    int v_rowcount  = 0;
    
    String v_userid="", v_cono="", v_name="", v_compnm="", v_email="";

    ArrayList list = (ArrayList)request.getAttribute("select");
    
    SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);

    EduStartBean	bean		= new EduStartBean();
    EduScoreData    scoredata	= new EduScoreData();
    				scoredata	= bean.SelectEduScore(box);

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>수강생 현황</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language = "JavaScript">
	function select(){
	document.form1.submit();
}
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post" action="/servlet/controller.study.StudentListServlet">
	<input type="hidden" name="p_subj" value="<%=v_subj%>"/>
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>"/>
	<input type="hidden" name="p_year" value="<%=v_year%>"/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit12.gif" alt="수강생현황" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">
			
<%	if (scoredata != null) { %>
				<div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>과정명</span></dt>
						<dd><%= subjdata.getSubjnm() %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~<%= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %></dd>
					</dl>
				</div></div></div>
<%	} %>
				
				<div class="content">
					<div class="h3_wrap">
						<h3><img src="/images/portal/myclass/study/pop_stit17.gif" alt="수강생현황" /></h3>
						<p>
							<img src="/images/portal/ico/ico_bul_gcircle.gif" alt="" class="mg_r5" /><span>성명</span>
							<input type="text" name="p_usernm" class="inbox" style="width:135px;" value="<%= v_usernm %>" onKeyPress="fnKeyPressEnter(event,'select');"/>
							<a href = "javascript:select();"><img src="/images/portal/btn/btn_serbox_search.gif" alt="검색" /></a>
						</p>
					</div>
					
					<table class="board_list">
					<colgroup><col width="10%" /><col width="30%" /><col width="60%" /></colgroup>
					<thead>
					<tr class="gray">
						<th><p>번호</p></th>
						<th><p>이름</p></th>
						<th class="end"><p>이메일</p></th>
					</tr>
					</thead>
					<tbody>
                      <%                    
                for(int i = 0; i < list.size(); i++) {
                    DataBox dbox = (DataBox)list.get(i);
                    v_userid    = dbox.getString("d_userid");
                    v_cono      = dbox.getString("d_cono");
                    v_name      = dbox.getString("d_name");
                    v_compnm    = dbox.getString("d_compnm");
                    v_email     = dbox.getString("d_email");
                    v_totalpage = dbox.getInt("d_totalpage");
                    v_rowcount  = dbox.getInt("d_rowcount");
                    v_dispnum   = dbox.getInt("d_dispnum");
%>
					<tr onmouseover="className='over'" onmouseout="className=' '">
						<td class="num"><%= v_dispnum %></td>
						<td><%= v_name %></td>
						<td class="num"><%= v_email %></td>
					</tr>
<%
                }
%>
					</tbody>
					</table>
					
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
					
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>