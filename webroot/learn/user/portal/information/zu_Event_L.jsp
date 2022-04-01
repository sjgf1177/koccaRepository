<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list2 = null;

    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    String  v_selSeminarGubun   = box.getString("p_selSeminarGubun");
    
    int v_pageno    = box.getInt("p_pageno");
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
	int v_pagesize = box.getInt("p_pagesize");
    
    int    v_seq     = 0;              // 일련번호
    int    v_cnt     = 0;              // 조회수

    String v_title     = "";           // 제목
    String v_content   = "";           // 내용
    String v_indate    = "";           // 등록일
    String v_name      = "";           // 등록자
    String v_startdate = "";           // 이벤트 시작일
    String v_enddate   = "";           // 이벤트 시작일
    String v_winneryn  = "";           // 담청자 여부
    String v_isall     = "";
    String v_status    = "";
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    
    list2 = (ArrayList)request.getAttribute("selectList");
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

    function viewPassContent(seq) {

    	window.open("about:blank", "popUpView", "top=0, left=0, width=400, height=330, status=no, resizable=no, scrollbars=auto");

        document.nform1.action="/servlet/controller.homepage.EventHomePageServlet";
        document.nform1.p_process.value = "selectViewPass";
        document.nform1.p_seq.value = seq;
        document.nform1.target="popUpView";
        document.nform1.submit();
        
        document.nform1.target="_self";
    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
    <input type = "hidden" name = "p_seq"    >
    
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
        <td><img src="/images/portal/homepage_renewal/support/stitle_09.gif" alt="이벤트 존" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>이벤트 존</strong></td>
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
                <select name="p_search">
                    <option value="title"   <%if("title".equals(v_search)) out.print(" selected");%>>제목</option>
                    <option value="all" <%if("all".equals(v_search)) out.print(" selected");%>>제목+내용</option>
                </select>
              </span></td>
              <td><input name="p_searchtext" type="text" class="input_search" id="p_searchtext" size="30" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/></td>
              <td><a href="javascript:select();"><img src="/images/portal/homepage_renewal//common/btn_search_in.jpg" alt="검색" width="46" height="24" /></a></td>
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
        <td class="tit_table">이벤트기간</td>
        <td class="tit_table">진행기간</td>
        <td class="tit_table">당첨자발표</td>
        <td class="tit_table_right">조회수</td>
      </tr>
      
      <%
			if(list2.size() != 0){
				for( int i = 0; i < list2.size(); i++) {
				    DataBox dbox     = (DataBox)list2.get(i);
				    
				    v_seq       = dbox.getInt("d_seq");
				    v_cnt       = dbox.getInt("d_cnt");
				    v_title     = dbox.getString("d_title");
				    v_startdate = dbox.getString("d_strdate");
				    v_enddate   = dbox.getString("d_enddate");
				    v_indate    = dbox.getString("d_indate");
				    v_name      = dbox.getString("d_name");
				    v_winneryn  = dbox.getString("d_winneryn");
				    v_isall     = dbox.getString("d_isall");
				
				    v_dispnum        = dbox.getInt("d_dispnum");
				    v_totalpage      = dbox.getInt("d_totalpage");
				    v_rowcount       = dbox.getInt("d_rowcount");
				    
				    if(v_winneryn.equals("C")){
				    	v_winneryn = "미발표";
				    	v_status = "완료";
				    } else if(v_winneryn.equals("Y")){
				        v_winneryn = "<a href='javascript:viewPassContent("+v_seq+")'><img src='/images/portal/homepage_renewal/library/btn_dok.jpg' width='90' height='21' alt='당첨자확인' /></a>";
				        v_status = "완료";
				    } else if(v_winneryn.equals("N")){
				    	v_winneryn = "미발표";
				    	v_status = "진행";
				    } else if(v_winneryn.equals("A")){
				    	v_winneryn = "미발표";
                        v_status = "진행";
                    } 
				    
				    
				    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
				    if (!v_searchtext.equals("")) {
				    	v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				    } 
			%>      
      
      <tr>
        <td class="ct_tit_table"><%=v_isall.equals("Y") ? "*" : v_dispnum%></td>
        <td class="ct_tit_table_left"><a href="javascript:selectView(<%=v_seq%>)"><%=v_title%></a></td>
        <td class="ct_tit_table"><%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")+"-"+FormatDate.getFormatDate(v_enddate, "MM.dd")%></td>
        <td class="ct_tit_table"><%=v_status%></td>
        <td class="ct_tit_table"><%=v_winneryn%></td>
        <td class="ct_tit_table_right"><%=v_cnt%></td>
      </tr>
			<%      }
			    }else{%>

		    <tr> 
		        <td colspan="6">등록된 내용이 없습니다.</td>
		    </tr>
            <%  }   %>
      
      <tr>
        <td height="1" colspan="6" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="6" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    
    <!-- total 시작 -->
		 <%= PageUtil.re_printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalpage) %>
		<!-- total 끝 -->
    
    <!--타이틀부분//-->
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
			
			<%}else{ %>

            <h2><img src="/images/portal/information/h2_tit5.gif" alt="이벤트" class="fl_l" /><p class="category">Home > 정보광장 > <strong>이벤트</strong></p></h2>

            <p><img src="/images/portal/information/event_borimg.gif" alt="콘텐츠진흥원에서 진행하는 이벤트를 만나보세요" /></p>
            
            <p class="board_serach">
                <select name="p_search">
                    <option value="title"   <%if("title".equals(v_search)) out.print(" selected");%>>제목</option>
                    <option value="all" <%if("all".equals(v_search)) out.print(" selected");%>>제목+내용</option>
                </select>
                <input name="p_searchtext" type="text" class="inbox" style="width:135px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/>
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="select();"/>
            </p>
            
            <table class="board_list">
            <colgroup><col width="48px" /><col width="323px" /><col width="106px" /><col width="74px" /><col width="82px" /><col width="60px" /></colgroup>
            <thead>
            <tr class="information">
                <th><p>번호</p></th>
                <th><p>제목</p></th>
                <th><p>이벤트기간</p></th>
                <th><p>진행기간</p></th>
                <th><p>당첨자발표</p></th>
                <th class="end"><p>조회수</p></th>
            </tr>
            </thead>
            <tbody>
			<%
			if(list2.size() != 0){
				for( int i = 0; i < list2.size(); i++) {
				    DataBox dbox     = (DataBox)list2.get(i);
				    
				    v_seq       = dbox.getInt("d_seq");
				    v_cnt       = dbox.getInt("d_cnt");
				    v_title     = dbox.getString("d_title");
				    v_startdate = dbox.getString("d_strdate");
				    v_enddate   = dbox.getString("d_enddate");
				    v_indate    = dbox.getString("d_indate");
				    v_name      = dbox.getString("d_name");
				    v_winneryn  = dbox.getString("d_winneryn");
				    v_isall     = dbox.getString("d_isall");
				
				    v_dispnum        = dbox.getInt("d_dispnum");
				    v_totalpage      = dbox.getInt("d_totalpage");
				    v_rowcount       = dbox.getInt("d_rowcount");
				    
				    if(v_winneryn.equals("C")){
				    	v_winneryn = "<a class='state'><span class='pre'>미발표</span></a>";
				    	v_status = "완료";
				    } else if(v_winneryn.equals("Y")){
				        v_winneryn = "<a class='state' href='javascript:viewPassContent("+v_seq+")'><span class='finish'>당첨자확인</span></a>";
				        v_status = "완료";
				    } else if(v_winneryn.equals("N")){
				    	v_winneryn = "<a class='state'><span class='pre'>미발표</span></a>";
				    	v_status = "진행";
				    } else if(v_winneryn.equals("A")){
				    	v_winneryn = "<a class='state'><span class='pre'>미발표</span></a>";
                        v_status = "진행";
                    } 
				    
				    
				    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
				    if (!v_searchtext.equals("")) {
				    	v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				    } 
			%>
            <tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="num"><%=v_isall.equals("Y") ? "*" : v_dispnum%></td>
                <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>)"><%=v_title%></a></td>
                <td class="num"><%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")+"-"+FormatDate.getFormatDate(v_enddate, "MM.dd")%></td>
                <td><%=v_status%></td>
                <td class="btn"><%=v_winneryn%></td>
                <td class="num"><%=v_cnt%></td>
            </tr>
			<%      }
			    }else{%>

		    <tr> 
		        <td colspan="6">등록된 내용이 없습니다.</td>
		    </tr>
            <%  }   %>
            </tbody>
            </table>

        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
        
        <%} %>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->