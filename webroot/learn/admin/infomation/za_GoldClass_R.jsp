<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_I.jsp
//  3. 개      요: 열린강좌 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 8. 1
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process = box.getString("p_process");
    String v_selcomp = box.getString("p_selcomp");

    int v_pageno = box.getInt("p_pageno");
    int v_pagesize = box.getInt("p_pagesize");

    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");

    int v_seq = box.getInt("p_seq");

    String v_lecnm = "";   // 강의명
    String v_tutornm           = "";   // 강사명
    String v_vodurl = "";   // 강좌 URL
    String v_tutorcareer = "";   // 약력
    String v_lectime = "";   // 강좌 시간
    String v_genre = "";   // 장르
    String v_creator = "";   // 제작
    String v_intro = "";   // 개요
    String v_contents = "";   // 내용
    String v_tutorauthor = "";   // 저서
    String v_width_s = "";   // 창넓이
    String v_height_s = "";   // 창높이
    String v_openyn = "";   // 오픈여부
    String v_useyn = "";   // 사용여부
    String v_img_save_file = "";   // 강좌이미지(저장파일명)
    String v_tutor_save_file = "";   // 강사이미지(저장파일명)
    String v_subtitle = "";   // 자막내용

    DataBox dbox = (DataBox)request.getAttribute("selectOffExpert");
    if (dbox != null) {
        v_lecnm = dbox.getString("d_lecnm");
        v_tutornm = dbox.getString("d_tutornm");
        v_vodurl = dbox.getString("d_vodurl");
        v_tutorcareer = dbox.getString("d_tutorcareer");
        v_lectime = dbox.getString("d_lectime");
        v_genre = dbox.getString("d_genre");
        v_creator = dbox.getString("d_creator");
        v_intro = dbox.getString("d_intro");
        v_contents = dbox.getString("d_contents");
        v_tutorauthor = dbox.getString("d_tutorauthor");
        v_width_s = dbox.getString("d_width_s");
        v_height_s = dbox.getString("d_height_s");
        v_openyn = dbox.getString("d_openyn");
        v_useyn = dbox.getString("d_useyn");
        v_img_save_file = dbox.getString("d_vodimg");
        v_tutor_save_file = dbox.getString("d_tutorimg");
        v_subtitle = dbox.getString("d_subtitle");
    }

    String s_gadmin = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
        v_gadmin = s_gadmin.substring(0,1);
    }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/css" href="/css/admin_style.css">
<script language="VBScript" src="/script/cresys_lib.vbs"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src= "/script/cresys_lib.js"></script>
<script type="text/javascript">
<!--
//저장
    function modify_article() {
        document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
        document.form1.target="_self";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function delete_article() {
        if ( confirm ("정말삭제하시겠습니까?") ) {
            document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
            document.form1.target="_self";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        } else {
            return;
        }
    }

    function list() {
        document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
        document.form1.target="_self";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function openGoldClass(urls,w,h) {

        if (w.length>0) {
            window.open("about:blank", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");

        }else{
            window.open("about:blank", "ViewVod", "top=0, left=0, width=600, height=500, status=no, resizable=no");
        }
        document.form1.action="/servlet/controller.infomation.GoldClassAdminServlet";
        document.form1.p_process.value = "popUpVod";
        document.form1.target="ViewVod";
        document.form1.submit();
    }

//-->
</script>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
 
<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%= v_pageno %>">
    <input type = "hidden" name="p_pagesize"   value = "<%= v_pagesize %>">
    <input type = "hidden" name="p_search"     value = "<%= v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%= v_searchtext %>">
    <input type = "hidden" name="p_seq"        value = "<%= v_seq %>">



<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td><img src="/images/admin/portal/s.1_19.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <br />
            <!-- 열린강좌 관리 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr>
                    <td width="15%" class="table_02_2"><strong>강좌명</strong></td>
                    <td height="25" class="table_02_2"><%=v_lecnm %>
                        <a href="javascript:openGoldClass('<%=v_vodurl %>','<%=v_width_s %>','<%=v_height_s %>')">&nbsp;&nbsp;<img src="/images/admin/button/btn_aviview.gif" align="absmiddle" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_02_2" colspan="2"><strong>강사소개</strong></td>
                </tr>
                <tr>
                    <td height="25" class="table_02_2" colspan="2">
                        <table width="97%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td height="10" colspan="2"></td>
                            </tr>
                            <tr>
                                <td width="140" valign="top">
                                    <table width="100" height="120" border="0" cellpadding="0" cellspacing="0" bgcolor="e0e0e0">
                                        <tr>
                                            <td style="text-align:center; vertical-align:middle;">
<%
    if ( v_tutor_save_file == null || v_tutor_save_file.equals("") ) {
%>
                                                <img src="/images/common/no_profile_img.png" width="130" height="130" border="0" alt="" />
<%
    } else {
%>
                                                <img src="/servlet/controller.library.DownloadServlet?p_savefile=<%= v_tutor_save_file %>" width="130" height="130" border="0" alt="" />
<%
    }
%>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>
                                    <table width="100%" border="0" cellspacing="2" cellpadding="8">
                                        <tr>
                                            <td width="60" valign="top"><strong><font color="33939D">강사명</font></strong></td>
                                            <td><%=v_tutornm %></td>
                                        </tr>
                                        <tr>
                                            <td valign="top"><strong><font color="33939D">약력</font></strong></td>
                                            <td><%=v_tutorcareer.replace("\r\n","<br />") %></td>
                                        </tr>
                                        <tr>
                                            <td valign="top"><strong><font color="33939D">저서</font></strong></td>
                                            <td><%=v_tutorauthor.replace("\r\n","<br />") %></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="10" colspan="2"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <td width="15%" class="table_02_2" colspan="2"><strong>강좌소개</strong></td>
                </tr>
                <tr>
                    <td width="15%" class="table_02_2"><strong>개요</strong></td>
                    <td height="25" class="table_02_2"><%=v_intro.replace("\r\n","<br />") %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_02_2"><strong>내용</strong></td>
                    <td height="25" class="table_02_2"><%=v_contents.replace("\r\n","<br />") %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_02_2"><strong>자막</strong></td>
                    <td height="25" class="table_02_2"><div style="height:170px; overflow-y:scroll;"><%=StringManager.convertHtmlchars(v_subtitle) %></div></td>
                </tr>
            </table>
            <!-- 열린강좌 관리 끝 //-->

            <br />

            <!-- 저장, 취소 버튼 시작 //-->
            <table width="16%" border="0" cellspacing="0" cellpadding="0">
                <tr>
<%
    if(v_gadmin.equals("A") || v_gadmin.equals("B") || (v_gadmin.equals("H") || v_gadmin.equals("K")) ) {
%>
                    <td align="center"><a href="javascript:modify_article()"><img src="/images/admin/button/btn_modify.gif" border="0" /></a></td>
                    <td align="center"><a href="javascript:delete_article()"><img src="/images/admin/button/btn_del.gif" border="0" /></a></td>
<%
    }
%>
                    <td align="center"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0" /></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
