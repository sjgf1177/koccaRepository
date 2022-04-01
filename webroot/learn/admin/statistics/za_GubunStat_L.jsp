<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 분야별현황
//  2. 프로그램명 : za_TotalStat_L.jsp
//  3. 개      요: 과정운영 통계 - 차수 New
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.07
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
<%@ page import = "com.credu.propose.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
   
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/style.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="/css/jqueryui/1/themes/ui-lightness/jquery-ui.css" />
<script type="text/javascript" src="/js/jquery/1/jquery.js"></script>
<script type="text/javascript" src="/js/jqueryui/1/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.js"></script>
<script type="text/javascript" src="/js/assets/prettify.js"></script>

</head>

<script type="text/javascript">
$(function(){
	$("select").multiselect({
		selectedList: 5
	});
});



</script>

<script language="JavaScript">
<!--

$(document).ready(function() { 
	$("#ss_year").change(
	function(){ 
		//alert('est');
		var newyear = $("#ss_year").val();//.indexOf(',');
		
		 $.ajax({ type : "POST", 			
	   		url : "/servlet/controller.common.TagServlet", 
	   		data: "sqlNum=onlinecourse&type=8&param="+newyear, 
	   		//beforeSend: function(){ $('#ss_subjseq').html('<img src="loading.gif" border="0"/>'); }, 
	   		success: function(msg){ 
	   			//alert(msg);
	   			if(msg.length > 0) { $('#s_subjseq').html(msg); } 
	   			$("select").multiselect();
	   			
	   		}  
	   	});//end ajax    
	
	});  	
		
	 $("#exec").bind('click',function(){ 
			var newyear = $("#ss_year").val();//.indexOf(',');
			var newsubjseq = $("#ss_subjseq").val();
			
			 $.ajax({ type : "POST", 			
		   		url : "/servlet/controller.course.CourseStateAdminServlet?p_process=selectGubunStat", 
		   		data: "&param="+newsubjseq, 
		   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
		   		success: function(msg){ 
		   			//alert(msg);
		   			if(msg.length > 0) { $('#s_list').html(msg); } 
		   			$("select").multiselect();
		   		}  
		   	});//end ajax    
		});  
	} 
); 

//-->
</script>

<style type="text/css">
li.tabnavi { font-size:12px; font-weight:bold; }
p {
	margin: 0;
	padding: 0;
}
#tabs_wrapper {
	width: 422px;
}
#tabs_container {
	border-bottom: 1px solid #ccc;
}
#tabs {
	list-style: none;
	padding: 5px 0 4px 0;
	margin: 0 0 0 10px;
	font: 0.75em arial;
}
#tabs li {
	display: inline;
}
#tabs li a {
	border: 1px solid #ccc;
	padding: 4px 6px;
	text-decoration: none;
	background-color: #eeeeee;
	border-bottom: none;
	outline: none;
	border-radius: 5px 5px 0 0;
	-moz-border-radius: 5px 5px 0 0;
	-webkit-border-top-left-radius: 5px;
	-webkit-border-top-right-radius: 5px;
}
#tabs li a:hover {
	background-color: #dddddd;
	padding: 4px 6px;
}
#tabs li.active a {
	border-bottom: 1px solid #fff;
	background-color: #fff;
	padding: 4px 6px 5px 6px;
	border-bottom: none;
}
#tabs li.active a:hover {
	background-color: #eeeeee;
	padding: 4px 6px 5px 6px;
	border-bottom: none;
}

#tabs li a.icon_accept {
	background-image: url(/learn/admin/statistics/accept.png);
	background-position: 5px;
	background-repeat: no-repeat;
	padding-left: 24px;
}
#tabs li a.icon_accept:hover {
	padding-left: 24px;
}

