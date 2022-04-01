<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import="com.credu.homepage.LoginBean" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process  = box.getString("p_process");

    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");
    String topWarp_gadmin1 = box.getSession("gadmin1");

    if (topWarp_gadmin.equals("")) {
        box.setSession("gadmin", "ZZ");
        topWarp_gadmin = box.getSession("gadmin");
    }
    else if(!(topWarp_gadmin.equals("") || topWarp_gadmin.equals("ZZ")))
    {
        box.setSession("gadmin1", box.getSession("gadmin"));
        topWarp_gadmin1 = box.getSession("gadmin1");
    }

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    String gubun = box.getString("gubun");
//    if(gubun.equals("60") && s_username.equals(""))
//        out.print("<script> alert('회원 정보가 존재하지 않습니다.'); </script>");

    ArrayList<DataBox> goldclassList   = (ArrayList)request.getAttribute("goldclassList");
    ArrayList<DataBox> noticeList        = (ArrayList)request.getAttribute("noticeList");
    
    // 팝업 공지사항
    ArrayList<DataBox> noticeListPopup = (ArrayList) request.getAttribute("noticePopup");
    //System.out.println("noticeListPopup.size():::::::"+noticeListPopup.size());
%>
<style type="text/css">
    .box {font-family: "Arial";font-size: 12px;color: #666666;text-decoration: none;width:100px;height:14px;border: 1px solid #ffffff;}
    .box03 {font-family: "Arial";font-size: 12px;color: #666666;text-decoration: none;width:90px;height:17px;border:2px solid #e1e1e1;  background:#ffffff;}
    .bodyform {width:770px; height:500px; text-align:center;}
    .bodyform_1 {background:#FFFFFF; border:solid #bdcede; border-width:0 1px 1px 1px;}
    .bodyform1 {width:729px; text-align:center;padding:15px 13px 15px 15px; background:#FFFFFF;}
    body {background-repeat:repeat-x;background-color:#fff; text-align:center;}
    a:link		{ font-size:12px; color: #333;  text-decoration: none; }
    a:visited	{ font-size:12px; color: #333;  text-decoration: none; }
    a:active	{ font-size:12px; color: #333;  text-decoration: none; }
    a:hover		{ font-size:12px; color: #000;  text-decoration: none; }
    img {border:none;}
    
</style>
<script type="text/javascript">

	document.onload=popupView();
	//window.onload=popupView();

	    function ASP_login()
    {
        var r=$("[name='p_id']").val();
        if(r=="")
        {
            alert("아이디를 입력 하여 주십시오.");
            $("[name='p_id']").focus();
            return;
        }

        var r1=$("[name='p_pw']").val();
        if(r1=="")
        {
            alert("암호를 입력 하여 주십시오.");
            $("[name='p_pw']").focus();
            return;
        }

        mainmenu("5");
    }

 // 윈도우 팝업
    function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar=no,menubar=no,status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }

    function popupView() /* 팝업 관련 스크립트 */
    {
    var  width  = 0;
    var  height = 0;
    var  left   = 0;
    var  top    = 0;

    <%  for(int i=0; i < noticeListPopup.size(); i++){
            DataBox dbox =    noticeListPopup.get(i);  %>
      width  = <%=dbox.getString("d_popwidth")%>;
      height = <%=dbox.getString("d_popheight")%>;
      left   = <%=dbox.getString("d_popxpos")%>;
      top    = <%=dbox.getString("d_popypos")%>;
      url    = "/servlet/controller.homepage.MainServlet?p_process=popupview&p_seq=<%=dbox.getString("d_seq")%>&p_useframe=<%=dbox.getString("d_useframe")%>&p_popUpPreview=popUpPreview<%=dbox.getString("d_seq")%>";

        if ( notice_getCookie( "popUpPreview<%=dbox.getString("d_seq")%>" ) != "done" ){
          open_window("popUpPreview<%=dbox.getString("d_seq")%>",url,left,top,width,height, "no","no","no","yes","no");
        }
    <%  }  %>
        }

        function trhidden(obj,nowTrName,sw,classStr,allHideTr)
        {
            var obj_parents=$("."+allHideTr);
            $(".hiddenYesNo",obj_parents).hide();
//            $("[name='"+nowTrName+"']").attr("class","");

            if(sw=="t")
            {
                $(".hiddenYesNo",obj).show();
//                $(obj).attr("class",classStr);
            }
        }
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    <table width="960" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td>


        <!--메인영역 시작--->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr><td width="20">&nbsp;</td>
            <td height="358" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="217"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="/images/asp/type2/login_tit.gif" alt="회원로그인" width="217" height="84" /></td>
                  </tr>
                  <tr>
                    <td height="69" valign="top" background="/images/asp/type2/login_bgbg.gif">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <% String imgUrl="background='/images/asp/type2/login_img_02.gif'"; if(!s_userid.equals("")) {imgUrl=""; }%>
                        <td width="46" <%=imgUrl%>>&nbsp;</td>
                        <td width="97" valign="top">
                            <% if(s_userid.equals("")) { %>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>
                                <input name="p_id" type="text" class="box03"  />
                            </td>
                          </tr>
                           <tr>
                            <td><input name="p_pw" type="password" class="box03" /></td>
                          </tr>
                         </table>
                        <%} else {%>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><%=s_username%>님,</td>
                          </tr>
                           <tr>
                            <td>환영 합니다.
                            <% if(!(topWarp_gadmin1.equals("ZZ") || topWarp_gadmin1.equals(""))) {%>
                                <%=LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") %>
                                <%}%>
                            <% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
                                <input type="image" src="/images/portal/btn/btn_top_go.gif" alt="go" class="va_m" onClick='topAdminOpenPage()'/>
                            <% }   %>
                            </td>
                          </tr>
                         </table>
                        <%}%>
                        </td>
                          <% if(s_userid.equals("")) {%>
                        <td align="left" valign="top" style="padding-top:1px"><a href="javascript:ASP_login()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image25','','/images/asp/type2/btn_login_o.gif',1)"><img src="/images/asp/type2/btn_login_a.gif" alt="로그인" name="Image25" width="49" height="42" border="0" id="Image25" /></a></td>
                           <%}%>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                          <% if(s_userid.equals("")) {%>
                        <td colspan="2" align="left" style="padding-top:7px"><a href="javascript:mainmenu('1');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image28','','/images/asp/type1/left_join_o.gif',1)"><img src="/images/asp/type1/left_join_a.gif" alt="회원가입" name="Image28" width="45" height="12" border="0" id="Image28" /></a>
                            <a href="javascript:mainmenu('2');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image29','','/images/asp/type1/left_idpw_o.gif',0)"><img src="/images/asp/type1/left_idpw_a.gif" alt="아이디/비밀번호찾기" name="Image29" width="94" height="12" border="0" id="Image29" /></a></td>
                      <% } else { %>
                        <td colspan="2" align="left" style="padding-top:7px;padding-left:12px">
                            <a href="javascript:mainmenu('3');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image43','','/images/asp/type1/left_logout_o.gif',1)"><img src="/images/asp/type1/left_logout_a.gif" alt="로그아웃" name="Image43" width="45" height="12" border="0" id="Image43" /></a>
                            <a href="javascript:mainmenu('4');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/asp/type1/left_info_o.gif',1)"><img src="/images/asp/type1/left_info_a.gif" alt="개인정보변경" name="Image44" width="65" height="12" border="0" id="Image44" /></a></td>
                      <%}%>
                        
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><a href="javascript:mainmenu('61');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image41','','/images/asp/type2/leftbanner1_o.gif',1)"><img src="/images/asp/type2/leftbanner1_a.gif" alt="나의강의실" name="Image41" width="217" height="69" border="0" id="Image41" /></a></td>
                  </tr>
                  <tr>
                    <td><a href="javascript:mainmenu('110');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image42','','/images/asp/type2/leftbanner2_o.gif',1)"><img src="/images/asp/type2/leftbanner2_a.gif" alt="교육과정소개" name="Image42" width="217" height="80" border="0" id="Image42" /></a></td>
                  </tr>
                  <tr>
                    <td><img src="/images/asp/type2/login_img_01.gif" width="217" height="43" /></td>
                  </tr>
                </table></td>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="/images/asp/type2/main_img_01.gif" alt="나의강의실" width="284" height="302" border="0" usemap="#Map" /></td>
                    <td><img src="/images/asp/type2/main_img_02.gif" alt="정보광장" width="224" height="302" border="0" usemap="#Map2" /></td>
                    <td><img src="/images/asp/type2/main_img_03.gif" alt="학습지원센터" width="195" height="302" border="0" usemap="#Map3" /></td>
                  </tr>
                  <tr>
                    <td colspan="3"><img src="/images/asp/type2/main_img_04.gif" width="703" height="43" /></td>
                    </tr>
                </table></td>
              </tr>
            </table></td>
            <td width="20">&nbsp;</td>
          </tr></table>
        <!--메인영역 끝--->

        </td>
        </tr>
        <tr>
        <td>

        <!--공지사항영역 시작--->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr><td width="20">&nbsp;</td>
        <td style="padding-bottom:20px"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="513" height="93" valign="top" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="15">&nbsp;</td>
                <td width="250" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td align="left"><img src="/images/asp/type2/notice_title.gif" alt="공지사항" width="135" height="23" /></td>
                          <td align="right" valign="bottom" style="padding-bottom:3px">
                              <a href="javascript:mainmenu('71');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image33','','/images/asp/type2/btn_more_o.gif',1)">
                                  <img src="/images/asp/type2/btn_more_a.gif" alt="more" name="Image33" width="39" height="13" border="0" id="Image33" />
                              </a>
                          </td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="175" height="2" align="left"></td>
                          <td align="left"></td>
                        </tr>
                         <%
                            if(noticeList!=null && noticeList.size()!=0)
                            {
                                for(int i=0;i<noticeList.size();i++)
                                {
                                    DataBox dbox =  noticeList.get(i);
                                    out.print("<tr>");
                                    out.print("<td width='175' height='17' align='left'><img src='/images/asp/type1/bbs_bullet.gif' style='margin-bottom:-2px'/><span class='fs_11 fc_gray1 ls'><a href='javascript:viewNotice("+dbox.getString("d_seq")+")'>"+StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 18)+"</a></span></td>");
                                    String dt=dbox.getString("d_ADDATE").substring(0,4)+"/"+dbox.getString("d_ADDATE").substring(4,6)+"/"+dbox.getString("d_ADDATE").substring(6,8);
                                    out.print("<td align='left'><span class='fs_11 fc_gray2'>"+dt+"</span></td>");
                                    out.print("</tr>");
                                    if(i>3)
                                        break;
                                }
                            }
                            %>
                    </table></td>
                  </tr>
                </table></td>
                <td width="25">&nbsp;</td>
                <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td align="left"><img src="/images/asp/type2/lecture_title.gif" alt="실무강좌" width="135" height="23" /></td>
                          <td align="right" valign="bottom" style="padding-bottom:3px"><a href="javascript:mainmenu('81');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image34','','/images/asp/type2/btn_more_o.gif',1)"><img src="/images/asp/type2/btn_more_a.gif" alt="more" name="Image34" width="39" height="13" border="0" id="Image34" /></a></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="175" height="2" align="left"></td>
                          </tr>
                        <%
                            if(goldclassList!=null && goldclassList.size()!=0)
                            {
                                for(int i=0;i<goldclassList.size();i++)
                                {
                                    DataBox dbox =  goldclassList.get(i);
                                    out.print("<tr>");
                                    out.print("<td width='175' height='17' align='left'><img src='/images/asp/type1/bbs_bullet.gif' style='margin-bottom:-2px'/><span class='fs_11 fc_gray1 ls'><a href=\"javascript:viewContent('"+dbox.getString("d_seq")+"', '"+dbox.getString("d_lecture_cls")+"')\">"+StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_lecnm")), 18)+"</a></span></td>");
                                    out.print("</tr>");
                                    if(i>3)
                                        break;
                                }
                            }
                            %>
                    </table></td>
                  </tr>
                </table></td>
                <td width="20">&nbsp;</td>
              </tr>
            </table></td>
            <td width="175"><img src="/images/asp/type2/service_banner.gif" alt="교육문의서비스" width="185" height="92" /></td>
            <td width="15">&nbsp;</td>
            <td width="188"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2"><img src="/images/asp/type2/center_tit.gif" alt="학습지원센터" width="188" height="18" /></td>
                </tr>
              <tr>
                <td><a href="javascript:mainmenu('72');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image36','','/images/asp/type2/center_m1_o.gif',1)"><img src="/images/asp/type2/center_m1_a.gif" alt="FAQ" name="Image36" width="94" height="37" border="0" id="Image36" /></a></td>
                <td><a href="javascript:mainmenu('73');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image37','','/images/asp/type2/center_m2_o.gif',1)"><img src="/images/asp/type2/center_m2_a.gif" alt="Q&A" name="Image37" width="94" height="37" border="0" id="Image37" /></a></td>
              </tr>
              <tr>
                <td><a href="javascript:mainmenu('75');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image38','','/images/asp/type2/center_m3_o.gif',1)"><img src="/images/asp/type2/center_m3_a.gif" alt="다운로드" name="Image38" width="94" height="37" border="0" id="Image38" /></a></td>
                <td><a href="javascript:mainmenu('76');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image39','','/images/asp/type2/center_m4_o.gif',1)"><img src="/images/asp/type2/center_m4_a.gif" alt="학습환경" name="Image39" width="94" height="37" border="0" id="Image39" /></a></td>
              </tr>
            </table></td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
        <td width="20">&nbsp;</td>
          </tr></table>
        <!--공지사항영역 끝--->

        </td>
        </tr>
    </table>
    </td>
  </tr>
</table>

<map name="Map" id="Map">
    <area shape="rect" coords="10,23,274,296" href="javascript:mainmenu('61');" />
</map>
<map name="Map2" id="Map2">
    <area shape="rect" coords="28,31,207,292" href="javascript:mainmenu('32');" />
</map>
<map name="Map3" id="Map3">
    <area shape="rect" coords="1,11,141,301" href="javascript:mainmenu('130');" />
</map>