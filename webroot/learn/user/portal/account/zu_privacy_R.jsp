<%
//**********************************************************
//  1. 제	  목: SUBJECT INSERT
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개	  요: 과정신청
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
	DataBox dbox = (DataBox)request.getAttribute("resultbox");
	List<DataBox> schoolList = (List<DataBox>) dbox.getObject("schoolList");
	List<DataBox> licenseList = (List<DataBox>) dbox.getObject("licenseList");
	List<DataBox> careerList = (List<DataBox>) dbox.getObject("careerList");
	List<DataBox> trainingINFOList = (List<DataBox>) dbox.getObject("trainingINFOList");
	String needinput = dbox.get("d_needinput");
	String[]hp = new String[3];
	int index = 0;
	StringTokenizer st = new StringTokenizer(dbox.get("d_handphone"), "-");
	while(st.hasMoreTokens()) {
		hp[index++] = st.nextToken();
	}

	String type = box.get("type");
	if (box == null) box = RequestManager.getBox(request);
	int i = 0;
	char[]token = needinput.toCharArray();
%>
<%@ include file="/learn/user/portal/include/topNoLeftMenu.jsp" %>

		<dl id="snb">
			<dt><img src="/images/portal/utility/snb_tit8.gif" alt="개인정보보변경" /></dt>
			<dd><a href="javascript:topMemberInfoModify();"><img src="/images/portal/utility/snb_stit1_on.gif" alt="개인정보변경" /></a></dd>
			<dd><a href="javascript:ssoMemberOut();"><img src="/images/portal/utility/snb_stit2_off.gif" alt="회원탈퇴" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></dd>
		</dl>
<!-- content 시작 -->
        <div id="content" class="<%=box.getString("contentClass") %>">

