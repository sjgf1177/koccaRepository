<%
//**********************************************************
//  1. 제      목: 결제취소
//  2. 프로그램명: za_PayCancel_P.jsp
//  3. 개      요: 결제취소
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.20
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

    String v_tid          = "";
    String v_goodname     = "";
    String v_buyername    = "";
    String v_pgauthdate   = "";
    String v_paymethod   = "";
    double v_price        = 0;
    double v_biyong       = 0;
    double v_discount     = 0;
    double v_total_biyong = 0;
    String v_subjnm       = "";
    
    ArrayList list = (ArrayList)request.getAttribute("payInfo");

    if (list.size() > 0) {
        DataBox dbox   = (DataBox)list.get(0);
        v_tid          = dbox.getString("d_tid");
        v_goodname     = dbox.getString("d_goodname");
        v_buyername    = dbox.getString("d_buyername");
        v_pgauthdate   = dbox.getString("d_pgauthdate");
        v_pgauthdate   = FormatDate.getFormatDate(v_pgauthdate,"yyyy/MM/dd");
        v_paymethod    = dbox.getString("d_paymethod");
        v_total_biyong = dbox.getDouble("d_total_biyong");
        v_discount     = dbox.getDouble("d_discount");
        v_price        = dbox.getDouble("d_price");
    }
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
function fnPayCancel() {

    if (blankCheck(document.ini.p_reason.value)) {
    	document.ini.p_reason.focus();
        alert("취소사유를 입력해 주십시오");
        return;
    }

    if(!confirm("취소하시겠습니까?\n결제를 취소하시면 수강신청내용과 결제정보가 함께 취소됩니다.")){
        return;
    }
   		
    document.ini.target = "_self"
    document.ini.p_tid.value = "<%= v_tid %>";
    document.ini.p_paymethod.value = "<%= v_paymethod %>";
	document.ini.p_process.value = 'CancelPropose';
    document.ini.action = "/servlet/controller.polity.SchoolfeeAdminServlet";
    document.ini.submit();

	
}
-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "ini" method = "post">
    <input type='hidden' name='p_tid'	    />
    <input type='hidden' name='p_paymethod' />
    <input type='hidden' name='p_process' />

<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="97%" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
          <!----------------- title 시작 ----------------->
          <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
            <tr>
              <td><img src="/images/admin/portal/s.1_admin02.gif"></td>
              <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
          </table>
          <br/>
          <table width="97%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
              <td class=sub_title>결제취소</td>
            </tr>
          </table>
          
	      <table width="97%" cellspacing="0" cellpadding="1" class="form_table_out">
	        <tr>
	          <td bgcolor="#C6C6C6" align="center">
				<table cellspacing="2" cellpadding="2" class="form_table_bg" >
				  <tr>
				    <td align="center">
				      <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
				        <tr>
				          <td width="60" align="left" valign="middle">결제명 : </td>
				          <td align="left" valign="middle"><%= v_goodname %></td>
				        </tr>
				        <tr>
				          <td align="left" valign="middle">결제자 : </td>
				          <td align="left" valign="middle"><%= v_buyername %></td>
				        </tr>
				        <tr>
				          <td align="left" valign="middle">결제일 : </td>
				          <td align="left" valign="middle"><%= v_pgauthdate %></td>
				        </tr>				
				        <tr>
				          <td align="left" valign="middle">총수강료 : </td>
				          <td align="left" valign="middle"><%= new java.text.DecimalFormat("###,###,##0").format(v_total_biyong) %>원</td>
				        </tr>				
				        <tr>
				          <td align="left" valign="middle">할인액 : </td>
				          <td align="left" valign="middle"><%= new java.text.DecimalFormat("###,###,##0").format(v_discount) %>원</td>
				        </tr>				
				        <tr>
				          <td align="left" valign="middle">실결제액 : </td>
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
          <table width="97%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class=sub_title>결제내역</td>
            </tr>
          </table>
<%
            for(int i = 0; i < list.size(); i++) {

            	DataBox dbox  = (DataBox)list.get(i);
                v_subjnm   = dbox.getString("d_subjnm");
                v_biyong   = dbox.getDouble("d_biyong");
%>

          <table class="box_table_out" cellspacing="1" cellpadding="5">
            <tr>
              <td colspan="2" class="table_top_line"></td>
            </tr>
            <tr>
              <td class="table_title" width="30%"><b>No</b></td>
              <td class="table_02_2" ><%= i + 1 %></td>
            </tr>
            <tr>
              <td class="table_title" ><b>신청과정명</b></td>
              <td class="table_02_2" ><%= v_subjnm %></td>
            </tr>
            <tr>
              <td class="table_title" ><b>수강료</b></td>
              <td class="table_02_2" ><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %>원</td>
            </tr>
          </table>
          <table border=0 cellpadding=0 cellspacing=0><tr><td height="5"></td></tr></table>
<%
            }
%>

<%	if(box.getBoolean("onlyView")){
%>
          <table cellspacing="0" cellpadding="0" >
            <tr><td height="5"></td></tr>
            <tr>
    		  <td width=8></td>
              <td align="left" style="padding-top=10"><a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
            </tr>
            <tr><td height="5"></td></tr>
          </table>
<%	} else {  %>
          <table cellspacing="0" cellpadding="0" >
            <tr><td height="5"></td></tr>
            <tr>
              <td colspan="3" height="25"><b>결제를 취소하시겠습니까?</b></td>
            </tr>
            <tr>
              <td colspan="3" height="25"><b>거래번호 : <%= v_tid %></b>
              <input type=hidden name=tid size=45 value="<%=v_tid%>">
              </td>
            </tr>
            <tr>
              <td colspan="3" height="25"><b>취소사유 : </b><input type=text name="p_reason" size=45 value=""></td>
            </tr>
            <tr>
              <td align="right" style="padding-top=10"><a href="javascript:fnPayCancel()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
    		  <td width=8></td>
              <td align="left" style="padding-top=10"><a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
            </tr>
            <tr><td height="5"></td></tr>
          </table>
<%	} %>
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
