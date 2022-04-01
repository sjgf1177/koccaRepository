<%
//**********************************************************
//  1. 제      목: OutUserComp_L 업체 리스트화면
//  2. 프로그램명 : za_OutUser_Comp_L.jsp
//  3. 개      요: OutUserComp_L 업체 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2004. 12. 26
//  7. 수      정: 신선철 2004. 12. 26
//***********************************************************
%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_comp = "", v_compnm = "", v_userid = "", v_name = "";
    String v_ldate = "";
    int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 1 ;

    ArrayList list = (ArrayList)request.getAttribute("selectoutcomp");

    String s_userid = box.getSession("userid");
    String s_usernm = box.getSession("username");

    int     v_pageno = box.getInt("p_pageno");

    String v_searchtext = box.getString("p_searchtext");

    String  v_orderColumn   = box.getStringDefault("p_orderColumn","comp"); //정렬순서
    String  v_orderType     = box.getStringDefault("p_orderType"," asc"); //정렬순서
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pagesize = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript" type="text/javascript">

//입력창에서 enter key를 쳤을때
function press_enter(e) {
    if (e.keyCode =='13'){  selectAction();  }
}

// 리스트 조회
function selectAction() {
    document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
    document.form1.p_process.value = "select";
    document.form1.submit();
}

// 회사 등록
function insertPageAction() {
    document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

// 회사 상세 정보
function selectPageAction(comp) {
    document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
    document.form1.p_process.value = "selectPage";
    document.form1.p_comp.value = comp;
    document.form1.submit();
}

// 정렬
function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.p_orderColumn.value = column;
    selectAction();
}

function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
         document.form1.p_process.value = "select";
         document.form1.submit();
}

function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
         document.form1.p_process.value = "select";
         document.form1.submit();
}

// 엑셀출력
function whenExcel() {
    window.self.name = "CompSelectExcel";
    open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
    document.form1.target = "openExcel";
    document.form1.p_process.value = "select";
    document.form1.isExcel.value = "true";
    document.form1.action='/servlet/controller.system.OutUserAdminServlet';
    document.form1.submit();
    document.form1.isExcel.value = "false";
    document.form1.target = window.self.name;
}
function pagesize(pageSize) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_pagesize.value = pageSize;
    document.form1.action = '/servlet/controller.system.OutUserAdminServlet';
    document.form1.p_process.value = "select";
    document.form1.submit();
}

</script>

</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post">
<input type="hidden" name="p_process"  id="p_process"     value= "">
<input type="hidden" name="p_pageno"        value= "<%=v_pageno%>">
<input type="hidden" name="p_comp"          value= "">
<input type="hidden" name="isExcel" id="isExcel"         value= "">

<input type="hidden" name="p_orderColumn"   value="<%=v_orderColumn%>">
<input type="hidden" name="p_orderType"     value="<%=v_orderType%>">
<input type = "hidden" name = "p_pagesize"  value = "<%=v_pagesize%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">

      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/system/tit_new_bman.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>업체 리스트</td>
        </tr>
        <tr>
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>
       <table width="970" cellpadding="1" cellspacing="0" class="form_table_out">
        <tr>
          <td align="center"><table width="970" cellpadding="0" cellspacing="0" class="form_table_bg">
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="right">
                  <table  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>회사명 : <input type="text" name="p_searchtext" class="input" value="<%=v_searchtext%>" onkeypress="press_enter(event)"></td>
                      <td>&nbsp;</td>
                      <td>
                        <a href="javascript:selectAction()" onfocus="this.blur()">
                        <img src="/images/admin/button/b_go.gif" border="0" valign="top"></a>
                      </td>
                      <td width=6></td>
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
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="80%"> </td>
          <td width="8%" align="right">
            <a href="javascript:whenExcel()" onfocus="this.blur()">
            <img src="/images/admin/button/btn_excelprint.gif" border="0"  valign="top"></a>
          </td>
          <td width="8%" align="right">
            <a href="javascript:insertPageAction()" onfocus="this.blur()">
            <img src="/images/admin/button/btn_company.gif" border="0"  valign="top"></a>
          </td>
        </tr>
      </table>

      <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="5%">순번</td>
          <td class="table_title" width="10%"><a href="javascript:whenOrder('comp')" class="e"><b>회사코드</b></a></td>
          <td class="table_title"><a href="javascript:whenOrder('compnm')" class="e"><b>회사명</b></a></td>
          <td class="table_title" width="10%"><a href="javascript:whenOrder('name')" class="e"><b>담당자명</b></a></td>
          <td class="table_title" width="10%"><a href="javascript:whenOrder('userid')" class="e"><b>담당자ID</b></a></td>
          <td class="table_title" width="10%"><a href="javascript:whenOrder('ldate')" class="e"><b>수정일</b></a></td>
        </tr>

        <%  int cnt = list.size();

             for(i = 0; i < list.size(); i++) {

                DataBox dbox = (DataBox)list.get(i);

                v_dispnum = dbox.getInt("d_dispnum");
                v_comp = dbox.getString("d_comp");
                v_compnm = dbox.getString("d_compnm");
                v_userid = dbox.getString("d_userid");
                v_name = dbox.getString("d_name");
                v_ldate  = dbox.getString("d_ldate");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
                v_rowcount = dbox.getInt("d_rowcount");
        %>

        <tr>
          <td class="table_02_1"><%=v_dispnum%></td>
          <td class="table_02_1"><%=v_comp%></td>
          <td class="table_02_1"><a href="javascript:selectPageAction('<%=v_comp%>')" onfocus=this.blur()><%=v_compnm%></a>
          <td class="table_02_1"><%=v_name%></td>
          <td class="table_02_1"><%=v_userid%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd")%></td>
        </tr>
    <%}%> <!-- for문 끝 -->
      </table> </td>
  </tr>
   <tr>
     <td height = "30" valign = "bottom" align = "right" colspan = "3"><%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %></td>
   </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>