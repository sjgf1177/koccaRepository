<%
//**********************************************************
//  1. 제      목: 모사답안 view
//  2. 프로그램명: za_ProjectCopyViewOpen_L.jsp
//  3. 개      요: 모사답안 view
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 6. 30
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");    
    String  v_subj      = box.getString("p_subj");
    String  v_subjnm    = box.getString("p_subjnm");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_copycnt   = box.getString("p_copycnt");
    
    int     v_ordseq        = 0;
    String  v_projid        = "";            
    String  v_title         = "";
    String  v_contents      = "";    
    String  v_upfile        = "";
    String  v_realfile      = "";
    String  v_upfilesize    = "";    
    String  v_contentsbyte  = "";        
    String  v_indate        = "";
    String  v_retdate   = "";
	String  v_isret	    = "";
	String  v_retreason = "";     
	String  v_retuserid = "";
	    
    ArrayList list      = (ArrayList)request.getAttribute("projectCopyView");  

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function projectJudge() {
        if (confirm("반려처리를 하시겠습니까?")) {          
            document.form1.action='/servlet/controller.study.ProjectAdminServlet';
            document.form1.p_process.value = 'ProjectJudgeAtOpenWinCopy';
            document.form1.submit();     
        }
    }
-->
</SCRIPT>    
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process"   value="<%=v_process%>">      
    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">
    <input type="hidden" name="p_year"      value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">           
    <input type="hidden" name="p_copycnt"   value="<%=v_copycnt%>"><!--비교대상갯수-->            
  <table width="630" border="0" cellpadding="0" cellspacing="10" class="table_02_1">
    <tr> 
    <td> 
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr> 
            <td align="center" bgcolor="#FFFFFF"> 
              <br>
              <table width="100%" class="table_out" border=0  cellspacing="1" cellpadding="5">
                <tr> 
                  <td height="30" class="table_01"><b>모사답안 비교</b></td>
                </tr>              
              </table><br>
          <% 
            for(int i = 0; i < list.size(); i++) {
                ProjectData data= (ProjectData)list.get(i);
				v_ordseq       = data.getOrdseq();
                v_projid       = data.getProjgrp();                 
                v_title        = data.getTitle();
                v_contents     = data.getContents();
                v_upfile       = data.getUpfile();
                v_realfile     = data.getRealfile();
                v_upfilesize   = data.getUpfilesize();
				v_contentsbyte = data.getContentsbyte();
				v_retdate      = data.getRetdate();
                v_retdate      = FormatDate.getFormatDate(v_retdate,"yyyy/MM/dd");								
            	v_isret        = data.getIsret();
            	v_retreason    = data.getRetreason();
                v_retuserid    = data.getRetuserid();

           %>
           
              <!----------------- project 상세정보 시작 ----------------->
              <input type='hidden' name='p_ordseq<%=i%>' value='<%=v_ordseq%>'>
              <input type='hidden' name='p_projid<%=i%>' value='<%=v_projid%>'>
              <table width="100%" class="table_out" border=0  cellspacing="1" cellpadding="5">
                <tr> 
                  <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr> 
                  <td width="20%" height="25" class="table_title"><font color='red'>[<%=i+1%>]</font> <b>제목</b></td>
                  <td class="table_02_2"><%=v_title%></td>
                </tr>
                <tr> 
                  <td class="table_title" height="25"><b>내용</b></td>
                  <td class="table_02_2">
                  
                  <%= StringManager.replace(v_contents,"\r\n","<br>") %>
                  
                  <p align=right>(바이트수: <b><%=v_contentsbyte%></b> Byte)</p>
                  </td>
                </tr>
                <tr> 
                  <td class="table_title" height="25"><b>첨부파일</b></td>
                  <td class="table_02_2">
                  <% if(v_upfile.length() > 0){ %> 
                  <a href = '/servlet/controller.library.DownloadServlet?p_year=<%=v_year%>&p_subj=<%=v_subj%>&p_savefile=<%= v_upfile %>&p_realfile=<%= v_upfile %>'><%=v_realfile%></a> 
                   (사이즈: <b> <%=v_upfilesize%></b>  Byte)
                  <% } %> </td>
                </tr>
                <tr> 
                  <td rowspan="2" class="table_title"><b>반려사유</b></td>
                  <td height="25" class="table_02_2"> <input type="checkbox" name="p_isret<%=i%>" value="Y" <%if (v_isret.equals("Y")) out.println("checked");%>>
                    반려하기 
                    <%if (v_isret.equals("Y")) {%> <font color="#999999">[반려자 : <b><%=v_retuserid%></b> , 반려일자 : <b><%=v_retdate%></b>]</font>  <%}%>
                  <br>
                  ※과제물이 모사답안인 경우 반려하기를 누르면 자동 '0'점 처리됩니다.
                  <textarea name="p_retreason<%=i%>" cols="80" rows=5"><%=v_retreason%></textarea> 
                  </td>
                </tr>                
              </table><br>
        <% } %>              
          </td>
        </tr>
      </table><br>
              <table border="0" cellspacing="0" cellpadding="0">
			  <tr>
                  <td>&nbsp;</td>
                  <td></td>
                  <td>&nbsp;</td>
                </tr>
                <tr> 
                  <td> <a href="javascript:projectJudge()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
                  <td width=8></td>
                  <td> <a href="javascript:javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
                </tr>
              </table>    
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>    
</table>
</form>
</body>
</html>
