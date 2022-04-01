<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_process = box.getString("p_process");
    int v_pageno = box.getInt("p_pageno");
    int v_pagesize = box.getInt("p_pagesize");
    int v_dispnum = 0;
    int v_totalpage = 0;           // 게시물총페이지수
    int v_totalrowcount =  0;

    String v_seq = "";
    String v_name="";
    String v_compnm = "";
    String v_indate = "";
    String v_email = "";
    String v_handphone = "";
    String v_reg7 = "";
    String v_realfile1 = ""; // 강의계획서
    String v_savefile1 = ""; // 강의계획서
    String v_file1 = "";
    String v_realfile2 = ""; // 증명자료
    String v_savefile2 = ""; // 증명자료
    String v_file2 = "";

    String sortByName = box.getStringDefault("sortByName", "N");
    String lectureName = box.getString("lectureName");

    ArrayList list = (ArrayList)request.getAttribute("infoList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<style type="text/css">
    a, img { border:0; outline:none; }
</style>
<script type="text/javascript">
    $(document).ready(function(){
    });

    function go(index) {
        document.form1.p_pageno.value = index;
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }
    function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }
    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }
    function whenSelection() {
        document.form1.p_pageno.value = 1;
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }
    function selectDetailView(seq){
        window.self.name="LectList";
        open_window("openMember","","100","100","1024","700",'','','','yes');
        document.form1.target = "openMember";
        document.form1.p_seq.value        = seq;
        document.form1.p_process.value = "detailView";
        document.form1.submit();
    }

    function selectPrint(seq){
        window.self.name="LectList";
        open_window("openPrint","","100","100","1024","700",'','','','yes');
        document.form1.target = "openPrint";
        document.form1.p_seq.value        = seq;
        document.form1.p_process.value = "selectPrint";
        document.form1.submit();
    }

    /**
     * 강사검색
     */
    function fnSearchLecturer() {
        var searchForm = document.form1;
        searchForm.submit();
    }

    /**
     * 엔터키 체크
     */
    function fnSearchLecturerByKey(e) {
        if (e.keyCode == "13") {
            fnSearchLecturer();
        }
    }

    /**
     * 이름 순 정렬
     */
    function fnSortByName() {
        var searchForm = document.form1;
        var currValue = searchForm.sortByName.value;
        if ( currValue == "Y" ) {
            searchForm.sortByName.value = "N";
        } else {
            searchForm.sortByName.value = "Y";
        }

        searchForm.submit();
    }
</script>

</head>

<body id="tutor" bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" action="/servlet/controller.off.OfflecturerCollusionAdminServlet">
    <input type="hidden" name="p_process"       value="<%= v_process %>"/>
    <input type="hidden" name="p_pageno"        value="<%= v_pageno %>"/>
    <input type="hidden" name="p_pagesize"      value="<%= v_pagesize%>"/>
    <input type="hidden" name="p_seq"           value=""/>
    <input type="hidden" name="sortByName"    value="<%= sortByName %>"/>

<table width="1000" border="0" cellspacing="0" cellpadding="0" >
    <tr>
        <td style="margin-top:20px; padding-left:14px; padding-top:12px;">

            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="0" class="form_table_bg">
                            <tr>
                                <td height="7" width="99%"></td>
                            </tr>
                            <tr>
                                <td align="center" width="99%" valign="middle">
                                    <table width="98%" border="0" cellspacing="0" cellpadding="0">
                                        <colgroup>
                                            <col cols="10%" />
                                            <col cols="80%" />
                                            <col cols="10%" />
                                        </colgroup>

                                        <tr>
                                            <th>강사명 : </th>
                                            <td align="left">
                                                <input type="text" id="oLectureName" name="lectureName" value="<%= lectureName %>" size="60" style="border:1 solid;" onkeypress="fnSearchLecturerByKey(event);" />
                                            </td>
                                            <td>
                                                <a href="javascript:fnSearchLecturer();"><img src="/images/admin/button/b_go.gif" alt="강사 검색" /></a>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td height="7" width="99%"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td align="center" valign="top">
            <div id="s_list">
                <br/><br/>
                <!-- 목록 시작 //-->
                <table class="table_out" cellspacing="0" cellpadding="3" border="0" summary="튜터 커뮤니티">
                    <caption></caption>
                    <colgroup>
                        <col width="10%"/>
                        <col width="10%"/>
                        <col width="10%"/>
                        <col width="10%"/>
                        <col width=""/>
                        <col width="20%"/>
                        <col width="18%"/>
                        <col width="15%"/>
                    </colgroup>
                    <thead>
                        <tr>
                            <th scope="col" class="table_title">No</th>
                            <th scope="col" class="table_title"><a href="javascript:fnSortByName();" <%= sortByName.equals("Y") ? "style='color:red;'" : "style='color:gray;'" %>>성명</a></th>
                            <th scope="col" class="table_title">주민등록번호</th>
                            <th scope="col" class="table_title">연락처</th>
                            <th scope="col" class="table_title">이메일</th>
                            <th scope="col" class="table_title">직장명</th>
                            <th scope="col" class="table_title">작성일</th>
                            <th scope="col" class="table_title">비고</th>

                        </tr>
                    </thead>
                    <tbody>
