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
	String p_ans1		= box.getStringDefault("p_ans_1","");
	String p_ans2		= box.getStringDefault("p_ans_2","");
	String p_ans3		= box.getStringDefault("p_ans_3","");
	String p_ans4		= box.getStringDefault("p_ans_4","");
	String p_ans5		= box.getStringDefault("p_ans_5","");
	String p_ans6		= box.getStringDefault("p_ans_6","");
	String p_ans7		= box.getStringDefault("p_ans_7","");
	String p_ans8		= box.getStringDefault("p_ans_8","");
	String p_ans9		= box.getStringDefault("p_ans_9","");
	String p_ans10		= box.getStringDefault("p_ans_10","");
	String p_ans11		= box.getStringDefault("p_ans_11","");
	String p_ans12		= box.getStringDefault("p_ans_12","");
	String p_ans13		= box.getStringDefault("p_ans_13","");
	String p_ans14		= box.getStringDefault("p_ans_14","");
	String p_ans15		= box.getStringDefault("p_ans_15","");
	String p_ans16		= box.getStringDefault("p_ans_16","");
	String p_ans17		= box.getStringDefault("p_ans_17","");
	String p_ans18		= box.getStringDefault("p_ans_18","");
	String p_ans19		= box.getStringDefault("p_ans_19","");
	String p_ans20		= box.getStringDefault("p_ans_20","");
	
	if(p_ans1.equals("undefined")){ p_ans1 = ""; }
	if(p_ans2.equals("undefined")){ p_ans2 = ""; }
	if(p_ans3.equals("undefined")){ p_ans3 = ""; }
	if(p_ans4.equals("undefined")){ p_ans4 = ""; }
	if(p_ans5.equals("undefined")){ p_ans5 = ""; }
	if(p_ans6.equals("undefined")){ p_ans6 = ""; }
	if(p_ans7.equals("undefined")){ p_ans7 = ""; }
	if(p_ans8.equals("undefined")){ p_ans8 = ""; }
	if(p_ans9.equals("undefined")){ p_ans9 = ""; }
	if(p_ans10.equals("undefined")){ p_ans10 = ""; }
	if(p_ans11.equals("undefined")){ p_ans11 = ""; }
	if(p_ans12.equals("undefined")){ p_ans12 = ""; }
	if(p_ans13.equals("undefined")){ p_ans13 = ""; }
	if(p_ans14.equals("undefined")){ p_ans14 = ""; }
	if(p_ans15.equals("undefined")){ p_ans15 = ""; }
	if(p_ans16.equals("undefined")){ p_ans16 = ""; }
	if(p_ans17.equals("undefined")){ p_ans17 = ""; }
	if(p_ans18.equals("undefined")){ p_ans18 = ""; }
	if(p_ans19.equals("undefined")){ p_ans19 = ""; }
	if(p_ans20.equals("undefined")){ p_ans20 = ""; }
	
	String p_gubunnm	= "";
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>사이버콘텐츠아카데미 교육 수요조사 (통계)</title>
<link href="/css/2012/research_style.css" type="text/css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script> 
<script type="text/javascript">
	$(document).ready(function(){
//		$("#q_ans_4 li input").click(function(){
//			var t = $("input[name=tans4]:checked").val();
//			if(t == "4" || t == "5"){
//				$("#question_5").attr("style","display:block;");
//			}else{
//				$("#question_5").attr("style","display:none;");
//				$("input[name=tans5]").attr('checked', false);
//			}
//		});
	});
	function next(){
		var f = document.form1;
		
		var ans_1 = "";		var ans_2 = "";		var ans_3 = "";
		var ans_4 = "";		var ans_5 = "";

		ans_1 = $("input[name=tans1]:checked").val();//1번 정답 담기
		ans_2 = $("input[name=tans2]:checked").val();//2번 정답 담기
		ans_3 = $("input[name=tans3]:checked").val();//3번 정답 담기
		ans_4 = $("input[name=tans4]:checked").val();//4번 정답 담기
		ans_5 = $("input[name=tans5]:checked").val();//5번 정답 담기

		f.p_tans1.value = ans_1;
		f.p_tans2.value = ans_2;
		f.p_tans3.value = ans_3;
		f.p_tans4.value = ans_4;
		f.p_tans5.value = ans_5;

		f.p_handphone.value = f.p_handphone1.value + "-" + f.p_handphone2.value + "-" +f.p_handphone3.value 
		
		if(value_checking()){
			if(f.p_name.value == ""){alert("성명을 입력해 주세요.");return;}
			if(f.p_handphone1.value == ""){alert("연락처를 입력해 주세요.");return;}
			if(f.p_handphone2.value == ""){alert("연락처를 입력해 주세요.");return;}
			if(f.p_handphone3.value == ""){alert("연락처를 입력해 주세요.");return;}
			if(f.p_email.value == ""){alert("이메일을 입력해 주세요.");return false;}
			if(f.p_email.value.indexOf("@") == -1){ alert("이메일 형식이 잘못되었습니다. \n '@'를 포함하여 주세요.");return;}
			if(f.p_email.value.indexOf(".") == -1){ alert("이메일 형식이 잘못되었습니다. \n '.'를 포함하여 주세요.");return;}
			f.submit();
		}else{
			alert("미응답 항목이 있습니다.");
		}
	}

	function value_checking(){
		var f = document.form1;

		if(f.p_tans1.value == "" || f.p_tans1.value == "undefined"){return false;}
		if(f.p_tans2.value == "" || f.p_tans2.value == "undefined"){return false;}
		if(f.p_tans3.value == "" || f.p_tans3.value == "undefined"){return false;}
		if(f.p_tans4.value == "" || f.p_tans4.value == "undefined"){return false;}

		//if(f.p_tans4.value == "4" || f.p_tans4.value == "5"){
		//	if(f.p_tans5.value == "" || f.p_tans5.value == "undefined"){return false;}
		//}

		return true;
	}

	function onlyNumber(){
		if ((event.keyCode<48)||(event.keyCode>57))
			event.returnValue = false;
	}

