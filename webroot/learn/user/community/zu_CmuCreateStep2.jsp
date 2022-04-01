<%
//**********************************************************
//  1. 제      목: 커뮤니티 만들기 
//  2. 프로그램명 : zu_CmuCreate.jsp
//  3. 개      요: 커뮤니티 새로 만들기.
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 이연정
//  7. 수      정: 2005.07.01 이연정
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

//커뮤니티 생성
function uf_insertOK() {
    document.form1.action = "/servlet/controller.community.CommunityCreateServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"   enctype = "multipart/form-data"  method = "post">
<input type = "hidden" name = "p_process"      value="">
<input type = "hidden" name = "p_cmu_nm"         value = "<%=box.getString("p_cmu_nm") %>">
<input type = "hidden" name = "p_in_method_fg"   value = "<%=box.getString("p_in_method_fg") %>">
<input type = "hidden" name = "p_search_fg"      value = "<%=box.getString("p_search_fg") %>">
<input type = "hidden" name = "p_data_passwd_fg" value = "<%=box.getString("p_data_passwd_fg") %>">
<input type = "hidden" name = "p_display_fg"     value = "<%=box.getString("p_display_fg") %>">
<input type = "hidden" name = "p_type_l"         value = "<%=box.getString("p_type_l") %>">
<input type = "hidden" name = "p_type_m"         value = "<%=box.getString("p_type_m") %>">
<input type = "hidden" name = "p_intro"          value = "<%=box.getString("p_intro") %>">


<table width="900" cellspacing="3" cellpadding="3">
 <tr>
  <td> 
   <table width="890" border="0" cellspacing="0" cellpadding="0">
    <tr><td align="right"><a href="index_c.htm" onFocus="blur()"><img src="../../images/community/sm_home.gif" border="0"></a></td></tr>
   </table>
   <!-- 맨바깥 테두리선 테이블 -->
   <table width="890" border="1" cellspacing="0" cellpadding="5"   style="border-collapse:collapse;" bordercolor="#B7BEC1">
    <tr>
     <td valign="top">
      <!-- 전체 백그라운드 넣는 테이블-->
      <table width="876" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <%if(s_grtype.equals("KOCCA")){%>
			<td valign="top" background="/images/community/back_total.jpg">
		<%}else if(s_grtype.equals("KGDI")){%>
			<td valign="top" background="/images/user/game/community/back_total.jpg"><%}%>
         <!-- top table -->
         <table width="876" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="168" valign="top"><a href="/servlet/controller.community.CommunityIndexServlet" onfocus="blur()">
					<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/top_img01.jpg" border="0"></a></td><%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/top_img01.jpg" border="0"></a></td><%}%>	
				<td width="708" valign="top">
					<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/topimg.jpg"></td>
					<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/topimg.jpg"></td><%}%>	
			</tr>
		</table>
         <!-- center table start -->
         <table width="876" border="0" cellpadding="0" cellspacing="0">
          <tr> 
           <td valign="top" style="padding-left:5px">
            <table width="860" border="0" cellspacing="0" cellpadding="0">
             <tr> 
              <!-- left -->
              <td width="174"  valign="top" background="../../images/community/bg_left.gif" style="padding-left:5px">
               <%@ include file="/learn/user/include/topCommunityA.jsp"%>
              </td>

              <!-- center -->
              <td width="686" rowspan="2" valign="top" background="../../images/community/bg_center.gif" > 
               <table width="686" border="0" cellspacing="0" cellpadding="0" >
                <tr><td><img src="../../images/community/center_top.gif"></td></tr>
               </table>
               <table width="686" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                 <td align="center" valign="top"> 
                  <!-- 커뮤니티main center start-->
                  <!-- 전체타이틀 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr><td height="1" bgcolor="CCCCCC"></td></tr>
                   <tr> 
                    <td>
                     <table width="407" border="0" cellspacing="0" cellpadding="0">
                      <tr><td width="407" valign="bottom" class="title">커뮤니티<img src="../../images/community/title_vline.gif" align="absbottom"><strong>커뮤니티 만들기 </strong></td></tr>
                     </table>
                    </td>
                   </tr>
                   <tr><td height="1" bgcolor="CCCCCC"></td></tr>
                   <tr><td height="15"></td></tr>
                  </table>
                  <!-- 커뮤니티스토리/레이아웃지정 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr> 
                    <td width="312"><img src="../../images/community/bl_02.gif"  ><strong>커버스토리 </strong></td>
                    <td width="340"><img src="../../images/community/bl_02.gif"  ><strong>레이아웃 지정</strong></td>
                   </tr>
                   <tr> 
                    <td height="5"></td>
                    <td height="5"></td>
                   </tr>
                   <tr> 
                    <td align="center"><img src="../../images/community/photo_back.jpg"></td>
                    <td valign="top">
                     <table width="309" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                      <tr>
                       <td bgcolor="#F9FAFB"><table width="296" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                         <td>&nbsp;</td>
                         <td>&nbsp;</td>
                        </tr>
                        <tr> 
                         <td width="152"><input type="radio" name="p_layout_fg" value="1" checked><strong>Layout 1</strong></td>
                         <td width="144"><input type="radio" name="p_layout_fg" value="2"><strong>Layout 2</strong></td>
                        </tr>
                        <tr> 
                         <td align="center"><img src="../../images/community/make_01.gif"></td>
                         <td align="center"><img src="../../images/community/make_02.gif"></td>
                        </tr>
                        <tr> 
                         <td>&nbsp;</td>
                         <td>&nbsp;</td>
                        </tr>
                        <tr>
                         <td>&nbsp;</td>
                         <td>&nbsp;</td>
                        </tr>
                       </table>
                      </td>
                     </tr>
                    </table>
                   </td>
                  </tr>
                  <tr> 
                   <td height="4"></td>
                   <td height="4"></td>
                  </tr>
                  <tr>
                   <td>
                    <table width="308" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                      <td width="95" valign="top"><img src="../../images/community/photo_text.gif"></td>
                      <td width="213"><div id="hdiv_MainBg"><input name="p_img_path" id="hFileMainBg" type="file" class="input" oncontextmenu="return false" onkeydown="return File_CancelKeyInput();" style="IME-MODE: disabled; HEIGHT: 22px" size="20" /></div>
                        <input name="hidMainBgPath" id="hidMainBgPath" type="hidden" />
                      </td>
                     </tr>
                    </table>
                   </td>
                   <td>&nbsp;</td>
                  </tr>
                  <tr> 
                   <td align="center"> </td>
                   <td>&nbsp;</td>
                  </tr>
                 </table>
                 <!-- 화면스킨선택 테이블 -->
                 <table width="652" border="0" cellspacing="0" cellpadding="0">
                  <tr><td><img src="../../images/community/bl_02.gif"  ><strong>화면 스킨을 선택해 주세요.</strong></td></tr>
                  <tr><td height="5"></td></tr>
                  <tr> 
                   <td align="center" valign="top"> 
                    <table width="630" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                     <tr> 
                       <td align="center" valign="top">
                        <table width="625" border="0" cellspacing="0" cellpadding="0">
                         <tr> 
                          <td width="21" valign="top"><input type="radio" name="p_html_skin_fg" value="1"  checked></td>
                          <td width="95"><img src="../../images/community/css1.jpg" align="absmiddle"></td>
                          <td width="21" valign="top"><input type="radio" name="p_html_skin_fg" value="2"></td>
                          <td width="93" valign="top"><img src="../../images/community/css2.jpg" width="79" height="89" align="absmiddle"></td>
                          <td width="22" valign="top"><input type="radio" name="p_html_skin_fg" value="3"></td>
                          <td width="93" valign="top"><img src="../../images/community/css3.jpg" align="absmiddle"></td>
                          <td width="20" valign="top"><input type="radio" name="p_html_skin_fg" value="4"></td>
                          <td width="91" valign="top"><img src="../../images/community/css4.jpg" width="79" height="89" align="absmiddle"></td>
                          <td width="22" valign="top"><input type="radio" name="p_html_skin_fg" value="5"></td>
                          <td width="147"><img src="../../images/community/css5.jpg" width="79" height="89" align="absmiddle"></td>
                         </tr>
                        </table>
                       </td>
                      </tr>
                     </table>
                    </td>
                   </tr>
                  </table>

                  <!-- 버튼 -->
                  <table width="652" border="0" cellspacing="0" cellpadding="0">
                   <tr><td height="5" align="right"></td></tr>
                   <tr> 
                    <td align="right"><a href="javascript:uf_insertOK();"><img src="../../images/user/button/btn_make.gif" border="0"></a> 
                       <a href="index_c.htm"><img src="../../images/user/button/btn_cancel.gif" border="0"></a> 
                    </td>
                   </tr>
                  </table>
                 </td>
                </tr>
                <tr><td height="10" align="center" valign="top"></td></tr>
               </table>






              </td>
             </tr>
             <tr><td height="100" valign="bottom" background="/images/community/bg_left2.gif" style="padding-left:5px">&nbsp; </td></tr>
            </table>
            <!-- footer table -->
           <table width="860" border="0" cellspacing="0" cellpadding="0">
			  <tr> 
				<td><img src="/images/community/footer_top.gif"></td>
			  </tr>
			  <tr> 
				<td height="28" align="center" bgcolor="F0F2F0">
					<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/footer_copy.gif">
					<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/footer_copy.gif"><%}%>
				</td>
			  </tr>
			</table>
           </td>
          </tr>
         </table>
        </td>
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
