//
$(document).bind("contextmenu", function(e){
		return false;
});


$(document).ready(function(){
	//////if(!parent.isPageControl) {
		$("#handle").css('cursor', 'none');
	//////}
	/*
	$("#reference_button").click(function() {
		$(".popupContent").css("background", "url('popup/outro1.png') no-repeat");
		$("#popupReference").show();
	});
	*/
	// 배속버튼 기능부여
	//alert($('#curSpeed').height());
	/* ie9 margin-top 값 0으로*/
	$('#displaySpeed').click(function(){
		if($('#curSpeed').hasClass('on')){
			$('#curSpeed').removeClass('on');
			$('#speedBg').css("display","none");
		//	$("#displaySpeed").css("line-height","20px");
			$("#displaySpeed").css("top","-15px");
			$("#displaySpeed").css("margin-left","");
			$("#displaySpeed").css("margin-top","0px");
	
		}else{
			if(parent.isPageControl) {
				$('#curSpeed').addClass('on');
				$('#speedBg').addClass('on');
				$('#speedBg').css("display","block");
				/*
				if(getBrowserType().indexOf("IE")!=-1){
				$("#displaySpeed").css("line-height","50px");	
				}else{
				$("#curSpeed li").css("height","35px");	
				}
				*/
				//$("#displaySpeed").css("line-height","35px");
				$("#displaySpeed").css("top","-5px");
				$("#displaySpeed").css("margin-left","6px");
				$("#displaySpeed").css("margin-top","0px");
				//alert($('#curSpeed').height());
			}
		}		
	});

	$('#speedBgBtn').click(function() {
			$('#curSpeed').removeClass('on');
			$('#speedBg').css("display","none");
			//$("#displaySpeed").css("line-height","20px");
			$("#displaySpeed").css("top","-15px");
				$("#displaySpeed").css("margin-left","");
			$("#displaySpeed").css("margin-top","0px");
	});


	// 일시정지 재생버튼 통합
	//$("#play_btn").css("display","none");	
	
	$("#popupCloseButton").click(function() {
		$("#popupReference").hide();
		$("#popupWrapper").hide();
			if(_video.currentTime<_video.duration-0.1){
			_playMv();
		}
	});

	$("#lecturerCloseButton").click(function() {
	$("#lecturerReference").hide();
	$("#lecturerWrapper").hide();
		if(_video.currentTime<_video.duration-0.1){
			_playMv();
		}
	});

	$("#popupCloseButtonBmk").click(function() {
		$("#bookmarkForm").hide();
	});

	$("#popupCloseButtonMemo").click(function() {
		var memoTxt=$("#memoInput").val();
		if(!(memoTxt=="" || memoTxt==null || memoTxt==undefined)){
			//alert("저장되었습니다.");
			setCookie(bmkCode+"_memo_"+currentChapter, memoTxt, 365);				
		}
		$("#memoForm").hide();	
	});
	
	$(function() {
		var bw=$("#bookmarkForm").width();
		var bh=$("#bookmarkForm").height();
		$( "#bookmarkForm" ).draggable({'cancel':'#bookmarkUnit', containment:'#content', scroll:false})
		//$( "#bookmarkForm" ).draggable({'cancel':'#bookmarkUnit', containment:[0,0,1000-bw,640-bh], scroll:false})

		var mw=$("#memoForm").width();
		var mh=$("#memoForm").height();
		$( "#memoForm" ).draggable({'cancel':'#memoUnit', containment:'#content', scroll:false})
		//$( "#memoForm" ).draggable({'cancel':'#memoUnit', containment:[0,0,1000-mw,640-mh], scroll:false})

	});

			// 키보드 제어
var keyt;
$(document).keydown(function(e) { 
    if (e.keyCode == 37){
		keyt=_video.currentTime-10;
		if(keyt<0){
			keyt=0;
		}
		audioJump(keyt);
    }else if(e.keyCode == 39){    
		keyt=_video.currentTime+10;
		if(keyt>_video.duration){
		keyt=_video.duration;
		}
		audioJump(keyt);
	}
});

$(document).keypress(function(e) { 
	 if(e.keyCode == 32){  
		//alert("비디오정지:"+_video.paused);
		if(_video){
			if(_video.paused){
				_video.play();
			}else{
				_video.pause();
			}	
		}
		if(_audio){
			if(_audio.paused){
				_audio.play();
			}else{
				_audio.pause();
			}	
		}
	}

});



});


