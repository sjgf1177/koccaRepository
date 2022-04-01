<%
//**********************************************************
//  1. 제      목: 아이디/비밀번호 찾기
//  2. 프로그램명 : ku_IdpwSearch.jsp
//  3. 개      요: 아이디/비밀번호 찾기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.02
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_name = box.getString("p_name_1");
    String v_pwd  = (String)request.getAttribute("pwd");
    String v_isOk = box.getString("p_isOk");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topMember.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

// ID 찾기
function searchId() {

    if(document.form1.p_name.value == ""){
        alert("이름을 입력하세요!");
        form1.p_name.focus();
        return;
    }

    if (document.form1.p_resno1.value=="") {
        alert("주민번호를 정확히 입력하세요.");
        form1.p_resno1.focus();
        return;
    }
    
    if (document.form1.p_resno2.value=="") {
        alert("주민번호를 정확히 입력하세요.");
        form1.p_resno2.focus();
        return;
    }


    document.form1.p_process.value = "lossidresult";
    document.form1.action = "/servlet/controller.homepage.KLoginServlet";
    document.form1.submit();
}

// 메인페이지로
function indexList() {
    document.form1.p_process.value = "";
    document.form1.action = "/servlet/controller.homepage.MainServlet";
    document.form1.submit();
}

//자동이동
function moveFocus(num,fromform,toform)
    {
        var str = fromform.value.length;
        if(str == num)
       toform.focus();
    }
// 숫자만 들어가게 만든다.
function onlyNumber()
{
    if(((event.keyCode<48)||(event.keyCode>57)))
    event.returnValue=false;
}

//-->
</SCRIPT>

 <form name="form1" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'>


    <!-- title -->
        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 회원 > 아이디/비밀번호찾기</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/member/<%=tem_subimgpath%>/tit_idpw.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="88" rowspan="3"><img src="/images/user/kocca/member/text_idpw.gif"></td>
                          </tr>
                          <tr> 
                            <td height="12"></td>
                          </tr>
                          <tr > 
                            <td height="1" colspan="2" background="/images/user/kocca/common/dot_bg_9.gif"></td>
                          </tr>
                          <tr > 
                            <td height="7" colspan="2" ></td>
                          </tr>
                        </table>
                        <!--게시판목록 -->
                        <table cellpadding="0" cellspacing="0" width="680">
                          <col width=50%>
                          <col width=7>
                          <col width=50%>
                          <tr> 
                            <td><img src="/images/user/kocca/member/id_pw_st01.gif" vspace="5"></td>
                            <td></td>
                            <td><img src="/images/user/kocca/member/id_pw_st02.gif" vspace="5"></td>
                          </tr>
                          <tr> 
                            <td style="border-top:5px solid #D8D2E0;border-bottom:5px solid #D8D2E0;border-left:5px solid #D8D2E0;"> 
                              <table cellpadding=0 cellspacing=0>
                                <tr> 
                                  <td><img src="/images/user/kocca/member/text_idpw_name.gif"></td>
                                  <td><input name="p_name" type="text" class="input3" style="width:175px"></td>
                                  <td></td>
                                </tr>
                                <tr> 
                                  <td><img src="/images/user/kocca/member/text_idpw_number.gif"></td>
                                  <td style="padding:5px 0px;">
                                      <input name="p_resno1" type="text" class="input3" style="width:74px" onkeyup="moveFocus(6,this,this.form.p_resno2);" OnKeypress="onlyNumber();">
                                      <img src="/images/user/kocca/member/idpw_dash.gif" hspace="5" align="absmiddle">
                                      <input name="p_resno2" type="text" class="input3" style="width:74px"></td>
                                  <td style="padding-left:25"><a href="javascript:searchId()"><img src="/images/user/kocca/button/btn_ok.gif" border="0"></a></td>
                                </tr>
                              </table>
                            </td>
                            <td style="border-top:5px solid #D8D2E0;border-bottom:5px solid #D8D2E0;"><img src="/images/user/kocca/member/idpw_hdot.gif"></td>
                            <td style="border-top:5px solid #D8D2E0;border-bottom:5px solid #D8D2E0;border-right:5px solid #D8D2E0;"> 
                              <table cellpadding=0 cellspacing=0>
                                <tr> 
                                  <td width="10%">&nbsp;</td>

<%                              if (!v_pwd.equals("")) {               %>
                                  <td><b><%=v_name%></b>님의 패스워드는 <b><%=v_pwd%></b> 입니다.</td>
<%                              } else  {                                %>
                                  <td>운영자에게 문의해주세요</td>
<%                              }                                        %>

<!--메일세팅후 변경        
<%                            if (StringManager.toInt(v_isOk) > 0) {               %>
                                <td>패스워드가 발송되었습니다.</td>
<%                            } else  {                                %>
                                <td>운영자에게 문의해주세요</td>
<%                            }                                        %>
-->

                                  <td width="22%" align="left" style="padding-left:25"><a href="javascript:indexList()"><img src="/images/user/kocca/button/btn_ok.gif "border="0"></a></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
