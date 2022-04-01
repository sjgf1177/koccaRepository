<%
//**********************************************************
//  1. 제      목: 임직원 조회
//  2. 프로그램명: zu_usercheck.jsp
//  3. 개      요: 가족회원 가입시 임직원 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005.2.15
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

DataBox dbox = (DataBox)request.getAttribute("userCheck");

//임직원이 맞다면 v_cnt 값은 1을 반환함.
int v_cnt = dbox.getInt("d_cnt");

if(v_cnt > 0){
%>
<script language="javascript">
//parent.form1.p_ischk.value = "0";
parent.regist_form.check_flag.value = "1";
parent.regist_form.hidden_staffid.value = "<%//=userid%>";
parent.regist_form.hidden_staffname.value = "<%//=hname%>";
alert("기아자동차 사내 임직원임이 확인되었습니다.");
</script>
<%
}
else{
%>
<script language="javascript">
//parent.form1.p_ischk.value = "1";
parent.regist_form.check_flag.value = "0";
alert("입력하신 정보에 해당하는 기아 임직원이 없습니다.\n\n확인후 다시 입력해 주세요.");
</script>
<%
}
%>