<script language="JavaScript">
<!--//
	//삭제
	function whenSimpleDelete(deleteType, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) {
		if(!confirm("정말 삭제하시겠습니까?")) return;
		$.post("/servlet/controller.common.AjaxServlet"
			, {	  sqlNum:"propose.delete."+deleteType
				, rerurnFunction:"resultSimpleDelete"
				, p_subj:"<%=box.get("p_subj")%>"
				, p_subjseq:"<%=box.get("p_subjseq")%>"
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
				if(data.indexOf('true') != -1)
					$('#'+deleteType+param1).hide();
				$("#ajaxDiv").html(data);
			});
	}
	function resultSimpleDelete(data) {
		if (data.result=='true') {
			 alert("삭제했습니다.");
		}
		else alert("삭제가 실패하였습니다.\n"+data.result);
		
	}
	//저장
	function whenSimpleSave(saveType, param1, param2, param3, param4, param5, param6, param7, param8, param9, param10) {
		if(saveType=='study') {
			if($('#p_schoolname').val() == '') {
				alert("학교명을 입력하여 주십시요");
				return;
			}
			if($('#p_place').val() == '') {
				alert("소재지를 입력하여 주십시요");
				return;
			}
			if($('#p_major').val() == '') {
				alert("학과/전공을 입력하여 주십시요");
				return;
			}
			if($('#p_sstart').val() == '' || $('#p_send').val() == '') {
				alert("재학기간을 입력하여 주십시요");
				return;
			}
		}
		else if(saveType=='license') {
			if($('#p_licensename').val() == '') {
				alert("자격증명을 입력하여 주십시요");
				return;
			}
			if($('#p_getdate').val() == '') {
				alert("취득일을 입력하여 주십시요");
				return;
			}
			if($('#p_publish').val() == '') {
				alert("발행처를 입력하여 주십시요");
				return;
			}
		}
		else if(saveType=='workinfo') {
			var jik = $("#p_jikup").val() + $("#p_jikjong").val();
			if(jik=="") {
				alert("종사직업을 선택하여 주십시요");
				return;
			}
			if($('#p_officename').val() == '') {
				alert("회사명을 입력하여 주십시요");
				return;
			}
			if($('#p_workstart').val() == '') {
				alert("재직기간을 시작일을 입력하여 주십시요");
				return;
			}
			if($("#p_workstatus option:selected").text() == "퇴사" || $("#p_workstatus option:selected").text() == "휴직") {
				if ($('#p_workend').val() == '') {
					alert("재직기간 종료일을 입력하여 주십시요.");
					return;
				}
			}
		}

		$.post("/servlet/controller.common.AjaxServlet"
			, {	  sqlNum:"propose.insert."+saveType
				, rerurnFunction:"resultSimpleSave"
				, p_subj:"<%=box.get("p_subj")%>"
				, p_subjseq:"<%=box.get("p_subjseq")%>"
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
				if(data.indexOf('true') != -1)
					 whenSimpleLoad(saveType);
				$("#ajaxDiv").html(data);
				if(saveType=='workinfo') {
					$('#p_officename').val('');
					$('#p_workstart').val('');
					$('#p_workend').val('');
				}
			});
	}
	function resultSimpleSave(data) {
		if (data.result=='true') {
			 alert("등록했습니다.");
		}
		else alert("등록이 실패하였습니다.\n"+data.result);
		
	}
	//출석상태조회
	function whenSimpleLoad(saveType) {
		$.post("/servlet/controller.common.TagServlet"
				, {	  sqlNum:"propose.select."+saveType,rerurnFunction:"readData"+saveType, type:6
					, p_subj:"<%=box.get("p_subj")%>"
					, p_subjseq:"<%=box.get("p_subjseq")%>"
					, p_userid:"<%= box.getSession("userid")%>"
				}
				, function(data) {
					$("#ajaxDiv").html(data);
				});
	}
	function readDatastudy(data) {
		 $("#schoolList").html( $("#schoolList").html()+
				 "<tr><td>"+data.d_schoolname+"</td><td>"
				 +data.d_statusnm+"</td><td>"
				 +data.d_send+"</td><td>"
				 +data.d_major+"</td><td>"
				 +data.d_place+"</td><td><a href=\"javascript:whenSimpleDelete('study', "
				 +data.d_seq+");\"><img src=\"/images/admin/portal/stat_0.gif\"/></a></td></tr>");
		 $('#p_schoolname').val('');
		 $('#p_place').val('');
		 $('#p_major').val('');
		 $('#p_sstart').val('');
		 $('#p_send').val('');
	}
	function readDatalicense(data) {
		 $("#licenseList").html( $("#licenseList").html()+
				 "<tr><td>"+data.d_licensename+"</td><td>"
				 +data.d_getdate+"</td><td>"
				 +data.d_publish+"</td><td><a href=\"javascript:whenSimpleDelete('license', "
				 +data.d_seq+");\"><img src=\"/images/admin/portal/stat_0.gif\"/></a></td></tr>");
		 $('#p_licensename').val('');
		 $('#p_getdate').val('');
		 $('#p_publish').val('');
	}
	function readDataworkinfo(data) {
		 $("#workinfoList").html( $("#workinfoList").html()+"<tr><td>"
				 +data.d_jikup+"</td><td>"
				 +data.d_jikjong+"</td><td>"
				 +data.d_officename+"</td><td>"
				 +data.d_position+"</td><td>"
				 +data.d_workstart+" ~ "
				 +data.d_workend+"</td><td>"
				 +data.d_workstatus+"</td><td><a href=\"javascript:whenSimpleDelete('workinfo', "+data.d_seq+");\"><img src=\"/images/admin/portal/stat_0.gif\"/></a></td></tr>");
	}
//-->
$(document).ready(function(){
	$("#p_militarystart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_militaryend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_sstart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_send").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_getdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_workstart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_workend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_jikup").show();$('#p_jikup').hide();
	selectMilitary();
});
</script>

