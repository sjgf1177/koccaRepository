<%
//**********************************************************
//  1. 제      목: 수강료결제 관리
//  2. 프로그램명 : za_OffBillAdmin_L.jsp
//  3. 개      요: 행정서비스 - 수강료결제 관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.18
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
<%@ page import = "com.credu.library.PageUtil" %>
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

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">

<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/style.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="/css/jqueryui/1.8.2/themes/ui-lightness/jquery-ui.css" />
<script type="text/javascript" src="/js/jquery/1.8.2/jquery.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.8.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="/js/assets/prettify.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/flash/swfobject.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/amcharts.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/amfallback.js"></script>

<!-- 
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type='text/javascript' src='/script/jquery.form.js'></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language="JavaScript" src = "/script/hhi_lib.js"></script> -->
<script language="JavaScript">
<!--
// 검색


function whenExcel32() {   
	 //alert('1');
	 document.form1.target = 'mainFrame';
     $("#isExcel").val("true");
     document.form1.action='/servlet/controller.study.StudyTotalStatusServlet';
     document.form1.p_process.value = 'LearningStatusExcelByTotal';
     //document.form1.submit();
     $("#isExcel").val("false");

 }


$("#tabs").tabs({
    select: function(event, ui) {
       window.location = $.data(ui.tab, 'href.tabs');
    }
});

/* 
function whenExcel() {
	var newyear = $("#ss_year").val();//.indexOf(',');
	var newmonth = $("#ss_month").val();
	var newcate =  $("#ss_category").val();				
	var newsubjseq = $("#ss_subjseq").val();
	var newpart =  $("#ss_part").val();
	$("#isExcel").val("true");
	//document.form1.s_action.value = "go";
	//document.form1.p_process.value = 'selectTotalStat';
	document.form1.action="/servlet/controller.course.CourseStateAdminServlet?isExcel=true&p_process=selectTotalStat&param="+newsubjseq+"&param1="+newpart;
	document.form1.submit();
	$("#isExcel").val("false");
}
 */

$(document).ready(function() { 
	$("#ss_category, #ss_year").change(   // 분류선택
	function(){ 
		var newyear = $("#ss_year").val();//.indexOf(',');
		var newcate =  $("#ss_category").val();
		
		 $.ajax({ type : "POST", 			
	   		url : "/servlet/controller.common.TagServlet", 
	   		data: "sqlNum=offlinecourse&type=11&param="+newyear+"&param1="+newcate, 
	   		success: function(msg){ 
	   			if(msg.length > 0) { $('#s_subjcourse').html(msg); } 
	   			$("select").multiselect();
	   		}  
	   	});//end ajax    
	
	});  	
	
		
 	$('#b_subj, #b_age,  #b_detail').click(function(e) {
 		var newyear =  $("#ss_year").val();				
		var newsubjcourse = $("#ss_subjcourse").val();
		
 		if ($(e.target).is("#b_subj")) { // 과정별
 			$.ajax({ type : "POST", 			
		   		url : "/servlet/controller.off.OffStatisticsConditionServlet?p_process=selectCourseStat", 
		   		data: "&param="+newyear+"&param1="+newsubjcourse, 
		   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); }, 
		   		success: function(msg){ 
		   			if(msg.length > 0) { $('#s_list').html(msg); } 
		   		}  
		   	});//end ajax   
		   	
 		
 		} else if ($(e.target).is("#b_age")) { // 연령분포
 			$.ajax({ type : "POST", 			
 				url : "/servlet/controller.off.OffStatisticsConditionServlet?p_process=selectAgeStat", 
 				data: "&param="+newyear+"&param1="+newsubjcourse, 
		   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); }, 
		   		success: function(msg){ 
		   			if(msg.length > 0) { $('#s_list').html(msg); } 
		   		}  
		   	});//end ajax   
 		
 		} else if ($(e.target).is("#b_detail")) { // 상세보기
 			$.ajax({ type : "POST", 			
 				url : "/servlet/controller.off.OffStatisticsConditionServlet?p_process=selectDetail", 
 				data: "&param="+newyear+"&param1="+newsubjcourse,
		   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); }, 
		   		success: function(msg){ 
		   			if(msg.length > 0) { $('#s_list').html(msg); } 
		   		}  
		   	});//end ajax   
 		}
 	});
}); 

