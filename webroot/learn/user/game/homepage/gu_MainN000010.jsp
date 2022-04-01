<%
//**********************************************************
//  1. 제      목 : 홈페이지 메인
//  2. 프로그램명 : zu_Main.jsp
//  3. 개      요 :
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 정상진 2005.07.8
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.net.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "java.net.InetAddress" %>
<%@ page import = "java.net.InetSocketAddress" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");

	//로그인 체크 변수
    boolean bLoginCheck = false;

    //세션에 사용자 정보가 있는지 검사
    if (session.getAttribute("userid") != null ) {
        bLoginCheck = true;
    }

    box.put("onLoad","onload=\"winonload();\"");

    LoginBean bean = new LoginBean();

    String v_m_gubun        = "";
    String v_m_menuid       = "";
    String v_m_menuname     = "";
    String v_m_menuurl      = "";
    ArrayList mainl_list = (ArrayList)request.getAttribute("mainl_list");
    ArrayList mainr_list = (ArrayList)request.getAttribute("mainr_list");
    ArrayList mainc_list = (ArrayList)request.getAttribute("mainc_list");

%>

<%//템플릿 정보%>
<%@ include file="/learn/user/game/include/site_info.jsp" %>

<!------- 글로벌메뉴, 상단 로고,메뉴 시작 ------------>
<%@ include file="/learn/user/game/include/topMainN000010.jsp"%>
<!------- 글로벌메뉴, 상단 로고,메뉴 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">

    function winonload()
    {
    //    if("<%=session.getAttribute("userid")%>" == "" || "<%=session.getAttribute("userid")%>" == "null")
    //    {
    //        document.getElementById("p_userid").focus();
    //    }
    }

</SCRIPT>
<SCRIPT type=text/JavaScript>
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
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

// 과정 내용보기
function whenSubjInfo(subj,tabnum){
    document.form1.p_subj.value     = subj;
    document.form1.p_tabnum.value   = tabnum;
    document.form1.p_process.value  = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.course.CourseKyowonServlet';
    document.form1.target = "_self";
    document.form1.submit();
}

//-->
</SCRIPT>
<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="SubjectList">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_iscourseYn' value="N">
    <input type='hidden' name='p_tabnum'  value="common">
    <input type='hidden' name='p_subj' value="">
</form>
    <table cellpadding="0" cellspacing="0" width="216" height="100%" style="table-layout:fixed" border="0" bgcolor="#FFFFFF">
        <col width="1"><col width="8"><col width="190"><col width="13">
        <tr>
        <td colspan="3" height="15"></td>
        <tr valign="top">
        <td bgcolor="#cccccc"></td>
        <td></td>
        <td align="right">
            <table cellpadding="0" cellspacing="0" width="190" border="0">
            <tr>
             <td>
<comment id=__NSID__>
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="190" height="127">
  <param name="movie" value="/images/user/homepage/type1/N000010/N000010_banner.swf">
  <param name="quality" value="high">
  <param name="wmode" value="transparent">
  <embed src="/images/user/homepage/type1/N000010/N000010_banner.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="190" height="127"></embed></object></comment>
 <script> __ws__(__NSID__);</script>
             </td>
            </tr>
            </table>
<%
    for (int i=0; i<mainl_list.size(); i++) {
        DataBox dbox1 = (DataBox)mainl_list.get(i);

        v_m_gubun     = dbox1.getString("d_gubun");
        v_m_menuid    = dbox1.getString("d_menuid");
        v_m_menuname  = dbox1.getString("d_menuname");
        v_m_menuurl   = dbox1.getString("d_menuurl");

%>
		<!--  <%=v_m_menuname%> table start -->
		<jsp:include page="<%=v_m_menuurl%>" flush="true" />
		<!--  <%=v_m_menuname%> table end -->
<%	}
%>
        </td>
        </tr>
        </table>
    </td>
    <TD width="558" align=center bgcolor="#FFFFFF">
