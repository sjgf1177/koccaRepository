<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_I.jsp
//  3. 개      요: 공지사항 등록
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

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");
    String  v_selcomp = box.getString("p_selcomp");

    int v_pageno = box.getInt("p_pageno");
    int v_pagesize = box.getInt("p_pagesize");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search = box.getString("p_search");

    int v_tabseq = box.getInt("p_tabseq");
    int v_seq = box.getInt("p_seq");
    int v_upfilecnt = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int v_cnt = 0;            // 조회수
    String  v_gubun = "";           // 전체구분
    String  v_startdate = "";           // 팝업공지시작일
    String  v_enddate = "";           // 팝업공지종료일
    String  v_title = "";           // 제목
    String  v_content = "";           // 내용
    String  v_addate = "";           // 등록일
    String  v_adname = "";           // 등록자
    String  v_loginyn = "";           // 로그인유무
    String  v_useyn = "";           // 사용유무
    String  v_compcd = "";           // 대상회사
    int v_popwidth = 0;            // 팝업가로
    int v_popheight = 0;            // 팝업세로
    int v_popxpos = 0;            // 팝업x위치
    int v_popypos = 0;            // 팝업y위치
    String  v_popup = "";           // 팝업공지여부
    String  v_uselist = "";           // 게시판 리스트 사용 여부 (팝업 설정시)
    String  v_useframe = "";           // 
    String  v_isall = "";           // 사용 여부
    String  v_upfile = "";
    String  v_upfile_value = "";
    String  v_grcodecd = "";
    String  v_isanswer = "";           // 답변형 설정 여부
    String v_bannerimg = "";
    String v_save_bannerimg = "";
    String  v_banner_save_file = "";   // 배너이미지(저장파일명)
    
    DataBox dbox = (DataBox)request.getAttribute("selectEvent");
    if (dbox != null) {
        v_gubun = dbox.getString("d_gubun");
        v_startdate = dbox.getString("d_strdate");
        v_enddate = dbox.getString("d_enddate");
        v_title = dbox.getString("d_title");
        v_content = dbox.getString("d_content");
        v_addate = dbox.getString("d_indate");
        v_adname = dbox.getString("d_name");
        v_cnt = dbox.getInt("d_cnt");
        v_loginyn = dbox.getString("d_loginyn");
        v_useyn = dbox.getString("d_useyn");
        v_compcd = dbox.getString("d_compcd");
        v_popwidth = dbox.getInt("d_popwidth");
        v_popheight = dbox.getInt("d_popheight");
        v_popxpos = dbox.getInt("d_popxpos");
        v_popypos = dbox.getInt("d_popypos");
        v_popup = dbox.getString("d_popup");
        v_uselist = dbox.getString("d_uselist");
        v_useframe = dbox.getString("d_useframe");
        v_isall = dbox.getString("d_isall");
        v_grcodecd = dbox.getString("d_grcodecd");
        v_isanswer = dbox.getString("d_isanswer");
        v_bannerimg = dbox.getString("d_bannerimg");
        v_save_bannerimg = dbox.getString("d_save_bannerimg");
        v_banner_save_file = dbox.getString("d_bannerimg");
    }
    v_content = StringManager.replace(v_content, "&nbsp;","&amp;nbsp;" );
    
    String s_username = box.getSession("name");
    String s_userid = box.getSession("userid");

    String v_server = conf.getProperty("kocca.url.value");
    String v_tem_url = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;


    String s_gadmin = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_edate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    
    // 입력값 쳌 및 저장
    function insert_check() {

        document.form1.p_content.value = document.form1.Wec.MIMEValue;
        document.form1.p_content.value.replace("&","&amp;");

        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        
        if (document.form1.p_sdate.value == "") {
            alert("이벤트 시작일 입력하세요");
            document.form1.p_sdate.focus();
            return;
        }

        if (document.form1.p_edate.value == "") {
            alert("이벤트 종료일 입력하세요");
            document.form1.p_edate.focus();
            return;
        }

        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

        if (document.form1.Wec.Value.length < 3) {
            alert("내용을 입력하세요");
            return;
        }

        if (document.form1.Wec.Value.length < 3) {
            alert("내용을 입력하세요");
            return;
        }

        if(st_date > ed_date){
            alert("시작일이 종료일보다 큽니다.");
            return;
        }

        var st_date = make_date(document.form1.p_sdate.value);
        var ed_date = make_date(document.form1.p_edate.value);

        document.form1.p_startdate.value = st_date;
        document.form1.p_enddate.value = ed_date;
        document.form1.p_pageno.value = 1;
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value = "";
        document.form1.action = "/servlet/controller.homepage.EventAdminServlet";
        document.form1.p_process.value = "update";

        document.form1.submit();
    }

    // 목록보기
    function list() {
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

<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_pagesize"   value = "<%= v_pagesize %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_tabseq"     value = "<%=v_tabseq %>">
    <input type = "hidden" name="p_startdate"  value = "">
    <input type = "hidden" name="p_enddate"    value = "">
    <input type = "hidden" id="oOrgSaveFileNm" name="orgSaveFileNm" value="<%= v_save_bannerimg %>">
    <input type = "hidden" id = "p_content" name="p_content"    value = "<%=StringUtil.convertHtmlchars(v_content)%>">
    <input type = "hidden" name="p_seq"        value = "<%=v_seq %>">
    

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
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
                    <td width="85%" height="25" class="table_02_2"><%=s_userid%></td>
                </tr>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>등록일</strong></td>
                    <td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>이벤트기간</strong></td>
                    <td height="25" class="table_02_2">
                        <input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_startdate, "yyyy-MM-dd") %>'>
                        <input name="p_edate" id="p_edate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_enddate, "yyyy-MM-dd") %>'>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>사용유무</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="radio" name="p_use" value='Y' class="input" <%=v_useyn.equals("Y") ? "checked" : "" %> >사용&nbsp;
                        <input type="radio" name="p_use" value='N' class="input" <%=!v_useyn.equals("Y") ? "checked" : "" %> >미사용&nbsp;
                    </td>
                </tr>
                <tr>
                    <td height="25" class="table_title"><strong>썸네일 이미지<br/>(435 X 140)</strong></td>
                    <td height="25" class="table_02_2" >
                        <input type="FILE" name="p_file" size="84" class="input"> <br>
                        <a href="javascript:fnDownlaodFile('<%= v_save_bannerimg.replace("\\", "/") %>', '<%= v_bannerimg %>');">
                        <img src="<%=v_save_bannerimg %>" width=255 height=65 border=0></a>
                        (새로운 파일을 등록하면 기존 파일은 삭제됩니다.) 
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title" style="height:55px;"><strong>제목</strong></td>
                    <td height="25" class="table_02_2"><input type="text" name="p_title" size = 100 class="input" maxlength="80" style="height:25px;font-size:15px" value="<%=v_title %>"></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>내용</strong></td>
                    <td height="25" class="table_02_2">
                        <!-- 나모 액티브 스퀘어  시작 -->
                        <script language="javascript" src="/script/user_patch.js"></script>
                        <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted('p_content');</script>
                        <script language="javascript">object_namoActivepatch('100%','500');</script>
                        <!-- 나모 액티브 스퀘어  종료 -->
                    </td>
                </tr>
            </table>
            <!----------------- 공지사항 관리 끝 ----------------->

            <br>
            <!----------------- 저장, 취소 버튼 시작----------------->
            <table width="11%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
                </tr>
            </table>
            <!----------------- 저장, 취소 버튼 끝 ----------------->


        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
