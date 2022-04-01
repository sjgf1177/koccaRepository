<%
//**********************************************************
//  1. 제      목 : 홈페이지 메인
//  2. 프로그램명  : gu_MainB.jsp
//  3. 개      요 : 홈페이지 메인
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 이나연 05.12.15
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
<%@ include file="/learn/user/game/include/topMainB.jsp"%>      
<!------- 글로벌메뉴, 상단 로고,메뉴 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

function winonload(){
//    if("<%=session.getAttribute("userid")%>" == "" || "<%=session.getAttribute("userid")%>" == "null") {
//        document.getElementById("p_userid").focus();
//    }
}

//-->
</SCRIPT>

        <!-- center -->
      <table width="1000" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="216" valign="bottom"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="216" height="606">
              <param name="movie" value="<%=tem_imgpath%><%=tem_mainflash%>">
              <param name="quality" value="high">
              <embed src="<%=tem_imgpath%><%=tem_mainflash%>" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="216" height="606"></embed></object></td>
          <td width="784" valign="top" background="/images/user/game/homepage/type2/con_bg.gif">
            <table width="784" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="581" align="center" valign="top">

<%
    for (int i=0; i<mainc_list.size(); i++) {
        DataBox dbox1 = (DataBox)mainc_list.get(i);

        v_m_gubun     = dbox1.getString("d_gubun");
        v_m_menuid    = dbox1.getString("d_menuid");
        v_m_menuname  = dbox1.getString("d_menuname");
        v_m_menuurl   = dbox1.getString("d_menuurl");

%>
        <%if( v_m_menuid.equals("07") ){
            if( (s_userid) != "" ){%>
        <!--  <%=v_m_menuname%> table start -->
        <jsp:include page="<%=v_m_menuurl%>" flush="true" />
        <!--  <%=v_m_menuname%> table end -->
            <%}else{}%>
        <%}else{%>
        <!--  <%=v_m_menuname%> table start -->
        <jsp:include page="<%=v_m_menuurl%>" flush="true" />
        <!--  <%=v_m_menuname%> table end -->
        <%}%>
<%  }
%>

                </td>
                <td width="203" align="right" valign="top"> 

<%
    for (int i=0; i<mainr_list.size(); i++) {
        DataBox dbox2 = (DataBox)mainr_list.get(i);

        v_m_gubun     = dbox2.getString("d_gubun");
        v_m_menuid    = dbox2.getString("d_menuid");
        v_m_menuname  = dbox2.getString("d_menuname");
        v_m_menuurl   = dbox2.getString("d_menuurl");

%>

                <!--  <%=v_m_menuname%> table start -->
                <jsp:include page="<%=v_m_menuurl%>" flush="true" />
                <!--  <%=v_m_menuname%> table end -->

<%  }
%>

                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottomB_main.jsp"%>
<!---------- copyright end  -------------------->

