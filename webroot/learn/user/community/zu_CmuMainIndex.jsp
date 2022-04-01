<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getStringDefault("p_process","selectmain");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;

    ArrayList list      = (ArrayList)request.getAttribute("list");
    ArrayList list1     = null;
    ArrayList list2     = null;
    ArrayList list3     = null;
    ArrayList list4     = null;
    ArrayList list5     = null;    
    
    if(list.size() != 0){
       list1       = (ArrayList)list.get(0);  
       list2       = (ArrayList)list.get(1);  
       list3       = (ArrayList)list.get(2);  
       list4       = (ArrayList)list.get(3);  
       list5       = (ArrayList)list.get(4);  
    }
 

%>  

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->



<script language="JavaScript" type="text/JavaScript">

//조회
function uf_searchOK(loc_fg) {
    if(loc_fg=='1'){
       if(document.form1.p_searchtext.value ==''){
          alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
       }
    }
    document.form1.p_loc_fg.value = loc_fg;
    document.form1.action = "/servlet/controller.community.CommunityCategoryResultServlet";
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}
//커뮤니티 새창
function goSubIndex(cmuno) {
    window.open("/servlet/controller.community.CommunityIndexServlet?p_process=selectmyindex&p_cmuno="+cmuno, "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,resizable=no, width = 920, height = 660, top=0, left=0");
}
//커뮤니티 홈으로이동
//function goSubIndex(p_cmuno) {
//    document.form1.action = "/servlet/controller.community.CommunityIndexServlet?p_cmuno="+p_cmuno;
//    document.form1.p_process.value = "selectmyindex";
//    document.form1.submit();
//}

