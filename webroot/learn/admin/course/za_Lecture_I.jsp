<%
//**********************************************************
//  1. 제      목: 강좌 등록
//  2. 프로그램명: SubjGongAdminServlet.java
//  3. 개      요: 과정차수 개설시(집합과정) 강좌정보 입력
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 22
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%    
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process    = box.getString("p_process");
    String v_subj       = box.getString("p_subj");
    String v_year       = box.getString("p_year");
    String v_subjseq    = box.getString("p_subjseq");
    String v_isonoff    = box.getString("p_isonoff");
    String v_tutorid    = "";
    String v_tutorname  = "";
    int     i           = 0;    	    
    ArrayList list1     = (ArrayList)request.getAttribute("TutorList");    
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
<script language="JavaScript">
$(document).ready(function(){
	$("#p_date").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    function whenLectureInsert() {
        var date = "";
        if (blankCheck(document.form1.p_date.value)) {
            alert("강의날짜를 입력하세요!");
            document.form1.p_date.focus();
            return;
        }else{
            date = make_date(form1.p_date.value);                    
        }
  
        if (blankCheck(document.form1.p_lecttime.value)) {
            alert("강의시간을 입력하세요!");
            document.form1.p_lecttime.focus();
            return;
        }

        if (blankCheck(document.form1.p_sdesc.value)) {
            alert("강좌명을 입력하세요!");
            document.form1.p_sdesc.focus();
            return;
        }
        if (blankCheck(document.form1.p_tutorid.value)) {
            alert("강사를 입력하세요!");
            document.form1.p_tutorid.focus();
            return;
        }        
		document.form1.action='/servlet/controller.course.GrseqServlet';
        document.form1.p_process.value = 'lectureInsert';
        document.form1.p_lectdate.value = date;
        document.form1.submit();
    }
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

    <form name = "form1" method = "post">
    <input type="hidden" name="p_process">
	<input type="hidden" name="p_subj"		value="<%=v_subj%>">
	<input type="hidden" name="p_year"		value="<%=v_year%>">
	<input type="hidden" name="p_subjseq"	value="<%=v_subjseq%>">	   
	<input type="hidden" name="p_isonoff"   value="<%=v_isonoff%>"> 
    <input type = "hidden" name="p_lectdate">
  
<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr> 
    <td> 
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr> 
            <td align="center" bgcolor="#FFFFFF"> 
              <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	                <!----------------- title 끝 ----------------->

              <br>

              <!----------------- 과정별 강좌정보 입력 시작 ----------------->
              <table cellspacing="1" cellpadding="5" class="open_table_out">
                <tr> 
                  <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr> 
                  <td class="table_title">강의날짜</td>
                  <td class="table_02_2"><input name="p_date" id="p_date" type="text" class="datepicker_input1" size="10"> 
                  </td>
                  <td class="table_title" width="18%">강의시작시간</td>
                  <td class="table_02_2">
                        <select name="p_lectsttime1">
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
                        <select name="p_lectsttime2">
                          <option value="00" selected>0분</option>    
                          <option value="05">5분</option>         
                          <option value="10">10분</option>          
                          <option value="15">15분</option>     
                          <option value="20">20분</option>     
                          <option value="25">25분</option>     
                          <option value="30">30분</option>     
                          <option value="35">35분</option>     
                          <option value="40">40분</option>    
                          <option value="45">45분</option>     
                          <option value="50">50분</option>     
                          <option value="55">55분</option>     
                        </select>
                  </td>                  
                </tr>
                <tr>                   
                  <td class="table_title" width="18%">강의시간</td>
                  <td class="table_02_2">총
                    <input name="p_lecttime" type="text" class="input" size="5">
                    분</td>
                  <td class="table_title">강사</td>
                  <td class="table_02_2">
                    <select name="p_tutorid">
<%
        for(i = 0; i < list1.size(); i++) {                
            OffSubjLectureData data  = (OffSubjLectureData)list1.get(i);   
            v_tutorid        = data.getTutorid();    
            v_tutorname      = data.getTutorname();  
%>
                      <option value="<%=v_tutorid%>"><%=v_tutorname%></option>
<%      } %>
                    </select>
                  </td>
                </tr>
                <tr> 
                  <td class="table_title">강좌명</td>
                  <td class="table_02_2" colspan="3">
                    <input name="p_sdesc" type="text" class="input" size="60">
                  </td>
                </tr>
              </table>
            <!----------------- 과정별 강좌정보 입력 끝 ----------------->




<br>


            <!----------------- 등록, 리스트, 닫기 버튼 시작 ----------------->
            <table cellspacing="0" cellpadding="0" >
              <tr> 
                  <td>
                  <a href="javascript:whenLectureInsert()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
				  <td width=8></td>
                  <td>
                  <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table>
            <!----------------- 등록, 리스트, 닫기 버튼 끝 ----------------->




            <br>




          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>



</form>
</body>
</html>
