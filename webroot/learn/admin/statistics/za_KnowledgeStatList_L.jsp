<%
//**********************************************************
//  1. 제      목: 접속통계
//  2. 프로그램명 : za_UserEntryList_L.jsp
//  3. 개      요: 접속통계 - 월일통계 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.statistics.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    String v_year    = box.getString("param");
    String v_month   = box.getString("param1");
    String 	gubundisplay = "";
    

    ArrayList list, list2 = null;
    int listsize = 0;
    list = (ArrayList)request.getAttribute("selectList");
    list2 = (ArrayList)request.getAttribute("selectList2");
    listsize = list.size();

%>
<script type="text/javascript"> 
 function view_Bbs(subj){
     //alert(subj);
     var year = '<%=v_year%>';
     var month = '<%=v_month%>';
     $.ajax({ type : "POST", 			
	   		url : "/servlet/controller.statistics.VocStatusticServlet?p_process=selectKnowledgeList", 
	   		data: "&param="+year+"&param1="+month+"&param2="+subj, 
	   		//data: "&param=2013&param1=01&param2="+subj, 
	   		beforeSend: function(){ $('#bbslist').html('<img src="/learn/user/contents/loading.gif" border="0"/>'); },
	   		success: function(msg){ 
	   			//alert(msg);
	   			if(msg.length > 0) { $('#bbslist').html(msg); } 
	   			//$("select").multiselect();
	   		}  
	   	});//end ajax   
}
 
</script>

        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="14" class="table_top_line"></td>
          </tr>

          <tr>
            <td class="table_title" width="5%">구분</td>  
            <td class="table_title" width="5%">1월</td>                        
            <td class="table_title" width="5%">2월</td>
            <td class="table_title" width="5%">3월</td>                        
            <td class="table_title" width="5%">4월</td>
            <td class="table_title" width="5%">5월</td>                        
            <td class="table_title" width="5%">6월</td>
            <td class="table_title" width="5%">7월</td>                        
            <td class="table_title" width="5%">8월</td>
            <td class="table_title" width="5%">9월</td>                        
            <td class="table_title" width="5%">10월</td>
            <td class="table_title" width="5%">11월</td>                        
            <td class="table_title" width="5%">12월</td>
            <td class="table_title" width="5%">총합</td>
            
          </tr>
<%
        for ( int i=0; i<list.size(); i++ ) {
            DataBox dbox = (DataBox)list.get(i);  
            
            gubundisplay = dbox.getString("d_gubun2");
            
            if (gubundisplay.equals("01")) {
            	gubundisplay = "홈페이지";
            } else if (gubundisplay.equals("10")) {
            	gubundisplay = "기타";
            }
     
%>
          <tr class="table_02_1">
           <td class="table_02_1"><%=gubundisplay%></td>
           <td class="table_02_1"><%=dbox.getString("d_jan")%></td>
            <td class="table_02_1"><%=dbox.getString("d_feb")%></td>
            <td class="table_02_1"><%=dbox.getString("d_mar")%></td>
            <td class="table_02_1"><%=dbox.getString("d_apr")%></td>
            
            <td class="table_02_1"><%=dbox.getString("d_may")%></td>
            <td class="table_02_1"><%=dbox.getString("d_jun")%></td>
            
            <td class="table_02_1"><%=dbox.getString("d_jul")%></td>
            <td class="table_02_1"><%=dbox.getString("d_aug")%></td>
            
            <td class="table_02_1"><%=dbox.getString("d_sep")%></td>
            <td class="table_02_1"><%=dbox.getString("d_oct")%></td>
            
            <td class="table_02_1"><%=dbox.getString("d_nov")%></td>
            <td class="table_02_1"><%=dbox.getString("d_dec")%></td>
            <td class="table_02_1"><%=dbox.getString("d_tot")%></td>
           </tr>
<%
        }
%>
        
          </table>
          
          <br/>
          
          <div style="width:500px; float:left;" >
          <table cellspacing="1" class="table_out" cellpadding="5">
          <tr>
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="3%">번호</td>  
            <td class="table_title" width="5%">구분</td>                        
            <td class="table_title" width="40%">과정명</td>
            <td class="table_title" width="6%">튜터명</td>                        
            <td class="table_title" width="6%">게시글</td>
          </tr>
<%
        for ( int i=0; i<list2.size(); i++ ) {
            DataBox dbox = (DataBox)list2.get(i);  
%>
          <tr class="table_02_1">
           <td class="table_02_1"><%=i+1%></td>
           <td class="table_02_1"><%=dbox.getString("d_area")%></td>
            <td class="table_02_1"><a href="javascript:view_Bbs('<%=dbox.getString("d_categorycd")%>')"><%=dbox.getString("d_subjnm")%></a></td>
            <td class="table_02_1"><%=dbox.getString("d_name")%></td>
            <td class="table_02_1"><%=dbox.getString("d_que")%></td>
           </tr>
<%    } %>
          </table>
		</div>
		
		<div style="width:500px; float:left;" id="bbslist">
          
        </div>
        <div style="clear:left;"></div>
          
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

