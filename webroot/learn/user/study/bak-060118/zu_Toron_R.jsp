<%
//**********************************************************
//  1. 제      목: TORON READ
//  2. 프로그램명: zu_Toron_R.jsp
//  3. 개      요: 토론방 글내용 보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 03
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
    String  v_user_id   = box.getSession("userid");  
    String  v_process   = box.getString("p_process");    
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");    
    String  v_tpcode    = box.getString("p_tpcode");                        
    String  v_started   = "";
    String  v_ended     = "";
    String  v_title     = "";                
    String  v_addate    = "";
    String  v_adcontent = "";
    String  v_aduserid  = "";
    String  v_name      = "";
    String  v_userid    = ""; 
    String  v_title1    = "";      
    String  v_adcontent1= "";      
    String  v_userid1   = "";      
    String  v_addate1   = "";       
    String  v_name1     = ""; 
    int     v_seq1      =  0;               
    int     v_cnt1      =  0;       
    int     v_seq       =  0;
    int     v_cnt       =  0;
    int     v_levels    =  0;
    int     i           =  0;
    boolean v_status    =  false;
    ToronData data1     = null;
    ToronData data2     = null;
    ArrayList list      = null;
    //DEFINED class&variable END
        
	data1 = (ToronData)request.getAttribute("topicSelect");     
	v_started   = data1.getStarted(); 
	v_ended     = data1.getEnded();      
	v_title     = data1.getTitle();      
	v_addate    = data1.getAddate();     
	v_adcontent = data1.getAdcontent();
	v_userid    = data1.getAduserid();
	v_name      = data1.getName();  		
	v_cnt       = data1.getCnt();  		
    if((Integer.parseInt(v_started) < Integer.parseInt(FormatDate.getDate("yyyyMMddHH")))&&
    (Integer.parseInt(FormatDate.getDate("yyyyMMddHH")) < Integer.parseInt(v_ended))){
        v_status = true;
    }else{
        v_status = false;
    }                          	
    v_addate    = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd"); 
    v_started   = FormatDate.getFormatDate(v_started,"yyyy/MM/dd HH");    
    v_ended     = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd HH");
    
	data2 = (ToronData)request.getAttribute("toronSelect");     
	v_seq1      = data2.getSeq();
	v_title1    = data2.getTitle();         
	v_adcontent1= data2.getAdcontent();
	v_userid1   = data2.getAduserid();
	v_addate1   = data2.getAddate();  	
	v_name1     = data2.getName();  		
	v_cnt1      = data2.getCnt();  		
	v_addate1   = FormatDate.getFormatDate(v_addate1,"yyyy/MM/dd"); 
    
    list = (ArrayList)request.getAttribute("toronList");  	
