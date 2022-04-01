<%
//**********************************************************
//  1. 제      목: ID 찾기 결과
//  2. 프로그램명 : zu_FindId_R.jsp
//  3. 개      요: ID 찾기 결과
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>


<%@page import="java.util.List"%><jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_name   = box.getString("p_name");
    List<DataBox> useridlist = (List<DataBox>)request.getAttribute("userid");
    int listsize = useridlist.size();
    String v_userid = "";
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
	
	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
      
      <table>   
	      <tr>
	      	<td>
	      	
	      	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/member/stitle_02.gif" alt="아이디/패스워드 찾기" /></td>
        <td class="h_road">Home &gt; 이용안내  &gt; <strong>아이디/패스워드 찾기</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <!--타이틀부분//-->

      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td>
                 
          </td>
        </tr>
      </table>
      
     
      <table width="100%" border="0" cellspacing="0" cellpadding="0" id="results_id">
        <tr>
          <td align="right" valign="bottom"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="30" align="left"><span class="blue_txt"><b><%=v_name %></b></span> 회원님의 아이디는 <span class="blue_txt">
              <%for(int i = 0 ; i < listsize ; i ++){
            	  DataBox dbox = (DataBox)useridlist.get(i);
              	v_userid = dbox.getString("d_userid");
              %>              
              [<b><%=v_userid %></b>]
              <%} %>
              </span>입니다.<br />
                비밀번호를 모르실 경우 비밀번호 찾기를 이용하여 주시기 바랍니다.</td>
            </tr>
          </table></td>
        </tr>
      </table>
      <br /><div id="btn"><a href="javascript:mainmenu('990');"><img src="/images/portal/homepage_renewal/member/btn_login01.gif" alt="로그인" /></a></div></td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
	      	
	      	<%}else{ %>

    <h2><img src="/images/portal/member/membership/h3_tit.gif" alt="아이디/패스워드 찾기" class="fl_l" /><p class="category">Home &gt; 이용안내 &gt; <strong>아이디/패스워드 찾기</strong></p></h2>

    <p><img src="/images/portal/member/membership/stit_m6.gif"  width="101" height="13" alt="아이디 찾기 결과" /></p>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18 ">
            <tr>
             <td height="129" align="left" valign="top" background="/images/portal/member/membership/box_img_7.gif" style="padding-top:35px;padding-left:270px;background-repeat:no-repeat">
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <% if (v_userid.equals("")) {  %>
                       <tr>
                         <td height="45" valign="top" class="lh_17"><strong>입력하신 정보와 일치하는 아이디가 없습니다.</strong><br />
                         <strong>이름이나 주민등록번호를 확인하여 주십시오.</strong></td>
                       </tr>
                   <% } else {  %>
                        <tr>
                         <td height="45" valign="top" class="lh_17"><strong><%=v_name %></strong> 회원님의 아이디는  <strong><%=v_userid %></strong> 입니다.<br />
                           비밀번호를 모르실 경우 &quot;비밀번호 찾기&quot;를 이용하여 주시기 바랍니다.</td>
                       </tr>
                   <% } %>
                       <tr>
                         <td align="left"><a href="javascript:mainmenu('990');"><img src="/images/portal/member/membership/btn_login2.gif" width="85" height="29" alt="로그인"/></a></td>
                      </tr>                   
             </table></td>
           </tr>
     </table>
		<%} %>
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->