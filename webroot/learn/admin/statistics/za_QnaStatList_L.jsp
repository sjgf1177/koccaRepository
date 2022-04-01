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
    String v_action    = box.getString("p_action");
    String v_process = box.getString("p_process");

    String v_sindate  = box.getStringDefault("p_project_year_s",  FormatDate.getDate("yyyy"));
    String v_eindate  = box.getStringDefault("p_project_year_e",  FormatDate.getDate("yyyy"));

    String v_fmt_usercnt = "";
    String v_indate      = "";
    String v_usercnt     = "";
    int    v_maxcnt      = 0;
    int    v_usercnt_i   = 0;
    int    v_percent     = 0;
    
    int    v_man_i = 0;
    int    v_woman_i = 0;
    
    int    v_man_total = 0;
    int    v_woman_total = 0;    
    
    int    v_fmt_usercnt_tatal = 0;

    int     v_sindate_i = 0;
    int     v_eindate_i = 0;
    
    String 	gubundisplay = "";
    String 	v_woman = "";
    

    ArrayList list = null;
    int listsize = 0;
    list = (ArrayList)request.getAttribute("selectList");
    listsize = list.size();

%>

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
            } else if (gubundisplay.equals("02")) {
            	gubundisplay = "교육문의";
            } else if (gubundisplay.equals("03")) {
            	gubundisplay = "직무교육";
            } else if (gubundisplay.equals("04")) {
            	gubundisplay = "시스템";
            } else if (gubundisplay.equals("05")) {
            	gubundisplay = "오프라인";
            } else if (gubundisplay.equals("06")) {
            	gubundisplay = "이벤트";
            } else if (gubundisplay.equals("07")) {
            	gubundisplay = "콘텐츠";
            } else if (gubundisplay.equals("08")) {
            	gubundisplay = "특강";
            } else if (gubundisplay.equals("09")) {
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
        
          </tr>
          </table>

       

          
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

