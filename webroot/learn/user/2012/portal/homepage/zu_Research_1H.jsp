<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<%
    RequestBox box		= (RequestBox)request.getAttribute("requestbox");
	String p_link		= box.getStringDefault("p_link","H");
	String p_gubun		= box.getStringDefault("p_gubun","S");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>사이버콘텐츠아카데미 교육 수요조사 (사이트가입자)</title>
<link href="/css/2012/research_style.css" type="text/css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script> 
<script type="text/javascript">
	

	$(document).ready(function(){
		var useragent = navigator.userAgent;
		if(useragent.indexOf("MSIE 8.0") != -1 || useragent.indexOf("MSIE 9.0") != -1 || useragent.indexOf("MSIE 7.0") != -1){
			$("#br_show").attr("style", "display:none;");
			$("#br_show2").attr("style", "display:none;");
		}
		
		$("#q_ans_2 li input").click(function(){
			var t = $("input[name=q_ans_2]:checked").val();
			if(t == "1" || t == "2"){
				$("#question_3").attr("style","display:block;");
				$("#question_4").attr("style","display:block;");
				$("#question_5").attr("style","display:block;");
			}else{
				$("#question_3").attr("style","display:none;");
				$("#question_4").attr("style","display:none;");
				$("#question_5").attr("style","display:none;");
				$("input[name=q_ans_3]").attr('checked', false);
				$("input[name=q_ans_4]").attr('checked', false);
				$("input[name=q_ans_5]").attr('checked', false);
				$("input[name=r_ans_4]").val("");
				$("input[name=r_ans_5]").val("");
			}
		});

		$("#q_ans_6 li input").click(function(){
			var t = $("input[name=q_ans_6]:checked").val();
			if(t == "1" || t == "2"){
				$("#question_7").attr("style","display:block;");// 7번문제 보여줌
				$("#question_8").attr("style","display:none;");// 8번문제 숨김
				$("#question_9").attr("style","display:none;");// 9번문제 숨김
				$("input[name=q_ans_8]").attr('checked', false);// 8번 내용 지움
				$("input[name=r_ans_8]").val("");// 8번 내용 지움
				$("input[name=r_ans_9]").val("");// 9번 내용 지움
			}else{
				$("#question_8").attr("style","display:block;");// 8번문제 보여줌
				$("#question_7").attr("style","display:none;");// 7번문제 숨김
				$("input[name=r_ans_7]").val("");// 7번 내용 지움
			}
		});

		$("#q_ans_8 li input[type=radio]").click(function(){
			var t = $("input[name=q_ans_8]:checked").val();
			if(t == "4"){
				$("#question_9").attr("style","display:block;");// 9번문제 보여줌
			}else{
				$("#question_9").attr("style","display:none;");// 9번문제 보여줌
				$("input[name=r_ans_9]").val("");// 9번 내용 지움
			}
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
		
		var ans_1 = "";		var ans_2 = "";		var ans_3 = "";			var ans_4 = "";			var ans_5 = "";			var ans_6 = "";		var ans_7 = "";
		var ans_8 = "";		var ans_9 = "";		var ans_10 = "";		var ans_11 = "";		var ans_12 = "";		var ans_13 = "";
		
		//1번 정답 담기
		for(var i = 0; i< $("[name='q_ans_1']:checked").length; i++){
			ans_1 += "," + $("[name='q_ans_1']:checked").eq(i).val();
		}
		if(ans_1 != ""){
			ans_1 = ans_1.substring(1);
			if($("[name='r_ans_1']").val() != ""){
				ans_1 += "-" + $("[name='r_ans_1']").val();
			}
		}
		
		ans_2 = $("input[name=q_ans_2]:checked").val();//2번 정답 담기
		
		ans_3 = $("input[name=q_ans_3]:checked").val();//3번 정답 담기

		ans_4 = $("input[name=q_ans_4]:checked").val();//4번 정답 담기
		if($("[name='r_ans_4']").val() != ""){
			ans_4 += "-" + $("[name='r_ans_4']").val();
		}
		
		ans_5 = $("input[name=q_ans_5]:checked").val();//5번 정답 담기
		if($("[name='r_ans_5']").val() != ""){
			ans_5 += "-" + $("[name='r_ans_5']").val();
		}
		
		ans_6 = $("input[name=q_ans_6]:checked").val();//6번 정답 담기
		
		ans_7 = $("input[name=r_ans_7]").val();//7번 정답 담기
		
		ans_8 = $("input[name=q_ans_8]:checked").val();//8번 정답 담기
		if($("[name='r_ans_8']").val() != ""){
			ans_8 += "-" + $("[name='r_ans_8']").val();
		}
		
		ans_9 = $("input[name=r_ans_9]").val();//9번 정답 담기
		
		ans_10 = $("input[name=q_ans_10]:checked").val();//10번 정답 담기
		if($("[name='r_ans_10']").val() != ""){
			ans_10 += "-" + $("[name='r_ans_10']").val();
		}
		
		ans_11 = $("input[name=r_ans_11]").val();//11번 정답 담기


		for(var i = 0; i< $("[name='q_ans_12']:checked").length; i++){//12번 정답 담기
			ans_12 += "," + $("[name='q_ans_12']:checked").eq(i).val();
		}
		if(ans_12 != ""){
			ans_12 = ans_12.substring(1);
			if($("[name='r_ans_12']").val() != ""){
				ans_12 += "-" + $("[name='r_ans_12']").val();
			}
		}
		
		ans_13 = $("input[name=r_ans_13]").val();//13번 정답 담기

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

		if(value_checking()){
			f.submit();
		}else{
			alert("미응답 항목이 있습니다.");
		}
	}

	function value_checking(){
		var f = document.form1;
		
		if(f.p_ans_1.value == "" || f.p_ans_1.value == "undefined"){return false;}
		if(f.p_ans_2.value == "" || f.p_ans_2.value == "undefined"){return false;}

		if(f.p_ans_6.value == "" || f.p_ans_6.value == "undefined"){return false;}

		if(f.p_ans_10.value == "" || f.p_ans_10.value == "undefined"){return false;}
		if(f.p_ans_11.value == "" || f.p_ans_11.value == "undefined"){return false;}
		if(f.p_ans_12.value == "" || f.p_ans_12.value == "undefined"){return false;}
		if(f.p_ans_13.value == "" || f.p_ans_13.value == "undefined"){return false;}

		if(f.p_ans_2.value == "1" || f.p_ans_2.value == "2"){
			if(f.p_ans_3.value == "" || f.p_ans_3.value == "undefined"){return false;}
			if(f.p_ans_4.value == "" || f.p_ans_4.value == "undefined"){return false;}
			if(f.p_ans_5.value == "" || f.p_ans_5.value == "undefined"){return false;}
		}

		if(f.p_ans_6.value == "1" || f.p_ans_6.value == "2"){
			if(f.p_ans_7.value == "" || f.p_ans_7.value == "undefined"){return false;}
		}else if(f.p_ans_6.value == "3" || f.p_ans_6.value == "4"){
			if(f.p_ans_8.value == "" || f.p_ans_8.value == "undefined"){
				return false;
			}else if(f.p_ans_8.value == "4"){
				if(f.p_ans_9.value == "" || f.p_ans_9.value == "undefined"){return false;}
			}
		}

		return true;
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
						<span>평소 콘텐츠(사업)와 관련된 정보를 어떤 경로를 통해 얻습니까? (중복선택 가능)</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="1" /> TV / 라디오</li>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="2" /> 신문 / 잡지 / 광고</li>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="3" /> 인터넷 (포털, 블로그, 트위터 등 SNS)</li>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="4" /> 한국콘텐츠진흥원 홈페이지</li>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="5" /> 한국콘텐츠진흥원 홍보물, 소식지 등</li>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="6" /> 한국콘텐츠진흥원 행사, 세미나 등</li>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="7" /> 주위 사람</li>
						<li class="wf"><input name="q_ans_1" type="checkbox" value="8" id="etc_che_1" onclick="javascript:toogle_checkbox('1');"/> 기타 <input name="r_ans_1" type="text" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_2">
					<p class="question_tit">
						<span>평소 방송, 게임, 문화 등 콘텐츠(산업)에 대한 교육에 대한 어느 정도 관심이 있습니까?</span>
					</p>
					<ul id="q_ans_2">
						<li class="wf"><input name="q_ans_2" type="radio" value="1" > 매우 관심있다</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="2" > 약간 관심있다</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="3" > 별로 관심없다</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="4" > 전혀 관심없다</li>
					</ul>
				</div>


				<div class="question" id="question_3" style="display:none;">
					<p class="question_tit">
						<span>사이버콘텐츠아카데미에서는 다양한 분야의 콘텐츠를 개발하고, 이를 사이버교육으로 제공하고 있습니다. 향후 사이버콘텐츠아카데미에서 제공하는 강좌를 듣게 된다면 어느 분야를 선택하시겠습니까?</span>
					</p>
		            <ul>
						<li class="wf"><input name="q_ans_3" type="radio" value="1" > 출판 / 인쇄 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="2" > 만화 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="3" > 애니메이션 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="4" > 게임 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="5" > 영화 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="6" > 방송 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="7" > 광고 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="8" > 캐릭터 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="9" > 지식정보 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="10" > 음악 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="11" > 공연 분야</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="12" > 콘텐츠 솔루션 분야</li>
					</ul>
				</div>


				<div class="question" id="question_4" style="display:none;">
					<p class="question_tit">
						<span>선택하신 콘텐츠 분야에서 어떤 직무와 관련된 교육내용을 선택하시겠습니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_4" type="radio" value="1" onclick="javascript:toogle_radio('4','N');"  > 기획 / 창작 관련</li>
						<li class="wf"><input name="q_ans_4" type="radio" value="2" onclick="javascript:toogle_radio('4','N');"  > 제작 / 표현 관련</li>
						<li class="wf"><input name="q_ans_4" type="radio" value="3" onclick="javascript:toogle_radio('4','N');"  > 유통 / 마케팅 관련</li>
						<li class="wf"><input name="q_ans_4" type="radio" value="4" onclick="javascript:toogle_radio('4','N');"  > 경영 / 경영지원 관련</li>
						<li class="wf"><input name="q_ans_4" type="radio" value="5" onclick="javascript:toogle_radio('4','Y');"  > 기타 <input name="r_ans_4" type="text" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_5" style="display:none;">
					<p class="question_tit">
						<span>사이버콘텐츠아카데미에서 어떤 목적의 강좌를 제공하는 것이 바람직하다고 생각하십니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_5" type="radio" value="1" onclick="javascript:toogle_radio('5','N');" /> 재미있거나 실생활에 유익한 강좌</li>
						<li class="wf"><input name="q_ans_5" type="radio" value="2" onclick="javascript:toogle_radio('5','N');" /> 취업에 도움되는 강좌</li>
						<li class="wf"><input name="q_ans_5" type="radio" value="3" onclick="javascript:toogle_radio('5','N');" /> 자격증 취득에 도움되는 강좌</li>
						<li class="wf"><input name="q_ans_5" type="radio" value="4" onclick="javascript:toogle_radio('5','N');" /> 진학 혹은 학문적 지식습득에 도움되는 강좌</li>
						<li class="wf"><input name="q_ans_5" type="radio" value="5" onclick="javascript:toogle_radio('5','N');" /> 전문적인 업무 역량을 향상시키는 강좌</li>
						<li class="wf"><input name="q_ans_5" type="radio" value="6" onclick="javascript:toogle_radio('5','Y');" /> 기타 <input name="r_ans_5" type="text" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_6">
					<p class="question_tit">
						<span>현재 사이버콘텐츠아카데미(http://edu.kocca.or.kr)에서는 아래 표에 해당하는 분야의 온라인 교육 콘텐츠를 무료로 제공하고 있습니다. 응답자님께서는 향후 사이버콘텐츠아카데미에서 무료로 제공하는 정규강좌 및 열린강좌를 학습하실 의향이 있습니까?</span> 
					</p><span id="br_show"><br/><br/><br/></span>
					<div class="view_box">
						<span class="tit_center">&lt;정규강좌&gt;</span><br/>
						방송 : 입체영상 / 영상편집 / 콘텐츠 투자 / 콘텐츠 유통마케팅 / 방송영상 제작 / 방송영상 기획<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / 방송콘텐츠 스토리텔링 등<br/>
						게임 : 2D-3D 게임 그래픽 / 게임배경 / 게임 캐릭터 / 게임 이펙트 / 애니메이션 개론 / 게임 아이디어<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / 게임 레벨 디자인 / 게임 시나리오 / 게임 마케팅 / 게임 알고리즘 / 게임기술이해 / 3D 프로그래밍<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / C# / DirectX / 스케일폼 등<br/>
						문화 : 스토리텔링 / 문화콘텐츠 기획 / 대중문화 / 음반 제작 / 공연 / 전시 / 축제 / 모바일앱<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / 모바일콘텐츠 개발 / 전자책 / 프레지 등<br/>
						<span class="tit_center">&lt;열린강좌&gt;</span><br/>
						콘텐츠 분야 전문가 특강, 콘텐츠 분야 트렌드 및 이슈
					</div>
					<ul id="q_ans_6">
						<li class="wf"><input name="q_ans_6" type="radio" value="1" > 매우 의향이 있다</li>
						<li class="wf"><input name="q_ans_6" type="radio" value="2" > 약간 의향이 있다</li>
						<li class="wf"><input name="q_ans_6" type="radio" value="3" > 별로 의향이 없다</li>
						<li class="wf"><input name="q_ans_6" type="radio" value="4" > 전혀 의향이 없다</li>
					</ul>
				</div>


				<div class="question" id="question_7" style="display:none;">
					<p class="question_tit">
						<span>현재 사이버콘텐츠아카데미에서 제공되고 있는 강좌, 혹은 제공되고 있지 않지만 학습하기를 원하는 내용이 있다면 구체적으로 적어주십시오.</span>
					</p>
					<ul>
						<li class="wf"><input name="r_ans_7" type="text" value="" size="95" /></li>
					</ul>
				</div>


				<div class="question" id="question_8" style="display:none;">
					<p class="question_tit">
						<span>사이버콘텐츠아카데미의 무료 강좌에 대한 수강 의향이 없는 이유는 무엇입니까?</span>
					</p>
					<ul id="q_ans_8">
						<li class="wf"><input name="q_ans_8" type="radio" value="1" onclick="javascript:toogle_radio('8','N');" /> 학업 / 직무와 관련이 없다</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="2" onclick="javascript:toogle_radio('8','N');" /> 취업에 도움이 되지 않는다</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="3" onclick="javascript:toogle_radio('8','N');" /> 사이버교육은 번거롭다</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="4" onclick="javascript:toogle_radio('8','N');" /> 관심있는 콘텐츠가 없다</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="5" onclick="javascript:toogle_radio('8','Y');" /> 기타 <input name="r_ans_8" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_9" style="display:none;">
					<p class="question_tit">
						<span>사이버콘텐츠아카데미에서 어떤 콘텐츠가 제공되면 수강하실 의향이 있는지 구체적인 학습내용이나 과목명을 작성해 주세요.</span>
					</p>
					<ul>
						<li class="wf"><input name="r_ans_9" type="text" value="" size="95" /></li>
					</ul>
				</div>

				<div class="question" id="question_10">
					<p class="question_tit">
						<span>향후 사이버콘텐츠아카데미에서 새로운 콘텐츠를 개발할 때 어떤 분을 강사 혹은 내용전문가로 선정하면 들으실 의향이 있으십니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_10" type="radio" value="1" onclick="javascript:toogle_radio('10','N');"/> 대학교, 연구원 등 학문적인 연구나 강의를 하시는 분</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="2" onclick="javascript:toogle_radio('10','N');"/> 콘텐츠 관련 직접 업무를 수행해온 업계 전문가 및 실무자</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="3" onclick="javascript:toogle_radio('10','N');"/> 자격증 취득에 도움이 될 수 있는 콘텐츠 관련 전문 강사</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="4" onclick="javascript:toogle_radio('10','N');"/> 한국콘텐츠진흥원 직원 중 강의능력이 뛰어나신 분</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="5" onclick="javascript:toogle_radio('10','Y');"/> 기타 <input name="r_ans_10" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_11">
					<p class="question_tit">
						<span>응답자님께서 강의를 듣고 싶은 강사 혹은 내용전문가가 있다면 구체적으로 적어주십시오.</span>
					</p><span id="br_show2"><br/></span>
					<div class="view_box">
						<span class="tit_center">&lt;예시&gt;</span><br/>
						‘이끼’, ‘미생’ 윤태호 작가 / ‘은밀하게 위대하게’ 최종훈 작가 / 엔씨소프트 김택진 대표 /‘구가의 서’ 박태영 PD <br/>
						/ ‘나인’ 조봉한 촬영감독 / 무한도전 김태호 PD 등
					</div>
					<ul>
						<li class="wf"><input name="r_ans_11" type="text" value="" size="95"></li>
					</ul>
				</div>

				<div class="question" id="question_12">
					<p class="question_tit">
						<span>향후 더 많은 사람이 사이버콘텐츠아카데미를 찾아오도록 하기 위해서 가장 중점을 두고 해야 할 일은 무엇이라고 생각하십니까? (중복선택 가능)</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_12" type="checkbox" value="1" /> 전문적인 내용으로 구성된 우수한 콘텐츠 확대</li>
						<li class="wf"><input name="q_ans_12" type="checkbox" value="2" /> 교육내용에 대한 안내 및 홍보 확대</li>
						<li class="wf"><input name="q_ans_12" type="checkbox" value="3" /> 간편하고 편리한 학습 사이트 구축</li>
						<li class="wf"><input name="q_ans_12" type="checkbox" value="4" /> 콘텐츠 산업에 대한 다양한 정보 제공</li>
						<li class="wf"><input name="q_ans_12" type="checkbox" value="5" /> 인적네트워크 형성을 위한 커뮤니티 활동 강화</li>
						<li class="wf"><input name="q_ans_12" type="checkbox" value="6" /> 콘텐츠 산업의 직무/직업별로 특화된 학습 및 경력개발 경로 제공</li>
						<li class="wf"><input name="q_ans_12" type="checkbox" value="7" id="etc_che_12" onclick="javascript:toogle_checkbox('12');" /> 기타 <input name="r_ans_12" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_13">
					<p class="question_tit">
						<span>마지막으로 사이버콘텐츠아카데미에 보다 많은 사람들이 방문하여 콘텐츠 관련 지식을 학습하도록 하기 위한 아이디어가 있다면, 무엇이든 상관없으니 자유롭게 말씀해 주십시오.</span>
					</p>
					<ul>
						<li class="wf"><input name="r_ans_13" type="text" value="" size="95"/></li>
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
