<%
//**********************************************************
//  1. 제      목: 성공취업 이벤트 리스트 조회화면
//  2. 프로그램명:  za_regEvent_L.jsp
//  3. 개      요: 성공취업 이벤트 결과 리스트 조회화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005. 7. 7 이연정
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
<%@ page import = "com.credu.homepage.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

	RequestBox box        = (RequestBox)request.getAttribute("requestbox");
	String  v_process     = box.getString("p_process");

    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;

    String startDate = FormatDate.getRelativeDate(-90);
    String endDate = FormatDate.getDate("yyyyMMdd");
    String searchStart   = "";
    String searchEnd     = "";
    String  v_name       = "";
    
    String  v_userid     = "";
    String  v_jobdate    = "";
    String  v_job        = "";
    String  v_realfile  = "";
    String  v_savefile  = "";
    String	v_ldate		= "";
    
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    searchStart = FormatDate.getFormatDate(startDate,"yyyy-MM-dd");
    searchEnd   = FormatDate.getFormatDate(endDate,"yyyy-MM-dd");
//	String  v_searchStart = box.getStringDefault("p_searchStart",searchStart);
    String  v_searchStart = box.getStringDefault("p_searchStart",searchEnd);
    String  v_searchEnd   = box.getStringDefault("p_searchEnd",searchEnd);
    String  v_startdate   = box.getString("p_startdate");
    String  v_enddate     = box.getString("p_enddate");
    String  v_searchtext     = box.getString("p_searchtext");
    String  v_select         = box.getString("p_select");
    String  v_search         = box.getString("p_search");
    
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    ArrayList list = (ArrayList)request.getAttribute("selectList");
    
    String v_isExcel	= "";
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
	$("#p_searchStart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_searchEnd").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--

//type값을 넘겨서 리스트 검색
function selectType(v_type){
    whenSelection('go', v_type );
}

//검색
function whenSelection(p_action, v_type) {
//	if( document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '' || document.form1.s_grcode.value == '---' ) {
//		alert("교육그룹을 선택하세요.");
//		return;
//	}
	
    document.form1.p_action.value = p_action;
        if (p_action == 'go') {
        top.ftop.setPam();
    }
    document.form1.p_startdate.value = make_date(document.form1.p_searchStart.value);
    document.form1.p_enddate.value = make_date(document.form1.p_searchEnd.value);

	document.form1.p_pageno.value = 1;
    if(document.form1.p_type.value=='SUBJ'){//과정질문방 선택했을때
            document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
            document.form1.p_process.value = 'selectListCourse';
            document.form1.submit();

//ALL검색 포함한 질문리스트를 선택했을때
    }else{
        document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
        document.form1.p_process.value = 'selectList';
//      document.form1.p_type.value= v_type;
        document.form1.submit();
    }
}


//검색
function whenSelection2(p_action) {
//	if( document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '' || document.form1.s_grcode.value == '---' ) {
//		alert("교육그룹을 선택하세요.");
//		return;
//	}
	
    document.form1.p_action.value = p_action;
        if (p_action == 'go') {
        top.ftop.setPam();
    }
    document.form1.p_startdate.value = make_date(document.form1.p_searchStart.value);
    document.form1.p_enddate.value = make_date(document.form1.p_searchEnd.value);

    if(document.form1.p_type.value=='SUBJ'){//과정질문방 선택했을때
            document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
            document.form1.p_process.value = 'selectListCourse';
            document.form1.submit();

//ALL검색 포함한 질문리스트를 선택했을때
    }else{
        document.form1.action='/servlet/controller.homepage.QnaAdminServlet';
        document.form1.p_process.value = 'selectList';
        document.form1.submit();
    }
}

//페이지 이동
function go(index) {
  document.form1.p_action.value = "go";
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}
//페이지 이동
function goPage(pageNum) {
  document.form1.p_action.value = "go";
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
     document.form1.p_process.value = "selectList";
     document.form1.submit();
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types, type, ctcd) {
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectView";
    document.form1.p_seq.value = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value    = types;
    document.form1.pp_type.value    = type;
    document.form1.p_categorycd.value    = ctcd;
    document.form1.p_upfilecnt.value = upfilecnt;

    document.form1.submit();
}

function whenExcel(){	
	
	/*	
	document.form1.p_action.value = "go";
	*/
	document.form1.isExcel.value = "go";
    document.form1.action='/servlet/controller.homepage.EventAdminServlet';
    document.form1.p_process.value = 'eventlist';
    document.form1.submit();
    
}

