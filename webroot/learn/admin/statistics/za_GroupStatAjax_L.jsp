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
	
    String v_cate = "";
    String v_grnm = "";
    
    int v_subj_cnt = 0;
    int v_user_cnt = 0;
    int v_grad_cnt = 0;
    int v_ngrad_cnt = 0;
    
    double v_grad_rate = 0;
    double v_sul_rate = 0;
    
    //챠트용
    double v_broad_rate = 0;
    double v_game_rate = 0;
    double v_culture_rate = 0;
    int v_broad_stold = 0;
    int v_game_stold = 0;
    int v_culture_stold = 0; 
    
    int v_game_rate_cnt = 0;
    int v_broad_rate_cnt = 0;
    int v_culture_rate_cnt = 0;
    
    
    int 	v_rowspantmp = 0;
    int		v_total_subj_cnt	= 0;
    int		v_total_user_cnt	= 0;
    int		v_total_grad_cnt	= 0;
    int		v_total_man_cnt		= 0;
    int		v_total_woman_cnt	= 0;
    int		v_total_man_rate	= 0;
    
    int		v_sum_subj_cnt	= 0;
    int		v_sum_user_cnt	= 0;
    int		v_sum_grad_cnt	= 0;
    int 	v_sul_cnt = 0;
    
    double 	v_total_grad_rate = 0;
    double  v_total_sul_rate = 0;
    double  v_sum_sul_rate = 0;
    
    
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function whenExcel() {   
	 document.form1.target = 'mainFrame';
     $("#isExcel").val("true");
     document.form1.submit();
     $("#isExcel").val("false");

 }
 
