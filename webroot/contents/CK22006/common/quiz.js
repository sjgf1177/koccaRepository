
var ReviewQuizManager = function(){
	var instance;
	var itv;
	
	this.setQuizType = function(){
		this.instance = new QuizForMultiChoice();		
	};
	
	this.start = function(quiz){
		this.setQuizType(quiz.type);
		this.instance.start(quiz);
	};
	
	this.restart = function(){
		this.instance.restart();
	};
	
	this.next = function(){
		this.instance.next();
	};

	this.prev = function(){
		this.instance.prev();
	};
	
	this.checkAnswer = function(){
		this.instance.checkAnswer();
	};
	
	this.result = function(){
		this.instance.result();
	};
	
	this.showAlertDiv = function(v){
		this.instance.showAlertDiv(v);
	}
	
	this.showPopup = function(){
		this.instance.showPopup();
	}
	
	this.runWatchMode = function(){
		this.instance.runWatchMode();
	}
};

var QuizForMultiChoice = function(){
	var Quiz;
	var isIncorrect = false;
	var selectedIncorrect = 0;
	var incorrectQuiz = new Array();
	var stateQuestionMark = [];
	
	var selectedQuiz = 0;
	var selectedAnswer = [];
	
	var oneMoreChance = true;
	var isWatcherMode = false;
	
	var currentQuestionNum=0;
	var prevClickNum=0;

	
	this.start = function(quiz){
		this.Quiz = quiz;
		this.isWatcherMode = false;

		this.setting();
		this.assignEvent();
	};
	
	this.restart = function(){
		this.isWatcherMode = false;
		this.isIncorrect = true;
		this.selectedIncorrect = 0;
		
		selectedQuiz = this.getSelectedQuiz();
		
	    $("#endQuestion").hide();
	    $("#checkAnswer").show();
	    
		this.clear();
		this.setting();
		this.assignEvent();

		$("#page2").show();
		$("#page3").hide();
	};
	
	this.next = function(){
		// 선택한 답이 없을 경우 문제풀기모드로 전환
		//alert("해당문제번호:"+selectedQuiz+",선택답:"+selectedAnswer[selectedQuiz]);

		//alert(this.currentQuestionNum);
		/*
		if(prevClickNum==1){
			this.isIncorrect = true;
		    this.isWatcherMode =false;
		}else{
			prevClickNum--;
		}
		*/

		
		if(this.isWatcherMode == false)
			selectedQuiz = this.getSelectedQuiz();
		else
			selectedQuiz++;

		if(selectedQuiz > this.Quiz.count-1){
			$("#prevQuestion").show();
			$("#nextQuestion").hide();
			$("#endQuestion").show();
			return;
		}


		
		$("#checkAnswer").show();
		$("#prevQuestion").show();
		$("#nextQuestion").hide();
		
		
		this.clear();
		this.setting();
		if(this.isWatcherMode == false)
			this.assignEvent();
	};

	this.prev = function(){
		//alert("prev");
		
		this.isIncorrect = false;
		this.isWatcherMode = true;
		prevClickNum++;


		if(this.isWatcherMode == false)
			selectedQuiz = this.getSelectedQuiz();
		else
			selectedQuiz--;

		if(selectedQuiz =0 ){
			$("#prevQuestion").hide();
			$("#nextQuestion").show();
			$("#endQuestion").hide();
			return;
		}
		/*
		$("#checkAnswer").show();
		$("#prevQuestion").show();
		$("#nextQuestion").hide();
		*/
		
		
		this.clear();
		this.setting();
		/*
		if(this.isWatcherMode == false)
			this.assignEvent();
		
		*/

	};
	
	this.getSelectedQuiz = function(){
//		if(this.isIncorrect && selectedQuiz < incorrectQuiz.length){
		if(this.isIncorrect){
			selectedQuiz = incorrectQuiz[selectedIncorrect];
			selectedIncorrect++;
		}else{
			selectedQuiz++;
		}
		
		return selectedQuiz;
	};
	
	// 주관식 중복 정답 체킹
	function stringAnsCheckFn(ansStr, inputStr) {
	var stringAns= ansStr.split("||");
	var stringAnsCorrect = false;

	for (var i = 0; i<stringAns.length; i++) {
		if (inputStr == stringAns[i]) {
			stringAnsCorrect = true;
		}
	}
	return stringAnsCorrect;
	}
	var isAnsString;
	
	this.checkAnswer = function(){
	// 단답형일때
		if(this.Quiz[currentChapter].questions[selectedQuiz].qztype=="ShortAnswer"){

			selectedAnswer[selectedQuiz] =$("#saInput").val();
			if(selectedAnswer[selectedQuiz]=="" ||    (isConsonantHint && selectedAnswer[selectedQuiz]==this.Quiz[currentChapter].questions[selectedQuiz].consonantHint)){
				//alert("답을 입력하세요.");
				this.showAlertDiv("5");
				return;
			}else{
				isAnsString=stringAnsCheckFn(this.Quiz[currentChapter].questions[selectedQuiz].answer,selectedAnswer[selectedQuiz]);
				//alert(isAnsString);
			}
			
		}
		if(selectedAnswer[selectedQuiz] =="undefined" || selectedAnswer[selectedQuiz] == null){
			//alert("답을 선택하세요.");
			this.showAlertDiv("1");
			return;
		}
		
		if(selectedAnswer[selectedQuiz]+1 == this.Quiz[currentChapter].questions[selectedQuiz].answer || (isAnsString && this.Quiz[currentChapter].questions[selectedQuiz].qztype=="ShortAnswer")){
			audioEnd();
			document.getElementById("correctAudio").play();
			
			//alert("정답입니다.");	
			this.showAlertDiv("4");
			$("#quiztfIcon").addClass("question_correct");
			stateQuestionMark[selectedQuiz] = "correct";
			
		}else{
			// OX 일경우는 기회 한번만 제공
			if(this.oneMoreChance == true && this.Quiz[currentChapter].questions[selectedQuiz].qztype!="OX") {
				this.oneMoreChance = false;
				this.tryAgain(true);
				
				
				//alert("다시 한 번 선택해 보세요.");
				audioEnd();
				document.getElementById("incorrectAudio").play();
				this.showAlertDiv("2");
				return;
			}

			
			
			stateQuestionMark[selectedQuiz] = "incorrect";
			document.getElementById("incorrectAudio").play();
			$("#quiztfIcon").addClass("question_incorrect");
			
			//alert("오답입니다.");	
			this.showAlertDiv("3");
		}
		$('#saInput').blur();
		
		$("#saInput").attr("disabled",true).attr("readonly",false);

		$("#quizIconDiv").show();

		this.tryAgain(false);
		this.updateQuestionMark();

		audioEnd();
		this.settingDap();

		this.currentQuestionNum=selectedQuiz;
		//alert("선택질문번호:"+this.currentQuestionNum);
	};
	
	this.settingDap = function() {
		$("#questDapDiv").show();
		$("#dapNumber").empty();
		//$("#dapNumber img").css("margin-top", "7px");
		//$("#dapNumber").append("<img src=\"../common/img/quiz/ans" + this.Quiz.questions[selectedQuiz].answer + "_over.png\">");
		
		var ansStr=""
		if(this.Quiz[currentChapter].questions[selectedQuiz].qztype=="OX"){
			if(this.Quiz[currentChapter].questions[selectedQuiz].answer==1){
				ansStr="O";
			}else{
				ansStr="X";
			}
		}else{
			ansStr=this.Quiz[currentChapter].questions[selectedQuiz].answer;
		}


			if(this.Quiz[currentChapter].questions[selectedQuiz].qztype=="ShortAnswer"){
			
			var ansStr=ansStr.replace( '||', '<br>또는 ');			
				$("#dapNumber").append("<span>정답<div class='dN_string'>"+ ansStr+"</div></span>");
		}else{
				$("#dapNumber").append("<span>정답<div class='dN_number'>"+ ansStr+"</div></span>");
		}
		/*
		$("#dapNumber").addClass("answer_number");		
		*/

		$("#dapStr").empty();
		$("#dapStr").append(this.Quiz[currentChapter].questions[selectedQuiz].memo);
		
//		$("DIV.select ul li").removeClass("selected");
//		$("DIV.select ul li").addClass("notselected");
		
		$("DIV.select ul li").unbind("click");

		if( this.Quiz[currentChapter].questions[selectedQuiz].qztype=="Choice"){
			if(this.isWatcherMode == false) {
				$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+")").addClass("answer");
				$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+") span:eq(1)").removeClass("question_no"+this.Quiz[currentChapter].questions[selectedQuiz].answer);
				$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+") span:eq(1)").addClass("answer_no"+this.Quiz[currentChapter].questions[selectedQuiz].answer);
			}
			else {
				$("DIV.select ul li:eq("+selectedAnswer[selectedQuiz]+")").removeClass("notselected");
				$("DIV.select ul li:eq("+selectedAnswer[selectedQuiz]+")").addClass("selected");
			}
		}else if(this.Quiz[currentChapter].questions[selectedQuiz].qztype=="OX"){
			if(this.isWatcherMode == false) {
		
				var qstr="";
				if(this.Quiz[currentChapter].questions[selectedQuiz].answer==1){
					qstr="O";
				}else{
					qstr="X";
				}

				$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+") span:eq(1)").removeClass("question"+qstr);
				$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+") span:eq(1)").addClass("answer"+qstr);				

			}else{
				$("DIV.select ul li:eq("+selectedAnswer[selectedQuiz]+")").removeClass("notselectedOX");
				$("DIV.select ul li:eq("+selectedAnswer[selectedQuiz]+")").addClass("selectedOX");
			}

		}

		/*
		if(this.isWatcherMode == false) {
			$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+")").addClass("answer");
			$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+") span:eq(1)").removeClass("question_no"+this.Quiz[currentChapter].questions[selectedQuiz].answer);
			$("DIV.select ul li:eq("+(this.Quiz[currentChapter].questions[selectedQuiz].answer - 1)+") span:eq(1)").addClass("answer_no"+this.Quiz[currentChapter].questions[selectedQuiz].answer);
		}
		else {
			$("DIV.select ul li:eq("+selectedAnswer[selectedQuiz]+")").removeClass("notselected");
			$("DIV.select ul li:eq("+selectedAnswer[selectedQuiz]+")").addClass("selected");
		}
		*/
		
		$("#checkAnswer").hide();
		
		$("#endQuestion").hide();
		$("#nextQuestion").hide();
		

		if(selectedQuiz!=0)
			$("#prevQuestion").show();
		else
			$("#prevQuestion").hide();
	

		if(selectedQuiz == this.Quiz[currentChapter].questions.length-1 || ( selectedIncorrect != 0 && selectedIncorrect == incorrectQuiz.length))
			$("#endQuestion").show();
		else
			$("#nextQuestion").show();
	}
	
	this.result = function(){
		var correctCount = 0;
		var questionCount = this.Quiz[currentChapter].questions.length;
		
		$("#resultTable").empty();

		incorrectQuiz = new Array();
		var tableContents = "";
		for ( var i = 0; i < this.Quiz[currentChapter].questions.length; i++) {
	
			if(i < this.Quiz[currentChapter].questions.length - 1)
				tableContents += "<ul class='column'>";
			else
				tableContents += "<ul class='column_final'>";
		
			if(this.Quiz[currentChapter].questions[i].qztype=="ShortAnswer"){
			isAnsString=stringAnsCheckFn(this.Quiz[currentChapter].questions[i].answer,selectedAnswer[i]);
			}
			if(selectedAnswer[i]+1 == this.Quiz[currentChapter].questions[i].answer || (isAnsString &&  this.Quiz[currentChapter].questions[i].qztype=="ShortAnswer")){
				correctCount++;
				//tableContents += "<li class='row'>Q" + (i + 1) + "</li><li class='row'><img src='../common/img/quiz/qResultMarkO.png'></li></ul>"
				//tableContents += "<li class='row'><img src='../common/img/quiz/q" + (i + 1) + "Number.png'></li><li class='row'><img src='../common/img/quiz/qResultMarkMainO.png'></li></ul>"
				tableContents += "<li class='row' style='background-image:url(../common/img/quiz/q" + (i + 1) + "Number.png)'><img src='../common/img/quiz/qResultMarkMainO.png'></li></ul>"
			//	$("#resultTable").addClass("qReimg"+i);
			}else{
				incorrectQuiz.push(i);
				//tableContents += "<li class='row'>Q" + (i + 1) + "</li><li class='row'><img src='../common/img/quiz/qResultMarkX.png'></li></ul>"
			//	tableContents += "<li class='row'><img src='../common/img/quiz/q" + (i + 1) + "Number.png'></li><li class='row'><img src='../common/img/quiz/qResultMarkMAinX.png'></li></ul>"
				tableContents += "<li class='row' style='background-image:url(../common/img/quiz/q" + (i + 1) + "Number.png)'><img src='../common/img/quiz/qResultMarkMainX.png'></li></ul>"
			//	$("#resultTable").addClass("qReimg"+i);
			}
		}
		$("#resultTable").append(tableContents);

		$("#total_quest_no").empty();
		$("#total_quest_no").append(questionCount);
		
		$("#correct_quest_no").empty();
		$("#correct_quest_no").append(correctCount);
		
		showPopupChapter();

		if(correctCount == questionCount){
			$("#reQuestion").hide();
			/*$("#commentary").css("margin-left", "130px");*/
		}

		if(questionCount==3){
			$("#resultTable").css("margin-left", "60px");
		}
	};
	
	this.clear = function(){
		$("#quiztfIcon").removeClass("question_correct question_incorrect");
		$("DIV.quest .question").empty();
		$("DIV.quest").removeClass("correct incorrect");
		$("DIV.select ul").empty();
		$("DIV.memo").empty();
		$("#saInput").empty();
		this.tryAgain(false);
	};
	
	this.setting = function(){
		this.updateQuestionMark();

		$("#quizIconDiv").empty();
		$("#quizIconDiv").append("<img src='../common/img/quiz/q" + (selectedQuiz + 1)+"Number.png'>");
		//$("#quizIconDiv").append("Q" + (selectedQuiz + 1));

		$("#quizTitle").empty();
		$("#quizTitle").append(this.Quiz[currentChapter].questions[selectedQuiz].question);
		
		$("#saInput").empty();

		$("#shortAnswerDiv").css("display","none");
		$("DIV.select ul").css("display","block");
		// 변경
		/*
		if(this.Quiz[currentChapter].questions[selectedQuiz].qztype!="OX"){
			$("#quizTitle").append(this.Quiz[currentChapter].questions[selectedQuiz].question);
		}else{
			$("#quizTitle").append("<img src='../common/img/quiz/quizOXDefaultText.png'>");
			$("#oxQuestionBox").css("display","block");
			$("#oxQuestionTitle").empty();
			$("#oxQuestionTitle").append(this.Quiz[currentChapter].questions[selectedQuiz].question);
		}
		*/
		
		var choice = this.Quiz[currentChapter].questions[selectedQuiz].items;
		
		// ------------------------------
		if( this.Quiz[currentChapter].questions[selectedQuiz].qztype=="Choice"){
			for(var i=0; i < choice.length; i++){
				$("#qsel").css('position','absolute').css('top','200px');
				$("DIV.select ul").append("<li class='notselected'><span class='mark'></span> <span class='question_icon question_no"+(i+1)+"' /> " + choice[i].item+"</li>");
			}
		}else if(this.Quiz[currentChapter].questions[selectedQuiz].qztype=="OX"){
			$("#qsel").css('position','absolute').css('top','180px'); /*edit*/
			$("DIV.select ul").append("<li class='notselectedOX'><span class='mark'></span> <span></span></li><li class='notselectedOX'><span class='mark'></span> <span></span></li>");
				//$("DIV.select ul").append("<li class='notselected'><span class='mark'></span><img src='../common/img/quiz/min_o.png'></li><li class='notselected'><span class='mark'></span><img src='../common/img/quiz/min_x.png'></li>");
				//$("DIV.select ul").append("<li class='notselected'><span class='mark'></span> <span class='answer_o'/></li><li class='notselected'><span class='mark'></span> <span class='answer_x'/></li>");
			$("DIV.select ul li").addClass("oxDisplay");
			$("DIV.select ul li:eq(0)").addClass("questionO");
			$("DIV.select ul li:eq(1)").addClass("questionX");
		}else{
		
			$("#shortAnswerDiv").css("display","block");			
			//$("#saInput").focus();
			$("#saInput").attr("disabled",false).attr("readonly",false);
		
			if(isConsonantHint && !this.isWatcherMode){				
				$("#saInput").val(this.Quiz[currentChapter].questions[selectedQuiz].consonantHint);				
				$("#saInput").focus(function(){
					$("#saInput").val('');
				});

				$('#saInput').focusin(function(e) {
					/*
					if(e.keyCode == 37 || e.keyCode == 39 || e.keyCode == 32 ){
						 e.stopPropagation();
					}
					*/
				})
 
				$('#saInput').focusout(function(e) {
			
				});

			
			}
			$("DIV.select ul").css("display","none");
		}
		

			$("#nextQuestion").hide();
			if(selectedQuiz==0){
				$("#prevQuestion").hide();
			}else{
				$("#prevQuestion").show();
			}
	
	
		
		// 한번더 기회주기
		this.oneMoreChance = true;
		
		if(this.isWatcherMode == false)
			$("#questDapDiv").hide();	
		else {
			$("#questDapDiv").show();
			this.settingDap();
			$("#saInput").attr("disabled",true).attr("readonly",true);
			$("#saInput").val(selectedAnswer[selectedQuiz]);
		}
	};
	
	// 문항 번호 클릭 시 동작
	this.assignEvent = function(){
		
		var str="selected";
		if( this.Quiz[currentChapter].questions[selectedQuiz].qztype=="OX"){
			str=str+"OX";	
		}
		//alert("aaa:"+str);
		if(this.Quiz[currentChapter].questions[selectedQuiz].qztype!="ShortAnswer"){
		$("DIV.select ul li").bind("click", function(e){
			var index = $("DIV.select ul li").index(this);
			$("DIV.select ul li").removeClass(str);
			$("DIV.select ul li").addClass("not"+str);
			$("DIV.select ul li:eq("+index+")").removeClass("not"+str);
			$("DIV.select ul li:eq("+index+")").addClass(str);
			selectedAnswer[selectedQuiz]=index;

			// 문항 클릭시 바로 정답 확인 (작동안함)
			//alert(selectedAnswer[selectedQuiz]);
			//this.checkAnswer();

		});
		}
	};
	
	this.tryAgain = function(flag) {
		if(flag == true) {
			$("DIV.try_again").show();

			$("DIV.select ul li").removeClass("selected");
			$("DIV.select ul li").addClass("notselected");
			selectedAnswer[selectedQuiz] = null;
		}
		else {
			$("DIV.try_again").hide();
		}
	}

	this.updateQuestionMark = function() {
		$("#quiz_sequence_area").empty();

		var questionCount = this.Quiz[currentChapter].questions.length;
		for(var i = questionCount - 1; i > -1; i--) {
			questionMark = "<span style='width: 0px; float:right'></span>";
			var questionMark;

			// 현재 문항은 맞음 틀림 결과값 안보이게
			if(i != selectedQuiz){
				questionMark += "<span class='question_mark_blue' style='color:white'><img src='../common/img/quiz/q" + (i + 1)+"mark.png'>";

			/*
			if(i < selectedQuiz + 1 || stateQuestionMark.length == questionCount)
				questionMark += "<span class='question_mark_blue' style='color:white'><img src='../common/img/quiz/q" + (i + 1)+"mark.png'>";
			else
				questionMark += "<span class='question_mark_grey' style='color:black'><img src='../common/img/quiz/q" + (i + 1)+"mark.png'>";
*/
			/*
			if(i < selectedQuiz + 1 || stateQuestionMark.length == questionCount)
				questionMark += "<span class='question_mark_blue' style='color:white'><span style='font-size:14px;'>&nbsp;<br/></span>Q" + (i + 1);
			else
				questionMark += "<span class='question_mark_grey' style='color:black'><span style='font-size:14px;'>&nbsp;<br/></span>Q" + (i + 1);
			*/
			if(stateQuestionMark[i] == "correct")
				questionMark += "<span class='question_mark_correct'></span>";

			if(stateQuestionMark[i] == "incorrect")
				questionMark += "<span class='question_mark_incorrect'></span>";

			questionMark += "</span>";
			}

			$("#quiz_sequence_area").append(questionMark);
		}

		if(this.isWatcherMode){

			// 푼 문제에 정답 맞춘여부 보이기
			//alert("정답은:"+stateQuestionMark[selectedQuiz]);
			if(stateQuestionMark[selectedQuiz] !="correct"){
				$("#quiztfIcon").addClass("question_incorrect");
			}else{
				$("#quiztfIcon").addClass("question_correct");
			}



		}

	}
	
	//edit
	
	this.showAlertDiv = function (v){
		var ssT="quizAlert"+v+".png";
		$("#quizAlert").css("display", "block");
		$("#quizAlert").css("backgroundImage", "url(../common/img/quiz/"+ssT+")");
		//alert(ssT);
		this.itv = setTimeout(this.hideDiv,1000);
	}

	this.hideDiv = function (){
		clearInterval(this.itv);
		$("#quizAlert").css("display", "none");
		$("#quizAlert").css("backgroundImage", "");
	}
	
	this.showPopup = function () {
		$(".popupContent").css("background", "url('popup/" + (selectedQuiz + 1) + ".png') no-repeat");
		$("#popupStudy").show();
	}
	
	// 푼 문제 보기
	this.runWatchMode = function() {
		this.isIncorrect = false;
		this.isWatcherMode = true;

		selectedIncorrect = 0;
		selectedQuiz = 0;

//		for( var i = 0; i < selectedAnswer.length; i++) {
//			selectedAnswer[i] = null;
//		}
		
		
		selectedQuiz = this.getSelectedQuiz()-1;
		
	    $("#endQuestion").hide();
	    $("#checkAnswer").show();

			$("#oxQuestionBox").css("display","none");
			$("#oxQuestionTitle").empty();
			$("#shortAnswerDiv").css("display","none");
	    
		this.clear();
		this.setting();
		$("#saInput").attr("disabled",true).attr("readonly",true);

		$("#page2").show();
		$("#page3").hide();

		
				
	}
};

var QuizForOX = function(){
	var answer;
	var question;
	
};

