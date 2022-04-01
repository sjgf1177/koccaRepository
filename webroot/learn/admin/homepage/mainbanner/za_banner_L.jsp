<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage= "/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    ArrayList<DataBox> bannerList = (ArrayList<DataBox>)request.getAttribute("bannerList");

    DataBox dbox = null;
    String process = box.getString("p_process");
    int seq = 0;
    String title ="";
    String onoff_flag ="";
    String use_yn ="";
    int sort_order = 0;
    String start_dt ="";
    String end_dt ="";
    String reg_dt ="";
    String reg_id ="";
    String period_yn ="";
    String fixed_flag ="";
%>
<html>
<head>
<title>메인배너 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--

    /**
     * 등록 화면으로 이동
     */
    function fnInsert() {
        document.form1.action = "/servlet/controller.homepage.BannerAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    /**
     * 수정 화면으로 이동
     */
    function fnUpdate(seq) {
        var url ="/servlet/controller.homepage.BannerAdminServlet";
        var param= "?&p_process=updatePage&p_seq=" + seq ;
        location.href = url + param ;
    }

    /*
    * 정렬순서 변경
    */
    function fnSortChange( idx, chgValue ){
        var tempValue = "";
        var orgValue = $("#orgVal" + idx).val();

        for( var i = 0 ; i < $("select[name=p_sort_order]").length; i++ ) {
            if ( idx != i ) {
                tempValue = $("select[name=p_sort_order]").eq(i).val();

                if ( chgValue == tempValue ) {
                    $("select[name=p_sort_order]").eq(i).val(orgValue);
                    $("#orgVal" + i).val(orgValue);
                    $("#orgVal" + idx).val(chgValue);

                    break;
                }

            }
        }
    }

    /**
     * 배너 팝업
     */
    function fnViewBanner() {
        var objUseYn = $("select[name=p_use_yn]");
        var seqParam = "";

        for( var k = 0 ; k < objUseYn.length ; k++ ) {
            if (objUseYn.eq(k).val() == "Y") {
                seqParam += "&v_seq=" + $("input[name=v_seq]").eq(k).val();
            }
        }

        var new_Open = window.open("/servlet/controller.homepage.BannerAdminServlet?p_process=viewBanner" + seqParam,"openclassWindow",'scrollbars=yes,width=1000,height=600,resizable=no');

        // document.form1.p_process.value = "viewBanner";
        // document.form1.target = "openclassWindow";
        // document.form1.action = "/servlet/controller.homepage.BannerAdminServlet?" + seqParam;

        // document.form1.submit();
        // document.form1.target = "_self";

    }
    /**
    * 사용여부/정렬순서 수정
    */
    function fnListUpdate(){
        if ($("input:checked").length < 1){
            alert("메인에 고정될 배너를 선택하세요.");
            return;
        }else{
            if(confirm("해당 내용을 저장하면 홈페이지에 즉시 적용됩니다.\n변경된 내용을 저장하시겠습니까?")){
                document.form1.action = "/servlet/controller.homepage.BannerAdminServlet";
                document.form1.p_process.value = "listUpdate";
                document.form1.submit();
            }

        }
    }

//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="get">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="p_seq" value="" />
<table width="1000" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td style="padding-top:20px;"><h2>메인배너 관리</h2></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <!-- form 시작 //-->

            <!-- form 끝 //-->
            <!-- 배너 목록 끝 //-->
            <BR>
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center">
<%
            if ( bannerList != null || bannerList.size() != 0 ) {
%>
                        <input type="button" value="(팝업)미리보기" onclick="javascript:fnViewBanner();">
                        <input type="button" value="사용여부/정렬순서 적용" onclick="javascript:fnListUpdate();">
<%
            }
%>
                        <input type="button" value="등록화면" onclick="javascript:fnInsert();">
                    </td>
                    <td width="100" align="right" valign="middle">
                        <input type="button" value="리셋" onclick="javascript:document.form1.reset();">
                    </td>

                </tr>
            </table>
            <br />

            <!-- 배너 목록 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="8" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" height="25"><b>제목</b></td>
                    <td class="table_title" width="8%" ><b>온/오프</b></td>
                    <td class="table_title" width="8%"><b>사용여부</b></td>
                    <td class="table_title" width="8%"><b>정렬순서</b></td>
                    <td class="table_title" width="18%"><b>게시기간</b></td>
                    <td class="table_title" width="10%"><b>등록일</b></td>
                    <td class="table_title" width="8%" ><b>등록자</b></td>
                    <td class="table_title" width="5%" ><b>메인고정</b></td>
                </tr>
<%

        if ( bannerList == null || bannerList.size() == 0 ) {
%>
                <tr>
                    <td class="table_02_5" colspan="7">등록된 내용이 없습니다.</td>
                </tr>
<%
        } else {

            for(int i = 0; i < bannerList.size(); i++) {

                dbox = (DataBox)bannerList.get(i);

                seq = dbox.getInt("d_seq");
                title = dbox.getString("d_title");
                onoff_flag = dbox.getString("d_onoff_flag");
                use_yn = dbox.getString("d_use_yn");
                sort_order = dbox.getInt("d_sort_order");
                start_dt = dbox.getString("d_start_dt").substring(0,4)+"/"+dbox.getString("d_start_dt").substring(4,6)+"/"+dbox.getString("d_start_dt").substring(6,8);
                end_dt = dbox.getString("d_end_dt").substring(0,4)+"/"+dbox.getString("d_end_dt").substring(4,6)+"/"+dbox.getString("d_end_dt").substring(6,8);
                reg_dt = dbox.getString("d_reg_dt");
                reg_id = dbox.getString("d_reg_id");
                period_yn = dbox.getString("d_period_yn");
                fixed_flag = dbox.getString("d_fixed_flag");

%>
                <tr>
                    <td class="table_02_2">
                        <input type="hidden" name="seq" value="<%=seq%>" />
                        <a href="javascript:fnUpdate(<%=seq%>);" ><%=title%></a></td>
                    <td class='table_02_1'>
<%
                    if(onoff_flag.equals("ON")){
%>
                        온라인
<%
                    }else if(onoff_flag.equals("OFF")){
%>
                        오프라인
<%
                    }
%>
                    </td>
                    <td class='table_02_1'>
                        <input type="hidden" name="v_seq" value="<%= seq %>">
                        <select id="oUse_Yn" name="p_use_yn">
                            <option value="" <%= (use_yn.equals("")) ? "selected='selected'" : "" %>>--선택--</option>
                            <option value="N" <%= (use_yn.equals("N")) ? "selected='selected'" : "" %>>N</option>
                            <option value="Y" <%= (use_yn.equals("Y")) ? "selected='selected'" : "" %>>Y</option>
                        </select>
                    </td>
                    <td class='table_02_1'>
                        <select id="oSort_order" name="p_sort_order" onchange="javascript:fnSortChange(<%= i %>, this.value);">
                            <option value="" <%= (sort_order==0) ? "selected='selected'" : "" %>>--선택--</option>
<%
                        for(int j = 0; j < bannerList.size(); j++) {
%>
                            <option value="<%=j+1%>" <%= (sort_order== (j+1)) ? "selected='selected'" : "" %>><%=j+1%></option>
<%

                        }
%>
                        </select>
                        <input type="hidden" id="orgVal<%= i %>" value="<%= sort_order %>" />
                    </td>
                    <td class='table_02_1'><%=start_dt%> ~ <%=end_dt%></td>
                    <td class='table_02_1'><%=reg_dt%></td>
                    <td class='table_02_1'><%=reg_id%></td>
                    <td class='table_02_1'><input type="radio" name="fixed_flag" value="<%=seq%>" <%=fixed_flag.equals("Y") ? "checked" : "" %>></td>
                </tr>
<%
            }
        }
%>

            </table>

        </td>
    </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
