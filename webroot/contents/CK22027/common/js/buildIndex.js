var flowInfo = new Object();
flowInfo = [
	{"code":"preview","index":""},
	{"code":"lesson","index":""},
	{"code":"review","index":""}
]
flowInfo["preview"] = []
flowInfo["lesson"] = []
flowInfo["review"] = []

var indexToggle = "close";
var flowTotal = flowInfo.length;
var curFlow = dataInfo[(now_page-1)].flow;
var curSub = 0;
var learnedPage = now_page+1;
$(document).ready(function(){
	createIndex();
});

function createIndex(){
	var html='';
	html += '<div id="index-wrap">';
	html += '<div id="index-list"></div>';
	// html += '<button id="index-close"><div id="index-close-icon"></div></button>';
	html += '</div>';
	$("#indexUI").html(html);
	$("#index-close").on({
		mouseover : function(){
			var _id = $(this).attr('id');
			$(this).css({"transform":"rotate(180deg)","transition":"all ease 0.5s"}); 
			$("#"+_id+"-icon").css({"background-position-x":"-"+$(this).width()+"px"});
		},
		mouseout : function(){
			var _id = $(this).attr('id');
			$(this).css({"transform":"rotate(0deg)","transition":"all ease 0.5s"});
			$("#"+_id+"-icon").css({"background-position-x":"0px"});
		},
		click : function(){
			index_view();
		}
	});
	mobileBtnSet();
	buildIndex();
}

function buildIndex(){
	var build = '';
	for(i = 1; i <= flowTotal; i++){
		build += '<div class="flow-wrap" id="flow-'+flowInfo[(i-1)].code+'">';
		build += '<button class="flow-index" id="'+flowInfo[(i-1)].code+'">';
		build += '<div class="flow-bullet" id="flow-bullet-'+flowInfo[(i-1)].code+'"></div><div class="flow-index-txt">'+flowInfo[(i-1)].index+'</div>';
		build += '</button>';
		build += '<div class="flow-index-sub-wrap">';
		var count = 0;
		for(j = 1; j <= total_page; j++){
			var _flow = dataInfo[(j-1)].flow;
			var _index = dataInfo[(j-1)].index;

			if(_index != "none" && flowInfo[(i-1)].code == _flow){
				count++;
				flowInfo[_flow].push(j);
				if(_index != "disable"){
					build += '<button class="flow-index-sub" id="'+_flow+'-sub'+count+'">';
					if(_flow == "lesson"){
						// build += '<div class="flow-index-sub-num">'+count+'.</div><div class="flow-index-sub-txt">'+_index+'</div>';
						build += '<div class="flow-index-sub-txt">'+_index+'</div>';
						subject.push(count+"&"+_index);
					}else{
						build += '<div class="flow-index-sub-bullet"></div><div class="flow-index-sub-txt">'+_index+'</div>';
						subject.push(_index);
					}
					build += '</button>';
				}else{
					subject.push(_index);
				}
			}
		}
		build += '</div>';
		build += '</div>';
	}
	/*build += '<div class="skill-btn-wrap">';
	if(chapter == 1){
		build += '<button class="skill-btn" id="skill-test-btn">';
		build += '<div class="skill-btn-name">역량진단</div>';
		build += '<div class="skill-btn-icon" id="skill-test-icon"></div>';
		build += '</button>';
	}
	build += '<button class="skill-btn" id="skill-dic-btn">';
	build += '<div class="skill-btn-name">용어설명집</div>';
	build += '<div class="skill-btn-icon" id="skill-dic-icon"></div>';
	build += '</button>';
	build += '</div>'; */
	$("#index-list").html(build);
	$(".flow-index").on({
		mouseover : function(){
			$(this).children("#flow-bullet-preview").css({"background-position-x":"-"+$(".flow-bullet").width()+"px"});
			$(this).children("#flow-bullet-lesson").css({"background-position-x":"-"+$(".flow-bullet").width()+"px"});
			$(this).children("#flow-bullet-review").css({"background-position-x":"-214px"});
			$(this).children(".flow-index-txt").css({"color":"#FDA947"});
		},
		mouseout : function(){
			var _id = $(this).attr('id');
			$(this).children(".flow-bullet").css({"background-position-x":"0px"});
			$(this).children(".flow-index-txt").css({"color":"#999"});
		},
				click : function(){
				var _id = $(this).attr('id');
				var _page = flowInfo[_id][0];

				if(parent.lastChkPageNum < _page){
					alert("순차학습하세요.");
				}else{
					movePage(_page);
				}
		}
	});
	$(".flow-index-sub").on({
		mouseover : function(){
			var _id = $(this).attr('id');
			var _idSplit = _id.split("-");
			var _flow = _idSplit[0];
			if(_flow == "lesson"){
				// $(this).children(".flow-index-sub-num").css({"color":"#FDA947","font-family":"scd9"});
				$(this).children(".flow-index-sub-bullet").css({"background-color":"#FDA947"});
			}else{
				$(this).children(".flow-index-sub-bullet").css({"background-color":"#FDA947"});
			}
			$(this).children(".flow-index-sub-txt").css({"color":"#fff","font-family":"scd7"});
		},
		mouseout : function(){
			var _id = $(this).attr('id');
			var _idSplit = _id.split("-");
			var _flow = _idSplit[0];
			var _sub = parseInt(_idSplit[1].replace("sub",""),10);
			if(_flow != curFlow){
				if(_flow == "lesson"){
					// $(this).children(".flow-index-sub-num").css({"color":"#999","font-family":"scd9"});
					$(this).children(".flow-index-sub-bullet").css({"background-color":"#999"});
				}else{
					$(this).children(".flow-index-sub-bullet").css({"background-color":"#999"});
				}
				$(this).children(".flow-index-sub-txt").css({"color":"#999","font-family":"scd7"});
			}else{
				if(_sub != curSub){
					if(_flow == "lesson"){
						$(this).children(".flow-index-sub-num").css({"color":"#999","font-family":"scd9"});
					}else{
						$(this).children(".flow-index-sub-bullet").css({"background-color":"#999"});
					}
					$(this).children(".flow-index-sub-txt").css({"color":"#999","font-family":"scd7"});
				}
			}
		},
		click : function(){
			var _id = $(this).attr('id');
			var _idSplit = _id.split("-");
			var _flow = _idSplit[0];
			var _sub = parseInt(_idSplit[1].replace("sub",""),10);
			var _page = flowInfo[_flow][(_sub-1)];
			if(parent.lastChkPageNum < _page){
				alert("순차학습하세요.");
			}else{
				movePage(_page);
			}

	}
	});
	$(".skill-btn").on({
		mouseover : function(){
			$(this).children(".skill-btn-name").css({"color":"#ffd600"});
			$(this).children(".skill-btn-icon").css({"background-position-x":"-"+$(".skill-btn-icon").width()+"px"});
		},
		mouseout : function(){
			$(this).children(".skill-btn-name").css({"color":"#fff"});
			$(this).children(".skill-btn-icon").css({"background-position-x":"0px"});
		},
		click : function(){
			var _id = $(this).attr('id').split("-");
			skill_fn(_id[1]);
		}
	});
	mobileBtnSet();
	index_on();
}

