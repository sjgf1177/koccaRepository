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

    ArrayList list1 = null;
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
    
    int    v_seq     = 0;              // 일련번호
    int    v_cnt     = 0;              // 조회수

    String v_code      = "";
    String v_codenm    = "";
    String v_seminargubun     = "";
    String v_seminargubunnm   = "";            
    String v_seminarnm = "";           // 제목
    String v_content   = "";           // 내용
    String v_tname     = "";           // 담당교수명
    String v_indate    = "";           // 등록일
    String v_name      = "";           // 등록자
    String v_startdate = "";           // 워크샵 시작일
    String v_enddate   = "";           // 워크샵 시작일
    String v_propstart = "";           // 워크샵 모집 시작일
    String v_propend   = "";           // 워크샵 모집 종료일
    String v_passyn  = "";             // 선정자 공지 여부
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    
    list2 = (ArrayList)request.getAttribute("selectSemanarList");
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {

        document.nform1.action = "/servlet/controller.homepage.SeminarHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

    function viewPassContent(seq) {

    	window.open("about:blank", "popUpView", "top=0, left=0, width=400, height=330, status=no, resizable=no, scrollbars=auto");

        document.nform1.action="/servlet/controller.homepage.SeminarHomePageServlet";
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

            <h2><img src="/images/portal/information/h2_tit4.gif" alt="워크샵" class="fl_l" /><p class="category">Home > 정보광장 > <strong>워크샵</strong></p></h2>

            <p><img src="/images/portal/information/workshop_borimg.gif" alt="콘텐츠진흥원 워크샵 입니다. 다양한 분야 강사들의 생생한 현장의 소리를 들으실 수 있습니다." /></p>

            <p class="board_serach">
                <select name="p_search">
                    <option value="seminarnm"   <%if("seminarnm".equals(v_search)) out.print(" selected");%>>제목</option>
                    <option value="all" <%if("all".equals(v_search)) out.print(" selected");%>>제목+내용</option>
                </select>
                <input name="p_searchtext" type="text" class="inbox" style="width:135px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'select');"/>
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="select();"/>
            </p>

            <table class="board_list">
            <colgroup><col width="50px" /><col width="81px" /><col /><col width="105px" /><col width="105px" /><col width="82px" /></colgroup>
            <thead>
            <tr class="information">
                <th><p>번호</p></th>
                <th><p>
                    <kocca_select:select                                       
		                name="p_selSeminarGubun"                                         
		                sqlNum="0001"                                    
		                param="0061"             
		                onChange="select();"                         
		                attr= "분류"                                        
		                selectedValue="<%=v_selSeminarGubun%>"                         
		                isLoad="true"                                           
		                type="3"                                                
		                styleClass=""                                
		                all="true" />
                </p></th>
                <th><p>제목</p></th>
                <th><p>워크샵일자</p></th>
                <th><p>신청기간</p></th>
                <th class="end"><p>결과발표</p></th>
            </tr>
            </thead>
            <tbody>
			<%
			if(list2.size() != 0){
			    for( int i = 0; i < list2.size(); i++) {
				    DataBox dbox     = (DataBox)list2.get(i);
				    
				    v_seq           = dbox.getInt("d_seq");
				    v_seminargubun  = dbox.getString("d_seminargubun");
				    v_seminargubunnm= dbox.getString("d_seminargubunnm");
				    v_seminarnm     = dbox.getString("d_seminarnm");
				    v_tname         = dbox.getString("d_tname");
				    v_startdate     = dbox.getString("d_startdate");
				    v_enddate       = dbox.getString("d_enddate");
				    v_propstart     = dbox.getString("d_propstart");
				    v_propend       = dbox.getString("d_propend");
				    v_passyn        = dbox.getString("d_pass_yn");
				
				    v_dispnum        = dbox.getInt("d_dispnum");
				    v_totalpage      = dbox.getInt("d_totalpage");
				    v_rowcount       = dbox.getInt("d_rowcount");
				    
				    if(v_passyn.equals("Y")){
				        v_passyn = "<a class='state' href='javascript:viewPassContent("+v_seq+")'><span class='finish'>선정자확인</span></a>";
				    } else if(v_passyn.equals("N")) {
				        v_passyn = "<a class='state'><span class='pre'>미발표</span></a>";
				    }
				    
				    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
				    if (!v_searchtext.equals("")) {
				    	v_seminarnm = StringManager.replace(v_seminarnm, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				    } 
			%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="num"><%=v_dispnum%></td>
                <td><%=v_seminargubunnm%></td>
                <td class="ta_l"><a href="javascript:selectView(<%=v_seq%>)"><%=v_seminarnm%></a></td>
                <td class="num"><%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")+"-"+FormatDate.getFormatDate(v_enddate, "MM.dd")%></td>
                <td class="num"><%=FormatDate.getFormatDate(v_propstart, "yyyy.MM.dd")+"-"+FormatDate.getFormatDate(v_propend, "MM.dd")%></td>
                <td class="btn"><%=v_passyn%></td>
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
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->
