<%
//**********************************************************
//  1. 제      목: TORON LIST
//  2. 프로그램명: zu_Toron_L.jsp
//  3. 개      요: 토론방 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 02
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
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_tpcode    = "";
    String  v_title     = "";
    String  v_aduserid  = "";
    String  v_addate    = "";
    String  v_started   = "";
    String  v_ended     = "";
    String  v_name      = "";
    String  v_status    = "";
    int     v_cnt       =  0;
    int     i           =  0;
    ArrayList list      = null;
    //DEFINED class&variable END
        
    list = (ArrayList)request.getAttribute("topicList");  
%>
<html>
<head>
<title>::: 토론방 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript">
<!--
    function topicInsert(){
        document.form1.p_process.value = "TopicInsertPage";
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();                
    }
    
    function topicView(tpcode){
        document.form1.p_process.value = "TopicSelect";
        document.form1.p_tpcode.value = tpcode;
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();           
    }
-->
</script>
</head>

<body topmargin="0" leftmargin="0">
<form name="form1" method="post">
<input type="hidden" name="p_process">
<input type="hidden" name="p_subj" value="<%=v_subj%>">
<input type="hidden" name="p_year" value="<%=v_year%>">
<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
<input type="hidden" name="p_tpcode">
<!--
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr> 
    <td> 
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2">
        <tr> 
          <td align="center" valign="top" class="body_color"> 
-->

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr> 
    <td> 
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr> 
          <td align="center" valign="top" class="body_color"> 
          
            <br>
            <!----------------- 타이틀 시작 ----------------->
  				<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    				<tr> 
                      <td><img src="/images/user/study/discuss_title.gif"></td>
                      <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
                      <td><img src="/images/user/study/gongi_tail.gif"></td>
    				</tr>
  				</table>
            <!----------------- 타이틀 끝 ---------------->
            <br>            
            <!----------------- 주제발의 버튼 시작 ----------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td></td>
                <td><b>아래에서 토론에 참여할 주제를 선택하십시오.</b></td>
                <td align="right" width="80">
                <a href="javascript:topicInsert()"><img src="/images/user/button/btn_subject.gif" border="0"></a>
                </td>
              </tr>
			  <tr>
			  <td height=6></td>
			  <td height=6></td>
			  <td height=6></td>
			  </tr>
            </table>
            <!----------------- 주제발의 버튼 끝 ----------------->

            <!----------------- 토론방 리스트 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>

            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1" width="5%" height="28">번호</td>
                      <td class="board_title_bg1" width="9%" height="28">상태</td>
                      <td class="board_title_bg1" height="28">주 제</td>
                      <td class="board_title_bg1" width="9%" height="28">발의자</td>
                      <td class="board_title_bg1" width="10%" height="28">발의일</td>
                      <td class="board_title_bg1" width="7%" height="28">조회수</td>
                      <td class="board_title_bg1" width="27%" height="28">토론기간</td>
                    </tr>
        <% 
            for(i = 0; i < list.size(); i++) {        
                ToronData data  = (ToronData)list.get(i);              
                v_tpcode    = data.getTpcode();   
                v_title     = data.getTitle();    
                v_aduserid  = data.getAduserid(); 
                v_addate    = data.getAddate();   
                v_started   = data.getStarted();  
                v_ended     = data.getEnded();
                v_name      = data.getName();     
                v_cnt       = data.getCnt();      
                if((Integer.parseInt(v_started) < Integer.parseInt(FormatDate.getDate("yyyyMMddHH")))&&
                (Integer.parseInt(FormatDate.getDate("yyyyMMddHH")) < Integer.parseInt(v_ended))){
                    v_status = "진행중";
                }else{
                    v_status = "마감";
                }                                
                v_addate    = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd"); 
                v_started   = FormatDate.getFormatDate(v_started,"yyyy/MM/dd HH" );    
                v_ended     = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd HH");                  
        %>
                    <tr> 
                      <td class="board_text1" height="27"><%= list.size() - i %></td>
                      <td class="board_text1" height="27"><!--<img src="/images/user/study/progress_butt1.gif" width="15" height="16" border="0"> -->
                        <%=v_status%></td>
                      <td class="board_text3" height="27">
                      <a href="javascript:topicView('<%=v_tpcode%>')"><%=v_title%></a>
                      </td>
                      <td class="board_text1"><%=v_name%></td>
                      <td class="board_text1"><%=v_addate%></td>
                      <td class="board_text1"><%=v_cnt%></td>
                      <td class="board_text1" height="27"><%=v_started%>시~<%=v_ended%>시</td>
                    </tr>
        <%  }  if(i == 0){ %>        
                      <tr> 
                        <td align="center" class="board_text1" colspan="7">등록된 내용이 없습니다</td>                  
                      </tr>
                 <%  } %>  
                  </table>

                </td>
              </tr>
            </table>
            <!----------------- 토론방 리스트 끝 ---------------->
            <!----------------- 닫기 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td height="11"></td>
              </tr>
                <td align="right">
                <a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 닫기 버튼 끝 ----------------->

            <br>
            
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
