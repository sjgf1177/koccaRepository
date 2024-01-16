<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="com.credu.library.RequestBox"%>
<%@ page import="com.credu.library.RequestManager"%>
<%@ page import="com.credu.library.DataBox"%>
<%@ page import="java.util.ArrayList"%>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    ArrayList onlineList = (ArrayList)request.getAttribute("onlineList");
    ArrayList categoryList = (ArrayList)request.getAttribute("categoryList");
    ArrayList jobList = (ArrayList)request.getAttribute("jobList");
    ArrayList loadMapList1 = (ArrayList)request.getAttribute("loadMapList1");
    ArrayList loadMapList2 = (ArrayList)request.getAttribute("loadMapList2");
    ArrayList loadMapList3 = (ArrayList)request.getAttribute("loadMapList3");
    ArrayList lvCdList = (ArrayList)request.getAttribute("lvCdList");

    DataBox dbox = null;
    DataBox dbox1 = null;
    DataBox dbox2 = null;
    DataBox dbox3 = null;
    DataBox dbox4 = null;

    String upperClsCd = box.getStringDefault("upperClsCd", "0000");
    String lmType = box.getStringDefault("lmType", "B0");
%>

<html>
<head>
<title>��� ��ȸ | �����з� ���� | ���� | ������ | �ѱ���������ī����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/css/2023/admin.css"> <!-- Ŀ���� css �߰�-->

<style type="text/css">
button {
	display: inline-block;
	padding: 2px 4px 0px 4px;
	margin-bottom: 0;
	font-size: 12px;
	font-weight: normal;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	border-style: solid 1px;
}

button.btn_reg {
	width: 80px;
	height: 20px;
	background-color: #efefef;
	color: #000000;
	border-color: #808080;
	border-width: 1px;
}

input.btn_reg {
	width: 80px;
	height: 20px;
	background-color: #efefef;
	color: #000000;
	border-color: #808080;
	border-width: 1px;
	text-align: center;
}

button.btn_basic {
	width: 80px;
	height: 20px;
	background-color: #efefff;
	color: #000000;
	border-color: #808090;
	border-width: 1px;
}
</style>

