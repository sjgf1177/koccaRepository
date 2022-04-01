<%
//**********************************************************
//  1. 제      목: TUTOR HISTORY LIST
//  2. 프로그램명: za_TutorHistory_L.jsp
//  3. 개      요: 강사 이력 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process        = box.getString("p_process");    
    String v_search         = box.getString("p_search");        //전문분야 검색
    String v_select         = box.getString("p_select");        //검색항목(과정명1,강사명2)
    String v_selectvalue    = box.getString("p_selectvalue");   //검색어
    String v_ongo           = "whenSelection('TutorHistoryList')";       
    String v_userid         = "";   
    String v_name           = "";
    String v_comp           = "";
    String v_dept           = "";
    String v_handphone      = "";
    String v_email          = "";
    String v_isgubun        = "";
    String v_subj           = "";
    String v_subjnm         = "";
    String v_isgubun_value  = "";
    String v_sdesc          = "";
    String v_lectlevel      = "";
    int    v_lecture        =  0;    
    int     i               =  0;
    //DEFINED class&variable END

    ArrayList list = (ArrayList)request.getAttribute("tutorHistoryList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script language = "javascript">
    <!--    
        function whenSelection(process) {
            document.form1.target = "_self";
            document.form1.action='/servlet/controller.tutor.TutorAdminServlet';
            document.form1.p_process.value = process;  
            document.form1.submit();
        }
        function search() {
             document.form1.target = "_self";
             document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
             document.form1.p_process.value = "TutorHistoryList";
             document.form1.submit();
        }              
        function tutor_select(userid) {
            document.form1.target = "_self";
        	document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
        	document.form1.p_userid.value = userid;
        	document.form1.p_process.value= "TutorHistorySelect";
        	document.form1.submit();
        }                     
        function whenExcel() {
            window.self.name = "TutorHistory";     
            open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");  
            document.form1.target = "openExcel";
            document.form1.action='/servlet/controller.tutor.TutorAdminServlet';
            document.form1.p_process.value = 'TutorHistoryExcel';   
            document.form1.submit();
        }
    -->
    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process"   value = "<%= v_process %>">  
    <input type = "hidden" name = "p_userid">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/tutor/title_bg.gif"><img src="/images/admin/tutor/tutor_title5.gif" width="206" height="36" border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
        <!----------------- form 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td valign="bottom" width="98%">&nbsp;<img src="/images/admin/tutor/icon1.gif" width="12" height="7" border="0"><b><font color="#107AAD" face="굴림" size="2.5">과정명 
              및 성명 조회</font></b></td>
          </tr>
          <tr> 
            <td height="3" width="98%"></td>
          </tr>
        </table>
        <table class="form_table_out" cellspacing="0" cellpadding="3">
          <tr> 
            <td bgcolor="#C6C6C6" align="center"> 
              <table class="form_table_bg" cellspacing="0" cellpadding="0">
                <tr> 
                  <td height="7" width="99%"></td>
                </tr>
                <tr> 
                  <td align="center" width="99%" valign="middle"> ◈ 과정명 또는 강사를 
                    입력하세요.</td>
                </tr>
                <tr> 
                  <td height="9" width="99%"></td>
                </tr>
                <tr> 
                  <td align="center" width="99%" valign="middle"> 

                    <table class="form_table" cellspacing="0" cellpadding="0">
                      <tr>                         
                        <td align="center">
                          <input type="radio" name="p_select" value="1" <% if(v_select.equals("1") || v_select.equals("")){ %> checked <% } %>>
                          과정명 
                          <input type="radio" name="p_select" value="2" <% if(v_select.equals("2")){ %> checked <% } %>>
                          강사 &nbsp; 
                          <input name="p_selectvalue" type="text" class="input" value="<%=v_selectvalue%>">                       
                        <a href="javascript:search()"><img src="/images/admin/tutor/search1_butt.gif" width="37" height="20" border="0"></a>
                        </td>
                      </tr>
                    </table>

                  </td>
                </tr>
                <tr> 
                  <td height="7" width="99%"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->


        <br>
        <br>

        <!----------------- 강사조회 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="3"></td>
          </tr>
          <tr> 
            <td valign="bottom">년도 
<%
    //검색년도 select box START
    String gyear = "";    
    gyear = box.getString("gyear");
    SelectParam param = new SelectParam("gyear", "", false, 24,box.getHttpSession()); 
    param.setOnchange(v_ongo);
    //검색년도 select box END
%>
    <%=SelectionUtil.getGyear(gyear, param)%>
            </td>
            <td align="right" valign="top">
            <a href="javascript:whenExcel()"><img src="/images/admin/tutor/excel_print_butt.gif" width="58" height="18" border="0"></a>
            </td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
                    
        <table class="table_out" cellspacing="1" cellpadding="0">
          <tr> 
            <td colspan="11" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title"><b>No</b></td>
            <td class="table_title"><b>강사명</b></td>
            <td class="table_title"><b>주민등록번호</b></td>
            <td class="table_title"><b>회사</b></td>
            <td class="table_title"><b>부서</b></td>
            <td class="table_title"><b>연락처</b></td>
            <td class="table_title"><b>E-mail</b></td>
            <td class="table_title"><b>과정명</b></td>
            <td class="table_title"><b>강좌</b></td>
            <td class="table_title"><b>구분</b></td>
            <td class="table_title"><b>등급</b></td>
          </tr>
        <%                                                                                                                                             
            for(i = 0; i < list.size(); i++) {
                TutorData data  = (TutorData)list.get(i);        
                v_userid        = data.getUserid();       
                v_name          = data.getName();         
                v_comp          = data.getComp();         
                v_dept          = data.getDept();         
                v_handphone     = data.getHandphone();    
                v_email         = data.getEmail();        
                v_isgubun       = data.getIsgubun();      
                v_subj          = data.getSubj();         
                v_subjnm        = data.getSubjnm();       
                v_sdesc         = data.getSdesc();        
                v_lectlevel     = data.getLectlevel();    
                v_lecture       = data.getLecture();                                   
                if( v_handphone.length() <= 3 )     {	v_handphone = "";       }
                if(v_isgubun.equals("1")){
                    v_isgubun_value = "사내강사";
                }else if(v_isgubun.equals("2")){
                    v_isgubun_value = "사외강사";
                }                              
        %>                    
                    
          <tr> 
            <td class="table_01"><%= list.size() - i %></td>
            <td class="table_02_1">
                <a href = "javascript:tutor_select('<%=v_userid%>')"><%=v_name%></a>
            </td>
            <td class="table_02_1"><%=v_userid%></td>
            <td class="table_02_1"><%=v_comp%></td>
            <td class="table_02_1"><%=v_dept%></td>
            <td class="table_02_1"><%=v_handphone%></td>
            <td class="table_02_1"><%=v_email%></td>
            <td class="table_02_1"><%=v_subjnm%></td>
            <td class="table_02_1"><%=v_sdesc%></td>
            <td class="table_02_1"><%=v_isgubun_value%></td>
            <td class="table_02_1"><%=v_lectlevel%></td>
          </tr>
          <%}%>
        <% if(i == 0){ %>
          <tr> 
            <td class="table_02_1" colspan="11">등록된 내용이 없습니다</td>
          </tr>
          <% } %>              
        </table>
        <!----------------- 강사조회 끝 ----------------->
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
