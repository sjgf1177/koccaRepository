<%
//**********************************************************
//  1. 제      목: 커뮤니티 공지사항보기
//  2. 프로그램명 : za_CmuNotice_R.jsp
//  3. 개      요: 커뮤니티 공지사항보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 
//  7. 수      정: 2005.07.01 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_faq_type   = box.getStringDefault("p_faq_type","DIRECT");
    int     v_faqno      = box.getInt("p_faqno");
    String  v_grtype     = box.getString("p_grtype");
    
    int     v_replcnt    = 0;
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    int     v_pageno     = box.getInt("p_pageno");
    int     v_rowseq     = box.getInt("p_rowseq");
     
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    ArrayList list         = (ArrayList)request.getAttribute("list");
    ArrayList viewfaq      = new ArrayList();
    ArrayList viewfaq2     = new ArrayList();
    ArrayList viewfaqreplay= new ArrayList();
    
    if(list.size() != 0){
       viewfaq        = (ArrayList)list.get(0);
       viewfaq2       = (ArrayList)list.get(1);
       viewfaqreplay  = (ArrayList)list.get(2);  

    }
    // 본문글 정보를 담고 있는 DATABOX 
    DataBox dbox = (DataBox)viewfaq.get(0);
    
    // 본문글의 댓글의 갯수
    v_replcnt    = viewfaqreplay.size();
%>  


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script src="/script/cafe_select.js" language="javascript"></script>
<SCRIPT language="javascript">
<!--

//리스트로 이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//글삭제하기
function uf_deleteOK() {
    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "deleteData";
    document.form1.submit();
}


//수정페이지로이동하기
function uf_updateOK() {
    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "updatePage";
    document.form1.submit();
}
//-->
</SCRIPT>
</head>
<body topmargin=0 leftmargin=0 bgcolor="#FFFFFF">
<form name = "form1"     enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_faq_type"    value="<%=v_faq_type%>">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_rplno"       value = "">
<input type = "hidden" name = "p_faqno"       value = "<%=v_faqno%>">
<input type = "hidden" name = "p_lv"          value = "<%=dbox.getInt("d_lv")%>">
<input type = "hidden" name = "p_position"    value = "<%=dbox.getInt("d_position")%>">
<input type = "hidden" name = "p_parent"      value = "<%=dbox.getInt("d_parent")%>">
<input type = "hidden" name = "p_root"        value = "<%=dbox.getInt("d_root")%>">
<input type = "hidden" name = "p_replcnt"     value = "<%=v_replcnt%>">
<input type = "hidden" name = "p_grtype"     value = "<%=v_grtype%>">

<input type = "hidden" name = "p_rowseq"   value = "<%=v_rowseq%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
 <tr>
  <td align="center"> 
   <!----------------- title 시작 ----------------->
   <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
    <tr> 
     <td><img src="../../../images/admin/homepage/tit_comm_notice.gif"></td>
     <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
    </tr>
   </table>
   <br>

   <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
    <tr> 
      <td width="96" class="table_title"  width="10%">작성자</td>
      <td class="table_02_2" width="40%"><%=dbox.getString("d_name")%>&nbsp; <%=dbox.getString("d_jikupnm")%>/<%=dbox.getString("d_jikwinm")%>&nbsp;<%=dbox.getString("d_deptnam")%></td>
      <td class="table_title" width="10%">번호</td>
      <td class="table_02_2" width="40%"><%=dbox.getString("d_faqno")%></td>
    </tr>
    <tr> 
      <td class="table_title">게시일</td>
      <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy/MM/dd")%></td>
      <td class="table_title">조회수</td>
      <td class="table_02_2"><%=dbox.getInt("d_read_cnt")%></td>
    </tr>
    <tr> 
      <td class="table_title" >제목</td>
      <td colspan="3" valign="top" class="table_02_2" ><%=dbox.getString("d_title")%></td>
    </tr>
    <tr> 
      <td class="table_title" >내용</td>
      <td colspan="3" valign="top" class="table_02_2" ><%=dbox.getString("d_content")%></td>
    </tr>
    <tr> 
      <td class="table_title" >첨부파일</td>
      <td colspan="3" valign="top" class="table_02_2" >
        <% 
        Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
        Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
        %>
        <%@ include file="/learn/admin/include/za_MultiAttach_R.jsp" %>
      </td>
    </tr>
  </table>
  <br>
    <table width="970" border="0" cellspacing="0" cellpadding="0">
     <tr align="center"> 
      <td>
       <a href="javascript:uf_updateOK();" onfocus=this.blur()><img src="/images/admin/button/btn_modify.gif" border = "0"></a>
       <a href="javascript:uf_deleteOK();" onfocus=this.blur()><img src="/images/admin/button/btn_del.gif" border = "0"></a>
       <a href="javascript:wf_listOK();" onfocus=this.blur()><img src="/images/admin/button/btn_cancel.gif" border = "0"></a>
       </td>
     </tr>
    </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
