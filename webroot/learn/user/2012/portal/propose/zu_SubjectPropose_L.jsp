<%
//**********************************************************
//  1. 제	  목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%!
	private void append(StringBuffer list, DataBox dbox) {
		list.append("<tr onmouseover=\"className='over'\" onmouseout=\"className=' '\">\n");
		list.append("	<td>" + dbox.get("d_classname") + "</td>\n");
		list.append("	<td class=\"ta_l\"><a href=\"\"><strong>" + dbox.get("d_subjnm") + "</strong></a></td>\n");
//교육기간/신청기간	:	list.append("	<td class=\"num\">" + dbox.getDate("d_edustart") + "~" + dbox.getDate("d_edustart") + "<br /><span class=\"sdata\">" + dbox.getDate("d_propstart") + "~" + dbox.getDate("d_propend") + "</span></td>\n");
		list.append("	<td class=\"num\">" + dbox.getDate("d_edustart") + "~" + dbox.getDate("d_eduend") + "</span></td>\n");
//할인가/수강료		list.append("	<td class=\"ta_r num\"><span class=\"td_lt\">" + dbox.getCommaInt("d_obiyong") + "원</span><br /><strong class=\"myclass\">" + dbox.getInt("d_biyong") + "원</strong></td>\n");
		list.append("	<td class=\"ta_r num\"><strong class=\"myclass\">" + dbox.getCommaInt("d_biyong") + "원</strong></td>\n");
		list.append("	<td><a href=\"javascript:fnSimpleDelete('" + dbox.get("d_subjnm") + "', '" + dbox.get("d_subj") + "','" + dbox.get("d_subjseq") + "', '" + dbox.get("d_year") + "');\" class=\"board_btn\"><span>삭제</span></a></td>\n");
		list.append("</tr>\n");
	}
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	//String type = box.get("type");
	String v_upperclass = box.get("p_upperclass");
	int sumPrice = 0;
	int countPrice = 0;
	int biyong = 0;
	if (box == null) box = RequestManager.getBox(request);
	DataBox resultbox = (DataBox) request.getAttribute("resultbox");
	List<DataBox> proposeResultList = (List<DataBox>)resultbox.getObject("proposeResultList");		 // 과정 리스트
	StringBuffer listY = new StringBuffer();
	StringBuffer listN = new StringBuffer();
	if (proposeResultList!=null) {
		for(DataBox dbox : proposeResultList) {
			if (dbox.get("d_chkfirst").equals("Y")) {
				biyong = dbox.getInt("d_biyong");
				sumPrice += biyong;
				if (biyong>0) countPrice++;
				append(listY, dbox);
			}
			else {
				append(listN, dbox);
			}
		}
	}
	
	// 할인율 셋팅
	int dis = resultbox.getInt("d_discount");
	int discountPrice = Integer.parseInt(Long.toString(sumPrice * dis/100));
	
	int usePoint = resultbox.getInt("d_getpoint") - (sumPrice - discountPrice) >= 0 ? (sumPrice - discountPrice) : resultbox.getInt("d_getpoint");
	%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script language="JavaScript">
