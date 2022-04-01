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
    box.put("leftmenu","05"); 

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
    ArrayList list2 = (ArrayList)request.getAttribute("memberView");
    ArrayList list3 = (ArrayList)request.getAttribute("boardCnt");

    DataBox dbox3 = (DataBox)list3.get(0);
    DataBox dbox2 = (DataBox)list2.get(0);

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topMyClass.jsp"%>
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
            <table width="730" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                    <td height="20" align="center" valign="top" class="location"> 
                    HOME &gt; 나의 강의실 &gt; 나의 학습활동</td>
                </tr>
                <tr> 
                    <td align="center" valign="top"> 
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                                <td valign="top"><img src="/images/user/myclass/<%=tem_subimgpath%>/tit_activity.jpg"></td>
                            </tr>
                            <tr> 
                                <td height="20"></td>
                            </tr>
                        </table>
                  <!-- 구분탭 -->
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                                <td height="5" colspan="4"></td>
                            </tr>
                            <tr> 
                                <td width="98" valign="top"><img src="/images/user/myclass/tab_siteact_on.gif" alt="사이트활동"></td>
                                <td width="98" valign="top" ><a href="/servlet/controller.system.StudyCountServlet?p_process=myActivity" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/myclass/tab_studyact_on.gif',1)"><img src="/images/user/myclass/tab_studyact.gif" name="Image37" border="0"></a></td>
                                <td width="98" valign="top">&nbsp;</td>
                                <td width="381" align="right"><a href="javascript:whenExcel()"><img src="/images/user/button/btn_print.gif" border="0"></a></td>
                            </tr>
                            <tr> 
                                <td height="10" colspan="4"></td>
                            </tr>
                        </table>
                        <table width="675" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                                <td width="675" height="5" valign="top"> 
                   <!-- 사이트활동정보 table -->
                        <table width="675" border="0" cellpadding="0" cellspacing="0" background="/images/user/myclass/pe_box_bg.gif">
                            <tr> 
                                <td><img src="/images/user/myclass/pe_box_top.gif"></td>
                            </tr>
                            <tr> 
                                <td align="center" valign="top">
                                    <table width="630" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td width="100" rowspan="3" align="center" valign="top"><img src="/images/user/myclass/activity_img.jpg"></td>
                                            <td width="300" height="18" class="tbl_ble"><strong>최초로그인 
                                            :</strong> <%=FormatDate.getFormatDate(dbox2.getString("d_lgfirst"),"yyyy-MM-dd HH:mm")%> </td>
                                            <td>&nbsp;</td>
                                            <td class="tbl_ble"><strong>최근로그인 :</strong> 
                                            <%=FormatDate.getFormatDate(dbox2.getString("d_lglast"),"yyyy-MM-dd HH:mm")%> </td>
                                        </tr>
                                        <tr> 
                                            <td height="2" colspan="3" background="/images/user/myclass/stu_box_vline2.gif"></td>
                                        </tr>
                                        <tr> 
                                            <td class="tbl_ble"><strong>총로그인수 :</strong> 
                                            <%=dbox2.getString("d_lgcnt")%></td>
                                            <td>&nbsp;</td>
                                            <td class="tbl_ble">&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr> 
                                <td><img src="/images/user/myclass/pe_box_bo.gif"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr> 
                    <td height="20" valign="top"></td>
                </tr>
            </table>
                  <!-- 사이트활동상세정보 테이블 -->
            <table width="675" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center">
                        <table width="617" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                                <td colspan="3" align="center" ><img src="/images/user/myclass/act_text_t2.gif"></td>
                                <td>&nbsp;</td>
                                <td colspan="3" align="center"><img src="/images/user/myclass/act_text_t2.gif"></td>
                            </tr>
                            <tr> 
                                <td height="5" colspan="3" ></td>
                                <td height="5"></td>
                                <td height="5" colspan="3"></td>
                            </tr>
                            <tr> 
                                <td width="113" rowspan="3" align="center" ><img src="/images/user/myclass/act_img_5.gif"></td>
                                <td width="114" align="center" ><img src="/images/user/myclass/act_text_5.gif" ></td>
                                <td width="73" class="tbl_pc"><%=dbox3.getString("d_knowcnt")%></td>
                                <td>&nbsp;</td>
                                <td width="100" rowspan="3" align="center"><img src="/images/user/myclass/act_img_6.gif"></td>
                                <td align="center"><img src="/images/user/myclass/act_text_7.gif"></td>
                                <td width="77" class="tbl_pc"><%=dbox3.getString("d_contactcnt")%></td>
                            </tr>
                            <tr> 
                                <td height="1" bgcolor="#E3DED5"></td>
                                <td height="1" bgcolor="#E3DED5"></td>
                                <td width="10" height="1"></td>
                                <td height="1" bgcolor="#E3DED5"></td>
                                <td height="1" bgcolor="#E3DED5"></td>
                            </tr>
                            <tr> 
                                <td align="center"><img src="/images/user/myclass/act_text_6.gif" alt="Q&A"></td>
                                <td class="tbl_pc"><%=dbox3.getString("d_qnacnt")%></td>
                                <td>&nbsp;</td>
                                <td width="130" align="center" ><img src="/images/user/myclass/act_text_8.gif" alt="댓글"></td>
                                <td class="tbl_pc"><%=dbox3.getString("d_commentcnt")%></td>
                            </tr>
                            <tr> 
                                <td height="20" colspan="7">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr> 
                    <td align="center" valign="top"> 
                    <!-- 메뉴별사이트활동테이블 -->
                        <table width="610" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td align="center" valign="top"><img src="/images/user/myclass/act_text_t1.gif"></td>
                                <td align="center" valign="top"><img src="/images/user/myclass/act_text_t1.gif"></td>
                            </tr>
                            <tr> 
                                <td width="305" align="center" valign="top"> 
                                    <table width="288" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td height="5" colspan="3"></td>
                                        </tr>
                                        <tr> 
                                            <td width="112" colspan="3"><img src="/images/user/myclass/act_text_1.gif" alt="수강신청/안내"></td>
                                        </tr>
                                
