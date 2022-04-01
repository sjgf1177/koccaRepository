<%
//**********************************************************
//  1. 제      목 : 홈페이지 메인     (B)
//  2. 프로그램명 : zu_MainB.jsp
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
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

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
<%@ include file="/learn/user/include/site_info.jsp" %>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topMainB.jsp"%>      
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
// 정보동의
function openInfomation() {
    window.open('/learn/user/homepage/pop_infomation_agree.html','openTong','width=450,height=328,scrollbars=no,resizable=no');
}
<%
   if (bLoginCheck) {
   // 처음로긴시 : 0, 동의후 : 1
       int isOk = bean.firstCheck(box);
       if (isOk == 0) {
%>
           openInfomation();
<%
       }
   }
%>
//-->
</SCRIPT>

<!-- center -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="/images/user/homepage/type2/bg_center.gif">
  <tr> 
    <td><table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="448" valign="top">
          <table width="448" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="5" class="contents_bg"></td>
              </tr>
              <tr> 
                <td>
                <!--플래쉬 left 시작-->
				<script language = "javascript" src = "/script/user_patch.js"></script>
				<script language='javascript'>
				setParam1('<%=tem_imgpath%><%=tem_menuflash%>');
				setParam2('448');
				setParam3('289');
				//alert("1111");
				object_patch('leftmenuB');
				setParam1('');
				setParam2('');
				setParam3('');
				</script>
                <!--object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="448" height="289">
                <param name="movie" value="<%=tem_imgpath+""+tem_mainflash%>">
                <param name="quality" value="high">
                <embed src="<%=tem_imgpath+""+tem_mainflash%>" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="448" height="289"></embed></object-->
                <!--플래쉬 left 종료-->
                </td>
              </tr>
              <tr>
                <td height="10"></td>
              </tr>
              <tr> 
                <td valign="top" style="padding-left:22;">
                  <table width="415" border="0" cellspacing="0" cellpadding="0">
<!--                    <tr> -->
<%
    for (int i=0; i<mainl_list.size(); i++) {
        DataBox dbox2 = (DataBox)mainl_list.get(i);

        v_m_gubun     = dbox2.getString("d_gubun");
        v_m_menuid    = dbox2.getString("d_menuid");
        v_m_menuname  = dbox2.getString("d_menuname");
        v_m_menuurl   = dbox2.getString("d_menuurl");

//        if (i % 2 == 0){
%>
<!--
                    </tr>
                    <tr> 
--->
<%
//        }
%>
<!--                      <td width="207 " valign="top"> -->
<!--  <%=v_m_menuname%> table start -->
<jsp:include page="<%=v_m_menuurl%>" flush="true" />
<!--  <%=v_m_menuname%> table end -->
<!--                     </td>-->
<%
    }
%>
<!--                    </tr>-->
                  </table>
                </td>
              </tr>
            </table></td>
          <td valign="top">
          <!-- center contents start -->
            <table width="452" border="0" cellpadding="0" cellspacing="0">

<%
    for (int i=0; i<mainc_list.size(); i++) {
        DataBox dbox1 = (DataBox)mainc_list.get(i);

        v_m_gubun     = dbox1.getString("d_gubun");
        v_m_menuid    = dbox1.getString("d_menuid");
        v_m_menuname  = dbox1.getString("d_menuname");
        v_m_menuurl   = dbox1.getString("d_menuurl");

        if (i < 2){
%>
              <tr> 
                <td valign="top" class="main_center"> 
<%
        } else if (i == 2) {
%>      
              <tr> 
                <td height="10"></td>
              </tr>
              <tr> 
                <td valign="top" style="padding-left:23;"> 
<%
        } else {
%>
              <tr> 
                <td valign="top" style="padding-left:23;"> 
<%
        }
%>

<!--  <%=v_m_menuname%> table start -->
<jsp:include page="<%=v_m_menuurl%>" flush="true" />
<!--  <%=v_m_menuname%> table end -->
                </td>
              </tr>
<%
    }
%>


            </table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottomB.jsp"%>
<!---------- copyright end  -------------------->

