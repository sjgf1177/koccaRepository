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
    
    String 	v_man = "";
    String 	v_woman = "";

    ArrayList list = null;
    int listsize = 0;
    if ( v_action.equals("go") ) {
        list = (ArrayList)request.getAttribute("FactoryCnt");
        listsize = list.size();
    }

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<link href="/css/layout.css" rel="stylesheet" type="text/css">

   
<SCRIPT LANGUAGE="JavaScript">
<!--

function whenSelection(p_action) {

  document.form1.p_action.value = p_action;
  document.form1.action='/servlet/controller.statistics.VocStatusticServlet';
  document.form1.target = "_self";
  document.form1.p_process.value = 'selectFactoryCnt';
  document.form1.submit();
}

function whenGrtype(){
    document.form1.p_action.value = 'change';
    document.form1.action='/servlet/controller.statistics.VocStatusticServlet';
    document.form1.target = "_self";
    document.form1.p_process.value = 'selectYearCnt';
    document.form1.submit();
}

// 탭 선택
function select_tab(sel){
    document.form1.p_action.value = 'change';
    document.form1.target = "_self";
    if(sel == "1")      { document.form1.p_process.value = "selectSubQna"; }
    else if(sel == "2") { document.form1.p_process.value = "selectQna";}
    else if(sel == "3") { document.form1.p_process.value = "selectVocCnt";}
    else if(sel == "4") { document.form1.p_process.value = "selectOnetoOne";}
    else if(sel == "5") { document.form1.p_process.value = "selectFactoryCnt";}
    document.form1.action = '/servlet/controller.statistics.VocStatusticServlet';
    document.form1.submit();
}

//-->
</SCRIPT>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_action"  value = "<%=v_action%>">
    <input type = "hidden" name = "p_gubun"   value = "">


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/statistics/tit_sum_d2.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 교육그룹 시작 ----------------->
     <table class="form_table_out" cellspacing="0" cellpadding="1">
      <tr>
        <td bgcolor="#C6C6C6" align="center">
          <table class="form_table_bg" cellspacing="0" cellpadding="0" border='0'>
            <tr>
              <td height="7"></td>
            </tr>
            <tr>
              <td align="center">

              <table width="97%" cellspacing="1" cellpadding="5" >
                <tr>
                  
                  <td > 년도
                    <select name = "p_project_year_s" >
                      <%=FormatDate.getDateOptions(2004,Integer.parseInt(FormatDate.getDate("yyyy"))+5, Integer.parseInt(v_sindate))%>
                    </select> 
                    
                  </td>
                  <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') --></td>    
                </tr>
              </table>

            </td>
        </tr>
        <tr>
          <td height="7"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

    <br>
    <br>
    <!----------------- tab 시작 ----------------->

      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="12%">

        <!----------------- button 1 시작----------------->
        
        	 <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>
                  <td class="black_butt_middle"><a href="javascript:select_tab('1')" class="c">과정Q/A</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>              
                
              </table>
              
            
        <!----------------- button 1 끝----------------->

          </td>
          <td width="1%"></td>
          <td width="12%">

        <!----------------- button 1 시작----------------->
        
        	 <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>
                  <td class="black_butt_middle"><a href="javascript:select_tab('2')" class="c">Q/A</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>              
                
              </table>
              
            
        <!----------------- button 1 끝----------------->


             </td>
             <td width="1%"></td>
                <td width="12%">

        <!----------------- button 3 시작----------------->
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>
                  <td class="black_butt_middle"><a href="javascript:select_tab('3')" class="c">VOC</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>              
                
              </table>
        <!----------------- button 3 끝----------------->
        
           </td>
          <td width="1%"></td>
          
          <td width="12%">

        <!----------------- button 3 시작----------------->
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>
                  <td class="black_butt_middle"><a href="javascript:select_tab('4')" class="c">1:1</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>              
                
              </table>
        <!----------------- button 3 끝----------------->

          </td>
          <td width="1%"></td>
          
          <td width="12%">
          
           <!----------------- button 2 시작----------------->
        	<table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="blue_butt_left"></td>
                <td class="blue_butt_top"></td>
                <td rowspan="3" class="blue_butt_right"></td>
              </tr>
              <tr>
                <td class="blue_butt_middle">지식팩토리</td>
              </tr>
              <tr>
                <td class="blue_butt_bottom"></td>
              </tr>
            </table>
        <!----------------- button 2 끝----------------->

      


          </td>
          <td width="1%"></td>
          <td width="75%">&nbsp;</td>
        </tr>
        <tr>
          <td height="2" colspan="4" bgcolor="#6699CC"></td>
        </tr>
      </table>
    <!----------------- tab 끝 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="11" class="table_top_line"></td>
          </tr>

          <tr>
           <td class="table_title" width="30%">분류</td>
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
            
          </tr>
<%
    if ( v_action.equals("go") ) {
        for ( int i=0; i<list.size(); i++ ) {
            DataBox dbox = (DataBox)list.get(i);           
     
%>
          <tr class="table_02_1">
            <td class="table_02_1"><%=dbox.getString("d_cate")%></td>
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
           </tr>
<%
        }
%>
        
          </tr>

       <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
				<div id="default" class="graph"></div>
			</td>
			</tr>
        </table>

<%
            }
%>

          </tr>
        </table>
   </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
