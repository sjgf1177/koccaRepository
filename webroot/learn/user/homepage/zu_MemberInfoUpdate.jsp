<%
//**********************************************************
//  1. 제      목: 개인정보수정 페이지
//  2. 프로그램명 : zu_MemberInfoUpdate.jsp
//  3. 개      요: 개인정보수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 7. 27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");
    box.put("leftmenu","2");

    //개인정보
    ArrayList  list1 = (ArrayList)request.getAttribute("memberView");

    MemberInfoBean bean = new MemberInfoBean();

%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topHome.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

    // 개인정보 수정
    function update() {
        if (document.form1.p_pwd.value == ""){
            alert('비밀번호를 입력하세요.');
            return;
        }else if(document.form1.p_pwd.value != document.form1.p_re_pwd.value){
            alert('비밀번호와 비밀번호확인이 다릅니다.비밀번호를 다시 입력하세요.');
            document.form1.p_pwd.value ="";
            document.form1.p_re_pwd.value ="";
            document.form1.p_pwd.focus();
            return;
        }

        if (document.form1.p_deptnam.value == ""){
            alert('부서를 입력하세요.');
           document.form1.p_deptnam.focus();
            return;
        }
        if (document.form1.p_work_plcnm.value == ""){
            alert('근무지를 입력하세요.');
           document.form1.p_work_plcnm.focus();
            return;
        }
        if (document.form1.p_handphone.value == "" && document.form1.p_comptel.value == ""){
            alert('사내전화/핸드폰중 한개는 입력하여야 합니다.');
           document.form1.p_handphone.focus();
            return;
        }

        if (document.form1.p_addr.value == ""){
            alert('주소를 입력하세요.');
           document.form1.p_addr.focus();
            return;
        }
        if (document.form1.p_addr2.value == ""){
            alert('상세주소를 입력하세요.');
           document.form1.p_addr2.focus();
            return;
        }
        if (document.form1.p_email.value == ""){
            alert('e-mail을 입력하세요.');
           document.form1.p_email.focus();
            return;
        }

        form1.p_process.value   = "memberUpdate";
        form1.action            = "/servlet/controller.homepage.MemberInfoServlet";
        form1.submit();
    }

    // 입력 체크
    function input_chk(form1)  {
        var chk = (isNull(form1.elements["p_pwd"],"비밀번호"));
        return (chk);
    }

    // 우편번호검색
    function searchPost() {
        window.self.name = "PersonalSelect";
        open_window("openPost","","100","100","390","400","","","","yes","");
        document.form1.target = "openPost";
        document.form1.action='/servlet/controller.library.PostSearchServlet';
        document.form1.p_process.value = 'SearchPostOpenPage';
        document.form1.submit();

        document.form1.target = window.self.name;
    }

    function goMain() {
        form1.p_process.value   = "";
        form1.action            = "/servlet/controller.homepage.MainServlet";
        form1.submit();
    }
//-->
</SCRIPT>

