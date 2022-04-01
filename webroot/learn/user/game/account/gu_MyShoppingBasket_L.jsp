<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: gu_SubjectPreviewOn.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process      = box.getString("p_process");

    String  v_tem_grcode   = box.getSession("tem_grcode");

    String  v_isevent   = box.getSession("isevent");	//과정당 할인회원 구분

    ArrayList list1 = (ArrayList)request.getAttribute("SubjList");
    ArrayList list2 = (ArrayList)request.getAttribute("PackageList");
    ArrayList list3 = (ArrayList)request.getAttribute("BookList");


	// 이벤트 부분 start
	String  s_userid = box.getSession("userid");
	String currentDate = new java.text.SimpleDateFormat("yyyyMMdd", new java.util.Locale("en", "US")).format(new java.util.Date());
	int iCurrentDate = Integer.parseInt(currentDate);
	// 이벤트 부분 end

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--
function go_Inicis(gubun) {

    farwindow = window.open("", "winInicis", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
    document.fbill.p_gubun.value = gubun;
    document.fbill.target = "winInicis";
    document.fbill.submit();

    farwindow.window.focus();
}

function go_free(gubun) {
	if (gubun== 'N') {
		document.form1.p_gubun.value = gubun;
		document.form1.submit();
	} else if (gubun== 'P') {
		document.form2.p_gubun.value = gubun;
		document.form2.submit();
	}
}

function delete_basket(v_gubun,v_subj,v_year,v_subjseq) {
	document.ff.p_gubun.value 	= v_gubun;
	document.ff.p_subj.value 	= v_subj;
	document.ff.p_year.value 	= v_year;
	document.ff.p_subjseq.value = v_subjseq;

	if (confirm("장바구니에서 삭제하시겠습니까?")) {
		document.ff.submit();
	}
}

function whenChangeBookUnit(v_id,v_seq,v_price) {
	v_unit =  eval("document.form3.p_unit"+v_id);

	var v_pattern = /[^(0-9)]/;
	if (v_pattern.test(v_unit.value)) {
		alert("수량은 숫자만 가능합니다.");
		return;
	}

	if (v_unit.value=="0") {
		alert("0은 입력할 수 없습니다.");
		return;
	}

	document.frmbupdate.p_unit.value = v_unit.value;
	document.frmbupdate.p_seq.value  = v_seq;
	document.frmbupdate.p_price.value = v_price;
	document.frmbupdate.submit();

}
//-->
</SCRIPT>



	<!-- title -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td width="720" height="35" align="right"  background="/images/user/game/account/tit_shoplist.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
		  HOME > 수강신청 > 장바구니</td>
	  </tr>
	  <tr>
		<td height="20"></td>
	  </tr>
	</table>


<form name="frmbupdate" method="post" action="/servlet/controller.account.AccountManagerServlet">
    <input type="hidden" name="p_process" value="UpdateBookUnit">
    <input type="hidden" name="p_seq">
    <input type="hidden" name="p_unit">
    <input type="hidden" name="p_price">
</form>

<form name="form1" method="post" action="/servlet/controller.account.AccountManagerServlet">
    <input type="hidden" name="p_process" value="InsertProposeFree">
    <input type="hidden" name="p_gubun">

	<table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="5"><img src="/images/user/game/account/t_s01.gif" width="192" height="31"></td>
          </tr>
        </table>
	<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
	  <tr  class="lcolor">
		<td height="3" colspan="8" class="linecolor_app"></td>
	  </tr>
		  <tr>
			<td class="tbl_gtit2">NO</td>
			<td class="tbl_gtit2">과정명</td>
			<td class="tbl_gtit2">차수</td>
			<td class="tbl_gtit2">교육기간</td>
			<td class="tbl_gtit2">수강료</td>
			<td class="tbl_gtit2">삭제</td>
		  </tr>
	  </tr>
	  <% int v_sumprice = 0;
	  	 int v_dcrate = 0;
	  	 int v_billprice = 0;

	  	 int v_pricecnt = 0;

	  	 for (int i=0; i<list1.size(); i++) {
	  		DataBox dbox = (DataBox)list1.get(i);

	  		String v_subjnm   = dbox.getString("d_subjnm");
	  		String v_subj	  = dbox.getString("d_subj");
	  		String v_year	  = dbox.getString("d_year");
	  		String v_subjseq  = dbox.getString("d_subjseq");
	  		String v_edustart = dbox.getString("d_edustart");
	  		String v_eduend   = dbox.getString("d_eduend");
	  		int    v_price	  = dbox.getInt("d_price");

	  		if (v_price>0) v_pricecnt++;

			if(v_tem_grcode.equals("N000002") && v_isevent.equals("Y") && (s_userid.equals("gameacademy") || (iCurrentDate >= 20090605 && iCurrentDate <= 20090731))) {
	  			v_price = 10000;
	  		}

	  		v_sumprice += v_price;
	  %>

	  <tr>
		<td class="tbl_grc"><%=i+1%></td>
		<td class="tbl_gleft"><%=v_subjnm%></td> <!-- <a href="javascript:whenSubjInfo('<%=v_subj%>','ALL')"> -->
		<td class="tbl_grc"><%=v_subjseq%></td>
		<td class="tbl_grc"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")%> ~ <%=FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
		<td class="tbl_grc"><%=new java.text.DecimalFormat("##,###,##0").format(v_price)%>원</td>
		<td class="tbl_grc"><a href="javascript:delete_basket('N','<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>');"><img src="/images/user/button/btn_del.gif" width="45" height="21" border="0"></a></td>
	  </tr>
	<% } %>

	<% if (list1.size()==0) { %>
	<tr>
	  <td colspan=6 align=center>등록된 내용이 없습니다.</td>
	</tr>
	<% } %>
	<tr>
	  <td colspan=6 height=30></td>
	</tr>
	<tr>
	  <td colspan=5 class="tbl_gleft">합계</td>
	  <td class="tbl_grc"><%=new java.text.DecimalFormat("##,###,##0").format(v_sumprice)%>원</td>
	</tr>

	<tr>
	  <td colspan=5 class="tbl_gleft">할인율</td>
	  <td class="tbl_grc">
<%
	if(v_tem_grcode.equals("N000002") && v_isevent.equals("Y") && (s_userid.equals("gameacademy") || (iCurrentDate >= 20090605 && iCurrentDate <= 20090731))) {
		out.print("0%"); v_dcrate = 0;
	} else {
		/*	50% 할인 이벤트
	   	 if (v_pricecnt==1)      { out.print("0%"); v_dcrate = 0; }
	  	 else if (v_pricecnt==2) { out.print("20%"); v_dcrate = 20; }
	  	 else if (v_pricecnt==3) { out.print("30%"); v_dcrate = 30; }
	  	 else if (v_pricecnt==4) { out.print("40%"); v_dcrate = 40; }
	  	 else if (v_pricecnt==5) { out.print("50%"); v_dcrate = 50; }
	  	 else { out.print("0%"); v_dcrate = 0; }
	  	 */
	  	 out.print("0%"); v_dcrate = 0;
 	}
 %>
	  </td>
	</tr>
	<tr>
	  <td colspan=5 class="tbl_gleft">결제금액</td>
	  <td class="tbl_grc">
	  <%v_billprice = (v_sumprice - (v_sumprice * v_dcrate / 100));%>
	  <%=new java.text.DecimalFormat("##,###,##0").format(v_billprice)%>원
	  </td>
	</tr>
		<input type="hidden" name="p_billprice" value="<%=v_billprice%>">
	  </form>
	</table>

	<% if (list1.size()!=0) { %>
	<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		<tr>
			<td align=right>
			<% if (v_billprice == 0) { %>
				<a href="javascript:go_free('N');"><img src="/images/user/button/btn_apply.gif" border="0"></a>
			<% } else { %>
				<a href="javascript:go_Inicis('N');"><img src="/images/user/game/account/b_progress.gif" width="67" height="19" border="0"></a>
			<% } %>
			</td>
		</tr>
	</table>
	<% } %>

	<!-- 일반과정 끝 -->

	<br>

	<!-- 전문가과정 시작 -->
	<form name="form2" method="post" action="/servlet/controller.account.AccountManagerServlet">
    	<input type="hidden" name="p_process" value="InsertProposeFree">
    	<input type="hidden" name="p_gubun">

        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="5"><img src="/images/user/game/account/t_s02.gif" width="192" height="31"></td>
          </tr>
        </table>
        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="11" rowspan="4">&nbsp;</td>
            <td height="5" colspan="3"></td>
            <td width="20" rowspan="4">&nbsp;</td>
          </tr>
          <tr>
            <td width="6">&nbsp;</td>
            <td>

			<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
			  <tr  class="lcolor">
				<td height="3" colspan="8" class="linecolor_app"></td>
			  </tr>
				  <tr>
					<td class="tbl_gtit2">NO</td>
					<td class="tbl_gtit2" colspan=2>과정명</td>
					<td class="tbl_gtit2">차수</td>
					<td class="tbl_gtit2">교육기간</td>
					<td class="tbl_gtit2">수강료</td>
					<td class="tbl_gtit2">삭제</td>
				  </tr>
			  </tr>
	  <% int v_psumprice = 0;
		 String v_tmp_course   = "";
		 String v_course       = "";

	  	 for (int i=0; i<list2.size(); i++) {
	  		DataBox dbox = (DataBox)list2.get(i);

	  		String v_scsubjnm   = dbox.getString("d_scsubjnm");
	  		String v_scsubj	  = dbox.getString("d_scsubj");
	  		String v_scyear	  = dbox.getString("d_scyear");
	  		String v_scsubjseq  = dbox.getString("d_scsubjseq");
	  		int    v_scbiyong  = dbox.getInt("d_scbiyong");
	  		int    v_subjcnt  = dbox.getInt("d_subjcnt");

	  		v_course        = dbox.getString("d_course");

	  		String v_subjnm   = dbox.getString("d_subjnm");
	  		String v_subj	  = dbox.getString("d_subj");
	  		String v_year	  = dbox.getString("d_year");
	  		String v_subjseq  = dbox.getString("d_subjseq");
	  		String v_edustart = dbox.getString("d_edustart");
	  		String v_eduend   = dbox.getString("d_eduend");
	  		int    v_price	  = dbox.getInt("d_price");



	  		if(!v_tmp_course.equals(v_course)) {
                v_tmp_course = v_course;
                v_psumprice += v_scbiyong;
                %>
                <tr >
                    <td class="tbl_grc" rowspan=<%=v_subjcnt%>><%=i+1%></td>
                    <td class="tbl_grc" rowspan=<%=v_subjcnt%>><%=v_scsubjnm%></td>
                    <td class="tbl_bleft" ><%=v_subjnm%></td>
                    <td class="tbl_grc"><%=v_subjseq%></td>
                    <td class="tbl_grc"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")%> ~ <%=FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
                    <td class="tbl_grc" rowspan=<%=v_subjcnt%>><%=new java.text.DecimalFormat("##,###,##0").format(v_scbiyong)%></td>
                    <td class="tbl_grc" rowspan=<%=v_subjcnt%>><a href="javascript:delete_basket('P','<%=v_scsubj%>','<%=v_scyear%>','<%=v_scsubjseq%>');"><img src="/images/user/button/btn_del.gif" width="45" height="21" border="0"></a></td>
                </tr>
	  	<% } else { %>
                <tr >
                    <td class="tbl_bleft" ><%=v_subjnm%></td>
                    <td class="tbl_grc"><%=v_subjseq%></td>
                    <td class="tbl_grc"><%=FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")%> ~ <%=FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")%></td>
                </tr>
	  	<% }  %>
      <% } %>

		  <% if (list2.size()==0) { %>
			<tr>
			  <td colspan=7 align=center>등록된 내용이 없습니다.</td>
			</tr>
		  <% } %>

              </table>
            </td>
            <td width="7">&nbsp;</td>
          </tr>
          <tr>
            <td height="2" colspan="7" class="linecolor_board5"></td>
          </tr>
          <tr>
            <td height="2" colspan="7"></td>
          </tr>
        </table>

		<% if (list2.size()!=0) { %>
		<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
			<tr>
				<td align=right>
				<% if (v_psumprice == 0) { %>
					<a href="javascript:go_free('P');"><img src="/images/user/button/btn_apply.gif" border="0"></a>
				<% } else { %>
					<a href="javascript:go_Inicis('P');"><img src="/images/user/game/account/b_progress.gif" width="67" height="19" border="0"></a>
				<% } %>
				</td>
			</tr>
		</table>
		<% } %>
		<input type="hidden" name="p_billprice" value="<%=v_psumprice%>">
	</form>

	<br>

	<!-- 전문가과정 끝 -->

	<!-- 도서 시작 -->
	<form name="form3" method="post" action="/servlet/controller.account.AccountManagerServlet">
    	<input type="hidden" name="p_process" value="InsertProposeFree">
    	<input type="hidden" name="p_gubun">

        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="5"><img src="/images/user/game/account/t_s03.gif" width="192" height="31"></td>
          </tr>
        </table>
        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="6">&nbsp;</td>
            <td>

			<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
			  <tr  class="lcolor">
				<td height="3" colspan="6" class="linecolor_app"></td>
			  </tr>
				  <tr>
					<td class="tbl_gtit2">NO</td>
					<td class="tbl_gtit2">도서명</td>
					<td class="tbl_gtit2">수량</td>
					<td class="tbl_gtit2">가격</td>
					<td class="tbl_gtit2">합계</td>
					<td class="tbl_gtit2">삭제</td>
				  </tr>
			  </tr>
			  <%
			  int v_booksumprice = 0;
			  for (int i=0; i<list3.size(); i++) {
		  		DataBox dbox = (DataBox)list3.get(i);

		  		String v_bookname = dbox.getString("d_bookname");
		  		String v_bookcode = dbox.getString("d_bookcode");
		  		int    v_seq	  = dbox.getInt("d_seq");
		  		int    v_price    = dbox.getInt("d_price");
		  		int    v_unit     = dbox.getInt("d_unit");
		  		int    v_amount   = dbox.getInt("d_amount");
		  		v_booksumprice    = v_booksumprice + v_amount;
	  		%>
                <tr>
                    <td width="46"  class="tbl_grc" ><%=i+1%></td>
                    <td width="256" class="tbl_grc" ><%=v_bookname%></td>
                    <td width="101" class="tbl_grc" ><input type="text" name="p_unit<%=i%>" value="<%=v_unit%>" size="3" maxvalue="3">
                    <a href="javascript:whenChangeBookUnit('<%=i%>','<%=v_seq%>','<%=v_price%>');">변경하기</a></td>
                    <td width="120" class="tbl_grc" ><%=new java.text.DecimalFormat("##,###,##0").format(v_price)%></td>
                    <td width="120" class="tbl_grc" ><%=new java.text.DecimalFormat("##,###,##0").format(v_amount)%></td>
                    <td width="100"  class="tbl_grc"><a href="javascript:delete_basket('B','<%=v_bookcode%>','','');"><img src="/images/user/button/btn_del.gif" width="45" height="21" border="0"></a></td>
                </tr>
                <% } %>
                <% if (list3.size()!=0) { %>
                <tr>
                    <td class="tbl_grc" colspan=4>합계</td>
                    <td class="tbl_grc" ><%=new java.text.DecimalFormat("##,###,##0").format(v_booksumprice)%></td>
                    <td width="100"  class="tbl_grc">&nbsp;</td>
                </tr>
                <% } %>
                <% if (list3.size()==0) { %>
                <tr>
                    <td class="tbl_grc" align=center colspan="6">등록된 정보가 없습니다.</td>
                </tr>
                <% } %>
              </table>
            </td>
          </tr>
        </table>
	</form>
	<% if (list3.size()>0) { %>
	<!-- ########## 5/1일 부터 적용
	<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
		<tr>
			<td align=right> <a href="javascript:go_Inicis('B');"><img src="/images/user/game/account/b_progress.gif" width="67" height="19" border="0"></a></td>
		</tr>
	</table>
	-->
	<% } %>
	<!-- 도서 끝 -->


	<form name="fbill" method="post" action="/inicis/plugin_check.jsp">
		<input type="hidden" name="p_gubun">
	</form>

	<form name="ff" method="post" action="/servlet/controller.account.AccountManagerServlet">
		<input type="hidden" name="p_process" value="DeleteBasket">
		<input type="hidden" name="p_gubun">
		<input type="hidden" name="p_subj">
		<input type="hidden" name="p_year">
		<input type="hidden" name="p_subjseq">
		<input type="hidden" name="p_course">
		<input type="hidden" name="p_courseyear">
		<input type="hidden" name="p_courseseq">
		<input type="hidden" name="p_bookcode">
	</form>

        <br>
        <br>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->