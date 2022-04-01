<%
//**********************************************************
//  1. 제      목: 수강신청 확인 취소 목록
//  2. 프로그램명: gu_ProposeHistory_L.jsp
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
	String	v_subj			= "";
	String	v_year			= "";
    String	v_subjseq		= "";
    String	v_subjnm		= "";
    String	v_edustart		= "";
    String	v_eduend		= "";
    String	v_appdate		= "";
    String	v_classname		= "";
	String	v_refundabledate= "";
    String	v_refundableyn	= "";
    String	v_cancelableyn	= "";
    String	v_refundyn		= "";
    String	v_canceldate	= "";
    String	v_paymethod		= "";
    String	v_acceptyn		= "";
    String	v_chkfirst		= "";
    String	v_chkfinal		= "";
    String	v_chkstatus		= "";
    String	v_rank			= "";
    String	v_refunddate	= "";
    int		v_rowspan		= 0;

    int     i           =  0;
    int     l           =  0;

    ArrayList list1     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("ProposeHistoryList");
    String grcode=box.getSession("tem_grcode");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="javascript">
<!--
	//검색내용에 맞는 리스트 페이지로 이동
	function selectList() {
    	document.form1.action = "/servlet/controller.study.MyClassServlet";
    	document.form1.p_process.value = "ProposeHistoryPage";
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
	    document.form1.action = "/servlet/controller.study.MyClassServlet";
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
		document.form1.p_process.value = 'CancelPropose';
	    document.form1.action = "/servlet/controller.study.MyClassServlet";
	    document.form1.submit();
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

			<h2><img src="/images/portal/myclass/h2_tit4.gif" alt="수강신청확인/취소" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>수강신청확인/신청</strong></p></h2>


			<%--<p class="myclass_tab cltxt_4_on"><img src="/images/portal/myclass/myclass_online_tab1_on.gif" alt="온라인과정" /><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeOffHistoryPage"><img src="/images/portal/myclass/myclass_online_tab2_off.gif" alt="오프라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></p>--%>
            <img src="/images/portal/myclass/apply_borimg_on.gif" alt="" style="width:681px;background-position: left 18px;background-repeat: no-repeat;">
            <p style="line-height:7px">&nbsp;</p>
    <% if(grcode.equals("N000001")) { %>
            <img src="/images/portal/btn/btn_online_on.gif" alt="온라인과정"><a href="/servlet/controller.study.MyClassServlet?p_process=ProposeOffHistoryPage"><img src="/images/portal/btn/btn_offline_off.gif" alt="온프라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
    <%}%>
			<table class="board_list">
			<colgroup><col width="90px" /><col width="200px" /><col width="120px" /><col width="78px" /><col width="70px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th class="row"><p>
                  <kocca_select:select name="p_upperclass" sqlNum="upperclass"  param=" "
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
				v_subjnm		= dbox.getString("d_subjnm");
				v_edustart		= dbox.getString("d_edustart");
				v_eduend		= dbox.getString("d_eduend");
				v_appdate		= dbox.getString("d_appdate");
				v_classname		= dbox.getString("d_classname");
				v_refundabledate= dbox.getString("d_refundabledate");
				v_refundableyn	= dbox.getString("d_refundableyn");
				v_cancelableyn	= dbox.getString("d_cancelableyn");
				v_refundyn		= dbox.getString("d_refundyn");
				v_canceldate	= dbox.getString("d_canceldate");
				v_paymethod		= dbox.getString("d_paymethod");
				v_acceptyn		= dbox.getString("d_acceptyn");
				v_chkfirst		= dbox.getString("d_chkfirst");
				v_chkfinal		= dbox.getString("d_chkfinal");
				v_rank			= dbox.getString("d_rank");
				v_rowspan		= dbox.getInt("d_rowspan");
				v_refunddate	= dbox.getString("d_refunddate");

				v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
				v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
				v_appdate		= FormatDate.getFormatDate(v_appdate,"yyyy.MM.dd");
				v_refundabledate= FormatDate.getFormatDate(v_refundabledate,"yyyy.MM.dd");
				v_canceldate	= FormatDate.getFormatDate(v_canceldate,"yyyy.MM.dd");
				v_refunddate	= FormatDate.getFormatDate(v_refunddate,"yyyy.MM.dd");

				if ("Y".equals(v_chkfirst) && "Y".equals(v_chkfinal)) {
					v_chkstatus = "승인";
				}
				if ("Y".equals(v_chkfirst) && "B".equals(v_chkfinal)) {
					v_chkstatus = "승인대기";
				}
				if ("Y".equals(v_chkfirst) && "N".equals(v_chkfinal)) {
					v_chkstatus = "반려";
				}

%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td><%= v_classname %></td>
				<td class="ta_l"><%= v_subjnm %></td>
				<td class="num"><%= v_edustart %> ~ <%= v_eduend %><br /> <%= v_appdate %></td>
				<td class="fs_s"><%= v_chkstatus %><br /><%= "Y".equals(v_refundyn) ? "환불완료" : "결제완료" %></td>
<%
				if (v_rowspan != 0) {
%>
				<td class="fs_s" rowspan="<%= v_rowspan %>">
<%
	                if ("N".equals(v_refundyn) && "Y".equals(v_refundableyn)) {
    	            	if (!"".equals(dbox.getString("d_canceldate"))) {
%>
                  <span class="fc_96">취소요청일</span><br/><span class="ff_t fs_s"><%= v_canceldate %></span>
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
                	} else {
                		if ("Y".equals(v_refundyn)) {
%>
				  <span class="fc_96">취소일</span><br/><span class="ff_t fs_s"><%= v_refunddate %></span>
<%
                		} else {
%>
						기간만료
<%
                		}
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
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->