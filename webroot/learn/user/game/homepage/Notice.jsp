
<%
//**********************************************************
//  1. 제      목: 홈페이지 공지사항 
//  2. 프로그램명 : Notice.jsp
//  3. 개      요: 홈페이지 공지사항 
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.18
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

    //int v_seqNotice = 0;

	HomeNoticeBean beanNotice= new HomeNoticeBean();
	ArrayList noticeList = beanNotice.selectDirectList(box); 
	
	HomeLetterBean beanLetter= new HomeLetterBean();
	ArrayList letterList = beanLetter.selectDirectList(box); 
	
	NoticeAdminBean nbean = new NoticeAdminBean();
	ArrayList nplist = nbean.selectListNoticePopupHome(box);  //팝업
	
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
    
	function notice_getCookie( name ) {
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

<SCRIPT LANGUAGE="JavaScript">

	//상세보기
    function selectList(seq) {
        new_mainList.action = "/servlet/controller.homepage.HomeNoticeServlet";
        new_mainList.p_process.value = "selectView";
        new_mainList.p_seq.value		 = seq;
        new_mainList.submit();
    }
    
    //상세보기
    function selectLList(seq) {
        new_mainList.action = "/servlet/controller.homepage.HomeLetterServlet";
        new_mainList.p_process.value = "selectView";
        new_mainList.p_seq.value		 = seq;
        new_mainList.submit();
    }



</SCRIPT>

<form name="new_mainList" method="post" >
  <input type="hidden" name="p_seq"        value="">
  <input type="hidden" name="p_process"    value="">
  <input type="hidden" name="p_userid"     value="">
</form>

<table cellspacing="0" cellpadding="0" border="0" width="100%">
<tr>
<td width="5"></td>
<td valign="top">
<!--신규공지사항시작-->
<table cellpadding="0" cellspacing="0" border="0" width="242" style="table-layout:fixed;">
		<COLGROUP>
              <COL width=242>
              <COL width=0>
		<tr valign="top">
		<td>
			<%if(box.getSession("userid").equals("")){%>
				<a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=moreNotice"><img src="/images/user/game/homepage/type1/tit_notice.gif">
			<%}else{%>
				<a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List"><img src="/images/user/game/homepage/type1/tit_notice.gif">
			<%}%>
		</td>
		</tr>
		<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0" width="242">
			<col width="5"><col width="235"><col width="2">
			<tr><td height="4" colspan="3"></td></tr>
	<% 
		int nlsize = (noticeList.size());
			for(int i = 0; i < nlsize; i++) {
			  DataBox dbox = (DataBox)noticeList.get(i);
			  int v_seqNotice  = dbox.getInt("d_seq");
	%>
			<tr height="18">
				<td></td>
				<td name="p_seq" style="color:#333333;font-size:11px;"><a href="javascript:selectList('<%=v_seqNotice%>')"><%=dbox.getString("d_adtitle")%></td>
				<!--<td style="color:#333333;font-size:11px;">[ <%=FormatDate.getFormatDate(dbox.getString("d_addate"),"yyyy-MM-dd")%> ]</td>-->
			</tr>
	 <%		}
			for(int j = 0; j < 3 - nlsize; j++){%>			
			<tr height="18">
				<td style="color:#333333;font-size:11px;"></td>	
			</tr>

			<!--<tr height="1"><td colspan="3" background="/images/user/game/homepage/type1/dot_blue.gif"></td></tr>-->
	<%		}%>
			</table>
		</td>
		</tr>
		<tr height="8"><td></td></tr>
		</table>
<!--신규공지사항끝-->
</td>
<td valign="top" align="right">
<!--뉴스레터시작-->
<table cellpadding="0" cellspacing="0" border="0" width="242" style="table-layout:fixed;">
		<COLGROUP>
              <COL width=242>
              <COL width=0>
		<tr valign="top">
		<td>
				<a href="/servlet/controller.homepage.HomeLetterServlet?p_process=moreNotice"><img src="/images/user/game/homepage/type1/tit_newletter.gif">
		</td>
		</tr>
		<tr>
		<td>
			<table border="0" cellpadding="0" cellspacing="0" width="242">
			<col width="5"><col width="230"><col width="2">
			<tr><td height="7" colspan="3"></td></tr>
			
				<% 
		int llsize = (letterList.size());
			for(int i = 0; i < llsize; i++) {
			  DataBox dbox = (DataBox)letterList.get(i);
			  int v_seqNotice  = dbox.getInt("d_seq");
	%>
			<tr height="18">
				<td></td>
				<td name="p_seq" style="color:#333333;font-size:11px;"><a href="javascript:selectLList('<%=v_seqNotice%>')"><%=dbox.getString("d_adtitle")%></td>
				<!--<td style="color:#333333;font-size:11px;">[ <%=FormatDate.getFormatDate(dbox.getString("d_addate"),"yyyy-MM-dd")%> ]</td>-->
			</tr>
	 <%		}
			for(int j = 0; j < 3 - nlsize; j++){%>			
			<tr height="18">
				<td style="color:#333333;font-size:11px;"></td>	
			</tr>

			<!--<tr height="1"><td colspan="3" background="/images/user/game/homepage/type1/dot_blue.gif"></td></tr>-->
	<%		}%>
	
			</table>
		</td>
		</tr>
		<tr height="8"><td></td></tr>
		</table>		
<!--뉴스레터끝-->
</td>
</tr>
</table>