%>
<html>
<head>
<title>::: 토론방 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript">
<!--
    function whenTopicList(){
        document.form1.p_process.value = "";
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();                
    }
       
    function whenTopicUpdate(){
        document.form1.p_process.value = "TopicUpdatePage";
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();           
    }

    function whenTopicDelete(){
        document.form1.p_process.value = "TopicDelete";
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();           
    }    
    function whenToronInsert(){
        document.form1.p_process.value = "ToronInsertPage";
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit(); 
    }            
    function whenToronView(seq){
        document.form1.p_process.value = "ToronSelect";
        document.form1.p_seq.value = seq;
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();
     }
     function whenToronReply(seq){
        document.form1.p_process.value = "ToronReplyPage";
        document.form1.p_seq.value = seq;
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();
    }
     function whenToronUpdate(seq){
        document.form1.p_process.value = "ToronUpdatePage";
        document.form1.p_seq.value = seq;
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();
    }    
     function whenToronDelete(seq){
        document.form1.p_process.value = "ToronDelete";
        document.form1.p_seq.value = seq;
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
<input type="hidden" name="p_tpcode" value="<%=v_tpcode%>">
<input type="hidden" name="p_seq">

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
            <!----------------- 토론방 내용 시작 ---------------->
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
                      <td class="board_title_bg1">주제개요</td>
                      <td class="board_title_bg3" width="28%"> <%=v_title%>
                      </td>
                      <td class="board_title_bg1" width="15%">조회수</td>
                      <td class="board_title_bg3" width="27%"><%=v_cnt%></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg1" width="15%">작성자</td>
                      <td class="board_title_bg3" width="28%"><%=v_name%></td>
                      <td class="board_title_bg1" width="15%"> 토론기간</td>
                      <td class="board_title_bg3" width="27%"><%=v_started%>시~<%=v_ended%>시</td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="board_text1" align="center"> 
                        <table width="98%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td><%= StringManager.replace(v_adcontent,"\r\n","<br>") %></td>
                          </tr>
                          <tr> 
                            <td>
                            <% if(v_user_id.equals(v_userid)){ %>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="right">
                                   <a href="javascript:whenTopicUpdate()"><img src="/images/user/button/btn_mod.gif"  border="0"></a>
                                  </td>
                                  <td align="right" width="65">
                                   <a href="javascript:whenTopicDelete()"><img src="/images/user/button/btn_del.gif"  border="0"></a>                                                                      
                                  </td>
                                </tr>
                              </table>                           
                             <% } %>    
                            </td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>


                </td>
              </tr>
            </table>
            <!----------------- 토론방 내용 끝 ---------------->


            <br>


            <!----------------- 의견 내용 시작 ---------------->
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
                      <td class="board_title_bg1" width="15%">의견제목</td>
                      <td class="board_title_bg3" width="28%"><%=v_title1%></td>
                      <td class="board_title_bg1" width="15%"> 조회수</td>
                      <td class="board_title_bg3" width="27%"><%=v_cnt1%></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg1">작성자</td>
                      <td class="board_title_bg3"> <%=v_name1%></td>
                      <td class="board_title_bg1">작성일</td>
                      <td class="board_title_bg3"><%=v_addate1%></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="board_text1" align="center"> 
                        <table width="98%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td><%= StringManager.replace(v_adcontent1,"\r\n","<br>") %></td>
                          </tr>
                          <tr> 
                            <td>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="right">
                                  <a href="javascript:whenToronReply('<%=v_seq1%>')"><img src="/images/user/button/btn_answer.gif"  border="0"></a></td>
                                  <% if(v_user_id.equals(v_userid1)){ %>
                                  <td align="right" width="65">
                                  <a href="javascript:whenToronUpdate('<%=v_seq1%>')"><img src="/images/user/button/btn_mod.gif"  border="0"></a></td>
                                  <td align="right" width="65">
                                  <a href="javascript:whenToronDelete('<%=v_seq1%>')"><img src="/images/user/button/btn_del.gif" border="0"></a></td>
                                  <% } %>
                                </tr>
                              </table>                              
                            </td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 의견 내용 끝 ---------------->
            
            <br>
            
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
                      <td class="board_title_bg1" height="28">의 견</td>
                      <td class="board_title_bg1" width="10%" height="28">게시일</td>
                      <td class="board_title_bg1" width="9%" height="28">참여자</td>
                    </tr>
        <% 
            for(i = 0; i < list.size(); i++) {        
                ToronData data3  = (ToronData)list.get(i);  
                v_seq       = data3.getSeq();      
                v_title     = data3.getTitle();    
                v_aduserid  = data3.getAduserid(); 
                v_addate    = data3.getAddate();   
                v_name      = data3.getName();    
                v_levels    = data3.getLevels();
                v_addate    = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd"); 
        %>        
                    <tr> 
                      <td class="board_text1" height="27"><%= list.size() - i %></td>                      
                      <td class="board_text3" height="27">
                      <% if (v_levels > 1) {%>
                        <img src = "/images/user/study/space.gif" width="<%= v_levels*15-30 %>" height ="10" border =0>
                        <img src="/images/user/support/ico_re.gif"   border="0">
                      <% } %>                      
                      <a href="javascript:whenToronView('<%=v_seq%>')"><%= v_title %></a></td>
                      <td class="board_text1"><%= v_addate %></td>
                      <td class="board_text1"><%= v_name %></td>
                    </tr>
                    
        <%  }  if(i == 0){ %>        
                      <tr> 
                        <td align="center" class="board_text1" colspan="5">등록된 내용이 없습니다</td>                  
                      </tr>
                 <%  } %>                                                                                                      
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 토론방 리스트 끝 ---------------->

            <!----------------- 의견작성, 주제목록으로, 닫기 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td height="11" colspan='3'></td>
              </tr>
              <tr> 
                <td align="right">
                <% if(v_status){ %><a href="javascript:whenToronInsert()"><img src="/images/user/button/btn_write.gif" border="0"></a><%}%>
                </td>
                <td align="right" width="109">
                <a href="javascript:whenTopicList()"><img src="/images/user/button/btn_subjectlist.gif"  border="0"></a></td>
                <td align="right" width="65">
                <a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 의견작성, 주제목록으로, 닫기 버튼 끝 ----------------->

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
