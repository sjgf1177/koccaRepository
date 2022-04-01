<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process  = box.getString("p_process");
    
    String s_userid = box.getSession("userid");
    int v_pageno   = box.getInt("p_pageno");
    String v_selcomp  = box.getString("p_selcomp");

    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");

    int v_tabseq        = box.getInt("p_tabseq");
    int v_seq           = box.getInt("p_seq");

    int v_cnt        = 0;            // 조회수
    String v_gubun      = "";           // 전체구분
    String v_startdate  = "";           // 팝업공지시작일
    String v_enddate    = "";           // 팝업공지종료일
    String v_adtitle    = "";           // 제목
    String v_adcontent  = "";           // 내용
    String v_addate     = "";           // 등록일
    String v_adname     = "";           // 등록자
    String v_aduserid   = "";           // userid

    String v_gubun_view = "";
    String v_loginyn    = "";           // 로그인유무
    String v_useyn      = "";           // 사용유무
    String v_compcd     = "";           // 대상회사
    int v_popwidth      = 0;            // 팝업가로
    int v_popheight     = 0;            // 팝업세로
    int v_popxpos       = 0;            // 팝업x위치
    int v_popypos       = 0;            // 팝업y위치
                        
    String v_popup      = "";           // 팝업여부
    String v_uselist    = "";           // 리스트사용
    String v_useframe   = "";           // 프레임사용
    String v_isall      = "";
    String v_grcodecd   = "";
    String v_isanswer  = "";           // 답변형 설정 여부
    String v_bannerimg = "";
    String v_save_bannerimg  = "";   // 배너이미지(저장파일명)
    String v_userid = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectEvent");
    
    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");            
        v_startdate = dbox.getString("d_strdate"); 
        v_enddate   = dbox.getString("d_enddate");   
        v_adtitle   = dbox.getString("d_title");  
        v_adcontent = dbox.getString("d_content");
        v_addate    = dbox.getString("d_indate");   
        v_adname    = dbox.getString("d_name");   
        v_cnt       = dbox.getInt("d_cnt");      
        v_loginyn   = dbox.getString("d_loginyn");   
        v_useyn     = dbox.getString("d_useyn");     
        v_compcd    = dbox.getString("d_compcd");    
        v_popwidth  = dbox.getInt("d_popwidth");     
        v_popheight = dbox.getInt("d_popheight");    
        v_popxpos   = dbox.getInt("d_popxpos");      
        v_popypos   = dbox.getInt("d_popypos");      
        v_popup     = dbox.getString("d_popup");     
        v_uselist   = dbox.getString("d_uselist");   
        v_useframe  = dbox.getString("d_useframe");  
        v_isall     = dbox.getString("d_isall");  
        v_grcodecd  = dbox.getString("d_grcodecd");  
        v_aduserid  = dbox.getString("d_aduserid");
        v_isanswer  = dbox.getString("d_isanswer");   
        v_bannerimg  = dbox.getString("d_bannerimg");
        v_save_bannerimg  = dbox.getString("d_save_bannerimg");
        v_userid = dbox.getString("d_userid");

        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy/MM/dd");
        if(v_isanswer.equals("Y")) v_isanswer = "사용";
        else v_isanswer = "사용안함";
     }  
     
    if (v_gubun.equals("Y")) v_gubun_view = "전체";
    else if (v_gubun.equals("N")) v_gubun_view = "일반";
    else if (v_gubun.equals("P")) v_gubun_view = "팝업";
    
    if (v_uselist.equals("")) v_uselist = "N";
    if (v_useframe.equals("")) v_useframe = "N";
    
    if(v_loginyn.equals("Y")) v_loginyn = "로그인후"; 
    else if(v_loginyn.equals("N")) v_loginyn = "로그인전";
    else v_loginyn = "전체";
    
    
    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";
     
//대상회사
    int tmplen= 0;
    String compcd="'1'";
    if(!v_compcd.equals("")) {
        compcd=v_compcd.substring(0, v_compcd.length()-1);
        compcd=StringManager.replace(compcd, ",", "','");
        compcd="'"+compcd+"'";
    }
    
    String grcodecd="'1'";
    if(!v_grcodecd.equals("")) {
        grcodecd=v_grcodecd.substring(0, v_grcodecd.length()-1);
        grcodecd=StringManager.replace(grcodecd, ",", "','");
        grcodecd="'"+grcodecd+"'";
    }
    
    
    NoticeAdminBean bean = new NoticeAdminBean();
    
    String s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
        v_gadmin = s_gadmin.substring(0,1);
    }
    
%>

<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    // 게시물 수정
    function modify_notice() {
        document.form1.action = "/servlet/controller.homepage.EventAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    //게시물삭제
    function delete_notice() {
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.homepage.EventAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }

    // 목록보기
    function list_notice() {
        document.form1.action = "/servlet/controller.homepage.EventAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    /**
     * 첨부파일 다운로드
     */
    function fnDownlaodFile( saveFileNm, fileNm ) {
        var url = "/servlet/controller.library.DownloadServlet";
        var param = "?p_savefile=" + saveFileNm + "&p_realfile=" + fileNm;

        location.href = url + param;
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search" value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq" value = "<%=v_seq %>">
    <input type = "hidden" name = "p_saveFileNm" value = "<%=v_save_bannerimg %>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top"> 
            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr> 
                    <td><img src="/images/admin/portal/s.1_14.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!----------------- title 끝 ----------------->

            <br>
            <br>
            <!----------------- 공지사항 관리 시작 ----------------->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>작성자</strong></td>
                    <td height="25" class="table_02_2"><%= v_userid %></td>
                </tr>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>등록일</strong></td>
                    <td height="25" class="table_02_2"><%= v_addate %></td>
                </tr>
                <tr class="table_02_2"> 
                    <td height="25" class="table_title" ><strong>이벤트 기간</strong></td>
                    <td class="table_02_2"><%=FormatDate.getFormatDate(v_startdate,"yyyy/MM/dd")%> ~ <%=FormatDate.getFormatDate(v_enddate,"yyyy/MM/dd")%>
                </tr>
                <tr> 
                    <td width="15%" class="table_title"><strong>사용유무</strong></td>
                    <td height="25" class="table_02_2"><%=v_useyn%></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>제목</strong></td>
                    <td height="25" class="table_02_2"><b><%= v_adtitle %></b></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>내용</strong></td>
                    <td height="50" class="table_02_2"><br><%= v_adcontent %><br></td>
                </tr>
                <tr> 
                    <td width="15%" class="table_title"><strong>썸네일 이미지</strong></td>
                    <td height="25" class="table_02_2">
                        <a href="javascript:fnDownlaodFile('<%= v_save_bannerimg.replace("\\", "/") %>', '<%= v_bannerimg %>');">
                        <img src="<%=v_save_bannerimg %>" width=255 height=65 border=0></a>
                    </td>
                </tr>
            </table>
            <!----------------- 공지사항 관리 끝 ----------------->

            <br>
            <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
            <table width="16%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                    <%if(v_gadmin.equals("A") || v_gadmin.equals("B") || ( s_userid.equals(v_aduserid) && (v_gadmin.equals("H") || v_gadmin.equals("K"))) ){%>
                    <td align="center"><a href="javascript:modify_notice()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>&nbsp;</td>
                    <td align="center"><a href="javascript:delete_notice()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;</td>
                    <%}%>
                    <td align="center"><a href="javascript:list_notice()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
                </tr>
            </table>
            <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