<!-- 스크립트영역-->
<div id="ajaxDiv"></div>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="form1" action="/servlet/controller.homepage.MemberInfoServlet" method="post">
	<input type="hidden" name="p_process"	value="memberUpdateNew"/>
	<input type="hidden" name="p_rprocess"	value=""/>
			<h2><img src="/images/portal/utility/h2_tit.gif" alt="개인정보변경" class="fl_l" /><p class="category">Home &gt; <strong>개인정보변경</strong></p></h2>

			<h3 class="mg_tn"><img src="/images/portal/common/stit_baseinfo.gif" alt="기본정보" /></h3>
			<table class="view_bl">
			<colgroup><col width="18%" /><col width="30%" /><col width="22%" /><col width="30%" /></colgroup>
            <tr>
				<th>사진</th>
				<td class="end" colspan="3">
                    <% if(!dbox.getFromDBTypeString("img_path").equals("")) {%>
                    <img src="<%=dbox.getFromDBTypeString("img_path").replace("\\","/")  %>" alt="">
                    <%}%>
                    </td>
			</tr>
			<tr>
				<th>ID</th>
				<td><%=dbox.getFromDBTypeString("d_userid")  %></td>
				<th>비밀번호</th>
				<td class="end">*******</td>
			</tr>
			<tr>
				<th>성명</th>
				<td><%= dbox.get("d_name") %></td>
				<th>성명(영문)</th>
				<td class="end"><%=dbox.getFromDBTypeString("eng_name")  %></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><%= dbox.get("d_email") %></td>
				<th>주민번호</th>
				<td class="end"><%=dbox.getFromDBTypeString("RESNO").substring(0, 5)  %>-********</td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td colspan="3" class="end"><%=dbox.getFromDBTypeString("post1")  %>-<%=dbox.getFromDBTypeString("post2")  %></td>
			</tr>
			<tr>
				<th>주소</th>
				<td colspan="3" class="end"><%=dbox.getFromDBTypeString("addr")  %></td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td colspan="3" class="end"><%=dbox.getFromDBTypeString("addr2")  %></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><%=dbox.getFromDBTypeString("hometel")  %></td>
				<th>휴대전화</th>
				<td class="end"><%= dbox.get("d_handphone") %></td>
			</tr>
			<tr>
				<th>팩스번호</th>
				<td><%= dbox.get("d_fax") %></td>
				<th>SMS수신여부</th>
				<td class="end"><%=dbox.getBoolean("d_islettering")?"받음" :"받지않음"  %></td>
			</tr>
			<tr>
				<th>메일수신여부</th>
				<td><%=dbox.getBoolean("d_ismailing")?"받음" :"받지않음"  %></td>
				<th>뉴스레터수신여부</th>
				<td class="end"><%=dbox.getBoolean("d_islettering")?"받음" :"받지않음"  %></td>
			</tr>
			</table>




			<p class="list_btn"><a href="javascript:ssoUpdate();" class="btn_gr"><span>통합회원정보 변경</span></a></p>
<script>
	function selectMilitary() {
		var s = $(":input:radio[name=p_militarystatus]:checked").val();
		if(!s || s=="") {
			$("#p_militarystart").val('');
			$("#p_militaryend").val('');
			$("#p_militarymemo").val('');
			$("#milliDate").hide();
			$("#milliMemo").hide();
		}
		else if(s.indexOf('1')!=-1) {
			$("#milliDate").show();
			$("#p_militarymemo").val('');
			$("#milliMemo").hide();
		}
		else if(s.indexOf('2')!=-1) {
			$("#p_militarystart").val('');
			$("#p_militaryend").val('');
			$("#p_militarymemo").val('');
			$("#milliDate").hide();
			$("#milliMemo").hide();
		}
		else {
			$("#p_militarystart").val('');
			$("#p_militaryend").val('');
			$("#milliDate").hide();
			$("#milliMemo").show();
		}
	} 
