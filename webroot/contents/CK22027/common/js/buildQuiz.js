var quizTotal = Object.keys(quizInfo).length;
var clear_quiz = 0;
var cur_quiz = 0;
var re_quiz = 0;
var select_total = 0;
var my_answer = 0;
var cur_correct = 0;
var start_guide = "지금까지 학습한 내용을 퀴즈를 통해|확인해 보겠습니다.";
var score_report = "총|{total}|문제 중|{clear}|문제를 맞히셨습니다.";

var alertMSG = new Object();
alertMSG = {
	"again":"오답입니다-한 번의 기회가 남았습니다.",
	"correct":"정답입니다",
	"incorrect":"오답입니다"
}

$(document).ready(function(){
	var html = '';
	html += '<div class="contents-stage" id="quiz-start-bg"></div>';
	html += '<div class="syncObj" id="quiz-start">';
	html += '<div class="quiz-start-wrap">';
	// html += '<div class="quiz-start-guide">'+start_guide.replaceAll('|','<br/>')+'</div>';
	html += '<button class="quiz-start-btn">START</button>';
	html += '</div>';
	html += '</div>';
	html += '<div class="syncObj" id="quiz">';
	html += '<div class="quiz-wrap">';
	html += '<div class="quiz-child-wrap">';
	html += '<div class="quiz-question-wrap">';
	html += '<div class="quiz-question-num"></div>';
	html += '<div class="quiz-question-txt"></div>';
	html += '</div>';
	html += '<div class="quiz-margin-blank"></div>';
	html += '<div class="quiz-select-wrap">';
	html += '</div>';
	html += '<div class="quiz-margin-blank"></div>';
	html += '</div>';
	html += '<div class="quiz-result">';
	html += '<div class="quiz-result-wrap">';
	html += '<div class="quiz-result-correct-wrap">';
	html += '<div class="quiz-result-correct"></div>';
	html += '</div>';
	html += '<div class="quiz-result-explain-wrap">';
	html += '<div class="quiz-result-explain"></div>';
	html += '</div>';
	html += '<div class="quiz-result-btn-wrap">';
	html += '<button class="quiz-btn" id="quiz-next-btn">';
	html += '<div class="quiz-btn-name">다음문제</div>';
	html += '<div class="quiz-btn-icon" id="quiz-next-icon"></div>';
	html += '</button>';
	html += '<button class="quiz-btn" id="quiz-score-btn">';
	html += '<div class="quiz-btn-name">결과보기</div>';
	html += '<div class="quiz-btn-icon" id="quiz-score-icon"></div>';
	html += '</button>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	html += '<div class="quiz-alert">';
	html += '<div class="quiz-alert-wrap">';
	html += '<div class="quiz-alert-icon"></div>';
	html += '<div class="quiz-alert-txt"></div>';
	html += '<div class="quiz-alert-ex-txt"></div>';
	html += '</div>';
	html += '</div>';
	html += '<div class="quiz-score">';
	html += '<div class="quiz-score-wrap">';
	// html += '<div class="quiz-score-tag">RESULT</div>';
	html += '<div class="quiz-margin-blank"></div>';
	html += '<div class="quiz-score-mark-wrap">';
	html += '<div class="quiz-score-mark-table">';
	for(i = 1; i <= quizTotal; i++){
		html += '<div class="quiz-score-mark">';
		html += '<div class="quiz-score-mark-num">Q'+i+'</div>';
		html += '<div class="quiz-score-mark-txt" id="score-mark'+i+'"></div>';
		html += '</div>';
	}
	html += '</div>';
	html += '<div class="quiz-score-txt"></div>';
	html += '</div>';
	html += '<div class="quiz-margin-blank"></div>';
	html += '<div class="quiz-score-btn-wrap">';
	html += '<button class="quiz-btn" id="quiz-retry-btn">';
	html += '<div class="quiz-btn-name">다시풀기</div>';
	html += '<div class="quiz-btn-icon" id="quiz-retry-icon"></div>';
	html += '</button>';
	html += '</div>';
	html += '</div>';
	html += '</div>';
	$("#contentUI").html(html);
	buildQuiz();
	$(".quiz-start-btn").on({
		mouseover : function(){
			$(this).css({"color":"#ffd600"});
		},
		mouseout : function(){
			$(this).css({"color":"#fff"});
		},
		click : function(){
			quiz_fn("start");
		}
	});
	$(".quiz-btn").on({
		mouseover : function(){
			$(this).children(".quiz-btn-name").css({"color":"#ffd600"});
			$(this).children(".quiz-btn-icon").css({"background-position-x":"-"+$(".quiz-btn-icon").width()+"px"});
		},
		mouseout : function(){
			$(this).children(".quiz-btn-name").css({"color":"#fff"});
			$(this).children(".quiz-btn-icon").css({"background-position-x":"0px"});
		},
		click : function(){
			var _id = $(this).attr('id').split("-");
			console.log("_id : "+_id+" / name : "+_id[1]);
			quiz_fn(_id[1]);
		}
	});
	mobileBtnSet();
});

