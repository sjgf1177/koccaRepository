<%
//**********************************************************
//  1. 제      목: EDUCATION SUBJECT PAGE
//  2. 프로그램명: zu_Activity_Site.jsp
//  3. 개      요: My activity
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 7. 25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","04"); 

    String v_process = box.getString("p_process");
    String v_grcode     = "";
    String v_gubun      = "";
    String v_gubunname  = "";
    String v_menuid     = "";
    String v_menuname   = "";
    String v_gubun1 = "";
    int    v_cnt        = 0;
    int    v_gubuncnt   = 0;
    int    v_gubuntot   = 0;

    ArrayList list1 = (ArrayList)request.getAttribute("selectActivity");
    //ArrayList list2 = (ArrayList)request.getAttribute("memberView");
    DataBox dbox2 = (DataBox)request.getAttribute("memberView");
    ArrayList list3 = (ArrayList)request.getAttribute("boardCnt");

    DataBox dbox3 = (DataBox)list3.get(0);
    //DataBox dbox2 = (DataBox)list2.get(0);

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMystudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--
    //엑셀 출력
    function whenExcel() {
        window.self.name = "ActivitySite";
        open_window("openExcel","","10","10","900","660","no","no","no","no","yes");
        document.form1.target = "openExcel";
        document.form1.action='/servlet/controller.system.MenuCountServlet';
        document.form1.p_process.value = 'myActivityExcel';
        document.form1.submit();
    }
