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
    
    double v_grad_rate = 0;
    double v_sul_rate = 0;
    
    int v_rowspantmp = 0;
    
    int v_high_cnt		= 0;
    int v_univ_cnt	= 0;
    int v_country_cnt		= 0;
    int v_gonggong_cnt	= 0;
    int v_freelancer_cnt		= 0;
    int v_preoffice_cnt	= 0;
    int v_office_cnt	= 0;
    int v_jubu_cnt	= 0;
    int v_miltiary_cnt	= 0;
    int v_univoffice_cnt	= 0;
    int v_hospital_cnt= 0;
    int v_lawyer_cnt	= 0;
    int v_broad_cnt	= 0;
    int v_jongkyo_cnt	= 0;
    int v_entertain_cnt	= 0;
    int v_sports_cnt	= 0;
    int v_proffesor_cnt		= 0;    
    int v_lecter_cnt	= 0;
    int v_owner_cnt	= 0;
    int v_etc_cnt		= 0;
    int v_total_cnt	= 0;
    int v_johndoe = 0;
    int v_tot = 0;
    
    int v_total_high_cnt = 0, v_sum_high_cnt	= 0;
    int v_total_univ_cnt = 0, v_sum_univ_cnt	= 0;
    int v_total_country_cnt = 0, v_sum_country_cnt	= 0;
    int v_total_gonggong_cnt = 0, v_sum_gonggong_cnt	= 0;
    int v_total_freelancer_cnt = 0, v_sum_freelancer_cnt	= 0;
    int v_total_preoffice_cnt = 0, v_sum_preoffice_cnt	= 0;
    int v_total_office_cnt = 0, v_sum_office_cnt	= 0;
    int v_total_jubu_cnt = 0, v_sum_jubu_cnt	= 0;
    int v_total_tot = 0, v_sum_tot	= 0;
    int v_total_miltiary_cnt = 0, v_sum_miltiary_cnt	= 0;
    int v_total_univoffice_cnt = 0, v_sum_univoffice_cnt	= 0;
    int v_total_hospital_cnt = 0, v_sum_hospital_cnt	= 0;
    int v_total_lawyer_cnt = 0, v_sum_lawyer_cnt	= 0;
    int v_total_broad_cnt = 0, v_sum_broad_cnt	= 0;
    int v_total_jongkyo_cnt = 0, v_sum_jongkyo_cnt	= 0;
    int v_total_entertain_cnt = 0, v_sum_entertain_cnt	= 0;
    int v_total_sports_cnt = 0, v_sum_sports_cnt	= 0;
    int v_total_proffesor_cnt = 0, v_sum_proffesor_cnt	= 0;
    int v_total_lecter_cnt = 0, v_sum_lecter_cnt	= 0;
    int v_total_owner_cnt = 0, v_sum_owner_cnt	= 0;
    int v_total_etc_cnt = 0, v_sum_etc_cnt	= 0;
    int v_total_total_cnt = 0, v_sum_total_cnt	= 0;
    int v_total_johndoe_cnt = 0, v_sum_johndoe_cnt	= 0;
    
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
          <td align="left" height="20">&nbsp;<b>?????? ????</b></td>
          <td align="right">
          <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
             <!-- <button id="exCel">????????</button> -->
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>           
      <!----------------- ???? ???? ----------------->
      <form name = "form1" method = "post">
        <input type="hidden" name="p_process" value="selectVocationStat">
        <input type="hidden" name="p_action" value ="/servlet/controller.course.CourseStateAdminServlet">
        <input type="hidden" name="param"    id ="param" value="<%=box.getString("param")%>">
        <input type="hidden" name="param1" 	id ="param1" value="<%=box.getString("param1")%>">
        <input type="hidden" name="param2" 	id ="param2" value="<%=box.getString("param2")%>">
        <input type="hidden" name="isExcel" id="isExcel" value="false">
      </form>
      
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="25" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>????????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>????????</b></td>
          <td class="table_title"><b>????????</b></td>
          <td class="table_title"><b>??????????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>??????</b></td>
          <td class="table_title"><b>????</b></td>
          <td class="table_title"><b>??????</b></td>
         </tr>
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");
 			DecimalFormat df = new DecimalFormat("###,##0.00");

        	for (i=0; i<list.size(); i++) {
        		
				DataBox dbox = (DataBox)list.get(i);
				
				v_grseqnm		= dbox.getString("d_grseqnm");
				v_areaname		= dbox.getString("d_areaname");
				v_cate			= dbox.getString("d_cate");
				
			    v_high_cnt		= dbox.getInt("d_high");
			    v_univ_cnt		= dbox.getInt("d_univ");
			    v_country_cnt	= dbox.getInt("d_country");
			    v_gonggong_cnt	= dbox.getInt("d_gonggong");
			    v_freelancer_cnt= dbox.getInt("d_freelancer");
			    v_preoffice_cnt	= dbox.getInt("d_preoffice");
			    v_office_cnt	= dbox.getInt("d_office");
			    v_jubu_cnt		= dbox.getInt("d_jubu");
			    v_miltiary_cnt	= dbox.getInt("d_miltiary");
			    v_univoffice_cnt= dbox.getInt("d_univoffice");
			    v_hospital_cnt	= dbox.getInt("d_hospital");
			    v_lawyer_cnt	= dbox.getInt("d_lawyer");
			    v_broad_cnt		= dbox.getInt("d_broad");
			    v_jongkyo_cnt	= dbox.getInt("d_jongkyo");
			    v_entertain_cnt	= dbox.getInt("d_entertain");
			    v_sports_cnt		= dbox.getInt("d_sports");
			    v_proffesor_cnt	= dbox.getInt("d_proffesor");
			    v_lecter_cnt	= dbox.getInt("d_lecter");
			    v_owner_cnt		= dbox.getInt("d_owner");
		        v_etc_cnt		= dbox.getInt("d_etc");
		        v_rank 			= dbox.getInt("d_rank"); 
		        v_rowspan		= dbox.getInt("d_rowspan"); 	
		        v_tot			= dbox.getInt("d_tot"); 	
		        
		        
		        v_total_high_cnt += v_high_cnt;	
		        v_total_univ_cnt +=  v_univ_cnt;
		        v_total_country_cnt += v_country_cnt		;
		        v_total_gonggong_cnt += v_gonggong_cnt	;
		        v_total_freelancer_cnt += v_freelancer_cnt		;
		        v_total_preoffice_cnt += v_preoffice_cnt	;
		        v_total_office_cnt += v_office_cnt	;
		        v_total_jubu_cnt += v_jubu_cnt	;
		        v_total_miltiary_cnt += v_miltiary_cnt	;
		        v_total_univoffice_cnt += v_univoffice_cnt	;
		        v_total_hospital_cnt += v_hospital_cnt;
		        v_total_lawyer_cnt += v_lawyer_cnt	;
		        v_total_broad_cnt += v_broad_cnt	;
		        v_total_jongkyo_cnt += v_jongkyo_cnt	;
		        v_total_entertain_cnt += v_entertain_cnt	;
		        v_total_sports_cnt += v_sports_cnt	;
		        v_total_proffesor_cnt += v_proffesor_cnt		;    
		        v_total_lecter_cnt += v_lecter_cnt	;
		        v_total_owner_cnt += v_owner_cnt	;
		        v_total_etc_cnt += v_etc_cnt		;
		        v_total_tot += v_tot	;
		        
		       
		        
			    if (v_rowspan != 0) {
			    	v_rowspantmp = v_rowspan;   // ???? ?? tr
			    }
		%>
		 <tr class="table_02_1">
		 
		  <% if (v_rank == 1) { %> 
		  <td class="table_02_1" rowspan="<%=v_rowspan+1%>"><%= v_grseqnm %></td> 
		  <td class="table_02_1" rowspan="<%=v_rowspan+1%>"><%= v_cate %></td> 
		  <% } %> 
		  
          <td class="table_02_1"><%= v_areaname %></td>
         
          <td class="table_02_1"><strong><%= v_tot %></strong></td>
          <td class="table_02_1"><%= v_high_cnt %></td>
          <td class="table_02_1"><%= v_univ_cnt %></td>
          <td class="table_02_1"><%= v_country_cnt %></td>
          <td class="table_02_1"><%= v_gonggong_cnt %></td>
          <td class="table_02_1"><%= v_freelancer_cnt %></td>
          <td class="table_02_1"><%= v_preoffice_cnt %></td>
          <td class="table_02_1"><%= v_office_cnt %></td>
          <td class="table_02_1"><%= v_jubu_cnt %></td>
          <td class="table_02_1"><%= v_miltiary_cnt %></td>
          <td class="table_02_1"><%= v_univoffice_cnt %></td>
          <td class="table_02_1"><%= v_hospital_cnt %></td>
          <td class="table_02_1"><%= v_lawyer_cnt %></td>
          <td class="table_02_1"><%= v_broad_cnt %></td>
          <td class="table_02_1"><%= v_jongkyo_cnt %></td>
          <td class="table_02_1"><%= v_entertain_cnt %></td>
          <td class="table_02_1"><%= v_sports_cnt %></td>
          <td class="table_02_1"><%= v_proffesor_cnt %></td>
          <td class="table_02_1"><%= v_lecter_cnt %></td>
          <td class="table_02_1"><%= v_owner_cnt %></td>
          <td class="table_02_1"><%= v_etc_cnt %></td>
          <td class="table_02_1"><%= v_johndoe %></td>
        </tr>
      <% if (v_rank == v_rowspantmp) { 
          %>
          	<tr class="table_02_3">
	          <td class="table_02_3">????</td>
	          <td class="table_02_1"><strong><%= v_total_tot %></strong></td>
	          <td class="table_02_1"><%= v_total_high_cnt %></td>
	          <td class="table_02_1"><%= v_total_univ_cnt %></td>
	          <td class="table_02_1"><%= v_total_country_cnt %></td>
	          <td class="table_02_1"><%= v_total_gonggong_cnt %></td>
	          <td class="table_02_1"><%= v_total_freelancer_cnt %></td>
	          <td class="table_02_1"><%= v_total_preoffice_cnt %></td>
	          <td class="table_02_1"><%= v_total_office_cnt %></td>
	          <td class="table_02_1"><%= v_total_jubu_cnt %></td>
	          <td class="table_02_1"><%= v_total_miltiary_cnt %></td>
	          <td class="table_02_1"><%= v_total_univoffice_cnt %></td>
	          <td class="table_02_1"><%= v_total_hospital_cnt %></td>
	          <td class="table_02_1"><%= v_total_lawyer_cnt %></td>
	          <td class="table_02_1"><%= v_total_broad_cnt %></td>
	          <td class="table_02_1"><%= v_total_jongkyo_cnt %></td>
	          <td class="table_02_1"><%= v_total_entertain_cnt %></td>
	          <td class="table_02_1"><%= v_total_sports_cnt %></td>
	          <td class="table_02_1"><%= v_total_proffesor_cnt %></td>
	          <td class="table_02_1"><%= v_total_lecter_cnt %></td>
	          <td class="table_02_1"><%= v_total_owner_cnt %></td>
	          <td class="table_02_1"><%= v_total_etc_cnt %></td>
	          <td class="table_02_1"><%= v_total_johndoe_cnt %></td>
	        </tr>
          <% 
          
            v_sum_high_cnt += v_total_high_cnt;	
	        v_sum_univ_cnt +=  v_total_univ_cnt;
	        v_sum_country_cnt += v_total_country_cnt		;
	        v_sum_gonggong_cnt += v_total_gonggong_cnt	;
	        v_sum_freelancer_cnt += v_total_freelancer_cnt		;
	        v_sum_preoffice_cnt += v_total_preoffice_cnt	;
	        v_sum_office_cnt += v_total_office_cnt	;
	        v_sum_jubu_cnt += v_total_jubu_cnt	;
	        v_sum_miltiary_cnt += v_total_miltiary_cnt	;
	        v_sum_univoffice_cnt += v_total_univoffice_cnt	;
	        v_sum_hospital_cnt += v_total_hospital_cnt;
	        v_sum_lawyer_cnt += v_total_lawyer_cnt	;
	        v_sum_broad_cnt += v_total_broad_cnt	;
	        v_sum_jongkyo_cnt += v_total_jongkyo_cnt	;
	        v_sum_entertain_cnt += v_total_entertain_cnt	;
	        v_sum_sports_cnt += v_total_sports_cnt	;
	        v_sum_proffesor_cnt += v_total_proffesor_cnt		;    
	        v_sum_lecter_cnt += v_total_lecter_cnt	;
	        v_sum_owner_cnt += v_total_owner_cnt	;
	        v_sum_etc_cnt += v_total_etc_cnt		;
	        v_sum_tot += v_total_tot	;
            
          } // ???? %>
		<%
        	 }
        	if (i == 0) { 
		%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="15">?????? ?????? ????????.</td>
        </tr>
       
       <% } else { %>
       		<tr class="table_02_3">
       		<td class="table_02_3" colspan="2">????</td>
	          <td class="table_02_3">-</td>
	         <td class="table_02_1"><strong><%= v_sum_tot %></strong></td>
	          <td class="table_02_1"><%= v_sum_high_cnt %></td>
	          <td class="table_02_1"><%= v_sum_univ_cnt %></td>
	          <td class="table_02_1"><%= v_sum_country_cnt %></td>
	          <td class="table_02_1"><%= v_sum_gonggong_cnt %></td>
	          <td class="table_02_1"><%= v_sum_freelancer_cnt %></td>
	          <td class="table_02_1"><%= v_sum_preoffice_cnt %></td>
	          <td class="table_02_1"><%= v_sum_office_cnt %></td>
	          <td class="table_02_1"><%= v_sum_jubu_cnt %></td>
	          <td class="table_02_1"><%= v_sum_miltiary_cnt %></td>
	          <td class="table_02_1"><%= v_sum_univoffice_cnt %></td>
	          <td class="table_02_1"><%= v_sum_hospital_cnt %></td>
	          <td class="table_02_1"><%= v_sum_lawyer_cnt %></td>
	          <td class="table_02_1"><%= v_sum_broad_cnt %></td>
	          <td class="table_02_1"><%= v_sum_jongkyo_cnt %></td>
	          <td class="table_02_1"><%= v_sum_entertain_cnt %></td>
	          <td class="table_02_1"><%= v_sum_sports_cnt %></td>
	          <td class="table_02_1"><%= v_sum_proffesor_cnt %></td>
	          <td class="table_02_1"><%= v_sum_lecter_cnt %></td>
	          <td class="table_02_1"><%= v_sum_owner_cnt %></td>
	          <td class="table_02_1"><%= v_sum_etc_cnt %></td>
	          <td class="table_02_1"><%= v_sum_johndoe_cnt %></td>
	        </tr>
        <% } %>
     
      </table>
      
    <!----------------- ???????????? ?? ----------------->
  
