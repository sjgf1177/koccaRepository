<%
//**********************************************************
//  1. 제      목: 홈페이지 공지사항
//  2. 프로그램명 : Notice.jsp
//  3. 개      요: 홈페이지 공지사항
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>
       
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    HomeNoticeBean beanNotice= new HomeNoticeBean();
    ArrayList noticeList = beanNotice.selectDirectList(box); 

    NoticeAdminBean nbean = new NoticeAdminBean();
    ArrayList nplist     = nbean.selectListNoticePopupHome(box);  // 팝업

    String v_npseq       = "";
    String v_npaddate    = "";
    String v_npadtitle   = "";
    String v_npadname    = "";
    String v_npadcontent = "";
    String v_npcnt       = "";
    String v_npuseframe  = "";
    String v_popxpos     = "";
    String v_popypos     = "";
    String v_popwidth    = "";
    String v_popheight   = "";
%>  

<SCRIPT LANGUAGE="JavaScript">
<!--

    function selectView(seq){
        var url = "/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq="+seq;
        document.form1.action = url;
        document.form1.submit();
    }

//-->
</SCRIPT>

<!-- 공지사항 -->
<form name="form1" method="post">
                      <table width="460" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/homepage/type1/st_notice.gif" border="0" usemap="#Map3"></td>
                          </tr>
                          <tr> 
                            <td>
                              <table width="460" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="97">
                                    <img src="/images/user/kocca/homepage/type1/photo_notice.gif">
                                  </td>
                                  <td width="2">&nbsp;</td>
                                  <td colspan="2" valign="top" class="tbl_bleft_none02">
                                    <table width="360" border="0" cellspacing="0" cellpadding="0">
    <% 
        int nlsize = (noticeList.size());
            for(int i = 0; i < nlsize; i++) {
                DataBox dbox = (DataBox)noticeList.get(i);

    %>
                                      <tr> 
                                        <td name="p_seq" width="287" class="notice" value=""><a href="javascript:selectView(<%=dbox.getInt("d_seq")%>)"><%=dbox.getString("d_adtitle")%>
                                        </td>
                                        <td width="73" class="notice_day">[<%=FormatDate.getFormatDate(dbox.getString("d_addate"),"yyyy-MM-dd")%>]</td>
                                      </tr>
                                      <tr> 
                                        <td height="1" colspan="2" background="/images/user/kocca/homepage/type1/bg_dot_line02.gif"></td>
                                      </tr>
    <%}     for(int j = 0; j < 4 - nlsize; j++){%>          
                                      <tr height="23">
                                        <td style="color:#333333;font-size:11px;"></td> 
                                      </tr>

                                      <tr> 
                                        <td height="1" colspan="2" background="/images/user/kocca/homepage/type1/bg_dot_line02.gif"></td></tr>
    <%      }%>
                                    </table>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr> 
                            <td height="20">&nbsp;</td>
                          </tr>
                        </table>
</form>

<script language="javascript">
    //function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable) {
    var  width  = 0;
    var  height = 0;
    var  left   = 0;
    var  top    = 0;

<%
      for(int npi=0; npi < nplist.size(); npi++){
        DataBox dbox = (DataBox)nplist.get(npi);
        v_npseq       = dbox.getString("d_seq");
        v_npaddate    = dbox.getString("d_addate");
        v_npadtitle   = dbox.getString("d_adtitle");
        v_npadname    = dbox.getString("d_adname");
        v_npuseframe  = dbox.getString("d_useframe");
        v_popxpos    = dbox.getString("d_popxpos");
        v_popypos    = dbox.getString("d_popypos");
        v_popwidth   = dbox.getString("d_popwidth");
        v_popheight  = dbox.getString("d_popheight");
%>
      width  = <%=v_popwidth%>;
      height = <%=v_popheight%>;
      left   = <%=v_popxpos%>;
      top    = <%=v_popypos%>;
      url    = "/servlet/controller.homepage.MainServlet?p_process=popupview&p_seq=<%=v_npseq%>&p_useframe=<%=v_npuseframe%>&p_popUpPreview=popUpPreview<%=v_npseq%>";
      
        if ( notice_getCookie( "popUpPreview<%=v_npseq%>" ) != "done" ){
          open_window("popUpPreview<%=v_npseq%>",url,left,top,width,height, "no","no","no","yes","no");
        }
<%
      }
%>

    function notice_getCookie( name ){
        var nameOfCookie = name + "=";
        var x = 0;
        while ( x <= document.cookie.length )
        {
                var y = (x+nameOfCookie.length);
                if ( document.cookie.substring( x, y ) == nameOfCookie ) {
                        if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                                endOfCookie = document.cookie.length;
                        return unescape( document.cookie.substring( y, endOfCookie ) );
                }
                x = document.cookie.indexOf( " ", x ) + 1;
                if ( x == 0 )
                        break;
        }
        return "";
    }

</script>
<map name="Map3">
  <area shape="rect" coords="422,1,459,18" href="/servlet/controller.homepage.KHomeNoticeServlet?p_process=moreNotice">
</map>

<!-- 공지사항 -->