document.write("<div id='controlset'>");

document.write("<div id='lmsDiv'>");
document.write("<input type='image' src='../common/img/controlbar/btnIndex.png' class='indexbtn' onClick='fnOpenMenu()'>");
document.write("<input type='image' src='../common/img/controlbar/btnMap.png' class='mapbtn' onClick='fnMap()'>");
/*
document.write("<img src='../common/img/controlbar/conPartion.png' class='btns1'>");

document.write("<input type='image' src='../common/img/controlbar/btnMemo.png' class='memobtn' onClick='fnMemo()'>");
document.write("<img src='../common/img/controlbar/conPartion.png' class='btns2'>");
*/
/* 배속기능 */
/*document.write("<input type='image' src='../common/img/controlbar/btnSpeed.png' class='spbtn' onClick='fnSpeed()'>");*/
document.write("<div id='speedBg'><div><a href='#'><span id='speedBgBtn'></span></a></div></div>");
document.write("<div id='speedSet'><input type='image' src='../common/img/controlbar/btnSpeed.png' class='spbtn' onClick='fnSpeed()'><ul id='curSpeed'><li id='x20' class='speedbtn'>2.0</li><li id='x18' class='speedbtn'>1.8</li><li id='x16' class='speedbtn'>1.6</li><li id='x14' class='speedbtn'>1.4</li><li id='x12' class='speedbtn'>1.2</li><li id='x10' class='speedbtn'>1.0</li>");
document.write("<li id='displaySpeed'>1.0</li></ul></div>");
/*
document.write("<input type='image' src='../common/img/controlbar/btnBookmark.png' class='bmkbtn' onClick='fnBookmark()'>");
document.write("<img src='../common/img/controlbar/conPartion.png' class='btns3'>");
document.write("<input type='image' src='../common/img/controlbar/btnLecturer.png' class='lecbtn' onClick='fnLec()'>");
*/
document.write("</div>");

/*document.write("<div style='background: url(../common/img/controlbar/line.png) no-repeat; margin: 8px 1px 0 0; width: 10px; height: 100% ' class='slidegap'></div>");*/

document.write("<div id='slideBg'>");
document.write("<div class='ui-slider-range' id='slideBar'></div>");
document.write("<div class='ui-slider-handle' id='slideHandleBg'>");
document.write("<div id='handleBar' style='background-color:#0058bb;width:0px;height:11px; margin-top:4px; margin-left:1px; border-radius:30px;'>");
document.write("<input type='image' src='../common/img/controlbar/btnHandler.png' draggable='true' alt='' id='handle' style='margin-left:-15px; margin-top:-5px'/>");
document.write("</div>");
document.write("</div>");
document.write("</div>");

document.write("<div id='vidctrlDiv'>");
document.write("<div id='timer'><span id='playedTime'>00:00</span><span id='totalTime'> / 00:00</span></div>");

/* document.write("<div style='background: url(../common/img/controlbar/line.png) no-repeat; margin: 8px 0 0 0; width: 10px; height: 100% ' ></div>");*/

document.write("<div id='play' >");
document.write("<input type='image' src='../common/img/controlbar/btnPlay.png' alt='재생' title='재생' id='play_btn'/>");
document.write("</div>");

document.write("<div id='pause' >");
document.write("<input type='image' src='../common/img/controlbar/btnPause.png' alt='일시정지' title='일시정지' id='pause_btn'/>");
document.write("</div>");

