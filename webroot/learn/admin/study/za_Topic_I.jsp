<%
//**********************************************************
//  1. 제      목: TOPIC INSERT
//  2. 프로그램명: za_Topic_I.jsp
//  3. 개      요: 토론방 주제발의
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 23
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
    String  v_grcode    = box.getString("p_grcode"); 
    String  v_grcodenm  = box.getString("p_grcodenm"); 
    String  v_gyear     = box.getString("p_gyear");    
    String  v_grseq     = box.getString("p_grseq");    
    String  v_subj      = box.getString("p_subj");
    String  v_subjnm    = box.getString("p_subjnm");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
	String  v_subjseqgr   = box.getString("p_subjseqgr");
    int v_pageno         = box.getInt("p_pageno");
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

    // 나모에디터
  	String contents = "";
  	String width	= "540";
  	String height	= "250";    

  	String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name; 

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css"> 
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
        function topic_insert() {
            var checkcnt = 0;
            var st_date = make_date(form1.p_sdate.value);
            var ed_date = make_date(form1.p_ldate.value);
    
            if (blankCheck(document.form1.p_title.value)) {
                alert("토론주제를 입력하세요!");
                document.form1.p_title.focus();
                return;
            }
    
            if (realsize(document.form1.p_title.value) > 200) {
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
    
            /*if (blankCheck(document.form1.p_adcontent.value)) {
                alert("토론내용을 입력하세요!");
                document.form1.p_adcontent.focus();
                return;
            }*/
            //alert(document.form1.Wec.MIMEValue);
        	document.form1.p_adcontent.value = document.form1.Wec.MIMEValue;	            
            document.form1.target = "_self";
            document.form1.p_started.value = st_date;
            document.form1.p_ended.value   = ed_date;
    
            document.form1.action = "/servlet/controller.study.ToronAdminServlet";
            document.form1.p_process.value = "TopicInsert";
            document.form1.submit();
        }    
	    function cancel() {
	        document.form1.target = "_self";
            document.form1.action = "/servlet/controller.study.ToronAdminServlet";
            document.form1.p_process.value = "TopicList";      
            document.form1.submit();
        }
   -->			
    </script>        
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

    <form name = "form1" method = "post">
    <input type="hidden" name="p_aduserid" value="<%=v_user_id%>">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">    
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_uclass"    value="<%=ss_uclass%>">     <!--in relation to select-->
    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->
    <input type="hidden" name="p_subj" value="<%=v_subj%>">
    <input type="hidden" name="p_year" value="<%=v_year%>">
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">  
	<input type="hidden" name="p_subjseqgr"   value="<%=v_subjseqgr%>">     
    <input type = "hidden" name="p_started">
	<input type = "hidden" name="p_ended">    
    <input type = "hidden" name = "p_adcontent"    value = "">	
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title06.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0"> 
              <b><%=v_subjnm%> </b>
          </td> 
        </tr>
      </table>
      <br>

      <!----------------- 토론방 주제 발의 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="15">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>
          <tr> 
            <td colspan="2" class="table_title"><b>토론주제 입력</b></td>
        </tr>
        <tr> 
            <td class="table_01"><b>토론기간</b></td>
            <td class="table_02_2"> 

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
                <td width="580">&nbsp;</td>
              </tr>
            </table>
           
          </td>
        </tr>        
        <tr  height='50'> 
            <td class="table_01" width="16%"><b>발의자</b></td>
            <td class="table_02_2" width="81%"><p><%=v_user_name%></p></td>
        </tr>
        <tr> 
            <td class="table_01" height='35'><b>토론주제</b></td>
            <td class="table_02_2"><p> 
            <input name="p_title" type="text" class="input" size="50"></p>
            </td>
        </tr>
        <tr> 
            <td class="table_01"><b>내용</b></td>
            <td class="table_02_2"> 
            <!--<textarea name="p_adcontent" cols="100" rows="15"></textarea>-->
                <br><br>
                <!-- 나모 Editor  -->
                <p align="center">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";namoActiveInitCompleted("p_adcontent");</script>
                <script language="javascript">object_namoActivepatch('684','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
                </p>
                <!-- 나모 Editor  -->                 
            </td>
        </tr>
      </table>
      <!----------------- 토론방 주제 발의 끝 ----------------->
      <br>
      <!----------------- 등록, 취소 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center">
          <a href="javascript:topic_insert()"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>
		  <td width=8></td>
          <td align="center">
          <a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
      </table>
        <!----------------- 등록, 취소 버튼 끝 ----------------->
        <br>
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>  
</table>
</form>
</body>
</html>
