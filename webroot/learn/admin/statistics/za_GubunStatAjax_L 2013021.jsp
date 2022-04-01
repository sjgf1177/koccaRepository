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
<script type="text/javascript" src="/js/assets/jquery.multiselect.filter.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/flash/swfobject.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/amcharts.js"></script>
<script type="text/javascript" src="/js/assets/amcharts/amfallback.js"></script>
        
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    int i = 0;
    
	
    String v_areaname = "";
    String v_cate = "";
    String v_area = "";
    int v_subj_cnt = 0;
    int v_user_cnt = 0;
    int v_grad_cnt = 0;
    int v_rowspan = 0;
    int v_rowspantmp = 0;
    int v_rank = 0;
    int v_man_cnt = 0;
    int v_woman_cnt = 0;
    int v_man_rate = 0;
    
    int v_broad_stu = 0;
    int v_game_stu = 0;
    int v_culture_stu = 0;
    int v_broad_stold = 0;
    int v_game_stold = 0;
    int v_culture_stold = 0;
    
    int		v_total_subj_cnt	= 0;
    int		v_total_user_cnt	= 0;
    int		v_total_grad_cnt	= 0;
    int		v_total_man_cnt		= 0;
    int		v_total_woman_cnt	= 0;
    int		v_total_man_rate	= 0;
    
    int		v_sum_subj_cnt	= 0;
    int		v_sum_user_cnt	= 0;
    int		v_sum_grad_cnt	= 0;
    int		v_sum_man_cnt = 0;
    int 	v_sum_woman_cnt = 0;
    
    double 	v_total_grad_rate = 0;
    double  v_total_sul_rate = 0;
    double  v_sum_sul_rate = 0;
    double  v_grad_rate = 0;
    double  v_sul_rate = 0;
    int  v_sum_man_rate = 0;
    
    
%>
   
      <div id="s_list">
      <table width="1000" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    	<td align="center" valign="top">  
    
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" height="20">&nbsp;<b>분야별 검색</b></td>
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
          
          <td class="table_title"><b>구분</b></td>
          <td class="table_title"><b>분야</b></td>
          <td class="table_title"><b>과목수</b></td>
          <td class="table_title"><b>교육인원</b></td>
          <td class="table_title"><b>수료인원</b></td>
          <td class="table_title"><b>남/여비율%</b></td>
          <td class="table_title"><b>수료율</b></td>
          <td class="table_title"><b>만족도</b></td>
         </tr>
         
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");
		 	//String format ="#.##";
			DecimalFormat df = new DecimalFormat("###,##0.00");

        	for (i=0; i<list.size(); i++) {
        		
				DataBox dbox = (DataBox)list.get(i);
				v_area			= dbox.getString("d_area");
				v_areaname		= dbox.getString("d_areaname");
				v_cate			= dbox.getString("d_catename");
		        v_subj_cnt		= dbox.getInt("d_subj_cnt");
		        v_user_cnt		= dbox.getInt("d_user_cnt");
		        v_grad_cnt		= dbox.getInt("d_grad_cnt");
		        v_grad_rate		= dbox.getDouble("d_grad_rate");
		        v_sul_rate		= dbox.getDouble("d_sul_rate"); 
		        v_rank			= dbox.getInt("d_rank");
		        v_rowspan		= dbox.getInt("d_rowspan");
		        
		        v_man_cnt		= dbox.getInt("d_man_cnt");
		        v_woman_cnt		= dbox.getInt("d_woman_cnt");
		        
		        if ((v_man_cnt+v_woman_cnt) == 0) {
		        	v_man_rate = 0;
		        } else {
		        	v_man_rate =    v_man_cnt*100 /(v_man_cnt+v_woman_cnt); 
		        }
		        v_total_subj_cnt	+= v_subj_cnt;
			    v_total_user_cnt	+= v_user_cnt;
			    v_total_grad_cnt	+= v_grad_cnt;
			    v_total_grad_rate	= (v_total_grad_cnt)*100.0 / v_total_user_cnt ;
			    v_total_sul_rate    += v_sul_rate;
			    v_total_man_cnt     += v_man_cnt;
			    v_total_woman_cnt   += v_woman_cnt;
			    //v_total_sul_rate    += v_sul_rate;
			    
			    if (v_rowspan != 0) {
			    	v_rowspantmp = v_rowspan;   // 소계 합 tr
			    }
			    
			    // 챠트용 데이타 출력
			    if (v_area.equals("G0")) { // 게임
			    	v_game_stu += v_user_cnt;
			    	v_game_stold += v_grad_cnt;
			    	
			    } else if (v_area.equals("K0")) { // 문화
			    	v_culture_stu += v_user_cnt;
			    	v_culture_stold += v_grad_cnt;
			    	
			    } else if (v_area.equals("B0")) { // 방송
			    	v_broad_stu += v_user_cnt;
			    	v_broad_stold += v_grad_cnt;
			    }
		        
		%>
		  <tr class="table_02_1">
		  <% if (v_rank == 1) { %> 
		  <td class="table_02_1" rowspan="<%=v_rowspan+1%>"><%= v_cate %></td> 
		  <% } %> 
		  
          <td class="table_02_1"><%= v_areaname %></td>
          <td class="table_02_1"><%= v_subj_cnt %></td>
          <td class="table_02_1"><%= v_user_cnt %></td>
          <td class="table_02_1"><%= v_grad_cnt %></td>
          
          <td class="table_02_1"><%= v_man_rate %> / <%= 100-v_man_rate %></td>
          
          <td class="table_02_1"><%= df.format(v_grad_rate) %></td>
          <td class="table_02_1"><%= df.format(v_sul_rate) %></td>
          </tr>
          <% if (v_rank == v_rowspantmp) { 
        	  v_total_sul_rate = (v_total_sul_rate / v_rowspantmp);
        	  if ((v_total_man_cnt + v_total_woman_cnt) == 0) {
        		  v_total_man_rate = 0;
        	  } else {
        	  		v_total_man_rate = v_total_man_cnt *100 / (v_total_man_cnt + v_total_woman_cnt);
        	  }
          %>
          	<tr class="table_02_3">
	          <td class="table_02_3">소계</td>
	          <td class="table_02_3"><%= v_total_subj_cnt %></td>
	          <td class="table_02_3"><%= v_total_user_cnt %></td>
	          <td class="table_02_3"><%= v_total_grad_cnt %></td>
	          <td class="table_02_3"><%= v_total_man_rate %>/<%= 100-v_total_man_rate %></td>
	          <td class="table_02_3"><%= df.format(v_total_grad_rate) %></td>
	          <td class="table_02_3"><%= df.format(v_total_sul_rate)%></td>
	        </tr>
          <% 
            v_sum_subj_cnt += v_total_subj_cnt;
            v_sum_user_cnt += v_total_user_cnt;
            v_sum_grad_cnt += v_total_grad_cnt;
            v_sum_man_cnt += v_total_man_cnt;
            v_sum_woman_cnt += v_total_woman_cnt;
            if ((v_sum_man_cnt + v_sum_woman_cnt) == 0) {
            	v_sum_man_rate = 0;
            } else {
            	v_sum_man_rate = v_sum_man_cnt*100 /  (v_sum_man_cnt + v_sum_woman_cnt);
            }
            v_total_sul_rate = (v_total_sul_rate / v_rowspantmp);
            //v_sum_sul_rate += v_total_sul_rate 
          	v_total_subj_cnt = 0;
          	v_total_user_cnt = 0;
          	v_total_grad_cnt = 0;
          	v_total_grad_rate = 0;
          	v_total_man_cnt = 0;
          	v_total_woman_cnt = 0;
          	v_total_man_rate = 0;
          	v_total_sul_rate = 0;
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
          <td class="table_02_3"><%= v_sum_subj_cnt %></td>
          <td class="table_02_3"><%= v_sum_user_cnt %></td>
          <td class="table_02_3"><%= v_sum_grad_cnt %></td>
          <td class="table_02_3"><%=v_sum_man_rate %>/<%=100-v_sum_man_rate %></td>
          <td class="table_02_3"><%= df.format(v_sum_grad_cnt*100/v_sum_user_cnt) %></td>
          <td class="table_02_3">-</td>
        </tr>
        <% } %>
     
      </table>
     
    <!----------------- 과정기준조회 끝 ----------------->
    <br>
    </td>
  </tr>
</table>
<!----------------- 챠트 ----------------->
<script type="text/javascript">
		//차트1
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "방송;<%=v_broad_stu%>\n문화;<%=v_culture_stu%>\n게임;<%=v_game_stu%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>1</enabled></legend><pie><brightness_step>-15</brightness_step><base_color>#ffffff</base_color><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv", "333", "222", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });

