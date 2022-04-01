<%
/**
 * file name : za_SulmunRegistQuestion_I2.jsp
 * date      : 
 * programmer:
 * function  : 설문 문항관리 - 디테일페이지
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    String  v_gubun = box.getStringDefault("p_gubun", SulmunRegistBean.DEFAULT_SUBJ);
    String  v_grcode    = box.getString("p_grcode");        //교육주관
	String v_gubunnm = "";
	    v_gubunnm = "가입경로";
	
    String  v_subj = box.getString("p_subj");
    String  v_sultype = box.getString("p_sultype");
    
    ArrayList  list = (ArrayList)request.getAttribute("ScaleQuestionExampleData");
   
    String v_reloadlist = box.getString("p_reloadlist");
	int v_scalecode = Integer.parseInt(box.getString("p_scalecode"));

%>
<html>
<head>
<title>설문문제관리-등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function InsertQuestion() {
  ActionPage('SulmunQuestionInsert');
}
function ActionPage(p_process) {
     if(!chkDataC()) {
         return;
     }
  document.form2.p_process.value = p_process;
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}

function chkDataC() {
  if (blankCheck(document.form2.p_sultext.value)) {
    document.form2.p_sultext.focus();
    alert('설문문제명을  입력하십시요.');
    return false;
  }

  var v_selcount = 0;
  var v_seltextcnt = 0;

	if (1 > document.form2.p_scalecode5.value || document.form2.p_scalecode5.value=="") {
      alert('척도을 선택해 주세요,');
      return false;	 
	 }    
	document.form2.p_selcount5.value = "5";

  return true;
}

<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>

function changeScale(flag) {

  if(flag == 1){
  var v_scalecode = document.form2.p_scalecode5.options[document.form2.p_scalecode5.selectedIndex].value;
  }else{
  var v_scalecode = document.form2.p_scalecode6.options[document.form2.p_scalecode6.selectedIndex].value;
  }
	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunRegistQuestionServlet";
		document.form2.p_process.value = "SulmunQuestionInsertS";
		document.form2.p_scalecode.value = v_scalecode;
		document.form2.submit();
 
  }
function changeScaleC() {

  var v_scalecode = document.form2.p_scalecode5.options[document.form2.p_scalecode5.selectedIndex].value;
  if(v_scalecode > 0){

	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunRegistQuestionServlet";
		document.form2.p_process.value = "SulmunQuestionInsertS";
		document.form2.p_scalecode.value = v_scalecode;
		document.form2.p_sultype.value = "5";
		document.form2.submit();
  }
  }
-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
	
     
	
     
	<!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/tit_pop_sulmunman.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	        <!----------------- title 끝 ----------------->
     
     
     
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
		 <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" height="20" class=sub_title>
		  <%=v_gubunnm%> 신규문제 등록</td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunRegistQuestionServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
        <input type="hidden" name="p_scalecode"  value="<%=v_scalecode%>">
        <input type="hidden" name="p_reloadlist"  value="">
        <input type="hidden" name="p_sulreturn"  value="Y">
        <input type="hidden" name="p_sultype"  value="<%=v_sultype%>">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>

    	<tr>
          <td class="table_title">문제</td>
          <td class="table_02_1" valign="top" colspan="3">
            <textarea name="p_sultext" cols="60" rows="12" class="input"><%=box.getString("p_sultext")%></textarea>
		  </td>
	   </table>

 
      <table cellspacing="1" cellpadding="5" class="table_out">
	    <input type="hidden" name="p_selmax5"  value="">
	    <input type="hidden" name="p_selcount5"  value="">		
		<tr>
    	  <td class="table_title">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("5", v_grcode, "S", "p_scalecode5", v_scalecode, "onchange=changeScaleC()")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 5; k++) {
       if (v_sultype.equals("5")){
         DataBox dbox = (DataBox)list.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>' readonly>
          </td>
          <td class="table_02_1"><%=dbox.getInt("d_selpoint")%>점
          </td>
          <input type="hidden" name="p_selpoint5"  value="<%=dbox.getInt("d_selpoint")%>">
		</tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='' readonly>
			 <input type="hidden" name="p_selpoint5"  value="0">
          </td>
          <td class="table_02_1">
          </td>
        </tr>
<%
      }
    }
%>
	  </table>


      </form>
	  <!----------------- 문제등록 끝 ----------------->
      <!----------------- 등록 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" >
        <tr>
          <td><a href="javascript:InsertQuestion()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</body>
</html>