<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript">
	/**
	 * �� ����. �ε��� ���� �����Ѵ�.
	 */
	function fnSelectTab( area ) {

		var tab = $('#'+area+'Tab');
		if(tab){
			$(tab).parent().parent().find('table').each(function(){
				$(this).find('td:eq(0)').removeClass("blue_butt_left").addClass("black_butt_left");
				$(this).find('td:eq(1)').removeClass("blue_butt_top").addClass("black_butt_top");
				$(this).find('td:eq(2)').removeClass("blue_butt_right").addClass("black_butt_right");
				$(this).find('td:eq(3)').removeClass("blue_butt_middle").addClass("black_butt_middle");
				$(this).find('td:eq(4)').removeClass("blue_butt_bottom").addClass("black_butt_bottom");
			});


			$(tab).find('#Td1').removeClass("black_butt_left").addClass("blue_butt_left");
			$(tab).find('#Td2').removeClass("black_butt_top").addClass("blue_butt_top");
			$(tab).find('#Td3').removeClass("black_butt_right").addClass("blue_butt_right");
			$(tab).find('#Td4').removeClass("black_butt_middle").addClass("blue_butt_middle");
			$(tab).find('#Td5').removeClass("black_butt_bottom").addClass("blue_butt_bottom");
		}

		$('#OnlineArea').hide();
		$('#MobileArea').hide();
		$('#JobArea').hide();
		$('#LoadMapArea').hide();
		$('#'+area).show();
		$("#oUpperClsCd").val( $(tab).attr('tabidx') );

		if(area == 'LoadMapArea'){
			$('.table1').hide();
			fnloadmap();
		}else{
			$('.table1').show();
		}
	}

	/**
	 * ����� ��ȸ�Ѵ�.
	 */
	function fnRetrieveSubjectClassifyList() {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("listPage");
		$("#oSubjClassifyForm").submit();
	}

	/**
	 * ��� ȭ������ �̵��Ѵ�. ���õ� ���� �з� �׸��� ����ϰ� �ȴ�..
	 */
	function fnGotoRegisterPage() {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("registerPage");
		$("#oSubjClassifyForm").submit();
	}

	/**
	 * ���� ȭ������ �̵��Ѵ�. ���õ� ���� �з� �׸��� ����ϰ� �ȴ�..
	 */
	function fnGotoModifyPage( clsCd ) {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("updatePage");
		$("#oClsCd").val(clsCd);
		$("#oSubjClassifyForm").submit();
	}

	/**
	 * �¶��� �з� ����ȭ���̵�
	 */
	function fnUpdatePage( gubun1, gubun2, gubun3 ) {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("updatePage");
		$("#gubun1").val(gubun1);
		$("#gubun2").val(gubun2);
		$("#gubun3").val(gubun3);
		$("#oSubjClassifyForm").submit();
	}

	/**
	 * ���� �з� �׸��� �����Ѵ�. ��ϵ� ������ ������ �Բ� �����ȴ�.
	 */
	function fnDeleteSubjClassify(clsCd) {
		if (confirm("������ �ϰԵǸ� ��ϵ� ���� ��ϵ� �Բ� �����˴ϴ�.\n���� �����Ͻðڽ��ϱ�?")) {
			$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
			$("#oProcess").val("delete");
			$("#oClsCd").val(clsCd);
			$("#oSubjClassifyForm").submit();
		}
	}

	/**
	 * �¶��� �����з� �׸��� ����
	 */
	function fnDeleteOnlineClassify(gubun1, gubun2, gubun3) {
		if (confirm("������ �ϰԵǸ� ��ϵ� ���� ��ϵ� �Բ� �����˴ϴ�.\n���� �����Ͻðڽ��ϱ�?")) {
			$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
			$("#oProcess").val("delete");
			$("#gubun1").val(gubun1);
			$("#gubun2").val(gubun2);
			$("#gubun3").val(gubun3);
			$("#oSubjClassifyForm").submit();
		}
	}

	/**
	 * �������� ȭ������ �̵�
	 */
	function fnGotoManagePage( clsCd, clsNm ) {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("manageSubjectPage");
		$("#oClsCd").val(clsCd);
		$("#oClsNm").val(clsNm);
		$("#oSubjClassifyForm").submit();
	}

	/**
	 * �������� ȭ������ �̵�
	 */
	function fnOnlineSubjPage( gubun1, gubun2, gubun3, lvcode ) {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("manageSubjectPage");
		$("#gubun1").val(gubun1);
		$("#gubun2").val(gubun2);
		$("#gubun3").val(gubun3);
		$("#hcd").val(lvcode);
		$("#oSubjClassifyForm").submit();
	}

	/**
	 * ���̵����� ȭ������ �̵�
	 */
	function fnOnlineLevelPage( gubun1, gubun2, gubun3, lvcode ) {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("courseLevelPage");
		$("#gubun1").val(gubun1);
		$("#gubun2").val(gubun2);
		$("#gubun3").val(gubun3);
		$("#hcd").val(lvcode);
		$("#oSubjClassifyForm").submit();
	}

	function fnSetLevelType(type){
		$("#" + type + "_2").show();

		if(!$("#" + type + "_1").is(':visible')){
			$("#" + type + "_1").hide();
		}

		$("#btn_" + type).hide();
	}

	function fnLevelTypeCancel(type){
		$("#" + type + "_1").show();
		$("#" + type + "_2").hide();
		$("#btn_" + type).show();
	}

	function fnLevelTypeSave(type){
		if($("#gubun_" + type).val() == ""){
			alert("���̵� ������ �����ϼ���.");
			$("#gubun_" + type).focus();
			return;
		}

		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("levelCodeUpdate");
		$("#type").val(type);
		$("#lvcd").val($("#gubun_" + type).val());
		$("#oSubjClassifyForm").submit();
	}

	function fnloadmap(){
		$('html, body').css("overflow","hidden");
		$('.main_layer').addClass('on');
	}

	function sublayerop(){
		$('.opacity_layer_bg02, .sub_layer').addClass('on');
	}

	// ���̾�â ����
	function fnsubclass(s, y, q, g){
		$.ajax({
			type: "post",
			url: "/servlet/controller.course.SubjectClassifyServlet",
			dataType: "json",
			data: {
				p_course_id: s
			,	p_year : y
			,	p_subjseq : q
			,	p_gubun : g
			,	p_type : $("#lmType").val()
			,	process: "nextCourseList"
			},
			success: function (data) {
				var res = "";
				var listStr = "";

				res = data.resInfo[0];

				$(".depth").text(res.d1 + " > " + res.d2);
				$(".title_txt").text("[" + res.crdate + "]" + res.courseNm.replaceAll("u2018", "'").replaceAll("u2019", "'"));
				$(".taglv").removeClass("bg_orange");
				$(".taglv").removeClass("bg_beage");
				$(".taglv").removeClass("bg_green");
				$("#snimg").attr("src", "");
				$(".eduview").attr("href", "");

				$(".taglv").text(res.lv);

				if (res.lv == "�ʱ�") {
					$(".taglv").addClass("bg_green");
				} else if (res.lv == "�߱�") {
					$(".taglv").addClass("bg_beage");
				} else if (res.lv == "���") {
					$(".taglv").addClass("bg_orange");
				}

				if (g == "S") {
					$(".tagtype").text("�α��� �� ����");
					$(".eduview").attr("href", "javascript:whenViewEdu('S', '" + res.courseId + "', 1200, 800)");
				} else if (g == "G") {
					$(".tagtype").text("�ٷ� ����");
					$(".eduview").attr("href", "javascript:whenViewEdu('G', '" + res.vodUrl + "', " + res.widthS + ", " + res.heightS + ")");
				}

				$("#snimg").attr("alt", res.courseNm.replaceAll("u2018", "'").replaceAll("u2019", "'"));
				$("#snimg").attr("src", "https://edu.kocca.kr/" + res.img);
				//<li><span className="fc_red">[2020]</span>����ο� ��Ʃ����Ȱ_��� �׷��� ��<span className="bg_orange fc_white tag" style="margin-left: 3px;">���</span><span className="tag linegray">����</span></li>

				$("#h5").text("�������(" + data.resList.length + ")");
				$.each(data.resList, function() {
					listStr += "<li>   \n";
					listStr += "    <span class=\"fc_red\">[" + this.crDate + "]</span>" + this.courseNm.replaceAll("u2018", "'").replaceAll("u2019", "'") + "     \n";

					if (this.lvNm == "�ʱ�") {
						listStr += "    <span class=\"bg_green fc_white tag\" style=\"margin-left: 3px;\">" + this.lvNm + "</span>    \n";
					} else if (this.lvNm == "�߱�") {
						listStr += "    <span class=\"bg_beage fc_white tag\" style=\"margin-left: 3px;\">" + this.lvNm + "</span>    \n";
					} else if (this.lvNm == "���") {
						listStr += "    <span class=\"bg_orange fc_white tag\" style=\"margin-left: 3px;\">" + this.lvNm + "</span>    \n";
					}

					if (this.type == "S") {
						listStr += "    <span class=\"tag linegray\">�α��� �� ����</span>  \n";
					} else if (this.type == 'G') {
						listStr += "    <span class=\"tag linegray\">�ٷ� ����</span>  \n";
					}

					listStr += "</li>   \n";
				});

				var listArea = $(".linkCrs");
				listArea.empty();
				listArea.html( listStr );

				sublayerop();
			},
			error: function (arg1, arg2) {
					alert("������ �߻��Ͽ����ϴ�.");
					return;
			}
		});
	}

    // ���̾�â �ݱ�
    function layerClose(){
        //$(this).click(function (){
            var opLayerstate = $('.opacity_layer_bg02').hasClass('on');

            if(opLayerstate == true){
                $('.sub_layer, .opacity_layer_bg02').removeClass('on');

            } else{
                $('.main_layer').removeClass('on');
                $('html, body').css('overflow','auto');

            }
        //});
    }

    /*
    * �н��ε�� ����� ��ȸ�Ѵ�.
    */
	function fnLoadMapList(t) {
		$("#oSubjClassifyForm").action = "/servlet/controller.course.SubjectClassifyServlet";
		$("#oProcess").val("listPage");
		$("#lmType").val(t);
		$("#oSubjClassifyForm").submit();
	}

	// �н�â �̸�����
	function whenViewEdu(t, u, w, h){
		if (t == "S") {
			u = "/servlet/controller.contents.EduStart?p_year=2000&p_subjseq=0001&p_subj=" + u;
		}

		window.open(u,'VodViewEdu','width=' + w + ',height=' + h);
	}

	/**
	 * ������ �ε��� �� ������ ����
	 */
	$(document).ready( function () {
		var upperClsCd = "<%= upperClsCd %>";
		var tab = "OnlineArea";
		if(upperClsCd == "1000"){
			tab = "MobileArea";
		}else if(upperClsCd == "2000"){
			tab = "JobArea";
		}else if(upperClsCd == "0001"){
			tab = "LoadMapArea";
		}
		fnSelectTab(tab);

		$(".layer_tab_menu").removeClass("active");

		if($("#lmType").val() == "G0") {
			$(".lmap_g").addClass("active");
		} else if($("#lmType").val() == "M0") {
			$(".lmap_m").addClass("active");
		} else if($("#lmType").val() == "S0") {
			$(".lmap_s").addClass("active");
		} else if($("#lmType").val() == "K0") {
			$(".lmap_k").addClass("active");
		} else {
			$(".lmap_b").addClass("active");
		}
	});