<% for (int i=0; i<list1.size(); i++) {
      DataBox dbox = (DataBox)list1.get(i); %>

 <form name="form1" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'>
          <!-- center start -->

<table width="730" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="20" align="center" valign="top" class="location">HOME &gt; 개인정보수정</td>
  </tr>
  <tr>
    <td align="center" valign="top"> <table width="675" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/user/common/<%=tem_subimgpath%>/tit_personal_mod.jpg"></td>
        </tr>
        <tr>
          <td height="20"></td>
        </tr>
      </table>
      <table width="675" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="200" valign="top"> <table width="675" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor">
                <td height="3" colspan="2" class="line_color_com"></td>
              </tr>
              <tr>
                <td width="117" class="tbl_dbtit1">&nbsp;&nbsp; 아이디</td>
                <td width="538" class="tbl_bleft"> <%=s_userid%></td>
              </tr>
              <tr>
                <td class="tbl_dbtit2">&nbsp;&nbsp; 이름(한글)</td>
                <td class="tbl_bleft"> <%=dbox.getString("d_name")%></td>
              </tr>
              <tr>
                <td height="26" class="tbl_dbtit1"><font color="red" size="4">*</font> 비밀번호</td>
                <td height="26" class="tbl_bleft"> <input type="password" class="input" name="p_pwd" size="20" maxlength="20" value="<%=dbox.getString("d_pwd")%>">&nbsp;&nbsp; 초기비밀번호(11111)일 경우 비밀번호를 변경해주셔야
                </td>
              </tr>
              <tr>
                <td class="tbl_dbtit2"><font color="red" size="4">*</font> 비밀번호확인</td>
                <td class="tbl_bleft"> <input type="password" class="input" name="p_re_pwd" size="20" maxlength="20" value="<%=dbox.getString("d_pwd")%>">&nbsp;&nbsp; 사이트 이용이 가능합니다.</td>
              </tr>
              <tr>
                <td class="tbl_dbtit1">&nbsp;&nbsp; 회사</td>
                <td class="tbl_bleft"> <%=dbox.getString("d_compnm")%></td>
              </tr>
              <tr>
                <td class="tbl_dbtit2"><font color="red" size="4">*</font> 부서</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_deptnam" size="20" maxlength="40" value="<%=dbox.getString("d_deptnam")%>"></td>
              </tr>
              <tr>
                <td class="tbl_dbtit1">&nbsp;&nbsp; 직위</td>
                <td class="tbl_bleft"> <%=dbox.getString("d_jikwinm")%></td>
              </tr>
              <tr>
                <td class="tbl_dbtit2"><font color="red" size="4">*</font> 근무지</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_work_plcnm" size="20" maxlength="100" value="<%=dbox.getString("d_work_plcnm")%>"> </td>
              </tr>
              <tr>
                <td class="tbl_dbtit1"><font color="red" size="4">*</font> 사내전화</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_comptel" size="20" maxlength="20" value="<%=dbox.getString("d_comptel")%>">
                  내선 :
                  <input type="text" class="input" name="p_tel_line"  size="5" maxlength="4" value="<%=dbox.getString("d_tel_line")%>"></td>
              </tr>
              <tr>
                <td class="tbl_dbtit2">&nbsp;&nbsp; 핸드폰</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_handphone" size="20" maxlength="20" value="<%=dbox.getString("d_handphone")%>"></td>
              </tr>
              <tr>
                <td class="tbl_dbtit1">&nbsp;&nbsp; 집전화</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_hometel" size="20" maxlength="20" value="<%=dbox.getString("d_hometel")%>"></td>
              </tr>
              <tr>
                <td class="tbl_dbtit2"><font color="red" size="4">*</font> 우편번호</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_post1" size="3" maxlength="3" value="<%=dbox.getString("d_post1")%>">
                  -
                  <input type="text" class="input" name="p_post2"  size="3" maxlength="3" value="<%=dbox.getString("d_post2")%>">
                  &nbsp;<a href="javascript:searchPost()"><img src="/images/user/button/btn_postfind.gif" align="absmiddle" border="0"></a></td>
              </tr>
              <tr>
                <td class="tbl_dbtit1"><font color="red" size="4">*</font> 주소</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_addr" size="50" maxlength="100" value="<%=dbox.getString("d_addr")%>" readonly></td>
              </tr>
              <tr>
                <td class="tbl_dbtit2"><font color="red" size="4">*</font> 상세주소</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_addr2" size="50" maxlength="50" value="<%=dbox.getString("d_addr2")%>"></td>
              </tr>
              <tr>
                <td class="tbl_dbtit1"><font color="red" size="4">*</font> e-mail</td>
                <td class="tbl_bleft"> <input type="text" class="input" name="p_email" size="50" maxlength="50" value="<%=dbox.getString("d_email")%>"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="5" align="center" valign="top"></td>
  </tr>
  <tr>
    <td align="center" valign="top"><a href="javascript:update()"><img src="/images/user/button/btn_save.gif" border="0"></a>
      <a href="javascript:goMain()"><img src="/images/user/button/btn_cancel.gif"></a></td>
  </tr>
  <tr>
    <td align="center" valign="top">&nbsp;</td>
  </tr>
  <tr>
    <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
  </tr>
</table>
<%}%>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->