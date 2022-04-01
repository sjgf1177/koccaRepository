<%
// 05.12.06 이나연 추가
  // 공지사항
  //mmunityMsMenuBean beanMenuTyp1= new CommunityMsMenuBean();
  //rayList listMenuType1       = (ArrayList)beanMenuTyp1.selectleftCbxList(box.getString("p_cmuno"),"0");

  //자료실
  CommunityMsMenuBean beanMenuTyp1= new CommunityMsMenuBean();
  ArrayList listMenuType1       = (ArrayList)beanMenuTyp1.selectleftCbxList(box.getString("p_cmuno"),"2");

  //게시판
  CommunityMsMenuBean beanMenuTyp2= new CommunityMsMenuBean();
  ArrayList listMenuType2       = (ArrayList)beanMenuTyp2.selectleftCbxList(box.getString("p_cmuno"),"1");

  ArrayList toplistUser       = (ArrayList)request.getAttribute("listUser");
  DataBox dbox1btn=null;
  System.out.println("...................  ::::"+box.getString("p_cmuno"));
   

  //커뮤니티 타입정보
  String v_left_display_fg =( new CommunityCreateBean()).getSingleColumn(box.getString("p_cmuno"),"html_skin_fg");
  if(v_left_display_fg.equals("")) v_left_display_fg="1";
  String v_left_menu_color="bluecolor1";
  if("1".equals(v_left_display_fg)){v_left_menu_color="bluecolor1";}
  if("2".equals(v_left_display_fg)){v_left_menu_color="greencolor1";}
  if("3".equals(v_left_display_fg)){v_left_menu_color="pinkcolor1";}
  if("4".equals(v_left_display_fg)){v_left_menu_color="yellowcolor1";}
  if("5".equals(v_left_display_fg)){v_left_menu_color="violetcolor1";}

%>
<script language="JavaScript" type="text/JavaScript">
//개인터뮤니티 홈으로 이동
function uf_moveMyCommunity() {
    if(document.form1.p_static_cmuno.value=='') return;
    document.form1.action = "/servlet/controller.community.CommunityIndexServlet?p_cmuno="+document.form1.p_static_cmuno.value+"&p_static_cmuno="+document.form1.p_static_cmuno.value;
    document.form1.p_process.value = "selectmyindex";

    document.form1.submit();
}

