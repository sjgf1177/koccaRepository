/////////// 정리하기(인쇄용pdf 생성파일) /////////////////////////


$(document).ready(function(){

	var curPageNum=1;
	var totalPageNum=pageView[currentChapter][pageView[currentChapter].length-1];

		var imgDefaultURL="../common/img/contentimg/";

		var subTag ="";	

		var cnt=0; 
		var mbimgcnt;

		subTag=subTag+"<div id='printTitleSet'>"
		subTag=subTag+"<div id='courseName'><span>"+subtitle+"</span></div>";
		subTag=subTag+"<div id='chapterTitleBox'><div id='chapterTitle'>"+currentChapter+"차시. "+chapNameSet[currentChapter]+"</div></div>";
		subTag=subTag+"</div>";
	

		for(var j=1;j<=totalPageNum;j++){
				subTag=subTag+"<div id='sumSet'>";
				// 제목 동일하지 않을때만 타이틀 출력
				/*
				if(partTitle[currentChapter][j]!=partTitle[currentChapter][j-1]){
					subTag=subTag+"<div id='sumtitle'><div id='snum'>"+j+"</div><div id='stitle'>"+partTitle[currentChapter][j]+"</div></div>";
				}
				*/
				
				for(var i=1;i<=pageView[currentChapter].length;i++){
					if(pageView[currentChapter][i-1]==j){
					subTag=subTag+"<div id='sec'>";
				subTag=subTag+"<div id='secTitlenum'>"+i+"</div><div id='secTitle'> "+summaryTitle[currentChapter][i]+"</div>";
				/* imgFile: 을 만나면 이미지로 치환 */
				var repstr=summaryDesc[currentChapter][i];
				mbimgcnt=0;
				var repstrm=repstr.replace( /:mb:/g, function(){
					mbimgcnt++;
					return "<div id='mbimg'>"+mbimgcnt+"</div>";
				});
				//var repstrm=repstr.replace( /:mb:/g, '<div id="mbimg"></div>');
				var repstrs=repstrm.replace( /:sb:/g, '<div id="sbimg"></div>');
				var repstr1=repstrs.replace( /imgFile:/g, '<img src="'+imgDefaultURL+'');
				var repstr2=repstr1.replace( /.png/gi, '.png">');
			
				subTag=subTag+"<div id='secDesc'> "+repstr2+"</div></div>";
					}
				}
		
				subTag=subTag+"</div>";
		}

		
		$("#pdfprtWrapper").empty();
		$("#pdfprtWrapper").append(subTag);

		//$("#contentBg").css("width","880px");
	//	$("#contentBg").css("width",$("#sumWrapper").width()+"px");
	//	$("#contentBg").css("background-color","#F4F5F9");



});
