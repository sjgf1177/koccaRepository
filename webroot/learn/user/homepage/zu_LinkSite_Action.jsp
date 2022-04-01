<%
//**********************************************************
//  1. 제      목: LinkSite
//  2. 프로그램명: za_LinkSite_L.jsp
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
    if (box == null) box = RequestManager.getBox(request);

	String p_grcode = request.getParameter("p_grcode");
	String p_seq = request.getParameter("p_seq");
	box.put("p_grcode", p_grcode);
	box.put("p_seq", p_seq);

	LinkSiteBean bean = new LinkSiteBean();            

	ArrayList list = bean.SelectView(box);
//request.setAttribute("selectInfo", list);

	String v_grcode     = "";
	String v_seq		= "";
	String v_sitenm     = "";
	String v_url		= "";
	String v_banner		= "";
	String v_isuse      = "";
	String v_islogin	= "";

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
	String v_return_url		= "";

	DataBox dbox = (DataBox)list.get(0);

	if(dbox != null){
		v_grcode		= dbox.getString("d_grcode");
		v_seq			= dbox.getString("d_seq");
		v_sitenm		= dbox.getString("d_sitenm");
		v_url			= dbox.getString("d_url");
		v_banner		= dbox.getString("d_banner");
		v_islogin		= dbox.getString("d_islogin");

		v_useridparam    = dbox.getString("d_useridparam");
		v_nameparam      = dbox.getString("d_nameparam");
		v_resnoparam     = dbox.getString("d_resnoparam");

		v_param1         = dbox.getString("d_param1");
		v_paramvalue1    = dbox.getString("d_paramvalue1");
		v_param2         = dbox.getString("d_param2");
		v_paramvalue2    = dbox.getString("d_paramvalue2");
		v_param3         = dbox.getString("d_param3");
		v_paramvalue3    = dbox.getString("d_paramvalue3");
		v_param4         = dbox.getString("d_param4");
		v_paramvalue4    = dbox.getString("d_paramvalue4");
		v_param5         = dbox.getString("d_param5");
		v_paramvalue5    = dbox.getString("d_paramvalue5");
		v_param6         = dbox.getString("d_param6");
		v_paramvalue6    = dbox.getString("d_paramvalue6");
	}

	v_return_url = v_url+"?";
	if(!v_useridparam.equals("")) v_return_url += "&"+v_useridparam+"="+box.getSession("userid");
	if(!v_nameparam.equals("")) v_return_url += "&"+v_nameparam+"="+box.getSession("name");
	if(!v_resnoparam.equals("")) v_return_url += "&"+v_resnoparam+"="+box.getSession("resno");

	if(!v_param1.equals("")) v_return_url += "&"+v_param1+"="+v_paramvalue1;
	if(!v_param2.equals("")) v_return_url += "&"+v_param2+"="+v_paramvalue2;
	if(!v_param3.equals("")) v_return_url += "&"+v_param3+"="+v_paramvalue3;
	if(!v_param4.equals("")) v_return_url += "&"+v_param4+"="+v_paramvalue4;
	if(!v_param5.equals("")) v_return_url += "&"+v_param5+"="+v_paramvalue5;
	if(!v_param6.equals("")) v_return_url += "&"+v_param6+"="+v_paramvalue6;

%>
<html>
<head>
<script language="JavaScript" type="text/JavaScript">
<!--
function goSite(url) {
	if(url != "?"){
		location.href=url;
	}else{
		alert('잘못된 url 입니다');
		self.close();
	}
}
-->
</script>
</head>
<body onload="goSite('<%=v_return_url%>')">
