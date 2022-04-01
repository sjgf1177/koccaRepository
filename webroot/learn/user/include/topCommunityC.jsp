<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
//개인터뮤니티 홈으로 이동
function uf_moveMsMainPage() {

    document.form1.action = "/servlet/controller.community.CommunityMasterServlet";
    document.form1.p_process.value = "selectmsmainPage";
    document.form1.submit();
}

//회원가입 페이지로 이동
function uf_moveFrJoinPage() {

    document.form1.action = "/servlet/controller.community.CommunityFrJoinServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}


//개인정보변경 페이지로 이동
function uf_moveFrMemChangePage() {

    document.form1.action = "/servlet/controller.community.CommunityFrMemberServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}


//회원정보조회 페이지로 이동
function uf_moveFrMemListPage() {

    document.form1.action = "/servlet/controller.community.CommunityFrMemberServlet";
    document.form1.p_process.value = "moveMemInfoData";
    document.form1.submit();
}


//회원탈퇴 페이지로 이동
function uf_moveFrOutPage() {
    document.form1.action = "/servlet/controller.community.CommunityIndexServlet";
    document.form1.p_process.value = "movememout";
    document.form1.submit();
}



//회원초대 페이지로 이동
function uf_moveFrInvitationPage() {
   open_window('msPopInvitation', '/servlet/controller.community.CommunityFrInvitationServlet?p_process=movePage&p_static_cmuno=<%=box.getString("p_static_cmuno")%>&p_cmuno=<%=v_cmuno%>', 100, 100, 620, 600, 'no', 'no', 'no', 'no', 'no');
  
}

//-->
</script>
                              <table width="162" border="0" cellspacing="0" cellpadding="0">

                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
<%if(toplistUser.size()>0){
    if("01".equals(dbox1btn.getString("d_grade"))){
 %>
                                <tr> 
                                  <td><a href="javascript:uf_moveMsMainPage();"><img src="../../images/community/btn_master.gif" border="0"></a></td>
                                </tr>
<%  }
  }%>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table> 
                              <!-- 하단네비게이션 -->
                              <table width="162" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td colspan="2" valign="top"><img src="../../images/community/down_top.gif"></td>
                                </tr>
                                <tr> 
<%if(toplistUser.size()>0){
    if("01".equals(dbox1btn.getString("d_grade"))){
 %>
                                  <td width="80"><a href="javascript:alert('마스타는 탈퇴할수 없습니다.');"><img src="../../images/community/down_out.gif" width="80" height="18" border="0"></a></td>
<%  } else {%>
                                  <td width="80"><a href="javascript:uf_moveFrOutPage();"><img src="../../images/community/down_out.gif" width="80" height="18" border="0"></a></td>

<%  }%>
                                  <td width="82"><a href="javascript:uf_moveFrInvitationPage();"><img src="../../images/community/down_g.gif" border="0"></a></td>
                                </tr>
                                <tr> 
                                  <td><a href="javascript:uf_moveFrMemListPage();"><img src="../../images/community/down_mem.gif" border="0"></a></td>
                                  <td><a href="javascript:uf_moveFrMemChangePage();"><img src="../../images/community/down_per.gif" border="0"></a></td>
                                </tr>

<%} else {%>
                                  <td width="80"><a href="javascript:uf_moveFrJoinPage();"><img src="../../images/community/down_join.gif" width="80" height="18" border="0"></a></td>
                                  <td width="82"><a href="javascript:alert('회원만의 공간입니다.');"><img src="../../images/community/down_g.gif" border="0"></a></td>
                                </tr>
                                <tr> 
                                  <td><a href="javascript:alert('회원만의 공간입니다.');"><img src="../../images/community/down_mem.gif" border="0"></a></td>
                                  <td><a href="javascript:alert('회원만의 공간입니다.');"><img src="../../images/community/down_per.gif" border="0"></a></td>
                                </tr>

<%}%>

                                <tr> 
                                  <td height="7" colspan="2" valign="bottom"><img src="../../images/community/down_bo.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="10" colspan="2" ></td>
                                </tr>
                              </table>
