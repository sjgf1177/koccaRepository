<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.off.OffClassifySubjectBean" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","4");
	box.put("submenu","5");

	
    if (box == null) box = RequestManager.getBox(request);
    int v_tabseq         = box.getInt("p_tabseq");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    
    GregorianCalendar   calendar    = new GregorianCalendar();
    String currentYear = Integer.toString(calendar.get(Calendar.YEAR));
    String currentMonth = calendar.get(Calendar.MONTH) + 1 < 10 ? "0"+(calendar.get(Calendar.MONTH)+1) : Integer.toString(calendar.get(Calendar.MONTH)+1);

    String  v_gyear         = box.getStringDefault("p_year", currentYear);           //년도
    String  v_month         = box.getStringDefault("p_month", currentMonth);          // 월
    
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
    String v_seq            = "";
    
    ArrayList offSchlMonthPlanList = (ArrayList)request.getAttribute("offSchlMonthPlanList");
    int listSize    = offSchlMonthPlanList != null ? offSchlMonthPlanList.size() : 0 ;
    
    int     v_dispnum = 0;
    int     v_totalrowcount = offSchlMonthPlanList != null &&  listSize > 0 ? ((DataBox)offSchlMonthPlanList.get(0)).getInt("d_totalrowcount") : 0;
    int     v_totalpage = 0;           
      
    int v_pageno             = box.getInt("p_pageno"); 
    int v_pagesize 			 = box.getInt("p_pagesize");
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    

    String v_classname1      = "end stxt";
    String v_classname2      = "end";
    
    DecimalFormat   dFormat = new DecimalFormat();
    
    String v_menuid         = "";
    String v_type           = "";
    
    ArrayList<DataBox> upperList = null;        // 과정 대분류 리스트
    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>
    
    upperList  = OffClassifySubjectBean.getUpperClassList(box); 
    upperMap   = OffClassifySubjectBean.getMenuId(box);
                  
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
    document.form1.p_process.value = "offSchlMonthPlanList";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
     document.form1.p_process.value = "offSchlMonthPlanList";
     document.form1.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, seq) {
    if(<%= box.getSession("userid").length()==0 %>) {
        alert("로그인이 필요합니다.");
        return;
    }
    if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
     return;
    }
    var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
    document.form1.p_process.value = "SubjectEduProposePage";
    document.form1.target = "proposeWindow";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_seq.value = seq;
    document.form1.action = "/servlet/controller.off.ProposeOffServlet";
    document.form1.submit();
    document.form1.target = "_self";
}

function changeMonth(month) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_month.value = month;
    document.form1.action = '/servlet/controller.course.EduScheduleHomePageServlet';
    document.form1.p_process.value = "offSchlMonthPlanList";
    document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj, subjseq, seq, menuid){
	document.form1.p_subj.value = subj;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_seq.value = seq;
	document.form1.menuid.value = menuid;
	document.form1.p_process.value = 'SubjectPreviewPage';
	document.form1.p_rprocess.value = 'SubjectList';
	document.form1.action='/servlet/controller.off.ProposeOffServlet';
	document.form1.target = "_self";
	document.form1.submit();
	}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작-->
