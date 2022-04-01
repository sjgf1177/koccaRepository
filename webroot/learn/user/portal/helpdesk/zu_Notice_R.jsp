<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
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
    String  v_search     = box.getString("p_search");

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
    String v_loginyn    = "";           // 로그인유무
    String v_useyn      = "";           // 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
    int v_popwidth   = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치

    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용



    DataBox dbox = (DataBox)request.getAttribute("selectNotice");

    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");
        v_startdate = dbox.getString("d_startdate");
        v_enddate   = dbox.getString("d_enddate");
        v_adtitle   = dbox.getString("d_adtitle");
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");
        v_adname    = dbox.getString("d_adname");
        v_luserid   = dbox.getString("d_luserid");
        v_cnt       = dbox.getInt("d_cnt");
        v_loginyn   = dbox.getString("d_loginyn");
        v_useyn     = dbox.getString("d_useyn");
        v_grcodecd  = dbox.getString("d_grcodecd");
        v_popwidth  = dbox.getInt("d_popwidth");
        v_popheight = dbox.getInt("d_popheight");
        v_popxpos   = dbox.getInt("d_popxpos");
        v_popypos   = dbox.getInt("d_popypos");
        v_popup     = dbox.getString("d_popup");
        v_uselist   = dbox.getString("d_uselist");
        v_useframe  = dbox.getString("d_useframe");

        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");

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


<!-- 스크립트영역 -->
<SCRIPT LANGUAGE="JavaScript">
	//rsg20171110
	function copyUrl( seq ){
		var url = '<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()%>/servlet/controller.homepage.HomeNoticeServlet?menuid=07&target=_self&p_process=selectView&p_seq='+seq;
		var IE=(document.all)?true:false;

		//if (IE) {
			//if ( window.clipboardData.setData('Text', url )) 
			//	alert('선택하신 게시물의 고유주소(URL)가 클립보드에 복사되었습니다.\n\Ctrl+V 또는 붙여넣기로 ');
		//} else {
			prompt("Ctrl+C를 눌러 클립보드로 복사하세요", url);
		//}
	}
    function listnotice(){
    	document.nform2.p_process.value = "List";
        document.nform2.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="nform2" method="post">
<input type = "hidden" name = "p_process" >
<input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
<input type = "hidden" name = "p_search" value = "<%=v_search %>">

	
	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	
	<table>
		<tr>
			<td>
	
		 <!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/stitle_02.gif" alt="공지사항" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>공지사항</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="4" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td width="12%" class="tit_table">제목</td>
        <td colspan="3" class="ct_tit_table_lright"><strong><%=v_adtitle%></strong></td>
      </tr>
      <tr>
        <td class="tit_table">작성일자</td>
        <td class="ct_tit_table_left"><%=v_addate%></td>
        <td width="8%" class="tit_table">조회</td>
        <td class="ct_tit_table_right"><%=v_cnt%></td>
      </tr>
      <tr>
        <td colspan="4" class="ct_tit_table_lright"><%=v_adcontent%></td>
      </tr>
      <%
      Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
      Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
      String i_boardStyle        = "board_view";                                // 보드 스타일
      %>     
      <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
      <tr>
        <td height="1" colspan="4" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="4" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="h_road"><a href="javascript:listnotice()"><img src="/images/portal/homepage_renewal/myclass/btn_list.jpg" alt="목록" width="46" height="25" /></a></td>
      </tr>
    </table>    
    <!--타이틀부분//-->    
    </td>
    <td width="20">&nbsp;</td>        
  </tr>
</table>



	
	<%}else{ %>

            <h2><img src="/images/portal/studying/h2_tit1.gif" alt="공지사항" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>공지사항</strong></p></h2>
 
            <p class="boardimg"><img src="/images/portal/studying/studying_notice_txt.gif" alt="학습지원센터에 오신것을 환영합니다.콘텐츠진흥원에서 알려드리는 새소식과 이벤트를 확인하실 수 있습니다." /></p>
            
            <table border="0px" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td style="padding-bottom:20px;">
                    <table class="board_view mg_t30">
                        <tr>
                            <th width="55px">제목</th>
                            <td colspan="3" style="font-weight:bold;"><%=v_adtitle %></td>
                        </tr>
                        <tr class="bo">
                            <th >작성일자</th>
                            <td style="padding:6px 0 5px 20px; font-size:11px; font-family:Tahoma;"> <%=v_addate%></td>
                            <th >조회</th>
                            <td style="font-size:11px !important;"><%=v_cnt%></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><%=v_adcontent%></td>
            </tr>
            </table>
            
            <!-- 파일첨부 시작 -->
            <%
                Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                String i_boardStyle        = "board_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
 
            <p class="board_btn">
            	<a href="javascript:listnotice()" class="board_blue"><span>목록</span></a>
            	<a href="javascript:copyUrl('<%= v_seq %>')" class="board_blue"><span>링크복사</span></a>
           	</p>
        </div>
    </div>
    <%} %>
 
</form>
<!-- form 끝 -->


<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->

