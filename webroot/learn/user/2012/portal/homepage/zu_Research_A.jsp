<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<%
    RequestBox box		= (RequestBox)request.getAttribute("requestbox");
	String p_link		= box.getStringDefault("p_link","H");
	String p_gubun		= box.getStringDefault("p_gubun","A");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>한국콘텐츠진흥원 교육서비스 만족도조사 (온라인 교육생)</title>
<link href="/css/2012/research_style.css" type="text/css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script> 
<script type="text/javascript">
	$(document).ready(function(){
		var useragent = navigator.userAgent;
		if(useragent.indexOf("MSIE 8.0") != -1 || useragent.indexOf("MSIE 9.0") != -1 || useragent.indexOf("MSIE 7.0") != -1){
			$("#br_show1").attr("style", "display:none;");
			$("#br_show2").attr("style", "display:none;");
			$("#br_show3").attr("style", "display:none;");
			$("#br_show4").attr("style", "display:none;");
		}

		$("#q_ans_1 li input").click(function(){
			var t = $("input[name=q_ans_1]:checked").val();
			if(t == "1" || t == "2"){
				$("#question_2").attr("style","display:block;");
			}else{
				$("#question_2").attr("style","display:none;");
				$("input[name=q_ans_2]").attr('checked', false);
				toogle_radio('2','N');
			}
		});

		$("#q_ans_11 li input").click(function(){
			var t = $("input[name=q_ans_11]:checked").val();
			if(t == "1" || t == "2"){
				$("#question_12").attr("style","display:block;");
			}else{
				$("#question_12").attr("style","display:none;");
				$("input[name=q_ans_12]").attr('checked', false);
				toogle_radio('12','N');
			}
		});

		$("#etc_che_15").click(function(){
			toogle_checkbox('15');
		});
		
	});

	function toogle_radio(exnum, sta){
		if(sta == "N"){
			$("input[name=r_ans_"+exnum+"]").val("");
			$("input[name=r_ans_"+exnum+"]").attr("readonly", true); 
		}else{
			$("input[name=r_ans_"+exnum+"]").removeAttr("readonly");
		}
	}

	function toogle_checkbox(exnum){
		var t = "";
		t = $("#etc_che_"+exnum).is(":checked");
		if(t){
			$("input[name=r_ans_"+exnum+"]").removeAttr("readonly");				
		}else{
			$("input[name=r_ans_"+exnum+"]").val("");
			$("input[name=r_ans_"+exnum+"]").attr("readonly", true);
		}
	}

	function next(){
		
		var f = document.form1;
		var ans_1 = "";		var ans_2 = "";		var ans_3 = "";			var ans_4 = "";			var ans_5 = "";			var ans_6 = "";		var ans_7 = "";		var ans_8 = "";
		var ans_9 = "";		var ans_10 = "";	var ans_11 = "";		var ans_12 = "";		var ans_13 = "";		var ans_14 = "";	var ans_15 = "";

		ans_1 = $("input[name=q_ans_1]:checked").val();//1번 정답 담기
		ans_2 = $("input[name=q_ans_2]:checked").val();//2번 정답 담기
			if($("[name='r_ans_2']").val() != ""){ans_2 += "-" + $("[name='r_ans_2']").val();}
		ans_3 = $("input[name=q_ans_3]:checked").val();//3번 정답 담기
		ans_4 = $("input[name=q_ans_4]:checked").val();//4번 정답 담기
		ans_5 = $("input[name=q_ans_5]:checked").val();//5번 정답 담기
		ans_6 = $("input[name=q_ans_6]:checked").val();//6번 정답 담기
		ans_7 = $("input[name=q_ans_7]:checked").val();//7번 정답 담기
		ans_8 = $("input[name=q_ans_8]:checked").val();//8번 정답 담기
		ans_9 = $("input[name=q_ans_9]:checked").val();//9번 정답 담기
		ans_10 = $("input[name=q_ans_10]:checked").val();//10번 정답 담기
		ans_11 = $("input[name=q_ans_11]:checked").val();//11번 정답 담기
		ans_12 = $("input[name=q_ans_12]:checked").val();//12번 정답 담기
			if($("[name='r_ans_12']").val() != ""){ans_12 += "-" + $("[name='r_ans_12']").val();}
		ans_13 = $("input[name=q_ans_13]:checked").val();//13번 정답 담기
		ans_14 = $("input[name=q_ans_14]:checked").val();//14번 정답 담기
		for(var i = 0; i< $("[name='q_ans_15']:checked").length; i++){// 15번 정답 담기
			ans_15 += "," + $("[name='q_ans_15']:checked").eq(i).val();
		}
		if(ans_15 != ""){
			ans_15 = ans_15.substring(1);
			if($("[name='r_ans_15']").val() != ""){
				ans_15 += "-" + $("[name='r_ans_15']").val();
			}
		}
		
		f.p_ans_1.value = ans_1;
		f.p_ans_2.value = ans_2;
		f.p_ans_3.value = ans_3;
		f.p_ans_4.value = ans_4;
		f.p_ans_5.value = ans_5;
		f.p_ans_6.value = ans_6;
		f.p_ans_7.value = ans_7;
		f.p_ans_8.value = ans_8;
		f.p_ans_9.value = ans_9;
		f.p_ans_10.value = ans_10;
		f.p_ans_11.value = ans_11;
		f.p_ans_12.value = ans_12;
		f.p_ans_13.value = ans_13;
		f.p_ans_14.value = ans_14;
		f.p_ans_15.value = ans_15;
		if(value_checking()){
			f.submit();
		}else{
			alert("미응답 항목이 있습니다.");
		}
	}

	function value_checking(){
		var f = document.form1;
		
		if(f.p_ans_1.value == "" || f.p_ans_1.value == "undefined"){$("#question_1").focus(); return false;}
		if(f.p_ans_1.value == "1" || f.p_ans_1.value == "2"){
			if(f.p_ans_2.value == "" || f.p_ans_2.value == "undefined"){$("#question_2").focus(); return false;}
		}
		if(f.p_ans_3.value == "" || f.p_ans_3.value == "undefined"){$("#question_3").focus(); return false;}
		if(f.p_ans_4.value == "" || f.p_ans_4.value == "undefined"){$("#question_4").focus(); return false;}
		if(f.p_ans_5.value == "" || f.p_ans_5.value == "undefined"){$("#question_5").focus(); return false;}
		if(f.p_ans_6.value == "" || f.p_ans_6.value == "undefined"){$("#question_6").focus(); return false;}
		if(f.p_ans_7.value == "" || f.p_ans_7.value == "undefined"){$("#question_7").focus(); return false;}
		if(f.p_ans_8.value == "" || f.p_ans_8.value == "undefined"){$("#question_8").focus(); return false;}
		if(f.p_ans_9.value == "" || f.p_ans_9.value == "undefined"){$("#question_9").focus(); return false;}
		if(f.p_ans_10.value == "" || f.p_ans_10.value == "undefined"){$("#question_10").focus(); return false;}
		if(f.p_ans_11.value == "" || f.p_ans_11.value == "undefined"){$("#question_11").focus(); return false;}
		if(f.p_ans_11.value == "1" || f.p_ans_11.value == "2"){
			if(f.p_ans_12.value == "" || f.p_ans_12.value == "undefined"){$("#question_12").focus(); return false;}
		}
		if(f.p_ans_13.value == "" || f.p_ans_13.value == "undefined"){$("#question_13").focus(); return false;}
		if(f.p_ans_14.value == "" || f.p_ans_14.value == "undefined"){$("#question_14").focus(); return false;}
		if(f.p_ans_15.value == "" || f.p_ans_15.value == "undefined"){$("#question_15").focus(); return false;}
		if($("[name='q_ans_15']:checked").length < 2){$("#question_15").focus(); return false;}
		
		
		return true;
	}

	function checkboxLimit(frm,obj,num){
		var str = obj.name;
		var len = frm.length;
		var chk = 0;
		for(i=0; i<len; i++){
			if(frm[i].name == str && frm[i].checked == true){
				chk++;
			}
			if(chk > num){
				alert(num + "개 까지 선택할 수 있습니다.");
				obj.checked = false;
				break;
			}
		}
	}

