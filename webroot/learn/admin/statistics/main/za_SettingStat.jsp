<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) {
	    box = RequestManager.getBox(request);
	}

    String  ss_grcode	= box.getStringDefault("s_grcode","N000001");		   //교육그룹
	String  ss_gyear	 = box.getString("s_gyear");			//년도
%>
<html>
<head>
<title>통계설정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/style.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="/css/jqueryui/1.9.2/themes/redmond/jquery-ui.css" />
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.9.2/jquery-ui.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="/js/assets/prettify.js"></script>

<script language="Javascript">
<!--
	function select_List(){
 		var s2_grcode = $("#s_grcode").val();
 		var s2_gyear = $("#s_gyear").val();

		if(s2_grcode == ""){alert("교육그룹을 선택하세요.");return;}
		if(s2_gyear == ""){return;}
		
			$.ajax({ type : "POST", 			
	   		url : "/servlet/controller.statistics.MainStatusticServlet?p_process=setStatList", 
	   		data: "&param_gubun=S&param1="+s2_grcode+"&param2="+s2_gyear, 
	   		beforeSend: function(){
					$("#list").html('<img src="/learn/user/contents/loading.gif" border="0"/>');
				},
	   		success: function(msg){ 
	   			if(msg.length > 0) {
		   			 $("#list").html(msg);
	   			} 
	   		}  
	   	});//end ajax
	}   

	function update_stat(grseq, stat){
		var s_stat = "";
		var s2_grcode = $("#s_grcode").val();
 		var s2_gyear = $("#s_gyear").val();

 		if(stat == "Y"){s_stat = "N";}else{s_stat = "Y";}
 		
		$.ajax({ type : "POST", 			
	   		url : "/servlet/controller.statistics.MainStatusticServlet?p_process=setStatList", 
	   		data: "&param_gubun=U&param1="+s2_grcode+"&param2="+s2_gyear+"&param3="+grseq+"&param4="+s_stat, 
	   		beforeSend: function(){
				},
	   		success: function(msg){ 
	   			if(msg.length > 0) {
		   			 $("#list").html(msg);
	   			} 
	   		}  
	   	});//end ajax
	}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="text-align: center;">
<h3>교육운영 통계 설정</h3>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
교육그룹 : <kocca_select:select name="s_grcode" sqlNum="course.0001" onChange="select_List();changes_gyear(this.value);" selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />&nbsp;&nbsp;
교육연도 : <kocca_select:select name="s_gyear" sqlNum="course.year" onChange="javascript:select_List();" param="<%=ss_grcode%>" selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
<br/><br/>
<div id="list" style="text-align: center;">
	<table class="table_out" cellspacing="1" cellpadding="5">
		<tr>
			<td colspan="3" class="table_top_line"></td>
		</tr>
		<tr>
            <td class="table_title" height="25" width="10%"><b>No</b></td>
   	        <td class="table_title"><b>교육명</b></td>
   	        <td class="table_title" width="20%"><b>사용유무</b></td>
		</tr>
		<tr>
			<td class="table_02_1" colspan="3">교육그룹, 교육연도를 선택해 주세요.</td>
		</tr>
	</table>		
</div>
</body>
</html>
