<%
//**********************************************************
//  1. 제      목: 과정 조회화면
//  2. 프로그램명: za_Subject_L.jsp
//  3. 개      요: 과정 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 12
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.budget.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String  v_year		 = box.getStringDefault("s_year","");    		//년도	
    String  v_edugubun   = box.getStringDefault("s_edugubun","ALL");    //교육유형
    String  v_company    = box.getStringDefault("s_company","ALL");    	//회사
    String  v_edudept    = box.getStringDefault("s_edudept","ALL");    	//교육주관부서
    String  v_srchsubjnm = box.getStringDefault("p_srchsubjnm","");		//과정명
    String  v_me		 = box.getString("p_me"); //자기가 등록한 과정
        
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    

    
    //총괄관리자/강사관리자는 Default N
    if (v_me.equals("")) {
    	if (box.getSession("gadmin").substring(0,1).equals("A") || box.getSession("gadmin").substring(0,1).equals("R"))
    		v_me = "N";
    	else
    		v_me = "Y";
	}
%>

<html>
<head>
<title>과정관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language = "javascript" src = "/script/tutor.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--


function password_enter(e) { 
        if (e.keyCode =='13'){  whenSelection();  }
}

function subjSelect(subj) {
	document.form2.target="_self";
	document.form2.p_process.value = 'SubjSelect';
	document.form2.p_subj.value = subj;
	document.form2.submit();
	
}

function Subjseq(subj) {
    alert('기존차수 조회화면으로 이동');
    return;
    /*
    document.form2.p_process.value = 'subjseqPage';
    document.form2.p_subj.value  = p_subj;
    document.form2.submit();
    */
}

function whenSelection() {
	document.form2.target="_self";
    document.form2.p_process.value = 'SubjList';
    document.form2.submit();
}

function whenExcel() {

        window.self.name = "CompleteRateList";
        open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
        document.form2.target = "openExcel";
        document.form2.action='/servlet/controller.budget.BudgetAdminServlet';
        document.form2.p_process.value = 'SubjListExcel';
        document.form2.submit();
    }

function insert() {
	document.form2.target="_self";
    document.form2.p_process.value = 'SubjInsertPage';
    document.form2.submit();
}

function whenOrder(column) {
	if (document.form2.p_orderType.value == " asc") {
		document.form2.p_orderType.value = " desc";
	} else {
		document.form2.p_orderType.value = " asc";
	}

    document.form2.p_orderColumn.value = column;

    whenSelection();
}

-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/tutor/t_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>     
	  <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <form name="form2" method="post" action="/servlet/controller.budget.BudgetAdminServlet">
          <input type="hidden" name="p_process"  value="">
          <input type="hidden" name="p_subj" value="">
          <input type="hidden" name="p_inusercompnm" value="">
          <input type="hidden" name="p_subjgubun" value="">
          <input type="hidden" name="p_orderColumn">
		  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

		  
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td>
                      <!--input type="radio" name="p_searchtype" checked value="S">시작일기준 &nbsp;<input type="radio" name="p_searchtype" value="E">
                        종료일기준<br>
                        <input type="radio" name="p_searchgubun" checked value="Y"-->
                        
                        <%= BudgetAdminBean.getYear(box, true, false)%><!-- getCompany(RequestBox, isChange, isALL)    회사(s_year)  -->
                        &nbsp;
                        <!--기간 <input name="p_edustartday" type="text" class="input" size="10" maxlength=10  value="" OnClick="MiniCal(this)" readonly>~
                        <input name="p_eduendday" type="text" class="input" size="10" maxlength=10  value="" OnClick="MiniCal(this)" readonly><br-->
						교육유형 <%=CodeConfigBean.getCodeGubunSelect ("0024", "", 1, "s_edugubun", v_edugubun, "onchange='javascript:whenSelection()'", 1)%>
						<%= SelectCompanyBean.getCompany(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사(s_company)  -->
						<%= BudgetAdminBean.getEduDept(box, true, true)%><!-- getEduDept(RequestBox, isChange, isALL)    교육주관팀(s_edudept)  -->
						<br>과정명 : <input type="text" name="p_srchsubjnm" size="40" value="<%=v_srchsubjnm%>" onkeypress="password_enter(event)" style="width:119;height:18;border:B4BDD4 1px solid">
						&nbsp;<input type="checkbox" name="p_me" value="Y" <%if (v_me.equals("Y")) out.print("checked"); %>>내가 등록한 과정
				  </td>
				  <td width=4></td>
				  <td><a href="javascript:whenSelection()"><img src="/images/admin/button/b_go.gif"  border="0"></a></td>	
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <br>
	  <!----------------- 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="91%" height="3"></td>
          </tr>
          <tr>
            <td align="right">&nbsp;
            <a href="javascript:insert()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>

			<td width="9%" align="right">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 버튼 종료 ----------------->
      <!----------------- 과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="5" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="4%"><b>NO</b></td>
          <td class="table_title" width="32%"><a href="javascript:whenOrder('subjnm')" class="e"><b>과정명</b></a></td>
          <td class="table_title" width="13%"><a href="javascript:whenOrder('edustarta')" class="e"><b>교육기간</b></a></td>
          <td class="table_title" width="13%"><a href="javascript:whenOrder('edugubunnm')" class="e"><b>교육유형</b></a></td>
          <td class="table_title" width="20%"><a href="javascript:whenOrder('inusercompnm')" class="e"><b>교육주관팀</b></a></td>
        </tr>
        <%  ArrayList  list = (ArrayList)request.getAttribute("budgetSubjectList");
	    for (int i=0; i<list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i); %>
        <tr>
          <td class="table_01"><%=list.size()-i%></td>
          <td class="table_02_2"><a href="javascript:subjSelect('<%=dbox.getString("d_subj")%>');"><%=dbox.getString("d_subjnm")%></a></td>
          <td class="table_02_1"><%=dbox.getString("d_edustarta")%>~<%=dbox.getString("d_eduenda")%></td>
          <td class="table_02_1"><%=dbox.getString("d_edugubunnm")%></td>
          <td class="table_02_1"><a href="javascript:inuserinfo('<%=dbox.getString("d_inusercompnm")%>','A','<%=dbox.getString("d_subj")%>')"><%=dbox.getString("d_inusercompnm")%></a></td>
        </tr>
        <%  } %>
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