<table border="0" cellspacing="0" cellpadding="0" width="539">
<tr>
<td height="15"></td>
<td valign="top" rowspan="2" bgcolor="#FFFFFF"><!--<img src="/images/user/game/homepage/type1/con_bg.gif">--></td>
</tr>
<tr>
<td>
<jsp:include page="/learn/user/game/homepage/Login.jsp" flush="true" />
<!-- 메인내용 start -->
				  <table width="536" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="left">
								<table width="536" border="0" cellspacing="0" cellpadding="0">
								  <tr>
								    <td><img src="/images/user/homepage/type1/N000010/curri_main_top.jpg" width="541" height="44"></td>
								  </tr>
								  <tr>
								    <td height="170" valign="top" background="/images/user/homepage/type1/N000010/curri_main_middle.jpg">
								     <table width="100%" border="0" cellspacing="0" cellpadding="0">
								        <tr>
								          <td><table width="100" border="0" align="center" cellpadding="0" cellspacing="3">
								              <tr><td colspan=3 height=3></td></tr>
								              <tr>
								                <td><a href="#"><img src="/images/user/homepage/type1/N000010/curriculum_b_01.gif" border="0"></a></td>
								                <td><a href="#"><img src="/images/user/homepage/type1/N000010/curriculum_b_02on.gif" border="0"></a></td>
								                <td><a href="#"><img src="/images/user/homepage/type1/N000010/curriculum_b_03.gif" border="0"></a></td>
								              </tr>
								            </table></td>
								        </tr>
								        <tr>
								          <td height="30">&nbsp;</td>
								        </tr>
								        <tr>
								          <td height="40">
								           <table width="300" border="0" align="right" cellpadding="0" cellspacing="0">
								              <tr>
								                <td valign=middle>
								                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
								                   <tr>
								                     <td width="6"><img src="/images/user/homepage/type1/N000010/dot.gif"></td>
								                     <td><a href="javascript:whenSubjInfo('T000099', 'common');">게임이용지도</a></td>
								                   </tr>
								                   <tr>
								                     <td><img src="/images/user/homepage/type1/N000010/dot.gif"></td>
								                     <td><a href="javascript:whenSubjInfo('T000100', 'common');">게임과몰입예방</a></td>
								                   </tr>
								                   </table>
								                </td>
								                <td width="40">&nbsp;</td>
								              </tr>
								            </table></td>
								        </tr>
								      </table></td>
								  </tr>
								</table>
                      </td>
                    </tr>
                    <tr>
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr align="center">
                            <td width="35%" height="50"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><a href="/servlet/controller.course.CourseKyowonServlet?p_process=KyowonSubPage&p_gubun=1"><img src="/images/user/homepage/type1/N000010/re_01.gif" border="0"></a></td>
                                </tr>
                              </table></td>
                            <td width="35%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><a href="/servlet/controller.course.CourseKyowonServlet?p_process=KyowonSubPage&p_gubun=7"><img src="/images/user/homepage/type1/N000010/re_02.gif" border="0"></a></td>
                                </tr>
                              </table></td>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><a href="/servlet/controller.study.MyClassServlet?p_process=StudyHistoryList"><img src="/images/user/homepage/type1/N000010/re_03.gif" border="0"></a></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <br>
<!-- 메인내용 end -->
</td>
</tr>
</table>
    </td>
    <td valign="top">
		<jsp:include page="/learn/user/game/homepage/NoticeN000010.jsp" flush="true" />
        <table cellpadding="0" cellspacing="0" width="224" height="100%" style="table-layout:fixed" border="0">
        <col width="223"><col width="1">
        <tr><td colspan="2" height="15"><img src="/images/user/game/homepage/type1/right_top.gif"></td></tr>
        <tr>
        <TD vAlign=top align=center width=223 bgcolor="#FFFFFF" >
        </td>
        <td bgcolor="#FFFFFF"></td></tr>
        </table>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottomA.jsp"%>
<!---------- copyright end  -------------------->


