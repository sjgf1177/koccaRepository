<%
//**********************************************************
//  1. 제      목: COMMUNITY PERMIT LIST
//  2. 프로그램명: za_CommunityPermit_L.jsp
//  3. 개      요: 커뮤니티 승인 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: mscho 2003. 7.  7
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");    
    String  v_search    = box.getString("p_search");    //검색어(커뮤니티명)
    String  v_select    = box.getString("p_select");    //검색항목(카테고리 아이디)
    int     v_commId    =   0;                          //동호회 아이디
    int     v_userCnt   =   0;                          //동호회 인원수
    String  v_sdesc     =   "";                         //동호회명
    String  v_categoryId=   "";                         //카테고리 아이디
    String  v_intros    =   "";                         //동호회 소개
    String  v_isPublic  =   "";                         //공개여부
    String  v_permittedStatus   =   "";                 //승인여부
    String  v_public_value      =   "";                 //공개여부 value
    String  v_category_value    =   "";                 //카테고리 value
    String  v_permit_value      =   "";                 //승인여부 value
    int     i           =   0;
    //DEFINED class&variable END

    ArrayList list = (ArrayList)request.getAttribute("permitList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript">
        function search() {
             document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
             document.form1.p_process.value = "permitList";
             document.form1.submit();
        }              
        function permit_select(commu_id) {
        	document.form1.action = "/servlet/controller.community.CommunityAdminServlet";
        	document.form1.p_commId.value = commu_id;
        	document.form1.p_process.value= "permitSelect";
        	document.form1.submit();
        }                     
    </script>     
</head>
<body bgcolor="#FFFFFF" leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process"   value = "<%= v_process %>">  
    <input type = "hidden" name = "p_commId">  
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/community/tit_comm_01.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->

      <br>

      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="3">
        <tr>
          <td> 

              <table class="form_table_bg" cellspacing="0" cellpadding="0">
                <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                  <td align="center" valign="absmiddle">커뮤니티 검색 : 
                    <input name="p_search" type="text" class="input" value="<%=v_search %>">
                    &nbsp;&nbsp; 
                    <%=CodeConfigBean.getCodeSelect("communityCategory","p_select",v_select,1)%>                          
                    &nbsp;&nbsp;<a href="javascript:search()"><img src="../../../images/admin/button/search3_butt.gif" align="absmiddle" border="0"></a></td>
              </tr>
                <tr> 
                <td height="7"></td>
              </tr>
            </table>

            </td>
        </tr>
      </table>
      <!----------------- form 시작 ----------------->

      <br>
      <br>
        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%"  class="table_title"><b>NO</b></td>
            <td width="25%" class="table_title"><b>학습동호회명</b></td>
            <td width="35%" class="table_title"><b>소개</b></td>
            <td width="10%" class="table_title"><b>인원수</b></td>
            <td width="10%" class="table_title"><b>공개여부</b></td>
            <td width="10%" class="table_title"><b>카테고리</b></td>
            <td width="5%"  class="table_title"><b>구분</b></td>
          </tr>
        <%                                                                                                                                             
            for(i = 0; i < list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);
               
                v_commId            = dbox.getInt	("d_commid");
                v_sdesc             = dbox.getString("d_sdesc");
                v_categoryId        = dbox.getString("d_categoryid");                
                v_intros            = dbox.getString("d_intros");
                v_isPublic          = dbox.getString("d_ispublic");
                v_userCnt           = dbox.getInt	("d_usercnt");
                v_permittedStatus   = dbox.getString("d_permittedstatus");

                if(v_isPublic.equals("Y")) {    v_public_value  = "공개";   }
                else                       {    v_public_value  = "비공개"; }
                System.out.println("v_categoryId===========>"+v_categoryId);
                v_category_value    =   CodeAdminBean.getCodeName(CodeConfigBean.getConfigValue("communityCategory"),v_categoryId);  
                if(v_permittedStatus.equals("01"))      {v_permit_value="승인";   }
                else if(v_permittedStatus.equals("02")) {v_permit_value="폐쇄";   }
                else if(v_permittedStatus.equals("03")) {v_permit_value="처리중";   }
                
                v_sdesc    =   StringManager.formatTitle(v_sdesc,15);
                v_intros   =   StringManager.formatTitle(v_intros,30);
        %>          
          <tr> 
            <td class="table_01"><%= list.size() - i %></td>
            <td class="table_02_1">
                <a href = "javascript:permit_select('<%=v_commId%>')"><%=v_sdesc%></a></td>
            <td class="table_02_2">&nbsp;<%=v_intros%></td>
            <td class="table_02_1"><%=v_userCnt%></td>
            <td class="table_02_1"><%=v_public_value%></td>
            <td class="table_02_1"><%=v_category_value%></td>
            <td class="table_02_1"><%=v_permit_value%></td>
          </tr>
          <%}%>
        <% if(i == 0){ %>
          <tr> 
            <td height="50" class="table_01" colspan="8">등록된 내용이 없습니다</td>
          </tr>
          <% } %>               
        </table>
        <!----------------- 과정기준조회 끝 ----------------->   
    </td>
  </tr> 
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>       
</table>
</form>
</body>
</html>