//커뮤니티 홍보페이지로이동
function goHongBo(cmuno) {
	window.open("/servlet/controller.community.CommunityPopUpServlet?p_process=hongboPop&p_cmuno="+cmuno, "openHongboPop", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 620, height = 360, top=0, left=0");
}

//카테고리 리스트 이동
function goCategoryList(type_l, loc_fg){
	document.form1.action = "/servlet/controller.community.CommunityCategoryResultServlet";
	document.form1.p_type_l.value = type_l;
	document.form1.p_loc_fg.value = loc_fg;
    document.form1.p_process.value = "movePage";
    document.form1.submit();
}

//내가 가입한 커뮤니티 이동(팝업)
function moveMyCommunity() {

    var cmuno = document.getElementById("p_static_cmuno").value;

    goSubIndex(cmuno);
}

function goSearchCommunityList(){
	document.form1.action = "/servlet/controller.community.CommunityCategoryResultServlet";
	document.form1.p_process.value = "movePage";
	document.form1.submit();
}

//공지사항 보기
function viewNotice(faqno){
    document.form1.action = "/servlet/controller.community.CommunityDirectServlet";
    document.form1.menuid.value = "01";
    document.form1.p_faq_type.value = "DIRECT";
    document.form1.p_faqno.value = faqno;
    document.form1.p_process.value = "viewPage";
    document.form1.submit();
}

// 공지사항 더보기
function moreNotice(){
    document.form1.menuid.value= "01";
    document.form1.action = "/servlet/controller.community.CommunityDirectServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.target="_self";
    document.form1.submit();
	
}
</script>
<!--  스크립트영역종료  -->

<!--  Form 영역 시작  -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_loc_fg"      value = "">
<input type = "hidden" name = "p_cmuno"       value = "">
<input type = "hidden" name = "p_type_l"      value = "">
<input type = "hidden" name = "menuid"        value = "">
<input type = "hidden" name = "p_faq_type"    value = "">
<input type = "hidden" name = "p_faqno"       value = "">
<input type = "hidden" name = "p_select"      value = "cmu_nm">

            <div class="community_visualbox">
                <dl class="category">
                    <dt><img src="/images/portal/community/main_tit_category.gif" alt="CATEGORY" /></dt>
                    <%  
                    for(int i = 0; i < list5.size(); i++) {
                        DataBox dbox = (DataBox)list5.get(i);
                    %>
                    <dd><a href="javascript:goCategoryList('<%=dbox.getString("d_code")%>','');" <%=i == 0 ? "class=\"fw_b\"" : "" %>><%=dbox.getString("d_codenm")%> [<%=dbox.getString("d_cnt")%>]</a></dd>
                    <%} %>
                </dl>
                
                <div class="community_box">
                    <dl class="search">
                        <dt><img src="/images/portal/community/main_tit.gif" alt="KOCCA COMMUNITY" /></dt>
                        <dd><img src="/images/portal/community/main_stit1.gif" alt="나의 커뮤니티" class="mg_r7" /> 
                            <select  name="p_static_cmuno" id="p_static_cmuno" onchange="javascript:moveMyCommunity();" style="width:332px;">
                                <option>내가 가입한 커뮤니티</option>
                                <%=(new com.credu.community.CommunityIndexBean()).selectMyCuminity(s_userid, "")%>
                            </select>
                        </dd>
                        <dd>
                            <img src="/images/portal/community/main_stit2.gif" alt="커뮤니티 검색" class="mg_r5" /> 
                            <input type="text" name="p_searchtext" class="inbox" style="width:280px;" onkeypress="javascript:fnKeyPressEnter(event, 'goSearchCommunityList');"/> 
                            <input type="image" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="goSearchCommunityList();"/>
                        </dd>
                    </dl>
                    <dl class="noticlist">
                        <dt><img src="/images/portal/community/main_stit3.gif" alt="공지사항" class="fl_l" /> <a href="javascript:menuForward('<%=box.getSession("s_gubun") %>', '01')" class="fl_r"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></dt>
                        <%  
                        for(int i = 0; i < list1.size(); i++) {
                            DataBox dbox = (DataBox)list1.get(i);
                        %>
                        <dd><a href="javascript:viewNotice(<%=dbox.getInt("d_faqno")%>);"><%=dbox.getString("d_title")%></a> <span class="data"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy.MM.dd")%></span></dd>
                        <%} %>
                    </dl>
                </div>
            </div>
            
            <div class="main_warp">
                <div class="com_div mg_r20">
                    <p class="main_stit"><img src="/images/portal/community/main_stit4.gif" alt="인기 커뮤니티" class="fl_l" /><a href="javascript:goCategoryList('ALL',1);" class="fl_r"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></p>

                    <table class="main_list">
                    <!--[if ie]><colgroup><col width="315px" /><col width="90" /><![endif]-->
                    <colgroup><col width="325px" /><col width="90" /></colgroup>
                    <thead>
                    <tr class="community1">
                        <th><img src="/images/portal/community/main_table_tit1.gif" alt="이름" /></th>
                        <th class="end"><img src="/images/portal/community/main_table_tit2.gif" alt="회원수" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                    if( list4.size() > 0 ) {
	                    for(int i = 0; i < list4.size(); i++) {
	                        DataBox dbox = (DataBox)list4.get(i);
                    %>
                    <tr>
                        <td class="ta_l"><a href="javascript:goSubIndex(<%=dbox.getString("d_cmuno")%>);"><%=dbox.getString("d_cmu_nm")%></a> </td>
                        <td><%=dbox.getString("d_member_cnt")%>명</td>
                    </tr>
                    <%	} 
                    } else { %>
                    <tr>
                    	<td colspan="2">등록된 커뮤니티가 없습니다.</td>
                    </tr>
                    <% } %>
                    </tbody>
                    </table>
                </div>
                
                <div class="com_div">
                    <p class="main_stit"><img src="/images/portal/community/main_stit5.gif" alt="신규 커뮤니티" class="fl_l" /><a href="javascript:goCategoryList('ALL',2);" class="fl_r"><img src="/images/portal/btn/btn_txt_more.gif" alt="더보기" /></a></p>

                    <table class="main_list">
                    <!--[if ie]><colgroup><col width="315px" /><col width="90" /><![endif]-->
                    <colgroup><col width="325px" /><col width="90" /></colgroup>
                    <thead>
                    <tr class="community2">
                        <th><img src="/images/portal/community/main_table_tit3.gif" alt="이름" /></th>
                        <th class="end"><img src="/images/portal/community/main_table_tit4.gif" alt="회원수" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                    if( list3.size() > 0 ) {
	                    for(int i = 0; i < list3.size(); i++) {
	                    	DataBox dbox = (DataBox)list3.get(i);
                    %>
                    <tr>
                        <td class="ta_l"><a href="javascript:goSubIndex(<%=dbox.getString("d_cmuno")%>);"><%=dbox.getString("d_cmu_nm")%></a> </td>
                        <td><%=dbox.getString("d_member_cnt")%>명</td>
                    </tr>
                    <%	}
                    } else { %>
                    <tr>
                    	<td colspan="2">등록된 커뮤니티가 없습니다.</td>
                    </tr>
                    <% } %>
                    </tbody>
                    </table>
                </div>
            </div>
            
            <div class="main_warp">
                <div class="com_div mg_r20">
                    <ul class="main_banner mg_tn">
                        <li><a href="javascript:menuForward('<%=gubun %>', '02');"><img src="/images/portal/community/main_banner1.gif" alt="커뮤니티 만들기" /></a></li>
                        <li class="bgn"><a href="javascript:menuForward('<%=gubun %>', '03');"><img src="/images/portal/community/main_banner2.gif" alt="불건전 커뮤니티 신고" /></a></li>
                    </ul>
                </div>

                <div class="com_div">
                    <dl class="info">
                    <%
                    if( list2.size() > 0 ) {
	                    Random ran = new Random();
	                    DataBox hongboBox = (DataBox)list2.get(ran.nextInt(list2.size()));
                    %>
                        <dt><img src="/images/portal/community/main_stit6.gif" alt="커뮤니티 홍보" /></dt>
                        <dd class="photo"><a href="javascript:goHongBo('<%=hongboBox.getString("d_cmuno")%>');"><img src="<%=hongboBox.getString("d_savefile").equals("") ? "/images/portal/thumb/main_110_110.jpg" : "/servlet/controller.library.DownloadServlet?p_savefile="+hongboBox.getString("d_savefile")%>" alt="커뮤니티 홍보" width="110px" height="110px"/></a></dd>
                        <dd class="tit"><a href="javascript:goHongBo('<%=hongboBox.getString("d_cmuno")%>');"><%=hongboBox.getString("d_cmu_nm")%></a></dd>
                        <dd class="data">회원수 : <%=hongboBox.getString("d_member_cnt") %>명 <span class="bar">|</span> 개설일 : <%=FormatDate.getFormatDate(hongboBox.getString("d_register_dte"), "yyyy.MM.dd")%></dd>
                        <dd class="con"><a href="javascript:goHongBo('<%=hongboBox.getString("d_cmuno")%>');"><%=StringUtil.getByteString(hongboBox.getString("d_contents"), 50)%></a></dd>
                    <% } %>
                    </dl>
                </div>
            </div>
             
</form>
<!--  Form 영역 종료 -->


<!--  Footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- Footer 영역 종료 -->