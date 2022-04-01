<%
//**********************************************************
//  1. 제      목: 홈페이지 버그신고
//  2. 프로그램명 : BugSingo.jsp
//  3. 개      요: 홈페이지 버그신고
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.21
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

%>
<script language="JavaScript">
<!--
var adBtns = new Array('01.gif','02.gif','03.gif','04.gif','05.gif','06.gif','07.gif','08.gif','09.gif','10.gif','11.gif','12.gif','13.gif','14.gif');
var adUrl = new Array('http://cms.hsc.ac.kr:8080/ct/new/index.htm','http://dept.kaywon.ac.kr/animation.dept','http://subweb.kpu.ac.kr/game/index.do','http://dept.dpc.ac.kr/_EZAI/game','http://www.hansei.org','http://www.sdh.hs.kr','http://www.yewon.ac.kr/Subject/game/index.jsp','http://game.wsu.ac.kr/','http://ani.dmc.ac.kr/','http://www.game.hs.kr/','http://gacon.woosuk.ac.kr/','http://ct.cheju.ac.kr','http://www.anigo.or.kr','http://www.sugong.org/');
function getRand(n) {
             var r = Math.round(Math.random()*n-0.5);
             if ( r < 0 ) { r=0; } return r;
}
function bottomBarAds() {
             var i, k, t, f = 0;
             var sHtml = '';
             var sTmp = '';
             for ( i=0; i<14;i++ ) {
             	/*
                           k = getRand(adBtns.length);
                           if ( ! (f & (1<<k)) ) {
                                        f |= 1<<k; i++;
                */
                k = i;
    		sHtml += "<span style='cursor:hand;width:190;height:108' onMouseOver='HisemBanner.stopRolling();' onMouseOut='HisemBanner.doRolling();' border='0'>  ";
    		sHtml += "	<table width='100%' border='0' cellspacing='0' cellpadding='0'>                                                                          ";
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center><a href='"+adUrl[k]+"' target=_blank><img src='/images/user/game/homepage/type1/banner/"+adBtns[k]+"' width='179' height='50' border='0'></a></td>                ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center height=4></td>                                                                                                      ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		t = k+2;
      		if(t >= 13) {
      				t = getRand(adBtns.length);
      		}
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center><a href='"+adUrl[t]+"' target=_blank><img src='/images/user/game/homepage/type1/banner/"+adBtns[t]+"' width='179' height='50' border='0'></a></td>                ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center height=4></td>                                                                                                      ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		sHtml += "	</table>                                                                                                                                 ";
      		sHtml += "</span>                                                                                                                                    ";
                           //}
             }
          document.write(sHtml);
}
//-->
</script>
<script language="javascript" src="/script/banner.js"></script>
<table width="190" height="149" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="24"><img src="/images/user/game/homepage/type1/banner/banner_title.gif" width="190" height="24"></td>
  </tr>
  <tr>
    <td align="center" background="/images/user/game/homepage/type1/banner/benner_bg.gif">
    	<div id='Banner' style='position:relative; width:190px; height:108px; overflow:hidden;'>
    		<div id='highsem_banner' style='position:relative;border:0 solid;'>
			<script language=javascript>bottomBarAds();</script>
      		</div>
      	</div>
      </td>
  </tr>
  <tr>
    <td height="2"></td>
  </tr>
</table>
<script language='javascript'>
	/***** ("div객체","배너 태그","배너 height","롤링 방향","속도","방향");*******/
	HisemBanner = new RollingBanner('highsem_banner', 'span', 1512, 345, 30, 'up');
	HisemBanner.doRolling();
</script>