<form name = "form1" method = "post" action="">
      <input type = "hidden" name = "menuid"        value = "">
      <input type = "hidden" name = "p_process"     value = "">
      <input type = "hidden" name = "p_rprocess"     value = "">
      <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
      <input type = "hidden" name = "p_seq"         value = "">
      <input type = "hidden" name = "p_userid"      value = "">
      <input type = "hidden" name = "p_year"        value = "<%=v_gyear %>">
      <input type = "hidden" name = "p_month"       value = "<%=v_month %>">
      <input type = "hidden" name = "p_subj"        value = "">
      <input type = "hidden" name = "p_subjseq"        value = "">
      
      <div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 오프라인과정 > <u>교육일정</u></td>
			</tr>
		</table>
		<div class="concept c03">
			<div class="pagetitle"><img src="/images/2012/sub/page3/title/offline_schedule.gif" alt="교육자료실"></div>
			<ul class="pagetab">
				<li><a href="javascript:menuForward('2','05');" tabindex="11-1" title="오프라인 연간 교육일정을 안내합니다"><img src="/images/2012/sub/page3/tabmenu/yearschedule_off.png" alt="연간교육일정"></a></li>
				<li><a href="javascript:menuForward('2','06');" tabindex="11-2" title="오프라인 월별 교육일정을 안내합니다"><img src="/images/2012/sub/page3/tabmenu/monthschedule_on.png" alt="월간교육일정"></a></li>
			</ul>
		</div>
		<ul class="monthchoice">
			<li><button type="button" tabindex="12-1" onClick="javascript:changeMonth('01');" class="<%= v_month.equals("01") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="1월 일정을 확인합니다">1월</button></li>
			<li><button type="button" tabindex="12-2" onClick="javascript:changeMonth('02');" class="<%= v_month.equals("02") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="2월 일정을 확인합니다">2월</button></li>
			<li><button type="button" tabindex="12-3" onClick="javascript:changeMonth('03');" class="<%= v_month.equals("03") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="3월 일정을 확인합니다">3월</button></li>
			<li><button type="button" tabindex="12-4" onClick="javascript:changeMonth('04');" class="<%= v_month.equals("04") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="4월 일정을 확인합니다">4월</button></li>
			<li><button type="button" tabindex="12-5" onClick="javascript:changeMonth('05');" class="<%= v_month.equals("05") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="5월 일정을 확인합니다">5월</button></li>
			<li><button type="button" tabindex="12-6" onClick="javascript:changeMonth('06');" class="<%= v_month.equals("06") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="6월 일정을 확인합니다">6월</button></li>
			<li><button type="button" tabindex="12-7" onClick="javascript:changeMonth('07');" class="<%= v_month.equals("07") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="7월 일정을 확인합니다">7월</button></li>
			<li><button type="button" tabindex="12-8" onClick="javascript:changeMonth('08');" class="<%= v_month.equals("08") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="8월 일정을 확인합니다">8월</button></li>
			<li><button type="button" tabindex="12-9" onClick="javascript:changeMonth('09');" class="<%= v_month.equals("09") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="9월 일정을 확인합니다">9월</button></li>
			<li><button type="button" tabindex="12-10" onClick="javascript:changeMonth('10');" class="<%= v_month.equals("10") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="10월 일정을 확인합니다">10월</button></li>
			<li><button type="button" tabindex="12-11" onClick="javascript:changeMonth('11');" class="<%= v_month.equals("11") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="11월 일정을 확인합니다">11월</button></li>
			<li><button type="button" tabindex="12-12" onClick="javascript:changeMonth('12');" class="<%= v_month.equals("12") ? "btn_nomal_darkmint" : "btn_nomal_white" %>" title="12월 일정을 확인합니다">12월</button></li>
		</ul>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="120"><col width="*"><col width="80"><col width="80"><col width="100"></colgroup>
					<tr class="listhead">
						<th>분야</th>
						<th>과정명(총 <%=v_totalrowcount %>개 과정)</th>
						<th>교육기간</th>
						<th>수강료</th>
						<th>수강신청</th>
					</tr>
					 <%
		            if(listSize > 0 ) {
			            for(int i = 0; i < listSize; i++) {
			                DataBox dbox = (DataBox)offSchlMonthPlanList.get(i);
			
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
			                v_seq            = dbox.getString("d_seq"); 
			                
			                v_biyong    = dFormat.format(Integer.parseInt(v_biyong));
			                
			                if(v_status.equals("Y")) {
			                	if( !box.getSession("userid").equals("") ) {
				                   // v_status = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','"+v_seq+"')\" class=\"board_btn mg_t3\"><img src=\"/images/portal/homepage_renewal/offline/btn_click.gif\" /></a>";
				                    v_status = "<button onClick=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','"+v_seq+"')\" tabindex=\""+(i+13)+"-2\" type=\"button\" class=\"btn_nomal_darkmint\" title=\"["+v_subjnm+"]과정을 신청합니다\">수강신청</button>";
				                    v_classname1 = "end";
			                	} else {
			                		v_status = "<button onClick=\"javascript:alert('로그인후 이용하실수 있습니다')\" type=\"button\" class=\"btn_nomal_darkmint\" title=\"["+v_subjnm+"]과정을 신청합니다\">수강신청</button>";
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
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%=v_classname %></td>
						<td class="title">
							<a href="javascript:whenSubjInfo('<%=v_subj %>','<%=v_subjseq %>','<%=v_seq %>','<%=v_menuid %>');" tabindex="<%=i+12%>-1" title="[2010.04.01] <%=v_subjnm %>"><%=v_subjnm %></a>
						</td>
						<td><%=v_edustart.substring(5,10)%>~<%=v_propstart.substring(5,10) %></td>
						<td><%=v_biyong %></td>
						<td><%=v_status %>
						</td>
					</tr>
						<%
                		}
           				} else { %>
						<tr><td colspan="5" class="nodata linefirst">등록된 게시물이 없습니다.</td></tr>
					<%  } %>    
				</table>
			</div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_totalrowcount) %>
			</div>
		</div>
	</div>
      
      
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->