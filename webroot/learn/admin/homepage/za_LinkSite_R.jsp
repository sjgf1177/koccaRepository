<%
//**********************************************************
//  1. 제      목: LinkSite
//  2. 프로그램명: za_LinkSite_I.jsp
//  3. 개      요: 관련사이트 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 8. 03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String v_process   = box.getString("p_process");    
    String v_grcode			= "";  
    String v_sitenm			= "";  
    String v_url			= "";  
	String v_banner			= "";
	String v_isuse			= "";
	String v_islogin		= "";
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
	String v_ldate			= "";    

    int     v_seq		=  0;
    int     v_sort		=  0;
    int     i           =  0;    

	ArrayList list      = null;
    list = (ArrayList)request.getAttribute("selectInfo");

		DataBox dbox = (DataBox)list.get(0);	
		v_grcode	= dbox.getString("d_grcode");  
		v_seq		= dbox.getInt("d_seq");  
		v_sort		= dbox.getInt("d_sort");  
		v_sitenm	= dbox.getString("d_sitenm");  
		v_url		= dbox.getString("d_url");  
		v_banner	= dbox.getString("d_banner"); 
		v_isuse		= dbox.getString("d_isuse");  
		v_islogin	= dbox.getString("d_islogin");  

		v_useridparam	= dbox.getString("d_useridparam");
		v_nameparam		= dbox.getString("d_nameparam");
		v_resnoparam	= dbox.getString("d_resnoparam");
		v_param1		= dbox.getString("d_param1");
		v_paramvalue1	= dbox.getString("d_paramvalue1");
		v_param2		= dbox.getString("d_param2");
		v_paramvalue2	= dbox.getString("d_paramvalue2");
		v_param3		= dbox.getString("d_param3");
		v_paramvalue3	= dbox.getString("d_paramvalue3");
		v_param4		= dbox.getString("d_param4");
		v_paramvalue4	= dbox.getString("d_paramvalue4");
		v_param5		= dbox.getString("d_param5");
		v_paramvalue5	= dbox.getString("d_paramvalue5");
		v_param6		= dbox.getString("d_param6");
		v_paramvalue6	= dbox.getString("d_paramvalue6");

		v_ldate		= dbox.getString("d_ldate");		
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">

	function update() { 
		document.form1.target = "_self";
		document.form1.p_process.value = 'updatePage';        
		document.form1.submit();
	}

	function linksite_delete() { 

		if(!confirm("삭제하시겠습니까?")) {
			 return;
		}    	  	       
		
		document.form1.target = "_self";
		document.form1.p_process.value = 'delete';        
		document.form1.submit();
	}

</SCRIPT>    
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" action="/servlet/controller.homepage.LinkSiteServlet">
	<input type="hidden" name="p_process" value="">
	<input type="hidden" name="p_grcode" value="<%=v_grcode%>">
	<input type="hidden" name="p_seq" value="<%=v_seq%>">
	<input type="hidden" name="p_sort" value="<%=v_sort%>">

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
          <td class="table_title" width="17%">교육그룹</td>
          <td class="table_02_2" width="83%">
			<%=GetCodenm.get_grcodenm(v_grcode)%>
		  </td>
        </tr>
        <tr> 
          <td class="table_title">순서</td>
          <td class="table_02_2"> <%=v_sort%></td>
        </tr>
        <tr> 
          <td class="table_title">사이트명</td>
          <td class="table_02_2"> <%=v_sitenm%></td>
        </tr>
        <tr> 
          <td class="table_title">사이트 URL</td>
          <td class="table_02_2">
			<%if( !v_url.equals("")){%><a href="<%=v_url%>">
			<%}else{}%>  <%=v_url%></a>
		  </td>
        </tr>
        <tr>
          <td class="table_title">배너 파일</td>
          <td class="table_02_2"> 
		    <%if(!v_banner.equals("")){%><img src="<%=conf.getProperty("dir.linksitepath")%><%=v_banner%>"> <%}%>
		  </td>
        </tr>
        <tr> 
          <td class="table_title">사용여부</td>
          <td class="table_02_2"> <%=v_isuse%></td>
        </tr>
        <tr> 
          <td class="table_title">로그인여부</td>
          <td class="table_02_2"> <%=v_islogin%></td>
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
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_useridparam%></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">성명</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_nameparam%></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">주민등록번호</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_resnoparam%></td>
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
            <td nowrap bgcolor="#FFFFFF"><%=v_param1%></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_paramvalue1%></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><%=v_param2%></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_paramvalue2%></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><%=v_param3%></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_paramvalue3%></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><%=v_param4%></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_paramvalue4%></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><%=v_param5%></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_paramvalue5%></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><%=v_param6%></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><%=v_paramvalue6%></td>
          </tr>
        </table>
      <!----------------- 파라미터정보 끝----------------->
	  <br>
	  <table width="970" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td width="80%"></td>
		  <td align="right" valign="top">
		    <a href="javascript:update()" onfocus="this.blur()"><img src="/images/admin/button/btn_modify.gif" border="0">
		  </td>
		  <td width="2"></td>
		  <td align="right" valign="top"><a href="javascript:linksite_delete()" onfocus="this.blur()">
		   <img src="/images/admin/button/btn_del.gif" border="0">
		  </td>
		  <td width="2"></td>
		  <td align="right" valign="top">
			<a href="javascript:history.back();" onfocus="this.blur()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
		  </td>
		</tr>
	  </table>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
