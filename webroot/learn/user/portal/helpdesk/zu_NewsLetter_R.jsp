<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
	String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_select");

    int v_tabseq   = box.getInt("p_tabseq");
    int v_seq      = box.getInt("p_seq");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
	String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
	String v_luserid   = "";
    String v_gubun_view = "";
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치

    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용

    String v_upfile    ="";
    //String v_realfile  ="";

    DataBox dbox = (DataBox)request.getAttribute("selectNotice");

    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");
        v_startdate = dbox.getString("d_startdate");
        v_enddate   = dbox.getString("d_enddate");
        v_adtitle   = dbox.getString("d_adtitle");
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");
        v_adname    = dbox.getString("d_adname");
		v_luserid	= dbox.getString("d_luserid");
        v_cnt       = dbox.getInt("d_cnt");
		v_loginyn   = dbox.getString("d_loginyn");
		v_useyn   	= dbox.getString("d_useyn");
		v_grcodecd	= dbox.getString("d_grcodecd");
		v_popwidth	= dbox.getInt("d_popwidth");
		v_popheight = dbox.getInt("d_popheight");
		v_popxpos   = dbox.getInt("d_popxpos");
		v_popypos   = dbox.getInt("d_popypos");
		v_popup     = dbox.getString("d_popup");
		v_upfile    = dbox.getString("d_upfile");
		//v_realfile  = dbox.getString("d_realfile");
		v_uselist   = dbox.getString("d_uselist");
		v_useframe  = dbox.getString("d_useframe");

        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");
        //v_adcontent  = StringManager.replace(data.getAdcontent(),"\n","<br>");


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


%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->

<SCRIPT LANGUAGE="JavaScript">

	function listnotice(){
		document.nform2.action = "/servlet/controller.homepage.HomeLetterServlet";
		document.nform2.p_process.value = "List";
        document.nform2.submit();
	}

</SCRIPT>

<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform2" method="post">
<input type = "hidden" name = "p_pageno"        value = "<%=v_pageno %>" >
<input type = "hidden" name = "p_select"        value = "<%=v_search%>">
<input type = "hidden" name = "p_searchtext"    value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_process"       value = "">

            <h2><img src="/images/portal/studying/h2_tit7.gif" alt="뉴스레터" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>뉴스레터</strong></p></h2>

            <p><img src="/images/portal/studying/news_borimg.gif" alt="콘텐츠 진흥원에서 이메일로 전해드리는 뉴스레터입니다. 뉴스레터수신를 원하시면 개인정보변경에서 이메일 수신에 동의해주세요." /></p>

            <table class="board_view mg_t30">
            <!--[if ie]><colgroup><col width="55px" /><col width="400px" /><col width="25px" /><col width="100px" /></colgroup><![endif]-->
            <colgroup><col width="75px" /><col width="400px" /><col width="45px" /><col width="100px" /></colgroup>
            <tr>
                <th>제목</th>
                <td colspan="3" class="tit"><%=v_adtitle%></td>
            </tr>
            <tr class="bo">
                <th>작성일자</th>
                <td class="num"><%=v_addate%></td>
                <th>조회</th>
                <td class="fs_s"><%=v_cnt%></td>
            </tr>
            <tr>
                <td colspan="4" class="con">
                <div class="imgtxt">
                    <%=v_adcontent%>
                </div>
                </td>
            </tr>
            <!--tr>
                <td colspan="4" class="con">
                <div class="img">
                    <img src="/images/portal/thumb/newsletter_b.jpg" />
                </div>
                </td>
            </tr-->
            </table>

            <p class="board_btn"><a href="javascript:listnotice();" class="board_blue"><span>목록</span></a></p>
            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->