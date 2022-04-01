<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list1 = null;
    ArrayList list2 = null;

    int     v_tabseq   = box.getInt("p_tabseq");
    int     v_seq      = box.getInt("p_seq");     
    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

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
    String v_isall      = "";           // 전체 공지 유무
    String v_onoffgubun = "";           // 온오프구분
    int v_popwidth   = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    
    String v_upfile    ="";
    //String v_realfile  ="";
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");
	int v_pagesize = box.getInt("p_pagesize");

	int	 v_totalrowcount =  0;

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    list2 = (ArrayList)request.getAttribute("selectNoticeList");
    
    String left_menu = "helpdesk";
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

	function pagesize(pageSize) {
		document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
		document.nform1.p_pageno.value = 1;
		document.nform1.p_pagesize.value = pageSize;
		document.nform1.p_process.value = "List";
		document.nform1.submit();
	}

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

//-->
</SCRIPT>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
    <input type = "hidden" name = "p_seq"    >
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
    
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
    <table width="672" border="0" cellpadding="0" cellspacing="1" bgcolor="ADBBD8">
      <tr>
        <td height="34" bgcolor="F0F0F0"><table border="0" align="center" cellpadding="3" cellspacing="0">
            <tr>
              <td><span class="tit_table">
                <select name="p_search" id="p_search">
                  <option value="adtitle"   <%if("adtitle".equals(v_search)) out.print(" selected");%>>제목</option>
                    <option value="adcontent" <%if("adcontent".equals(v_search)) out.print(" selected");%>>내용</option>
                    <option value="adname"    <%if("adname".equals(v_search)) out.print(" selected");%>>작성자</option>
                </select>
              </span></td>
              <td><input name="p_searchtext" type="text" class="input_search" id="p_searchtext" size="30" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/></td>
              <td><a href="javascript:select();"><img src="/images/portal/homepage_renewal/common/btn_search_in.jpg" alt="검색" width="46" height="24" /></a></td>
            </tr>
        </table></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <!--검색부분//-->
    <!--테이블부분-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">번호</td>
        <td class="tit_table">제목 </td>
        <td class="tit_table">작성일자</td>
        <td class="tit_table_right">조회수</td>
      </tr>
      
      <%

if(list2.size() != 0){
  for( int i = 0; i < list2.size(); i++) {

    DataBox dbox = (DataBox)list2.get(i);
    v_seq      = dbox.getInt("d_seq");
    v_cnt      = dbox.getInt("d_cnt");
    v_adtitle  = dbox.getString("d_adtitle");
    v_addate   = dbox.getString("d_addate");
    v_adname   = dbox.getString("d_adname");
    v_upfilecnt = dbox.getInt("d_filecnt");
    v_dispnum   = dbox.getInt("d_dispnum");
    v_totalpage = dbox.getInt("d_totalpage");
    v_totalrowcount = dbox.getInt("d_totalrowcount");
    v_rowcount  = dbox.getInt("d_rowcount");
    v_isall     = dbox.getString("d_isall");
    v_onoffgubun = dbox.getString("d_onoffgubun");
    if( v_onoffgubun.equals("C") ) {
		v_onoffgubun = "[온라인]";
    } else if( v_onoffgubun.equals("C") ) {
    	v_onoffgubun = "[오프라인]";
    } else {
    	v_onoffgubun = "";
    }
    
    v_addate = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");
    
    v_adtitle = StringUtil.getByteString(v_adtitle, 60);
    
%>
      
      <tr>
        <td class="ct_tit_table"><%=v_isall.equals("Y") ? "*" : v_dispnum%></td>
        <td class="ct_tit_table_left"><a href="javascript:selectView(<%=v_seq%>)">
                <% if( box.getSession("tem_grcode").equals("N000001") && !v_onoffgubun.equals("") ) { %>
                	<%=v_onoffgubun %><%=v_adtitle%> <%=v_addate.equals(FormatDate.getDate("yyyy.MM.dd")) ? "<img src=\"/images/portal/ico/ico_board_n.gif\" alt=\"new\" />" : "" %>
                <% } else { %>
                	<%=v_adtitle%> <%=v_addate.equals(FormatDate.getDate("yyyy.MM.dd")) ? "<img src=\"/images/portal/ico/ico_board_n.gif\" alt=\"n\" />" : "" %>
                <% } %>
                </a></td>
        <td class="ct_tit_table"><%=v_addate%></td>
        <td class="ct_tit_table_right"><%=v_cnt%></td>
        
        <%      }
    }else{%>

            <tr> 
               <td class="num" colspan="4">등록된 내용이 없습니다.</td>
            </tr>
<%  }   %>
        
      </tr>
      <tr>
        <td height="1" colspan="4" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="4" bgcolor="F7F7F7"></td>
      </tr>
    </table>    
    <!--타이틀부분//-->
    
    	<!-- total 시작 -->
		 <%= PageUtil.re_printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
		<!-- total 끝 -->
    
    </td>
    <td width="20">&nbsp;</td>
    

    
  </tr>