<!--//
	//과정 내용보기
	function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq){
		document.form1.p_subj.value = subj;
		document.form1.p_subjnm.value = subjnm;
		document.form1.p_iscourseYn.value = courseyn;
		document.form1.p_upperclass.value = upperclass;
		document.form1.p_upperclassnm.value = upperclassnm;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value = subjseq;
		document.form1.p_process.value = 'SubjectPreviewPage';
		document.form1.p_rprocess.value = 'SubjectList';
		document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
		document.form1.target = "_self";
		document.form1.submit();
	}

	//수강신청
	function whenStartBill() {

		var lprice = document.form1.totalPrice.value;	// 최종 결제금액
		var tPrice = $("#sumPrice").val() * 1 - $("#discountPrice").val() * 1;	// 과정 결제 금액
		var maxPoint = <%= resultbox.getInt("d_getpoint") %> * 1;	// 보유 포인트

		var result_price;	// return 최종 결제 금액

		if( lprice > 0 && lprice < 1000) {	// 최종 결제금액이 천원 미만으로 들어오면 결제 거부

			if( maxPoint < tPrice ) {	// 보유 포인트가 과정 금액보다 적을때
				
				var tusept = tPrice - 1000;	// 사용가능한 최대 포인트
				var lpayprice = tPrice - tusept;
				
				$("#payPoint").val(tusept);
				$('#usePointTd').html("<input type='hidden' name='usePoint' value='"+tusept+"'/>- " + tusept + " 원");
				$('#totalPriceTd').html("<strong><em><input type='hidden' name='totalPrice' value='"+lpayprice+"'/>" + lpayprice + " 원</em></strong>");
				
			} else {	// 보유 포인트가 과정금액보다 많을때

				$("#payPoint").val(tPrice);
				$('#usePointTd').html("<input type='hidden' name='usePoint' value='"+tPrice+"'/>- " + tPrice + " 원");
				$('#totalPriceTd').html("<strong><em><input type='hidden' name='totalPrice' value='0'/>0 원</em></strong>");
				
			}

			alert("무료/1000원 이상 결제만 지원합니다.");
			return;
			
		}
		
		document.form1.action = "/inicis/plugin_check_new.jsp";
		document.form1.submit();
		document.form1.target = "_self";
	}

	function show(x, y) {
		$('#list'+x).hide();
		$('#list'+y).fadeIn(700);
	}

	function whenPointChange(pointVar) {
		
		if(!numeric_chk(pointVar)) {
			$("#payPoint").val(<%= usePoint %>);
		}
		
		var point = pointVar.value;		// 입력된 포인트 값
		var maxPoint = <%= resultbox.getInt("d_getpoint") %> * 1;	// 보유 포인트
		
		if (point > maxPoint) {
			point = maxPoint;
			$("#payPoint").val(point);
		}
		
		var tPrice = $("#sumPrice").val() * 1 - $("#discountPrice").val() * 1;		// 과정 결제 금액
		
		if ( tPrice < point ) {
			point = tPrice;
			$("#payPoint").val(point);
		}
		
		$('#usePointTd').html("<input type='hidden' name='usePoint' value='"+point+"'/>- " + point + " 원");
		
		var tPrice = $("#sumPrice").val()*1 - $("#discountPrice").val() * 1 - point * 1;
		
		$('#totalPriceTd').html("<strong><em><input type='hidden' name='totalPrice' value='"+tPrice+"'/>" + tPrice + " 원</em></strong>");
	}
	
	//관심버튼 클릭
	function fnSimpleDelete(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) {
		if (confirm(param1 + "과정을 정말 삭제하시겠습니까?")) {
				$.post("/servlet/controller.common.AjaxServlet"
						, {	  sqlNum:"propose.cancel", rerurnFunction:"simpleResult"
							, p_userid:"<%= box.getSession("userid")%>"
							, param1:param1
							, param2:param2
							, param3:param3
							, param4:param4
							, param5:param5
							, param6:param6
							, param7:param7
							, param8:param8
							, param9:param9
							, param10:param10
						}
						, function(data) {
							$("#ajaxDiv").html(data);
						});
		}
	}
	
	function simpleResult(data) {
		if (data.result=='true') {
			alert("삭제하였습니다.");
			menuForward('3', '18');
		} 
		else alert("삭제가 실패하였습니다.\n"+data.result);
	}
	
	$(document).ready(function(){
		<%--if(<%= proposeResultList==null || proposeResultList.size()==0 %>) {--%>
			<%--$("#startBillBtn").hide();--%>
			<%--alert("수강신청된 과정이 없습니다. 한개 이상의  과정을 신청하여 주십시요.");--%>
			<%--menuMainForward('3','/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');--%>
		<%--}--%>
	});

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료-->
<!-- form 시작 -->
<form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
	<input type="hidden" name="p_upperclass"	value="<%=v_upperclass%>"/>
	<input type="hidden" name="p_process"	value="<%=box.get("p_process")%>"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	<input type="hidden" name="p_action"	value="<%=p_action%>"/>
	<input type="hidden" name="p_dis"	value="<%=dis%>"/>
	
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
        <td><img src="/images/portal/homepage_renewal/myclass/stitle_06.gif" alt="장바구니" /></td>
        <td class="h_road">Home &gt; 나의 강의실  &gt; <strong>장바구니</strong></td>
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
    <td><img src="/images/portal/homepage_renewal/myclass/ton_01.jpg" name="Image49" id="Image49" /></td>
    <td><img src="/images/portal/homepage_renewal/myclass/toff_02.jpg" name="Image50"  id="Image50" /></td>
    <td class="h_road" ><img src="/images/portal/homepage_renewal/myclass/toff_03.jpg" name="Image51" id="Image51" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="org">* 결제가 완료되야 수강신청이 완료됩니다.</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="org">* 오프라인 과정은 운영진 1차 승인 후, “수강료결제 조회/납부”에서 결제를 진행하여 주시기 바랍니다.</td>
        <td class="h_road">
        	<a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage">
        		<img src="/images/portal/homepage_renewal/myclass/btn_offline_ok.jpg" />
        	</a>
        </td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/myclass/sstitle_02.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">분류</td>
        <td class="tit_table"> 과정명 </td>
        <td class="tit_table">교육비</td>
        <td class="tit_table">교육기간</td>
        <td class="tit_table_right">삭제</td>
      </tr>
      <%
	if (proposeResultList!=null) {
		for(DataBox dbox : proposeResultList) { %>
      <tr>
        <td class="ct_tit_table"><%=dbox.get("d_classname") %></td>
        <td class="ct_tit_table_left"><%=dbox.get("d_subjnm") %></td>
        <td class="ct_tit_table"><strong><%=dbox.get("d_biyong") %></strong></td>
        <td class="ct_tit_table"><%=dbox.getDate("d_edustart") %>~<%=dbox.getDate("d_eduend") %></td>
        <td class="ct_tit_table_right"><a href="javascript:fnSimpleDelete('<%=dbox.get("d_subjnm")%>','<%=dbox.get("d_subj")%>','<%=dbox.get("d_subjseq")%>','<%=dbox.get("d_year")%>');"><img src="/images/portal/homepage_renewal/myclass/btn_sdel.jpg" alt="삭제" /></a></td>
      </tr>
      <%} }%>
      <tr>
        <td height="1" colspan="5" bgcolor="#C6C6C6"></td>
        </tr>
      <tr>
        <td height="30" colspan="2"><div align="center"><strong>총 주문금액</strong></div></td>
        <td colspan="3"><div align="center"><span class="rtxt"><strong><input type='hidden' name='totalPrice' value='<%= sumPrice %>'/><%= StringUtil.setString(sumPrice) %> 원</strong></span></div></td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      
      <tr>
        <td height="1" colspan="3" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="3" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>
        	<div align="center">
        		<a href="javascript:whenStartBill();" class="btn_account">
        			<img src="/images/portal/homepage_renewal/myclass/btn_pok.jpg" alt="결제하기"  />
        		</a>
        	</div></td>
      </tr>
    </table></td>
    <td width="20">&nbsp;</td>
   
  </tr>
