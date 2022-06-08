/// 학습목표, 주제 구현 스크립트

$(document).ready(function(){

	var subTag ="";

	for(var i=1;i<subjectNameSet[currentChapter].length;i++){
		/*
		if(i!=1){
		subTag=subTag+"<br>"	
		}
		*/
		subTag=subTag+"<div id='dot'><img src='../common/img/target/b1"+i+".png'></div><div id='cont'>"+subjectNameSet[currentChapter][i]+"</div>";
		//$("#dot").css({'background':'url(../common/img/target/b1'+i +'.png)', 'background-repeat' : 'no-repeat', 'background-position':'left top'}); 
	}
	$("#subIn").empty();
	$("#subIn").append(subTag);

	subTag ="";

	for(var i=1;i<targetNameSet[currentChapter].length;i++){
		/*
		if(i!=1){
		subTag=subTag+"<br>"	
		}
		*/
		subTag=subTag+"<div id='dot'><img src='../common/img/target/b2"+i+".png'></div><div id='cont'>"+targetNameSet[currentChapter][i]+"</div>";
		//$("#dot").css({'background':'url(../common/img/target/b2'+i +'.png)', 'background-repeat' : 'no-repeat', 'background-position':'left top'}); 
	}

	$("#tarIn").empty();
	$("#tarIn").append(subTag);	
	

});