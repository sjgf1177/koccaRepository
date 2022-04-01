
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String tem_imgpath = box.getSession("tem_imgpath");

//bLoginCheck :로그인여부 (boolean)

    //ArrayList nplist = (ArrayList)request.getAttribute("noticePopup");
    //ArrayList ntlist = (ArrayList)request.getAttribute("noticeListTop");
    
    NoticeAdminBean nbean = new NoticeAdminBean();

    int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
    if (tabseq == 0) {


        /*------- 게시판 분류에 대한 부분 세팅 -----*/
        box.put("p_type", "HN");
        box.put("p_grcode", "0000000");
        box.put("p_comp", "0000000000");
        box.put("p_subj", "0000000000");
        box.put("p_year", "0000");
        box.put("p_subjseq", "0000");
        /*----------------------------------------*/

        tabseq = nbean.selectTableseq(box);
        box.put("p_tabseq", String.valueOf(tabseq));
    }
    
    

    
    ArrayList nplist = nbean.selectListNoticePopupHome(box);  //팝업
    ArrayList ntlist = nbean.selectListNoticeTop(box);        //최근세개


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
                        <!-- notice table start-->
                        <table width="415" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="65" height="19"><img src="<%=tem_imgpath%>gong_tit.gif"></td>
                            <td width="11" rowspan="3" align="center"><img src="<%=tem_imgpath%>gong_line.gif"></td>
                            <td colspan="2" rowspan="3" valign="top" class="notice_day"><table width="339" border="0" cellspacing="0" cellpadding="0">
                                
                                <%
                                  for(int ni=0; ni < ntlist.size(); ni++){
                                  DataBox dbox = (DataBox)ntlist.get(ni);
                                    v_npseq       = dbox.getString("d_seq");
                                    v_npaddate    = dbox.getString("d_addate");
                                    v_npadtitle   = dbox.getString("d_adtitle");
                                    v_npadname    = dbox.getString("d_adname");
                                    v_npuseframe  = dbox.getString("d_useframe");
                                    v_popxpos     = dbox.getString("d_popxpos");
                                    v_popypos     = dbox.getString("d_popypos");
                                    v_popwidth    = dbox.getString("d_popwidth");
                                    v_popheight   = dbox.getString("d_popheight");
                                    v_npaddate    = FormatDate.getFormatDate(v_npaddate, "yyyy/MM/dd");
                                    
                                    if(v_npadtitle.length() > 23)
                                        v_npadtitle = v_npadtitle.substring(0,23)+"...";
                                %>
                                <tr>
                                  <td width="70" class="notice_day">[<%=v_npaddate%>]</td>
                                  <td width="269" class="notice"><a href="javascript:goSelectView('<%=v_npseq%>')"><%=v_npadtitle%></a></td>
                                </tr>
                                <%}%>
                              </table></td>
                          </tr>
                          <tr>
                            <td align="center"><a href="javascript:goSelectList()"><img src="/images/user/homepage/btn_more.gif" width="25" height="9" border="0"></a></td>
                          </tr>
                          <tr>
                            <td align="center"><img src="<%=tem_imgpath%>gong_img.gif"></td>
                          </tr>
                          <tr>
                            <td height="15" colspan="4">&nbsp;</td>
                          </tr>
<form name="nform1" method="post">
  <input type="hidden" name="p_seq"        value="">
  <input type="hidden" name="p_process"    value="">
</form>
                        </table>
                        <!-- notice table end-->

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
    <%}%>
    
    
    function notice_getCookie( name )

{
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
    
    
    function goSelectView(seq){
        document.nform1.action = "controller.homepage.MainServlet";
        document.nform1.p_process.value = "selectNoticeView";
        document.nform1.p_seq.value = seq;
        document.nform1.submit();
    }
    
    function goSelectList(){
        //alert("1111");
        document.nform1.action = "controller.homepage.MainServlet";
        document.nform1.p_process.value = "selectNoticeList";
        document.nform1.submit();
    }

	
 	    
	
</script>