document.write("<div id='replay' >");
document.write("<input type='image' src='../common/img/controlbar/btnReplay.png' alt='리플레이' title='리플레이'/ id='replay_btn'>");
document.write("</div>");
/*
document.write("<div id='script'>");
document.write("<input type='image' src='../common/img/controlbar/script.png' alt='자막' title='자막' id='nar_btn'/>");
document.write("</div>");
*/
document.write("<div id='vol' >");
document.write("<input type='image' src='../common/img/controlbar/btnVol.png' draggable='true' alt='볼륨' title='볼륨' id='vol_btn'/>");
document.write("</div>");

document.write("<div id='volSlideBar'>");
document.write("<div id='volHandleBg'>");
document.write("<input type='image' src='../common/img/controlbar/volHandle.png' id='volHandle' draggable='true'>");
document.write("</div>");
document.write("</div>");

document.write("<div id='fsc' >");
document.write("<input type='image' src='../common/img/controlbar/btnFullScreen.png' draggable='true' alt='풀스크린' title='풀스크린' id='full_btn'/>");
document.write("</div>");

document.write("</div>");
document.write("</div>");



document.write("<div id='paging'>");
document.write("<div id='prev'>");
document.write("<input type='image' src='../common/img/controlbar/btnPrev.png' alt='이전 페이지' title='이전 페이지'  id='btnPrev' onClick='goPrevPage()'/>");
/*document.write("<img src='../common/img/controlbar/conPartion.png'>");*/
document.write("</div>");

document.write("<div id='page_num'><span id='current_page'></span><img src='../common/img/controlbar/num/slash.png'><span id='total_page'></span></div>");

document.write("<div id='next'>");
/*document.write("<img src='../common/img/controlbar/conPartion.png'>");*/
document.write("<input type='image' src='../common/img/controlbar/btnNext.png' alt='다음 페이지' title='다음 페이지' id='btnNext' onClick='goNextPage()'/>");
document.write("</div>");

document.write("<div id='popBubble'>");
document.write("</div>");



document.write("</div>");


document.write("</div>");



// 각 페이지에 따라 보이기 숨기기 설정

var browserType = getBrowserType();



/*
if(currentPage==1 && browserType.indexOf("Chrome")==-1){
	$("#control").attr('style','visibility:hidden');
	$("#paging").attr('style','visibility:hidden');
	$("#volSlideBar").attr('style','visibility:hidden');
}
*/

if(currentPage==1 || currentPage==totalPage || currentClau==movieClau){
	$("#nar_btn").attr('style','visibility:hidden');
	//$(".indexbtn").attr('style','visibility:hidden');
}

/*
if(currentClau==movieClau){
	$("#vol_btn").attr('style','visibility:hidden');
}
*/

if(currentClau!=movieClau){
	$(".lecbtn").attr('style','display:none');
	$(".bmkbtn").attr('style','display:none');
	$(".btns2").attr('style','display:none');
	$(".btns3").attr('style','display:none');
	
}

// 크게보기는 동영상페이지에서만
/*
if(!(currentClau==movieClau || currentPage==1 || currentPage==totalPage) ){
$("#full_btn").attr('style','visibility:hidden');
}
*/

// 마우스오버, 아웃

$(".indexbtn").mouseover(function() {
	if(parent.isFinishControl){
		$(this).attr("src","../common/img/controlbar/btnIndexOver.png");
		$(".indexbtn").css('cursor', 'pointer');
	}else{
		$(".indexbtn").css('cursor', 'none');
	}		 
});
$(".indexbtn").mouseout(function() {
	if(parent.isFinishControl) {
		$(this).attr("src", "../common/img/controlbar/btnIndex.png");
		$(".indexbtn").css('cursor', 'pointer');
	}else{
		$(".indexbtn").css('cursor', 'none');
	}
});

$(".indexbtn").click(function() {
	if(!parent.isFinishControl) {
		alert("해당 차시의 모든 학습 완료 후 이용 가능합니다.");
	}		  
});

