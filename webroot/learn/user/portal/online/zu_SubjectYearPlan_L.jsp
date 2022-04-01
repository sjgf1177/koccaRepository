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
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    
    GregorianCalendar   calendar    = new GregorianCalendar();
    String currentYear = Integer.toString(calendar.get(Calendar.YEAR));

    String  v_gyear         = box.getStringDefault("p_year", currentYear);           //년도
    String  v_month         = box.getStringDefault("p_month", "01");                  // 월
    String  v_upperclass    = box.getStringDefault("p_upperclass", "C01");            // 대분류
    String  v_middleclass   = box.getString("p_middleclass");                         // 중분류
    
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
    String v_classname      = "";
    
    int    v_month_cnt   =  0;
    int    v_subj_cnt    =  0;


    
    ArrayList schlYearPlanList = (ArrayList)request.getAttribute("schlYearPlanList");
    int listSize    = schlYearPlanList != null ? schlYearPlanList.size() : 0 ;
    int subCnt      = schlYearPlanList != null && listSize > 0 ? ((DataBox)schlYearPlanList.get(0)).getInt("d_total_subj") : 0 ;
    	
    int     v_dispnum = 0;
    int     v_totalrowcount = schlYearPlanList != null &&  listSize > 0 ? ((DataBox)schlYearPlanList.get(0)).getInt("d_totalrowcount") : 0;
    int     v_totalpage = 0;           
      
    int v_pageno             = box.getInt("p_pageno"); 
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    

    String v_classname1      = "end stxt";
    String v_classname2      = "end";
    
    DecimalFormat   dFormat = new DecimalFormat();
    
    ArrayList<DataBox> upperList = null;        // 과정 대분류 리스트
    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>
    
    upperList  = ClassifySubjectBean.getUpperClassList2(box); 
    upperMap   = ClassifySubjectBean.getMenuId(box);
                  
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
    document.form1.p_process.value = "schlYearPlanList";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
     document.form1.p_process.value = "schlYearPlanList";
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
    document.form1.p_process.value = "schlYearPlanList";
    document.form1.submit();
}

function moveTab(upperclass){
	 document.form1.p_upperclass.value = upperclass;
	 document.form1.p_middleclass.value = "";
     document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
     document.form1.p_process.value = "schlYearPlanList";
     document.form1.submit();
}
function changeYear(){
	document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
    document.form1.p_process.value = "schlYearPlanList";
    document.form1.submit();
}

