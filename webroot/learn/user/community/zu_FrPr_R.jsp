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
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_cmuno = box.getString("p_cmuno");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  v_type_l     = box.getString("p_type_l");
    String  v_loc_fg     = box.getStringDefault("p_loc_fg","2");
    String  content      = box.getString("content");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);
    String  width = "630";
    String  height = "150";


    ArrayList list           = (ArrayList)request.getAttribute("list");
    DataBox dbox = (DataBox)list.get(0);
    content      = dbox.getString("d_hongbo_contents");


    //커뮤니티 타입정보
    String v_top_display_fg =( new CommunityCreateBean()).getSingleColumn(box.getString("p_cmuno"),"html_skin_fg");
    if(v_top_display_fg.equals("")) v_top_display_fg="1";
    String[] v_title_color={"","",""};
    if("1".equals(v_top_display_fg)){v_title_color[0]="line_color_blue";v_title_color[1]="tbl_dbtit1";v_title_color[2]="tbl_dbtit2";}
    if("2".equals(v_top_display_fg)){v_title_color[0]="line_color_green";v_title_color[1]="tbl_gtit1" ;v_title_color[2]="tbl_gtit2";}
    if("3".equals(v_top_display_fg)){v_title_color[0]="line_color_pink";v_title_color[1]="tbl_ptit1" ;v_title_color[2]="tbl_ptit2";}
    if("4".equals(v_top_display_fg)){v_title_color[0]="line_color_yellow";v_title_color[1]="tbl_ytit1" ;v_title_color[2]="tbl_ytit2";}
    if("5".equals(v_top_display_fg)){v_title_color[0]="line_color_violet";v_title_color[1]="tbl_vtit1" ;v_title_color[2]="tbl_vtit2";}

%>  



<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_community2.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--


//리스트페이지로 이동
function uf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityCategoryResultServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"   enctype = "multipart/form-data" method = "post">
<input type = "hidden" name = "p_process" value="">
<input type = "hidden" name = "p_dupchk" value="<%=box.getStringDefault("p_dupchk","N")%>">
<input type = "hidden" name = "p_cmuno" value="<%=dbox.getString("d_cmuno")%>">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_selact" value="">

<input type = "hidden" name = "p_loc_fg" value="<%=v_loc_fg%>">
<table width="900" cellspacing="3" cellpadding="3">
  <tr>
    <td><table width="890" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="right"><a href="/servlet/controller.community.CommunityIndexServlet" onFocus="blur()"><img src="/images/community/sm_home.gif" border="0"></a></td>
        </tr>
      </table>
      <!-- 맨바깥 테두리선 테이블 -->
    <table width="890" border="1" cellspacing="0" 
                         cellpadding="5"   style="border-collapse:collapse;" bordercolor="#B7BEC1">
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
                            <td width="174"  valign="top" background="/images/community/bg_left.gif" style="padding-left:5px"> 
                            <%@ include file="/learn/user/include/topCommunityB.jsp"%>
</td>

                            <!-- center -->
                            <td width="686" rowspan="2" align="center" valign="top" background="/images/community/bg_center.gif" > 
                              <table width="686" border="0" cellspacing="0" cellpadding="0" >
                                <tr> 
                                  <td><img src="/images/community/center_top.gif"></td>
                                </tr>
                              </table>
                              <table width="686" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="center" valign="top"> 
                                    <!-- 커뮤니티main center start-->
                                    <!-- 전체타이틀 -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="1" bgcolor="CCCCCC"></td>
                                      </tr>
                                      <tr> 
                                        <td><table width="243" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td valign="bottom" class="title">커뮤니티<img src="/images/community/title_vline.gif" align="absbottom"><strong>홍보방</strong></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr> 
                                        <td height="1" bgcolor="CCCCCC"></td>
                                      </tr>
                                      <tr> 
                                        <td height="10"></td>
                                      </tr>
                                    </table>
                                    <!-- 게시판보기 -->
                                    <table width="652" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                                      <tr  class="lcolor"> 
                                        <td height="3" colspan="4" class="<%=v_title_color[0]%>"></td>
                                      </tr>
                                      <tr> 
                                        <td width="94" class="<%=v_title_color[1]%>">커뮤니티명</td>
                                        <td class="tbl_gleft"><a href="#"><%=dbox.getString("d_cmu_nm")%></a></td>
                                        <td class="<%=v_title_color[1]%>">시샵이름</td>
                                        <td class="tbl_gleft"><%=dbox.getString("d_kor_name")%></td>
                                      </tr>
                                      <tr> 
                                        <td width="94" class="<%=v_title_color[2]%>">개설일자</td>
                                        <td width="303" class="tbl_gleft"><%=FormatDate.getFormatDate(dbox.getString("d_accept_dte"), "yyyy/MM/dd")%></td>
                                        <td width="84" class="<%=v_title_color[2]%>">회원수</td>
                                        <td width="135" class="tbl_gleft"><%=dbox.getString("d_member_cnt")%></td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4" valign="top" class="tbl_contents" >
                                        <!-- 홍보이미지나오는 테이블 -->
                                        <table width="590" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td><img src="<%=dbox.getString("d_hongbo_savefile")%>" width="230" height="200"></td>
                                            </tr>
                                          </table>
                                          <!-- 홍보글 나오는 테이블 -->
                                          <table width="590" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td>&nbsp;</td>
                                            </tr>
                                            <tr> 
                                              <td><%=content%></td>
                                            </tr>
                                          </table>
                                          <p>&nbsp; </p>
                                          </td>
                                      </tr>
                                    </table>
                                    <!-- 버튼 -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td align="right"> <a href="javascript:uf_listOK();"><img src="/images/user/button/btn_list.gif" border="0"></a></td>
                                      </tr>
                                    </table>
                                    
                                  </td>
                                </tr>
                                <tr>
                                  <td height="10" align="center" valign="top"></td>
                                </tr>
                              </table>
                              
                            </td>
                          </tr>
                          <tr>
                            <td height="100" valign="bottom" background="/images/community/bg_left2.gif" style="padding-left:5px"> 
                            <%@ include file="/learn/user/include/topCommunityC.jsp"%>
</td>
                          </tr>
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
						</table></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
     </table>
    </td>
  </tr>
</table>

</form>
</body>
</html>