$(".mapbtn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnMapOver.png");
});
$(".mapbtn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnMap.png");
});

$(".memobtn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnMemoOver.png");
});
$(".memobtn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnMemo.png");
});

$(".bmkbtn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnBookmarkOver.png");
});
$(".bmkbtn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnBookmark.png");
});

$(".lecbtn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnLecturerOver.png");
});
$(".lecbtn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnLecturer.png");
});

$(".spbtn").mouseover(function() {
	if(parent.isPageControl) {
		$("#displaySpeed").attr("color", "#0058bb");
		$(".spbtn").css('cursor', 'pointer');
	}else{
		$(".spbtn").css('cursor', 'none');
	}		 
});
$(".spbtn").mouseout(function() {
	if(parent.isPageControl) {
		$("#displaySpeed").attr("color", "#FFFFFF");
		$(".spbtn").css('cursor', 'pointer');
	}else{
		$(".spbtn").css('cursor', 'none');
	}		  
});

$("#displaySpeed").mouseover(function() {
	if(parent.isPageControl) {
		$(".spbtn").attr("src", "../common/img/controlbar/btnSpeedOver.png");
		$("#displaySpeed").css('color', '#0058bb');
		$("#displaySpeed").css('cursor', 'pointer');
	}else{
		$("#displaySpeed").css('color', '#ffffff');
		$("#displaySpeed").css('cursor', 'none');
	}   

});

$("#displaySpeed").mouseout(function() {
	if(parent.isPageControl) {
		$(".spbtn").attr("src","../common/img/controlbar/btnSpeed.png");
		$("#displaySpeed").css('color', '#0058bb');
		$("#displaySpeed").css('cursor', 'pointer');
	}else{
		$("#displaySpeed").css('color', '#ffffff');
		$("#displaySpeed").css('cursor', 'none');
	}   
});


$("#play_btn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnPlayOver.png");
});
$("#play_btn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnPlay.png");
});


$("#replay_btn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnReplayOver.png");
});
$("#replay_btn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnReplay.png");
});


$("#pause_btn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnPauseOver.png");
});
$("#pause_btn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnPause.png");
});

$("#nar_btn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnScriptOver.png");
});
$("#nar_btn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnScript.png");
});


$("#vol_btn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnVolOver.png");
});
$("#vol_btn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnVol.png");
})

$("#full_btn").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnFullScreenOver.png");
});
$("#full_btn").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnFullScreen.png");
})

$("#btnPrev").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnPrevOver.png");
});
$("#btnPrev").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnPrev.png");
})

$("#btnNext").mouseover(function() {
	     $(this).attr("src","../common/img/controlbar/btnNextOver.png");
});
$("#btnNext").mouseout(function() {
		  $(this).attr("src","../common/img/controlbar/btnNext.png");
})



var currentPageContent = $("#current_page");
if(currentPageContent){
	currentPageContent.empty();
	var pageNumString ="";

	if(currentPage > 9){
		
		var d10=Math.floor(currentPage/10);
		var d1=currentPage%10;
		pageNumString="<img src='../common/img/controlbar/num/d"+d10+".png'><img src='../common/img/controlbar/num/d"+d1+".png'>";
		//currentPageContent.append(currentPage);
	}else{
		pageNumString="<img src='../common/img/controlbar/num/d0.png'><img src='../common/img/controlbar/num/d"+currentPage+".png'>";
		//currentPageContent.append("0"+currentPage);
	}
	currentPageContent.append(pageNumString);
}

var totalPageContent = $("#total_page");
if(totalPageContent){
	totalPageContent.empty();
	var pageNumString ="";

	if(totalPage > 9){
		var d10=Math.floor(totalPage/10);
		var d1=totalPage%10;
		pageNumString="<img src='../common/img/controlbar/num/d"+d10+".png'><img src='../common/img/controlbar/num/d"+d1+".png'>";
	//totalPageContent.append(totalPage);
	}else{
	//totalPageContent.append("0" + totalPage);
		pageNumString="<img src='../common/img/controlbar/num/d0.png'><img src='../common/img/controlbar/num/d"+totalPage+".png'>";
	}
	totalPageContent.append(pageNumString);
	//	totalPageContent.append(" / " + totalPage);
}

