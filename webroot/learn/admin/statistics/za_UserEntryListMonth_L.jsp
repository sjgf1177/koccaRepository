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
    String v_usercnt     = "";
    int    v_fmt_usercnt_tatal = 0;
    int    v_maxcnt     = 0;
    int    v_usercnt_i  = 0;
    int    v_percent    = 0;
    int    v_indate_i   = 0;

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
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

function whenSelection(p_action) {
  if (p_action == 'go') {
    if (document.form1.p_grtype.value == '' || document.form1.p_grtype.value == '----') {
        alert("교육기관을 선택하세요.");
        return ;
    }
  }

  document.form1.p_action.value = p_action;
  document.form1.action='/servlet/controller.statistics.UserCountServlet';
  document.form1.target = "_self";
  document.form1.p_process.value = 'selectMonthCnt';
  document.form1.submit();
}

function whenGrtype(){
    document.form1.p_action.value = 'change';
    document.form1.action='/servlet/controller.statistics.UserCountServlet';
    document.form1.target = "_self";
    document.form1.p_process.value = 'selectMonthCnt';
    document.form1.submit();
}

// 탭 선택
function select_tab(sel){
    document.form1.p_action.value = 'change';
    document.form1.target = "_self";
    if(sel == "1")      { document.form1.p_process.value = "selectYearCnt"; }
    else if(sel == "2") { document.form1.p_process.value = "selectMonthCnt";}
    document.form1.action = '/servlet/controller.statistics.UserCountServlet';
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
                    <td align="left">
<% if (StringManager.substring(box.getSession("gadmin"), 0, 1).equals("A")) { %>
                                <%=CodeConfigBean.getCodeGubunSelect("0060","",1,"p_grtype", box.getString("p_grtype"),"onChange=\"whenGrtype()\"",3) %>
<% } else  {                                                   %>
                            <%//=CodeConfigBean.getCodeName("grtype",box.getSession("grtype")) %>
                                <input type="hidden" name="p_grtype" value="<%=box.getSession("grtype")%>">
<% }                                                           %>
                    </td>
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
                  <td rowspan="3" class="blue_butt_left"></td>
                  <td class="blue_butt_top"></td>
                  <td rowspan="3" class="blue_butt_right"></td>
                </tr>
                <tr>
                  <td class="blue_butt_middle">월별</td>
                </tr>
                <tr>
                  <td class="blue_butt_bottom"></td>
                </tr>
              </table>
        <!----------------- button 2 끝----------------->
             </td>
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
            <td class="table_title" width="10%">월</td>
            <td class="table_title" width="70%">통계</td>
            <td class="table_title" width="20%">인원</td>
          </tr>
<%
    if ( v_action.equals("go") ) {
        for ( int i=0; i<list.size(); i++ ) {
            DataBox dbox = (DataBox)list.get(i);
            v_usercnt   = dbox.getString("d_usercnt");
            v_usercnt_i = Integer.parseInt(v_usercnt);
            if(v_maxcnt < v_usercnt_i) v_maxcnt = v_usercnt_i;
            }
        int k = 0;
        for(int j = 1; j <=12 ; j++){

            if(k < list.size()){
            DataBox dbox = (DataBox)list.get(k);

            v_usercnt   = dbox.getString("d_usercnt");
            v_usercnt_i = Integer.parseInt(v_usercnt);
            v_percent   = (v_usercnt_i*90)/v_maxcnt;

            v_indate_m  = dbox.getString("d_indate_m");
            v_indate_i  = Integer.parseInt(v_indate_m);
            v_usercnt_i = Integer.parseInt(v_usercnt);

            v_fmt_usercnt_tatal +=  v_usercnt_i;

            k++;
            } else {
                v_indate_m  = String.valueOf(j);
                v_indate_i  = Integer.parseInt(v_indate_m);
                v_percent   = 1;
                v_usercnt_i = 0;
            }
            NumberFormat fmt = NumberFormat.getInstance();    //통화단위 ,표시
            v_fmt_usercnt = fmt.format(v_usercnt_i);

            while (StringManager.toInt(v_indate_m)  >j) {
%>
          <tr class="table_02_1">
            <td class="table_02_1"><%=j+" 월"%></td>
            <td bgcolor="#F7F7F7">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="14" background="/images/admin/system/statistics_line.gif" width="1%"></td>
                  <td type = hidden" style="padding-left:5px"></td>
                </tr>
              </table>
            </td>
            <td class="table_02_1">0</td>
           </tr>
<%
                j++;
        }
%>
            <tr class="table_02_1">
            <td class="table_02_1"><%=v_indate_i+" 월"%></td>
            <td bgcolor="#F7F7F7">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="14" background="/images/admin/system/statistics_line.gif" width="<%=v_percent%>%"></td>
                  <td type = "hidden" style="padding-left:5px" value = "<%=v_usercnt%>"></td>
                </tr>
              </table>
            </td>
            <td class="table_02_1"><%=v_fmt_usercnt%></td>
           </tr>
<%
        }
            NumberFormat fmt = NumberFormat.getInstance();              //통화단위 ,표시
%>
          <tr>
            <td colspan="2" class="table_title">계</td>
            <td class="table_title" width="10%"><%=fmt.format(v_fmt_usercnt_tatal)%></td>
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
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
