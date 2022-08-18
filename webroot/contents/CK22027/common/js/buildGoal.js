$(document).ready(function(){
	var html = '';
	html += '<div class="contents-stage" id="goal">';
	html += '<div class="syncObj" id="goal-wrap">';
	html += '<div class="goal-learning-wrap">';
	html += '<div class="goal-title-wrap" id="goal-learning-title"><div class="goal-title-txt">학습내용</div></div>';
	var learningTotal = goalInfo["learning"].length;
	for(i = 0; i < learningTotal; i++){
		html += '<div class="goal-data-wrap">';
		html += '<div class="goal-learning-bullet">'+(i+1)+'</div>';
		html += '<div class="goal-data-txt goal-learning-txt">'+goalInfo["learning"][i]+'</div>';
		html += '</div>';
	}
	html += '</div>';
	html += '<div class="goal-goal-wrap">';
	html += '<div class="goal-title-wrap" id="goal-goal-title"><div class="goal-title-txt">학습목표</div></div>';
	var goalTotal = goalInfo["goal"].length;
	for(i = 0; i < goalTotal; i++){
		html += '<div class="goal-data-wrap">';
		html += '<div class="goal-goal-bullet"></div>';
		html += '<div class="goal-data-txt goal-goal-txt">'+goalInfo["goal"][i]+'</div>';
		html += '</div>';
	}
	html += '</div>';
	if(goal_exercise && contentsMode != "capture"){
		html += '<div class="goal-btn-wrap">';
		/*html += '<button class="goal-btn" id="goal-down-btn">';*/
		/*html += '<div class="goal-btn-name">예제 다운로드</div>';*/
		/*html += '<div class="goal-btn-icon" id="goal-down-icon"></div>';*/
		/*html += '</button>';*/
		html += '</div>';
	}
	html += '</div>';
	$("#contentUI").html(html);
	app_complete = "complete";
	$(".goal-btn").on({
		mouseover : function(){
			$(this).children(".goal-btn-name").css({"color":"#ffd600"});
			$(this).children(".goal-btn-icon").css({"background-position-x":"-"+$(".goal-btn-icon").width()+"px"});
		},
		mouseout : function(){
			$(this).children(".goal-btn-name").css({"color":"#fff"});
			$(this).children(".goal-btn-icon").css({"background-position-x":"0px"});
		},
		click : function(){
			exercise_down();
		}
	});
	mobileBtnSet();
});

function exercise_down(){
	var down_url = "./down/exercise.zip";
	window.open(down_url);
}