// 말풍선
if(currentPage == totalPage)
	$("#popBubble").css("background", "url('../common/img/controlbar/conChapterEnd.png') no-repeat");
else
	$("#popBubble").css("background", "url('../common/img/controlbar/conChapterNext.png') no-repeat");

function showPopupChapter()
{
	$("#popBubble").show();

}

function hidePopupChapter()
{
	$("#popBubble").hide();
}


function fnMap() {
	//$("#popupReference").css("display", "block");
$("#popupReference").show();
$("#popupWrapper").show();
	_pauseMv();
		learningMapListing();
}

function fnLec() {
	//alert("runLec");
	$("#lecturerReference").show();
	$("#lecturerWrapper").show();
	_pauseMv();
		lecturerListing();
}


function fnMemo() {
		$("#bookmarkForm").hide();
		$("#memoForm").show();
		memoListing();
}

function fnBookmark() {
	if(movieClau!=currentClau){
		alert("이 페이지에서는 지원하지 않습니다.");		
	}else{
		$("#memoForm").hide();
		$("#bookmarkForm").show();
		bookmarkListing();
	}
}

function runDictionary() {
	alert("runDictionary");
}


// long ver
function learningMapListing(){
	//var lecnt=Math.round((chapNameSet.length-1)/2);
	var learningMapContent = $(".popupContent");
	if(learningMapContent){
		learningMapContent.empty();
		var learningString ="";
		//learningString ="<div id='learningUnit'>"
		for ( var i = 1; i < chapNameSet.length; i++) {
			if(i==1){
				learningString  += "<div id='learningSection'>"
			}
			learningString  += "<div id='learningUnit'>"
		
			if(i!=currentChapter){
				learningString  += "<div id='lmarkoff'></div><div id='lnumber'>"+i+"</div><div id='ltxt'>"+chapNameSet[i]+"</div>";
			}else{
				learningString  += "<div id='lmarkon'></div><div id='lnumber'>"+i+"</div><div id='ltxton'>"+chapNameSet[i]+"</div>";
			}		
			learningString +="</div>"

			if( i==chapNameSet.length){
				learningString  += "</div>"
			}
		}
		//learningString +="</div>"
		//alert(learningString);
		learningMapContent.append(learningString);
	}

}


function lecturerListing(){
	var lecturerContent = $(".lecturerContent");
	if(lecturerContent){
	lecturerContent.empty();
	var lecturerString="";
	lecturerString+="<div id='lecturerTitle'>학력</div>";
	lecturerString+="<div id='lecturerDesc'>"+lecturerDesc1+"</div>";
	lecturerString+="<div id='lecturerTitle'>경력</div>";
	lecturerString+="<div id='lecturerDesc'>"+lecturerDesc2+"</div>";
	//lecturerString+="<div id='lecturerName'><span>"+lecturerName+" 교수님</span><div>";
	lecturerString+="<div id='lecturerName'><div><span id='lname1'>"+lecturerName+"</span><span  id='lname2'> 교수님</span></div><div>";
	lecturerString+="<div id='lecturerImage'></div>";
	lecturerContent.append(lecturerString);
	}
}



var saveTime=[];
var saveText=[];