function buildQuiz(){
	cur_quiz++;
	re_quiz = 0;
	my_answer = 0;
	cur_correct = quizInfo[cur_quiz][0].correct;
	$(".quiz-result-wrap").hide();
	$(".quiz-alert").hide();
	$(".quiz-score").hide();
	$("#quiz-next-btn").hide();
	$("#quiz-score-btn").hide();
	$(".quiz-question-num").html("Q"+cur_quiz+".");
	var _question = quizInfo[cur_quiz][0].question;
	_question = _question.replaceAll("|","<br/>");
	_question = _question.replaceAll("{","<span class='quiz-question-txt-impact'>");
	_question = _question.replaceAll("}","</span>");
	$(".quiz-question-txt").html(_question);
	var _explain = quizInfo[cur_quiz][0].explain;
	_explain = _explain.replaceAll('|','<br/>');
	if(quizInfo[cur_quiz][0].scroll == "none"){
		$(".quiz-result-explain").html(_explain);
		// $(".quiz-result-explain").html(_explain).css({"display":"flex","align-items":"center","justify-content":"flex-start"});
	}else{
		$(".quiz-result-explain").html(_explain).mCustomScrollbar({theme:"dark-3"});
	}
	$(".quiz-result-correct").html(cur_correct);
	select_total = quizInfo[cur_quiz].length-1;
	var html = '';
	for(i = 1; i <= select_total; i++){
		html += '<div class="quiz-select-btn-wrap">';
		html += '<div class="quiz-select-mark" id="quiz-select-mark'+i+'"></div>';
		html += '<button class="quiz-select-btn" id="quiz-select-btn'+i+'">';
		html += '<div class="quiz-select-num">'+i+'</div>';
		html += '<div class="quiz-select-txt">'+quizInfo[cur_quiz][i].select.replaceAll("|","<br/>")+'</div>';
		html += '</button>';
		html += '</div>';
	}
	$(".quiz-select-wrap").html(html);
	if(isIE == "IE"){
		$(".quiz-select-btn-wrap").css({"margin-top":"5px"});
		$(".quiz-select-btn").css({"padding-bottom":"3px"});
	}
	$(".quiz-select-btn").on({
		mouseover : function(){
			$(this).children(".quiz-select-num").css({"color":"#ffd600","background-color":"#FDA947"});
		},
		mouseout : function(){
			var _num = parseInt($(this).attr('id').replace("quiz-select-btn",""),10);
			if(my_answer != _num){
				$(this).children(".quiz-select-num").css({"color":"#fff","background-color":"#000"});
			}
		},
		click : function(){
			var _num = parseInt($(this).attr('id').replace("quiz-select-btn",""),10);
			console.log("_num : "+_num);
			select_fn(_num);
		}
	});
	mobileBtnSet();
	console.log(
		"buildQuiz!!"+"\n"+
		"cur_quiz : "+cur_quiz+"\n"+
		"question : "+quizInfo[cur_quiz][0].question+"\n"+
		"cur_correct : "+cur_correct+"\n"+
		"---------------------------------------"
	);
}

function select_fn(n){
	my_answer = n;
	$(".quiz-select-btn").attr("disabled",true).css({"cursor":"default"});
	$(".quiz-select-btn").children(".quiz-select-num").css({"color":"#fff","background-color":"#000"});
	$("#quiz-select-btn"+n).children(".quiz-select-num").css({"color":"#ffd600","background-color":"#FDA947"});
	submit_fn();
}

function quiz_fn(_btn){
	console.log("quiz_fn : "+_btn);
	switch(_btn){
		case 'start':
			// playerGoto(10);
			$("#quiz").show();
			break;
		case 'next':
			buildQuiz();
			break;
		case 'score':
			score_fn();
			break;
		case 'retry':
			reset_fn();
			break;
	}
}

