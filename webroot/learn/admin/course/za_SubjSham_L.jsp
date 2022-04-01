<%
//**********************************************************
//  1. 제      목: 강의실
//  2. 프로그램명 : za_SubjSham_L.jsp
//  3. 개      요: 강의실
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process       = box.getString("p_process");
    String  v_searchtext    = box.getString("p_searchtext");

	String  v_orderColumn   = box.getString("p_orderColumn");
    String  v_orderType     = box.getStringDefault("p_orderType"," asc"); //정렬순서

    //페이징
    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스

    String  v_upperclass = "";
    String  v_classname  = "";
    String  v_subj       = "";
    String  v_subjnm     = "";
    String  v_isonoff    = "";
    String  v_aescontentid = "";
    String  v_yeslearn = conf.getProperty("yeslearn.url.value");

    ArrayList list = (ArrayList)request.getAttribute("selectList");

	String v_gadmin = box.getSession("gadmin").substring(0,1);

    /*=======  과정분류 조건시 필요 =========*/
    String  v_action = "/servlet/controller.course.SubjShamAdminServlet";
    String  v_form   = "form1";
    /*=======  과정분류 조건시 필요 =========*/
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript"><!--

$(document).ready(function(){
	$('input[name=s_inpubox]').keypress(function(){
		if(event.keyCode == 13){
			whenSelection('go');
		}
	});
});

    // 상세보기
    function view(upperclass, subj,subjnm, isonoff) {

        document.form1.action = "/servlet/controller.course.SubjShamAdminServlet";
        document.form1.p_upperclass.value  = upperclass;
        document.form1.p_subj.value        = subj;
        document.form1.p_subjnm.value      = subjnm;
        document.form1.p_isonoff.value     = isonoff;        
        //document.form1.p_ctype.value       = ctype;
        //document.form1.p_estart.value      = estart;        
        //document.form1.p_eend.value        = eend;        
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    // 검색
    function whenSelection(p_action) {	

        document.form1.p_process.value    = "selectPre";
        if(p_action=='go') {
        document.form1.p_process.value    = "select";
        }
        document.form1.p_pageno.value = "1";
        document.form1.action = "/servlet/controller.course.SubjShamAdminServlet";
        document.form1.submit();
    }

    // yeslearn 학습창
    function whenViewEdu(url){
        window.open(url,'EduStart','width=800,height=600');
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
		document.form1.action = "/servlet/controller.course.SubjShamAdminServlet";
		document.form1.p_process.value = "select";
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.action = "/servlet/controller.course.SubjShamAdminServlet";
		document.form1.p_process.value = "select";
		document.form1.submit();
	}
    
//
----></SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" onsubmit="return false;">
    <input type = "hidden" name = "p_process"     value = "<%= v_process %>">
    <input type = "hidden" name = "p_upperclass"  value = "">
    <input type = "hidden" name = "p_subj"        value = "">
    <input type = "hidden" name = "p_subjnm"      value = "">
    <!--<input type = "hidden" name = "isOffSubj"     value = "Y">-->
    <input type = "hidden" name = "p_isonoff"      value = "">
    <input type = "hidden" name = "p_ctype"       value = "">
    <input type = "hidden" name = "p_estart"      value = "">
    <input type = "hidden" name = "p_eend"        value = "">
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_action"  value="">  
    
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <!--<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title30.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>-->
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/course/co_title09_1.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 --------------- -->
      <br>
      <!----------------- title 끝 ----------------->

        <br>
        <!----------------- form 시작 ----------------->
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr> 
            <td align="center"> 
              <table class="form_table_bg" cellspacing="0" cellpadding="0">
                <tr><td height="7"></td></tr>
                <tr> 
                  <td>
<script type="text/javascript">
var startup = 0;
function Main_subjcourse(){
	if(startup > 1)
		changes_subjcourse('ALL', document.form1.s_upperclass.value, document.form1.s_middleclass.value, document.form1.s_lowerclass.value,'ALL','%' );
	startup++;
}
</script>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
	과정 <kocca:selectBox name="s_subjcourse" id="oSubjcourse" optionTitle="== 전체 ==" optionTitleValue="ALL" type="sqlID"  sqlID="selectBox.subjList2" selectedValue="<%= ss_subjcourse %>" isLoad="true" />
                  과정명 <input type="text" name="s_inpubox">
                  <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                  </td>
                </tr>
                <tr><td height="7"></td></tr>
              </table>
            </td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->

        <br>
        <br>

        <!----------------- 과정모의학습 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" width="10%" height="25" ><b>No</b></td>
            <td class="table_title" width="30%" height="25" ><a href="javascript:whenOrder('classname')" class="e">과정분류</a></td>
            <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정명</a></td>
            <td class="table_title"><a href="javascript:whenOrder('isuse')" class="e">사용여부</a></td>
          </tr>

<%

       for(int i = 0; i < list.size(); i++) {

    	   /*
           SubjectData data   = (SubjectData)list.get(i);

           v_upperclass = data.getUpperclass();
           v_classname  = data.getClassname();
           v_subj       = data.getSubj();
           v_subjnm     = data.getSubjnm();
           v_isonoff    = data.getIsonoff();           
           //v_aescontentid = data.getAescontentid();
           */
           
			DataBox dbox = (DataBox)list.get(i);

           	v_upperclass = dbox.getString("d_upperclass");
           	v_classname  = dbox.getString("d_classname");
           	v_subj       = dbox.getString("d_subj");
           	v_subjnm     = dbox.getString("d_subjnm");    
            v_isonoff    = dbox.getString("d_isonoff");            
            String v_isuse = dbox.getString("d_isuse");

           //페이징
			v_dispnum       = dbox.getInt("d_dispnum");
            v_totalpage     = dbox.getInt("d_totalpage");
            v_rowcount      = dbox.getInt("d_rowcount");

%>
          <tr> 
            <td class="table_01" height="25"><%=v_dispnum%></td>
            <td class="table_01" height="25"><%=v_classname%></td>
            <td class="table_02_2">            
              <a href="javascript:view('<%=v_upperclass%>', '<%=v_subj%>', '<%=v_subjnm%>', '<%=v_isonoff%>')"><%=v_subjnm%>&nbsp;(<%=v_subj%>)</a>
            </td>
            <td class="table_01"><%=v_isuse%></td>
          </tr>

<%
       }
%>

        </table>
        <!----------------- 과정모의학습 끝 ----------------->
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
      </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
