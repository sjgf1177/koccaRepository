<%
//**********************************************************
//  1. 제      목: 입과인원 조회
//  2. 프로그램명: za_searchStudent.jsp
//  3. 개      요: 발급대상자 검색
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.19
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");

    String v_grseqnm    = "";
    int    v_studentcnt = 0;
    double v_price      = 0;
    String v_name       = "";
    String v_userid     = "";
    String v_comptext   = "";
    String v_subjnm     = "";
    double v_biyong     = 0;
    String v_appdate    = "";
    
    int    v_total_studentcnt = 0;
    double v_total_biyong     = 0;
    
    ArrayList list1 = (ArrayList)request.getAttribute("searchGrInfo");
    ArrayList list2 = (ArrayList)request.getAttribute("searchStudent");

    if (list1.size() > 0) {
        DataBox dbox  = (DataBox)list1.get(0);
        v_grseqnm    = dbox.getString("d_grseqnm");
        v_studentcnt = dbox.getInt("d_studentcnt");
        v_price      = dbox.getDouble("d_price");
    }
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<script type="text/javascript" src="/script/portal/common.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" onsubmit="javascript:search();">

<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="97%" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
          <!----------------- title 시작 ----------------->
          <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
            <tr>
              <td><img src="/images/admin/portal/s.1_admin01.gif"></td>
              <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
          </table>
          <br/>
	      <table width="97%" cellspacing="0" cellpadding="1" class="form_table_out">
	        <tr>
	          <td bgcolor="#C6C6C6" align="center">
				<table cellspacing="2" cellpadding="2" class="form_table_bg" >
				  <tr>
				    <td align="center">
				      <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
				        <tr>
				          <td width="60" align="left" valign="middle">교육차수 : </td>
				          <td align="left" valign="middle"><%= v_grseqnm %></td>
				        </tr>
				        <tr>
				          <td align="left" valign="middle">입과인원 : </td>
				          <td align="left" valign="middle"><%= v_studentcnt %>명</td>
				        </tr>
				        <tr>
				          <td align="left" valign="middle">결제액 : </td>
				          <td align="left" valign="middle"><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %>원</td>
				        </tr>				
				      </table>
				    </td>
				  </tr>
				</table>
			  </td>
			</tr>
	      </table>
          <br>
            <table width="97%" class="table_out" cellspacing="1" cellpadding="0">
                <tr>
                  <td colspan="7" class="table_top_line"></td>
                </tr>
                <tr>
                  <td class="table_title"><b>번호</b></td>
                  <td class="table_title"><b>성명</b></td>
                  <td class="table_title"><b>ID</b></td>
                  <td class="table_title"><b>소속</b></td>
                  <td class="table_title"><b>과정명</b></td>
                  <td class="table_title"><b>수강료</b></td>
                  <td class="table_title"><b>입과일</b></td>
                </tr>
              <!--회원구분 ID 성명 -->
<%
            for(int i = 0; i < list2.size(); i++) {

            	DataBox dbox  = (DataBox)list2.get(i);
                v_userid   = dbox.getString("d_userid");
                v_name     = dbox.getString("d_name");
                v_comptext = dbox.getString("d_comptext");
                v_subjnm   = dbox.getString("d_subjnm");
                v_biyong   = dbox.getDouble("d_biyong");
                v_appdate  = dbox.getString("d_appdate");
                
                v_total_biyong += v_biyong;
%>
                <tr>
                  <td class="table_02_1"><%=i + 1%></td>
                  <td class="table_02_1"><%=v_name%></td>
                  <td class="table_02_1"><%=v_userid%></td>
                  <td class="table_02_1"><%=v_comptext%></td>
                  <td class="table_02_1"><%=v_subjnm%></td>
                  <td class="table_02_1"><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %></td>
                  <td class="table_02_1"><%= FormatDate.getFormatDate(v_appdate,"yyyy/MM/dd") %></td>
                </tr>
                <%
        }
        if(list2.size() == 0){
%>
                <tr>
                  <td class="table_02_1" colspan="7">입과인원이 없습니다.</td>
                </tr>
<% 		} else {
%>
                <tr class="table_02_3">
                  <td class="table_02_3" colspan="2">인원</td>
                  <td class="table_02_3" colspan="2"><%= list2.size() %></td>
                  <td class="table_02_3">총합</td>
                  <td class="table_02_3" colspan="2"><%= new java.text.DecimalFormat("###,###,##0").format(v_total_biyong) %></td>
                 </tr>
<%
		}
%>
              </table>

            <table width="97%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10"><a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif" border=0></a></td>
              </tr>
              <tr>
                <td height="5"></td>
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
