<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    int v_tabseq         = box.getInt("p_tabseq");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    
    GregorianCalendar   calendar    = new GregorianCalendar();
    String currentYear  = Integer.toString(calendar.get(Calendar.YEAR));
    String currentMonth = calendar.get(Calendar.MONTH) + 1 < 10 ? "0"+(calendar.get(Calendar.MONTH)+1) : Integer.toString(calendar.get(Calendar.MONTH)+1);

    String  v_gyear         = box.getStringDefault("p_year", currentYear);           //년도
    String  v_month         = box.getStringDefault("p_month", currentMonth);         // 월
    
    String v_subj           = "";
    String v_subjnm         = "";
    String v_edustart       = "";
    String v_eduend         = "";
    String v_propstart      = "";                          
    String v_propend        = "";
    String v_biyong         = "";
    String v_studentlimit   = "";                                              
    String v_status         = "";        
    String v_subjseq        = "";
    String v_year           = "";
    String v_upperclass     = "";
    String v_classname      = "";
    
    ArrayList schlMonthPlanList = (ArrayList)request.getAttribute("schlMonthPlanList");
    int listSize    = schlMonthPlanList != null ? schlMonthPlanList.size() : 0 ;
    
    int     v_dispnum = 0;
    int     v_totalrowcount = schlMonthPlanList != null &&  listSize > 0 ? ((DataBox)schlMonthPlanList.get(0)).getInt("d_totalrowcount") : 0;
    int     v_totalpage = 0;           
      
    int v_pageno             = box.getInt("p_pageno"); 
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    

    String v_classname1      = "end stxt";
    String v_classname2      = "end";
    
    DecimalFormat   dFormat = new DecimalFormat();
    
    String v_menuid         = "";
    
    ArrayList<DataBox> upperList = null;        // 과정 대분류 리스트
    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>
    
    upperList  = ClassifySubjectBean.getUpperClassList(box); 
    upperMap   = ClassifySubjectBean.getMenuId(box);
    
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--
function subjSearch() {
    document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
	document.form1.p_process.value = "schlMonthPlanList";
	document.form1.submit();
}
//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
    document.form1.p_process.value = "schlMonthPlanList";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
     document.form1.p_process.value = "schlMonthPlanList";
     document.form1.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm) {

    if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
     return;
    }
    document.form1.p_process.value = "SubjectEduProposePage";
    document.form1.target = "proposeWindow";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
    document.form1.submit();
    document.form1.target = "_self";
}