//상세화면 과정질문방 페이지로 이동
function selectCourse(seq,subj,year,subjseq) {
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectViewCourse";
    document.form1.p_seq.value     = seq;
    document.form1.p_subj.value    = subj;
    document.form1.p_year.value    = year;
    document.form1.p_subjseq.value = subjseq;
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
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
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
          <td><img src="/images/admin/homepage/homepage_title14.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

        <!------------------------------------- form 시작 ------------------------------------->
        <form name  = "form1" method = "post">
        <input type = "hidden" name = "p_process"   value = "<%= v_process %>">
        <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno%>">
        <input type = "hidden" name = "p_seq"       value = "">
        <input type = "hidden" name = "p_userid"    value = "">
        <input type = "hidden" name = "p_types"     value = "">
        <input type = "hidden" name = "p_action"    value = "">
        <input type = "hidden" name = "p_startdate" value = "<%=v_startdate %>">
        <input type = "hidden" name = "p_enddate"   value = "<%=v_enddate %>">
        <input type = "hidden" name = "pp_type"     value = "">
        <input type = "hidden" name = "p_grcodenm"  value = "">
        <input type = "hidden" name = "p_upfilecnt" value = "">
        <input type = "hidden" name = "p_subj">
        <input type = "hidden" name = "p_year">
        <input type = "hidden" name = "p_subjseq">
        <input type = "hidden" name = "isExcel" value="<%=v_isExcel %>">

      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
              <!--<table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
 교육그룹 -
                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                        <tr>
                          <td width="60%" >
                            <select name = "p_select" class = "input">
                                <option value="name" <% if (v_select.equals("name")) out.print("selected"); %>>이름</option>
                                <option value="userid" <% if (v_select.equals("userid")) out.print("selected"); %>>아이디</option>
                            </select>
                            <input type="text" class="input" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_searchtext" value = "<%=v_searchtext %>" size = "25" maxlength = "30">
                          </td>
                          <td width="40%" colspan="2">조회기간
	                        <input name="p_searchStart" id="p_searchStart" type="text" class="datepicker_input1" size="10" value="<%=v_searchStart%>"> 일 ~
	                        <input name="p_searchEnd" id="p_searchEnd" type="text" class="datepicker_input1" size="10" value="<%=v_searchEnd%>"> 일
	                      </td>
                          <td width="10%" align="right"><a href = "javascript:whenSelection('go','ALL')"><img src = "/images/admin/button/b_go.gif" border = "0" align="absmiddle"></a></td>
                        </tr>
                      </table>
 교육그룹 -
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          --></td>
        </tr>
      </table>
      <br>

        </form>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td class=sub_title width="220px">성공취업 이벤트 참여 인원 조회</td>                        
                        <td align="right">
                        	<a href="javascript:whenExcel()">
                        		<img src="/images/admin/button/btn_excelprint.gif" alt="엑셀받기" border="0">
                        	</a>
                        </td>
                    </tr>
                </table>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="13" class="table_top_line"></td>
          </tr>
          <FORM name="ff" method="post" >
            <input type="hidden" name="p_process"    value="">
          <tr>
            <td class="table_title" width="5%">NO</td>
            <td class="table_title" width="10%">성명</td>
            <td class="table_title" width="15%">ID</td>
            <td class="table_title" width="10%">회사명</td>
            <td class="table_title" width="7%">등록일</td>
            <td class="table_title" width="7%">취업일</td>
            <td class="table_title" width="5%">첨부파일</td>
          </tr>

<%if(list.size() > 0){%>
<%
            for(int i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
                v_dispnum       = dbox.getInt("d_dispnum");
                v_userid        = dbox.getString("d_userid");
                v_name          = dbox.getString("d_name");
                v_ldate       = dbox.getString("d_ldate");
                v_jobdate       = dbox.getString("d_jobdate");
                v_job           = dbox.getString("d_job");
                v_realfile    = dbox.getString("d_realfile");
                v_savefile     = dbox.getString("d_savefile");

                v_totalpage     = dbox.getInt("d_totalpage");
                v_rowcount      = dbox.getInt("d_rowcount");
                
%>
             <tr class="table_02_1">
                <td class="table_02_1"><%=v_dispnum%></td>
                <td class="table_02_1"><%=v_name%></td>
                <td class="table_02_2"><%=v_userid%></td>
                <td class="table_02_1"><%=v_job%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd")%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_jobdate,"yyyy/MM/dd")%></td>
                <td class="table_02_1">
            <%
//		            int    v_filecount   = v_realfileVector.size();
	                if( v_realfile != null ) {
//	                    for(int j = 0; j < v_realfile.size(); j++) {      //     첨부파일 갯수에 따라
	
	                        if(v_realfile != null && !v_realfile.equals("")) {  %>
	                            &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
	
	                            <%= v_realfile%></a><br>
	                            <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
	                <%      }   %>
	        <%  //        }
	                }
	        %>
           </td>
            </tr>
           <%}%>
         <%}else{%>
                    <tr>
                      <td colspan='15' class="table_02_1">이벤트 응시인원이 없습니다.</td>
                    </tr>
         <%}%>
            </FORM>
        </table>

        <!----------------- total 시작 -----------------><!--
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
            </td>
          </tr>
        </table>
        --><!----------------- total 끝 ----------------->

      </td>
  </tr>

</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