/*
// t - type of property: n - number; b - boolean; c - color; a - alpha; s - string; 
// arr = array; {s} - balloon strings, where tags are used; e - effect
pie(legend, lsettings, this.legendMap);
<settings>
	<data_type>csv</data_type>
	<legend>
		<position>1</position>
		<enabled>1</enabled>
		<align>left</align>
		<text_size>15</text_size>
	</legend>
	<bullet_size>50</bullet_size>
	<pie>
		<margin_left>50</margin_left>
		<brightness_step>50</brightness_step>
		<base_color>#555555</base_color>
		<inner_radius>30</inner_radius>
		<height>7</height>
		<angle>10</angle>
		<gradient></gradient>
	</pie>
	<animation>
		<start_time>1</start_time>
		<pull_out_time>1</pull_out_time>
	</animation>
	<data_labels>
		<show>{title}</show>
		<max_width>100</max_width>
	</data_labels>
</settings>	
*/

		//차트2
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "방송;<%=v_broad_stold%>\n문화;<%=v_culture_stold%>\n게임;<%=v_game_stold%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>1</enabled></legend><pie><brightness_step>-15</brightness_step><base_color>#ffffff</base_color><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv1", "333", "222", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });
    //차트3
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "남자;<%=v_sum_man_cnt%>\n여자;<%=v_sum_woman_cnt%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>1</enabled></legend><pie><brightness_step>-15</brightness_step><base_color>#ffffff</base_color><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv2", "333", "222", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });
</script>
<style>
div.chartbox { width:968px; height:300px; position:relative; border:1px solid #77a3eb; }
div.chartbox table.chartset { width:1000px; padding:0; position:absolute; top:0px; left:-16px; }
div.chartbox table.chartset td { padding:0; margin:0; text-align:center; }
div.chartbox table.chartset td p { width:100px; height:29px; margin:0 auto; font-family:"malgun gothic","gulim","sans-serif"; font-size:14px; font-weight:bold; background-image:url("/js/assets/amcharts/images/bg_chart_title.png"); background-repeat:no-repeat; text-align:center; line-height:26px; }
</style>
<div class="chartbox">
	<table class="chartset" cellpadding="0" cellspacing="0">
		<tr>
			<td><div id="chartdiv"></div><p>교육인원</p></td>
			<td><div id="chartdiv1"></div><p>수료인원</p></td>
			<td><div id="chartdiv2"></div><p>남녀비율</p></td>
		</tr>
	</table>
</div>
</div>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>