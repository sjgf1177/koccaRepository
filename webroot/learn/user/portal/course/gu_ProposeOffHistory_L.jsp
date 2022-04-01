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

    list1 = (ArrayList)request.getAttribute("ProposeOffHistoryList");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
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
    
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name= "form1" method= "post">
    <input type='hidden' name='p_process'   />
    <input type='hidden' name='p_userid'    />
    <input type='hidden' name='p_subj'      />
    <input type='hidden' name='p_year'      />
    <input type='hidden' name='p_subjseq'   />
    <input type='hidden' name='p_seq'       />
    <input type='hidden' name='p_subjnm'    />
    <input type='hidden' name='p_tid'	    />
    <input type='hidden' name='p_paymethod' />
    <input type='hidden' name='p_listgubun' />
    
    
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
        <td><img src="/images/portal/homepage_renewal/myclass/stitle_07.gif" alt="교육신청 확인/취소" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>교육신청 확인/취소</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <!--타이틀부분//-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/myclass/bar_05.jpg" width="672" height="104" /></td>
      </tr>
      <tr>
      	<td>
      		<br/>
      		<span style="txt">※ <strong>“교육비가 유료인 경우  수강료결제 조회/납부->오프라인과정”</strong>에서 결제를 완료하셔야 수강신청이 완료됩니다.</span><br>
            <span style="txt">&nbsp;&nbsp;&nbsp;&nbsp;수강료 결제는 운영진 1차 승인 후, 가능합니다.</span>
            <a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage"><span style="vertical-align:middle;"><img src="/images/portal/homepage_renewal/myclass/btn_offline_ok.jpg" alt="결제하기 바로가기" style="CURSOR:POINTER"/></span></a>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <!--탭부분-->
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeHistoryPage" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image55','','/images/portal/homepage_renewal/myclass/tab_on_001.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_off_001.gif" name="Image55" id="Image55" /></a></td>
        <td><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeOffHistoryPage" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image56','','/images/portal/homepage_renewal/myclass/tab_on_002.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_on_002.gif" name="Image56" id="Image56" /></a></td>
      </tr>
    </table>
    <!--탭부분//-->
    <!--테이블부분-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>        
        <td class="tit_table" width="50%">과정명</td>
        <td class="tit_table" width="7%">교육비</td>
        <td class="tit_table" width="13%">교육기간</td>
        <td class="tit_table" width="12%">수강신청일</td>
        <td class="tit_table" width="8%">승인상태<br />
          결제상태</td>
        <td class="tit_table_right" width="12%">비고</td>
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
      <tr>
        <td class="ct_tit_table_left"><%= v_subjnm %></td>
        <td class="ct_tit_table" width="5%"><%if(v_biyong == 0){%>무료<%}else{%><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %><%} %></td>
        <td class="ct_tit_table_left"><%= v_edustart %> ~ <%= v_eduend %></td>
        <td class="ct_tit_table"><%if(!v_appdate.equals("")){%><%= v_appdate %><%}else{ %>&nbsp;<%} %></td>
        <td class="ct_tit_table"><%if(!v_accept_state.equals("")){ %><%= v_accept_state %><%}else{ %>&nbsp;<%} %><br />
          <%
				if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
%>
                  <a href="javascript:payApply('PROP','<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')"  class="board_btn2">
                  	<img src="/images/portal/homepage_renewal/myclass/btn_off_pay.jpg" />
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
        <td class="ct_tit_table_right">
        	<%
            if (!"".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
            	if (!"".equals(dbox.getString("d_canceldate"))) {
%>
             <span class="fc_96"> 취소요청일</span><br/><%= v_canceldate %>
<%                	
				} else {
						if ("Y".equals(v_cancelableyn)) {
%>
              <a href="javascript:cancelPropose('<%= v_tid %>','<%= v_paymethod %>')" class="board_btn2">
              	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel.jpg" />
              </a>
<%
						} else {
%>
			  <a href="javascript:cancelApply('<%= v_tid %>','<%= v_paymethod %>')" class="board_btn2">
			  	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel_y.jpg" />
			  </a>
<%
						}
				}
            } else if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
%>
              <a href="javascript:cancelApplyFirst('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')" class="board_btn2">
              	<img src="/images/portal/homepage_renewal/myclass/btn_off_cancel.jpg" />
              </a><br>
              <a href="javascript:updatePropose('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_subjnm %>')" class="board_btn2">
              	<img src="/images/portal/homepage_renewal/myclass/btn_off_info.jpg" />
              </a>
