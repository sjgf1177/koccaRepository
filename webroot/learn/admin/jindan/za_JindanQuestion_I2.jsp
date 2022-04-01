<%
/**
 * file name : za_ExamQuestion_I2.jsp
 * date      : 2003/09/01
 * programmer:
 * function  : 평가 문항관리 - 보기페이지
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode    = box.getString("p_grcode");        //교육주관

    String  v_subj    = box.getString("p_subj");
    int     v_examnum = box.getInt("p_examnum");

	String v_reloadlist = box.getString("p_reloadlist");
    String v_ctype    = box.getString("p_ctype");  // 콘텐츠타입		
%>
<html>
<head>
<title>평가 문제 등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function InsertQuestion() {
  ActionPage('ExamQuestionInsert');
}
function ActionPage(p_process) {
    if(!chkData()) {
      return;
    }
  document.form2.action = "/servlet/controller.exam.ExamQuestionServlet?p_file=1&p_motion=1&p_sound=1";
  document.form2.p_process.value = p_process;
  //document.form2.p_lesson.value = "01";
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}
function chkData() {
  if (blankCheck(document.form2.p_examtext.value)) {
    document.form2.p_examtext.focus();
    alert('문제를 입력하십시요.');
    return false;
  }
  
  var v_examtype = document.form2.p_examtype.value;
  var v_seltextcnt = 0;
  var v_test      = '';
  var v_isanswer = false;
  var i=1;
  for (i=1; i<=9; i++) {
    v_test = eval('document.form2.p_seltext'+i+'.value');
    if (!blankCheck(v_test)) {
      v_seltextcnt++;
    }
  }
  
    if (v_seltextcnt < 1) {
      document.form2.p_seltext1.focus();
      alert('인정 답안을 1개이상 입력하십시요.');
      return false;
    }

 
  for(i=0;i<document.form2.length;i++) {
    if (document.form2.elements[i].type=="checkbox") {
      if (document.form2.elements[i].checked==true) {
        document.form2.elements[i].value = 'Y';
      } else {
        document.form2.elements[i].value = 'N';
      }
    }
  }    
  return true;
}
function display(){
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.exam.ExamQuestionServlet";
        document.form2.p_process.value = 'ExamQuestionInsertPage';
		document.form2.submit();

        //farwindow.window.focus();
        document.form2.target = window.self.name;
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" height="20" class=sub_title>
		  [<%=GetCodenm.get_subjnm(v_subj)%>] 신규문제 등록</td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" enctype="multipart/form-data">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
        <input type="hidden" name="p_examnum"    value="<%=String.valueOf(v_examnum)%>">
        <input type="hidden" name="p_reloadlist" value="">

        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>

		<tr>
          <td class="table_title">차시</td>
          <td class="table_02_1">
          <%if(v_ctype.equals("Y")){%>
<SELECT name=p_lesson  > 
 <option value=001>001</option> 
 <option value=002>002</option> 
  </SELECT>   
          <%}else{%>
            <%=SelectBoxBean.get_SelectSubjLesson("p_lesson", "", v_subj)%>
          <%}%>            
          </td>
          <td class="table_title">문제분류</td>
          <td class="table_02_1">
		  <SELECT name="p_examtype" onchange="display();"> 
            <option value=1>객관식</option> 
            <option value=2>주관식</option> 
            <option value=3>OX식</option>                 
			<script>document.form2.p_examtype.value = <%=box.getString("p_examtype")%>;</script>
          </SELECT> 
          </td>
		</tr>
		<tr>
    	  <td class="table_title">난이도</td>
          <td class="table_02_1" colspan="3" align="left"><%=CodeConfigBean.getCodeGubunSelect(ExamBean.EXAM_LEVEL,"", 1, "p_levels", "", "", 0)%>
		  </td>
		</tr>
		<tr>
          <td class="table_title">문제</td>
          <td class="table_02_1" valign="top" colspan="3">
            <textarea name="p_examtext" cols="70" rows="7" class="input"></textarea>
		  </td>
        </tr>
		<tr>
          <td class="table_title">문제해설</td>
          <td class="table_02_1" valign="top" colspan="3">
            <textarea name="p_exptext" cols="70" rows="7" class="input"></textarea>
		  </td>
        </tr>
	   </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="examtype1" STYLE="display:">
	    <input type="hidden" name="p_selcount1"  value="">	
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">인정답안</td>
		</tr>
<%  
    for (int k=1; k <= 10; k++) {
%>
        <tr>
          <td class="table_01" width="15%"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext<%=String.valueOf(k)%>" type="text" class="input" size="60" value="">
          </td>
             <input type="hidden" name="p_isanswer<%=String.valueOf(k)%>" value="">
        </tr>
<% } %>
        <tr>
          <td class="table_title">이미지파일</td>
          <td colspan="3" class="table_02_2" width="60%">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_img1" type="FILE" class="input" size="40">&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title">오디오파일</td>
          <td colspan="3" class="table_02_2" width="60%">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_sound1" type="FILE" class="input" size="40">&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title">동영상파일</td>
          <td colspan="3" class="table_02_2" width="60%">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_motion1" type="FILE" class="input" size="40">&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title">플래시파일</td>
          <td colspan="3" class="table_02_2" width="60%">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_flash1" type="FILE" class="input" size="40">&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      </form>

	  <!----------------- 문제등록 끝 ----------------->
      <!----------------- 등록 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" >
        <tr>
          <td><a href="javascript:InsertQuestion()"><img src="/images/admin/button/btn_apply.gif" border="0"></a>
          		  <a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
<form name="form3" method="post" action="/servlet/controller.exam.ExamQuestionServlet">
  <input type="hidden" name="p_process"    value="">
  <input type="hidden" name="p_action"     value="">
  <input type="hidden" name="p_subj"       value="<%=v_subj%>">
  <input type="hidden" name="p_examnum"    value="<%=String.valueOf(v_examnum)%>">
</form>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>

</body>
</html>
