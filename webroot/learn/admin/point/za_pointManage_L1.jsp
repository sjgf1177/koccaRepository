<%
//**********************************************************
//  1. 제      목: 회원 정보 보기
//  2. 프로그램명: za_SearchTutor.jsp
//  3. 개      요: 회원 정보(팝업)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 23
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "java.text.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%

NumberFormat s_fmt = NumberFormat.getInstance();                //통화단위 ,표시
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_userid = box.getStringDefault("p_userid", "lee1");
    int     v_tabseq = box.getInt("p_tabseq");
    int     v_pageno    = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
    
    int     v_totalrowcount =  0;
    int     v_dispnum       = 0;  
    int     v_totalpage     = 0;  
    
    int i = 0;

    String v_tid        = "";
    String v_getpoint   = "";
    String v_getdate    = "";
    String v_title      = "";
    String v_luserid    = "";
    String v_ldate      = "";
    String v_usepoint   = "";
    String v_leftpoint  = ""; 

    ArrayList list = (ArrayList)request.getAttribute("selectList");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.point.PointManageAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.point.PointManageAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    function changeTab(idx){
    	document.form1.target = "_self";
        document.form1.p_tabseq.value = idx;
        document.form1.action = "/servlet/controller.point.PointManageAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%=v_process%>">
    <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_userid"  value = "<%=v_userid %>">
    <input type = "hidden" name = "p_tabseq"  value = "<%=v_tabseq %>">

<table width="522" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="520" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
          <!----------------- title 시작 ----------------->
          <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
            <tr>
              <td><img src="/images/admin/tutor/tit_compeople.gif"></td>
              <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
          </table>
          <br>
          <table width="97%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="left">
              <span style="background:grey;">보유포인트</span>
              <span><a href="javascript:changeTab(2);">적립내역</a></span>
              <span><a href="javascript:changeTab(3);">사용내역</a></span>
              </td>
            </tr>
          </table>
          <br>
            <table width="515" class="table_out" cellspacing="1" cellpadding="0">
                <tr>
                  <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                  <td width="25%" class="table_title"><b>변경날짜</b></td>
                  <td width="25%" class="table_title"><b>적립포인트</b></td>
                  <td width="25%" class="table_title"><b>사용포인트</b></td>
                  <td width="25%" class="table_title"><b>보유포인트</b></td>
                </tr>
              <!--회원구분 ID 성명 -->
<%
            for(i = 0; i < list.size(); i++) {

                DataBox dbox  = (DataBox)list.get(i);
                
                v_tid        = dbox.getString("d_tid")     ;
               	v_userid     = dbox.getString("d_userid")  ;
               	v_getpoint   = dbox.getString("d_getpoint");
               	v_getdate    = dbox.getString("d_getdate") ;
               	v_title      = dbox.getString("d_title")   ;
               	v_luserid    = dbox.getString("d_luserid") ;
               	v_ldate      = dbox.getString("d_ldate")   ;
               	v_usepoint   = dbox.getString("d_usepoint");
               	v_leftpoint  = dbox.getString("d_lefrpoint");
               	v_getpoint      =  s_fmt.format(dbox.getInt("d_getpoint"));
               	v_usepoint      =  s_fmt.format(dbox.getInt("d_usepoint"));
               	v_leftpoint     =  s_fmt.format(dbox.getInt("d_lefrpoint"));
               	
               	v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");

%>
                <tr>
                  <td class="table_02_1"><%=FormatDate.getFormatDate(v_ldate,"yyyy-MM-dd")%></td>
                  <td class="table_02_1"><%=v_getpoint%></td>
                  <td class="table_02_1"><%=v_usepoint%></td>
                  <td class="table_02_1"><%=v_leftpoint%></td>
                </tr>
                <%
        }
        if(i == 0){
%>
                <tr>
                  <td class="table_02_1" colspan="6">검색된 내용이 없습니다.</td>
                </tr>
                <% } %>
              </table>

            <!----------------- total 시작 ----------------->
            <table width="496" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="right" valign="absmiddle">
                  <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
            </table>
            <!----------------- total 끝 ----------------->

            <table width="496" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10"><a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif" border=0></a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