#tabs_content_container {
	border: 1px solid #ccc;
	border-top: none;
	padding: 10px;
	width: 400px;
}
.tab_content {
	display: none;
}
</style>
</head>
<body id="test"  onload="prettyPrint();" bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top">
      
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
         
          <td align="left">
          <div id="tabs_wrapper">
			<div id="tabs_container">
			
				<ul id="tabs">
					<li class="active tabnavi"><a href="#tab1">분야별</a></li>
					<li class="tabnavi"><a class="icon_accept" href="/servlet/controller.course.CourseStateAdminServlet?p_process=selectPreSeqStat">차수별</a></li>
					<li class="tabnavi"><a class="icon_accept" href="/servlet/controller.course.CourseStateAdminServlet?p_process=selectPreCourseStat">과정별 </a></li>
					<li class="tabnavi"><a class="icon_accept" href="/servlet/controller.course.CourseStateAdminServlet?p_process=selectPreSatisfyStat">만족도 </a></li>
					<li class="tabnavi"><a class="icon_accept" href="/servlet/controller.course.CourseStateAdminServlet?p_process=selectPreTotalStat">상세검색</a></li>
				</ul>
			</div>
			</div>	
          
          </td>
        </tr>
      </table>
      <br/>
      <table border="0" cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="left">
                  <table  cellspacing="0" cellpadding="0" width="100%" class="form_table" border="0">
                    <tr>
                      <td align="left" valign="middle">&nbsp;</td>
                      <td align="left" valign="middle" width="40">	년도별</td>
                      <td align="left" valign="middle" width="100">	
							<select name="ss_year" id="ss_year" multiple="multiple">
							<option value="2013">2013</option>
							<option value="2012">2012</option>
							<option value="2011">2011</option>
							<option value="2010">2010</option>
							<option value="2009">2009</option>
							<option value="2008">2008</option>
							<option value="2007">2007</option>
							<option value="2006">2006</option>
							<option value="2005">2005</option>
							<option value="2004">2004</option>
							</select>
						</td>	
					  <td align="left" valign="right" width="10">&nbsp;</td>
					  
					 <td align="left" valign="middle">구분별</td>
                     <td align="left" valign="middle">	
							<select name="ss_category" multiple="multiple" id="ss_category">
							<option value="01">일반</option>
							<option value="02">공공</option>
							<option value="03">기업</option>	
							<option value="04">대학</option>
							<option value="05">특화고</option>						
							</select>
                     </td>
                     </tr>
                     <tr>  
                     <td align="left" valign="right" width="10">&nbsp;</td>                   
                      <td align="left" valign="middle" width="40">차수별</td>
                       
                      <td align="left" valign="middle" width="450">
                     
                      		<div id="s_subjseq" style="width:450px;">                      	
							<select name="ss_subjseq" multiple="multiple" id="ss_subjseq" style="width:450px">
							</select>				
							</div>
                     </td> 
                     <td align="left" valign="right" width="10">&nbsp;</td>
                      <td align="left" valign="middle">
							<button id="exec">검색</button>
					 </td>	
					 
					 <td align="left" valign="right">&nbsp;</td>
					  
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
</td>
</tr></table>     
<br/>
<div id="s_list"> 
      <!----------------- 엑셀출력 버튼 시작 ----------------->
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top">      
     
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>종합현황</b></td>
          <td align="right">
          <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
             <!-- <button id="exCel">엑셀출력</button> -->
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>           
      <!----------------- 조회 시작 ----------------->
      
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>구분</b></td>
          <td class="table_title"><b>분야</b></td>
          <td class="table_title"><b>과목수</b></td>
          <td class="table_title"><b>교육인원</b></td>
          <td class="table_title"><b>수료인원</b></td>
          <td class="table_title"><b>수료율</b></td>
          <td class="table_title"><b>만족도</b></td>
         
        </tr>

        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="7">등록된 내용이 없습니다.</td>
        </tr>

     
      </table>
</td></tr></table>      
     
      
    <!----------------- 과정기준조회 끝 ----------------->
    <br>

<%@ include file = "/learn/library/getJspName.jsp" %>
 </div>
</body>
</html>