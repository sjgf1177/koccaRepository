<%
//**********************************************************
//  1. 제      목: PROJECT Grouping
//  2. 프로그램명: za_ProjectGroup_U.jsp
//  3. 개      요: 리포트 그룹핑
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 29
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
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
    String  v_ordseq    = box.getString("p_ordseq");
    String  v_lesson     = box.getString("p_lesson");
    String  v_selprojgrp= box.getString("p_projgrp");
    String  v_projgrp   = "";
    String  v_projname  = "";
    String  v_chief     = "";    
    String  v_userid    = "";
    String  v_cono      = "";
    String  v_name      = "";
    String  v_jikwinm   = "";
    String  v_companynm = "";
    String  v_gpmnm     = "";
    String  v_deptnm    = "";
    int     v_groupcnt  = box.getInt("p_groupcnt");         //그룹갯수      
    int     i           =  0;                        
    ArrayList list      = null;    
	//DEFINED class&variable END
	
    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_uclass    = box.getString("s_uclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_action    = box.getString("s_action");
    //DEFINED in relation to select END

    list = (ArrayList)request.getAttribute("projectGroupHandlingPage"); 
    if(v_selprojgrp.length() == 0){ v_selprojgrp = "1";    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css"> 
<script language = "javascript" src = "/script/cresys_lib.js"></script> 
    <script	language="JavaScript" type="text/JavaScript">
    <!--	   
    function projectList() {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = "ProjectQuestionsList";
        document.form1.submit();
    }        
    function projectGrouping() {    
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectGroupHandling";
        document.form1.submit();
    }
    function projgrp_select(){
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectGroupHandlingPage";
        document.form1.submit();
    }
   -->
    </script>        
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
   <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_uclass"    value="<%=ss_uclass%>">     <!--in relation to select-->
    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->    
    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">
    <input type="hidden" name="p_year"      value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">
    <input type="hidden" name="p_ordseq"    value="<%=v_ordseq%>">
    <input type="hidden" name="p_lesson"     value="<%=v_lesson%>">
    <input type="hidden" name="p_groupcnt"  value="<%=v_groupcnt%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>   
          <td background="/images/admin/community/title_bg.gif"><img src="/images/admin/study/study_title21.gif" width="259" height="36" border="0"></td>
        </tr>
      </table>        
      <!----------------- title 끝 ----------------->
      <br>
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">
          <%=v_subjnm%> 과정 &gt; <%=v_subjseq%>차수 &gt; <%=v_lesson%>일차</td>
        </tr>
      </table>
        <br>
        <!----------------- 등록, 리스트 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td valign="bottom" height="20">그룹명 
              <select name="p_projgrp" onchange="javascript:projgrp_select()">
              <% for(int j=1;j <= v_groupcnt;j++) { %>
                <option value="<%=j%>" <% if(j == Integer.parseInt(v_selprojgrp)){ %> selected <% } %>><%=v_subjnm%><%=v_subjseq%>과정<%=v_lesson%>일차<%=j%>그룹</option>
              <% } %>
              </select>
            </td>
            <td align="right" width="65">
            <a href="javascript:projectGrouping()"><img src="/images/admin/study/record1_butt.gif" width="37" height="18" border="0"></a>
            </td>
            <td align="right" width="55">
            <a href="javascript:projectList()"><img src="/images/admin/study/list1_butt.gif" width="48" height="18" border="0"></a>
            </td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 등록, 리스트 버튼 끝 ----------------->
        <!----------------- 리포트 그룹별 등록/수정 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title"><b>NO</b></td>
            <td class="table_title"><b>소속</b></td>
            <td class="table_title"><b>직위</b></td>
            <td class="table_title"><b>ID</b></td>
            <td class="table_title"><b>사번</b></td>
            <td class="table_title"><b>성명</b></td>
            <td class="table_title"><b>그룹명</b></td>
            <td class="table_title">&nbsp;</td>
          </tr>
        <% 
            for(i = 0; i < list.size(); i++) {
                ProjectData data= (ProjectData)list.get(i);
                v_projgrp       = data.getProjgrp();
                v_projname      = data.getProjname();
                v_userid        = data.getUserid();
                v_cono          = data.getCono();
                v_name          = data.getName();
                v_jikwinm       = data.getJikwinm();
                v_companynm     = data.getCompanynm();
                v_gpmnm         = data.getGpmnm();
                v_deptnm        = data.getDeptnm();          
                if(v_projgrp  == null ){ v_projgrp  = "";   }                      
                if(v_projname == null ){ v_projname = "";   }
           %>
          <tr> 
            <td class="table_01"><%= list.size() - i %></td>
            <td class="table_02_1"><%=v_companynm%>/<%=v_gpmnm%>/<%=v_deptnm%></td>
            <td class="table_02_1"><%=v_jikwinm%></td>
            <td class="table_02_1"><%=v_userid%></td>
            <td class="table_02_1"><%=v_cono%></td>
            <td class="table_02_1"><%=v_name%></td>
            <td class="table_02_1"><%=v_projname%></td>
            <td class="table_02_1">
              <input type="checkbox" name="p_grouping" value="<%=v_userid%>" <% if(v_projgrp.equals(v_selprojgrp)){%> checked<% } %>>
            </td>
          </tr>
         <%}  
          if(i == 0){ %>        
              <tr> 
                <td class="table_02_1" colspan="8">등록된 내용이 없습니다</td>                  
              </tr>         
         <%  } %>                      
        </table>
        <br>

        <!----------------- 대표자 지정 시작 -----------------> 
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table width="20%" border="0" cellspacing="1" cellpadding="5">
                <tr> 
                  <td height="3" class="table_top_line"></td>
                </tr>
                <tr> 
                  <td width="5%" class="table_title"><b>대표자 
                    지정</b></td>
                </tr>
                <tr> 
                  <td class="table_02_1""> 
                    <select name="p_chief">
                      <% for(int k=0;k < list.size();k++) { 
                            ProjectData data= (ProjectData)list.get(k);
                            v_projgrp       = data.getProjgrp();
                            v_userid        = data.getUserid();
                            v_name          = data.getName();
                            v_chief         = data.getChief();
                            if(v_projgrp  == null ){ v_projgrp  = "";   }                      
                            if(v_chief == null )   { v_chief = "";   }                            
                       %>     
                        <option value="<%=v_userid%>"  <% if(v_projgrp.equals(v_selprojgrp) && v_userid.equals(v_chief)){ %> selected <% } %>><%=v_name%></option>
                      <% } %>
                    </select>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>      
        <!----------------- 대표자 지정 끝 -----------------> 

        <!----------------- 리포트 그룹별 등록/수정 끝 ----------------->  
        <br>
      </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr> 

</table>
</form>
</body>
</html>
