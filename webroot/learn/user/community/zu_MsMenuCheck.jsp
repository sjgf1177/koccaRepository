<%
//**********************************************************
//  1. 제      목: 커뮤니티명 중복체크
//  2. 프로그램명 : zu_CmuPopNameCheck.jsp
//  3. 개      요: 커뮤니티명의 중복을 체크한다.
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 이연정
//  7. 수      정: 2005.07.01 이연정
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_cmuno      = box.getString("p_cmuno");//마스터 관련페이지는 무조건 들어가야함.
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    int  recordcnt       = Integer.parseInt(String.valueOf(request.getAttribute("recordcnt")));
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>커뮤니티</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
//중복체크
function uf_boxsexlect() {
    if(document.form1.p_title.value ==''){
       alert('커뮤니티명을 입력하여야합니다.');document.form1.p_title.focus();return;
    }
    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "boarddupchkPage";
    document.form1.submit();
}


//전화면으로 데이터전송
function uf_send() {
    if (document.form1.p_rowcnt.value ==99){
        alert('중복확인을 하여야합니다.');document.form1.p_title.focus();return;
    }

    if (document.form1.p_rowcnt.value !=0){
        alert('사용할실 수 없는 명칭입니다');return;
    }
    opener.document.form1.p_ins_title.value='<%=box.getString("p_title")%>';
    opener.document.form1.p_dupchk.value ='Y';
    this.close();
  
}
//-->
</script>
</head>
<body id="popup"><!-- popup size : 490* -->
<form name = "form1"  method = "post">
	<input type = "hidden" name = "p_process" value=""/>
	<input type = "hidden" name = "p_rowcnt"  value="<%=recordcnt%>"/>
	<input type = "hidden" name = "p_brd_fg"  value="<%=box.getString("p_brd_fg")%>"/>
	<input type = "hidden" name = "p_cmuno"  value="<%=box.getString("p_cmuno")%>"/>
    
    <div id="pop_header">
        <h1><img src="/images/portal/community/pop_h1_tit4.gif" alt="커뮤니티" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap" class="message_top">
            <p class="searchbox_com">
                <span class="txt">게시판 명칭 중복확인</span>
                <input type="text" name="p_title" class="inbox" style="width:144px;" value="<%=box.getString("p_title")%>" onkeyup="javascript:document.form1.p_rowcnt.value ='99';" onkeypress="javascript:fnKeyPressEnter(event, 'uf_boxsexlect');"/>
                <input type="image" src="/images/portal/btn/btn_overlapconfirm.gif" class="va_m mg_r12" alt="검색" />
            </p>
            <% if(recordcnt !=0){%>
            <p class="com_txt">사용하실 수 없습니다</p>
            <% } else {%>
            <p class="com_txt">사용하실 수 있습니다</p>
            <% }%>
        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn">
        <% if(recordcnt==0){%>
        <a href="javascript:uf_send();" class="btn_gr"><span>사용</span></a>
        <% }%>
        <a href="javascript:this.close();" class="btn_gr"><span>닫기</span></a>
        </p>
    </div>
</form>
</body>
</html>