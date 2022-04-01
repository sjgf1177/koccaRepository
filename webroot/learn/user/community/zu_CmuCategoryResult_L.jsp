<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  v_orderby    = box.getStringDefault("p_orderby","cmu_nm");
    String  v_type_m     = box.getString("p_type_m");
    String  v_type_m_nm  = box.getString("p_type_m_nm");
    String  v_type_l     = box.getStringDefault("p_type_l", "ALL");
    String  v_type_l_nm  = box.getString("p_type_l_nm");
	
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    String  v_loc_fg     = box.getStringDefault("p_loc_fg","ALL");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
	String	v_class		 = "";
	
	String v_cmu_nm     = "";
	String v_intro      = "";
	String v_kor_name   = "";
	String v_display_fg = "";
	String v_accept_dte = "";
	
	int v_cmuno         =  0;
	int v_member_cnt    =  0;


    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;
    int v_pageno             = box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));

    ArrayList selectList     = (ArrayList)request.getAttribute("selectList");
    
    ArrayList groupList      = (ArrayList)request.getAttribute("groupList");
    
    // tr 클래스 명
    String sClassName = "";

%>  
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">

<!--

//페이지이동
function goPage(pageNum) {
 document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityCategoryResultServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}

//홍보페이지이동
function goHongboPage(p_cmuno) {
 document.form1.p_cmuno.value = p_cmuno;
    document.form1.action = "/servlet/controller.community.CommunityPrServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}

//조회
function uf_searchOK() {

    document.form1.p_pageno.value = 1;
    document.form1.action = "/servlet/controller.community.CommunityCategoryResultServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}

//커뮤닡 홈으로이동
function goSubIndex(cmuno) {
    window.open("/servlet/controller.community.CommunityIndexServlet?p_process=selectmyindex&p_cmuno="+cmuno, "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 660, top=0, left=0");
}
//카테고리 리스트 이동
function goCategoryList(type_l){
    document.form1.action = "/servlet/controller.community.CommunityCategoryResultServlet";
    document.form1.p_type_l.value = type_l;
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process" value="">
<!--input type = "hidden" name = "p_type_m" value="<%=v_type_m%>"-->
<!--input type = "hidden" name = "p_type_m_nm" value="<%=v_type_m_nm%>"-->
<input type = "hidden" name = "p_type_l" value="<%=v_type_l%>">
<input type = "hidden" name = "p_type_l_nm" value="<%=v_type_l_nm%>">
<input type = "hidden" name = "p_pageno"      value = "">
<input type = "hidden" name = "p_cmuno"      value = "">

            <h2><img src="/images/portal/community/h2_tit4.gif" alt="커뮤니티 리스트" class="fl_l" /><p class="category">Home > 커뮤니티 > <strong>커뮤니티 리스트</strong></p></h2>
            
            <div class="com_box_list">
                <ul>
                <%
                    if(groupList != null && groupList.size() > 0 ) {
                    	for(int i = 0 ; i < groupList.size() ; i++) {
                    		DataBox dbox = (DataBox) groupList.get(i);
                %>
                    <li><a href="javascript:goCategoryList('<%=dbox.getString("d_code")%>');" <%=v_type_l.equals(dbox.getString("d_code")) ? "class=\"on\"" : "" %>><%=dbox.getString("d_codenm")%> (<%=dbox.getString("d_cnt")%>)</a></li>
                <%    	}
                    }
                %>
                </ul>
            </div>

            <p class="searchbox mg_t30">
                <span class="fl_l pd_l25">
                    <select name="p_select">
                        <option value="cmu_nm"       <%if("cmu_nm".equals(v_select)) out.print(" selected");%>>커뮤니티명</option>
                        <option value="kor_name"    <%if("kor_name".equals(v_select)) out.print(" selected");%>>시  샵</option>
                    </select>
                    <input type="text" name="p_searchtext" class="inbox" style="width:205px;" value="<%=v_searchtext%>" onkeypress="javascript:fnKeyPressEnter(event, 'uf_searchOK');"/>
                    <input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" onclick="uf_searchOK();"/>
                </span>
                <span class="fl_r pd_r25">
                    <img src="/images/portal/board/serbox_aligntxt.gif" alt="보기순서" class="mg_r5" />
                    <select name="p_loc_fg" onchange="uf_searchOK();">
                        <option value="ALL">전 체</option>
                        <option value="1"      <%if("1".equals(v_loc_fg)) out.print(" selected");%>>인기 커뮤니티</option>
                        <option value="2"    <%if("2".equals(v_loc_fg)) out.print(" selected");%>>신규 커뮤니티</option>
                    </select>
                </span>
            </p>

            <table class="list com_list">
            <colgroup><col width="160" /><col width="50" /><col width="40" /><col width="30" /><col width="50" /></colgroup>
            <thead>
            <tr>
                <th>커뮤니티명</th>
                <th>시삽이름</th>
                <th>공개여부</th>
                <th>회원수</th>
                <th>개설일자</th>
            </tr>
            </thead>
            <tbody>
            <%  
                if(selectList != null && selectList.size() != 0){
                    for(int i = 0; i < selectList.size(); i++) {
                        DataBox dbox    = (DataBox)selectList.get(i);
                        v_cmuno         = dbox.getInt("d_cmuno");
                        v_cmu_nm        = dbox.getString("d_cmu_nm");
                        v_intro         = dbox.getString("d_intro");
                        v_kor_name      = dbox.getString("d_kor_name");
                        v_accept_dte    = FormatDate.getFormatDate(dbox.getString("d_accept_dte"), "yyyy.MM.dd");
                        v_member_cnt    = dbox.getInt("d_member_cnt");
                        
                        v_totalpage = dbox.getInt("d_totalpage");
                        
                        //v_intro = StringManager.formatTitle(StringManager.removeHTML(v_intro), 300);
                
                        if("1".equals(dbox.getString("d_in_method_fg"))) v_display_fg="공개";
                        if("2".equals(dbox.getString("d_in_method_fg"))) v_display_fg="비공개";
            %>   
            <tr class="tit">
                <td class="ta_l"><a href="javascript:goSubIndex(<%=v_cmuno%>);" class="fw_b"><%=v_cmu_nm %></a></td>
                <td><%=v_kor_name %></td>
                <td><%=v_display_fg %></td>
                <td class="num"><%=v_member_cnt %></td>
                <td class="num"><%=v_accept_dte %></td>
            </tr>
            <tr class="txt">
                <td class="ta_l" colspan="5"><%=v_intro %></td>
            </tr>  
            <%      }
                } else {%>
            <tr class="tit">
                <td colspan="5">등록된 커뮤니티가 없습니다.</a></td>
            </tr>
            <%  } %>
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