//-->
</SCRIPT>
<form name="form1" method="post" action="">
<input type="hidden" name="p_process" value="">
</form>
<!-- center start -->

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_studyact.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 나의 공부방 > 나의 학습활동</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="3"><img src="/images/user/game/mystudy/st_site_act_on.gif" name="Image1" width="101" height="31" id="Image1"><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" width="3" height="10"><a href="/servlet/controller.system.StudyCountServlet?p_process=myActivity"><img src="/images/user/game/mystudy/st_learn_act_off.gif" name="Image2" width="101" height="31" border="0" id="Image2" onMouseOver="MM_swapImage('Image1','','/images/user/game/mystudy/st_site_act_off.gif','Image2','','/images/user/game/mystudy/st_learn_act_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
  </tr>
  <tr>
    <td width="9" height="22" valign="top"><img src="/images/user/game/mystudy/g_box_corner01.gif" width="9" height="22"></td>
    <td width="702" valign="bottom" background="/images/user/game/mystudy/bg_studyact01.gif"><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" width="20" height="10"></td>
    <td width="9" valign="top"><div align="right"><img src="/images/user/game/mystudy/g_box_corner02.gif" width="9" height="24"></div></td>
  </tr>
  <tr>
    <td height="12" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3"><table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="102"><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" width="9" height="10"><img src="/images/user/game/mystudy/st_info_login.gif" width="77" height="59"><img src="/images/user/game/mystudy/st_v_dot_line.gif" width="16" height="59"></td>
          <td bgcolor="#E0EBE4"><table width="601" height="59" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="4" height="5"><img src="/images/user/game/mystudy/box_corner01.gif" width="4" height="5"></td>
                <td></td>
                <td width="4"><img src="/images/user/game/mystudy/box_corner02.gif" width="4" height="5"></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="92" height="29"><img src="/images/user/game/mystudy/st_siteact_info01.gif" width="74" height="12"></td>
                      <td width="180">: <%=FormatDate.getFormatDate(dbox2.getString("d_lgfirst"),"yyyy-MM-dd HH:mm")%></td>
                      <td width="86"><img src="/images/user/game/mystudy/st_siteact_info02.gif" width="66" height="15"></td>
                      <td width="235">: <%=dbox2.getString("d_lgcnt")%></td>
                    </tr>
                    <tr> 
                      <td height="2" colspan="4" bgcolor="#FFFFFF"></td>
                    </tr>
                    <tr> 
                      <td height="29"><img src="/images/user/game/mystudy/st_siteact_info03.gif" width="74" height="12"></td>
                      <td>: <%=FormatDate.getFormatDate(dbox2.getString("d_lglast"),"yyyy-MM-dd HH:mm")%></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td height="5"><img src="/images/user/game/mystudy/box_corner03.gif" width="4" height="5"></td>
                <td></td>
                <td><img src="/images/user/game/mystudy/box_corner04.gif" width="4" height="5"></td>
              </tr>
            </table></td>
        <td width="17" bgcolor="#FFFFFF"><div align="right"></div></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="20" colspan="3">&nbsp;</td>
  </tr>
</table>
<!-- 과정안내표 -->
<table width="720"  border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="341" background="/images/user/game/mystudy/bg_site_act.gif"><table width="341" height="27" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="5" height="27" align="center"  ><img src="/images/user/game/mystudy/st_siteact_left01.gif" width="5" height="27"></td>
          <td width="125" align="center"  ><img src="/images/user/game/mystudy/siteact_info_course.gif" width="125" height="19"></td>
          <td width="145" align="center"  ><img src="/images/user/game/mystudy/text_t_menu.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="61" align="center" ><img src="/images/user/game/mystudy/text_t_num.gif" ></td>
          <td width="5" align="center" ><img src="/images/user/game/mystudy/st_siteact_right01.gif" width="5" height="27"></td>
        </tr>
      </table></td>
    <td width="38" rowspan="9">&nbsp;</td>
    <td width="341" background="/images/user/game/mystudy/bg_site_act.gif"><table width="341" height="27" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="5" height="27" align="center"  ><img src="/images/user/game/mystudy/st_siteact_left01.gif" width="5" height="27"></td>
          <td width="125" align="center"  ><img src="/images/user/game/mystudy/siteact_info_apply.gif" width="125" height="19"></td>
          <td width="147" align="center"  ><img src="/images/user/game/mystudy/text_t_menu.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="59" align="center" ><img src="/images/user/game/mystudy/text_t_num.gif" ></td>
          <td width="5" align="center" ><img src="/images/user/game/mystudy/st_siteact_right01.gif" width="5" height="27"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><table width="341" border="0" cellspacing="0" cellpadding="0"><!--과정안내 시작-->    
<%
     int  j = 0;
      v_gubuntot = 0;
      for(int i = 0; i < list1.size(); i++) {
        DataBox dbox = (DataBox)list1.get(i);

        if(dbox.getString("d_gubun").equals("4")){

            v_gubun = dbox.getString("d_gubun");
            v_menuid = dbox.getString("d_menuid");
            v_menuname = dbox.getString("d_menuname");
            v_cnt = dbox.getInt("d_cnt");
            v_gubuncnt = dbox.getInt("d_gubuncnt");
            v_gubuntot += v_cnt;

            j++;
%>
     <%	if(j==1){%>
	            <tr> 
	                <td width="5">&nbsp;</td>
		            <td width="125" rowspan="<%=v_gubuncnt%>"><img src="/images/user/game/mystudy/act_img1.gif" width="97" height="92"></td>
		            <td width="6"><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
		            <td width="139" height="25" class="tbl_gleft"><%=v_menuname%></td>
		            <td width="1" rowspan="<%=v_gubuncnt%>">&nbsp;</td>
		            <td width="60" class="tbl_grc"><%=v_cnt%></td>
		            <td width="5" class="tbl_grc">&nbsp;</td>
		        </tr>
     <%	}else{%>
				<tr>
			        <td width="5">&nbsp;</td>
		            <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
		            <td class="tbl_gleft"><%=v_menuname%></td>
		            <td class="tbl_grc"><%=v_cnt%></td>
		            <td class="tbl_grc">&nbsp;</td>
	            </tr>
<%	 	}
	}
}%>    
        <tr class="dotlinebg"> 
          <td width="5" >&nbsp;</td>
          <td height="25" >&nbsp;</td>
          <td>&nbsp;</td>
          <td><div align="center"><img src="/images/user/game/mystudy/text_t_total.gif" width="19" height="11"></div></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif" width="1" height="8"></td>
          <td class="tbl_grc"><%=v_gubuntot%></td>
         <td class="tbl_grc">&nbsp;</td>
        </tr>
      </table></td><!--과정안내 끝-->
    <td width="341"><table width="341" border="0" cellspacing="0" cellpadding="0"><!--수강신청 시작-->
<%
      j = 0;
      for(int i = 0; i < list1.size(); i++) {
        DataBox dbox = (DataBox)list1.get(i);

        if(dbox.getString("d_gubun").equals("1")){

            v_gubun = dbox.getString("d_gubun");
            v_menuid = dbox.getString("d_menuid");
            v_menuname = dbox.getString("d_menuname");
            v_cnt = dbox.getInt("d_cnt");
            v_gubuncnt = dbox.getInt("d_gubuncnt");
            v_gubuntot += v_cnt;

            j++;
%>
      <%	if(j==1){%>
			<tr> 
	          <td width="5">&nbsp;</td>
	          <td width="125" rowspan="<%=v_gubuncnt%>"><img src="/images/user/game/mystudy/act_img2.gif" width="97" height="92"></td>
	          <td width="6"><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
	          <td width="139" class="tbl_gleft"><%=v_menuname%></td>
	          <td width="1" rowspan="<%=v_gubuncnt%>">&nbsp;</td>
	          <td width="60" class="tbl_grc"><%=v_cnt%></td>
	          <td width="5" class="tbl_grc">&nbsp;</td>
	        </tr>            
      <%	}else{%>
	        <tr> 
	          <td width="5">&nbsp;</td>
	          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
	          <td class="tbl_gleft"><%=v_menuname%></td>
	          <td class="tbl_grc"><%=v_cnt%></td>
	          <td class="tbl_grc">&nbsp;</td>
	        </tr>
<%		}
	}
}%>                                
        <tr class="dotlinebg"> 
          <td width="5" >&nbsp;</td>
          <td height="25" >&nbsp;</td>
          <td>&nbsp;</td>
          <td><div align="center"><img src="/images/user/game/mystudy/text_t_total.gif" width="19" height="11"></div></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif" width="1" height="8"></td>
          <td class="tbl_grc"><%=v_gubuntot%></td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
      </table></td><!--수강신청 끝-->
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td width="341">&nbsp;</td>
  </tr>
  <tr> 
    <td height="27" background="/images/user/game/mystudy/bg_site_act.gif"><table width="341" height="27" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="5" height="27" align="center"  ><img src="/images/user/game/mystudy/st_siteact_left01.gif" width="5" height="27"></td>
          <td width="125" align="center"  ><img src="/images/user/game/mystudy/siteact_info_myroom.gif" width="125" height="19"></td>
          <td width="145" align="center"  ><img src="/images/user/game/mystudy/text_t_menu.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="61" align="center" ><img src="/images/user/game/mystudy/text_t_num.gif" ></td>
          <td width="5" align="center" ><img src="/images/user/game/mystudy/st_siteact_right01.gif" width="5" height="27"></td>
        </tr>
      </table></td>
    <td width="341" height="27" background="/images/user/game/mystudy/bg_site_act.gif"><table width="341" height="27" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="5" height="27" align="center"  ><img src="/images/user/game/mystudy/st_siteact_left01.gif" width="5" height="27"></td>
          <td width="125" align="center"  ><img src="/images/user/game/mystudy/siteact_info_desk.gif" width="125" height="19"></td>
          <td width="145" align="center"  ><img src="/images/user/game/mystudy/text_t_menu.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="61" align="center" ><img src="/images/user/game/mystudy/text_t_num.gif" ></td>
          <td width="5" align="center" ><img src="/images/user/game/mystudy/st_siteact_right01.gif" width="5" height="27"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td width="341" valign="top"><table width="341" border="0" cellspacing="0" cellpadding="0"><!--나의공부방 시작-->
<%
      j = 0;
      v_gubuntot = 0;
      for(int i = 0; i < list1.size(); i++) {
        DataBox dbox = (DataBox)list1.get(i);

        if(dbox.getString("d_gubun").equals("2")){

            v_gubun = dbox.getString("d_gubun");
            v_menuid = dbox.getString("d_menuid");
            v_menuname = dbox.getString("d_menuname");
            v_cnt = dbox.getInt("d_cnt");
            v_gubuncnt = dbox.getInt("d_gubuncnt");
            v_gubuntot += v_cnt;

            j++;
%>
	<%	if(j==1){%>
			<tr> 
	          <td width="5">&nbsp;</td>
	          <td width="125" rowspan="<%=v_gubuncnt%>"><img src="/images/user/game/mystudy/act_img3.gif" width="97" height="92"></td>
	          <td width="6"><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
	          <td width="139" height="25" class="tbl_gleft"><%=v_menuname%></td>
	          <td width="1" rowspan="<%=v_gubuncnt%>">&nbsp;</td>
	          <td width="60" class="tbl_grc"><%=v_cnt%></td>
	          <td width="5" class="tbl_grc">&nbsp;</td>                               
	        </tr>
	<%	}else{%>
			<tr> 
	          <td width="5">&nbsp;</td>
	          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
	          <td class="tbl_gleft"><%=v_menuname%></td>
	          <td class="tbl_grc"><%=v_cnt%></td>
	          <td class="tbl_grc">&nbsp;</td>
	        </tr>	
<%		}
	}
}%>                                
        <tr class="dotlinebg"> 
          <td width="5" >&nbsp;</td>
          <td height="25" >&nbsp;</td>
          <td>&nbsp;</td>
          <td><div align="center"><img src="/images/user/game/mystudy/text_t_total.gif" width="19" height="11"></div></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif" width="1" height="8"></td>
          <td class="tbl_grc"><%=v_gubuntot%></td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
      </table></td><!--나의공부방 끝-->
    <td width="341" valign="top"><table width="341" border="0" cellspacing="0" cellpadding="0"><!--헬프데스크 시작-->    
<%
      j = 0;
      v_gubuntot = 0;
      for(int i = 0; i < list1.size(); i++) {
        DataBox dbox = (DataBox)list1.get(i);

        if(dbox.getString("d_gubun").equals("3")){

            v_gubun = dbox.getString("d_gubun");
            v_menuid = dbox.getString("d_menuid");
            v_menuname = dbox.getString("d_menuname");
            v_cnt = dbox.getInt("d_cnt");
            v_gubuncnt = dbox.getInt("d_gubuncnt");
            v_gubuntot += v_cnt;

            j++;
%>
<%		if(j==1){%>
				<tr> 
		          <td width="5">&nbsp;</td>
		          <td width="125" rowspan="<%=v_gubuncnt%>"><img src="/images/user/game/mystudy/act_img4.gif" width="97" height="92"></td>
		          <td width="6"><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
		          <td width="139" height="25" class="tbl_gleft"><%=v_menuname%></td>
		          <td width="1" rowspan="<%=v_gubuncnt%>">&nbsp;</td>
		          <td width="60" class="tbl_grc"><%=v_cnt%></td>
		          <td width="5" class="tbl_grc">&nbsp;</td>
		        </tr>
  
<%		}else{   %>
				<tr> 
		          <td width="5">&nbsp;</td>
		          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
		          <td class="tbl_gleft"><%=v_menuname%></td>
		          <td class="tbl_grc"><%=v_cnt%></td>
		          <td class="tbl_grc">&nbsp;</td>
		        </tr>
<%		}
	}
}%>
        <tr class="dotlinebg"> 
          <td width="5" >&nbsp;</td>
          <td height="25" >&nbsp;</td>
          <td>&nbsp;</td>
          <td><div align="center"><img src="/images/user/game/mystudy/text_t_total.gif" width="19" height="11"></div></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif" width="1" height="8"></td>
          <td class="tbl_grc"><%=v_gubuntot%></td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
      </table></td><!--헬프데스크 끝-->
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td width="341">&nbsp;</td>
  </tr>
  <tr> 
    <td height="27" background="/images/user/game/mystudy/bg_site_act.gif"><table width="341" height="27" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="5" height="27" align="center"  ><img src="/images/user/game/mystudy/st_siteact_left01.gif" width="5" height="27"></td>
          <td width="125" align="center"  ><img src="/images/user/game/mystudy/siteact_info_booksale.gif" width="125" height="19"></td>
          <td width="145" align="center"  ><img src="/images/user/game/mystudy/text_t_menu.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="61" align="center" ><img src="/images/user/game/mystudy/text_t_num.gif" ></td>
          <td width="5" align="center" ><img src="/images/user/game/mystudy/st_siteact_right01.gif" width="5" height="27"></td>
        </tr>
      </table></td>
    <td width="341" background="/images/user/game/mystudy/bg_site_act.gif"><table width="341" height="27" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="5" height="27" align="center"  ><img src="/images/user/game/mystudy/st_siteact_left01.gif" width="5" height="27"></td>
          <td width="125" align="center"  ><img src="/images/user/game/mystudy/siteact_info_service.gif" width="125" height="19"></td>
          <td width="145" align="center"  ><img src="/images/user/game/mystudy/text_t_menu.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
          <td width="61" align="center" ><img src="/images/user/game/mystudy/text_t_num.gif" ></td>
          <td width="5" align="center" ><img src="/images/user/game/mystudy/st_siteact_right01.gif" width="5" height="27"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="top"><table width="341" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="5">&nbsp;</td>
          <td width="125" rowspan="6"><img src="/images/user/game/mystudy/act_img5.gif" width="97" height="92"></td>
          <td width="6"><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
          <td width="139" height="25" class="tbl_gleft">&nbsp;</td>
          <td width="1" rowspan="6">&nbsp;</td>
          <td width="60" class="tbl_grc">&nbsp;</td>
          <td width="5" class="tbl_grc">&nbsp;</td>
        </tr>
        <tr> 
          <td width="5">&nbsp;</td>
          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
          <td class="tbl_gleft">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
        <tr> 
          <td width="5">&nbsp;</td>
          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
          <td class="tbl_gleft">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
        <tr> 
          <td width="5">&nbsp;</td>
          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
          <td class="tbl_gleft">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
        <tr> 
          <td width="5">&nbsp;</td>
          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
          <td class="tbl_gleft">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
        <tr> 
          <td width="5">&nbsp;</td>
          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
          <td class="tbl_gleft">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
        <tr class="dotlinebg"> 
          <td width="5" >&nbsp;</td>
          <td height="25" >&nbsp;</td>
          <td>&nbsp;</td>
          <td><div align="center"><img src="/images/user/game/mystudy/text_t_total.gif" width="19" height="11"></div></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif" width="1" height="8"></td>
          <td class="tbl_grc">&nbsp;</td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
      </table></td>
	  <td width="341" valign="top"><table width="341" border="0" cellspacing="0" cellpadding="0"><!--Service 시작-->    
<%
      j = 0;
      v_gubuntot = 0;
      for(int i = 0; i < list1.size(); i++) {
        DataBox dbox = (DataBox)list1.get(i);

        if(dbox.getString("d_gubun").equals("5")){

            v_gubun = dbox.getString("d_gubun");
            v_menuid = dbox.getString("d_menuid");
            v_menuname = dbox.getString("d_menuname");
            v_cnt = dbox.getInt("d_cnt");
            v_gubuncnt = dbox.getInt("d_gubuncnt");
            v_gubuntot += v_cnt;

            j++;
%>
<%		if(j==1){%>
				<tr> 
		          <td width="5">&nbsp;</td>
		          <td width="125" rowspan="<%=v_gubuncnt%>"><img src="/images/user/game/mystudy/act_img6.gif" width="97" height="92"></td>
		          <td width="6"><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
		          <td width="139" height="25" class="tbl_gleft"><%=v_menuname%></td>
		          <td width="1" rowspan="<%=v_gubuncnt%>">&nbsp;</td>
		          <td width="60" class="tbl_grc"><%=v_cnt%></td>
		          <td width="5" class="tbl_grc">&nbsp;</td>
		        </tr>
  
<%		}else{   %>
				<tr> 
		          <td width="5">&nbsp;</td>
		          <td><img src="/images/user/game/mystudy/bl_siteact.gif" width="6" height="5"></td>
		          <td class="tbl_gleft"><%=v_menuname%></td>
		          <td class="tbl_grc"><%=v_cnt%></td>
		          <td class="tbl_grc">&nbsp;</td>
		        </tr>
<%		}
	}
}%>
        <tr class="dotlinebg"> 
          <td width="5" >&nbsp;</td>
          <td height="25" >&nbsp;</td>
          <td>&nbsp;</td>
          <td><div align="center"><img src="/images/user/game/mystudy/text_t_total.gif" width="19" height="11"></div></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif" width="1" height="8"></td>
          <td class="tbl_grc"><%=v_gubuntot%></td>
          <td class="tbl_grc">&nbsp;</td>
        </tr>
      </table></td><!--Service 끝-->
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td width="341">&nbsp;</td>
  </tr>
</table>
    
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
