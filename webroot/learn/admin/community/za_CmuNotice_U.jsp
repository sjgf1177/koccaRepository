<%
//**********************************************************
//  1. 제      목: 커뮤니티 공지사항수정
//  2. 프로그램명 : za_CmuNotice_U.jsp
//  3. 개      요: 커뮤니티 공지사항수정.
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
<%@ page import = " com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_faq_type   = box.getString("p_faq_type");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");


    int     v_faqno      = box.getInt("p_faqno");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    int     v_pageno     = box.getInt("p_pageno");
    String  v_grtype     = box.getString("p_grtype");


    String  content      = "";

    String  width = "630";
    String  height = "200";

    ArrayList list           = (ArrayList)request.getAttribute("list");
    ArrayList viewfaq      = new ArrayList();
    ArrayList viewfaq2     = new ArrayList();
    ArrayList viewfaqreplay= new ArrayList();
    
    if(list.size() != 0){
        viewfaq        = (ArrayList)list.get(0);
        viewfaq2       = (ArrayList)list.get(1);
        viewfaqreplay  = (ArrayList)list.get(2); 

    }
 
    DataBox dbox = (DataBox)viewfaq.get(0);
    content=dbox.getString("d_content");
    
 // 웹에디터 템플릿 설정
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

%>  


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script src="/script/cresys_lib.js" language="javascript"></script>
<SCRIPT language="javascript">
<!--

function uf_updateOK() {
     if(blankCheck(document.form1.p_title.value)){
         alert("제목을 입력하세요");
         document.form1.p_title.focus();
         return;
     }
         
     if (document.form1.p_title.value == "") {
         alert("제목을 입력하세요");
         document.form1.p_title.focus();
         return;
     }
     if (realsize(document.form1.p_title.value) > 200) {
         alert("제목은 한글기준 100자를 초과하지 못합니다.");
         document.form1.p_title.focus();
         return;
     }
    document.form1.content.value = document.form1.Wec.MIMEValue;
    document.form1.content.value.replace("&","&amp;");
     
     if (document.form1.Wec.TextValue.length < 3) {
         alert("내용을 입력하세요");
         return;
     }

    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "updateData";
    document.form1.submit();
}
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}


//파일삭제
function uf_deleteFileOK(fileno) {
    document.form1.p_delfileno.value = fileno;
    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "deleteFileData";
    document.form1.submit();
}
    
//-->
</SCRIPT>
</head>
<body topmargin=0 leftmargin=0 bgcolor="#FFFFFF">
<form name = "form1"     enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process"       value="">
<input type = "hidden" name = "p_faq_type"      value="<%=v_faq_type%>">
<input type = "hidden" name = "p_pageno"        value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"    value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"        value = "<%=v_select%>">
<input type = "hidden" name = "p_faqno"         value = "<%=v_faqno%>">
<input type = "hidden" name = "p_fileno"        value = "">
<input type = "hidden" name = "p_lv"            value = "<%=dbox.getString("d_lv")%>">
<input type = "hidden" name = "p_position"      value = "<%=dbox.getString("d_position")%>">
<input type = "hidden" name = "p_delfileno"     value = "">
<input type = "hidden" name = "content"         value = "<%=StringUtil.convertHtmlchars(content)%>">
<input type = "hidden" name = "p_grtype"     value = "<%=v_grtype%>">

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
     <td class="table_title" width="18%">작성자</td>
     <td class="table_02_2"  width="82%"><%=dbox.getString("d_name")%></td>
    </tr>
    
    <tr> 
     <td class="table_title" width="18%">제목</td>
     <td class="table_02_2"  width="82%"><input name="p_title" type="text" class="input" size="129" value="<%=dbox.getString("d_title")%>"></td>
    </tr>

     <tr>
      <td class="table_title">내용</td>
      <td class="table_02_2" style=padding-top:5;padding-bottom:5>
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted('content');</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
      </td>
     </tr>
     <tr> 
     	<td class="table_title" width="18%">파일첨부</td>
     	<td class="table_02_2"  width="82%"><div id="hdiv_MainBg"> 
                  <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
                  
                  int    i_fileLimit         = CommunityAdminDirectBean.getFILE_LIMIT();              // 제한 첨부 파일수
                   
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach.jsp" %> 
        </div></td>
    	</tr>
    </table>
    <br>
    <table width="970" border="0" cellspacing="0" cellpadding="0">
     <tr align="center"> 
      <td><a href="javascript:uf_updateOK();" onfocus=this.blur()><img src="/images/admin/button/btn_save.gif" border = "0">
					<a href="javascript:wf_listOK();" onfocus=this.blur()><img src="/images/admin/button/btn_cancel.gif" border = "0"></td>
     </tr>
    </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
