<%
//bLoginCheck :로그인여부 (boolean)

    ArrayList nplist = (ArrayList)request.getAttribute("noticePopup");
    ArrayList ntlist = (ArrayList)request.getAttribute("noticeListTop");

    String v_npseq       = "";
    String v_npaddate    = "";
    String v_npadtitle   = "";
    String v_npadname    = "";
    String v_npadcontent = "";
    String v_npcnt       = "";
    String v_npuseframe  = "";
    String v_popxpos    = "";
    String v_popypos    = "";
    String v_popwidth   = "";
    String v_popheight  = "";
    
    
%>  
                        <!-- notice table start-->
                        <table width="415" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="65" height="19"><img src="/images/user/homepage/type1/gong_tit.gif"></td>
                            <td width="11" rowspan="3" align="center"><img src="/images/user/homepage/type1/gong_line.gif"></td>
                            <td colspan="2" rowspan="3" valign="top" class="notice_day"><table width="339" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="70" class="notice_day">[2005/06/25]</td>
                                  <td width="269" class="notice"><a href="#">'초일류
                                    기업으로 가는 지름길-SCM'과정 수강</a></td>
                                </tr>
                                <tr>
                                  <td class="notice_day">[2005/07/07]</td>
                                  <td class="notice"><a href="#">5월9일 종료된 직무,
                                    외국어 과정 결과</a></td>
                                </tr>
                                <tr>
                                  <td class="notice_day">[2005/07/15]</td>
                                  <td class="notice"><a href="#">사이버 직무, 외국어 2차
                                    수강신청 안내</a></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td align="center"><a href="#"><img src="/images/user/homepage/btn_more.gif" width="25" height="9" border="0"></a></td>
                          </tr>
                          <tr>
                            <td align="center"><img src="/images/user/homepage/type1/gong_img.gif"></td>
                          </tr>
                          <tr>
                            <td height="20" colspan="4">&nbsp;</td>
                          </tr>
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
        v_npadcontent = dbox.getString("d_adcontent");
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
      url    = "/servlet/controller.homepage.MainServlet?p_process=popupview&p_seq=<%=v_npseq%>&p_useframe=<%=v_npuseframe%>";
      open_window("popUpPreview<%=npi%>",url,left,top,width,height, "no","no","no","yes","no");
    <%}%>

</script>
