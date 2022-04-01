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
    
    String v_year = "";
    String v_month = "";
    
    int v_toddler_cnt   =0;	
    int v_teens_cnt		=0;
    int v_twenty_cnt	=0;
    int v_thirty_cnt	=0;
    int v_fourty_cnt	=0;
    int v_fifty_cnt		=0;
    int v_sixty_cnt		=0;
    int v_seventy_cnt	=0;
    int v_johndoe_cnt	=0;
    
    int v_tot = 0;
    int v_man_cnt	= 0;
    int	v_woman_cnt		= 0;
    
    int v_total_teens_cnt = 0, v_sum_teens_cnt	= 0;
    int v_total_twenty_cnt = 0, v_sum_twenty_cnt	= 0;
    int v_total_thirty_cnt = 0, v_sum_thirty_cnt	= 0;
    int v_total_fourty_cnt = 0, v_sum_fourty_cnt	= 0;
    int v_total_fifty_cnt = 0, v_sum_fifty_cnt	= 0;
    int v_total_sixty_cnt = 0, v_sum_sixty_cnt	= 0;
    int v_total_seventy_cnt = 0, v_sum_seventy_cnt	= 0;
    int v_total_johndoe_cnt = 0, v_sum_johndoe_cnt	= 0;
    int v_total_tot = 0, v_sum_tot	= 0, v_total_toddler_cnt = 0;
    
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
          <td align="left" height="20">&nbsp;<b>연령별 통계</b></td>
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
      <form name = "form1" method = "post" action="/servlet/controller.statistics.UserCountServlet">
        <input type="hidden" name="p_process" id="p_process" value="selectAgeStat">
        <input type="hidden" name="param"    id ="param" value="<%=box.getString("param")%>">
        <input type="hidden" name="param1" 	id ="param1" value="<%=box.getString("param1")%>">
        <input type="hidden" name="param2" 	id ="param2" value="<%=box.getString("param2")%>">
        <input type="hidden" name="isExcel" id="isExcel" value="true">
      </form>
      
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>연도</b></td>
          <td class="table_title"><b>월</b></td>
          <td class="table_title"><b>전체</b></td>
          <td class="table_title"><b>10대</b></td>
          <td class="table_title"><b>20대</b></td>
          <td class="table_title"><b>30대</b></td>
          <td class="table_title"><b>40대</b></td>
          <td class="table_title"><b>50대</b></td>
          <td class="table_title"><b>60대</b></td>
          <td class="table_title"><b>70대</b></td>
          <td class="table_title"><b>미확인</b></td>
         </tr>
         
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
        		
				DataBox dbox = (DataBox)list.get(i);

				v_year			= dbox.getString("d_inyear");
				v_month			= dbox.getString("d_indate").substring(4, 6);
				v_tot			= dbox.getInt("d_usercnt");
				v_toddler_cnt	= dbox.getInt("d_toddler");
		        v_teens_cnt		= dbox.getInt("d_teens");
		        v_twenty_cnt	= dbox.getInt("d_twenty");
		        v_thirty_cnt	= dbox.getInt("d_thirty");
		        v_fourty_cnt	= dbox.getInt("d_fourty");
		        v_fifty_cnt		= dbox.getInt("d_fifty");
		        v_sixty_cnt		= dbox.getInt("d_sixty");
		        v_seventy_cnt	= dbox.getInt("d_seventy");
		        v_johndoe_cnt	= dbox.getInt("d_johndoe");
		       
		        v_total_toddler_cnt	+= v_toddler_cnt;
		        v_total_teens_cnt	+= v_teens_cnt;
			    v_total_twenty_cnt	+= v_twenty_cnt;
			    v_total_thirty_cnt	+= v_thirty_cnt;
			    v_total_fourty_cnt	+= v_fourty_cnt;
			    v_total_fifty_cnt	+= v_fifty_cnt;
			    v_total_sixty_cnt	+= v_sixty_cnt;
			    v_total_seventy_cnt	+= v_seventy_cnt;
			    v_total_johndoe_cnt	+= v_johndoe_cnt;
			    v_total_tot 		+= v_tot;
		        
		%>
		 <tr class="table_02_1">
		 
          <td class="table_02_1"><%= v_year %></td>
          <td class="table_02_1"><%= v_month %></td>
          <td class="table_02_1"><%= v_tot %></td>
          <td class="table_02_1"><%= v_teens_cnt %></td>
          <td class="table_02_1"><%= v_twenty_cnt %></td>
          <td class="table_02_1"><%= v_thirty_cnt %></td>
          <td class="table_02_1"><%= v_fourty_cnt %></td>
          <td class="table_02_1"><%= v_fifty_cnt %></td>
          <td class="table_02_1"><%= v_sixty_cnt %></td>
          <td class="table_02_1"><%= v_seventy_cnt %></td>
          <td class="table_02_1"><%= v_johndoe_cnt %></td>
        </tr>
        
     	<%}
        if (i == 0) { %>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="15">등록된 내용이 없습니다.</td>
        </tr>
       
       <% } else { %>
       		<tr class="table_02_3">
       		<td class="table_02_3" colspan="2">합계</td>
	          <td class="table_02_3"><%= v_total_tot %></td>
	          <td class="table_02_3"><%= v_total_teens_cnt %></td>
	          <td class="table_02_3"><%= v_total_twenty_cnt %></td>
	          <td class="table_02_3"><%= v_total_thirty_cnt %></td>
	          <td class="table_02_3"><%= v_total_fourty_cnt %></td>
	          <td class="table_02_3"><%= v_total_fifty_cnt %></td>
	          <td class="table_02_3"><%= v_total_sixty_cnt %></td>
	          <td class="table_02_3"><%= v_total_seventy_cnt %></td>
	          <td class="table_02_3"><%= v_total_johndoe_cnt %></td>
	        </tr>
        <% } %>
     
      </table>
      <br/>
    <!----------------- 과정기준조회 끝 ----------------->