</table>
    
    <%}else{ %>

            <h2><img src="/images/portal/studying/h2_tit1.gif" alt="공지사항" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>공지사항</strong></p></h2>
 
            <p><img src="/images/portal/studying/notice_borimg.gif" alt="학습지원센터에 오신것을 환영합니다.콘텐츠진흥원에서 알려드리는 새소식과 이벤트를 확인하실 수 있습니다." /></p>
            
            <p class="board_serach">
                <select name="p_search">
                    <option value="adtitle"   <%if("adtitle".equals(v_search)) out.print(" selected");%>>제목</option>
                    <option value="adcontent" <%if("adcontent".equals(v_search)) out.print(" selected");%>>내용</option>
                    <option value="adname"    <%if("adname".equals(v_search)) out.print(" selected");%>>작성자</option>
                </select>
                <input type="text" name="p_searchtext" class="inbox" style="width:135px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/>
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" />
            </p>
                        
            <table class="board_list">
            <colgroup><col width="63px" /><col width="460px" /><col width="106px" /><col width="62px" /></colgroup>
            <thead>
            <tr>
                <th><p>번호</p></th>
                <th><p>제목</p></th>
                <th><p>작성일자</p></th>
                <th class="end"><p>조회수</p></th>
            </tr>
            </thead>
            <tbody>
<%

if(list2.size() != 0){
  for( int i = 0; i < list2.size(); i++) {

    DataBox dbox = (DataBox)list2.get(i);
    v_seq      = dbox.getInt("d_seq");
    v_cnt      = dbox.getInt("d_cnt");
    v_adtitle  = dbox.getString("d_adtitle");
    v_addate   = dbox.getString("d_addate");
    v_adname   = dbox.getString("d_adname");
    v_upfilecnt = dbox.getInt("d_filecnt");
    v_dispnum   = dbox.getInt("d_dispnum");
    v_totalpage = dbox.getInt("d_totalpage");
    v_rowcount  = dbox.getInt("d_rowcount");
    v_isall     = dbox.getString("d_isall");
    v_onoffgubun = dbox.getString("d_onoffgubun");
    if( v_onoffgubun.equals("C") ) {
		v_onoffgubun = "[온라인]";
    } else if( v_onoffgubun.equals("C") ) {
    	v_onoffgubun = "[오프라인]";
    } else {
    	v_onoffgubun = "";
    }
    
    v_addate = FormatDate.getFormatDate(v_addate, "yyyy.MM.dd");
    
    v_adtitle = StringUtil.getByteString(v_adtitle, 60);
    
%>
            <tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="num"><%=v_isall.equals("Y") ? "*" : v_dispnum%></td>
                <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>)">
                <% if( box.getSession("tem_grcode").equals("N000001") && !v_onoffgubun.equals("") ) { %>
                	<%=v_onoffgubun %><%=v_adtitle%> <%=v_addate.equals(FormatDate.getDate("yyyy.MM.dd")) ? "<img src=\"/images/portal/ico/ico_board_n.gif\" alt=\"n\" />" : "" %>
                <% } else { %>
                	<%=v_adtitle%> <%=v_addate.equals(FormatDate.getDate("yyyy.MM.dd")) ? "<img src=\"/images/portal/ico/ico_board_n.gif\" alt=\"n\" />" : "" %>
                <% } %>
                </a></td>
                <td class="num"><%=v_addate%></td>
                <td class="num"><%=v_cnt%></td>
            </tr>
<%      }
    }else{%>

            <tr> 
               <td class="num" colspan="4">등록된 내용이 없습니다.</td>
            </tr>
<%  }   %>
            </tbody>
            </table>

        <!-- 페이징 영역 시작 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
        
        <%} %>
            </form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->

