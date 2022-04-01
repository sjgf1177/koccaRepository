<%
//**********************************************************
//  1. 제      목 : 홈페이지 메인
//  2. 프로그램명  : ku_MainA.jsp
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
<%@ include file="/learn/user/kocca/include/site_info.jsp" %>

<!------- 글로벌메뉴, 상단 로고,메뉴 시작 ------------>
<%@ include file="/learn/user/kocca/include/topMainA.jsp"%>      
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

<script language="javascript">

function getCookie( name ){ 
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



// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_open" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/open_pop.jsp','kocca_open','scrollbars=no, left=0, top=0, width=450,height=560');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_event" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/open_event.jsp','kocca_event','scrollbars=no, left=0, top=0, width=400,height=370');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
if ( getCookie( "kocca_invitation" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
PopCookieWindow = window.open('/learn/user/kocca/pop/open_invitation.jsp','kocca_invitation','scrollbars=no, left=0, top=0, width=400,height=500');
PopCookieWindow.opener = self;
}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_curri" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/open_curri.jsp','kocca_curri','scrollbars=no, left=845, top=0, width=400,height=500');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
//if ( getCookie( "kocca_work" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
//PopCookieWindow = window.open('/learn/user/kocca/pop/open_work.jsp','kocca_work','scrollbars=no, left=410, top=0, width=425,height=645');
//PopCookieWindow.opener = self;
//}

// 팝업창에서 만들어진 쿠키 PopCookie 의 값이 done 이 아니면(즉, 체크하지 않으면,)
if ( getCookie( "kocca_special" ) != "done" ) { // 새창(pop.htm)을 띄웁니다
PopCookieWindow = window.open('/learn/user/kocca/pop/open_special.jsp','kocca_special','scrollbars=no, left=410, top=0, width=494,height=585');
PopCookieWindow.opener = self;
}


// -->
</SCRIPT>

<table width="182" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="9" valign="top">&nbsp;</td>
		<td> 

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

		<td width="7" valign="top">&nbsp;</td>
	</tr>
</table>
</td>
<td width="491" valign="top" background="/images/user/kocca/homepage/type1/bg_center.gif">
	<table width="491" border="0" cellspacing="0" cellpadding="0">
		<tr> 
			<td width="15" valign="top">&nbsp;</td>
			<td>
				<table width="460" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="15">&nbsp;</td>
					</tr>
				</table>

<%
    for (int i=0; i<mainc_list.size(); i++) {
        DataBox dbox1 = (DataBox)mainc_list.get(i);

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
				<table width="223" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</td>
			<td width="243" valign="top">
				<table width="243" border="0" cellspacing="0" cellpadding="0">
			</td>
		<tr>
			<td>
				<img src="/images/user/kocca/homepage/type1/r_contents_top.gif" width="243" height="20">
			</td>
		</tr>
		<tr>
			<td height="100%" align="right" background="/images/user/kocca/homepage/type1/bg_r_contents.gif">
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
<%	}
%>
			</td>
		</tr>
	</table>
</td>
</tr>
</table>



<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottomA_main.jsp"%>
<!---------- copyright end  -------------------->