<!----------------- 챠트 ----------------->
<script type="text/javascript">
		//차트1         chart_data: "10대;<%=v_total_teens_cnt%>\n20대;<%=v_total_twenty_cnt%>\n30대;<%=v_total_thirty_cnt%>\n40대;<%=v_total_fourty_cnt%>\n50대;<%=v_total_fifty_cnt%>\n60대;<%=v_total_sixty_cnt%>\n70대;<%=v_total_seventy_cnt%>",

    var params = { wmode:"transparent" };
    var flashVars1 = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "10대;<%=v_total_teens_cnt%>\n20대;<%=v_total_twenty_cnt%>\n30대;<%=v_total_thirty_cnt%>\n40대;<%=v_total_fourty_cnt%>\n50대;<%=v_total_fifty_cnt%>\n60대;<%=v_total_sixty_cnt%>\n70대;<%=v_total_seventy_cnt%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>0</enabled></legend><pie><brightness_step>-15</brightness_step><base_color>#ffffff</base_color><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"

    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv", "400", "400", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars1, params); });
    

  	//차트2
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",       
        chart_data: "10대;<%=v_total_teens_cnt%>\n20대;<%=v_total_twenty_cnt%>\n30대;<%=v_total_thirty_cnt%>\n40대;<%=v_total_fourty_cnt%>\n50대;<%=v_total_fifty_cnt%>\n60대;<%=v_total_sixty_cnt%>\n70대;<%=v_total_seventy_cnt%>",
        chart_settings: "<settings><text_size>13</text_size><data_type>csv</data_type><plot_area><margins><left>70</left><right>40</right><top>50</top><bottom>40</bottom></margins></plot_area><grid><category><dashed>1</dashed><dash_length>4</dash_length></category><value><dashed>1</dashed><dash_length>4</dash_length></value></grid><axes><category><width>1</width><color>888888</color></category><value><width>1</width><color>888888</color></value></axes><values><value><min>0</min></value></values><legend><enabled>0</enabled></legend><angle>0</angle><column><width>40</width><balloon_text>{title}: {value}</balloon_text><grow_time>3</grow_time><sequenced_grow>1</sequenced_grow></column><graphs><graph gid='0'><title>회원</title><color>#df3d1d,#dfaf1d</color></graph></graphs><labels><label lid='0'><text><![CDATA[]]></text><y>18</y><text_color>000000</text_color><align>center</align></label></labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/amcolumn.swf", "chartdiv1", "400", "400", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });
</script>
<style>
div.chartbox { width:968px; height:450px; position:relative; border:1px solid #77a3eb; }
div.chartbox table.chartset { width:1000px; padding:0; position:absolute; top:0px; left:-16px; }
div.chartbox table.chartset td { padding:0; margin:0; text-align:center; }
div.chartbox table.chartset td p { width:100px; height:29px; margin:0 auto; font-family:"malgun gothic","gulim","sans-serif"; font-size:14px; font-weight:bold; background-image:url("/js/assets/amcharts/images/bg_chart_title.png"); background-repeat:no-repeat; background-color:#ffffff; text-align:center; line-height:26px; }
</style>
<div class="chartbox">
	<table class="chartset" cellpadding="0" cellspacing="0">
		<tr>
			<td><div id="chartdiv"></div><p>연령별 백분율</p></td>
			<td><div id="chartdiv1"></div><p>연령별</p></td>
		</tr>
	</table>
</div>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>