<%
      int j = 0;
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
                                        <tr>
            <%if(j==1){%>
                                            <td rowspan="<%=v_gubuncnt*2+1%>" align="center" valign="top"><img src="/images/user/myclass/act_img_1.gif"></td>
            <%}%>
                                            <td width="120" class="tbl_gleft"><%=v_menuname%></td>
                                            <td width="61" class="tbl_pc"><%=v_cnt%></td>
                                        </tr>
<%}
}%>                                
                                        <tr> 
                                            <td bgcolor="#F6F6F6" class="tbl_gleft"><b>총계</b></td>
                                            <td bgcolor="#F6F6F6" class="tbl_pc"><%=v_gubuntot%></td>
                                        </tr>
                                        <tr> 
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="305" align="center" valign="top">
                                    <table width="288" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td height="5" colspan="3"></td>
                                        </tr>
                                        <tr> 
                                            <td><img src="/images/user/myclass/act_text_2.gif" alt="나의강의실" ></td>
                                        </tr>


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
                                        <tr>
            <%if(j==1){%>
                                            <td rowspan="<%=v_gubuncnt*2+1%>" align="center" valign="top"><img src="/images/user/myclass/act_img_2.gif"></td>
            <%}%>
                                            <td width="120" class="tbl_gleft"><%=v_menuname%></td>
                                            <td width="61" class="tbl_pc"><%=v_cnt%></td>
                                        </tr>
<%}
}%>                                
                                        <tr> 
                                            <td bgcolor="#F6F6F6" class="tbl_gleft"><b>총계</b></td>
                                            <td bgcolor="#F6F6F6" class="tbl_pc"><%=v_gubuntot%></td>
                                        </tr>
                                        <tr> 
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table> 
                                </td>
                            </tr>
                            <tr> 
                            
                                <td align="center" valign="top">
                                    <table width="288" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td height="5" colspan="3"></td>
                                        </tr>
                                        <tr> 
                                            <td><img src="/images/user/myclass/act_text_3.gif" alt="학습지원센터"></td>
                                        </tr>
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
                                        <tr> 
            <%if(j==1){%>
                                            <td rowspan="<%=v_gubuncnt*2+1%>" align="center" valign="top"><img src="/images/user/myclass/act_img_3.gif"></td>
            <%}%>
                                            <td width="120" class="tbl_gleft"><%=v_menuname%></td>
                                            <td width="61" class="tbl_pc"><%=v_cnt%></td>
                                        </tr>
<%}
}%>
                                        <tr> 
                                            <td bgcolor="#F6F6F6" class="tbl_gleft"><b>총계</b></td>
                                            <td bgcolor="#F6F6F6" class="tbl_pc"><%=v_gubuntot%></td>
                                        </tr>
                                        <tr> 
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="305" align="center" valign="top">
                                    <table width="288" border="0" cellspacing="0" cellpadding="0">
                                        <tr> 
                                            <td height="5" colspan="3"></td>
                                        </tr>
                                        <tr> 
                                            <td><img src="/images/user/myclass/act_text_4.gif" alt="교육안내"></td>
                                        </tr>
<%
      j = 0;
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
                                        <tr> 
            <%if(j==1){%>
                                            <td rowspan="<%=v_gubuncnt*2+1%>" align="center" valign="top"><img src="/images/user/myclass/act_img_4.gif"></td>
            <%}%>
                                            <td width="120" class="tbl_gleft"><%=v_menuname%></td>
                                            <td width="61" class="tbl_pc"><%=v_cnt%></td>
                                        </tr>
<%}
}%>
                                        <tr> 
                                            <td bgcolor="#F6F6F6" class="tbl_gleft"><b>총계</b></td>
                                            <td bgcolor="#F6F6F6" class="tbl_pc"><%=v_gubuntot%></td>
                                        </tr>
                                        <tr> 
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table></td>
        </tr>
        <tr>
            <td align="center" valign="top">&nbsp;</td>
        </tr>
        <tr> 
            <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
        </tr>
    </table>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
