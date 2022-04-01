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
<%@page errorPage = "/learn/library/error.jsp" %>
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

    String v_sindate  = box.getStringDefault("p_project_year_s",  FormatDate.getDate("yyyy"));
    String v_action    = box.getString("p_action");
    String v_process = box.getString("p_process");

    String v_fmt_usercnt = "";
    String v_indate      = "";
    String v_indate_y    = "";
    String v_indate_m    = "";
    String[] v_usercnt     = new String[20];
    String[] v_addr     = new String[20];
    int    v_fmt_usercnt_tatal = 0;
    int    v_maxcnt     = 0;
    int    v_usercnt_i  = 0;
    int    v_percent    = 0;
    int    v_indate_i   = 0;    
    
    String 	v_man = "";
    String 	v_woman = "";
    
    int    v_man_i = 0;
    int    v_woman_i = 0;
    
    int    v_man_total = 0;
    int    v_woman_total = 0;        

    ArrayList list = null;
    int listsize = 0;
    if ( v_action.equals("go") ) {
        list = (ArrayList)request.getAttribute("UserCnt");
        listsize = list.size();
    }

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
 <script language="javascript" type="text/javascript" src="/js/flot/excanvas.min.js"></script>
 <script language="javascript" type="text/javascript" src="/js/flot/jquery.js"></script>
 <script language="javascript" type="text/javascript" src="/js/flot/jquery.flot.js"></script>
 <script language="javascript" type="text/javascript" src="/js/flot/jquery.flot.pie.js"></script>
  
<script language = "javascript" src = "/script/cresys_lib.js"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--

function whenSelection(p_action) {
  
  document.form1.p_action.value = p_action;
  document.form1.action='/servlet/controller.statistics.UserCountServlet';
  document.form1.target = "_self";
  document.form1.p_process.value = 'selectLocationCnt';
  document.form1.submit();
}

function whenGrtype(){
    document.form1.p_action.value = 'change';
    document.form1.action='/servlet/controller.statistics.UserCountServlet';
    document.form1.target = "_self";
    document.form1.p_process.value = 'selectLocationCnt';
    document.form1.submit();
}

// 탭 선택
function select_tab(sel){
    document.form1.p_action.value = 'change';
    document.form1.target = "_self";
    if(sel == "1")      { document.form1.p_process.value = "selectYearCnt"; }
    else if(sel == "2") { document.form1.p_process.value = "selectMonthCnt";}
    else if(sel == "3") { document.form1.p_process.value = "selectLocationCnt";}
    else if(sel == "4") { document.form1.p_process.value = "selectAgeCnt";}
    else if(sel == "5") { document.form1.p_process.value = "selectVocationCnt";}
    document.form1.action = '/servlet/controller.statistics.UserCountServlet';
    document.form1.submit();
}

//-->
</SCRIPT>
<style type="text/css">
	* {
	  font-family: sans-serif;
	}
	
	body
	{
		padding: 0 1em 1em 1em;
	}
	
	div.graph
	{
		width: 400px;
		height: 300px;
		float: left;
		border: 1px dashed gainsboro;
	}
	
	label
	{
		display: block;
		margin-left: 400px;
		padding-left: 1em;
	}
	
	h2
	{
		padding-top: 1em;
		margin-bottom: 0;
		clear: both;
		color: #ccc;
	}
	
	code
	{
		display: block;
		background-color: #eee;
		border: 1px dashed #999;
		padding: 0.5em;
		margin: 0.5em;
		color: #666;
		font-size: 10pt;
	}
	
	code b
	{
		color: black;
	}
	
	ul
	{
		font-size: 10pt;
	}
	
	ul li
	{
		margin-bottom: 0.5em;
	}
	
	ul.options li
	{
		list-style: none;
		margin-bottom: 1em;
	}
	
	ul li i
	{
		color: #999;
	}
</style>

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
     <table class="form_table_out" cellspacing="0" cellpadding="1">
      <tr>
        <td bgcolor="#C6C6C6" align="center">
          <table class="form_table_bg" cellspacing="0" cellpadding="0" border='0'>
            <tr>
              <td height="7"></td>
            </tr>
            <tr>
              <td align="center">

                  <!----------------- 교육그룹 시작 ----------------->
                <table width="97%" cellspacing="1" cellpadding="5">
                  <tr>                  
                    <td> 년도
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

    <!----------------- tab 시작 ----------------->
       <br>
       <br>
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
                  <td class="black_butt_middle"><a href="javascript:select_tab('1')" class="c">년도별</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>              
                
              </table>
        <!----------------- button 1 끝----------------->

          </td>
          <td width="1%"></td>
          <td width="12%">

        <!----------------- button 2 시작----------------->
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>
                  <td class="black_butt_middle"><a href="javascript:select_tab('2')" class="c">월별</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>              
                
              </table>
        <!----------------- button 2 끝----------------->
             </td>
           <td width="1%"></td>  
           <td width="12%">

        <!----------------- button 3 시작----------------->
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="blue_butt_left"></td>
                  <td class="blue_butt_top"></td>
                  <td rowspan="3" class="blue_butt_right"></td>
                </tr>
                <tr>
                  <td class="blue_butt_middle">지역별</td>
                </tr>
                <tr>
                  <td class="blue_butt_bottom"></td>
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
                  <td class="black_butt_middle"><a href="javascript:select_tab('4')" class="c">연령별</a></td>
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
                  <td class="black_butt_middle"><a href="javascript:select_tab('5')" class="c">직업별</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>              
                
              </table>
        <!----------------- button 3 끝----------------->

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
            <td class="table_title" width="10%">지역</td>            
            <td class="table_title" width="20%">인원</td>
            <td class="table_title" width="20%">남자</td>
            <td class="table_title" width="20%">여자</td>
          </tr>
