<%
//**********************************************************
//  1. 제      목: COMMUNITY PERMIT SELECT
//  2. 프로그램명: za_CommunityPermit_R.jsp
//  3. 개      요: 커뮤니티 승인 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.02.17
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    
    String  v_process   = box.getString("p_process");    
    String  v_search    = box.getString("p_search");    //검색어(커뮤니티명)
    String  v_select    = box.getString("p_select");    //검색항목(카테고리 아이디)
    String  v_commId    = box.getString("p_commId");    //동호회 아이디
      
    int     v_userCnt   =   0;                          //동호회 인원수
    String  v_sdesc     =   "";                         //동호회명
    String  v_categoryId=   "";                         //카테고리 아이디
    String  v_master    =   "";                         //동호회 시샵
    String  v_requestDate=  "";                         //신청일
    String  v_intros    =   "";                         //동호회 소개
    String  v_topics    =   "";                         //동호회 내용
    String  v_permittedStatus="";                       //승인여부
    String  v_permittedDate="";                         //승인일
    String  v_rejectedDate=  "";                        //폐쇄일
    String  v_rejectedReason="";                        //폐쇄사유     
    String  v_category_value    =   "";                 //카테고리 value
    String  v_permit_value      =   "";                 //승인여부 value
    String  v_data_value        =   "";                 //날짜 value  
    //DEFINED class&variable END

    DataBox dbox  = (DataBox)request.getAttribute("permitSelect");    
    v_userCnt           = dbox.getInt	("d_usercnt");  
    v_sdesc             = dbox.getString("d_sdesc");  
    v_categoryId        = dbox.getString("d_categoryid");
    v_master            = dbox.getString("d_master");
    v_requestDate       = dbox.getString("d_requestdate");
    v_intros            = dbox.getString("d_intros");
    v_topics            = dbox.getString("d_topics");
    v_permittedStatus   = dbox.getString("d_permittedstatus");
    v_permittedDate     = dbox.getString("d_permitteddate");
    v_rejectedDate      = dbox.getString("d_rejecteddate");   
    v_rejectedReason    = dbox.getString("d_rejectedreason");
    v_category_value    = CodeConfigBean.getCodeName("communityCategory",v_categoryId);
    v_requestDate       = FormatDate.getFormatDate(v_requestDate,"yyyy/MM/dd");
    if(v_permittedStatus.equals("01")){
        v_permit_value  = "승인일";
        v_data_value    = FormatDate.getFormatDate(v_permittedDate,"yyyy/MM/dd");
    }else if(v_permittedStatus.equals("02")){
        v_permit_value  = "폐쇄일";   
        v_data_value    = FormatDate.getFormatDate(v_rejectedDate,"yyyy/MM/dd");
    }else if(v_permittedStatus.equals("03")){
        v_permit_value  = "처리중"; 
        v_data_value    = "없음";
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript" type="text/JavaScript">
	   function permit() {
                document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
                document.form1.p_process.value = "permitHandling";      
                document.form1.submit();	
            }                        
	   function cancel() {
                document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
                document.form1.p_process.value = "permitList";      
                document.form1.submit();	
            }                        
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">  
    <input type = "hidden" name = "p_commId" value = "<%= v_commId %>">  
    <input type = "hidden" name = "p_search" value = "<%= v_search %>">   
    <input type = "hidden" name = "p_select" value = "<%= v_select %>">       
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/community/tit_comm_04.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
      <br>
        <br>
        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>학습동호회명</strong></td>
            <td width="30%" class="table_02_1"><%=v_sdesc%></td>
            <td width="15%" class="table_title"><b>카테고리</b> : <%=v_category_value%></td>
            <td width="30%" class="table_title"><b>시삽</b> : <%=v_master%></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>회원수</strong></td>
            <td width="30%" class="table_02_1"><%=v_userCnt%>명</td>
            <td width="15%" class="table_title"><strong>신청일</strong></td>
            <td width="30%" class="table_02_1"> <p><%=v_requestDate%></p></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>학습동호회소개</strong></td>
            <td height="25" colspan="3" class="table_02_2" style="padding-top=10;padding-right=10;padding-bottom=10;padding-left=10">
                <%= StringManager.replace(v_intros,"\r\n","<br>") %>
            </td>
          </tr>      
          <tr>
            <td width="15%" class="table_title"><strong>학습동호회내용</strong></td>
            <td height="25" colspan="3" class="table_02_2" style="padding-top=10;padding-right=10;padding-bottom=10;padding-left=10">
                <%= StringManager.replace(v_topics,"\r\n","<br>") %>
            </td>    
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>승인여부</strong></td>
            <td width="30%" class="table_02_1">
                <select name="p_permittedStatus">
                    <option value="01" <% if(v_permittedStatus.equals("01")){ %> selected <%}%>>승인</option>
                    <option value="02" <% if(v_permittedStatus.equals("02")){ %> selected <%}%>>폐쇄</option>
                </select>
            </td>
            <td width="15%" class="table_title"><strong><%=v_permit_value%></strong></td>
            <td width="30%" class="table_02_1"><%=v_data_value%></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>거부사유</strong></td>
            <td colspan="3" class="table_02_2">
                <input name="p_rejectedReason" type="text" class="input" size="80" value="<%=v_rejectedReason%>"> 
            </td>
          </tr>
        </table>
        <!----------------- 과정기준조회 끝 ----------------->
        <!----------------- total 시작 ----------------->
        <br>
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="right" class="ms"><a href="javascript:permit();"><img src="/images/admin/button/btn_checkok.gif"  border="0"></a></td>
            <td width="1%" align="center" class="ms">&nbsp;</td>
            <td align="left" class="ms"><a href="javascript:cancel();"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
          </tr>
        </table>
      <!----------------- total 끝 ----------------->     
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>         
</table>
</form>
</body>
</html>