</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

	<form id="oSubjClassifyForm" name="subjClassifyForm" method="post">
		<input type="hidden" id="oProcess" name="process" value="" /> 
		<input type="hidden" id="oUpperClsCd" name="upperClsCd" value="<%= upperClsCd %>" />
		<input type="hidden" id="oClsCd" name="clsCd" value="" /> 
		<input type="hidden" id="oClsNm" name="clsNm" value="" />
		<input type="hidden" id="gubun1" name="gubun1" value="" />
		<input type="hidden" id="gubun2" name="gubun2" value="" />
		<input type="hidden" id="gubun3" name="gubun3" value="" />
		<input type="hidden" id="type" name="type" value="" />
		<input type="hidden" id="lvcd" name="lvcd" value="" />
		<input type="hidden" id="hcd" name="hcd" value="" />
		<input type="hidden" id="lmType" name="lmType" value="<%= lmType %>" />

		<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
			<tr>
				<td align="center" valign="top">
					<!-- title ���� //-->
					<table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
						<tr>
							<td style="text-align: left;">
								<img src="/images/admin/course/c_title12.gif">
							</td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
						</tr>
					</table> <!-- title �� //--> <br /> <!-- tab ���� //-->
					<table width="97%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12%">
								<table cellspacing="0" cellpadding="0" class="s_table" tabidx="0000" id="OnlineAreaTab">
									<tr>
										<td rowspan="3" class="blue_butt_left" id="Td1"></td>
										<td class="blue_butt_top" id="Td2"></td>
										<td rowspan="3" class="blue_butt_right" id="Td3"></td>
									</tr>
									<tr>
										<td class="blue_butt_middle" id="Td4"><a href="javascript:fnSelectTab('OnlineArea')" class="c">�¶��� �з���</a></td>
									</tr>
									<tr>
										<td class="blue_butt_bottom" id="Td5"></td>
									</tr>
								</table>
							</td>
							<td width="12%">
								<table cellspacing="0" cellpadding="0" class="s_table" tabidx="1000" id="MobileAreaTab">
									<tr>
										<td rowspan="3" class="black_butt_left" id="Td1"></td>
										<td class="black_butt_top" id="Td2"></td>
										<td rowspan="3" class="black_butt_right" id="Td3"></td>
									</tr>
									<tr>
										<td class="black_butt_middle" id="Td4"><a href="javascript:fnSelectTab('MobileArea')" class="c">����� �з���</a></td>
									</tr>
									<tr>
										<td class="black_butt_bottom" id="Td5"></td>
									</tr>
								</table>
							</td>
							<td width="12%">
								<table cellspacing="0" cellpadding="0" class="s_table" tabidx="2000" id="JobAreaTab">
									<tr>
										<td rowspan="3" class="black_butt_left" id="Td1"></td>
										<td class="black_butt_top" id="Td2"></td>
										<td rowspan="3" class="black_butt_right" id="Td3"></td>
									</tr>
									<tr>
										<td class="black_butt_middle" id="Td4"><a href="javascript:fnSelectTab('JobArea')" class="c">������</a></td>
									</tr>
									<tr>
										<td class="black_butt_bottom" id="Td5"></td>
									</tr>
								</table>
							</td>
							<td width="12%">
								<table cellspacing="0" cellpadding="0" class="s_table" tabidx="0001" id="LoadMapAreaTab">
									<tr>
										<td rowspan="3" class="black_butt_left" id="Td1"></td>
										<td class="black_butt_top" id="Td2"></td>
										<td rowspan="3" class="black_butt_right" id="Td3"></td>
									</tr>
									<tr>
										<td class="black_butt_middle" id="Td4"><a href="javascript:fnSelectTab('LoadMapArea')" class="c">�н��ε��</a></td>
									</tr>
									<tr>
										<td class="black_butt_bottom" id="Td5"></td>
									</tr>
								</table>
							</td>
							<td width="75%">&nbsp;</td>
						</tr>
						<tr>
							<td height="2" colspan="4" bgcolor="#6699CC"></td>
						</tr>
					</table> 
					<br /> 
					
					<!--- �߰� ��ư ���� //-->
					<table cellpadding="0" cellspacing="0" class="table1" border="0">
						<tr>
							<td width="480"><span style="font-weight: bold; font-size: 16pt; vertical-align: bottom;">�з��� ���</span></td>
							<td align="right" height="20">
								<button class="btn_reg" onclick="fnGotoRegisterPage();">���</button>&nbsp;&nbsp;
								<button class="btn_reg" onclick="fnRetrieveSubjectClassifyList();">��ȸ</button>
							</td>
						</tr>
						<tr>
							<td height="3"></td>
						</tr>
					</table> 
					<!-- �߰� ��ư �� //--> 
					
					<!-- �з��� ��� ���� //-->
					<div id="OnlineArea" style="display: block;">
						<table cellspacing="1" cellpadding="5" class="table_out">
							<colgroup>
								<col width="8%" />
 								<col width="25%" />
								<col width="20%" />
								<col width="20%" />
								<col width="15%" />
								<col width="20%" />
								<col width="20%" />
								<col width="*" /> 
							</colgroup>
							<tr>
								<td colspan="8" class="table_top_line"></td>
							</tr>
							<tr>
								<td class="table_title">NO</td>
								<td class="table_title">�帣</td>
 								<td class="table_title">��з�</td>
								<td class="table_title">�Һз�</td>
								<td class="table_title">������(��/��)</td>
								<td class="table_title">��������</td>
								<td class="table_title">���̵�����</td>
								<td class="table_title">����/����</td> 
							</tr>

							<%
						    if (onlineList.size() > 0) {
						    	int row1 = 0;
						    	int row2 = 0;
						    	String v_gubun_tmp1 = "";
						    	String v_gubun_tmp2 = ""; 
						        for ( int i = 0 ;i < onlineList.size() ; i++ ) {
						            dbox = (DataBox)onlineList.get(i);
						            row1 = dbox.getInt("d_gubuncnt1"); 
						            row2 = dbox.getInt("d_gubuncnt2"); 
							%>
									<tr>
										<td class="table_01"><%= (i + 1) %></td>
										<%
										if(!v_gubun_tmp1.equals(dbox.getString("d_gubunnm1"))){
											v_gubun_tmp1 = dbox.getString("d_gubunnm1");
										%>
											<td class="table_02_2" rowspan="<%=row1%>">
												<b><%= dbox.getString("d_gubunnm1") %></b>
												<br/>
					                       	<%
										    if (!"".equals(dbox.getString("d_lvcode"))) {
										    %>
					                       		<div id="<%=dbox.getString("d_gubun1") %>_1" style="color:blue"><b>(<%= dbox.getString("d_lvcdnm") %>)</b></div>
										    <% 
										    } else {
											%>		
												<div id="<%=dbox.getString("d_gubun1") %>_1" style="display:none;"></div>
											<% 
										    }
											%>													
												<div id="<%=dbox.getString("d_gubun1") %>_2" style="display:none;">
							                        <select id="gubun_<%=dbox.getString("d_gubun1") %>" name="gubun_<%=dbox.getString("d_gubun1") %>" style="width:110px;">
							                        	<option value="">�����ϼ���.</option>
						                       	<%
											    if (lvCdList.size() > 0) {
											        for ( int j = 0 ;j < lvCdList.size() ; j++ ) {
											        	dbox1 = (DataBox)lvCdList.get(j);
											    %>
					                       				<option value="<%=dbox1.getString("d_code")%>"><%=dbox1.getString("d_codenm")%></option>
											    <% 
											        }
											    }
												%>
							                        </select>													
							                        <button type="button" class="btn_reg" onclick="javascript:fnLevelTypeSave('<%=dbox.getString("d_gubun1") %>');" style="width:30px;">����</button>
							                        <button type="button" class="btn_reg" onclick="javascript:fnLevelTypeCancel('<%=dbox.getString("d_gubun1") %>');" style="width:30px;">���</button>
												</div>

												
												<button type="button" id="btn_<%=dbox.getString("d_gubun1") %>" class="btn_reg" onclick="javascript:fnSetLevelType('<%=dbox.getString("d_gubun1") %>');" >���̵� ����</button>
											</td>
										<%}%>
 										<%
										if(!v_gubun_tmp2.equals(dbox.getString("d_gubunnm2")) || "".equals(dbox.getString("d_gubunnm2"))){
											v_gubun_tmp2 = dbox.getString("d_gubunnm2");
										%>
											<td class="table_02_2" rowspan="<%=row2%>"><b><%= dbox.getString("d_gubunnm2") %></b> </td>
										<%}%>
										<td class="table_02_2"><b><%= dbox.getString("d_gubunnm3") %></b> </td>
										<td class="table_03_1">(<%= dbox.getInt("d_subjcnt") %> / <%= dbox.getInt("d_goldcnt") %>) ����</td>
										<td class="table_03_1">
											<button class="btn_basic" onclick="fnOnlineSubjPage('<%= dbox.getString("d_gubun1")%>', '<%= dbox.getString("d_gubun2")%>', '<%= dbox.getString("d_gubun3")%>', '<%= dbox.getString("d_lvcode")%>');">��������</button>
										</td>
										<td class="table_03_1">
											<button class="btn_basic" onclick="fnOnlineLevelPage('<%= dbox.getString("d_gubun1")%>', '<%= dbox.getString("d_gubun2")%>', '<%= dbox.getString("d_gubun3")%>', '<%= dbox.getString("d_lvcode")%>');">���̵�����</button>
										</td>										
										<td class="table_03_1">
											<button id="oModBtn" class="btn_basic" onclick="fnUpdatePage('<%= dbox.getString("d_gubun1")%>', '<%= dbox.getString("d_gubun2")%>', '<%= dbox.getString("d_gubun3")%>');return false;">����</button>&nbsp;&nbsp;
											<button id="oDelBtn" class="btn_basic" onclick="fnDeleteOnlineClassify('<%= dbox.getString("d_gubun1")%>', '<%= dbox.getString("d_gubun2")%>', '<%= dbox.getString("d_gubun3")%>');return false;">����</button>
										</td>
									</tr>
							<%
						        }
						    } else {
							%>
								<tr>
									<td class="table_01" colspan="6" style="text-align: center; text-weight: bold;">��ϵ� �з��� �����ϴ�.</td>
								</tr>
							<%
						    }
							%>
						</table>
					</div> 
					<!-- �з��� ��� �� //-->
					
					
					<!-- �з��� ��� ���� //-->
					<div id="MobileArea" style="display: none;">
						<table cellspacing="1" cellpadding="5" class="table_out">
							<colgroup>
								<col width="8%" />
								<col width="*" />
								<col width="10%" />
								<col width="10%" />
								<col width="14%" />
								<col width="22%" />
							</colgroup>
							<tr>
								<td colspan="6" class="table_top_line"></td>
							</tr>
							<tr>
								<td class="table_title">NO</td>
								<td class="table_title">�з���</td>
								<td class="table_title">��뿩��</td>
								<td class="table_title">���ļ���</td>
								<td class="table_title">��������</td>
								<td class="table_title">����/����</td>
							</tr>

							<%
						    if (categoryList.size() > 0) {
						        for ( int i = 0 ;i < categoryList.size() ; i++ ) {
						            dbox = (DataBox)categoryList.get(i);
							%>
									<tr>
										<td class="table_01"><%= (i + 1) %></td>
										<td class="table_02_1" style="text-align: left; padding-left: 8px;"><b><%= dbox.getString("d_cls_nm") %></b> (<%= dbox.getInt("d_cnt") %>����)</td>
										<td class="table_02_1"><%= dbox.getString("d_use_yn") %></td>
										<td class="table_02_1"><%= dbox.getString("d_sort_order") %></td>
										<td class="table_03_1"><button class="btn_basic" onclick="fnGotoManagePage('<%= dbox.getString("d_cls_cd") %>', '<%= dbox.getString("d_cls_nm") %>');">��������</button></td>
										<td class="table_03_1">
											<button id="oModBtn" class="btn_basic" onclick="fnGotoModifyPage('<%= dbox.getString("d_cls_cd") %>');">����</button>&nbsp;&nbsp;
											<button id="oDelBtn" class="btn_basic" onclick="fnDeleteSubjClassify('<%= dbox.getString("d_cls_cd") %>');">����</button>
										</td>
									</tr>
							<%
						        }
						    } else {
							%>
								<tr>
									<td class="table_01" colspan="6" style="text-align: center; text-weight: bold;">��ϵ� �з��� �����ϴ�.</td>
								</tr>
							<%
						    }
							%>
						</table>
					</div> 
					<!-- �з��� ��� �� //-->
					
					
					<!-- ������ ��� ���� //-->
					<div id="JobArea" style="display: none;">
						<table cellspacing="1" cellpadding="5" class="table_out">
							<colgroup>
								<col width="8%" />
								<col width="*" />
								<col width="10%" />
								<col width="10%" />
								<col width="14%" />
								<col width="22%" />
							</colgroup>
							<tr>
								<td colspan="6" class="table_top_line"></td>
							</tr>
							<tr>
								<td class="table_title">NO</td>
								<td class="table_title">�з���</td>
								<td class="table_title">��뿩��</td>
								<td class="table_title">���ļ���</td>
								<td class="table_title">��������</td>
								<td class="table_title">����/����</td>
							</tr>

							<%
						    if (jobList.size() > 0) {
						        for ( int i = 0 ;i < jobList.size() ; i++ ) {
						            dbox = (DataBox)jobList.get(i);
							%>
									<tr>
										<td class="table_01"><%= (i + 1) %></td>
										<td class="table_02_1" style="text-align: left; padding-left: 8px;"><b><%= dbox.getString("d_cls_nm") %></b> (<%= dbox.getInt("d_cnt") %>����)</td>
										<td class="table_02_1"><%= dbox.getString("d_use_yn") %></td>
										<td class="table_02_1"><%= dbox.getString("d_sort_order") %></td>
										<td class="table_03_1"><button class="btn_basic" onclick="fnGotoManagePage('<%= dbox.getString("d_cls_cd") %>', '<%= dbox.getString("d_cls_nm") %>');">��������</button></td>
										<td class="table_03_1">
											<button id="oModBtn" class="btn_basic" onclick="fnGotoModifyPage('<%= dbox.getString("d_cls_cd") %>');">����</button>&nbsp;&nbsp;
											<button id="oDelBtn" class="btn_basic" onclick="fnDeleteSubjClassify('<%= dbox.getString("d_cls_cd") %>');">����</button>
										</td>
									</tr>
							<%
						        }
						    } else {
							%>
								<tr>
									<td class="table_01" colspan="6" style="text-align: center; text-weight: bold;">��ϵ� �з��� �����ϴ�.</td>
								</tr>
							<%
						    }
							%>
						</table>
					</div>
					<!-- ������ ��� �� //-->

				</td>
			</tr>
			<tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
		</table>

		<!-- layer -->
		<div class="opacity_layer_bg01"></div>
		<div class="layer_wrap main_layer" id="LoadMapArea1" style="overflow: auto;">
			<div class="layer_top">
				<button type="button" title="�ݱ�" class="btn_layerClose" onclick="layerClose();">�ݱ�</button>
				<h3>������ī ��ü ���� ��ü�ε��</h3>
			</div>
			<ul class="layer_tab_menu_list d-flex justify-content-center">
				<Li><button type="button" class="layer_tab_menu lmap_b active" onclick="fnLoadMapList('B0')">��ۿ���</button></Li>
				<Li><button type="button" class="layer_tab_menu lmap_g" onclick="fnLoadMapList('G0')">����</button></Li>
				<Li><button type="button" class="layer_tab_menu lmap_k" onclick="fnLoadMapList('K0')">��ȭ/�ִ�/ĳ����</button></Li>
				<Li><button type="button" class="layer_tab_menu lmap_m" onclick="fnLoadMapList('M0')">����/����</button></Li>
				<Li><button type="button" class="layer_tab_menu lmap_s" onclick="fnLoadMapList('S0')">�ι�����</button></Li>
			</ul>
			<div class="roadmap_table_wrap">
				<p><span class="fw-bold">�� <strong class="fc_skyblue"><%= loadMapList3.size() %></strong> ����</span></p>
				<div class="overflow-scroll-x overflow-scroll-y" style="border-left: 1px solid #ccc; max-height: 100%;">
					<ul class="roadmap_table_top d-flex">
						<li class="bg_darkgray">��з�</li>
						<li class="bg_darkgray">�Һз�</li>
						<li class="bg_green">�ʱ�(�α��� ��)</li>
						<li class="bg_green">�ʱ�(�ٷ� ����)</li>
						<li class="bg_beage">�߱�(�α��� ��)</li>
						<li class="bg_beage">�߱�(�ٷ� ����)</li>
						<li class="bg_orange">���(�α��� ��)</li>
						<li class="bg_orange">���(�ٷ� ����)</li>
					</ul>
			<%
				if (loadMapList1.size() > 0){
					String v_html = "";
					String v_tmp1 = "";
					String v_tmp2 = "";
					String v_tmp3 = "";
					String v_div1 = "";
					String v_div2 = "";
					String v_div3 = "";
					String v_div4 = "";
					String v_div5 = "";
					String v_div6 = "";
					String v_cd   = "";
					String v_year = "";
					String v_seq  = "";

					for ( int i = 0 ;i < loadMapList1.size() ; i++ ) {
						dbox2 = (DataBox) loadMapList1.get(i);
						v_tmp1 = dbox2.getString("d_g2c");
						v_html += "	<div class='one_daps d-flex'>																											\n";
						v_html += "		<span class='d-flex align-item-center justify-content-center'>" + dbox2.getString("d_g2n") + "</span>								\n";
						v_html += "		<ul class='two_daps'>																												\n";

						for ( int j = 0 ;j < loadMapList2.size() ; j++ ) {
							dbox3 = (DataBox) loadMapList2.get(j);
							v_tmp2 = dbox3.getString("d_g2c");
							v_tmp3 = dbox3.getString("d_g3c");

							if(v_tmp1.equals(v_tmp2)) {
								v_html += "			<li class='three_daps d-flex'>																							\n";
								v_html += "				<span class='d-flex align-item-center justify-content-center'>" + dbox3.getString("d_g3n") + "</span>				\n";

								for ( int k = 0 ;k < loadMapList3.size() ; k++ ) {
									dbox4 = (DataBox) loadMapList3.get(k);

									if(v_tmp2.equals(dbox4.getString("d_g2c"))) {
										if(v_tmp3.equals(dbox4.getString("d_g3c"))) {
										//	s_tree_stur += "aux2 = insFld(foldersTree, gFld(\""+ s_desc +"\",\""+ s_link2 + "\",'0'));\n"; // ���� ���� ����
											v_cd   = dbox4.getString("d_cd");
											v_year = dbox4.getString("d_syear");
											v_seq  = dbox4.getString("d_subjseq");

											if ("1".equals(dbox4.getString("d_type"))) {
												v_div1 += "					<button type='button' class='d-block' onclick=\"fnsubclass(\'" + v_cd + "\', \'" + v_year + "\', \'" + v_seq + "\', 'S');\">	\n";
												v_div1 += "						<span class='fc_red'>" + dbox4.getString("d_crdate") + "</span>								\n";
												v_div1 += "						<strong title='" + dbox4.getString("d_nm") + "'>" + dbox4.getString("d_nm") + "</strong>	\n";
												v_div1 += "						<span class='fc_skyblue'>(" + dbox4.getString("d_l_cnt") + ")</span>						\n";
												v_div1 += "					</button>																						\n";
											} else if ("2".equals(dbox4.getString("d_type"))) {
												v_div2 += "					<button type='button' class='d-block' onclick=\"fnsubclass(\'" + v_cd + "\', '', '', 'G');\">	\n";
												v_div2 += "						<span class='fc_red'>" + dbox4.getString("d_crdate") + "</span>								\n";
												v_div2 += "						<strong title='" + dbox4.getString("d_nm") + "'>" + dbox4.getString("d_nm") + "</strong>	\n";
												v_div2 += "					</button>																						\n";
											} else if ("3".equals(dbox4.getString("d_type"))) {
												v_div3 += "					<button type='button' class='d-block' onclick=\"fnsubclass(\'" + v_cd + "\', \'" + v_year + "\', \'" + v_seq + "\', 'S');\">	\n";
												v_div3 += "						<span class='fc_red'>" + dbox4.getString("d_crdate") + "</span>								\n";
												v_div3 += "						<strong title='" + dbox4.getString("d_nm") + "'>" + dbox4.getString("d_nm") + "</strong>	\n";
												v_div3 += "						<span class='fc_skyblue'>(" + dbox4.getString("d_l_cnt") + ")</span>						\n";
												v_div3 += "					</button>																						\n";
											} else if ("4".equals(dbox4.getString("d_type"))) {
												v_div4 += "					<button type='button' class='d-block' onclick=\"fnsubclass(\'" + v_cd + "\', '', '', 'G');\">	\n";
												v_div4 += "						<span class='fc_red'>" + dbox4.getString("d_crdate") + "</span>								\n";
												v_div4 += "						<strong title='" + dbox4.getString("d_nm") + "'>" + dbox4.getString("d_nm") + "</strong>	\n";
												v_div4 += "					</button>																						\n";
											} else if ("5".equals(dbox4.getString("d_type"))) {
												v_div5 += "					<button type='button' class='d-block' onclick=\"fnsubclass(\'" + v_cd + "\', \'" + v_year + "\', \'" + v_seq + "\', 'S');\">	\n";
												v_div5 += "						<span class='fc_red'>" + dbox4.getString("d_crdate") + "</span>								\n";
												v_div5 += "						<strong title='" + dbox4.getString("d_nm") + "'>" + dbox4.getString("d_nm") + "</strong>	\n";
												v_div5 += "						<span class='fc_skyblue'>(" + dbox4.getString("d_l_cnt") + ")</span>						\n";
												v_div5 += "					</button>																						\n";
											} else if ("6".equals(dbox4.getString("d_type"))) {
												v_div6 += "					<button type='button' class='d-block' onclick=\"fnsubclass(\'" + v_cd + "\', '', '', 'G');\">	\n";
												v_div6 += "						<span class='fc_red'>" + dbox4.getString("d_crdate") + "</span>								\n";
												v_div6 += "						<strong title='" + dbox4.getString("d_nm") + "'>" + dbox4.getString("d_nm") + "</strong>	\n";
												v_div6 += "					</button>																						\n";
											}
										}
									}
								}

								v_html += "				<div class='list_box'>																								\n";
								v_html += v_div1;
								v_html += "				</div>																												\n";
								v_html += "				<div class='list_box'>																								\n";
								v_html += v_div2;
								v_html += "				</div>																												\n";
								v_html += "				<div class='list_box'>																								\n";
								v_html += v_div3;
								v_html += "				</div>																												\n";
								v_html += "				<div class='list_box'>																								\n";
								v_html += v_div4;
								v_html += "				</div>																												\n";
								v_html += "				<div class='list_box'>																								\n";
								v_html += v_div5;
								v_html += "				</div>																												\n";
								v_html += "				<div class='list_box'>																								\n";
								v_html += v_div6;
								v_html += "				</div>																												\n";
								v_html += "			</li>																													\n";

								v_div1 = "";
								v_div2 = "";
								v_div3 = "";
								v_div4 = "";
								v_div5 = "";
								v_div6 = "";
							}
						}

						v_html += "		</ul>																																\n";
						v_html += "	</div>																																	\n";
					}
			%>
					<%= v_html %>
			<%
				}
			%>
				</div>
			</div>

			<!-- layer in layer -->
			<div class="opacity_layer_bg02"></div>
			<div class="layer_wrap sub_layer" style="width: 900px;">
				<div class="layer_top">
					<button type="button" title="�ݱ�" class="btn_layerClose" onclick="layerClose();">�ݱ�</button>
				</div>
				<div class="layer_body">
					<div class="class_info_box" style="margin-top: 30px;">
						<div class="d-flex">
							<span class="snail_box"><img id="snimg" src="" alt=""></span>
							<div class="info_box">
								<p class="guide_txt depth" style="margin-bottom: 10px; text-align: left;"></p>
								<p class="title_txt" style="margin-bottom: 8px;"></p>
								<div><span class="bg_green fc_white tag taglv"></span><span class="tag linegray tagtype"></span></div>
								<a href="" class="d-block guide_txt eduview" style="text-align: right; margin-top: 17px;">���� �̸����� ></a>
							</div>
						</div>
						<div class ="bottom_list_box" style="margin-top: 40px;">
							<h5 id="h5" style="padding-bottom: 10px; border-bottom: 1px solid #ededed;">�������� (4)</h5>
							<ul class="linkCrs">
								<li><span class="fc_red">[2020]</span>����ο� ��Ʃ����Ȱ_��� �׷��� ��<span class="bg_green fc_white tag" style="margin-left: 3px;">�ʱ�</span><span class="tag linegray">����</span></li>
								<li><span class="fc_red">[2020]</span>����ο� ��Ʃ����Ȱ_��� �׷��� ��<span class="bg_beage fc_white tag" style="margin-left: 3px;">�߱�</span><span class="tag linegray">����</span></li>
								<li><span class="fc_red">[2020]</span>����ο� ��Ʃ����Ȱ_��� �׷��� ��<span class="bg_orange fc_white tag" style="margin-left: 3px;">���</span><span class="tag linegray">����</span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>

</body>
</html>