<!----------------- ???? ----------------->
<script type="text/javascript">
		//????1
    var params = { wmode:"transparent" };
    var flashVars1 = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "????????;<%=v_sum_high_cnt%>\n??????;<%=v_sum_univ_cnt%>\n??????;<%=v_sum_country_cnt%>\n????????;<%=v_sum_gonggong_cnt%>\n????????;<%=v_sum_freelancer_cnt%>\n??????????;<%=v_sum_preoffice_cnt%>\n??????;<%=v_sum_office_cnt%>\n????;<%=v_sum_jubu_cnt%>\n????;<%=v_sum_miltiary_cnt%>\n??????;<%=v_sum_univoffice_cnt%>\n??????;<%=v_sum_hospital_cnt%>\n??????;<%=v_sum_lawyer_cnt%>\n??????;<%=v_sum_broad_cnt%>\n??????;<%=v_sum_jongkyo_cnt%>\n??????;<%=v_sum_entertain_cnt%>\n??????;<%=v_sum_sports_cnt%>\n????;<%=v_sum_proffesor_cnt%>\n????;<%=v_sum_lecter_cnt%>\n??????;<%=v_sum_owner_cnt%>\n????;<%=v_sum_etc_cnt%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>0</enabled></legend><pie><brightness_step>-35</brightness_step><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv", "400", "360", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars1, params); });


  	//????2
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",       
        chart_data: "????????;<%=v_sum_high_cnt%>\n??????;<%=v_sum_univ_cnt%>\n??????;<%=v_sum_country_cnt%>\n????????;<%=v_sum_gonggong_cnt%>\n????????;<%=v_sum_freelancer_cnt%>\n??????????;<%=v_sum_preoffice_cnt%>\n??????;<%=v_sum_office_cnt%>\n????;<%=v_sum_jubu_cnt%>\n????;<%=v_sum_miltiary_cnt%>\n??????;<%=v_sum_univoffice_cnt%>\n??????;<%=v_sum_hospital_cnt%>\n??????;<%=v_sum_lawyer_cnt%>\n??????;<%=v_sum_broad_cnt%>\n??????;<%=v_sum_jongkyo_cnt%>\n??????;<%=v_sum_entertain_cnt%>\n??????;<%=v_sum_sports_cnt%>\n????;<%=v_sum_proffesor_cnt%>\n????;<%=v_sum_lecter_cnt%>\n??????;<%=v_sum_owner_cnt%>\n????;<%=v_sum_etc_cnt%>",
        chart_settings: "<settings><text_size>13</text_size><data_type>csv</data_type><plot_area><margins><left>50</left><right>40</right><top>50</top><bottom>40</bottom></margins></plot_area><grid><category><dashed>1</dashed><dash_length>4</dash_length></category><value><dashed>1</dashed><dash_length>4</dash_length></value></grid><axes><category><width>1</width><color>888888</color></category><value><width>1</width><color>888888</color></value></axes><values><value><min>0</min></value></values><legend><enabled>0</enabled></legend><angle>0</angle><column><width>40</width><balloon_text>{title}: {value}</balloon_text><grow_time>3</grow_time><sequenced_grow>1</sequenced_grow></column><graphs><graph gid='0'><title>??????</title><color>#df3d1d,#dfaf1d</color></graph></graphs><labels><label lid='0'><text><![CDATA[]]></text><y>18</y><text_color>000000</text_color><align>center</align></label></labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/amcolumn.swf", "chartdiv1", "400", "360", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });
</script>
<style>
div.chartbox { width:968px; height:440px; position:relative; border:1px solid #77a3eb; }
div.chartbox table.chartset { width:1000px; padding:0; position:absolute; top:0px; left:-16px; }
div.chartbox table.chartset td { padding:0; margin:0; text-align:center; }
div.chartbox table.chartset td p { width:100px; height:29px; margin:0 auto; font-family:"malgun gothic","gulim","sans-serif"; font-size:14px; font-weight:bold; background-image:url("/js/assets/amcharts/images/bg_chart_title.png"); background-repeat:no-repeat; background-color:#ffffff; text-align:center; line-height:26px; }
</style>
<div class="chartbox">
	<table class="chartset" cellpadding="0" cellspacing="0">
		<tr>
			<td><div id="chartdiv"></div><br><br><p>?????? ??????</p></td>
			<td><div id="chartdiv1"></div><br><br><p>??????</p></td>
		</tr>
	</table>
</div>   
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>