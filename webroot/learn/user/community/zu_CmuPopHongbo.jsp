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

    String  v_cmuno = box.getString("p_cmuno");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");


    DataBox hongboBox       = (DataBox)request.getAttribute("selectHongbo");

%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>커뮤니티 홍보</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
//페이지이동
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "gomsmemberPop";
    document.form1.submit();
}

//조회
function uf_searchOK(pageNum) {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityPopUpServlet";
    document.form1.p_process.value = "gomsmemberPop";
    document.form1.submit();
}

function goSubIndex(cmuno) {
    window.open("/servlet/controller.community.CommunityIndexServlet?p_process=selectmyindex&p_cmuno="+cmuno, "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 920, height = 660, top=0, left=0");

    self.close();
}
//-->
</script>
</head>
<body id="popup" onload="self.focus();"><!-- popup size : 750* -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"    value="" />
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>" />

    <div id="pop_header">
        <h1><img src="/images/portal/community/pop_h1_tit5.gif" alt="커뮤니티 홍보" /></h1>
    </div>
    <div id="pop_container">
        <div id="contentwrap" class="message_top">
            <dl class="photo_list1">
                <dt><img src="<%=hongboBox.getString("d_savefile").equals("") ? "/images/portal/thumb/main_110_110.jpg" : "/servlet/controller.library.DownloadServlet?p_savefile="+hongboBox.getString("d_savefile")%>" alt="커뮤니티 홍보" width="110px" height="110px" /></dt>
                <dd class="fw_b"><a href="javascript:goSubIndex('<%=hongboBox.getString("d_cmuno") %>')"><%=hongboBox.getString("d_cmu_nm")%></a></dd>
                <dd>회원수 : <%=hongboBox.getString("d_member_cnt") %>명</dd>
                <dd>개설일 : <%=FormatDate.getFormatDate(hongboBox.getString("d_register_dte"), "yyyy.MM.dd")%></dd>
            </dl>
            
            <div class="info_txt">
                <%=hongboBox.getString("d_contents") %>
            </div>

            <p class="info_txt1"><span>※</span> 홍보요청은 마스터관리>홍보요청 메뉴를 통해 요청하실 수 있습니다.</p>
        </div>
    </div>
    <div id="pop_footer">
        <p class="f_btn"><a href="javascript:goSubIndex('<%=hongboBox.getString("d_cmuno") %>')" class="btn_gr"><span>이동</span></a><a href="javascript:self.close();" class="btn_gr"><span>닫기</span></a></p>
    </div>
</form>
</body>
</html>