<%
if ( v_action.equals("go") ) {    
    for ( int i=0; i<list.size(); i++ ) {
        DataBox dbox = (DataBox)list.get(i);
        v_addr[i]   = dbox.getString("d_addr");
        v_usercnt[i]   = dbox.getString("d_usercnt");
        v_usercnt_i = Integer.parseInt(v_usercnt[i]);
        if(v_maxcnt < v_usercnt_i) v_maxcnt = v_usercnt_i;
    
        v_indate    = dbox.getString("d_indate");
        v_usercnt_i = Integer.parseInt(v_usercnt[i]);
        v_percent     = (v_usercnt_i*90)/v_maxcnt;
        
        v_man = dbox.getString("d_man");
        v_woman = dbox.getString("d_woman");
        
        v_man_i = Integer.parseInt(v_man);
        v_woman_i = Integer.parseInt(v_woman);
                  

        v_fmt_usercnt_tatal +=  v_usercnt_i;
        v_man_total += v_man_i;
        v_woman_total += v_woman_i;

        NumberFormat fmt = NumberFormat.getInstance();    //통화단위 ,표시
        v_fmt_usercnt = fmt.format(v_usercnt_i);
           
%>
            <tr class="table_02_1">
            <td class="table_02_1"><%=v_addr[i]%></td>           
            <td class="table_02_1"><%=fmt.format(v_usercnt_i)%></td>
            <td class="table_02_1"><%=fmt.format(v_man_i)%></td>
             <td class="table_02_1"><%=fmt.format(v_woman_i)%></td>
           </tr>
<%
        }
            NumberFormat fmt = NumberFormat.getInstance();              //통화단위 ,표시
%>
          <tr>
            <td class="table_title">계</td>
            <td class="table_title" width="10%"><%=fmt.format(v_fmt_usercnt_tatal)%></td>
            <td class="table_title" width="10%"><%=fmt.format(v_man_total)%></td>
            <td class="table_title" width="10%"><%=fmt.format(v_woman_total)%></td>
          </tr>

<%
    }
%>

      </tr>
      </table>
<%  if ( v_action.equals("go") ) {                              %>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right" class="ms">◈ <b><font color="#336699"><%=v_sindate%>년 </font>월별 회원등록 현황</b></td>
        </tr>
      </table>
<%  }                                                            %>
    <br>
   </td>
  </tr>
</table>
<div id="default" class="graph"></div>
<script type="text/javascript">
$(function () {
	// data
	var data = [
		{ label: "<%=v_addr[0]%>",  data: <%=v_usercnt[0]%>},
		{ label: "<%=v_addr[1]%>",  data: <%=v_usercnt[1]%>},
		{ label: "<%=v_addr[2]%>",  data: <%=v_usercnt[2]%>},
		{ label: "<%=v_addr[3]%>",  data: <%=v_usercnt[3]%>},
		{ label: "<%=v_addr[4]%>",  data: <%=v_usercnt[4]%>},
		{ label: "<%=v_addr[5]%>",  data: <%=v_usercnt[5]%>},
		{ label: "<%=v_addr[6]%>",  data: <%=v_usercnt[6]%>},
		{ label: "<%=v_addr[7]%>",  data: <%=v_usercnt[7]%>},
		{ label: "<%=v_addr[8]%>",  data: <%=v_usercnt[8]%>},
		{ label: "<%=v_addr[9]%>",  data: <%=v_usercnt[9]%>},
		{ label: "<%=v_addr[10]%>",  data: <%=v_usercnt[10]%>},
		{ label: "<%=v_addr[11]%>",  data: <%=v_usercnt[11]%>},
		{ label: "<%=v_addr[12]%>",  data: <%=v_usercnt[12]%>},
		{ label: "<%=v_addr[13]%>",  data: <%=v_usercnt[13]%>}
	];
	/*var data = [
		{ label: "Series1",  data: [[1,10]]},
		{ label: "Series2",  data: [[1,30]]},
		{ label: "Series3",  data: [[1,90]]},
		{ label: "Series4",  data: [[1,70]]},
		{ label: "Series5",  data: [[1,80]]},
		{ label: "Series6",  data: [[1,0]]}
	];*/
	
	/*
	for( var i = 0; i<series; i++)
	{
		data[i] = { label: "Series"+(i+1), data: Math.floor(Math.random()*100)+1 }
	}
	*/

	// DEFAULT
    $.plot($("#default"), data, 
	{
		series: {
			pie: { 
				show: true
			}
		}
	});
	
	
});

</script>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