function bookmarkListing(){
	var isBookSave=false;
	var scnt=0;

	var bookmarkContent = $(".bookmarkContent");
	if(bookmarkContent){
		bookmarkContent.empty();
		
		// 북마크 정보가 있는지 확인
		/*
		var st = localStorage.getItem(bmkCode+"_bookMarkTime_"+currentChapter+"_"+currentPage);
		if(!(st==null || st==undefined)){
		saveTime = JSON.parse(st);
		}
		var td = localStorage.getItem(bmkCode+"_bookMarkText_"+currentChapter+"_"+currentPage);
		if(!(td==null || td==undefined)){
		saveText = JSON.parse(td);
		}
		*/
		var st= getCookie(bmkCode+"_bookMarkTime_"+currentChapter+"_"+currentPage);
		
		if(!(st==null || st==undefined)){
		saveTime=st.split(",");
		}
		var td= getCookie(bmkCode+"_bookMarkText_"+currentChapter+"_"+currentPage);
		if(!(td==null || td==undefined)){
		saveText = td.split(",");		
		}
	
		console.log("로딩1:"+saveTime);
		console.log("로딩2:"+saveText);	


		var bookmarkString ="";
		bookmarkString  += "<div id='bookmarkHead'>BOOKMARK</div><div class='bookmarkline'></div>"
		for ( var i = 1; i < 5 ; i++) {		
			bookmarkString  += "<div id='bookmarkUnit'>"
			/*console.log("세이브타임"+i+":"+saveTime[i-1]);*/
			if(saveTime[i-1]=="" || saveTime[i-1]==null || saveTime[i-1]==undefined){
			bookmarkString  += "<div><input type='image' src='../common/img/popup/btnAddTime.png' alt='시간추가' title='시간추가' id='addtime"+i+"'  class='bmkaction'></div>";
			bookmarkString  += "<div><input type='text' readonly id='savetime"+i+"'  class='stime'></div>";
			}else{
			bookmarkString  += "<div><input type='image' src='../common/img/popup/btnDelete.png' alt='삭제' title='삭제' id='deltime"+i+"'  class='bmkaction'><input type='image' src='../common/img/popup/btnMove.png' alt='이동' title='이동' id='movetime"+i+"'  class='bmkmove'  onclick='moveTime("+i+");return false;'></div>";
			bookmarkString  += "<div><input type='text' readonly id='savetime"+i+"'  class='stime' value='"+saveTime[i-1]+"'></div>";
			//$("#savetime"+i).val(saveTime[i-1]);
		//	console.log("시간 값 체크"+i+":"+$("#savetime"+i).val());
			}			
			
			if(saveText[i-1]=="" || saveText[i-1]==null || saveText[i-1]==undefined){
			bookmarkString  += "<div ><input type='text' id='txtdesc"+i+"' class='txtd'></div>";
			}else{
			//$("#txtdesc"+i).val(saveText[i-1]);
			//console.log("텍스트 값 체크"+i+":"+$("#txtdesc"+i).val());
			bookmarkString  += "<div ><input type='text' id='txtdesc"+i+"' class='txtd' value='"+saveText[i-1]+"'></div>";
			}
			bookmarkString +="</div>"
		}
		bookmarkString  += "<div class='bookmarkline'></div><div id='bookmarkFoot'><input type='image' id='bookSave' src='../common/img/popup/btnSave.png' alt='시간저장' title='시간저장'></div>"
		bookmarkContent.append(bookmarkString);

			// 북마크 시간 저장 또는 삭제시 액션
		$(".bmkaction").each(function(index) {
			$(this).click(function() {
				if($("#savetime"+(index+1)).val()==""){
				//console.log("북마크 저장 인덱스번호:"+index);  	
				var bt=rectime(_video.currentTime);
				$("#savetime"+(index+1)).val(bt);
				$("#addtime"+(index+1)).replaceWith("<input type='image' src='../common/img/popup/btnDelete.png' alt='삭제' title='삭제' id='deltime"+(index+1)+"'  class='bmkaction'><input type='image' src='../common/img/popup/btnMove.png' alt='이동' title='이동' id='movetime"+(index+1)+"'  class='bmkmove'  onclick='moveTime("+(index+1)+");return false;'>");

				}else{
		
					saveTime[index]="";
					saveText[index]="";
					$("#savetime"+(index+1)).val('');
					$("#txtdesc"+(index+1)).val('');
					/* 크롬 로컬에서는 해당 액션 잘 작동안함 */
					$("#deltime"+(index+1)).replaceWith("<input type='image' src='../common/img/popup/btnAddTime.png' alt='시간추가' title='시간추가' id='addtime"+(index+1)+"'  class='bmkaction'>");
					$("#movetime"+(index+1)).remove();
				}
			});	
		});

		// 북마크 정보 저장
	
		$("#bookSave").click(function() {
			scnt=0;
			$(".bmkaction").each(function(index) {

				var st=$("#savetime"+(index+1)).val();
					if(!(st=="" || st==null || st==undefined)){
						scnt++;
						saveTime[index]=st;
					}
				var td=$("#txtdesc"+(index+1)).val();

					if(!(td=="" || td==null || td==undefined)){
						scnt++;
						saveText[index]=td;
					}
			});
			if(scnt!=0){

				console.log("시간값:"+saveTime);
				console.log("텍스트값:"+saveText);
				setCookie(bmkCode+"_bookMarkTime_"+currentChapter+"_"+currentPage, saveTime, 365);
				setCookie(bmkCode+"_bookMarkText_"+currentChapter+"_"+currentPage, saveText, 365);
				/*
				localStorage["bookMarkTime_"+currentChapter+"_"+currentPage]=JSON.stringify(saveTime);	
				localStorage["bookMarkText_"+currentChapter+"_"+currentPage]=JSON.stringify(saveText);		
				localStorage.setItem("bookMarkTime_"+currentChapter+"_"+currentPage,JSON.stringify(saveTime));				
				localStorage.setItem("bookMarkText_"+currentChapter+"_"+currentPage,JSON.stringify(saveText));
				*/
				alert("북마크 저장되었습니다.");
				return;
			}else{
				alert("북마크에 저장할 정보가 없습니다.");
				return;
			}
		});	

	}

}


