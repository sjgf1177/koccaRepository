<%
//**********************************************************
//  1. 제      목: 오프라인 수강신청 학인 취소 목록
//  2. 프로그램명: gu_ProposeOffHistory_L.jsp
//  3. 개      요: 오프라인수강신청 확인 취소 목록
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009. 12. 14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","2");
	box.put("submenu","3");

    String  v_process   	= box.getString("p_process");
    String  v_user_id   	= box.getSession("userid");
    String  v_tem_grcode	= box.getSession("tem_grcode");
    String  v_upperclass	= box.getString("v_upperclass");
        
	String	v_tid			= "";
	String	v_subj			= "";
	String	v_year			= "";
    String	v_subjseq		= "";
    String	v_seq			= "";
    String	v_subjnm		= "";
    String	v_edustart		= "";
    String	v_eduend		= "";
    String	v_appdate		= "";
    String	v_classname		= "";
	String	v_refundabledate= "";
    String	v_refundableyn	= "";
    String	v_refundyn		= "";
    String	v_canceldate	= "";
    String	v_paymethod		= "";
    String	v_chkfirst		= "";
    String	v_chkfinal		= "";
    String	v_accept_state	= "";
    String	v_rank			= "";
    String	v_cancelableyn	= "";
    String	v_refunddate	= "";
    double	v_biyong		= 0;
    int		v_rowspan		= 0;

    int     i           =  0;
    int     l           =  0;

    ArrayList list1     = null;
    //DEFINED class&variable END
    
    int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
	
	 int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;

    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   

	    

    list1 = (ArrayList)request.getAttribute("ProposeOffHistoryList");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="javascript">
