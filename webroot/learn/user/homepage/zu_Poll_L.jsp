
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
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
        farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 567, height = 371, top=0, left=0");
        document.pform1.target = "openWin"
        
        document.pform1.p_process.value = "insertPoll";
        document.pform1.p_seq.value = "<%=v_seq%>";
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
        farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 567, height = 371, top=0, left=0");
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
<table width="158" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/user/homepage/poll_tit.gif"></td>
  </tr>
  <tr> 
    <td valign="top" class="font_poll"><%=v_title%></td>
  </tr>
  <tr>
    <td><img src="/images/user/homepage/poll_line.gif"></td>
  </tr>
  
  <tr>

<%
  int v_pselnum = 0;
  String v_pseltxt = "";
  for(int pi = 0; pi < pslist.size(); pi++) {
  
      PollSelData data2   = (PollSelData)pslist.get(pi);
  
      v_pselnum = data2.getSelnum();
      v_pseltxt = data2.getSeltxt();
%>

  <tr> 
    <td valign="top"> <input type="radio" name="p_selnum" value="<%=v_pselnum%>"><%=v_pseltxt%></td>
  </tr>
<%}%>

  <tr>
    <td height="10"></td>
  </tr>
  <tr> 
    <td height="10" align="center"><a href="javascript:vote()"><img src="/images/user/button/btn_joinpoll.gif"></a>
      <a href="javascript:viewResult()"><img src="/images/user/button/btn_result.gif" border="0"></a> 
    </td>
  </tr>
</table>
</form>
<%}%>