function submit_fn(){
	if(my_answer == cur_correct){
		fn_correct();
	}else{
		fn_incorrect();
	}
}

function fn_correct(){
	effect_sound_fn("correct");
	clear_quiz++;
	alert_fn("correct");
	$("#quiz-select-mark"+cur_correct).html("정답").fadeIn().css({"background-color":"#0066cc"});
	$("#score-mark"+cur_quiz).html("O").css({"color":"#0292d8"});
	result_fn();
	console.log(
		"fn_correct!!"+"\n"+
		"my_answer : "+my_answer+"\n"+
		"cur_correct : "+cur_correct+"\n"+
		"---------------------------------------"
	);
}

function fn_incorrect(){
	console.log("오답!!!!!"+"\n"+"내 답 : "+my_answer+" / 정답 : "+cur_correct);
	effect_sound_fn("incorrect");
	if(re_quiz == 0){
		alert_fn("again");
	}else{
		alert_fn("incorrect");
		$("#quiz-select-mark"+my_answer).html("오답").fadeIn().css({"background-color":"#ff6666"});
		$("#quiz-select-mark"+cur_correct).html("정답").fadeIn().css({"background-color":"#0066cc"});
		$("#score-mark"+cur_quiz).html("X").css({"color":"#ff6666"});
		result_fn();
	}
	console.log(
		"fn_incorrect!!"+"\n"+
		"my_answer : "+my_answer+"\n"+
		"cur_correct : "+cur_correct+"\n"+
		"re_quiz : "+re_quiz+"\n"+
		"---------------------------------------"
	);
}

function alert_fn(_status){
	if(_status == "miss"){
		effect_sound_fn(_status);
	}
	if(_status == "correct") $(".quiz-alert-wrap").css({"background-color":"#0066cc"});
	if(_status == "incorrect" || _status == "again") $(".quiz-alert-wrap").css({"background-color":"#ff6666"});
	$(".quiz-alert-icon").css({"background":"url(../common/img/contentUI/quiz/quiz-"+_status+"-icon.png) no-repeat"});
	var _alert = alertMSG[_status];
	_alert = _alert.replaceAll("|","<br/>");
	console.log("alert_fn : "+_status);
	if(_status == "correct" || _status == "incorrect"){
		$(".quiz-alert-txt").html(_alert);
		if(cur_quiz < quizTotal){
			$(".quiz-alert-ex-txt").html("다음 문제를 풀어보세요");
		}else{
			$(".quiz-alert-ex-txt").html("결과를 확인해 보세요");
		}
	}else{
		_alert = _alert.split("-");
		$(".quiz-alert-txt").html(_alert[0]);
		$(".quiz-alert-ex-txt").html(_alert[1]);
	}
	$(".quiz-alert").fadeIn().delay(1000).fadeOut(function(){
		if(_status == "again"){
			my_answer = 0;
			re_quiz++;
			$(".quiz-select-btn").attr("disabled",false).css({"cursor":"pointer"}).children(".quiz-select-num").css({"color":"#fff","background-color":"#000"});
		}
	});
}

function result_fn(){
	$(".quiz-result-wrap").show().stop().animate({"margin-top":"0px"},300);
	if(cur_quiz < quizTotal){
		$("#quiz-next-btn").show();
	}else{
		$("#quiz-score-btn").show();
	}
	console.log("result_fn!!");
}

function score_fn(){
	var _score = score_report.split("|");
	var html = '';
	for(i = 0; i < _score.length; i++){
		html += "<span class='score-split'>"+_score[i]+"</span>";
	}
	html = html.replaceAll("{total}","<span class='sc-impact score-total'>"+quizTotal+"</span>");
	html = html.replaceAll("{clear}","<span class='sc-impact score-clear'>"+clear_quiz+"</span>");
	$(".quiz-score-txt").html(html);
	$(".quiz-score").fadeIn();
	complete_check();
}

function reset_fn(){
	app_complete = "incomplete";
	balloon_toggle("hide");
	clear_quiz = 0;
	cur_quiz = 0;
	buildQuiz();
}

function complete_check(){
	app_complete = "complete";
	if(isPlaying == false && $("#balloonUI").css("display") == "none"){
		balloon_toggle("show");
		console.log("complete!!!");
	}
}

function initApp(){
	reset_fn();
}