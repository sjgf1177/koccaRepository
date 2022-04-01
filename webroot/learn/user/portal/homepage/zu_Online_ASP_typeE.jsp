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

/*기본링크폰트*/
.sf1 a:link{font-size:11px; color:#4d4d4d; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:none;}
.sf1 a:visited{font-size:11px; color:#4d4d4d; font-family:"돋움",Arial, Helvetica, sans-serif;}
.sf1 a:hover{font-size:11px; color:#ffffff; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:none;}
.sf2 a:link{font-size:11px; color:#999999; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:underline;}
.sf2 a:visited{font-size:11px; color:#999999; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:underline;}
.sf2 a:hover{font-size:11px; color:#999999; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:underline;}
.sf3 a:link{font-size:11px; color:#063d99; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:underline;}
.sf3 a:visited{font-size:11px; color:#063d99; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:underline;}
.sf3 a:hover{font-size:11px; color:#063d99; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:underline;}
.mf1 a:link{font-size:12px; color:#666666; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:none;}
.mf1 a:visited{font-size:12px; color:#666666; font-family:"돋움",Arial, Helvetica, sans-serif;}
.mf1 a:hover{font-size:12px; color:#063d99; font-family:"돋움",Arial, Helvetica, sans-serif; text-decoration:none;}

/*폰트크기*/
.fs_11 {font-size:11px;} .fs_11 a:link {font-size:11px;} .fs_11 a:hover {font-size:11px;} .fs_11 a:visited {font-size:11px;}

/*폰트컬러*/
.fc_white1 a:hover, a.fc_white1:hover {color:#ffffff;}
.fc_red1 a:hover, a.fc_red1:hover {color:#ff6632;}
.fc_red2 a:hover, a.fc_red2:hover {color:#e85e3e;}
.fc_blue1 a:hover, a.fc_blue1:hover {color:#3f84a1;}
.fc_blue2 a:hover, a.fc_blue2:hover {color:#6f8ca4;}
.fc_purple1 a:hover, a.fc_purple1:hover {color:#7348b5;}
.fc_purple2 a:hover, a.fc_purple2:hover {color:#9791bb;}
.fc_gray1, .fc_gray1 a:link, .fc_gray1 a:visited, a.fc_gray1, a.fc_gray1:link, a.fc_gray1:visited {color:#656565;} /* 656565 */
.fc_gray1 a:hover, a.fc_gray1:hover {color:#656565;}
.fc_gray2, .fc_gray2 a:link, .fc_gray2 a:visited, a.fc_gray2, a.fc_gray2:link, a.fc_gray2:visited {color:#9e9d9b;} /* 9e9d9b */
.fc_gray2 a:hover, a.fc_gray2:hover {color:#9e9d9b;}
.fc_black a:hover, a.fc_black:hover {color:#000000;}
.box02 {font-family: "Arial";font-size: 12px;color: #666666;text-decoration: none;width:118px;height:16px;border:2px solid #e1e1e1;  background:#f0f0f0;}

/* 기본 클래스 */
.ls {letter-spacing:-1px;}
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
//               $("[name='"+nowTrName+"']").attr("class","");

               if(sw=="t")
               {
                   $(".hiddenYesNo",obj).show();
//                   $(obj).attr("class",classStr);
               }
           }
       
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td>
    <table width="960" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td>
        <!--메인영역 시작--->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr><td width="20">&nbsp;</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="top"><img src="/images/asp/type3/mainimg_01.gif" width="920" height="36" /></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="59" valign="top"><img src="/images/asp/type3/mainimg_02.gif" width="59" height="120" /></td>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/asp/type3/login_tit.gif" alt="회원로그인" width="216" height="34" /></td>
                          </tr>
                          <tr>
                            <td height="66" valign="top" background="/images/asp/type3/login_bgbg.gif" style="padding-right:5px">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td height="52" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                    <td width="34" valign="top"><img src="/images/asp/type3/login_img_02.gif" width="34" height="49" /></td>
                                    <td>
                                        <% if(s_userid.equals("")) { %>
                                        <table width="100%" border="0" cellspacing="2" cellpadding="0">
                                          <tr>
                                            <td align="left"><input name="p_id" type="text" class="box02" id="p_id" value="" /></td>
                                          </tr>
                                          <tr>
                                            <td align="left"><input name="p_pw" type="password" class="box02" id="p_pw" value="" /></td>
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
                                    <td width="49"><a href="javascript:ASP_login()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image36','','/images/asp/type3/bn_login_o.gif',1)"><img src="/images/asp/type3/bn_login_a.gif" alt="로그인" name="Image36" width="49" height="42" border="0" id="Image36" /></a></td>
                                      <%}%>
                                  </tr>
                                </table></td>
                              </tr>
                              <tr>
                                <td align="right">
                                     <% if(s_userid.equals("")) {%>
                                        <a href="javascript:mainmenu('1');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image33','','/images/asp/type3/bn_join_o.gif',1)"><img src="/images/asp/type3/bn_join_a.gif" alt="회원가입"name="Image33" width="48" height="13" border="0" id="Image33" /></a>
                                        <a href="javascript:mainmenu('2')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image34','','/images/asp/type3/bn_idpw_o.gif',1)"><img src="/images/asp/type3/bn_idpw_a.gif" alt="아이디/비밀번호찾기" name="Image34" width="93" height="13" border="0" id="Image34" /></a>
                                    <% } else { %>
                                        <a href="javascript:mainmenu('3');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image43','','/images/asp/type3/left_logout_o.gif',1)"><img src="/images/asp/type3/left_logout_a.gif" alt="로그아웃" name="Image43" width="45" height="12" border="0" id="Image43" /></a>
                                        <a href="javascript:mainmenu('4');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/asp/type3/left_info_o.gif',1)"><img src="/images/asp/type3/left_info_a.gif" alt="개인정보변경" name="Image44" width="65" height="12" border="0" id="Image44" /></a>
                                      <%}%>
                                </td>
                              </tr>
                            </table></td>
                          </tr>
                          <tr>
                            <td><img src="/images/asp/type3/login_img_01.gif" width="216" height="14" /></td>
                          </tr>
                        </table></td>
                        <td width="33" valign="top"><img src="/images/asp/type3/mainimg_03.gif" width="33" height="120" /></td>
                      </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="59"><img src="/images/asp/type3/mainimg_05.gif" width="59" height="143" /></td>
                        <td><a href="javascript:mainmenu('61');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image26','','/images/asp/type3/mainimg_06_o.gif',1)"><img src="/images/asp/type3/mainimg_06_a.gif" alt="나의강의실" name="Image26" width="117" height="143" border="0" id="Image26" /></a></td>
                        <td><a href="javascript:mainmenu('110');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image27','','/images/asp/type3/mainimg_07_o.gif',1)"><img src="/images/asp/type3/mainimg_07_a.gif" alt="교육과정소개" name="Image27" width="99" height="143" border="0" id="Image27" /></a></td>
                        <td valign="top"><img src="/images/asp/type3/mainimg_08.gif" width="33" height="143" /></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
                <td width="510" background="/images/asp/type3/board_bg.gif"><script>jsFlash('/images/asp/type3/mainimg.swf','510','257','','','', '');</script></td>
                <td width="102" valign="top"><img src="/images/asp/type3/mainimg_04.gif" width="102" height="263" /></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><img src="/images/asp/type3/mainimg_09.gif" width="920" height="63" /></td>
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
            <td width="513" height="93" valign="top" style="background-image:url(/images/asp/type3/bgbg_2.gif);background-repeat:no-repeat;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="15">&nbsp;</td>
                <td width="250" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td align="left"><img src="/images/asp/type3/notice_tit.gif" alt="공지사하" width="135" height="23" /></td>
                          <td align="right" valign="bottom" style="padding-bottom:3px"><a href="javascript:mainmenu('71');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image49','','/images/asp/type3/bn_more_o.gif',1)"><img src="/images/asp/type3/bn_more_a.gif" alt="more" name="Image49" width="36" height="9" border="0" id="Image49" /></a></td>
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
                                    out.print("<td width='175' height='17' align='left'><img src='/images/asp/type3/bbs_bullet.gif' style='margin-bottom:-2px'/><span class='fs_11 fc_gray1 ls'><a href='javascript:viewNotice("+dbox.getString("d_seq")+")'>"+StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 18)+"</a></span></td>");
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
                          <td align="left"><img src="/images/asp/type3/lecture_tit.gif" alt="실무강좌" width="135" height="23" /></td>
                          <td align="right" valign="bottom" style="padding-bottom:3px"><a href="javascript:mainmenu('81');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image1','','/images/asp/type3/bn_more_o.gif',1)"><img src="/images/asp/type3/bn_more_a.gif" alt="more" name="Image1" width="36" height="9" border="0" id="Image1" /></a></td>
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
                                    out.print("<td width='175' height='17' align='left'><img src='/images/asp/type3/bbs_bullet.gif' style='margin-bottom:-2px'/><span class='fs_11 fc_gray1 ls'><a href=\"javascript:viewContent('"+dbox.getString("d_seq")+"', '"+dbox.getString("d_lecture_cls")+"')\">"+StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_lecnm")), 18)+"</a></span></td>");
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
            </table>
              <td width="175" valign="top"><img src="/images/asp/type3/main_banner.gif" alt="교육문의서비스" width="175" height="95" /></td>
             <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td ><img src="/images/asp/type3/main_center_tit.gif" alt="학습지원센터" width="232" height="27" /></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><a href="javascript:mainmenu('72');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image39','','/images/asp/type3/main_center_1_o.gif',1)"><img src="/images/asp/type3/main_center_1_a.gif" alt="FAQ" name="Image39" width="64" height="68" border="0" id="Image39" /></a></td>
                    <td><a href="javascript:mainmenu('73');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image40','','/images/asp/type3/main_center_2_o.gif',1)"><img src="/images/asp/type3/main_center_2_a.gif" alt="Q&A" name="Image40" width="42" height="68" border="0" id="Image40" /></a></td>
                    <td><a href="javascript:mainmenu('75');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image41','','/images/asp/type3/main_center_3_o.gif',1)"><img src="/images/asp/type3/main_center_3_a.gif" alt="다운로드" name="Image41" width="52" height="68" border="0" id="Image41" /></a></td>
                    <td><a href="javascript:mainmenu('76');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image42','','/images/asp/type3/main_center_4_o.gif',1)"><img src="/images/asp/type3/main_center_4_a.gif" alt="학습환경" name="Image42" width="52" height="68" border="0" id="Image42" /></a></td>
                    <td><img src="/images/asp/type3/main_center_5.gif" width="22" height="68" /></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
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