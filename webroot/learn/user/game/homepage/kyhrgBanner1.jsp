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
             var i = 1;
             var k, t, f = 0;
             var sHtml = '';
             var sTmp = '';
             for ( k=0; k<14;k++ ) {
				i = k+i;
    		sHtml += "<span style='cursor:hand;width:190;height:108' onMouseOver='HisemBanner.stopRolling();' onMouseOut='HisemBanner.doRolling();' border='0'>  ";
    		sHtml += "	<table width='100%' border='0' cellspacing='0' cellpadding='0'>                                                                          ";
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center><a href='"+adUrl[k]+"' target=_blank><img src='/images/user/game/homepage/type1/banner/"+adBtns[k]+"' width='179' height='50' border='0'></a></td>                ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center height=4>"+k+"</td>                                                                                                      ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center><a href='"+adUrl[i]+"' target=_blank><img src='/images/user/game/homepage/type1/banner/"+adBtns[i]+"' width='179' height='50' border='0'></a></td>                ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		sHtml += "	  <tr>                                                                                                                                   ";
      		sHtml += "	    <td align=center height=4>"+i+"</td>                                                                                                      ";
      		sHtml += "	  </tr>                                                                                                                                  ";
      		sHtml += "	</table>                                                                                                                                 ";
      		sHtml += "</span> ";

             }
             document.write(sHtml);
}
//-->
</script>
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
/**
*@ 중요한부분은 div 부분의 높이(height) 값을 확실히 불러와야하고
*위치(top,left) 부분을 잘 맞춰야 함..
*@설명 : div트릭..  div안에 div을 넣어서 위로 위치조정해줌..appendchild로 없어진 부분을 맨끝에 추가..
*            계속 반복하게 됨.. 결국 롤링하는 효과.
*RollingBanner=function("div객체","배너 태그","배너 height","롤링 방향","속도","딜레이");
*당연히 딜레이가 속도보다 커야..표시가 남..
*/
RollingBanner=function(obj,tag,hei,wid,spd,rtype)
{
	this.ban_obj = document.getElementById(obj); //div객체
	this.ban_tag = this.ban_obj.getElementsByTagName(tag);
	this.ban_height = hei; //배너 or text 높이
	this.ban_width = wid; //가로
//	this.ban_direction = drt; // 방향을 결정함(div태그이므로 top,left)
	this.ban_speed = spd; //속도
	this.ban_delay = 2000; //딜레이는 고정시켜놨음
	this.ban_cnt = 0; //배너의 top,left 위치 를 결정  (0은 최상위,가장왼쪽)
	this.ban_timer;
	this.ban_rolling_type = rtype;
}

/*배너의 세로로 롤링함수*/
RollingBanner.prototype.doRolling=function()
{
	if(this.ban_rolling_type == "up" || this.ban_rolling_type == "down"){
		this.rollingY();
	}else if(this.ban_rolling_type == "left" || this.ban_rolling_type == "right") {
		this.rollingX();
	}
}

/*배너의 가로 롤링함수*/
RollingBanner.prototype.rollingX=function()
{
	obj=this;
	this.rollingTypeChecker(this.ban_rolling_type);
	if(this.ban_width > tmpcnt) {
		this.ban_obj.style.left = this.ban_cnt;
		this.ban_timer = setTimeout("obj.rollingX()",this.ban_speed);
	} else {
		this.ban_obj.appendChild(this.ban_tag[0]);
		this.ban_obj.style.left = 0;
		this.ban_timer = setTimeout("obj.rollingX()",2000);
		this.ban_cnt = 0;
	}
}

/*배너의 세로 롤링함수*/
RollingBanner.prototype.rollingY=function()
{
	obj = this; //객체참조
	this.rollingTypeChecker(this.ban_rolling_type);
	if(this.ban_height > tmpcnt) 	{
		this.ban_obj.style.top = this.ban_cnt;
		this.ban_timer = setTimeout("obj.rollingY()",this.ban_speed);
	} else	 {
		this.ban_obj.appendChild(this.ban_tag[0]);
		this.ban_obj.style.top = this.ban_cnt;
		this.ban_timer = setTimeout("obj.rollingY()",this.ban_delay);
		this.ban_cnt = 0;
	}
}

RollingBanner.prototype.stopRolling=function()
{
	clearTimeout(this.ban_timer);
}

/*배너의 방향을 결정하는 함수(left -> right, left <- right , top -> bottom, top <- bottom)*/
RollingBanner.prototype.rollingTypeChecker=function(rtype)
{
	if(this.ban_rolling_type == "right" || this.ban_rolling_type == "down") {
		this.ban_cnt++;
		tmpcnt = this.ban_cnt;
	}else if(this.ban_rolling_type == "left" || this.ban_rolling_type == "up") {
		this.ban_cnt--;
		tmpcnt = -this.ban_cnt;
	}else { //default
		this.ban_cnt++;
		tmpcnt = this.ban_cnt;
	}
}

/***** ("div객체","배너 태그","배너 height","롤링 방향","속도","방향");*******/
HisemBanner = new RollingBanner('highsem_banner', 'span', 1521, 345, 1, 'up');
HisemBanner.doRolling();
</script>
<!-- 345 -->