function moveTime(arg){

	var mvTime=$("#savetime"+arg).val();

	//alert("이동할 시간값:"+mvTime);
	var mvM=parseInt(mvTime.split(":")[0],10);
	var mvS=parseInt(mvTime.split(":")[1],10);
	var mvTime=mvM*60+mvS;
	//	alert("시간이동:"+mvTime);
	audioJump(mvTime);
}

var memoTxt="";
function memoListing(){

	memoContent = $(".memoContent");
	if(memoContent){
		memoContent.empty();

		var ckmemo= getCookie(bmkCode+"_memo_"+currentChapter);
		
		if(!(ckmemo==null || ckmemo==undefined)){
		memoTxt=ckmemo;
		}
		//console.log(memoTxt);

		var memoString ="";
		memoString  += "<div id='memoHead'>MEMO</div><div class='memoline'></div>"
		memoString  += "<div id='memoUnit'>"		
		memoString +="<form><textarea id='memoInput' cols='50' rows='6'></textarea>"

		memoString +="</form></div></div>";
		memoContent.append(memoString);
		if(!(memoTxt=="" || memoTxt==null || memoTxt==undefined)){
			console.log(memoTxt);
			$("#memoInput").val(memoTxt);
		}
	}

}


// 시계모양
function fnSpeed(){
	if(parent.isPageControl) {
		if ($('#curSpeed').hasClass('on')) {
			$('#curSpeed').removeClass('on');
			$('#speedBg').css("display", "none");
			$("#displaySpeed").css("top", "-15px");
			$("#displaySpeed").css("margin-left", "");
			$("#displaySpeed").css("margin-top", "0px");
		} else {
			$('#curSpeed').addClass('on');
			$('#speedBg').css("display", "inline-block");

			$("#displaySpeed").css("top", "-5px");
			$("#displaySpeed").css("margin-left", "6px");
		}
	}	
}

function fnOpenMenu(){
	if(parent.isFinishControl) {
		if (indexStatus == "close") {
			_indexOpenAc()
		} else {
			_indexCloseAc()
		}
	}
}