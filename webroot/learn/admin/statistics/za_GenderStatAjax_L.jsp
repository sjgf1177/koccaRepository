<%
//**********************************************************
//  1. 제      목: 성별 통계 - 전체 현황
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
    String v_grseqnm = "";
    
    int v_rank = 0;
    int v_rowspan = 0;
    int v_tot = 0;
    
    double v_grad_rate = 0;
    double v_sul_rate = 0;
    
    int v_rowspantmp = 0;
    int v_man_cnt	= 0;
    int	v_woman_cnt		= 0;
    int v_johndoe_cnt	= 0;
    
    int v_total_man_cnt = 0, v_sum_man_cnt	= 0;
    int v_total_woman_cnt = 0, v_sum_woman_cnt	= 0;
    int v_total_johndoe_cnt = 0, v_sum_johndoe_cnt	= 0;
    int v_total_tot = 0, v_sum_tot	= 0;
    
%>
<script language="JavaScript">
<!--
function whenExcel() {
	document.form1.target = 'mainFrame';
    $("#isExcel").val("true");
    document.form1.submit();
    $("#isExcel").val("false");
}
//-->
</script>
     
    	
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>성별 검색</b></td>
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
      <form name = "form1" method = "post">
        <input type="hidden" name="p_process" value="selectGenderStat">
        <input type="hidden" name="p_action" value ="/servlet/controller.course.CourseStateAdminServlet">
        <input type="hidden" name="param"    id ="param" value="<%=box.getString("param")%>">
        <input type="hidden" name="param1" 	id ="param1" value="<%=box.getString("param1")%>">
        <input type="hidden" name="param2" 	id ="param2" value="<%=box.getString("param2")%>">
        <input type="hidden" name="isExcel" id="isExcel" value="false">
      </form>
      
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>차수명</b></td>
          <td class="table_title"><b>분류</b></td>
          <td class="table_title"><b>분야</b></td>
          <td class="table_title"><b>전체</b></td>
          <td class="table_title"><b>남자</b></td>
          <td class="table_title"><b>여자</b></td>
          <td class="table_title"><b>미확인</b></td>
         </tr>
         
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");
 			DecimalFormat df = new DecimalFormat("###,##0.00");

        	for (i=0; i<list.size(); i++) {
        		
				DataBox dbox = (DataBox)list.get(i);

				v_grseqnm		= dbox.getString("d_grseqnm");
				v_areaname		= dbox.getString("d_areaname");
				v_cate			= dbox.getString("d_cate");
				
				v_tot			= dbox.getInt("d_tot");
		        v_man_cnt		= dbox.getInt("d_man");
		        v_woman_cnt		= dbox.getInt("d_woman");
		        v_johndoe_cnt	= dbox.getInt("d_johndoe");
		       
		        v_rank 			= dbox.getInt("d_rank"); 
		        v_rowspan		= dbox.getInt("d_rowspan"); 	
		        
		        v_total_man_cnt		+= v_man_cnt;
			    v_total_woman_cnt	+= v_woman_cnt;
			    v_total_johndoe_cnt	+= v_johndoe_cnt;
			    v_total_tot 		+= v_tot;
		        
			    if (v_rowspan != 0) {
			    	v_rowspantmp = v_rowspan;   // 소계 합 tr
			    }
		%>
		 <tr class="table_02_1">
		 
		  <% if (v_rank == 1) { %> 
		  <td class="table_02_1" rowspan="<%=v_rowspan+1%>"><%= v_grseqnm %></td> 
		  <td class="table_02_1" rowspan="<%=v_rowspan+1%>"><%= v_cate %></td> 
		  <% } %> 
		  
          <td class="table_02_1"><%= v_areaname %></td>
          <td class="table_02_1"><%= v_tot %></td>
          <td class="table_02_1"><%= v_man_cnt %></td>
          <td class="table_02_1"><%= v_woman_cnt %></td>
          <td class="table_02_1"><%= v_johndoe_cnt %></td>
        </tr>
        
      <% if (v_rank == v_rowspantmp) { 
          %>
          	<tr class="table_02_3">
	          <td class="table_02_3">소계</td>
	          <td class="table_02_3"><%= v_total_tot %></td>
	          <td class="table_02_3"><%= v_total_man_cnt %></td>
	          <td class="table_02_3"><%= v_total_woman_cnt %></td>
	          <td class="table_02_3"><%= v_total_johndoe_cnt %></td>
	        </tr>
          <% 
           
            v_sum_man_cnt		+= v_total_man_cnt;
          	v_sum_woman_cnt		+= v_total_woman_cnt;
          	v_sum_johndoe_cnt	+= v_total_johndoe_cnt;
          	v_sum_tot			+= v_total_tot;
		    
          	v_total_man_cnt	= 0;
		    v_total_woman_cnt	= 0;
		    v_total_johndoe_cnt	= 0;
		    v_tot = 0;
          } // 소계 %>
		<%
        	 }
        	if (i == 0) { 
		%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="15">등록된 내용이 없습니다.</td>
        </tr>
       
       <% } else { %>
       		<tr class="table_02_3">
       		<td class="table_02_3" colspan="2">합계</td>
	          <td class="table_02_3">-</td>
	          <td class="table_02_3"><%= v_sum_tot %></td>
	          <td class="table_02_3"><%= v_sum_man_cnt %></td>
	          <td class="table_02_3"><%= v_sum_woman_cnt %></td>
	          <td class="table_02_3"><%= v_sum_johndoe_cnt %></td>
	        </tr>
        <% } %>
     
      </table>
      
    <!----------------- 과정기준조회 끝 ----------------->
  
