<%
/**
 * file name : za_Courseseq_U.jsp
 * date      : 2003/11/05
 * programmer: LeeSuMin.. icarus..
 * function  : 코스차수 수정화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //년도
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    //DEFINED in relation to select END
    String  v_grcode = box.getString("p_grcode");
    String  v_gyear  = box.getString("p_gyear");
    String  v_grseq  = box.getString("p_grseq");

    CourseData da = (CourseData)request.getAttribute("courseseqData");
    
%>
<html>
<head>
<title></title>
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

<script language = "javascript">
    function whenSubmit(){
        var result=false;

        result = dateChk();
        if(!result){
            alert('수강신청 기간을 정확히 입력하여 주십시오.');
            return;
        }

        result = dateChk2();
        if(!result){
            alert('교육 기간을 정확히 입력하여 주십시오.');
            return;
        }

        if (blankCheck(document.form1.p_gradscore.value) || !number_chk_noalert(document.form1.p_gradscore.value)) {
          alert('이수기준-평균점수가 잘못입력되었습니다.');
          document.form1.p_gradscore.focus();
          return false;
        }
        if (blankCheck(document.form1.p_canceldays.value) || !number_chk_noalert(document.form1.p_canceldays.value)) {
            alert('수강신청취소기간이 잘못입력되었습니다.');
            document.form1.p_canceldays.focus();
            return false;
        }

        document.form1.action='/servlet/controller.course.CourseServlet';
        document.form1.p_process.value = 'updateCourseseq';     
        document.form1.target = "_self";
        document.form1.submit();
    }
    
    function dateChk(){
        ff = document.form1;

        if(ff.p_propstart1.value != null){
            ff.p_propstart.value=make_date(ff.p_propstart1.value)+ff.p_propstart2.options[ff.p_propstart2.selectedIndex].value;
        }else   return false;
        
        if(ff.p_propend1.value != null){
            ff.p_propend.value=make_date(ff.p_propend1.value)+ff.p_propend2.options[ff.p_propend2.selectedIndex].value;
        }else   return false;
        
        return true;
    }

    function dateChk2(){
        ff = document.form1;

        if(ff.p_edustart1.value != null){
            ff.p_edustart.value=make_date(ff.p_edustart1.value)+ff.p_edustart2.options[ff.p_edustart2.selectedIndex].value;
        }else   return false;
        
        if(ff.p_eduend1.value != null){
            ff.p_eduend.value=make_date(ff.p_eduend1.value)+ff.p_eduend2.options[ff.p_eduend2.selectedIndex].value;
        }else   return false;
        
        return true;
    }

    
    //날자 입력폼 삭제
    function whenclick(caldate) {
        caldate.value = "";
    }

    $(document).ready(function(){
    	$("#p_propstart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
    	$("#p_propend1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
    	$("#p_edustart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
    	$("#p_eduend1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
    });
</script>   

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<center>
<table width="85%" border="0" cellspacing="0" cellpadding="0" height="663">
<form name = "form1" method = "post" action="/servlet/controller.course.GrseqServlet">
    <input type="hidden" name="s_grcode"        value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"         value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"         value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>"> <!--in relation to select-->
    <input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>"><!--in relation to select-->
    <input type="hidden" name="s_subjcourse"    value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"       value="<%=ss_subjseq%>">    <!--in relation to select-->
    <input type="hidden" name="p_grcode"        value="<%=v_grcode%>">
    <input type="hidden" name="p_gyear"         value="<%=v_gyear%>">
    <input type="hidden" name="p_grseq"         value="<%=v_grseq%>">

    <input type="hidden" name="p_process"   value="updateCourseseq">
    <input type="hidden" name="p_course"    value="<%=da.getCourse()%>">
    <input type="hidden" name="p_cyear"     value="<%=da.getCyear()%>">
    <input type="hidden" name="p_courseseq" value="<%=da.getCourseseq()%>">
  <tr>
    <td align="center" valign="top">
      <!----------------- 코스차수 정보 title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/course/c_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- 코스차수 정보 title 끝 ----------------->
      <br>
      <br>
      <!----------------- 코스차수 정보 테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="25%"><b>코스</b></td>
          <td class="table_02_2">[ <%=da.getCourse()%> ] <%=da.getCoursenm()%></td>
        </tr>
        <tr>
          <td class="table_title"><b>년도</b></td>
          <td class="table_02_2"><%=da.getCyear()%></td>
        </tr>
        <tr>
          <td class="table_title"><b>차수</b></td>
          <td class="table_02_2"><%=da.getCourseseq()%></td>
        </tr>
        <tr>
          <td class="table_title"><b>교육그룹</b></td>
          <td class="table_02_2">[<%=da.getGrcode()%>] <%=da.getGrcodenm()%></td>
        </tr>
        <tr>
          <td class="table_title"><b>교육차수</b></td>
          <td class="table_02_2"><%=da.getGrseq()%></td>
        </tr>
        <tr>
          <td class="table_title"><b>수강신청시작</b></td>
          <td class="table_02_2">
          <% if(da.getPropstart().length() == 10){ %>
            <input name="p_propstart1" id="p_propstart1" type="text" class="datepicker_input1" size="10" value="<%=FormatDate.getFormatDate(StringManager.substring(da.getPropstart(),0,8),"yyyy-MM-dd")%>">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_propstart2">
            <%=FormatDate.getDateOptions(0,23,Integer.parseInt(StringManager.substring(da.getPropstart(),8,10)) )%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
              <a href="javascript:whenclick(document.form1.p_propstart1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a> 
          <% }else{ %>  
            <input name="p_propstart1" id="p_propstart1" type="text" class="datepicker_input1" size="10" value="">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_propstart2">
            <%=FormatDate.getDateOptions(0,23,0)%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
              <a href="javascript:whenclick(document.form1.p_propstart1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a> 
          <% } %>              
            <input type="hidden" name="p_propstart">
           </td>
        </tr>
        <tr>
          <td class="table_title"><b>수강신청완료</b></td>
          <td class="table_02_2">
          <% if(da.getPropend().length() == 10){ %>
            <input name="p_propend1" id="p_propend1" type="text" class="datepicker_input1" size="10"value="<%=FormatDate.getFormatDate(StringManager.substring(da.getPropend(),0,8),"yyyy-MM-dd")%>">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_propend2">
            <%=FormatDate.getDateOptions(0,23,Integer.parseInt(StringManager.substring(da.getPropend(),8,10)) )%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
              <a href="javascript:whenclick(document.form1.p_propend1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a>
          <% }else{ %>   
            <input name="p_propend1" id="p_propend1" type="text" class="datepicker_input1" size="10" value="">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_propend2">
            <%=FormatDate.getDateOptions(0,23,23)%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
              <a href="javascript:whenclick(document.form1.p_propend1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a>
          <% } %>  
            <input type="hidden" name="p_propend">
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육시작</b></td>
          <td class="table_02_2">
          <% if(da.getEdustart().length() == 10){ %>
            <input name="p_edustart1" id="p_edustart1" type="text" class="datepicker_input1" size="10" value="<%=FormatDate.getFormatDate(StringManager.substring(da.getEdustart(),0,8),"yyyy-MM-dd")%>">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_edustart2">
            <%=FormatDate.getDateOptions(0,23,Integer.parseInt(StringManager.substring(da.getEdustart(),8,10)) )%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
              <a href="javascript:whenclick(document.form1.p_edustart1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a> 
          <% }else{ %>  
            <input name="p_edustart1" id="p_edustart1" type="text" class="datepicker_input1" size="10" value="">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_edustart2">
            <%=FormatDate.getDateOptions(0,23,0)%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
              <a href="javascript:whenclick(document.form1.p_edustart1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a> 
          <% } %>              
            <input type="hidden" name="p_edustart">
           </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육완료</b></td>
          <td class="table_02_2">
          <% if(da.getEduend().length() == 10){ %>
            <input name="p_eduend1" id="p_eduend1" type="text" class="datepicker_input1" size="10" value="<%=FormatDate.getFormatDate(StringManager.substring(da.getEduend(),0,8),"yyyy-MM-dd")%>">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_eduend2">
            <%=FormatDate.getDateOptions(0,23,Integer.parseInt(StringManager.substring(da.getEduend(),8,10)) )%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
              <a href="javascript:whenclick(document.form1.p_eduend1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a> 
          <% }else{ %>   
            <input name="p_eduend1" id="p_eduend1" type="text" class="datepicker_input1" size="10" value="">
            일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <select name="p_eduend2">
            <%=FormatDate.getDateOptions(0,23,23)%>
            </select>시&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
              <a href="javascript:whenclick(document.form1.p_eduend1)"> <img src="/images/admin/button/b_delete.gif" align="absmiddle" border="0"></a> 
          <% } %>  
            <input type="hidden" name="p_eduend">
          </td>
        </tr>
         <tr>
          <td class="table_title"><b>수강신청취소기간</b></td>
          <td class="table_02_2">학습시작일로부터<input type="text" class="input" name="p_canceldays" size="10" maxlength="3" value="<%=da.getCanceldays()%>">일</td>
        </tr>
        <tr>
          <td class="table_title"><b>이수기준-평균점수</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_gradscore" size="10" maxlength="3" value="<%=da.getGradscore()%>"></td>
        </tr>
        <tr>
          <td class="table_title"><b>수강료</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="7" value="<%=da.getBiyong()%>"> 원</td>
        </tr>
       </table>
      <!----------------- 코스차수 정보 테이블 끝 ----------------->
      <br>
      <!----------------- 저장/취소/닫기 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="center" height="20"><a href='javascript:whenSubmit()'><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;
            &nbsp;<a href="javascript:window.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 저장/취소/닫기 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</form>
</table>

</center>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
