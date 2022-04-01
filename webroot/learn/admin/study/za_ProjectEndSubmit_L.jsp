<%
//**********************************************************
//  1. 제      목: PROJECT 교육 후 LIST
//  2. 프로그램명: za_ProjectEndSubmit_L.jsp
//  3. 개      요: 리포트 교육 후 평가 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 8. 30
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
    String  v_projseq   = box.getString("p_projseq");    
    int     v_ordseq    = box.getInt("p_ordseq");                	
    String  v_ptitle    = box.getString("p_ptitle");   // 리포트제목
    
    ArrayList list1      = null;
    DataBox data1        = null;
    ArrayList list2      = null;
    DataBox data2        = null;
    
    list1 = (ArrayList)request.getAttribute("ProjectEndAssignList");      // 배정받은 수강생
    list2 = (ArrayList)request.getAttribute("ProjectEndAssignNotList");   // 다른 리포트에도 배정 받지 않은 수강생      
            
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 새창크기 조절
    function open_window(name, url, left, top, width, height, scrollbar, toolbar, menubar, statusbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }         
    
    // 리포트 제출
    function projectSubmit(userid, name, assign) {
        window.self.name = "projectDetail";     //opener 객체에 name 을 지정한다
        open_window("openProjectSubmit","","50","50","750","600","1");      
        document.form1.target = "openProjectSubmit";
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectEndSubmitOpen";
        document.form1.p_userid.value = userid;
        document.form1.p_name.value = name;
        document.form1.p_assign.value = assign;        
        document.form1.submit();     
    }

    // 리스트이동
    function list() {
        document.form1.target = "_self";    
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectSubmitList";        
        document.form1.submit();       
    }    
                     