//메뉴페이지로이동
function uf_moveBrdMenuList(menuno,brd_fg) {
    if(<%=toplistUser.size()%> <1){ alert('회원만의공간입니다');return;}

    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet?p_menuno="+menuno+"&p_brd_fg="+brd_fg;
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//설문조사페이지로이동
function uf_moveFrPollListPage() {
    if(<%=toplistUser.size()%> <1){ alert('회원만의공간입니다');return;}

    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet?p_brd_fg=4";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}
//메일전송페이지로이동
function uf_comSendMail(userid) {
   open_window('msPopNotice', '/servlet/controller.community.CommunityPopUpServlet?p_process=sendmailPage&p_static_cmuno=<%=box.getString("p_static_cmuno")%>&p_cmuno=<%=v_cmuno%>&p_parent_userid='+userid, 100, 100, 810, 480, 'no', 'no', 'no', 'no', 'no');
  
}

//메일전송페이지로이동
function uf_moveFrPollListPage() {
    if(<%=toplistUser.size()%> <1){ alert('회원만의공간입니다');return;}

    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet?p_brd_fg=4";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}


//일정등록페이지로이동
function uf_moveFrCalendarWritePage() {
    if(<%=toplistUser.size()%> <1){ alert('회원만의공간입니다');return;}
    document.form1.action = "/servlet/controller.community.CommunityFrCalendarServlet?p_if_flag=MM&p_brd_fg=5";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}
//-->
</script>
  <!-- 정보창 테이블 -->
  <table width="162" border="0" cellspacing="0" cellpadding="0">
   <tr> 
    <td height="50" align="center" valign="top" background="../../images/community/info_bg.jpg" class="tbl_bc">
     <table width="150" border="0" cellspacing="0" cellpadding="0">

<% if(toplistUser.size()<1){%>
      <tr><td align="center" class="tbl_bc"><strong><a href="javascript:uf_moveMyCommunity();"><%=( new CommunityCreateBean()).getSingleColumn(box.getString("p_cmuno"),"cmu_nm")%></a></strong></td></tr>
      <tr><td align="center" bgcolor="D9E7EE" class="tbl_bc">손님</td></tr>

<% } else {
    dbox1btn = (DataBox)toplistUser.get(0); 
   
    String v_btn_grade_kor_nm =dbox1btn.getString("d_grade_kor_nm");
 
 %>
      <tr><td align="center" class="tbl_bc"><strong><a href="javascript:uf_moveMyCommunity();"><%=( new CommunityCreateBean()).getSingleColumn(box.getString("p_cmuno"),"cmu_nm")%></a></strong></td></tr>
      <tr><td align="center" bgcolor="D9E7EE" class="tbl_bc"><%=v_btn_grade_kor_nm%></td></tr>
<%}%>
     </table></td>
    </tr>
    <tr><td height="10">&nbsp;</td></tr>
  </table>

  <!-- 나의커뮤니티 -->
  <table width="162" border="0" cellspacing="0" cellpadding="0">
   <tr><td><img src="/images/community/my_text.gif"></td></tr>
   <tr>
    <td><select name="p_static_cmuno" onchange="javascript:uf_moveMyCommunity();" style="width:150">
     <option >::내가 가입한 커뮤니티:: </option>
     <%=(new com.credu.community.CommunityIndexBean()).selectMyCuminity(s_userid,v_cmuno, s_grtype)%>
     </select>
    </td>
   </tr>
   <tr><td height="5"><img src="/images/community/my_line.gif"></td></tr>
   <tr><td height="10"></td></tr>
  </table>
  <!--좌측메뉴테이블-->
  
<table width="162" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="5" class="<%=v_left_menu_color%>"></td>
  </tr>
  <tr> 
    <td align="center" valign="top" class="<%=v_left_menu_color%>"> 
      <table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="19"><img src="../../images/community/left_bl_notice.gif"></td>
          <% if("0".equals(box.getString("p_brd_fg"))){%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveBrdMenuList(1,0);"><font color="FB0303"><strong>공지사항</strong></font></a></td>
          <% } else {%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveBrdMenuList(1,0);">공지사항</a></td>
          <% }%>
        </tr>
        <tr>
          <td colspan="2"><img src="../../images/community/left_line.gif"></td>
        </tr>
      </table>
      <table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="19"><img src="../../images/community/left_bl_board.gif"></td>
          <td width="131" class="leftmenu">게시판</td>
        </tr>
        <tr>
          <td colspan="2"><img src="../../images/community/left_line.gif"></td>
        </tr>
      </table>
      <table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="8"></td>
        </tr>
        <%
  if(toplistUser.size()>0){
      for(int i = 0; i < listMenuType1.size(); i++) {
          DataBox dboxMenuType1 = (DataBox)listMenuType1.get(i);
          String v_menunbsp="";
          for(int k=1;k<dboxMenuType1.getInt("d_lv");k++){
             v_menunbsp +="&nbsp;&nbsp;";
          }
          if("file".equals(dboxMenuType1.getString("d_directory_fg"))){
             if(dboxMenuType1.getInt("d_menuno")==box.getInt("p_menuno")){
%>
        <tr>
          <td class="leftmenu_down1"><%=v_menunbsp%><a href="javascript:uf_moveBrdMenuList(<%=dboxMenuType1.getString("d_menuno")%>,<%=dboxMenuType1.getString("d_brd_fg")%>);">- 
            <font color="FB0303"><strong><%=dboxMenuType1.getString("d_title")%></strong></font></a></td>
        </tr>
        <%           } else {%>
        <tr>
          <td class="leftmenu_down1"><%=v_menunbsp%><a href="javascript:uf_moveBrdMenuList(<%=dboxMenuType1.getString("d_menuno")%>,<%=dboxMenuType1.getString("d_brd_fg")%>);">- 
            <%=dboxMenuType1.getString("d_title")%></a></td>
        </tr>
        <%           }    
          } else {%>
        <tr>
          <td class="leftmenu_down1"><%=v_menunbsp%>- <%=dboxMenuType1.getString("d_title")%></td>
        </tr>
        <%
          }
      }
  }
%>
        <tr> 
          <td height="8"></td>
        </tr>
      </table>
      <table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="19"><img src="../../images/community/left_bl_data.gif"></td>
          <td width="131" class="leftmenu">자료실</td>
        </tr>
        <tr> 
          <td colspan="2"><img src="../../images/community/left_line.gif"></td>
        </tr>
      </table>
      <table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="8"></td>
        </tr>
        <%
  if(toplistUser.size()>0){
      for(int i = 0; i < listMenuType2.size(); i++) {
          DataBox dboxMenuType2 = (DataBox)listMenuType2.get(i);
          String v_menunbsp="";
          for(int k=1;k<dboxMenuType2.getInt("d_lv");k++){
             v_menunbsp +="&nbsp;&nbsp;";
          }

          if("file".equals(dboxMenuType2.getString("d_directory_fg"))){
             if(dboxMenuType2.getInt("d_menuno")==box.getInt("p_menuno")){
%>
        <tr>
          <td class="leftmenu_down1"><%=v_menunbsp%><a href="javascript:uf_moveBrdMenuList(<%=dboxMenuType2.getString("d_menuno")%>,<%=dboxMenuType2.getString("d_brd_fg")%>);">- 
            <font color="FB0303"><strong><%=dboxMenuType2.getString("d_title")%></strong></font></a></td>
        </tr>
        <%           } else {%>
        <tr>
          <td class="leftmenu_down1"><%=v_menunbsp%><a href="javascript:uf_moveBrdMenuList(<%=dboxMenuType2.getString("d_menuno")%>,<%=dboxMenuType2.getString("d_brd_fg")%>);">- 
            <%=dboxMenuType2.getString("d_title")%></a></td>
        </tr>
        <%           }    
          } else {%>
        <tr>
          <td class="leftmenu_down1"><%=v_menunbsp%>- <%=dboxMenuType2.getString("d_title")%></td>
        </tr>
        <%
          }
      }
   }
%>
        <tr> 
          <td height="8"></td>
        </tr>
      </table>
      <table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="19"><img src="../../images/community/left_bl_photo.gif"></td>
          <% if("3".equals(box.getString("p_brd_fg"))){%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveBrdMenuList(2,3);"><font color="FB0303"><strong>앨범</strong></font></a></td>
          <% } else {%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveBrdMenuList(2,3);">앨범</a></td>
          <% }%>
        </tr>
        <tr>
          <td colspan="2"><img src="../../images/community/left_line.gif"></td>
        </tr>
      </table>
      <table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="19"><img src="../../images/community/left_bl_poll.gif"></td>
          <% if("4".equals(box.getString("p_brd_fg"))){%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveFrPollListPage();"><font color="FB0303"><strong>설문조사</strong></font></a></td>
          <% } else {%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveFrPollListPage();">설문조사</a></td>
          <% }%>
        </tr>
        <tr>
          <td colspan="2"><img src="../../images/community/left_line.gif"></td>
        </tr>
      </table>

	  <!-- 05.12.05 이나연 일정삭제  -->
      <!--table width="150" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="19"><img src="../../images/community/left_bl_day.gif"></td>
          <% if("5".equals(box.getString("p_brd_fg"))){%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveFrCalendarWritePage();"><font color="FB0303"><strong>일정</strong></font></a></td>
          <% } else {%>
          <td width="131" class="leftmenu"><a href="javascript:uf_moveFrCalendarWritePage();">일정</a></td>
          <% }%>
        </tr>
      </table--></td>
  </tr>
</table> 
