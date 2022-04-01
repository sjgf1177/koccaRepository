<%
//**********************************************************
//  1. 제      목: 홈페이지 라이브폴
//  2. 프로그램명 : LivePoll.jsp
//  3. 개      요: 홈페이지 라이브폴
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
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.homepage.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String tem_imgpath = box.getSession("tem_imgpath");
    
    
    PollBean pbean = new PollBean();
    
    int v_pollseq = pbean.getPollSeq(box);
    int v_votecnt = pbean.getPollVoteCheck(box);
    PollData    pdata = new PollData();
    ArrayList pslist = new ArrayList();
    
    box.put("p_seq", String.valueOf(v_pollseq));
    pdata = pbean.selectPoll(box);     
    pslist = pbean.selectPollSel(box);  
    
    
%>  

<%
  
  int v_seq         = 0;
  String v_title    = "";
  String v_started  = ""; 
  String v_ended    = "";
  int    v_total    = 0;

  
  
  
  if(pdata != null){
  
  v_seq      =pdata.getSeq(); 
  v_title    =pdata.getTitle();
  v_started  =pdata.getStarted();
  v_ended    =pdata.getEnded();
  v_total    =pdata.getTotal();

String puserid = box.getSession("userid");
%>
<SCRIPT LANGUAGE="JavaScript">
    function vote() {
	var num = document.pform1.p_selnum.value;
<%
    if (puserid.equals("")) {
%>
            alert("로그인후 투표해 주세요.");
            return;
<%
    }
%>
        if (!hasCheckedRadio(document.pform1.p_selnum)) {
            alert("선택해 주세요.");
            return;
        }

        window.self.name = "winSelectView";
        farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 610, height = 355, top=0, left=0");
        document.pform1.target = "openWin"
        
        document.pform1.p_process.value = "insertPoll";
        document.pform1.p_seq.value = "<%=v_seq%>";
		document.pform1.p_selnum.value= num;
        document.pform1.action = "/servlet/controller.homepage.PollServlet";
        document.pform1.submit();

        farwindow.window.focus();
        document.pform1.target = window.self.name;
     }
     
     function viewResult() {
<%
    if (puserid.equals("")) {
%>
            alert("로그인후 조회해 주세요.");
            return;
<%
    }
%>
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 610, height = 355, top=0, left=0");
        document.pform1.target = "openWin"

        document.pform1.p_process.value = "resultPoll";
        document.pform1.p_seq.value = "<%=v_seq%>";
        document.pform1.action = "/servlet/controller.homepage.PollServlet";
        document.pform1.submit();

        farwindow.window.focus();
        document.pform1.target = window.self.name;
     }
//-->
</SCRIPT>

<form name="pform1">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_seq" value="">

	<table width="190" border="0" cellspacing="0" cellpadding="0" height="200">
	  <tr> 
		<td colspan="2"><img src="/images/user/kocca/homepage/type1/st_livepoll.gif" border="0"> <!--usemap="#Map6"--></td>
	  </tr>
	  <tr> 
		<td height="5" colspan="2"></td>
	  </tr>
	  <tr> 
		<td width="20"><div align="center"><img src="/images/user/kocca/homepage/type1/i_quistion.gif" width="12" height="12"></div></td>
		<td width="170" class="tbl_bleft4"><%=v_title%></td>
	  </tr>
	  <tr > 
		<td height="1" colspan="2" background="/images/user/kocca/homepage/type1/bg_dot_line.gif"></td>
	  </tr>
<%
  int v_pselnum = 0;
  String v_pseltxt = "";
  for(int pi = 0; pi < pslist.size(); pi++) {
  
      DataBox dbox = (DataBox)pslist.get(pi);
  
       v_pselnum = dbox.getInt("d_selnum");
       v_pseltxt = dbox.getString("d_seltxt");
%>
	  <tr> 
		<td height="66" colspan="2" class="tbl_gleft"><input type="radio" name="p_selnum" value="<%=v_pselnum%>">
		  <%=v_pseltxt%></td>
	  </tr>
<%}%>
	  <tr> 
		<td height="1" colspan="2" class="linecolor_main01"></td>
	  </tr>
	  <tr> 
		<td height="26" colspan="2"><div align="center">
			<a href="javascript:vote()"><img src="/images/user/kocca/button/btn_lets_join.gif" width="49" height="17"></a>
			<img src="/images/user/kocca/homepage/type1/blank.gif" width="4" height="10">
			<a href="javascript:viewResult()"><img src="/images/user/kocca/button/btn_view_result.gif" width="49" height="17"></a>
		</div></td>
	  </tr>
	  <tr> 
		<td height="1" colspan="2" class="linecolor_main01"></td>
	  </tr>
	  <tr> 
		<td height="15" colspan="2"></td>
	  </tr>
	</table>
<%}%>

</form>

<!--map name="Map6">
  <area shape="rect" coords="189,3,220,23" href="#">
</map-->