-->    	
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
   <form name="form1" method="post" action="">
    <input type="hidden" name="p_process"   value="<%=v_process%>">
    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">
    <input type="hidden" name="p_year"      value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">
    <input type="hidden" name="p_ordseq"    value="<%=v_ordseq%>">
    <input type="hidden" name="p_projseq"   value="<%=v_projseq%>"> 
    <input type="hidden" name="p_userid">
    <input type="hidden" name="p_name">
    <input type="hidden" name="p_ptitle"    value="<%=v_ptitle%>">
    <input type="hidden" name="p_assign"><!--배정 받을지 flag-->
    

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title08.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>  
	  <!----------------- title 끝 ----------------->
      <br>
      
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>교육기간 완료 후 재제출</td>
        </tr>
      </table> 
      <br>
        <!----------------- 리스트 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
		    <td width="305"><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">
            <%=v_subjnm%> &gt; <%=StringManager.cutZero(v_subjseq)%>차수
            &gt; <b><%=v_ptitle%></b>
            </td>
            <td align=right><a href="javascript:list();"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
		<br>

      <!----------------- 배정받은 수강생 시작 ----------------->
		<table cellspacing="0" cellpadding="0" class="table1">
          <tr>
            <td width="150" height="23" align="center" valign="middle">
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="blue_butt_left"></td>
                  <td class="blue_butt_top"></td>
                  <td rowspan="3" class="blue_butt_right"></td>
                </tr>
                <tr>
                  <td class="blue_butt_middle">배정받은 수강생</td>
                </tr>
                <tr>
                  <td class="blue_butt_bottom"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>        
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="12" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title"><b>NO</b></td>
            <td class="table_title" width="8%"><b>성명</b></td>            
            <td class="table_title" width="8%"><b>ID</b></td>
            <td class="table_title" width="10%"><b>부서</b></td>
            <td class="table_title"><b>리포트 제목</b></td>
            <td class="table_title" width="10%"><b>제출일</b></td>
            <td class="table_title" width="10%"><b>반려여부</b></td>
            <td class="table_title" width="10%"><b>재제출여부</b></td>  
            <td class="table_title" width="8%"><b>기능</td>          
          </tr>
          <% 
            for(int i = 0; i < list1.size(); i++) {
                data1= (DataBox)list1.get(i);
                
                String s_ldate      = "";    // 제출일     
                String s_ldatevalue = "-";   // 제출여부     
                String s_isretvalue = "";    // 반려      
                String s_resendvalue= "";    // 재제출여부   
                                
                if(data1.getInt("d_ordseq") > 0) { 
                    s_ldate = FormatDate.getFormatDate(data1.getString("d_indate"),"yyyy/MM/dd");            
                    s_ldatevalue = "<font color=blue>제출</font>";
                }
                if(data1.getString("d_isret").equals("Y")) {
                    s_isretvalue = "<font color=red>반려</font>";
                }              
                if(data1.getInt("d_resend") > 0) {
                    s_resendvalue = "<font color=blue>재제출</font>";
                }   
                
           %>           
                           
          <tr> 
            <td class="table_01"><%= i+1 %></td>
            <td class="table_02_1" align="center"><%=data1.getString("d_name")%></td>            
            <td class="table_02_1" align="center"><%=data1.getString("d_userid")%></td>
            <td class="table_02_1" align="center"><%=data1.getString("d_deptnam")%></td>            
            <td class="table_02_2"><%=data1.getString("d_title")%></td>       
            <td class="table_02_1" align="center"><%=s_ldate%><br><%=s_ldatevalue%></td>                
            <td class="table_02_1" align="center"><%=s_isretvalue%></td>     
            <td class="table_02_1" align="center"><%=s_resendvalue%></td>                   
            <td class="table_03_1" align="center"> <a href="javascript:projectSubmit('<%=data1.getString("d_userid")%>','<%=data1.getString("d_name")%>', '0')"><img src='/images/admin/button/b_insert.gif' border='0'></a><a href="javascript:projectSubmit('<%=data1.getString("d_userid")%>','<%=data1.getString("d_name")%>', '0')"> 
              </a></td>                 
          </tr>
          <%}  
          if(list1.size() == 0){ %>
          <tr> 
            <td align="center" class="table_02_1" height="50" colspan="12">등록된 내용이 없습니다</td>
          </tr>
          <%  } %>
        </table>
        <!----------------- 배정받은 수강생 끝 ----------------->
        <br>  <br>
        <!----------------- 다른 리포트에도 배정 받지 않은 수강생 시작 ----------------->
		<table cellspacing="0" cellpadding="0" class="table1">
          <tr>
            <td width="280" height="23" align="center" valign="middle">
              <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="blue_butt_left"></td>
                  <td class="blue_butt_top"></td>
                  <td rowspan="3" class="blue_butt_right"></td>
                </tr>
                <tr>
                  <td class="blue_butt_middle">다른 리포트에도 배정 받지 않은 수강생</td>
                </tr>
                <tr>
                  <td class="blue_butt_bottom"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>     
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="12" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="5%" class="table_title"><b>NO</b></td>
            <td class="table_title" width="8%"><b>성명</b></td>            
            <td class="table_title" width="8%"><b>ID</b></td>
            <td class="table_title" width="10%"><b>부서</b></td>
            <td class="table_title"><b>리포트 제목</b></td>
            <td class="table_title" width="10%"><b>제출일</b></td>
            <td class="table_title" width="10%"><b>반려여부</b></td>
            <td class="table_title" width="10%"><b>재제출여부</b></td> 
            <td class="table_title" width="8%"><b>기능</td>          
          </tr>
          <% 
            for(int i = 0; i < list2.size(); i++) {
                data2= (DataBox)list2.get(i);
                
                String s_ldate      = "";   // 제출일     
                if(data2.getInt("d_ordseq") > 0) { 
                    s_ldate = FormatDate.getFormatDate(data2.getString("d_indate"),"yyyy/MM/dd");            
                }               
           %>           
                           
          <tr> 
            <td class="table_01"><%= i+1 %></td>
            <td class="table_02_1" align="center"><%=data2.getString("d_name")%></td>            
            <td class="table_02_1" align="center"><%=data2.getString("d_userid")%></td>
            <td class="table_02_1" align="center"><%=data2.getString("d_deptnam")%></td>            
            <td class="table_02_2"><%=data2.getString("d_title")%></td>       
            <td class="table_02_1" align="center"><%=s_ldate%></td>                
            <td class="table_02_1" align="center"></td>      
            <td class="table_02_1" align="center"></td>                
            <td class="table_02_1" align="center">
            <a href="javascript:projectSubmit('<%=data2.getString("d_userid")%>','<%=data2.getString("d_name")%>', '1')"><img src='/images/admin/button/b_insert.gif' border='0'>
            </a></td>                 
          </tr>
          <%}  
          if(list2.size() == 0){ %>
          <tr> 
            <td align="center" class="table_02_1" height="50" colspan="12">등록된 내용이 없습니다</td>
          </tr>
          <%  } %>
        </table>
        <!----------------- 다른 리포트에도 배정 받지 않은 수강생 끝 ----------------->        
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>    
</table>
</form>
</body>
</html>
