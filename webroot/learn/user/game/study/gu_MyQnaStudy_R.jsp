<%
//**********************************************************
//  1. 제      목: 나의질문방 - 학습관련 상세보기
//  2. 프로그램명 : gu_MyQnaStudy_R.jsp
//  3. 개      요: 나의질문방
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","03"); 

    DataBox dbox        = (DataBox)request.getAttribute("MyQnaStudyViewPage");
       
    String v_inuserid   = dbox.getString("d_inuserid");
    String v_name       = dbox.getString("d_name");
    String v_indate     = dbox.getString("d_indate");
    String v_title      = dbox.getString("d_title");
    String v_contents   = dbox.getString("d_contents");
    //String v_cnt        = dbox.getString("d_cnt");

   	Vector v_realfileVector = null;
	Vector v_savefileVector = null;
	v_realfileVector    = (Vector)dbox.getObject("d_realfile");
	v_savefileVector    = (Vector)dbox.getObject("d_savefile");
	int    v_filecount = v_realfileVector.size();

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMyStudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

//-->
</script>



<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_myqurestion.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 나의 공부방 > 나의 질문방</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>

<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="101"><img src="/images/user/game/mystudy/tab_quest02_on.gif"></td>
	<td width="3"></td>
    <td width="101"><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaSiteListPage" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/game/mystudy/tab_quest01_on.gif',1)"><img src="/images/user/game/mystudy/tab_quest01.gif" name="Image37"></td>
    <td width="515"></td>
  </tr>
  <tr> 
    <td height="5" colspan="4"></td>
  </tr>
  <tr>
	<Td colspan="4"><img src="/images/user/game/mystudy/g_back.jpg"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="81" height="2" class="linecolor_board3"></td>
	<td width="639" height="2" class="linecolor_board4"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0" 
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#D6E5C5" frame="hsides" rules="rows" align="center">
 <tr> 
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_t_subject02.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td class="font_specialroom_list" colspan="7"><%=v_title%></td>
  </tr>
  <tr> 
    <td width="79" class="tbl_grc"><img src="/images/user/game/mystudy/text_t_writer.gif"></td>
    <td width="1"valign="bottom"><img src="/images/user/game/mystudy/vline.gif"></td>
    <td width="298" bgcolor="F8F7EF" class="tbl_bleft"><%=v_name%>(<%= v_inuserid %>)</td>
    <td width="1" valign="bottom"><img src="/images/user/game/mystudy/vline.gif"></td>
    <td width="82" class="tbl_grc"><img src="/images/user/game/mystudy/text_day.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td width="260" class="tbl_grc"><%= FormatDate.getFormatDate(v_indate, "yyyy-MM-dd") %></td>
  </tr>
  <tr> 
    <td colspan="7" class="tbl_contents"><%=v_contents%></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/mystudy/text_t_addfile.gif"></td>
    <td valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td colspan="5" class="tbl_gleft">
		<table width="630" height="20" border="0" cellpadding="0" cellspacing="0" align="center">
			
			  <!--td class="tbl_gleft04"-->
		<%		
			if( v_realfileVector != null ) { 
				
				for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
					String v_realfile = (String)v_realfileVector.elementAt(i);
					String v_savefile = (String)v_savefileVector.elementAt(i); 
			
					if(!v_realfile.equals("")) {  
		%>
			<tr bgcolor="#F5F8EF"><td class="tbl_gleft04"><a href ='/servlet/controller.library.DownloadServlet?p_savefile=<%=v_savefile%>&p_realfile=<%=v_realfile%>'> 
				<%=v_realfile%></a>&nbsp;</td>
			<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>> 
		<%			}else{%>
			 <tr bgcolor="#F5F8EF"><td class="tbl_gleft04">첨부된 파일이 없습니다 <td>
		<%			}
				}
			}
		%> 			
			</tr>
		</table>  
	 </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="1" colspan="2" class="linecolor_bg01"></td>
  </tr>
  <tr> 
    <td height="5" colspan="2"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="bottom"><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaStudyListPage"><img src="/images/user/game/button/btn_list.gif" border="0"></a></td>
  </tr>   
  <tr> 
    <td height="5" colspan="2" ></td>
  </tr><tr> 
    <td height="2" colspan="2" class="linecolor_board3"></td>
  </tr>
</table>


            
            
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->