function ordering(sortitem) {   
	 //document.form1.target = 'mainFrame';
	 //document.form1.ordering = sortitem;
	 //document.form1.submit();   
	 
	 var newpart =  $("#param1").val();				
	 var newsubjseq = $("#param").val();
	 var newsubjcourse = $("#param2").val();
	 
	 $.ajax({ type : "POST", 			
	   		url : "/servlet/controller.course.CourseStateAdminServlet?p_process=selectGroupStat", 
	   		//data: "&param="+newsubjseq+"&param1="+newcourse, 
	   		data: "&param="+newsubjseq+"&param1="+newpart+"&param2="+newsubjcourse+"&param3="+sortitem, 
	   		//data: "&param="+<%=box.getString("param")%>+"&param1="+<%=box.getString("param1")%>+"&param2="+<%=box.getString("param2")%>+"&param3="+sortitem, 
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
          <td align="left" height="20">&nbsp;<b>교육그룹별검색</b></td>
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
        <input type="hidden" name="p_process" value="selectGroupStat">
        <input type="hidden" name="p_action" value ="/servlet/controller.course.CourseStateAdminServlet">
        <input type="hidden" name="param"    id ="param" value="<%=box.getString("param")%>">
        <input type="hidden" name="param1" 	id ="param1" value="<%=box.getString("param1")%>">
        <input type="hidden" name="param2" 	id ="param2" value="<%=box.getString("param2")%>">
        <input type="hidden" name="ordering"  value="">
        <input type="hidden" name="isExcel" id="isExcel" value="false">
      </form>
      
      <table class="table_out" cellspacing="1" cellpadding="5">		
        <tr>
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr>
          
          <td class="table_title"><b>No</b></td>
          <td class="table_title"><a href="javascript:ordering('subjnm')"><b>교육그룹</b></a></td>         
          <td class="table_title"><a href="javascript:ordering('user_cnt')"><b>교육인원</b></a></td>
          <td class="table_title"><a href="javascript:ordering('grad_cnt')"><b>수료</b></a></td>
          <td class="table_title"><a href="javascript:ordering('ngrad_cnt')"><b>미수료</b></a></td>
          <td class="table_title"><a href="javascript:ordering('grad_rate')"><b>수료율</b></a></td>
          <td class="table_title"><a href="javascript:ordering('sul_rate')"><b>만족도</b></a></td>
         </tr>
         
 <%  	
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");
 			DecimalFormat df = new DecimalFormat("###,##0.0");
        	for (i=0; i<list.size(); i++) {
        		
		 		DataBox dbox = (DataBox)list.get(i);

		 		v_grnm		= dbox.getString("d_grnm");
		        v_user_cnt	= dbox.getInt("d_user_cnt");
		        v_grad_cnt	= dbox.getInt("d_grad_cnt");
		        v_ngrad_cnt	= dbox.getInt("d_ngrad_cnt");
		        v_grad_rate	= dbox.getDouble("d_grad_rate");
		        v_sul_rate	= dbox.getDouble("d_sul_rate"); 
		        
			    v_total_user_cnt	+= v_user_cnt;
			    v_total_grad_cnt	+= v_grad_cnt;
			    v_total_grad_rate	= (v_total_grad_cnt)*100.0 / v_total_user_cnt ;
			    
			    if (v_sul_rate > 0) {
			    	v_total_sul_rate    += v_sul_rate;
			    	v_sul_cnt++;
			    }
		        
		%>
		 <tr class="table_02_1">
		 
		<td class="table_02_1"><%= i+1 %></td>
          <td class="table_02_1"><%= v_grnm %></td>
          <td class="table_02_1"><%= v_user_cnt %></td>
          <td class="table_02_1"><%= v_grad_cnt %></td>
          <td class="table_02_1"><%= v_ngrad_cnt %></td>
          <td class="table_02_1"><%= df.format(v_grad_rate) %></td>
          <td class="table_02_1"><%= df.format(v_sul_rate) %></td>
        </tr>
		<%
        	 }
        	if (i == 0) { 
		%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="15">등록된 내용이 없습니다.</td>
        </tr>
          <% } else { 
        	  
        	  if (v_sul_cnt  == 0) {
        		v_total_sul_rate = 0;
          	} else {
          		v_total_sul_rate = v_total_sul_rate/v_sul_cnt;
          	}         
          %>
        <tr class="table_02_3">
          <td class="table_02_3" colspan="2">합계</td>
          <td class="table_02_3"><%= v_total_user_cnt %></td>
          <td class="table_02_3"><%= v_total_grad_cnt %></td>
          <td class="table_02_3"><%= v_total_user_cnt - v_total_grad_cnt %></td>
          <td class="table_02_3"><%= df.format(v_total_grad_cnt*100/v_total_user_cnt) %>점</td>
          <td class="table_02_3"><%= df.format(v_total_sul_rate)%>%</td>
        </tr>
        <% } %>
     
      </table>
      <br/>
    <!----------------- 과정기준조회 끝 ----------------->
    
<!----------------- 챠트 ----------------->
<script type="text/javascript">
		//차트1
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",
        chart_data: "방송;<%=v_broad_stold%>\n문화;<%=v_culture_stold%>\n게임;<%=v_game_stold%>",
        chart_settings: "<settings><data_type>csv</data_type><text_size>13</text_size><legend><align>center</align><enabled>1</enabled></legend><pie><brightness_step>-15</brightness_step><base_color>#ffffff</base_color><inner_radius>30</inner_radius><height>7</height><angle>10</angle><gradient></gradient></pie><animation><start_time>1</start_time><pull_out_time>1</pull_out_time></animation><data_labels><show>{title}</show><max_width>100</max_width></data_labels></settings>"
    };
    $(function(){ swfobject.embedSWF("/js/assets/amcharts/flash/ampie.swf", "chartdiv", "333", "222", "8.0.0", "/js/assets/amcharts/flash/expressInstall.swf", flashVars, params); });


  	//차트2
    var params = { wmode:"transparent" };
    var flashVars = 
    {
        path: "/js/assets/amcharts/flash/",       
        chart_data: "방송;<%=v_broad_rate/v_broad_rate_cnt%>\n문화;<%=v_culture_rate/v_culture_rate_cnt%>\n게임;<%=v_game_rate/v_game_rate_cnt%>",
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
			<td><div id="chartdiv"></div><p>수료율</p></td>
			<td><div id="chartdiv1"></div><p>만족도</p></td>
		</tr>
	</table>
</div>
    
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>