<!--
	//검색내용에 맞는 리스트 페이지로 이동
	function selectList() {
    	document.form1.action = "/servlet/controller.study.MyClassServlet";
    	document.form1.p_process.value = "ProposeOffHistoryPage";
    	document.form1.submit();
	}

	//수강신청 결제팝업
	function payApply(p_listgubun, p_subj, p_year, p_subjseq, p_seq){
	    window.self.name = "winProposePage";
	    farwindow = window.open("", "openWinPayApply", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 780, height = 800, top=0, left=0");
		document.form1.p_listgubun.value = p_listgubun;
		document.form1.p_subj.value = p_subj;
		document.form1.p_year.value = p_year;
		document.form1.p_subjseq.value = p_subjseq;
		document.form1.p_seq.value = p_seq;
	    document.form1.target = "openWinPayApply"
		document.form1.action = "/inicis/plugin_check_new_off.jsp";
	    document.form1.submit();
	
	    farwindow.window.focus();
	    document.form1.target = window.self.name;
	}

	//수강취소
	function cancelPropose(p_tid, p_paymethod){

	    if(!confirm("수강을 취소하시겠습니까?\n수강신청내용과 결제정보가 취소됩니다.")){
	        return;
	    }
	   		
	    document.form1.target = "_self"
	    document.form1.p_tid.value = p_tid;
	    document.form1.p_paymethod.value = p_paymethod;
		document.form1.p_process.value = 'CancelOffPropose';
	    document.form1.action = "/servlet/controller.study.MyClassServlet";
	    document.form1.submit();
	}
	
	//수강신청 취소 팝업
	function cancelApply(p_tid, p_paymethod){
	    window.self.name = "winProposePage";
	    farwindow = window.open("", "openWinCancelApply", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 550, height = 400, top=0, left=0");
	    document.form1.target = "openWinCancelApply"
	    document.form1.p_tid.value = p_tid;
	    document.form1.p_paymethod.value = p_paymethod;
		document.form1.p_process.value = 'ProposeOffCancelApplyPage';
	    document.form1.action = "/servlet/controller.study.MyClassServlet";
	    document.form1.submit();

	    farwindow.window.focus();
	    document.form1.target = window.self.name;
	}

    //수강취소(신청만 하고 승인이나 결재가 없는경우
	function cancelApplyFirst(v_subj,v_year,v_subjseq,v_seq){
        if(!confirm("수강을 취소하시겠습니까?")){
	        return;
	    }
        document.form1.target = "_self"
	    document.form1.p_tid.value = "";
	    document.form1.p_paymethod.value = "";
        document.form1.p_listgubun.value = "PROP";
		document.form1.p_subj.value = v_subj;
		document.form1.p_year.value = v_year;
		document.form1.p_subjseq.value = v_subjseq;
		document.form1.p_seq.value = v_seq;
		document.form1.p_process.value = 'ProposeOffCancelFirst';
	    document.form1.action = "/servlet/controller.study.MyClassServlet";
	    document.form1.submit();
	}

//수강신청정보수정
function updatePropose(subj,year,subjseq, subjnm) {
	if(<%= box.getSession("userid").length()==0 %>) {
		alert("로그인이 필요합니다.");
		return;
	}

	var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
	document.form1.p_process.value = "UpdateSubjectEduProposePage";
	document.form1.target = "proposeWindow";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.action = "/servlet/controller.off.ProposeOffServlet";
	document.form1.submit();
	document.form1.target = "_self";
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_process.value = "ProposeOffHistoryPage";
	document.form1.submit();

}
    
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name= "form1" method= "post" action="">
    <input type='hidden' name='p_process'  >
    <input type='hidden' name='p_userid'   >
    <input type='hidden' name='p_subj'     >
    <input type='hidden' name='p_year'     >
    <input type='hidden' name='p_subjseq'  >
    <input type='hidden' name='p_seq'      >
    <input type='hidden' name='p_subjnm'   >
    <input type='hidden' name='p_tid'	   >
    <input type='hidden' name='p_paymethod'>
    <input type='hidden' name='p_listgubun'>
    <input type="hidden" name="p_pageno" value="<%= v_pageno %>">
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>">
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 ><u>교육신청 확인/취소</u></td>
			</tr>
		</table>
		<div class="concept a03">
			<div class="titlemsg"><img src="/images/2012/sub/page1/title/learning_request.gif" alt="수강신청 확인/취소"></div>
			<ul class="pagetab">
				<li><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeHistoryPage" tabindex="171" title="회원님의 온라인 수강신청 내역을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/online_off.png" alt="온라인과정"></a></li>
				<li><img src="/images/2012/sub/page1/tabmenu/offline_on.png" alt="오프라인과정"></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="classtip">
				<h6><img src="/images/2012/sub/icon_dot.png" alt="dot"> “<span>교육비가 유료인 경우 수강료결제 조회/납부 > 오프라인과정</span>”에서 결제를 완료하셔야 수강신청이 완료됩니다.</h6>
				<h6><img src="/images/2012/sub/icon_dot.png" alt="dot"> 수강료 결제는 운영진 1차 승인 후, 가능합니다. <a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage" tabindex="172" title="결제 바로가기"><u>오프라인 결제하기</u></a></h6>
			</div>
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="*"><col width="80"><col width="140"><col width="80"><col width="70"><col width="75"><col width="75"></colgroup>
					<tr class="listhead">
						<th>과정명</th>
						<th>교육비(원)</th>
						<th>교육기간</th>
						<th>수강신청일</th>
						<th>승인상태</th>
						<th>결제상태</th>
						<th>비고</th>
					</tr>
					
					<%
						if(list1 != null)
						{
							for(i = 0; i < list1.size(); i++)
							{
								DataBox dbox = (DataBox)list1.get(i);
							   
								v_tid			= dbox.getString("d_tid");
								v_subj			= dbox.getString("d_subj");
								v_year			= dbox.getString("d_year");
								v_subjseq		= dbox.getString("d_subjseq");
								v_seq			= dbox.getString("d_seq");
								v_subjnm		= dbox.getString("d_subjnm");
								v_edustart		= dbox.getString("d_edustart");
								v_eduend		= dbox.getString("d_eduend");
								v_appdate		= dbox.getString("d_appdate");
								v_classname		= dbox.getString("d_classname");
								v_refundabledate= dbox.getString("d_refundabledate");
								v_refundableyn	= dbox.getString("d_refundableyn");
								v_refundyn		= dbox.getString("d_refundyn");
								v_canceldate	= dbox.getString("d_canceldate");
								v_paymethod		= dbox.getString("d_paymethod");
								v_chkfirst		= dbox.getString("d_chkfirst");
								v_chkfinal		= dbox.getString("d_chkfinal");
								v_rank			= dbox.getString("d_rank");
								v_cancelableyn	= dbox.getString("d_cancelableyn");
								v_refunddate	= dbox.getString("d_refunddate");
								v_rowspan		= dbox.getInt("d_rowspan");
								v_biyong		= dbox.getDouble("d_biyong");
				
								v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
								v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
								v_appdate		= FormatDate.getFormatDate(v_appdate,"yyyy.MM.dd");
								v_refundabledate= FormatDate.getFormatDate(v_refundabledate,"yyyy.MM.dd");
								v_canceldate	= FormatDate.getFormatDate(v_canceldate,"yyyy.MM.dd");
								v_refunddate	= FormatDate.getFormatDate(v_refunddate,"yyyy.MM.dd");
								
								v_dispnum	= dbox.getInt("d_dispnum");
						        v_totalpage	= dbox.getInt("d_totalpage");
								
								if ("U".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
				//					v_accept_state	= "발표대기";
									v_accept_state	= "미처리";
								} else if ("Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
				//					v_accept_state	= "예비합격";
									v_accept_state	= "1차승인";
								} else if ("Y".equals(v_chkfirst) && "N".equals(v_chkfinal)) {
									v_accept_state	= "최종불합격";
								} else if ("Y".equals(v_chkfirst) && "Y".equals(v_chkfinal)) {
				//					v_accept_state	= "합격";
									v_accept_state	= "최종승인";
								} else if ("N".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
				//					v_accept_state	= "불합격";
									v_accept_state	= "미승인";
								}
				%>
					
					
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst title"><%= v_subjnm %></td>
						<td><%if(v_biyong == 0){%>무료<%}else{%><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %><%} %></td>
						<td><%= v_edustart %> ~ <%= v_eduend %></td>
						<td><%if(!v_appdate.equals("")){%><%= v_appdate %><%}else{ %>&nbsp;<%} %></td>
						<td><%if(!v_accept_state.equals("")){ %><%= v_accept_state %><%}else{ %>&nbsp;<%} %></td>
						<td>
							<%
								if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
							%>
				            	<a href="javascript:payApply('PROP','<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')" tabindex="<%="2"+i+"1"%>">
				                  	<img src="/images/portal/homepage_renewal/myclass/btn_off_pay.jpg" alt="결제하기">
				                  </a>
							<%
								} else if (!"".equals(v_tid) && "N".equals(v_refundyn)) {
							%>
							                 결제완료
							<%
								} else if ("Y".equals(v_refundyn)) {
							%>
							                  환불완료
							<%
								}
							%>
						</td>
						<td>
							<%
							            if (!"".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
							            	if (!"".equals(dbox.getString("d_canceldate"))) {
							%>
							             <span class="fc_96"> 취소요청일</span><br><%= v_canceldate %>
							<%                	
											} else {
													if ("Y".equals(v_cancelableyn)) {
							%>
							              <a href="javascript:cancelPropose('<%= v_tid %>','<%= v_paymethod %>')" tabindex="<%="2"+i+"2"%>" title="수강신청을 취소합니다">
							              	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel.jpg" alt="수강취소">
							              </a>
							<%
													} else {
							%>
										  <a href="javascript:cancelApply('<%= v_tid %>','<%= v_paymethod %>')" tabindex="<%="2"+i+"2"%>" title="취소를 요청합니다">
										  	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel_y.jpg" alt="취소요청">
										  </a>
							<%
													}
											}
							            } else if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
							%>
							              <a href="javascript:cancelApplyFirst('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')" tabindex="<%="2"+i+"3"%>" title="수강신청을 취소합니다">
							              	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel.jpg" alt="수강취소">
							              </a><br>
							              <a href="javascript:updatePropose('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_subjnm %>')" tabindex="<%="2"+i+"3"%>" title="수강정보를 수정합니다">
							              	<img src="/images/portal/homepage_renewal/myclass/btn_off_info.jpg" alt="정보수정">
							              </a>
							<%
							        	} else {
							        		if ("Y".equals(v_refundyn)) {
							%>
									  취소일<br><%= v_refunddate %>
							<%
							        		}else{%>
							        		&nbsp;<%}
							        	}
							%>
						</td>
					</tr>
					
					<%} 
				      }
				      if(i == 0){%>
				      	<tr>
				      		<td colspan="7" class="linefirst">
				      			수강신청 이력이 없습니다.
				      		</td>
				      	</tr>
				      <%} %>
				</table>
			</div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
