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
<title>사이버콘텐츠아카데미 교육 수요조사 (교육생)</title>
<link href="/css/2012/research_style.css" type="text/css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script> 
<script type="text/javascript">
	$(document).ready(function(){
		var useragent = navigator.userAgent;
		if(useragent.indexOf("MSIE 8.0") != -1 || useragent.indexOf("MSIE 9.0") != -1 || useragent.indexOf("MSIE 7.0") != -1){
			$("#br_show").attr("style", "display:none;");
			$("#br_show2").attr("style", "display:none;");
		}

		$("#q_ans_4 li input").click(function(){
			var t = "";
			t = $("#etc_che_4").is(":checked");
			if(t){
				$("#question_5").attr("style","display:block;");
			}else{
				$("#question_5").attr("style","display:none;");
				$("input[name=q_ans_5]").attr('checked', false);
			}
		});

		$("#q_ans_6 li input").click(function(){
			var t = $("input[name=q_ans_6]:checked").val();
			if(t == "1" || t == "2"){
				$("#question_7").attr("style","display:block;");
				$("#question_8").attr("style","display:none;");
				$("input[name=q_ans_8]").attr('checked', false);
			}else{
				$("#question_8").attr("style","display:block;");
				$("#question_7").attr("style","display:none;");
				$("input[name=q_ans_7]").attr('checked', false);
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
		var ans_1 = "";		var ans_2 = "";		var ans_3 = "";			var ans_4 = "";			var ans_5 = "";			var ans_6 = "";		var ans_7 = "";		var ans_8 = "";
		var ans_9 = "";		var ans_10 = "";	var ans_11 = "";		var ans_12 = "";		var ans_13 = "";		var ans_14 = "";	var ans_15;

		ans_1 = $("input[name=q_ans_1]:checked").val();//1번 정답 담기
		if($("[name='r_ans_1']").val() != ""){ans_1 += "-" + $("[name='r_ans_1']").val();}
		
		ans_2 = $("input[name=q_ans_2]:checked").val();//2번 정답 담기
		
		ans_3 = $("input[name=q_ans_3]:checked").val();//3번 정답 담기

		for(var i = 0; i< $("[name='q_ans_4']:checked").length; i++){// 4번 정답 담기
			ans_4 += "," + $("[name='q_ans_4']:checked").eq(i).val();
		}
		if(ans_4 != ""){ans_4 = ans_4.substring(1);}
		
		for(var i = 0; i< $("[name='q_ans_5']:checked").length; i++){// 5번 정답 담기
			ans_5 += "," + $("[name='q_ans_5']:checked").eq(i).val();
		}
		if(ans_5 != ""){ans_5 = ans_5.substring(1);}
		
		ans_6 = $("input[name=q_ans_6]:checked").val();//6번 정답 담기
		
		ans_7 = $("input[name=q_ans_7]:checked").val();//7번 정답 담기
		if($("[name='r_ans_7']").val() != ""){ans_7 += "-" + $("[name='r_ans_7']").val();}
		
		ans_8 = $("input[name=q_ans_8]:checked").val();//8번 정답 담기
		if($("[name='r_ans_8']").val() != ""){ans_8 += "-" + $("[name='r_ans_8']").val();}
		
		for(var i = 0; i< $("[name='q_ans_9']:checked").length; i++){// 9번 정답 담기
			ans_9 += "," + $("[name='q_ans_9']:checked").eq(i).val();
		}
		if(ans_9 != ""){ans_9 = ans_9.substring(1);}
		
		ans_10 = $("input[name=q_ans_10]:checked").val();//10번 정답 담기
		if($("[name='r_ans_10']").val() != ""){ans_10 += "-" + $("[name='r_ans_10']").val();}
		
		ans_11 = $("input[name=r_ans_11]").val();//11번 정답 담기

		ans_12 = $("input[name=q_ans_12]:checked").val();//12번 정답 담기
		if($("[name='r_ans_12']").val() != ""){ans_12 += "-" + $("[name='r_ans_12']").val();}
		
		ans_13 = $("input[name=r_ans_13]").val();//13번 정답 담기

		for(var i = 0; i< $("[name='q_ans_14']:checked").length; i++){// 14번 정답 담기
			ans_14 += "," + $("[name='q_ans_14']:checked").eq(i).val();
		}
		if(ans_14 != ""){
			ans_14 = ans_14.substring(1);
			if($("[name='r_ans_14']").val() != ""){
				ans_14 += "-" + $("[name='r_ans_14']").val();
			}
		}

		ans_15 = $("input[name=r_ans_15]").val();//15번 정답 담기

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
		
		if(f.p_ans_1.value == "" || f.p_ans_1.value == "undefined"){return false;}
		if(f.p_ans_2.value == "" || f.p_ans_2.value == "undefined"){return false;}
		if(f.p_ans_3.value == "" || f.p_ans_3.value == "undefined"){return false;}
		if(f.p_ans_4.value == "" || f.p_ans_4.value == "undefined"){return false;}

		if(f.p_ans_6.value == "" || f.p_ans_6.value == "undefined"){return false;}

		if(f.p_ans_9.value == "" || f.p_ans_9.value == "undefined"){return false;}
		if(f.p_ans_10.value == "" || f.p_ans_10.value == "undefined"){return false;}
		if(f.p_ans_11.value == "" || f.p_ans_11.value == "undefined"){return false;}
		if(f.p_ans_12.value == "" || f.p_ans_12.value == "undefined"){return false;}
		if(f.p_ans_13.value == "" || f.p_ans_13.value == "undefined"){return false;}
		if(f.p_ans_14.value == "" || f.p_ans_14.value == "undefined"){return false;}
		if(f.p_ans_15.value == "" || f.p_ans_15.value == "undefined"){return false;}

		if(f.p_ans_4.value.substring(0,1) == "1"){
			if(f.p_ans_5.value == "" || f.p_ans_5.value == "undefined"){return false;}
		}

		if(f.p_ans_6.value == "1" || f.p_ans_6.value == "2"){
			if(f.p_ans_7.value == "" || f.p_ans_7.value == "undefined"){return false;}
		}else if(f.p_ans_6.value == "3" || f.p_ans_6.value == "4"){
			if(f.p_ans_8.value == "" || f.p_ans_8.value == "undefined"){return false;}
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
						<span>어떤 경로를 통해 사이버콘텐츠아카데미에 대해 알게 되었습니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_1" type="radio" value="1" onclick="javascript:toogle_radio('1','N');" /> TV / 라디오</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="2" onclick="javascript:toogle_radio('1','N');" /> 신문 / 잡지 / 광고</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="3" onclick="javascript:toogle_radio('1','N');" /> 인터넷 (포털, 블로그, 트위터 등 SNS)</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="4" onclick="javascript:toogle_radio('1','N');" /> 한국콘텐츠진흥원 홈페이지</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="5" onclick="javascript:toogle_radio('1','N');" /> 한국콘텐츠진흥원 홍보물, 소식지 등</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="6" onclick="javascript:toogle_radio('1','N');" /> 한국콘텐츠진흥원 행사, 세미나 등</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="7" onclick="javascript:toogle_radio('1','N');" /> 주위 사람</li>
						<li class="wf"><input name="q_ans_1" type="radio" value="8" onclick="javascript:toogle_radio('1','Y');" /> 기타 <input name="r_ans_1" type="text" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_2">
					<p class="question_tit">
						<span>현재 어떤 콘텐츠 분야에 관심이 있습니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_2" type="radio" value="1" /> 출판 / 인쇄</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="2" /> 만화</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="3" /> 애니메이션</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="4" /> 게임</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="5" /> 영화</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="6" /> 방송</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="7" /> 광고</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="8" /> 캐릭터</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="9" /> 지식정보</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="10" /> 음악</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="11" /> 공연</li>
						<li class="wf"><input name="q_ans_2" type="radio" value="12" /> 콘텐츠 솔루션</li>
					</ul>
				</div>


				<div class="question" id="question_3">
					<p class="question_tit">
						<span>응답자님께서 사이버콘텐츠아카데미의 온라인 교육 콘텐츠를 학습함으로써 얻고자 하는 지식의 수준은 어느 정도입니까?</span>
					</p>
		            <ul>
						<li class="wf"><input name="q_ans_3" type="radio" value="1" > 교양 수준의 기초 지식</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="2" > 취업 준비 수준의 기본 지식</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="3" > 기본 직무 수행이 가능한 실무 지식</li>
						<li class="wf"><input name="q_ans_3" type="radio" value="4" > 전문 직무 수행이 가능한 실무 지식</li>
					</ul>
				</div>


				<div class="question" id="question_4">
					<p class="question_tit">
						<span>응답자님께서 사이버콘텐츠아카데미에서 학습하신 강좌는 무엇입니까? (중복선택 가능)</span>
					</p>
					<ul id="q_ans_4">
						<li class="wf"><input id="etc_che_4" name="q_ans_4" type="checkbox" value="1" /> 정규 강좌 (게임/문화/방송 분야 1개월 과정)</li>
						<li class="wf"><input name="q_ans_4" type="checkbox" value="2" /> 열린 강좌 (지식동영상/골드클래스 등 1차시 분량의 동영상 과정)</li>
					</ul>
				</div>


				<div class="question" id="question_5" style="display:none;">
					<p class="question_tit">
						<span>어떤 분야의 정규강좌를 학습하셨나요? (중복선택 가능)</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="1"/> 방송 분야 기획</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="2"/> 방송 분야 제작</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="3"/> 방송 분야 경영지원/유통</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="4"/> 게임 분야 기획</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="5"/> 게임 분야 제작</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="6"/> 게임 분야 경영지원/유통</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="7"/> 문화 분야 기획</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="8"/> 문화 분야 제작</li>
						<li class="wf"><input name="q_ans_5" type="checkbox" value="9"/> 문화 분야 경영지원/유통</li>
					</ul>
				</div>


				<div class="question" id="question_6">
					<p class="question_tit">
						<span>사이버콘텐츠 아카데미에서 제공하는 사이버교육에 대해 전반적으로 어느 정도 만족하십니까? </span>
					</p>
					<ul id="q_ans_6">
						<li class="wf"><input name="q_ans_6" type="radio" value="1" > 매우 만족한다</li>
						<li class="wf"><input name="q_ans_6" type="radio" value="2" > 약간 만족한다</li>
						<li class="wf"><input name="q_ans_6" type="radio" value="3" > 약간 불만이다</li>
						<li class="wf"><input name="q_ans_6" type="radio" value="4" > 매우 불만이다</li>
					</ul>
				</div>


				<div class="question" id="question_7" style="display:none;">
					<p class="question_tit">
						<span>학습하신 사이버 교육에서 가장 만족한 점은 무엇입니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_7" type="radio" value="1" onclick="javascript:toogle_radio('7','N');"/> 우수한 학습 내용</li>
						<li class="wf"><input name="q_ans_7" type="radio" value="2" onclick="javascript:toogle_radio('7','N');" /> 전문/유명 강사</li>
						<li class="wf"><input name="q_ans_7" type="radio" value="3" onclick="javascript:toogle_radio('7','N');" /> 무료 교육 제공</li>
						<li class="wf"><input name="q_ans_7" type="radio" value="4" onclick="javascript:toogle_radio('7','N');" /> 안정적인 교육 운영 서비스</li>
						<li class="wf"><input name="q_ans_7" type="radio" value="5" onclick="javascript:toogle_radio('7','N');" /> 편리한 학습 사이트</li>
						<li class="wf"><input name="q_ans_7" type="radio" value="6" onclick="javascript:toogle_radio('7','Y');" /> 기타 <input name="r_ans_7" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_8" style="display:none;">
					<p class="question_tit">
						<span>학습하신 사이버 교육에서 가장 불만인 점은 무엇입니까? </span>
					</p>
					<ul id="q_ans_8">
						<li class="wf"><input name="q_ans_8" type="radio" value="1" onclick="javascript:toogle_radio('8','N');" /> 부정확하고 오래된 학습 내용</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="2" onclick="javascript:toogle_radio('8','N');" /> 낮은 강사 수준</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="3" onclick="javascript:toogle_radio('8','N');" /> 콘텐츠 오류로 인한 학습 중단</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="4" onclick="javascript:toogle_radio('8','N');" /> 불친절한 교육 운영</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="5" onclick="javascript:toogle_radio('8','N');" /> 복잡한 홈페이지 구조</li>
						<li class="wf"><input name="q_ans_8" type="radio" value="6" onclick="javascript:toogle_radio('8','Y');" /> 기타 <input name="r_ans_8" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_9">
					<p class="question_tit">
						<span>향후 사이버콘텐츠아카데미에서 확대했으면 하는 콘텐츠 분야가 있다면 무엇입니까? (중복선택 가능)</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="1" /> 출판 / 인쇄</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="2" /> 만화</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="3" /> 애니메이션</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="4" /> 게임</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="5" /> 영화</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="6" /> 방송</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="7" /> 광고</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="8" /> 캐릭터</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="9" /> 지식정보</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="10" /> 음악</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="11" /> 공연</li>
						<li class="wf"><input name="q_ans_9" type="checkbox" value="12" /> 콘텐츠 솔루션</li>
					</ul>
				</div>

				<div class="question" id="question_10">
					<p class="question_tit">
						<span>앞에서 선택하신 콘텐츠 분야에서 어떤 교육내용을 더 확대하면 좋겠습니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_10" type="radio" value="1" onclick="javascript:toogle_radio('10','N');"/> 기획/창작 관련</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="2" onclick="javascript:toogle_radio('10','N');"/> 제작/개발/표현 관련</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="3" onclick="javascript:toogle_radio('10','N');"/> 유통/마케팅 관련</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="4" onclick="javascript:toogle_radio('10','N');"/> 경영/경영지원 관련</li>
						<li class="wf"><input name="q_ans_10" type="radio" value="5" onclick="javascript:toogle_radio('10','Y');"/> 기타 <input name="r_ans_10" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_11">
					<p class="question_tit">
						<span>혹시 학습하기를 원하는 구체적인 과목이 있다면 무엇입니까? 과목명을 구체적으로 적어주십시오.</span>
					</p><span id="br_show"><br/></span>
					<div class="view_box">
						<span class="tit_center">&lt;과목명 예시&gt;</span><br/>
						방송 : 입체영상 / 영상편집 / 콘텐츠 투자 / 콘텐츠 유통마케팅 / 방송영상 제작 / 방송영상 기획<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / 방송콘텐츠 스토리텔링 등<br/>
						게임 : 2D-3D 게임 그래픽 / 게임배경 / 게임 캐릭터 / 게임 이펙트 / 애니메이션 개론 / 게임 아이디어<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / 게임 레벨 디자인 / 게임 시나리오 / 게임 마케팅 / 게임 알고리즘 / 게임기술이해 / 3D 프로그래밍<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / C# / DirectX / 스케일폼 등<br/>
						문화 : 스토리텔링 / 문화콘텐츠 기획 / 대중문화 / 음반 제작 / 공연 / 전시 / 축제 / 모바일앱<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   / 모바일콘텐츠 개발 / 전자책 / 프레지 등
					</div>
					<ul>
						<li class="wf"><input name="r_ans_11" type="text" value="" size="95"></li>
					</ul>
				</div>

				<div class="question" id="question_12">
					<p class="question_tit">
						<span>향후 사이버콘텐츠아카데미에서 새로운 콘텐츠를 개발할 때 어떤 분을 강사 혹은 내용전문가로 선정하는 것이 좋겠습니까?</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_12" type="radio" value="1" onclick="javascript:toogle_radio('12','N');"/> 대학교, 연구원 등 학문적인 연구나 강의를 하시는 분</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="2" onclick="javascript:toogle_radio('12','N');"/> 콘텐츠 관련 직접 업무를 수행해온 업계 전문가 및 실무자</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="3" onclick="javascript:toogle_radio('12','N');"/> 자격증 취득에 도움이 될 수 있는 콘텐츠 관련 전문 강사</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="4" onclick="javascript:toogle_radio('12','N');"/> 한국콘텐츠진흥원 직원 중 강의능력이 뛰어나신 분</li>
						<li class="wf"><input name="q_ans_12" type="radio" value="5" onclick="javascript:toogle_radio('12','Y');"/> 기타 <input name="r_ans_12" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>


				<div class="question" id="question_13">
					<p class="question_tit">
						<span>응답자님께서 강의를 듣고 싶은 강사 혹은 내용전문가가 있다면 구체적으로 적어주십시오.</span>
					</p><span id="br_show2"><br/></span>
					<div class="view_box">
						<span class="tit_center">&lt;예시&gt;</span><br/>
						‘이끼’, ‘미생’ 윤태호 작가 / ‘은밀하게 위대하게’ 최종훈 작가 / 엔씨소프트 김택진 대표 /‘구가의 서’ 박태영 PD <br/>
						/ ‘나인’ 조봉한 촬영감독 / 무한도전 김태호 PD 등
					</div>
					<ul>
						<li class="wf"><input name="r_ans_13" type="text" value="" size="95"/></li>
					</ul>
				</div>
				
				
				<div class="question" id="question_14">
					<p class="question_tit">
						<span>향후 더 많은 사람이 사이버콘텐츠아카데미를 찾아오도록 하기 위해서 가장 중점을 두고 해야 할 일은 무엇이라고 생각하십니까? (중복선택 가능)</span>
					</p>
					<ul>
						<li class="wf"><input name="q_ans_14" type="checkbox" value="1" /> 전문적인 내용으로 구성된 우수한 콘텐츠 확대</li>
						<li class="wf"><input name="q_ans_14" type="checkbox" value="2" /> 교육내용에 대한 안내 및 홍보 확대</li>
						<li class="wf"><input name="q_ans_14" type="checkbox" value="3" /> 간편하고 편리한 학습 사이트 구축</li>
						<li class="wf"><input name="q_ans_14" type="checkbox" value="4" /> 콘텐츠 산업에 대한 다양한 정보 제공</li>
						<li class="wf"><input name="q_ans_14" type="checkbox" value="5" /> 인적네트워크 형성을 위한 커뮤니티 활동 강화</li>
						<li class="wf"><input name="q_ans_14" type="checkbox" value="6" /> 콘텐츠 산업의 직무/직업별로 특화된 학습 및 경력개발 경로 제공</li>
						<li class="wf"><input name="q_ans_14" type="checkbox" value="7" id="etc_che_14" onclick="javascript:toogle_checkbox('14');" /> 기타 <input name="r_ans_14" type="text" value="" size="50" readonly="readonly"/></li>
					</ul>
				</div>
				
				<div class="question" id="question_13">
					<p class="question_tit">
						<span>마지막으로 사이버콘텐츠아카데미에 보다 많은 사람들이 방문하여 콘텐츠 관련 지식을 학습하도록 하기 위한 아이디어가 있다면, 무엇이든 상관없으니 자유롭게 말씀해 주십시오.</span>
					</p>
					<ul>
						<li class="wf"><input name="r_ans_15" type="text" value="" size="95"/></li>
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