//-->
</script>
</head>

<body id="test" onload="prettyPrint();" bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" >
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/portal/s.1_off13.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
      </td>
     </tr>
     <tr>
     <td align="center" valign="top">
     <br/> 

<!------------------------------------- form 시작 ------------------------------------->
 <table cellspacing="0" cellpadding="1" class="form_table_out" >
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="left">
                  <style>
                  table#cellp td { padding:3px 0px 3px 0px; }
                  dl,dt,dd { padding:0; margin:0; list-style:none; }
                  </style>
                  <table id="cellp" border="0" cellspacing="0" cellpadding="0" width="100%"  class="form_table">
                    <tr>
                      <td align="left" valign="middle">&nbsp;</td>
                      <td align="left" valign="middle">	년도별</td>
                      <td align="left" valign="middle">	
							<select name="ss_year" id="ss_year" multiple="multiple" style="width:30px">
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
							<option value="2003">2003</option>
							</select>
						</td>		
					
                     <td align="left" valign="middle">분류</td>
                     <td align="left" valign="middle">	
							<select name="ss_category" multiple="multiple" id="ss_category" size="8" style="width:350px">
							<optgroup label="방송영상" >
							<option value="S01001">산업계직무재교육</option>
							<option value="S01002">차세대인재양성교육</option>
							<option value="S01003">수탁교육</option>
							<option value="S01004">3D 입체콘텐츠 전문인력양성</option>
							</optgroup>
							<optgroup label="게임제작">
							<option value="S02001">산업계직무재교육</option>
							<option value="S02002">차세대인재양성교육</option>
							<option value="S02003">수탁교육</option>
							<option value="S02004">3D 입체콘텐츠 전문인력양성</option>
							</optgroup>		
							<optgroup label="기획창작">
							<option value="S03001">산업계직무재교육</option>
							<option value="S03002">차세대인재양성교육</option>
							<option value="S03003">수탁교육</option>
							<option value="S03004">3D 입체콘텐츠 전문인력양성</option>
							</optgroup>								
							</select>
                     </td>                     
                     </tr>
					 <tr>
                     <td align="left" valign="middle">&nbsp;</td>
                     <td align="left" valign="middle" >과정별</td>
                     <td align="left" valign="middle" colspan="4" width="450">
                     	<dl>
                     	  <dd style="float:left;" id="s_subjcourse"><select name="ss_subjcourse" multiple="multiple" id="ss_subjcourse" style="width:500px"></select></dd>
                     	  <dd style="float:left; margin-left:5px;"></dd>
                     	</dl>
                     </td>
                     <td></td>  
                     </tr>
                     
                     <tr>
                     <td align="left" valign="middle">&nbsp;</td>
                     <td align="left" valign="middle" colspan="6" >
                     	<dl>
                     	  <dd style="float:left; margin-left:5px;"><button id="b_subj">수강생현황</button></dd>
                     	  <dd style="float:left; margin-left:5px;"><button id="b_age">연령분포</button></dd>
                     	  <dd style="float:left; margin-left:5px;"><button id="b_detail">상세보기</button></dd>
                     	</dl>
                     </td>
                     
                     </tr>
                  </table>
                </td>
              </tr>
            
            </table>
          </td>
        </tr>
      </table><br>
      <!----------------- 엑셀출력 버튼 시작 ----------------->
      <div id="s_list">
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>통계현황</b></td>
          <td align="right">
          <!-- <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a> -->
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
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>No</b></td>
          <td class="table_title"><b>과정명</b></td>
          <td class="table_title"><b>교육인원</b></td>
          <td class="table_title"><b>수료</b></td>
          <td class="table_title"><b>미수료</b></td>
          <td class="table_title"><b>수료율</b></td>
         </tr>

        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="15">등록된 내용이 없습니다.</td>
        </tr>

     
      </table>
      </div>
    <!----------------- 과정기준조회 끝 ----------------->
    <br>
    </td>
  </tr>
</table>
<script type="text/javascript">
//$("select").multiselect().multiselectfilter();
$(function(){
	$("select").multiselect({
		selectedList: 5
	});
});
</script>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>