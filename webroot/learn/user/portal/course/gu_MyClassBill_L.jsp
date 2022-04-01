<%
//**********************************************************
//  1. 제      목: 수강신청 확인 취소 목록
//  2. 프로그램명: gu_MyClassBill_L.jsp
//  3. 개      요: 수강신청 확인 취소 목록
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
	String	v_userid		= "";
	String	v_usernm		= "";
    String	v_goodname		= "";
    String	v_inputdate		= "";
    double	v_price			= 0;
    double	v_biyong		= 0;
    String	v_resultcode	= "";
    String	v_paymethod		= "";
	String	v_buyername		= "";
    String	v_pgauthdate	= "";
    String	v_cancelyn		= "";
    String	v_canceldate	= "";
    String	v_canceltime	= "";
    String	v_paystatus		= "";
    String	v_ldate			= "";

    int     i           =  0;
    int     l           =  0;

    ArrayList list1     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("MyClassBillList");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<script type="text/javascript"  src="/script/jquery-1.3.2.min.js"></script>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="javascript">
<!--
	//검색내용에 맞는 리스트 페이지로 이동
	function selectList() {
    	document.form1.action = "/servlet/controller.study.MyClassBillServlet";
    	document.form1.p_process.value = "MyClassBillListPage";
    	document.form1.submit();
	}

	//수강신청 취소 팝업
	function cancelApply(p_tid, p_paymethod){
	    window.self.name = "winProposePage";
	    farwindow = window.open("", "openWinCancelApply", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 550, height = 450, top=0, left=0");
	    document.form1.target = "openWinCancelApply"
	    document.form1.p_tid.value = p_tid;
	    document.form1.p_paymethod.value = p_paymethod;
		document.form1.p_process.value = 'ProposeCancelApplyPage';
	    document.form1.action = "/servlet/controller.study.MyClassBillServlet";
	    document.form1.submit();

	    farwindow.window.focus();
	    document.form1.target = window.self.name;
	}

	//결제세부정보 조회
	function fnPayInfo(p_tid) {
		window.self.name = "winProposePage";
		open_window("openPayInfo","","100","100","550","450","no","no","no","yes","yes");
		document.form1.target = "openPayInfo";
		document.form1.p_tid.value = p_tid;
		document.form1.action='/servlet/controller.study.MyClassBillServlet';
		document.form1.p_process.value = 'PayInfoPage';
		document.form1.submit();
	    document.form1.target = window.self.name;
	}

    //영수증 확인하는곳
    function receipt(tid)
    {
      var receiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=" + tid + "&noMethod=1";
      var a=window.open(receiptUrl,"receipt","width=430,height=700");
      //a.document.title="aaa";
    }
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_userid'    >
    <input type='hidden' name='p_subj'      >
    <input type='hidden' name='p_year'      >
    <input type='hidden' name='p_subjseq'   >
    <input type='hidden' name='p_subjnm'    >
    <input type='hidden' name='p_tid'	    >
    <input type='hidden' name='p_paymethod' >
    
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
        <td><img src="/images/portal/homepage_renewal/myclass/stitle_08.gif" alt="수강료 결제 조회/납부" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>수강료 결제 조회/납부</strong></td>
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
        <td><img src="/images/portal/homepage_renewal/myclass/bar_06.jpg" width="672" height="126" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
    <!--탭부분-->
    <table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><a href="/servlet/controller.study.MyClassBillServlet?p_process=MyClassBillListPage" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image55','','/images/portal/homepage_renewal/myclass/tab_on_001.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_on_001.gif" name="Image55" id="Image55" /></a></td>
        <td><a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image56','','/images/portal/homepage_renewal/myclass/tab_on_002.gif',1)"><img src="/images/portal/homepage_renewal/myclass/tab_off_002.gif" name="Image56" id="Image56" /></a></td>
      </tr>
    </table>
    <!--탭부분//-->
    <!--테이블부분-->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">과정명</td>
        <td class="tit_table">수강신청일</td>
        <td class="tit_table">교육료</td>
        <td class="tit_table">결제상태</td>
        <td class="tit_table">결제일자</td>
        <td class="tit_table_right">취소완료일</td>
      </tr>
      <%
		if(list1 != null)
		{
			for(i = 0; i < list1.size(); i++)
			{
				DataBox dbox = (DataBox)list1.get(i);

				
				v_tid			= dbox.getString("d_tid");
				v_userid		= dbox.getString("d_userid");
				v_usernm		= dbox.getString("d_usernm");
			    v_goodname		= dbox.getString("d_goodname");
			    v_inputdate		= dbox.getString("d_inputdate");
			    v_price			= dbox.getDouble("d_price");
			    v_biyong		= dbox.getDouble("d_biyong");
			    v_resultcode	= dbox.getString("d_resultcode");
			    v_paymethod		= dbox.getString("d_paymethod");
				v_buyername		= dbox.getString("d_buyername");
			    v_pgauthdate	= dbox.getString("d_pgauthdate");
			    v_cancelyn		= dbox.getString("d_cancelyn");
			    v_canceldate	= dbox.getString("d_canceldate");
			    v_canceltime	= dbox.getString("d_canceltime");
			    v_paystatus		= dbox.getString("d_paystatus");
			    v_ldate			= dbox.getString("d_ldate");

				if ("BankBook".equals(v_paymethod)) {
					v_paymethod = "무통장";
				} else if ("Card".equals(v_paymethod)) {
					v_paymethod = "신용카드(ISP)";
				} else if ("DirectBank".equals(v_paymethod)) {
					v_paymethod = "실시간계좌이체";
				} else if ("TaxBill".equals(v_paymethod)) {
					v_paymethod = "계산서";
				} else if ("VBank".equals(v_paymethod)) {
					v_paymethod = "무통장(가상계좌)";
				} else if ("VCard".equals(v_paymethod)) {
					v_paymethod = "신용카드(안심클릭)";
				} else if ("FreePay".equals(v_paymethod)) {
					v_paymethod = "무료결제";
				}

				v_inputdate		= FormatDate.getFormatDate(v_inputdate,"yyyy.MM.dd");
				v_pgauthdate	= FormatDate.getFormatDate(v_pgauthdate,"yyyy.MM.dd");
				v_canceldate	= FormatDate.getFormatDate(v_canceldate,"yyyy.MM.dd");
%>
      <tr>
        <td class="ct_tit_table_left"><a href="javascript:fnPayInfo('<%= v_tid %>')"><%= v_goodname %></a></td>
        <td class="ct_tit_table"><%= v_inputdate %></td>
        <td class="ct_tit_table"><strong><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %></strong></td>
        <td class="ct_tit_table"><%= "Y".equals(v_cancelyn) ? "환불완료" : "결제완료" %></td>
        <td class="ct_tit_table">
        		<% if (v_price==0 || v_paymethod.equals("무료결제")) {%>
                         <%= v_pgauthdate %>
                     <% } else { %>
                        <a href="javascript:receipt('<%=v_tid%>')" style="letter-spacing: 0px; font-size: 12px;"><font color="red"><%= v_pgauthdate %></font></a>
                    <%}%>
                    </td>
        <td class="ct_tit_table_right"><%if(!v_canceldate.equals("")){%><%= v_canceldate %><%}else{ %>&nbsp;<%} %></td>
      </tr>      
      <%} }%>
      <tr>
        <td height="1" colspan="6" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="6" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
      	
      	<%}else{ %>

			<h2><img src="/images/portal/myclass/h2_tit5.gif" alt="수강료결제 조회/납부" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>수강료결제조회/납부</strong></p></h2>

			<%--<p class="myclass_tab cltxt_5"><img src="/images/portal/myclass/myclass_online_tab1_on.gif" alt="온라인과정" /><a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage"><img src="/images/portal/myclass/myclass_online_tab2_off.gif" alt="오프라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></p>--%>
            <img src="/images/portal/myclass/sletter_borimg.gif" alt="" style="width:681px;background-position: left 18px;background-repeat: no-repeat;">
            <p style="line-height:7px">&nbsp;</p>
            <img src="/images/portal/btn/btn_online_on.gif" alt="온라인과정"><a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage"><img src="/images/portal/btn/btn_offline_off.gif" alt="오프라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
			
    <table class="board_list">
			<colgroup><col width="200px" /><col width="80px" /><col width="100px" /><col width="70px" /><col width="70px" /><col width="70px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th class="row"><p>결제명 </p></th>
				<th class="row"><p>수강신청일</p></th>
				<th><p>수강료<br />실결제액</p></th>
				<th><p>결제상태<br />결제방법</p></th>
				<th class="row"><p>결제일자</p></th>
				<th class="end"><p>취소완료일</p></th>
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
				v_userid		= dbox.getString("d_userid");
				v_usernm		= dbox.getString("d_usernm");
			    v_goodname		= dbox.getString("d_goodname");
			    v_inputdate		= dbox.getString("d_inputdate");
			    v_price			= dbox.getDouble("d_price");
			    v_biyong		= dbox.getDouble("d_biyong");
			    v_resultcode	= dbox.getString("d_resultcode");
			    v_paymethod		= dbox.getString("d_paymethod");
				v_buyername		= dbox.getString("d_buyername");
			    v_pgauthdate	= dbox.getString("d_pgauthdate");
			    v_cancelyn		= dbox.getString("d_cancelyn");
			    v_canceldate	= dbox.getString("d_canceldate");
			    v_canceltime	= dbox.getString("d_canceltime");
			    v_paystatus		= dbox.getString("d_paystatus");
			    v_ldate			= dbox.getString("d_ldate");

				if ("BankBook".equals(v_paymethod)) {
					v_paymethod = "무통장";
				} else if ("Card".equals(v_paymethod)) {
					v_paymethod = "신용카드(ISP)";
				} else if ("DirectBank".equals(v_paymethod)) {
					v_paymethod = "실시간계좌이체";
				} else if ("TaxBill".equals(v_paymethod)) {
					v_paymethod = "계산서";
				} else if ("VBank".equals(v_paymethod)) {
					v_paymethod = "무통장(가상계좌)";
				} else if ("VCard".equals(v_paymethod)) {
					v_paymethod = "신용카드(안심클릭)";
				} else if ("FreePay".equals(v_paymethod)) {
					v_paymethod = "무료결제";
				}

				v_inputdate		= FormatDate.getFormatDate(v_inputdate,"yyyy.MM.dd");
				v_pgauthdate	= FormatDate.getFormatDate(v_pgauthdate,"yyyy.MM.dd");
				v_canceldate	= FormatDate.getFormatDate(v_canceldate,"yyyy.MM.dd");
%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="ta_l"><a href="javascript:fnPayInfo('<%= v_tid %>')"><%= v_goodname %></a></td>
				<td class="num"><%= v_inputdate %></td>
				<td class="num"><span class="td_lt"><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %></span><br /><strong><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %></strong></td>
				<td class="fs_s"><%= "Y".equals(v_cancelyn) ? "환불완료" : "결제완료" %><br /><%= v_paymethod %></td>
				<td class="num">
                    <% if (v_price==0) {
                         out.print(v_pgauthdate);
                      } else { %>
                        <a href="javascript:receipt('<%=v_tid%>')"><font color="red"><%= v_pgauthdate %></font></a>
                    <%}%>
                </td>
				<td class="num"><%= v_canceldate %></td>
			</tr>
<%
			}
		}
%>
<%
		if(i == 0){
%>
            <tr>
                <td colspan="6">결제내역이 없습니다.</td>
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