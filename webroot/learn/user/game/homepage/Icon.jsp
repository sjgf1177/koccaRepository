
<%
//**********************************************************
//  1. 제      목: 바로가기아이콘 모음
//  2. 프로그램명 : Icon.jsp
//  3. 개      요: 바로가기아이콘 모음
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
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>        
        <table cellpadding="0" cellspacing="0" background="/images/user/game/homepage/type1/direct_gobg.gif" width="535">        
			<TR>
				  <TD width="80"><a href="/servlet/controller.course.CourseIntroServlet?p_process=SubjectList"><img src="/images/user/game/homepage/type1/direct_go.gif"></a></TD>
				  <TD><a href="/learn/user/game/course/gu_CourseIntro_I.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image66','','/images/user/game/homepage/type1/direct_go01on.gif',1)"><img src="/images/user/game/homepage/type1/direct_go01.gif" name="Image66" border="0"></a></TD>
				  <TD><a href="/learn/user/game/course/gu_CourseIntro_I.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image67','','/images/user/game/homepage/type1/direct_go02on.gif',1)"><img src="/images/user/game/homepage/type1/direct_go02.gif" name="Image67" border="0"></a></TD>
				  <TD><a href="/learn/user/game/course/gu_CourseIntro_I.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image68','','/images/user/game/homepage/type1/direct_go03on.gif',1)"><img src="/images/user/game/homepage/type1/direct_go03.gif" name="Image68" border="0"></a></TD>
				  <TD><a href="/learn/user/game/course/gu_CourseIntro_I.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image69','','/images/user/game/homepage/type1/direct_go04on.gif',1)"><img src="/images/user/game/homepage/type1/direct_go04.gif" name="Image69" border="0"></a></TD>
				  <TD width="18"><img src="/images/user/game/homepage/type1/direct_gos.gif" border="0"></TD>
			</TR>
			<!--<tr>
				<td background="/images/user/game/homepage/type1/tip_mainline.gif" height="10"></td>
			</tr>-->
        <!--<tr height="5"><td colspan="5"></td></tr>-->
        </table>
        <table cellpadding="0" cellspacing="0" width="536">
        	<tr>
        		<td align="center"><img src="/images/user/game/homepage/type1/tip_mainline.gif"></td>
        	</tr>
        	<tr>
        		<td height="3"></td>
        	</tr>
        </table>