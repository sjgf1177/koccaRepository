<%
//**********************************************************
//  1. 제      목: 회원가입 완료
//  2. 프로그램명 : zu_MemberJoinOk.jsp
//  3. 개      요: 회원가입 완료
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post" >
	<input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_resno" value="" />
	
	<!--타이틀부분//-->
	
<table>
<tr>
<td>
<table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/member/stitle_01.gif" alt="회원가입" /></td>
        <td class="h_road">Home &gt; 이용안내  &gt; <strong>회원가입</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td>
          
          <!--process-->
          <table width="672" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td height="34"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="/images/portal/homepage_renewal/member/step_04.gif" alt="가입완료" /></td>
                </tr>
              </table></td>
            </tr>
          </table>
          <!--process//-->
          
          </td>
        </tr>
        <tr>
          <td height="15"></td>
        </tr>
      </table>
      
     
      <table width="100%" border="0" cellspacing="10" cellpadding="0" id="join_last">
        <tr>
          <td align="right" valign="bottom" ><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="30" align="left"><b>한국콘텐츠아카데미 회원가입이 완료되었습니다.</b></td>
            </tr>
            <tr>
              <td><a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/homepage_renewal/member/btn_main.jpg" alt="메인페이지 바로가기" /></a> <a href="javascript:menuMainForward('1','/servlet/controller.homepage.SubMenuMainServlet?p_process=ONLINE_COURSE')"><img src="/images/portal/homepage_renewal/member/btn_online.jpg" alt="온라인과정 바로가기" /></a> <a href="javascript:menuMainForward('2','/servlet/controller.homepage.SubMenuMainServlet?p_process=OFFLINE_COURSE')"><img src="/images/portal/homepage_renewal/member/btn_offline.jpg" alt="오프라인과정 바로가기" /></a></td>              
            </tr>
          </table></td>
        </tr>
      </table>
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->