</script>
			<h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_army.gif" alt="병역구분" class="fl_l" /> <span class="ra_txt"><input type="radio" onclick='selectMilitary()' name="p_militarystatus" value="" <%= dbox.get("d_militarystatus").length()==0 ? "checked" : "" %>/><strong>해당없음</strong> (해당사항이 없을 경우 체크해주세요)</span></h3>
			<table class="write">
			<tr>
				<td class="intxt end">
					<span><kocca_select:select sqlNum="0001" name="p_militarystatus" styleClass="mg_l10" param="0093" onChange="selectMilitary" selectedValue='<%= dbox.get("d_militarystatus") %>' isLoad="true" type="2.2"/></span>
					<span class="mg_l20" id="milliDate">
						복무기간
							<input name="p_militarystart" id="p_militarystart" type="text" class="inbox" style="width:65px;" value="<%= dbox.get("d_militarystart") %>"/>
							 ~
							<input name="p_militaryend" id="p_militaryend" type="text" class="inbox" style="width:65px;" value="<%= dbox.get("d_militaryend") %>"/>
					</span>
					<span class="mg_l15" id="milliMemo">면제사유 <input type="text" name="p_militarymemo" id="p_militarymemo" class="inbox" style="width:113px;"  value="<%= dbox.get("d_militarymemo") %>" /></span>
				</td>
			</tr>
			</table>
			<p class="list_btn mg_b9"><a href="javascript:document.form1.submit();" class="btn_gr"><span>등록</span></a></p>
			
	<h3 class="mg_b9"><img src="/images/portal/common/stit_edu.gif" alt="학력정보" class="tit1 fl_l" /></h3>
			<table class="write">
			<colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
			<tr>
				<th>학교명</th>
				<td><input type="text" name="p_schoolname" id="p_schoolname" value="" class="inbox" style="width:162px;" /></td>
				<th>소재지</th>
				<td class="end"><input type="text" class="inbox" name="p_place"  id="p_place"  style="width:162px;" /></td>
			</tr>
			<tr>
				<th>학과/전공</th>
				<td><input type="text" class="inbox" name="p_major" id="p_major" style="width:162px;" /></td>
				<th>졸업구분</th>
				<td class="end"><span><kocca_select:select sqlNum="0001" name="p_status" styleClass="mg_l10" param="0094"	 selectedValue='<%= dbox.get("d_militarystatus") %>' isLoad="true" type="2.2"/></span></td>
			</tr>
			<tr>
				<th>재학기간</th>
				<td colspan="3" class="end">
					<!-- 	<input type="text" name="p_sstart" id="p_sstart" class="inbox" style="width:65px;" size="10" maxlength=10  value="" onclick="MiniCal(this)" readonly /><img onclick="p_sstart.click();" src="/images/portal/ico/ico_calendar.gif"  style='CURSOR:hand' class="calendar" /> ~
					<input type="text" name="p_send" id="p_send" class="inbox" style="width:65px;" size="10" maxlength=10  value="" onclick="MiniCal(this)" readonly /><img onclick="p_send.click();" src="/images/portal/ico/ico_calendar.gif"  style='CURSOR:hand' class="calendar" /> -->
					<input type="text" name="p_sstart" id="p_sstart" class="inbox" style="width:65px;" value=""> ~
					<input type="text" name="p_send" id="p_send" class="inbox" style="width:65px;" value="">
				</td>
			</tr>
			</table>
			<p class="list_btn mg_b9"><a href="javascript:whenSimpleSave('study',
															$('#p_schoolname').val(),
															$('#p_place').val(),
															$(':input:radio[name=p_status]:checked').val(),
															$('#p_major').val(),
															$('#p_sstart').val(),
															$('#p_send').val()	)" class="btn_gr"><span>등록</span></a></p>
			<table class="ac_list">
			<colgroup><col width="114px" /><col width="101px" /><col width="147px" /><col /><col width="101px" /></colgroup>
			<thead>
			<tr>
				<th>학교명</th>
				<th>졸업구분</th>
				<th>재학기간</th>
				<th>학과/전공</th>
				<th>소재지</th>
				<th>삭제</th>
			</tr>
			</thead>
			<tbody id="schoolList">
<%	for(DataBox schoolListBox : schoolList) { %>
			<tr id="study<%= schoolListBox.get("d_seq") %>">
				<td><%= schoolListBox.get("d_schoolname") %></td>
				<td><%= schoolListBox.get("d_statusnm") %></td>
				<td><%= schoolListBox.get("d_sstart") %>~<%= schoolListBox.get("d_send") %></td>
				<td><%= schoolListBox.get("d_major") %></td>
				<td><%= schoolListBox.get("d_place") %></td>
				<td><a href="javascript:whenSimpleDelete('study', '<%= schoolListBox.get("d_seq") %>');"><img src="/images/admin/portal/stat_0.gif"/></a></td>
			</tr>
<%	} %>
			</tbody>
			</table>


			<h3 class="mg_b9"><img src="/images/portal/common/stit_certificate.gif" alt="자격증" class="fl_l" /></h3>
			<table class="write">
			<colgroup><col width="18%" /><col width="32%" /><col width="18%" /><col width="32%" /></colgroup>
			<tr>
				<th>자격증명</th>
				<td><input type="text" name="p_licensename" id="p_licensename" class="inbox" style="width:162px;" /></td>
				<th>취득일</th>
				<td class="end">
				<!-- 	<input type="text" name="p_getdate" id="p_getdate" class="inbox" style="width:65px;" value="" onclick="MiniCal(this)" readonly/><img src="/images/portal/ico/ico_calendar.gif"  onclick="p_getdate.click();" style='CURSOR:hand' class="calendar" /> -->
				<input type="text" name="p_getdate" id="p_getdate" class="inbox" style="width:65px;" value="">
				</td>
			</tr>
			<tr>
				<th>발행처</th>
				<td colspan="3" class="end"><input type="text" name="p_publish" id="p_publish" class="inbox" style="width:162px;" /></td>
			</tr>
			</table>
			<p class="list_btn mg_b9"><a href="javascript:whenSimpleSave('license',
																$('#p_licensename').val(),
																$('#p_getdate').val(),
																$('#p_publish').val()	)" class="btn_gr"><span>등록</span></a></p>

			<table class="ac_list">
			<colgroup><col width="221px" /><col width="175px" /><col /></colgroup>
			<thead>
			<tr>
				<th>자격증명</th>
				<th>취득일</th>
				<th>발행처</th>
				<th>삭제</th>
			</tr>
			</thead>
			<tbody id="licenseList">