<!----------------- 챠트 ----------------->
<script type="text/javascript">
		//차트1
    var params = { wmode:"transparent" };
    var flashVars1 = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "남자;<%=v_sum_man_cnt%>\n여자;<%=v_sum_woman_cnt%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>1</enabled></legend><pie><brightness_step>-15</brightness_step><base_color>#ffffff</base_color><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv", "333", "222", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars1, params); });


  	//차트2
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",       
        chart_data: "남자;<%=v_sum_man_cnt%>\n여자;<%=v_sum_woman_cnt%>",
        chart_settings: "<settings><text_size>13</text_size><data_type>csv</data_type><plot_area><margins><left>50</left><right>40</right><top>50</top><bottom>40</bottom></margins></plot_area><grid><category><dashed>1</dashed><dash_length>4</dash_length></category><value><dashed>1</dashed><dash_length>4</dash_length></value></grid><axes><category><width>1</width><color>888888</color></category><value><width>1</width><color>888888</color></value></axes><values><value><min>0</min></value></values><legend><enabled>0</enabled></legend><angle>0</angle><column><width>40</width><balloon_text>{title}: {value}</balloon_text><grow_time>3</grow_time><sequenced_grow>1</sequenced_grow></column><graphs><graph gid='0'><title>만족도</title><color>#df3d1d,#dfaf1d</color></graph></graphs><labels><label lid='0'><text><![CDATA[]]></text><y>18</y><text_color>000000</text_color><align>center</align></label></labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/amcolumn.swf", "chartdiv1", "333", "222", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });
</script>
<style>
div.chartbox { width:968px; height:300px; position:relative; border:1px solid #77a3eb; }
div.chartbox table.chartset { width:1000px; padding:0; position:absolute; top:0px; left:-16px; }
div.chartbox table.chartset td { padding:0; margin:0; text-align:center; }
div.chartbox table.chartset td p { width:100px; height:29px; margin:0 auto; font-family:"malgun gothic","gulim","sans-serif"; font-size:14px; font-weight:bold; background-image:url("/js/assets/amcharts/images/bg_chart_title.png"); background-repeat:no-repeat; background-color:#ffffff; text-align:center; line-height:26px; }
</style>
<div class="chartbox">
	<table class="chartset" cellpadding="0" cellspacing="0">
		<tr>
			<td><div id="chartdiv"></div><p>남여 백분율</p></td>
			<td><div id="chartdiv1"></div><p>성별</p></td>
		</tr>
	</table>
</div>   
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>