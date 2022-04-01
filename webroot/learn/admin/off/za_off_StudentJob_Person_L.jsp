<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_process   = box.getString("p_process");
    int     v_pageno    = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
    
    //DEFINED in relation to select START
    String  ss_year         = box.getString("s_year");                      //연도
    String  ss_subj         = box.getString("s_subjcode");                  //과정
    String  ss_grseq        = box.getString("s_grseq");                     //차수
    String  ss_upperclass   = box.getStringDefault("s_upperclass", "ALL");  //과정대분류
    String  ss_middleclass  = box.getStringDefault("s_middleclass", "ALL"); //과정중분류
    String  ss_lowerclass   = box.getStringDefault("s_lowerclass", "ALL");  //과정소분류
    String  ss_action       = box.getString("s_action");
    String  s_subjsearchkey = box.getString("s_subjsearchkey");
    String  v_gyear         = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
    String  v_searchtext    = box.getString("p_searchtext");
    //DEFINED in relation to select END

    int    v_seq            = 0;
   	String v_comp           = "";
   	String v_compnm         = "";
   	String v_employgubun    = "";
   	String v_employgubunnm  = "";
   	String v_employdate     = "";
   	String v_subj           = "";
   	String v_year           = "";
   	String v_subjseq        = "";
   	String v_userid         = "";
   	
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_totalrowcount =  0;


    ArrayList list = (ArrayList)request.getAttribute("selectListPerPerson");
    
    DataBox subjBox = (DataBox)request.getAttribute("selectView");
    
    String v_subjnm     = "";
    String v_name       = "";
    String v_studentno  = "";
    String v_state      = "";
    String v_statenm    = "";
    String v_depart		= "";
    
    if(subjBox !=null){
    	v_subj            = subjBox.getString("d_subj");
        v_subjnm          = subjBox.getString("d_subjnm");
        v_year            = subjBox.getString("d_year");
        v_subjseq         = subjBox.getString("d_subjseq");
        v_name            = subjBox.getString("d_name");
        v_studentno       = subjBox.getString("d_studentno");
        v_state           = subjBox.getString("d_stustatus");
        v_statenm         = subjBox.getString("d_statenm");
        v_userid          = subjBox.getString("d_userid");
    }
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
         document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
         document.form1.p_process.value = "selectListPerPerson";
         document.form1.submit();
    }

    function goPage(pageNum) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
         document.form1.p_process.value = "selectListPerPerson";
         document.form1.submit();
    }

    function insertPage(){
    	 document.form1.target = "_self";
         document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
         document.form1.p_process.value = "insertPage";
         document.form1.submit();
    }

    function updatePage(seq){
        document.form1.p_seq.value = seq;
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.off.OffStudentJobAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
   }

//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process"/>
    <input type="hidden" name="p_pageno"  value = "<%=v_pageno%>">
	<input type="hidden" name="p_subj"    value="<%=v_subj %>">
	<input type="hidden" name="p_subjnm"  value="">
	<input type="hidden" name="p_year"    value="<%=v_year %>">
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq %>">
	<input type="hidden" name="p_userid"  value="<%=v_userid %>">
	<input type="hidden" name="p_seq"     >
	
	<input type = "hidden" name="s_year"          value="<%=ss_year        %>"/>
    <input type = "hidden" name="s_subj"          value="<%=ss_subj        %>"/>
    <input type = "hidden" name="s_grseq"         value="<%=ss_grseq       %>"/>
    <input type = "hidden" name="s_upperclass"    value="<%=ss_upperclass  %>"/>
    <input type = "hidden" name="s_middleclass"   value="<%=ss_middleclass %>"/>
    <input type = "hidden" name="s_lowerclass"    value="<%=ss_lowerclass  %>"/>
    <input type = "hidden" name="s_action"        value="<%=ss_action      %>"/>
    <input type = "hidden" name="s_subjsearchkey" value="<%=s_subjsearchkey%>"/>
    <input type = "hidden" name="p_gyear"         value="<%=v_gyear        %>"/>
    <input type = "hidden" name="p_searchtext"    value="<%=v_searchtext   %>"/>

<table width="522" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td><table width="520" height="337" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" valign='top' bgcolor="#FFFFFF">
          <!----------------- title 시작 ----------------->
          <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
            <tr>
              <td><img src="/images/admin/portal/s.1_off09.gif" ></td>
              <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
          </table>
          
          <br/>
          <table width="515" cellspacing="1" cellpadding="0" class="table_out">
              <tr>
                <td colspan="4" class="table_top_line"></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >과정명</td>
                <td class="table_02_2"  width="80%" colspan=3><%=v_subjnm %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >성명</td>
                <td class="table_02_2"  width="30%" ><%=v_name %></td>
                <td class="table_title" width="20%" >학번</td>
                <td class="table_02_2"  width="30%" ><%=v_studentno %></td>
              </tr>
              <tr>
                <td class="table_title" width="20%" >차수</td>
                <td class="table_02_2"  width="30%" ><%=Integer.parseInt(v_subjseq) %>기</td>
                <td class="table_title" width="20%" >학적상태</td>
                <td class="table_02_2"  width="30%" ><%=v_statenm%></td>
              </tr>
            </table>
          <%//@ include file="/learn/admin/off/za_off_StudentJob_U.jsp" %>
          
          <br>
            <table width="515" class="table_out" cellspacing="1" cellpadding="0">
                <tr>
                  <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                  <td width="5%"  class="table_title">NO</td>
                  <td width="65%" class="table_title"><b>회사명</b></td>
                  <td width="15%" class="table_title"><b>부서명</b></td>
                  <td width="15%" class="table_title"><b>취업일</b></td>
                </tr>
              <!--회원구분 ID 성명 -->
            <%
            if(list != null && list.size()>0) {
	            for(int i = 0; i < list.size(); i++) {
	
	                DataBox dbox     = (DataBox)list.get(i);
	                v_seq            = dbox.getInt   ("d_seq");
	                v_comp           = dbox.getString("d_comp");
	                v_compnm         = dbox.getString("d_compnm");
	                v_employgubun    = dbox.getString("d_employgubun");
	                v_employgubunnm  = dbox.getString("d_employgubunnm");
	                v_employdate     = dbox.getString("d_employdate");
	                v_subj           = dbox.getString("d_subj");
	                v_year           = dbox.getString("d_year");
	                v_subjseq        = dbox.getString("d_subjseq");
	                v_userid         = dbox.getString("d_userid");
	                
	                v_dispnum        = dbox.getInt("d_dispnum");
	                v_totalpage      = dbox.getInt("d_totalpage");
	                v_totalrowcount  = dbox.getInt("d_totalrowcount");
	                v_depart         = dbox.getString("d_depart");
                %>
                <tr>
                  <td class="table_01"><%=v_dispnum %></td>
                  <td class="table_02_1"><a href="javascript:updatePage(<%=v_seq %>);"><%=v_compnm%></a></td>
                  <td class="table_02_1"><%=v_depart%></td>
                  <td class="table_02_1"><%=FormatDate.getFormatDate(v_employdate,"yyyy/MM/dd")%></td>
                </tr>
                <%
                }
            }else{
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
                <td align="right" style="padding-top=10">
                <% if (list.size()== 0) { %>
                <a href='javascript:insertPage()'><img src="/images/admin/button/btn_add.gif" border=0></a>
                <% } %>
                <a href='javascript:self.close()'><img src="/images/admin/button/btn_close.gif" border=0></a>
                </td>
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