<%	for(DataBox licenseListBox : licenseList) { %>
			<tr id="license<%= licenseListBox.get("d_seq") %>">
				<td><%= licenseListBox.get("d_licensename") %></td>
				<td><%= licenseListBox.get("d_getdate") %></td>
				<td><%= licenseListBox.get("d_publish") %></td>
				<td><a href="javascript:whenSimpleDelete('license', '<%= licenseListBox.get("d_seq") %>');"><img src="/images/admin/portal/stat_0.gif"/></a></td>
			</tr>
<%	} %>
			</tbody>
			</table>

			<h3 class="pop_tit mg_b9"><img src="/images/portal/common/stit_career.gif" alt="경력사항" class="tit1 fl_l" /></h3>
			<table class="write">
			<colgroup><col width="16%" /><col width="38%" /><col width="16%" /><col width="20%" /></colgroup>
			<tr>
				<th>직업</th>
				<td colspan="3" class="end">
					<input type="radio" name="jikGubun" onclick="changep_jikup('0091');$('#p_jikjong').hide();$('#p_jikup').show();"   checked/>문화산업종사자&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="jikGubun" onclick="changep_jikjong('0092');$('#p_jikup').hide();$('#p_jikjong').show();"/>비문화산업종사자&nbsp;&nbsp;
					<kocca_select:select sqlNum="0001" name="p_jikup" styleClass="mg_l10" param="0091" selectedValue='' isLoad="true" type="1" all="no"/>
					<kocca_select:select sqlNum="0001" name="p_jikjong" styleClass="mg_l10" param="0092" selectedValue='' isLoad="true" type="1" all="no"/>
				</td>
			</tr>
			<tr>
				<th>회사명</th>
				<td><input type="text" class="inbox" name="p_officename" id="p_officename" style="width:162px;" /></td>
				<th>직무/직급</th>
				<td class="end"><input type="text" class="inbox" name="p_position" id="p_position" style="width:162px;" /></td>
			</tr>
			<tr>
				<th>재직기간</th>
				<td>
					 <input type="text" name="p_workstart" id="p_workstart" class="inbox" style="width:65px;" value=""/>
					 ~ <input type="text" name="p_workend" id="p_workend" class="inbox" style="width:65px;" value=""/>
					 </td>
				<th>재직구분</th>
				<td class="end"><select><option>선택</option></select></td>
			</tr>
			</table>
			<p class="list_btn mg_b9"><a href="javascript:whenSimpleSave('workinfo',
																$('#p_jikup').val(),
																$('#p_jikjong').val(),
																$('#p_officename').val(),
																$('#p_position').val(),
																$('#p_workstart').val(),
																$('#p_workend').val(),
																$('#p_workstatus').val()
																)" class="btn_gr"><span>등록</span></a></p>

			<table class="ac_list">
			<colgroup><col width="67px" /><col width="108px" /><col width="135px" /><col /><col width="150px" /><col width="80px" /></colgroup>
			<thead>
			<tr>
				<th>직업</th>
				<th>직종</th>
				<th>회사명</th>
				<th>직무/직급</th>
				<th>재직기간</th>
				<th>재직구분 </th>
				<th>삭제</th>
			</tr>
			</thead>
			<tbody id="workinfoList">
<%	for(DataBox careerListbox : careerList) { %>
			<tr id="workinfo<%= careerListbox.get("d_seq") %>">
				<td><%= careerListbox.get("d_jikup") %></td>
				<td><%= careerListbox.get("d_jikjong") %></td>
				<td><%= careerListbox.get("d_officename") %></td>
				<td><%= careerListbox.get("d_position") %></td>
				<td><%= careerListbox.get("d_workstart") %>~<%= careerListbox.get("d_workend") %></td>
				<td><%= careerListbox.get("d_workstatus") %></td>
				<td><a href="javascript:whenSimpleDelete('workinfo', '<%= careerListbox.get("d_seq") %>');"><img src="/images/admin/portal/stat_0.gif"/></a></td>
			</tr>
<%	} %>
			</tbody>
			</table>

</form>
<!-- form 끝 -->
<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
