<%
/**
 * file name : za_ExamQuestionPreview.jsp
 * date      : 2003/09/01
 * programmer:
 * function  : 평가 문제 미리보기
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    String  v_grcode    = box.getString("p_grcode");        //교육주관

    String  v_subj    = box.getString("p_subj");

	String v_reloadlist = box.getString("p_reloadlist");

    ArrayList  list = (ArrayList)request.getAttribute("QuestionExampleData");
	DataBox dbox0 = null;
	if(list.size() > 0) {
		 dbox0 = (DataBox)list.get(0);
    } else {
	    dbox0 = new DataBox("resoponsebox");
	}

	int v_examnum = dbox0.getInt("d_examnum");
	String v_examtype = box.getStringDefault("p_examtype", dbox0.getString("d_examtype"));

    String v_urldir = "/upload/exam/";
%>
<html>
<head>
<title>평가 문제 미리보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function UpdateQuestion() {
  ActionPage('ExamQuestionUpdate');
}
function DeleteQuestion() {
  if (confirm("평가문제를 삭제 하시겠습니까?")) {
    ActionPage('ExamQuestionDelete');
  }
}
function ActionPage(p_process) {
  if (p_process == 'ExamQuestionDelete') {
    document.form2.action = "/servlet/controller.beta.BPExamQuestionServlet";
  } else {
    if(!chkData()) {
      return;
    }
    document.form2.action = "/servlet/controller.beta.BPExamQuestionServlet?p_file=1&p_motion=1&p_sound=1";
  }
  document.form2.p_process.value = p_process;
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
  var v_selcount = 0;
  var v_seltextcnt = 0;
  var v_test      = '';
  var v_isanswer = false;
  var i=1;
  for (i=1; i<=10; i++) {
    v_test = eval('document.form2.p_seltext'+i+'.value');
    if (!blankCheck(v_test)) {
      v_seltextcnt++;
    }
  }

    if (v_seltextcnt < 2) {
      document.form2.p_seltext1.focus();
      alert('문제 보기를 2개이상 입력하십시요.');
      return false;
    }

  var v_answercnt = 0;
  for (k=1; k<=10; k++) {
    v_isanswer = eval('document.form2.p_isanswer'+k+'.checked');
    if (v_isanswer) {
      v_test = eval('document.form2.p_seltext'+k+'.value');
      if (blankCheck(v_test) && (v_examtype=='1'||v_examtype=='3')) {
        document.form2.p_seltext1.focus();
        alert('정답을 선택한 보기가 없습니다.');
        return false;
      }
      v_answercnt++;
    }
  }

  /* 객관식 문제 */
  if (v_examtype == '1') {
    if (v_answercnt == 0) {
      document.form2.p_seltext1.focus();
      alert('정답여부를 선택해 주십시요.');
      return false;
    } else if (v_answercnt > 1) {
      document.form2.p_seltext1.focus();
      alert('정답여부를 1개만 선택해 주십시요.');
      return false;
    }
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
	v_selcount = v_seltextcnt;
	document.form2.p_selcount1.value = v_selcount;

  return true;
}
function display(){
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.beta.BPExamQuestionServlet";
        document.form2.p_process.value = 'ExamQuestionUpdatePage';
        document.form2.p_action.value = 'go';
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
function onload() {
  window.self.close();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" height="20" class=sub_title>
		  [<%=GetCodenm.get_subjnm(v_subj)%>] [<%=dbox0.getString("d_examnum")%>번] 문제 미리보기</td>
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
        <input type="hidden" name="p_img2"      value="<%=dbox0.getString("d_saveimage")%>">  
        <input type="hidden" name="p_audio2"     value="<%=dbox0.getString("d_saveaudio")%>">
        <input type="hidden" name="p_movie2"    value="<%=dbox0.getString("d_savemovie")%>"> 
        <input type="hidden" name="p_flash2"    value="<%=dbox0.getString("d_saveflash")%>"> 
        <input type="hidden" name="p_img3"      value="<%=dbox0.getString("d_realimage")%>">  
        <input type="hidden" name="p_audio3"     value="<%=dbox0.getString("d_realaudio")%>">
        <input type="hidden" name="p_movie3"    value="<%=dbox0.getString("d_realmovie")%>"> 
        <input type="hidden" name="p_flash3"    value="<%=dbox0.getString("d_realflash")%>"> 
        <input type="hidden" name="p_reloadlist" value="">

        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
<!-- 문제관련 이미지, 음성, 동영상 파일 시작 -->
<%      if (!dbox0.getString("d_imgurl").equals("")) { %> 
        <tr> 
          <td bgcolor="#FFFFFF"> 
            <table cellspacing="0" cellpadding="0" class="img_table_out">
              <tr> 
                <td> 
                  <table cellspacing="2" cellpadding="2">
                    <tr> 
                      <td class="img_table" height="26"><img src='<%=v_urldir+dbox0.getString("d_imgurl")%>'></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
<%      }  %>  
<%      if (!dbox0.getString("d_audiourl").equals("")) { %> 
        <tr> 
          <td class="board_text4"> <img src="/images/user/study/voice_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox0.getString("d_audiourl")%>"><%=dbox0.getString("d_realaudiourl")%></a></td>
        </tr>
<%      }  %>  
<%      if (!dbox0.getString("d_movieurl").equals("")) { %> 
        <tr> 
          <td class="board_text3"> <img src="/images/user/study/meida_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox0.getString("d_movieurl")%>"><%=dbox0.getString("d_realmovieurl")%></a></td>
        </tr>
<%      }  %>        
<!-- 문제관련 이미지, 음성, 동영상 파일 끝 -->
		<tr>
          <td class="table_title">문제분류</td>
          <td class="table_02_1"><%=dbox0.getString("d_examtypenm")%></td>
          <td class="table_title">난이도</td>
          <td class="table_02_1"><%=dbox0.getString("d_levelsnm")%></td>
		</tr>
		<tr>
          <td class="table_title">문제</td>
          <td class="table_02_1" valign="top" colspan="3"><%=dbox0.getString("d_examtext")%></td>
        </tr>
		<tr>
          <td class="table_title">문제해설</td>
          <td class="table_02_1" valign="top" colspan="3"><%=dbox0.getString("d_exptext")%></td>
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
    for (int k=1; k <= list.size(); k++) {
         DataBox dbox = (DataBox)list.get(k-1);   
%>
        <tr>
          <td class="table_01" width="15%"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>
          </td>
		  <input type="hidden" name="p_isanswer<%=String.valueOf(k)%>" value="<%=dbox.getString("d_isanswer")%>">
        </tr>
<%
 }
%>
        <tr>
          <td class="table_title">이미지파일</td>
          <td colspan="3" class="table_02_2" width="60%">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_img1" type="FILE" class="input" size="40">&nbsp;<%=dbox0.getString("d_realimgurl")%>
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
                  <input name="p_audio1" type="FILE" class="input" size="40">&nbsp;<%=dbox0.getString("d_realaudiourl")%>
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
                  <input name="p_movie1" type="FILE" class="input" size="40">&nbsp;<%=dbox0.getString("d_realmovieurl")%>
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
                  <input name="p_flash1" type="FILE" class="input" size="40">&nbsp;<%=dbox0.getString("d_realflashurl")%>
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
            <td align="right"><a href="javascript:history.go(-1)"><img src="/images/admin/button/btn_before.gif" border="0"></a></td>
			<td  width="8"></td>
			<td align="right"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
<form name="form3" method="post" action="/servlet/controller.beta.BPExamQuestionServlet">
  <input type="hidden" name="p_process"    value="">
  <input type="hidden" name="p_action"     value="">
  <input type="hidden" name="p_subj"       value="<%=v_subj%>">
  <input type="hidden" name="p_examnum"    value="<%=String.valueOf(v_examnum)%>">
</form>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>

</body>
</html>
