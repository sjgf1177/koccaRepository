<%
//**********************************************************
//  1. 제      목: 회사별 수강신청 제약 설정
//  2. 프로그램명: za_CompCondition_L.jsp
//  3. 개      요: 회사별 수강신청 제약 설정 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 07. 14
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_comp				= "";
    String v_companynm			= "";
    int v_duty					= 0;
    int v_language				= 0;
    int v_allcondition			= 0;
    int v_yearduty				= 0;
    int v_yearlanguage			= 0;

    String v_process = box.getString("p_process"); 
    String ss_action = box.getString("p_action"); 
    String s_gadmin	 = box.getSession("gadmin");

	ArrayList list = null;

    if(ss_action.equals("go")){
      list = (ArrayList)request.getAttribute("compconditionlist");
    }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<script language="JavaScript">
<!--

//조회
function whenSelection(p_action) {
        document.form1.p_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.propose.AcceptFileToDBServlet';
        document.form1.p_process.value = 'compCoditionPage';
        document.form1.submit();
}

// 수강신청 제약 조건 수정
function whenUpdateCondition(i){
		document.form1.unum.value = i;
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.propose.AcceptFileToDBServlet";
        document.form1.p_process.value="compCoditionUpdate";
        document.form1.submit();
}

// 수강신청 제약 조건 삭제
function whenDeleteCondition(i){
		document.form1.unum.value = i;
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.propose.AcceptFileToDBServlet";
        document.form1.p_process.value="compCoditionDelete";
        document.form1.submit();
}

// 수강신청 제약 조건 추가
function whenInsertCondition(){

	  if(document.form1.s_company.value == "ALL"){
		alert("회사를 선택하여 주십시오.");
		return;
	  }

        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.propose.AcceptFileToDBServlet";
        document.form1.p_process.value="compCoditionInsert";
        document.form1.submit();
}

//-->
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


<form name="form1" method="post">
<input type=hidden name=p_process value="">
<input type=hidden name=p_action value="">
<input type="hidden" name="unum" value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../../images/admin/system/tit_new_comapply.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

	  <!----------------- form 시작 ----------------->
      <table  width="1000" border=0 cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="left">

            <table border=0 cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="left">

                  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <!------------------- 조건검색 시작 ------------------------->
                      <td width="90%" align="left" valign="middle">&nbsp; 
                      <%
                      	if(s_gadmin.equals("A1")){
                      %>
											<%= SelectCompanyBean.getCompany(box, false, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사 --> 					  
											<%
												} else {
											%>
											<%= SelectCompanyBean.getCompany(box, false, false)%><!-- getCompany(RequestBox, isChange, isALL)    회사 --> 					  
											<%	}	%>
					  </td>
					  <td width="10%" align="center">
	                    <%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
                      </td>
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
      </table>
      <!----------------- form 끝 ----------------->
	  <br>

	  <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class=sub_title><img src="/images/admin/common/icon.gif"> 수강신청 제약 등록</td>
		  <td align="right">
		  	<img src="/images/admin/button/btn_add.gif" style="cursor:hand" onClick="whenInsertCondition()">
		  </td>
        </tr>
      </table>

        <!----------------- 수강 제한 추가 ----------------->

        <table width="50%" class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="40%"class="table_title" colspan="2"><b>과정분류</b></td>
            <td width="20%"class="table_title" rowspan="2"><b>전체수강가능수</b></td>
            <td width="40%"class="table_title" colspan="2"><b>년간수강가능수</b></td>
          </tr>
          <tr> 
            <td width="20%" class="table_title"><b>직무</b></td>
            <td width="20%" class="table_title"><b>어학</b></td>
            <td width="20%" class="table_title"><b>직무</b></td>
            <td width="20%" class="table_title"><b>어학</b></td>
          </tr>
		  <tr>
            <td class="table_02_1"><input type="text" class="input" name="duty" size="5" maxlength="1" value="" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="language" size="5" maxlength="1" value="" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="allcondition" size="5" maxlength="1"  value="" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="yearduty" size="5" maxlength="1"  value="" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="yearlanguage" size="5" maxlength="1"  value="" onblur="numeric_chk(this)"></td>
          </tr>
		</table>
        <!----------------- 수강 제한 추가 ----------------->
		<br>
		<br>
		<table width="97%" border="0" cellspacing="0" cellpadding="0">
		  <tr> 
		    <td class=sub_title><img src="/images/admin/common/icon.gif"> 수강신청 제약 목록</td>
		  </tr>
		</table>
        <!----------------- 수강 제한 내용 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" height="25" class="table_title" rowspan="2"><b>NO</b></td>
            <td width="25%"class="table_title" rowspan="2"><b>회사</b></td>
            <td width="20%"class="table_title" colspan="2"><b>과정분류</b></td>
            <td width="15%"class="table_title" rowspan="2"><b>전체수강가능수</b></td>
            <td width="15%"class="table_title" colspan="2"><b>년간수강가능수</b></td>
            <td width="20%"class="table_title" rowspan="2"><b>기능</b></td>
          </tr>
		  <tr>
            <td width="10%"class="table_title"><b>직무</b></td>
            <td width="10%"class="table_title"><b>어학</b></td>
            <td width="10%"class="table_title"><b>직무</b></td>
            <td width="10%"class="table_title"><b>어학</b></td>
		  </tr>
<%
    if(ss_action.equals("go")){
        for(int i = 0; i < list.size(); i++) {
          DataBox dbox		= (DataBox)list.get(i);
          v_comp			= dbox.getString("d_comp");
          v_companynm		= dbox.getString("d_companynm");
          v_duty			= dbox.getInt("d_duty");
          v_language		= dbox.getInt("d_language");
          v_allcondition    = dbox.getInt("d_allcondition");
          v_yearduty		= dbox.getInt("d_yearduty");          
          v_yearlanguage    = dbox.getInt("d_yearlanguage");          
%>
          <tr> 
		  <input type="hidden" name="comp<%=i%>" value="<%=v_comp%>">
            <td class="table_02_1"><%=i+1%></td>
            <td class="table_02_1"><%=v_companynm%></td>
            <td class="table_02_1"><input type="text" class="input" name="duty<%=i%>" size="5" maxlength="1" value="<%=v_duty%>" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="language<%=i%>" size="5" maxlength="1" value="<%=v_language%>" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="allcondition<%=i%>" size="5" maxlength="1"  value="<%=v_allcondition%>" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="yearduty<%=i%>" size="5" maxlength="1"  value="<%=v_yearduty%>" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><input type="text" class="input" name="yearlanguage<%=i%>" size="5" maxlength="1"  value="<%=v_yearlanguage%>" onblur="numeric_chk(this)"></td>
            <td class="table_02_1"><img src="/images/admin/button/btn_modify.gif" onClick="javascript:whenUpdateCondition('<%=i%>')" style="cursor:hand"> <img src="/images/admin/button/btn_del.gif" onClick="javascript:whenDeleteCondition('<%=i%>')" style="cursor:hand"></td>
          </tr>
<%
       }
   }
%>

        </table>
        <!----------------- 수강제한 내용입력 끝 ----------------->
        <br>
      </td>
  </tr>

  <tr><td>

<%@ include file = "/learn/library/getJspName.jsp" %>

</form>
</body>
</html>
