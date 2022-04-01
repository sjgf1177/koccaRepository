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
    String v_edustart = "";
    String v_eduend = "";
    
    int v_job_cnt = 0;
    
    int v_user_cnt = 0;
    int v_grad_cnt = 0;
    int v_ngrad_cnt = 0;
    int v_man_cnt = 0;
    int v_woman_cnt = 0;
    
    int v_total_user_cnt	= 0;
    int v_total_grad_cnt	= 0;
    int v_total_ngrad_cnt   = 0;
    double v_total_grad_rate	= 0;
    int v_total_man_cnt		= 0;
    int v_total_woman_cnt	= 0;
    int v_total_job_cnt		= 0;
    

    
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function whenExcel() {   
	 document.form1.target = 'mainFrame';
     $("#isExcel").val("true");
     document.form1.submit();
     $("#isExcel").val("false");

 }
 
//-->
</SCRIPT>  
       <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>수강생 현황</b></td>
          <td align="right">
         
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>           
      <!----------------- 조회 시작 ----------------->
    
      
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="9" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>No</b></td>
          <td class="table_title"><b>과정명</b></td>  
          <td class="table_title"><b>교육기간</b></td>       
          <td class="table_title"><b>교육인원</b></td>
          <td class="table_title"><b>수료</b></td>
          <td class="table_title"><b>미수료</b></td>
          <td class="table_title"><b>남자</b></td>
          <td class="table_title"><b>여자</b></td>
          <td class="table_title"><b>취업</b></td>
         </tr>
         
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");
 			DecimalFormat df = new DecimalFormat("###,##0.00");
        	for (i=0; i<list.size(); i++) {
        		
				DataBox dbox = (DataBox)list.get(i);
				v_subjnm		= dbox.getString("d_subjnm");
				v_edustart		= dbox.getString("d_edustart");
				v_eduend		= dbox.getString("d_eduend");
		        v_user_cnt		= dbox.getInt("d_user_cnt");
		        v_grad_cnt		= dbox.getInt("d_grad_cnt");
		        v_ngrad_cnt		= dbox.getInt("d_ngrad_cnt");
		        v_man_cnt		= dbox.getInt("d_man_cnt");
		        v_woman_cnt		= dbox.getInt("d_woman_cnt"); 
		        v_job_cnt		= dbox.getInt("d_job_cnt");
		        
		        v_total_user_cnt	+= v_user_cnt;
			    v_total_grad_cnt	+= v_grad_cnt;
			    v_total_ngrad_cnt   += v_ngrad_cnt;
			    v_total_man_cnt   	+= v_man_cnt;
			    v_total_woman_cnt   += v_woman_cnt;
			    v_total_job_cnt		+= v_job_cnt;
			    v_total_grad_rate	= (v_total_grad_cnt)*100.0 / v_total_user_cnt ;
		%>
		 <tr class="table_02_1">
		  <td class="table_02_1"><%= i+1 %></td>
          <td class="table_02_1"><%= v_subjnm %></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%> </td>
          <td class="table_02_1"><%= v_user_cnt %></td>
          <td class="table_02_1"><%= v_grad_cnt %></td>
          <td class="table_02_1"><%= v_ngrad_cnt %></td>
          <td class="table_02_1"><%= v_man_cnt %></td>
          <td class="table_02_1"><%= v_woman_cnt %></td>
          <td class="table_02_1"><%= v_job_cnt %></td>
        </tr>
		<% } %>
		<tr class="table_02_3">
		  <td class="table_02_3" colspan="3">전체 </td>
          <td class="table_02_3"><%= v_total_user_cnt %></td>
          <td class="table_02_3"><%= v_total_grad_cnt %></td>
          <td class="table_02_3"><%= v_total_ngrad_cnt %></td>
          <td class="table_02_3"><%= v_total_man_cnt %></td>
          <td class="table_02_3"><%= v_total_woman_cnt %></td>
          <td class="table_02_3"><%= v_total_job_cnt %></td>
        </tr>
     
      </table>
      <br/> 
   
<!----------------- 챠트 ----------------->
<script type="text/javascript">
		//차트1
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "수료;<%=v_total_grad_cnt%>\n미수료;<%=v_total_ngrad_cnt%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>1</enabled></legend><pie><brightness_step>-15</brightness_step><base_color>#ffffff</base_color><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv", "333", "222", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });


  	//차트2
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",       
        chart_data: "남자;<%=v_total_man_cnt%>\n여자;<%=v_total_woman_cnt%>",
        chart_settings: "<settings><text_size>13</text_size><data_type>csv</data_type><plot_area><margins><left>50</left><right>40</right><top>50</top><bottom>40</bottom></margins></plot_area><grid><category><dashed>1</dashed><dash_length>4</dash_length></category><value><dashed>1</dashed><dash_length>4</dash_length></value></grid><axes><category><width>1</width><color>888888</color></category><value><width>1</width><color>888888</color></value></axes><values><value><min>0</min></value></values><legend><enabled>0</enabled></legend><angle>0</angle><column><width>40</width><balloon_text>{title}: {value}</balloon_text><grow_time>3</grow_time><sequenced_grow>1</sequenced_grow></column><graphs><graph gid='0'><title>인원</title><color>#df3d1d,#dfaf1d</color></graph></graphs><labels><label lid='0'><text><![CDATA[]]></text><y>18</y><text_color>000000</text_color><align>center</align></label></labels></settings>"
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
			<td><div id="chartdiv"></div><p>수료율</p></td>
			<td><div id="chartdiv1"></div><p>남여현황</p></td>
		</tr>
	</table>
</div>
    
<%@ include file = "/learn/library/getJspName.jsp" %>   
   