<%
        	} else {
        		if ("Y".equals(v_refundyn)) {
%>
		  <span class="fc_96">취소일</span><br/><span class="ff_t fs_s"><%= v_refunddate %></span>
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
      		<td colspan="8">
      			수강신청 이력이 없습니다.
      		</td>
      	</tr>
      <%} %>
      <tr>
        <td height="1" colspan="7" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="7" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    </td>
    <td width="20">&nbsp;</td>
   
  </tr>
</table>
      	
      	<%}else{ %>

			<h2><img src="/images/portal/myclass/h2_tit4.gif" alt="수강신청확인/취소" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>수강신청확인/취소</strong></p></h2>
 
			<%--<p class="myclass_tab cltxt_4_off"><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeHistoryPage"><img src="/images/portal/myclass/myclass_online_tab1_off.gif" alt="온라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a><img src="/images/portal/myclass/myclass_online_tab2_on.gif" alt="오프라인과정" /></p>--%>
            <img src="/images/portal/myclass/apply_borimg_off.gif" alt="" style="width:681px;background-position: left 18px;background-repeat: no-repeat;">
            <span style="color:red">※ <strong>“수강료결제 조회/납부->오프라인과정”</strong>에서 결제를 완료하셔야 수강신청이 완료됩니다.</span><br>
            <span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;수강료 결제는 운영진 1차 승인 후, 가능합니다.</span>
            <a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage"><span style="vertical-align:middle;"><img src="/images/portal/btn/btn_basket_buy.gif" alt="결제하기 바로가기" style="CURSOR:POINTER"/></span></a><br>
            <p style="line-height:7px">&nbsp;</p>
            <a href="/servlet/controller.study.MyClassServlet?p_process=ProposeHistoryPage"><img src="/images/portal/btn/btn_online_off.gif" alt="온라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a><img src="/images/portal/btn/btn_offline_on.gif" alt="온프라인과정">
			<table class="board_list">
			<colgroup><col width="90px" /><col width="200px" /><col width="120px" /><col width="78px" /><col width="70px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th class="row"><p>
                  <kocca_select:select name="p_upperclass" sqlNum="offupperclass"  param=" "
	              onChange="selectList();" attr="분류" selectedValue="<%= v_upperclass %>" isLoad="true" all="none" type="3"/>
                </p></th>
				<th class="row"><p>과정명</p></th>
				<th><p>교육기간<br />수강신청일</p></th>
				<th><p>승인상태<br />결제상태</p></th>
				<th class="end"><p>비고</p></th>
			</tr>
			</thead>
			<tbody>
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

				v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
				v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
				v_appdate		= FormatDate.getFormatDate(v_appdate,"yyyy.MM.dd");
				v_refundabledate= FormatDate.getFormatDate(v_refundabledate,"yyyy.MM.dd");
				v_canceldate	= FormatDate.getFormatDate(v_canceldate,"yyyy.MM.dd");
				v_refunddate	= FormatDate.getFormatDate(v_refunddate,"yyyy.MM.dd");
				
				if ("U".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
//					v_accept_state	= "발표대기";
					v_accept_state	= "미처리";
				} else if ("Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
//					v_accept_state	= "예비합격";
					v_accept_state	= "1차승인";
				} else if ("Y".equals(v_chkfirst) && "N".equals(v_chkfinal)) {
					v_accept_state	= "합격처리 후 불합격";
				} else if ("Y".equals(v_chkfirst) && "Y".equals(v_chkfinal)) {
//					v_accept_state	= "합격";
					v_accept_state	= "최종승인";
				} else if ("N".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
//					v_accept_state	= "불합격";
					v_accept_state	= "미승인";
				}
%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td><%= v_classname %></td>
				<td class="ta_l"><%= v_subjnm %></td>
				<td class="num"><%= v_edustart %> ~ <%= v_eduend %><br /> <%= v_appdate %></td>
				<td class="fs_s"><%= v_accept_state %><br />
<%
				if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_chkfirst) && "U".equals(v_chkfinal)) {
%>
                  <a href="javascript:payApply('PROP','<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')"  class="board_btn2"><span>결제하기</span></a>
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
				<td class="fs_s">
<%
                if (!"".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
                	if (!"".equals(dbox.getString("d_canceldate"))) {
%>
                 <span class="fc_96"> 취소요청일</span><br/><span class="ff_t fs_s"><%= v_canceldate %>
<%                	
					} else {
							if ("Y".equals(v_cancelableyn)) {
%>
                  <a href="javascript:cancelPropose('<%= v_tid %>','<%= v_paymethod %>')" class="board_btn2"><span>수강취소</span></a>
<%
							} else {
%>
				  <a href="javascript:cancelApply('<%= v_tid %>','<%= v_paymethod %>')" class="board_btn2"><span>취소요청</span></a>
<%
							}
					}
                } else if ("".equals(v_tid) && "N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
%>
                  <a href="javascript:cancelApplyFirst('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_seq %>')" class="board_btn2"><span>수강취소</span></a><br>
                  <a href="javascript:updatePropose('<%= v_subj %>','<%= v_year %>','<%= v_subjseq %>','<%= v_subjnm %>')" class="board_btn2"><span>정보수정</span></a>
<%
            	} else {
            		if ("Y".equals(v_refundyn)) {
%>
			  <span class="fc_96">취소일</span><br/><span class="ff_t fs_s"><%= v_refunddate %></span>
<%
            		}
            	}
%>
            	</td>
			</tr>
<%
			}
		}
%>
<%
		if(i == 0){
%>
            <tr>
                <td colspan="8">수강신청 이력이 없습니다.</td>
            </tr>
              <%
        }
%>
			</tbody>
			</table>
			<%} %>
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->