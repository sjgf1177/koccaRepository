<%
//**********************************************************
//  1. 제      목: 회워탈퇴
//  2. 프로그램명 :zu_MemberWithdraw.jsp
//  3. 개      요: 회워탈퇴
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
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
   
	String v_userid   = box.getSession("userid");
%>


<%@ include file="/learn/user/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

//탈퇴신청
function doWithdraw(){
    var fm=document.form1;

    if( (fm.p_pwd.value) == ""){
        alert("비밀번호를 입력하세요");
        fm.p_pwd.focus();return;
    }

    //document.form1.target="_self";
    document.form1.p_process.value="MemberWithdrawOk";
    document.form1.action="/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.submit()
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post">
	<input type="hidden"	name="p_process"	value="" />
    <input type="hidden"	name="p_userid"	value="<%=v_userid %>" />
    
    	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	
	<table>
		<tr>
			<td>
			
			<!--타이틀부분//-->
	  <table width="672" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="30" colspan="2" class="h_road">&nbsp;</td>
        </tr>
        <tr>
          <td><img src="/images/portal/homepage_renewal//member/stitle_06.gif" alt="비밀번호변경" /></td>
          <td class="h_road">Home &gt; 이용안내  &gt; <strong>회원탈퇴
          </strong></td>
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
          <td id="s_stit"><img src="/images/portal/homepage_renewal/member/s_stitle_09.gif" alt="회원정보" /></td>
        </tr>
        <tr>
          <td>          </td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" id="join_form">
            <tr>
              <th class="tit_table01">아이디</th>
              <td class="tit_table02"><b><%=v_userid %></b></td>
            </tr>
            <tr>
              <th>비밀번호 *</th>
              <td class="table_right"><input name="p_pwd" type="password" class="inp_txt_01" id="p_pwd" size="20"  maxlength="20" style="width:130px; height:20px;" /></td>
            </tr>
            <tr>
              <th>탈퇴사유</th>
              <td height="110" class="table_right"><textarea name="p_leave_reason" cols="70" rows="10" class="bodyform" id="p_leave_reason" maxlength="100" style="width:505px; height:100px;"></textarea></td>
            </tr>

          </table></td>
        </tr>
        <tr>
          <td height="10">&nbsp;</td>
        </tr>
        <tr>
          <td height="30"><b>&middot; 회원탈퇴를 신청하시면 해당 아이디는 사용이 중지되므로 해당 아이디로는 재가입이 불가능합니다.</b><br />
            &middot; 회원탈퇴한 회원정보는 삭제되며, 기존 가입한 ID와 신규가입 ID간의 ID 중복방지와 게시글 관리를 위하여 성명과<br />
&nbsp;&nbsp;ID는 남습니다.<br />
&middot; 탈퇴 후 재가입은 가능하며, 탈퇴 전 삭제 및 파기됩니다.<br />
* 문의 : 한국콘텐츠아카데미 운영팀 TEL. 02-2161-0077</td>
        </tr>
        <tr>
        	<td height="30"></td>
        </tr>
        <tr>
        	<td id="btn"><a href="javascript:doWithdraw();"><img src="/images/portal/homepage_renewal/member/btn_leave.gif" alt="탈퇴신청" /></a></td>
        </tr>
    </table></td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
			
			<%}else{ %>


        <h2><img src="/images/portal/member/membership/h4_tit.gif" alt="회원탈퇴" class="fl_l" /><p class="category">Home &gt; 이용안내 &gt; <strong>회원탈퇴</strong></p></h2>

        <p><img src="/images/portal/member/membership/stit_m10.gif"  alt="회원탈퇴" /></p>
        <p class="mg_t15"></p>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
           <tr>
             <td bgcolor="#d4d9ee" style="border-top:1px solid #96a5e1;"><table width="100%" border="0" cellspacing="1" cellpadding="0">
               <tr>
                 <td width="125" class="chart001">아이디</td>
                 <td class="chart002"><strong><%=v_userid %></strong></td>
               </tr>
               <tr>
                 <td width="125" class="chart001">비밀번호 *</td>
                 <td class="chart002"><input name="p_pwd" type="password" class="inp_txt_01" id="p_pwd" size="20"  maxlength="20"/></td>
               </tr>
               <tr>
                 <td width="125" class="chart001">탈퇴사유</td>
                 <td class="chart002"><textarea name="p_leave_reason" cols="70" rows="10" class="bodyform" id="p_leave_reason" maxlength="100"></textarea>
                 </td>
               </tr>

             </table></td>
            </tr>
        </table>

         <p class="mg_t15 mg_l15 lh_17">· <strong>회원탈퇴를 신청하시면 해당 아이디는 사용이 중지되므로 해당 아이디로는 재가입이 불가능합니다.</strong><br />
          · 회원탈퇴한 회원정보는 삭제되며, 기존 가입한 ID와 신규가입 ID간의 ID 중복방지와 게시글 관리를 위하여 성명과 ID는 남습니다. <br />
          · 탈퇴후 재가입은 가능하며, 탈퇴 전 삭제 및 파기됩니다.<br />
          ※ 문의 : 한국콘텐츠아카데미 운영팀 Tel. 02-2161-0077 <br />
         </p>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
            <tr>
             <td height="50" align="center"><a href="javascript:doWithdraw();"><img src="/images/portal/member/membership/btn_out.gif" width="84" height="30" alt="탈퇴신청"/></a></td>
           </tr>
        </table>
        
        <%} %>
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->