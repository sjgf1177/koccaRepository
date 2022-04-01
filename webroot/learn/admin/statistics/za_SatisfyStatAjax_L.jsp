<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 과정별 현황
//  2. 프로그램명 : za_TotalStat_L.jsp
//  3. 개      요: 과정운영 통계 - 전체
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
    
    int i = 0;
    
    String v_areaname = "";
    String v_cate = "";
    String v_subjnm = "";
    int v_subj_cnt = 0;
    int v_user_cnt = 0;
    int v_grad_cnt = 0;
    int v_ngrad_cnt = 0;
    int v_sul_cnt = 0;
    int v_sul1_cnt = 0;
    
    double v_grad_rate = 0;
    double v_sul_rate = 0;
    double v_sul_rate2 = 0;
    
    int 	v_rowspantmp = 0;
    int		v_total_subj_cnt	= 0;
    int		v_total_user_cnt	= 0;
    int		v_total_sul_cnt	= 0;
    int		v_total_man_cnt		= 0;
    int		v_total_woman_cnt	= 0;
    int		v_total_man_rate	= 0;
    
    int		v_sum_subj_cnt	= 0;
    int		v_sum_user_cnt	= 0;
    int		v_sum_sul_cnt	= 0;
    
    double 	v_total_grad_rate = 0;
    double  v_total_sul_rate = 0;
    double  v_sum_sul_rate = 0;
    double v_totalsul_rate = 0;
    
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function whenExcel() {   
	 //alert('3');
	 document.form1.target = 'mainFrame';
     $("#isExcel").val("true");
     document.form1.submit();
     $("#isExcel").val("false");

 }
 

function ordering(sortitem) {   
	 	 
	 var newpart =  $("#param1").val();				
	 var newsubjseq = $("#param").val();
	 var newsubjcourse = $("#param2").val();
	 
	 $.ajax({ type : "POST", 			
	   		url : "/servlet/controller.course.CourseStateAdminServlet?p_process=selectSatisfyStat", 
	   		data: "&param="+newsubjseq+"&param1="+newpart+"&param2="+newsubjcourse+"&param3="+sortitem, 
	   		beforeSend: function(){ $('#s_list').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); }, 
	   		success: function(msg){ 
	   			if(msg.length > 0) { $('#s_list').html(msg); } 
	   		}  
	  });//end ajax  
	   	

} 
//-->
</SCRIPT>  
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>만족도검색</b></td>
          <td align="right">
          <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>           
      <!----------------- 조회 시작 ----------------->
      <form name = "form1" method = "post">
        <input type="hidden" name="p_process" value="selectSatisfyStat">
        <input type="hidden" name="isExcel" id="isExcel" value="false">
        <input type="hidden" name="p_action" value ="/servlet/controller.course.CourseStateAdminServlet">
        <input type="hidden" name="param"    id ="param" value="<%=box.getString("param")%>">
        <input type="hidden" name="param1" 	id ="param1" value="<%=box.getString("param1")%>">
        <input type="hidden" name="param2" 	id ="param2" value="<%=box.getString("param2")%>">
        <input type="hidden" name="ordering"  value="">
        
      </form>
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>No</b></td>
          <td class="table_title"><b>분야</b></td>
          <td class="table_title"><a href="javascript:ordering('subjnm')"><b>과정명</b></a></td>
          <td class="table_title"><a href="javascript:ordering('user_cnt')"><b>교육인원</b></a></td>
          <td class="table_title"><a href="javascript:ordering('sul_cnt')"><b>참여인원</b></a></td>
          <td class="table_title"><a href="javascript:ordering('sul_rate')"><b>참여율</b></a></td>
          <td class="table_title"><a href="javascript:ordering('sul_rate')"><b>만족도</b></a></td>
         </tr>
         
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");
 
 			DecimalFormat df = new DecimalFormat("00.0");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);
				v_areaname		= dbox.getString("d_area");
				v_subjnm		= dbox.getString("d_subjnm");
		        v_user_cnt		= dbox.getInt("d_user_cnt");
		        v_sul_cnt		= dbox.getInt("d_sul_cnt");
		        v_sul_rate		= dbox.getDouble("d_sul_rate"); 
		        v_totalsul_rate		= dbox.getDouble("d_totalsul_rate"); 
		        
		        v_sul_rate2 = (double)v_sul_cnt / (double)v_user_cnt * 100;
		        //v_sul_rate =  ((double)v_sul_rate/(double)100.00 * 100);
		        
		        v_total_user_cnt	+= v_user_cnt;
			    v_total_sul_cnt		+= v_sul_cnt;
			    //v_total_sul_rate	+= v_sul_rate;
			    
			    /* if (v_sul_rate > 0) {
			    	v_total_sul_rate    += v_sul_rate;
			    	v_sul1_cnt++;
			    } */
			    
		%>
		 <tr class="table_02_1">
		 
		<td class="table_02_1"><%= i+1 %></td>
          <td class="table_02_1"><%= v_areaname %></td>
          <td class="table_02_1"><%= v_subjnm %></td>
          <td class="table_02_1"><%= v_user_cnt %></td>
          <td class="table_02_1"><%= v_sul_cnt %></td>
          <td class="table_02_1"><%= df.format(v_sul_rate2) %>%</td>
          <td class="table_02_1"><%= df.format(v_sul_rate) %>점</td>
        </tr>
		<%
        	 }
        	if (i == 0) { 
		%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="15">등록된 내용이 없습니다.</td>
        </tr>
        <% } else {
        	
        	if (v_sul1_cnt  == 0) {
        		v_total_sul_rate = 0;
        	} else {
        		v_total_sul_rate = v_total_sul_rate/v_sul1_cnt;
        	}
        %>
        <tr class="table_02_3">
          <td class="table_02_3" colspan="2">합계</td>
          <td class="table_02_3">-</td>
          <td class="table_02_3"><%= v_total_user_cnt %></td>
          <td class="table_02_3"><%= v_total_sul_cnt %></td>
          <td class="table_02_3"><%= df.format(v_total_sul_cnt*100/v_total_user_cnt) %>%</td>
          <td class="table_02_3"><%= df.format(v_totalsul_rate)%>점</td>
        </tr>
        <% } %>
     
      </table>
      <br>
    <!----------------- 과정기준조회 끝 ----------------->
    
    

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>