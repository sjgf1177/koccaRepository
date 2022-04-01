<%
//**********************************************************
//  1. 제      목: 수강생 검색
//  2. 프로그램명: za_searchStudent.jsp
//  3. 개      요: 수강생 검색
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.23
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

    String v_userid = "";
    String v_name        = "";  //이름
    String v_studentno   = "";  //이메일
    String v_confirmdate = "";
    String v_codenm      = "";  //학적상태

    ArrayList list = (ArrayList)request.getAttribute("searchStudent");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<script type="text/javascript" src="/script/portal/common.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function selectStudent(userid,name,studentno) {

		opener.document.all.div_billstudent.style.display = "block";

		if (!opener.document.getElementById("spn_"+userid)) {
			opener.document.all.spn_addstudent.innerHTML += "<span id=\"spn_" + userid + "\"/>";
			opener.document.all.spn_addstudent_hidden.innerHTML += "<span id=\"spn_" + userid + "_hidden\"/>";
		}

		if (!opener.document.getElementById("tb_"+userid)) {

			var sAddTable = "";
			sAddTable = "<table id=\"tb_" + userid + "\" width=\"100%\">"
			          + "  <tr><td width=\"25%\">" + userid + "</td>"
			          + "      <td width=\"25%\">" + name + "</td>"
			          + "      <td width=\"30%\">" + studentno + "</td>"
			          + "      <td width=\"20%\">"
			          + "        <a href=\"javascript:spanclear('spn_" + userid + "','spn_" + userid + "_hidden')\">[삭제]</a>"
			          + "      </td></tr></table>";
			
			opener.document.getElementById("spn_"+userid).innerHTML += sAddTable;

			var sAddTableHidden = "";
			sAddTableHidden = "<input type=\"hidden\" name=\"p_billstudent\" value=\"" + userid + "\">";

			opener.document.getElementById("spn_"+userid+"_hidden").innerHTML += sAddTableHidden;
			
		} else {
			alert("이미 추가된 수강생입니다!");
			return;
		}

        //self.close();
    }
//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" onsubmit="javascript:search();">

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
            <table width="515" class="table_out" cellspacing="1" cellpadding="0" id="tb_billstudent">
                <tr>
                  <td colspan="7" class="table_top_line"></td>
                </tr>
                <tr>
                  <td class="table_title"><b>선택</b></td>
                  <td class="table_title"><b>번호</b></td>
                  <td class="table_title"><b>ID</b></td>
                  <td class="table_title"><b>성명</b></td>
                  <td class="table_title"><b>학번</b></td>
                  <td class="table_title"><b>입학일자</b></td>
                  <td class="table_title"><b>상태</b></td>

                </tr>
              <!--회원구분 ID 성명 -->
<%
            for(int i = 0; i < list.size(); i++) {

                DataBox dbox  = (DataBox)list.get(i);
                
                v_userid      = dbox.getString("d_userid");
                v_name        = dbox.getString("d_name");
                v_studentno   = dbox.getString("d_studentno");
                v_confirmdate = dbox.getString("d_confirmdate");
                v_codenm      = dbox.getString("d_codenm");

%>
                <tr>
                  <td class="table_01"> <input type="radio" name="no" value="<%=v_userid%>" onClick="javascipt:selectStudent('<%=v_userid%>','<%=v_name%>','<%=v_studentno %>')">
                  </td>
                  <td class="table_02_1"><%=i+1%></td>
                  <td class="table_02_1"><%=v_userid%></td>
                  <td class="table_02_1"><%=v_name%></td>
                  <td class="table_02_1"><%=v_studentno%></td>
                  <td class="table_02_1"><%=FormatDate.getFormatDate(v_confirmdate,"yyyy/MM/dd")%></td>
                  <td class="table_02_1"><%=v_codenm%></td>
                </tr>
                <%
        }
        if(list.size() == 0){
%>
                <tr>
                  <td class="table_02_1" colspan="7">검색된 내용이 없습니다.</td>
                </tr>
                <% } %>
              </table>

            <table width="496" border="0" cellspacing="0" cellpadding="0">
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
