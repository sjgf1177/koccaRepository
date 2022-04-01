<%
//**********************************************************
//  1. 제      목: Poll
//  2. 프로그램명 : za_Poll_L.jsp
//  3. 개      요: Poll 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process       = box.getString("p_process");
    String  v_searchtext    = box.getString("p_searchtext");
    String  ss_grcode       = box.getString("s_grcode");            //교육그룹
    String  s_gadmin		= box.getSession("gadmin");
    String  v_gadmin        = StringManager.substring(s_gadmin, 0, 1);
    String  isAll           = !v_gadmin.equals("A") ? "false" : "true";
    String  v_selGroup      = box.getStringDefault("p_selGroup", "ALL");
    
    
    int  v_pageno           = box.getInt("p_pageno");
    if (v_pageno == 0) {
        v_pageno = 1;
    }
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    int    v_seq     = 0;            // 폴일련번호
    int    v_total   = 0;            // 응답수

    String v_title   = "";           // 타이틀
    String v_grcode  = "";
    String v_grcodenm="";
    String v_started = "";           // 시작일
    String v_ended   = "";           // 등록일
    String v_f_use   = "";           // 사용여부
		
		String s_grcode = box.getString("s_grcode");
    ArrayList list = (ArrayList)request.getAttribute("selectList");
%>
<html>
<head>
<title>::: AES 사이버 연수원 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

	function go(index) {
	    document.form1.p_pageno.value = index;
	    document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
	    document.form1.p_process.value = "select";
	    document.form1.submit();
	}
	function goPage(pageNum) {
	    document.form1.p_pageno.value = pageNum;
	    document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
	    document.form1.p_process.value = "select";
	    document.form1.submit();
	}
		//상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_seq.value = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }
		
		//사이버폴 등록
    function insert() {
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
		
		//조회
    function search() {
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
		
		//검색
		function whenSelection(p_action) {
			document.form1.p_action.value = p_action;
			if (p_action == 'go') {
				top.ftop.setPam();
			}
      document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
      document.form1.p_process.value = "select";
      document.form1.submit();
		}
		
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_seq"     value = "">
    <input type = "hidden" name = "p_action"  value = "">
    <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno %>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

       <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/homepage/title_bg.gif" class="page_title"><img src="/images/admin/homepage/homepage_title2.gif"  border="0"></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
			  <td width="27%" align="left" valign="middle">
				<!-- 교육그룹 시작 -->                                                                                            
				<%= !v_gadmin.equals("A") ? "<font color=\"red\">★</font>":"" %>                                                 
				                       교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "              
				      onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="<%= isAll %>" />
				<% //교육그룹 관리자는 ALL 검색이 안되게 설정 %>                                                                  
				<input type="hidden" name="s_grseq" value="ALL">                                                                  
				<!-- 교육그룹 종료 -->    
			  </td>
            <td align="right"> 질문검색 :
              <input name="p_searchtext" type="text" class="input" value = "<%=v_searchtext %>">
            </td>
            <td width="100" align="right" valign="middle">
              <a href='javascript:whenSelection()'><img src="/images/admin/button/btn_search02.gif" border="0"></a>
              <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->

        <!----------------- CyberPoll관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="8%" height="25" class="table_title"><b>NO</b></td>
            <td width="15%" class="table_title"><b>교육그룹</b></td>
            <td width="45%" class="table_title"><b>질문</b></td>
            <td width="20%" class="table_title"><b>설문기간</b></td>
            <td width="12%" class="table_title"><b>응답수</b></td>
          </tr>
<%
System.out.println("list.size : " + list.size());
            for(int i = 0; i < list.size(); i++) {

                //PollData data   = (PollData)list.get(i);

                //v_seq     = data.getSeq();
                //v_grcode  = data.getGrcode();
                //v_total   = data.getTotal();
                //v_title   = data.getTitle();
                //v_started = data.getStarted();
                //v_started = FormatDate.getFormatDate(v_started,"yyyy/MM/dd");
                //v_ended   = data.getEnded();
                //v_ended   = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd");
                //v_f_use   = data.getF_use();
                //v_total   = data.getTotal();
                DataBox dbox = (DataBox)list.get(i);

                v_seq      = dbox.getInt("d_seq");
                v_grcodenm = dbox.getString("d_grcodenm");
                v_grcode   = dbox.getString("d_grcode");
                v_total    = dbox.getInt("d_cnt");
                v_title    = dbox.getString("d_title");
                v_started  = dbox.getString("d_started");
                v_ended    = dbox.getString("d_ended");
                v_f_use    = dbox.getString("d_f_use");

                v_started = FormatDate.getFormatDate(v_started,"yyyy/MM/dd");
                v_ended   = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd");
                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");

%>

          <tr>
            <td class="table_01" height="25"><%=v_dispnum%></td>
            <td class="table_02_1"><%=v_grcodenm%></td>
            <td class="table_02_2"><a href="javascript:view('<%=v_seq%>')"><%=v_title%></a></td>
            <td class="table_02_1"><%=v_started%> ~ <%=v_ended%></td>
            <td class="table_02_1"><%=v_total%></td>
          </tr>
<%
        }
%>



        </table>
        <!----------------- CyberPoll관리 끝 ----------------->

        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageListGame(v_totalpage, v_pageno, 10) %>
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
