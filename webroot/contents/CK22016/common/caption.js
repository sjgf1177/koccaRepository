
var isNarrationsEmpty = false;

/// 나레이션 구현 스크립트

$(document).ready(function(){
	document.title = subtitle;
	
	if(captionSet[currentChapter][currentPage - 1] == "")
		isNarrationsEmpty = true;

	$("#nar_txt").empty();
	$("#nar_txt").append(captionSet[currentChapter][currentPage - 1]);
	
	$("#nar_close").click(function(){
		_narDiv = document.getElementById("narration");
		if(_narDiv){
			if(_narFlag){
				_narFlag=false;	
				_narBtn.style.opacity=1;
			}else{
				_narFlag=true;	
				_narBtn.style.opacity=0.7;
			}
			
			if(_narFlag){
				_narDiv.style.display="block";
			}else{
				_narDiv.style.display="none";
			}
		}
	});
});