<%
    if(list.size() > 0 ) {
        for(int i = 0; i < list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i);

            v_seq         = dbox.getString("d_seq");
            v_name        = dbox.getString("d_name");
            v_compnm      = dbox.getString("d_compnm");
            v_reg7        = dbox.getString("d_reg7");
            v_handphone   = dbox.getString("d_handphone");
            v_email       = dbox.getString("d_email");
            v_indate      = FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy-MM-dd");

            v_realfile1   = dbox.getString("d_realfile1");
            v_realfile2   = dbox.getString("d_realfile2");
            v_savefile1   = dbox.getStringDefault("d_savefile1","empty");
            v_savefile2   = dbox.getStringDefault("d_savefile2","empty");

            v_dispnum = dbox.getInt("d_dispnum");
            v_totalpage = dbox.getInt("d_totalpage");
            v_totalrowcount = dbox.getInt("d_totalrowcount");

            if(!v_savefile1.equals("empty")){
                v_file1 = "<a href=/servlet/controller.library.DownloadServlet?p_savefile=" + v_savefile1 + "&p_realfile=" + v_realfile1 + " target=_blank><img src=\"/images/2012/sub/lectur/btn_hangeul.png\" class=\"icon\" alt=\"강의계획서\"/></a> ";
            }else{
                v_file1 = "";
            }
            if(!v_savefile2.equals("empty")){
                v_file2 = "<a href=/servlet/controller.library.DownloadServlet?p_savefile=" + v_savefile2 + "&p_realfile=" + v_realfile2 + " target=_blank><img src=\"/images/2012/sub/lectur/btn_alzip.png\" class=\"icon\" alt=\"증명자료\"/></a> ";
            }else{
                v_file2 = "";
            }
%>

                        <tr>
                            <td class="table_01_under_c9"><%=v_dispnum %></td>
                            <td class="table_02_1_under_c9"><a href="javascript:selectDetailView(<%=v_seq%>);"><%=v_name %></a></td>
                            <td class="table_02_1_under_c9"><%=v_reg7 %>******</td>
                            <td class="table_02_1_under_c9"><%=v_handphone %></td>
                            <td class="table_02_1_under_c9"><%=v_email %></td>
                            <td class="table_02_1_under_c9"><%=v_compnm %></td>
                            <td class="table_02_1_under_c9"><%=v_indate %></td>
                            <td class="table_02_1_under_c9"><%=v_file1 %><%=v_file2 %><a href="javascript:selectPrint(<%=v_seq%>);"><img src="/images/2012/sub/lectur/btn_output.png" alt="출력"/></a></td>
                        </tr>

<%
        }
    } else {
%>
                        <tr>
                            <td align="center" bgcolor="#FFFFFF" height="100" colspan="8">등록된 내용이 없습니다.</td>
                        </tr>
<%
    }
%>
                    </tbody>
                </table>
                <!-- 조회 끝 //-->

                <!-- total 시작 //-->
                <br>
                <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="right" valign="absmiddle">
                            <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
                        </td>
                    </tr>
                </table>
                <!-- total 끝 //-->
            </div>
        </td>
    </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>