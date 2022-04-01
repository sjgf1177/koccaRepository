<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	ArrayList list1 = null;
	ArrayList list2 = null;

    int		v_tabseq   = box.getInt("p_tabseq");
    int		v_seq      = box.getInt("p_seq");     
    String  v_process  = box.getString("p_process");

	// 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_select");

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
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
	int v_rowcount	= 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    list2 = (ArrayList)request.getAttribute("selectLetterList");
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->

<SCRIPT LANGUAGE="JavaScript">
<!--
    //리스트 조회
    function select() {
        document.nform1.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.nform1.p_pageno.value = "1";
        document.nform1.p_process.value = "List";
        document.nform1.submit();
    }

	//상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.HomeLetterServlet";
		document.nform1.p_process.value = "selectView";
		document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

  //페이지 이동
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.nform1.p_process.value = "";
        document.nform1.submit();
    }

    //페이지 이동
    function goPage(pageNum) {
         document.nform1.p_pageno.value = pageNum;
         document.nform1.action = "/servlet/controller.homepage.HomeLetterServlet";
         document.nform1.p_process.value = "";
         document.nform1.submit();
    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post">
	<input type = "hidden" name = "p_process">
	<input type = "hidden" name = "p_seq"	 >
	<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">

            <h2><img src="/images/portal/studying/h2_tit7.gif" alt="뉴스레터" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>뉴스레터</strong></p></h2>

            <p><img src="/images/portal/studying/news_borimg.gif" alt="콘텐츠 진흥원에서 이메일로 전해드리는 뉴스레터입니다. 뉴스레터수신를 원하시면 개인정보변경에서 이메일 수신에 동의해주세요." /></p>
            
            
            <p class="board_serach">
                <select>
                    <option>제목</option>
                </select>
                <input name="p_searchtext" type="text" class="inbox" style="width:135px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/>
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" />
                <input type="hidden" name="p_select" value = "adtitle">
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
				
				if (!v_searchtext.equals("")&&v_search.equals("adtitle")) {
					v_adtitle = StringManager.replace(v_adtitle, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				} 
				else if(!v_searchtext.equals("")&&v_search.equals("adname")){
					v_adname = StringManager.replace(v_adname, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				}
			%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
                <td><%=v_dispnum%></td>
                <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>)"><%=v_adtitle%></a></td>
                <td class="num"><%=FormatDate.getFormatDate(v_addate, "yyyy.MM.dd")%></td>
                <td class="fs_s"><%=v_cnt%></td>
            </tr>
			<%		}
				}else{%>
			
			<tr> 
			     <td colspan ='5' >등록된 내용이 없습니다.</td>
			</tr>
			<%  }   %>
            </tbody>
            </table>
        
            </tbody>
            </table>

        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
            </form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->