</script>
</head>
<BODY>
<form name="form1" id="form1" method="post" action="/servlet/controller.homepage.MainServlet">
<input type="hidden" name="p_gubun" value="<%=p_gubun %>"/>
<input type="hidden" name="p_link" value="<%=p_link %>"/>
<input type="hidden" name="p_process" value="research_2"/>
<input type="hidden" name="p_ans_1"/>
<input type="hidden" name="p_ans_2"/>
<input type="hidden" name="p_ans_3"/>
<input type="hidden" name="p_ans_4"/>
<input type="hidden" name="p_ans_5"/>
<input type="hidden" name="p_ans_6"/>
<input type="hidden" name="p_ans_7"/>
<input type="hidden" name="p_ans_8"/>
<input type="hidden" name="p_ans_9"/>
<input type="hidden" name="p_ans_10"/>
<input type="hidden" name="p_ans_11"/>
<input type="hidden" name="p_ans_12"/>
<input type="hidden" name="p_ans_13"/>
<input type="hidden" name="p_ans_14"/>
<input type="hidden" name="p_ans_15"/>
<div id="wrap">
	<!-- header -->
	<div id="header">
		<h1><img src="/images/2012/main/research/logo.gif" /></h1>
	</div>
	<!-- //header -->

	<!-- content -->
	<div id="con">
		<div class="box_header">
			<div class="box">
				<div class="question" id="question_1">
					<p class="question_tit">
						<span>학습자님께서는 한국콘텐츠진흥원의 인력양성사업에 대해 어느 정도 알고 계십니까?</span>
					</p>
					<ul id="q_ans_1">
						<li class="wf"><input name="q_ans_1" type="radio" value="1"/> 잘 알고 있다.</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="2"/> 약간 알고 있는 편이다.</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="3"/> 잘 모른다.</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="4"/> 전혀 모른다.</li>
					</ul>
				</div>

				<div class="question" id="question_2" style="display: none;">
					<p class="question_tit">
						<span>주로 어떤 경로를 통해 듣거나 알게 되었습니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_2" type="radio" value="1" onclick="javascript:toogle_radio('2','N');" /> TV, 라디오, 신문 등의 매체</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="2" onclick="javascript:toogle_radio('2','N');" /> 인터넷 (한국콘텐츠진흥원 홈페이지 제외)</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="3" onclick="javascript:toogle_radio('2','N');" /> 한국콘텐츠진흥원 홈페이지</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="4" onclick="javascript:toogle_radio('2','N');" /> 친구, 동료, 친척 등 주위 사람</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="5" onclick="javascript:toogle_radio('2','N');" /> 직접 인력양성사업(교육) 참여를 통해</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="6" onclick="javascript:toogle_radio('2','Y');" /> 기타 <input name="r_ans_2" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>

				<div class="question" id="question_3">
					<p class="question_tit">
						<span>다음은 사이버콘텐츠아카데미 교육 서비스 내용에 대한 만족도입니다. <br>1점에서 5점 중 학습자님께서 동의하시는 정도가 클수록 높은 점수로 골라주십시오.</span>
					</p><span id="br_show1"><br/></span>
		            <table cellpadding="0" cellspacing="0" border="0" width="100%" style="border-style: solid;border-color: gray;border-width: 1px;">
						<colgroup><col width=""/><col width="50px"/><col width="50px"/><col width="50px"/><col width="50px"/><col width="50px"/></colgroup>
						<thead>
							<tr height="70px" bgcolor="#efefef">
								<th>구 분</th>
								<th>전혀<br>그렇지<br>않다</th>
								<th>그렇지<br>않은<br>편이다</th>
								<th>보통<br>이다</th>
								<th>그런<br>편이다</th>
								<th>매우<br>그렇다</th>
							</tr>
						</thead>
						<tbody>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 교육내용이 콘텐츠 관련 지식 습득에 도움이 된다</td>
								<td><input name="q_ans_3" type="radio" value="1" /></td>
								<td><input name="q_ans_3" type="radio" value="2" /></td>
								<td><input name="q_ans_3" type="radio" value="3" /></td>
								<td><input name="q_ans_3" type="radio" value="4" /></td>
								<td><input name="q_ans_3" type="radio" value="5" /></td>
							</tr>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 강사는 교육에 필요한 지식과 강의능력을 갖추고 있다</td>
								<td><input name="q_ans_4" type="radio" value="1" /></td>
								<td><input name="q_ans_4" type="radio" value="2" /></td>
								<td><input name="q_ans_4" type="radio" value="3" /></td>
								<td><input name="q_ans_4" type="radio" value="4" /></td>
								<td><input name="q_ans_4" type="radio" value="5" /></td>
							</tr>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 교육내용이 체계적이다</td>
								<td><input name="q_ans_5" type="radio" value="1" /></td>
								<td><input name="q_ans_5" type="radio" value="2" /></td>
								<td><input name="q_ans_5" type="radio" value="3" /></td>
								<td><input name="q_ans_5" type="radio" value="4" /></td>
								<td><input name="q_ans_5" type="radio" value="5" /></td>
							</tr>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 온라인 교육환경 개선을 위해 노력한다</td>
								<td><input name="q_ans_6" type="radio" value="1" /></td>
								<td><input name="q_ans_6" type="radio" value="2" /></td>
								<td><input name="q_ans_6" type="radio" value="3" /></td>
								<td><input name="q_ans_6" type="radio" value="4" /></td>
								<td><input name="q_ans_6" type="radio" value="5" /></td>
							</tr>
						</tbody>
						<tfoot>
							<tr height="1px">
								<td colspan="6"></td>
							</tr>
						</tfoot>
					</table> <span id="br_show2"><br/></span><br/>
				</div>
				
				<div class="question" id="question_4">
					<p class="question_tit">
						<span>다음은 사이버콘텐츠아카데미 교육 서비스 이용과정 및 이용환경에 대한 만족도입니다. <br>1점에서 5점 중 학습자님께서 동의하시는 정도가 클수록 높은 점수로 골라주십시오.</span>
					</p><span id="br_show3"><br/></span>
		            <table cellpadding="0" cellspacing="0" border="0" width="100%" style="border-style: solid;border-color: gray;border-width: 1px;">
						<colgroup><col width=""/><col width="50px"/><col width="50px"/><col width="50px"/><col width="50px"/><col width="50px"/></colgroup>
						<thead>
							<tr height="70px" bgcolor="#efefef">
								<th>구 분</th>
								<th>전혀<br>그렇지<br>않다</th>
								<th>그렇지<br>않은<br>편이다</th>
								<th>보통<br>이다</th>
								<th>그런<br>편이다</th>
								<th>매우<br>그렇다</th>
							</tr>
						</thead>
						<tbody>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 교육과정 신청 및 선택 등 이용이 편리하다 </td>
								<td><input name="q_ans_7" type="radio" value="1" /></td>
								<td><input name="q_ans_7" type="radio" value="2" /></td>
								<td><input name="q_ans_7" type="radio" value="3" /></td>
								<td><input name="q_ans_7" type="radio" value="4" /></td>
								<td><input name="q_ans_7" type="radio" value="5" /></td>
							</tr>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 교육생의 요구사항을 이해하려고 노력한다</td>
								<td><input name="q_ans_8" type="radio" value="1" /></td>
								<td><input name="q_ans_8" type="radio" value="2" /></td>
								<td><input name="q_ans_8" type="radio" value="3" /></td>
								<td><input name="q_ans_8" type="radio" value="4" /></td>
								<td><input name="q_ans_8" type="radio" value="5" /></td>
							</tr>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 교육 관련 정보를 쉽게 찾거나 얻을 수 있다</td>
								<td><input name="q_ans_9" type="radio" value="1" /></td>
								<td><input name="q_ans_9" type="radio" value="2" /></td>
								<td><input name="q_ans_9" type="radio" value="3" /></td>
								<td><input name="q_ans_9" type="radio" value="4" /></td>
								<td><input name="q_ans_9" type="radio" value="5" /></td>
							</tr>
							<tr height="30px" valign="middle">
								<td align="left">&nbsp; 사이트 메뉴는 교육에 적합하게 배치되어 있다</td>
								<td><input name="q_ans_10" type="radio" value="1" /></td>
								<td><input name="q_ans_10" type="radio" value="2" /></td>
								<td><input name="q_ans_10" type="radio" value="3" /></td>
								<td><input name="q_ans_10" type="radio" value="4" /></td>
								<td><input name="q_ans_10" type="radio" value="5" /></td>
							</tr>
						</tbody>
						<tfoot>
							<tr height="1px">
								<td colspan="6"></td>
							</tr>
						</tfoot>
					</table> <span id="br_show4"><br/></span><br/>
				</div>

				<div class="question" id="question_11">
					<p class="question_tit">
						<span>사이버콘텐츠 아카데미에서 제공하는 교육내용, 이용절차, 이용환경 등을 모두 고려했을 때, 온라인 교육 서비스에 대해 전반적으로 어느 정도 만족하십니까?</span>
					</p>
					<ul id="q_ans_11">
						<li class="wf"><input name="q_ans_11" type="radio" value="1" > 매우 불만족한다</li>
						<li class="wf"><input name="q_ans_11" type="radio" value="2" > 약간 불만족한다</li>
						<li class="wf"><input name="q_ans_11" type="radio" value="3" > 보통이다</li>
						<li class="wf"><input name="q_ans_11" type="radio" value="4" > 약간 만족한다</li>
						<li class="wf"><input name="q_ans_11" type="radio" value="5" > 매우 만족한다</li>
					</ul>
				</div>

				<div class="question" id="question_12" style="display: none;">
					<p class="question_tit">
						<span>학습하신 사이버 교육에서 가장 불만인 점은 무엇입니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_12" type="radio" value="1" onclick="javascript:toogle_radio('12','N');"/> 정확하고 오래된 학습 내용</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="2" onclick="javascript:toogle_radio('12','N');"/> 낮은 강사 수준</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="3" onclick="javascript:toogle_radio('12','N');"/> 콘텐츠 오류로 인한 학습 중단</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="4" onclick="javascript:toogle_radio('12','N');"/> 불친절한 교육 운영</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="5" onclick="javascript:toogle_radio('12','N');"/> 복잡한 홈페이지 구조</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="6" onclick="javascript:toogle_radio('12','Y');"/> 기타 <input name="r_ans_12" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>

				<div class="question" id="question_13">
					<p class="question_tit">
						<span>학습자님께서 들이신 시간, 노력, 비용 등을 고려하여 기대하셨던 것에 비해 온라인 교육 서비스에 대해 어느 정도 만족하십니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_13" type="radio" value="1" > 매우 불만족한다</li>
						<li class="wf"><input name="q_ans_13" type="radio" value="2" > 약간 불만족한다</li>
						<li class="wf"><input name="q_ans_13" type="radio" value="3" > 보통이다 (기대와 비슷하다)</li>
						<li class="wf"><input name="q_ans_13" type="radio" value="4" > 약간 만족한다</li>
						<li class="wf"><input name="q_ans_13" type="radio" value="5" > 매우 만족한다</li>
					</ul>
				</div>
				
				<div class="question" id="question_14">
					<p class="question_tit">
						<span>만약 주위에 콘텐츠에 관심있는 친구나 동료가 있다면, 학습자님께서는 사이버콘텐츠 아카데미를 주위 사람들에게 추천할 의향은 어느 정도 있습니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_14" type="radio" value="1" > 전혀 추천의향이 없다</li>
						<li class="wf"><input name="q_ans_14" type="radio" value="2" > 별로 추천의향이 없다</li>
						<li class="wf"><input name="q_ans_14" type="radio" value="3" > 보통이다</li>
						<li class="wf"><input name="q_ans_14" type="radio" value="4" > 가급적 추천하겠다</li>
						<li class="wf"><input name="q_ans_14" type="radio" value="5" > 적극 추천하겠다</li>
					</ul>
				</div>
				
				<div class="question" id="question_15">
					<p class="question_tit">
						<span>향후 더 많은 사람들이 사이버콘텐츠 아카데미를 찾아오도록 하기 위해서 가장 중점을 두고 해야 할 일은 무엇이라고 생각하십니까? <font color="red">(2개 선택)</font></span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_15" type="checkbox" value="1" onclick="checkboxLimit(this.form,this,2)" /> 전문적인 내용으로 구성된 우수한 콘텐츠 확대</li>
						<li class="wf"><input name="q_ans_15" type="checkbox" value="2" onclick="checkboxLimit(this.form,this,2)" /> 교육내용에 대한 안내 및 홍보 확대</li>
						<li class="wf"><input name="q_ans_15" type="checkbox" value="3" onclick="checkboxLimit(this.form,this,2)" /> 간편하고 편리한 학습 사이트 구축</li>
						<li class="wf"><input name="q_ans_15" type="checkbox" value="4" onclick="checkboxLimit(this.form,this,2)" /> 콘텐츠 산업에 대한 다양한 정보 제공</li>
						<li class="wf"><input name="q_ans_15" type="checkbox" value="5" onclick="checkboxLimit(this.form,this,2)" /> 인적네트워크 형성을 위한 커뮤니티 활동 강화</li>
						<li class="wf"><input name="q_ans_15" type="checkbox" value="6" onclick="checkboxLimit(this.form,this,2)" /> 콘텐츠 산업의 직무/직업별로 특화된 학습 및 경력개발 경로 제공</li>
						<li class="wf"><input name="q_ans_15" type="checkbox" value="7" onclick="checkboxLimit(this.form,this,2)" id="etc_che_15" /> 기타 <input name="r_ans_15" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>
				
			</div>
		</div>
	</div>
	<div id="footer">
	<a href="javascript:next();"><img src="/images/2012/main/research/nextbutton.gif"/></a>
</div>
</div>
<!-- //content -->

</form>
</BODY>
</HTML>