</script>
</head>
<BODY>
<form name="form1" id="form1" method="post" action="/servlet/controller.homepage.MainServlet">
<input type="hidden" name="p_process" value="research_save"/>
<input type="hidden" name="p_link" value="<%=p_link %>"/>
<input type="hidden" name="p_gubun" value="<%=p_gubun %>"/>
<input type="hidden" name="p_ans1" value="<%=p_ans1 %>"/>
<input type="hidden" name="p_ans2" value="<%=p_ans2 %>"/>
<input type="hidden" name="p_ans3" value="<%=p_ans3 %>"/>
<input type="hidden" name="p_ans4" value="<%=p_ans4 %>"/>
<input type="hidden" name="p_ans5" value="<%=p_ans5 %>"/>
<input type="hidden" name="p_ans6" value="<%=p_ans6 %>"/>
<input type="hidden" name="p_ans7" value="<%=p_ans7 %>"/>
<input type="hidden" name="p_ans8" value="<%=p_ans8 %>"/>
<input type="hidden" name="p_ans9" value="<%=p_ans9 %>"/>
<input type="hidden" name="p_ans10" value="<%=p_ans10 %>"/>
<input type="hidden" name="p_ans11" value="<%=p_ans11 %>"/>
<input type="hidden" name="p_ans12" value="<%=p_ans12 %>"/>
<input type="hidden" name="p_ans13" value="<%=p_ans13 %>"/>
<input type="hidden" name="p_ans14" value="<%=p_ans14 %>"/>
<input type="hidden" name="p_ans15" value="<%=p_ans15 %>"/>
<input type="hidden" name="p_ans16" value="<%=p_ans16 %>"/>
<input type="hidden" name="p_ans17" value="<%=p_ans17 %>"/>
<input type="hidden" name="p_ans18" value="<%=p_ans18 %>"/>
<input type="hidden" name="p_ans19" value="<%=p_ans19 %>"/>
<input type="hidden" name="p_ans20" value="<%=p_ans20 %>"/>
<input type="hidden" name="p_tans1" />
<input type="hidden" name="p_tans2" />
<input type="hidden" name="p_tans3" />
<input type="hidden" name="p_tans4" />
<input type="hidden" name="p_tans5" />
<div id="wrap">
	<!-- header -->
	<div id="header">
		<h1><img src="/images/2012/main/research/logo.gif" /></h1>
	</div>
	<!-- //header -->

	<!-- content -->
	<div id="con">
		<div class="box_header">
			<img class="header_tit" src="/images/2012/main/research/endtitle.png"/>
		
			<div class="box">
				<div class="question" id="question_1">
					<p class="question_tit">
						<span>성별은 무엇입니까?</span> 
					</p>
					<ul>
						<li class="wf"><input name="tans1" type="radio" value="1" /> 남성</li>
						<li class="wf"><input name="tans1" type="radio" value="2" /> 여성</li>
					</ul>
				</div>
			
				<div class="question" id="question_2">
					<p class="question_tit">
						<span>연령은 어떻게 되십니까? </span>
					</p>
					<ul>
						<li class="wf"><input name="tans2" type="radio" value="1" /> 19세 이하</li>
						<li class="wf"><input name="tans2" type="radio" value="2" /> 20대</li>
						<li class="wf"><input name="tans2" type="radio" value="3" /> 30대</li>
						<li class="wf"><input name="tans2" type="radio" value="4" /> 40대</li>
						<li class="wf"><input name="tans2" type="radio" value="5" /> 50세 이상</li>
					</ul>
				</div>
			
				<div class="question" id="question_3">
					<p class="question_tit">
						<span>현재 거주하고 있는 지역은 어디입니까? </span>
					</p>
					<ul>
						<li class="wh"><input name="tans3" type="radio" value="1" /> 서울</li>
						<li class="wh"><input name="tans3" type="radio" value="2" /> 경기 / 인천</li>
						<li class="wh"><input name="tans3" type="radio" value="3" /> 강원</li>
						<li class="wh"><input name="tans3" type="radio" value="4" /> 충청 / 대전</li>
						<li class="wh"><input name="tans3" type="radio" value="5" /> 경상 / 대구 / 울산 / 부산</li>
						<li class="wh"><input name="tans3" type="radio" value="6" /> 전라 / 광주</li>
						<li class="wf"><input name="tans3" type="radio" value="7" /> 제주</li>
					</ul>
				</div>
			
				<div class="question" id="question_4">
					<p class="question_tit">
						<span>현재 콘텐츠(게임/방송/문화 등) 분야와 관련하여 어떤 일을 하고 계십니까? </span>
					</p>
					<ul id="q_ans_4">
						<li class="wf"><input name="tans4" type="radio" value="1" /> 콘텐츠 산업 관련 학업 진행</li>
						<li class="wf"><input name="tans4" type="radio" value="2" /> 콘텐츠 산업 분야 취업 준비</li>
						<li class="wf"><input name="tans4" type="radio" value="3" /> 콘텐츠 산업 분야 창업 준비</li>
						<li class="wf"><input name="tans4" type="radio" value="4" /> 콘텐츠 산업 분야 기업 재직</li>
						<li class="wf"><input name="tans4" type="radio" value="5" /> 콘텐츠 산업 분야 공공기관 재직</li>
						<li class="wf"><input name="tans4" type="radio" value="6" /> 콘텐츠 산업 분야에 관심있는 일반인</li>
					</ul>
				</div>
			
				<div class="question" id="question_5" style="display: none;">
					<p class="question_tit">
						<span>어떤 콘텐츠 분야의 업무에 종사하고 계십니까?</span>
					</p>
					<ul>
						<li class="wh"><input name="tans5" type="radio" value="1" /> 출판 / 인쇄</li>
						<li class="wh"><input name="tans5" type="radio" value="2" /> 만화</li>
						<li class="wh"><input name="tans5" type="radio" value="3" /> 애니메이션</li>
						<li class="wh"><input name="tans5" type="radio" value="4" /> 게임</li>
						<li class="wh"><input name="tans5" type="radio" value="5" /> 영화</li>
						<li class="wh"><input name="tans5" type="radio" value="6" /> 방송</li>
						<li class="wh"><input name="tans5" type="radio" value="7" /> 광고</li>
						<li class="wh"><input name="tans5" type="radio" value="8" /> 캐릭터</li>
						<li class="wh"><input name="tans5" type="radio" value="9" /> 지식정보</li>
						<li class="wh"><input name="tans5" type="radio" value="10" /> 음악</li>
						<li class="wh"><input name="tans5" type="radio" value="11" /> 공연</li>
						<li class="wf"><input name="tans5" type="radio" value="12" /> 콘텐츠 솔루션</li>
					</ul>
				</div>
				
				<div class="smallbox">
					※ 추천 경품 제공을 위해 필요한 정보입니다.<br/>
					아래 정보는 이번 조사의 답례품 제공 목적으로만 사용됨을 알려드립니다.<br/><br/>
					성&nbsp;&nbsp;&nbsp;명 <input type="text" name="p_name" size="30"/><br/>
					연락처
						<input type="text" name="p_handphone1" size="7" maxlength="3" style="ime-mode:disabled;" onkeypress="onlyNumber();"/> -
						<input type="text" name="p_handphone2" size="8" maxlength="4" style="ime-mode:disabled;" onkeypress="onlyNumber();"/> - 
						<input type="text" name="p_handphone3" size="8" maxlength="4" style="ime-mode:disabled;" onkeypress="onlyNumber();"/>
						<input type="hidden" name="p_handphone" size="30"/><br/>
					이메일 <input type="text" name="p_email" size="30"/>
				</div>
			</div>
		</div>
	
		<br/>
		<div id="footer">
			<a href="javascript:next();"><img src="/images/2012/main/research/finishbutton.gif"/></a>
		</div>
	</div>
</div>
<!-- //content -->

</form>
</BODY>
</HTML>
