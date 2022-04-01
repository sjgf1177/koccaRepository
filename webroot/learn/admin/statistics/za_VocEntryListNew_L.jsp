<%
//**********************************************************
//  1. 제      목: 회원운영 통계 - 차수별 현황
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
<link rel="stylesheet" type="text/css" href="/css/assets/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/style.css" />
<link rel="stylesheet" type="text/css" href="/css/assets/prettify.css" />
<link rel="stylesheet" type="text/css" href="/css/jqueryui/1.9.2/themes/redmond/jquery-ui.css" />
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.js"></script>
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script> -->
<script type="text/javascript" src="/js/jqueryui/1.9.2/jquery-ui.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.js"></script>
<script type="text/javascript" src="/js/assets/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="/js/assets/prettify.js"></script>

<script language="JavaScript">
<!--
// 검색



$(document).ready(function() { 
	 	
	 	$('#b_all, #b_1to1, #b_qna, #b_subj, #b_knowledge').click(function(e) {
	 		//alert('start');
	 		var newyear =  $("#ss_year").val();				
			var ss_month = $("#ss_month").val();
			var ss_part = $("#ss_part").val();
			
	 		if ($(e.target).is("#b_all")) {  // 전체
	 			$.ajax({ type : "POST", 			
			   		url : "/servlet/controller.statistics.VocStatusticServlet?p_process=selectVocAllStat", 
			   		data: "&param="+newyear, 
			   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
			   		success: function(msg){ 
			   			//alert(msg);
			   			if(msg.length > 0) { $('#s_list').html(msg); } 
			   			//$("select").multiselect();
			   		}  
			   	});//end ajax   
	 		} else if ($(e.target).is("#b_1to1")) { // 1:1문의
	 			 $.ajax({ type : "POST", 			
	 				url : "/servlet/controller.statistics.VocStatusticServlet?p_process=select1to1Stat", 
			   			data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_part,
				   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
				   		success: function(msg){ 
				   			if(msg.length > 0) { $('#s_list').html(msg); } 
				   			$("select").multiselect();
				   		}  
				   	});//end ajax    
			   	
	 		} else if ($(e.target).is("#b_qna")) { // 홈페이지Q/A
	 			$.ajax({ type : "POST", 			
	 				url : "/servlet/controller.statistics.VocStatusticServlet?p_process=selectQnaStat", 
			   		data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_part,
			   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); }, 
			   		success: function(msg){ 
			   			if(msg.length > 0) { $('#s_list').html(msg); } 
			   		}  
			   	});//end ajax   
			   	
	 		} else if ($(e.target).is("#b_subj")) { // 과정질문방
	 			$.ajax({ type : "POST", 			
	 				url : "/servlet/controller.statistics.VocStatusticServlet?p_process=selectSubjQnaStat",
			   		data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_part,
			   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); }, 
			   		success: function(msg){ 
			   			if(msg.length > 0) { $('#s_list').html(msg); } 
			   		}  
			   	});//end ajax   
	 		} else if ($(e.target).is("#b_knowledge")) { // 지식팩토리
	 			$.ajax({ type : "POST", 			
	 				url : "/servlet/controller.statistics.VocStatusticServlet?p_process=selectKnowledgeStat",
			   		data: "&param="+newyear+"&param1="+ss_month+"&param2="+ss_part,
			   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); }, 
			   		success: function(msg){ 
			   			if(msg.length > 0) { $('#s_list').html(msg); } 
			   		}  
			   	});//end ajax   
	 		} 
	 	});
	} 
	
); 

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
          <td align="left"><img src="/images/admin/statistics/tit_sum_d2.gif"></td>
          
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
    
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0"  class="form_table_bg" >
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
                      <td align="left" valign="middle" width="50">	년도별</td>
                      <td align="left" valign="middle" width="200">	
							<select name="ss_year" id="ss_year" multiple="multiple" >
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
							<option value="2005">2003</option>
							<option value="2004">2002</option>
							</select>
						</td>		
					
                     	<td align="left" valign="middle" width="50">월   별</td>
						<td align="left" valign="middle">	
							<select title="월선택" multiple="multiple" name="ss_month" id="ss_month" size="5" >
							<option value="01">1월</option>
							<option value="02">2월</option>
							<option value="03">3월</option>
							<option value="04">4월</option>
							<option value="05">5월</option>
							<option value="06">6월</option>
							<option value="07">7월</option>
							<option value="08">8월</option>
							<option value="09">9월</option>
							<option value="10">10월</option>
							<option value="11">11월</option>
							<option value="12">12월</option>
							</select>
                     	</td>
	                      <td align="left" valign="middle" width="50">분야별</td>
	                      <td align="left" valign="middle"  width="450">
								<select name="ss_part" id="ss_part" multiple="multiple">
								<option value="B0">방송</option>
								<option value="K0">문화</option>
								<option value="G0">게임</option>						
								</select>
						 </td>	
                     </tr>
                
                     
                    <tr>
                     <td align="left" valign="middle">&nbsp;</td>
                     <td align="left" valign="middle" colspan="6" >
                     	<dl>
                     	 <!--  <dd style="float:left; margin-left:5px;"><button id="b_all">전&nbsp;&nbsp;체</button> </dd> -->
                     	  <dd style="float:left; margin-left:5px;"><button id="b_1to1">1:1문의</button></dd>
                     	  <dd style="float:left; margin-left:5px;"><button id="b_qna">홈페이지Q&A</button></dd>
                     	  <dd style="float:left; margin-left:5px;"><button id="b_subj">과정질문방</button></dd>
                     	  <dd style="float:left; margin-left:5px;"><button id="b_knowledge">지식팩토리</button></dd>
                     	</dl>
                     </td>
                     </tr> 
                  </table>
                  
                </td>
              </tr>
            
            </table>
          </td>
        </tr>
      </table>
      <br/>
      <!----------------- 엑셀출력 버튼 시작 ----------------->
      <div id="s_list">
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>전체</b></td>
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
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>No</b></td>
          <td class="table_title"><b>구분</b></td>
          <td class="table_title"><b>분류1</b></td>
          <td class="table_title"><b>분류2</b></td>
          <td class="table_title"><b>제목</b></td>
          <td class="table_title"><b>과정</b></td>
          <td class="table_title"><b>성명</b></td>
          <td class="table_title"><b>월별</b></td>
         </tr>

        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="8">등록된 내용이 없습니다.</td>
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