</table>
			
			<%}else{ %>
	
	<h2><img src="/images/portal/myclass/h2_tit3.gif" alt="장바구니" class="fl_l" /><p class="category">Home &gt; 나의강의실 &gt; <strong>장바구니</strong></p></h2>
	<ul class="basket_tab">
		<li><img src="/images/portal/myclass/basket_tab1_on.gif" alt="1.신청목록 확인" /></li>
		<li><img src="/images/portal/myclass/basket_tab2.gif" alt="2.결제정보 확인" /></li>
		<li><img src="/images/portal/myclass/basket_tab3.gif" alt="3.결제완료" /></li>
	</ul>
    <br>
    ※ 결제가 완료되야 수강신청이 완료됩니다.<br>
    <span style="color:red;">※  <strong>오프라인 과정</strong>은 운영진 1차 승인 후, <strong>“수강료결제 조회/납부”</strong>에서 결제를 진행하여 주시기 바랍니다.</span>
    <a href="/servlet/controller.study.MyClassBillServlet?p_process=MyOffClassBillListPage"><span style="color:red;vertical-align:middle;"><img src="/images/portal/btn/btn_basket_buy.gif" alt="결재하기 바로가기" style="CURSOR:POINTER;"/></span></a>
<!-- 	<li>수강 신청 시 수강료 할인은 2과목 <%//=resultbox.getInt("d_discount2")%>%~ 5과목 <%//=resultbox.getInt("d_discount5")%>%시스템 자동적용 됩니다.</li>  -->
	<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket1.gif" alt="신청과정" /></h3>

<div id="listY">
<!--
	****************
	1차 승인을 사용하게 되어서 승인시와 미승인시 구분이 필요하면 사용한다.
	****************
	<ul class="tabwrap mg_t35">
		<li><a href="javascript:show('N', 'Y');" class="tab_myclass tab_myclass_on"><span>승인과정</span></a></li>
		<li><a href="javascript:show('Y', 'N');" class="tab_myclass"><span>승인대기과정</span></a></li>
	</ul>
	****************
	끝
	****************
-->
	<table class="board_list">
	<colgroup><col width="110px" /><col width="270px" /><col width="150px" /><col width="90px" /><col width="70px" /></colgroup>
	<thead>
	<tr class="myclass">
		<th><p>분류</p></th>
		<th><p>과정명</p></th>
		<th><p>교육기간</p></th>
		<th><p>수강료</p></th>
		<th class="end"><p>삭제</p></th>
	</tr>
	</thead>
	<tbody>
		<%= listY.toString() %>
	</tbody>
	</table>
</div>
<!--
	****************
	1차 승인을 사용하게 되어서 승인시와 미승인시 구분이 필요하면 사용한다.
	****************
<div id="listN" style="display:none">
	<ul class="tabwrap mg_t35">
		<li><a href="javascript:show('N', 'Y');" class="tab_myclass"><span>승인과정</span></a></li>
		<li><a href="javascript:show('Y', 'N');" class="tab_myclass tab_myclass_on"><span>승인대기과정</span></a></li>
	</ul>
	<table class="board_list">
	<colgroup><col width="110px" /><col width="270px" /><col width="150px" /><col width="90px" /><col width="70px" /></colgroup>
	<thead>
	<tr class="myclass">
		<th><p>분류</p></th>
		<th><p>과정명</p></th>
		<th><p>교육기간</p></th>
		<th><p>수강료</p></th>
		<th class="end"><p>삭제</p></th>
	</tr>
	</thead>
	<tbody>
		<%= listN.toString() %>
	</tbody>
	</table>
</div>
	****************
	끝
	****************
  -->
<%	if (resultbox.getInt("d_getpoint")!=0) {%>
	<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket2.gif" alt="포인트사용" /></h3>
	<div class="basket_point">
		<p class="point_info">현재 회원님이 사용하실 수 있는 포인트는 <strong><%= resultbox.getCommaInt("d_getpoint") %> 포인트</strong> 입니다.<br/>
		사용하실 포인트를 입력하여 주십시오.</p>
<!-- 		<p class="point_input"><input type="text" style="width:110px;" value="<%= StringUtil.setString(usePoint) %>" /><a href="" class="btn_wh"><span>포인트사용</span></a></p> -->
		<p class="point_input"><span>사용포인트</span> <input type="text" name="payPoint" id="payPoint" style="width:110px;" value="<%= usePoint %>" onkeyup="whenPointChange(this);"/></p>
	</div>
<%	}	else { %>
<input type="hidden" name="payPoint" id="payPoint" style="width:110px;" value="<%= usePoint %>" onkeyup="whenPointChange(this);"/>
<%	}	%>
	<h3 class=" mg_t30"><img src="/images/portal/myclass/stit_basket3.gif" alt="총 결제금액" /></h3>
	<table class="box_list">
	<colgroup><col width="175px" /><col width="175px" /><col width="175px" /><col width="175px" /></colgroup>
	<thead>
	<tr>
		<th>수강료</th>
		<th>할인적용</th>
		<th>포인트사용</th>
		<th>실결제 금액</th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td><input type='hidden' name='sumPrice' id='sumPrice' value='<%= sumPrice %>'/><%= StringUtil.setString(sumPrice) %> 원</td>
		<td><input type='hidden' name='discountPrice' id='discountPrice' value='<%= discountPrice %>'/>- <%= StringUtil.setString(discountPrice) %> 원</td>
		<td id="usePointTd"><input type='hidden' name='usePoint' value='<%= usePoint %>'/>- <%= StringUtil.setString(usePoint) %> 원</td>
		<td id="totalPriceTd"><strong><em><input type='hidden' name='totalPrice' value='<%= sumPrice-discountPrice-usePoint %>'/><%= StringUtil.setString(sumPrice-discountPrice-usePoint) %></em> 원</strong></td>
	</tr>
	</tbody>
	</table>
	
	<p id="startBillBtn" class="board_btn ag_c mg_t20">
		<a href="javascript:whenStartBill();" class="btn_account"><span>결제하기</span></a>
	</p>
	
	<%} %>
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
