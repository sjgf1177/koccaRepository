<%
//**********************************************************
//  1. 제      목: 교수메세지
//  2. 프로그램명 : za_TutorMessageAdmin_L.jsp
//  3. 개      요: 교수메세지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: msc
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    int    v_pageno         = box.getInt("p_pageno");

    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");    //과정&코스
    String  ss_action       = box.getString("s_action");

    String v_subj   =  "";
    String v_subjnm = "";
    String v_userid = "";
    String v_name   = "";
    String v_title  = "";
    String v_indate = "";
    int v_seq = 0;
    int v_cnt = 0;

    int    v_dispnum        =  0;
    int    v_totalpage      =  0;
    int    v_rowcount       =  0;
    int     i               =  0;

    ArrayList list      = null;
    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
      list = (ArrayList)request.getAttribute("selectMessageList");
    }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = index;
        document.form1.action = '/servlet/controller.tutor.TutorMessageAdminServlet';
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }

    function goPage(pageNum) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = pageNum;
        document.form1.action = '/servlet/controller.tutor.TutorMessageAdminServlet';
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }

    function select(seq) {
        document.form1.action = "/servlet/controller.tutor.TutorMessageAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.submit();

    }

    function whenSelection(p_action) {
        if (p_action=="go"){
/*
            if(document.form1.s_subjcourse.value == 'ALL') {
                alert("선택된 과정이 없습니다.");
                return;
            }
*/
            top.ftop.setPam();
        }
        document.form1.s_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.tutor.TutorMessageAdminServlet';
        document.form1.p_process.value = 'listPage';
        document.form1.submit();
    }

    function insertPage() {
        if(document.form1.s_subjcourse.value == 'ALL') {
            alert("선택된 과정이 없습니다.");
            return;
        }
        document.form1.action = "/servlet/controller.tutor.TutorMessageAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_pageno"  value="">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="p_seq"     value = "">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

   <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
            <td><img src="/images/admin/tutor/tit_tutor_message.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <br>

      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center" valign="middle">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle">
                      <script type="text/javascript">

                        function Main_subjcourse(){
                            //var upper = document.form1.s_upperclass;
                            var upper = document.getElementsByName("s_upperclass")[0];
                            var middle = document.getElementsByName("s_middleclass")[0];
                            var lower = document.getElementsByName("s_lowerclass")[0];
                            //var middle = document.form1.s_middleclass;
                            //var lower = document.form1.s_lowerclass;
                            //alert(upper);
                            //alert(middle);
                            alert(lower.value);
                            changes_subjcourse(upper.value, middle.value, lower.value);
                            
                        }
                        </script>
                        <!------------------- 조건검색 시작 ------------------------->
                         대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" " 
                                onChange="changes_middleclass(this.value);Main_subjcourse()" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
                            중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>" 
                                onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse()" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
                            소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>" 
                                onChange="Main_subjcourse()" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" />
                      </td>
                      <td width="23%" colspan="2">
                        과정 <kocca_select:select name="s_subjcourse" sqlNum="subjlist01"
                                        param="<%=ss_upperclass%>" param2="<%=ss_middleclass%>" param3="<%=ss_lowerclass%>"
                                        onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_subjcourse %>" isLoad="true" all="true" />
                      </td>
                      <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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
      <!----------------- form 끝 ----------------->
      <br>
      <br>

      <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right">
            <a href = "javascript:insertPage();"><img src="/images/admin/button/btn_add.gif" border="0"></a>
          </td>
        </tr>
      </table>
      <br>

        <!----------------- List 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="8%"  class="table_title" height="25" align="center"><b>NO</b></td>
			<td width="20%" class="table_title"><b>과정명</b></td>
            <td width="42%" class="table_title"><b>내 용</b><b></b></td>
            <td width="8%"  class="table_title"><b>작성자</b></td>
            <td width="14%" class="table_title"><b>등록일</b></td>
            <td width="8%"  class="table_title"><b>조회수</b></td>
          </tr>
<%
        if (ss_action.equals("go")) {    //go button 선택시만 list 출력

            for( i = 0 ; i < list.size() ; i++ ){
                DataBox dbox = (DataBox)list.get(i);

                v_seq     = dbox.getInt   ("d_seq");
                v_subj    = dbox.getString("d_subj");
                v_subjnm  = dbox.getString("d_subjnm");
                v_userid  = dbox.getString("d_userid");
                v_name    = dbox.getString("d_name");
                v_title   = dbox.getString("d_title");
                v_indate  = dbox.getString("d_indate");
                v_cnt     = dbox.getInt   ("d_cnt");

                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpagecount");
                v_rowcount  = dbox.getInt("d_rowcount");
%>
            <tr>
            <td class="table_01" height="25"><%=list.size()-i%></td>    <!-- NO -->
			<td class="table_02_2"><%= v_subjnm%></td>
            <td class="table_02_2">
              <a href="javascript:select('<%=v_seq%>')"><%=v_title%></a>
              <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){          %>
                 &nbsp;&nbsp;<img src="/images/user/course/btn_new.gif" align = "absmiddle" border = "0">
              <%  }                                                                                                 %>
            </td>
            <td class="table_02_1"><%= v_name%></td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
            <td class="table_02_1"><%= v_cnt%></td>
            </tr>
<%
                }
            }

        if(i == 0 && ss_action.equals("go")){
%>
              <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="19">등록된 내용이 없습니다</td>
              </tr>
<%
        }
%>
        </table>

<%  if(i > 0 && ss_action.equals("go")){            %>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
          </tr>
        </table>
<%  }                                              %>

     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
