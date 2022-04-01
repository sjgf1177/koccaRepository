<%
//**********************************************************
//  1. 제      목: 관련사이트 등록관리
//  2. 프로그램명: za_LinkSite_I.jsp
//  3. 개      요: 관련사이트 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005.08.03
//  7. 수      정: 정경진 2005.08.17
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");
%>

<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

<SCRIPT LANGUAGE="JavaScript">
  function insert() { 

        if (document.form1.p_grcode.value == "") {
            alert("교육그룹을 선택하세요.!");
            document.form1.p_grcode.focus();
            return;
        }
        if (document.form1.p_sitenm.value == "") {
            alert("사이트명을 입력하세요.!");
            document.form1.p_sitenm.focus();
            return;
        }
        if (document.form1.p_url.value == "http://") {
            alert("사이트 URL을 입력하세요.!");
            document.form1.p_url.focus();
            return;
        }
  	
		if(!confirm("등록하시겠습니까?")) {
  			 return;
		}    	  	       
		
        document.form1.target = "_self";
        document.form1.p_process.value = 'insert';        
        document.form1.submit();
    }

-->
</SCRIPT>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form1" enctype= "multipart/form-data"  method="post" action="/servlet/controller.homepage.LinkSiteServlet">
    <input type="hidden" name="p_process"		value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/homepage/tit_site.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
      <br>

      <table width="97%"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="17%">*교육그룹</td>
          <td class="table_02_2" width="83%">
			<%= SelectEduBean.getGrcodeSelect("p_grcode", "", 1)%>
		  </td>
        </tr>
        <tr> 
          <td class="table_title">*사이트명</td>
          <td class="table_02_2">
			<input name="p_sitenm" type="text" class="input" size="50" maxlength="50">
		  </td>
        </tr>
        <tr> 
          <td class="table_title">*사이트 URL</td>
          <td class="table_02_2">
		    <input name="p_url" type="text" class="input" size="100" maxlength="120" value="http://">
		  </td>
        </tr>
        <tr>
          <td class="table_title">배너 파일명</td>
          <td class="table_02_2"><input type="file" name="p_banner" size="80" class="input"></td>
        </tr>
        <tr> 
          <td class="table_title">사용여부</td>
          <td class="table_02_2">
			<select name="p_isuse">
				<option value="Y">Y</option>
				<option value="N">N</option>
			</select>
		  </td>
        </tr>
        <tr> 
          <td class="table_title">로그인여부</td>
          <td class="table_02_2">
			<select name="p_islogin">
				<option value="N">N</option>
				<option value="Y">Y</option>
			</select>
		  </td>
        </tr>
	  </table>
	  <br>
      <!----------------- 파라미터정보 시작----------------->
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC" class="table_out">
          <tr align="center" bgcolor="#EAF4FD">
            <td height="26" colspan="3" nowrap class="tdtitle"><div align="center"> 기본 파라메터</div></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap class="table_title">Value</td>
            <td height="26" nowrap class="table_title">Param</td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">아이디</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_useridparam" maxlength="16" class="input"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">성명</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_nameparam" maxlength="16" class="input"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">주민등록번호</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_resnoparam" maxlength="16"   class="input"></td>
          </tr>
        </table>
		        <br>
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC" class="table_out">
          <tr align="center" bgcolor="#EAF4FD">
            <td height="26" colspan="2" nowrap class="tdtitle"><div align="center"> 추가 파라메터</div></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td width="388" nowrap class="table_title">Param</td>
            <td width="348" height="26" nowrap class="table_title">Value</td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param1" maxlength="16" class="input"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue1" maxlength="16" class="input"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param2" maxlength="16" class="input"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue2" maxlength="16" class="input"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param3" maxlength="16" class="input"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue3" maxlength="16" class="input"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param4" maxlength="16" class="input"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue4" maxlength="16" class="input"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param5" maxlength="16" class="input"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue5" maxlength="16" class="input"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param6" maxlength="16" class="input"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue6" maxlength="16" class="input"></td>
          </tr>
        </table>
      <!----------------- 파라미터정보 끝----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
	  <table width="970" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td align="right" valign="top"><a href="javascript:insert()" onfocus="this.blur()">
		   <img src="/images/admin/button/btn_save.gif" border="0">
		   </td>
		  <td width="8"></td>
		  <td width="32" align="right" valign="top">
			<a href="javascript:history.back();" onfocus="this.blur()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a>
		  </td>
		</tr>
	  </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
    </td>
  </tr>
    <tr height="100">
      <td>
      <%@ include file = "/learn/library/getJspName.jsp" %>
      </td>
  </tr>
</table>

</form>

</body>
</html>