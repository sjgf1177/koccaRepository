<%
//**********************************************************
//  1. 제      목 : 학습자유의사항
//  2. 프로그램명 : zu_student_notice.jsp
//  3. 개      요 :
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 정상진 2005.07.8
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String v_subj     = box.getString("p_subj");
    String v_year     = box.getString("p_year");
    String v_subjseq  = box.getString("p_subjseq");

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function submit_check() {
        document.form1.p_process.value   = "firstSubj";
        document.form1.action            = "/servlet/controller.contents.EduStart";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<form name="form1" method="post">
  <input type='hidden' name='p_process'  value='firstSubj'>
  <input type='hidden' name='p_subj'     value='<%=v_subj%>'>
  <input type='hidden' name='p_year'     value='<%=v_year%>'>
  <input type='hidden' name='p_subjseq'  value='<%=v_subjseq%>'>


  <table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="550">
    <tr>
        <td>
            <table cellpadding="0" cellspacing="1" bgcolor="#bebebe" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" bgcolor="#FFFFFF" class="body_color">
                    <br>
<!-- 학습자유의사항 table  -->
            <table width="490" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
              <tr>
                <td align="center" valign="top"><img src="/images/user/common/stunotice_title.gif">
                </td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
              <tr valign="top">
                <td height="20" align="center"> <table width="480" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td valign="top">학습자님께서 수강하고 계시는 교육과정은 근로자직업훈련촉진법 제28조의
                        규정에 의하여 노동부로부터 <font color="#CC3300">직업능력개발훈련 과정으로 지정받은
                        교육과정</font>입니다.<br> <br>
                        본 과정의 교육비는 학습자님께서 소속된 회사에서 전액 부담하며,귀하가 수료를 필한 경우에 한해 노동부로부터
                        교육비를 지원 받을 수 있게 됩니다. <br>
                        학습자님께서 어떤한 사유에서든 미수료하시게 되는 경우 학습자님이 소속된 회사는 교육비를 지원 받을
                        수 없으며, 학습자님 또한 학점이수/급여공제등 불이익을 받게 됩니다. <br> <font color="#006699">따라서
                        학습자님꼐서는 다음 사항에 유의하여 학습에 임해 주시기 바랍니다.</font></td>
                    </tr>
                    <tr>
                      <td height="8" valign="top"></td>
                    </tr>
                    <tr >
                      <td height="5" background="/images/user/common/search_line-1.gif"></td>
                    </tr>
                    <tr >
                      <td height="8"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td height="10" align="center" valign="top"> <table width="477" border="0" cellpadding="0" cellspacing="0" background="/images/user/common/stu_box_bg.gif">
                    <tr>
                      <td valign="top"><img src="/images/user/common/stu_box_top.gif"></td>
                    </tr>
                    <tr>
                      <td height="10" align="center" valign="top" >- 다음 -</td>
                    </tr>
                    <tr>
                      <td height="8" valign="top" style="padding-left:13pt"></td>
                    </tr>
                    <tr>
                      <td height="10" valign="top" style="padding-left:13pt"><img src="/images/user/common/stu_bl.gif" align="absmiddle"><font color="#000000">일일
                        진도제한은 평일 전체 진도량의 20%, 토요일 및 법정공휴일은 전체 진도<br>
                        &nbsp;&nbsp;&nbsp;&nbsp; 량의 40% 까지 입니다.</font></td>
                    </tr>
                    <tr>
                      <td height="8" valign="top" style="padding-left:13pt"></td>
                    </tr>
                    <tr>
                      <td height="10" valign="top" style="padding-left:13pt"><img src="/images/user/common/stu_bl.gif"><font color="#000000">취득점수가
                        수료기준을 넘더라도 <font color="#CC3300">최종평가 미응시(0점)일경우 또는 과제물
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp; 미제출(0점)</font> 일때는 미수료 처리됩니다.</font></td>
                    </tr>
                    <tr>
                      <td height="8" valign="top" style="padding-left:13pt"></td>
                    </tr>
                    <tr>
                      <td height="10" valign="top" style="padding-left:13pt"><img src="/images/user/common/stu_bl.gif"><font color="#000000">평가는
                        문제Pool방식(3배수 이상)으로 과제물은 5개 유형으로 제출됩니다.</font></td>
                    </tr>
                    <tr>
                      <td height="8" valign="top" style="padding-left:13pt"></td>
                    </tr>
                    <tr>
                      <td height="10" valign="top" style="padding-left:13pt"><img src="/images/user/common/stu_bl.gif"><font color="#000000">과제물은
                        교육종료일 3일전까지 제출해주시기 바라며(권장사항), 교육종료일<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;까지 과제물 미제출시는 미수료 처리됩니다 <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;(훈련기관의 귀책의 경우 소속 회사에 손해배상을 하나,
                        미수료 처리를 번복하지 <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;않습니다)</font></td>
                    </tr>
                    <tr>
                      <td height="8" valign="top" style="padding-left:13pt"></td>
                    </tr>
                    <tr>
                      <td height="10" valign="top" style="padding-left:13pt"><img src="/images/user/common/stu_bl.gif"><font color="#000000">과제물
                        분량은 A4 기준 2장 이상 제출을 원칙으로하며, 부정한 방법으로 <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;동 일답안이 제출시 동일 답안 모두를 0점 처리하여 미수료
                        처리 합니다. </font></td>
                    </tr>
                    <tr >
                      <td><img src="/images/user/common/stu_box_bo.gif"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
              <tr>
                <td height="10" align="center">상기 학습자 유의사항에 동의 하십니까?</td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
              <tr align="right">
                <td height="20" align="center"><a href="javascript:submit_check()"><img src="/images/user/button/btn_agree_ok.gif" border="0"></a>
                  <a href="javascript:self.close()"><img src="/images/user/button/btn_agree_no.gif" border="0"></a>
                </td>
              </tr>
              <tr>
                <td height="10" align="right"><img src="/images/user/common/stu_logo.gif"></td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
            </table>

          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
