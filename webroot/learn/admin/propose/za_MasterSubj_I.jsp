<%
//**********************************************************
//  1. 제      목: 교육그룹등록 화면
//  2. 프로그램명: za_EduGroup_I.jsp
//  3. 개      요: 교육그룹등록 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_mastercd  = box.getSession("p_mastercd");

    String s_userid  = box.getSession("userid");
    String s_name    = box.getSession("name");
    
    /* 교육 및 과정 차수 정보 selected Params*/
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //교육차수
    String  ss_action    = box.getString("p_action");
    
    String  v_proposetype= box.getString("p_proposetype");
    //String v_grcode = box.getString("p_grcode");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
/*
function changeHTML(){
  var edustr = "";
  if(document.form1.p_proposetype.value == "2"){
    document.form1.p_isedutarget.value = "N";
    edustr = "대상자선정이 불필요한 방식입니다.";
  }
  if(document.form1.p_proposetype.value == "3"){
    document.form1.p_isedutarget.value = "Y";
    edustr = "대상자선정이 필수인 방식입니다.";
  }
  if(document.form1.p_proposetype.value == "4"){
    document.form1.p_isedutarget.value = "Y";
    edustr = "대상자선정이 필수인 방식입니다.";
  }
  if(document.form1.p_proposetype.value == "5"){
    document.form1.p_isedutarget.value = "Y";
    edustr = "대상자선정이 필수인 방식입니다.";
  }

  layer.innerHTML = edustr;
}
*/
//값체크
function insert_check(){
/*
    if (blankCheck(document.form1.p_proposetype.value)) {
         alert('수강신청방식을 선택하세요.');
         document.form1.p_proposetype.focus();
         return;
    }
*/
    if (blankCheck(document.form1.p_masternm.value)) {
         alert('마스터과정을 입력하십시요.');
         document.form1.p_masternm.focus();
         return;
    }
    
    //document.form1.p_action.value = p_action;
    document.form1.target = "_self";
    document.form1.action = '/servlet/controller.propose.MasterSubjServlet';
    document.form1.p_process.value = 'insert';
    document.form1.submit(); 
}
    
function list(){
    //document.form1.p_action.value = p_action;
    document.form1.target = "_self";
    document.form1.action = '/servlet/controller.propose.MasterSubjServlet';
    document.form1.p_process.value = 'listPage';
    document.form1.submit(); 
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<!--div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99"></div-->

<form name="form1" method="post">
    <input type = "hidden" name="p_process"    value = "insertPage">
    <input type = "hidden" name="p_action"    value = "<%=ss_action%>">
    <input type = "hidden" name="p_pageno"     value = "">
    <input type = "hidden" name="p_searchtext" value = "">
    <input type = "hidden" name="p_search"     value = "name">
    <input type = "hidden" name="s_grcode"       value = "<%=ss_grcode%>">
    <input type = "hidden" name="s_grseq"       value = "<%=ss_grseq%>">
    <input type = "hidden" name="s_gyear"     value = "<%=ss_gyear%>">
    <input type = "hidden" name="p_mastercd"     value = "<%=v_mastercd%>">
    <input type = "hidden" name="s_userid"     value = "<%=s_userid%>">
    <input type = "hidden" name="s_name"     value = "<%=s_name%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top"> 


      <!----------------- title 시작 ----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/propose/p_title12.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	        <!----------------- title 끝 ----------------->
        <br>
        <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>등록</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->        <!----------------- 마스터과정등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
<!--
          <tr> 
            <td width="15%" height="25" class="table_title"><strong>수강신청방식</strong></td>
            <td class="table_02_2"><%//=CodeConfigBean.getCodeSelect("sugang_kind","p_proposetype",v_proposetype,"onchange=javascript:changeHTML()",1,"25")%>< !--  코드 셀렉트박스  -- ></td>
          </tr>

          <tr> 
            <td width="15%" height="25" class="table_title"><strong>대상자선정여부</strong></td>
            <td class="table_02_2">
              <div id="layer">수강신청방식을 선택하세요.</div>
            </td>
          </tr>
-->
              <input type=hidden  name="p_proposetype"  value="4">
              <input type=hidden  name="p_isedutarget"  value="Y">
          <tr> 
            <td width="15%" class="table_title"><strong>마스터과정명</strong></td>
            <td height="25" class="table_02_2"><input name="p_masternm" type="text" class="input" value=""> </td>
          </tr>
          <tr class="table_02_2"> 
            <td height="25" class="table_title"><strong>사번</strong></td>
            <td height="25" align="left"><%=s_userid%></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>작성자이름</strong></td>
            <td height="25" class="table_02_2"><%=s_name%></td>
          </tr>
        </table>
        <!----------------- 운영자등록 끝 ----------------->


        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td width=8></td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->





    </td>
  </tr>
</table>


<table>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     


</table>

</form>
</body>
</html>