function skill_fn(_order){
	console.log("skill_fn : "+_order);
	switch(_order){
		case 'dic':
			var down_url = "../common/down/dictionary.pdf";
			window.open(down_url);
			break;
		case 'test':
			initTest();
			break;
	}
}

function index_on(){
	var _total = flowInfo[curFlow].length;
	for(i = 1; i <= _total; i++){
		if(i == _total){
			if(now_page >= flowInfo[curFlow][(i-1)]){
				curSub = i;
			}
		}else{
			if(now_page >= flowInfo[curFlow][(i-1)] && now_page < flowInfo[curFlow][i]){
				curSub = i;
			}
		}
	}
	$("#flow-"+curFlow).css({"background":"url(./common/img/indexUI/index_bg_u.png) no-repeat "});
	if(curFlow == "review"){
		$("#review").children(".flow-bullet").css({"background-position-x":"-214px"});
	}else{
		$("#"+curFlow).children(".flow-bullet").css({"background-position-x":"-"+$(".flow-bullet").width()+"px"});
	}
	$("#"+curFlow).children(".flow-index-txt").css({"color":"#FDA947"});
	if(curFlow == "lesson"){
		// $("#"+curFlow+"-sub"+curSub).children(".flow-index-sub-num").css({"color":"#FDA947","font-family":"scd9"});
		$("#"+curFlow+"-sub"+curSub).children(".flow-index-sub-bullet").css({"background-color":"#FDA947","font-family":"scd9"});
	}else{
		$("#"+curFlow+"-sub"+curSub).children(".flow-index-sub-bullet").css({"background-color":"#FDA947"});
	}
	$("#"+curFlow+"-sub"+curSub).children(".flow-index-sub-txt").css({"color":"#fff","font-family":"scd7"});
}

function index_view(){
	if(indexToggle == "close"){
		indexToggle = "open";
		$("#indexUI").show();
		var _temp = 258;
		$("#index-wrap").stop().animate({"margin-top":-_temp+"px"},300);
		$("#index-open-icon").css({"background":"url(./common/img/navigationUI/btn_indexOver.png)"});
		// $("#index-open-txt").html("INDEX").css({"color":"#FDA947"});
	}else{
		indexToggle = "close";
		$("#index-wrap").stop().animate({"margin-top":"0px"},300,function(){
			$("#indexUI").hide();
		});
		$("#index-open-icon").css({"background":"url(../img/navigationUI/btn_index.png) no-repeat"});
		// $("#index-open-txt").html("INDEX").css({"color":"#fff"});
	}
}