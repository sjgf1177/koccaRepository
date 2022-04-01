<%
//**********************************************************
//  1. 제      목: 과정모의학습-교안리스트
//  2. 프로그램명: za_SubjectStudy_L.jsp
//  3. 개      요: 과정모의학습-교안리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: S.W.Kang
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box  = (RequestBox)request.getAttribute("requestbox");
    String  v_userid    = "";
    String  v_name      = "";
    String  v_indate    = "";
    String  v_title     = "";
    int     v_seq       = 0;
    int     v_levels    = 0;
    int     v_cnt       = 0;
    int     i           = 0;
    int     v_dispnum   = 0;
    int     v_totalpage = 0;
    int     v_rowcount  = 0;
    int     v_upfilecnt = 0;
    int     v_filecnt   = 0;
    int     v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String s_userid     = box.getSession("userid");
    String s_username   = box.getSession("name");
    String v_class1     = "";
    String v_class2     = "";
    
    ArrayList list = (ArrayList)request.getAttribute("selectStudyDocList");

    String  v_subj       = box.getString("p_subj");       //과정&코스
    String  v_subjnm     = box.getString("p_subjnm");
    String  v_comp       = box.getString("p_comp");
    String  ss_action    = box.getString("s_action");   
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.beta.SubjectSimulationServlet";
         document.form1.p_process.value = "list";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.beta.SubjectSimulationServlet";
         document.form1.p_process.value = "list";
         document.form1.submit();
    }
    function listPage() {
        document.form1.action = "/servlet/controller.beta.SubjectSimulationServlet";
        document.form1.p_process.value = "listPage";
        document.form1.p_pageno.value = "1";
        document.form1.submit();
    }
    function insertPage() {
        document.form1.action = "/servlet/controller.beta.SubjectSimulationServlet";
        document.form1.p_process.value = "docInsertPage";
        document.form1.submit();
    }
    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.beta.SubjectSimulationServlet";
        document.form1.p_process.value = "updateDocPage";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
   <form name="form1" method="post" action="">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/b_title05.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	       <!----------------- title 끝 ----------------->
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
		    <td width="1%"><img src="/images/admin/common/icon.gif" ></td> 
            <td class=sub_title>과정명 : <%=v_subjnm%></td>
			<td width="1%"><img src="/images/admin/common/icon.gif" ></td> 
            <td class=sub_title>콘텐츠회사 : <%=v_comp%></td>            
          </tr>
        </table>
        <br>
        <!----------------- 리스트, 추가 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>                       
            <td align="right" width="92%">
                <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
                    <a href = "javascript:insertPage();"><img src="/images/admin/button/btn_add.gif" border="0"></a>
                <%  }   %>            
            </td>
			
            <td align="right" width="8%">
                <a href = "javascript:listPage();"><img src="/images/admin/button/btn_list.gif" border="0"></a>
            </td>
          </tr>
          <tr> 
            <td height="3"></td>
			<td height="3"></td>
			<td width="0%" height="3"></td>
          </tr>
        </table>
        <!----------------- 리스트, 추가 버튼 끝 ----------------->
        <!----------------- 과정 자료실  리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" height="25" class="table_title"><b>NO</b></td>
            <td width="50%" class="table_title"><b>제목</b></td>
            <td width="15%" class="table_title"><b>등록일자</b></td>
            <td width="15%" class="table_title"><b>조회수</b></td>
            <td width="15%" class="table_title"><b>첨부파일</b></td>
          </tr>
<%
    for(i = 0; i < list.size(); i++) {

		DataBox dbox = (DataBox)list.get(i);
		v_seq       = dbox.getInt("d_seq");
		v_subj      = dbox.getString("d_subj");
		v_userid    = dbox.getString("d_userid");
		v_indate    = dbox.getString("d_indate");
		v_title     = dbox.getString("d_title");
		v_cnt       = dbox.getInt("d_cnt");
		v_filecnt   = dbox.getInt("d_filecnt");
		v_dispnum   = dbox.getInt("d_dispnum");
		v_totalpage = dbox.getInt("d_totalpage");
		v_rowcount  = dbox.getInt("d_rowcount");

%>          
          <tr> 
            <td class="table_01" height="25"><%=v_dispnum%></td>
            <td class="table_02_1"><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a>
              <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){ %>
                 &nbsp;&nbsp;<img src = "/images/user/homepage/new_icon1.gif" align = "absmiddle" border = "0">
              <%  } %>            
            </td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
            <td class="table_02_1"><%= v_cnt %><!--a href = "mailto:<% //=fbean.getSession("email")%>"></a--></td>
            <td class="table_02_1"><%=v_filecnt%></td>
          </tr>
<%  } if(i == 0){   %>    
                    <tr> 
                      <td class="table_02_1" colspan="5">등록된 내용이 없습니다.</td>
                    </tr>    
<%    } %>
        </table>
        <!----------------- 과정 자료실  리스트 끝 ----------------->
        <br>





        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center" class="ms"><%= PageUtil.printPageList(v_totalpage, v_pageno, row) %></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
        </table>

        <br>

      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
    <input type= "hidden" name="p_process">
    <input type= "hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
	<input type= "hidden" name="p_subj"      value="<%=v_subj%>"> <!--in relation to select-->
    <input type= "hidden" name="p_subjnm"    value="<%=v_subjnm %>">
    <input type  "hidden" name="p_comp"      value="<%=v_comp%>">
    <input type= "hidden" name="p_pageno"    value="<%=v_pageno %>">
    <input type= "hidden" name="p_search"    value="<%=v_search%>">
    <input type= "hidden" name="p_seq"       value="">
    <input type= "hidden" name="p_upfilecnt" value="">
    <input type= "hidden" name="p_userid"    value="">

</form>
</body>
</html>