function changeMonth(month) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_month.value = month;
    document.form1.action = '/servlet/controller.course.EduScheduleHomePageServlet';
    document.form1.p_process.value = "schlMonthPlanList";
    document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj, menuid, upperclass){
    document.form1.p_subj.value = subj;
    document.form1.p_upperclass.value   = upperclass;
    document.form1.menuid.value = menuid;
    
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.submit();
    document.form1.target = "_self";
}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name = "form1" method = "post">
      <input type = "hidden" name = "p_process"     value = "">
      <input type = "hidden" name = "p_rprocess"    value = "">
      <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
      <input type = "hidden" name = "p_seq"         value = "">
      <input type = "hidden" name = "p_userid"      value = "">
      <input type = "hidden" name = "p_year"        value = "<%=v_gyear %>">
      <input type = "hidden" name = "p_month"       value = "<%=v_month %>">
      <input type = "hidden" name = "menuid"        value = "">
      <input type = "hidden" name = "p_upperclass"  value = "">
      <input type = "hidden" name = "p_subj"        value = "">
      <input type = "hidden" name = "p_subjseq"     value = "">
      
            <h2><img src="/images/portal/online/h2_tit6.gif" alt="월별교육일정" class="fl_l" /><p class="category">Home > 온라인과정 > <strong>월별교육일정</strong></p></h2>

            <p><img src="/images/portal/online/month_txt.gif" alt="온라인 과정 연간교육일정입니다. 교육과정명을 클릭하시면 과정내용을 보실 수 있으며, 월을 클릭하시면 해당월의 교육과정을 보실 수 있습니다 " /></p>

            <p class="searchbox" style="height:40px">
				<img src="/images/portal/board/serbox_classtit.gif" alt="과정검색" class="mg_r7" />
				<input type="text" name="s_subjnm" class="inbox" style="width:280px;" value="<%= box.get("s_subjnm") %>" />
				<input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" onclick="javascript:subjSearch();"/>
			</p>

            <%--<p  class="board_hearder mg_t30">--%>
            <p  class="board_hearder" style="height:30px">
            <span class="year_tit"><img src="/images/portal/online/<%=v_gyear %>.gif" alt="<%=v_gyear %>" /></span>
            <span class="year_btn">
            <a href="javascript:changeMonth('01');" class="btn_wred <%=v_month.equals("01") ? "btn_wred_on" : "" %>"><span>1월</span></a>
            <a href="javascript:changeMonth('02');" class="btn_wred <%=v_month.equals("02") ? "btn_wred_on" : "" %>"><span>2월</span></a>
            <a href="javascript:changeMonth('03');" class="btn_wred <%=v_month.equals("03") ? "btn_wred_on" : "" %>"><span>3월</span></a>
            <a href="javascript:changeMonth('04');" class="btn_wred <%=v_month.equals("04") ? "btn_wred_on" : "" %>"><span>4월</span></a>
            <a href="javascript:changeMonth('05');" class="btn_wred <%=v_month.equals("05") ? "btn_wred_on" : "" %>"><span>5월</span></a>
            <a href="javascript:changeMonth('06');" class="btn_wred <%=v_month.equals("06") ? "btn_wred_on" : "" %>"><span>6월</span></a>
            <a href="javascript:changeMonth('07');" class="btn_wred <%=v_month.equals("07") ? "btn_wred_on" : "" %>"><span>7월</span></a>
            <a href="javascript:changeMonth('08');" class="btn_wred <%=v_month.equals("08") ? "btn_wred_on" : "" %>"><span>8월</span></a>
            <a href="javascript:changeMonth('09');" class="btn_wred <%=v_month.equals("09") ? "btn_wred_on" : "" %>"><span>9월</span></a>
            <a href="javascript:changeMonth('10');" class="btn_wred <%=v_month.equals("10") ? "btn_wred_on" : "" %>"><span>10월</span></a>
            <a href="javascript:changeMonth('11');" class="btn_wred <%=v_month.equals("11") ? "btn_wred_on" : "" %>"><span>11월</span></a>
            <a href="javascript:changeMonth('12');" class="btn_wred <%=v_month.equals("12") ? "btn_wred_on" : "" %>"><span>12월</span></a>
            </span>
            </p>
            
            <table class="row_list row_list_program">
            <thead>
            <tr>
                <th>분야</th>
                <th>과정명<br /><span class="stit">(총 <%=v_totalrowcount %>개 과정)</span></th>
                <th>교육기간<br /><span class="stit">(수강신청기간)</span></th>
                <!--<th>수강료</th>
                --><th>수강신청</th>
            </tr>
            </thead>
            <tbody>
            <%
            if(listSize > 0 ) {
	            for(int i = 0; i < listSize; i++) {
	                DataBox dbox = (DataBox)schlMonthPlanList.get(i);
	
	                v_subj           = dbox.getString("d_subj");        
	                v_subjnm         = dbox.getString("d_subjnm");      
	                v_edustart       = dbox.getString("d_edustart");    
	                v_eduend         = dbox.getString("d_eduend");      
	                v_propstart      = dbox.getString("d_propstart");   
	                v_propend        = dbox.getString("d_propend");     
	                v_biyong         = dbox.getString("d_biyong");      
	                v_studentlimit   = dbox.getString("d_studentlimit");
	                v_status         = dbox.getString("d_status");
	                v_subjseq        = dbox.getString("d_subjseq");
	                v_year           = dbox.getString("d_year");
	                v_upperclass     = dbox.getString("d_upperclass");
	                v_classname      = dbox.getString("d_classname");
	                
	                v_biyong    = dFormat.format(Integer.parseInt(v_biyong));
	                
	                if(v_status.equals("Y")) {
	                	if( !box.getSession("userid").equals("") ) {
		                    v_status = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"')\" class=\"board_btn mg_t3\"><span>신청</span></a>";
		                    v_classname1 = "end";
	                	} else {
	                		v_status = "<a href=\"javascript:alert('로그인후 이용하실수 있습니다.')\" class=\"board_btn mg_t3\"><span>신청</span></a>";
		                    v_classname1 = "end";
	                	}
	                } else {
	                    v_status = "마감";
	                    v_classname1 = "end stxt";
	                }
	                
	                v_dispnum   = dbox.getInt("d_dispnum");
	                v_totalpage = dbox.getInt("d_totalpage");
	                v_totalrowcount = dbox.getInt("d_totalrowcount");
	                
	                v_edustart  = FormatDate.getFormatDate(v_edustart, "yyyy.MM.dd")+"~"+FormatDate.getFormatDate(v_eduend, "yyyy.MM.dd");
	                v_propstart = FormatDate.getFormatDate(v_propstart, "yyyy.MM.dd")+"~"+FormatDate.getFormatDate(v_propend, "yyyy.MM.dd");
	                
	                v_menuid = upperMap !=null && upperMap.get(v_upperclass) != null ?  upperMap.get(v_upperclass) : "00";
            %>
            <tr>
                <td class="ta_l"><%=v_classname %></td>
                <td class="ta_l"><a href="javascript:whenSubjInfo('<%=v_subj %>','<%=v_menuid%>','<%=v_upperclass %>');"><%=v_subjnm %></a></td>
                <td class="data"><%=v_edustart%><br /><span class="sdata"><%=v_propstart %></span></td>
                <!--<td class="data ta_r"><%=v_biyong %></td>
                --><td class="<%=v_classname1 %>"><%=v_status %></td>
            </tr>
            <%
                }
            } else { %>
              <tr>
                <td colspan="5">등록된 내용이 없습니다</td>
              </tr>
            <%  } %>
            </tbody>
            </table>
            
            
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_totalrowcount) %>
        <!-- 페이징 영역 종료 -->
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->