function moveMonthList(month){
	document.form1.p_gyear.value = document.form1.p_year.value;
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.menuid.value = 11;
    document.form1.p_month.value = month;
    document.form1.action = '/servlet/controller.course.EduScheduleHomePageServlet';
    document.form1.p_process.value = "schlMonthPlanList";
    document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj, menuid){
	document.form1.target = "proposeWindow";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = "";
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
      <input type = "hidden" name = "p_month"       value = "<%=v_month %>">
      <input type = "hidden" name = "p_subj"        value = "">
      <input type = "hidden" name = "p_subjseq"     value = "">
      <input type = "hidden" name = "p_upperclass"  value = "<%=v_upperclass %>">
      <input type = "hidden" name = "menuid"        value = "">
      
      <input type = "hidden" name = "p_gyear"       value = "">
      
      
            <h2><img src="/images/portal/online/h2_tit5.gif" alt="연간교육일정" class="fl_l" /><p class="category">Home > 온라인과정 > <strong>연간교육일정</strong></p></h2>                                     
                                                                                                                                                                                                 
            <p><img src="/images/portal/online/year_txt.gif" alt="온라인 과정 연간교육일정입니다. 교육과정명을 클릭하시면 과정내용을 보실 수 있으며, 월을 클릭하시면 해당월의 교육과정을 보실 수 있습니다 " /></p>

            <ul class="tabwrap mg_t30">
            <%  if(upperList != null && upperList.size() > 0) {
                	for(int i = 0 ; i < upperList.size() ; i++){ 
                		   DataBox dbox = upperList.get(i); %>
                <li><a <%=v_upperclass.equals(dbox.get("d_upperclass")) ?  "class=\"tab_online tab_online_on\"" : "href=\"javascript:moveTab('"+dbox.get("d_upperclass")+"');\" class=\"tab_online\""%>><span><%=dbox.get("d_classname") %></span></a></li>
            <%      if(v_upperclass.equals(dbox.get("d_upperclass"))) v_classname =  dbox.get("d_classname");
                    }
                } %>
            </ul>
            
            <p class="board_top_search">
                <img src="/images/portal/board/serbox_category.gif" > <kocca_select:select name="p_middleclass" sqlNum="course.0003"  param="<%= v_upperclass %>"
                                                    onChange="changeYear()" styleClass="mg_r12" attr=" " selectedValue="<%= v_middleclass %>" isLoad="true" all="true" />
              
                <img src="/images/portal/board/serbox_year.gif  ">
                    <select name="p_year" class="mg_r12" onchange="changeYear();">
                        <option value="<%=Integer.parseInt(currentYear)+1 %>" <%=v_gyear.equals(""+(Integer.parseInt(currentYear)+1)) ? "selected" : ""%>><%=Integer.parseInt(currentYear)+1 %></option>
                        <option value="<%=currentYear %>"   <%=v_gyear.equals(currentYear) ? "selected" : ""%>><%=currentYear %></option>
                        <option value="<%=Integer.parseInt(currentYear)-1 %>" <%=v_gyear.equals(""+(Integer.parseInt(currentYear)-1)) ? "selected" : ""%>><%=Integer.parseInt(currentYear)-1 %></option>
                    </select>
            </p>
            
            <table class="row_list_prg" >
            <colgroup>
                <col width="100px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
                <col width="48px" />
            </colgroup>
            <thead>
            <tr>
                <th>교육과정<br /><span class="stit">(총 <%=subCnt %>개 과정)</span></th>
                <th><a href="javascript:moveMonthList('01');" class="btn_wred"><span>1월</span></a></th>
                <th><a href="javascript:moveMonthList('02');" class="btn_wred"><span>2월</span></a></th>
                <th><a href="javascript:moveMonthList('03');" class="btn_wred"><span>3월</span></a></th>
                <th><a href="javascript:moveMonthList('04');" class="btn_wred"><span>4월</span></a></th>
                <th><a href="javascript:moveMonthList('05');" class="btn_wred"><span>5월</span></a></th>
                <th><a href="javascript:moveMonthList('06');" class="btn_wred"><span>6월</span></a></th>
                <th><a href="javascript:moveMonthList('07');" class="btn_wred"><span>7월</span></a></th>
                <th><a href="javascript:moveMonthList('08');" class="btn_wred"><span>8월</span></a></th>
                <th><a href="javascript:moveMonthList('09');" class="btn_wred"><span>9월</span></a></th>
                <th><a href="javascript:moveMonthList('10');" class="btn_wred"><span>10월</span></a></th>
                <th><a href="javascript:moveMonthList('11');" class="btn_wred"><span>11월</span></a></th>
                <th><a href="javascript:moveMonthList('12');" class="btn_wred"><span>12월</span></a></th>
            </tr>
            </thead>
            <tbody>
            <%
            if(listSize > 0 ) {
	            for(int i = 0; i < listSize;) {
	                DataBox dbox = (DataBox)schlYearPlanList.get(i);
	
	                v_subj           = dbox.getString("d_subj");        
	                v_year           = dbox.getString("d_year");
	                v_subjnm         = dbox.getString("d_subjnm");
	                v_upperclass = dbox.getString("d_upperclass");
                	
                	v_subj_cnt       = dbox.getInt("d_subj_cnt");

	        %>
	        <tr>
                <td class="ta_l"><a href="javascript:whenSubjInfo('<%=v_subj %>', '<%=upperMap !=null && upperMap.get(v_upperclass) != null ?  upperMap.get(v_upperclass) : "00"%>')"><%=v_subjnm %></a></td>
            <%  for(int k = 1 ; k <= 12 ; k++){ %>
                <td class="data<%=k == 1 || k%2 == 1 ?  " board_bg" : ""%>">
	        <%        
	                for(int j = 0 ; j < v_subj_cnt ; j++){
	                	dbox = (DataBox)schlYearPlanList.get(i+j);
	                	v_month_cnt   = dbox.getInt("d_month_"+(k < 10 ? "0"+k : "k")+"_cnt");
	                	if(v_month_cnt != 0) {
	                		v_status         = dbox.getString("d_status");
	                		v_edustart       = dbox.getString("d_edustart");    
	                        v_eduend         = dbox.getString("d_eduend");   
	                        v_subjseq        = dbox.getString("d_subjseq");
	                        if(v_status.equals("Y")) {
	                        	if( !box.getSession("userid").equals("") ) {
	                            	v_status = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"')\" class=\"board_btn\"><span class=\"pd_l6 pd_r5\">신청</span></a>";
	                        	} else {
	                        		v_status = "<a href=\"javascript:alert('로그인후 사용하실수 있습니다.')\" class=\"board_btn\"><span class=\"pd_l6 pd_r5\">신청</span></a>";
	                        	}
	                        } else {
	                            v_status = "";
	                        }
	                        
	                        int v_edustartMonth = Integer.parseInt(v_edustart.substring(4,6));
	                        int v_edustartDay = Integer.parseInt(v_edustart.substring(6,8));
	                        
	                        int v_eduendYear = Integer.parseInt(v_eduend.substring(0,4));
	                        int v_eduendMonth = Integer.parseInt(v_eduend.substring(4,6));
	                        int v_eduendDay = Integer.parseInt(v_eduend.substring(6,8));
	                        
	                        if(v_eduendYear > Integer.parseInt(v_year)){
	                        	v_eduend  = v_eduendMonth+"."+ v_eduendDay;
	                        } else if(v_eduendMonth > v_edustartMonth){
	                        	v_eduend  = v_eduendMonth+"."+ v_eduendDay;
	                        } else {
	                        	v_eduend = ""+v_eduendDay;
	                        }
	                        
	                        v_edustart = ""+v_edustartDay;
	                        
	                		v_edustart  = "<p>"+v_edustart+"~"+v_eduend+"</p>";
	                		out.println(v_edustart+v_status);
	                	}
	                }
            %>
                </td>
            <% }  %>
            </tr>
            <% i += v_subj_cnt;
                }
            } else { %>
              <tr>
                <td colspan="13">등록된 내용이 없습니다</td>
              </tr>
            <%  } %>
            </tbody>
            </table>
            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->