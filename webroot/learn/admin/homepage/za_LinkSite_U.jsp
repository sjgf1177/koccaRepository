<%
//**********************************************************
//  1. 제      목: 교육과정정보 수정관리
//  2. 프로그램명: za_cpcourse_u.jsp
//  3. 개      요: 교육과정정보 수정관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 황창근 2004.12.23
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_process   = box.getString("p_process");    
    String  v_grcode    = box.getStringDefault("p_grcode",box.getSession("tem_grcode"));   
    String  v_sitenm	= "";  
    String  v_url		= "";  
	String  v_banner	= "";
	String  v_isuse		= "";
	String  v_islogin	= "";
    String  v_ldate     = "";    
	String v_useridparam    = "";
	String v_nameparam      = "";
	String v_resnoparam     = "";
	String v_param1         = "";
	String v_paramvalue1    = "";
	String v_param2         = "";
	String v_paramvalue2    = "";
	String v_param3         = "";
	String v_paramvalue3    = "";
	String v_param4         = "";
	String v_paramvalue4    = "";
	String v_param5         = "";
	String v_paramvalue5    = "";
	String v_param6         = "";
	String v_paramvalue6    = "";


    int     v_seq		=  0;
    int     v_sort		=  0;
    int     i           =  0;    

	ArrayList list      = null;
    list = (ArrayList)request.getAttribute("selectInfo");

	DataBox dbox1 = (DataBox)list.get(0);	

	if(dbox1 != null){
		v_grcode	= dbox1.getString("d_grcode");  
		v_seq		= dbox1.getInt("d_seq");  
		v_sort		= dbox1.getInt("d_sort");  
		v_sitenm	= dbox1.getString("d_sitenm");  
		v_url		= dbox1.getString("d_url");  
		v_banner	= dbox1.getString("d_banner"); 
		v_isuse		= dbox1.getString("d_isuse");  
		v_islogin	= dbox1.getString("d_islogin");  

		v_useridparam    = dbox1.getString("d_useridparam");
		v_nameparam      = dbox1.getString("d_nameparam");
		v_resnoparam     = dbox1.getString("d_resnoparam");
		v_param1         = dbox1.getString("d_param1");
		v_paramvalue1    = dbox1.getString("d_paramvalue1");
		v_param2         = dbox1.getString("d_param2");
		v_paramvalue2    = dbox1.getString("d_paramvalue2");
		v_param3         = dbox1.getString("d_param3");
		v_paramvalue3    = dbox1.getString("d_paramvalue3");
		v_param4         = dbox1.getString("d_param4");
		v_paramvalue4    = dbox1.getString("d_paramvalue4");
		v_param5         = dbox1.getString("d_param5");
		v_paramvalue5    = dbox1.getString("d_paramvalue5");
		v_param6         = dbox1.getString("d_param6");
		v_paramvalue6    = dbox1.getString("d_paramvalue6");
	}

%>


<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

<SCRIPT LANGUAGE="JavaScript">
  function update() { 
  	
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

		if(!confirm("수정하시겠습니까?")) {
  			 return;
		}   
			
        document.form1.target = "_self";
        document.form1.p_process.value = 'update';        
        document.form1.submit();
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

<form name="form1" enctype= "multipart/form-data"  method="post" action="/servlet/controller.homepage.LinkSiteServlet">
	<input type="hidden" name="p_process"		value="">
	<input type="hidden" name="p_grcode"		value="<%=v_grcode%>">
	<input type="hidden" name="p_seq"			value="<%=v_seq%>">
	<input type="hidden" name="p_banner1"		value="<%=v_banner%>">

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
          <td class="table_02_2" width="83%"><%//=dbox.getString("d_grcode")%>
			<%//= SelectEduBean.getGrcodeSelect("p_grcode", v_grcode, 1)%>
			<%=GetCodenm.get_grcodenm(v_grcode)%>
		  </td>
        </tr>
        <tr> 
          <td class="table_title">*사이트명</td>
          <td class="table_02_2">
			<input name="p_sitenm" type="text" class="input" size="50" maxlength="50" value="<%=v_sitenm%>">
		  </td>
        </tr>
        <tr> 
          <td class="table_title">*사이트 URL</td>
          <td class="table_02_2">
		    <input name="p_url" type="text" class="input" size="100" maxlength="50" value="<%=v_url%>">
		  </td>
        </tr>
        <tr>
          <td class="table_title">배너 파일</td>
          <td class="table_02_2">
			<%if(!v_banner.equals("")){%><img src="<%=conf.getProperty("dir.linksitepath")%><%=v_banner%>"> <%}%>
			변경시 : <input type="file" name="p_banner" size="50" class="input"></td>
        </tr>
        <tr> 
          <td class="table_title">사용여부</td>
          <td class="table_02_2">
			<select name="p_isuse">
				<option value="Y" <%=v_isuse.equals("Y")?"selected":""%>>Y</option>
				<option value="N" <%=v_isuse.equals("N")?"selected":""%>>N</option>
			</select>
		  </td>
        </tr>
        <tr> 
          <td class="table_title">로그인여부</td>
          <td class="table_02_2">
			<select name="p_islogin">
				<option value="N" <%=v_islogin.equals("N")?"selected":""%>>N</option>
				<option value="Y" <%=v_islogin.equals("Y")?"selected":""%>>Y</option>
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
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_useridparam" maxlength="16"  class="input" value="<%=v_useridparam%>" ></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">성명</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_nameparam" maxlength="16"  class="input" value="<%=v_nameparam%>" ></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">주민등록번호</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_resnoparam" maxlength="16"   class="input" value="<%=v_resnoparam%>"></td>
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
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param1" maxlength="16"  class="input"  value="<%=v_param1%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue1" maxlength="16"  class="input"  value="<%=v_paramvalue1%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param2" maxlength="16"  class="input"  value="<%=v_param2%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue2" maxlength="16"  class="input"  value="<%=v_paramvalue2%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param3" maxlength="16"  class="input"  value="<%=v_param3%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue3" maxlength="16"  class="input"  value="<%=v_paramvalue3%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param4" maxlength="16"  class="input"  value="<%=v_param4%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue4" maxlength="16"  class="input"  value="<%=v_paramvalue4%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param5" maxlength="16"  class="input"  value="<%=v_param5%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue5" maxlength="16"  class="input"  value="<%=v_paramvalue5%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param6" maxlength="16"  class="input"  value="<%=v_param6%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue6" maxlength="16"  class="input"  value="<%=v_paramvalue6%>"></td>
          </tr>
        </table>
      <!----------------- 파라미터정보 끝----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
	  <table width="970" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td width="90%"></td>
		  <td width="45" align="right" valign="top">
		    <a href="javascript:update()" onfocus="this.blur()"><img src="/images/admin/button/btn_save.gif" border="0">
		  </td>
		  <td width="5"> </td>
		  <td width="45" align="right" valign="top">
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