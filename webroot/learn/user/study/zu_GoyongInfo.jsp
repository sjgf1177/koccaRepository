<%
//**********************************************************
//  1. 제      목: 학습창 메인화면 - 고용보험 안내페이지
//  2. 프로그램명: zu_GoyongInfo.jsp
//  3. 개      요: 학습창 메인화면 - 고용보험 안내페이지
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 1. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_subj      = box.getString("p_subj");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_year			= box.getString("p_year");
    
    String  v_office  = "";
    String v_museridnm = "운영자";
    SubjGongData data = (SubjGongData)request.getAttribute("selectGoyong");
    String  v_subjnm        = data.getSubjnm();
    int     v_studentlimit  = data.getStudentLimit();
    int     v_framecnt      = data.getFrameCnt();
    int     v_edutimes      = data.getEduTimes();
    String  v_musertel      = data.getMuserTel();
    String  v_comp			= data.getComp();
    
    if (v_comp.equals("0101")){
    	v_office = "서울지방노동사무소";
    } else {
    	v_office = "수원지방노동청(사무소)";
    }
    
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/user_style_study.css">
<script>
function doCancel() {
        self.close();
}

</script>
</head>
<body bgcolor="#FCE1D0" text="#000000" topmargin=0 leftmargin=0>
<form name="form1" method="post">
<table width="560" border="0" cellspacing="0" cellpadding="0" align=center>
  <tr>
    <td height="11"></td>
    <td height="11"></td>
    <td height="11"></td>
  </tr>
  <tr> 
    <td><img src="/images/user/study/notice_top_l.gif"></td>
    <td width="510"><img src="/images/user/myclass/notice_top_title.gif" alt="통신훈련 수강자 유의사항"></td>
      <td><img src="/images/user/study/notice_top_r.gif"></td>
  </tr>
  <tr> 
    <td background="/images/user/myclass/notice_l_bg.gif"></td>
    <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="4%" height="8"></td>
          <td width="92%" height="8"></td>
          <td width="4%" height="8"></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>귀하께서 수강 신청한 <span class=notice_pop><%=v_subjnm%></span> 훈련과정은 근로자직업훈련촉진법 제28조의 규정에 의하여 노동부 <%=v_office%>로부터 
            직업능력개발훈련과정으로지정 받은 훈련과정입니다.</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>본 과정의 훈련비는 귀하의 사업주가 전액 부담하며 근로자직업훈련촉진법시행규칙<br>
            제8조 및 소정의 수료기준에 의하여 수료한 경우 귀하가 소속해 있는 사업주는 <br>
           수료생 1인당 노동부로부터 고용보험기금에서 훈련비용을 지원받을 수 있습니다.<br>
           따라서 귀하께서는 다음사항에 유의하여 훈련에 임해주시기 바랍니다.</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="5%" valign="top">가.</td>
                <td width="95%">훈련세부일정에 따라 본인이 직접 훈련을 수강하여야 하며, 대리, 허위 수강등을<br>
                  하는 경우에는 노동부로부터 훈련비용의 지원을 받을 수 없음.</td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="top">나.</td>
                <td>평가는 반드시 참여하여야 하며, 지방노동관서의 장으로부터 지정받은 훈련기간이 종료하기 전까지 모든 평가에 
                  참여하여 수료기준에 도달한 경우에 한하여 귀하의 사업주는 노동부로부터 훈련비용의 지원을 받을 수 있음</td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td valign="top">다.</td>
                <td>학습보고서, 평가지 등의 대리, 허위 작성 기타 부정한 방법으로 수료한 경우 수료할 수 없으며 향후 귀하가 
                  소속되어 있는 사업주는 1년간 고용보험법 상의 직업능력개발훈련 비용진원을 받을 수 없게 될 수도 있음.</td>
              </tr>
            </table></td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td class= notice_pop>※ 본과정은 한차수에 <%=v_studentlimit%>명 정원이며 <%=v_framecnt%>프레임수/<%=v_edutimes%>시간으로 이루어져 있음</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td style=padding-left:18px>기타 훈련과정 진행일정 등 문의 사항이 있으신 경우 전화 <%=v_musertel%><br>
            (담당자:<%=v_museridnm%>)으로 문의하시기 바랍니다.</td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td background="/images/user/myclass/notice_r_bg.gif"></td>
  </tr>
  <tr> 
    <td><img src="/images/user/study/notice_bottom_l.gif"></td>
    <td background="/images/user/myclass/notice_bottom_bg.gif"></td>
    <td><img src="/images/user/myclass/notice_bottom_r.gif"></td>
  </tr>
  <tr> 
    <td height="35">&nbsp;</td>
    <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right"><img src="/images/user/button/btn_close.gif" alt="닫기" border="0" onclick="doCancel();" style="cursor:hand;"></td>
        </tr>
      </table></td>
    <td height="35">&nbsp;</td>
  </tr>
</table>
<br>
</form>
</body>
</html>
