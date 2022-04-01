<%
//**********************************************************
//  1. 제      목: TORIC INSERT
//  2. 프로그램명: zu_Topic_I.jsp
//  3. 개      요: 토론방 주제발의
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
    String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("name");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");              
    String  v_subjseq  = box.getString("p_subjseq");
	//DEFINED class&variable END         	   

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";	           
%>
<html>
<head>
<title>::: 토론방 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script	language="JavaScript" type="text/JavaScript">
    $(document).ready(function(){
    	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
    	$("#p_ldate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
    });
    <!--	   
        function whenTopicInsert() {
            var checkcnt = 0;
            var st_date = make_date(form1.p_sdate.value);
            var ed_date = make_date(form1.p_ldate.value);
    
            if (blankCheck(document.form1.p_title.value)) {
                alert("토론주제를 입력하세요!");
                document.form1.p_title.focus();
                return;
            }
    
            if (realsize(document.form1.p_title.value) > 100) {
                alert("토론주제는 한글기준 100자를 초과하지 못합니다.");
                document.form1.p_title.focus();
                return;
            }
    
            if(blankCheck(document.form1.p_sdate.value)){
                alert("토론 시작일을 입력하세요.");
                return;
            }
    
            if(blankCheck(document.form1.p_ldate.value)){
                alert("토론 마감일을 입력하세요.");
                return;
            }
    
            if(st_date > ed_date) {
                alert("토론시작일이 마감일보다 큽니다.");
                return;
            }
    
        	if(document.all.use_editor[0].checked) {        
                document.form1.p_adcontent.value = ObjEditor.document.all.tags("html")[0].outerHTML;
        	}else {
    		    document.form1.p_adcontent.value = document.all.txtDetail.value;
    	    }
            document.form1.target = "_self";
            document.form1.p_started.value = st_date;
            document.form1.p_ended.value   = ed_date;
    
            document.form1.action = "/servlet/controller.study.ToronServlet";
            document.form1.p_process.value = "TopicInsert";
            document.form1.submit();
        }    
	    function cancel() {
	        document.form1.target = "_self";
            document.form1.action = "/servlet/controller.study.ToronServlet";
            document.form1.p_process.value = "";      
            document.form1.submit();	
        }
   -->			
    </script>        
</head>




<body topmargin="0" leftmargin="0">

    <form name = "form1" method = "post">
    <input type="hidden" name="p_aduserid" value="<%=v_user_id%>">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_subj" value="<%=v_subj%>">
    <input type="hidden" name="p_year" value="<%=v_year%>">
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">       
    <input type = "hidden" name="p_started">
	<input type = "hidden" name="p_ended">    
    <input type="hidden" name="p_adcontent"> 



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
            <!----------------- 주제발의 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="1" class="table2">
				  <tr>
                        <td class="board_color_line" height=2></td>
                        <td class="board_color_line" height=2></td>
                      </tr>
                    <tr> 
                      <td class="board_title_bg2">토론주제</td>
                      <td class="table_text3" colspan="3"> 
                        <input size="90" maxlength="100" name="p_title" class="input">
                      </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="15%">작성자</td>
                      <td class="table_text3" colspan="3"> <%=v_user_name%></td>
                    </tr>
                    <tr>                       
                      <td class="board_title_bg2">토론기간설정</td>
                      <td class="table_text3" colspan="3">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="180">토론시작일 : <input name="p_sdate" id="p_sdate" type="text" class="datepicker_input1" size="10">                                
                            </td>
                            <td width="20">
                              <select name="p_stime">
                                      <option value="01" selected>1시</option>     
                                      <option value="02">2시</option>     
                                      <option value="03">3시</option>     
                                      <option value="04">4시</option>     
                                      <option value="05">5시</option>     
                                      <option value="06">6시</option>     
                                      <option value="07">7시</option>    
                                      <option value="08">8시</option>     
                                      <option value="09">9시</option>     
                                      <option value="10">10시</option>     
                                      <option value="11">11시</option>     
                                      <option value="12">12시</option>     
                                      <option value="13">13시</option>     
                                      <option value="14">14시</option>     
                                      <option value="15">15시</option>     
                                      <option value="16">16시</option>     
                                      <option value="17">17시</option>     
                                      <option value="18">18시</option>     
                                      <option value="19">19시</option>     
                                      <option value="20">20시</option>     
                                      <option value="21">21시</option>     
                                      <option value="22">22시</option>     
                                      <option value="23">23시</option>     
                                      <option value="24">24시</option>                          
                                    </select>
                            </td>
                            <td width="20">&nbsp;&nbsp;~</td>
                            <td width="180">토론마감일 : <input name="p_ldate" id="p_ldate" type="text" class="datepicker_input1" size="10">
                            </td>
                            <td width="20">
                              <select name="p_ltime">
                                      <option value="01" selected>1시</option>     
                                      <option value="02">2시</option>     
                                      <option value="03">3시</option>     
                                      <option value="04">4시</option>     
                                      <option value="05">5시</option>     
                                      <option value="06">6시</option>     
                                      <option value="07">7시</option>    
                                      <option value="08">8시</option>     
                                      <option value="09">9시</option>     
                                      <option value="10">10시</option>     
                                      <option value="11">11시</option>     
                                      <option value="12">12시</option>     
                                      <option value="13">13시</option>     
                                      <option value="14">14시</option>     
                                      <option value="15">15시</option>     
                                      <option value="16">16시</option>     
                                      <option value="17">17시</option>     
                                      <option value="18">18시</option>     
                                      <option value="19">19시</option>     
                                      <option value="20">20시</option>     
                                      <option value="21">21시</option>     
                                      <option value="22">22시</option>     
                                      <option value="23">23시</option>     
                                      <option value="24">24시</option>                          
                                    </select>                
                            </td>  
                            <td width="200">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2">내 용</td>
                      <td class="table_text3" colspan="3"> 
						<!-- DHTML Editor  -->
			            <%//@ include file="/portal/include/DhtmlEditor.jsp" %>
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                        <!-- DHTML Editor  -->  
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 주제발의 끝 ---------------->
            <br>
            <!----------------- 저장, 취소, 닫기 버튼 시작 ----------------->
            <table width="210" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td align="center">
                <a href="javascript:whenTopicInsert()"><img src="/images/user/button/btn_save.gif"  border="0"></a></td>
                <td align="center">
                <a href="javascript:cancel()"><img src="/images/user/button/btn_cancel.gif"  border="0"></a></td>
                <td align="center">
                <a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 저장, 취소, 닫기 버튼 끝 ----------------->
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
