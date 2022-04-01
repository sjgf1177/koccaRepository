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

    String ser_nm = (String) request.getParameter("ser_nm");
    String rejectResult = (String) request.getParameter("rejectResult");

    boolean isReject = rejectResult.equals("1") ? true : false;
%>
<style type="text/css">
    .box {font-family: "Arial";font-size: 12px;color: #666666;text-decoration: none;width:100px;height:14px;border: 1px solid #ffffff;}
</style>
<script type="text/javascript">
    document.onload=popupView();

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
<%
    String imgPath = "";
    if ( s_userid.equals("") ) {
        imgPath = "/images/asp/type1/left_img_02.gif";
    } else {
        imgPath="/images/asp/type1/left_img_03.gif";
}
%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center">
            <table width="960" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td>

<!--메인영역 시작--->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr><td width="20">&nbsp;</td>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="222" valign="top">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="/images/asp/type1/left_img_01.gif" alt="회원로그인" width="222" height="32" /></td>
                            </tr>
                            <tr>
                                <td height="68" align="left" valign="top" background="<%=imgPath%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td colspan="2">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="45"></td>
                                                        <td width="110" valign="top" style="padding-top:2px">
<%
    if(s_userid.equals("")) {
%>
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td>
                                                                        <input name="p_id" type="text" class="box"  />
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td height="5"></td>
                                                                </tr>
                                                                <tr>
                                                                    <td><input name="p_pw" type="password" class="box" /></td>
                                                                </tr>
                                                            </table>
<%
    } else {
%>
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td><%=s_username%>님,</td>
                                                                </tr>
                                                                <tr>
                                                                    <td height="5"></td>
                                                                </tr>
                                                                <tr>
                                                                    <td>환영 합니다.
<% 
        if(!(topWarp_gadmin1.equals("ZZ") || topWarp_gadmin1.equals(""))) {
%>
                                                                        <%=LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") %>
<%
        }

        if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {
%>
                                                                        <input type="image" src="/images/portal/btn/btn_top_go.gif" alt="go" class="va_m" onClick='topAdminOpenPage()'/>
<%
        }
%>
                                                                    </td>
                                                                </tr>
                                                            </table>
<%
    }
%>
                                                        </td>
<%
    if(s_userid.equals("")) {
%>
                                                        <td style="padding-top:1px"><a href="javascript:ASP_login()" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image25','','/images/asp/type1/btn_login_o.gif',1)"><img src="/images/asp/type1/btn_login_a.gif" alt="로그인" name="Image25" width="49" height="42" border="0" id="Image25" /></a></td>
<%
    }
%>
                                                    </tr>
                                                    <tr>
                                                        <td>&nbsp;</td>
<%
    if(s_userid.equals("")) {
%>
                                                        <td colspan="2" style="padding-top:7px">
<%
        if ( isReject ) {
            if (ser_nm.equals("mcst.kocca.or.kr") || ser_nm.equals("mcst.edukocca.or.kr") ) {
%>
                                                            <a href="javascript:alert('아이나루를 통하여 접속해 주세요');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image28','','/images/asp/type1/left_join_o.gif',1)"><img src="/images/asp/type1/left_join_a.gif" alt="회원가입" name="Image28" width="45" height="12" border="0" id="Image28" /></a>
                                                            <a href="javascript:mainmenu('2');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image29','','/images/asp/type1/left_idpw_o.gif',0)"><img src="/images/asp/type1/left_idpw_a.gif" alt="아이디/비밀번호찾기" name="Image29" width="94" height="12" border="0" id="Image29" /></a>
<%
            }

        } else {
%>
                                                            <a href="javascript:mainmenu('1');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image28','','/images/asp/type1/left_join_o.gif',1)"><img src="/images/asp/type1/left_join_a.gif" alt="회원가입" name="Image28" width="45" height="12" border="0" id="Image28" /></a>
                                                            <a href="javascript:mainmenu('2');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image29','','/images/asp/type1/left_idpw_o.gif',0)"><img src="/images/asp/type1/left_idpw_a.gif" alt="아이디/비밀번호찾기" name="Image29" width="94" height="12" border="0" id="Image29" /></a>
<%
        }
%>
                                                        </td>
<%
    } else {
%>
                                                        <td colspan="2" style="padding-top:7px;padding-left:12px">
                                                            <a href="javascript:mainmenu('3');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image43','','/images/asp/type1/left_logout_o.gif',1)"><img src="/images/asp/type1/left_logout_a.gif" alt="로그아웃" name="Image43" width="45" height="12" border="0" id="Image43" /></a>
                                                            <a href="javascript:mainmenu('4');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image44','','/images/asp/type1/left_info_o.gif',1)"><img src="/images/asp/type1/left_info_a.gif" alt="개인정보변경" name="Image44" width="65" height="12" border="0" id="Image44" /></a>
                                                        </td>
<%
    }
%>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td valign="top"><a href="javascript:mainmenu('61');"><img src="/images/asp/type1/lnb_1.jpg" alt="나의강의실바로가기" onmouseover="this.src='/images/asp/type1/lnb_1_0.jpg'" onmouseout="this.src='/images/asp/type1/lnb_1.jpg'"/></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td valign="top"><a href="javascript:mainmenu('110');"><img src="/images/asp/type1/lnb_2.jpg" alt="교육과정소개바로가기"onmouseover="this.src='/images/asp/type1/lnb_2_0.jpg'" onmouseout="this.src='/images/asp/type1/lnb_2.jpg'"/></a></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
<%
    if(box.getSession("tem_grcode").equals("N000068")) {
%>
                        <img src="/images/asp/type1/mainimg_N000068.png" width="697" height="313" />
<%
    } else {
%>
                        <img src="/images/asp/type1/mainkv.jpg" alt=""/>
<%
    }
%>
                        <!-- <img src="/images/asp/type1/mainimg.gif" width="698" height="313" /> //-->
                    </td>
                </tr>
            </table>
        </td>
        <td width="20">&nbsp;</td>
    </tr>
</table>
<!--메인영역 끝--->

                        </td>
                    </tr>
                    <tr>
                        <td>

<!--공지사항영역 시작--->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="20">&nbsp;</td>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td height="25" background="/images/asp/type1/main_line.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="15">&nbsp;</td>
                                <td width="250" valign="top">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td align="left"><img src="/images/asp/type1/notice_title.gif" alt="공지사항" width="135" height="23" /></td>
                                                        <td align="right" valign="bottom"><a href="javascript:mainmenu('71');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image36','','/images/asp/type1/btn_more_o.gif',1)"><img src="/images/asp/type1/btn_more_a.gif" alt="more" name="Image36" width="39" height="13" border="0" id="Image36" /></a></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="175" height="2" align="left"></td>
                                                        <td align="left"></td>
                                                    </tr>
<%
    if(noticeList!=null && noticeList.size()!=0) {
        for(int i=0;i<noticeList.size();i++) {
            DataBox dbox = noticeList.get(i);
            out.print("<tr>");
            out.print("<td width='175' height='17' align='left'><img src='/images/asp/type1/bbs_bullet.gif' style='margin-bottom:-2px'/><span class='fs_11 fc_gray1 ls'><a href='javascript:viewNotice("+dbox.getString("d_seq")+")'>"+StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_adtitle")), 18)+"</a></span></td>");
            String dt=dbox.getString("d_ADDATE").substring(0,4)+"/"+dbox.getString("d_ADDATE").substring(4,6)+"/"+dbox.getString("d_ADDATE").substring(6,8);
            out.print("<td align='left'><span class='fs_11 fc_gray2'>"+dt+"</span></td>");
            out.print("</tr>");
            if(i > 3)
                break;
        }
    }
%>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="20">&nbsp;</td>
                                <td width="250" valign="top">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td align="left"><img src="/images/asp/type1/lecture_title.gif" alt="실무강좌" width="135" height="23" /></td>
                                                        <td align="right" valign="bottom"><a href="javascript:mainmenu('81');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image1','','/images/asp/type1/btn_more_o.gif',1)"><img src="/images/asp/type1/btn_more_a.gif" alt="more" name="Image1" width="39" height="13" border="0" id="Image1" /></a></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="175" height="2" align="left"></td>
                                                        <td align="left"></td>
                                                    </tr>
<%
    if(goldclassList!=null && goldclassList.size()!=0) {
        for(int i=0;i<goldclassList.size();i++) {
            DataBox dbox =  goldclassList.get(i);
            out.print("<tr>");
            out.print("<td width='175' height='17' align='left'><img src='/images/asp/type1/bbs_bullet.gif' style='margin-bottom:-2px'/><span class='fs_11 fc_gray1 ls'><a href=\"javascript:viewContent('"+dbox.getString("d_seq")+"', '"+dbox.getString("d_lecture_cls")+"')\">"+StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_lecnm")), 18)+"</a></span></td>");
            out.print("</tr>");
            if(i > 3)
                break;
        }
    }
%>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="20">&nbsp;</td>
                                <td width="185"><img src="/images/asp/type1/main_tel.gif" alt="교육문의서비스" width="185" height="92" /></td>
                                <td width="15">&nbsp;</td>
                                <td align="left" valign="top">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><img src="/images/asp/type1/center_title.gif" alt="학습지원센터" width="63" height="13" /></td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td width="96"><a href="javascript:mainmenu('72');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image31','','/images/asp/type1/banner_faq_o.gif',1)"><img src="/images/asp/type1/banner_faq_a.gif" alt="FAQ" name="Image31" width="96" height="40" border="0" id="Image31" /></a></td>
                                            <td><a href="javascript:mainmenu('73');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image34','','/images/asp/type1/banner_qna_o.gif',1)"><img src="/images/asp/type1/banner_qna_a.gif" alt="Q&A" name="Image34" width="96" height="40" border="0" id="Image34" /></a></td>
                                        </tr>
                                        <tr>
                                            <td><a href="javascript:mainmenu('75');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image33','','/images/asp/type1/banner_down_o.gif',1)"><img src="/images/asp/type1/banner_down_a.gif" alt="다운로드" name="Image33" width="96" height="39" border="0" id="Image33" /></a></td>
                                            <td><a href="javascript:mainmenu('76');" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image35','','/images/asp/type1/banner_system_o.gif',1)"><img src="/images/asp/type1/banner_system_a.gif" alt="학습환경" name="Image35" width="96" height="39" border="0" id="Image35" /></a></td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="10">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="38">&nbsp;</td>
                </tr>
            </table>
        </td>
        <td width="20">&nbsp;</td>
    </tr>
</table>
<!--공지사항영역 끝--->

                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>