var pageTotal =  6;
var curPage = 1;
var completeConf = []

$(document).ready(function(){
	var print_html = '';
	print_html += '<div id="print-area" class="print-area">';
	for(i = 1; i <= print_total; i++){
		print_html += '<div class="print-page"><img src="./img/print-'+transNum(i)+'.png" /></div>';
	}	
	print_html += '</div>';
	$("body").append(print_html);
	var html = '';
	html += '<div class="contents-stage" id="summary-bg"></div>';
	html += '<div class="syncObj" id="summary">';
	html += '<div class="summary-wrap">';
	html += '<div class="summary-btn-wrap">';
	if(contentsMode != "capture"){
		html += '<button class="summary-btn summary-func-btn" id="summary-print-btn">';
		html += '<div class="summary-btn-name"></div>';
		html += '<div class="summary-btn-icon" id="summary-print-icon"></div>';
		html += '</button>';
		html += '<button class="summary-btn summary-func-btn" id="summary-down-btn">';
		html += '<div class="summary-btn-name"></div>';
		html += '<div class="summary-btn-icon" id="summary-down-icon"></div>';
		html += '</button>';
	}
	html += '</div>';
	html += '<div class="summary-con"></div>';
	html += '<div class="summary-move-wrap">';
	if(pageTotal > 1){
		html += '<button class="summary-btn summary-move-btn" id="summary-prev-btn"></button>';
		html += '<div class="summary-page" id="summary-cur-page"></div>';
		html += '<div class="summary-page" id="summary-slash">/</div>';
		html += '<div class="summary-page">'+transNum(pageTotal)+'</div>';
		html += '<button class="summary-btn summary-move-btn" id="summary-next-btn"></button>';
	}
	html += '</div>';
	html += '</div>';
	html += '</div>';
	$("#contentUI").html(html);
	buildSummary(curPage);
	$(".summary-btn").on({
		mouseover : function(){
			// if($(this).hasClass('summary-func-btn')){
			// 	// $(this).children(".summary-btn-name").css({"color":"#ffd600"});
			// 	// $(this).children(".summary-btn-icon").css({"background-position-x":"-"+$(".summary-btn-icon").width()+"px"});
			// }else{
			// 	$(this).css({"background-position-x":"-"+$(this).width()+"px"});
			// }
		},
		mouseout : function(){
			if($(this).hasClass('summary-func-btn')){
				// $(this).children(".summary-btn-name").css({"color":"#fff"});
				// $(this).children(".summary-btn-icon").css({"background-position-x":"0px"});
			}else{
				$(this).css({"background-position-x":"0px"});
			}
		},
		click : function(){
			var _id = $(this).attr('id').split("-");
			console.log("_id : "+_id+" / name : "+_id[1]);
			summary_fn(_id[1]);
		}
	});
	mobileBtnSet();
});

function  buildSummary(n){
	console.log("buildSummary : "+n);
	curPage = n;
	completeConf[curPage-1] = "complete";
	var html = '';
	for(i = 0; i < curPage; i++){
		$(".summary-con").html('<img src="common/img/contentUI/summary/'+i+'.png">');
	}
	// $(".summary-con").html(html);
	moveSet();
	complete_check();
}

function moveSet(){
	$("#summary-cur-page").html(transNum(curPage));
	$(".summary-move-btn").css({"background-position-x":"0px"});
	if(curPage == 1){
		$("#summary-prev-btn").attr("disabled",true).css({"cursor":"default","opacity":0});
		$("#summary-next-btn").attr("disabled",false).css({"cursor":"pointer","opacity":1});
	}else if(curPage == pageTotal){
		$("#summary-prev-btn").attr("disabled",false).css({"cursor":"pointer","opacity":1});
		$("#summary-next-btn").attr("disabled",true).css({"cursor":"default","opacity":0});
	}else{
		$("#summary-prev-btn").attr("disabled",false).css({"cursor":"pointer","opacity":1});
		$("#summary-next-btn").attr("disabled",false).css({"cursor":"pointer","opacity":1});
	}
}

function summary_move_fn(_dir){
	var move = 0;
	switch(_dir){
		case 'prev':
			move = curPage-1;
			break;
		case 'next':
			move = curPage+1;
			break;
	}
	buildSummary(move);
}

function summary_fn(_func){
	switch(_func){
		case 'print':
			console.log("인쇄하기!!!");
			window.print();
			break;
		case 'down':
			var down_url = "./down/summary.pdf";
			console.log("Summary 다운!!!"+"\n"+"down_url : "+down_url);
			window.open(down_url);
			break;
		default : 
			summary_move_fn(_func);
			break;
	}
}

function complete_check(){
	var _count = 0;
	for(i = 0; i < pageTotal; i++){
		if(completeConf[i] == "complete"){
			_count++;
		}
	}
	if(pageTotal == _count){
		app_complete = "complete";
		if(isPlaying == false && $("#balloonUI").css("display") == "none"){
			balloon_toggle("show");
			console.log("complete!!!");
		}
	}
}

function initApp(){
	app_complete = "incomplete";
	for(i = 0; i < pageTotal; i++){
		completeConf[i] = "incomplete";
	}
	console.log("initApp!!!"+"\n"+"completeConf : "+completeConf);
	curPage = 1;
	buildSummary(curPage);
}