<%
/**
 * file name : za_Approval_L.jsp
 * date      :
 * programmer:
 * function  : 수강신청 승인 리스트 조회화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%
  RequestBox box = null;
  
  box = (RequestBox)request.getAttribute("requestbox");
  if (box == null) {
    box = RequestManager.getBox(request);
  }
  
  String v_index = box.getString("p_index");
  String v_rejectkind = box.getString("p_rejectkind");
  String v_rejectedreason = box.getString("p_rejectedreason");
  
  //DEFINED in relation to select START
  String  ss_grcode    = box.getString("s_grcode");           //교육주관
  String  ss_gyear     = box.getString("s_gyear");            //년도
  String  ss_grseq     = box.getString("s_grseq");            //교육차수
  String  ss_grseqnm   = box.getString("s_grseqnm");          //교육차수명
  String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
  String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
  String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
  String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
  String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
  String  ss_company   = box.getString("s_company");          //회사
  String  ss_selgubun  = box.getString("s_selgubun");
  String  ss_seltext   = box.getString("s_seltext");
  String  ss_seldept   = box.getString("s_seldept");
  String  ss_edustart  = box.getString("s_start");            //교육시작일
  String  ss_eduend    = box.getString("s_end");              //교육종료일
  
  String  ss_action    = box.getString("s_action");
  String  v_ongo      = "whenSelection('go')";
  int     i           =  0;
  //out.println(p_index);
  CodeData data = null;

  ArrayList list = null;
  list = (ArrayList)request.getAttribute("reasonlist");
%>
<html>
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript">
function changeReason(index){
  document.form1.p_rejreasoncd.value = document.form1.p_rejeccd[index].value;
  document.form1.p_rejreasonnm.value = document.form1.p_codenm[index].value;
  
  if(document.form1.p_rejeccd[index].value != "15"){
    document.forms[0].p_rejreasonnm.readOnly = true;
    document.form1.p_rejreasonnm.focus();
  }else{
    document.forms[0].p_rejreasonnm.readOnly = false;
  }
}

function inputReason(){
  if(chkSelected() < 1){
    alert('반려사유를 선택하여 주십시오!');
    return;
  }
  document.form1.action='/servlet/controller.propose.StudentManagerServlet';
  document.form1.p_process.value = 'saverejectreason';
  document.form1.submit();
}

function chkSelected() {
      var selectedcnt = 0;
      if(document.form1.all['p_rejeccd']) {
        if (document.form1.p_rejeccd.length > 0) {
          for (i=0; i<document.form1.p_rejeccd.length; i++) {
            if (document.form1.p_rejeccd[i].checked == true) {
              selectedcnt++;
            }
          }
        } else {
          if (document.form1.p_rejeccd.checked == true) {
            selectedcnt++;
          }
        }
      }
      return selectedcnt;
}
</script>
<body>
<form name="form1">
<%
    Vector v_checks  = box.getVector("p_checks");
    String v_schecks = "";
    int v_checkcnt = v_checks.size();
    for(i=0;i<v_checks.size();i++) {              
      
      v_schecks = (String)v_checks.elementAt(i);
      
      //if(v_schecks.indexOf(',') > 0){
      //  v_schecks = StringManager.substring(v_schecks, 0, v_schecks.indexOf(','));
      //}
%>
  <input type='hidden' name = 'p_checks' value='<%=v_schecks%>'>
<% }               
%>
<input type="hidden" name="p_process"    value="">
<input type="hidden" name="s_grcode"     value="<%=ss_grcode%>">
<input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">
<input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
<input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>">
<input type="hidden" name="s_upperclass" value="<%=ss_upperclass%>">
<input type="hidden" name="s_middleclass" value="<%=ss_middleclass%>">
<input type="hidden" name="s_subjseq"     value="<%=ss_subjseq%>">
<input type="hidden" name="s_company"     value="<%=ss_company%>">

<input type="hidden" name="p_subj"     value="">
<input type="hidden" name="p_subjseq"     value="">
<input type="hidden" name="p_year"     value="">

<input type="hidden" name="p_tagetcnt"     value="<%=v_checkcnt%>">

<input type="hidden" name="p_action"     value="go">

    <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
          <td class=dir_txt><b>*반려사유를 선택하세요</b></td>
        </tr>
        <tr>
          <td height="2"></td>
        </tr>
        <%
        if(list != null){
          for ( i=0; i<list.size(); i++ ) {
		  data = (CodeData)list.get(i);
		  
        %>
        <tr>
          <td height="20" >
            <input type="radio" name="p_rejeccd" value="<%=data.getCode()%>" onClick="javascript:changeReason('<%=i%>')" <%if(v_rejectkind.equals(data.getCode())){out.print("checked");}%>><%=data.getCodenm()%></td>
            <input type="hidden" name="p_codenm" value="<%=data.getCodenm()%>">
        </tr>
        <%}
        }
        %>
        
        <tr>
          <td height="20">
            <input name="p_rejreasoncd" type="hidden" size="40" value=''>
            <input name="p_rejreasonnm" type="text" size="40" class="input" maxlength="100" value='<%=v_rejectedreason%>'>
          </td>
        </tr>
        <tr>
          <td height="6">&nbsp;</td>
        </tr>
        <tr>
          <td align="center" ><a href="javascript:inputReason()"><img src="/images/admin/button/btn_save.gif" border="0"></a> &nbsp;&nbsp; <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
    </table>
</form>
</body>
</html>