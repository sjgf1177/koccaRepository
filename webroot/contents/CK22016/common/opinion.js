	
/////////////////////  의견입력  ///////////////////////////


var inputStart=true;
var defaultString="의견을 입력하세요.";
	
	
$(document).ready(function(){

	$("#opinionSubject").empty();
	$("#opinionSubject").append(opinionList[currentChapter]);
		/*$("#bg1").fadeIn(1000);
		$("#opinionSubject").fadeIn(2000);
		$("#opinionInput").fadeIn(3000);
		$("#btn").fadeIn(5000);*/
		$("#opinionInput").val(defaultString);
		$("#opinionInput").css({"background":"url(../common/img/opinion/opinionLine.png)","background-repeat" : "no-repeat", "background-position":"0 30px"});
		
	});


	function focuscheck(){
		if(inputStart){
			$("#opinionInput").val("");
			inputStart=false;
			}
	}

